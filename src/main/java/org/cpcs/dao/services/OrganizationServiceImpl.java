package org.cpcs.dao.services;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.OrganizationService;
import org.cpcs.dao.services.beans.Organization;
import org.cpcs.dao.services.mappers.OrganizationMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

public class OrganizationServiceImpl implements OrganizationService {

  private static final Logger LOG = Logger.getLogger(OrganizationServiceImpl.class);

  private JdbcTemplate jdbcTemplate;

  private DataFieldMaxValueIncrementer organizationIncrementer;

  private String listOrganizationsQuery;

  private String listCountQuery;

  public OrganizationServiceImpl() {

  }

  public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);

  }

  public List<Organization> listOrganizations(int index, int count) {
    return jdbcTemplate.query(getListOrganizationsQuery(), new Object[] { index, count }, new OrganizationMapper());
  }

  public void setListOrganizationsQuery(String listOrganizationsQuery) {
    this.listOrganizationsQuery = listOrganizationsQuery;
  }

  public String getListOrganizationsQuery() {
    return listOrganizationsQuery;
  }

  public int listCount() {
    return jdbcTemplate.queryForInt(getListCountQuery());
  }

  public void setListCountQuery(String listCountQuery) {
    this.listCountQuery = listCountQuery;
  }

  public String getListCountQuery() {
    return listCountQuery;
  }

  public DataFieldMaxValueIncrementer getOrganizationIncrementer() {
    return organizationIncrementer;
  }

  public void setOrganizationIncrementer(DataFieldMaxValueIncrementer organizationIncrementer) {
    this.organizationIncrementer = organizationIncrementer;
  }

  public int organizationSequenceNextValue() {
    return organizationIncrementer.nextIntValue();
  }

  public int updateOrganization(Organization organization) {
    return -1;
  }
}
