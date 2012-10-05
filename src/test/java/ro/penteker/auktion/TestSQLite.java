package ro.penteker.auktion;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public void testSQLite() throws Exception {
    AukUser aukUser = securityService.getUser("asda");
    LOG.info("User: " + aukUser);
  }
}
