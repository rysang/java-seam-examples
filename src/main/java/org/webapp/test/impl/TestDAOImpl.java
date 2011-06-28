package org.webapp.test.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.webapp.test.JSPSession;
import org.webapp.test.api.DummyRet;
import org.webapp.test.api.TestDAO;
import org.webapp.test.rest.StatusInfoBean;

@JSPSession
public class TestDAOImpl implements TestDAO {

  private JdbcTemplate jdbcTemplate;

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public DummyRet getDummyById(String id) {
    jdbcTemplate.queryForObject("ss", new Object[] {}, new RowMapper<StatusInfoBean>() {

      public StatusInfoBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
      }

    });
    return null;
  }

  public String getVersion() throws SQLException {
    return jdbcTemplate.getDataSource().toString();
  }

}
