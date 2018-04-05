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
import com.microlabs.admin.form.UserForm;

public class UserAction extends DispatchAction{
	
	
	public ActionForward searchSid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm=(UserForm)form;
		
		UserDao ad=new UserDao();
		
		String sql="select distinct group_name from user_group";
		
		ResultSet rs=ad.selectQuery(sql);
		
		
		ArrayList groupIdList=new ArrayList();
		ArrayList groupValueList=new ArrayList();
		
		try{
			
			while(rs.next()) {
				groupIdList.add(rs.getString("group_name"));
				groupValueList.add(rs.getString("group_name"));
			}
			userForm.setGroupIdList(groupIdList);
			userForm.setGroupValueList(groupValueList);
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		return mapping.findForward("searchEmployee");
	}
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm=(UserForm)form;
		
		UserDao ad=new UserDao();
		
		String sql="select distinct group_name from user_group";
		
		ResultSet rs=ad.selectQuery(sql);
		
		ArrayList groupIdList=new ArrayList();
		ArrayList groupValueList=new ArrayList();
		
		try{
			
			while(rs.next()) {
				groupIdList.add(rs.getString("group_name"));
				groupValueList.add(rs.getString("group_name"));
			}
			
			userForm.setGroupIdList(groupIdList);
			userForm.setGroupValueList(groupValueList);
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		return mapping.findForward("display");
	}
	
	
	public ActionForward searchUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm=(UserForm)form;
		
		UserDao ad=new UserDao();
		
		String employeeType=userForm.getEmployeeType();
		String countryName=userForm.getCountryName();
		String state=userForm.getState();
		String plant=userForm.getPlant();
		String department=userForm.getDepartment();
		String designation=userForm.getDesignation();
		
	/*	String sql="select * from users where usr_type like '%"+employeeType+"%' and " +
				"country_id like '%"+countryName+"%' and department_id like '%"+department+"%' " +
				"and designation_id like '%"+designation+"%' " +
				"and state_id like '%"+state+"%' and plant_id like '%"+plant+"%' and activated='On'" +
				" and status='1'";*/
		
		
		String sql="select * from users where usr_type like '%"+employeeType+"%' and " +
		"country_id like '%"+countryName+"%' and department_id like '%"+department+"%' " +
		" and activated='On'" +
		" and status='1'";
		
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			UserForm userForm1=null;
			
			ArrayList a1=new ArrayList();
			
