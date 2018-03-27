package alignWebsite.alignadmin;

import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignadmin.AdminLoginsDao;
import org.mehaexample.asdDemo.dao.alignadmin.AdministratorsDao;
import org.mehaexample.asdDemo.model.alignadmin.AdminLogins;
import org.mehaexample.asdDemo.model.alignadmin.Administrators;

import java.sql.Timestamp;

public class AdminLoginsDaoTest {
  private static AdminLoginsDao adminLoginsDao;
  private static AdministratorsDao administratorsDao;

  @BeforeClass
  public static void init() {
    adminLoginsDao = new AdminLoginsDao(true);
    administratorsDao = new AdministratorsDao(true);

//    adminLoginsDao = new AdminLoginsDao();
//    administratorsDao = new AdministratorsDao();

    Administrators newAdministrator = new Administrators("00000000", "catwoman@gmail.com",
            "Cat", "Main", "Woman");
    Administrators newAdministrator2 = new Administrators("11111111", "catwoman2@gmail.com",
            "Cat", "Main", "Woman");
    Administrators newAdministrator3 = new Administrators("22222222", "catwoman3@gmail.com",
            "Cat", "Main", "Woman");

    administratorsDao.addAdministrator(newAdministrator);
    administratorsDao.addAdministrator(newAdministrator2);
    administratorsDao.addAdministrator(newAdministrator3);

    AdminLogins adminLogins = new AdminLogins("catwoman2@gmail.com",
            "password",
            "key",
            Timestamp.valueOf("2017-09-23 10:10:10.0"),
            Timestamp.valueOf("2017-09-23 10:10:10.0"),
            false);
    adminLoginsDao.createAdminLogin(adminLogins);
  }

  @AfterClass
  public static void deletePlaceholderDB() {
    adminLoginsDao.deleteAdminLogin("catwoman2@gmail.com");
    administratorsDao.deleteAdministrator("22222222");
    administratorsDao.deleteAdministrator("11111111");
    administratorsDao.deleteAdministrator("00000000");
  }

  @Test(expected = HibernateException.class)
  public void addDuplicateAdminLogins() {
    AdminLogins adminLogins = new AdminLogins("catwoman2@gmail.com",
            "password",
            "key",
            Timestamp.valueOf("2017-09-23 10:10:10.0"),
            Timestamp.valueOf("2017-09-23 10:10:10.0"),
            false);
    adminLoginsDao.createAdminLogin(adminLogins);
  }

  @Test(expected = HibernateException.class)
  public void updateNonExistentAdminLogins() {
    AdminLogins adminLogins = new AdminLogins("catwoman12@gmail.com",
            "password",
            "key",
            Timestamp.valueOf("2017-09-23 10:10:10.0"),
            Timestamp.valueOf("2017-09-23 10:10:10.0"),
            false);
    adminLoginsDao.updateAdminLogin(adminLogins);
  }

  @Test(expected = HibernateException.class)
  public void deleteNonExistentAdminLogins() {
    adminLoginsDao.deleteAdminLogin("catwoman12@gmail.com");
  }

  @Test
  public void AdminLoginIntegrationTest() {
    AdminLogins adminLogins = new AdminLogins("catwoman@gmail.com",
            "password",
            "key",
            Timestamp.valueOf("2017-09-23 10:10:10.0"),
            Timestamp.valueOf("2017-09-23 10:10:10.0"),
            false);
    adminLoginsDao.createAdminLogin(adminLogins);

    AdminLogins temp = adminLoginsDao.findAdminLoginsByEmail("catwoman@gmail.com");
    Assert.assertTrue(temp.getAdminPassword().equals("password"));
    Assert.assertTrue(temp.getRegistrationKey().equals("key"));

    temp.setAdminPassword("password2");
    adminLoginsDao.updateAdminLogin(temp);
    Assert.assertTrue(adminLoginsDao.findAdminLoginsByEmail(
            "catwoman@gmail.com").getAdminPassword().equals("password2"));

    Assert.assertTrue(adminLoginsDao.findAdminLoginsByEmail("tomcat@gmail.com") == null);

    adminLoginsDao.deleteAdminLogin("catwoman@gmail.com");
    Assert.assertTrue(adminLoginsDao.findAdminLoginsByEmail("catwoman@gmail.com") == null);
  }
}