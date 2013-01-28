package ro.penteker.auktion.presentation.beans.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.dao.AukType;
import ro.penteker.auktion.security.SecurityContext;
import ro.penteker.auktion.services.api.CategoryService;

@Scope("session")
@Component("categoryActions")
public class CategoryActions implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final transient Logger LOG = Logger.getLogger(CategoryActions.class);

  private AukCategory currentCategory;

  private String newType;

  @Autowired
  @Qualifier("categoryService")
  private transient CategoryService categoryService;

  @Autowired
  @Qualifier("securityContext")
  private SecurityContext securityContext;

  private List<AukType> removalList = null;

  private List<AukCategory> categories = null;

  // private List<AukType> selectedTypes = new ArrayList<AukType>();

  public CategoryActions() {

  }

  public void typeSelect(AjaxBehaviorEvent ev) {
    AukType type = (AukType) ev.getComponent().getAttributes().get("type");
    LOG.info("Select type: " + type + " name=" + type.getName() + " selected=" + type.isSelected());

  }

  public void addType() {
    LOG.info("Type Added: " + newType);

    currentCategory.getAukTypeList().add(
        new AukType(null, newType, new Date(), securityContext.getCurrentUser().getUsername()));
    newType = null;
  }

  public void deleteType(AukType type) {
    LOG.info("Removing type: " + type);

    getRemovalList().add(type);
    currentCategory.getAukTypeList().remove(type);
  }

  public String editCategory(AukCategory category) {
    LOG.info("Edit category: " + category);
    reset();

    currentCategory = categoryService.getCategoryAndTypes(category.getName());

    return "edit-category";
  }

  public String deleteCategory(AukCategory category) {
    LOG.info("Delete category: " + category);

    categoryService.deleteCategory(category);

    return "home-secure";
  }

  public void setCurrentCategory(AukCategory currentCategory) {
    this.currentCategory = currentCategory;
  }

  public AukCategory getCurrentCategory() {
    return currentCategory;
  }

  public String createCategory() {
    LOG.info("Creating new category.");
    setCurrentCategory(new AukCategory());

    return "create-category";
  }

  public String saveCategory() {
    if (currentCategory.getId() == null) {
      LOG.info("Creating new category.");

      currentCategory.setCreatedBy(securityContext.getCurrentUser().getUsername());
      currentCategory.setCreatedDate(new Date());
      categoryService.createCategory(currentCategory);
    }

    else {
      categoryService.updateCategory(currentCategory, removalList);
    }

    return "home-secure";
  }

  public void setNewType(String newType) {
    this.newType = newType;
  }

  public String getNewType() {
    return newType;
  }

  public void setRemovalList(List<AukType> removalList) {
    this.removalList = removalList;
  }

  public void reset() {
    removalList = null;
  }

  public List<AukType> getRemovalList() {
    if (removalList == null) {
      removalList = new ArrayList<AukType>();
    }

    return removalList;
  }

  public void setCategories(List<AukCategory> categories) {
    this.categories = categories;
  }

  public List<AukCategory> getCategories() {
    if (categories == null) {
      categories = categoryService.getCategoriesAndTypes();
    }

    return categories;
  }

}
