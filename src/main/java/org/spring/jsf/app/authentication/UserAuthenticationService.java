package org.spring.jsf.app.authentication;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@Scope("prototype")
public class UserAuthenticationService implements UserDetailsService {

  private static final Logger LOG = Logger.getLogger("userDetailsService");

  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
    User user = new User("moez", "moez", true, true, true, true, Arrays.asList(new GrantedAuthority[] {
        new Authority("ROLE_ADMIN"), new Authority("ROLE_ANONYMOUS") }));

    return user;
  }

}
