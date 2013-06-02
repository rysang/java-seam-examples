package org.price.test.dao.services;

import java.util.List;
import java.util.logging.Logger;

import org.price.test.beans.Contact;
import org.price.test.dao.services.api.TestDaoService;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class TestServiceDaoImpl implements TestDaoService {

    private DatastoreService    dataStoreService;
    private static final Logger LOG = Logger.getLogger(TestDaoService.class.getName());

    public DatastoreService getDataStoreService() {
        return dataStoreService;
    }

    public void setDataStoreService(DatastoreService dataStoreService) {
        this.dataStoreService = dataStoreService;
    }

    @Override
    public void txSaveBean(Entity entity) {
        dataStoreService.put(entity);
    }

    @Override
    public void txDelete(String id) {
        LOG.info("Key: " + KeyFactory.stringToKey(id));
        dataStoreService.delete(KeyFactory.stringToKey(id));
    }

    @Override
    public List<Entity> getAllBeans() {
        Query query = new Query(Contact.NAME);
        PreparedQuery pq = dataStoreService.prepare(query);

        return pq.asList(FetchOptions.Builder.withDefaults());
    }

}
