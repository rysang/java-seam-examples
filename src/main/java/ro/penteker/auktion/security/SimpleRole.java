package ro.penteker.auktion.security;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class SimpleRole implements GrantedAuthority, Serializable {

  private static final long serialVersionUID = 1L;
  private String authority;

  public SimpleRole() {

  }

  public SimpleRole(String authority) {
    setAuthority(authority);
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return authority == null ? "ROLE_ANONYMOUS" : authority;
  }

  @Override
  public String toString() {
    return String.format("SimpleRole [authority=%s]", authority);
  }
}
