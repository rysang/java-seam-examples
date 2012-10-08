/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.penteker.auktion.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author pricecr
 */
@Entity
@Table(name = "auk_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AukUser.findAll", query = "SELECT a FROM AukUser a"),
    @NamedQuery(name = "AukUser.findById", query = "SELECT a FROM AukUser a WHERE a.id = :id"),
    @NamedQuery(name = "AukUser.findByUsername", query = "SELECT a FROM AukUser a WHERE a.username = :username"),
    @NamedQuery(name = "AukUser.findByPassword", query = "SELECT a FROM AukUser a WHERE a.password = :password"),
    @NamedQuery(name = "AukUser.findByEnabled", query = "SELECT a FROM AukUser a WHERE a.enabled = :enabled"),
    @NamedQuery(name = "AukUser.findByAccountNonExpired", query = "SELECT a FROM AukUser a WHERE a.accountNonExpired = :accountNonExpired"),
    @NamedQuery(name = "AukUser.findByAccountNonLocked", query = "SELECT a FROM AukUser a WHERE a.accountNonLocked = :accountNonLocked"),
    @NamedQuery(name = "AukUser.findByCredentialsNonExpired", query = "SELECT a FROM AukUser a WHERE a.credentialsNonExpired = :credentialsNonExpired"),
    @NamedQuery(name = "AukUser.findByCreatedDate", query = "SELECT a FROM AukUser a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "AukUser.findByCreatedBy", query = "SELECT a FROM AukUser a WHERE a.createdBy = :createdBy") })
public class AukUser implements UserDetails, Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
  private boolean enabled = true;
  @Basic(optional = false)
  @Column(name = "account_non_expired", nullable = false)
  private boolean accountNonExpired = true;
  @Basic(optional = false)
  @Column(name = "account_non_locked", nullable = false)
  private boolean accountNonLocked = true;
  @Basic(optional = false)
  @Column(name = "credentials_non_expired", nullable = false)
  private boolean credentialsNonExpired = true;
  @Basic(optional = false)
  @Column(name = "created_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  @Basic(optional = false)
  @Column(name = "created_by", nullable = false, length = 120)
  private String createdBy;

  private transient boolean admin = false;

  @JoinTable(name = "auk_role_2_user", joinColumns = { @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false) })
  @ManyToMany(fetch = FetchType.LAZY)
  private List<AukRole> aukRoleList = new ArrayList<AukRole>();

  public AukUser() {
  }

  public AukUser(Long id) {
    this.id = id;
  }

  public AukUser(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
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
  public List<AukRole> getAukRoleList() {
    return aukRoleList;
  }

  public void setAukRoleList(List<AukRole> aukRoleList) {
    this.aukRoleList = aukRoleList;
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
    if (!(object instanceof AukUser)) {
      return false;
    }
    AukUser other = (AukUser) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ro.penteker.auktion.dao.AukUser[ id=" + id + " ]";
  }

  public Collection<GrantedAuthority> getAuthorities() {
    return (List) getAukRoleList();
  }

  public boolean isAccountNonExpired() {
    return getAccountNonExpired();
  }

  public boolean isAccountNonLocked() {
    return getAccountNonLocked();
  }

  public boolean isCredentialsNonExpired() {
    return getCredentialsNonExpired();
  }

  public boolean isEnabled() {
    return getEnabled();
  }

  public void setAdmin(boolean isAdmin) {
    this.admin = isAdmin;
  }

  public boolean isAdmin() {
    return admin;
  }

}
