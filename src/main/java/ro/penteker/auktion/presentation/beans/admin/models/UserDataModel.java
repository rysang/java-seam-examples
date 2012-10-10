package ro.penteker.auktion.presentation.beans.admin.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityService;

@Scope("session")
@Component("userDataModel")
public class UserDataModel extends LazyDataModel<AukUser> implements Serializable {

  private static final long serialVersionUID = 2654016626874491269L;

  private static final transient Logger LOG = Logger.getLogger(UserDataModel.class);
  private List<AukUser> users;

  @Autowired
  @Qualifier("securityService")
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
