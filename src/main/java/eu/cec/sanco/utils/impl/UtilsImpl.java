package eu.cec.sanco.utils.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.cec.sanco.beans.AppState;
import eu.cec.sanco.utils.api.Utils;

@Scope("singleton")
@Component("utils")
public class UtilsImpl implements Utils {

  private List<SelectItem> availableCountries;

  @Autowired
  private AppState appState;

  private static char[] ALL_CHARS = null;

  private static final Random RANDOM = new Random(System.currentTimeMillis());

  public String rebuildString(String str) {
    String[] split = str.replaceAll("[^A-Za-z]", " ").split("\\s");
    StringBuilder ret = new StringBuilder();
    List<String> tmp = new ArrayList<String>(split.length);

    for (String s : split) {
      if (!StringUtils.isEmpty(s.trim())) {
        tmp.add(s.trim().toLowerCase());
      }
    }

    for (int i = 0; i < tmp.size(); i++) {
      ret.append(tmp.get(i));
      if (i < (tmp.size() - 1)) {
        ret.append('.');
      }
    }

    return ret.toString();
  }
  
  public Date getNow() {
    return new Date();
  }

  public List<SelectItem> getAvailableCountries() {
    // if (availableCountries == null) {
    availableCountries = new ArrayList<SelectItem>(20);

    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "appmsg");

    availableCountries.add(new SelectItem("AT", bundle.getString("country.name.at")));
    availableCountries.add(new SelectItem("BE", bundle.getString("country.name.be")));
    availableCountries.add(new SelectItem("BG", bundle.getString("country.name.bg")));
    availableCountries.add(new SelectItem("CY", bundle.getString("country.name.cy")));
    availableCountries.add(new SelectItem("CZ", bundle.getString("country.name.cz")));
    availableCountries.add(new SelectItem("DK", bundle.getString("country.name.dk")));
    availableCountries.add(new SelectItem("EE", bundle.getString("country.name.ee")));
    availableCountries.add(new SelectItem("FI", bundle.getString("country.name.fi")));
    availableCountries.add(new SelectItem("FR", bundle.getString("country.name.fr")));
    availableCountries.add(new SelectItem("DE", bundle.getString("country.name.de")));
    availableCountries.add(new SelectItem("GR", bundle.getString("country.name.gr")));
    availableCountries.add(new SelectItem("HU", bundle.getString("country.name.hu")));
    availableCountries.add(new SelectItem("IS", bundle.getString("country.name.is")));
    availableCountries.add(new SelectItem("IE", bundle.getString("country.name.ie")));
    availableCountries.add(new SelectItem("IT", bundle.getString("country.name.it")));
    availableCountries.add(new SelectItem("LV", bundle.getString("country.name.lv")));
    availableCountries.add(new SelectItem("LI", bundle.getString("country.name.li")));
    availableCountries.add(new SelectItem("LT", bundle.getString("country.name.lt")));
    availableCountries.add(new SelectItem("LU", bundle.getString("country.name.lu")));
    availableCountries.add(new SelectItem("MT", bundle.getString("country.name.mt")));
    availableCountries.add(new SelectItem("NL", bundle.getString("country.name.nl")));
    availableCountries.add(new SelectItem("NO", bundle.getString("country.name.no")));
    availableCountries.add(new SelectItem("PL", bundle.getString("country.name.pl")));
    availableCountries.add(new SelectItem("PT", bundle.getString("country.name.pt")));
    availableCountries.add(new SelectItem("RO", bundle.getString("country.name.ro")));
    availableCountries.add(new SelectItem("SK", bundle.getString("country.name.sk")));
    availableCountries.add(new SelectItem("SI", bundle.getString("country.name.si")));
    availableCountries.add(new SelectItem("ES", bundle.getString("country.name.es")));
    availableCountries.add(new SelectItem("SE", bundle.getString("country.name.se")));
    availableCountries.add(new SelectItem("CH", bundle.getString("country.name.ch")));
    availableCountries.add(new SelectItem("UK", bundle.getString("country.name.uk")));
    availableCountries.add(new SelectItem("AU", bundle.getString("country.name.au")));
    availableCountries.add(new SelectItem("BR", bundle.getString("country.name.br")));
    availableCountries.add(new SelectItem("CA", bundle.getString("country.name.ca")));
    availableCountries.add(new SelectItem("CN", bundle.getString("country.name.cn")));
    availableCountries.add(new SelectItem("HR", bundle.getString("country.name.hr")));
    availableCountries.add(new SelectItem("SR", bundle.getString("country.name.sr")));
    availableCountries.add(new SelectItem("IN", bundle.getString("country.name.in")));
    availableCountries.add(new SelectItem("MX", bundle.getString("country.name.mx")));
    availableCountries.add(new SelectItem("RU", bundle.getString("country.name.ru")));
    availableCountries.add(new SelectItem("ZA", bundle.getString("country.name.za")));
    availableCountries.add(new SelectItem("TR", bundle.getString("country.name.tr")));
    availableCountries.add(new SelectItem("US", bundle.getString("country.name.us")));
    availableCountries.add(new SelectItem("Other", bundle.getString("country.name.other")));
    availableCountries.add(new SelectItem("Do not know", bundle.getString("country.name.do.not.know")));

