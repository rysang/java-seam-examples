package ro.penteker.auktion.services.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityPersistenceService;

public class SecurityPersistenceServiceImpl implements SecurityPersistenceService {
  private static final transient Logger LOG = Logger.getLogger(SecurityPersistenceServiceImpl.class);

  private SessionFactory sessionFactory;

  public SecurityPersistenceServiceImpl() {
    LOG.info("Init: " + this.getClass());
  }

  @SuppressWarnings("unchecked")
  public List<AukRole> getRoles() {
    Query q = sessionFactory.getCurrentSession().createQuery("select r from AukRole r");
    return q.list();
  }

  public AukUser getUser(String username) {
    Query q = sessionFactory.getCurrentSession()
        .createQuery("select u from AukUser u join u.aukRoleList r where u.username = :username")
        .setString("username", username);
    return (AukUser) q.uniqueResult();
  }

  public AukRole getRole(String roleName) {
    Query q = sessionFactory.getCurrentSession().createQuery("select r from AukRole r where r.authority = :authority")
        .setString("authority", roleName);
    return (AukRole) q.uniqueResult();
  }

  public Long saveUser(AukUser user) {
    return (Long) sessionFactory.getCurrentSession().save(user);
  }

  public Long saveRole(AukRole role) {
    return (Long) sessionFactory.getCurrentSession().save(role);
  }

  public void updateUser(AukUser user) {
    sessionFactory.getCurrentSession().update(user);
  }

  public void updateRole(AukRole role) {
    sessionFactory.getCurrentSession().update(role);
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
