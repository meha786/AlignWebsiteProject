package org.mehaexample.asdDemo.dao.alignprivate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.model.alignprivate.PriorEducations;

import java.util.List;

public class PriorEducationsDao {
  private SessionFactory factory;
  private Session session;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public PriorEducationsDao() {
    factory = new Configuration().configure().buildSessionFactory();
  }

  public PriorEducationsDao(boolean test) {
    if (test) {
      factory = new Configuration().configure("/hibernate_private_test.cfg.xml").buildSessionFactory();
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
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(
              "FROM PriorEducations WHERE priorEducationId = :priorEducationId");
      query.setParameter("priorEducationId", priorEducationId);
      List listOfPriorEducation = query.list();
      if (listOfPriorEducation.isEmpty()) {
        return null;
      }
      return (PriorEducations) listOfPriorEducation.get(0);
    } finally {
      session.close();
    }
  }

  /**
   * Find prior education records of a student in private DB.
   *
   * @param neuId the neu Id of a student; not null.
   * @return List of Prior Educations if neu Id found, null if Neu Id not found.
   */
  public List<PriorEducations> getPriorEducationsByNeuId(String neuId) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(
              "FROM PriorEducations WHERE neuId = :neuId");
      query.setParameter("neuId", neuId);
      return (List<PriorEducations>) query.list();
    } finally {
      session.close();
    }
  }

  /**
   * Create a prior education in the private database.
   * This function requires the Student, institution, major
   * object inside the prior education object to be not null.
   *
   * @param priorEducation the prior education object to be created; not null.
   * @return newly created priorEducation.
   */
  public PriorEducations createPriorEducation(PriorEducations priorEducation) {
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.save(priorEducation);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }

    return priorEducation;
  }

  /**
   * Delete a prior education in the private database.
   *
   * @param priorEducationId the prior education Id to be deleted.
   * @return true if prior education is deleted, false otherwise.
   */
  public boolean deletePriorEducationById(int priorEducationId) {
    PriorEducations priorEducation = getPriorEducationById(priorEducationId);
    if (priorEducation == null) {
      throw new HibernateException("Prior Education does not exist.");
    }

    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.delete(priorEducation);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }

    return true;
  }

  /**
   * Update a prior education in the private DB.
   *
   * @param priorEducation prior education object; not null.
   * @return true if the prior education is updated, false otherwise.
   */
  public boolean updatePriorEducation(PriorEducations priorEducation) {
    if (getPriorEducationById(priorEducation.getPriorEducationId()) == null) {
      throw new HibernateException("Prior Education does not exist.");
    }
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(priorEducation);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }
    return true;
  }

  public List<String> getTopTenBachelors(Campus campus) {
    StringBuilder hql = new StringBuilder("SELECT pe.majorName AS MajorName " +
            "FROM Students s INNER JOIN PriorEducations pe " +
            "ON s.neuId = pe.neuId " +
            "WHERE pe.degreeCandidacy = 'BACHELORS' ");
    if (campus != null) {
      hql.append("AND s.campus = :campus ");
    }
    hql.append("GROUP BY MajorName ");
    hql.append("ORDER BY Count(*) DESC ");
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(
              hql.toString());
      query.setMaxResults(10);
      if (campus != null) {
        query.setParameter("campus", campus);
      }
      return (List<String>) query.list();
    } finally {
      session.close();
    }
  }
}