package org.gmoss;

import java.io.IOException;

import junit.framework.TestCase;

import org.gmoss.api.document.Document;
import org.gmoss.api.document.impl.filesystem.FileSystemDocumentManager;
import org.gmoss.api.service.GMOSSService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest extends TestCase {

	public void testSpring() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"org/gmoss/applicationContext.xml");

		GMOSSService v = (GMOSSService) context.getBean("OPTIONS");
		assertNotNull(v);

		System.out.println(v);
	}

	public void testGetDocumentByPath() throws IOException {
		FileSystemDocumentManager manager = new FileSystemDocumentManager();
		Document doc = manager.getDocument("/");
		assertNotNull(doc);

		Document newDoc = manager.createDocument(doc, "test.doc");

		Document[] children = manager.getChildren(doc);
		assertTrue(children.length > 0);

		manager.deleteDocument(newDoc);

	}
}
