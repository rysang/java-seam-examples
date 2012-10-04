package ro.penteker.auktion.services.api;

import java.util.List;

import ro.penteker.auktion.beans.AppState;
import ro.penteker.auktion.beans.Entry;
import ro.penteker.auktion.beans.Organisation;



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
