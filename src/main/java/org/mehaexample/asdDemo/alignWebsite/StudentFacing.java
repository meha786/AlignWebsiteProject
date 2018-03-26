package org.mehaexample.asdDemo.alignWebsite;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignprivate.StudentLoginsDao;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.model.alignadmin.AdminLogins;
import org.mehaexample.asdDemo.model.alignprivate.StudentLogins;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.restModels.MailClient;
import org.mehaexample.asdDemo.restModels.PasswordChangeObject;
import org.mehaexample.asdDemo.restModels.PasswordCreateObject;
import org.mehaexample.asdDemo.restModels.PasswordResetObject;
import org.mehaexample.asdDemo.utils.StringUtils;
import org.mehaexample.asdDemo.restModels.EmailToRegister;

@Path("student-facing")
public class StudentFacing {
	StudentsDao studentDao = new StudentsDao(true);
	StudentLoginsDao studentLoginsDao = new StudentLoginsDao(true); 
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

	/**------------------------------------------------------------------------------------------------*/

	//	//  webapi/student-facing/students/change-password/{NUID}
	//	@POST
	//	@Path("/{changePassword}")
	//	@Consumes(MediaType.APPLICATION_JSON)
	//	public boolean chnagePassword(PasswordChangeModel passwordModel){
	//		boolean exists = studentDao.ifEmailExists(passwordModel.getEmail());
	//		
	////		if(passwordDao.getOldPassword(passwordModel.getEmail()).equals(passwordModel.getOldPwd())) {
	////			passwordDao.updatePassword(passwordModel);
	////		}
	//		
	//		return true;
	//	}

	// webapi/student-facing/students/reset-password/{NUID}
	@POST
	@Path("/{resetPassword}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void resetPassword(String nuid) {
		// check if nuid is valid
		// get the email of student from db
		// send an email with the link
	}

	//  webapi/student-facing/students/registration
	@POST
	@Path("/{registerStudent}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void registerStudent(Students student) {
		// check if the student data is present in align db
		// register the student.
	}

	/**-----------------------------------------------------**/
	//	@POST 
	//	@Path("/{VerifyStudentLogin2FA}")
	//	@Consumes(MediaType.APPLICATION_JSON)
	//	public void VerifyStudentLogin2FA(){
	//		// check if password entered is correct from db
	//		// if it is correct, send him an email 
	//	}

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

	// ====================================================================================================================

