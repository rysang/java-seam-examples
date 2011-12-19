package org.cpcs.dao.services.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cpcs.dao.services.beans.Directive;
import org.springframework.jdbc.core.RowMapper;

public class DirectiveMapper implements RowMapper<Directive> {

  public Directive mapRow(ResultSet rs, int rowNum) throws SQLException {
    Directive directive = new Directive(rs.getString("id"));
    directive.setDescription(rs.getString("description"));
    directive.setCode(rs.getString("code"));
    directive.setActive(rs.getString("active").equals("Y") ? true : false);
    directive.setComDirId(rs.getString("com_dir_id"));

    return directive;
  }

}
