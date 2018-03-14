package org.mehaexample.asdDemo.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.model.Electives;

public class ElectivesDao {
  private static SessionFactory factory;
  private static Session session;

  private StudentsDao studentDao;

  /**
   * Default Constructor.
   */
  public ElectivesDao() {
//    coursesDao = new CoursesDao();
    studentDao = new StudentsDao();
    try {
      // it will check the hibernate.cfg.xml file and load it
      // next it goes to all table files in the hibernate file and loads them
      factory = new Configuration()
              .configure("/hibernate_Admin.cfg.xml").buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);

    }
  }

  public List<Electives> getElectivesByNeuId(String neuId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("from Electives where neuId = :neuId");
    query.setParameter("neuId", neuId);
    List<Electives> list = query.list();
    session.close();
    return list;
  }

  public Electives getElectiveById(int electiveId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("from Electives where electiveId = :electiveId");
    query.setParameter("electiveId", electiveId);
    List<Electives> list = query.list();
    session.close();
    if (list.isEmpty()) {
      return null;
    }
    Electives elective = list.get(0);
    return elective;
  }

  /**
   * This is the function to add an Elective for a given student into database.
   *
   * @param elective elective to be added; not null.
   * @return true if insert successfully. Otherwise throws exception.
   */
  public Electives addElective(Electives elective) {
    if (elective == null) {
      return null;
    }

    Transaction tx = null;
    session = factory.openSession();

    if (studentDao.ifNuidExists(elective.getNeuId())) {
      try {
        tx = session.beginTransaction();
        session.save(elective);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        return null;
      } finally {
        session.close();
      }
    } else {
      System.out.println("The student with a given nuid doesn't exists");
      return null;
    }
    return elective;
  }

  public boolean updateElectives(Electives elective) {
    session = factory.openSession();
    Transaction tx = null;
    System.out.println("updating electives in electives table...");
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(elective);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("HibernateException: " + e);
      if (tx != null) tx.rollback();
      return false;
    } finally {
      session.close();
    }
    return true;
  }

  public boolean deleteElectiveRecord(int id) {
    Transaction tx = null;

    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      Electives electives = session.get(Electives.class, id);
      System.out.println("Deleting elective for id = " + id);
      session.delete(electives);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("exceptionnnnnn");
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }

    return true;
  }

  public boolean deleteElectivesByNeuId(String neuId) {
    Transaction tx = null;
    boolean deleted = false;

    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("DELETE FROM Electives " +
              "WHERE neuId = :neuId ");
      query.setParameter("neuId", neuId);
      query.executeUpdate();
      tx.commit();
      deleted = true;
    } catch (HibernateException e) {
      if (tx!=null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }

    return deleted;
  }

  public List<String> getTopTenElectives(Campus campus, Integer year) {
    StringBuilder hql = new StringBuilder("SELECT e.courseId AS CourseId " +
            "FROM Students s INNER JOIN Electives e " +
            "ON s.neuId = e.neuId ");
    boolean first = true;
    if (campus != null) {
      hql.append("WHERE s.campus = :campus ");
      first = false;
    }
    if (year != null) {
      if (first) {
        hql.append("WHERE ");
      } else {
        hql.append("AND ");
      }
      hql.append("e.courseYear = :year ");
    }
    hql.append("GROUP BY CourseId ");
    hql.append("ORDER BY Count(*) DESC ");
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            hql.toString());
    query.setMaxResults(10);
    if (campus != null) {
      query.setParameter("campus", campus);
    }
    if (year != null) {
      query.setParameter("year", year);
    }
    List<String> listOfElectives = query.list();
    session.close();
    return listOfElectives;
  }
}