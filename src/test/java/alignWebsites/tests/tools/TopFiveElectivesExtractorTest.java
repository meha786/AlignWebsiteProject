//package alignWebsites.tests.tools;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mehaexample.asdDemo.tools.TopFiveElectivesExtractor;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TopFiveElectivesExtractorTest {
//  private TopFiveElectivesExtractor topFiveElectivesExtractor;
//
//  private List<String> topFiveElectivesFromPlaceholder;
//
//  @Before
//  public void init() {
//    topFiveElectivesExtractor = new TopFiveElectivesExtractor();
//    topFiveElectivesFromPlaceholder = new ArrayList<>();
//    topFiveElectivesFromPlaceholder.add("Database Management Systems");
//    topFiveElectivesFromPlaceholder.add("Machine Learning");
//    topFiveElectivesFromPlaceholder.add("Web Development");
//    topFiveElectivesFromPlaceholder.add("NULL");
//    topFiveElectivesFromPlaceholder.add("NULL");
//  }
//
//  @Test
//  public void extractFromPrivateAndLoadToPublicTest() throws SQLException {
//    Assert.assertTrue(
//            topFiveElectivesExtractor
//                    .extractFromPrivateAndLoadToPublic()
//                    .equals(topFiveElectivesFromPlaceholder));
//  }
//}