package org.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws QuestFailedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("hello.xml");
		Knight knight = (Knight) ctx.getBean("knight");
		knight.embarkOnQuest();
	}
}