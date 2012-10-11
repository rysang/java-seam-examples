package ro.penteker.auktion.services.api;

import java.util.List;

import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukProduct;
import ro.penteker.auktion.dao.AukType;

public interface ProductPersistenceService {

  public AukProduct saveProduct(AukProduct product);

  public List<AukProduct> getProducts(int first, int pageSize, String sortField, SortOrder sortOrder,
      List<AukType> types);
}
