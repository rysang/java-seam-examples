package ro.penteker.auktion.security;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class AuktionRole implements GrantedAuthority, Serializable {

  private static final long serialVersionUID = 1L;
  private String authority = "ROLE_ANONYMOUS";

  public AuktionRole() {

  }

  public AuktionRole(String authority) {
    setAuthority(authority);
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return authority;
  }

  @Override
  public String toString() {
    return String.format("AuktionRole [authority=%s]", authority);
  }

}
