package org.cpcs.dao.services;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.DirectiveService;
import org.cpcs.dao.services.beans.Directive;
import org.cpcs.dao.services.mappers.DirectiveMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class DirectiveServiceImpl implements DirectiveService {

  private static final Logger LOG = Logger.getLogger(DirectiveServiceImpl.class);

  private JdbcTemplate jdbcTemplate;

  private String listDirectivesQuery;

  private String listCountQuery;

  private String createDirectiveQuery;

  private String deleteDirectiveQuery;

  public DirectiveServiceImpl() {

  }

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<Directive> listDirectives() {
    return jdbcTemplate.query(getListDirectivesQuery(), new DirectiveMapper());
  }

  public void setListDirectivesQuery(String listDirectivesQuery) {
    this.listDirectivesQuery = listDirectivesQuery;
  }

  public String getListDirectivesQuery() {
    return listDirectivesQuery;
  }

  public int listCount() {
    return jdbcTemplate.queryForInt(getListCountQuery());
  }

  public void setListCountQuery(String listCountQuery) {
    this.listCountQuery = listCountQuery;
  }

  public String getListCountQuery() {
    return listCountQuery;
  }

  public void saveDirective(Directive directive) {
    LOG.info("Saving: " + directive);

    if (directive.getId() == null) {
      LOG.info("New directive detected.");

      jdbcTemplate.update(getCreateDirectiveQuery(), directive.getCode(), directive.getDescription(),
          directive.getComDirId());
    }

  }

  public void deleteDirective(Directive directive) {
    LOG.info("Deleting directive: " + directive);

    jdbcTemplate.update(getDeleteDirectiveQuery(), directive.getId());
  }

  public void setCreateDirectiveQuery(String createDirectiveQuery) {
    this.createDirectiveQuery = createDirectiveQuery;
  }

  public String getCreateDirectiveQuery() {
    return createDirectiveQuery;
  }

  public void setDeleteDirectiveQuery(String deleteDirectiveQuery) {
    this.deleteDirectiveQuery = deleteDirectiveQuery;
  }

  public String getDeleteDirectiveQuery() {
    return deleteDirectiveQuery;
  }

}
