package eu.cec.sanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.google.gson.Gson;

@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestSQLite extends AbstractJUnit38SpringContextTests {

  private static final Logger LOG = Logger.getLogger(TestSQLite.class);

  @Autowired
  private DataSource dataSource;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  static class Entry {

    private String creation_date;
    private String modification_date;

    public Entry() {

    }

    public void setCreation_date(String creation_date) {
      this.creation_date = creation_date;
    }

    public String getCreation_date() {
      return creation_date;
    }

    public void setModification_date(String modification_date) {
      this.modification_date = modification_date;
    }

    public String getModification_date() {
      return modification_date;
    }

    @Override
    public String toString() {
      return String.format("Entry [creation_date=%s, modification_date=%s]", creation_date, modification_date);
    }

  }

  public void testSQLite() throws Exception {
    Connection connection = dataSource.getConnection();
    assertNotNull(connection);
    Gson gson = new Gson();

    PreparedStatement ps = connection.prepareStatement("select value from complaints");
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      String object = rs.getString("VALUE");
      LOG.info(object);

      try {
        Entry o = gson.fromJson(object, Entry.class);
        LOG.info(new Date(new Long(o.getCreation_date())));
        LOG.info(o);
      } catch (Exception e) {
        LOG.error("Failed to deserialize");
      }

    }

    rs.close();
    ps.close();
    connection.close();
  }
}
