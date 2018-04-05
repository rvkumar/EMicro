package com.microlabs.admin.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.EmpCreationForm;
import com.microlabs.contacts.form.ContactsForm;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.utilities.UserInfo;


public class EmpCreationAction extends DispatchAction{
	
	
	 private static final char[] PASSWORD = "enfldsgbnlfghfghsngdlkddddsgm".toCharArray();
	    private static final byte[] SALT = {
	        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
	        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
	    };

	
	 private static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException {
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
	        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
	        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
	    }

	    private static String base64Encode(byte[] bytes) {
	        // NB: This class is internal, and you probably should use another impl
	        return new BASE64Encoder().encode(bytes);
	    }

	    private static String decrypt(String property) throws GeneralSecurityException, IOException {
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
	        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
	        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
	        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
	    }

	    private static byte[] base64Decode(String property) throws IOException {
	        // NB: This class is internal, and you probably should use another impl
	        return new BASE64Decoder().decodeBuffer(property);
	    }
	
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			HRDao ad=new HRDao();
			String keyword=creationForm.getSearchEmployee();
			ArrayList empList=new ArrayList();
		int totalRecords=creationForm.getTotalRecords();//21
		int startRecord=creationForm.getStartRecord();//11
		int endRecord=creationForm.getEndRecord();	
		
		
		if(totalRecords>10){
			  startRecord=1;
			  endRecord=10;
			  creationForm.setTotalRecords(totalRecords);
			  creationForm.setStartRecord(startRecord);
			  creationForm.setEndRecord(10);
			  }
			  else{
				  startRecord=1;
				  creationForm.setTotalRecords(totalRecords);
				  creationForm.setStartRecord(startRecord);
				  creationForm.setEndRecord(totalRecords);  
			  }

		  
		String getEmpdetails=" select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.employeenumber) AS  RowNum, u.username,u.employeenumber,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.TEL_EXTENS,dept.DPTSTXT,desg.DSGSTXT, " +
		"loc.location_code from users as u,Location as loc,department as  dept,designation as desg ,emp_official_info as emp where " +
		"(emp.EMP_FULLNAME like '%"+keyword+"%'  or u.username like '%"+keyword+"%'   or u.employeenumber like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' " +
				" or  dept.DPTSTXT " +
		"like'%"+keyword+"%'  or loc.location_code like'%"+keyword+"%'  or  emp.EMAIL_ID like'%"+keyword+"%' or  emp.TEL_EXTENS like'%"+keyword+"%')  and  emp.LOCID=loc.LOCATION_CODE " +
		"and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID  and emp.PERNR=u.employeenumber)as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";
		
