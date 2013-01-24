package org.manga.reader;

import java.util.Set;
import java.util.TreeSet;

public class Doc {
  private String id;
  private String title;
  private String description;
  private Set<String> genres = new TreeSet<String>();
  private String year;

  // Advanced
  private String status;
  private String author;
  private String artist;
  private String readingDirection;
  private String alternateName;

  public Doc() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<String> getGenres() {
    return genres;
  }

  public void setGenres(Set<String> genres) {
    this.genres = genres;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public String getReadingDirection() {
    return readingDirection;
  }

  public void setReadingDirection(String readingDirection) {
    this.readingDirection = readingDirection;
  }

  public void setAlternateName(String alternateName) {
    this.alternateName = alternateName;
  }

  public String getAlternateName() {
    return alternateName;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return String
        .format(
            "Doc [id=%s, title=%s, description=%s, genres=%s, year=%s, status=%s, author=%s, artist=%s, readingDirection=%s, alternateName=%s]",
            id, title, description, genres, year, status, author, artist, readingDirection, alternateName);
  }

}
