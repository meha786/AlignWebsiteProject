package org.mehaexample.asdDemo.alignWebsite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignprivate.WorkExperiences;

@Path("student-facing")
public class StudentResource {
    StudentsDao studentDao = new StudentsDao();

    /**
     * search studdent by:
     * company name
     * course name
     * start term
     * end term
     * campus gender
     *http://localhost:8181/webapi/student-facing/students
     * @param search
     * @return student object
     */
    @POST
    @Path("/students")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Students> searchStudent(String search){
        search = "{companyName: Amazon}";
        JSONObject jsonObj = new JSONObject(search);
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        String companyName = "";
        String courseName = "";
        String startTerm = "";
        String endTerm = "";
        String campus = "";
        String gender = "";
        if (!jsonObj.isNull("companyName")){
            companyName = jsonObj.get("companyName").toString();
            ArrayList<String> companyNameList = new ArrayList<String>();
            companyNameList.add(companyName);
            map.put("companyName",companyNameList);
        }
        if (!jsonObj.isNull("courseName")){
            courseName = jsonObj.get("courseName").toString();
            ArrayList<String> courseNameList = new ArrayList<String>();
            courseNameList.add(courseName);
            map.put("courseName",courseNameList);
        }
        if (!jsonObj.isNull("startTerm")){
            startTerm = jsonObj.get("email").toString();
            ArrayList<String> startTermList = new ArrayList<String>();
            startTermList.add(startTerm);
            map.put("startTerm",startTermList);
        }
        if (!jsonObj.isNull("endTerm")){
            endTerm = jsonObj.get("endTerm").toString();
            ArrayList<String> endTermList = new ArrayList<String>();
            endTermList.add(endTerm);
            map.put("endTerm",endTermList);
        }
        if (!jsonObj.isNull("campus")){
            campus = jsonObj.get("campus").toString();
            ArrayList<String> campusList = new ArrayList<String>();
            campusList.add(campus);
            map.put("Campus",campusList);
        }
        if (!jsonObj.isNull("gender")){
            gender = jsonObj.get("gender").toString();
            ArrayList<String> genderList = new ArrayList<String>();
            genderList.add(gender);
            map.put("gender",genderList);
        }
        ArrayList<Students> studentRecords = (ArrayList<Students>) studentDao.getStudentFilteredStudents(map);
        System.out.println(studentRecords);
        return studentRecords;
    }

    /**
     * uopdate student details.
     *
     * http://localhost:8181/webapi/student-facing/students/{NUID}
     *
     * @param neuId
     * @param student
     * @return
     */
    @PUT
    @Path("/students/{nuId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateStudentRecord(@PathParam("nuId") String neuId, Students student) {

        student = studentDao.getStudentRecord(neuId);

        if (student == null) {
            return "Student cant be null";
        }

        if (!student.getNeuId().equals(neuId)) {
            return "NeuId Cant be updated";
        }

        studentDao.updateStudentRecord(student);

        return "Student record updated successfully";
    }

    /**
     *
     * retrive student details by NUID
     *
     * http://localhost:8181/webapi/student-facing/students/{NUID}
     *
     * @param nuid
     * @return a student object
     */
    @GET
    @Path("/students/{nuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Students getStudentRecord(@PathParam("nuid") String nuid) {
        Students studentRecord = studentDao.getStudentRecord(nuid);
        return studentRecord;
    }


}
