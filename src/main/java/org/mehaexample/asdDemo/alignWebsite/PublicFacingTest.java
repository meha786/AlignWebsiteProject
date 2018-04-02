package org.mehaexample.asdDemo.alignWebsite;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignpublic.UndergraduatesPublicDao;
import org.mehaexample.asdDemo.model.alignpublic.TopUndergradSchools;
import org.mehaexample.asdDemo.restModels.EmailToRegister;
import org.mehaexample.asdDemo.restModels.TopUnderGradSchools;


import junit.framework.Assert;

public class PublicFacingTest {

	private static TopUnderGradSchools topUnderGradSchools;

	private static PublicFacing publicFacing;
	private static StudentFacing studentFacing;

	UndergraduatesPublicDao undergraduatesPublicDao = new UndergraduatesPublicDao(true);

	@BeforeClass
	public static void init() {
	}

	@Before
	public void setup() {

		publicFacing = new PublicFacing();
		studentFacing = new StudentFacing();
		//		topUnderGradSchools.setNumber(10); 
	}	

	@SuppressWarnings("unchecked")
	@Test
	public void getStudentProfileTest(){
		topUnderGradSchools = new TopUnderGradSchools();
		topUnderGradSchools.setNumber(10);

		try {
			publicFacing = new PublicFacing();

			Response resp = publicFacing.getUndergradSchools(topUnderGradSchools);
			List<TopUndergradSchools> topUndergradSchools = (List<TopUndergradSchools>) resp.getEntity();

			Assert.assertTrue(topUndergradSchools.size() > 0);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void registerStudent(){
		EmailToRegister emailToRegister = new EmailToRegister("meha@gmail.com");
		Response res = studentFacing.sendRegistrationEmail(emailToRegister);
		
		String response = (String) res.getEntity();
		
		Assert.assertEquals("To Register should be an Align Student!", response); 
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void registerStudent2(){
		EmailToRegister emailToRegister = new EmailToRegister("test.alignstudent123@gmail.com");
		Response res = studentFacing.sendRegistrationEmail(emailToRegister);
		
		String response = (String) res.getEntity();
		
		Assert.assertEquals("Registration link sent succesfully to test.alignstudent123@gmail.com" , response); 
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void registerStudent3(){
		EmailToRegister emailToRegister = new EmailToRegister("test.alignstudent1231@gmail.com");
		Response res = studentFacing.sendRegistrationEmail(emailToRegister);
		
		String response = (String) res.getEntity();
		
		Assert.assertEquals("To Register should be an Align Student!" , response); 
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void registerStudent4(){
		EmailToRegister emailToRegister = new EmailToRegister("");
		Response res = studentFacing.sendRegistrationEmail(emailToRegister);
		
		String response = (String) res.getEntity();
		
		Assert.assertEquals("Email Id can't be null or empty" , response); 
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void registerStudent5(){
		EmailToRegister emailToRegister = new EmailToRegister("doe.j@husky.neu.edu");
		Response res = studentFacing.sendRegistrationEmail(emailToRegister);
		
		String response = (String) res.getEntity();
		
		Assert.assertEquals("Student is Already Registered!doe.j@husky.neu.edu" , response); 
	}
	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void passwordRs(){
//		PasswordCreateObject password = new PasswordCreateObject();
//		
//	}
	
	
	
	
}
