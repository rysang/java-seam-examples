package org.cpcs.navigation.api;

public interface NavigationService {
  public String navigateTo(String navigation, String title);

  public String getCurrentPage();

  public String getCurrentPageTitle();
}
