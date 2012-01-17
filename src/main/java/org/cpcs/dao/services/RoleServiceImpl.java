package org.cpcs.dao.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.RoleService;
import org.cpcs.dao.services.beans.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
    return jdbcTemplate.query(getListRolesQuery(), new RowMapper<Role>() {

      public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role(rs.getLong("id"));
        role.setDescription(rs.getString("description"));
        role.setCode(rs.getString("code"));
        role.setInternalRole(rs.getString("internal_role").equals("Y") ? true : false);

        return role;
      }

    });
  }

  public void setListRolesQuery(String listRolesQuery) {
    this.listRolesQuery = listRolesQuery;
  }

  public String getListRolesQuery() {
    return listRolesQuery;
  }

}
