package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.TopCoops;
import org.mehaexample.asdDemo.model.alignpublic.TopGradYears;

import javax.persistence.TypedQuery;
import java.util.*;

public class StudentsPublicDao {
  private SessionFactory factory;
  private Session session;

  public StudentsPublicDao() {
    try {
      // it will check the hibernate.cfg.xml file and load it
      // next it goes to all table files in the hibernate file and loads them
      factory = new Configuration().configure("/hibernate_Public.cfg.xml").buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public StudentsPublic createStudent(StudentsPublic student) {
    Transaction tx = null;
    if (findStudentByPublicId(student.getPublicId()) != null) {
      throw new HibernateException("Student already exist in public database.");
    } else {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.save(student);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException("Connection error.");
      } finally {
        session.close();
      }
    }
    return student;
  }

  public StudentsPublic findStudentByPublicId(int publicId) {
    List<StudentsPublic> list;
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM StudentsPublic WHERE publicId = :publicId ");
      query.setParameter("publicId", publicId);
      list = query.list();
    } catch (HibernateException e) {
      throw new HibernateException("Connection error.");
    } finally {
      session.close();
    }
    if (list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

  public List<TopGradYears> getTopGraduationYears(int numberOfResultsDesired) {
    String hql = "SELECT NEW org.mehaexample.asdDemo.model.alignpublic.TopGradYears(s.graduationYear, Count(*)) " +
            "FROM StudentsPublic s " +
            "GROUP BY s.graduationYear " +
            "ORDER BY Count(*) DESC ";
    List<TopGradYears> listOfTopGradYears;
    try {
      session = factory.openSession();
      TypedQuery<TopGradYears> query = session.createQuery(hql, TopGradYears.class);
      query.setMaxResults(numberOfResultsDesired);
      listOfTopGradYears = query.getResultList();
    } catch (HibernateException e) {
      throw new HibernateException("Connection error.");
    } finally{
      session.close();
    }
    return listOfTopGradYears;
  }

  public List<Integer> getListOfAllGraduationYears() {
    String hql = "SELECT s.graduationYear " +
            "FROM StudentsPublic s " +
            "GROUP BY s.graduationYear " +
            "ORDER BY s.graduationYear ASC";
    List<Integer> listOfAllGraduationYears;
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(hql);
      listOfAllGraduationYears = query.getResultList();
    } catch (HibernateException e) {
      throw new HibernateException("Connection error.");
    } finally{
      session.close();
    }
    return listOfAllGraduationYears;
  }

  public List<StudentsPublic> getListOfAllStudents() {
    String hql = "SELECT s " +
            "FROM StudentsPublic s " +
            "ORDER BY s.graduationYear DESC";
    List<StudentsPublic> listOfAllStudents;
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(hql);
      listOfAllStudents = query.getResultList();
    } catch (HibernateException e) {
      throw new HibernateException("Connection error.");
    } finally{
      session.close();
    }
    return listOfAllStudents;
  }

  public List<StudentsPublic> getPublicFilteredStudents(Map<String, List<String>> filters) {
    StringBuilder hql = new StringBuilder("SELECT Distinct(s) " +
            "FROM StudentsPublic s " +
            "LEFT OUTER JOIN WorkExperiencesPublic we ON s.publicId = we.publicId " +
            "LEFT OUTER JOIN UndergraduatesPublic u ON s.publicId = u.publicId ");
    List<StudentsPublic> list;
    Set<String> filterKeys = filters.keySet();
    if (!filters.isEmpty()) {
      hql.append(" WHERE ");
    }
    boolean firstWhereArgument = true;
    for (String filter : filterKeys) {
      if (!firstWhereArgument) {
        hql.append("AND ");
      }
      hql.append("(");
      boolean first = true;
      List<String> filterElements = filters.get(filter);
      for (int i = 0; i < filterElements.size(); i++) {
        if (!first) {
          hql.append(" OR ");
        }
        if (first) {
          first = false;
        }
        if (filter.equals("coop")) {
          hql.append("we.").append(filter).append(" = :").append(filter).append(i);
        } else if (filter.equals("graduationYear")) {
          hql.append("s.").append(filter).append(" = :").append(filter).append(i);
        } else {
          hql.append("u.").append(filter).append(" = :").append(filter).append(i);
        }
      }
      hql.append(") ");
      if (firstWhereArgument) {
        firstWhereArgument = false;
      }
    }

    hql.append(" ORDER BY s.graduationYear DESC ");
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(hql.toString());

      for (String filter : filterKeys) {
        List<String> filterElements = filters.get(filter);
        for (int i = 0; i < filterElements.size(); i++) {
          if (filter.equals("graduationYear")) {
            query.setParameter(filter + i, Integer.parseInt(filterElements.get(i)));
          } else {
            query.setParameter(filter + i, filterElements.get(i));
          }
        }
      }
      list = query.list();
    } catch(HibernateException e) {
      throw new HibernateException("Connection error.");
    } finally {
      session.close();
    }
    return list;
  }

  public boolean deleteStudentByPublicId(int publicId) {
    StudentsPublic student = findStudentByPublicId(publicId);
    if (student != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(student);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException("Connection error.");
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Public Id does not exist.");
    }

    return true;
  }
}
