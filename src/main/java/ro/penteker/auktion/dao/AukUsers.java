/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.penteker.auktion.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author pricecr
 */
@Entity
@Table(name = "auk_users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AukUsers.findAll", query = "SELECT a FROM AukUsers a"),
    @NamedQuery(name = "AukUsers.findById", query = "SELECT a FROM AukUsers a WHERE a.id = :id"),
    @NamedQuery(name = "AukUsers.findByUsername", query = "SELECT a FROM AukUsers a WHERE a.username = :username"),
    @NamedQuery(name = "AukUsers.findByPassword", query = "SELECT a FROM AukUsers a WHERE a.password = :password"),
    @NamedQuery(name = "AukUsers.findByEnabled", query = "SELECT a FROM AukUsers a WHERE a.enabled = :enabled"),
    @NamedQuery(name = "AukUsers.findByAccountNonExpired", query = "SELECT a FROM AukUsers a WHERE a.accountNonExpired = :accountNonExpired"),
    @NamedQuery(name = "AukUsers.findByAccountNonLocked", query = "SELECT a FROM AukUsers a WHERE a.accountNonLocked = :accountNonLocked"),
    @NamedQuery(name = "AukUsers.findByCredentialsNonExpired", query = "SELECT a FROM AukUsers a WHERE a.credentialsNonExpired = :credentialsNonExpired"),
    @NamedQuery(name = "AukUsers.findByCreatedDate", query = "SELECT a FROM AukUsers a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "AukUsers.findByCreatedBy", query = "SELECT a FROM AukUsers a WHERE a.createdBy = :createdBy") })
public class AukUsers implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id", nullable = false)
  private Long id;
  @Basic(optional = false)
  @Column(name = "username", nullable = false, length = 120)
  private String username;
  @Basic(optional = false)
  @Column(name = "password", nullable = false, length = 150)
  private String password;
  @Basic(optional = false)
  @Column(name = "enabled", nullable = false)
  private boolean enabled;
  @Basic(optional = false)
  @Column(name = "account_non_expired", nullable = false)
  private boolean accountNonExpired;
  @Basic(optional = false)
  @Column(name = "account_non_locked", nullable = false)
  private boolean accountNonLocked;
  @Basic(optional = false)
  @Column(name = "credentials_non_expired", nullable = false)
  private boolean credentialsNonExpired;
  @Basic(optional = false)
  @Column(name = "created_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  @Basic(optional = false)
  @Column(name = "created_by", nullable = false, length = 120)
  private String createdBy;
  @ManyToMany(mappedBy = "aukUsersList", fetch = FetchType.LAZY)
  private List<AukRoles> aukRolesList;

  public AukUsers() {
  }

  public AukUsers(Long id) {
    this.id = id;
  }

  public AukUsers(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
      boolean accountNonLocked, boolean credentialsNonExpired, Date createdDate, String createdBy) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.enabled = enabled;
    this.accountNonExpired = accountNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.credentialsNonExpired = credentialsNonExpired;
    this.createdDate = createdDate;
    this.createdBy = createdBy;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean getAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public boolean getAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public boolean getCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  @XmlTransient
  public List<AukRoles> getAukRolesList() {
    return aukRolesList;
  }

  public void setAukRolesList(List<AukRoles> aukRolesList) {
    this.aukRolesList = aukRolesList;
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
    if (!(object instanceof AukUsers)) {
      return false;
    }
    AukUsers other = (AukUsers) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ro.penteker.auktion.dao.AukUsers[ id=" + id + " ]";
  }

}
