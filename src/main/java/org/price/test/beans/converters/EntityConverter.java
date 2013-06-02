package org.price.test.beans.converters;

import org.price.test.beans.Contact;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class EntityConverter {
    public static final Contact contactFromEntity(Entity e) {
        Contact contact = new Contact();
        contact.setEmail(e.getProperty("email").toString());
        contact.setFirstname(e.getProperty("firstName").toString());
        contact.setLastname(e.getProperty("lastName").toString());
        contact.setTelephone(e.getProperty("telephone").toString());
        contact.setId(KeyFactory.keyToString(e.getKey()));

        return contact;
    }

    public static final Entity contactToEntity(Contact contact) {

        Entity entity = new Entity(Contact.NAME);
        entity.setProperty("email", contact.getEmail());
        entity.setProperty("firstName", contact.getFirstname());
        entity.setProperty("lastName", contact.getLastname());
        entity.setProperty("telephone", contact.getTelephone());

        return entity;
    }
}
