package eu.cec.sanco.utils.api;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public interface Utils {
  public List<SelectItem> getAvailableCountries();

  public List<SelectItem> getSellingMethods();

  public List<SelectItem> getSectors();
  
  public List<SelectItem> getMarkets(String sector);

  public List<SelectItem> getAdvertisingMethods();

  public List<String> getKeys(String login);

  public String getCountryShort(String countryName);

  public String generateUser(String country);

  public String rebuildString(String str);

  public Date getNow();
}
