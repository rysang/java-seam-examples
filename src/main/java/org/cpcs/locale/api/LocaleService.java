package org.cpcs.locale.api;

import java.util.List;

public interface LocaleService {

  public abstract String getCurrentLocale();

  public void setCurrentLocale(String currentLocale);

  public abstract List<String> getSupportedLocales();

  public abstract void setCurrentLocaleLanguage();

  public void reset();
}