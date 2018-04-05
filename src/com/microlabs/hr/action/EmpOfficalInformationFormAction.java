package com.microlabs.hr.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.db.MainDao;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class EmpOfficalInformationFormAction extends DispatchAction{
	EssDao ad=new EssDao();
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			String keyword=empForm.getSearchEmployee();
			ArrayList empList=new ArrayList();
		int totalRecords=empForm.getTotalRecords();//21
		int startRecord=empForm.getStartRecord();//11
		int endRecord=empForm.getEndRecord();	
		
		
		if(totalRecords>10){
			  startRecord=1;
			  endRecord=10;
			  empForm.setTotalRecords(totalRecords);
			  empForm.setStartRecord(startRecord);
			  empForm.setEndRecord(10);
			  }
			  else{
				  startRecord=1;
				  empForm.setTotalRecords(totalRecords);
				  empForm.setStartRecord(startRecord);
				  empForm.setEndRecord(totalRecords);  
			  }

		  
		String getEmpdetails="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY emp.PERNR) AS  RowNum,emp.PERNR,emp.Emp_FullName,loc.LOCATION_CODE,desg.DSGSTXT," +
		"dept.DPTSTXT,emp.EMAIL_ID,emp.TEL_EXTENS,emp.IP_PHONE from emp_official_info as emp,Location as loc,designation as desg,department as  dept where " +
		"(emp.PERNR like'%"+keyword+"%' or   emp.EMP_FULLNAME like'%"+keyword+"%' or emp.DPTID like'%"+keyword+"%' or emp.DSGID like'%"+keyword+"%' or " +
		"emp.EMAIL_ID  like'%"+keyword+"%' or emp.TEL_EXTENS like'%"+keyword+"%' or emp.IP_PHONE like'%"+keyword+"%' or dept.DPTSTXT like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' or loc.LOCATION_CODE='"+keyword+"' ) and " +
		" dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID	and emp.LOCID=loc.LOCATION_CODE  and emp.active='1') as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";
		ResultSet rs=ad.selectQuery(getEmpdetails);
		while(rs.next())
		{
			EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
			emp.setLoginName(rs.getString("Emp_FullName"));
			emp.setEmployeeNumber(rs.getString("PERNR"));
			emp.setFirstName(rs.getString("Emp_FullName"));
			emp.setDesignation(rs.getString("DSGSTXT"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setLocation(rs.getString("LOCATION_CODE"));
			emp.setEmailid(rs.getString("EMAIL_ID"));
			emp.setContactNo(rs.getString("TEL_EXTENS"));
			emp.setIpPhoneNo(rs.getString("IP_PHONE"));
	
			empList.add(emp);
		}
		request.setAttribute("employeeList", empList);
			
		 if(totalRecords>10)
			{
				request.setAttribute("nextButton", "nextButton");
			}
		
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("materialCodeList","materialCodeList");
			
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("EmployeeOfficailInfoList");
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			
			String keyword=empForm.getSearchEmployee();
			ArrayList empList=new ArrayList();
		
		
		int totalRecords=empForm.getTotalRecords();//21
		int startRecord=empForm.getStartRecord();//11
		int endRecord=empForm.getEndRecord();	
		
		
		 startRecord=totalRecords-9;
		 endRecord=totalRecords;
		 empForm.setTotalRecords(totalRecords);
		 empForm.setStartRecord(startRecord);
		 empForm.setEndRecord(totalRecords);
		  
		 
		  
		 String getEmpdetails="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY emp.PERNR) AS  RowNum,emp.PERNR,emp.Emp_FullName,loc.LOCATION_CODE,desg.DSGSTXT," +
			"dept.DPTSTXT,emp.EMAIL_ID,emp.TEL_EXTENS,emp.IP_PHONE from emp_official_info as emp,Location as loc,designation as desg,department as  dept where " +
			"(emp.PERNR like'%"+keyword+"%' or   emp.EMP_FULLNAME like'%"+keyword+"%' or emp.DPTID like'%"+keyword+"%' or emp.DSGID like'%"+keyword+"%' or " +
			"emp.EMAIL_ID  like'%"+keyword+"%' or emp.TEL_EXTENS like'%"+keyword+"%' or emp.IP_PHONE like'%"+keyword+"%'  or dept.DPTSTXT like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' or loc.LOCATION_CODE='"+keyword+"' ) and " +
			" dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID	and emp.LOCID=loc.LOCATION_CODE and emp.active='1') as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";	
		 ResultSet rs=ad.selectQuery(getEmpdetails);
			while(rs.next())
			{
				EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
				emp.setLoginName(rs.getString("Emp_FullName"));
				emp.setEmployeeNumber(rs.getString("PERNR"));
				emp.setFirstName(rs.getString("Emp_FullName"));
				emp.setDesignation(rs.getString("DSGSTXT"));
				emp.setDepartment(rs.getString("DPTSTXT"));
				emp.setLocation(rs.getString("LOCATION_CODE"));
				emp.setEmailid(rs.getString("EMAIL_ID"));
				emp.setContactNo(rs.getString("TEL_EXTENS"));
				emp.setIpPhoneNo(rs.getString("IP_PHONE"));
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
		return mapping.findForward("EmployeeOfficailInfoList");
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			
			String keyword=empForm.getSearchEmployee();
			ArrayList empList=new ArrayList();
			System.out.println("Start Record="+empForm.getStartRecord());
			System.out.println("End record="+empForm.getEndRecord());
			System.out.println("Total Record="+empForm.getTotalRecords());	
			
			int totalRecords=empForm.getTotalRecords();//21
			int endRecord=empForm.getStartRecord()-1;//20
			int startRecord=empForm.getStartRecord()-10;//11
			
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}
			empForm.setTotalRecords(totalRecords);
			empForm.setStartRecord(1);
			empForm.setEndRecord(10);
			  
		
			String getEmpdetails="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY emp.PERNR) AS  RowNum,emp.PERNR,emp.Emp_FullName,loc.LOCATION_CODE,desg.DSGSTXT," +
			"dept.DPTSTXT,emp.EMAIL_ID,emp.TEL_EXTENS,emp.IP_PHONE from emp_official_info as emp,Location as loc,designation as desg,department as  dept where " +
			"(emp.PERNR like'%"+keyword+"%' or   emp.EMP_FULLNAME like'%"+keyword+"%' or emp.DPTID like'%"+keyword+"%' or emp.DSGID like'%"+keyword+"%' or " +
			"emp.EMAIL_ID  like'%"+keyword+"%' or emp.TEL_EXTENS like'%"+keyword+"%' or emp.IP_PHONE like'%"+keyword+"%'  or dept.DPTSTXT like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' or loc.LOCATION_CODE='"+keyword+"' ) and " +
			" dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID	and emp.LOCID=loc.LOCATION_CODE and emp.active='1') as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";
			ResultSet rs=ad.selectQuery(getEmpdetails);
			while(rs.next())
			{
				EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
				emp.setLoginName(rs.getString("Emp_FullName"));
				emp.setEmployeeNumber(rs.getString("PERNR"));
				emp.setFirstName(rs.getString("Emp_FullName"));
				emp.setDesignation(rs.getString("DSGSTXT"));
				emp.setDepartment(rs.getString("DPTSTXT"));
				emp.setLocation(rs.getString("LOCATION_CODE"));
				emp.setEmailid(rs.getString("EMAIL_ID"));
				emp.setContactNo(rs.getString("TEL_EXTENS"));
				emp.setIpPhoneNo(rs.getString("IP_PHONE"));
				empList.add(emp);
			}
			request.setAttribute("employeeList", empList);
			
			empForm.setTotalRecords(totalRecords);
			empForm.setStartRecord(startRecord);
			empForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(empList.size()<10)
				{
					empForm.setStartRecord(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("EmployeeOfficailInfoList");
	}
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			int totalRecords=empForm.getTotalRecords();//21
			int startRecord=empForm.getStartRecord();//11
			int endRecord=empForm.getEndRecord();
			
			String keyword=empForm.getSearchEmployee();
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
			
				
				String getEmpdetails="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY emp.PERNR) AS  RowNum,emp.PERNR,emp.Emp_FullName,loc.LOCATION_CODE,desg.DSGSTXT," +
				"dept.DPTSTXT,emp.EMAIL_ID,emp.TEL_EXTENS,emp.IP_PHONE from emp_official_info as emp,Location as loc,designation as desg,department as  dept where " +
				"(emp.PERNR like'%"+keyword+"%' or   emp.EMP_FULLNAME like'%"+keyword+"%' or emp.DPTID like'%"+keyword+"%' or emp.DSGID like'%"+keyword+"%' or " +
				"emp.EMAIL_ID  like'%"+keyword+"%' or emp.TEL_EXTENS like'%"+keyword+"%' or emp.IP_PHONE like'%"+keyword+"%' or dept.DPTSTXT like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' or loc.LOCATION_CODE='"+keyword+"' ) and " +
				" dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID	and emp.LOCID=loc.LOCATION_CODE and emp.active='1') as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'   ";
				ResultSet rs=ad.selectQuery(getEmpdetails);
				while(rs.next())
				{
					EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
					emp.setLoginName(rs.getString("Emp_FullName"));
					emp.setEmployeeNumber(rs.getString("PERNR"));
					emp.setFirstName(rs.getString("Emp_FullName"));
					emp.setDesignation(rs.getString("DSGSTXT"));
					emp.setDepartment(rs.getString("DPTSTXT"));
					emp.setLocation(rs.getString("LOCATION_CODE"));
					emp.setEmailid(rs.getString("EMAIL_ID"));
					emp.setContactNo(rs.getString("TEL_EXTENS"));
					emp.setIpPhoneNo(rs.getString("IP_PHONE"));
					empList.add(emp);
				}
				request.setAttribute("employeeList", empList);
				
			}
			
			
			System.out.println("list length="+empList.size());
			
			 if(empList.size()!=0)
				{
				 empForm.setTotalRecords(totalRecords);
				 empForm.setStartRecord(startRecord);
				 empForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					empForm.setTotalRecords(totalRecords);
					empForm.setStartRecord(start);
					empForm.setEndRecord(end);
					
				}
			 if(empList.size()<10)
			 {
				 empForm.setTotalRecords(totalRecords);
				 empForm.setStartRecord(startRecord);
				 empForm.setEndRecord(startRecord+empList.size()-1);
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
		return mapping.findForward("EmployeeOfficailInfoList");
	}
	
	
	public ActionForward searchContacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		EmpOfficalInformationForm contacForm=(EmpOfficalInformationForm)form;
		LoginDao ad=new LoginDao();
		try{
		
			LinkedList listOfMaterialCode=new LinkedList();
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			 HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			contacForm.setLocationIdList(locationList);
			contacForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			contacForm.setDepartmentList(deparmentList);
		
			String locationId=contacForm.getLocationId();
			String department=contacForm.getDepartment();
			String firstName=contacForm.getFirstName();
			String lastName=contacForm.getLastName();
			String emailID=contacForm.getEmailID();
			ArrayList contactList=new ArrayList();
			EmpOfficalInformationForm emp=null;
			
			
			String getContactList="Select  u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code,desg.DSGSTXT " +
					" from emp_official_info as u,Location as loc,department as dept,designation as desg where u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DPTID and u.active='1' ";
		if(!locationId.equalsIgnoreCase(""))
		{
			getContactList=getContactList+"and u.LOCID= '"+locationId+"'";
		}
		if(!department.equalsIgnoreCase(""))
		{
			getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
		}
		if(!firstName.equalsIgnoreCase(""))
		{
			getContactList=getContactList+"and  u.EMP_FULLNAME like'%"+firstName+"%'";
		}
		
		
		getContactList=getContactList;
		
			ResultSet rs=ad.selectQuery(getContactList);
			while(rs.next())
			{
				emp=new EmpOfficalInformationForm();
				emp.setEmployeeNumber(rs.getString("PERNR"));
				emp.setFirstName(rs.getString("EMP_FULLNAME"));
				emp.setDesignation(rs.getString("DSGSTXT"));
				emp.setDepartment(rs.getString("DPTSTXT"));
				emp.setLocationId(rs.getString("location_code"));
				contactList.add(emp);
			}			
		
			request.setAttribute("userDetails", contactList);
			
			if(contactList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				
				contacForm.setMessage("No Contacts");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayUsers");
	}
	
	
	
	public ActionForward displayListUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		
		String userName=request.getParameter("uId");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		UserDao ad=new UserDao();
		
	
		String plantID=user.getPlantId();
		
		
		try{
			
			LinkedList listOfMaterialCode=new LinkedList();
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			 
				
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location"); 
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			empForm.setLocationIdList(locationList);
			empForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			empForm.setDepartmentList(deparmentList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	
		try{
			
		
			EmpOfficalInformationForm empForm1=null;
			
			ArrayList a1=new ArrayList();
			/*String sql="select * from users where Activated='On'";
			ResultSet rs=ad.selectQuery(sql);
			while(rs.next()) {
				empForm1=new EmpOfficalInformationForm();
				empForm1.setUserName(rs.getString("username"));
				empForm1.setFullName(rs.getString("fullname"));
				
				//empForm1.setGroupName(rs.getString("groupname"));
				
				a1.add(empForm1);
			}
			
			request.setAttribute("userDetails", a1);*/
			
		}catch(Exception se){
			se.printStackTrace();
		}
		
		return mapping.findForward("displayUsers");
	}
	
	
	public ActionForward searchForApprovers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	int userID=user.getId();
	EmpOfficalInformationForm userRightsForm=(EmpOfficalInformationForm)form;
	NewsandMediaDao ad=new NewsandMediaDao();
	String sTxt = request.getParameter("searchText");

	userRightsForm.setSearchText(sTxt);
	MailInboxAction mAction = new MailInboxAction();
	LinkedList searchList=new LinkedList();
	try{
		String searchQuery="select * from emp_official_info where EMP_FULLNAME like '%"+sTxt+"%' or EMAIL_ID like '%"+sTxt+"%' or PERNR like'%"+sTxt+"%'";
		ResultSet rs=ad.selectQuery(searchQuery);
	 int i=1;
		while(rs.next()) {
			
			EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
			emp.setAppr(rs.getString("EMP_FULLNAME")+"-"+rs.getString("PERNR"));
		   searchList.add(emp);
		  if(i==4)
		  {
			  break;
		  }
		i++;
		}
		
	}
	
	catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
	
	request.setAttribute("SearchUserDetails", searchList);
	
	
	return mapping.findForward("searchApprovers");
	}
	


	
	public ActionForward setLeaveType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		try{
			
			LinkedList leaveTypeID=new LinkedList();
			LinkedList leaveType=new LinkedList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("EmployeeOfficailInfoList");
	}
	
	public ActionForward serchEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
		
		String keyword=empForm.getSearchEmployee();
		ArrayList empList=new ArrayList();
		String getCount="select count(*) from emp_official_info as emp,Location as loc,department as dept,designation as desg where  " +
		"emp.LOCID=loc.location_code  and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID and emp.active='1' and (emp.PERNR like'%"+keyword+"%' or   emp.EMP_FULLNAME like'%"+keyword+"%' or " +
		"emp.DPTID like'%"+keyword+"%' or emp.DSGID like'%"+keyword+"%' or emp.EMAIL_ID  like'%"+keyword+"%'  or dept.DPTSTXT like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' or loc.LOCATION_CODE='"+keyword+"')";
		int  totalRecords=0;
		int  startRecord=0;
		int  endRecord=0;
					ResultSet rsCount=ad.selectQuery(getCount);
					while(rsCount.next())
					{
						totalRecords=rsCount.getInt(1);	
					}
					
					 if(totalRecords>10)
					  {
						 empForm.setTotalRecords(totalRecords);
					  startRecord=1;
					  endRecord=10;
					  empForm.setStartRecord(1);
					  empForm.setEndRecord(10);
					  request.setAttribute("displayRecordNo", "displayRecordNo");
					  request.setAttribute("nextButton", "nextButton");
					  }else
					  {
						  startRecord=1;
						  endRecord=totalRecords;
						  empForm.setTotalRecords(totalRecords);
						  empForm.setStartRecord(1);
						  empForm.setEndRecord(totalRecords); 
					  }
		String getEmpdetails="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY emp.PERNR) AS  RowNum,emp.PERNR,emp.Emp_FullName,loc.LOCATION_CODE,desg.DSGSTXT," +
		"dept.DPTSTXT,emp.EMAIL_ID,emp.TEL_EXTENS,emp.IP_PHONE from emp_official_info as emp,Location as loc,designation as desg,department as  dept where  emp.active='1' and " +
		"(emp.PERNR like'%"+keyword+"%' or   emp.EMP_FULLNAME like'%"+keyword+"%' or emp.DPTID like'%"+keyword+"%' or emp.DSGID like'%"+keyword+"%' or " +
		"emp.EMAIL_ID  like'%"+keyword+"%' or emp.TEL_EXTENS like'%"+keyword+"%' or emp.IP_PHONE like'%"+keyword+"%'  or dept.DPTSTXT like'%"+keyword+"%' or desg.DSGSTXT like'%"+keyword+"%' or loc.LOCATION_CODE='"+keyword+"' ) and " +
		" dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID	and emp.LOCID=loc.LOCATION_CODE ) as sub Where  sub.RowNum between  1 and 10  ";
		ResultSet rs=ad.selectQuery(getEmpdetails);
		while(rs.next())
		{
			EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
			emp.setLoginName(rs.getString("Emp_FullName"));
			emp.setEmployeeNumber(rs.getString("PERNR"));
			emp.setFirstName(rs.getString("Emp_FullName"));
			emp.setDesignation(rs.getString("DSGSTXT"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setLocation(rs.getString("LOCATION_CODE"));
			emp.setEmailid(rs.getString("EMAIL_ID"));
			emp.setContactNo(rs.getString("TEL_EXTENS"));
			emp.setIpPhoneNo(rs.getString("IP_PHONE"));
	
			empList.add(emp);
		}
		request.setAttribute("employeeList", empList);
		rs.close();
		ad.connClose();
		if(empList.size()==0)
		{
			request.setAttribute("noRecords", "noRecords");
			empForm.setMessage("No Employee Found");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("EmployeeOfficailInfoList");
		
	}
	
	
	
	
	public ActionForward modifyEmployeeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		try{
			
			
			String dateofJoin=empForm.getDateofJoining();
			String a[]=dateofJoin.split("/");
			dateofJoin=a[2]+"-"+a[1]+"-"+a[0];
			String dateofConf=empForm.getDateofConformation();
			if(dateofConf.equalsIgnoreCase(""))
			{
				dateofConf="";
			}else{
			String b[]=dateofConf.split("/");
			dateofConf=b[2]+"-"+b[1]+"-"+b[0];
			}
			String dateOfleav=empForm.getDateofLeaving();
		if(!dateOfleav.equalsIgnoreCase("")){
				String c[]=dateOfleav.split("/");
				dateOfleav=c[2]+"-"+c[1]+"-"+c[0];
		}else{
			dateOfleav="";
		}
		String dob=empForm.getDob();
		String d[]=dob.split("/");
		dob=d[2]+"-"+d[1]+"-"+d[0];
		
		String ipPhone=empForm.getIpPhoneNo();
        	String modifyEmpOfcDetails ="update emp_official_info set Emp_FullName='"+empForm.getEmployeeName()+"',Sex='"+empForm.getGender()+"'," +
        	"BUKRS ='"+empForm.getCompanyName()+"' ,WERKS='"+empForm.getLocation()+"',pay_group='"+empForm.getPayGroup()+"',STAFFCAT='"+empForm.getEmployeeCategory()+"'," +
        	"RPTMGR='"+empForm.getReportingManger()+"',ROOM='"+empForm.getRoom()+"',APPMGR='"+empForm.getApprovalManger()+"',FLOOR='"+empForm.getFloor()+"'," +
        	"BUILDING='"+empForm.getBuilding()+"',DOJ = '"+dateofJoin+"',LAND1='"+empForm.getCounID()+"'," +
        	"STATE='"+empForm.getState()+"',LOCID='"+empForm.getPlant()+"',GRDID='"+empForm.getGradeID()+"',DSGID='"+empForm.getDesignation()+"'," +
        	"DPTID='"+empForm.getDepartment()+"',EMAIL_ID='"+empForm.getEmailid()+"',TEL_NO='"+empForm.getTelNo()+"',TEL_EXTENS='"+empForm.getExtnNo()+"'," +
        	" ESI='"+empForm.getEligibleforESIDeduction()+"',ESINO='"+empForm.getEsiNumber()+"',PF='"+empForm.getEligibleforPFDeduction()+"'," +
        	"PFNO='"+empForm.getPfNumber()+"',PT='"+empForm.getEligibleforPTDeduction()+"',IT='"+empForm.getEligibleforITDeduction()+"',PANNO='"+empForm.getPanNo()+"'," +
        	"BONUS ='"+empForm.getBonus()+"',LEAVES ='"+empForm.getLeaves()+"',WAERS ='"+empForm.getSalaryCurrency()+"',payment_method ='"+empForm.getPaymentMethod()+"'," +
        	"BANKID ='"+empForm.getBankName()+"',BACCTYP ='"+empForm.getAccountType()+"',BACCNO ='"+empForm.getAccountNumber()+"',BRANCH ='"+empForm.getBranchName()+"'," +
        	"IFSC_CODE ='"+empForm.getIfsCCode()+"',MICR_CODE  ='"+empForm.getMicrCode()+"',IP_PHONE='"+empForm.getIpPhoneNo()+"',DOB='"+dob+"',UANNO='"+empForm.getUanno()+"'   ";
			
        	if(!dateofConf.equalsIgnoreCase("") && !dateOfleav.equalsIgnoreCase("")){
        		
        		modifyEmpOfcDetails=modifyEmpOfcDetails + " ,DOC='"+dateofConf+"',DOL='"+dateOfleav+"' where PERNR ='"+empForm.getReqEmpNo()+"' " ;
        	}else{
		        	if(dateofConf.equalsIgnoreCase("") && dateOfleav.equalsIgnoreCase("")){
		        		
		        		modifyEmpOfcDetails=modifyEmpOfcDetails + "  where PERNR ='"+empForm.getReqEmpNo()+"' " ;
		        	}
		        	
		        	if(!dateofConf.equalsIgnoreCase("")){
		        		
		        		modifyEmpOfcDetails=modifyEmpOfcDetails+",DOC='"+dateofConf+"' where PERNR ='"+empForm.getReqEmpNo()+"'";
		        	}
		        	if(!dateOfleav.equalsIgnoreCase("")){
		        		
		        		modifyEmpOfcDetails=modifyEmpOfcDetails+",DOL='"+dateOfleav+"' where PERNR ='"+empForm.getReqEmpNo()+"'";
		        	}
        	
        	}
        	System.out.println(modifyEmpOfcDetails);
        	int i=ad.SqlExecuteUpdate(modifyEmpOfcDetails);
        	if(i>0)
        	{
        		try{
		    		empForm.setMessage("Data Updated Successfully");
		    		String reqEmpNo=empForm.getReqEmpNo();
		    		empForm.setReqEmpNo(reqEmpNo);
		    		request.setAttribute("ModifyButton", "ModifyButton");
		    		
		    		String casOpBal=empForm.getOpeningBalence1();
		    		String casCloBal=empForm.getClosingBalence1();
		    		
		    		String sickOpBal=empForm.getOpeningBalence2();
		    		String sickCloBal=empForm.getClosingBalence2();
		    		
		    		String plOpBal=empForm.getOpeningBalence3();
		    		String plCloBal=empForm.getClosingBalence3();
		    		
					String maternityLeaveType=empForm.getOpeningBalence4();
					
					/*String deleteLvDetails="delete from lv_type_d where lv_empcode='"+empForm.getEmployeeNumber()+"'";
					int test=ad.SqlExecuteUpdate(deleteLvDetails);*/
					
					if(!casOpBal.equalsIgnoreCase("")&&!casCloBal.equalsIgnoreCase(""))
					{
					String updateCL="update lv_type_d set lv_opbal='"+empForm.getOpeningBalence1()+"',lv_clbal='"+empForm.getClosingBalence1()+"',lv_availed='"+empForm.getAvailedBal1()+"',lv_awtBal='"+empForm.getAwaitingBal1()+"'  where lv_empcode='"+empForm.getEmployeeNumber()+"' and lv_typeid=1";	
					int check=ad.SqlExecuteUpdate(updateCL);
					if(check==0){
					String insertCL="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_availed,lv_clbal,lv_awtBal) " +
							"values('1','"+empForm.getYear()+"','"+empForm.getEmployeeNumber()+"','"+empForm.getOpeningBalence1()+"','"+empForm.getAvailedBal1()+"','"+empForm.getClosingBalence1()+"','"+empForm.getAwaitingBal1()+"')";
					ad.SqlExecuteUpdate(insertCL);
					}
					}
					if(!sickOpBal.equalsIgnoreCase("")&&!sickCloBal.equalsIgnoreCase(""))
					{
						String updateSK="update lv_type_d set lv_opbal='"+empForm.getOpeningBalence2()+"',lv_clbal='"+empForm.getClosingBalence2()+"',lv_availed='"+empForm.getAvailedBal2()+"',lv_awtBal='"+empForm.getAwaitingBal2()+"'  where lv_empcode='"+empForm.getEmployeeNumber()+"' and lv_typeid=2";	
						int check=ad.SqlExecuteUpdate(updateSK);
						if(check==0){
					String insertSK="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_availed,lv_clbal,lv_awtBal) " +
							"values('2','"+empForm.getYear()+"','"+empForm.getEmployeeNumber()+"','"+empForm.getOpeningBalence2()+"','"+empForm.getAvailedBal2()+"','"+empForm.getClosingBalence2()+"','"+empForm.getAwaitingBal2()+"')";
					ad.SqlExecuteUpdate(insertSK);
						}
					}
					if(!plOpBal.equalsIgnoreCase("")&&!plCloBal.equalsIgnoreCase(""))
					{
						String updatePL="update lv_type_d set lv_opbal='"+empForm.getOpeningBalence3()+"',lv_clbal='"+empForm.getClosingBalence3()+"',lv_availed='"+empForm.getAvailedBal3()+"',lv_awtBal='"+empForm.getAwaitingBal3()+"'  where lv_empcode='"+empForm.getEmployeeNumber()+"' and lv_typeid=3";	
						int check=ad.SqlExecuteUpdate(updatePL);
						if(check==0){
					String insertSK="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_availed,lv_clbal,lv_awtBal) " +
							"values('3','"+empForm.getYear()+"','"+empForm.getEmployeeNumber()+"','"+empForm.getOpeningBalence3()+"','"+empForm.getAvailedBal3()+"','"+empForm.getClosingBalence3()+"','"+empForm.getAwaitingBal3()+"')";
					ad.SqlExecuteUpdate(insertSK);
						}
					}
					if(!maternityLeaveType.equalsIgnoreCase(""))
					{
						String updatePL="update lv_type_d set lv_opbal='"+empForm.getOpeningBalence4()+"',lv_clbal='"+empForm.getClosingBalence4()+"',lv_availed='"+empForm.getAvailedBal4()+"',lv_awtBal='"+empForm.getAwaitingBal4()+"'  where lv_empcode='"+empForm.getEmployeeNumber()+"' and lv_typeid=5";	
						int check=ad.SqlExecuteUpdate(updatePL);
						if(check==0){
					String insertSK="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_availed,lv_clbal,lv_awtBal) " +
							"values('5','"+empForm.getYear()+"','"+empForm.getEmployeeNumber()+"','"+empForm.getOpeningBalence4()+"','"+empForm.getAvailedBal4()+"','"+empForm.getClosingBalence4()+"','"+empForm.getAwaitingBal4()+"')";
					ad.SqlExecuteUpdate(insertSK);
						}
					}
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        	}else{
        		empForm.setMessage("Error...Data is not updated");	
        		empForm.setSaveType("Modify");
        		request.setAttribute("ModifyButton", "ModifyButton");
        	}
			ResultSet rs5 = ad.selectQuery("select LOCID,LOCNAME,LOCATION_CODE from Location");
			ArrayList locIDList=new ArrayList();
			ArrayList locNameList=new ArrayList();
			while(rs5.next()) {
				locIDList.add(rs5.getString("LOCATION_CODE"));
				locNameList.add(rs5.getString("LOCATION_CODE")+"-"+rs5.getString("LOCNAME"));
			}
			empForm.setLocIDList(locIDList);
			empForm.setLocNameList(locNameList);
			ResultSet rs7 = ad.selectQuery("select  pay_id,pay_method from PAYMODE");
			ArrayList payIDList=new ArrayList();
			ArrayList payNameList=new ArrayList();
				
			while(rs7.next()) {
				payIDList.add(rs7.getString("pay_id"));
				payNameList.add(rs7.getString("pay_method"));
			}
			empForm.setPayIDList(payIDList);
			empForm.setPayNameList(payNameList);
			
				
			ResultSet rs11 = ad.selectQuery("select  WERKS,NAME1 from Plant");
			ArrayList plantNameList=new ArrayList();
			ArrayList plantIDList=new ArrayList();
				
			while(rs11.next()) {
				plantIDList.add(rs11.getString("WERKS"));
				plantNameList.add(rs11.getString("WERKS")+"-"+rs11.getString("NAME1"));
			}
			empForm.setPlantNameList(plantNameList);
			empForm.setPlantIDList(plantIDList);
			
			ResultSet rs14 = ad.selectQuery("select GRDID,GRDTXT from Grade");
			ArrayList gradeidList = new ArrayList();
			ArrayList gradetxtList=new ArrayList();
			while(rs14.next()){
				gradeidList.add(rs14.getString("GRDID"));
				gradetxtList.add(rs14.getString("GRDTXT"));
				
			}
			
		
			empForm.setGradeidList(gradeidList);
			empForm.setGradetxtList(gradetxtList);
			
			
			ResultSet rs9 = ad.selectQuery("select  WAERS,ISOCD from Currency order by ISOCD");
			ArrayList currencyIDList=new ArrayList();
			ArrayList currencyNameList=new ArrayList();
				
			while(rs9.next()) {
				currencyIDList.add(rs9.getString("WAERS"));
				currencyNameList.add(rs9.getString("ISOCD"));
			}
			empForm.setCurrencyIDList(currencyIDList);
			empForm.setCurrencyNameList(currencyNameList);
			
			ResultSet rs10 = ad.selectQuery("select  BRANCH,BANKID,BNAME from Bank");
			ArrayList bankIDList=new ArrayList();
			ArrayList banknameList=new ArrayList();
				
			while(rs10.next()) {
				bankIDList.add(rs10.getString("BANKID"));
				banknameList.add(rs10.getString("BNAME")+","+rs10.getString("BRANCH"));
			}
			empForm.setBankIDList(bankIDList);
			empForm.setBanknameList(banknameList);
			
			LinkedList listofPayGroup= new LinkedList();
			ResultSet rs12= ad.selectQuery("select Paygroup,Short_desc from Paygroup_Master");
			ArrayList  paygroupList = new ArrayList();
			ArrayList  paytestList=new ArrayList();
			while(rs12.next()){
				paygroupList.add(rs12.getString("Paygroup"));
				paytestList.add(rs12.getString("Short_desc"));
			}
			empForm.setPaygroupList(paygroupList);
			empForm.setPaystextList(paytestList);
			
			/////////////
			String countryId=empForm.getCounID();
			LinkedList stateID=new LinkedList();
			LinkedList stateName=new LinkedList();
			String getStateDetails="select * from State where LAND1='"+countryId+"'";
			ResultSet rsState=ad.selectQuery(getStateDetails);
			while(rsState.next())
			{
				stateID.add(rsState.getString("BLAND"));
				stateName.add(rsState.getString("BEZEI"));
			}
			
			
			
			empForm.setStateID(stateID);
			empForm.setStateName(stateName);
			request.setAttribute("diplayStates", "diplayStates");
			
			
			String getCountryDetails="select LAND1,LANDX from Country order by LANDX";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LANDX"));
			}

			empForm.setCountryID(countryID);
			empForm.setCountry(countryName);
				
				
				String getDesignationDetails="select DSGID,DSGSTXT from Designation order by DSGSTXT";
				LinkedList desgnIDList=new LinkedList();
				LinkedList desgnTXTList=new LinkedList();
				ResultSet rsDesignationDetails=ad.selectQuery(getDesignationDetails);
				while(rsDesignationDetails.next()){
					desgnIDList.add(rsDesignationDetails.getString("DSGID"));
					desgnTXTList.add(rsDesignationDetails.getString("DSGSTXT"));
					
				}
				
				empForm.setDesgnIDList(desgnIDList);
				empForm.setDesgnTXTList(desgnTXTList);
				

				String getdepartmentDetails="select DPTID,DPTSTXT from Department order by DPTSTXT";
				LinkedList departIDList=new LinkedList();
				LinkedList departTXTList= new LinkedList();
				ResultSet rsdepartmentDetails=ad.selectQuery(getdepartmentDetails);
				while(rsdepartmentDetails.next()){
					departIDList.add(rsdepartmentDetails.getString("DPTID"));
					departTXTList.add(rsdepartmentDetails.getString("DPTSTXT"));
					
				}
			
				empForm.setDepartIDList(departIDList);
				empForm.setDepartTXTList(departTXTList);
				
				
				
				ResultSet rs13 =ad.selectQuery("select STAFFCAT,CATSTEXT from Category");
				    ArrayList empCatogeryList = new ArrayList();
				    ArrayList empCatogerytextList=new ArrayList();
				    while(rs13.next()){
				    	
				    	 empCatogeryList.add(rs13.getString("STAFFCAT"));
				    	 empCatogerytextList.add(rs13.getString("CATSTEXT"));
				    	
				    }
				    empForm.setEmpCatogeryList(empCatogeryList);
				    empForm.setEmpCatogerytextList(empCatogerytextList);
				    
					
					ResultSet rs8 = ad.selectQuery("select  BUKRS,BUTXT from Company");
					ArrayList companyIDList=new ArrayList();
					ArrayList companyNameList=new ArrayList();
						
					while(rs8.next()) {
						companyIDList.add(rs8.getString("BUKRS"));
						companyNameList.add(rs8.getString("BUTXT")+"-"+rs8.getString("BUKRS"));
					}
					empForm.setCompanyIDList(companyIDList);
					empForm.setCompanyNameList(companyNameList);
					
					///Update//
					
			
					
		   }catch (Exception e) {
		  e.printStackTrace();
		   }
		empForm.setSaveType("Modify");
		return mapping.findForward("displayEmployee"); 
			
	}
	
	public ActionForward editEmployeeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		EssDao ad=new EssDao();
		try{
			
			
			String empNo=request.getParameter("EmpNo");
			empForm.setReportingManger(empNo);
			empForm.setReqEmpNo(empNo);
			String getEmpDetails="select *  from emp_official_info where  PERNR='"+empNo+"' ";
			
			ResultSet rs=ad.selectQuery(getEmpDetails);
			while(rs.next()){
				empForm.setEmployeeName(rs.getString("Emp_FullName"));
				empForm.setGender(rs.getString("Sex"));
				empForm.setEmployeeNumber(rs.getString("PERNR"));
				empForm.setCompanyName(rs.getString("BUKRS"));
				empForm.setPlant(rs.getString("LOCID"));
				empForm.setEmployeeName(rs.getString("Emp_FullName"));
				empForm.setPayGroup(rs.getString("pay_group"));
				empForm.setEmployeeCategory(rs.getString("STAFFCAT"));
				empForm.setReportingManger(rs.getString("RPTMGR"));
			//	empForm.setHeadOfDepartment(rs.getString("HOD"));
				empForm.setRoom(rs.getString("ROOM"));
				empForm.setApprovalManger(rs.getString("APPMGR"));
				empForm.setFloor(rs.getString("FLOOR"));
				empForm.setBuilding(rs.getString("BUILDING"));
				empForm.setIpPhoneNo(rs.getString("IP_PHONE"));
			//	empForm.setAddressTypeID(rs.getString("ADRTYP"));
				String doj=rs.getString("DOJ");
				String a[]=doj.split("-");
				doj=a[2]+"/"+a[1]+"/"+a[0];
				empForm.setDateofJoining(doj);
				//empForm.setDateofLeaving(EMicroUtils.display1(rs.getDate("DOL")));
				String dateOfleav=rs.getString("DOL");
				if(dateOfleav==null)
				{
					empForm.setDateofLeaving("");
					
				}else{
					String dol=rs.getString("DOL");
					String b[]=dol.split("-");
					dol=b[2]+"/"+b[1]+"/"+b[0];
					empForm.setDateofLeaving(dol);
				}
				String doc=rs.getString("DOC");
				if(doc==null){
				empForm.setDateofConformation("");	
				}else{
				String c[]=doc.split("-");
				doc=c[2]+"/"+c[1]+"/"+c[0];
				empForm.setDateofConformation(doc);
				}
				empForm.setDateofConformation(doc);
				empForm.setDob(EMicroUtils.display(rs.getDate("DOB")));
				empForm.setUanno("");
				String uanNo=rs.getString("UANNO");
				if(uanNo!=null){
					if(!uanNo.equals(""))
						empForm.setUanno(rs.getString("UANNO"));
				}
					
				empForm.setCounID(rs.getString("LAND1"));
				empForm.setState(rs.getString("STATE"));
				empForm.setLocation(rs.getString("WERKS"));
				empForm.setGradeID(rs.getString("GRDID"));
				empForm.setDesignation(rs.getString("DSGID"));
				empForm.setDepartment(rs.getString("DPTID"));
				empForm.setEmailid(rs.getString("EMAIL_ID"));
				empForm.setTelNo(rs.getString("TEL_NO"));
				empForm.setExtnNo(rs.getString("TEL_EXTENS"));
				//empForm.setHeadQuarters(rs.getString("head_quarter"));
				empForm.setEligibleforESIDeduction(rs.getString("ESI"));
				empForm.setEsiNumber(rs.getString("ESINO"));
				empForm.setEligibleforPFDeduction(rs.getString("PF"));
				empForm.setPfNumber(rs.getString("PFNO"));
				empForm.setEligibleforPTDeduction(rs.getString("PT"));
				empForm.setEligibleforITDeduction(rs.getString("IT"));
				empForm.setPanNo(rs.getString("PANNO"));
				empForm.setBonus(rs.getString("BONUS"));
				empForm.setLeaves(rs.getString("LEAVES"));
				empForm.setSalaryCurrency(rs.getString("WAERS"));
				empForm.setPaymentMethod(rs.getString("payment_method"));
				empForm.setBankName(rs.getString("BANKID"));
				empForm.setAccountType(rs.getString("BACCTYP"));
				empForm.setAccountNumber(rs.getString("BACCNO"));
				empForm.setBranchName(rs.getString("BRANCH"));
				empForm.setIfsCCode(rs.getString("IFSC_CODE"));
				empForm.setMicrCode(rs.getString("MICR_CODE"));
			//	empForm.setDepartment(rs.getString("DPTID"));
			//	empForm.setDepartIDList("DPTID");
				
				
			}
		rs.close();
		ad.connClose();
		
			String getCLeave="select * from lv_type_d where lv_empcode='"+empNo+"' and lv_typeid=1";
			ResultSet rsLeave=ad.selectQuery(getCLeave);
			while(rsLeave.next())
			{
				empForm.setYear(rsLeave.getString("lv_calyear"));
				empForm.setOpeningBalence1(rsLeave.getString("lv_opbal"));
				empForm.setClosingBalence1(rsLeave.getString("lv_clbal"));
				empForm.setAvailedBal1(rsLeave.getString("lv_availed"));
				empForm.setAwaitingBal1(rsLeave.getString("lv_awtBal"));
			}
			
			String getSLeaveDetails="select * from lv_type_d where lv_empcode='"+empNo+"' and lv_typeid=2";
			ResultSet rsSLeave=ad.selectQuery(getSLeaveDetails);
			while(rsSLeave.next())
			{
				empForm.setYear(rsSLeave.getString("lv_calyear"));
				empForm.setOpeningBalence2(rsSLeave.getString("lv_opbal"));
				empForm.setClosingBalence2(rsSLeave.getString("lv_clbal"));
				empForm.setAvailedBal2(rsSLeave.getString("lv_availed"));
				empForm.setAwaitingBal2(rsSLeave.getString("lv_awtBal"));
			}
			String getPLeaveDetails="select * from lv_type_d where lv_empcode='"+empNo+"' and lv_typeid=3";
			ResultSet rsPLeave=ad.selectQuery(getPLeaveDetails);
			while(rsPLeave.next())
			{
				empForm.setYear(rsPLeave.getString("lv_calyear"));
				empForm.setOpeningBalence3(rsPLeave.getString("lv_opbal"));
				empForm.setClosingBalence3(rsPLeave.getString("lv_clbal"));
				empForm.setAvailedBal3(rsPLeave.getString("lv_availed"));
				empForm.setAwaitingBal3(rsPLeave.getString("lv_awtBal"));
			}
			String getMLeaveDetails="select * from lv_type_d where lv_empcode='"+empNo+"' and lv_typeid=4";
			ResultSet rsMLeave=ad.selectQuery(getMLeaveDetails);
			while(rsMLeave.next())
			{
				empForm.setYear(rsMLeave.getString("lv_calyear"));
				empForm.setOpeningBalence4(rsMLeave.getString("lv_opbal"));
				empForm.setClosingBalence4(rsMLeave.getString("lv_clbal"));
				empForm.setAvailedBal4(rsMLeave.getString("lv_availed"));
				empForm.setAwaitingBal4(rsMLeave.getString("lv_awtBal"));
			}
			ad.connClose();
			ResultSet rs5 = ad.selectQuery("select LOCID,LOCNAME,LOCATION_CODE from Location");
			ArrayList locIDList=new ArrayList();
			ArrayList locNameList=new ArrayList();
				
			while(rs5.next()) {
				locIDList.add(rs5.getString("LOCATION_CODE"));
				locNameList.add(rs5.getString("LOCATION_CODE")+"-"+rs5.getString("LOCNAME"));
			}
			empForm.setLocIDList(locIDList);
			empForm.setLocNameList(locNameList);
			
			
			ResultSet rs7 = ad.selectQuery("select  pay_id,pay_method from PAYMODE");
			ArrayList payIDList=new ArrayList();
			ArrayList payNameList=new ArrayList();
				
			while(rs7.next()) {
				payIDList.add(rs7.getString("pay_id"));
				payNameList.add(rs7.getString("pay_method"));
			}
			empForm.setPayIDList(payIDList);
			empForm.setPayNameList(payNameList);
			
				
			ResultSet rs11 = ad.selectQuery("select  WERKS,NAME1 from Plant");
			ArrayList plantNameList=new ArrayList();
			ArrayList plantIDList=new ArrayList();
				
			while(rs11.next()) {
				plantIDList.add(rs11.getString("WERKS"));
				plantNameList.add(rs11.getString("WERKS")+"-"+rs11.getString("NAME1"));
			}
			empForm.setPlantNameList(plantNameList);
			empForm.setPlantIDList(plantIDList);
			
			ResultSet rs14 = ad.selectQuery("select GRDID,GRDTXT from Grade");
			ArrayList gradeidList = new ArrayList();
			ArrayList gradetxtList=new ArrayList();
			while(rs14.next()){
				
				gradeidList.add(rs14.getString("GRDID"));
				gradetxtList.add(rs14.getString("GRDTXT"));
				
			}
			
		
			empForm.setGradeidList(gradeidList);
			empForm.setGradetxtList(gradetxtList);
			
			
			ResultSet rs9 = ad.selectQuery("select  WAERS,ISOCD from Currency order by ISOCD");
			ArrayList currencyIDList=new ArrayList();
			ArrayList currencyNameList=new ArrayList();
				
			while(rs9.next()) {
				currencyIDList.add(rs9.getString("WAERS"));
				currencyNameList.add(rs9.getString("ISOCD"));
			}
			empForm.setCurrencyIDList(currencyIDList);
			empForm.setCurrencyNameList(currencyNameList);
			
			ResultSet rs10 = ad.selectQuery("select  BANKID,BNAME,BRANCH from Bank");
			ArrayList bankIDList=new ArrayList();
			ArrayList banknameList=new ArrayList();
				
			while(rs10.next()) {
				bankIDList.add(rs10.getString("BANKID"));
				banknameList.add(rs10.getString("BNAME")+","+rs10.getString("BRANCH"));
			}
			empForm.setBankIDList(bankIDList);
			empForm.setBanknameList(banknameList);
			ad.connClose();
			LinkedList listofPayGroup= new LinkedList();
			ResultSet rs12= ad.selectQuery("select Paygroup,Short_desc from Paygroup_Master");
			ArrayList  paygroupList = new ArrayList();
			ArrayList  paytestList=new ArrayList();
			while(rs12.next()){
				paygroupList.add(rs12.getString("Paygroup"));
				paytestList.add(rs12.getString("Short_desc"));
			}
			empForm.setPaygroupList(paygroupList);
			empForm.setPaystextList(paytestList);
			/////////////
			String countryId=empForm.getCounID();
			LinkedList stateID=new LinkedList();
			LinkedList stateName=new LinkedList();
			String getStateDetails="select * from State where LAND1='"+countryId+"' ";
			ResultSet rsState=ad.selectQuery(getStateDetails);
			while(rsState.next())
			{
				stateID.add(rsState.getString("BLAND"));
				stateName.add(rsState.getString("BEZEI"));
			}
			request.setAttribute("diplayStates", "diplayStates");
			
			
			empForm.setStateID(stateID);
			empForm.setStateName(stateName);
			
			
			String getCountryDetails="select LAND1,LANDX from Country order by LANDX";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LANDX"));
			}

			empForm.setCountryID(countryID);
			empForm.setCountry(countryName);
				
				
				String getDesignationDetails="select DSGID,DSGSTXT from Designation order by DSGSTXT";
				LinkedList desgnIDList=new LinkedList();
				LinkedList desgnTXTList=new LinkedList();
				ResultSet rsDesignationDetails=ad.selectQuery(getDesignationDetails);
				while(rsDesignationDetails.next()){
					desgnIDList.add(rsDesignationDetails.getString("DSGID"));
					desgnTXTList.add(rsDesignationDetails.getString("DSGSTXT"));
					
				}
				
				empForm.setDesgnIDList(desgnIDList);
				empForm.setDesgnTXTList(desgnTXTList);
				ad.connClose();

				String getdepartmentDetails="select DPTID,DPTSTXT from Department order by DPTSTXT";
				LinkedList departIDList=new LinkedList();
				LinkedList departTXTList= new LinkedList();
				ResultSet rsdepartmentDetails=ad.selectQuery(getdepartmentDetails);
				while(rsdepartmentDetails.next()){
					departIDList.add(rsdepartmentDetails.getString("DPTID"));
					departTXTList.add(rsdepartmentDetails.getString("DPTSTXT"));
					
				}
			
				empForm.setDepartIDList(departIDList);
				empForm.setDepartTXTList(departTXTList);
				
				
				
				ResultSet rs13 =ad.selectQuery("select STAFFCAT,CATSTEXT from Category");
				    ArrayList empCatogeryList = new ArrayList();
				    ArrayList empCatogerytextList=new ArrayList();
				    while(rs13.next()){
				    	
				    	 empCatogeryList.add(rs13.getString("STAFFCAT"));
				    	 empCatogerytextList.add(rs13.getString("CATSTEXT"));
				    	
				    }
				    empForm.setEmpCatogeryList(empCatogeryList);
				    empForm.setEmpCatogerytextList(empCatogerytextList);
				    
					
					ResultSet rs8 = ad.selectQuery("select  BUKRS,BUTXT from Company");
					ArrayList companyIDList=new ArrayList();
					ArrayList companyNameList=new ArrayList();
						
					while(rs8.next()) {
						companyIDList.add(rs8.getString("BUKRS"));
						companyNameList.add(rs8.getString("BUTXT")+"-"+rs8.getString("BUKRS"));
					}
					empForm.setCompanyIDList(companyIDList);
					empForm.setCompanyNameList(companyNameList);
			
					ad.connClose();
		
		
			}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		request.setAttribute("modifyDetails", "modifyDetails");
		empForm.setSaveType("Modify");
		request.setAttribute("ModifyButton", "ModifyButton");
		return mapping.findForward("displayEmployee"); 
		
		
	
	}

	
	public ActionForward displayEmpOfficalInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
				
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
	
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try{
			
			List empList=new ArrayList();
			String keyword=empForm.getSearchEmployee();
			
			  
			  String getCount="select count(*) from emp_official_info as emp,Location as loc,department as dept,designation as desg where  " +
				"emp.LOCID=loc.location_code  and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID and emp.active='1'";
			  int totalRecords=empForm.getTotalRecords();//21
				int startRecord=empForm.getStartRecord();//11
				int endRecord=empForm.getEndRecord();	
				
				
				if(totalRecords>10){
					  startRecord=1;
					  endRecord=10;
					  empForm.setTotalRecords(totalRecords);
					  empForm.setStartRecord(startRecord);
					  empForm.setEndRecord(10);
					  }
					  else{
						  startRecord=1;
						  empForm.setTotalRecords(totalRecords);
						  empForm.setStartRecord(startRecord);
						  empForm.setEndRecord(totalRecords);  
					  }
							ResultSet rsCount=ad.selectQuery(getCount);
							while(rsCount.next())
							{
								totalRecords=rsCount.getInt(1);	
							}
							
							 if(totalRecords>10)
							  {
								 empForm.setTotalRecords(totalRecords);
							  startRecord=1;
							  endRecord=10;
							  empForm.setStartRecord(1);
							  empForm.setEndRecord(10);
							  request.setAttribute("displayRecordNo", "displayRecordNo");
							  request.setAttribute("nextButton", "nextButton");
							  }else
							  {
								  startRecord=1;
								  endRecord=totalRecords;
								  empForm.setTotalRecords(totalRecords);
								  empForm.setStartRecord(1);
								  empForm.setEndRecord(totalRecords); 
							  }
								
				String getEmpdetails="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY emp.PERNR) AS  RowNum,emp.PERNR,emp.Emp_FullName,loc.LOCATION_CODE,desg.DSGSTXT," +
				"dept.DPTSTXT,emp.EMAIL_ID,emp.TEL_EXTENS,emp.IP_PHONE from emp_official_info as emp,Location as loc,designation as desg,department as  dept where " +
				"  dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID	and emp.LOCID=loc.LOCATION_CODE  and emp.active='1') as sub Where  sub.RowNum between  1 and 10  ";
	            ResultSet rs=ad.selectQuery(getEmpdetails);
					while(rs.next())
					{
						EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
							emp.setLoginName(rs.getString("PERNR"));
							emp.setEmployeeNumber(rs.getString("PERNR"));
							emp.setFirstName(rs.getString("Emp_FullName"));
							emp.setDesignation(rs.getString("DSGSTXT"));
							emp.setDepartment(rs.getString("DPTSTXT"));
							emp.setLocation(rs.getString("LOCATION_CODE"));
							emp.setEmailid(rs.getString("EMAIL_ID"));
							emp.setContactNo(rs.getString("TEL_EXTENS"));
							emp.setIpPhoneNo(rs.getString("IP_PHONE"));
							empList.add(emp);
					}
				
			
					request.setAttribute("employeeList", empList);
					
		
		}catch (Exception e) {
		e.printStackTrace();
		}
		
		return mapping.findForward("EmployeeOfficailInfoList");
		
		
		
		
		
	}
	
	
	
	
	public String getLocationName(String type, String locId){
		MainDao exeQry = new MainDao();
		String locName="";
		try{
			if(type.equalsIgnoreCase("user")){
				int plantId=0;
				String getLocations="select plant_id from emp_master where emp_id='"+locId+"'";
				ResultSet rsGetPlant=exeQry.selectQuery(getLocations);
				while(rsGetPlant.next()){
						System.out.println("if "+rsGetPlant.getInt("plant_id"));
						plantId=rsGetPlant.getInt("plant_id");
				}
				locName = getLocationName("plant",""+plantId);
			}
			else{
				String getLocations="select * from Plant where NAME1='"+locId+"'";
				ResultSet rsGetLocation=exeQry.selectQuery(getLocations);
				while(rsGetLocation.next()){
						locName=rsGetLocation.getString("WERKS");
					
				}
			}
		}catch (SQLException e) {System.out.println("exception @ getting location");
			e.printStackTrace();
		}
		return locName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ActionForward saveEmpOfficialInformation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			
		
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		String an =empForm.getCompanyName();
	
		
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
		
		
		String dateofJoin=empForm.getDateofJoining();
		String a[]=dateofJoin.split("/");
		dateofJoin=a[2]+"-"+a[1]+"-"+a[0];
		String dateofConf=empForm.getDateofConformation();
		if(dateofConf.equalsIgnoreCase(""))
		{
			dateofConf="";
		
		}else{
			String b[]=dateofConf.split("/");
			dateofConf=b[2]+"-"+b[1]+"-"+b[0];
		}
		String dateOfleav=empForm.getDateofLeaving();
		
		if(!dateOfleav.equalsIgnoreCase("")){
			String c[]=dateOfleav.split("/");
			dateOfleav=c[2]+"-"+c[1]+"-"+c[0];
		}else{
			dateOfleav="";
		}
		
		
		String dob=empForm.getDob();
		String c[]=dob.split("/");
		dob=c[2]+"-"+c[1]+"-"+c[0];
		
		
		try{
			
			int empCount=0;
		String checkEmployee="select count(*) from emp_official_info where PERNR='"+empForm.getEmployeeNumber()+"'";
		ResultSet rsCheckEmp=ad.selectQuery(checkEmployee);
		while(rsCheckEmp.next())
		{
			empCount=rsCheckEmp.getInt(1);
		}
		if(empCount==0){
		String saveOIEmp ="insert into emp_official_info( PERNR,Emp_FullName,Sex,BUKRS,WERKS,pay_group,STAFFCAT,RPTMGR,APPMGR,ROOM,FLOOR,BUILDING,DOJ,DOC,DOL,LAND1," +
				"STATE,LOCID,GRDID,DSGID,DPTID,EMAIL_ID,TEL_NO,TEL_EXTENS,ESI,ESINO,PF,PFNO,PT,IT,PANNO,BONUS,LEAVES,WAERS,payment_method,BANKID,BACCTYP," +
				"BACCNO,BRANCH,IFSC_CODE,MICR_CODE,IP_PHONE,DOB,UANNO) values ('"+empForm.getEmployeeNumber()+"','"+empForm.getEmployeeName()+"','"+empForm.getGender()+"','"+empForm.getCompanyName()+"','"+empForm.getLocation()+"'," +
				"'"+empForm.getPayGroup()+"','"+empForm.getEmployeeCategory()+"','"+empForm.getReportingManger()+"','"+empForm.getApprovalManger()+"'," +
				"'"+empForm.getRoom()+"','"+empForm.getFloor()+"','"+empForm.getBuilding()+"','"+dateofJoin+"'," +
				"'"+dateofConf+"','"+dateOfleav+"','"+empForm.getCounID()+"','"+empForm.getState()+"','"+empForm.getPlant()+"','"+empForm.getGradeID()+"','"+empForm.getDesignation()+"'," +
				"'"+empForm.getDepartment()+"','"+empForm.getEmailid()+"','"+empForm.getTelNo()+"','"+empForm.getExtnNo()+"','"+empForm.getEligibleforESIDeduction()+"'," +
				"'"+empForm.getEsiNumber()+"','"+empForm.getEligibleforPFDeduction()+"','"+empForm.getPfNumber()+"','"+empForm.getEligibleforPTDeduction()+"','"+empForm.getEligibleforITDeduction()+"'," +
				"'"+empForm.getPanNo()+"','"+empForm.getBonus()+"','"+empForm.getLeaves()+"','"+empForm.getSalaryCurrency()+"','"+empForm.getPaymentMethod()+"','"+empForm.getBankName()+"','"+empForm.getAccountType()+"'," +
				"'"+empForm.getAccountNumber()+"','"+empForm.getBranchName()+"','"+empForm.getIfsCCode()+"','"+empForm.getMicrCode()+"','"+empForm.getIpPhoneNo()+"','"+dob+"','"+empForm.getUanno()+"')";
			int rs =ad.SqlExecuteUpdate(saveOIEmp);
			
			String casOpBal=empForm.getOpeningBalence1();
    		String casCloBal=empForm.getClosingBalence1();
    		
    		String sickOpBal=empForm.getOpeningBalence2();
    		String sickCloBal=empForm.getClosingBalence2();
    		
    		String plOpBal=empForm.getOpeningBalence3();
    		String plCloBal=empForm.getClosingBalence3();
    		
			String maternityLeaveType=empForm.getOpeningBalence4();
			
		try{
			if(!casOpBal.equalsIgnoreCase("")&&!casCloBal.equalsIgnoreCase(""))
			{
			String insertCL="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_clbal) " +
					"values('1','"+empForm.getYear()+"','"+empForm.getEmployeeNumber()+"','"+empForm.getOpeningBalence1()+"','"+empForm.getClosingBalence1()+"')";
			ad.SqlExecuteUpdate(insertCL);
			}
			if(!sickOpBal.equalsIgnoreCase("")&&!sickCloBal.equalsIgnoreCase(""))
			{
			String insertSK="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_clbal) " +
					"values('2','"+empForm.getYear()+"','"+empForm.getEmployeeNumber()+"','"+empForm.getOpeningBalence2()+"','"+empForm.getClosingBalence2()+"')";
			ad.SqlExecuteUpdate(insertSK);
			}
			if(!plOpBal.equalsIgnoreCase("")&&!plCloBal.equalsIgnoreCase(""))
			{
			String insertSK="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_clbal) " +
					"values('3','"+empForm.getYear()+"','"+empForm.getEmployeeNumber()+"','"+empForm.getOpeningBalence3()+"','"+empForm.getClosingBalence3()+"')";
			ad.SqlExecuteUpdate(insertSK);
			}
			if(!maternityLeaveType.equalsIgnoreCase(""))
			{
			String insertSK="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_clbal) " +
					"values('5','"+empForm.getYear()+"','"+empForm.getEmployeeNumber()+"','"+empForm.getOpeningBalence4()+"','"+empForm.getClosingBalence4()+"')";
			ad.SqlExecuteUpdate(insertSK);
			}
		}catch(Exception e){
			e.printStackTrace(); 
		}
			if(rs>0){
				empForm.setMessage("Employee Official Information Saved Succesfully");
				empForm.setSaveType("Modify");
				request.setAttribute("ModifyButton", "ModifyButton");
				empForm.setEmailid("");
				empForm.setTelNo("");
				empForm.setExtnNo("");
				empForm.setLocation("");
				empForm.setDateofConformation("");
				empForm.setDateofLeaving("");
				empForm.setRoom("");
				empForm.setFloor("");
				empForm.setBuilding("");
				empForm.setAccountType("");
				empForm.setEligibleforITDeduction("");
				empForm.setPanNo("");
				empForm.setLeaves("");
				empForm.setBonus("");
				empForm.setEmployeeNumber("");
				empForm.setCompanyName("");
				empForm.setPlant("");
				empForm.setPayGroup("");
				
				empForm.setEmployeeCategory("");
				empForm.setCountryofWorking("");
				empForm.setCounID("");
				empForm.setState("");
				empForm.setGradeID("");
				empForm.setDesignation("");
				empForm.setDepartment("");
				empForm.setHeadQuarters("");
				empForm.setDateofJoining("");
				empForm.setEligibleforESIDeduction("");
				empForm.setEsiNumber("");
				empForm.setEligibleforPFDeduction("");
				empForm.setPfNumber("");
				empForm.setSalaryCurrency("");
			
				empForm.setEligibleforPTDeduction("");
				empForm.setPaymentMethod("");
				empForm.setAccountType("");
				empForm.setAccountNumber("");
				empForm.setBankName("");
				empForm.setBranchName("");
				empForm.setIfsCCode("");
				empForm.setMicrCode("");
				empForm.setIpPhoneNo("");
				empForm.setEmployeeName("");
				empForm.setOpeningBalence1("");
				empForm.setOpeningBalence2("");
				empForm.setOpeningBalence3("");
				empForm.setOpeningBalence4("");
				empForm.setClosingBalence1("");
				empForm.setClosingBalence2("");
				empForm.setClosingBalence3("");
				empForm.setClosingBalence4("");
				
				
				
				
				
			}else{
				empForm.setSaveType("Save");
				request.setAttribute("SaveButton", "SaveButton");
				empForm.setMessage("Error...While Saving Employee Official Information.Please Check...");
			}
		}else{
			empForm.setSaveType("Save");
			request.setAttribute("SaveButton", "SaveButton");
			empForm.setMessage("Error...Employee Number Already Exist.Please Check...");
			
		}
					
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		
		hrDisplayEmployee(mapping, form, request, response);
		
		return mapping.findForward("displayEmployee");
				
	
	}
	
	public ActionForward hrDisplayEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		EssDao ad=new EssDao();
		try{ 
			
			
			ResultSet rs5 = ad.selectQuery("select LOCID,LOCNAME,LOCATION_CODE from Location");
			ArrayList locIDList=new ArrayList();
			ArrayList locNameList=new ArrayList();
				
			while(rs5.next()) {
				locIDList.add(rs5.getString("LOCATION_CODE"));
				locNameList.add(rs5.getString("LOCATION_CODE")+"-"+rs5.getString("LOCNAME"));
			//	banknameList.add(rs10.getString("BNAME"));
			}
			empForm.setLocIDList(locIDList);
			empForm.setLocNameList(locNameList);
			
			
		//	empForm.setBanknameList(banknameList);
			
			ResultSet rs6 = ad.selectQuery("select  BANKID from Bank");
			ArrayList bankIDList=new ArrayList();
		//	ArrayList banknameList=new ArrayList();
				
			while(rs6.next()) {
				bankIDList.add(rs6.getString("BANKID"));
			//	banknameList.add(rs10.getString("BNAME"));
			}
			empForm.setBankIDList(bankIDList);
		//	empForm.setBanknameList(banknameList);
			
			ResultSet rs7 = ad.selectQuery("select  pay_id,pay_method from PAYMODE");
			ArrayList payIDList=new ArrayList();
			ArrayList payNameList=new ArrayList();
				
			while(rs7.next()) {
				payIDList.add(rs7.getString("pay_id"));
				payNameList.add(rs7.getString("pay_method"));
			}
			empForm.setPayIDList(payIDList);
			empForm.setPayNameList(payNameList);
			ad.connClose();
			
			
			
			
			
			
			ResultSet rs8 = ad.selectQuery("select  BUKRS,BUTXT from Company");
			ArrayList companyIDList=new ArrayList();
			ArrayList companyNameList=new ArrayList();
				
			while(rs8.next()) {
				companyIDList.add(rs8.getString("BUKRS"));
				companyNameList.add(rs8.getString("BUTXT")+"-"+rs8.getString("BUKRS"));
			}
			empForm.setCompanyIDList(companyIDList);
			empForm.setCompanyNameList(companyNameList);
			
			
			
			ResultSet rs9 = ad.selectQuery("select  WAERS,ISOCD from Currency order by ISOCD");
			ArrayList currencyIDList=new ArrayList();
			ArrayList currencyNameList=new ArrayList();
				
			while(rs9.next()) {
				currencyIDList.add(rs9.getString("WAERS"));
				currencyNameList.add(rs9.getString("ISOCD"));
			}
			empForm.setCurrencyIDList(currencyIDList);
			empForm.setCurrencyNameList(currencyNameList);
			
			ResultSet rs10 = ad.selectQuery("select  BANKID,BNAME from Bank");
			ArrayList bankIDLists=new ArrayList();
			ArrayList banknameLists=new ArrayList();
				
			while(rs10.next()) {
				bankIDLists.add(rs10.getString("BANKID"));
				banknameLists.add(rs10.getString("BNAME"));
			}
			empForm.setBankIDList(bankIDLists);
			empForm.setBanknameList(banknameLists);
			
			ad.connClose();
		
			ResultSet rs11 = ad.selectQuery("select  WERKS,NAME1 from Plant");
			ArrayList plantNameList=new ArrayList();
			ArrayList plantIDList=new ArrayList();
				
			while(rs11.next()) {
				plantIDList.add(rs11.getString("WERKS"));
				plantNameList.add(rs11.getString("WERKS")+"-"+rs11.getString("NAME1"));
			}
			empForm.setPlantNameList(plantNameList);
			empForm.setPlantIDList(plantIDList);
		
				
			
		String getCountryDetails="select LAND1,LANDX from Country order by LANDX";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LANDX"));
		}

		empForm.setCountryID(countryID);
		empForm.setCountry(countryName);
		
		
		
		
		
		String getDesignationDetails="select DSGID,DSGSTXT from Designation order by DSGSTXT";
		LinkedList desgnIDList=new LinkedList();
		LinkedList desgnTXTList=new LinkedList();
		ResultSet rsDesignationDetails=ad.selectQuery(getDesignationDetails);
		while(rsDesignationDetails.next()){
			desgnIDList.add(rsDesignationDetails.getString("DSGID"));
			desgnTXTList.add(rsDesignationDetails.getString("DSGSTXT"));
			
		}
		
		empForm.setDesgnIDList(desgnIDList);
		empForm.setDesgnTXTList(desgnTXTList);
		

		String getdepartmentDetails="select DPTID,DPTSTXT from Department order by DPTSTXT";
		LinkedList departIDList=new LinkedList();
		LinkedList departTXTList= new LinkedList();
		ResultSet rsdepartmentDetails=ad.selectQuery(getdepartmentDetails);
		while(rsdepartmentDetails.next()){
			departIDList.add(rsdepartmentDetails.getString("DPTID"));
			departTXTList.add(rsdepartmentDetails.getString("DPTSTXT"));
			
		}
		
		//empForm.setDepars2(depars);
	//	empForm.setDepars2(depars);
		empForm.setDepartIDList(departIDList);
		empForm.setDepartTXTList(departTXTList);
		
		ad.connClose();
		

		
		
		
		
		LinkedList listofPayGroup= new LinkedList();
		ResultSet rs12= ad.selectQuery("select Paygroup,Short_desc from Paygroup_Master");
		ArrayList  paygroupList = new ArrayList();
		ArrayList  paytestList=new ArrayList();
		while(rs12.next()){
			paygroupList.add(rs12.getString("Paygroup"));
			paytestList.add(rs12.getString("Short_desc"));
		}
		empForm.setPaygroupList(paygroupList);
		empForm.setPaystextList(paytestList);
		
		
		ResultSet rs13 =ad.selectQuery("select STAFFCAT,CATSTEXT from Category");
	    ArrayList empCatogeryList = new ArrayList();
	    ArrayList empCatogerytextList=new ArrayList();
	    while(rs13.next()){
	    	
	    	 empCatogeryList.add(rs13.getString("STAFFCAT"));
	    	 empCatogerytextList.add(rs13.getString("CATSTEXT"));
	    	
	    }
	    empForm.setEmpCatogeryList(empCatogeryList);
	    empForm.setEmpCatogerytextList(empCatogerytextList);
		
		ResultSet rs14 = ad.selectQuery("select GRDID,GRDTXT from Grade");
		ArrayList gradeidList = new ArrayList();
		ArrayList gradetxtList=new ArrayList();
		while(rs14.next()){
			
			gradeidList.add(rs14.getString("GRDID"));
			gradetxtList.add(rs14.getString("GRDTXT"));
			
		}
		
	
		empForm.setGradeidList(gradeidList);
		empForm.setGradetxtList(gradetxtList);
		
		
	
		
		setLeaveType(mapping, form, request, response);
		
		}catch (Exception e) {
		e.printStackTrace();
		}
		
	empForm.setSaveType("Save");
		
	request.setAttribute("SaveButton", "SaveButton");
		return mapping.findForward("displayEmployee");
	}
	
	public ActionForward getStates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		
	try{
		ResultSet rs5 = ad.selectQuery("select LOCID,LOCNAME,LOCATION_CODE from Location");
		ArrayList locIDList=new ArrayList();
		ArrayList locNameList=new ArrayList();
			
		while(rs5.next()) {
			locIDList.add(rs5.getString("LOCATION_CODE"));
			locNameList.add(rs5.getString("LOCATION_CODE")+"-"+rs5.getString("LOCNAME"));
		}
		empForm.setLocIDList(locIDList);
		empForm.setLocNameList(locNameList);
		
		
		ResultSet rs7 = ad.selectQuery("select  pay_id,pay_method from PAYMODE");
		ArrayList payIDList=new ArrayList();
		ArrayList payNameList=new ArrayList();
			
		while(rs7.next()) {
			payIDList.add(rs7.getString("pay_id"));
			payNameList.add(rs7.getString("pay_method"));
		}
		empForm.setPayIDList(payIDList);
		empForm.setPayNameList(payNameList);
		
			
		ResultSet rs11 = ad.selectQuery("select  WERKS,NAME1 from Plant");
		ArrayList plantNameList=new ArrayList();
		ArrayList plantIDList=new ArrayList();
			
		while(rs11.next()) {
			plantIDList.add(rs11.getString("WERKS"));
			plantNameList.add(rs11.getString("WERKS")+"-"+rs11.getString("NAME1"));
		}
		empForm.setPlantNameList(plantNameList);
		empForm.setPlantIDList(plantIDList);
		
		ResultSet rs14 = ad.selectQuery("select GRDID,GRDTXT from Grade");
		ArrayList gradeidList = new ArrayList();
		ArrayList gradetxtList=new ArrayList();
		while(rs14.next()){
			
			gradeidList.add(rs14.getString("GRDID"));
			gradetxtList.add(rs14.getString("GRDTXT"));
			
		}
		
	
		empForm.setGradeidList(gradeidList);
		empForm.setGradetxtList(gradetxtList);
		
		
		ResultSet rs9 = ad.selectQuery("select  WAERS,ISOCD from Currency order by ISOCD");
		ArrayList currencyIDList=new ArrayList();
		ArrayList currencyNameList=new ArrayList();
			
		while(rs9.next()) {
			currencyIDList.add(rs9.getString("WAERS"));
			currencyNameList.add(rs9.getString("ISOCD"));
		}
		empForm.setCurrencyIDList(currencyIDList);
		empForm.setCurrencyNameList(currencyNameList);
		
		ResultSet rs10 = ad.selectQuery("select  BANKID,BNAME from Bank");
		ArrayList bankIDList=new ArrayList();
		ArrayList banknameList=new ArrayList();
			
		while(rs10.next()) {
			bankIDList.add(rs10.getString("BANKID"));
			banknameList.add(rs10.getString("BNAME"));
		}
		empForm.setBankIDList(bankIDList);
		empForm.setBanknameList(banknameList);
		
		LinkedList listofPayGroup= new LinkedList();
		ResultSet rs12= ad.selectQuery("select Paygroup,Short_desc from Paygroup_Master");
		ArrayList  paygroupList = new ArrayList();
		ArrayList  paytestList=new ArrayList();
		while(rs12.next()){
			paygroupList.add(rs12.getString("Paygroup"));
			paytestList.add(rs12.getString("Short_desc"));
		}
		empForm.setPaygroupList(paygroupList);
		empForm.setPaystextList(paytestList);
		/////////////
		String countryId=empForm.getCounID();
		LinkedList stateID=new LinkedList();
		LinkedList stateName=new LinkedList();
		String getStateDetails="select * from State where LAND1='"+countryId+"'";
		ResultSet rsState=ad.selectQuery(getStateDetails);
		while(rsState.next())
		{
			stateID.add(rsState.getString("BLAND"));
			stateName.add(rsState.getString("BEZEI"));
		}
		
		
		
		empForm.setStateID(stateID);
		empForm.setStateName(stateName);
		
		
		String getCountryDetails="select LAND1,LANDX from Country order by LANDX";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LANDX"));
		}

		empForm.setCountryID(countryID);
		empForm.setCountry(countryName);
			
			
			String getDesignationDetails="select DSGID,DSGSTXT from Designation order by DSGSTXT";
			LinkedList desgnIDList=new LinkedList();
			LinkedList desgnTXTList=new LinkedList();
			ResultSet rsDesignationDetails=ad.selectQuery(getDesignationDetails);
			while(rsDesignationDetails.next()){
				desgnIDList.add(rsDesignationDetails.getString("DSGID"));
				desgnTXTList.add(rsDesignationDetails.getString("DSGSTXT"));
				
			}
			
			empForm.setDesgnIDList(desgnIDList);
			empForm.setDesgnTXTList(desgnTXTList);
			

			String getdepartmentDetails="select DPTID,DPTSTXT from Department order by DPTSTXT";
			LinkedList departIDList=new LinkedList();
			LinkedList departTXTList= new LinkedList();
			ResultSet rsdepartmentDetails=ad.selectQuery(getdepartmentDetails);
			while(rsdepartmentDetails.next()){
				departIDList.add(rsdepartmentDetails.getString("DPTID"));
				departTXTList.add(rsdepartmentDetails.getString("DPTSTXT"));
				
			}
		
			empForm.setDepartIDList(departIDList);
			empForm.setDepartTXTList(departTXTList);
			
			
			
			ResultSet rs13 =ad.selectQuery("select STAFFCAT,CATSTEXT from Category");
			    ArrayList empCatogeryList = new ArrayList();
			    ArrayList empCatogerytextList=new ArrayList();
			    while(rs13.next()){
			    	
			    	 empCatogeryList.add(rs13.getString("STAFFCAT"));
			    	 empCatogerytextList.add(rs13.getString("CATSTEXT"));
			    	
			    }
			    empForm.setEmpCatogeryList(empCatogeryList);
			    empForm.setEmpCatogerytextList(empCatogerytextList);
			    
				
				ResultSet rs8 = ad.selectQuery("select  * from Company");
				ArrayList companyIDList=new ArrayList();
				ArrayList companyNameList=new ArrayList();
					
				while(rs8.next()) {
					companyIDList.add(rs8.getString("BUKRS"));
					companyNameList.add(rs8.getString("BUTXT"));
				}
				empForm.setCompanyIDList(companyIDList);
				empForm.setCompanyNameList(companyNameList);
			
			
			
			setLeaveType(mapping, form, request, response);
			
	}catch (Exception e) {
			e.printStackTrace();
		}
	    request.setAttribute("diplayStates", "diplayStates");
	    
	    String saveType=empForm.getSaveType();
	    if(saveType.equalsIgnoreCase("Save"))
	    {
	    	request.setAttribute("SaveButton", "SaveButton");
	    }else{
	    	request.setAttribute("ModifyButton", "ModifyButton");
	    }
	    
		return mapping.findForward("displayEmployee");
		
	
	}
	
	
	public ActionForward getStatesModifyEmp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		
	try{
		ResultSet rs5 = ad.selectQuery("select LOCID,LOCNAME,LOCATION_CODE from Location");
		ArrayList locIDList=new ArrayList();
		ArrayList locNameList=new ArrayList();
			
		while(rs5.next()) {
			locIDList.add(rs5.getString("LOCATION_CODE"));
			locNameList.add(rs5.getString("LOCATION_CODE")+"-"+rs5.getString("LOCNAME"));
		}
		empForm.setLocIDList(locIDList);
		empForm.setLocNameList(locNameList);
		
		
		ResultSet rs7 = ad.selectQuery("select  pay_id,pay_method from PAYMODE");
		ArrayList payIDList=new ArrayList();
		ArrayList payNameList=new ArrayList();
			
		while(rs7.next()) {
			payIDList.add(rs7.getString("pay_id"));
			payNameList.add(rs7.getString("pay_method"));
		}
		empForm.setPayIDList(payIDList);
		empForm.setPayNameList(payNameList);
		
			
		ResultSet rs11 = ad.selectQuery("select  WERKS,NAME1 from Plant");
		ArrayList plantNameList=new ArrayList();
		ArrayList plantIDList=new ArrayList();
			
		while(rs11.next()) {
			plantIDList.add(rs11.getString("WERKS"));
			plantNameList.add(rs11.getString("WERKS")+"-"+rs11.getString("NAME1"));
		}
		empForm.setPlantNameList(plantNameList);
		empForm.setPlantIDList(plantIDList);
		
		ResultSet rs14 = ad.selectQuery("select GRDID,GRDTXT from Grade");
		ArrayList gradeidList = new ArrayList();
		ArrayList gradetxtList=new ArrayList();
		while(rs14.next()){
			
			gradeidList.add(rs14.getString("GRDID"));
			gradetxtList.add(rs14.getString("GRDTXT"));
			
		}
		
	
		empForm.setGradeidList(gradeidList);
		empForm.setGradetxtList(gradetxtList);
		
		
		ResultSet rs9 = ad.selectQuery("select  WAERS,ISOCD from Currency order by ISOCD");
		ArrayList currencyIDList=new ArrayList();
		ArrayList currencyNameList=new ArrayList();
			
		while(rs9.next()) {
			currencyIDList.add(rs9.getString("WAERS"));
			currencyNameList.add(rs9.getString("ISOCD"));
		}
		empForm.setCurrencyIDList(currencyIDList);
		empForm.setCurrencyNameList(currencyNameList);
		
		ResultSet rs10 = ad.selectQuery("select  BANKID,BNAME from Bank");
		ArrayList bankIDList=new ArrayList();
		ArrayList banknameList=new ArrayList();
			
		while(rs10.next()) {
			bankIDList.add(rs10.getString("BANKID"));
			banknameList.add(rs10.getString("BNAME"));
		}
		empForm.setBankIDList(bankIDList);
		empForm.setBanknameList(banknameList);
		
		LinkedList listofPayGroup= new LinkedList();
		ResultSet rs12= ad.selectQuery("select Paygroup,Short_desc from Paygroup_Master");
		ArrayList  paygroupList = new ArrayList();
		ArrayList  paytestList=new ArrayList();
		while(rs12.next()){
			paygroupList.add(rs12.getString("Paygroup"));
			paytestList.add(rs12.getString("Short_desc"));
		}
		empForm.setPaygroupList(paygroupList);
		empForm.setPaystextList(paytestList);
		/////////////
		String countryId=empForm.getCounID();
		LinkedList stateID=new LinkedList();
		LinkedList stateName=new LinkedList();
		String getStateDetails="select * from State where LAND1='"+countryId+"'";
		ResultSet rsState=ad.selectQuery(getStateDetails);
		while(rsState.next())
		{
			stateID.add(rsState.getString("BLAND"));
			stateName.add(rsState.getString("BEZEI"));
		}
		
		
		
		empForm.setStateID(stateID);
		empForm.setStateName(stateName);
		
		
		String getCountryDetails="select LAND1,LANDX from Country order by LANDX";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LANDX"));
		}

		empForm.setCountryID(countryID);
		empForm.setCountry(countryName);
			
			
			String getDesignationDetails="select DSGID,DSGSTXT from Designation order by DSGSTXT";
			LinkedList desgnIDList=new LinkedList();
			LinkedList desgnTXTList=new LinkedList();
			ResultSet rsDesignationDetails=ad.selectQuery(getDesignationDetails);
			while(rsDesignationDetails.next()){
				desgnIDList.add(rsDesignationDetails.getString("DSGID"));
				desgnTXTList.add(rsDesignationDetails.getString("DSGSTXT"));
				
			}
			
			empForm.setDesgnIDList(desgnIDList);
			empForm.setDesgnTXTList(desgnTXTList);
			

			String getdepartmentDetails="select DPTID,DPTSTXT from Department order by DPTSTXT";
			LinkedList departIDList=new LinkedList();
			LinkedList departTXTList= new LinkedList();
			ResultSet rsdepartmentDetails=ad.selectQuery(getdepartmentDetails);
			while(rsdepartmentDetails.next()){
				departIDList.add(rsdepartmentDetails.getString("DPTID"));
				departTXTList.add(rsdepartmentDetails.getString("DPTSTXT"));
				
			}
		
			empForm.setDepartIDList(departIDList);
			empForm.setDepartTXTList(departTXTList);
			
			
			
			ResultSet rs13 =ad.selectQuery("select STAFFCAT,CATSTEXT from Category");
			    ArrayList empCatogeryList = new ArrayList();
			    ArrayList empCatogerytextList=new ArrayList();
			    while(rs13.next()){
			    	
			    	 empCatogeryList.add(rs13.getString("STAFFCAT"));
			    	 empCatogerytextList.add(rs13.getString("CATSTEXT"));
			    	
			    }
			    empForm.setEmpCatogeryList(empCatogeryList);
			    empForm.setEmpCatogerytextList(empCatogerytextList);
			    
				
				ResultSet rs8 = ad.selectQuery("select  BUKRS,BUTXT from Company");
				ArrayList companyIDList=new ArrayList();
				ArrayList companyNameList=new ArrayList();
					
				while(rs8.next()) {
					companyIDList.add(rs8.getString("BUKRS"));
					companyNameList.add(rs8.getString("BUTXT")+"-"+rs8.getString("BUKRS"));
				}
				empForm.setCompanyIDList(companyIDList);
				empForm.setCompanyNameList(companyNameList);
			
			
			
			
			
	}catch (Exception e) {
			e.printStackTrace();
		}
	    request.setAttribute("diplayStates", "diplayStates");
		return mapping.findForward("modifyEmpOfficialInfo");
		
	
	}
	
	
	public ActionForward  getcodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmpOfficalInformationForm empForm=(EmpOfficalInformationForm)form;
		
	/*	
		String countryId=empForm.getCounID();
		LinkedList stateID=new LinkedList();
		LinkedList stateName=new LinkedList();
		String getStateDetails="select * from State where LAND1='"+countryId+"'";
		ResultSet rsState=ad.selectQuery(getStateDetails);
		while(rsState.next())
		{
			stateID.add(rsState.getString("BLAND"));
			stateName.add(rsState.getString("BEZEI"));
		}*/
		
