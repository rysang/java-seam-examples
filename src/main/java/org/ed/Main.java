package org.ed;

import org.ed.dao.Repository;
import org.ed.dao.entities.Certificate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 * 
 */
public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beanContext.xml");

		Repository repo = (Repository) context.getBean("repository");

		Certificate certificate = new Certificate();
		certificate.getUid();
		certificate.setcIssuer("Priceputu Cristian Sorin");
		certificate.setcSerial(234234234);
		certificate.setcVersion(1);
		certificate.setcSubjKey("Blash");
		certificate.setcBinaryContent(new byte[] { 1, 2, 3 });

		repo.saveObject(certificate);

		String id = certificate.getUid();
		certificate = null;

		certificate = (Certificate) repo.findObjectById(Certificate.class, id);
		System.out.println(certificate);
	}
}
