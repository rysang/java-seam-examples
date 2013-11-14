package org.price.test.beans.converters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.price.test.beans.Identifiable;
import org.price.test.conversion.annotations.Store;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("rawtypes")
public class BaseConverter implements Converter<Identifiable> {

	protected Map<Class, Map<String, Field>> fields = new HashMap<>();

	public BaseConverter() {

	}

	@Override
	public Identifiable convertEntity(Entity entity) {
		Identifiable ret;
		try {
			ret = (Identifiable) Class.forName(
					entity.getProperty("class").toString()).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		ret.setId(KeyFactory.keyToString(entity.getKey()));
		Map<String, Field> fields = getFields(ret.getClass());
		Map<String, Object> properties = entity.getProperties();
		for (Entry<String, Object> entry : properties.entrySet()) {
			Field field = fields.get(entry.getKey());
			if (field != null) {
				try {
					field.set(ret, entry.getValue());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		return ret;
	}

	@Override
	public Entity convertType(Identifiable type) {
		Key key = null;

		if (type.getId() == null) {
			key = KeyFactory.createKey(type.getClass().getSimpleName(), UUID
					.randomUUID().toString());
		} else {
			key = KeyFactory.stringToKey(type.getId());
		}

		Entity entity = new Entity(key);
		entity.setProperty("class", type.getClass().getName());

		Map<String, Field> fields = getFields(type.getClass());
		for (Entry<String, Field> entry : fields.entrySet()) {
			try {
				entity.setProperty(entry.getKey(), entry.getValue().get(type));
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		return entity;
	}

	public Map<String, Field> getFields(Class clazz) {
		Map<String, Field> ret = fields.get(clazz);
		if (ret == null) {
			createFieldsForClass(clazz);
			ret = fields.get(clazz);
		}

		return ret;
	}

	public void createFieldsForClass(Class clazz) {
		if (clazz == null) {
			return;
		}

		Map<String, Field> fieldMap = new HashMap<>();

		fields.put(clazz, fieldMap);

		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			if ((f.getAnnotation(Store.class) != null)
					&& !Modifier.isStatic(f.getModifiers())) {
				f.setAccessible(true);
				fieldMap.put(f.getName(), f);
			}
		}

		fields = clazz.getFields();
		for (Field f : fields) {
			if ((f.getAnnotation(Store.class) != null)
					&& !Modifier.isStatic(f.getModifiers())) {
				f.setAccessible(true);
				fieldMap.put(f.getName(), f);
			}
		}

		createFieldsForClass(clazz.getSuperclass());
	}

	@Override
	public String toString() {
		return "BaseConverter [fields=" + fields + "]";
	}

}
