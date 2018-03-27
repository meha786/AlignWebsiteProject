//package alignWebsite.studentresourseTest;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.mehaexample.asdDemo.alignWebsite.StudentResource;
//import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
//import org.mehaexample.asdDemo.enums.*;
//import org.mehaexample.asdDemo.model.alignprivate.Students;
//
//import javax.ws.rs.core.Response;
//
//import javax.ws.rs.core.Response;
//
//public class studentFacingTest {
//
//    public static StudentResource studentResource;
//    public static StudentsDao studentsDao;
//
//
////    @Test
////    public void getStudentRecord() {
////        Students newStudent = new Students("060", "unittest@gmail.com", "test", "",
////                "tetsing", Gender.M, "F1", "090909090",
////                "402 Terry Ave", "WA", "Seattle", "98109", Term.FALL, 2015,
////                Term.SPRING, 2017,
////                EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null, true);
////
////        studentsDao.addStudent(newStudent);
////        Students student = studentResource.getStudentProfile("080");
////        Assert.assertTrue(student.toString().equals(newStudent.toString()));
////        Assert.assertTrue(studentdao.searchStudentRecord("Tom").get(0).getNeuId().equals("0000000"));
////        studentdao.deleteStudent("0000000");
////
////    }
//
//    @Test
//    public void getStudentByNuid() {
//        String test = "{\n" +
//                "    \"lastName\": \"Cat\",\n" +
//                "    \"expectedLastYear\": 2017,\n" +
//                "    \"gender\": \"M\",\n" +
//                "    \"city\": \"Boston\",\n" +
//                "    \"student\": \"StudentsPublic{neuId='080', email='test@gmail.comextraexperiences', firstName='Test', middleName='Riddle', lastName='Cat', gender=M, scholarship=false, visa=F1, phoneNum='111111101', address='402 Terry Ave', state='MA', city='Boston', zip='98109', enrollmentStatus=FULL_TIME, campus=SEATTLE, degree=MASTERS, photo=null}\",\n" +
//                "    \"project\": [],\n" +
//                "    \"phoneNum\": \"111111101\",\n" +
//                "    \"experience\": [\n" +
//                "        {\n" +
//                "            \"neuId\": \"080\",\n" +
//                "            \"endDate\": \"2017-10-03\",\n" +
//                "            \"companyName\": \"facebook\",\n" +
//                "            \"description\": \"Develop the backend-service\",\n" +
//                "            \"title\": \"Software Engineer I\",\n" +
//                "            \"extraExperienceId\": 1,\n" +
//                "            \"startDate\": \"2017-07-03\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"neuId\": \"080\",\n" +
//                "            \"endDate\": \"2017-11-03\",\n" +
//                "            \"companyName\": \"Apple\",\n" +
//                "            \"description\": \"Develop the front end\",\n" +
//                "            \"title\": \"Software Engineer II\",\n" +
//                "            \"extraExperienceId\": 2,\n" +
//                "            \"startDate\": \"2017-07-04\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"neuId\": \"080\",\n" +
//                "            \"endDate\": \"2018-09-09\",\n" +
//                "            \"companyName\": \"zillow\",\n" +
//                "            \"description\": \"intern\",\n" +
//                "            \"title\": \"SDE\",\n" +
//                "            \"extraExperienceId\": 9,\n" +
//                "            \"startDate\": \"2018-09-12\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"entryTerm\": \"FALL\",\n" +
//                "    \"scholarship\": false,\n" +
//                "    \"state\": \"MA\",\n" +
//                "    \"email\": \"test@gmail.comextraexperiences\",\n" +
//                "    \"zip\": \"98109\",\n" +
//                "    \"entryYear\": 2015,\n" +
//                "    \"address\": \"402 Terry Ave\",\n" +
//                "    \"visible\": true,\n" +
//                "    \"neuId\": \"080\",\n" +
//                "    \"race\": \"asia\",\n" +
//                "    \"campus\": \"SEATTLE\",\n" +
//                "    \"degree\": \"MASTERS\",\n" +
//                "    \"firstName\": \"Test\",\n" +
//                "    \"enrollmentStatus\": \"FULL_TIME\",\n" +
//                "    \"visa\": \"F1\",\n" +
//                "    \"middleName\": \"Riddle\",\n" +
//                "    \"workexperience\": [\n" +
//                "        {\n" +
//                "            \"currentJob\": false,\n" +
//                "            \"neuId\": \"080\",\n" +
//                "            \"endDate\": \"2017-10-03\",\n" +
//                "            \"companyName\": \"Amazon\",\n" +
//                "            \"description\": \"Develop the backend-service\",\n" +
//                "            \"title\": \"Software Engineer I\",\n" +
//                "            \"startDate\": \"2017-07-03\",\n" +
//                "            \"workExperienceId\": 1\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"expectedLastTerm\": \"SPRING\"\n" +
//                "}";
//        Response testResponse = Response.status(Response.Status.OK).entity(test).build();
//        System.out.println(studentResource.getStudentProfile("080").toString());
//        Assert.assertTrue(testResponse.toString().equals(studentResource.getStudentProfile("080").toString()));
//    }
//
//}
