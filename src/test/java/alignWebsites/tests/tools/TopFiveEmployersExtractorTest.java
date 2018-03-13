//package org.alignprivate.asd.tools;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TopFiveEmployersExtractorTest {
//  private TopFiveEmployersExtractor topFiveEmployersExtractor;
//  private List<String> topFiveEmployersFromPlaceholder;
//
//  @Before
//  public void init() {
//    topFiveEmployersExtractor = new TopFiveEmployersExtractor();
//    topFiveEmployersFromPlaceholder = new ArrayList<>();
//    topFiveEmployersFromPlaceholder.add("Google");
//    topFiveEmployersFromPlaceholder.add("Amazon");
//    topFiveEmployersFromPlaceholder.add("Facebook");
//    topFiveEmployersFromPlaceholder.add("Microsoft");
//    topFiveEmployersFromPlaceholder.add("Apple");
//  }
//
//  @Test
//  public void extractFromPrivateAndLoadToPublicTest() throws SQLException {
//    Assert.assertTrue(
//            topFiveEmployersExtractor
//                    .extractFromPrivateAndLoadToPublic()
//                    .equals(topFiveEmployersFromPlaceholder));
//  }
//}