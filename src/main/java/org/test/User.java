package org.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.jboss.seam.annotations.security.management.UserEnabled;
import org.jboss.seam.annotations.security.management.UserFirstName;
import org.jboss.seam.annotations.security.management.UserLastName;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.test.datalists.Idable;

@Entity
@Table(name = "user")
public class User implements Idable<String> {

	@Id
	@Column(name = "id")
	private String id;

	@NotEmpty
	@Column(name = "first_name")
	@UserFirstName
	private String firstName;

	@NotEmpty
	@Column(name = "second_name")
	@UserLastName
	private String secondName;

	@Email
	@NotEmpty
	@Column(name = "email")
	@UserPrincipal
	private String email;

	@UserEnabled
	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "administrator")
	private boolean administrator;

	@NotEmpty
	@Length(min = 6, max = 100)
	@Column(name = "password")
	@UserPassword(hash = "none")
	private String password;

	public User() {
		setEnabled(true);
		setAdministrator(false);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSecondName() {
		return secondName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder('[');
		sb.append(getId());
		sb.append(',');
		sb.append(getFirstName());
		sb.append(',');
		sb.append(getSecondName());
		sb.append(']');

		return sb.toString();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public boolean getAdministrator() {
		return administrator;
	}
}
