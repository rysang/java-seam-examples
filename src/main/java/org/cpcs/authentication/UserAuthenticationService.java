package org.cpcs.authentication;

import org.apache.log4j.Logger;
import org.cpcs.dao.authentication.services.api.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@Scope("prototype")
public class UserAuthenticationService implements UserDetailsService {

  @Autowired
  private UserDao userService;

  private static final Logger LOG = Logger.getLogger("userDetailsService");

  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
    UserDetails user = userService.getByUsername(userName);
    if (user == null) {
      throw new UsernameNotFoundException("User not found.");
    }

    LOG.info("Authorities size: " + user.getAuthorities().size());
    return user;
  }

}
