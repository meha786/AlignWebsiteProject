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
import org.mehaexample.asdDemo.dao.alignprivate.ExtraExperiencesDao;
import org.mehaexample.asdDemo.dao.alignprivate.PriorEducationsDao;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.dao.alignprivate.WorkExperiencesDao;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.model.alignadmin.AdminLogins;
import org.mehaexample.asdDemo.model.alignadmin.ElectivesAdmin;
import org.mehaexample.asdDemo.model.alignadmin.GenderRatio;
import org.mehaexample.asdDemo.model.alignadmin.LoginObject;
import org.mehaexample.asdDemo.model.alignadmin.StudentBachelorInstitution;
import org.mehaexample.asdDemo.model.alignadmin.TopBachelor;
import org.mehaexample.asdDemo.model.alignadmin.TopElective;
import org.mehaexample.asdDemo.model.alignadmin.TopEmployer;
import org.mehaexample.asdDemo.model.alignadmin.ParamsObject;
import org.mehaexample.asdDemo.model.alignprivate.ExtraExperiences;
import org.mehaexample.asdDemo.model.alignprivate.StudentBasicInfo;
import org.mehaexample.asdDemo.model.alignprivate.StudentCoopList;
import org.mehaexample.asdDemo.model.alignprivate.StudentLogins;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignprivate.WorkExperiences;
import org.mehaexample.asdDemo.restModels.PasswordChangeObject;
import org.mehaexample.asdDemo.restModels.PasswordCreateObject;
import org.mehaexample.asdDemo.restModels.PasswordResetObject;
import org.mehaexample.asdDemo.utils.MailClient;

import com.lambdaworks.crypto.SCryptUtil;


@Path("")
public class Admin{

	//importing DAO methods
	StudentsDao studentDao = new StudentsDao();
	ElectivesAdminDao electiveDao = new ElectivesAdminDao();
	GenderRatioDao genderRatioDao = new GenderRatioDao();
	WorkExperiencesDao workExperiencesDao = new WorkExperiencesDao();
	PriorEducationsDao priorEducationsDao = new PriorEducationsDao();
	ElectivesDao electivesDao = new ElectivesDao();
	AdminLoginsDao adminLoginsDao = new AdminLoginsDao();
	ExtraExperiencesDao extraExperiencesDao = new ExtraExperiencesDao();
	StudentLogins studentLogins = new StudentLogins();

	/**
	 * Request 1
	 * This is the function to search for students
	 *	
	 *	http://localhost:8080/webapi/students
	 * @param firstname
	 * @return the list of student profiles matching the search fields.
	 */
	@POST
	@Path("students")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchStudent(ParamsObject input){
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		ArrayList<Students> studentRecords = new ArrayList<Students>();
		int begin = 1;
		int end = 20;
	try{
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
		if (input.getNeuid()!=null){
			ArrayList<String> neuIdList = new ArrayList<String>();
			neuIdList.add(input.getNeuid());
			map.put("neuId",neuIdList);
		}
		if (input.getUndergradmajor()!=null){
			ArrayList<String> undergradmajor = new ArrayList<String>();
			undergradmajor.add(input.getUndergradmajor());
			map.put("majorName",undergradmajor);
		}
		if (input.getNuundergrad()!=null){
			ArrayList<String> nuundergrad = new ArrayList<String>();
			nuundergrad.add(input.getUndergradmajor());
			map.put("institutionName",nuundergrad);
		}
		if (input.getCoop()!=null){
			ArrayList<String> coop = new ArrayList<String>();
			coop.add(input.getCoop());
			map.put("companyName",coop);
		}
		if (input.getGender()!=null){
			ArrayList<String> gender = new ArrayList<String>();
			gender.add(input.getGender());
			map.put("gender",gender);
		}
		if (input.getRace()!=null){
			ArrayList<String> race = new ArrayList<String>();
			race.add(input.getRace());
			map.put("race",race);
		}
		if (input.getBeginindex()!=null){
			begin = Integer.valueOf(input.getBeginindex());
		}
		if (input.getEndindex()!=null){
			end = Integer.valueOf(input.getEndindex());
		}
		studentRecords = (ArrayList<Students>) studentDao.getAdminFilteredStudents(map, begin, end);
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("please check the request.").build();
		}
		return Response.status(Response.Status.OK).entity(studentRecords).build();
	}

