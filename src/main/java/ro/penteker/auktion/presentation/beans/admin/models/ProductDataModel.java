package ro.penteker.auktion.presentation.beans.admin.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ro.penteker.auktion.dao.AukProduct;
import ro.penteker.auktion.dao.AukType;
import ro.penteker.auktion.services.api.ProductService;

@Scope("session")
@Component("productDataModel")
public class ProductDataModel extends LazyDataModel<AukProduct> implements Serializable {

  private static final long serialVersionUID = 2654016626874491269L;

  private static final transient Logger LOG = Logger.getLogger(ProductDataModel.class);
  private List<AukProduct> products;

  private List<AukType> types = new ArrayList<AukType>();

  @Autowired
  @Qualifier("productService")
  private transient ProductService productService;

  public ProductDataModel() {

  }

  @Override
  public List<AukProduct> load(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters) {
    LOG.info("Query : first=" + first + " pageSize=" + pageSize + " sortField=" + sortField + " sortOrder=" + sortOrder);
    products = productService.getProducts(first, pageSize, sortField, sortOrder, types);
    setRowCount(products.size());

    return products;
  }

  @Override
  public AukProduct getRowData(String rowKey) {
    for (AukProduct u : products) {
      if (u.getId().equals(new Long(rowKey))) {
        return u;
      }
    }

    return null;
  }

  @Override
  public Object getRowKey(AukProduct object) {
    return object.getId();
  }

  public void setTypes(List<AukType> types) {
    this.types = types;
  }

  public List<AukType> getTypes() {
    return types;
  }

}