			while(rs.next()) {
				userForm1=new UserForm();
				userForm1.setUserId(rs.getString("id"));
				userForm1.setCountryName(rs.getString("country_id"));
				userForm1.setState(rs.getString("state_id"));
				userForm1.setDepartment(rs.getString("department_id"));
				userForm1.setDesignation(rs.getString("designation_id"));
				userForm1.setPlant(rs.getString("plant_id"));
				//userForm1.setFullName(rs.getString("fullname"));
				userForm1.setUserName(rs.getString("username"));
				userForm1.setPassword(rs.getString("password"));
				userForm1.setEmployeeType(rs.getString("usr_type"));
				userForm1.setGroupID(rs.getString("groupname"));
				
				a1.add(userForm1);
			}
			
			
			request.setAttribute("userDetails", a1);
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		return mapping.findForward("searchEmployee");
	}
	
	
	public ActionForward modifyUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm=(UserForm)form;
		
		UserDao ad=new UserDao();
		
		String employeeType=userForm.getEmployeeType();
		String countryName=userForm.getCountryName();
		String state=userForm.getState();
		String plant=userForm.getPlant();
		String department=userForm.getDepartment();
		String designation=userForm.getDesignation();
		
		String groupID=userForm.getGroupID();
		String userName=userForm.getUserName();
		String password=userForm.getPassword();
		
		
		String sql1="select id from links where link_name in (select module_name from" +
				" user_group where group_name='"+groupID+"' and status=1 and " +
				"sub_modulename is null and sub_linkname is null) and sub_linkname is" +
				" null and status is null";
		
		ResultSet rs=ad.selectQuery(sql1);
		
		String incLinks="";
		
		String subIncLinks="";
		
		String subSubIncLinks="";
		try{
			while(rs.next()) {
				incLinks+=rs.getString("id")+",";
			}
			
			
			String sql2="select id from links where link_name in (select sub_modulename from" +
			" user_group where group_name='"+groupID+"' and status=1 and " +
			"sub_modulename is not null and sub_linkname is null) and sub_linkname is" +
			" null and status=1";
			
			ResultSet rs1=ad.selectQuery(sql2);
			while (rs1.next()) {
				subIncLinks+=rs1.getString("id")+",";
			}
			
			
			String sql3="select id from links where link_name in (select sub_linkname from" +
			" user_group where group_name='"+groupID+"' and status=1 and " +
			"sub_modulename is not null and sub_linkname is not null) and sub_linkname is not" +
			" null and status=1";
			
			ResultSet rs2=ad.selectQuery(sql3);
			while (rs2.next()) {
				subSubIncLinks+=rs2.getString("id")+",";
			}
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		String subSubLinks="";
		
		if(subSubIncLinks.equalsIgnoreCase("")){
			
		}else{
			subSubLinks=subSubIncLinks.substring(0, subSubIncLinks.lastIndexOf(","));
		}
		
		
		String sql="update users set groupname='"+groupID+"'," +
				"include_links='"+incLinks.substring(0, incLinks.lastIndexOf(","))+"'," +
				"incsublinks='"+subIncLinks.substring(0, subIncLinks.lastIndexOf(","))+"'," +
				"incsubsublinks='"+subSubLinks+"',usr_type='per' and where " +
				"username='"+userName+"' and password='"+password+"'";
		
		int a= ad.SqlExecuteUpdate(sql);
		
		if(a>0){
			userForm.setMessage("User Details Modified Successfully");
			
			userForm.setEmployeeType("");
			userForm.setCountryName("");
			userForm.setState("");
			userForm.setPlant("");
			userForm.setDepartment("");
			userForm.setDesignation("");
			userForm.setGroupID("");
			userForm.setUserName("");
			userForm.setPassword("");
			userForm.setFullName("");
			userForm.setEmployeeNumber("");
		}
		String sql2="select distinct group_name from user_group";
		
		ResultSet rs1=ad.selectQuery(sql2);
		
		ArrayList groupIdList=new ArrayList();
		ArrayList groupValueList=new ArrayList();
		
		try{
			
			while(rs1.next()) {
				groupIdList.add(rs1.getString("group_name"));
				groupValueList.add(rs1.getString("group_name"));
			}
			
			userForm.setGroupIdList(groupIdList);
			userForm.setGroupValueList(groupValueList);
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		return mapping.findForward("display");
	}
	
	
	
	
	public ActionForward deleteUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserForm userForm=(UserForm)form;
		
		UserDao ad=new UserDao();
		
		String employeeType=userForm.getEmployeeType();
		String countryName=userForm.getCountryName();
		String state=userForm.getState();
		String plant=userForm.getPlant();
		String department=userForm.getDepartment();
		String designation=userForm.getDesignation();
		
		String groupID=userForm.getGroupID();
		String userName=userForm.getUserName();
		String password=userForm.getPassword();
		
		String sql="update users set status='0' where usr_type='"+employeeType+"' and " +
				"username='"+userName+"' and password='"+password+"'";
		
		int a= ad.SqlExecuteUpdate(sql);
		
		if(a>0){
			userForm.setMessage("User Details Delete Successfully");
			
			userForm.setEmployeeType("");
			userForm.setCountryName("");
			userForm.setState("");
			userForm.setPlant("");
			userForm.setDepartment("");
			userForm.setDesignation("");
			userForm.setGroupID("");
			userForm.setUserName("");
			userForm.setPassword("");
			userForm.setFullName("");
			userForm.setEmployeeNumber("");
			
		}
		
		
		return mapping.findForward("display");
	}
	
	
	
	
	
}
