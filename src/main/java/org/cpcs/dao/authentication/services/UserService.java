package org.cpcs.dao.authentication.services;

import org.cpcs.dao.authentication.services.api.UserDao;
import org.cpcs.dao.authentication.user.AppUser;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class UserService implements UserDao {

  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public UserService() {

  }

  @Transactional
  public void save(AppUser appUser) {
    sessionFactory.getCurrentSession().save(appUser);
  }

  @Transactional(readOnly = true)
  public AppUser getByUsername(String username) {
    return (AppUser) sessionFactory.getCurrentSession().get(AppUser.class, username);
  }
}
