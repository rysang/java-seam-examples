package com.penteker.auction.services.api;

import java.util.List;

import com.penteker.auction.beans.AppState;
import com.penteker.auction.beans.Entry;
import com.penteker.auction.beans.Organisation;


public interface PersistenceService {

  public Entry getEntry(String id);

  public List<Entry> getEntries();

  public AppState getAppState();

  public Organisation getOrganisation();

  public List<Entry> getUnlockedComplaints(String orgId);

  public List<Entry> getLockedComplaints(String orgId);

  public int saveEntry(Entry entry);

  public int updateEntry(Entry entry);

  public int removeEntries(List<String> ids);
}
