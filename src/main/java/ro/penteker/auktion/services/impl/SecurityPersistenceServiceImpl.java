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

  @Override
  @SuppressWarnings("unchecked")
  public List<AukRole> getRoles() {
    Query q = sessionFactory.getCurrentSession().createQuery("select r from AukRole r");
    return q.list();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<AukRole> getPublicRoles() {
    Query q = sessionFactory.getCurrentSession().createQuery("select r from AukRole r where r.isPrivate = false");
    return q.list();
  }

  @Override
  public AukUser getUser(String username) {
    Query q = sessionFactory.getCurrentSession()
        .createQuery("select u from AukUser u left join fetch u.aukRoleList r where u.username = :username")
        .setString("username", username);
    return (AukUser) q.uniqueResult();
  }

  @Override
  public AukRole getRole(String roleName) {
    Query q = sessionFactory.getCurrentSession().createQuery("select r from AukRole r where r.authority = :authority")
        .setString("authority", roleName);
    return (AukRole) q.uniqueResult();
  }

  @Override
  public void deleteRole(AukRole role) {
    Query q = sessionFactory.getCurrentSession().createQuery("delete AukRole r where r.id = :id")
        .setLong("id", role.getId());
    q.executeUpdate();
  }

  @Override
  public Long saveUser(AukUser user) {
    return (Long) sessionFactory.getCurrentSession().save(user);
  }

  @Override
  public Long saveRole(AukRole role) {
    return (Long) sessionFactory.getCurrentSession().save(role);
  }

  @Override
  public void updateUser(AukUser user) {
    sessionFactory.getCurrentSession().update(user);
  }

  @Override
  public void updateRole(AukRole role) {
    sessionFactory.getCurrentSession().update(role);
  }

  // delete Customer c where c.name = :oldName
  @Override
  public void deleteUser(AukUser user) {
    Query q = sessionFactory.getCurrentSession().createQuery("delete AukUser u where u.id = :id")
        .setLong("id", user.getId());
    q.executeUpdate();
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

}
