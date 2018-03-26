package alignWebsite.alignpublic;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignpublic.MultipleValueAggregatedDataDao;
import org.mehaexample.asdDemo.model.alignpublic.MultipleValueAggregatedData;

import java.util.ArrayList;
import java.util.List;

public class MultipleValueAggregatedDataDaoTest {
  private static MultipleValueAggregatedDataDao dataDao;

  @BeforeClass
  public static void init() {
    dataDao = new MultipleValueAggregatedDataDao(true);
  }

  @Test
  public void updateGetDeleteEmployersTest() {
    // add data
    MultipleValueAggregatedData google = new MultipleValueAggregatedData();
    google.setAnalyticTerm(MultipleValueAggregatedDataDao.LIST_OF_EMPLOYERS);
    google.setAnalyticKey("Google");
    google.setAnalyticValue(20);

    MultipleValueAggregatedData microsoft = new MultipleValueAggregatedData();
    microsoft.setAnalyticTerm(MultipleValueAggregatedDataDao.LIST_OF_EMPLOYERS);
    microsoft.setAnalyticKey("Microsoft");
    microsoft.setAnalyticValue(21);

    // update in database
    List<MultipleValueAggregatedData> listOfEmployers = new ArrayList<>();
    listOfEmployers.add(google);
    listOfEmployers.add(microsoft);
    dataDao.saveOrUpdateList(listOfEmployers);

    // query the database
    List<String> result = dataDao.getTopFiveListOfEmployers();
    Assert.assertTrue(result.size() == 2);
    Assert.assertTrue(result.get(0).equals("Microsoft"));
    Assert.assertTrue(result.get(1).equals("Google"));

    // clear the database
    dataDao.deleteListOfEmployers();
    Assert.assertTrue(dataDao.getTopFiveListOfEmployers().isEmpty());
  }

  @Test
  public void updateGetDeleteBachelorDegreesTest() {
    // add data
    MultipleValueAggregatedData accounting = new MultipleValueAggregatedData();
    accounting.setAnalyticTerm(MultipleValueAggregatedDataDao.LIST_OF_BACHELOR_DEGREES);
    accounting.setAnalyticKey("Accounting");
    accounting.setAnalyticValue(20);

    MultipleValueAggregatedData english = new MultipleValueAggregatedData();
    english.setAnalyticTerm(MultipleValueAggregatedDataDao.LIST_OF_BACHELOR_DEGREES);
    english.setAnalyticKey("English");
    english.setAnalyticValue(21);

    // update in database
    List<MultipleValueAggregatedData> listOfBachelorDegrees = new ArrayList<>();
    listOfBachelorDegrees.add(accounting);
    listOfBachelorDegrees.add(english);
    dataDao.saveOrUpdateList(listOfBachelorDegrees);

    // query the database
    List<String> result = dataDao.getTopFiveListOfBachelorDegrees();
    Assert.assertTrue(result.size() == 2);
    Assert.assertTrue(result.get(0).equals("English"));
    Assert.assertTrue(result.get(1).equals("Accounting"));

    // clear the database
    dataDao.deleteListOfBachelorDegrees();
    Assert.assertTrue(dataDao.getTopFiveListOfBachelorDegrees().isEmpty());
  }
}
