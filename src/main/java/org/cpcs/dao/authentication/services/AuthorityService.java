package org.cpcs.dao.authentication.services;

import java.util.List;

import org.cpcs.dao.NotifRole;
import org.cpcs.dao.NotifUser;
import org.cpcs.dao.authentication.services.api.AuthorityDao;
import org.hibernate.Query;
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
  public void save(NotifRole appAuthority) {
    sessionFactory.getCurrentSession().save(appAuthority);
  }

  public List<NotifRole> getRolesForUser(NotifUser notifUser) {
    Query q = sessionFactory.getCurrentSession().createQuery("select r from NotifRole r where r.");
    
    return null;
  }

}
