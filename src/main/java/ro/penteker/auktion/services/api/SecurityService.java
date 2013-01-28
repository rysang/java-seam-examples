package ro.penteker.auktion.services.api;

import java.util.List;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;

public interface SecurityService {

  public AukUser getUser(String username);

  public AukRole getRole(String roleName);

  public AukRole createRole(AukRole role);

  public void deleteRole(AukRole role);

  public List<AukRole> getPublicRoles();

  public List<AukRole> createDefaultRoles();

  public void deleteUser(AukUser user);

  public void updateUser(AukUser user);

  public AukUser createUser(String createdBy, String username, String password, boolean enabled, List<AukRole> roles);

}
