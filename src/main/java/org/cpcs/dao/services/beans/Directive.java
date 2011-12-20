package org.cpcs.dao.services.beans;

public class Directive {
  private String id;
  private String code;
  private String description;
  private String comDirId;
  private boolean active;
  private String compendiumUrl;

  public Directive() {

  }

  public Directive(String id) {
    setId(id);
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
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

  public void setComDirId(String comDirId) {
    this.comDirId = comDirId;
  }

  public String getComDirId() {
    return comDirId;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return active;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder('[');
    sb.append(getCode()).append(" ,");
    sb.append(getDescription()).append(']');

    return sb.toString();
  }

  public void setCompendiumUrl(String compendiumUrl) {
    this.compendiumUrl = compendiumUrl;
  }

  public String getCompendiumUrl() {
    return compendiumUrl;
  }
}
