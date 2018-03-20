package org.mehaexample.asdDemo.alignWebsite;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.UndergraduatesPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.WorkExperiencesPublicDao;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignpublic.TopGradYears;
import org.mehaexample.asdDemo.model.alignpublic.TopUndergradSchools;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;

@Path("public")
public class PublicFacing {

	// student dao
	StudentsDao studentDao = new StudentsDao();
	StudentsPublicDao studentsPublicDao = new StudentsPublicDao();
	UndergraduatesPublicDao undergraduatesPublicDao = new UndergraduatesPublicDao();
	WorkExperiencesPublicDao workExperiencesPublicDao = new WorkExperiencesPublicDao();
	
	
	/**
     * This is the function to search and filter for students like me.
     *	The body should be in the Json format like below:
     *	{
	 *		"degree":"PHD",
	 *		"enrollmentStatus":"FULL_TIME",
	 *		"gender":"M",
	 *		"campus":"SEATTLE",
	 *		"scholarship": "false",
	 *		"visa": "F1"
	 *	}
     *	http://localhost:8080/alignWebsite/webapi/public/search
     * @param search
     * @return List of all the students matching the criteria.
     */
	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Students> searchSimilarStudents(String search){
		JSONObject jsonObj = new JSONObject(search);
		
		String degree = "PLACEHOLDER";
		String campus = "PLACEHOLDER";
		String enrollmentStatus = "PLACEHOLDER";
		String gender = "PLACEHOLDER";
		Campus campusEnum = Campus.valueOf(campus);
		DegreeCandidacy degreeCandidacyEnum = DegreeCandidacy.valueOf(degree);
		EnrollmentStatus enrollmentEnum = EnrollmentStatus.valueOf(enrollmentStatus);
		Gender genderEnum = Gender.valueOf(gender);
		String scholarship = "PLACEHOLDER";
		String visa = "PLACEHOLDER";
		
		if (!jsonObj.isNull("degree")){
			degree = jsonObj.get("degree").toString();
			degreeCandidacyEnum = DegreeCandidacy.valueOf(degree);
		}
		if (!jsonObj.isNull("campus")){
			campus = jsonObj.get("campus").toString();
			campusEnum = Campus.valueOf(campus);
		}
		if (!jsonObj.isNull("enrollmentStatus")){
			enrollmentStatus = jsonObj.get("enrollmentStatus").toString();
			enrollmentEnum = EnrollmentStatus.valueOf(enrollmentStatus);
		}
		if (!jsonObj.isNull("gender")){
			gender = jsonObj.get("gender").toString();
			genderEnum = Gender.valueOf(gender);
		}
		if (!jsonObj.isNull("scholarship")){
			scholarship = jsonObj.get("scholarship").toString();
		}
		if (!jsonObj.isNull("visa")){
			visa = jsonObj.get("visa").toString();
		}
		
		ArrayList<Students> studentRecords = 
				(ArrayList<Students>) studentDao.searchSimilarStudents(degreeCandidacyEnum,campusEnum,enrollmentEnum,genderEnum,scholarship,visa);
		return studentRecords;
	}
	
	/**
     * This is the function to get a profile of a student based on his/her nuid.
     *	
     *	http://localhost:8080/alignWebsite/webapi/public/student/001234543
     * @param nuid
     * @return the student profile matching the nuid.
     */
	@GET
	@Path("student/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Students getStudentProfile(@PathParam("nuid") String nuid){
		System.out.println("getting student profile for nuid = " + nuid);
		Students studentRecord = studentDao.getStudentRecord(nuid);
		return studentRecord;
	}
	
	/**
	 * This is a function to get top X graduation years and students that graduated then
	 * 
	 * webapi/public-facing/students/top-graduationyears
	 * @param num
	 * @return
	 */
	@GET
	@Path("student/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopGraduationYears(int num){
		List<TopGradYears>  topGradYears = studentsPublicDao.getTopGraduationYears(num);
		
		if(topGradYears == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("Company can't be null: ").build();
		}else{
			return Response.status(Response.Status.OK).
					entity(topGradYears.toString()).build();
		}		
	}
	
	/**
	 * This is a function to get all undergradschools
	 * 
	 * webapi/public-facing/students/all-schools
	 * @return List of UnderGradSchools
	 */
	@GET
	@Path("student/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSchools(){
		List<String>  allUnderGradSchools = undergraduatesPublicDao.getListOfAllSchools();
		
		if(allUnderGradSchools == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("No Udergrad Scholls were found!").build();
		}else{
			return Response.status(Response.Status.OK).
					entity(allUnderGradSchools.toString()).build();
		}		
	}
	
	/**
	 * This is a function to get list of ALL Coop companies
	 * 
	 * webapi/public-facing/students/all-coops
	 * @return List of UnderGradSchools
	 */
	@GET
	@Path("student/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCoopCompanies(){
		
		List<String> listOfAllCoopCompanies = workExperiencesPublicDao.getListOfAllCoopCompanies();
		
		if(listOfAllCoopCompanies == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("No Coop companies were found!").build();
		}else{
			return Response.status(Response.Status.OK).
					entity(listOfAllCoopCompanies.toString()).build();
		}	
		
	}
	
}
