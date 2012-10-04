package com.penteker.auction;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.penteker.auction.beans.Entry;
import com.penteker.auction.services.api.PersistenceService;


@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestSQLite extends AbstractJUnit38SpringContextTests {

  private static final Logger LOG = Logger.getLogger(TestSQLite.class);

  @Autowired
  private PersistenceService persistenceService;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testSQLite() throws Exception {
    List<Entry> entries = persistenceService.getEntries();
    for (Entry e : entries) {
      if (e.getComplaintSet().getLocked().equals("false"))
        LOG.info(e);
    }
  }
}
