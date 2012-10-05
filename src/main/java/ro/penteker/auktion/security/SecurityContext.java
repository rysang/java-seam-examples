package ro.penteker.auktion.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ro.penteker.auktion.dao.security.AuktionRole;
import ro.penteker.auktion.dao.security.AuktionUser;

public class SecurityContext {

  private static final Logger LOG = Logger.getLogger(SecurityContext.class);

  public static final UserDetails getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof UserDetails)) {

      LOG.info("Getting current user: " + principal);

      UserDetails userDetails = new AuktionUser("ROLE_ANONYMOUS", "ROLE_ANONYMOUS");
      userDetails.getAuthorities().add(new AuktionRole());
      return userDetails;
    }

    return (UserDetails) principal;
  }
}