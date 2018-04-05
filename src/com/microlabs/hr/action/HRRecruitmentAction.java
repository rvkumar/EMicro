package com.microlabs.hr.action;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.hr.form.HRRecruitmentForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.UserInfo;
public class HRRecruitmentAction extends DispatchAction{
	

	
	public ActionForward updateForwardRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		System.out.println("updateForwardRequest()---");
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		System.out.println("employee number="+hrForm.getEmployeeNumber());
		System.out.println("employee name="+hrForm.getApprover1());
		System.out.println("Designation number="+hrForm.getDesignation());
		
		
		
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userId=user.getId();
		System.out.println("userId="+userId);
		
		int approver1=0;
		int approver2=0;
		int approver3=0;
		try{
			ResultSet rs=null;
		String getApproverID="select * from leaveapprovallevels where approval_type='Recruitment Request'";
		
		rs=ad.selectQuery(getApproverID);
		while(rs.next()){
			
			approver1=rs.getInt("approver1");
			approver2=rs.getInt("approver2");
			approver3=rs.getInt("approver3");
		}
		
		

		if(userId==approver1){		
			
			String updateForwardRequest="update Recruitmen_Request set Forward_Emp1='"+hrForm.getEmployeeNumber()+"',Forward_Emp1_status='0',Approver1_Type='Forwarded',Reqest_Forward_Emp_Id='"+userId+"' where Req_Id='"+hrForm.getRecuritmentID()+"'";
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
			
			String updateForwardRequest="update Recruitmen_Request set Forward_Emp2='"+hrForm.getEmployeeNumber()+"',Forward_Emp2_status='0',Approver2_Type='Forwarded',Reqest_Forward_Emp_Id='"+userId+"' where Req_Id='"+hrForm.getRecuritmentID()+"'";
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
			
			String updateForwardRequest="update Recruitmen_Request set Forward_Emp3='"+hrForm.getEmployeeNumber()+"',Forward_Emp3_status='0',Approver3_Type='Forwarded',Reqest_Forward_Emp_Id='"+userId+"' where Req_Id='"+hrForm.getRecuritmentID()+"'";
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
		else if(userId!=approver1 ||userId!=approver2 ||userId!=approver3){
				int ForwardEmpID=0;
				String getForwardEmpID="select * from Recruitmen_Request where Req_Id='"+hrForm.getRecuritmentID()+"' ";
				ResultSet rs1=ad.selectQuery(getForwardEmpID);
				while(rs1.next()){
					ForwardEmpID=rs1.getInt("Reqest_Forward_Emp_Id");
				}
				
				if(ForwardEmpID==approver1){		
					
					String updateForwardRequest="update Recruitmen_Request set Forward_Emp1='"+hrForm.getEmployeeNumber()+"',Forward_Emp1_status='0',Approver1_Type='Forwarded' where Req_Id='"+hrForm.getRecuritmentID()+"'";
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
				if(ForwardEmpID==approver2){		
					
					String updateForwardRequest="update Recruitmen_Request set Forward_Emp2='"+hrForm.getEmployeeNumber()+"',Forward_Emp2_status='0',Approver2_Type='Forwarded' where Req_Id='"+hrForm.getRecuritmentID()+"'";
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
				if(ForwardEmpID==approver3){		
					
					String updateForwardRequest="update Recruitmen_Request set Forward_Emp3='"+hrForm.getEmployeeNumber()+"',Forward_Emp3_status='0',Approver3_Type='Forwarded' where Req_Id='"+hrForm.getRecuritmentID()+"'";
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
				
				
			
		}
		
		
		
		}catch (Exception e) {
		e.printStackTrace();
		}
		
		showApprovedAndPendingRequest(mapping, form, request, response);
		
		return mapping.findForward("pendingRequest");
	}

	
	public ActionForward displayForwardReqEmpDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayForwardReqEmpDetails()---");
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		String param=request.getParameter("param");
		hrForm.setApproverNumber(param);
		return mapping.findForward("forwardEmployeeListForm");
	}

	
	public ActionForward submitApproveRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("showPendingRequest()---");
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userId=user.getId();
		System.out.println("userId="+userId);
		System.out.println("status="+hrForm.getApprovalStatus());
		int approver1=0;
		int approver2=0;
		int approver3=0;
		try{
			ResultSet rs=null;
		String getApproverID="select * from leaveapprovallevels where approval_type='Recruitment Request'";
		
		rs=ad.selectQuery(getApproverID);
		while(rs.next()){
			approver1=rs.getInt("approver1");
			approver2=rs.getInt("approver2");
			approver3=rs.getInt("approver3");
		}
		
		String getForwardEmpID="select * from Recruitmen_Request where  Req_Id='"+hrForm.getRecuritmentID()+"'";
		ResultSet rs1=ad.selectQuery(getForwardEmpID);
		int forwardEmpId=0;
		while(rs1.next()){
			forwardEmpId=rs1.getInt("Reqest_Forward_Emp_Id");
		
		}
		if(userId==approver1 || approver1==forwardEmpId){
			System.out.println("approver1");
			
			String status=hrForm.getApprovalStatus();
			
			if(status.equalsIgnoreCase("Approved")){
			
			String updateRequest="update Recruitmen_Request set Approver1_status='1',Approver1_Reason='"+hrForm.getResons()+"',Approver1_Type='Approved' where Req_Id='"+hrForm.getRecuritmentID()+"'";
			ad.SqlExecuteUpdate(updateRequest);		
			}else{
			String updateRequest="update Recruitmen_Request set Approvel_Status='Cancel',Approver1_status='0',Approver1_Reason='"+hrForm.getResons()+"',Approver1_Type='Cancel' where Req_Id='"+hrForm.getRecuritmentID()+"'";
			ad.SqlExecuteUpdate(updateRequest);				
				
			}
		}
		if(userId==approver2 || approver2==forwardEmpId){
			System.out.println("approver2");
			String status=hrForm.getApprovalStatus();
			if(status.equalsIgnoreCase("Approved")){
			
			String updateRequest="update Recruitmen_Request set Approver2_status='1',Approver2_Reason='"+hrForm.getResons()+"',Approver2_Type='Approved' where Req_Id='"+hrForm.getRecuritmentID()+"'";
			ad.SqlExecuteUpdate(updateRequest);		
			}else{
			String updateRequest="update Recruitmen_Request set Approvel_Status='Cancel',Approver2_status='0',Approver2_Reason='"+hrForm.getResons()+"',Approver2_Type='Cancel' where Req_Id='"+hrForm.getRecuritmentID()+"'";
			ad.SqlExecuteUpdate(updateRequest);				
				
			}
			
		}
		if(userId==approver3 || approver3==forwardEmpId){
			System.out.println("approve3");
			
			String status=hrForm.getApprovalStatus();
			if(status.equalsIgnoreCase("Approved")){
			
			String updateRequest="update Recruitmen_Request set Approver3_status='1',Approver3_Reason='"+hrForm.getResons()+"',Approvel_Status='Approved',Approver3_Type='Approved' where Req_Id='"+hrForm.getRecuritmentID()+"'";
			ad.SqlExecuteUpdate(updateRequest);		
			}else{
			String updateRequest="update Recruitmen_Request set Approvel_Status='Cancel',Approver3_status='0',Approver3_Reason='"+hrForm.getResons()+"',Approver3_Type='Cancel' where Req_Id='"+hrForm.getRecuritmentID()+"'";
			ad.SqlExecuteUpdate(updateRequest);				
				
			}
		}
		
		}catch (Exception e) {
			// TODO: handle exception
		}
		showApprovedAndPendingRequest(mapping, form, request, response);
				
		return mapping.findForward("pendingRequest");
	}
	
	public ActionForward showApprovedAndPendingRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("showApprovedAndPendingRequest()---");
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int loginId=user.getId();
		System.out.println("userId="+loginId);
		String userId=Integer.toString(loginId);
		try{
			String approver1="";
			String approver2="";
			String approver3="";
		
String getApproverID="select * from leaveapprovallevels where approval_type='Recruitment Request'";
		
		ResultSet rs1=ad.selectQuery(getApproverID);
		while(rs1.next()){
			
			approver1=rs1.getString("approver1");
			approver2=rs1.getString("approver2");
			approver3=rs1.getString("approver3");
		}
		ArrayList list=new ArrayList();

		String getPendingRequest="select * from Recruitmen_Request  where  " +
		"Approver1='"+userId+"'  or Approver2='"+userId+"'  or " +
		"Approver3='"+userId+"' or Forward_Emp1='"+userId+"' or Forward_Emp2='"+userId+"' or Forward_Emp3='"+userId+"' ";
		System.out.println("getPendingRequest="+getPendingRequest);
		ResultSet rs=ad.selectQuery(getPendingRequest);
	
		while(rs.next()){
			String forwardEmpId="";
		
			hrForm=new HRRecruitmentForm();
			hrForm.setRecuritmentID(rs.getString("Req_Id"));
			hrForm.setJobTitle(rs.getString("Job_Title"));
			hrForm.setDepartment(rs.getString("Department"));
			forwardEmpId=rs.getString("Reqest_Forward_Emp_Id");
			
			
			
			
			if(approver1.equalsIgnoreCase(userId) || forwardEmpId.equalsIgnoreCase(approver1))
			{
			hrForm.setApprovalStatus(rs.getString("Approver1_Type"));
			System.out.println("status****="+rs.getString("Approver1_Type"));
			}
			else if(approver2.equalsIgnoreCase(userId)|| forwardEmpId.equalsIgnoreCase(approver2))
			{
			hrForm.setApprovalStatus(rs.getString("Approver2_Type"));
			System.out.println("status****="+rs.getString("Approver1_Type"));
			}
			else if(approver3.equalsIgnoreCase(userId) || forwardEmpId.equalsIgnoreCase(approver2))
			{
			hrForm.setApprovalStatus(rs.getString("Approver3_Type"));
			System.out.println("status****="+rs.getString("Approver1_Type"));
			}
			hrForm.setNoOfPersonsApproved(rs.getString("Total_No_of_Persons_Approved"));
			hrForm.setApprovedpersons(rs.getString("Approved_Persons"));
			
			list.add(hrForm);
		}
		
	
		session.setAttribute("listOfReq", list);
	
			
			
	
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return mapping.findForward("pendingRequest");
	}
	
	
	public ActionForward showPendingRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("showPendingRequest()---");
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userId=user.getId();
		System.out.println("userId="+userId);
		try{
		int approver1=0;
		int approver2=0;
		int approver3=0;
		
String getApproverID="select * from leaveapprovallevels where approval_type='Recruitment Request'";
		
		ResultSet rs1=ad.selectQuery(getApproverID);
		while(rs1.next()){
			
			approver1=rs1.getInt("approver1");
			approver2=rs1.getInt("approver2");
			approver3=rs1.getInt("approver3");
			
		}
		
		ArrayList list=new ArrayList();

		String getPendingRequest="select * from Recruitmen_Request  where Approvel_Status='Pending' and " +
		"Approver1='"+userId+"' and Approver1_status=0 or (Approver2='"+userId+"' and Approver1_status=1 and Approver2_status=0) or " +
		"(Approver3='"+userId+"' and Approver1_status=1 and Approver2_status=1 and Approver3_status=0) or Forward_Emp1='"+userId+"'or Forward_Emp2='"+userId+"'or Forward_Emp2='"+userId+"' ";
		System.out.println("getPendingRequest="+getPendingRequest);
		ResultSet rs=ad.selectQuery(getPendingRequest);
		while(rs.next()){
			
			hrForm=new HRRecruitmentForm();
			hrForm.setRecuritmentID(rs.getString("Req_Id"));
			hrForm.setJobTitle(rs.getString("Job_Title"));
			hrForm.setDepartment(rs.getString("Department"));
			hrForm.setApprovalStatus(rs.getString("Approvel_Status"));
			hrForm.setNoOfPersonsApproved(rs.getString("Total_No_of_Persons_Approved"));
			hrForm.setApprovedpersons(rs.getString("Approved_Persons"));
			
			list.add(hrForm);
		}
		
	
		session.setAttribute("listOfReq", list);
	
			
			
	
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return mapping.findForward("pendingRequest");
	}
	
	
	
	public ActionForward approvalForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		HRDao ad=new HRDao();
	
		String requestID=request.getParameter("recuritmentID");
		
		try{
			
			String getRecruitmentDetails="select r.Req_Id,r.Job_Title,r.Department," +
					"r.PrimaryLocation,r.OtherLocation,r.EmpStatus,r.TotalEmp,r.ShiftType,r.JobDescription," +
					"r.Qualification,r.Experience,r.IndustryType,r.SaleryOffered,m.Country,m.State,m.City," +
					"m.Plant,r.Requester_name,r.Request_Date from Man_Power_Matrix as m,Recruitmen_Request as r where" +
					" r.Req_Id=m.Req_Id and r.Req_Id='"+requestID+"'";
			ResultSet rs=ad.selectQuery(getRecruitmentDetails);
			while(rs.next()){
				hrForm.setRecuritmentID(rs.getString("Req_Id"));
				hrForm.setJobTitle(rs.getString("Job_Title"));
				hrForm.setDepartment(rs.getString("Department"));
				hrForm.setCountry(rs.getString("Country"));
				hrForm.setState(rs.getString("State"));
				hrForm.setCity(rs.getString("City"));
				hrForm.setPlant(rs.getString("Plant"));
				hrForm.setPrimaryLocation(rs.getString("PrimaryLocation"));
				hrForm.setOtherLocation(rs.getString("OtherLocation"));
				hrForm.setEmpStatus(rs.getString("EmpStatus"));
				hrForm.setTotalEmp(rs.getString("TotalEmp"));
				hrForm.setShiftType(rs.getString("ShiftType"));
				hrForm.setJobDescription(rs.getString("JobDescription"));
				hrForm.setQualifications(rs.getString("Qualification"));
				hrForm.setExperience(rs.getString("Experience"));
				hrForm.setIndustryType(rs.getString("IndustryType"));
				hrForm.setSaleryOffered(rs.getString("SaleryOffered"));
				
				hrForm.setRequesterName(rs.getString("Requester_name"));
				hrForm.setRequestDate(rs.getString("Request_Date"));
			}
				
		
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return mapping.findForward("approvalForm");
	}
	


	public ActionForward displayAllRecruitmentRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayAllRecruitmentRequest()---");
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();

		
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
		String getAllRequest="select * from Recruitmen_Request where Requester_id='"+user.getId()+"'";
		
		ArrayList list=new ArrayList();
		ResultSet rs=ad.selectQuery(getAllRequest);
		while(rs.next()){
			hrForm=new HRRecruitmentForm();
			hrForm.setRecuritmentID(rs.getString("Req_Id"));
			hrForm.setJobTitle(rs.getString("Job_Title"));
			hrForm.setDepartment(rs.getString("Department"));
			hrForm.setApprovalStatus(rs.getString("Approvel_Status"));
			hrForm.setNoOfPersonsApproved(rs.getString("Total_No_of_Persons_Approved"));
			hrForm.setApprovedpersons(rs.getString("Approved_Persons"));
			
			list.add(hrForm);
		}
		
		
		session.setAttribute("listOfReq", list);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return mapping.findForward("displayAllRequest");
	}


