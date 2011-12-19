package org.cpcs.dao.services.beans;

public class MemberState {
  private String id;
  private String description;
  private String code;

  public MemberState() {

  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder('[');
    sb.append(getId()).append(" ,");
    sb.append(getCode()).append(" ,");
    sb.append(getDescription()).append("]");

    return sb.toString();
  }
}
