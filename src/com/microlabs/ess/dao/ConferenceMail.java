package com.microlabs.ess.dao;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.microlabs.ess.form.ConferenceForm;
import com.microlabs.it.Dao.ITMail;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class ConferenceMail {

	public void sendMailToRequester(HttpServletRequest request, int Req_Id,String reqester,String approver,String status)
	 {
		HttpSession session=request.getSession();
		MailInboxForm mailForm = new MailInboxForm();
		ConferenceMail conferenceMail=new ConferenceMail();
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ConferenceForm c=new ConferenceForm();
		String senderMail="";
		String getRequestDetails="select conf.Requster_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,conf.Ext_No,conf.Email_ID,conf.IP_Phone,"
				+ "conf.IPAddress,loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,"
				+ "conf.Purpose,conf.approval_status,conf.approved_date from ConferenceRoom_Details conf,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where "
				+ "conf.Reqest_No='"+Req_Id+"' and conf.Requster_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
				ResultSet rs=ad.selectQuery(getRequestDetails);
				try{
				while(rs.next()){
					
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
					
				}
				
						senderMail=user.getMail_id();
					 
					mailForm.setSendermail(user.getMail_id());
					mailForm.setEmpname(user.getFullName());
					mailForm.setToAddress(c.getEmpEmailID());
					System.out.println("m -> "+senderMail);
					
					mailForm.setccAddress("");
					mailForm.setbccAddress("");
					String desc = "";
					mailForm.setSubject("Conference Room Request Status From "+user.getFullName());
					desc=desc+"";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
			             +"TABLE {border-collapse:collapse;border:1px solid black;}"
			             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
			             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
					 +"</STYLE>";
					desc=desc+"<html><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=7 ><center>Conference Room Booking Details</center></th></tr>";
					desc=desc+"<tr><th align=left>Req. No</th><th align=left>Location</th><th align=left>Floor</th><th align=left>Conf.Room</th><th align=left>From Date</th><th align=left>To Date</th><th align=left>Status</th>";
					desc=desc+"<tr><td>"+Req_Id+"</td><td>"+c.getLocation()+"</td><td>"+c.getFloor()+"</td><td>"+c.getRoomName()+"</td><td>"+c.getFromDate()+"  "+c.getFromTime()+"</td><td>"+c.getToDate()+"  "+c.getToTime()+"</td><td><b>"+status+"</b></td></tr></table><br><br>";
					desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
				    desc=desc+"</body></html>";
				    mailForm.setDescription(desc);
				    ITMail it=new ITMail();
				    it.sendMail(request, mailForm,"request");
	 }catch(Exception e){
		 e.printStackTrace();
	 }
}
	public void sendMailToApprover(HttpServletRequest request, int Req_Id,String reqester,String approver,String status)
	 {
		HttpSession session=request.getSession();
		MailInboxForm mailForm = new MailInboxForm();
		ConferenceMail conferenceMail=new ConferenceMail();
		EssDao ad=new EssDao();
		ConferenceForm c=new ConferenceForm();
		String senderMail="";
		String getRequestDetails="select conf.Requster_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,conf.Ext_No,conf.Email_ID,conf.IP_Phone,"
				+ "conf.IPAddress,loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,"
				+ "conf.Purpose,conf.approval_status,conf.approved_date from ConferenceRoom_Details conf,emp_official_info emp,DEPARTMENT dept,DESIGNATION desg ,Location loc where "
				+ "conf.Reqest_No='"+Req_Id+"' and conf.Requster_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID and loc.LOCID=conf.Loc_Id ";
				ResultSet rs=ad.selectQuery(getRequestDetails);
				try{
				while(rs.next()){
					
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
					
				}
				 String a1="Select * from emp_official_info where pernr='"+approver+"'";
					ResultSet rs1= ad.selectQuery(a1);
					while(rs1.next())
					 {
						senderMail=rs1.getString("EMAIL_ID"); 
					 }
					mailForm.setSendermail(senderMail);
					mailForm.setEmpname(c.getEmpName());
					mailForm.setToAddress(senderMail);
					System.out.println("m -> "+senderMail);
					
					mailForm.setccAddress("");
					mailForm.setbccAddress("");
					String desc = "";
					mailForm.setSubject("Conference Room Request From "+c.getEmpName());
					desc=desc+"";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
			             +"TABLE {border-collapse:collapse;border:1px solid black;}"
			             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
			             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
					 +"</STYLE>";
					desc=desc+"<html><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=7 ><center>Conference Room Booking Details</center></th></tr>";
					desc=desc+"<tr><th align=left>Req. No</th><th align=left>Location</th><th align=left>Floor</th><th align=left>Conf.Room</th><th align=left>From Date</th><th align=left>To Date</th><th align=left>Status</th>";
					desc=desc+"<tr><td>"+Req_Id+"</td><td>"+c.getLocation()+"</td><td>"+c.getFloor()+"</td><td>"+c.getRoomName()+"</td><td>"+c.getFromDate()+"  "+c.getFromTime()+"</td><td>"+c.getToDate()+"  "+c.getToTime()+"</td><td><b>"+status+"</b></td></tr></table><br><br>";
					desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
				    desc=desc+"</body></html>";
				    mailForm.setDescription(desc);
				    ITMail it=new ITMail();
				    it.sendMail(request, mailForm,"request");
	 }catch(Exception e){
		 e.printStackTrace();
	 }
}
}
