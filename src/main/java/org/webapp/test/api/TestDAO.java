package org.webapp.test.api;

import java.sql.SQLException;

public interface TestDAO {

  public DummyRet getDummyById(String id);

  public String getVersion() throws SQLException;
}
