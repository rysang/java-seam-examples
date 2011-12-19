package org.cpcs.presentation;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.OrganizationService;
import org.cpcs.dao.services.beans.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component("organizationActions")
public class OrganizationActions {

  private static final Logger LOG = Logger.getLogger(OrganizationActions.class);

  @Autowired
  @Qualifier("organizationDao")
  private OrganizationService organizationDao;

  private OrganizationDataModel organizationDataModel;
  private Organization selectedOrganization;

  public OrganizationActions() {

  }

  public OrganizationDataModel getOrganizationDataModel() {
    if (organizationDataModel == null) {
      organizationDataModel = new OrganizationDataModel(organizationDao);
      organizationDataModel.setRowCount(organizationDao.listCount());
    }
    return organizationDataModel;
  }

  public void setSelectedOrganization(Organization selectedOrganization) {
    this.selectedOrganization = selectedOrganization;
  }

  public Organization getSelectedOrganization() {
    return selectedOrganization;
  }

  public int getOrganizationCount() {
    return organizationDataModel.getRowCount();
  }

  public String editOrganization(Organization organization) {
    LOG.info(organization);

    setSelectedOrganization(organization);
    return "edit-organization";
  }

  public String createOrganization() {
    LOG.info("Creating new organization.");

    setSelectedOrganization(new Organization());
    return "create-organization";
  }

  public String saveOrganization() {

    return null;
  }
}
