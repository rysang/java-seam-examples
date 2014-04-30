package org.price.manga.reader.services.api;

import java.util.List;

import org.price.manga.reader.entities.Genre;
import org.price.manga.reader.entities.Issue;
import org.price.manga.reader.entities.Manga;
import org.price.manga.reader.entities.Page;

public interface MangaOpsService {
	public Manga createManga(Manga manga);

	public List<Genre> createGenres(List<Genre> genres);

	public Issue createIssue(Issue issue);

	public Page createPage(Page page);

	public Issue getIssueByLink(String link);
}