	/**
	 * Request 2
	 * This is the function to retrieve a student details based on nuid.
	 *	
	 *	http://localhost:8080/webapi/students/090
	 * @param nuid
	 * @return the student details matching the nuid.
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
			List<ExtraExperiences> coop = extraExperiencesDao.getExtraExperiencesByNeuId(nuid);
			jsonObj.put("coopexperience", coop);
			return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
		}
	}

	/**
	 * Request 2
	 * This is the function to get the gender ratio counts per year.
	 *	
	 *	http://localhost:8080/webapi/analytics/gender-ratio
	 * @param 
	 * @return the gender ratio is returned as a list of years with counts
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
	 * Request 4
	 * This is the function to get the list of top 10 bachelor degrees.
	 *	
	 *	http://localhost:8080/webapi/analytics/top-bachelor-degrees
	 * @param 
	 * @return the list of top 10 bachelor degrees and number of students
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
	 * Request 5
	 * This is the function to get a list of the top 10 employers.
	 *	
	 *	http://localhost:8080/webapi/analytics/top-employers
	 * @param 
	 * @return the list of top 10 employers and number of students
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
	 * Request 6
	 * This is the function to get a list of the top 10 electives.
	 *	
	 *	http://localhost:8080/webapi/aanalytics/top-electives
	 * @param 
	 * @return the list of top 10 electives and number of students
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
	 * Request 7
	 * This is the function to get the list of students,companies worked for as coop.
	 *	
	 *	http://localhost:8080/webapi/analytics/coop-students
	 * @param 
	 * @return the list student details , companies they worked for as coop
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
	 * Request 8 
	 * This is a function for retrieving the students working in a given company
	 * 
	 * http://localhost:8080/webapi/analytics/company
	 * @param params
	 * @return the list student details working for a company
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
	 * Request 9
	 * This is a function for retrieving the students working as full time
	 * 
	 * http://localhost:8080/webapi/analytics/working
	 * @param params
	 * @return the list student details and company they are working for.
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
	 * Request 10
	 * This is a function for retrieving the undergrad institutions of all the students
	 * 
	 * http://localhost:8080/webapi/analytics/undergrad-institutions
	 * @param params
	 * @return the list of undergrad institution and count
	 */
	@POST
	@Path("/analytics/undergrad-institutions")
	public Response getStudentundergradInstitutuins(ParamsObject input){
		List<StudentBachelorInstitution> instList = new ArrayList<StudentBachelorInstitution>();
		if (input.getCampus()!=null && input.getYear()!=null){
			try{
				instList = priorEducationsDao.
						getListOfBachelorInstitutions(Campus.valueOf(input.getCampus().toUpperCase()),Integer.valueOf(input.getYear()));
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist or year should be integer.").build();
			}
		} else if (input.getCampus()!=null && input.getYear()==null){
			try{
				instList = priorEducationsDao.
						getListOfBachelorInstitutions(Campus.valueOf(input.getCampus().toUpperCase()),null);
			} catch(Exception e){
				return Response.status(Response.Status.NOT_ACCEPTABLE).entity("campus doesn't exist.").build();
			}
		} else if (input.getCampus()==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Campus cannot be null.").build();
		}
		return Response.status(Response.Status.OK).
				entity(instList).build();  
	}
	
