package org.mehaexample.asdDemo.alignWebsite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.UndergraduatesPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.WorkExperiencesPublicDao;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.TopCoops;
import org.mehaexample.asdDemo.model.alignpublic.TopGradYears;
import org.mehaexample.asdDemo.model.alignpublic.TopUndergradDegrees;
import org.mehaexample.asdDemo.model.alignpublic.TopUndergradSchools;
import org.mehaexample.asdDemo.restModels.StudentSerachCriteria;
import org.mehaexample.asdDemo.restModels.TopCoopsNumber;
import org.mehaexample.asdDemo.restModels.TopGraduationYearsNumber;
import org.mehaexample.asdDemo.restModels.TopUnderGradDegreesNumber;
import org.mehaexample.asdDemo.restModels.TopUnderGradSchools;

@Path("public-facing")
public class PublicFacing {
	UndergraduatesPublicDao undergraduatesPublicDao = new UndergraduatesPublicDao(true);
	WorkExperiencesPublicDao workExperiencesPublicDao = new WorkExperiencesPublicDao(true);
	StudentsPublicDao studentsPublicDao = new StudentsPublicDao(true);
	
	/**
	 * This is the function to get top n undergraduate schools
	 * 
	 * @param topUnderGradSchools
	 * @return List of n top undergraduate schools
	 * @throws SQLException
	 */
	@POST
	@Path("top-undergradschools")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUndergradSchools(TopUnderGradSchools topUnderGradSchools) throws SQLException{

		List<TopUndergradSchools> undergrad = new ArrayList();

		int number = topUnderGradSchools.getNumber();
		if(number < 1){
			return Response.status(Response.Status.BAD_REQUEST).
					entity("The number can't be less than one").build();
		}
		
		undergrad = undergraduatesPublicDao.getTopUndergradSchools(number);
		
		return Response.status(Response.Status.OK).entity(undergrad).build();
	}

	/**
	 * This is the function to get top coops.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/top-coops
	 * @param search
	 * @return List of n top coops
	 */
	@POST
	@Path("top-coops")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopCoops(TopCoopsNumber topCoopsNumber) throws SQLException{
		List<TopCoops> coops = new ArrayList();
		int number = topCoopsNumber.getNumber();

		coops = workExperiencesPublicDao.getTopCoops(number);

		return Response.status(Response.Status.OK).entity(coops).build();
	}
	
	/**
	 * This is the function to get top undergraduate degrees.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/top-undergraddegrees
	 * @param search
	 * @return List of n top undergraduate degrees
	 */
	@POST
	@Path("top-undergraddegrees")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUndergradDegrees(TopUnderGradDegreesNumber topUnderGradDegreesNumber) throws SQLException{
		
		List<TopUndergradDegrees> degrees = new ArrayList();
		int number = topUnderGradDegreesNumber.getNumber();
		
		degrees = undergraduatesPublicDao.getTopUndergradDegrees(number);

		return Response.status(Response.Status.OK).entity(degrees).build();
	}
	
	/**
	 * This is the function to get top graduation years.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/top-graduationyears
	 * @param search
	 * @return List of n top graduation years
	 */
	@POST
	@Path("top-graduationyears")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopGraduationYears(TopGraduationYearsNumber topGraduationYearsNumber) throws SQLException{

		List<TopGradYears> gradYears = new ArrayList();
		int number = topGraduationYearsNumber.getNumber();
		
		gradYears = studentsPublicDao.getTopGraduationYears(number);
		
		return Response.status(Response.Status.OK).entity(gradYears).build();
	}
	
	/**
	 * This is a function to get all undergrad schools
	 * 
	 * http://localhost:8080/alignWebsite/webapi/public-facing/all-schools
	 * @return List of UnderGradSchools
	 */
	@GET
	@Path("all-schools")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSchools(){
		List<String>  allUnderGradSchools = undergraduatesPublicDao.getListOfAllSchools();
		
		return Response.status(Response.Status.OK).entity(allUnderGradSchools).build();	
	}
	

	/**
	* This is a function to get list of ALL Coop companies
	* 
	* http://localhost:8080/alignWebsite/webapi/public-facing/all-coops
	* @return List of UnderGradSchools
	*/
	@GET
	@Path("/all-coops")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCoopCompanies(){
		List<String> listOfAllCoopCompanies = workExperiencesPublicDao.getListOfAllCoopCompanies();
		
		return Response.status(Response.Status.OK).entity(listOfAllCoopCompanies).build();	
	}
	
	/**
	 * This is the function to get all undergraduate degrees.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/all-undergraddegrees
	 * @param search
	 * @return List of all undergraduate degrees
	 */
	@GET
	@Path("all-undergraddegrees")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUndergradDegrees() throws SQLException{
		List<String> degrees = new ArrayList();
		
		degrees = undergraduatesPublicDao.getListOfAllUndergraduateDegrees();
		
		return Response.status(Response.Status.OK).entity(degrees).build();
	}
	
	/**
	 * This is the function to get all graduate years.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/all-grad-years
	 * @param search
	 * @return List of all graduate years
	 */
	@GET
	@Path("all-grad-years")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllGradYears() throws SQLException{
		List<Integer> years = new ArrayList();
		
		years = studentsPublicDao.getListOfAllGraduationYears();
		
		return Response.status(Response.Status.OK).entity(years).build();
	}
	
	/**
	 * This is the function to get all students.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/students
	 * @param search
	 * @return List of all students
	 */
	@GET
	@Path("students")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents() throws SQLException{
		List<StudentsPublic> studentList = new ArrayList();
		studentList = studentsPublicDao.getListOfAllStudents();
		
		return Response.status(Response.Status.OK).entity(studentList).build();
	}
	
	/**
     * This is the function to search for students
     *	
     *	http://localhost:8080/alignWebsite/webapi/public-facing/students
     * @param firstname
     * @return the list of student profiles matching the fields.
     */
	@POST
	@Path("students")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchStudent(StudentSerachCriteria studentSerachCriteria){
		Map<String, List<String>> searchCriteriaMap = new HashMap<>();

		if(studentSerachCriteria.getCoops().size() > 0){
			searchCriteriaMap.put("coop", studentSerachCriteria.getCoops());
		}
		
		if(studentSerachCriteria.getUndergraddegree().size() > 0){
			searchCriteriaMap.put("undergradDegree", studentSerachCriteria.getUndergraddegree());
		}

		if(studentSerachCriteria.getUndergradschool().size() > 0){
			searchCriteriaMap.put("undergradSchool", studentSerachCriteria.getUndergradschool());
		}

		if(studentSerachCriteria.getGraduationyear().size() > 0){
			searchCriteriaMap.put("graduationYear", studentSerachCriteria.getGraduationyear());
		}

		for(String key: searchCriteriaMap.keySet()){
			System.out.println("Key: " + key );
			
			List<String> values = searchCriteriaMap.get(key);
			
			for(String s: values){
				System.out.print(", " + s); 
			}
			
		}
		
		List<StudentsPublic> studentRecords =  studentsPublicDao.getPublicFilteredStudents(searchCriteriaMap, 1, 20);
	
		return Response.status(Response.Status.OK).entity(studentRecords).build();
	}
	
}
