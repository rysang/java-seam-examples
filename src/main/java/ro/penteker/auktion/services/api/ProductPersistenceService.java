package ro.penteker.auktion.services.api;

import ro.penteker.auktion.dao.AukProduct;

public interface ProductPersistenceService {

  public AukProduct saveProduct(AukProduct product);

}
