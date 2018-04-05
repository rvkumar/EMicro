package com.microlabs.it.Dao;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.DispatchAction;

import com.microlabs.it.form.HelpDeskForm;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;

import java.io.IOException;  
import java.net.InetAddress; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

public class ITMail extends DispatchAction {
	private String password; 
	
	
	public int sendMailToSapApprover(HttpServletRequest request, int Req_Id, String type,String approver)
	 {HttpSession session=request.getSession();
		MailInboxForm mailForm = new MailInboxForm();
        ITMail it=new ITMail();
			UserInfo user=(UserInfo)session.getAttribute("user");
		 HelpDeskForm help=new HelpDeskForm();
		 NewsandMediaDao ad=new NewsandMediaDao();
		 LinkedList apprList=new LinkedList();
		 try {
		 if(type.equals("IT Sap Request")){
			 String reqDate="";
			 String empNo="";
			 String category="";
			 String subCategory="";
			 String description="";
			 String empName="";
			 String senderMail="";
			 String approvermail="";
			 String priority="";
			 String status="";
			 String reason="";
			 String techassign ="";
			 String lastApprover ="";
			 String comments ="";
			 String location ="";
			 String reqDetails="select Req_No,Req_date,raisedBy,Req_type,Sub_Category,Subject,Req_Priority,Reason,AssignTo, "
			 	+" Last_approver, Pending_approver,Req_Status,Location from IT_SAP_ISSUES where Req_No='"+Req_Id+"'";
			 ResultSet rs=ad.selectQuery(reqDetails);
			 while(rs.next()){
				  	reqDate=rs.getString("Req_date");
					String a[]=reqDate.split(" "); 
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					empNo=rs.getString("raisedBy");
					category=rs.getString("Req_type");
					subCategory=rs.getString("Sub_Category");
					priority=rs.getString("Req_Priority");
					status=rs.getString("Req_Status");
					description=rs.getString("Subject");
					reason = rs.getString("reason");
					techassign=rs.getString("AssignTo");
					lastApprover=rs.getString("Last_approver");
					location = rs.getString("Location");
			 }
			 //ad.connClose();
				String a1="Select * from emp_official_info where pernr='"+empNo+"'";
				ResultSet rs1= ad.selectQuery(a1);
				while(rs1.next())
				 {
					empName=rs1.getString("EMP_FULLNAME");
					senderMail=rs1.getString("EMAIL_ID"); 
				 }
				mailForm.setEmpname(empName);
				mailForm.setSendermail(senderMail);
				
				
				
				//Approver
				String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+approver+"'";
				ResultSet approverRS=ad.selectQuery(getAppEmailID);
				while(approverRS.next()){
					approvermail = approverRS.getString(1);
				}
				
				
				
				//comments
				
				String a2="Select Comments from All_request where Req_Id='"+Req_Id+"' and Last_approver = '"+user.getEmployeeNo()+"'";
				ResultSet rs2= ad.selectQuery(a2);
				while(rs2.next())
				 {
					comments=rs2.getString("Comments");
					
				 }
				if(comments.equalsIgnoreCase(""))
				{
				  comments = "-";	
				}
				 
				
				mailForm.setToAddress(approvermail);
				System.out.println("m -> "+approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				String desc = "";
				if(status.equalsIgnoreCase("Completed")) {
				
				 //mailForm.setSubject( subCategory + " Request No . '" +Req_Id +"' Completed By "+lastApprover);
				   mailForm.setSubject(  "SAP Issue : "+subCategory+" ,  Request No . " +Req_Id +" Completed By "+lastApprover);	
				}
				
				if(status.equalsIgnoreCase("Pending")) 
				{
					mailForm.setSubject(  "SAP Issue : "+subCategory+" ,  Request No . " +Req_Id +" Created By "+empName);	
				}
				
				if(status.equalsIgnoreCase("In Process")) 
				{
					mailForm.setSubject(  " SAP Issue : "+subCategory+" , Request No . " +Req_Id +" Approved By "+lastApprover);	
				}
				
				if(status.equalsIgnoreCase("Rejected")) 
				{
					mailForm.setSubject(  "SAP Issue : "+subCategory+" , Request No . " +Req_Id +" Rejected By "+lastApprover);	
				}
				
				
				
				//arun 
				String getReqestNumber="select MAX(Priority) as priority"
				    	+" from Material_Approvers app,"
					+"emp_official_info as emp"
				+" where Material_Type='"+category+"' and " 
				+"Material_Sub_Category='"+subCategory+"' and "    
				+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+location+"')";
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
		
		int hodreq =0;
		String catfullname ="";
		
		String chkreq=" select c_hod_req ,c_cat_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and c_cat_sh_name = '"+category+"' and c_sub_cat_name ='"+subCategory+"'";
		 ResultSet chk= ad.selectQuery(chkreq);
		 try {
			while(chk.next())
			 {
				 hodreq=chk.getInt("c_hod_req");
				 catfullname =chk.getString("c_cat_name"); 
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		 
		 String Sqlstring ="";
		      if(hodreq == 1)
		      { 
				 Sqlstring ="select 0 as priority ,"  
						+"isnull(All_Request.Actual_Approver ,PERNR ) as employeeCode,"
						+"isnull((select EMP_FULLNAME from emp_official_info e where e.PERNR = All_Request.Actual_Approver) , EMP_FULLNAME) as employeeName ,"
						+"DEPARTMENT.DPTLTXT as department,"
			        +"DESIGNATION.DSGLTXT as designation,"
			        +"All_Request.Req_Status ,"
			        +"All_Request.Comments ,"
			        +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
			        +" from  DEPARTMENT,DESIGNATION ,ESS_Approvers ,emp_official_info "
					  +"left outer join All_Request on Req_type ='IT Sap Request' and "
			        +"Req_Id = '"+Req_Id+"' and "
			        +"emp_official_info.PERNR = All_Request.Last_Approver"
			        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
			        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
			        +"emp_official_info.PERNR =  ESS_Approvers.ApproverId and "
			       +"ESS_Approvers.employeeNumber = '"+empNo+"'"
			       +" union " 
			      +"select app.priority as priority,"
			      +"isnull(All_Request.Actual_Approver ,PERNR ) as employeeCode,"
					+"isnull((select EMP_FULLNAME from emp_official_info e where e.PERNR = All_Request.Actual_Approver) , EMP_FULLNAME) as employeeName ,"
			        +"DEPARTMENT.DPTLTXT as department,"
					+"DESIGNATION.DSGLTXT as designation ,"
					+"All_Request.Req_Status ,"
			        +"All_Request.Comments ,"
			        +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
					+" from Material_Approvers app,"
					+" DEPARTMENT,DESIGNATION ,emp_official_info as emp  left outer join All_Request on Req_type ='IT Sap Request' and "
					 +"Req_Id = '"+Req_Id+"' and "
			       +"emp.PERNR = All_Request.Last_Approver "
						+" where Material_Type='"+category+"' and " 
					+"Material_Sub_Category='"+subCategory+"' and "  
					+"emp.PERNR=app.Approver_Id and "					 
					+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+location+"') and role ='USER' and "
					+"emp.DPTID  = DEPARTMENT.DPTID and "
					+"emp.DSGID  = DESIGNATION.DSGID" 
					+" Union "
					+"select '"+maxPriority+"' as priority ," 
					  +"isnull(All_Request.Actual_Approver ,PERNR ) as employeeCode,"
						 +"isnull((select EMP_FULLNAME from emp_official_info e where e.PERNR = All_Request.Actual_Approver) , EMP_FULLNAME) as employeeName ,"
					  +"DEPARTMENT.DPTLTXT as department,"
			        +"DESIGNATION.DSGLTXT as designation ,"
			        +"All_Request.Req_Status ,"
			        +"All_Request.Comments ,"
			        +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
			        +" from DEPARTMENT,DESIGNATION ,emp_official_info "
					 +"Left Outer Join  All_Request on Req_type ='IT Sap Request' and" 
			                 +" Req_Id = '"+Req_Id+"' and" 
			                 +" convert(varchar(10),emp_official_info.PERNR) = All_Request.Last_Approver "  

			       +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
			       +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
			       +"emp_official_info.PERNR = '"+techassign+"'" 
					+" Order by 1";
		      }
		      else
		      {
		    	  Sqlstring ="select app.priority as priority,"
				      +"isnull(All_Request.Actual_Approver ,PERNR ) as employeeCode,"
						+"isnull((select EMP_FULLNAME from emp_official_info e where e.PERNR = All_Request.Actual_Approver) , EMP_FULLNAME) as employeeName ,"
				        +"DEPARTMENT.DPTLTXT as department,"
						+"DESIGNATION.DSGLTXT as designation ,"
						+"All_Request.Req_Status ,"
				        +"All_Request.Comments ,"
				        +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
						+" from Material_Approvers app,"
						+" DEPARTMENT,DESIGNATION ,emp_official_info as emp  left outer join All_Request on Req_type ='IT Sap Request' and "
						 +"Req_Id = '"+Req_Id+"' and "
				       +"emp.PERNR = All_Request.Last_Approver "
							+" where Material_Type='"+category+"' and " 
						+"Material_Sub_Category='"+subCategory+"' and "  
						+"emp.PERNR=app.Approver_Id and "					 
						+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+location+"') and role ='USER' and "
						+"emp.DPTID  = DEPARTMENT.DPTID and "
						+"emp.DSGID  = DESIGNATION.DSGID" 
						+" Union "
						+"select '"+maxPriority+"' as priority ," 
						  +"isnull(All_Request.Actual_Approver ,PERNR ) as employeeCode,"
							 +"isnull((select EMP_FULLNAME from emp_official_info e where e.PERNR = All_Request.Actual_Approver) , EMP_FULLNAME) as employeeName ,"
						  +"DEPARTMENT.DPTLTXT as department,"
				        +"DESIGNATION.DSGLTXT as designation ,"
				        +"All_Request.Req_Status ,"
				        +"All_Request.Comments ,"
				        +"ISNULL(All_Request.approved_date,All_Request.rejected_date) as date "
				        +" from DEPARTMENT,DESIGNATION ,emp_official_info "
						 +"Left Outer Join  All_Request on Req_type ='IT Sap Request' and" 
				                 +" Req_Id = '"+Req_Id+"' and" 
				                 +" convert(varchar(10),emp_official_info.PERNR) = All_Request.Last_Approver "  

				       +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
				       +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
				       +"emp_official_info.PERNR = '"+techassign+"'" 
						+" Order by 1";

		      }
			   ResultSet rs9=ad.selectQuery(Sqlstring);
			   int i =0;
			   String presentApp= "";
			   String actualapp ="";
				
				try{
					while(rs9.next()){
						//IssuesForm form1=new IssuesForm();
						ApprovalsForm form1 = new ApprovalsForm();
						i = i+1;
						
						presentApp = rs9.getString("employeeCode");
						
								
								
									form1.setPriority(Integer.toString(i));
									//pendAppForm.setEmployeeCode(rs.getString("employeeCode"));
									
									form1.setEmployeeCode(rs9.getString("employeeCode"));
									
									form1.setEmployeeName(rs9.getString("employeeName"));
									
									//form1.setEmployeename(rs.getString("employeeName"));
									//pendAppForm.setDepartment(rs.getString("department"));
									form1.setDepartment(rs9.getString("department"));
									//form1.setDesignation(rs.getString("designation"));
									form1.setDesignation(rs9.getString("designation"));
									form1.setStatus(rs9.getString("req_status"));
									form1.setComments(rs9.getString("Comments"));
									form1.setDate(rs9.getString("date"));
									
								
							
						
						//form1.setPriority(rs.getInt("priority"));
						
						
						apprList.add(form1);
						
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				//---
				
				
				desc=desc+"";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
			             +"TABLE {border-collapse:collapse;border:1px solid black;}"
			             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
			             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
					 +"</STYLE>";
				desc=desc+"<html ><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50 />";
				desc=desc+ "<table style= width:70% ><tr><th colspan=6 ><center> SAP Issues </center></th></tr>";
				desc=desc+"<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Category</th><th align=left>Sub Category</th><th align=left>Priority</th><th align=left>Status</th></br>";
				desc=desc+"<tr><td width=10%>"+Req_Id+"</td><td width=20%>"+reqDate+"</td><td width=25%>"+catfullname+"</td><td width=30%>"+subCategory+"</td><td width=10%>"+priority+"</td><td width=30%>"+status+"</td></tr></br>";
				desc=desc+"<tr><th colspan=6>Description</th></tr>";
				desc=desc+"<tr><td colspan=6 width=10% >"+description+"</td></tr><br><br>";
				desc=desc+"<tr><th colspan=6>Reason</th></tr>";
				desc=desc+"<tr><td colspan=6 width=10% >"+reason+"</td></tr><br><br>";
				desc=desc+"<tr><th colspan=6 >Comments</th></tr>";
				desc=desc+"<tr><td colspan=6 width=10% >"+comments+"</td></tr></table><br><br>";
				
		
				desc=desc+"<table style= width:70%>";
				desc=desc+"<tr><th colspan="+6+">Approvers Details</th></tr>";
				desc=desc+"<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th></tr>";
				
				
				String priority1 = "";
				String empno1 = "";  
				String empname1 ="";
				String status1 ="";
				String comments1 ="";
				String date1 = ""; 
				Iterator I = apprList.iterator();
				
					while(I.hasNext())
					{
						ApprovalsForm Help1 = (ApprovalsForm) I.next();
						priority1 = Help1.getPriority();
						empno1 = Help1.getEmployeeCode();
						empname1 = Help1.getEmployeeName();
						status1 =Help1.getStatus();
						if(status1==null)
						{
							status1="";	
						}
						comments1 = Help1.getComments();
						
						if(comments1==null)
						{	
							comments1="";
						}
						date1 = Help1.getDate();
						if(date1==null)
						{	
							date1="";
						}
						
					desc=desc+"<tr>";
					desc=desc+"<td width=10%>"+priority1+"</td><td width=20%>"+empno1+"</td><td width=30%>"+empname1+"</td><td width=20%>"+status1+"</td><td width=10%>"+date1+"</td></tr>";
				
					}
				
			  
				desc=desc+"</table>";
				desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This is an Auto generated Email Please donot Reply .</span> ";
				desc=desc+"</body></html>";
			    mailForm.setDescription(desc);
				
		 }
		 it.sendMail(request, mailForm,"request");
		 }catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		 return 1;
		 }
	
	 public int sendMailToRequester(HttpServletRequest request, int Req_Id, String type,String reqester,String comments)
	 {
		 HttpSession session=request.getSession();
			MailInboxForm mailForm = new MailInboxForm();
        ITMail it=new ITMail();
			UserInfo user=(UserInfo)session.getAttribute("user");
		 HelpDeskForm help=new HelpDeskForm();
		 NewsandMediaDao ad=new NewsandMediaDao();
		 try {
			 if(type.equals("Issues")){
				 String reqDate="";
				 String empNo="";
				 String techNo="";
				 String category="";
				 String subCategory="";
				 String description="";
				 String reqPriority="";
				 String empName="";
				 String senderMail="";
				 String approvermail="";
				 String priority="";
				 String status="";
				 
				 
				 
				 
				 String reqDetails="select Req_No,Req_date,Emp_No,Req_type,Sub_Category,Subject,Req_Priority,"
						 	+ "Pending_approver,Req_Status from IT_ISSUES where Req_No='"+Req_Id+"'";
						 ResultSet rs=ad.selectQuery(reqDetails);
						 while(rs.next()){
							  	reqDate=rs.getString("Req_date");
								String a[]=reqDate.split(" "); 
								reqDate=a[0];
								String b[]=reqDate.split("-");
								reqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								empNo=rs.getString("Emp_No");
								category=rs.getString("Req_type");
								subCategory=rs.getString("Sub_Category");
								//techNo=rs.getString("Pending_approver");
								priority=rs.getString("Req_Priority");
								status=rs.getString("Req_Status");
								description=rs.getString("Subject");
						 }
						 ad.connClose();
			 String a1="Select * from emp_official_info where pernr='"+empNo+"'";
				ResultSet rs1= ad.selectQuery(a1);
				while(rs1.next())
				 {
					senderMail=rs1.getString("EMAIL_ID"); 
				 }
				rs1.close();
				ad.connClose();
				
				mailForm.setSendermail(senderMail);
				
				//Approver
				String getAppEmailID="select EMAIL_ID,EMP_FULLNAME from emp_official_info where PERNR='"+reqester+"'";
				ResultSet approverRS=ad.selectQuery(getAppEmailID);
				while(approverRS.next()){
					approvermail = approverRS.getString("EMAIL_ID");
					empName=approverRS.getString("EMP_FULLNAME");
				}
				mailForm.setEmpname(empName);
				approverRS.close();
				ad.connClose();
				mailForm.setToAddress(senderMail);
				System.out.println("m -> "+senderMail);
				
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				//constructing mail description for each request
				String desc = "";
				mailForm.setSubject("IT Issue Request Status From "+empName);
				desc=desc+"";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
				desc=desc+"<html><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=7 ><center>IT Issue Details</center></th></tr>";
				desc=desc+"<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Category</th><th align=left>Sub Category</th><th align=left>Description</th><th align=left>Priority</th><th align=left>Status</th>";
				if(comments==null)
				{
					comments="";
				}
				if(!comments.equalsIgnoreCase(""))
				desc=desc+"<tr><td>"+Req_Id+"</td><td>"+reqDate+"</td><td>"+category+"</td><td>"+subCategory+"</td><td>"+description+"</td><td>"+priority+"</td><td>"+status+"</td></tr><tr><th colspan=10>Comments</th></tr><tr><td colspan=10>"+comments+"</td></tr></table><br><br>";
				else
				{
					desc=desc+"<tr><td>"+Req_Id+"</td><td>"+reqDate+"</td><td>"+category+"</td><td>"+subCategory+"</td><td>"+description+"</td><td>"+priority+"</td><td>"+status+"</td></tr></table><br><br>";
				}
				desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
			    desc=desc+"</body></html>";
			
			    mailForm.setDescription(desc);
			 }
			 
			 if(!type.equals("Issues")){
				 

				 String a="Select * from IT_HELPDESK where Req_No='"+Req_Id+"'";
					ResultSet rs= ad.selectQuery(a);
				
					 
					 String location="";
					 String reqstrname="";
					 String reqstNo="";
					 String reqdate="";
					 String approvermail="";
					 String senderMail="";
					 String priority="";
					 String itengstatus="";
					 String apprStatus="";
					 
					while(rs.next())
					 {
						
						reqdate=rs.getString("Req_date");
						reqstNo=rs.getString("Emp_No");
						
						priority=rs.getString("Req_Priority");
						itengstatus=rs.getString("ITEng_Status");
						apprStatus=rs.getString("Req_Status");
					 }
					
					
					//requester 
					String empName="";
					
					 String a1="Select * from emp_official_info where pernr='"+reqstNo+"'";
						ResultSet rs1= ad.selectQuery(a1);
						while(rs1.next())
						 {
							senderMail=rs1.getString("EMAIL_ID"); 
						 }
						rs1.close();
						ad.connClose();
						
						mailForm.setSendermail(senderMail);
						
						//Approver
						String getAppEmailID="select EMAIL_ID,EMP_FULLNAME from emp_official_info where PERNR='"+reqester+"'";
						ResultSet approverRS=ad.selectQuery(getAppEmailID);
						while(approverRS.next()){
							approvermail = approverRS.getString("EMAIL_ID");
							empName=approverRS.getString("EMP_FULLNAME");
						}
						mailForm.setEmpname(empName);
						approverRS.close();
						ad.connClose();
						mailForm.setToAddress(senderMail);
						System.out.println("m -> "+senderMail);
						
						mailForm.setccAddress("");
						mailForm.setbccAddress("");
				
				 
				//constructing mail description for each request
					String desc = "";
					mailForm.setSubject("IT HelpDesk Request Status  From "+empName);
					desc=desc+"";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
			             +"TABLE {border-collapse:collapse;border:1px solid black;}"
			             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
			             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
					 +"</STYLE>";
					if(!apprStatus.equalsIgnoreCase("Rejected")){
					desc=desc+"<html><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=5 ><center>IT Request Details</center></th></tr>";
					desc=desc+"<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Request Type</th><th align=left>Priority</th><th align=left>IT Engineer Status</th>";
					desc=desc+"<tr><td>"+Req_Id+"</td><td>"+reqdate+"</td><td>"+type+"</td><td>"+priority+"</td><td>"+itengstatus+"</td></tr></table><br><br>";
					desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
				    desc=desc+"</body></html>";
					}
					else{

						desc=desc+"<html><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=6 ><center>IT Request Details</center></th></tr>";
						desc=desc+"<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Request Type</th><th align=left>Priority</th><th align=left>IT Engineer Status</th><th align=left>Approval Status</th>";
						desc=desc+"<tr><td>"+Req_Id+"</td><td>"+reqdate+"</td><td>"+type+"</td><td>"+priority+"</td><td>"+itengstatus+"</td><td>"+apprStatus+"</td></tr></table><br><br>";
						desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
					    desc=desc+"</body></html>";
						
					}
				
				    mailForm.setDescription(desc);
				 
				 
			 }
			 
			 it.sendMail(request, mailForm,"request");
				
				
		 }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return 1;
	 }
	
	 public int sendMailToApprover(HttpServletRequest request, int Req_Id, String type,String approver,String comments)
	 {
		 HttpSession session=request.getSession();
			MailInboxForm mailForm = new MailInboxForm();
           ITMail it=new ITMail();
			UserInfo user=(UserInfo)session.getAttribute("user");
		 HelpDeskForm help=new HelpDeskForm();
		 NewsandMediaDao ad=new NewsandMediaDao();
		 try {
		 if(type.equals("Issues")){
			 String reqDate="";
			 String empNo="";
			 String category="";
			 String subCategory="";
			 String description="";
			 String empName="";
			 String senderMail="";
			 String approvermail="";
			 String priority="";
			 String status="";
			 String reqDetails="select Req_No,Req_date,Emp_No,Req_type,Sub_Category,Subject,Req_Priority,"
			 	+ "Pending_approver,Req_Status from IT_ISSUES where Req_No='"+Req_Id+"'";
			 ResultSet rs=ad.selectQuery(reqDetails);
			 while(rs.next()){
				  	reqDate=rs.getString("Req_date");
					String a[]=reqDate.split(" "); 
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					empNo=rs.getString("Emp_No");
					category=rs.getString("Req_type");
					subCategory=rs.getString("Sub_Category");
					priority=rs.getString("Req_Priority");
					status=rs.getString("Req_Status");
					description=rs.getString("Subject");
			 }
			 ad.connClose();
				String a1="Select * from emp_official_info where pernr='"+empNo+"'";
				ResultSet rs1= ad.selectQuery(a1);
				while(rs1.next())
				 {
					empName=rs1.getString("EMP_FULLNAME");
					senderMail=rs1.getString("EMAIL_ID"); 
				 }
				mailForm.setEmpname(empName);
				mailForm.setSendermail(senderMail);
				
				//Approver
				String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+approver+"'";
				ResultSet approverRS=ad.selectQuery(getAppEmailID);
				while(approverRS.next()){
					approvermail = approverRS.getString(1);
				}
				mailForm.setToAddress(approvermail);
				System.out.println("m -> "+approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				String desc = "";
				mailForm.setSubject("IT Issue Request From "+empName);
				desc=desc+"";
				desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
		             +"TABLE {border-collapse:collapse;border:1px solid black;}"
		             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
		             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
				 +"</STYLE>";
				desc=desc+"<html><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=7 ><center>IT Issue Details</center></th></tr>";
				desc=desc+"<tr><th align=left>Req. No</th><th align=left>Requested On</th><th align=left>Category</th><th align=left>Sub Category</th><th align=left>Description</th><th align=left>Priority</th><th align=left>Status</th>";
				if(comments==null)
				{
					comments="";
				}
				if(!comments.equalsIgnoreCase(""))
				desc=desc+"<tr><td>"+Req_Id+"</td><td>"+reqDate+"</td><td>"+category+"</td><td>"+subCategory+"</td><td>"+description+"</td><td>"+priority+"</td><td>"+status+"</td></tr><tr><th colspan=10>Comments</th></tr><tr><td colspan=10>"+comments+"</td></tr></table><br><br>";
				else
				{
					desc=desc+"<tr><td>"+Req_Id+"</td><td>"+reqDate+"</td><td>"+category+"</td><td>"+subCategory+"</td><td>"+description+"</td><td>"+priority+"</td><td>"+status+"</td></tr></table><br><br>";
				}
				desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
			    desc=desc+"</body></html>";
			    mailForm.setDescription(desc);
				
		 }
		 if(!type.equals("Issues")){
			 String a="Select Department.dptstxt,Designation.dsgstxt,* from IT_HELPDESK  left outer join department on department.dptid=IT_HELPDESK.Department"
				 		+ " left outer join Designation on Designation.dsgid=IT_HELPDESK.Designation  where Req_No='"+Req_Id+"'";
					ResultSet rs= ad.selectQuery(a);
					 String location="";
					 String reqstrname="";
					 String reqstNo="";
					 String reqdate="";
					 String approvermail="";
					 String senderMail="";
					 String priority="";
					 String itengstatus="";
						String empno="";
					while(rs.next())
					 {
						if(!rs.getString("raisedBy").equalsIgnoreCase(rs.getString("Emp_No")))
						{
							empno=rs.getString("Emp_No");
						}
						else
						{
							empno=user.getEmployeeNo();
						}
						reqdate=rs.getString("Req_date");
						reqstNo=rs.getString("Emp_No");
						
						priority=rs.getString("Req_Priority");
						itengstatus=rs.getString("ITEng_Status");
						
						if(type.equalsIgnoreCase("Email ID Create Request"))
						{
						help.setAdloginname(rs.getString("ADLoginName"));
						help.setSuggestedEmailId(rs.getString("SuggestedEmailId"));
						String x=rs.getString("comm_outside");
						if(x.equalsIgnoreCase("Yes"))
						{
							help.setOutsidecomyes("Yes");
						}
						if(x.equalsIgnoreCase("No"))
						{
							help.setOutsidecomyes("No");
						}
						help.setIsHeHod(rs.getString("Is_He_Hod"));
						help.setIsthisCommonId(rs.getString("Is_This_common_ID"));
						help.setEnablewebAccess(rs.getString("Enable_Web_access"));
						help.setReason(rs.getString("Reason"));
						help.setReqPriority(rs.getString("Req_Priority"));
						help.setHostname(rs.getString("HostName"));
						help.setIPNumber(rs.getString("IPAddress"));
						help.setFirstname(rs.getString("Frst_Name"));
						help.setLastname(rs.getString("Last_Name"));
						help.setMiddname(rs.getString("Middle_Name"));
						help.setDepartment(rs.getString("dptstxt"));
						help.setDesignation(rs.getString("dsgstxt"));
						//newly_Joinied,joining_date,Total_Mailids
						 a=rs.getString("newly_Joinied");
						if(a.equalsIgnoreCase("Yes"))
						{
							help.setNewlyJoinedYes("Yes");
						}
						if(a.equalsIgnoreCase("No"))
						{
							help.setNewlyJoinedYes("No");
						}
						help.setJoiningDate(rs.getString("joining_date"));
						help.setTotalIDs(rs.getString("Total_Mailids"));
						}
						
						if(type.equalsIgnoreCase("Email ID Change Request"))
						{
							
							help.setAdloginname(rs.getString("ADLoginName"));
							help.setFromemailId(rs.getString("From_EmailID"));
							String x=rs.getString("comm_outside");
							if(x.equalsIgnoreCase("Yes"))
							{
								help.setOutsidecomyes("Yes");
							}
							if(x.equalsIgnoreCase("No"))
							{
								help.setOutsidecomyes("No");
							}
							help.setDeleteoldmails(rs.getString("delete_old_email"));
							help.setSuggestedEmailId(rs.getString("SuggestedEmailId"));
							help.setIsHeHod(rs.getString("Is_He_Hod"));
							help.setTransferoldmail(rs.getString("Transfer_Old_Emails"));
							help.setIsthisCommonId(rs.getString("Is_This_common_ID"));
							help.setEnablewebAccess(rs.getString("Enable_Web_access"));
							help.setReason(rs.getString("Reason"));
							help.setReqPriority(rs.getString("Req_Priority"));
							help.setHostname(rs.getString("HostName"));
							help.setIPNumber(rs.getString("IPAddress"));
							 a=rs.getString("newly_Joinied");
							if(a.equalsIgnoreCase("Yes"))
							{
								help.setNewlyJoinedYes("Yes");
							}
							if(a.equalsIgnoreCase("No"))
							{
								help.setNewlyJoinedYes("No");
							}
							help.setJoiningDate(rs.getString("joining_date"));
							help.setTotalIDs(rs.getString("Total_Mailids"));
							
							help.setFirstname(rs.getString("Frst_Name"));
							help.setLastname(rs.getString("Last_Name"));
							help.setMiddname(rs.getString("Middle_Name")); 
						}
						
						if(type.equalsIgnoreCase("Email ID Delete Request"))
						{
							
							help.setAdloginname(rs.getString("ADLoginName"));
							help.setSuggestedEmailId(rs.getString("SuggestedEmailId"));
					        help.setIsHeHod(rs.getString("Is_He_Hod"));
							help.setIsthisCommonId(rs.getString("Is_This_common_ID"));
							help.setEmailidDelete(rs.getString("EmailID_Deleted"));
							help.setDeletefrom(rs.getString("Delete_From"));
						    help.setReason(rs.getString("Reason"));
							help.setReqPriority(rs.getString("Req_Priority"));
							help.setHostname(rs.getString("HostName"));
							help.setIPNumber(rs.getString("IPAddress"));
							help.setMailIDPath(rs.getString("path"));
							help.setDeletedDBYes(rs.getString("deletedDB"));
							help.setForward_MailID(rs.getString("forward_MailID"));
						}
						
						if(type.equalsIgnoreCase("Internet Access Request"))
						{
							help.setAssetvalue(rs.getString("Asset_Details"));
							help.setAdloginname(rs.getString("ADLoginName"));
							help.setReason(rs.getString("Reason"));
							help.setReqPriority(rs.getString("Req_Priority"));
							help.setHostname(rs.getString("HostName"));
							help.setIPNumber(rs.getString("IPAddress"));
							help.setIsHeHod(rs.getString("Is_He_Hod"));
							
						}
						if(type.equalsIgnoreCase("External Drives Access Request"))
						{
							help.setAdloginname(rs.getString("ADLoginName"));
							help.setAssetvalue(rs.getString("Asset_Details"));
							help.setAccesstype(rs.getString("Access_type"));
							help.setReason(rs.getString("Reason"));
							help.setReqPriority(rs.getString("Req_Priority"));
							help.setHostname(rs.getString("HostName"));
							help.setIPNumber(rs.getString("IPAddress"));
						
							String device=rs.getString("Device_type");
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
						}
						
						if(type.equalsIgnoreCase("FTP Access Request"))
						{
							help.setAdloginname(rs.getString("ADLoginName"));
							help.setAssetvalue(rs.getString("Asset_Details"));
							help.setRequiredfoldername(rs.getString("Req_Folder_access"));
							help.setServer(rs.getString("Server_Address"));
							help.setAccesstype(rs.getString("Access_type"));
							help.setFrmdurationofaccess(rs.getString("FrmRequiredDurationofAccess"));
							help.setTodurationofaccess(rs.getString("ToRequiredDurationofAccess"));
							help.setReason(rs.getString("Reason"));
							help.setReqPriority(rs.getString("Req_Priority"));
							help.setHostname(rs.getString("HostName"));
							help.setIPNumber(rs.getString("IPAddress"));
				
							help.setDurationofaccess(rs.getString("RequiredDurationofAccess"));
							String fileusername=rs.getString("UserName");
							 if(fileusername!=null)
								 help.setUsername(fileusername);
							 else
								 help.setUsername("");
							 
							   String filePwd=rs.getString("Password");
							   if(filePwd!=null)
								   help.setPassword(filePwd);
							   else
								   help.setPassword("");
						}
						
						if(type.equalsIgnoreCase("File Server Access Request"))
						{
							help.setAdloginname(rs.getString("ADLoginName"));
							help.setAssetvalue(rs.getString("Asset_Details"));
							help.setRequiredfoldername(rs.getString("Req_Folder_access"));
							help.setServer(rs.getString("Server_Address"));
							help.setAccesstype(rs.getString("Access_type"));
							help.setReason(rs.getString("Reason"));
							help.setReqPriority(rs.getString("Req_Priority"));
							help.setHostname(rs.getString("HostName"));
							help.setIPNumber(rs.getString("IPAddress"));
							help.setFromlocationId(rs.getString("From_Loc"));
							
						}
						if(type.equalsIgnoreCase("New IT Spares Request"))
						{
							help.setAssetvalue(rs.getString("Asset_Details"));
							help.setAdloginname(rs.getString("ADLoginName"));
							help.setServer(rs.getString("Server_Address"));
							help.setReqdfolderacces(rs.getString("Req_Folder_access"));
							help.setReason(rs.getString("Reason"));
							help.setHostname(rs.getString("HostName"));
							help.setIPNumber(rs.getString("IPAddress"));
							help.setReqPriority(rs.getString("Req_Priority"));
							help.setAssetcategory(rs.getString("Category"));
							help.setReqbydate(rs.getString("reqbydate"));
							help.setSuggestmodelname(rs.getString("suggmodel"));
							
							help.setGxpyes(rs.getString("gxp"));
							
							help.setTypeofprinter(rs.getString("typeofprinter"));
							help.setUsers(rs.getString("noofusers"));
							help.setPrintvolpermonth(rs.getString("printvolpm"));
							help.setSuggestmodelno(rs.getString("suggmodelno"));
							help.setApprxvalue(rs.getString("Apprxvalue"));
					
							help.setLanyes(rs.getString("lan"));
							help.setAnysplreq(rs.getString("anysplreq"));
							help.setPurpose(rs.getString("purpose"));
						}
						
						if(type.equalsIgnoreCase("New IT Asset Request"))
						{
							
							help.setAssetvalue(rs.getString("Asset_Details"));
							help.setAdloginname(rs.getString("ADLoginName"));
							help.setServer(rs.getString("Server_Address"));
							help.setReqdfolderacces(rs.getString("Req_Folder_access"));
							help.setReason(rs.getString("Reason"));
							help.setHostname(rs.getString("HostName"));
							help.setIPNumber(rs.getString("IPAddress"));
							help.setReqPriority(rs.getString("Req_Priority"));
							help.setAssetcategory(rs.getString("Category"));
							help.setReqbydate(rs.getString("reqbydate"));
							help.setSuggestmodelname(rs.getString("suggmodel"));
							
							help.setGxpyes(rs.getString("gxp"));
							
							help.setTypeofprinter(rs.getString("typeofprinter"));
							help.setUsers(rs.getString("noofusers"));
							help.setPrintvolpermonth(rs.getString("printvolpm"));
							help.setSuggestmodelno(rs.getString("suggmodelno"));
							help.setApprxvalue(rs.getString("Apprxvalue"));
					
							help.setLanyes(rs.getString("lan"));
							help.setAnysplreq(rs.getString("anysplreq"));
							help.setPurpose(rs.getString("purpose"));
						}
						
						
					 }
					
					
					//requester 
			
					
					String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE " +
							"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+empno+"' and " +
							"dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";	
					 rs= ad.selectQuery(data);
					try {
						while(rs.next())
						{
							reqstrname=rs.getString("EMP_FULLNAME");
							senderMail=rs.getString("EMAIL_ID"); 
							
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
					
					
					mailForm.setEmpname(reqstrname);
					mailForm.setSendermail(senderMail);
					
					//Approver
					String getAppEmailID="select EMAIL_ID from emp_official_info where PERNR='"+approver+"'";
					ResultSet approverRS=ad.selectQuery(getAppEmailID);
					while(approverRS.next()){
						approvermail = approverRS.getString(1);
					}
					mailForm.setToAddress(approvermail);
					System.out.println("m -> "+approvermail);
					mailForm.setccAddress("");
					mailForm.setbccAddress("");
					String desc = "";
					mailForm.setSubject("IT HelpDesk Request From "+reqstrname);
					desc=desc+"";
					desc = "<STYLE>body {font-family:sans-serif,arial,helvetica,sans-serif;font-size:9pt;}"
			             +"TABLE {border-collapse:collapse;border:1px solid black;}"
			             +"TH {background-color:#dce9f9 ;border:1px solid black;font-size:9pt;}"
			             +"TD {padding:5px;border:1px solid black;font-size:9pt;}"
					 +"</STYLE>";
					desc=desc+"<html><body><img src="+"https://portal.microlabs.co.in/images/logo.png"+" height=60 width=50  /></br> <table><tr><th colspan=5 ><center><big>IT Request Details</big></center></th></tr>";
					
					if(type.contains("Active Directory"))
					{
					
					desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
					+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
					+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
					+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
					
					desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
							+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
				
					;
				
					}
					
					if(type.equalsIgnoreCase("Email ID Create Request"))
					{
					
					desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
					+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
					+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
					+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
					
					desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
							+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
					+ "<tr><td><b>Suggested Email Id</b></td><td colspan=3>"+help.getSuggestedEmailId()+"</td></tr>"
					+ "<tr><td><b>How many ID's there in requested dept</td><td colspan=3>"+help.getTotalIDs()+"</td></tr>"			
					+ "<tr><td><b>Department</td><td >"+help.getDepartment()+"</td><td><b>Designation</td><td >"+help.getDesignation()+"</td></tr>"
					+ "<tr><td><b>Allow Outside Communication</td><td >"+help.getOutsidecomyes()+"</td><td><b>Is He HOD</td><td >"+help.getIsHeHod()+"</td></tr>"
					+ "<tr><td><b>Enable Web Access</td><td >"+help.getEnablewebAccess()+"</td><td><b>Is This Common ID</td><td >"+help.getIsthisCommonId()+"</td></tr>"
					+ "<tr><td><b>Remarks</td><td colspan=3>"+help.getReason()+"</td></tr>"
					;
				
					}
					
					if(type.equalsIgnoreCase("Email ID Change Request"))
					{
						desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
								+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
								+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
								+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
								
								desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
										+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
										+ "<tr><td><b>First Name</td><td >"+help.getFirstname()+"</td><td><b>Last Name</td><td >"+help.getLastname()+"</td></tr>"
										+ "<tr><td><b>Middle Name</td><td >"+help.getMiddname()+"</td><td><b>From Email Id</td><td >"+help.getFromemailId()+"</td></tr>"
										+ "<tr><td><b>Newly Joined</td><td >"+help.getNewlyJoinedYes()+"</td><td><b>Joining Date</td><td >"+help.getJoiningDate()+"</td></tr>"
										+ "<tr><td><b>Suggested Email Id</td><td >"+help.getSuggestedEmailId()+"</td><td><b>How many ID's there in requested dept</td><td >"+help.getTotalIDs()+"</td></tr>"
										+ "<tr><td><b>Is He HOD</td><td >"+help.getIsHeHod()+"</td><td><b>Transfer Old Emails</td><td >"+help.getTransferoldmail()+"</td></tr>"
										+ "<tr><td><b>Allow Outside Communication</b></td><td colspan=3>"+help.getOutsidecomyes()+"</td></tr>"
										+ "<tr><td><b>Is This Common ID</td><td >"+help.getIsthisCommonId()+"</td><td><b>Enable Web Access</td><td >"+help.getEnablewebAccess()+"</td></tr>"
										+ "<tr><td><b>Remarks</td><td colspan=3>"+help.getReason()+"</td></tr>"
										;
						
					}
					if(type.equalsIgnoreCase("Email ID Delete Request"))
					{
						desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
								+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
								+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
								+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
								
								desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
										+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
						+ "<tr><td><b>Email Id To Be Deleted:</b></td><td >"+help.getEmailidDelete()+"</td><td><b>Emails To Be Forwarded To ID:</b></td><td >"+help.getForward_MailID()+"</td></tr>"
						+ "<tr><td><b>Is He HOD:</b></td><td >"+help.getIsHeHod()+"</td><td><b>Is This Common ID:</b></td><td >"+help.getIsthisCommonId()+"</td></tr>"
						+ "<tr><td><b>Delete From:</b></td><td >"+help.getDeletefrom()+"</td><td><b>Required Deleted Mail Database:</b></td><td >"+help.getDeletedDBYes()+"</td></tr>"
							+ "<tr><td><b>Path Where To Be Copied </b></td><td colspan=3>"+help.getMailIDPath()+"</td></tr>"			
							+ "<tr><td><b>Remarks</td><td colspan=3>"+help.getReason()+"</td></tr>"
										;
					}
					
					if(type.equalsIgnoreCase("Internet Access Request"))
					{
						
						desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
								+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
								+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
								+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
								
								desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
										+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
								+ "<tr><td><b>AD Login Name </b></td><td colspan=3>"+help.getAdloginname()+"</td></tr>"
											+ "<tr><td><b>Asset Details </b></td><td colspan=3>"+help.getAssetvalue()+"</td></tr>"								
								+ "<tr><td><b>Remarks</td><td colspan=3>"+help.getReason()+"</td></tr>"
								;
					}
					if(type.equalsIgnoreCase("External Drives Access Request"))
					{
						
						desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
								+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
								+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
								+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
								
								desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
										+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
						          + "<tr><td><b>Computer Name/Host Name:</b></td><td >"+help.getAdloginname()+"</td><td><b>Asset Details:</b></td><td >"+help.getAssetvalue()+"</td></tr>"
						          + "<tr><td><b>Device Type:</b></td><td >"+help.getCdrom()+" "+help.getUsb()+"</td><td><b>Access Type:</b></td><td >"+help.getAccesstype()+"</td></tr>"								
							+ "<tr><td><b>Remarks</td><td colspan=3>"+help.getReason()+"</td></tr>"
							;
					}
					if(type.equalsIgnoreCase("FTP Access Request"))
					{
						
						desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
								+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
								+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
								+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
								
								desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
										+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
						+ "<tr><td><b><b>Required Folder Access/Path: </b></td><td colspan=3>"+help.getRequestername()+"</td></tr>"
											+ "<tr><td><b>Access Type </b></td><td colspan=3>"+help.getAccesstype()+"</td></tr>"	
										+ "<tr><td><b>Required Duration Of Access</b></td><td colspan=3>"+help.getDurationofaccess()+"</td></tr>"	
								+ "<tr><td><b>Remarks</td><td colspan=3>"+help.getReason()+"</td></tr>"
								;
					}
					if(type.equalsIgnoreCase("File Server Access Request"))
					{

						desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
								+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
								+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
								+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
								
								desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
										+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
										+ "<tr><td><b><b>AD Login Name: </b></td><td colspan=3>"+help.getAdloginname()+"</td></tr>"
									   + "<tr><td><b>Required Folder Access:</b></td><td >"+help.getRequiredfoldername()+"</td><td><b>Location:</b></td><td >"+help.getFromlocationId()+"</td></tr>"	
									      + "<tr><td><b>Access Type:</b></td><td >"+help.getAccesstype()+"</td><td><b>Asset Details:</b></td><td >"+help.getAssetvalue()+"</td></tr>"	
			+ "<tr><td><b>Remarks</td><td colspan=3>"+help.getReason()+"</td></tr>"
			;
					}
					if(type.equalsIgnoreCase("New IT Spares Request"))
					{
						
						desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
								+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
								+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
								+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
								
								desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
										+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
								+ "<tr><td><b>Category:</b></td><td >"+help.getAssetcategory()+"</td><td><b>Required By Date:</b></td><td >"+help.getReqbydate()+"</td></tr>"
								+ "<tr><td><b>Suggested Model If Any:</b></td><td >"+help.getSuggestmodelname()+"</td><td><b>Approximate Value:</b></td><td >"+help.getApprxvalue()+"</td></tr>"
							    + "<tr><td><b>Purpose</td><td colspan=3>"+help.getPurpose()+"</td></tr>"
								;
					}
					
					if(type.equalsIgnoreCase("New IT Asset Request"))
					{
						
						desc=desc+"<tr><th colspan=8>Requester Details</th></tr>"
								+ "<tr><td><b>Name:</b></td><td> "+help.getRequestername()+"</td><td><b>Employee No:</b></td><td >"+help.getEmpno()+"</td></tr>"
								+ "<tr><td><b>Department:</b></td><td >"+help.getRequesterdepartment()+"</td><td><b>Designation:</b></td><td >"+help.getRequesterdesignation()+"</td></tr>"
								+ "<tr><td><b>Location:</b></td><td >"+help.getLocation()+"</td><td><b>Ext No:</b></td><td >"+help.getExtno()+"</td></tr>";
								
								desc=desc+"<tr><th colspan=8>Other Details</th></tr>"
										+ "<tr><td><b>Req Type</td><td >"+type+"</td><td><b>Req No</td><td >"+Req_Id+"</td></tr>"
										+ "<tr><td><b>Category:</b></td><td >"+help.getAssetcategory()+"</td><td><b>Required By Date:</b></td><td >"+help.getReqbydate()+"</td></tr>"
										+ "<tr><td><b>Suggested Model If Any:</b></td><td >"+help.getSuggestmodelname()+"</td><td><b>GXP Impact Validation Required:</b></td><td >"+help.getGxpyes()+"</td></tr>"					
											+ "<tr><td><b>Approximate Value:</b></td><td >"+help.getApprxvalue()+"</td><td><b>LAN Connectivity:</b></td><td >"+help.getLanyes()+"</td></tr>"
										
											 + "<tr><td><b>Any Special Requirements</td><td colspan=3>"+help.getAnysplreq()+"</td></tr>"
										
										 + "<tr><td><b>Purpose</td><td colspan=3>"+help.getPurpose()+"</td></tr>"
											;
					}
					
					
					desc=desc+"</table><br><br>";
					desc=desc+"<a href="+"https://portal.microlabs.co.in/approvals.do?method=approveITMailRequest&userID="+approver+"&reqId="+Req_Id+"&status=Approve>"
				    		+ "<img src="+"https://portal.microlabs.co.in/images/Approve.png"+" height=20 width=80 title=Approve border=0/>"
				    				+ "</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="+"https://portal.microlabs.co.in/approvals.do?method=approveITMailRequest&userID="+approver+"&reqId="+Req_Id+"&status=Reject>"
				    						+ "<img src="+"https://portal.microlabs.co.in/images/Reject.png"+" height=20 width=80 title=Reject border=0 /></a></br></br>";
				   
				    desc=desc+"</br></br>";
					desc=desc+" <span style='font-size:10.0pt;font-family:\'Verdana\',\'sans-serif\';mso-fareast-font-family:\'Times New Roman\'>This information provided from <a href="+"https://portal.microlabs.co.in/"+">EMicro Portal</a> .</span> ";
				    desc=desc+"</body></html>";
				
				    mailForm.setDescription(desc);
				 }
		    it.sendMail(request, mailForm,"request");
		 }catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		 return 1;
	 }
	 
	 public LinkedList getUserLoginDetails(HttpServletRequest request){
			LinkedList userDetails = new LinkedList();
			HttpSession session=request.getSession();
			MainDao ad = new MainDao(); 
			String userName = "";
			String userMail = "";
			String userPass = "";
			try{
			UserInfo user=(UserInfo)session.getAttribute("user");
			if(user == null){
				String getApproverID="select * from users where username='admin'";
				
				ResultSet approverRS=ad.selectQuery(getApproverID);
				while(approverRS.next()){
					
					userName = approverRS.getString("username");
					userMail = approverRS.getString("mail_id");
					userPass = approverRS.getString("password");
						
				}
			}
			else{
				userName = user.getUserName();
				userMail = user.getFullName();
				userMail=userMail.trim();
				userMail=userMail.replaceAll("\\s+","");
				userPass = user.getPassword();
			}
			userDetails.add(0,userName);
			userDetails.add(1,userMail);
			userDetails.add(2,userPass);
			}
			catch (SQLException sqle) { System.out.println("SQLException @ sending User Details"); sqle.printStackTrace();}
			return userDetails;
		}

	 
	 public String sendMail(HttpServletRequest request, MailInboxForm mailSentForm, String mailfrom) throws IOException, ServletException, SQLException, MessagingException
		{
		 
			String HOST_NAME = "mail.microlabs.co.in";
			LinkedList  userInfo = getUserLoginDetails(request);
	    	final String USER_NAME = userInfo.get(1).toString();
	    	final String DEFAULT_ID = "emicro@microlabs.in";
	    	String protocol ="smtp";
	         	password = "micro1@";
			String sucMessage="success";
			String to = mailSentForm.getToAddress();
			String cc = mailSentForm.getccAddress();
			String localhost = (InetAddress.getLocalHost()).getCanonicalHostName();
			System.out.println("local --> "+localhost);
			String bcc = mailSentForm.getbccAddress();
			String port = "25";//request.getServerPort();
		
			Properties props = new Properties();
			props.put("mail.smtp.host",HOST_NAME);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.socketFactory.port", port);
			
			
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					   @Override
					protected PasswordAuthentication getPasswordAuthentication() {
					   return new PasswordAuthentication(DEFAULT_ID,password);//employee company email address and password
					   }
			});
			  try {
			   MimeMessage message = new MimeMessage(session);
			   message.setFrom(new InternetAddress(USER_NAME));//employee company email address
			   message.addRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			   message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(cc));
			   message.addRecipients(Message.RecipientType.BCC,InternetAddress.parse(bcc));
			   message.setSubject(mailSentForm.getSubject());
			   
			   //Description
			   Multipart mp = new MimeMultipart();
			   MimeBodyPart descripPart = new MimeBodyPart();
			   System.out.println(mailSentForm.getDescription());
			   descripPart.setContent(mailSentForm.getDescription(), "text/html");
		       mp.addBodyPart(descripPart);
			   
			   
			   //For Attachment
		       if(mailfrom.equalsIgnoreCase("mail")){}
			   
			   message.setContent(mp);
			   session.setDebug(true);
			   //Service sr = new Service(session,)
			   //send message
			   Transport transport = session.getTransport(protocol);
			   
			   transport.connect();
			   if(message.getAllRecipients()!=null)
			   {
				   transport.sendMessage(message, message.getAllRecipients());
			   }
			   transport.close();
			   //Transport.send(message);
			 
			  }
			  catch (NoSuchProviderException e1) {
				  sucMessage = "Mail not Sent";
				  System.out.println("Exception @ mail sending");
			  e1.printStackTrace();}
			  catch (MessagingException e) {
				  sucMessage = "Mail not Sent";
				  System.out.println("Exception @ mail sending");
					if(mailfrom.equalsIgnoreCase("mail")){}
			  		e.printStackTrace();
			 }
			 catch (NullPointerException npe) {
				 sucMessage = "Mail not Sent";
				 System.out.println("Exception @ mail sending");
					if(mailfrom.equalsIgnoreCase("mail")){}
			  		npe.printStackTrace();
			 }
			  /*finally {
		            deleteUploadFiles(uploadedFiles);
		        }*/
			return sucMessage;
		}

}
