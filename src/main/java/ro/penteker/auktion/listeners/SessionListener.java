package ro.penteker.auktion.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ro.penteker.auktion.utils.api.Utils;

public class SessionListener implements HttpSessionListener {
  private static final transient Logger LOG = Logger.getLogger(SessionListener.class);

  public void sessionCreated(HttpSessionEvent arg0) {

  }

  public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sessionEvent.getSession()
        .getServletContext());

    if (ctx.containsBean("utils")) {
      Utils submissionService = (Utils) ctx.getBean("utils");
      submissionService.reset();
    } else {
      LOG.error("No bean loginService.");
    }
  }
}