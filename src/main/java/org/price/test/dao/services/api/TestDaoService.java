package org.price.test.dao.services.api;

import java.util.List;

import org.price.test.cache.annotations.Cached;
import org.price.test.cache.annotations.RemoveCache;

import com.google.appengine.api.datastore.Entity;

public interface TestDaoService {

    @RemoveCache(key = "org.entities")
    public void txSaveBean(Entity entity);

    @RemoveCache(key = "org.entities")
    public void txDelete(String id);

    @Cached(key = "org.entities")
    public List<Entity> getAllBeans();
}
