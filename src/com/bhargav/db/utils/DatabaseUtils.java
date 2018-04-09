/**
 * 
 */
package com.bhargav.db.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author nsrikantaiah
 */
public class DatabaseUtils {

  public void closeDatabaseObjects(Connection dbConnection, Statement dbStatement,
      ResultSet dbResultSet) {
    try {
      if (dbResultSet != null) {
        dbResultSet.close();
        dbResultSet = null;
      }
    } catch (Exception er) {
      dbResultSet = null;
    }

    try {
      if (dbStatement != null) {
        dbStatement.close();
        dbStatement = null;
      }
    } catch (Exception es) {
      dbStatement = null;
    }

    try {
      if (dbConnection != null) {
        dbConnection.close();
        dbConnection = null;
      }
    } catch (Exception ec) {
      dbConnection = null;
    }

  }

}
