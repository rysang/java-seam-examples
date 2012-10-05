package ro.penteker.auktion.services.api;

import ro.penteker.auktion.dao.AukUser;

public interface SecurityService {

  public AukUser getUser(String username);
}
