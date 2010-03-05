package org.test;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Scope(ScopeType.SESSION)
@Name("test")
public class Test implements Serializable {
	public static void main(String[] args) {
		EntityManager em = EntityManagerCreator.createManager();
		Query q = em
				.createQuery("select count(u.firstName), u.firstName from User u group by u.firstName");
		q.setFirstResult(1);
		q.setMaxResults(4);

		List<Object[]> ls = q.getResultList();

		System.out.println(ls.size() > 0 ? ls.get(0)[0] : -1);
		System.out.println(ls.size());
	}
}