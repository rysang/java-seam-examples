package org.spring.jsf.app.dao.authentication.services;

import org.hibernate.SessionFactory;
import org.spring.jsf.app.dao.authentication.services.api.UserDao;
import org.spring.jsf.app.dao.authentication.user.AppUser;
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
