package org.price.manga.reader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.price.manga.reader.services.CrawlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/services-context.xml" })
public class TestThreading {

	@Autowired
	private CrawlerFactory crawlerFactory;

	@Test
	public void testUserCreationGetDelete() throws Exception {
		Assert.assertNotNull(crawlerFactory);

		crawlerFactory.newMangaCrawler().crawl(
				"http://www.mangareader.net/the-breaker-new-waves");

		System.in.read();
	}
}
