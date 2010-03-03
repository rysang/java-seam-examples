package org.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerCreator {

	private static EntityManagerFactory em = null;

	public static EntityManager createManager() {
		if (em == null) {
			em = Persistence.createEntityManagerFactory("test-persistence");
		}

		return em.createEntityManager();
	}
}
