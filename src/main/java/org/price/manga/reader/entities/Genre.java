package org.price.manga.reader.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "genres", uniqueConstraints = @UniqueConstraint(columnNames = {
		"name", "manga_id" }, name = "name_manga_idx"))
public class Genre implements Serializable, Identifiable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "name", unique = true, nullable = false, length = 50)
	private String name;

	@ManyToOne
	@JoinColumn(name = "manga_id")
	private Manga manga;

	public Genre() {

	}

	public Genre(String name, Manga manga) {
		super();
		this.name = name;
		this.manga = manga;
	}

	public Manga getManga() {
		return manga;
	}

	public void setManga(Manga manga) {
		this.manga = manga;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Genre other = (Genre) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", name=" + name + ", manga=" + manga + "]";
	}

}