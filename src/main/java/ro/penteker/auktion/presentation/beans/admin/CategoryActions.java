package ro.penteker.auktion.presentation.beans.admin;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.dao.AukType;
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
  @Qualifier("currentUser")
  private UserDetails loggedInUser;

  public CategoryActions() {

  }

  public void addType() {
    LOG.info("Type Added: " + newType);

    currentCategory.getAukTypeList().add(new AukType(null, newType, new Date(), loggedInUser.getUsername()));
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
    return "home-secure";
  }

  public void setNewType(String newType) {
    this.newType = newType;
  }

  public String getNewType() {
    return newType;
  }

}
