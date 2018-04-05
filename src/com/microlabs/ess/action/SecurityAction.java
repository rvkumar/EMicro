package com.microlabs.ess.action;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.SecurityForm;
import com.microlabs.utilities.UserInfo;

public class SecurityAction extends DispatchAction {
	
	EssDao ad=new EssDao();
	
	String Securitydate="";
	String ReqStatus="";
	
	public ActionForward updateDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SecurityForm secForm=(SecurityForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requestNo=secForm.getRequestNo();
		String requsterID=secForm.getRequsterID();
		String type=secForm.getReqType();
		String status=secForm.getSelectedFilter();
		secForm.setSelectedFilter(status);
	
		if(type.equalsIgnoreCase("On Duty")){
			String update="update OnDuty_details set OutTime='"+secForm.getOutTime()+"',InTime='"+secForm.getInTime()+"',Security_Id='"+user.getEmployeeNo()+"' where request_no='"+secForm.getRequestNo()+"' and user_id='"+secForm.getRequsterID()+"'";
			int i=ad.SqlExecuteUpdate(update);
			if(i>0)
			{
				secForm.setMessage("Details are upated");
			}
			if(type.equalsIgnoreCase("On Duty"))
			{
				try{
					String getOnDutyDetails="select dept.DPTSTXT,desg.DSGSTXT,u.Emp_Photo,emp.SEX,onduty.request_no,onduty.user_id,emp.EMP_FULLNAME,onduty.onDuty_Type,onduty.location,onduty.start_date,onduty.end_date," +
		          	"onduty.startTime,onduty.endTime,onduty.reason,u.Emp_Photo,onduty.OutTime,onduty.InTime from OnDuty_details as onduty,users as u,emp_official_info as emp,DESIGNATION as desg,DEPARTMENT as dept where request_no='"+requestNo+"' " +
		          	"and user_id='"+requsterID+"' and Approver_Status='"+status+"' and onduty.user_id=u.employeenumber and onduty.user_id=emp.PERNR and emp.PERNR=u.employeenumber and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
		          	ResultSet rs=ad.selectQuery(getOnDutyDetails);
		        
	          	while(rs.next()){
	          		secForm.setRequestNo(rs.getString("request_no"));
	          		secForm.setRequsterID(rs.getString("user_id"));
	          		secForm.setUserName(rs.getString("EMP_FULLNAME"));
	          		secForm.setOndutyType(rs.getString("onDuty_Type"));
	          		secForm.setDepartment(rs.getString("DPTSTXT"));
	          		secForm.setDesignation(rs.getString("DSGSTXT"));
	          		secForm.setReqType("On Duty");
	          		secForm.setLocation(rs.getString("location"));
	          		secForm.setFromDate(rs.getString("start_date"));
	          		secForm.setToDate(rs.getString("end_date"));
	          		secForm.setFromTime(rs.getString("startTime"));
	          		secForm.setToTime(rs.getString("endTime"));
	          		secForm.setReason(rs.getString("reason"));
	          		String empSex=rs.getString("SEX");
				    String empImg=rs.getString("Emp_Photo");
				   
				    secForm.setEmpPhoto(rs.getString("Emp_Photo"));
				    
				    if(empImg.equalsIgnoreCase(""))
				    {
				    	if(empSex.equalsIgnoreCase("M"))
				    	{
				    		secForm.setEmpPhoto("male.png");
				    	}
				    	if(empSex.equalsIgnoreCase("F"))
				    	{
				    		secForm.setEmpPhoto("female.png");
				    	}
				    	
				    }
	          		
	          		String outTime="";
	          		secForm.setOutTime(outTime);
	          		String inTime="";
	          		secForm.setInTime(inTime);
	          		outTime=rs.getString("OutTime");
	          		inTime=rs.getString("InTime");
	          		if(outTime!=null){
	          			secForm.setOutTime(outTime);
	          		}
	          		if(inTime!=null){
	          			secForm.setInTime(inTime);
	          		}
	          	}
	          	request.setAttribute("On Duty", "On Duty");
	          	}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(type.equalsIgnoreCase("Permission")){
			String updatePermission="update Permission_details set OutTime='"+secForm.getOutTime()+"',InTime='"+secForm.getInTime()+"',Security_Id='"+user.getEmployeeNo()+"' where request_no='"+secForm.getRequestNo()+"' and user_id='"+secForm.getRequsterID()+"'";
			int i1=ad.SqlExecuteUpdate(updatePermission);
			if(i1>0)
			{
				secForm.setMessage("Details are saved");
			}
			
			//set permission values
			int filter=0;
			if(status.equalsIgnoreCase("In Process"))
			{
				filter=0;
			}
			if(status.equalsIgnoreCase("Approved"))
			{
				filter=1;
			}
			
			String permissionDetails="select dept.DPTSTXT,desg.DSGSTXT,empU.SEX,per.request_no,per.user_id,empU.EMP_FULLNAME,per.date,per.startTime,per.endTime,per.reason,per.InTime,per.OutTime," +
			"u.Emp_Photo from Permission_details as per,emp_official_info as empU,users as u,DEPARTMENT as dept,DESIGNATION as desg where per.Approver_Status='"+filter+"' and empU.PERNR=per.user_id and u.employeenumber=per.user_id " +
			"and empU.PERNR=u.employeenumber  and desg.DSGID=empU.DSGID and dept.DPTID=empU.DPTID";
		ResultSet rs=ad.selectQuery(permissionDetails);
			try{
			while(rs.next()){
				secForm.setRequestNo(rs.getString("request_no"));
	      		secForm.setRequsterID(rs.getString("user_id"));
	      		secForm.setUserName(rs.getString("EMP_FULLNAME"));
	      		secForm.setDepartment(rs.getString("DPTSTXT"));
	      		secForm.setDesignation(rs.getString("DSGSTXT"));
	      		secForm.setReqType("Permission");
	      		secForm.setFromDate(rs.getString("date"));
	      		secForm.setFromTime(rs.getString("startTime"));
	      		secForm.setToTime(rs.getString("endTime"));
	      		secForm.setReason(rs.getString("reason"));
	      		String empSex=rs.getString("SEX");
			    String empImg=rs.getString("Emp_Photo");
			   
			    secForm.setEmpPhoto(rs.getString("Emp_Photo"));
			    
			    if(empImg.equalsIgnoreCase(""))
			    {
			    	if(empSex.equalsIgnoreCase("M"))
			    	{
			    		secForm.setEmpPhoto("male.png");
			    	}
			    	if(empSex.equalsIgnoreCase("F"))
			    	{
			    		secForm.setEmpPhoto("female.png");
			    	}
			    	
			    }
	      		String outTime="";
	      		secForm.setOutTime(outTime);
	      		String inTime="";
	      		secForm.setInTime(inTime);
	      		outTime=rs.getString("OutTime");
	      		inTime=rs.getString("InTime");
	      		if(outTime!=null){
	      			secForm.setOutTime(outTime);
	      		}
	      		if(inTime!=null){
	      			secForm.setInTime(inTime);
	      		}
	      	}
	      	request.setAttribute("Permission", "Permission");
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	
	
	return mapping.findForward("editDetails");
}
	
	public ActionForward editDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SecurityForm secForm=(SecurityForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String requestNo=request.getParameter("requestNo");
		String requsterID=request.getParameter("requsterID");
		String status=request.getParameter("status");
		secForm.setSelectedFilter(status);
	
		String type=request.getParameter("type");
		if(type.equalsIgnoreCase("On Duty"))
		{
			
          	String getOnDutyDetails="select dept.DPTSTXT,desg.DSGSTXT,u.Emp_Photo,emp.SEX,onduty.request_no,onduty.user_id,emp.EMP_FULLNAME,onduty.onDuty_Type,onduty.location,onduty.start_date,onduty.end_date," +
          	"onduty.startTime,onduty.endTime,onduty.reason,u.Emp_Photo,onduty.OutTime,onduty.InTime from OnDuty_details as onduty,users as u,emp_official_info as emp,DESIGNATION as desg,DEPARTMENT as dept where request_no='"+requestNo+"' " +
          	"and user_id='"+requsterID+"' and Approver_Status='"+status+"' and onduty.user_id=u.employeenumber and onduty.user_id=emp.PERNR and emp.PERNR=u.employeenumber and dept.DPTID=emp.DPTID and desg.DSGID=emp.DSGID";
          	ResultSet rs=ad.selectQuery(getOnDutyDetails);
          	try{
          	while(rs.next()){
          		secForm.setRequestNo(rs.getString("request_no"));
          		secForm.setRequsterID(rs.getString("user_id"));
          		secForm.setUserName(rs.getString("EMP_FULLNAME"));
          		secForm.setOndutyType(rs.getString("onDuty_Type"));
          		secForm.setDepartment(rs.getString("DPTSTXT"));
          		secForm.setDesignation(rs.getString("DSGSTXT"));
          		secForm.setReqType("On Duty");
          		
          		
          		secForm.setLocation(rs.getString("location"));
          		secForm.setFromDate(rs.getString("start_date"));
          		secForm.setToDate(rs.getString("end_date"));
          		secForm.setFromTime(rs.getString("startTime"));
          		secForm.setToTime(rs.getString("endTime"));
          		secForm.setReason(rs.getString("reason"));
          		String empSex=rs.getString("SEX");
			    String empImg=rs.getString("Emp_Photo");
			   
			    secForm.setEmpPhoto(rs.getString("Emp_Photo"));
			    
			    if(empImg.equalsIgnoreCase(""))
			    {
			    	if(empSex.equalsIgnoreCase("M"))
			    	{
			    		secForm.setEmpPhoto("male.png");
			    	}
			    	if(empSex.equalsIgnoreCase("F"))
			    	{
			    		secForm.setEmpPhoto("female.png");
			    	}
			    	
			    }
          		
          		String outTime="";
          		secForm.setOutTime(outTime);
          		String inTime="";
          		secForm.setInTime(inTime);
          		outTime=rs.getString("OutTime");
          		inTime=rs.getString("InTime");
          		if(outTime!=null){
          			secForm.setOutTime(outTime);
          		}
          		if(inTime!=null){
          			secForm.setInTime(inTime);
          		}
          	}
          	request.setAttribute("On Duty", "On Duty");
          	}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		int filter=0;
		if(status.equalsIgnoreCase("In Process"))
		{
			filter=0;
		}
		if(status.equalsIgnoreCase("Approved"))
		{
			filter=1;
		}
		if(type.equalsIgnoreCase("Permission")){
			String permissionDetails="select dept.DPTSTXT,desg.DSGSTXT,empU.SEX,per.request_no,per.user_id,empU.EMP_FULLNAME,per.date,per.startTime,per.endTime,per.reason,per.InTime,per.OutTime," +
		"u.Emp_Photo from Permission_details as per,emp_official_info as empU,users as u,DEPARTMENT as dept,DESIGNATION as desg where per.Approver_Status='"+filter+"' and empU.PERNR=per.user_id and u.employeenumber=per.user_id " +
		"and empU.PERNR=u.employeenumber  and desg.DSGID=empU.DSGID and dept.DPTID=empU.DPTID and per.user_id='"+requsterID+"'";
		ResultSet rs=ad.selectQuery(permissionDetails);
		try{
		while(rs.next()){
			secForm.setRequestNo(rs.getString("request_no"));
      		secForm.setRequsterID(rs.getString("user_id"));
      		secForm.setUserName(rs.getString("EMP_FULLNAME"));
      		secForm.setDepartment(rs.getString("DPTSTXT"));
      		secForm.setDesignation(rs.getString("DSGSTXT"));
      		secForm.setReqType("Permission");
      		secForm.setFromDate(rs.getString("date"));
      		secForm.setFromTime(rs.getString("startTime"));
      		secForm.setToTime(rs.getString("endTime"));
      		secForm.setReason(rs.getString("reason"));
      		String empSex=rs.getString("SEX");
		    String empImg=rs.getString("Emp_Photo");
		   
		    secForm.setEmpPhoto(rs.getString("Emp_Photo"));
		    
		    if(empImg.equalsIgnoreCase(""))
		    {
		    	if(empSex.equalsIgnoreCase("M"))
		    	{
		    		secForm.setEmpPhoto("male.png");
		    	}
		    	if(empSex.equalsIgnoreCase("F"))
		    	{
		    		secForm.setEmpPhoto("female.png");
		    	}
		    	
		    }
      		String outTime="";
      		secForm.setOutTime(outTime);
      		String inTime="";
      		secForm.setInTime(inTime);
      		outTime=rs.getString("OutTime");
      		inTime=rs.getString("InTime");
      		if(outTime!=null){
      			secForm.setOutTime(outTime);
      		}
      		if(inTime!=null){
      			secForm.setInTime(inTime);
      		}
      	}
      	request.setAttribute("Permission", "Permission");
		}catch (Exception e) {
			e.printStackTrace();
		}
		}
		return mapping.findForward("editDetails");
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		SecurityForm secForm=(SecurityForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		LinkedList listOfOndutyRequest=new LinkedList();
		int prev=secForm.getPrev();
		int start=prev-10;
		//start--;
		//prev--;
		if(start<0)
			start=0;
		secForm.setStartRecord(start+1);
		secForm.setEndRecord(prev);
		int i=0;
		int row=0;
		EssDao ad=new EssDao();
	
String dateNow=secForm.getSecurityDate();
		
		secForm.setSecurityDate(dateNow);
		String type=secForm.getSelectedFilter();
		secForm.setSelectedFilter(type);

		listOfOndutyRequest=searchOndutyList(mapping, secForm, request, response);
		
		ArrayList NewList=new ArrayList();
		
		
		Iterator it=listOfOndutyRequest.iterator();
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
		secForm.setPrev(prev-10);
		secForm.setNext(prev);
	
		request.setAttribute("displayRecordNo","displayRecordNo");
		
		request.setAttribute("allrecords", NewList);
		
		
		
		return mapping.findForward("displayPending");
		
	}
	
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		SecurityForm secForm=(SecurityForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList listOfOndutyRequest=new LinkedList();
		int prev=secForm.getPrev();
		int start=0;
		
		prev--;
		
		int i=0;
		int row=0;
		EssDao ad=new EssDao();
		
      String dateNow=secForm.getSecurityDate();
		
		secForm.setSecurityDate(dateNow);
		String type=secForm.getSelectedFilter();
		secForm.setSelectedFilter(type);
		
 listOfOndutyRequest=searchOndutyList(mapping, secForm, request, response);
		
		ArrayList NewList=new ArrayList();
		ListIterator it=listOfOndutyRequest.listIterator();
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
		secForm.setStartRecord(i+1);
		System.out.println("------------------------------"+i+1);
		secForm.setPrev(i);
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
	
	
	
		secForm.setEndRecord(l);
	
	
	request.setAttribute("displayRecordNo","ok");
			
				
					request.setAttribute("allrecords", NewList);
		
		
		return mapping.findForward("displayPending");
	}
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		SecurityForm secForm=(SecurityForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList listOfOndutyRequest=new LinkedList();
		
		int nextval=secForm.getNext();
		int i=0;
		i=nextval;
		secForm.setStartRecord(i+1);
		secForm.setPrev(nextval);
		int end=nextval+10;
		int start=0;
		int row=0;
		
		LinkedList Alldetails=new LinkedList();
		LinkedList onduty=new LinkedList();
		LinkedList permission=new LinkedList();
		
		String dateNow=secForm.getSecurityDate();
		
		secForm.setSecurityDate(dateNow);
		String type=secForm.getSelectedFilter();
		secForm.setSelectedFilter(type);

		listOfOndutyRequest=searchOndutyList(mapping, secForm, request, response);
		
		ArrayList NewList=new ArrayList();
		Iterator it=listOfOndutyRequest.iterator();
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
		secForm.setEndRecord(nextval);
		if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
		request.setAttribute("previousButton", "ok");
		}
		else{
			request.setAttribute("disableNextButton","yes");
		request.setAttribute("previousButton", "ok");
		}
		request.setAttribute("displayRecordNo","ok");	
		
		secForm.setNext(nextval);
	

		 request.setAttribute("displayRecordNo", "displayRecordNo");
		request.setAttribute("allrecords", NewList);
	
		return mapping.findForward("displayPending");
	}

	public LinkedList searchPermissionList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		LinkedList permission=new LinkedList();
		
		String getPermissionDt="Select dept.DPTSTXT,desg.DSGSTXT, per.request_no,per.user_id,empU.EMP_FULLNAME,per.date,per.startTime," +
		"per.endTime,per. reason,per.Approver_id ,empA.EMP_FULLNAME as approverName,per.Approver_Status from " +
		"Permission_details as per,emp_official_info empU,emp_official_info as empA,DEPARTMENT as dept,DESIGNATION as desg where per.Approver_Status='1'" +
		" and empU.PERNR=per.user_id and empA.PERNR=per.Last_approver and dept.DPTID=empU.DPTID and desg.DSGID=empU.DSGID and( per.InTime='' or per.OutTime='' ) order by per.id desc";
ResultSet rs1=ad.selectQuery(getPermissionDt);
try {
	while(rs1.next()){
		SecurityForm form1=new SecurityForm();
		form1.setRequestNo(rs1.getString("request_no"));
		form1.setUserId(rs1.getString("user_id"));
		form1.setUserName(rs1.getString("EMP_FULLNAME"));
		form1.setDepartment(rs1.getString("DPTSTXT"));
		form1.setDesignation(rs1.getString("DSGSTXT"));
		form1.setReqType("Permission");
		form1.setOndutyType(" ");
		form1.setFromDate(rs1.getString("date"));
		form1.setFromTime(rs1.getString("startTime"));
		form1.setToDate(" ");
		form1.setToTime(rs1.getString("endTime"));
		form1.setApproverId(rs1.getString("Approver_id"));
		form1.setApproverName(rs1.getString("approverName"));
		
		form1.setApproveStatus("Approved");
		permission.add(form1);
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
request.setAttribute("permission", permission);



return permission;
		
	}
	
	
	public LinkedList searchOndutyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		SecurityForm secForm=(SecurityForm)form;
		
		String date=secForm.getSecurityDate();
		String type =secForm.getSelectedFilter();
		secForm.setSelectedFilter(type);
	
		if(date!=null)
		{
		Securitydate=date;
		}
		else
		{
			date=Securitydate;
		}
		if(type!=null)
		{
		ReqStatus=type;
		}
		else
		{
			type=ReqStatus;
		}
		secForm.setSecurityDate(date);
		int totalRecords=secForm.getTotalRecords();
		int endrecord=secForm.getEndRecord();
		
		LinkedList Alldetails=new LinkedList();
		LinkedList onduty=new LinkedList();
		LinkedList permission=new LinkedList();
		String getOndutyApprovedDt="";
	
		if(type.equalsIgnoreCase("Approved")){
		
		 getOndutyApprovedDt="Select dept.DPTSTXT, desg.DSGSTXT, onduty.request_no,onduty.user_id,empU.EMP_FULLNAME,onduty.onDuty_Type,onduty.submit_date," +
		"onduty.start_date, onduty.end_date,onduty.startTime,onduty.endTime,onduty.Approver_id,onduty.InTime,onduty.OutTime,empA.EMP_FULLNAME as approverName," +
		"onduty.Approver_Status from OnDuty_details as onduty,emp_official_info as empU,emp_official_info as empA,DEPARTMENT as " +
		"dept,DESIGNATION as desg where onduty.Approver_Status='"+type+"' and convert(varchar(10),onduty.approved_date,103)='"+date+"' " +
		"and empU.PERNR=onduty.user_id and empA.PERNR=onduty.Approver_id  and (onduty.startTime !='09:00AM' or onduty.endTime !='06:00PM') and" +
		" dept.DPTID=empU.DPTID and desg.DSGID=empU.DSGID and( onduty.InTime='' or onduty.OutTime='' ) order by onduty.id desc";
		
		}
		
		if(type.equalsIgnoreCase("In Process")){
			
			 getOndutyApprovedDt="Select dept.DPTSTXT, desg.DSGSTXT, onduty.request_no,onduty.user_id,empU.EMP_FULLNAME,onduty.onDuty_Type,onduty.submit_date," +
			"onduty.start_date, onduty.end_date,onduty.startTime,onduty.endTime,onduty.Approver_id,onduty.InTime,onduty.OutTime,empA.EMP_FULLNAME as approverName," +
			"onduty.Approver_Status from OnDuty_details as onduty,emp_official_info as empU,emp_official_info as empA,DEPARTMENT as " +
			"dept,DESIGNATION as desg where onduty.Approver_Status='"+type+"' and onduty.submit_date='"+date+"' " +
			"and empU.PERNR=onduty.user_id and empA.PERNR=onduty.Approver_id  and (onduty.startTime !='09:00AM' or onduty.endTime !='06:00PM') and" +
			" dept.DPTID=empU.DPTID and desg.DSGID=empU.DSGID and( onduty.InTime='' or onduty.OutTime='' ) order by onduty.id desc";
			
			}
ResultSet rs=ad.selectQuery(getOndutyApprovedDt);
try {
	while(rs.next()){
		SecurityForm form2=new SecurityForm();
		form2.setRequestNo(rs.getString("request_no"));
		form2.setRequsterID(rs.getString("user_id"));
		
		form2.setUserName(rs.getString("EMP_FULLNAME"));
		form2.setReqType("On Duty");
		form2.setDepartment(rs.getString("DPTSTXT"));
		form2.setDesignation(rs.getString("DSGSTXT"));
		form2.setOndutyType(rs.getString("onDuty_Type"));
		form2.setFromDate(rs.getString("start_date"));
		form2.setFromTime(rs.getString("startTime"));
		form2.setToDate(rs.getString("end_date"));
		form2.setToTime(rs.getString("endTime"));
		form2.setApproverId(rs.getString("Approver_id"));
		form2.setApproverName(rs.getString("approverName"));
		form2.setApproveStatus(rs.getString("Approver_Status"));
		form2.setInTime(rs.getString("InTime"));
		form2.setOutTime(rs.getString("OutTime"));
		onduty.add(form2);
	}
} catch (SQLException e1) {
	
	e1.printStackTrace();
}


String getPermissionDt="";
String status="0";
if(type.equalsIgnoreCase("Approved"))
{
	 status="1";
	 
	 getPermissionDt="Select dept.DPTSTXT,desg.DSGSTXT, per.request_no,per.user_id,empU.EMP_FULLNAME,per.date,per.startTime," +
	 "per.endTime,per. reason,per.Approver_id ,per.OutTime,per.InTime,empA.EMP_FULLNAME as approverName,per.Approver_Status from " +
	 "Permission_details as per,emp_official_info empU,emp_official_info as empA,DEPARTMENT as dept,DESIGNATION as desg where per.Approver_Status='"+status+"'" +
	 " and convert(varchar(10),per.approved_date,103)='"+date+"' and (per.startTime !='09:00AM' or per.endTime !='06:00PM') and " +
	 "empU.PERNR=per.user_id and empA.PERNR=per.Approver_id and dept.DPTID=empU.DPTID and desg.DSGID=empU.DSGID and" +
	 "( per.InTime='' or per.OutTime='' ) order by per.id desc";
}

if(type.equalsIgnoreCase("In Process"))
{
	 status="0";
	 
	 getPermissionDt="Select dept.DPTSTXT,desg.DSGSTXT, per.request_no,per.user_id,empU.EMP_FULLNAME,per.date,per.startTime," +
		"per.endTime,per. reason,per.Approver_id ,per.OutTime,per.InTime,empA.EMP_FULLNAME as approverName,per.Approver_Status from " +
		"Permission_details as per,emp_official_info empU,emp_official_info as empA,DEPARTMENT as dept,DESIGNATION as desg where per.Approver_Status='"+status+"'" +
		" and convert(varchar(10),per.date,103)='"+date+"' and (per.startTime !='09:00AM' or per.endTime !='06:00PM') and " +
		"empU.PERNR=per.user_id and empA.PERNR=per.Approver_id and dept.DPTID=empU.DPTID and desg.DSGID=empU.DSGID and" +
		"( per.InTime='' or per.OutTime='' ) order by per.id desc";
}



ResultSet rs1=ad.selectQuery(getPermissionDt);
try {
while(rs1.next()){
SecurityForm form2=new SecurityForm();
form2.setRequestNo(rs1.getString("request_no"));
form2.setRequsterID(rs1.getString("user_id"));
form2.setUserName(rs1.getString("EMP_FULLNAME"));
form2.setDepartment(rs1.getString("DPTSTXT"));
form2.setDesignation(rs1.getString("DSGSTXT"));
form2.setReqType("Permission");
form2.setOndutyType(" ");
form2.setFromDate(rs1.getString("date"));
form2.setFromTime(rs1.getString("startTime"));
form2.setToDate(" ");
form2.setToTime(rs1.getString("endTime"));
form2.setApproverId(rs1.getString("Approver_id"));
form2.setApproverName(rs1.getString("approverName"));
form2.setInTime(rs1.getString("InTime"));
form2.setOutTime(rs1.getString("OutTime"));
String stat=rs1.getString("Approver_Status");
if(stat.equalsIgnoreCase("0"))
{
	stat="In Process";
}
else
{
	stat="Approved";
}
form2.setApproveStatus(stat);
onduty.add(form2);
}
} catch (SQLException e) {

e.printStackTrace();
}

		
			
		return onduty;
	}
	
	public ActionForward pedingList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SecurityForm secForm=(SecurityForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int totalRecords1=0;
		String getOndutyCount="select COUNT(*) from OnDuty_details where Approver_Status='Approved'";
		LinkedList listOfEmp=new LinkedList();
		
		LinkedList listOfOndutyRequest=new LinkedList();
		LinkedList listOfPermissionRequest=new LinkedList();
		
		try{
			Date dNow = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			String dateNow = ft.format(dNow);
			
			int  totalRecords=0;
			  int  startRecord=0;
			  int  endRecord=0;
			  try{/*
			  String getTotalRecords="select COUNT(*) from All_Request as all_r,OnDuty_details " +
				"as onduty,Permission_details as per,emp_official_info as u,emp_official_info as appr,DEPARTMENT as dept,DESIGNATION as desg " +
				"where convert(varchar(10),all_R.approved_date,103)='"+dateNow+"' and (all_r.Req_Type='On Duty' or all_r.Req_Type='Permission') and onduty.request_no=all_r.Req_Id and per.request_no=all_r.Req_Id " +
				"and all_r.Req_Status='Approved' and (onduty.InTime='' or onduty.OutTime='') and (per.InTime='' or per.OutTime='') and" +
				" all_r.Requester_Name=u.PERNR and all_r.Approved_Persons=appr.PERNR and u.DPTID=dept.DPTID and u.DSGID=desg.DSGID ";
			  
			  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
			  while(rsTotalRecods.next())
			  {
				  totalRecords=rsTotalRecods.getInt(1);
			  }
			  
			  
			  
			    if(totalRecords>=10)
			    {
						  secForm.setTotalRecords(totalRecords);
					  startRecord=1;
					  endRecord=10;
					  secForm.setStartRecord(1);
					  secForm.setEndRecord(10);
					  request.setAttribute("displayRecordNo", "displayRecordNo");
					  request.setAttribute("nextButton", "nextButton");
				}
			    
			    else
				  {
					  startRecord=1;
					  endRecord=totalRecords;
					  secForm.setTotalRecords(totalRecords);
					  secForm.setStartRecord(1);
					  secForm.setEndRecord(totalRecords); 
				  

		
		
		 System.out.println(listOfOndutyRequest.size());
}
	    
	    
	    
	    
*/}catch (Exception e) {
	e.printStackTrace();
}



secForm.setSecurityDate(dateNow);
secForm.setSelectedFilter("Approved");

listOfOndutyRequest=searchOndutyList(mapping, secForm, request, response);

System.out.println(listOfOndutyRequest.size());

ArrayList newList=new ArrayList();

Iterator it=listOfOndutyRequest.iterator();

int i=0;
while(i<10){
	if(it.hasNext()){
		newList.add(it.next());
	i++;
	}
	else
		break;
}
secForm.setStartRecord(1);
secForm.setEndRecord(i);
secForm.setNext(i);
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
request.setAttribute("allrecords", newList);



		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(listOfOndutyRequest.size()==0)
		{
			
			request.setAttribute("No records", "No records");
			
			
			}
		
		
		return mapping.findForward("displayPending");
		
	}
	
	public ActionForward pendingList1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SecurityForm secForm=(SecurityForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int totalRecords1=0;
		String getOndutyCount="select COUNT(*) from OnDuty_details where Approver_Status='Approved'";
		LinkedList listOfEmp=new LinkedList();
		
		LinkedList listOfOndutyRequest=new LinkedList();
		LinkedList listOfPermissionRequest=new LinkedList();
		
		try{
			String Date =secForm.getSecurityDate();
			
			int  totalRecords=0;
			  int  startRecord=0;
			  int  endRecord=0;




secForm.setSecurityDate(Date);
String type=secForm.getSelectedFilter();
secForm.setSelectedFilter(type);

listOfOndutyRequest=searchOndutyList(mapping, secForm, request, response);

ArrayList newList=new ArrayList();

Iterator it=listOfOndutyRequest.iterator();

int i=0;
while(i<10){
	if(it.hasNext()){
		newList.add(it.next());
	i++;
	}
	else
		break;
}
secForm.setStartRecord(1);
secForm.setEndRecord(i);
secForm.setNext(i);
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

request.setAttribute("allrecords", newList);

System.out.println(listOfOndutyRequest.size());

if(listOfOndutyRequest.size()==0)
{
	
	request.setAttribute("No records", "No records");
	
	
	}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return mapping.findForward("displayPending");
		
	}
}


