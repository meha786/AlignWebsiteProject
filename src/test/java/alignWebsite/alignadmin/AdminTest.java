package alignWebsite.alignadmin;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.model.alignadmin.ParamsObject;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.alignWebsite.Admin;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.enums.Term;

public class AdminTest {

	private static ParamsObject input;
	private static Admin admin;
	private static Students st;
	
	@BeforeClass
	public static void init() {
	}
	
	@Before
	public void setup() {
		
		st = new Students();
		st.setAddress("225 Terry Ave");
		st.setCampus(Campus.valueOf("SEATTLE"));
		st.setDegree(DegreeCandidacy.valueOf("MASTERS"));
		st.setEmail("tonyhawk@gmail.com");
		st.setEnrollmentStatus(EnrollmentStatus.valueOf("FULL_TIME"));
		st.setEntryTerm(Term.valueOf("SPRING"));
		st.setEntryYear(2015);
		st.setExpectedLastTerm(Term.valueOf("FALL"));
		st.setExpectedLastYear(2017);
		st.setFacebook("www.facebook.com");
		st.setFirstName("Tony");
		st.setGender(Gender.valueOf("F"));
		st.setGithub("www.github.com");
		st.setLastName("Hawk");
		st.setLinkedin("www.linkedin.com");
		st.setNeuId("9878987");
		st.setPhoneNum("2061112222");
		st.setRace("White");
		st.setScholarship(false);
		st.setSkills("Microsoft office, java. c");
		st.setState("WA");
		st.setSummary("I am genius");
		st.setVisa("F1");
		st.setVisible(true);
		st.setWebsite("www.example.com");
		st.setZip("98109");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void searchTest(){
		input = new ParamsObject();
		input.setCampus("SEATTLE");
		input.setEmail("tonyhawk@gmail.com");
		try {
			admin = new Admin();
		Response resp = admin.searchStudent(input);
		
		ArrayList<Students> studentRecords = new ArrayList<Students>();
		studentRecords =  (ArrayList<Students>) resp.getEntity();
		
		Students responseRecord = studentRecords.get(0);
		Assert.assertEquals(st.getAddress(), responseRecord.getAddress());
		Assert.assertEquals(st.getCampus(), responseRecord.getCampus());
		Assert.assertEquals(st.getDegree(), responseRecord.getDegree());
		Assert.assertEquals(st.getEmail(), responseRecord.getEmail());
		Assert.assertEquals(st.getEnrollmentStatus(), responseRecord.getEnrollmentStatus());
		Assert.assertEquals(st.getEntryTerm(), responseRecord.getEntryTerm());
		Assert.assertEquals(st.getEntryYear(), responseRecord.getEntryYear());
		Assert.assertEquals(st.getExpectedLastTerm(), responseRecord.getExpectedLastTerm());
		Assert.assertEquals(st.getExpectedLastYear(), responseRecord.getExpectedLastYear());
		Assert.assertEquals(st.getFacebook(), responseRecord.getFacebook());
		Assert.assertEquals(st.getFirstName(), responseRecord.getFirstName());
		Assert.assertEquals(st.getGender(), responseRecord.getGender());
		Assert.assertEquals(st.getGithub(), responseRecord.getGithub());
		Assert.assertEquals(st.getLastName(), responseRecord.getLastName());
		Assert.assertEquals(st.getLinkedin(), responseRecord.getLinkedin());
		Assert.assertEquals(st.getNeuId(), responseRecord.getNeuId());
		Assert.assertEquals(st.getPhoneNum(), responseRecord.getPhoneNum());
		Assert.assertEquals(st.getRace(), responseRecord.getRace());
		Assert.assertEquals(st.isScholarship(), responseRecord.isScholarship());
		Assert.assertEquals(st.getSkills(), responseRecord.getSkills());
		Assert.assertEquals(st.getState(), responseRecord.getState());
		Assert.assertEquals(st.getSummary(), responseRecord.getSummary());
		Assert.assertEquals(st.getVisa(), responseRecord.getVisa());
		Assert.assertEquals(st.isVisible(), responseRecord.isVisible());
		Assert.assertEquals(st.getWebsite(), responseRecord.getWebsite());
		Assert.assertEquals(st.getZip(), responseRecord.getZip());
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void getStudentProfileTest(){
		input = new ParamsObject();
		input.setCampus("SEATTLE");
		input.setEmail("tonyhawk@gmail.com");
		try {
			admin = new Admin();
		Response resp = admin.getStudentProfile("9878987");
		
		ArrayList<Students> studentRecords = new ArrayList<Students>();

		JSONObject jsonObj = new JSONObject(resp.getEntity());
		System.out.println(jsonObj.get("address"));
		
		Students responseRecord = studentRecords.get(0);
		Assert.assertEquals(st.getAddress(), responseRecord.getAddress());
		Assert.assertEquals(st.getCampus(), responseRecord.getCampus());
		Assert.assertEquals(st.getDegree(), responseRecord.getDegree());
		Assert.assertEquals(st.getEmail(), responseRecord.getEmail());
		Assert.assertEquals(st.getEnrollmentStatus(), responseRecord.getEnrollmentStatus());
		Assert.assertEquals(st.getEntryTerm(), responseRecord.getEntryTerm());
		Assert.assertEquals(st.getEntryYear(), responseRecord.getEntryYear());
		Assert.assertEquals(st.getExpectedLastTerm(), responseRecord.getExpectedLastTerm());
		Assert.assertEquals(st.getExpectedLastYear(), responseRecord.getExpectedLastYear());
		Assert.assertEquals(st.getFacebook(), responseRecord.getFacebook());
		Assert.assertEquals(st.getFirstName(), responseRecord.getFirstName());
		Assert.assertEquals(st.getGender(), responseRecord.getGender());
		Assert.assertEquals(st.getGithub(), responseRecord.getGithub());
		Assert.assertEquals(st.getLastName(), responseRecord.getLastName());
		Assert.assertEquals(st.getLinkedin(), responseRecord.getLinkedin());
		Assert.assertEquals(st.getNeuId(), responseRecord.getNeuId());
		Assert.assertEquals(st.getPhoneNum(), responseRecord.getPhoneNum());
		Assert.assertEquals(st.getRace(), responseRecord.getRace());
		Assert.assertEquals(st.isScholarship(), responseRecord.isScholarship());
		Assert.assertEquals(st.getSkills(), responseRecord.getSkills());
		Assert.assertEquals(st.getState(), responseRecord.getState());
		Assert.assertEquals(st.getSummary(), responseRecord.getSummary());
		Assert.assertEquals(st.getVisa(), responseRecord.getVisa());
		Assert.assertEquals(st.isVisible(), responseRecord.isVisible());
		Assert.assertEquals(st.getWebsite(), responseRecord.getWebsite());
		Assert.assertEquals(st.getZip(), responseRecord.getZip());
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}



