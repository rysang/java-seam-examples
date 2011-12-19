package org.cpcs.dao.services.beans;

import java.io.Serializable;

public class Organization implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  private String memberState;
  private String name;
  private String shortName;
  private String longName;
  private boolean deleted;

  private String role;

  private String roleId;
  private String msId;

  public Organization() {

  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setMemberState(String memberState) {
    this.memberState = memberState;
  }

  public String getMemberState() {
    return memberState;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
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

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder('[');
    sb.append(getId()).append(" ,");
    sb.append(getName()).append(" ,");
    sb.append(getShortName()).append("]");

    return sb.toString();
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setMsId(String msId) {
    this.msId = msId;
  }

  public String getMsId() {
    return msId;
  }
}
