package org.price.manga.reader.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.price.manga.reader.entities.Issue;
import org.price.manga.reader.entities.Page;
import org.price.manga.reader.services.api.Crawler;
import org.price.manga.reader.services.api.MangaOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("issueCrawler")
public class IssueCrawler implements Crawler, Runnable {

	private static final Logger LOG = Logger.getLogger("IssueCrawler");

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private MangaOpsService mangaOpsService;

	private String page;

	public IssueCrawler() {

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
			String parentLink = Utils.getBaseUrl(page);
			Issue issue = mangaOpsService.getIssueByLink(page);
			if (issue == null) {
				LOG.log(Level.SEVERE, "Failed to find issue. Exiting ...");
				return;
			}

			List<Page> links = getPages(doc, parentLink, issue);
			for (Page p : links) {
				mangaOpsService.createPage(p);
			}

		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error", e);
		} catch (URISyntaxException e) {
			LOG.log(Level.SEVERE, "Error", e);
		}
	}

	public List<Page> getPages(Document doc, String baseLink, Issue issue)
			throws IOException {

		List<Page> pages = new ArrayList<>();
		Elements options = doc.select("#pageMenu option");

		for (Element opt : options) {
			Page page = new Page();
			page.setName(baseLink + opt.attr("value"));
			page.setIssue(issue);
			page.setIndex(new Integer(opt.html().trim()));
			page.setData(Utils.createImage(getImgLink(page.getName(), baseLink)));

			pages.add(page);
		}

		return pages;
	}

	public String getImgLink(String page, String baseUrl) throws IOException {
		Document doc = Jsoup.connect(page).get();
		return doc.select("#img").get(0).attr("src");
	}
}
