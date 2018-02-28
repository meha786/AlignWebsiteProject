package org.mehaexample.asdDemo.alignWebsite;
/*
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

import org.mehaexample.asdDemo.dao.ExperienceDao;
import org.mehaexample.asdDemo.dao.PriorEducationDao;
import org.mehaexample.asdDemo.model.Experience;
import org.mehaexample.asdDemo.model.PriorEducation;
import org.mehaexample.asdDemo.model.Student;

@Path("studentExperienceResource")
public class StudentExperienceResource {

	ExperienceDao studentExperienceDao = new ExperienceDao();

	// http://localhost:8080/alignWebsite/webapi/studentExperienceResource/all
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Experience> getAllExperiences() {
		ArrayList<Experience> list = (ArrayList<Experience>) studentExperienceDao.getAllExperiences();
		return list;
	}

	// get the experience list for a given nuid
	// Example request: http://localhost:8080/alignWebsite/webapi/studentExperienceResource/1234
	@GET
	@Path("{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Experience> getExperience(@PathParam("nuid") String nuid) {
		System.out.println("Getting Prior Education Details for student with nuid: " + nuid);
		ArrayList<Experience> list = (ArrayList<Experience>) studentExperienceDao.getExperience(nuid);
		return list;
	}

	// http://localhost:8080/alignWebsite/webapi/studentExperienceResource/1234
	// Body:  {
	//    "description": "desc2",
	//    "nuid": "1234",
	//    "title": "exp title 2"
	//	}
	@POST
	@Path("{nuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void saveExperienceForm(@PathParam("nuid") String nuid, Experience experience){
		System.out.println("saveExperienceForm - nuid =" + nuid);
		studentExperienceDao.addExperience(nuid, experience); 
	} 
	
	@PUT
	@Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public void updateStudentRecord(@PathParam("id") int id , Experience experience) {
		System.out.println("update record nuid=" + id);
		studentExperienceDao.updateStudentRecordDao(id, experience);
    }

	// http://localhost:8080/alignWebsite/webapi/studentExperienceResource/9
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON})
	public void deleteExperienceById(@PathParam("id") int id)
	{      
		System.out.println("experience id to be deleted is: " + id);
		studentExperienceDao.deleteExperienceRecord(id);
	} 
}*/
