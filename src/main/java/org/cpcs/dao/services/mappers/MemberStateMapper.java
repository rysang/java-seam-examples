package org.cpcs.dao.services.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cpcs.dao.services.beans.MemberState;
import org.springframework.jdbc.core.RowMapper;

public class MemberStateMapper implements RowMapper<MemberState> {

  public MemberState mapRow(ResultSet rs, int rowNum) throws SQLException {
    MemberState memberState = new MemberState();
    memberState.setId(rs.getString("id"));
    memberState.setDescription(rs.getString("description"));
    memberState.setCode(rs.getString("code"));

    return memberState;
  }

}
