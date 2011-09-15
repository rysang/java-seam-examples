package org.cpcs.dao.authentication.services;

import org.cpcs.dao.authentication.services.api.AuthorityDao;
import org.cpcs.dao.authentication.user.AppAuthority;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class AuthorityService implements AuthorityDao {

  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public AuthorityService() {

  }

  @Transactional
  public void save(AppAuthority appAuthority) {
    sessionFactory.getCurrentSession().save(appAuthority);
  }

}
