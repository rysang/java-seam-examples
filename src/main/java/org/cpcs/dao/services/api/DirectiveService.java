package org.cpcs.dao.services.api;

import java.util.List;

import org.cpcs.dao.services.beans.Directive;

public interface DirectiveService {

  public abstract List<Directive> listDirectives();

  public abstract int listCount();

  public void saveDirective(Directive directive);

  public abstract void deleteDirective(Directive directive);

}