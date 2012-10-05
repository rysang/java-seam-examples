package ro.penteker.auktion.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ro.penteker.auktion.services.api.SecurityService;

public class UserAuthenticationService implements UserDetailsService {

  private static final Logger LOG = Logger.getLogger("userDetailsService");

  @Autowired
  @Qualifier("securityService")
  private SecurityService securityService;

  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
    LOG.info("Authenticating: " + userName);
    return securityService.getUser(userName);
  }

}
