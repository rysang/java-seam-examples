package org.cpcs.dao.authentication.services.api;

import java.util.List;

import org.cpcs.dao.NotifRole;
import org.cpcs.dao.NotifUser;

public interface AuthorityDao {
  public void save(NotifRole appAuthority);
  
  public List<NotifRole> getRolesForUser(NotifUser notifUser);
}
