package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;

import java.util.List;

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
