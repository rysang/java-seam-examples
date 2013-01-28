package org.manga.reader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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

public class PageReader implements Runnable {

  private static final Logger LOG = Logger.getLogger(PageReader.class);

  private static final PoolingClientConnectionManager CLIENT_CONNECTION_MANAGER = new PoolingClientConnectionManager();
  private final DefaultHttpClient httpclient = new DefaultHttpClient(CLIENT_CONNECTION_MANAGER);

  private final String page;
  private final File directory;

  static {
    CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
  }

  public PageReader(String page, File directory) {
    httpclient.getCredentialsProvider().setCredentials(new AuthScope("158.169.9.13", 8012),
        new UsernamePasswordCredentials("pricecr", "hidden01!"));
    HttpHost proxy = new HttpHost("158.169.9.13", 8012);
    httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

    this.page = page;
    this.directory = directory;
  }

  @Override
  public void run() {
    LOG.info("Getting zip file.");
    HttpGet httpget = new HttpGet(page);
    try {

      HttpResponse response = httpclient.execute(httpget);
      HttpEntity entity = response.getEntity();

      int index = page.toLowerCase().lastIndexOf('/');
      final OutputStream os = new FileOutputStream(new File(directory, page.toLowerCase().substring(index)));
      entity.writeTo(os);

      os.close();
      httpget.releaseConnection();

    } catch (Exception e) {
      LOG.error("Error", e);
    }

  }

}
