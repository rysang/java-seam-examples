package org.price.manga.reader.dao.services;

import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.price.manga.reader.dao.services.api.MangaDao;
import org.price.manga.reader.entities.Manga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mangaDao")
public class MangaDaoBean implements MangaDao {

	private static final Logger LOG = Logger.getLogger("UserMgmt");

	@Autowired
	private SessionFactory sessionFactory;

	public MangaDaoBean() {
	}

	@Override
	public Manga createManga(Manga manga) {
		sessionFactory.getCurrentSession().save(manga);
		return manga;
	}

	@Override
	public void deleteManga(String id) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"DELETE FROM Manga WHERE name = :name");
		query.setParameter("name", id);
		query.executeUpdate();
	}

	@Override
	public Manga getManga(String id) {
		return (Manga) sessionFactory.getCurrentSession().get(Manga.class, id);
	}

}
