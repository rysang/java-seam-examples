package org.cpcs.dao.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.MemberStateService;
import org.cpcs.dao.services.beans.MemberState;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MemberStateServiceImpl implements MemberStateService {

  private static final Logger LOG = Logger.getLogger(MemberStateServiceImpl.class);

  private JdbcTemplate jdbcTemplate;

  private String listStatesQuery;

  public MemberStateServiceImpl() {

  }

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<MemberState> listMemberStates() {
    return jdbcTemplate.query(getListStatesQuery(), new RowMapper<MemberState>() {
      public MemberState mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberState memberState = new MemberState();
        memberState.setId(rs.getString("id"));
        memberState.setDescription(rs.getString("description"));
        memberState.setCode(rs.getString("code"));

        return memberState;
      }
    });
  }

  public void setListStatesQuery(String listStatesQuery) {
    this.listStatesQuery = listStatesQuery;
  }

  public String getListStatesQuery() {
    return listStatesQuery;
  }
}
