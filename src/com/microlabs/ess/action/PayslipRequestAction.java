package com.microlabs.ess.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.PayslipRequestForm;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class PayslipRequestAction extends DispatchAction{
	EssDao ad=new EssDao();
	
	
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
	
	public int getCountForTable(String reqStatus, String fullName, String reqType){
    	int rowCount = 0;
    	MainDao exeQry = new MainDao();
    	String reqList="select count(*) from All_Request where Pending_approver='"+fullName+"' and Req_Status='Pending' and Req_Type='"+reqType+"'";
    	
    	if(reqStatus.equalsIgnoreCase("Open")){
			reqList="select count(*) from All_Request where Pending_approver='"+fullName+"' and Req_Status='Pending' and Req_Type='"+reqType+"'";
		}
    	
    	if(reqStatus.equalsIgnoreCase("Closed")||reqStatus.equalsIgnoreCase("ReOpen")){
			reqList="select count(*) from All_Request where Last_Approver='"+fullName+"' and Req_Status='Approved' and   (Actual_Approver_status is null or Actual_Approver_status='')  and Req_Type='"+reqType+"'";
		}
    	
		
		else if(reqStatus.equalsIgnoreCase("All")){
			reqList="select count(*) from All_Request where (Last_Approver ='"+fullName+"' or Pending_Approver = '"+fullName+"')  and Req_Type='"+reqType+"'";
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
	
	public ActionForward ExportHRquerylist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PayslipRequestForm approvalsForm=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String exportdate=ft.format(dNow);

		response.setHeader("Content-Disposition", "inline; filename="+exportdate+"_HR_QUERY_Report.xls");
		
		
		
		String start=approvalsForm.getDatefrom();
		String end=approvalsForm.getDateto();
		
		String[] g=start.split("/");
		start=g[2]+"-"+g[1]+"-"+g[0];
		String[] j=end.split("/");
		end=j[2]+"-"+j[1]+"-"+j[0];
		
		ArrayList query=new ArrayList();
		String getcomoff ="";
		if(!approvalsForm.getSelectedFilter().equalsIgnoreCase("All"))		
		 getcomoff = "select CONVERT(varchar(10),HR_QUERIES.Req_date,103)+' '+ CONVERT(varchar(5),HR_QUERIES.Req_date,108) as Req_date1, CONVERT(varchar(10),HR_QUERY_TRANSACTION.updated_time,103)+' '+ CONVERT(varchar(5),HR_QUERY_TRANSACTION.updated_time,108) as updated_time1,* from HR_QUERIES left outer join HR_QUERY_TRANSACTION on HR_QUERY_TRANSACTION.id=HR_QUERIES.id   where (convert(date,req_date) between '"+start+"' and '"+end+"') and status='"+approvalsForm.getSelectedFilter()+"'  order by HR_QUERIES.id,Updated_Time desc";
		else
			getcomoff = "select CONVERT(varchar(10),HR_QUERIES.Req_date,103)+' '+ CONVERT(varchar(5),HR_QUERIES.Req_date,108) as Req_date1, CONVERT(varchar(10),HR_QUERY_TRANSACTION.updated_time,103)+' '+ CONVERT(varchar(5),HR_QUERY_TRANSACTION.updated_time,108) as updated_time1,* from HR_QUERIES left outer join HR_QUERY_TRANSACTION on HR_QUERY_TRANSACTION.id=HR_QUERIES.id   where (convert(date,req_date) between '"+start+"' and '"+end+"')   order by HR_QUERIES.id,Updated_Time desc";	
		
		ResultSet ff = ad.selectQuery(getcomoff);
		try {
			while (ff.next()) {
				PayslipRequestForm statusForm =new PayslipRequestForm();	

				statusForm.setReq_id(ff.getInt("id"));
				
			statusForm.setSubject(ff.getString("subject"));
			statusForm.setDescription(ff.getString("description"));
		statusForm.setReq_date(ff.getString("Req_date1"));
		statusForm.setStatus(ff.getString("Status"));
			
			statusForm.setReq_by(ff.getInt("req_by"));
			
				
				String[] a=null;
				
				a=empDet(Integer.toString(ff.getInt("req_by")));
				statusForm.setEmpname(a[0]);
		
				
				statusForm.setHrName(Empname(Integer.toString(ff.getInt("Updated_by"))));
				statusForm.setComments(ff.getString("Comments"));
				statusForm.setApprvddate(ff.getString("updated_time1"));
				statusForm.setCategory(ff.getString("category"));
				query.add(statusForm);
	
		}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("Usrreq", query);
		
		
		if(query.size()>0)
		return mapping.findForward("ExportHRquery");
		
		else
		{
			approvalsForm.setMessage("No records to export");
		return mapping.findForward("pendingHRquery");
		}
			
		
	}
	
	public ActionForward displayHRquerylist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PayslipRequestForm approvalsForm=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
		
		return mapping.findForward("pendingHRquery");
	}
	
	
	public ActionForward statusChangeHRQuery(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		PayslipRequestForm statusForm = (PayslipRequestForm) form;
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		EssDao ad = new EssDao();
	
		String filterBy = statusForm.getSelectedFilter();
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateNow = ft.format(dNow);
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
	
		String locId = "";
		String floor = "";
		String room = "";
		String requesterID = "";
		int reqId = statusForm.getReq_id();
		String submit_date="";
		//
		if (true) {
			
			
			String trna="insert into HR_QUERY_TRANSACTION(id,Updated_by,Updated_Time,Comments) values('"+reqId+"','"+user.getEmployeeNo()+"',getdate(),'"+statusForm.getComments()+"')";
			int trnahg = ad.SqlExecuteUpdate(trna);
			if(trnahg>0)
				statusForm.setMessage("Query Response Submitted Successfully");
			
			
			String updateStatus = "update All_Request set Req_Status='Approved',Comments='"
					+ statusForm.getComments()
					+ "',Last_Approver=Pending_Approver,"
					+ "Pending_Approver='',Approved_Persons=Pending_Approver,approved_date='"
					+ dateNow
					+ "',Actual_Approver='"
					+ user.getEmployeeNo()
					+ "' where Req_Id='"
					+ reqId
					+ "' and Req_Type='User Request' and Req_status='Pending'";
			int saveStatus = 0;
			saveStatus = ad.SqlExecuteUpdate(updateStatus);

			
			
				String uu="update Hr_queries set Status='Closed',Approved_date=getdate(),Pending_Approver='No',Last_Approver='"+user.getFirstName()+"' where id='"+reqId+"'";
				int rewer = ad.SqlExecuteUpdate(uu);
				
				
				
				
			
			
		}
		
		ArrayList query=new ArrayList();
		String getcomoff = "select * from HR_QUERIES where id='"+reqId+"'";
		ResultSet ff = ad.selectQuery(getcomoff);
		try {
			while (ff.next()) {
				

				
				
			statusForm.setSubject(ff.getString("subject"));
			statusForm.setDescription(ff.getString("description"));
		
			
			statusForm.setReq_by(ff.getInt("req_by"));
				
				String[] a=null;
				
				a=empDet(Integer.toString(ff.getInt("req_by")));
				statusForm.setEmpname(a[0]);
				statusForm.setEmpdep(a[2]);
				statusForm.setEmpdesg(a[3]);
				statusForm.setEmploc(a[1]);
				statusForm.setEmpext(a[4]);
	
		}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("query", query);
		
		LinkedList trnalist = new LinkedList();
		String ypu="select designation.dsgstxt,CONVERT(varchar(10),HR_QUERY_TRANSACTION.updated_time,103)+' '+ CONVERT(varchar(5),HR_QUERY_TRANSACTION.updated_time,108) as updated_time1,"
				+ "HR_QUERY_TRANSACTION.*,EMP_FULLNAME from HR_QUERY_TRANSACTION,emp_official_info,designation where HR_QUERY_TRANSACTION.id='"+reqId+"' and emp_official_info.pernr=Updated_by"
				+ " and  designation.DSGID=emp_official_info.DSGID order by updated_time desc";
		ResultSet n=ad.selectQuery(ypu);
		try {
			while(n.next())
			{
				PayslipRequestForm yo=new PayslipRequestForm();
				yo.setEmpname(n.getString("EMP_FULLNAME"));
				yo.setComments(n.getString("Comments"));
				yo.setApprvddate(n.getString("Updated_Time1"));
				yo.setEmpdesg(n.getString("dsgstxt"));
				trnalist.add(yo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(trnalist.size()>0)
		request.setAttribute("trnalist", trnalist);
		
		String app = "";
		// PLANT HEAD
		if (filterBy.equalsIgnoreCase("Pending")||filterBy.equalsIgnoreCase("Open")) {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='User Request' and a.Req_Id='"
					+ reqId
					+ "' and e.PERNR=a.Pending_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID "
					+ "union "
					 +"select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where Req_Type='User Request' and a.Req_Id='"
						+ reqId
						+ "' and e.PERNR=a.Actual_Approver and e.DPTID=dep.DPTID and e.DSGID=desg.DSGID ";

		} else {
			app = "select a.*,e.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from All_Request a,emp_official_info e,DEPARTMENT dep,DESIGNATION desg where  Req_Type='User Request' and a.Req_Id='"
					+ reqId
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

			e.printStackTrace();
		}
		pendingRecords(mapping, statusForm, request, response);
		return mapping.findForward("pendingHRquery");
	}
	
	
	public ActionForward pendingRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PayslipRequestForm approvalsForm=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
		int totalRecords = getCountForTable(approvalsForm.getSelectedFilter(),user.getEmployeeNo(),"User Request");
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
			 
			 if(approvalsForm.getSelectedFilter().equalsIgnoreCase(""))
			 {
				 approvalsForm.setSelectedFilter("New");
			 }
			 
			 if(totalRecords>0)
			 {
				 
				 if (true) {
						LinkedList Usrreq = new LinkedList();
						String getITRecords = "";

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("New")||approvalsForm.getSelectedFilter().equalsIgnoreCase("Open")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Pending' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between 1 and 10 order by Req_Id desc";

						}

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Closed")||approvalsForm.getSelectedFilter().equalsIgnoreCase("ReOpen")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Approved' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between 1 and 10 order by Req_Id desc";

						}
					

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("All")) {

							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where (all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' or all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "')  and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between 1 and 10 order by Req_Id desc";

						}
						ResultSet rsit = ad.selectQuery(getITRecords);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_Id"));
								leave.setEmployeeName(rsit.getString("EMP_FULLNAME"));
								leave.setSubmitDate(EMicroUtils.display(rsit
										.getDate("Req_Date")));
								
                            
                                leave.setStatus(rsit.getString("status"));
                           
                                
								leave.setReason(rsit.getString("subject"));
								leave.setLeaveType(rsit.getString("category"));
								leave.setLapprover(rsit.getString("Last_approver"));
								leave.setPapprover(rsit.getString("Pending_approver"));
								Usrreq.add(leave);

							}
							if (Usrreq.size() > 0) {
								if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Pending")) {
									// request.setAttribute("displayButton",
									// "displayButton");
								}
							}
							request.setAttribute("Usrreq", Usrreq);
							if (Usrreq.size() == 0) {
								request.setAttribute("no Usrreq",
										"no Usrreq");
								
								 request.setAttribute("displayRecordNo", "");
								
							}
							
							if(Usrreq.size() < 10)
							{
								request.setAttribute("displayRecordNo", "");
							}
						} catch (SQLException e) {

							e.printStackTrace();
						}

					}
			 }
			 else
			 {
				 request.setAttribute("no Usrreq",
							"no Usrreq");
			 }
			 
			 
			 
				return mapping.findForward("pendingHRquery");
		
	}
	
	public ActionForward curentRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
 
		
 
		

		
		PayslipRequestForm approvalsForm=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
	
		
		int totalRecords=approvalsForm.getTotalRecords();//21
		int startRecord=approvalsForm.getStartRecord();//11
		int endRecord=approvalsForm.getEndRecord();	
		
		
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(startRecord);
		approvalsForm.setEndRecord(endRecord);
		 
		 
			
			 
			
				 
				 if (true) {
						LinkedList Usrreq = new LinkedList();
						String getITRecords = "";

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("New")||approvalsForm.getSelectedFilter().equalsIgnoreCase("Open")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Pending' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Closed")||approvalsForm.getSelectedFilter().equalsIgnoreCase("ReOpen")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Approved' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}
					

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("All")) {

							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where (all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' or all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "')  and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}
						ResultSet rsit = ad.selectQuery(getITRecords);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_Id"));
								leave.setEmployeeName(rsit.getString("EMP_FULLNAME"));
								leave.setSubmitDate(EMicroUtils.display(rsit
										.getDate("Req_Date")));
								
							    leave.setStatus(rsit.getString("status"));
                                
								leave.setReason(rsit.getString("subject"));
								leave.setLapprover(rsit.getString("Last_approver"));
								leave.setPapprover(rsit.getString("Pending_approver"));
								Usrreq.add(leave);

							}
							if (Usrreq.size() > 0) {
								if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Pending")) {
									// request.setAttribute("displayButton",
									// "displayButton");
								}
							}
							request.setAttribute("Usrreq", Usrreq);
							
								
								
								
				 
						} catch (SQLException e) {

							e.printStackTrace();
						}

					}
				  if(totalRecords>10){
			    	   
				       
						 
						 if(startRecord==1)
						 {
							 request.setAttribute("disablePreviousButton", "disablePreviousButton"); 
						 }
						 if(endRecord>10)
						 {
							 request.setAttribute("previousButton", "previousButton"); 
						 }
						 
						 if(endRecord==totalRecords)
						 {
							request.setAttribute("nextButton", "");
							request.setAttribute("disableNextButton", "disableNextButton");
						 }
						 if(endRecord<totalRecords)
						 {
							 request.setAttribute("nextButton", "nextButton");
						 }
						 request.setAttribute("displayRecordNo", "displayRecordNo");
				       }
						 approvalsForm.setTotalRecords(totalRecords);
						 approvalsForm.setStartRecord(startRecord);
						 approvalsForm.setEndRecord(endRecord);
			 
	
				return mapping.findForward("pendingHRquery");
		
	
		
		
	
		
		
	
		
		
	
		
		
		
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
 
		

		
		PayslipRequestForm approvalsForm=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
	
		
		int totalRecords=approvalsForm.getTotalRecords();//21
		int startRecord=approvalsForm.getStartRecord();//11
		int endRecord=approvalsForm.getEndRecord();	
		
		
		 startRecord=totalRecords-9;
		 endRecord=totalRecords;
		 approvalsForm.setTotalRecords(totalRecords);
		 approvalsForm.setStartRecord(startRecord);
		 approvalsForm.setEndRecord(totalRecords);
		 
			
			 
			
				 
				 if (true) {
						LinkedList Usrreq = new LinkedList();
						String getITRecords = "";

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("New")||approvalsForm.getSelectedFilter().equalsIgnoreCase("Open")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Pending' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Closed")||approvalsForm.getSelectedFilter().equalsIgnoreCase("ReOpen")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Approved' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}
					

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("All")) {

							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where (all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' or all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "')  and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}
						ResultSet rsit = ad.selectQuery(getITRecords);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_Id"));
								leave.setEmployeeName(rsit.getString("EMP_FULLNAME"));
								leave.setSubmitDate(EMicroUtils.display(rsit
										.getDate("Req_Date")));
							    leave.setStatus(rsit.getString("status"));
								leave.setReason(rsit.getString("subject"));
								leave.setLeaveType(rsit.getString("category"));
								leave.setLapprover(rsit.getString("Last_approver"));
								leave.setPapprover(rsit.getString("Pending_approver"));
								Usrreq.add(leave);

							}
							if (Usrreq.size() > 0) {
								if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Pending")) {
									// request.setAttribute("displayButton",
									// "displayButton");
								}
							}
							request.setAttribute("Usrreq", Usrreq);
							if(Usrreq.size()==0){
								request.setAttribute("no records", "no  records");
							
							}
							
						request.setAttribute("disableNextButton",
								"disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if (Usrreq.size() < 10) {

							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("disablePreviousButton",
									"disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");
				 
						} catch (SQLException e) {

							e.printStackTrace();
						}

					}
			
			 
	
				return mapping.findForward("pendingHRquery");
		
	
		
		
	
		
		
	
		
		
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		

		
		PayslipRequestForm approvalsForm=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
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
			
			 
			
				 
				 if (true) {
						LinkedList Usrreq = new LinkedList();
						String getITRecords = "";

											
						
						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("New")||approvalsForm.getSelectedFilter().equalsIgnoreCase("Open")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Pending' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Closed")||approvalsForm.getSelectedFilter().equalsIgnoreCase("ReOpen")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Approved' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}
					

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("All")) {

							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where (all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' or all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "')  and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}
						ResultSet rsit = ad.selectQuery(getITRecords);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_Id"));
								leave.setEmployeeName(rsit.getString("EMP_FULLNAME"));
								leave.setSubmitDate(EMicroUtils.display(rsit
										.getDate("Req_Date")));
								
							    leave.setStatus(rsit.getString("status"));
							    leave.setLeaveType(rsit.getString("category"));
								leave.setReason(rsit.getString("subject"));
								leave.setLapprover(rsit.getString("Last_approver"));
								leave.setPapprover(rsit.getString("Pending_approver"));
								Usrreq.add(leave);

							}
							if (Usrreq.size() > 0) {
								if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Pending")) {
									// request.setAttribute("displayButton",
									// "displayButton");
								}
							}
							request.setAttribute("Usrreq", Usrreq);
							if (Usrreq.size() == 0) {
								request.setAttribute("no it records", "no it records");
								
							}
							request.setAttribute("displayRecordNo", "displayRecordNo");

							approvalsForm.setTotalRecords(totalRecords);
							approvalsForm.setStartRecord(startRecord);
							approvalsForm.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							if (startRecord != 1)
								request.setAttribute("previousButton", "previousButton");
							request.setAttribute("displayRecordNo", "displayRecordNo");
							if (Usrreq.size() < 10) {
								approvalsForm.setStartRecord(1);
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton",
										"disablePreviousButton");
							}
				 
						} catch (SQLException e) {

							e.printStackTrace();
						}

					}
			
			 
	
				return mapping.findForward("pendingHRquery");
		
	
		
		
	
		
		
	}
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		
		PayslipRequestForm approvalsForm=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
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
			
			 
			
				 
				 if (true) {
						LinkedList Usrreq = new LinkedList();
						String getITRecords = "";

					
						
						
						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("New")||approvalsForm.getSelectedFilter().equalsIgnoreCase("Open")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Pending' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Closed")||approvalsForm.getSelectedFilter().equalsIgnoreCase("ReOpen")) {
							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' and all_R.Req_Status='Approved' and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' and it.status='"+approvalsForm.getSelectedFilter()+"' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}
					

						if (approvalsForm.getSelectedFilter().equalsIgnoreCase("All")) {

							getITRecords = "Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,it.status,it.subject,it.category,it.Last_approver,it.Pending_approver,all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type"
									+ " from  All_Request as all_R,emp_official_info as  emp, HR_QUERIES as it where (all_R.Last_Approver='"
									+ user.getEmployeeNo()
									+ "' or all_R.Pending_Approver='"
									+ user.getEmployeeNo()
									+ "')  and all_R.Req_Id=it.id and  all_R.Requester_Name=emp.PERNR "
									+ "and all_R.Req_Type='User Request' group by all_R.Req_Id,all_R.Requester_Name,emp.EMP_FULLNAME,all_R.Req_Status,all_R.Req_Date,all_R.type,it.subject,it.category,it.Last_approver,it.status,it.Pending_approver) as  sub Where   sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";

						}
						ResultSet rsit = ad.selectQuery(getITRecords);
						try {
							while (rsit.next()) {
								LeaveForm leave = new LeaveForm();
								leave.setRequestNumber(rsit.getInt("Req_Id"));
								leave.setEmployeeName(rsit.getString("EMP_FULLNAME"));
								leave.setSubmitDate(EMicroUtils.display(rsit
										.getDate("Req_Date")));
								
							    leave.setStatus(rsit.getString("status"));
							    leave.setLeaveType(rsit.getString("category"));
								leave.setReason(rsit.getString("subject"));
								leave.setLapprover(rsit.getString("Last_approver"));
								leave.setPapprover(rsit.getString("Pending_approver"));
								Usrreq.add(leave);

							}
							if (Usrreq.size() > 0) {
								if (approvalsForm.getSelectedFilter().equalsIgnoreCase("Pending")) {
									// request.setAttribute("displayButton",
									// "displayButton");
								}
							}
							request.setAttribute("Usrreq", Usrreq);
							if(Usrreq.size()!=0)
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
						 if(Usrreq.size()<10)
						 {
							 approvalsForm.setTotalRecords(totalRecords);
							 approvalsForm.setStartRecord(startRecord);
							 approvalsForm.setEndRecord(startRecord+Usrreq.size()-1);
								request.setAttribute("nextButton", "");
								request.setAttribute("disableNextButton", "disableNextButton");
								request.setAttribute("previousButton", "previousButton"); 
							 
						 }
						} catch (SQLException e) {

							e.printStackTrace();
						}

					}
			
			 
		}
		 if(endRecord==totalRecords)
		 {
			 request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
		 }
		 request.setAttribute("displayRecordNo", "displayRecordNo");
				return mapping.findForward("pendingHRquery");
		
	
		
		
	}
	
	public ActionForward SubmitHRqueryUSERRESPONSE(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		PayslipRequestForm pendAppForm = (PayslipRequestForm) form;
		int reqId = pendAppForm.getReq_id();
		EMailer email = new EMailer();	
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		String categ=pendAppForm.getCategory();
		
		String trna="insert into HR_QUERY_TRANSACTION(id,Updated_by,Updated_Time,Comments) values('"+reqId+"','"+user.getEmployeeNo()+"',getdate(),'"+pendAppForm.getComments()+"')";
		int trnahg = ad.SqlExecuteUpdate(trna);
		if(trnahg>0)
			pendAppForm.setMessage("Request Sent to HR Successfully");
		
		
		
		
		String hrap="select distinct HR_ID from HR_QUERIES_APPROVERS where role=(select role from hr_queries where id='"+reqId+"') and location='"+user.getPlantId()+"'  and category='"+categ+"'";
		ResultSet sad=ad.selectQuery(hrap);
	
			try {
				while(sad.next())
				{


int j3 = email.sendMailToApprover(request, Integer.toString(sad.getInt("HR_ID")),Integer.toString(reqId),"HR Query");

				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		int j = 1;
		int reqby = 0;
		LinkedList OT = new LinkedList();

		ArrayList query=new ArrayList();
		String getcomoff = "select * from HR_QUERIES where id='"+reqId+"'";
		ResultSet ff = ad.selectQuery(getcomoff);
		try {
			while (ff.next()) {
					

				
				
				pendAppForm.setSubject(ff.getString("subject"));
				pendAppForm.setDescription(ff.getString("description"));
			
				
				pendAppForm.setReq_by(ff.getInt("req_by"));
					
					String[] a=null;
					
					a=empDet(Integer.toString(ff.getInt("req_by")));
					pendAppForm.setEmpname(a[0]);
					pendAppForm.setEmpdep(a[2]);
					pendAppForm.setEmpdesg(a[3]);
					pendAppForm.setEmploc(a[1]);
					pendAppForm.setEmpext(a[4]);
		
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("query", query);
		
		
		LinkedList trnalist = new LinkedList();
		String ypu="select designation.dsgstxt,CONVERT(varchar(10),HR_QUERY_TRANSACTION.updated_time,103)+' '+ CONVERT(varchar(5),HR_QUERY_TRANSACTION.updated_time,108) as updated_time1,"
				+ "HR_QUERY_TRANSACTION.*,EMP_FULLNAME from HR_QUERY_TRANSACTION,emp_official_info,designation where HR_QUERY_TRANSACTION.id='"+reqId+"' and emp_official_info.pernr=Updated_by"
				+ " and  designation.DSGID=emp_official_info.DSGID order by updated_time desc";
		ResultSet n=ad.selectQuery(ypu);
		try {
			while(n.next())
			{
				PayslipRequestForm yo=new PayslipRequestForm();
				yo.setEmpname(n.getString("EMP_FULLNAME"));
				yo.setComments(n.getString("Comments"));
				yo.setApprvddate(n.getString("Updated_Time1"));
				yo.setEmpdesg(n.getString("dsgstxt"));
				trnalist.add(yo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(trnalist.size()>0)
		request.setAttribute("trnalist", trnalist);
		
		
		//update open status 
		String hr="update HR_QUERIES set status='ReOpen' where id='"+reqId+"' and status='Closed'";
		int g=ad.SqlExecuteUpdate(hr);
		
		
		displayquerieslist(mapping, form, request, response);
		
		return mapping.findForward("displayquerieslist");
		
		
	}
	
	public ActionForward HRqueryUSERRESPONSE(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		PayslipRequestForm pendAppForm = (PayslipRequestForm) form;
		String reqId = request.getParameter("reqId");
		
		pendAppForm.setReq_id(Integer.parseInt(reqId));
	
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int count=0;
		int requesterNo = Integer.parseInt(request.getParameter("reqId"));
		String coun = "select count(*) cou from HR_QUERIES where id='"+requesterNo+"' and req_by='"+user.getEmployeeNo()+"'";
		ResultSet fef = ad.selectQuery(coun);
		try {
			if(fef.next())
			{
				count=fef.getInt("cou");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(count==0)
		{
			pendAppForm.setMessage("You dont have authorization to view this request");
			displayquerieslist(mapping, pendAppForm, request, response);
			return mapping.findForward("displayquerieslist");
		}
		
		
		
		
		int j = 1;
		int reqby = 0;
		LinkedList OT = new LinkedList();
	
		ArrayList query=new ArrayList();
		String getcomoff = "select * from HR_QUERIES where id='"+requesterNo+"'";
		ResultSet ff = ad.selectQuery(getcomoff);
		try {
			while (ff.next()) {
					

				
				pendAppForm.setReq_id(requesterNo);
				pendAppForm.setSubject(ff.getString("subject"));
				pendAppForm.setDescription(ff.getString("description"));
			pendAppForm.setStatus(ff.getString("status"));
			pendAppForm.setCategory(ff.getString("category"));
				pendAppForm.setReq_by(ff.getInt("req_by"));
					
					String[] a=null;
					
					a=empDet(Integer.toString(ff.getInt("req_by")));
					pendAppForm.setEmpname(a[0]);
					pendAppForm.setEmpdep(a[2]);
					pendAppForm.setEmpdesg(a[3]);
					pendAppForm.setEmploc(a[1]);
					pendAppForm.setEmpext(a[4]);
		
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("query", query);
		
		LinkedList trnalist = new LinkedList();
		String ypu="select designation.dsgstxt,CONVERT(varchar(10),HR_QUERY_TRANSACTION.updated_time,103)+' '+ CONVERT(varchar(5),HR_QUERY_TRANSACTION.updated_time,108) as updated_time1,"
				+ "HR_QUERY_TRANSACTION.*,EMP_FULLNAME from HR_QUERY_TRANSACTION,emp_official_info,designation where HR_QUERY_TRANSACTION.id='"+reqId+"' and emp_official_info.pernr=Updated_by"
				+ " and  designation.DSGID=emp_official_info.DSGID order by updated_time desc";
		ResultSet n=ad.selectQuery(ypu);
		try {
			while(n.next())
			{
				PayslipRequestForm yo=new PayslipRequestForm();
				yo.setEmpname(n.getString("EMP_FULLNAME"));
				yo.setComments(n.getString("Comments"));
				yo.setApprvddate(n.getString("Updated_Time1"));
				yo.setEmpdesg(n.getString("dsgstxt"));
				trnalist.add(yo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(trnalist.size()>0)
		request.setAttribute("trnalist", trnalist);
		return mapping.findForward("HRqueryUserView");
		
	}
	
	
	public ActionForward HRqueryRequestToApprove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PayslipRequestForm pendAppForm = (PayslipRequestForm) form;
		String reqId = request.getParameter("reqId");
		pendAppForm.setReq_id(Integer.parseInt(reqId));
	
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("message",
					"Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String scount = Integer.toString(pendAppForm.getStartRecord());

		int totalRecords = pendAppForm.getTotalRecords();// 21
		int startRecord = pendAppForm.getStartRecord();// 11
		int endRecord = pendAppForm.getEndRecord();
		String filterBy = pendAppForm.getSelectedFilter();
		EssDao ad = new EssDao();
		pendAppForm.setTotalRecords(totalRecords);
		pendAppForm.setStartRecord(startRecord);
		pendAppForm.setEndRecord(endRecord);
		pendAppForm.setSelectedFilter(filterBy);
		
		
	
		int j = 1;
		int reqby = 0;
		LinkedList OT = new LinkedList();
		int requesterNo = Integer.parseInt(request.getParameter("reqId"));
		
	
		
		ArrayList query=new ArrayList();
		String getcomoff = "select * from HR_QUERIES where id='"+requesterNo+"'";
		ResultSet ff = ad.selectQuery(getcomoff);
		try {
			if (ff.next()) {
					

				pendAppForm.setReq_id(requesterNo);
				
				pendAppForm.setSubject(ff.getString("subject"));
				pendAppForm.setDescription(ff.getString("description"));
				pendAppForm.setCategory(ff.getString("category"));
				
				pendAppForm.setReq_by(ff.getInt("req_by"));
					
					String[] a=null;
					
					a=empDet(Integer.toString(ff.getInt("req_by")));
					pendAppForm.setEmpname(a[0]);
					pendAppForm.setEmpdep(a[2]);
					pendAppForm.setEmpdesg(a[3]);
					pendAppForm.setEmploc(a[1]);
					pendAppForm.setEmpext(a[4]);
		
			}
			else
			{
				pendAppForm.setMessage("Invalid Req No");
				return mapping.findForward("pendingHRquery");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("query", query);
		
		
		LinkedList trnalist = new LinkedList();
		String ypu="select designation.dsgstxt,CONVERT(varchar(10),HR_QUERY_TRANSACTION.updated_time,103)+' '+ CONVERT(varchar(5),HR_QUERY_TRANSACTION.updated_time,108) as updated_time1,"
				+ "HR_QUERY_TRANSACTION.*,EMP_FULLNAME from HR_QUERY_TRANSACTION,emp_official_info,designation where HR_QUERY_TRANSACTION.id='"+reqId+"' and emp_official_info.pernr=Updated_by"
				+ " and  designation.DSGID=emp_official_info.DSGID order by updated_time desc";
		ResultSet n=ad.selectQuery(ypu);
		try {
			while(n.next())
			{
				PayslipRequestForm yo=new PayslipRequestForm();
				yo.setEmpname(n.getString("EMP_FULLNAME"));
				yo.setComments(n.getString("Comments"));
				yo.setApprvddate(n.getString("Updated_Time1"));
				yo.setEmpdesg(n.getString("dsgstxt"));
				trnalist.add(yo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(trnalist.size()>0)
		request.setAttribute("trnalist", trnalist);

	//update open status 
String hr="update HR_QUERIES set status='Open' where id='"+reqId+"' and status='New'";
int g=ad.SqlExecuteUpdate(hr);
		

		return mapping.findForward("HrqueryView");
	}
	
	private String[] empDet( String empno) {
		String[] a = new String[10];
		
		
		if(empno==null)
		{
			return a;
		}
		if(empno.equalsIgnoreCase(""))
		{
			return a;
		}
		
		HRDao ad=new HRDao();
			String emp=" select emp.eMP_FULLNAME,emp.lOCID,dep.DPTSTXT,desg.DSGSTXT,emp.tel_extens from Emp_official_info emp "
					+ " , Department dep ,Designation desg  where emp.dPTID = dep.DPTID and emp.pERNR='"+empno+"' "
							+ " and desg.DSGID= emp.dSGID  ";
			ResultSet ae=ad.selectQuery(emp);

		
		
			try {
				while(ae.next())
				{
				
				

				    a[0]=ae.getString("eMP_FULLNAME");
				    a[1]=ae.getString("lOCID");
				    a[2]=ae.getString("DPTSTXT");
				    a[3]=ae.getString("DSGSTXT");
				    a[4]=ae.getString("tel_extens");
				   


				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return a;
			}
	
	
	public ActionForward submitquery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		PayslipRequestForm p1=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
		EMailer email = new EMailer();	

		String approver="";
		String lApprover="";
		String pApprover="";
		String approvermail="";
	
		String hrid=p1.getHrID();
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String dateNow = ft.format(dNow);
		
		int  newReqestNo=0;
		String getRequstNo="select max(id) from HR_QUERIES";
		ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
		try {
			while(rsRequestNo.next())
			{
				newReqestNo=rsRequestNo.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		newReqestNo=newReqestNo+1;
		
		
		String is="insert into HR_QUERIES(id,Req_by,Req_date,Status,Subject,Description,Pending_Approver,Last_Approver,role,category) values ('"+newReqestNo+"','"+user.getEmployeeNo()+"',getdate(),'New','"+p1.getSubject()+"','"+p1.getDescription()+"','','No','"+hrid+"','"+p1.getCategory()+"')";
		int g=ad.SqlExecuteUpdate(is);
		if(g>0)
		{
			p1.setMessage("Request No: "+newReqestNo+" submitted successfully"); 
			
			String hrap="select distinct HR_ID from HR_QUERIES_APPROVERS where role='"+hrid+"' and location='"+user.getPlantId()+"' and category='"+p1.getCategory()+"'";
			ResultSet sad=ad.selectQuery(hrap);
			try {
				if(sad.next())
				{
					sad.beforeFirst();
				}
				
				else
					
				{
					p1.setMessage("No HRs Assigned For Selected Category ..Contact Admin"); 
					newquery(mapping, form, request, response)		;			
					return mapping.findForward("newquery");
				}
				while(sad.next())
				{
				
				String saveAllReq = "insert into All_Request(Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,"
							+ "Approved_Persons,Requester_Id) values ('"
							+ newReqestNo
							+ "','User Request','"
							+ user.getEmployeeNo()
							+ "','"
							+ dateNow
							+ "','Pending','',"
							+ "'"
							+ sad.getInt("HR_ID")
							+ "','','"
							+ user.getEmployeeNo()
							+ "')";
					int iu=ad.SqlExecuteUpdate(saveAllReq);
					
					int j = email.sendMailToApprover(request, Integer.toString(sad.getInt("HR_ID")),Integer.toString(newReqestNo),"HR Query");
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else
		{
			p1.setMessage("Error ..Please Check"); 
		}
		p1.setReq_by(Integer.parseInt(user.getEmployeeNo()));
		
		String[] a=null;
		
		a=empDet(Integer.toString(p1.getReq_by()));
		p1.setEmpname(a[0]);
		p1.setEmpdep(a[2]);
		p1.setEmpdesg(a[3]);
		p1.setEmploc(a[1]);
		p1.setEmpext(a[4]);
		
		
		LinkedList categorylist=new LinkedList();
		
		
		String reason="select * from HR_QUERY_CATEGORY";
		ResultSet a11=ad.selectQuery(reason);
		try {
			while(a11.next())
			{
				categorylist.add(a11.getString("type"));
			
			}
			p1.setCategoryList(categorylist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		displayquerieslist(mapping, form, request, response);
	
	return mapping.findForward("displayquerieslist");
	}
	
	public ActionForward newquery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		PayslipRequestForm p1=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
	       p1.setReq_by(Integer.parseInt(user.getEmployeeNo()));
			
			String[] a=null;
			
			a=empDet(Integer.toString(p1.getReq_by()));
			p1.setEmpname(a[0]);
			p1.setEmpdep(a[2]);
			p1.setEmpdesg(a[3]);
			p1.setEmploc(a[1]);
			p1.setEmpext(a[4]);
		
			LinkedList categorylist=new LinkedList();
			
			
			String reason="select * from HR_QUERY_CATEGORY";
			ResultSet a11=ad.selectQuery(reason);
			try {
				while(a11.next())
				{
					categorylist.add(a11.getString("type"));
				
				}
				p1.setCategoryList(categorylist);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return mapping.findForward("newquery");
	}
	
	public ActionForward displayquerieslist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		PayslipRequestForm p1=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
		ArrayList query=new ArrayList();
		
   
		
		if(p1.getSelectedFilter()==null)
		{
			p1.setSelectedFilter("New");
		}
			if(p1.getSelectedFilter().equalsIgnoreCase(""))
			{
				p1.setSelectedFilter("New");
			}
			String t="";
			if(!p1.getSelectedFilter().equalsIgnoreCase("All"))
		 t="select top 100 CONVERT(varchar(10),hr_queries.Req_date,103)+' '+ CONVERT(varchar(5),hr_queries.Req_date,108) as Req_date1,CONVERT(varchar(10),hr_queries.Approved_date,103)+' '+ CONVERT(varchar(5),hr_queries.Approved_date,108) as Approved_date1,* from hr_queries where req_by='"+user.getEmployeeNo()+"' and status='"+p1.getSelectedFilter()+"' order by  id desc";
	    else
	    	t="select top 100 CONVERT(varchar(10),hr_queries.Req_date,103)+' '+ CONVERT(varchar(5),hr_queries.Req_date,108) as Req_date1,CONVERT(varchar(10),hr_queries.Approved_date,103)+' '+ CONVERT(varchar(5),hr_queries.Approved_date,108) as Approved_date1,* from hr_queries where req_by='"+user.getEmployeeNo()+"' order by  id desc";
				
		ResultSet ff=ad.selectQuery(t);
		try {
			while(ff.next())
			{
				PayslipRequestForm pay =new PayslipRequestForm();	

				pay.setReq_id(ff.getInt("id"));
				pay.setReq_date(ff.getString("Req_date1"));
	
                pay.setStatus(ff.getString("status"));
				pay.setSubject(ff.getString("subject"));
				if(ff.getString("description").length()>50)
				pay.setDescription(ff.getString("description").substring(0, 50));
				else
					pay.setDescription(ff.getString("description"));	
				if(ff.getString("Pending_Approver")!=null)
				pay.setPendappr(ff.getString("Pending_Approver"));
				if(ff.getString("Last_Approver")!=null)
				pay.setLastappr(ff.getString("Last_Approver"));
				
				pay.setApprvddate(ff.getString("Approved_date1"));
				pay.setCategory(ff.getString("category"));
				
				query.add(pay);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("query", query);
		return mapping.findForward("displayquerieslist");
	}
	
	public ActionForward displaylist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		PayslipRequestForm p1=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
		String id=request.getParameter("sId");
				String type="";
		
		
		if(id.equalsIgnoreCase("Request Payslip"))
		{
			type="Payslip";
			
			ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select year(getdate()) as yearlist union select year(getdate())-1   order by 1 desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("yearlist"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					p1.setYearList(yearList);
		}
		
		if(id.equalsIgnoreCase("Request Form16"))
		{
			type="Form16";
			
			ArrayList yearList=new ArrayList();
			//ResultSet rs17 = ad.selectQuery("select year(getdate())-1 as yearlist    order by 1 desc"); 
			ResultSet rs17 = ad.selectQuery(" select year(getdate()) as yearlist union select year(getdate())-1   order by 1 desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("yearlist"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					p1.setYearList(yearList);
		}
		
		p1.setReq_type(type);
		
		
		
		ArrayList bn=new ArrayList();
		String g="select top 50 pay.*,DateName( month , DateAdd( month , pay.month , 0 ) - 1 ) as monthname,CONVERT(varchar(10),req_date,103)+' '+ CONVERT(varchar(5),req_date,108) as req_date1,emp_official_info.emp_fullname from "
				+ "PAYSLIP_REQUEST pay,emp_official_info  where pay.Req_By='"+user.getEmployeeNo()+"' and pay.Req_Type='"+p1.getReq_type()+"' and pernr=pay.Req_By order by Req_id desc";
		ResultSet ff=ad.selectQuery(g);
		try {
			while(ff.next())
			{
				PayslipRequestForm pay =new PayslipRequestForm();
				pay.setMonthname(ff.getString("monthname"));
				pay.setYear(ff.getInt("year"));
				pay.setReq_id(ff.getInt("Req_id"));
				pay.setEmpname(ff.getString("emp_fullname"));
				pay.setReq_date(ff.getString("req_date1"));
				pay.setStatus(ff.getString("status"));
				pay.setReq_type(ff.getString("req_type"));
				bn.add(pay);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		request.setAttribute("paylist", bn);
		return mapping.findForward("displaylist");
	}
	
	private String rpHash(String value) {
		int hash = 5381;
		value = value.toUpperCase();
		for(int i = 0; i < value.length(); i++) {
			hash = ((hash << 5) + hash) + value.charAt(i);
		}
		return String.valueOf(hash);
	}
	
	public ActionForward getHRList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		PayslipRequestForm approvalsForm=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		String role=request.getParameter("role");
		String category=request.getParameter("category");
		ArrayList hrl=new ArrayList();
		
		String hrap="select distinct HR_ID,emp_official_info.EMP_FULLNAME,DSGSTXT,role from HR_QUERIES_APPROVERS,emp_official_info,designation where role='"+role+"' and location='"+user.getPlantId()+"' and category='"+category+"' "
				+ "and HR_QUERIES_APPROVERS.hr_id=emp_official_info.pernr and designation.dsgid=emp_official_info.DSGID";
		ResultSet sad=ad.selectQuery(hrap);

			try {
				while(sad.next())
				{
					PayslipRequestForm p=new PayslipRequestForm();
					p.setEmpname(sad.getString("EMP_FULLNAME"));
					p.setHrID(sad.getString("role"));
					p.setEmpdesg(sad.getString("DSGSTXT"));
					
					hrl.add(p);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			request.setAttribute("hrlist", hrl);
		return mapping.findForward("hrlistajax");
	}
	
	public ActionForward raiserequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		PayslipRequestForm p1=(PayslipRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			
			return mapping.findForward("displayiFrameSession");
		}
		
		///capthca
		if (rpHash(request.getParameter("defaultReal")).equals(
				request.getParameter("defaultRealHash"))) {

			
		int  newReqestNo=0;
		String getRequstNo="select max(Req_id) from PAYSLIP_REQUEST";
		ResultSet rsRequestNo=ad.selectQuery(getRequstNo);
		try {
			while(rsRequestNo.next())
			{
				newReqestNo=rsRequestNo.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		newReqestNo=newReqestNo+1;
		
		Calendar calendar = Calendar.getInstance();	
		calendar.add(Calendar.HOUR, 1);
		Date d = calendar.getTime();
		SimpleDateFormat ft = new SimpleDateFormat("h a");
		String submitDate = ft.format(d);
		
		
		int alreadyder=0;
		String  cou="select count(*) cu from PAYSLIP_REQUEST where Req_Type='"+p1.getReq_type()+"' and Req_By='"+user.getEmployeeNo()+"'"
				+ " and month='"+p1.getMonth()+"' and year='"+p1.getYear()+"' and Status ='Pending'";
		ResultSet bb=ad.selectQuery(cou);
		try {
			while(bb.next())
			{
				alreadyder=bb.getInt("cu");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(alreadyder==0)
	
		{
		//raise req
		String req="insert into PAYSLIP_REQUEST(Req_id,Req_Type,Req_By,Month,Year,Req_date,Status) values('"+newReqestNo+"','"+p1.getReq_type()+"','"+user.getEmployeeNo()+"'"
				+ ",'"+p1.getMonth()+"','"+p1.getYear()+"',getdate(),'Pending')";
		int y=ad.SqlExecuteUpdate(req);
		if(y>0)
			p1.setMessage("Request will be processed and mail will be sent by "+submitDate+"");
		else
			p1.setMessage("Error..Please check");
		}
		
		else
		{
			p1.setMessage("Request already Submitted for this selection");
			
		}
		
	}
		else {
			p1.setMessage("Captcha Incorrect ..Re-enter");
		}
		
		
		if(p1.getReq_type().equalsIgnoreCase("Payslip"))
		{
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select year(getdate()) as yearlist union select year(getdate())-1   order by 1 desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("yearlist"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				p1.setYearList(yearList);
		}
		else
		{
			ArrayList yearList=new ArrayList();
			ResultSet rs17 = ad.selectQuery("select year(getdate())-1 as yearlist    order by 1 desc"); 
					try {
						while(rs17.next()) {
							yearList.add(rs17.getString("yearlist"));
						}
						rs17.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					p1.setYearList(yearList);
		}
		
		ArrayList bn=new ArrayList();
		String g="select top 50 pay.*,DateName( month , DateAdd( month , pay.month , 0 ) - 1 ) as monthname,CONVERT(varchar(10),req_date,103)+' '+ CONVERT(varchar(5),req_date,108) as req_date1,emp_official_info.emp_fullname from PAYSLIP_REQUEST pay,emp_official_info  where pay.Req_By='"+user.getEmployeeNo()+"'"
				+ " and pay.Req_Type='"+p1.getReq_type()+"' and pernr=pay.Req_By order by Req_id desc";
		ResultSet ff=ad.selectQuery(g);
		try {
			while(ff.next())
			{
				PayslipRequestForm pay =new PayslipRequestForm();
				pay.setMonthname(ff.getString("monthname"));
				pay.setYear(ff.getInt("year"));
				pay.setReq_id(ff.getInt("Req_id"));
				pay.setEmpname(ff.getString("emp_fullname"));
				pay.setReq_date(ff.getString("req_date1"));
				pay.setStatus(ff.getString("status"));
				pay.setReq_type(ff.getString("req_type"));
				bn.add(pay);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		request.setAttribute("paylist", bn);
		return mapping.findForward("displaylist");
		
		
		
	}
	
	
}
