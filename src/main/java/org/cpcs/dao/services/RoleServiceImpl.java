package org.cpcs.dao.services;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.RoleService;
import org.cpcs.dao.services.beans.Role;
import org.cpcs.dao.services.mappers.RoleMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class RoleServiceImpl implements RoleService {

  private static final Logger LOG = Logger.getLogger(RoleServiceImpl.class);

  private JdbcTemplate jdbcTemplate;

  private String listRolesQuery;

  public RoleServiceImpl() {

  }

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<Role> listRoles() {
    return jdbcTemplate.query(getListRolesQuery(), new RoleMapper());
  }

  public void setListRolesQuery(String listRolesQuery) {
    this.listRolesQuery = listRolesQuery;
  }

  public String getListRolesQuery() {
    return listRolesQuery;
  }

}
