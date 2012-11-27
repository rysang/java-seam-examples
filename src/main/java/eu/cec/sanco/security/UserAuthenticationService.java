package eu.cec.sanco.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import eu.cec.sanco.utils.api.Utils;

public class UserAuthenticationService implements UserDetailsService {

  private static final Logger LOG = Logger.getLogger("userDetailsService");

  @Autowired
  @Qualifier("utils")
  private Utils utils;

  private int passwordIndex = 0;

  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
    try {

      SimpleUser user = new SimpleUser(userName, utils.getKeys(userName).get(passwordIndex));
      SimpleRole role = new SimpleRole("ROLE_USER");

      user.getAuthorities().add(role);
      return user;
    } catch (Exception e) {
      throw new UsernameNotFoundException("User not found.", e);
    }
  }

  public void setPasswordIndex(int passwordIndex) {
    this.passwordIndex = passwordIndex;
  }

  public int getPasswordIndex() {
    return passwordIndex;
  }

}
