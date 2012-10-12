package ro.penteker.auktion.presentation.beans.admin.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukType;
import ro.penteker.auktion.presentation.beans.admin.ProductActions;
import ro.penteker.auktion.presentation.beans.admin.SecurityActions;

public class TypeConverter implements Converter {

  private ProductActions productActions;

  public TypeConverter() {

  }

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    // for (AukType r : productActions.getSourceRoles()) {
    // if (value.equals(r.getAuthority())) {
    // return r;
    // }
    // }
    //
    // for (AukRole r : securityActions.getTargetRoles()) {
    // if (value.equals(r.getAuthority())) {
    // return r;
    // }
    // }

    return null;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    return ((AukRole) value).getAuthority();
  }

  public void setProductActions(ProductActions productActions) {
    this.productActions = productActions;
  }

  public ProductActions getProductActions() {
    return productActions;
  }
}
