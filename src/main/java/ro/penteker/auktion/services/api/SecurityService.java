package ro.penteker.auktion.services.api;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukUser;

public interface SecurityService {

  public AukUser getUser(String username);

  public void createDefaultRoles();

  public AukUser createUser(String createdBy, String username, String password, boolean isAdmin);

  public List<AukUser> getUsers(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters);

}
