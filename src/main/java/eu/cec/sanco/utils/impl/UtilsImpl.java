package eu.cec.sanco.utils.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.cec.sanco.utils.api.Utils;

@Scope("singleton")
@Component("utils")
public class UtilsImpl implements Utils {

  private List<SelectItem> availableCountries;

  private static char[] ALL_CHARS = null;

  private static final Random RANDOM = new Random(System.currentTimeMillis());

  public List<SelectItem> getAvailableCountries() {
    if (availableCountries == null) {
      availableCountries = new ArrayList<SelectItem>(20);

      availableCountries.add(new SelectItem("Austria"));
      availableCountries.add(new SelectItem("Belgium"));
      availableCountries.add(new SelectItem("Bulgaria"));
      availableCountries.add(new SelectItem("Cyprus"));
      availableCountries.add(new SelectItem("Czech Republic"));
      availableCountries.add(new SelectItem("Denmark"));
      availableCountries.add(new SelectItem("Estonia"));
      availableCountries.add(new SelectItem("Finland"));
      availableCountries.add(new SelectItem("France"));
      availableCountries.add(new SelectItem("Germany"));
      availableCountries.add(new SelectItem("Greece"));
      availableCountries.add(new SelectItem("Hungary"));
      availableCountries.add(new SelectItem("Iceland"));
      availableCountries.add(new SelectItem("Ireland"));
      availableCountries.add(new SelectItem("Italy"));
      availableCountries.add(new SelectItem("Latvia"));
      availableCountries.add(new SelectItem("Liechtenstein"));
      availableCountries.add(new SelectItem("Lithuania"));
      availableCountries.add(new SelectItem("Luxembourg"));
      availableCountries.add(new SelectItem("Malta"));
      availableCountries.add(new SelectItem("Netherlands"));
      availableCountries.add(new SelectItem("Norway"));
      availableCountries.add(new SelectItem("Poland"));
      availableCountries.add(new SelectItem("Portugal"));
      availableCountries.add(new SelectItem("Romania"));
      availableCountries.add(new SelectItem("Slovakia"));
      availableCountries.add(new SelectItem("Slovenia"));
      availableCountries.add(new SelectItem("Spain"));
      availableCountries.add(new SelectItem("Sweden"));
      availableCountries.add(new SelectItem("Switzerland"));
      availableCountries.add(new SelectItem("United Kingdom"));
    }

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
}
