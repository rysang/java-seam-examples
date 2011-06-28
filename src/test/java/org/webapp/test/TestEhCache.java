package org.webapp.test;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.webapp.test.cache.api.LongQuery;

@ContextConfiguration(locations = { "classpath:ehcache-context.xml" })
public class TestEhCache extends AbstractJUnit38SpringContextTests {

  @Resource
  private ApplicationContext applicationContext;

  public void testDatasource() throws Exception {

    LongQuery longQuery = (LongQuery) applicationContext.getBean("longQuery");
    assertNotNull(longQuery);

    long milisecs = System.currentTimeMillis();
    longQuery.getLongQuery();
    System.out.println("Time : " + (System.currentTimeMillis() - milisecs));

    milisecs = System.currentTimeMillis();
    longQuery.getLongQuery();
    System.out.println("Time : " + (System.currentTimeMillis() - milisecs));

    longQuery.reset();
    milisecs = System.currentTimeMillis();
    longQuery.getLongQuery();
    System.out.println("Time : " + (System.currentTimeMillis() - milisecs));

  }
}
