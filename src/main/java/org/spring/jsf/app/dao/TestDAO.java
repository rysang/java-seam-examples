package org.spring.jsf.app.dao;

import java.util.List;

public interface TestDAO {
  public void save(TestBean testBean);

  public List<TestBean> list();
}
