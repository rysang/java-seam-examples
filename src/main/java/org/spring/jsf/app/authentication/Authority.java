package org.spring.jsf.app.authentication;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

  private static final long serialVersionUID = 1L;
  private String authority;

  public Authority() {

  }

  public Authority(String authority) {
    setAuthority(authority);
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public String toString() {
    return getAuthority() == null ? "ROLE_ANONYMOUS" : getAuthority();
  }
}
