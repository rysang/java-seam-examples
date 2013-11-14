package org.price.test.beans.converters;

import com.google.appengine.api.datastore.Entity;

public interface Converter<T> {
	public T convertEntity(Entity entity);

	public Entity convertType(T type);
}
