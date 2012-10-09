package ro.penteker.auktion;

import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;
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

    List<AukUser> aukUser = securityService.getUsers(0, 15, "enabled", SortOrder.ASCENDING,
        new TreeMap<String, String>());
    LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++");
    LOG.info("User: " + aukUser);
    LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++");

  }
}
