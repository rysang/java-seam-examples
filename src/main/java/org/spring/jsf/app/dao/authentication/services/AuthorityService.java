package org.spring.jsf.app.dao.authentication.services;

import org.hibernate.SessionFactory;
import org.spring.jsf.app.dao.authentication.services.api.AuthorityDao;
import org.spring.jsf.app.dao.authentication.user.AppAuthority;
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
