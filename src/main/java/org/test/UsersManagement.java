package org.test;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name("usersManagement")
@Scope(ScopeType.CONVERSATION)
public class UsersManagement implements Serializable {

	private static final long serialVersionUID = -1470908246493192947L;
	public static final String INDEX = "INDEX";
	public static final String CREATE_USER = "CREATE_USER";
	public static final String EDIT_USER = "EDIT_USER";
	public static final String LIST_USERS = "LIST_USERS";

	@Logger
	private transient Log log;

	@In(value = "accessExtendedDataModel", required = true, create = true)
	private AccessExtendedDataModel accessExtendedDataModel;

	private User selectedUser;

	private transient EntityManager em;

	public UsersManagement() {

	}

	@Create
	public void create() {
		log.info(" Bean Created.");
		em = EntityManagerCreator.createManager();
	}

	@Destroy
	public void destroy() {
		log.info(" Bean Destroyed.");
		em.close();
	}

	@Begin(nested = true)
	public String createUser() {
		log.info(" Create User called.");
		return CREATE_USER;
	}

	@Begin(nested = true)
	public String listUsers() {
		log.info(" List Users called.");
		return LIST_USERS;
	}

	@Begin(nested = true)
	public String editUser(User user) {
		log.info(" Edit User called.");
		setSelectedUser(user);
		return EDIT_USER;
	}

	@End
	public String persist(User user) {
		if (user.getId() == null) {
			user.setId(UUID.randomUUID().toString());
		}

		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();

		accessExtendedDataModel.reset();
		setSelectedUser(null);

		return INDEX;
	}

	@End
	public String update(User user) {
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();

		accessExtendedDataModel.reset();
		setSelectedUser(null);

		return INDEX;
	}

	@End
	public String delete(User user) {
		User usr = em.find(User.class, user.getId());
		em.getTransaction().begin();
		em.remove(usr);
		em.getTransaction().commit();

		accessExtendedDataModel.reset();
		setSelectedUser(null);

		return INDEX;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public User getSelectedUser() {
		if (selectedUser == null) {
			selectedUser = new User();
		}
		return selectedUser;
	}
}