	/*Get the approvers list, Insert into Table, Send to First Approver 
	Display the request*/
	
	public ActionForward saveRecruitmentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		String lcode = user.getPlantId();
		int user_Id = user.getId();
		try{
			
			String fullname=user.getFullName();//+" "+user.getMiddleName()+" "+user.getLastName();
			System.out.println("user name="+fullname);
			System.out.println();
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//get current date time with Date()
			Date date = new Date();
			System.out.println(dateFormat.format(date));

			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			System.out.println("Current Date="+dateFormat.format(cal.getTime()));
			String currentDate=dateFormat.format(cal.getTime());
			
			String approver="";
			String lApprover="";
			String pApprover="";
			String approvermail="";
			
			String getApproverID="select * from Approvers_Details where Type='Recruitment Request' and Priority=1";
			
			ResultSet rs=ad.selectQuery(getApproverID);
			while(rs.next()){
				
					approver = rs.getString("Employee_Name");
					lApprover = "";
					pApprover = approver;
					approvermail = rs.getString("emailID");
					
			}
			
			String saveRecruitmentDetails="insert into Recruitment_Request(Req_Id,Job_Title,Department," +
		"PrimaryLocation,OtherLocation,EmpStatus,TotalEmp,ShiftType,JobDescription,Qualification," +
		"Experience,IndustryType,SalaryOffered,Approvel_Status,Total_No_of_Persons_Approved,Approved_Persons,Requester_name,Request_Date,Location_Code, User_Id,Last_approver,Pending_approver)" +
		" values('"+hrForm.getRecuritmentID()+"','"+hrForm.getJobTitle()+"','"+hrForm.getDepartment()+"'," +
		"'"+hrForm.getPrimaryLocation()+"','"+hrForm.getOtherLocation()+"','"+hrForm.getEmpStatus()+"','"+hrForm.getTotalEmp()+"'," +
		"'"+hrForm.getShiftType()+"','"+hrForm.getJobDescription()+"','"+hrForm.getQualifications()+"','"+hrForm.getExperience()+"'," +
		"'"+hrForm.getIndustryType()+"','"+hrForm.getSaleryOffered()+"','Pending','0','No','"+fullname+"','"+currentDate+"','"+lcode+"','"+user_Id+"','"+lApprover+"','"+pApprover+"')";
			
			System.out.println("saveRecruitmentDetails="+saveRecruitmentDetails);
			int i=0;
			i=ad.SqlExecuteUpdate(saveRecruitmentDetails);
			System.out.println("i="+i);
			if(i==0){
				hrForm.setMessage("Your Request Not Sent.Please Check.... ");
				//session.setAttribute("status","Your Request Not Sent.Please Check.... ");
			}
			else if(i>0){
				/*String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id) values (";
				saveRecReq = saveRecReq + "'"+hrForm.getRecuritmentID()+"','Recruitment Request','"+fullname+"','"+currentDate+"','Pending','','"+pApprover+"','No','"+user_Id+"')";
				i=ad.SqlExecuteUpdate(saveRecReq);*/
				String Req_Id = ""+hrForm.getRecuritmentID();
				EMailer email = new EMailer();
				i = email.sendMailToApprover(request, approvermail,Req_Id,"Recruitment Request");
				if(i > 0){
					hrForm.setMessage("Your Request Has Been Sent.");
				}
				/*send mail to approver
				MailInboxForm mailForm = new MailInboxForm();
				MailInboxAction mailAction = new MailInboxAction();
				mailForm.setToAddress(approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				mailForm.setSubject("Recruitment Request");
				String desc = "Requested By : "+fullname+"</br>";
				desc = desc + "Requested Date : "+currentDate+"</br>";
				desc = desc + "Dpartment : "+hrForm.getDepartment()+"</br>";
				desc = desc + "Required Employee : "+hrForm.getTotalEmp()+"</br>";
				desc = desc + "Required Qualification : "+hrForm.getQualifications()+"</br>";
				desc = desc + "Salary Offered : "+hrForm.getSaleryOffered()+"</br>";
				mailForm.setDescription(desc);
				mailAction.mailSendToRecipient(request, mailForm);*/
				
			}
			
			
		}catch (Exception e) {
			System.out.println("exception @ upload request");
			hrForm.setMessage("Error while uploading the request!");
			e.printStackTrace();
		}
		
