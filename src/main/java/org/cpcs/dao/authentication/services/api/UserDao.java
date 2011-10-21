package org.cpcs.dao.authentication.services.api;

import org.cpcs.dao.NotifUser;

public interface UserDao {

  public void save(NotifUser appUser);

  public NotifUser getByUsername(String username);

}
