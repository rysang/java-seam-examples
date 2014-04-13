package org.price.manga.reader.dao.services.api;

import org.price.manga.reader.entities.Manga;

public interface MangaDao {
	public Manga createManga(Manga manga);

	public void deleteManga(String id);

	public Manga getManga(String id);
}
