package org.spring.jsf.app.cache;

import java.util.List;

public interface LongQuery {
  public List<String> getLongQuery() throws Exception;

  public void reset();
}
