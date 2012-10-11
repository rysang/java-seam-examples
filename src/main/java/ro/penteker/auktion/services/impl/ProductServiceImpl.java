package ro.penteker.auktion.services.impl;

import java.util.List;

import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukProduct;
import ro.penteker.auktion.dao.AukType;
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

  @Override
  public List<AukProduct> getProducts(int first, int pageSize, String sortField, SortOrder sortOrder,
      List<AukType> types) {
    return productPersistenceService.getProducts(first, pageSize, sortField, sortOrder, types);
  }

  public void setProductPersistenceService(ProductPersistenceService productPersistenceService) {
    this.productPersistenceService = productPersistenceService;
  }

  public ProductPersistenceService getProductPersistenceService() {
    return productPersistenceService;
  }
}