		ResultSet rs=ad.selectQuery(getEmpdetails);
		while(rs.next())
		{
			EmpCreationForm emp=new EmpCreationForm();
			emp.setLoginName(rs.getString("username"));
			emp.setEmployeeNo(rs.getString("employeenumber"));
			emp.setFirstName(rs.getString("EMP_FULLNAME"));
			emp.setDesignation(rs.getString("DSGSTXT"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setLocation(rs.getString("location_code"));
			emp.setEmailID(rs.getString("EMAIL_ID"));
			emp.setContactNo(rs.getString("TEL_EXTENS"));
		
		  empList.add(emp);
		}
		request.setAttribute("employeeList", empList);
			
		 if(totalRecords>10)
			{
				request.setAttribute("nextButton", "nextButton");
			}
		
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			
			request.setAttribute("displayRecordNo", "displayRecordNo");
			
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("employeeList");
	}
	
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			HRDao ad=new HRDao();
			String keyword=creationForm.getSearchEmployee();
			ArrayList empList=new ArrayList();
		
		
		int totalRecords=creationForm.getTotalRecords();//21
		int startRecord=creationForm.getStartRecord();//11
		int endRecord=creationForm.getEndRecord();	
		
		
		 startRecord=totalRecords-9;
		 endRecord=totalRecords;
		 creationForm.setTotalRecords(totalRecords);
		 creationForm.setStartRecord(startRecord);
		 creationForm.setEndRecord(totalRecords);
		  
		 String getEmpdetails=" select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.employeenumber) AS  RowNum, u.username,u.employeenumber,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.TEL_EXTENS,dept.DPTSTXT,desg.DSGSTXT, " +
			"loc.location_code from users as u,Location as loc,department as  dept,designation as desg ,emp_official_info as emp where " +
			"(emp.EMP_FULLNAME like '%"+keyword+"%'  or u.username like '%"+keyword+"%'   or u.employeenumber like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' " +
					" or  dept.DPTSTXT " +
			"like'%"+keyword+"%'  or loc.location_code like'%"+keyword+"%'  or  emp.EMAIL_ID like'%"+keyword+"%' or  emp.TEL_EXTENS like'%"+keyword+"%')  and  emp.LOCID=loc.LOCATION_CODE " +
			"and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID  and emp.PERNR=u.employeenumber)as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";
			
		 ResultSet rs=ad.selectQuery(getEmpdetails);
			while(rs.next())
			{
				EmpCreationForm emp=new EmpCreationForm();
				emp.setLoginName(rs.getString("username"));
				emp.setEmployeeNo(rs.getString("employeenumber"));
				emp.setFirstName(rs.getString("EMP_FULLNAME"));
				emp.setDesignation(rs.getString("DSGSTXT"));
				emp.setDepartment(rs.getString("DPTSTXT"));
				emp.setLocation(rs.getString("location_code"));
				emp.setEmailID(rs.getString("EMAIL_ID"));
				emp.setContactNo(rs.getString("TEL_EXTENS"));
			
			  empList.add(emp);
			}
			request.setAttribute("employeeList", empList);
				
				
			
		 request.setAttribute("disableNextButton", "disableNextButton");
			request.setAttribute("previousButton", "previousButton");
			if(empList.size()<10)
			{
				
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
			request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("employeeList");
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			HRDao ad=new HRDao();
			String keyword=creationForm.getSearchEmployee();
			ArrayList empList=new ArrayList();
			System.out.println("Start Record="+creationForm.getStartRecord());
			System.out.println("End record="+creationForm.getEndRecord());
			System.out.println("Total Record="+creationForm.getTotalRecords());	
			
			int totalRecords=creationForm.getTotalRecords();//21
			int endRecord=creationForm.getStartRecord()-1;//20
			int startRecord=creationForm.getStartRecord()-10;//11
			
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}
			creationForm.setTotalRecords(totalRecords);
			creationForm.setStartRecord(1);
			creationForm.setEndRecord(10);
			  
		
			String getEmpdetails=" select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.employeenumber) AS  RowNum, u.username,u.employeenumber,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.TEL_EXTENS,dept.DPTSTXT,desg.DSGSTXT, " +
			"loc.location_code from users as u,Location as loc,department as  dept,designation as desg ,emp_official_info as emp where " +
			"(emp.EMP_FULLNAME like '%"+keyword+"%'  or u.username like '%"+keyword+"%'   or u.employeenumber like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' " +
					" or  dept.DPTSTXT " +
			"like'%"+keyword+"%'  or loc.location_code like'%"+keyword+"%'  or  emp.EMAIL_ID like'%"+keyword+"%' or  emp.TEL_EXTENS like'%"+keyword+"%')  and  emp.LOCID=loc.LOCATION_CODE " +
			"and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID  and emp.PERNR=u.employeenumber)as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";
			
			ResultSet rs=ad.selectQuery(getEmpdetails);
			while(rs.next())
			{	EmpCreationForm emp=new EmpCreationForm();
			emp.setLoginName(rs.getString("username"));
			emp.setEmployeeNo(rs.getString("employeenumber"));
			emp.setFirstName(rs.getString("EMP_FULLNAME"));
			emp.setDesignation(rs.getString("DSGSTXT"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setLocation(rs.getString("location_code"));
			emp.setEmailID(rs.getString("EMAIL_ID"));
			emp.setContactNo(rs.getString("TEL_EXTENS"));
		
		  empList.add(emp);
			}
			request.setAttribute("employeeList", empList);
			
			creationForm.setTotalRecords(totalRecords);
			creationForm.setStartRecord(startRecord);
			creationForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(empList.size()<10)
				{
					creationForm.setStartRecord(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("employeeList");
	}
	
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			int totalRecords=creationForm.getTotalRecords();//21
			int startRecord=creationForm.getStartRecord();//11
			int endRecord=creationForm.getEndRecord();
			HRDao ad=new HRDao();
			String keyword=creationForm.getSearchEmployee();
			ArrayList empList=new ArrayList();
			if(totalRecords>endRecord)
			{
				if(totalRecords==endRecord)
				{
					startRecord=startRecord;
					endRecord=totalRecords;
				}
				if(totalRecords>endRecord)
				{
				startRecord=endRecord+1;
				endRecord=endRecord+10;
				}
			
				String getEmpdetails=" select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.employeenumber) AS  RowNum, u.username,u.employeenumber,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.TEL_EXTENS,dept.DPTSTXT,desg.DSGSTXT, " +
				"loc.location_code from users as u,Location as loc,department as  dept,designation as desg ,emp_official_info as emp where " +
				"(emp.EMP_FULLNAME like '%"+keyword+"%'  or u.username like '%"+keyword+"%'   or u.employeenumber like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' " +
						" or  dept.DPTSTXT " +
				"like'%"+keyword+"%'  or loc.location_code like'%"+keyword+"%'  or  emp.EMAIL_ID like'%"+keyword+"%' or  emp.TEL_EXTENS like'%"+keyword+"%')  and  emp.LOCID=loc.LOCATION_CODE " +
				"and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID  and emp.PERNR=u.employeenumber)as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";
				
				ResultSet rs=ad.selectQuery(getEmpdetails);
				while(rs.next())
				{
					EmpCreationForm emp=new EmpCreationForm();
					emp.setLoginName(rs.getString("username"));
					emp.setEmployeeNo(rs.getString("employeenumber"));
					emp.setFirstName(rs.getString("EMP_FULLNAME"));
					emp.setDesignation(rs.getString("DSGSTXT"));
					emp.setDepartment(rs.getString("DPTSTXT"));
					emp.setLocation(rs.getString("location_code"));
					emp.setEmailID(rs.getString("EMAIL_ID"));
					emp.setContactNo(rs.getString("TEL_EXTENS"));
				
				  empList.add(emp);
				}
				request.setAttribute("employeeList", empList);
				
			}
			
			
			System.out.println("list length="+empList.size());
			
			 if(empList.size()!=0)
				{
				 creationForm.setTotalRecords(totalRecords);
				 creationForm.setStartRecord(startRecord);
				 creationForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					creationForm.setTotalRecords(totalRecords);
					creationForm.setStartRecord(start);
					creationForm.setEndRecord(end);
					
				}
			 if(empList.size()<10)
			 {
				 creationForm.setTotalRecords(totalRecords);
				 creationForm.setStartRecord(startRecord);
				 creationForm.setEndRecord(startRecord+empList.size()-1);
					request.setAttribute("nextButton", "");
					request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton"); 
				 
			 }
			 
			 if(endRecord==totalRecords)
			 {
				 request.setAttribute("nextButton", "");
					request.setAttribute("disableNextButton", "disableNextButton");
			 }
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("employeeList");
	}
	
	
	public ActionForward modifyEmployeeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		UserDao ad=new UserDao();
		try{
			
			//added by maha for main links for based group
			String status="0";
			String empActivation=creationForm.getActivation();
			if(empActivation.equalsIgnoreCase("On"))
				status="1";
			String gName = creationForm.getEmployeeGroup();
			String inMainLinks="";
	/*		try{
				String gmsql="select module_name from user_group where group_name like '%"+gName+"%' or group_name = 'All'";
				System.out.println("SQL is *********************************"+gmsql);
				ResultSet gmrs=ad.selectQuery(gmsql);
				while(gmrs.next()){
					gmsql="select id from links where link_name like '%"+gmrs.getString(1)+"%'";
					System.out.println("SQL is *********************************"+gmsql);
					ResultSet modrs=ad.selectQuery(gmsql);
					while(modrs.next()){
						if(inMainLinks.equalsIgnoreCase("")){
							inMainLinks = modrs.getString(1);
						}
						else{
							inMainLinks = inMainLinks +","+modrs.getString(1);
						}
					}
				}
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
			}*/
			//added by maha
			int i=0;
			String a="";
			try {
			 a = encrypt(creationForm.getPassword());
			} catch (UnsupportedEncodingException
					| GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			GregorianCalendar calendar = new GregorianCalendar();
			//Display the date now:
	        Date now = calendar.getTime();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String formattedDate = sdf.format(now);
	        System.out.println(formattedDate);

	        //Advance the calendar one day:
	        calendar.add(Calendar.DAY_OF_MONTH, 44);
	        Date tomorrow = calendar.getTime();
	        formattedDate = sdf.format(tomorrow);
	        String pwdExpDt=formattedDate;
	        
			String modifyEmpDetailsInUsersTable="update users " +
		"set username='"+creationForm.getLoginName()+"',password='"+a+"'," +
		"employeenumber='"+creationForm.getEmployeeNo()+"',activated='"+creationForm.getActivation()+"'," +
		"usr_type='"+creationForm.getEmpStatus()+"',status='"+status+"' , " +"squestionid='"+creationForm.getFavoriteQuest()+
		"',"+"squesanswer='"+creationForm.getAnswer()+"',passwordexpirydate='"+pwdExpDt+"'"+
		"where  employeenumber='"+creationForm.getReqEmpNo()+"'";
			i=ad.SqlExecuteUpdate(modifyEmpDetailsInUsersTable);
			if(i==1)
			{
				creationForm.setMessage("Employee Details Are Updated");
				String reqEmpno=creationForm.getReqEmpNo();
				creationForm.setReqEmpNo(reqEmpno);
		
			}
		request.setAttribute("modifyDetails", "modifyDetails");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("display"); 
	}
	public ActionForward editEmployeeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmpCreationForm empForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		UserDao ad=new UserDao();
		String empNo=request.getParameter("EmpNo");
		try{
		String getEmpDetails="select  u.username,u.password," +
				"u.employeenumber,u.activated," +
				" u.usr_type,u.squestionid,u.squesanswer from  users as u," +
				"emp_official_info as emp where u.employeenumber='"+empNo+"' and emp.PERNR='"+empNo+"'";	
		ResultSet rs=ad.selectQuery(getEmpDetails);
		while(rs.next())
		{
			empForm.setLoginName(rs.getString("username"));
		    empForm.setEmployeeNo(empNo);
		    empForm.setPassword(decrypt(rs.getString("password")));
			empForm.setActivation(rs.getString("activated"));
			empForm.setEmpStatus(rs.getString("usr_type"));
	        empForm.setFavoriteQuest(rs.getString("squestionid"));
			empForm.setAnswer(rs.getString("squesanswer"));
			empForm.setReqEmpNo(empNo);
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("modifyDetails", "modifyDetails");
		setValues(form, request);
		return mapping.findForward("display"); 
	}
	
	public ActionForward serchEmployee(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) { 
		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		
		String keyword=creationForm.getSearchEmployee();
		try{
			UserInfo user=(UserInfo)sesion.getAttribute("user");
			UserDao ad=new UserDao();
			String getCount="select count(*) from users as u,emp_official_info as emp,Location as loc,department as dept,designation as " +
	  		"desg where  " + "emp.LOCID=loc.location_code  and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID and u.employeenumber=emp.PERNR and" +
	  				"(emp.EMP_FULLNAME like '%"+keyword+"%'  or u.username like '%"+keyword+"%'   or u.employeenumber like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' " +
					" or  dept.DPTSTXT " +
			"like'%"+keyword+"%'  or loc.location_code like'%"+keyword+"%'  or  emp.EMAIL_ID like'%"+keyword+"%' or  emp.TEL_EXTENS like'%"+keyword+"%') ";
	  
		int totalRecords=creationForm.getTotalRecords();//21
		int startRecord=creationForm.getStartRecord();//11
		int endRecord=creationForm.getEndRecord();	
		
		if(totalRecords>10){
			  startRecord=1;
			  endRecord=10;
			  creationForm.setTotalRecords(totalRecords);
			  creationForm.setStartRecord(startRecord);
			  creationForm.setEndRecord(10);
			  }
			  else{
				  startRecord=1;
				  creationForm.setTotalRecords(totalRecords);
				  creationForm.setStartRecord(startRecord);
				  creationForm.setEndRecord(totalRecords);  
			  }
					ResultSet rsCount=ad.selectQuery(getCount);
					while(rsCount.next())
					{
						totalRecords=rsCount.getInt(1);	
					}
					
					 if(totalRecords>10)
					  {
						 creationForm.setTotalRecords(totalRecords);
					  startRecord=1;
					  endRecord=10;
					  creationForm.setStartRecord(1);
					  creationForm.setEndRecord(10);
					  request.setAttribute("displayRecordNo", "displayRecordNo");
					  request.setAttribute("nextButton", "nextButton");
					  }else
					  {
						  startRecord=1;
						  endRecord=totalRecords;
						  creationForm.setTotalRecords(totalRecords);
						  creationForm.setStartRecord(1);
						  creationForm.setEndRecord(totalRecords); 
					  }
						
	
	
			
			ArrayList empList=new ArrayList();
			String getEmpdetails=" select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.employeenumber) AS  RowNum, u.username,u.employeenumber,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.TEL_EXTENS,dept.DPTSTXT,desg.DSGSTXT, " +
			"loc.location_code from users as u,Location as loc,department as  dept,designation as desg ,emp_official_info as emp where " +
			"(emp.EMP_FULLNAME like '%"+keyword+"%'  or u.username like '%"+keyword+"%'   or u.employeenumber like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' " +
					" or  dept.DPTSTXT " +
			"like'%"+keyword+"%'  or loc.location_code like'%"+keyword+"%'  or  emp.EMAIL_ID like'%"+keyword+"%' or  emp.TEL_EXTENS like'%"+keyword+"%')  and  emp.LOCID=loc.LOCATION_CODE " +
			"and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID  and emp.PERNR=u.employeenumber)as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";
			
			ContactsForm form2=new ContactsForm();
			ResultSet rs=ad.selectQuery(getEmpdetails);
			while(rs.next())
			{
				EmpCreationForm emp=new EmpCreationForm();
				emp.setLoginName(rs.getString("username"));
				emp.setEmployeeNo(rs.getString("employeenumber"));
				emp.setFirstName(rs.getString("EMP_FULLNAME"));
				emp.setDesignation(rs.getString("DSGSTXT"));
				emp.setDepartment(rs.getString("DPTSTXT"));
				emp.setLocation(rs.getString("location_code"));
				emp.setEmailID(rs.getString("EMAIL_ID"));
				emp.setContactNo(rs.getString("TEL_EXTENS"));
			
			  empList.add(emp);
			}
			request.setAttribute("employeeList", empList);
			if(empList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				creationForm.setMessage("No Employee Found");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("employeeList");
	}
	
	public ActionForward displayEmployee(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
		List empList=new ArrayList();
		UserDao ad=new UserDao();
		
		  String getCount="select count(*) from users as u,emp_official_info as emp,Location as loc,department as dept,designation as " +
		  		"desg where  " + "emp.LOCID=loc.location_code  and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID and u.employeenumber=emp.PERNR";
		  int totalRecords=creationForm.getTotalRecords();//21
			int startRecord=creationForm.getStartRecord();//11
			int endRecord=creationForm.getEndRecord();	
			
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  creationForm.setTotalRecords(totalRecords);
				  creationForm.setStartRecord(startRecord);
				  creationForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  creationForm.setTotalRecords(totalRecords);
					  creationForm.setStartRecord(startRecord);
					  creationForm.setEndRecord(totalRecords);  
				  }
						ResultSet rsCount=ad.selectQuery(getCount);
						while(rsCount.next())
						{
							totalRecords=rsCount.getInt(1);	
						}
						
						 if(totalRecords>10)
						  {
							 creationForm.setTotalRecords(totalRecords);
						  startRecord=1;
						  endRecord=10;
						  creationForm.setStartRecord(1);
						  creationForm.setEndRecord(10);
						  request.setAttribute("displayRecordNo", "displayRecordNo");
						  request.setAttribute("nextButton", "nextButton");
						  }else
						  {
							  startRecord=1;
							  endRecord=totalRecords;
							  creationForm.setTotalRecords(totalRecords);
							  creationForm.setStartRecord(1);
							  creationForm.setEndRecord(totalRecords); 
						  }
							
		
		
		
		String getEmployee="select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.employeenumber) AS  RowNum,u.employeenumber,emp.EMP_FULLNAME,emp.EMAIL_ID," +
	"emp.TEL_EXTENS,u.username,loc.location_code,desg.DSGSTXT,dept.DPTSTXT from users as u,Location as loc,designation as desg,department " +
	"as dept,emp_official_info as emp where emp.LOCID=loc.LOCATION_CODE and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and " +
	"emp.PERNR=u.employeenumber) as sub Where  sub.RowNum between  1 and 10 ";

		ResultSet rs=ad.selectQuery(getEmployee);
		while(rs.next())
		{
			EmpCreationForm emp=new EmpCreationForm();
			emp.setLoginName(rs.getString("username"));
			emp.setEmployeeNo(rs.getString("employeenumber"));
			emp.setFirstName(rs.getString("EMP_FULLNAME"));
			emp.setDesignation(rs.getString("DSGSTXT"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setLocation(rs.getString("location_code"));
			emp.setEmailID(rs.getString("EMAIL_ID"));
			emp.setContactNo(rs.getString("TEL_EXTENS"));
			empList.add(emp);
			
		}
		request.setAttribute("employeeList", empList);	
		creationForm.setSearchEmployee("");	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mapping.findForward("employeeList");
	}
	
	public ActionForward display(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try
		{
			
			setValues(form,request);
			
		
			request.setAttribute("saveDetails", "saveDetails");
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("display");
	}
	
	
	public ActionForward resetProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		
		UserDao ad=new UserDao();
		String a="update attendance_process_log set process_Status='Completed',progress='100' where process_Status='Pending' ";
		ad.SqlExecuteUpdate(a);
		
		displayEmployee(mapping, creationForm, request, response);
		
		return mapping.findForward("employeeList");
	}
	

	public ActionForward saveEmployee(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EmpCreationForm creationForm=(EmpCreationForm)form;
		UserDao ad=new UserDao();
		
		String status="0";
		String empActivation=creationForm.getActivation();
		if(empActivation.equalsIgnoreCase("On"))
			status="1";
		
		
		String inMainLinks="";
	
	
		String saveEmpDetails="insert into users(username,employeenumber,password,activated,usr_type,include_links,incsublinks," +
		"incsubsublinks,groupname,group_id,status,count,squestionid,squesanswer,Emp_Photo,loginCount) " +
		"values('"+creationForm.getLoginName()+"','"+creationForm.getEmployeeNo()+"','"+creationForm.getPassword()+"'," +
		"'"+creationForm.getActivation()+"','"+creationForm.getEmpStatus()+"','1','1','1','"+creationForm.getEmployeeGroup()+"'" +
				",33,'"+status+"','0','"+creationForm.getFavoriteQuest()+"','"+creationForm.getAnswer()+"','',0)";
		int i=0;
		i=ad.SqlExecuteUpdate(saveEmpDetails);
		if(i==1)
		{
			creationForm.setMessage("Employee Details Are Saved");
		}else{
			creationForm.setMessage("Error...When Adding Employee Details. Please Check....");
		}
		request.setAttribute("saveDetails", "saveDetails");
		return mapping.findForward("display");
	}
	
	public void setValues(ActionForm form,HttpServletRequest request)
	{
		EmpCreationForm creationForm=(EmpCreationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
		ArrayList list = null;
		LinkedList locationID=new LinkedList();
		LinkedList locationName=new LinkedList();
		UserDao ad=new UserDao();
		String getLocations="select * from Location";
		ResultSet rsGetLocation=ad.selectQuery(getLocations);
		while(rsGetLocation.next()){
			locationID.add(rsGetLocation.getInt("LOCID"));
			locationName.add(rsGetLocation.getString("Location_Code")+"-"+rsGetLocation.getString("LOCNAME"));
		}
		
		creationForm.setLocationId(locationID);
		creationForm.setLocationName(locationName);
		
	
		LinkedList deptID=new LinkedList();
		LinkedList deptName=new LinkedList();
		String getdepartment="select * from department";
		ResultSet rsdepartment=ad.selectQuery(getdepartment);
		while(rsdepartment.next()){
			deptID.add(rsdepartment.getInt("DPTID"));
			deptName.add(rsdepartment.getString("DPTSTXT"));
		}
		
		creationForm.setDeptId(deptID);
		creationForm.setDeptName(deptName);
		
		LinkedList desgID=new LinkedList();
		LinkedList desgName=new LinkedList();
		String getdesignation="select * from designation";
		ResultSet rsdesignation=ad.selectQuery(getdesignation);
		while(rsdesignation.next()){
			desgID.add(rsdesignation.getInt("DSGID"));
			desgName.add(rsdesignation.getString("DSGSTXT"));
		}
		
		creationForm.setDesgnId(desgID);
		creationForm.setDesgName(desgName);
		
		LinkedList groupList=new LinkedList();
		
		String getGroup="select group_name from user_group group by group_name";
		ResultSet rsGroup=ad.selectQuery(getGroup);
		while(rsGroup.next()){
			groupList.add(rsGroup.getString("group_name"));
		}
		creationForm.setGroupList(groupList);
		
	}
	catch (Exception e) {
	e.printStackTrace();
	}
	}
}
