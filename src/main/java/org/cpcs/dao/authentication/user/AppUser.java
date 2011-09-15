package org.cpcs.dao.authentication.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class AppUser implements UserDetails {

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "account_non_expired")
  private boolean accountNonExpired;

  @Column(name = "account_non_locked")
  private boolean accountNonLocked;

  @Column(name = "credentials_non_expired")
  private boolean credentialsNonExpired;

  @Column(name = "enabled")
  private boolean enabled;

  @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
  private List<AppAuthority> appAuthorities;

  public AppUser() {
    setAccountNonExpired(true);
    setAccountNonLocked(true);
    setCredentialsNonExpired(true);
    setEnabled(true);
  }

  public List<AppAuthority> getAppAuthorities() {
    if (appAuthorities == null) {
      appAuthorities = new ArrayList<AppAuthority>();
    }

    return appAuthorities;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Collection<GrantedAuthority> getAuthorities() {
    return (Collection) getAppAuthorities();
  }

  public void setAppAuthorities(List<AppAuthority> appAuthorities) {
    this.appAuthorities = appAuthorities;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder('[').append(getUsername());
    sb.append(" ,").append(getPassword()).append(']');

    return sb.toString();
  }

}
