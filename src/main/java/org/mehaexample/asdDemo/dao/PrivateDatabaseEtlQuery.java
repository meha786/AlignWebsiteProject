package org.mehaexample.asdDemo.dao;

import org.mehaexample.asdDemo.dao.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrivateDatabaseEtlQuery {
  private ConnectionManager privateConnectionManager;

  /**
   * Default Constructors.
   *
   * @throws SQLException when connection to database has something wrong.
   */
  public PrivateDatabaseEtlQuery() throws SQLException {
    privateConnectionManager = new ConnectionManager(
            "root",
            "Turkey#789",
            "localhost",
            3306,
            "AlignPrivate");
  }

  /**
   * Get a data for a single value query from the Private DB
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL);
   *                    require a '?' place to be replaced
   *                    by the columnLabel.
   * @param columnLabel Column Label of the Data
   *                    that wants to be retrieved; not null;
   *                    required column has been populated
   *                    in the DB
   * @return Data returned from the Database;
   *         By default the value should be -1 from the DB
   *         (if no data has been put there).
   * @throws SQLException when connection to the DB has a problem.
   */
  public int getSingleValueQuery(String queryString, String columnLabel) throws SQLException {
    Connection privateConnection;
    PreparedStatement selectStatement;
    ResultSet results = null;
    int totalStudents;
    privateConnectionManager.connect();
    privateConnection = privateConnectionManager.getConnection();
    selectStatement = privateConnection.prepareStatement(queryString);
    results = selectStatement.executeQuery();
    // always assume the data has been placed
    results.next();
    totalStudents = results.getInt(columnLabel);
    privateConnectionManager.closeConnection();
    selectStatement.close();
    results.close();
    return totalStudents;
  }

  /**
   * Get list of data for a multiple value query from the Private DB
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL);
   *                    require a '?' place to be replaced
   *                    by the columnLabel.
   * @param columnLabel Column Label of the Data
   *                    that wants to be retrieved; not null;
   *                    required column has been populated
   *                    in the DB.
   * @return List Of Data from the Public DB;
   *         By Default, every column should have a string
   *         of "NULL" (if no data has been put in the table).
   * @throws SQLException when connection to the DB has a problem.
   */
  public List<String> getMultipleValueQuery(
          String queryString,
          String columnLabel) throws SQLException {
    List<String> listOfData = new ArrayList<>();
    Connection privateConnection;
    PreparedStatement selectStatement;
    ResultSet results;
    privateConnectionManager.connect();
    privateConnection = privateConnectionManager.getConnection();
    selectStatement = privateConnection.prepareStatement(queryString);
    results = selectStatement.executeQuery();
    while (results.next()) {
      String data = results.getString(columnLabel);
      listOfData.add(data);
    }
    privateConnectionManager.closeConnection();
    selectStatement.close();
    results.close();
    return listOfData;
  }
}
