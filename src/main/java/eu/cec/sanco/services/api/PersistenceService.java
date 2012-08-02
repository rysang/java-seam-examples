package eu.cec.sanco.services.api;

import java.util.List;

import eu.cec.sanco.beans.Entry;

public interface PersistenceService {

  public Entry getEntry(String id);

  public List<Entry> getEntries();
}
