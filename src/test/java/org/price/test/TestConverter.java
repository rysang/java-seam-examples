package org.price.test;

import junit.framework.TestCase;

import org.price.test.beans.Contact;
import org.price.test.beans.converters.BaseConverter;

public class TestConverter extends TestCase {
	public void testNothing() throws Exception {

		BaseConverter baseConverter = new BaseConverter() {
		};

		baseConverter.createFieldsForClass(Contact.class);

		System.out.println(baseConverter);

	}
}
