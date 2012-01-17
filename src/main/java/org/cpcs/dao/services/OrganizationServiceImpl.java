package org.cpcs.dao.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.OrganizationService;
import org.cpcs.dao.services.beans.Organization;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    return jdbcTemplate.query(getListOrganizationsQuery(), new Object[] { index, count },
        new RowMapper<Organization>() {
          public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getString("id"));
            organization.setMemberState(rs.getString("member_state"));
            organization.setName(rs.getString("name"));
            organization.setShortName(rs.getString("short_name"));
            organization.setLongName(rs.getString("long_name"));
            organization.setRole(rs.getString("role"));
            organization.setDeleted(rs.getString("is_deleted").equals("N") ? false : true);
            organization.setMsId(rs.getString("ms_id"));
            organization.setRoleId(rs.getString("role_id"));

            return organization;
          }
        });
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
