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
//public class TopTenBachelorsDegreeCandidacyExtractorTest {
//  private TopTenBachelorsDegreeExtractor topTenBachelorsDegreeExtractor;
//  private List<String> topTenBachelorsDegreeFromPlaceholder;
//
//  @Before
//  public void init() {
//    topTenBachelorsDegreeExtractor = new TopTenBachelorsDegreeExtractor();
//    topTenBachelorsDegreeFromPlaceholder = new ArrayList<>();
//    topTenBachelorsDegreeFromPlaceholder.add("Computer Science");
//    topTenBachelorsDegreeFromPlaceholder.add("Accounting");
//    topTenBachelorsDegreeFromPlaceholder.add("English");
//    topTenBachelorsDegreeFromPlaceholder.add("NULL");
//    topTenBachelorsDegreeFromPlaceholder.add("NULL");
//    topTenBachelorsDegreeFromPlaceholder.add("NULL");
//    topTenBachelorsDegreeFromPlaceholder.add("NULL");
//    topTenBachelorsDegreeFromPlaceholder.add("NULL");
//    topTenBachelorsDegreeFromPlaceholder.add("NULL");
//    topTenBachelorsDegreeFromPlaceholder.add("NULL");
//  }
//
//  @Test
//  public void extractFromPrivateAndLoadToPublicTest() throws SQLException {
//    Assert.assertTrue(topTenBachelorsDegreeExtractor
//            .extractFromPrivateAndLoadToPublic()
//            .equals(topTenBachelorsDegreeFromPlaceholder));
//  }
//}