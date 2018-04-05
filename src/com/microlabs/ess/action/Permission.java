package com.microlabs.ess.action;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.OnDutyForm;
import com.microlabs.ess.form.PermissionForm;
import com.microlabs.hr.dao.SAPAttendenceDAO;
import com.microlabs.hr.form.SAPAttendenceForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class Permission extends  DispatchAction{
	EssDao ad=EssDao.dBConnection();
	
	public String getCurrentDateWithFormat(){
		Calendar currentDate = Calendar.getInstance();
		 int day = currentDate.get(Calendar.DATE);
	     int month = currentDate.get(Calendar.MONTH) + 1;
	     int year = currentDate.get(Calendar.YEAR);
	     String dateNow=day+"/"+month+"/"+year;
	     return dateNow;
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
	
	public String Empshifttimings(String a)
	{
		
		String b = "";
		String timings = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
			EssDao ad = new EssDao();
		String emp = "select * from  EMP_SHIFT_MASTER where Shift_code = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("stxt")+", Start-"+rs.getString("Shift_start_time").substring(0, 5)+",End-"+rs.getString("Shift_End_Time").substring(0, 5); 
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return b;
		
	}
	
	public String shiftexactimings(String a)
	{
		
		String b = "";

		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
			EssDao ad = new EssDao();
		String emp = "select * from  EMP_SHIFT_MASTER where Shift_code = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
			
				b=rs.getString("Shift_start_time").substring(0, 5)+","+rs.getString("Shift_End_Time").substring(0, 5);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return b;
		
	}
	
	public LinkedList viewOnduty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String HRid="";
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList approverList=new LinkedList();
		String dateNow=getCurrentDateWithFormat();

		int reqId=Integer.parseInt(request.getParameter("requstNo"));
		String approverName="";
		String approverID="";
		String reportingMgrID="";
		String reportingMgrName="";
		String appDesig="";
		String reportMgrDesg="";
		String getApprovers="select APPMGR,RPTMGR  from emp_official_info  emp where " +
		"PERNR='"+user.getEmployeeNo()+"'  ";	
ResultSet rsApprovers=ad.selectQuery(getApprovers);
try {
	while(rsApprovers.next())
	{
		approverID=rsApprovers.getString("APPMGR");
		reportingMgrID=rsApprovers.getString("RPTMGR");
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
boolean existStatus=false;
String reqType="";

ArrayList paralist=new ArrayList();
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
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
String duplicate="";
if(existStatus==true){
	if(reqType.equalsIgnoreCase("sequential"))
	{
	String getApproverDesig=" select e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' order by Priority";
	 
	ResultSet rsA=ad.selectQuery(getApproverDesig);
		try {
			while(rsA.next())
			{
				LeaveForm lvform=new LeaveForm();
				lvform.setApptype("Approve Manager");
				 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
				 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
				String getdetails=" select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date," +
						"all_R.rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
						" where all_R.Req_Id='"+reqId+"' and all_R.Req_Type='Permission' and ((emp.PERNR=all_R.actual_approver) or " +
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
						
						 lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
						
					}
					if(reqstatus.equalsIgnoreCase("Approved"))
					{
					lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));
					 lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
				   lvform.setApprovedDate(rsApproverDsg.getString("approved_date"));
					}
					
					if(reqstatus.equalsIgnoreCase("Rejected"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
					}
					if(reqstatus.equalsIgnoreCase("Cancelled"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
					}
					lvform.setComments(rsApproverDsg.getString("Comments"));
					
					if(HRid!=null)
					{
						if( reqstatus.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs=ad.selectQuery(hrdetails);
						
						while(rs.next())
						{
							lvform.setApprover(rs.getString("EMP_FULLNAME"));
							 lvform.setAppDesig(rs.getString("DSGSTXT"));
						}
					}
					}
					
					
				
				}
				
				if(!duplicate.equalsIgnoreCase(rsApproverDsg.getString("approved_date")))
					paralist.add(lvform);
					duplicate=rsApproverDsg.getString("approved_date");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		request.setAttribute("ApproverList", paralist);
		
	}
	if(reqType.equalsIgnoreCase("parallel"))
	{
		
		String getApproverDesig="  select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date,all_R.rejected_date from " +
				"All_Request as all_R,emp_official_info as emp, DESIGNATION as desg    where all_R.Req_Id='"+reqId+"' and all_R.Req_Type='Permission' and " +
				"((emp.PERNR=all_R.actual_approver) or (emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) ";
		    ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
		    
			try {
				while(rsApproverDsg.next())
				{
					HRid=rsApproverDsg.getString("Hr_Id");
					LeaveForm lvform=new LeaveForm();
					lvform.setApptype("Approve Manager");
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
				   lvform.setApprovedDate(rsApproverDsg.getString("approved_date"));
					}
					
					if(reqstatus.equalsIgnoreCase("Rejected")||reqstatus.equalsIgnoreCase("Cancelled"))
					{
						  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
						  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
					     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
					}
					lvform.setComments(rsApproverDsg.getString("Comments"));
					
					if(HRid!=null)
					{
						if( reqstatus.equalsIgnoreCase("Approved")){
						String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
						
						ResultSet rs=ad.selectQuery(hrdetails);
						
						while(rs.next())
						{
							lvform.setApprover(rs.getString("EMP_FULLNAME"));
							 lvform.setAppDesig(rs.getString("DSGSTXT"));
						}
					}
					}
					
					if(!duplicate.equalsIgnoreCase(rsApproverDsg.getString("approved_date")))
						paralist.add(lvform);
						duplicate=rsApproverDsg.getString("approved_date");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	request.setAttribute("ApproverList", paralist);
}
if(existStatus==false){

String getRepMgrDesig="select dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where " +
" emp.PERNR='"+approverID+"' and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID";
ResultSet rsRepMgrDesig=ad.selectQuery(getRepMgrDesig);
try {
	LeaveForm leaveForm2=null;
	while(rsRepMgrDesig.next())
	{
	 leaveForm2=new LeaveForm();
	leaveForm2.setApptype("Approve Manager");
	leaveForm2.setApproverID(approverID);
	reportMgrDesg=rsRepMgrDesig.getString("DSGSTXT");
	reportingMgrName=rsRepMgrDesig.getString("EMP_FULLNAME");
	leaveForm2.setAppDesig(reportMgrDesg);
	leaveForm2.setApproverName(reportingMgrName);
	
	}
String getApproveTimings="select * from Permission_details where request_no='"+reqId+"' and user_id='"+user.getEmployeeNo()+"'";
ResultSet rsTimings=ad.selectQuery(getApproveTimings);
while(rsTimings.next())
{
	String reqstatus=rsTimings.getString("Approver_Status");
	leaveForm2.setStatus(rsTimings.getString("Approver_Status"));
	if(reqstatus.equalsIgnoreCase("Approved"))
	{
		leaveForm2.setApprovedDate(rsTimings.getString("approved_date"));
	}
	
	if(reqstatus.equalsIgnoreCase("Rejected")||reqstatus.equalsIgnoreCase("Cancelled"))
	{
		leaveForm2.setApprovedDate(rsTimings.getString("rejected_date"));
	}
	leaveForm2.setComments(rsTimings.getString("Comments"));
}
approverList.add(leaveForm2);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
request.setAttribute("ApproverList", approverList);

}


		
		return approverList;}
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
					"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' order by Priority ";
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
					"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and e.essType='Leave'";
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
						"DESIGNATION as desg  where employeeNumber='"+user.getEmployeeNo()+"' and  e.Parallel_Approver1=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'";
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
			"DESIGNATION as desg  where employeeNumber='"+user.getEmployeeNo()+"' and  e.Parallel_Approver2=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'";
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
	
	public ActionForward selfcancelRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		PermissionForm permissionform = (PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			permissionform.setMessage2("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		
		String requestno=request.getParameter("reqno");
		
		String modifyRequest="update All_Request set Req_Status='Self Cancelled'  where Req_Type='Permission' and Req_Id='"+requestno+"'";	
		int statsChange=ad.SqlExecuteUpdate(modifyRequest);	
		if(statsChange>0){
			permissionform.setMessage("Request has been cancelled successfully");
		 
		String updateSql= "update Permission_details set Approver_Status='4'  where request_no='"+requestno+"'";
		int ii=ad.SqlExecuteUpdate(updateSql);
		}
		
		displayPermissions(mapping, permissionform, request, response);
		return mapping.findForward("permissionList");
	}
	
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		PermissionForm permissionform = (PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			permissionform.setMessage2("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		
		int requestNo=Integer.parseInt(request.getParameter("RequestNo"));
		String output="Permission.pdf";
		String path="Permission.jrxml";
	    
		String pat3=getServlet().getServletContext().getRealPath("/jasperReports/"+path);
		String pat_out=getServlet().getServletContext().getRealPath("/jasperReports/"+output);
		System.out.println("path of the JRXML File is *********************"+path);
		System.out.println("pat3 of the JRXML File is *********************"+pat3);
		try {
		String path2="Permission.jasper";
		//String path2="MarksCard.jasper";
		String path1=getServlet().getServletContext().getRealPath("/jasperReports/"+path2);
		System.out.println("Path of the JRXML File is *********************"+path1);
		File reportFile=new File(path1);
		Map parameters = new HashMap();
		
		InputStream input = new FileInputStream(new File(pat3));
		
		JasperDesign design = JRXmlLoader.load(input);
		Map param_map = new HashMap();
		Map param_map2 = new HashMap();
		
		param_map.put("REQUEST_NO", requestNo);
		JasperReport jReport = JasperCompileManager.compileReport(design);
		Connection connection=ConnectionFactory.getConnection();
		JasperPrint jasperPrint = JasperFillManager.fillReport(jReport, param_map, connection);
		 List<JasperPrint> jlist=new ArrayList<JasperPrint>();
		 jlist.add(jasperPrint);
		session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
		 if(jasperPrint!=null)
		  { 
			 //JRHtmlExporterParameter.BETWEEN_PAGES_HTML;
			 FileOutputStream os = new FileOutputStream(pat_out);
			 //JasperExportManager.exportReportToPdfFile(jasperPrint, pat_out);
			 JRPdfExporter exporter = new JRPdfExporter();
			 exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jlist);
			 exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, os);
			 //JasperExportManager.exportReportToHtml(jasperPrint,"yy.html");
			   //JasperExportManager.exportReportToPdfFile(jasperPrint,path4 + "/"+"db_stats.pdf"); 
			   // int t=pdfasbytes.length;
			   // System.out.println("Length===="+t);
			 exporter.exportReport();
			 
			 OutputStream servletOutputStream = null;
			 ByteArrayOutputStream htmlStream =new ByteArrayOutputStream();
			 byte[] report=htmlStream.toByteArray();
			 
			 
			 response.reset();
				response.setContentType("application/pdf");
			
			 
			 		int length=0;
					StringBuffer buf=new StringBuffer();
					ServletOutputStream outstream=response.getOutputStream();
					response.setHeader("Content-disposition", "attachment; filename="+pat_out+"");
					//response.setContentLength(pdfasbytes.length);
					FileInputStream input1 = new FileInputStream(pat_out);
		                ServletOutputStream sos = response.getOutputStream();   
		                byte[] bbuf = new byte[4096];   

		              DataInputStream in = new DataInputStream(input1); 
		                
		               while ((in != null) && ((length = in.read(bbuf)) != -1))   
		               {   
		                    sos.write(bbuf,0,length);   
		               }   
		                
		                
		              in.close();   
		                sos.flush();   
					outstream.close();
		  } 
		
	} catch (JRException e) {
		e.printStackTrace();
		displayPermissions(mapping, form, request, response);
		permissionform.setMessage2("Error..When generating Print");
	} catch (IOException e) {
		e.printStackTrace();
		displayPermissions(mapping, form, request, response);
	}
	    
	    
	
	
return mapping.findForward("");	

	}
	
	public ActionForward lastCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		
		
		PermissionForm permissionform = (PermissionForm)form;
		LinkedList permissionList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");

		int userID=user.getId();
		try{
			int totalRecords=permissionform.getTotalRecords();//21
			int startRecord=permissionform.getStartRecord();//11
			int endRecord=permissionform.getEndRecord();	


			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 permissionform.setTotalRecords(totalRecords);
			 permissionform.setStartRecord(startRecord);
			 permissionform.setEndRecord(totalRecords);
			 int year=0;
				if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
				{
					year=Calendar.getInstance().get(Calendar.YEAR);
				}
				else
				{
					 year = Integer.parseInt(request.getParameter("year"));
				}

				permissionform.setYear(Integer.toString(year));
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

						permissionform.setYearList(yearList);



			 String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no desc) AS RowNum,request_no,date,startTime,"
			 		+ "endTime,Approver_Status,CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),"
		   		+ "Created_date,108) as Created_date1 from Permission_details where user_id='"+user.getEmployeeNo()+"'and year(date)='"+year+"' )" +
		   	" as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
		    ResultSet rs=ad.selectQuery(getRecord);

		while(rs.next())
		{
		PermissionForm	permissionForm2 = new PermissionForm();
		permissionForm2.setRequestNumber(rs.getInt("request_no"));
		permissionForm2.setReqdate(rs.getString("Created_date1"));
		permissionForm2.setStartTime(rs.getString("startTime"));
		permissionForm2.setEndTime(rs.getString("endTime"));

		String date = rs.getString("date");
		String a[]=date.split(" ");
		date=a[0];
		String b[]=date.split("-");
		date=b[2]+"/"+b[1]+"/"+b[0];

		permissionForm2.setDate(date);
		int OnDuty_status=rs.getInt("Approver_Status");
		//String OnDuty_status =rs.getString("Approver_Status");

		String status="";
		if(OnDuty_status==0)
		{
			status="Pending";
		}
		if(OnDuty_status==1)
		{
			status="Approved";
		}
		if(OnDuty_status==2)
		{
			status="Rejected";
		}
		if(OnDuty_status==3)
		{
			status="Cancelled";
		}
		if(OnDuty_status==4)
		{
			status="Self Cancelled";
		}
		permissionForm2.setApproverStatus(status);

		permissionList.add(permissionForm2);
		}
		request.setAttribute("permissionList", permissionList);



			 request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton");
				if(permissionList.size()<10)
				{

					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				request.setAttribute("displayRecordNo", "displayRecordNo");

	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("permissionList");
 
	
	}
	
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		PermissionForm permissionform = (PermissionForm)form;
		LinkedList permissionList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
	try{


		int totalRecords=permissionform.getTotalRecords();//21
		int endRecord=permissionform.getStartRecord()-1;//20
		int startRecord=permissionform.getStartRecord()-10;//11

		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}


		permissionform.setTotalRecords(totalRecords);
		permissionform.setStartRecord(1);
		permissionform.setEndRecord(10);
		int year=0;
		if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
		{
			year=Calendar.getInstance().get(Calendar.YEAR);
		}
		else
		{
			 year = Integer.parseInt(request.getParameter("year"));
		}

		permissionform.setYear(Integer.toString(year));
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

				permissionform.setYearList(yearList);



		String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no desc) AS RowNum,request_no,date,startTime,"
				+ "endTime,Approver_Status,CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),"
		   		+ "Created_date,108) as Created_date1 from Permission_details where user_id='"+user.getEmployeeNo()+"'and year(date)='"+year+"')" +
   		" as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
ResultSet rs=ad.selectQuery(getRecord);

while(rs.next())
{
PermissionForm	permissionForm2 = new PermissionForm();
permissionForm2.setRequestNumber(rs.getInt("request_no"));
permissionForm2.setReqdate(rs.getString("Created_date1"));
permissionForm2.setStartTime(rs.getString("startTime"));
permissionForm2.setEndTime(rs.getString("endTime"));

String date = rs.getString("date");
String a[]=date.split(" ");
date=a[0];
String b[]=date.split("-");
date=b[2]+"/"+b[1]+"/"+b[0];

permissionForm2.setDate(date);
int OnDuty_status=rs.getInt("Approver_Status");
//String OnDuty_status =rs.getString("Approver_Status");

String status="";
if(OnDuty_status==0)
{
	status="Pending";
}
if(OnDuty_status==1)
{
	status="Approved";
}
if(OnDuty_status==2)
{
	status="Rejected";
}
if(OnDuty_status==3)
{
	status="Cancelled";
}
if(OnDuty_status==4)
{
	status="Self Cancelled";
}
permissionForm2.setApproverStatus(status);

permissionList.add(permissionForm2);
}
request.setAttribute("permissionList", permissionList);



permissionform.setTotalRecords(totalRecords);
permissionform.setStartRecord(startRecord);
permissionform.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(permissionList.size()<10)
			{
				permissionform.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}


	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("permissionList");
		
	
	}
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		
		PermissionForm permissionform = (PermissionForm)form;

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		LinkedList permissionList=new LinkedList();
	try{
		int totalRecords=permissionform.getTotalRecords();//21
		int startRecord=permissionform.getStartRecord();//11
		int endRecord=permissionform.getEndRecord();

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
			 int year=0;
				if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
				{
					year=Calendar.getInstance().get(Calendar.YEAR);
				}
				else
				{
					 year = Integer.parseInt(request.getParameter("year"));
				}

				permissionform.setYear(Integer.toString(year));
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

						permissionform.setYearList(yearList);


		String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no desc) AS RowNum,request_no,date,startTime,"
				+ "endTime,Approver_Status,CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),"
		   		+ "Created_date,108) as Created_date1 from Permission_details where user_id='"+user.getEmployeeNo()+"' and year(date)='"+year+"' )" +
		   		" as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
	ResultSet rs=ad.selectQuery(getRecord);

	while(rs.next())
	{
		PermissionForm	permissionForm2 = new PermissionForm();
		permissionForm2.setRequestNumber(rs.getInt("request_no"));
		permissionForm2.setReqdate(rs.getString("Created_date1"));
		permissionForm2.setStartTime(rs.getString("startTime"));
		permissionForm2.setEndTime(rs.getString("endTime"));

		String date = rs.getString("date");
		String a[]=date.split(" ");
		date=a[0];
		String b[]=date.split("-");
		date=b[2]+"/"+b[1]+"/"+b[0];

		permissionForm2.setDate(date);
		int OnDuty_status=rs.getInt("Approver_Status");
		//String OnDuty_status =rs.getString("Approver_Status");

		String status="";
		if(OnDuty_status==0)
		{
			status="Pending";
		}
		if(OnDuty_status==1)
		{
			status="Approved";
		}
		if(OnDuty_status==2)
		{
			status="Rejected";
		}
		if(OnDuty_status==3)
		{
			status="Cancelled";
		}
		if(OnDuty_status==4)
		{
			status="Self Cancelled";
		}
		permissionForm2.setApproverStatus(status);

		permissionList.add(permissionForm2);
	}
	request.setAttribute("permissionList", permissionList);
		}


		System.out.println("list length="+permissionList.size());

		 if(permissionList.size()!=0)
			{
			 permissionform.setTotalRecords(totalRecords);
			 permissionform.setStartRecord(startRecord);
			 permissionform.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;

				permissionform.setTotalRecords(totalRecords);
				permissionform.setStartRecord(start);
				permissionform.setEndRecord(end);

			}
		 if(permissionList.size()<10)
		 {
			 permissionform.setTotalRecords(totalRecords);
			 permissionform.setStartRecord(startRecord);
			 permissionform.setEndRecord(startRecord+permissionList.size()-1);
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
		return mapping.findForward("permissionList");
	
	}
	
	public ActionForward displaycancellist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {	
		PermissionForm permissionForm =(PermissionForm)form;
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			  int  totalRecords=0;
			  int  startRecord=0;
			  int  endRecord=0;
			  try{
				  String getTotalRecords="select count(*) from Permission_details where user_id='"+user.getEmployeeNo()+"' and cancelflag=0   ";
				  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
				  while(rsTotalRecods.next())
				  {
					  totalRecords=rsTotalRecods.getInt(1);
				  }
			   if(totalRecords>=10)
			  {
			
					  permissionForm.setTotalRecords(totalRecords);
					  startRecord=1;
					  endRecord=10;
					  permissionForm.setStartRecord(1);
					  permissionForm.setEndRecord(10);
					  request.setAttribute("displayRecordNo", "displayRecordNo");
					  request.setAttribute("nextButton", "nextButton");
					  }else
					  {
						  startRecord=1;
						  endRecord=totalRecords;
						  permissionForm.setTotalRecords(totalRecords);
						  permissionForm.setStartRecord(1);
						  permissionForm.setEndRecord(totalRecords); 
				   
				   
			  }			
			   ArrayList permissionList=new ArrayList();
			   PermissionForm permissionForm2 = null;
			   String sql = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no desc) AS  RowNum,request_no,date,startTime," +
			   		"endTime,Approver_Status,type,CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),Created_date,108) as Created_date1 from Permission_details where user_id='"+user.getEmployeeNo()+"' and cancelflag=0  and Approver_Status=1 and DATEDIFF(day,date,GETDATE())<60 ) as sub Where  sub.RowNum between 1 and 20";
			   ResultSet rs=ad.selectQuery(sql);
				while (rs.next()) {
					permissionForm2 = new PermissionForm();
					permissionForm2.setRequestNumber(rs.getInt("request_no"));
					permissionForm2.setReqdate(rs.getString("Created_date1"));
					permissionForm2.setType(rs.getString("type"));
					permissionForm2.setStartTime(rs.getString("startTime"));
					permissionForm2.setEndTime(rs.getString("endTime"));
					
					String date = rs.getString("date");
					String a[]=date.split(" ");
					date=a[0];
					String b[]=date.split("-");
					date=b[2]+"/"+b[1]+"/"+b[0];
					
					permissionForm2.setDate(date);
					int OnDuty_status=rs.getInt("Approver_Status");
					//String OnDuty_status =rs.getString("Approver_Status");
					
					String status="";
					if(OnDuty_status==0)
					{
						status="Pending";
					}
					if(OnDuty_status==1)
					{
						status="Approved";
					}
					if(OnDuty_status==2)
					{
						status="Rejected";
					}
					if(OnDuty_status==3)
					{
						status="Cancelled";
					}
					if(OnDuty_status==4)
					{
						status="Self Cancelled";
					}
					permissionForm2.setApproverStatus(status);
					
					permissionList.add(permissionForm2);
				
				}
				request.setAttribute("permissionList", permissionList);
				request.setAttribute("disablePreviousButton","disablePreviousButton");
				if(permissionList.size()==0)
				{
					request.setAttribute("noRecords", "noRecords");
					permissionForm.setMessage("No records are found");
				}
			  
		}catch (Exception e) {
		e.printStackTrace();
		}
				return mapping.findForward("cancelpermissionList");
				}

	public ActionForward submitcancelRequest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	
	{
		PermissionForm leaveForm = (PermissionForm) form;
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
		
		
		
		String as="select * from ess_cancel_appr where Location=(select LOCATION_CODE from Location where LOCID="+user.getPlantId()+")";
		ResultSet ads=ad.selectQuery(as);
		try {
			while(ads.next())
			{		
			
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+Req_Id+"','Cancel Permission','"+user.getEmployeeNo()+"','"+submitDate+"','Pending','','"+ads.getString("HR_NO")+"','No','"+user.getEmployeeNo()+"','"+leaveForm.getRemark()+"')";
			 i=ad.SqlExecuteUpdate(saveRecReq);
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 if(i > 0){
				leaveForm.setMessage("Data Submitted Successfully");
				 //update flag
				 String update="update Permission_details set cancelflag=1 where request_no='"+Req_Id+"'";
				int j =ad.SqlExecuteUpdate(update);
			}
		
		 else
			{
				leaveForm.setMessage("Error Please Check");
			}
	
		 editCancelRequest(mapping, leaveForm, request, response);
		return mapping.findForward("editcancelPermission");
	}
	
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
	
	public ActionForward editCancelRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		PermissionForm permissionform = (PermissionForm)form;
		String requestNo=request.getParameter("requstNo");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String reqmon="";
		String reqyear="";
		String reqstart="";
		String reqend="";
		String reqstart1="";
		String reqend1="";
		String reqmonname="";
		try{
			String getdetails="select right(left(convert(varchar(11),date,106) ,6),3)as monname,day(date) as startday,right('0' + rtrim(month(date)),2) as mon,YEAR(date) as year,CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),Created_date,108) as Created_date1,* from Permission_details where request_no='"+requestNo+"'";
			ResultSet rs=ad.selectQuery(getdetails);
			while(rs.next())
			{
				permissionform.setRequestNumber(Integer.parseInt(rs.getString("request_no")));
				reqmon=rs.getString("mon");
				reqyear=rs.getString("year");
				reqmonname=rs.getString("monname");
				if(rs.getInt("cancelflag")==0)
				{
					request.setAttribute("cancelbutton", "cancelbutton");
				}
				reqstart=rs.getString("startday");
				reqend=rs.getString("startday");
				reqstart1=display1(rs.getDate("date"));
				reqend1=display1(rs.getDate("date"));
				String date = rs.getString("date");
				String a[]=date.split(" ");
				date=a[0];
				String b[]=date.split("-");
				date=b[2]+"/"+b[1]+"/"+b[0];
				permissionform.setDate(date);
				permissionform.setStartTime(rs.getString("startTime"));
				permissionform.setEndTime(rs.getString("endTime"));
				permissionform.setReason(rs.getString("reason"));
				permissionform.setType(rs.getString("type"));
				int perm_status=rs.getInt("Approver_Status");
				permissionform.setComments(rs.getString("comments"));
				//String OnDuty_status =rs.getString("Approver_Status");
				
				
				
				String status="";
				if(perm_status==0)
				{
					status="Pending";
				}
				if(perm_status==1)
				{
					status="Approved";
				}
				if(perm_status==2)
				{
					status="Rejected";
				}
				if(perm_status==3)
				{
					status="Cancelled";
				}
				if(perm_status==4)
				{
					status="Self Cancelled";
				}
				permissionform.setApproverStatus(status);
				String approveDate="";
				if(status.equalsIgnoreCase("Approved"))
				{
					approveDate=rs.getString("approved_date");
				}
				if(status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("Cancelled"))
				{
					approveDate=rs.getString("rejected_date");
				}
				if(!(approveDate.equalsIgnoreCase("")))
				{
					String c[]=approveDate.split(" ");
					approveDate=a[0];
					String d[]=approveDate.split("-");
					approveDate=d[2]+"/"+d[1]+"/"+d[0]+" "+c[1];
					permissionform.setApproveDate(approveDate);
				}
			
				permissionform.setReqdate(rs.getString("Created_date1"));
				
			}
			Permission lA = new Permission();
			LinkedList appList= lA.viewOnduty(mapping, form, request, response);
			request.setAttribute("appList", appList);
			
			
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
				
	
				if (attDataList.size() > 0) {
					request.setAttribute("attDataList", attDataList);
				}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		permissionform.setMessage2("Apply");
		return mapping.findForward("editcancelPermission");
		
	
		
	}
	public ActionForward editPermission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PermissionForm permissionform = (PermissionForm)form;
		String requestNo=request.getParameter("requstNo");
		String requesterNo="";
		try{
			String getdetails="select *,CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),Created_date,108) as Created_date1 from Permission_details where request_no='"+requestNo+"'";
			ResultSet rs=ad.selectQuery(getdetails);
			while(rs.next())
			{
				
				
				String date = rs.getString("date");
				String a[]=date.split(" ");
				date=a[0];
				String b[]=date.split("-");
				date=b[2]+"/"+b[1]+"/"+b[0];
				permissionform.setDate(date);
				permissionform.setStartTime(rs.getString("startTime"));
				permissionform.setEndTime(rs.getString("endTime"));
				permissionform.setReason(rs.getString("reason"));
				permissionform.setType(rs.getString("type"));
				int perm_status=rs.getInt("Approver_Status");
				permissionform.setComments(rs.getString("comments"));
				requesterNo=rs.getString("user_id");
				
				//String OnDuty_status =rs.getString("Approver_Status");
				
				
				
				String status="";
				if(perm_status==0)
				{
					status="Pending";
				}
				if(perm_status==1)
				{
					status="Approved";
				}
				if(perm_status==2)
				{
					status="Rejected";
				}
				if(perm_status==3)
				{
					status="Cancelled";
				}
				if(perm_status==4)
				{
					status="Self Cancelled";
				}
				permissionform.setApproverStatus(status);
				String approveDate="";
				if(status.equalsIgnoreCase("Approved"))
				{
					approveDate=rs.getString("approved_date");
				}
				if(status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("Cancelled"))
				{
					approveDate=rs.getString("rejected_date");
				}
				if(!(approveDate.equalsIgnoreCase("")))
				{
					String c[]=approveDate.split(" ");
					approveDate=a[0];
					String d[]=approveDate.split("-");
					approveDate=d[2]+"/"+d[1]+"/"+d[0]+" "+c[1];
					permissionform.setApproveDate(approveDate);
					
				}
				permissionform.setReqdate(rs.getString("Created_date1"));
				permissionform.setSwipetype(rs.getString("swipe_type"));
				
			}}catch (Exception e) {
				e.printStackTrace();
			}
			
			String reqType="";
			boolean existStatus = false;
			String HRid="";
			LinkedList 	paralist = new LinkedList();		
			String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+requesterNo+"' and essType='Leave' group by reqType";
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
			String duplicate="";
			if(existStatus==true){
				if(reqType.equalsIgnoreCase("sequential"))
				{
				String getApproverDesig=" select priority ,  e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
										" where employeeNumber='"+requesterNo+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' "
										+ " union "
										+" select top 1 10 as priority, e.hr_no ,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_CANCEL_APPR as e,emp_official_info as emp,DESIGNATION as desg  "
										+ " where e.hr_no=emp.PERNR and emp.DSGID=desg.DSGID and e.location = '"+EmpLoc(requesterNo)+"' "
										+ " order by Priority";
				 
				ResultSet rsA=ad.selectQuery(getApproverDesig);
					try {
						while(rsA.next())
						{
							LeaveForm lvform=new LeaveForm();
							 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
							 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
							String getdetails1=" select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date," +
									"all_R.rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
									" where all_R.Req_Id='"+requestNo+"' and all_R.Req_Type='Permission' and ((emp.PERNR=all_R.last_approver) or " +
									"(emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) and((Last_Approver='"+rsA.getString("ApproverId")+"') or " +
									"(Pending_Approver='"+rsA.getString("ApproverId")+"'))  and Requester_Name='"+requesterNo+"'";
							
							ResultSet rsApproverDsg=ad.selectQuery(getdetails1);
							
							while(rsApproverDsg.next())
							{
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
							   lvform.setApprovedDate(rsApproverDsg.getString("approved_date"));
								}
								
								if(reqstatus.equalsIgnoreCase("Rejected"))
								{
									  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
									  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
								     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
								}
								if(reqstatus.equalsIgnoreCase("Cancelled"))
								{
									  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
									  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
								     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
								}
								lvform.setComments(rsApproverDsg.getString("Comments"));
								
								if(HRid!=null)
								{
									if( reqstatus.equalsIgnoreCase("Approved")){
									String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
									
									ResultSet rss=ad.selectQuery(hrdetails);
									
									while(rss.next())
									{
										lvform.setApprover(rss.getString("EMP_FULLNAME"));
										 lvform.setAppDesig(rss.getString("DSGSTXT"));
									}
								}
								}if(rsApproverDsg.getString("approved_date")!=null)
								{
								if(!duplicate.equalsIgnoreCase(rsApproverDsg.getString("approved_date")))
									paralist.add(lvform);
									duplicate=rsApproverDsg.getString("approved_date");
								}
							
							}
							
							
							
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					request.setAttribute("appList", paralist);
					
				}
				if(reqType.equalsIgnoreCase("parallel"))
				{
					
		
					String getApproverDesig=" select priority, e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
						" where employeeNumber='"+requesterNo+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'"
						+ " union "
						+ " select priority, e.Parallel_Approver1,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
						" where employeeNumber='"+requesterNo+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'"
						+ " union "
						+ " select priority, e.Parallel_Approver1,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
						" where employeeNumber='"+requesterNo+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' "
						+ " union "
						+ " select top 1 10 as priority , e.hr_no ,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_CANCEL_APPR as e,emp_official_info as emp,DESIGNATION as desg  "
						+ " where e.hr_no=emp.PERNR and emp.DSGID=desg.DSGID and e.location = '"+EmpLoc(requesterNo)+"' "
						+ " order by priority  ";
					 
					ResultSet rsA=ad.selectQuery(getApproverDesig);
						try {
							while(rsA.next())
							{
								LeaveForm lvform=new LeaveForm();
								 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
								 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
								String getdetails1=" select all_R.Comments,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date," +
										"all_R.rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
										" where all_R.Req_Id='"+requestNo+"' and all_R.Req_Type='Permission' and ((emp.PERNR=all_R.last_approver) or " +
										"(emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) and((Last_Approver='"+rsA.getString("ApproverId")+"') or " +
										"(Pending_Approver='"+rsA.getString("ApproverId")+"'))  and Requester_Name='"+requesterNo+"'";
								
								ResultSet rsApproverDsg=ad.selectQuery(getdetails1);
								
								
									while(rsApproverDsg.next())
									{
									
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
									   lvform.setApprovedDate(rsApproverDsg.getString("approved_date"));
										}
										
										if(reqstatus.equalsIgnoreCase("Rejected"))
										{
											  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
											  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
										     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
										}
										if(reqstatus.equalsIgnoreCase("Cancelled"))
										{
											  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
											  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
										     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
										}
										lvform.setComments(rsApproverDsg.getString("Comments"));
										
										if(rsApproverDsg.getString("approved_date")!=null)
										{
										if(!duplicate.equalsIgnoreCase(rsApproverDsg.getString("approved_date")))
											paralist.add(lvform);
											duplicate=rsApproverDsg.getString("approved_date");
										}
										
										if(reqstatus.equalsIgnoreCase("Pending"))
										{
											paralist.add(lvform);
										}
									
									}
								
								
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						request.setAttribute("appList", paralist);
						
						
				}
			}
			
			/*
			Permission lA = new Permission();
			LinkedList appList= lA.viewOnduty(mapping, form, request, response);
			request.setAttribute("appList", appList);*/
		
		return mapping.findForward("editPermission");
		
		
	}	
	
	public ActionForward markattendance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PermissionForm permissionform = (PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		try{
	
			permissionform.setDate("");
			permissionform.setStartTime("");
			permissionform.setEndTime("");
			permissionform.setReason("");
			
		}catch (Exception e) {
			
		}
		
		LinkedList appList= getApprovers(request, response);
		String getApproverDesig= " select hr_no, emp.EMP_FULLNAME,desg.DSGSTXT  from ess_cancel_appr e, emp_official_info as emp,DESIGNATION as desg where  "
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
		
		if(appList.size()==0)
			permissionform.setMessage2("No Approvers assigned.Please Contact Admin");
		
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select distinct year from EMP_IN_OUT_Status order by year desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("year"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		permissionform.setYearList(yearList);
		
		 String chek="select *  from location where   location.LOCID='"+user.getPlantId()+"'";
		  ResultSet d=ad.selectQuery(chek);
		 try {
			if(d.next())
			 {
				
					permissionform.setEndRecord(d.getInt("Mark_att"));
					
				
				
				 
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mapping.findForward("markattendance");
	}
	
	public ActionForward forgetswipe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PermissionForm permissionform = (PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		try{
	
			permissionform.setDate("");
			permissionform.setStartTime("");
			permissionform.setEndTime("");
			permissionform.setReason("");
			
		}catch (Exception e) {
			
		}
		
		LinkedList appList= getApprovers(request, response);
		String getApproverDesig= " select hr_no, emp.EMP_FULLNAME,desg.DSGSTXT  from ess_cancel_appr e, emp_official_info as emp,DESIGNATION as desg where  "
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
		
		if(appList.size()==0)
			permissionform.setMessage2("No Approvers assigned.Please Contact Admin");
		
		if(request.getParameter("sId").equalsIgnoreCase("forgot Swipe"))
			request.setAttribute("forget", "forget");
		else
			request.setAttribute("personal", "personal");
		
		 String chek="select *  from location where   location.LOCID='"+user.getPlantId()+"'";
			  ResultSet d=ad.selectQuery(chek);
			 try {
				if(d.next())
				 {
					if(request.getParameter("sId").equalsIgnoreCase("forgot Swipe"))
					{
						permissionform.setEndRecord(d.getInt("Forgot_swipe"));
						 permissionform.setType("Forgot Swipe");
					}
					else
					{
						permissionform.setEndRecord(d.getInt("Permission_count"));
						 permissionform.setType("Personal");
					}
					 
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	
		
		return mapping.findForward("forgetswipe");
	}
	
	public ActionForward permission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PermissionForm permissionform = (PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		try{
	
			permissionform.setDate("");
			permissionform.setStartTime("");
			permissionform.setEndTime("");
			permissionform.setReason("");
			
		}catch (Exception e) {
			
		}
		
		LinkedList appList= getApprovers(request, response);
		String getApproverDesig= " select hr_no, emp.EMP_FULLNAME,desg.DSGSTXT  from ess_cancel_appr e, emp_official_info as emp,DESIGNATION as desg where  "
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
		
		if(appList.size()==0)
			permissionform.setMessage2("No Approvers assigned.Please Contact Admin");
		
		return mapping.findForward("permission");
	}
	
	public String getrailwaytime(String time)
	{
		String reqtime="";
		
	  
	      //Format of the date defined in the input String
	      DateFormat df = new SimpleDateFormat("hh:mm aa");
	      //Desired format: 24 hour format: Change the pattern as per the need
	      DateFormat outputformat = new SimpleDateFormat("HH:mm");
	      Date date = null;
	  
	      try{
	         //Converting the input String to Date
	    	 date= df.parse(time);
	         //Changing the format of date and storing it in String
	    	 reqtime = outputformat.format(date);
	         //Displaying the date
	    	 System.out.println(reqtime);
	      }catch(ParseException pe){
	         pe.printStackTrace();
	       }
	   
		
		return reqtime;
	}
	
	public ActionForward saveMarkPermission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		PermissionForm permissionform = (PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 	
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
				
				String dates[]=request.getParameterValues("selectedRequestNo");
				
				
				
				String reason=permissionform.getReason();
				if(dates!=null)
				{
				for(int y=0;y<dates.length;y++)
				{
					
					String swipes[]=request.getParameterValues("swipecheck"+dates[y]);
					
					if(swipes.length>1)
					{
						int requestNo=0;
						String getRequstNo="select max(request_no) from Permission_details";
						ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
						while(rsRequestNo.next())
						{
							requestNo=rsRequestNo.getInt(1);
						}
						requestNo=requestNo+1;
						
						permissionform.setRequestNumber(requestNo);
					
						String startDate=dates[y];
						String a[]=startDate.split("/");
						 
						startDate=a[2]+"-"+a[1]+"-"+a[0];
						try{
									 	
						 	int approver1=0;
							String approvermail="";
							String pApprover="";
							 String dateNow=dates[y];
						    
						    String reqdate=dates[y];
						    
						    String reqmon="";
						    String reqyear="";
						    String reqday="";
						    
							String apprmgr="";
							String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
									+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
							ResultSet approverRS=ad.selectQuery(getApproverID);
							if(approverRS.next()){
								
								apprmgr = approverRS.getString("ApproverId");
								
							}	
							
							 String[] monthName = {"","JAN", "FEB",
									  "MAR", "APR", "MAY", "JUN", "JUL",
									  "AUG", "SEP", "OCT", "NOV",
									  "DEC"
									  };
							 String f[]=dates[y].split("/");
							 reqday=f[0];
							 reqmon=f[1];
							 reqyear=f[2];
							 
							 

							 String allow="";
							  
							  String chek="select case when count(*)>=max(Mark_att) then 0 else 1 end as allow from Permission_details,location where type in ('Mark Attendance')"
							  		+ " and (Approver_Status not in (2,3,4)) and month(date)='"+reqmon+"'   and year(date)='"+reqyear+"' and user_id='"+user.getEmployeeNo()+"' and location.LOCID='"+user.getPlantId()+"'";
							  ResultSet d=ad.selectQuery(chek);
							 if(d.next())
							 {
								 allow=d.getString("allow");
							 }
							  
							 if(allow.equalsIgnoreCase("0"))
							 {
								 permissionform.setMessage2("No. of Mark Attendance Count Exceeded for the Month...");
								 markattendance(mapping, permissionform, request, response);
									return mapping.findForward("markattendance");
							 }
							 
							//check nof permission in month
							 
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
							
							
									
										 
									 
				
							try{
								
							boolean check= false;
							
							//chekc if alread ypending record is der
							int alreadypres=0;
							String chekc="select count(*) from Permission_details where user_id='"+user.getEmployeeNo()+"' and date='"+startDate+"' and swipe_type='"+swipes[0]+"' and approver_status not in (2,3,4)";
							ResultSet wf=ad.selectQuery(chekc);
							if(wf.next())
							{
								alreadypres=wf.getInt(1);
							}
							if(alreadypres>0)
							{
								permissionform.setMessage2("Request Already Raised for the date '"+startDate+"'");
								markattendance(mapping, permissionform, request, response);
								return mapping.findForward("markattendance");
								
							}
							
							if(check==false)
							{
								String minutes="0";
								 SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
								 SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
						
								String Permissions ="";
										if(swipes[1].equalsIgnoreCase("In"))
										{
									
											String _24HourTime=request.getParameter("iNTIME"+dates[y]);;
											 Date _24HourDt = _24HourSDF.parse(_24HourTime);
											String it=_12HourSDF.format(_24HourDt).replace(" ", "");
											
											Permissions= "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type,avail_minutes)" +
								" values ('"+permissionform.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+startDate+"','"+it+"','"+reason+"','"+user.getFirstName()+"','0','','','"+pApprover+"','','"+apprmgr+"','','','Mark Attendance','In',("+minutes+"))";
										}
										else
										{
											
											String _24HourTime=request.getParameter("oUTTIME"+dates[y]);
											 Date _24HourDt = _24HourSDF.parse(_24HourTime);
											String out=_12HourSDF.format(_24HourDt).replace(" ", "");
											
						 Permissions = "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type,avail_minutes)" +
				" values ('"+permissionform.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+startDate+"','"+out+"','"+reason+"','"+user.getFirstName()+"','0','','','"+pApprover+"','','"+apprmgr+"','','','Mark Attendance','Out',("+minutes+"))";
															}
															
										
										
										
						int i=0;
						i=ad.SqlExecuteUpdate(Permissions);
						
						if(i>0)
						{
					   		EMailer email = new EMailer();
							permissionform.setMessage("Request submitted Successfully");
							
							
							int	ij = email.sendMailToApprover(request, approvermail,Integer.toString(requestNo), "Permission");
							
							permission(mapping, form, request, response);
							
							
						}else{
							permissionform.setMessage2("Error...Permission not Send");
							
						}
								
							}
							}catch (Exception e) {
								e.printStackTrace();
							}
							
						
				//insert into All_Request and send mail to approver
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
							
							e.printStackTrace();
						}
						int i=0;
						String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
						saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
						 i=ad.SqlExecuteUpdate(saveRecReq);
						 if(i > 0){
							}
					}
					if(reqType.equalsIgnoreCase("parallel"))
					{
						String parallelApprover1="";
						String parallelApprover2="";
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
								
								e.printStackTrace();
							}
							int i=0;
						String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
							saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
							 i=ad.SqlExecuteUpdate(saveRecReq);
							if(!(parallelApprover1.equalsIgnoreCase("")))
							{
								saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+parallelApprover1+"','','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);
							}
							if(!(parallelApprover2.equalsIgnoreCase("")))
							{
								saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
								saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+parallelApprover2+"','','"+user.getEmployeeNo()+"','')";
								 i=ad.SqlExecuteUpdate(saveRecReq);
							}
							
					}
				}
				int i=0;
				if(existStatus==false){

					String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
				saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
				 i=ad.SqlExecuteUpdate(saveRecReq);
				}
				}
						
						
					}catch (Exception e) {
						e.printStackTrace();
					}
					}
				
					
			int requestNo=0;
			String getRequstNo="select max(request_no) from Permission_details";
			ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
			while(rsRequestNo.next())
			{
				requestNo=rsRequestNo.getInt(1);
			}
			requestNo=requestNo+1;
			
			permissionform.setRequestNumber(requestNo);
		
			String startDate=dates[y];
			String a[]=startDate.split("/");
			 
			startDate=a[2]+"-"+a[1]+"-"+a[0];
			try{
						 	
			 	int approver1=0;
				String approvermail="";
				String pApprover="";
				 String dateNow=dates[y];
			    
			    String reqdate=dates[y];
			    
			    String reqmon="";
			    String reqyear="";
			    String reqday="";
			    
				String apprmgr="";
				String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
						+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
				ResultSet approverRS=ad.selectQuery(getApproverID);
				if(approverRS.next()){
					
					apprmgr = approverRS.getString("ApproverId");
					
				}	
				
				 String[] monthName = {"","JAN", "FEB",
						  "MAR", "APR", "MAY", "JUN", "JUL",
						  "AUG", "SEP", "OCT", "NOV",
						  "DEC"
						  };
				 String f[]=dates[y].split("/");
				 reqday=f[0];
				 reqmon=f[1];
				 reqyear=f[2];
				 
				 
				 String allow="";
				  
				  String chek="select case when count(*)>=max(Mark_att) then 0 else 1 end as allow from Permission_details,location where type in ('Mark Attendance')"
				  		+ " and (Approver_Status not in (2,3,4)) and month(date)='"+reqmon+"'   and year(date)='"+reqyear+"' and user_id='"+user.getEmployeeNo()+"' and location.LOCID='"+user.getPlantId()+"'";
				  ResultSet d=ad.selectQuery(chek);
				 if(d.next())
				 {
					 allow=d.getString("allow");
				 }
				  
				 if(allow.equalsIgnoreCase("0"))
				 {
					 permissionform.setMessage2("No. of Mark Attendance Count Exceeded for the Month...");
					 markattendance(mapping, permissionform, request, response);
						return mapping.findForward("markattendance");
				 }
				 
				//check nof permission in month
				 
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
				
				
			
	
				try{
					
				boolean check= false;
				
				//chekc if alread ypending record is der
				int alreadypres=0;
				String chekc="select count(*) from Permission_details where user_id='"+user.getEmployeeNo()+"' and date='"+startDate+"' and swipe_type='"+swipes[0]+"' and approver_status not in (2,3,4)";
				ResultSet wf=ad.selectQuery(chekc);
				if(wf.next())
				{
					alreadypres=wf.getInt(1);
				}
				if(alreadypres>0)
				{
					permissionform.setMessage2("Request Already Raised for the date '"+startDate+"'");
					markattendance(mapping, permissionform, request, response);
					return mapping.findForward("markattendance");
					
				}
 				
				
				if(check==false)
				{
					String minutes="0";
					 SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
					 SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
			
					String Permissions ="";
							if(swipes[0].equalsIgnoreCase("In"))
							{
						
								String _24HourTime=request.getParameter("iNTIME"+dates[y]);
								 Date _24HourDt = _24HourSDF.parse(_24HourTime);
								String it=_12HourSDF.format(_24HourDt).replace(" ", "");
								
								Permissions= "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type,avail_minutes)" +
					" values ('"+permissionform.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+startDate+"','"+it+"','"+reason+"','"+user.getFirstName()+"','0','','','"+pApprover+"','','"+apprmgr+"','','','Mark Attendance','In',("+minutes+"))";
							}
							else
							{
								
								String _24HourTime=request.getParameter("oUTTIME"+dates[y]);
								 Date _24HourDt = _24HourSDF.parse(_24HourTime);
								String out=_12HourSDF.format(_24HourDt).replace(" ", "");
								
			 Permissions = "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type,avail_minutes)" +
	" values ('"+permissionform.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+startDate+"','"+out+"','"+reason+"','"+user.getFirstName()+"','0','','','"+pApprover+"','','"+apprmgr+"','','','Mark Attendance','Out',("+minutes+"))";
												}
												
							
							
							
			int i=0;
			i=ad.SqlExecuteUpdate(Permissions);
			
			if(i>0)
			{
		   		EMailer email = new EMailer();
				permissionform.setMessage("Request Submitted Successfully");
				
				
				int	ij = email.sendMailToApprover(request, approvermail,Integer.toString(requestNo), "Permission");
				
				permission(mapping, form, request, response);
				
				
			}else{
				permissionform.setMessage2("Error...Permission not Send");
				
			}
					
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			
	//insert into All_Request and send mail to approver
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
				
				e.printStackTrace();
			}
			int i=0;
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
			 i=ad.SqlExecuteUpdate(saveRecReq);
			 if(i > 0){
				}
		}
		if(reqType.equalsIgnoreCase("parallel"))
		{
			String parallelApprover1="";
			String parallelApprover2="";
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
					
					e.printStackTrace();
				}
				int i=0;
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
				saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
				 i=ad.SqlExecuteUpdate(saveRecReq);
				if(!(parallelApprover1.equalsIgnoreCase("")))
				{
					saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+parallelApprover1+"','','"+user.getEmployeeNo()+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
				}
				if(!(parallelApprover2.equalsIgnoreCase("")))
				{
					saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+parallelApprover2+"','','"+user.getEmployeeNo()+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
				}
				
		}
	}
	int i=0;
	if(existStatus==false){

		String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
	saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
	 i=ad.SqlExecuteUpdate(saveRecReq);
	}
	}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		   LeaveAction lA = new LeaveAction();
		LinkedList appList= lA.getApprovers(request, response);
		String getApproverDesig= "select hr_no, emp.EMP_FULLNAME,desg.DSGSTXT  from ess_cancel_appr e, emp_official_info as emp,DESIGNATION as desg where  "
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
		
			}
			}
markattendance(mapping, permissionform, request, response);
			
			}}}
			
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return mapping.findForward("markattendance");
		
		
		
	
		
	}
	
	public ActionForward savePermission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PermissionForm permissionform = (PermissionForm)form;
		if(permissionform.getSwipetype()==null)
		{
			permissionform.setSwipetype("Out");
		}
		if(permissionform.getSwipetype().equalsIgnoreCase(""))
		{
			permissionform.setSwipetype("Out");
		}
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String permission_type="";
		int min_time=0;
		int max_time=0;
		String locc="select PERMISSION_TYPE,P_MINIMUM_MIN,P_MAX_MIN from location where locid='"+user.getPlantId()+"'";
		ResultSet getit=ad.selectQuery(locc);
		try {
			while(getit.next())
			{
				permission_type=getit.getString("PERMISSION_TYPE");
				min_time=getit.getInt("P_MINIMUM_MIN");
				max_time=getit.getInt("P_MAX_MIN");
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try{
			Permission lA1=new Permission();
			LinkedList appList1= lA1.getApprovers(request, response);
			if(appList1.size()==0)
				permissionform.setMessage2("No Approvers assigned.Please Contact Admin");
			if(appList1.size()>0){
			synchronized (this) {
				
				//Permission resticted  6 days	
				 String allowtoapply="";
				 String reqdate=permissionform.getDate();

				    String reqmon="";
				    String reqyear="";
				    String reqday="";

				 String f[]=reqdate.split("/");
				 reqday=f[0];
				 reqmon=f[1];
				 reqyear=f[2];
				 if(permissionform.getType().equalsIgnoreCase("Personal"))
					{
               String abcdef="select case when convert(date, dateadd(day,-(select Apply_after_Permission from Location where locid='"+user.getPlantId()+"'),getdate()) )<='"+(f[2]+"-"+f[1]+"-"+f[0])+"' then 1 else 0 end as allowdate ";
               ResultSet nn=ad.selectQuery(abcdef);
               while(nn.next())
               {
              	 allowtoapply=nn.getString("allowdate");
               }

               if(allowtoapply.equalsIgnoreCase("0"))
               {
              	 permissionform.setMessage2("Request Can be applied  only within 6 days after going on permission..Contact HR");

              		return mapping.findForward("forgetswipe");
               }
					}
				 if(permissionform.getType().equalsIgnoreCase("Forgot Swipe"))
					{
         String abcdef="select case when convert(date, dateadd(day,-(select Apply_after_FORGETSWIPE from Location where locid='"+user.getPlantId()+"'),getdate()) )<='"+(f[2]+"-"+f[1]+"-"+f[0])+"' then 1 else 0 end as allowdate ";
         ResultSet nn=ad.selectQuery(abcdef);
         while(nn.next())
         {
        	 allowtoapply=nn.getString("allowdate");
         }

         if(allowtoapply.equalsIgnoreCase("0"))
         {
        	 permissionform.setMessage2("Request Can be applied  only within 3 days after going on permission..Contact HR");

        		return mapping.findForward("forgetswipe");
         }
         
					}  
               
            // permission for onduty check


               String reqStarttime=permissionform.getStartTime();
               String reqEndtime=permissionform.getEndTime();
               String allowtoapply1="";


               String checkOnDutyRecords="select *from OnDuty_details where convert(datetime,convert(nvarchar(12),"
               		+ "'"+(f[2]+"-"+f[1]+"-"+f[0])+"')+' '+convert(nvarchar(12),'"+reqStarttime+"'))between convert(datetime,"
               				+ "convert(nvarchar(12),start_date)+' '+convert(nvarchar(12),startTime)) and convert(datetime,"
               				+ "convert(nvarchar(12),end_date)+' '+convert(nvarchar(12),endtime)) and Approver_Status not in ( 'Rejected' ,'Self Cancelled') and user_id='"+user.getEmployeeNo()+"' union select *from OnDuty_details where convert(datetime,convert(nvarchar(12),"
               				+ "'"+(f[2]+"-"+f[1]+"-"+f[0])+"')+' '+convert(nvarchar(12),'"+reqEndtime+"')) between convert(datetime,"
               						+ "convert(nvarchar(12),start_date)+' '+convert(nvarchar(12),startTime)) and convert(datetime,"
               						+ "convert(nvarchar(12),end_date)+' '+convert(nvarchar(12),endtime)) and Approver_Status not in ( 'Rejected' ,'Self Cancelled')  and user_id='"+user.getEmployeeNo()+"'";
               	ResultSet rsOutDutyRec=ad.selectQuery(checkOnDutyRecords);

               	 while(rsOutDutyRec.next())
                   {
                  	 allowtoapply1="0";
                   }
                     if(allowtoapply1.equalsIgnoreCase("0"))
                   {
                  	 permissionform.setMessage2("Already Onduty applied for the selected duration");

                  		return mapping.findForward("forgetswipe");
                   }
				
			
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
			try{
			  	String getAppliedDates="Select * from OnDuty_details where (OnDuty_status=0 or OnDuty_status=1) and  user_id='"+user.getEmployeeNo()+"' " +
			  			"and ('"+startDate+"' between start_date and end_date )";
				ResultSet rsAppDate=ad.selectQuery(getAppliedDates);
				while(rsAppDate.next())
				{/*
					String onDutystartDt=rsAppDate.getString("start_date");
					String onDutyendDt=rsAppDate.getString("end_date");
					String fromTime=rsAppDate.getString("startTime");
					String fromTimeCycle=fromTime.substring(5, 7);
					String toTimeCycle="";
					String toTime=rsAppDate.getString("endTime");
					if(!toTime.equalsIgnoreCase(""))
					 toTimeCycle=toTime.substring(5, 7);
					
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
						{
							fromHours=fromHours+12;	
						}
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
					
					String reqTime=permissionform.getStartTime();
					String reqTimeCycle=reqTime.substring(5, 7);
					reqTime=permissionform.getStartTime();
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
					
					
					
					String reqToTime=permissionform.getEndTime();
					String reqToTimeCycle=reqToTime.substring(5, 7);
					reqToTime=permissionform.getEndTime();
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
					System.out.println("reqMint="+reqMint);
					System.out.println("reqToMint="+reqToMint);
					if(startDate.equalsIgnoreCase(onDutystartDt) || startDate.equalsIgnoreCase(onDutyendDt))
					{
							
							if(fromMint<reqMint && reqMint<toMint )
							{
								permissionform.setMessage2("OnDuty already applied for this timings..Please check ");
								  
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
							if(fromMint<reqToMint && reqToMint<toMint )
							{
								permissionform.setMessage2("OnDuty already applied for this timings..Please check ");
								  
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
							if(reqMint<fromMint && toMint<reqToMint )
							{
								permissionform.setMessage2("OnDuty already applied for this timings..Please check ");
								  
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
							
							
							
						
						
					}
					if(!(startDate.equalsIgnoreCase(onDutystartDt)) && !(startDate.equalsIgnoreCase(onDutyendDt))){
						permissionform.setMessage2("OnDuty already applied for this timings..Please check ");
						  
					    LeaveAction lA = new LeaveAction();
						LinkedList appList= lA.getApprovers(request, response);
						request.setAttribute("appList", appList);
						return mapping.findForward("permission");
					}
				*/}
				
			  }catch (Exception e) {
				e.printStackTrace();
			  }
			
			 //check Leave
			  
			  String checkLeaveRecords="Select * from leave_details where user_id='"+user.getEmployeeNo()+"' and (Approvel_Status='Pending' or  Approvel_Status='Approved') and " +
			  		" ('"+startDate+"' between start_date and end_date )  ";
			  ResultSet rsLeaveRec=ad.selectQuery(checkLeaveRecords);
			  while(rsLeaveRec.next())
			  {/*
				  String startDateDuration=rsLeaveRec.getString("start_duration");
				  String endDurationType=rsLeaveRec.getString("end_duration");
					String lvStDate=rsLeaveRec.getString("start_date");
					String x[]=lvStDate.split(" ");
					lvStDate=x[0];
					String lvEndDate=rsLeaveRec.getString("end_date");
					String y[]=lvEndDate.split(" ");
					lvEndDate=y[0];
					
					String draftstatus=rsLeaveRec.getString("record_status");
					String reqTime=permissionform.getStartTime();
					String reqTimeCycle=reqTime.substring(5, 7);
					reqTime=permissionform.getStartTime();
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
					
					
					
					String reqToTime=permissionform.getEndTime();
					String reqToTimeCycle=reqToTime.substring(5, 7);
					reqToTime=permissionform.getEndTime();
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
					
					if(startDate.equalsIgnoreCase(lvStDate))
					{
						if(startDateDuration.equalsIgnoreCase("FD") )
						{
							 permissionform.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
								 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
						}
						if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
						{
							if(reqMint<780)
							{
							 permissionform.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
								 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
						}
						if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FH"))
						{
							if(reqToMint>780)
							{
							 permissionform.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
								 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
						}
						if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
						{
							if(reqToMint>780)
							{
								permissionform.setMessage2("Leave already applied for this timings..Please check ");
								 if(draftstatus.equalsIgnoreCase("Draft")){
									 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
									}
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
						}
						if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
						{
							permissionform.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
								 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
						    LeaveAction lA = new LeaveAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("permission");
						}
						
					}
					
					if(startDate.equalsIgnoreCase(lvEndDate))
					{
						if(endDurationType.equalsIgnoreCase("FD") )
						{
							 permissionform.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
								 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
						}
						if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
						{
							if(reqToMint<780)
							{
						
							permissionform.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
								 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
						    LeaveAction lA = new LeaveAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("permission");
							}
						}
						if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FH"))
						{
							if(reqToMint<780)
							{
							 permissionform.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
								 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
						}
						if(endDurationType.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
						{
							if(reqToMint<=780)
							{
								permissionform.setMessage2("Leave already applied for this timings..Please check ");
								 if(draftstatus.equalsIgnoreCase("Draft")){
									 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
									}
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
						}
						if(endDurationType.equalsIgnoreCase("SH") && startDateDuration.equalsIgnoreCase("SH"))
						{
							if(reqToMint>780)
							{
								permissionform.setMessage2("Leave already applied for this timings..Please check ");
								 if(draftstatus.equalsIgnoreCase("Draft")){
									 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
									}
							    LeaveAction lA = new LeaveAction();
								LinkedList appList= lA.getApprovers(request, response);
								request.setAttribute("appList", appList);
								return mapping.findForward("permission");
							}
						}
						if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
						{
							permissionform.setMessage2("Leave already applied for this timings..Please check ");
							 if(draftstatus.equalsIgnoreCase("Draft")){
								 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
								}
						    LeaveAction lA = new LeaveAction();
							LinkedList appList= lA.getApprovers(request, response);
							request.setAttribute("appList", appList);
							return mapping.findForward("permission");
						}
						
					}
					if(!(startDate.equalsIgnoreCase(lvEndDate)) && !(startDate.equalsIgnoreCase(lvStDate))){
						permissionform.setMessage2("Leave already applied for this timings..Please check ");
						 if(draftstatus.equalsIgnoreCase("Draft")){
							 permissionform.setMessage2(" Leave already Saved in Drafts for this timings..Please check ");
							}
					    LeaveAction lA = new LeaveAction();
						LinkedList appList= lA.getApprovers(request, response);
						request.setAttribute("appList", appList);
						return mapping.findForward("permission");
					}
		
				*/}
			 	
			  
			  
			  
			  
			  
			 	
			 	int approver1=0;
				String approvermail="";
				String pApprover="";
				 String dateNow=permissionform.getDate();
			    
			     reqdate=permissionform.getDate();
			    
			     reqmon="";
			     reqyear="";
			     reqday="";
			    
				String apprmgr="";
				String getApproverID="select ess.ApproverId,emp.EMAIL_ID from ESS_Approvers as ess,emp_official_info as emp where "
						+ "ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
				ResultSet approverRS=ad.selectQuery(getApproverID);
				if(approverRS.next()){
					
					apprmgr = approverRS.getString("ApproverId");
					
				}	
				
				 String[] monthName = {"","JAN", "FEB",
						  "MAR", "APR", "MAY", "JUN", "JUL",
						  "AUG", "SEP", "OCT", "NOV",
						  "DEC"
						  };
				  f=reqdate.split("/");
				 reqday=f[0];
				 reqmon=f[1];
				 reqyear=f[2];
				 
				//check nof permission in month
				 int difference=0;
				 if(permission_type!=null)
				 {
					 if(permission_type.equalsIgnoreCase("COUNT WISE"))
					 {
						 
					 
				 if(permissionform.getType().equalsIgnoreCase("Late") || permissionform.getType().equalsIgnoreCase("Early") )
				 {
				  String allow="";
				  
				  String chek="select case when count(*)>=max(Permission_count) then 0 else 1 end as allow from Permission_details,location where type in ('Early','Late')"
				  		+ " and (Approver_Status not in (2,3,4)) and month(date)='"+reqmon+"'   and year(date)='"+reqyear+"' and user_id='"+user.getEmployeeNo()+"' and location.LOCID='"+user.getPlantId()+"'and firstname='"+user.getFullName()+"'";
				  ResultSet d=ad.selectQuery(chek);
				 if(d.next())
				 {
					 allow=d.getString("allow");
				 }
				  
				 if(allow.equalsIgnoreCase("0"))
				 {
					 permissionform.setMessage2("No. of Permission Count Exceeded for the Month...");
					 permission(mapping, permissionform, request, response);
						return mapping.findForward("forgetswipe");
				 }
				 
				 
				
				 
				 ///max hrs limit for early yand late  early
				 String allowhrs="";
				
				 String minutes="0";
					if(permissionform.getType().equalsIgnoreCase("Early"))
					{
						minutes="select datediff(MINUTE,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ (left('"+permissionform.getStartTime()+"',5)+' '+right('"+permissionform.getStartTime()+"',2)), 120) ,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ (left('"+permissionform.getEndTime()+"',5)+' '+right('"+permissionform.getEndTime()+"',2)), 120))  minutes";
					}
					if(permissionform.getType().equalsIgnoreCase("Late"))
					{
						minutes=" select datediff(MINUTE,EMP_SHIFT_MASTER.Shift_start_time ,CONVERT(time,CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ "
								+ "  (left('"+permissionform.getStartTime()+"',5)+' '+right('"+permissionform.getStartTime()+"',2)),120)) minutes  from  	EMP_SHIFT_MASTER,EMP_Shift_Register    where"
								+ "	 EMP_SHIFT_MASTER.Shift_code=EMP_Shift_Register.Day"+Integer.parseInt(reqday)+" and  EMP_Shift_Register.Pernr='"+user.getEmployeeNo()+"' and"
								+ " EMP_Shift_Register.Month='"+reqmon+"' and EMP_Shift_Register.Year='"+reqyear+"' ";				
				   }
               
               
               String maX="  select  case when sum(minutes)>max(Permission_Hrs*60) then 0 else 1 end allow from ("
               		+ ""+minutes+" union all select sum(avail_minutes) from Permission_details where user_id='"+user.getEmployeeNo()+"' and month(date)='"+reqmon+"' and year(date)='"+reqyear+"' and type in ('Early','Late') and (Approver_Status not in (2,3,4)) "
               		+ ")t,Location where location.LOCID='"+user.getPlantId()+"'";               
             
               
               ResultSet jj=ad.selectQuery(maX);
               if(jj.next())
               {
            	   allowhrs= jj.getString("allow");
            	   
               }
               
               if(allowhrs.equalsIgnoreCase("0"))
               {
            	   permissionform.setMessage2("No. of Permission Hours Exceeded for the Month...");
					 permission(mapping, permissionform, request, response);
						return mapping.findForward("forgetswipe");
               }
               
           
				 }
				 
				 
				   
				
				    
				    
				    if(permissionform.getType().equalsIgnoreCase("Personal") )
				 {
					 
					 
					 String allow="";
					  
					  String chek="select case when count(*)>=max(Permission_count) then 0 else 1 end as allow from Permission_details,location where type in ('Personal')"
					  		+ " and (Approver_Status not in (2,3,4)) and month(date)='"+reqmon+"'   and year(date)='"+reqyear+"' and user_id='"+user.getEmployeeNo()+"' and location.LOCID='"+user.getPlantId()+"'";
					  ResultSet d=ad.selectQuery(chek);
					 if(d.next())
					 {
						 allow=d.getString("allow");
					 }
					  
					 if(allow.equalsIgnoreCase("0"))
					 {
						 permissionform.setMessage2("No. of Permission Count Exceeded for the Month...");
						 permission(mapping, permissionform, request, response);
							return mapping.findForward("forgetswipe");
					 }
					 
					  allow="";
					  
					   chek="select case when count(*)>=max(Permission_count_yearly) then 0 else 1 end as allow from Permission_details,location where type in ('Personal')"
					  		+ " and (Approver_Status not in (2,3,4))    and year(date)='"+reqyear+"' and user_id='"+user.getEmployeeNo()+"' and location.LOCID='"+user.getPlantId()+"'";
					   d=ad.selectQuery(chek);
					 if(d.next())
					 {
						 allow=d.getString("allow");
					 }
					  
					 if(allow.equalsIgnoreCase("0"))
					 {
						 permissionform.setMessage2("No. of Permission Count Exceeded for the Year...");
						 permission(mapping, permissionform, request, response);
							return mapping.findForward("forgetswipe");
					 }
					 
					 
					 
						//diff of 1 hr checkjim for coprartew
							
							SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
							try {
							
							 
							    
							  
							    String Time = null;
							
							    // Get time values of the date objects
						        long l1 = sdf.parse(permissionform.getStartTime().substring(0, 5)+" "+permissionform.getStartTime().substring(5, 7)).getTime();
						        long l2 = sdf.parse(permissionform.getEndTime().substring(0, 5)+" "+permissionform.getEndTime().substring(5, 7)).getTime();
						        difference = (int) (l2 - l1);
						        difference = (difference < 0 ? -difference : difference) / 60000;
						        if (difference > 60) {
						            int Hour = difference / 60;
						            int Mins = difference % 60;
						            if (Mins == 0)
						                Time = Hour + " Hours";
						            else
						                Time = Hour + " Hours" + " " + Mins + " Mins";
						        } else {
						            Time = difference + " " + "Mins";
						        }
							    
								if(difference>max_time)
								{
									 permissionform.setMessage2("Permission Hours cannot be more than "+max_time+" Minutes...");
									 permission(mapping, permissionform, request, response);
										return mapping.findForward("forgetswipe");
								}
								if(difference<min_time)
								{
									 permissionform.setMessage2("Permission Hours cannot be less than "+max_time+" Minutes...");
									 permission(mapping, permissionform, request, response);
										return mapping.findForward("forgetswipe");
								}
									
									
							} catch (ParseException ex) {
							    ex.printStackTrace();
							}
							
							
							
							
						
					 
				 }
				
				 
				 if(permissionform.getType().equalsIgnoreCase("Forgot Swipe") )
				 {
					 String allow="";
					  
					  String chek="select case when count(*)>=max(Forgot_swipe) then 0 else 1 end as allow from Permission_details,location where type in ('Forgot Swipe')"
					  		+ " and (Approver_Status not in (2,3,4)) and month(date)='"+reqmon+"'   and year(date)='"+reqyear+"' and user_id='"+user.getEmployeeNo()+"' and location.LOCID='"+user.getPlantId()+"'";
					  ResultSet d=ad.selectQuery(chek);
					 if(d.next())
					 {
						 allow=d.getString("allow");
					 }
					  
					 if(allow.equalsIgnoreCase("0"))
					 {
						 permissionform.setMessage2("No. of Forgot Swipe  Count Exceeded for the Month...");
						 permission(mapping, permissionform, request, response);
							return mapping.findForward("forgetswipe");
					 }
					 
				 }
				 
				 
					 }
				 }
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
			String date="select Count(*) from Permission_details where date='"+startDate+"' and user_id='"+user.getEmployeeNo()+"'";
			ResultSet rs2=ad.selectQuery(date);
			while(rs2.next())
			{
				test=rs2.getInt(1);
			}
			if(test>0){ 
				try{
					
				boolean check= false;
				
				String getAppliedDates="Select * from Permission_details where date='"+startDate+"' and user_id='"+user.getEmployeeNo()+"'";
				ResultSet rsAppDate=ad.selectQuery(getAppliedDates);
				while(rsAppDate.next())
				{
					String fromTime=rsAppDate.getString("startTime");
					String fromTimeCycle=fromTime.substring(5, 7);

			/*		String toTime=rsAppDate.getString("endTime");
					String toTimeCycle=fromTime.substring(5, 7);*/
					
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
				/*	toTime=rsAppDate.getString("endTime");
					if(!toTime.equalsIgnoreCase("")||toTime!=null)
					{
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
					
					
					String reqTime=permissionform.getStartTime();
					String reqTimeCycle=reqTime.substring(5, 7);
					reqTime=permissionform.getStartTime();
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
					
					
					String reqToTimeCycle="";
					String reqToTime=permissionform.getEndTime();
					if(!reqToTime.equalsIgnoreCase(""))
					{
					 reqToTimeCycle=reqToTime.substring(5, 7);
					reqToTime=permissionform.getEndTime();
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
					
					
					
					if ((fromMint<=reqMint) && (reqMint<=toMint)) {
					  
					    System.out.println(true);
					    check=true;
						permissionform.setMessage2("Permission already applied for this timings..Please change the timings ");
					}
					if ((fromMint<=reqToMint) && (reqToMint<=toMint)) {
						  
					    System.out.println(true);
					    check=true;
						permissionform.setMessage2("Permission already applied for this timings..Please change the timings ");
					}
					}
					else
					{
						
					}
					}
					else
					{
						
					}*/
					
				}
				
				if(check==false)
				{
					String minutes="0";
					if(permissionform.getType().equalsIgnoreCase("Early"))
					{
						minutes="select datediff(MINUTE,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ (left('"+permissionform.getStartTime()+"',5)+' '+right('"+permissionform.getStartTime()+"',2)), 120) ,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ (left('"+permissionform.getEndTime()+"',5)+' '+right('"+permissionform.getEndTime()+"',2)), 120)) ";
					}
					int check_minutes=0;
					//int difference=0;
					int opbal=0;
					int clobal=0;
					int availbal=0;
					int awtbal=0;
					 if(permission_type!=null)
					 {
						 if(permission_type.equalsIgnoreCase("MINUTE WISE"))
						 {
					if(permissionform.getType().equalsIgnoreCase("Personal"))
					{
						
						String getDet="select *  from permission_mASTER where per_empcode='"+user.getEmployeeNo()+"' and per_calyear=year(current_timestamp)";
						ResultSet dd= ad.selectQuery(getDet);
						
					
						
						
						if(dd.next())
						{
							
							
							/*SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
							try {
							
							 
							    
							  
							    String Time = null;
							   
							    // Get time values of the date objects
						        long l1 = sdf.parse(permissionform.getStartTime().substring(0, 5)+" "+permissionform.getStartTime().substring(5, 7)).getTime();
						        long l2 = sdf.parse(permissionform.getEndTime().substring(0, 5)+" "+permissionform.getEndTime().substring(5, 7)).getTime();
						        difference = (int) (l2 - l1);
						        difference = (difference < 0 ? -difference : difference) / 60000;
						        if (difference > 60) {
						            int Hour = difference / 60;
						            int Mins = difference % 60;
						            if (Mins == 0)
						                Time = Hour + " Hours";
						            else
						                Time = Hour + " Hours" + " " + Mins + " Mins";
						        } else {
						            Time = difference + " " + "Mins";
						        }
							    
								
									
							} catch (ParseException ex) {
							    ex.printStackTrace();
							}*/
							
							check_minutes=dd.getInt("per_clbal")-difference;
							opbal=dd.getInt("per_opbal");
							clobal=opbal-check_minutes;
							if(user.getPlantId().equalsIgnoreCase("16"))
							{
								if(check_minutes<0)
								{
									permissionform.setMessage2("Only "+dd.getInt("per_clbal")+" minutes is  available");
									permission(mapping, permissionform, request, response);
									return mapping.findForward("forgetswipe");
								}
								
								if(dd.getInt("per_clbal")==0)
								{
									permissionform.setMessage2("Permission Minutes over");
									permission(mapping, permissionform, request, response);
									return mapping.findForward("forgetswipe");
								}
						}
							
							
							
						}
						else
						{
							check_minutes=difference;
						String insert="insert into permission_mASTER(per_calyear,per_empcode,per_opbal,per_availed,per_clbal,per_awtBal,Prodata) values(year(current_timestamp),'"+user.getEmployeeNo()+"','180','0','180',"+difference+",'1')";
						int j= ad.SqlExecuteUpdate(insert);
						}
					}
						 }
					 }
					if(permissionform.getType().equalsIgnoreCase("Late"))
					{
						minutes=" select datediff(MINUTE,EMP_SHIFT_MASTER.Shift_start_time ,CONVERT(time,CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ "
								+ "  (left('"+permissionform.getStartTime()+"',5)+' '+right('"+permissionform.getStartTime()+"',2)),120))   from  	EMP_SHIFT_MASTER,EMP_Shift_Register    where"
								+ "	 EMP_SHIFT_MASTER.Shift_code=EMP_Shift_Register.Day"+Integer.parseInt(reqday)+" and  EMP_Shift_Register.Pernr='"+user.getEmployeeNo()+"' and"
								+ " EMP_Shift_Register.Month='"+reqmon+"' and EMP_Shift_Register.Year='"+reqyear+"' ";				
						}
					String Permissions="";
					if(permissionform.getType().equalsIgnoreCase("Personal"))
					{
					 Permissions = "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type,avail_minutes)" +
							" values ('"+permissionform.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+startDate+"','"+permissionform.getStartTime()+"','"+permissionform.getReason()+"','"+user.getFirstName()+"','0','','','"+pApprover+"','"+permissionform.getEndTime()+"','"+apprmgr+"','','','"+permissionform.getType()+"','"+permissionform.getSwipetype()+"',("+check_minutes+"))";
						
					 if(permission_type!=null)
					 {
						 if(permission_type.equalsIgnoreCase("MINUTE WISE"))
						 {
					 String updates="update permission_master set per_awtBal="+check_minutes+" where per_empcode='"+user.getEmployeeNo()+"' and per_calyear=year(current_timestamp)";
					int j1=ad.SqlExecuteUpdate(updates);
						 }
					 }
					}
					else
					{
					//insert query
					 Permissions = "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type,avail_minutes)" +
					" values ('"+permissionform.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+startDate+"','"+permissionform.getStartTime()+"','"+permissionform.getReason()+"','"+user.getFirstName()+"','0','','','"+pApprover+"','"+permissionform.getEndTime()+"','"+apprmgr+"','','','"+permissionform.getType()+"','"+permissionform.getSwipetype()+"',("+minutes+"))";
					}
			int i=0;
			i=ad.SqlExecuteUpdate(Permissions);
			
			if(i>0)
			{
		   		EMailer email = new EMailer();
				permissionform.setMessage("Permission submitted saved with  request number='"+requestNo+"' ");
				
				
				int	ij = email.sendMailToApprover(request, approvermail,Integer.toString(requestNo), "Permission");
				
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
				
				int check_minutes=0;
				//int difference=0;
				int opbal=0;
				int clobal=0;
				int availbal=0;
				int awtbal=0;
				 if(permission_type.equalsIgnoreCase("MINUTE WISE"))
				 {
			if(permissionform.getType().equalsIgnoreCase("Personal"))
			{
				
			
					
					String getDet="select *  from permission_mASTER where per_empcode='"+user.getEmployeeNo()+"' and per_calyear=year(current_timestamp)";
					ResultSet dd= ad.selectQuery(getDet);
					
				
					
					
					if(dd.next())
					{
						
						
						/*SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
						try {
						
						 
						    
						  
						    String Time = null;
						   
						    // Get time values of the date objects
					        long l1 = sdf.parse(permissionform.getStartTime().substring(0, 5)+" "+permissionform.getStartTime().substring(5, 7)).getTime();
					        long l2 = sdf.parse(permissionform.getEndTime().substring(0, 5)+" "+permissionform.getEndTime().substring(5, 7)).getTime();
					        difference = (int) (l2 - l1);
					        difference = (difference < 0 ? -difference : difference) / 60000;
					        if (difference > 60) {
					            int Hour = difference / 60;
					            int Mins = difference % 60;
					            if (Mins == 0)
					                Time = Hour + " Hours";
					            else
					                Time = Hour + " Hours" + " " + Mins + " Mins";
					        } else {
					            Time = difference + " " + "Mins";
					        }
						    
							
								
						} catch (ParseException ex) {
						    ex.printStackTrace();
						}*/
						
						check_minutes=dd.getInt("per_clbal")-difference;
						opbal=dd.getInt("per_opbal");
						clobal=opbal-check_minutes;
						if(!user.getPlantId().equalsIgnoreCase("16"))
						{
						if(check_minutes<0)
						{
							permissionform.setMessage2("Only "+dd.getInt("per_clbal")+" minutes is  available");
							permission(mapping, permissionform, request, response);
							return mapping.findForward("forgetswipe");
						}
						
						if(dd.getInt("per_clbal")==0)
						{
							permissionform.setMessage2("Permission Minutes over");
							permission(mapping, permissionform, request, response);
							return mapping.findForward("forgetswipe");
						}
					
					}
						
						
					}
					else
					{
						check_minutes=difference;
					String insert="insert into permission_mASTER(per_calyear,per_empcode,per_opbal,per_availed,per_clbal,per_awtBal,Prodata) values(year(current_timestamp),'"+user.getEmployeeNo()+"','180','0','180',"+check_minutes+",'1')";
					int j= ad.SqlExecuteUpdate(insert);
					}
				
			}
				 }
				
				String minutes="0";
				if(permissionform.getType().equalsIgnoreCase("Early"))
				{
					minutes="select datediff(MINUTE,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ (left('"+permissionform.getStartTime()+"',5)+' '+right('"+permissionform.getStartTime()+"',2)), 120) ,CONVERT(time, CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ (left('"+permissionform.getEndTime()+"',5)+' '+right('"+permissionform.getEndTime()+"',2)), 120)) ";
				}
				if(permissionform.getType().equalsIgnoreCase("Late"))
				{
					minutes=" select datediff(MINUTE,EMP_SHIFT_MASTER.Shift_start_time ,CONVERT(time,CONVERT(varchar,CONVERT(date, getdate()))+ ' '+ "
							+ "  (left('"+permissionform.getStartTime()+"',5)+' '+right('"+permissionform.getStartTime()+"',2)),120))   from  	EMP_SHIFT_MASTER,EMP_Shift_Register    where"
							+ "	 EMP_SHIFT_MASTER.Shift_code=EMP_Shift_Register.Day"+Integer.parseInt(reqday)+" and  EMP_Shift_Register.Pernr='"+user.getEmployeeNo()+"' and"
							+ " EMP_Shift_Register.Month='"+reqmon+"' and EMP_Shift_Register.Year='"+reqyear+"' ";				
					}
				String	 Permissions="";
				if(permissionform.getType().equalsIgnoreCase("Personal"))
				{
				//insert query
					 Permissions = "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type,avail_minutes)" +
							" values ('"+permissionform.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+startDate+"','"+permissionform.getStartTime()+"','"+permissionform.getReason()+"','"+user.getFirstName()+"','0','','','"+pApprover+"','"+permissionform.getEndTime()+"','"+apprmgr+"','','','"+permissionform.getType()+"','"+permissionform.getSwipetype()+"',("+check_minutes+"))";
				
					
				
					 if(permission_type.equalsIgnoreCase("MINUTE WISE"))
					 {
					 String updates="update permission_master set per_awtBal="+check_minutes+" where per_empcode='"+user.getEmployeeNo()+"' and per_calyear=year(current_timestamp)";
						int j1=ad.SqlExecuteUpdate(updates);
			
				
					 }
				}
				else
				{
						 Permissions = "insert into Permission_details(request_no,user_id,date,startTime,reason,firstname,Approver_Status,Forwarded_emp_id,Last_approver,Pending_approver,endTime,Approver_id,InTime,OutTime,type,swipe_type,avail_minutes)" +
							" values ('"+permissionform.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+startDate+"','"+permissionform.getStartTime()+"','"+permissionform.getReason()+"','"+user.getFirstName()+"','0','','','"+pApprover+"','"+permissionform.getEndTime()+"','"+apprmgr+"','','','"+permissionform.getType()+"','"+permissionform.getSwipetype()+"',("+minutes+"))";
				
				}
	
		int i=0;
		i=ad.SqlExecuteUpdate(Permissions);
		
		if(i>0)
		{
	   		EMailer email = new EMailer();
			permissionform.setMessage("Permission request submitted with  request number='"+requestNo+"' ");
			int	ij = email.sendMailToApprover(request, approvermail,Integer.toString(requestNo), "Permission");
			
			permission(mapping, form, request, response);
			
			
		}else{
			permissionform.setMessage2("Error...Permission not Send");
		}
				
			}
	//insert into All_Request and send mail to approver
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
				
				e.printStackTrace();
			}
			int i=0;
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
			saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
			 i=ad.SqlExecuteUpdate(saveRecReq);
			 if(i > 0){
				}
		}
		if(reqType.equalsIgnoreCase("parallel"))
		{
			String parallelApprover1="";
			String parallelApprover2="";
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
					
					e.printStackTrace();
				}
				int i=0;
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
				saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
				 i=ad.SqlExecuteUpdate(saveRecReq);
				if(!(parallelApprover1.equalsIgnoreCase("")))
				{
					saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+parallelApprover1+"','','"+user.getEmployeeNo()+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
				}
				if(!(parallelApprover2.equalsIgnoreCase("")))
				{
					saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
					saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+parallelApprover2+"','','"+user.getEmployeeNo()+"','')";
					 i=ad.SqlExecuteUpdate(saveRecReq);
				}
				
		}
	}
	int i=0;
	if(existStatus==false){

		String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments) values (";
	saveRecReq = saveRecReq + "'"+requestNo+"','Permission','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','','"+pApprover+"','','"+user.getEmployeeNo()+"','')";
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
		String getApproverDesig= "select hr_no, emp.EMP_FULLNAME,desg.DSGSTXT  from ess_cancel_appr e, emp_official_info as emp,DESIGNATION as desg where  "
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
		
	
		
				return mapping.findForward("forgetswipe");
		
		
		
	}
	
	public ActionForward printPermission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PermissionForm permissionform = (PermissionForm)form;
		String requestNo=request.getParameter("requstNo");
		String requesterNo="";
		try{
			String getdetails="select *,CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),Created_date,108) as Created_date1 from Permission_details where request_no='"+requestNo+"'";
			ResultSet rs=ad.selectQuery(getdetails);
			while(rs.next())
			{
				
				
				String date = rs.getString("date");
				String a[]=date.split(" ");
				date=a[0];
				String b[]=date.split("-");
				date=b[2]+"/"+b[1]+"/"+b[0];
				permissionform.setDate(date);
				permissionform.setStartTime(rs.getString("startTime"));
				permissionform.setEndTime(rs.getString("endTime"));
				permissionform.setReason(rs.getString("reason"));
				permissionform.setType(rs.getString("type"));
				int perm_status=rs.getInt("Approver_Status");
				permissionform.setComments(rs.getString("comments"));
				requesterNo=rs.getString("user_id");
				
				
				String emp="select * from emp_official_info as emp,department as dpt,location as loc where emp.pernr='"+rs.getString("user_id")+"' and emp.dptid=dpt.dptid and emp.locid=loc.location_code";
				ResultSet ff=ad.selectQuery(emp);
				while(ff.next())
				{
					permissionform.setEmployeeName(ff.getString("EMP_FULLNAME"));
					permissionform.setDepartment(ff.getString("DPTLTXT"));
					permissionform.setEmployeeNo(ff.getString("PERNR"));
					permissionform.setLocationId(ff.getString("LOCID"));
					permissionform.setLocname(ff.getString("LOCNAME"));
					
				}
				
				
				permissionform.setRequestNumber(rs.getInt("request_no"));
				
				//String OnDuty_status =rs.getString("Approver_Status");
				
				
				
				String status="";
				if(perm_status==0)
				{
					status="Pending";
				}
				if(perm_status==1)
				{
					status="Approved";
				}
				if(perm_status==2)
				{
					status="Rejected";
				}
				if(perm_status==3)
				{
					status="Cancelled";
				}
				if(perm_status==4)
				{
					status="Self Cancelled";
				}
				permissionform.setApproverStatus(status);
				String approveDate="";
				if(status.equalsIgnoreCase("Approved"))
				{
					approveDate=rs.getString("approved_date");
				}
				if(status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("Cancelled"))
				{
					approveDate=rs.getString("rejected_date");
				}
				if(!(approveDate.equalsIgnoreCase("")))
				{
					String c[]=approveDate.split(" ");
					approveDate=a[0];
					String d[]=approveDate.split("-");
					approveDate=d[2]+"/"+d[1]+"/"+d[0]+" "+c[1];
					permissionform.setApproveDate(approveDate);
					
				}
				permissionform.setReqdate(rs.getString("Created_date1"));
				permissionform.setSwipetype(rs.getString("swipe_type"));
				
			}}catch (Exception e) {
				e.printStackTrace();
			}
			
			String reqType="";
			boolean existStatus = false;
			String HRid="";
			LinkedList 	paralist = new LinkedList();		
			String checkESSApproverList="select count(*),reqType from ESS_Approvers where employeeNumber='"+requesterNo+"' and essType='Leave' group by reqType";
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
			String duplicate="";
			if(existStatus==true){
				if(reqType.equalsIgnoreCase("sequential"))
				{
				String getApproverDesig=" select priority ,  e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
										" where employeeNumber='"+requesterNo+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' "
										+ " union "
										+" select top 1 10 as priority, e.hr_no ,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_CANCEL_APPR as e,emp_official_info as emp,DESIGNATION as desg  "
										+ " where e.hr_no=emp.PERNR and emp.DSGID=desg.DSGID and e.location = '"+EmpLoc(requesterNo)+"' "
										+ " order by Priority";
				 
				ResultSet rsA=ad.selectQuery(getApproverDesig);
					try {
						while(rsA.next())
						{
							LeaveForm lvform=new LeaveForm();
							 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
							 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
							String getdetails1=" select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,CONVERT(varchar(10),all_R.approved_date,103)+' '+ CONVERT(varchar(5),all_R.approved_date,108) AS approved_date," +
									"CONVERT(varchar(10),all_R.rejected_date,103)+' '+ CONVERT(varchar(5),all_R.rejected_date,108) AS rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
									" where all_R.Req_Id='"+requestNo+"' and all_R.Req_Type='Permission' and ((emp.PERNR=all_R.last_approver) or " +
									"(emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) and((Last_Approver='"+rsA.getString("ApproverId")+"') or " +
									"(Pending_Approver='"+rsA.getString("ApproverId")+"'))  and Requester_Name='"+requesterNo+"'";
							
							ResultSet rsApproverDsg=ad.selectQuery(getdetails1);
							
							while(rsApproverDsg.next())
							{
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
							   lvform.setApprovedDate(rsApproverDsg.getString("approved_date"));
								}
								
								if(reqstatus.equalsIgnoreCase("Rejected"))
								{
									  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
									  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
								     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
								}
								if(reqstatus.equalsIgnoreCase("Cancelled"))
								{
									  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
									  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
								     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
								}
								lvform.setComments(rsApproverDsg.getString("Comments"));
								
								if(HRid!=null)
								{
									if( reqstatus.equalsIgnoreCase("Approved")){
									String hrdetails="select emp.EMP_FULLNAME,des.DSGSTXT from emp_official_info as emp,DESIGNATION as des where PERNR='"+HRid+"' and emp.DSGID=des.DSGID";
									
									ResultSet rss=ad.selectQuery(hrdetails);
									
									while(rss.next())
									{
										lvform.setApprover(rss.getString("EMP_FULLNAME"));
										 lvform.setAppDesig(rss.getString("DSGSTXT"));
									}
								}
								}if(rsApproverDsg.getString("approved_date")!=null)
								{
								if(!duplicate.equalsIgnoreCase(rsApproverDsg.getString("approved_date")))
									
									duplicate=rsApproverDsg.getString("approved_date");
								}
								paralist.add(lvform);
							}
							
							
							
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					request.setAttribute("appList", paralist);
					
				}
				if(reqType.equalsIgnoreCase("parallel"))
				{
					
		
					String getApproverDesig=" select priority, e.ApproverId,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
						" where employeeNumber='"+requesterNo+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'"
						+ " union "
						+ " select priority, e.Parallel_Approver1,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
						" where employeeNumber='"+requesterNo+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave'"
						+ " union "
						+ " select priority, e.Parallel_Approver1,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_Approvers as e,emp_official_info as emp,DESIGNATION as desg " +
						" where employeeNumber='"+requesterNo+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' "
						+ " union "
						+ " select top 1 10 as priority , e.hr_no ,emp.EMP_FULLNAME,desg.DSGSTXT  from ESS_CANCEL_APPR as e,emp_official_info as emp,DESIGNATION as desg  "
						+ " where e.hr_no=emp.PERNR and emp.DSGID=desg.DSGID and e.location = '"+EmpLoc(requesterNo)+"' "
						+ " order by priority  ";
					 
					ResultSet rsA=ad.selectQuery(getApproverDesig);
						try {
							while(rsA.next())
							{
								LeaveForm lvform=new LeaveForm();
								 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
								 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
								String getdetails1=" select all_R.Comments,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,CONVERT(varchar(10),all_R.approved_date,103)+' '+ CONVERT(varchar(5),all_R.approved_date,108) AS approved_date," +
										"CONVERT(varchar(10),all_R.rejected_date,103)+' '+ CONVERT(varchar(5),all_R.rejected_date,108) AS rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
										" where all_R.Req_Id='"+requestNo+"' and all_R.Req_Type='Permission' and ((emp.PERNR=all_R.last_approver) or " +
										"(emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) and((Last_Approver='"+rsA.getString("ApproverId")+"') or " +
										"(Pending_Approver='"+rsA.getString("ApproverId")+"'))  and Requester_Name='"+requesterNo+"'";
								
								ResultSet rsApproverDsg=ad.selectQuery(getdetails1);
								
								
									while(rsApproverDsg.next())
									{
									
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
									   lvform.setApprovedDate(rsApproverDsg.getString("approved_date"));
										}
										
										if(reqstatus.equalsIgnoreCase("Rejected"))
										{
											  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
											  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
										     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
										}
										if(reqstatus.equalsIgnoreCase("Cancelled"))
										{
											  lvform.setApprover(rsApproverDsg.getString("EMP_FULLNAME"));	
											  lvform.setAppDesig(rsApproverDsg.getString("DSGSTXT"));
										     lvform.setApprovedDate(rsApproverDsg.getString("rejected_date"));
										}
										lvform.setComments(rsApproverDsg.getString("Comments"));
										
										if(rsApproverDsg.getString("approved_date")!=null)
										{
										if(!duplicate.equalsIgnoreCase(rsApproverDsg.getString("approved_date")))
											paralist.add(lvform);
											duplicate=rsApproverDsg.getString("approved_date");
										}
										
										if(reqstatus.equalsIgnoreCase("Pending"))
										{
											paralist.add(lvform);
										}
									
									}
								
								
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						request.setAttribute("appList", paralist);
						
						
				}
			}
			
			/*
			Permission lA = new Permission();
			LinkedList appList= lA.viewOnduty(mapping, form, request, response);
			request.setAttribute("appList", appList);*/
		
		return mapping.findForward("printPermission");
		
		
	}	
	
	
	public ActionForward  permission_card(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		PermissionForm permissionForm =(PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		  int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		   int year=0;
					if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
					{
						year=Calendar.getInstance().get(Calendar.YEAR);
					}
					else
					{
						 year = Integer.parseInt(request.getParameter("year"));
					}
					
					
					String	empno=user.getEmployeeNo();
					
					
					/*leaveForm.setEmployeeName(user.getFullName());*/
					permissionForm.setEmployeeNo(empno);
					permissionForm.setYear(request.getParameter("year"));
					String emp = "Select dep.DPTSTXT,desg.DSGSTXT,emp.DOJ ,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg where PERNR='"
							+ empno
							+ "' and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID ";

					ResultSet rs1 = ad.selectQuery(emp);

					try {
						while (rs1.next())

						{
							permissionForm.setDepartment(rs1.getString("DPTSTXT"));
							permissionForm.setDesignation(rs1.getString("DSGSTXT"));
							permissionForm.setDoj(EMicroUtils.display1(rs1.getDate("DOJ")));
							permissionForm.setEmployeeName(rs1.getString("EMP_FULLNAME"));
						}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
					
					
					
					
					

					permissionForm.setYear(Integer.toString(year));
					ArrayList yearList=new ArrayList();
						ResultSet rs17 = ad.selectQuery("select distinct top 2 per_calyear from permission_master group by per_calyear order by per_calyear desc"); 
							try {
								while(rs17.next()) {
									yearList.add(rs17.getString("per_calyear"));
								}
								rs17.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							permissionForm.setYearList(yearList);
		  try{
			  
			  String per="select * from permission_master where per_calyear=year(current_timestamp) and per_empcode='"+user.getEmployeeNo()+"'";
			  ResultSet rs= ad.selectQuery(per);
			  if(rs.next())
			  {
				  PermissionForm new1= new PermissionForm();
				  permissionForm.setOpbal(rs.getInt("per_opbal"));
				  permissionForm.setClosbal(rs.getInt("per_clbal"));
				  permissionForm.setAvailbal(rs.getInt("per_availed"));
				  permissionForm.setAwtbal(rs.getInt("per_awtBal"));
				  permissionForm.setYear(rs.getString("per_calyear"));
				  new1.setEmployeeName(user.getFullName());
				  request.setAttribute("permissionBalenceList", "permissionBalenceList");
			  }
			  
			  
		  }catch (Exception e) {
	e.printStackTrace();

	}
		/*  String startdate = "" + year + "-01-01";
			String enddate = "" + year + "-12-31";*/
			LinkedList report = new LinkedList();
			String data = "select convert(nvarchar(11),date,103) as date,startTime,endTime,avail_minutes,convert(nvarchar(11),approved_date,103) as approved_date,reason from permission_details where user_id='"
					+ empno
					+ "'  and Approver_Status='1'  and  YEAR(date)=YEAR(CURRENT_TIMESTAMP) order by date desc";

			ResultSet rs = ad.selectQuery(data);
			try {
				while (rs.next()) {
					PermissionForm bb = new PermissionForm();
					
					bb.setDate(rs.getString("date"));
					bb.setApproveDate(rs.getString("approved_date"));
				//	leaveForm0.setStartDate(rs.getString("fromdate"));
					//leaveForm0.setEndDate(rs.getString("enddate"));
					//leaveForm0.setNoOfDays(rs.getString("no_of_days"));
					bb.setReason(rs.getString("reason"));
					bb.setStartTime(rs.getString("startTime"));
					bb.setEndTime(rs.getString("endTime"));
					bb.setAvailbal(Integer.parseInt(rs.getString("avail_minutes")));
					//permissionForm.setDate(rs.getString("date"));

					report.add(bb);
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

			return mapping.findForward("permissionrecord");
		
	
	}
	
	public ActionForward displayAttendancejax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PermissionForm permissionForm =(PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 
		String datereq=request.getParameter("datepick");
		
		String yo[]=datereq.split("/");
		
		String reqmon="";
		String reqyear="";
		String reqstart="";
		String reqend="";
		String empcode="";
		String reqmonname="";
		String reqstart1="";
		String reqend1="";
		
		reqstart1=yo[2]+"-"+yo[1]+"-"+yo[0];
		reqend1=yo[2]+"-"+yo[1]+"-"+yo[0];
		reqmon=yo[1];
		reqyear=yo[2];
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
						+ "left(status,2) as instatus,right(status,2) as outstatus,shift from Report_Daily_wise,emp_official_info where emp_official_info.PERNR=Report_Daily_wise.Pernr and emp_official_info.LOCID=Report_Daily_wise.Location and  emp_official_info.Pernr='"+user.getEmployeeNo()+"'  and year(date)='"+reqyear+"' "
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
							
			            	help.setShift(Empshifttimings(rs9.getString("shift")));
						  String val[]=shiftexactimings(rs9.getString("shift")).split(",");
						  help.setShiftStart(val[0]);
						  help.setShiftEnd(val[1]);
						  
            	
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
				
							help.setShift(Empshifttimings(rs9.getString("shift")));
							  String val[]=shiftexactimings(rs9.getString("shift")).split(",");
							  help.setShiftStart(val[0]);
							  help.setShiftEnd(val[1]);      
				    	}
					   

				    	String data = "select Approvel_Status,reason from leave_details where  user_id='"
								+ empcode
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
						rs10.close();
						String data1 = "select Approver_Status,reason from OnDuty_details where  user_id='"
								+empcode
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

						rs101.close();
						



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
				
				
		}
			return mapping.findForward("permissionattListajax");
			
}
	
	public ActionForward displayMarkjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PermissionForm permissionForm =(PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 
	
		
		
		if(true)
		{
			//Attendance

			SAPAttendenceDAO dao = new SAPAttendenceDAO();

	



			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date datenow = new Date();
			String time = sdf.format(datenow);

			String today[]=time.split("-");

			int datetoday=Integer.parseInt(today[2]);
			String monthtoday=today[1];
			String monName=monthtoday;

			


			LinkedList attDataList = new LinkedList();
			Calendar cal = Calendar.getInstance();



			/*
			 * String reqyear=month.substring(month.length()-2, month.length());
			 * String reqmonth=monName+reqyear; AttendenceForm attendenceForm = new
			 * AttendenceForm(); attendenceForm.setEmpcode(empNo);
			 * attendenceForm.setDat(reqmonth); AttendenceAction a4=new
			 * AttendenceAction(); if(monName.equalsIgnoreCase("Nov")) {
			 * a4.submit(mapping, attendenceForm, request, response); return
			 * mapping.findForward("display"); }
			 */




	
			String date ="";	




			ArrayList att = new ArrayList();
			int year=Calendar.getInstance().get(Calendar.YEAR); 
			cal.set(year, Integer.parseInt(monName)-1, 1);
			int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

			int i=1;
			/*    String query="EXEC daily_report_status_Att '"+days+"','"+monName+"','"+year+"','"+empNo+"' ";
			int k=ad.SqlExecuteUpdate(query);*/

				String s9 = " select date,convert(varchar(11),date,103) as datereq,convert(varchar(11),date,106) as date1,left(DATENAME(WEEKDAY,date),3) as day,left(In_time,5) as intime,left(out_time,5) as outtime,"
						+ "left(status,2) as instatus,right(status,2) as outstatus,shift from Report_Daily_wise,emp_official_info where"
						+ " emp_official_info.PERNR=Report_Daily_wise.Pernr and emp_official_info.LOCID=Report_Daily_wise.Location and "
						+ " emp_official_info.Pernr='"+user.getEmployeeNo()+"'  and date>=dateadd(month,-1, DATEFROMPARTS(YEAR(getdate()),MONTH(getdate()),21)) and ( left(status,2)='AA' or right(status,2)='AA')   order by date";
				ResultSet rs9 = ad.selectQuery(s9);
				try {
					while(rs9.next())
					{
						
						SAPAttendenceForm help = new SAPAttendenceForm();
						help.setDate(rs9.getString("date1"));
						help.setPayGroup(rs9.getString("datereq"));
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
							case "0001": help.setShift("Gen(0001)");
							  break;
case "0002": help.setShift("Gen(0002)");
							  break;
case "0003": help.setShift("FS(0003)");
							  break;
case "0004": help.setShift("SS(0004)");
							  break;
case "0005": help.setShift("TS(0005)");
							  break;
case "0006": help.setShift("SS1(0006)");
							  break;
case "0007": help.setShift("FS(0007)");
							  break;
case "0008": help.setShift("SS0008)");
							  break;
case "0009": help.setShift("TS(0009)");
							  break;
case "0010": help.setShift("NS(0010)");
							  break;
case "0011": help.setShift("SS-SM(0011)");
							  break;
case "0012": help.setShift("EnggB(0012)");
							  break;
case "0013": help.setShift("EnggC(0013)");
							  break;
case "0014": help.setShift("SecA(0014)");
							  break;
case "0015": help.setShift("SecB(0015)");
							  break;
case "0016": help.setShift("SecC(0016)");
							  break;
case "0017": help.setShift("Gen(0017)");
							  break;
case "0018": help.setShift("Night(0018)");
							  break;
case "0019": help.setShift("Gen(0019)");
							  break;
case "0020": help.setShift("Gen2(0020)");
							  break;
case "0021": help.setShift("2ND(0021)");
							  break;
case "0022": help.setShift("2ND1(0022)");
							  break;
case "0023": help.setShift("EnggGen(0023)");
							  break;
case "0024": help.setShift("EnggGen1(0024)");
							  break;
case "0025": help.setShift("1ST(0025)");
							  break;
case "0026": help.setShift("Engg2ND(0026)");
							  break;
case "0027": help.setShift("3RD(0027)");
							  break;
case "0028": help.setShift("QC1ST(0028)");
							  break;
case "0030": help.setShift("Gen4(0030)");
							  break;
case "0031": help.setShift("1ST(0031)");
							  break;
case "0032": help.setShift("Gen(0032)");
							  break;
case "0033": help.setShift("2ND(0033)");
							  break;
case "0034": help.setShift("Night(0034)");
							  break;
case "0035": help.setShift("GOASecA(0035)");
							  break;
case "0036": help.setShift("GOASecB(0036)");
							  break;
case "0037": help.setShift("GOASecC(0037)");
							  break;
case "0038": help.setShift("Gen3(0038)");
							  break;
case "0029": help.setShift("QC2ND(0029)");
							  break;
case "0039": help.setShift("Gen1(0039)");
							  break;
case "0040": help.setShift("Gen2(0040)");
							  break;
case "0041": help.setShift("FS(0041)");
							  break;
case "0042": help.setShift("SS(0042)");
							  break;
case "0043": help.setShift("TS(0043)");
							  break;
case "0054": help.setShift("Gen(0054)");
							  break;
case "0055": help.setShift("1ST(0055)");
							  break;
case "0056": help.setShift("2ND(0056)");
							  break;
case "0057": help.setShift("Night(0057)");
							  break;
case "0058": help.setShift("Gen5(0058)");
							  break;
case "0059": help.setShift("Gen6(0059)");
							  break;
case "0060": help.setShift("Gen(0060)");
							  break;
case "0061": help.setShift("FS(0061)");
							  break;
case "0062": help.setShift("SS(0062)");
							  break;
case "0063": help.setShift("TS(0063)");
							  break;
case "0064": help.setShift("Night(0064)");
							  break;
case "0065": help.setShift("Gen(0065)");
							  break;
case "0066": help.setShift("SSQC(0066)");
							  break;
case "0067": help.setShift("FS(0067)");
							  break;
case "0069": help.setShift("Gen(0069)");
							  break;
case "0070": help.setShift("Gen2(0070)");
							  break;
case "0071": help.setShift("Gen3(0071)");
							  break;
case "0072": help.setShift("QC2ND1(0072)");
							  break;
case "0074": help.setShift("EnggA(0074)");
							  break;
case "0075": help.setShift("EnggD(0075)");
							  break;
case "0044": help.setShift("FS1(0044)");
							  break;
case "0045": help.setShift("FS2(0045)");
							  break;
case "0046": help.setShift("Gen1(0046)");
							  break;
case "0047": help.setShift("Gen2(0047)");
							  break;
case "0048": help.setShift("SS(0048)");
							  break;
case "0049": help.setShift("3RD(0049)");
							  break;
case "0050": help.setShift("Gen(0050)");
							  break;
case "0051": help.setShift("FS(0051)");
							  break;
case "0052": help.setShift("SS(0052)");
							  break;
case "0053": help.setShift("TS(0053)");
							  break;
case "0068": help.setShift("Gen(0068)");
							  break;
case "0073": help.setShift("QcNig(0073)");
							  break;
}
						


					
			                	
			            }
			            else
				    	{
				    		
			            	help.setiNSTATUS("");
							help.setoUTSTATUS("");	
			                  
				    	}
			            
			    	}
				    	
					   

				    	if((!rs9.getString("intime").equalsIgnoreCase("00:00") && rs9.getString("instatus").equalsIgnoreCase("AA") ||(!rs9.getString("outtime").equalsIgnoreCase("00:00") && rs9.getString("outstatus").equalsIgnoreCase("AA") )))
				    	{
					attDataList.add(help);
				    	}
				    	
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
				
				
		}
			return mapping.findForward("markattjax");
			
}
	
	public ActionForward displayPermissions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		PermissionForm permissionForm =(PermissionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		  int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		   int year=0;
					if(request.getParameter("year")==null || request.getParameter("year").equalsIgnoreCase(""))
					{
						year=Calendar.getInstance().get(Calendar.YEAR);
					}
					else
					{
						 year = Integer.parseInt(request.getParameter("year"));
					}

					permissionForm.setYear(Integer.toString(year));
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
		  try{
			  String getTotalRecords="select count(*) from Permission_details where user_id='"+user.getEmployeeNo()+"'  and year(date)='"+year+"' ";
			  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
			  while(rsTotalRecods.next())
			  {
				  totalRecords=rsTotalRecods.getInt(1);
			  }
		   if(totalRecords>10)
		  {

				  permissionForm.setTotalRecords(totalRecords);
				  startRecord=1;
				  endRecord=10;
				  permissionForm.setStartRecord(1);
				  permissionForm.setEndRecord(10);
				  request.setAttribute("displayRecordNo", "displayRecordNo");
				  request.setAttribute("nextButton", "nextButton");
				  }else
				  {
					  startRecord=1;
					  endRecord=totalRecords;
					  permissionForm.setTotalRecords(totalRecords);
					  permissionForm.setStartRecord(1);
					  permissionForm.setEndRecord(totalRecords); 


		  }			
		   ArrayList permissionList=new ArrayList();
		

					permissionForm.setYearList(yearList);
		   PermissionForm permissionForm2 = null;
		   String sql = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no desc) AS  RowNum,request_no,date,startTime," +
		   		"endTime,Approver_Status,type,attendance_status,CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),"
		   		+ "Created_date,108) as Created_date1 from Permission_details where user_id='"+user.getEmployeeNo()+"'  and year(date)='"+year+"' ) as sub Where  sub.RowNum between 1 and 10";
		   ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				permissionForm2 = new PermissionForm();
				if(rs.getString("attendance_status").equalsIgnoreCase("0"))
					permissionForm2.setAttendanceStatus("Not Considered as the punch time already available in Biometric device");
				else
					permissionForm2.setAttendanceStatus("Considered");

				permissionForm2.setRequestNumber(rs.getInt("request_no"));
				permissionForm2.setReqdate(rs.getString("Created_date1"));
				permissionForm2.setStartTime(rs.getString("startTime"));
				permissionForm2.setEndTime(rs.getString("endTime"));
				permissionForm2.setType(rs.getString("type"));

				String date = rs.getString("date");
				String a[]=date.split(" ");
				date=a[0];
				String b[]=date.split("-");
				date=b[2]+"/"+b[1]+"/"+b[0];

				permissionForm2.setDate(date);
				int OnDuty_status=rs.getInt("Approver_Status");
				//String OnDuty_status =rs.getString("Approver_Status");

				String status="";
				if(OnDuty_status==0)
				{
					status="Pending";
				}
				if(OnDuty_status==1)
				{
					status="Approved";
				}
				if(OnDuty_status==2)
				{
					status="Rejected";
				}
				if(OnDuty_status==3)
				{
					status="Cancelled";
				}
				if(OnDuty_status==4)
				{
					status="Self Cancelled";
				}
				permissionForm2.setApproverStatus(status);

				permissionList.add(permissionForm2);

			}
			request.setAttribute("permissionList", permissionList);
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			if(permissionList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				permissionForm.setMessage("No records are found");
			}

	}catch (Exception e) {
	e.printStackTrace();

	}


			return mapping.findForward("permissionList");
		
	
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
	
		return tableName;
	}

}
	
	


