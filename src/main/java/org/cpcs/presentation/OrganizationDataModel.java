package org.cpcs.presentation;

import java.util.List;
import java.util.Map;

import org.cpcs.dao.services.api.OrganizationService;
import org.cpcs.dao.services.beans.Organization;
import org.primefaces.model.LazyDataModel;

public class OrganizationDataModel extends LazyDataModel<Organization> {

  private static final long serialVersionUID = 2654016626874491269L;
  private OrganizationService organizationService;

  public OrganizationDataModel() {

  }

  public OrganizationDataModel(OrganizationService organizationService) {
    setOrganizationService(organizationService);
  }

  @Override
  public List<Organization> load(int first, int pageSize, String sortField, boolean sortOrder,
      Map<String, String> filters) {
    List<Organization> ret = organizationService.listOrganizations(first, first + pageSize);
    setRowCount(organizationService.listCount());

    return ret;
  }

  public void setOrganizationService(OrganizationService organizationService) {
    this.organizationService = organizationService;
  }

  public OrganizationService getOrganizationService() {
    return organizationService;
  }

}
