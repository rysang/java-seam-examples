package org.cpcs.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@Scope("prototype")
public class UserAuthenticationService implements UserDetailsService {

  private static final Logger LOG = Logger.getLogger("userDetailsService");

  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
    if (userName.equals("admin")) {

      SimpleRole role = new SimpleRole("ROLE_ADMIN");
      SimpleUser user = new SimpleUser("admin", "admin");
      user.setAuthorities((Collection<GrantedAuthority>) (List) Arrays.asList(role));

      return user;
    }

    throw new UsernameNotFoundException("User is not admin.");
  }

}
