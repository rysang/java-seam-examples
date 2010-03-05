package org.test.datalists;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.test.EntityManagerCreator;
import org.test.User;

public class LazyFetchList implements ExtendedFetchList<User>, Serializable {
	private static final long serialVersionUID = 1L;
	private int listSize;
	private int maxListSize;

	private EntityManager entityManager;
	private SmallRangeQueryList<User> returnList;

	@SuppressWarnings("unchecked")
	public List<User> fetchList(int startRow, int size) {
		Query q = getEntityManager().createQuery(
				"select count(u), u from User u group by u.id");
		q.setFirstResult(startRow);
		q.setMaxResults(size);

		returnList = new SmallRangeQueryList<User>(q.getResultList());
		setListSize(returnList.size());
		setMaxListSize((int) returnList.getMaxSize());
		return returnList;
	}

	public String getPk(Object obj) {
		if (obj instanceof User)
			return ((User) obj).getId();

		return null;
	}

	public void update() {
	}

	public int getListSize() {
		return listSize;
	}

	public int getMaxListSize() {
		return maxListSize;
	}

	/**
	 * @param listSize
	 *            the listSize to set
	 */
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	/**
	 * @param maxListSize
	 *            the maxListSize to set
	 */
	public void setMaxListSize(int maxListSize) {
		this.maxListSize = maxListSize;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = EntityManagerCreator.createManager();
		}

		return entityManager;
	}
}