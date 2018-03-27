package org.mehaexample.asdDemo.alignWebsite;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.mehaexample.asdDemo.dao.alignprivate.CoursesDao;
import org.mehaexample.asdDemo.dao.alignprivate.ElectivesDao;
import org.mehaexample.asdDemo.dao.alignprivate.ExtraExperiencesDao;
import org.mehaexample.asdDemo.dao.alignprivate.ProjectsDao;
import org.mehaexample.asdDemo.dao.alignprivate.StudentLoginsDao;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.dao.alignprivate.WorkExperiencesDao;
import org.mehaexample.asdDemo.model.alignprivate.Courses;
import org.mehaexample.asdDemo.model.alignprivate.Electives;
import org.mehaexample.asdDemo.model.alignprivate.ExtraExperiences;
import org.mehaexample.asdDemo.model.alignprivate.Projects;
import org.mehaexample.asdDemo.model.alignprivate.StudentLogins;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignprivate.WorkExperiences;
import org.mehaexample.asdDemo.restModels.PasswordChangeObject;
import org.mehaexample.asdDemo.restModels.PasswordCreateObject;
import org.mehaexample.asdDemo.restModels.PasswordResetObject;
import org.mehaexample.asdDemo.utils.MailClient;
import org.mehaexample.asdDemo.utils.StringUtils;
import org.mehaexample.asdDemo.restModels.EmailToRegister;

@Path("student-facing")
public class StudentFacing {
    StudentsDao studentDao = new StudentsDao(true);
    ElectivesDao electivesDao = new ElectivesDao(true);
    CoursesDao coursesDao = new CoursesDao(true);
    WorkExperiencesDao workExperiencesDao = new WorkExperiencesDao(true);
    ExtraExperiencesDao extraExperiencesDao = new ExtraExperiencesDao(true);
    ProjectsDao projectsDao = new ProjectsDao(true);
    StudentLoginsDao studentLoginsDao = new StudentLoginsDao(); 
    String nuIdNotFound = "No Student record exists with given ID";
    String workExperienceRecord = "WorkExperienceRecoed";
    String studentData = "studentRecord";
    String projectRecord = "ProjectRecord";
    String extraExperienceRecord = "extraExperienceRecord";
    
    

