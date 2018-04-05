package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.ChangePasswordForm;
import com.microlabs.utilities.UserInfo;


public class ChangePasswordAction extends DispatchAction{
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;// TODO Auto-generated method stub
		changePasswordForm.setOldPassword1("");
		
		return mapping.findForward("display");
	}
	
	
	public ActionForward changePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ChangePasswordForm changePasswordForm = (ChangePasswordForm) form;// TODO Auto-generated method stub
		String oldPassword=changePasswordForm.getOldPassword1();
		String newPassword=changePasswordForm.getNewPassword();
		String confirmNewPassword=changePasswordForm.getConfirmNewPassword();
		
		UserDao ad=new UserDao();
		
		HttpSession session=request.getSession();
		
		UserInfo userInfo=(UserInfo)session.getAttribute("user");
		
		String userName=userInfo.getUserName();
		
		
		String sql="select password from users where username='"+userName+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			
			String dbPassword="";
			
			while(rs.next()) 
			{
				dbPassword=rs.getString("password");
			}
			
			
			if(!oldPassword.equals(dbPassword)){
				changePasswordForm.setMessage("Old Password is In Correct");
			}
			
			if(!newPassword.equals(confirmNewPassword)){
				changePasswordForm.setMessage("New Password and Confirm Password does not match");
			}
			
			if(oldPassword.equals(dbPassword) && newPassword.equals(confirmNewPassword)){
				
				String sql1="update users set password='"+newPassword+"' " +
						"where username='"+userName+"'";
				
				int a=ad.SqlExecuteUpdate(sql1);
				
				if(a>0){
					changePasswordForm.setMessage("Password Updated Successfully");
					
					changePasswordForm.setOldPassword1("");
					changePasswordForm.setConfirmNewPassword("");
					changePasswordForm.setNewPassword("");
				}
				
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		
		
		return mapping.findForward("display");
	}
	
	
	
}
