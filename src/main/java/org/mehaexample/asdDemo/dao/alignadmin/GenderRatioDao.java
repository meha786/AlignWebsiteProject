package org.mehaexample.asdDemo.dao.alignadmin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.model.alignadmin.GenderRatio;

import java.util.List;

public class GenderRatioDao {
  private SessionFactory factory;

  public GenderRatioDao() {
    // it will check the hibernate.cfg.xml file and load it
    // next it goes to all table files in the hibernate file and loads them
    factory = new Configuration().configure().buildSessionFactory();
  }

  public List<GenderRatio> getYearlyGenderRatio(Campus campus) {
    String hql = "SELECT NEW org.mehaexample.asdDemo.model.alignadmin.GenderRatio(s.entryYear, " +
            "COUNT(CASE s.gender WHEN 'M' THEN 1 ELSE NULL END), " +
            "COUNT(CASE s.gender WHEN 'F' THEN 1 ELSE NULL END)) " +
            "FROM Students s " +
            "WHERE s.campus = :campus " +
            "GROUP BY s.entryYear " +
            "ORDER BY s.entryYear ASC ";
    List<GenderRatio> list;
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(hql);
      query.setParameter("campus", campus);
      list = query.list();
    } finally {
      session.close();
    }
    return list;
  }
}
