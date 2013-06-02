package org.price.test.dao.services.api;

import java.util.List;

import com.google.appengine.api.datastore.Entity;

public interface TestDaoService {
	public void txSaveBean(Entity entity);

	public void txDelete(String id);

	public List<Entity> getAllBeans();
}
