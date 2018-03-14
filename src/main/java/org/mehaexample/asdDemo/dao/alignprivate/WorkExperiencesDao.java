package org.mehaexample.asdDemo.dao.alignprivate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.model.alignprivate.WorkExperiences;

import java.util.List;

public class WorkExperiencesDao {
  private static SessionFactory factory;
  private static Session session;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public WorkExperiencesDao() {
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * Find a Work Experience by the Work Experience Id.
   * This method searches the work experience from the private database.
   *
   * @param workExperienceId work experience Id in private database.
   * @return Work Experience if found.
   */
  public WorkExperiences getWorkExperienceById(int workExperienceId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            "FROM WorkExperiences WHERE workExperienceId = :workExperienceId");
    query.setParameter("workExperienceId", workExperienceId);
    List<WorkExperiences> listOfWorkExperience = query.list();
    if (listOfWorkExperience.size() == 0)
      return null;
    WorkExperiences workExperiences = listOfWorkExperience.get(0);
    session.close();
    return workExperiences;
  }

  /**
   * Find work experience records of a student in private DB.
   *
   * @param neuId the neu Id of a student; not null.
   * @return List of Work Experiences.
   */
  public List<WorkExperiences> getWorkExperiencesByNeuId(String neuId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            "FROM WorkExperiences WHERE neuId = :neuId");
    query.setParameter("neuId", neuId);
    List<WorkExperiences> listOfWorkExperience = query.list();
    session.close();
    return listOfWorkExperience;
  }

  /**
   * Create a work experience in the private database.
   * This function requires the StudentsPublic object and the Companies
   * object inside the work experience object to be not null.
   *
   * @param workExperience the work experience object to be created; not null.
   * @return newly created WorkExperience if success. Otherwise, return null;
   */
  public WorkExperiences createWorkExperience(WorkExperiences workExperience) {
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      System.out.println("saving work experience"
              + " in Work Experiences table");
      session.save(workExperience);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("HibernateException: " + e);
      if (tx != null) tx.rollback();
      workExperience = null;
    } finally {
      session.close();
    }

    return workExperience;
  }

  /**
   * Delete a work experience in the private database.
   *
   * @param workExperienceId the work experience Id to be deleted.
   * @return true if work experience is deleted, false otherwise.
   */
  public boolean deleteWorkExperienceById(int workExperienceId) {
    boolean deleted = false;
    WorkExperiences workExperiences = getWorkExperienceById(workExperienceId);
    if (workExperiences != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("Deleting work experience with id = " + workExperiences.getWorkExperienceId());
        session.delete(workExperiences);
        tx.commit();
        deleted = true;
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
      } finally {
        session.close();
      }
    }

    return deleted;
  }

  public boolean deleteWorkExperienceByNeuId(String neuId) {
    Transaction tx = null;
    boolean deleted = false;

    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("DELETE FROM WorkExperiences " +
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

  /**
   * Update a work experience in the private DB.
   *
   * @param workExperience work experience object; not null.
   * @return true if the work experience is updated, false otherwise.
   */
  public boolean updateWorkExperience(WorkExperiences workExperience) {
    boolean updated = false;

    if (getWorkExperienceById(workExperience.getWorkExperienceId()) != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("updating work experience in Work Experiences table...");
        session.saveOrUpdate(workExperience);
        tx.commit();
        updated = true;
      } catch (HibernateException e) {
        System.out.println("HibernateException: " + e);
        if (tx != null) tx.rollback();
      } finally {
        session.close();
      }
    }

    return updated;
  }

  public List<String> getTopTenEmployers(Campus campus, Integer year) {
    StringBuilder hql = new StringBuilder("SELECT we.companyName AS CompanyName " +
            "FROM Students s INNER JOIN WorkExperiences we " +
            "ON s.neuId = we.neuId ");
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
      hql.append("year(we.startDate) = :year ");
    }
    hql.append("GROUP BY s.neuId ");
    hql.append("ORDER BY Count(DISTINCT s.neuId) DESC ");
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
    List<String> listOfWorkExperience = query.list();
    session.close();
    return listOfWorkExperience;
  }

  public int getTotalStudentsWithWorkExp(Campus campus, Integer year) {
    StringBuilder hql = new StringBuilder("SELECT Count(DISTINCT s.neuId) AS Total " +
            "FROM Students s INNER JOIN WorkExperiences we " +
            "ON s.neuId = we.neuId ");
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
      hql.append("year(we.startDate) = :year ");
    }
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            hql.toString());
    if (campus != null) {
      query.setParameter("campus", campus);
    }
    if (year != null) {
      query.setParameter("year", year);
    }
    List<Long> listOfWorkExperience = query.list();
    session.close();
    return listOfWorkExperience.get(0).intValue();
  }

  public int getTotalStudentsWorkingInACompany(Campus campus, Integer year, String companyName) {
    StringBuilder hql = new StringBuilder("SELECT Count(DISTINCT s.neuId) AS Total " +
            "FROM Students s INNER JOIN WorkExperiences we " +
            "ON s.neuId = we.neuId " +
            "WHERE we.companyName = :companyName ");
    if (campus != null) {
      hql.append("AND s.campus = :campus ");
    }
    if (year != null) {
      hql.append("AND year(we.startDate) = :year ");
    }
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            hql.toString());
    query.setParameter("companyName", companyName);
    if (campus != null) {
      query.setParameter("campus", campus);
    }
    if (year != null) {
      query.setParameter("year", year);
    }
    List<Long> listOfWorkExperience = query.list();
    session.close();
    return listOfWorkExperience.get(0).intValue();
  }
}