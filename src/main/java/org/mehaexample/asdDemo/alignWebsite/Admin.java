package org.mehaexample.asdDemo.alignWebsite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignadmin.ElectivesAdminDao;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.dao.alignprivate.WorkExperiencesDao;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.model.alignprivate.Electives;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.scripts.TopFiveElectivesDao;
import org.mehaexample.asdDemo.scripts.TopFiveEmployersDao;
import org.mehaexample.asdDemo.scripts.TopTenBachelorsDegreeDao;
import org.mehaexample.asdDemo.scripts.TotalExperiencedStudentsDao;
import org.mehaexample.asdDemo.scripts.TotalFemaleStudentsDao;
import org.mehaexample.asdDemo.scripts.TotalMaleStudentsDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsInAmazonDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsInBostonDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsInCharlotteDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsInFacebookDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsInGoogleDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsInMicrosoftDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsInSeattleDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsInSiliconValleyDao;
import org.mehaexample.asdDemo.scripts.TotalStudentsWithScholarshipDao;



@Path("admin")
public class Admin{
	
	// student details methods
	StudentsDao studentDao = new StudentsDao();
	ElectivesAdminDao electiveDao = new ElectivesAdminDao();
	TopTenBachelorsDegreeDao topTenBachelorsDegreeDao = new TopTenBachelorsDegreeDao();
	TopFiveEmployersDao topFiveEmployersDao = new TopFiveEmployersDao();
	TopFiveElectivesDao topFiveElectivesDao = new TopFiveElectivesDao();
	TotalExperiencedStudentsDao totalExperiencedStudentsDao = new TotalExperiencedStudentsDao();
	TotalFemaleStudentsDao totalFemaleStudentsDao = new TotalFemaleStudentsDao();
	TotalMaleStudentsDao totalMaleStudentsDao = new TotalMaleStudentsDao();
	TotalStudentsInAmazonDao totalStudentsInAmazonDao = new TotalStudentsInAmazonDao();
	TotalStudentsInBostonDao totalStudentsInBostonDao = new TotalStudentsInBostonDao();
	TotalStudentsInCharlotteDao totalStudentsInCharlotteDao = new TotalStudentsInCharlotteDao();
	TotalStudentsInFacebookDao totalStudentsInFacebookDao = new TotalStudentsInFacebookDao();
	TotalStudentsInGoogleDao totalStudentsInGoogleDao = new TotalStudentsInGoogleDao();
	TotalStudentsInMicrosoftDao totalStudentsInMicrosoftDao = new TotalStudentsInMicrosoftDao();
	TotalStudentsInSeattleDao totalStudentsInSeattleDao = new TotalStudentsInSeattleDao();
	TotalStudentsInSiliconValleyDao totalStudentsInSiliconValleyDao = new TotalStudentsInSiliconValleyDao();
	TotalStudentsWithScholarshipDao totalStudentsWithScholarshipDao = new TotalStudentsWithScholarshipDao();
	
	
	WorkExperiencesDao workExperiencesDao = new WorkExperiencesDao();
	
	/**
     * This is the function to search a student based on his/her firstname.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/search/Tony
     * @param firstname
     * @return the list of student profiles matching the first name.
     */
	@GET
	@Path("search/{firstName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Students> searchStudent(@PathParam("firstName") String firstName){
		System.out.println("getting search results for firstName = " + firstName);
		ArrayList<Students> studentRecords = (ArrayList<Students>) studentDao.searchStudentRecord(firstName);
		return studentRecords; 
	}
	
	/**
     * This is the function to search a student based on his/her nuid.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/student/111234547
     * @param nuid
     * @return the student profile matching the nuid.
     */
	@GET
	@Path("student/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Students getStudentProfile(@PathParam("nuid") String nuid){
		System.out.println("getting student for nuid = " + nuid);
		Students studentRecord = studentDao.getStudentRecord(nuid);
		return studentRecord; 
	}
	
