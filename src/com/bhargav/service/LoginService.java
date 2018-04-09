/**
 * 
 */
package com.bhargav.service;

import org.apache.commons.lang.StringUtils;

import com.bhargav.dao.LoginDao;
import com.microlabs.login.form.LoginForm;
import com.microlabs.utilities.UserInfo;

/**
 * @author nsrikantaiah
 */
public class LoginService {

  private LoginDao loginDAO = new LoginDao();
  
  public UserInfo loginUser(LoginForm loginForm) {

    // Add validation for username and password here
    if (StringUtils.isBlank(loginForm.getUserName()) || StringUtils.isBlank(loginForm.getPassword())) {
      // Either username or password is null/empty. Hence return null reference to the caller.
      return null;
    }
    
    return loginDAO.loginUser(loginForm);

  }

}
