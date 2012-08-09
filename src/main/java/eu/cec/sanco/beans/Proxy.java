package eu.cec.sanco.beans;

public class Proxy {

  private String host;
  private int port;
  private String username;
  private String password;

  public Proxy() {

  }

  public Proxy(String host, int port) {
    super();
    this.host = host;
    this.port = port;
  }

  public Proxy(String host, int port, String username, String password) {
    super();
    this.host = host;
    this.port = port;
    this.username = username;
    this.password = password;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return String.format("Proxy [host=%s, port=%s, username=%s, password=%s]", host, port, username, password);
  }

}
