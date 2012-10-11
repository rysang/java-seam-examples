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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
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
@Table(name = "auk_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AukProduct.findAll", query = "SELECT a FROM AukProduct a"),
    @NamedQuery(name = "AukProduct.findById", query = "SELECT a FROM AukProduct a WHERE a.id = :id"),
    @NamedQuery(name = "AukProduct.findByName", query = "SELECT a FROM AukProduct a WHERE a.name = :name"),
    @NamedQuery(name = "AukProduct.findByCreatedDate", query = "SELECT a FROM AukProduct a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "AukProduct.findByCreatedBy", query = "SELECT a FROM AukProduct a WHERE a.createdBy = :createdBy"),
    @NamedQuery(name = "AukProduct.findByPrice", query = "SELECT a FROM AukProduct a WHERE a.price = :price"),
    @NamedQuery(name = "AukProduct.findByCurrency", query = "SELECT a FROM AukProduct a WHERE a.currency = :currency") })
public class AukProduct implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id", nullable = false)
  private Long id;
  @Basic(optional = false)
  @Column(name = "name", nullable = false, length = 255)
  private String name;
  @Basic(optional = false)
  @Lob
  @Column(name = "description", nullable = false, length = 2147483647)
  private String description;
  @Basic(optional = false)
  @Column(name = "created_date", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  @Basic(optional = false)
  @Column(name = "created_by", nullable = false, length = 120)
  private String createdBy;
  @Basic(optional = false)
  @Column(name = "price", nullable = false)
  private int price;
  @Basic(optional = false)
  @Column(name = "currency", nullable = false, length = 10)
  private String currency;

  @JoinTable(name = "auk_product_2_type", joinColumns = { @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false) })
  @ManyToMany
  private List<AukType> aukTypeList;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
  private List<AukRight> aukRightList;

  public AukProduct() {
  }

  public AukProduct(Long id) {
    this.id = id;
  }

  public AukProduct(Long id, String name, String description, Date createdDate, String createdBy, int price,
      String currency) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdDate = createdDate;
    this.createdBy = createdBy;
    this.price = price;
    this.currency = currency;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @XmlTransient
  public List<AukType> getAukTypeList() {
    return aukTypeList;
  }

  public void setAukTypeList(List<AukType> aukTypeList) {
    this.aukTypeList = aukTypeList;
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
    if (!(object instanceof AukProduct)) {
      return false;
    }
    AukProduct other = (AukProduct) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ro.penteker.auktion.dao.AukProduct[ id=" + id + " ]";
  }

}
