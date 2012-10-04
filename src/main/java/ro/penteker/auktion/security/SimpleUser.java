package ro.penteker.auktion.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SimpleUser implements UserDetails, Serializable {
  private static final long serialVersionUID = 1L;

  private ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
  private String username;
  private String password;

  public SimpleUser() {

  }

  public SimpleUser(String username, String password) {
    setUsername(username);
    setPassword(password);
  }

  public SimpleUser(String username, String password, Collection<GrantedAuthority> authorities) {
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
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return String.format("SimpleUser [authorities=%s, username=%s, password=%s]", authorities, username, password);
  }

}
