package org.cpcs.navigation.impl;

import org.apache.log4j.Logger;
import org.cpcs.navigation.api.NavigationService;

public class NavigationBean implements NavigationService {
  private static Logger LOG = Logger.getLogger(NavigationBean.class);
  private String currentPage;
  private String currentPageTitle;

  public NavigationBean() {

  }

  public void navigateTo(String navigation, String title) {
    setCurrentPage(navigation);
    setCurrentPageTitle(title);

    LOG.info("Navigating to :" + navigation);
  }

  public String getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(String currentPage) {
    this.currentPage = currentPage;
  }

  public void setCurrentPageTitle(String currentPageTitle) {
    this.currentPageTitle = currentPageTitle;
  }

  public String getCurrentPageTitle() {
    return currentPageTitle;
  }
}
