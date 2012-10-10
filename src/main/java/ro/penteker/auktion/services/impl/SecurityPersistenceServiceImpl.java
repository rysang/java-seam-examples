package ro.penteker.auktion.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

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

  @SuppressWarnings("unchecked")
  public List<AukRole> getPublicRoles() {
    Query q = sessionFactory.getCurrentSession().createQuery("select r from AukRole r where r.isPrivate = false");
    return q.list();
  }

  public AukUser getUser(String username) {
    Query q = sessionFactory.getCurrentSession()
        .createQuery("select u from AukUser u left join fetch u.aukRoleList r where u.username = :username")
        .setString("username", username);
    return (AukUser) q.uniqueResult();
  }

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

  @Override
  public List<AukUser> getUsers(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters) {

    Criteria crit = sessionFactory.getCurrentSession().createCriteria(AukUser.class);
    if (filters.size() > 0) {
      for (Entry<String, String> e : filters.entrySet()) {
        crit.add(Restrictions.ilike(e.getKey(), '%' + e.getValue() + '%'));
      }
    }

    if (sortField == null) {
      sortField = "username";
    }

    crit.addOrder(SortOrder.ASCENDING == sortOrder ? Order.asc(sortField) : Order.desc(sortField));
    crit.setFirstResult(first);
    crit.setMaxResults(pageSize);

    return crit.list();
  }

  @Override
  public List<AukRole> getRoles(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters) {

    Criteria crit = sessionFactory.getCurrentSession().createCriteria(AukRole.class);
    if (filters.size() > 0) {
      for (Entry<String, String> e : filters.entrySet()) {
        crit.add(Restrictions.ilike(e.getKey(), '%' + e.getValue() + '%'));
      }
    }

    if (sortField == null) {
      sortField = "authority";
    }

    crit.addOrder(SortOrder.ASCENDING == sortOrder ? Order.asc(sortField) : Order.desc(sortField));
    crit.setFirstResult(first);
    crit.setMaxResults(pageSize);

    return crit.list();
  }
}
