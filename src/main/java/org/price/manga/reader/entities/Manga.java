package org.price.manga.reader.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "manga")
public class Manga implements Serializable, Identifiable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "name", unique = true, nullable = false, length = 250)
	private String name;

	@Column(name = "alternate_name", unique = true, length = 250)
	private String alternateName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified", nullable = false)
	private final Date lastModified = new Date();

	@Column(name = "year_released")
	private String yearReleased;

	@Column(name = "author", length = 100)
	private String author;

	@Column(name = "artist", length = 100)
	private String artist;

	@Column(name = "status", length = 100)
	private String status;

	@Column(name = "reading_direction", length = 100)
	private String readingDirection;

	@OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Genre> genres = new HashSet<>();

	@OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Issue> issues = new HashSet<>();

	// Hibernate.createBlob(..)
	@Lob
	@Column(name = "image")
	private byte[] image;

	public Manga() {
	}

	public String getYearReleased() {
		return yearReleased;
	}

	public void setYearReleased(String yearReleased) {
		this.yearReleased = yearReleased;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReadingDirection() {
		return readingDirection;
	}

	public void setReadingDirection(String readingDirection) {
		this.readingDirection = readingDirection;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public Set<Issue> getIssues() {
		return issues;
	}

	public void setIssues(Set<Issue> issues) {
		this.issues = issues;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Date getLastModified() {
		return lastModified;
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

	public String getAlternateName() {
		return alternateName;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
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

	@Override
	public String toString() {
		return "Manga [name=" + name + ", alternateName=" + alternateName
				+ ", lastModified=" + lastModified + ", yearReleased="
				+ yearReleased + ", author=" + author + ", artist=" + artist
				+ ", status=" + status + ", readingDirection="
				+ readingDirection + ", genres=" + genres + "]";
	}

}