package ro.penteker.auktion.presentation.beans.admin;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.event.TabChangeEvent;

import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.security.SecurityContext;
import ro.penteker.auktion.services.api.SecurityService;

@SessionScoped
@ManagedBean(name = "securityActions")
public class SecurityActions implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final transient Logger LOG = Logger.getLogger(SecurityActions.class);
  private int tabIndex;

  private AukUser currentUser;

  @ManagedProperty("#{securityService}")
  private transient SecurityService securityService;

  @ManagedProperty("#{securityContext}")
  private transient SecurityContext securityContext;

  public SecurityActions() {

  }

  public void onTabChange(TabChangeEvent event) {
    tabIndex = new Integer(event.getTab().getId().substring(3));
    LOG.info("Tab changed to: " + tabIndex);
  }

  public void setTabIndex(int tabIndex) {
    this.tabIndex = tabIndex;
  }

  public int getTabIndex() {
    return tabIndex;
  }

  public String createUser() {
    setCurrentUser(new AukUser());
    return "create-edit-user";
  }

  public String saveUser() {
    currentUser = securityService.createUser(securityContext.getCurrentUser().getUsername(), currentUser.getUsername(),
        currentUser.getPassword(), currentUser.isAdmin());

    return "homesecure";
  }

  public String closeUserEditCreate() {
    return "homesecure";
  }

  public void setCurrentUser(AukUser currentUser) {
    this.currentUser = currentUser;
  }

  public AukUser getCurrentUser() {
    return currentUser;
  }

  public void setSecurityService(SecurityService securityService) {
    this.securityService = securityService;
  }

  public SecurityService getSecurityService() {
    return securityService;
  }

  public void setSecurityContext(SecurityContext securityContext) {
    this.securityContext = securityContext;
  }

  public SecurityContext getSecurityContext() {
    return securityContext;
  }
}