	/**
	 * Send registration email to the student only if he/she is present in the align database
	 * 
	 * http://localhost:8080/alignWebsite/webapi/student-facing/registration
	 * @param adminEmail
	 * @return
	 */
	@POST
	@Path("/registration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// Send email to admin’s northeastern ID to reset the password.
	public Response sendRegistrationEmail(EmailToRegister emailToRegister){

		String studentEmail = emailToRegister.getEmail();

		if (studentEmail == null){
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Email Id can't be null").build();
		}else{

			Students student = studentDao.getStudentRecordByEmailId(studentEmail);

			// check if the student record exists in the student database
			if(student == null){
				return Response.status(Response.Status.BAD_REQUEST).
						entity("Student should be an Align Student!").build();
			}

			// check if the student is already registered
			StudentLogins studentLogin = studentLoginsDao.findStudentLoginsByEmail(studentEmail);

			if(studentLogin == null){
				// generate registration key 
				String registrationKey = createRegistrationKey(); 

				// after generation, send email
				MailClient.sendRegistrationEmail(studentEmail, registrationKey);

				return Response.status(Response.Status.OK).
						entity("Registration link sent succesfully to " + studentEmail).build(); 

			}else{

				return Response.status(Response.Status.NOT_ACCEPTABLE).
						entity("Student is Already Registered!" + studentEmail).build();
			} 

		}

	}

	/**
	 * This is a function to change an existing students password
	 * 
	 * http://localhost:8080/alignWebsite/webapi/student-facing/password-change
	 * @param passwordChangeObject
	 * @return 200 if password changed successfully else return 404
	 */
	@POST
	@Path("/password-change")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeUserPassword(PasswordChangeObject passwordChangeObject){

		System.out.println("--" + passwordChangeObject.getEmail()+","+passwordChangeObject.getOldPassword());
		
		StudentLogins studentLogins = studentLoginsDao.findStudentLoginsByEmail(passwordChangeObject.getEmail());

		if(studentLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("This Email doesn't exist: " + passwordChangeObject.getEmail()).build();
		}

		String enteredPassword = passwordChangeObject.getOldPassword();
	    String enteredHashedPassword = StringUtils.createHash(enteredPassword);
System.out.println("hash = " + enteredHashedPassword +" and existing pwd = " + studentLogins.getStudentPassword());
		if(studentLogins.getStudentPassword().equals(enteredHashedPassword)){
			
		    String hashNewPassword = StringUtils.createHash(passwordChangeObject.getNewPassword());

			studentLogins.setStudentPassword(hashNewPassword);
			studentLoginsDao.updateStudentLogin(studentLogins);

			return Response.status(Response.Status.OK).
					entity("Password Changed Succesfully!" ).build();
		}else{
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Incorrect Password: ").build();
		}
	}

	/**
	 * This function sends email to Student's northeastern ID to reset the password.
	 * 
	 * @param adminEmail
	 * @return 200 if password changed successfully else return 404
	 */
	@POST
	@Path("/password-reset")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendEmailForPasswordResetStudent2(PasswordResetObject passwordResetObject){

		String studentEmail = passwordResetObject.getEmail();

		if (studentEmail == null){
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Email Id can't be null").build();
		}else{

			StudentLogins studentLogins = studentLoginsDao.findStudentLoginsByEmail(studentEmail);

			// Check if student has Registered or not
			if(studentLogins == null){
				return Response.status(Response.Status.NOT_FOUND).
						entity("Email doesn't exist: " + studentLogins).build();
			}

			// generate registration key 
			String registrationKey = createRegistrationKey(); 

			System.out.println("Registration key: " + registrationKey);
			// after generation, send email
			MailClient.sendPasswordResetEmail(studentEmail, registrationKey);

			return Response.status(Response.Status.OK).
					entity("Password Reset link sent succesfully!" ).build(); 	
		}

	}

	/**
	 * This function creates the password and registers the student
	 * 
	 * @param passwordCreateObject
	 * @return 200 if password changed successfully else return 404
	 */
	@POST
	@Path("/create-password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPassword(PasswordCreateObject passwordCreateObject){
		String email = passwordCreateObject.getEmail();
		String password = passwordCreateObject.getPassword();
		String registrationKey = passwordCreateObject.getRegistrationKey();
		System.out.println(email + password + registrationKey); 

		// check if the student 
		
		// get the current student login
		StudentLogins studentLoginsExisting = studentLoginsDao.findStudentLoginsByEmail(email);
		if(studentLoginsExisting == null) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Invalid Student details. Student does not exist" ).build();
		}
		
		String databaseRegistrationKey = studentLoginsExisting.getRegistrationKey();
		Timestamp databaseTimestamp = studentLoginsExisting.getKeyExpiration();
		
		// override existing values for testing
		databaseTimestamp = new Timestamp(System.currentTimeMillis()+3600*1000);
		databaseRegistrationKey = "122345";
		
		if(studentLoginsExisting.isConfirmed() == false) {
			return Response.status(Response.Status.OK).
					entity("Password Already created. Consider resetting it" ).build();
		}
		
		// check if the entered registration key matches 
		if((databaseRegistrationKey.equals(registrationKey))){
			// if registration key matches, then check if its valid or not
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

			// check if the database time is after the current time
			if(databaseTimestamp.after(currentTimestamp)){
				String hashedPassword = StringUtils.createHash(password);
				studentLoginsExisting.setStudentPassword(hashedPassword);
				studentLoginsExisting.setConfirmed(true);
				boolean studentLoginUpdatedWithPassword = studentLoginsDao.updateStudentLogin(studentLoginsExisting);
				if(studentLoginUpdatedWithPassword) {
					return Response.status(Response.Status.OK).
							entity("Student registered successfully!" ).build();
				} else {
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
							entity("Database exception thrown" ).build();
				}
			} else {
				return Response.status(Response.Status.OK).
						entity(" Registration key expired successfully!" ).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Invalid registration key" ).build();
		}
	}

	private String createRegistrationKey() {

		return UUID.randomUUID().toString();
	}	
}
