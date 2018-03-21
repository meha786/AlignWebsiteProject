//package org.mehaexample.asdDemo.dao.alignprivate;
//
//import java.util.List;
//
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.Configuration;
//import org.mehaexample.asdDemo.model.alignprivate.Experiences;
//
//public class ExperiencesDao {
//  private static SessionFactory factory;
//  private static Session session;
//
//  private StudentsDao studentsDao;
//
//  public ExperiencesDao() {
//    studentsDao = new StudentsDao();
//    try {
//      // it will check the hibernate.cfg.xml file and load it
//      // next it goes to all table files in the hibernate file and loads them
//      factory = new Configuration().configure().buildSessionFactory();
//    } catch (Throwable ex) {
//      System.err.println("Failed to create sessionFactory object." + ex);
//      throw new ExceptionInInitializerError(ex);
//    }
//  }
//
//  public List<Experiences> getAllExperiences() {
//    session = factory.openSession();
//    org.hibernate.query.Query query = session.createQuery("from Experiences");
//    List<Experiences> list = query.list();
//    for (Experiences experience : list) {
//      populateForeignKey(experience);
//    }
//    session.close();
//    return list;
//  }
//
//  public Experiences getExperience(int id) {
//    session = factory.openSession();
//    org.hibernate.query.Query query = session.createQuery("from Experiences where experienceId = :id");
//    query.setParameter("id", id);
//    List<Experiences> list = query.list();
//    session.close();
//    if (list.isEmpty()) {
//      return null;
//    }
//    Experiences experience = list.get(0);
//    populateForeignKey(experience);
//    return experience;
//
//  }
//
//  public Experiences updateExperience(Experiences experience) {
//    if (experience == null) {
//      return null;
//    }
//
//    Transaction tx = null;
//    StudentsDao studentDaoHibernate = new StudentsDao();
//    session = factory.openSession();
//
//    if (studentDaoHibernate.ifNuidExists(experience.getStudent().getNeuId())) {
//      try {
//        tx = session.beginTransaction();
//        session.saveOrUpdate(experience);
//        tx.commit();
//      } catch (HibernateException e) {
//        if (tx != null) tx.rollback();
//        e.printStackTrace();
//      } finally {
//        //session.close();
//      }
//    } else {
//      System.out.println("The student with a given nuid doesn't exists");
//    }
//    session = factory.openSession();
//
//    return experience;
//  }
//
//  public Experiences addExperience(Experiences experience) {
//    if (experience == null) {
//      return null;
//    }
//
//    session = factory.openSession();
//    Transaction tx = null;
//    StudentsDao studentDaoHibernate = new StudentsDao();
//
//    if (studentDaoHibernate.ifNuidExists(experience.getStudent().getNeuId())) {
//      try {
//        tx = session.beginTransaction();
//        session.save(experience);
//        tx.commit();
//      } catch (HibernateException e) {
//        if (tx != null) tx.rollback();
//        e.printStackTrace();
//      } finally {
//        //session.close();
//      }
//    } else {
//      System.out.println("The student with a given nuid doesn't exists");
//    }
//
//    session.close();
//
//    return experience;
//  }
//
//  public boolean deleteExperienceRecord(int id) {
//    session = factory.openSession();
//
//    Transaction tx = null;
//
//    try {
//      tx = session.beginTransaction();
//      Experiences experience = session.get(Experiences.class, id);
//
//      if (experience == null) {
//        return false;
//      }
//
//      System.out.println("Deleting student for id = " + id);
//      session.delete(experience);
//      tx.commit();
//    } catch (HibernateException e) {
//      System.out.println("exceptionnnnnn");
//      if (tx != null) tx.rollback();
//      e.printStackTrace();
//    } finally {
//      session.close();
//    }
//
//    return true;
//  }
//
//  private void populateForeignKey(Experiences experiences) {
//    experiences.setStudent(studentsDao.getStudentRecord(experiences.getStudent().getNeuId()));
//  }
//}