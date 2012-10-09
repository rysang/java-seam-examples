package ro.penteker.auktion.presentation.beans.admin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.presentation.beans.admin.SecurityActions;

public class RoleConverter implements Converter {

  private SecurityActions securityActions;

  public RoleConverter() {

  }

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    for (AukRole r : securityActions.getSourceRoles()) {
      if (value.equals(r.getAuthority())) {
        return r;
      }
    }

    for (AukRole r : securityActions.getTargetRoles()) {
      if (value.equals(r.getAuthority())) {
        return r;
      }
    }

    return null;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    return ((AukRole) value).getAuthority();
  }

  public void setSecurityActions(SecurityActions securityActions) {
    this.securityActions = securityActions;
  }

  public SecurityActions getSecurityActions() {
    return securityActions;
  }
}
