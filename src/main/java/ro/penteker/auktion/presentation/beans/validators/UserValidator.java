package ro.penteker.auktion.presentation.beans.validators;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ro.penteker.auktion.services.api.SecurityService;

public class UserValidator implements Validator {

  private transient SecurityService securityService;

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "auktion");

    if (securityService.getUser(value.toString()) != null) {

      FacesMessage msg = new FacesMessage(bundle.getString("ro.penteker.auktion.user.validation.user.verif.failed"),
          bundle.getString("ro.penteker.auktion.user.validation.user.exists"));
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ValidatorException(msg);

    }
  }

  public void setSecurityService(SecurityService securityService) {
    this.securityService = securityService;
  }

  public SecurityService getSecurityService() {
    return securityService;
  }

}
