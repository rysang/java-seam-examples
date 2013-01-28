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
  private static final File dir = new File("D:\\discoveries");

  public static final String PAGE = "http://www.rexresearch.com";

  static {
    dir.mkdirs();
  }

  public PageFinder() {
    httpclient.getCredentialsProvider().setCredentials(new AuthScope("158.169.9.13", 8012),
        new UsernamePasswordCredentials("pricecr", "hidden01!"));
    HttpHost proxy = new HttpHost("158.169.9.13", 8012);
    httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
  }

  public void find() throws Exception {

    HttpGet httpget = new HttpGet(PAGE);
    HttpResponse response = httpclient.execute(httpget);
    HttpEntity entity = response.getEntity();

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    entity.writeTo(bos);

    Document document = Jsoup.parse(bos.toString("UTF-8"));
    Elements links = document.select("a");
    int counter = 0;

    for (Element link : links) {

      String page = "http://www.rexresearch.com/" + link.attr("href");
      if (!page.toLowerCase().endsWith(".pdf")) {
        LOG.warn("Not a zip file. Exiting thread.");
        continue;
      }

      EXECUTOR_SERVICE.execute(new PageReader(page, dir));

      if (counter > 0 && (counter % 20 == 0)) {
        Thread.sleep(2000);
      }
    }
  }
}
