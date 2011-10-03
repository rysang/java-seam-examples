package org.spring.jsf.app;

import java.util.List;

import org.cpcs.locale.api.LocaleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.jsf.app.scope.WebContextTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ehcache-context.xml", "classpath:presentation-context.xml" })
@TestExecutionListeners({ WebContextTestExecutionListener.class, DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class })
public class LocaleTest {

  @Autowired
  private LocaleService localeService;

  public LocaleTest() {

  }

  @Test
  public void testLocale() throws Exception {

    List<String> locales = localeService.getSupportedLocales();

  }
}
