package ro.penteker.auktion.services.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityPersistenceService;
import ro.penteker.auktion.services.api.SecurityService;

public class SecurityServiceImpl implements SecurityService {
  private static final Logger LOG = Logger.getLogger(SecurityService.class);

  private SecurityPersistenceService securityPersistenceService;

  public SecurityServiceImpl() {

  }

  public AukUser getUser(String username) {
    return securityPersistenceService.getUser(username);
  }

  public void createDefaultRoles() {
    LOG.info("Creating default roles.");

    AukRole admin = new AukRole(new Long(1), "ROLE_ADMIN", new Date(), "system");
    AukRole user = new AukRole(new Long(2), "ROLE_USER", new Date(), "system");
    AukRole anonymous = new AukRole(new Long(3), "ROLE_ANONYMOUS", new Date(), "system");

    securityPersistenceService.saveRole(admin);
    securityPersistenceService.saveRole(user);
    securityPersistenceService.saveRole(anonymous);
  }

  public AukUser createUser(String createdBy, String username, String password, boolean isAdmin) {
    AukUser user = new AukUser();
    user.setCreatedBy(createdBy);
    user.setUsername(username);
    user.setPassword(password);
    user.setCreatedDate(new Date());

    List<AukRole> roles = securityPersistenceService.getRoles();

    if (!isAdmin) {
      for (AukRole r : roles) {
        if ("ROLE_ADMIN".equals(r.getAuthority())) {
          roles.remove(r);
          break;
        }
      }
    }

    user.getAukRoleList().addAll(roles);

    Long id = securityPersistenceService.saveUser(user);

    user.setId(id);
    return user;
  }

  public void setSecurityPersistenceService(SecurityPersistenceService securityPersistenceService) {
    this.securityPersistenceService = securityPersistenceService;
  }

}
