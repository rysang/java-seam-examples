/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cpcs.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author pricecr
 */
@Entity
@Table(name = "email_account")
@NamedQueries({
    @NamedQuery(name = "EmailAccount.findAll", query = "SELECT e FROM EmailAccount e"),
    @NamedQuery(name = "EmailAccount.findById", query = "SELECT e FROM EmailAccount e WHERE e.id = :id"),
    @NamedQuery(name = "EmailAccount.findByEmailUser", query = "SELECT e FROM EmailAccount e WHERE e.emailUser = :emailUser"),
    @NamedQuery(name = "EmailAccount.findByServerName", query = "SELECT e FROM EmailAccount e WHERE e.serverName = :serverName"),
    @NamedQuery(name = "EmailAccount.findByPort", query = "SELECT e FROM EmailAccount e WHERE e.port = :port")})
public class EmailAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "email_user", nullable = false, length = 255)
    private String emailUser;
    @Lob
    @Column(name = "description", length = 65535)
    private String description;
    @Basic(optional = false)
    @Column(name = "server_name", nullable = false, length = 150)
    private String serverName;
    @Basic(optional = false)
    @Column(name = "port", nullable = false)
    private int port;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emailAccountId")
    private List<Notification> notificationList;
    @JoinColumn(name = "user_login", referencedColumnName = "login", nullable = false)
    @ManyToOne(optional = false)
    private NotifUser userLogin;

    public EmailAccount() {
    }

    public EmailAccount(Integer id) {
        this.id = id;
    }

    public EmailAccount(Integer id, String emailUser, String serverName, int port) {
        this.id = id;
        this.emailUser = emailUser;
        this.serverName = serverName;
        this.port = port;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
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
        if (!(object instanceof EmailAccount)) {
            return false;
        }
        EmailAccount other = (EmailAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.cpcs.dao.EmailAccount[ id=" + id + " ]";
    }
    
}
