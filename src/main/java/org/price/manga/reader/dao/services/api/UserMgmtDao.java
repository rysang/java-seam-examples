package org.price.manga.reader.dao.services.api;

import java.util.Collection;

import org.price.manga.reader.entities.User;

public interface UserMgmtDao {
	public User createUser(String user, String password);

	public void deleteUser(String user);

	public User getUser(String name);

	public Collection<User> getUsers();
}
