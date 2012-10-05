/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.penteker.auktion.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pricecr
 */
@Entity
@Table(name = "auk_right", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"role_id", "product_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AukRight.findAll", query = "SELECT a FROM AukRight a"),
    @NamedQuery(name = "AukRight.findById", query = "SELECT a FROM AukRight a WHERE a.id = :id"),
    @NamedQuery(name = "AukRight.findByRead", query = "SELECT a FROM AukRight a WHERE a.read = :read"),
    @NamedQuery(name = "AukRight.findByWrite", query = "SELECT a FROM AukRight a WHERE a.write = :write")})
public class AukRight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "read", nullable = false)
    private boolean read;
    @Basic(optional = false)
    @Column(name = "write", nullable = false)
    private boolean write;
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AukRole roleId;
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AukProduct productId;

    public AukRight() {
    }

    public AukRight(Long id) {
        this.id = id;
    }

    public AukRight(Long id, boolean read, boolean write) {
        this.id = id;
        this.read = read;
        this.write = write;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean getWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public AukRole getRoleId() {
        return roleId;
    }

    public void setRoleId(AukRole roleId) {
        this.roleId = roleId;
    }

    public AukProduct getProductId() {
        return productId;
    }

    public void setProductId(AukProduct productId) {
        this.productId = productId;
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
        if (!(object instanceof AukRight)) {
            return false;
        }
        AukRight other = (AukRight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ro.penteker.auktion.dao.AukRight[ id=" + id + " ]";
    }
    
}
