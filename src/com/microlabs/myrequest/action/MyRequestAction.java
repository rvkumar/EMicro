package com.microlabs.myrequest.action;
					
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.action.Permission;
import com.microlabs.ess.dao.ConferenceMail;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.VCMail;
import com.microlabs.ess.form.ConferenceForm;
import com.microlabs.ess.form.CustomerMasterForm;
import com.microlabs.ess.form.FinishedProductForm;
import com.microlabs.ess.form.GeneralMaterialForm;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.MaterialCodeExtensionForm;
import com.microlabs.ess.form.OnDutyForm;
import com.microlabs.ess.form.PackageMaterialMasterForm;
import com.microlabs.ess.form.PermissionForm;
import com.microlabs.ess.form.PromotionalForm;
import com.microlabs.ess.form.RawMaterialForm;
import com.microlabs.ess.form.SemiFinishedForm;
import com.microlabs.ess.form.ServiceMasterRequestForm;
import com.microlabs.ess.form.VCForm;
import com.microlabs.ess.form.VendorMasterRequestForm;
import com.microlabs.ess.form.ZPSRForm;
import com.microlabs.it.form.HelpDeskForm;
import com.microlabs.it.form.IssuesForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.myrequest.dao.MyRequestDAO;
import com.microlabs.myrequest.form.MyRequestForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;
					
public class MyRequestAction extends DispatchAction {
	
