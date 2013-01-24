package org.manga.reader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageFinder {
  private static final Logger LOG = Logger.getLogger(PageFinder.class);
  private final DefaultHttpClient httpclient = new DefaultHttpClient();
  private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(20);
  private static final File dir = new File("D:\\solr-tests\\test-files");

  public static final String MANGA_LIST = "http://www.mangapanda.com/alphabetical";

  public PageFinder() {
    httpclient.getCredentialsProvider().setCredentials(new AuthScope("158.169.9.13", 8012),
        new UsernamePasswordCredentials("pricecr", "hidden01!"));
    HttpHost proxy = new HttpHost("158.169.9.13", 8012);
    httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
  }

  public void find() throws Exception {

    HttpGet httpget = new HttpGet(MANGA_LIST);
    HttpResponse response = httpclient.execute(httpget);
    HttpEntity entity = response.getEntity();

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    entity.writeTo(bos);

    Document document = Jsoup.parse(bos.toString("UTF-8"));
    Elements links = document.select(".series_alpha a");
    int counter = 0;

    for (Element link : links) {
      EXECUTOR_SERVICE.execute(new PageReader("http://www.mangapanda.com" + link.attr("href"), dir));

      if (counter > 0 && (counter % 20 == 0)) {
        Thread.sleep(2000);
      }
    }
  }
}
