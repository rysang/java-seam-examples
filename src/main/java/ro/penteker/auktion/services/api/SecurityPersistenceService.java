package ro.penteker.auktion.services.api;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;

public interface SecurityPersistenceService {
  public AukUser getUser(String userName);

  public AukRole getRole(String roleName);

  public List<AukRole> getRoles();
  
  public List<AukRole> getPublicRoles();

  public Long saveUser(AukUser user);
  
  public void deleteUser(AukUser user);

  public Long saveRole(AukRole role);

  public void updateUser(AukUser user);

  public void updateRole(AukRole role);

  public List<AukUser> getUsers(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters);
}
