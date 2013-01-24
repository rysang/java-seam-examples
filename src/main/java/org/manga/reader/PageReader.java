package org.manga.reader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageReader implements Runnable {

  private static final Logger LOG = Logger.getLogger(PageReader.class);

  private static final PoolingClientConnectionManager CLIENT_CONNECTION_MANAGER = new PoolingClientConnectionManager();
  private final DefaultHttpClient httpclient = new DefaultHttpClient(CLIENT_CONNECTION_MANAGER);

  private final String mangaPage;
  private Document document;
  private final File directory;

  private static Long count = 1L;

  static {
    CLIENT_CONNECTION_MANAGER.setMaxTotal(100);
  }

  public PageReader(String mangaPage, File directory) {
    httpclient.getCredentialsProvider().setCredentials(new AuthScope("158.169.9.13", 8012),
        new UsernamePasswordCredentials("pricecr", "hidden01!"));
    HttpHost proxy = new HttpHost("158.169.9.13", 8012);
    httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

    this.mangaPage = mangaPage;
    this.directory = directory;
  }

  @Override
  public void run() {
    HttpGet httpget = new HttpGet(mangaPage);
    try {

      HttpResponse response = httpclient.execute(httpget);
      HttpEntity entity = response.getEntity();

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      entity.writeTo(bos);

      Doc doc = new Doc();
      document = Jsoup.parse(bos.toString("UTF-8"));

      doc.setId(UUID.randomUUID().toString());
      doc.setTitle(document.select("#mangaproperties h1").text());
      doc.setDescription(document.select("#readmangasum p").text());

      Elements genres = document.select(".genretags");
      for (Element g : genres) {
        doc.getGenres().add(g.text());
      }

      Elements propertytitles = document.select("#mangaproperties tr");
      for (Element prop : propertytitles) {
        Elements siblings = prop.getElementsByTag("td");
        if (siblings.get(0).text().toLowerCase().contains("year")) {
          doc.setYear(siblings.get(1).text().trim());
        } else if (siblings.get(0).text().toLowerCase().contains("alternate name")) {
          doc.setAlternateName(siblings.get(1).text().trim());
        } else if (siblings.get(0).text().toLowerCase().contains("status")) {
          doc.setStatus(siblings.get(1).text().trim());
        } else if (siblings.get(0).text().toLowerCase().contains("author")) {
          doc.setAuthor(siblings.get(1).text().trim());
        } else if (siblings.get(0).text().toLowerCase().contains("artist")) {
          doc.setArtist(siblings.get(1).text().trim());
        } else if (siblings.get(0).text().toLowerCase().contains("reading direction")) {
          doc.setReadingDirection(siblings.get(1).text().trim());
        }
      }

      LOG.info(doc);

      String fileName;
      synchronized (count) {
        fileName = count + ".xml";
      }

      writeToDisk(new File(directory, fileName), doc);

      synchronized (count) {
        count++;
      }

      httpget.releaseConnection();

    } catch (Exception e) {
      LOG.error("Error", e);
    }

  }

  public void writeToDisk(File file, Doc doc) throws Exception {
    LOG.info(file);

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    org.w3c.dom.Document xmlDoc = documentBuilder.newDocument();

    org.w3c.dom.Element addElem = xmlDoc.createElement("add");
    xmlDoc.appendChild(addElem);

    org.w3c.dom.Element docElem = xmlDoc.createElement("doc");
    addElem.appendChild(docElem);

    org.w3c.dom.Element fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "id");
    fieldElem.setTextContent(doc.getId());

    fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "title");
    fieldElem.setTextContent(doc.getTitle());

    fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "description");
    fieldElem.setTextContent(doc.getDescription());

    for (String genre : doc.getGenres()) {

      fieldElem = xmlDoc.createElement("field");
      docElem.appendChild(fieldElem);
      fieldElem.setAttribute("name", "genre");
      fieldElem.setTextContent(genre);

    }

    fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "year");
    fieldElem.setTextContent(String.valueOf(doc.getYear()));

    fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "status");
    fieldElem.setTextContent(String.valueOf(doc.getStatus()));

    fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "author");
    fieldElem.setTextContent(String.valueOf(doc.getAuthor()));

    fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "artist");
    fieldElem.setTextContent(String.valueOf(doc.getArtist()));

    fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "readingDirection");
    fieldElem.setTextContent(String.valueOf(doc.getReadingDirection()));

    fieldElem = xmlDoc.createElement("field");
    docElem.appendChild(fieldElem);
    fieldElem.setAttribute("name", "alternateName");
    fieldElem.setTextContent(String.valueOf(doc.getAlternateName()));

    FileOutputStream fos = new FileOutputStream(file);

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    DOMSource source = new DOMSource(xmlDoc);
    StreamResult result = new StreamResult(fos);
    transformer.transform(source, result);

    fos.close();

    LOG.info("Done " + file + " ...");
  }

}
