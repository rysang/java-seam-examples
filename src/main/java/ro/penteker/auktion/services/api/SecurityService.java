package ro.penteker.auktion.services.api;

import ro.penteker.auktion.dao.AukUser;

public interface SecurityService {

  public AukUser getUser(String username);

  public void createDefaultRoles();

  public AukUser createUser(String createdBy, String username, String password, boolean isAdmin);
}
