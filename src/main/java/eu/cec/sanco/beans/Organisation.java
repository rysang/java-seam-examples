package eu.cec.sanco.beans;

public class Organisation {
  private String organisation_id;
  private String country;
  private String pwd;
  private String username;

  public Organisation() {

  }

  public String getOrganisation_id() {
    return organisation_id;
  }

  public void setOrganisation_id(String organisation_id) {
    this.organisation_id = organisation_id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return String.format("Organisation [organisation_id=%s, country=%s, pwd=%s, username=%s]", organisation_id,
        country, pwd, username);
  }

}
