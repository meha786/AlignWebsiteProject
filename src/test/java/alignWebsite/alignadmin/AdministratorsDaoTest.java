package alignWebsite.alignadmin;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignadmin.AdministratorsDao;
import org.mehaexample.asdDemo.model.alignadmin.Administrators;

public class AdministratorsDaoTest {

	private static AdministratorsDao administratorsDao;

	@BeforeClass
	public static void init() {
		administratorsDao = new AdministratorsDao();
	}

	@Test
	public void addNullAdministratorTest() {
		Administrators administrators = administratorsDao.addAdministrator(null);
		Assert.assertNull(administrators);
	}

	@Test
	public void addAdministratorTest() {
		Administrators newAdministrator = new Administrators("123789456", "john.stewart@gmail.com", 
				"John", "Main", "Stewart");
		Administrators administrators = administratorsDao.addAdministrator(newAdministrator);
		Assert.assertTrue(administrators.toString().equals(newAdministrator.toString()));
		administratorsDao.deleteAdministrator("123789456");
	}

	@Test
	public void deleteAdminRecordTest() {
		List<Administrators> administrators = administratorsDao.getAllAdminstrators();
		int oldSize = administrators.size();		
		Administrators newAdministrator = new Administrators("123789456", "john.stewart@gmail.com", 
				"John", "Main", "Stewart");		

		administratorsDao.addAdministrator(newAdministrator);
		administratorsDao.deleteAdministrator("123789456");
		int newSize = administrators.size();	
		Assert.assertEquals(oldSize, newSize); 
	}

	@Test
	public void getAllAdministratorsTest() {
		List<Administrators> administrators = administratorsDao.getAllAdminstrators();

		System.out.println(administrators.size());
		Administrators newAdministrator = new Administrators("111", "john.lstew1art@gmail.com", 
				"John", "Main", "Stewart");	
		administratorsDao.addAdministrator(newAdministrator);
		List<Administrators> newAdministrators = administratorsDao.getAllAdminstrators();
		Assert.assertTrue(newAdministrators.size() == administrators.size() + 1);
		administratorsDao.deleteAdministrator("111");
	}

	@Test
	public void getAdminRecordTest() {
		Administrators newAdministrator = new Administrators("123789456", "john.stewart@gmail.com", 
				"John", "Main", "Stewart");	

		administratorsDao.addAdministrator(newAdministrator);
		Administrators administrator = administratorsDao.getAdministratorRecord("123789456");
		Assert.assertTrue(administrator.toString().equals(newAdministrator.toString()));
		administratorsDao.deleteAdministrator("123789456");

	}

	@Test
	public void updateAdministratorRecordTest() {
		Administrators administrator = new Administrators("123789456", "john.stewart@gmail.com", 
				"John", "Main", "Stewart");	

		administratorsDao.addAdministrator(administrator);

		administrator = administratorsDao.getAdministratorRecord("123789456");
		Assert.assertTrue(administrator.getEmail().equals("john.stewart@gmail.com"));

		Administrators newAdministrator = new Administrators("123789456", "john.stewart123@gmail.com", 
				"John", "Main", "Stewart");

		administratorsDao.updateAdministratorRecord(newAdministrator);
		Assert.assertTrue(administratorsDao.getAdministratorRecord("123789456")
				.getEmail().equals("john.stewart123@gmail.com"));

		administratorsDao.deleteAdministrator("123789456");
	}

	@Test
	public void deleteNullAdministratorTest() {
		boolean result = administratorsDao.deleteAdministrator("");
		Assert.assertFalse(result);

		result = administratorsDao.deleteAdministrator(null);
		Assert.assertFalse(result);
	}
}