	/**
	 * Request 12
	 * This function creates the password for admin when they reset their password
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
		AdminLogins adminLoginsExisting = adminLoginsDao.findAdminLoginsByEmail(email); 
		if(adminLoginsExisting == null) {
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Invalid Admin details. Admin does not exist" ).build();
		}

		String databaseRegistrationKey = adminLoginsExisting.getRegistrationKey();
		Timestamp databaseTimestamp = adminLoginsExisting.getKeyExpiration();

		// check if the entered registration key matches 
		if((databaseRegistrationKey.equals(registrationKey))){

			// if registration key matches, then check if its valid or not
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

			// check if the database time is after the current time
			if(databaseTimestamp.after(currentTimestamp)){
	    		String saltnewStr = email.substring(0, email.length()/2);
	    		String setPassword = password+saltnewStr;
	            String hashedPassword = SCryptUtil.scrypt(setPassword, 16, 16, 16);
				adminLoginsExisting.setAdminPassword(hashedPassword);
				adminLoginsExisting.setConfirmed(true);
				boolean adminLoginUpdatedWithPassword = adminLoginsDao.updateAdminLogin(adminLoginsExisting);
				if(adminLoginUpdatedWithPassword) {
					
					return Response.status(Response.Status.OK).
							entity("Congratulations Password Reset successfully for Admin!").build();
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
	 * Request 13
	 * This is a function to login using admin email and password
	 * 
	 * http://localhost:8080/webapi/login
	 * @param passwordChangeObject
	 * @return the token if logged in successfully
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
				jsonObj.put("id", "nuidOfAdmin");
				
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
	 * Request 14
	 * This is a function to logout
	 * 
	 * http://localhost:8080/webapi/logout
	 * @param 
	 * @return 200 OK
	 */
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logoutUser(@Context HttpServletRequest request,LoginObject loginInput){
		AdminLogins adminLogins = adminLoginsDao.findAdminLoginsByEmail(loginInput.getUsername());
		if(adminLogins == null){
			return Response.status(Response.Status.NOT_FOUND).
					entity("User doesn't exist: " + loginInput.getUsername()).build();
		}
		try{
			Timestamp keyExpiration = new Timestamp(System.currentTimeMillis());
			adminLogins.setKeyExpiration(keyExpiration);
			adminLoginsDao.updateAdminLogin(adminLogins);
		}
		catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
					entity("Internal Server Error").build();	
		}
		return Response.status(Response.Status.OK).
				entity("Logged Out Successfully").build();
	}

	/**
	 * Request 15
	 * This is a function to change an existing admin's password
	 * 
	 * http://localhost:8080/webapi/password-change
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
					entity("Email doesn't exist: " + passwordChangeObject.getEmail()).build();
		}

        boolean matched = false;
        try{
        	String reqPass = passwordChangeObject.getOldPassword();
    		String saltStr = passwordChangeObject.getEmail().substring(0, passwordChangeObject.getEmail().length()/2);
    		String originalPassword = reqPass+saltStr;
        	matched = SCryptUtil.check(originalPassword,adminLogins.getAdminPassword());
        } catch (Exception e){
        	return Response.status(Response.Status.UNAUTHORIZED).
					entity("Incorrect Password").build();
        }
        
		if(matched){
			String newPass = passwordChangeObject.getNewPassword();
    		String saltnewStr = passwordChangeObject.getEmail().substring(0, passwordChangeObject.getEmail().length()/2);
    		String updatePassword = newPass+saltnewStr;
            String generatedSecuredPasswordHash = SCryptUtil.scrypt(updatePassword, 16, 16, 16);
            adminLogins.setAdminPassword(generatedSecuredPasswordHash);
			adminLoginsDao.updateAdminLogin(adminLogins);

			return Response.status(Response.Status.OK).
					entity("Password Changed Succesfully!" ).build();
		}else{
			return Response.status(Response.Status.BAD_REQUEST).
					entity("Incorrect Password: ").build();
		}
	}


	/**
	 * Request 16
	 * This function sends email to admin’s northeastern ID to reset the password.
	 * 
	 * @param adminEmail
	 * @return 200 if password reset successfully else return 404
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
