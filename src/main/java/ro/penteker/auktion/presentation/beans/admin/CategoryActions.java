package ro.penteker.auktion.presentation.beans.admin;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.services.api.CategoryService;

@Scope("session")
@Component("categoryActions")
public class CategoryActions implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final transient Logger LOG = Logger.getLogger(CategoryActions.class);

  private AukCategory currentCategory;

  @Autowired
  @Qualifier("categoryService")
  private transient CategoryService categoryService;

  public CategoryActions() {

  }

  public void setCurrentCategory(AukCategory currentCategory) {
    this.currentCategory = currentCategory;
  }

  public AukCategory getCurrentCategory() {
    return currentCategory;
  }

  public String createCategory() {
    LOG.info("Creating new category.");

    return "create-category";
  }

  public String saveCategory() {
    return "home-secure";
  }

}
