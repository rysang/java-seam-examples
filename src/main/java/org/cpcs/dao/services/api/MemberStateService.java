package org.cpcs.dao.services.api;

import java.util.List;

import org.cpcs.dao.services.beans.MemberState;
import org.springframework.transaction.annotation.Transactional;

public interface MemberStateService {

  public abstract List<MemberState> listMemberStates();

}