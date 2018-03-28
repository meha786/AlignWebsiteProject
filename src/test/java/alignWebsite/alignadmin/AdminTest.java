//package alignWebsite.alignadmin;
//
//import java.util.ArrayList;
//
//import javax.ws.rs.core.Response;
//
//import org.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.mehaexample.asdDemo.model.alignadmin.GenderRatio;
//import org.mehaexample.asdDemo.model.alignadmin.ParamsObject;
//import org.mehaexample.asdDemo.model.alignadmin.TopBachelor;
//import org.mehaexample.asdDemo.model.alignadmin.TopElective;
//import org.mehaexample.asdDemo.model.alignadmin.TopEmployer;
//import org.mehaexample.asdDemo.model.alignprivate.StudentBasicInfo;
//import org.mehaexample.asdDemo.model.alignprivate.StudentCoopList;
//import org.mehaexample.asdDemo.model.alignprivate.Students;
//import org.mehaexample.asdDemo.alignWebsite.Admin;
//import org.mehaexample.asdDemo.enums.Campus;
//import org.mehaexample.asdDemo.enums.DegreeCandidacy;
//import org.mehaexample.asdDemo.enums.EnrollmentStatus;
//import org.mehaexample.asdDemo.enums.Gender;
//import org.mehaexample.asdDemo.enums.Term;
//
//public class AdminTest {
//
//	private static ParamsObject input;
//	private static Admin admin;
//	private static Students st;
//
//	@BeforeClass
//	public static void init() {
//	}
//
//	@Before
//	public void setup() {
//
//		st = new Students();
//		st.setAddress("225 Terry Ave");
//		st.setCampus(Campus.valueOf("SEATTLE"));
//		st.setDegree(DegreeCandidacy.valueOf("MASTERS"));
//		st.setEmail("tonyhawk@gmail.com");
//		st.setEnrollmentStatus(EnrollmentStatus.valueOf("FULL_TIME"));
//		st.setEntryTerm(Term.valueOf("SPRING"));
//		st.setEntryYear(2015);
//		st.setExpectedLastTerm(Term.valueOf("FALL"));
//		st.setExpectedLastYear(2017);
//		st.setFacebook("www.facebook.com");
//		st.setFirstName("Tony");
//		st.setGender(Gender.valueOf("F"));
//		st.setGithub("www.github.com");
//		st.setLastName("Hawk");
//		st.setLinkedin("www.linkedin.com");
//		st.setNeuId("9878987");
//		st.setPhoneNum("2061112222");
//		st.setRace("White");
//		st.setScholarship(false);
//		st.setSkills("Microsoft office, java. c");
//		st.setState("WA");
//		st.setSummary("I am genius");
//		st.setVisa("F1");
//		st.setVisible(true);
//		st.setWebsite("www.example.com");
//		st.setZip("98109");
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void searchTest(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setEmail("tonyhawk@gmail.com");
//		try {
//			admin = new Admin();
//		Response resp = admin.searchStudent(input);
//
//		ArrayList<Students> studentRecords = new ArrayList<Students>();
//		studentRecords =  (ArrayList<Students>) resp.getEntity();
//
//		Students responseRecord = studentRecords.get(0);
//		Assert.assertEquals(st.getAddress(), responseRecord.getAddress());
//		Assert.assertEquals(st.getCampus(), responseRecord.getCampus());
//		Assert.assertEquals(st.getDegree(), responseRecord.getDegree());
//		Assert.assertEquals(st.getEmail(), responseRecord.getEmail());
//		Assert.assertEquals(st.getEnrollmentStatus(), responseRecord.getEnrollmentStatus());
//		Assert.assertEquals(st.getEntryTerm(), responseRecord.getEntryTerm());
//		Assert.assertEquals(st.getEntryYear(), responseRecord.getEntryYear());
//		Assert.assertEquals(st.getExpectedLastTerm(), responseRecord.getExpectedLastTerm());
//		Assert.assertEquals(st.getExpectedLastYear(), responseRecord.getExpectedLastYear());
//		Assert.assertEquals(st.getFacebook(), responseRecord.getFacebook());
//		Assert.assertEquals(st.getFirstName(), responseRecord.getFirstName());
//		Assert.assertEquals(st.getGender(), responseRecord.getGender());
//		Assert.assertEquals(st.getGithub(), responseRecord.getGithub());
//		Assert.assertEquals(st.getLastName(), responseRecord.getLastName());
//		Assert.assertEquals(st.getLinkedin(), responseRecord.getLinkedin());
//		Assert.assertEquals(st.getNeuId(), responseRecord.getNeuId());
//		Assert.assertEquals(st.getPhoneNum(), responseRecord.getPhoneNum());
//		Assert.assertEquals(st.getRace(), responseRecord.getRace());
//		Assert.assertEquals(st.isScholarship(), responseRecord.isScholarship());
//		Assert.assertEquals(st.getSkills(), responseRecord.getSkills());
//		Assert.assertEquals(st.getState(), responseRecord.getState());
//		Assert.assertEquals(st.getSummary(), responseRecord.getSummary());
//		Assert.assertEquals(st.getVisa(), responseRecord.getVisa());
//		Assert.assertEquals(st.isVisible(), responseRecord.isVisible());
//		Assert.assertEquals(st.getWebsite(), responseRecord.getWebsite());
//		Assert.assertEquals(st.getZip(), responseRecord.getZip());
//
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void searchTest2(){
//		input = new ParamsObject();
//		input.setCampus("SEAT");
//		input.setEmail("tonyhawk@gmail.com");
//		try {
//			admin = new Admin();
//		Response resp = admin.searchStudent(input);
//
//		String error =  (String) resp.getEntity();
//		Assert.assertEquals("please check the request.", error);
//
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getStudentProfileTest(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setEmail("tonyhawk@gmail.com");
//		try {
//			admin = new Admin();
//		Response resp = admin.getStudentProfile("9878987");
//		JSONObject jsonObj = new JSONObject(resp.getEntity().toString());
//
//		Assert.assertEquals(st.getAddress(), jsonObj.get("address"));
//		Assert.assertEquals(st.getEmail(), jsonObj.get("email"));
//
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getStudentProfileTest2(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setEmail("tonyhawk@gmail.com");
//		try {
//			admin = new Admin();
//		Response resp = admin.getStudentProfile("98789873");
//		String error =  (String) resp.getEntity();
//
//		Assert.assertEquals("No Student record exists with given ID", error);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void genderRatioTest(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2017");
//		try {
//			admin = new Admin();
//		Response resp = admin.getGenderRatio(input);
//
//		ArrayList<GenderRatio> ratio = new ArrayList<GenderRatio>();
//		ratio =  (ArrayList<GenderRatio>) resp.getEntity();
//
//		GenderRatio responseRecord = ratio.get(0);
//		Assert.assertEquals("2015", responseRecord.getEntryYear());
//		Assert.assertEquals("1", responseRecord.getFemale());
//		Assert.assertEquals("1", responseRecord.getMale());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void genderRatioTest2(){
//		input = new ParamsObject();
//		input.setCampus("Boston");
//		input.setYear("2017");
//		try {
//			admin = new Admin();
//		Response resp = admin.getGenderRatio(input);
//
//		ArrayList<GenderRatio> ratio = new ArrayList<GenderRatio>();
//		ratio =  (ArrayList<GenderRatio>) resp.getEntity();
//
//		Assert.assertEquals(0, ratio.size());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void genderRatioTest3(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2017");
//		try {
//			admin = new Admin();
//		Response resp = admin.getGenderRatio(input);
//		String error = (String) resp.getEntity();
//		Assert.assertEquals("campus doesn't exist", error);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopBachelorDegreeTest(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2017");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopBachelorDegree(input);
//
//		ArrayList<TopBachelor> degrees = new ArrayList<TopBachelor>();
//		degrees =  (ArrayList<TopBachelor>) resp.getEntity();
//
//		TopBachelor responseRecord = degrees.get(0);
//		Assert.assertEquals("Baking", responseRecord.getDegree());
//		Assert.assertEquals("1", responseRecord.getTotalStudents());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopBachelorDegreeTest2(){
//		input = new ParamsObject();
//		input.setCampus("BOSTON");
//		input.setYear("2017");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopBachelorDegree(input);
//
//		ArrayList<TopBachelor> degrees = new ArrayList<TopBachelor>();
//		degrees =  (ArrayList<TopBachelor>) resp.getEntity();
//
//		Assert.assertEquals(0, degrees.size());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopBachelorDegreeTest3(){
//		input = new ParamsObject();
//		input.setCampus("BOSTON");
//		input.setYear("2017");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopBachelorDegree(input);
//		String error = (String) resp.getEntity();
//		Assert.assertEquals("campus doesn't exist", error);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopEmployersTest(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2017");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopEmployers(input);
//
//		ArrayList<TopEmployer> emp = new ArrayList<TopEmployer>();
//		emp =  (ArrayList<TopEmployer>) resp.getEntity();
//
//		TopEmployer responseRecord = emp.get(0);
//		Assert.assertEquals("Amazon", responseRecord.getCompanyName());
//		Assert.assertEquals("1", responseRecord.getTotalStudents());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopEmployersTest2(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2016");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopEmployers(input);
//
//		ArrayList<TopEmployer> emp = new ArrayList<TopEmployer>();
//		emp =  (ArrayList<TopEmployer>) resp.getEntity();
//		Assert.assertEquals(0, emp.size());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopEmployersTest3(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2016");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopEmployers(input);
//		String error = (String) resp.getEntity();
//		Assert.assertEquals("campus doesn't exist", error);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopElectivesTest(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2017");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopElectives(input);
//
//		ArrayList<TopElective> emp = new ArrayList<TopElective>();
//		emp =  (ArrayList<TopElective>) resp.getEntity();
//
//		TopElective responseRecord = emp.get(0);
//		Assert.assertEquals("Advanced Software Development", responseRecord.getCourseName());
//		Assert.assertEquals("1", responseRecord.getTotalStudents());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopElectivesTest2(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2015");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopElectives(input);
//
//		ArrayList<TopElective> emp = new ArrayList<TopElective>();
//		emp =  (ArrayList<TopElective>) resp.getEntity();
//		Assert.assertEquals(0, emp.size());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getTopElectivesTest3(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2015");
//		try {
//			admin = new Admin();
//		Response resp = admin.getTopElectives(input);
//		String error = (String) resp.getEntity();
//		Assert.assertEquals("campus doesn't exist", error);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getCoopStudentsTest(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		try {
//			admin = new Admin();
//		Response resp = admin.getCoopStudents(input);
//
//		ArrayList<StudentCoopList> emp = new ArrayList<StudentCoopList>();
//		emp =  (ArrayList<StudentCoopList>) resp.getEntity();
//
//		StudentCoopList responseRecord = emp.get(0);
//		Assert.assertEquals("Tony", responseRecord.getFirstName());
//		Assert.assertEquals("Hawk", responseRecord.getLastName());
//		Assert.assertEquals("9878987", responseRecord.getNeuId());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getCoopStudentsTest2(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setYear("2015");
//		try {
//			admin = new Admin();
//		Response resp = admin.getCoopStudents(input);
//
//		ArrayList<StudentCoopList> emp = new ArrayList<StudentCoopList>();
//		emp =  (ArrayList<StudentCoopList>) resp.getEntity();
//		Assert.assertEquals(0, emp.size());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getCoopStudentsTest3(){
//		input = new ParamsObject();
//		input.setCampus("SEATTE");
//		input.setYear("2015");
//		try {
//			admin = new Admin();
//		Response resp = admin.getCoopStudents(input);
//		String error = (String) resp.getEntity();
//		Assert.assertEquals("campus doesn't exist", error);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getStudentsWorkingTest(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setCompany("amazon");
//		try {
//			admin = new Admin();
//		Response resp = admin.getStudentsWorkingForACompany(input);
//
//		ArrayList<StudentBasicInfo> emp = new ArrayList<StudentBasicInfo>();
//		emp =  (ArrayList<StudentBasicInfo>) resp.getEntity();
//
//		StudentBasicInfo responseRecord = emp.get(0);
//		Assert.assertEquals("Tony", responseRecord.getFirstName());
//		Assert.assertEquals("Hawk", responseRecord.getLastName());
//		Assert.assertEquals("9878987", responseRecord.getNeuId());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getStudentsWorkingTest2(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setCompany("amazon");
//		try {
//			admin = new Admin();
//		Response resp = admin.getStudentsWorkingForACompany(input);
//
//		ArrayList<StudentCoopList> emp = new ArrayList<StudentCoopList>();
//		emp =  (ArrayList<StudentCoopList>) resp.getEntity();
//		Assert.assertEquals(0, emp.size());
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//	@SuppressWarnings("unchecked")
//	@Test
//	public void getStudentsWorkingTest3(){
//		input = new ParamsObject();
//		input.setCampus("SEATTLE");
//		input.setCompany("amazon");
//		try {
//			admin = new Admin();
//		Response resp = admin.getStudentsWorkingForACompany(input);
//		String error = (String) resp.getEntity();
//		Assert.assertEquals("campus doesn't exist", error);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//
//}
//
//
//
