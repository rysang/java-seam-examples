package org.spring.jsf.app.dao.authentication.services.api;

import org.spring.jsf.app.dao.authentication.user.AppUser;

public interface UserDao {

  public void save(AppUser appUser);
  
  public AppUser getByUsername(String username);

}
