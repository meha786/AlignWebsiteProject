package org.mehaexample.asdDemo.alignWebsite;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.model.Students;

@Path("studentresource")
public class StudentResource {
	StudentsDao studentDao = new StudentsDao();

	/**
	 * Method handling HTTP GET requests. The returned object will be sent
	 * to the client as "APPLICATION_JSON" media type.
	 *
	 * @return String that will be returned as a APPLICATION_JSON response.
	 */

	/**
	 * Get all the students
	 *  
	 * @return list of all students.
	 * http://localhost:8080/alignWebsite/webapi/studentresource/all
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Students> getAllStudents() {
		List<Students> list = studentDao.getAllStudents();
		return list;
	}

	/**
	 * Fetch the student details by nuid
	 * 
	 * @param nuid
	 * @return a student object
	 * http://localhost:8080/alignWebsite/webapi/studentresource/id/211234549
	 */
	@GET
	@Path("/id/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Students getStudentRecord(@PathParam("nuid") String nuid){
		System.out.println("getting student for nuid = " + nuid);
		Students studentRecord = studentDao.getStudentRecord(nuid);
		return studentRecord;
	}
	
	/**
	 * Fetch the student details by nuid valuing his/her privacy settings
	 * 
	 * @param nuid
	 * @return a student object
	 * http://localhost:8080/alignWebsite/webapi/studentresource/privacies/id/211234549
	 */
	@GET
	@Path("/privacies/id/{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Students getStudentRecordPrivately(@PathParam("nuid") String nuid){
		System.out.println("getting student for nuid = " + nuid);
		Students studentRecord = studentDao.getStudentRecordPrivately(nuid);
		return studentRecord;
	}

	/**
	 * Fetch the student details by email id
	 * 
	 * @param emailId
	 * @return a student record
	 * http://localhost:8080/alignWebsite/webapi/studentresource/email/alvin.straws@husky.neu.edu
	 */
	@GET
	@Path("/email/{emailId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Students getStudentRecordByEmailId(@PathParam("emailId") String emailId){
		System.out.println("get by email : " + emailId);
		Students studentRecord = studentDao.getStudentRecordByEmailId(emailId);
		return studentRecord; 
	}

	/**
	 * Add a student into the database
	 * 
	 * @param student
	 * @return status created if added successfully else error message
	 * http://localhost:8080/alignWebsite/webapi/studentresource
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveStudentForm(Students student){
		boolean exists = studentDao.ifNuidExists(student.getNeuId());
		if(exists == false){			
			studentDao.addStudent(student);	

			return "Added Student sucessfully!";
		}else{

			return "The entered NUID exists already";
		}
	}

	/**
	 * Edit the fields of a student 
	 * 
	 * @param neuid
	 * @return a student record
	 * http://localhost:8080/alignWebsite/webapi/studentresource/id/0000002
	 */
	@PUT
	@Path("/id/{neuId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateStudentRecord(@PathParam("neuId") String neuId , Students student) {
		if(student == null){
			return "Student cant be null";
		}

		if(!student.getNeuId().equals(neuId)){
			return "NeuId Cant be updated";
		}

		studentDao.updateStudentRecord(student);

		return "Student record updated successfully";
	}

	/**
	 * Search for other students
	 * 
	 * @param firstName
	 * @return list of students with matched first name
	 * http://localhost:8080/alignWebsite/webapi/studentresource/search/Tom21
	 */
	@GET
	@Path("/search/{firstName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Students> getStudentRecordByFirstName(@PathParam("firstName") String firstName){
		
		if(firstName == null || firstName.isEmpty()){
			return null;	
		}
		
		List<Students> studentList = studentDao.searchStudentRecord(firstName);
		
		if(studentList.size() == 0){
			return new ArrayList<>();
		}
		return studentList; 
	}


	// student opt-in/opt-out
	@PUT
	@Path("/opt-in/{nuid}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public void updateStudentOptIn(@PathParam("nuid") String nuid , Students student) {
		System.out.println("update opt-in field for nuid=" + nuid);
    }	
	
	/**
	 * Delete a student
	 * 
	 * @param firstName
	 * @return String
	 * http://localhost:8080/alignWebsite/webapi/studentresource/211232248
	 */
	@DELETE
	@Path("{neuid}")
	@Produces({ MediaType.APPLICATION_JSON})
	public void deleteStudentByNUID(@PathParam("neuid") String neuid)
	{      
		Students student = new Students();

		System.out.println("nuid to be deleted is: " + neuid);
		boolean exists = studentDao.ifNuidExists(neuid);
		if(exists == true){
			studentDao.deleteStudent(neuid);
		}else{
			System.out.println("This nuid doesn't exist");
		}
	}
}
