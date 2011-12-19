package org.cpcs.dao.services.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cpcs.dao.services.beans.Role;
import org.springframework.jdbc.core.RowMapper;

public class RoleMapper implements RowMapper<Role> {

  public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
    Role role = new Role(rs.getLong("id"));
    role.setDescription(rs.getString("description"));
    role.setCode(rs.getString("code"));
    role.setInternalRole(rs.getString("internal_role").equals("Y") ? true : false);

    return role;
  }

}
