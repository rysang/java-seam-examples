package ro.penteker.auktion.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;

public class SecurityContext {

  private static final Logger LOG = Logger.getLogger(SecurityContext.class);

  public final UserDetails getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof UserDetails)) {

      LOG.info("Getting current user: " + principal);

      AukUser userDetails = new AukUser();
      userDetails.setUsername("ROLE_ANONYMOUS");
      userDetails.setPassword("ROLE_ANONYMOUS");

      userDetails.getAuthorities().add(new AukRole());
      return userDetails;
    }

    return (UserDetails) principal;
  }
}