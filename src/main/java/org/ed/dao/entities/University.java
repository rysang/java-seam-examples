package org.ed.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class University extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "u_name", length = 255, nullable = false)
	private String uName;

	@Column(name = "u_ft_name", length = 255, nullable = false)
	private String uFtName;

	@Column(name = "u_description", length = 2048, nullable = false)
	private String uDescription;

	@Column(name = "u_country_code", length = 8, nullable = false)
	private String uCountryCode;

	@Column(name = "u_address", length = 255, nullable = false)
	private String uAddress;

	@Column(name = "u_email", length = 255, nullable = false)
	private String uEmail;

	@Column(name = "u_contact", length = 2048, nullable = false)
	private String uContact;

	public University() {

	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuName() {
		return uName;
	}

	public void setuFtName(String uFtName) {
		this.uFtName = uFtName;
	}

	public String getuFtName() {
		return uFtName;
	}

	public void setuDescription(String uDescription) {
		this.uDescription = uDescription;
	}

	public String getuDescription() {
		return uDescription;
	}

	public void setuCountryCode(String uCountryCode) {
		this.uCountryCode = uCountryCode;
	}

	public String getuCountryCode() {
		return uCountryCode;
	}

	public void setuAddress(String uAddress) {
		this.uAddress = uAddress;
	}

	public String getuAddress() {
		return uAddress;
	}

	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getuEmail() {
		return uEmail;
	}

	public void setuContact(String uContact) {
		this.uContact = uContact;
	}

	public String getuContact() {
		return uContact;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder('[');
		sb.append(getUid()).append(" ,");
		sb.append(getuName()).append(" ,");
		sb.append(getuFtName()).append(" ,");
		sb.append(getuDescription()).append(" ,");
		sb.append(getuCountryCode()).append(" ,");
		sb.append(getuAddress()).append(" ,");
		sb.append(getuEmail()).append(" ,");
		sb.append(getuContact()).append(" ]");

		return sb.toString();
	}
}
