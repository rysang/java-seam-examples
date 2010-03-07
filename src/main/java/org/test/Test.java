package org.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class Test {
	public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource(
				"hello.xml"));
		GreetingService greetingService = (GreetingService) factory
				.getBean("greetingService");
		greetingService.sayGreeting();
	}
}