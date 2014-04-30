package org.price.manga.reader.dao.services.api;

import org.price.manga.reader.entities.Genre;
import org.price.manga.reader.entities.Issue;
import org.price.manga.reader.entities.Manga;
import org.price.manga.reader.entities.Page;

public interface MangaDao {
	public Manga createManga(Manga manga);

	public Genre createGenre(Genre genre);

	public void deleteManga(String id);

	public Manga getManga(String id);

	public Issue createIssue(Issue issue);

	public Issue getIssueByLink(String link);

	public Page createPage(Page page);
}
