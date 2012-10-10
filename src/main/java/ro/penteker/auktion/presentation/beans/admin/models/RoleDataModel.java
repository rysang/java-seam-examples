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

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.services.api.SecurityService;

@Scope("session")
@Component("roleDataModel")
public class RoleDataModel extends LazyDataModel<AukRole> implements Serializable {

  private static final long serialVersionUID = 2654016626874491269L;

  private static final transient Logger LOG = Logger.getLogger(RoleDataModel.class);
  private List<AukRole> roles;

  @Autowired
  @Qualifier("securityService")
  private transient SecurityService securityService;

  public RoleDataModel() {

  }

  @Override
  public List<AukRole> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
    LOG.info("Query : first=" + first + " pageSize=" + pageSize + " sortField=" + sortField + " sortOrder=" + sortOrder
        + " filters=" + filters);
    roles = securityService.getRoles(first, pageSize, sortField, sortOrder, filters);
    setRowCount(roles.size());

    return roles;
  }

  @Override
  public AukRole getRowData(String rowKey) {
    for (AukRole u : roles) {
      if (u.getId().equals(new Long(rowKey))) {
        return u;
      }
    }

    return null;
  }

  @Override
  public Object getRowKey(AukRole object) {
    return object.getId();
  }

}
