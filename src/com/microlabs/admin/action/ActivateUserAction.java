package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.ActivateUserForm;


public class ActivateUserAction extends DispatchAction{
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActivateUserForm activateUserForm=(ActivateUserForm)form;
		
		UserDao ad=new UserDao();
		
		String sql="select * from users where status='0'";
		
		ActivateUserForm activateUserForm1=null;
		
		try{
			
			ArrayList a1=new ArrayList();
			
			ResultSet rs=ad.selectQuery(sql);
			
			while(rs.next()) {
				activateUserForm1=new ActivateUserForm();
				activateUserForm1.setUserName(rs.getString("username"));
 				activateUserForm1.setUserId(rs.getString("id"));
				a1.add(activateUserForm1);
			}
			
			request.setAttribute("userDetails",a1);
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		return mapping.findForward("display");
	}
	
	
	
	public ActionForward activateUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActivateUserForm activateUserForm=(ActivateUserForm)form;
		
		UserDao ad=new UserDao();
		
		
		String userId=request.getParameter("id");
		
		
		String sql="update users set status='1' where id='"+userId+"'";
		
		int a= ad.SqlExecuteUpdate(sql);
		
		if(a>0){
			activateUserForm.setMessage("User is Activated Successfully");
		}
		
		return mapping.findForward("display");
	}
	
	
	
}
