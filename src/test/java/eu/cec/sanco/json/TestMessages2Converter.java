package eu.cec.sanco.json;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

public class TestMessages2Converter extends TestCase {
  private static final Logger LOG = Logger.getLogger(TestMessages2Converter.class);
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

  public Map<String, String> getProperties(Map<String, Object> o, String prefix, Map<String, String> ret) {
    Set<String> keys = o.keySet();
    if (ret == null) {
      ret = new Hashtable<String, String>();
    }

    for (String key : keys) {
      Object obj = o.get(key);
      if (obj instanceof String) {
        ret.put(prefix != null ? (prefix + '.' + key) : key, obj.toString());
      } else if (obj instanceof Map) {
        getProperties((Map<String, Object>) obj, prefix != null ? (prefix + '.' + key) : key, ret);
      }
    }

    return ret;
  }

  public void testMessagesConverter() throws Exception {

    File dir = new File("src/main/resources/org/imported/msg2").getAbsoluteFile();
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

      String name = "controls_msg_" + f.getName().substring(0, f.getName().indexOf('.'));
      File destFile = new File(dest, name + ".properties");
      destFile.delete();
      destFile.createNewFile();

      Map<String, String> props = getProperties(o, null, null);

      FileOutputStream fos = new FileOutputStream(destFile);
      OutputStreamWriter w = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
      Set<String> keys = new TreeSet<String>(props.keySet());

      for (String key : keys) {
        w.append(rebuildString(key)).append('=').append(props.get(key)).append('\n');
      }

      w.flush();
      fos.close();
    }
  }
}
