package org.price.test.beans;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.price.test.conversion.annotations.Store;

public class Contact implements Identifiable {

	public static final String NAME = "Contact";

	private String id;

	@NotEmpty
	@Length(min = 2, max = 50)
	@Store
	private String firstname;

	@NotEmpty
	@Store
	private String lastname;

	@NotEmpty
	@Store
	private String email;

	@NotEmpty
	@Store
	private String telephone;

	public Contact() {

	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstname=" + firstname + ", lastname="
				+ lastname + ", email=" + email + ", telephone=" + telephone
				+ "]";
	}

}