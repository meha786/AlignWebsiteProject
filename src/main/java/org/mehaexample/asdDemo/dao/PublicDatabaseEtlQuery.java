package org.mehaexample.asdDemo.dao;

import org.mehaexample.asdDemo.Constants;
import org.mehaexample.asdDemo.dao.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublicDatabaseEtlQuery {
  private static final String PUBLIC_SINGLE_VALUE_QUERY = "DataValue";

  private ConnectionManager publicConnectionManager;

  /**
   * Default Constructors.
   *
   * @throws SQLException when connection to the DB has a problem.
   */
  public PublicDatabaseEtlQuery() throws SQLException {
    publicConnectionManager = new ConnectionManager(
            "root",
            "Turkey#786",
            "localhost",
            3307,
            "AlignPublic");
  }

  /**
   * Update the Public DB that only has a single value
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL); require to provide
   *                    a '?' place to be replaced by the updated data.
   * @param updatedData the data that will replace the one in public DB.
   * @throws SQLException when connection to the DB has a problem.
   */
  public void updateSingleValueQuery(String queryString, int updatedData) throws SQLException {
    Connection publicConnection;
    PreparedStatement updateStatement;
    publicConnectionManager.connect();
    publicConnection = publicConnectionManager.getConnection();
    updateStatement = publicConnection.prepareStatement(queryString);
    updateStatement.setInt(1, updatedData);
    updateStatement.executeUpdate();
    publicConnectionManager.closeConnection();
    updateStatement.close();
  }

  /**
   * Get a data for a single value query from the Public DB
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL);
   *                    require a '?' place to be replaced
   *                    by the "DataValue" column label from
   *                    the DB.
   * @return Data returned from the Database.
   * @throws SQLException when connection to the DB has a problem.
   */
  public int getSingleValueQuery(String queryString) throws SQLException {
    Connection publicConnection;
    PreparedStatement selectStatement;
    ResultSet results;
    int dataValue;
    publicConnectionManager.connect();
    publicConnection = publicConnectionManager.getConnection();
    selectStatement = publicConnection.prepareStatement(queryString);
    results = selectStatement.executeQuery();
    // assume that the DB always has value populated
    results.next();
    dataValue = results.getInt(PUBLIC_SINGLE_VALUE_QUERY);
    publicConnectionManager.closeConnection();
    selectStatement.close();
    results.close();
    return dataValue;
  }

  /**
   * Update the Public DB that has a multiple values (rows in the table)
   * using the query string provided from the parameter.
   * if the total Value does not match with the total data expected
   * in the expected table, it will not update anything.
   *
   * @param queryString Query String (MySQL); require to provide
   *                    a '?' place to be replaced by the updated data.
   * @param updatedList list of data that will replace
   *                    the datas in public DB; require not null.
   * @param totalValue  size of the list of data (updatedList); require
   *                    bigger than 0.
   * @throws SQLException when connection to the DB has a problem.
   */
  public void updateMultipleValueQuery(
          String queryString,
          List<String> updatedList,
          int totalValue) throws SQLException {
    Connection publicConnection;
    PreparedStatement updateStatement = null;
    int listSize = updatedList.size();
    publicConnectionManager.connect();
    publicConnection = publicConnectionManager.getConnection();
    for (int index = 0; index < listSize; index++) {
      updateStatement = publicConnection.prepareStatement(queryString);
      updateStatement.setString(1, updatedList.get(index));
      updateStatement.setInt(2, index + 1);
      updateStatement.executeQuery();
    }
    if (listSize < totalValue) {
      for (int index = listSize; index < totalValue; index++) {
        updateStatement = publicConnection.prepareStatement(queryString);
        updateStatement.setString(1, Constants.NULL_STRING);
        updateStatement.setInt(2, index + 1);
        updateStatement.executeQuery();
      }
    }
    publicConnectionManager.closeConnection();
    updateStatement.close();
  }

  /**
   * Get list of data for a multiple value query from the Public DB
   * using the query string provided from the parameter.
   *
   * @param queryString Query String (MySQL); require to provide
   *                    a '?' place to be replaced by the columnLabel.
   * @param columnLabel Column Label of the Data
   *                    that wants to be retrieved; not null;
   *                    required column has been populated in
   *                    the DB.
   * @return List Of Data from the Public DB.
   * @throws SQLException SQLException when connection to the DB has a problem.
   */
  public List<String> getMultipleValueQuery(
          String queryString,
          String columnLabel) throws SQLException {
    Connection publicConnection;
    PreparedStatement selectStatement;
    ResultSet results;
    List<String> listOfData = new ArrayList<>();
    publicConnectionManager.connect();
    publicConnection = publicConnectionManager.getConnection();
    selectStatement = publicConnection.prepareStatement(queryString);
    results = selectStatement.executeQuery();
    while (results.next()) {
      String data = results.getString(columnLabel);
      listOfData.add(data);
    }
    publicConnectionManager.closeConnection();
    selectStatement.close();
    results.close();
    return listOfData;
  }
}
