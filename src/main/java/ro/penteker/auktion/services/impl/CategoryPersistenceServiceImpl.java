package ro.penteker.auktion.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.dao.AukType;
import ro.penteker.auktion.services.api.CategoryPersistenceService;

public class CategoryPersistenceServiceImpl implements CategoryPersistenceService {

  private SessionFactory sessionFactory;

  public CategoryPersistenceServiceImpl() {

  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public AukCategory getCategory(String name) {
    Query query = sessionFactory.getCurrentSession().createQuery("select c from AukCategory c where c.name = :name")
        .setString("name", name);
    return (AukCategory) query.uniqueResult();
  }

  @Override
  public AukCategory getCategoryAndTypes(String name) {
    Query query = sessionFactory.getCurrentSession()
        .createQuery("select c from AukCategory c left join fetch c.aukTypeList where c.name = :name")
        .setString("name", name);
    return (AukCategory) query.uniqueResult();
  }

  @Override
  public AukCategory saveCategory(AukCategory category) {
    Long id = (Long) sessionFactory.getCurrentSession().save(category);
    category.setId(id);
    return category;
  }

  @Override
  public AukType saveType(AukType type) {
    Long id = (Long) sessionFactory.getCurrentSession().save(type);
    type.setId(id);
    return type;
  }

  @Override
  public void deleteType(AukType type) {
    Query q = sessionFactory.getCurrentSession().createQuery("delete AukType t where t.id = :id")
        .setLong("id", type.getId());
    q.executeUpdate();
  }

  @Override
  public void updateCategory(AukCategory category) {
    sessionFactory.getCurrentSession().update(category);
  }

  @Override
  public void updateType(AukType type) {
    sessionFactory.getCurrentSession().update(type);
  }

  @Override
  public void deleteCategory(AukCategory category) {

    Query q = sessionFactory.getCurrentSession().createQuery("delete AukCategory c where c.id = :id")
        .setLong("id", category.getId());
    q.executeUpdate();
  }

  @Override
  public List<AukCategory> getCategories(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters) {

    Criteria crit = sessionFactory.getCurrentSession().createCriteria(AukCategory.class);
    if (filters.size() > 0) {
      for (Entry<String, String> e : filters.entrySet()) {
        crit.add(Restrictions.ilike(e.getKey(), '%' + e.getValue() + '%'));
      }
    }

    if (sortField == null) {
      sortField = "name";
    }

    crit.addOrder(SortOrder.ASCENDING == sortOrder ? Order.asc(sortField) : Order.desc(sortField));
    crit.setFirstResult(first);
    crit.setMaxResults(pageSize);

    return crit.list();
  }

  @Override
  public AukType getType(AukCategory category, String name) {
    Query query = sessionFactory.getCurrentSession()
        .createQuery("select c from AukType t where t.name = :name and t.category.id = :id").setString("name", name)
        .setLong("id", category.getId());
    return (AukType) query.uniqueResult();
  }
}
