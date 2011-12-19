package org.cpcs.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.RoleServiceImpl;
import org.cpcs.dao.services.api.DirectiveService;
import org.cpcs.dao.services.api.MemberStateService;
import org.cpcs.dao.services.api.RoleService;
import org.cpcs.dao.services.beans.Directive;
import org.cpcs.dao.services.beans.MemberState;
import org.cpcs.dao.services.beans.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component("directiveActions")
public class DirectiveActions {

  private static final Logger LOG = Logger.getLogger(DirectiveActions.class);

  @Autowired
  @Qualifier("directiveDao")
  private DirectiveService directiveDao;

  private DirectiveDataModel directiveDataModel;

  public DirectiveActions() {

  }

  public List<SelectItem> getDirectives() {
    List<Directive> directives = directiveDao.listDirectives();
    ArrayList<SelectItem> ret = new ArrayList<SelectItem>(directives.size());

    for (Directive dir : directives) {
      if (dir.isActive())
        ret.add(new SelectItem(dir.getId(), dir.getDescription()));
    }

    return ret;
  }

  public DirectiveDataModel getDirectiveDataModel() {
    if (directiveDataModel == null) {
      directiveDataModel = new DirectiveDataModel(directiveDao);
      directiveDataModel.setRowCount(directiveDao.listCount());
    }
    return directiveDataModel;
  }

}
