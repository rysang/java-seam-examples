package ro.penteker.auktion.services.api;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;

public interface SecurityService {

  public AukUser getUser(String username);
  
  public List<AukRole> getPublicRoles();

  public List<AukRole> createDefaultRoles();
  
  public void deleteUser(AukUser user);

  public void updateUser(AukUser user);

  public AukUser createUser(String createdBy, String username, String password, boolean enabled, List<AukRole> roles);

  public List<AukUser> getUsers(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters);

}
