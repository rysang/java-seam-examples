package ro.penteker.auktion.presentation.beans.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.dao.AukProduct;
import ro.penteker.auktion.dao.AukType;
import ro.penteker.auktion.security.SecurityContext;
import ro.penteker.auktion.services.api.CategoryService;
import ro.penteker.auktion.services.api.ProductService;

@Scope("session")
@Component("productActions")
public class ProductActions implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final transient Logger LOG = Logger.getLogger(ProductActions.class);

  @Autowired
  @Qualifier("securityContext")
  private SecurityContext securityContext;

  @Autowired
  @Qualifier("categoryService")
  private transient CategoryService categoryService;

  @Autowired
  @Qualifier("productService")
  private transient ProductService productService;

  private AukProduct currentProduct;

  private Long selectedCategoryId;

  private Long selectedTypeId;

  private List<AukCategory> categories;

  private List<AukType> types;

  public ProductActions() {

  }

  public String createProduct() {
    LOG.info("Creating product.");
    setCurrentProduct(new AukProduct());

    return "create-product";
  }

  public void setCurrentProduct(AukProduct currentProduct) {
    this.currentProduct = currentProduct;
  }

  public AukProduct getCurrentProduct() {
    return currentProduct;
  }

  public void addType() {
    LOG.info("Adding type: " + selectedTypeId);

    for (AukType t : getTypes()) {
      if (t.getId().equals(selectedTypeId)) {

        if (!currentProduct.getAukTypeList().contains(t)) {
          currentProduct.getAukTypeList().add(t);
          break;
        }
      }
    }
  }

  public String saveProduct() {
    currentProduct.setCreatedBy(securityContext.getCurrentUser().getUsername());
    currentProduct.setCreatedDate(new Date());

    productService.saveProduct(currentProduct);

    return "home-public";
  }

  public void deleteType(AukType type) {
    LOG.info("Removing type: " + type);
    currentProduct.getAukTypeList().remove(type);
  }

  public List<AukCategory> getCategories() {
    if (categories == null) {
      categories = categoryService.getCategoriesAndTypes();
      selectedCategoryId = categories.get(0).getId();
    }

    return categories;
  }

  public void setSelectedCategoryId(Long selectedCtegoryId) {
    this.selectedCategoryId = selectedCtegoryId;
    types = null;
  }

  public Long getSelectedCategoryId() {
    return selectedCategoryId;
  }

  public void setTypes(List<AukType> types) {
    this.types = types;
  }

  public List<AukType> getTypes() {
    if (types == null) {
      for (AukCategory c : getCategories()) {
        if (c.getId().equals(selectedCategoryId)) {
          types = c.getAukTypeList();
          break;
        }
      }
    }

    return types;
  }

  public void setSelectedTypeId(Long selectedTypeId) {
    this.selectedTypeId = selectedTypeId;
  }

  public Long getSelectedTypeId() {
    return selectedTypeId;
  }
}
