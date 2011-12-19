package org.cpcs.dao.services.beans;

public class Role {
  private long id;
  private String code;
  private String description;
  private boolean internalRole;

  public Role() {

  }

  public Role(long id) {
    setId(id);
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setInternalRole(boolean internalRole) {
    this.internalRole = internalRole;
  }

  public boolean isInternalRole() {
    return internalRole;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder('[');
    sb.append(getCode()).append(" ,");
    sb.append(getDescription()).append(']');

    return sb.toString();
  }

}
