package ro.penteker.auktion.presentation.beans.validators;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ro.penteker.auktion.dao.AukType;
import ro.penteker.auktion.presentation.beans.admin.CategoryActions;
import ro.penteker.auktion.services.api.CategoryService;

public class CategTypeValidator implements Validator {

  private transient CategoryService categoryService;

  private transient CategoryActions categoryActions;

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "auktion");

    for (AukType t : categoryActions.getCurrentCategory().getAukTypeList()) {
      if (t.getName().equals(value)) {
        FacesMessage msg = new FacesMessage(bundle.getString("ro.penteker.auktion.category.type.verif.failed"),
            bundle.getString("ro.penteker.auktion.category.type.exists"));
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
      }
    }

  }

  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  public CategoryService getCategoryService() {
    return categoryService;
  }

  public void setCategoryActions(CategoryActions categoryActions) {
    this.categoryActions = categoryActions;
  }

  public CategoryActions getCategoryActions() {
    return categoryActions;
  }

}
