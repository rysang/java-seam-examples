package org.price.manga.reader.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.price.manga.reader.entities.Genre;
import org.price.manga.reader.entities.Issue;
import org.price.manga.reader.entities.Manga;
import org.price.manga.reader.services.api.Crawler;
import org.price.manga.reader.services.api.MangaOpsService;
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

	@Autowired
	private MangaOpsService mangaOpsService;

	@Autowired
	private CrawlerFactory crawlerFactory;

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
			Manga manga = createManga(doc);

			manga = mangaOpsService.createManga(manga);
			createIssueCrawlers(doc, manga);

		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error", e);
		}
	}

	protected void createIssueCrawlers(Document document, Manga manga)
			throws URISyntaxException, ParseException {
		Elements trs = document.select("#chapterlist #listing tr");

		for (Element tr : trs) {
			Iterator<Element> tdIt = tr.children().iterator();
			Element td = tdIt.next();

			String link = td.select("a").attr("href");
			String name = td.select("a").html().trim();

			String dateCreated = tdIt.next().html().trim();

			if (!"".equals(link)) {
				link = Utils.getBaseUrl(page) + link;

				Issue issue = createIssue(name, dateCreated, link, manga);
				crawlerFactory.newIssueCrawler().crawl(issue.getLink());
			}
		}
	}

	protected Issue createIssue(String name, String dateCreated, String link,
			Manga manga) throws ParseException {

		Issue issue = new Issue();
		SimpleDateFormat dt = new SimpleDateFormat("mm/dd/yyyy");
		issue.setDateAdded(dt.parse(dateCreated));
		issue.setName(name);
		issue.setManga(manga);
		issue.setLink(link);

		issue = mangaOpsService.createIssue(issue);

		return issue;
	}

	protected Manga createManga(Document document) throws IOException {
		Manga manga = new Manga();

		Element masthead = document.select("h2.aname").first();
		manga.setName(masthead.html().trim());

		Element img = document.select("#mangaimg img").first();
		String imgLink = img.attr("src");
		manga.setImage(Utils.createImage(imgLink));

		collectMangaData(manga, document);

		return manga;
	}

	protected void collectMangaData(Manga manga, Document document) {
		Elements trs = document.select("#mangaproperties tr");

		for (Element e : trs) {
			Elements tds = e.children();
			Iterator<Element> it = tds.iterator();

			Element td1 = it.next();
			Element td2 = it.next();

			if (td1.html().contains("Alternate Name:")) {
				manga.setAlternateName(td2.html().trim());
			} else if (td1.html().contains("Year of Release:")) {
				manga.setYearReleased(td2.html().trim());
			} else if (td1.html().contains("Status:")) {
				manga.setStatus(td2.html().trim());
			} else if (td1.html().contains("Author:")) {
				manga.setAuthor(td2.html().trim());
			} else if (td1.html().contains("Artist:")) {
				manga.setArtist(td2.html().trim());
			} else if (td1.html().contains("Reading Direction:")) {
				manga.setReadingDirection(td2.html().trim());
			} else if (td1.html().contains("Genre:")) {

				Elements links = td2.children();
				for (Element l : links) {
					Genre g = new Genre();
					g.setId(UUID.randomUUID().toString());
					g.setName(l.children().iterator().next().html().trim());
					g.setManga(manga);

					manga.getGenres().add(g);
				}
			}
		}
	}

}
