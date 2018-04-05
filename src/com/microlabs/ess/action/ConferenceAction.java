package com.microlabs.ess.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.microlabs.ess.dao.ConferenceMail;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.ConferenceForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class ConferenceAction extends DispatchAction{
	
	public ActionForward searchConfRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm conForm=(ConferenceForm)form;
		EssDao ad=new EssDao();
		
		String locId=conForm.getLocationId();
		String floor=conForm.getFloor();
		String room=conForm.getRoomName();
		
		request.setAttribute("ResetSearch", "ResetSearch");
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		
		conForm.setLocationIdList(locationList);
		conForm.setLocationLabelList(locationLabelList);
		
		String reqFromDate=conForm.getFromDate();
		
	    String a1[]=reqFromDate.split("/");
	    reqFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
	    String fromtime=conForm.getFromTime();
	    String pm="PM";
	   if(fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1 ) {
		   fromtime=fromtime.replaceAll("PM","");
		   String a[]=fromtime.split(":");
		   if(Integer.parseInt(a[0])!=12){
	       int hr=Integer.parseInt(a[0])+12;
	       reqFromDate=reqFromDate+" "+hr+":"+a[1];
		   }else{
			   fromtime=fromtime.replaceAll("PM","");
				reqFromDate=reqFromDate+" "+fromtime;
		   }
		}else{
			fromtime=fromtime.replaceAll("AM","");
			reqFromDate=reqFromDate+" "+fromtime;
		}
	   
	  
	   String reqToDate=conForm.getFromDate();
	   String b1[]=reqToDate.split("/");
	    reqToDate=b1[2]+"-"+b1[1]+"-"+b1[0];
	    String totime=conForm.getToTime();
	   if(totime.toLowerCase().indexOf(pm.toLowerCase()) != -1 ) {
		   totime=totime.replaceAll("PM","");
		   String a[]=totime.split(":");
		   if(Integer.parseInt(a[0])!=12){
	       int hr=Integer.parseInt(a[0])+12;
	       reqToDate=reqToDate+" "+hr+":"+a[1];
		   }else{
			   totime=totime.replaceAll("PM","");
				reqToDate=reqToDate+" "+totime;
		   }
		}else{
			totime=totime.replaceAll("AM","");
			reqToDate=reqToDate+" "+totime;
		}
		
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	    String dateNow = ft.format(dNow);
		List resarvedList=new LinkedList();
		String checkAvilbilty="select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
	   	+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from ConferenceRoom_Details conf,"
	   	+ "emp_official_info emp,Location loc where ((conf.Req_From_Date between '"+reqFromDate+"' and '"+reqToDate+"') or "
	   	+ "(conf.Req_To_Date between '"+reqFromDate+"' and '"+reqToDate+"')) and emp.PERNR=conf.Requster_Id "
	   	+ "and conf.Loc_Id=loc.LOCID and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID";
			 	
	 	if(conForm.getFromTime().equals("")){
	 		String fromDate=conForm.getFromDate();
	 	   String b2[]=fromDate.split("/");
	 	  fromDate=b2[2]+"-"+b2[1]+"-"+b2[0];
	 		checkAvilbilty="select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
		   	+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from ConferenceRoom_Details conf,"
		   	+ "emp_official_info emp,Location loc where (conf.From_Date='"+fromDate+"' or conf.To_Date='"+fromDate+"' ) and emp.PERNR=conf.Requster_Id "
		   	+ "and conf.Loc_Id=loc.LOCID and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID";
	 		
	 	}
	 	   
	   ResultSet rs=ad.selectQuery(checkAvilbilty);
		   try{
			   while(rs.next()){
			   ConferenceForm c=new ConferenceForm();
				   c.setReqNo(rs.getString("Reqest_No"));
				   c.setLocation(rs.getString("LOCATION_CODE"));
				   c.setEmpName(rs.getString("EMP_FULLNAME"));
				   c.setFloor(rs.getString("Floor"));
				   c.setRoomName(rs.getString("Conf_Room"));
				   c.setFromDate(rs.getString("From_Date")+" "+rs.getString("From_Time"));
				   c.setToDate(rs.getString("To_Date")+" "+rs.getString("To_Time"));
				   c.setApprovalStatus(rs.getString("approval_status"));
			   resarvedList.add(c);
			   
			   }
			   }catch(Exception e){
			   e.printStackTrace();
		   }
		   if(resarvedList.size()>0){
			   request.setAttribute("resarvedList", resarvedList);
		   }else{
			   request.setAttribute("NoRecords", "NoRecords");
		   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward("confBookedList");
	}
	
	
	public ActionForward checkAvailablety1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm conForm=(ConferenceForm)form;
		EssDao ad=new EssDao();
		String locId=request.getParameter("locID");
		String floor=request.getParameter("floor");
		String room=request.getParameter("room");
		String reqFromDate=request.getParameter("fromDate");
		
	    String a1[]=reqFromDate.split("/");
	    reqFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
	    String fromtime=request.getParameter("formTime");
	    String pm="PM";
	   if(fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1 ) {
		   fromtime=fromtime.replaceAll("PM","");
		   String a[]=fromtime.split(":");
		   if(Integer.parseInt(a[0])!=12){
	       int hr=Integer.parseInt(a[0])+12;
	       reqFromDate=reqFromDate+" "+hr+":"+a[1];
		   }else{
			   fromtime=fromtime.replaceAll("PM","");
				reqFromDate=reqFromDate+" "+fromtime;
		   }
		}else{
			fromtime=fromtime.replaceAll("AM","");
			reqFromDate=reqFromDate+" "+fromtime;
		}
	   
	   String reqToDate=request.getParameter("fromDate");
	    String b1[]=reqToDate.split("/");
	    reqToDate=b1[2]+"-"+b1[1]+"-"+b1[0];
	    String totime=request.getParameter("toTime");
	   if(totime.toLowerCase().indexOf(pm.toLowerCase()) != -1 ) {
		   totime=totime.replaceAll("PM","");
		   String a[]=totime.split(":");
		   if(Integer.parseInt(a[0])!=12){
	       int hr=Integer.parseInt(a[0])+12;
	       reqToDate=reqToDate+" "+hr+":"+a[1];
		   }else{
			   totime=totime.replaceAll("PM","");
				reqToDate=reqToDate+" "+totime;
		   }
		}else{
			totime=totime.replaceAll("AM","");
			reqToDate=reqToDate+" "+totime;
		}
	   String fromDt=EMicroUtils.dateConvert(request.getParameter("fromDate"));
	   String toDt=EMicroUtils.dateConvert(request.getParameter("toDate"));
	   int count=0;
	   
	   List resarvedList=new LinkedList();
	  /* select conf.Reqest_No,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from ConferenceRoom_Details conf,emp_official_info emp,Location loc where (('2014-08-25 10:03:00.000' between Req_From_Date and Req_To_Date) or
			   ('2014-08-25 11:03:00.000' between Req_From_Date and Req_To_Date)) and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID*/
	   String checkAvilbilty="select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,"
	   + "conf.To_Date,conf.To_Time,conf.approval_status from ConferenceRoom_Details conf,emp_official_info emp,Location loc "
	   + "where (( Req_From_Date between '"+reqFromDate+"'  and '"+reqToDate+"') or ( Req_To_Date  between '"+reqFromDate+"'  and '"+reqToDate+"')) "
	   + "and conf.Loc_Id='"+locId+"' and conf.Floor='"+floor+"' and conf.Conf_Room='"+room+"' and emp.PERNR=conf.Requster_Id and "
	   + "conf.Loc_Id=loc.LOCID ";
	   ResultSet rs=ad.selectQuery(checkAvilbilty);
	   try{
		   while(rs.next()){
		   ConferenceForm c=new ConferenceForm();
			   c.setReqNo(rs.getString("Reqest_No"));
			   c.setLocation(rs.getString("LOCATION_CODE"));
			   c.setEmpName(rs.getString("EMP_FULLNAME"));
			   c.setFloor(rs.getString("Floor"));
			   c.setRoomName(rs.getString("Conf_Room"));
			   c.setFromDate(rs.getString("From_Date")+" "+rs.getString("From_Time"));
			   c.setToDate(rs.getString("To_Date")+" "+rs.getString("To_Time"));
			   c.setApprovalStatus(rs.getString("approval_status"));
		   resarvedList.add(c);
		   
		   }
		   }catch(Exception e){
		   e.printStackTrace();
	   }
	   if(resarvedList.size()>0){
		   request.setAttribute("resarvedList", resarvedList);
	   }else{
		   request.setAttribute("NoRecords", "NoRecords");
	   }
	   
		return mapping.findForward("availabletyStatus");
	}
	
	public ActionForward checkAvailablety(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm conForm=(ConferenceForm)form;
		EssDao ad=new EssDao();
		String locId=request.getParameter("locID");
		String floor=request.getParameter("floor");
		String room=request.getParameter("room");
		String reqFromDate=request.getParameter("fromDate");
		
	    String a1[]=reqFromDate.split("/");
	    reqFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
	    String fromtime=request.getParameter("formTime");
	    String pm="PM";
	   if(fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1 ) {
		   fromtime=fromtime.replaceAll("PM","");
		   String a[]=fromtime.split(":");
	       int hr=Integer.parseInt(a[0])+12;
	       reqFromDate=reqFromDate+" "+hr+":"+a[1];
		}else{
			fromtime=fromtime.replaceAll("AM","");
			reqFromDate=reqFromDate+" "+fromtime;
		}
	   
	   String reqToDate=request.getParameter("fromDate");
	    String b1[]=reqToDate.split("/");
	    reqToDate=b1[2]+"-"+b1[1]+"-"+b1[0];
	    String totime=request.getParameter("toTime");
	   if(totime.toLowerCase().indexOf(pm.toLowerCase()) != -1 ) {
		   totime=totime.replaceAll("PM","");
		   String a[]=totime.split(":");
	       int hr=Integer.parseInt(a[0])+12;
	       reqToDate=reqToDate+" "+hr+":"+a[1];
		}else{
			totime=totime.replaceAll("AM","");
			reqToDate=reqToDate+" "+totime;
		}
	   String fromDt=EMicroUtils.dateConvert(request.getParameter("fromDate"));
	   String toDt=EMicroUtils.dateConvert(request.getParameter("toDate"));
	   int count=0;
	   String checkAvilbilty="select count(*) from ConferenceRoom_Details where '"+reqFromDate+"' between Req_From_Date and Req_To_Date";
	   ResultSet rsChekFromDt=ad.selectQuery(checkAvilbilty);
	   try{
		   while(rsChekFromDt.next()){
		   count=rsChekFromDt.getInt(1);
		   }
		   checkAvilbilty="select count(*) from ConferenceRoom_Details where '"+reqToDate+"' between Req_From_Date and Req_To_Date"; 
		   ResultSet rsChekToDt=ad.selectQuery(checkAvilbilty);
		   while(rsChekToDt.next()){
			   count=count+rsChekToDt.getInt(1);
			   }
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   if(count>0){
		   conForm.setStatus("Booked");
		   request.setAttribute("status", "Reserved");
	   }else{
		   conForm.setStatus("Available");
		   request.setAttribute("status", "Available");
	   }
	   
		return mapping.findForward("availabletyStatus");
	}
	public ActionForward submitDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm conForm=(ConferenceForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		userDetails(mapping, form, request, response);
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		conForm.setLocationIdList(locationList);
		conForm.setLocationLabelList(locationLabelList);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
	    String dateNow = ft.format(dNow);
	    
	    String reqFromDate=conForm.getFromDate();
	    String a1[]=reqFromDate.split("/");
	    reqFromDate=a1[2]+"-"+a1[1]+"-"+a1[0];
	    String fromtime=conForm.getFromTime();
	    String pm="PM";
	    if(fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1 ) {
			   fromtime=fromtime.replaceAll("PM","");
			   String a[]=fromtime.split(":");
			   if(Integer.parseInt(a[0])!=12){
		       int hr=Integer.parseInt(a[0])+12;
		       reqFromDate=reqFromDate+" "+hr+":"+a[1];
			   }else{
				   fromtime=fromtime.replaceAll("PM","");
					reqFromDate=reqFromDate+" "+fromtime;
			   }
			}else{
				fromtime=fromtime.replaceAll("AM","");
				reqFromDate=reqFromDate+" "+fromtime;
			}
		   
		   String reqToDate=request.getParameter("fromDate");
		    String b1[]=reqToDate.split("/");
		    reqToDate=b1[2]+"-"+b1[1]+"-"+b1[0];
		    String totime=request.getParameter("toTime");
		   if(totime.toLowerCase().indexOf(pm.toLowerCase()) != -1 ) {
			   totime=totime.replaceAll("PM","");
			   String a[]=totime.split(":");
			   if(Integer.parseInt(a[0])!=12){
		       int hr=Integer.parseInt(a[0])+12;
		       reqToDate=reqToDate+" "+hr+":"+a[1];
			   }else{
				   totime=totime.replaceAll("PM","");
					reqToDate=reqToDate+" "+totime;
			   }
			}else{
				totime=totime.replaceAll("AM","");
				reqToDate=reqToDate+" "+totime;
			}
	   String fromDt=EMicroUtils.dateConvert(conForm.getFromDate());
	   String toDt=EMicroUtils.dateConvert(conForm.getToDate());
	   int reqNo=0;
	   
	   int count=0;
	   String checkAvilbilty="select count(*) from ConferenceRoom_Details conf where (( Req_From_Date between '"+reqFromDate+"'  and '"+reqToDate+"') "
	   + "or ( Req_To_Date  between '"+reqFromDate+"'  and '"+reqToDate+"')) and  conf.Loc_Id='"+conForm.getLocationId()+"' and conf.Floor='"+conForm.getFloor()+"' "
	   + "and conf.Conf_Room='"+conForm.getRoomName()+"' and (approval_status='Pending' or approval_status='Reserved' or approval_status='Completed')";
	   ResultSet rsChekFromDt=ad.selectQuery(checkAvilbilty);
	   try{
		   while(rsChekFromDt.next()){
		   count=rsChekFromDt.getInt(1);
		   }
		 
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   if(count==0){
	   synchronized(this){

		   String getMaxOfReqNo="select max(Reqest_No) from ConferenceRoom_Details";
	   ResultSet rsMax=ad.selectQuery(getMaxOfReqNo);
	   try{
		   while(rsMax.next()){
			   reqNo=rsMax.getInt(1)+1;
		   }
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   String approverID="";
	   String pendingApprs="";
	   String getApprovers="select appr.Approver_Id,emp.EMP_FULLNAME,appr.Priority from ConferanceRoom_Approvers appr,emp_official_info emp "
	   		+ " where appr.LocID='"+conForm.getLocationId()+"' and appr.Floor='"+conForm.getFloor()+"' and appr.Room='"+conForm.getRoomName()+"' "
	   				+ "and emp.PERNR=appr.Approver_Id order by Priority";
	   ResultSet rsAppr=ad.selectQuery(getApprovers);
	   try{
	   while(rsAppr.next()){
		   if(rsAppr.getInt("Priority")==1)
		   approverID=rsAppr.getString("Approver_Id");
		   pendingApprs=pendingApprs+rsAppr.getString("EMP_FULLNAME")+" , ";
	   }
	   if(!(pendingApprs.equalsIgnoreCase(""))){
 			int size=pendingApprs.length();
 			pendingApprs=pendingApprs.substring(0, (size-2));
 		}
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   String saveData="insert into ConferenceRoom_Details(Reqest_No,Requster_Id,Loc_Id,Floor,Conf_Room,From_Date,From_Time,To_Date,To_Time,Purpose,Req_From_Date,"
	   + "Req_To_Date,Email_ID,Ext_No,IP_Phone,IPAddress,Submit_Date,Last_approver,Pending_approver,approved_date,approval_status) "
	   + "values('"+reqNo+"','"+user.getEmployeeNo()+"','"+conForm.getLocationId()+"','"+conForm.getFloor()+"','"+conForm.getRoomName()+"','"+fromDt+"','"+conForm.getFromTime()+"',"
	   + "'"+toDt+"','"+conForm.getToTime()+"','"+conForm.getPurpose()+"','"+reqFromDate+"','"+reqToDate+"','"+user.getMail_id()+"',"
	   + "'"+conForm.getExtno()+"','"+conForm.getIpPhoneno()+"','"+conForm.getIPNumber()+"','"+dateNow+"','','"+pendingApprs+"','','Pending')";
	   int i=ad.SqlExecuteUpdate(saveData);
	   if(i>0){
		   //send Request to Approvers
			  ft = new SimpleDateFormat ("dd/MM/yyyy");
		     dateNow = ft.format(dNow);
		  String saveAllReq="insert into All_Request(Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,"
		  + "Approved_Persons,Requester_Id,Comments,type) values ('"+reqNo+"','Conference','"+user.getEmployeeNo()+"','"+dateNow+"','Pending','',"
		  + "'"+approverID+"','','"+user.getEmployeeNo()+"','','Conference')";
		  int j=ad.SqlExecuteUpdate(saveAllReq);
		  conForm.setMessage("Request has been sent to approver.");
		  ConferenceMail mail=new ConferenceMail();
		  mail.sendMailToApprover(request, reqNo, user.getEmployeeNo(), approverID,"Pending");
	   }
	   }
	}else{
		conForm.setMessage2("Selected Date Conference Room Already Reserved.");
		 String getBookedList="select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
	   	+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from ConferenceRoom_Details conf,emp_official_info emp,Location loc where (( Req_From_Date between '"+reqFromDate+"'  and '"+reqToDate+"') "
				   + "or ( Req_To_Date  between '"+reqFromDate+"'  and '"+reqToDate+"')) and  conf.Loc_Id='"+conForm.getLocationId()+"' and conf.Floor='"+conForm.getFloor()+"' "
				   + "and conf.Conf_Room='"+conForm.getRoomName()+"' and (approval_status='Pending' or approval_status='Reserved' or approval_status='Completed') and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID";
		 List resarvedList=new LinkedList();
	 	  /* String checkAvilbilty="select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
	   	+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from ConferenceRoom_Details conf,"
	   	+ "emp_official_info emp,Location loc where conf.From_Date>='"+dateNow+"' and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID";
	   ResultSet rs=ad.selectQuery(checkAvilbilty);*/
		 ResultSet rs=ad.selectQuery(getBookedList);
	   try{
		   while(rs.next()){
		   ConferenceForm c=new ConferenceForm();
			   c.setReqNo(rs.getString("Reqest_No"));
			   c.setLocation(rs.getString("LOCATION_CODE"));
			   c.setEmpName(rs.getString("EMP_FULLNAME"));
			   c.setFloor(rs.getString("Floor"));
			   c.setRoomName(rs.getString("Conf_Room"));
			   c.setFromDate(rs.getString("From_Date")+" "+rs.getString("From_Time"));
			   c.setToDate(rs.getString("To_Date")+" "+rs.getString("To_Time"));
			   c.setApprovalStatus(rs.getString("approval_status"));
		   resarvedList.add(c);
		   
		   }
		   }catch(Exception e){
		   e.printStackTrace();
	   }
	   if(resarvedList.size()>0){
		   request.setAttribute("resarvedList", resarvedList);
	   }
	}
	   
		
	
	                            
	    
	    return mapping.findForward("bookRoom");
	}
	
	public ActionForward bookRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm conForm=(ConferenceForm)form;
		EssDao ad=new EssDao();
		userDetails(mapping, form, request, response);
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		conForm.setLocationIdList(locationList);
		conForm.setLocationLabelList(locationLabelList);
		}catch(Exception e){
			e.printStackTrace();
		}
		conForm.setLocationId("");
		conForm.setFloor("");
		conForm.setRoomName("");
		return mapping.findForward("bookRoom");
	}
	public ActionForward userDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm help = (ConferenceForm) form;
		EssDao ad=new EssDao();
		String ipaddress="";
		String username="";
		ipaddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}
		try{
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE,loc.LOCID " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+user.getEmployeeNo()+"' and " +
					"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
			ResultSet rs= ad.selectQuery(data);
			
				while(rs.next())
				{
					help.setRequestername(rs.getString("EMP_FULLNAME"));
					help.setRequesterdepartment(rs.getString("DPTSTXT"));
					help.setRequesterdesignation(rs.getString("DSGSTXT"));
					help.setExtno(rs.getString("TEL_EXTENS"));
					help.setIpPhoneno(rs.getString("IP_PHONE"));
					help.setLocation(rs.getString("LOCATION_CODE"));
					help.setLocNo(rs.getString("LOCID"));
					help.setHostname(username);
					help.setIPNumber(ipaddress);
					help.setEmpno(rs.getString("PERNR"));
				    help.setEmpEmailID(rs.getString("EMAIL_ID"));
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward("newincidentform");
	}
	public ActionForward displayList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm conForm=(ConferenceForm)form;
		EssDao ad=new EssDao();
		
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		
		conForm.setLocationIdList(locationList);
		conForm.setLocationLabelList(locationLabelList);
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	    String dateNow = ft.format(dNow);
		List resarvedList=new LinkedList();
		 	   String checkAvilbilty="select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
		   	+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from ConferenceRoom_Details conf,"
		   	+ "emp_official_info emp,Location loc where conf.From_Date>='"+dateNow+"' and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID";
		   ResultSet rs=ad.selectQuery(checkAvilbilty);
		   try{
			   while(rs.next()){
			   ConferenceForm c=new ConferenceForm();
				   c.setReqNo(rs.getString("Reqest_No"));
				   c.setLocation(rs.getString("LOCATION_CODE"));
				   c.setEmpName(rs.getString("EMP_FULLNAME"));
				   c.setFloor(rs.getString("Floor"));
				   c.setRoomName(rs.getString("Conf_Room"));
				   c.setFromDate(rs.getString("From_Date")+" "+rs.getString("From_Time"));
				   c.setToDate(rs.getString("To_Date")+" "+rs.getString("To_Time"));
				   c.setApprovalStatus(rs.getString("approval_status"));
			   resarvedList.add(c);
			   
			   }
			   }catch(Exception e){
			   e.printStackTrace();
		   }
		   if(resarvedList.size()>0){
			   request.setAttribute("resarvedList", resarvedList);
		   }else{
			   request.setAttribute("NoRecords", "NoRecords");
		   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conForm.setLocationId("");
		conForm.setFloor("");
		conForm.setRoomName("");
		
		return mapping.findForward("confBookedList");
	}
	
	public ActionForward searchRoomsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm conForm=(ConferenceForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String locID=conForm.getLocationId();
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
			String getConfList="select loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Room_Name  from ConferanceRoom_List conf,Location loc where "
					+ "conf.LocId=loc.LOCID and Status='yes' and conf.LocId='"+locID+"'";
			ResultSet rsConfList=ad.selectQuery(getConfList);
			List confList=new LinkedList();
			while(rsConfList.next()){
				ConferenceForm rooms=new ConferenceForm();
				rooms.setLocationId(rsConfList.getString("LOCATION_CODE")+"-"+rsConfList.getString("LOCNAME"));
				rooms.setFloor(rsConfList.getString("Floor"));
				rooms.setRoomName(rsConfList.getString("Room_Name"));
				
				confList.add(rooms);
			}
			request.setAttribute("confList", confList);	
			if(confList.size()==0)
				conForm.setMessage2("No Records Found");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return mapping.findForward("roomsList");
	}
	
	public ActionForward roomsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ConferenceForm conForm=(ConferenceForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
			conForm.setLocationId(user.getPlantId());
			String getConfList="select loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Room_Name  from ConferanceRoom_List conf,Location loc where "
					+ "conf.LocId=loc.LOCID and Status='yes'";
			ResultSet rsConfList=ad.selectQuery(getConfList);
			List confList=new LinkedList();
			while(rsConfList.next()){
				ConferenceForm rooms=new ConferenceForm();
				rooms.setLocationId(rsConfList.getString("LOCATION_CODE")+"-"+rsConfList.getString("LOCNAME"));
				rooms.setFloor(rsConfList.getString("Floor"));
				rooms.setRoomName(rsConfList.getString("Room_Name"));
				
				confList.add(rooms);
			}
			request.setAttribute("confList", confList);	
			if(confList.size()==0)
				conForm.setMessage2("No Records Found");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return mapping.findForward("roomsList");
	}
	
}
