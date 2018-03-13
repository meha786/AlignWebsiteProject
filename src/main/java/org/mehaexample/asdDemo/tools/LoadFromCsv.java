//package org.alignprivate.asd.tools;
//
//import java.io.IOException;
//
//import com.csvreader.CsvReader;
//
//import org.alignprivate.asd.dal.maintable.StudentsDao;
//import org.alignprivate.asd.enumeration.Campus;
//import org.alignprivate.asd.enumeration.DegreeCandidacy;
//import org.alignprivate.asd.enumeration.EnrollmentStatus;
//import org.alignprivate.asd.enumeration.Gender;
//import org.alignprivate.asd.model.Students;
//
//public class LoadFromCsv {
//    private StudentsDao studentDao;
//
//    public LoadFromCsv() {
//        studentDao = new StudentsDao();
//    }
//
//    public void loadDatabase(String filePath) {
//
//        try {
//            CsvReader csvReader = new CsvReader(filePath);
//            csvReader.readHeaders();
//
//            while (csvReader.readRecord()){
//                String neuId = csvReader.get("Student_NUID").trim();
//                String email = csvReader.get("Email").trim();
//                String firstName = csvReader.get("First_Name").trim();
//                String middleName = csvReader.get("Middle_Name").trim();
//                String lastName = csvReader.get("Last_Name").trim();
//                Gender gender = Gender.valueOf(csvReader.get("Gender").trim());
//                String visa = csvReader.get("Visa_Type").trim();
//                String phone = csvReader.get("Local_Phone_Number").trim();
//                String address = csvReader.get("Local_Street").trim();
//                String state = csvReader.get("Local_State").trim();
//                String city = csvReader.get("Local_City").trim();
//                String zip = csvReader.get("Local_Zip_Code").trim();
//                EnrollmentStatus status = EnrollmentStatus.FULL_TIME;
//                Campus campus = Campus.BOSTON;
//                DegreeCandidacy degree = DegreeCandidacy.MASTERS;
//
//                String studentAttribute = csvReader.get("Student_Attribute_Concat");
//                if (studentAttribute.contains("SEA")) {
//                    campus = Campus.SEATTLE;
//                } else if (studentAttribute.contains("SAJ")) {
//                    campus = Campus.SILICON_VALLEY;
//                }
//
//                Students student = new Students(neuId, email, firstName, middleName, lastName, gender,
//                        visa, phone, address, state, city, zip, status, campus, degree, null);
//                if (studentDao.ifNuidExists(neuId)) {
//                    studentDao.updateStudentRecord(student);
//                } else {
//                    studentDao.addStudent(student);
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
