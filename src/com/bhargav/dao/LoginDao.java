/**
 * 
 */
package com.bhargav.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bhargav.db.utils.DatabaseUtils;
import com.microlabs.login.form.LoginForm;
import com.microlabs.utilities.UserInfo;

/**
 * @author nsrikantaiah
 */
public class LoginDao {

  public UserInfo loginUser(LoginForm loginForm) {

    StringBuilder queryBuilder = new StringBuilder("select * from users where");
    queryBuilder.append(" username = '").append(loginForm.getUserName()).append("'");
    queryBuilder.append(" and password = '").append(loginForm.getPassword()).append("'");
    queryBuilder.append(" and status = '1' and activated='On'");
    return getUserInfo(queryBuilder.toString());

  }

  /**
   * Utility method to execute the provided sql query and return the UserInfo object reference for
   * the caller.
   * 
   * @param sQuery
   * @return UserInfo reference. If exception raised then the reference will be null
   */
  private UserInfo getUserInfo(final String sQuery) {

    Connection dbConnection = null;
    Statement dbStatement = null;
    ResultSet dbResultSet = null;

    try {
      // Connect to Database
      dbConnection = ConnectionFactory.getConnection();
      dbStatement = dbConnection.createStatement();
      dbResultSet = dbStatement.executeQuery(sQuery);

      // Populate the UserInfo object
      dbResultSet.next();
      final UserInfo userObject = new UserInfo();
      userObject.setFirstName(dbResultSet.getString("first_name"));
      userObject.setLastName(dbResultSet.getString("last_name"));
      userObject.setUserType(dbResultSet.getString("usr_type"));
      // Blah... Blah... Blah...
      return userObject;

    } catch (SQLException se) {
      // Use the Logger here instead of printing the stackTrace to console
      se.printStackTrace();
    } finally {
      DatabaseUtils.closeDatabaseObjects(dbConnection, dbStatement, dbResultSet);
    }

  }


}