    /**
     * uopdate student details.
     * <p>
     * http://localhost:8181/webapi/student-facing/students/{NUID}
     *
     * @param neuId
     * @param student
     * @return
     */
    @PUT
    @Path("/students/{nuId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudentRecord(@PathParam("nuId") String neuId, Students student) {
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        }
        studentDao.updateStudentRecord(student);
        return Response.status(Response.Status.OK).entity("Student record updated successfully").build();
    }
    
    
    @GET
    @Path("/students/{nuId}/courses")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentCourses(@PathParam("nuId") String neuId) {
        ArrayList<String> courses = new ArrayList<>();
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        } else {
            List<Electives> electives;
            electives = electivesDao.getElectivesByNeuId(neuId);
            for (int i = 0; i < electives.size(); i++) {
                Electives elective = electivesDao.getElectiveById(electives.get(i).getElectiveId());
                Courses course = coursesDao.getCourseById(elective.getCourseId());
                courses.add(course.getCourseName());
            }
        }
        return Response.status(Response.Status.OK).entity(courses).build();
    }
    
    
    @GET
    @Path("/students/{nuId}/workexperiences")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentWorkExperience(@PathParam("nuId") String neuId, Students student) {
        List<WorkExperiences> workExperiencesList;
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        } else {
            workExperiencesList = workExperiencesDao.getWorkExperiencesByNeuId(neuId);
        }
        JSONObject jsonObj = new JSONObject(workExperiencesList);
        jsonObj.put(workExperienceRecord, workExperiencesList);
        return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }
    
    
    /**
     * @param neuId
     * @param student
     * @return
     */
    @GET
    @Path("/students/{nuId}/extraexperiences")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentExtraExperience(@PathParam("nuId") String neuId, Students student) {
        List<ExtraExperiences> extraExperiencesList;
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        } else {
            extraExperiencesList = extraExperiencesDao.getExtraExperiencesByNeuId(neuId);
        }
        JSONObject jsonObj = new JSONObject(extraExperiencesList);
        jsonObj.put(extraExperienceRecord, extraExperiencesList);
        return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }
    
    
    /**
     * @param neuId
     * @param extraExperienceId
     * @return
     */
    @DELETE
    @Path("/students/{nuId}/extraexperiences/{Id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteExtraExperience(@PathParam("nuId") String neuId, @PathParam("Id") Integer extraExperienceId) {
        List<ExtraExperiences> extraExperiencesList;
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        } else {
            extraExperiencesList = extraExperiencesDao.getExtraExperiencesByNeuId(neuId);
            for (int i = 0; i < extraExperiencesList.size(); i++) {
                if (extraExperiencesList.get(i).getExtraExperienceId() == extraExperienceId) {
                    extraExperiencesDao.deleteExtraExperienceById(extraExperienceId);
                    return Response.status(Response.Status.OK).entity("Experience deleted successfully").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No Experience record exists with given ID").build();
                }
            }
        }
        return Response.status(Response.Status.OK).entity("Experience deleted successfully").build();
    }
    
    
    @DELETE
    @Path("/students/{nuId}/project/{Id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProject(@PathParam("nuId") String neuId, @PathParam("Id") Integer projectId) {
        List<Projects> projectsList;
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        } else {
            projectsList = projectsDao.getProjectsByNeuId(neuId);
            for (int i = 0; i < projectsList.size(); i++) {
                if (projectsList.get(i).getProjectId() == projectId) {
                    projectsDao.deleteProjectById(projectId);
                    return Response.status(Response.Status.OK).entity("Project deleted successfully").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No project record exists with given ID").build();
                }
            }
        }
        return Response.status(Response.Status.OK).entity("Project deleted successfully").build();
    }
    
    
    /**
     * @param neuId
     * @param extraExperienceId
     * @return
     */
    @PUT
    @Path("/students/{nuId}/extraexperiences/{Id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateExtraExperience(@PathParam("nuId") String neuId, @PathParam("Id") Integer extraExperienceId) {
        List<ExtraExperiences> extraExperiencesList;
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        } else {
            extraExperiencesList = extraExperiencesDao.getExtraExperiencesByNeuId(neuId);
            for (int i = 0; i < extraExperiencesList.size(); i++) {
                if (extraExperiencesList.get(i).getExtraExperienceId() == extraExperienceId) {
                    extraExperiencesDao.updateExtraExperience(extraExperiencesList.get(i));
                    return Response.status(Response.Status.OK).entity("Experience updated successfully :)").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No Experience record exists with given ID").build();
                }
            }
        }
        return Response.status(Response.Status.OK).entity("Experience updated successfully").build();
    }
    
    
    @PUT
    @Path("/students/{nuId}/projects/{Id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProject(@PathParam("nuId") String neuId, @PathParam("Id") Integer projectId) {
        List<Projects> projectsList;
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        } else {
            projectsList = projectsDao.getProjectsByNeuId(neuId);
            for (int i = 0; i < projectsList.size(); i++) {
                if (projectsList.get(i).getProjectId() == projectId) {
                    projectsDao.updateProject(projectsList.get(i));
                    return Response.status(Response.Status.OK).entity("Project updated successfully :)").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No Project record exists with given ID").build();
                }
            }
        }
        return Response.status(Response.Status.OK).entity("Project updated successfully").build();
    }
    @POST
    @Path("/students/{nuId}/extraexperiences")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addExtraExperience(@PathParam("nuId") String neuId, ExtraExperiences extraExperiences) throws ParseException {
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        }
        extraExperiences = new ExtraExperiences();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        extraExperiences.setExtraExperienceId(89);
        extraExperiences.setNeuId(neuId);
        extraExperiences.setCompanyName("zillow");
        extraExperiences.setEndDate(dateFormat.parse("2017-06-01"));
        extraExperiences.setStartDate(dateFormat.parse("2017-12-01"));
        extraExperiences.setTitle("SDE");
        extraExperiences.setDescription("intern");
        extraExperiencesDao.createExtraExperience(extraExperiences);
        return Response.status(Response.Status.OK).entity("Experience added successfully").build();
    }
    @POST
    @Path("/students/{nuId}/projects")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProject(@PathParam("nuId") String neuId, Projects project) throws ParseException {
        if (!studentDao.ifNuidExists(neuId)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        }
        project = new Projects();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        project.setProjectId(89);
        project.setNeuId(neuId);
        project.setProjectName("ASD align project");
        project.setEndDate(dateFormat.parse("2017-06-01"));
        project.setStartDate(dateFormat.parse("2017-12-01"));
        project.setDescription("align website backend team");
        projectsDao.createProject(project);
        return Response.status(Response.Status.OK).entity("Project added successfully").build();
    }
    /**
     * retrive student details by NUID
     * <p>
     * http://localhost:8080/webapi/student-facing/students/{NUID}
     *
     * @param nuid
     * @return a student object
     */
    @GET
    @Path("students/{nuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentProfile(@PathParam("nuid") String nuid) {
        if (!studentDao.ifNuidExists(nuid)) {
            return Response.status(Response.Status.NOT_FOUND).entity(nuIdNotFound).build();
        } else {
            Students studentRecord = studentDao.getStudentRecord(nuid);
            List<WorkExperiences> workExperiencesRecord = workExperiencesDao.getWorkExperiencesByNeuId(nuid);
            List<Projects> projects = projectsDao.getProjectsByNeuId(nuid);
            List<ExtraExperiences> extraExperiences = extraExperiencesDao.getExtraExperiencesByNeuId(nuid);
            JSONObject jsonObj = new JSONObject(studentRecord);
            jsonObj.put(studentData, studentRecord);
            jsonObj.put(workExperienceRecord, workExperiencesRecord);
            jsonObj.put(projectRecord, projects);
            jsonObj.put("experience", extraExperiences);
            return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
        }
    }

	// ====================================================================================================================

	/**
	 * Send registration email to the student only if he/she is present in the align database
	 * 
	 * http://localhost:8080/alignWebsite/webapi/student-facing/registration
	 * test.alignstudent123@gmail.com
	 * @param emailToRegister
	 * @return 200 if Registration link is sent successfully
	 */
	@POST
	@Path("/registration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// Send email to admin’s northeastern ID to reset the password.
	public Response sendRegistrationEmail(EmailToRegister emailToRegister){

		String studentEmail = emailToRegister.getEmail();

		// check if the email string is null or empty
		if (StringUtils.isNullOrEmpty(studentEmail)){
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Email Id can't be null or empty").build();
		}else{

			// check if the student is a valid Align student
			Students student = studentDao.getStudentRecordByEmailId(studentEmail);

			// check if the student record exists in the student database
			if(student == null){
				return Response.status(Response.Status.BAD_REQUEST).
						entity("To Register should be an Align Student!").build();
			}

			// check if the student is already registered
			StudentLogins studentLogin = studentLoginsDao.findStudentLoginsByEmail(studentEmail);

			// check if studenLogin either has  record for given email and "confirmed" is true
			if(studentLogin != null && (studentLogin.isConfirmed() == true)) {

				return Response.status(Response.Status.NOT_ACCEPTABLE).
						entity("Student is Already Registered!" + studentEmail).build();
			}

			StudentLogins studentLoginsNew = new StudentLogins();

			// generate registration key 
			String registrationKey = createRegistrationKey(); 

			// Create TimeStamp for Key Expiration for 15 min
			Timestamp keyExpirationTime = new Timestamp(System.currentTimeMillis()+ 15*60*1000);

			studentLoginsNew.setEmail(studentEmail); 
			studentLoginsNew.setStudentPassword("waitingForCreatePassword");
			studentLoginsNew.setConfirmed(false);
			studentLoginsNew.setRegistrationKey(registrationKey);
			studentLoginsNew.setKeyExpiration(keyExpirationTime);

			boolean success = false;
			if(studentLogin == null){

				StudentLogins studentLoginUpdatedWithoutPassword = studentLoginsDao.createStudentLogin(studentLoginsNew);
				if(studentLoginUpdatedWithoutPassword != null) {
					success = true;
				}
			}else{
				boolean studentLoginUpdatedWithoutPassword = studentLoginsDao.updateStudentLogin(studentLoginsNew);
				if(studentLoginUpdatedWithoutPassword == true) {
					success = true;
				}
			}

			// after record created without password, registration link will be sent

			if(success){
				MailClient.sendRegistrationEmail(studentEmail, registrationKey);

				return Response.status(Response.Status.OK).
						entity("Registration link sent succesfully to " + studentEmail).build();
			}

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
					entity("Something Went Wrong" + studentEmail).build();
		}
	}

	/**
	 * This function creates the password and registers the student
	 * 
	 * @param passwordCreateObject
	 * @return 200 if password changed successfully else return 404
	 */
	@POST
	@Path("/password-create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPassword(PasswordCreateObject passwordCreateObject){
		String email = passwordCreateObject.getEmail();
		String password = passwordCreateObject.getPassword();
		String registrationKey = passwordCreateObject.getRegistrationKey();
		System.out.println(email + password + registrationKey); 

		// before create password, a student login should exist
		StudentLogins studentLoginsExisting = studentLoginsDao.findStudentLoginsByEmail(email);
		if(studentLoginsExisting == null) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Invalid Student details. Student does not exist" ).build();
		}

		String databaseRegistrationKey = studentLoginsExisting.getRegistrationKey();
		Timestamp databaseTimestamp = studentLoginsExisting.getKeyExpiration();

		// check if the entered registration key matches 
		if((databaseRegistrationKey.equals(registrationKey))){

			// if registration key matches, then check if its valid or not
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			
			String successMessage = "Congratulations Password created and Student registered successfully!";
			
			if(studentLoginsExisting.isConfirmed()){
				
				successMessage = "Password Reset successfully";
			}

			// check if the database time is after the current time
			if(databaseTimestamp.after(currentTimestamp)){
				String hashedPassword = StringUtils.createHash(password);
				studentLoginsExisting.setStudentPassword(hashedPassword);
				studentLoginsExisting.setConfirmed(true);
				boolean studentLoginUpdatedWithPassword = studentLoginsDao.updateStudentLogin(studentLoginsExisting);
				if(studentLoginUpdatedWithPassword) {
					
					return Response.status(Response.Status.OK).
							entity(successMessage).build();
				} else {
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
							entity("Database exception thrown" ).build();
				}
			} else {
				return Response.status(Response.Status.OK).
						entity(" Registration key expired!" ).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Invalid registration key" ).build();
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

		// check if the student login exists i.e the request is made by already registered student
		StudentLogins studentLogins = studentLoginsDao.findStudentLoginsByEmail(passwordChangeObject.getEmail());

		if(studentLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("This Email doesn't exist: " + passwordChangeObject.getEmail()).build();
		}

		if(studentLogins.isConfirmed() == false){

			return Response.status(Response.Status.NOT_ACCEPTABLE).
					entity("Please create the password before reseting it! " + passwordChangeObject.getEmail()).build();
		}

		String enteredOldPassword = passwordChangeObject.getOldPassword();
		//		String convertOldPasswordToHash = StringUtils.createHash(enteredOldPassword);

		String enteredNewPassword = passwordChangeObject.getNewPassword();
		//		String convertNewPasswordToHash = StringUtils.createHash(enteredNewPassword);

		if(enteredOldPassword.equals(enteredNewPassword)){

			System.out.println("entered: " + enteredOldPassword);
			System.out.println("database: " + enteredNewPassword);

			return Response.status(Response.Status.NOT_ACCEPTABLE).
					entity("The New Password can't be same as Old passoword ").build();
		}

		String convertOldPasswordToHash = StringUtils.createHash(enteredOldPassword);

		// check if the entered old password is correct
		if(studentLogins.getStudentPassword().equals(convertOldPasswordToHash)){

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
						entity("Email doesn't exist, Please enter a valid Email Id " + studentLogins).build();
			}

			if(studentLogins.isConfirmed() == false){

				return Response.status(Response.Status.NOT_FOUND).
						entity("Password can't be reset....Please create password and register first: ").build();
			}

			// generate registration key 
			String registrationKey = createRegistrationKey(); 

			// Create TimeStamp for Key Expiration for 15 min
			Timestamp keyExpirationTime = new Timestamp(System.currentTimeMillis()+ 15*60*1000);

			StudentLogins studentLoginsNew = new StudentLogins();

			studentLoginsNew.setEmail(studentEmail);
			studentLoginsNew.setStudentPassword(studentLogins.getStudentPassword()); 
			studentLoginsNew.setLoginTime(studentLogins.getLoginTime()); 
			studentLoginsNew.setRegistrationKey(registrationKey);
			studentLoginsNew.setKeyExpiration(keyExpirationTime);
			studentLoginsNew.setConfirmed(true);

			boolean studentLoginUpdated = studentLoginsDao.updateStudentLogin(studentLoginsNew);

			if(studentLoginUpdated) {
				System.out.println("Registration key: " + registrationKey);
				// after generation, send email
				MailClient.sendPasswordResetEmail(studentEmail, registrationKey);

				return Response.status(Response.Status.OK).
						entity("Password Reset link sent succesfully!" ).build(); 
			}

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
					entity("Something Went Wrong" + studentEmail).build();
		}
	}

	private String createRegistrationKey() {

		return UUID.randomUUID().toString();
	}	
}
