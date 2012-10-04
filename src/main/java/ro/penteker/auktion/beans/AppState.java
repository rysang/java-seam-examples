package ro.penteker.auktion.beans;

public class AppState {
  private String lang;
  private String version;

  public AppState() {

  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return String.format("AppState [lang=%s, version=%s]", lang, version);
  }

}
