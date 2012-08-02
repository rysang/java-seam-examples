package eu.cec.sanco.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;

import eu.cec.sanco.beans.ComplaintSet;
import eu.cec.sanco.beans.Entry;
import eu.cec.sanco.services.api.PersistenceService;

public class PersistenceServiceImpl implements PersistenceService {
  private static final transient Logger LOG = Logger.getLogger(PersistenceServiceImpl.class);

  private JdbcTemplate jdbcTemplate;
  private Gson gson;

  public PersistenceServiceImpl() {

  }

  public void setGson(Gson gson) {
    this.gson = gson;
  }

  public Gson getGson() {
    return gson;
  }

  public void setDataSource(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public Entry getEntry(String id) {
    LOG.info("Searching for : " + id);
    return jdbcTemplate.queryForObject("select * from complaints c where c.id = ?", new Object[] { id },
        new RowMapper<Entry>() {
          public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
            Entry entry = new Entry();
            entry.setId(rs.getString("ID"));
            entry.setComplaintSet(gson.fromJson(rs.getString("VALUE"), ComplaintSet.class));
            entry.setTimestamp(new Date(new Long(rs.getLong("TIMESTAMP"))));

            return entry;
          }
        });
  }

  public List<Entry> getEntries() {
    LOG.info("Getting all entries.");

    return jdbcTemplate.query("select * from complaints c where c.id <> 'app_state' and c.id <> 'organisation' order by c.timestamp desc",
        new RowMapper<Entry>() {
          public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
            Entry entry = new Entry();
            entry.setId(rs.getString("ID"));
            entry.setComplaintSet(gson.fromJson(rs.getString("VALUE"), ComplaintSet.class));
            entry.setTimestamp(new Date(new Long(rs.getLong("TIMESTAMP"))));

            return entry;
          }
        });
  }
}
