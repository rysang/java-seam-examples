package org.webapp.test;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

@ContextConfiguration(locations = { "classpath:logic-context.xml" })
public class TestSpring extends AbstractJUnit38SpringContextTests {

  @Resource
  private ApplicationContext applicationContext;

  public void testDatasource() throws Exception {

    DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
    assertNotNull(dataSource);
  }
}
