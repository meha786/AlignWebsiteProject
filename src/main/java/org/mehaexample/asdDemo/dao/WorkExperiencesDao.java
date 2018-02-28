package org.mehaexample.asdDemo.dao;

import org.mehaexample.asdDemo.model.WorkExperiences;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class WorkExperiencesDao {
  private static SessionFactory factory;
  private static Session session;

  private StudentsDao studentsDao;
  private CompaniesDao companiesDao;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public WorkExperiencesDao() {
    studentsDao = new StudentsDao();
    companiesDao = new CompaniesDao();
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
   * @return Work Experience if found, null if not found.
   */
  public WorkExperiences getWorkExperienceById(int workExperienceId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            "FROM WorkExperiences WHERE workExperienceId = :workExperienceId");
    query.setParameter("workExperienceId", workExperienceId);
    List listOfWorkExperience = query.list();
    if (listOfWorkExperience.isEmpty()) {
      return null;
    }
    WorkExperiences workExperiences = (WorkExperiences) listOfWorkExperience.get(0);
    populateForeignKey(workExperiences);
    session.close();
    return workExperiences;
  }

  
  /**
   * Find work experience records of a student in private DB.
   *
   * @param neuId the neu Id of a student; not null.
   * @return List of Work Experiences if neu Id found, null if Neu Id not found.
   */
  public List<WorkExperiences> getWorkExperiencesByNeuId(String neuId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            "FROM WorkExperiences WHERE student.neuId = :neuId");
    query.setParameter("neuId", neuId);
    List<WorkExperiences> listOfWorkExperience = query.list();
    if (listOfWorkExperience.isEmpty()) {
      return null;
    }
    for (WorkExperiences workExperience : listOfWorkExperience) {
      populateForeignKey(workExperience);
    }
    session.close();
    return listOfWorkExperience;
  }

  /**
   * Create a work experience in the private database.
   * This function requires the Students object and the Companies
   * object inside the work experience object to be not null.
   *
   * @param workExperience the work experience object to be created; not null.
   */
  public void createWorkExperience(WorkExperiences workExperience) {
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
    } finally {
      session.close();
    }
  }

  /**
   * Delete a work experience in the private database.
   *
   * @param workExperienceId the work experience Id to be deleted.
   * @return true if work experience is deleted, false otherwise.
   */
  public boolean deleteWorkExperienceById(int workExperienceId) {
    WorkExperiences workExperiences = getWorkExperienceById(workExperienceId);
    if (workExperiences != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("Deleting work experience with id = " + workExperiences.getWorkExperienceId());
        session.delete(workExperiences);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        return false;
      } finally {
        session.close();
      }
      return true;
    }
    return false;
  }

  /**
   * Update a work experience in the private DB.
   *
   * @param workExperiences work experience object; not null.
   * @return true if the work experience is updated, false otherwise.
   */
  public boolean updateWorkExperience(WorkExperiences workExperiences) {
    if (getWorkExperienceById(workExperiences.getWorkExperienceId()) != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("updating work experience in Work Experiences table...");
        session.saveOrUpdate(workExperiences);
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
    return false;
  }

  private void populateForeignKey(WorkExperiences workExperiences) {
    workExperiences.setStudent(studentsDao.getStudentRecord(workExperiences.getStudent().getNeuId()));
    workExperiences.setCompany(companiesDao.getCompanyById(workExperiences.getCompany().getCompanyId()));
  }
}
