package org.cpcs.locale.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.cpcs.locale.api.LocaleService;

import com.googlecode.ehcache.annotations.Cacheable;

public class LocaleBean implements LocaleService {
  private static Logger LOG = Logger.getLogger(LocaleBean.class);

  private Locale currentLocale;

  public LocaleBean() {

  }

  /*
   * (non-Javadoc)
   * @see org.cpcs.locale.LocaleService#getCurrentLocale()
   */
  public String getCurrentLocale() {
    if (currentLocale == null) {
      currentLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
    }
    return currentLocale.getLanguage();
  }

  /*
   * (non-Javadoc)
   * @see org.cpcs.locale.LocaleService#getSupportedLocales()
   */

  @Cacheable(cacheName = "org.cpcs.languageCache")
  public List<String> getSupportedLocales() {
    ArrayList<String> listOfSupp = new ArrayList<String>(20);
    Iterator<Locale> locales = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
    while (locales.hasNext()) {
      Locale locale = locales.next();
      listOfSupp.add(locale.getLanguage());
    }

    return listOfSupp;
  }

  public void setCurrentLocale(String currentLocale) {
    this.currentLocale = new Locale(currentLocale);
  }

  /*
   * (non-Javadoc)
   * @see org.cpcs.locale.LocaleService#setCurrentLocaleLanguage(java.lang.String)
   */
  public void setCurrentLocaleLanguage() {
    LOG.info("Setting current locale to :" + getCurrentLocale());
    FacesContext.getCurrentInstance().getViewRoot().setLocale(currentLocale);
  }
}
