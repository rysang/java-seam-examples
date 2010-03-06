package org.test;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Scope(ScopeType.SESSION)
@Name("test")
public class Test implements Serializable {

	public String goToIndex() {
		return "INDEX";
	}

	public static void main(String[] args) {
		EntityManager em = EntityManagerCreator.createManager();
		User usr = new User();
		usr.setId(UUID.randomUUID().toString());
		usr.setAdministrator(true);
		usr.setEmail("admin@admin.com");
		usr.setPassword("admin01");
		usr.setEnabled(true);
		usr.setFirstName("Admin");
		usr.setSecondName("Admin");

		em.getTransaction().begin();
		em.persist(usr);
		em.getTransaction().commit();

		em.close();
	}
}