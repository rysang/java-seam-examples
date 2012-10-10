/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.penteker.auktion.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author pricecr
 */
@Entity
@Table(name = "auk_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "authority" }) })
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AukRole.findAll", query = "SELECT a FROM AukRole a"),
    @NamedQuery(name = "AukRole.findById", query = "SELECT a FROM AukRole a WHERE a.id = :id"),
    @NamedQuery(name = "AukRole.findByAuthority", query = "SELECT a FROM AukRole a WHERE a.authority = :authority"),
    @NamedQuery(name = "AukRole.findByCreatedDate", query = "SELECT a FROM AukRole a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "AukRole.findByCreatedBy", query = "SELECT a FROM AukRole a WHERE a.createdBy = :createdBy") })
public class AukRole implements GrantedAuthority, Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id", nullable = false)
  private Long id;
  @Basic(optional = false)
  @Column(name = "authority", nullable = false, length = 145)
  private String authority;
  @Basic(optional = false)
  @Column(name = "created_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  @Basic(optional = false)
  @Column(name = "created_by", nullable = false, length = 120)
  private String createdBy;

  @ManyToMany(mappedBy = "aukRoleList")
  private List<AukUser> aukUserList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId")
  private List<AukRight> aukRightList = new ArrayList<AukRight>();

  @Basic(optional = false)
  @Column(name = "private", nullable = false)
  private boolean isPrivate = false;

  public AukRole() {
  }

  public AukRole(Long id) {
    this.id = id;
  }

  public AukRole(Long id, String authority, Date createdDate, String createdBy) {
    this.id = id;
    this.authority = authority;
    this.createdDate = createdDate;
    this.createdBy = createdBy;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
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
  public List<AukUser> getAukUserList() {
    return aukUserList;
  }

  public void setAukUserList(List<AukUser> aukUserList) {
    this.aukUserList = aukUserList;
  }

  @XmlTransient
  public List<AukRight> getAukRightList() {
    return aukRightList;
  }

  public void setAukRightList(List<AukRight> aukRightList) {
    this.aukRightList = aukRightList;
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
    if (!(object instanceof AukRole)) {
      return false;
    }
    AukRole other = (AukRole) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ro.penteker.auktion.dao.AukRole[ id=" + id + " ]";
  }

  public void setPrivate(boolean isPrivate) {
    this.isPrivate = isPrivate;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public boolean getIsPrivate() {
    return isPrivate;
  }

}
