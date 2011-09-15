package org.cpcs.dao.authentication.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
public class AppAuthority implements GrantedAuthority {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "authority")
  private String authority;

  @ManyToMany(mappedBy = "appAuthorities")
  private List<AppUser> users;

  public AppAuthority() {

  }

  public AppAuthority(String authority) {
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

  public void setUsers(List<AppUser> users) {
    this.users = users;
  }

  public List<AppUser> getUsers() {
    if (users == null) {
      users = new ArrayList<AppUser>();
    }

    return users;
  }

}
