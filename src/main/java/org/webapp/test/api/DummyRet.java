package org.webapp.test.api;

public class DummyRet {
  private String name;
  private String surname;
  private int age;

  public DummyRet() {

  }

  public DummyRet(String name, String surname, int age) {
    setName(name);
    setSurname(surname);
    setAge(age);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getSurname() {
    return surname;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getAge() {
    return age;
  }

}
