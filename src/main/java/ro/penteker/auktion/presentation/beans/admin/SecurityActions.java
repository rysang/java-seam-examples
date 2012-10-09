package ro.penteker.auktion.presentation.beans.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityService;

@Scope("session")
@Component("securityActions")
public class SecurityActions implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final transient Logger LOG = Logger.getLogger(SecurityActions.class);
  private int tabIndex;

  private AukUser currentUser;

  @Autowired
  @Qualifier("securityService")
  private transient SecurityService securityService;

  @Autowired
  @Qualifier("currentUser")
  private UserDetails loggedInUser;

  private List<AukRole> sourceRoles = null;
  private List<AukRole> targetRoles = null;

  private DualListModel<AukRole> roleDualListModel;

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

  protected void reset() {
    roleDualListModel = null;
    sourceRoles = null;
    targetRoles = null;
  }

  public void onTransfer(TransferEvent event) {
    for (AukRole item : (List<AukRole>) event.getItems()) {
      if (event.isAdd()) {
        LOG.info("Adding roles: " + event.getItems());
        getTargetRoles().add(item);
        getSourceRoles().remove(item);
      } else if (event.isRemove()) {
        LOG.info("Removing roles: " + event.getItems());
        getSourceRoles().add(item);
        getTargetRoles().remove(item);
      }
    }
  }

  public void deleteUser(AukUser user) {
    securityService.deleteUser(user);
  }

  public String editUser(AukUser user) {
    reset();

    setCurrentUser(securityService.getUser(user.getUsername()));
    return "edit-user";
  }

  public String createUser() {
    reset();

    setCurrentUser(new AukUser());
    return "create-user";
  }

  public String saveUser() {
    if (currentUser.getId() == null) {
      currentUser = securityService.createUser(loggedInUser.getUsername(), currentUser.getUsername(),
          currentUser.getPassword(), currentUser.getEnabled(), getTargetRoles());
    } else {
      currentUser.getAukRoleList().clear();
      currentUser.getAukRoleList().addAll(getTargetRoles());
      securityService.updateUser(currentUser);
    }

    return "home-secure";
  }

  public String closeUserEditCreate() {
    return "home-secure";
  }

  public void setCurrentUser(AukUser currentUser) {
    this.currentUser = currentUser;
  }

  public AukUser getCurrentUser() {
    return currentUser;
  }

  public List<AukRole> getSourceRoles() {
    if (sourceRoles == null) {
      sourceRoles = securityService.getPublicRoles();
    }

    return sourceRoles;
  }

  public List<AukRole> getTargetRoles() {
    if (targetRoles == null) {
      targetRoles = new ArrayList<AukRole>(currentUser.getAukRoleList());
    }

    return targetRoles;
  }

  public void setRoleDualListModel(DualListModel<AukRole> roleDualListModel) {
    this.roleDualListModel = roleDualListModel;
  }

  public DualListModel<AukRole> getRoleDualListModel() {
    if (roleDualListModel == null) {
      Iterator<AukRole> srcRolesIt = getSourceRoles().iterator();
      while (srcRolesIt.hasNext()) {
        AukRole r = srcRolesIt.next();
        if (getTargetRoles().contains(r)) {
          srcRolesIt.remove();
        }
      }

      roleDualListModel = new DualListModel<AukRole>(getSourceRoles(), getTargetRoles());
    }

    return roleDualListModel;
  }

}
