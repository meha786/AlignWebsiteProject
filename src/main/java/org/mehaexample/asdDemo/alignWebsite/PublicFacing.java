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

import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.model.Students;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;

@Path("public")
public class PublicFacing {

	// student dao
	StudentsDao studentDao = new StudentsDao();
	
	
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
	
}
