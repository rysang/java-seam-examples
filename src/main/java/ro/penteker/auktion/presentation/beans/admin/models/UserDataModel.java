package ro.penteker.auktion.presentation.beans.admin.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityService;

@SessionScoped
@ManagedBean(name = "userDataModel")
public class UserDataModel extends LazyDataModel<AukUser> implements Serializable {

  private static final long serialVersionUID = 2654016626874491269L;

  private static final transient Logger LOG = Logger.getLogger(UserDataModel.class);
  private List<AukUser> users;

  @ManagedProperty("#{securityService}")
  private transient SecurityService securityService;

  public UserDataModel() {

  }

  @Override
  public List<AukUser> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
    LOG.info("Query : first=" + first + " pageSize=" + pageSize + " sortField=" + sortField + " sortOrder=" + sortOrder
        + " filters=" + filters);
    users = securityService.getUsers(first, pageSize, sortField, sortOrder, filters);
    setRowCount(users.size());

    return users;
  }

  public void setSecurityService(SecurityService securityService) {
    this.securityService = securityService;
  }

  public SecurityService getSecurityService() {
    return securityService;
  }

  @Override
  public AukUser getRowData(String rowKey) {
    for (AukUser u : users) {
      if (u.getId().equals(new Long(rowKey))) {
        return u;
      }
    }

    return null;
  }

  @Override
  public Object getRowKey(AukUser object) {
    return object.getId();
  }

}
