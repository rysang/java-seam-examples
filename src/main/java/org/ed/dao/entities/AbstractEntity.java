package org.ed.dao.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "euid", unique = true, nullable = false, updatable = false, length = 36)
	private String uid;

	@Version
	private Integer version;

	@Override
	public boolean equals(Object o) {
		return (o == this || (o instanceof AbstractEntity && getUid().equals(
				((AbstractEntity) o).getUid())));
	}

	@Override
	public int hashCode() {
		return getUid().hashCode();
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		if (uid == null)
			uid = UUID.randomUUID().toString();
		return uid;
	}
}
