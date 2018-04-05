package com.microlabs.hr.action;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.hr.dao.HRDao;
import com.microlabs.hr.form.HRNewEmpJoinForm;

public class HREmpAction extends DispatchAction{
	
	
	
	
	public ActionForward getUserName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			HRNewEmpJoinForm empForm=(HRNewEmpJoinForm)form;
			HRDao ad=new HRDao();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//get current date time with Date()
			Date date = new Date();
			System.out.println(dateFormat.format(date));

			//get current date time with Calendar()
			String username="";
			Calendar cal = Calendar.getInstance();
			System.out.println("Current Date="+dateFormat.format(cal.getTime()));
			String currentDate=dateFormat.format(cal.getTime());
			String getTotalEmp="select max(employeenumber) from users ";
			System.out.println("getTotalEmp="+getTotalEmp);
			int max=0;
			ResultSet rs1=ad.selectQuery(getTotalEmp);
			while(rs1.next()){
				max=Integer.parseInt(rs1.getString(1));
			}
			
			System.out.println("max of emp="+max);
			max=max+1;
			empForm.setEmpno(max);
			username=Integer.toString(max);
			
			String pwd=currentDate;
			System.out.println("pwd="+pwd);
			System.out.println("pwd="+pwd.length());
			pwd=pwd.replaceAll("[-]","");
			System.out.println("pwd="+pwd);
			pwd=max+"@"+pwd;
			System.out.println("auto pwd="+pwd);
			System.out.println("auto username="+username);
			empForm.setUserloginname(username);
			empForm.setPassword(pwd);
			
	
		}
	catch (Exception e) {
		
		}
	request.setAttribute("SaveButton", "SaveButton");
	return mapping.findForward("displayNewEmpJoinForm");
	}
	public ActionForward updateEmp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			System.out.println("updateEmp()--------");
			HRNewEmpJoinForm empForm=(HRNewEmpJoinForm)form;
			HRDao ad=new HRDao();
			HttpSession session=request.getSession();
			System.out.println("emp id="+empForm.getId());
			String checkUsernameQuery="select * from users where id='"+empForm.getId()+"'";
		
			String username="";
			String password="";
			ResultSet rs=ad.selectQuery(checkUsernameQuery);
			while(rs.next()){
				username=rs.getString("username");
				password=rs.getString("password");
			}
		
			
			if(username.equalsIgnoreCase(empForm.getUserloginname())&& password.equalsIgnoreCase(empForm.getPassword()))
			{
				System.out.println("same");
				String updateEmp="update users set jobTitle='"+empForm.getJobTitle()+"',firstName='"+empForm.getFirstName()+"',middleName='"+empForm.getMiddleName()+"'," +
				" lastname='"+empForm.getLastname()+"',usr_type='"+empForm.getEmpType()+"',userstatus='"+empForm.getEmpStatus()+"'," +
				" mail_id='"+empForm.getEmailID()+"',department_id='"+empForm.getDepartment()+"',country_id='"+empForm.getCountry()+"',state_id='"+empForm.getState()+"',plant_id='"+empForm.getPlant()+"'," +
						"designation_id='"+empForm.getDesignation()+"',mobile_no='"+empForm.getMobileno()+"',entering_date='"+empForm.getEnteringDate()+"',gender='"+empForm.getGender()+"',experiance='"+empForm.getExperiance()+"' where id='"+empForm.getId()+"'  ";
		System.out.println("update emp info="+updateEmp);
		int i=0;
		i=ad.SqlExecuteUpdate(updateEmp);
		
		if(i>0){
			String updateEmpPersonalInfo="update emp_personal_info set first_name='"+empForm.getFirstName()+"',middle_name='"+empForm.getMiddleName()+"',last_name='"+empForm.getLastname()+"',mobile_no='"+empForm.getMobileno()+"',email_address='"+empForm.getEmailID()+"' where user_id='"+empForm.getId()+"'  ";
			
			ad.SqlExecuteUpdate(updateEmpPersonalInfo);
			session.setAttribute("status","Employee Information Is Updated");
			 return mapping.findForward("displayNewEmpJoinForm");				
		}
		if(i==0){
			session.setAttribute("status","values are not updated.Please Check....");
			 return mapping.findForward("displayNewEmpJoinForm");				
			
		}
			}
			else{
				
				System.out.println("not same");
				
				String checkUsernameQuery1="select count(*) from users where  username='"+empForm.getUserloginname()+"' or password='"+empForm.getPassword()+"'";
				System.out.println("checkUsernameQuery="+checkUsernameQuery1);
				int count=0;
				ResultSet rs1=ad.selectQuery(checkUsernameQuery1);
				while(rs1.next()){
					count=rs1.getInt(1);
				}
				
				
				if(count==0)
				{
					
					String updateEmp="update users set jobTitle='"+empForm.getJobTitle()+"',username='"+empForm.getUserloginname()+"',password='"+empForm.getPassword()+"',firstName='"+empForm.getFirstName()+"',middleName='"+empForm.getMiddleName()+"'," +
					" lastname='"+empForm.getLastname()+"',usr_type='"+empForm.getEmpType()+"',userstatus='"+empForm.getEmpStatus()+"'," +
					" mail_id='"+empForm.getEmailID()+"',department_id='"+empForm.getDepartment()+"',country_id='"+empForm.getCountry()+"',state_id='"+empForm.getState()+"',plant_id='"+empForm.getPlant()+"'," +
							"designation_id='"+empForm.getDesignation()+"',mobile_no='"+empForm.getMobileno()+"',entering_date='"+empForm.getEnteringDate()+"',gender='"+empForm.getGender()+"',experiance='"+empForm.getExperiance()+"' where id='"+empForm.getId()+"'  ";
			System.out.println("update emp info="+updateEmp);
				int i=0;
				i=ad.SqlExecuteUpdate(updateEmp);
				
				if(i>0){
					String updateEmpPersonalInfo="update emp_personal_info set first_name='"+empForm.getFirstName()+"',middle_name='"+empForm.getMiddleName()+"',last_name='"+empForm.getLastname()+"',mobile_no='"+empForm.getMobileno()+"',email_address='"+empForm.getEmailID()+"',user_name='"+empForm.getUserloginname()+"' where user_id='"+empForm.getId()+"'  ";
					
					ad.SqlExecuteUpdate(updateEmpPersonalInfo);
					session.setAttribute("status","Employee Information Is Updated");
					 return mapping.findForward("displayNewEmpJoinForm");				
				}
				if(i==0){
					session.setAttribute("status","values are not updated.Please Check....");
					 return mapping.findForward("displayNewEmpJoinForm");				
					
				}
				}
				else{
					session.setAttribute("status","Employee Information Not Updated UserName or Password Already Exist");
					 return mapping.findForward("displayNewEmpJoinForm");
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		

		request.setAttribute("ModifyButton", "ModifyButton");
		return mapping.findForward("displayNewEmpJoinForm");
	}

	public ActionForward modifyEmpInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			HRNewEmpJoinForm empForm=(HRNewEmpJoinForm)form;
			System.out.println("modifyEmpInfo()----");
			HRDao ad=new HRDao();
			
			String id=request.getParameter("id");
			System.out.println("selected id="+id);
			
			
		
			String getEmp="select * from users where id='"+id+"'";
			ResultSet rs=ad.selectQuery(getEmp);
			while(rs.next()){
				
				empForm.setId(id);
				empForm.setJobTitle(rs.getString("jobTitle"));
				empForm.setFirstName(rs.getString("firstName"));
				empForm.setMiddleName(rs.getString("middleName"));
				empForm.setLastname(rs.getString("lastname"));
				empForm.setDepartment(rs.getString("department_id"));
				empForm.setDesignation(rs.getString("designation_id"));
				empForm.setEmpStatus(rs.getString("userstatus"));
				empForm.setEmpType(rs.getString("usr_type"));
				empForm.setEmailID(rs.getString("mail_id"));
				empForm.setCountry(rs.getString("country_id"));
				empForm.setState(rs.getString("state_id"));
				empForm.setPlant(rs.getString("plant_id"));
				empForm.setUserloginname(rs.getString("username"));
				empForm.setPassword(rs.getString("password"));
				empForm.setMobileno(rs.getString("mobile_no"));
				empForm.setEnteringDate(rs.getString("entering_date"));
				empForm.setGender(rs.getString("gender"));
			    empForm.setEmpActivation(rs.getString("activated"));
			    empForm.setExperiance(rs.getString("experiance"));
			    
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
        request.setAttribute("username","username");
		request.setAttribute("ModifyButton", "ModifyButton");
		return mapping.findForward("displayNewEmpJoinForm");
	}
	
	
	public ActionForward listOfEmp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
		System.out.println("listOfEmp()----");
		HRDao ad=new HRDao();
		String getEmpQuery="select * from users order by id";
		ResultSet rs=ad.selectQuery(getEmpQuery);
		ArrayList listOfEmp=new ArrayList();
	
		while(rs.next()){
			HRNewEmpJoinForm emp=new HRNewEmpJoinForm();
			emp.setId(rs.getString("id"));
			
			emp.setFirstName(rs.getString("firstName")+" "+rs.getString("middleName")+" "+rs.getString("lastname"));
			
			emp.setDepartment(rs.getString("department_id"));
			String designation=rs.getString("designation_id");
			System.out.println("designation="+designation);
		
			if(designation.equalsIgnoreCase("1"))
			{
				designation="Head Technology";
			}
			else if(designation.equalsIgnoreCase("2"))
			{
				designation="Project Assistant";
			}else if(designation.equalsIgnoreCase("3"))
			{
				designation="IT";
			}
			else if(designation.equalsIgnoreCase("4"))
			{
				designation="Master Trainer";
			}
			else if(designation.equalsIgnoreCase("5"))
			{
				designation="Manager";
			}
			
			
			
			emp.setDesignation(designation);
			
			String plant=rs.getString("plant_id");
		
			
			if(plant.equalsIgnoreCase("1"))
			{
				plant="Bangalore";
			}
			else if(plant.equalsIgnoreCase("2"))
			{
				designation="Hyderabad";
			}else if(plant.equalsIgnoreCase("3"))
			{
				plant="Chennai";
			}
			else if(plant.equalsIgnoreCase("4"))
			{
				plant="Delhi";
			}
			
			
			emp.setPlant(plant);
			
			listOfEmp.add(emp);
			
		}
		HttpSession session=request.getSession();
		session.setAttribute("listOfEmp", listOfEmp);
		
		
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return mapping.findForward("displayEmployee");
	}
	
	public ActionForward hrNewEmpForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("hrNewEmpForm()----");
		 request.setAttribute("status","");
	
			request.setAttribute("SaveButton", "SaveButton");
		return mapping.findForward("displayNewEmpJoinForm");
	}
	
	public ActionForward saveNewEmp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			System.out.println("saveNewEmp()----");
		HRNewEmpJoinForm empForm=(HRNewEmpJoinForm)form;
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//get current date time with Date()
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		//get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		System.out.println("Current Date="+dateFormat.format(cal.getTime()));
		String currentDate=dateFormat.format(cal.getTime());

		
		String checkUsernameQuery="select count(*) from users where username='"+empForm.getUserloginname()+"' and password='"+empForm.getPassword()+"'";
		
		int count=0;
		ResultSet rs1=ad.selectQuery(checkUsernameQuery);
		while(rs1.next()){
			count=rs1.getInt(1);
		}
		System.out.println("count="+count);
		if(count==0)
		{
    
			//jobTitle,username,password,firstName,middleName,shortname,usr_type,mail_id,department_id,country_id,state_id,plant_id,designation_id,mobile_no,entering_date,gender
		 
			String insertEmpQuery="insert into users(jobTitle,username,password,firstname,middlename,lastname," +
					"usr_type,mail_id,department_id,country_id,state_id,plant_id,designation_id,mobile_no,entering_date,gender,activated,experiance,employeenumber,include_links,exclude_links,incsublinks,count,userstatus) values" +
					"('"+empForm.getJobTitle()+"','"+empForm.getUserloginname()+"','"+empForm.getPassword()+"','"+empForm.getFirstName()+"'," +
					"'"+empForm.getMiddleName()+"','"+empForm.getLastname()+"','"+empForm.getEmpType()+"'," +
					"'"+empForm.getEmailID()+"','"+empForm.getDepartment()+"','"+empForm.getCountry()+"','"+empForm.getState()+"','"+empForm.getPlant()+"'," +
					"'"+empForm.getDesignation()+"','"+empForm.getMobileno()+"','"+currentDate+"','"+empForm.getGender()+"','On','"+empForm.getExperiance()+"','"+empForm.getUserloginname()+"','4','','18','0','"+empForm.getEmpStatus()+"')";
			System.out.println("inertEmpQuery="+insertEmpQuery);
			
			int i=0;
			i=ad.SqlExecuteUpdate(insertEmpQuery);
			System.out.println("i="+i);
			
			if(i>0){
				
				String getUserID="select * from users where username='"+empForm.getUserloginname()+"' and password='"+empForm.getPassword()+"'";
			    System.out.println("getUserID="+getUserID); 
				ResultSet rs=ad.selectQuery(getUserID);
				int id=0;
				while(rs.next())
				{
					id=Integer.parseInt(rs.getString("id"));
				}
				
				System.out.println("id from users table="+id);
				String insertEmp_PersonalInfo="insert into emp_personal_info(user_id,employee_no,first_name,middle_name,last_name,mobile_no,email_address,user_name)" +
						" values('"+id+"','"+empForm.getUserloginname()+"','"+empForm.getFirstName()+"','"+empForm.getMiddleName()+"','"+empForm.getLastname()+"','"+empForm.getMobileno()+"','"+empForm.getEmailID()+"','"+empForm.getUserloginname()+"')";
				ad.SqlExecuteUpdate(insertEmp_PersonalInfo);           
				
				session.setAttribute("status","New Employee Inserted Successfully User Name='"+empForm.getUserloginname()+"' and Password='"+empForm.getPassword()+"'");
			
			}
			if(i==0){
				session.setAttribute("status","Values Are Not Inserted.Please Check.... ");
			}
			
		}
		
		else{
			session.setAttribute("status","Please Change Username and Password");
		}
		
		 
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return mapping.findForward("displayNewEmpJoinForm");
		
	}
		
}
	
	
