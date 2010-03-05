package org.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.test.datalists.Idable;

@Entity
@Table(name = "user")
public class User implements Idable<String> {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "second_name")
	private String secondName;

	public User() {

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
}
