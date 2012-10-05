/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.penteker.auktion.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author pricecr
 */
@Entity
@Table(name = "auk_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AukRoles.findAll", query = "SELECT a FROM AukRoles a"),
    @NamedQuery(name = "AukRoles.findById", query = "SELECT a FROM AukRoles a WHERE a.id = :id"),
    @NamedQuery(name = "AukRoles.findByAuthority", query = "SELECT a FROM AukRoles a WHERE a.authority = :authority"),
    @NamedQuery(name = "AukRoles.findByCreatedDate", query = "SELECT a FROM AukRoles a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "AukRoles.findByCreatedBy", query = "SELECT a FROM AukRoles a WHERE a.createdBy = :createdBy") })
public class AukRoles implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
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
  @JoinTable(name = "auk_role_2_user", joinColumns = { @JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false) })
  @ManyToMany(fetch = FetchType.LAZY)
  private List<AukUsers> aukUsersList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId", fetch = FetchType.LAZY)
  private List<AukRights> aukRightsList;

  public AukRoles() {
  }

  public AukRoles(Long id) {
    this.id = id;
  }

  public AukRoles(Long id, String authority, Date createdDate, String createdBy) {
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
  public List<AukUsers> getAukUsersList() {
    return aukUsersList;
  }

  public void setAukUsersList(List<AukUsers> aukUsersList) {
    this.aukUsersList = aukUsersList;
  }

  @XmlTransient
  public List<AukRights> getAukRightsList() {
    return aukRightsList;
  }

  public void setAukRightsList(List<AukRights> aukRightsList) {
    this.aukRightsList = aukRightsList;
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
    if (!(object instanceof AukRoles)) {
      return false;
    }
    AukRoles other = (AukRoles) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ro.penteker.auktion.dao.AukRoles[ id=" + id + " ]";
  }

}
