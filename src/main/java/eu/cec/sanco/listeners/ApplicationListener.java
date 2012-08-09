package eu.cec.sanco.listeners;

import java.awt.Desktop;
import java.net.URI;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class ApplicationListener implements ServletContextListener, Runnable {

  private static final Logger LOG = Logger.getLogger(ApplicationListener.class);

  public void contextDestroyed(ServletContextEvent arg0) {

    
  }

  public void contextInitialized(ServletContextEvent arg0) {
    Executors.newSingleThreadExecutor().execute(this);
  }

  public void run() {
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e1) {
      LOG.error("Error", e1);
    }

    if (!java.awt.Desktop.isDesktopSupported()) {
      LOG.fatal("Desktop env is not available.");
      return;
    }

    Desktop desktop = Desktop.getDesktop();

    if (!desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {

      LOG.fatal("Desktop doesn't support the browse action (fatal)");
      return;
    }

    try {
      URI uri = new URI("http://localhost:8080/eccrs-ittool");
      desktop.browse(uri);
    } catch (Exception e) {
      LOG.fatal("Error", e);
    }
  }

}
