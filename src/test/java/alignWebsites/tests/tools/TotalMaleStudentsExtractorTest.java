//package org.alignprivate.asd.tools;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
//public class TotalMaleStudentsExtractorTest {
//  private static final int TEST_VALUE_FROM_DB = 5;
//  private TotalMaleStudentsExtractor totalMaleStudentsExtractor;
//
//  @Before
//  public void init() {
//    totalMaleStudentsExtractor = new TotalMaleStudentsExtractor();
//  }
//
//  @Test
//  public void extractFromPrivateAndLoadToPublicTest() throws SQLException {
//    Assert.assertTrue(totalMaleStudentsExtractor.extractFromPrivateAndLoadToPublic() == TEST_VALUE_FROM_DB);
//  }
//}