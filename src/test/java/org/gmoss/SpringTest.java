package org.gmoss;

import org.gmoss.api.service.GMOSSService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class SpringTest extends TestCase {

	public void testSpring() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"org/gmoss/applicationContext.xml");

		GMOSSService v = (GMOSSService) context.getBean("OPTIONS");
		assertNotNull(v);
		
		System.out.println(v);
	}
}
