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
	 * This is the function to get top undergraduate schools.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/top-undergradschools
	 * @param search
	 * @return List of n top undergraduate schools
	 */
	@POST
	@Path("top-undergradschools2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUndergradSchools2(TopUnderGradSchools topUnderGradSchools) throws SQLException{

		List<TopUndergradSchools> undergrad = new ArrayList();

		int number = topUnderGradSchools.getNumber();
		if(number < 1){
			return Response.status(Response.Status.BAD_REQUEST).
					entity("The number can't be less than one").build();
		}
		
		undergrad = undergraduatesPublicDao.getTopUndergradSchools(number);
		
		return Response.status(Response.Status.OK).entity(undergrad).build();
	}

	@POST
	@Path("top-undergradschools")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUndergradSchools(String para) throws SQLException{
		System.out.println("par= "+para);

		JSONObject jsonObj = new JSONObject(para);
		List<TopUndergradSchools> undergrad = new ArrayList();
		int number = 10;
		if (!jsonObj.isNull("number")){
			try{
				System.out.println("yo= "+number);

				number = (int) jsonObj.getInt("number");
				System.out.println("noooo= "+number);

			} catch(Exception e) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request" + e).build();
			}
		}
		
		System.out.println("nums= "+number);
		undergrad = undergraduatesPublicDao.getTopUndergradSchools(number);
		JSONArray resultArray = new JSONArray();
		for(TopUndergradSchools ungrad : undergrad) {
			resultArray.put(ungrad);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}

