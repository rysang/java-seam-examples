package ro.penteker.auktion.services.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import ro.penteker.auktion.dao.AukProduct;
import ro.penteker.auktion.services.api.ProductPersistenceService;

public class ProductPersistenceServiceImpl implements ProductPersistenceService {

  private SessionFactory sessionFactory;

  private static transient final Logger LOG = Logger.getLogger(ProductPersistenceServiceImpl.class);

  public ProductPersistenceServiceImpl() {
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public AukProduct saveProduct(AukProduct product) {
    Long id = (Long) sessionFactory.getCurrentSession().save(product);
    product.setId(id);

    return product;
  }

}
