package eu.cec.sanco.json;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class TestMessages1Converter extends TestCase {
  private static final Logger LOG = Logger.getLogger(TestMessages1Converter.class);
  private static final Gson gson = new Gson();

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public static final String rebuildString(String str) {
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

  public void testMessagesConverter() throws Exception {

    File dir = new File("src/main/resources/org/imported/msg1").getAbsoluteFile();
    File dest = new File("src/main/resources/org/msg/i18n").getAbsoluteFile();

    assertTrue(dir.isDirectory());

    LOG.info(dir);

    File[] files = dir.listFiles(new FileFilter() {

      public boolean accept(File f) {
        return f.getName().endsWith(".json");
      }
    });

    LOG.info(files.length);

    for (File f : files) {
      Map<String, Object> o = gson.fromJson(new InputStreamReader(new FileInputStream(f), Charset.forName("UTF-8")),
          Map.class);

      LOG.info(o);

      String name = f.getName().substring(0, f.getName().indexOf('.'));
      File destFile = new File(dest, name + ".properties");
      destFile.delete();
      destFile.createNewFile();

      Map<String, Object> levels = (Map<String, Object>) o.get("levels");
      LOG.info(levels);

      FileOutputStream fos = new FileOutputStream(destFile);
      OutputStreamWriter w = new OutputStreamWriter(fos, Charset.forName("UTF-8"));

      Map<String, Object> qaGoodsServices = (Map<String, Object>) levels.get("Quality of goods and services");
      w.append("levels.").append(rebuildString("Quality of goods and services")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      List<Map<String, Object>> values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Quality of goods and services")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Delivery of goods/ Provision of services");
      w.append("levels.").append(rebuildString("Delivery of goods/ Provision of services")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Delivery of goods/ Provision of services")).append('.').append(v)
            .append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Price / Tariff");
      w.append("levels.").append(rebuildString("Price / Tariff")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Price / Tariff")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Invoicing / billing and debt collection");
      w.append("levels.").append(rebuildString("Invoicing / billing and debt collection")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Invoicing / billing and debt collection")).append('.').append(v)
            .append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Warranty / statutory guarantee and commercial guarantees");
      w.append("levels.").append(rebuildString("Warranty / statutory guarantee and commercial guarantees"))
          .append(".value=").append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Warranty / statutory guarantee and commercial guarantees"))
            .append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Redress");
      w.append("levels.").append(rebuildString("Redress")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Redress")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Unfair Commercial Practices");
      w.append("levels.").append(rebuildString("Unfair Commercial Practices")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Unfair Commercial Practices")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Contracts and sales");
      w.append("levels.").append(rebuildString("Contracts and sales")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Contracts and sales")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Provider change / switching");
      w.append("levels.").append(rebuildString("Provider change / switching")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Provider change / switching")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Safety - covers both goods (including food) and services");
      w.append("levels.").append(rebuildString("Safety - covers both goods (including food) and services"))
          .append(".value=").append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Safety - covers both goods (including food) and services"))
            .append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Privacy and data protection");
      w.append("levels.").append(rebuildString("Privacy and data protection")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Privacy and data protection")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Other issues");
      w.append("levels.").append(rebuildString("Other issues")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("levels.").append(rebuildString("Other issues")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      // sectors
      levels = (Map<String, Object>) o.get("sectors");

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Consumer Goods");
      w.append("sectors.").append(rebuildString("Consumer Goods")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Consumer Goods")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("General Consumer Services");
      w.append("sectors.").append(rebuildString("General Consumer Services")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("General Consumer Services")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Financial Services");
      w.append("sectors.").append(rebuildString("Financial Services")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Financial Services")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Postal services and electronic communications");
      w.append("sectors.").append(rebuildString("Postal services and electronic communications")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Postal services and electronic communications")).append('.')
            .append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Transport services");
      w.append("sectors.").append(rebuildString("Transport services")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Transport services")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Leisure Services");
      w.append("sectors.").append(rebuildString("Leisure Services")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Leisure Services")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Energy and Water");
      w.append("sectors.").append(rebuildString("Energy and Water")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Energy and Water")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Health");
      w.append("sectors.").append(rebuildString("Health")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Health")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Education");
      w.append("sectors.").append(rebuildString("Education")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Education")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.append('\n');

      qaGoodsServices = (Map<String, Object>) levels.get("Other");
      w.append("sectors.").append(rebuildString("Other")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("sectors.").append(rebuildString("Other")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      // country
      levels = (Map<String, Object>) o.get("country");

      w.append('\n');

      qaGoodsServices = levels;
      w.append("country.").append(rebuildString("name")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("country.").append(rebuildString("name")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      // distinction
      levels = (Map<String, Object>) o.get("distinction");

      w.append('\n');

      qaGoodsServices = levels;
      w.append("distinction.").append(rebuildString("name")).append(".value=")
          .append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("distinction.").append(rebuildString("name")).append('.').append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      // selling_method
      levels = (Map<String, Object>) o.get("selling_method");

      w.append('\n');

      qaGoodsServices = levels;
      w.append("selling.method").append(".value=").append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("selling.method.").append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      // advertising_method
      levels = (Map<String, Object>) o.get("advertising_method");

      w.append('\n');

      qaGoodsServices = levels;
      w.append("advertising.method").append(".value=").append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("advertising.method.").append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      // payment_means
      levels = (Map<String, Object>) o.get("payment_means");

      w.append('\n');

      qaGoodsServices = levels;
      w.append("payment.means").append(".value=").append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("payment.means.").append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      // transaction_currency
      levels = (Map<String, Object>) o.get("transaction_currency");

      w.append('\n');

      qaGoodsServices = levels;
      w.append("transaction.currency").append(".value=").append(qaGoodsServices.get("value").toString()).append("\n");

      values = (List<Map<String, Object>>) qaGoodsServices.get("values");

      for (Map<String, Object> val : values) {
        String v = rebuildString(val.get("value").toString());
        w.append("transaction.currency.").append(v).append('=');
        w.append(val.get("label").toString()).append('\n');
      }

      w.flush();
      fos.close();
    }
  }
}
