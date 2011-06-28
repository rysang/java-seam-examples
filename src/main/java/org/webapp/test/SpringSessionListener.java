package org.webapp.test;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringSessionListener implements HttpSessionListener {
  private boolean eventOccurring_ = false;
  private static final Logger LOG = Logger.getLogger("SpringSessionListener");

  public SpringSessionListener() {
    LOG.info("Init.");
  }

  public synchronized void sessionCreated(HttpSessionEvent sessionEvt) {
    LOG.info("Session Created. Exposing spring beans.");

    if (!eventOccurring_) {
      eventOccurring_ = true;
      HttpSession session = sessionEvt.getSession();
      WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
      Map<String, Object> allBeans = ctx.getBeansWithAnnotation(JSPSession.class);
      Set<Map.Entry<String, Object>> beanSet = allBeans.entrySet();

      for (Map.Entry<String, Object> bean : beanSet) {
        session.setAttribute(bean.getKey(), bean.getValue());
      }

      eventOccurring_ = false;
    }
  }

  public void sessionDestroyed(HttpSessionEvent arg0) {
  }
}