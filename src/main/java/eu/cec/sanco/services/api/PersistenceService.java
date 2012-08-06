package eu.cec.sanco.services.api;

import java.util.List;

import eu.cec.sanco.beans.AppState;
import eu.cec.sanco.beans.Entry;
import eu.cec.sanco.beans.Organisation;

public interface PersistenceService {

  public Entry getEntry(String id);

  public List<Entry> getEntries();

  public AppState getAppState();

  public Organisation getOrganisation();

  public List<Entry> getUnlockedComplaints();
  
  public List<Entry> getLockedComplaints();
}
