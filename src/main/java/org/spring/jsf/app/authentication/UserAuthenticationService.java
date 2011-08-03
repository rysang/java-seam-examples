package org.spring.jsf.app.authentication;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@Scope("prototype")
public class UserAuthenticationService implements AuthenticationProvider {

  private static final Logger LOG = Logger.getLogger("userDetailsService");

  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UsernamePasswordAuthenticationToken ret = null;

    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      ret = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),
          Arrays.asList(new GrantedAuthority[] { new Authority("ROLE_ADMIN"), new Authority("ROLE_ANONYMOUS") }));
    }

    return ret;
  }

  public boolean supports(Class<? extends Object> authentication) {
    if (authentication.getName().equals(UsernamePasswordAuthenticationToken.class.getName()))
      return true;

    return false;
  }

}
