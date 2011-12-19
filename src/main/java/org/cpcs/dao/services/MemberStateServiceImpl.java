package org.cpcs.dao.services;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.MemberStateService;
import org.cpcs.dao.services.beans.MemberState;
import org.cpcs.dao.services.mappers.MemberStateMapper;
import org.springframework.jdbc.core.JdbcTemplate;

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
    return jdbcTemplate.query(getListStatesQuery(), new MemberStateMapper());
  }

  public void setListStatesQuery(String listStatesQuery) {
    this.listStatesQuery = listStatesQuery;
  }

  public String getListStatesQuery() {
    return listStatesQuery;
  }
}
