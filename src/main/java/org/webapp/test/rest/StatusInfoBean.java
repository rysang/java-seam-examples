package org.webapp.test.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusInfoBean {
  private String version;
  private String name;

  public StatusInfoBean() {
    setVersion("1.0");
    setName("StatusInfo");
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getVersion() {
    return version;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
