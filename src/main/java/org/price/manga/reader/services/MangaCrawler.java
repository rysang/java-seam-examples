package org.price.manga.reader.services;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.price.manga.reader.services.api.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("mangaCrawler")
public class MangaCrawler implements Crawler, Runnable {

	private static final Logger LOG = Logger.getLogger("MangaCrawler");

	@Autowired
	private ExecutorService executorService;
	private String page;

	public MangaCrawler() {

	}

	@Override
	public void crawl(String page) {
		this.page = page;
		executorService.execute(this);
	}

	@Override
	public void run() {
		try {
			Document doc = Jsoup.connect(page).get();
			Element masthead = doc.select("h2.aname").first();

			System.out.println(masthead.data().trim());

		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error", e);
		}
	}

}
