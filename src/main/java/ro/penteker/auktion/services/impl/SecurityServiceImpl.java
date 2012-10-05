package ro.penteker.auktion.services.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityService;

public class SecurityServiceImpl implements SecurityService {

  private SessionFactory sessionFactory;

  public AukUser getUser(String username) {
    Query q = sessionFactory.getCurrentSession().createQuery("select u from AukUser u where u.username = :username")
        .setString("username", username);
    return (AukUser) q.uniqueResult();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

}
