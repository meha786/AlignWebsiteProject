package org.mehaexample.asdDemo.alignWebsite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignadmin.AdminLoginsDao;
import org.mehaexample.asdDemo.dao.alignadmin.ElectivesAdminDao;
import org.mehaexample.asdDemo.dao.alignadmin.GenderRatioDao;
import org.mehaexample.asdDemo.dao.alignprivate.ElectivesDao;
import org.mehaexample.asdDemo.dao.alignprivate.PriorEducationsDao;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.dao.alignprivate.WorkExperiencesDao;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.model.alignadmin.AdminLogins;
import org.mehaexample.asdDemo.model.alignadmin.ElectivesAdmin;
import org.mehaexample.asdDemo.model.alignadmin.GenderRatio;
import org.mehaexample.asdDemo.model.alignprivate.StudentBasicInfo;
import org.mehaexample.asdDemo.model.alignprivate.StudentCoopList;
import org.mehaexample.asdDemo.model.alignprivate.StudentLogins;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignprivate.WorkExperiences;
import org.mehaexample.asdDemo.restModels.MailClient;
import org.mehaexample.asdDemo.restModels.PasswordChangeObject;
import org.mehaexample.asdDemo.restModels.PasswordCreateObject;

@Path("admin-facing")
public class Admin{
	
	// DAO methods
	StudentsDao studentDao = new StudentsDao();
	ElectivesAdminDao electiveDao = new ElectivesAdminDao();
	GenderRatioDao genderRatioDao = new GenderRatioDao();
	WorkExperiencesDao workExperiencesDao = new WorkExperiencesDao();
	PriorEducationsDao priorEducationsDao = new PriorEducationsDao();
	ElectivesDao electivesDao = new ElectivesDao();
	AdminLoginsDao adminLoginsDao = new AdminLoginsDao();
	StudentLogins studentLogins = new StudentLogins();

