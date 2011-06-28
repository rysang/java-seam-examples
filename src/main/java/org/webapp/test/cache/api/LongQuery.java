package org.webapp.test.cache.api;

import java.util.List;

public interface LongQuery {
  public List<String> getLongQuery() throws Exception;

  public void reset();
}
