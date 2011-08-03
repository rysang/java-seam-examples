package org.spring.jsf.app.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;

@Scope("singleton")
@Service("longQuery")
public class LongQueryImpl implements LongQuery {

  private static final Logger LOG = Logger.getLogger(LongQueryImpl.class);

  public LongQueryImpl() {
    LOG.info("Init");
  }

  @Cacheable(cacheName = "test1Cache")
  public List<String> getLongQuery() throws Exception {
    List<String> ret = new ArrayList<String>(100);

    for (int i = 0; i < 100; i++) {
      ret.add(UUID.randomUUID().toString());
      Thread.sleep(5);
    }
    return ret;
  }

  @TriggersRemove(cacheName = "test1Cache", when = When.AFTER_METHOD_INVOCATION, removeAll = true)
  public void reset() {
    LOG.info("Reset ehcache.");
  }

}