	public LinkedList viewOnduty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String HRid="";
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList approverList=new LinkedList();
	
		
		int reqId=Integer.parseInt(request.getParameter("requestNo"));
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
	e.printStackTrace();
}
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
				 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
				 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
				String getdetails=" select all_R.Comments,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.approved_date," +
						"all_R.rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
						" where all_R.Req_Id='"+reqId+"' and all_R.Req_Type='On Duty' and ((emp.PERNR=all_R.Last_Approver) or " +
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
				approverList.add(lvform);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		request.setAttribute("ondutyview", approverList);
		
	}
	if(reqType.equalsIgnoreCase("parallel"))
	{
		
		String getApproverDesig="  select all_R.Comments,desg.DSGSTXT,all_R.Hr_Id,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date,all_R.rejected_date from " +
				"All_Request as all_R,emp_official_info as emp, DESIGNATION as desg    where all_R.Req_Id='"+reqId+"' and all_R.Req_Type='On Duty' and " +
				"((emp.PERNR=all_R.Last_Approver) or (emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) ";
		    ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
			try {
				while(rsApproverDsg.next())
				{
					
					HRid=rsApproverDsg.getString("Hr_Id");
					LeaveForm lvform=new LeaveForm();
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
					
					
					approverList.add(lvform);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	request.setAttribute("ondutyview", approverList);
}
if(existStatus==false){

String getRepMgrDesig="select dept.DPTSTXT,desg.DSGSTXT,emp.EMP_FULLNAME from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where " +
" emp.PERNR='"+approverID+"' and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID";
ResultSet rsRepMgrDesig=ad.selectQuery(getRepMgrDesig);
try {
	LeaveForm leaveForm2=null;
	if(rsRepMgrDesig.next())
	{
	 leaveForm2=new LeaveForm();
	leaveForm2.setApptype("Approver Manager");
	leaveForm2.setApproverID(approverID);
	reportMgrDesg=rsRepMgrDesig.getString("DSGSTXT");
	reportingMgrName=rsRepMgrDesig.getString("EMP_FULLNAME");
	leaveForm2.setAppDesig(reportMgrDesg);
	leaveForm2.setApproverName(reportingMgrName);
	}
	String getApproveTimings="select * from OnDuty_details where request_no='"+reqId+"' and user_id='"+user.getEmployeeNo()+"'";
	ResultSet rsTimings=ad.selectQuery(getApproveTimings);
	if(rsTimings.next())
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
request.setAttribute("appList", approverList);

}


		
		return approverList;
	}
	public ActionForward cancelVCRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MyRequestForm myReqForm = (MyRequestForm) form;
			String reqno=request.getParameter("requestNo");
			HttpSession session=request.getSession();
			 UserInfo user=(UserInfo)session.getAttribute("user");
			 EssDao ad=new EssDao();
			String empno=user.getEmployeeNo();
			LinkedList confDetails=new LinkedList();
			VCForm conf=new VCForm();
			myReqForm.setRequestNo(reqno);
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
			 String dateNow = ft.format(dNow);
		String update="update VCRoom_Details set approval_status='Self Cancelled',approved_date='"+dateNow+"' where Reqest_No='"+reqno+"'";
		ad.SqlExecuteUpdate(update);
		String update1 = "update All_Request set Req_Status='Self Cancelled',approved_date='"
				+ dateNow + "' where Req_Id='" + reqno + "'  and Req_Type='VC Booking'";
		ad.SqlExecuteUpdate(update1);
	    myReqForm.setMessage("Request has been cancelled Successfully");
	    //get Pending Approver
	    String pendingApprover="";
	    String getApprover="select Pending_Approver from All_Request where Req_Id='"+reqno+"' and Req_Type='VC Booking' and Req_Status='Pending'";
	    ResultSet rsApp=ad.selectQuery(getApprover);
	    try{
	    while(rsApp.next()){
	    	pendingApprover=rsApp.getString("Pending_Approver");
	    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    if(!pendingApprover.equals("")){
	    VCMail mail=new VCMail();
		mail.sendMailToApprover(request, Integer.parseInt(reqno), user.getEmployeeNo(), pendingApprover,"Self Cancelled");
	    }
			String getRequestDetails="select conf.Requster_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,conf.Ext_No,conf.Email_ID,conf.IP_Phone,"
					+ "conf.IPAddress,loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,"
					+ "conf.Purpose,conf.approval_status,conf.approved_date from VCRoom_Details conf,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where "
					+ "conf.Reqest_No='"+reqno+"' and conf.Requster_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
					ResultSet rs=ad.selectQuery(getRequestDetails);
					try{
					while(rs.next()){
						VCForm c=new VCForm();
						c.setReqNo(rs.getString("Requster_Id"));
						c.setEmpId(rs.getString("Requster_Id"));
						c.setEmpName(rs.getString("EMP_FULLNAME"));
						c.setDept(rs.getString("DPTSTXT"));
						c.setDesg(rs.getString("DSGSTXT"));
						c.setExtno(rs.getString("Ext_No"));
						c.setEmpEmailID(rs.getString("Email_ID"));
						c.setIpPhoneno(rs.getString("IP_Phone"));
						c.setIPNumber(rs.getString("IPAddress"));
						c.setLocation(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
						c.setFloor(rs.getString("Floor"));
						c.setRoomName(rs.getString("Conf_Room"));
						c.setFromDate(EMicroUtils.display(rs.getDate("From_Date")));
						c.setFromTime(rs.getString("From_Time"));
						c.setToDate(EMicroUtils.display(rs.getDate("To_Date")));
						c.setToTime(rs.getString("To_Time"));
						c.setPurpose(rs.getString("Purpose"));
						c.setStatus(rs.getString("approval_status"));
						String approvalStatus=rs.getString("approval_status");
						if(approvalStatus.equals("Pending")||approvalStatus.equals("Approved"))
							request.setAttribute("cancelRequest", "cancelRequest");
						c.setApprovedDate("");
						if(!approvalStatus.equals("Pending"))
							c.setApprovedDate(rs.getString("approved_date"));
						
						confDetails.add(c);
					}
					request.setAttribute("confList", confDetails);
					}catch(Exception e){
						e.printStackTrace();
					}
					//Approvers Details
					List appList=new LinkedList();
					String getApproverDetails="select appr.Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments,all_r.Approved_Persons,all_r.Pending_Approver from VCRoom_Details conf,VCRoom_Approvers appr ,"
							+ "All_Request all_r,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where conf.Reqest_No="+reqno+" and conf.Loc_Id=appr.LocID "
							+ "and conf.Floor=appr.Floor and conf.Conf_Room=appr.Room and all_r.Req_Id=conf.Reqest_No and all_r.Req_Type='VC Booking' and  "
							+ "(appr.Approver_Id=all_r.Last_Approver or appr.Approver_Id=all_r.Pending_Approver) and  (all_r.Pending_Approver=emp.PERNR or "
							+ "all_r.Last_Approver =emp.PERNR) and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
				ResultSet rsAppr=ad.selectQuery(getApproverDetails);
				try{
					while(rsAppr.next()){
					ApprovalsForm appr=new ApprovalsForm();
					appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
					appr.setDesignation(rsAppr.getString("DPTSTXT"));
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					String approvaStatus=rsAppr.getString("Req_Status");
					if(approvaStatus.equals("Pending")){
						appr.setApproveDate("");
						appr.setComments("");
					}
					if(approvaStatus.equals("Approved")||approvaStatus.equals("Completed")){
						appr.setApproveDate(rsAppr.getString("approved_date"));
						appr.setComments(rsAppr.getString("Comments"));
					}
					if(approvaStatus.equals("Rejected")||approvaStatus.equals("Cancelled")){
						appr.setApproveDate(rsAppr.getString("rejected_date"));
						appr.setComments(rsAppr.getString("Comments"));
					}
					appList.add(appr);
					}
					request.setAttribute("appList",appList);
					ad.connClose();
				}catch(Exception e){
					e.printStackTrace();
				}
		
			return mapping.findForward("VCRoom");
			}
	public ActionForward cancelConferenceRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MyRequestForm myReqForm = (MyRequestForm) form;
		String reqno=request.getParameter("requestNo");
		HttpSession session=request.getSession();
		 UserInfo user=(UserInfo)session.getAttribute("user");
		 EssDao ad=new EssDao();
		String empno=user.getEmployeeNo();
		LinkedList confDetails=new LinkedList();
		ConferenceForm conf=new ConferenceForm();
		myReqForm.setRequestNo(reqno);
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		 String dateNow = ft.format(dNow);
	String update="update ConferenceRoom_Details set approval_status='Self Cancelled',approved_date='"+dateNow+"' where Reqest_No='"+reqno+"'";
	ad.SqlExecuteUpdate(update);
    myReqForm.setMessage("Request has been cancelled Successfully");
    //get Pending Approver
    String pendingApprover="";
    String getApprover="select Pending_Approver from All_Request where Req_Id='"+reqno+"' and Req_Type='Conference' and Req_Status='Pending'";
    ResultSet rsApp=ad.selectQuery(getApprover);
    try{
    while(rsApp.next()){
    	pendingApprover=rsApp.getString("Pending_Approver");
    }
    }catch(Exception e){
    	e.printStackTrace();
    }
    if(!pendingApprover.equals("")){
    ConferenceMail mail=new ConferenceMail();
	mail.sendMailToApprover(request, Integer.parseInt(reqno), user.getEmployeeNo(), pendingApprover,"Self Cancelled");
    }
		String getRequestDetails="select conf.Requster_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,conf.Ext_No,conf.Email_ID,conf.IP_Phone,"
				+ "conf.IPAddress,loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,"
				+ "conf.Purpose,conf.approval_status,conf.approved_date from ConferenceRoom_Details conf,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where "
				+ "conf.Reqest_No='"+reqno+"' and conf.Requster_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
				ResultSet rs=ad.selectQuery(getRequestDetails);
				try{
				while(rs.next()){
					ConferenceForm c=new ConferenceForm();
					c.setReqNo(rs.getString("Requster_Id"));
					c.setEmpId(rs.getString("Requster_Id"));
					c.setEmpName(rs.getString("EMP_FULLNAME"));
					c.setDept(rs.getString("DPTSTXT"));
					c.setDesg(rs.getString("DSGSTXT"));
					c.setExtno(rs.getString("Ext_No"));
					c.setEmpEmailID(rs.getString("Email_ID"));
					c.setIpPhoneno(rs.getString("IP_Phone"));
					c.setIPNumber(rs.getString("IPAddress"));
					c.setLocation(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
					c.setFloor(rs.getString("Floor"));
					c.setRoomName(rs.getString("Conf_Room"));
					c.setFromDate(EMicroUtils.display(rs.getDate("From_Date")));
					c.setFromTime(rs.getString("From_Time"));
					c.setToDate(EMicroUtils.display(rs.getDate("To_Date")));
					c.setToTime(rs.getString("To_Time"));
					c.setPurpose(rs.getString("Purpose"));
					c.setStatus(rs.getString("approval_status"));
					String approvalStatus=rs.getString("approval_status");
					if(approvalStatus.equals("Pending")||approvalStatus.equals("Approved"))
						request.setAttribute("cancelRequest", "cancelRequest");
					c.setApprovedDate("");
					if(!approvalStatus.equals("Pending"))
						c.setApprovedDate(rs.getString("approved_date"));
					
					confDetails.add(c);
				}
				request.setAttribute("confList", confDetails);
				}catch(Exception e){
					e.printStackTrace();
				}
				//Approvers Details
				List appList=new LinkedList();
				String getApproverDetails="select appr.Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
						+ "all_r.Comments,all_r.Approved_Persons,all_r.Pending_Approver from ConferenceRoom_Details conf,ConferanceRoom_Approvers appr ,"
						+ "All_Request all_r,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where conf.Reqest_No="+reqno+" and conf.Loc_Id=appr.LocID "
						+ "and conf.Floor=appr.Floor and conf.Conf_Room=appr.Room and all_r.Req_Id=conf.Reqest_No and all_r.Req_Type='Conference' and  "
						+ "(appr.Approver_Id=all_r.Last_Approver or appr.Approver_Id=all_r.Pending_Approver) and  (all_r.Pending_Approver=emp.PERNR or "
						+ "all_r.Last_Approver =emp.PERNR) and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
			ResultSet rsAppr=ad.selectQuery(getApproverDetails);
			try{
				while(rsAppr.next()){
				ApprovalsForm appr=new ApprovalsForm();
				appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
				appr.setDesignation(rsAppr.getString("DPTSTXT"));
				appr.setApproveStatus(rsAppr.getString("Req_Status"));
				String approvaStatus=rsAppr.getString("Req_Status");
				if(approvaStatus.equals("Pending")){
					appr.setApproveDate("");
					appr.setComments("");
				}
				if(approvaStatus.equals("Approved")||approvaStatus.equals("Completed")){
					appr.setApproveDate(rsAppr.getString("approved_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				if(approvaStatus.equals("Rejected")||approvaStatus.equals("Cancelled")){
					appr.setApproveDate(rsAppr.getString("rejected_date"));
					appr.setComments(rsAppr.getString("Comments"));
				}
				appList.add(appr);
				}
				request.setAttribute("appList",appList);
				ad.connClose();
			}catch(Exception e){
				e.printStackTrace();
			}
			return mapping.findForward("Conference");
	
	}
	
	
	public ActionForward editMyRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MyRequestForm myReqForm = (MyRequestForm) form;
		String cate="";
		String filepath2="";
		String chkdate1="";
		String chkdate="";
		String HRid="";
		ArrayList listOfMyRequest=new ArrayList();
		MyRequestDAO ad=new MyRequestDAO();
		String forwardType="";
		String materialType="";
		String requstNo=request.getParameter("requstNo");
		String reqno=request.getParameter("requstNo");
		String type=request.getParameter("type");
		 HttpSession session=request.getSession();
		 UserInfo user=(UserInfo)session.getAttribute("user");
			HelpDeskForm help = new  HelpDeskForm();
			String localhostname="";
			String ipaddress="";
			String username="";
			try{
				
				
				if (type.equalsIgnoreCase("Sap issues"))
				{  LinkedList empList=new LinkedList();

				String empno="";
				
				String data00=" select * from IT_SAP_ISSUES where Req_No="+reqno+"";	
				ResultSet rs00= ad.selectQuery(data00);
				while(rs00.next())
				{
				if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
				{
					empno=rs00.getString("Emp_No");
				}
				else
				{
					empno=user.getEmployeeNo();
				}
				}
				
				String data="select Emp.EMP_FULLNAME ,Emp.PERNR,dep.DPTSTXT,des.DSGSTXT,emp.IP_PHONE,emp.TEL_EXTENS,emp.IP_PHONE,IT.Req_No,"
			            +" It.read_Status,It.Req_Priority,It.mode,It.Req_type,It.Sub_Category,It.AssignTo,loc.LOCATION_CODE,It.IPAddress,"
					    +" IT.Req_Status,It.Subject,It.Reason,it.sap_request_type,it.ROLE,IT.old_id,IT.NEW_id"
			            +" from IT_SAP_ISSUES It,emp_official_info emp ,DEPARTMENT as dep ,Location as loc ,"
			          +"DESIGNATION as des"
			           +" where IT.Emp_No =emp.PERNR and dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID and"
			          +" IT.Req_No = '"+reqno+"'";
				ResultSet rs= ad.selectQuery(data);
				try {
					while(rs.next())
					{   myReqForm.setEmpno(rs.getString("PERNR"));
				//	System.out.println(myReqForm.getEmpno());
					myReqForm.setRequestername(rs.getString("EMP_FULLNAME"));
					myReqForm.setRequesterdepartment(rs.getString("DPTSTXT"));
					myReqForm.setRequesterdesignation(rs.getString("DSGSTXT"));
					myReqForm.setExtno(rs.getString("TEL_EXTENS"));
					myReqForm.setIpPhoneno(rs.getString("IP_PHONE"));
					myReqForm.setLocation(rs.getString("LOCATION_CODE"));
					myReqForm.setIPNumber(rs.getString("IPAddress"));
					myReqForm.setRequestNo(reqno);
					myReqForm.setRequestStatus(rs.getString("req_status"));
					myReqForm.setPriority(rs.getString("Req_Priority"));
					myReqForm.setMode(rs.getString("mode"));
					myReqForm.setCategory(rs.getString("Req_type"));
					myReqForm.setCategory1(rs.getString("Req_type"));						
					myReqForm.setSubcategory(rs.getString("Sub_Category"));
					myReqForm.setSubcategory1(rs.getString("Sub_Category"));
					myReqForm.setTechniciansID(rs.getString("AssignTo"));
					myReqForm.setReason(rs.getString("reason"));
					myReqForm.setSubject(rs.getString("subject"));
					myReqForm.setRequestName(rs.getString("sap_request_type"));
					if(rs.getString("sap_request_type")!=null)
					{
						if(!rs.getString("sap_request_type").equalsIgnoreCase("Document Cancellation"))
						{
							String rol="select rol.role as role_manage from IT_SAP_ISSUES as sap,Role_SAP as rol where sap.ROLE=rol.id and sap.location=rol.location_id and sap.role='"+rs.getString("ROLE")+"' and  sap.Req_No='"+reqno+"'";
								
							 ResultSet role1=ad.selectQuery(rol);
								while(role1.next())
								{
									myReqForm.setRoleId(role1.getString("role_manage"));
								}
						}
						
					}
					
					if(rs.getString("Sub_Category")!=null)
					{
						if(rs.getString("Sub_Category").equalsIgnoreCase("Rename ID"))	
						{
							request.setAttribute("renameId", "renameId");
							myReqForm.setOldId(rs.getString("old_id"));
							myReqForm.setNewId(rs.getString("new_id"));
						}
						
					}
					if(rs.getString("sap_request_type")!=null)
					{
						if(rs.getString("sap_request_type").equalsIgnoreCase("SAP Roles Management"))	
						{
							cate="'ROLES'";
							request.setAttribute("role", "role");
						//	pendAppForm.setOldId(rsLeave.getString("old_id"));
						//	pendAppForm.setNewId(rsLeave.getString("new_id"));
						}
						if(rs.getString("sap_request_type").equalsIgnoreCase("SAP Authorization Management"))	
						{
							 cate="'AUTH'";
							request.setAttribute("role", "role");
						//	pendAppForm.setOldId(rsLeave.getString("old_id"));
						//	pendAppForm.setNewId(rsLeave.getString("new_id"));
						}
						if(rs.getString("sap_request_type").equalsIgnoreCase("SAP User Id Management"))	
						{
							 
							 cate="'PSAP','SSAP'";
							 request.setAttribute("role", "role");
						//	pendAppForm.setOldId(rsLeave.getString("old_id"));
						//	pendAppForm.setNewId(rsLeave.getString("new_id"));
						}
						if(rs.getString("sap_request_type").equalsIgnoreCase("Document Cancellation"))	
						{
							 
							 cate="'DC'";
							// request.setAttribute("role", "role");
						//	pendAppForm.setOldId(rsLeave.getString("old_id"));
						//	pendAppForm.setNewId(rsLeave.getString("new_id"));
						}
						}
					empList.add(myReqForm);
							}
				} catch (SQLException e) {
				  e.printStackTrace();
				}
				request.setAttribute("empList", empList);
				String getReqestNumber="select MAX(Priority) as priority"
		 		    	+" from Material_Approvers app,"
						+"emp_official_info as emp"
					+" where Material_Type='"+myReqForm.getCategory()+"' and " 
					+"Material_Sub_Category='"+myReqForm.getSubcategory()+"' and "    
					+"app.LOCATION='"+myReqForm.getLocation()+"'";
			int maxPriority=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			try {
				if(rsReqestNumber.next())
				{
					maxPriority=rsReqestNumber.getInt(1);
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			//maxPriority+=1;
			
			
			int hodreq =0;
			
			String chkreq=" select c_hod_req from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and c_cat_name = '"+myReqForm.getCategory()+"' and c_sub_cat_name ='"+myReqForm.getSubcategory()+"'";
			 ResultSet chk= ad.selectQuery(chkreq);
			 try {
				while(chk.next())
				 {
					 hodreq=chk.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
			 String Sqlstring ="";
			 
			if(hodreq ==1)
			{	
			
			 Sqlstring ="select 0 as priority ,"  
			         +"PERNR as employeeCode ," 
			         +"EMP_FULLNAME as employeeName ,"
			         +"DEPARTMENT.DPTLTXT as department,"
			         +"DESIGNATION.DSGLTXT as designation,"
			         +"All_Request.Req_Status ,"
			         +"All_Request.Comments ,"
			         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
			         +" from  DEPARTMENT,DESIGNATION ,ESS_Approvers ,emp_official_info "
					  +"left outer join All_Request on Req_type ='IT Sap Request' and "
		             +"Req_Id = '"+reqno+"' and "
		             +"emp_official_info.PERNR = All_Request.Last_Approver"
			         +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
			         +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
			         +"emp_official_info.PERNR =  ESS_Approvers.ApproverId and "
			        +"ESS_Approvers.employeeNumber = '"+myReqForm.getEmpno()+"'"
			        +" union " 
		           +"select app.priority as priority,"
				    +"PERNR as employeeCode ," 
					+"EMP_FULLNAME as employeeName ,"
				    +"DEPARTMENT.DPTLTXT as department,"
					+"DESIGNATION.DSGLTXT as designation ,"
					+"All_Request.Req_Status ,"
			         +"All_Request.Comments ,"
			         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
					+" from Material_Approvers app,"
						+" DEPARTMENT,DESIGNATION ,emp_official_info as emp  left outer join All_Request on Req_type ='IT Sap Request' and "
					 +"Req_Id = '"+reqno+"' and "
		            +"emp.PERNR = All_Request.Last_Approver "
						+" where Material_Type='"+myReqForm.getCategory()+"' and " 
					+"Material_Sub_Category='"+myReqForm.getSubcategory()+"' and "  
					+"emp.PERNR=app.Approver_Id and " 
					+"app.LOCATION='"+myReqForm.getLocation()+"' and role ='USER' and "
					+"emp.DPTID  = DEPARTMENT.DPTID and "
					+"emp.DSGID  = DESIGNATION.DSGID" 
					+" Union "
					+"select '"+maxPriority+"' as priority ," 
			         +"PERNR as employeeCode ," 
			         +"EMP_FULLNAME as employeeName ,"
			         +"DEPARTMENT.DPTLTXT as department,"
			         +"DESIGNATION.DSGLTXT as designation ,"
			         +"All_Request.Req_Status ,"
			         +"All_Request.Comments ,"
			         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
			         +" from DEPARTMENT,DESIGNATION ,emp_official_info "
					 +"Left Outer Join  All_Request on Req_type ='IT Sap Request' and" 
		                      +" Req_Id = '"+reqno+"' and" 
		                      +" emp_official_info.PERNR = All_Request.Last_Approver"   

			        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
			        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
			        +"emp_official_info.PERNR = '"+myReqForm.getTechniciansID()+"'" 
					+" Order by 1";
			}
			else
			{
				Sqlstring=  "select app.priority as priority,"
			    		    +"PERNR as employeeCode ," 
			    			+"EMP_FULLNAME as employeeName ,"
			    		    +"DEPARTMENT.DPTLTXT as department,"
			    			+"DESIGNATION.DSGLTXT as designation ,"
			    			+"All_Request.Req_Status ,"
					         +"All_Request.Comments ,"
					         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
							+" from Material_Approvers app,"
			 				+" DEPARTMENT,DESIGNATION ,emp_official_info as emp  left outer join All_Request on Req_type ='IT Sap Request' and "
							 +"Req_Id = '"+reqno+"' and "
			                +"emp.PERNR = All_Request.Last_Approver "
								+" where Material_Type='"+myReqForm.getCategory()+"' and " 
			   			+"Material_Sub_Category='"+myReqForm.getSubcategory()+"' and "  
			   			+"emp.PERNR=app.Approver_Id and " 
							+"app.LOCATION='"+myReqForm.getLocation()+"' and role ='USER' and "
							+"emp.DPTID  = DEPARTMENT.DPTID and "
			   			+"emp.DSGID  = DESIGNATION.DSGID" 
							+" Union "
							+"select '"+maxPriority+"' as priority ," 
					         +"PERNR as employeeCode ," 
					         +"EMP_FULLNAME as employeeName ,"
					         +"DEPARTMENT.DPTLTXT as department,"
					         +"DESIGNATION.DSGLTXT as designation ,"
					         +"All_Request.Req_Status ,"
					         +"All_Request.Comments ,"
					         +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
					         +" from DEPARTMENT,DESIGNATION ,emp_official_info "
							 +"Left Outer Join  All_Request on Req_type ='IT Sap Request' and" 
			                          +" Req_Id = '"+reqno+"' and" 
			                          +" emp_official_info.PERNR = All_Request.Last_Approver"   
			  
					        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
					        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
					        +"emp_official_info.PERNR = '"+myReqForm.getTechniciansID()+"'" 
			   			+" Order by 1";
			}
			
		    ResultSet rs10=ad.selectQuery(Sqlstring);
		    int i =0;
		    LinkedList apprList=new LinkedList();
			
			try{
				while(rs10.next()){
					//IssuesForm form1=new IssuesForm();
					MyRequestForm form1 = new MyRequestForm();
					i = i+1;
					//form1.setPriority(rs.getInt("priority"));
					form1.setPriority(Integer.toString(i));
					//pendAppForm.setEmployeeCode(rs.getString("employeeCode"));
					form1.setEmployeeCode(rs10.getString("employeeCode"));
					form1.setEmployeeName(rs10.getString("employeeName"));
					
					//form1.setEmployeename(rs.getString("employeeName"));
					//pendAppForm.setDepartment(rs.getString("department"));
					form1.setDepartment(rs10.getString("department"));
					//form1.setDesignation(rs.getString("designation"));
					form1.setDesignation(rs10.getString("designation"));
					form1.setStatus(rs10.getString("req_status"));
					form1.setComments(rs10.getString("Comments"));
					form1.setDate(rs10.getString("date"));
					
					apprList.add(form1);	
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			request.setAttribute("apprList", apprList);
			//file upload list

			 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	 try {
				props.load(in);
			
			 in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
		 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/SapIssues/UploadFiles");
		 	String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/SapIssues/UploadFiles";
		 	 filepath2="/EMicro Files/IT/Help Desk/SapIssues/UploadFiles/"+cate+"";
			String sql1="select * from Issues_document where request_no='"+reqno+"' and user_id='"+empno+"'";
			ResultSet rs1=ad.selectQuery(sql1);
			IssuesForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				try {
					while(rs1.next()) {
						leaveForm1=new IssuesForm();
						String start = rs1.getString("file_name").substring(rs1.getString("file_name").indexOf("_")+1, rs1.getString("file_name").length()-1);
						leaveForm1.setFileName(start);
						leaveForm1.setId(rs1.getString("id"));
						leaveForm1.setFileFullPath(filepath2+"/"+rs1.getString("file_name"));
						a1.add(leaveForm1);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("requesterDetails", a1);
			//----
			
				forwardType="viewNewSapRequestform";
			
			
		}
				
				
		if(type.equalsIgnoreCase("VC Room"))
		{
			String empno = user.getEmployeeNo();
			LinkedList confDetails = new LinkedList();
			ConferenceForm conf = new ConferenceForm();
			myReqForm.setRequestNo(requstNo);

			String getRequestDetails = "select case when to_date>=getdate() then 1 else 0 end as tag,conf.Requster_Id,conf.foruse,emp.EMP_FULLNAME,emp1.EMP_FULLNAME as forusage,dept.DPTSTXT,"
					+ "desg.DSGSTXT,conf.Ext_No,conf.Email_ID,conf.IP_Phone,conf.IPAddress,loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,"
					+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,(select LOCATION_CODE+'-'+LOCNAME from Location where LOCID = conf.vc_from) as vc_from,(select LOCATION_CODE+'-'+LOCNAME from Location where LOCID = conf.vc_to) as vc_to,conf.Purpose,"
					+ "conf.approval_status,conf.approved_date from VCRoom_Details conf,emp_official_info emp,emp_official_info emp1,"
					+ "DEPARTMENT dept,DESIGNATION desg ,Location loc where conf.Reqest_No='"
					+ requstNo
					+ "' and conf.Requster_Id=emp.PERNR and"
					+ " emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id  and emp1.PERNR=conf.foruse";
			ResultSet rs = ad.selectQuery(getRequestDetails);
			try {
				while (rs.next()) {
					VCForm c = new VCForm();
					c.setReqNo(rs.getString("Requster_Id"));
					c.setEmpId(rs.getString("Requster_Id"));
					c.setEmpName(rs.getString("EMP_FULLNAME"));
					c.setUsage(rs.getString("foruse") + "-"
							+ rs.getString("forusage"));
					c.setDept(rs.getString("DPTSTXT"));
					c.setDesg(rs.getString("DSGSTXT"));
					c.setExtno(rs.getString("Ext_No"));
					c.setEmpEmailID(rs.getString("Email_ID"));
					c.setIpPhoneno(rs.getString("IP_Phone"));
					c.setIPNumber(rs.getString("IPAddress"));
					c.setLocation(rs.getString("LOCATION_CODE") + "-"
							+ rs.getString("LOCNAME"));
					c.setFloor(rs.getString("Floor"));
					c.setRoomName(rs.getString("Conf_Room"));
					c.setFromDate(EMicroUtils.display(rs
							.getDate("From_Date")));
					c.setFromTime(rs.getString("From_Time"));
					c.setToDate(EMicroUtils.display(rs.getDate("To_Date")));
					c.setToTime(rs.getString("To_Time"));
					c.setPurpose(rs.getString("Purpose"));
					c.setStatus(rs.getString("approval_status"));
					c.setVcFrom(rs.getString("vc_from"));
					c.setVcTo(rs.getString("vc_to"));
					String approvalStatus = rs.getString("approval_status");
					if (approvalStatus.equals("Pending")|| approvalStatus.equals("Approved"))
					{
						if(rs.getString("tag").equalsIgnoreCase("1"))
						request.setAttribute("cancelRequest","cancelRequest");
					}
					c.setApprovedDate("");
					if (!approvalStatus.equals("Pending"))
						c.setApprovedDate(rs.getString("approved_date"));
					confDetails.add(c);
				}
				request.setAttribute("confList", confDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Approvers Details
			List appList = new LinkedList();
			String getApproverDetails = "select appr.Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
					+ "all_r.Comments,all_r.Approved_Persons,all_r.Pending_Approver from VCRoom_Details conf,VCRoom_Approvers appr ,"
					+ "All_Request all_r,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where conf.Reqest_No="
					+ requstNo
					+ " and conf.Loc_Id=appr.LocID "
					+ "and conf.Floor=appr.Floor and conf.Conf_Room=appr.Room and all_r.Req_Id=conf.Reqest_No and all_r.Req_Type='VC Booking' and  "
					+ "(appr.Approver_Id=all_r.Last_Approver or appr.Approver_Id=all_r.Pending_Approver) and  (all_r.Pending_Approver=emp.PERNR or "
					+ "all_r.Last_Approver =emp.PERNR) and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
			ResultSet rsAppr = ad.selectQuery(getApproverDetails);
			try {
				while (rsAppr.next()) {
					ApprovalsForm appr = new ApprovalsForm();
					appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
					appr.setDesignation(rsAppr.getString("DPTSTXT"));
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					String approvaStatus = rsAppr.getString("Req_Status");
					if (approvaStatus.equals("Pending")) {
						appr.setApproveDate("");
						appr.setComments("");
					}
					if (approvaStatus.equals("Approved")
							|| approvaStatus.equals("Completed")) {
						appr.setApproveDate(rsAppr
								.getString("approved_date"));
						appr.setComments(rsAppr.getString("Comments"));
					}
					if (approvaStatus.equals("Rejected")
							|| approvaStatus.equals("Cancelled")) {
						appr.setApproveDate(rsAppr
								.getString("rejected_date"));
						appr.setComments(rsAppr.getString("Comments"));
					}
					appList.add(appr);
				}
				request.setAttribute("appList", appList);
				ad.connClose();
			} catch (Exception e) {
				e.printStackTrace();
			}
			forwardType = "VCRoom";

		}
		if(type.equalsIgnoreCase("Conference"))
		{
			String empno=user.getEmployeeNo();
			LinkedList confDetails=new LinkedList();
			ConferenceForm conf=new ConferenceForm();
			myReqForm.setRequestNo(requstNo);
			
			String getRequestDetails="select conf.Requster_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,conf.Ext_No,conf.Email_ID,conf.IP_Phone,"
					+ "conf.IPAddress,loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,"
					+ "conf.Purpose,conf.approval_status,conf.approved_date from ConferenceRoom_Details conf,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where "
					+ "conf.Reqest_No='"+requstNo+"' and conf.Requster_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
					ResultSet rs=ad.selectQuery(getRequestDetails);
					try{
					while(rs.next()){
						ConferenceForm c=new ConferenceForm();
						c.setReqNo(rs.getString("Requster_Id"));
						c.setEmpId(rs.getString("Requster_Id"));
						c.setEmpName(rs.getString("EMP_FULLNAME"));
						c.setDept(rs.getString("DPTSTXT"));
						c.setDesg(rs.getString("DSGSTXT"));
						c.setExtno(rs.getString("Ext_No"));
						c.setEmpEmailID(rs.getString("Email_ID"));
						c.setIpPhoneno(rs.getString("IP_Phone"));
						c.setIPNumber(rs.getString("IPAddress"));
						c.setLocation(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
						c.setFloor(rs.getString("Floor"));
						c.setRoomName(rs.getString("Conf_Room"));
						c.setFromDate(EMicroUtils.display(rs.getDate("From_Date")));
						c.setFromTime(rs.getString("From_Time"));
						c.setToDate(EMicroUtils.display(rs.getDate("To_Date")));
						c.setToTime(rs.getString("To_Time"));
						c.setPurpose(rs.getString("Purpose"));
						c.setStatus(rs.getString("approval_status"));
						String approvalStatus=rs.getString("approval_status");
						if(approvalStatus.equals("Pending")||approvalStatus.equals("Approved"))
							request.setAttribute("cancelRequest", "cancelRequest");
						c.setApprovedDate("");
						if(!approvalStatus.equals("Pending"))
							c.setApprovedDate(rs.getString("approved_date"));
						confDetails.add(c);
					}
					request.setAttribute("confList", confDetails);
					}catch(Exception e){
						e.printStackTrace();
					}
					//Approvers Details
					List appList=new LinkedList();
					String getApproverDetails="select appr.Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments,all_r.Approved_Persons,all_r.Pending_Approver from ConferenceRoom_Details conf,ConferanceRoom_Approvers appr ,"
							+ "All_Request all_r,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where conf.Reqest_No="+requstNo+" and conf.Loc_Id=appr.LocID "
							+ "and conf.Floor=appr.Floor and conf.Conf_Room=appr.Room and all_r.Req_Id=conf.Reqest_No and all_r.Req_Type='Conference' and  "
							+ "(appr.Approver_Id=all_r.Last_Approver or appr.Approver_Id=all_r.Pending_Approver) and  (all_r.Pending_Approver=emp.PERNR or "
							+ "all_r.Last_Approver =emp.PERNR) and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
				ResultSet rsAppr=ad.selectQuery(getApproverDetails);
				try{
					while(rsAppr.next()){
					ApprovalsForm appr=new ApprovalsForm();
					appr.setApprover(rsAppr.getString("EMP_FULLNAME"));
					appr.setDesignation(rsAppr.getString("DPTSTXT"));
					appr.setApproveStatus(rsAppr.getString("Req_Status"));
					String approvaStatus=rsAppr.getString("Req_Status");
					if(approvaStatus.equals("Pending")){
						appr.setApproveDate("");
						appr.setComments("");
					}
					if(approvaStatus.equals("Approved")||approvaStatus.equals("Completed")){
						appr.setApproveDate(rsAppr.getString("approved_date"));
						appr.setComments(rsAppr.getString("Comments"));
					}
					if(approvaStatus.equals("Rejected")||approvaStatus.equals("Cancelled")){
						appr.setApproveDate(rsAppr.getString("rejected_date"));
						appr.setComments(rsAppr.getString("Comments"));
					}
					appList.add(appr);
					}
					request.setAttribute("appList",appList);
					ad.connClose();
				}catch(Exception e){
					e.printStackTrace();
				}
				forwardType="Conference";
			
		}
		
		
		if(type.equalsIgnoreCase("New IT Spares Request"))
		{
			LinkedList newasset=new LinkedList();
			LinkedList Adusercreation=new LinkedList();
			String empno="";
			
			String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs00= ad.selectQuery(data00);
			try {
				while(rs00.next())
				{
				if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
				{
					empno=rs00.getString("Emp_No");
				}
				else
				{
					empno=user.getEmployeeNo();
				}
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			try {
				while(rs.next())
				{
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setEmpno(rs.getString("PERNR"));
					
				}
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			
			String locID="";
			String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
	ResultSet rs1= ad.selectQuery(data1);
	try {
		while(rs1.next())
		{
			help.setAssetvalue(rs1.getString("Asset_Details"));
			help.setAdloginname(rs1.getString("ADLoginName"));
			help.setServer(rs1.getString("Server_Address"));
			help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
			help.setReason(rs1.getString("Reason"));
			help.setHostname(rs1.getString("HostName"));
			help.setIPNumber(rs1.getString("IPAddress"));
			help.setReqPriority(rs1.getString("Req_Priority"));
			help.setAssetcategory(rs1.getString("Category"));
			help.setReqbydate(rs1.getString("reqbydate"));
			help.setSuggestmodelname(rs1.getString("suggmodel"));
			
			help.setGxpyes(rs1.getString("gxp"));
			
			help.setTypeofprinter(rs1.getString("typeofprinter"));
			help.setUsers(rs1.getString("noofusers"));
			help.setPrintvolpermonth(rs1.getString("printvolpm"));
			help.setSuggestmodelno(rs1.getString("suggmodelno"));
			help.setApprxvalue(rs1.getString("Apprxvalue"));
	
			help.setLanyes(rs1.getString("lan"));
			help.setAnysplreq(rs1.getString("anysplreq"));
			help.setPurpose(rs1.getString("purpose"));
            locID=rs1.getString("Location");

			newasset.add(help);
		}
	} catch (SQLException e) {
		  e.printStackTrace();
		}
	
	//Approver Details
	int  checkStatus=0;
	LinkedList listApprers=new LinkedList();
	int jk=0;
	
	//HOD

	int hodpresent=0;
	int hodpresentinIt=0;
	String HODPresentId="";
	String HODPresentname="";
	String itengineerid="";
	String itengineername="";
	String HOD="";
	String	pendingApprovers="";
	
	 //chk if he has Hod
	 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
	 ResultSet rs10= ad.selectQuery(chkhod);
	 try {
		while(rs10.next())
		 {
			 hodpresent=rs10.getInt(1);
		 }
	} catch (SQLException e1) {
		
		e1.printStackTrace();
	}
	
	//get hod details
	

	if(hodpresent>0){
	String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
			" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
	 ResultSet rs2= ad.selectQuery(gethod);
	 
	 try {
		if(rs2.next())
		 {
		
			HODPresentId=rs2.getString("ApproverId");
			HODPresentname=rs2.getString("EMP_FULLNAME");
		 }
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	}
	
	//chk if he is present in It approver
	 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+locID+"'";
	 ResultSet rs11= ad.selectQuery(chkhodinit);
	 try {
		while(rs11.next())
		 {
			hodpresentinIt=rs11.getInt(1);
		 }
	} catch (SQLException e1) {
		
		e1.printStackTrace();
	}
	 
	 

	 
	 
			
		if(hodpresent>0 && hodpresentinIt==0 )
		{
			
			
			try {
				
					checkStatus=1;
					ApprovalsForm apprvershod=new ApprovalsForm();
					apprvershod.setPriority("1");
					
					apprvershod.setEmployeeCode(HODPresentId);
					apprvershod.setEmployeeName(HODPresentname);
					
					String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
				+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+locID+"' AND "
				+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
				+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
						{
							apprvershod.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvershod.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvershod.setComments("");
						}else{
							apprvershod.setComments(rsAppInfo.getString("Comments"));
						}
					}
					
					
					listApprers.add(apprvershod);
				 jk=1;
				}
			
			catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
		}
	
			
	
	
	
	String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
	ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
	try {
		while(rsApprDetails.next())
		{
			
			checkStatus=1;
			ApprovalsForm apprvers=new ApprovalsForm();
			apprvers.setPriority(rsApprDetails.getString("Priority"));
			
				
			apprvers.setPriority(rsApprDetails.getString("Priority"));
			if(jk==1){
				
				apprvers.setPriority(Integer.toString(listApprers.size()+1));
			}
			apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
			String empCode=rsApprDetails.getString("Approver_Id");
			
			String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
					+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
					+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
					+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
					+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
			
			ResultSet rsAppInfo=ad.selectQuery(recordStatus);
			if(rsAppInfo.next())
			{
				if(rsAppInfo.getInt("Priority")==1 )
				{
					 chkdate=rsAppInfo.getString("approved_date");
					 chkdate1=rsAppInfo.getString("rejected_date");
				}
				apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
				String approveStatus=rsAppInfo.getString("Req_Status");
				if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
				{
					
				apprvers.setDate(rsAppInfo.getString("approved_date"));
				
				}else{
					apprvers.setDate(rsAppInfo.getString("rejected_date"));
				}
				String comments="";
				comments=rsAppInfo.getString("Comments");
				if(comments=="null" )
				{
					apprvers.setComments("");
				}else{
					apprvers.setComments(rsAppInfo.getString("Comments"));
				}
			}
			apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
			
			listApprers.add(apprvers);
			
		}
			//IT ENGINEER DETAIlS
			
			
			String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+locID+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
			
				while(rsApprDetails1.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(Integer.toString(listApprers.size()+1));
					
					apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
					String empCode1=rsApprDetails1.getString("Approver_Id");
					
					String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+locID+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
					if(rsAppInfo1.next())
					{
						
						String approveStatus=rsAppInfo1.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
							recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
							ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
							if(rsAppInfo2.next())
							{
								if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
								{
									apprvers.setApproveStatus("Closed");
								apprvers.setDate(rsAppInfo2.getString("approved_date"));
								
							
							
							String comments="";
							comments=rsAppInfo2.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo2.getString("Comments"));
							}
							}
							}
						}else{
							
							recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
							ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
							if(rsAppInfo3.next())
							{
								if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
								{
								apprvers.setDate(rsAppInfo3.getString("rejected_date"));
								}
							
							String comments="";
							comments=rsAppInfo3.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo3.getString("Comments"));
							}
							}
						}
						
					}
					apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
			
				listApprers.add(apprvers);
				}
			
			request.setAttribute("approverDetails", listApprers);
		
	} 
	
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
			
	request.setAttribute("newspare",newasset );
	forwardType="viewNewITSparesform";
		
			
		}
		
				if(type.equalsIgnoreCase("New IT Asset Request"))
				{

					LinkedList newasset=new LinkedList();
String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
					help.setReason(rs1.getString("Reason"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setAssetcategory(rs1.getString("Category"));
					help.setReqbydate(rs1.getString("reqbydate"));
					help.setSuggestmodelname(rs1.getString("suggmodel"));
					
					help.setGxpyes(rs1.getString("gxp"));
					
					help.setTypeofprinter(rs1.getString("typeofprinter"));
					help.setUsers(rs1.getString("noofusers"));
					help.setPrintvolpermonth(rs1.getString("printvolpm"));
					help.setSuggestmodelno(rs1.getString("suggmodelno"));
					help.setApprxvalue(rs1.getString("Apprxvalue"));
			
					help.setLanyes(rs1.getString("lan"));
					help.setAnysplreq(rs1.getString("anysplreq"));
					help.setPurpose(rs1.getString("purpose"));
			
					newasset.add(help);
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			//HOD

			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String HOD="";
			String	pendingApprovers="";
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs10= ad.selectQuery(chkhod);
			 try {
				while(rs10.next())
				 {
					 hodpresent=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			

			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
				if(hodpresent>0 && hodpresentinIt==0 )
				{
					
					
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(HODPresentId);
							apprvershod.setEmployeeName(HODPresentname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
						+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			
			

			
			
			
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
							 chkdate1=rsAppInfo.getString("rejected_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
					
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus("Closed");
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
										{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										}
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			request.setAttribute("newasset",newasset );
					forwardType="viewNewITAssetform";
				
					
				
					
				}
				
				
				if(type.equalsIgnoreCase("Active Directory User Creation"))
				{
					LinkedList Adusercreation=new LinkedList();
String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReqdfolderacces(rs1.getString("Req_Folder_access"));
					help.setReason(rs1.getString("Reason"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setInitials(rs1.getString("Initials"));
					help.setFirstname(rs1.getString("Frst_Name"));
					help.setLastname(rs1.getString("Last_Name"));
					
					Adusercreation.add(help);
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			//HOD

			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String HOD="";
			String	pendingApprovers="";
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs10= ad.selectQuery(chkhod);
			 try {
				while(rs10.next())
				 {
					 hodpresent=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			

			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
				if(hodpresent>0 && hodpresentinIt==0 )
				{
					
					
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(HODPresentId);
							apprvershod.setEmployeeName(HODPresentname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
						+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			
			

			
			
			
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					/*
					String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
							 chkdate1=rsAppInfo.getString("rejected_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);
					
				*/}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,it.Parallel_Approver1,it.Parallel_Approver2,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,it.Parallel_Approver1,it.Parallel_Approver2,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");

String par1=rsApprDetails1.getString("Parallel_Approver1");
String par2=rsApprDetails1.getString("Parallel_Approver2");
							
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND (all_r.Last_Approver='"+empCode1+"' or all_r.Last_Approver='"+par1+"' or all_r.Last_Approver='"+par2+"' ) and (it.Approver_Id=all_r.Last_Approver or it.Parallel_Approver1=all_r.Last_Approver or it.Parallel_Approver2=all_r.Last_Approver) and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus("Closed");
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
										{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										}
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			request.setAttribute("Adusercreation",Adusercreation );
			forwardType="viewAduserCreationform";
				}
				if(type.equalsIgnoreCase("Active Directory User Deletion"))
				{
					LinkedList AduserDeletion=new LinkedList();
String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
						
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							
							help.setEmpno(rs.getString("PERNR"));
						
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setServer(rs1.getString("Server_Address"));
					help.setReqdfolderdeletion(rs1.getString("Req_Folder_Deletion"));
					help.setDeletefrom(rs1.getString("Delete_From"));
					help.setReason(rs1.getString("Reason"));
					help.setHostname(rs1.getString("HostName"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
			
					AduserDeletion.add(help);
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				int jk=0;
				
				//HOD

				int hodpresent=0;
				int hodpresentinIt=0;
				String HODPresentId="";
				String HODPresentname="";
				String itengineerid="";
				String itengineername="";
				String HOD="";
				String	pendingApprovers="";
				
				 //chk if he has Hod
				 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
				 ResultSet rs10= ad.selectQuery(chkhod);
				 try {
					while(rs10.next())
					 {
						 hodpresent=rs10.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
				//get hod details
				

				if(hodpresent>0){
				String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
						" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
				 ResultSet rs2= ad.selectQuery(gethod);
				 
				 try {
					if(rs2.next())
					 {
					
						HODPresentId=rs2.getString("ApproverId");
						HODPresentname=rs2.getString("EMP_FULLNAME");
					 }
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				}
				
				//chk if he is present in It approver
				 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
				 ResultSet rs11= ad.selectQuery(chkhodinit);
				 try {
					while(rs11.next())
					 {
						hodpresentinIt=rs11.getInt(1);
					 }
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				 
					if(hodpresent>0 && hodpresentinIt==0 )
					{
						
						
						try {
							
								checkStatus=1;
								ApprovalsForm apprvershod=new ApprovalsForm();
								apprvershod.setPriority("1");
								
								apprvershod.setEmployeeCode(HODPresentId);
								apprvershod.setEmployeeName(HODPresentname);
								
								String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
							+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
							+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
							+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								if(rsAppInfo.next())
								{
									apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
									{
										apprvershod.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvershod.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvershod.setComments("");
									}else{
										apprvershod.setComments(rsAppInfo.getString("Comments"));
									}
								}
								
								
								listApprers.add(apprvershod);
							 jk=1;
							}
						
						catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				
				

				
				
				
				String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						
							
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						if(jk==1){
							
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
						}
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						
						/*String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
								+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
								+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
								+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
								+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
						
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							if(rsAppInfo.getInt("Priority")==1 )
							{
								 chkdate=rsAppInfo.getString("approved_date");
								 chkdate1=rsAppInfo.getString("rejected_date");
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
								
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						
						listApprers.add(apprvers);
						
					}*/
					}
						//IT ENGINEER DETAIlS
						
						
						String getApprDetails1="select it.Priority,it.Approver_Id,it.Parallel_Approver1,it.Parallel_Approver1,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,it.Parallel_Approver1,it.Parallel_Approver2,emp.EMP_FULLNAME order by Priority";
						ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
						
							while(rsApprDetails1.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
								
								apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
								String empCode1=rsApprDetails1.getString("Approver_Id");
								String par1=rsApprDetails1.getString("Parallel_Approver1");
								String par2=rsApprDetails1.getString("Parallel_Approver2");
								String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
										+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
										+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND (all_r.Last_Approver='"+empCode1+"' or all_r.Last_Approver='"+par1+"' or all_r.Last_Approver='"+par2+"' ) and (it.Approver_Id=all_r.Last_Approver or it.Parallel_Approver1=all_r.Last_Approver or it.Parallel_Approver2=all_r.Last_Approver ) and all_r.Req_Type='IT Request' and "
										+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
										+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
								
								ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
								if(rsAppInfo1.next())
								{
									
									String approveStatus=rsAppInfo1.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
									{
										recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
										ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
										if(rsAppInfo2.next())
										{
											if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
											{
												apprvers.setApproveStatus("Closed");
											apprvers.setDate(rsAppInfo2.getString("approved_date"));
											
										
										
										String comments="";
										comments=rsAppInfo2.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo2.getString("Comments"));
										}
										}
										}
									}else{
										
										recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
										ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
										if(rsAppInfo3.next())
										{
											if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
											{
											apprvers.setDate(rsAppInfo3.getString("rejected_date"));
											}
										
										String comments="";
										comments=rsAppInfo3.getString("Comments");
										if(comments=="null" )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo3.getString("Comments"));
										}
										}
									}
									
								}
								apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
						
							listApprers.add(apprvers);
							}
						
						request.setAttribute("approverDetails", listApprers);
				}
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
				request.setAttribute("AduserDeletion",AduserDeletion );
				forwardType="viewAduserDeletionform";
				}
				
				if(type.equalsIgnoreCase("Active Directory User Transfer"))
				{

					LinkedList Adusertransfer=new LinkedList();
String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
			ResultSet rs1= ad.selectQuery(data1);
			try {
				while(rs1.next())
				{
					help.setAssetvalue(rs1.getString("Asset_Details"));
					help.setAdloginname(rs1.getString("ADLoginName"));
					help.setReason(rs1.getString("Reason"));
					help.setIPNumber(rs1.getString("IPAddress"));
					help.setReqPriority(rs1.getString("Req_Priority"));
					help.setFromlocationId(rs1.getString("From_Loc"));
					help.setTolocationId(rs1.getString("To_Loc"));
					help.setFromdepartment(rs1.getString("From_Dep"));
					help.setTodepartment(rs1.getString("To_Dep"));
					Adusertransfer.add(help);
				}
			} catch (SQLException e) {
				  e.printStackTrace();
				}
			
			//Approver Details
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			int jk=0;
			
			//HOD

			int hodpresent=0;
			int hodpresentinIt=0;
			String HODPresentId="";
			String HODPresentname="";
			String itengineerid="";
			String itengineername="";
			String HOD="";
			String	pendingApprovers="";
			
			 //chk if he has Hod
			 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs10= ad.selectQuery(chkhod);
			 try {
				while(rs10.next())
				 {
					 hodpresent=rs10.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			//get hod details
			

			if(hodpresent>0){
			String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
					" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
			 ResultSet rs2= ad.selectQuery(gethod);
			 
			 try {
				if(rs2.next())
				 {
				
					HODPresentId=rs2.getString("ApproverId");
					HODPresentname=rs2.getString("EMP_FULLNAME");
				 }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			}
			
			//chk if he is present in It approver
			 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
			 ResultSet rs11= ad.selectQuery(chkhodinit);
			 try {
				while(rs11.next())
				 {
					hodpresentinIt=rs11.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			 
				if(hodpresent>0 && hodpresentinIt==0 )
				{
					
					
					try {
						
							checkStatus=1;
							ApprovalsForm apprvershod=new ApprovalsForm();
							apprvershod.setPriority("1");
							
							apprvershod.setEmployeeCode(HODPresentId);
							apprvershod.setEmployeeName(HODPresentname);
							
							String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
						+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
						+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
						+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									apprvershod.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvershod.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvershod.setComments("");
								}else{
									apprvershod.setComments(rsAppInfo.getString("Comments"));
								}
							}
							
							
							listApprers.add(apprvershod);
						 jk=1;
						}
					
					catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
			
			

			
			
			
			String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					
						
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					if(jk==1){
						
						apprvers.setPriority(Integer.toString(listApprers.size()+1));
					}
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					
					/*String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
							+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
							+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
							+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
							+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
					
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						if(rsAppInfo.getInt("Priority")==1 )
						{
							 chkdate=rsAppInfo.getString("approved_date");
							 chkdate1=rsAppInfo.getString("rejected_date");
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					
					listApprers.add(apprvers);*/
					
				}
					//IT ENGINEER DETAIlS
					
					
					String getApprDetails1="select it.Priority,it.Approver_Id,it.Parallel_Approver1,it.Parallel_Approver2,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,it.Parallel_Approver1,it.Parallel_Approver2,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
					
						while(rsApprDetails1.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(Integer.toString(listApprers.size()+1));
							
							apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
							String empCode1=rsApprDetails1.getString("Approver_Id");
							String par1=rsApprDetails1.getString("Parallel_Approver1");
							String par2=rsApprDetails1.getString("Parallel_Approver2");
							String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND (all_r.Last_Approver='"+empCode1+"' or all_r.Last_Approver='"+par1+"' or all_r.Last_Approver='"+par2+"' )  and (it.Approver_Id=all_r.Last_Approver or it.Parallel_Approver1=all_r.Last_Approver or it.Parallel_Approver2=all_r.Last_Approver ) and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
							if(rsAppInfo1.next())
							{
								
								String approveStatus=rsAppInfo1.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
									ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
									if(rsAppInfo2.next())
									{
										if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
										{
											apprvers.setApproveStatus("Closed");
										apprvers.setDate(rsAppInfo2.getString("approved_date"));
										
									
									
									String comments="";
									comments=rsAppInfo2.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo2.getString("Comments"));
									}
									}
									}
								}else{
									
									recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
									ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
									if(rsAppInfo3.next())
									{
										if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
										{
										apprvers.setDate(rsAppInfo3.getString("rejected_date"));
										}
									
									String comments="";
									comments=rsAppInfo3.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo3.getString("Comments"));
									}
									}
								}
								
							}
							apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
					
						listApprers.add(apprvers);
						}
					
					request.setAttribute("approverDetails", listApprers);
				
			} 
			
				catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
					
			request.setAttribute("Adusertransfer",Adusertransfer );
					forwardType="viewAduserTransferform";
				
					
				}
				
				
				
				if(type.equalsIgnoreCase("Email ID Create Request"))
				{
					LinkedList LotusIDCreation=new LinkedList();
                    
					

					ArrayList desgList=new ArrayList();
					ArrayList desgLabelList=new ArrayList();
					ResultSet rs16 = ad.selectQuery("select dsgid," +
							"DSGLTXT from DESIGNATION order by DSGLTXT"); 
							try {
								while(rs16.next()) {
									desgList.add(rs16.getString("dsgid"));
									desgLabelList.add(rs16.getString("DSGLTXT"));
								}
								rs16.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								
								e.printStackTrace();
							}
							
							myReqForm.setDesgList(desgList);
							myReqForm.setDesgLabelList(desgLabelList);
					
							ArrayList deptList=new ArrayList();
							ArrayList deptLabelList=new ArrayList();
							ResultSet rs161 = ad.selectQuery("select dptid," +
									"DPTLTXT from DEPARTMENT order by DPTLTXT "); 
									try {
										while(rs161.next()) {
											deptList.add(rs161.getString("dptid"));
											deptLabelList.add(rs161.getString("DPTLTXT"));
										}
										rs16.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									myReqForm.setDeptList(deptList);
									myReqForm.setDeptLabelList(deptLabelList);
			
							
							
							String empno="";
                    
                    
                    
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK left outer join department on IT_HELPDESK.department=department.dptid left  outer join designation on IT_HELPDESK.designation=designation.dsgid  where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{

							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
							String x=rs1.getString("comm_outside");
							if(x.equalsIgnoreCase("Yes"))
							{
								help.setOutsidecomyes("Yes");
							}
							if(x.equalsIgnoreCase("No"))
							{
								help.setOutsidecomyes("No");
							}
							help.setIsHeHod(rs1.getString("Is_He_Hod"));
							help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
							help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setFirstname(rs1.getString("Frst_Name"));
							help.setLastname(rs1.getString("Last_Name"));
							help.setMiddname(rs1.getString("Middle_Name"));
							help.setDepartment(rs1.getString("dptltxt"));
							help.setDesignation(rs1.getString("dsgltxt"));
							
							//newly_Joinied,joining_date,Total_Mailids
							String a=rs1.getString("newly_Joinied");
							if(a.equalsIgnoreCase("Yes"))
							{
								help.setNewlyJoinedYes("Yes");
							}
							if(a.equalsIgnoreCase("No"))
							{
								help.setNewlyJoinedYes("No");
							}
							help.setJoiningDate(rs1.getString("joining_date"));
							help.setTotalIDs(rs1.getString("Total_Mailids"));
						
							
							LotusIDCreation.add(help);
							
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD

					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					

					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=user.getPlantId();
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
					 
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
						}
					
						 if(!plantheadid.equalsIgnoreCase(""))
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(plantheadid);
										apprvershod.setEmployeeName(plantheadname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
									+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						

					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					request.setAttribute("LotusIDCreation", LotusIDCreation);
					forwardType="viewLotusIDCreationform";
				}
				
				if(type.equalsIgnoreCase("Email ID Change Request"))
				{
					LinkedList LotusIDChange=new LinkedList();
                    String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{

							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setFromemailId(rs1.getString("From_EmailID"));
							String x=rs1.getString("comm_outside");
							if(x.equalsIgnoreCase("Yes"))
							{
								help.setOutsidecomyes("Yes");
							}
							if(x.equalsIgnoreCase("No"))
							{
								help.setOutsidecomyes("No");
							}
							help.setDeleteoldmails(rs1.getString("delete_old_email"));
							help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
							help.setIsHeHod(rs1.getString("Is_He_Hod"));
							help.setTransferoldmail(rs1.getString("Transfer_Old_Emails"));
							help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
							help.setEnablewebAccess(rs1.getString("Enable_Web_access"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							String a=rs1.getString("newly_Joinied");
							if(a.equalsIgnoreCase("Yes"))
							{
								help.setNewlyJoinedYes("Yes");
							}
							if(a.equalsIgnoreCase("No"))
							{
								help.setNewlyJoinedYes("No");
							}
							help.setJoiningDate(rs1.getString("joining_date"));
							help.setTotalIDs(rs1.getString("Total_Mailids"));
							
							help.setFirstname(rs1.getString("Frst_Name"));
							help.setLastname(rs1.getString("Last_Name"));
							help.setMiddname(rs1.getString("Middle_Name")); 
							
							
							LotusIDChange.add(help);
							
						}
						
						
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD

					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					

					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=user.getPlantId();
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
						}
						
						 if(!plantheadid.equalsIgnoreCase(""))
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(plantheadid);
										apprvershod.setEmployeeName(plantheadname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
									+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						
					

					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					request.setAttribute("LotusIDChange", LotusIDChange);
					forwardType="viewLotusIDChangeform";
				}
				
				if(type.equalsIgnoreCase("Email ID Delete Request"))
				{

					LinkedList LotusIDDeletion=new LinkedList();
                      String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{

							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setSuggestedEmailId(rs1.getString("SuggestedEmailId"));
					        help.setIsHeHod(rs1.getString("Is_He_Hod"));
							help.setIsthisCommonId(rs1.getString("Is_This_common_ID"));
							help.setEmailidDelete(rs1.getString("EmailID_Deleted"));
							help.setDeletefrom(rs1.getString("Delete_From"));
						    help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setMailIDPath(rs1.getString("path"));
							help.setDeletedDBYes(rs1.getString("deletedDB"));
							help.setForward_MailID(rs1.getString("forward_MailID"));
						
						
							
							LotusIDDeletion.add(help);
							
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD

					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					

					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
					//Plant head
						String plantheadid="";
						String plantheadname="";
						String plantheaddep="";
						String plantheaddesg="";
						String a=user.getPlantId();
						if(!a.equalsIgnoreCase("16"))
						{
							String plant ="select pl.Plant,pl.PERNR,emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from PLANT_HEAD pl,emp_official_info emp,DEPARTMENT dep,DESIGNATION desg where Plant=(select LOCATION_CODE from Location where LOCID='"+a+"') and emp.PERNR=pl.PERNR and dep.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
							ResultSet pl=ad.selectQuery(plant);
							try {
								while(pl.next())
								 {
									plantheadid=pl.getString("pernr");
									plantheadname=pl.getString("EMP_FULLNAME");
									plantheaddep=pl.getString("DPTSTXT");
									plantheaddesg=pl.getString("DSGSTXT");
								 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
						if(plantheadid.equalsIgnoreCase(""))
						{
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
						}
						
						 if(!plantheadid.equalsIgnoreCase(""))
							{
								
								
								try {
									
										checkStatus=1;
										ApprovalsForm apprvershod=new ApprovalsForm();
										apprvershod.setPriority("1");
										
										apprvershod.setEmployeeCode(plantheadid);
										apprvershod.setEmployeeName(plantheadname);
										
										String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
									+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
									+ "all_r.Last_Approver='"+plantheadid+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
									+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
										
										ResultSet rsAppInfo=ad.selectQuery(recordStatus);
										if(rsAppInfo.next())
										{
											apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
											String approveStatus=rsAppInfo.getString("Req_Status");
											if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
											{
												apprvershod.setDate(rsAppInfo.getString("approved_date"));
											}else{
												apprvershod.setDate(rsAppInfo.getString("rejected_date"));
											}
											String comments="";
											comments=rsAppInfo.getString("Comments");
											if(comments=="null" )
											{
												apprvershod.setComments("");
											}else{
												apprvershod.setComments(rsAppInfo.getString("Comments"));
											}
										}
										
										
										listApprers.add(apprvershod);
									 jk=1;
									}
								
								catch (SQLException e1) {
									
									e1.printStackTrace();
								}
								
							}
						
					

					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					request.setAttribute("LotusIDDeletion", LotusIDDeletion);
					forwardType="viewLotusIDDeletionform";
				
					
				}

					
				
				if(type.equalsIgnoreCase("Internet Access Request"))
				{
					LinkedList internet=new LinkedList();
String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAssetvalue(rs1.getString("Asset_Details"));
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setIsHeHod(rs1.getString("Is_He_Hod"));
							internet.add(help);
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
							
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD

					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					

					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					

					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					request.setAttribute("Internet", internet);
					forwardType="viewInternetAccessform";
				}
				
				if(type.equalsIgnoreCase("External Drives Access Request"))
				{
					LinkedList externaldrives=new LinkedList();
String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setAssetvalue(rs1.getString("Asset_Details"));
							help.setAccesstype(rs1.getString("Access_type"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
						
							String device=rs1.getString("Device_type");
							if(device.equalsIgnoreCase("3"))
							{
								help.setCdrom("CD/DVD_ROM");
								help.setUsb("USB");
							}
							if(device.equalsIgnoreCase("1"))
							{
								help.setCdrom("CD/DVD_ROM");
								
							}
							if(device.equalsIgnoreCase("2"))
							{
								
								help.setUsb("USB");
							}
							externaldrives.add(help);
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD

					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					

					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					

					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					request.setAttribute("externaldrives", externaldrives);
					forwardType="viewexternaldrivesform";
				}
				
				if(type.equalsIgnoreCase("FTP Access Request"))
				{
					LinkedList ftp=new LinkedList();
String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						  
						   
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setAssetvalue(rs1.getString("Asset_Details"));
							help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
							help.setServer(rs1.getString("Server_Address"));
							help.setAccesstype(rs1.getString("Access_type"));
							help.setFrmdurationofaccess(rs1.getString("FrmRequiredDurationofAccess"));
							help.setTodurationofaccess(rs1.getString("ToRequiredDurationofAccess"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setDurationofaccess(rs1.getString("RequiredDurationofAccess"));
							 String fileusername=rs1.getString("UserName");
							 if(fileusername!=null)
								 help.setUsername(fileusername);
							 else
								 help.setUsername("");
							 
							   String filePwd=rs1.getString("Password");
							   if(filePwd!=null)
								   help.setPassword(filePwd);
							   else
								   help.setPassword("");
							ftp.add(help);
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD

					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					

					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					

					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					request.setAttribute("ftp", ftp);
					forwardType="viewFtpAccessform";
				}
				
				if(type.equalsIgnoreCase("File Server Access Request"))
				{
					LinkedList folder=new LinkedList();
String empno="";
					
					String data00=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs00= ad.selectQuery(data00);
					while(rs00.next())
					{
					if(!rs00.getString("raisedBy").equalsIgnoreCase(rs00.getString("Emp_No")))
					{
						empno=rs00.getString("Emp_No");
					}
					else
					{
						empno=user.getEmployeeNo();
					}
					}
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					ResultSet rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							
							help.setRequestername(rs.getString("EMP_FULLNAME"));
							help.setRequesterdepartment(rs.getString("DPTSTXT"));
							help.setRequesterdesignation(rs.getString("DSGSTXT"));
							help.setExtno(rs.getString("TEL_EXTENS"));
							help.setIpPhoneno(rs.getString("IP_PHONE"));
							help.setLocation(rs.getString("LOCATION_CODE"));
						
							help.setEmpno(rs.getString("PERNR"));
							
						}
					} catch (SQLException e) {
					  e.printStackTrace();
					}
					
					String data1=" select * from IT_HELPDESK where Req_No="+reqno+"";	
					ResultSet rs1= ad.selectQuery(data1);
					try {
						while(rs1.next())
						{
							help.setAdloginname(rs1.getString("ADLoginName"));
							help.setAssetvalue(rs1.getString("Asset_Details"));
							help.setRequiredfoldername(rs1.getString("Req_Folder_access"));
							help.setServer(rs1.getString("Server_Address"));
							help.setAccesstype(rs1.getString("Access_type"));
							help.setReason(rs1.getString("Reason"));
							help.setReqPriority(rs1.getString("Req_Priority"));
							help.setHostname(rs1.getString("HostName"));
							help.setIPNumber(rs1.getString("IPAddress"));
							help.setFromlocationId(rs1.getString("From_Loc"));
							
							folder.add(help);
						}
					} catch (SQLException e) {
						  e.printStackTrace();
						}
					
					
					//Approver Details
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					int jk=0;
					
					//HOD

					int hodpresent=0;
					int hodpresentinIt=0;
					String HODPresentId="";
					String HODPresentname="";
					String itengineerid="";
					String itengineername="";
					String HOD="";
					String	pendingApprovers="";
					
					 //chk if he has Hod
					 String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
					 ResultSet rs10= ad.selectQuery(chkhod);
					 try {
						while(rs10.next())
						 {
							 hodpresent=rs10.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
					//get hod details
					

					if(hodpresent>0){
					String gethod="select ess.ApproverId,emp.EMP_FULLNAME from ESS_Approvers  as ess,emp_official_info as emp  " +
							" where employeeNumber='"+empno+"'  and essType='Leave' and emp.PERNR=ess.ApproverId ";
					 ResultSet rs2= ad.selectQuery(gethod);
					 
					 try {
						if(rs2.next())
						 {
						
							HODPresentId=rs2.getString("ApproverId");
							HODPresentname=rs2.getString("EMP_FULLNAME");
						 }
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					}
					
					//chk if he is present in It approver
					 String chkhodinit="select count(*) from IT_Approvers where Approver_ID='"+HODPresentId+"' and Req_Type = '"+type+"'  and LOCATION_ID='"+user.getPlantId()+"'";
					 ResultSet rs11= ad.selectQuery(chkhodinit);
					 try {
						while(rs11.next())
						 {
							hodpresentinIt=rs11.getInt(1);
						 }
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					 
						if(hodpresent>0 && hodpresentinIt==0 )
						{
							
							
							try {
								
									checkStatus=1;
									ApprovalsForm apprvershod=new ApprovalsForm();
									apprvershod.setPriority("1");
									
									apprvershod.setEmployeeCode(HODPresentId);
									apprvershod.setEmployeeName(HODPresentname);
									
									String recordStatus="select emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments"
								+ " from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' and it.LOCATION_ID='"+user.getPlantId()+"' AND "
								+ "all_r.Last_Approver='"+HODPresentId+"' and all_r.Req_Type='IT Request' and it.Req_Type='"+type+"' and emp.PERNR=all_r.Last_Approver"
								+ " group by emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date, all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									if(rsAppInfo.next())
									{
										apprvershod.setApproveStatus(rsAppInfo.getString("Req_Status"));
										String approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
										{
											apprvershod.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvershod.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments=="null" )
										{
											apprvershod.setComments("");
										}else{
											apprvershod.setComments(rsAppInfo.getString("Comments"));
										}
									}
									
									
									listApprers.add(apprvershod);
								 jk=1;
								}
							
							catch (SQLException e1) {
								
								e1.printStackTrace();
							}
							
						}
					
					

					
					
					
					String getApprDetails="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							
								
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							if(jk==1){
								
								apprvers.setPriority(Integer.toString(listApprers.size()+1));
							}
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							
							String recordStatus="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
									+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
									+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode+"' and it.Approver_Id=all_r.Last_Approver  and all_r.Req_Type='IT Request' and "
									+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
									+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
							
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								if(rsAppInfo.getInt("Priority")==1 )
								{
									 chkdate=rsAppInfo.getString("approved_date");
									 chkdate1=rsAppInfo.getString("rejected_date");
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							
							listApprers.add(apprvers);
							
						}
							//IT ENGINEER DETAIlS
							
							
							String getApprDetails1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp 	where it.LOCATION_ID='"+user.getPlantId()+"'  and it.Approver_Id=emp.PERNR  and it.Req_Type='"+type+"' and it.Priority=1    group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME order by Priority";
							ResultSet rsApprDetails1=ad.selectQuery(getApprDetails1);
							
								while(rsApprDetails1.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(Integer.toString(listApprers.size()+1));
									
									apprvers.setEmployeeCode(rsApprDetails1.getString("Approver_Id"));
									String empCode1=rsApprDetails1.getString("Approver_Id");
									
									String recordStatus1="select it.Priority,it.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,"
											+ "all_r.Comments from IT_Approvers as it,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqno+"' "
											+ "and it.LOCATION_ID='"+user.getPlantId()+"' AND all_r.Last_Approver='"+empCode1+"' and it.Approver_Id=all_r.Last_Approver and all_r.Req_Type='IT Request' and "
											+ "it.Req_Type='"+type+"' and it.Approver_Id=emp.PERNR  group by it.Priority,it.Approver_Id,emp.EMP_FULLNAME,"
											+ "all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments   HAVING count(*) > 0";
									
									ResultSet rsAppInfo1=ad.selectQuery(recordStatus1);
									if(rsAppInfo1.next())
									{
										
										String approveStatus=rsAppInfo1.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase("Approved"))
										{
											recordStatus1=recordStatus1+" order by all_r.approved_date desc ";
											ResultSet rsAppInfo2=ad.selectQuery(recordStatus1);
											if(rsAppInfo2.next())
											{
												if(!chkdate.equalsIgnoreCase(rsAppInfo2.getString("approved_date")))
												{
													apprvers.setApproveStatus("Closed");
												apprvers.setDate(rsAppInfo2.getString("approved_date"));
												
											
											
											String comments="";
											comments=rsAppInfo2.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo2.getString("Comments"));
											}
											}
											}
										}else{
											
											recordStatus1=recordStatus1+" order by all_r.rejected_date desc ";
											ResultSet rsAppInfo3=ad.selectQuery(recordStatus1);
											if(rsAppInfo3.next())
											{
												if(!chkdate1.equalsIgnoreCase(rsAppInfo3.getString("rejected_date")))
												{
												apprvers.setDate(rsAppInfo3.getString("rejected_date"));
												}
											
											String comments="";
											comments=rsAppInfo3.getString("Comments");
											if(comments=="null" )
											{
												apprvers.setComments("");
											}else{
												apprvers.setComments(rsAppInfo3.getString("Comments"));
											}
											}
										}
										
									}
									apprvers.setEmployeeName(rsApprDetails1.getString("EMP_FULLNAME"));
							
								listApprers.add(apprvers);
								}
							
							request.setAttribute("approverDetails", listApprers);
						
					} 
					
						catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					request.setAttribute("folder", folder);
					forwardType="viewFolderAccessform";
				}
				
				if(type.equalsIgnoreCase("OnDuty"))
			     {
					
				    HRid="";
					LinkedList onduty=new LinkedList();
					OnDutyForm ondutyForm=new OnDutyForm();
				
					int userID=user.getId();
					int requestNo=Integer.parseInt(request.getParameter("requestNo"));
					try{
					ondutyForm.setRequestNumber(requestNo);
					String draftData="select * from OnDuty_details where request_no='"+requestNo+"'";
					ResultSet rs=ad.selectQuery(draftData);
					while(rs.next())
					{
						ondutyForm.setOnDutyType(rs.getString("onDuty_Type"));
						ondutyForm.setLocationId(rs.getString("location"));
						ondutyForm.setStartTime(rs.getString("startTime"));
						ondutyForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
						ondutyForm.setEndTime(rs.getString("endTime"));
						ondutyForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
						ondutyForm.setNoOfDays(rs.getString("no_of_days"));
						int approveStatus=rs.getInt("OnDuty_status");
						String status="";
						if(approveStatus==0)
						{
							status="In Process";
						}
						if(approveStatus==1)
						{
							status="Approved";
						}
						if(approveStatus==2)
						{
							status="Rejected";
						}
						if(approveStatus==3)
						{
							status="canceled";
						}
						ondutyForm.setStatus(status);
						String approveDate="";
						if(status.equalsIgnoreCase("Approved"))
						{
							approveDate=rs.getString("approved_date");
						}
						if(status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("canceled"))
						{
							approveDate=rs.getString("rejected_date");
						}
						
						if(!(approveDate.equalsIgnoreCase("")))
						{
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							ondutyForm.setApproveDate(approveDate);
						}
						
						ondutyForm.setReason(rs.getString("reason"));
						ondutyForm.setRemark(rs.getString("comments"));
						
					}			
					String sql1="select * from onDuty_emp_documents where request_no='"+ondutyForm.getRequestNumber()+"' and user_id='"+user.getId()+"' ";
					ResultSet rs1=ad.selectQuery(sql1);
					OnDutyForm leaveForm1=null;
						ArrayList a1=new ArrayList();
						while(rs1.next()) {
							leaveForm1=new OnDutyForm();
							leaveForm1.setFileName(rs1.getString("file_name"));
							leaveForm1.setId(rs1.getString("id"));
							a1.add(leaveForm1);
						}
						request.setAttribute("documentDetails", a1);
						MyRequestAction lA = new MyRequestAction();
						LinkedList appList= lA.viewOnduty(mapping, form, request, response);
						request.setAttribute("aa", appList);
					}catch (Exception e) {
						e.printStackTrace();
					}
					onduty.add(ondutyForm);
					request.setAttribute("onduty", onduty);
					
					forwardType="onDuty";
				}
				
				if(type.equalsIgnoreCase("Permission"))
			     {
					LinkedList permission=new LinkedList();
					
					PermissionForm permissionform = new PermissionForm();
			
					String requestNo=request.getParameter("requstNo");
					try{
						String getdetails="select * from Permission_details where request_no='"+requestNo+"'";
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
							int perm_status=rs.getInt("Approver_Status");
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
								status="canceled";
							}
							permissionform.setApproverStatus(status);
							String approveDate="";
							if(status.equalsIgnoreCase("Approved"))
							{
								approveDate=rs.getString("approved_date");
							}
							if(status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("canceled"))
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
						
							permissionform.setComments(rs.getString("comments"));
							
						}
						Permission lA = new Permission();
						LinkedList appList= lA.viewOnduty(mapping, form, request, response);
						request.setAttribute("appList", appList);
					}catch (Exception e) {
						e.printStackTrace();
					}
					permission.add(permissionform);
					request.setAttribute("perMission", permission);
					
					forwardType="Permission";
				}	
				if(type.equalsIgnoreCase("Customer Master"))
			     {
					
					LinkedList CustDetails=new LinkedList();
		            String location="";
			 		
		        	CustomerMasterForm custForm=new CustomerMasterForm();
			 		
			 		int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
			 		
			 		String editRecord="select C.ACCOUNT_GROUP_ID,c.REQUEST_NO,c.REQUEST_DATE,c.VIEW_TYPE,c.cutomer_code,c.Customer_Type,c.NAME, " +
			 	"c.ADDRESS1,c.ADDRESS2,c.ADDRESS3,c.ADDRESS4,c.IS_REGD_EXCISE_Customer,c.CITY,c.PINCODE,c.Attachments,c.LANDLINE_NO," +
			 	"c.MOBILE_NO,c.FAX_NO,c.EMAIL_ID,c.IS_REGD_EXCISE_VENDOR,c.LST_NO,c.TIN_NO,c.CST_NO,c.PAN_No,c.Service_Tax_Registration_No," +
			 	"c.ECC_No,c.Excise_Reg_No,c.Excise_Range,c.Excise_Division,c.DLNO1,c.DLNO2, SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
			 	"SAP_CREATED_BY,REQUESTED_BY from CUSTOMER_MASTER_M as c  where REQUEST_NO='"+requstNo1+"'";
			 		
			 		
			 		
					ResultSet rsEditRecord=ad.selectQuery(editRecord);
					if(rsEditRecord.next())
					{
						
						custForm.setRequestNumber(rsEditRecord.getInt("REQUEST_NO"));
						String reqDate=rsEditRecord.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						custForm.setRequestDate(reqDate);
						
						if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IN"))
						{
							custForm.setAccGroupId("Domestic");
						}
						if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IM"))
						{
							custForm.setAccGroupId("Export Customer");
						}
						if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("FS"))
						{
							custForm.setAccGroupId("Field Staff");
						}
						if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("7"))
						{
							custForm.setAccGroupId("Plants");
						}
						
						custForm.setSales("");
						custForm.setAccounts("");
						String viewType=rsEditRecord.getString("VIEW_TYPE");
						if(viewType.equalsIgnoreCase("S"))
						{
							custForm.setSales("Sales");
						}
						if(viewType.equalsIgnoreCase("A"))
						{
							custForm.setAccounts("Accounts");
						}
						if(viewType.equalsIgnoreCase("3"))
						{
							custForm.setSales("Sales");
							custForm.setAccounts("Accounts");
						}
					
						
						custForm.setDomestic("");
						custForm.setExports("");
						
					    custForm.setCustomerType(rsEditRecord.getString("Customer_Type"));
						custForm.setCustomerName(rsEditRecord.getString("NAME"));
						custForm.setAddress1(rsEditRecord.getString("ADDRESS1"));
						custForm.setAddress2(rsEditRecord.getString("ADDRESS2"));
						custForm.setAddress3(rsEditRecord.getString("ADDRESS3"));
						custForm.setAddress4(rsEditRecord.getString("ADDRESS4"));
						custForm.setCity(rsEditRecord.getString("CITY"));
						custForm.setPincode(rsEditRecord.getString("PINCODE"));
						
					
						custForm.setLandlineNo(rsEditRecord.getString("LANDLINE_NO"));
						custForm.setMobileNo(rsEditRecord.getString("MOBILE_NO"));
						custForm.setFaxNo(rsEditRecord.getString("FAX_NO"));
						custForm.setEmailId(rsEditRecord.getString("EMAIL_ID"));
						
						String tdsStatus=rsEditRecord.getString("IS_REGD_EXCISE_Customer");
						if(tdsStatus.equalsIgnoreCase("1"))
						{
							custForm.setTdsStatus("YES");
							request.setAttribute("setTdsState", "setTdsState");
						}
						if(tdsStatus.equalsIgnoreCase("0"))
							custForm.setTdsStatus("No");
							
					
						custForm.setListNumber(rsEditRecord.getString("LST_NO"));
						custForm.setTinNumber(rsEditRecord.getString("TIN_NO"));
						custForm.setCstNumber(rsEditRecord.getString("CST_NO"));
						custForm.setPanNumber(rsEditRecord.getString("PAN_No"));
						custForm.setServiceTaxNo(rsEditRecord.getString("Service_Tax_Registration_No"));
						String isRegdExciseVender=rsEditRecord.getString("IS_REGD_EXCISE_VENDOR");
						if(isRegdExciseVender.equalsIgnoreCase("1"))
						{
							custForm.setIsRegdExciseVender("Yes");
							request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
						}
						if(isRegdExciseVender.equalsIgnoreCase("0"))
						{
							custForm.setIsRegdExciseVender("No");
							request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
						}
						custForm.setEccNo(rsEditRecord.getString("ECC_No"));
						custForm.setExciseRegNo(rsEditRecord.getString("Excise_Reg_No"));
						custForm.setExciseRange(rsEditRecord.getString("Excise_Range"));
						custForm.setExciseDivision(rsEditRecord.getString("Excise_Division"));
						custForm.setDlno1(rsEditRecord.getString("DLNO1"));
						custForm.setDlno2(rsEditRecord.getString("DLNO2"));
					
					
						custForm.setCutomerCode(rsEditRecord.getString("cutomer_code"));
						
						 String sapCodeno=rsEditRecord.getString("SAP_CODE_NO");  
							if(sapCodeno!=null){ 
								custForm.setSapCodeNo(sapCodeno);		
						String sapCodeExist=rsEditRecord.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							custForm.setSapCodeExists("Yes");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							custForm.setSapCodeExists("No");
						String sapCreationDate=rsEditRecord.getString("SAP_CREATION_DATE");
						String sapDate[]=sapCreationDate.split(" ");
						sapCreationDate=sapDate[0];
						String sapCode[]=sapCreationDate.split("-");
						sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
						custForm.setSapCreationDate(sapCreationDate);
						custForm.setSapCreatedBy(rsEditRecord.getString("SAP_CREATED_BY"));
						custForm.setRequestedBy(rsEditRecord.getString("REQUESTED_BY"));
							}
						
						
						
						ArrayList fileList = new ArrayList();
						String uploadedFiles=rsEditRecord.getString("Attachments");
						if(uploadedFiles.equalsIgnoreCase(""))
						{
							
						}else{
						String v[] = uploadedFiles.split(",");
						int l = v.length;
							for (int i = 0; i < l; i++) 
							{
								CustomerMasterForm custForm2=new CustomerMasterForm();
							int x=v[i].lastIndexOf("/");
							uploadedFiles=v[i].substring(x+1);		
							custForm2.setFileList(uploadedFiles);
							custForm2.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/");
							fileList.add(custForm2);
							}
						request.setAttribute("listName", fileList);
						}
						
						
						
							
							//currency
							String editRecord2="select cur.ISOCD from CUSTOMER_MASTER_M as c,Currency as cur where REQUEST_NO='"+requstNo1+"'" +
									" and cur.WAERS=c.CURRENCY_ID";
						 		
						 	
								ResultSet rsEditRecord2=ad.selectQuery(editRecord2);
								if(rsEditRecord2.next())
								{
									custForm.setCurrencyId(rsEditRecord2.getString("ISOCD"));
								}
								
								//payment term
								
								String editRecord3="select pay.PAYMENT_TERM_NAME from CUSTOMER_MASTER_M as c,PAYMENT_TERM_M as pay " +
										"where REQUEST_NO='"+requstNo1+"' and pay.PAYMENT_TERM_ID=c.PAYMENT_TERM_ID";
							 		
							 		
							 		ResultSet rsEditRecord3=ad.selectQuery(editRecord3);
									if(rsEditRecord3.next())
									{
										custForm.setPaymentTermID(rsEditRecord3.getString("PAYMENT_TERM_NAME"));
									}
									
									//acc clerk
									
									String editRecord4="select acccl.ACC_CLERK_DESC from CUSTOMER_MASTER_M as c,ACC_CLERK_M as acccl " +
											"where REQUEST_NO='"+requstNo1+"' and acccl.ACC_CLERK_ID=c.ACCOUNT_CLERK_ID";
								 		
								 		
								 		
										ResultSet rsEditRecord4=ad.selectQuery(editRecord4);
										if(rsEditRecord4.next())
										{
											custForm.setAccountClerkId(rsEditRecord4.getString("ACC_CLERK_DESC"));
										}
										
										//tds code
										
										String editRecord5="select tds.TDS_SECTION_DESC from CUSTOMER_MASTER_M as c,TDS_SECTION_M as tds" +
												" where REQUEST_NO='"+requstNo1+"' and tds.TDS_SECTION_ID=c.TDS_CODE";
									 		
									 		
									 		
											ResultSet rsEditRecord5=ad.selectQuery(editRecord5);
											if(rsEditRecord5.next())
											{
												custForm.setTdsCode(rsEditRecord5.getString("TDS_SECTION_DESC"));
												
											}
											
											//country
											String editRecord6="select cou.LANDX from CUSTOMER_MASTER_M as c ,Country as cou " +
													"where REQUEST_NO='"+requstNo1+"'and cou.LAND1=c.COUNTRY_ID ";
								 		
								 		
										ResultSet rsEditRecord6=ad.selectQuery(editRecord6);
										if(rsEditRecord6.next())
										{
											custForm.setCountryId(rsEditRecord6.getString("LANDX"));
											
										}
										
										//state
										String editRecord7=" select sta.BEZEI  from CUSTOMER_MASTER_M as c,State as sta " +
												"where REQUEST_NO='"+requstNo1+"' and sta.BLAND=c.STATE";
							 		
							 								 		
									ResultSet rsEditRecord7=ad.selectQuery(editRecord7);
									if(rsEditRecord7.next())
									{
										custForm.setState(rsEditRecord7.getString("BEZEI"));
										
									}
									
									//customer grp
									String editRecord8="select cust.C_GROUP_NAME from CUSTOMER_MASTER_M as c,Customer_Group as cust " +
											"where REQUEST_NO='"+requstNo1+"' and cust.C_GROUP_ID=c.Customer_Group";
						 		
						 							 		
								ResultSet rsEditRecord8=ad.selectQuery(editRecord8);
								if(rsEditRecord8.next())
								{
									custForm.setCustomerGroup(rsEditRecord8.getString("C_GROUP_NAME"));
									
								}
								
								//price grp
								String editRecord9="select pri.P_GROUP_NAME from CUSTOMER_MASTER_M as c,PRICE_GROUP as pri " +
										"where REQUEST_NO='"+requstNo1+"'and pri.P_GROUP_ID=c.Price_Group";
					 		
					 						 		
							ResultSet rsEditRecord9=ad.selectQuery(editRecord9);
							if(rsEditRecord9.next())
							{
								custForm.setPriceGroup(rsEditRecord9.getString("P_GROUP_NAME"));
								
							}
							//price list
							String editRecord10="select prili.P_LIST_NAME from CUSTOMER_MASTER_M as c,PRICE_LIST as  prili  " +
									"where REQUEST_NO='"+requstNo1+"'and  prili.P_List_ID=c.Price_List";
				 		
				 		
				 		ResultSet rsEditRecord10=ad.selectQuery(editRecord10);
						if(rsEditRecord10.next())
						{
							custForm.setPriceList(rsEditRecord10.getString("P_LIST_NAME"));
							
						}
						
						//tax type
						String editRecord11="select tax.T_CLASS_NAME from CUSTOMER_MASTER_M as c,TAX_CLASS as tax" +
								" where REQUEST_NO='"+requstNo1+"'and  tax.T_CLASS_ID=c.Tax_Type";
			 		
			 		
			 		ResultSet rsEditRecord11=ad.selectQuery(editRecord11);
					if(rsEditRecord11.next())
					{
						custForm.setTaxType(rsEditRecord11.getString("T_CLASS_NAME"));
						
					}												
																	
													
						
						CustDetails.add(custForm);
						request.setAttribute("custdetails", CustDetails);
			 		
					}
					
					String matGroup="";
					String Customer_Type="";
					
					String getMatGroup="select Customer_Type from CUSTOMER_MASTER_M where REQUEST_NO='"+requstNo+"' ";
				 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
				 	while(rsMatGrup.next()){
				 		Customer_Type=rsMatGrup.getString("Customer_Type");
				 		
				 	}
				 	if(Customer_Type.equalsIgnoreCase("Exports"))
				 	{
				 		Customer_Type="Export";
				 	}   
				 	
					
					int checkStatus=0;
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
							"where mast.Location='' AND  mast.Material_Type='Customer Master' AND Material_Group='"+Customer_Type+"' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String approveStatus="";
						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date," +
						"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and " +
						"mast.Location='' AND all_r.type='Customer Master' AND Material_Group='"+Customer_Type+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver " +
						"and (mast.Role='User' or mast.Role='Accounts' or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority," +
						"mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase(""))
							{
								apprvers.setApproveStatus("In Process");
							}
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
							
							
						}
						if(approveStatus.equalsIgnoreCase(""))
						{
							apprvers.setApproveStatus("In Process");
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						listApprers.add(apprvers);
					}
					
					request.setAttribute("approverDetails", listApprers);
					
					

			 		forwardType="CustomerMaster";
					
			     }
				if(type.equalsIgnoreCase("Vendor Master"))
				{

					String accountGroupID="";
			           LinkedList venDetails=new LinkedList();		 

			           VendorMasterRequestForm vendorMasterRequestForm=new VendorMasterRequestForm();
			           
						int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
							
						String getVendorDetails="select  v.REQUEST_NO,v.REQUEST_DATE,acc.ACCOUNT_GROUP_NAME,acccl.ACC_CLERK_DESC,v.VIEW_TYPE,v.Type_Of_Vendor," +
					"v.TITLE,v.NAME,v.ADDRESS1,v.ADDRESS2,v.ADDRESS3,v.ADDRESS4,v.CITY,v.PINCODE,v.LANDLINE_NO," +
					"v.MOBILE_NO,v.FAX_NO,v.EMAIL_ID,cur.ISOCD,rec.RECONCILIATION_ACCOUNT_NAME,  v.IS_ELIGIBLE_FOR_TDS,v.LST_NO,v.TIN_NO" +
					",v.CST_NO, v.IS_APPROVED_VENDOR,v.PAN_No,v.Service_Tax_Registration_No,v.IS_REGD_EXCISE_VENDOR," +
					"v.ECC_No,v.Excise_Reg_No,v.Excise_Range,v.Excise_Division,v.COMMISSIONERATE,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
					"SAP_CREATED_BY,REQUESTED_BY  from vendor_master_m as v ,ACCOUNT_GROUP_M as acc,ACC_CLERK_M as acccl ,Currency as cur," +
					"RECONCILIATION_ACCOUNT_M as rec  where REQUEST_NO='"+requstNo1+"' and acc.ACCOUNT_GROUP_ID=v.ACCOUNT_GROUP_ID and  " +
					" cur.WAERS=v.CURRENCY_ID  and rec.RECONCILIATION_ACCOUNT_ID=v.RECONCILATION_ACT_ID " +
					" and  acccl.ACC_CLERK_ID=v.ACCOUNT_CLERK_ID";
							
							
							ResultSet rs=ad.selectQuery(getVendorDetails);
							if(rs.next())
							{
								vendorMasterRequestForm.setRequestNo(rs.getInt("REQUEST_NO"));
						
							    accountGroupID=rs.getString("ACCOUNT_GROUP_NAME");
							    accountGroupID=accountGroupID.replace(" ","");
								vendorMasterRequestForm.setAccountGroupId(accountGroupID);
							     
								 String reqDate=rs.getString("REQUEST_DATE");
									String a[]=reqDate.split(" ");
									reqDate=a[0];
									String b[]=reqDate.split("-");
									reqDate=b[2]+"/"+b[1]+"/"+b[0];
									 vendorMasterRequestForm.setRequestDate(reqDate);
								 
								 String viewType=rs.getString("VIEW_TYPE");
								 if(viewType.equalsIgnoreCase("1"))
								 {
								vendorMasterRequestForm.setPurchaseView("Purchase View");
								 }
								 if(viewType.equalsIgnoreCase("2"))
								 {
								vendorMasterRequestForm.setAccountView("Account View");
								 }
								 if(viewType.equalsIgnoreCase("3"))
								 {
										vendorMasterRequestForm.setPurchaseView("Purchase View");
								vendorMasterRequestForm.setAccountView("Account View");
								 }

								 vendorMasterRequestForm.setTitle(rs.getString("TITLE"));
								 vendorMasterRequestForm.setName(rs.getString("NAME"));
								 vendorMasterRequestForm.setAddress1(rs.getString("ADDRESS1"));
								vendorMasterRequestForm.setAddress2(rs.getString("ADDRESS2"));
								 vendorMasterRequestForm.setAddress3(rs.getString("ADDRESS3"));
								 vendorMasterRequestForm.setAddress4(rs.getString("ADDRESS4"));
								vendorMasterRequestForm.setCity(rs.getString("CITY"));
								 vendorMasterRequestForm.setPinCode(rs.getString("PINCODE"));
							
								vendorMasterRequestForm.setLandLineNo(rs.getString("LANDLINE_NO"));
								 vendorMasterRequestForm.setMobileNo(rs.getString("MOBILE_NO"));
								 vendorMasterRequestForm.setFaxNo(rs.getString("FAX_NO"));
								 vendorMasterRequestForm.setEmailId(rs.getString("EMAIL_ID"));
								 vendorMasterRequestForm.setCurrencyId(rs.getString("ISOCD"));
								 vendorMasterRequestForm.setReConcillationActId(rs.getString("RECONCILIATION_ACCOUNT_NAME"));
								String elgTds=rs.getString("IS_ELIGIBLE_FOR_TDS");
								if(elgTds.equalsIgnoreCase("1"))
								{
									vendorMasterRequestForm.setElgTds("YES");
								}
								if(elgTds.equalsIgnoreCase("0"))
								{
									vendorMasterRequestForm.setElgTds("No");
								}
							
								 vendorMasterRequestForm.setLstNo(rs.getString("LST_NO"));
								 vendorMasterRequestForm.setTinNo(rs.getString("TIN_NO"));
								vendorMasterRequestForm.setCstNo(rs.getString("CST_NO"));
								 vendorMasterRequestForm.setPanNo(rs.getString("PAN_No"));
								 vendorMasterRequestForm.setServiceTaxRegNo(rs.getString("Service_Tax_Registration_No"));
								 String isRegistVendor=rs.getString("IS_REGD_EXCISE_VENDOR");
								 	if(isRegistVendor.equalsIgnoreCase("1"))
									{
									 vendorMasterRequestForm.setRegExciseVendor("Yes");
									 request.setAttribute("RegExciseVendor", "RegExciseVendor");
									}
									if(isRegistVendor.equalsIgnoreCase("0"))
									{
										
										
									vendorMasterRequestForm.setRegExciseVendor("No");
									}
								 
								 vendorMasterRequestForm.setEccNo(rs.getString("ECC_No"));
								 vendorMasterRequestForm.setExciseRegNo(rs.getString("Excise_Reg_No"));
								 vendorMasterRequestForm.setExciseRange(rs.getString("Excise_Range"));
								 vendorMasterRequestForm.setCommissionerate(rs.getString("COMMISSIONERATE"));
								 vendorMasterRequestForm.setExciseDivision(rs.getString("Excise_Division"));
					
								 vendorMasterRequestForm.setAccountClerkId(rs.getString("ACC_CLERK_DESC"));
								 vendorMasterRequestForm.setIsApprovedVendor(rs.getString("IS_APPROVED_VENDOR"));
								 String isApproveVendor=rs.getString("IS_APPROVED_VENDOR");
								if(isApproveVendor.equalsIgnoreCase("1"))
								{
									vendorMasterRequestForm.setIsApprovedVendor("Yes");
								}	if(isApproveVendor.equalsIgnoreCase("0")){
									vendorMasterRequestForm.setIsApprovedVendor("No");
								}
								 
							
							
								 String typeofVendor=rs.getString("Type_Of_Vendor");
								 if(typeofVendor.equalsIgnoreCase("I"))
									{												
									vendorMasterRequestForm.setTypeOfVendor("IMPORTER");
									}
								 if(typeofVendor.equalsIgnoreCase("MD"))
									{												
									vendorMasterRequestForm.setTypeOfVendor("DEPOT MANUFACTURER");
									}
								 if(typeofVendor.equalsIgnoreCase("M"))
									{												
									vendorMasterRequestForm.setTypeOfVendor("MANUFACTURER");
									}
								 if(typeofVendor.equalsIgnoreCase("D"))
									{												
									vendorMasterRequestForm.setTypeOfVendor("DEALER");
									}
								 if(typeofVendor.equalsIgnoreCase("FD"))
									{												
									vendorMasterRequestForm.setTypeOfVendor("FIRST STAGE DEALER OF INDIGENOUS");
									}
								 if(typeofVendor.equalsIgnoreCase("SD"))
									{												
									vendorMasterRequestForm.setTypeOfVendor("SECOND STAGE DEALER OF INDIGENOUS");
									}
								 String sapCodeno=rs.getString("SAP_CODE_NO");  
									if(sapCodeno!=null){ 
										vendorMasterRequestForm.setSapCodeNo(sapCodeno);		
								String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
								if(sapCodeExist.equalsIgnoreCase("1"))
								{
									vendorMasterRequestForm.setSapCodeExists("Yes");
								}
								if(sapCodeExist.equalsIgnoreCase("0"))
									vendorMasterRequestForm.setSapCodeExists("No");
								String sapCreationDate=rs.getString("SAP_CREATION_DATE");
								String sapDate[]=sapCreationDate.split(" ");
								sapCreationDate=sapDate[0];
								String sapCode[]=sapCreationDate.split("-");
								sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
								vendorMasterRequestForm.setSapCreationDate(sapCreationDate);
								vendorMasterRequestForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
								vendorMasterRequestForm.setRequestedBy(rs.getString("REQUESTED_BY"));
									}
								
							}
							
							
							ArrayList listOfFiles=new ArrayList();
							String getUploadedFiles="select * from temp_vendor_documents where request_no='"+requstNo+"' and userId='"+user.getEmployeeNo()+"' ";
							ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
							while(rsUploadFile.next())
							{
								VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
								vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
								vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
								vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
								listOfFiles.add(vendorMasterRequestForm1);
							}
							request.setAttribute("documentDetails", listOfFiles);
							
							//payment term
							String getVendorDetails4="select pay.PAYMENT_TERM_NAME from vendor_master_m as v,PAYMENT_TERM_M as pay	" +
									"where REQUEST_NO='"+requstNo1+"'and pay.PAYMENT_TERM_ID=v.PAYMENT_TERM_ID";
									
							ResultSet rs4=ad.selectQuery(getVendorDetails4);
							if(rs4.next())
							{
								 vendorMasterRequestForm.setPaymentTermId(rs4.getString("PAYMENT_TERM_NAME"));
							}
							
							
							//tds
							String getVendorDetails1="select tds.TDS_SECTION_DESC from vendor_master_m as v," +
									"TDS_SECTION_M as tds where REQUEST_NO='"+requstNo1+"' and tds.TDS_SECTION_ID=v.TDS_CODE";
									
							ResultSet rs1=ad.selectQuery(getVendorDetails1);
							if(rs1.next())
							{
								 vendorMasterRequestForm.setTdsCode(rs1.getString("TDS_SECTION_DESC"));
							}
							
							//country
							String getVendorDetails2=" select cou.LANDX from vendor_master_m as v,Country as cou " +
									" where REQUEST_NO='"+requstNo1+"' and cou.LAND1=v.COUNTRY_ID ";
							
					ResultSet rs2=ad.selectQuery(getVendorDetails2);
					if(rs2.next())
					{
						 vendorMasterRequestForm.setCountry(rs2.getString("LANDX"));
					}
					//state
					String getVendorDetails3=" select sta.BEZEI from vendor_master_m as v,State as sta " +
							"where REQUEST_NO='"+requstNo1+"' and sta.BLAND=v.STATE";
					
			ResultSet rs3=ad.selectQuery(getVendorDetails3);
			if(rs3.next())
			{
				 vendorMasterRequestForm.setState(rs3.getString("BEZEI"));
			}
							
							
							venDetails.add(vendorMasterRequestForm);
								request.setAttribute("vendetails", venDetails);
								
								
								if(accountGroupID.equalsIgnoreCase("Micro-Staff") || accountGroupID.equalsIgnoreCase("Domestic") || accountGroupID.equalsIgnoreCase("Loan-Licence") || accountGroupID.equalsIgnoreCase("Plants"))
								{
									accountGroupID="Local";
								}
								
								int checkStatus=0;
								LinkedList listApprers=new LinkedList();
								String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
										"where mast.Location='' AND  mast.Material_Type='Vendor Master' AND Material_Group='"+accountGroupID+"' and mast.Approver_Id=emp.PERNR order by Priority";
								ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
								while(rsApprDetails.next())
								{
									checkStatus=1;
									ApprovalsForm apprvers=new ApprovalsForm();
									apprvers.setPriority(rsApprDetails.getString("Priority"));
									apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
									String empCode=rsApprDetails.getString("Approver_Id");
									String approveStatus="";
									String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date," +
									"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and " +
									"mast.Location='' AND all_r.type='Vendor Master' AND Material_Group='"+accountGroupID+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver " +
									"and (mast.Role='User' or mast.Role='Accounts' or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority," +
									"mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
									ResultSet rsAppInfo=ad.selectQuery(recordStatus);
									while(rsAppInfo.next())
									{
										
										apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
										approveStatus=rsAppInfo.getString("Req_Status");
										if(approveStatus.equalsIgnoreCase(""))
										{
											apprvers.setApproveStatus("In Process");
										}
										if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
										{
										apprvers.setDate(rsAppInfo.getString("approved_date"));
										}else{
											apprvers.setDate(rsAppInfo.getString("rejected_date"));
										}
										String comments="";
										comments=rsAppInfo.getString("Comments");
										if(comments.equalsIgnoreCase("null") )
										{
											apprvers.setComments("");
										}else{
											apprvers.setComments(rsAppInfo.getString("Comments"));
										}
										
										
									}
									if(approveStatus.equalsIgnoreCase(""))
									{
										apprvers.setApproveStatus("In Process");
									}
									apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
									listApprers.add(apprvers);
								}
								
								request.setAttribute("approverDetails", listApprers);		
								
								
								
								
								
						 		forwardType="venmaterial";
					
				}
				
				if(type.equalsIgnoreCase("Code Extention"))
				{

					 LinkedList extDetails=new LinkedList();
					 

					 MaterialCodeExtensionForm masterForm=new MaterialCodeExtensionForm();
					 String location="";
					String matType="";	 
						int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
						String reqtype=request.getParameter("planttype");
						String matDetails="select loc.LOCATION_CODE from code_extenstion_request as mat,Location as loc  "
						+ "where mat.REQUEST_NO='"+requstNo+"' and (mat.EXTENDED_TO_PLANT_1=loc.LOCATION_CODE or mat.PLANT_2=loc.LOCATION_CODE) ";
						ResultSet rsDetails=ad.selectQuery(matDetails);
						while(rsDetails.next())
						{
							location=rsDetails.getString("LOCATION_CODE");
							matType="Code Extention";
						}
					
					   if(reqtype.equalsIgnoreCase("Plant"))
						{
						String getRecord="select REQUEST_NO,REQUEST_DATE,TYPE,PLANT_1,MATERIAL_CODE_1,EXTENDED_TO_PLANT_1,STORAGE_LOCATION_ID_1," +
						"EXTENDED_STORAGE_LOCATION_1 from code_extenstion_request as c where REQUEST_NO='"+requstNo+"' ";
						
						ResultSet rs=ad.selectQuery(getRecord);
						while(rs.next())
						{
							masterForm.setRequestNo(rs.getInt("REQUEST_NO"));
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							masterForm.setRequestDate(reqDate);
							masterForm.setPlantType(rs.getString("TYPE"));
						     masterForm.setMaterialCode1(rs.getString("MATERIAL_CODE_1"));
								masterForm.setPlant1(rs.getString("PLANT_1"));
								masterForm.setStorageLocationId1(rs.getString("STORAGE_LOCATION_ID_1"));
								masterForm.setExtendToPlant1(rs.getString("EXTENDED_TO_PLANT_1"));
								masterForm.setExtendToStorageLocation1(rs.getString("EXTENDED_STORAGE_LOCATION_1"));
							
						}
						request.setAttribute("plantMandatory", "plantMandatory");
						}
					   
					   else{
						   
						  String getRecord=" select c.REQUEST_NO,c.REQUEST_DATE,c.TYPE,c.MATERIAL_CODE_2,c.PLANT_2,c.FROM_STORAGE_LOCATION,c.TO_STORAGE_LOCATION " +
						  		" from code_extenstion_request as c where REQUEST_NO='"+requstNo+"' ";
							
							ResultSet rs=ad.selectQuery(getRecord);
							if(rs.next()){
								
							masterForm.setRequestNo(rs.getInt("REQUEST_NO"));
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							masterForm.setRequestDate(reqDate);
							masterForm.setPlantType(rs.getString("TYPE"));
							masterForm.setPlant2(rs.getString("PLANT_2"));
							masterForm.setMaterialCode2(rs.getString("MATERIAL_CODE_2"));
						    masterForm.setFromStorageLocation(rs.getString("FROM_STORAGE_LOCATION"));
							masterForm.setToStorageLocation(rs.getString("TO_STORAGE_LOCATION"));
					   }
							request.setAttribute("storageMandatory", "storageMandatory");
							}
							
						   extDetails.add(masterForm);
							request.setAttribute("extdetails", extDetails);
							
							int checkStatus=0;
							LinkedList listApprers=new LinkedList();
							String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
									"where mast.Location='"+location+"' AND  mast.Material_Type='Code Extention' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
							ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
							while(rsApprDetails.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(rsApprDetails.getString("Priority"));
								apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
								String empCode=rsApprDetails.getString("Approver_Id");
								String approveStatus="";
								String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date," +
								"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and " +
								"mast.Location='"+location+"' AND all_r.type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver " +
								"and (mast.Role='User' or mast.Role='Accounts' or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority," +
								"mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								while(rsAppInfo.next())
								{
									
									apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
									approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase(""))
									{
										apprvers.setApproveStatus("In Process");
									}
									if(approveStatus.equalsIgnoreCase("Approved"))
									{
									apprvers.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvers.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments.equalsIgnoreCase("null") )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo.getString("Comments"));
									}
								}
								if(approveStatus.equalsIgnoreCase(""))
								{
									apprvers.setApproveStatus("In Process");
								}
								apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
								listApprers.add(apprvers);
							}
							
							request.setAttribute("approverDetails", listApprers);
							
							
					 		forwardType="extmaterial";
				
				}
		if(type.equalsIgnoreCase("Leave")){
			String sql="select emp.EMP_FULLNAME,desg.DSGSTXT,leave.leave_type,leave.submit_date,leave.start_duration,leave.end_duration,leave.start_date,leave.end_date,leave.reason,leave.reasonType,leave.no_of_days,leave.documents,leave.Approvel_Status,leave.approved_date,leave.rejected_date" +
			" from leave_details as leave,emp_official_info as emp,DESIGNATION as desg where leave.Req_Id='"+requstNo+"' and leave.firstname='"+user.getEmployeeNo()+"' and leave.Approver_id=emp.PERNR and emp.DSGID=desg.DSGID";
			ResultSet rs = ad.selectQuery(sql);
			String leaveType="";
			String userId="";
			LinkedList leaveDetails=new LinkedList();
			LeaveForm leaveForm=new LeaveForm();
			while (rs.next()) {
				leaveType=rs.getString("leave_type");
				if(leaveType.equalsIgnoreCase("1")){
					leaveType="Casual";
				}
				if(leaveType.equalsIgnoreCase("2")){
					leaveType="SICK";
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
				leaveForm.setStartDurationType(rs.getString("start_duration"));
				leaveForm.setEndDurationType(rs.getString("end_duration"));
				leaveForm.setStartDate(EMicroUtils.display1(rs.getDate("start_date")));
				leaveForm.setEndDate(EMicroUtils.display1(rs.getDate("end_date")));
				leaveForm.setReason(rs.getString("reason"));
				leaveForm.setReasonType(rs.getString("reasonType"));
				leaveForm.setNoOfDays(rs.getString("no_of_days"));
				leaveForm.setDocumentName(rs.getString("documents"));
			
			}
			
			String getComments="select Comments from All_Request where Req_Id='"+requstNo+"' and Req_Type='Leave' and Requester_Name='"+user.getEmployeeNo()+"'";
			ResultSet rsComments=ad.selectQuery(getComments);
			while(rsComments.next()){
				leaveForm.setComments(rsComments.getString("Comments"));
			}
			
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
			"where employeeNumber='"+user.getEmployeeNo()+"' and  e.ApproverId=emp.PERNR and emp.DSGID=desg.DSGID and essType='Leave' order by Priority";
				 
				ResultSet rsA=ad.selectQuery(getApproverDesig);
					while(rsA.next())
					{
						LeaveForm lvform=new LeaveForm();
						 lvform.setApprover(rsA.getString("EMP_FULLNAME"));	
						 lvform.setAppDesig(rsA.getString("DSGSTXT"));	
						String getdetails=" select all_R.Comments,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Hr_Id,all_R.approved_date," +
								"all_R.rejected_date from 	All_Request as all_R,emp_official_info as emp, DESIGNATION as desg   " +
								" where all_R.Req_Id='"+requstNo+"' and all_R.Req_Type='Leave' and ((emp.PERNR=all_R.Last_Approver) or " +
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
					
					String actual="select a.*,e.EMP_FULLNAME,d.DSGSTXT from All_Request a,emp_official_info e,DESIGNATION d where Req_Id='"+requstNo+"' and Req_Type='Leave'  and e.PERNR=a.Actual_Approver and e.DSGID=d.DSGID  ";
					ResultSet rsact=ad.selectQuery(actual);
					while(rsact.next())
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
					
					String getApproverDesig="  select all_R.Comments,all_R.Hr_Id,desg.DSGSTXT,emp.EMP_FULLNAME,all_R.Req_Status,all_R.approved_date,all_R.rejected_date from " +
							"All_Request as all_R,emp_official_info as emp, DESIGNATION as desg    where all_R.Req_Id='"+requstNo+"' and all_R.Req_Type='Leave' and " +
							"((emp.PERNR=all_R.Last_Approver) or (emp.PERNR=all_R.Pending_Approver))  and (emp.DSGID=desg.DSGID) ";
					    ResultSet rsApproverDsg=ad.selectQuery(getApproverDesig);
						while(rsApproverDsg.next())
						{
							
							HRid=rsApproverDsg.getString("Hr_Id");
							LeaveForm lvform=new LeaveForm();
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
								
								ResultSet rs2=ad.selectQuery(hrdetails);
								
								while(rs2.next())
								{
									lvform.setApprover(rs2.getString("EMP_FULLNAME"));
									 lvform.setAppDesig(rs2.getString("DSGSTXT"));
								}
							}
								
							}
							
							
							paralist.add(lvform);
							
						}
						
						///Actual approver 
						
						String actual="select a.*,e.EMP_FULLNAME,d.DSGSTXT from All_Request a,emp_official_info e,DESIGNATION d where Req_Id='"+requstNo+"' and Req_Type='Leave'  and e.PERNR=a.Actual_Approver and e.DSGID=d.DSGID  ";
						ResultSet rsact=ad.selectQuery(actual);
						while(rsact.next())
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
						
						
						
				}
				request.setAttribute("parallelapprovers", paralist);
				
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
			
	
			leaveDetails.add(leaveForm);
			String sql1="select * from leave_details where Req_Id='"+requstNo+"' and firstname='"+user.getEmployeeNo()+"' and documents not like ''";
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
				request.setAttribute("leaveDetails", leaveDetails);
				request.setAttribute("leave", "leave");
				
				forwardType="myLeave";
			
	
		}
			
	
		if(type.equalsIgnoreCase("Service Master"))
	     {

            LinkedList SerDetails=new LinkedList();
            String location="";
	 		
        	ServiceMasterRequestForm sform6=new ServiceMasterRequestForm();
	 		
	 		int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
	 		
	 		String query="select ser.request_date,loc.LOCNAME,loc.LOCATION_CODE,ser.service_description,ser.detailed_desc,u.LTXT," +
	 				"pur.PURCHASE_GROUP_DESC,ser.service_catagory,se.STXT,ser.machine_name,ser.app_value,ser.justification," +
	 				"val.VALUATION_DESC,ser.attachment,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
			 	"SAP_CREATED_BY,REQUESTED_BY from SERVICE_MASTER as ser,Location as loc,UNIT_MESUREMENT as u," +
	 				"PURCHASE_GROUP as pur, SERVICE_GROUP as se,VALUATION_CLASS as val where REQUEST_NO='"+requstNo+"' and	" +
	 				"loc.LOCID=ser.plant_code and u.UNIT_OF_MEAS_ID=ser.uom and pur.PURCHASE_GROUP_ID=ser.purchase_group " +
	 				"and se.group_id=ser.service_group and val.VALUATION_ID=ser.valuation_class";
		
	 		System.out.println(query);
			
				ResultSet rs=ad.selectQuery(query);
				if(rs.next()){
					sform6.setR_no(requstNo1);
					sform6.setPlantCode(rs.getString("LOCNAME"));
					location=rs.getString("LOCATION_CODE");
			        sform6.setRequestDate(rs.getString("request_date"));
					sform6.setServiceDescription(rs.getString("service_description"));
					sform6.setDetailedServiceDescription(rs.getString("detailed_desc"));
					sform6.setUom(rs.getString("LTXT"));
					sform6.setPurchaseGroup(rs.getString("PURCHASE_GROUP_DESC"));
					String sercat=rs.getString("service_catagory");
					
					
					
					if(sercat.equalsIgnoreCase("ZITA"))
					{
						sform6.setServiceCatagory("ZITA- AMC-IT");
						
					}
					if(sercat.equalsIgnoreCase("ZAMC"))
					{
						sform6.setServiceCatagory("ZAMC- Annual maintainence");
						
					}
					if(sercat.equalsIgnoreCase("ZCLB"))
					{
						sform6.setServiceCatagory("ZCLB- Calibration");
						
					}
					if(sercat.equalsIgnoreCase("ZCIV"))
					{
						sform6.setServiceCatagory("ZCIV- Civil works");
						
					}
					if(sercat.equalsIgnoreCase("ZMNT"))
					{
						sform6.setServiceCatagory("ZMNT- Maintainence");
						
					}
					if(sercat.equalsIgnoreCase("ZITM"))
					{
						sform6.setServiceCatagory("ZITM-MAINAINENCE-IT");
						
					}
					if(sercat.equalsIgnoreCase("ZMKT"))
					{
						sform6.setServiceCatagory("ZMKT- Marketing");
						
					}
					if(sercat.equalsIgnoreCase("ZTST"))
					{
						sform6.setServiceCatagory("ZTST- Testing&Analysis");
						
					}
					if(sercat.equalsIgnoreCase("ZTRC"))
					{
						sform6.setServiceCatagory("ZTRC- Training&Recruitment");
						
					}

					
					
					sform6.setServiceGroup(rs.getString("STXT"));
					sform6.setE_m_name(rs.getString("machine_name"));
					sform6.setApp_amount(rs.getString("app_value"));
				
					sform6.setJustification(rs.getString("justification"));
					sform6.setValuationClass(rs.getString("VALUATION_DESC"));
				
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null){
						sform6.setSapCodeNo(sapCodeno);		
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					sform6.setSapCodeExists("Yes");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					sform6.setSapCodeExists("No");
				String sapCreationDate=rs.getString("SAP_CREATION_DATE");
				String sapDate[]=sapCreationDate.split(" ");
				sapCreationDate=sapDate[0];
				String sapCode[]=sapCreationDate.split("-");
				sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
				sform6.setSapCreationDate(sapCreationDate);
				sform6.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				sform6.setRequestedBy(rs.getString("REQUESTED_BY"));
					}
						
															
				
				
				SerDetails.add(sform6);
				
				
				String query_file="select file_name from service_documents where request_no='"+requstNo1+"'";
				ArrayList documentDetails=new ArrayList();
				ResultSet rs_file=ad.selectQuery(query_file);
				try{
				while(rs_file.next()){
					ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
					sf.setSfile(rs_file.getString("file_name"));
					sf.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/"+rs_file.getString("file_name")+"");
					documentDetails.add(sf);
				}
				
				
				}
				catch(Exception e){
					e.printStackTrace();
				}
				request.setAttribute("documentDetails", documentDetails);
				request.setAttribute("serdetails", SerDetails);
				
				
				
				//set ApproverDetails 
				
				
				
				
				
		 		forwardType="sermaterial";
				}
	     		
				int checkStatus=0;
				
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='Service Master' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String approveStatus="";
					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date," +
					"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and " +
					"mast.Location='"+location+"' AND all_r.type='Service Master' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver " +
					"and (mast.Role='User' or mast.Role='Accounts' or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority," +
					"mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase(""))
						{
							apprvers.setApproveStatus("In Process");
						}
						
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							String approveDate=	rsAppInfo.getString("approved_date");
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(approveDate);
						}else{
							String rejectDate=	rsAppInfo.getString("rejected_date");
							
							String a[]=rejectDate.split(" ");
							rejectDate=a[0];
							String b[]=rejectDate.split("-");
							rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(rejectDate);
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
			
						
						
					}
					if(approveStatus.equalsIgnoreCase(""))
					{
						apprvers.setApproveStatus("In Process");
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					listApprers.add(apprvers);
				}
				
				request.setAttribute("approverDetails", listApprers);
	
				forwardType="sermaterial";
	
	     }
		
		if(type.equalsIgnoreCase("RM")){
			LinkedList rawDetails=new LinkedList();

			
			RawMaterialForm masterForm=new RawMaterialForm();

			
			int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
			
			
			String getMaterial="select  mat.REQUEST_NO,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME," +
			" mat.MATERIAL_SHORT_NAME,mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, mat.PHARMACOP_NAME," +
			"mat.PHARMACOP_GRADE,mat.GENERIC_NAME,mat.SYNONYM,mat.PHARMACOP_SPECIFICATION,mat.DUTY_ELEMENT, mat.IS_DMF_MATERIAL," +
			"dmf.DMF_GRADE_DESC,mat.MATERIAL_GRADE,mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST, mat.IS_VENDOR_SPECIFIC_MATERIAL," +
			"mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX,mat.CUSTOMER_NAME, mat.TO_BE_USED_IN_PRODUCTS,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE," +
			"mat.RETEST_DAYS ,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC,mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS, " +
			"SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP " +
			"as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,DMF_GRADE as dmf,Country as cou, VALUATION_CLASS as val " +
			" where REQUEST_NO='"+requstNo+"' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and " +
			"matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID " +
			"and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID and val.VALUATION_ID=mat.VALUATION_CLASS";	
			
			ResultSet rs=ad.selectQuery(getMaterial);
			while(rs.next())
			{

				masterForm.setRequestNo(requstNo1);
				String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				masterForm.setRequestDate(reqDate);
				masterForm.setLocationId(rs.getString("LOCNAME"));
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
				
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("STXT"));
				masterForm.setPharmacopName(rs.getString("PHARMACOP_NAME"));
			    String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
				masterForm.setPharmacopGrade(pharmacopGrade);
				
				masterForm.setGenericName(rs.getString("GENERIC_NAME"));
				masterForm.setSynonym(rs.getString("SYNONYM"));
				masterForm.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
				String isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
				if(isDMfMaterial.equalsIgnoreCase("1"))
				{
					masterForm.setIsDMFMaterial("YES");
				
					masterForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
					masterForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
					masterForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					
				}
				masterForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
				if(isDMfMaterial.equalsIgnoreCase("0"))
				{
					masterForm.setIsDMFMaterial("NO");
					masterForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
					masterForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
					masterForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					
				}
				
				masterForm.setCountryId(rs.getString("LANDX"));
				masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				masterForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
				
				String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
				if(isVendorStatus.equalsIgnoreCase("1"))
				{
					masterForm.setIsVendorSpecificMaterial("YES");
					masterForm.setMfgrName(rs.getString("MFGR_NAME"));
					masterForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				}
				if(isVendorStatus.equalsIgnoreCase("0"))
				{
					masterForm.setIsVendorSpecificMaterial("NO");
				}
				
				
				masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
				String dutyElement=rs.getString("DUTY_ELEMENT");
				if(dutyElement.equalsIgnoreCase("0"))
				{
					masterForm.setDutyElement("0-Duty Exempted Raw Materials for Finished product");
				}
				if(dutyElement.equalsIgnoreCase("2")){
					masterForm.setDutyElement("2-Indigenous Material with or without Cenvat");
				}
				masterForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
				masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
				masterForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
				masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
				masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				masterForm.setUnitOfMeasId(rs.getString("LTXT"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
			
			
				
				ArrayList fileList = new ArrayList();
				String uploadedFiles=rs.getString("Attachements");
				if(uploadedFiles.equalsIgnoreCase(""))
				{
					
				}else{
				String v[] = uploadedFiles.split(",");
				int l = v.length;
				for (int i = 0; i < l; i++) 
				{
					RawMaterialForm materialForm2=new RawMaterialForm();
					//String url=v[i];
					//materialForm2.setUrl(url);
				System.out.println(v[i]);
					materialForm2.setUploadFilePath(v[i]);
				int x=v[i].lastIndexOf("/");
				uploadedFiles=v[i].substring(x+1);		
				materialForm2.setFileList(uploadedFiles);
				
				fileList.add(materialForm2);
				}
				request.setAttribute("listName", fileList);
				
				String sapCodeno=rs.getString("SAP_CODE_NO");
				if(sapCodeno!=null)
				{
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
					String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
					{
						masterForm.setSapCodeExists("Yes");
					}
					if(sapCodeExist.equalsIgnoreCase("0"))
						masterForm.setSapCodeExists("No");
					String createDate=rs.getString("SAP_CREATION_DATE");
					String a1[]=createDate.split(" ");
					createDate=a1[0];
					String b1[]=createDate.split("-");
					createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					masterForm.setSapCreationDate(createDate);
					masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				}
				
				
			}
			
			
				
				
			}
			String getMaterial1="select tem.TEMP_CON_DESC from material_code_request as mat,TEMP_CONDITION as tem " +
			" where REQUEST_NO='"+requstNo+"' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
	
	ResultSet rs1=ad.selectQuery(getMaterial1);
	while(rs1.next())
	{
		masterForm.setTempCondition(rs1.getString("TEMP_CON_DESC"));
	}
	
	//STORAGE_CONDITION
	
	String getMaterial2="select stcon.LTXT from material_code_request as mat,STORAGE_CONDITION as stcon" +
			" where REQUEST_NO='"+requstNo+"'  and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
	
	ResultSet rs2=ad.selectQuery(getMaterial2);
	while(rs2.next())
	{
		masterForm.setStorageCondition(rs2.getString("LTXT"));
	}
	
	
	rawDetails.add(masterForm);
			
			request.setAttribute("rawdetails", rawDetails);
			forwardType="rawmaterial";
		
			String location="";
			String matGroup="";
			String matDetails="select loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+requstNo+"' and mat.LOCATION_ID=loc.LOCID";
			ResultSet rsDetails=ad.selectQuery(matDetails);
			if(rsDetails.next())
			{
				location=rsDetails.getString("LOCATION_CODE");
				matGroup=rsDetails.getString("MATERIAL_GROUP_ID");
			}
			
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
			"where mast.Location='"+location+"' AND  mast.Material_Type='RM' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			while(rsApprDetails.next())
			{
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				String actualapprover="";
				boolean data=false;
				
				String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
				"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
				"mast.Material_Type='RM' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
				"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
				"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					
					actualapprover=rsAppInfo.getString("Actual_Approver");

					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
				     if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
					{
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments="";
					comments=rsAppInfo.getString("Comments");
					if(comments=="null" )
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				String pernr="";

							if(data==true)
							{
								pernr=actualapprover;
							}
							else
							{
								pernr=rsApprDetails.getString("Approver_Id");
							}						
						
						
					String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
					ResultSet rsname=ad.selectQuery(name);
					if(rsname.next())
					{
						apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
					}
				listApprers.add(apprvers);
			}
			if(checkStatus==0)
			{
				getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='RM' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
				rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='RM' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						actualapprover=rsAppInfo.getString("Actual_Approver");
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(approveDate);
						}else{
							String rejectDate=	rsAppInfo.getString("rejected_date");
							
							String a[]=rejectDate.split(" ");
							rejectDate=a[0];
							String b[]=rejectDate.split("-");
							rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(rejectDate);
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					String pernr="";

					if(data==true)
									{
										pernr=actualapprover;
									}
									else
									{
										pernr=rsApprDetails.getString("Approver_Id");
									}						
							
							
						String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
						ResultSet rsname=ad.selectQuery(name);
						if(rsname.next())
						{
							apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
						}
					listApprers.add(apprvers);
			}
			}
			request.setAttribute("approverDetails", listApprers);
			
			
	
	
		}	

		 if(type.equalsIgnoreCase("PM")){
				LinkedList pacDetails=new LinkedList();

				
				PackageMaterialMasterForm materialForm=new PackageMaterialMasterForm();
				
				int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
				String matType="";
				String matGroup="";
				 String location="";
			
				 String getMaterial="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
					"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.DUTY_ELEMENT,pacgrp.PACKING_MATERIAL_GROUP_NAME," +
					"mat.Type_Of_Material,mat.ARTWORK_NO,mat.IS_ARTWORK_REVISION,mat.EXISTING_SAP_ITEM_CODE,mat.IS_DMF_MATERIAL,dmf.DMF_GRADE_DESC," +
					"mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST,mat.IS_VENDOR_SPECIFIC_MATERIAL,mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX," +
					"mat.CUSTOMER_NAME,mat.TO_BE_USED_IN_PRODUCTS,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC," +
					"mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc," +
					"MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,PACKAGE_MATERIAL_GROUP as pacgrp,DMF_GRADE as dmf," +
					"Country as cou,VALUATION_CLASS as val where REQUEST_NO='"+requstNo+"' " +
					"and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID" +
					" and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID  and pacgrp.PACKING_MATERIAL_GROUP_ID=mat.PACKING_MATERIAL_GROUP " +
					" and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID  " +
					" and val.VALUATION_ID=mat.VALUATION_CLASS";
					
					
					
					ResultSet rs=ad.selectQuery(getMaterial);
					
						if(rs.next())
					{

							materialForm.setRequestNo(requstNo1);
							matType=rs.getString("Type");
							matGroup=rs.getString("MATERIAL_GROUP_ID");
							location=rs.getString("LOCATION_CODE");
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							materialForm.setRequestDate(reqDate);
							materialForm.setLocationId(rs.getString("LOCNAME"));
							materialForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						
							materialForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							materialForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							materialForm.setMaterialGroupId(rs.getString("STXT"));
							String isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
							if(isDMFMaterial.equalsIgnoreCase("1"))
							{
								materialForm.setIsDMFMaterial("YES");
								request.setAttribute("dmfMandatory", "dmfMandatory");
								materialForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
								materialForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
							}
							if(isDMFMaterial.equalsIgnoreCase("0")){
								materialForm.setIsDMFMaterial("NO");
								request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
							}
							materialForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
							
							materialForm.setCountryId(rs.getString("LANDX"));
							materialForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
							materialForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
							String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
							if(isVendorStatus.equalsIgnoreCase("1"))
							{
								materialForm.setIsVendorSpecificMaterial("YES");
								request.setAttribute("vedorMandatory", "vedorMandatory");
								materialForm.setMfgrName(rs.getString("MFGR_NAME"));
								materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
							}
							if(isVendorStatus.equalsIgnoreCase("0"))
							{
								materialForm.setIsVendorSpecificMaterial("NO");
								request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
							}
								materialForm.setMfgrName(rs.getString("MFGR_NAME"));
							materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
							
							materialForm.setRetestDays(rs.getString("RETEST_DAYS"));
							materialForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
							String dutyElement=rs.getString("DUTY_ELEMENT");
							if(dutyElement.equalsIgnoreCase("0"))
							{
								materialForm.setDutyElement("0-Indigenous Material with or without Cenvat");
							}
							if(dutyElement.equalsIgnoreCase("1"))
								materialForm.setDutyElement("1-Duty Exempted Packing Materials for Finished product");
							materialForm.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP_NAME"));
							materialForm.setTypeOfMaterial(rs.getString("Type_Of_Material"));
							String typeOfMaterial=rs.getString("Type_Of_Material");
							
							if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
							{
								request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
								materialForm.setArtworkNo(rs.getString("ARTWORK_NO"));
								String isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
								if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
								{
									materialForm.setIsArtworkRevision("YES");
								}
								if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
									materialForm.setIsArtworkRevision("NO");
								
							}
							else
								request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
							
							materialForm.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
							materialForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						
							
							ArrayList fileList = new ArrayList();
							String uploadedFiles=rs.getString("Attachements");
							materialForm.setUnitOfMeasId(rs.getString("LTXT"));
							materialForm.setValuationClass(rs.getString("VALUATION_DESC"));
							materialForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
							if(uploadedFiles.equalsIgnoreCase(""))
							{
							}else{
							String v[] = uploadedFiles.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) 
							{
								PackageMaterialMasterForm materialForm2=new PackageMaterialMasterForm();
							int x=v[i].lastIndexOf("/");
							uploadedFiles=v[i].substring(x+1);		
							materialForm2.setFileList(uploadedFiles);
							materialForm2.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/"+uploadedFiles+"");
							fileList.add(materialForm2);
							}
							request.setAttribute("listName", fileList);
							}
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null)
							{
								materialForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
								if(sapCodeExist.equalsIgnoreCase("1"))
								{
									materialForm.setSapCodeExists("Yes");
								}
								if(sapCodeExist.equalsIgnoreCase("0"))
									materialForm.setSapCodeExists("No");
								String createDate=rs.getString("SAP_CREATION_DATE");
								String a1[]=createDate.split(" ");
								createDate=a1[0];
								String b1[]=createDate.split("-");
								createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
								materialForm.setSapCreationDate(createDate);
								materialForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
							}
							
					}
						//temp cond
						
						String getMaterial1="select tem.TEMP_CON_DESC from material_code_request as mat,TEMP_CONDITION as tem " +
								" where REQUEST_NO='"+requstNo+"' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
						
						ResultSet rs1=ad.selectQuery(getMaterial1);
						if(rs1.next())
						{
							materialForm.setTempCondition(rs1.getString("TEMP_CON_DESC"));
						}
						
						//STORAGE_CONDITION
						
						String getMaterial2="select stcon.LTXT from material_code_request as mat,STORAGE_CONDITION as stcon" +
								" where REQUEST_NO='"+requstNo+"'  and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
						
						ResultSet rs2=ad.selectQuery(getMaterial2);
						if(rs2.next())
						{
							materialForm.setStorageCondition(rs2.getString("LTXT"));
						}
						pacDetails.add(materialForm);
					
				
			
			request.setAttribute("pacdetails", pacDetails);
			
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
			"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			while(rsApprDetails.next())
			{
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				String actualapprover="";
				boolean data=false;
				
				String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
				"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
				"mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
				"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
				"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					actualapprover=rsAppInfo.getString("Actual_Approver");
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
						data=true;
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
					{
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments="";
					comments=rsAppInfo.getString("Comments");
					if(comments=="null" )
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				
		String pernr="";

	if(data==true)
					{
						pernr=actualapprover;
					}
					else
					{
						pernr=rsApprDetails.getString("Approver_Id");
					}						
			
			
		String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
		ResultSet rsname=ad.selectQuery(name);
		if(rsname.next())
		{
			apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
		}
				listApprers.add(apprvers);
				request.setAttribute("approverDetails", listApprers);
			}
			if(checkStatus==0)
			{

				getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
				rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+type+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						
						actualapprover=rsAppInfo.getString("Actual_Approver");
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(approveDate);
						}else{
							String rejectDate=	rsAppInfo.getString("rejected_date");
							
							String a[]=rejectDate.split(" ");
							rejectDate=a[0];
							String b[]=rejectDate.split("-");
							rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(rejectDate);
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					
					String pernr="";

				if(data==true)
								{
									pernr=actualapprover;
								}
								else
								{
									pernr=rsApprDetails.getString("Approver_Id");
								}						
						
						
					String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
					ResultSet rsname=ad.selectQuery(name);
					if(rsname.next())
					{
						apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
					}
					listApprers.add(apprvers);
					request.setAttribute("approverDetails", listApprers);
			}
			
			}
			
			forwardType="pacmaterial";
		}
		     
		     if(type.equalsIgnoreCase("BULK"))
		     {
		 		LinkedList semfDetails=new LinkedList();

		 		
		 		SemiFinishedForm masterForm=new SemiFinishedForm();
		 		
		 		int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
		        String matType="";
		        String matGroup="";
		        String location="";
		 		String getSemiFinished="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
		 				"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pac.PACK_SIZE_DESC,mat.CUSTOMER_NAME,mat.SHELF_LIFE," +
		 				"mat.SHELF_LIFE_TYPE,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ,mat.TARGET_WEIGHT," +
		 				"wei.W_UOM_DESC,val.VALUATION_DESC,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as " +
		 				"stLoc,MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PACK_SIZE as pac,WEIGHT_UOM as wei,VALUATION_CLASS" +
		 				" as val where REQUEST_NO='"+requstNo+"' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID " +
		 				"and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID	" +
		 				"and pac.PACK_SIZE_CODE=mat.PACK_SIZE and wei.W_UOM_CODE=mat.WEIGHT_UOM " +
		 				"and val.VALUATION_ID=mat.VALUATION_CLASS";
				ResultSet rs=ad.selectQuery(getSemiFinished);
				if(rs.next())
				{
					masterForm.setRequestNo(requstNo1);
					matType=rs.getString("Type");
					matGroup=rs.getString("MATERIAL_GROUP_ID");
					location=rs.getString("LOCATION_CODE");
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					masterForm.setRequestDate(reqDate);
					
					masterForm.setLocationId(rs.getString("LOCNAME"));
					
					masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
					masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					masterForm.setMaterialGroupId(rs.getString("STXT"));
					
			 		masterForm.setUnitOfMeasId(rs.getString("LTXT"));
			 		masterForm.setPackSize(rs.getString("PACK_SIZE_DESC"));
			 	
			 		masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
			 		masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
			 		masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
			 		masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
			 		masterForm.setBatchCode(rs.getString("BATCH_CODE"));
			 		masterForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
			 		
			 		
			 		masterForm.setWeightUOM(rs.getString("W_UOM_DESC"));
			 		masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
			 		masterForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
			 		masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
			 		
			 		
			
					
					
					
			 		request.setAttribute("semidetails", semfDetails);
			 		
			 		String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							masterForm.setSapCodeExists("Yes");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							masterForm.setSapCodeExists("No");
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						masterForm.setSapCreationDate(createDate);
						masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
			 		
			 		
			 		
			 		
			 		
			 		
			 		forwardType="semimaterial";

			 	}
				
				//country
				String editRecord1="select cou.LANDX from material_code_request as m ,Country as cou " +
						"where REQUEST_NO='"+requstNo+"'and cou.LAND1=m.COUNTRY_ID ";
	 		
	 		
			ResultSet rs1=ad.selectQuery(editRecord1);
			if(rs1.next())
			{
				masterForm.setCountryId(rs1.getString("LANDX"));
				
			}
				
				semfDetails.add(masterForm);
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{

						actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments=="null" )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					String pernr="";

					if(data==true)
									{
										pernr=actualapprover;
									}
									else
									{
										pernr=rsApprDetails.getString("Approver_Id");
									}						
							
							
						String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
						ResultSet rsname=ad.selectQuery(name);
						if(rsname.next())
						{
							apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
						}
					listApprers.add(apprvers);
					request.setAttribute("approverDetails", listApprers);
				}
				if(checkStatus==0)
				{

					getApprDetails="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;
						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+type+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
								String approveDate=	rsAppInfo.getString("approved_date");
								
								String a[]=approveDate.split(" ");
								approveDate=a[0];
								String b[]=approveDate.split("-");
								approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(approveDate);
							}else{
								String rejectDate=	rsAppInfo.getString("rejected_date");
								
								String a[]=rejectDate.split(" ");
								rejectDate=a[0];
								String b[]=rejectDate.split("-");
								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(rejectDate);
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
				}
				
				}
				
				
				
		     }
					
				if(type.equalsIgnoreCase("FG")||type.equalsIgnoreCase("HAWA"))
			     {
			 		LinkedList finDetails=new LinkedList();

			 		String matType="";
			 		String matGroup="";
			 		 String location="";
			 		FinishedProductForm masterForm=new FinishedProductForm();
			 		
			 		int requstNo1=Integer.parseInt(request.getParameter("requstNo"));	
					
					
			 		String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,mat.MANUFACTURED_AT," +
			 			 	  "mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,mat.DOMESTIC_OR_EXPORTS,cou.LANDX,mat.CUSTOMER_NAME," +
			 			 	  "mat.SALEABLE_OR_SAMPLE,mat.SALES_PACK_ID,pac.P_TYPE_DESC,sal.S_UOM_DESC,div.DIV_DESC,the.THER_SEG_DESC,bran.BRAND_DESC, " +
			 			 	  "st.STRENGTH_DESC,gen.GEN_NAME_DESC,mat.GROSS_WEIGHT,mat.NET_WEIGHT,mat.DIMENSION," +
			 			 	  "mat.Material_Pricing,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ," +
			 			 	  "SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,MATERIAL_GROUP as matGroup ," +
			 			 	  "UNIT_MESUREMENT AS uom,Country as cou,PACK_TYPE as pac,SALES_UOM as sal,DIVISION as div,THERAPEUTIC_SEGMENT as the,BRAND as bran," +
			 			 	  "STRENGTH as st,GENERIC_NAME as gen " +
			 			 	  "where REQUEST_NO='"+requstNo1+"' and loc.LOCID=mat.LOCATION_ID  and " +
			 			 	  "cou.LAND1=mat.COUNTRY_ID and pac.P_TYPE_CODE=mat.PACK_TYPE_ID and sal.S_UOM_CODE=mat.SALES_UNIT_OF_MEAS_ID and " +
			 			 	  "uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID " +
			 			 	  "and the.THER_SEG_CODE=mat.THERAPEUTIC_SEGMENT_ID and bran.BRAND_CODE=mat.BRAND_ID and st.STRENGTH_CODE=mat.STRENGTH_ID" +
			 			 	  " and gen.GEN_NAME_CODE=mat.GENERIC_NAME    ";
			 		ResultSet rs=ad.selectQuery(getFinishedProduct);
					if(rs.next())
					{
						masterForm.setRequestNo(requstNo1);
						matType=rs.getString("Type");
						masterForm.setLocationId(rs.getString("LOCNAME"));
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");
						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						
						
						
						String manufacturedAt=rs.getString("MANUFACTURED_AT");
						
						if(manufacturedAt.equalsIgnoreCase("Third Party"))
						{
							request.setAttribute("standardBathcNotMandatory", "standardBathcNotMandatory");
							request.setAttribute("manufactureMandatory", "manufactureMandatory");
						}
						else{
							request.setAttribute("standardBathcMandatory", "standardBathcMandatory");
							request.setAttribute("manufactureNotMandatory", "manufactureNotMandatory");
						}
						
						masterForm.setManufacturedAt(manufacturedAt);
						
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						
						masterForm.setCountryId(rs.getString("LANDX"));
						masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
						masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
						masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
						masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
						masterForm.setBatchCode(rs.getString("BATCH_CODE"));
						String salorsam=rs.getString("SALEABLE_OR_SAMPLE");
						if(salorsam.equalsIgnoreCase("1")){
							masterForm.setSaleableOrSample("Saleable");
							}
							if(salorsam.equalsIgnoreCase("2")){
								masterForm.setSaleableOrSample("Sample");
								}
						String domorexp=rs.getString("DOMESTIC_OR_EXPORTS");
						if(domorexp.equalsIgnoreCase("D")){
						masterForm.setDomesticOrExports("DOMESTIC");
						}
						if(domorexp.equalsIgnoreCase("E")){
							masterForm.setDomesticOrExports("EXPORTS");
							}
						if(domorexp.equalsIgnoreCase("V")){
							masterForm.setDomesticOrExports("Validation");
							}
						String salesPackId=rs.getString("SALES_PACK_ID");
						masterForm.setSalesPackId(rs.getString("SALES_PACK_ID"));
						masterForm.setPackTypeId(rs.getString("P_TYPE_DESC"));
						masterForm.setSalesUnitOfMeaseurement(rs.getString("S_UOM_DESC"));
						masterForm.setDivisionId(rs.getString("DIV_DESC"));
						masterForm.setTherapeuticSegmentID(rs.getString("THER_SEG_DESC"));
						masterForm.setBrandID(rs.getString("BRAND_DESC"));
						masterForm.setSrengthId(rs.getString("STRENGTH_DESC"));
						masterForm.setGenericName(rs.getString("GEN_NAME_DESC"));
						
						
						String matpri=rs.getString("Material_Pricing");
						if(matpri.equalsIgnoreCase("1")){
							masterForm.setMaterialPricing("Normal");
							}
						if(matpri.equalsIgnoreCase("2")){
							masterForm.setMaterialPricing("Spare parts");
							}
						if(matpri.equalsIgnoreCase("11")){
							masterForm.setMaterialPricing("Scheduled(Controled)");
							}
						if(matpri.equalsIgnoreCase("12")){
							masterForm.setMaterialPricing("Un-Scheduled(De-Con)");
							}
						if(matpri.equalsIgnoreCase("13")){
							masterForm.setMaterialPricing("PS / Promo (Micro)");
							}
						if(matpri.equalsIgnoreCase("14")){
							masterForm.setMaterialPricing("No MRP ED Extra(Mic)");
							}
						if(matpri.equalsIgnoreCase("15")){
							masterForm.setMaterialPricing("No MRP ED Incl (Mic)");
							}
						if(matpri.equalsIgnoreCase("16")){
							masterForm.setMaterialPricing("Scrap IT Extra (Mic)");
							}
						if(matpri.equalsIgnoreCase("17")){
							masterForm.setMaterialPricing("MRP(Con)-ED Exe(Mic)");
							}
						if(matpri.equalsIgnoreCase("18")){
							masterForm.setMaterialPricing("MRP(DeC)-ED Exe(Mic)");
							}
						if(matpri.equalsIgnoreCase("19")){
							masterForm.setMaterialPricing("No MRP-ED Exe(Mic)");
							}
						if(matpri.equalsIgnoreCase("20")){
							masterForm.setMaterialPricing("Food Prod(MRP)-Micro)");
							}
						if(matpri.equalsIgnoreCase("21")){
							masterForm.setMaterialPricing("Vet Prod (MRP)-Micr");
							}
						if(matpri.equalsIgnoreCase("22")){
							masterForm.setMaterialPricing("Generic Price Grp-Mi");
							}
						if(matpri.equalsIgnoreCase("23")){
							masterForm.setMaterialPricing("MRP(Vet)-ED Exe(Mic)");
							}
						if(matpri.equalsIgnoreCase("24")){
							masterForm.setMaterialPricing("P-to-P ED Extra(Mic)");
							}
						if(matpri.equalsIgnoreCase("25")){
							masterForm.setMaterialPricing("Cosm Prod(MRP)-Micro");
							}
						if(matpri.equalsIgnoreCase("26")){
							masterForm.setMaterialPricing("Scrap - ED Exe(Mic))");
							}
						if(matpri.equalsIgnoreCase("27")){
							masterForm.setMaterialPricing("Import Items PG");
							}
					
						masterForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
						masterForm.setNetWeight(rs.getString("NET_WEIGHT"));
						
						masterForm.setDimension(rs.getString("DIMENSION"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
					
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
						
									
					}
					masterForm.setStorageLocationId("");
					masterForm.setWeightUOM("");
					masterForm.setValuationClass("");
			
					try {
						
						//storage location	
						String getStorageLoc="select s.STORAGE_LOCATION_ID,s.STORAGE_LOCATION_NAME from material_code_request m,STORAGE_LOCATION s where "
								+ "REQUEST_NO='"+requstNo1+"' and m.STORAGE_LOCATION_ID=s.STORAGE_LOCATION_ID";
						ResultSet rsStLoc=ad.selectQuery(getStorageLoc);
						if(rsStLoc.next()){
							masterForm.setStorageLocationId(rsStLoc.getString("STORAGE_LOCATION_ID")+"-"+rsStLoc.getString("STORAGE_LOCATION_NAME"));
						}
						//weight wom
						String getweight="select w.W_UOM_CODE,w.W_UOM_DESC from material_code_request m,WEIGHT_UOM w where REQUEST_NO='"+requstNo1+"' "
								+ "and m.WEIGHT_UOM=w.W_UOM_CODE";
						ResultSet rsWt=ad.selectQuery(getweight);
						if(rsWt.next()){
							masterForm.setWeightUOM(rsWt.getString("W_UOM_CODE")+"-"+rsWt.getString("W_UOM_DESC"));
						}
						String getValuation="select v.VALUATION_DESC from material_code_request m,VALUATION_CLASS v where REQUEST_NO='"+requstNo1+"' "
								+ "and m.VALUATION_CLASS=v.VALUATION_ID";
						ResultSet rsValu=ad.selectQuery(getValuation);
						if(rsValu.next()){
							masterForm.setValuationClass(rsValu.getString("VALUATION_DESC"));
						}
						//purchasegrp
						String getFinishedProduct1="select pur.PURCHASE_GROUP_DESC from material_code_request as mat,PURCHASE_GROUP as pur" +
						" where REQUEST_NO='"+requstNo1+"' and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID";
						ResultSet rs1=ad.selectQuery(getFinishedProduct1);
						if(rs1.next())
						{
							masterForm.setPuchaseGroupId(rs1.getString("PURCHASE_GROUP_DESC"));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				
			
				//tax type
				String editRecord11="select tax.T_CLASS_NAME from material_code_request as m,TAX_CLASS as tax" +
						" where REQUEST_NO='"+requstNo+"'and  tax.T_CLASS_ID=m.Tax_Classification";
	 		
	 		
	 		ResultSet rsEditRecord11=ad.selectQuery(editRecord11);
			if(rsEditRecord11.next())
			{
				masterForm.setTaxClassification(rsEditRecord11.getString("T_CLASS_NAME"));
				
			}
		     
			if(matType.equals("FG")||matType.equals("HAWA")){
				String matDetails="select loc.LOCATION_CODE,mat.Type,mat.CREATED_BY,mat.DOMESTIC_OR_EXPORTS from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+requstNo+"' and mat.LOCATION_ID=loc.LOCID";
				ResultSet rsDetails=ad.selectQuery(matDetails);
				try{
					while(rsDetails.next())
				
				{
					
					matGroup=rsDetails.getString("DOMESTIC_OR_EXPORTS");
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				if(matGroup.equals("D"))
					matGroup="Domestic";
				if(matGroup.equals("E"))
		 			matGroup="Export";
		 		if(matGroup.equals("V"))
		 			matGroup="V";
			}
			
					
					finDetails.add(masterForm);
					request.setAttribute("findetails", finDetails);
					forwardType="finmaterial";
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;
						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
					}
					if(checkStatus==0)
					{

						getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
						rsApprDetails=ad.selectQuery(getApprDetails);
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;
							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+type+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									String approveDate=	rsAppInfo.getString("approved_date");
									
									String a[]=approveDate.split(" ");
									approveDate=a[0];
									String b[]=approveDate.split("-");
									approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments.equalsIgnoreCase("null") )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
					}
					
					}
		     
					forwardType="finmaterial";
		     }
				if(type.equalsIgnoreCase("PPC"))
			     {
			 		LinkedList proDetails=new LinkedList();

			 		
			 		PromotionalForm masterForm=new PromotionalForm();
			 		
			 		int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
			 		String matType="";
			 		String matGroup="";
			 		 String location="";
			 		
			 		
			 		String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME," +
			 		"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, div.DIV_DESC," +
			 		"mat.is_asset,mat.PURPOSE_ID,mat.IS_SAS_FORM_AVAILABLE,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION," +
			 		"mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as " +
			 		"matGroup , UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur,DIVISION as div,VALUATION_CLASS as val where REQUEST_NO='"+requstNo+"' and " +
			 		"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
			 		"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID  " +
			 		"and val.VALUATION_ID=mat.VALUATION_CLASS and Type='PPC'"; 
			 				
			 		ResultSet rs=ad.selectQuery(getFinishedProduct);
			 		
			 	
			 			if(rs.next())
					{
			 				String  str=rs.getString("STORAGE_LOCATION_NAME");
			 		 		if(str.equalsIgnoreCase("Printed Material")||str.equalsIgnoreCase("General Material")||str.equalsIgnoreCase("Complement store"))
			 		 		{	
						masterForm.setRequestNo(requstNo1);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");

						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
						
						masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
				 		
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setDivisionId(rs.getString("DIV_DESC"));
						String isAsset=rs.getString("is_asset");
						if(isAsset.equalsIgnoreCase("1"))
						{
							masterForm.setIsAsset("YES");
						}
						if(isAsset.equalsIgnoreCase("0")){
							masterForm.setIsAsset("NO");
						}
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						
						String purposeId=rs.getString("PURPOSE_ID");
					    if(purposeId.equalsIgnoreCase("1"))
					    {
					    	masterForm.setPurposeID("Gift & Compliments");
					    	
					    }
					    if(purposeId.equalsIgnoreCase("2"))
					    {
					    	masterForm.setPurposeID("Propaganda & Promotional (KUDLU DEPOT)");
					    	
					    }
					    if(purposeId.equalsIgnoreCase("3"))
					    {
					    	masterForm.setPurposeID("Product Launch Exp");
					    	
					    }
					    if(purposeId.equalsIgnoreCase("4"))
					    {
					    	masterForm.setPurposeID("Sales Promotional (SAS)");
					    	
					    }
					    if(purposeId.equalsIgnoreCase("5"))
					    {
					    	masterForm.setPurposeID("Visual Ads,Literature");
					    	
					    }
						
					    if(purposeId.equalsIgnoreCase("6"))
					    {
					    	masterForm.setPurposeID("Conference,National & Regional");
					    	
					    }
					    if(purposeId.equalsIgnoreCase("7"))
					    {
					    	masterForm.setPurposeID("Incentive to Field Staff");
					    	
					    }
					    if(purposeId.equalsIgnoreCase("8"))
					    {
					    	masterForm.setPurposeID("Incentive to Stockist/Chemist");
					    	
					    }
					    if(purposeId.equalsIgnoreCase("9"))
					    {
					    	masterForm.setPurposeID("Travelling Lodging & Boarding Exp");
					    	
					    }
						
					   
						String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
						
						if(isSAS.equalsIgnoreCase("1"))
						{
							masterForm.setIsSASFormAvailable("YES");
						}
						if(isSAS.equalsIgnoreCase("0")){
							masterForm.setIsSASFormAvailable("NO");
			 		
					}
						
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
						proDetails.add(masterForm);
			 		
						request.setAttribute("prodetails", proDetails);
				 		forwardType="promaterial";
			    
			 		
			     }}
			 			int  checkStatus=0;
						LinkedList listApprers=new LinkedList();
						String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
						ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;
							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
						}
						if(checkStatus==0)
						{

							getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
							"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
							rsApprDetails=ad.selectQuery(getApprDetails);
							while(rsApprDetails.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(rsApprDetails.getString("Priority"));
								apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
								String empCode=rsApprDetails.getString("Approver_Id");
								String actualapprover="";
								boolean data=false;
								
								String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
								"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
								"mast.Material_Type='"+type+"' AND Material_Group=''  and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
								"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
								"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								while(rsAppInfo.next())
								{
									
									actualapprover=rsAppInfo.getString("Actual_Approver");
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
										data=true;
									}
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
										data=true;
									}
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
										data=true;
									}
									apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
									{
										String approveDate=	rsAppInfo.getString("approved_date");
										
										String a[]=approveDate.split(" ");
										approveDate=a[0];
										String b[]=approveDate.split("-");
										approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
										apprvers.setDate(approveDate);
									}else{
										String rejectDate=	rsAppInfo.getString("rejected_date");
										
										String a[]=rejectDate.split(" ");
										rejectDate=a[0];
										String b[]=rejectDate.split("-");
										rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
										apprvers.setDate(rejectDate);
									}
									String comments="";
									comments=rsAppInfo.getString("Comments");
									if(comments=="null" )
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo.getString("Comments"));
									}
								}
								String pernr="";

								if(data==true)
												{
													pernr=actualapprover;
												}
												else
												{
													pernr=rsApprDetails.getString("Approver_Id");
												}						
										
										
									String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
									ResultSet rsname=ad.selectQuery(name);
									if(rsname.next())
									{
										apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
									}
								listApprers.add(apprvers);
								request.setAttribute("approverDetails", listApprers);
						}
						
						}
			 			forwardType="promaterial";
			 		}
			     
				if(type.equalsIgnoreCase("ZCIV")||type.equalsIgnoreCase("ZCON")||type.equalsIgnoreCase("ZITC")||type.equalsIgnoreCase("LC")
						||type.equalsIgnoreCase("ZPFL")||type.equalsIgnoreCase("ZSCR"))
			     {
			 		LinkedList genDetails=new LinkedList();

			 		GeneralMaterialForm masterForm=new GeneralMaterialForm();
			 		
			 		int requstNo1=Integer.parseInt(request.getParameter("requstNo"));	
			 		
			 		String matType="";
			 		String matGroup="";
			 		 String location="";
					String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME," +
							"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.is_asset,dep.DPTSTXT" +
							",mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from " +
							"material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , " +
							"UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+requstNo+"' and	" +
							"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
							"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='"+type+"' and val.VALUATION_ID=mat.VALUATION_CLASS" +
							" and dep.DPTID=mat.UTILIZING_DEPT";
					
					ResultSet rs=ad.selectQuery(getFinishedProduct);
					if(rs.next())
					{
						masterForm.setRequestNo(requstNo1);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");

						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
					
						masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
						masterForm.setUtilizingDept(rs.getString("DPTSTXT"));
						String isAsset=rs.getString("is_asset");
						if(isAsset.equalsIgnoreCase("1"))
						{
							masterForm.setIsAsset("YES");
						}
						if(isAsset.equalsIgnoreCase("0"))
							masterForm.setIsAsset("NO");
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
						
						genDetails.add(masterForm);
						request.setAttribute("gendetails", genDetails);
				 		forwardType="genmaterial";
				
					}
					
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;
						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
					}
					if(checkStatus==0)
					{

						getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
						rsApprDetails=ad.selectQuery(getApprDetails);
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;
							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+type+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									String approveDate=	rsAppInfo.getString("approved_date");
									
									String a[]=approveDate.split(" ");
									approveDate=a[0];
									String b[]=approveDate.split("-");
									approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
					}
					
					}
					  
					forwardType="genmaterial";
					}
				
				if(type.equalsIgnoreCase("OSE"))
			     {
			 		LinkedList planDetails=new LinkedList();
			 		String matType="";
			 		String matGroup="";
			 		 String location="";
			 		
			 		ZPSRForm masterForm=new ZPSRForm();
			 		
			 		int requstNo1=Integer.parseInt(request.getParameter("requstNo"));	
			 		
			 		String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
			 		" mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.IS_EQUIPMENT,mat.EQUIPMENT_NAME,mat.EQUIPMENT_MAKE," +
			 		"mat.Component_MAKE,mat.IS_SPARE,mat.OEM_PartNo,mat.IS_NEW_Equipment,mat.IS_NEW_Furniture,mat.IS_NEW_Facility,	mat.IS_Spare_required,mat.moc,mat.rating,mat.range," +
			 		"mat.PO_NUMBER,mat.PR_NUMBER,dep.DPTSTXT,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY,mat.DIMENSION,mat.PACK_SIZE,mat.equip_Intended" +
			 		" from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , UNIT_MESUREMENT AS uom " +
			 		",PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+requstNo+"' and	loc.LOCID=mat.LOCATION_ID and " +
			 		"stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID " +
			 		"and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='OSE' and val.VALUATION_ID=mat.VALUATION_CLASS and dep.DPTID=mat.UTILIZING_DEPT";
					
			 		
			 		
			 		ResultSet rs=ad.selectQuery(getFinishedProduct);
					if(rs.next())
					{
						masterForm.setRequestNo(requstNo1);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");
						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
					
						masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
					
						String isEquipment=rs.getString("IS_EQUIPMENT");
						if(isEquipment.equalsIgnoreCase("1"))
						{
							masterForm.setIsEquipment("Yes");
							
						}
						masterForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
						masterForm.setEquipmentMake(rs.getString("EQUIPMENT_MAKE"));
						if(isEquipment.equalsIgnoreCase("0")){
							masterForm.setIsEquipment("No");
						
						}
						String isSpare=rs.getString("IS_SPARE");
						if(isSpare.equalsIgnoreCase("1"))
						{
							masterForm.setIsSpare("Yes");
						
						}
						masterForm.setComponentMake(rs.getString("Component_MAKE"));
						masterForm.setOemPartNo(rs.getString("OEM_PartNo"));
						if(isSpare.equalsIgnoreCase("0")){
							masterForm.setIsSpare("No");
						
							
						}
						masterForm.setMoc(rs.getString("moc"));
						masterForm.setRating(rs.getString("rating"));
						masterForm.setRange(rs.getString("range"));
						String isNewEquipment=rs.getString("IS_NEW_Equipment");
						if(isNewEquipment.equalsIgnoreCase("1"))
						{
							masterForm.setIsNewEquipment("Yes");
							
						}
						if(isNewEquipment.equalsIgnoreCase("0"))
						{
							masterForm.setIsNewEquipment("No");
							
						}
						if(isNewEquipment.equalsIgnoreCase("2"))
						{
							masterForm.setIsNewEquipment("N/A");
							
						}
						String isNewfurniturt=rs.getString("IS_NEW_Furniture");
						if(isNewfurniturt.equalsIgnoreCase("1"))
						{
							masterForm.setIsItNewFurniture("Yes");
							
						}
						if(isNewfurniturt.equalsIgnoreCase("0"))
						{
							masterForm.setIsItNewFurniture("No");
							
						}
						if(isNewfurniturt.equalsIgnoreCase("2"))
						{
							masterForm.setIsItNewFurniture("N/A");
							
						}
						
						String isNewfacility=rs.getString("IS_NEW_Facility");
						if(isNewfacility.equalsIgnoreCase("1"))
						{
							masterForm.setIsItFacility("Yes");
							
						}
						if(isNewfacility.equalsIgnoreCase("0"))
						{
							masterForm.setIsItFacility("No");
							
						}
						if(isNewfacility.equalsIgnoreCase("2"))
						{
							masterForm.setIsItFacility("N/A");
							
						}
							
						String issparey=rs.getString("IS_Spare_required");
						if(issparey.equalsIgnoreCase("1"))
						{
							masterForm.setIsSpareNewEquipment("Yes");
							
						}
						if(issparey.equalsIgnoreCase("0"))
						{
							masterForm.setIsSpareNewEquipment("No");
							
						}		
						if(issparey.equalsIgnoreCase("2"))
						{
							masterForm.setIsSpareNewEquipment("N/A");
							
						}
							
					
						masterForm.setPrNumber(rs.getString("PR_NUMBER"));
						masterForm.setPoNumber(rs.getString("PO_NUMBER"));
						masterForm.setUtilizingDept(rs.getString("DPTSTXT"));
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						masterForm.setDimensions(rs.getString("DIMENSION"));
						masterForm.setPackSize(rs.getString("PACK_SIZE"));
						masterForm.setEquipIntendedFor(rs.getString("equip_Intended"));
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
					
						planDetails.add(masterForm);
						request.setAttribute("plandetails", planDetails);
				 		forwardType="planmaterial";
				
				
					}
					
					int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;
						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+type+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments="";
							comments=rsAppInfo.getString("Comments");
							if(comments=="null" )
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
					}
					if(checkStatus==0)
					{

						getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='"+type+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
						rsApprDetails=ad.selectQuery(getApprDetails);
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;
							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+type+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									String approveDate=	rsAppInfo.getString("approved_date");
									
									String a[]=approveDate.split(" ");
									approveDate=a[0];
									String b[]=approveDate.split("-");
									approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments="";
								comments=rsAppInfo.getString("Comments");
								if(comments=="null" )
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
					}
					
					}
					forwardType="planmaterial";
					}
				}
					catch (Exception e) {
					e.printStackTrace();
				}
				
				return mapping.findForward(forwardType);
			}
				
	
	
	
	
	
	
	
	
	

	
	
	
	public ActionForward displayPending(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MyRequestForm myReqForm = (MyRequestForm) form;
		ArrayList listOfMyRequest=new ArrayList();
		MyRequestDAO ad=new MyRequestDAO();
		 HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String reqStatus=request.getParameter("status");
		
		String userPlantID=user.getPlantId();
		int userID=user.getId();
		try{
			
			String status="";
			if(reqStatus.equalsIgnoreCase("Pending"))
				status="Pending";
			if(reqStatus.equalsIgnoreCase("Approved"))
				status="Approved";
			if(reqStatus.equalsIgnoreCase("Rejected"))
				status="Rejected";
				String getMaterialCodeRecords="select m.REQUEST_NO,l.location_code,m.Type,m.CREATED_DATE,m.Approve_Type,m.REQUEST_DATE,m.URL,m.last_approver,m.pending_approver from  material_code_request as m," +
	"Location as l where m.LOCATION_ID=l.LOCID_ID and m.CREATED_BY='"+user.getEmployeeNo()+"'and m.Approve_Type='"+reqStatus+"'";
				
	ResultSet rsGetMaterialCode=ad.selectQuery(getMaterialCodeRecords);
	while(rsGetMaterialCode.next()){
		MyRequestForm materialRecords=new MyRequestForm();
		materialRecords.setRequestNumber(rsGetMaterialCode.getString("REQUEST_NO"));
		materialRecords.setLocationName(rsGetMaterialCode.getString("location_code"));
		materialRecords.setRequestType(rsGetMaterialCode.getString("Type"));
		String requestDate=rsGetMaterialCode.getString("REQUEST_DATE");
		String req[]=requestDate.split(" ");
		requestDate=req[0];
		String a[]=requestDate.split("-");
		requestDate=a[2]+"/"+a[1]+"/"+a[0];
		materialRecords.setCreateDate(requestDate);
		materialRecords.setUrl(rsGetMaterialCode.getString("URL"));
		
		materialRecords.setApprovalStatus(rsGetMaterialCode.getString("Approve_Type"));
		materialRecords.setLastApprover(rsGetMaterialCode.getString("last_approver"));
		materialRecords.setPendingApprover(rsGetMaterialCode.getString("pending_approver"));
		
		listOfMyRequest.add(materialRecords);	
			
		}
	
		
		
		
			String getCustomerMasterRecords="select v.REQUEST_NO,v.CITY,v.CREATED_DATE,v.Approve_Status,v.last_approver,v.pending_approver from CUSTOMER_MASTER_M as v where  v.CREATED_BY='"+user.getEmployeeNo()+"' and v.Approve_Status='"+reqStatus+"' " ;
			
			ResultSet rsGetCustomerMaster=ad.selectQuery(getCustomerMasterRecords);
			while(rsGetCustomerMaster.next()){
			MyRequestForm customerRecords=new MyRequestForm();
			customerRecords.setRequestNumber(rsGetCustomerMaster.getString("REQUEST_NO"));
			customerRecords.setLocationName(rsGetCustomerMaster.getString("CITY"));
			customerRecords.setRequestType("Customer Master");
			String requestDate=rsGetCustomerMaster.getString("CREATED_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			customerRecords.setCreateDate(requestDate);
			customerRecords.setApprovalStatus(rsGetCustomerMaster.getString("Approve_Status"));
			String url="customerMaster.do?method=editCustomerRecord";
			customerRecords.setUrl(url);
			customerRecords.setLastApprover(rsGetCustomerMaster.getString("last_approver"));
			customerRecords.setPendingApprover(rsGetCustomerMaster.getString("pending_approver"));
			listOfMyRequest.add(customerRecords);	
			}
	
		
		
	
			String getVenorMasterRecords="select v.REQUEST_NO,l.location_code,v.CREATED_DATE,v.Approve_Status,v.last_approver,v.pending_approver from vendor_master_m as v,Location as l " +
			"where v.LOCATION_ID=l.LOCID  and v.CREATED_BY='"+user.getEmployeeNo()+"' and v.Approve_Status='"+reqStatus+"'";
		
			ResultSet rsGetVenorMaster=ad.selectQuery(getVenorMasterRecords);
			while(rsGetVenorMaster.next()){
		MyRequestForm venorMasterRecords=new MyRequestForm();
		venorMasterRecords.setRequestNumber(rsGetVenorMaster.getString("REQUEST_NO"));	
		venorMasterRecords.setLocationName(rsGetVenorMaster.getString("location_code"));
		venorMasterRecords.setRequestType("Vendor Master");
		String requestDate=rsGetVenorMaster.getString("CREATED_DATE");
		String req[]=requestDate.split(" ");
		requestDate=req[0];
		String a[]=requestDate.split("-");
		requestDate=a[2]+"/"+a[1]+"/"+a[0];
		venorMasterRecords.setCreateDate(requestDate);
		String url="vendorMasterRequest.do?method=editVendorRecord";
		venorMasterRecords.setUrl(url);
		venorMasterRecords.setApprovalStatus(rsGetVenorMaster.getString("Approve_Status"));
		venorMasterRecords.setLastApprover(rsGetVenorMaster.getString("last_approver"));
		venorMasterRecords.setPendingApprover(rsGetVenorMaster.getString("pending_approver"));
		listOfMyRequest.add(venorMasterRecords);
		}
		
		
			String getServiceMaster="select c.request_no,c.plant_code,c.CREATED_DATE,c.app_satus,c.last_approver,c.pending_approver from SERVICE_MASTER as c where  c.CREATED_BY='"+user.getEmployeeNo()+"' and c.app_satus='"+reqStatus+"'";
			ResultSet rsServiceMaster=ad.selectQuery(getServiceMaster);
			while(rsServiceMaster.next()){
			MyRequestForm serviceMaster=new MyRequestForm();
			serviceMaster.setRequestNumber(rsServiceMaster.getString("request_no"));
			serviceMaster.setLocationName(rsServiceMaster.getString("plant_code"));
			serviceMaster.setRequestType("Service Master");
			String requestDate=rsServiceMaster.getString("CREATED_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			serviceMaster.setCreateDate(requestDate);
			serviceMaster.setApprovalStatus(rsServiceMaster.getString("app_satus"));
			String url="serviceMasterRequest.do?method=editRecord";
			serviceMaster.setUrl(url);
			serviceMaster.setLastApprover(rsServiceMaster.getString("last_approver"));
			serviceMaster.setPendingApprover(rsServiceMaster.getString("pending_approver"));

			listOfMyRequest.add(serviceMaster);
		}
		
		
		
			String getCodeExtention="select c.REQUEST_NO,l.location_code,c.CREATED_DATE,c.APPROVE_TYPE,c.last_approver,c.pending_approver from code_extenstion_request as c,Location as l where  l.LOCID=c.PLANT_1 and c.CREATED_BY='"+user.getEmployeeNo()+"' and c.APPROVE_TYPE='"+reqStatus+"'";
			
			ResultSet rsCodeExtention=ad.selectQuery(getCodeExtention);
			while(rsCodeExtention.next()){
			MyRequestForm codeExtentionRec=new MyRequestForm();
			codeExtentionRec.setRequestNumber(rsCodeExtention.getString("REQUEST_NO"));
			codeExtentionRec.setLocationName(rsCodeExtention.getString("location_code"));
			codeExtentionRec.setRequestType("Code Extention");
			String requestDate=rsCodeExtention.getString("CREATED_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			codeExtentionRec.setCreateDate(requestDate);
			codeExtentionRec.setApprovalStatus(rsCodeExtention.getString("APPROVE_TYPE"));
			String url="materialCodeExtenstion.do?method=editCodeExtension";
			codeExtentionRec.setUrl(url);
			codeExtentionRec.setLastApprover(rsCodeExtention.getString("last_approver"));
			codeExtentionRec.setPendingApprover(rsCodeExtention.getString("pending_approver"));

			listOfMyRequest.add(codeExtentionRec);
			}
		int leaveStatus=0;
			if(reqStatus.equalsIgnoreCase("Pending"))
				leaveStatus=0;
			if(reqStatus.equalsIgnoreCase("Approved"))
				leaveStatus=1;
			if(reqStatus.equalsIgnoreCase("Rejected"))
				leaveStatus=2;
			String getLeaveDetails="select loc.location_code,l.id,l.submit_date,l.leave_status,l.Last_approver,l.Pending_approver from leave_details as l,Location as loc  " +
			"where loc.LOCID=l.plant_id and l.user_id='"+user.getId()+"' and l.leave_status='"+leaveStatus+"' ";
		
	ResultSet rsLeaveDetails=ad.selectQuery(getLeaveDetails);
	while(rsLeaveDetails.next()){
	MyRequestForm leaveMaster=new MyRequestForm();
	leaveMaster.setRequestNumber(rsLeaveDetails.getString("id"));
	leaveMaster.setLocationName(rsLeaveDetails.getString("location_code"));
	leaveMaster.setRequestType("Leave");
	String requestDate=rsLeaveDetails.getString("submit_date");
	
	leaveMaster.setCreateDate(requestDate);
	
	int leave_status=rsLeaveDetails.getInt("leave_status");
	if(leave_status==0)
		leaveMaster.setApprovalStatus("Pending");
	if(leave_status==1)
		leaveMaster.setApprovalStatus("Approved");
	if(leave_status==2)
		leaveMaster.setApprovalStatus("Rejected");
	leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
	String pendingApprover=rsLeaveDetails.getString("Pending_approver");
	if(pendingApprover.equalsIgnoreCase("0"))
		pendingApprover="No";
	leaveMaster.setPendingApprover(pendingApprover);
	
	String url="leave.do?method=selectRequest";
	
	leaveMaster.setUrl(url);
	//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
	//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
	listOfMyRequest.add(leaveMaster);
	}
	request.setAttribute("myRequestList", listOfMyRequest);
	
	if(listOfMyRequest.size()==0)
	{
		myReqForm.setMessage("No Records Found");
		request.setAttribute("noRecords", "noRecords");
	}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		return mapping.findForward("displayMyRequestList");
	}
	
	public ActionForward displayMyRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MyRequestForm myReqForm = (MyRequestForm) form;
		setFilterType("Pending", myReqForm);
		
		return mapping.findForward("displayMyRequestList");
	}
	
	
						
						public ActionForward displayMyRequestList(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {
							MyRequestForm myReqForm = (MyRequestForm) form;
							setFilterType("Pending", myReqForm);
							ArrayList listOfMyRequest=new ArrayList();
							try{
								String r=myReqForm.getReqRequstType();
								String s=myReqForm.getReqStatus();
							  
							    	listOfMyRequest=searchMyRequestList(mapping, myReqForm, request, response);
					
					      
							
							ArrayList newList=new ArrayList();
							
							Iterator it=listOfMyRequest.iterator();
							
							int i=0;
							while(i<10){
								if(it.hasNext()){
									newList.add(it.next());
								i++;
								}
								else
									break;
							}
							myReqForm.setStartRecord(1);
							myReqForm.setEndRecord(i);
							myReqForm.setNext(i);
						if(i>=10){
							
							request.setAttribute("displayRecordNo","ok");
							if(it.hasNext()){
							request.setAttribute("nextButton", "yes");
							request.setAttribute("disablePreviousButton", "ok");
							request.setAttribute("displayRecordNo","ok");
							}}
							else{
							request.setAttribute("disablePreviousButton", "ok");
							request.setAttribute("disableNextButton","yes");
							
							}
							  //myReqForm.setMyRequestRecords(listOfMyRequest);
							request.setAttribute("myRequestList", newList);
							//request.setAttribute("myRequestList1", listOfMyRequest);
							
							}catch (Exception e) {
								e.printStackTrace();
							}
							myReqForm.setHeading("My Request");
							myReqForm.setListOfrecords(listOfMyRequest);
							
							if(listOfMyRequest.size()==0)
							{
								myReqForm.setMessage("No Records Found.");
								request.setAttribute("noRecords", "noRecords");
							}
							
							return mapping.findForward("displayMyRequestList");
						}
						
						
						public ActionForward next(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {
							MyRequestForm myReqForm = (MyRequestForm) form;
							int nextval=myReqForm.getNext();
							int i=0;
							i=nextval;
							myReqForm.setStartRecord(i+1);
							myReqForm.setPrev(nextval);
							int end=nextval+10;
							int start=0;
							int row=0;
						    HttpSession session=request.getSession();
							//sform.setEndRecord(end);
							 ArrayList alldata=new ArrayList();
							try{
								 if((myReqForm.getReqRequstType().equalsIgnoreCase(""))&&(myReqForm.getReqStatus().equalsIgnoreCase("")))
									 alldata=getResultList(form,request);
									    else
									    	 alldata=searchMyRequestList(mapping, myReqForm, request, response);
								 }
								 
							 
							 catch(Exception e){
								 e.printStackTrace();
							 }
							//HttpSession session=request.getSession();
							
							ArrayList NewList=new ArrayList();
							Iterator it=alldata.iterator();
							try{
							while(start<end){
								if(it.hasNext()&&start==nextval){
									NewList.add(it.next());
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
							System.out.println(nextval);
							myReqForm.setEndRecord(nextval);
							if(it.hasNext()){
								request.setAttribute("nextButton", "yes");
							request.setAttribute("previousButton", "ok");
							}
							else{
								request.setAttribute("disableNextButton","yes");
							request.setAttribute("previousButton", "ok");
							}
							request.setAttribute("displayRecordNo","ok");	
							
							myReqForm.setNext(nextval);
							//myReqForm.setRow(row);
							request.setAttribute("myRequestList", NewList);
							return mapping.findForward("displayMyRequestList");
						}
						public ActionForward prev(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {

							MyRequestForm myReqForm = (MyRequestForm) form;
							HttpSession session=request.getSession();
							int prev=myReqForm.getPrev();
							int start=prev-10;
							//start--;
							//prev--;
							if(start<0)
								start=0;
							myReqForm.setStartRecord(start+1);
							myReqForm.setEndRecord(prev);
							int i=0;
							int row=0;
							EssDao ad=new EssDao();
							//sform.setEndRecord(end);
							ArrayList alldata=new ArrayList();
							
							try{

								 if((myReqForm.getReqRequstType().equalsIgnoreCase(""))&&(myReqForm.getReqStatus().equalsIgnoreCase("")))
									 alldata=getResultList(form,request);
									
									    else
									    	 alldata=searchMyRequestList(mapping, myReqForm, request, response);
								 }
								 
							 
							 catch(Exception e){
								 e.printStackTrace();
							 }
							
							
							ArrayList NewList=new ArrayList();
							
							
							Iterator it=alldata.iterator();
							while(i<prev){
								if(it.hasNext()&&i==start){
									NewList.add(it.next());
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
							myReqForm.setPrev(prev-10);
							myReqForm.setNext(prev);
						
							request.setAttribute("displayRecordNo","ok");
							
						
							
							
							request.setAttribute("myRequestList", NewList);
							
							return mapping.findForward("displayMyRequestList");
						}
						public ActionForward first(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {

							MyRequestForm myReqForm = (MyRequestForm) form;
							HttpSession session=request.getSession();
							EssDao ad=new EssDao();
							//sform.setEndRecord(end);
							int row=0;
							ArrayList alldata=new ArrayList();
							
							try{

								 if((myReqForm.getReqRequstType().equalsIgnoreCase(""))&&(myReqForm.getReqStatus().equalsIgnoreCase("")))
									 alldata=getResultList(form,request);
									
									    else
									    	 alldata=searchMyRequestList(mapping, myReqForm, request, response);
							 }
							 catch(Exception e){
								 e.printStackTrace();
							 }
							
							ArrayList NewList=new ArrayList();
							
							Iterator it=alldata.iterator();
							
							int i=0;
							while(i<10){
								if(it.hasNext()){
									NewList.add(it.next());
								i++;
								}
								else
									break;
							}
							myReqForm.setStartRecord(1);
							myReqForm.setEndRecord(i);
							
							myReqForm.setNext(i);
							if(it.hasNext()){
							request.setAttribute("nextButton", "yes");
							request.setAttribute("disablePreviousButton", "ok");
							}
							else{
							request.setAttribute("disablePreviousButton", "ok");
							request.setAttribute("disableNextButton","yes");
							}
							request.setAttribute("displayRecordNo","ok");
							request.setAttribute("myRequestList", NewList);
						
							
							//request.setAttribute("serviceMasterList", serviceDetails);
							
							
							  
							//return mapping.findForward("displayList");
							
						
							
							
							
							return mapping.findForward("displayMyRequestList");
							
						}
						public ActionForward last(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {

							
							
							
							
							MyRequestForm myReqForm = (MyRequestForm) form;
							HttpSession session=request.getSession();
							int prev=myReqForm.getPrev();
							int start=0;
							
							prev--;
							
							int i=0;
							int row=0;
							EssDao ad=new EssDao();
							//sform.setEndRecord(end);
							ArrayList alldata=new ArrayList();
							
							try{

								 if((myReqForm.getReqRequstType().equalsIgnoreCase(""))&&(myReqForm.getReqStatus().equalsIgnoreCase("")))
									 alldata=getResultList(form,request);
									
									    else
									    	 alldata=searchMyRequestList(mapping, myReqForm, request, response);
							 }
							 catch(Exception e){
								 e.printStackTrace();
							 }
							
							ArrayList NewList=new ArrayList();
							
							
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
							myReqForm.setStartRecord(i+1);
							System.out.println("------------------------------"+i+1);
							myReqForm.setPrev(i);
							while(i<l){
								if(it.hasNext()&&i==start){
									NewList.add(it.next());
									
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
							
							
								myReqForm.setEndRecord(l);
							
							//sform.setNext(prev);
							request.setAttribute("displayRecordNo","ok");
							
							
							
							
							request.setAttribute("myRequestList", NewList);
							
							
						
							
							
							return mapping.findForward("displayMyRequestList");
							
						}
						
						public ArrayList getResultList( ActionForm form,HttpServletRequest request) {
							ArrayList listOfMyRequest=new ArrayList();
							MyRequestForm myReqForm = (MyRequestForm) form;
							String s=myReqForm.getReqStatus();
							if(s == null){
								s="Pending";
							}
							try{
								System.out.println("displayMyRequestList()------");
							
								MyRequestDAO ad=new MyRequestDAO();
								 HttpSession session=request.getSession();
									UserInfo user=(UserInfo)session.getAttribute("user");
									String userPlantID=user.getPlantId();
									int userID=user.getId();
								
								String getMaterialCodeRecords="select m.REQUEST_NO,l.location_code,m.Type,m.CREATED_DATE,m.Approve_Type,m.REQUEST_DATE,m.last_approver,m.pending_approver,m.URL from  material_code_request as m," +
										"Location as l where m.LOCATION_ID=l.LOCID and m.Approve_Type='"+s+"' and m.CREATED_BY='"+user.getEmployeeNo()+"' order by REQUEST_NO desc";
								ResultSet rsGetMaterialCode=ad.selectQuery(getMaterialCodeRecords);
								while(rsGetMaterialCode.next()){
									MyRequestForm materialRecords=new MyRequestForm();
									materialRecords.setRequestNumber(rsGetMaterialCode.getString("REQUEST_NO"));
									materialRecords.setLocationName(rsGetMaterialCode.getString("location_code"));
									materialRecords.setRequestType(rsGetMaterialCode.getString("Type"));
									String requestDate=rsGetMaterialCode.getString("REQUEST_DATE");
									String req[]=requestDate.split(" ");
									requestDate=req[0];
									String a[]=requestDate.split("-");
									requestDate=a[2]+"/"+a[1]+"/"+a[0];
									materialRecords.setCreateDate(requestDate);
									materialRecords.setUrl(rsGetMaterialCode.getString("URL"));
									
									materialRecords.setApprovalStatus(rsGetMaterialCode.getString("Approve_Type"));
									materialRecords.setLastApprover(rsGetMaterialCode.getString("last_approver"));
									materialRecords.setPendingApprover(rsGetMaterialCode.getString("pending_approver"));
									
									listOfMyRequest.add(materialRecords);
								}
								String getVenorMasterRecords="select v.REQUEST_NO,l.location_code,v.REQUEST_DATE,v.Approve_Status,v.last_approver,v.pending_approver from vendor_master_m as v,Location as l " +
										"where v.LOCATION_ID=l.LOCID and v.Approve_Status='"+s+"' and v.CREATED_BY='"+user.getEmployeeNo()+"'";
								ResultSet rsGetVenorMaster=ad.selectQuery(getVenorMasterRecords);
								while(rsGetVenorMaster.next()){
							MyRequestForm venorMasterRecords=new MyRequestForm();
							venorMasterRecords.setRequestNumber(rsGetVenorMaster.getString("REQUEST_NO"));
							venorMasterRecords.setLocationName(rsGetVenorMaster.getString("location_code"));
							venorMasterRecords.setRequestType("Vendor Master");
							String requestDate=rsGetVenorMaster.getString("REQUEST_DATE");
							String req[]=requestDate.split(" ");
							requestDate=req[0];
							String a[]=requestDate.split("-");
							requestDate=a[2]+"/"+a[1]+"/"+a[0];
							venorMasterRecords.setCreateDate(requestDate);
							String url="vendorMasterRequest.do?method=editVendorRecord";
							venorMasterRecords.setUrl(url);
							venorMasterRecords.setApprovalStatus(rsGetVenorMaster.getString("Approve_Status"));
							venorMasterRecords.setLastApprover(rsGetVenorMaster.getString("last_approver"));
							venorMasterRecords.setPendingApprover(rsGetVenorMaster.getString("pending_approver"));
							listOfMyRequest.add(venorMasterRecords);
								}
								
								
								String getCustomerMasterRecords="select v.REQUEST_NO,v.CITY,v.CREATED_DATE,v.Approve_Status,v.last_approver,v.pending_approver from CUSTOMER_MASTER_M as v where  v.Approve_Status='"+s+"' and v.CREATED_BY='"+user.getEmployeeNo()+"'";
						ResultSet rsGetCustomerMaster=ad.selectQuery(getCustomerMasterRecords);
						while(rsGetCustomerMaster.next()){
						MyRequestForm customerRecords=new MyRequestForm();
						customerRecords.setRequestNumber(rsGetCustomerMaster.getString("REQUEST_NO"));
						customerRecords.setLocationName(rsGetCustomerMaster.getString("CITY"));
						customerRecords.setRequestType("Customer Master");
						String requestDate=rsGetCustomerMaster.getString("CREATED_DATE");
						String req[]=requestDate.split(" ");
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
						customerRecords.setCreateDate(requestDate);
						customerRecords.setApprovalStatus(rsGetCustomerMaster.getString("Approve_Status"));
						String url="customerMaster.do?method=editCustomerRecord";
						customerRecords.setUrl(url);
						customerRecords.setLastApprover(rsGetCustomerMaster.getString("last_approver"));
						customerRecords.setPendingApprover(rsGetCustomerMaster.getString("pending_approver"));
						
						listOfMyRequest.add(customerRecords);
						}
						
						String getCodeExtention="select c.REQUEST_NO,l.location_code,c.CREATED_DATE,c.APPROVE_TYPE,c.last_approver,c.pending_approver from code_extenstion_request as c,Location as l where  l.LOCID=c.PLANT_1 and c.APPROVE_TYPE='"+s+"' and c.CREATED_BY='"+user.getEmployeeNo()+"'";
						ResultSet rsCodeExtention=ad.selectQuery(getCodeExtention);
						while(rsCodeExtention.next()){
						MyRequestForm codeExtentionRec=new MyRequestForm();
						codeExtentionRec.setRequestNumber(rsCodeExtention.getString("REQUEST_NO"));
						codeExtentionRec.setLocationName(rsCodeExtention.getString("location_code"));
						codeExtentionRec.setRequestType("Code Extention");
						String requestDate=rsCodeExtention.getString("CREATED_DATE");
						String req[]=requestDate.split(" ");
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
						codeExtentionRec.setCreateDate(requestDate);
						codeExtentionRec.setApprovalStatus(rsCodeExtention.getString("APPROVE_TYPE"));
						String url="materialCodeExtenstion.do?method=editCodeExtension";
						codeExtentionRec.setUrl(url);
						codeExtentionRec.setLastApprover(rsCodeExtention.getString("last_approver"));
						codeExtentionRec.setPendingApprover(rsCodeExtention.getString("pending_approver"));
						listOfMyRequest.add(codeExtentionRec);
						}
						
						String getServiceMaster="select c.request_no,c.plant_code,c.CREATED_DATE,c.app_satus from SERVICE_MASTER as c" +
								" where  c.app_satus='"+s+"' and c.CREATED_BY='"+user.getEmployeeNo()+"'";
						ResultSet rsServiceMaster=ad.selectQuery(getServiceMaster);
						while(rsServiceMaster.next()){
						MyRequestForm serviceMaster=new MyRequestForm();
						serviceMaster.setRequestNumber(rsServiceMaster.getString("request_no"));
						serviceMaster.setLocationName(rsServiceMaster.getString("plant_code"));
						serviceMaster.setRequestType("Code Extention");
						String requestDate=rsServiceMaster.getString("CREATED_DATE");
						String req[]=requestDate.split(" ");
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
						serviceMaster.setCreateDate(requestDate);
						serviceMaster.setApprovalStatus(rsServiceMaster.getString("app_satus"));
						String url="serviceMasterRequest.do?method=editRecord";
						serviceMaster.setUrl(url);
						
						listOfMyRequest.add(serviceMaster);
						}
						
						String getLeaveDetails="select l.id,t.lv_type,l.submit_date,l.start_date,l.start_duration,l.end_date,l.end_duration,l.Last_approver,l.Pending_approver from leave_details as l,Location as loc,lv_type_m as t " +
								"where loc.LOCID=l.plant_id and l.user_id='"+user.getId()+"' and l.Approvel_Status='"+s+"' and l.leave_type=t.lv_typeid ";
						
						ResultSet rsLeaveDetails=ad.selectQuery(getLeaveDetails);
						while(rsLeaveDetails.next()){
						MyRequestForm leaveMaster=new MyRequestForm();
						leaveMaster.setRequestNumber(rsLeaveDetails.getString("id"));
						leaveMaster.setLocationName(rsLeaveDetails.getString("location_code"));
						leaveMaster.setRequestType(rsLeaveDetails.getString("lv_type"));
						leaveMaster.setCreateDate(rsLeaveDetails.getString("submit_date"));
						leaveMaster.setStartDate(rsLeaveDetails.getString("start_date"));
						leaveMaster.setStartDateDuration(rsLeaveDetails.getString("start_duration"));
						leaveMaster.setEndDate(rsLeaveDetails.getString("end_date"));
						leaveMaster.setEndDateDuration(rsLeaveDetails.getString("end_duration"));
						leaveMaster.setApprovalStatus("Pending");
						leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
						leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
						String url="myRequest.do?method=editMyRequest";
						leaveMaster.setUrl(url);
						listOfMyRequest.add(leaveMaster);
						}
							String getOndutyDetails="select loc.location_code,l.request_no,l.submit_date,l.OnDuty_status,l.Last_approver,l.Pending_approver from OnDuty_details as l,Location as loc " +
									"where loc.LOCID='"+user.getPlantId()+"' and l.user_id='"+user.getId()+"' and l.OnDuty_status='0'";
						
							ResultSet rsOndutyDetails=ad.selectQuery(getOndutyDetails);
							while(rsOndutyDetails.next()){
								MyRequestForm onduty=new MyRequestForm();
								onduty.setRequestNumber(rsOndutyDetails.getString("request_no"));
								onduty.setLocationName(rsOndutyDetails.getString("location_code"));
								onduty.setRequestType("On Duty");
								String requestDate=rsOndutyDetails.getString("submit_date");
								int isthere = (requestDate.indexOf("/"));
								if(isthere <= 0){
									String req[]=requestDate.split(" ");
									requestDate=req[0];
									String a[]=requestDate.split("-");
									requestDate=a[2]+"/"+a[1]+"/"+a[0];
								}
								
								onduty.setCreateDate(requestDate);
								int leave_status=rsOndutyDetails.getInt("OnDuty_status");
								if(leave_status==0)
									onduty.setApprovalStatus("Pending");
								if(leave_status==1)
									onduty.setApprovalStatus("Approved");
								if(leave_status==2)
									onduty.setApprovalStatus("Rejected");
								onduty.setLastApprover(rsOndutyDetails.getString("Last_approver"));
								String pendingApprover=rsOndutyDetails.getString("Pending_approver");
								if(pendingApprover.equalsIgnoreCase("0"))
									pendingApprover="No";
								onduty.setPendingApprover(pendingApprover);
								
								String url="";
								url="";
								onduty.setUrl(url);
								//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
								//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
								listOfMyRequest.add(onduty);
								
								
							}
							
							String getPermissionDetails="select * from Permission_details where user_id='"+user.getId()+"' ";
							ResultSet rsPermission=ad.selectQuery(getPermissionDetails);
							while(rsPermission.next())
							{
								MyRequestForm permission=new MyRequestForm();
								permission.setRequestNumber(rsPermission.getString("request_no"));
								
								permission.setLocationName(user.getPlantId());
								permission.setRequestType("Permission");
								String requestDate=rsPermission.getString("date");
								int isthere = (requestDate.indexOf("/"));
								if(isthere <= 0){
									String req[]=requestDate.split(" ");
									requestDate=req[0];
									String a[]=requestDate.split("-");
									requestDate=a[2]+"/"+a[1]+"/"+a[0];
								}
								permission.setCreateDate(requestDate);
								int permission_status=rsPermission.getInt("Approver_Status");
								if(permission_status==0)
									permission.setApprovalStatus("Pending");
								if(permission_status==1)
									permission.setApprovalStatus("Approved");
								if(permission_status==2)
									permission.setApprovalStatus("Rejected");
								permission.setLastApprover(rsPermission.getString("Last_approver"));
								String pendingApprover=rsPermission.getString("Pending_approver");
								if(pendingApprover.equalsIgnoreCase("0"))
									pendingApprover="No";
								permission.setPendingApprover(pendingApprover);
								
								String url="permission.do?method=editPermission";
								
								permission.setUrl(url);
								//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
								//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
								listOfMyRequest.add(permission);
							}
						
						
						
					}
					catch(Exception e){
						e.printStackTrace();
					}
							return listOfMyRequest;
						}
						public ArrayList searchMyRequestList(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {
							MyRequestForm myReqForm = (MyRequestForm) form;
							ArrayList listOfMyRequest=new ArrayList();
							try{
								
								MyRequestDAO ad=new MyRequestDAO();
								 HttpSession session=request.getSession();
								UserInfo user=(UserInfo)session.getAttribute("user");
								
								
								String userPlantID=user.getPlantId();
								int userID=user.getId();
								String requiredType=myReqForm.getReqRequstType();
								System.out.println("mmm > "+requiredType);
								String reqStatus=myReqForm.getReqStatus();
								
								
								if (requiredType.equalsIgnoreCase("SAP Issues")) {

									LinkedList it = new LinkedList();
									String getITRecords = "";

									if (!reqStatus.equalsIgnoreCase("")) {

										getITRecords = "select * from IT_SAP_ISSUES where Req_Status='"
												+ reqStatus
												+ "' and (Emp_No='"
												+ user.getEmployeeNo()
												+ "' or raisedBy='"
												+ user.getEmployeeNo() + "') order by Req_No desc";
									}
									if (reqStatus.equalsIgnoreCase("All")) {

										getITRecords = "select * from IT_SAP_ISSUES where  (Emp_No='"
												+ user.getEmployeeNo() + "' or raisedBy='"
												+ user.getEmployeeNo() + "') order by Req_No desc";

									}

									ResultSet rs = ad.selectQuery(getITRecords);

									while (rs.next()) {
										MyRequestForm help = new MyRequestForm();

										help.setItReqNo(rs.getInt("Req_No"));
										help.setItReqDate((EMicroUtils.display(rs
												.getDate("Req_date"))));
										help.setLastApprover(rs.getString("Last_approver"));
										help.setPendingApprover(rs.getString("Pending_approver"));
										help.setReqStatus(rs.getString("Req_Status"));
										help.setRequestType("sap issues");
										help.setReqPriority(rs.getString("Req_Priority"));
										help.setRequestName(rs.getString("sap_request_type"));
										
										listOfMyRequest.add(help);

									}

									request.setAttribute("sapit", listOfMyRequest);
								}

								
								if(requiredType.equalsIgnoreCase("VC Room"))
								{
									LinkedList confList=new LinkedList();
									String getConfList="";
									if(!reqStatus.equalsIgnoreCase("")){
									getConfList="select conf.Reqest_No,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,"
									+ "conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.Last_approver,conf.Pending_approver,conf.approval_status from VCRoom_Details conf,emp_official_info emp,"
									+ "Location loc where conf.Requster_Id=emp.PERNR and loc.LOCID=conf.Loc_Id and conf.Requster_Id='"+user.getEmployeeNo()+"' and approval_status='"+reqStatus+"' order by conf.Reqest_No desc";
									}
									if(reqStatus.equalsIgnoreCase("All")){
									getConfList="select conf.Reqest_No,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,"
											+ "conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.Last_approver,conf.Pending_approver,conf.approval_status from VCRoom_Details conf,emp_official_info emp,"
											+ "Location loc where conf.Requster_Id=emp.PERNR and loc.LOCID=conf.Loc_Id and conf.Requster_Id='"+user.getEmployeeNo()+"' order by conf.Reqest_No desc";
									}
									ResultSet rsconfList=ad.selectQuery(getConfList);
									while(rsconfList.next()){
										VCForm c=new VCForm();
										c.setReqNo(rsconfList.getString("Reqest_No"));
										c.setSubmitDate(EMicroUtils.reqDisplay(rsconfList.getString("Submit_Date")));
										c.setLocation(rsconfList.getString("LOCATION_CODE"));
										c.setFloor(rsconfList.getString("Floor"));
										c.setRoomName(rsconfList.getString("Conf_Room"));
										c.setFromDate(rsconfList.getString("From_Date")+" "+rsconfList.getString("From_Time"));
										c.setToDate(rsconfList.getString("To_Date")+" "+rsconfList.getString("To_Time"));
										c.setLastApprover(rsconfList.getString("Last_approver"));
										c.setPendingApprover(rsconfList.getString("Pending_approver"));
										c.setApprovalStatus(rsconfList.getString("approval_status"));
										c.setRequestType("VC Room");
										listOfMyRequest.add(c);
									}
									request.setAttribute("vcList", listOfMyRequest);
									
								}
								if(requiredType.equalsIgnoreCase("Conference"))
								{
									LinkedList confList=new LinkedList();
									String getConfList="";
									if(!reqStatus.equalsIgnoreCase("")){
									getConfList="select conf.Reqest_No,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,"
									+ "conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.Last_approver,conf.Pending_approver,conf.approval_status from ConferenceRoom_Details conf,emp_official_info emp,"
									+ "Location loc where conf.Requster_Id=emp.PERNR and loc.LOCID=conf.Loc_Id and conf.Requster_Id='"+user.getEmployeeNo()+"' and approval_status='"+reqStatus+"' order by conf.Reqest_No desc";
									}
									if(reqStatus.equalsIgnoreCase("All")){
									getConfList="select conf.Reqest_No,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,"
											+ "conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.Last_approver,conf.Pending_approver,conf.approval_status from ConferenceRoom_Details conf,emp_official_info emp,"
											+ "Location loc where conf.Requster_Id=emp.PERNR and loc.LOCID=conf.Loc_Id and conf.Requster_Id='"+user.getEmployeeNo()+"' order by conf.Reqest_No desc";
									}
									ResultSet rsconfList=ad.selectQuery(getConfList);
									while(rsconfList.next()){
										ConferenceForm c=new ConferenceForm();
										c.setReqNo(rsconfList.getString("Reqest_No"));
										c.setSubmitDate(EMicroUtils.reqDisplay(rsconfList.getString("Submit_Date")));
										c.setLocation(rsconfList.getString("LOCATION_CODE"));
										c.setFloor(rsconfList.getString("Floor"));
										c.setRoomName(rsconfList.getString("Conf_Room"));
										c.setFromDate(rsconfList.getString("From_Date")+" "+rsconfList.getString("From_Time"));
										c.setToDate(rsconfList.getString("To_Date")+" "+rsconfList.getString("To_Time"));
										c.setLastApprover(rsconfList.getString("Last_approver"));
										c.setPendingApprover(rsconfList.getString("Pending_approver"));
										c.setApprovalStatus(rsconfList.getString("approval_status"));
										c.setRequestType("Conference");
										listOfMyRequest.add(c);
									}
									request.setAttribute("confereceList", listOfMyRequest);
									
								}
								
								
								if(requiredType.equalsIgnoreCase("IT Request"))
								{
									
									LinkedList it=new LinkedList();
									String getITRecords="";
									
									if(!reqStatus.equalsIgnoreCase("")){
										
										getITRecords="select * from IT_HELPDESK where Req_Status='"+reqStatus+"' and (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') order by Req_No desc";	
									}
                                      if(reqStatus.equalsIgnoreCase("All")){
										
                                    	  getITRecords="select * from IT_HELPDESK where  (Emp_No='"+user.getEmployeeNo()+"' or raisedBy='"+user.getEmployeeNo()+"') order by Req_No desc";
                                    	  
                                      }
								
									
										ResultSet rs=ad.selectQuery(getITRecords);
										
										while(rs.next())
										{
											MyRequestForm help=new MyRequestForm();
										
											help.setItReqNo(rs.getInt("Req_No"));
											help.setItReqDate((EMicroUtils.display(rs.getDate("Req_date"))));
											help.setLastApprover(rs.getString("Last_approver"));
											help.setPendingApprover(rs.getString("Pending_approver"));
											help.setReqStatus(rs.getString("Req_Status"));
											help.setRequestType(rs.getString("Req_type"));
											help.setReqPriority(rs.getString("Req_Priority"));
											help.setItEngStatus(rs.getString("ITEng_Status"));
											listOfMyRequest.add(help);	
												
										}
									
										request.setAttribute("it", listOfMyRequest);
								}
								
						   if(requiredType.equalsIgnoreCase("Material Master")){
									System.out.println("Requested Type In Material Code");
							
									String getMaterialCodeRecords="select  m.MATERIAL_SHORT_NAME,m.REQUEST_NO,l.location_code,m.Type,mType.M_DESC,m.CREATED_DATE,m.Approve_Type,m.REQUEST_DATE,m.URL,m.last_approver,m.pending_approver from  material_code_request as m," +
							"Location as l,MATERIAL_TYPE AS mType  where m.LOCATION_ID=l.LOCID  and m.Type=mType.MATERIAL_GROUP_ID and m.CREATED_BY='"+user.getEmployeeNo()+"' ";
									
									if(!(reqStatus.equalsIgnoreCase("")))
									{
										getMaterialCodeRecords=getMaterialCodeRecords+"and m.Approve_Type='"+reqStatus+"' order by m.REQUEST_NO desc";
									}
									if(reqStatus.equalsIgnoreCase("All"))
									{
										getMaterialCodeRecords="select  m.MATERIAL_SHORT_NAME,m.REQUEST_NO,l.location_code,m.Type,mType.M_DESC,m.CREATED_DATE,m.Approve_Type,m.REQUEST_DATE,m.URL,m.last_approver,m.pending_approver from  material_code_request as m," +
										"Location as l,MATERIAL_TYPE AS mType where m.LOCATION_ID=l.LOCID  and m.Type=mType.MATERIAL_GROUP_ID  and m.CREATED_BY='"+user.getEmployeeNo()+"' order by m.REQUEST_NO desc";
									}		
									
							ResultSet rsGetMaterialCode=ad.selectQuery(getMaterialCodeRecords);
							while(rsGetMaterialCode.next()){
								MyRequestForm materialRecords=new MyRequestForm();
								materialRecords.setMatshortname(rsGetMaterialCode.getString("MATERIAL_SHORT_NAME"));
								materialRecords.setRequestNumber(rsGetMaterialCode.getString("REQUEST_NO"));
								materialRecords.setLocationName(rsGetMaterialCode.getString("location_code"));
								materialRecords.setReqMaterialType(rsGetMaterialCode.getString("Type"));
								materialRecords.setRequestType(rsGetMaterialCode.getString("Type"));
								String requestDate=rsGetMaterialCode.getString("REQUEST_DATE");
								String req[]=requestDate.split(" ");
								requestDate=req[0];
								String a[]=requestDate.split("-");
								requestDate=a[2]+"/"+a[1]+"/"+a[0];
								materialRecords.setCreateDate(requestDate);
								materialRecords.setUrl(rsGetMaterialCode.getString("URL"));
						
								materialRecords.setApprovalStatus(rsGetMaterialCode.getString("Approve_Type"));
								materialRecords.setLastApprover(rsGetMaterialCode.getString("last_approver"));
								materialRecords.setPendingApprover(rsGetMaterialCode.getString("pending_approver"));
								
								listOfMyRequest.add(materialRecords);	
								request.setAttribute("Material Master", "Material Master");	
								}
							
							request.setAttribute("Material code list", "Material code list");	
								}
								
								if(requiredType.equalsIgnoreCase("Customer Master"))
								{
									String getCustomerMasterRecords="select v.REQUEST_NO,v.NAME,v.CITY,v.CREATED_DATE,v.Approve_Status,v.last_approver,v.pending_approver from CUSTOMER_MASTER_M as v where  v.CREATED_BY='"+user.getEmployeeNo()+"' " ;
									
									if(!(reqStatus.equalsIgnoreCase("")))
									{
										getCustomerMasterRecords=getCustomerMasterRecords + "and v.Approve_Status='"+reqStatus+"' order by v.REQUEST_NO desc";
									}
									if(reqStatus.equalsIgnoreCase("All"))
									{
										getCustomerMasterRecords="select v.REQUEST_NO,v.NAME,v.CITY,v.CREATED_DATE,v.Approve_Status,v.last_approver,v.pending_approver from CUSTOMER_MASTER_M as v where  v.CREATED_BY='"+user.getEmployeeNo()+"' order by v.REQUEST_NO desc" ;
									}	
									
									ResultSet rsGetCustomerMaster=ad.selectQuery(getCustomerMasterRecords);
									while(rsGetCustomerMaster.next()){
									MyRequestForm customerRecords=new MyRequestForm();
									customerRecords.setRequestNumber(rsGetCustomerMaster.getString("REQUEST_NO"));
									customerRecords.setLocationName(rsGetCustomerMaster.getString("CITY"));
									customerRecords.setVendorName(rsGetCustomerMaster.getString("NAME"));
									customerRecords.setRequestType("Customer Master");
									String requestDate=rsGetCustomerMaster.getString("CREATED_DATE");
									String req[]=requestDate.split(" ");
									requestDate=req[0];
									String a[]=requestDate.split("-");
									requestDate=a[2]+"/"+a[1]+"/"+a[0];
									customerRecords.setCreateDate(requestDate);
									customerRecords.setApprovalStatus(rsGetCustomerMaster.getString("Approve_Status"));
									String url="customerMaster.do?method=editCustomerRecord";
									customerRecords.setUrl(url);
									customerRecords.setLastApprover(rsGetCustomerMaster.getString("last_approver"));
									customerRecords.setPendingApprover(rsGetCustomerMaster.getString("pending_approver"));
									listOfMyRequest.add(customerRecords);	
									}
							request.setAttribute("customer  list", "customer  list");
								}
								
								if(requiredType.equalsIgnoreCase("Vendor Master"))
								{
									String getVenorMasterRecords="select v.REQUEST_NO,v.name,v.CITY,acc.ACCOUNT_GROUP_NAME,v.CREATED_DATE,v.Approve_Status,v.last_approver," +
										"v.pending_approver from vendor_master_m as v,ACCOUNT_GROUP_M as acc where  v.CREATED_BY='"+user.getEmployeeNo()+"' and v.ACCOUNT_GROUP_ID=acc.ACCOUNT_GROUP_ID ";
								
								if(!(reqStatus.equalsIgnoreCase("")))
								{
									getVenorMasterRecords=getVenorMasterRecords + " and v.Approve_Status='"+reqStatus+"' order by v.REQUEST_NO desc";
								}
								if(reqStatus.equalsIgnoreCase("All"))
								{
									getVenorMasterRecords="select v.REQUEST_NO,v.name,v.CITY,acc.ACCOUNT_GROUP_NAME,v.CREATED_DATE,v.Approve_Status,v.last_approver," +
									"v.pending_approver from vendor_master_m as v,ACCOUNT_GROUP_M as acc where  v.CREATED_BY='"+user.getEmployeeNo()+"' and v.ACCOUNT_GROUP_ID=acc.ACCOUNT_GROUP_ID order by v.REQUEST_NO desc";
								}
								ResultSet rsGetVenorMaster=ad.selectQuery(getVenorMasterRecords);
								while(rsGetVenorMaster.next()){
							MyRequestForm venorMasterRecords=new MyRequestForm();
							venorMasterRecords.setRequestNumber(rsGetVenorMaster.getString("REQUEST_NO"));	
					
							venorMasterRecords.setRequestType("Vendor Master");
							venorMasterRecords.setVendorName(rsGetVenorMaster.getString("name"));
							venorMasterRecords.setLocationName(rsGetVenorMaster.getString("CITY"));
							venorMasterRecords.setVendorType(rsGetVenorMaster.getString("ACCOUNT_GROUP_NAME"));
							String requestDate=rsGetVenorMaster.getString("CREATED_DATE");
							String req[]=requestDate.split(" ");
							requestDate=req[0];
							String a[]=requestDate.split("-");
							requestDate=a[2]+"/"+a[1]+"/"+a[0];
							venorMasterRecords.setCreateDate(requestDate);
							String url="vendorMasterRequest.do?method=editVendorRecord";
							venorMasterRecords.setUrl(url);
							
							
							venorMasterRecords.setApprovalStatus(rsGetVenorMaster.getString("Approve_Status"));
							venorMasterRecords.setLastApprover(rsGetVenorMaster.getString("last_approver"));
							venorMasterRecords.setPendingApprover(rsGetVenorMaster.getString("pending_approver"));
							listOfMyRequest.add(venorMasterRecords);
							request.setAttribute("Vendor Master", "Vendor Master");	
							}
								request.setAttribute("vendor  list", "vendor  list");	
							}
								if(requiredType.equalsIgnoreCase("Service Master"))
								{
									String getServiceMaster="select c.request_no,c.plant_code,c.service_description,c.CREATED_DATE,c.app_satus,c.last_approver,c.pending_approver " +
											"from SERVICE_MASTER as c where  c.CREATED_BY='"+user.getEmployeeNo()+"'";
									if(!(requiredType.equalsIgnoreCase("")))
									{
										getServiceMaster=getServiceMaster;
									}
									if(!(reqStatus.equalsIgnoreCase("")))
									{
										getServiceMaster=getServiceMaster + "and c.app_satus='"+reqStatus+"'  order by c.request_no desc";
									}
									
									if(reqStatus.equalsIgnoreCase("All"))
									{
										getServiceMaster="select c.request_no,c.plant_code,c.service_description,c.CREATED_DATE,c.app_satus,c.last_approver,c.pending_approver " +
										"from SERVICE_MASTER as c where  c.CREATED_BY='"+user.getEmployeeNo()+"' order by c.request_no desc";
									}
									
									ResultSet rsServiceMaster=ad.selectQuery(getServiceMaster);
									while(rsServiceMaster.next()){
									MyRequestForm serviceMaster=new MyRequestForm();
									serviceMaster.setRequestNumber(rsServiceMaster.getString("request_no"));
									serviceMaster.setLocationName(rsServiceMaster.getString("plant_code"));
									serviceMaster.setRequestType("Service Master");
									serviceMaster.setServiceDescription(rsServiceMaster.getString("service_description"));
									String requestDate=rsServiceMaster.getString("CREATED_DATE");
									String req[]=requestDate.split(" ");
									requestDate=req[0];
									String a[]=requestDate.split("-");
									requestDate=a[2]+"/"+a[1]+"/"+a[0];
									serviceMaster.setCreateDate(requestDate);
									serviceMaster.setApprovalStatus(rsServiceMaster.getString("app_satus"));
									String url="serviceMasterRequest.do?method=editRecord";
									serviceMaster.setUrl(url);
									serviceMaster.setLastApprover(rsServiceMaster.getString("last_approver"));
									serviceMaster.setPendingApprover(rsServiceMaster.getString("pending_approver"));

									listOfMyRequest.add(serviceMaster);
									request.setAttribute("Service List", "Service List");	
									
								}
								}
								
								if(requiredType.equalsIgnoreCase("Code Extention"))
								{
									String getCodeExtention="select code.REQUEST_NO,code.TYPE,code.CREATED_DATE,MATERIAL_SHORT_NAME,MATERIAL_CODE_1,MATERIAL_CODE_2,PLANT_1,"
									+ "STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION,TO_STORAGE_LOCATION,code.APPROVE_TYPE,code.last_approver,"
									+ "code.pending_approver from code_extenstion_request as code where " +
									"code.CREATED_BY='"+user.getEmployeeNo()+"' ";
								
								if(!(reqStatus.equalsIgnoreCase("")))
								{
									getCodeExtention=getCodeExtention + "and code.APPROVE_TYPE='"+reqStatus+"' order by code.REQUEST_NO desc";
								}
								if(reqStatus.equalsIgnoreCase("All"))
									
								{
									getCodeExtention="select code.REQUEST_NO,code.TYPE,code.CREATED_DATE,MATERIAL_SHORT_NAME,MATERIAL_CODE_1,"
									+ "MATERIAL_CODE_2,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION,"
									+ "TO_STORAGE_LOCATION,code.APPROVE_TYPE,code.last_approver,code.pending_approver from code_extenstion_request as code where " +
									"code.CREATED_BY='"+user.getEmployeeNo()+"'  order by code.REQUEST_NO desc";
							}
								ResultSet rsCodeExtention=ad.selectQuery(getCodeExtention);
								while(rsCodeExtention.next()){
								MyRequestForm codeExtentionRec=new MyRequestForm();
								codeExtentionRec.setRequestNumber(rsCodeExtention.getString("REQUEST_NO"));
								codeExtentionRec.setPlantType(rsCodeExtention.getString("TYPE"));
								codeExtentionRec.setRequestType("Code Extention");
								
								codeExtentionRec.setDescription(rsCodeExtention.getString("MATERIAL_SHORT_NAME"));
								String sapNo="";
								if(rsCodeExtention.getString("TYPE").equals("Plant"))
									sapNo=rsCodeExtention.getString("MATERIAL_CODE_1");
								else
									sapNo=rsCodeExtention.getString("MATERIAL_CODE_2");
								codeExtentionRec.setSapNo(sapNo);
								String matType=rsCodeExtention.getString("TYPE");
								if(matType.equalsIgnoreCase("Plant"))
								{
									codeExtentionRec.setFromStorage(rsCodeExtention.getString("STORAGE_LOCATION_ID_1"));
									codeExtentionRec.setToStorage(rsCodeExtention.getString("EXTENDED_STORAGE_LOCATION_1"));
								}
								else{
								
								codeExtentionRec.setFromStorage(rsCodeExtention.getString("FROM_STORAGE_LOCATION"));
								codeExtentionRec.setToStorage(rsCodeExtention.getString("TO_STORAGE_LOCATION"));
								}
								String requestDate=rsCodeExtention.getString("CREATED_DATE");
								String req[]=requestDate.split(" ");
								requestDate=req[0];
								String a[]=requestDate.split("-");
								requestDate=a[2]+"/"+a[1]+"/"+a[0];
								codeExtentionRec.setCreateDate(requestDate);
								codeExtentionRec.setApprovalStatus(rsCodeExtention.getString("APPROVE_TYPE"));
								String url="materialCodeExtenstion.do?method=editCodeExtension";
								codeExtentionRec.setUrl(url);
								codeExtentionRec.setLastApprover(rsCodeExtention.getString("last_approver"));
								codeExtentionRec.setPendingApprover(rsCodeExtention.getString("pending_approver"));

								listOfMyRequest.add(codeExtentionRec);
								request.setAttribute("Code Extention", "Code Extention");	
								}
								
							
							
							request.setAttribute("Code Extention list", "Code Extention list");	
								}
								if(requiredType.equalsIgnoreCase("Leave"))
								{
									String approveMgr="";
									String getApprover="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp "
											+ "where ess.employeeNumber='"+user.getEmployeeNo()+"' and ess.essType='Leave' and emp.PERNR=ess.ApproverId";
									ResultSet rsApprover=ad.selectQuery(getApprover);
									if(rsApprover.next()){
										approveMgr=rsApprover.getString(1);
									}
									String getLeaveDetails="select l.Req_Id,t.lv_type,l.submit_date,l.start_date,l.start_duration,l.end_date,l.end_duration,l.Last_approver,l.Pending_approver,l.leave_status,emp.APPMGR,l.Approvel_Status from leave_details as l,Location as loc,lv_type_m as t,emp_official_info as emp " +
									"where loc.LOCID=l.plant_id and l.firstname='"+user.getEmployeeNo()+"'  and l.leave_type=t.lv_typeid and emp.PERNR=l.firstname  ";
									if(!(requiredType.equalsIgnoreCase("")))
									{
										getLeaveDetails=getLeaveDetails;
									}
									if(!(reqStatus.equalsIgnoreCase("")))
									{
										getLeaveDetails=getLeaveDetails + "and l.Approvel_Status='"+reqStatus+"' and record_status!='Draft' ORDER BY l.Req_Id  desc";
									}
								if((reqStatus.equalsIgnoreCase("All"))){
									getLeaveDetails="";
									getLeaveDetails="select l.Req_Id,t.lv_type,l.submit_date,l.start_date,l.start_duration,l.end_date,l.end_duration,l.Last_approver,l.Pending_approver,l.leave_status,emp.APPMGR,l.Approvel_Status from leave_details as l,Location as loc,lv_type_m as t,emp_official_info as emp " +
									"where loc.LOCID=l.plant_id and l.firstname='"+user.getEmployeeNo()+"'  and l.leave_type=t.lv_typeid and emp.PERNR=l.firstname  ";
									if(!(requiredType.equalsIgnoreCase("")))
									{
										getLeaveDetails=getLeaveDetails;
									}
									if(!(reqStatus.equalsIgnoreCase("")))
									{
										getLeaveDetails=getLeaveDetails + " ORDER BY l.Req_Id  desc";
									}
								}
									
									
							ResultSet rsLeaveDetails=ad.selectQuery(getLeaveDetails);
							while(rsLeaveDetails.next()){
							MyRequestForm leaveMaster=new MyRequestForm();
							leaveMaster.setRequestNumber(rsLeaveDetails.getString("Req_Id"));
							//leaveMaster.setLocationName(rsLeaveDetails.getString("location_code"));
							leaveMaster.setRequestType(rsLeaveDetails.getString("lv_type"));
							leaveMaster.setCreateDate(rsLeaveDetails.getString("submit_date"));
							leaveMaster.setStartDate(EMicroUtils.dateConvertDisplay(rsLeaveDetails.getString("start_date")));
							String stDur=rsLeaveDetails.getString("start_duration");
							if(stDur.equalsIgnoreCase("FD"))
								stDur="Full Day";
							if(stDur.equalsIgnoreCase("FH"))
								stDur="First Half";
							if(stDur.equalsIgnoreCase("FH"))
								stDur="Second Half";
							leaveMaster.setStartDateDuration(stDur);
							leaveMaster.setEndDate(EMicroUtils.dateConvertDisplay(rsLeaveDetails.getString("end_date")));
							
							String endDur=rsLeaveDetails.getString("end_duration");
							if(endDur.equalsIgnoreCase("FD"))
								endDur="Full Day";
							if(endDur.equalsIgnoreCase("FH"))
								endDur="First Half";
							if(endDur.equalsIgnoreCase("FH"))
								endDur="Second Half";
							leaveMaster.setEndDateDuration(endDur);
						
						
							leaveMaster.setApprover(approveMgr);
							leaveMaster.setApprovalStatus(rsLeaveDetails.getString("Approvel_Status"));
							String pendingApprover=rsLeaveDetails.getString("Pending_approver");
							if(pendingApprover.equalsIgnoreCase("0"))
								pendingApprover="No";
							leaveMaster.setPendingApprover(pendingApprover);
							
							String url="myRequest.do?method=editMyRequest";
							
							leaveMaster.setUrl(url);
							
							//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
							//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
							listOfMyRequest.add(leaveMaster);
							}
							
							request.setAttribute("Leave List", "Leave List");
								}
							
								if(requiredType.equalsIgnoreCase("Recruitment Request"))
								{
									String getLeaveDetails="select * from Recruitment_Request inner join Location on Location.LOCID = Recruitment_Request.Location_Code where Recruitment_Request.User_Id='"+user.getId()+"'";
									if(!(requiredType.equalsIgnoreCase("")))
									{
										getLeaveDetails=getLeaveDetails;
									}
									if(!(reqStatus.equalsIgnoreCase("")))
									{
										getLeaveDetails=getLeaveDetails + " and Recruitment_Request.Approvel_Status='"+reqStatus+"'";
									}
									ResultSet rsLeaveDetails=ad.selectQuery(getLeaveDetails);
									while(rsLeaveDetails.next()){
										MyRequestForm RRequest=new MyRequestForm();
										RRequest.setRequestNumber(rsLeaveDetails.getString("Recruitment_Id"));
										RRequest.setLocationName(rsLeaveDetails.getString("Location_Code"));
										RRequest.setRequestType(requiredType);
										String requestDate=rsLeaveDetails.getString("Request_Date");
										
										RRequest.setCreateDate(requestDate);
										RRequest.setApprovalStatus(rsLeaveDetails.getString("Approvel_Status"));
										
										RRequest.setLastApprover(rsLeaveDetails.getString("Last_approver"));
										String pendingApprover=rsLeaveDetails.getString("Pending_approver");
										if(pendingApprover.equalsIgnoreCase("0"))
											pendingApprover="No";
										RRequest.setPendingApprover(pendingApprover);
							
										String url="approvals.do?method=getRequestUploadInOpenings&reqId="+rsLeaveDetails.getString("Recruitment_Id")+"&reqType=Recruitment Request";
							
										RRequest.setUrl(url);
										//leaveMaster.setLastApprover(rsLeaveDetails.getString("Last_approver"));
										//leaveMaster.setPendingApprover(rsLeaveDetails.getString("Pending_approver"));
										listOfMyRequest.add(RRequest);
									}
								}
								if(requiredType.equalsIgnoreCase("On Duty"))
								{
									String getOndutyDetails="select l.request_no,l.submit_date,l.start_date,l.end_date,l.OnDuty_status,l.Last_approver,emp.EMP_FULLNAME," +
											"l.onDuty_Type,l.location,l.startTime ,l.endTime,l.no_of_days,l.Approver_Status   from OnDuty_details as l,emp_official_info as emp  " +
											"where  l.user_id='"+user.getEmployeeNo()+"'  and l.Approver_id=emp.PERNR ";
									if(!(requiredType.equalsIgnoreCase("")))
									{
										getOndutyDetails=getOndutyDetails;
									}
									if(!(reqStatus.equalsIgnoreCase("")))
									{
										if(reqStatus.equalsIgnoreCase("In Process"))
											reqStatus="0";
										if(reqStatus.equalsIgnoreCase("Approved"))
											reqStatus="1";
										if(reqStatus.equalsIgnoreCase("Rejected"))
											reqStatus="2";
										if(reqStatus.equalsIgnoreCase("Cancelled"))
											reqStatus="3";
										if(reqStatus.equalsIgnoreCase("Self Cancelled"))
											reqStatus="4";
										getOndutyDetails=getOndutyDetails + "and l.OnDuty_status='"+reqStatus+"' order by l.request_no desc";
									}
									
									if(reqStatus.equalsIgnoreCase("All"))
									{
										getOndutyDetails="select  l.request_no,l.submit_date,l.start_date,l.end_date,l.OnDuty_status,l.Last_approver," +
										"emp.EMP_FULLNAME,l.onDuty_Type,l.location,l.startTime ,l.endTime,l.no_of_days,l.Approver_Status from OnDuty_details as l," +
										"emp_official_info as emp  where  l.user_id='"+user.getEmployeeNo()+"'  and  l.Approver_id=emp.PERNR  order by l.request_no desc";
									}
									ResultSet rsOndutyDetails=ad.selectQuery(getOndutyDetails);
									while(rsOndutyDetails.next()){
										MyRequestForm onduty=new MyRequestForm();
										onduty.setRequestNumber(rsOndutyDetails.getString("request_no"));
										onduty.setLocationName(rsOndutyDetails.getString("location"));
										onduty.setRequestType("On Duty");
										String requestDate=rsOndutyDetails.getString("submit_date");
										int isthere = (requestDate.indexOf("/"));
										if(isthere <= 0){
											String req[]=requestDate.split(" ");
											requestDate=req[0];
											String a[]=requestDate.split("-");
											requestDate=a[2]+"/"+a[1]+"/"+a[0];
										}
										onduty.setCreateDate(requestDate);
										String startDate=rsOndutyDetails.getString("start_date");
										String a[]=startDate.split(" ");
										startDate=a[0];
										String b[]=startDate.split("-");
										startDate=b[2]+"/"+b[1]+"/"+b[0];
										onduty.setStartDate(startDate);
										String endDate=rsOndutyDetails.getString("end_date");
										String c[]=endDate.split(" ");
										endDate=c[0];
										String d[]=endDate.split("-");
										endDate=d[2]+"/"+d[1]+"/"+d[0];
										onduty.setEndDate(endDate);
										onduty.setOnDutyType(rsOndutyDetails.getString("onDuty_Type"));
										
										onduty.setStartTime(rsOndutyDetails.getString("startTime"));
										
										onduty.setEndTime(rsOndutyDetails.getString("endTime"));
										onduty.setNoOfDays(rsOndutyDetails.getNString("no_of_days"));
										
										
										int OnDuty_status=rsOndutyDetails.getInt("OnDuty_status");
										String status="";
										if(OnDuty_status==0)
										{
											status="In Process";
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
										onduty.setStatus(status);
										
										onduty.setPendingApprover(rsOndutyDetails.getString("EMP_FULLNAME"));
										
										String url="";
										
										onduty.setUrl(url);
										
										listOfMyRequest.add(onduty);
										request.setAttribute("ondutyList", "ondutyList");
										
										
									}
								}
								if(requiredType.equalsIgnoreCase("Permission"))
								{
									String getPermissionDetails="select request_no,date,startTime,endTime,reason,Approver_Status,emp.EMP_FULLNAME " +
									"from Permission_details as p,emp_official_info as emp where user_id='"+user.getEmployeeNo()+"' and emp.PERNR=p.Approver_id ";
									
									
									if(!(reqStatus.equalsIgnoreCase("")))
									{
										if(reqStatus.equalsIgnoreCase("In Process"))
											reqStatus="0";
										if(reqStatus.equalsIgnoreCase("Approved"))
											reqStatus="1";
										if(reqStatus.equalsIgnoreCase("Rejected"))
											reqStatus="2";
										if(reqStatus.equalsIgnoreCase("Cancelled"))
											reqStatus="3";
										getPermissionDetails=getPermissionDetails + "and Approver_Status='"+reqStatus+"' order by request_no desc";
									}
									if(reqStatus.equalsIgnoreCase("All"))
									{
										getPermissionDetails="select request_no,date,startTime,endTime,reason,Approver_Status,emp.EMP_FULLNAME " +
										"from Permission_details as p,emp_official_info as emp where user_id='"+user.getEmployeeNo()+"' and emp.PERNR=p.Approver_id order by request_no desc";
										
									}
										
									
									
									ResultSet rsPermission=ad.selectQuery(getPermissionDetails);
									while(rsPermission.next())
									{
										MyRequestForm permission=new MyRequestForm();
										permission.setRequestNumber(rsPermission.getString("request_no"));
							            permission.setStartTime(rsPermission.getString("startTime"));
								          permission.setEndTime(rsPermission.getString("endTime"))		;
							        	permission.setRequestType("Permission");
										String requestDate=rsPermission.getString("date");
										int isthere = (requestDate.indexOf("/"));
										if(isthere <= 0){
											String req[]=requestDate.split(" ");
											requestDate=req[0];
											String a[]=requestDate.split("-");
											requestDate=a[2]+"/"+a[1]+"/"+a[0];
										}
										permission.setCreateDate(requestDate);
										int permission_status=rsPermission.getInt("Approver_Status");
										if(permission_status==0)
											permission.setApprovalStatus("In Process");
										if(permission_status==1)
											permission.setApprovalStatus("Approved");
										if(permission_status==2)
											permission.setApprovalStatus("Rejected");
										if(permission_status==3)
											permission.setApprovalStatus("Cancelled");
										permission.setPendingApprover(rsPermission.getString("EMP_FULLNAME"));
										listOfMyRequest.add(permission);
										request.setAttribute("permission", "permission");	
									}
								
								}
								
							}catch (Exception e) {
								e.printStackTrace();
							}
							return listOfMyRequest;
						}
						private void setFilterType(String requestType, MyRequestForm myReqForm){
							LinkedList defaultFilter=new LinkedList();
							defaultFilter.add(0,"Pending");
							defaultFilter.add(1,"Approved");
							defaultFilter.add(2,"Rejected");
							LinkedList filter=new LinkedList();
							System.out.println("size -> "+defaultFilter.size());
							for(int i = 0; i < defaultFilter.size(); i++){
								String selectedFilter = defaultFilter.get(i).toString();
								if(requestType.equalsIgnoreCase(selectedFilter)){
									System.out.println("if "+selectedFilter);
									filter.addFirst(selectedFilter);
								}
								else{
									System.out.println("else "+selectedFilter);
									filter.add(selectedFilter);
								}
							}
							myReqForm.setFilterType(filter);
							
						}
						public ActionForward searchInMyRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
							HttpSession session=request.getSession();
							UserInfo user=(UserInfo)session.getAttribute("user");
							int userID=user.getId();
							MailInboxAction mAction = new MailInboxAction();
							
							MyRequestForm myReqForm = (MyRequestForm) form;
							NewsandMediaDao ad=new NewsandMediaDao();
							String sTxt = request.getParameter("searchTxt");
							EMailer email = new EMailer();
							String filter = request.getParameter("filter");
							String requestName = myReqForm.getReqRequstType();
							myReqForm.setSearchText(sTxt);
							ArrayList searchList=new ArrayList();
							setFilterType(filter, myReqForm);
							try{
								int  startAppCount=0;
								int  endAppCount=0;
								String scount = request.getParameter("sCount");
								if(scount != null){
									startAppCount=Integer.parseInt(scount);
									endAppCount=Integer.parseInt(request.getParameter("eCount"));
								}
								mAction.basicNavigation(request);
								int rowCount=0;
								String tName = email.getTableNameForRequest(requestName);
								String searchQueryCount="select count(*) from All_Request where Requester_Name ='"+user.getUserName()+"' and (Req_Type like '%"+sTxt+"%' or Req_Date like '%"+sTxt+"%' or Last_Approver like '%"+sTxt+"%' or Pending_Approver like '%"+sTxt+"%' or Comments like '%"+sTxt+"%')";
								ResultSet countrs=ad.selectQuery(searchQueryCount);
								while(countrs.next()) {
										rowCount=Integer.parseInt(countrs.getString(1));
								}
								int searchTotal = rowCount;
								if(startAppCount == 0){
									if(searchTotal == 0){
										myReqForm.setMessage("No Result for Given Text");
										request.setAttribute("noRecords","noRecords");
									}
									else if(searchTotal <= mAction.MAIL_PER_PAGE)
									{
										startAppCount=1;
										endAppCount=searchTotal;
									}
									else
									{
										startAppCount=1;
										endAppCount=mAction.MAIL_PER_PAGE;
										mAction.enableNextNavigation(request);
									}
									/*InList = getLimitedItems(request,tablename,startAppCount,endAppCount);
									setAllCount(mailInForm,InboxTotal,startAppCount,endAppCount);*/
								}
								else{
									String naviType = request.getParameter("fnpl");
									if(naviType.equalsIgnoreCase("next")){
										startAppCount=(startAppCount+mAction.MAIL_PER_PAGE);
										endAppCount=(endAppCount+mAction.MAIL_PER_PAGE);
										mAction.enablePreviousNavigation(request);
										if(searchTotal <= endAppCount)
										{
											mAction.disableNextNavigation(request);
											endAppCount = searchTotal;
										}
										else
										{
											mAction.enableNextNavigation(request);
										}
									}
									else if(naviType.equalsIgnoreCase("priv"))
									{
										startAppCount=(startAppCount-mAction.MAIL_PER_PAGE);
										int ecount=0;
										if(endAppCount == searchTotal)
										{
											int ec = endAppCount/mAction.MAIL_PER_PAGE;
											ecount = ec;
											endAppCount=(ecount*mAction.MAIL_PER_PAGE);
										}
										else
											endAppCount=(endAppCount-mAction.MAIL_PER_PAGE);
										if(endAppCount == mAction.MAIL_PER_PAGE)
										{
											mAction.disablePreviousNavigation(request);
											mAction.enableNextNavigation(request);
										}
										else{
											mAction.enablePreviousNavigation(request);
											mAction.enableNextNavigation(request);
										}
									}
									else if(naviType.equalsIgnoreCase("alast")){
										int ecount=0;
										endAppCount = searchTotal;
										int typeStart = endAppCount%mAction.MAIL_PER_PAGE;
										System.out.println("mod "+typeStart);
										if(typeStart == 0){
											int ec = endAppCount-mAction.MAIL_PER_PAGE;
											ecount = ec;
											startAppCount=ecount+1;
										}
										else{
											int ec = endAppCount/mAction.MAIL_PER_PAGE;
											ecount = ec;
											startAppCount=(ecount*mAction.MAIL_PER_PAGE)+1;
										}
										mAction.enablePreviousNavigation(request);
										mAction.disableNextNavigation(request);
									}
									else{
										startAppCount=1;
										endAppCount = mAction.MAIL_PER_PAGE;
										mAction.disablePreviousNavigation(request);
										mAction.enableNextNavigation(request);
									}
									
									
									/*InList = getLimitedItems(request,tablename,startAppCount,endAppCount);
									setAllCount(mailInForm,InboxTotal,startAppCount,endAppCount);*/
								}
								setAllCount(myReqForm,searchTotal,startAppCount,endAppCount);
								String searchQuery="select * from All_Request where Requester_Name ='"+user.getUserName()+"' and (Req_Type like '%"+sTxt+"%' or Req_Date like '%"+sTxt+"%' or Last_Approver like '%"+sTxt+"%' or Pending_Approver like '%"+sTxt+"%' or Comments like '%"+sTxt+"%')";
								System.out.println(searchQuery);
								if(filter.equalsIgnoreCase("Approved")){
									searchQuery="select * from All_Request where Requester_Name ='"+user.getUserName()+"' and (Req_Type like '%"+sTxt+"%' or Req_Date like '%"+sTxt+"%' or Last_Approver like '%"+sTxt+"%' or Pending_Approver like '%"+sTxt+"%' or Comments like '%"+sTxt+"%')";
								}
								ResultSet srchRS=ad.selectQuery(searchQuery);
								int count = 0;
								String request_Name= "";
								Date today = new Date();
								while(srchRS.next()) {
									
									
									int startRow = srchRS.getRow();
									if(startRow >= startAppCount)
									{
										MyRequestForm mrform = new MyRequestForm();
										String reqId = srchRS.getString("Req_Id");
										if(reqId.indexOf("_") > 0){
											reqId = reqId.substring(reqId.indexOf("_"), reqId.length());
										}
										mrform.setRequestNumber(reqId);
										//mrform.set(srchRS.getString("Requester_name"));
										request_Name = srchRS.getString("Req_Type");
										mrform.setRequestType(request_Name);
										mrform.setCreateDate(srchRS.getString("Req_Date"));
										myReqForm.setRequestType(request_Name);
										mrform.setLastApprover(srchRS.getString("Last_Approver"));
										mrform.setPendingApprover(srchRS.getString("Pending_Approver"));
										mrform.setUrl("approvals.do?method=getSelectedRequestToApprove&reqId="+srchRS.getString("Req_Id")+"&reqType="+request_Name+"&sCount="+startAppCount+"&eCount="+endAppCount+"&searchTxt="+sTxt);
										myReqForm.setSelectedFilter(filter);
										searchList.add(mrform);
										count++;
										if(count == mAction.MAIL_PER_PAGE)
										{
											break;
										}
									}
								}
							}
							catch(SQLException se){
								se.printStackTrace();
							}
							request.setAttribute("header", "Pending Request");
							request.setAttribute("openRequest", "");
							request.setAttribute("myRequestList", searchList);
							myReqForm.setListOfrecords(searchList);
							String result=(String)session.getAttribute("result");
							System.out.println("result="+result);
							if(result==null||result==" ")
							session.setAttribute("result"," ");
							
							
							if(request.getParameter("id")!=null){
							
							request.setAttribute("MenuIcon", request.getParameter("id"));
							}
							myReqForm.setHeading("Search result for "+sTxt+" in "+filter);
							String returnMsg="displayMyRequestList";
							return mapping.findForward(returnMsg);
						}
						
						private void setAllCount(MyRequestForm mailForm,int totalCount, int sCount, int eCount){
							mailForm.setTotal(totalCount);
							mailForm.setStartRecord(sCount);
							mailForm.setEndRecord(eCount);
					    }
						
						public ActionForward exportdisplayMyRequestList(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response) {
							MyRequestForm myReqForm = (MyRequestForm) form;
							setFilterType("Pending", myReqForm);
							ArrayList listOfMyRequest=new ArrayList();
							try{
								String r=myReqForm.getReqRequstType();
								String s=myReqForm.getReqStatus();
							  
							    	listOfMyRequest=searchMyRequestList(mapping, myReqForm, request, response);
					
					      
							
							ArrayList newList=new ArrayList();
							
							Iterator it=listOfMyRequest.iterator();
							
							int i=0;
							while(it.hasNext()){
								if(it.hasNext()){
									newList.add(it.next());
								i++;
								}
								else
									break;
							}
							
							
							request.setAttribute("displayRecordNo","ok");
							
							  //myReqForm.setMyRequestRecords(listOfMyRequest);
							request.setAttribute("myRequestList", newList);
							//request.setAttribute("myRequestList1", listOfMyRequest);
							
							}catch (Exception e) {
								e.printStackTrace();
							}
							myReqForm.setHeading("My Request");
							myReqForm.setListOfrecords(listOfMyRequest);
							
							if(listOfMyRequest.size()==0)
							{
								myReqForm.setMessage("No Records Found.");
								request.setAttribute("noRecords", "noRecords");
							}
							
							return mapping.findForward("exportdisplayMyRequestList");
						}
						
						
					}

	
