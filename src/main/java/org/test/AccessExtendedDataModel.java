package org.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.test.datalists.BaseExtendedDataModel;

@Scope(ScopeType.SESSION)
@Name("accessExtendedDataModel")
public class AccessExtendedDataModel extends
		BaseExtendedDataModel<User, String> {

	private EntityManager entityManager;
	private List<User> returnList;
	private Long count = -1L;

	private Integer firstRow;
	private Integer maxResults;

	public AccessExtendedDataModel() {

	}

	@Override
	public Long getCount() {
		if (count == -1) {
			Query q = getEntityManager().createQuery(
					"select count(u) from User u");
			count = (Long) q.getSingleResult();
		}
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public User findById(String id) {
		if (returnList != null) {
			for (User user : returnList) {
				if (user.getId().equals(id)) {
					return user;
				}
			}
		}
		return null;
	}

	@Override
	public List<User> getList(Integer firstRow, Integer maxResults) {
		if (this.firstRow != firstRow || this.maxResults != maxResults) {
			Query q = getEntityManager().createQuery("select u from User u");
			q.setFirstResult(firstRow);
			q.setMaxResults(maxResults);

			returnList = q.getResultList();
			setCount(-1L);
			this.firstRow = firstRow;
			this.maxResults = maxResults;
		}
		return returnList;
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
