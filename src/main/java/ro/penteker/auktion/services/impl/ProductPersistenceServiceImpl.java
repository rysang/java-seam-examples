package ro.penteker.auktion.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukProduct;
import ro.penteker.auktion.dao.AukType;
import ro.penteker.auktion.services.api.ProductPersistenceService;

public class ProductPersistenceServiceImpl implements ProductPersistenceService {

  private SessionFactory sessionFactory;

  public ProductPersistenceServiceImpl() {
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public AukProduct saveProduct(AukProduct product) {
    Long id = (Long) sessionFactory.getCurrentSession().save(product);
    product.setId(id);

    return product;
  }

  public List<AukProduct> getProducts(int first, int pageSize, String sortField, SortOrder sortOrder,
      List<AukType> types) {
    if (sortField == null) {
      sortField = "createdDate";
      if (sortOrder == SortOrder.UNSORTED) {
        sortOrder = SortOrder.DESCENDING;
      }
    }

    List<Long> ids = new ArrayList<Long>(types.size());
    for (AukType t : types) {
      ids.add(t.getId());
    }

    final StringBuilder qStr = new StringBuilder(
        "select distinct p from AukProduct p join fetch p.aukTypeList t where 1=1 ");

    qStr.append(types.size() > 0 ? " and t.id in ( :idList) " : " ");
    qStr.append(" order by ").append("p.").append(sortField).append(' ')
        .append(sortOrder == SortOrder.DESCENDING ? "desc" : "asc");

    Query query = sessionFactory.getCurrentSession().createQuery(qStr.toString());
    if (types.size() > 0) {
      query.setParameterList("idList", ids);
    }

    query.setFirstResult(first);
    query.setMaxResults(pageSize);

    return query.list();
  }
}
