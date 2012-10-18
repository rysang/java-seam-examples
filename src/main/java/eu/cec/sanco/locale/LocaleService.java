package eu.cec.sanco.locale;

import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.cec.sanco.utils.api.Utils;

@Component
@Scope("session")
@Qualifier("localeService")
public class LocaleService {

  private static final transient Logger LOG = Logger.getLogger(LocaleService.class);
  private String currentLocale = "en";

  @Autowired
  @Qualifier("utils")
  private Utils utils;

  public LocaleService() {

  }

  // @TriggersRemove(cacheName = "eu.cec.sanco.eccrs.selects", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
  public void setCurrentLocale(String currentLocale) {
    this.currentLocale = currentLocale;

    utils.resetSelects();
    LOG.info("Changing locale to : " + currentLocale);
    FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(currentLocale));
  }

  public String getCurrentLocale() {
    return currentLocale;
  }

  @Override
  public String toString() {
    return String.format("LocaleService [currentLocale=%s]", currentLocale);
  }

}
