package eu.cec.sanco.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

import eu.cec.sanco.beans.Proxy;
import eu.cec.sanco.beans.SearchResult;
import eu.cec.sanco.services.api.XMLGateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class XMLGateServiceImpl implements XMLGateService {

  private static final transient Logger LOG = Logger.getLogger(XMLGateServiceImpl.class);
  public static final String SOAP_ACTION = "callServices";
  public static final String CONTENT_TYPE = "application/soap+xml; charset=utf-8";

  private Gson gson;
  private DefaultHttpClient httpclient = new DefaultHttpClient();

  private Configuration freemarkerConfiguration;
  private static Template createEntryTpl;
  private static Template searchEntriesTpl;
  private static Template retrieveSubmissionTpl;

  private String webServicePath = "https://ec.europa.eu/ws/sanco/xmlgatev2/acceptance";

  public XMLGateServiceImpl() {

  }

  public void init() throws IOException {
    LOG.info("Init templates ...");

    freemarkerConfiguration.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
    // freemarkerConfiguration.setEncoding(Locale.getDefault(), "UTF-8");

    createEntryTpl = freemarkerConfiguration.getTemplate("soap-create-entry.tpl");
    searchEntriesTpl = freemarkerConfiguration.getTemplate("soap-search.tpl");
    retrieveSubmissionTpl = freemarkerConfiguration.getTemplate("retrieve-submission.tpl");

    LOG.info("Done ...");
  }

  public void setGson(Gson gson) {
    this.gson = gson;
  }

  public Gson getGson() {
    return gson;
  }

  public void setProxy(Proxy proxy) {
    httpclient.getCredentialsProvider().setCredentials(new AuthScope(proxy.getHost(), proxy.getPort()),
        new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword()));
    HttpHost httpProxy = new HttpHost("localhost", 8080);
    httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpProxy);
  }

  /**
   * 
   * 
   * @param url
   * @return
   * @throws IOException
   * @throws ClientProtocolException
   */
  public String post(String requestBody) throws ClientProtocolException, IOException {
    HttpPost httpPost = new HttpPost(getWebServicePath());
    httpPost.setHeader("SOAPAction", SOAP_ACTION);
    httpPost.setHeader("Content-Type", CONTENT_TYPE);
    httpPost.setEntity(new StringEntity(requestBody, Charset.forName("UTF-8")));

    HttpResponse response = httpclient.execute(httpPost);
    HttpEntity entity = response.getEntity();

    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    try {

      InputStream is = entity.getContent();
      int c;
      while ((c = is.read()) != -1) {
        bos.write(c);
      }
      is.close();

    } catch (Exception e) {
      LOG.error(bos.toString("UTF-8"));
    }

    LOG.info("Buffer size: " + ((bos.size() / 1024) / 1024) + " MB");
    // LOG.info(bos);
    EntityUtils.consume(entity);

    return new String(bos.toByteArray(), Charset.forName("UTF-8"));

  }

  public SearchResult searchSubmissions(String year, String organizationId, String country) throws TemplateException,
      IOException, SAXException, ParserConfigurationException {

    Map<String, Object> params = new Hashtable<String, Object>();
    params.put("year", year);
    params.put("organizationId", organizationId);
    params.put("country", country);

    params.put("username", System.getProperty("client.username", "test"));
    LOG.info("Using username: " + System.getProperty("client.username", "test"));
    params.put("password", System.getProperty("client.password", "test"));
    LOG.info("Using password: " + System.getProperty("client.password", "test"));

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    Writer w = new PrintWriter(bos);
    searchEntriesTpl.process(params, w);
    w.close();

    String result = post(new String(bos.toByteArray(), Charset.forName("UTF-8")));

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new ByteArrayInputStream(result.getBytes(Charset.forName("UTF-8"))));

    SearchResult jsonResult = new SearchResult();

    NodeList list = doc.getElementsByTagName("searchSubmission");
    if (list.getLength() > 0) {
      Element searchSubmission = (Element) list.item(0);
      String content = searchSubmission.getTextContent();

      jsonResult = gson.fromJson(content, SearchResult.class);
    }

    return jsonResult;
  }

  public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
    this.freemarkerConfiguration = freemarkerConfiguration;
  }

  public Configuration getFreemarkerConfiguration() {
    return freemarkerConfiguration;
  }

  public void setWebServicePath(String webServicePath) {
    this.webServicePath = webServicePath;
  }

  public String getWebServicePath() {
    return webServicePath;
  }
}
