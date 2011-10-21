/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cpcs.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author pricecr
 */
@Entity
@Table(name = "notif_role")
@NamedQueries({ @NamedQuery(name = "NotifRole.findAll", query = "SELECT n FROM NotifRole n"),
    @NamedQuery(name = "NotifRole.findByName", query = "SELECT n FROM NotifRole n WHERE n.name = :name") })
public class NotifRole implements Serializable, GrantedAuthority {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "name", nullable = false, length = 100)
  private String name;
  @ManyToMany(mappedBy = "notifRoleList")
  private List<NotifUser> notifUserList;

  public NotifRole() {
  }

  public NotifRole(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<NotifUser> getNotifUserList() {
    return notifUserList;
  }

  public void setNotifUserList(List<NotifUser> notifUserList) {
    this.notifUserList = notifUserList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (name != null ? name.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof NotifRole)) {
      return false;
    }
    NotifRole other = (NotifRole) object;
    if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "org.cpcs.dao.NotifRole[ name=" + name + " ]";
  }

  public String getAuthority() {
    return getName();
  }

}