try{
	
	ResultSet rs5 = ad.selectQuery("select LOCID,LOCNAME,LOCATION_CODE from Location");
	ArrayList locIDList=new ArrayList();
	ArrayList locNameList=new ArrayList();
		
	while(rs5.next()) {
		locIDList.add(rs5.getString("LOCATION_CODE"));
		locNameList.add(rs5.getString("LOCATION_CODE")+"-"+rs5.getString("LOCNAME"));
	//	banknameList.add(rs10.getString("BNAME"));
	}
	empForm.setLocIDList(locIDList);
	empForm.setLocNameList(locNameList);
	
	
	ResultSet rs8 = ad.selectQuery("select  BUKRS,BUTXT from Company");
	ArrayList companyIDList=new ArrayList();
	ArrayList companyNameList=new ArrayList();
		
	while(rs8.next()) {
		companyIDList.add(rs8.getString("BUKRS"));
		companyNameList.add(rs8.getString("BUTXT")+"-"+rs8.getString("BUKRS"));
	}
	empForm.setCompanyIDList(companyIDList);
	empForm.setCompanyNameList(companyNameList);
	
	
	
	
		String codeId=empForm.getBankName();
		LinkedList ifsCCodeList=new LinkedList();
		LinkedList micrCodeList=new LinkedList();
		String getCodeDetails="select * from Bank where BANKID='"+codeId+"'";
	
		ResultSet rsCode=ad.selectQuery(getCodeDetails);
	
		while(rsCode.next())
		{
			empForm.setIfsCCode(rsCode.getString("IFSC_CODE"));
			empForm.setMicrCode(rsCode.getString("MICR_CODE"));
		}
		
		ResultSet rs7 = ad.selectQuery("select  pay_id,pay_method from PAYMODE");
		ArrayList payIDList=new ArrayList();
		ArrayList payNameList=new ArrayList();
			
		while(rs7.next()) {
			payIDList.add(rs7.getString("pay_id"));
			payNameList.add(rs7.getString("pay_method"));
		}
		empForm.setPayIDList(payIDList);
		empForm.setPayNameList(payNameList);
		
		
		
		
		
		
		
		
		ResultSet rs11 = ad.selectQuery("select  WERKS,NAME1 from Plant");
		ArrayList plantNameList=new ArrayList();
		ArrayList plantIDList=new ArrayList();
			
		while(rs11.next()) {
			plantIDList.add(rs11.getString("WERKS"));
			plantNameList.add(rs11.getString("WERKS")+"-"+rs11.getString("NAME1"));
		}
		empForm.setPlantNameList(plantNameList);
		empForm.setPlantIDList(plantIDList);
		
		ResultSet rs14 = ad.selectQuery("select GRDID,GRDTXT from Grade");
		ArrayList gradeidList = new ArrayList();
		ArrayList gradetxtList=new ArrayList();
		while(rs14.next()){
			
			gradeidList.add(rs14.getString("GRDID"));
			gradetxtList.add(rs14.getString("GRDTXT"));
			
		}
		
	
		empForm.setGradeidList(gradeidList);
		empForm.setGradetxtList(gradetxtList);
		
		
		ResultSet rs9 = ad.selectQuery("select  WAERS,ISOCD from Currency order by ISOCD");
		ArrayList currencyIDList=new ArrayList();
		ArrayList currencyNameList=new ArrayList();
			
		while(rs9.next()) {
			currencyIDList.add(rs9.getString("WAERS"));
			currencyNameList.add(rs9.getString("ISOCD"));
		}
		empForm.setCurrencyIDList(currencyIDList);
		empForm.setCurrencyNameList(currencyNameList);
		
		ResultSet rs10 = ad.selectQuery("select BRANCH,BANKID,BNAME from Bank");
		ArrayList bankIDList=new ArrayList();
		ArrayList banknameList=new ArrayList();
			
		while(rs10.next()) {
			bankIDList.add(rs10.getString("BANKID"));
			banknameList.add(rs10.getString("BNAME")+","+rs10.getString("BRANCH"));
		}
		empForm.setBankIDList(bankIDList);
		empForm.setBanknameList(banknameList);
		
		LinkedList listofPayGroup= new LinkedList();
		ResultSet rs12= ad.selectQuery("select Paygroup,Short_desc from Paygroup_Master");
		ArrayList  paygroupList = new ArrayList();
		ArrayList  paytestList=new ArrayList();
		while(rs12.next()){
			paygroupList.add(rs12.getString("Paygroup"));
			paytestList.add(rs12.getString("Short_desc"));
		}
		empForm.setPaygroupList(paygroupList);
		empForm.setPaystextList(paytestList);
		/////////////
		String countryId=empForm.getCounID();
		LinkedList stateID=new LinkedList();
		LinkedList stateName=new LinkedList();
		String getStateDetails="select * from State where LAND1='"+countryId+"'";
		ResultSet rsState=ad.selectQuery(getStateDetails);
		while(rsState.next())
		{
			stateID.add(rsState.getString("BLAND"));
			stateName.add(rsState.getString("BEZEI"));
		}
		
		
		
		empForm.setStateID(stateID);
		empForm.setStateName(stateName);
		
		
		String getCountryDetails="select LAND1,LANDX from Country order by LANDX";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LANDX"));
		}

		empForm.setCountryID(countryID);
		empForm.setCountry(countryName);
			
			
			String getDesignationDetails="select DSGID,DSGSTXT from Designation order by DSGSTXT";
			LinkedList desgnIDList=new LinkedList();
			LinkedList desgnTXTList=new LinkedList();
			ResultSet rsDesignationDetails=ad.selectQuery(getDesignationDetails);
			while(rsDesignationDetails.next()){
				desgnIDList.add(rsDesignationDetails.getString("DSGID"));
				desgnTXTList.add(rsDesignationDetails.getString("DSGSTXT"));
				
			}
			
			empForm.setDesgnIDList(desgnIDList);
			empForm.setDesgnTXTList(desgnTXTList);
			

			String getdepartmentDetails="select DPTID,DPTSTXT from Department order by DPTSTXT";
			LinkedList departIDList=new LinkedList();
			LinkedList departTXTList= new LinkedList();
			ResultSet rsdepartmentDetails=ad.selectQuery(getdepartmentDetails);
			while(rsdepartmentDetails.next()){
				departIDList.add(rsdepartmentDetails.getString("DPTID"));
				departTXTList.add(rsdepartmentDetails.getString("DPTSTXT"));
				
			}
		
			empForm.setDepartIDList(departIDList);
			empForm.setDepartTXTList(departTXTList);
			
		/*	LinkedList listOfMaterialCode=new LinkedList();
			ResultSet rs11 = ad.selectQuery("select location_id," +
			"location_name,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				
			while(rs11.next()) {
				locationList.add(rs11.getString("location_id"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			empForm.setLocationIdList(locationList);
			empForm.setLocationLabelList(locationLabelList);*/
			
			
			ResultSet rs13 =ad.selectQuery("select STAFFCAT,CATSTEXT from Category");
			    ArrayList empCatogeryList = new ArrayList();
			    ArrayList empCatogerytextList=new ArrayList();
			    while(rs13.next()){
			    	
			    	 empCatogeryList.add(rs13.getString("STAFFCAT"));
			    	 empCatogerytextList.add(rs13.getString("CATSTEXT"));
			    	
			    }
			    empForm.setEmpCatogeryList(empCatogeryList);
			    empForm.setEmpCatogerytextList(empCatogerytextList);
		
		
		String saveType=empForm.getSaveType();
		if(saveType.equalsIgnoreCase("Save"))
		{
			empForm.setSaveType(saveType);
			request.setAttribute("SaveButton", "SaveButton");
		}else{
			empForm.setSaveType(saveType);
			request.setAttribute("ModifyButton", "ModifyButton");
			
		}
		
		
		
		
		
		
		
		
		
		
}catch (Exception e) {
	e.printStackTrace();
}
 request.setAttribute("diplayStates", "diplayStates");
     return mapping.findForward("displayEmployee");
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
	
	
