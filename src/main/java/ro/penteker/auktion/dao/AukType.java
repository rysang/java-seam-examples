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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "auk_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AukType.findAll", query = "SELECT a FROM AukType a"),
    @NamedQuery(name = "AukType.findById", query = "SELECT a FROM AukType a WHERE a.id = :id"),
    @NamedQuery(name = "AukType.findByName", query = "SELECT a FROM AukType a WHERE a.name = :name"),
    @NamedQuery(name = "AukType.findByCreatedDate", query = "SELECT a FROM AukType a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "AukType.findByCreatedBy", query = "SELECT a FROM AukType a WHERE a.createdBy = :createdBy") })
public class AukType implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id", nullable = false)
  private Long id;
  @Basic(optional = false)
  @Column(name = "name", nullable = false, length = 120)
  private String name;
  @Basic(optional = false)
  @Column(name = "created_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  @Basic(optional = false)
  @Column(name = "created_by", nullable = false, length = 120)
  private String createdBy;
  @Lob
  @Column(name = "description", length = 2147483647)
  private String description;
  @ManyToMany(mappedBy = "aukTypeList")
  private List<AukProduct> aukProductList;
  @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
  @ManyToOne(optional = false)
  private AukCategory category;

  public AukType() {
  }

  public AukType(Long id) {
    this.id = id;
  }

  public AukType(Long id, String name, Date createdDate, String createdBy) {
    this.id = id;
    this.name = name;
    this.createdDate = createdDate;
    this.createdBy = createdBy;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @XmlTransient
  public List<AukProduct> getAukProductList() {
    return aukProductList;
  }

  public void setAukProductList(List<AukProduct> aukProductList) {
    this.aukProductList = aukProductList;
  }

  public void setCategory(AukCategory category) {
    this.category = category;
  }

  public AukCategory getCategory() {
    return category;
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
    if (!(object instanceof AukType)) {
      return false;
    }
    AukType other = (AukType) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ro.penteker.auktion.dao.AukType[ id=" + id + " ]";
  }

}
