package com.microlabs.ess.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.springframework.util.StringUtils;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.ESSApproverForm;
import com.microlabs.admin.form.LinksForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.hr.dao.SAPAttendenceDAO;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.microlabs.hr.form.HRApprovalForm;
import com.microlabs.hr.form.SAPAttendenceForm;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.newsandmedia.form.NewsAndMediaForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.Leave;
import com.microlabs.utilities.UserInfo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class LeaveAction extends DispatchAction{
	EssDao ad=EssDao.dBConnection();
	static Logger log = Logger.getLogger(LeaveAction.class);
	public String Empname(String a)
	{
		
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
		
		String emp = "select EMP_FULLNAME from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("emp_fullname"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
		return b;
		
	}
	
	public String EmpLoc(String a)
	{
		
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
			EssDao ad = new EssDao();
		String emp = "select LOCID from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("LOCID"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return b;
		
	}
	
	public ActionForward searchContacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LeaveForm masterForm = (LeaveForm) form;
		LoginDao ad = new LoginDao();
		String reqFiled = masterForm.getReqFiled();
		masterForm.setReqFiled(reqFiled);
		try {

			LinkedList listOfMaterialCode = new LinkedList();
			ResultSet rs11 = ad.selectQuery("select LOCID,"
					+ "LOCNAME,location_code from location");
			ArrayList locationList = new ArrayList();
			ArrayList locationLabelList = new ArrayList();
			HttpSession session = request.getSession();
			UserInfo user = (UserInfo) session.getAttribute("user");

			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("location_code") + " - "
						+ rs11.getString("LOCNAME"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList = new ArrayList();
			ResultSet rs12 = ad.selectQuery("select DPTSTXT from department ");
			while (rs12.next()) {

				deparmentList.add(rs12.getString("DPTSTXT"));
			}

			masterForm.setDepartmentList(deparmentList);

			String locationId = masterForm.getLocationId();
			String department = masterForm.getDepartment();
			String firstName = masterForm.getEmpName();
			ArrayList contactList = new ArrayList();
			EmpOfficalInformationForm emp = null;

			String getContactList = "Select  u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code,desg.DSGSTXT,u.DOJ "
					+ " from emp_official_info as u,Location as loc,department as dept,designation as desg where u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID  ";
			if (!locationId.equalsIgnoreCase("")) {
				getContactList = getContactList + "and u.LOCID= '" + locationId
						+ "'";
			}
			if (!department.equalsIgnoreCase("")) {
				getContactList = getContactList + " and dept.DPTSTXT ='"
						+ department + "'";
			}
			if (!firstName.equalsIgnoreCase("")) {
				getContactList = getContactList + "and  (u.EMP_FULLNAME like'%"
						+ firstName + "%' or u.PERNR like'%" + firstName
						+ "%')";
			}

			getContactList = getContactList;

			ResultSet rs = ad.selectQuery(getContactList);
			while (rs.next()) {
				emp = new EmpOfficalInformationForm();
				emp.setEmployeeNumber(rs.getString("PERNR"));
				emp.setFirstName(rs.getString("EMP_FULLNAME"));
				emp.setDesignation(rs.getString("DSGSTXT"));
				emp.setDepartment(rs.getString("DPTSTXT"));
				emp.setLocationId(rs.getString("location_code"));
				emp.setDateofJoining(EMicroUtils.display(rs.getDate("DOJ")));
				contactList.add(emp);
			}

			request.setAttribute("userDetails", contactList);

			if (contactList.size() == 0) {
				request.setAttribute("noRecords", "noRecords");

				masterForm.setMessage("No Contacts");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayUsers");
	}
	
	public ActionForward displayListUsers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LeaveForm masterForm = (LeaveForm) form;

		String reqFiled = request.getParameter("reqFiled");
		masterForm.setReqFiled(reqFiled);
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		UserDao ad = new UserDao();
		String plantID = user.getPlantId();
		try {
			LinkedList listOfMaterialCode = new LinkedList();
			ArrayList locationList = new ArrayList();
			ArrayList locationLabelList = new ArrayList();
			ResultSet rs11 = ad.selectQuery("select LOCID,"
					+ "LOCNAME,location_code from location");
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("location_code") + " - "
						+ rs11.getString("LOCNAME"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList = new ArrayList();
			ResultSet rs12 = ad
					.selectQuery("select DPTSTXT from department order by DPTSTXT");
			while (rs12.next()) {
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			masterForm.setDepartmentList(deparmentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayUsers");
	}
	
	
	public ActionForward displaycompofflist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
	       String  yearrequird="";
		UserInfo user=(UserInfo)session.getAttribute("user");
		String leaveType=(String)request.getParameter("LeaveType");
		String startDate=(String)request.getParameter("StartDate");  
		 String c1[]=startDate.split("/");
		 startDate=c1[2]+"-"+c1[1]+"-"+c1[0];
		 yearrequird=c1[2];
         
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String todaydate = ft.format(dNow);
		

        if(leaveType.equalsIgnoreCase("6"))
        {
       	       	
        	float comOpBal=0;
        	float comAvaiBal=0;
        	float comCloBal=0;
    
        	ArrayList f=new ArrayList();
        	///Get balance from comptable
        	String bal="select convert(nvarchar(10),start_date,103) as start_date1,convert(nvarchar(10),DATEADD(day,(select comp_off_days  from location where locid='"+user.getPlantId()+"'),Start_Date),103) as lapsydate,* from COMP_OT_SAP where Employee_Number='"+user.getEmployeeNo()+"'  and Comp_Off_Balance>0"
        			+ "  and  DATEDIFF(DAY,Start_Date,'"+startDate+"') <=(select comp_off_days  from location where locid='"+user.getPlantId()+"') and "
        					+ "'"+startDate+"'>=Start_Date order by start_date";
       	    ResultSet ba=ad.selectQuery(bal);
       	 try {
			while (ba.next ()) 
				{
				LeaveForm masterForm = new LeaveForm();
				masterForm.setStartDate(ba.getString("start_date1"));
				masterForm.setNoOfDays(ba.getString("Number_of_Days"));
				masterForm.setCompBalance(ba.getString("Comp_Off_Balance"));
				masterForm.setEndDate(ba.getString("lapsydate"));
				f.add(masterForm);
					
				}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        	if(f.size()>0)
           	request.setAttribute("complist", f);

      
        }
        
		return mapping.findForward("compofflist");	
	}
	
	public ActionForward displaycompbalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
	       String  yearrequird="";
		UserInfo user=(UserInfo)session.getAttribute("user");
		String leaveType=request.getParameter("LeaveType");
		String startDate=request.getParameter("StartDate");  
		 String c1[]=startDate.split("/");
		 startDate=c1[2]+"-"+c1[1]+"-"+c1[0];
		 yearrequird=c1[2];
         
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String todaydate = ft.format(dNow);
		

        if(leaveType.equalsIgnoreCase("6"))
        {
       	       	
        	float comOpBal=0;
        	float comAvaiBal=0;
        	float comCloBal=0;
    
        	
        	///Get balance from comptable
        	String bal="select year(Start_Date) as year,sum(Number_of_Days) as openbal,sum(Comp_Off_Availed_Days) as avbal,sum(Comp_Off_Balance) as clobal from COMP_OT_SAP where Employee_Number='"+user.getEmployeeNo()+"'  and Comp_Off_Balance>0"
        			+ "  and  DATEDIFF(DAY,Start_Date,'"+startDate+"') <=(select comp_off_days  from location where locid='"+user.getPlantId()+"') and '"+startDate+"'>=Start_Date group by Start_Date";
       	    ResultSet ba=ad.selectQuery(bal);
       	 try {
			while (ba.next ()) 
				{
				        comOpBal=comOpBal+ba.getFloat("openbal");
						comAvaiBal=comAvaiBal+ba.getFloat("avbal");
						comCloBal=comCloBal+ba.getFloat("clobal");
					
				}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        	
           	 //Check comp balance availabe
        	

           	 int balpresent=0;
           	 String coun="select count(*) from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_typeid='6' and lv_calyear='"+yearrequird+"'";
           	 ResultSet as=ad.selectQuery(coun);
           	 try {
				while (as.next ()) 
				{
					
					balpresent=as.getInt(1);
					
				}
			} catch (SQLException e) {				
				e.printStackTrace();
			}
        	

           	 if(balpresent==0)
           	 {
       	 //insert into lv_type
       	 String insertinto="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_availed,lv_clbal,lv_awtBal) values(6,'"+yearrequird+"','"+user.getEmployeeNo()+"','"+comOpBal+"','"+comAvaiBal+"','"+comCloBal+"',0)";
       	 int j=ad.SqlExecuteUpdatePrmaryKeys(insertinto);
           	 }
           	 
           	 else
           	 {
          String update="update lv_type_d set lv_opbal="+comOpBal+",lv_clbal="+comCloBal+" where lv_empcode='"+user.getEmployeeNo()+"' and lv_calyear='"+yearrequird+"' and lv_typeid='6'";
          int j=ad.SqlExecuteUpdatePrmaryKeys(update);
           	 }

      
        }
        
     	displayLeaveBal(mapping, leaveForm, request, response);
		return mapping.findForward("leaveBal");	
	}
	
	public ActionForward searchForApprovers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");

		String sTxt = request.getParameter("searchText");
		String reqFieldName = request.getParameter("reqFieldName");
		LeaveForm f = (LeaveForm) form;

		f.setSearchText(sTxt);

		LinkedList searchList = new LinkedList();
		try {
			String searchQuery = "select dpt.DPTSTXT,*,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dpt,DESIGNATION desg where dpt.DPTID=emp.DPTID and desg.DSGID=emp.DSGID and (EMP_FULLNAME like '%"
					+ sTxt
					+ "%' or EMAIL_ID like '%"
					+ sTxt
					+ "%' or PERNR like '%" + sTxt + "%')";
			ResultSet rs = ad.selectQuery(searchQuery);
			int i = 1;
			while (rs.next()) {

				ESSApproverForm emp = new ESSApproverForm();
				emp.setEmp(rs.getString("EMP_FULLNAME") + "-"
						+ rs.getString("PERNR") + "-" + rs.getString("DPTSTXT")
						+ "-" + rs.getString("DSGSTXT") + "-"
						+ EMicroUtils.display(rs.getDate("DOJ")));
				emp.setEmpname(rs.getString("EMP_FULLNAME") + "-"
						+ rs.getString("PERNR") + "-" + rs.getString("DPTSTXT"));

				emp.setReqFieldName(reqFieldName);
				searchList.add(emp);
				if (i == 8) {
					break;
				}
				i++;
			}

		}

		catch (SQLException sqle) {
			System.out.println("SQLException @ searching User Details");
			sqle.printStackTrace();
		}

		request.setAttribute("SearchUserDetails", searchList);

		return mapping.findForward("searchemployee");
	}

	
	
	
	public ActionForward displaynewcomp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm masterForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");

		// PLANT HEAD
		String plant="";
		//
		String HODAPPROVALREQURED = "";
		// plant head
		
		String hodreq="select * from location where locid='"+user.getPlantId()+"'";
		ResultSet f=ad.selectQuery(hodreq);
		try {
			if(f.next())
			{
				HODAPPROVALREQURED=f.getString("comp_off_hod");
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		if(HODAPPROVALREQURED.equalsIgnoreCase("1"))
		{
		
		LinkedList appList = getApprovers(request, response);		
		request.setAttribute("appList", appList);
		
		}
		
		
		if(!user.getPlantId().equalsIgnoreCase("17"))
		{
		 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
				+ user.getPlantId()
				+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
		
		}
		else
		{
			
			 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
						+ user.getPlantId()
						+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+user.getEmployeeNo()+"')";
		}
		ResultSet rsit = ad.selectQuery(plant);
		
		
		
		try {
			if (rsit.next()) {
				masterForm.setEmployeeName(rsit.getString("EMP_FULLNAME"));
				masterForm.setEmployeeNumber(rsit.getString("PERNR"));
				masterForm.setDepartment(rsit.getString("DPTSTXT"));
				masterForm.setDesignation(rsit.getString("DSGSTXT"));
			}
			else{
				masterForm.setMessage2("Plant Head Not Assigned");
				return mapping.findForward("compreq");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		LinkedList leaveIDReason=new LinkedList();
		LinkedList leaveDetReason=new LinkedList();
		
		String reason="select id,Reason from COMP_OT_TYPE where type='CompOff'";
		ResultSet a=ad.selectQuery(reason);
		try {
			while(a.next())
			{
		leaveIDReason.add(a.getString("id"));
		leaveDetReason.add(a.getString("Reason"));
			}
			masterForm.setLeaveReason(leaveIDReason);
			masterForm.setLeaveDetReason(leaveDetReason);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return mapping.findForward("newcomp");
	}

	public ActionForward displaycompDRAFT(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LeaveForm masterForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		LinkedList comp = new LinkedList();
		String a = "select c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason  from COMP_OT c where RequestedBy='"
				+ user.getEmployeeNo()
				+ "'  and  c.ApprvrStatus='Draft' group by c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason  ";
		ResultSet rsit = ad.selectQuery(a);
		try {
			while (rsit.next()) {
				LeaveForm leave = new LeaveForm();
				leave.setRequestNumber(rsit.getInt("Req_no"));
				leave.setSubmitDate(EMicroUtils.display(rsit
						.getDate("RequestedDate")));
				leave.setStatus(rsit.getString("ApprvrStatus"));
				leave.setStartDate(EMicroUtils.display(rsit.getDate("FromDate")));
				leave.setEndDate(EMicroUtils.display(rsit.getDate("ToDate")));
				int lengt = rsit.getString("reason").length();
				if (lengt > 30)
					leave.setReason(rsit.getString("reason").substring(0, 30));
				else
					leave.setReason(rsit.getString("reason"));

				comp.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (comp.size() == 0) {
			request.setAttribute("nocomp", "nocomp");
		} else {
			request.setAttribute("comp", comp);
		}

		return mapping.findForward("compDraft");

	}

	public ActionForward displaycompreq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		LeaveForm masterForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		LinkedList comp = new LinkedList();
		int year=0;
		if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
		{
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		else
		{
			 year = Integer.parseInt(request.getParameter("year"));
		}
		masterForm.setYear(year);
		ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("lv_calyear"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				masterForm.setYearList(yearList);

		String a = "select c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.SAP_Approved,c.Reason,c.Last_approver,"
				+ "c.Pending_approver  from COMP_OT c where pernr='"+ user.getEmployeeNo()+ "' and year(FromDate)='"+year+"' and  c.ApprvrStatus!='Draft' group by c.Req_no,"
				+ "c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.SAP_Approved,c.Reason,c.Last_approver,c.Pending_approver order by c.Req_no desc ";
		ResultSet rsit = ad.selectQuery(a);
		try {
			while (rsit.next()) {
				LeaveForm leave = new LeaveForm();
				leave.setRequestNumber(rsit.getInt("Req_no"));
				leave.setSubmitDate(EMicroUtils.display(rsit
						.getDate("RequestedDate")));
				leave.setStatus(rsit.getString("ApprvrStatus"));
				
				if(rsit.getString("SAP_Approved").equalsIgnoreCase("1")){
					leave.setApproveStatus("Credited");	
				}
				else{
					leave.setApproveStatus("Pending");	
				}
				
				leave.setStartDate(EMicroUtils.display(rsit.getDate("FromDate")));
				leave.setEndDate(EMicroUtils.display(rsit.getDate("ToDate")));
				int lengt = rsit.getString("reason").length();
				if (lengt > 30)
					leave.setReason(rsit.getString("reason").substring(0, 30));
				else
					leave.setReason(rsit.getString("reason"));

				leave.setPapprover(rsit.getString("Pending_approver"));

				leave.setLapprover(rsit.getString("Last_approver"));


				comp.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (comp.size() == 0) {
			request.setAttribute("nocomp", "nocomp");
		} else {
			request.setAttribute("comp", comp);
		}
		return mapping.findForward("compreq");
 
	
	}
	
	
	public ActionForward displayOTreq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		LeaveForm masterForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		LinkedList ot = new LinkedList();
		int year=0;
		if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
		{
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		else
		{
			 year = Integer.parseInt(request.getParameter("year"));
		}
		masterForm.setYear(year);
		ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("lv_calyear"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				masterForm.setYearList(yearList);
		String a = "select c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.Last_approver,"
				+ "c.Pending_approver  from OVER_TIME_DETAILS c where pernr='"+ user.getEmployeeNo()+ "'  and year(FromDate)='"+year+"'    group by c.Req_no,"
						+ "c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.Last_approver,"
						+ "c.Pending_approver order by c.Req_no desc ";
		ResultSet rsit = ad.selectQuery(a);
		try {
			while (rsit.next()) {
				LeaveForm leave = new LeaveForm();
				leave.setRequestNumber(rsit.getInt("Req_no"));
				leave.setSubmitDate(EMicroUtils.display(rsit
						.getDate("RequestedDate")));
				leave.setStatus(rsit.getString("ApprvrStatus"));
				leave.setStartDate(EMicroUtils.display(rsit.getDate("FromDate")));
				leave.setEndDate(EMicroUtils.display(rsit.getDate("ToDate")));
				int lengt = rsit.getString("reason").length();
				if (lengt > 30)
					leave.setReason(rsit.getString("reason").substring(0, 30));
				else
					leave.setReason(rsit.getString("reason"));
                leave.setPapprover(rsit.getString("Pending_approver"));

				leave.setLapprover(rsit.getString("Last_approver"));
				ot.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ot.size() == 0) {
			request.setAttribute("noOT", "noOT");
		} else {
			request.setAttribute("OT", ot);
		}
		return mapping.findForward("OTreq");
 
	
	}

	public ActionForward submitnewOT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		LeaveForm masterForm = (LeaveForm) form;

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		String pern[] = request.getParameterValues("employeeNumber");
		String pernarray = "";
		for (int j = 0; j < pern.length; j++) {
			pernarray = pernarray + "," + pern[j];
		}
		pernarray = pernarray.substring(1, pernarray.length());

		String hrs[] = request.getParameterValues("nofhrs");


		String param = request.getParameter("param");
		String HODAPPROVALREQURED = "";
		int reqid = 0;
		String max = "select max(Req_no) from  OVER_TIME_DETAILS";
		ResultSet rs = ad.selectQuery(max);
		try {
			while (rs.next()) {
				reqid = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reqid = reqid + 1;

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String submitDate = ft.format(dNow);
		int j = 0;

		String strtdate[] = masterForm.getStartDate().split("/");
		String fromdate = strtdate[2] + "-" + strtdate[1] + "-" + strtdate[0];
		

		String enddate[] = masterForm.getStartDate().split("/");
		String todate = enddate[2] + "-" + enddate[1] + "-" + enddate[0];

		synchronized (this) {

			int userpresent = 0;
			String users = "";
			// /chk comp already der for user in the day

			String chk = "select pernr from COMP_OT where  Pernr in ("
					+ pernarray + ") and (('" + fromdate
					+ "' between FromDate and ToDate) or  ('" + todate
					+ "' between FromDate and ToDate) or (FromDate between '"+ fromdate+"' and '"+ todate+"') or (ToDate between '"+ fromdate+"' and '"+ todate+"')) and ApprvrStatus NOT IN ('Rejected','Self Cancelled','Cancelled')";
			ResultSet rschk = ad.selectQuery(chk);
			try {
				while (rschk.next()) {
					userpresent = rschk.getInt(1);
					users = users + "," + rschk.getString("pernr");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!users.equalsIgnoreCase(""))
				users = users.substring(1, users.length());

			if (userpresent == 0) {

				if (param.equalsIgnoreCase("Submit")) {

					for (int i = 0; i < pern.length; i++) {
 
					

						String update = "insert into OVER_TIME_DETAILS(Req_no,FromDate,ToDate,Pernr,NoHRS,Applicabale,ApprvrStatus,RequestedBy,RequestedDate,Reason) values('"
								+ reqid
								+ "','"
								+ fromdate
								+ "','"
								+ todate
								+ "','"
								+ pern[i]
								+ "','"
								+ hrs[i]
								+ "','','Pending','"
								+ user.getEmployeeNo()
								+ "','"
								+ submitDate
								+ "','"
								+ masterForm.getReason()	+ "')";
						j = ad.SqlExecuteUpdate(update);

					}
					if (j > 0) {
						
						String parallelApprover1="";
						String parallelApprover2="";
						String	pApprover="";
						String planthead = "";

						// plant head
						
						String hodreq="select * from location where locid='"+user.getPlantId()+"'";
						ResultSet f=ad.selectQuery(hodreq);
						try {
							if(f.next())
							{
								HODAPPROVALREQURED=f.getString("comp_off_hod");
							}
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						
						if(HODAPPROVALREQURED.equalsIgnoreCase("1"))
						{
						boolean existStatus=false;
						String reqType="";
						String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
						ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
						try {
							while(rsCheckInEss.next())
							{
								int number=rsCheckInEss.getInt(1);
								reqType=rsCheckInEss.getString("reqType");
								if(number==0)
								{
									existStatus=false;
								}else{
									existStatus=true;
								}
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(existStatus==true){
							if(reqType.equalsIgnoreCase("sequential"))
							{
							String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
							ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
								try {
									while(rsApproverDsg.next())
									{
										pApprover=rsApproverDsg.getString("ApproverId");
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
										+ reqid
										+ "','OverTime','"
										+ user.getEmployeeNo()
										+ "','"
										+ submitDate
										+ "','Pending','No','"
										+ pApprover
										+ "','No','"
										+ user.getEmployeeNo() + "')";
								j = ad.SqlExecuteUpdate(insert);
								masterForm.setMessage("Request No. '" + reqid
										+ "' Submitted ");
								
								
								String update="update OVER_TIME_DETAILS set Pending_approver='"+Empname(pApprover)+"',Last_approver='' where Req_no='"+reqid+"'";
								int h = ad.SqlExecuteUpdate(update);
								
							}
							if(reqType.equalsIgnoreCase("parallel"))
							{
								
								String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
								ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
									try {
										while(rsApproverDsg.next())
										{
											pApprover=rsApproverDsg.getString("ApproverId");
											parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
											parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
											
										}
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									int i=0;
									String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
											+ reqid
											+ "','OverTime','"
											+ user.getEmployeeNo()
											+ "','"
											+ submitDate
											+ "','Pending','No','"
											+ pApprover
											+ "','No','"
											+ user.getEmployeeNo() + "')";
									j = ad.SqlExecuteUpdate(insert);
									masterForm.setMessage("Request No. '" + reqid
											+ "' Submitted ");
									
									if(!(parallelApprover1.equalsIgnoreCase("")))
									{
										 insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
												+ reqid
												+ "','OverTime','"
												+ user.getEmployeeNo()
												+ "','"
												+ submitDate
												+ "','Pending','No','"
												+ parallelApprover1
												+ "','No','"
												+ user.getEmployeeNo() + "')";
										j = ad.SqlExecuteUpdate(insert);
										
									}
									if(!(parallelApprover2.equalsIgnoreCase("")))
									{
										 insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
													+ reqid
													+ "','OverTime','"
													+ user.getEmployeeNo()
													+ "','"
													+ submitDate
													+ "','Pending','No','"
													+ parallelApprover2
													+ "','No','"
													+ user.getEmployeeNo() + "')";
											j = ad.SqlExecuteUpdate(insert);
									}
									
									String update="update OVER_TIME_DETAILS set Pending_approver='"+Empname(pApprover)+"',Last_approver='' where Req_no='"+reqid+"'";
									int h = ad.SqlExecuteUpdate(update);
									
							}
						}
						if(existStatus==false){
						// PLANT HEAD
						String plant="";
						if(!user.getPlantId().equalsIgnoreCase("17"))
						{
						 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
								+ user.getPlantId()
								+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
						
						}
						else
						{
							
							 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
										+ user.getPlantId()
										+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+user.getEmployeeNo()+"')";
						}
						ResultSet a = ad.selectQuery(plant);
						
						
						
						try {
							while (a.next()) {
								planthead = a.getString("PERNR");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
								+ reqid
								+ "','OverTime','"
								+ user.getEmployeeNo()
								+ "','"
								+ submitDate
								+ "','Pending','No','"
								+ planthead
								+ "','No','"
								+ user.getEmployeeNo() + "')";
						j = ad.SqlExecuteUpdate(insert);
						masterForm.setMessage("Request No. '" + reqid
								+ "' Submitted ");
						
						String update="update OVER_TIME_DETAILS set Pending_approver='"+Empname(planthead)+"',Last_approver='' where Req_no='"+reqid+"'";
						int h = ad.SqlExecuteUpdate(update);
						
						}
						}
						
						else
						{

							// PLANT HEAD
							String plant="";
							if(!user.getPlantId().equalsIgnoreCase("17"))
							{
							 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
									+ user.getPlantId()
									+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
							
							}
							else
							{
								
								 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
											+ user.getPlantId()
											+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+user.getEmployeeNo()+"')";
							}
							ResultSet a = ad.selectQuery(plant);
							
							
							
							try {
								while (a.next()) {
									planthead = a.getString("PERNR");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
									+ reqid
									+ "','OverTime','"
									+ user.getEmployeeNo()
									+ "','"
									+ submitDate
									+ "','Pending','No','"
									+ planthead
									+ "','No','"
									+ user.getEmployeeNo() + "')";
							j = ad.SqlExecuteUpdate(insert);
							masterForm.setMessage("Request No. '" + reqid
									+ "' Submitted ");
							
							String update="update OVER_TIME_DETAILS set Pending_approver='"+Empname(planthead)+"',Last_approver='' where Req_no='"+reqid+"'";
							int h = ad.SqlExecuteUpdate(update);
							
							
							
						}
						
					} else {
						masterForm.setMessage2("Error While Submitting ..");
					}

				} else {}

			} else {
				masterForm
						.setMessage2("COMP-OFF Request already applied for the selected date for the Emp No. '"
								+ users + "' ");
			}

		}

		masterForm.setStartDate("");
		masterForm.setEndDate("");
		masterForm.setReason("");
		masterForm.setEmployeeNumber("");
		
		if(HODAPPROVALREQURED.equalsIgnoreCase("1"))
		{
		
		LinkedList appList = getApprovers(request, response);		
		request.setAttribute("appList", appList);
		
		}
		// PLANT HEAD
		String plant="";
		if(!user.getPlantId().equalsIgnoreCase("17"))
		{
		 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
				+ user.getPlantId()
				+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
		
		}
		else
		{
			
			 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
						+ user.getPlantId()
						+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+user.getEmployeeNo()+"')";
		}
		ResultSet rsit = ad.selectQuery(plant);
		try {
			while (rsit.next()) {
				masterForm.setEmployeeName(rsit.getString("EMP_FULLNAME"));
				masterForm.setEmployeeNumber(rsit.getString("PERNR"));
				masterForm.setDepartment(rsit.getString("DPTSTXT"));
				masterForm.setDesignation(rsit.getString("DSGSTXT"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("newOT");
	
		
	}
	
	public ActionForward displayOTreport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {

			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		leaveForm.setEmployeeName(user.getFullName());
		leaveForm.setEmployeeNumber(user.getEmployeeNo());
		leaveForm.setYear(year);
		String emp = "Select dep.DPTSTXT,desg.DSGSTXT,emp.DOJ from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg where PERNR='"
				+ user.getEmployeeNo()
				+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID ";

		ResultSet rs1 = ad.selectQuery(emp);

		try {
			while (rs1.next())

			{
				leaveForm.setDepartment(rs1.getString("DPTSTXT"));
				leaveForm.setDesignation(rs1.getString("DSGSTXT"));
				leaveForm.setDoj(EMicroUtils.display1(rs1.getDate("DOJ")));
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		String startdate = "" + year + "-01-01";
		String enddate = "" + year + "-12-31";
		LinkedList OT = new LinkedList();
		String a = "select c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.ApprvdDate  from OVER_TIME_DETAILS c where pernr='"
				+ user.getEmployeeNo()
				+ "' and c.ApprvrStatus='Approved' and FromDate between '"
				+ startdate
				+ "' and '"
				+ enddate
				+ "'   group by c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.ApprvdDate   ";
		ResultSet rsit = ad.selectQuery(a);
		try {
			while (rsit.next()) {
				LeaveForm leave = new LeaveForm();
				leave.setRequestNumber(rsit.getInt("Req_no"));
				leave.setSubmitDate(EMicroUtils.display(rsit
						.getDate("RequestedDate")));
				leave.setStatus(rsit.getString("ApprvrStatus"));
				leave.setStartDate(EMicroUtils.display(rsit.getDate("FromDate")));
				leave.setEndDate(EMicroUtils.display(rsit.getDate("ToDate")));
				int lengt = rsit.getString("reason").length();
				if (lengt > 30)
					leave.setReason(rsit.getString("reason").substring(0, 30));
				else
					leave.setReason(rsit.getString("reason"));
				leave.setApprovedDate(EMicroUtils.display(rsit
						.getDate("ApprvdDate")));

				OT.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (OT.size() == 0) {
			request.setAttribute("noOT", "noOT");
		} else {
			request.setAttribute("OT", OT);
		}

		return mapping.findForward("OTreport");
	}
	
	public ActionForward viewOT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LeaveForm pendAppForm = (LeaveForm) form;
		String reqId = request.getParameter("requstNo");

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int j = 1;
		int reqby = 0;
		LinkedList comp = new LinkedList();
		String status = "";
		int requesterNo = Integer.parseInt(reqId);
		String getcomoff = "select c.*,e.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from OVER_TIME_DETAILS c,emp_official_info e,DEPARTMENT dept,DESIGNATION desg where Req_no='"
				+ requesterNo
				+ "' and e.PERNR=c.Pernr and dept.DPTID=e.DPTID and desg.DSGID=e.DSGID";
		ResultSet a = ad.selectQuery(getcomoff);
		try {
			while (a.next()) {

				LeaveForm l = new LeaveForm();
				pendAppForm.setStartDate(EMicroUtils.display(a
						.getDate("FromDate")));
				pendAppForm
						.setEndDate(EMicroUtils.display(a.getDate("ToDate")));
				pendAppForm.setReason(a.getString("Reason"));
				status = a.getString("ApprvrStatus");
				reqby = a.getInt("RequestedBy");
				l.setSlmindur(j);
				l.setEmployeeNumber(Integer.toString(a.getInt("Pernr")));
				l.setEmployeeName(a.getString("EMP_FULLNAME"));
				l.setDepartment(a.getString("DPTSTXT"));
				l.setDesignation(a.getString("DSGSTXT"));
				l.setNofhrs(a.getString("NoHRS"));
		/*		String app = a.getString("Applicabale");
				if (app.equalsIgnoreCase("BS"))
					app = "Before Shift Start Time";
				if (app.equalsIgnoreCase("AS"))
					app = "After Shift End Time";

				l.setShift(app);*/
				++j;
				comp.add(l);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("comp", comp);

		// plant
		String app = "";
		// PLANT HEAD
		
		
		
		if (status.equalsIgnoreCase("Pending")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='OverTime' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='OverTime' and a.Req_Id='"
						+ requesterNo
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='OverTime' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				

		}
		List appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(app);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					appr.setApproveDate("");
					appr.setComments("");
				}
				if (approvaStatus.equals("Approved")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if(duplicate!=null)
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
					appList.add(appr);
					duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("viewOT");
	}

	
	public ActionForward displaynewOT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm masterForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		String HODAPPROVALREQURED = "";
		// plant head
		
		String hodreq="select * from location where locid='"+user.getPlantId()+"'";
		ResultSet f=ad.selectQuery(hodreq);
		try {
			if(f.next())
			{
				HODAPPROVALREQURED=f.getString("comp_off_hod");
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		if(HODAPPROVALREQURED.equalsIgnoreCase("1"))
		{
		
		LinkedList appList = getApprovers(request, response);		
		request.setAttribute("appList", appList);
		
		}

		// PLANT HEAD
		String plant="";
		if(!user.getPlantId().equalsIgnoreCase("17"))
		{
		 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
				+ user.getPlantId()
				+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
		
		}
		else
		{
			
			 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
						+ user.getPlantId()
						+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+user.getEmployeeNo()+"')";
		}
		ResultSet rsit = ad.selectQuery(plant);
		
		
		try {
			if (rsit.next()) {
				masterForm.setEmployeeName(rsit.getString("EMP_FULLNAME"));
				masterForm.setEmployeeNumber(rsit.getString("PERNR"));
				masterForm.setDepartment(rsit.getString("DPTSTXT"));
				masterForm.setDesignation(rsit.getString("DSGSTXT"));
			}
			else{
				masterForm.setMessage2("Plant Head Not Assigned");
				return mapping.findForward("compreq");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("newOT");
	}

	public ActionForward submitnewcomp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm masterForm = (LeaveForm) form;

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		String pern[] = request.getParameterValues("employeeNumber");
		String pernarray = "";
		for (int j = 0; j < pern.length; j++) {
			pernarray = pernarray + "," + pern[j];
		}
		pernarray = pernarray.substring(1, pernarray.length());

		String hrs[] = request.getParameterValues("nofhrs");
		String shft[] = request.getParameterValues("appl");

		String param = request.getParameter("param");

		int reqid = 0;
		String max = "select max(Req_no) from  COMP_OT";
		ResultSet rs = ad.selectQuery(max);
		try {
			while (rs.next()) {
				reqid = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reqid = reqid + 1;
		String HODAPPROVALREQURED = "";
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String submitDate = ft.format(dNow);
		int j = 0;

		String strtdate[] = masterForm.getStartDate().split("/");
		String fromdate = strtdate[2] + "-" + strtdate[1] + "-" + strtdate[0];
		String thirtyFromdat = "";
		String thirtyprevdays="";
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(ft.parse(fromdate));
			cal.add(Calendar.DATE, 30);
			thirtyFromdat = ft.format(cal.getTime());
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(ft.parse(fromdate));
			cal1.add(Calendar.DATE, -30);
			thirtyprevdays = ft.format(cal1.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String enddate[] = masterForm.getStartDate().split("/");
		String todate = enddate[2] + "-" + enddate[1] + "-" + enddate[0];

		synchronized (this) {

			int userpresent = 0;
			int otpresent = 0;
			String users = "";
			String otusers = "";
			
			// /chk comp already der for user in the day

			String chk = "select pernr from COMP_OT where  Pernr in ("
					+ pernarray + ") and (('" + fromdate
					+ "' between FromDate and ToDate) or  ('" + todate
					+ "' between FromDate and ToDate) or (FromDate between '"+ fromdate+"' and '"+ todate+"') or (ToDate between '"+ fromdate+"' and '"+ todate+"')) and ApprvrStatus NOT IN ('Rejected','Self Cancelled','Cancelled')";
			ResultSet rschk = ad.selectQuery(chk);
			try {
				while (rschk.next()) {
					userpresent = rschk.getInt(1);
					users = users + "," + rschk.getString("pernr");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!users.equalsIgnoreCase(""))
				users = users.substring(1, users.length());
			
			
			//ot present
			String chk1 = "select distinct pernr from OVER_TIME_DETAILS where  Pernr in ("
					+ pernarray + ") and (('" + fromdate
					+ "' between FromDate and ToDate) or  ('" + todate
					+ "' between FromDate and ToDate) or (FromDate between '"+ fromdate+"' and '"+ todate+"') or (ToDate between '"+ fromdate+"' and '"+ todate+"')) and ApprvrStatus NOT IN ('Rejected','Self Cancelled','Cancelled')";
			ResultSet rschk1 = ad.selectQuery(chk1);
			try {
				while (rschk1.next()) {
					otpresent = rschk1.getInt(1);
					otusers = otusers + "," + rschk1.getString("pernr");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (!otusers.equalsIgnoreCase(""))
				otusers = otusers.substring(1, otusers.length());
			
			if(otpresent==0)
			{
			if (userpresent == 0) {

				if (param.equalsIgnoreCase("Submit")) {

					for (int i = 0; i < pern.length; i++) {
 
						int maxr = 0;
						String maxreq = "select MAX(SAP_ReqNo)as req  from COMP_OT ";
						ResultSet rsrt = ad.selectQuery(maxreq);
						try {
							while (rsrt.next()) {
								maxr = rsrt.getInt("req");

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						maxr = maxr + 1;
						double noodays = 0;
						if (hrs[i].equalsIgnoreCase("4"))
							noodays = 0.5;
						else
							noodays = 1;

						String update = "insert into COMP_OT(Req_no,FromDate,ToDate,Pernr,NoHRS,Applicabale,ApprvrStatus,RequestedBy,RequestedDate,Reason,SAP_ReqNo,SAP_Approved,Comp_Availed,Comp_Lapsed,LapsBydate,NofDaysRemaining,SAP_Prev_date,Comp_type) values('"
								+ reqid
								+ "','"
								+ fromdate
								+ "','"
								+ todate
								+ "','"
								+ pern[i]
								+ "','"
								+ hrs[i]
								+ "','"
								+ shft[i]
								+ "','Pending','"
								+ user.getEmployeeNo()
								+ "','"
								+ submitDate
								+ "','"
								+ masterForm.getReason()
								+ "','"
								+ maxr
								+ "','0','0','0','"
								+ thirtyFromdat
								+ "','"
								+ noodays + "','"+thirtyprevdays+"','"+masterForm.getReasonType()+"')";
						j = ad.SqlExecuteUpdate(update);

					}
					if (j > 0) {
						
						String parallelApprover1="";
						String parallelApprover2="";
						String	pApprover="";
						String planthead = "";
						
						// plant head
						
						String hodreq="select * from location where locid='"+user.getPlantId()+"'";
						ResultSet f=ad.selectQuery(hodreq);
						try {
							if(f.next())
							{
								HODAPPROVALREQURED=f.getString("comp_off_hod");
							}
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						
						if(HODAPPROVALREQURED.equalsIgnoreCase("1"))
						{
						boolean existStatus=false;
						String reqType="";
						String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
						ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
						try {
							while(rsCheckInEss.next())
							{
								int number=rsCheckInEss.getInt(1);
								reqType=rsCheckInEss.getString("reqType");
								if(number==0)
								{
									existStatus=false;
								}else{
									existStatus=true;
								}
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(existStatus==true){
							if(reqType.equalsIgnoreCase("sequential"))
							{
							String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
							ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
								try {
									while(rsApproverDsg.next())
									{
										pApprover=rsApproverDsg.getString("ApproverId");
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
										+ reqid
										+ "','Comp-Off/OT','"
										+ user.getEmployeeNo()
										+ "','"
										+ submitDate
										+ "','Pending','No','"
										+ pApprover
										+ "','No','"
										+ user.getEmployeeNo() + "')";
								j = ad.SqlExecuteUpdate(insert);
								masterForm.setMessage("Request No. '" + reqid
										+ "' Submitted ");
								
								
								String update="update COMP_OT set Pending_approver='"+Empname(pApprover)+"',Last_approver='' where Req_no='"+reqid+"'";
								int h = ad.SqlExecuteUpdate(update);
								
							}
							if(reqType.equalsIgnoreCase("parallel"))
							{
								
								String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
								ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
									try {
										while(rsApproverDsg.next())
										{
											pApprover=rsApproverDsg.getString("ApproverId");
											parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
											parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
											
										}
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									int i=0;
									String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
											+ reqid
											+ "','Comp-Off/OT','"
											+ user.getEmployeeNo()
											+ "','"
											+ submitDate
											+ "','Pending','No','"
											+ pApprover
											+ "','No','"
											+ user.getEmployeeNo() + "')";
									j = ad.SqlExecuteUpdate(insert);
									masterForm.setMessage("Request No. '" + reqid
											+ "' Submitted ");
									
									if(!(parallelApprover1.equalsIgnoreCase("")))
									{
										 insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
												+ reqid
												+ "','Comp-Off/OT','"
												+ user.getEmployeeNo()
												+ "','"
												+ submitDate
												+ "','Pending','No','"
												+ parallelApprover1
												+ "','No','"
												+ user.getEmployeeNo() + "')";
										j = ad.SqlExecuteUpdate(insert);
										
									}
									if(!(parallelApprover2.equalsIgnoreCase("")))
									{
										 insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
													+ reqid
													+ "','Comp-Off/OT','"
													+ user.getEmployeeNo()
													+ "','"
													+ submitDate
													+ "','Pending','No','"
													+ parallelApprover2
													+ "','No','"
													+ user.getEmployeeNo() + "')";
											j = ad.SqlExecuteUpdate(insert);
									}
									
									String update="update COMP_OT set Pending_approver='"+Empname(pApprover)+"',Last_approver='' where Req_no='"+reqid+"'";
									int h = ad.SqlExecuteUpdate(update);
									
							}
						}
						if(existStatus==false){
						// PLANT HEAD
						String plant="";
						if(!user.getPlantId().equalsIgnoreCase("17"))
						{
						 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
								+ user.getPlantId()
								+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
						
						}
						else
						{
							
							 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
										+ user.getPlantId()
										+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+user.getEmployeeNo()+"')";
						}
						ResultSet a = ad.selectQuery(plant);
						
						
						
						try {
							while (a.next()) {
								planthead = a.getString("PERNR");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
								+ reqid
								+ "','Comp-Off/OT','"
								+ user.getEmployeeNo()
								+ "','"
								+ submitDate
								+ "','Pending','No','"
								+ planthead
								+ "','No','"
								+ user.getEmployeeNo() + "')";
						j = ad.SqlExecuteUpdate(insert);
						masterForm.setMessage("Request No. '" + reqid
								+ "' Submitted ");
						
						String update="update COMP_OT set Pending_approver='"+Empname(planthead)+"',Last_approver='' where Req_no='"+reqid+"'";
						int h = ad.SqlExecuteUpdate(update);
						
						}
						}
						
						else
						{

							// PLANT HEAD
							String plant="";
							if(!user.getPlantId().equalsIgnoreCase("17"))
							{
							 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
									+ user.getPlantId()
									+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
							
							}
							else
							{
								
								 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
											+ user.getPlantId()
											+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+user.getEmployeeNo()+"')";
							}
							ResultSet a = ad.selectQuery(plant);
							
							
							
							try {
								while (a.next()) {
									planthead = a.getString("PERNR");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
									+ reqid
									+ "','Comp-Off/OT','"
									+ user.getEmployeeNo()
									+ "','"
									+ submitDate
									+ "','Pending','No','"
									+ planthead
									+ "','No','"
									+ user.getEmployeeNo() + "')";
							j = ad.SqlExecuteUpdate(insert);
							masterForm.setMessage("Request No. '" + reqid
									+ "' Submitted ");
							
							String update="update COMP_OT set Pending_approver='"+Empname(planthead)+"',Last_approver='' where Req_no='"+reqid+"'";
							int h = ad.SqlExecuteUpdate(update);
							
							
							
						}
						
					} else {
						masterForm.setMessage2("Error While Submitting ..");
					}

				} else {
					for (int i = 0; i < pern.length; i++) {

						//
						int maxr = 0;
						String maxreq = "select MAX(SAP_ReqNo)as req  from COMP_OT ";
						ResultSet rsrt = ad.selectQuery(maxreq);
						try {
							while (rsrt.next()) {
								maxr = rsrt.getInt("req");

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						maxr = maxr + 1;
						double noodays = 0;
						if (hrs[i].equalsIgnoreCase("4"))
							noodays = 0.5;
						else
							noodays = 1;

						String update = "insert into COMP_OT(Req_no,FromDate,ToDate,Pernr,NoHRS,Applicabale,ApprvrStatus,RequestedBy,RequestedDate,Reason,SAP_ReqNo,SAP_Approved,Comp_Availed,Comp_Lapsed,LapsBydate,NofDaysRemaining,SAP_Prev_date,Comp_type) values('"
								+ reqid
								+ "','"
								+ fromdate
								+ "','"
								+ todate
								+ "','"
								+ pern[i]
								+ "','"
								+ hrs[i]
								+ "','"
								+ shft[i]
								+ "','Draft','"
								+ user.getEmployeeNo()
								+ "','"
								+ submitDate
								+ "','"
								+ masterForm.getReason()
								+ "','"
								+ maxr
								+ "','0','0','0','"
								+ thirtyFromdat
								+ "','"
								+ noodays + "','"+thirtyprevdays+"','"+masterForm.getReasonType()+"')";
						j = ad.SqlExecuteUpdate(update);
						masterForm.setMessage("Request No. '" + reqid
								+ "' Saved As Draft");
					}

				}

			} else {
				masterForm
						.setMessage2("Request already applied for the selected date for the Emp No. '"
								+ users + "' ");
			}
			
			}
			else
			{
				masterForm
				.setMessage2("OverTime Request already applied for the selected date for the Emp No. '"
						+ otusers + "' ");
			}

		}

		masterForm.setStartDate("");
		masterForm.setEndDate("");
		masterForm.setReason("");
		masterForm.setEmployeeNumber("");
		
		if(HODAPPROVALREQURED.equalsIgnoreCase("1"))
		{
		
		LinkedList appList = getApprovers(request, response);		
		request.setAttribute("appList", appList);
		
		}
		
		// PLANT HEAD
				String plant="";
				if(!user.getPlantId().equalsIgnoreCase("17"))
				{
				 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
						+ user.getPlantId()
						+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
				
				}
				else
				{
					
					 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
								+ user.getPlantId()
								+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+user.getEmployeeNo()+"')";
				}
				ResultSet rsit = ad.selectQuery(plant);
		try {
			while (rsit.next()) {
				masterForm.setEmployeeName(rsit.getString("EMP_FULLNAME"));
				masterForm.setEmployeeNumber(rsit.getString("PERNR"));
				masterForm.setDepartment(rsit.getString("DPTSTXT"));
				masterForm.setDesignation(rsit.getString("DSGSTXT"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LinkedList leaveIDReason=new LinkedList();
		LinkedList leaveDetReason=new LinkedList();
		
		String reason="select id,Reason from COMP_OT_TYPE where type='CompOff'";
		ResultSet a=ad.selectQuery(reason);
		try {
			while(a.next())
			{
		leaveIDReason.add(a.getString("id"));
		leaveDetReason.add(a.getString("Reason"));
			}
			masterForm.setLeaveReason(leaveIDReason);
			masterForm.setLeaveDetReason(leaveDetReason);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward("newcomp");
	}
	public ActionForward  viewupdatecompoff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){


		LeaveForm pendAppForm = (LeaveForm) form;
		String reqId = request.getParameter("requstNo");
		if(reqId==null)
		{
			reqId= Integer.toString(pendAppForm.getRequestNumber());
		}

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int j = 1;
		int reqby = 0;
		LinkedList comp = new LinkedList();
		String  date2="";
		String status = "";
		int requesterNo = Integer.parseInt(reqId);
		String getcomoff = "select c.*,e.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,COMP_OT_TYPE.Reason as compreason from COMP_OT c left outer join COMP_OT_TYPE on COMP_OT_TYPE.id=c.Comp_type,emp_official_info e,DEPARTMENT dept,DESIGNATION desg where Req_no='"
				+ requesterNo
				+ "' and e.PERNR=c.Pernr and dept.DPTID=e.DPTID and desg.DSGID=e.DSGID";
		ResultSet a = ad.selectQuery(getcomoff);
		try {
			while (a.next()) {

				LeaveForm l = new LeaveForm();
				pendAppForm.setStartDate(EMicroUtils.display(a
						.getDate("FromDate")));
				pendAppForm
						.setEndDate(EMicroUtils.display(a.getDate("ToDate")));
				pendAppForm.setReason(a.getString("Reason"));
				
				String sdate=(EMicroUtils.display(a.getDate("FromDate")));
				SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
				  try {
					  date2=myFormat.format(fromUser.parse(sdate));
					 System.out.println(date2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				//  System.out.println(date2);
				  String emp=(Integer.toString(a.getInt("Pernr")));
				  String stim="select  SUBSTRING(Shift, 3, 4)as Shift,CONVERT(VARCHAR(5),In_time,108) as In_time,CONVERT(VARCHAR(5),Out_time,108)as Out_time,Total,Status  from Report_Daily_wise where pernr='"+emp+"' and Date='"+date2+"'";
				  ResultSet a1 = ad.selectQuery(stim);
					while (a1.next()) {
							  pendAppForm.setShift(a1.getString("Shift"));
							  pendAppForm.setComFromtime(a1.getString("In_time"));
							  pendAppForm.setComTotime(a1.getString("Out_time"));
							  pendAppForm.setToPlace(a1.getString("Total"));
							  pendAppForm.setSecstatus(a1.getString("Status"));
						
						}  
				
				status = a.getString("ApprvrStatus");
				reqby = a.getInt("RequestedBy");
				l.setSlmindur(j);
				l.setRequestNumber(a.getInt("Req_no"));
				l.setEmployeeNumber(Integer.toString(a.getInt("Pernr")));
				l.setEmployeeName(a.getString("EMP_FULLNAME"));
				l.setDepartment(a.getString("DPTSTXT"));
				l.setDesignation(a.getString("DSGSTXT"));
				l.setNofhrs(a.getString("NoHRS"));
				String app = a.getString("Applicabale");
				if (app.equalsIgnoreCase("BS"))
					app = "Before Shift Start Time";
				if (app.equalsIgnoreCase("AS"))
					app = "After Shift End Time";
				pendAppForm.setReasonType(a.getString("compreason"));

				l.setShift(app);
				++j;
				comp.add(l);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("comp", comp);

		
		// plant
		String app = "";
		// PLANT HEAD
		
		
		
		if (status.equalsIgnoreCase("Pending")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Comp-Off/OT' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Comp-Off/OT' and a.Req_Id='"
						+ requesterNo
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Comp-Off/OT' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				

		}
		List appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(app);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					appr.setApproveDate("");
					appr.setComments("");
				}
				if (approvaStatus.equals("Approved")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
		
				if(duplicate!=null)
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
				appList.add(appr);
				duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("updatecompoff");
	}
	
	public ActionForward updatecompoff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){


		LeaveForm pendAppForm = (LeaveForm) form;
		String reqId = request.getParameter("reqno");
		String hrs = request.getParameter("hrs");
		String app = request.getParameter("appl");

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int j = 1;
		int reqby = 0;
		LinkedList comp = new LinkedList();
		String status = "";
		int requesterNo = Integer.parseInt(reqId);
		String a ="update comp_ot set NoHRS='"+hrs+"', Applicabale='"+app+"' where Req_no='"+reqId+"'";
		int k=ad.SqlExecuteUpdate(a);
		if (k>0){
			
			pendAppForm.setMessage2("Updated Sucessfully for Req No. "+reqId+" ");
		}else{
			pendAppForm.setMessage("Error Occured");
		}
		
		pendAppForm.setRequestNumber(Integer.parseInt(reqId));
		viewupdatecompoff(mapping,form,request,response);

		return mapping.findForward("updatecompoff");
	}

	
	
	public ActionForward Updatecomp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LeaveForm masterForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		String pern[] = request.getParameterValues("employeeNumber");
		String pernarray = "";
		for (int j = 0; j < pern.length; j++) {
			pernarray = pernarray + "," + pern[j];
		}
		pernarray = pernarray.substring(1, pernarray.length());

		String hrs[] = request.getParameterValues("nofhrs");
		String shft[] = request.getParameterValues("appl");
		int req = masterForm.getRequestNumber();
		System.out.println(req);

		String param = "Submit";

		int reqid = 0;
		String max = "select max(Req_no) from  COMP_OT";
		ResultSet rs = ad.selectQuery(max);
		try {
			while (rs.next()) {
				reqid = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reqid = reqid + 1;

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String submitDate = ft.format(dNow);
		int j = 0;

		String strtdate[] = masterForm.getStartDate().split("/");
		String fromdate = strtdate[2] + "-" + strtdate[1] + "-" + strtdate[0];
		String thirtyFromdat = "";
		String thirtyprevdays="";
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(ft.parse(fromdate));
			cal.add(Calendar.DATE, 30);
			thirtyFromdat = ft.format(cal.getTime());
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(ft.parse(fromdate));
			cal1.add(Calendar.DATE, -30);
			thirtyprevdays = ft.format(cal1.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String enddate[] = masterForm.getStartDate().split("/");
		String todate = enddate[2] + "-" + enddate[1] + "-" + enddate[0];

		synchronized (this) {

			int userpresent = 0;
			int otpresent = 0;
			String users = "";
			String otusers = "";
			// /chk comp already der for user in the day

			String chk = "select pernr from COMP_OT where  Pernr in ("
					+ pernarray + ") and (('" + fromdate
					+ "' between FromDate and ToDate) or  ('" + todate
					+ "' between FromDate and ToDate) or (FromDate between '"+ fromdate+"' and '"+ todate+"') or (ToDate between '"+ fromdate+"' and '"+ todate+"')) and ApprvrStatus not in ('Rejected','Draft')";
			ResultSet rschk = ad.selectQuery(chk);
			try {
				while (rschk.next()) {
					userpresent = rschk.getInt(1);
					users = users + "," + rschk.getString("pernr");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!users.equalsIgnoreCase(""))
				users = users.substring(1, users.length());
			
			
			//ot present
			String chk1 = "select distinct pernr from OVER_TIME_DETAILS where  Pernr in ("
					+ pernarray + ") and (('" + fromdate
					+ "' between FromDate and ToDate) or  ('" + todate
					+ "' between FromDate and ToDate) or (FromDate between '"+ fromdate+"' and '"+ todate+"') or (ToDate between '"+ fromdate+"' and '"+ todate+"')) and ApprvrStatus!='Rejected'";
			ResultSet rschk1 = ad.selectQuery(chk1);
			try {
				while (rschk1.next()) {
					otpresent = rschk1.getInt(1);
					otusers = otusers + "," + rschk1.getString("pernr");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (!otusers.equalsIgnoreCase(""))
				otusers = otusers.substring(1, otusers.length());
			
			if(otpresent==0)
			{
			if (userpresent == 0) {

				if (param.equalsIgnoreCase("Submit")) {
					
					// delete
					String dele = "delete COMP_OT where Req_no='" + req + "'";
					int j1 = ad.SqlExecuteUpdate(dele);
					if (j1 > 0) {

					for (int i = 0; i < pern.length; i++) {
 
						int maxr = 0;
						String maxreq = "select MAX(SAP_ReqNo)as req  from COMP_OT ";
						ResultSet rsrt = ad.selectQuery(maxreq);
						try {
							while (rsrt.next()) {
								maxr = rsrt.getInt("req");

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						maxr = maxr + 1;
						double noodays = 0;
						if (hrs[i].equalsIgnoreCase("4"))
							noodays = 0.5;
						else
							noodays = 1;
						
						

						String update = "insert into COMP_OT(Req_no,FromDate,ToDate,Pernr,NoHRS,Applicabale,ApprvrStatus,RequestedBy,RequestedDate,Reason,SAP_ReqNo,SAP_Approved,Comp_Availed,Comp_Lapsed,LapsBydate,NofDaysRemaining,SAP_Prev_date) values('"
								+ reqid
								+ "','"
								+ fromdate
								+ "','"
								+ todate
								+ "','"
								+ pern[i]
								+ "','"
								+ hrs[i]
								+ "','"
								+ shft[i]
								+ "','Pending','"
								+ user.getEmployeeNo()
								+ "','"
								+ submitDate
								+ "','"
								+ masterForm.getReason()
								+ "','"
								+ maxr
								+ "','0','0','0','"
								+ thirtyFromdat
								+ "','"
								+ noodays + "','"+thirtyprevdays+"')";
						j = ad.SqlExecuteUpdate(update);

					}
					if (j > 0) {
						String planthead = "";
						// plant head
						String plant = "select * from PLANT_HEAD_COMP_OT where Plant=(select LOCATION_CODE from Location where LOCID='"
								+ user.getPlantId() + "')";
						ResultSet a = ad.selectQuery(plant);
						try {
							while (a.next()) {
								planthead = a.getString("PERNR");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
								+ reqid
								+ "','Comp-Off/OT','"
								+ user.getEmployeeNo()
								+ "','"
								+ submitDate
								+ "','Pending','No','"
								+ planthead
								+ "','No','"
								+ user.getEmployeeNo() + "')";
						j = ad.SqlExecuteUpdate(insert);
						masterForm.setMessage("Request No. '" + reqid
								+ "' Submitted ");
					} else {
						masterForm.setMessage2("Error While Submitting ..");
					}
					}
				} 

			} else {
				masterForm
						.setMessage2("Request already applied for the selected date for the Emp No. '"
								+ users + "' ");
			}
			
			}
			else
			{
				masterForm
				.setMessage2("OverTime Request already applied for the selected date for the Emp No. '"
						+ otusers + "' ");
			}

		}

		masterForm.setStartDate("");
		masterForm.setEndDate("");
		masterForm.setReason("");
		masterForm.setEmployeeNumber("");
		// PLANT HEAD

		String plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
				+ user.getPlantId()
				+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
		ResultSet rsit = ad.selectQuery(plant);
		try {
			while (rsit.next()) {
				masterForm.setEmployeeName(rsit.getString("EMP_FULLNAME"));
				masterForm.setEmployeeNumber(rsit.getString("PERNR"));
				masterForm.setDepartment(rsit.getString("DPTSTXT"));
				masterForm.setDesignation(rsit.getString("DSGSTXT"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return mapping.findForward("updatecomoff");
	}

	public ActionForward savecompDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("newcomp");
	}

	public ActionForward displaycompreport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {

			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		leaveForm.setEmployeeName(user.getFullName());
		leaveForm.setEmployeeNumber(user.getEmployeeNo());
		leaveForm.setYear(year);
		String emp = "Select dep.DPTSTXT,desg.DSGSTXT,emp.DOJ from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg where PERNR='"
				+ user.getEmployeeNo()
				+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID ";

		ResultSet rs1 = ad.selectQuery(emp);

		try {
			while (rs1.next())

			{
				leaveForm.setDepartment(rs1.getString("DPTSTXT"));
				leaveForm.setDesignation(rs1.getString("DSGSTXT"));
				leaveForm.setDoj(EMicroUtils.display1(rs1.getDate("DOJ")));
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		String startdate = "" + year + "-01-01";
		String enddate = "" + year + "-12-31";
		LinkedList comp = new LinkedList();
		String a = "select c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.ApprvdDate  from COMP_OT c where pernr='"
				+ user.getEmployeeNo()
				+ "' and c.ApprvrStatus='Approved' and FromDate between '"
				+ startdate
				+ "' and '"
				+ enddate
				+ "'   group by c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.ApprvdDate   ";
		ResultSet rsit = ad.selectQuery(a);
		try {
			while (rsit.next()) {
				LeaveForm leave = new LeaveForm();
				leave.setRequestNumber(rsit.getInt("Req_no"));
				leave.setSubmitDate(EMicroUtils.display(rsit
						.getDate("RequestedDate")));
				leave.setStatus(rsit.getString("ApprvrStatus"));
				leave.setStartDate(EMicroUtils.display(rsit.getDate("FromDate")));
				leave.setEndDate(EMicroUtils.display(rsit.getDate("ToDate")));
				int lengt = rsit.getString("reason").length();
				if (lengt > 30)
					leave.setReason(rsit.getString("reason").substring(0, 30));
				else
					leave.setReason(rsit.getString("reason"));
				leave.setApprovedDate(EMicroUtils.display(rsit
						.getDate("ApprvdDate")));

				comp.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (comp.size() == 0) {
			request.setAttribute("nocomp", "nocomp");
		} else {
			request.setAttribute("comp", comp);
		}

		return mapping.findForward("compffreport");
	}

	public ActionForward editcompoff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LeaveForm pendAppForm = (LeaveForm) form;
		String reqId = request.getParameter("requstNo");
		pendAppForm.setRequestNumber(Integer.parseInt(reqId));

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int j = 1;
		LinkedList comp = new LinkedList();
		String status = "";
		int requesterNo = Integer.parseInt(reqId);
		String getcomoff = "select c.*,e.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from COMP_OT c,emp_official_info e,DEPARTMENT dept,DESIGNATION desg where Req_no='"
				+ requesterNo
				+ "' and e.PERNR=c.Pernr and dept.DPTID=e.DPTID and desg.DSGID=e.DSGID";
		ResultSet a = ad.selectQuery(getcomoff);
		try {
			while (a.next()) {

				LeaveForm l = new LeaveForm();
				pendAppForm.setStartDate(EMicroUtils.display(a
						.getDate("FromDate")));
				pendAppForm
						.setEndDate(EMicroUtils.display(a.getDate("ToDate")));
				pendAppForm.setReason(a.getString("Reason"));
				status = a.getString("ApprvrStatus");
				l.setSlmindur(j);
				l.setEmployeeNumber(Integer.toString(a.getInt("Pernr")));
				l.setEmployeeName(a.getString("EMP_FULLNAME"));
				l.setDepartment(a.getString("DPTSTXT"));
				l.setDesignation(a.getString("DSGSTXT"));
				l.setNofhrs(a.getString("NoHRS"));
				String app = a.getString("Applicabale");

				l.setShift(app);
				++j;
				comp.add(l);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("comp", comp);

		// PLANT HEAD

		String plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCATION_CODE from Location where LOCID='"
				+ user.getPlantId()
				+ "') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
		ResultSet rsit = ad.selectQuery(plant);
		try {
			while (rsit.next()) {
				pendAppForm.setEmployeeName(rsit.getString("EMP_FULLNAME"));
				pendAppForm.setEmployeeNumber(rsit.getString("PERNR"));
				pendAppForm.setDepartment(rsit.getString("DPTSTXT"));
				pendAppForm.setDesignation(rsit.getString("DSGSTXT"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("updatecomoff");

	}

	public ActionForward viewcompoff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LeaveForm pendAppForm = (LeaveForm) form;
		String reqId = request.getParameter("requstNo");

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int j = 1;
		int reqby = 0;
		LinkedList comp = new LinkedList();
		String status = "";
		int requesterNo = Integer.parseInt(reqId);
		String getcomoff = "select c.*,e.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,COMP_OT_TYPE.Reason as compreason from COMP_OT c left outer join COMP_OT_TYPE on COMP_OT_TYPE.id=c.Comp_type,emp_official_info e,DEPARTMENT dept,DESIGNATION desg where Req_no='"
				+ requesterNo
				+ "' and e.PERNR=c.Pernr and dept.DPTID=e.DPTID and desg.DSGID=e.DSGID";
		ResultSet a = ad.selectQuery(getcomoff);
		try {
			while (a.next()) {

				LeaveForm l = new LeaveForm();
				pendAppForm.setStartDate(EMicroUtils.display(a
						.getDate("FromDate")));
				pendAppForm
						.setEndDate(EMicroUtils.display(a.getDate("ToDate")));
				pendAppForm.setReason(a.getString("Reason"));
				status = a.getString("ApprvrStatus");
				reqby = a.getInt("RequestedBy");
				l.setSlmindur(j);
				l.setEmployeeNumber(Integer.toString(a.getInt("Pernr")));
				l.setEmployeeName(a.getString("EMP_FULLNAME"));
				l.setDepartment(a.getString("DPTSTXT"));
				l.setDesignation(a.getString("DSGSTXT"));
				l.setNofhrs(a.getString("NoHRS"));
				String app = a.getString("Applicabale");
				if (app.equalsIgnoreCase("BS"))
					app = "Before Shift Start Time";
				if (app.equalsIgnoreCase("AS"))
					app = "After Shift End Time";
				pendAppForm.setReasonType(a.getString("compreason"));

				l.setShift(app);
				++j;
				comp.add(l);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("comp", comp);

		
		// plant
		String app = "";
		// PLANT HEAD
		
		
		
		if (status.equalsIgnoreCase("Pending")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Comp-Off/OT' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Comp-Off/OT' and a.Req_Id='"
						+ requesterNo
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Comp-Off/OT' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				

		}
		List appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(app);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					appr.setApproveDate("");
					appr.setComments("");
				}
				if (approvaStatus.equals("Approved")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
		
				if(duplicate!=null)
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
				appList.add(appr);
				duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.findForward("viewcomoff");
	}
	
	public ActionForward selectOTCancelRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LeaveForm pendAppForm = (LeaveForm) form;
		String reqId = request.getParameter("requstNo");
		pendAppForm.setId(reqId);
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int j = 1;
		int reqby = 0;
		LinkedList comp = new LinkedList();
		String status = "";
		int requesterNo = Integer.parseInt(reqId);
		String reqmon="";
		String reqyear="";
		String reqstart="";
		String reqend="";
		String reqstart1="";
		String reqend1="";
		String reqmonname="";
		
		
		String getcomoff = "select right(left(convert(varchar(11),c.FromDate,106) ,6),3)as monname,day(c.FromDate) as startday,day(c.ToDate) as enday,right('0' + rtrim(month(c.FromDate)),2) as mon,YEAR(c.FromDate) as year,c.*,e.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from OVER_TIME_DETAILS c,emp_official_info e,DEPARTMENT dept,DESIGNATION desg where Req_no='"
				+ requesterNo
				+ "' and e.PERNR=c.Pernr and dept.DPTID=e.DPTID and desg.DSGID=e.DSGID";
		ResultSet a = ad.selectQuery(getcomoff);
		try {
			while (a.next()) {

				LeaveForm l = new LeaveForm();
				pendAppForm.setStartDate(EMicroUtils.display(a
						.getDate("FromDate")));
				pendAppForm
						.setEndDate(EMicroUtils.display(a.getDate("ToDate")));
				pendAppForm.setReason(a.getString("Reason"));
				if(a.getInt("cancelflag")==0)
				{
					request.setAttribute("cancelbutton", "cancelbutton");
				}
				reqmonname=a.getString("monname");
				reqmon=a.getString("mon");
				reqyear=a.getString("year");
				status = a.getString("ApprvrStatus");
				reqby = a.getInt("RequestedBy");
				reqstart1=display1(a.getDate("FromDate"));
				reqstart=a.getString("FromDate");
				reqend1=display1(a.getDate("ToDate"));
				reqend=a.getString("ToDate");
				l.setSlmindur(j);
				l.setEmployeeNumber(Integer.toString(a.getInt("Pernr")));
				l.setEmployeeName(a.getString("EMP_FULLNAME"));
				l.setDepartment(a.getString("DPTSTXT"));
				l.setDesignation(a.getString("DSGSTXT"));
				l.setNofhrs(a.getString("NoHRS"));
		/*		String app = a.getString("Applicabale");
				if (app.equalsIgnoreCase("BS"))
					app = "Before Shift Start Time";
				if (app.equalsIgnoreCase("AS"))
					app = "After Shift End Time";

				l.setShift(app);*/
				++j;
				comp.add(l);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("comp", comp);

		// plant
		String app = "";
		// PLANT HEAD
		
		
		
		if (status.equalsIgnoreCase("Pending")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='OverTime' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='OverTime' and a.Req_Id='"
						+ requesterNo
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='OverTime' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				

		}
		List appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(app);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					appr.setApproveDate("");
					appr.setComments("");
				}
				if (approvaStatus.equals("Approved")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if(duplicate!=null)
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
					appList.add(appr);
					duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		if(true)
		{
			//Attendance

			SAPAttendenceDAO dao = new SAPAttendenceDAO();

			String month=reqyear+reqmon+"01";



			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date datenow = new Date();
			String time = sdf.format(datenow);

			String today[]=time.split("-");

			int datetoday=Integer.parseInt(today[2]);
			String monthtoday=today[1];
			String monName=monthtoday;

			if (monName.equals("01"))
				monName = "Jan";
			if (monName.equals("02"))
				monName = "Feb";
			if (monName.equals("03"))
				monName = "Mar";
			if (monName.equals("04"))
				monName = "Apr";
			if (monName.equals("05"))
				monName = "May";
			if (monName.equals("06"))
				monName = "Jun";
			if (monName.equals("07"))
				monName = "Jul";
			if (monName.equals("08"))
				monName = "Aug";
			if (monName.equals("09"))
				monName = "Sep";
			if (monName.equals("10"))
				monName = "Oct";
			if (monName.equals("11"))
				monName = "Nov";
			if (monName.equals("12"))
				monName = "Dec";



			LinkedList attDataList = new LinkedList();
			Calendar cal = Calendar.getInstance();



			String calendarmon=monName;
			String mon=month.substring(0, 3);
			String year = month.substring(month.length() - 4, month.length());

			/*
			 * String reqyear=month.substring(month.length()-2, month.length());
			 * String reqmonth=monName+reqyear; AttendenceForm attendenceForm = new
			 * AttendenceForm(); attendenceForm.setEmpcode(empNo);
			 * attendenceForm.setDat(reqmonth); AttendenceAction a4=new
			 * AttendenceAction(); if(monName.equalsIgnoreCase("Nov")) {
			 * a4.submit(mapping, attendenceForm, request, response); return
			 * mapping.findForward("display"); }
			 */




			if (monName.equals("Jan"))
				monName = "01";
			if (monName.equals("Feb"))
				monName = "02";
			if (monName.equals("Mar"))
				monName = "03";
			if (monName.equals("Apr"))
				monName = "04";
			if (monName.equals("May"))
				monName = "05";
			if (monName.equals("Jun"))
				monName = "06";
			if (monName.equals("Jul"))
				monName = "07";
			if (monName.equals("Aug"))
				monName = "08";
			if (monName.equals("Sep"))
				monName = "09";
			if (monName.equals("Oct"))
				monName = "10";
			if (monName.equals("Nov"))
				monName = "11";
			if (monName.equals("Dec"))
				monName = "12";

			String date ="";	




			ArrayList att = new ArrayList();

			cal.set(Integer.parseInt(year), Integer.parseInt(monName)-1, 1);
			int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			int i=1;
			/*    String query="EXEC daily_report_status_Att '"+days+"','"+monName+"','"+year+"','"+empNo+"' ";
			int k=ad.SqlExecuteUpdate(query);*/

				String s9 = " select date,convert(varchar(11),date,106) as date1,left(DATENAME(WEEKDAY,date),3) as day,left(In_time,5) as intime,left(out_time,5) as outtime,"
						+ "left(status,2) as instatus,right(status,2) as outstatus,shift from Report_Daily_wise where Pernr='"+user.getEmployeeNo()+"'  and year(date)='"+reqyear+"' "
								+ "and date between '"+reqstart1+"' and '"+reqend1+"' order by date";
				ResultSet rs9 = ad.selectQuery(s9);
				try {
					while(rs9.next())
					{
						
						SAPAttendenceForm help = new SAPAttendenceForm();
						help.setDate(rs9.getString("date1"));
						help.setDay(rs9.getString("day"));
						date=rs9.getString("date");
						
						if(!(rs9.getString("intime").equalsIgnoreCase("00:00") && rs9.getString("instatus").equalsIgnoreCase("AA")))
			    			help.setiNTIME(rs9.getString("intime"));


				    	if(!(rs9.getString("outtime").equalsIgnoreCase("00:00") && rs9.getString("outstatus").equalsIgnoreCase("AA")))
						help.setoUTTIME(rs9.getString("outtime"));

				    	if(monName.equalsIgnoreCase(monthtoday))
				    	{
			            if(datetoday>=i)
			            {
			            	help.setiNSTATUS(rs9.getString("instatus"));
			            	if(datetoday==i)
			            	{
			            		if(rs9.getString("outstatus").equalsIgnoreCase("AA"))
			            		{
			            			help.setoUTSTATUS("");
			            		}
			            		else
			            		{
			            			help.setoUTSTATUS(rs9.getString("outstatus"));
			            		}
			            	}
			            	else
			            	{
			            		help.setoUTSTATUS(rs9.getString("outstatus"));
			            	}
							
							
							switch(rs9.getString("shift")){
							  case "0001": help.setShift("GS");
							  break;
							  case "0002": help.setShift("GS");
							  break;
							  case "0003": help.setShift("FS");
							  break;
							  case "0004": help.setShift("SS");
							  break;
							  case "0005": help.setShift("TS");
							  break;
							  case "0006": help.setShift("SS");
							  break;
							  case "0007": help.setShift("FS");
							  break;
							  case "0008": help.setShift("SS");
							  break;
							  case "0009": help.setShift("TS");
							  break;
							  case "0010": help.setShift("NS");
							  break;
							  case "0011": help.setShift("SS");
							  break;
							  case "0012": help.setShift("Engg B");
							  break;
							  case "0013": help.setShift("Engg C");
							  break;
							  case "0014": help.setShift("Sec A");
							  break;
							  case "0015": help.setShift("Sec B");
							  break;
							  case "0016": help.setShift("Sec C");
							  break;

						}
						


					
			                	
			            }
			            else
				    	{
				    		
			            	help.setiNSTATUS("");
							help.setoUTSTATUS("");	
			                  
				    	}
			            
			    	}
				    	else
				    	{
				    		
				    		help.setiNSTATUS(rs9.getString("instatus"));
							help.setoUTSTATUS(rs9.getString("outstatus"));
							
							switch(rs9.getString("shift")){
							  case "0001": help.setShift("GS");
							  break;
							  case "0002": help.setShift("GS");
							  break;
							  case "0003": help.setShift("FS");
							  break;
							  case "0004": help.setShift("SS");
							  break;
							  case "0005": help.setShift("TS");
							  break;
							  case "0006": help.setShift("SS");
							  break;
							  case "0007": help.setShift("FS");
							  break;
							  case "0008": help.setShift("SS");
							  break;
							  case "0009": help.setShift("TS");
							  break;
							  case "0010": help.setShift("NS");
							  break;
							  case "0011": help.setShift("SS");
							  break;
							  case "0012": help.setShift("Engg B");
							  break;
							  case "0013": help.setShift("Engg B");
							  break;
							  case "0014": help.setShift("Sec A");
							  break;
							  case "0015": help.setShift("Sec B");
							  break;
							  case "0016": help.setShift("Sec C");
							  break;

									

						}
						            
				    	}
					   

				    	String data = "select Approvel_Status,reason from leave_details where  user_id='"
								+ user.getEmployeeNo()
								+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
								+  date 
								+ "' between start_date and  end_date and YEAR(start_date)='"
								+ year + "'";
						ResultSet rs10 = ad.selectQuery(data);
						while (rs10.next()) {
							if (rs10.getString("Approvel_Status").equalsIgnoreCase(
									"Approved"))
								help.setRemarks(rs10.getString("reason"));
							else
								help.setRemarks("Leave to be Approved");
						}

						String data1 = "select Approver_Status,reason from OnDuty_details where  user_id='"
								+ user.getEmployeeNo()
								+ "' and Approver_Status in('Approved','In Process') and '"
								+ date
								+ "' between start_date and  end_date  and YEAR(start_date)='"
								+ year + "'";
						ResultSet rs101 = ad.selectQuery(data1);
						while (rs101.next()) {
							if (rs101.getString("Approver_Status")
									.equalsIgnoreCase("Approved"))
								help.setRemarks(rs101.getString("reason"));
							else
								help.setRemarks("Onduty to be Approved");
						} 


						



						String data11 = "select * from holidays where date = '"+date+"' and location = '"+user.getPlantId()+"' ";

						ResultSet rs1011 = ad.selectQuery(data11);
						try {
							while (rs1011.next()) {

									help.setRemarks(rs1011.getString("Holiday_Name"));
									help.setMessage("HOL");
									

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					i++;
					 
					attDataList.add(help);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				pendAppForm.setAppType("Apply");
				if (attDataList.size() > 0) {
					request.setAttribute("attDataList", attDataList);
				}
		}
		return mapping.findForward("displayOTCancelRequest");
	}
	public ActionForward selectCompoffCancelRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		LeaveForm pendAppForm = (LeaveForm) form;
		String reqId = request.getParameter("requstNo");
		pendAppForm.setId(reqId);
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String reqmon="";
		String reqyear="";
		String reqstart="";
		String reqend="";
		String reqstart1="";
		String reqend1="";
		String reqmonname="";
		int j = 1;
		int reqby = 0;
		LinkedList comp = new LinkedList();
		String status = "";
		int requesterNo = Integer.parseInt(reqId);
		String getcomoff = "select right(left(convert(varchar(11),c.FromDate,106) ,6),3)as monname,day(c.FromDate) as startday,day(c.ToDate) as enday,right('0' + rtrim(month(c.FromDate)),2) as mon,YEAR(c.FromDate) as year,c.*,e.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,COMP_OT_TYPE.Reason as compreason from COMP_OT c left outer join COMP_OT_TYPE on COMP_OT_TYPE.id=c.Comp_type,emp_official_info e,DEPARTMENT dept,DESIGNATION desg where Req_no='"
				+ requesterNo
				+ "' and e.PERNR=c.Pernr and dept.DPTID=e.DPTID and desg.DSGID=e.DSGID";
		ResultSet a = ad.selectQuery(getcomoff);
		try {
			while (a.next()) {

				LeaveForm l = new LeaveForm();
				if(a.getInt("cancelflag")==0)
				{
					request.setAttribute("cancelbutton", "cancelbutton");
				}
				reqmonname=a.getString("monname");
				reqmon=a.getString("mon");
				reqyear=a.getString("year");
				pendAppForm.setStartDate(EMicroUtils.display(a
						.getDate("FromDate")));
				pendAppForm
						.setEndDate(EMicroUtils.display(a.getDate("ToDate")));
				pendAppForm.setReason(a.getString("Reason"));
				status = a.getString("ApprvrStatus");
				reqby = a.getInt("RequestedBy");
				reqstart1=display1(a.getDate("FromDate"));
				reqstart=a.getString("FromDate");
				reqend1=display1(a.getDate("ToDate"));
				reqend=a.getString("ToDate");
				l.setSlmindur(j);
				l.setEmployeeNumber(Integer.toString(a.getInt("Pernr")));
				l.setEmployeeName(a.getString("EMP_FULLNAME"));
				l.setDepartment(a.getString("DPTSTXT"));
				l.setDesignation(a.getString("DSGSTXT"));
				l.setNofhrs(a.getString("NoHRS"));
				String app = a.getString("Applicabale");
				if (app.equalsIgnoreCase("BS"))
					app = "Before Shift Start Time";
				if (app.equalsIgnoreCase("AS"))
					app = "After Shift End Time";
				pendAppForm.setReasonType(a.getString("compreason"));

				l.setShift(app);
				++j;
				comp.add(l);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("comp", comp);

		
		// plant
		String app = "";
		// PLANT HEAD
		
		
		
		if (status.equalsIgnoreCase("Pending")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Comp-Off/OT' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='Comp-Off/OT' and a.Req_Id='"
						+ requesterNo
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='Comp-Off/OT' and a.Req_Id='"
					+ requesterNo
					+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";
				

		}
		List appList = new LinkedList();
		ResultSet rsAppr = ad.selectQuery(app);
		String duplicate="";
		try {
			while (rsAppr.next()) {
				ApprovalsForm appr = new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DSGSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus = rsAppr.getString("Req_Status");
				if (approvaStatus.equals("Pending")) {
					appr.setApproveDate("");
					appr.setComments("");
				}
				if (approvaStatus.equals("Approved")
						|| approvaStatus.equals("Completed")) {
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if (approvaStatus.equals("Rejected")
						|| approvaStatus.equals("Cancelled")) {
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
		
				if(duplicate!=null)
				if(!duplicate.equalsIgnoreCase(rsAppr.getString("approved_date")))
				appList.add(appr);
				duplicate=rsAppr.getString("approved_date");
			}
			request.setAttribute("appList", appList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(true)
		{
			//Attendance

			SAPAttendenceDAO dao = new SAPAttendenceDAO();

			String month=reqyear+reqmon+"01";



			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date datenow = new Date();
			String time = sdf.format(datenow);

			String today[]=time.split("-");

			int datetoday=Integer.parseInt(today[2]);
			String monthtoday=today[1];
			String monName=monthtoday;

			if (monName.equals("01"))
				monName = "Jan";
			if (monName.equals("02"))
				monName = "Feb";
			if (monName.equals("03"))
				monName = "Mar";
			if (monName.equals("04"))
				monName = "Apr";
			if (monName.equals("05"))
				monName = "May";
			if (monName.equals("06"))
				monName = "Jun";
			if (monName.equals("07"))
				monName = "Jul";
			if (monName.equals("08"))
				monName = "Aug";
			if (monName.equals("09"))
				monName = "Sep";
			if (monName.equals("10"))
				monName = "Oct";
			if (monName.equals("11"))
				monName = "Nov";
			if (monName.equals("12"))
				monName = "Dec";



			LinkedList attDataList = new LinkedList();
			Calendar cal = Calendar.getInstance();



			String calendarmon=monName;
			String mon=month.substring(0, 3);
			String year = month.substring(month.length() - 4, month.length());

			/*
			 * String reqyear=month.substring(month.length()-2, month.length());
			 * String reqmonth=monName+reqyear; AttendenceForm attendenceForm = new
			 * AttendenceForm(); attendenceForm.setEmpcode(empNo);
			 * attendenceForm.setDat(reqmonth); AttendenceAction a4=new
			 * AttendenceAction(); if(monName.equalsIgnoreCase("Nov")) {
			 * a4.submit(mapping, attendenceForm, request, response); return
			 * mapping.findForward("display"); }
			 */




			if (monName.equals("Jan"))
				monName = "01";
			if (monName.equals("Feb"))
				monName = "02";
			if (monName.equals("Mar"))
				monName = "03";
			if (monName.equals("Apr"))
				monName = "04";
			if (monName.equals("May"))
				monName = "05";
			if (monName.equals("Jun"))
				monName = "06";
			if (monName.equals("Jul"))
				monName = "07";
			if (monName.equals("Aug"))
				monName = "08";
			if (monName.equals("Sep"))
				monName = "09";
			if (monName.equals("Oct"))
				monName = "10";
			if (monName.equals("Nov"))
				monName = "11";
			if (monName.equals("Dec"))
				monName = "12";

			String date ="";	




			ArrayList att = new ArrayList();

			cal.set(Integer.parseInt(year), Integer.parseInt(monName)-1, 1);
			int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			int i=1;
			/*    String query="EXEC daily_report_status_Att '"+days+"','"+monName+"','"+year+"','"+empNo+"' ";
			int k=ad.SqlExecuteUpdate(query);*/

				String s9 = " select date,convert(varchar(11),date,106) as date1,left(DATENAME(WEEKDAY,date),3) as day,left(In_time,5) as intime,left(out_time,5) as outtime,"
						+ "left(status,2) as instatus,right(status,2) as outstatus,shift from Report_Daily_wise where Pernr='"+user.getEmployeeNo()+"'  and year(date)='"+reqyear+"' "
								+ "and date between '"+reqstart1+"' and '"+reqend1+"' order by date";
				ResultSet rs9 = ad.selectQuery(s9);
				try {
					while(rs9.next())
					{
						
						SAPAttendenceForm help = new SAPAttendenceForm();
						help.setDate(rs9.getString("date1"));
						help.setDay(rs9.getString("day"));
						date=rs9.getString("date");
						
						if(!(rs9.getString("intime").equalsIgnoreCase("00:00") && rs9.getString("instatus").equalsIgnoreCase("AA")))
			    			help.setiNTIME(rs9.getString("intime"));


				    	if(!(rs9.getString("outtime").equalsIgnoreCase("00:00") && rs9.getString("outstatus").equalsIgnoreCase("AA")))
						help.setoUTTIME(rs9.getString("outtime"));

				    	if(monName.equalsIgnoreCase(monthtoday))
				    	{
			            if(datetoday>=i)
			            {
			            	help.setiNSTATUS(rs9.getString("instatus"));
			            	if(datetoday==i)
			            	{
			            		if(rs9.getString("outstatus").equalsIgnoreCase("AA"))
			            		{
			            			help.setoUTSTATUS("");
			            		}
			            		else
			            		{
			            			help.setoUTSTATUS(rs9.getString("outstatus"));
			            		}
			            	}
			            	else
			            	{
			            		help.setoUTSTATUS(rs9.getString("outstatus"));
			            	}
							
							
							switch(rs9.getString("shift")){
							  case "0001": help.setShift("GS");
							  break;
							  case "0002": help.setShift("GS");
							  break;
							  case "0003": help.setShift("FS");
							  break;
							  case "0004": help.setShift("SS");
							  break;
							  case "0005": help.setShift("TS");
							  break;
							  case "0006": help.setShift("SS");
							  break;
							  case "0007": help.setShift("FS");
							  break;
							  case "0008": help.setShift("SS");
							  break;
							  case "0009": help.setShift("TS");
							  break;
							  case "0010": help.setShift("NS");
							  break;
							  case "0011": help.setShift("SS");
							  break;
							  case "0012": help.setShift("Engg B");
							  break;
							  case "0013": help.setShift("Engg C");
							  break;
							  case "0014": help.setShift("Sec A");
							  break;
							  case "0015": help.setShift("Sec B");
							  break;
							  case "0016": help.setShift("Sec C");
							  break;

						}
						


					
			                	
			            }
			            else
				    	{
				    		
			            	help.setiNSTATUS("");
							help.setoUTSTATUS("");	
			                  
				    	}
			            
			    	}
				    	else
				    	{
				    		
				    		help.setiNSTATUS(rs9.getString("instatus"));
							help.setoUTSTATUS(rs9.getString("outstatus"));
							
							switch(rs9.getString("shift")){
							  case "0001": help.setShift("GS");
							  break;
							  case "0002": help.setShift("GS");
							  break;
							  case "0003": help.setShift("FS");
							  break;
							  case "0004": help.setShift("SS");
							  break;
							  case "0005": help.setShift("TS");
							  break;
							  case "0006": help.setShift("SS");
							  break;
							  case "0007": help.setShift("FS");
							  break;
							  case "0008": help.setShift("SS");
							  break;
							  case "0009": help.setShift("TS");
							  break;
							  case "0010": help.setShift("NS");
							  break;
							  case "0011": help.setShift("SS");
							  break;
							  case "0012": help.setShift("Engg B");
							  break;
							  case "0013": help.setShift("Engg B");
							  break;
							  case "0014": help.setShift("Sec A");
							  break;
							  case "0015": help.setShift("Sec B");
							  break;
							  case "0016": help.setShift("Sec C");
							  break;

									

						}
						            
				    	}
					   

				    	String data = "select Approvel_Status,reason from leave_details where  user_id='"
								+ user.getEmployeeNo()
								+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
								+  date 
								+ "' between start_date and  end_date and YEAR(start_date)='"
								+ year + "'";
						ResultSet rs10 = ad.selectQuery(data);
						while (rs10.next()) {
							if (rs10.getString("Approvel_Status").equalsIgnoreCase(
									"Approved"))
								help.setRemarks(rs10.getString("reason"));
							else
								help.setRemarks("Leave to be Approved");
						}

						String data1 = "select Approver_Status,reason from OnDuty_details where  user_id='"
								+ user.getEmployeeNo()
								+ "' and Approver_Status in('Approved','In Process') and '"
								+ date
								+ "' between start_date and  end_date  and YEAR(start_date)='"
								+ year + "'";
						ResultSet rs101 = ad.selectQuery(data1);
						while (rs101.next()) {
							if (rs101.getString("Approver_Status")
									.equalsIgnoreCase("Approved"))
								help.setRemarks(rs101.getString("reason"));
							else
								help.setRemarks("Onduty to be Approved");
						} 


						



						String data11 = "select * from holidays where date = '"+date+"' and location = '"+user.getPlantId()+"' ";

						ResultSet rs1011 = ad.selectQuery(data11);
						try {
							while (rs1011.next()) {

									help.setRemarks(rs1011.getString("Holiday_Name"));
									help.setMessage("HOL");
									

							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					i++;
					 
					attDataList.add(help);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				pendAppForm.setAppType("Apply");
				if (attDataList.size() > 0) {
					request.setAttribute("attDataList", attDataList);
				}
		}
		return mapping.findForward("displayCompoffCancelRequest");
	}
	
	public ActionForward selectCancelRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
{
		

		String HRid="";
LeaveForm leaveForm=(LeaveForm)form;// TODO Auto-generated method stub
String Id=request.getParameter("requstNo");
leaveForm.setId(Id);
HttpSession session=request.getSession();
UserInfo user=(UserInfo)session.getAttribute("user");
String reqmon="";
String reqyear="";
String reqstart="";
String reqend="";
String reqstart1="";
String reqend1="";
String reqmonname="";
try 
{
	
	
	String sql="select right(left(convert(varchar(11),start_date,106) ,6),3)as monname,day(start_date) as startday,day(end_date) as enday,right('0' + rtrim(month(start_date)),2) as mon,YEAR(start_date) as year,emp.EMP_FULLNAME,desg.DSGSTXT,leave.leave_type,leave.submit_date,leave.start_duration,leave.end_duration,leave.start_date,leave.end_date,leave.reason,leave.reasonType,leave.no_of_days,leave.documents,leave.Approvel_Status,leave.approved_date,leave.rejected_date,cancelflag" +
	" from leave_details as leave,emp_official_info as emp,DESIGNATION as desg where leave.Req_Id='"+Id+"' and leave.firstname='"+user.getEmployeeNo()+"' and leave.user_id=emp.PERNR and emp.DSGID=desg.DSGID";

	ResultSet rs = ad.selectQuery(sql);
	String leaveType="";
	String userId="";
	while (rs.next()) {
		
		if(rs.getInt("cancelflag")==0)
		{
			request.setAttribute("cancelbutton", "cancelbutton");
		}
		reqmonname=rs.getString("monname");
		reqmon=rs.getString("mon");
		reqyear=rs.getString("year");
		leaveType=rs.getString("leave_type");
		if(leaveType.equalsIgnoreCase("1")){
			leaveType="Casual";
		}
		if(leaveType.equalsIgnoreCase("2")){
			leaveType="Sick";
		}
		if(leaveType.equalsIgnoreCase("3")){
			leaveType="Privilege";
		}
		if(leaveType.equalsIgnoreCase("4")){
			leaveType="Loss Of Pay";
		}
		if(leaveType.equalsIgnoreCase("5")){
			leaveType="Maternity";
		}
		if(leaveType.equalsIgnoreCase("6")){
			leaveType="Comp-Off";
		}
		leaveForm.setLeaveType(leaveType);
		leaveForm.setSubmitDate(rs.getString("submit_date"));
		String stDur=rs.getString("start_duration");
		
		if(stDur.equalsIgnoreCase("FD")){
			stDur="Full Day";
		}
		if(stDur.equalsIgnoreCase("FH")){
			stDur="First Half";
		}
		if(stDur.equalsIgnoreCase("SH")){
			stDur="Second Half";
		}
		leaveForm.setStartDurationType(stDur);
		String endDur=rs.getString("end_duration");
		
		if(endDur.equalsIgnoreCase("FD")){
			endDur="Full Day";
		}
		if(endDur.equalsIgnoreCase("FH")){
			endDur="First Half";
		}
		if(endDur.equalsIgnoreCase("SH")){
			endDur="Second Half";
		}
		leaveForm.setEndDurationType(endDur);
		leaveForm.setSubmitDate(rs.getString("submit_date"));
		reqstart1=display1(rs.getDate("start_date"));
		reqstart=rs.getString("startday");
		leaveForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
		reqend1=display1(rs.getDate("end_date"));
		reqend=rs.getString("enday");
		leaveForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
		leaveForm.setReason(rs.getString("reason"));
		leaveForm.setReasonType(rs.getString("reasonType"));
		leaveForm.setNoOfDays(rs.getString("no_of_days"));
		leaveForm.setDocumentName(rs.getString("documents"));
		
	
	//list.add(pendAppForm);
	//leave approve timings
	boolean existStatus=false;
	String reqType="";

	ArrayList paralist=new ArrayList();
	String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
	ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
	while(rsCheckInEss.next())
	{
		int number=rsCheckInEss.getInt(1);
		reqType=rsCheckInEss.getString("reqType");
		if(number==0)
		{
			existStatus=false;
		}else{
			existStatus=true;
		}
	}
	
		if(reqType.equalsIgnoreCase("sequential"))
		{
		String getApproverDesig=" select e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
	"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' order by Priority ";
		 
		ResultSet rsA=ad.selectQuery(getApproverDesig);
			while(rsA.next())
			{
				LeaveForm lvform=new LeaveForm();
				 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
				 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
				String getdetails=" select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date," +
						"all_R.rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
						" where all_R.Req_Id='"+Id+"' and all_R.Req_Type='Leave' and ((emp.PERNR=all_R.Last_Approver) or " +
						"(emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) and((Last_Approver='"+rsA.getString("ApproverId")+"') or " +
						"(Pending_Approver='"+rsA.getString("ApproverId")+"'))  and Requester_Name='"+user.getEmployeeNo()+"'";
				
				ResultSet rsApproverDsg=ad.selectQuery(getdetails);
				
				while(rsApproverDsg.next())
				{
				
					HRid=rsApproverDsg.getString("Hr_Id");
					String reqstatus=rsApproverDsg.getString("Req_Status");
					lvform.setStatus(rsApproverDsg.getString("Req_Status"));
					if(reqstatus.equalsIgnoreCase("Pending"))
					{
						
					
						
					}
					if(reqstatus.equalsIgnoreCase("Approved"))
					{
					lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
				  
				   
					String approveDate=	rsApproverDsg.getString("approved_date");
					
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					
					 lvform.setApprovedDate(approveDate);
					}
					
					if(reqstatus.equalsIgnoreCase("Rejected"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					  
					     
					     String approveDate=rsApproverDsg.getString("rejected_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							
							 lvform.setApprovedDate(approveDate);
					}
					if(reqstatus.equalsIgnoreCase("Cancelled"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
						  String approveDate=rsApproverDsg.getString("rejected_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							
							 lvform.setApprovedDate(approveDate);
					}
					lvform.setComments(rsApproverDsg.getString("Comments"));
					if(HRid!=null)
					{
						if( reqstatus.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs1=ad.selectQuery(hrdetails);
						
						while(rs1.next())
						{
							lvform.setApprover(rs1.getString("EMP_FULLNAME"));
							 lvform.setAppDesig(rs1.getString("DSGSTXT"));
						}
					}
					}
					
				
				}
				paralist.add(lvform);
			}
			
			///Actual approver 
			
			String actual="select a.*,e.EMP_FULLNAME,d.DSGSTXT from All_Request a,emp_official_info e,DESIGNATION d where Req_Id='"+Id+"' and Req_Type=' Cancel Leave'  and e.PERNR=a.Actual_Approver and e.DSGID=d.DSGID  ";
			ResultSet rsact=ad.selectQuery(actual);
			if(rsact.next())
			{
				LeaveForm l=new LeaveForm();
				l.setApprover(rsact.getString("EMP_FULLNAME"));	
			 l.setAppDesig(rsact.getString("DSGSTXT"));	
				l.setStatus(rsact.getString("Actual_Approver_status"));
				  String approveDate=rsact.getString("Actual_Approved_Date");
					
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					
					 l.setApprovedDate(approveDate);
					 l.setComments(rsact.getString("Actual_Approved_Comments"));
						paralist.add(l);
			}
			
			
			request.setAttribute("parallelapprovers", paralist);
			
		}
		else
		{
			
			String getApproverDesig="  select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date,all_R.rejected_date from " +
					"All_Request as all_R,emp_official_info as emp, DESIGNATION as desg    where all_R.Req_Id='"+Id+"' and all_R.Req_Type='Leave' and " +
					"((emp.PERNR=all_R.Last_Approver) or (emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) ";
			    ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				while(rsApproverDsg.next())
				{
					LeaveForm lvform=new LeaveForm();
					HRid=rsApproverDsg.getString("Hr_Id");
					String reqstatus=rsApproverDsg.getString("Req_Status");
					lvform.setStatus(rsApproverDsg.getString("Req_Status"));
					if(reqstatus.equalsIgnoreCase("Pending"))
					{
						
					 lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));	
						
					}
					if(reqstatus.equalsIgnoreCase("Approved"))
					{
					lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
				   
				   String approveDate=	rsApproverDsg.getString("approved_date");
					
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					
					 lvform.setApprovedDate(approveDate);
					}
					
					if(reqstatus.equalsIgnoreCase("Rejected"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					    
					     String approveDate=	rsApproverDsg.getString("rejected_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							
							 lvform.setApprovedDate(approveDate);
					}
					if(reqstatus.equalsIgnoreCase("Cancelled"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					    
					     String approveDate=	rsApproverDsg.getString("rejected_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							
							 lvform.setApprovedDate(approveDate);
					}
					lvform.setComments(rsApproverDsg.getString("Comments"));
					
					if(HRid!=null)
					{
						if( reqstatus.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs1=ad.selectQuery(hrdetails);
						
						while(rs1.next())
						{
							lvform.setApprover(rs1.getString("EMP_FULLNAME"));
							 lvform.setAppDesig(rs1.getString("DSGSTXT"));
						}
					}
					}
					
					
					paralist.add(lvform);
					
				}
				
				///Actual approver 
				
				String actual="select a.*,e.EMP_FULLNAME,d.DSGSTXT from All_Request a,emp_official_info e,DESIGNATION d where Req_Id='"+Id+"' and Req_Type='Cancel Leave'  and e.PERNR=a.Actual_Approver and e.DSGID=d.DSGID  ";
				ResultSet rsact=ad.selectQuery(actual);
				if(rsact.next())
				{
					LeaveForm l=new LeaveForm();
					l.setApprover(rsact.getString("EMP_FULLNAME"));	
				 l.setAppDesig(rsact.getString("DSGSTXT"));	
					l.setStatus(rsact.getString("Actual_Approver_status"));
					  String approveDate=rsact.getString("Actual_Approved_Date");
						
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						
						 l.setApprovedDate(approveDate);
						 l.setComments(rsact.getString("Actual_Approved_Comments"));
							paralist.add(l);
				}
				
				request.setAttribute("parallelapprovers", paralist);
		}
		
		LinkedList leaveReason=new LinkedList();
		LinkedList leaveDetReason=new LinkedList();
		
		String reason="select * from LEAVE_REASON";
		ResultSet a=ad.selectQuery(reason);
		try {
			while(a.next())
			{
		leaveReason.add(a.getString("Reason"));
		leaveDetReason.add(a.getString("Detailed_Reason"));
			}
			leaveForm.setLeaveReason(leaveReason);
			leaveForm.setLeaveDetReason(leaveDetReason);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	String sql1="select * from leave_details where id='"+Id + "' and documents not like ''";
	ResultSet rs11=ad.selectQuery(sql1);
		LeaveForm leaveForm1=null;
		ArrayList a1=new ArrayList();
		while(rs11.next()) {
			leaveForm1=new LeaveForm();
			leaveForm1.setDocumentName(rs11.getString("documents"));
			leaveForm1.setId(rs11.getString("id"));
			a1.add(leaveForm1);
		}
		request.setAttribute("documentDetails", a1);
	request.setAttribute("submitDetails", "submitDetails");
			request.setAttribute("leave", "leave");
	}}catch (Exception e) {
	e.printStackTrace();
}



if(true)
{
	//Attendance

	SAPAttendenceDAO dao = new SAPAttendenceDAO();

	String month=reqyear+reqmon+"01";



	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date datenow = new Date();
	String time = sdf.format(datenow);

	String today[]=time.split("-");

	int datetoday=Integer.parseInt(today[2]);
	String monthtoday=today[1];
	String monName=monthtoday;

	if (monName.equals("01"))
		monName = "Jan";
	if (monName.equals("02"))
		monName = "Feb";
	if (monName.equals("03"))
		monName = "Mar";
	if (monName.equals("04"))
		monName = "Apr";
	if (monName.equals("05"))
		monName = "May";
	if (monName.equals("06"))
		monName = "Jun";
	if (monName.equals("07"))
		monName = "Jul";
	if (monName.equals("08"))
		monName = "Aug";
	if (monName.equals("09"))
		monName = "Sep";
	if (monName.equals("10"))
		monName = "Oct";
	if (monName.equals("11"))
		monName = "Nov";
	if (monName.equals("12"))
		monName = "Dec";



	LinkedList attDataList = new LinkedList();
	Calendar cal = Calendar.getInstance();



	String calendarmon=monName;
	String mon=month.substring(0, 3);
	String year = month.substring(month.length() - 4, month.length());

	/*
	 * String reqyear=month.substring(month.length()-2, month.length());
	 * String reqmonth=monName+reqyear; AttendenceForm attendenceForm = new
	 * AttendenceForm(); attendenceForm.setEmpcode(empNo);
	 * attendenceForm.setDat(reqmonth); AttendenceAction a4=new
	 * AttendenceAction(); if(monName.equalsIgnoreCase("Nov")) {
	 * a4.submit(mapping, attendenceForm, request, response); return
	 * mapping.findForward("display"); }
	 */




	if (monName.equals("Jan"))
		monName = "01";
	if (monName.equals("Feb"))
		monName = "02";
	if (monName.equals("Mar"))
		monName = "03";
	if (monName.equals("Apr"))
		monName = "04";
	if (monName.equals("May"))
		monName = "05";
	if (monName.equals("Jun"))
		monName = "06";
	if (monName.equals("Jul"))
		monName = "07";
	if (monName.equals("Aug"))
		monName = "08";
	if (monName.equals("Sep"))
		monName = "09";
	if (monName.equals("Oct"))
		monName = "10";
	if (monName.equals("Nov"))
		monName = "11";
	if (monName.equals("Dec"))
		monName = "12";

	String date ="";	




	ArrayList att = new ArrayList();

	cal.set(Integer.parseInt(year), Integer.parseInt(monName)-1, 1);
	int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

	int i=1;
	/*    String query="EXEC daily_report_status_Att '"+days+"','"+monName+"','"+year+"','"+empNo+"' ";
	int k=ad.SqlExecuteUpdate(query);*/

		String s9 = " select date,convert(varchar(11),date,106) as date1,left(DATENAME(WEEKDAY,date),3) as day,left(In_time,5) as intime,left(out_time,5) as outtime,"
				+ "left(status,2) as instatus,right(status,2) as outstatus,shift from Report_Daily_wise where Pernr='"+user.getEmployeeNo()+"'  and year(date)='"+reqyear+"' "
						+ "and date between '"+reqstart1+"' and '"+reqend1+"' order by date";
		ResultSet rs9 = ad.selectQuery(s9);
		try {
			while(rs9.next())
			{
				
				SAPAttendenceForm help = new SAPAttendenceForm();
				help.setDate(rs9.getString("date1"));
				help.setDay(rs9.getString("day"));
				date=rs9.getString("date");
				
				if(!(rs9.getString("intime").equalsIgnoreCase("00:00") && rs9.getString("instatus").equalsIgnoreCase("AA")))
	    			help.setiNTIME(rs9.getString("intime"));


		    	if(!(rs9.getString("outtime").equalsIgnoreCase("00:00") && rs9.getString("outstatus").equalsIgnoreCase("AA")))
				help.setoUTTIME(rs9.getString("outtime"));

		    	if(monName.equalsIgnoreCase(monthtoday))
		    	{
	            if(datetoday>=i)
	            {
	            	help.setiNSTATUS(rs9.getString("instatus"));
	            	if(datetoday==i)
	            	{
	            		if(rs9.getString("outstatus").equalsIgnoreCase("AA"))
	            		{
	            			help.setoUTSTATUS("");
	            		}
	            		else
	            		{
	            			help.setoUTSTATUS(rs9.getString("outstatus"));
	            		}
	            	}
	            	else
	            	{
	            		help.setoUTSTATUS(rs9.getString("outstatus"));
	            	}
					
					
					switch(rs9.getString("shift")){
					  case "0001": help.setShift("GS");
					  break;
					  case "0002": help.setShift("GS");
					  break;
					  case "0003": help.setShift("FS");
					  break;
					  case "0004": help.setShift("SS");
					  break;
					  case "0005": help.setShift("TS");
					  break;
					  case "0006": help.setShift("SS");
					  break;
					  case "0007": help.setShift("FS");
					  break;
					  case "0008": help.setShift("SS");
					  break;
					  case "0009": help.setShift("TS");
					  break;
					  case "0010": help.setShift("NS");
					  break;
					  case "0011": help.setShift("SS");
					  break;
					  case "0012": help.setShift("Engg B");
					  break;
					  case "0013": help.setShift("Engg C");
					  break;
					  case "0014": help.setShift("Sec A");
					  break;
					  case "0015": help.setShift("Sec B");
					  break;
					  case "0016": help.setShift("Sec C");
					  break;

				}
				


			
	                	
	            }
	            else
		    	{
		    		
	            	help.setiNSTATUS("");
					help.setoUTSTATUS("");	
	                  
		    	}
	            
	    	}
		    	else
		    	{
		    		
		    		help.setiNSTATUS(rs9.getString("instatus"));
					help.setoUTSTATUS(rs9.getString("outstatus"));
					
					switch(rs9.getString("shift")){
					  case "0001": help.setShift("GS");
					  break;
					  case "0002": help.setShift("GS");
					  break;
					  case "0003": help.setShift("FS");
					  break;
					  case "0004": help.setShift("SS");
					  break;
					  case "0005": help.setShift("TS");
					  break;
					  case "0006": help.setShift("SS");
					  break;
					  case "0007": help.setShift("FS");
					  break;
					  case "0008": help.setShift("SS");
					  break;
					  case "0009": help.setShift("TS");
					  break;
					  case "0010": help.setShift("NS");
					  break;
					  case "0011": help.setShift("SS");
					  break;
					  case "0012": help.setShift("Engg B");
					  break;
					  case "0013": help.setShift("Engg B");
					  break;
					  case "0014": help.setShift("Sec A");
					  break;
					  case "0015": help.setShift("Sec B");
					  break;
					  case "0016": help.setShift("Sec C");
					  break;

							

				}
				            
		    	}
			   

		    	String data = "select Approvel_Status,reason from leave_details where  user_id='"
						+ user.getEmployeeNo()
						+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
						+  date 
						+ "' between start_date and  end_date and YEAR(start_date)='"
						+ year + "'";
				ResultSet rs10 = ad.selectQuery(data);
				while (rs10.next()) {
					if (rs10.getString("Approvel_Status").equalsIgnoreCase(
							"Approved"))
						help.setRemarks(rs10.getString("reason"));
					else
						help.setRemarks("Leave to be Approved");
				}

				String data1 = "select Approver_Status,reason from OnDuty_details where  user_id='"
						+ user.getEmployeeNo()
						+ "' and Approver_Status in('Approved','In Process') and '"
						+ date
						+ "' between start_date and  end_date  and YEAR(start_date)='"
						+ year + "'";
				ResultSet rs101 = ad.selectQuery(data1);
				while (rs101.next()) {
					if (rs101.getString("Approver_Status")
							.equalsIgnoreCase("Approved"))
						help.setRemarks(rs101.getString("reason"));
					else
						help.setRemarks("Onduty to be Approved");
				} 


				



				String data11 = "select * from holidays where date = '"+date+"' and location = '"+user.getPlantId()+"' ";

				ResultSet rs1011 = ad.selectQuery(data11);
				try {
					while (rs1011.next()) {

							help.setRemarks(rs1011.getString("Holiday_Name"));
							help.setMessage("HOL");
							

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			i++;
			 
			attDataList.add(help);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		leaveForm.setAppType("Apply");
		if (attDataList.size() > 0) {
			request.setAttribute("attDataList", attDataList);
		}
}
else
{
	//Attendance

	SAPAttendenceDAO dao = new SAPAttendenceDAO();
	try
			{
	connectSAP();
	JCoDestination destination = JCoDestinationManager
			.getDestination(DESTINATION);// TODO change to real
											// destination
	if (destination == null) {

		destination = JCoDestinationManager
				.getDestination(DESTINATION);// TODO change to
												// real
												// destination
		if (destination == null) {
			throw new RuntimeException(
					"Could not connect to SAP, destination not found.");
		}
	}

	String month=reqyear+reqmon+"01";
	JCoFunction function = null;
	String pay="";
	String getPayGroup = "Select PAY_GROUP from emp_official_info where PERNR='"
			+ user.getEmployeeNo() + "'";
	ResultSet rsPayGroup = ad.selectQuery(getPayGroup);
	try {
		while (rsPayGroup.next()) {
			pay=rsPayGroup.getString("PAY_GROUP");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}


	function = destination.getRepository().getFunction(
			"ZBAPI_HR_ATTENDANCE");
	if (function == null) {
		throw new RuntimeException(
				" ZBAPI_HR_ATTENDANCE not found in SAP.");// ZBAPI_HR_PAYSLIP
	}
	function.getImportParameterList().setValue("PAYGROUP",pay);
	function.getImportParameterList().setValue("PERNR", user.getEmployeeNo());
	function.getImportParameterList().setValue("MONTH", month);

	function.execute(destination);
	LinkedList attdList = new LinkedList();
	JCoTable returnTable = function.getTableParameterList()
			.getTable("ATTNDATA");// PAYDATA_D_H
	Map<Integer, Character> returnMap = new HashMap<Integer, Character>();
	if (returnTable.getNumRows() > 0) {
		returnTable.firstRow();
		do {
			String reqDate = convertDate(returnTable
					.getDate("BEGDA"));
			String inTime = returnTable.getTime("INTIME")
					.toString();
			String a1[] = inTime.split(" ");
			if (a1.length == 6)
				inTime = a1[3];
			String outTime = returnTable.getTime("OUTTIME")
					.toString();
			String b1[] = outTime.split(" ");
			if (b1.length == 6)
				outTime = b1[3];

			SAPAttendenceForm attData = new SAPAttendenceForm();
			attData.setDate(reqDate);
			attData.setiNTIME(returnTable.getString("INTIME"));
			attData.setiNSTATUS(returnTable.getString("INSTATUS"));
			attData.setoUTTIME(returnTable.getString("OUTTIME"));
			attData.setoUTSTATUS(returnTable.getString("OUTSTATUS"));
			attdList.add(attData);

		} while (returnTable.nextRow());

		String deleteRecords = "delete from SAP_Attendence where PERNR='"
				+ user.getEmployeeNo() + "'  ";
		ad.SqlExecuteUpdate(deleteRecords);

		dao.updateAttendenceDetails(attdList, user.getEmployeeNo());

	}

	if (returnTable.getNumRows() == 0) {

		JCoTable returnStatus = function.getTableParameterList()
				.getTable("RETURN");
		if (returnStatus.getNumRows() > 0) {
			char c = returnStatus.getChar("TYPE");
			if (c == 'E') {
				leaveForm.setMessage(returnStatus
						.getString("MESSAGE"));
			}
		}

	}

			}catch (Exception e) {
				e.printStackTrace();
			}

	//DATA SET

	LinkedList attDataList = new LinkedList();
	String getAttendence = "select convert(varchar(11),s.[BEGDA],106) as date,BEGDA as rdate,DATENAME(WEEKDAY,S.[BEGDA]) as day,convert(varchar(5),s.[INTIME],"
			+ "106) as INTIME,s.INSTATUS,convert(varchar(5),s.[OUTTIME],106) as OUTTIME,s.OUTSTATUS,(SELECT Holiday_Name FROM holidays WHERE Location='"
			+ user.getPlantId()
			+ "' "
			+ "AND convert(varchar(11),s.[BEGDA],103)=convert(varchar(11),Date,103) ) as holiday from SAP_Attendence as s where s.PERNR='"
			+ user.getEmployeeNo()
			+ "' and"
			+ " MONTH(s.[BEGDA])='"
			+ reqmon
			+ "' and BEGDA between '"+reqstart1+"' and '"+reqend1+"' order by date";
	ResultSet rsAttend = ad.selectQuery(getAttendence);

	try {
		while (rsAttend.next()) {	
			SAPAttendenceForm attData = new SAPAttendenceForm();
		Date today = new Date();
		String date = today.toString();
		String a[] = date.split(" ");
		date = a[2] + " " + a[1] + " " + a[5];
		attData.setDate(rsAttend.getString("date"));
		attData.setDay(rsAttend.getString("day").substring(0, 3));
		
		/* if(!rsAttend.getString("INSTATUS").equalsIgnoreCase("")) 
				attData.setiNTIME(rsAttend.getString("INTIME"));
		if (!(rsAttend.getString("INSTATUS").equalsIgnoreCase("PP")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("AA")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("TI") || rsAttend.getString("INSTATUS").equalsIgnoreCase("TO")))
			attData.setiNTIME(rsAttend.getString("INSTATUS"));	
		if((rsAttend.getString("INSTATUS").equalsIgnoreCase("PP")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("AA")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("TI") || rsAttend.getString("INSTATUS").equalsIgnoreCase("TO")))
			attData.setiNTIME(rsAttend.getString("INTIME"));*/
		attData.setiNSTATUS(rsAttend.getString("INSTATUS"));	
		attData.setiNTIME(rsAttend.getString("INTIME"));	
		attData.setoUTTIME(rsAttend.getString("OUTTIME"));
		attData.setoUTSTATUS(rsAttend.getString("OUTSTATUS"));
		
		/* if(!rsAttend.getString("OUTSTATUS").equalsIgnoreCase("")) 
				attData.setoUTTIME(rsAttend.getString("OUTTIME"));
		
		if (!(rsAttend.getString("OUTSTATUS")
				.equalsIgnoreCase("PP")
				|| rsAttend.getString("OUTSTATUS")
						.equalsIgnoreCase("AA")
				|| rsAttend.getString("OUTSTATUS")
						.equalsIgnoreCase("TI") || rsAttend
				.getString("OUTSTATUS").equalsIgnoreCase("TO")))
			attData.setoUTTIME(rsAttend.getString("OUTSTATUS"));
		if((rsAttend.getString("OUTSTATUS")
				.equalsIgnoreCase("PP")
				|| rsAttend.getString("OUTSTATUS")
						.equalsIgnoreCase("AA")
				|| rsAttend.getString("OUTSTATUS")
						.equalsIgnoreCase("TI") || rsAttend
				.getString("OUTSTATUS").equalsIgnoreCase("TO")))
			attData.setoUTTIME(rsAttend.getString("OUTTIME"));
		*/

		String remark = rsAttend.getString("holiday");
		if (remark != null) {
			attData.setRemarks(remark);
			attData.setMessage("HOL");
		}

		String data = "select * from leave_details where  user_id='"
				+ user.getEmployeeNo()
				+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
				+ rsAttend.getString("rdate")
				+ "' between start_date and  end_date and YEAR(start_date)='"
				+ reqyear + "'";
		ResultSet rs10 = ad.selectQuery(data);
		while (rs10.next()) {
			if (rs10.getString("Approvel_Status").equalsIgnoreCase(
					"Approved"))
				attData.setRemarks(rs10.getString("reason"));
			else
				attData.setRemarks("Leave to be Approved");
		}

		String data1 = "select * from OnDuty_details where  user_id='"
				+  user.getEmployeeNo()
				+ "' and Approver_Status in('Approved','In Process') and '"
				+ rsAttend.getString("rdate")
				+ "' between start_date and  end_date  and YEAR(start_date)='"
				+ reqyear + "'";
		ResultSet rs101 = ad.selectQuery(data1);
		while (rs101.next()) {
			if (rs101.getString("Approver_Status")
					.equalsIgnoreCase("Approved"))
				attData.setRemarks(rs101.getString("reason"));
			else
				attData.setRemarks("Onduty to be Approved");
		}
		
		//chk whether he can apply
		
		
		if(rsAttend.getString("INSTATUS").equalsIgnoreCase("OD")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("LP")|| rsAttend.getString("INSTATUS").equalsIgnoreCase("CL") || rsAttend.getString("INSTATUS").equalsIgnoreCase("SL")  || rsAttend.getString("INSTATUS").equalsIgnoreCase("EL")  || rsAttend.getString("INSTATUS").equalsIgnoreCase("ML") || rsAttend.getString("INSTATUS").equalsIgnoreCase("SH"))
		{
	    leaveForm.setApptype("Apply");
		}
		if(rsAttend.getString("OUTSTATUS").equalsIgnoreCase("OD")|| rsAttend.getString("OUTSTATUS").equalsIgnoreCase("LP")|| rsAttend.getString("OUTSTATUS").equalsIgnoreCase("CL") || rsAttend.getString("OUTSTATUS").equalsIgnoreCase("SL")  || rsAttend.getString("OUTSTATUS").equalsIgnoreCase("EL")  || rsAttend.getString("OUTSTATUS").equalsIgnoreCase("ML") || rsAttend.getString("OUTSTATUS").equalsIgnoreCase("SH"))
		{
			 leaveForm.setApptype("Apply");
		}
		
		attDataList.add(attData);
		}
		if (attDataList.size() > 0) {
			request.setAttribute("attDataList", attDataList);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
		return mapping.findForward("displayCancelRequest");

		
}
	
	
private static final String DESTINATION = "SAP_DESTINATION";
	
	public static String display1(Date dbDate)
	{
	String dt="";
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	try
	{
	dt=dateFormat.format(dbDate);
	}
	catch (Exception e)
	{
	dt="";
	}
	return dt;
	}
	
	public String convertDate(Date date1) {

		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		String formatedDate = "";
		try {
			Date date = formatter.parse(date1.toString());

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			formatedDate = cal.get(Calendar.YEAR) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.DATE);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatedDate;
	}
	
	private void connectSAP() {
		try {

			Properties connectProperties = new Properties();// TODO change the
			// details
			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
					"192.168.1.2");
			connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,
					"00");
			connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,
					"900");
			connectProperties.setProperty(DestinationDataProvider.JCO_USER,
					"rfcprd");
			connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
					"Test123#");
			connectProperties.setProperty(DestinationDataProvider.JCO_LANG,
					"EN");
			File destCfg = new File(DESTINATION + ".jcoDestination");

			FileOutputStream fos = new FileOutputStream(destCfg, false);
			connectProperties.store(fos, "SAP_DESTINATION config");
			fos.close();

		} catch (Exception e) {
			throw new RuntimeException("Unable to create the destination file",
					e);
		}
	}
	public ActionForward submitOTcancelRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String Req_Id=request.getParameter("requstNo");
		
		  Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			 String submitDate = ft.format(dNow);
		
		int i=0;
		//GET HR DATA WHO CANCEL
		
		String rep=user.getRepgrp();
		String loc="";
		
		if(rep.equalsIgnoreCase("12"))
		{
			loc="34";
		}
		else
		{
			loc=user.getPlantId();
		}
		
		
		
		String as="select * from ess_cancel_appr where Location=(select LOCATION_CODE from Location where LOCID="+loc+")";
		ResultSet ads=ad.selectQuery(as);
		try {
			while(ads.next())
			{		
			
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+Req_Id+"','Cancel OT','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+ads.getString("HR_NO")+"','No','"+user.getEmployeeNo()+"','"+leaveForm.getComments()+"')";
			 i=ad.SqlExecuteUpdate(saveRecReq);
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 if(i > 0){
				leaveForm.setMessage("Data Submitted Successfully");
				//update flag
				 String update="update over_time_details set cancelflag=1 where Req_no='"+Req_Id+"'";
				int j =ad.SqlExecuteUpdate(update);
			}
		
		 else
			{
				leaveForm.setMessage("Error Please Check");
			}
		 
		 selectOTCancelRequest(mapping, leaveForm, request, response);
		return mapping.findForward("displayOTCancelRequest");
	}
	
	public ActionForward submitCompoffcancelRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String Req_Id=request.getParameter("requstNo");
		
		  Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			 String submitDate = ft.format(dNow);
		
		int i=0;
		//GET HR DATA WHO CANCEL
		
		String rep=user.getRepgrp();
		String loc="";
		
		if(rep.equalsIgnoreCase("12"))
		{
			loc="34";
		}
		else
		{
			loc=user.getPlantId();
		}
		
		
		
		String as="select * from ess_cancel_appr where Location=(select LOCATION_CODE from Location where LOCID="+loc+")";
		ResultSet ads=ad.selectQuery(as);
		try {
			while(ads.next())
			{		
			
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+Req_Id+"','Cancel Comp-Off','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+ads.getString("HR_NO")+"','No','"+user.getEmployeeNo()+"','"+leaveForm.getComments()+"')";
			 i=ad.SqlExecuteUpdate(saveRecReq);
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 if(i > 0){
				leaveForm.setMessage("Data Submitted Successfully");
				//update flag
				 String update="update COMP_OT set cancelflag=1 where Req_no='"+Req_Id+"'";
				int j =ad.SqlExecuteUpdate(update);
			}
		
		 else
			{
				leaveForm.setMessage("Error Please Check");
			}
		 
		 selectCompoffCancelRequest(mapping, leaveForm, request, response);
		return mapping.findForward("displayCompoffCancelRequest");
	}
	
	public ActionForward submitcancelRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String Req_Id=request.getParameter("requstNo");
		
		  Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			 String submitDate = ft.format(dNow);
		
		int i=0;
		//GET HR DATA WHO CANCEL
		

		String rep=user.getRepgrp();
		String loc="";
		if(rep==null)
		{
			rep="";
		}
		
		if(rep.equalsIgnoreCase("12"))
		{
			loc="34";
		}
		else
		{
			loc=user.getPlantId();
		}
		
		
		
		String as="select * from ess_cancel_appr where Location=(select LOCATION_CODE from Location where LOCID="+loc+")";
		ResultSet ads=ad.selectQuery(as);
		try {
			while(ads.next())
			{		
			
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+Req_Id+"','Cancel Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+ads.getString("HR_NO")+"','No','"+user.getEmployeeNo()+"','"+leaveForm.getComments()+"')";
			 i=ad.SqlExecuteUpdate(saveRecReq);
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 if(i > 0){
				leaveForm.setMessage("Data Submitted Successfully");
				 //update flag
				 String update="update leave_details set cancelflag=1 where Req_Id='"+Req_Id+"'";
				int j =ad.SqlExecuteUpdate(update);
			}
		 else
			{
				leaveForm.setMessage("Error Please Check");
			}
		
		 selectCancelRequest(mapping, leaveForm, request, response);
		return mapping.findForward("displayCancelRequest");
	}
	
	public ActionForward displayOTCancelList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		LeaveForm masterForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		LinkedList ot = new LinkedList();
		String a = "select c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.Last_approver,c.Pending_approver  from OVER_TIME_DETAILS c where RequestedBy='"
				+ user.getEmployeeNo()				+ "' and c.ApprvrStatus='Approved' and cancelflag=0 and "
				+ " DATEDIFF(day,c.ToDate,GETDATE())<60  group by c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.Last_approver,c.Pending_approver order by c.Req_no desc ";
		ResultSet rsit = ad.selectQuery(a);
		try {
			while (rsit.next()) {
				LeaveForm leave = new LeaveForm();
				leave.setRequestNumber(rsit.getInt("Req_no"));
				leave.setSubmitDate(EMicroUtils.display(rsit
						.getDate("RequestedDate")));
				leave.setStatus(rsit.getString("ApprvrStatus"));
				leave.setStartDate(EMicroUtils.display(rsit.getDate("FromDate")));
				leave.setEndDate(EMicroUtils.display(rsit.getDate("ToDate")));
				int lengt = rsit.getString("reason").length();
				if (lengt > 30)
					leave.setReason(rsit.getString("reason").substring(0, 30));
				else
					leave.setReason(rsit.getString("reason"));
                leave.setPapprover(rsit.getString("Pending_approver"));
				
				leave.setLapprover(rsit.getString("Last_approver"));
				ot.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ot.size() == 0) {
			request.setAttribute("noOT", "noOT");
		} else {
			request.setAttribute("OT", ot);
		}
		return mapping.findForward("OTCancel");
	}
	public ActionForward displayCompoffCancelList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		LeaveForm masterForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		LinkedList comp = new LinkedList();
		String a = "select c.Req_no,c.FromDate,c.ToDate,c.Reason,c.RequestedDate,c.ApprvrStatus,c.Reason,c.Last_approver,c.Pending_approver  from COMP_OT c where RequestedBy='"
				+ user.getEmployeeNo()
				+ "'  and  c.ApprvrStatus!='Draft' and c.ApprvrStatus='Approved' and cancelflag=0 and "
				+ " DATEDIFF(day,c.ToDate,GETDATE())<60 group by c.Req_no,c.FromDate,c.ToDate,c.Reason,"
				+ "c.RequestedDate,c.ApprvrStatus,c.Reason,c.Last_approver,c.Pending_approver order by c.Req_no desc";
		ResultSet rsit = ad.selectQuery(a);
		try {
			while (rsit.next()) {
				LeaveForm leave = new LeaveForm();
				leave.setRequestNumber(rsit.getInt("Req_no"));
				leave.setSubmitDate(EMicroUtils.display(rsit
						.getDate("RequestedDate")));
				leave.setStatus(rsit.getString("ApprvrStatus"));
				leave.setStartDate(EMicroUtils.display(rsit.getDate("FromDate")));
				leave.setEndDate(EMicroUtils.display(rsit.getDate("ToDate")));
				int lengt = rsit.getString("reason").length();
				if (lengt > 30)
					leave.setReason(rsit.getString("reason").substring(0, 30));
				else
					leave.setReason(rsit.getString("reason"));
				
				leave.setPapprover(rsit.getString("Pending_approver"));
				
				leave.setLapprover(rsit.getString("Last_approver"));
		

				comp.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (comp.size() == 0) {
			request.setAttribute("nocomp", "nocomp");
		} else {
			request.setAttribute("comp", comp);
		}
		return mapping.findForward("compoffCancel");
	}
	
	
	public ActionForward displayCancelList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		
		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		String leaveType="";
	
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		LinkedList a1 =new LinkedList();
		 String getLeaveRecords="select l.leave_type,l.start_duration,l.Approvel_Status,l.start_date,l.start_duration,l.end_date,l.end_duration,l.no_of_days,l.Req_Id,l.submit_date,l.record_status,l.reason,l.documents " +
					" from leave_details as l  where    l.firstname='"+user.getEmployeeNo()+"' and l.record_status!='Draft'  and Approvel_Status='Approved' and cancelflag=0 and DATEDIFF(day,end_date,GETDATE())<60 order by id  desc";
		
			
			ResultSet rsLeaveRecords=ad.selectQuery(getLeaveRecords);
			try {
				while(rsLeaveRecords.next())
				{
					LeaveForm leave=new LeaveForm();
					leave.setId(rsLeaveRecords.getString("Req_Id"));
					leave.setSubmitDate(rsLeaveRecords.getString("submit_date"));
					leaveType=rsLeaveRecords.getString("leave_type");
					if(leaveType.equalsIgnoreCase("1")){
						leaveType="Casual";
					}
					if(leaveType.equalsIgnoreCase("2")){
						leaveType="Sick";
					}
					if(leaveType.equalsIgnoreCase("3")){
						leaveType="Privilege";
					}
					if(leaveType.equalsIgnoreCase("4")){
						leaveType="Loss Of Pay";
					}
					if(leaveType.equalsIgnoreCase("5")){
						leaveType="Maternity";
					}
					if(leaveType.equalsIgnoreCase("6")){
						leaveType="Comp-Off";
					}
					leave.setLeaveType(leaveType);
					String stDur=rsLeaveRecords.getString("start_duration");
					if(stDur.equalsIgnoreCase("FD"))
					{
						stDur="Full Day";
					}
					if(stDur.equalsIgnoreCase("FH"))
					{
						stDur="First Half";
					}
					if(stDur.equalsIgnoreCase("SH"))
					{
						stDur="Second Half";
					}
					leave.setStartDurationType(stDur);
					leave.setStartDate(EMicroUtils.display(rsLeaveRecords.getDate("start_date")));
					String enDur=rsLeaveRecords.getString("end_duration");
					if(enDur.equalsIgnoreCase("FD"))
					{
						enDur="Full Day";
					}
					if(enDur.equalsIgnoreCase("FH"))
					{
						enDur="First Half";
					}
					if(enDur.equalsIgnoreCase("SH"))
					{
						enDur="Second Half";
					}
					leave.setEndDurationType(enDur);
					leave.setEndDate(EMicroUtils.display(rsLeaveRecords.getDate("end_date")));
					leave.setNoOfDays(rsLeaveRecords.getString("no_of_days"));
					leave.setStatus(rsLeaveRecords.getString("Approvel_Status"));
					/* Leave l = new Leave(EMicroUtils.display(rsLeaveRecords.getDate("start_date")),EMicroUtils.display(rsLeaveRecords.getDate("end_date")),
							 rsLeaveRecords.getString("no_of_days"),leaveType,rsLeaveRecords.getString("fullname"),
								status,rsLeaveRecords.getString("id"),rsLeaveRecords.getString("submit_date"),rsLeaveRecords.getString("record_status"),holidayType,rsLeaveRecords.getString("reason"),rsLeaveRecords.getString("documents"),rsLeaveRecords.getString(""),rsLeaveRecords.getString(""));
*/		 
					
					 a1.add(leave);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(a1.size()==0)
			{
				request.setAttribute("norecords", "norecords");
			}
			else				
			request.setAttribute("leavecancel", a1);
			
		return mapping.findForward("leaveCancel");
	}
	public ActionForward selfcancelOTRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			leaveForm.setMessage2("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		
		String requestno=request.getParameter("reqno");
		
		String modifyRequest="update All_Request set Req_Status='Self Cancelled'  where Req_Type='OverTime' and Req_Id='"+requestno+"'";	
		int statsChange=ad.SqlExecuteUpdate(modifyRequest);	
		if(statsChange>0){
			leaveForm.setMessage("Request has been cancelled successfully");
		 
		String updateSql= "update over_time_details set ApprvrStatus='Self Cancelled'  where req_no='"+requestno+"'";
		int ii=ad.SqlExecuteUpdate(updateSql);
		}
		
		displayOTreq(mapping, leaveForm, request, response);
		return mapping.findForward("OTreq");
	} 
	public ActionForward selfcancelCompRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			leaveForm.setMessage2("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		
		String requestno=request.getParameter("reqno");
		
		String modifyRequest="update All_Request set Req_Status='Self Cancelled'  where Req_Type='Comp-Off/OT' and Req_Id='"+requestno+"'";	
		int statsChange=ad.SqlExecuteUpdate(modifyRequest);	
		if(statsChange>0){
			leaveForm.setMessage("Request has been cancelled successfully");
		 
		String updateSql= "update comp_ot set ApprvrStatus='Self Cancelled'  where req_no='"+requestno+"'";
		int ii=ad.SqlExecuteUpdate(updateSql);
		}
		
		displaycompreq(mapping, leaveForm, request, response);
		return mapping.findForward("compreq");
	}
	
	public ActionForward displayOthersLeaveReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();

		UserInfo user = (UserInfo) session.getAttribute("user");
		String requestno = request.getParameter("reqno");
		String empno = leaveForm.getEmpno();
		if(empno==null)
		{
			empno=user.getEmployeeNo();
		}
		int year = Integer.parseInt(leaveForm.getEmpyear());
		
	
		
		ArrayList casleave = new ArrayList();
		ArrayList sickleave = new ArrayList();
		ArrayList prileave = new ArrayList();
		ArrayList LOPleave = new ArrayList();
		//int year = Calendar.getInstance().get(Calendar.YEAR);

		float casleavbal = 0;
        
		if(empno.equalsIgnoreCase(""))
		{
			empno=user.getEmployeeNo();
		}
		
		/*leaveForm.setEmployeeName(user.getFullName());*/
		leaveForm.setEmployeeNumber(empno);
		leaveForm.setYear(year);
		String emp = "Select dep.DPTSTXT,desg.DSGSTXT,emp.DOJ ,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg where PERNR='"
				+ empno
				+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID ";

		ResultSet rs1 = ad.selectQuery(emp);

		try {
			while (rs1.next())

			{
				leaveForm.setDepartment(rs1.getString("DPTSTXT"));
				leaveForm.setDesignation(rs1.getString("DSGSTXT"));
				leaveForm.setDoj(EMicroUtils.display1(rs1.getDate("DOJ")));
				leaveForm.setEmployeeName(rs1.getString("EMP_FULLNAME"));
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		ArrayList empIdList=new ArrayList();
		ArrayList empLabelList=new ArrayList();
		ArrayList yearList=new ArrayList();
		String isapprover = "select employeeNumber , employeeNumber+'-'+emp_official_info.EMP_FULLNAME as emplabel from ESS_Approvers , emp_official_info"
							 +" where ESS_Approvers.employeeNumber = emp_official_info.PERNR and (ESS_Approvers.ApproverId = '"+user.getEmployeeNo()+"' or Parallel_Approver1 = '"+user.getEmployeeNo()+"' or Parallel_Approver2 = '"+user.getEmployeeNo()+"') and Active='1'" ;
		ResultSet rs2 = ad.selectQuery(isapprover);
		
		try {
			while (rs2.next())

			{
				empIdList.add(rs2.getString("employeeNumber"));
				empLabelList.add(rs2.getString("emplabel"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		leaveForm.setEmplIdList(empIdList);
		leaveForm.setEmpLabelList(empLabelList);
		if (empIdList.size() > 0) {
			request.setAttribute("Approver", "Approver");
		}
		
		String year1 = "select distinct(lv_calyear) as Year from lv_type_d order by lv_calyear desc " ;
		ResultSet rs3 = ad.selectQuery(year1);
		
		try {
		while (rs3.next())
		
		{
	    yearList.add(rs3.getString("Year"));
		
		}
		} catch (SQLException e1) {
		
		e1.printStackTrace();
		}
		
		leaveForm.setYearList(yearList);
		
		////////
		
		try {
			
			leaveForm.setYear(year);
			String getleaveBalence = "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
					+  empno
					+ "' and t.lv_calyear='"
					+ year
					+ "' and t.lv_typeid=m.lv_typeid";
			ResultSet rsBalance = ad.selectQuery(getleaveBalence);
			ArrayList balList = new ArrayList();
			while (rsBalance.next()) {
				leaveForm.setYear(rsBalance.getInt("lv_calyear"));
				LeaveForm form2 = new LeaveForm();
				form2.setLeaveType(rsBalance.getString("lv_type"));
				form2.setOpeningBalence(rsBalance.getFloat("lv_opbal"));
				form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
				form2.setNoOfDays(rsBalance.getString("lv_availed"));
				form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
				form2.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
				balList.add(form2);
			}
			request.setAttribute("LeaveBalenceList", balList);

			if (balList.size() == 0) {
				request.setAttribute("No LeaveBalence", "No LeaveBalence");
				leaveForm.setMessage2("You dont have Leave balence");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		//////

		/*
		 * //leave balance try{ String getleaveBalence=
		 * "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
		 * +user.getEmployeeNo()+"' and t.lv_calyear='"+year+
		 * "' and t.lv_typeid=m.lv_typeid"; ResultSet
		 * rsBalance=ad.selectQuery(getleaveBalence); ArrayList balList=new
		 * ArrayList(); while(rsBalance.next()) {
		 * leaveForm.setYear(rsBalance.getInt("lv_calyear"));
		 * leaveForm=(LeaveForm)form;
		 * leaveForm.setLeaveType(rsBalance.getString("lv_type"));
		 * leaveForm.setOpeningBalence(rsBalance.getInt("lv_opbal"));
		 * leaveForm.setAvalableBalence(rsBalance.getFloat("lv_availed"));
		 * leaveForm.setNoOfDays(rsBalance.getString("lv_availed"));
		 * leaveForm.setClosingBalence(rsBalance.getFloat("lv_clbal"));
		 * 
		 * leaveForm.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
		 * balList.add(leaveForm); } request.setAttribute("LeaveBalenceList",
		 * balList);
		 * 
		 * if(balList.size()==0){ request.setAttribute("No LeaveBalence",
		 * "No LeaveBalence");
		 * leaveForm.setMessage2("You don't have Leave balence"); }
		 * 
		 * }catch (Exception e) { e.printStackTrace(); }
		 */

		// casual
		String getcasleavclobal = "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
				+ empno
				+ "' and t.lv_calyear='"
				+ year
				+ "' and t.lv_typeid=m.lv_typeid and lv_type='CASUAL'";
		ResultSet rsBalance = ad.selectQuery(getcasleavclobal);
		ArrayList balList1 = new ArrayList();
		try {
			while (rsBalance.next()) {
				leaveForm.setCaslopenBalence(rsBalance.getInt("lv_opbal"));
				leaveForm.setCaslclosingBalence(rsBalance.getFloat("lv_clbal"));
				balList1.add(leaveForm);
				request.setAttribute("casclosbal", balList1);
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		// sick
		String getsickleavclobal = "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
				+ empno
				+ "' and t.lv_calyear='"
				+ year
				+ "' and t.lv_typeid=m.lv_typeid and lv_type='SICK'";
		ResultSet rsBalance1 = ad.selectQuery(getsickleavclobal);
		ArrayList balList2 = new ArrayList();
		try {
			while (rsBalance1.next()) {
				leaveForm.setSickopenBalence(rsBalance1.getInt("lv_opbal"));
				leaveForm
						.setSickclosingBalence(rsBalance1.getFloat("lv_clbal"));
				balList2.add(leaveForm);
				request.setAttribute("sickclosbal", balList2);

			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		// privilege
		String getprileavclobal = "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
				+ empno
				+ "' and t.lv_calyear='"
				+ year
				+ "' and t.lv_typeid=m.lv_typeid and lv_type='PRIVILEGE'";
		ResultSet rsBalance2 = ad.selectQuery(getprileavclobal);
		ArrayList balList3 = new ArrayList();
		try {
			while (rsBalance2.next()) {
				leaveForm.setPrivopenBalence(rsBalance2.getInt("lv_opbal"));
				leaveForm
						.setPrivclosingBalence(rsBalance2.getFloat("lv_clbal"));
				balList3.add(leaveForm);
				request.setAttribute("Privclosbal", balList3);

			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		String startdate = "" + year + "-01-01";
		String enddate = "" + year + "-12-31";
		LinkedList report = new LinkedList();
		String data = "select m.lv_type,convert(nvarchar(11),start_date,103) as fromdate,convert(nvarchar(11),end_date,103) as enddate,no_of_days,submit_date,convert(nvarchar(11),approved_date,103) as approveddate,reason from leave_details,lv_type_m as m where user_id='"
				+ empno
				+ "' and m.lv_typeid=leave_type and Approvel_Status='Approved'  and start_date between '"
				+ startdate + "' and '" + enddate + "' order by start_date desc ";

		ResultSet rs = ad.selectQuery(data);
		try {
			while (rs.next()) {
				LeaveForm leaveForm0 = new LeaveForm();
				leaveForm0.setLeaveType(rs.getString("lv_type"));
				leaveForm0.setSubmitDate(rs.getString("submit_date"));
				leaveForm0.setApprovedDate(rs.getString("approveddate"));
				leaveForm0.setStartDate(rs.getString("fromdate"));
				leaveForm0.setEndDate(rs.getString("enddate"));
				leaveForm0.setNoOfDays(rs.getString("no_of_days"));
				leaveForm0.setReason(rs.getString("reason"));

				report.add(leaveForm0);
				/*
				 * 
				 * 
				 * String type=rs.getString("leave_type");
				 * if(type.equalsIgnoreCase("1")) { LeaveForm leaveForm0=new
				 * LeaveForm();
				 * leaveForm0.setSubmitDate(rs.getString("submit_date"));
				 * leaveForm0
				 * .setStartDate(EMicroUtils.display1(rs.getDate("start_date"
				 * )));
				 * leaveForm0.setEndDate(EMicroUtils.display1(rs.getDate("end_date"
				 * ))); leaveForm0.setNoOfDays(rs.getString("no_of_days"));
				 * leaveForm0.setReasonType(rs.getString("reasonType"));
				 * leaveForm0.setReason(rs.getString("reason"));
				 * 
				 * casleave.add(leaveForm0); } if(type.equalsIgnoreCase("2")) {
				 * LeaveForm leaveForm1=new LeaveForm();
				 * leaveForm1.setSubmitDate(rs.getString("submit_date"));
				 * leaveForm1
				 * .setStartDate(EMicroUtils.display1(rs.getDate("start_date"
				 * )));
				 * leaveForm1.setEndDate(EMicroUtils.display1(rs.getDate("end_date"
				 * ))); leaveForm1.setNoOfDays(rs.getString("no_of_days"));
				 * leaveForm1.setReasonType(rs.getString("reasonType"));
				 * leaveForm1.setReason(rs.getString("reason"));
				 * 
				 * sickleave.add(leaveForm1);
				 * 
				 * } if(type.equalsIgnoreCase("3")) { LeaveForm leaveForm2=new
				 * LeaveForm();
				 * leaveForm2.setSubmitDate(rs.getString("submit_date"));
				 * leaveForm2
				 * .setStartDate(EMicroUtils.display1(rs.getDate("start_date"
				 * )));
				 * leaveForm2.setEndDate(EMicroUtils.display1(rs.getDate("end_date"
				 * ))); leaveForm2.setNoOfDays(rs.getString("no_of_days"));
				 * leaveForm2.setReasonType(rs.getString("reasonType"));
				 * leaveForm2.setReason(rs.getString("reason"));
				 * 
				 * prileave.add(leaveForm2);
				 * 
				 * } if(type.equalsIgnoreCase("4")) { LeaveForm leaveForm3=new
				 * LeaveForm();
				 * leaveForm3.setSubmitDate(rs.getString("submit_date"));
				 * leaveForm3
				 * .setStartDate(EMicroUtils.display1(rs.getDate("start_date"
				 * )));
				 * leaveForm3.setEndDate(EMicroUtils.display1(rs.getDate("end_date"
				 * ))); leaveForm3.setNoOfDays(rs.getString("no_of_days"));
				 * leaveForm3.setReasonType(rs.getString("reasonType"));
				 * leaveForm3.setReason(rs.getString("reason"));
				 * 
				 * LOPleave.add(leaveForm3);
				 * 
				 * }
				 */}
			request.setAttribute("list", report);

			if (report.size() == 0) {
				request.setAttribute("norecords", "norecords");
			}
			/*
			 * if(casleave.size()>0) { request.setAttribute("casleave",
			 * casleave); }
			 * 
			 * if(sickleave.size()>0) { request.setAttribute("sickleave",
			 * sickleave); } if(prileave.size()>0) {
			 * request.setAttribute("prileaveDetails", prileave); }
			 * if(LOPleave.size()>0) { request.setAttribute("LOPleave",
			 * LOPleave); }
			 * 
			 * if(casleave.size()==0) { request.setAttribute("No casleave",
			 * "No casleave"); }
			 * 
			 * if(sickleave.size()==0) { request.setAttribute("No sickleave",
			 * "No sickleave"); } if(prileave.size()==0) {
			 * request.setAttribute("No prileave", "No prileave"); }
			 * if(LOPleave.size()==0) { request.setAttribute("No LOPleave",
			 * "No LOPleave"); }
			 */

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return mapping.findForward("leavereport");
	}
	
	
	public ActionForward displaySummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
	
		
		ArrayList yearList=new ArrayList();
		String year1 = "select distinct(lv_calyear) as Year from lv_type_d order by lv_calyear desc " ;
		ResultSet rs3 = ad.selectQuery(year1);
		
		try {
		while (rs3.next())
		
		{
	    yearList.add(rs3.getString("Year"));
		
		}
		} catch (SQLException e1) {
		
		e1.printStackTrace();
		}
		
		leaveForm.setYearList(yearList);
		
		if(leaveForm.getEmpyear()!=null)
		{
		
		String emplist="";
		
		String isapprover = "select employeeNumber , employeeNumber+'-'+emp_official_info.EMP_FULLNAME as emplabel from ESS_Approvers , emp_official_info"
							 +" where ESS_Approvers.employeeNumber = emp_official_info.PERNR and (ESS_Approvers.ApproverId = '"+user.getEmployeeNo()+"' or Parallel_Approver1 = '"+user.getEmployeeNo()+"' or Parallel_Approver2 = '"+user.getEmployeeNo()+"') and Active='1' "  ;
		ResultSet rs2 = ad.selectQuery(isapprover);
		
		try {
			while (rs2.next())

			{
				emplist=emplist+rs2.getString("employeeNumber")+",";
		
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		if(!emplist.equalsIgnoreCase(""))
		{
		emplist=emplist.substring(0, emplist.length()-1);
		}
		
		ArrayList addi=new ArrayList();
		String add="select sum(cl_open) as cl_open,sum(cl_avail) as cl_avail,sum(cl_close) as cl_close,sum(sl_open) as sl_open,sum(sl_avail) as sl_avail,"
				+ "sum(sl_close) as sl_close,sum(el_open) as el_open,sum(el_avail) as el_avail,sum(el_close) as el_close,t.PERNR,t.DPTID,t.EMP_FULLNAME,t.DPTSTXT,t.DSGSTXT "
				+ " from(select case when lv_typeid=1 then lv_opbal else 0 end as cl_open,case when lv_typeid=1 then lv_availed else 0 end as cl_avail,case when lv_typeid=1 then"
				+ " lv_clbal else 0 end as cl_close,case when lv_typeid=2 then lv_opbal else 0 end as sl_open,case when lv_typeid=2 then lv_availed else 0 end as sl_avail,case "
				+ "when lv_typeid=2 then lv_clbal else 0 end as sl_close,case when lv_typeid=3 then lv_opbal else 0 end as el_open,case when lv_typeid=3 then lv_availed else 0 "
				+ "end as el_avail,case when lv_typeid=3 then lv_clbal else 0 end as el_close,pernr,emp_official_info.EMP_FULLNAME,emp_official_info.DPTID,DEPARTMENT.DPTSTXT,"
				+ "DESIGNATION.DSGSTXT from lv_type_d,emp_official_info,DEPARTMENT,DESIGNATION  where lv_calyear='"+leaveForm.getEmpyear()+"' and emp_official_info.PERNR=lv_type_d.lv_empcode "
				+ "  and emp_official_info.DPTID=DEPARTMENT.DPTID and DESIGNATION.DSGID=emp_official_info.DSGID and emp_official_info.ACTIVE=1)t,"
				+ "emp_official_info where emp_official_info.PERNR=t.PERNR and emp_official_info.PERNR in ("+emplist+")  and emp_official_info.ACTIVE=1  ";
					
						
					
						
						add=add+"group by t.PERNR,t.DPTID,t.EMP_FULLNAME,t.DPTSTXT,t.DSGSTXT";
						
						
						ResultSet a=ad.selectQuery(add);
						try {
							while(a.next())
							{
								HRApprovalForm hr=new HRApprovalForm();
								hr.setDepartment(a.getString("DPTSTXT"));
								hr.setEmployeeno(a.getString("PERNR"));
								hr.setEmployeeName(a.getString("EMP_FULLNAME"));
								hr.setDesignation(a.getString("DSGSTXT"));
								hr.setCl_openingBalence(a.getFloat("cl_open"));
							    hr.setCl_avalableBalence(a.getFloat("cl_avail"));
							    hr.setCl_closingBalence(a.getFloat("cl_close"));
							    hr.setSl_openingBalence(a.getFloat("sl_open"));
							    hr.setSl_avalableBalence(a.getFloat("sl_avail"));
							    hr.setSl_closingBalence(a.getFloat("sl_close"));
							    hr.setEl_openingBalence(a.getFloat("el_open"));
							    hr.setEl_avalableBalence(a.getFloat("el_avail"));
							    hr.setEl_closingBalence(a.getFloat("el_close"));		    
							    addi.add(hr);
								
							}
							
							
							request.setAttribute("addi", addi);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							a.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
		
		return mapping.findForward("leavereportsummary");
	}
	
	
	public ActionForward displayLeaveReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LeaveForm leaveForm = (LeaveForm) form;
		HttpSession session = request.getSession();

		UserInfo user = (UserInfo) session.getAttribute("user");
		String requestno = request.getParameter("reqno");
	
		
		ArrayList casleave = new ArrayList();
		ArrayList sickleave = new ArrayList();
		ArrayList prileave = new ArrayList();
		ArrayList LOPleave = new ArrayList();
		int year = Calendar.getInstance().get(Calendar.YEAR);

		float casleavbal = 0;
        
		leaveForm.setEmployeeName(user.getFullName());
		leaveForm.setEmployeeNumber(user.getEmployeeNo());
		leaveForm.setYear(year);
		String emp = "Select dep.DPTSTXT,desg.DSGSTXT,emp.DOJ from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg where PERNR='"
				+ user.getEmployeeNo()
				+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID ";

		ResultSet rs1 = ad.selectQuery(emp);

		try {
			while (rs1.next())

			{
				leaveForm.setDepartment(rs1.getString("DPTSTXT"));
				leaveForm.setDesignation(rs1.getString("DSGSTXT"));
				leaveForm.setDoj(EMicroUtils.display1(rs1.getDate("DOJ")));
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		ArrayList empIdList=new ArrayList();
		ArrayList empLabelList=new ArrayList();
		ArrayList yearList=new ArrayList();
		String isapprover = "select employeeNumber , employeeNumber+'-'+emp_official_info.EMP_FULLNAME as emplabel from ESS_Approvers , emp_official_info"
							 +" where ESS_Approvers.employeeNumber = emp_official_info.PERNR and (ESS_Approvers.ApproverId = '"+user.getEmployeeNo()+"' or Parallel_Approver1 = '"+user.getEmployeeNo()+"' or Parallel_Approver2 = '"+user.getEmployeeNo()+"') and Active='1' "  ;
		ResultSet rs2 = ad.selectQuery(isapprover);
		
		try {
			while (rs2.next())

			{
				empIdList.add(rs2.getString("employeeNumber"));
				empLabelList.add(rs2.getString("emplabel"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		leaveForm.setEmplIdList(empIdList);
		leaveForm.setEmpLabelList(empLabelList);
		if (empIdList.size() > 0) {
			request.setAttribute("Approver", "Approver");
		}
		
//-----------------

		String year1 = "select distinct(lv_calyear) as Year from lv_type_d order by lv_calyear desc " ;
		ResultSet rs3 = ad.selectQuery(year1);
		
		try {
		while (rs3.next())
		
		{
	    yearList.add(rs3.getString("Year"));
		
		}
		} catch (SQLException e1) {
		
		e1.printStackTrace();
		}
		
		leaveForm.setYearList(yearList);
		
		try {
			
			leaveForm.setYear(year);
			String getleaveBalence = "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
					+ user.getEmployeeNo()
					+ "' and t.lv_calyear='"
					+ year
					+ "' and t.lv_typeid=m.lv_typeid";
			ResultSet rsBalance = ad.selectQuery(getleaveBalence);
			ArrayList balList = new ArrayList();
			while (rsBalance.next()) {
				leaveForm.setYear(rsBalance.getInt("lv_calyear"));
				LeaveForm form2 = new LeaveForm();
				form2.setLeaveType(rsBalance.getString("lv_type"));
				form2.setOpeningBalence(rsBalance.getFloat("lv_opbal"));
				form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
				form2.setNoOfDays(rsBalance.getString("lv_availed"));
				form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
				form2.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
				balList.add(form2);
			}
			request.setAttribute("LeaveBalenceList", balList);

			if (balList.size() == 0) {
				request.setAttribute("No LeaveBalence", "No LeaveBalence");
				leaveForm.setMessage2("You dont have Leave balence");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * //leave balance try{ String getleaveBalence=
		 * "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
		 * +user.getEmployeeNo()+"' and t.lv_calyear='"+year+
		 * "' and t.lv_typeid=m.lv_typeid"; ResultSet
		 * rsBalance=ad.selectQuery(getleaveBalence); ArrayList balList=new
		 * ArrayList(); while(rsBalance.next()) {
		 * leaveForm.setYear(rsBalance.getInt("lv_calyear"));
		 * leaveForm=(LeaveForm)form;
		 * leaveForm.setLeaveType(rsBalance.getString("lv_type"));
		 * leaveForm.setOpeningBalence(rsBalance.getInt("lv_opbal"));
		 * leaveForm.setAvalableBalence(rsBalance.getFloat("lv_availed"));
		 * leaveForm.setNoOfDays(rsBalance.getString("lv_availed"));
		 * leaveForm.setClosingBalence(rsBalance.getFloat("lv_clbal"));
		 * 
		 * leaveForm.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
		 * balList.add(leaveForm); } request.setAttribute("LeaveBalenceList",
		 * balList);
		 * 
		 * if(balList.size()==0){ request.setAttribute("No LeaveBalence",
		 * "No LeaveBalence");
		 * leaveForm.setMessage2("You don't have Leave balence"); }
		 * 
		 * }catch (Exception e) { e.printStackTrace(); }
		 */

		// casual
		String getcasleavclobal = "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
				+ user.getEmployeeNo()
				+ "' and t.lv_calyear='"
				+ year
				+ "' and t.lv_typeid=m.lv_typeid and lv_type='CASUAL'";
		ResultSet rsBalance = ad.selectQuery(getcasleavclobal);
		ArrayList balList1 = new ArrayList();
		try {
			while (rsBalance.next()) {
				leaveForm.setCaslopenBalence(rsBalance.getInt("lv_opbal"));
				leaveForm.setCaslclosingBalence(rsBalance.getFloat("lv_clbal"));
				balList1.add(leaveForm);
				request.setAttribute("casclosbal", balList1);
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		// sick
		String getsickleavclobal = "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
				+ user.getEmployeeNo()
				+ "' and t.lv_calyear='"
				+ year
				+ "' and t.lv_typeid=m.lv_typeid and lv_type='SICK'";
		ResultSet rsBalance1 = ad.selectQuery(getsickleavclobal);
		ArrayList balList2 = new ArrayList();
		try {
			while (rsBalance1.next()) {
				leaveForm.setSickopenBalence(rsBalance1.getInt("lv_opbal"));
				leaveForm
						.setSickclosingBalence(rsBalance1.getFloat("lv_clbal"));
				balList2.add(leaveForm);
				request.setAttribute("sickclosbal", balList2);

			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		// privilege
		String getprileavclobal = "select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"
				+ user.getEmployeeNo()
				+ "' and t.lv_calyear='"
				+ year
				+ "' and t.lv_typeid=m.lv_typeid and lv_type='PRIVILEGE'";
		ResultSet rsBalance2 = ad.selectQuery(getprileavclobal);
		ArrayList balList3 = new ArrayList();
		try {
			while (rsBalance2.next()) {
				leaveForm.setPrivopenBalence(rsBalance2.getInt("lv_opbal"));
				leaveForm
						.setPrivclosingBalence(rsBalance2.getFloat("lv_clbal"));
				balList3.add(leaveForm);
				request.setAttribute("Privclosbal", balList3);

			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		String startdate = "" + year + "-01-01";
		String enddate = "" + year + "-12-31";
		LinkedList report = new LinkedList();
		String data = "select m.lv_type,convert(nvarchar(11),start_date,103) as fromdate,convert(nvarchar(11),end_date,103) as enddate,no_of_days,submit_date,convert(nvarchar(11),approved_date,103) as approveddate,reason from leave_details,lv_type_m as m where user_id='"
				+ user.getEmployeeNo()
				+ "' and m.lv_typeid=leave_type and Approvel_Status='Approved'  and start_date between '"
				+ startdate + "' and '" + enddate + "' order by start_date desc";

		ResultSet rs = ad.selectQuery(data);
		try {
			while (rs.next()) {
				LeaveForm leaveForm0 = new LeaveForm();
				leaveForm0.setLeaveType(rs.getString("lv_type"));
				leaveForm0.setSubmitDate(rs.getString("submit_date"));
				leaveForm0.setApprovedDate(rs.getString("approveddate"));
				leaveForm0.setStartDate(rs.getString("fromdate"));
				leaveForm0.setEndDate(rs.getString("enddate"));
				leaveForm0.setNoOfDays(rs.getString("no_of_days"));
				leaveForm0.setReason(rs.getString("reason"));

				report.add(leaveForm0);
				/*
				 * 
				 * 
				 * String type=rs.getString("leave_type");
				 * if(type.equalsIgnoreCase("1")) { LeaveForm leaveForm0=new
				 * LeaveForm();
				 * leaveForm0.setSubmitDate(rs.getString("submit_date"));
				 * leaveForm0
				 * .setStartDate(EMicroUtils.display1(rs.getDate("start_date"
				 * )));
				 * leaveForm0.setEndDate(EMicroUtils.display1(rs.getDate("end_date"
				 * ))); leaveForm0.setNoOfDays(rs.getString("no_of_days"));
				 * leaveForm0.setReasonType(rs.getString("reasonType"));
				 * leaveForm0.setReason(rs.getString("reason"));
				 * 
				 * casleave.add(leaveForm0); } if(type.equalsIgnoreCase("2")) {
				 * LeaveForm leaveForm1=new LeaveForm();
				 * leaveForm1.setSubmitDate(rs.getString("submit_date"));
				 * leaveForm1
				 * .setStartDate(EMicroUtils.display1(rs.getDate("start_date"
				 * )));
				 * leaveForm1.setEndDate(EMicroUtils.display1(rs.getDate("end_date"
				 * ))); leaveForm1.setNoOfDays(rs.getString("no_of_days"));
				 * leaveForm1.setReasonType(rs.getString("reasonType"));
				 * leaveForm1.setReason(rs.getString("reason"));
				 * 
				 * sickleave.add(leaveForm1);
				 * 
				 * } if(type.equalsIgnoreCase("3")) { LeaveForm leaveForm2=new
				 * LeaveForm();
				 * leaveForm2.setSubmitDate(rs.getString("submit_date"));
				 * leaveForm2
				 * .setStartDate(EMicroUtils.display1(rs.getDate("start_date"
				 * )));
				 * leaveForm2.setEndDate(EMicroUtils.display1(rs.getDate("end_date"
				 * ))); leaveForm2.setNoOfDays(rs.getString("no_of_days"));
				 * leaveForm2.setReasonType(rs.getString("reasonType"));
				 * leaveForm2.setReason(rs.getString("reason"));
				 * 
				 * prileave.add(leaveForm2);
				 * 
				 * } if(type.equalsIgnoreCase("4")) { LeaveForm leaveForm3=new
				 * LeaveForm();
				 * leaveForm3.setSubmitDate(rs.getString("submit_date"));
				 * leaveForm3
				 * .setStartDate(EMicroUtils.display1(rs.getDate("start_date"
				 * )));
				 * leaveForm3.setEndDate(EMicroUtils.display1(rs.getDate("end_date"
				 * ))); leaveForm3.setNoOfDays(rs.getString("no_of_days"));
				 * leaveForm3.setReasonType(rs.getString("reasonType"));
				 * leaveForm3.setReason(rs.getString("reason"));
				 * 
				 * LOPleave.add(leaveForm3);
				 * 
				 * }
				 */}
			request.setAttribute("list", report);

			if (report.size() == 0) {
				request.setAttribute("norecords", "norecords");
			}
			/*
			 * if(casleave.size()>0) { request.setAttribute("casleave",
			 * casleave); }
			 * 
			 * if(sickleave.size()>0) { request.setAttribute("sickleave",
			 * sickleave); } if(prileave.size()>0) {
			 * request.setAttribute("prileaveDetails", prileave); }
			 * if(LOPleave.size()>0) { request.setAttribute("LOPleave",
			 * LOPleave); }
			 * 
			 * if(casleave.size()==0) { request.setAttribute("No casleave",
			 * "No casleave"); }
			 * 
			 * if(sickleave.size()==0) { request.setAttribute("No sickleave",
			 * "No sickleave"); } if(prileave.size()==0) {
			 * request.setAttribute("No prileave", "No prileave"); }
			 * if(LOPleave.size()==0) { request.setAttribute("No LOPleave",
			 * "No LOPleave"); }
			 */

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return mapping.findForward("leavereport");
		}


	
public	String nextDateOF(String reqDate)
	{
		 int oneDay = 1000 * 60 * 60 * 24;
	     Date date = new Date(reqDate);
	     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
	     String currDate = dateFormat.format(date.getTime());
	     String nextDate = dateFormat.format(date.getTime() + oneDay);
	     System.out.println("Currnent date: " + currDate);
	     System.out.println("Next date: " + nextDate);

	     // Read and parse input, stopping on a blank input line
	     
	    
return nextDate;
	 

	}

public ActionForward leavereason(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) {
	
	LeaveForm leaveForm=(LeaveForm)form;
	HttpSession session=request.getSession();
	
	
	UserInfo user=(UserInfo)session.getAttribute("user");
	
	String type=request.getParameter("LeaveType");
	
	
	List leaveReason=new LinkedList();

	
	String reason="select * from LEAVE_REASON where Leav_type='"+type+"'";
	ResultSet b1=ad.selectQuery(reason);
	try {
		while(b1.next())
		{
			LeaveForm lv=new LeaveForm();
			lv.setReasonID(b1.getString("Reason"));
			lv.setReasonName(b1.getString("Detailed_Reason"));
	     leaveReason.add(lv);
	     
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	request.setAttribute("leaveReasonlist", leaveReason);
	
	

	
	return mapping.findForward("lvreason");
}

public ActionForward cancelRecord(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) 
{
	LeaveForm leaveForm=(LeaveForm)form;
	HttpSession session=request.getSession();
	
	UserInfo user=(UserInfo)session.getAttribute("user");
	String requestno=request.getParameter("reqno");
	
	String modifyRequest="update All_Request set Req_Status='Self Cancelled'  where Req_Type='Leave' and Req_Id='"+requestno+"'";	
	int statsChange=ad.SqlExecuteUpdate(modifyRequest);	
	if(statsChange>0){
	leaveForm.setMessage2("Request has been cancelled successfully");
	 
	String updateSql= "update leave_details set Approvel_Status='Self Cancelled'  where Req_Id='"+requestno+"'";
	int ii=ad.SqlExecuteUpdate(updateSql);
	}
	//change leave Balence
	float noOfDays=0;
	String leaveType="";
	String empID="";
	float lvBal=0;
	float avlBal=0;
	float waitingBal=0;
	float closingBal=0;
	int year=0;
		try{
		String getLeaveDetails="select *,year(start_date) as year from leave_details where Req_Id='"+requestno+"'"; 
		ResultSet rsLeave=ad.selectQuery(getLeaveDetails);
		while(rsLeave.next())
		{
			noOfDays=rsLeave.getFloat("no_of_days");
			leaveType=rsLeave.getString("leave_type");
			empID=rsLeave.getString("firstname");
			year=Integer.parseInt(rsLeave.getString("year"));
			
			
		}
		/*int year = Calendar.getInstance().get(Calendar.YEAR);*/
		EMailer email = new EMailer();
		int j=email.sendMailToApproverForCancel(request, "", requestno, "Leave");
		String leaveBal="select * from lv_type_d where lv_empcode='"+empID+"' and lv_typeid='"+leaveType+"' and lv_calyear='"+year+"'";
		ResultSet rsLeaveBal=ad.selectQuery(leaveBal);
		while(rsLeaveBal.next()){
			
			waitingBal=rsLeaveBal.getFloat("lv_awtBal");
		}
		
		waitingBal=waitingBal-noOfDays;
		
	
	  String updateLeaveDetails="update lv_type_d set lv_awtBal='"+waitingBal+"' where lv_typeid='"+leaveType+"' and lv_empcode='"+empID+"' and lv_calyear='"+year+"'";
	  int checkResult=ad.SqlExecuteUpdate(updateLeaveDetails);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	
	
	
	
	
	
	return displayRequests(mapping, form, request, response) ;

	
}


	
	public ActionForward displayLeaveBal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	try{
		
		int year=0;
		if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
		{
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		else
		{
			 year = Integer.parseInt(request.getParameter("year"));
		}
		
		
		int timeoutInSeconds = request.getSession().getMaxInactiveInterval();   
		
		
		String startDate=(String)request.getParameter("StartDate");  
		if(startDate!=null)
		{
		 String c1[]=startDate.split("/");
		 startDate=c1[2]+"-"+c1[1]+"-"+c1[0];
		}
	       	
    	float comOpBal=0;
    	float comAvaiBal=0;
    	float comCloBal=0;

    	String bal="";
    	///Get balance from comptable
    	if(startDate!=null)
		{
    	 bal="select year(Start_Date) as year,sum(Number_of_Days) as openbal,sum(Comp_Off_Availed_Days) as avbal,sum(Comp_Off_Balance) as clobal from COMP_OT_SAP where Employee_Number='"+user.getEmployeeNo()+"'  and Comp_Off_Balance>0"
    			+ "  and  DATEDIFF(DAY,Start_Date, convert(date,'"+startDate+"')) <=(select comp_off_days  from location where locid='"+user.getPlantId()+"') and  '"+startDate+"'>=Start_Date group by Start_Date";
		}
    	else
    	{
       	 bal="select year(Start_Date) as year,sum(Number_of_Days) as openbal,sum(Comp_Off_Availed_Days) as avbal,sum(Comp_Off_Balance) as clobal from COMP_OT_SAP where Employee_Number='"+user.getEmployeeNo()+"'  and Comp_Off_Balance>0"
     			+ "  and  DATEDIFF(DAY,Start_Date, convert(date,getdate())) <=(select comp_off_days  from location where locid='"+user.getPlantId()+"') and  convert(date,getdate())>=Start_Date group by Start_Date";
 		}
    	  ResultSet ba=ad.selectQuery(bal);
   	 try {
		while (ba.next ()) 
			{
			        comOpBal=comOpBal+ba.getFloat("openbal");
					comAvaiBal=comAvaiBal+ba.getFloat("avbal");
					comCloBal=comCloBal+ba.getFloat("clobal");
				
			}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    	
       	 //Check comp balance availabe
    	

       	 int balpresent=0;
       	 String coun="select count(*) from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_typeid='6' and lv_calyear='"+year+"'";
       	 ResultSet as=ad.selectQuery(coun);
       	 try {
			while (as.next ()) 
			{
				
				balpresent=as.getInt(1);
				
			}
		} catch (SQLException e) {				
			e.printStackTrace();
		}
    	

       	 if(balpresent==0)
       	 {
   	 //insert into lv_type
   	 String insertinto="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_availed,lv_clbal,lv_awtBal) values(6,'"+year+"','"+user.getEmployeeNo()+"','"+comOpBal+"','"+comAvaiBal+"','"+comCloBal+"',0)";
   	 int j=ad.SqlExecuteUpdatePrmaryKeys(insertinto);
       	 }
       	 
       	 else
       	 {
      String update="update lv_type_d set lv_opbal="+comOpBal+",lv_clbal="+comCloBal+" where lv_empcode='"+user.getEmployeeNo()+"' and lv_calyear='"+year+"' and lv_typeid='6'";
      int j=ad.SqlExecuteUpdatePrmaryKeys(update);
       	 }

  
    
		
		leaveForm.setYear(year);
			String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"' and t.lv_calyear='"+year+"' and t.lv_typeid=m.lv_typeid";
			ResultSet rsBalance=ad.selectQuery(getleaveBalence);
			ArrayList balList=new ArrayList();
			while(rsBalance.next())
			{
			   leaveForm.setYear(rsBalance.getInt("lv_calyear"));
				LeaveForm form2=new LeaveForm();
				form2.setLeaveType(rsBalance.getString("lv_type"));
				form2.setOpeningBalence(rsBalance.getFloat("lv_opbal"));
				form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
				form2.setNoOfDays(rsBalance.getString("lv_availed"));
				form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
			form2.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
				balList.add(form2);
			}
			request.setAttribute("LeaveBalenceList", balList);
			
			if(balList.size()==0){
				request.setAttribute("No LeaveBalence", "No LeaveBalence");
				leaveForm.setMessage2("You dont have Leave balence");
			}
			
	}catch (Exception e) {
		e.printStackTrace();
	}
		return mapping.findForward("leaveBal");
	}
	public ActionForward checkLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		
		try{
			String leaveType=request.getParameter("LeaveType");
			String startDate=request.getParameter("StartDate");
			int i=0;
			String checkLeave="select count(*) from leave_details where leave_type='"+leaveType+"' and start_date='"+startDate+"'";
			ResultSet rsCheckLeave=ad.selectQuery(checkLeave);
			while(rsCheckLeave.next()){
				i=rsCheckLeave.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("calculateDays");
		
	}
	
	public ActionForward calculateDays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		 double onedayPLDAYs=0;
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String userLocation="";
			int plCount=0;
			String userLoc="select LOCATION_CODE,Plcount from Location where LOCID='"+user.getPlantId()+"'";
			ResultSet rsloc=ad.selectQuery(userLoc);
			while(rsloc.next()){
				userLocation=rsloc.getString("LOCATION_CODE");
				plCount=rsloc.getInt("Plcount");
			}
			String tableName=getTableName(userLocation);
			
		String getLeaveStruct="select * from Leave_Structure ";
		ResultSet rsLS=ad.selectQuery(getLeaveStruct);
		float casulLeaveBal=0;
		float casulLeavMaxBal=0;
		float previlageLeaveBal=0;
		float previlageLeaveMaxBal=0;
		float sickLeaveBal=0;
		float sickLeavMaxBal=0;
		float casulLeaveAdv=0;
		float casualhalfyear=0;
		int privmaxtime=0;
		
		double sicklvcloseBal=0;
		double casuallvcloseBal=0;
		String clawothltyp="";
		String slawothltyp="";
		String plawothltyp="";
		String lossOfPayawothltyp="";
		int lmindur=0;
		String startDate=request.getParameter("StartDate");  
		String reqStartDate=startDate;
		String startDateDuration=request.getParameter("StartDur");
		String endDate=request.getParameter("EndDate");
		String reqEndDate=endDate;
		String endDurationType=request.getParameter("EndDur");
		String leaveType=request.getParameter("LeaveType");
		float previlageAdv=0;
		while(rsLS.next()){
			if(rsLS.getString("LEAVTYP").equalsIgnoreCase("CL")){
				casulLeaveBal=rsLS.getFloat("LMINALW");
				casulLeavMaxBal=rsLS.getFloat("LMAXALW");
				casulLeaveAdv=rsLS.getFloat("LADVDAY");
				clawothltyp=rsLS.getString("AWOTHLTYP");
				casualhalfyear=rsLS.getFloat("LHALFYR");
				if(leaveType.equals("1"))
				lmindur=rsLS.getInt("LMINDUR");
			}
			if(rsLS.getString("LEAVTYP").equalsIgnoreCase("SL")){
				sickLeaveBal=rsLS.getFloat("LMINALW");
				sickLeavMaxBal=rsLS.getFloat("LMAXALW");
				slawothltyp=rsLS.getString("AWOTHLTYP");
				if(leaveType.equals("2"))
				lmindur=rsLS.getInt("LMINDUR");
			}
			if(rsLS.getString("LEAVTYP").equalsIgnoreCase("EL")){
				previlageLeaveBal=rsLS.getFloat("LMINALW");
				leaveForm.setPreleavmin(previlageLeaveBal);
				previlageLeaveMaxBal=rsLS.getFloat("LMAXALW");
				previlageAdv=rsLS.getFloat("LADVDAY");
				plawothltyp=rsLS.getString("AWOTHLTYP");
				privmaxtime=rsLS.getInt("LMAXTIME");
				if(leaveType.equals("3"))
				lmindur=rsLS.getInt("LMINDUR");
			}
			if(rsLS.getString("LEAVTYP").equalsIgnoreCase("LP")){
				lossOfPayawothltyp=rsLS.getString("AWOTHLTYP");
				if(leaveType.equals("4"))
				lmindur=rsLS.getInt("LMINDUR");
			}
		}
		System.out.println("calculateDays");
		
	
		
		int year=0;
		if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
		{
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		else
		{
			 year = Integer.parseInt(request.getParameter("year"));
		}
		
		double noOfDays=0;
		float closingBal=0;
		float awaitingBal=0;
		////leave restriction rule 
		String reststart=request.getParameter("StartDate");
		String restendate=request.getParameter("EndDate");
		
		String restst[]=reststart.split("/");
		String restend[]=restendate.split("/");
		
		reststart=restst[2]+"-"+restst[1]+"-"+restst[0];
		restendate=restend[2]+"-"+restend[1]+"-"+restend[0];
		
		String ag="select * from ESS_restriction_rule where prefix='Leave' and pernr='"+user.getEmployeeNo()+"' and (fromdate between '"+reststart+"' and '"+restendate+"' ) and (todate between '"+reststart+"' and '"+restendate+"')";
		ResultSet r=ad.selectQuery(ag);
		if(r.next())
		{
			leaveForm.setMessage("You cannot apply");
			noOfDays=-14.0;
			leaveForm.setTotalLeaveDays(noOfDays);
			return mapping.findForward("calculateDays");
		}
			
		
		String getLeaveBalence="select * from lv_type_d where lv_typeid='"+leaveType+"' and lv_empcode='"+user.getEmployeeNo()+"' and lv_calyear='"+year+"'";
		ResultSet rsLeaveBal=ad.selectQuery(getLeaveBalence);
		while(rsLeaveBal.next())
		{
			closingBal=rsLeaveBal.getFloat("lv_clbal");
			awaitingBal=rsLeaveBal.getFloat("lv_awtBal");
		}
		
		closingBal=closingBal-awaitingBal;
		
		 String[] monthName = {"","JAN", "FEB",
				  "MAR", "APR", "MAY", "JUN", "JUL",
				  "AUG", "SEP", "OCT", "NOV",
				  "DEC"
				  };
		 String a[]=startDate.split("/");
		 startDate=a[1];
			
		 String a1[]=endDate.split("/");
		 endDate=a1[1];
		
			String startMonth=a[1];
			String EndMonth=a1[1];	
			int staMont=Integer.parseInt(startMonth);
			int endMnth=Integer.parseInt(EndMonth);
				  Calendar cal = Calendar.getInstance();
				  startMonth = monthName[staMont];
				  System.out.println("startMonth name: " + startMonth);
				  EndMonth = monthName[endMnth];
				  System.out.println("EndMonth name: " + EndMonth);
				  
					 String startDate1=request.getParameter("StartDate");  
					 String b[]=startDate1.split("/");
					 String str_date=b[2]+b[1]+b[0];
					 
					 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					 Date date = new Date();
					 String currentdate=dateFormat.format(date);
					 String c1[]=currentdate.split("/");
					 currentdate=c1[0]+c1[1]+c1[2];
					 
					 int cnt=0;
                     boolean abc=false;
                     
                     //CHEKC leave can be applied after predefined days
                     String allowtoapply="";
                     
                     String abcdef="select case when convert(date, dateadd(day,-(select Apply_after_Leave from Location where locid='"+user.getPlantId()+"'),getdate()) )<='"+(a1[2]+"-"+a1[1]+"-"+a1[0])+"' then 1 else 0 end as allowdate ";
                     ResultSet nn=ad.selectQuery(abcdef);
                     while(nn.next())
                     {
                    	 allowtoapply=nn.getString("allowdate");
                     }
                     
                     if(allowtoapply.equalsIgnoreCase("0"))
                     {
                    	 noOfDays=-13.0;
                         leaveForm.setTotalLeaveDays(noOfDays);
                    	
                    		return mapping.findForward("calculateDays");
                     }
                     
  //casual leave condition                   
 //if(leaveType.equalsIgnoreCase("1"))
 if(false)                    
{
	String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+currentdate+"','"+str_date+"') as a";
	ResultSet rs=ad.selectQuery(db1);
	while(rs.next())
	{
		cnt=rs.getInt(1);
	}	
	if(cnt<=casulLeaveAdv)
	{
		//check sick leave balence 
		
		String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_typeid=2 and lv_calyear='"+year+"'";
		ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
		while(rsSickBal.next()){
		double closeBal=rsSickBal.getDouble("lv_clbal");	
		if(closeBal==0.0){
			abc=false;
		}else{
		  noOfDays=-3.0;  
		abc=true;
		}
		}
		
	}
	
}
if(leaveType.equalsIgnoreCase("2"))
{
	
	String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+currentdate+"','"+str_date+"') as a";
	ResultSet rs=ad.selectQuery(db1);
	while(rs.next())
	{
		cnt=rs.getInt(1);
	}	
/*	if(cnt<=4)
	{
		noOfDays=-4.0;
			abc=true;
	}
*/

}	
if(leaveType.equalsIgnoreCase("3"))
{
	String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+currentdate+"','"+str_date+"') as a";
	ResultSet rs=ad.selectQuery(db1);
	while(rs.next())
	{
		cnt=rs.getInt(1);
	}	
	//	if(cnt<previlageAdv)
	if(true)
	{	
		
		String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_calyear='"+year+"'";
		ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
		while(rsSickBal.next()){
			
		if(rsSickBal.getInt("lv_typeid")==2){
			 sicklvcloseBal=rsSickBal.getDouble("lv_clbal");
		}
		if(rsSickBal.getInt("lv_typeid")==1){
			 casuallvcloseBal=rsSickBal.getDouble("lv_clbal");
		}
		}
		if(sicklvcloseBal==0.0 && casuallvcloseBal==0.0){
			abc=false;
		}else{
			
		
			abc=false;
		}
	
		
	}

	
	}	
if(leaveType.equalsIgnoreCase("4"))
{
	String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+currentdate+"','"+str_date+"') as a";
	ResultSet rs=ad.selectQuery(db1);
	while(rs.next())
	{
		cnt=rs.getInt(1);
	}	
	/*if(cnt<=4)
	{
		noOfDays=-4.0;
		abc=true;
	}*/
	
}
	
	
				  
				  
				  
	  if(abc==false){
	
  if(leaveType.equalsIgnoreCase("4"))//Loss Of Pay
  {



		String checkDayType="";
		if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
		{
			if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
			{
				
			
				
				 String cou="";
	             String checkDay="";
	             //check emp cal
	             
	             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
	             ResultSet ac=ad.selectQuery(empca);
	             if(ac.next())
	             {
	          	  cou=ac.getString(1); 
	             }
	             if(cou.equalsIgnoreCase("0"))
	             {
	             checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
	             }
	             else
	             {
		             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+user.getEmployeeNo()+"'"; 
	             }
				
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
				}
				if((checkDayType.equalsIgnoreCase("W"))&& (startMonth.equalsIgnoreCase(EndMonth)) &&(a[0].equalsIgnoreCase(a1[0])))
					noOfDays=noOfDays+0.5;
			}
			
		
		}
	
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{
		    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				int noofdays=daysBetween(cal1.getTime(),cal2.getTime());
				 
					 if(staMont==endMnth)
					 {
						
						
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
					 }
					 if(staMont!=endMnth)
					 {
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						cou="";
				             String getCountQuery1="";
				             //check emp cal
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
				              ac=ad.selectQuery(empca);
				             if(ac.next())
				             {
				          	  cou=ac.getString(1); 
				             }
				             if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
				             }
				             else
				             {
								 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
				             }
							 
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						
						
					 } 
					 
				 
			}
			 else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") )){
				
					 if(staMont==endMnth)
					 {
						
						
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
						 
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
					
					 }
					 if(staMont!=endMnth)
					 {
						
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
							 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						
					 } 
				 
			 }else{

				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
					
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
					
						
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						  cou="";
				             String getCountQuery1="";
				             //check emp cal
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
				              ac=ad.selectQuery(empca);
				             if(ac.next())
				             {
				          	  cou=ac.getString(1); 
				             }
				             if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
				             }
				             else
				             {
								 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
				             }
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
							 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
							
						String cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			            String  empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			            ResultSet  ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
							 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
			             }
					
						
								ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
						
					 } 
					 
				 }
			 
		 
				 
			 }
			if(!leaveType.equalsIgnoreCase("4"))
			{
			if(closingBal<noOfDays)
				
			{
				noOfDays=0.0;
			}
			}
			
			///check holiday or not if statrt date and end date both same 
			 String cou="";
	         String checkDay="";
	         String checkDay1="";
	         //check emp cal
	         
	         String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
	         ResultSet ac=ad.selectQuery(empca);
	         if(ac.next())
	         {
	      	  cou=ac.getString(1); 
	         }
		
	         if(cou.equalsIgnoreCase("0"))
	         {
					 checkDay="select day,"+startMonth+" from "+tableName+" where (day='"+a[0]+"') and CYEAR='"+year+"'";
					 checkDay1="select day,"+EndMonth+" from "+tableName+" where (day='"+a1[0]+"') and CYEAR='"+year+"'";
	         }
	         else
	         {
	      	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
	      	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
	         }
					
	         ResultSet rsCheckDay=ad.selectQuery(checkDay);
					while(rsCheckDay.next())
					{
						checkDayType=rsCheckDay.getString(startMonth);
						if(!(checkDayType.equalsIgnoreCase("W")))
						noOfDays=-6.0;
					}
					
					   ResultSet rsCheckDay1=ad.selectQuery(checkDay1);
						while(rsCheckDay1.next())
						{
							checkDayType=rsCheckDay1.getString(EndMonth);
							if(!(checkDayType.equalsIgnoreCase("W")))
							noOfDays=-6.0;
						}
  }
				  
  if(closingBal>0)
	{
	  
	  
	  if (leaveType.equalsIgnoreCase("6"))// comp off
		{

			String checkDayType = "";
			if (startDateDuration.equalsIgnoreCase("FH")
					|| endDurationType.equalsIgnoreCase("SH")) {
				if ((startDateDuration.equalsIgnoreCase("FH") && endDurationType
						.equalsIgnoreCase("FH"))
						|| (startDateDuration
								.equalsIgnoreCase("SH") && endDurationType
								.equalsIgnoreCase("SH"))) {

					
					
					
					 String cou="";
		             String checkDay="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             if(cou.equalsIgnoreCase("0"))
		             {
		             checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
		             }
		             else
		             {
			             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+user.getEmployeeNo()+"'"; 
		             }
					
					ResultSet rsCheckDay = ad.selectQuery(checkDay);
					while (rsCheckDay.next()) {
						checkDayType = rsCheckDay
								.getString(startMonth);
					}
					if ((checkDayType.equalsIgnoreCase("W"))
							&& (startMonth
									.equalsIgnoreCase(EndMonth))
							&& (a[0].equalsIgnoreCase(a1[0])))
						noOfDays = noOfDays + 0.5;
				}

			}

			if ((startDateDuration.equalsIgnoreCase("FD") && endDurationType
					.equalsIgnoreCase("FD"))) {

				Calendar cal1 = new GregorianCalendar();
				Calendar cal2 = new GregorianCalendar();

				String startYear = a[2];
				startYear = startYear.replaceAll(" ", "");
				String endYear = a1[2];
				endYear = endYear.replaceAll(" ", "");
				cal1.set(Integer.parseInt(startYear), staMont,
						Integer.parseInt(a[0]));
				cal2.set(Integer.parseInt(endYear), endMnth,
						Integer.parseInt(a1[0]));
				int noofdays = daysBetween(cal1.getTime(),
						cal2.getTime());

				if (staMont == endMnth) {
					
						 String cou="";
		             String getCountQuery="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             
		             if(cou.equalsIgnoreCase("0"))
		             {
					 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'";
		             }
		             else
		             {
		            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'"; 
		             }
					
					
					ResultSet rsCount = ad
							.selectQuery(getCountQuery);
					while (rsCount.next()) {
						noOfDays = rsCount.getInt(1);
					}

				}
				if (staMont != endMnth) {

				
					String cou="";
		             String getCountQuery="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             
		             if(cou.equalsIgnoreCase("0"))
		             {
					 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' ";
		             }
		             
		             else
		             {
		            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' "; 
		             }
					 
					ResultSet rsCount = ad
							.selectQuery(getCountQuery);
					while (rsCount.next()) {
						noOfDays = rsCount.getInt(1);
					}

					
					cou="";
		             String getCountQuery1="";
		             //check emp cal
		             
		              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		              ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             if(cou.equalsIgnoreCase("0"))
		             {
					 getCountQuery1 = "select count("+EndMonth+") from " + tableName+ " where (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
		             }
		             else
		             {
		            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
		             }
					
					
					
					ResultSet rsCount1 = ad
							.selectQuery(getCountQuery1);
					while (rsCount1.next()) {
						noOfDays = noOfDays + rsCount1.getInt(1);
					}

				}

			} else if (startDateDuration.equalsIgnoreCase("FD")
					&& (endDurationType.equalsIgnoreCase("FH"))) {

				if (staMont == endMnth) {
					
					
					
					String cou="";
		             String getCountQuery="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             
		             if(cou.equalsIgnoreCase("0"))
		             {
					 getCountQuery = "select count("	+ startMonth + ") from " + tableName+ " where (day between " + a[0]	+ " and " + a1[0] + ") and "+ startMonth + " !='WO' and "+ startMonth + "!='SS' and "+ startMonth + " !='' and CYEAR='"+year+"'";
		             }
		             else
		             {
		            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]	+ " and " + a1[0] + ") and "+ startMonth + " !='WO' and "+ startMonth + "!='SS' and "+ startMonth + " !='' and CYEAR='"+year+"'"; 
		             }
					
					ResultSet rsCount = ad
							.selectQuery(getCountQuery);
					while (rsCount.next()) {
						noOfDays = rsCount.getInt(1);
					}
					noOfDays = 0.5 + noOfDays - 1;

				}
				if (staMont != endMnth) {
             		String cou="";
		             String getCountQuery="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             
		             if(cou.equalsIgnoreCase("0"))
		             {
					 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"'";
		             }
		             
		             else
		             {
		            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"'"; 
		             }
					
					ResultSet rsCount = ad
							.selectQuery(getCountQuery);
					while (rsCount.next()) {
						noOfDays = rsCount.getInt(1);
					}
                    cou="";
		             String getCountQuery1="";
		             //check emp cal
		             
		              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		              ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             if(cou.equalsIgnoreCase("0"))
		             {

					 getCountQuery1 = "select count("	+ EndMonth + ") from " + tableName+ " where (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"'";
		             }
		             else
		             {
		            	 getCountQuery1 = "select count("	+ EndMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"'";	 
		             }
					
					ResultSet rsCount1 = ad
							.selectQuery(getCountQuery1);
					while (rsCount1.next()) {
						noOfDays = noOfDays + rsCount1.getInt(1);
					}
					noOfDays = 0.5 + noOfDays - 1;

				}

			} else {

				if (startDateDuration.equalsIgnoreCase("SH")
						&& (endDurationType.equalsIgnoreCase("FH"))) {

					if (staMont == endMnth) {
						noOfDays = 1;
						
						
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount = ad
								.selectQuery(getCountQuery);
						while (rsCount.next()) {
							noOfDays = noOfDays + rsCount.getInt(1);
						}
						noOfDays = noOfDays - 2;

					}
					if (staMont != endMnth) {

						noOfDays = 1;
						
					
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             
			             else
			             {
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' "; 
			             }
						
						
						ResultSet rsCount = ad
								.selectQuery(getCountQuery);
						while (rsCount.next()) {
							noOfDays = noOfDays + rsCount.getInt(1);
						}

						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1 = "select count("+EndMonth+") from " + tableName+ " where (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             else
			             {
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
						ResultSet rsCount1 = ad
								.selectQuery(getCountQuery1);
						while (rsCount1.next()) {
							noOfDays = noOfDays
									+ rsCount1.getInt(1);
						}
						noOfDays = noOfDays - 2;

					}

				}
				if (startDateDuration.equalsIgnoreCase("SH")
						&& (endDurationType.equalsIgnoreCase("FD"))) {

					if (staMont == endMnth) {
						noOfDays = 0.5;
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount = ad
								.selectQuery(getCountQuery);
						while (rsCount.next()) {
							noOfDays = noOfDays + rsCount.getInt(1);
						}
						noOfDays = noOfDays - 1;

					}
					if (staMont != endMnth) {

						noOfDays = 0.5;
						if (Integer.parseInt(a[0]) != 31) {
								String cou="";
				             String getCountQuery="";
				             //check emp cal
				             
				             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
				             ResultSet ac=ad.selectQuery(empca);
				             if(ac.next())
				             {
				          	  cou=ac.getString(1); 
				             }
				             
				             if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' ";
				             }
				             
				             else
				             {
				            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' "; 
				             }
							ResultSet rsCount = ad
									.selectQuery(getCountQuery);
							while (rsCount.next()) {
								noOfDays = noOfDays
										+ rsCount.getInt(1) - 1;
							}
						}
						
					
						String cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			            ResultSet  ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1 = "select count("+EndMonth+") from " + tableName+ " where (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             else
			             {
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
						
						
						
						
						ResultSet rsCount1 = ad
								.selectQuery(getCountQuery1);
						while (rsCount1.next()) {
							noOfDays = noOfDays
									+ rsCount1.getInt(1);
						}
						noOfDays = noOfDays;

					}

				}

			}

			// /
			if (closingBal < noOfDays)

			{
				noOfDays = 0.0;
			}

			/*// /transaction chk for lapsed comofff or not
			double commoffdays = 0.0;
			String ij[] = reqStartDate.split("/");
			String lapsdate = ij[2] + "-" + ij[1] + "-" + ij[0];

			String data = "select SUM(NofDaysRemaining) as sum  from COMP_OT where Pernr='"
					+ user.getEmployeeNo()
					+ "'  and LapsBydate>'"
					+ lapsdate + "' and Comp_Availed='0' ";
			ResultSet rsdata = ad.selectQuery(data);
			while (rsdata.next()) {
				commoffdays = rsdata.getFloat("sum");
			}

			if (commoffdays < closingBal) {
				noOfDays = -11.0;
			}
*/
			//check holiday or not if statrt date and end date both same 
			 String cou="";
	         String checkDay="";
	         String checkDay1="";
	         //check emp cal
	         
	         String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
	         ResultSet ac=ad.selectQuery(empca);
	         if(ac.next())
	         {
	      	  cou=ac.getString(1); 
	         }
		
	         if(cou.equalsIgnoreCase("0"))
	         {
					 checkDay="select day,"+startMonth+" from "+tableName+" where (day='"+a[0]+"') and CYEAR='"+year+"'";
					 checkDay1="select day,"+EndMonth+" from "+tableName+" where (day='"+a1[0]+"') and CYEAR='"+year+"'";
	         }
	         else
	         {
	      	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
	      	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
	         }
					
	         ResultSet rsCheckDay=ad.selectQuery(checkDay);
					while(rsCheckDay.next())
					{
						checkDayType=rsCheckDay.getString(startMonth);
						if(!(checkDayType.equalsIgnoreCase("W")))
						noOfDays=-6.0;
					}
					
					   ResultSet rsCheckDay1=ad.selectQuery(checkDay1);
						while(rsCheckDay1.next())
						{
							checkDayType=rsCheckDay1.getString(EndMonth);
							if(!(checkDayType.equalsIgnoreCase("W")))
							noOfDays=-6.0;
						}
		}


	if(leaveType.equalsIgnoreCase("3"))//Previlage Leave or Earned leave
	{


		String checkDayType="";
		if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
		{
			if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
			{
				
				
				
				
				 String cou="";
	             String checkDay="";
	             //check emp cal
	             
	             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
	             ResultSet ac=ad.selectQuery(empca);
	             if(ac.next())
	             {
	          	  cou=ac.getString(1); 
	             }
	             
	             if(cou.equalsIgnoreCase("0"))
	             {
	             checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
	             }
	             else
	             {
		             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+user.getEmployeeNo()+"'"; 
	             }
				
				
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
				}
				if(checkDayType.equalsIgnoreCase("W")||checkDayType.equalsIgnoreCase("SS")||checkDayType.equalsIgnoreCase("WO"))
					noOfDays=noOfDays+0.5;
			}
			else{
				if(checkDayType.equalsIgnoreCase("W"))
					noOfDays=noOfDays+1;
			}
		
		}
		else{
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{
		    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				int noofdays=daysBetween(cal1.getTime(),cal2.getTime());
				 
					 if(staMont==endMnth)
					 {
							
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
				             } 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
							 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
				             }
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
///two months diff
						
						if(staMont+2==endMnth)
						{
							
							 String midmonth = monthName[staMont+1];
							cou="";
				             String midmonthQuery="";
				             //check emp cal
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
				              ac=ad.selectQuery(empca);
				             if(ac.next())
				             {
				          	  cou=ac.getString(1); 
				             }
				             if(plCount==1){
				             if(cou.equalsIgnoreCase("0"))
				             {
				            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
							
				             }
				             else
				             {
				            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
				             }
				             }else{
				            	 if(cou.equalsIgnoreCase("0"))
					             {
					            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
								
					             }
					             else
					             {
					            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
					             }
				             }
							
							
							ResultSet rsCount1=ad.selectQuery(midmonthQuery);
							while(rsCount1.next())
							{
								noOfDays=noOfDays+rsCount1.getInt(1);
							}
							
						}
						
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'";
						
			             }
			             else
			             {
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
							
				             }
				             else
				             {
				            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             } 
			             }
						
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 
			}
			 else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") )){
				
					 if(staMont==endMnth)
					 {
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
				             }
			             }
						
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
							 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
				             } 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						if(staMont+2==endMnth)
						{
							
							 String midmonth = monthName[staMont+1];
							cou="";
				             String midmonthQuery="";
				             //check emp cal
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
				              ac=ad.selectQuery(empca);
				             if(ac.next())
				             {
				          	  cou=ac.getString(1); 
				             }
				             if(plCount==1){
				             if(cou.equalsIgnoreCase("0"))
				             {
				            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
							
				             }
				             else
				             {
				            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
				             }
				             }else{
				            	 if(cou.equalsIgnoreCase("0"))
					             {
					            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
								
					             }
					             else
					             {
					            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
					             }
				             }
							
							
							ResultSet rsCount1=ad.selectQuery(midmonthQuery);
							while(rsCount1.next())
							{
								noOfDays=noOfDays+rsCount1.getInt(1);
							}
							
						}
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
						
			             }
			             else
			             {
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             }else{
			            	  if(cou.equalsIgnoreCase("0"))
					             {
								 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
								
					             }
					             else
					             {
					            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
					             }
			             }
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
				 
			 }else{

				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
						 
						
						
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
				             }
			            	 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
						 
					
						
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						if(staMont+2==endMnth)
						{
							
							 String midmonth = monthName[staMont+1];
							cou="";
				             String midmonthQuery="";
				             //check emp cal
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
				              ac=ad.selectQuery(empca);
				             if(ac.next())
				             {
				          	  cou=ac.getString(1); 
				             }
				             if(plCount==1){
				             if(cou.equalsIgnoreCase("0"))
				             {
				            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
							
				             }
				             else
				             {
				            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and 31)  and "+midmonth+" !=''and CYEAR='"+year+"'";
				             }
				             }else{
				            	 if(cou.equalsIgnoreCase("0"))
					             {
					            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
								
					             }
					             else
					             {
					            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
					             } 
				             }
							
							
							ResultSet rsCount1=ad.selectQuery(midmonthQuery);
							while(rsCount1.next())
							{
								noOfDays=noOfDays+rsCount1.getInt(1);
							}
							
						}
						
							cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+"='W' and "+EndMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+") and "+EndMonth+"='W' and "+EndMonth+" !='' and CYEAR='"+year+"'";
			             }
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
							
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
				             }
			            	 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
							 
						
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }	
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
				             }
			            	 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
						 
						 
						 
						 
							if(staMont+2==endMnth)
							{
								
								 String midmonth = monthName[staMont+1];
								 String cou="";
					             String midmonthQuery="";
					             //check emp cal
					             
					             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
					             ResultSet  ac=ad.selectQuery(empca);
					             if(ac.next())
					             {
					          	  cou=ac.getString(1); 
					             }
					             if(plCount==1){
					             if(cou.equalsIgnoreCase("0"))
					             {
					            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
								
					             }
					             else
					             {
					            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
					             }
					             }else{
					            	 if(cou.equalsIgnoreCase("0"))
						             {
						            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
									
						             }
						             else
						             {
						            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
						             } 
					            	 
					             }
								
								
								ResultSet rsCount1=ad.selectQuery(midmonthQuery);
								while(rsCount1.next())
								{
									noOfDays=noOfDays+rsCount1.getInt(1);
								}
								
							}
					
						String cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			             
			             
			             
			             String  empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet  ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(plCount==1){
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
						
			             }
			             else
			             {
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
							
				             }
				             else
				             {
				            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
				             }
			            	 
			             }
			             
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
						if(closingBal<noOfDays||noOfDays<0)
							noOfDays=0;
					 } 
					 
				 }
			 
		 
				 
			 }
			
		}
		
		
		
		//check holiday or not if statrt date and end date both same 
		 String cou="";
        String checkDay="";
        String checkDay1="";
        //check emp cal
        
        String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
        ResultSet ac=ad.selectQuery(empca);
        if(ac.next())
        {
     	  cou=ac.getString(1); 
        }
	
        if(cou.equalsIgnoreCase("0"))
        {
				 checkDay="select day,"+startMonth+" from "+tableName+" where (day='"+a[0]+"') and CYEAR='"+year+"'";
				 checkDay1="select day,"+EndMonth+" from "+tableName+" where (day='"+a1[0]+"') and CYEAR='"+year+"'";
        }
        else
        {
     	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
     	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
        }
				
        ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
					if(!(checkDayType.equalsIgnoreCase("W")))
					noOfDays=-6.0;
				}
				
				   ResultSet rsCheckDay1=ad.selectQuery(checkDay1);
					while(rsCheckDay1.next())
					{
						checkDayType=rsCheckDay1.getString(EndMonth);
						if(!(checkDayType.equalsIgnoreCase("W")))
						noOfDays=-6.0;
					}
	
		if(noOfDays<previlageLeaveBal||noOfDays>previlageLeaveMaxBal)
		{
			onedayPLDAYs=noOfDays;
			noOfDays=-12.0;
		}
		
		int maxcount=0;
		String maxtimeinyear="select COUNT(*) from leave_details where user_id='"+user.getEmployeeNo()+"' and leave_type='3'  and YEAR(start_date)='"+year+"' and Approvel_Status not in ('Self Cancelled','Cancelled','Rejected') ";
		ResultSet rsmaxtimeinyear=ad.selectQuery(maxtimeinyear);
		while(rsmaxtimeinyear.next()){
			maxcount=rsmaxtimeinyear.getInt(1);
		}
		if(maxcount>=privmaxtime)
		{
			noOfDays=-9.0;  
		}
	
	}
	if(leaveType.equalsIgnoreCase("2"))//SICK LEAVE
	{

		String checkDayType="";
		if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
		{
			if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
			{
				
				String cou="";
	             String checkDay="";
	             //check emp cal
	             
	             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
	             ResultSet ac=ad.selectQuery(empca);
	             if(ac.next())
	             {
	          	  cou=ac.getString(1); 
	             }
	             if(cou.equalsIgnoreCase("0"))
	             {
	             checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
	             }
	             else
	             {
		             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+user.getEmployeeNo()+"'"; 
	             }
				
				
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
				}
				if(checkDayType.equalsIgnoreCase("W")||checkDayType.equalsIgnoreCase("SS")||checkDayType.equalsIgnoreCase("WO"))
					noOfDays=noOfDays+0.5;
			}
			else{
				if(checkDayType.equalsIgnoreCase("W"))
					noOfDays=noOfDays+1;
			}
		}
		else{
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{
		    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				int noofdays=daysBetween(cal1.getTime(),cal2.getTime());
				 
					 if(staMont==endMnth)
					 {
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
							 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 
			}
			 else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") )){
				
					 if(staMont==endMnth)
					 {
							 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
						 
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
							 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
				 
			 }else{

				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
							
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
					
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and 31)  and " + startMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             
			             else
			             {
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and 31)  and " + startMonth + " !='' and CYEAR='"+year+"' "; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
							 cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1 = "select count("+EndMonth+") from " + tableName+ " where (day between 1 and " + a1[0]+ ")  and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             else
			             {
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and " + a1[0]+ ")  and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
						
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
						
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and 31)  and " + startMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             
			             else
			             {
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and 31)  and " + startMonth + " !='' and CYEAR='"+year+"' "; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
						String cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1 = "select count("+EndMonth+") from " + tableName+ " where (day between 1 and " + a1[0]+ ")  and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             else
			             {
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and " + a1[0]+ ")  and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
						
						
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
						if(closingBal<noOfDays||noOfDays<0)
							noOfDays=0;
					 } 
					 
				 }
			 
		 
				 
			 }
			
					
		}
		
		//check holiday or not if statrt date and end date both same 
		 String cou="";
        String checkDay="";
        String checkDay1="";
        //check emp cal
        
        String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
        ResultSet ac=ad.selectQuery(empca);
        if(ac.next())
        {
     	  cou=ac.getString(1); 
        }
	
        if(cou.equalsIgnoreCase("0"))
        {
				 checkDay="select day,"+startMonth+" from "+tableName+" where (day='"+a[0]+"') and CYEAR='"+year+"'";
				 checkDay1="select day,"+EndMonth+" from "+tableName+" where (day='"+a1[0]+"') and CYEAR='"+year+"'";
        }
        else
        {
     	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
     	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
        }
				
        ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
					if(!(checkDayType.equalsIgnoreCase("W")))
					noOfDays=-6.0;
				}
				
				   ResultSet rsCheckDay1=ad.selectQuery(checkDay1);
					while(rsCheckDay1.next())
					{
						checkDayType=rsCheckDay1.getString(EndMonth);
						if(!(checkDayType.equalsIgnoreCase("W")))
						noOfDays=-6.0;
					}
		//sick leave cannot be aplied in advance
		int start=Integer.parseInt(str_date);
		int present=Integer.parseInt(currentdate);
		if(start>present)
		{
			noOfDays=-10.0;
		}
	
			
	}
	if(leaveType.equalsIgnoreCase("1"))//CASUL LEAVE
	{
		String checkDayType="";
		String endDayType="";
	
		
		String cou1="";
		String checkEndDayType="";
   
        //check emp cal
        
        String empca1="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
        ResultSet ac1=ad.selectQuery(empca1);
        if(ac1.next())
        {
     	  cou1=ac1.getString(1); 
        }
        if(cou1.equalsIgnoreCase("0"))
        {
		 checkEndDayType="select day,"+startMonth+" from "+tableName+" where day='"+a1[0]+"' and CYEAR='"+year+"'";
        }
        else
        {
        	 checkEndDayType="select day,"+startMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and day='"+a1[0]+"' and CYEAR='"+year+"'";
        }
		ResultSet rsEndDayType=ad.selectQuery(checkEndDayType);
		while(rsEndDayType.next())
		{
			endDayType=rsEndDayType.getString(startMonth);
		}
	
		
		if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
		{
			if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
			{
					String cou="";
	             String checkDay="";
	             //check emp cal
	             
	             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
	             ResultSet ac=ad.selectQuery(empca);
	             if(ac.next())
	             {
	          	  cou=ac.getString(1); 
	             }
	             if(cou.equalsIgnoreCase("0"))
	             {
	             checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
	             }
	             else
	             {
		             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+user.getEmployeeNo()+"'"; 
	             }
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
				}
				if(checkDayType.equalsIgnoreCase("W"))
					noOfDays=noOfDays+0.5;
			
				
				
			}
			else{
				if(checkDayType.equalsIgnoreCase("W"))
					noOfDays=noOfDays+1;
			}
		
		}
		else{
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{
		    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				int noofdays=daysBetween(cal1.getTime(),cal2.getTime());
				 if(closingBal>noOfDays)
				 {
					 if(staMont==endMnth)
					 {
						 	 
							 String cou="";
				             String getCountQuery="";
				             //check emp cal
				             
				             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
				             ResultSet ac=ad.selectQuery(empca);
				             if(ac.next())
				             {
				          	  cou=ac.getString(1); 
				             }
				             
				             if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
				             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						 cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
							 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 }
				 else{
					 noOfDays=0;
					 leaveForm.setMessage("Casual Leave should not exceeds more than closing balence");
				 }
			}
			 else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") ||(endDurationType.equalsIgnoreCase("SH")))){
				
					 if(staMont==endMnth)
					 {
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
						 
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
					
						
						if(endDayType.equalsIgnoreCase("W")){
							if((endDurationType.equalsIgnoreCase("FH") ||(endDurationType.equalsIgnoreCase("SH")))){
							noOfDays=0.5+noOfDays-1;
							}
							
						}
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						
						String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
							 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
							
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
							ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
				 
			 }else{

				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
						 
							String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
					
						 String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery = "select count("+ startMonth + ") from " + tableName+ " where (day between " + a[0]+ " and 31)  and " + startMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             
			             else
			             {
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between " + a[0]+ " and 31)  and " + startMonth + " !='' and CYEAR='"+year+"' "; 
			             }
						
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			              ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1 = "select count("+EndMonth+") from " + tableName+ " where (day between 1 and " + a1[0]+ ")  and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
			             else
			             {
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and " + a1[0]+ ")  and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
			             }
							ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
					String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
					String cou="";
			             String getCountQuery="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
						String cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			            String  empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			            ResultSet  ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             else
			             {
							 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
						if(closingBal<noOfDays||noOfDays<0)
							noOfDays=0;
					 } 
					 
				 }
			 
		 
				 
			 }
			
		}
		
		////chk 3 days in 6 months claus
		String startdate=str_date;
		String enddate=reqEndDate;
	 
		 String c[]=enddate.split("/");
		  enddate=c[2]+c[1]+c[0];
		  

		  int year1=0;
			if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
			{
				year1=Calendar.getInstance().get(Calendar.YEAR);
			}
			else
			{
				year1 = Integer.parseInt(request.getParameter("year"));
			}
		  
		  String frstday=Integer.toString(year1)+"01"+"01";
		  String midday=Integer.toString(year1)+"06"+"30";
		  
		  int fstday=Integer.parseInt(frstday);
		  int mday=Integer.parseInt(midday);
		  int stdate=Integer.parseInt(startdate);
		  int endate=Integer.parseInt(enddate);
		  
		  if((stdate>=fstday && stdate<=mday)&&(endate>=fstday && endate<=mday))
		  {		  
		  
		
		double casualdays=0.0;
		String chkcasu="select * from leave_details where leave_type='1' and user_id='"+user.getEmployeeNo()+"'  and YEAR ( start_date )='"+year1+"' and Approvel_Status not in ('Self Cancelled','Cancelled','Rejected') and record_status!='Draft'";
		ResultSet rschkcasu=ad.selectQuery(chkcasu);
		while(rschkcasu.next())
		{
		casualdays=casualdays+Double.parseDouble(rschkcasu.getString("no_of_days"));
		}
		double reqcasualdays=casualdays+noOfDays;
		
		if(reqcasualdays>casualhalfyear)
		{
			noOfDays=-8.0;
		}
		  }
		  
		  if((stdate>=fstday && stdate<=mday)&&(endate>mday))
		  {
				double casualdays=0.0;
				String chkcasu="select * from leave_details where leave_type='1' and user_id='"+user.getEmployeeNo()+"'  and YEAR ( start_date )='"+year1+"' and Approvel_Status not in ('Self Cancelled','Cancelled','Rejected') and record_status!='Draft'";
				ResultSet rschkcasu=ad.selectQuery(chkcasu);
				while(rschkcasu.next())
				{
				casualdays=casualdays+Double.parseDouble(rschkcasu.getString("no_of_days"));
				}
				
				String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+str_date+"','"+midday+"') as a";
				ResultSet rs=ad.selectQuery(db1);
				while(rs.next())
				{
					cnt=rs.getInt(1);
				}	
				double reqcasualdays=0.0;
				 if(startDateDuration.equalsIgnoreCase("FD"))
				 {
				 reqcasualdays=casualdays+cnt;
				
				 }
				 else
				 {
					 reqcasualdays=casualdays+(cnt-0.5);
				 }
				if(reqcasualdays>casualhalfyear)
				{
					noOfDays=-8.0;
				}
				  
			  
		  }
		  
		  
		  
		////
		  
		/**/
		  
		  
		
		//check holiday or not if statrt date and end date both same 
			 String cou="";
	         String checkDay="";
	         String checkDay1="";
	         //check emp cal
	         
	         String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
	         ResultSet ac=ad.selectQuery(empca);
	         if(ac.next())
	         {
	      	  cou=ac.getString(1); 
	         }
		
	         if(cou.equalsIgnoreCase("0"))
	         {
					 checkDay="select day,"+startMonth+" from "+tableName+" where (day='"+a[0]+"') and CYEAR='"+year+"'";
					 checkDay1="select day,"+EndMonth+" from "+tableName+" where (day='"+a1[0]+"') and CYEAR='"+year+"'";
	         }
	         else
	         {
	      	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
	      	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
	         }
					
	         ResultSet rsCheckDay=ad.selectQuery(checkDay);
					while(rsCheckDay.next())
					{
						checkDayType=rsCheckDay.getString(startMonth);
						if(!(checkDayType.equalsIgnoreCase("W")))
						noOfDays=-6.0;
					}
					
					   ResultSet rsCheckDay1=ad.selectQuery(checkDay1);
						while(rsCheckDay1.next())
						{
							checkDayType=rsCheckDay1.getString(EndMonth);
							if(!(checkDayType.equalsIgnoreCase("W")))
							noOfDays=-6.0;
						}
		
		
		if(noOfDays==0.5)
			noOfDays=0.5;
		
	
		//check previous day of start date and nxt day of end date
		double nodays=0.0;
		String cas="select * from leave_details where  (convert(nvarchar,start_date-1,103)='"+reqStartDate+"' or convert(nvarchar,end_date+1,103)='"+reqStartDate+"' or  convert(nvarchar,start_date-1,103)='"+reqEndDate+"' or convert(nvarchar,end_date+1,103)='"+reqEndDate+"') and leave_type='1' and user_id='"+user.getEmployeeNo()+"' and  Approvel_Status not in ('Self Cancelled','Cancelled','Rejected')   and end_duration!='FH'";
		ResultSet rscas=ad.selectQuery(cas);
		while(rscas.next())
		{
			nodays=nodays+Double.parseDouble(rscas.getString("no_of_days"));
		}
		if(nodays>=casulLeavMaxBal)
		{
			noOfDays=casulLeavMaxBal+1;
		}
		
	}
	}
    
    //check onduty 
  String c[]=reqStartDate.split("/");
  reqStartDate=c[2]+"-"+c[1]+"-"+c[0];
  String d[]=reqEndDate.split("/");
  reqEndDate=d[2]+"-"+d[1]+"-"+d[0];
  
  String lvStDate= request.getParameter("StartDate"); 
  String lvEndDate=request.getParameter("EndDate");
  String  e1[]=lvStDate.split("/");
  lvStDate=e1[2]+"-"+e1[1]+"-"+e1[0];
  String f[]=lvEndDate.split("/"); 
  lvEndDate=f[2]+"-"+f[1]+"-"+f[0];
  
	  try{
		  
				  
	
	  	String getAppliedDates="Select * from OnDuty_details where (OnDuty_status=0 or OnDuty_status=1) and  user_id='"+user.getEmployeeNo()+"' " +
	  			"and (('"+reqStartDate+"' between start_date and end_date) or ('"+reqEndDate+"' between start_date and end_date))";
		ResultSet rsAppDate=ad.selectQuery(getAppliedDates);
		while(rsAppDate.next())
		{
			String startDt=rsAppDate.getString("start_date");
			
			String endDt=rsAppDate.getString("end_date");
			
			
			
			String fromTime=rsAppDate.getString("startTime");
			String fromTimeCycle=fromTime.substring(5, 7);

			String toTime=rsAppDate.getString("endTime");
			String toTimeCycle=toTime.substring(5, 7);
			
			fromTime=rsAppDate.getString("startTime");
			fromTime=fromTime.substring(0, 5);
			
			String fromT[]=fromTime.split(":");
			int fromHours=Integer.parseInt(fromT[0]);
			int fromMint=Integer.parseInt(fromT[1]);
			if(fromTimeCycle.equalsIgnoreCase("AM"))
			{
				fromHours=fromHours;
			}else{
				if(fromHours!=12)
				fromHours=fromHours+12;	
			}
			
			fromMint=(fromHours*60)+fromMint;
			toTime=rsAppDate.getString("endTime");
			toTime=toTime.substring(0, 5);
			
			String toT[]=toTime.split(":");
			int toHours=Integer.parseInt(toT[0]);
			int toMint=Integer.parseInt(toT[1]);
			if(toTimeCycle.equalsIgnoreCase("AM"))
			{
				toHours=toHours;
			}else{
				if(toHours!=12)
				toHours=toHours+12;	
			}
			
			toMint=(toHours*60)+toMint;
			fromTime=Integer.toString(fromHours)+":"+fromT[1]+":"+"00";
			toTime=Integer.toString(toHours)+":"+toT[1]+":"+"00";
			
			Date time1 = new SimpleDateFormat("HH:mm:ss").parse(fromTime);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(time1);

			Date time2 = new SimpleDateFormat("HH:mm:ss").parse(toTime);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(time2);
			calendar2.add(Calendar.DATE, 1);
			
			System.out.println("from Mint="+fromMint);
			System.out.println("To Mint="+toMint);
			
			if(startDt.equalsIgnoreCase(lvEndDate))
			{
				
				if(endDurationType.equalsIgnoreCase("FD")){
					noOfDays=-1.0;
				}
				if(endDurationType.equalsIgnoreCase("FH")){
					if(fromMint<780)
					{
						  noOfDays=-1.0;  
					}
				}
				
				if(endDurationType.equalsIgnoreCase("SH")){
					if(fromMint>780)
					{
						  noOfDays=-1.0;  
					}
				}
			}
			if(endDt.equalsIgnoreCase(lvStDate))
			{
				if(startDateDuration.equalsIgnoreCase("FD"))
						{
					  noOfDays=-1.0;  

						}
				if(startDateDuration.equalsIgnoreCase("FH"))
				{
					if(fromMint<780)
					{
						  noOfDays=-1.0;  
					}

				}
				if(startDateDuration.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
						  noOfDays=-1.0;  
					}

				}
				
			}
			
			if(startDt.equalsIgnoreCase(lvStDate))
			{
				if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-1.0;  
					}
				}
				if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
					noOfDays=-1.0;
					}
				}
				if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
				{
					noOfDays=-1.0;
				}
				
			}
			
			if(endDt.equalsIgnoreCase(lvEndDate))
			{
				if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-1.0;  
					}
				}
				if(endDurationType.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<=780)
					{
					noOfDays=-1.0;
					}
				}
				if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
					noOfDays=-1.0;
					}
				}
				if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
				{
					noOfDays=-1.0;
				}
				
			}
			
			if(!(endDt.equalsIgnoreCase(lvEndDate)) && !(startDt.equalsIgnoreCase(lvStDate))){
				noOfDays=-1.0;
			}
			
		}
		
	  }catch (Exception e) {
		e.printStackTrace();
	  }
      
	  //check Permission
	  
	  
	  
	  String checkPermission="Select * from Permission_details where (Approver_Status=0 or  Approver_Status=1) and  user_id='"+user.getEmployeeNo()+"'" +
	  		" and ((date  between '"+reqStartDate+"' and '"+reqEndDate+"'))";
	  ResultSet rscheckPermi=ad.selectQuery(checkPermission);
	  while(rscheckPermi.next())
	  {/*

			String startDt=rscheckPermi.getString("date");
			
			
			
			
			String fromTime=rscheckPermi.getString("startTime");
			String fromTimeCycle=fromTime.substring(5, 7);

			String toTime=rscheckPermi.getString("endTime");
			String toTimeCycle=toTime.substring(5, 7);
			
			fromTime=rscheckPermi.getString("startTime");
			fromTime=fromTime.substring(0, 5);
			
			String fromT[]=fromTime.split(":");
			int fromHours=Integer.parseInt(fromT[0]);
			int fromMint=Integer.parseInt(fromT[1]);
			if(fromTimeCycle.equalsIgnoreCase("AM"))
			{
				fromHours=fromHours;
			}else{
				if(fromHours!=12)
				fromHours=fromHours+12;	
			}
			
			fromMint=(fromHours*60)+fromMint;
			toTime=rscheckPermi.getString("endTime");
			toTime=toTime.substring(0, 5);
			
			String toT[]=toTime.split(":");
			int toHours=Integer.parseInt(toT[0]);
			int toMint=Integer.parseInt(toT[1]);
			if(toTimeCycle.equalsIgnoreCase("AM"))
			{
				toHours=toHours;
			}else{
				if(toHours!=12)
				toHours=toHours+12;	
			}
			
			toMint=(toHours*60)+toMint;
			fromTime=Integer.toString(fromHours)+":"+fromT[1]+":"+"00";
			toTime=Integer.toString(toHours)+":"+toT[1]+":"+"00";
			
			Date time1 = new SimpleDateFormat("HH:mm:ss").parse(fromTime);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(time1);

			Date time2 = new SimpleDateFormat("HH:mm:ss").parse(toTime);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(time2);
			calendar2.add(Calendar.DATE, 1);
			
			System.out.println("from Mint="+fromMint);
			System.out.println("To Mint="+toMint);
			
			if(startDt.equalsIgnoreCase(lvStDate))
			{
				if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-2.0;  
					}  
				}
				if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
						  noOfDays=-2.0;  
					}
				}
				if(startDateDuration.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint>780)
					{
						
						  noOfDays=-2.0;  
					}
				}
				if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
				{
					  noOfDays=-2.0;  
				}
				
			}
			if(startDt.equalsIgnoreCase(lvEndDate))
			{
				if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-2.0;  
					}
				}
				if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
						  noOfDays=-2.0;  
					}
				}
				if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
				{
					  noOfDays=-2.0;  
				}
				
				if(endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-2.0;  
					}
				}
				
			}
	  */}
	  
	  //check sick leave available or not
	  
	  String stm=startMonth;
		String enm=EndMonth;
		String value="";
		  
		 
		  
		  
		  boolean change=false;
		      if(leaveType.equalsIgnoreCase("1"))
			  if( clawothltyp.equalsIgnoreCase("X")){
				  change=true;
				  String sqlstartdate=reqStartDate;
					String sqlenddate=reqEndDate;
				  String f1[]=reqStartDate.split("-");
				  reqStartDate=f1[2]+"/"+f1[1]+"/"+f1[0];
				  String g1[]=reqEndDate.split("-");
				  reqEndDate=g1[2]+"/"+g1[1]+"/"+g1[0];
				  
				  int fu=Integer.parseInt(f1[2])-1;
				  int eu=Integer.parseInt(g1[2])+1;
				  int stcounter=0;
				  int encounter=0;
				  
				  
				
				  
				  if(f1[1].equalsIgnoreCase(g1[1]))
					{ 
					  if(!(endMnth==12 && eu>31))
					  {
					  if(eu==32)
					  {
						  eu=1;
						  enm = monthName[endMnth + 1];
					  }
					  }
					  if(fu==0)
					  {
						  fu=31;
						  stm = monthName[staMont-1];
					  }
					  
					}
				 
				  if(fu>=1)
					 {
				 do
				 {
					 
					 if(staMont==1 && fu==31)
					  {
						 break;
					  }
					 if(stm.equalsIgnoreCase(""))
						 stm="JAN";
					
					 if(fu==0)
					  {
						 String last="select day(CONVERT(varchar,dateadd(d,-(day('"+sqlstartdate+"')),'"+sqlstartdate+"'),106))";
                         ResultSet xyz=ad.selectQuery(last);
                         while(xyz.next())
                         {
                         fu=xyz.getInt(1);
                         }
                     	 if(staMont==1 && fu==31)
      					  {
      						 break;
      					  }
						
                        else
                        {
                       	 stm = monthName[staMont-1]; 
                        }
					}
					 String cou="";
		             String table="";
		             //check emp cal
		             
		             
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             if(cou.equalsIgnoreCase("0"))
		             {
					   table="select "+stm+" as m from "+tableName+" where DAY='"+fu+"' and CYEAR='"+year+"'";
		             }
		             else
		             {
		            	 table="select "+stm+" as m from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  DAY='"+fu+"' and CYEAR='"+year+"'"; 
		             }
				  
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  value=rstable.getString("m");
					}
				  fu =fu-1;
				  if(!value.equalsIgnoreCase(""))
				  stcounter=stcounter+1;
					
				  
			  }while(!value.equalsIgnoreCase("W"));
					 }
				  if(eu<=31)
					 {
				  
				 do
				 {
					 if(endMnth==12 && eu>31)
					  {
						 break;
					  }
					 
					 if(enm.equalsIgnoreCase(""))
						 enm="DEC";
					 
						if (eu >= 28) {
							int chklastdayofmonth=0;
							 String frst="select day(CONVERT(varchar,dateadd(d,-(day(dateadd(m,1,'"+sqlenddate+"'))),dateadd(m,1,'"+sqlenddate+"')),106))";
                                ResultSet xyz=ad.selectQuery(frst);
                                while(xyz.next())
                                {
                                chklastdayofmonth=xyz.getInt(1);
                                }
                                
                               
                            if(eu>chklastdayofmonth)    
                            {
							eu = 1;
							enm = monthName[endMnth + 1];
                            }
                                
						}
					 
						String cou="";
			             String table="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
				              table="select "+enm+" as m from "+tableName+" where DAY='"+eu+"' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	  table="select "+enm+" as m from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and DAY='"+eu+"' and CYEAR='"+year+"'"; 
			             }
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  value=rstable.getString("m");
					}
				  eu =eu+1;
				  if(!value.equalsIgnoreCase(""))
				  encounter=encounter+1;
				  
			  }while(!value.equalsIgnoreCase("W"));
					 }
				  
				  
				  double nodays=0.0;
				  String cas="";
				  
				  if(stcounter!=0)
					{
					if(stcounter==1)
					{
					
					 cas = "select * from leave_details where  ( convert(nvarchar,end_date+"
							+ stcounter
							+ ",103)='"
							+ reqStartDate
							+ "')  and user_id='"
							+ user.getEmployeeNo()
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (1,4,5,6)";
					}
					if(stcounter>1)
					{
						 cas = "select * from leave_details where  ( convert(nvarchar,end_date+"
									+ stcounter
									+ ",103)='"
									+ reqStartDate
									+ "')  and user_id='"
									+ user.getEmployeeNo()
									+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (4,5,6)";
					}					ResultSet rscas=ad.selectQuery(cas);
					while(rscas.next())
					{
						boolean allow= false;
						String oldstartduration=rscas.getString("start_duration");
						String oldendduration=rscas.getString("end_duration");
					
						String end[]=rscas.getString("end_date").split(" ");
						String end1[]=end[0].split("-");
						String end2=end1[2]+"-"+end1[1]+"-"+end1[0];
				         String reqstr[]=reqStartDate.split("/");
				         reqStartDate=reqstr[0]+"-"+reqstr[1]+"-"+reqstr[2];
						
						   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						    Date date1 = format.parse(end2);
						    Date date2 = format.parse(reqStartDate);
					
						int co=date1.compareTo(date2);
						if(co<=-1)
						{
							if(oldendduration.equalsIgnoreCase("FH"))
							{
								allow=true;
							}
							else
							{
								if(!startDateDuration.equalsIgnoreCase("SH"))
								{
									allow=false;
								}
								else
								{
									allow=true;
								}
							
							}
							
							
						}
						else
						{
							if(oldendduration.equalsIgnoreCase("SH"))
							{
								allow=true;
							}
							
							else
							{
								if(oldendduration.equalsIgnoreCase("FD")||oldendduration.equalsIgnoreCase("FH"))
								{
									if(startDateDuration.equalsIgnoreCase("FH"))
									{
									allow=true;
									}
									else
									{
										allow=false;
									}
								}
								
							}
							
						}
						if(allow==false)
					       {
					    	   noOfDays=-11.0;
					       }
				
						
					}
					
					}
					String cas1="";
					
					if(encounter!=0)
					{
					if(encounter==1)
					{

					 cas1 = "select * from leave_details where   ( convert(nvarchar,start_date-"
							+ encounter
							+ ",103)='"
							+ reqEndDate
							+ "')  and user_id='"
							+ user.getEmployeeNo()
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (1,4,5,6)";
					}
					if(encounter>1)
					{

					 cas1 = "select * from leave_details where   ( convert(nvarchar,start_date-"
							+ encounter
							+ ",103)='"
							+ reqEndDate
							+ "')  and user_id='"
							+ user.getEmployeeNo()
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (4,5,6)";
					}					
					ResultSet rscas1=ad.selectQuery(cas1);
					while(rscas1.next())
					{
						boolean allow= false;
						String oldstartduration=rscas1.getString("start_duration");
						String oldendduration=rscas1.getString("end_duration");
					
						String end[]=rscas1.getString("end_date").split(" ");
						String end1[]=end[0].split("-");
						String end2=end1[2]+"-"+end1[1]+"-"+end1[0];
				         String reqstr[]=reqStartDate.split("/");
				         reqStartDate=reqstr[0]+"-"+reqstr[1]+"-"+reqstr[2];
						
						   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						    Date date1 = format.parse(end2);
						    Date date2 = format.parse(reqStartDate);
					
						int co=date1.compareTo(date2);
						if(co<=-1)
						{
							if(oldendduration.equalsIgnoreCase("FH"))
							{
								allow=true;
							}
							else
							{
								if(endDurationType.equalsIgnoreCase("FH"))
								{
									allow=true;
								}
								else
								{
									allow=false;
								}
							
							}
							
							
						}
						else
						{
							if(oldstartduration.equalsIgnoreCase("SH"))
							{
								allow=true;
							}
							
							else
							{
								if(oldendduration.equalsIgnoreCase("FD")||oldendduration.equalsIgnoreCase("FH"))
								{
									if(startDateDuration.equalsIgnoreCase("FH"))
									{
									allow=true;
									}
									else
									{
										allow=false;
									}
								}
								
							}
							
						}
						if(allow==false)
					       {
					    	   noOfDays=-11.0;
					       }
				
						
					}
					
					
				  }
				  
				  
			  String checkSL="select * from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
			  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"') and leave_type!=6";
			  ResultSet rsCheckSL=ad.selectQuery(checkSL);
			  if(rsCheckSL.next()){
				 noOfDays=-4.0;
			  }
			  
				 
			  }
		      if(leaveType.equalsIgnoreCase("2"))
			  if( slawothltyp.equalsIgnoreCase("X")){
				  change=true;
				  String sqlstartdate=reqStartDate;
					String sqlenddate=reqEndDate;
				  String f1[]=reqStartDate.split("-");
				  reqStartDate=f1[2]+"/"+f1[1]+"/"+f1[0];
				  String g1[]=reqEndDate.split("-");
				  reqEndDate=g1[2]+"/"+g1[1]+"/"+g1[0];
				  
				  
				  int fu=Integer.parseInt(f1[2])-1;
				  int eu=Integer.parseInt(g1[2])+1;
				  int stcounter=0;
				  int encounter=0;
				  
				
				
				  if(f1[1].equalsIgnoreCase(g1[1]))
					{ 
					  if(!(endMnth==12 && eu>31))
					  {
					  if(eu==32)
					  {
						  eu=1;
						  enm = monthName[endMnth + 1];
					  }
					  }
					  if(fu==0)
					  {
						  fu=31;
						  stm = monthName[staMont-1];
					  }
					  
					}
				 
				  if(fu>=1)
					 {  
				 do
				 {
					 if(staMont==1 && fu==31)
					  {
						 break;
					  }
					 
					 if(stm.equalsIgnoreCase(""))
						 stm="JAN";
					 
					 if(fu==0)
					  {
						 String last="select day(CONVERT(varchar,dateadd(d,-(day('"+sqlstartdate+"')),'"+sqlstartdate+"'),106))";
                         ResultSet xyz=ad.selectQuery(last);
                         while(xyz.next())
                         {
                         fu=xyz.getInt(1);
                         }
                        
                        	 if(staMont==1 && fu==31)
       					  {
       						 break;
       					  }
						
                         else
                         {
                        	 stm = monthName[staMont-1]; 
                         }
					}
					 String cou="";
		             String table="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             if(cou.equalsIgnoreCase("0"))
		             {
					   table="select "+stm+" as m from "+tableName+" where DAY='"+fu+"' and CYEAR='"+year+"'";
		             }
		             else
		             {
		            	 table="select "+stm+" as m from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  DAY='"+fu+"' and CYEAR='"+year+"'"; 
		             }
				  
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  value=rstable.getString("m");
					}
				  fu =fu-1;
				  if(!value.equalsIgnoreCase(""))
				  stcounter=stcounter+1;
				  
			  }while(!value.equalsIgnoreCase("W"));
				 
					 }
				  
				  if(eu<=31)
					 {
				 do
				 {
					 
					 if(endMnth==12 && eu>31)
					  {
						 break;
					  }
					 if(enm.equalsIgnoreCase(""))
						 enm="DEC";
					 
						if (eu >= 28) {
							int chklastdayofmonth=0;
							 String frst="select day(CONVERT(varchar,dateadd(d,-(day(dateadd(m,1,'"+sqlenddate+"'))),dateadd(m,1,'"+sqlenddate+"')),106))";
                                ResultSet xyz=ad.selectQuery(frst);
                                while(xyz.next())
                                {
                                chklastdayofmonth=xyz.getInt(1);
                                }
                            if(eu>chklastdayofmonth)    
                            {
							eu = 1;
							enm = monthName[endMnth + 1];
                            }
							
						}
						  String cou="";
				             String table="";
				             //check emp cal
				             
				             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
				             ResultSet ac=ad.selectQuery(empca);
				             if(ac.next())
				             {
				          	  cou=ac.getString(1); 
				             }
				             if(cou.equalsIgnoreCase("0"))
				             {
					              table="select "+enm+" as m from "+tableName+" where DAY='"+eu+"' and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	  table="select "+enm+" as m from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and DAY='"+eu+"' and CYEAR='"+year+"'"; 
				             }
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  value=rstable.getString("m");
					}
				  eu =eu+1;
				  if(!value.equalsIgnoreCase(""))
				  encounter=encounter+1;
				  
			  }while(!value.equalsIgnoreCase("W"));
					 }
				  
				  double nodays=0.0;
				  String cas="";
				  
				  if(stcounter!=0)
					{
					if(stcounter==1)
					{
					
					 cas = "select * from leave_details where  ( convert(nvarchar,end_date+"
							+ stcounter
							+ ",103)='"
							+ reqStartDate
							+ "')  and user_id='"
							+ user.getEmployeeNo()
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (2,4,5,6)";
					}
					
					if(stcounter>1)
					{
						
						 cas = "select * from leave_details where  ( convert(nvarchar,end_date+"
									+ stcounter
									+ ",103)='"
									+ reqStartDate
									+ "')  and user_id='"
									+ user.getEmployeeNo()
									+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (4,5,6)";
					}					ResultSet rscas=ad.selectQuery(cas);
					while(rscas.next())
					{
						boolean allow= false;
						String oldstartduration=rscas.getString("start_duration");
						String oldendduration=rscas.getString("end_duration");
					
						String end[]=rscas.getString("end_date").split(" ");
						String end1[]=end[0].split("-");
						String end2=end1[2]+"-"+end1[1]+"-"+end1[0];
				         String reqstr[]=reqStartDate.split("/");
				         reqStartDate=reqstr[0]+"-"+reqstr[1]+"-"+reqstr[2];
						
						   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						    Date date1 = format.parse(end2);
						    Date date2 = format.parse(reqStartDate);
					
						int co=date1.compareTo(date2);
						if(co<=-1)
						{
							if(oldendduration.equalsIgnoreCase("FH"))
							{
								allow=true;
							}
							else
							{
								if(!startDateDuration.equalsIgnoreCase("SH"))
								{
									allow=false;
								}
								else
								{
									allow=true;
								}
							
							}
							
							
						}
						else
						{
							if(oldendduration.equalsIgnoreCase("SH"))
							{
								allow=true;
							}
							
							else
							{
								if(oldendduration.equalsIgnoreCase("FD")||oldendduration.equalsIgnoreCase("FH"))
								{
									if(startDateDuration.equalsIgnoreCase("FH"))
									{
									allow=true;
									}
									else
									{
										allow=false;
									}
								}
								
							}
							
						}
						if(allow==false)
					       {
					    	   noOfDays=-11.0;
					       }
				
					}	
					}
					
					String cas1="";
					if(encounter!=0)
					{
					
					if(encounter==1)
					{
					
					 cas1 = "select * from leave_details where   ( convert(nvarchar,start_date-"
							+ encounter
							+ ",103)='"
							+ reqEndDate
							+ "')  and user_id='"
							+ user.getEmployeeNo()
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (2,4,5,6)";
					}
					
					if(encounter>1)
					{
						 cas1 = "select * from leave_details where   ( convert(nvarchar,start_date-"
									+ encounter
									+ ",103)='"
									+ reqEndDate
									+ "')  and user_id='"
									+ user.getEmployeeNo()
									+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (4,5,6)";
						
					}					ResultSet rscas1=ad.selectQuery(cas1);
					while(rscas1.next())
					{
						boolean allow= false;
						String oldstartduration=rscas1.getString("start_duration");
						String oldendduration=rscas1.getString("end_duration");
					
						String end[]=rscas1.getString("end_date").split(" ");
						String end1[]=end[0].split("-");
						String end2=end1[2]+"-"+end1[1]+"-"+end1[0];
				         String reqstr[]=reqStartDate.split("/");
				         reqStartDate=reqstr[0]+"-"+reqstr[1]+"-"+reqstr[2];
						
						   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						    Date date1 = format.parse(end2);
						    Date date2 = format.parse(reqStartDate);
					
						int co=date1.compareTo(date2);
						if(co<=-1)
						{
							if(oldendduration.equalsIgnoreCase("FH"))
							{
								allow=true;
							}
							else
							{
								if(endDurationType.equalsIgnoreCase("FH"))
								{
									allow=true;
								}
								else
								{
									allow=false;
								}
							
							}
							
							
						}
						else
						{
							if(oldstartduration.equalsIgnoreCase("SH"))
							{
								allow=true;
							}
							
							else
							{
								if(oldendduration.equalsIgnoreCase("FD")||oldendduration.equalsIgnoreCase("FH"))
								{
									if(startDateDuration.equalsIgnoreCase("FH"))
									{
									allow=true;
									}
									else
									{
										allow=false;
									}
								}
								
							}
							
						}
						if(allow==false)
					       {
					    	   noOfDays=-11.0;
					       }
				
						
					}
					}
				  String checkSL="select * from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
				  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"')  and leave_type!=6";
				  ResultSet rsCheckSL=ad.selectQuery(checkSL);
				  if(rsCheckSL.next()){
					 noOfDays=-4.0;
				  }
				}
		      if(leaveType.equalsIgnoreCase("3"))
			  if( plawothltyp.equalsIgnoreCase("X")){
				  change=true;
				  String sqlstartdate=reqStartDate;
					String sqlenddate=reqEndDate;
				  String f1[]=reqStartDate.split("-");
				  reqStartDate=f1[2]+"/"+f1[1]+"/"+f1[0];
				  String g1[]=reqEndDate.split("-");
				  reqEndDate=g1[2]+"/"+g1[1]+"/"+g1[0];
				  
				  int fu=Integer.parseInt(f1[2])-1;
				  int eu=Integer.parseInt(g1[2])+1;
				  int stcounter=0;
				  int encounter=0;
				  
				
				  
				
				  if(f1[1].equalsIgnoreCase(g1[1]))
					{ 
					  if(!(endMnth==12 && eu>31))
					  {
					  if(eu==32)
					  {
						  eu=1;
						  enm = monthName[endMnth + 1];
					  }
					  }
					  if(fu==0)
					  {
						  fu=31;
						  stm = monthName[staMont-1];
					  }
					  
					}
				 
				  if(fu>=1)
					 {
				 do
				 {
					 if(staMont==1 && fu==31)
					  {
						 break;
					  }
					 
					 if(stm.equalsIgnoreCase(""))
						 stm="JAN";
					 
					 if(fu==0)
					  {
						 String last="select day(CONVERT(varchar,dateadd(d,-(day('"+sqlstartdate+"')),'"+sqlstartdate+"'),106))";
                         ResultSet xyz=ad.selectQuery(last);
                         while(xyz.next())
                         {
                         fu=xyz.getInt(1);
                         }
                     	 if(staMont==1 && fu==31)
      					  {
      						 break;
      					  }
						
                        else
                        {
                       	 stm = monthName[staMont-1]; 
                        }
					}
					  String cou="";
			             String table="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
						   table="select "+stm+" as m from "+tableName+" where DAY='"+fu+"' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	 table="select "+stm+" as m from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  DAY='"+fu+"' and CYEAR='"+year+"'"; 
			             }
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  value=rstable.getString("m");
					}
				  fu =fu-1;
				  if(!value.equalsIgnoreCase(""))
				  stcounter=stcounter+1;
				  
			  }while(!value.equalsIgnoreCase("W"));
				 
					 }
				  if(eu<=31)
					 {
				 do
				 {
					 if(endMnth==12 && eu>31)
					  {
						 break;
					  }
					 if(enm.equalsIgnoreCase(""))
						 enm="DEC";
						if (eu >= 28) {
							int chklastdayofmonth=0;
							 String frst="select day(CONVERT(varchar,dateadd(d,-(day(dateadd(m,1,'"+sqlenddate+"'))),dateadd(m,1,'"+sqlenddate+"')),106))";
                                ResultSet xyz=ad.selectQuery(frst);
                                while(xyz.next())
                                {
                                chklastdayofmonth=xyz.getInt(1);
                                }
                            if(eu>chklastdayofmonth)    
                            {
							eu = 1;
							enm = monthName[endMnth + 1];
                            }
							
						}
				
				  
				  String cou="";
		             String table="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             if(cou.equalsIgnoreCase("0"))
		             {
			              table="select "+enm+" as m from "+tableName+" where DAY='"+eu+"' and CYEAR='"+year+"'";
		             }
		             else
		             {
		            	  table="select "+enm+" as m from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and DAY='"+eu+"' and CYEAR='"+year+"'"; 
		             }
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  value=rstable.getString("m");
					}
				  eu =eu+1;
				  if(!value.equalsIgnoreCase(""))
				  encounter=encounter+1;
				  
			  }while(!value.equalsIgnoreCase("W"));
					 }
				  
				  double nodays=0.0;
				  String cas="";
				  
				  if(stcounter!=0)
					{
					if(stcounter>0)
					{
					
					 cas = "select * from leave_details where  ( convert(nvarchar,end_date+"
							+ stcounter
							+ ",103)='"
							+ reqStartDate
							+ "')  and user_id='"
							+ user.getEmployeeNo()
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (3,4,5,6)";
					}
					
									ResultSet rscas=ad.selectQuery(cas);
					while(rscas.next())
					{
						boolean allow= false;
						String oldstartduration=rscas.getString("start_duration");
						String oldendduration=rscas.getString("end_duration");
					
						String end[]=rscas.getString("end_date").split(" ");
						String end1[]=end[0].split("-");
						String end2=end1[2]+"-"+end1[1]+"-"+end1[0];
				         String reqstr[]=reqStartDate.split("/");
				         reqStartDate=reqstr[0]+"-"+reqstr[1]+"-"+reqstr[2];
						
						   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						    Date date1 = format.parse(end2);
						    Date date2 = format.parse(reqStartDate);
					
						int co=date1.compareTo(date2);
						if(co<=-1)
						{
							if(oldendduration.equalsIgnoreCase("FH"))
							{
								allow=true;
							}
							else
							{
								if(!startDateDuration.equalsIgnoreCase("SH"))
								{
									allow=false;
								}
								else
								{
									allow=true;
								}
							
							}
							
							
						}
						else
						{
							if(oldendduration.equalsIgnoreCase("SH"))
							{
								allow=true;
							}
							
							else
							{
								if(oldendduration.equalsIgnoreCase("FD")||oldendduration.equalsIgnoreCase("FH"))
								{
									if(startDateDuration.equalsIgnoreCase("FH"))
									{
									allow=true;
									}
									else
									{
										allow=false;
									}
								}
								
							}
							
						}
						if(allow==false)
					       {
					    	   noOfDays=-11.0;
					       }
				
						
					}}
					
					
					//CHK nofdays 0 or not and allow to apply 1 day pl in sequence
					
					
			
					
					String oneday = "select *,convert(nvarchar,end_date,103) as endd from leave_details where  (( convert(nvarchar,end_date+"+stcounter+",103)='"+reqStartDate+"') or ( convert(nvarchar,end_date,103)='"+reqStartDate+"'))  and user_id='"+user.getEmployeeNo()+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type=3";
					ResultSet rsoneday = ad.selectQuery(oneday);
					while (rsoneday.next()) {
						String startdur=rsoneday.getString("start_duration");
						String enddur=rsoneday.getString("end_duration");
						String endDATE=rsoneday.getString("endd");
						
					if(noOfDays==-12.0)
					{
						
						if(enddur.equalsIgnoreCase("FD"))
						{
						if(!startDateDuration.equalsIgnoreCase("SH"))
						{
						noOfDays=onedayPLDAYs;
						}		
						
						}
						
                        else
							
						{
                        	if(endDATE.equalsIgnoreCase(reqStartDate))
                        	{
							if(startDateDuration.equalsIgnoreCase("SH"))
							{
							noOfDays=onedayPLDAYs;
							}		
                        	}
						}
					}
				
					}
					
					
					String cas1="";
					if(encounter!=0)
					{
					
					if(encounter>0)
					{
					
					 cas1 = "select * from leave_details where   ( convert(nvarchar,start_date-"
							+ encounter
							+ ",103)='"
							+ reqEndDate
							+ "')  and user_id='"
							+ user.getEmployeeNo()
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (3,4,5,6)";
					}
					
									ResultSet rscas1=ad.selectQuery(cas1);
					while(rscas1.next())
					{
						boolean allow= false;
						String oldstartduration=rscas1.getString("start_duration");
						String oldendduration=rscas1.getString("end_duration");
					
						String end[]=rscas1.getString("end_date").split(" ");
						String end1[]=end[0].split("-");
						String end2=end1[2]+"-"+end1[1]+"-"+end1[0];
				         String reqstr[]=reqStartDate.split("/");
				         reqStartDate=reqstr[0]+"-"+reqstr[1]+"-"+reqstr[2];
						
						   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						    Date date1 = format.parse(end2);
						    Date date2 = format.parse(reqStartDate);
					
						int co=date1.compareTo(date2);
						if(co<=-1)
						{
							if(oldendduration.equalsIgnoreCase("FH"))
							{
								allow=true;
							}
							else
							{
								if(endDurationType.equalsIgnoreCase("FH"))
								{
									allow=true;
								}
								else
								{
									allow=false;
								}
							
							}
							
							
						}
						else
						{
							if(oldstartduration.equalsIgnoreCase("SH"))
							{
								allow=true;
							}
							
							else
							{
								if(oldendduration.equalsIgnoreCase("FD")||oldendduration.equalsIgnoreCase("FH"))
								{
									if(startDateDuration.equalsIgnoreCase("FH"))
									{
									allow=true;
									}
									else
									{
										allow=false;
									}
								}
								
							}
							
						}
						if(allow==false)
					       {
					    	   noOfDays=-11.0;
					       }
				
						
					}
					}
					
	//CHK nofdays 0 or not and allow to apply 1 day pl in sequence
					
					
				
					
					String oneday1 = "select *,convert(nvarchar,start_date,103) as startt from leave_details where  ( ( convert(nvarchar,start_date-"+ encounter+ ",103)='"+ reqEndDate	+ "') or   ( convert(nvarchar,start_date,103)='"+ reqEndDate	+ "')) and user_id='"+ user.getEmployeeNo()	+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type=3";
					
					ResultSet rsoneday1 = ad.selectQuery(oneday1);
					while (rsoneday1.next()) {
						
						String startdur=rsoneday1.getString("start_duration");
						String enddur=rsoneday1.getString("end_duration");
						String startDATE=rsoneday1.getString("startt");
						
					if(noOfDays==-12.0)
					{
						
						
						if(startdur.equalsIgnoreCase("FD"))
						{
						if(!startDateDuration.equalsIgnoreCase("FH"))
						{
						noOfDays=onedayPLDAYs;
						}		
						
						}
						  else
								
							{
	                        	if(startDATE.equalsIgnoreCase(reqEndDate))
	                        	{
								if(startDateDuration.equalsIgnoreCase("FH"))
								{
								noOfDays=onedayPLDAYs;
								}		
	                        	}
							}
							

					}
				
					}
					
			  
				  
				  String checkSL="select * from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
				  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"')  and leave_type!=6";
				  ResultSet rsCheckSL=ad.selectQuery(checkSL);
				  if(rsCheckSL.next()){
					 noOfDays=-4.0;
				  }
				}
		      if(leaveType.equalsIgnoreCase("4"))
			  if( lossOfPayawothltyp.equalsIgnoreCase("X")){
				  change=true;
				  String sqlstartdate=reqStartDate;
					String sqlenddate=reqEndDate;
				  String f1[]=reqStartDate.split("-");
				  reqStartDate=f1[2]+"/"+f1[1]+"/"+f1[0];
				  String g1[]=reqEndDate.split("-");
				  reqEndDate=g1[2]+"/"+g1[1]+"/"+g1[0];
				  
				  int fu=Integer.parseInt(f1[2])-1;
				  int eu=Integer.parseInt(g1[2])+1;
				  int stcounter=0;
				  int encounter=0;
				
				  
				  if(f1[1].equalsIgnoreCase(g1[1]))
					{ 
					  if(!(endMnth==12 && eu>31))
					  {
					  if(eu==32)
					  {
						  eu=1;
						  enm = monthName[endMnth + 1];					 
					}
					  }
					  if(fu==0)
					  {
						  fu=31;
						  stm = monthName[staMont-1];
					  }
					  
					}
				  
				 
				  if(fu>=1)
					 {
				 do
				 {
					 if(staMont==1 && fu==31)
					  {
						 break;
					  }
					 
					 if(stm.equalsIgnoreCase(""))
						 stm="JAN";
					 
					 if(fu==0)
					  {
						 String last="select day(CONVERT(varchar,dateadd(d,-(day('"+sqlstartdate+"')),'"+sqlstartdate+"'),106))";
                         ResultSet xyz=ad.selectQuery(last);
                         while(xyz.next())
                         {
                         fu=xyz.getInt(1);
                         }
						stm = monthName[staMont - 1];
					}
					 
					 String cou="";
		             String table="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
		             ResultSet ac=ad.selectQuery(empca);
		             if(ac.next())
		             {
		          	  cou=ac.getString(1); 
		             }
		             if(cou.equalsIgnoreCase("0"))
		             {
					   table="select "+stm+" as m from "+tableName+" where DAY='"+fu+"' and CYEAR='"+year+"'";
		             }
		             else
		             {
		            	 table="select "+stm+" as m from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and  DAY='"+fu+"' and CYEAR='"+year+"'"; 
		             }
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  value=rstable.getString("m");
					}
				  fu =fu-1;
				  if(!value.equalsIgnoreCase(""))
				  stcounter=stcounter+1;
				  
			  }while(!value.equalsIgnoreCase("W"));
					 }
				 
				  if(eu<=31)
					 {
				 do
				 {
					 
					 if(endMnth==12 && eu>31)
					  {
						 break;
					  }
					 if(enm.equalsIgnoreCase(""))
						 enm="DEC";
					 
						if (eu >= 28) {
							int chklastdayofmonth=0;
							 String frst="select day(CONVERT(varchar,dateadd(d,-(day(dateadd(m,1,'"+sqlenddate+"'))),dateadd(m,1,'"+sqlenddate+"')),106))";
                                ResultSet xyz=ad.selectQuery(frst);
                                while(xyz.next())
                                {
                                chklastdayofmonth=xyz.getInt(1);
                                }
                            if(eu>chklastdayofmonth)    
                            {
							eu = 1;
							enm = monthName[endMnth + 1];
                            }
							
						}
						 String cou="";
			             String table="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
			             if(ac.next())
			             {
			          	  cou=ac.getString(1); 
			             }
			             if(cou.equalsIgnoreCase("0"))
			             {
				              table="select "+enm+" as m from "+tableName+" where DAY='"+eu+"' and CYEAR='"+year+"'";
			             }
			             else
			             {
			            	  table="select "+enm+" as m from EMP_CAL where Pernr='"+user.getEmployeeNo()+"' and DAY='"+eu+"' and CYEAR='"+year+"'"; 
			             }
					  
					  
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  value=rstable.getString("m");
					}
				  eu =eu+1;
				  if(!value.equalsIgnoreCase(""))
				  encounter=encounter+1;
				  
			  }while(!value.equalsIgnoreCase("W"));
					 }
				  
				  double nodays=0.0;
					String cas="select * from leave_details where  ( convert(nvarchar,end_date+"+stcounter+",103)='"+reqStartDate+"')  and user_id='"+user.getEmployeeNo()+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')";
					ResultSet rscas=ad.selectQuery(cas);
					while(rscas.next())
					{
						boolean allow= false;
						String oldstartduration=rscas.getString("start_duration");
						String oldendduration=rscas.getString("end_duration");
					
						String end[]=rscas.getString("end_date").split(" ");
						String end1[]=end[0].split("-");
						String end2=end1[2]+"-"+end1[1]+"-"+end1[0];
				         String reqstr[]=reqStartDate.split("/");
				         reqStartDate=reqstr[0]+"-"+reqstr[1]+"-"+reqstr[2];
						
						   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						    Date date1 = format.parse(end2);
						    Date date2 = format.parse(reqStartDate);
					
						int co=date1.compareTo(date2);
						if(co<=-1)
						{
							if(oldendduration.equalsIgnoreCase("FH"))
							{
								allow=true;
							}
							else
							{
								if(!startDateDuration.equalsIgnoreCase("SH"))
								{
									allow=false;
								}
								else
								{
									allow=true;
								}
							
							}
							
							
						}
						else
						{
							if(oldendduration.equalsIgnoreCase("SH"))
							{
								allow=true;
							}
							
							else
							{
								if(oldendduration.equalsIgnoreCase("FD")||oldendduration.equalsIgnoreCase("FH"))
								{
									if(startDateDuration.equalsIgnoreCase("FH"))
									{
									allow=true;
									}
									else
									{
										allow=false;
									}
								}
								
							}
							
						}
						if(allow==false)
					       {
					    	   noOfDays=-11.0;
					       }
				
						
					}
					
					String cas1="select * from leave_details where   ( convert(nvarchar,start_date-"+encounter+",103)='"+reqEndDate+"')  and user_id='"+user.getEmployeeNo()+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')";
					ResultSet rscas1=ad.selectQuery(cas1);
					while(rscas1.next())
					{
						boolean allow= false;
						String oldstartduration=rscas1.getString("start_duration");
						String oldendduration=rscas1.getString("end_duration");
					
						String end[]=rscas1.getString("end_date").split(" ");
						String end1[]=end[0].split("-");
						String end2=end1[2]+"-"+end1[1]+"-"+end1[0];
				         String reqstr[]=reqStartDate.split("/");
				         reqStartDate=reqstr[0]+"-"+reqstr[1]+"-"+reqstr[2];
						
						   SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						    Date date1 = format.parse(end2);
						    Date date2 = format.parse(reqStartDate);
					
						int co=date1.compareTo(date2);
						if(co<=-1)
						{
							if(oldendduration.equalsIgnoreCase("FH"))
							{
								allow=true;
							}
							else
							{
								if(endDurationType.equalsIgnoreCase("FH"))
								{
									allow=true;
								}
								else
								{
									allow=false;
								}
							}
							
							
						}
						else
						{
							if(oldendduration.equalsIgnoreCase("SH"))
							{
								allow=true;
							}
							
							else
							{
								if(oldendduration.equalsIgnoreCase("FD")||oldendduration.equalsIgnoreCase("FH"))
								{
									if(startDateDuration.equalsIgnoreCase("FH"))
									{
									allow=true;
									}
									else
									{
										allow=false;
									}
								}
								
							}
							
						}
						if(allow==false)
					       {
					    	   noOfDays=-11.0;
					       }
				
						
					}
				  
				  String checkSL="select * from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
				  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"')";
				  ResultSet rsCheckSL=ad.selectQuery(checkSL);
				  if(rsCheckSL.next()){
					 noOfDays=-4.0;
				  }
				}
		      
		      
		      if(leaveType.equalsIgnoreCase("6"))
		      {
		      String checkSL="select * from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
					  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"')";
					  ResultSet rsCheckSL=ad.selectQuery(checkSL);
					  if(rsCheckSL.next()){
						 noOfDays=-4.0;
					  }
		      }
	    /*  if(change==true)
		  {
			  String f1[]=reqStartDate.split("/");
			  reqStartDate=f1[2]+"-"+f1[1]+"-"+f1[0];
			  String g1[]=reqEndDate.split("/");
			  reqEndDate=g1[2]+"-"+g1[1]+"-"+g1[0];
		  }*/
	  
		  
	  }
	  
	  
	
	  
	  //check minimum duration between two leaves
	  // reqStartDate,reqStartDate,lmindur
	  
	 /* String nextlvStDt="";
	     String prevlvEndDt="";
	  String getPreviousLv="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY l.id) AS RowNum, l.start_date,l.end_date from leave_details as l 	 " +
	  "where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and '"+reqStartDate+"'>=l.end_date) as  sub Where  sub.RowNum between 1 and 1 order by RowNum desc";
	  ResultSet rsPreviouslv=ad.selectQuery(getPreviousLv);
	  while(rsPreviouslv.next()){
		  prevlvEndDt=rsPreviouslv.getString("end_date");
	      String aa[]=prevlvEndDt.split(" ");
	      prevlvEndDt=aa[0];
	      int betweenDays=0;
	  	 String getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+prevlvEndDt+"','"+reqStartDate+"')";
	  	 ResultSet rsNoOfDays=ad.selectQuery(getNoOfDays);
	  	 while(rsNoOfDays.next()){
	  		 betweenDays=rsNoOfDays.getInt(1);
	  	 }
	  	if(lmindur>betweenDays){
			 noOfDays=-7.0;
		 }
	  }
	  
 	
 	String getNextLv="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY l.id) AS RowNum,l.start_date from leave_details as l " +
 	"where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and '"+reqStartDate+"'<=l.start_date ) as  sub Where  sub.RowNum between 1 and 1 order by RowNum desc";
	  ResultSet rsNextLv=ad.selectQuery(getNextLv);
	  while(rsNextLv.next()){
		  nextlvStDt=rsNextLv.getString("start_date");
	      String aa[]=nextlvStDt.split(" ");
	      nextlvStDt=aa[0];
	      int betweenDays=0;
	    String  getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+reqStartDate+"','"+nextlvStDt+"')";
		ResultSet  rsNoOfDays=ad.selectQuery(getNoOfDays);
		 while(rsNoOfDays.next()){
			 betweenDays=rsNoOfDays.getInt(1);
		 }
		if(lmindur>betweenDays){
			 noOfDays=-7.0;
		 }
	  }*/
	  
	 
 	
	  /*String getLastTwoLeaves="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY l.id) AS RowNum,l.start_date,l.end_date from leave_details as l " +
	  "where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' ) as  sub Where  sub.RowNum between 1 and 2 order by RowNum desc";
     ResultSet rsLast2lv=ad.selectQuery(getLastTwoLeaves);
     int totalLeaves=0;
     String prevlvStDt="";
     String prevlvEndDt="";
     while(rsLast2lv.next()){
    	 totalLeaves=totalLeaves+1;
    	 if(totalLeaves==1){
    	 prevlvEndDt=rsLast2lv.getString("end_date");
    	 String aa[]=prevlvEndDt.split(" ");
    	 prevlvEndDt=aa[0];
    	 }
    	 if(totalLeaves==2){
    		 prevlvStDt=rsLast2lv.getString("start_date");
    		 String aa[]=prevlvStDt.split(" ");
    		 prevlvStDt=aa[0];
    	 } 
     } 
     if(totalLeaves==1){
    	 System.out.println(reqStartDate);
    	 int betweenDays=0;
    	 String getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+prevlvEndDt+"','"+reqStartDate+"')";
    	 ResultSet rsNoOfDays=ad.selectQuery(getNoOfDays);
    	 while(rsNoOfDays.next()){
    		 betweenDays=rsNoOfDays.getInt(1);
    	 }
    	 System.out.println(betweenDays);
    	 if(lmindur>betweenDays){
    		 noOfDays=-7.0;
    	 }
     }
     if(totalLeaves==2){
    	 int betweenDays=0;
    	 String getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+reqStartDate+"','"+prevlvEndDt+"')";
    	 ResultSet rsNoOfDays=ad.selectQuery(getNoOfDays);
    	 while(rsNoOfDays.next()){
    		 betweenDays=rsNoOfDays.getInt(1);
    	 }
    	 System.out.println(betweenDays);
    	 if(betweenDays>0)
    	 if(lmindur>betweenDays){
    		 noOfDays=-7.0;
    	 }
    	  getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+reqStartDate+"','"+prevlvStDt+"')";
    	  rsNoOfDays=ad.selectQuery(getNoOfDays);
    	 while(rsNoOfDays.next()){
    		 betweenDays=rsNoOfDays.getInt(1);
    	 }
    	 if(betweenDays>0)
    	 if(lmindur>betweenDays){
    		 noOfDays=-7.0;
    	 }
     }*/
	  
	  
		noOfDays=noOfDays;
		leaveForm.setTotalLeaveDays(noOfDays);
		ArrayList totalDays=new ArrayList();
		LeaveForm leaveDays=new LeaveForm();
		leaveDays.setTotalLeaveDays(noOfDays);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("calculateDays");
	}
	
	 public int daysBetween(Date d1, Date d2){
		 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
		 }
	 
		public void setLeaveType(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
	LeaveForm leaveForm=(LeaveForm)form;
			
			LinkedList leaveTypeID=new LinkedList();
			LinkedList leaveType=new LinkedList();
			
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			try{
				String getGender="select gender from emp_personal_info where user_id='"+user.getUserName()+"'";
				ResultSet rsGender=ad.selectQuery(getGender);
				String gender="";
				while(rsGender.next()){
					gender=rsGender.getString("gender");
				}
				if(gender.equalsIgnoreCase("Male"))
				{
					String getLeaveType="select * from lv_type_m where lv_typeid!=6";
					ResultSet rs=ad.selectQuery(getLeaveType);
					while(rs.next())
					{
						leaveTypeID.add(rs.getString("lv_typeid"));
						leaveType.add(rs.getString("lv_type"));
					}
					leaveForm.setLeaveTypeID(leaveTypeID);
					leaveForm.setLeaveTypeName(leaveType);
				}else{
					String getLeaveType="select * from lv_type_m ";
					ResultSet rs=ad.selectQuery(getLeaveType);
					while(rs.next())
					{
						leaveTypeID.add(rs.getString("lv_typeid"));
						leaveType.add(rs.getString("lv_type"));
					}
					leaveForm.setLeaveTypeID(leaveTypeID);
					leaveForm.setLeaveTypeName(leaveType);
				}
				
				//set Leave Balence Table
				int year = Calendar.getInstance().get(Calendar.YEAR);
				String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"' and t.lv_calyear='"+year+"' and t.lv_typeid=m.lv_typeid";
				ResultSet rsBalance=ad.selectQuery(getleaveBalence);
				ArrayList balList=new ArrayList();
				while(rsBalance.next())
				{
				   leaveForm.setYear(rsBalance.getInt("lv_calyear"));
					LeaveForm form2=new LeaveForm();
					form2.setLeaveType(rsBalance.getString("lv_type"));
					form2.setOpeningBalence(rsBalance.getInt("lv_opbal"));
					form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
					float noDays=0;
					String lvqry = "select no_of_days from leave_details where Approvel_Status='Pending'";
					ResultSet lvqryRS=ad.selectQuery(lvqry);
					while(lvqryRS.next()){
						String nD = lvqryRS.getString("no_of_days");
						if(!nD.equalsIgnoreCase("null")){
						noDays = noDays+lvqryRS.getFloat("no_of_days");
						}
					}
					form2.setNoOfDays(""+noDays);
					form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
					balList.add(form2);
				}
				request.setAttribute("LeaveBalenceList", balList);
			}catch (Exception e) {
				e.printStackTrace();
			}
		} 
	public LinkedList getApprovers(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList approverList=new LinkedList();
		try{
		
		String approverName="";
		String approverID="";
		String reportingMgrID="";
		String reportingMgrName="";
		String appDesig="";
		String reportMgrDesg="";
		/*String getApprovers="select APPMGR,RPTMGR  from emp_official_info  emp where " +
				"PERNR='"+user.getEmployeeNo()+"'  ";	
		ResultSet rs=ad.selectQuery(getApprovers);
		while(rs.next())
		{
			approverID=rs.getString("APPMGR");
			reportingMgrID=rs.getString("RPTMGR");
		}*/
		boolean existStatus=false;
		String reqType="";
		String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
		ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
		while(rsCheckInEss.next())
		{
			int number=rsCheckInEss.getInt(1);
			reqType=rsCheckInEss.getString("reqType");
			if(number==0)
			{
				existStatus=false;
			}else{
				existStatus=true;
			}
		}
		if(existStatus==true){
			if(reqType.equalsIgnoreCase("sequential"))
			{
			String getApproverDesig="select e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
					"where employeeNumber='"+user.getEmployeeNo()+"' and e.essType='Leave' and   e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID order by Priority";
			ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				while(rsApproverDsg.next())
				{
					LeaveForm leaveForm1=new LeaveForm();
					leaveForm1.setApptype("Approving Manager");
					leaveForm1.setApproverID(rsApproverDsg.getString("ApproverId"));
					appDesig=rsApproverDsg.getString("DSGSTXT");
					approverName=rsApproverDsg.getString("EMP_FULLNAME");
					leaveForm1.setAppDesig(appDesig);
					leaveForm1.setApproverName(approverName);
					approverList.add(leaveForm1);
				}
			}
			if(reqType.equalsIgnoreCase("parallel"))
			{
				String approver1="";
				String approver2="";
				String approver3="";
			String getApproversList="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave'";
			ResultSet rsApprList=ad.selectQuery(getApproversList);
			while(rsApprList.next()){
				approver1=rsApprList.getString("ApproverId");
				approver2=rsApprList.getString("Parallel_Approver1");
				approver3=rsApprList.getString("Parallel_Approver2");
			}
			
			String getApproverDesig="select e.PERNR,e.EMP_FULLNAME,desg.DSGSTXT  from  emp_official_info as e,DESIGNATION as desg where  e.PERNR='"+approver1+"'   and e.DSGID=desg.DSGID";
			ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				while(rsApproverDsg.next())
				{
					LeaveForm leaveForm1=new LeaveForm();
					leaveForm1.setApptype("Approving Manager");
					leaveForm1.setApproverID(rsApproverDsg.getString("PERNR"));
					appDesig=rsApproverDsg.getString("DSGSTXT");
					approverName=rsApproverDsg.getString("EMP_FULLNAME");
					leaveForm1.setAppDesig(appDesig);
					leaveForm1.setApproverName(approverName);
					approverList.add(leaveForm1);
				}
				String getParallelApprover1="select e.PERNR,e.EMP_FULLNAME,desg.DSGSTXT  from  emp_official_info as e,DESIGNATION as desg where  e.PERNR='"+approver2+"'   and e.DSGID=desg.DSGID";
				rsApproverDsg=ad.selectQuery(getParallelApprover1);
			if(rsApproverDsg.next())
			{
				LeaveForm leaveForm1=new LeaveForm();
				leaveForm1.setApptype("Approving Manager");
				leaveForm1.setApproverID(rsApproverDsg.getString("PERNR"));
				appDesig=rsApproverDsg.getString("DSGSTXT");
				approverName=rsApproverDsg.getString("EMP_FULLNAME");
				leaveForm1.setAppDesig(appDesig);
				leaveForm1.setApproverName(approverName);
				approverList.add(leaveForm1);
			}
			String getParallelApprover2="select e.PERNR,e.EMP_FULLNAME,desg.DSGSTXT  from  emp_official_info as e,DESIGNATION as desg where  e.PERNR='"+approver3+"'   and e.DSGID=desg.DSGID";
	rsApproverDsg=ad.selectQuery(getParallelApprover2);
	if(rsApproverDsg.next())
	{
		LeaveForm leaveForm1=new LeaveForm();
		leaveForm1.setApptype("Approving Manager");
		leaveForm1.setApproverID(rsApproverDsg.getString("PERNR"));
		appDesig=rsApproverDsg.getString("DSGSTXT");
		approverName=rsApproverDsg.getString("EMP_FULLNAME");
		leaveForm1.setAppDesig(appDesig);
		leaveForm1.setApproverName(approverName);
		approverList.add(leaveForm1);
	}	
				
			}
		}
		if(existStatus==false){
		String getApproverDesig="select dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where " +
				" emp.PERNR='"+approverID+"' and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID";
		ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
		while(rsApproverDsg.next())
		{
			LeaveForm leaveForm1=new LeaveForm();
			leaveForm1.setApptype("Approving Manager");
			leaveForm1.setApproverID(approverID);
			appDesig=rsApproverDsg.getString("DSGSTXT");
			approverName=rsApproverDsg.getString("EMP_FULLNAME");
			leaveForm1.setAppDesig(appDesig);
			leaveForm1.setApproverName(approverName);
			approverList.add(leaveForm1);
		}
		}
		/*String getRepMgrDesig="select dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where " +
				" emp.PERNR='"+reportingMgrID+"' and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID";
		ResultSet rsRepMgrDesig=ad.selectQuery(getRepMgrDesig);
		while(rsRepMgrDesig.next())
		{
			LeaveForm leaveForm2=new LeaveForm();
			leaveForm2.setApptype("Reporting Manager");
			leaveForm2.setApproverID(reportingMgrID);
			reportMgrDesg=rsRepMgrDesig.getString("DSGSTXT");
			reportingMgrName=rsRepMgrDesig.getString("EMP_FULLNAME");
			leaveForm2.setAppDesig(reportMgrDesg);
			leaveForm2.setApproverName(reportingMgrName);
			approverList.add(leaveForm2);
		}*/
		
		/*leaveForm.setApproverID(approverID);
		leaveForm.setApproverName(approverName);
		leaveForm.setAppDesig(appDesig);
		leaveForm.setReportingMgrID(reportingMgrID);
		leaveForm.setReportingMgrName(reportingMgrName);
		leaveForm.setReportMgrDesg(reportMgrDesg);*/
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return approverList;
	}
	
	public ActionForward firstMyRequestRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		ArrayList all=(ArrayList)session.getAttribute("leaveMyRequest");
		
		ArrayList serviceMasterList=new ArrayList();
		
		Iterator it=all.iterator();
		
		int i=0;
		while(i<10){
			if(it.hasNext()){
			serviceMasterList.add(it.next());
			i++;
			}
			else
				break;
		}
		leaveForm.setStartRecord(1);
		leaveForm.setEndRecord(i++);
		leaveForm.setNext(i);
		if(i>9){
		request.setAttribute("nextButton", "yes");
		request.setAttribute("disablePreviousButton", "ok");
		}
		else{
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("disableNextButton","yes");
		}
		request.setAttribute("displayRecordNo","ok");
		
		
		//request.setAttribute("serviceMasterList", serviceDetails);
		
request.setAttribute("leaveEmpDetails", serviceMasterList);
		
		return mapping.findForward("display");
		//return mapping.findForward("displayList");
		
	}
	
	public ActionForward lastMyRequestRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		int prev=leaveForm.getPrev();
		int start=0;

		prev--;

		int i=0;
		ArrayList alldata=(ArrayList)session.getAttribute("leaveMyRequest");
		ArrayList serviceMasterList=new ArrayList();


		ListIterator it=alldata.listIterator();
		int l=0;
		int j=0;
		while(it.hasNext())
		{
			l++;
			it.next();
		}
		j=l;
		while(j>0)
		{
			it.previous();
			j--;
		}
		i=l-10;
		leaveForm.setStartRecord(i+1);
		leaveForm.setPrev(i);
		while(i<l){
			if(it.hasNext()&&i==start){
				serviceMasterList.add(it.next());

				i++;
			}
			else if(it.hasNext())
			it.next();
			else
				break;
			start++;
		}

			request.setAttribute("previousButton", "ok");
			request.setAttribute("disableNextButton","yes");
		//request.setAttribute("nextButton", "ok");


			leaveForm.setEndRecord(l);

		//sform.setNext(prev);
		request.setAttribute("displayRecordNo","ok");
request.setAttribute("leaveEmpDetails", serviceMasterList);
int year=0;
if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
{
	year=Calendar.getInstance().get(Calendar.YEAR);
}
else
{
	 year = Integer.parseInt(request.getParameter("year"));
}
leaveForm.setYear(year);
ArrayList yearList=new ArrayList();
	ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
		try {
			while(rs17.next()) {
				yearList.add(rs17.getString("lv_calyear"));
			}
			rs17.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  leaveForm.setYearList(yearList);

		return mapping.findForward("display");
	
	}
	
	public ActionForward previousMyRequestRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {


		
		
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		int prev=leaveForm.getPrev();
		int start=prev-10;
		System.out.println("ppppppppppppppppppppppppppppppppppp"+start);
		//start--;
		//prev--;
		if(start<0)
			start=0;
		leaveForm.setStartRecord(start);
		leaveForm.setEndRecord(prev);
		int i=0;
		ArrayList alldata=(ArrayList)session.getAttribute("leaveMyRequest");
		ArrayList serviceMasterList=new ArrayList();


		Iterator it=alldata.iterator();
		while(i<prev){
			if(it.hasNext()&&i==start){
				serviceMasterList.add(it.next());
				start++;
			}
			else
			it.next();
			i++;
		}
		if((prev-10)<=0){
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("nextButton", "ok");
	          }

		else{
			request.setAttribute("previousButton", "ok");
		request.setAttribute("nextButton", "ok");
		}
		leaveForm.setPrev(prev-10);
		leaveForm.setNext(prev);
		request.setAttribute("displayRecordNo","ok");
request.setAttribute("leaveEmpDetails", serviceMasterList);
start=leaveForm.getStartRecord();
if(start==0){
	start=1;
leaveForm.setStartRecord(start);
}
int year=0;
if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
{
	year=Calendar.getInstance().get(Calendar.YEAR);
}
else
{
	 year = Integer.parseInt(request.getParameter("year"));
}
leaveForm.setYear(year);
ArrayList yearList=new ArrayList();
	ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
		try {
			while(rs17.next()) {
				yearList.add(rs17.getString("lv_calyear"));
			}
			rs17.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  leaveForm.setYearList(yearList);
		return mapping.findForward("display");
 
	}
	
	public ActionForward nextMyRequestRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		
		LeaveForm leaveForm=(LeaveForm)form;
		int nextval=leaveForm.getEndRecord();
		leaveForm.setStartRecord(nextval+1);
		leaveForm.setPrev(nextval);
		int end=nextval+10;
		//sform.setEndRecord(end);
		int start=1;


		HttpSession session=request.getSession();
		ArrayList alldata=(ArrayList)session.getAttribute("leaveMyRequest");

		ArrayList serviceMasterList=new ArrayList();
		Iterator it=alldata.iterator();
		try{

		while(start<end){
			if(it.hasNext()&&start==nextval){
				serviceMasterList.add(it.next());
				nextval++;

			}
			else
			it.next();
			start++;
		}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		leaveForm.setEndRecord(nextval);
		if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
		request.setAttribute("previousButton", "ok");
		}
		else{
			request.setAttribute("disableNextButton","yes");
		request.setAttribute("previousButton", "ok");
		}
		request.setAttribute("displayRecordNo","ok");	

		leaveForm.setNext(nextval);
		request.setAttribute("leaveEmpDetails", serviceMasterList);
		int year=0;
		if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
		{
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		else
		{
			 year = Integer.parseInt(request.getParameter("year"));
		}
		leaveForm.setYear(year);
		ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("lv_calyear"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		  leaveForm.setYearList(yearList);

		return mapping.findForward("display");
 
	
	}
	
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		LeaveForm leaveForm=(LeaveForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ArrayList a1=new ArrayList();
		try{
			int totalRecords=leaveForm.getTotalRecords();//21
			int startRecord=leaveForm.getStartRecord();//11
			int endRecord=leaveForm.getEndRecord();	
			
			
			if(totalRecords>10){
			  startRecord=1;
			  endRecord=10;
			  leaveForm.setTotalRecords(totalRecords);
			  leaveForm.setStartRecord(startRecord);
			  leaveForm.setEndRecord(10);
			  }
			  else{
				  startRecord=1;
				  leaveForm.setTotalRecords(totalRecords);
				  leaveForm.setStartRecord(startRecord);
				  leaveForm.setEndRecord(totalRecords);  
			  }
			 String holidayType="";
				String LeaveType="";
				LeaveForm leaveForm1=null;
			 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,id,start_date,start_duration,end_date,no_of_days,leave_type " +
				"from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status='Draft') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
			ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					leaveForm1=new LeaveForm();
					
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
					holidayType=rs.getString("start_duration");
					if(holidayType.equalsIgnoreCase("FD")){
						holidayType="Full Day";
					}
					if(holidayType.equalsIgnoreCase("FH")){
						holidayType="First Half";
					}
					if(holidayType.equalsIgnoreCase("SH")){
						holidayType="Second Half";
					}
					leaveForm1.setHolidayType(holidayType);
					leaveForm1.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
					leaveForm1.setNoOfDays(rs.getString("no_of_days"));
					LeaveType=getLeaveType(rs.getString("leave_type"));
					
					leaveForm1.setLeaveType(LeaveType);
					a1.add(leaveForm1); 
				}
				request.setAttribute("leaveDraftDetails", a1);
				 if(totalRecords>10)
					{
						request.setAttribute("nextButton", "nextButton");
					}
				
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
					
					request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		LeaveForm leaveForm=(LeaveForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ArrayList a1=new ArrayList();
		try{
			int totalRecords=leaveForm.getTotalRecords();//21
			int startRecord=leaveForm.getStartRecord();//11
			int endRecord=leaveForm.getEndRecord();	
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 leaveForm.setTotalRecords(totalRecords);
			 leaveForm.setStartRecord(startRecord);
			 leaveForm.setEndRecord(totalRecords);
			 String holidayType="";
				String LeaveType="";
				LeaveForm leaveForm1=null;
			 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,id,start_date,start_duration,end_date,no_of_days,leave_type " +
				"from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status='Draft') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
			ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					leaveForm1=new LeaveForm();
					
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
					holidayType=rs.getString("start_duration");
					if(holidayType.equalsIgnoreCase("FD")){
						holidayType="Full Day";
					}
					if(holidayType.equalsIgnoreCase("FH")){
						holidayType="First Half";
					}
					if(holidayType.equalsIgnoreCase("SH")){
						holidayType="Second Half";
					}
					leaveForm1.setHolidayType(holidayType);
					leaveForm1.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
					leaveForm1.setNoOfDays(rs.getString("no_of_days"));
					LeaveType=getLeaveType(rs.getString("leave_type"));
					leaveForm1.setLeaveType(LeaveType);
					a1.add(leaveForm1); 
				}
				request.setAttribute("leaveDraftDetails", a1);
				 request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton");
					if(a1.size()<10)
					{
						
						request.setAttribute("previousButton", "");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
					request.setAttribute("displayRecordNo", "displayRecordNo");
			 
	}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		LeaveForm leaveForm=(LeaveForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ArrayList a1=new ArrayList();
	try{
		int totalRecords=leaveForm.getTotalRecords();//21
		int endRecord=leaveForm.getStartRecord()-1;//20
		int startRecord=leaveForm.getStartRecord()-10;//11
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		leaveForm.setTotalRecords(totalRecords);
		leaveForm.setStartRecord(1);
		leaveForm.setEndRecord(10);
		String holidayType="";
		String LeaveType="";
		LeaveForm leaveForm1=null;
		String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,id,start_date,start_duration,end_date,no_of_days,leave_type " +
		"from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status='Draft') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
	ResultSet rs=ad.selectQuery(sql);
		while (rs.next()) {
			leaveForm1=new LeaveForm();
			
			leaveForm1.setId(rs.getString("id"));
			leaveForm1.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
			holidayType=rs.getString("start_duration");
			if(holidayType.equalsIgnoreCase("FD")){
				holidayType="Full Day";
			}
			if(holidayType.equalsIgnoreCase("FH")){
				holidayType="First Half";
			}
			if(holidayType.equalsIgnoreCase("SH")){
				holidayType="Second Half";
			}
			leaveForm1.setHolidayType(holidayType);
			leaveForm1.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
			leaveForm1.setNoOfDays(rs.getString("no_of_days"));
			LeaveType=getLeaveType(rs.getString("leave_type"));
			leaveForm1.setLeaveType(LeaveType);
			a1.add(leaveForm1);
		}
		request.setAttribute("leaveDraftDetails", a1);
		leaveForm.setTotalRecords(totalRecords);
		leaveForm.setStartRecord(startRecord);
		leaveForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(a1.size()<10)
				{
					leaveForm.setStartRecord(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
	}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			int totalRecords=leaveForm.getTotalRecords();//21
			int startRecord=leaveForm.getStartRecord();//11
			int endRecord=leaveForm.getEndRecord();
			ArrayList a1=new ArrayList();
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
				String holidayType="";
				String LeaveType="";
				LeaveForm leaveForm1=null;
				String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,id,start_date,start_duration,end_date,no_of_days,leave_type " +
				"from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status='Draft') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
			ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					leaveForm1=new LeaveForm();
					
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
					holidayType=rs.getString("start_duration");
					if(holidayType.equalsIgnoreCase("FD")){
						holidayType="Full Day";
					}
					if(holidayType.equalsIgnoreCase("FH")){
						holidayType="First Half";
					}
					if(holidayType.equalsIgnoreCase("SH")){
						holidayType="Second Half";
					}
					leaveForm1.setHolidayType(holidayType);
					leaveForm1.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
					leaveForm1.setNoOfDays(rs.getString("no_of_days"));
					LeaveType=getLeaveType(rs.getString("leave_type"));
					leaveForm1.setLeaveType(LeaveType);
					a1.add(leaveForm1);
				}
				request.setAttribute("leaveDraftDetails", a1);
				
				
			}
			
			System.out.println("list length="+a1.size());
			
			 if(a1.size()!=0)
				{
				 leaveForm.setTotalRecords(totalRecords);
				 leaveForm.setStartRecord(startRecord);
				 leaveForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					leaveForm.setTotalRecords(totalRecords);
					leaveForm.setStartRecord(start);
					leaveForm.setEndRecord(end);
					
				}
			 if(a1.size()<10)
			 {
				 leaveForm.setTotalRecords(totalRecords);
				 leaveForm.setStartRecord(startRecord);
				 leaveForm.setEndRecord(startRecord+a1.size()-1);
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
		return mapping.findForward("draftRecords");
	}
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String sql="select * from links where id in("+user.getIncludeSubLinks()+")";
		ResultSet rs=ad.selectQuery(sql);
		try
		{
				 LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
				 while(rs.next())
				 {
				 String sql1="select * from links where module='"+id+"' " +
				 		"and id ='"+rs.getString("id")+"'";
				 ResultSet rs1=ad.selectQuery(sql1);
				 if(rs1.next()){
				 hm.put(rs1.getString("link_path")+"?method="+rs1.getString("method")+"&sId="+rs1.getString("link_name")+"&id="+rs1.getString("module"), rs1.getString("link_name"));
				 }
				 leaveForm.setContentDescription("  ");
			     }
			session.setAttribute("SUBLINKS", hm);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		ArrayList list=new ArrayList();
		String sql2="select ld.*,u.firstname from leave_details as ld,users as u" +
		" where u.id='"+user.getEmployeeNo()+"' and ld.user_id=u.id and record_status='Applied' order by id desc";
		ResultSet rs2=ad.selectQuery(sql2);
		try{
			LinkedList a1=new LinkedList();
			while(rs2.next()){
				String leaveType="";
				String holidayType="";
				String status="";
				leaveType=getLeaveType(rs2.getString("leave_type"));
				
				if(rs2.getString("leave_status").equalsIgnoreCase("0")){
					status="Pending";
				}
				if(rs2.getString("leave_status").equalsIgnoreCase("1")){
					status="Approved";
				}
				if(rs2.getString("leave_status").equalsIgnoreCase("2")){
					status="Rejected";
				}
				holidayType = getHolidayType(rs2.getString("start_duration"));
				
				 Leave l = new Leave(EMicroUtils.display1(rs2.getDate("start_date")),EMicroUtils.display1(rs2.getDate("end_date")),
							rs2.getString("no_of_days"),leaveType,rs2.getString("firstname"),
							status,rs2.getString("id"),rs2.getString("submit_date"),rs2.getString("record_status"),holidayType,rs2.getString("reason"),rs2.getString("documents"));
					a1.add(l);
			}
			request.setAttribute("leaveDetails", a1);
			if(a1.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				leaveForm.setMessage("No records are found");
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		request.setAttribute("listName", list);
		return mapping.findForward("display");
	}
	public ActionForward submitRequests(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		
		String param=request.getParameter("param");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String selected="";
		int userId=Integer.parseInt(user.getEmployeeNo());
		String[] selectedValue=request.getParameterValues("listid");
		try
		{
		for(int i=0;i<selectedValue.length;i++)
		{
		System.out.println("SELECTED***======"+selectedValue[i]);
		
		
		String getTotalPriority="select Approver_id from Approvers_Details where Priority in (select max(Priority) from Approvers_Details) and type='Leave'";
		int highest_approve_id=0;
		ResultSet rsTotalPriority=ad.selectQuery(getTotalPriority);
		while(rsTotalPriority.next())
		{
			highest_approve_id=rsTotalPriority.getInt("Approver_id");
		}
		int approvID=0;
		String getReqValues="select * from leave_details where id='"+selectedValue[i]+"' and Approvel_Status='3'";
		ResultSet rsReqValues=ad.selectQuery(getReqValues);
		while(rsReqValues.next())
		{
			approvID=rsReqValues.getInt("Approver_id");
		}
		
		if(highest_approve_id==approvID)
		{
			if(param.equalsIgnoreCase("approve"))
			{
				String updateApproveStats="update leave_details set Approver_id='0',leave_status='1',Approvel_Status='0',Forwarded_emp_id='0',Last_approver='"+user.getFullName()+"',Pending_approver='0' where id='"+selectedValue[i]+"'";
				int j=0;
				j=ad.SqlExecuteUpdate(updateApproveStats);
				if(j>0)
				{
					leaveForm.setMessage("Selected Leaves are Approved Successfully");
					
					String insertApproverDetails="insert into Leave_Sub_Table(Leave_ReqId,Approver_id,Status) values('"+selectedValue[i]+"','"+userId+"','1')";
					int k=0;
					k=ad.SqlExecuteUpdate(insertApproverDetails);
				}
				else
					leaveForm.setMessage("Error...while updating leave details.");
			}else{
				//reject leave
				String updateApproveStats="update leave_details set leave_status='2',Approvel_Status='2',Last_approver='"+user.getFullName()+"',Pending_approver='0' where id='"+selectedValue[i]+"'";
				int j=0;
				j=ad.SqlExecuteUpdate(updateApproveStats);
				if(j>0)
				{
					leaveForm.setMessage("Selected Leaves are Rejected Successfully");
				}
				else
					leaveForm.setMessage("Error...while updating leave details.");
				
			}
		}
		else{
		if(userId==highest_approve_id)
		{
			//last approver
			if(param.equalsIgnoreCase("approve"))
			{
			String updateApproveStats="update leave_details set Approver_id='"+user.getEmployeeNo()+"',leave_status='1',Approvel_Status='1',Last_approver='"+user.getFullName()+"',Pending_approver='0' where id='"+selectedValue[i]+"'";
			int j=0;
			j=ad.SqlExecuteUpdate(updateApproveStats);
			if(j>0)
			{
				leaveForm.setMessage("Selected Leaves are Approved Successfully");
				
				String insertApproverDetails="insert into Leave_Sub_Table(Leave_ReqId,Approver_id,Status) values('"+selectedValue[i]+"','"+userId+"','1')";
				int k=0;
				k=ad.SqlExecuteUpdate(insertApproverDetails);
			}
			else
				leaveForm.setMessage("Error...while updating leave details.");
			}else{
				//reject
				
				String updateApproveStats="update leave_details set leave_status='2',Approvel_Status='2',Last_approver='"+user.getFullName()+"',Pending_approver='0' where id='"+selectedValue[i]+"'";
				int j=0;
				j=ad.SqlExecuteUpdate(updateApproveStats);
				if(j>0)
				{
					leaveForm.setMessage("Selected Leaves are Rejected Successfully");
				}
				else
					leaveForm.setMessage("Error...while updating leave details.");
				
			}
			
			
		}else{
			int next_appoverID=0;
			int next_priority=0;
			
			if(param.equalsIgnoreCase("approve"))
			{
				//leave Approved
				int ch=0;
				String pendingApprover="";
				int approverPriority=0;
				String app_check="select * from Approvers_Details where Approver_id='"+userId+"' and type='Leave'";
				ResultSet app_rs=ad.selectQuery(app_check);
				if(app_rs.next()){
					ch=1;
					approverPriority=app_rs.getInt("Priority");
				}
				
				String getPendingApprovers="select a.Approver_id,u.fullname from Approvers_Details as a,users as u where a.Priority>'"+approverPriority+"' and a.Approver_id=u.id  and a.type='Leave'";
				ResultSet rsPeningApprover=ad.selectQuery(getPendingApprovers);
				while(rsPeningApprover.next())
				{
					pendingApprover=pendingApprover+","+rsPeningApprover.getString("fullname");
				}
				
				pendingApprover=pendingApprover.substring(1, pendingApprover.length());
				
				if(ch==1){
			String getNextApprover="select * from  Approvers_Details where Priority in (( select Priority from Approvers_Details where Approver_id='"+userId+"' and type='Leave')+1)";
			ResultSet rsNextApprover=ad.selectQuery(getNextApprover);
			while(rsNextApprover.next())
			{
				next_appoverID=rsNextApprover.getInt(2);
			}
			
			
				String updateApproveStats="update leave_details set Approver_id='"+next_appoverID+"',Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprover+"' where id='"+selectedValue[i]+"'";
				int j=0;
				j=ad.SqlExecuteUpdate(updateApproveStats);
				if(j>0)
				{
					leaveForm.setMessage("Selected Leaves are Approved Successfully");
					
					String insertApproverDetails="insert into Leave_Sub_Table(Leave_ReqId,Approver_id,Status) values('"+selectedValue[i]+"','"+userId+"','1')";
					int k=0;
					k=ad.SqlExecuteUpdate(insertApproverDetails);
				}
				}
				else if(ch==0){
					String pendingApprovers="";
					String get_prev_approver="select * from leave_details where id='"+selectedValue[i]+"'";
					ResultSet rs=ad.selectQuery(get_prev_approver);
					int app_id=0;
					if(rs.next()){
					 app_id=rs.getInt("approver_id");
					 pendingApprovers=rs.getString("Pending_approver");
					 
					}
					pendingApprovers=pendingApprovers.replace(user.getFullName(),"");
					pendingApprovers=pendingApprovers.substring(1,pendingApprovers.length());
					String update_sub="insert into Leave_sub_Table(Leave_ReqId,Approver_id,Status) values('"+selectedValue[i]+"','"+userId+"','1')";
					int kk=0;
					kk=ad.SqlExecuteUpdate(update_sub);
					
					String getnext_approver="select * from  Approvers_Details where Priority in (( select Priority from Approvers_Details where Approver_id='"+app_id+"' and type='Leave')+1)";
					
					ResultSet rsNextApprover=ad.selectQuery(getnext_approver);
					while(rsNextApprover.next())
					{
						next_appoverID=rsNextApprover.getInt(2);
					}
					String update_leave_table="update leave_details set Approver_id='"+next_appoverID+"',approver_status='0',Forwarded_emp_id='0',Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"' where id='"+selectedValue[i]+"'";
					ad.SqlExecuteUpdate(update_leave_table);
					leaveForm.setMessage("Selected Leaves are Approved Successfully");
					
					
				}
				else
					leaveForm.setMessage("Error...while updating leave details.");
				
			}else{
				//leave Rejected
				String updateApproveStats="update leave_details set leave_status='2',Approvel_Status='2',Last_approver='"+user.getFullName()+"' where id='"+selectedValue[i]+"'";
				int j=0;
				j=ad.SqlExecuteUpdate(updateApproveStats);
				if(j>0)
				{
					leaveForm.setMessage("Selected Leaves are Rejected Successfully");
				}
				else
					leaveForm.setMessage("Error...while updating leave details.");
				
			}
	
			}
		}
		}
		String leaveType="";
		String holidayType="";
		String status="";
		displayRequests(mapping, form, request, response);
		String userName="";
		userName=user.getFirstName();
		/*ArrayList a1=new ArrayList();
		String getLeaveRecords="select l.leave_type,l.holiday_type,l.start_date,l.end_date,l.no_of_days,l.id,l.submit_date,s.Status,l.reason,l.documents,u.fullname  from  leave_details as l,Leave_Sub_Table as s,users as u where l.id=s.Leave_ReqId and s.Approver_id='"+userId+"' and u.id=l.user_id";
		ResultSet rsLeaveRecords=ad.selectQuery(getLeaveRecords);
		while(rsLeaveRecords.next())
		{
			if(rsLeaveRecords.getString("leave_type").equalsIgnoreCase("CL"))leaveType="Casual Leave";
			else if(rsLeaveRecords.getString("leave_type").equalsIgnoreCase("SL"))leaveType="Sick Leave";
			else if(rsLeaveRecords.getString("leave_type").equalsIgnoreCase("EL"))leaveType="Earned Leave";
			else if(rsLeaveRecords.getString("leave_type").equalsIgnoreCase("LOP"))leaveType="Loss of Pay";
			else if(rsLeaveRecords.getString("leave_type").equalsIgnoreCase("AL"))leaveType="Advance Leave";
			else if(rsLeaveRecords.getString("leave_type").equalsIgnoreCase("ML"))leaveType="Maternity Leave";
			
			if(rsLeaveRecords.getString("holiday_type").equalsIgnoreCase("FD"))holidayType="Full Day";
			else if(rsLeaveRecords.getString("holiday_type").equalsIgnoreCase("FH"))holidayType="First Half";
			else if(rsLeaveRecords.getString("holiday_type").equalsIgnoreCase("SH"))holidayType="Second Half";
			
			if(rsLeaveRecords.getString("Status").equalsIgnoreCase("0"))
			status="Pending";
			else
				status="Approved";
			
			 Leave l = new Leave(EMicroUtils.display(rsLeaveRecords.getDate("start_date")),EMicroUtils.display(rsLeaveRecords.getDate("end_date")),
					 rsLeaveRecords.getString("no_of_days"),leaveType,rsLeaveRecords.getString("fullname"),
						status,rsLeaveRecords.getString("id"),rsLeaveRecords.getString("submit_date"),rsLeaveRecords.getString("Status"),holidayType,rsLeaveRecords.getString("reason"),rsLeaveRecords.getString("documents"));
			 
			 a1.add(l);
		}
		
		request.setAttribute("leaveEmpDetails", a1);*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward("display");
	}
	
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
			
			FormFile documentFile=leaveForm.getDocumentFile();
			String documentName=documentFile.getFileName();
			String leaveType=leaveForm.getLeaveType();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			float clMaxDays=leaveForm.getClMaxDays();
			leaveForm.setClMaxDays(clMaxDays);
			float slMaxDays=leaveForm.getSlMaxDays();
			leaveForm.setSlMaxDays(slMaxDays);
			String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
			int filesize=documentFile.getFileSize();
			
			if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
			{
			try{
			 byte[] size=documentFile.getFileData();
			 if(!documentName.equalsIgnoreCase("")){
			 int length=documentName.length();
		     int dot=documentName.lastIndexOf(".");
		     String extension=documentName.substring(dot,length);
		     
		     
				
		     
	 	     String filepath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/ESS/Leave");
	 	     
	 	    InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 	 Properties props = new Properties();
	 	 	props.load(in);
	 		in.close();
	 	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	 	filepath=uploadFilePath+"/EMicro Files/ESS/Leave";
	 	 	 
	 	    File destinationDir = new File(filepath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			filepath = filepath+"/"+documentFile.getFileName();
			 File imageFile=new File(filepath);
			 FileOutputStream outputStream=new FileOutputStream(imageFile);
			 outputStream.write(size);
			 outputStream.flush();
			 outputStream.close();
			 request.setAttribute("submitDetails", "submitDetails");
			 setLeaveType(mapping, form, request, response);
				//upload files in another path
				try{
					String filePath1 = "E:/EMicro Files/ESS/Leave";
					byte[] fileData1 = documentFile.getFileData();
					File destinationDir1 = new File(filePath1);
				if(!destinationDir1.exists())
				{
					destinationDir1.mkdirs();
				}
				filePath1 = filePath1+"/"+documentFile.getFileName();
				 File imageFile1=new File(filePath1);
				 FileOutputStream outputStream1=new FileOutputStream(imageFile1);
				 outputStream1.write(size);
				 outputStream1.flush();
				 outputStream1.close();
				 
				}catch (Exception e) {
					e.printStackTrace();
				}
				
		
			 LinkedList appList = getApprovers(request, response);
				request.setAttribute("appList", appList);
			 }
			
			 }catch(FileNotFoundException fe){
				fe.printStackTrace();
			}catch(IOException ie){
				ie.printStackTrace();
			}
			 
	          String contentType =documentFile.getContentType();
			  String fileName   =  documentFile.getFileName();
			
		
			try{
				
				String sql9="select count(*) from temp_emp_leave_documents  where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and file_name='"+documentFile.getFileName()+"'";
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount=0;
				while (rs15.next())
				{
					fileCount=Integer.parseInt(rs15.getString(1));
				}
				if(fileCount>0)
				{
					leaveForm.setMessage("Document aleardy uploaded..please choose another file");
				}
				else
				{
					String sql="insert into temp_emp_leave_documents(user_id,file_name,leave_type,req_no)" +
					"values('"+user.getEmployeeNo()+"','"+documentFile.getFileName()+"','"+leaveType+"','"+leaveForm.getRequestNumber()+"')";
					int a=ad.SqlExecuteUpdate(sql);
					if(a>0)
						{
						leaveForm.setMessage("Documents Uploaded Successfully");
						
						}
				}
				
				String sql1="select * from temp_emp_leave_documents where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and req_no='"+leaveForm.getRequestNumber()+"'";
				ResultSet rs=ad.selectQuery(sql1);
					LeaveForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs.next()) {
						leaveForm1=new LeaveForm();
						leaveForm1.setFileName(rs.getString("file_name"));
					
						String filePath="jsp/EMicro Files/ESS/Leave"+"/"+rs.getString("file_name");
						leaveForm1.setFilePath(filePath);
						leaveForm1.setId(rs.getString("id"));
						a1.add(leaveForm1);
					}
					request.setAttribute("documentDetails", a1);
				
			
			

			String content_description = leaveForm.getReason();
			leaveForm.setReason(content_description);
			int requestNo=leaveForm.getRequestNumber();
			leaveForm.setRequestNumber(requestNo);
			
			
			float a =leaveForm.getCasleavadv();
			leaveForm.setCasleavadv(a);
			
			float b =leaveForm.getPreleavadv();
			leaveForm.setPreleavadv(b);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			 }else{
				 leaveForm.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
				 request.setAttribute("submitDetails", "submitDetails");
				 setLeaveType(mapping, form, request, response);
				 LinkedList appList = getApprovers(request, response);
					request.setAttribute("appList", appList);
					String sql1="select * from temp_emp_leave_documents where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and req_no='"+leaveForm.getRequestNumber()+"'";
					ResultSet rs=ad.selectQuery(sql1);
						LeaveForm leaveForm1=null;
						ArrayList a1=new ArrayList();
						try {
							while(rs.next()) {
								leaveForm1=new LeaveForm();
								leaveForm1.setFileName(rs.getString("file_name"));
							
								String filePath="jsp/EMicro Files/ESS/Leave"+"/"+rs.getString("file_name");
								leaveForm1.setFilePath(filePath);
								leaveForm1.setId(rs.getString("id"));
								a1.add(leaveForm1);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						request.setAttribute("documentDetails", a1);
						}
			
			LinkedList leaveIDReason=new LinkedList();
			LinkedList leaveDetReason=new LinkedList();
			
			String reason="select distinct(Reason),Detailed_Reason from LEAVE_REASON";
			ResultSet a=ad.selectQuery(reason);
			try {
				while(a.next())
				{
			leaveIDReason.add(a.getString("Reason"));
			leaveDetReason.add(a.getString("Detailed_Reason"));
				}
				leaveForm.setLeaveReason(leaveIDReason);
				leaveForm.setLeaveDetReason(leaveDetReason);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("lv_calyear"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					leaveForm.setYearList(yearList);
			
			return mapping.findForward("newLeaveForm");
			}
	public ActionForward uploadDocumentsModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
			
			FormFile documentFile=leaveForm.getDocumentFile();
			String documentName=documentFile.getFileName();
			String leaveType=leaveForm.getLeaveType();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			float clMaxDays=leaveForm.getClMaxDays();
			leaveForm.setClMaxDays(clMaxDays);

			setLeaveType(mapping, form, request, response);
					LinkedList appList = getApprovers(request, response);
					request.setAttribute("appList", appList);
			String Id=leaveForm.getId();
			String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
			int filesize=documentFile.getFileSize();
			
			if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
			{
			try{
			 byte[] size=documentFile.getFileData();
			 if(!documentName.equalsIgnoreCase("")){
			 int length=documentName.length();
		     int dot=documentName.lastIndexOf(".");
		     String extension=documentName.substring(dot,length);
		     String filepath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Leave");
		     InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 	 Properties props = new Properties();
	 	 	props.load(in);
	 		in.close();
	 	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	 	filepath=uploadFilePath+"/EMicro Files/ESS/Leave";
	 	 	
	 	 	
	 	    File destinationDir = new File(filepath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
				filepath = filepath+"/"+documentFile.getFileName();
			 File imageFile=new File(filepath);
			 FileOutputStream outputStream=new FileOutputStream(imageFile);
			 outputStream.write(size);
			 outputStream.flush();
			 outputStream.close();
			
				 
				 
			 request.setAttribute("submitDetails", "submitDetails");
			 
				//upload files in another path
				
				try{
					String filePath1 = "E:/EMicro Files/ESS/Leave";
					byte[] fileData1 = documentFile.getFileData();
					
					File destinationDir1 = new File(filePath1);
				if(!destinationDir1.exists())
				{
					destinationDir1.mkdirs();
				}
				filePath1 = filePath1+"/"+documentFile.getFileName();
				 File imageFile1=new File(filePath1);
				 FileOutputStream outputStream1=new FileOutputStream(imageFile1);
				 outputStream1.write(size);
				 outputStream1.flush();
				 outputStream1.close();
				 
				}catch (Exception e) {
					e.printStackTrace();
				}
			 }
			 }catch(FileNotFoundException fe){
				fe.printStackTrace();
			}catch(IOException ie){
				ie.printStackTrace();
			}
			  String contentType =documentFile.getContentType();
			  String fileName   =  documentFile.getFileName();
			
			
			try{
				
				String sql9="select * from leave_details where id='"+Id + "'";
				ResultSet rs9=ad.selectQuery(sql9);
				while (rs9.next()) 
				{
				 leaveForm.setReason(rs9.getString("reason"));	
				}
				String fileNameList=documentFile.getFileName();
				String sqlselect="select * from leave_details where id='"+Id + "' and documents not like ''";
				ResultSet rs15 = ad.selectQuery(sqlselect);
				String FileList="";
				while (rs15.next())
				{
					FileList=rs15.getString("documents");
				}
				boolean b;
			    b = FileList.contains(fileNameList);
			    if(b)
			    {
			    	leaveForm.setMessage("This file  is  already uploaded..please choose another file");	
			    }
			    else
			    {
			    	String fileNames=null;
			    	if(FileList.equalsIgnoreCase(""))
			    	{
			    		fileNames=fileNameList;
			    	}
			    	else
			    	{
			    		fileNames=FileList+','+fileNameList;
			    	}
					String updatesql="update leave_details set documents='"+fileNames+"' where id='"+Id + "'" ;
					int a = ad.SqlExecuteUpdate(updatesql);
					if (a > 0)
					{
						leaveForm.setMessage("Documents Uploaded Successfully");
					} else
					{
						leaveForm.setMessage("Error While Uploading Files ... Please check Entered Values");
					}	
			    }
			    String sql1="select * from leave_details where id='"+Id + "' and documents not like ''";
				ResultSet rs11=ad.selectQuery(sql1);
					LeaveForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs11.next()) {
						leaveForm1=new LeaveForm();
						leaveForm1.setDocumentName(rs11.getString("documents"));
						//String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Leave//")+rs11.getString("documents");
						String filePath="jsp/EMicro Files/ESS/Leave"+"/"+rs11.getString("documents");
						leaveForm1.setFilePath(filePath);
						leaveForm1.setId(rs11.getString("id"));
						a1.add(leaveForm1);
					}
					request.setAttribute("documentDetails", a1);
					//set content

				String content_description=leaveForm.getReason();
				leaveForm.setReason(content_description);
				float a =leaveForm.getCasleavadv();
				leaveForm.setCasleavadv(a);
				
				float c =leaveForm.getPreleavadv();
				leaveForm.setPreleavadv(c);
				
				
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			}else{
				 leaveForm.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
				 request.setAttribute("submitDetails", "submitDetails");
				 setLeaveType(mapping, form, request, response);
			      appList = getApprovers(request, response);
					request.setAttribute("appList", appList);
					String sql1="select * from leave_details where id='"+Id + "' and documents not like ''";
					ResultSet rs11=ad.selectQuery(sql1);
						LeaveForm leaveForm1=null;
						ArrayList a1=new ArrayList();
						try {
							while(rs11.next()) {
								leaveForm1=new LeaveForm();
								try {
									leaveForm1.setDocumentName(rs11.getString("documents"));
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Leave//")+rs11.getString("documents");
								String filePath="jsp/EMicro Files/ESS/Leave"+"/"+rs11.getString("documents");
								leaveForm1.setFilePath(filePath);
								leaveForm1.setId(rs11.getString("id"));
								a1.add(leaveForm1);
							}
							request.setAttribute("documentDetails", a1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
   						}
			
			ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("lv_calyear"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					leaveForm.setYearList(yearList);
			
			return mapping.findForward("displayDraft");
			}
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		String[] documentCheck=leaveForm.getDocumentCheck();
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		setLeaveType(mapping, form, request, response);
		float clMaxDays=leaveForm.getClMaxDays();
		leaveForm.setClMaxDays(clMaxDays);
		float slMaxDays=leaveForm.getSlMaxDays();
		leaveForm.setSlMaxDays(slMaxDays);
		LinkedList appList = getApprovers(request, response);
		request.setAttribute("appList", appList);
		 int documentLength=0;
		 try 
		 {
			 documentLength=documentCheck.length;
		 }catch(Exception e){
			
		 }
			int document=0;
			String documentId="";
			String documentName="";
			String sql="";
			System.out.println("rejectLength Is ********************"+documentLength);
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					
					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 	 Properties props = new Properties();
			 	 	props.load(in);
			 		in.close();
			 	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			 	 String	filepath=uploadFilePath+"/EMicro Files/ESS/Leave";
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						  String fileName="";
						 String getFileName="select * from temp_emp_leave_documents where id="+documentId+"";
    					 ResultSet rsFileName=ad.selectQuery(getFileName);
    					 while(rsFileName.next())
    					 {
    						 fileName=rsFileName.getString("file_name");
    					 }
	        				sql="delete from temp_emp_leave_documents " +
        			 		" where id="+documentId+" and req_no='"+leaveForm.getRequestNumber()+"' ";
	        				System.out.println("Getting a sql is *************"+sql);
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					leaveForm.setMessage("Document Details Deleted sucessfully");
	        					
	        					 File fileToCreate = new File(filepath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/Leave", fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from temp_emp_leave_documents where user_id='"+user.getEmployeeNo()+"' and  req_no='"+leaveForm.getRequestNumber()+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			
			try{
				LeaveForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new LeaveForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					
					String filePath="jsp/EMicro Files/ESS/Leave/"+"/"+rs.getString("file_name");
					
					leaveForm1.setFilePath(filePath);
					leaveForm1.setId(rs.getString("id"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);

			String content_description=leaveForm.getReason();
			leaveForm.setReason(content_description);
			int requestNo=leaveForm.getRequestNumber();
			leaveForm.setRequestNumber(requestNo);
			
			float a =leaveForm.getCasleavadv();
			leaveForm.setCasleavadv(a);
			
			float b =leaveForm.getPreleavadv();
			leaveForm.setPreleavadv(b);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			LinkedList leaveIDReason=new LinkedList();
			LinkedList leaveDetReason=new LinkedList();
			
			String reason="select distinct(Reason),Detailed_Reason from LEAVE_REASON";
			ResultSet a=ad.selectQuery(reason);
			try {
				while(a.next())
				{
			leaveIDReason.add(a.getString("Reason"));
			leaveDetReason.add(a.getString("Detailed_Reason"));
				}
				leaveForm.setLeaveReason(leaveIDReason);
				leaveForm.setLeaveDetReason(leaveDetReason);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("lv_calyear"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					leaveForm.setYearList(yearList);
			
		return mapping.findForward("newLeaveForm");
	}
	public ActionForward deleteDocumentsModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			LeaveForm leaveForm=(LeaveForm)form;
			HttpSession session=request.getSession();
			
			String Id=leaveForm.getId();
			String uncheckValues=request.getParameter("unValues");
			String checkValues=request.getParameter("cValues");
			UserInfo user=(UserInfo)session.getAttribute("user");
			float clMaxDays=leaveForm.getClMaxDays();
			leaveForm.setClMaxDays(clMaxDays);
			setLeaveType(mapping, form, request, response);
					LinkedList appList = getApprovers(request, response);
					request.setAttribute("appList", appList);
			try 
			{
				String sql9="select * from leave_details where id='"+Id + "'";
				ResultSet rs9=ad.selectQuery(sql9);
				while (rs9.next()) 
				{
				 leaveForm.setReason(rs9.getString("reason"));	
				}
				uncheckValues = uncheckValues.substring(0, uncheckValues.length());
				checkValues = checkValues.substring(0, checkValues.length());
				String v[] = uncheckValues.split(",");
				String upFileName=null;
				String upFileName2="";
				for(int i=0;i<v.length;i++)
				{
				upFileName=v[i];
			    upFileName2+=upFileName+",";
				}
				String up2="";
				upFileName2 = upFileName2.substring(0, upFileName2.length()-1);
				String updatesql=null;
				if(upFileName2.equalsIgnoreCase(up2))
				{
					updatesql="update leave_details set documents=''where id='"+Id + "'";
				}
				else
				{
					 updatesql="update leave_details set documents='"+upFileName2+"' where id='"+Id + "'" ;
				}
				int a = ad.SqlExecuteUpdate(updatesql);
				if (a > 0) {
					leaveForm.setMessage("Documents deleted Successfully");
				}
				else
				{
					leaveForm.setMessage("Error while deleting documents");
				}
				 request.setAttribute("submitDetails", "submitDetails");
				String sql1="select * from leave_details where id='"+Id + "' and documents not like ''";
				ResultSet rs11=ad.selectQuery(sql1);
				LeaveForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs11.next())
				{
						leaveForm1=new LeaveForm();
						leaveForm1.setDocumentName(rs11.getString("documents"));
						leaveForm1.setId(rs11.getString("id"));
					
						String filePath="jsp/EMicro Files/ESS/Leave/"+rs11.getString("documents");
						leaveForm1.setFilePath(filePath);
						a1.add(leaveForm1);
				}
					request.setAttribute("documentDetails", a1);
			//set content
					
					 Enumeration<String> params = request
						.getParameterNames();
				String parameter;
				String content_description =leaveForm.getReason();
				leaveForm.setReason(content_description);
				float a11 =leaveForm.getCasleavadv();
				leaveForm.setCasleavadv(a11);
				
				float b =leaveForm.getPreleavadv();
				leaveForm.setPreleavadv(b);
		 }
			
			
				catch(Exception e)
				{
				e.printStackTrace();
			}
			ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("lv_calyear"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					leaveForm.setYearList(yearList);	
			
		return mapping.findForward("displayDraft");
	}
	public ActionForward selectContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
{
		LeaveForm leaveForm=(LeaveForm)form;// TODO Auto-generated method stub
		System.out.println("********SELECT METHOD========");
		String Id=request.getParameter("id");
		leaveForm.setId(Id);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		setLeaveType(mapping, form, request, response);
		LinkedList appList = getApprovers(request, response);
		request.setAttribute("appList", appList);
		
		
		LinkedList leaveTypeID=new LinkedList();
		LinkedList leaveType11=new LinkedList();
		try 
		{
			String applyafterdate="";
			String rule="select * from LEAVE_APPLY_RULE";
			ResultSet bbc=ad.selectQuery(rule);
			if(bbc.next())
			{
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -bbc.getInt("days"));
				Date date = cal.getTime();             
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				applyafterdate = format1.format(date);            
				
			}
			
			leaveForm.setApplyAfterDate(applyafterdate);
			
			
			String getleavadvdays="select * from Leave_Structure ";
			ResultSet rsleavadvdays=ad.selectQuery(getleavadvdays);
			while(rsleavadvdays.next())
			{ 
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("CL")){
				leaveForm.setCasleavadv(rsleavadvdays.getFloat("LADVDAY"));
				leaveForm.setClMaxDays(rsleavadvdays.getFloat("LMAXALW"));
				leaveForm.setClmindur(rsleavadvdays.getInt("LMINDUR"));
				}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("EL")){
					leaveForm.setPreleavadv(rsleavadvdays.getFloat("LADVDAY"));
					leaveForm.setPreleavmin(rsleavadvdays.getFloat("LMINALW"));
					leaveForm.setPlmindur(rsleavadvdays.getInt("LMINDUR"));
					}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("SL")){
					leaveForm.setSlMaxDays(rsleavadvdays.getFloat("LMAXALW"));
				
					leaveForm.setSlmindur(rsleavadvdays.getInt("LMINDUR"));
					
					}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("LP")){
					leaveForm.setLossmindur(rsleavadvdays.getInt("LMINDUR"));
				}
			}
			int year = Calendar.getInstance().get(Calendar.YEAR);
			double sicklvcloseBal=0;
			double casuallvcloseBal=0;
			String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_calyear='"+year+"' ";
			ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
			while(rsSickBal.next()){
				
			if(rsSickBal.getInt("lv_typeid")==2){
				 sicklvcloseBal=rsSickBal.getDouble("lv_clbal");
			}
			if(rsSickBal.getInt("lv_typeid")==1){
				 casuallvcloseBal=rsSickBal.getDouble("lv_clbal");
			}
		
			}
			leaveForm.setSicklvcloseBal(sicklvcloseBal);
			leaveForm.setCasuallvcloseBal(casuallvcloseBal);
			String getLeaveType="select * from lv_type_m";
			ResultSet rsLeaveType=ad.selectQuery(getLeaveType);
			while(rsLeaveType.next())
			{
				leaveTypeID.add(rsLeaveType.getString("lv_typeid"));
				leaveType11.add(rsLeaveType.getString("lv_type"));
			}
			leaveForm.setLeaveTypeID(leaveTypeID);
			leaveForm.setLeaveTypeName(leaveType11);
			
			String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"' and t.lv_calyear='"+year+"' and t.lv_typeid=m.lv_typeid";
			ResultSet rsBalance=ad.selectQuery(getleaveBalence);
			ArrayList balList=new ArrayList();
			while(rsBalance.next())
			{
			   leaveForm.setYear(rsBalance.getInt("lv_calyear"));
				LeaveForm form2=new LeaveForm();
				form2.setLeaveType(rsBalance.getString("lv_type"));
				form2.setOpeningBalence(rsBalance.getInt("lv_opbal"));
				form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
				form2.setNoOfDays(rsBalance.getString("lv_availed"));
				form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
			form2.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
				balList.add(form2);
			}
			
			request.setAttribute("LeaveBalenceList", balList);
			
			
			String sql="select * from leave_details where id='"+Id + "'";
			ResultSet rs = ad.selectQuery(sql);
			String leaveType="";
			while (rs.next()) {
				leaveType=rs.getString("leave_type");
				leaveForm.setLeaveType(rs.getString("leave_type"));
				leaveForm.setStartDurationType(rs.getString("start_duration"));
				leaveForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
				
				
				leaveForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
				leaveForm.setReason(rs.getString("reason"));
				leaveForm.setReasonType(rs.getString("reasonType"));
				leaveForm.setEndDurationType(rs.getString("end_duration"));
				leaveForm.setNoOfDays(rs.getString("no_of_days"));
				
				leaveForm.setTotalLeaveDays(rs.getDouble("no_of_days"));
			}
			String sql1="select * from leave_details where id='"+Id + "' and documents not like ''";
			ResultSet rs11=ad.selectQuery(sql1);
				LeaveForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs11.next()) {
					leaveForm1=new LeaveForm();
					leaveForm1.setDocumentName(rs11.getString("documents"));
					leaveForm1.setId(rs11.getString("id"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);
			request.setAttribute("submitDetails", "submitDetails");
			
			LinkedList leaveReason=new LinkedList();
			LinkedList leaveDetReason=new LinkedList();
			
			String reason="select * from LEAVE_REASON";
			ResultSet a=ad.selectQuery(reason);
			try {
				while(a.next())
				{
			leaveReason.add(a.getString("Reason"));
			leaveDetReason.add(a.getString("Detailed_Reason"));
				}
				leaveForm.setLeaveReason(leaveReason);
				leaveForm.setLeaveDetReason(leaveDetReason);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("lv_calyear"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				leaveForm.setYearList(yearList);
		
		return mapping.findForward("displayDraft");
	}
	public ActionForward selectRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
{String HRid="";
LeaveForm leaveForm=(LeaveForm)form;// TODO Auto-generated method stub
String Id=request.getParameter("requstNo");
leaveForm.setId(Id);
HttpSession session=request.getSession();
UserInfo user=(UserInfo)session.getAttribute("user");
try 
{
	
	
	String sql="select emp.EMP_FULLNAME,desg.DSGSTXT,leave.leave_type,leave.submit_date,leave.start_duration,leave.end_duration,leave.start_date,leave.end_date,leave.reason,leave.reasonType,leave.no_of_days,leave.documents,leave.Approvel_Status,leave.approved_date,leave.rejected_date" +
	" from leave_details as leave,emp_official_info as emp,DESIGNATION as desg where leave.Req_Id='"+Id+"' and leave.firstname='"+user.getEmployeeNo()+"' and leave.user_id=emp.PERNR and emp.DSGID=desg.DSGID";

	ResultSet rs = ad.selectQuery(sql);
	String leaveType="";
	String userId="";
	String appstatus="";
	while (rs.next()) {
		
		leaveType=rs.getString("leave_type");
		if(leaveType.equalsIgnoreCase("1")){
			leaveType="Casual";
		}
		if(leaveType.equalsIgnoreCase("2")){
			leaveType="Sick";
		}
		if(leaveType.equalsIgnoreCase("3")){
			leaveType="Privilege";
		}
		if(leaveType.equalsIgnoreCase("4")){
			leaveType="Loss Of Pay";
		}
		if(leaveType.equalsIgnoreCase("5")){
			leaveType="Maternity";
		}
		if(leaveType.equalsIgnoreCase("6")){
			leaveType="Comp-Off";
		}
		leaveForm.setLeaveType(leaveType);
		leaveForm.setSubmitDate(rs.getString("submit_date"));
		String stDur=rs.getString("start_duration");
		
		if(stDur.equalsIgnoreCase("FD")){
			stDur="Full Day";
		}
		if(stDur.equalsIgnoreCase("FH")){
			stDur="First Half";
		}
		if(stDur.equalsIgnoreCase("SH")){
			stDur="Second Half";
		}
		leaveForm.setStartDurationType(stDur);
		String endDur=rs.getString("end_duration");
		
		if(endDur.equalsIgnoreCase("FD")){
			endDur="Full Day";
		}
		if(endDur.equalsIgnoreCase("FH")){
			endDur="First Half";
		}
		if(endDur.equalsIgnoreCase("SH")){
			endDur="Second Half";
		}
		leaveForm.setEndDurationType(endDur);
		leaveForm.setSubmitDate(rs.getString("submit_date"));
		leaveForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
		leaveForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
		leaveForm.setReason(rs.getString("reason"));
		leaveForm.setReasonType(rs.getString("reasonType"));
		leaveForm.setNoOfDays(rs.getString("no_of_days"));
		leaveForm.setDocumentName(rs.getString("documents"));
		appstatus=rs.getString("Approvel_Status");
	
	//list.add(pendAppForm);
	//leave approve timings
	boolean existStatus=false;
	String reqType="";

	ArrayList paralist=new ArrayList();
	String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
	ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
	while(rsCheckInEss.next())
	{
		int number=rsCheckInEss.getInt(1);
		reqType=rsCheckInEss.getString("reqType");
		if(number==0)
		{
			existStatus=false;
		}else{
			existStatus=true;
		}
	}
	
		if(reqType.equalsIgnoreCase("sequential"))
		{
		String getApproverDesig=" select e.ApproverId,emp.pernr ,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
	"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' order by Priority ";
		 
		ResultSet rsA=ad.selectQuery(getApproverDesig);
			while(rsA.next())
			{
				LeaveForm lvform=new LeaveForm();
				 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
				 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
				String getdetails=" select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date," +
						"all_R.rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
						" where all_R.Req_Id='"+Id+"' and all_R.Req_Type='Leave' and ((emp.PERNR=all_R.Last_Approver) or " +
						"(emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) and Req_Id='"+Id+"' and Requester_Name='"+user.getEmployeeNo()+"' and emp.PERNR='"+rsA.getString("pernr")+"'";
				
				ResultSet rsApproverDsg=ad.selectQuery(getdetails);
				
				while(rsApproverDsg.next())
				{
				
					HRid=rsApproverDsg.getString("Hr_Id");
					String reqstatus=rsApproverDsg.getString("Req_Status");
					lvform.setStatus(rsApproverDsg.getString("Req_Status"));
					if(reqstatus.equalsIgnoreCase("Pending"))
					{
						
					
						
					}
					if(reqstatus.equalsIgnoreCase("Approved"))
					{
					lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
				  
				   
					String approveDate=	rsApproverDsg.getString("approved_date");
					
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
					
					 lvform.setApprovedDate(approveDate);
					}
					
					if(reqstatus.equalsIgnoreCase("Rejected"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					  
					     
					     String approveDate=rsApproverDsg.getString("rejected_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
							
							 lvform.setApprovedDate(approveDate);
					}
					if(reqstatus.equalsIgnoreCase("Cancelled"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
						  String approveDate=rsApproverDsg.getString("rejected_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
							
							 lvform.setApprovedDate(approveDate);
					}
					lvform.setComments(rsApproverDsg.getString("Comments"));
					if(HRid!=null)
					{
						if( reqstatus.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs1=ad.selectQuery(hrdetails);
						
						while(rs1.next())
						{
							lvform.setApprover(rs1.getString("EMP_FULLNAME"));
							 lvform.setAppDesig(rs1.getString("DSGSTXT"));
						}
					}
					}
					
				
				}
				paralist.add(lvform);
			}
			
			///Actual approver 
			
			String actual="select a.*,e.EMP_FULLNAME,d.DSGSTXT from All_Request a,emp_official_info e,DESIGNATION d where Req_Id='"+Id+"' and Req_Type='Cancel Leave'  and e.PERNR=a.Actual_Approver and e.DSGID=d.DSGID  ";
			ResultSet rsact=ad.selectQuery(actual);
			if(rsact.next())
			{
				LeaveForm l=new LeaveForm();
				l.setApprover(rsact.getString("EMP_FULLNAME"));	
			 l.setAppDesig(rsact.getString("DSGSTXT"));	
				l.setStatus(rsact.getString("Actual_Approver_status"));
				  String approveDate=rsact.getString("Actual_Approved_Date");
					
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
					
					 l.setApprovedDate(approveDate);
					 l.setComments(rsact.getString("Actual_Approved_Comments"));
						paralist.add(l);
			}
			
			
			request.setAttribute("parallelapprovers", paralist);
			
		}
		else
		{
			
			String getApproverDesig="";
			if(appstatus.equalsIgnoreCase("Pending"))
			{
			 getApproverDesig="  select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date,all_R.rejected_date from " +
					"All_Request as all_R,emp_official_info as emp, DESIGNATION as desg    where all_R.Req_Id='"+Id+"' and all_R.Req_Type='Leave' and " +
					"((emp.PERNR=all_R.Last_Approver) or (emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) ";
			}
			else
			{
				 getApproverDesig="  select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date,all_R.rejected_date from " +
						"All_Request as all_R,emp_official_info as emp, DESIGNATION as desg    where all_R.Req_Id='"+Id+"' and all_R.Req_Type='Leave' and " +
						"((emp.PERNR=all_R.Last_Approver) or (emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID)  and Actual_Approver=Last_Approver";
			}
			    ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				while(rsApproverDsg.next())
				{
					LeaveForm lvform=new LeaveForm();
					HRid=rsApproverDsg.getString("Hr_Id");
					String reqstatus=rsApproverDsg.getString("Req_Status");
					lvform.setStatus(rsApproverDsg.getString("Req_Status"));
					if(reqstatus.equalsIgnoreCase("Pending"))
					{
						
					 lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));	
						
					}
					if(reqstatus.equalsIgnoreCase("Approved"))
					{
					lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
				   
				   String approveDate=	rsApproverDsg.getString("approved_date");
					
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
					
					 lvform.setApprovedDate(approveDate);
					}
					
					if(reqstatus.equalsIgnoreCase("Rejected"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					    
					     String approveDate=	rsApproverDsg.getString("rejected_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
							
							 lvform.setApprovedDate(approveDate);
					}
					if(reqstatus.equalsIgnoreCase("Cancelled"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					    
					     String approveDate=	rsApproverDsg.getString("rejected_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
							
							 lvform.setApprovedDate(approveDate);
					}
					lvform.setComments(rsApproverDsg.getString("Comments"));
					
					if(HRid!=null)
					{
						if( reqstatus.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs1=ad.selectQuery(hrdetails);
						
						while(rs1.next())
						{
							lvform.setApprover(rs1.getString("EMP_FULLNAME"));
							 lvform.setAppDesig(rs1.getString("DSGSTXT"));
						}
					     }
					}
					
					
					paralist.add(lvform);
					
				}
				
				///Actual approver 
				
				String actual="select a.*,e.EMP_FULLNAME,d.DSGSTXT from All_Request a,emp_official_info e,DESIGNATION d where Req_Id='"+Id+"' and Req_Type='Cancel Leave'  and e.PERNR=a.Actual_Approver and e.DSGID=d.DSGID  ";
				ResultSet rsact=ad.selectQuery(actual);
				if(rsact.next())
				{
					LeaveForm l=new LeaveForm();
					l.setApprover(rsact.getString("EMP_FULLNAME"));	
				 l.setAppDesig(rsact.getString("DSGSTXT"));	
					l.setStatus(rsact.getString("Actual_Approver_status"));
					  String approveDate=rsact.getString("Actual_Approved_Date");
						
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1].substring(0, 5);
						
						 l.setApprovedDate(approveDate);
						 l.setComments(rsact.getString("Actual_Approved_Comments"));
							paralist.add(l);
				}
				
				request.setAttribute("parallelapprovers", paralist);
		}
		
		LinkedList leaveReason=new LinkedList();
		LinkedList leaveDetReason=new LinkedList();
		
		String reason="select * from LEAVE_REASON";
		ResultSet a=ad.selectQuery(reason);
		try {
			while(a.next())
			{
		leaveReason.add(a.getString("Reason"));
		leaveDetReason.add(a.getString("Detailed_Reason"));
			}
			leaveForm.setLeaveReason(leaveReason);
			leaveForm.setLeaveDetReason(leaveDetReason);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	String sql1="select * from leave_details where id='"+Id + "' and documents not like ''";
	ResultSet rs11=ad.selectQuery(sql1);
		LeaveForm leaveForm1=null;
		ArrayList a1=new ArrayList();
		while(rs11.next()) {
			leaveForm1=new LeaveForm();
			leaveForm1.setDocumentName(rs11.getString("documents"));
			leaveForm1.setId(rs11.getString("id"));
			a1.add(leaveForm1);
		}
		request.setAttribute("documentDetails", a1);
	request.setAttribute("submitDetails", "submitDetails");
			request.setAttribute("leave", "leave");
	}}catch (Exception e) {
	e.printStackTrace();
}
	
		return mapping.findForward("displayRequest");
}
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("SUBMIT LEAVE DETAILS");
		LeaveForm leaveForm=(LeaveForm)form;
		String param=request.getParameter("param");
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String leaveType=leaveForm.getLeaveType();
		String startDate=leaveForm.getStartDate();
		String startDurationType=leaveForm.getStartDurationType();
		String endDurationType=leaveForm.getEndDurationType();
		String endDate=leaveForm.getEndDate();
		String noOfDays=request.getParameter("noOfDays");
		 Enumeration<String> params = request.getParameterNames();
	String parameter;
	String content_description = leaveForm.getReason();
	try{
	int check=0;
	
	/*int leaveAdv=0;
	String lvType="";
	if(leaveType.equalsIgnoreCase("1"))
	{
		lvType="CL";
	}
	if(leaveType.equalsIgnoreCase("2"))
	{
		lvType="SL";
	}
	if(leaveType.equalsIgnoreCase("3"))
	{
		lvType="EL";
	}
	if(leaveType.equalsIgnoreCase("4"))
	{
		lvType="LP";
	}
	if(leaveType.equalsIgnoreCase("5"))
	{
		lvType="ML";
	}
	
	String getLeaveAdv="select * from Leave_Structure where LEAVTYP='"+lvType+"'";
	ResultSet rsLeaveAdv=ad.selectQuery(getLeaveAdv);
	try{
	while(rsLeaveAdv.next())
	{
		leaveAdv=rsLeaveAdv.getInt("LADVDAY");
	}
	}catch (Exception e) {
		e.printStackTrace();
	}
	 String recentLeaveDt="";
	String getRecentLeaveDt="select end_date from leave_details where user_id='"+user.getEmployeeNo()+"' order by Req_Id desc";
	ResultSet rsRecDt=ad.selectQuery(getRecentLeaveDt);
	if(rsRecDt.next())
	{
		recentLeaveDt=rsRecDt.getString("end_date");
	}
	if(!(recentLeaveDt.equalsIgnoreCase("")))
	{
		String a[]=recentLeaveDt.split(" ");
		System.out.println("recentLeaveDt="+recentLeaveDt);
		String lvstDt=leaveForm.getStartDate();
		String b[]=lvstDt.split("/");
		lvstDt=b[2]+"-"+b[1]+"-"+b[0];
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try{
		Date recentlvdate = formatter.parse(recentLeaveDt);
		Date lvdate = formatter.parse(lvstDt);
		
		
		
		
		int  diffLeaveDays =  recentlvdate.getDate()- lvdate.getDate();
		System.out.println("diffLeaveDays="+diffLeaveDays);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
	
	String checkLeave="select start_date,end_date,start_duration,end_duration from leave_details where  firstname='"+user.getEmployeeNo()+"' and (Approvel_Status='Approved' or Approvel_Status='Pending')";
	ResultSet rsCheckLeave=ad.selectQuery(checkLeave);
	while(rsCheckLeave.next()){
		try{
			List<Date> dates = new ArrayList<Date>();
			String str_date =rsCheckLeave.getString("start_date");
			String lvStDur=rsCheckLeave.getString("start_duration");
			String lvEndDur=rsCheckLeave.getString("end_duration");
			String a[]=str_date.split(" ");
			str_date=a[0];
			String end_date =rsCheckLeave.getString("end_date");
            String b[]=end_date.split(" ");
            end_date=b[0];
            String a1[]=str_date.split("-");
            str_date=a1[2]+"/"+a1[1]+"/"+a1[0];
            String b1[]=end_date.split("-");
            end_date=b1[2]+"/"+b1[1]+"/"+b1[0]; 
			DateFormat formatter ; 
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date  startDate1 = formatter.parse(str_date); 
			Date  endDate1 = formatter.parse(end_date);
			long interval = 24*1000 * 60 * 60; // 1 hour in millis
			long endTime =endDate1.getTime() ; // create your endtime here, possibly using Calendar or Date
			long curTime = startDate1.getTime();
			while (curTime <= endTime) {
			    dates.add(new Date(curTime));
			    curTime += interval;
			}
			for(int i=0;i<dates.size();i++){
			    Date lDate =dates.get(i);
			    String ds = formatter.format(lDate);    
			 
			    if(ds.equalsIgnoreCase(startDate)){
			    	check++;
			    }
			}
			    for(int j=0;j<dates.size();j++){
				    Date lDate1 =dates.get(j);
				    String ds1 = formatter.format(lDate1);    
				
				    if(ds1.equalsIgnoreCase(endDate)){
				    	check++;
				    }
			}
			 if(check>0)
			 {
				 
				 
					if(lvEndDur.equalsIgnoreCase("FH")&& startDurationType.equalsIgnoreCase("SH"))
					{
						check=0;
					}
					if(lvEndDur.equalsIgnoreCase("SH")&& startDurationType.equalsIgnoreCase("FH"))
					{
						check=0;
					}
			 }
			    
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//System.out.println("check count="+check);
	
	
	if(leaveType.equalsIgnoreCase("6"))
	{
		check=0;
	}
	
	
	if(check==0){

		String documents="";
	
	        Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
			 String submitDate = ft.format(dNow);
	       
		String sql1="select * from temp_emp_leave_documents where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and req_no='"+leaveForm.getRequestNumber()+"'";
		ResultSet rs=ad.selectQuery(sql1);
	
			while(rs.next()) {
				documents+=rs.getString("file_name")+",";
			}
			if(documents.equalsIgnoreCase("")){
				documents=",";
			}
		
			Connection connection=ConnectionFactory.getConnection();
			
		
			int userLeaveCount = getCountForTable("leave_details",Integer.parseInt(user.getEmployeeNo()));
			
			String Req_Id = Integer.toString(leaveForm.getRequestNumber());
			
			int checkValue=0;
			String checkReqNo="select count(*) from leave_details where Req_Id='"+Req_Id+"'";
			ResultSet rsCheckNo=ad.selectQuery(checkReqNo);
			while(rsCheckNo.next())
			{
				checkValue=rsCheckNo.getInt(1);
			
			}
			
			
			
			
			int a=0;
			long key=-1L;
			synchronized (this) {
			//request no not available
			if(checkValue==0){
			String sql="insert into leave_details(user_id,leave_type," +
			"start_date,end_date,start_duration,end_duration,no_of_days,leave_status," +
			"record_status,documents,department_id,plant_id,designation_id,submit_date,reasonType,reason,firstname,Req_Id,Approvel_Status)" +
			" values('"+user.getEmployeeNo()+"'," +
					"'"+leaveType+"','"+EMicroUtils.dateConvert(startDate)+"','"+EMicroUtils.dateConvert(endDate)+"','"+startDurationType+"','"+leaveForm.getEndDurationType()+"'," +
					"'"+noOfDays+"','0'" +
					",'"+param+"','"+documents.substring(0, documents.lastIndexOf(","))+"'," +
					"'"+user.getDepartmentId()+"'" +
					",'"+user.getPlantId()+"','"+user.getDesignationId()+"','"+submitDate+"','"+leaveForm.getReasonType()+"','"+content_description+"','"+user.getUserName()+"','"+Req_Id+"','Pending')";
			PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			a=psmt.executeUpdate();
			ResultSet rs1=psmt.getGeneratedKeys();
		
			if (rs1!=null && rs1.next()) {
			    key = rs1.getLong(1);
			}
			
			}else{
				String getReqestNumber="select max(id)  from leave_details";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				
				int newReqNo=maxReqno;
				
				String sql="insert into leave_details(user_id,leave_type," +
				"start_date,end_date,start_duration,end_duration,no_of_days,leave_status," +
				"record_status,documents,department_id,plant_id,designation_id,submit_date,reasonType,reason,firstname,Req_Id,Approvel_Status)" +
				" values('"+user.getEmployeeNo()+"'," +
						"'"+leaveType+"','"+EMicroUtils.dateConvert(startDate)+"','"+EMicroUtils.dateConvert(endDate)+"','"+startDurationType+"','"+leaveForm.getEndDurationType()+"'," +
						"'"+noOfDays+"','0'" +
						",'"+param+"','"+documents.substring(0, documents.lastIndexOf(","))+"'," +
						"'"+user.getDepartmentId()+"'" +
						",'"+user.getPlantId()+"','"+user.getDesignationId()+"','"+submitDate+"','"+leaveForm.getReasonType()+"','"+content_description+"','"+user.getUserName()+"','"+newReqNo+"','Pending')";
				PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				a=psmt.executeUpdate();
				ResultSet rs1=psmt.getGeneratedKeys();
				String updateUploadedFiles="update temp_emp_leave_documents set req_no='"+newReqNo+"'  where user_id='"+user.getEmployeeNo()+"' and req_no='"+Req_Id+"'";
				ad.SqlExecuteUpdate(updateUploadedFiles);
				
				Req_Id=Integer.toString(newReqNo);
				leaveForm.setRequestNumber(newReqNo);
			}
			
			//update in avaiable leaves
			//String uname = user.getEmployeeNo();
			//int upd = updateAvail(uname, leaveType,noOfDays);		
			EMailer email = new EMailer();
			String approver="";
			String lApprover="";
			String pApprover="";
			String approvermail="";
			
			String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
					+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
			ResultSet approverRS=ad.selectQuery(getApproverID);
			if(approverRS.next()){
				
					approver = approverRS.getString("ApproverId");
					approver = email.getApproverName(approver);
					lApprover = "";
					pApprover = approver;
					approvermail=approverRS.getString("EMAIL_ID");
						}
			
			
			
			String addApprover="update leave_details set Approver_id='"+approver+"',Approvel_Status='Pending',Last_approver='No',Pending_approver='"+pApprover+"' where id='"+key+"' ";
			ad.SqlExecuteUpdate(addApprover);
			if(a>0){
				if(param.equalsIgnoreCase("Applied"))
				{
					
					
					boolean existStatus=false;
					String reqType="";
					String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
					ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
					while(rsCheckInEss.next())
					{
						int number=rsCheckInEss.getInt(1);
						reqType=rsCheckInEss.getString("reqType");
						if(number==0)
						{
							existStatus=false;
						}else{
							existStatus=true;
						}
					}
					if(existStatus==true){
						if(reqType.equalsIgnoreCase("sequential"))
						{
						String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
						ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
							while(rsApproverDsg.next())
							{
								pApprover=rsApproverDsg.getString("ApproverId");
							}
							int i=0;
							String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
							saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
							 i=ad.SqlExecuteUpdate(saveRecReq);
							 if(i > 0){
									leaveForm.setMessage("Leave Details Submitted Successfully");
								}
						}
						if(reqType.equalsIgnoreCase("parallel"))
						{
							String parallelApprover1="";
							String parallelApprover2="";
							String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
							ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
								while(rsApproverDsg.next())
								{
									pApprover=rsApproverDsg.getString("ApproverId");
									parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
									parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
									
								}
								int i=0;
								String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);
								leaveForm.setMessage("Leave Details Submitted Successfully");
								if(!(parallelApprover1.equalsIgnoreCase("")))
								{
									saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
									saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+parallelApprover1+"','No','"+user.getEmployeeNo()+"','')";
									 i=ad.SqlExecuteUpdate(saveRecReq);
								}
								if(!(parallelApprover2.equalsIgnoreCase("")))
								{
									saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
									saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+parallelApprover2+"','No','"+user.getEmployeeNo()+"','')";
									 i=ad.SqlExecuteUpdate(saveRecReq);
								}
								
						}
					}
					int i=0;
					if(existStatus==false){
				
					String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
					leaveForm.setMessage("Leave Details Submitted Successfully");
					
					}
					//update awaiting balence values
		
					
					int year=0;
					if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
					{
						year=Calendar.getInstance().get(Calendar.YEAR);
					}
					else
					{
						 year = Integer.parseInt(request.getParameter("year"));
					}
					
					float balence=0;
					String getBalence="select lv_awtBal from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_typeid='"+leaveType+"' and lv_calyear='"+year+"'";
					ResultSet rsBalence=ad.selectQuery(getBalence);
					while(rsBalence.next()){
						balence=rsBalence.getFloat(1);
					}
					balence=balence+Float.parseFloat(noOfDays);
					String updateWatingBal="update  lv_type_d set lv_awtBal='"+balence+"' where lv_empcode='"+user.getEmployeeNo()+"' and lv_typeid='"+leaveType+"' and lv_calyear='"+year+"' ";
					i=ad.SqlExecuteUpdate(updateWatingBal);
					if(i > 0){
						leaveForm.setMessage("Leave Details Submitted Successfully");
					}
					if(existStatus==false && !approvermail.equalsIgnoreCase("")){
					 int j = email.sendMailToApprover(request, approvermail,Req_Id,"Leave");
					}
					
					if(existStatus==true && !approvermail.equalsIgnoreCase("")){
						if(reqType.equalsIgnoreCase("sequential")){
							int j = email.sendMailToApprover(request, approvermail,Req_Id,"Leave");
						}
						if(reqType.equalsIgnoreCase("parallel")){
							int j = email.sendMailToApprover(request, approvermail,Req_Id,"Leave");
						}
					
						}
					
				}
				else
				{
					leaveForm.setMessage("Leave Details Saved in drafts Successfully");
				}
				String sql2="delete from temp_emp_leave_documents where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and req_no='"+leaveForm.getRequestNumber()+"'";
				ad.SqlExecuteUpdate(sql2);
				leaveForm.setLeaveType("");
				leaveForm.setContentDescription("");
				leaveForm.setStartDate("");
				leaveForm.setStartDurationType("");
				leaveForm.setEndDate("");
				leaveForm.setEndDurationType("");
				leaveForm.setNoOfDays("");
				leaveForm.setReasonType("");
				leaveForm.setTotalLeaveDays(0);
				
			}else{
				leaveForm.setMessage("Problem while Submitting Leave Details");
			}
		}}else{
			leaveForm.setMessage("Leave Already Applied For The Selected Date");
			leaveForm.setStartDate("");
			leaveForm.setEndDate("");
			String sql1="select * from temp_emp_leave_documents where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"'";
			ResultSet rs=ad.selectQuery(sql1);
				LeaveForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new LeaveForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Leave/")+"/"+rs.getString("file_name");
					leaveForm1.setFilePath(filePath);
					leaveForm1.setId(rs.getString("id"));
					a1.add(leaveForm1);
				}
				
				request.setAttribute("documentDetails", a1);
				
				
			
		}
		}
		catch(SQLException se){
			se.printStackTrace();
			}
			newLeaveRequest(mapping, form, request, response);
			String reasonType=leaveForm.getReasonType();
			leaveForm.setReasonType(reasonType);
			String reason=leaveForm.getReason();
			leaveForm.setReason(reason);
			
			
			ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("lv_calyear"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					leaveForm.setYearList(yearList);
		return mapping.findForward("newLeaveForm");
	}
	public ActionForward submitDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		String param=request.getParameter("param");
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String leaveType=leaveForm.getLeaveType();
		String startDate=leaveForm.getStartDate();
		String startDurationType=leaveForm.getStartDurationType();
		String endDate=leaveForm.getEndDate();
		String noOfDays=request.getParameter("noOfDays");
		String Id=leaveForm.getId();
		String Req_Id="";
		Enumeration<String> params = request.getParameterNames();
		String parameter;
		String content_description = leaveForm.getReason();
			
		String documents="";
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int subyear = cal.get(Calendar.YEAR);
	    String submitDate=day+"/"+month+"/"+subyear;
	    int check=0;
	    int checkDt=0;
	   
	    String sql11="select * from leave_details where id='"+Id + "'";
		ResultSet rs=ad.selectQuery(sql11);
		try
		{
			while(rs.next())
			{
				documents+=rs.getString("documents")+",";
				Req_Id=rs.getString("Req_Id");
			}
			if(documents.equalsIgnoreCase(""))
			{
				documents=",";
			}
		}catch(SQLException se)
		{
			se.printStackTrace();
		}
		try
		{
		Connection connection=ConnectionFactory.getConnection();
		
		
		String sql="update leave_details set leave_type='"+leaveType+"',start_date='"+EMicroUtils.dateConvert(startDate)+"',end_date='"+EMicroUtils.dateConvert(endDate)+"',start_duration='"+startDurationType+"',end_duration='"+leaveForm.getEndDurationType()+"',no_of_days='"+noOfDays+"',record_status='"+param+"',documents='"+documents.substring(0, documents.lastIndexOf(","))+"',submit_date='"+submitDate+"',reasonType='"+leaveForm.getReasonType()+"',reason='"+content_description+"' where id='"+Id+"'";
		System.out.println("****SQL OUTPUT***** of updating draft information"+sql);
		PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		int a=psmt.executeUpdate();
		EMailer email = new EMailer();
		String approver="";
		String lApprover="";
		String pApprover="";
		String approvermail="";
		
		
		/*String approver="";
		String lApprover="";
		String pApprover="";
		String approvermail="";
		
		String getApproverID="select * from Approvers_Details where Type='Leave' and Priority=1";
		
		ResultSet approverRS=ad.selectQuery(getApproverID);
		while(approverRS.next()){
			
				approver = approverRS.getString("Employee_Name");
				lApprover = "";
				pApprover = approver;
				approvermail = approverRS.getString("emailID");
				
		}*/
		if(a>0){
			if(param.equalsIgnoreCase("applied"))
			{
				
				leaveForm.setMessage("Leave Details Submitted Successfully");
				
				int year=0;
				if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
				{
					year=Calendar.getInstance().get(Calendar.YEAR);
				}
				else
				{
					 year = Integer.parseInt(request.getParameter("year"));
				}
				
				//send mail to approver
				float balence=0;
				String getBalence="select lv_awtBal from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_typeid='"+leaveType+"' and lv_calyear='"+year+"'";
				ResultSet rsBalence=ad.selectQuery(getBalence);
				while(rsBalence.next()){
					balence=rsBalence.getFloat(1);
				}
				balence=balence+Float.parseFloat(noOfDays);
				String updateWatingBal="update  lv_type_d set lv_awtBal='"+balence+"' where lv_empcode='"+user.getEmployeeNo()+"' and lv_typeid='"+leaveType+"'  and lv_calyear='"+year+"'";
				ad.SqlExecuteUpdate(updateWatingBal);
				
				
				boolean existStatus=false;
				String reqType="";
				String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' group by reqType";
				ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
				while(rsCheckInEss.next())
				{
					int number=rsCheckInEss.getInt(1);
					reqType=rsCheckInEss.getString("reqType");
					if(number==0)
					{
						existStatus=false;
					}else{
						existStatus=true;
					}
				}
				if(existStatus==true){
					if(reqType.equalsIgnoreCase("sequential"))
					{
					String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
					ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
						while(rsApproverDsg.next())
						{
							pApprover=rsApproverDsg.getString("ApproverId");
						}
						int i=0;
						String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
						saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
						 i=ad.SqlExecuteUpdate(saveRecReq);
						 if(i > 0){
								leaveForm.setMessage("Leave Details Submitted Successfully");
								newLeaveRequest(mapping, form, request, response);
								 
							}
					}
					if(reqType.equalsIgnoreCase("parallel"))
					{
						String parallelApprover1="";
						String parallelApprover2="";
						String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+user.getEmployeeNo()+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
						ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
							while(rsApproverDsg.next())
							{
								pApprover=rsApproverDsg.getString("ApproverId");
								parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
								parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
								
							}
							int i=0;
							String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
							saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
							 i=ad.SqlExecuteUpdate(saveRecReq);
							leaveForm.setMessage("Leave Details Submitted Successfully");
							if(!(parallelApprover1.equalsIgnoreCase("")))
							{
								saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+parallelApprover1+"','No','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);
							}
							if(!(parallelApprover2.equalsIgnoreCase("")))
							{
								saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+parallelApprover2+"','No','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);
							}
							newLeaveRequest(mapping, form, request, response);	
					}
				}
				if(existStatus==false){
					String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
					int i=0; 
					i=ad.SqlExecuteUpdate(saveRecReq);
				
					//int ij = email.sendMailToApprover(request, approvermail, Req_Id,"Leave");
					if(i > 0){
						leaveForm.setMessage("Leave Details Submitted Successfully");
						newLeaveRequest(mapping, form, request, response);
					}
				}
				if(existStatus==false){
					 int j = email.sendMailToApprover(request, approvermail,Req_Id,"Leave");
					}
					
					if(existStatus==true){
						if(reqType.equalsIgnoreCase("sequential")){
							int j = email.sendMailToApprover(request, approvermail,Req_Id,"Leave");
						}
						if(reqType.equalsIgnoreCase("parallel")){
							int j = email.sendMailToApprover(request, approvermail,Req_Id,"Leave");
						}
					
						}
				
				
			}
			else
			{
				leaveForm.setMessage("Leave Details Saved in drafts Successfully");
			}
			leaveForm.setLeaveType("");
			leaveForm.setContentDescription("");
			leaveForm.setStartDate("");
			leaveForm.setStartDurationType("");
			leaveForm.setEndDate("");
			leaveForm.setEndDurationType("");
			leaveForm.setNoOfDays("");
		}
		else
		{
			leaveForm.setMessage("Problem while Submitting Leave Details");
		}
		
		}catch(SQLException se){
			se.printStackTrace();
		}
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("lv_calyear"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				leaveForm.setYearList(yearList);
	    	
		return mapping.findForward("displayDraft");
	}
	public ActionForward displayRequests(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		leaveForm.setMessage("");
		leaveForm.setMessage2("");
		ArrayList a1=new ArrayList();

		ArrayList myRequestList=new ArrayList();
		String leaveType="";
		String holidayType="";
		String status="";

		String username="";

		int  totalRecords1=0;
		int  totalRecords2=0;
		  int  startRecord=0;
		  int  endRecord=0;
		try{
			int year=0;
			if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
			{
				year=Calendar.getInstance().get(Calendar.YEAR);
			}
			else
			{
				 year = Integer.parseInt(request.getParameter("year"));
			}
			leaveForm.setYear(year);
			ArrayList yearList=new ArrayList();
				ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("lv_calyear"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			  leaveForm.setYearList(yearList);
			  String getTotalRecords="select count(*) from leave_details as l  where  record_status!='Draft' and  l.firstname='"+user.getEmployeeNo()+"' and year(l.start_date)='"+year+"'";
			  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
			  while(rsTotalRecods.next())
			  {
				  totalRecords1=rsTotalRecods.getInt(1);
			  }

			  if(totalRecords1>10)
			  {
				  leaveForm.setTotalRecords(totalRecords1);
			  startRecord=1;
			  endRecord=10;
			  leaveForm.setStartRecord(1);
			  leaveForm.setEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords1;
				  leaveForm.setTotalRecords(totalRecords1);
				  leaveForm.setStartRecord(1);
				  leaveForm.setEndRecord(totalRecords1); 
			  }

			   String getLeaveRecords="select l.leave_type,l.start_duration,l.Approvel_Status,l.start_date,l.start_duration,l.end_date,l.end_duration,l.no_of_days,l.Req_Id,l.submit_date,l.record_status,l.reason,l.documents " +
				" from leave_details as l  where l.firstname='"+user.getEmployeeNo()+"' and l.record_status!='Draft' and year(l.start_date)='"+year+"' order by id  desc";


		ResultSet rsLeaveRecords=ad.selectQuery(getLeaveRecords);
		while(rsLeaveRecords.next())
		{
			LeaveForm leave=new LeaveForm();
			leave.setId(rsLeaveRecords.getString("Req_Id"));
			leave.setSubmitDate(rsLeaveRecords.getString("submit_date"));
			leaveType=rsLeaveRecords.getString("leave_type");
			if(leaveType.equalsIgnoreCase("1")){
				leaveType="Casual";
			}
			if(leaveType.equalsIgnoreCase("2")){
				leaveType="Sick";
			}
			if(leaveType.equalsIgnoreCase("3")){
				leaveType="Privilege";
			}
			if(leaveType.equalsIgnoreCase("4")){
				leaveType="Loss Of Pay";
			}
			if(leaveType.equalsIgnoreCase("5")){
				leaveType="Maternity";
			}
			if(leaveType.equalsIgnoreCase("6")){
				leaveType="Comp-Off";
			}
			leave.setLeaveType(leaveType);
			String stDur=rsLeaveRecords.getString("start_duration");
			if(stDur.equalsIgnoreCase("FD"))
			{
				stDur="Full Day";
			}
			if(stDur.equalsIgnoreCase("FH"))
			{
				stDur="First Half";
			}
			if(stDur.equalsIgnoreCase("SH"))
			{
				stDur="Second Half";
			}
			leave.setStartDurationType(stDur);
			leave.setStartDate(EMicroUtils.display(rsLeaveRecords.getDate("start_date")));
			String enDur=rsLeaveRecords.getString("end_duration");
			if(enDur.equalsIgnoreCase("FD"))
			{
				enDur="Full Day";
			}
			if(enDur.equalsIgnoreCase("FH"))
			{
				enDur="First Half";
			}
			if(enDur.equalsIgnoreCase("SH"))
			{
				enDur="Second Half";
			}
			leave.setEndDurationType(enDur);
			leave.setEndDate(EMicroUtils.display(rsLeaveRecords.getDate("end_date")));
			leave.setNoOfDays(rsLeaveRecords.getString("no_of_days"));
			leave.setStatus(rsLeaveRecords.getString("Approvel_Status"));
			/* Leave l = new Leave(EMicroUtils.display(rsLeaveRecords.getDate("start_date")),EMicroUtils.display(rsLeaveRecords.getDate("end_date")),
					 rsLeaveRecords.getString("no_of_days"),leaveType,rsLeaveRecords.getString("fullname"),
						status,rsLeaveRecords.getString("id"),rsLeaveRecords.getString("submit_date"),rsLeaveRecords.getString("record_status"),holidayType,rsLeaveRecords.getString("reason"),rsLeaveRecords.getString("documents"),rsLeaveRecords.getString(""),rsLeaveRecords.getString(""));
	*/		 

			 a1.add(leave);
		}





		Iterator it=a1.iterator();

		int i=0;
		while(i<10){
			if(it.hasNext()){
				myRequestList.add(it.next());
			i++;
			}
			else
				break;
		}
		leaveForm.setStartRecord(1);
		leaveForm.setEndRecord(i);
		leaveForm.setNext(i);
		}catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("leaveEmpDetails", myRequestList);
		session.setAttribute("leaveMyRequest", a1);
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		int length=a1.size();
		if(length==0)
		{
			request.setAttribute("noRecords", "noRecords");
			leaveForm.setMessage("No Records Are Found");

		}

		 if(totalRecords1>10)
		  {
			 leaveForm.setTotalRecords(totalRecords1);
		  startRecord=1;
		  endRecord=10;
		  leaveForm.setStartRecord(1);
		  leaveForm.setEndRecord(10);
		  request.setAttribute("displayRecordNo", "displayRecordNo");
		  request.setAttribute("nextButton", "nextButton");
		  }else
		  {
			  startRecord=1;
			  endRecord=totalRecords1;
			  leaveForm.setTotalRecords(totalRecords1);
			  leaveForm.setStartRecord(1);
			  leaveForm.setEndRecord(totalRecords1); 
		  }

		return mapping.findForward("display");
		
	}
	
	public ActionForward displayCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LeaveForm leaveForm=(LeaveForm)form;
		log.info("displayCMS()-------method");
		
		String linkName=request.getParameter("sId"); 
		
		String module=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displaySession");
		}
		try{
			
			 	
			String sql3="select id from links where module='"+module+"'";
			
			ResultSet rs2=ad.selectQuery(sql3);
			
			String inLinks="";
			while (rs2.next()) {
				inLinks+=rs2.getString("id")+",";
			}
			
			/*String sql11="select * from links where link_name='"+linkName+"' and module='Main' and" +
			" status is null";*/
			
			String sql11="select * from archieves where link_name='ESS' and module='Main' and status='null'";
			
			ResultSet rs11=ad.selectQuery(sql11);
			
			try{
				while(rs11.next()) {
					 
					leaveForm.setContentDescription(rs11.getString("content_description"));
					leaveForm.setFileFullPath(rs11.getString("file_name"));
					leaveForm.setVideoFullPath(rs11.getString("video_name"));
				}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			inLinks=inLinks.substring(0, inLinks.lastIndexOf(","));
			String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
			    list.add(s);  
			}  
			
			
			
			String userLinks=user.getIncludeSubLinks()+","+user.getIncludeSubSubLinks();
			
			String[] a1=userLinks.split(",");
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
			    list1.add(s);  
			}  
		    
			
			
			Collection listOne = list;
	        Collection listTwo = list1;
	        
	        listTwo.retainAll(listOne);
			
			
			String inkLinks="";
			String commaDelimitedString = StringUtils.collectionToCommaDelimitedString(list1);
			 commaDelimitedString=commaDelimitedString;
			
			 
			String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname," +
					"icon_name " +
			" from links  " +
			" where module='ESS'" +
			" and id in("+commaDelimitedString+") order by priority";
			
			ResultSet rs1=ad.selectQuery(sql1);
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<LinksForm> allLinkList=new LinkedList<LinksForm>();
		 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= new LinkedHashMap<LinksForm,LinkedList<LinksForm>>();
		 	
			while(rs1.next()){
				
				mainMenuSet.add(rs1.getString("sub_linkname"));
				LinksForm l=new LinksForm();
			
					l.setLinkPath(rs1.getString("url"));
					l.setPriority(""+rs1.getString("priority"));
					l.setSubLinkName(rs1.getString("sub_linkname"));
					l.setLinkName(rs1.getString("link_name"));
					l.setIconName(rs1.getString("icon_name"));
				allLinkList.add(l);
			}	
		 		
				
			 	for(String main:mainMenuSet){
			 		LinkedList<LinksForm> links=new LinkedList<LinksForm>();
			 		LinksForm temp=null;
			 		for(LinksForm aa:allLinkList){
			 				for(LinksForm sl:allLinkList)
			 				{
			 						
			 				
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 	
	 				finalLnkdList.put(temp, links);
			 	}
			 	
			 	leaveForm.setLinkName(linkName);
			 	
			 	
		 	
		 	
		 	
		 	String sql2="select * from cms_sublinks where main_linkname='"+module+"' " +
		 			"and sub_linkname='"+linkName+"' and archived_status='0'";
		 	
		 	
		 	ResultSet rs3=ad.selectQuery(sql2);
		 	
		 	NewsAndMediaForm newsMediaForm1=null;
		 	
		 	ArrayList a2=new ArrayList();
		 	while (rs3.next()) {
		 		newsMediaForm1=new NewsAndMediaForm();
		 		
		 		
		 		newsMediaForm1.setLinkId(rs3.getString("id"));
		 		newsMediaForm1.setLinkTitle(rs3.getString("link_name"));
		 		newsMediaForm1.setImageName(rs3.getString("icon_name"));
		 		a2.add(newsMediaForm1);
			}
		 	
		 	
		 	session.setAttribute("SUBLINKS", finalLnkdList);
		 	request.setAttribute("subLinkDetails", a2);
				
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		request.setAttribute("MenuIcon", module);
		
		return mapping.findForward("displayCMS");
	}
	public ActionForward displayCMS1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		LeaveForm leaveForm=(LeaveForm)form;
		
		
		String linkName=request.getParameter("sId"); 
		
		String module="ESS"; 		
		HttpSession session=request.getSession();
		//UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{
			
			String contentDesc="";
			String uplodedfiles="";
			String uplodedvideos="";
			String sql11="select * from archieves where link_name='ESS' and module='Main' and status='null' order by month desc";
			
			ResultSet rs11=ad.selectQuery(sql11);
			
			try{
				while(rs11.next()) {
					 contentDesc+=rs11.getString("content_description");
					 if(!rs11.getString("file_name").equals(""))
					 uplodedfiles+=rs11.getString("file_name")+",";
					 if(!rs11.getString("video_name").equals(""))
					 uplodedvideos+=rs11.getString("video_name")+",";
					leaveForm.setContentDescription(contentDesc);
					leaveForm.setFileFullPath(uplodedfiles);
					leaveForm.setVideoFullPath(uplodedvideos);
				}
				
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					leaveForm.setFileFullPath("");
				}
				if(uplodedvideos.equalsIgnoreCase(""))
				{
					leaveForm.setVideoFullPath("");
				}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			 	leaveForm.setLinkName(linkName);
			 	
			 	
			 	
		 	String sql2="select * from cms_sublinks where main_linkname='"+module+"' " +
		 			"and sub_linkname='"+linkName+"' and archived_status='0'";
		 	
		 	
		 	ResultSet rs3=ad.selectQuery(sql2);
		 	
		 	NewsAndMediaForm newsMediaForm1=null;
		 	
		 	ArrayList a2=new ArrayList();
		 	while (rs3.next()) {
		 		newsMediaForm1=new NewsAndMediaForm();
		 		
		 		
		 		newsMediaForm1.setLinkId(rs3.getString("id"));
		 		newsMediaForm1.setLinkTitle(rs3.getString("link_name"));
		 		newsMediaForm1.setImageName(rs3.getString("icon_name"));
		 		a2.add(newsMediaForm1);
			}
		 	
		 	
		 	session.setAttribute("SUBLINKS", session.getAttribute("SUBLINKS"));
		 	request.setAttribute("subLinkDetails", a2);
				
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		
		request.setAttribute("MenuIcon", module);
		request.setAttribute("linkName", "ESS");
		return mapping.findForward("displayCMS1");
	}
	
	
	public ActionForward newLeaveRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		
		
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		int lmindur=0;
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		try{
			
			String applyafterdate="";
			String rule="select * from LEAVE_APPLY_RULE";
			ResultSet bbc=ad.selectQuery(rule);
			if(bbc.next())
			{
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -bbc.getInt("days"));
				Date date = cal.getTime();             
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				applyafterdate = format1.format(date);            
				
			}
			
			leaveForm.setApplyAfterDate(applyafterdate);
			
			String getleavadvdays="select * from Leave_Structure ";
			ResultSet rsleavadvdays=ad.selectQuery(getleavadvdays);
			while(rsleavadvdays.next())
			{ 
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("CL")){
				leaveForm.setCasleavadv(rsleavadvdays.getFloat("LADVDAY"));
				leaveForm.setClMaxDays(rsleavadvdays.getFloat("LMAXALW"));
				leaveForm.setClmindur(rsleavadvdays.getInt("LMINDUR"));
				
				}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("SL")){
					leaveForm.setSlMaxDays(rsleavadvdays.getFloat("LMAXALW"));
					leaveForm.setSlmindur(rsleavadvdays.getInt("LMINDUR"));
					}
				
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("EL")){
					leaveForm.setPreleavadv(rsleavadvdays.getFloat("LADVDAY"));
					leaveForm.setPreleavmin(rsleavadvdays.getFloat("LMINALW"));
					leaveForm.setPlmindur(rsleavadvdays.getInt("LMINDUR"));
					}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("LP")){
					leaveForm.setLossmindur(rsleavadvdays.getInt("LMINDUR"));
				}
			}
			
			int year = Calendar.getInstance().get(Calendar.YEAR);
			
			
			double sicklvcloseBal=0;
			double casuallvcloseBal=0;
			String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_calyear='"+year+"'";
			ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
			while(rsSickBal.next()){
			if(rsSickBal.getInt("lv_typeid")==2){
				 sicklvcloseBal=rsSickBal.getDouble("lv_clbal");
			}
			if(rsSickBal.getInt("lv_typeid")==1){
				 casuallvcloseBal=rsSickBal.getDouble("lv_clbal");
			}
			}
			leaveForm.setSicklvcloseBal(sicklvcloseBal);
			leaveForm.setCasuallvcloseBal(casuallvcloseBal);
			
			
			String getReqestNumber="select max(id)  from leave_details";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			
			leaveForm.setRequestNumber(maxReqno);
			
			LinkedList leaveTypeID=new LinkedList();
			LinkedList leaveType=new LinkedList();
			
			leaveForm.setYear(year);
		String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"' and t.lv_calyear='"+year+"' and t.lv_typeid=m.lv_typeid";
		ResultSet rsBalance=ad.selectQuery(getleaveBalence);
		ArrayList balList=new ArrayList();
		while(rsBalance.next())
		{
		   leaveForm.setYear(rsBalance.getInt("lv_calyear"));
			LeaveForm form2=new LeaveForm();
			form2.setLeaveType(rsBalance.getString("lv_type"));
			form2.setOpeningBalence(rsBalance.getFloat("lv_opbal"));
			form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
			form2.setNoOfDays(rsBalance.getString("lv_availed"));
			form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
		form2.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
		/*	String lvqry = "select no_of_days from leave_details where Approvel_Status='Pending'";
			ResultSet lvqryRS=ad.selectQuery(lvqry);
			while(lvqryRS.next()){
				String nD = lvqryRS.getString("no_of_days");
				if(!nD.equalsIgnoreCase("null")){
				noDays = noDays+lvqryRS.getFloat("no_of_days");
				}
			}*/
		/*	form2.setNoOfDays(""+noDays);
			if(rsBalance.getFloat("lv_clbal") != 0.0){
				String getLeaveType="select * from lv_type_m where lv_typeid="+rsBalance.getString("lv_typeid");
				ResultSet rs=ad.selectQuery(getLeaveType);
				while(rs.next())
				{
					leaveTypeID.add(rs.getString("lv_typeid"));
					leaveType.add(rs.getString("lv_type"));
				}
				leaveForm.setLeaveTypeID(leaveTypeID);
				leaveForm.setLeaveTypeName(leaveType);
			}*/
			
			
			
			balList.add(form2);
		}
		request.setAttribute("LeaveBalenceList", balList);
		String getGender="select SEX from emp_official_info where PERNR='"+user.getUserName()+"'";
		ResultSet rsGender=ad.selectQuery(getGender);
		String gender="";
		while(rsGender.next()){
			gender=rsGender.getString("SEX");
		}
		if(!user.getPlantId().equalsIgnoreCase("16"))
		{
		if(gender.equalsIgnoreCase("M"))
		{
			String getLeaveType="select * from lv_type_m where lv_typeid!=5";
			ResultSet rs=ad.selectQuery(getLeaveType);
			while(rs.next())
			{
				leaveTypeID.add(rs.getString("lv_typeid"));
				leaveType.add(rs.getString("lv_type"));
			}
			leaveForm.setLeaveTypeID(leaveTypeID);
			leaveForm.setLeaveTypeName(leaveType);
		}else{
			String getLeaveType="select * from lv_type_m ";
			ResultSet rs=ad.selectQuery(getLeaveType);
			while(rs.next())
			{
				leaveTypeID.add(rs.getString("lv_typeid"));
				leaveType.add(rs.getString("lv_type"));
			}
			leaveForm.setLeaveTypeID(leaveTypeID);
			leaveForm.setLeaveTypeName(leaveType);
		}
		}
		else
		{
			if(gender.equalsIgnoreCase("M"))
			{
				String getLeaveType="select * from lv_type_m where lv_typeid not in (5,6)";
				ResultSet rs=ad.selectQuery(getLeaveType);
				while(rs.next())
				{
					leaveTypeID.add(rs.getString("lv_typeid"));
					leaveType.add(rs.getString("lv_type"));
				}
				leaveForm.setLeaveTypeID(leaveTypeID);
				leaveForm.setLeaveTypeName(leaveType);
			}else{
				String getLeaveType="select * from lv_type_m where lv_typeid not in (6)";
				ResultSet rs=ad.selectQuery(getLeaveType);
				while(rs.next())
				{
					leaveTypeID.add(rs.getString("lv_typeid"));
					leaveType.add(rs.getString("lv_type"));
				}
				leaveForm.setLeaveTypeID(leaveTypeID);
				leaveForm.setLeaveTypeName(leaveType);
			}
			}
		LinkedList leaveIDReason=new LinkedList();
		LinkedList leaveDetReason=new LinkedList();
		
		String reason="select distinct(Reason),Detailed_Reason from LEAVE_REASON";
		ResultSet a=ad.selectQuery(reason);
		try {
			while(a.next())
			{
		leaveIDReason.add(a.getString("Reason"));
		leaveDetReason.add(a.getString("Detailed_Reason"));
			}
			leaveForm.setLeaveReason(leaveIDReason);
			leaveForm.setLeaveDetReason(leaveDetReason);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		leaveForm.setNoOfDays("");
		leaveForm.setReason("");
		leaveForm.setReasonType("");
		leaveForm.setTotalLeaveDays(0);
		LinkedList appList = getApprovers(request, response);
		
		request.setAttribute("appList", appList);
		
		if(appList.size()==0)
		{
			leaveForm.setMessage("No Approvers assigned.Please Contact Admin");
			return mapping.findForward("leaveBal");
			
		}
		
		///		check  personal info updated or not
		String arrow="select count(distinct emp_personal_info.user_id) + count( distinct emp_address.user_id)  +count( distinct emp_family_details.user_id)+count(distinct emp_education_details.user_id)"
				+ " +count( distinct emp_language_details.user_id)as allow from emp_official_info left outer join emp_personal_info on "
				+ "  emp_personal_info.user_id=emp_official_info.PERNR left outer join emp_address on   emp_address.user_id=emp_official_info.PERNR left outer"
				+ " join emp_family_details on   emp_family_details.user_id=emp_official_info.PERNR left outer join emp_education_details on   "
				+ "emp_education_details.user_id=emp_official_info.PERNR left outer join emp_language_details on   emp_language_details.user_id=emp_official_info.PERNR"
				+ "  where  emp_official_info.ACTIVE=1  and getdate()>='2017-02-20'  and  pernr='"+user.getEmployeeNo()+"'  ";
		ResultSet gg=ad.selectQuery(arrow);
		try {
			if(gg.next())
			{
				if(gg.getInt(1)<5)
				{
					leaveForm.setMessage("Please Update Personal info");
					return mapping.findForward("leaveBal");
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		///
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select distinct top 2 lv_calyear from lv_type_d order by lv_calyear desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("lv_calyear"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				leaveForm.setYearList(yearList);
		
		request.setAttribute("submitDetails", "submitDetails");
		return mapping.findForward("newLeaveForm");
	}
	
	public ActionForward displayDrafts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String leaveType="";
		leaveForm.setMessage("");
		leaveForm.setMessage2("");
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		  
		  
		  try{
			  int year = Calendar.getInstance().get(Calendar.YEAR);
			  String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_calyear='"+year+"'";
				ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
				while(rsSickBal.next()){
					double sicklvcloseBal=0;
					double casuallvcloseBal=0;
				if(rsSickBal.getInt("lv_typeid")==2){
					 sicklvcloseBal=rsSickBal.getDouble("lv_clbal");
				}
				if(rsSickBal.getInt("lv_typeid")==1){
					 casuallvcloseBal=rsSickBal.getDouble("lv_clbal");
				}
				leaveForm.setSicklvcloseBal(sicklvcloseBal);
				leaveForm.setCasuallvcloseBal(casuallvcloseBal);
				}
			  
		  String getTotalRecords="select count(*) from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status='Draft'";
		  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
		  while(rsTotalRecods.next())
		  {
			  totalRecords=rsTotalRecods.getInt(1);
		  }
		  if(totalRecords>10)
		  {
			  leaveForm.setTotalRecords(totalRecords);
		  startRecord=1;
		  endRecord=10;
		  leaveForm.setStartRecord(1);
		  leaveForm.setEndRecord(10);
		  request.setAttribute("displayRecordNo", "displayRecordNo");
		  request.setAttribute("nextButton", "nextButton");
		  }else
		  {
			  startRecord=1;
			  endRecord=totalRecords;
			  leaveForm.setTotalRecords(totalRecords);
			  leaveForm.setStartRecord(1);
			  leaveForm.setEndRecord(totalRecords); 
		  }			
						
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,id,start_date,start_duration,end_date,end_duration,no_of_days,leave_type,submit_date " +
			"from leave_details where user_id='"+user.getEmployeeNo()+"' and record_status='Draft' and Year(start_date)='"+year+"') as sub Where  sub.RowNum between 1 and 10 ";
		ResultSet rsLeaveRecords=ad.selectQuery(sql);
		
			ArrayList a1=new ArrayList();
			String holidayType="";
			String LeaveType="";
			LeaveForm leave=null;
			while (rsLeaveRecords.next()) {
				
				leave=new LeaveForm();
				
			
				leave.setId(rsLeaveRecords.getString("id"));
				leave.setSubmitDate(rsLeaveRecords.getString("submit_date"));
				leaveType=rsLeaveRecords.getString("leave_type");
				if(leaveType.equalsIgnoreCase("1")){
					leaveType="Casual";
				}
				if(leaveType.equalsIgnoreCase("2")){
					leaveType="Sick";
				}
				if(leaveType.equalsIgnoreCase("3")){
					leaveType="Privilege";
				}
				if(leaveType.equalsIgnoreCase("4")){
					leaveType="Loss Of Pay";
				}
				if(leaveType.equalsIgnoreCase("5")){
					leaveType="Maternity";
				}
				if(leaveType.equalsIgnoreCase("6")){
					leaveType="Comp-Off";
				}
				leave.setLeaveType(leaveType);
				String stDur=rsLeaveRecords.getString("start_duration");
				if(stDur.equalsIgnoreCase("FD"))
				{
					stDur="Full Day";
				}
				if(stDur.equalsIgnoreCase("FH"))
				{
					stDur="First Half";
				}
				if(stDur.equalsIgnoreCase("SH"))
				{
					stDur="Second Half";
				}
				leave.setStartDurationType(stDur);
				leave.setStartDate(EMicroUtils.display(rsLeaveRecords.getDate("start_date")));
				String enDur=rsLeaveRecords.getString("end_duration");
				if(enDur.equalsIgnoreCase("FD"))
				{
					enDur="Full Day";
				}
				if(enDur.equalsIgnoreCase("FH"))
				{
					enDur="First Half";
				}
				if(enDur.equalsIgnoreCase("SH"))
				{
					enDur="Second Half";
				}
				leave.setEndDurationType(enDur);
				leave.setEndDate(EMicroUtils.display(rsLeaveRecords.getDate("end_date")));
				leave.setNoOfDays(rsLeaveRecords.getString("no_of_days"));
			
				
				
				a1.add(leave);
			}
			request.setAttribute("leaveDraftDetails", a1);
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			if(a1.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				leaveForm.setMessage("No records are found");
			}
		}catch(SQLException se){
			se.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	
	
	/*public ActionForward displayTabs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		String parameter=request.getParameter("param");
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(parameter.equalsIgnoreCase("NewRequests")){
			request.setAttribute("submitDetails", "submitDetails");
		}
		if(parameter.equalsIgnoreCase("drafts")){
			String sql="select * from leave_details where user_id='"+user.getId()+"' " +
					"and record_status='Draft'";
			
			ResultSet rs=ad.selectQuery(sql);
			try{
				ArrayList a1=new ArrayList();
				String holidayType="";
				String LeaveType="";
				LeaveForm leaveForm1=null;
				while (rs.next()) {
					leaveForm1=new LeaveForm();
					
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
					holidayType=rs.getString("holiday_type");
					if(holidayType.equalsIgnoreCase("FD")){
						holidayType="Full Day";
					}
					if(holidayType.equalsIgnoreCase("FH")){
						holidayType="First Half";
					}
					if(holidayType.equalsIgnoreCase("SH")){
						holidayType="Second Half";
					}
					leaveForm1.setHolidayType(holidayType);
					leaveForm1.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
					leaveForm1.setNoOfDays(rs.getString("no_of_days"));
					LeaveType=rs.getString("leave_type");
					if(LeaveType.equalsIgnoreCase("CL")){
						LeaveType="Casual Leave";
					}
					if(LeaveType.equalsIgnoreCase("SL")){
						LeaveType="Sick Leave";
					}
					if(LeaveType.equalsIgnoreCase("EL")){
						LeaveType="Earned Leave";
					}
					if(LeaveType.equalsIgnoreCase("LOP")){
						LeaveType="Loss of Pay";
					}
					if(LeaveType.equalsIgnoreCase("AL")){
						LeaveType="Advance Leave";
					}
					if(LeaveType.equalsIgnoreCase("ML")){
						LeaveType="Maternity Leave";
					}
					
					leaveForm1.setLeaveType(LeaveType);
					a1.add(leaveForm1);
				}
				request.setAttribute("leaveDraftDetails", a1);
				if(a1.size()==0)
				{
					request.setAttribute("noRecords", "noRecords");
					leaveForm.setMessage("No records are found");
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		if(parameter.equalsIgnoreCase("listAll")){
			display(mapping, form, request, response);
		}
		return mapping.findForward("display");
	}
	*/
	
	
	
	
	
	
	public ActionForward cancelRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LeaveForm leaveForm=(LeaveForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String[] selectedValue=request.getParameterValues("listid2");
		try
		{
		for(int i=0;i<selectedValue.length;i++)
		{
			String deleteRequest="delete from leave_details where id='"+selectedValue[i]+"' ";
			int a=0;
			a=ad.SqlExecuteUpdate(deleteRequest);
			if(a>0)
			{
				leaveForm.setMessage("Requests cancelled Successfully");
			}
			else
			{
				leaveForm.setMessage(" Error while Canceling Requests ");
			}
		}
		//display(mapping, form, request, response);
		displayDrafts(mapping, form, request, response);
		}catch(Exception se)
		{
			se.printStackTrace();
		}
		return mapping.findForward("draftRecords");
	}
	public ActionForward deleteDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LeaveForm leaveForm=(LeaveForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String[] selectedValue=request.getParameterValues("listid2");
		try
		{
		for(int i=0;i<selectedValue.length;i++)
		{
			String deleteRequest="delete from leave_details where id='"+selectedValue[i]+"' and record_status='Draft'";
			int a=0;
			a=ad.SqlExecuteUpdate(deleteRequest);
			if(a>0)
			{
				leaveForm.setMessage("Draft deleted Suceessfully");
			}
			else
			{
				leaveForm.setMessage(" Error while deleteing  Drafts ");
			}
		}
		
		}catch(Exception se)
		{
			se.printStackTrace();
		}
		return mapping.findForward("display");
	}
	
	public ActionForward displayForwardReqEmpDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LeaveForm leaveForm=(LeaveForm)form;
		String param=request.getParameter("param");
		leaveForm.setApproverNumber(param);
		return mapping.findForward("forwardEmployeeListForm");
	}
	


	public ActionForward ForwardRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		HttpSession session=request.getSession();
		LeaveForm leaveForm=(LeaveForm)form;
		UserInfo user=(UserInfo)session.getAttribute("user");

	    Integer id=Integer.parseInt(leaveForm.getId());
		Integer emp_id=Integer.parseInt(leaveForm.getEmployeeNumber());
		
		
		try{
		String pendingApprover="";	
		String pendingApprovers="select * from leave_details where id='"+id+"'";
		ResultSet rsPenddingList=ad.selectQuery(pendingApprovers);
		while(rsPenddingList.next())
		{
			pendingApprover=rsPenddingList.getString("Pending_approver");
		}
		
		pendingApprover=pendingApprover.replaceAll(user.getFullName(),leaveForm.getApprover());
		
		int j=0;
		String Query="update leave_details set Forwarded_emp_id='"+emp_id+"', Approvel_Status='3',Pending_approver='"+pendingApprover+"' where id='"+id+"'";
		j=ad.SqlExecuteUpdate(Query);
		if(j>0)
			leaveForm.setMessage("Request has been farwarded..");
		}catch (Exception e) {
			e.printStackTrace();
		}
		/*System.out.println("employee number="+leaveForm.getEmployeeNumber());
		System.out.println("employee name="+leaveForm.getApprover());
		System.out.println("Designation number="+leaveForm.getDesignation());
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userId=user.getId();
		System.out.println("userId="+userId);
		int approver1=0;
		int approver2=0;
		int approver3=0;
		int approver4=0;
		int approver5=0;
		try{
			ResultSet rs=null;
		String getApproverID="select * from leaveapprovallevels where approval_type='Leave'";
		rs=ad.selectQuery(getApproverID);
		while(rs.next()){
			approver1=rs.getInt("approver1");
			approver2=rs.getInt("approver2");
			approver3=rs.getInt("approver3");
			approver4=rs.getInt("approver4");
			approver5=rs.getInt("approver5");
		}
		if(userId==approver1){		
			
			String updateForwardRequest="update leave_details set Forwarded_Emp_id='"+leaveForm.getEmployeeNumber()+"',Forward_Emp1_status='0',approver_status1='3',Reqest_Forward_Emp_Id='"+userId+"' where id='"+leaveForm.getId()+"'";
			int i=0;
			i=ad.SqlExecuteUpdate(updateForwardRequest);
			System.out.println("i="+i);
			if(i>0){
				
				session.setAttribute("status","Your Request Has Been Forwarded.");
				
			}
			if(i==0){
				session.setAttribute("status","Your Request Not Forwarded.Please Check.... ");
			}
		}
		else if(userId==approver2){		
			
			String updateForwardRequest="update leave_details set Forward_Emp2='"+leaveForm.getEmployeeNumber()+"',Forward_Emp2_status='0',approver_status2='3',Reqest_Forward_Emp_Id='"+userId+"' where id='"+leaveForm.getId()+"'";
			int i=0;
			i=ad.SqlExecuteUpdate(updateForwardRequest);
			System.out.println("i="+i);
			if(i>0){
				
				session.setAttribute("status","Your Request Has Been Forwarded.");
				
			}
			if(i==0){
				session.setAttribute("status","Your Request Not Forwarded.Please Check.... ");
			}
		}
		else if(userId==approver3){		
			
			String updateForwardRequest="update leave_details set Forward_Emp3='"+leaveForm.getEmployeeNumber()+"',Forward_Emp3_status='0',approver_status3='3',Reqest_Forward_Emp_Id='"+userId+"' where id='"+leaveForm.getId()+"'";
			int i=0;
			i=ad.SqlExecuteUpdate(updateForwardRequest);
			System.out.println("i="+i);
			if(i>0){
				session.setAttribute("status","Your Request Has Been Forwarded.");
			}
			if(i==0){
				session.setAttribute("status","Your Request Not Forwarded.Please Check.... ");
			}
		}
      else if(userId==approver4){		
			
			String updateForwardRequest="update leave_details set Forward_Emp4='"+leaveForm.getEmployeeNumber()+"',Forward_Emp4_status='0',approver_status4='3',Reqest_Forward_Emp_Id='"+userId+"' where id='"+leaveForm.getId()+"'";
			int i=0;
			i=ad.SqlExecuteUpdate(updateForwardRequest);
			System.out.println("i="+i);
			if(i>0){
				session.setAttribute("status","Your Request Has Been Forwarded.");
			}
			if(i==0){
				session.setAttribute("status","Your Request Not Forwarded.Please Check.... ");
			}
		}
      else if(userId==approver5){		
			
			String updateForwardRequest="update leave_details set Forward_Emp5='"+leaveForm.getEmployeeNumber()+"',Forward_Emp5_status='0',approver_status5='3',Reqest_Forward_Emp_Id='"+userId+"' where id='"+leaveForm.getId()+"'";
			System.out.println(" updateForwardRequest===="+updateForwardRequest);
			int i=0;
			i=ad.SqlExecuteUpdate(updateForwardRequest);
			System.out.println("i="+i);
			if(i>0){
				session.setAttribute("status","Your Request Has Been Forwarded.");
			}
			if(i==0){
				session.setAttribute("status","Your Request Not Forwarded.Please Check.... ");
			}
		}
		
		}catch (Exception e) {
		e.printStackTrace();
		}
		*/
		displayRequests(mapping, form, request, response);
		
		return mapping.findForward("display");
	}

	public String getLeaveType(String leaveType){
		if(leaveType.equalsIgnoreCase("1"))leaveType="Casual Leave";
		else if(leaveType.equalsIgnoreCase("2"))leaveType="Sick Leave";
		else if(leaveType.equalsIgnoreCase("3"))leaveType="Privilage Leave";
		else if(leaveType.equalsIgnoreCase("4"))leaveType="Loss of Pay";
		else if(leaveType.equalsIgnoreCase("5"))leaveType="Maternity Leave";
		else if(leaveType.equalsIgnoreCase("6"))leaveType="COMP-OFF";
		return leaveType;
	}
	
	public String getHolidayType(String holidayType){
		if(holidayType.equalsIgnoreCase("FD")){
			holidayType="Full Day";
		}
		if(holidayType.equalsIgnoreCase("FH")){
			holidayType="First Half";
		}
		if(holidayType.equalsIgnoreCase("SH")){
			holidayType="Second Half";
		}
		return holidayType;
	}
	public int getCountForTable(String tname, int uId){
    	int rowCount = 0;
    	MainDao exeQry = new MainDao();
	    String sql="select count(*) from "+tname+" where user_id ='"+uId+"'";
		try{
			ResultSet countrs=exeQry.selectQuery(sql);
			while(countrs.next()) {
					rowCount=Integer.parseInt(countrs.getString(1));
			}
		}
		catch(SQLException se){
			System.out.println("Exception @ getting count");
			se.printStackTrace();
			}
    	return rowCount;
    }
	
	
	public int sendMailToApprover(HttpServletRequest request, String approvermail, String Req_Id){
		int success=0;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String userId=user.getEmployeeNo();
		String leaveType = ""; //getLeaveType(leaveType);
		String holidayType = "";
		String fullName = user.getFullName();
		try{
			//getting details from leave_details
			String submitDate="";
			String pApprover="";
			String startDate = "";
			String noOfDays = "";
			String leavedetails = "select * from leave_details where user_id="+userId+" and Req_Id='"+Req_Id+"'";
			ResultSet ldRS = ad.selectQuery(leavedetails);
			while(ldRS.next()){
				submitDate = ldRS.getString("submit_date");
				startDate = EMicroUtils.display1(ldRS.getDate("start_date"));
				pApprover = ldRS.getString("Pending_Approver");
				leaveType = ldRS.getString("leave_type");
				noOfDays = ldRS.getString("no_of_days");
				holidayType = ldRS.getString("start_duration");
			}
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+user.getEmployeeNo()+"','')";
			int i=ad.SqlExecuteUpdate(saveRecReq);
			leaveType = getLeaveType(leaveType);
			MailInboxForm mailForm = new MailInboxForm();
			MailInboxAction mailAction = new MailInboxAction();
			mailForm.setToAddress(approvermail);
			System.out.println("m -> "+approvermail);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");
			mailForm.setSubject("Leave Request");
			String desc = "Requested By : "+fullName+"</br>";
			desc = desc + "Requested Date : "+submitDate+"</br>";
			desc = desc + "Leave Type : "+leaveType+" for "+getHolidayType(holidayType)+"</br>";
			desc = desc + "From : "+startDate+"</br>";
			desc = desc + "No.Of Days : "+noOfDays+"</br>";
			mailForm.setDescription(desc);
			mailAction.mailSendToRecipient(request, mailForm,"request");
			success=1;
		}catch (ServletException se) { System.out.println("ServletException @ sending leave request.."); se.printStackTrace();}
		catch (MessagingException se) { System.out.println("MessagingException @ sending leave request.."); se.printStackTrace();}
		catch (IOException ioe) { System.out.println("IOException @ sending leave request.."); ioe.printStackTrace();}
		catch (SQLException sqle) { System.out.println("SQLException @ sending leave request.."); sqle.printStackTrace();}
		return success;
	}
	public int updateAvail(String empno, String leaveType, String takenLeave){
		int upd=0;
		float avail=0;
		float cb=0;
		
		try{
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int lvt = Integer.parseInt(leaveType);
			float ll = Float.parseFloat(takenLeave);
			String getleaveBalence="select t.lv_availed,t.lv_clbal from lv_type_d as t where t.lv_empcode='"+empno+"' and t.lv_calyear='"+year+"' and t.lv_typeid="+lvt;
			ResultSet rsBalance=ad.selectQuery(getleaveBalence);
			ArrayList balList=new ArrayList();
			while(rsBalance.next())
			{
				avail = rsBalance.getFloat("lv_availed") + ll;
				cb = rsBalance.getFloat("lv_clbal") - ll;
				upd = ad.SqlExecuteUpdate("update lv_type_d set lv_availed="+avail+", lv_clbal="+cb+" where lv_empcode='"+empno+"' and lv_calyear='"+year+"' and lv_typeid="+lvt);
			}
		}
		catch (SQLException sqle) { System.out.println("SQLException @ updating available leave.."); sqle.printStackTrace();}
		return upd;
	}
	
	public String getTableName(String location)
	{
		String tableName="";
		if(location.equalsIgnoreCase("CBWH"))
			tableName="CBWHCAL";
		if(location.equalsIgnoreCase("ML00"))
				tableName="CmpCal2013";
		if(location.equalsIgnoreCase("ML01"))
			tableName="ML01CAL";
		if(location.equalsIgnoreCase("ML02"))
			tableName="ML02CAL";
		if(location.equalsIgnoreCase("ML03"))
			tableName="ML03CAL";
		if(location.equalsIgnoreCase("ML04"))
			tableName="ML04CAL";
		if(location.equalsIgnoreCase("ML05"))
			tableName="ML05CAL";
		if(location.equalsIgnoreCase("ML06"))
			tableName="ML06CAL";
		if(location.equalsIgnoreCase("ML07"))
			tableName="ML07CAL";
		if(location.equalsIgnoreCase("ML08"))
			tableName="ML08CAL";
		if(location.equalsIgnoreCase("ML09"))
			tableName="ML09CAL";
		if(location.equalsIgnoreCase("ML10"))
			tableName="ML10CAL";
		if(location.equalsIgnoreCase("ML11"))
			tableName="ML11CAL";
		if(location.equalsIgnoreCase("ML12"))
			tableName="ML12CAL";
		if(location.equalsIgnoreCase("ML13"))
			tableName="ML13CAL";
		if(location.equalsIgnoreCase("ML14"))
			tableName="ML14CAL";
		if(location.equalsIgnoreCase("ML15"))
			tableName="ML15CAL";
		if(location.equalsIgnoreCase("ML16"))
			tableName="ML16CAL";
		if(location.equalsIgnoreCase("ML17"))
			tableName="ML17CAL";
		if(location.equalsIgnoreCase("ML18"))
       		tableName="ML18CAL";
		if(location.equalsIgnoreCase("ML19"))
			tableName="ML19CAL";
		if(location.equalsIgnoreCase("ML20"))
			tableName="ML20CAL";
		if(location.equalsIgnoreCase("ML21"))
			tableName="ML21CAL";
		if(location.equalsIgnoreCase("ML22"))
       		tableName="ML22CAL";
		if(location.equalsIgnoreCase("ML23"))
			tableName="ML23CAL";
		if(location.equalsIgnoreCase("ML24"))
			tableName="ML24CAL";
		if(location.equalsIgnoreCase("ML25"))
			tableName="ML25CAL";
		if(location.equalsIgnoreCase("ML26"))
       		tableName="ML26CAL";
		if(location.equalsIgnoreCase("ML51"))
			tableName="ML51CAL";
		if(location.equalsIgnoreCase("ML52"))
			tableName="ML52CAL";
		if(location.equalsIgnoreCase("ML53"))
			tableName="ML53CAL";
		if(location.equalsIgnoreCase("ML54"))
			tableName="ML54CAL";
		if(location.equalsIgnoreCase("ML55"))
			tableName="ML55CAL";
		if(location.equalsIgnoreCase("ML56"))
			tableName="ML56CAL";
		if(location.equalsIgnoreCase("ML57"))
			tableName="ML57CAL";
		if(location.equalsIgnoreCase("ML58"))
			tableName="ML58CAL";
		if(location.equalsIgnoreCase("ML59"))
			tableName="ML59CAL";
		if(location.equalsIgnoreCase("ML60"))
			tableName="ML60CAL";
		if(location.equalsIgnoreCase("ML61"))
			tableName="ML61CAL";
		if(location.equalsIgnoreCase("ML62"))
			tableName="ML62CAL";
		if(location.equalsIgnoreCase("ML63"))
			tableName="ML63CAL";
		if(location.equalsIgnoreCase("ML64"))
			tableName="ML64CAL";
		if(location.equalsIgnoreCase("ML65"))
			tableName="ML65CAL";
		if(location.equalsIgnoreCase("ML66"))
			tableName="ML66CAL";
		if(location.equalsIgnoreCase("ML67"))
			tableName="ML67CAL";
		if(location.equalsIgnoreCase("ML68"))
			tableName="ML68CAL";
		if(location.equalsIgnoreCase("ML90"))
			tableName="ML90CAL";
		if(location.equalsIgnoreCase("ML91"))
			tableName="ML91CAL";
		if(location.equalsIgnoreCase("ML92"))
			tableName="ML92CAL";
		if(location.equalsIgnoreCase("ML27"))
       		tableName="ML27CAL";
	
		return tableName;
	}
}
