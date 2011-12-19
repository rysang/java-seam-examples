package org.cpcs.dao.services.beans.saas;

public class RoleGeneric {
  private int id;
  private String longName;
  private String shortName;
  private String comments;

  public RoleGeneric() {

  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setLongName(String longName) {
    this.longName = longName;
  }

  public String getLongName() {
    return longName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public String getComments() {
    return comments;
  }
}
