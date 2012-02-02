package org.price.jndi;

import java.sql.SQLException;

import javax.sql.DataSource;
import javax.sql.XADataSource;

import com.arjuna.ats.internal.jdbc.DynamicClass;

public class FakeJndi implements DynamicClass {

  private static XADataSource db1;
  private static XADataSource db2;

  public FakeJndi() {

  }

  public XADataSource getDataSource(String name) throws SQLException {
    return name.contains("mydb1") ? db1 : db2;
  }

  public XADataSource getDataSource(String name, boolean arg1) throws SQLException {
    return getDataSource(name);
  }

  public void shutdownDataSource(XADataSource ds) throws SQLException {

  }

  public void setDb1(XADataSource db1) {
    FakeJndi.db1 = db1;
  }

  public void setDb2(XADataSource db2) {
    FakeJndi.db2 = db2;
  }
}
