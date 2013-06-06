package org.price.test.beans.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.price.test.conversion.annotations.Store;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

@SuppressWarnings("rawtypes")
public class EntityConverter {

    private Map<Class, Set<Field>> convertClasses = new HashMap<>();

    public EntityConverter() {

    }

    public Object convertFromEntity(Entity e, Class clazz) throws Exception {
        Set<Field> fields = convertClasses.get(clazz);
        if (fields == null) {
            createFieldsForClass(clazz);
        }

        Object o = clazz.newInstance();
        for (Field f : fields) {
            Field oField = o.getClass().getField(f.getName());
            PropertyUtils.setProperty(oField, oField.getName(), e.getProperty(oField.getName()));
        }

        return o;
    }

    protected void createFieldsForClass(Class clazz) {
        if (clazz == null) {
            return;
        }

        Set<Field> finalFields = convertClasses.get(clazz);
        if (finalFields == null) {
            finalFields = new HashSet<>();
            convertClasses.put(clazz, finalFields);
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if ((f.getAnnotation(Store.class) != null) && !Modifier.isStatic(f.getModifiers())) {
                finalFields.add(f);
            }
        }

        fields = clazz.getFields();
        for (Field f : fields) {
            if ((f.getAnnotation(Store.class) != null) && !Modifier.isStatic(f.getModifiers())) {
                finalFields.add(f);
            }
        }

        createFieldsForClass(clazz.getSuperclass());
    }

    public Entity convertToEntity(Object o) throws Exception {

        Set<Field> finalFields = convertClasses.get(o.getClass());
        if (finalFields == null) {
            finalFields = new HashSet<>();
            convertClasses.put(o.getClass(), finalFields);
        }

        Entity entity = new Entity(o.getClass().getName());

        for (Field f : finalFields) {
            entity.setProperty(f.getName(), f.get(o));
        }

        return entity;
    }

    public Entity convertToEntity(Object o, Key parent) throws Exception {

        Set<Field> finalFields = convertClasses.get(o.getClass());
        if (finalFields == null) {
            finalFields = new HashSet<>();
            convertClasses.put(o.getClass(), finalFields);
        }

        Entity entity = new Entity(o.getClass().getName(), parent);

        for (Field f : finalFields) {
            entity.setProperty(f.getName(), f.get(o));
        }

        return entity;
    }
}
