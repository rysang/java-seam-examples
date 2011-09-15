package org.spring.jsf.app;

import java.util.List;
import java.util.UUID;

import org.cpcs.dao.TestBean;
import org.cpcs.dao.TestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

@ContextConfiguration(locations = { "classpath:db-context.xml", "classpath:security-context.xml" })
public class TestSpring extends AbstractJUnit38SpringContextTests {

  @Autowired
  private TestDAO testDao;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testDB() throws Exception {
    TestBean testBean = new TestBean();
    testBean.setId(UUID.randomUUID().toString());
    testBean.setName("Price");

    testDao.save(testBean);

    List<TestBean> list = testDao.list();
    assertEquals(1, list.size());
  }
}
