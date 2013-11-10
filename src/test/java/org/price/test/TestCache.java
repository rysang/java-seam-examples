package org.price.test;

import junit.framework.TestCase;
import net.sf.cglib.proxy.Enhancer;

import org.price.test.beans.Contact;
import org.price.test.cache.interceptors.SetterInterceptor;

public class TestCache extends TestCase {
	public void testNothing() {

		Contact contact = (Contact) Enhancer.create(Contact.class,
				new Class[] { EntitySupport.class }, new SetterInterceptor());
		contact.setEmail("email");

		EntitySupport support = (EntitySupport) contact;
		support.getEntity();
	}
}
