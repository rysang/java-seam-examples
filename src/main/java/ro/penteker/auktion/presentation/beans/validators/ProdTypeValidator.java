package ro.penteker.auktion.presentation.beans.validators;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ro.penteker.auktion.dao.AukType;
import ro.penteker.auktion.presentation.beans.admin.ProductActions;

public class ProdTypeValidator implements Validator {

  private transient ProductActions productActions;

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "auktion");

    for (AukType t : productActions.getCurrentProduct().getAukTypeList()) {
      if (t.getId().equals(value)) {
        FacesMessage msg = new FacesMessage(bundle.getString("ro.penteker.auktion.category.type.verif.failed"),
            bundle.getString("ro.penteker.auktion.category.type.exists"));
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
      }
    }

  }

  public void setProductActions(ProductActions productActions) {
    this.productActions = productActions;
  }

  public ProductActions getProductActions() {
    return productActions;
  }

}
