package alignWebsite.alignpublic;

import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignpublic.SingleValueAggregatedDataDao;
import org.mehaexample.asdDemo.model.alignpublic.SingleValueAggregatedData;

public class SingleValueAggregatedDataDaoTest {
  private static SingleValueAggregatedDataDao dataDao;

  @BeforeClass
  public static void init() {
    dataDao = new SingleValueAggregatedDataDao(true);

//    dataDao = new SingleValueAggregatedDataDao();
  }

  @Test(expected = HibernateException.class)
  public void updateNonExistentData() {
    SingleValueAggregatedData data = new SingleValueAggregatedData();
    data.setAnalyticKey("NonExist");
    data.setAnalyticValue(3);
    dataDao.saveOrUpdateData(data);
  }

  @Test
  public void getAndUpdateData() {
    // Total Students
    SingleValueAggregatedData data = dataDao.findTotalStudentsData();
    data.setAnalyticValue(50);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalStudents() == 50);

    // Total Current Students
    data = dataDao.findTotalCurrentStudentsData();
    data.setAnalyticValue(20);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalCurrentStudents() == 20);

    // Total Students With Scholarship
    data = dataDao.findTotalStudentsWithScholarshipData();
    data.setAnalyticValue(60);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsWithScholarship() == 60);

    // Total Male Students
    data = dataDao.findTotalMaleStudentsData();
    data.setAnalyticValue(24);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalMaleStudents() == 24);

    // Total Students
    data = dataDao.findTotalFemaleStudentsData();
    data.setAnalyticValue(26);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalFemaleStudents() == 26);

    // Total Full Time Students
    data = dataDao.findTotalFullTimeStudentsData();
    data.setAnalyticValue(27);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalFullTimeStudents() == 27);

    // Total Part Time Students
    data = dataDao.findTotalPartTimeStudentsData();
    data.setAnalyticValue(28);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalPartTimeStudents() == 28);

    // Total Graduated Students
    data = dataDao.findTotalGraduatedStudentsData();
    data.setAnalyticValue(51);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalGraduatedStudents() == 51);

    // Total Dropped Out Students
    data = dataDao.findTotalDroppedOutStudentsData();
    data.setAnalyticValue(52);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalDroppedOutStudents() == 52);

    // Total Students Got Job
    data = dataDao.findTotalStudentsGotJobData();
    data.setAnalyticValue(53);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsGotJob() == 53);

    // Total Students In Boston
    data = dataDao.findTotalStudentsInBostonData();
    data.setAnalyticValue(54);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsInBoston() == 54);

    // Total Students In Seattle
    data = dataDao.findTotalStudentsInSeattleData();
    data.setAnalyticValue(55);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsInSeattle() == 55);

    // Total Students In Charlotte
    data = dataDao.findTotalStudentsInCharlotteData();
    data.setAnalyticValue(56);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsInCharlotte() == 56);

    // Total Students In Boston
    data = dataDao.findTotalStudentsInSiliconValleyData();
    data.setAnalyticValue(57);
    dataDao.saveOrUpdateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsInSiliconValley() == 57);
  }
}
