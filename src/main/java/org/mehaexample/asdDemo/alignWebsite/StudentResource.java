package org.mehaexample.asdDemo.alignWebsite;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

import org.json.JSONArray;
import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignprivate.*;
import org.mehaexample.asdDemo.model.alignprivate.*;

@Path("student-facing")
public class StudentResource {
    StudentsDao studentDao = new StudentsDao(true);
    ElectivesDao electivesDao = new ElectivesDao(true);
    CoursesDao coursesDao = new CoursesDao(true);
    WorkExperiencesDao workExperiencesDao = new WorkExperiencesDao(true);
    ExtraExperiencesDao extraExperiencesDao = new ExtraExperiencesDao(true);
    ProjectsDao projectsDao = new ProjectsDao(true);
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
        JSONArray resultArray = new JSONArray();
        for (String course : courses) {
            resultArray.put(course);
        }
        return Response.status(Response.Status.OK).entity(resultArray.toString()).build();
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
//        extraExperiences = new ExtraExperiences();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        extraExperiences.setExtraExperienceId(89);
//        extraExperiences.setNeuId(neuId);
//        extraExperiences.setCompanyName("zillow");
//        extraExperiences.setEndDate(dateFormat.parse("2017-06-01"));
//        extraExperiences.setStartDate(dateFormat.parse("2017-12-01"));
//        extraExperiences.setTitle("SDE");
//        extraExperiences.setDescription("intern");

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

//        project = new Projects();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        project.setProjectId(89);
//        project.setNeuId(neuId);
//        project.setProjectName("ASD align project");
//        project.setEndDate(dateFormat.parse("2017-06-01"));
//        project.setStartDate(dateFormat.parse("2017-12-01"));
//        project.setDescription("align website backend team");

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
}