		request.setAttribute("formDetails", "formDetails");
		//newHrRecrutmentRequestForm(mapping, form, request, response);
		return mapping.findForward("displayRecruitmentRequestForm");
		
	}
	
	public ActionForward reFresh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("reFresh()-----");
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		HRDao ad=new HRDao();
		System.out.println("selcted id="+hrForm.getRecuritmentID());
	
		try{
			
			String getManpowerMatrixDetails="select * from Man_Power_Matrix where Req_Id='"+hrForm.getRecuritmentID()+"'";
			ResultSet rs=ad.selectQuery(getManpowerMatrixDetails);
			while(rs.next())
			{
			
				hrForm.setJobTitle(rs.getString("Job_Title"));
		         hrForm.setDepartment(rs.getString("Department"));
		         hrForm.setCountry(rs.getString("Country"));
		         hrForm.setState(rs.getString("State"));
		         hrForm.setCity(rs.getString("City"));
		         hrForm.setPlant(rs.getString("Plant"));
		         hrForm.setTotalEmp(rs.getString("Req_Man_Power"));
		         int salery=rs.getInt("Emp_Monthly_Salery");
		         salery=salery*12;
		         hrForm.setSaleryOffered(Integer.toString(salery));
		         
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		request.setAttribute("formDetails", "formDetails");
		newHrRecrutmentRequestForm(mapping, form, request, response);
		return mapping.findForward("displayRecrutmentForm");
		
	}

	public ActionForward displayRecruitmentRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		System.out.println("dsplay Recruitment Request");
		HRRecruitmentForm hrRRForm=(HRRecruitmentForm) form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		String module = request.getParameter("id");
		System.out.println(" module - "+module);
		System.out.println(" user selection loc "+hrRRForm.getLocation());
		System.out.println(" sub link of - "+request.getParameter("subLink"));
		System.out.println(" link name - "+request.getParameter("sId"));
		int userSelectedlocID=Integer.parseInt(user.getPlantId());
		if(hrRRForm.getLocation() != null)
		{
			userSelectedlocID = Integer.parseInt(hrRRForm.getLocation());
		}
		String locName = "";
		try
		{
			LinkedList list = new LinkedList();
			//get Location and Location names
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			UserDao adMPM=new UserDao();
			String getLocations="select * from Location";
			ResultSet rsGetLocation=adMPM.selectQuery(getLocations);
			while(rsGetLocation.next()){
				if(userSelectedlocID == rsGetLocation.getInt("LOCID")){
					System.out.println("if "+rsGetLocation.getInt("LOCID"));
					locationID.addFirst(rsGetLocation.getInt("LOCID"));
					locName=rsGetLocation.getString("LOCNAME");
					locationName.addFirst(locName);
				}
				else{
					System.out.println("else "+rsGetLocation.getInt("LOCID"));
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("LOCNAME"));
				}
			}
			hrRRForm.setLocationId(locationID);
			hrRRForm.setLocationName(locationName);
			
			int userId = user.getId();
			String userDepart = "";
			if(hrRRForm.getUserDepart() == null){
				ResultSet rs1 = adMPM.selectQuery("select dept_name from emp_master where emp_id="+user.getUserName()+"");
				while (rs1.next()) {
					userDepart = rs1.getString("dept_name");
				}
			}
			else
			{
				userDepart = hrRRForm.getUserDepart();
			}
			
			LinkedList departNames=new LinkedList();
			
			String getDeparts="select dept_name from emp_master group by dept_name";
			ResultSet departRS=adMPM.selectQuery(getDeparts);
			while(departRS.next()){
				if(userDepart.equalsIgnoreCase(departRS.getString("dept_name"))){
					System.out.println("if "+departRS.getString("dept_name"));
					departNames.addFirst(userDepart);
				}
				else{
					System.out.println("else "+departRS.getString("dept_name"));
					departNames.add(departRS.getString("dept_name"));
				}
			}
			hrRRForm.setDepartmentName(departNames);
			
			//get recruitment requests to list
			
			
			list = getListOfRecruitment(locName, userDepart, userId, "");
			
			
			request.setAttribute("header", "Recruitment Request List for Plant");
			request.setAttribute("listDetails", list);
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	String result=(String)sesion.getAttribute("result");
	System.out.println("result="+result);
	if(result==null||result==" ")
	sesion.setAttribute("result"," ");
	
	
	if(request.getParameter("id")!=null){
	
	request.setAttribute("MenuIcon", request.getParameter("id"));
	}
	
		return mapping.findForward("displayRecrutmentList");
	}
		
	
	public ActionForward newHrRecrutmentRequestForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
	    HRRecruitmentForm hrForm=(HRRecruitmentForm)form;	
	    hrForm.setMessage("");
		HRDao ad=new HRDao();
		ArrayList idList=new ArrayList();
		idList.add("");
		/*String getIDs="select Req_Id from Man_Power_Matrix order by Req_Id";
		ResultSet rs=ad.selectQuery(getIDs);
		while(rs.next()){
			
		}*/
		hrForm.setGroupIds(idList);
		}catch (Exception e) {
			System.out.println("exception @ hrrecruitment request");
			e.printStackTrace();
		}
		return mapping.findForward("displayRecruitmentRequestForm");
	}
	
	public ActionForward displayCurrentOpenings(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession sesion=request.getSession();
		try{
		    HRRecruitmentForm hrForm=(HRRecruitmentForm)form;	
		    
			UserInfo user=(UserInfo)sesion.getAttribute("user");
			String module = request.getParameter("id");
			System.out.println(" module - "+module);
			System.out.println(" user selection loc "+hrForm.getLocation());
			System.out.println(" sub link of - "+request.getParameter("subLink"));
			System.out.println(" link name - "+request.getParameter("sId"));
			int userSelectedlocID=Integer.parseInt(user.getPlantId());
			if(hrForm.getLocation() != null)
			{
				userSelectedlocID = Integer.parseInt(hrForm.getLocation());
			}
			int userId = user.getId();
			String locName = "";
			LinkedList list = new LinkedList();
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			UserDao adMPM=new UserDao();
			String getLocations="select * from Location";
			ResultSet rsGetLocation=adMPM.selectQuery(getLocations);
			while(rsGetLocation.next()){
				if(userSelectedlocID == rsGetLocation.getInt("LOCID")){
					System.out.println("if "+rsGetLocation.getInt("LOCID"));
					locationID.addFirst(rsGetLocation.getInt("LOCID"));
					locName=rsGetLocation.getString("LOCNAME");
					locationName.addFirst(locName);
				}
				else{
					System.out.println("else "+rsGetLocation.getInt("LOCID"));
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("LOCNAME"));
				}
			}
			hrForm.setLocationId(locationID);
			hrForm.setLocationName(locationName);
			list = getListOfRecruitment(locName, "openings", userId, "");
			request.setAttribute("header", "Current Openings List for Plant");
			LinkedList openingmoredetails = new LinkedList();
			request.setAttribute("openingMoreDetails", openingmoredetails);
			request.setAttribute("listDetails", list);
		}catch (Exception e) {
			System.out.println("exception @ hrrecruitment request");
			e.printStackTrace();
		}
		String result=(String)sesion.getAttribute("result");
		System.out.println("result="+result);
		if(result==null||result==" ")
		sesion.setAttribute("result"," ");
		
		
		if(request.getParameter("id")!=null){
		
		request.setAttribute("MenuIcon", request.getParameter("id"));
		}
		return mapping.findForward("displayCurrentOpenings");
	}
	
	public LinkedList getListOfRecruitment(String locName, String userSelectedDepart, int uId, String reqId){
		LinkedList list = new LinkedList();
		HRDao ad=new HRDao();
		try{
			String qry = "select * from Recruitment_Request where PrimaryLocation='"+locName+"' and Department='"+userSelectedDepart+"' and User_Id='"+uId+"'";
			if(userSelectedDepart.equalsIgnoreCase("openings")){
				qry = "select * from Recruitment_Request where PrimaryLocation='"+locName+"' and Approvel_Status='Approved'";
			}
			else if(userSelectedDepart.equalsIgnoreCase("moredetails")){
				qry = "select * from Recruitment_Request where Req_Id='"+reqId+"' and Approvel_Status='Approved'";
			}
			ResultSet rs = ad.selectQuery(qry);
			HRRecruitmentForm hrForm=null;
			while (rs.next()) {
	            String locationName1="";
	            hrForm=new HRRecruitmentForm();
	            locationName1 = locName;
	            hrForm.setPlant(locationName1);
	            hrForm.setRecuritmentID(rs.getString("Req_Id"));
	            hrForm.setDepartment(rs.getString("Department"));
	            hrForm.setPrimaryLocation(rs.getString("PrimaryLocation"));
	            hrForm.setJobTitle(rs.getString("Job_Title"));
	            hrForm.setEmpStatus(rs.getString("EmpStatus"));
	            hrForm.setTotalEmp(rs.getString("TotalEmp"));
	            hrForm.setQualifications(rs.getString("Qualification"));
	            hrForm.setShiftType(rs.getString("ShiftType"));
	            hrForm.setJobDescription(rs.getString("JobDescription"));
	            hrForm.setExperience(rs.getString("Experience"));
	            hrForm.setSaleryOffered(rs.getString("SalaryOffered"));
	            hrForm.setRequesterName(rs.getString("Requester_name"));
		        hrForm.setApprovalStatus(rs.getString("Approvel_Status"));
		        hrForm.setMoreUrl("hrRecruitment.do?method=displayMoreDetails&sId=Current Openings&id=HR&reqId="+rs.getString("Req_Id"));
		        hrForm.setApplyUrl("hrRecruitment.do?method=displayApplyForOpenings&sId=Current Openings&id=HR&reqId="+rs.getString("Req_Id"));
				
				list.add(hrForm);
			}
		}catch (Exception e) {
			System.out.println("exception @ hrrecruitment request");
			e.printStackTrace();
		}
		return list;
	}
	
	public ActionForward displayMoreDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession sesion=request.getSession();
		HRRecruitmentForm hrForm=(HRRecruitmentForm)form;
		LinkedList openingmoredetails = new LinkedList();
		try{
			UserInfo user=(UserInfo)sesion.getAttribute("user");
			String module = request.getParameter("id");
			String reqId=request.getParameter("reqId");
			System.out.println(" module - "+module);
			System.out.println(" user selection loc "+hrForm.getLocation());
			System.out.println(" req Id - "+reqId);
			System.out.println(" link name - "+request.getParameter("sId"));
			openingmoredetails = getListOfRecruitment("","moredetails",0,reqId);
		   
		}catch (Exception e) {
			System.out.println("exception @ hrrecruitment request");
			e.printStackTrace();
		}
		request.setAttribute("openingMoreDetails", openingmoredetails);
		request.setAttribute("displayRecordNo","record");
		return mapping.findForward("displayCurrentOpenings");
	}
	
}
