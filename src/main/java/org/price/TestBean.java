package org.price;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.price.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestBean implements Test {
  private static final Logger LOG = Logger.getLogger(Test.class);

  private JdbcTemplate db1;
  private JdbcTemplate db2;

  public void handleUpdate() {

  }

  public void handleDbCreate() {
    LOG.info("Creating tables.");

    db1.update("create table test1(pk integer, val varchar(20) )");
    db2.update("create table test2pk integer, val varchar(20) )");

    LOG.info("Tables created");
  }

  public void setDb1(DataSource db1) {
    this.db1 = new JdbcTemplate(db1);
  }

  public void setDb2(DataSource db2) {
    this.db2 = new JdbcTemplate(db2);
  }

}
