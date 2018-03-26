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
  }

  @Test(expected = HibernateException.class)
  public void updateNonExistentData() {
    SingleValueAggregatedData data = new SingleValueAggregatedData();
    data.setAnalyticKey("NonExist");
    data.setAnalyticValue(3);
    dataDao.updateData(data);
  }

  @Test
  public void getAndUpdateData() {
    // Total Students
    SingleValueAggregatedData data = dataDao.findTotalStudentsData();
    data.setAnalyticValue(50);
    dataDao.updateData(data);
    Assert.assertTrue(dataDao.getTotalStudents() == 50);

    // Total Graduated Students
    data = dataDao.findTotalGraduatedStudentsData();
    data.setAnalyticValue(51);
    dataDao.updateData(data);
    Assert.assertTrue(dataDao.getTotalGraduatedStudents() == 51);

    // Total Dropped Out Students
    data = dataDao.findTotalDroppedOutStudentsData();
    data.setAnalyticValue(52);
    dataDao.updateData(data);
    Assert.assertTrue(dataDao.getTotalDroppedOutStudents() == 52);

    // Total Students Got Job
    data = dataDao.findTotalStudentsGotJobData();
    data.setAnalyticValue(53);
    dataDao.updateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsGotJob() == 53);

    // Total Students In Boston
    data = dataDao.findTotalStudentsInBostonData();
    data.setAnalyticValue(54);
    dataDao.updateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsInBoston() == 54);

    // Total Students In Seattle
    data = dataDao.findTotalStudentsInSeattleData();
    data.setAnalyticValue(55);
    dataDao.updateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsInSeattle() == 55);

    // Total Students In Charlotte
    data = dataDao.findTotalStudentsInCharlotteData();
    data.setAnalyticValue(56);
    dataDao.updateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsInCharlotte() == 56);

    // Total Students In Boston
    data = dataDao.findTotalStudentsInSiliconValleyData();
    data.setAnalyticValue(57);
    dataDao.updateData(data);
    Assert.assertTrue(dataDao.getTotalStudentsInSiliconValley() == 57);
  }
}
