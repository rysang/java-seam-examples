package org.price.manga.reader.dao.services;

import java.util.Collection;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.price.manga.reader.dao.services.api.UserMgmtDao;
import org.price.manga.reader.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userMgmtDao")
public class UserMgmtDaoBean implements UserMgmtDao {

	private static final Logger LOG = Logger.getLogger("UserMgmt");

	@Autowired
	private SessionFactory sessionFactory;

	public UserMgmtDaoBean() {
		LOG.info("Created user mgmt service ...");
	}

	@Override
	public User createUser(String user, String password) {
		User usr = new User(user, password);
		sessionFactory.getCurrentSession().persist(usr);
		return usr;
	}

	@Override
	public void deleteUser(String user) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"DELETE FROM User WHERE name = :name");
		query.setParameter("name", user);
		query.executeUpdate();
	}

	@Override
	public User getUser(String name) {
		return (User) sessionFactory.getCurrentSession().get(User.class, name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> getUsers() {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"SELECT u FROM User u");
		return query.list();
	}
}
