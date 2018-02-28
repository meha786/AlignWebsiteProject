package org.mehaexample.asdDemo.dao;

import org.mehaexample.asdDemo.model.Companies;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CompaniesDao {
  private static SessionFactory factory;
  private static Session session;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public CompaniesDao() {
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * Get list of all companies from the private database.
   *
   * @return list of All companies from private database.
   */
  public List<Companies> getAllCompanies() {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Companies");
    List<Companies> listOfCompanies = query.list();
    session.close();
    return listOfCompanies;
  }

  /**
   * Get a company from the private database from their
   * company Id.
   *
   * @param companyId company Id that wants to be found.
   * @return company if found, null if not found.
   */
  public Companies getCompanyById(int companyId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Companies WHERE companyId = :companyId");
    query.setParameter("companyId", companyId);
    List listOfCompanies = query.list();
    if (listOfCompanies.isEmpty()) {
      return null;
    }
    session.close();
    return (Companies) listOfCompanies.get(0);
  }

  /**
   * Get a company from the private database from their name.
   *
   * @param companyName company Name that wants to be found.
   * @return company if found, null if not found.
   */
  public Companies getCompanyByName(String companyName) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Companies WHERE companyName = :companyName");
    query.setParameter("companyName", companyName);
    List listOfCompanies = query.list();
    if (listOfCompanies.isEmpty()) {
      return null;
    }
    session.close();
    return (Companies) listOfCompanies.get(0);
  }

  /**
   * Create a new company in the private database.
   *
   * @param company the company (model) that wants to be
   *                inserted into the private database.
   */
  public void createCompany(Companies company) {
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      System.out.println("saving " + company.getCompanyName() + " in Companies table");
      session.save(company);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("HibernateException: " + e);
      if (tx != null) tx.rollback();
    } finally {
      session.close();
    }
  }

  /**
   * Delete a company from the private database based
   * on the company Id.
   *
   * @param companyId company Id that wants to be deleted.
   * @return true if company is deleted,
   * false if company is not found
   */
  public boolean deleteCompanyById(int companyId) {
    Companies company = getCompanyById(companyId);
    if (company != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("Deleting company with name = " + company.getCompanyName());
        session.delete(company);
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
   * Update a company based on the provided company model.
   *
   * @param company full company model (including the companyId).
   * @return true if company is successfully updated, false if
   * the specific company is not found.
   */
  public boolean updateCompany(Companies company) {
    if (getCompanyById(company.getCompanyId()) != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("updating company in Companies table...");
        session.saveOrUpdate(company);
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
}
