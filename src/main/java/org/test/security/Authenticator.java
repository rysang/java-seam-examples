package org.test.security;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.test.EntityManagerCreator;
import org.test.User;

@Scope(ScopeType.SESSION)
@Name("authenticator")
public class Authenticator {
	@Logger
	private Log log;

	@In
	private Credentials credentials;

	@In
	private Identity identity;

	private EntityManager entityManager;
	private final static String query = "select u from User u where u.email=:email and u.password = :password and enabled=true";

	public Authenticator() {

	}

	@Create
	public void create() {
		log.info(" Bean Created.");
		entityManager = EntityManagerCreator.createManager();
	}

	@Destroy
	public void destroy() {
		log.info(" Bean Destroyed.");
		entityManager.close();
	}

	public boolean authenticate() {
		log.debug("Authenticating username:" + credentials.getUsername());
		try {
			User result = (User) entityManager.createQuery(query).setParameter(
					"email", credentials.getUsername()).setParameter(
					"password", credentials.getPassword()).getSingleResult();
			log.debug("Authentication successful:" + credentials.getUsername());
			identity.addRole("USER");
			if (result.getAdministrator()) {
				identity.addRole("ADMINISTRATOR");
			}
			log.info("Authenticated username:" + credentials.getUsername());
			return true;
		} catch (Exception e) {
			log.debug("Failed to authenticate user:"
					+ credentials.getUsername());
			return false;
		}
	}
}