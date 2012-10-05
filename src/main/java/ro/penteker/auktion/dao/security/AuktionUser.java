package ro.penteker.auktion.dao.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuktionUser implements UserDetails, Serializable {
  private static final long serialVersionUID = 1L;

  private ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
  private String username = "USER_ANONYMOUS";
  private String password;

  private boolean enabled = true;
  private boolean accountNonExpired = true;
  private boolean accountNonLocked = true;
  private boolean credentialsNonExpired = true;

  public AuktionUser() {

  }

  public AuktionUser(String username, String password) {
    setUsername(username);
    setPassword(password);
  }

  public AuktionUser(String username, String password, Collection<GrantedAuthority> authorities) {
    setUsername(username);
    setPassword(password);
    setAuthorities(authorities);
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<GrantedAuthority> authorities) {
    this.authorities = new ArrayList<GrantedAuthority>(authorities);
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public String toString() {
    return String.format("AuktionUser [authorities=%s, username=%s, password=%s]", authorities, username, password);
  }

}
