package ro.penteker.auktion.services.impl;

import ro.penteker.auktion.dao.AukProduct;
import ro.penteker.auktion.services.api.ProductPersistenceService;
import ro.penteker.auktion.services.api.ProductService;

public class ProductServiceImpl implements ProductService {

  private ProductPersistenceService productPersistenceService;

  public ProductServiceImpl() {
  }

  @Override
  public AukProduct saveProduct(AukProduct product) {
    return productPersistenceService.saveProduct(product);
  }

  public void setProductPersistenceService(ProductPersistenceService productPersistenceService) {
    this.productPersistenceService = productPersistenceService;
  }

  public ProductPersistenceService getProductPersistenceService() {
    return productPersistenceService;
  }
}
