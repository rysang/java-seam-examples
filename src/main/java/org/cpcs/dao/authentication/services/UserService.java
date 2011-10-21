package org.cpcs.dao.authentication.services;

import org.cpcs.dao.NotifUser;
import org.cpcs.dao.authentication.services.api.UserDao;
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
  public void save(NotifUser appUser) {
    sessionFactory.getCurrentSession().save(appUser);
  }

  @Transactional(readOnly = true)
  public NotifUser getByUsername(String username) {
    return (NotifUser) sessionFactory.getCurrentSession().get(NotifUser.class, username);
  }
}
