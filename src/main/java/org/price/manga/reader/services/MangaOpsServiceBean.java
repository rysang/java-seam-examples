package org.price.manga.reader.services;

import org.price.manga.reader.dao.services.api.MangaDao;
import org.price.manga.reader.entities.Manga;
import org.price.manga.reader.services.api.MangaOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("mangaOpsService")
public class MangaOpsServiceBean implements MangaOpsService {

	@Autowired
	private MangaDao mangaDao;

	public MangaOpsServiceBean() {
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Manga createManga(Manga manga) {
		return mangaDao.createManga(manga);
	}

}
