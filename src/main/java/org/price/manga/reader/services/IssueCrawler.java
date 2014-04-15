package org.price.manga.reader.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.price.manga.reader.entities.Genre;
import org.price.manga.reader.entities.Manga;
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
			Manga manga = createManga(doc);

			manga = mangaOpsService.createManga(manga);

		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error", e);
		}
	}

	protected Manga createManga(Document document) throws IOException {
		Manga manga = new Manga();

		Element masthead = document.select("h2.aname").first();
		manga.setName(masthead.html().trim());

		Element img = document.select("#mangaimg img").first();
		String imgLink = img.attr("src");
		manga.setImage(createImage(imgLink));

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

	protected byte[] createImage(String link) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(link)
				.openConnection();
		connection.setDoInput(true);

		try (InputStream is = connection.getInputStream()) {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			IOUtils.copy(is, bos);

			return bos.toByteArray();
		}

	}
}