//	==========================================================================================================
	/**
	 * This is the function to get top coops.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/top-coops
	 * @param search
	 * @return List of n top coops
	 */
	@POST
	@Path("top-coops2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopCoops2(TopCoopsNumber topCoopsNumber) throws SQLException{
		List<TopCoops> coops = new ArrayList();
		int number = topCoopsNumber.getNumber();

		coops = workExperiencesPublicDao.getTopCoops(number);

		return Response.status(Response.Status.OK).entity(coops).build();
	}
	
	@POST
	@Path("top-coops")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopCoops(String para) throws SQLException{
		JSONObject jsonObj = new JSONObject(para);
		List<TopCoops> coops = new ArrayList();
		int number = 10;
		if (!jsonObj.isNull("number")){
			try{
				number = (int) jsonObj.get("number");
			} catch(Exception e) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request").build();
			}
		}
		coops = workExperiencesPublicDao.getTopCoops(number);
		JSONArray resultArray = new JSONArray();
		for(TopCoops ungrad : coops) {
			resultArray.put(ungrad);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
//	==========================================================================================================

	/**
	 * This is the function to get top undergraduate degrees.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/top-undergraddegrees
	 * @param search
	 * @return List of n top undergraduate degrees
	 */
	@POST
	@Path("top-undergraddegrees2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUndergradDegrees2(TopUnderGradDegreesNumber topUnderGradDegreesNumber) throws SQLException{
		
		List<TopUndergradDegrees> degrees = new ArrayList();
		int number = topUnderGradDegreesNumber.getNumber();
		
		degrees = undergraduatesPublicDao.getTopUndergradDegrees(number);

		return Response.status(Response.Status.OK).entity(degrees).build();
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
	public Response getUndergradDegrees(String para) throws SQLException{
		JSONObject jsonObj = new JSONObject(para);
		List<TopUndergradDegrees> degrees = new ArrayList();
		int number = 10;
		if (!jsonObj.isNull("number")){
			try{
				number = (int) jsonObj.get("number");
			} catch(Exception e) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request").build();
			}
		}
		degrees = undergraduatesPublicDao.getTopUndergradDegrees(number);
		JSONArray resultArray = new JSONArray();
		for(TopUndergradDegrees ungrad : degrees) {
			resultArray.put(ungrad);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
//	==========================================================================================================

	/**
	 * This is the function to get top graduation years.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/top-graduationyears
	 * @param search
	 * @return List of n top graduation years
	 */
	@POST
	@Path("top-graduationyears2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopGraduationYears2(TopGraduationYearsNumber topGraduationYearsNumber) throws SQLException{

		List<TopGradYears> gradYears = new ArrayList();
		int number = topGraduationYearsNumber.getNumber();
		
		gradYears = studentsPublicDao.getTopGraduationYears(number);
		
		return Response.status(Response.Status.OK).entity(gradYears).build();
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
	public Response getTopGraduationYears(String para) throws SQLException{
		JSONObject jsonObj = new JSONObject(para);
		List<TopGradYears> gradYears = new ArrayList();
		int number = 10;
		if (!jsonObj.isNull("number")){
			try{
				number = (int) jsonObj.get("number");
			} catch(Exception e) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Bad Request").build();
			}
		}
		gradYears = studentsPublicDao.getTopGraduationYears(number);
		JSONArray resultArray = new JSONArray();
		for(TopGradYears gy : gradYears) {
			resultArray.put(gy);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
//	==========================================================================================================
	
	/**
	 * This is a function to get all undergrad schools
	 * 
	 * http://localhost:8080/alignWebsite/webapi/public-facing/all-schools
	 * @return List of UnderGradSchools
	 */
	@GET
	@Path("all-schools2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllSchools2(){
		List<String>  allUnderGradSchools = undergraduatesPublicDao.getListOfAllSchools();
		
		return Response.status(Response.Status.OK).entity(allUnderGradSchools).build();	
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
		JSONArray resultArray = new JSONArray();
		for(String gs : allUnderGradSchools) {
			resultArray.put(gs);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();	
	}
	
//	==========================================================================================================

	/**
	* This is a function to get list of ALL Coop companies
	* 
	* http://localhost:8080/alignWebsite/webapi/public-facing/all-coops
	* @return List of UnderGradSchools
	*/
	@GET
	@Path("/all-coops2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCoopCompanies2(){
		List<String> listOfAllCoopCompanies = workExperiencesPublicDao.getListOfAllCoopCompanies();
		
		return Response.status(Response.Status.OK).entity(listOfAllCoopCompanies).build();	
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
		JSONArray resultArray = new JSONArray();
		for(String gs : listOfAllCoopCompanies) {
			resultArray.put(gs);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();	
	}
	
//	==========================================================================================================

	/**
	 * This is the function to get all undergraduate degrees.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/all-undergraddegrees
	 * @param search
	 * @return List of all undergraduate degrees
	 */
	@GET
	@Path("all-undergraddegrees2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUndergradDegrees2() throws SQLException{
		List<String> degrees = new ArrayList();
		
		degrees = undergraduatesPublicDao.getListOfAllUndergraduateDegrees();
		
		return Response.status(Response.Status.OK).entity(degrees).build();
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
		JSONArray resultArray = new JSONArray();
		for(String ungrad : degrees) {
			resultArray.put(ungrad);
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
//	==========================================================================================================

	/**
	 * This is the function to get all graduate years.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/all-grad-years
	 * @param search
	 * @return List of all graduate years
	 */
	@GET
	@Path("all-grad-years2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllGradYears2() throws SQLException{
		List<Integer> years = new ArrayList();
		
		years = studentsPublicDao.getListOfAllGraduationYears();
		
		return Response.status(Response.Status.OK).entity(years).build();
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
		JSONArray resultArray = new JSONArray();
		for(Integer year : years) {
			resultArray.put(year.toString());
	    }
		return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
	}
	
//	==========================================================================================================

	/**
	 * This is the function to get all students.
	 *	The body should be in the JSON format like below:
	 *	
	 *	http://localhost:8080/alignWebsite/webapi/public-facing/students
	 * @param search
	 * @return List of all students
	 */
	@GET
	@Path("students2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents2() throws SQLException{
		List<StudentsPublic> studentList = new ArrayList();
		studentList = studentsPublicDao.getListOfAllStudents();
		
		return Response.status(Response.Status.OK).entity(studentList).build();
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
		JSONArray resultArray = new JSONArray();
		for(StudentsPublic st : studentList) {
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
	
//	==========================================================================================================

	/**
     * This is the function to search for students
     *	
     *	http://localhost:8080/alignWebsite/webapi/public-facing/students
     * @param firstname
     * @return the list of student profiles matching the fields.
     */
	@POST
	@Path("students-search2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchStudent2(StudentSerachCriteria studentSerachCriteria){
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
	
	/**
     * This is the function to search for students
     *	
     *	http://localhost:8080/alignWebsite/webapi/public-facing/students
     * @param firstname
     * @return the list of student profiles matching the fields.
     */
	@POST
	@Path("students-search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchStudent(String search){
		JSONObject jsonObj = new JSONObject(search);
		System.out.println(jsonObj);
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		List<String> coops = new ArrayList<String>();
		List<String> undergraddegree = new ArrayList<String>();
		List<String> undergradschool = new ArrayList<String>();
		List<String> graduationyear = new ArrayList<String>();
		if (!jsonObj.isNull("coops")){
			System.out.println(jsonObj.get("coops"));
			JSONArray coopsArray = (JSONArray) jsonObj.get("coops");
			System.out.println(coopsArray);
			for(Object cp : coopsArray) {
				coops.add((String) cp);
			}
			map.put("coop",coops);
		}
		if (!jsonObj.isNull("undergraddegree")){
			System.out.println(jsonObj.get("undergraddegree"));
			JSONArray undergraddegreeArray = (JSONArray) jsonObj.get("undergraddegree");
			System.out.println(undergraddegreeArray);
			for(Object cp : undergraddegreeArray) {
				undergraddegree.add((String) cp);
			}
			map.put("undergradDegree",undergraddegree);
		}
		if (!jsonObj.isNull("undergradschool")){
			System.out.println(jsonObj.get("undergradschool"));
			JSONArray undergradschoolArray = (JSONArray) jsonObj.get("undergradschool");
			System.out.println(undergradschoolArray);
			for(Object cp : undergradschoolArray) {
				undergradschool.add((String) cp);
			}
			map.put("undergradSchool",undergradschool);
		}
		if (!jsonObj.isNull("graduationyear")){
			System.out.println(jsonObj.get("graduationyear"));
			JSONArray graduationyearArray = (JSONArray) jsonObj.get("graduationyear");
			System.out.println(graduationyearArray);
			for(Object cp : graduationyearArray) {
				graduationyear.add((String) cp);
			}
			map.put("graduationYear",graduationyear);
		}
		
		for(String key: map.keySet()){
			System.out.println("Key: " + key );
			
			List<String> values = map.get(key);
			
			for(String s: values){
				System.out.print(", " + s); 
			}
			
		}
		List<StudentsPublic> studentRecords =  studentsPublicDao.getPublicFilteredStudents(map, 1, 20);
		JSONArray resultArray = new JSONArray();
		
		for(StudentsPublic st : studentRecords) {
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
	
	
}
