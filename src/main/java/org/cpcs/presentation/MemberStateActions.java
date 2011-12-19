package org.cpcs.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.cpcs.dao.services.api.MemberStateService;
import org.cpcs.dao.services.beans.MemberState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component("memberStateActions")
public class MemberStateActions {

  private static final Logger LOG = Logger.getLogger(MemberStateActions.class);

  @Autowired
  @Qualifier("memberStateDao")
  private MemberStateService memberStateDao;

  private MemberState selectedMemberState;

  public MemberStateActions() {

  }

  public List<SelectItem> getMemberStates() {
    List<MemberState> states = memberStateDao.listMemberStates();
    ArrayList<SelectItem> ret = new ArrayList<SelectItem>(states.size());

    for (MemberState ms : states) {
      ret.add(new SelectItem(ms.getId(), ms.getDescription()));
    }

    return ret;
  }

  public void setSelectedMemberState(MemberState selectedMemberState) {
    this.selectedMemberState = selectedMemberState;
  }

  public MemberState getSelectedMemberState() {
    return selectedMemberState;
  }

}
