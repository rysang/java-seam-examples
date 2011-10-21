/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cpcs.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author pricecr
 */
@Entity
@Table(name = "user_preferences", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "id" }) })
@NamedQueries({ @NamedQuery(name = "UserPreferences.findAll", query = "SELECT u FROM UserPreferences u"),
    @NamedQuery(name = "UserPreferences.findById", query = "SELECT u FROM UserPreferences u WHERE u.id = :id"),
    @NamedQuery(name = "UserPreferences.findByName", query = "SELECT u FROM UserPreferences u WHERE u.name = :name"),
    @NamedQuery(name = "UserPreferences.findByValue", query = "SELECT u FROM UserPreferences u WHERE u.value = :value") })
public class UserPreferences implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id", nullable = false)
  private Integer id;
  @Basic(optional = false)
  @Column(name = "name", nullable = false, length = 255)
  private String name;
  @Column(name = "value", length = 255)
  private String value;
  @JoinColumn(name = "user_login", referencedColumnName = "login", nullable = false)
  @ManyToOne(optional = false)
  private NotifUser userLogin;

  public UserPreferences() {
  }

  public UserPreferences(Integer id) {
    this.id = id;
  }

  public UserPreferences(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public NotifUser getUserLogin() {
    return userLogin;
  }

  public void setUserLogin(NotifUser userLogin) {
    this.userLogin = userLogin;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UserPreferences)) {
      return false;
    }
    UserPreferences other = (UserPreferences) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "org.cpcs.dao.UserPreferences[ id=" + id + " ]";
  }

}
