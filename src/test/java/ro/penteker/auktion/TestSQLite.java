package ro.penteker.auktion;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import ro.penteker.auktion.dao.AukUser;
import ro.penteker.auktion.services.api.SecurityService;

@ContextConfiguration(locations = { "classpath:application-context.xml", "classpath:db-context.xml" })
public class TestSQLite extends AbstractJUnit38SpringContextTests {

  private static final Logger LOG = Logger.getLogger(TestSQLite.class);

  @Autowired
  private SecurityService securityService;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testSQLite() throws Exception {
    securityService.createDefaultRoles();
    
    AukUser aukUser = securityService.getUser("admin");
    LOG.info("User: " + aukUser);

    aukUser = securityService.createUser("Test", "admin", "admin", true);
    LOG.info("User: " + aukUser);
  }
}
