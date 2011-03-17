package org.ed.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public class Repository {

	@PersistenceContext
	private EntityManager entityManager;

	public Repository() {
	}

	@Transactional
	public void saveObject(Object object) {
		entityManager.persist(object);
	}

	@SuppressWarnings("unchecked")
	public Object findObjectById(Class objectClass, Object id) {
		return entityManager.find(objectClass, id);
	}
}
