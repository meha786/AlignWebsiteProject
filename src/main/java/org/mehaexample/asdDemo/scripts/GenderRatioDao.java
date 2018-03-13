package org.mehaexample.asdDemo.scripts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.model.GenderRatio;

public class GenderRatioDao {
  // Singleton pattern
  private static GenderRatioDao instance = null;

  /**
   * Default Constructor.
   */
  public GenderRatioDao() {
  }

  /**
   * Singleton Pattern.
   *
   * @return instance of Gender Ratio Dao.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static GenderRatioDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new GenderRatioDao();
    }

    return instance;
  }

  public List<GenderRatio> getYearlyGenderRatioFromPrivateDatabase(Campus campus) throws SQLException {
    String query = "SELECT EntryYear, COUNT(CASE Gender WHEN 'M' THEN 1 ELSE NULL END) AS Male, " +
            "COUNT(CASE Gender WHEN 'F' THEN 1 ELSE NULL END) As Female " +
            "FROM Students " +
            "WHERE Campus = ? " +
            "GROUP BY EntryYear ASC;";
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    ConnectionManager privateConnectionManager = privateDatabaseEtlQuery.getConnectionManager();
    Connection privateConnection;
    PreparedStatement selectStatement;
    ResultSet results;
    List<GenderRatio> listOfGenderRatio = new ArrayList<>();
    privateConnectionManager.connect();
    privateConnection = privateConnectionManager.getConnection();
    selectStatement = privateConnection.prepareStatement(query);
    selectStatement.setString(1, campus.toString());
    results = selectStatement.executeQuery();
    while (results.next()) {
      GenderRatio genderRatio = new GenderRatio();
      genderRatio.setEntryYear(results.getInt("EntryYear"));
      genderRatio.setMale(results.getInt("Male"));
      genderRatio.setFemale(results.getInt("Female"));
      listOfGenderRatio.add(genderRatio);
    }
    privateConnectionManager.closeConnection();
    selectStatement.close();
    results.close();
    return listOfGenderRatio;
  }

  public void insertYearlyGenderRatioToPublicDatabase(Campus campus, List<GenderRatio> inputGenderRatios) throws SQLException {
    String query = "INSERT INTO GenderRatio (EntryYear, Campus, Male, Female) VALUES (?, ?, ?, ?) " +
            "  ON DUPLICATE KEY UPDATE Male = ?, Female = ?;";
    PublicDatabaseEtlQuery publicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    ConnectionManager publicConnectionManager = publicDatabaseEtlQuery.getConnectionManager();
    Connection publicConnection;
    PreparedStatement updateStatement = null;
    int listSize = inputGenderRatios.size();
    publicConnectionManager.connect();
    publicConnection = publicConnectionManager.getConnection();
    for (int index = 0; index < listSize; index++) {
      updateStatement = publicConnection.prepareStatement(query);
      updateStatement.setInt(1, inputGenderRatios.get(index).getEntryYear());
      updateStatement.setString(2, campus.toString());
      updateStatement.setInt(3, inputGenderRatios.get(index).getMale());
      updateStatement.setInt(4, inputGenderRatios.get(index).getFemale());
      updateStatement.setInt(5, inputGenderRatios.get(index).getMale());
      updateStatement.setInt(6, inputGenderRatios.get(index).getFemale());
      updateStatement.executeQuery();
    }
    publicConnectionManager.closeConnection();
    updateStatement.close();
  }

  public List<GenderRatio> getYearlyGenderRatioFromPublicDatabase(Campus campus) throws SQLException {
    String query = "SELECT EntryYear, Male, Female " +
            "FROM GenderRatio " +
            "WHERE Campus = ? " +
            "ORDER BY EntryYear ASC;";
    PublicDatabaseEtlQuery publicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    ConnectionManager publicConnectionManager = publicDatabaseEtlQuery.getConnectionManager();
    Connection publicConnection;
    PreparedStatement selectStatement;
    ResultSet results;
    List<GenderRatio> listOfGenderRatio = new ArrayList<>();
    publicConnectionManager.connect();
    publicConnection = publicConnectionManager.getConnection();
    selectStatement = publicConnection.prepareStatement(query);
    selectStatement.setString(1, campus.toString());
    results = selectStatement.executeQuery();
    while (results.next()) {
      GenderRatio genderRatio = new GenderRatio();
      genderRatio.setEntryYear(results.getInt("EntryYear"));
      genderRatio.setMale(results.getInt("Male"));
      genderRatio.setFemale(results.getInt("Female"));
      listOfGenderRatio.add(genderRatio);
    }
    publicConnectionManager.closeConnection();
    selectStatement.close();
    results.close();
    return listOfGenderRatio;
  }
}
