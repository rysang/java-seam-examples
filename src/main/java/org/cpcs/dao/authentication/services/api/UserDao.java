package org.cpcs.dao.authentication.services.api;

import org.cpcs.dao.authentication.user.AppUser;

public interface UserDao {

  public void save(AppUser appUser);
  
  public AppUser getByUsername(String username);

}
