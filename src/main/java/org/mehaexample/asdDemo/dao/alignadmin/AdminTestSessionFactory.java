package org.mehaexample.asdDemo.dao.alignadmin;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AdminTestSessionFactory {
  private static SessionFactory factory;

  static {
    factory = new Configuration()
//    .configure("/hibernate_Admin.cfg.xml").buildSessionFactory();
    .configure("/hibernate_admin_test.cfg.xml").buildSessionFactory();
  }

  public static SessionFactory getFactory() {
    return factory;
  }
}
