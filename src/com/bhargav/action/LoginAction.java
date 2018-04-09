/**
 * 
 */
package com.bhargav.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.bhargav.service.LoginService;
import com.microlabs.login.form.LoginForm;
import com.microlabs.utilities.UserInfo;

/**
 * @author nsrikantaiah
 */
public class LoginAction extends DispatchAction {

  private LoginService loginService = new LoginService();

  public ActionForward loginAction(ActionMapping actionMapping, ActionForm actionForm,
      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    final LoginForm loginForm = (LoginForm) actionForm;
    final UserInfo userObject = loginService.loginUser(loginForm);

    if (userObject == null) {
      loginForm.setMessage("Invalid username or password. Please check the credentials.");
      return mapping.findForward("displayLoginPage");
    }

    // Create the session for the user
    final HttpSession httpSession = httpServletRequest.getSession(true);
    httpSession.setAttribute("userInfo", userObject);
    
    // Redirect the user to home page
    return mapping.findForward("home");
    
  }

}
