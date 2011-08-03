package org.spring.jsf.app.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;

@Entity
@Table(name = "test_table")
public class TestBean implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  private String id;

  @Index(name = "name_idx")
  @Column(name = "name")
  private String name;

  public TestBean() {

  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
