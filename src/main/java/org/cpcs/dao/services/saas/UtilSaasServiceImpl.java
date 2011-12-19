package org.cpcs.dao.services.saas;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.beans.saas.RoleGeneric;
import org.springframework.jdbc.core.JdbcTemplate;

public class UtilSaasServiceImpl {

  private static final Logger LOG = Logger.getLogger(UtilSaasServiceImpl.class);

  private JdbcTemplate jdbcTemplate;

  public UtilSaasServiceImpl() {

  }

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public RoleGeneric getRoleGenericByShortName(String name) {
    return null;
  }
}
