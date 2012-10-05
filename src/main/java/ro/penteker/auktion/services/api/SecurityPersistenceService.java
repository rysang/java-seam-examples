package ro.penteker.auktion.services.api;

import java.util.List;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;

public interface SecurityPersistenceService {
  public AukUser getUser(String userName);

  public AukRole getRole(String roleName);
  
  public List<AukRole> getRoles();

  public Long saveUser(AukUser user);

  public Long saveRole(AukRole role);

  public void updateUser(AukUser user);

  public void updateRole(AukRole role);
}
