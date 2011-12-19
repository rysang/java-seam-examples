package org.cpcs.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.RoleServiceImpl;
import org.cpcs.dao.services.api.MemberStateService;
import org.cpcs.dao.services.api.RoleService;
import org.cpcs.dao.services.beans.MemberState;
import org.cpcs.dao.services.beans.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component("roleActions")
public class RoleActions {

  private static final Logger LOG = Logger.getLogger(RoleActions.class);

  @Autowired
  @Qualifier("roleDao")
  private RoleService roleDao;

  public RoleActions() {

  }

  public List<SelectItem> getRoles() {
    List<Role> roles = roleDao.listRoles();
    ArrayList<SelectItem> ret = new ArrayList<SelectItem>(roles.size());

    for (Role role : roles) {
      if (!role.isInternalRole())
        ret.add(new SelectItem(role.getId(), role.getCode()));
    }

    return ret;
  }

}
