package org.price.manga.reader.dao.services;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.price.manga.reader.dao.services.api.MangaDao;
import org.price.manga.reader.entities.Genre;
import org.price.manga.reader.entities.Issue;
import org.price.manga.reader.entities.Manga;
import org.price.manga.reader.entities.Page;
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
	public Genre createGenre(Genre genre) {
		sessionFactory.getCurrentSession().save(genre);
		return genre;
	}

	@Override
	public Issue createIssue(Issue issue) {
		sessionFactory.getCurrentSession().save(issue);
		return issue;
	}

	@Override
	public Page createPage(Page page) {
		sessionFactory.getCurrentSession().save(page);
		return page;
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

	@Override
	@SuppressWarnings("unchecked")
	public Issue getIssueByLink(String link) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT i FROM Issue i WHERE i.link = :link");
		query.setParameter("link", link);
		List<Issue> ret = query.list();

		return ret.size() == 0 ? null : ret.get(0);
	}
}
