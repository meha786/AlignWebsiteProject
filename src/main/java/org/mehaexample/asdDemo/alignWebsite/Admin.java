package org.mehaexample.asdDemo.alignWebsite;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
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
import org.mehaexample.asdDemo.model.alignadmin.LoginObject;
import org.mehaexample.asdDemo.model.alignadmin.TopBachelor;
import org.mehaexample.asdDemo.model.alignadmin.TopElective;
import org.mehaexample.asdDemo.model.alignadmin.TopEmployer;
import org.mehaexample.asdDemo.model.alignadmin.ParamsObject;
import org.mehaexample.asdDemo.model.alignprivate.StudentBasicInfo;
import org.mehaexample.asdDemo.model.alignprivate.StudentCoopList;
import org.mehaexample.asdDemo.model.alignprivate.StudentLogins;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignprivate.WorkExperiences;
import org.mehaexample.asdDemo.restModels.PasswordChangeObject;
import org.mehaexample.asdDemo.restModels.PasswordResetObject;
import org.mehaexample.asdDemo.utils.MailClient;
import org.mehaexample.asdDemo.utils.StringUtils;

import com.lambdaworks.crypto.SCryptUtil;


@Path("admin-facing")
public class Admin{

	// DAO methods
	StudentsDao studentDao = new StudentsDao(true);
	ElectivesAdminDao electiveDao = new ElectivesAdminDao(true);
	GenderRatioDao genderRatioDao = new GenderRatioDao(true);
	WorkExperiencesDao workExperiencesDao = new WorkExperiencesDao(true);
	PriorEducationsDao priorEducationsDao = new PriorEducationsDao(true);
	ElectivesDao electivesDao = new ElectivesDao(true);
	AdminLoginsDao adminLoginsDao = new AdminLoginsDao(true);
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
	public Response searchStudent(ParamsObject input){
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		int begin = 1;
		int end = 20;
		if (input.getFirstname()!=null){
			System.out.println("got firstname"+input.getFirstname());
			ArrayList<String> firstnameList = new ArrayList<String>();
			firstnameList.add(input.getFirstname());
			map.put("firstName",firstnameList);
		}
		if (input.getLastname()!=null){
			ArrayList<String> lastnameList = new ArrayList<String>();
			lastnameList.add(input.getLastname());
			map.put("lastName",lastnameList);
		}
		if (input.getEmail()!=null){
			ArrayList<String> emailList = new ArrayList<String>();
			emailList.add(input.getEmail());
			map.put("email",emailList);
		}
		if (input.getDegreeyear()!=null){
			ArrayList<String> degreeyearList = new ArrayList<String>();
			degreeyearList.add(input.getDegreeyear());
			map.put("expectedLastYear",degreeyearList);
		}
		if (input.getEnrollmentstatus()!=null){
			ArrayList<String> enrollmentstatusList = new ArrayList<String>();
			enrollmentstatusList.add(input.getEnrollmentstatus());
			map.put("enrollmentStatus",enrollmentstatusList);
		}
		if (input.getCampus()!=null){
			ArrayList<String> campusList = new ArrayList<String>();
			campusList.add(input.getCampus());
			map.put("campus",campusList);
		}
		if (input.getCompany()!=null){
			ArrayList<String> companyList = new ArrayList<String>();
			companyList.add(input.getCompany());
			map.put("companyName",companyList);
		}
		try{
			if (input.getBeginindex()!=null){
				begin = Integer.valueOf(input.getBeginindex());
			}
			if (input.getEndindex()!=null){
				end = Integer.valueOf(input.getEndindex());
			}
		}
		catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("please specify begin and end index.").build();
		}
		ArrayList<Students> studentRecords = (ArrayList<Students>) studentDao.getAdminFilteredStudents(map, begin, end);
		return Response.status(Response.Status.OK).entity(studentRecords).build();
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
	public Response getGenderRatio(ParamsObject input) throws SQLException{

		List<GenderRatio> ratio = new ArrayList<GenderRatio>();
		if (input.getCampus()!=null){
			try{
				ratio = genderRatioDao.getYearlyGenderRatio(Campus.valueOf(input.getCampus().toUpperCase()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist").build();
			}
		} else {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus field cannot be null").build();
		}
		return Response.status(Response.Status.OK).entity(ratio).build();
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
	public Response getTopBachelorDegree(ParamsObject input) throws SQLException{
		List<TopBachelor> degrees = new ArrayList<TopBachelor>();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				degrees = priorEducationsDao.getTopTenBachelors(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist or year should be integer.").build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				degrees = priorEducationsDao.getTopTenBachelors(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist.").build();
			}
		} else if (input.getCampus()==null && input.getYear()!=null){
			try{
				degrees = priorEducationsDao.getTopTenBachelors(null,Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist or year should be integer.").build();
			}
		} else if (input.getCampus()==null && input.getYear()==null){
			degrees = priorEducationsDao.getTopTenBachelors(null,null);
		}
		return Response.status(Response.Status.OK).entity(degrees).build();
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
	public Response getTopEmployers(ParamsObject input) throws SQLException{
		List<TopEmployer> employers = new ArrayList<TopEmployer>();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				employers = workExperiencesDao.getTopTenEmployers(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist or year should be integer.").build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				employers = workExperiencesDao.getTopTenEmployers(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist.").build();
			}
		}else if (input.getCampus()==null && input.getYear()!=null){
			try{
				employers = workExperiencesDao.getTopTenEmployers(null,Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist.").build();
			}
		} else if (input.getCampus()==null && input.getYear()==null){
			employers = workExperiencesDao.getTopTenEmployers(null,null);
		} 
		return Response.status(Response.Status.OK).entity(employers).build();
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
	public Response getTopElectives(ParamsObject input) throws SQLException{
		List<TopElective> electives = new ArrayList<TopElective>();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				electives = electivesDao.getTopTenElectives(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist or year should be integer.").build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				electives = electivesDao.getTopTenElectives(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist.").build();
			}
		} else if (input.getCampus()==null && input.getYear()!=null){
			try{
				electives = electivesDao.getTopTenElectives(null,Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("year should be integer.").build();
			}
		} else if (input.getCampus()==null && input.getYear()==null){
			electives = electivesDao.getTopTenElectives(null,null);
		}
		return Response.status(Response.Status.OK).entity(electives).build();
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
	public Response getCoopStudents(ParamsObject input) throws SQLException{
		List<StudentCoopList> coopStudentsList = new ArrayList<StudentCoopList>();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				coopStudentsList = workExperiencesDao.getStudentCoopCompanies(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist or year should be integer.").build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				coopStudentsList = workExperiencesDao.getStudentCoopCompanies(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist.").build();
			}
		} else if (input.getCampus()==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Campus cannot be null.").build();
		}
		return Response.status(Response.Status.OK).entity(coopStudentsList).build();
	}

	/**
	 * This is a function for retrieving the students working in a given company
	 * 
	 * http://localhost:8080/alignWebsite/webapi/admin-facing/analytics/company
	 * @param params
	 * @return
	 */
	@POST
	@Path("/analytics/company")
	public Response getStudentsWorkingForACompany(ParamsObject input){
		List<StudentBasicInfo> studentsList = new ArrayList<StudentBasicInfo>();
		if (input.getCampus()!=null && input.getCompany()!=null && input.getYear()!=null){
			try{
				studentsList = workExperiencesDao.getStudentsWorkingInACompany(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()),input.getCompany());
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist or year should be integer.").build();
			}
		} else if (input.getCampus()!=null && input.getCompany()!=null && input.getYear()==null){
			try{
				studentsList = workExperiencesDao.getStudentsWorkingInACompany(Campus.valueOf(input.getCampus().toUpperCase()),null,input.getCompany());
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist.").build();
			}
		} else if (input.getCampus()==null || input.getCompany()==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Campus and Company cannot be null.").build();
		}

		return Response.status(Response.Status.OK).
				entity(studentsList).build();  
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
	public Response getStudentWorkingFullTime(ParamsObject input){
		List<StudentCoopList> studentsList = new ArrayList<StudentCoopList>();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				studentsList = workExperiencesDao.
						getStudentCurrentCompanies(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist or year should be integer.").build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				studentsList = workExperiencesDao.
						getStudentCurrentCompanies(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist.").build();
			}
		} else if (input.getCampus()==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Campus cannot be null.").build();
		}
		return Response.status(Response.Status.OK).
				entity(studentsList).build();  
	}
	
	/**
	 * This is a function to change an existing admin's password
	 * 
	 * http://localhost:8080/alignWebsite/webapi/admin-facing/login
	 * @param passwordChangeObject
	 * @return 200 + token if logged in successfully else return 404
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUser(@Context HttpServletRequest request,LoginObject loginInput){
		AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(loginInput.getUsername());
		if(adminLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("User doesn't exist: " + loginInput.getUsername()).build();
		}
		        
        boolean matched = false;
        try{
        	String reqPass = loginInput.getPassword();
    		String saltStr = loginInput.getUsername().substring(0, loginInput.getUsername().length()/2);
    		String originalPassword = reqPass+saltStr;
        	matched = SCryptUtil.check(originalPassword,adminLogins.getAdminPassword());
        } catch (Exception e){
        	return Response.status(Response.Status.UNAUTHORIZED).
					entity("Incorrect Password").build();
        }

		if(matched){
			try {
				JSONObject jsonObj = new JSONObject();
				Timestamp keyGeneration = new Timestamp(System.currentTimeMillis());
				Timestamp keyExpiration = new Timestamp(System.currentTimeMillis()+15*60*1000);
				adminLogins.setLoginTime(keyGeneration);
				adminLogins.setKeyExpiration(keyExpiration);
				adminLoginsDao.updateAdminLogin(adminLogins);
				String ip = request.getRemoteAddr();
				JsonWebEncryption senderJwe = new JsonWebEncryption();
				senderJwe.setPlaintext(adminLogins.getEmail()+"*#*"+ip+"*#*"+keyGeneration.toString());
				senderJwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.DIRECT);
				senderJwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
				
				String secretKey = ip+"sEcR3t_nsA-K3y";
				byte[] key = secretKey.getBytes();
				key = Arrays.copyOf(key, 32);
				AesKey keyMain = new AesKey(key);
				senderJwe.setKey(keyMain);
				String compactSerialization = senderJwe.getCompactSerialization();
				jsonObj.put("token", compactSerialization);
				
				return Response.status(Response.Status.OK).
						entity(jsonObj.toString()).build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
						entity("Internal Server Error").build();
			}
		}else{
			return Response.status(Response.Status.UNAUTHORIZED).
					entity("Incorrect Password").build();
		}
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

		// check if the admin login exists already or not

		AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(passwordChangeObject.getEmail());

		if(adminLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("Email doesn't exist: Invalid Email" + passwordChangeObject.getEmail()).build();
		}

		if(adminLogins.isConfirmed() == false){

			return Response.status(Response.Status.NOT_ACCEPTABLE).
					entity("Please create the password before reseting it! " + passwordChangeObject.getEmail()).build();
		}

		String enteredOldPassword = passwordChangeObject.getOldPassword();

		String enteredNewPassword = passwordChangeObject.getNewPassword();

		if(enteredOldPassword.equals(enteredNewPassword)){

			System.out.println("entered: " + enteredOldPassword);
			System.out.println("database: " + enteredNewPassword);

			return Response.status(Response.Status.NOT_ACCEPTABLE).
					entity("The New Password can't be same as Old passoword ").build();
		}

		String convertOldPasswordToHash = StringUtils.createHash(enteredOldPassword);

		// check if the entered old password is correct
		if(adminLogins.getAdminPassword().equals(convertOldPasswordToHash)){

			String hashNewPassword = StringUtils.createHash(passwordChangeObject.getNewPassword());

			adminLogins.setAdminPassword(hashNewPassword);
			adminLoginsDao.updateAdminLogin(adminLogins);

			return Response.status(Response.Status.OK).
					entity("Password Changed Succesfully!" ).build();
		}else{
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Incorrect Password: ").build();
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
	public Response sendEmailForPasswordResetAdmin(PasswordResetObject passwordResetObject){

		String adminEmail = passwordResetObject.getEmail();

		if (adminEmail == null){

			return Response.status(Response.Status.BAD_REQUEST).
					entity("Email Id can't be null").build();
		}else{

			// Check if the admin is registered already
			AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(adminEmail);

			if(adminLogins == null){

				return Response.status(Response.Status.NOT_FOUND).
						entity("Email doesn't exist: " + adminEmail).build();
			}

			if(adminLogins.isConfirmed() == false){

				return Response.status(Response.Status.NOT_FOUND).
						entity("Password can't be reset....Please create password and register: " + adminEmail).build();
			}

			// generate registration key 
			String registrationKey = createRegistrationKey(); 

			// Create TimeStamp for Key Expiration for 15 min
			Timestamp keyExpirationTime = new Timestamp(System.currentTimeMillis()+ 15*60*1000);

			AdminLogins adminLoginsNew = new AdminLogins(); 

			adminLoginsNew.setEmail(adminEmail);
			adminLoginsNew.setAdminPassword(adminLogins.getAdminPassword()); 
			adminLoginsNew.setLoginTime(adminLogins.getLoginTime()); 
			adminLoginsNew.setRegistrationKey(registrationKey);
			adminLoginsNew.setKeyExpiration(keyExpirationTime);
			adminLoginsNew.setConfirmed(true);

			boolean adminLoginUpdated = adminLoginsDao.updateAdminLogin(adminLoginsNew);

			if(adminLoginUpdated) {

				// after generation, send email
				MailClient.sendPasswordResetEmail(adminEmail, registrationKey);

				return Response.status(Response.Status.OK).
						entity("Password Reset link sent succesfully!" ).build(); 
			}

			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
					entity("Something Went Wrong" + adminEmail).build();
		}
	}

	private String createRegistrationKey() {

		return UUID.randomUUID().toString();
	}	
}
