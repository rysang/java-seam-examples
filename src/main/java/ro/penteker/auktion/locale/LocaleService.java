package ro.penteker.auktion.locale;

import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
@Qualifier("localeServicex")
public class LocaleService {

  private static final transient Logger LOG = Logger.getLogger(LocaleService.class);
  private String currentLocale = "en";

  public LocaleService() {

  }

  public void setCurrentLocale(String currentLocale) {
    this.currentLocale = currentLocale;

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
