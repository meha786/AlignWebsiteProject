package org.mehaexample.asdDemo.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.PriorEducations;

import java.util.List;

public class PriorEducationsDao {
  private static SessionFactory factory;
  private static Session session;

  private StudentsDao studentsDao;
  private MajorsDao majorsDao;
  private InstitutionsDao institutionsDao;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public PriorEducationsDao() {
    studentsDao = new StudentsDao();
    majorsDao = new MajorsDao();
    institutionsDao = new InstitutionsDao();
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * Find a Prior Education by the Work Experience Id.
   * This method searches the work experience from the private database.
   *
   * @param priorEducationId prior education Id in private database.
   * @return Prior Education if found, null if not found.
   */
  public PriorEducations getPriorEducationById(int priorEducationId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            "FROM PriorEducations WHERE priorEducationId = :priorEducationId");
    query.setParameter("priorEducationId", priorEducationId);
    List listOfPriorEducation = query.list();
    if (listOfPriorEducation.isEmpty()) {
      return null;
    }
    PriorEducations priorEducations = (PriorEducations) listOfPriorEducation.get(0);
    populateForeignKey(priorEducations);
    session.close();
    return priorEducations;
  }

  /**
   * Find prior education records of a student in private DB.
   *
   * @param neuId the neu Id of a student; not null.
   * @return List of Prior Educations if neu Id found, null if Neu Id not found.
   */
  public List<PriorEducations> getPriorEducationsByNeuId(String neuId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            "FROM PriorEducations WHERE student.neuId = :neuId");
    query.setParameter("neuId", neuId);
    List<PriorEducations> listOfPriorEducation = query.list();
    if (listOfPriorEducation.isEmpty()) {
      return null;
    }
    for (PriorEducations priorEducation : listOfPriorEducation) {
      populateForeignKey(priorEducation);
    }
    session.close();
    return listOfPriorEducation;
  }

  /**
   * Create a prior education in the private database.
   * This function requires the Student, institution, major
   * object inside the prior education object to be not null.
   *
   * @param priorEducation the prior education object to be created; not null.
   */
  public void createPriorEducation(PriorEducations priorEducation) {
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      System.out.println("saving prior education"
              + " in Prior Educations table");
      session.save(priorEducation);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("HibernateException: " + e);
      if (tx != null) tx.rollback();
    } finally {
      session.close();
    }
  }

  /**
   * Delete a prior education in the private database.
   *
   * @param priorEducationId the prior education Id to be deleted.
   * @return true if prior education is deleted, false otherwise.
   */
  public boolean deletePriorEducationById(int priorEducationId) {
    PriorEducations priorEducation = getPriorEducationById(priorEducationId);
    if (priorEducation != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("Deleting prior education with id = " + priorEducation.getPriorEducationId());
        session.delete(priorEducation);
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
   * Update a prior education in the private DB.
   *
   * @param priorEducation prior education object; not null.
   * @return true if the prior education is updated, false otherwise.
   */
  public boolean updatePriorEducation(PriorEducations priorEducation) {
    if (getPriorEducationById(priorEducation.getPriorEducationId()) != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("updating prior education in Prior Educations table...");
        session.saveOrUpdate(priorEducation);
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

  private void populateForeignKey(PriorEducations priorEducations) {
    priorEducations.setStudent(studentsDao.getStudentRecord(priorEducations.getStudent().getNeuId()));
    priorEducations.setInstitution(institutionsDao.getInstitutionById(priorEducations.getInstitution().getInstitutionId()));
    priorEducations.setMajor(majorsDao.getMajorById(priorEducations.getMajor().getMajorId()));
  }
}
