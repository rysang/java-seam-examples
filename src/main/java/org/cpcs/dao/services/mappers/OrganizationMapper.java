package org.cpcs.dao.services.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cpcs.dao.services.beans.Organization;
import org.springframework.jdbc.core.RowMapper;

public class OrganizationMapper implements RowMapper<Organization> {

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

}
