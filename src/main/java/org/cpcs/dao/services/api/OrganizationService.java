package org.cpcs.dao.services.api;

import java.util.List;

import org.cpcs.dao.services.beans.Organization;

public interface OrganizationService {

  public abstract List<Organization> listOrganizations(int index, int count);

  public int listCount();

}