	/**
     * This is the function to search a student based on his/her emailid.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/studentdetails/doe.j@husky.neu.edu
     * @param email
     * @return the student profile and gpa info matching the emailid.
     */
	@GET
	@Path("studentdetails/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStudentProfileDetails(@PathParam("email") String email){
		System.out.println("getting student details for email = " + email);
		Students studentRecordDetails = studentDao.getStudentRecordByEmailId(email);
		List<Electives> electivesDetails = new ArrayList<>();
		//electiveDao.getAllElectivesbyNuid(studentRecordDetails.getNeuId());
		JSONObject jsonObj = new JSONObject(studentRecordDetails);
		jsonObj.put("terms", electivesDetails);
		return jsonObj.toString(); 
	}
	
	/**
     * This is the function to search and filter a students based on degree, programType,name, email.
     *	{
	 *		"degree":"PHD",
	 *		"enrollmentStatus":"FULL_TIME",
	 *		"name": "John",
	 *		"email": "doe.j@husky.neu.edu"
	 *	}
     *	http://localhost:8080/alignWebsite/webapi/admin/filter
     * @param email
     * @return the list of student profiles matching the filters.
     */
	@POST
	@Path("filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Students> searchSimilarStudents(String search){
		JSONObject jsonObj = new JSONObject(search);
		String degree = "PLACEHOLDER";
		DegreeCandidacy degreeCandidacyEnum = DegreeCandidacy.valueOf(degree);
		String enrollmentStatus = "PLACEHOLDER";
		EnrollmentStatus enrollmentEnum = EnrollmentStatus.valueOf(enrollmentStatus);
		String name = "PLACEHOLDER";
		String email = "PLACEHOLDER";
		
		if (!jsonObj.isNull("degree")){
			degree = jsonObj.get("degree").toString();
			degreeCandidacyEnum = DegreeCandidacy.valueOf(degree);
		}
		if (!jsonObj.isNull("enrollmentStatus")){
			enrollmentStatus = jsonObj.get("enrollmentStatus").toString();
			enrollmentEnum = EnrollmentStatus.valueOf(enrollmentStatus);
		}
		if (!jsonObj.isNull("firstName")){
			name = jsonObj.get("firstName").toString();
		}
		if (!jsonObj.isNull("email")){
			email = jsonObj.get("email").toString();
		}
		
		ArrayList<Students> studentRecords = 
				(ArrayList<Students>) studentDao.adminFiterStudents(degreeCandidacyEnum, enrollmentEnum, name ,email);
		return studentRecords;
	}
	
	
	// Data analytics methods
	
	/**
     * This is the function to get the men to women ratio.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/genderratio
     * @param 
     * @return the gender ratio is returned as string
	 * @throws SQLException 
     */
	@GET
	@Path("analytics/genderratio")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGenderRatio() throws SQLException{
		System.out.println("getting gender ratio");
		int a = totalMaleStudentsDao.getTotalMaleStudentsFromPublicDatabase();
		int b = totalFemaleStudentsDao.getTotalFemaleStudentsFromPublicDatabase();
		String ratio = ratio(a,b);
		return ratio; 
	}
	
