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

/**
 *
 * @author pricecr
 */
@Entity
@Table(name = "google_cal_acc")
@NamedQueries({
    @NamedQuery(name = "GoogleCalAcc.findAll", query = "SELECT g FROM GoogleCalAcc g"),
    @NamedQuery(name = "GoogleCalAcc.findById", query = "SELECT g FROM GoogleCalAcc g WHERE g.id = :id"),
    @NamedQuery(name = "GoogleCalAcc.findByGoogleLogin", query = "SELECT g FROM GoogleCalAcc g WHERE g.googleLogin = :googleLogin"),
    @NamedQuery(name = "GoogleCalAcc.findByGooglePassword", query = "SELECT g FROM GoogleCalAcc g WHERE g.googlePassword = :googlePassword"),
    @NamedQuery(name = "GoogleCalAcc.findByUrl", query = "SELECT g FROM GoogleCalAcc g WHERE g.url = :url")})
public class GoogleCalAcc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "google_login", nullable = false, length = 255)
    private String googleLogin;
    @Basic(optional = false)
    @Column(name = "google_password", nullable = false, length = 150)
    private String googlePassword;
    @Basic(optional = false)
    @Column(name = "url", nullable = false, length = 255)
    private String url;
    @JoinColumn(name = "user_login", referencedColumnName = "login", nullable = false)
    @ManyToOne(optional = false)
    private NotifUser userLogin;

    public GoogleCalAcc() {
    }

    public GoogleCalAcc(Integer id) {
        this.id = id;
    }

    public GoogleCalAcc(Integer id, String googleLogin, String googlePassword, String url) {
        this.id = id;
        this.googleLogin = googleLogin;
        this.googlePassword = googlePassword;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoogleLogin() {
        return googleLogin;
    }

    public void setGoogleLogin(String googleLogin) {
        this.googleLogin = googleLogin;
    }

    public String getGooglePassword() {
        return googlePassword;
    }

    public void setGooglePassword(String googlePassword) {
        this.googlePassword = googlePassword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(object instanceof GoogleCalAcc)) {
            return false;
        }
        GoogleCalAcc other = (GoogleCalAcc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cpcs.dao.GoogleCalAcc[ id=" + id + " ]";
    }
    
}
