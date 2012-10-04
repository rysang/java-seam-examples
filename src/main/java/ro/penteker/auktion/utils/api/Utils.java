package ro.penteker.auktion.utils.api;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public interface Utils {
  public List<SelectItem> getLevel1Classifications();

  public List<SelectItem> getCurrencies();

  public List<SelectItem> getPaymentMeans();

  public List<SelectItem> getLevel2Classifications(String level1);

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

  public List<SelectItem> getYears();

  public String getVersion();

  public String getCountry();

  public void reset();

  public void setTheme(String theme);

  public String getTheme();
  
  public long getNextCid();
}