	/**
     * This is the function to get the top 10 bachelor degrees.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/topbachelordegrees
     * @param 
     * @return the list of top 10 bachelor degrees
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/topbachelordegrees")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getTopBachelorDegree() throws SQLException{
		System.out.println("getting top bachelor degrees");
		ArrayList<String> list = (ArrayList<String>) topTenBachelorsDegreeDao.getTopTenBachelorsDegreeFromPublicDatabase();
		return list; 
	}
	
	/**
     * This is the function to get the top 10 employers.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/topemployers
     * @param 
     * @return the list of top 10 employers
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/topemployers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getTopEmployers() throws SQLException{
		System.out.println("getting top employers");
		ArrayList<String> list = (ArrayList<String>) topFiveEmployersDao.getTopFiveEmployersFromPublicDatabase();
		return list;
	}
	
	
	/**
     * This is the function to get the top 10 electives.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/topelectives
     * @param 
     * @return the list of top 10 electives
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/topelectives")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getTopElectives() throws SQLException{
		System.out.println("getting top electives");
		ArrayList<String> list =  (ArrayList<String>) topFiveElectivesDao.getTopFiveElectivesFromPublicDatabase();
		return list; 
	}
	
	
	/**
     * This is the function to get the total number of students with work experience.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/withworkexp
     * @param 
     * @return the total number of students with work experience
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/withworkexp")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsWorkExp() throws SQLException{
		System.out.println("getting total number of students with work experience");
		int total = totalExperiencedStudentsDao.getTotalExperiencedStudents();
		return total; 
	}
	
	/**
     * This is the function to get the total number of students working in facebook.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/workinginfacebook
     * @param 
     * @return the total number of students working in facebook.
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/workinginfacebook")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsWorkingInFacebook() throws SQLException{
		System.out.println("getting total number of students working in facebook");
		int total = totalStudentsInFacebookDao.getTotalStudentsInFacebook();
		return total; 
	}
	
	
	/**
     * This is the function to get the total number of students working in amazon.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/workinginamazon
     * @param 
     * @return the total number of students working in amazon.
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/workinginamazon")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsWorkingInAmazon() throws SQLException{
		System.out.println("getting total number of students working in amazon");
		int total = totalStudentsInAmazonDao.getTotalStudentsInAmazon();
		return total; 
	}
	
	
	/**
     * This is the function to get the total number of students working in google.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/workingingoogle
     * @param 
     * @return the total number of students working in google.
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/workingingoogle")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsWorkingInGoogle() throws SQLException{
		System.out.println("getting total number of students working in google");
		int total = totalStudentsInGoogleDao.getTotalStudentsInGoogle();
		return total; 
	}
	
	
	/**
     * This is the function to get the total number of students working in microsoft.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/workinginmicrosoft
     * @param 
     * @return the total number of students working in microsoft.
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/workinginmicrosoft")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsWorkingInMicrosoft() throws SQLException{
		System.out.println("getting total number of students working in microsoft");
		int total = totalStudentsInMicrosoftDao.getTotalStudentsInMicrosoft();
		return total; 
	}
	
	/**
     * This is the function to get the total number of students with scholarship.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/withscholarship
     * @param 
     * @return the total number of students with scholarship
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/withscholarship")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsWithScholarship() throws SQLException{
		System.out.println("getting total number of students with scholarship");
		int total = totalStudentsWithScholarshipDao.getTotalStudentsWithScholarshipFromPublicDatabase();
		return total; 
	}
	
	
	/**
     * This is the function to get the total number of students in align program in seattle.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/alignseattle
     * @param 
     * @return the total number of students in align program in seattle.
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/alignseattle")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsAlignSeattle() throws SQLException{
		System.out.println("getting total number of ALIGN students in seattle");
		int total = totalStudentsInSeattleDao.getTotalStudentsInSeattleFromPublicDatabase();
		return total; 
	}
	
	/**
     * This is the function to get the total number of students in align program in boston.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/alignboston
     * @param 
     * @return the total number of students in align program in boston.
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/alignboston")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsAlignBoston() throws SQLException{
		System.out.println("getting total number of ALIGN students in boston");
		int total = totalStudentsInBostonDao.getTotalStudentsInBostonFromPublicDatabase();
		return total; 
	}
	
	
	/**
     * This is the function to get the total number of students in align program in charlotte.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/aligncharlotte
     * @param 
     * @return the total number of students in align program in charlotte.
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/aligncharlotte")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsAlignCharlotte() throws SQLException{
		System.out.println("getting total number of ALIGN students in charlotte");
		int total = totalStudentsInCharlotteDao.getTotalStudentsInCharlotteFromPublicDatabase();
		return total; 
	}
	
	/**
     * This is the function to get the total number of students in align program in siliconvalley.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/alignsiliconvalley
     * @param 
     * @return the total number of students in align program in siliconvalley.
	 * @throws SQLException 
     * 
     */
	@GET
	@Path("analytics/alignsiliconvalley")
	@Produces(MediaType.APPLICATION_JSON)
	public int getStudentsAlignSiliconValley() throws SQLException{
		System.out.println("getting total number of ALIGN students in silicon valley");
		int total = totalStudentsInSiliconValleyDao.getTotalStudentsInSiliconValleyFromPublicDatabase();
		return total; 
	}
	
	private int gcd(int p, int q) {
	    if (q == 0) return p;
	    else return gcd(q, p % q);
	}
	
	private String ratio(int a, int b) {
		final int gcd = gcd(a,b);
		return String.valueOf(a/gcd) + " " + String.valueOf(b/gcd);
	}
}
