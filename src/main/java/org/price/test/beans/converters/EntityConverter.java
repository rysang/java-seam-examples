package org.price.test.beans.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.price.test.conversion.annotations.Id;
import org.price.test.conversion.annotations.Store;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("rawtypes")
public class EntityConverter {

	private static final Logger LOG = Logger.getLogger(EntityConverter.class
			.getName());

	private Map<Class, Set<Field>> convertClasses = new HashMap<>();
	private Map<Class, Field> ids = new HashMap<>();

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
			try {
				PropertyUtils.setProperty(oField, oField.getName(),
						e.getProperty(oField.getName()));
			} catch (Exception ex) {
				LOG.warning(ex.toString());
			}
		}

		Field id = ids.get(clazz);
		if (id == null) {
			throw new Exception("Each bean must define a key.");
		} else {
			PropertyUtils.setProperty(id, id.getName(),
					KeyFactory.keyToString(e.getKey()));
		}

		return o;
	}

	protected void createFieldsForClass(Class clazz) throws Exception {
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
			if ((f.getAnnotation(Store.class) != null)
					&& !Modifier.isStatic(f.getModifiers())) {
				finalFields.add(f);
			} else if (f.getAnnotation(Id.class) != null) {
				if (!f.getType().equals(String.class)) {
					throw new Exception("Id can be only string");
				}
				ids.put(clazz, f);
			}
		}

		fields = clazz.getFields();
		for (Field f : fields) {
			if ((f.getAnnotation(Store.class) != null)
					&& !Modifier.isStatic(f.getModifiers())) {
				finalFields.add(f);
			} else if (f.getAnnotation(Id.class) != null) {
				if (!f.getType().equals(String.class)) {
					throw new Exception("Id can be only string");
				}
				ids.put(clazz, f);
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

		Field id = ids.get(o.getClass());

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
