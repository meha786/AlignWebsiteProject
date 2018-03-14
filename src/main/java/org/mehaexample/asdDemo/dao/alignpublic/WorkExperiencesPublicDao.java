package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.WorkExperiencesPublic;

import java.util.List;

public class WorkExperiencesPublicDao {
  private SessionFactory factory;
  private Session session;

  public WorkExperiencesPublicDao() {
    try {
      // it will check the hibernate.cfg.xml file and load it
      // next it goes to all table files in the hibernate file and loads them
      factory = new Configuration().configure("/hibernate_Public.cfg.xml").buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public WorkExperiencesPublic createWorkExperience(WorkExperiencesPublic workExperience) {
    Transaction tx = null;
    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      session.save(workExperience);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException("Connection error.");
    } finally {
      session.close();
    }
    return workExperience;
  }

  public WorkExperiencesPublic findWorkExperienceById(int workExperienceId) {
    List<WorkExperiencesPublic> list;
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(
              "FROM WorkExperiencesPublic WHERE workExperienceId = :workExperienceId ");
      query.setParameter("workExperienceId", workExperienceId);
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

  public boolean deleteWorkExperienceById(int workExperienceId) {
    WorkExperiencesPublic workExperience = findWorkExperienceById(workExperienceId);
    if (workExperience != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(workExperience);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException("Connection error.");
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Work Experience Id does not exist.");
    }
    return true;
  }
}
