package org.price.manga.reader.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User implements Serializable, Identifiable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;

	@Column(name = "password", nullable = false, length = 50)
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 50)
	private Date created = new Date();

	public User() {

	}

	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	@Override
	public String getId() {
		return getName();
	}

	@Override
	public void setId(String id) {
		setName(id);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Date getCreated() {
		return created;
	}

	public String getPassword() {
		return password;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", created="
				+ created + "]";
	}

}