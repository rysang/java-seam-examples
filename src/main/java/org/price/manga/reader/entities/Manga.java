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
@Table(name = "manga")
public class Manga implements Serializable, Identifiable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified", nullable = false)
	private final Date lastModified = new Date();

	@Column(name = "year_released")
	private Integer yearReleased;

	@Column(name = "author", length = 100)
	private String author;

	@Column(name = "artist", length = 100)
	private String artist;

	public Manga() {

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
		Manga other = (Manga) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}