package com.microlabs.hr.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.OnDutyForm;
import com.microlabs.hr.form.HRApprovalForm;
import com.microlabs.hr.form.HRReportForm;
import com.microlabs.main.db.MainDao;
import com.microlabs.myrequest.form.MyRequestForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class HRReportAction extends DispatchAction {
	
	private String filterby;
	private String Departemnt;
	private String EmpNo;
	private String HrfromDate;
	private String HrtoDate;
	
	public ActionForward OTApprovedListRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		HRReportForm hr=(HRReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String reqType=hr.getReqRequstType();
		String type=hr.getSelectedFilter();
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String exportdate=ft.format(dNow);

		response.setHeader("Content-Disposition", "inline; filename="+exportdate+"_OT_Summary_Report.xls");
		
		
		
		String hrFromDate=hr.getHrFromDate();
		
		String hrToDate=hr.getHrToDate();
	
		String empno=EmpNo;
		
		if(empno==null)
		{
			empno="";
		}
		 String a1[]=hrFromDate.split("/");
		 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
		
		String b[]=hrToDate.split("/");
		 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
		 
		 
			String cmpcode="";
			int id=user.getGroupId();
			String cmp[]=new String[0]; 
			String locations="";
			
			String aa="select * from user_group where id='"+id+"'";
			ResultSet cc=ad.selectQuery(aa);
			try {
				if(cc.next())
				{
					cmpcode=cc.getString("Attendance_Loc");
					
					if(cmpcode.contains(","))
					{
					 cmp=cmpcode.split(",");
					
					for(int b1=0;b1<cmp.length;b1++)
					{
						locations=locations+"'"+cmp[b1]+"',";
					}
					
					locations=locations.substring(0, locations.length()-1);
					}
					else
					{
						locations="'"+cmpcode+"'";
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ArrayList clist=new ArrayList();
			String h="";
			if(empno.equalsIgnoreCase(""))
			{
		 h="select EMP_FULLNAME,DPTSTXT,Report_Daily_wise.* from Report_Daily_wise,emp_official_info ,DEPARTMENT where ot is not null and "
		 		+ "emp_official_info.LOCID in ("+locations+")and (Report_Daily_wise.Date between '"+hrFromDate+"' and '"+hrToDate+"')and"
		 				+ " emp_official_info.PERNr=Report_Daily_wise.pernr and DEPARTMENT.DPTID=emp_official_info.DPTID order by Pernr";
			}
			else
			{
				 h="select EMP_FULLNAME,DPTSTXT,Report_Daily_wise.* from Report_Daily_wise,emp_official_info ,DEPARTMENT where ot is not null and "
					 		+ "emp_official_info.LOCID in ("+locations+")and (Report_Daily_wise.Date between '"+hrFromDate+"' and '"+hrToDate+"')and"
					 				+ " emp_official_info.PERNr=Report_Daily_wise.pernr and DEPARTMENT.DPTID=emp_official_info.DPTID and Pernr='"+empno+"' order by Pernr";	}
		
		
		ResultSet g=ad.selectQuery(h);
		try {
			while(g.next())
			{
				HRApprovalForm leave = new HRApprovalForm();
				leave.setEmployeeName(g.getString("EMP_FULLNAME"));
				leave.setDepartment(g.getString("DPTSTXT"));
				leave.setEmployeeNumber(g.getString("pernr"));
				leave.setStartDate(EMicroUtils.display(g.getDate("Date")));	
				leave.setStartTime(g.getString("In_time").substring(0, 5));
				leave.setEndTime(g.getString("Out_time").substring(0, 5));
				leave.setLocationId(g.getString("location"));
				leave.setOt(g.getString("OT"));
				leave.setShift(g.getString("shift"));
			
				leave.setTotal(g.getString("total"));
				
				
				
				clist.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.setAttribute("clist", clist);
		
		
		return mapping.findForward("ExportOT");
		
	}
	
	public ActionForward CompApprovedListRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		HRReportForm hr=(HRReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String reqType=hr.getReqRequstType();
		String type=hr.getSelectedFilter();
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String exportdate=ft.format(dNow);

		response.setHeader("Content-Disposition", "inline; filename="+exportdate+"_CompOff_Summary_Report.xls");
		
		
		
		String hrFromDate=hr.getHrFromDate();
		
		String hrToDate=hr.getHrToDate();
	
		String empno=EmpNo;
		
		if(empno==null)
		{
			empno="";
		}
		 String a1[]=hrFromDate.split("/");
		 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
		
		String b[]=hrToDate.split("/");
		 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
		 
		 
			String cmpcode="";
			int id=user.getGroupId();
			String cmp[]=new String[0]; 
			String locations="";
			
			String aa="select * from user_group where id='"+id+"'";
			ResultSet cc=ad.selectQuery(aa);
			try {
				if(cc.next())
				{
					cmpcode=cc.getString("Attendance_Loc");
					
					if(cmpcode.contains(","))
					{
					 cmp=cmpcode.split(",");
					
					for(int b1=0;b1<cmp.length;b1++)
					{
						locations=locations+"'"+cmp[b1]+"',";
					}
					
					locations=locations.substring(0, locations.length()-1);
					}
					else
					{
						locations="'"+cmpcode+"'";
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ArrayList clist=new ArrayList();
			String h="";
			if(empno.equalsIgnoreCase(""))
			{
		 h="select EMP_FULLNAME,DPTSTXT,comp_ot_sap.* From comp_ot_sap,emp_official_info ,DEPARTMENT where emp_official_info.LOCID in"
				+ " ("+locations+")and (comp_ot_sap.Start_Date between '"+hrFromDate+"' and '"+hrToDate+"')and emp_official_info.PERNr=comp_ot_sap.Employee_Number and"
				+ " DEPARTMENT.DPTID=emp_official_info.DPTID";
			}
			else
			{
				 h="select EMP_FULLNAME,DPTSTXT,comp_ot_sap.* From comp_ot_sap,emp_official_info ,DEPARTMENT where emp_official_info.LOCID in"
							+ " ("+locations+")and (comp_ot_sap.Start_Date between '"+hrFromDate+"' and '"+hrToDate+"')and emp_official_info.PERNr=comp_ot_sap.Employee_Number and"
							+ " DEPARTMENT.DPTID=emp_official_info.DPTID and emp_official_info.pernr='"+empno+"'";
			}
		
		
		ResultSet g=ad.selectQuery(h);
		try {
			while(g.next())
			{
				LeaveForm leave = new LeaveForm();
				leave.setEmpName(g.getString("EMP_FULLNAME"));
				leave.setDepartment(g.getString("DPTSTXT"));
				leave.setEmpno(g.getString("Employee_Number"));
				leave.setStartDate(EMicroUtils.display(g.getDate("Start_Date")));
				leave.setEndDate(EMicroUtils.display(g.getDate("End_Date")));
				leave.setNofhrs(g.getString("Number_of_Days"));
				leave.setNoOfDays(g.getString("Comp_Off_Balance"));
				if(!g.getString("Comp_Off_Availed_Date").contains("1900"))
				leave.setStartDurationType(EMicroUtils.display(g.getDate("Comp_Off_Availed_Date")));
				
				
				clist.add(leave);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.setAttribute("clist", clist);
		
		
		return mapping.findForward("ExportCompff");
		
	}
	
	public ActionForward getHrPermissionReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	
	{
		HRReportForm approvalsForm=(HRReportForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		
		String reqType=filterby;
		 String Dept=Departemnt;
			String Empno=EmpNo;
			String reqList="";
			
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentLabelList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			try {
				while(rs12.next()){
					
					deparmentList.add(rs12.getString("DPTID"));
					departmentLabelList.add(rs12.getString("DPTSTXT"));
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			approvalsForm.setDepartmentList(deparmentList);
			approvalsForm.setDepartmentLabelList(departmentLabelList);
		 
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			if(user == null)
			{
				approvalsForm.setAppMessage("Session Expried! Try to Login again!");
				return mapping.findForward("approvePage");
			}
			int totalCount=0;
			String getTotalCount="";
			String hrFromDate=request.getParameter("hrFromDate");
			 String hrToDate=request.getParameter("hrToDate");
			 
			 String a1[]=hrFromDate.split("/");
			 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
			
			String b[]=hrToDate.split("/");
			 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
			 
			 String cmpcode="";
				int id=user.getGroupId();
				String cmp[]=new String[0]; 
				String locations="";
				
				String aa="select * from user_group where id='"+id+"'";
				ResultSet cc=ad.selectQuery(aa);
				try {
					if(cc.next())
					{
						cmpcode=cc.getString("Attendance_Loc");
						
						if(cmpcode.contains(","))
						{
						 cmp=cmpcode.split(",");
						
						for(int b1=0;b1<cmp.length;b1++)
						{
							locations=locations+"'"+cmp[b1]+"',";
						}
						
						locations=locations.substring(0, locations.length()-1);
						}
						else
						{
							locations="'"+cmpcode+"'";
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 
			
			try{
				
				LinkedList 	permList=new LinkedList();
				String getPermissionRecords="";
				
				if(reqType.equalsIgnoreCase("Pending")){
					getPermissionRecords="Select emp.locid,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date,all_R.Requester_Name,dept.DPTSTXT,all_R.Hr_Id,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
					"emp_official_info as emp, Permission_details as perm,DEPARTMENT as dept,DESIGNATION as desg where all_R.Req_Status='"+reqType+"' and" +
					" all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission' and (perm.date between '"+hrFromDate+"' and '"+hrToDate+"') and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and  emp.LOCID in ("+locations+") ";
					if(!Empno.equals(""))
						getPermissionRecords=getPermissionRecords+" and emp.PERNR='"+Empno+"'";
					if(!Dept.equals(""))
						getPermissionRecords=getPermissionRecords+" and dept.DPTSTXT='"+Dept+"' ";
					
					getPermissionRecords=getPermissionRecords+" group by emp.locid,all_R.Req_Id,date,all_R.Requester_Name,dept.DPTSTXT,all_R.Hr_Id,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type order by Req_Id desc";
					
						}
					
					if(reqType.equalsIgnoreCase("Approved"))
					{
				getPermissionRecords="Select emp.locid,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
					"all_R.Requester_Name,all_R.Hr_Id,dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
				"emp_official_info as emp, Permission_details as perm,DEPARTMENT as dept,DESIGNATION as desg where all_R.Req_Status='"+reqType+"' and" +
				" all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission' and (perm.date between '"+hrFromDate+"' and '"+hrToDate+"') and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and  emp.LOCID in ("+locations+") ";
				if(!Empno.equals(""))
					getPermissionRecords=getPermissionRecords+" and emp.PERNR='"+Empno+"'";	
				if(!Dept.equals(""))
						getPermissionRecords=getPermissionRecords+" and dept.DPTSTXT='"+Dept+"' ";
					
					getPermissionRecords=getPermissionRecords+" group by emp.locid,all_R.Req_Id,date,all_R.Requester_Name,dept.DPTSTXT,all_R.Hr_Id,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type order by Req_Id desc";
					
				}
			
					if(reqType.equalsIgnoreCase("All")){
					getPermissionRecords="Select emp.locid,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
					"all_R.Requester_Name,all_R.Hr_Id,dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
				"emp_official_info as emp, Permission_details as perm,DEPARTMENT as dept,DESIGNATION as desg where " +
				" all_R.Req_Id=perm.request_no  and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission' and (perm.date between '"+hrFromDate+"' and '"+hrToDate+"') and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and  emp.LOCID in ("+locations+") ";
					if(!Empno.equals(""))
						getPermissionRecords=getPermissionRecords+" and emp.PERNR='"+Empno+"'";
					if(!Dept.equals(""))
						getPermissionRecords=getPermissionRecords+" and dept.DPTSTXT='"+Dept+"' ";
					
					getPermissionRecords=getPermissionRecords+" group by emp.locid,all_R.Req_Id,date,all_R.Requester_Name,dept.DPTSTXT,all_R.Hr_Id,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type order by Req_Id desc";
					
				}		
				ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
				while(rsPermission.next()){
					MyRequestForm permission=new MyRequestForm();
					permission.setType(rsPermission.getString("type"));
					permission.setRequestNumber(rsPermission.getString("Req_Id"));
		            permission.setStartTime(rsPermission.getString("startTime"));
			          permission.setEndTime(rsPermission.getString("endTime"));
		        	permission.setPermissiondate(rsPermission.getString("date"));
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
					permission.setStatus(rsPermission.getString("Req_Status"));
					permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
					
					permission.setDesignation(rsPermission.getString("DSGSTXT"));
					permission.setDepartment(rsPermission.getString("DPTSTXT"));
					String url="";
					
					permission.setUrl(url);
					//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
					//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));

						String HRId=rsPermission.getString("Hr_Id");

					//Approvers
					String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsPermission.getString("Requester_Name")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
					ResultSet rsData=ad.selectQuery(getapp);
					while(rsData.next())
					{
						permission.setApprover(rsData.getString("EMP_FULLNAME"));
					}
					
					if(HRId!=null)
					{
						
						if( reqType.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs1=ad.selectQuery(hrdetails);
						
						while(rs1.next())
						{
							permission.setApprover(rs1.getString("EMP_FULLNAME"));
							permission.setApproverDesg(rs1.getString("DSGSTXT"));
						}
					}
						
					}
					permList.add(permission);
						
				}
				request.setAttribute("Permissionlist", permList);
				
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			String dateNow = ft.format(dNow);
			 approvalsForm.setDate(dateNow);
			 
				}catch (Exception e) {
					e.printStackTrace();
				}
		
		
		return mapping.findForward("HrLeaves");
	}
	
	public ActionForward getHrOndutyReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRReportForm approvalsForm=(HRReportForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		
		 String reqType=filterby;
		 String Dept=Departemnt;
			String Empno=EmpNo;
			String reqList="";
			
		
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentLabelList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			try {
				while(rs12.next()){
					
					deparmentList.add(rs12.getString("DPTID"));
					departmentLabelList.add(rs12.getString("DPTSTXT"));
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			approvalsForm.setDepartmentList(deparmentList);
			approvalsForm.setDepartmentLabelList(departmentLabelList);
		
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			approvalsForm.setAppMessage("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		int totalCount=0;
		String getTotalCount="";
		
		String hrFromDate=request.getParameter("hrFromDate");
		 String hrToDate=request.getParameter("hrToDate");
		 
		 String a1[]=hrFromDate.split("/");
		 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
		
		String b[]=hrToDate.split("/");
		 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
		
		 String cmpcode="";
			int id=user.getGroupId();
			String cmp[]=new String[0]; 
			String locations="";
			
			String aa="select * from user_group where id='"+id+"'";
			ResultSet cc=ad.selectQuery(aa);
			try {
				if(cc.next())
				{
					cmpcode=cc.getString("Attendance_Loc");
					
					if(cmpcode.contains(","))
					{
					 cmp=cmpcode.split(",");
					
					for(int b1=0;b1<cmp.length;b1++)
					{
						locations=locations+"'"+cmp[b1]+"',";
					}
					
					locations=locations.substring(0, locations.length()-1);
					}
					else
					{
						locations="'"+cmpcode+"'";
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 
		
		try{
		
		LinkedList 	ondutyList=new LinkedList();
		String getOndutyRecords="";
		
		if(reqType.equalsIgnoreCase("Pending")){
				getOndutyRecords="Select  emp.locid,all_R.Req_Id,all_R.Requester_Name ,all_R.Req_Status,ond.location,emp.PERNR," +
				"emp.EMP_FULLNAME,dept.DPTSTXT,all_R.Hr_Id,desg.DSGSTXT,all_R.Req_Date,ond.start_date,ond.startTime,ond.end_date,ond.endTime,ond.onDuty_Type " +
				" from  All_Request as all_R,emp_official_info as emp,OnDuty_details as ond,DEPARTMENT as dept,DESIGNATION as desg where " +
				"Req_Status='"+reqType+"' and all_R.Req_Type='On Duty' and (ond.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and all_R.Requester_Name=emp.PERNR and ond.request_no=all_R.Req_Id and" +
				" emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and  emp.LOCID in ("+locations+") ";
				if(!Empno.equals(""))
					getOndutyRecords=getOndutyRecords+" and emp.PERNR='"+Empno+"'";
				if(!Dept.equals(""))
					getOndutyRecords=getOndutyRecords+" and dept.DPTSTXT='"+Dept+"' ";
				
				getOndutyRecords=getOndutyRecords+" order by Req_Id desc";	
				
		}
		if(reqType.equalsIgnoreCase("Approved")){
				getOndutyRecords="Select emp.locid,all_R.Req_Id,all_R.Requester_Name ,all_R.Req_Status,ond.location,emp.PERNR," +
				"emp.EMP_FULLNAME,dept.DPTSTXT,all_R.Hr_Id,desg.DSGSTXT,all_R.Req_Date,ond.start_date,ond.startTime,ond.end_date,ond.endTime,ond.onDuty_Type " +
				" from  All_Request as all_R,emp_official_info as emp,OnDuty_details as ond,DEPARTMENT as dept,DESIGNATION as desg where " +
				"Req_Status='"+reqType+"' and all_R.Req_Type='On Duty' and (ond.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and all_R.Requester_Name=emp.PERNR and ond.request_no=all_R.Req_Id and" +
				" emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and  emp.LOCID in ("+locations+")  ";
				if(!Empno.equals(""))
					getOndutyRecords=getOndutyRecords+" and emp.PERNR='"+Empno+"'";
				if(!Dept.equals(""))
					getOndutyRecords=getOndutyRecords+" and dept.DPTSTXT='"+Dept+"' ";
				
				getOndutyRecords=getOndutyRecords+" order by Req_Id desc";
					
		}
	
		if(reqType.equalsIgnoreCase("All")){
			getOndutyRecords="Select emp.locid,all_R.Req_Id,all_R.Requester_Name ,all_R.Req_Status,ond.location,emp.PERNR," +
			"emp.EMP_FULLNAME,dept.DPTSTXT,all_R.Hr_Id,desg.DSGSTXT,all_R.Req_Date,ond.start_date,ond.startTime,ond.end_date,ond.endTime,ond.onDuty_Type " +
			" from  All_Request as all_R,emp_official_info as emp,OnDuty_details as ond,DEPARTMENT as dept,DESIGNATION as desg where (all_R.Req_Status='Pending' or all_R.Req_Status='Approved' ) and " +
			"  all_R.Req_Type='On Duty' and (ond.start_date between '"+hrFromDate+"' and '"+hrToDate+"')  and all_R.Requester_Name=emp.PERNR and ond.request_no=all_R.Req_Id and" +
			" emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and  emp.LOCID in ("+locations+")";
			if(!Empno.equals(""))
				getOndutyRecords=getOndutyRecords+" and emp.PERNR='"+Empno+"'";
			if(!Dept.equals(""))
				getOndutyRecords=getOndutyRecords+" and dept.DPTSTXT='"+Dept+"' ";
			
			getOndutyRecords=getOndutyRecords+" order by Req_Id desc";
		}
		
		
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
			onduty.setDesignation(rsOnduty.getString("DSGSTXT"));
			onduty.setDepartment(rsOnduty.getString("DPTSTXT"));
			
			onduty.setEndTime(rsOnduty.getString("endTime"));
			onduty.setStatus(rsOnduty.getString("Req_Status"));
			
            String HRId=rsOnduty.getString("Hr_Id");
			
			//Approvers
			String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsOnduty.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
			ResultSet rsData=ad.selectQuery(getapp);
			while(rsData.next())
			{
				onduty.setApprover(rsData.getString("EMP_FULLNAME"));
			}
			
			if(HRId!=null)
			{
				
				if( reqType.equalsIgnoreCase("Approved")){
				String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
				
				ResultSet rs1=ad.selectQuery(hrdetails);
				
				while(rs1.next())
				{
					onduty.setApprover(rs1.getString("EMP_FULLNAME"));
					onduty.setApproverDesg(rs1.getString("DSGSTXT"));
				}
			}
				
			}
			ondutyList.add(onduty);
			}
		request.setAttribute("ondutyList", ondutyList);
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
	String dateNow = ft.format(dNow);
	 approvalsForm.setDate(dateNow);
	 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("HrLeaves");
	
	}
	public ActionForward getHrLeaveReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRReportForm approvalsForm=(HRReportForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		
		 String reqType=filterby;
		 String Dept=Departemnt;
			String Empno=EmpNo;
			String reqList="";
			
			
		
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentLabelList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			try {
				while(rs12.next()){
					
					deparmentList.add(rs12.getString("DPTID"));
					departmentLabelList.add(rs12.getString("DPTSTXT"));
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			approvalsForm.setDepartmentList(deparmentList);
			approvalsForm.setDepartmentLabelList(departmentLabelList);
			
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			approvalsForm.setAppMessage("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		int totalCount=0;
		String getTotalCount="";
		
		 String hrFromDate=request.getParameter("hrFromDate");
		 String hrToDate=request.getParameter("hrToDate");
		 
		 String a1[]=hrFromDate.split("/");
		 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
		
		String b[]=hrToDate.split("/");
		 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
		
		try{
		
		LinkedList leaveList=new LinkedList();
		String getLeaveRecords="";
		
		String cmpcode="";
		int id=user.getGroupId();
		String cmp[]=new String[0]; 
		String locations="";
		
		String aa="select * from user_group where id='"+id+"'";
		ResultSet cc=ad.selectQuery(aa);
		try {
			if(cc.next())
			{
				cmpcode=cc.getString("Attendance_Loc");
				
				if(cmpcode.contains(","))
				{
				 cmp=cmpcode.split(",");
				
				for(int b1=0;b1<cmp.length;b1++)
				{
					locations=locations+"'"+cmp[b1]+"',";
				}
				
				locations=locations.substring(0, locations.length()-1);
				}
				else
				{
					locations="'"+cmpcode+"'";
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(reqType.equalsIgnoreCase("Pending")){
		getLeaveRecords="Select emp.locid,all_R.Req_Id,leave.leave_type,all_R.Req_Status,emp.PERNR,emp.EMP_FULLNAME," +
		"dept.DPTSTXT,desg.DSGSTXT,all_R.Req_Date,all_R.Hr_Id,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days,leave.reasonType" +
		"  from  All_Request as all_R,emp_official_info as emp," +
		"leave_details as leave,DEPARTMENT as dept,DESIGNATION as desg where  Req_Status='"+reqType+"'  and all_R.Req_Type='Leave' and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"') " +
		"and all_R.Requester_Name=emp.PERNR and leave.Req_Id=all_R.Req_Id and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and  emp.LOCID in ("+locations+")  ";
		if(!Empno.equals(""))
			getLeaveRecords=getLeaveRecords+" and emp.PERNR='"+Empno+"'";
		if(!Dept.equals(""))
			getLeaveRecords=getLeaveRecords+" and dept.DPTSTXT='"+Dept+"' ";
		
		getLeaveRecords=getLeaveRecords+" group by emp.locid,all_R.Req_Id,leave.leave_type,all_R.Req_Status,emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,all_R.Req_Date,all_R.Hr_Id,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days,leave.reasonType order by Req_Id desc";	
		}
		
		if(reqType.equalsIgnoreCase("Approved")){
		getLeaveRecords="Select emp.locid,all_R.Req_Id,leave.leave_type,all_R.Req_Status,emp.PERNR,emp.EMP_FULLNAME," +
		"dept.DPTSTXT,desg.DSGSTXT,all_R.Req_Date,all_R.Hr_Id,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days,leave.reasonType" +
		"  from  All_Request as all_R,emp_official_info as emp," +
		"leave_details as leave,DEPARTMENT as dept,DESIGNATION as desg where  Req_Status='"+reqType+"' and   (Actual_Approver_status is null or Actual_Approver_status='') and all_R.Req_Type='Leave'  " +
		"and all_R.Requester_Name=emp.PERNR and leave.Req_Id=all_R.Req_Id and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and  emp.LOCID in ("+locations+") ";
		if(!Empno.equals(""))
			getLeaveRecords=getLeaveRecords+" and emp.PERNR='"+Empno+"'";
		if(!Dept.equals(""))
			getLeaveRecords=getLeaveRecords+" and dept.DPTSTXT='"+Dept+"' ";
		
		getLeaveRecords=getLeaveRecords+" group by emp.locid,all_R.Req_Id,leave.leave_type,all_R.Req_Status,emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,all_R.Req_Date,all_R.Hr_Id,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days,leave.reasonType order by Req_Id desc";
		
		}
		if(reqType.equalsIgnoreCase("All")){
		getLeaveRecords="Select emp.locid,all_R.Req_Id,leave.leave_type,all_R.Req_Status,emp.PERNR,emp.EMP_FULLNAME," +
		"dept.DPTSTXT,desg.DSGSTXT,all_R.Req_Date,all_R.Hr_Id,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days,leave.reasonType" +
		"  from  All_Request as all_R,emp_official_info as emp," +
		"leave_details as leave,DEPARTMENT as dept,DESIGNATION as desg where (Req_Status='Pending' or Req_Status='Approved' ) and    all_R.Req_Type='Leave'  " +
		"and all_R.Requester_Name=emp.PERNR and leave.Req_Id=all_R.Req_Id and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and  emp.LOCID in ("+locations+") ";
		if(!Empno.equals(""))
			getLeaveRecords=getLeaveRecords+" and emp.PERNR='"+Empno+"'";
		if(!Dept.equals(""))
			getLeaveRecords=getLeaveRecords+" and dept.DPTSTXT='"+Dept+"' ";
		
		getLeaveRecords=getLeaveRecords+" group by emp.locid,all_R.Req_Id,leave.leave_type,all_R.Req_Status,emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,all_R.Req_Date,all_R.Hr_Id,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days,leave.reasonType order by Req_Id desc";
		}
		
		ResultSet rsLeave=ad.selectQuery(getLeaveRecords);
		while(rsLeave.next()){
		LeaveForm leave=new LeaveForm();
		leave.setLocationId(rsLeave.getString("locid"));
		leave.setRequestNumber(rsLeave.getInt("Req_Id"));
		leave.setEmployeeNumber(rsLeave.getString("PERNR"));
		leave.setDepartment(rsLeave.getString("DPTSTXT"));
		leave.setDesignation(rsLeave.getString("DSGSTXT"));
	String	leaveType=rsLeave.getString("leave_type");
		if(leaveType.equalsIgnoreCase("1")){
			leaveType="CL";
		}
		if(leaveType.equalsIgnoreCase("2")){
			leaveType="SL";
		}
		if(leaveType.equalsIgnoreCase("3")){
			leaveType="PL";
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
		String reason=rsLeave.getString("reasonType");
		if(reason.equalsIgnoreCase("Personal"))
			reason="Personal-Activities to be taken care by self only";
		if(reason.equalsIgnoreCase("Bereavement"))
			reason="Bereavement-Death in the employees immediate family";
		if(reason.equalsIgnoreCase("Funeral"))
			reason="Funeral-Death in the employee relation";
		if(reason.equalsIgnoreCase("Compassionate"))
			reason="Compassionate-Provide care for family person illness";
		if(reason.equalsIgnoreCase("Medical"))
			reason="Medical-Personal health";
		if(reason.equalsIgnoreCase("Others"))
			reason="Others-Misc reasons";
		
		leave.setReason(reason);
		leave.setApproveStatus(rsLeave.getString("Req_Status"));
		

		String getLeaveBal="select * from lv_type_d where lv_empcode='"+rsLeave.getInt("PERNR")+"'";
		ResultSet rsbal=ad.selectQuery(getLeaveBal);
		while(rsbal.next())
		{
			if(rsbal.getInt("lv_typeid")==1)
			{
				leave.setCl(rsbal.getDouble("lv_clbal"));
			}
			if(rsbal.getInt("lv_typeid")==2)
			{
	    leave.setSl(rsbal.getString("lv_clbal"));
			}
			if(rsbal.getInt("lv_typeid")==3)
			{
	    leave.setPl(rsbal.getString("lv_clbal"));
			}
			if(rsbal.getInt("lv_typeid")==6)
			{
	    leave.setComp(rsbal.getString("lv_clbal"));
			}
		}
		
		String HRId=rsLeave.getString("Hr_Id");
		//Approvers
		String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsLeave.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
		ResultSet rsData=ad.selectQuery(getapp);
		while(rsData.next())
		{
			leave.setApproverName(rsData.getString("EMP_FULLNAME"));
		}
		
		if(HRId!=null)
		{
			
			if(reqType.equalsIgnoreCase("Approved")){
			String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
			
			ResultSet rs1=ad.selectQuery(hrdetails);
			
			while(rs1.next())
			{
				leave.setApproverName(rs1.getString("EMP_FULLNAME"));
				leave.setAppDesig(rs1.getString("DSGSTXT"));
			}
		}
			
		}
		leaveList.add(leave);
		}
		request.setAttribute("leaveList", leaveList);
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
	String dateNow = ft.format(dNow);
	 approvalsForm.setDate(dateNow);
	 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("HrLeaves");
	
	}
	
	public ActionForward displayReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HRReportForm hr=(HRReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		
		ArrayList deparmentList=new ArrayList();
		ArrayList departmentLabelList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
		try {
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTID"));
				departmentLabelList.add(rs12.getString("DPTSTXT"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		hr.setDepartmentList(deparmentList);
		hr.setDepartmentLabelList(departmentLabelList);
		
		return mapping.findForward("display");
}
	
	
	public ActionForward pendingRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HRReportForm hr=(HRReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String reqType=hr.getReqRequstType();
		String type=hr.getSelectedFilter();
		
		filterby=type;
		String hrfromdate=hr.getHrFromDate();
		HrfromDate=hrfromdate;
		String hrtodate=hr.getHrToDate();
		HrtoDate=hrtodate;
		
		ArrayList deparmentList=new ArrayList();
		ArrayList departmentLabelList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
		try {
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTID"));
				departmentLabelList.add(rs12.getString("DPTSTXT"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		hr.setDepartmentList(deparmentList);
		hr.setDepartmentLabelList(departmentLabelList);
		
		
		String Dept=hr.getDepartment();
		Departemnt=Dept;
		String empno=hr.getEmployeeNo();
		EmpNo=empno;
	
		  int  totalRecords = getCountForTable(hr.getSelectedFilter(),user.getEmployeeNo(),hr.getReqRequstType(),hr.getHrFromDate(),hr.getHrToDate(),Dept,empno,user);
		  int  startRecord=0;
		  int  endRecord=0;
			 if(totalRecords>10)
			 {
				 hr.setTotalRecords(totalRecords);
			 startRecord=1;
			 endRecord=10;
			 hr.setStartRecord(1);
			 hr.setEndRecord(10);
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=1400;
				  hr.setTotalRecords(totalRecords);
				  hr.setStartRecord(1);
				  hr.setEndRecord(totalRecords); 
			 }
			 
			 String hrFromDate=hr.getHrFromDate();
			 String hrToDate=hr.getHrToDate();
			 
			 String a1[]=hrFromDate.split("/");
			 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
			
			String b[]=hrToDate.split("/");
			 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
			 
			 
				String cmpcode="";
				int id=user.getGroupId();
				String cmp[]=new String[0]; 
				String locations="";
				
				String aa="select * from user_group where id='"+id+"'";
				ResultSet cc=ad.selectQuery(aa);
				try {
					if(cc.next())
					{
						cmpcode=cc.getString("Attendance_Loc");
						
						if(cmpcode.contains(","))
						{
						 cmp=cmpcode.split(",");
						
						for(int b1=0;b1<cmp.length;b1++)
						{
							locations=locations+"'"+cmp[b1]+"',";
						}
						
						locations=locations.substring(0, locations.length()-1);
						}
						else
						{
							locations="'"+cmpcode+"'";
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 
			 if(reqType.equalsIgnoreCase("OT"))
				{
				 LinkedList ot = new LinkedList();
					String 	reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,RequestedDate,ApprvrStatus,FromDate,ToDate,reason,ApprvdDate from over_time_details lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
							+ "all_r.Req_Type='OverTime' and lv.RequestedBy=all_r.Requester_Id and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and all_r.Req_Status='"+type+"' and    (Actual_Approver_status is null or Actual_Approver_status='') and  emp.LOCID in ("+locations+") ";
					if(!empno.equals(""))
						reqList=reqList+"and (lv.PERNR="+empno+") " ;
					if(!Dept.equals(""))
						reqList=reqList+" and emp.DPTID='"+Dept+"'";
					
					reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
					
					
					
					 if(type.equalsIgnoreCase("All")){
			    			reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate from over_time_details lv,All_Request all_r,emp_official_info emp,DEPARTMENT dept where all_r.Req_Id=lv.Req_no and "
			    			+ "all_r.Req_Type='OverTime' and lv.RequestedBy=all_r.Requester_Id  and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
			    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID in ("+locations+")  ";
			    			if(!empno.equals(""))
			    				reqList=reqList+"and (lv.PERNR="+empno+" ) " ;
			    			if(!Dept.equals(""))
			    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
			    			
							reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
				 		}
					 
					ResultSet rsit = ad.selectQuery(reqList);
					try {
						while (rsit.next()) {
							LeaveForm leave = new LeaveForm();
							leave.setRequestNumber(rsit.getInt("Req_no"));
							leave.setEmployeeName(rsit.getString("emp_fullname"));
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

							ot.add(leave);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (ot.size() == 0) {
						request.setAttribute("noot", "noot");
					} else {
						request.setAttribute("ot", ot);
					}
					
					if(ot.size()>0){
						if(type.equalsIgnoreCase("Pending")){
							request.setAttribute("displayButton", "displayButton");
							}
						}
					
						
				}
			 
			 if(reqType.equalsIgnoreCase("Comp-Off"))
				{
				 LinkedList comp = new LinkedList();
					String 	reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,RequestedDate,ApprvrStatus,SAP_Approved,FromDate,ToDate,reason,ApprvdDate from comp_ot lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
							+ "all_r.Req_Type='Comp-Off/OT' and lv.RequestedBy=all_r.Requester_Id and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and all_r.Req_Status='"+type+"' and    (Actual_Approver_status is null or Actual_Approver_status='') and  emp.LOCID in ("+locations+") ";
					if(!empno.equals(""))
						reqList=reqList+"and (lv.PERNR="+empno+") " ;
					if(!Dept.equals(""))
						reqList=reqList+" and emp.DPTID='"+Dept+"'";
					
					reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,SAP_Approved,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
					
					
					
					 if(type.equalsIgnoreCase("All")){
			    			reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,ApprvrStatus,SAP_Approved,RequestedDate,FromDate,ToDate,reason,ApprvdDate from comp_ot lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
			    			+ "all_r.Req_Type='Comp-Off/OT' and lv.RequestedBy=all_r.Requester_Id  and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
			    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID in ("+locations+")  ";
			    			if(!empno.equals(""))
			    				reqList=reqList+"and (lv.PERNR="+empno+" ) " ;
			    			if(!Dept.equals(""))
			    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
			    			
							reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,SAP_Approved,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
				 		}
					 
					ResultSet rsit = ad.selectQuery(reqList);
					try {
						while (rsit.next()) {
							LeaveForm leave = new LeaveForm();
							leave.setRequestNumber(rsit.getInt("Req_no"));
							leave.setEmployeeName(rsit.getString("emp_fullname"));
							leave.setSubmitDate(EMicroUtils.display(rsit
									.getDate("RequestedDate")));
							leave.setStatus(rsit.getString("ApprvrStatus"));
							if(rsit.getString("SAP_Approved").equalsIgnoreCase("1")){
								leave.setApproveStatus("Credited");
								
							}else{
								leave.setApproveStatus("Pending");
								
							}
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
					
					if(comp.size()>0){
						if(type.equalsIgnoreCase("Pending")){
							request.setAttribute("displayButton", "displayButton");
							}
						}
					
						
				}
			 
		if(reqType.equalsIgnoreCase("Leave"))
		{

			LinkedList leaveList=new LinkedList();
			String getLeaveRecords="";
			
			
				getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,leave.leave_type,emp.PERNR," +
				"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
				"emp_official_info as emp,leave_details as leave where   Req_Status='"+type+"' and   (Actual_Approver_status is null or Actual_Approver_status='') and all_R.Req_Type='Leave' and "
				+ "emp.PERNR=leave.user_id  and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"')  and  all_R.Requester_Name=emp.PERNR  and leave.Req_Id=all_R.Req_Id  and  emp.LOCID in ("+locations+")  ";
				if(!empno.equals(""))
				getLeaveRecords=getLeaveRecords+"and emp.PERNR="+empno+"";
				if(!Dept.equals(""))
					getLeaveRecords=getLeaveRecords+"and emp.DPTID='"+Dept+"'";
				
				getLeaveRecords=getLeaveRecords+" group by all_R.Req_Id,leave.leave_type,emp.PERNR,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
			
	
			if(type.equalsIgnoreCase("All")){
				
				getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,leave.leave_type,emp.PERNR," +
						"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
						"emp_official_info as emp,leave_details as leave where   (all_R.Req_Status='Pending' or all_R.Req_Status='Approved' ) and all_R.Req_Type='Leave'  and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and emp.PERNR=leave.user_id  and  all_R.Requester_Name=emp.PERNR  and leave.Req_Id=all_R.Req_Id and  emp.LOCID in ("+locations+")    ";
				if(!empno.equals(""))
				getLeaveRecords=getLeaveRecords+" and emp.PERNR='"+empno+"'";
				if(!Dept.equals(""))
					getLeaveRecords=getLeaveRecords+" and emp.DPTID='"+Dept+"'";
				
				getLeaveRecords=getLeaveRecords+" group by all_R.Req_Id,leave.leave_type,emp.PERNR,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
			}
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
			
			String HRId=rsLeave.getString("Hr_Id");
			
			//Approvers
			String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsLeave.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
			ResultSet rsData=ad.selectQuery(getapp);
			while(rsData.next())
			{
				leave.setApproverName(rsData.getString("EMP_FULLNAME"));
			}
			
			if(HRId!=null)
			{
				
				if( type.equalsIgnoreCase("Approved")){
				String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
				
				ResultSet rs=ad.selectQuery(hrdetails);
				
				while(rs.next())
				{
					leave.setApproverName(rs.getString("EMP_FULLNAME"));
					leave.setAppDesig(rs.getString("DSGSTXT"));
				}
			}
				
			}
			
			leaveList.add(leave);
			}
			if(leaveList.size()>0){
			if(type.equalsIgnoreCase("Pending")){
				request.setAttribute("displayButton", "displayButton");
				}
			}
			request.setAttribute("leaveList", leaveList);
			if(leaveList.size()==0){
				request.setAttribute("no Leave records", "no Leave records");
				
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		
		}
		if(reqType.equalsIgnoreCase("On Duty"))
		{

			LinkedList ondutyList=new LinkedList();
			String getOndutyRecords="";
			
			getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
					"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date " +
					"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty,DEPARTMENT dept,Location as loc where  all_R.Req_Status='"+type+"'  and onduty.start_date between '"+hrFromDate+"' and '"+hrToDate+"' and all_R.Req_Id=onduty.request_no " +
					"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty' and dept.DPTID=emp.DPTID and emp.PERNR=onduty.user_id  and  emp.LOCID=loc.LOCATION_CODE and   emp.LOCID in ("+locations+") ";
					if(!empno.equals(""))
						getOndutyRecords=getOndutyRecords+" and emp.PERNR="+empno+"";
						if(!Dept.equals(""))
							getOndutyRecords=getOndutyRecords+" and emp.DPTID='"+Dept+"'";
						
						getOndutyRecords=getOndutyRecords+" group by all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date )as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";  	
			
				if(type.equalsIgnoreCase("All")){
					
					getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
							"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date " +
							"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty,DEPARTMENT dept,Location as loc where  (all_R.Req_Status='Approved' or all_R.Req_Status='Pending') and onduty.start_date between '"+hrFromDate+"' and '"+hrToDate+"' and all_R.Req_Id=onduty.request_no " +
							"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty' and dept.DPTID=emp.DPTID and emp.PERNR=onduty.user_id and  emp.LOCID=loc.LOCATION_CODE and  emp.LOCID in ("+locations+")";
					if(!empno.equals(""))
						getOndutyRecords=getOndutyRecords+" and emp.PERNR="+empno+"";
						if(!Dept.equals(""))
							getOndutyRecords=getOndutyRecords+" and emp.DPTID='"+Dept+"'";
						getOndutyRecords=getOndutyRecords+" group by all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date )as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc"; 
			
			}
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
			onduty.setStatus(rsOnduty.getString("Req_Status"));
			
			String HRId=rsOnduty.getString("Hr_Id");
			
			//Approvers
			String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsOnduty.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
			ResultSet rsData=ad.selectQuery(getapp);
			while(rsData.next())
			{
				onduty.setApprover(rsData.getString("EMP_FULLNAME"));
			}
			
			if(HRId!=null)
			{
				
				if( type.equalsIgnoreCase("Approved")){
				String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
				
				ResultSet rs=ad.selectQuery(hrdetails);
				
				while(rs.next())
				{
					onduty.setApprover(rs.getString("EMP_FULLNAME"));
					onduty.setApproverDesg(rs.getString("DSGSTXT"));
				}
			}
				
			}
			ondutyList.add(onduty);
			}
			if(ondutyList.size()>0){
			if(type.equalsIgnoreCase("Pending")){
				request.setAttribute("displayButton", "displayButton");
				}
			}
			request.setAttribute("ondutyList", ondutyList);
			if(ondutyList.size()==0){
				request.setAttribute("no OnDuty records", "no OnDuty records");
				
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			 
			
		
	 }
		
		if(reqType.equalsIgnoreCase("Permission"))
		{

			 LinkedList permList=new LinkedList();
				String getPermissionRecords="";
			
				String Hrlocation=user.getPlantId();
				
				getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS RowNum,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
						"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
					"emp_official_info as emp, Permission_details as perm where  all_R.Req_Status='"+type+"'  and perm.date between '"+hrFromDate+"' and '"+hrToDate+"' " +
					"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission'  and  emp.LOCID in ("+locations+") ";
					if(!empno.equals(""))
						getPermissionRecords=getPermissionRecords+" and emp.PERNR="+empno+"";
						if(!Dept.equals(""))
							getPermissionRecords=getPermissionRecords+" and emp.DPTID='"+Dept+"'";
						
						getPermissionRecords=getPermissionRecords+" group by all_R.Req_Id,perm.date, all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";  	
				
						if(type.equalsIgnoreCase("All")){
							getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS RowNum,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
									"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
								"emp_official_info as emp, Permission_details as perm where  (all_R.Req_Status='Approved' or all_R.Req_Status='Pending')  and perm.date between '"+hrFromDate+"' and '"+hrToDate+"' " +
								"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission'  and  emp.LOCID in ("+locations+") ";
						if(!empno.equals(""))
							getPermissionRecords=getPermissionRecords+" and emp.PERNR="+empno+"";
							if(!Dept.equals(""))
								getPermissionRecords=getPermissionRecords+" and emp.DPTID='"+Dept+"'";
							
							getPermissionRecords=getPermissionRecords+" group by all_R.Req_Id,perm.date, all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						}	
				
				
				ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
				try{
				while(rsPermission.next())
				{
					MyRequestForm permission=new MyRequestForm();
					permission.setType(rsPermission.getString("type"));
					permission.setRequestNumber(rsPermission.getString("Req_Id"));
		            permission.setStartTime(rsPermission.getString("startTime"));
			          permission.setEndTime(rsPermission.getString("endTime"));
		        	permission.setPermissiondate(rsPermission.getString("date"));
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
					permission.setStatus(rsPermission.getString("Req_Status"));
					permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
					String url="";
					
					permission.setUrl(url);
					//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
					//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
					String HRId=rsPermission.getString("Hr_Id");

					//Approvers
					String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsPermission.getString("Requester_Name")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
					ResultSet rsData=ad.selectQuery(getapp);
					while(rsData.next())
					{
						permission.setApprover(rsData.getString("EMP_FULLNAME"));
					}
					
					if(HRId!=null)
					{
						
						if( type.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs=ad.selectQuery(hrdetails);
						
						while(rs.next())
						{
							permission.setApprover(rs.getString("EMP_FULLNAME"));
							permission.setApproverDesg(rs.getString("DSGSTXT"));
						}
					}
						
					}
					permList.add(permission);
						
				}
				
				if(permList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					request.setAttribute("Permissionlist", permList);
					if(permList.size()==0){
						request.setAttribute("no Permission records", "no Permission records");
						
					}
					}catch (Exception e) {
						e.printStackTrace();
					}
			 
		 
			
			
		}
		
		
		
		
		
		hr.setHrFromDate(hrfromdate);
		hr.setHrToDate(hrtodate);
		
		
		return mapping.findForward("display");
	
}
	
	public int getCountForTable(String reqStatus, String fullName, String reqType,String hrFromDate,String hrToDate,String Dept,String Empno,UserInfo user)
	{
		
		String a[]=hrFromDate.split("/");
		 hrFromDate=a[2]+"-"+a[1]+"-"+a[0];
		
		String b[]=hrToDate.split("/");
		 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
		
    	int rowCount = 0;
    	MainDao exeQry = new MainDao();
    	String reqList="";
    	
	if(reqType.equals("OT")){
    		
			reqList="select count(*) from over_time_details lv,emp_official_info emp where  "
			+ " emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and lv.ApprvrStatus='"+reqStatus+"'  and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"') ";
			if(!Empno.equals(""))
				reqList=reqList+"and (emp.PERNR="+Empno+") " ;
			if(!Dept.equals(""))
				reqList=reqList+" and emp.DPTID='"+Dept+"'";
		
	 if(reqStatus.equalsIgnoreCase("All")){
    			reqList="select count(*) from over_time_details lv,emp_official_info emp where  "
    			+ " emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"')  ";
    			if(!Empno.equals(""))
    				reqList=reqList+"and (emp.PERNR="+Empno+" ) " ;
    			if(!Dept.equals(""))
    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
	 		}
	}
    	if(reqType.equals("Comp-Off")){
    		
			reqList="select count(*) from comp_ot lv,emp_official_info emp where  "
			+ " emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and lv.ApprvrStatus='"+reqStatus+"'  and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"') ";
			if(!Empno.equals(""))
				reqList=reqList+"and (emp.PERNR="+Empno+") " ;
			if(!Dept.equals(""))
				reqList=reqList+" and emp.DPTID='"+Dept+"'";
		
	 if(reqStatus.equalsIgnoreCase("All")){
    			reqList="select count(*) from comp_ot lv,emp_official_info emp where  "
    			+ " emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"')  ";
    			if(!Empno.equals(""))
    				reqList=reqList+"and (emp.PERNR="+Empno+" ) " ;
    			if(!Dept.equals(""))
    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
	 		}
	}
    	if(reqType.equals("Leave")){
    		
    			reqList="select count(*) from leave_details lv,emp_official_info emp where   "
    			+ " emp.PERNR=lv.user_id  and (lv.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and lv.Approvel_Status='"+reqStatus+"' and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"') ";
    			if(!Empno.equals(""))
    				reqList=reqList+"and (emp.PERNR="+Empno+") " ;
    			if(!Dept.equals(""))
    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
    		
		 if(reqStatus.equalsIgnoreCase("All")){
	    			reqList="select count(*) from leave_details lv,emp_official_info emp where   "
	    			+ "emp.PERNR=lv.user_id  and (lv.start_date between '"+hrFromDate+"' "
	    			+ "and '"+hrToDate+"') and (lv.Approvel_Status='Approved' or lv.Approvel_Status='Pending') and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"')  ";
	    			if(!Empno.equals(""))
	    				reqList=reqList+"and (emp.PERNR="+Empno+" ) " ;
	    			if(!Dept.equals(""))
	    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
		 		}
    	}
    	if(reqType.equals("On Duty")){
    		reqList="select count(*) from OnDuty_details OnDuty,emp_official_info emp where  "
    		+ " OnDuty.user_id=emp.pernr  and emp.PERNR=OnDuty.user_id and "
    		+ "(OnDuty.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and OnDuty.Approver_Status='"+reqStatus+"' and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"')   ";
			if(!Empno.equals(""))
				reqList=reqList+"and (emp.PERNR="+Empno+") " ;
			if(!Dept.equals(""))
				reqList=reqList+" and emp.DPTID='"+Dept+"'";
			
			 if(reqStatus.equalsIgnoreCase("All")){
				 reqList="select count(*) from OnDuty_details OnDuty,emp_official_info emp where"
				    		+ " emp.PERNR=OnDuty.user_id and "
				    		+ "(OnDuty.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and (OnDuty.Approver_Status='Approved' or OnDuty.Approver_Status='Pending')  and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"')  ";
				if(!Empno.equals(""))
					reqList=reqList+"and (emp.PERNR="+Empno+") " ;
				if(!Dept.equals(""))
					reqList=reqList+" and emp.DPTID='"+Dept+"'";
			 }
    	}
    	if(reqType.equals("Permission")){
    		reqList="select count(*) from Permission_details OnDuty,emp_official_info emp where  "
    	    		+ "emp.PERNR=OnDuty.user_id and "
    	    		+ "(OnDuty.date between '"+hrFromDate+"' and '"+hrToDate+"') and OnDuty.Approver_Status='"+reqStatus+"'  and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"')  ";
    				if(!Empno.equals(""))
    					reqList=reqList+"and (emp.PERNR="+Empno+") " ;
    				if(!Dept.equals(""))
    					reqList=reqList+" and emp.DPTID='"+Dept+"'";
    				
    				 if(reqStatus.equalsIgnoreCase("All")){
    					 reqList="select count(*) from Permission_details OnDuty,emp_official_info emp where "
    					    		+ " emp.PERNR=OnDuty.user_id and "
    					    		+ "(OnDuty.date between '"+hrFromDate+"' and '"+hrToDate+"') and (OnDuty.Approver_Status='1' or OnDuty.Approver_Status='0')  and  emp.LOCID=(select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"')  ";
    					if(!Empno.equals(""))
    						reqList=reqList+"and (emp.PERNR="+Empno+") " ;
    					if(!Dept.equals(""))
    						reqList=reqList+" and emp.DPTID='"+Dept+"'";
    				 }
    	}
    	
    	try
    	{
    		
    		ResultSet countrs=exeQry.selectQuery(reqList);
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
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HRReportForm hr=(HRReportForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		
		String hrfromdate=hr.getHrFromDate();
		HrfromDate=hrfromdate;
		String hrtodate=hr.getHrToDate();
		HrtoDate=hrtodate;
		
		String Dept=hr.getDepartment();
		Departemnt=Dept;
		String empno=hr.getEmployeeNo();
		EmpNo=empno;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String type=hr.getSelectedFilter();
		if(type.equalsIgnoreCase("Pending")){
			request.setAttribute("displayButton", "displayButton");
			}
		
		ArrayList deparmentList=new ArrayList();
		ArrayList departmentLabelList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
		try {
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTID"));
				departmentLabelList.add(rs12.getString("DPTSTXT"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		hr.setDepartmentList(deparmentList);
		hr.setDepartmentLabelList(departmentLabelList);
		try{
			int totalRecords=hr.getTotalRecords();//21
			int startRecord=hr.getStartRecord();//11
			int endRecord=hr.getEndRecord();	
			
			
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

			  String status=hr.getReqRequstType();
			  String hrFromDate=hr.getHrFromDate();
				 String a1[]=hrFromDate.split("/");
				 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
				String hrToDate=hr.getHrToDate();
				String b[]=hrToDate.split("/");
				 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
				 String cmpcode="";
					int id=user.getGroupId();
					String cmp[]=new String[0]; 
					String locations="";
					
					String aa="select * from user_group where id='"+id+"'";
					ResultSet cc=ad.selectQuery(aa);
					try {
						if(cc.next())
						{
							cmpcode=cc.getString("Attendance_Loc");
							
							if(cmpcode.contains(","))
							{
							 cmp=cmpcode.split(",");
							
							for(int b1=0;b1<cmp.length;b1++)
							{
								locations=locations+"'"+cmp[b1]+"',";
							}
							
							locations=locations.substring(0, locations.length()-1);
							}
							else
							{
								locations="'"+cmpcode+"'";
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				 
				 
				 if(status.equalsIgnoreCase("Comp-Off"))
					{

					 LinkedList comp = new LinkedList();
						String 	reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,RequestedDate,ApprvrStatus,FromDate,ToDate,reason,ApprvdDate from comp_ot lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
								+ "all_r.Req_Type='Comp-Off/OT' and lv.RequestedBy=all_r.Requester_Id and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and all_r.Req_Status='"+type+"' and    (Actual_Approver_status is null or Actual_Approver_status='') and  emp.LOCID in ("+locations+") ";
						if(!empno.equals(""))
							reqList=reqList+"and (lv.PERNR="+empno+") " ;
						if(!Dept.equals(""))
							reqList=reqList+" and emp.DPTID='"+Dept+"'";
						
						reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
						
						
						
						 if(type.equalsIgnoreCase("All")){
				    			reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate from comp_ot lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
				    			+ "all_r.Req_Type='Comp-Off/OT' and lv.RequestedBy=all_r.Requester_Id  and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
				    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID in ("+locations+")  ";
				    			if(!empno.equals(""))
				    				reqList=reqList+"and (lv.PERNR="+empno+" ) " ;
				    			if(!Dept.equals(""))
				    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
				    			
								reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
					 		}
						ResultSet rsit = ad.selectQuery(reqList);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_no"));
								leave.setEmployeeName(rsit.getString("emp_fullname"));
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
		request.setAttribute("comp", comp);
						
						if(comp.size()!=0)
						{
							hr.setTotalRecords(totalRecords);
							hr.setStartRecord(startRecord);
							hr.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							request.setAttribute("previousButton", "previousButton");
						}
						else
						{
							int start=startRecord;
							int end=startRecord;
							
							hr.setTotalRecords(totalRecords);
							hr.setStartRecord(start);
							hr.setEndRecord(end);
							
						}
					 if(comp.size()<10)
					 {
						 hr.setTotalRecords(totalRecords);
						 hr.setStartRecord(startRecord);
						 hr.setEndRecord(startRecord+comp.size()-1);
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
				 if(status.equalsIgnoreCase("OT"))
					{
					 LinkedList ot = new LinkedList();
						String 	reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,RequestedDate,ApprvrStatus,FromDate,ToDate,reason,ApprvdDate from over_time_details lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
								+ "all_r.Req_Type='OverTime' and lv.RequestedBy=all_r.Requester_Id and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and all_r.Req_Status='"+type+"' and    (Actual_Approver_status is null or Actual_Approver_status='') and  emp.LOCID in ("+locations+") ";
						if(!empno.equals(""))
							reqList=reqList+"and (lv.PERNR="+empno+") " ;
						if(!Dept.equals(""))
							reqList=reqList+" and emp.DPTID='"+Dept+"'";
						
						reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
						
						
						
						 if(type.equalsIgnoreCase("All")){
				    			reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate from over_time_details lv,All_Request all_r,emp_official_info emp,DEPARTMENT dept where all_r.Req_Id=lv.Req_no and "
				    			+ "all_r.Req_Type='OverTime' and lv.RequestedBy=all_r.Requester_Id  and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
				    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID in ("+locations+")  ";
				    			if(!empno.equals(""))
				    				reqList=reqList+"and (lv.PERNR="+empno+" ) " ;
				    			if(!Dept.equals(""))
				    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
				    			
								reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
					 		}
						 
						ResultSet rsit = ad.selectQuery(reqList);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_no"));
								leave.setEmployeeName(rsit.getString("emp_fullname"));
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

								ot.add(leave);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						request.setAttribute("ot", ot);
						
						if(ot.size()!=0)
						{
							hr.setTotalRecords(totalRecords);
							hr.setStartRecord(startRecord);
							hr.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							request.setAttribute("previousButton", "previousButton");
						}
						else
						{
							int start=startRecord;
							int end=startRecord;
							
							hr.setTotalRecords(totalRecords);
							hr.setStartRecord(start);
							hr.setEndRecord(end);
							
						}
					 if(ot.size()<10)
					 {
						 hr.setTotalRecords(totalRecords);
						 hr.setStartRecord(startRecord);
						 hr.setEndRecord(startRecord+ot.size()-1);
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
				 
			  if(status.equalsIgnoreCase("Leave"))
				{
				  LinkedList leaveList=new LinkedList();
					String getLeaveRecords="";
					
					
						getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,leave.leave_type,emp.PERNR," +
						"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
						"emp_official_info as emp,leave_details as leave where   Req_Status='"+type+"' and   (Actual_Approver_status is null or Actual_Approver_status='') and all_R.Req_Type='Leave' and "
						+ "emp.PERNR=leave.user_id  and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"')  and  all_R.Requester_Name=emp.PERNR  and leave.Req_Id=all_R.Req_Id  and  emp.LOCID in ("+locations+")  ";
						if(!empno.equals(""))
						getLeaveRecords=getLeaveRecords+"and emp.PERNR="+empno+"";
						if(!Dept.equals(""))
							getLeaveRecords=getLeaveRecords+"and emp.DPTID='"+Dept+"'";
						
						getLeaveRecords=getLeaveRecords+" group by all_R.Req_Id,leave.leave_type,emp.PERNR,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
					
			
					if(type.equalsIgnoreCase("All")){
						
						getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,leave.leave_type,emp.PERNR," +
								"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
								"emp_official_info as emp,leave_details as leave where   (all_R.Req_Status='Pending' or all_R.Req_Status='Approved' ) and all_R.Req_Type='Leave'  and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and emp.PERNR=leave.user_id  and  all_R.Requester_Name=emp.PERNR  and leave.Req_Id=all_R.Req_Id and  emp.LOCID in ("+locations+")    ";
						if(!empno.equals(""))
						getLeaveRecords=getLeaveRecords+" and emp.PERNR='"+empno+"'";
						if(!Dept.equals(""))
							getLeaveRecords=getLeaveRecords+" and emp.DPTID='"+Dept+"'";
						
						getLeaveRecords=getLeaveRecords+" group by all_R.Req_Id,leave.leave_type,emp.PERNR,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
					}
					
					
					
				 ResultSet rsLeave=ad.selectQuery(getLeaveRecords);

					while(rsLeave.next())
					{
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
						
						String HRId=rsLeave.getString("Hr_Id");
						//Approvers
						String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsLeave.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
						ResultSet rsData=ad.selectQuery(getapp);
						while(rsData.next())
						{
							leave.setApproverName(rsData.getString("EMP_FULLNAME"));
						}
						
						
						if(HRId!=null)
						{
							
							if( type.equalsIgnoreCase("Approved")){
							String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
							
							ResultSet rs=ad.selectQuery(hrdetails);
							
							while(rs.next())
							{
								leave.setApproverName(rs.getString("EMP_FULLNAME"));
								leave.setAppDesig(rs.getString("DSGSTXT"));
							}
						}
							
						}
						leaveList.add(leave);
						}
						
					request.setAttribute("leaveList", leaveList);
					
					if(leaveList.size()!=0)
					{
						hr.setTotalRecords(totalRecords);
						hr.setStartRecord(startRecord);
						hr.setEndRecord(endRecord);
						request.setAttribute("nextButton", "nextButton");
						request.setAttribute("previousButton", "previousButton");
					}
					else
					{
						int start=startRecord;
						int end=startRecord;
						
						hr.setTotalRecords(totalRecords);
						hr.setStartRecord(start);
						hr.setEndRecord(end);
						
					}
				 if(leaveList.size()<10)
				 {
					 hr.setTotalRecords(totalRecords);
					 hr.setStartRecord(startRecord);
					 hr.setEndRecord(startRecord+leaveList.size()-1);
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
			  
			  if(status.equalsIgnoreCase("On Duty"))
				{

				  LinkedList ondutyList=new LinkedList();
					String getOndutyRecords="";
					
					getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
							"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date " +
							"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty,DEPARTMENT dept,Location as loc where  all_R.Req_Status='"+type+"'  and onduty.start_date between '"+hrFromDate+"' and '"+hrToDate+"' and all_R.Req_Id=onduty.request_no " +
							"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty' and dept.DPTID=emp.DPTID and emp.PERNR=onduty.user_id  and  emp.LOCID=loc.LOCATION_CODE and   emp.LOCID in ("+locations+") ";
							if(!empno.equals(""))
								getOndutyRecords=getOndutyRecords+" and emp.PERNR="+empno+"";
								if(!Dept.equals(""))
									getOndutyRecords=getOndutyRecords+" and emp.DPTID='"+Dept+"'";
								
								getOndutyRecords=getOndutyRecords+" group by all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date )as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";  	
					
						if(type.equalsIgnoreCase("All")){
							
							getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
									"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date " +
									"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty,DEPARTMENT dept,Location as loc where  (all_R.Req_Status='Approved' or all_R.Req_Status='Pending') and onduty.start_date between '"+hrFromDate+"' and '"+hrToDate+"' and all_R.Req_Id=onduty.request_no " +
									"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty' and dept.DPTID=emp.DPTID and emp.PERNR=onduty.user_id and  emp.LOCID=loc.LOCATION_CODE and  emp.LOCID in ("+locations+")";
							if(!empno.equals(""))
								getOndutyRecords=getOndutyRecords+" and emp.PERNR="+empno+"";
								if(!Dept.equals(""))
									getOndutyRecords=getOndutyRecords+" and emp.DPTID='"+Dept+"'";
								getOndutyRecords=getOndutyRecords+" group by all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date )as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc"; 
					
					}
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
					onduty.setStatus(rsOnduty.getString("Req_Status"));
					
					String HRId=rsOnduty.getString("Hr_Id");
					
					//Approvers
					String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsOnduty.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
					ResultSet rsData=ad.selectQuery(getapp);
					while(rsData.next())
					{
						onduty.setApprover(rsData.getString("EMP_FULLNAME"));
					}
					
					if(HRId!=null)
					{
						
						if( type.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs=ad.selectQuery(hrdetails);
						
						while(rs.next())
						{
							onduty.setApprover(rs.getString("EMP_FULLNAME"));
							onduty.setApproverDesg(rs.getString("DSGSTXT"));
						}
					}
						
					}
					ondutyList.add(onduty);
					}
				
					request.setAttribute("ondutyList", ondutyList);
					
					if(ondutyList.size()!=0)
					{
						hr.setTotalRecords(totalRecords);
						hr.setStartRecord(startRecord);
						hr.setEndRecord(endRecord);
						request.setAttribute("nextButton", "nextButton");
						request.setAttribute("previousButton", "previousButton");
					}
					
					else
					{
						int start=startRecord;
						int end=startRecord;
				     	hr.setTotalRecords(totalRecords);
						hr.setStartRecord(start);
						hr.setEndRecord(end);
						
					}
				 if(ondutyList.size()<10)
				 {
					 hr.setTotalRecords(totalRecords);
					 hr.setStartRecord(startRecord);
					 hr.setEndRecord(startRecord+ondutyList.size()-1);
						request.setAttribute("nextButton", "");
						request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton"); 
					 
				 }
					
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					 if(endRecord==totalRecords)
					 {
						 request.setAttribute("nextButton", "");
							request.setAttribute("disableNextButton", "disableNextButton");
					 }
					 request.setAttribute("displayRecordNo", "displayRecordNo");
				
			 }
			  
			  if(status.equalsIgnoreCase("Permission"))
				{

				  LinkedList permList=new LinkedList();
					String getPermissionRecords="";
				
					String Hrlocation=user.getPlantId();
					
					getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS RowNum,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
							"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
						"emp_official_info as emp, Permission_details as perm where  all_R.Req_Status='"+type+"'  and perm.date between '"+hrFromDate+"' and '"+hrToDate+"' " +
						"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission'  and  emp.LOCID in ("+locations+") ";
						if(!empno.equals(""))
							getPermissionRecords=getPermissionRecords+" and emp.PERNR="+empno+"";
							if(!Dept.equals(""))
								getPermissionRecords=getPermissionRecords+" and emp.DPTID='"+Dept+"'";
							
							getPermissionRecords=getPermissionRecords+" group by all_R.Req_Id,perm.date, all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";  	
					
							if(type.equalsIgnoreCase("All")){
								getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS RowNum,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
										"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
									"emp_official_info as emp, Permission_details as perm where  (all_R.Req_Status='Approved' or all_R.Req_Status='Pending')  and perm.date between '"+hrFromDate+"' and '"+hrToDate+"' " +
									"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission'  and  emp.LOCID in ("+locations+") ";
							if(!empno.equals(""))
								getPermissionRecords=getPermissionRecords+" and emp.PERNR="+empno+"";
								if(!Dept.equals(""))
									getPermissionRecords=getPermissionRecords+" and emp.DPTID='"+Dept+"'";
								
								getPermissionRecords=getPermissionRecords+" group by all_R.Req_Id,perm.date, all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							}	
					
					
						
						ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
						try{
						while(rsPermission.next())
						{
							MyRequestForm permission=new MyRequestForm();
							permission.setType(rsPermission.getString("type"));
							permission.setRequestNumber(rsPermission.getString("Req_Id"));
				            permission.setStartTime(rsPermission.getString("startTime"));
					          permission.setEndTime(rsPermission.getString("endTime"));
				        	permission.setPermissiondate(rsPermission.getString("date"));
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
							permission.setStatus(rsPermission.getString("Req_Status"));
							permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
							String url="";
							
							permission.setUrl(url);
							//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
							//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
							String HRId=rsPermission.getString("Hr_Id");
							//Approvers
							String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsPermission.getString("Requester_Name")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
							ResultSet rsData=ad.selectQuery(getapp);
							while(rsData.next())
							{
								permission.setApprover(rsData.getString("EMP_FULLNAME"));
							}
							
							if(HRId!=null)
							{
								
								if( type.equalsIgnoreCase("Approved")){
								String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
								
								ResultSet rs=ad.selectQuery(hrdetails);
								
								while(rs.next())
								{
									permission.setApprover(rs.getString("EMP_FULLNAME"));
									permission.setApproverDesg(rs.getString("DSGSTXT"));
								}
							}
								
							}
							permList.add(permission);
								
						}
						
						
							request.setAttribute("Permissionlist", permList);
							if(permList.size()!=0)
							{
								hr.setTotalRecords(totalRecords);
								hr.setStartRecord(startRecord);
								hr.setEndRecord(endRecord);
								request.setAttribute("nextButton", "nextButton");
								request.setAttribute("previousButton", "previousButton");
							}
							else
							{
								int start=startRecord;
								int end=startRecord;
								
								hr.setTotalRecords(totalRecords);
								hr.setStartRecord(start);
								hr.setEndRecord(end);
								
							}
						 if(permList.size()<10)
						 {
							 hr.setTotalRecords(totalRecords);
							 hr.setStartRecord(startRecord);
							 hr.setEndRecord(startRecord+permList.size()-1);
								request.setAttribute("nextButton", "");
								request.setAttribute("disableNextButton", "disableNextButton");
								request.setAttribute("previousButton", "previousButton"); 
							 
						 }
							
							
							}catch (Exception e) {
								e.printStackTrace();
							}
					 
							
							 if(endRecord==totalRecords)
							 {
								 request.setAttribute("nextButton", "");
									request.setAttribute("disableNextButton", "disableNextButton");
							 }
							 request.setAttribute("displayRecordNo", "displayRecordNo");
			  
			  }
			 
			
			}}
		catch (Exception e) {
					e.printStackTrace();
	
	}
			
			return mapping.findForward("display");
	}			
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRReportForm approvalsForm=(HRReportForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		LinkedList leaveList=new LinkedList();
		HttpSession session=request.getSession();
		
		String Dept=approvalsForm.getDepartment();
		Departemnt=Dept;
		String empno=approvalsForm.getEmployeeNo();
		EmpNo=empno;
		
		String hrfromdate=approvalsForm.getHrFromDate();
		HrfromDate=hrfromdate;
		String hrtodate=approvalsForm.getHrToDate();
		HrtoDate=hrtodate;
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String type=approvalsForm.getSelectedFilter();
		if(type.equalsIgnoreCase("Pending")){
			request.setAttribute("displayButton", "displayButton");
			}
		
		ArrayList deparmentList=new ArrayList();
		ArrayList departmentLabelList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
		try {
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTID"));
				departmentLabelList.add(rs12.getString("DPTSTXT"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		approvalsForm.setDepartmentList(deparmentList);
		approvalsForm.setDepartmentLabelList(departmentLabelList);
		try{
			int totalRecords=approvalsForm.getTotalRecords();//21
			int startRecord=approvalsForm.getStartRecord();//11
			int endRecord=approvalsForm.getEndRecord();	
			
			
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 approvalsForm.setTotalRecords(totalRecords);
			 approvalsForm.setStartRecord(startRecord);
			 approvalsForm.setEndRecord(totalRecords);
			 String status=approvalsForm.getReqRequstType();
			 
			 String hrFromDate=approvalsForm.getHrFromDate();
			 String a1[]=hrFromDate.split("/");
			 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
			String hrToDate=approvalsForm.getHrToDate();
			String b[]=hrToDate.split("/");
			 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
			 String cmpcode="";
				int id=user.getGroupId();
				String cmp[]=new String[0]; 
				String locations="";
				
				String aa="select * from user_group where id='"+id+"'";
				ResultSet cc=ad.selectQuery(aa);
				try {
					if(cc.next())
					{
						cmpcode=cc.getString("Attendance_Loc");
						
						if(cmpcode.contains(","))
						{
						 cmp=cmpcode.split(",");
						
						for(int b1=0;b1<cmp.length;b1++)
						{
							locations=locations+"'"+cmp[b1]+"',";
						}
						
						locations=locations.substring(0, locations.length()-1);
						}
						else
						{
							locations="'"+cmpcode+"'";
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 
			 
			 if(status.equalsIgnoreCase("Comp-Off"))
				{

				 LinkedList comp = new LinkedList();
					String 	reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,RequestedDate,ApprvrStatus,FromDate,ToDate,reason,ApprvdDate from comp_ot lv,All_Request all_r,emp_official_info emp,DEPARTMENT dept where all_r.Req_Id=lv.Req_no and "
							+ "all_r.Req_Type='Comp-Off/OT' and lv.RequestedBy=all_r.Requester_Id and emp.DPTID=dept.DPTID and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and all_r.Req_Status='"+type+"' and    (Actual_Approver_status is null or Actual_Approver_status='') and  emp.LOCID in ("+locations+") ";
					if(!empno.equals(""))
						reqList=reqList+"and (emp.PERNR="+empno+") " ;
					if(!Dept.equals(""))
						reqList=reqList+" and dept.DPTSTXT='"+Dept+"'";
					
					reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
					
					
					
					 if(type.equalsIgnoreCase("All")){
			    			reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate from comp_ot lv,All_Request all_r,emp_official_info emp,DEPARTMENT dept where all_r.Req_Id=lv.Req_no and "
			    			+ "all_r.Req_Type='Comp-Off/OT' and lv.RequestedBy=all_r.Requester_Id and emp.DPTID=dept.DPTID and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
			    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID in ("+locations+")  ";
			    			if(!empno.equals(""))
			    				reqList=reqList+"and (emp.PERNR="+empno+" ) " ;
			    			if(!Dept.equals(""))
			    				reqList=reqList+" and dept.DPTSTXT='"+Dept+"'";
			    			
							reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
				 		}
					 
					ResultSet rsit = ad.selectQuery(reqList);
					try {
						while (rsit.next()) {
							LeaveForm leave = new LeaveForm();
							leave.setRequestNumber(rsit.getInt("Req_no"));
							leave.setEmployeeName(rsit.getString("emp_fullname"));
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
					
					request.setAttribute("comp", comp);
					 request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if(comp.size()<10)
						{
							
							request.setAttribute("previousButton", "");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");
					
						
				
				 
				}
			 
			 
			 
			 
			 if(status.equalsIgnoreCase("OT"))
				{
				 LinkedList ot = new LinkedList();
					String 	reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,RequestedDate,ApprvrStatus,FromDate,ToDate,reason,ApprvdDate from over_time_details lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
							+ "all_r.Req_Type='OverTime' and lv.RequestedBy=all_r.Requester_Id and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and all_r.Req_Status='"+type+"' and    (Actual_Approver_status is null or Actual_Approver_status='') and  emp.LOCID in ("+locations+") ";
					if(!empno.equals(""))
						reqList=reqList+"and (lv.PERNR="+empno+") " ;
					if(!Dept.equals(""))
						reqList=reqList+" and emp.DPTID='"+Dept+"'";
					
					reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
					
					
					
					 if(type.equalsIgnoreCase("All")){
			    			reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate from over_time_details lv,All_Request all_r,emp_official_info emp,DEPARTMENT dept where all_r.Req_Id=lv.Req_no and "
			    			+ "all_r.Req_Type='OverTime' and lv.RequestedBy=all_r.Requester_Id  and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
			    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID in ("+locations+")  ";
			    			if(!empno.equals(""))
			    				reqList=reqList+"and (lv.PERNR="+empno+" ) " ;
			    			if(!Dept.equals(""))
			    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
			    			
							reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
				 		}
					 
					ResultSet rsit = ad.selectQuery(reqList);
					try {
						while (rsit.next()) {
							LeaveForm leave = new LeaveForm();
							leave.setRequestNumber(rsit.getInt("Req_no"));
							leave.setEmployeeName(rsit.getString("emp_fullname"));
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

							ot.add(leave);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("ot", ot);
					
			
			
					 request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if(ot.size()<10)
						{
							
							request.setAttribute("previousButton", "");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");
	}
					
						
				
			 if(status.equalsIgnoreCase("Leave"))
				{
				  leaveList=new LinkedList();
					String getLeaveRecords="";
					
					
						getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,leave.leave_type,emp.PERNR," +
						"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
						"emp_official_info as emp,leave_details as leave where   Req_Status='"+type+"' and   (Actual_Approver_status is null or Actual_Approver_status='') and all_R.Req_Type='Leave' and "
						+ "emp.PERNR=leave.user_id  and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"')  and  all_R.Requester_Name=emp.PERNR  and leave.Req_Id=all_R.Req_Id  and  emp.LOCID in ("+locations+")  ";
						if(!empno.equals(""))
						getLeaveRecords=getLeaveRecords+"and emp.PERNR="+empno+"";
						if(!Dept.equals(""))
							getLeaveRecords=getLeaveRecords+"and emp.DPTID='"+Dept+"'";
						
						getLeaveRecords=getLeaveRecords+" group by all_R.Req_Id,leave.leave_type,emp.PERNR,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
					
			
					if(type.equalsIgnoreCase("All")){
						
						getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,leave.leave_type,emp.PERNR," +
								"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
								"emp_official_info as emp,leave_details as leave where   (all_R.Req_Status='Pending' or all_R.Req_Status='Approved' ) and all_R.Req_Type='Leave'  and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and emp.PERNR=leave.user_id  and  all_R.Requester_Name=emp.PERNR  and leave.Req_Id=all_R.Req_Id and  emp.LOCID in ("+locations+")    ";
						if(!empno.equals(""))
						getLeaveRecords=getLeaveRecords+" and emp.PERNR='"+empno+"'";
						if(!Dept.equals(""))
							getLeaveRecords=getLeaveRecords+" and emp.DPTID='"+Dept+"'";
						
						getLeaveRecords=getLeaveRecords+" group by all_R.Req_Id,leave.leave_type,emp.PERNR,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
					}
					ResultSet rsLeave=ad.selectQuery(getLeaveRecords);	
		

				 while(rsLeave.next())
					{
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
						
						String HRId=rsLeave.getString("Hr_Id");
						//Approvers
						String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsLeave.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
						ResultSet rsData=ad.selectQuery(getapp);
						while(rsData.next())
						{
							leave.setApproverName(rsData.getString("EMP_FULLNAME"));
						}
						
						if(HRId!=null)
						{
							
							if( type.equalsIgnoreCase("Approved")){
							String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
							
							ResultSet rs=ad.selectQuery(hrdetails);
							
							while(rs.next())
							{
								leave.setApproverName(rs.getString("EMP_FULLNAME"));
								leave.setAppDesig(rs.getString("DSGSTXT"));
							}
						}
							
						}
						leaveList.add(leave);
						}
						
						request.setAttribute("leaveList", leaveList);
						 request.setAttribute("disableNextButton", "disableNextButton");
							request.setAttribute("previousButton", "previousButton");
							if(leaveList.size()<10)
							{
								
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
							}
							request.setAttribute("displayRecordNo", "displayRecordNo");
				
				}
			 
			 if(status.equalsIgnoreCase("On Duty"))
				{

				 LinkedList ondutyList=new LinkedList();
					String getOndutyRecords="";
					
					getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
							"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date " +
							"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty,DEPARTMENT dept,Location as loc where  all_R.Req_Status='"+type+"'  and onduty.start_date between '"+hrFromDate+"' and '"+hrToDate+"' and all_R.Req_Id=onduty.request_no " +
							"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty' and dept.DPTID=emp.DPTID and emp.PERNR=onduty.user_id  and  emp.LOCID=loc.LOCATION_CODE and   emp.LOCID in ("+locations+") ";
							if(!empno.equals(""))
								getOndutyRecords=getOndutyRecords+" and emp.PERNR="+empno+"";
								if(!Dept.equals(""))
									getOndutyRecords=getOndutyRecords+" and emp.DPTID='"+Dept+"'";
								
								getOndutyRecords=getOndutyRecords+" group by all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date )as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";  	
					
						if(type.equalsIgnoreCase("All")){
							
							getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
									"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date " +
									"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty,DEPARTMENT dept,Location as loc where  (all_R.Req_Status='Approved' or all_R.Req_Status='Pending') and onduty.start_date between '"+hrFromDate+"' and '"+hrToDate+"' and all_R.Req_Id=onduty.request_no " +
									"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty' and dept.DPTID=emp.DPTID and emp.PERNR=onduty.user_id and  emp.LOCID=loc.LOCATION_CODE and  emp.LOCID in ("+locations+")";
							if(!empno.equals(""))
								getOndutyRecords=getOndutyRecords+" and emp.PERNR="+empno+"";
								if(!Dept.equals(""))
									getOndutyRecords=getOndutyRecords+" and emp.DPTID='"+Dept+"'";
								getOndutyRecords=getOndutyRecords+" group by all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date )as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc"; 
					
					}
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
					onduty.setStatus(rsOnduty.getString("Req_Status"));
					
					String HRId=rsOnduty.getString("Hr_Id");
					
					//Approvers
					String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsOnduty.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
					ResultSet rsData=ad.selectQuery(getapp);
					while(rsData.next())
					{
						onduty.setApprover(rsData.getString("EMP_FULLNAME"));
					}
					
					if(HRId!=null)
					{
						
						if( type.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs=ad.selectQuery(hrdetails);
						
						while(rs.next())
						{
							onduty.setApprover(rs.getString("EMP_FULLNAME"));
							onduty.setApproverDesg(rs.getString("DSGSTXT"));
						}
					}
						
					}
					ondutyList.add(onduty);
					}
					request.setAttribute("ondutyList", ondutyList);
					 request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if(ondutyList.size()<10)
						{
							
							request.setAttribute("previousButton", "");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");
					
					}catch (Exception e) {
						e.printStackTrace();
					}
					
				
			 }
			  if(status.equalsIgnoreCase("Permission"))
				{

				  LinkedList permList=new LinkedList();
					String getPermissionRecords="";
				
					String Hrlocation=user.getPlantId();
					
					getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS RowNum,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
							"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
						"emp_official_info as emp, Permission_details as perm where  all_R.Req_Status='"+type+"'  and perm.date between '"+hrFromDate+"' and '"+hrToDate+"' " +
						"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission'  and  emp.LOCID in ("+locations+") ";
						if(!empno.equals(""))
							getPermissionRecords=getPermissionRecords+" and emp.PERNR="+empno+"";
							if(!Dept.equals(""))
								getPermissionRecords=getPermissionRecords+" and emp.DPTID='"+Dept+"'";
							
							getPermissionRecords=getPermissionRecords+" group by all_R.Req_Id,perm.date, all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";  	
					
							if(type.equalsIgnoreCase("All")){
								getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS RowNum,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
										"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
									"emp_official_info as emp, Permission_details as perm where  (all_R.Req_Status='Approved' or all_R.Req_Status='Pending')  and perm.date between '"+hrFromDate+"' and '"+hrToDate+"' " +
									"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission'  and  emp.LOCID in ("+locations+") ";
							if(!empno.equals(""))
								getPermissionRecords=getPermissionRecords+" and emp.PERNR="+empno+"";
								if(!Dept.equals(""))
									getPermissionRecords=getPermissionRecords+" and emp.DPTID='"+Dept+"'";
								
								getPermissionRecords=getPermissionRecords+" group by all_R.Req_Id,perm.date, all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							}	
					
					
						
						ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
						try{
						while(rsPermission.next())
						{
							MyRequestForm permission=new MyRequestForm();
							permission.setType(rsPermission.getString("type"));
							permission.setRequestNumber(rsPermission.getString("Req_Id"));
				            permission.setStartTime(rsPermission.getString("startTime"));
					          permission.setEndTime(rsPermission.getString("endTime"));
				        	permission.setPermissiondate(rsPermission.getString("date"));
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
							permission.setStatus(rsPermission.getString("Req_Status"));
							permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
							String url="";
							
							permission.setUrl(url);
							//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
							//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
							String HRId=rsPermission.getString("Hr_Id");
							//Approvers
							String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsPermission.getString("Requester_Name")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
							ResultSet rsData=ad.selectQuery(getapp);
							while(rsData.next())
							{
								permission.setApprover(rsData.getString("EMP_FULLNAME"));
							}
							
							if(HRId!=null)
							{
								
								if( type.equalsIgnoreCase("Approved")){
								String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
								
								ResultSet rs=ad.selectQuery(hrdetails);
								
								while(rs.next())
								{
									permission.setApprover(rs.getString("EMP_FULLNAME"));
									permission.setApproverDesg(rs.getString("DSGSTXT"));
								}
							}
								
							}
							permList.add(permission);
								
						}
						
						request.setAttribute("Permissionlist", permList);
						 request.setAttribute("disableNextButton", "disableNextButton");
							request.setAttribute("previousButton", "previousButton");
							if(permList.size()<10)
							{
								
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
							}
							request.setAttribute("displayRecordNo", "displayRecordNo");
							}catch (Exception e) {
								e.printStackTrace();
							}
					 
			  
			  }
			 
			 
		
		
		
	
	}catch (Exception e) {
		e.printStackTrace();
	}
				
	return mapping.findForward("display");
	}
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		
		
		
		HRReportForm approvalsForm=(HRReportForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		LinkedList leaveList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String type=approvalsForm.getSelectedFilter();
		if(type.equalsIgnoreCase("Pending")){
			request.setAttribute("displayButton", "displayButton");
			}
		
		
		String Dept=approvalsForm.getDepartment();
		Departemnt=Dept;
		String empno=approvalsForm.getEmployeeNo();
		EmpNo=empno;
		
		String hrfromdate=approvalsForm.getHrFromDate();
		HrfromDate=hrfromdate;
		String hrtodate=approvalsForm.getHrToDate();
		HrtoDate=hrtodate;
		
		ArrayList deparmentList=new ArrayList();
		ArrayList departmentLabelList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
		try {
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTID"));
				departmentLabelList.add(rs12.getString("DPTSTXT"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		approvalsForm.setDepartmentList(deparmentList);
		approvalsForm.setDepartmentLabelList(departmentLabelList);
		try{
			
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
			  String hrFromDate=approvalsForm.getHrFromDate();
				 String a1[]=hrFromDate.split("/");
				 hrFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
				String hrToDate=approvalsForm.getHrToDate();
				String b[]=hrToDate.split("/");
				 hrToDate=b[2]+"-"+b[1]+"-"+b[0];
				 
				 
				 String cmpcode="";
					int id=user.getGroupId();
					String cmp[]=new String[0]; 
					String locations="";
					
					String aa="select * from user_group where id='"+id+"'";
					ResultSet cc=ad.selectQuery(aa);
					try {
						if(cc.next())
						{
							cmpcode=cc.getString("Attendance_Loc");
							
							if(cmpcode.contains(","))
							{
							 cmp=cmpcode.split(",");
							
							for(int b1=0;b1<cmp.length;b1++)
							{
								locations=locations+"'"+cmp[b1]+"',";
							}
							
							locations=locations.substring(0, locations.length()-1);
							}
							else
							{
								locations="'"+cmpcode+"'";
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				 
				 
				 if(status.equalsIgnoreCase("Comp-Off"))
					{

					 LinkedList comp = new LinkedList();
						String 	reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,RequestedDate,ApprvrStatus,FromDate,ToDate,reason,ApprvdDate from comp_ot lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
								+ "all_r.Req_Type='Comp-Off/OT' and lv.RequestedBy=all_r.Requester_Id and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and all_r.Req_Status='"+type+"' and    (Actual_Approver_status is null or Actual_Approver_status='') and  emp.LOCID in ("+locations+") ";
						if(!empno.equals(""))
							reqList=reqList+"and (lv.PERNR="+empno+") " ;
						if(!Dept.equals(""))
							reqList=reqList+" and emp.DPTID='"+Dept+"'";
						
						reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
						
						
						
						 if(type.equalsIgnoreCase("All")){
				    			reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate from comp_ot lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
				    			+ "all_r.Req_Type='Comp-Off/OT' and lv.RequestedBy=all_r.Requester_Id  and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
				    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID in ("+locations+")  ";
				    			if(!empno.equals(""))
				    				reqList=reqList+"and (lv.PERNR="+empno+" ) " ;
				    			if(!Dept.equals(""))
				    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
				    			
								reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
					 		}
						 
						ResultSet rsit = ad.selectQuery(reqList);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_no"));
								leave.setEmployeeName(rsit.getString("emp_fullname"));
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
						
						request.setAttribute("comp", comp);
						approvalsForm.setTotalRecords(totalRecords);
						  approvalsForm.setStartRecord(startRecord);
						  approvalsForm.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							if(startRecord!=1)
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("displayRecordNo", "displayRecordNo");
							if(comp.size()<10)
							{
								approvalsForm.setStartRecord(1);
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
		}
				
							
					
					 
					}
				 
				 
				 if(status.equalsIgnoreCase("OT"))
					{
					 LinkedList ot = new LinkedList();
						String 	reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,RequestedDate,ApprvrStatus,FromDate,ToDate,reason,ApprvdDate from over_time_details lv,All_Request all_r,emp_official_info emp where all_r.Req_Id=lv.Req_no and "
								+ "all_r.Req_Type='OverTime' and lv.RequestedBy=all_r.Requester_Id and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' and '"+hrToDate+"') and all_r.Req_Status='"+type+"' and    (Actual_Approver_status is null or Actual_Approver_status='') and  emp.LOCID in ("+locations+") ";
						if(!empno.equals(""))
							reqList=reqList+"and (lv.PERNR="+empno+") " ;
						if(!Dept.equals(""))
							reqList=reqList+" and emp.DPTID='"+Dept+"'";
						
						reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
						
						
						
						 if(type.equalsIgnoreCase("All")){
				    			reqList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY Req_no desc) AS  RowNum,Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate from over_time_details lv,All_Request all_r,emp_official_info emp,DEPARTMENT dept where all_r.Req_Id=lv.Req_no and "
				    			+ "all_r.Req_Type='OverTime' and lv.RequestedBy=all_r.Requester_Id  and emp.PERNR=lv.RequestedBy  and (lv.fromdate between '"+hrFromDate+"' "
				    			+ "and '"+hrToDate+"') and (lv.ApprvrStatus='Approved' or lv.ApprvrStatus='Pending') and  emp.LOCID in ("+locations+")  ";
				    			if(!empno.equals(""))
				    				reqList=reqList+"and (lv.PERNR="+empno+" ) " ;
				    			if(!Dept.equals(""))
				    				reqList=reqList+" and emp.DPTID='"+Dept+"'";
				    			
								reqList=reqList+" group by Req_no,emp_fullname,ApprvrStatus,RequestedDate,FromDate,ToDate,reason,ApprvdDate)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_no desc";
					 		}
						 
						ResultSet rsit = ad.selectQuery(reqList);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_no"));
								leave.setEmployeeName(rsit.getString("emp_fullname"));
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

								ot.add(leave);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						request.setAttribute("ot", ot);
						
				
						approvalsForm.setTotalRecords(totalRecords);
						  approvalsForm.setStartRecord(startRecord);
						  approvalsForm.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							if(startRecord!=1)
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("displayRecordNo", "displayRecordNo");
							if(ot.size()<10)
							{
								approvalsForm.setStartRecord(1);
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
		}
						
							
					}
				 
			  if(status.equalsIgnoreCase("Leave"))
				{
				  
					 leaveList=new LinkedList();
					String getLeaveRecords="";
					
					
						getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,leave.leave_type,emp.PERNR," +
						"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
						"emp_official_info as emp,leave_details as leave where   Req_Status='"+type+"' and   (Actual_Approver_status is null or Actual_Approver_status='') and all_R.Req_Type='Leave' and "
						+ "emp.PERNR=leave.user_id  and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"')  and  all_R.Requester_Name=emp.PERNR  and leave.Req_Id=all_R.Req_Id  and  emp.LOCID in ("+locations+")  ";
						if(!empno.equals(""))
						getLeaveRecords=getLeaveRecords+"and emp.PERNR="+empno+"";
						if(!Dept.equals(""))
							getLeaveRecords=getLeaveRecords+"and emp.DPTID='"+Dept+"'";
						
						getLeaveRecords=getLeaveRecords+" group by all_R.Req_Id,leave.leave_type,emp.PERNR,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
					
			
					if(type.equalsIgnoreCase("All")){
						
						getLeaveRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,leave.leave_type,emp.PERNR," +
								"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days from  All_Request as all_R," +
								"emp_official_info as emp,leave_details as leave where   (all_R.Req_Status='Pending' or all_R.Req_Status='Approved' ) and all_R.Req_Type='Leave'  and (leave.start_date between '"+hrFromDate+"' and '"+hrToDate+"') and emp.PERNR=leave.user_id  and  all_R.Requester_Name=emp.PERNR  and leave.Req_Id=all_R.Req_Id and  emp.LOCID in ("+locations+")    ";
						if(!empno.equals(""))
						getLeaveRecords=getLeaveRecords+" and emp.PERNR='"+empno+"'";
						if(!Dept.equals(""))
							getLeaveRecords=getLeaveRecords+" and emp.DPTID='"+Dept+"'";
						
						getLeaveRecords=getLeaveRecords+" group by all_R.Req_Id,leave.leave_type,emp.PERNR,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,leave.start_date,leave.start_duration,leave.end_date,leave.end_duration,leave.no_of_days)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
					}
			
			
			
				 ResultSet rsLeave=ad.selectQuery(getLeaveRecords);

					while(rsLeave.next())
					{
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
						
						String HRId=rsLeave.getString("Hr_Id");
						//Approvers
						String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsLeave.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
						ResultSet rsData=ad.selectQuery(getapp);
						while(rsData.next())
						{
							leave.setApproverName(rsData.getString("EMP_FULLNAME"));
						}
						
						if(HRId!=null)
						{
							
							if( type.equalsIgnoreCase("Approved")){
							String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
							
							ResultSet rs=ad.selectQuery(hrdetails);
							
							while(rs.next())
							{
								leave.setApproverName(rs.getString("EMP_FULLNAME"));
								leave.setAppDesig(rs.getString("DSGSTXT"));
							}
						}
							
						}
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
			  
				}
			  
				 if(status.equalsIgnoreCase("On Duty"))
					{
					 LinkedList ondutyList=new LinkedList();
						String getOndutyRecords="";
						
						getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
								"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date " +
								"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty,DEPARTMENT dept,Location as loc where  all_R.Req_Status='"+type+"'  and onduty.start_date between '"+hrFromDate+"' and '"+hrToDate+"' and all_R.Req_Id=onduty.request_no " +
								"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty' and dept.DPTID=emp.DPTID and emp.PERNR=onduty.user_id  and  emp.LOCID=loc.LOCATION_CODE and   emp.LOCID in ("+locations+") ";
								if(!empno.equals(""))
									getOndutyRecords=getOndutyRecords+" and emp.PERNR="+empno+"";
									if(!Dept.equals(""))
										getOndutyRecords=getOndutyRecords+" and emp.DPTID='"+Dept+"'";
									
									getOndutyRecords=getOndutyRecords+" group by all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date )as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";  	
						
							if(type.equalsIgnoreCase("All")){
								
								getOndutyRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime," +
										"emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date " +
										"from  All_Request as all_R,emp_official_info as emp,OnDuty_details as onduty,DEPARTMENT dept,Location as loc where  (all_R.Req_Status='Approved' or all_R.Req_Status='Pending') and onduty.start_date between '"+hrFromDate+"' and '"+hrToDate+"' and all_R.Req_Id=onduty.request_no " +
										"and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='On Duty' and dept.DPTID=emp.DPTID and emp.PERNR=onduty.user_id and  emp.LOCID=loc.LOCATION_CODE and  emp.LOCID in ("+locations+")";
								if(!empno.equals(""))
									getOndutyRecords=getOndutyRecords+" and emp.PERNR="+empno+"";
									if(!Dept.equals(""))
										getOndutyRecords=getOndutyRecords+" and emp.DPTID='"+Dept+"'";
									getOndutyRecords=getOndutyRecords+" group by all_R.Req_Id,all_R.Requester_Name,emp.PERNR,onduty.onDuty_Type,onduty.location,onduty.startTime,onduty.endTime,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.Req_Date,onduty.start_date,onduty.end_date )as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc"; 
						
						}
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
						onduty.setStatus(rsOnduty.getString("Req_Status"));
						
						String HRId=rsOnduty.getString("Hr_Id");
						
						//Approvers
						String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsOnduty.getString("PERNR")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
						ResultSet rsData=ad.selectQuery(getapp);
						while(rsData.next())
						{
							onduty.setApprover(rsData.getString("EMP_FULLNAME"));
						}
						
						if(HRId!=null)
						{
							
							if( type.equalsIgnoreCase("Approved")){
							String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
							
							ResultSet rs=ad.selectQuery(hrdetails);
							
							while(rs.next())
							{
								onduty.setApprover(rs.getString("EMP_FULLNAME"));
								onduty.setApproverDesg(rs.getString("DSGSTXT"));
							}
						}
							
						}
						ondutyList.add(onduty);
						}
						request.setAttribute("ondutyList", ondutyList);
						approvalsForm.setTotalRecords(totalRecords);
						  approvalsForm.setStartRecord(startRecord);
						  approvalsForm.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							if(startRecord!=1)
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("displayRecordNo", "displayRecordNo");
							if(ondutyList.size()<10)
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
						String getPermissionRecords="";
					
						String Hrlocation=user.getPlantId();
						
						getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS RowNum,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
								"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
							"emp_official_info as emp, Permission_details as perm where  all_R.Req_Status='"+type+"'  and perm.date between '"+hrFromDate+"' and '"+hrToDate+"' " +
							"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission'  and  emp.LOCID in ("+locations+") ";
							if(!empno.equals(""))
								getPermissionRecords=getPermissionRecords+" and emp.PERNR="+empno+"";
								if(!Dept.equals(""))
									getPermissionRecords=getPermissionRecords+" and emp.DPTID='"+Dept+"'";
								
								getPermissionRecords=getPermissionRecords+" group by all_R.Req_Id,perm.date, all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";  	
						
								if(type.equalsIgnoreCase("All")){
									getPermissionRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS RowNum,all_R.Req_Id,convert(nvarchar(10),perm.date,103)as date, " +
											"all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type from  All_Request as all_R," +
										"emp_official_info as emp, Permission_details as perm where  (all_R.Req_Status='Approved' or all_R.Req_Status='Pending')  and perm.date between '"+hrFromDate+"' and '"+hrToDate+"' " +
										"and all_R.Req_Id=perm.request_no and all_R.Requester_Name=emp.PERNR and all_R.Req_Type='Permission'  and  emp.LOCID in ("+locations+") ";
								if(!empno.equals(""))
									getPermissionRecords=getPermissionRecords+" and emp.PERNR="+empno+"";
									if(!Dept.equals(""))
										getPermissionRecords=getPermissionRecords+" and emp.DPTID='"+Dept+"'";
									
									getPermissionRecords=getPermissionRecords+" group by all_R.Req_Id,perm.date, all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Hr_Id,all_R.Req_Status,all_R.Req_Date, perm.startTime,perm.endTime,perm.type)as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								}	
								
						
						
							ResultSet rsPermission=ad.selectQuery(getPermissionRecords);
							try{
							while(rsPermission.next())
							{
								MyRequestForm permission=new MyRequestForm();
								permission.setType(rsPermission.getString("type"));
								permission.setRequestNumber(rsPermission.getString("Req_Id"));
					            permission.setStartTime(rsPermission.getString("startTime"));
						          permission.setEndTime(rsPermission.getString("endTime"));
					        	permission.setPermissiondate(rsPermission.getString("date"));
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
								permission.setStatus(rsPermission.getString("Req_Status"));
								permission.setEmployeeNumber(rsPermission.getString("Requester_Name"));
								String url="";
								
								permission.setUrl(url);
								//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
								//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
								String HRId=rsPermission.getString("Hr_Id");
								//Approvers
								String getapp="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where employeeNumber='"+rsPermission.getString("Requester_Name")+"' and essType='Leave'  and ess.ApproverId=emp.PERNR";
								ResultSet rsData=ad.selectQuery(getapp);
								while(rsData.next())
								{
									permission.setApprover(rsData.getString("EMP_FULLNAME"));
								}
								
								if(HRId!=null)
								{
									
									if( type.equalsIgnoreCase("Approved")){
									String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRId+"' and emp.DSGID=des.DSGID";
									
									ResultSet rs=ad.selectQuery(hrdetails);
									
									while(rs.next())
									{
										permission.setApprover(rs.getString("EMP_FULLNAME"));
										permission.setApproverDesg(rs.getString("DSGSTXT"));
									}
								}
									
								}
								permList.add(permission);
									
							}
							
							request.setAttribute("Permissionlist", permList);
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
		}catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("display");
		}
		
		
		
		
	
	
}