    // }

    return availableCountries;
  }

  public String getCountryShort(String countryName) {

    if ("Austria".equals(countryName))
      return "AT";

    if ("Belgium".equals(countryName))
      return "BE";

    if ("Bulgaria".equals(countryName))
      return "BG";

    if ("Cyprus".equals(countryName))
      return "CY";

    if ("Czech Republic".equals(countryName))
      return "CZ";

    if ("Denmark".equals(countryName))
      return "DK";

    if ("Estonia".equals(countryName))
      return "EE";

    if ("Finland".equals(countryName))
      return "FI";

    if ("France".equals(countryName))
      return "FR";

    if ("Germany".equals(countryName))
      return "DE";

    if ("Greece".equals(countryName))
      return "GR";

    if ("Hungary".equals(countryName))
      return "HU";

    if ("Iceland".equals(countryName))
      return "IS";

    if ("Ireland".equals(countryName))
      return "IE";

    if ("Italy".equals(countryName))
      return "IT";

    if ("Latvia".equals(countryName))
      return "LV";

    if ("Liechtenstein".equals(countryName))
      return "LI";

    if ("Lithuania".equals(countryName))
      return "LT";

    if ("Luxembourg".equals(countryName))
      return "LU";

    if ("Malta".equals(countryName))
      return "MT";

    if ("Netherlands".equals(countryName))
      return "NL";

    if ("Norway".equals(countryName))
      return "NO";

    if ("Poland".equals(countryName))
      return "PL";

    if ("Portugal".equals(countryName))
      return "PT";

    if ("Romania".equals(countryName))
      return "RO";

    if ("Slovakia".equals(countryName))
      return "SK";

    if ("Slovenia".equals(countryName))
      return "SI";

    if ("Spain".equals(countryName))
      return "ES";

    if ("Sweden".equals(countryName))
      return "SE";

    if ("Switzerland".equals(countryName))
      return "CH";

    if ("United Kingdom".equals(countryName))
      return "UK";

    return "Other";

  }

  public static char[] getALL_CHARS() {
    List<Character> chars = new ArrayList<Character>(40);

    if (ALL_CHARS == null) {
      for (char c = 'a'; c <= 'z'; ++c) {
        chars.add(c);
      }

      for (char c = 'A'; c <= 'Z'; ++c) {
        chars.add(c);
      }

      for (char c = '0'; c <= '9'; ++c) {
        chars.add(c);
      }

      ALL_CHARS = new char[chars.size()];
      for (int i = 0; i < chars.size(); i++) {
        ALL_CHARS[i] = chars.get(i);
      }
    }

    return ALL_CHARS;
  }

  public String generateUser(String country) {
    getALL_CHARS();

    StringBuilder sb = new StringBuilder(getCountryShort(country)).append('-');
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);
    sb.append('-');
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);
    sb.append(ALL_CHARS[Math.abs(RANDOM.nextInt(ALL_CHARS.length))]);

    return sb.toString();
  }

  public static final String getDigitsFromLogin(String login, int index) {
    StringBuilder sb = new StringBuilder();

    for (int i = index; i < index + 3; i++) {
      sb.append((int) login.charAt(i));
    }

    return sb.substring(0, 6);
  }

  public static final String getKey(String login, int index) {
    final String first = getDigitsFromLogin(login, index);
    final String last = getDigitsFromLogin(login, index + 2);

    return (calculate(first) + "" + last);
  }

  public static final int calculate(String l) {
    int sum = 0;
    for (int i = 0; i < l.length(); i++) {
      sum += Integer.parseInt("" + l.charAt(i));
    }

    int[] delta = new int[] { 0, 1, 2, 3, 4, -4, -3, -2, -1, 0 };

    for (int i = l.length() - 1; i > 0; i += -2) {
      int delta_index = Integer.parseInt(l.substring(i, i + 1));
      int delta_value = delta[delta_index];
      sum += delta_value;
    }

    int mod10 = 10 - (sum % 10);
    if (mod10 == 10)
      return 0;

    return mod10;
  }

  public List<String> getKeys(String login) {
    ArrayList<String> ret = new ArrayList<String>();

    for (int i = 0; i < login.length() - 4; i++) {
      ret.add(getKey(login, i));
    }

    return ret;
  }

  public static void main(String[] args) {
    System.out.println(new UtilsImpl().getKeys("EE-V1-z38"));
  }

  public String getVersion() {
    return appState.getVersion();
  }
}
