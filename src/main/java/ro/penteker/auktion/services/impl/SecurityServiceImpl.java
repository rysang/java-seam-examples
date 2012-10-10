package ro.penteker.auktion.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukRole;
import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityPersistenceService;
import ro.penteker.auktion.services.api.SecurityService;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

public class SecurityServiceImpl implements SecurityService {
  private static final Logger LOG = Logger.getLogger(SecurityService.class);

  private SecurityPersistenceService securityPersistenceService;

  public SecurityServiceImpl() {

  }

  // @Cacheable(cacheName = "ro.penteker.auktion.security.cache")
  public AukUser getUser(String username) {
    return securityPersistenceService.getUser(username);
  }

  @Override
  public AukRole getRole(String roleName) {
    return securityPersistenceService.getRole(roleName);
  }

  @Override
  public AukRole createRole(AukRole role) {
    Long id = securityPersistenceService.saveRole(role);
    role.setId(id);

    return role;
  }

  @Override
  public void deleteRole(AukRole role) {
    securityPersistenceService.deleteRole(role);
  }

  @Override
  public List<AukRole> getPublicRoles() {
    return securityPersistenceService.getPublicRoles();
  }

  @Override
  public void deleteUser(AukUser user) {
    securityPersistenceService.deleteUser(user);
  }

  public List<AukRole> createDefaultRoles() {
    LOG.info("Creating default roles.");

    AukRole admin = new AukRole(new Long(1), "ROLE_ADMIN", new Date(), "system");
    AukRole user = new AukRole(new Long(2), "ROLE_USER", new Date(), "system");
    AukRole anonymous = new AukRole(new Long(3), "ROLE_ANONYMOUS", new Date(), "system");

    securityPersistenceService.saveRole(admin);
    securityPersistenceService.saveRole(user);
    securityPersistenceService.saveRole(anonymous);

    return securityPersistenceService.getPublicRoles();

  }

  // @TriggersRemove(cacheName = "ro.penteker.auktion.security.cache", when = When.AFTER_METHOD_INVOCATION, removeAll =
  // true)
  public void updateUser(AukUser user) {
    securityPersistenceService.updateUser(user);
  }

  // @TriggersRemove(cacheName = "ro.penteker.auktion.security.cache", when = When.AFTER_METHOD_INVOCATION, removeAll =
  // true)
  public AukUser createUser(String createdBy, String username, String password, boolean enabled, List<AukRole> roles) {
    AukUser user = new AukUser();
    user.setCreatedBy(createdBy);
    user.setUsername(username);
    user.setPassword(password);
    user.setCreatedDate(new Date());
    user.setEnabled(enabled);

    user.getAukRoleList().addAll(roles);

    Long id = securityPersistenceService.saveUser(user);

    user.setId(id);
    return user;
  }

  public void setSecurityPersistenceService(SecurityPersistenceService securityPersistenceService) {
    this.securityPersistenceService = securityPersistenceService;
  }

  @Override
  // @Cacheable(cacheName = "ro.penteker.auktion.security.cache")
  public List<AukUser> getUsers(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters) {
    return securityPersistenceService.getUsers(first, pageSize, sortField, sortOrder, filters);
  }

  @Override
  public List<AukRole> getRoles(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters) {
    return securityPersistenceService.getRoles(first, pageSize, sortField, sortOrder, filters);
  }

  public static void main(String[] args) {
    System.out.println(System.getProperty("java.io.tmpdir"));
  }

}
