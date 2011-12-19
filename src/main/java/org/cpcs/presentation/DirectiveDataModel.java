package org.cpcs.presentation;

import java.util.List;
import java.util.Map;

import org.cpcs.dao.services.api.DirectiveService;
import org.cpcs.dao.services.beans.Directive;
import org.primefaces.model.LazyDataModel;

public class DirectiveDataModel extends LazyDataModel<Directive> {

  private static final long serialVersionUID = 2654016626874491269L;
  private DirectiveService directiveService;

  public DirectiveDataModel() {

  }

  public DirectiveDataModel(DirectiveService directiveService) {
    setDirectiveService(directiveService);
  }

  @Override
  public List<Directive> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
    List<Directive> ret = directiveService.listDirectives();
    setRowCount(ret.size());

    return ret.subList(first, (first + pageSize) > ret.size() ? ret.size() : (first + pageSize));
  }

  public void setDirectiveService(DirectiveService directiveService) {
    this.directiveService = directiveService;
  }

  public DirectiveService getDirectiveService() {
    return directiveService;
  }

}
