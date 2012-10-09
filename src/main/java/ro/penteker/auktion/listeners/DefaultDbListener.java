package ro.penteker.auktion.listeners;

import java.util.List;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityService;

public class DefaultDbListener implements HttpSessionListener {
  private static final transient Logger LOG = Logger.getLogger(SessionListener.class);

  private static boolean CREATED_DEFAULT = false;

  public void sessionCreated(HttpSessionEvent sessionEvent) {

    if (CREATED_DEFAULT)
      return;

    WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sessionEvent.getSession()
        .getServletContext());

    if (ctx.containsBean("securityService")) {
      SecurityService securityService = (SecurityService) ctx.getBean("securityService");
      AukUser admin = securityService.getUser("admin");
      if (admin == null) {
        LOG.warn("Admin user does not exist, creating a new one.");
        List<AukRole> roles = securityService.createDefaultRoles();
        securityService.createUser("system", "admin", "admin", true, roles);
      }
    } else {
      LOG.error("No bean securityService.");
    }

    CREATED_DEFAULT = true;
  }

  public void sessionDestroyed(HttpSessionEvent sessionEvent) {

  }
}
