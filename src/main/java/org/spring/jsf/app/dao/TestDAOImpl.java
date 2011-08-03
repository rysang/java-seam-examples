package org.spring.jsf.app.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

public class TestDAOImpl implements TestDAO {

  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional
  public void save(TestBean testBean) {
    this.sessionFactory.getCurrentSession().saveOrUpdate(testBean);
  }

  @Transactional(readOnly = true)
  public List<TestBean> list() {
    return this.sessionFactory.getCurrentSession().createQuery("select tb from TestBean tb").list();
  }

}