	/**
     * This is the function to search for students
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin-facing/students
     * @param firstname
     * @return the list of student profiles matching the fields.
     */
	@POST
	@Path("students")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchStudent(String search){
		JSONObject jsonObj = new JSONObject(search);
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		String firstname = "";
		String lastname = "";
		String email = "";
		String degreeyear = "";
		String enrollmentstatus = "";
		String campus = "";
		String company = "";
		if (!jsonObj.isNull("firstname")){
			firstname = jsonObj.get("firstname").toString();
			ArrayList<String> firstnameList = new ArrayList<String>();
			firstnameList.add(firstname);
			map.put("firstName",firstnameList);
		}
		if (!jsonObj.isNull("lastname")){
			lastname = jsonObj.get("lastname").toString();
			ArrayList<String> lastnameList = new ArrayList<String>();
			lastnameList.add(lastname);
			map.put("lastName",lastnameList);
		}
		if (!jsonObj.isNull("email")){
			email = jsonObj.get("email").toString();
			ArrayList<String> emailList = new ArrayList<String>();
			emailList.add(email);
			map.put("email",emailList);
		}
		if (!jsonObj.isNull("degreeyear")){
			degreeyear = jsonObj.get("degreeyear").toString();
			ArrayList<String> degreeyearList = new ArrayList<String>();
			degreeyearList.add(degreeyear);
			map.put("expectedLastYear",degreeyearList);
		}
		if (!jsonObj.isNull("enrollmentstatus")){
			enrollmentstatus = jsonObj.get("enrollmentstatus").toString();
			ArrayList<String> enrollmentstatusList = new ArrayList<String>();
			enrollmentstatusList.add(enrollmentstatus);
			map.put("enrollmentStatus",enrollmentstatusList);
		}
		if (!jsonObj.isNull("campus")){
			campus = jsonObj.get("campus").toString();
			ArrayList<String> campusList = new ArrayList<String>();
			campusList.add(campus);
			map.put("campus",campusList);
		}
		if (!jsonObj.isNull("company")){
			company = jsonObj.get("company").toString();
			ArrayList<String> companyList = new ArrayList<String>();
			companyList.add(company);
			map.put("companyName",companyList);
		}
		ArrayList<Students> studentRecords = (ArrayList<Students>) studentDao.getAdminFilteredStudents(map, 1, 20);
		JSONArray resultArray = new JSONArray();
		
		for(Students st : studentRecords) {
			JSONObject studentJson = new JSONObject();
			JSONObject eachStudentJson = new JSONObject(st);
			java.util.Set<String> keys = eachStudentJson.keySet();
			for(int i=0;i<keys.toArray().length; i++){
				studentJson.put(((String) keys.toArray()[i]).toLowerCase(), eachStudentJson.get((String) keys.toArray()[i]));
			}
			resultArray.put(studentJson);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
	/**
     * This is the function to search a student based on his/her nuid.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/student/090
     * @param nuid
     * @return the student profile matching the nuid.
     */
	@GET
	@Path("students/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentProfile(@PathParam("nuid") String nuid){
		if(!studentDao.ifNuidExists(nuid)){
			return Response.status(Response.Status.NOT_FOUND).entity("No Student record exists with given ID").build();
		} else {
			Students studentRecord = studentDao.getStudentRecord(nuid);
			JSONObject jsonObj = new JSONObject(studentRecord);
			ArrayList<WorkExperiences> workEx = (ArrayList<WorkExperiences>) workExperiencesDao.getWorkExperiencesByNeuId(nuid);
			jsonObj.put("company", workEx);
			ArrayList<ElectivesAdmin> electives = (ArrayList<ElectivesAdmin>) electiveDao.getElectivesByNeuId(nuid);
			jsonObj.put("courses", electives);
			return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
		}
	}
	
	// Data analytics methods
	
	/**
     * This is the function to get the men to women ratio.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/gender-ratio
     * @param 
     * @return the gender ratio is returned as string
	 * @throws SQLException 
     */
	@POST
	@Path("analytics/gender-ratio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGenderRatio(String para) throws SQLException{
		JSONObject jsonObj = new JSONObject(para);
		Object campus ="";
		List<GenderRatio> ratio = new ArrayList();
		if (!jsonObj.isNull("campus")){
			try{
				campus = jsonObj.get("campus");
				ratio = genderRatioDao.getYearlyGenderRatio(Campus.valueOf(campus.toString().toUpperCase()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist").build();
			}
		} else {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus field cannot be null").build();
		}
		JSONArray resultArray = new JSONArray();
		for(GenderRatio gr : ratio) {
			JSONObject studentJson = new JSONObject();
			JSONObject eachStudentJson = new JSONObject(gr);
			java.util.Set<String> keys = eachStudentJson.keySet();
			for(int i=0;i<keys.toArray().length; i++){
				studentJson.put(((String) keys.toArray()[i]).toLowerCase(), eachStudentJson.get((String) keys.toArray()[i]));
			}
			resultArray.put(studentJson);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
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
	@POST
	@Path("analytics/top-bachelor-degrees")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopBachelorDegree(String para) throws SQLException{
		JSONObject jsonObj = new JSONObject(para);
		Object campus ="";
		List<String> degrees = new ArrayList();
		if (!jsonObj.isNull("campus")){
			try{
				campus = jsonObj.get("campus");
				degrees = priorEducationsDao.getTopTenBachelors(Campus.valueOf(campus.toString().toUpperCase()));
			} catch(Exception e){
				degrees = priorEducationsDao.getTopTenBachelors(null);
			}
		}
		JSONArray resultArray = new JSONArray();
		for(String deg : degrees) {
			resultArray.put(deg);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
	/**
     * This is the function to get the top 10 employers.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/top-employers
     * @param 
     * @return the list of top 10 employers
	 * @throws SQLException 
     * 
     */
	@POST
	@Path("analytics/top-employers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopEmployers(String para) throws SQLException{
		JSONObject jsonObj = new JSONObject(para);
		Object campus = null;
		Campus camp;
		int year;
		List<String> employers = new ArrayList();
		try{
			if (!jsonObj.isNull("campus")){
				campus = jsonObj.get("campus");
				camp = Campus.valueOf(campus.toString().toUpperCase());
			} else {
				camp = null;
			}
		} catch(Exception e){
			camp = null;
		}
		try{
			if (!jsonObj.isNull("year")){
				year = Integer.valueOf(jsonObj.get("year").toString());
			} else {
				year = -1;
			}
		} catch(Exception e){
			year = -1;
		}
		if (year <0)
			employers = workExperiencesDao.getTopTenEmployers(camp,null);
		else
			employers = workExperiencesDao.getTopTenEmployers(camp,year);
		JSONArray resultArray = new JSONArray();
		for(String emp : employers) {
			resultArray.put(emp);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
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
	@POST
	@Path("analytics/top-electives")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopElectives(String para) throws SQLException{
		JSONObject jsonObj = new JSONObject(para);
		Object campus =null;
		Campus camp;
		int year;
		List<String> electives = new ArrayList();
		try{
			if (!jsonObj.isNull("campus")){
				campus = jsonObj.get("campus");
				camp = Campus.valueOf(campus.toString().toUpperCase());
			} else {
				camp = null;
			}
		} catch(Exception e){
			camp = null;
		}
		try{
			if (!jsonObj.isNull("year")){
				year = Integer.valueOf(jsonObj.get("year").toString());
			} else {
				year = -1;
			}
		} catch(Exception e){
			year = -1;
		}
		if (year <0)
			electives = electivesDao.getTopTenElectives(camp,null);
		else
			electives = electivesDao.getTopTenElectives(camp,year);
		JSONArray resultArray = new JSONArray();
		for(String ele : electives) {
			resultArray.put(ele);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
	/**
     * This is the function to get the list of companies students worked for as coop.
     *	
     *	http://localhost:8080/alignWebsite/webapi/admin/analytics/coop-students
     * @param 
     * @return the list of top 10 electives
	 * @throws SQLException 
     * 
     */
	@POST
	@Path("analytics/coop-students")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCoopStudents(String para) throws SQLException{
		JSONObject jsonObj = new JSONObject(para);
		Object campus =null;
		Campus camp;
		int year;
		List<StudentCoopList> coopStudentsList = new ArrayList();
		try{
			if (!jsonObj.isNull("campus")){
				campus = jsonObj.get("campus");
				camp = Campus.valueOf(campus.toString().toUpperCase());
			} else {
				camp = null;
			}
		} catch(Exception e){
			camp = null;
		}
		try{
			if (!jsonObj.isNull("year")){
				year = Integer.valueOf(jsonObj.get("year").toString());
			} else {
				year = -1;
			}
		} catch(Exception e){
			year = -1;
		}
		if (year <0)
			coopStudentsList = workExperiencesDao.getStudentCoopCompanies(camp,null);
		else
			coopStudentsList = workExperiencesDao.getStudentCoopCompanies(camp,year);
		
		JSONArray resultArray = new JSONArray();
		for(StudentCoopList cl : coopStudentsList) {
			JSONObject studentJson = new JSONObject();
			JSONObject eachStudentJson = new JSONObject(cl);
			java.util.Set<String> keys = eachStudentJson.keySet();
			for(int i=0;i<keys.toArray().length; i++){
				studentJson.put(((String) keys.toArray()[i]).toLowerCase(), eachStudentJson.get((String) keys.toArray()[i]));
			}
			resultArray.put(studentJson);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
	
	
	//====================================================================================

	/**
	 * This is a function for retrieving the students working in a given company
	 * 
	 * http://localhost:8080/alignWebsite/webapi/admin-facing/analytics/company
	 * @param params
	 * @return
	 */
	@POST
	@Path("/analytics/company")
	public Response getStudentsWorkingForACompany(String params){
		JSONObject jsonObj = new JSONObject(params);
		Object campus = null;
		Campus camp;
		int year;
		String company = null;
		List<StudentBasicInfo> studentsList = new ArrayList();
		
		// company can't have null value
		if (!jsonObj.isNull("company")){
			try {
				campus = jsonObj.get("campus");
				camp = Campus.valueOf(campus.toString().toUpperCase());
			} catch(Exception e){
				camp = null;
			}
			try{
				if (!jsonObj.isNull("year")){
					year = Integer.valueOf(jsonObj.get("year").toString());
				} else {
					year = -1;
				}
			} catch(Exception e){
				year = -1;
			}
			
			company = (String) jsonObj.get("company");
			
			if (year <0)
				studentsList = workExperiencesDao.getStudentsWorkingInACompany(camp, null, company);
			else
				studentsList = workExperiencesDao.getStudentsWorkingInACompany(camp, year, company);
				
		}else{
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Company can't be null: ").build();
		}
		
		JSONArray resultArray = new JSONArray();
		
		for(StudentBasicInfo st : studentsList) {
			JSONObject studentJson = new JSONObject();
			JSONObject eachStudentJson = new JSONObject(st);
			java.util.Set<String> keys = eachStudentJson.keySet();
			for(int i=0;i<keys.toArray().length; i++){
				studentJson.put(((String) keys.toArray()[i]).toLowerCase(), eachStudentJson.get((String) keys.toArray()[i]));
			}
			resultArray.put(studentJson);
	    }
		
		return Response.status(Response.Status.OK).
				entity(resultArray.toString()).build();  
	}

	
	/**
	 * This is a function for retrieving the students working as full time
	 * 
	 * http://localhost:8080/alignWebsite/webapi/admin-facing/analytics/working
	 * @param params
	 * @return
	 */
	@POST
	@Path("/analytics/working")
	public Response getStudentWorkingFullTime(String params){
		JSONObject jsonObj = new JSONObject(params);
		Object campus = null;
		Campus camp;
		int year;
		List<StudentCoopList> studentsList = new ArrayList();
		
		// company can't have null value
		try {
			campus = jsonObj.get("campus");
			camp = Campus.valueOf(campus.toString().toUpperCase());
		} catch(Exception e){
			camp = null;
		}
		try{
			if (!jsonObj.isNull("year")){
				year = Integer.valueOf(jsonObj.get("year").toString());
			} else {
				year = -1;
			}
		} catch(Exception e){
			year = -1;
		}
			
		if (year <0)
			studentsList = workExperiencesDao.getStudentCurrentCompanies(camp, null);
		else
			studentsList = workExperiencesDao.getStudentCurrentCompanies(camp, year);
				
		JSONArray resultArray = new JSONArray();
		
		for(StudentCoopList st : studentsList) {
			JSONObject studentJson = new JSONObject();
			JSONObject eachStudentJson = new JSONObject(st);
			java.util.Set<String> keys = eachStudentJson.keySet();
			for(int i=0;i<keys.toArray().length; i++){
				studentJson.put(((String) keys.toArray()[i]).toLowerCase(), eachStudentJson.get((String) keys.toArray()[i]));
			}
			resultArray.put(studentJson);
	    }
		
		return Response.status(Response.Status.OK).
				entity(resultArray.toString()).build();  
	}
	
	/**
	 * This is a function to change an existing admin's password
	 * 
	 * http://localhost:8080/alignWebsite/webapi/password-changes
	 * @param passwordChangeObject
	 * @return 200 if password changed successfully else return 404
	 */
	@POST
	@Path("/password-change")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeUserPassword(PasswordChangeObject passwordChangeObject){

		AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(passwordChangeObject.getEmail());

		if(adminLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("Email doesn't exist: Invalid Email" + passwordChangeObject.getEmail()).build();
		}

		if(adminLogins.getAdminPassword().equals(passwordChangeObject.getOldPassword())){
			adminLogins.setAdminPassword(passwordChangeObject.getNewPassword());
			adminLoginsDao.updateAdminLogin(adminLogins);

			return Response.status(Response.Status.OK).
					entity("Password Changed Succesfully!" ).build();
		}else{
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Password Entered was Incorrect: ").build();
		}
	}

	/**
	 * This function sends email to admin’s northeastern ID to reset the password.
	 * 
	 * @param adminEmail
	 * @return 200 if password changed successfully else return 404
	 */
	@POST
	@Path("/password-reset")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmailForPasswordResetAdmin(String adminEmailInput){

		JSONObject jsonObj = new JSONObject(adminEmailInput);

		if (jsonObj.isNull("email")){
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Email Id can't be null").build();
		}else{
			String adminEmail = (String) jsonObj.get("email");

			AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(adminEmail);

			// or invalid entry
			if(adminLogins == null){
				return Response.status(Response.Status.NOT_FOUND).
						entity("Email doesn't exist: " + adminEmail).build();
			}

			// generate registration key 
			String registrationKey = createRegistrationKey(); 

			System.out.println("Registration key: " + registrationKey);
			// after generation, send email
			MailClient.sendPasswordResetEmail(adminEmail, registrationKey);

			return Response.status(Response.Status.OK).
					entity("Password Reset link sent succesfully!" ).build(); 	
		}
	}

	private String createRegistrationKey() {

		return UUID.randomUUID().toString();
	}	
	
	
}
