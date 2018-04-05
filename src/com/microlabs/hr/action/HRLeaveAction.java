package com.microlabs.hr.action;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.action.LeaveAction;
import com.microlabs.ess.action.OnDutyAction;
import com.microlabs.ess.action.Permission;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.OnDutyForm;
import com.microlabs.ess.form.PermissionForm;
import com.microlabs.hr.dao.SAPAttendenceDAO;
import com.microlabs.hr.form.HRLeaveForm;
import com.microlabs.hr.form.SAPAttendenceForm;
import com.microlabs.main.db.MainDao;
import com.microlabs.myrequest.form.MyRequestForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class HRLeaveAction extends DispatchAction{
	EssDao ad=new EssDao();
	
	public ActionForward displaycompbalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HRLeaveForm leaveForm=(HRLeaveForm)form;
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
		
			String empNo=request.getParameter("empNo");
			String plantid="";
			//get plant id
			
			String plant="select * from location where location_code in (select locid from emp_official_info where pernr='"+empNo+"' )";
			ResultSet g=ad.selectQuery(plant);
			try {
				if(g.next())
				{
					plantid=g.getString("locid");
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
					
        if(leaveType.equalsIgnoreCase("6"))
        {
       	       	
        	float comOpBal=0;
        	float comAvaiBal=0;
        	float comCloBal=0;
    
        	
        	///Get balance from comptable
        	String bal="select year(Start_Date) as year,sum(Number_of_Days) as openbal,sum(Comp_Off_Availed_Days) as avbal,sum(Comp_Off_Balance) as clobal from COMP_OT_SAP where Employee_Number='"+empNo+"'  and Comp_Off_Balance>0"
        			+ "  and  DATEDIFF(DAY,Start_Date,'"+startDate+"') <=(select comp_off_days  from location where locid='"+plantid+"') and '"+startDate+"'>=Start_Date group by Start_Date";
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
           	 String coun="select count(*) from lv_type_d where lv_empcode='"+empNo+"' and lv_typeid='6' and lv_calyear='"+yearrequird+"'";
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
       	 String insertinto="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_availed,lv_clbal,lv_awtBal) values(6,'"+yearrequird+"','"+empNo+"','"+comOpBal+"','"+comAvaiBal+"','"+comCloBal+"',0)";
       	 int j=ad.SqlExecuteUpdatePrmaryKeys(insertinto);
           	 }
           	 
           	 else
           	 {
          String update="update lv_type_d set lv_opbal="+comOpBal+",lv_clbal="+comCloBal+" where lv_empcode='"+empNo+"' and lv_calyear='"+yearrequird+"' and lv_typeid='6'";
          int j=ad.SqlExecuteUpdatePrmaryKeys(update);
           	 }

      
        }
        
     	displayLeaveBal(mapping, leaveForm, request, response);
		return mapping.findForward("leaveBal");	
	}
	
	public ActionForward displaycompofflist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HRLeaveForm leaveForm=(HRLeaveForm)form;
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
		
			String empNo=request.getParameter("empNo");
			String plantid="";
			//get plant id
			
			String plant="select * from location where location_code in (select locid from emp_official_info where pernr='"+empNo+"' )";
			ResultSet g=ad.selectQuery(plant);
			try {
				if(g.next())
				{
					plantid=g.getString("locid");
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

        if(leaveType.equalsIgnoreCase("6"))
        {
       	       	
        	float comOpBal=0;
        	float comAvaiBal=0;
        	float comCloBal=0;
    
        	ArrayList f=new ArrayList();
        	///Get balance from comptable
        	String bal="select convert(nvarchar(10),start_date,103) as start_date1,convert(nvarchar(10),DATEADD(day,(select comp_off_days  from location where locid='"+plantid+"'),Start_Date),103) as lapsydate,* from COMP_OT_SAP where Employee_Number='"+empNo+"'  and Comp_Off_Balance>0"
        			+ "  and  DATEDIFF(DAY,Start_Date,'"+startDate+"') <=(select comp_off_days  from location where locid='"+plantid+"') and "
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
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		HRLeaveForm approvalsForm = (HRLeaveForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		  String status=approvalsForm.getReqRequstType();
		  String type=approvalsForm.getSelectedFilter();
			int totalRecords=approvalsForm.getTotalRecords();//21
			int startRecord=approvalsForm.getStartRecord();//11
			int endRecord=approvalsForm.getEndRecord();	
		  if(status.equalsIgnoreCase("Leave"))
			 {
				LinkedList leaveList=new LinkedList();
			  String getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_R.Req_Id,leave.leave_type," +
				"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
				"emp_official_info as emp,leave_details as leave where leave.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' and all_R.Req_Type='Leave' and all_R.Requester_Name=emp.PERNR and leave.Req_Id=all_R.Req_Id) " +
				"as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";
				ResultSet rsLeave=ad.selectQuery(getLeaveRecords);
				try{
					while(rsLeave.next()){
						LeaveForm leave=new LeaveForm();
						leave.setRequestNumber(rsLeave.getInt("Req_Id"));
						
					String	leaveType=rsLeave.getString("leave_type");
						if(leaveType.equalsIgnoreCase("1")){
							leaveType="Casual";
						}
						if(leaveType.equalsIgnoreCase("2")){
							leaveType="Sick";
						}
						if(leaveType.equalsIgnoreCase("3")){
							leaveType="privilege";
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
						leave.setEmployeeName(rsLeave.getString("EMP_FULLNAME"));
						leave.setSubmitDate(rsLeave.getString("Req_Date"));
						leave.setStartDate(EMicroUtils.display(rsLeave.getDate("start_date")));
						String stDur=rsLeave.getString("start_duration");
						
						if(stDur.equalsIgnoreCase("FD")){
							stDur="Full Day";
						}
						if(stDur.equalsIgnoreCase("FH")){
							stDur="First Half";
						}
						if(stDur.equalsIgnoreCase("SH")){
							stDur="Second Half";
						}
						leave.setStartDurationType(stDur);
						
						leave.setEndDate(EMicroUtils.display(rsLeave.getDate("end_date")));
						
						String endDur=rsLeave.getString("end_duration");
						
						if(endDur.equalsIgnoreCase("FD")){
							endDur="Full Day";
						}
						if(endDur.equalsIgnoreCase("FH")){
							endDur="First Half";
						}
						if(endDur.equalsIgnoreCase("SH")){
							endDur="Second Half";
						}
						leave.setEndDurationType(endDur);
						leave.setNoOfDays(rsLeave.getString("no_of_days"));
						leave.setApproveStatus(rsLeave.getString("Req_Status"));
						leaveList.add(leave);
						}
					request.setAttribute("leaveList", leaveList);
					approvalsForm.setTotalRecords(totalRecords);
					  approvalsForm.setStartRecord(startRecord);
					  approvalsForm.setEndRecord(endRecord);
						request.setAttribute("nextButton", "nextButton");
						if(startRecord!=1)
						request.setAttribute("previousButton", "previousButton");
						request.setAttribute("displayRecordNo", "displayRecordNo");
						if(leaveList.size()<10)
						{
							approvalsForm.setStartRecord(1);
							request.setAttribute("previousButton", "");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						}catch (Exception e) {
							e.printStackTrace();
						}
			 }
		  if(status.equalsIgnoreCase("Permission"))
			 {
			  LinkedList permList=new LinkedList();
			  
			  String	getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Requester_Id) AS RowNum,all_R.Req_Id," +
				"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime from  All_Request as all_R," +
			"emp_official_info as emp, Permission_details as perm where perm.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' " +
			"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission') as  sub Where  " +
			"sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";				
					
		
			  ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
				try{
				while(rsPermission.next())
				{
					MyRequestForm permission=new MyRequestForm();
					permission.setRequestNumber(rsPermission.getString("Req_Id"));
		            permission.setStartTime(rsPermission.getString("startTime"));
			          permission.setEndTime(rsPermission.getString("endTime"))		;
		        	permission.setRequestType("Permission");
					String requestDate=rsPermission.getString("Req_Date");
					int isthere = (requestDate.indexOf("/"));
					if(isthere <= 0){
						String req[]=requestDate.split(" ");
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
					}
					permission.setCreateDate(requestDate);
					permission.setEmployeeName(rsPermission.getString("EMP_FULLNAME"));
					permission.setApprover(rsPermission.getString("Req_Status"));
					permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
					String url="";
					
					permission.setUrl(url);
					
					permList.add(permission);
						
				}
				request.setAttribute("Permissionlist", permList);
			
				if(permList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					
					
					if(permList.size()==0){
						request.setAttribute("no Permission records", "no Permission records");
						approvalsForm.setMessage3("No Records Found..");
					}
				
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 
				 
				 approvalsForm.setTotalRecords(totalRecords);
				  approvalsForm.setStartRecord(startRecord);
				  approvalsForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					if(startRecord!=1)
					request.setAttribute("previousButton", "previousButton");
					request.setAttribute("displayRecordNo", "displayRecordNo");
					if(permList.size()<10)
					{
						approvalsForm.setStartRecord(1);
						request.setAttribute("previousButton", "");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
					}catch (Exception e) {
						e.printStackTrace();
					}
			 }
		  if(status.equalsIgnoreCase("On Duty")){
				LinkedList ondutyList=new LinkedList();
			  String getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
				"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,onduty.start_date,onduty.end_date " +
				"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty where onduty.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' and all_R.Req_Id=onduty.request_no " +
				"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty') as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";
	ResultSet rsOnduty=ad.selectQuery(getOndutyRecords);
try{
	while(rsOnduty.next()){
	OnDutyForm onduty=new OnDutyForm();
	onduty.setRequestNumber(rsOnduty.getInt("Req_Id"));
	onduty.setEmployeeNumber(rsOnduty.getString("Requester_Name"));

	onduty.setOnDutyType(rsOnduty.getString("onDuty_Type"));
	onduty.setLocationId(rsOnduty.getString("location"));
	onduty.setEmployeeName(rsOnduty.getString("EMP_FULLNAME"));
	onduty.setSubmitDate(rsOnduty.getString("Req_Date"));
	onduty.setStartDate(EMicroUtils.display(rsOnduty.getDate("start_date")));
	onduty.setLocationId(rsOnduty.getString("location"));
	onduty.setStartTime(rsOnduty.getString("startTime"));
	onduty.setEndDate(EMicroUtils.display(rsOnduty.getDate("end_date")));
	
	
	onduty.setEndTime(rsOnduty.getString("endTime"));
	onduty.setApprover(rsOnduty.getString("Req_Status"));
	ondutyList.add(onduty);
	}
}catch (Exception e) {
e.printStackTrace();
}
request.setAttribute("ondutyList", ondutyList);
if(ondutyList.size()>0){
if(type.equalsIgnoreCase("Pending")){
	request.setAttribute("displayButton", "displayButton");
	}
}


if(ondutyList.size()==0){
	request.setAttribute("no OnDuty records", "no OnDuty records");
	approvalsForm.setMessage3("No Records Found..");
}

request.setAttribute("displayRecordNo", "displayRecordNo");
		  }
		return mapping.findForward("approvePage");
		
	}
	
	
	public ActionForward displayLeaveBal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm leaveForm=(HRLeaveForm)form;
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
		 String empNo=request.getParameter("empNo");
		leaveForm.setYear(year);
			String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+empNo+"' and t.lv_calyear='"+year+"' and t.lv_typeid=m.lv_typeid";
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
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm approvalsForm = (HRLeaveForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String type=approvalsForm.getSelectedFilter();
		if(type.equalsIgnoreCase("Pending")){
			request.setAttribute("displayButton", "displayButton");
			}
		int totalRecords=approvalsForm.getTotalRecords();//21
		int endRecord=approvalsForm.getStartRecord()-1;//20
		int startRecord=approvalsForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(1);
		approvalsForm.setEndRecord(10);
		  String status=approvalsForm.getReqRequstType();
		  if(status.equalsIgnoreCase("Leave"))
			 {
				LinkedList leaveList=new LinkedList();
			  String getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_R.Req_Id,leave.leave_type," +
				"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
				"emp_official_info as emp,leave_details as leave where leave.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' and all_R.Req_Type='Leave' and all_R.Requester_Name=emp.PERNR and leave.Req_Id=all_R.Req_Id) " +
				"as  sub Where  sub.RowNum between "+(totalRecords-9)+" and "+totalRecords+" order by Req_Id desc";
				ResultSet rsLeave=ad.selectQuery(getLeaveRecords);
				try{
					while(rsLeave.next()){
						LeaveForm leave=new LeaveForm();
						leave.setRequestNumber(rsLeave.getInt("Req_Id"));
						
					String	leaveType=rsLeave.getString("leave_type");
						if(leaveType.equalsIgnoreCase("1")){
							leaveType="Casual";
						}
						if(leaveType.equalsIgnoreCase("2")){
							leaveType="Sick";
						}
						if(leaveType.equalsIgnoreCase("3")){
							leaveType="privilege";
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
						leave.setEmployeeName(rsLeave.getString("EMP_FULLNAME"));
						leave.setSubmitDate(rsLeave.getString("Req_Date"));
						leave.setStartDate(EMicroUtils.display(rsLeave.getDate("start_date")));
						String stDur=rsLeave.getString("start_duration");
						
						if(stDur.equalsIgnoreCase("FD")){
							stDur="Full Day";
						}
						if(stDur.equalsIgnoreCase("FH")){
							stDur="First Half";
						}
						if(stDur.equalsIgnoreCase("SH")){
							stDur="Second Half";
						}
						leave.setStartDurationType(stDur);
						
						leave.setEndDate(EMicroUtils.display(rsLeave.getDate("end_date")));
						
						String endDur=rsLeave.getString("end_duration");
						
						if(endDur.equalsIgnoreCase("FD")){
							endDur="Full Day";
						}
						if(endDur.equalsIgnoreCase("FH")){
							endDur="First Half";
						}
						if(endDur.equalsIgnoreCase("SH")){
							endDur="Second Half";
						}
						leave.setEndDurationType(endDur);
						leave.setNoOfDays(rsLeave.getString("no_of_days"));
						leave.setApproveStatus(rsLeave.getString("Req_Status"));
						leaveList.add(leave);
						}
					request.setAttribute("leaveList", leaveList);
					approvalsForm.setTotalRecords(totalRecords);
					  approvalsForm.setStartRecord(startRecord);
					  approvalsForm.setEndRecord(endRecord);
						request.setAttribute("nextButton", "nextButton");
						if(startRecord!=1)
						request.setAttribute("previousButton", "previousButton");
						request.setAttribute("displayRecordNo", "displayRecordNo");
						if(leaveList.size()<10)
						{
							approvalsForm.setStartRecord(1);
							request.setAttribute("previousButton", "");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						}catch (Exception e) {
							e.printStackTrace();
						}
			 }
		  if(status.equalsIgnoreCase("Permission"))
			 {
			  LinkedList permList=new LinkedList();
			  
			  String	getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Requester_Id) AS RowNum,all_R.Req_Id," +
				"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime from  All_Request as all_R," +
			"emp_official_info as emp, Permission_details as perm where perm.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' " +
			"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission') as  sub Where  " +
			"sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";				
					
		
			  ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
				try{
				while(rsPermission.next())
				{
					MyRequestForm permission=new MyRequestForm();
					permission.setRequestNumber(rsPermission.getString("Req_Id"));
		            permission.setStartTime(rsPermission.getString("startTime"));
			          permission.setEndTime(rsPermission.getString("endTime"))		;
		        	permission.setRequestType("Permission");
					String requestDate=rsPermission.getString("Req_Date");
					int isthere = (requestDate.indexOf("/"));
					if(isthere <= 0){
						String req[]=requestDate.split(" ");
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
					}
					permission.setCreateDate(requestDate);
					permission.setEmployeeName(rsPermission.getString("EMP_FULLNAME"));
					permission.setApprover(rsPermission.getString("Req_Status"));
					permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
					String url="";
					
					permission.setUrl(url);
					
					permList.add(permission);
						
				}
				request.setAttribute("Permissionlist", permList);
			
				if(permList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					
					
					if(permList.size()==0){
						request.setAttribute("no Permission records", "no Permission records");
						approvalsForm.setMessage3("No Records Found..");
					}
				
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 
				 
				 approvalsForm.setTotalRecords(totalRecords);
				  approvalsForm.setStartRecord(startRecord);
				  approvalsForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					if(startRecord!=1)
					request.setAttribute("previousButton", "previousButton");
					request.setAttribute("displayRecordNo", "displayRecordNo");
					if(permList.size()<10)
					{
						approvalsForm.setStartRecord(1);
						request.setAttribute("previousButton", "");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
					}catch (Exception e) {
						e.printStackTrace();
					}
			 }
		  if(status.equalsIgnoreCase("On Duty")){
				LinkedList ondutyList=new LinkedList();
			  String getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
				"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,onduty.start_date,onduty.end_date " +
				"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty where onduty.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' and all_R.Req_Id=onduty.request_no " +
				"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty') as  sub Where  sub.RowNum between "+(totalRecords-9)+" and "+totalRecords+" order by Req_Id desc";
	ResultSet rsOnduty=ad.selectQuery(getOndutyRecords);
try{
	while(rsOnduty.next()){
	OnDutyForm onduty=new OnDutyForm();
	onduty.setRequestNumber(rsOnduty.getInt("Req_Id"));
	onduty.setEmployeeNumber(rsOnduty.getString("Requester_Name"));

	onduty.setOnDutyType(rsOnduty.getString("onDuty_Type"));
	onduty.setLocationId(rsOnduty.getString("location"));
	onduty.setEmployeeName(rsOnduty.getString("EMP_FULLNAME"));
	onduty.setSubmitDate(rsOnduty.getString("Req_Date"));
	onduty.setStartDate(EMicroUtils.display(rsOnduty.getDate("start_date")));
	onduty.setLocationId(rsOnduty.getString("location"));
	onduty.setStartTime(rsOnduty.getString("startTime"));
	onduty.setEndDate(EMicroUtils.display(rsOnduty.getDate("end_date")));
	
	
	onduty.setEndTime(rsOnduty.getString("endTime"));
	onduty.setApprover(rsOnduty.getString("Req_Status"));
	ondutyList.add(onduty);
	}
}catch (Exception e) {
e.printStackTrace();
}
request.setAttribute("ondutyList", ondutyList);
if(ondutyList.size()>0){
if(type.equalsIgnoreCase("Pending")){
	request.setAttribute("displayButton", "displayButton");
	}
}


if(ondutyList.size()==0){
	request.setAttribute("no OnDuty records", "no OnDuty records");
	approvalsForm.setMessage3("No Records Found..");
}

request.setAttribute("displayRecordNo", "displayRecordNo");
		  }
	
		return mapping.findForward("approvePage");
	}
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm approvalsForm = (HRLeaveForm) form;
			
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int userID=user.getId();
			String type=approvalsForm.getSelectedFilter();
			if(type.equalsIgnoreCase("Pending")){
				request.setAttribute("displayButton", "displayButton");
				}
			try{
				int totalRecords=approvalsForm.getTotalRecords();//21
				int startRecord=approvalsForm.getStartRecord();//11
				int endRecord=approvalsForm.getEndRecord();	
				
				
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

				  String status=approvalsForm.getReqRequstType();
				  if(status.equalsIgnoreCase("Leave"))
					 {
						LinkedList leaveList=new LinkedList();
					  String getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_R.Req_Id,leave.leave_type," +
						"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
						"emp_official_info as emp,leave_details as leave where leave.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' and all_R.Req_Type='Leave' and all_R.Requester_Name=emp.PERNR and leave.Req_Id=all_R.Req_Id) " +
						"as  sub Where  sub.RowNum between "+(totalRecords-9)+" and "+totalRecords+" order by Req_Id desc";
						ResultSet rsLeave=ad.selectQuery(getLeaveRecords);
						try{
							while(rsLeave.next()){
								LeaveForm leave=new LeaveForm();
								leave.setRequestNumber(rsLeave.getInt("Req_Id"));
								
							String	leaveType=rsLeave.getString("leave_type");
								if(leaveType.equalsIgnoreCase("1")){
									leaveType="Casual";
								}
								if(leaveType.equalsIgnoreCase("2")){
									leaveType="Sick";
								}
								if(leaveType.equalsIgnoreCase("3")){
									leaveType="privilege";
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
								leave.setEmployeeName(rsLeave.getString("EMP_FULLNAME"));
								leave.setSubmitDate(rsLeave.getString("Req_Date"));
								leave.setStartDate(EMicroUtils.display(rsLeave.getDate("start_date")));
								String stDur=rsLeave.getString("start_duration");
								
								if(stDur.equalsIgnoreCase("FD")){
									stDur="Full Day";
								}
								if(stDur.equalsIgnoreCase("FH")){
									stDur="First Half";
								}
								if(stDur.equalsIgnoreCase("SH")){
									stDur="Second Half";
								}
								leave.setStartDurationType(stDur);
								
								leave.setEndDate(EMicroUtils.display(rsLeave.getDate("end_date")));
								
								String endDur=rsLeave.getString("end_duration");
								
								if(endDur.equalsIgnoreCase("FD")){
									endDur="Full Day";
								}
								if(endDur.equalsIgnoreCase("FH")){
									endDur="First Half";
								}
								if(endDur.equalsIgnoreCase("SH")){
									endDur="Second Half";
								}
								leave.setEndDurationType(endDur);
								leave.setNoOfDays(rsLeave.getString("no_of_days"));
								leave.setApproveStatus(rsLeave.getString("Req_Status"));
								leaveList.add(leave);
								}
							if(leaveList.size()!=0)
							{
								approvalsForm.setTotalRecords(totalRecords);
								approvalsForm.setStartRecord(startRecord);
								approvalsForm.setEndRecord(endRecord);
								request.setAttribute("nextButton", "nextButton");
								request.setAttribute("previousButton", "previousButton");
							}
							else
							{
								int start=startRecord;
								int end=startRecord;
								
								approvalsForm.setTotalRecords(totalRecords);
								approvalsForm.setStartRecord(start);
								approvalsForm.setEndRecord(end);
								
							}
						 if(leaveList.size()<10)
						 {
							 approvalsForm.setTotalRecords(totalRecords);
							 approvalsForm.setStartRecord(startRecord);
							 approvalsForm.setEndRecord(startRecord+leaveList.size()-1);
								request.setAttribute("nextButton", "");
								request.setAttribute("disableNextButton", "disableNextButton");
								request.setAttribute("previousButton", "previousButton"); 
							 
						 }
								}catch (Exception e) {
									e.printStackTrace();
								}
					 }
				  if(status.equalsIgnoreCase("Permission"))
					 {
					  LinkedList permList=new LinkedList();
					  
					  String	getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Requester_Id) AS RowNum,all_R.Req_Id," +
						"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime from  All_Request as all_R," +
					"emp_official_info as emp, Permission_details as perm where perm.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' " +
					"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission') as  sub Where  " +
					"sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";				
							
				
					  ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
						try{
						while(rsPermission.next())
						{
							MyRequestForm permission=new MyRequestForm();
							permission.setRequestNumber(rsPermission.getString("Req_Id"));
				            permission.setStartTime(rsPermission.getString("startTime"));
					          permission.setEndTime(rsPermission.getString("endTime"))		;
				        	permission.setRequestType("Permission");
							String requestDate=rsPermission.getString("Req_Date");
							int isthere = (requestDate.indexOf("/"));
							if(isthere <= 0){
								String req[]=requestDate.split(" ");
								requestDate=req[0];
								String a[]=requestDate.split("-");
								requestDate=a[2]+"/"+a[1]+"/"+a[0];
							}
							permission.setCreateDate(requestDate);
							permission.setEmployeeName(rsPermission.getString("EMP_FULLNAME"));
							permission.setApprover(rsPermission.getString("Req_Status"));
							permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
							String url="";
							
							permission.setUrl(url);
							
							permList.add(permission);
								
						}
						request.setAttribute("Permissionlist", permList);
					
						if(permList.size()>0){
							if(type.equalsIgnoreCase("Pending")){
								request.setAttribute("displayButton", "displayButton");
								}
							}
							
							if(permList.size()==0){
								request.setAttribute("no Permission records", "no Permission records");
								approvalsForm.setMessage3("No Records Found..");
							}
							
							if(permList.size()!=0)
							{
								approvalsForm.setTotalRecords(totalRecords);
								approvalsForm.setStartRecord(startRecord);
								approvalsForm.setEndRecord(endRecord);
								request.setAttribute("nextButton", "nextButton");
								request.setAttribute("previousButton", "previousButton");
							}
							else
							{
								int start=startRecord;
								int end=startRecord;
								
								approvalsForm.setTotalRecords(totalRecords);
								approvalsForm.setStartRecord(start);
								approvalsForm.setEndRecord(end);
								
							}
						 if(permList.size()<10)
						 {
							 approvalsForm.setTotalRecords(totalRecords);
							 approvalsForm.setStartRecord(startRecord);
							 approvalsForm.setEndRecord(startRecord+permList.size()-1);
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
					 }
				  if(status.equalsIgnoreCase("On Duty")){
						LinkedList ondutyList=new LinkedList();
					  String getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
						"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,onduty.start_date,onduty.end_date " +
						"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty where onduty.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' and all_R.Req_Id=onduty.request_no " +
						"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty') as  sub Where  sub.RowNum between "+(totalRecords-9)+" and "+totalRecords+" order by Req_Id desc";
			ResultSet rsOnduty=ad.selectQuery(getOndutyRecords);
		
			while(rsOnduty.next()){
			OnDutyForm onduty=new OnDutyForm();
			onduty.setRequestNumber(rsOnduty.getInt("Req_Id"));
			onduty.setEmployeeNumber(rsOnduty.getString("Requester_Name"));
		
			onduty.setOnDutyType(rsOnduty.getString("onDuty_Type"));
			onduty.setLocationId(rsOnduty.getString("location"));
			onduty.setEmployeeName(rsOnduty.getString("EMP_FULLNAME"));
			onduty.setSubmitDate(rsOnduty.getString("Req_Date"));
			onduty.setStartDate(EMicroUtils.display(rsOnduty.getDate("start_date")));
			onduty.setLocationId(rsOnduty.getString("location"));
			onduty.setStartTime(rsOnduty.getString("startTime"));
			onduty.setEndDate(EMicroUtils.display(rsOnduty.getDate("end_date")));
			
			
			onduty.setEndTime(rsOnduty.getString("endTime"));
			onduty.setApprover(rsOnduty.getString("Req_Status"));
			ondutyList.add(onduty);
			}
			if(ondutyList.size()>0){
				if(type.equalsIgnoreCase("Pending")){
					request.setAttribute("displayButton", "displayButton");
					}
				}
				
				if(ondutyList.size()==0){
					request.setAttribute("no OnDuty records", "no OnDuty records");
					approvalsForm.setMessage3("No Records Found..");
				}
				
				if(ondutyList.size()!=0)
				{
					approvalsForm.setTotalRecords(totalRecords);
					approvalsForm.setStartRecord(startRecord);
					approvalsForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					approvalsForm.setTotalRecords(totalRecords);
					approvalsForm.setStartRecord(start);
					approvalsForm.setEndRecord(end);
					
				}
			 if(ondutyList.size()<10)
			 {
				 approvalsForm.setTotalRecords(totalRecords);
				 approvalsForm.setStartRecord(startRecord);
				 approvalsForm.setEndRecord(startRecord+ondutyList.size()-1);
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
				  }
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("approvePage");
	}
	public ActionForward pendingRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm approvalsForm = (HRLeaveForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String reqType=approvalsForm.getReqRequstType();
		String type=approvalsForm.getSelectedFilter();
		int totalRecords = 0;
		 if(reqType.equalsIgnoreCase("Permission"))
		 {
			 LinkedList permList=new LinkedList();
				String getCount="select count(*) from Permission_details where HR_id='"+user.getEmployeeNo()+"' and Approver_Status='0'";
				ResultSet rsCount=ad.selectQuery(getCount);
				try{
				while(rsCount.next()){
					totalRecords=rsCount.getInt(1);
				}
				int  startRecord=0;
				  int  endRecord=0;
					 if(totalRecords>10)
					 {
						 approvalsForm.setTotalRecords(totalRecords);
					 startRecord=1;
					 endRecord=10;
					 approvalsForm.setStartRecord(1);
					 approvalsForm.setEndRecord(10);
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					 request.setAttribute("displayRecordNo", "displayRecordNo");
					 request.setAttribute("nextButton", "nextButton");
					 }else
					 {
						  startRecord=1;
						  endRecord=totalRecords;
						  approvalsForm.setTotalRecords(totalRecords);
						  approvalsForm.setStartRecord(1);
						  approvalsForm.setEndRecord(totalRecords); 
					 }
					 
				}catch (Exception e) {
				e.printStackTrace();
				}
				if(type.equalsIgnoreCase("Pending")){
				String	getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Requester_Id) AS RowNum,all_R.Req_Id," +
				"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime from  All_Request as all_R," +
			"emp_official_info as emp, Permission_details as perm where perm.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' " +
			"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission') as  sub Where  " +
			"sub.RowNum between "+(totalRecords-9)+" and "+totalRecords+" order by Req_Id desc";				
				ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
				try{
				while(rsPermission.next())
				{
					MyRequestForm permission=new MyRequestForm();
					permission.setRequestNumber(rsPermission.getString("Req_Id"));
		            permission.setStartTime(rsPermission.getString("startTime"));
			          permission.setEndTime(rsPermission.getString("endTime"));
		        	permission.setRequestType("Permission");
					String requestDate=rsPermission.getString("Req_Date");
					int isthere = (requestDate.indexOf("/"));
					if(isthere <= 0){
						String req[]=requestDate.split(" ");
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
					}
					permission.setCreateDate(requestDate);
					permission.setEmployeeName(rsPermission.getString("EMP_FULLNAME"));
					permission.setApprover(rsPermission.getString("Req_Status"));
					permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
					String url="";
					permission.setUrl(url);
					permList.add(permission);
						
				}
				
				if(permList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						request.setAttribute("Permissionlist", permList);
						}
					}
					
					if(permList.size()==0){
						request.setAttribute("no Material Master records", "no Material Master records");
						approvalsForm.setMessage3("No Records Found..");
					}
					}catch (Exception e) {
						e.printStackTrace();
					}
					
				}		
		 }
		if(reqType.equalsIgnoreCase("On Duty")){
			LinkedList ondutyList=new LinkedList();
			String getCount="select count(*) from OnDuty_details where HR_id='"+user.getEmployeeNo()+"' and Approver_Status='In Process'";
			ResultSet rsCount=ad.selectQuery(getCount);
			try{
			while(rsCount.next()){
				totalRecords=rsCount.getInt(1);
			}
			int  startRecord=0;
			  int  endRecord=0;
				 if(totalRecords>10)
				 {
					 approvalsForm.setTotalRecords(totalRecords);
				 startRecord=1;
				 endRecord=10;
				 approvalsForm.setStartRecord(1);
				 approvalsForm.setEndRecord(10);
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  approvalsForm.setTotalRecords(totalRecords);
					  approvalsForm.setStartRecord(1);
					  approvalsForm.setEndRecord(totalRecords); 
				 }
				 
			}catch (Exception e) {
			e.printStackTrace();
			}
			String getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
						"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,onduty.start_date,onduty.end_date " +
						"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty where onduty.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' and all_R.Req_Id=onduty.request_no " +
						"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty') as  sub Where  sub.RowNum between "+(totalRecords-9)+" and "+totalRecords+" order by Req_Id desc";
			ResultSet rsOnduty=ad.selectQuery(getOndutyRecords);
			try{
			while(rsOnduty.next()){
			OnDutyForm onduty=new OnDutyForm();
			onduty.setRequestNumber(rsOnduty.getInt("Req_Id"));
			onduty.setEmployeeNumber(rsOnduty.getString("Requester_Name"));
		
			onduty.setOnDutyType(rsOnduty.getString("onDuty_Type"));
			onduty.setLocationId(rsOnduty.getString("location"));
			onduty.setEmployeeName(rsOnduty.getString("EMP_FULLNAME"));
			onduty.setSubmitDate(rsOnduty.getString("Req_Date"));
			onduty.setStartDate(EMicroUtils.display(rsOnduty.getDate("start_date")));
			onduty.setLocationId(rsOnduty.getString("location"));
			onduty.setStartTime(rsOnduty.getString("startTime"));
			onduty.setEndDate(EMicroUtils.display(rsOnduty.getDate("end_date")));
			
			
			onduty.setEndTime(rsOnduty.getString("endTime"));
			onduty.setApprover(rsOnduty.getString("Req_Status"));
			ondutyList.add(onduty);
			}
			if(ondutyList.size()>0){
			if(type.equalsIgnoreCase("Pending")){
				request.setAttribute("displayButton", "displayButton");
				}
			request.setAttribute("ondutyList", ondutyList);
			}
		
			if(ondutyList.size()==0){
				request.setAttribute("no OnDuty records", "no OnDuty records");
				approvalsForm.setMessage3("No Records Found..");
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(reqType.equalsIgnoreCase("Leave")){
			LinkedList leaveList=new LinkedList();
			String getCount="select count(*) from leave_details where HR_id='"+user.getEmployeeNo()+"' and Approvel_Status='Pending'";
			ResultSet rsCount=ad.selectQuery(getCount);
			try{
			while(rsCount.next()){
				totalRecords=rsCount.getInt(1);
			}
			int  startRecord=0;
			  int  endRecord=0;
				 if(totalRecords>10)
				 {
					 approvalsForm.setTotalRecords(totalRecords);
				 startRecord=1;
				 endRecord=10;
				 approvalsForm.setStartRecord(1);
				 approvalsForm.setEndRecord(10);
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  approvalsForm.setTotalRecords(totalRecords);
					  approvalsForm.setStartRecord(1);
					  approvalsForm.setEndRecord(totalRecords); 
				 }
			}catch (Exception e) {
			e.printStackTrace();
			}
			
			String getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_R.Req_Id,leave.leave_type," +
			"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
			"emp_official_info as emp,leave_details as leave where leave.HR_id='"+user.getEmployeeNo()+"' and Req_Status='"+type+"' and all_R.Req_Type='Leave' and all_R.Requester_Name=emp.PERNR and leave.Req_Id=all_R.Req_Id) " +
			"as  sub Where  sub.RowNum between "+(totalRecords-9)+" and "+totalRecords+" order by Req_Id desc";
			ResultSet rsLeave=ad.selectQuery(getLeaveRecords);
			try{
				while(rsLeave.next()){
					LeaveForm leave=new LeaveForm();
					leave.setRequestNumber(rsLeave.getInt("Req_Id"));
					
				String	leaveType=rsLeave.getString("leave_type");
					if(leaveType.equalsIgnoreCase("1")){
						leaveType="Casual";
					}
					if(leaveType.equalsIgnoreCase("2")){
						leaveType="Sick";
					}
					if(leaveType.equalsIgnoreCase("3")){
						leaveType="privilege";
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
					leave.setEmployeeName(rsLeave.getString("EMP_FULLNAME"));
					leave.setSubmitDate(rsLeave.getString("Req_Date"));
					leave.setStartDate(EMicroUtils.display(rsLeave.getDate("start_date")));
					String stDur=rsLeave.getString("start_duration");
					
					if(stDur.equalsIgnoreCase("FD")){
						stDur="Full Day";
					}
					if(stDur.equalsIgnoreCase("FH")){
						stDur="First Half";
					}
					if(stDur.equalsIgnoreCase("SH")){
						stDur="Second Half";
					}
					leave.setStartDurationType(stDur);
					
					leave.setEndDate(EMicroUtils.display(rsLeave.getDate("end_date")));
					
					String endDur=rsLeave.getString("end_duration");
					
					if(endDur.equalsIgnoreCase("FD")){
						endDur="Full Day";
					}
					if(endDur.equalsIgnoreCase("FH")){
						endDur="First Half";
					}
					if(endDur.equalsIgnoreCase("SH")){
						endDur="Second Half";
					}
					leave.setEndDurationType(endDur);
					leave.setNoOfDays(rsLeave.getString("no_of_days"));
					leave.setApproveStatus(rsLeave.getString("Req_Status"));
					leaveList.add(leave);
					}
					if(leaveList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					request.setAttribute("leaveList", leaveList);
					}
				
					if(leaveList.size()==0){
						request.setAttribute("no Leave records", "no Leave records");
						approvalsForm.setMessage3("No Records Found..");
					}
					}catch (Exception e) {
						e.printStackTrace();
					}
		}
		return mapping.findForward("approvePage");
	}
	
	public ActionForward displayPendingReq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm approvalsForm = (HRLeaveForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		return mapping.findForward("approvePage");
	}
	public ActionForward savePermission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HRLeaveForm permissionform = (HRLeaveForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 	
		String empNo=permissionform.getEmpNumber();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		try{
			Permission lA1=new Permission();
			LinkedList appList1= lA1.getApprovers(request, response);
			if(appList1.size()==0)
				permissionform.setMessage2("No Approvers assigned.Please Contact Admin");
			if(appList1.size()>0){
			synchronized (this) {
				
			
			int requestNo=0;
			String getRequstNo="select max(request_no) from Permission_details";
			ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
			while(rsRequestNo.next())
			{
				requestNo=rsRequestNo.getInt(1);
			}
			requestNo=requestNo+1;
			
			permissionform.setRequestNumber(requestNo);
			String type=request.getParameter("param");
			String startDate=permissionform.getDate();
			String a[]=startDate.split("/");
			 
			startDate=a[2]+"-"+a[1]+"-"+a[0];
		
				  
			 	
			 	int approver1=0;
				String approvermail="";
				String pApprover="";
				 String dateNow=permissionform.getDate();
			    
			    String reqdate=permissionform.getDate();
			    
				String apprmgr="";
				String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
						+ "ess.employeeNumber='"+empNo+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
				ResultSet approverRS=ad.selectQuery(getApproverID);
				if(approverRS.next()){
					
					apprmgr = approverRS.getString("ApproverId");
					
				}	
				
				 String[] monthName = {"","JAN", "FEB",
						  "MAR", "APR", "MAY", "JUN", "JUL",
						  "AUG", "SEP", "OCT", "NOV",
						  "DEC"
						  };
				 String f[]=reqdate.split("/");
				 reqdate=f[1];
				 
				//check nof permission in month
				 
				 
				 if(permissionform.getType().equalsIgnoreCase("Late") || permissionform.getType().equalsIgnoreCase("Early") )
				 {
				  String allow="";
				  
				  String chek="select case when count(*)>=max(Permission_count) then 0 else 1 end as allow from Permission_details,location where type in ('"+permissionform.getType()+"')"
				  		+ " and Approver_Status!=2 and month(date)='"+reqdate+"' and user_id='"+empNo+"' and location.LOCID='"+user.getPlantId()+"'";
				  ResultSet d=ad.selectQuery(chek);
				 if(d.next())
				 {
					 allow=d.getString("allow");
				 }
				/*  
				 if(allow.equalsIgnoreCase("0"))
				 {
					 permissionform.setMessage2("No. of Permission Count Exceeded for the Month...");
					 permission(mapping, permissionform, request, response);
						return mapping.findForward("permission");
				 }*/
				 
				 
				 }
				 
				 ///max hrs limit  early
				 String allowhrs="";
				
		
               
               String maX=" select case when isnull( sum( datediff(MINUTE,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+(left(starttime,5)+'"
               		+ " '+right(starttime,2)), 120) ,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+(left(endTime,5)+' '+right(endTime,2)), 120)) ),0)+"
               		+ " isnull(sum( datediff(MINUTE,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ (left('"+permissionform.getStartTime()+"',5)+' '+right('"+permissionform.getStartTime()+"',2)), 120) ,"
               		+ "CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ (left('"+permissionform.getEndTime()+"',5)+' '+right('"+permissionform.getEndTime()+"',2)), 120)) ),0)   >max(Permission_Hrs*60)"
               		+ " then 0 else 1 end allow  from location left outer join Permission_details on type in ('Early') and Approver_Status!=2 and month(date)='"+reqdate+"' and"
               		+ " user_id='"+empNo+"'  where location.LOCID='"+user.getPlantId()+"'";
               
               ResultSet jj=ad.selectQuery(maX);
               if(jj.next())
               {
            	   allowhrs= jj.getString("allow");
            	   
               }
               
           /*    if(allowhrs.equalsIgnoreCase("0"))
               {
            	   permissionform.setMessage2("No. of Permission Hours Exceeded for the Month...");
					 permission(mapping, permissionform, request, response);
						return mapping.findForward("permission");
               }*/
               
           
               
               
				
				 String startMonth=f[1];
				
					int staMont=Integer.parseInt(startMonth);
			
				  Calendar cal = Calendar.getInstance();
				  startMonth = monthName[staMont];
				  System.out.println("startMonth name: " + startMonth);
				 int count=0;
				 
				 String userLocation="";
					String userLoc="select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"'";
					ResultSet rsloc=ad.selectQuery(userLoc);
					while(rsloc.next()){
						userLocation=rsloc.getString("LOCATION_CODE");
					}
					String tableName=getTableName(userLocation);
				  
				  String getCountQuery="select count("+startMonth+") from "+tableName+" where (day = "+f[0]+") and "+startMonth+" ='W' and "+startMonth+" !=''";
					ResultSet rsCount=ad.selectQuery(getCountQuery);
					while(rsCount.next())
					{
						count=rsCount.getInt(1);
					}
					if(count==0)
					{
						permissionform.setMessage2("Error...Please check the date");
					}
					else
					{
				
				
				int test=0;
			String date="select Count(*) from Permission_details where date='"+startDate+"' and user_id='"+empNo+"'";
			ResultSet rs2=ad.selectQuery(date);
			while(rs2.next())
			{
				test=rs2.getInt(1);
			}
			if(test>0){ 
				try{
					
				boolean check= false;
				
				String getAppliedDates="Select * from Permission_details where date='"+startDate+"' and user_id='"+empNo+"'";
				ResultSet rsAppDate=ad.selectQuery(getAppliedDates);
				while(rsAppDate.next())
				{
					String fromTime=rsAppDate.getString("startTime");
					String fromTimeCycle=fromTime.substring(5, 7);

				}
				
				if(check==false)
				{
					//insert query
					String Permissions = "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type)" +
					" values ('"+permissionform.getRequestNumber()+"','"+empNo+"','"+startDate+"','"+permissionform.getStartTime()+"','"+permissionform.getReason()+"','"+user.getFirstName()+"','0','','','"+pApprover+"','"+permissionform.getEndTime()+"','"+apprmgr+"','','','"+permissionform.getType()+"','"+permissionform.getSwipetype()+"')";
		
			int i=0;
			i=ad.SqlExecuteUpdate(Permissions);
			
			if(i>0)
			{
		   		EMailer email = new EMailer();
				permissionform.setMessage("Permission submitted saved with  request number='"+requestNo+"' ");
				
				
				int	ij = email.sendMailToOffice_EmpApprover(request, approvermail,Integer.toString(permissionform.getRequestNumber()), "Permission",empNo);
				
				permission(mapping, form, request, response);
				
				
			}else{
				permissionform.setMessage2("Error...Permission not Send");
				
			}
					
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}else{
				//insert query
				
				String Permissions = "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type)" +
				" values ('"+permissionform.getRequestNumber()+"','"+empNo+"','"+startDate+"','"+permissionform.getStartTime()+"','"+permissionform.getReason()+"','"+user.getFirstName()+"','0','','','"+pApprover+"','"+permissionform.getEndTime()+"','"+apprmgr+"','','','"+permissionform.getType()+"','"+permissionform.getSwipetype()+"')";
	
		int i=0;
		i=ad.SqlExecuteUpdate(Permissions);
		
		if(i>0)
		{
	   		EMailer email = new EMailer();
			permissionform.setMessage("Permission request submitted with  request number='"+requestNo+"' ");
			int	ij = email.sendMailToOffice_EmpApprover(request, approvermail,Integer.toString(permissionform.getRequestNumber()), "Permission",empNo);
			
			permission(mapping, form, request, response);
			
			
		}else{
			permissionform.setMessage2("Error...Permission not Send");
		}
				
			}
	//insert into All_Request and send mail to approver
	boolean existStatus=false;		
	String reqType="";
	String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' group by reqType";
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
	
		e1.printStackTrace();
	}
	if(existStatus==true){
		if(reqType.equalsIgnoreCase("sequential"))
		{
		String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
		ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
			try {
				while(rsApproverDsg.next())
				{
					pApprover=rsApproverDsg.getString("ApproverId");
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			int i=0;
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+empNo+"','"+dateNow+"','Pending','','"+pApprover+"','','"+empNo+"','')";
			 i=ad.SqlExecuteUpdate(saveRecReq);
			 if(i > 0){
				}
		}
		if(reqType.equalsIgnoreCase("parallel"))
		{
			String parallelApprover1="";
			String parallelApprover2="";
			String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
			ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				try {
					while(rsApproverDsg.next())
					{
						pApprover=rsApproverDsg.getString("ApproverId");
						parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
						parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
						
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				int i=0;
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
				saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+empNo+"','"+dateNow+"','Pending','','"+pApprover+"','','"+empNo+"','')";
				 i=ad.SqlExecuteUpdate(saveRecReq);
				if(!(parallelApprover1.equalsIgnoreCase("")))
				{
					saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+empNo+"','"+dateNow+"','Pending','','"+parallelApprover1+"','','"+empNo+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
				}
				if(!(parallelApprover2.equalsIgnoreCase("")))
				{
					saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+empNo+"','"+dateNow+"','Pending','','"+parallelApprover2+"','','"+empNo+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
				}
				
		}
	}
	int i=0;
	if(existStatus==false){

		String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
	saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+empNo+"','"+dateNow+"','Pending','','"+pApprover+"','','"+empNo+"','')";
	 i=ad.SqlExecuteUpdate(saveRecReq);
	}
	}
			}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		   LeaveAction lA = new LeaveAction();
		LinkedList appList= lA.getApprovers(request, response);
		String getApproverDesig= "select top 1 2 as ord, hr_no, emp.EMP_FULLNAME,desg.DSGSTXT  from ess_cancel_appr e, emp_official_info as emp,DESIGNATION as desg where  "
				+ " Location=(select LOCATION_CODE from Location where LOCID="+user.getPlantId()+")  and  e.hr_no=emp.PERNR and emp.DSGID=desg.DSGID order by 1 ";
		ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
			try {
				while(rsApproverDsg.next())
				{
					LeaveForm leaveForm1=new LeaveForm();
					leaveForm1.setApptype("Approving Manager");
					leaveForm1.setApproverID(rsApproverDsg.getString("HR_NO"));
					leaveForm1.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					leaveForm1.setApproverName(rsApproverDsg.getString("EMP_FULLNAME"));
					appList.add(leaveForm1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		request.setAttribute("appList", appList);
				return mapping.findForward("permission");
		
		
		
	}
	public ActionForward permission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		 HRLeaveForm permissionform=(HRLeaveForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	String empNo=permissionform.getEmpNumber();
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		try{
			String hrLocID=user.getPlantId();
			 boolean checkEmpExist=false;
			 String checkEmpStatus="select * from emp_official_info as emp,Location as loc where PERNR='"+empNo+"' and loc.LOCID='"+hrLocID+"'";
			 ResultSet rsEmpStatus=ad.selectQuery(checkEmpStatus);
			
				 while(rsEmpStatus.next()){
					 permissionform.setEmpName(empNo+" - "+rsEmpStatus.getString("EMP_FULLNAME"));
					 checkEmpExist=true;
				 }
			if(checkEmpExist==false){
				permissionform.setEmpStatus("Employee Number Not Exist Or Invalid Employee Number.Please Check");
				return mapping.findForward("search");
			}
			permissionform.setDate("");
			permissionform.setStartTime("");
			permissionform.setEndTime("");
			permissionform.setReason("");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		HRLeaveAction lA=new HRLeaveAction();
		LinkedList appList= lA.getApprovers(request, response,empNo,"Permission");
		request.setAttribute("appList", appList);
				return mapping.findForward("permission");
				
	}
	
	public LinkedList getApproverDetails(String OldrequestNo, ActionForm form, HttpServletRequest request, String pApprover,String empNo)
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList approverList=new LinkedList();
	
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		 String dateNow = ft.format(dNow);
		 
		HRLeaveForm leaveForm=(HRLeaveForm)form;
		
		String saveRecReq="";
		boolean existStatus=false;
		String reqType="";
		String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' group by reqType";
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
		
			e1.printStackTrace();
		}
		synchronized (this) {
		if(existStatus==true){
			if(reqType.equalsIgnoreCase("sequential"))
			{
			String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
			ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
				try {
					while(rsApproverDsg.next())
					{
						pApprover=rsApproverDsg.getString("ApproverId");
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				int i=0;
				 saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
				saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+empNo+"','"+dateNow+"','Pending','','"+pApprover+"','No','"+empNo+"','')";
				 i=ad.SqlExecuteUpdate(saveRecReq);
				 if(i > 0){
						leaveForm.setMessage("Onduty Details Submitted Successfully");
					}
			}
			if(reqType.equalsIgnoreCase("parallel"))
			{
				String parallelApprover1="";
				String parallelApprover2="";
				String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
				ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
					try {
						while(rsApproverDsg.next())
						{
							pApprover=rsApproverDsg.getString("ApproverId");
							parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
							parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
							
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					int i=0;
				 saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+empNo+"','"+dateNow+"','Pending','','"+pApprover+"','No','"+empNo+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
					leaveForm.setMessage("Onduty Details Submitted Successfully");
					if(!(parallelApprover1.equalsIgnoreCase("")))
					{
						saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
						saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+empNo+"','"+dateNow+"','Pending','','"+parallelApprover1+"','No','"+empNo+"','')";
						 i=ad.SqlExecuteUpdate(saveRecReq);
					}
					if(!(parallelApprover2.equalsIgnoreCase("")))
					{
						saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
						saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+empNo+"','"+dateNow+"','Pending','','"+parallelApprover2+"','No','"+empNo+"','')";
						 i=ad.SqlExecuteUpdate(saveRecReq);
					}
					
			}
		}
		int i=0;
		if(existStatus==false){
	
		 saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
		saveRecReq = saveRecReq + "'"+OldrequestNo+"','On Duty','"+empNo+"','"+dateNow+"','Pending','','"+pApprover+"','No','"+empNo+"','')";
		 i=ad.SqlExecuteUpdate(saveRecReq);
		leaveForm.setMessage("Onduty Details Submitted Successfully");
		}
		
		
		
		}
		return approverList;
	}
	public ActionForward saveOnduty(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		HRLeaveForm ondutyForm=(HRLeaveForm)form;
		String empNo=ondutyForm.getEmpNumber();
		try{
			String type=request.getParameter("param");
			int OldrequestNo=ondutyForm.getRequestNumber();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			String module=request.getParameter("id"); 
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
			
			LinkedList ondutyid=new LinkedList();
			LinkedList ondutyreason=new LinkedList();
			
			String reason="select * from Onduty_Type";
			ResultSet a11=ad.selectQuery(reason);
			try {
				while(a11.next())
				{
					ondutyid.add(a11.getString("OnDuty_type"));
					ondutyreason.add(a11.getString("OnDuty_type"));
				}
				ondutyForm.setOndutyvalue(ondutyid);
				ondutyForm.setOndutyreason(ondutyreason);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				String userPlantID=user.getPlantId();
				int userID=user.getId();
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			ondutyForm.setLocationIdList(locationList);
			ondutyForm.setLocationLabelList(locationLabelList);
			
			String content_description = ondutyForm.getReason();
			
			OnDutyAction lA1 = new OnDutyAction();
			LinkedList appList1= lA1.getApprovers(request, response);
			if(appList1.size()>0){	
		String documents="";
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		 String dateNow = ft.format(dNow);
		String sql1="select * from onDuty_emp_documents where user_id='"+empNo+"' and request_no='"+OldrequestNo+"'";
		ResultSet rs=ad.selectQuery(sql1);

			while(rs.next()) {
				documents+=rs.getString("file_name")+",";
			}
			if(documents.equalsIgnoreCase("")){
				documents=",";
			}
			EMailer email = new EMailer();
			String approver="";
			String lApprover="";
			String pApprover="";
			String approvermail="";
			
			String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
					+ "ess.employeeNumber='"+empNo+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
			ResultSet approverRS=ad.selectQuery(getApproverID);
			if(approverRS.next()){
				
					approver = approverRS.getString("ApproverId");
					approver = email.getApproverName(approver);
					lApprover = "";
					pApprover = approver;
					approvermail=approverRS.getString("EMAIL_ID");
			}
			
			 String startDate=ondutyForm.getStartDate();
		 	 
			  String a[]=startDate.split("/");
			 
			  startDate=a[2]+"-"+a[1]+"-"+a[0];
				int year = Integer.parseInt(a[2]);
			  String endDate=ondutyForm.getEndDate();
			 	 
			  String b[]=endDate.split("/");
			 
			  endDate=b[2]+"-"+b[1]+"-"+b[0];
			  String checkDayType="";
			  boolean checkDayStatus=true;
				boolean check1= false;
				String ondutyType=ondutyForm.getOnDutyType();
				
				/* //CHEKC leave can be applied after predefined days
	            String allowtoapply="";
	            int applyafter=0;
	            String lastdate="";
	            
	            String abcdef="select convert(date, dateadd(day,-(select Apply_after from Location where locid='16'),getdate()) ) lastdate,Apply_after,case when convert(date, dateadd(day,-(select Apply_after from Location where locid='"+user.getPlantId()+"'),getdate()) )<='"+(b[2]+"-"+b[1]+"-"+b[0])+"' then 1 else 0 end as allowdate  from Location where locid='"+user.getPlantId()+"'";
	            ResultSet nn=ad.selectQuery(abcdef);
	            while(nn.next())
	            {
	           	 allowtoapply=nn.getString("allowdate");
	           	applyafter=nn.getInt("Apply_after");
	           	lastdate=nn.getString("lastdate");
	            }
	            
	            if(allowtoapply.equalsIgnoreCase("0"))
	            {
	            	checkDayStatus=false;
					ondutyForm.setMessage2("Request Cannot be applied...  To Date should be greater than or equal to '"+lastdate+"'.. Contact HR");
					OnDutyAction lA = new OnDutyAction();
					LinkedList appList= lA.getApprovers(request, response);
					request.setAttribute("appList", appList);
					return mapping.findForward("applyOnduty");
	            }*/
				
				
			  if(ondutyType.equalsIgnoreCase("Visit Plants"))
			  {
				if(startDate.equalsIgnoreCase(endDate)){
				  String[] monthName = {"","JAN", "FEB",
						  "MAR", "APR", "MAY", "JUN", "JUL",
						  "AUG", "SEP", "OCT", "NOV",
						  "DEC"
						  };
				  String reqdate=ondutyForm.getStartDate();
				  String f[]=reqdate.split("/");
					 reqdate=f[1];
					
					 String startMonth=f[1];
					int staMont=Integer.parseInt(startMonth);
				  startMonth = monthName[staMont];
					String location=ondutyForm.getLocationId();
					String tableName=getTableName(location);
				
						
						String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"' and CYEAR='"+year+"'";
						ResultSet rsCheckDay=ad.selectQuery(checkDay);
						while(rsCheckDay.next())
						{
							checkDayType=rsCheckDay.getString(startMonth);
							
						}
						if(!(checkDayType.equalsIgnoreCase("W")))
						{
							checkDayStatus=false;
							ondutyForm.setMessage2("Selected date is a holiday for "+ondutyForm.getLocationId()+"");
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
				}
			  }
			  
			  //check Leave
			  
			  String checkLeaveRecords="Select * from leave_details where user_id='"+empNo+"' and (Approvel_Status='Pending' or  Approvel_Status='Approved') and " +
			  		" (('"+startDate+"' between start_date and end_date) or ('"+endDate+"' between start_date and end_date))  ";
			  ResultSet rsLeaveRec=ad.selectQuery(checkLeaveRecords);
			  while(rsLeaveRec.next())
			  {
				  String startDateDuration=rsLeaveRec.getString("start_duration");
				  String endDurationType=rsLeaveRec.getString("end_duration");
					String lvStDate=rsLeaveRec.getString("start_date");
					String x[]=lvStDate.split(" ");
					lvStDate=x[0];
					String lvEndDate=rsLeaveRec.getString("end_date");
					String y[]=lvEndDate.split(" ");
					lvEndDate=y[0];
					
					String draftstatus=rsLeaveRec.getString("record_status");
					
					String reqTime=ondutyForm.getStartTime();
					String reqTimeCycle=reqTime.substring(5, 7);
					reqTime=ondutyForm.getStartTime();
					reqTime=reqTime.substring(0, 5);
					
					String reqT[]=reqTime.split(":");
					int reqHours=Integer.parseInt(reqT[0]);
					int reqMint=Integer.parseInt(reqT[1]);
					if(reqTimeCycle.equalsIgnoreCase("AM"))
					{
						reqHours=reqHours;
					}else{
						if(reqHours!=12)
						reqHours=reqHours+12;	
					}
					reqMint=(reqHours*60)+reqMint;
					
					
					
					String reqToTime=ondutyForm.getEndTime();
					String reqToTimeCycle=reqToTime.substring(5, 7);
					reqToTime=ondutyForm.getEndTime();
					reqToTime=reqToTime.substring(0, 5);
					
					String reqToT[]=reqToTime.split(":");
					int reqToHours=Integer.parseInt(reqToT[0]);
					int reqToMint=Integer.parseInt(reqToT[1]);
					if(reqToTimeCycle.equalsIgnoreCase("AM"))
					{
						reqToHours=reqToHours;
					}else{
						if(reqToHours!=12)
						reqToHours=reqToHours+12;	
					}
					reqToMint=(reqToHours*60)+reqToMint;
					
					reqToTime=Integer.toString(reqHours)+":"+reqT[1]+":"+"00";
					
					System.out.println("from Mint="+reqMint);
					System.out.println("To Mint="+reqToMint);
					
					if(startDate.equalsIgnoreCase(lvStDate) )
					{
						
						if(startDateDuration.equalsIgnoreCase("FD") )
						{
							
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							
						
						}
						
						if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
						{
							
							if(reqMint<780)
							{
							 ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
									ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
							 OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
							
						
						}
						if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
						{
							if(reqToMint>780)
							{
								ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
								  
								if(draftstatus.equalsIgnoreCase("Draft")){
									ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
								OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
						}
						if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
						
					}
					if(startDate.equalsIgnoreCase(lvEndDate))
					{
						if(endDurationType.equalsIgnoreCase("FD") )
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
						}
						if(endDurationType.equalsIgnoreCase("FH") )
						{
							if(reqMint<=780)
							{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
						}
						if(endDurationType.equalsIgnoreCase("SH") )
						{
							if(reqMint>780)
							{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
						}
					}
					if(endDate.equalsIgnoreCase(lvStDate))
					{
						if(startDateDuration.equalsIgnoreCase("FD") )
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
						}
						if(startDateDuration.equalsIgnoreCase("FH") )
						{
							if(reqToMint<=780)
							{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
						}
						if(startDateDuration.equalsIgnoreCase("SH") )
						{
							
							 
							if(reqToMint>780)
							{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
						}
						
					}
					
					if(endDate.equalsIgnoreCase(lvEndDate)  )
					{
						if(endDurationType.equalsIgnoreCase("FD") )
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
						}
						if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
						{
							if(reqToMint<=780)
							{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							  
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
							}
						
						}
						if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FH"))
						{
							if(reqToMint<=780)
							{
								ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
								if(draftstatus.equalsIgnoreCase("Draft")){
									ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
								  
								OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
						}
						if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
						{
							if(reqToMint<=780)
							{
								ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
								if(draftstatus.equalsIgnoreCase("Draft")){
									ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
								OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
						}
						if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
						{
							if(reqToMint>780)
							{
								ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
								if(draftstatus.equalsIgnoreCase("Draft")){
									ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
								OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
							}
						}
						if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
						{
							ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
							if(draftstatus.equalsIgnoreCase("Draft")){
								ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
						}
						
					}
					
					
					if(!(startDate.equalsIgnoreCase(lvStDate)) && !(endDate.equalsIgnoreCase(lvEndDate))){
						ondutyForm.setMessage2("Leave already applied for this timings..Please check ");
						if(draftstatus.equalsIgnoreCase("Draft")){
							ondutyForm.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
						}
						OnDutyAction lA = new OnDutyAction();
						LinkedList appList= lA.getApprovers(request, response);
						request.setAttribute("appList", appList);
						return mapping.findForward("applyOnduty");				
						}
					
				}
			  //check Permission
			  
			  String checkPermission="Select * from Permission_details where (Approver_Status=0 or  Approver_Status=1) and  user_id='"+empNo+"'" +
			  		" and ((date  between '"+startDate+"' and '"+endDate+"'))";
			  ResultSet rscheckPermi=ad.selectQuery(checkPermission);
			  while(rscheckPermi.next())
			  {

					String permissonDt=rscheckPermi.getString("date");
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
						{
						fromHours=fromHours+12;
						}
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
						{
							toHours=toHours+12;
						}
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
					
					String reqTime=ondutyForm.getStartTime();
					String reqTimeCycle=reqTime.substring(5, 7);
					reqTime=ondutyForm.getStartTime();
					reqTime=reqTime.substring(0, 5);
					
					String reqT[]=reqTime.split(":");
					int reqHours=Integer.parseInt(reqT[0]);
					int reqMint=Integer.parseInt(reqT[1]);
					if(reqTimeCycle.equalsIgnoreCase("AM"))
					{
						reqHours=reqHours;
					}else{
						if(reqHours!=12)
						{
							reqHours=reqHours+12;
						}
					}
					reqMint=(reqHours*60)+reqMint;
					
					
					
					String reqToTime=ondutyForm.getEndTime();
					String reqToTimeCycle=reqToTime.substring(5, 7);
					reqToTime=ondutyForm.getEndTime();
					reqToTime=reqToTime.substring(0, 5);
					
					String reqToT[]=reqToTime.split(":");
					int reqToHours=Integer.parseInt(reqToT[0]);
					int reqToMint=Integer.parseInt(reqToT[1]);
					if(reqToTimeCycle.equalsIgnoreCase("AM"))
					{
						reqToHours=reqToHours;
					}else{
						if(reqToHours!=12)
						{
							reqToHours=reqToHours+12;
						}
					}
					reqToMint=(reqToHours*60)+reqToMint;
					
					reqToTime=Integer.toString(reqHours)+":"+reqT[1]+":"+"00";
					
					if(permissonDt.equalsIgnoreCase(startDate)  || permissonDt.equalsIgnoreCase(endDate))
					{
						
	                  if(permissonDt.equalsIgnoreCase(startDate))
	                  {
						
						if(fromMint<reqMint && reqMint<toMint )
						{
							 ondutyForm.setMessage2("Permission already applied for this timings..Please check ");
							  
							 OnDutyAction lA = new OnDutyAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
						}
						
						/*if(reqMint<fromMint && toMint<reqToMint )
						{
							 ondutyForm.setMessage("Permission already applied for this timings..Please check ");
							  
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
						}*/
						
	                  }
	                  if(permissonDt.equalsIgnoreCase(endDate))
	                  {
	                	  if(fromMint<reqToMint && reqToMint<toMint )
	  					{
	  						 ondutyForm.setMessage2("Permission already applied for this timings..Please check ");
	  						  
	  						OnDutyAction lA = new OnDutyAction();
	  							LinkedList appList= lA.getApprovers(request, response);
	  							request.setAttribute("appList", appList);
	  							return mapping.findForward("applyOnduty");
	  					}
	                  }
						
					
					
				
						
						
						/*
						if(permissonDt.equalsIgnoreCase(startDate))
						{
							if(reqMint>fromMint && reqMint <toMint)
							{
								 ondutyForm.setMessage("Permission already applied for this timings..Please check ");
								  
								    LeaveAction lA = new LeaveAction();
									LinkedList appList= lA.getApprovers(request, response);
									request.setAttribute("appList", appList);
									return mapping.findForward("applyOnduty");
							}
							
						}
						
					*/}else{/*
					
					
					if(permissonDt.equalsIgnoreCase(startDate))
					{
						if(reqMint>fromMint)
						{
							 ondutyForm.setMessage("Permission already applied for this timings..Please check ");
							  
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
						}
					}
					if(permissonDt.equalsIgnoreCase(endDate))
					{
						if(reqMint>toMint)
						{
							 ondutyForm.setMessage("Permission already applied for this timings..Please check ");
							  
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("applyOnduty");
						}
					}
					*/
						}
				
					
			  }
			  
			  
			        if(true)
			        {
			        	
			        	if(!ondutyForm.getOnDutyType().equalsIgnoreCase(""))
			        	{
			        	String data="Select * from OnDuty_details where  user_id='"+empNo+"' and (('"+startDate+"' between " +
								"start_date and end_date) or ('"+endDate+"' between start_date and end_date)) and onDuty_Type='Visit Plants' and (Approver_Status='Approved' or Approver_Status='In Process')";	        	
			        	ResultSet rsAppDate1=ad.selectQuery(data);
						while(rsAppDate1.next())
						{
							ondutyForm.setMessage("OnDuty already applied for this Date..Please check ");
							  
							OnDutyAction lA = new OnDutyAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("applyOnduty");
							
							
						}
			        	}
			        	if(!(ondutyForm.getOnDutyType().equalsIgnoreCase("Visit Plants")))
			        	{
			        	
						String getAppliedDates="Select * from OnDuty_details where  user_id='"+empNo+"' and (('"+startDate+"' between " +
						"start_date and end_date) or ('"+endDate+"' between start_date and end_date)) and (Approver_Status='Approved' or Approver_Status='In Process')";
						ResultSet rsAppDate=ad.selectQuery(getAppliedDates);
						while(rsAppDate.next())
						{
							
							String reqStDate=rsAppDate.getString("start_date");
							String[] a1=reqStDate.split(" ");
							reqStDate=a1[0];
							String reqEndDate=rsAppDate.getString("end_date");
							String[] b1=reqEndDate.split(" ");
							reqEndDate=b1[0];
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
							
							String reqTime=ondutyForm.getStartTime();
							String reqTimeCycle=reqTime.substring(5, 7);
							reqTime=ondutyForm.getStartTime();
							reqTime=reqTime.substring(0, 5);
							
							String reqT[]=reqTime.split(":");
							int reqHours=Integer.parseInt(reqT[0]);
							int reqMint=Integer.parseInt(reqT[1]);
							if(reqTimeCycle.equalsIgnoreCase("AM"))
							{
								reqHours=reqHours;
							}else{
								if(reqHours!=12)
								reqHours=reqHours+12;	
							}
							reqMint=(reqHours*60)+reqMint;
							
							
							
							String reqToTime=ondutyForm.getEndTime();
							String reqToTimeCycle=reqToTime.substring(5, 7);
							reqToTime=ondutyForm.getEndTime();
							reqToTime=reqToTime.substring(0, 5);
							
							String reqToT[]=reqToTime.split(":");
							int reqToHours=Integer.parseInt(reqToT[0]);
							int reqToMint=Integer.parseInt(reqToT[1]);
							if(reqToTimeCycle.equalsIgnoreCase("AM"))
							{
								reqToHours=reqToHours;
							}else{
								if(reqToHours!=12)
								reqToHours=reqToHours+12;	
							}
							reqToMint=(reqToHours*60)+reqToMint;
							
							reqTime=Integer.toString(reqHours)+":"+reqT[1]+":"+"00";
							reqToTime=Integer.toString(reqToHours)+":"+reqToT[1]+":"+"00";
							
							if(startDate.equalsIgnoreCase(reqStDate) && reqEndDate.equalsIgnoreCase(startDate))
							{
								if(reqStDate.equalsIgnoreCase(startDate))
								{
									if(reqMint>fromMint && reqMint <toMint)
									{
										 ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
										  
										 OnDutyAction lA = new OnDutyAction();
											LinkedList appList= lA.getApprovers(request, response);
											request.setAttribute("appList", appList);
											return mapping.findForward("applyOnduty");
									}
									
								}
								
							}
							else{
							 if(reqStDate.equalsIgnoreCase(startDate) || startDate.equalsIgnoreCase(reqEndDate))
							{
								
								if(reqStDate.equalsIgnoreCase(startDate))
								{
									if ((fromMint<=reqMint) ) {
										  
									    System.out.println(true);
									    check1=true;
									    ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
									  
									    OnDutyAction lA = new OnDutyAction();
										LinkedList appList= lA.getApprovers(request, response);
										request.setAttribute("appList", appList);
										return mapping.findForward("applyOnduty");
									}
								}
								if(startDate.equalsIgnoreCase(reqEndDate))
								{
									if (reqMint<=toMint) {
										  
									    System.out.println(true);
									    check1=true;
									    ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
									  
									    OnDutyAction lA = new OnDutyAction();
										LinkedList appList= lA.getApprovers(request, response);
										request.setAttribute("appList", appList);
										return mapping.findForward("applyOnduty");
									}
								}
							
								
							}						
							if(reqEndDate.equalsIgnoreCase(endDate) || endDate.equalsIgnoreCase(reqStDate))
							{
								if(endDate.equalsIgnoreCase(reqStDate))
								{
									if (reqToMint>=fromMint)  {
										  
									    System.out.println(true);
									    check1=true;
									    ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
									  
									    OnDutyAction lA = new OnDutyAction();
										LinkedList appList= lA.getApprovers(request, response);
										request.setAttribute("appList", appList);
										return mapping.findForward("applyOnduty");
									}
								}
								if(reqEndDate.equalsIgnoreCase(endDate))
								{
									if (toMint>=reqToMint) {
										System.out.println(true);
									    check1=true;
									    ondutyForm.setMessage2("OnDuty already applied for this timings..Please check ");
									  
									    OnDutyAction lA = new OnDutyAction();
										LinkedList appList= lA.getApprovers(request, response);
										request.setAttribute("appList", appList);
										return mapping.findForward("applyOnduty");
									   
									}else{
										
									}

								}
							 }
							
							}
						}
			        	}
				      }
			  
			  int check=0;
			  if(false)
			  {
			 
			
			  String checkLeave="select start_date,end_date from OnDuty_details where  user_id='"+empNo+"'";
				ResultSet rsCheckLeave=ad.selectQuery(checkLeave);
				while(rsCheckLeave.next()){

					try{
						List<Date> dates = new ArrayList<Date>();
						String str_date =rsCheckLeave.getString("start_date");
						String c[]=str_date.split(" ");
						str_date=c[0];
						String end_date =rsCheckLeave.getString("end_date");
			            String d[]=end_date.split(" ");
			            end_date=d[0];
			            String a1[]=str_date.split("-");
			            str_date=a1[2]+"/"+a1[1]+"/"+a1[0];
			            String b1[]=end_date.split("-");
			            end_date=b1[2]+"/"+b1[1]+"/"+b1[0]; 
						DateFormat formatter ; 
						formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date  startDate1 = (Date)formatter.parse(str_date); 
						Date  endDate1 = (Date)formatter.parse(end_date);
						long interval = 24*1000 * 60 * 60; // 1 hour in millis
						long endTime =endDate1.getTime() ; // create your endtime here, possibly using Calendar or Date
						long curTime = startDate1.getTime();
						while (curTime <= endTime) {
						    dates.add(new Date(curTime));
						    curTime += interval;
						}
						for(int i=0;i<dates.size();i++){
						    Date lDate =(Date)dates.get(i);
						    String ds = formatter.format(lDate);    
						 
						    if(ds.equalsIgnoreCase(ondutyForm.getStartDate())){
						    	check++;
						    }
						}
						    for(int j=0;j<dates.size();j++){
							    Date lDate1 =(Date)dates.get(j);
							    String ds1 = formatter.format(lDate1);    
							
							    if(ds1.equalsIgnoreCase(ondutyForm.getEndDate())){
							    	check++;
							    }
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			  }
				if(check==0){
			synchronized (this) {
			int status=0;
			String getRequestNoStatus="select count(*) from OnDuty_details where request_no='"+OldrequestNo+"'";
			ResultSet rsReqNoStatus=ad.selectQuery(getRequestNoStatus);
			while(rsReqNoStatus.next())
			{
				status=rsReqNoStatus.getInt(1);
			}
			
			 
			//request no is not available
			if(status==0)
			{
				
		
				OldrequestNo = OldrequestNo ;
				if(type.equalsIgnoreCase("Applied"))
				{
					String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
					"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime) " +
					"values('"+OldrequestNo+"','"+empNo+"','"+ondutyForm.getOnDutyType()+"','"+startDate+"','"+endDate+"','0'," +
							"'Submited','"+documents+"','"+dateNow+"','"+content_description+"','"+user.getFirstName()+"','"+pApprover+"','In Process','','No','"+pApprover+"','"+ondutyForm.getLocationId()+"','"+ondutyForm.getStartTime()+"','"+ondutyForm.getEndTime()+"','','')";
					int i=0;
					i=ad.SqlExecuteUpdate(saveOnDuty);
					
					if(i>0)
					{
						ondutyForm.setMessage("On Duty request submited with  request number='"+OldrequestNo+"' ");
						i = email.sendMailToOffice_EmpApprover(request, approvermail,Integer.toString(OldrequestNo), "On Duty",empNo);
					
						getApproverDetails(Integer.toString(OldrequestNo), form, request, pApprover,empNo);
						
						applyOnduty(mapping, form, request, response);
						
					}else{
						ondutyForm.setMessage2("Error...When code saving creation request.Please check");
						ondutyForm.setRequestNumber(OldrequestNo);
					}
				}else{}
			}else{
				int  newReqestNo=0;
				String getRequstNo="select max(request_no) from OnDuty_details";
				ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
				while(rsRequestNo.next())
				{
					newReqestNo=rsRequestNo.getInt(1);
				}
				newReqestNo=newReqestNo+1;
				
				if(type.equalsIgnoreCase("Applied"))
				{
					String saveOnDuty="insert into OnDuty_details(request_no,user_id,onDuty_Type,start_date,end_date,OnDuty_status," +
					"record_status,documents,submit_date,reason,firstname,Approver_id,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,location,startTime,endTime,InTime,OutTime) " +
					"values('"+newReqestNo+"','"+empNo+"','"+ondutyForm.getOnDutyType()+"','"+startDate+"','"+endDate+"','0'," +
							"'Submited','"+documents+"','"+dateNow+"','"+content_description+"','"+user.getFirstName()+"','"+pApprover+"','In Process','','No','"+pApprover+"','"+ondutyForm.getLocationId()+"','"+ondutyForm.getStartTime()+"','"+ondutyForm.getEndTime()+"','','')";
					int i=0;
					i=ad.SqlExecuteUpdate(saveOnDuty);
					
					if(i>0)
					{
						
						ondutyForm.setMessage("On Duty request submited with request number='"+newReqestNo+"' ");
						getApproverDetails(Integer.toString(newReqestNo), form, request, pApprover,empNo);
						i = email.sendMailToOffice_EmpApprover(request, approvermail,Integer.toString(newReqestNo), "On Duty",empNo);
						
						
						
					
					}else{
						ondutyForm.setMessage2("Error...When code saving creation request.Please check");
						ondutyForm.setRequestNumber(newReqestNo);
					}
				}else{}
				
				String updateDocuments="update onDuty_emp_documents set request_no='"+newReqestNo+"' where request_no='"+OldrequestNo+"' and user_id='"+empNo+"'";
				ad.SqlExecuteUpdate(updateDocuments);
			}
			}
				}else{
					ondutyForm.setMessage2("OnDuty Already Applied For The Selected Date");
				}
		}else{
			ondutyForm.setMessage2("No Approvers assigned.Please Contact Admin");
		}
			}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("applyOnduty");
	
		
	}

	
	public ActionForward deleteIOnDutyDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm ondutyForm=(HRLeaveForm)form;
		String checkedValues=request.getParameter("cValues");
		String empNo=ondutyForm.getEmpNumber();
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=ondutyForm.getRequestNumber();
		 int documentLength=0;
		 
	 
		 try 
		 {
			 documentLength=documentCheck.length;
		 }catch(Exception e){
			
		 }
		 
		 ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				String userPlantID=user.getPlantId();
				int userID=user.getId();
				try{
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
				}catch (Exception e) {
					e.printStackTrace();
				}
			ondutyForm.setLocationIdList(locationList);
			ondutyForm.setLocationLabelList(locationLabelList);
			int document=0;
			String documentId="";
			String documentName="";
			String sql="";
			System.out.println("rejectLength Is ********************"+documentLength);
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						String fileName="";
						String getFilename="select * from onDuty_emp_documents where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from onDuty_emp_documents  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					ondutyForm.setMessage("Document Details Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/On Duty/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from onDuty_emp_documents where user_id='"+empNo+"' and request_no='"+reqNo+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			
			try{
				HRLeaveForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new HRLeaveForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getString("id"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);
				
				HRLeaveAction lA = new HRLeaveAction();
				LinkedList appList= lA.getApprovers(request, response,empNo,"OnDuty");
				request.setAttribute("appList", appList);
			}catch(Exception e){
				e.printStackTrace();
			}
		return mapping.findForward("applyOnduty");
	}
	public ActionForward uploadOnDutyDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm ondutyForm=(HRLeaveForm)form;
		
		FormFile documentFile=ondutyForm.getDocumentFile();
		String documentName=documentFile.getFileName();
		String onDutyType=ondutyForm.getOnDutyType();
		String empNo=ondutyForm.getEmpNumber();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 String filePath="";
		try{
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				String userPlantID=user.getPlantId();
				int userID=user.getId();
				try{
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
				}catch (Exception e) {
					e.printStackTrace();
				}
			ondutyForm.setLocationIdList(locationList);
			ondutyForm.setLocationLabelList(locationLabelList);
	 	
		 byte[] size=documentFile.getFileData();
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	 	String fileName = documentFile.getFileName();
	     String extension=documentName.substring(dot,length);
	     //jsp/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles
 	     //String filepath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/ESS/On Duty/UploadFiles/"+documentFile.getFileName());
 	  filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/On Duty/UploadFiles");
 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
 	 Properties props = new Properties();
 	props.load(in);
	in.close();
 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles");
 	 filePath=uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles";
 	 
 	 
		//masterForm.setUploadFilePath(filePath);
		File destinationDir = new File(filePath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
		}
		if (!fileName.equals("")) {
			File fileToCreate = new File(filePath, fileName);
			if (!fileToCreate.exists()) {
				FileOutputStream fileOutStream = new FileOutputStream(
						fileToCreate);
				fileOutStream.write(documentFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
			}
		}
		//upload files in another path
		
		try{
			String filePath1 = "E:/EMicro Files/ESS/On Duty/UploadFiles";
			
			byte[] fileData1 = documentFile.getFileData();
			
			
			File destinationDir1 = new File(filePath1);
			if(!destinationDir1.exists())
			{
				destinationDir1.mkdirs();
			}
			if (!fileName.equals("")) {
				File fileToCreate1 = new File(filePath1, fileName);
				if (!fileToCreate1.exists()) {
					FileOutputStream fileOutStream1 = new FileOutputStream(
							fileToCreate1);
					fileOutStream1.write(documentFile.getFileData());
					fileOutStream1.flush();
					fileOutStream1.close();
				}
			}
			
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
		
		String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
		try{
			if(ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("txt")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg"))
			{
			
			int reqNo=ondutyForm.getRequestNumber();
			
			String sql9="select count(*) from onDuty_emp_documents  where  file_name='"+documentFile.getFileName()+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				ondutyForm.setMessage("Document aleardy uploaded..please choose another file");
			}
			else
			{
				String sql="insert into onDuty_emp_documents(request_no,user_id,file_name,onDuty_Type)" +
				"values('"+reqNo+"','"+empNo+"','"+documentFile.getFileName()+"','"+onDutyType+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					ondutyForm.setMessage("Documents Uploaded Successfully");
					}
			}
			}
			else{
				ondutyForm.setMessage2("Please upload files with doc,docx,pdf or jpg extensions");
				HRLeaveAction lA = new HRLeaveAction();
				LinkedList appList= lA.getApprovers(request, response,empNo,"OnDuty");
				request.setAttribute("appList", appList);
				
			}
		String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+empNo+"' and onDuty_Type='"+onDutyType+"'";
		ResultSet rs=ad.selectQuery(sql1);
		HRLeaveForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs.next()) {
				leaveForm1=new HRLeaveForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getString("id"));
				leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
			HRLeaveAction lA = new HRLeaveAction();
			LinkedList appList= lA.getApprovers(request, response,empNo,"OnDuty");
			request.setAttribute("appList", appList);
		
			
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("applyOnduty");
		}
	public ActionForward applyOnduty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HRLeaveForm ondutyForm=(HRLeaveForm)form;
		
		LinkedList totalList = new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		String empNo=ondutyForm.getEmpNumber();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		try{
			 
			 String hrLocID=user.getPlantId();
			 boolean checkEmpExist=false;
			 String checkEmpStatus="select * from emp_official_info as emp,Location as loc where PERNR='"+empNo+"' and loc.LOCID='"+hrLocID+"'";
			 ResultSet rsEmpStatus=ad.selectQuery(checkEmpStatus);
			
				 while(rsEmpStatus.next()){
					 ondutyForm.setEmpName(empNo+" - "+rsEmpStatus.getString("EMP_FULLNAME"));
					 checkEmpExist=true;
				 }
			if(checkEmpExist==false){
				ondutyForm.setEmpStatus("Employee Number Not Exist Or Invalid Employee Number.Please Check");
				return mapping.findForward("search");
			}
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				String userPlantID=user.getPlantId();
				int userID=user.getId();
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			ondutyForm.setLocationIdList(locationList);
			ondutyForm.setLocationLabelList(locationLabelList);	
		int requestNo=0;
		String getRequstNo="select max(request_no) from OnDuty_details";
		ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
		while(rsRequestNo.next())
		{
			requestNo=rsRequestNo.getInt(1);
		}
		requestNo=requestNo+1;
		ondutyForm.setRequestNumber(requestNo);
		ondutyForm.setOnDutyType("");
		ondutyForm.setDuration("");
		ondutyForm.setStartDate("");
		ondutyForm.setEndDate("");
		ondutyForm.setNoOfDays("");
		ondutyForm.setReason("");
		ondutyForm.setLocationId("");
		ondutyForm.setStartTime("");
		ondutyForm.setEndTime("");
		//for get total onduty taken 
		int onDutyTotal=0;
		int typeTotal=0;
		HRLeaveForm odf = new HRLeaveForm();
		odf.setVpTotal(typeTotal);
		odf.setAcTotal(typeTotal);
		odf.setCvTotal(typeTotal);
		odf.setTourTotal(typeTotal);
		String totalQry="select count(*) from OnDuty_details where Approver_Status='Approved' and onDuty_Type like '%Visit%'";
		ResultSet totalRS = ad.selectQuery(totalQry);
		while(totalRS.next()){
			typeTotal = totalRS.getInt(1);
			onDutyTotal = onDutyTotal + typeTotal;
			odf.setVpTotal(typeTotal);
		}
		totalQry="select count(*) from OnDuty_details where Approver_Status='Approved' and onDuty_Type like '%Attend%'";
		totalRS = ad.selectQuery(totalQry);
		while(totalRS.next()){
			typeTotal = totalRS.getInt(1);
			onDutyTotal = onDutyTotal + typeTotal;
			odf.setAcTotal(typeTotal);
		}
		totalQry="select count(*) from OnDuty_details where Approver_Status='Approved' and onDuty_Type like '%Meet%'";
		totalRS = ad.selectQuery(totalQry);
		while(totalRS.next()){
			typeTotal = totalRS.getInt(1);
			onDutyTotal = onDutyTotal + typeTotal;
			odf.setCvTotal(typeTotal);
		}
		totalQry="select count(*) from OnDuty_details where Approver_Status='Approved' and onDuty_Type like '%Tour%'";
		totalRS = ad.selectQuery(totalQry);
		while(totalRS.next()){
			typeTotal = totalRS.getInt(1);
			onDutyTotal = onDutyTotal + typeTotal;
			odf.setTourTotal(typeTotal);
		}
		odf.setOnDTotal(onDutyTotal);
		
		totalList.add(odf);
		
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("OnDutyTakenList",totalList);
		HRLeaveAction lA = new HRLeaveAction();
		LinkedList appList= lA.getApprovers(request, response,empNo,"OnDuty");
		request.setAttribute("appList", appList);
		
		if(appList.size()==0)
			ondutyForm.setMessage2("No Approvers assigned.Please Contact Admin");
		
		LinkedList ondutyid=new LinkedList();
		LinkedList ondutyreason=new LinkedList();
		
		String reason="select * from Onduty_Type";
		ResultSet a=ad.selectQuery(reason);
		try {
			while(a.next())
			{
				ondutyid.add(a.getString("OnDuty_type"));
				ondutyreason.add(a.getString("OnDuty_type"));
			}
			ondutyForm.setOndutyvalue(ondutyid);
			ondutyForm.setOndutyreason(ondutyreason);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mapping.findForward("applyOnduty");
	
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
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("SUBMIT LEAVE DETAILS");
		HRLeaveForm leaveForm=(HRLeaveForm)form;
		String param=request.getParameter("param");
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String empNo=leaveForm.getEmpNumber();
		String leaveType=leaveForm.getLeaveType();
		String startDate=leaveForm.getStartDate();
		String startDurationType=leaveForm.getStartDurationType();
		String endDurationType=leaveForm.getEndDurationType();
		String endDate=leaveForm.getEndDate();
		String noOfDays=request.getParameter("noOfDays");
		 Enumeration<String> params = (Enumeration<String>) request.getParameterNames();
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
	
	
	String checkLeave="select start_date,end_date,start_duration,end_duration from leave_details where  firstname='"+empNo+"' and (Approvel_Status='Approved' or Approvel_Status='Pending')";
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
			Date  startDate1 = (Date)formatter.parse(str_date); 
			Date  endDate1 = (Date)formatter.parse(end_date);
			long interval = 24*1000 * 60 * 60; // 1 hour in millis
			long endTime =endDate1.getTime() ; // create your endtime here, possibly using Calendar or Date
			long curTime = startDate1.getTime();
			while (curTime <= endTime) {
			    dates.add(new Date(curTime));
			    curTime += interval;
			}
			for(int i=0;i<dates.size();i++){
			    Date lDate =(Date)dates.get(i);
			    String ds = formatter.format(lDate);    
			 
			    if(ds.equalsIgnoreCase(startDate)){
			    	check++;
			    }
			}
			    for(int j=0;j<dates.size();j++){
				    Date lDate1 =(Date)dates.get(j);
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
	
	System.out.println("check count="+check);
	
	
	if(leaveType.equalsIgnoreCase("6"))
	{
		check=0;
	}
	
	
	if(check==0){

		String documents="";
	
	        Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			 String submitDate = ft.format(dNow);
	       
		String sql1="select * from temp_emp_leave_documents where user_id='"+empNo+"' and leave_type='"+leaveType+"' and req_no='"+leaveForm.getRequestNumber()+"'";
		ResultSet rs=ad.selectQuery(sql1);
	
			while(rs.next()) {
				documents+=rs.getString("file_name")+",";
			}
			if(documents.equalsIgnoreCase("")){
				documents=",";
			}
		
			Connection connection=ConnectionFactory.getConnection();
			
		
			int userLeaveCount = getCountForTable("leave_details",Integer.parseInt(empNo));
			
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
			" values('"+empNo+"'," +
					"'"+leaveType+"','"+EMicroUtils.dateConvert(startDate)+"','"+EMicroUtils.dateConvert(endDate)+"','"+startDurationType+"','"+leaveForm.getEndDurationType()+"'," +
					"'"+noOfDays+"','0'" +
					",'"+param+"','"+documents.substring(0, documents.lastIndexOf(","))+"'," +
					"'"+user.getDepartmentId()+"'" +
					",'"+user.getPlantId()+"','"+user.getDesignationId()+"','"+submitDate+"','"+leaveForm.getReasonType()+"','"+content_description+"','"+empNo+"','"+Req_Id+"','Pending')";
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
				" values('"+empNo+"'," +
						"'"+leaveType+"','"+EMicroUtils.dateConvert(startDate)+"','"+EMicroUtils.dateConvert(endDate)+"','"+startDurationType+"','"+leaveForm.getEndDurationType()+"'," +
						"'"+noOfDays+"','0'" +
						",'"+param+"','"+documents.substring(0, documents.lastIndexOf(","))+"'," +
						"'"+user.getDepartmentId()+"'" +
						",'"+user.getPlantId()+"','"+user.getDesignationId()+"','"+submitDate+"','"+leaveForm.getReasonType()+"','"+content_description+"','"+empNo+"','"+newReqNo+"','Pending')";
				PreparedStatement psmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				a=psmt.executeUpdate();
				ResultSet rs1=psmt.getGeneratedKeys();
				String updateUploadedFiles="update temp_emp_leave_documents set req_no='"+newReqNo+"'  where user_id='"+empNo+"' and req_no='"+Req_Id+"'";
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
					+ "ess.employeeNumber='"+empNo+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
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
					String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' group by reqType";
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
						String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
						ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
							while(rsApproverDsg.next())
							{
								pApprover=rsApproverDsg.getString("ApproverId");
							}
							int i=0;
							String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
							saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+empNo+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+empNo+"','')";
							 i=ad.SqlExecuteUpdate(saveRecReq);
							 if(i > 0){
									leaveForm.setMessage("Leave Details Submitted Successfully");
								}
						}
						if(reqType.equalsIgnoreCase("parallel"))
						{
							String parallelApprover1="";
							String parallelApprover2="";
							String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
							ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
								while(rsApproverDsg.next())
								{
									pApprover=rsApproverDsg.getString("ApproverId");
									parallelApprover1=rsApproverDsg.getString("Parallel_Approver1");
									parallelApprover2=rsApproverDsg.getString("Parallel_Approver2");
									
								}
								int i=0;
								String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+empNo+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+empNo+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);
								leaveForm.setMessage("Leave Details Submitted Successfully");
								if(!(parallelApprover1.equalsIgnoreCase("")))
								{
									saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
									saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+empNo+"','"+submitDate+"','Pending','','"+parallelApprover1+"','No','"+empNo+"','')";
									 i=ad.SqlExecuteUpdate(saveRecReq);
								}
								if(!(parallelApprover2.equalsIgnoreCase("")))
								{
									saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
									saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+empNo+"','"+submitDate+"','Pending','','"+parallelApprover2+"','No','"+empNo+"','')";
									 i=ad.SqlExecuteUpdate(saveRecReq);
								}
								
						}
					}
					int i=0;
					if(existStatus==false){
				
					String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+Req_Id+"','Leave','"+empNo+"','"+submitDate+"','Pending','','"+pApprover+"','No','"+empNo+"','')";
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
					String getBalence="select lv_awtBal from lv_type_d where lv_empcode='"+empNo+"' and lv_typeid='"+leaveType+"' and lv_calyear='"+year+"'";
					ResultSet rsBalence=ad.selectQuery(getBalence);
					while(rsBalence.next()){
						balence=rsBalence.getFloat(1);
					}
					balence=balence+Float.parseFloat(noOfDays);
					String updateWatingBal="update  lv_type_d set lv_awtBal='"+balence+"' where lv_empcode='"+empNo+"' and lv_typeid='"+leaveType+"' and lv_calyear='"+year+"' ";
					i=ad.SqlExecuteUpdate(updateWatingBal);
					if(i > 0){
						leaveForm.setMessage("Leave Details Submitted Successfully");
					}
					if(existStatus==false  && !approvermail.equalsIgnoreCase("")){
					 int j = email.sendMailToOffice_EmpApprover(request, approvermail,Req_Id,"Leave",empNo);
					}
					
					if(existStatus==true  && !approvermail.equalsIgnoreCase("")){
						if(reqType.equalsIgnoreCase("sequential")){
							int j = email.sendMailToOffice_EmpApprover(request, approvermail,Req_Id,"Leave",empNo);
						}
						if(reqType.equalsIgnoreCase("parallel")){
							int j = email.sendMailToOffice_EmpApprover(request, approvermail,Req_Id,"Leave",empNo);
						}
					
						}
					
				}
				else
				{
					leaveForm.setMessage("Leave Details Saved in drafts Successfully");
				}
				String sql2="delete from temp_emp_leave_documents where user_id='"+empNo+"' and leave_type='"+leaveType+"' and req_no='"+leaveForm.getRequestNumber()+"'";
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
			String sql1="select * from temp_emp_leave_documents where user_id='"+empNo+"' and leave_type='"+leaveType+"'";
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
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HRLeaveForm leaveForm=(HRLeaveForm)form;
		String[] documentCheck=leaveForm.getDocumentCheck();
		HttpSession session=request.getSession();
		String empNo=leaveForm.getEmpNumber();
		UserInfo user=(UserInfo)session.getAttribute("user");
		setLeaveType(mapping, form, request, response);
		float clMaxDays=leaveForm.getClMaxDays();
		leaveForm.setClMaxDays(clMaxDays);
		float slMaxDays=leaveForm.getSlMaxDays();
		leaveForm.setSlMaxDays(slMaxDays);
		LinkedList appList = getApprovers(request, response,empNo,"Leave");
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
			String sql1="select * from temp_emp_leave_documents where user_id='"+empNo+"' and  req_no='"+leaveForm.getRequestNumber()+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			
			try{
				HRLeaveForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new HRLeaveForm();
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
		return mapping.findForward("newLeaveForm");
	
	}
	
	public ActionForward displayattendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm leaveForm=(HRLeaveForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String empNo=request.getParameter("empNo");
		System.out.println(empNo);
		

		//Attendance

		
		 String startDate1=(String)request.getParameter("StartDate");  
		String  k[]=startDate1.split("/");
		String str_date=k[2]+"-"+k[1]+"-"+k[0];
		  
			
		String endDate=(String)request.getParameter("EndDate");  
		String  g[]=endDate.split("/");
		  endDate=g[2]+"-"+g[1]+"-"+g[0];
		SAPAttendenceDAO dao = new SAPAttendenceDAO();

		String month=k[2]+k[1]+"01";



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
		String year1 = month.substring(month.length() - 4, month.length());

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

		String date1 ="";	




		ArrayList att = new ArrayList();

		cal.set(Integer.parseInt(year1), Integer.parseInt(monName)-1, 1);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int i=1;
		/*    String query="EXEC daily_report_status_Att '"+days+"','"+monName+"','"+year+"','"+empNo+"' ";
		int k=ad.SqlExecuteUpdate(query);*/

			String s9 = " select date,convert(varchar(11),date,106) as date1,left(DATENAME(WEEKDAY,date),3) as day,left(In_time,5) as intime,left(out_time,5) as outtime,"
					+ "left(status,2) as instatus,right(status,2) as outstatus,shift from Report_Daily_wise,emp_official_info where emp_official_info.PERNR=Report_Daily_wise.Pernr and emp_official_info.LOCID=Report_Daily_wise.Location and  emp_official_info.Pernr='"+empNo+"'  and year(date)='"+k[2]+"' "
							+ "and date between '"+str_date+"' and '"+endDate+"' order by date";
			ResultSet rs9 = ad.selectQuery(s9);
			try {
				while(rs9.next())
				{
					
					SAPAttendenceForm help = new SAPAttendenceForm();
					help.setDate(rs9.getString("date1"));
					help.setDay(rs9.getString("day"));
					date1=rs9.getString("date");
					
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
						  case "0006": help.setShift("SS1");
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
						  case "0017": help.setShift("GS");
						  break;
						  case "0018": help.setShift("NS");
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
						  case "0006": help.setShift("SS1");
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
						  case "0017": help.setShift("GS");
						  break;
						  case "0018": help.setShift("NS");
						  break;
								

					}
					            
			    	}
				   

			    	String data = "select Approvel_Status,reason from leave_details where  user_id='"
							+ empNo
							+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
							+  date1 
							+ "' between start_date and  end_date and YEAR(start_date)='"
							+ k[2] + "'";
					ResultSet rs10 = ad.selectQuery(data);
					while (rs10.next()) {
						if (rs10.getString("Approvel_Status").equalsIgnoreCase(
								"Approved"))
							help.setRemarks(rs10.getString("reason"));
						else
							help.setRemarks("Leave to be Approved");
					}
					rs10.close();
					String data1 = "select Approver_Status,reason from OnDuty_details where  user_id='"
							+empNo
							+ "' and Approver_Status in('Approved','In Process') and '"
							+ date1
							+ "' between start_date and  end_date  and YEAR(start_date)='"
							+ k[2] + "'";
					ResultSet rs101 = ad.selectQuery(data1);
					while (rs101.next()) {
						if (rs101.getString("Approver_Status")
								.equalsIgnoreCase("Approved"))
							help.setRemarks(rs101.getString("reason"));
						else
							help.setRemarks("Onduty to be Approved");
					} 

					rs101.close();
					



					String data11 = "select * from holidays where date = '"+date1+"' and location = '"+user.getPlantId()+"' ";

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
					rs1011.close();
				i++;
				
				attDataList.add(help);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rs9.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (attDataList.size() > 0) {
				request.setAttribute("attDataList", attDataList);
			}
	
		

		return mapping.findForward("displayattendance");
		
	}
	public ActionForward calculateDays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm leaveForm=(HRLeaveForm)form;
		HttpSession session=request.getSession();
		 double onedayPLDAYs=0;
		 String userLocation1="";
		 int plCount=0;
		 
		UserInfo user=(UserInfo)session.getAttribute("user");
		String empNo=request.getParameter("empNo");
		System.out.println(empNo);
		try{
			String userLocation="";
			String userLoc="select locid from emp_official_info where pernr='"+empNo+"'";
			ResultSet rsloc=ad.selectQuery(userLoc);
			while(rsloc.next()){
				userLocation=rsloc.getString("locid");
			}
			
			String tableName=getTableName(userLocation);
			
			String userLoc1="select LOCATION_CODE,Plcount from Location where LOCATION_CODE='"+userLocation+"'";
			ResultSet rsloc1=ad.selectQuery(userLoc1);
			while(rsloc1.next()){
				userLocation1=rsloc1.getString("LOCATION_CODE");
				plCount=rsloc1.getInt("Plcount");
			}
			
			/*String tableName=userLocation;*/
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
		String startDate=(String)request.getParameter("StartDate");  
		String reqStartDate=startDate;
		String startDateDuration=(String)request.getParameter("StartDur");
		String endDate=(String)request.getParameter("EndDate");
		String reqEndDate=endDate;
		String endDurationType=(String)request.getParameter("EndDur");
		String leaveType=(String)request.getParameter("LeaveType");
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
		String getLeaveBalence="select * from lv_type_d where lv_typeid='"+leaveType+"' and lv_empcode='"+empNo+"' and lv_calyear='"+year+"'";
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
				  
					 String startDate1=(String)request.getParameter("StartDate");  
					 String b[]=startDate1.split("/");
					 String str_date=b[2]+b[1]+b[0];
					 
					 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					 Date date = new Date();
					 String currentdate=dateFormat.format(date);
					 String c1[]=currentdate.split("/");
					 currentdate=c1[0]+c1[1]+c1[2];
					 
					 int cnt=0;
                     boolean abc=false;
                     
                   
                     
                  /*   
            //CHEKC leave can be applied after predefined days
                     String allowtoapply="";
     	            int applyafter=0;
     	            String lastdate="";
     	            
     	            String abcdef="select convert(date, dateadd(day,-(select Apply_after from Location where locid='16'),getdate()) ) lastdate,Apply_after,case when convert(date, dateadd(day,-(select Apply_after from Location where locid='"+user.getPlantId()+"'),getdate()) )<='"+(a1[2]+"-"+a1[1]+"-"+a1[0])+"' then 1 else 0 end as allowdate  from Location where locid='"+user.getPlantId()+"'";
     	            ResultSet nn=ad.selectQuery(abcdef);
     	            while(nn.next())
     	            {
     	           	 allowtoapply=nn.getString("allowdate");
     	           	applyafter=nn.getInt("Apply_after");
     	           	lastdate=nn.getString("lastdate");
     	            }
            
            
                     
                     if(allowtoapply.equalsIgnoreCase("0"))
                     {
                    	 noOfDays=-13.0;
                         leaveForm.setTotalLeaveDays(noOfDays);
                    	
                    		return mapping.findForward("calculateDays");
                     }
                     */
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
		
		String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+empNo+"' and lv_typeid=2 and lv_calyear='"+year+"'";
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
		
		String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+empNo+"' and lv_calyear='"+year+"'";
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
	             
	             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+empNo+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						cou="";
				             String getCountQuery1="";
				             //check emp cal
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
								 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
							 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						  cou="";
				             String getCountQuery1="";
				             //check emp cal
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
								 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			            String  empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
							 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
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
			
			//check holiday or not if statrt date and end date both same 
			 String cou="";
	         String checkDay="";
	         String checkDay1="";
	         //check emp cal
	         
	         String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
	      	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+empNo+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
	      	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+empNo+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
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
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+empNo+"'"; 
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
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'"; 
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
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' "; 
		             }
					 
					ResultSet rsCount = ad
							.selectQuery(getCountQuery);
					while (rsCount.next()) {
						noOfDays = rsCount.getInt(1);
					}

					
					cou="";
		             String getCountQuery1="";
		             //check emp cal
		             
		              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
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
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]	+ " and " + a1[0] + ") and "+ startMonth + " !='WO' and "+ startMonth + "!='SS' and "+ startMonth + " !='' and CYEAR='"+year+"'"; 
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
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"'"; 
		             }
					
					ResultSet rsCount = ad
							.selectQuery(getCountQuery);
					while (rsCount.next()) {
						noOfDays = rsCount.getInt(1);
					}
                    cou="";
		             String getCountQuery1="";
		             //check emp cal
		             
		              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 getCountQuery1 = "select count("	+ EndMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"'";	 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' "; 
			             }
						
						
						ResultSet rsCount = ad
								.selectQuery(getCountQuery);
						while (rsCount.next()) {
							noOfDays = noOfDays + rsCount.getInt(1);
						}

						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and " + a1[0] + ") and "+ startMonth + " ='W' and "+ startMonth + " !='' and CYEAR='"+year+"'"; 
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
				             
				             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
				            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' "; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
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
					+ empNo
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
			// check holiday or not if statrt date and end date both
			// same
			//check holiday or not if statrt date and end date both same 
			 String cou="";
	         String checkDay="";
	         String checkDay1="";
	         //check emp cal
	         
	         String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
	      	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+empNo+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
	      	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+empNo+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
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
	             
	             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+empNo+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
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
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
				            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and 31)  and "+midmonth+" !='' and CYEAR='"+year+"'";
				             }
				             }else{
				            	 if(cou.equalsIgnoreCase("0"))
					             {
					            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
								
					             }
					             else
					             {
					            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
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
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
							
				             }
				             else
				             {
				            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	  if(cou.equalsIgnoreCase("0"))
					             {
								 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
					             }
					             else
					             {
					            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	  if(cou.equalsIgnoreCase("0"))
					             {
								 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
					             }
					             else
					             {
					            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
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
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
				            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and 31)  and "+midmonth+" !='' and CYEAR='"+year+"'";
				             }
				             }else{
				            	 
				            	  if(cou.equalsIgnoreCase("0"))
						             {
						            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
									
						             }
						             else
						             {
						            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
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
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'";
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
							
				             }
				             else
				             {
				            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						
///two months diff
						
						if(staMont+2==endMnth)
						{
							
							 String midmonth = monthName[staMont+1];
							cou="";
				             String midmonthQuery="";
				             //check emp cal
				             
				              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
				            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
				             }
				             }else{
				            	 if(cou.equalsIgnoreCase("0"))
					             {
					            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
								
					             }
					             else
					             {
					            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
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
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+") and "+EndMonth+"='W' and "+EndMonth+" !='' and CYEAR='"+year+"'";
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !=''  and CYEAR='"+year+"'"; 
			             }	
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
				             }
				             else
				             {
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and "+startMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'"; 
				             }	
			            	 
			             }
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
					
						 
						///two months diff
							
							if(staMont+2==endMnth)
							{
								
								 String midmonth = monthName[staMont+1];
								 String cou="";
					             String midmonthQuery="";
					             //check emp cal
					             
					             String  empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
					            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and 31)  and "+midmonth+" !=''  and CYEAR='"+year+"'";
					             }
					             }else{
					            	 if(cou.equalsIgnoreCase("0"))
						             {
						            	 midmonthQuery="select count("+midmonth+") from "+tableName+" where (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
									
						             }
						             else
						             {
						            	 midmonthQuery="select count("+midmonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and 31)  and "+midmonth+" !='' and "+midmonth+" not in ('WO','SS','PH','SH','') and CYEAR='"+year+"'";
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
			             
			             String  empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
			             ResultSet  ac=ad.selectQuery(empca);
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
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'";
			             }
			             }else{
			            	 if(cou.equalsIgnoreCase("0"))
				             {
							 getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
							
				             }
				             else
				             {
				            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and  (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and "+EndMonth+" not in ('WO','SS','PH','SH') and CYEAR='"+year+"'";
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
         
         String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
      	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+empNo+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
      	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+empNo+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
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
		String maxtimeinyear="select COUNT(*) from leave_details where user_id='"+empNo+"' and leave_type='3'  and YEAR(start_date)='"+year+"' and Approvel_Status not in ('Self Cancelled','Cancelled','Rejected') ";
		ResultSet rsmaxtimeinyear=ad.selectQuery(maxtimeinyear);
		while(rsmaxtimeinyear.next()){
			maxcount=rsmaxtimeinyear.getInt(1);
		}
		/*if(maxcount>=privmaxtime)
		{
			noOfDays=-9.0;  
		}
	*/
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
	             
	             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+empNo+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
							 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
							 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' "; 
			             }
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
							 cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and 31) and " + startMonth+ " ='W' and " + startMonth + " !='' and CYEAR='"+year+"' "; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
			             ResultSet ac=ad.selectQuery(empca);
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
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and " + a1[0]+ ") and " + EndMonth + " ='W' and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
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
        
        String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
     	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+empNo+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
     	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+empNo+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
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
        
        String empca1="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
        	 checkEndDayType="select day,"+startMonth+" from EMP_CAL where Pernr='"+empNo+"' and day='"+a1[0]+"' and CYEAR='"+year+"'";
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
	             
	             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		             checkDay="select day,"+startMonth+" from EMP_CAL where day='"+a[0]+"' and CYEAR='"+year+"' and Pernr='"+empNo+"'"; 
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
				             
				             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
				            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						 cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
							 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
							 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31) and "+startMonth+" !='' and CYEAR='"+year+"'"; 
			             }
						
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
							
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''  and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery = "select count("+ startMonth + ") from EMP_CAL where Pernr='"+empNo+"' and (day between " + a[0]+ " and 31)  and " + startMonth + " !='' and CYEAR='"+year+"' "; 
			             }
						
						
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						
						cou="";
			             String getCountQuery1="";
			             //check emp cal
			             
			              empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery1 = "select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and " + a1[0]+ ")  and "+ EndMonth + " !='' and CYEAR='"+year+"' ";
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and "+a1[0]+")  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	  getCountQuery="select count("+startMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between "+a[0]+" and 31)  and "+startMonth+" !='' and CYEAR='"+year+"'"; 
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
			             
			            String  empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
							 getCountQuery1="select count("+EndMonth+") from EMP_CAL where Pernr='"+empNo+"' and (day between 1 and "+a1[0]+")  and "+EndMonth+" !='' and CYEAR='"+year+"'"; 
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
		String chkcasu="select * from leave_details where leave_type='1' and user_id='"+empNo+"'  and YEAR ( start_date )='"+year1+"' and Approvel_Status not in ('Self Cancelled','Cancelled','Rejected') and record_status!='Draft'";
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
				String chkcasu="select * from leave_details where leave_type='1' and user_id='"+empNo+"'  and YEAR ( start_date )='"+year1+"' and Approvel_Status not in ('Self Cancelled','Cancelled','Rejected') and record_status!='Draft'";
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
	         
	         String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
	      	    checkDay="select day,"+startMonth+" from EMP_CAL where Pernr='"+empNo+"' and  (day='"+a[0]+"' ) and CYEAR='"+year+"'";
	      	  checkDay1="select day,"+EndMonth+" from EMP_CAL where Pernr='"+empNo+"' and  ( day='"+ a1[0] + "') and CYEAR='"+year+"'";
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
		String cas="select * from leave_details where  (convert(nvarchar,start_date-1,103)='"+reqStartDate+"' or convert(nvarchar,end_date+1,103)='"+reqStartDate+"' or  convert(nvarchar,start_date-1,103)='"+reqEndDate+"' or convert(nvarchar,end_date+1,103)='"+reqEndDate+"') and leave_type='1' and user_id='"+empNo+"' and  Approvel_Status not in ('Self Cancelled','Cancelled','Rejected')  ";
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
  
  String lvStDate= (String)request.getParameter("StartDate"); 
  String lvEndDate=(String)request.getParameter("EndDate");
  String  e1[]=lvStDate.split("/");
  lvStDate=e1[2]+"-"+e1[1]+"-"+e1[0];
  String f[]=lvEndDate.split("/"); 
  lvEndDate=f[2]+"-"+f[1]+"-"+f[0];
  
	  try{
		  
				  
	
	  	String getAppliedDates="Select * from OnDuty_details where (OnDuty_status=0 or OnDuty_status=1) and  user_id='"+empNo+"' " +
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
	  
	  
	  
	  String checkPermission="Select * from Permission_details where (Approver_Status=0 or  Approver_Status=1) and  user_id='"+empNo+"'" +
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
						stm = monthName[staMont - 1];
					}
					 String cou="";
		             String table="";
		             //check emp cal
		             
		             
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 table="select "+stm+" as m from EMP_CAL where Pernr='"+empNo+"' and  DAY='"+fu+"' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	  table="select "+enm+" as m from EMP_CAL where Pernr='"+empNo+"' and DAY='"+eu+"' and CYEAR='"+year+"'"; 
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
							+ empNo
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (1,4,5,6)";
					}
					if(stcounter>1)
					{
						 cas = "select * from leave_details where  ( convert(nvarchar,end_date+"
									+ stcounter
									+ ",103)='"
									+ reqStartDate
									+ "')  and user_id='"
									+ empNo
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
							+ empNo
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (1,4,5,6)";
					}
					if(encounter>1)
					{

					 cas1 = "select * from leave_details where   ( convert(nvarchar,start_date-"
							+ encounter
							+ ",103)='"
							+ reqEndDate
							+ "')  and user_id='"
							+ empNo
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
				  
				  
			  String checkSL="select * from leave_details where user_id='"+empNo+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
			  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"')";
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
						stm = monthName[staMont - 1];
					}
					 String cou="";
		             String table="";
		             //check emp cal
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 table="select "+stm+" as m from EMP_CAL where Pernr='"+empNo+"' and  DAY='"+fu+"' and CYEAR='"+year+"'"; 
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
				             
				             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
				            	  table="select "+enm+" as m from EMP_CAL where Pernr='"+empNo+"' and DAY='"+eu+"' and CYEAR='"+year+"'"; 
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
							+ empNo
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (2,4,5,6)";
					}
					
					if(stcounter>1)
					{
						
						 cas = "select * from leave_details where  ( convert(nvarchar,end_date+"
									+ stcounter
									+ ",103)='"
									+ reqStartDate
									+ "')  and user_id='"
									+ empNo
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
							+ empNo
							+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type not in (2,4,5,6)";
					}
					
					if(encounter>1)
					{
						 cas1 = "select * from leave_details where   ( convert(nvarchar,start_date-"
									+ encounter
									+ ",103)='"
									+ reqEndDate
									+ "')  and user_id='"
									+ empNo
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
				  String checkSL="select * from leave_details where user_id='"+empNo+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
				  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"')";
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
						stm = monthName[staMont - 1];
					}
					  String cou="";
			             String table="";
			             //check emp cal
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	 table="select "+stm+" as m from EMP_CAL where Pernr='"+empNo+"' and  DAY='"+fu+"' and CYEAR='"+year+"'"; 
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
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	  table="select "+enm+" as m from EMP_CAL where Pernr='"+empNo+"' and DAY='"+eu+"' and CYEAR='"+year+"'"; 
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
							+ empNo
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
					
					
			
					
					String oneday = "select *,convert(nvarchar,end_date,103) as endd from leave_details where  (( convert(nvarchar,end_date+"+stcounter+",103)='"+reqStartDate+"') or ( convert(nvarchar,end_date,103)='"+reqStartDate+"'))  and user_id='"+empNo+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type=3";
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
							+ empNo
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
					
	//CHK nofdays 0 or not and allow to apply 1 day pl in sequence
					
					
				
					
					String oneday1 = "select *,convert(nvarchar,start_date,103) as startt from leave_details where  ( ( convert(nvarchar,start_date-"+ encounter+ ",103)='"+ reqEndDate	+ "') or   ( convert(nvarchar,start_date,103)='"+ reqEndDate	+ "')) and user_id='"+ empNo	+ "' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved') and leave_type=3";
					
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
					
			  
				  
				  String checkSL="select * from leave_details where user_id='"+empNo+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
				  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"')";
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
		             
		             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
		            	 table="select "+stm+" as m from EMP_CAL where Pernr='"+empNo+"' and  DAY='"+fu+"' and CYEAR='"+year+"'"; 
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
			             
			             String empca="Select count(*) as cou from EMP_CAL where Pernr='"+empNo+"' and CYEAR='"+year+"'";
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
			            	  table="select "+enm+" as m from EMP_CAL where Pernr='"+empNo+"' and DAY='"+eu+"' and CYEAR='"+year+"'"; 
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
					String cas="select * from leave_details where  ( convert(nvarchar,end_date+"+stcounter+",103)='"+reqStartDate+"')  and user_id='"+empNo+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')";
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
					
					String cas1="select * from leave_details where   ( convert(nvarchar,start_date-"+encounter+",103)='"+reqEndDate+"')  and user_id='"+empNo+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')";
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
				  
				  String checkSL="select * from leave_details where user_id='"+empNo+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
				  + "and (start_date between '"+lvStDate+"' and '"+lvEndDate+"') and (end_date between '"+lvStDate+"' and '"+lvEndDate+"')";
				  ResultSet rsCheckSL=ad.selectQuery(checkSL);
				  if(rsCheckSL.next()){
					 noOfDays=-4.0;
				  }
				}
		      
		      if(leaveType.equalsIgnoreCase("6"))
		      {
		      String checkSL="select * from leave_details where user_id='"+empNo+"' and record_status!='Draft' and (Approvel_Status='Pending' or Approvel_Status='Approved')"
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
	  "where user_id='"+empNo+"' and leave_type='"+leaveType+"' and '"+reqStartDate+"'>=l.end_date) as  sub Where  sub.RowNum between 1 and 1 order by RowNum desc";
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
 	"where user_id='"+empNo+"' and leave_type='"+leaveType+"' and '"+reqStartDate+"'<=l.start_date ) as  sub Where  sub.RowNum between 1 and 1 order by RowNum desc";
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
	  "where user_id='"+empNo+"' and leave_type='"+leaveType+"' ) as  sub Where  sub.RowNum between 1 and 2 order by RowNum desc";
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
				
				
			String emp = "select locid from location where LOCATION_CODE=(select LOCID from emp_official_info where pernr = '"+a+"')";
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
			
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			return b;
			
		}
	 
		public ActionForward submitnewcomp(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			HRLeaveForm masterForm = (HRLeaveForm) form;
			String empNo=masterForm.getEmpNumber();
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
						+ "' between FromDate and ToDate) or (FromDate between '"+ fromdate+"' and '"+ todate+"') or (ToDate between '"+ fromdate+"' and '"+ todate+"')) and ApprvrStatus!='Rejected'";
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
									+ empNo
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
							
							String hodreq="select * from location where LOCATION_CODE=(select LOCID from emp_official_info where pernr = '"+empNo+"' )";
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
							String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' group by reqType";
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
								String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
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
											+ empNo
											+ "','"
											+ submitDate
											+ "','Pending','No','"
											+ pApprover
											+ "','No','"
											+ empNo + "')";
									j = ad.SqlExecuteUpdate(insert);
									masterForm.setMessage("Request No. '" + reqid
											+ "' Submitted ");
									
									
									String update="update COMP_OT set Pending_approver='"+Empname(pApprover)+"',Last_approver='' where Req_no='"+reqid+"'";
									int h = ad.SqlExecuteUpdate(update);
									
								}
								if(reqType.equalsIgnoreCase("parallel"))
								{
									
									String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
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
												+ empNo
												+ "','"
												+ submitDate
												+ "','Pending','No','"
												+ pApprover
												+ "','No','"
												+ empNo + "')";
										j = ad.SqlExecuteUpdate(insert);
										masterForm.setMessage("Request No. '" + reqid
												+ "' Submitted ");
										
										if(!(parallelApprover1.equalsIgnoreCase("")))
										{
											 insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
													+ reqid
													+ "','Comp-Off/OT','"
													+ empNo
													+ "','"
													+ submitDate
													+ "','Pending','No','"
													+ parallelApprover1
													+ "','No','"
													+ empNo + "')";
											j = ad.SqlExecuteUpdate(insert);
											
										}
										if(!(parallelApprover2.equalsIgnoreCase("")))
										{
											 insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
														+ reqid
														+ "','Comp-Off/OT','"
														+ empNo
														+ "','"
														+ submitDate
														+ "','Pending','No','"
														+ parallelApprover2
														+ "','No','"
														+empNo + "')";
												j = ad.SqlExecuteUpdate(insert);
										}
										
										String update="update COMP_OT set Pending_approver='"+Empname(pApprover)+"',Last_approver='' where Req_no='"+reqid+"'";
										int h = ad.SqlExecuteUpdate(update);
										
								}
							}
							if(existStatus==false){
							// PLANT HEAD
							String plant="";
							if(!EmpLoc(empNo).equalsIgnoreCase("17"))
							{
							 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where"
							 		+ " Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
							
							}
							else
							{
								
								 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where"
								 		+ " Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  "
								 				+ "and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+empNo+"')";
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
									+ empNo
									+ "','"
									+ submitDate
									+ "','Pending','No','"
									+ planthead
									+ "','No','"
									+ empNo+ "')";
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
								if(!EmpLoc(empNo).equalsIgnoreCase("17"))
								{
								 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
								
								}
								else
								{
									
									 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID"
									 		+ "  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+empNo+"')";
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
										+ empNo
										+ "','"
										+ submitDate
										+ "','Pending','No','"
										+ planthead
										+ "','No','"
										+ empNo+ "')";
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
									+ empNo
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
			
			LinkedList appList = getApprovers(request, response,empNo,"COMP_OT");		
			request.setAttribute("appList", appList);
			
			}
			
			// PLANT HEAD
					String plant="";
					if(!EmpLoc(empNo).equalsIgnoreCase("17"))
					{
					 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
					
					}
					else
					{
						
						 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+empNo+"')";
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
	 
		
		public ActionForward submitnewOT(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			

			HRLeaveForm masterForm = (HRLeaveForm) form;
			String empNo=masterForm.getEmpNumber();

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
						+ "' between FromDate and ToDate) or (FromDate between '"+ fromdate+"' and '"+ todate+"') or (ToDate between '"+ fromdate+"' and '"+ todate+"')) and ApprvrStatus!='Rejected'";
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
									+ empNo
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
							
							String hodreq="select * from location where LOCATION_CODE=(select LOCID from emp_official_info where pernr = '"+empNo+"' )";
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
							String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' group by reqType";
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
								String getApproverDesig="select ApproverId   from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='sequential'  and  Priority=1";
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
											+ empNo
											+ "','"
											+ submitDate
											+ "','Pending','No','"
											+ pApprover
											+ "','No','"
											+ empNo + "')";
									j = ad.SqlExecuteUpdate(insert);
									masterForm.setMessage("Request No. '" + reqid
											+ "' Submitted ");
									
									
									String update="update OVER_TIME_DETAILS set Pending_approver='"+Empname(pApprover)+"',Last_approver='' where Req_no='"+reqid+"'";
									int h = ad.SqlExecuteUpdate(update);
									
								}
								if(reqType.equalsIgnoreCase("parallel"))
								{
									
									String getApproverDesig="select * from ESS_Approvers where employeeNumber='"+empNo+"' and essType='Leave' and reqType='parallel'  and  Priority=1";
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
												+ empNo
												+ "','"
												+ submitDate
												+ "','Pending','No','"
												+ pApprover
												+ "','No','"
												+ empNo + "')";
										j = ad.SqlExecuteUpdate(insert);
										masterForm.setMessage("Request No. '" + reqid
												+ "' Submitted ");
										
										if(!(parallelApprover1.equalsIgnoreCase("")))
										{
											 insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
													+ reqid
													+ "','OverTime','"
													+ empNo
													+ "','"
													+ submitDate
													+ "','Pending','No','"
													+ parallelApprover1
													+ "','No','"
													+ empNo + "')";
											j = ad.SqlExecuteUpdate(insert);
											
										}
										if(!(parallelApprover2.equalsIgnoreCase("")))
										{
											 insert = "insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id) values('"
														+ reqid
														+ "','OverTime','"
														+ empNo
														+ "','"
														+ submitDate
														+ "','Pending','No','"
														+ parallelApprover2
														+ "','No','"
														+ empNo + "')";
												j = ad.SqlExecuteUpdate(insert);
										}
										
										String update="update OVER_TIME_DETAILS set Pending_approver='"+Empname(pApprover)+"',Last_approver='' where Req_no='"+reqid+"'";
										int h = ad.SqlExecuteUpdate(update);
										
								}
							}
							if(existStatus==false){
							// PLANT HEAD
							String plant="";
							if(!EmpLoc(empNo).equalsIgnoreCase("17"))
							{
							 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
							
							}
							else
							{
								
								 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID"
								 		+ "  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+empNo+"')";
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
									+ empNo
									+ "','"
									+ submitDate
									+ "','Pending','No','"
									+ planthead
									+ "','No','"
									+ empNo + "')";
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
								if(!EmpLoc(empNo).equalsIgnoreCase("17"))
								{
								 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
								
								}
								else
								{
									
									 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID"
									 		+ "  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+empNo+"')";
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
										+ empNo
										+ "','"
										+ submitDate
										+ "','Pending','No','"
										+ planthead
										+ "','No','"
										+ empNo + "')";
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
			
			LinkedList appList = getApprovers(request, response,empNo,"OT");		
			request.setAttribute("appList", appList);
			
			}
			// PLANT HEAD
			String plant="";
			if(!EmpLoc(empNo).equalsIgnoreCase("17"))
			{
			 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
			
			}
			else
			{
				
				 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+empNo+"')";
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
		
		public ActionForward displaynewOT(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			HRLeaveForm masterForm=(HRLeaveForm)form;
			
			String employeeNo=masterForm.getEmpNumber();
			 String empNo=masterForm.getEmpNumber();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			String module=request.getParameter("id"); 
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				request.setAttribute("MenuIcon", module);
				return mapping.findForward("displayiFrameSession");
			}
		
				
				
				 String hrLocID=user.getPlantId();
				 boolean checkEmpExist=false;
				 
				 String checkEmpStatus="select * from emp_official_info as emp,Location as loc where PERNR='"+empNo+"' and loc.LOCID='"+hrLocID+"'";
				 ResultSet rsEmpStatus=ad.selectQuery(checkEmpStatus);
				try{
					 while(rsEmpStatus.next()){
						 masterForm.setEmpName(empNo+" - "+rsEmpStatus.getString("EMP_FULLNAME"));
						 checkEmpExist=true;
					 }
				}catch (Exception e) {
					masterForm.setEmpStatus("Employee Number Not Exist Or Invalid Employee Number.Please Check");
					return mapping.findForward("search");
				}
				if(checkEmpExist==false){
					masterForm.setEmpStatus("Employee Number Not Exist Or Invalid Employee Number.Please Check");
					return mapping.findForward("search");
				}
				
				
				
			// PLANT HEAD
			String plant="";
			//
			String HODAPPROVALREQURED = "";
			// plant head
			
			String hodreq="select * from location where LOCATION_CODE=(select LOCID from emp_official_info where pernr = '"+empNo+"' )";
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
			
			LinkedList appList = getApprovers(request, response,empNo,"Permission");	
			request.setAttribute("appList", appList);
			
			}
			
			
			if(!EmpLoc(empNo).equalsIgnoreCase("17"))
			{
			 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where"
			 		+ " Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
			
			}
			else
			{
				
				 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where"
				 		+ " Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  "
				 				+ "and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+empNo+"')";
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
					return mapping.findForward("search");
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
			
			

			return mapping.findForward("newOT");
		}
		public ActionForward displaynewcomp(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			HRLeaveForm masterForm=(HRLeaveForm)form;
			
			String employeeNo=masterForm.getEmpNumber();
			 String empNo=masterForm.getEmpNumber();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			String module=request.getParameter("id"); 
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				request.setAttribute("MenuIcon", module);
				return mapping.findForward("displayiFrameSession");
			}
		
				
				
				 String hrLocID=user.getPlantId();
				 boolean checkEmpExist=false;
				 
				 String checkEmpStatus="select * from emp_official_info as emp,Location as loc where PERNR='"+empNo+"' and loc.LOCID='"+hrLocID+"'";
				 ResultSet rsEmpStatus=ad.selectQuery(checkEmpStatus);
				try{
					 while(rsEmpStatus.next()){
						 masterForm.setEmpName(empNo+" - "+rsEmpStatus.getString("EMP_FULLNAME"));
						 checkEmpExist=true;
					 }
				}catch (Exception e) {
					masterForm.setEmpStatus("Employee Number Not Exist Or Invalid Employee Number.Please Check");
					return mapping.findForward("search");
				}
				if(checkEmpExist==false){
					masterForm.setEmpStatus("Employee Number Not Exist Or Invalid Employee Number.Please Check");
					return mapping.findForward("search");
				}
				
				
				
			// PLANT HEAD
			String plant="";
			//
			String HODAPPROVALREQURED = "";
			// plant head
			
			String hodreq="select * from location where LOCATION_CODE=(select LOCID from emp_official_info where pernr = '"+empNo+"' )";
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
			
			LinkedList appList = getApprovers(request, response,empNo,"Permission");	
			request.setAttribute("appList", appList);
			
			}
			
			
			if(!EmpLoc(empNo).equalsIgnoreCase("17"))
			{
			 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where"
			 		+ " Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID";
			
			}
			else
			{
				
				 plant = "select p.*,e.EMP_FULLNAME,ds.DSGSTXT,de.DPTSTXT from PLANT_HEAD_COMP_OT p,emp_official_info e,DESIGNATION ds,DEPARTMENT de where"
				 		+ " Plant=(select LOCID from emp_official_info where pernr = '"+empNo+"') and e.PERNR=p.PERNR and e.DSGID=ds.DSGID and e.DPTID=de.DPTID  "
				 				+ "and Pay_grp=(select PAY_GROUP from emp_official_info where pernr='"+empNo+"')";
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
					return mapping.findForward("search");
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
	 
		
		
	public ActionForward newLeaveRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm leaveForm=(HRLeaveForm)form;
		
		String employeeNo=leaveForm.getEmpNumber();
		 String empNo=leaveForm.getEmpNumber();
		 leaveForm.setEmployeeNumber(empNo);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		try{
			
			
			 String hrLocID=user.getPlantId();
			 boolean checkEmpExist=false;
			 
			 String checkEmpStatus="select * from emp_official_info as emp,Location as loc where PERNR='"+empNo+"' and loc.LOCID='"+hrLocID+"'";
			 ResultSet rsEmpStatus=ad.selectQuery(checkEmpStatus);
			try{
				 while(rsEmpStatus.next()){
					 leaveForm.setEmpName(empNo+" - "+rsEmpStatus.getString("EMP_FULLNAME"));
					 checkEmpExist=true;
				 }
			}catch (Exception e) {
				leaveForm.setEmpStatus("Employee Number Not Exist Or Invalid Employee Number.Please Check");
				return mapping.findForward("search");
			}
			if(checkEmpExist==false){
				leaveForm.setEmpStatus("Employee Number Not Exist Or Invalid Employee Number.Please Check");
				return mapping.findForward("search");
			}
				 
			
			

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
			String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+empNo+"' and lv_calyear='"+year+"'";
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
			
			
			leaveForm.setYear(year);
		String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+empNo+"' and t.lv_calyear='"+year+"' and t.lv_typeid=m.lv_typeid";
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
		
		LinkedList leaveTypeID=new LinkedList();
		LinkedList leaveType=new LinkedList();
		String getGender="select SEX from emp_official_info where PERNR='"+empNo+"'";
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
				String getLeaveType="select * from lv_type_m where lv_typeid not in (5)";
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
		LinkedList appList = getApprovers(request, response, empNo,"Leave");
		
		request.setAttribute("appList", appList);
		
		if(appList.size()==0)
		{
			leaveForm.setMessage("No Approvers assigned.Please Contact Admin");
			return mapping.findForward("leaveBal");
			
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
	
	public LinkedList getApprovers(HttpServletRequest request, HttpServletResponse response,String employyNumber,String essType) {
		
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
		String getApprovers="select APPMGR,RPTMGR  from emp_official_info  emp where " +
				"PERNR='"+user.getEmployeeNo()+"'  ";	
		ResultSet rs=ad.selectQuery(getApprovers);
		while(rs.next())
		{
			approverID=rs.getString("APPMGR");
			reportingMgrID=rs.getString("RPTMGR");
		}
		boolean existStatus=false;
		String reqType="";
		String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+employyNumber+"' and essType='Leave' group by reqType";
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
					"where employeeNumber='"+employyNumber+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' order by Priority ";
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
			String getApproverDesig="select e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
					"where employeeNumber='"+employyNumber+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and e.essType='Leave'";
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
				String getParallelApprover1="select e.Parallel_Approver1,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp," +
						"DESIGNATION as desg  where employeeNumber='"+employyNumber+"' and  e.Parallel_Approver1=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'";
				rsApproverDsg=ad.selectQuery(getParallelApprover1);
			while(rsApproverDsg.next())
			{
				LeaveForm leaveForm1=new LeaveForm();
				leaveForm1.setApptype("Approving Manager");
				leaveForm1.setApproverID(rsApproverDsg.getString("Parallel_Approver1"));
				appDesig=rsApproverDsg.getString("DSGSTXT");
				approverName=rsApproverDsg.getString("EMP_FULLNAME");
				leaveForm1.setAppDesig(appDesig);
				leaveForm1.setApproverName(approverName);
				approverList.add(leaveForm1);
			}
			String getParallelApprover2="select e.Parallel_Approver2,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp," +
			"DESIGNATION as desg  where employeeNumber='"+employyNumber+"' and  e.Parallel_Approver2=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'";
	rsApproverDsg=ad.selectQuery(getParallelApprover2);
	while(rsApproverDsg.next())
	{
		LeaveForm leaveForm1=new LeaveForm();
		leaveForm1.setApptype("Approving Manager");
		leaveForm1.setApproverID(rsApproverDsg.getString("Parallel_Approver2"));
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
				" emp.PERNR='"+approverID+"' and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID ";
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
	
	public void setLeaveType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HRLeaveForm leaveForm=(HRLeaveForm)form;
				
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
					String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"' and t.lv_calyear='2014' and t.lv_typeid=m.lv_typeid";
					ResultSet rsBalance=ad.selectQuery(getleaveBalence);
					ArrayList balList=new ArrayList();
					while(rsBalance.next())
					{
					   leaveForm.setYear(rsBalance.getInt("lv_calyear"));
					   HRLeaveForm form2=new HRLeaveForm();
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
	
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HRLeaveForm leaveForm=(HRLeaveForm)form;
			
		String empNo=leaveForm.getEmpNumber();
			FormFile documentFile=leaveForm.getDocumentFile();
			String documentName=documentFile.getFileName();
			String leaveType=leaveForm.getLeaveType();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			float clMaxDays=leaveForm.getClMaxDays();
			leaveForm.setClMaxDays(clMaxDays);
			float slMaxDays=leaveForm.getSlMaxDays();
			leaveForm.setSlMaxDays(slMaxDays);
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
				
		
			 LinkedList appList = getApprovers(request, response,empNo,"Leave");
				request.setAttribute("appList", appList);
			 }
			 
			 }catch(FileNotFoundException fe){
				fe.printStackTrace();
			}catch(IOException ie){
				ie.printStackTrace();
			}
			 
	          String contentType =documentFile.getContentType();
			  String fileName   =  documentFile.getFileName();
			
			String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
			try{
				if(ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("txt")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg"))
				{
				String sql9="select count(*) from temp_emp_leave_documents  where user_id='"+empNo+"' and leave_type='"+leaveType+"' and file_name='"+documentFile.getFileName()+"'";
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
					"values('"+empNo+"','"+documentFile.getFileName()+"','"+leaveType+"','"+leaveForm.getRequestNumber()+"')";
					int a=ad.SqlExecuteUpdate(sql);
					if(a>0)
						{
						leaveForm.setMessage("Documents Uploaded Successfully");
						}
				}
				}
				else{
					leaveForm.setMessage("Please upload files with doc,docx,pdf or jpg extensions");
				}
			String sql1="select * from temp_emp_leave_documents where user_id='"+empNo+"' and leave_type='"+leaveType+"' and req_no='"+leaveForm.getRequestNumber()+"'";
			ResultSet rs=ad.selectQuery(sql1);
			HRLeaveForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new HRLeaveForm();
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
			return mapping.findForward("newLeaveForm");
			
	}

	
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRLeaveForm form2=(HRLeaveForm)form;
		 HttpSession session=request.getSession();
		 UserInfo user=(UserInfo)session.getAttribute("user");
		 
		
		
		return mapping.findForward("search");
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
		if(location.equalsIgnoreCase("ML27"))
       		tableName="ML27CAL";
		if(location.equalsIgnoreCase("ML92"))
			tableName="ML92CAL";
		return tableName;
	}
}
