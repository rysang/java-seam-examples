/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cpcs.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author pricecr
 */
@Entity
@Table(name = "notif_user")
@NamedQueries({
    @NamedQuery(name = "NotifUser.findAll", query = "SELECT n FROM NotifUser n"),
    @NamedQuery(name = "NotifUser.findByLogin", query = "SELECT n FROM NotifUser n WHERE n.login = :login"),
    @NamedQuery(name = "NotifUser.findByPassword", query = "SELECT n FROM NotifUser n WHERE n.password = :password"),
    @NamedQuery(name = "NotifUser.findByFirstName", query = "SELECT n FROM NotifUser n WHERE n.firstName = :firstName"),
    @NamedQuery(name = "NotifUser.findByLastName", query = "SELECT n FROM NotifUser n WHERE n.lastName = :lastName"),
    @NamedQuery(name = "NotifUser.findByActive", query = "SELECT n FROM NotifUser n WHERE n.active = :active") })
public class NotifUser implements Serializable, UserDetails {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "login", nullable = false, length = 255)
  private String login;
  @Basic(optional = false)
  @Column(name = "password", nullable = false, length = 10)
  private String password;
  @Column(name = "first_name", length = 100)
  private String firstName;
  @Column(name = "last_name", length = 100)
  private String lastName;
  @Basic(optional = false)
  @Column(name = "active", nullable = false)
  private boolean active;
  @JoinTable(name = "user2role", joinColumns = { @JoinColumn(name = "user_login", referencedColumnName = "login", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_name", referencedColumnName = "name", nullable = false) })
  @ManyToMany
  private List<NotifRole> notifRoleList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userLogin")
  private List<GoogleCalAcc> googleCalAccList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userLogin")
  private List<EmailAccount> emailAccountList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userLogin")
  private List<UserPreferences> userPreferencesList;

  public NotifUser() {
  }

  public NotifUser(String login) {
    this.login = login;
  }

  public NotifUser(String login, String password, boolean active) {
    this.login = login;
    this.password = password;
    this.active = active;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<NotifRole> getNotifRoleList() {
    return notifRoleList;
  }

  public void setNotifRoleList(List<NotifRole> notifRoleList) {
    this.notifRoleList = notifRoleList;
  }

  public List<GoogleCalAcc> getGoogleCalAccList() {
    return googleCalAccList;
  }

  public void setGoogleCalAccList(List<GoogleCalAcc> googleCalAccList) {
    this.googleCalAccList = googleCalAccList;
  }

  public List<EmailAccount> getEmailAccountList() {
    return emailAccountList;
  }

  public void setEmailAccountList(List<EmailAccount> emailAccountList) {
    this.emailAccountList = emailAccountList;
  }

  public List<UserPreferences> getUserPreferencesList() {
    return userPreferencesList;
  }

  public void setUserPreferencesList(List<UserPreferences> userPreferencesList) {
    this.userPreferencesList = userPreferencesList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (login != null ? login.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof NotifUser)) {
      return false;
    }
    NotifUser other = (NotifUser) object;
    if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "org.cpcs.dao.NotifUser[ login=" + login + " ]";
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return (Collection) getNotifRoleList();
  }

  public String getUsername() {
    return getLogin();
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

  public void setUsername(String string) {
    setLogin(string);
  }

}
