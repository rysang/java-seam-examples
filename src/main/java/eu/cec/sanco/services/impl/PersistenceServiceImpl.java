package eu.cec.sanco.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.gson.Gson;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

import eu.cec.sanco.beans.AppState;
import eu.cec.sanco.beans.ComplaintSet;
import eu.cec.sanco.beans.Entry;
import eu.cec.sanco.beans.Organisation;
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

  @TriggersRemove(cacheName = "eu.cec.sanco.eccrs.persistence", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
  public int saveEntry(Entry entry) {
    int ret = this.jdbcTemplate.update("insert into complaints(id,value,timestamp) values (?,?,?)", new Object[] {
        entry.getId(), gson.toJson(entry.getComplaintSet()), entry.getTimestamp().getTime() });

    return ret;
  }

  @TriggersRemove(cacheName = "eu.cec.sanco.eccrs.persistence", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
  public int updateEntry(Entry entry) {
    int ret = this.jdbcTemplate.update("update complaints set  value = ?, timestamp = ? where id = ?", new Object[] {
        gson.toJson(entry.getComplaintSet()), entry.getTimestamp().getTime(), entry.getId() });

    return ret;
  }

  @TriggersRemove(cacheName = "eu.cec.sanco.eccrs.persistence", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
  public int removeEntries(List<String> ids) {
    if (ids.isEmpty()) {
      LOG.info("No entries to delete.");
      return -1;
    }

    LOG.info("Deleting entries:" + ids);
    StringBuilder query = new StringBuilder("delete from complaints where id = ? ");
    for (int i = 1; i < ids.size(); i++) {
      query.append(" or id = ? ");
    }

    int ret = this.jdbcTemplate.update(query.toString(), ids.toArray());
    LOG.info("Ret: " + ret);
    return ret;

  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.persistence")
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

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.persistence")
  public AppState getAppState() {
    LOG.info("Searching for appstate.");
    return jdbcTemplate.queryForObject("select * from complaints c where c.id = ?", new Object[] { "app_state" },
        new RowMapper<AppState>() {
          public AppState mapRow(ResultSet rs, int rowNum) throws SQLException {
            AppState appState = gson.fromJson(rs.getString("VALUE"), AppState.class);
            return appState;
          }
        });
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.persistence")
  public Organisation getOrganisation() {
    LOG.info("Searching for organisation.");
    return jdbcTemplate.queryForObject("select * from complaints c where c.id = ?", new Object[] { "organisation" },
        new RowMapper<Organisation>() {
          public Organisation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organisation org = gson.fromJson(rs.getString("VALUE"), Organisation.class);
            return org;
          }
        });
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.persistence")
  public List<Entry> getEntries() {
    LOG.info("Getting all entries.");

    return jdbcTemplate.query(
        "select * from complaints c where c.id <> 'app_state' and c.id <> 'organisation' order by c.timestamp desc",
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

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.persistence")
  public List<Entry> getUnlockedComplaints(String orgId) {
    List<Entry> allEntries = getEntries();
    ArrayList<Entry> unlockedEntries = new ArrayList<Entry>(allEntries.size());

    for (Entry e : allEntries) {
      if (!"true".equals(e.getComplaintSet().getLocked()) && e.getComplaintSet().getOrganisation_id().equals(orgId)) {
        unlockedEntries.add(e);
      }
    }

    return unlockedEntries;
  }

  @Cacheable(cacheName = "eu.cec.sanco.eccrs.persistence")
  public List<Entry> getLockedComplaints(String orgId) {
    List<Entry> allEntries = getEntries();
    ArrayList<Entry> lockedEntries = new ArrayList<Entry>(allEntries.size());

    for (Entry e : allEntries) {
      if (!"false".equals(e.getComplaintSet().getLocked()) && e.getComplaintSet().getOrganisation_id().equals(orgId)) {
        lockedEntries.add(e);
      }
    }

    return lockedEntries;
  }
}
