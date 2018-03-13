//package org.alignprivate.asd.tools;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
//public class TotalFemaleStudentsExtractorTest {
//  private static final int TEST_VALUE_FROM_DB = 5;
//  private TotalFemaleStudentsExtractor totalFemaleStudentsExtractor;
//
//  @Before
//  public void init() {
//    totalFemaleStudentsExtractor = new TotalFemaleStudentsExtractor();
//  }
//
//  @Test
//  public void extractFromPrivateAndLoadToPublicTest() throws SQLException {
//    Assert.assertTrue(totalFemaleStudentsExtractor.extractFromPrivateAndLoadToPublic() == TEST_VALUE_FROM_DB);
//  }
//}