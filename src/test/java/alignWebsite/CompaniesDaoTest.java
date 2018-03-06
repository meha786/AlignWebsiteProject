package alignWebsite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.CompaniesDao;
import org.mehaexample.asdDemo.model.Companies;

import java.util.List;

import static org.junit.Assert.*;

public class CompaniesDaoTest {
  private static CompaniesDao companiesDao;

  @BeforeClass
  public static void init() {
    companiesDao = new CompaniesDao();
  }

  @Test
  public void getAllCompaniesTest() {
    List<Companies> listOfCompanies = companiesDao.getAllCompanies();
    for (Companies company: listOfCompanies) {
      if (company.getCompanyName().equals("Amazon")) {
        assertTrue(company.getCompanyName().equals("Amazon"));
      } else if (company.getCompanyName().equals("Apple")) {
        assertTrue(company.getCompanyName().equals("Apple"));
      } else if (company.getCompanyName().equals("Facebook")) {
        assertTrue(company.getCompanyName().equals("Facebook"));
      } else if (company.getCompanyName().equals("Google")) {
        assertTrue(company.getCompanyName().equals("Google"));
      } else if (company.getCompanyName().equals("Microsoft")) {
        assertTrue(company.getCompanyName().equals("Microsoft"));
      } else {
        fail();
      }
    }
  }

  @Test
  public void getCompanyByCompanyIdTest() {
    assertTrue(companiesDao.getCompanyById(1).getCompanyName().equals("Amazon"));
    assertTrue(companiesDao.getCompanyById(3).getCompanyName().equals("Microsoft"));
    assertTrue(companiesDao.getCompanyById(5).getCompanyName().equals("Facebook"));
    assertTrue(companiesDao.getCompanyById(100) == null);
  }

  @Test
  public void getCompanyByCompanyNameTest() {
    assertTrue(companiesDao.getCompanyByName("Apple").getCompanyName().equals("Apple"));
    assertTrue(companiesDao.getCompanyByName("Google").getCompanyName().equals("Google"));
    assertTrue(companiesDao.getCompanyByName("NonExistentCompany") == null);
  }

  @Test
  public void createAndDeleteCompanyTest() {
    Companies company = new Companies();
    company.setCompanyName("Intel");
    companiesDao.createCompany(company);
    company = companiesDao.getCompanyByName("Intel");
    System.out.println(company.getCompanyName());
    assertTrue(company.getCompanyName().equals("Intel"));

    assertTrue(companiesDao.deleteCompanyById(company.getCompanyId()));
    assertFalse(companiesDao.deleteCompanyById(-20));
  }

  @Test
  public void updateCompanyTest() {
    Companies company = companiesDao.getCompanyByName("Facebook");
    company.setCompanyName("Facemash");
    companiesDao.updateCompany(company);
    assertTrue(companiesDao.getCompanyByName("Facemash").getCompanyName().equals("Facemash"));

    company.setCompanyName("Facebook");
    companiesDao.updateCompany(company);
    assertTrue(companiesDao.getCompanyByName("Facebook").getCompanyName().equals("Facebook"));
    assertTrue(companiesDao.getCompanyByName("Facemash") == null);

    company.setCompanyId(-100);
    assertFalse(companiesDao.updateCompany(company));
    assertTrue(companiesDao.getCompanyById(-100) == null);
  }
}