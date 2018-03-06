package alignWebsite;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.EntryTermsDao;
import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.dao.TermsDao;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.enums.Term;
import org.mehaexample.asdDemo.enums.TermType;
import org.mehaexample.asdDemo.model.EntryTerms;
import org.mehaexample.asdDemo.model.Students;
import org.mehaexample.asdDemo.model.Terms;

public class EntryTermsDaoTest {

  private static EntryTermsDao entryTermsDao;
  private static StudentsDao studentsDao;
  private static TermsDao termsDao;

  @BeforeClass
  public static void init() {
    entryTermsDao = new EntryTermsDao();
    studentsDao = new StudentsDao();
    termsDao = new TermsDao();
  }

  @Test
  public void getAllEntryTerms() {
    List<EntryTerms> entryTerms = entryTermsDao.getAllEntryTerms();

    System.out.println(entryTerms.size());
  }

  @Test
  public void addNullEntryTermsTest() {
    EntryTerms EntryTerms = entryTermsDao.addEntryTerm(null);
    Assert.assertNull(EntryTerms);
  }

  @Test
  public void addEntryTermsTest() {
    int tempId = 1221;
    String email = "abc@def.com";

    Students newStudent = new Students(tempId + "", email, "Tom3", "",
            "Cat", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Seattle", "98109",
            EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null);

    studentsDao.addStudent(newStudent);

    Terms newTerm = new Terms(Term.FALL, tempId, TermType.QUARTER);
    Terms term = termsDao.addTerm(newTerm);

    EntryTerms entryTerms = new EntryTerms();
    entryTerms.setStudent(newStudent);
    entryTerms.setTerms(newTerm);

    EntryTerms newEntryTerms = entryTermsDao.addEntryTerm(entryTerms);
    Assert.assertTrue(newEntryTerms.getStudent().getEmail().equals(email));

    entryTermsDao.deleteEntryTerms(newEntryTerms.getEntryTermId());

    termsDao.deleteTerm(term.getTermId());
    studentsDao.deleteStudent(tempId + "");
  }

  @Test
  public void deleteEntryTermsTest() {
    List<EntryTerms> list = entryTermsDao.getAllEntryTerms();

    int tempId = 1221;
    String email = "abc@def.com";

    Students newStudent = new Students(tempId + "", email, "Tom3", "",
            "Cat", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Seattle", "98109",
            EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null);

    studentsDao.addStudent(newStudent);

    Terms newTerm = new Terms(Term.FALL, tempId, TermType.QUARTER);
    Terms term = termsDao.addTerm(newTerm);

    EntryTerms entryTerms = new EntryTerms();
    entryTerms.setStudent(newStudent);
    entryTerms.setTerms(newTerm);

    EntryTerms newEntryTerms = entryTermsDao.addEntryTerm(entryTerms);
    Assert.assertTrue(newEntryTerms.getStudent().getEmail().equals(email));

    entryTermsDao.deleteEntryTerms(newEntryTerms.getEntryTermId());
    List<EntryTerms> listnew = entryTermsDao.getAllEntryTerms();
    Assert.assertEquals(list.size(), listnew.size());


    termsDao.deleteTerm(term.getTermId());
    studentsDao.deleteStudent(tempId + "");
  }

}
