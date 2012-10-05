package ro.penteker.auktion.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ro.penteker.auktion.dao.security.AuktionRole;
import ro.penteker.auktion.dao.security.AuktionUser;
import ro.penteker.auktion.utils.api.Utils;



public class UserAuthenticationService implements UserDetailsService {

  private static final Logger LOG = Logger.getLogger("userDetailsService");

  @Autowired
  @Qualifier("utils")
  private Utils utils;
  
  private int passwordIndex = 0;

  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
    AuktionUser user = new AuktionUser(userName,utils.getKeys(userName).get(passwordIndex));
    AuktionRole role = new AuktionRole("ROLE_USER");
    
    user.getAuthorities().add(role);
    return user;
  }

  public void setPasswordIndex(int passwordIndex) {
    this.passwordIndex = passwordIndex;
  }

  public int getPasswordIndex() {
    return passwordIndex;
  }

}
