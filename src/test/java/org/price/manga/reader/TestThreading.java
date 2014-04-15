package org.price.manga.reader;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.price.manga.reader.services.CrawlerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/services-context.xml" })
public class TestThreading {

	@Test
	public void testUserCreationGetDelete() throws Exception {

		System.in.read();
	}

	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"file:src/main/webapp/WEB-INF/services-context.xml",
				"file:src/main/webapp/WEB-INF/db-context.xml");
		CrawlerFactory crawlerFactory = (CrawlerFactory) ctx
				.getBean("crawlerFactory");
		crawlerFactory.newMangaCrawler().crawl(
				"http://www.mangareader.net/the-breaker-new-waves");
		System.in.read();
		ctx.close();
	}
}
