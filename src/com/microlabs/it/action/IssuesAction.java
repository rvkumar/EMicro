package com.microlabs.it.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.microlabs.ess.dao.EssDao;
import com.microlabs.it.Dao.ITMail;
import com.microlabs.it.DaoImpl.IssueImpl;
import com.microlabs.it.form.IssuesForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;

public class IssuesAction extends DispatchAction{
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

		}
		return b;

	} 
	
	public ActionForward closeRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		EssDao ad=new EssDao();
		String selectedReq[]=help.getSelectedRequestNo();
		for(int a=0;a<selectedReq.length;a++){
			int reqNo=Integer.parseInt(selectedReq[a]);
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
			String dateNow = ft.format(dNow);
			
			//check update employee
			int empCount=0;
			String checkEmp="select count(*) from IT_All_ISSUES WHERE Req_Id='"+reqNo+"'";
			ResultSet rsCheckEmp=ad.selectQuery(checkEmp);
			try{
				while(rsCheckEmp.next()){
					empCount=rsCheckEmp.getInt(1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			if(empCount>0){ 
				
				String updateStatus="update IT_ISSUES set approved_date='"+dateNow+"',Req_Status='Completed',read_Status=0 where Req_No='"+reqNo+"'";
				int  i=ad.SqlExecuteUpdate(updateStatus);
				if(i>0){
					String updateAll_ITStatus="update IT_All_ISSUES set approved_date='"+dateNow+"',Req_Status='Completed',"
					+ "Comments='"+help.getComments()+"' where Req_Id='"+reqNo+"' and Technician_Id='"+user.getEmployeeNo()+"'";
					int j=ad.SqlExecuteUpdate(updateAll_ITStatus);
					help.setMessage("Request has been Saved.");
					ad.connClose();
					ITMail mail=new ITMail();
					String reqester="";
					mail.sendMailToRequester(request, reqNo, "Issues", user.getEmployeeNo(),help.getComments());
					if(j>0){
						int transNo=help.getTransNo();
						
						String insertIntoTrans="INSERT INTO IT_Issue_Trans(Req_No,Emp_No,Req_Status,Transaction_Date,Remarks,Sequence,Status) "
						+ "values('"+reqNo+"','"+user.getEmployeeNo()+"','Completed','"+dateNow+"','"+help.getComments()+"','0','Completed')";
						ad.SqlExecuteUpdate(insertIntoTrans);
					}
					}
				
			}
		}
		ad.connClose();
		currentRecord(mapping, form, request, response);
		return mapping.findForward("displayIssues");
	}
	public ActionForward sapRequestform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesForm help = (IssuesForm) form;
		/*String forwardtype="";
		String localhostname="";
		String ipaddress="";
		String username="";
		
	
		help.setCategory("");
		help.setSubject("");
		help.setReason("");
		NewsandMediaDao ad=new NewsandMediaDao();
		try {
			int reqNo=0;
		String maxReqNo="Select max(Req_No) from IT_SAP_ISSUES";
		ResultSet rsMax=ad.selectQuery(maxReqNo);
		while(rsMax.next()){
			reqNo=1+rsMax.getInt(1);
		}
         help.setRequestNo(reqNo);		
         help.setRequestStatus("New");
		userDetails(mapping, form, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(help.getEmployeeno().equals(""))
		{
			help.setEmployeeno(user.getEmployeeNo());
		}
		else
		{
			help.setEmployeeno(help.getEmployeeno());
		}
		
		///MAIL ID OF USER
		String mail="";
		String a="Select EMAIL_ID from emp_official_info where pernr='"+help.getEmployeeno()+"'";
		ResultSet rss=ad.selectQuery(a);
		try {
			if(rss.next())
			{
			mail=rss.getString("EMAIL_ID")	;
			if(!mail.equalsIgnoreCase("null"))
				help.setReqEmail(mail);
		
			}
			
			if(mail.equalsIgnoreCase("")||mail.equalsIgnoreCase("null"))
			{
				request.setAttribute("MAILA","MAILA");
			}
			else
			{
				request.setAttribute("MAILP","MAILP");	
			}
			
		} catch (SQLException e) {
					e.printStackTrace();
		}
       //Category Setting
		
		ArrayList category=new ArrayList();
		ArrayList categoryShortName=new ArrayList();
		String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and (c_cat_sh_name='DC' or c_cat_sh_name='PSAP' or c_cat_sh_name='SSAP' or c_cat_sh_name='AUTH')";
		ResultSet rs2 = ad.selectQuery(cat);
		
		try {
			while (rs2.next())

			{
				category.add(rs2.getString("c_cat_name"));
				categoryShortName.add(rs2.getString("c_cat_sh_name"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		help.setCategorylist(category);
		
		help.setCategortShortlist(categoryShortName);*/
		
		ArrayList list = new ArrayList();
		ArrayList idlist = new ArrayList();
		String rr= "select * from sap_Request";
		ResultSet ff=ad.selectQuery(rr);
		try {
			while(ff.next())
			{
				/*IssuesForm h1= new IssuesForm();
				h1.setRequestId(ff.getInt("ID"));
				h1.setRequestName(ff.getString("REQUEST"));
				list.add(h1);*/
				idlist.add(ff.getInt("ID"));
				list.add(ff.getString("REQUEST"));
			}
			help.setRequestlist(list);
			help.setRequestidlist(idlist);
			/*if(list.size()>0)
			{
				request.setAttribute("ll", list);
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return mapping.findForward("saprequestform");
	}
	
	

	public ActionForward sapuseridmanagement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesForm help = (IssuesForm) form;
		String forwardtype="";
		String localhostname="";
		String ipaddress="";
		String username="";
		String cate= request.getParameter("cate");
	
		help.setCategory("");
		help.setSubject("");
		help.setReason("");
		NewsandMediaDao ad=new NewsandMediaDao();
		try {
			int reqNo=0;
		String maxReqNo="Select max(Req_No) from IT_SAP_ISSUES";
		ResultSet rsMax=ad.selectQuery(maxReqNo);
		while(rsMax.next()){
			reqNo=1+rsMax.getInt(1);
		}
         help.setRequestNo(reqNo);		
         help.setRequestStatus("New");
		userDetails(mapping, form, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(help.getEmployeeno().equals(""))
		{
			help.setEmployeeno(user.getEmployeeNo());
		}
		else
		{
			help.setEmployeeno(help.getEmployeeno());
		}
		
		///MAIL ID OF USER
		String mail="";
		String a="Select EMAIL_ID from emp_official_info where pernr='"+help.getEmployeeno()+"'";
		ResultSet rss=ad.selectQuery(a);
		try {
			if(rss.next())
			{
			mail=rss.getString("EMAIL_ID")	;
			if(!mail.equalsIgnoreCase("null"))
				help.setReqEmail(mail);
		
			}
			
			if(mail.equalsIgnoreCase("")||mail.equalsIgnoreCase("null"))
			{
				request.setAttribute("MAILA","MAILA");
			}
			else
			{
				request.setAttribute("MAILP","MAILP");	
			}
			
		} catch (SQLException e) {
					e.printStackTrace();
		}
       //Category Setting
		
		ArrayList category=new ArrayList();
		ArrayList categoryShortName=new ArrayList();
	/*	String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and (c_cat_sh_name='DC' or c_cat_sh_name='PSAP' or c_cat_sh_name='SSAP' or c_cat_sh_name='AUTH')";*/
		String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and (c_cat_sh_name in("+cate+"))";
		ResultSet rs2 = ad.selectQuery(cat);
		
		try {
			while (rs2.next())

			{
				category.add(rs2.getString("c_cat_name"));
				categoryShortName.add(rs2.getString("c_cat_sh_name"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		help.setCategorylist(category);
		
		help.setCategortShortlist(categoryShortName);
		ArrayList roleList = new ArrayList();
		ArrayList roleLabelList = new ArrayList();
		String role = "select sap.role,sap.id,sap.department from Role_SAP as sap,location as loc where loc.LOCID = '"+user.getPlantId()+"' AND sap.plant=loc.location_code";
		ResultSet rs21 = ad.selectQuery(role);
		
		try {
			while (rs21.next())

			{
				roleList.add(rs21.getString("id"));
				roleLabelList.add(rs21.getString("Department")+"-"+rs21.getString("Role"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		help.setRolelist(roleList);
		help.setRoleLabelList(roleLabelList);
		
		if(cate.equalsIgnoreCase("'PSAP','SSAP'"))
		{
			request.setAttribute("userid", "userid");
			help.setRequestName("SAP User Id Management");
		}
		if(cate.equalsIgnoreCase("'AUTH'"))
		{
			request.setAttribute("userid", "userid");
			help.setRequestName("SAP Authorization Management");
		}
		if(cate.equalsIgnoreCase("'ROLES'"))
		{
			request.setAttribute("userid", "userid");
			help.setRequestName("SAP Roles Management");
		}
		if(cate.equalsIgnoreCase("'DC'"))
		{
		/*	request.setAttribute("userid", "userid");*/
			help.setRequestName("Document Cancellation");
		}
		return mapping.findForward("sapuseridmanagement");
	}
	
	
	public ActionForward sapuseridsubmitreq(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		//String maild=request.getParameter("EMAIL");

		//insert emial id
		/*if(!maild.equalsIgnoreCase("null")||!maild.equalsIgnoreCase(""))
		{
		String mail="Update emp_official_info set EMAIL_ID='"+maild+"' where pernr='"+user.getEmployeeNo()+"'";
		ad.SqlExecuteUpdate(mail);
		}*/
		
		EssDao ad=EssDao.dBConnection();
		IssuesForm help = (IssuesForm) form;
		ITMail email = new ITMail();
//		String category=help.getCategory();
		String subCategory=request.getParameter("subCategory");
		String technician=request.getParameter("technician");
		String category=request.getParameter("category");
		//String reqname=request.getParameter("reqname");
		String cate="";
		 String reqname=request.getParameter("reqname");
		 if(reqname!=null)
		 {
		 if(reqname.equalsIgnoreCase("SAP User Id Management"))
		 {
			 
			 cate="'PSAP','SSAP'";
			 
		 }
		 if(reqname.equalsIgnoreCase("SAP Authorization Management"))
		 {
			 
			 cate="'AUTH'";
			 
		 }
		 if(reqname.equalsIgnoreCase("SAP Roles Management"))
		 {
			 
			 cate="'ROLES'";
			 
		 }
		 if(reqname.equalsIgnoreCase("Document Cancellation"))
		 {
			 
			 cate="'DC'";
			 
		 }
		 }
		String locNo=request.getParameter("locNo");
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
	    String dateNow = ft.format(dNow);
	    String type=help.getRequestType();
	    Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("dd/MM/yyyy");
	    String currendate = ft1.format(dNow1);
	    String empno="";
		empno=help.getEmployeeno();
		if((empno.equalsIgnoreCase("") )|| empno==null)
		{
		 empno=user.getEmployeeNo();
		}
		
		synchronized (this) {
	    int requestNo=help.getRequestNo();
	    int newReqNo=0;
	    int status=0;
	    int hodpresent=0;
	    String HODPresentId="";
		String HODPresentname="";
		int hodpresentinIt=0;
	    
	//check requestno
	String checkReqNo="select count(*) from IT_SAP_ISSUES where Req_No='"+requestNo+"'";
	try{
	ResultSet rsCheck=ad.selectQuery(checkReqNo);
	while(rsCheck.next()){
		status=rsCheck.getInt(1);
	}
	
	if(status>0){
		String maxReqNo="Select max(Req_No) from IT_SAP_ISSUES";
		ResultSet rsMax=ad.selectQuery(maxReqNo);
		while(rsMax.next()){
			requestNo=1+rsMax.getInt(1);
			newReqNo=requestNo;
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	
	int hodreq =0;
	
	String chkreq=" select c_hod_req from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and c_cat_name = '"+category+"' and c_sub_cat_name ='"+subCategory+"'";
	 ResultSet chk= ad.selectQuery(chkreq);
	 try {
		while(chk.next())
		 {
			 hodreq=chk.getInt(1);
		 }
	} catch (SQLException e1) {
		
		e1.printStackTrace();
	}
	
	 String pendingApprovers="";
	if(hodreq ==1)
	 {
				
			String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs1= ad.selectQuery(chkhod);
			 try {
				while(rs1.next())
				 {
					 hodpresent=rs1.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
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
			 
			 String getReqestNumber="select MAX(Priority) as priority"
				    	+" from Material_Approvers app,"
					+"emp_official_info as emp"
					+" where Material_Type='"+category+"' and " 
					+"Material_Sub_Category='"+subCategory+"' and "  
					+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"')";
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
			maxPriority+=1;
			
		
			 
		if(hodpresent == 1)
			 {   
				       String getApproverID ="select 1 as priority ,"
				         +"PERNR as employeeNo ,"   
				         +"EMP_FULLNAME as employeeName "
				         +" from emp_official_info,  DEPARTMENT,DESIGNATION ,ESS_Approvers"
				        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
				        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
				        +"emp_official_info.PERNR =  ESS_Approvers.ApproverId and "
				        +"ESS_Approvers.employeeNumber = '"+empno+"'"
				        +" union " 
			            +"select app.priority+1 as priority,"
			            +"PERNR as employeeNo ,"
			 			+"EMP_FULLNAME as employeeName "
							+" from Material_Approvers app,"
							+"emp_official_info as emp ,DEPARTMENT,DESIGNATION"
							+" where Material_Type='"+category+"' and " 
						+"Material_Sub_Category='"+subCategory+"' and "  
						+"emp.PERNR=app.Approver_Id and " 
						+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"') and role ='USER' and "
						+"emp.DPTID  = DEPARTMENT.DPTID and "
						+"emp.DSGID  = DESIGNATION.DSGID" 
						+" Union "
						+"select '"+maxPriority+"' as priority ,"
						+"PERNR as employeeNo ," 
				         +"EMP_FULLNAME as employeeName "
				         +" from emp_official_info,  DEPARTMENT,DESIGNATION "
				        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
				        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
				        +"emp_official_info.PERNR = '"+technician+"'"
						+" Order by 1";
				 
			 
				    ResultSet approverRS=ad.selectQuery(getApproverID);
					try {
						while(approverRS.next()){
								//int priority = approverRS.getInt("priority");
								
								if(pendingApprovers.equalsIgnoreCase("")){
								pendingApprovers = approverRS.getString("employeeName");
								}
								else{
									pendingApprovers = pendingApprovers + " ," +approverRS.getString("employeeName");
								}
								
						}
						}
					catch (SQLException e) {
			                e.printStackTrace();
			            }
			 
			 
			 
			 String saveIssue="INSERT INTO IT_SAP_ISSUES(Req_No,Req_date,Emp_No,Email_ID,Ext_No,IP_Phone,HostName,IPAddress,"
			+ "Req_type,Req_Status,Req_Priority,ITEng_Status,Subject,Reason,Last_approver,Pending_approver,approved_date,Priority,Sub_Category,mode,Location,read_Status,AssignTo,SLA_Status,raisedBy,Role,sap_request_type,old_id,new_id) "
			+ "values('"+requestNo+"','"+dateNow+"','"+help.getEmpno()+"','"+help.getEmpEmailID()+"','"+help.getExtno()+"','"+help.getIpPhoneno()+"',"
			+ "'','"+help.getIPNumber()+"','"+category+"','Pending','"+help.getIssuePriority()+"','Pending','"+help.getSubject()+"','"+help.getReason()+"','','"+pendingApprovers+"','','1','"+subCategory+"','"+help.getMode()+"','"+help.getLocNo()+"','2','"+technician+"','','"+user.getEmployeeNo()+"','"+help.getRoleId()+"','"+reqname+"','"+help.getOldid()+"','"+help.getNewid()+"')";
			 int i=ad.SqlExecuteUpdate(saveIssue);
			 
			 if(i>0){
				 //send to Hod
			 String sendReq="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
							"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
							"'"+currendate+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
			 
			 int k = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", HODPresentId);
							
			 int j=ad.SqlExecuteUpdate(sendReq);
			     ad.connClose();
			     if(i>0){
				 help.setMessage("Request '"+requestNo+"'  has been Created and Submitted for Process");
			     }
			     else 
			     {
			     help.setMessage("Request '"+requestNo+"'  has been Created and Submitted for Process");	 
			     }
			     
				// ITMail mail=new ITMail();
				// mail.sendMailToApprover(request, requestNo, "Issues", technician);
				 
					
				}
			 }	
		else
			{
			help.setMessage("Request not sent HOD Not assigned Contact Admin ");
			}
			}
else
{
	
	String getReqestNumber="select MAX(Priority) as priority"
	    	+" from Material_Approvers app,"
		+"emp_official_info as emp"
		+" where Material_Type='"+category+"' and " 
		+"Material_Sub_Category='"+subCategory+"' and "  
		+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"')";
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
		maxPriority+=1;

	String getApproverID =
           "select app.priority+1 as priority,"
           +"PERNR as employeeNo ,"
			+"EMP_FULLNAME as employeeName "
				+" from Material_Approvers app,"
				+"emp_official_info as emp ,DEPARTMENT,DESIGNATION"
				+" where Material_Type='"+category+"' and " 
			+"Material_Sub_Category='"+subCategory+"' and "  
			+"emp.PERNR=app.Approver_Id and " 
			+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"') and role ='USER' and "
			+"emp.DPTID  = DEPARTMENT.DPTID and "
			+"emp.DSGID  = DESIGNATION.DSGID" 
			+" Union "
			+"select '"+maxPriority+"' as priority ,"
			+"PERNR as employeeNo ," 
	         +"EMP_FULLNAME as employeeName "
	         +" from emp_official_info,  DEPARTMENT,DESIGNATION "
	        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
	        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
	        +"emp_official_info.PERNR = '"+technician+"'"
			+" Order by 1";
	 

	    ResultSet approverRS=ad.selectQuery(getApproverID);
		try {
			while(approverRS.next()){
					//int priority = approverRS.getInt("priority");
					
					if(pendingApprovers.equalsIgnoreCase("")){
					pendingApprovers = approverRS.getString("employeeName");
					}
					else{
						pendingApprovers = pendingApprovers + " ," +approverRS.getString("employeeName");
					}
					
			}
			}
		catch (SQLException e) {
               e.printStackTrace();
           }



String saveIssue="INSERT INTO IT_SAP_ISSUES(Req_No,Req_date,Emp_No,Email_ID,Ext_No,IP_Phone,HostName,IPAddress,"
+ "Req_type,Req_Status,Req_Priority,ITEng_Status,Subject,Reason,Last_approver,Pending_approver,approved_date,Priority,Sub_Category,mode,Location,read_Status,AssignTo,SLA_Status,raisedBy,Role,sap_request_type,old_id,new_id) "
+ "values('"+requestNo+"','"+dateNow+"','"+help.getEmpno()+"','"+help.getEmpEmailID()+"','"+help.getExtno()+"','"+help.getIpPhoneno()+"',"
+ "'','"+help.getIPNumber()+"','"+category+"','Pending','"+help.getIssuePriority()+"','Pending','"+help.getSubject()+"','"+help.getReason()+"','','"+pendingApprovers+"','','1','"+subCategory+"','"+help.getMode()+"','"+help.getLocNo()+"','2','"+technician+"','','"+user.getEmployeeNo()+"','"+help.getRoleId()+"','"+reqname+"','"+help.getOldid()+"','"+help.getNewid()+"')";
int i=ad.SqlExecuteUpdate(saveIssue);


if(i>0){
	 help.setMessage("Request '"+requestNo+"'  has been Created and Submitted for Process");
    }
    else 
    {
    help.setMessage("Request '"+requestNo+"'  has been Created and Submitted for Process");	 
    }	
	
	
	
	
	///new
    int presentPriorityCreator = 0;
	String Approver_Id_New ="";
	String role_New ="";
	String Parllel_Approver_1_New ="";
	String Parllel_Approver_2_New ="";
	
	String getpriotiy3 ="select Priority , Approver_Id, Parllel_Approver_1,Parllel_Approver_2 , Role from Material_Approvers"
            		   +" where Location = (select LOCATION_CODE FROM Location WHERE LOCID ='"+user.getPlantId()+"')"
            		   +" and Material_sub_category = '"+subCategory+"'"
            		   +" and Priority = '1' "
            		   +" and Material_Type= '"+category+"'" ;
	
	
	ResultSet rs2=ad.selectQuery(getpriotiy3);
	
	try{
       	
		if (rs2.next()){
			presentPriorityCreator = rs2.getInt("Priority");
			Approver_Id_New = rs2.getString("Approver_Id");
			role_New = rs2.getString("role");
			Parllel_Approver_1_New = rs2.getString("Parllel_Approver_1");
			Parllel_Approver_2_New = rs2.getString("Parllel_Approver_2");
			
		}
		}catch (SQLException e) {
		
		e.printStackTrace(); }
   
	  
	   if (!role_New.equalsIgnoreCase("Creator")){
		   
		 
		   
		   
	   if(!Approver_Id_New.equalsIgnoreCase("")) {
	   
	   String sendReq16="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
				"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
				"'"+currendate+"','Pending','','"+Approver_Id_New+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
	   int ii16=ad.SqlExecuteUpdate(sendReq16);
	   
	   
	   int j = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", Approver_Id_New);
	   }
	   
	   if(!Parllel_Approver_1_New.equalsIgnoreCase("")) {
	   String sendReq15="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
				"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
				"'"+currendate+"','Pending','','"+Parllel_Approver_1_New+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
	       int ii15=ad.SqlExecuteUpdate(sendReq15);
	       
	       int j = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", Parllel_Approver_1_New);
	   }
	   
	 
	   
	   if(!Parllel_Approver_2_New.equalsIgnoreCase("")) {
    	   String sendReq14="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
					"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
					"'"+currendate+"','Pending','','"+Parllel_Approver_2_New+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
   	   int ii10=ad.SqlExecuteUpdate(sendReq14);
   	   
   	   int j = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", Parllel_Approver_2_New);
       
   	
    	   }
	   
	   }
	   
	   //next level is creator den sending mail to oly technician 
	   if (role_New.equalsIgnoreCase("Creator")){
		   
		   if(!technician.equalsIgnoreCase("")) {
	    	   
	    	   String sendReq16="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
						"'"+currendate+"','Pending','','"+technician+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
	    	   int ii16=ad.SqlExecuteUpdate(sendReq16);
	    	   
	    	   
	    	   
	    	   int j = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", technician);

	           
		   
	   }
	   }  
	
	   
	   
	}			
		
	
	
}
		
		//sapuseridmanagement(mapping, form, request, response);
		 //Category Setting
		
		ArrayList category1=new ArrayList();
		ArrayList categoryShortName=new ArrayList();
		String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP' AND c_cat_sh_name IN ("+cate+")";
		ResultSet rs2 = ad.selectQuery(cat);
		
		try {
			while (rs2.next())

			{
				category1.add(rs2.getString("c_cat_name"));
				categoryShortName.add(rs2.getString("c_cat_sh_name"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		help.setCategorylist(category1);
		
		help.setCategortShortlist(categoryShortName);
		
		help.setRequestName(reqname);
		ArrayList roleList = new ArrayList();
		ArrayList roleLabelList = new ArrayList();
		String role = "select sap.role,sap.id,sap.department from Role_SAP as sap,location as loc where loc.LOCID = '"+user.getPlantId()+"' AND sap.plant=loc.location_code";
		ResultSet rs21 = ad.selectQuery(role);
		
		try {
			while (rs21.next())

			{
				roleList.add(rs21.getString("id"));
				roleLabelList.add(rs21.getString("Department")+"-"+rs21.getString("Role"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		help.setRolelist(roleList);
		help.setRoleLabelList(roleLabelList);
		ArrayList list = new ArrayList();
		ArrayList idlist = new ArrayList();
		String rr= "select * from sap_Request";
		ResultSet ff=ad.selectQuery(rr);
		try {
			while(ff.next())
			{
				/*IssuesForm h1= new IssuesForm();
				h1.setRequestId(ff.getInt("ID"));
				h1.setRequestName(ff.getString("REQUEST"));
				list.add(h1);*/
				idlist.add(ff.getInt("ID"));
				list.add(ff.getString("REQUEST"));
			}
			help.setRequestlist(list);
			help.setRequestidlist(idlist);
			/*if(list.size()>0)
			{
				request.setAttribute("ll", list);
			}*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return mapping.findForward("saprequestform");
		
	//	return mapping.findForward("sapuseridmanagement");
 }

	
	public ActionForward assignIssueReq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		EssDao ad=new EssDao();
		String selectedReq[]=help.getSelectedRequestNo();
		IssueImpl impl=new IssueImpl();
		for(int a=0;a<selectedReq.length;a++){
		int reqNo=Integer.parseInt(selectedReq[a]);
		String forwardEmpNo=help.getIssAssignedTechn();
		String category="";
		String subCategory="";
		String requesterId="";
		String reqDate="";
		int maxSeqNo=0;
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		String dateNow = ft.format(dNow);
		String updateStatus="update IT_ISSUES set Req_Status='Forwarded' where Req_No='"+reqNo+"'";
		int i=ad.SqlExecuteUpdate(updateStatus);
		if(i>0){
		String getMaxSeq="select MAX(Sequence) from IT_All_ISSUES where Req_Id='"+reqNo+"'";
		ResultSet rsMax=ad.selectQuery(getMaxSeq);
		try{
		   while(rsMax.next()){
			   maxSeqNo=1+rsMax.getInt(1);
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
		String assignTo="";
		String reqStatus="select * from IT_ISSUES where Req_No='"+reqNo+"'";
		ResultSet rs=ad.selectQuery(reqStatus);
		try{
			while(rs.next()){
				category=rs.getString("Req_type");
				subCategory=rs.getString("Sub_Category");
				requesterId=rs.getString("Emp_No");
				reqDate=rs.getString("Req_date");
				assignTo=rs.getString("AssignTo");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		assignTo=assignTo+","+forwardEmpNo;
		help.setAssignTo(assignTo);
		String updateAllIssueStatus="update IT_All_ISSUES set Req_Status='Forwarded',approved_date='"+dateNow+"',Comments='',readStatus=1 where Req_Id='"+reqNo+"' and "
		+ "Technician_Id='"+user.getEmployeeNo()+"'";
		int j=ad.SqlExecuteUpdate(updateAllIssueStatus);
		if(j>0){
	String forwardToOther="INSERT INTO IT_All_ISSUES(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Technician_id,"
			 + "Requester_Id,Comments,Sub_Category,Sequence,Documents,readStatus) values('"+reqNo+"','"+category+"','"+requesterId+"','"+reqDate+"',"
			 + "'','"+forwardEmpNo+"','"+requesterId+"','','"+subCategory+"','"+maxSeqNo+"','','0')";
			 int k=ad.SqlExecuteUpdate(forwardToOther);
			 if(k>0){
				 help.setMessage("Issue has been forwarded successfully.");
				 String updateIssueStatus="update IT_ISSUES set AssignTo='"+assignTo+"' where Req_No='"+reqNo+"' ";
							ad.SqlExecuteUpdate(updateIssueStatus);
							ad.connClose();
				//Mail			
				 ITMail mail=new ITMail();
				 mail.sendMailToApprover(request, reqNo, "Issues", forwardEmpNo,help.getComments()); 
			 }else{
				 help.setMessage("Error..When forwarding issue");
			 }
		}
		}//end of if
		
		}
		currentRecord(mapping, form, request, response);
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward checkReqNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String reqNo=request.getParameter("reqNo");
		try{
		String checkReqNo="select count(*) from IT_ISSUES where Req_No='"+reqNo+"'";
		ResultSet rsCheck=ad.selectQuery(checkReqNo);
		while(rsCheck.next()){
      		if(rsCheck.getInt(1)>0)
      			help.setReqNoStatus(true);
      		
      		if(rsCheck.getInt(1)==0)
      			help.setReqNoStatus(false);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
 		return mapping.findForward("ReqNoStatusAjax");
	}
	
	public ActionForward currentRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		IssueImpl impl=new IssueImpl();
		List IssuesList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		
		int totalRecords=help.getTotalRecords();//21
		int startRecord=help.getStartRecord();//11
		int endRecord=help.getEndRecord();
		
		help.setTotalRecords(totalRecords);
		help.setStartRecord(startRecord);
		help.setEndRecord(endRecord);
		
		
		  String empType="";	
		    //check login User Type	
		    String checkEmpType="select COUNT(*) from IT_Approvers where Approver_ID='"+user.getEmployeeNo()+"' and Category!='IT SERVICES'";
		    ResultSet rsEmpType=ad.selectQuery(checkEmpType);
		    try{
		    while(rsEmpType.next()){
		    	if(rsEmpType.getInt(1)>0){
		    		empType="Technician";
		    	}else{
		    		empType="Requester";
		    	}
		    	help.setEmpType(empType);
		    }
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		String locationID=help.getLocationId();
		help.setLocationId(locationID);
		String chooseType=help.getChooseType();		
		String emptype=help.getEmpType();
	   String requestStatus=help.getRequestStatus();
	   
	   String fromDate=help.getFromDate();
	   String toDate=help.getToDate();
		if(help.getFromDate()==null)
		{
	 	help.setFromDate("");	
		}
		if(help.getFromDate().equalsIgnoreCase(""))
		{
			fromDate="";
		}
		if(!help.getFromDate().equalsIgnoreCase(""))
		{
			 String a[]=help.getFromDate().split("/");
			 fromDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"00:00:00.000";
		}
	   
	   
	   
	   //to datwe
	   
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd 23:59:00.000");
		String dateNow ="";
		if(help.getToDate()==null)
		{
	 	help.setToDate("");	
		}
		if(help.getToDate().equalsIgnoreCase(""))
		{
			toDate = "";	
		}
		if(!help.getToDate().equalsIgnoreCase(""))
		{
			 String a[]=help.getToDate().split("/");
			 toDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"23:59:00.000";
		}

		
	
			
		try{
			if(emptype.equalsIgnoreCase("Technician")){			
				String getAllReq="Select * from (Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
						+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep  "
						+ "where it.Req_No=all_r.Req_Id and all_r.Technician_Id='"+user.getEmployeeNo()+"' and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+user.getEmployeeNo()+"'  and emp.DPTID=dep.DPTID ";
				
			     if(help.getLocationId()==null)
			     {
			    	 help.setLocationId(user.getPlantId());
			     }
			    if(help.getLocationId().equalsIgnoreCase(""))
			    {
			    	help.setLocationId(user.getPlantId());
			    }
			    if(!help.getLocationId().equalsIgnoreCase("All"))
			    {
			    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
			    }
				
			    if(!help.getFromDate().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
			    }
			    
			    if(!help.getMaincategory().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
			    }
			   
			    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
			    }
			    
			    if(!help.getDepartment().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
			    }
				 getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"')t order by t.approved_date desc";
				ResultSet rs=ad.selectQuery(getAllReq);
				String reqnos="";
				while(rs.next()){
				IssuesForm form1=new IssuesForm();
				form1.setRequestNo(rs.getInt("Req_Id"));
				form1.setLocation(rs.getString("LOCID"));
				form1.setEmployeename(rs.getString("EMP_FULLNAME"));
				form1.setCategory(rs.getString("Req_Type"));
				form1.setSubcategory(rs.getString("Sub_Category"));
				form1.setSubject(rs.getString("Subject"));
				String ReqDate=rs.getString("Req_date");
				String a[]=ReqDate.split(" "); 
				ReqDate=a[0];
				String b[]=ReqDate.split("-");
				ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
				form1.setReqDate(ReqDate);
				form1.setRequestStatus(rs.getString("Req_Status"));
				form1.setReqPriority(rs.getString("Req_Priority"));
				form1.setReadStatus(rs.getInt("readStatus"));
				String assignTo=rs.getString("AssignTo");
				String techNames="";
				if(assignTo.contains(","))
				{
		           String a1[]=assignTo.split(",");
		           
		           for(int i=0;i<a1.length;i++)
		           {
		        	   techNames=techNames+ Empname(a1[i])+",";
		           }
				}
				
				else
				{
							
		     	  techNames= Empname(assignTo)+",";
				}
					
				
				if(!(techNames.equalsIgnoreCase(""))){
					techNames=techNames.substring(0, (techNames.length()-1));	
				}
				form1.setAssignTo(techNames);
				if(!(reqnos.contains(Integer.toString(rs.getInt("Req_Id")))))
				{
					IssuesList.add(form1);
				}
				reqnos=	reqnos+","+rs.getInt("Req_Id");
				}
			}
			if(emptype.equalsIgnoreCase("Requester")){
				String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,"
						+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject from IT_ISSUES as it,emp_official_info as emp,Location as loc,DEPARTMENT as dep  where it.Emp_No='"+user.getEmployeeNo()+"'  "
						+ "and emp.PERNR=it.Emp_No and loc.LOCATION_CODE=emp.LOCID  and emp.DPTID=dep.DPTID ";
				if(help.getLocationId()==null)
			     {
			    	 help.setLocationId(user.getPlantId());
			     }
			    if(help.getLocationId().equalsIgnoreCase(""))
			    {
			    	help.setLocationId(user.getPlantId());
			    }
			    if(!help.getLocationId().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
			    }
				
			    if(!help.getFromDate().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
			    }
			    
			    if(!help.getMaincategory().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
			    }
			   
			    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
			    }
			    
			    if(!help.getDepartment().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
			    }
						getAllReq=getAllReq+" group by it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject  ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
						ResultSet rs=ad.selectQuery(getAllReq); 
						while(rs.next()){
							IssuesForm form1=new IssuesForm();
							form1.setRequestNo(rs.getInt("Req_No"));
							form1.setLocation(rs.getString("LOCID"));
							form1.setEmployeename(rs.getString("EMP_FULLNAME"));
							form1.setCategory(rs.getString("Req_Type"));
							form1.setSubcategory(rs.getString("Sub_Category"));
							form1.setSubject(rs.getString("Subject"));
							String ReqDate=rs.getString("Req_date");
							String a[]=ReqDate.split(" "); 
							ReqDate=a[0];
							String b[]=ReqDate.split("-");
							ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							form1.setReqDate(ReqDate);
							form1.setRequestStatus(rs.getString("Req_Status"));
							form1.setReqPriority(rs.getString("Req_Priority"));
							form1.setReadStatus(rs.getInt("read_Status"));
							String assignTo=rs.getString("AssignTo");
							String techNames="";
							if(assignTo.contains(","))
							{
					           String a1[]=assignTo.split(",");
					           
					           for(int i=0;i<a1.length;i++)
					           {
					        	   techNames=techNames+ Empname(a1[i])+",";
					           }
							}
							
							else
							{
										
					     	  techNames= Empname(assignTo)+",";
							}
								
							
							if(!(techNames.equalsIgnoreCase(""))){
								techNames=techNames.substring(0, (techNames.length()-1));	
							}
							form1.setAssignTo(techNames);
							IssuesList.add(form1);
						}
			}
				request.setAttribute("IssuesList", IssuesList);
				ad.connClose();
					
					if(IssuesList.size()==0){
						request.setAttribute("noRecords", "noRecords");
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
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
			
	
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		impl.getTechnicanList(form, request);
		
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward searchByLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		IssueImpl impl=new IssueImpl();
		List IssuesList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesList=impl.getSearchIssuesByLocation(form,request);
		if(IssuesList.size()>0)
			request.setAttribute("IssuesList", IssuesList);
		else
			request.setAttribute("noRecords", "noRecords");
		
		impl.getTechnicanList(form, request);
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		
		return mapping.findForward("displayIssues");
	}
	public ActionForward assignToTechnician(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		/*	select it.Approver_ID,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp,Location as loc where Category!='IT SERVICES' 
		and it.LOCATION_ID=loc.LOCID and emp.LOCID='ML00' and emp.PERNR=it.Approver_ID group by it.Approver_ID,emp.EMP_FULLNAME
*/	
		return mapping.findForward("displayIssues");
	}
	public ActionForward firstLocationIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List IssuesList=new LinkedList();
		try{
			int totalRecords=help.getTotalRecords();//21
			int startRecord=help.getStartRecord();//11
			int endRecord=help.getEndRecord();	
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  help.setTotalRecords(totalRecords);
				  help.setStartRecord(startRecord);
				  help.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  help.setTotalRecords(totalRecords);
					  help.setStartRecord(startRecord);
					  help.setEndRecord(totalRecords);  
				  }
		String chooseType=help.getChooseType();			
		String location=help.getLocationId();
		if(chooseType.equals("All Requests") || chooseType.equals("Open Requests")||chooseType.equals("Completed")){			
			String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
					+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp "
					+ "where it.Req_No=all_r.Req_Id and  it.Location='"+location+"'";
			 if(chooseType.equals("Open Requests")||chooseType.equals("Completed")){
					if(chooseType.equals("Open Requests"))  
						getAllReq=getAllReq+" and it.Req_Status!='Completed'  ";
					if(chooseType.equals("Completed"))  
						getAllReq=getAllReq+" and it.Req_Status='Completed'";
			 }
			 getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
			+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			ResultSet rs=ad.selectQuery(getAllReq);
			while(rs.next()){
			IssuesForm form1=new IssuesForm();
			form1.setRequestNo(rs.getInt("Req_Id"));
			form1.setLocation(rs.getString("LOCID"));
			form1.setEmployeename(rs.getString("EMP_FULLNAME"));
			form1.setCategory(rs.getString("Req_Type"));
			form1.setSubcategory(rs.getString("Sub_Category"));
			form1.setSubject(rs.getString("Subject"));
			String ReqDate=rs.getString("Req_date");
			String a[]=ReqDate.split(" "); 
			ReqDate=a[0];
			String b[]=ReqDate.split("-");
			ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
			form1.setReqDate(ReqDate);
			form1.setRequestStatus(rs.getString("Req_Status"));
			form1.setReqPriority(rs.getString("Req_Priority"));
			form1.setReadStatus(rs.getInt("readStatus"));
			String assignTo=rs.getString("AssignTo");
			String techNames="";
			if(assignTo.contains(","))
			{
	           String a1[]=assignTo.split(",");
	           
	           for(int i=0;i<a1.length;i++)
	           {
	        	   techNames=techNames+ Empname(a1[i])+",";
	           }
			}
			
			else
			{
						
	     	  techNames= Empname(assignTo)+",";
			}
				
			
			if(!(techNames.equalsIgnoreCase(""))){
				techNames=techNames.substring(0, (techNames.length()-1));	
			}
			form1.setAssignTo(techNames);
			IssuesList.add(form1);
			}
		}
		
		request.setAttribute("IssuesList", IssuesList);
		ad.connClose();
		if(totalRecords>10)
		{
			request.setAttribute("nextButton", "nextButton");
		}
		request.setAttribute("disablePreviousButton", "disablePreviousButton");
		request.setAttribute("displayRecordNo", "displayRecordNo");
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getTechnicanList(form, request);
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward firstIssueRecord(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List IssuesList=new LinkedList();
		try{
			int totalRecords=help.getTotalRecords();//21
			int startRecord=help.getStartRecord();//11
			int endRecord=help.getEndRecord();	
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  help.setTotalRecords(totalRecords);
				  help.setStartRecord(startRecord);
				  help.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  help.setTotalRecords(totalRecords);
					  help.setStartRecord(startRecord);
					  help.setEndRecord(totalRecords);  
				  }
			String chooseType=help.getChooseType();		
			String emptype=help.getEmpType();
		   String requestStatus=help.getRequestStatus();
		   
		   String fromDate=help.getFromDate();
		   String toDate=help.getToDate();
			if(help.getFromDate()==null)
			{
		 	help.setFromDate("");	
			}
			if(help.getFromDate().equalsIgnoreCase(""))
			{
				fromDate="";
			}
			if(!help.getFromDate().equalsIgnoreCase(""))
			{
				 String a[]=help.getFromDate().split("/");
				 fromDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"00:00:00.000";
			}
		   
		   
		   
		   //to datwe
		   
			Date dNow = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd 23:59:00.000");
			String dateNow ="";
			if(help.getToDate()==null)
			{
		 	help.setToDate("");	
			}
			if(help.getToDate().equalsIgnoreCase(""))
			{
				toDate = "";	
			}
			if(!help.getToDate().equalsIgnoreCase(""))
			{
				 String a[]=help.getToDate().split("/");
				 toDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"23:59:00.000";
			}

		   
			if(emptype.equalsIgnoreCase("Technician")){			
				String getAllReq="Select * from (Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
						+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp ,DEPARTMENT as dep "
						+ "where it.Req_No=all_r.Req_Id and all_r.Technician_Id='"+user.getEmployeeNo()+"' and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+user.getEmployeeNo()+"'  and emp.DPTID=dep.DPTID ";
				
			     if(help.getLocationId()==null)
			     {
			    	 help.setLocationId(user.getPlantId());
			     }
			    if(help.getLocationId().equalsIgnoreCase(""))
			    {
			    	help.setLocationId(user.getPlantId());
			    }
			    if(!help.getLocationId().equalsIgnoreCase("All"))
			    {
			    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
			    }
				
			    if(!help.getFromDate().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
			    }
			    
			    if(!help.getMaincategory().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
			    }
			   
			    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
			    }
			    if(!help.getDepartment().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
			    }
			    
			 getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"')t order by t.approved_date desc ";
			ResultSet rs=ad.selectQuery(getAllReq);
			String reqnos="";
			while(rs.next()){
			IssuesForm form1=new IssuesForm();
			form1.setRequestNo(rs.getInt("Req_Id"));
			form1.setLocation(rs.getString("LOCID"));
			form1.setEmployeename(rs.getString("EMP_FULLNAME"));
			form1.setCategory(rs.getString("Req_Type"));
			form1.setSubcategory(rs.getString("Sub_Category"));
			form1.setSubject(rs.getString("Subject"));
			String ReqDate=rs.getString("Req_date1");
			/*String a[]=ReqDate.split(" "); 
			ReqDate=a[0];
			String b[]=ReqDate.split("-");
			ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
			form1.setReqDate(ReqDate);
			form1.setRequestStatus(rs.getString("Req_Status"));
			form1.setReqPriority(rs.getString("Req_Priority"));
			form1.setReadStatus(rs.getInt("readStatus"));
			String assignTo=rs.getString("AssignTo");
			String techNames="";
			if(assignTo.contains(","))
			{
	           String a1[]=assignTo.split(",");
	           
	           for(int i=0;i<a1.length;i++)
	           {
	        	   techNames=techNames+ Empname(a1[i])+",";
	           }
			}
			
			else
			{
						
	     	  techNames= Empname(assignTo)+",";
			}
				
			
			if(!(techNames.equalsIgnoreCase(""))){
				techNames=techNames.substring(0, (techNames.length()-1));	
			}
			form1.setAssignTo(techNames);
			if(!(reqnos.contains(Integer.toString(rs.getInt("Req_Id")))))
			{
				IssuesList.add(form1);
			}
			reqnos=	reqnos+","+rs.getInt("Req_Id");
			
			}
		}
			if(emptype.equalsIgnoreCase("Requester")){
				String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,"
						+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject from IT_ISSUES as it,emp_official_info as emp,Location as loc,DEPARTMENT as dep where it.Emp_No='"+user.getEmployeeNo()+"'  "
						+ "and emp.PERNR=it.Emp_No and loc.LOCATION_CODE=emp.LOCID  and emp.DPTID=dep.DPTID ";
				if(help.getLocationId()==null)
			     {
			    	 help.setLocationId(user.getPlantId());
			     }
			    if(help.getLocationId().equalsIgnoreCase(""))
			    {
			    	help.setLocationId(user.getPlantId());
			    }
			    if(!help.getLocationId().equalsIgnoreCase("All"))
			    {
			    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
			    }
				
			    if(!help.getFromDate().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
			    }
			    if(!help.getMaincategory().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
			    }
			   
			    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
			    }
			    
			    if(!help.getDepartment().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
			    }
			    
					getAllReq=getAllReq+"   group by it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
					ResultSet rs=ad.selectQuery(getAllReq); 
					while(rs.next()){
						IssuesForm form1=new IssuesForm();
						form1.setRequestNo(rs.getInt("Req_No"));
						form1.setLocation(rs.getString("LOCID"));
						form1.setEmployeename(rs.getString("EMP_FULLNAME"));
						form1.setCategory(rs.getString("Req_Type"));
						form1.setSubcategory(rs.getString("Sub_Category"));
						form1.setSubject(rs.getString("Subject"));
						String ReqDate=rs.getString("Req_date1");
						/*String a[]=ReqDate.split(" "); 
						ReqDate=a[0];
						String b[]=ReqDate.split("-");
						ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
						form1.setReqDate(ReqDate);
						form1.setRequestStatus(rs.getString("Req_Status"));
						form1.setReqPriority(rs.getString("Req_Priority"));
						form1.setReadStatus(rs.getInt("read_Status"));
						String assignTo=rs.getString("AssignTo");
						String techNames="";
						if(assignTo.contains(","))
						{
				           String a1[]=assignTo.split(",");
				           
				           for(int i=0;i<a1.length;i++)
				           {
				        	   techNames=techNames+ Empname(a1[i])+",";
				           }
						}
						
						else
						{
									
				     	  techNames= Empname(assignTo)+",";
						}
							
						
						if(!(techNames.equalsIgnoreCase(""))){
							techNames=techNames.substring(0, (techNames.length()-1));	
						}
						form1.setAssignTo(techNames);
						IssuesList.add(form1);
					}
		}
		request.setAttribute("IssuesList", IssuesList);
		ad.connClose();
		if(totalRecords>10)
		{
			request.setAttribute("nextButton", "nextButton");
		}
		request.setAttribute("disablePreviousButton", "disablePreviousButton");
		request.setAttribute("displayRecordNo", "displayRecordNo");
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getTechnicanList(form, request);
		return mapping.findForward("displayIssues");
	}
	public ActionForward lastLocationIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List IssuesList=new LinkedList();
		try{
			int totalRecords=help.getTotalRecords();//21
			int startRecord=help.getStartRecord();//11
			int endRecord=help.getEndRecord();	
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 help.setTotalRecords(totalRecords);
			 help.setStartRecord(startRecord);
			 help.setEndRecord(totalRecords);
			 String chooseType=help.getChooseType();
			 String location=help.getLocationId();
			 if(chooseType.equals("All Requests") || chooseType.equals("Open Requests")||chooseType.equals("Completed")){			
				String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
						+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp "
						+ "where it.Req_No=all_r.Req_Id and it.Location='"+location+"'";
				 if(chooseType.equals("Open Requests")||chooseType.equals("Completed")){
						if(chooseType.equals("Open Requests"))  
							getAllReq=getAllReq+" and it.Req_Status!='Completed' ";
						if(chooseType.equals("Completed"))  
							getAllReq=getAllReq+" and it.Req_Status='Completed'";
				 }
				getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
			+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
					ResultSet rs=ad.selectQuery(getAllReq);
					while(rs.next()){
					IssuesForm form1=new IssuesForm();
					form1.setRequestNo(rs.getInt("Req_Id"));
					form1.setLocation(rs.getString("LOCID"));
					form1.setEmployeename(rs.getString("EMP_FULLNAME"));
					form1.setCategory(rs.getString("Req_Type"));
					form1.setSubcategory(rs.getString("Sub_Category"));
					form1.setSubject(rs.getString("Subject"));
					String ReqDate=rs.getString("Req_date");
					String a[]=ReqDate.split(" "); 
					ReqDate=a[0];
					String b[]=ReqDate.split("-");
					ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					form1.setReqDate(ReqDate);
					form1.setRequestStatus(rs.getString("Req_Status"));
					form1.setReqPriority(rs.getString("Req_Priority"));
					form1.setReadStatus(rs.getInt("readStatus"));
					String assignTo=rs.getString("AssignTo");
					String techNames="";
					if(assignTo.contains(","))
					{
			           String a1[]=assignTo.split(",");
			           
			           for(int i=0;i<a1.length;i++)
			           {
			        	   techNames=techNames+ Empname(a1[i])+",";
			           }
					}
					
					else
					{
								
			     	  techNames= Empname(assignTo)+",";
					}
						
					
					if(!(techNames.equalsIgnoreCase(""))){
						techNames=techNames.substring(0, (techNames.length()-1));	
					}
					form1.setAssignTo(techNames);
					IssuesList.add(form1);
					}
				}
				
		request.setAttribute("IssuesList", IssuesList);
		ad.connClose();
		request.setAttribute("disableNextButton", "disableNextButton");
		request.setAttribute("previousButton", "previousButton");
		if(IssuesList.size()<10)
		{
			
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		}
		request.setAttribute("displayRecordNo", "displayRecordNo");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getTechnicanList(form, request);
		return mapping.findForward("displayIssues");
	}
	public ActionForward lastIssueRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List IssuesList=new LinkedList();
		try{
			int totalRecords=help.getTotalRecords();//21
			int startRecord=help.getStartRecord();//11
			int endRecord=help.getEndRecord();	
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 help.setTotalRecords(totalRecords);
			 help.setStartRecord(startRecord);
			 help.setEndRecord(totalRecords);
			 String chooseType=help.getChooseType();		
				String emptype=help.getEmpType();
			   String requestStatus=help.getRequestStatus();
			   
			   String fromDate=help.getFromDate();
			   String toDate=help.getToDate();
				if(help.getFromDate()==null)
				{
			 	help.setFromDate("");	
				}
				if(help.getFromDate().equalsIgnoreCase(""))
				{
					fromDate="";
				}
				if(!help.getFromDate().equalsIgnoreCase(""))
				{
					 String a[]=help.getFromDate().split("/");
					 fromDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"00:00:00.000";
				}
			   
			   
			   
			   //to datwe
			   
				Date dNow = new Date( );
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd 23:59:00.000");
				String dateNow ="";
				if(help.getToDate()==null)
				{
			 	help.setToDate("");	
				}
				if(help.getToDate().equalsIgnoreCase(""))
				{
					toDate = "";	
				}
				if(!help.getToDate().equalsIgnoreCase(""))
				{
					 String a[]=help.getToDate().split("/");
					 toDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"23:59:00.000";
				}

			   
				if(emptype.equalsIgnoreCase("Technician")){			
					String getAllReq="Select * from (Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
							+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp ,DEPARTMENT as dep "
							+ "where it.Req_No=all_r.Req_Id and all_r.Technician_Id='"+user.getEmployeeNo()+"' and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+user.getEmployeeNo()+"'  and emp.DPTID=dep.DPTID ";
					
				     if(help.getLocationId()==null)
				     {
				    	 help.setLocationId(user.getPlantId());
				     }
				    if(help.getLocationId().equalsIgnoreCase(""))
				    {
				    	help.setLocationId(user.getPlantId());
				    }
				    if(!help.getLocationId().equalsIgnoreCase("All"))
				    {
				    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
				    }
					
				    if(!help.getFromDate().equalsIgnoreCase(""))
				    {
				    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
				    }
				
				    if(!help.getMaincategory().equalsIgnoreCase(""))
				    {
				    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
				    }
				   
				    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
				    {
				    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
				    }
				    
				    if(!help.getDepartment().equalsIgnoreCase(""))
				    {
				    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
				    }
				    
				getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"')t order by t.approved_date desc ";
					ResultSet rs=ad.selectQuery(getAllReq);
					String reqnos="";
					while(rs.next()){
					IssuesForm form1=new IssuesForm();
					form1.setRequestNo(rs.getInt("Req_Id"));
					form1.setLocation(rs.getString("LOCID"));
					form1.setEmployeename(rs.getString("EMP_FULLNAME"));
					form1.setCategory(rs.getString("Req_Type"));
					form1.setSubcategory(rs.getString("Sub_Category"));
					form1.setSubject(rs.getString("Subject"));
					String ReqDate=rs.getString("Req_date1");
					/*String a[]=ReqDate.split(" "); 
					ReqDate=a[0];
					String b[]=ReqDate.split("-");
					ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
					form1.setReqDate(ReqDate);
					form1.setRequestStatus(rs.getString("Req_Status"));
					form1.setReqPriority(rs.getString("Req_Priority"));
					form1.setReadStatus(rs.getInt("readStatus"));
					String assignTo=rs.getString("AssignTo");
					String techNames="";
					if(assignTo.contains(","))
					{
			           String a1[]=assignTo.split(",");
			           
			           for(int i=0;i<a1.length;i++)
			           {
			        	   techNames=techNames+ Empname(a1[i])+",";
			           }
					}
					
					else
					{
								
			     	  techNames= Empname(assignTo)+",";
					}
						
					
					if(!(techNames.equalsIgnoreCase(""))){
						techNames=techNames.substring(0, (techNames.length()-1));	
					}
					form1.setAssignTo(techNames);
					if(!(reqnos.contains(Integer.toString(rs.getInt("Req_Id")))))
					{
						IssuesList.add(form1);
					}
					reqnos=	reqnos+","+rs.getInt("Req_Id");
					
					}
				}
				if(emptype.equalsIgnoreCase("Requester")){
					String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,"
							+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject from IT_ISSUES as it,emp_official_info as emp,Location as loc,DEPARTMENT as dep where it.Emp_No='"+user.getEmployeeNo()+"'  "
							+ "and emp.PERNR=it.Emp_No and loc.LOCATION_CODE=emp.LOCID   and emp.DPTID=dep.DPTID ";
					if(help.getLocationId()==null)
				     {
				    	 help.setLocationId(user.getPlantId());
				     }
				    if(help.getLocationId().equalsIgnoreCase(""))
				    {
				    	help.setLocationId(user.getPlantId());
				    }
				    if(!help.getLocationId().equalsIgnoreCase("All"))
				    {
				    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
				    }
					
				    if(!help.getFromDate().equalsIgnoreCase(""))
				    {
				    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
				    }
				    
				    if(!help.getMaincategory().equalsIgnoreCase(""))
				    {
				    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
				    }
				   
				    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
				    {
				    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
				    }
				    
				    if(!help.getDepartment().equalsIgnoreCase(""))
				    {
				    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
				    }
				    
							getAllReq=getAllReq+"   group by it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
							ResultSet rs=ad.selectQuery(getAllReq); 
							while(rs.next()){
								IssuesForm form1=new IssuesForm();
								form1.setRequestNo(rs.getInt("Req_No"));
								form1.setLocation(rs.getString("LOCID"));
								form1.setEmployeename(rs.getString("EMP_FULLNAME"));
								form1.setCategory(rs.getString("Req_Type"));
								form1.setSubcategory(rs.getString("Sub_Category"));
								form1.setSubject(rs.getString("Subject"));
								String ReqDate=rs.getString("Req_date1");
								/*String a[]=ReqDate.split(" "); 
								ReqDate=a[0];
								String b[]=ReqDate.split("-");
								ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
								form1.setReqDate(ReqDate);
								form1.setRequestStatus(rs.getString("Req_Status"));
								form1.setReqPriority(rs.getString("Req_Priority"));
								form1.setReadStatus(rs.getInt("read_Status"));
								String assignTo=rs.getString("AssignTo");
								String techNames="";
								if(assignTo.contains(","))
								{
						           String a1[]=assignTo.split(",");
						           
						           for(int i=0;i<a1.length;i++)
						           {
						        	   techNames=techNames+ Empname(a1[i])+",";
						           }
								}
								
								else
								{
											
						     	  techNames= Empname(assignTo)+",";
								}
									
								
								if(!(techNames.equalsIgnoreCase(""))){
									techNames=techNames.substring(0, (techNames.length()-1));	
								}
								form1.setAssignTo(techNames);
								IssuesList.add(form1);
							}
				}
		request.setAttribute("IssuesList", IssuesList);
		ad.connClose();
		request.setAttribute("disableNextButton", "disableNextButton");
		request.setAttribute("previousButton", "previousButton");
		if(IssuesList.size()<10)
		{
			
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		}
		request.setAttribute("displayRecordNo", "displayRecordNo");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getTechnicanList(form, request);
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward preLocationIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List IssuesList=new LinkedList();
		try{
			int totalRecords=help.getTotalRecords();//21
			int endRecord=help.getStartRecord()-1;//20
			int startRecord=help.getStartRecord()-10;//11
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}
			help.setTotalRecords(totalRecords);
			help.setStartRecord(1);
			help.setEndRecord(10);
			String chooseType=help.getChooseType();	
			String location=help.getLocationId();
			if(chooseType.equals("All Requests") || chooseType.equals("Open Requests")||chooseType.equals("Completed")){			
				String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
				+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp "
				+ "where it.Req_No=all_r.Req_Id  and it.Location='"+location+"'";
				 if(chooseType.equals("Open Requests")||chooseType.equals("Completed")){
						if(chooseType.equals("Open Requests"))  
							getAllReq=getAllReq+" and it.Req_Status!='Completed' ";
						if(chooseType.equals("Completed"))  
							getAllReq=getAllReq+" and it.Req_Status='Completed'";
				 }
				 getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
			+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(getAllReq);
				while(rs.next()){
				IssuesForm form1=new IssuesForm();
				form1.setRequestNo(rs.getInt("Req_Id"));
				form1.setLocation(rs.getString("LOCID"));
				form1.setEmployeename(rs.getString("EMP_FULLNAME"));
				form1.setCategory(rs.getString("Req_Type"));
				form1.setSubcategory(rs.getString("Sub_Category"));
				form1.setSubject(rs.getString("Subject"));
				String ReqDate=rs.getString("Req_date");
				String a[]=ReqDate.split(" "); 
				ReqDate=a[0];
				String b[]=ReqDate.split("-");
				ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
				form1.setReqDate(ReqDate);
				form1.setRequestStatus(rs.getString("Req_Status"));
				form1.setReqPriority(rs.getString("Req_Priority"));
				form1.setReadStatus(rs.getInt("readStatus"));
				String assignTo=rs.getString("AssignTo");
				String techNames="";
				if(assignTo.contains(","))
				{
		           String a1[]=assignTo.split(",");
		           
		           for(int i=0;i<a1.length;i++)
		           {
		        	   techNames=techNames+ Empname(a1[i])+",";
		           }
				}
				
				else
				{
							
		     	  techNames= Empname(assignTo)+",";
				}
					
				
				if(!(techNames.equalsIgnoreCase(""))){
					techNames=techNames.substring(0, (techNames.length()-1));	
				}
				form1.setAssignTo(techNames);
				IssuesList.add(form1);
				}
			}
			
		request.setAttribute("IssuesList", IssuesList);
		ad.connClose();
		help.setTotalRecords(totalRecords);
		help.setStartRecord(startRecord);
		help.setEndRecord(endRecord);
		request.setAttribute("nextButton", "nextButton");
		if(startRecord!=1)
		request.setAttribute("previousButton", "previousButton");
		request.setAttribute("displayRecordNo", "displayRecordNo");
		if(IssuesList.size()<10)
		{
			help.setStartRecord(1);
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getTechnicanList(form, request);
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward previousIssueRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List IssuesList=new LinkedList();
		try{
			int totalRecords=help.getTotalRecords();//21
			int endRecord=help.getStartRecord()-1;//20
			int startRecord=help.getStartRecord()-10;//11
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}
			help.setTotalRecords(totalRecords);
			help.setStartRecord(1);
			help.setEndRecord(10);
			String chooseType=help.getChooseType();		
			String emptype=help.getEmpType();
		   String requestStatus=help.getRequestStatus();
		   
		   String fromDate=help.getFromDate();
		   String toDate=help.getToDate();
			if(help.getFromDate()==null)
			{
		 	help.setFromDate("");	
			}
			if(help.getFromDate().equalsIgnoreCase(""))
			{
				fromDate="";
			}
			if(!help.getFromDate().equalsIgnoreCase(""))
			{
				 String a[]=help.getFromDate().split("/");
				 fromDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"00:00:00.000";
			}
		   
		   
		   
		   //to datwe
		   
			Date dNow = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd 23:59:00.000");
			String dateNow ="";
			if(help.getToDate()==null)
			{
		 	help.setToDate("");	
			}
			if(help.getToDate().equalsIgnoreCase(""))
			{
				toDate = "";	
			}
			if(!help.getToDate().equalsIgnoreCase(""))
			{
				 String a[]=help.getToDate().split("/");
				 toDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"23:59:00.000";
			}
		
			if(emptype.equalsIgnoreCase("Technician")){			
				String getAllReq="Select * from (Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
						+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep "
						+ "where it.Req_No=all_r.Req_Id and all_r.Technician_Id='"+user.getEmployeeNo()+"'  and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+user.getEmployeeNo()+"' and emp.DPTID=dep.DPTID ";
				
			     if(help.getLocationId()==null)
			     {
			    	 help.setLocationId(user.getPlantId());
			     }
			    if(help.getLocationId().equalsIgnoreCase(""))
			    {
			    	help.setLocationId(user.getPlantId());
			    }
			    if(!help.getLocationId().equalsIgnoreCase("All"))
			    {
			    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
			    }
				
			    if(!help.getFromDate().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
			    }
			    
			    
			    if(!help.getMaincategory().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
			    }
			   
			    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
			    }
			    
			    if(!help.getDepartment().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
			    }
			    
				 getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"')t order by t.approved_date desc ";
				ResultSet rs=ad.selectQuery(getAllReq);
				String reqnos="";
				while(rs.next()){
				IssuesForm form1=new IssuesForm();
				form1.setRequestNo(rs.getInt("Req_Id"));
				form1.setLocation(rs.getString("LOCID"));
				form1.setEmployeename(rs.getString("EMP_FULLNAME"));
				form1.setCategory(rs.getString("Req_Type"));
				form1.setSubcategory(rs.getString("Sub_Category"));
				form1.setSubject(rs.getString("Subject"));
				String ReqDate=rs.getString("Req_date1");
			/*	String a[]=ReqDate.split(" "); 
				ReqDate=a[0];
				String b[]=ReqDate.split("-");
				ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
				form1.setReqDate(ReqDate);
				form1.setRequestStatus(rs.getString("Req_Status"));
				form1.setReqPriority(rs.getString("Req_Priority"));
				form1.setReadStatus(rs.getInt("readStatus"));
				String assignTo=rs.getString("AssignTo");
				String techNames="";
				if(assignTo.contains(","))
				{
		           String a1[]=assignTo.split(",");
		           
		           for(int i=0;i<a1.length;i++)
		           {
		        	   techNames=techNames+ Empname(a1[i])+",";
		           }
				}
				
				else
				{
							
		     	  techNames= Empname(assignTo)+",";
				}
					
				
				if(!(techNames.equalsIgnoreCase(""))){
					techNames=techNames.substring(0, (techNames.length()-1));	
				}
				form1.setAssignTo(techNames);
				if(!(reqnos.contains(Integer.toString(rs.getInt("Req_Id")))))
				{
					IssuesList.add(form1);
				}
				reqnos=	reqnos+","+rs.getInt("Req_Id");
				
				}
			}
			if(emptype.equalsIgnoreCase("Requester")){
				String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,"
						+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject from IT_ISSUES as it,emp_official_info as emp,Location as loc,DEPARTMENT as dep where it.Emp_No='"+user.getEmployeeNo()+"'  "
						+ "and emp.PERNR=it.Emp_No and loc.LOCATION_CODE=emp.LOCID  and emp.DPTID=dep.DPTID  ";
				if(help.getLocationId()==null)
			     {
			    	 help.setLocationId(user.getPlantId());
			     }
			    if(help.getLocationId().equalsIgnoreCase(""))
			    {
			    	help.setLocationId(user.getPlantId());
			    }
			    if(!help.getLocationId().equalsIgnoreCase("All"))
			    {
			    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
			    }
				
			    if(!help.getFromDate().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
			    }
			    
			    if(!help.getMaincategory().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
			    }
			   
			    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
			    }
			    
			    if(!help.getDepartment().equalsIgnoreCase(""))
			    {
			    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
			    }
			    
						getAllReq=getAllReq+"  group by it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject  ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
						ResultSet rs=ad.selectQuery(getAllReq); 
					while(rs.next()){
						IssuesForm form1=new IssuesForm();
						form1.setRequestNo(rs.getInt("Req_No"));
						form1.setLocation(rs.getString("LOCID"));
						form1.setEmployeename(rs.getString("EMP_FULLNAME"));
						form1.setCategory(rs.getString("Req_Type"));
						form1.setSubcategory(rs.getString("Sub_Category"));
						form1.setSubject(rs.getString("Subject"));
						String ReqDate=rs.getString("Req_date1");
				/*		String a[]=ReqDate.split(" "); 
						ReqDate=a[0];
						String b[]=ReqDate.split("-");
						ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
						form1.setReqDate(ReqDate);
						form1.setRequestStatus(rs.getString("Req_Status"));
						form1.setReqPriority(rs.getString("Req_Priority"));
						form1.setReadStatus(rs.getInt("read_Status"));
						String assignTo=rs.getString("AssignTo");
						String techNames="";
						if(assignTo.contains(","))
						{
				           String a1[]=assignTo.split(",");
				           
				           for(int i=0;i<a1.length;i++)
				           {
				        	   techNames=techNames+ Empname(a1[i])+",";
				           }
						}
						
						else
						{
									
				     	  techNames= Empname(assignTo)+",";
						}
							
						
						if(!(techNames.equalsIgnoreCase(""))){
							techNames=techNames.substring(0, (techNames.length()-1));	
						}
						form1.setAssignTo(techNames);
						IssuesList.add(form1);
					}
			}
		request.setAttribute("IssuesList", IssuesList);
		ad.connClose();
		help.setTotalRecords(totalRecords);
		help.setStartRecord(startRecord);
		help.setEndRecord(endRecord);
		request.setAttribute("nextButton", "nextButton");
		if(startRecord!=1)
		request.setAttribute("previousButton", "previousButton");
		request.setAttribute("displayRecordNo", "displayRecordNo");
		if(IssuesList.size()<10)
		{
			help.setStartRecord(1);
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getTechnicanList(form, request);
		return mapping.findForward("displayIssues");
	}
	
	
	
	public ActionForward nextLocationIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List IssuesList=new LinkedList();
		try{
			int totalRecords=help.getTotalRecords();//21
			int startRecord=help.getStartRecord();//11
			int endRecord=help.getEndRecord();
			if(totalRecords>endRecord)
			{
				if(totalRecords==endRecord)
				{
					endRecord=totalRecords;
				}
				if(totalRecords>endRecord)
				{
				startRecord=endRecord+1;
				endRecord=endRecord+10;
				}
	String chooseType=help.getChooseType();
	String location=help.getLocationId();
	
	if(chooseType.equals("All Requests") || chooseType.equals("Open Requests")||chooseType.equals("Completed")){			
		String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
				+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp "
				+ "where it.Req_No=all_r.Req_Id and it.Location='"+location+"'";
		 if(chooseType.equals("Open Requests")||chooseType.equals("Completed")){
				if(chooseType.equals("Open Requests"))  
					getAllReq=getAllReq+" and it.Req_Status!='Completed' ";
				if(chooseType.equals("Completed"))  
					getAllReq=getAllReq+" and it.Req_Status='Completed'";
		 }
		 getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
			+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
		ResultSet rs=ad.selectQuery(getAllReq);
		while(rs.next()){
		IssuesForm form1=new IssuesForm();
		form1.setRequestNo(rs.getInt("Req_Id"));
		form1.setLocation(rs.getString("LOCID"));
		form1.setEmployeename(rs.getString("EMP_FULLNAME"));
		form1.setCategory(rs.getString("Req_Type"));
		form1.setSubcategory(rs.getString("Sub_Category"));
		form1.setSubject(rs.getString("Subject"));
		String ReqDate=rs.getString("Req_date");
		String a[]=ReqDate.split(" "); 
		ReqDate=a[0];
		String b[]=ReqDate.split("-");
		ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
		form1.setReqDate(ReqDate);
		form1.setRequestStatus(rs.getString("Req_Status"));
		form1.setReqPriority(rs.getString("Req_Priority"));
		form1.setReadStatus(rs.getInt("readStatus"));
		String assignTo=rs.getString("AssignTo");
		String techNames="";
		if(assignTo.contains(","))
		{
           String a1[]=assignTo.split(",");
           
           for(int i=0;i<a1.length;i++)
           {
        	   techNames=techNames+ Empname(a1[i])+",";
           }
		}
		
		else
		{
					
     	  techNames= Empname(assignTo)+",";
		}
			
		
		if(!(techNames.equalsIgnoreCase(""))){
			techNames=techNames.substring(0, (techNames.length()-1));	
		}
		form1.setAssignTo(techNames);
		IssuesList.add(form1);
		}
	}
	
		request.setAttribute("IssuesList", IssuesList);
		ad.connClose();
		if(IssuesList.size()!=0)
		{
			help.setTotalRecords(totalRecords);
			help.setStartRecord(startRecord);
			help.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		}
		else
		{
			int start=startRecord;
			int end=startRecord;
			
			help.setTotalRecords(totalRecords);
			help.setStartRecord(start);
			help.setEndRecord(end);
			
		}
	 if(IssuesList.size()<10)
	 {
		 help.setTotalRecords(totalRecords);
		 help.setStartRecord(startRecord);
		 help.setEndRecord(startRecord+IssuesList.size()-1);
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
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getTechnicanList(form, request);
		return mapping.findForward("displayIssues");
	}
	public ActionForward nextIssueRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List IssuesList=new LinkedList();
		try{
			int totalRecords=help.getTotalRecords();//21
			int startRecord=help.getStartRecord();//11
			int endRecord=help.getEndRecord();
			if(totalRecords>endRecord)
			{
				if(totalRecords==endRecord)
				{
					endRecord=totalRecords;
				}
				if(totalRecords>endRecord)
				{
				startRecord=endRecord+1;
				endRecord=endRecord+10;
				}
	String chooseType=help.getChooseType();		
	String emptype=help.getEmpType();
   String requestStatus=help.getRequestStatus();
   
   String fromDate=help.getFromDate();
   String toDate=help.getToDate();
	if(help.getFromDate()==null)
	{
 	help.setFromDate("");	
	}
	if(help.getFromDate().equalsIgnoreCase(""))
	{
		fromDate="";
	}
	if(!help.getFromDate().equalsIgnoreCase(""))
	{
		 String a[]=help.getFromDate().split("/");
		 fromDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"00:00:00.000";
	}
   
   
   
   //to datwe
   
	Date dNow = new Date( );
	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd 23:59:00.000");
	String dateNow ="";
	if(help.getToDate()==null)
	{
 	help.setToDate("");	
	}
	if(help.getToDate().equalsIgnoreCase(""))
	{
		toDate = "";	
	}
	if(!help.getToDate().equalsIgnoreCase(""))
	{
		 String a[]=help.getToDate().split("/");
		 toDate=a[2]+"-"+a[1]+"-"+a[0]+" "+"23:59:00.000";
	}

   
	if(emptype.equalsIgnoreCase("Technician")){			
		String getAllReq="Select * from (Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
				+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep "
				+ "where it.Req_No=all_r.Req_Id and all_r.Technician_Id='"+user.getEmployeeNo()+"' and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+user.getEmployeeNo()+"' and emp.DPTID=dep.DPTID ";
		
	     if(help.getLocationId()==null)
	     {
	    	 help.setLocationId(user.getPlantId());
	     }
	    if(help.getLocationId().equalsIgnoreCase(""))
	    {
	    	help.setLocationId(user.getPlantId());
	    }
	    if(!help.getLocationId().equalsIgnoreCase("All"))
	    {
	    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
	    }
		
	    if(!help.getFromDate().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
	    }
	    
	    if(!help.getMaincategory().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
	    }
	   
	    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
	    }
	    
	    if(!help.getDepartment().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
	    }
	
		
	    getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,all_r.approved_date ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"')t order by t.approved_date desc ";
		ResultSet rs=ad.selectQuery(getAllReq);
		String reqnos="";
		while(rs.next()){
		IssuesForm form1=new IssuesForm();
		form1.setRequestNo(rs.getInt("Req_Id"));
		form1.setLocation(rs.getString("LOCID"));
		form1.setEmployeename(rs.getString("EMP_FULLNAME"));
		form1.setCategory(rs.getString("Req_Type"));
		form1.setSubcategory(rs.getString("Sub_Category"));
		form1.setSubject(rs.getString("Subject"));
		String ReqDate=rs.getString("Req_date1");
	/*	String a[]=ReqDate.split(" "); 
		ReqDate=a[0];
		String b[]=ReqDate.split("-");
		ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
		form1.setReqDate(ReqDate);
		form1.setRequestStatus(rs.getString("Req_Status"));
		form1.setReqPriority(rs.getString("Req_Priority"));
		form1.setReadStatus(rs.getInt("readStatus"));
		String assignTo=rs.getString("AssignTo");
		String techNames="";
		if(assignTo.contains(","))
		{
           String a1[]=assignTo.split(",");
           
           for(int i=0;i<a1.length;i++)
           {
        	   techNames=techNames+ Empname(a1[i])+",";
           }
		}
		
		else
		{
					
     	  techNames= Empname(assignTo)+",";
		}
			
		
		if(!(techNames.equalsIgnoreCase(""))){
			techNames=techNames.substring(0, (techNames.length()-1));	
		}
		form1.setAssignTo(techNames);
		
		if(!(reqnos.contains(Integer.toString(rs.getInt("Req_Id")))))
		{
			IssuesList.add(form1);
		}
		reqnos=	reqnos+","+rs.getInt("Req_Id");
		}
	}
	if(emptype.equalsIgnoreCase("Requester")){
		String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,"
				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject from IT_ISSUES as it,emp_official_info as emp,Location as loc,DEPARTMENT as dep where it.Emp_No='"+user.getEmployeeNo()+"'  "
				+ "and emp.PERNR=it.Emp_No and loc.LOCATION_CODE=emp.LOCID and emp.DPTID=dep.DPTID  ";
		if(help.getLocationId()==null)
	     {
	    	 help.setLocationId(user.getPlantId());
	     }
	    if(help.getLocationId().equalsIgnoreCase(""))
	    {
	    	help.setLocationId(user.getPlantId());
	    }
	    if(!help.getLocationId().equalsIgnoreCase("All"))
	    {
	    	getAllReq=getAllReq+" and Location ='"+help.getLocationId()+"' ";
	    }
		
	    if(!help.getFromDate().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
	    }
	    
	    if(!help.getMaincategory().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_type ='"+help.getMaincategory()+"' ";
	    }
	   
	    if(!help.getMainrequestStatus().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_Status ='"+help.getMainrequestStatus()+"' ";
	    }
	    
	    if(!help.getDepartment().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and dep.DPTID ='"+help.getDepartment()+"' ";
	    }
	    
				getAllReq=getAllReq+" group by it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject ) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(getAllReq); 
				while(rs.next()){
					IssuesForm form1=new IssuesForm();
					form1.setRequestNo(rs.getInt("Req_No"));
					form1.setLocation(rs.getString("LOCID"));
					form1.setEmployeename(rs.getString("EMP_FULLNAME"));
					form1.setCategory(rs.getString("Req_Type"));
					form1.setSubcategory(rs.getString("Sub_Category"));
					form1.setSubject(rs.getString("Subject"));
					String ReqDate=rs.getString("Req_date1");
			/*		String a[]=ReqDate.split(" "); 
					ReqDate=a[0];
					String b[]=ReqDate.split("-");
					ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
					form1.setReqDate(ReqDate);
					form1.setRequestStatus(rs.getString("Req_Status"));
					form1.setReqPriority(rs.getString("Req_Priority"));
					form1.setReadStatus(rs.getInt("read_Status"));
					String assignTo=rs.getString("AssignTo");
					String techNames="";
					if(assignTo.contains(","))
					{
			           String a1[]=assignTo.split(",");
			           
			           for(int i=0;i<a1.length;i++)
			           {
			        	   techNames=techNames+ Empname(a1[i])+",";
			           }
					}
					
					else
					{
								
			     	  techNames= Empname(assignTo)+",";
					}
						
					
					if(!(techNames.equalsIgnoreCase(""))){
						techNames=techNames.substring(0, (techNames.length()-1));	
					}
					form1.setAssignTo(techNames);
					IssuesList.add(form1);
				}
	}
		request.setAttribute("IssuesList", IssuesList);
		ad.connClose();
		if(IssuesList.size()!=0)
		{
			help.setTotalRecords(totalRecords);
			help.setStartRecord(startRecord);
			help.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		}
		else
		{
			int start=startRecord;
			int end=startRecord;
			
			help.setTotalRecords(totalRecords);
			help.setStartRecord(start);
			help.setEndRecord(end);
			
		}
	 if(IssuesList.size()<10)
	 {
		 help.setTotalRecords(totalRecords);
		 help.setStartRecord(startRecord);
		 help.setEndRecord(startRecord+IssuesList.size()-1);
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
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getTechnicanList(form, request);
		return mapping.findForward("displayIssues");
	}
	public ActionForward deleteIssue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String reqNo=request.getParameter("reqNo");
		String chooseType=help.getChooseType();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String deleteIssue="delete IT_ISSUES where Req_No='"+reqNo+"' and Req_Status='New' and Emp_No='"+user.getEmployeeNo()+"'";
		int i=ad.SqlExecuteUpdate(deleteIssue);
		List IssuesList=new LinkedList();
		IssueImpl impl=new IssueImpl();
		if(i>0){
			help.setMessage("Issue has been deleted.");
			String deleteIssueAll="delete IT_All_ISSUES where Req_Id='"+reqNo+"'";
			int j=ad.SqlExecuteUpdate(deleteIssueAll);
			if(j>0){
				try{
				 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	 String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
				 	 
				String getFilename="select * from Issues_document where  request_no='"+reqNo+"'";
				ResultSet rsFileName=ad.selectQuery(getFilename);
				while(rsFileName.next())
				{
				 String fileName=rsFileName.getString("file_name");
				 File fileToCreate = new File(filePath, fileName);
				 	boolean test=fileToCreate.delete();
				 	File fileToCreate1 = new File("E:/EMicro Files/IT/Help Desk/Issues/UploadFiles",fileName);
				 	boolean test1=fileToCreate1.delete();
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				String deleteDoc="delete Issues_document where request_no='"+reqNo+"'";
				ad.SqlExecuteUpdate(deleteDoc);
			}
			
		}
	/*	if(chooseType.equals("My All Requests")||chooseType.equals("My Open Requests")||chooseType.equals("My Completed Requests")){
			
			IssuesList=impl.getSearchDetails(form, request);
		}else{
			IssuesList=impl.getAllRequest(user.getEmployeeNo(),form,request);
		}
		if(IssuesList.size()>0)
			request.setAttribute("IssuesList", IssuesList);
		else
			request.setAttribute("noRecords", "noRecords");*/
		
		currentRecord(mapping, form, request, response);
		
		return mapping.findForward("displayIssues");
	}
	public ActionForward modifyIssue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		userDetails(mapping, form, request, response);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String updateIssue="update IT_ISSUES set mode='"+help.getMode()+"',Req_Priority='"+help.getIssuePriority()+"',"
		+ "Subject='"+help.getSubject()+"',Reason='"+help.getReason()+"'  where Req_No='"+help.getRequestNo()+"'";
		int i=ad.SqlExecuteUpdate(updateIssue);
		if(i>0){
			help.setMessage("Issue details has been updated.");
		}else{
			help.setMessage("Error..When Updating Issue details.");
		}
		String getDetails="Select * from IT_ISSUES where Req_No='"+help.getRequestNo()+"'";
		ResultSet rs1=ad.selectQuery(getDetails);
		try{
		while(rs1.next()){
			help.setRequestStatus(rs1.getString("Req_Status"));
			help.setMode(rs1.getString("mode"));
			help.setIssuePriority(rs1.getString("Req_Priority"));
			help.setCategory(rs1.getString("Req_type"));
			help.setSelectedSubCategory(rs1.getString("Sub_Category"));
			help.setTechnicianName(rs1.getString("Pending_approver"));
			help.setSubject(rs1.getString("Subject"));
			help.setReason(rs1.getString("Reason"));
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	return mapping.findForward("editIssueDetails");
	}
	public ActionForward deleteModifyDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=help.getRequestNo();
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
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						String fileName="";
						String getFilename="select * from Issues_document where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from Issues_document  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					help.setMessage("Document Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	File fileToCreate1 = new File("E:/EMicro Files/IT/Help Desk/Issues/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from Issues_document where user_id='"+help.getEmployeeno()+"' and request_no='"+reqNo+"'";
			ResultSet rs=ad.selectQuery(sql1);
			try{
				IssuesForm help1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					help1=new IssuesForm();
					help1.setFileName(rs.getString("file_name"));
					help1.setId(rs.getString("id"));
					a1.add(help1);
				}
				request.setAttribute("documentDetails", a1);
			}catch(Exception e){
				e.printStackTrace();
			}
			userDetails(mapping, form, request, response);
			String getDetails="Select * from IT_ISSUES where Req_No='"+help.getRequestNo()+"'";
			ResultSet rs1=ad.selectQuery(getDetails);
			try{
			while(rs1.next()){
				help.setRequestStatus(rs1.getString("Req_Status"));
				help.setMode(rs1.getString("mode"));
				help.setIssuePriority(rs1.getString("Req_Priority"));
				help.setCategory(rs1.getString("Req_type"));
				help.setSelectedSubCategory(rs1.getString("Sub_Category"));
				help.setTechnicianName(rs1.getString("Pending_approver"));
				help.setSubject(rs1.getString("Subject"));
				help.setReason(rs1.getString("Reason"));
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return mapping.findForward("editIssueDetails");
		}
	
	public ActionForward uploadModifyDocument(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		
		FormFile documentFile=help.getDocumentFile();
		String documentName=documentFile.getFileName();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 String filePath="";
		 
		 String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
			int filesize=documentFile.getFileSize();
			
			if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
			{
		try{
		 byte[] size=documentFile.getFileData();
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	 	String fileName = documentFile.getFileName();
	     String extension=documentName.substring(dot,length);
 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
 	 Properties props = new Properties();
 	 props.load(in);
	 in.close();
 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
 	 filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
 	 
 	 
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
			String filePath1 = "E:/EMicro Files/IT/Help Desk/Issues/UploadFiles";
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
		try{
			int reqNo=help.getRequestNo();
			String sql9="select count(*) from Issues_document  where  file_name='"+documentFile.getFileName()+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				help.setMessage("File name is exist.Please choose another file");
			}
			else
			{
				String sql="insert into Issues_document(request_no,user_id,file_name)" +
				"values('"+reqNo+"','"+help.getEmployeeno()+"','"+documentFile.getFileName()+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					help.setMessage("Documents Uploaded Successfully");
					}
			}
		String sql1="select * from Issues_document where request_no='"+reqNo+"' and user_id='"+help.getEmployeeno()+"' ";
		ResultSet rs=ad.selectQuery(sql1);
		IssuesForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs.next()) {
				leaveForm1=new IssuesForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getString("id"));
				leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
		
		}catch(Exception e){
			e.printStackTrace();
		}
			}else{
				help.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
				String sql1="select * from Issues_document where request_no='"+help.getRequestNo()+"' and user_id='"+help.getEmployeeno()+"' ";
				ResultSet rs=ad.selectQuery(sql1);
				IssuesForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					try {
						while(rs.next()) {
							leaveForm1=new IssuesForm();
							leaveForm1.setFileName(rs.getString("file_name"));
							leaveForm1.setId(rs.getString("id"));
							leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
							a1.add(leaveForm1);
						}
						request.setAttribute("documentDetails", a1);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					request.setAttribute("documentDetails", a1);
						}
			userDetails(mapping, form, request, response);
			String getDetails="Select * from IT_ISSUES where Req_No='"+help.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getDetails);
			try{
			while(rs.next()){
				help.setRequestStatus(rs.getString("Req_Status"));
				help.setMode(rs.getString("mode"));
				help.setIssuePriority(rs.getString("Req_Priority"));
				help.setCategory(rs.getString("Req_type"));
				help.setSelectedSubCategory(rs.getString("Sub_Category"));
				help.setTechnicianName(rs.getString("Pending_approver"));
				help.setSubject(rs.getString("Subject"));
				help.setReason(rs.getString("Reason"));
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		return mapping.findForward("editIssueDetails");
		}
	public ActionForward editIssueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		int  reqNo=Integer.parseInt(request.getParameter("RequestNo"));
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		help.setRequestNo(reqNo);
		help.setRequestNumber(request.getParameter("RequestNo"));
		help.setEmployeeno(user.getEmployeeNo());
		userDetails(mapping, form, request, response);
		String getDetails="Select * from IT_ISSUES where Req_No='"+reqNo+"'";
		ResultSet rs=ad.selectQuery(getDetails);
		try{
		while(rs.next()){
			help.setRequestStatus(rs.getString("Req_Status"));
			help.setMode(rs.getString("mode"));
			help.setIssuePriority(rs.getString("Req_Priority"));
			help.setCategory(rs.getString("Req_type"));
			help.setSelectedSubCategory(rs.getString("Sub_Category"));
			help.setTechnicianName(rs.getString("Pending_approver"));
			help.setSubject(rs.getString("Subject"));
			help.setReason(rs.getString("Reason"));
		}
		String filePath="";
		InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 Properties props = new Properties();
	 	 props.load(in);
		 in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	 	 filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
		String sql1="select * from Issues_document where request_no='"+reqNo+"' and user_id='"+help.getEmployeeno()+"' ";
		ResultSet rs1=ad.selectQuery(sql1);
		IssuesForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs1.next()) {
				leaveForm1=new IssuesForm();
				leaveForm1.setFileName(rs1.getString("file_name"));
				leaveForm1.setId(rs1.getString("id"));
				leaveForm1.setFileFullPath(filePath+"/"+rs1.getString("file_name"));
				a1.add(leaveForm1);
			}
			if(a1.size()>0)
			request.setAttribute("documentDetails", a1);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward("editIssueDetails");
	}
	public ActionForward replyToTechnican(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		int reqNo=help.getRequestNo();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		String dateNow = ft.format(dNow);
		String updateStatus="update IT_ISSUES set Req_Status='"+help.getSelectedIssueStatus()+"' where Req_No='"+reqNo+"'";
		int  i=ad.SqlExecuteUpdate(updateStatus);
		int transNo=help.getTransNo();
		if(i>0){
		
			String insertIntoTrans="INSERT INTO IT_Issue_Trans(Req_No,Emp_No,Req_Status,Transaction_Date,Remarks,Status,Sequence) "
			+ "values('"+reqNo+"','"+user.getEmployeeNo()+"','"+help.getSelectedIssueStatus()+"','"+dateNow+"','"+help.getComments()+"','"+help.getSelectedIssueStatus()+"','"+transNo+"')";
			ad.SqlExecuteUpdate(insertIntoTrans);
			help.setMessage("Ticket No. '"+reqNo+"' has been Updated with the status : '"+help.getSelectedIssueStatus()+"'");
			
			 updateStatus="update IT_All_ISSUES set readStatus=0 where Req_Id='"+reqNo+"' ";
			ad.SqlExecuteUpdate(updateStatus);
			
			//Mail			
			 ITMail mail=new ITMail();
			 String approver="";
			 String reqDetails="select Req_No,Req_date,Emp_No,Req_type,Sub_Category,Subject,Req_Priority,"
					 	+ "(CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo), charindex(',', reverse(AssignTo)) -1))ELSE AssignTo END) as Pending_approver,Req_Status from IT_ISSUES where Req_No='"+reqNo+"'";
					 ResultSet rs=ad.selectQuery(reqDetails);
					 try{
					 while(rs.next()){
							approver=rs.getString("Pending_approver");
					 }
					 rs.close();
					 ad.connClose();
					 }catch(Exception e){
						 e.printStackTrace();
					 }
					
			 mail.sendMailToApprover(request, reqNo, "Issues", approver,help.getComments());
		}
		String empType="";
		String pendingtechn="";
		//check emp type
		String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo from IT_ISSUES as it,IT_All_ISSUES as all_r "
		+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
		ResultSet rs=ad.selectQuery(checkEmp);
		try{
		while(rs.next()){	
			 if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
				empType="Technician";
				request.setAttribute("showTechnReply", "showTechnReply");
			}
			 else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
				empType="Requester";
				request.setAttribute("showReqReply", "showReqReply");
			}else{
				empType="Other";
			}
			help.setEmpType(empType);
			
			
			if(rs.getString("AssignTo").contains(","))
			{
			String asgn[]=rs.getString("AssignTo").split(",");
			int asgnlength=asgn.length;
			 pendingtechn=asgn[asgnlength-1];
			
			}
			else
			{
				pendingtechn=rs.getString("AssignTo");
			}
		}
		
		if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
		 {
			 request.setAttribute("submittbuton", "submittbuton");
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		help.setComments("");
		help.setSelectedIssueStatus("");
		IssueImpl impl=new IssueImpl();
		impl.getRequesterDetails(reqNo, form, request);
		impl.getApprovalStatus(reqNo, form, request,user.getEmployeeNo());
		impl.getConversationList(reqNo, form, request);
		help.setComments("");
		return mapping.findForward("pickupSelectedIssue");
	}
	public ActionForward searchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		IssueImpl impl=new IssueImpl();
		List IssuesList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesList=impl.getSearchDetails(form,request);
		
		if(IssuesList.size()>0)
			request.setAttribute("IssuesList", IssuesList);
		else
			request.setAttribute("noRecords", "noRecords");
		
		impl.getTechnicanList(form, request);
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward forwardIssue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		int reqNo=help.getRequestNo();
		String forwardEmpNo=help.getForwardEmpId();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String category="";
		String subCategory="";
		String requesterId="";
		String reqDate="";
		int maxSeqNo=0;
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		String dateNow = ft.format(dNow);
		 int transNo=help.getTransNo();
		String updateStatus="update IT_ISSUES set Req_Status='Forwarded' where Req_No='"+reqNo+"'";
		int i=ad.SqlExecuteUpdate(updateStatus);
		if(i>0){
		String getMaxSeq="select MAX(Sequence) from IT_All_ISSUES where Req_Id='"+reqNo+"'";
		ResultSet rsMax=ad.selectQuery(getMaxSeq);
		try{
		   while(rsMax.next()){
			   maxSeqNo=1+rsMax.getInt(1);
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
		String assignTo="";
		String reqStatus="select * from IT_ISSUES where Req_No='"+reqNo+"'";
		ResultSet rs=ad.selectQuery(reqStatus);
		try{
			while(rs.next()){
				category=rs.getString("Req_type");
				subCategory=rs.getString("Sub_Category");
				requesterId=rs.getString("Emp_No");
				reqDate=rs.getString("Req_date");
				assignTo=rs.getString("AssignTo");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		if(!requesterId.equalsIgnoreCase(forwardEmpNo))
		{
		assignTo=assignTo.trim()+","+forwardEmpNo.trim();
		}
		help.setAssignTo(assignTo);
		String updateAllIssueStatus="update IT_All_ISSUES set Req_Status='Forwarded',approved_date='"+dateNow+"',Comments='"+help.getComments()+"',readStatus=1 where Req_Id='"+reqNo+"' and "
		+ "Technician_Id='"+user.getEmployeeNo()+"'";
		int j=ad.SqlExecuteUpdate(updateAllIssueStatus);
		if(j>0){
	String forwardToOther="INSERT INTO IT_All_ISSUES(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Technician_id,"
			 + "Requester_Id,Comments,Sub_Category,Sequence,Documents,readStatus) values('"+reqNo+"','"+category+"','"+requesterId+"','"+reqDate+"',"
			 + "'','"+forwardEmpNo+"','"+requesterId+"','','"+subCategory+"','"+maxSeqNo+"','Forwarded','0')";
			 int k=ad.SqlExecuteUpdate(forwardToOther);
			 if(k>0){
				 help.setMessage("Issue has been forwarded successfully.");
				 String updateIssueStatus="update IT_ISSUES set AssignTo='"+assignTo+"',Escalated_Level=0 where Req_No='"+reqNo+"' ";
							ad.SqlExecuteUpdate(updateIssueStatus);
				//Mail			
				 ITMail mail=new ITMail();
				 mail.sendMailToApprover(request, reqNo, "Issues", forwardEmpNo,help.getComments()); 
				 
		
						 
			 }else{
				 help.setMessage("Error..When forwarding issue");
			 }
		}
		}//end of if
		
		String empType="";
		String pendingtechn="";
		//check emp type
		String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo from IT_ISSUES as it,IT_All_ISSUES as all_r "
		+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id ";
		ResultSet rs=ad.selectQuery(checkEmp);
		try{
		while(rs.next()){	
			 if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
				empType="Technician";
				request.setAttribute("showTechnReply", "showTechnReply");
			}
			 else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
				empType="Requester";
				request.setAttribute("showReqReply", "showReqReply");
			}else{
				empType="Other";
			}
			help.setEmpType(empType);
			
			if(rs.getString("AssignTo").contains(","))
			{
			String asgn[]=rs.getString("AssignTo").split(",");
			int asgnlength=asgn.length;
			 pendingtechn=asgn[asgnlength-1];
			
			}
			else
			{
				pendingtechn=rs.getString("AssignTo");
			}
		}
		
		if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
		 {
			 request.setAttribute("submittbuton", "submittbuton");
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		IssueImpl impl=new IssueImpl();
		impl.getRequesterDetails(reqNo, form, request);
		impl.getApprovalStatus(reqNo, form, request,user.getEmployeeNo());
		
		help.setTransNo(transNo);
		return mapping.findForward("pickupSelectedIssue");
	}
	public ActionForward resolveIssue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		
		if(!help.getForwardEmpId().equalsIgnoreCase(""))
		{
	  forwardIssue(mapping, form, request, response);
		}
		
		int reqNo=help.getRequestNo();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
		String dateNow = ft.format(dNow);
		int transNo=help.getTransNo();
		String updateStatus="update IT_ISSUES set approved_date='"+dateNow+"',Req_Status='"+help.getSelectedIssueStatus()+"',read_Status=0 where Req_No='"+reqNo+"'";
		int  i=ad.SqlExecuteUpdate(updateStatus);
		if(i>0){
		String updateAll_ITStatus="update IT_All_ISSUES set approved_date='"+dateNow+"',Req_Status='"+help.getSelectedIssueStatus()+"',"
		+ "Comments='"+help.getComments()+"' where Req_Id='"+reqNo+"' and Technician_Id='"+user.getEmployeeNo()+"' and Req_Status!='Forwarded'";
		int j=ad.SqlExecuteUpdate(updateAll_ITStatus);
		help.setMessage("Ticket No. '"+reqNo+"' has been Updated with the status : '"+help.getSelectedIssueStatus()+"'");
		ad.connClose();
		ITMail mail=new ITMail();
		String reqester="";
		mail.sendMailToRequester(request, reqNo, "Issues", user.getEmployeeNo(),help.getComments());
	
	
			
			String insertIntoTrans="INSERT INTO IT_Issue_Trans(Req_No,Emp_No,Req_Status,Transaction_Date,Remarks,Sequence,Status) "
			+ "values('"+reqNo+"','"+user.getEmployeeNo()+"','"+help.getSelectedIssueStatus()+"','"+dateNow+"','"+help.getComments()+"','"+transNo+"','"+help.getSelectedIssueStatus()+"')";
			ad.SqlExecuteUpdate(insertIntoTrans);
		
		}
		String empType="";
		String pendingtechn="";
		//check emp type
		String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo from IT_ISSUES as it,IT_All_ISSUES as all_r "
		+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
		ResultSet rs=ad.selectQuery(checkEmp);
		try{
		while(rs.next()){	
			 if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
				empType="Technician";
				request.setAttribute("showTechnReply", "showTechnReply");
			}
			 else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
				empType="Requester";
				request.setAttribute("showReqReply", "showReqReply");
			}else{
				empType="Other";
			}
			 
			 
			help.setEmpType(empType);
			
			if(rs.getString("AssignTo").contains(","))
			{
			String asgn[]=rs.getString("AssignTo").split(",");
			int asgnlength=asgn.length;
			 pendingtechn=asgn[asgnlength-1];
			
			}
			else
			{
				pendingtechn=rs.getString("AssignTo");
			}
		}
		
		if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
		 {
			 request.setAttribute("submittbuton", "submittbuton");
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		help.setComments("");
		help.setSelectedIssueStatus("");
		IssueImpl impl=new IssueImpl();
		impl.getRequesterDetails(reqNo, form, request);
		impl.getApprovalStatus(reqNo, form, request,user.getEmployeeNo());
		impl.getConversationList(reqNo, form, request);
		help.setComments("");
		help.setTransNo(transNo);
		return mapping.findForward("pickupSelectedIssue");
	}
	public ActionForward deleteSolutionDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=help.getRequestNo();
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
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						String fileName="";
						String getFilename="select * from Issues_solution_document where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from Issues_solution_document  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					help.setMessage("Document Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	File fileToCreate1 = new File("E:/EMicro Files/IT/Help Desk/Issues/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from Issues_solution_document where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqNo+"'";
			ResultSet rs=ad.selectQuery(sql1);
			try{
				IssuesForm help1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					help1=new IssuesForm();
					help1.setFileName(rs.getString("file_name"));
					help1.setId(rs.getString("id"));
					a1.add(help1);
				}
				request.setAttribute("issueDocs", a1);
			}catch(Exception e){
				e.printStackTrace();
			}
			String empType="";
			String pendingtechn="";
			//check emp type
			String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo from IT_ISSUES as it,IT_All_ISSUES as all_r "
			+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
			ResultSet rs1=ad.selectQuery(checkEmp);
			try{
			while(rs1.next()){	
				 if(user.getEmployeeNo().equals(rs1.getString("Technician_Id"))){
						empType="Technician";
						request.setAttribute("showTechnReply", "showTechnReply");
					}
					 else if(user.getEmployeeNo().equals(rs1.getString("Emp_No"))){
						empType="Requester";
						request.setAttribute("showReqReply", "showReqReply");
					}else{
						empType="Other";
					}
					help.setEmpType(empType);
					
					if(rs.getString("AssignTo").contains(","))
					{
					String asgn[]=rs.getString("AssignTo").split(",");
					int asgnlength=asgn.length;
					 pendingtechn=asgn[asgnlength-1];
					
					}
					else
					{
						pendingtechn=rs.getString("AssignTo");
					}
				}
			
			if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
			 {
				 request.setAttribute("submittbuton", "submittbuton");
			 }
			}catch(Exception e){
				e.printStackTrace();
			}
			IssueImpl impl=new IssueImpl();
			impl.getRequesterDetails(reqNo, form, request);
			impl.getConversationList(reqNo, form, request);
			impl.getApprovalStatus(reqNo, form, request, user.getEmployeeNo());
		return mapping.findForward("pickupSelectedIssue");
	}
	public ActionForward uploadSolutionDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		int reqNo=help.getRequestNo();
		FormFile documentFile=help.getDocumentFile();
		String documentName=documentFile.getFileName();
		if(documentName.contains("'"))
		{
		documentName.replace("'", "`");
		documentFile.setFileName(documentName);
		}
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 String filePath="";
		 int transNo=0;
		 String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
			int filesize=documentFile.getFileSize();
			
			if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls"))||ext.equalsIgnoreCase("txt") && (filesize<1048576))
			{
		try{
		 byte[] size=documentFile.getFileData();
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	 	String fileName = documentFile.getFileName();
	     String extension=documentName.substring(dot,length);
 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
 	 Properties props = new Properties();
 	 props.load(in);
	 in.close();
 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
 	 filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
 	  
 	 
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
			String filePath1 = "E:/EMicro Files/IT/Help Desk/Issues/UploadFiles";
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
		
		try{
			
			String sql9="select count(*) from Issues_solution_document  where  file_name='"+documentFile.getFileName()+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				help.setMessage("File name is exist.Please choose another file");
			}
			else
			{
				//get Transaction No
				
				String getMaxTrans="select max(Sequence) from IT_Issue_Trans where Req_No='"+reqNo+"'";
				ResultSet rsMax=ad.selectQuery(getMaxTrans);
				while(rsMax.next()){
					transNo=rsMax.getInt(1)+1;
				}
				
				String sql="insert into Issues_solution_document(request_no,user_id,file_name,sequence_no)" +
				"values('"+reqNo+"','"+user.getEmployeeNo()+"','"+documentFile.getFileName()+"','"+transNo+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
				{
				help.setMessage("Documents Uploaded Successfully");
				help.setTransNo(transNo);
				}
			}
		String sql1="select * from Issues_solution_document where request_no='"+reqNo+"' and user_id='"+user.getEmployeeNo()+"' ";
		ResultSet rs=ad.selectQuery(sql1);
		IssuesForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs.next()) {
				leaveForm1=new IssuesForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getString("id"));
				leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("issueDocs", a1);
		
		}catch(Exception e){
			e.printStackTrace();
		}
			}else{
				help.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
				String sql1="select * from Issues_solution_document where request_no='"+reqNo+"' and user_id='"+user.getEmployeeNo()+"' ";
				ResultSet rs=ad.selectQuery(sql1);
				IssuesForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					try {
						while(rs.next()) {
							leaveForm1=new IssuesForm();
							leaveForm1.setFileName(rs.getString("file_name"));
							leaveForm1.setId(rs.getString("id"));
							leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
							a1.add(leaveForm1);
						}
						request.setAttribute("issueDocs", a1);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					request.setAttribute("issueDocs", a1);
						}
			
			String empType="";
			String pendingtechn="";
			//check emp type
			String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo from IT_ISSUES as it,IT_All_ISSUES as all_r "
			+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
			ResultSet rs=ad.selectQuery(checkEmp);
			try{
			while(rs.next()){	
				 if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
						empType="Technician";
						request.setAttribute("showTechnReply", "showTechnReply");
					}
					 else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
						empType="Requester";
						request.setAttribute("showReqReply", "showReqReply");
					}else{
						empType="Other";
					}
					help.setEmpType(empType);

					if(rs.getString("AssignTo").contains(","))
							{
							String asgn[]=rs.getString("AssignTo").split(",");
							int asgnlength=asgn.length;
							 pendingtechn=asgn[asgnlength-1];
							
							}
							else
							{
								pendingtechn=rs.getString("AssignTo");
							}
							
				}
			if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
			 {
				 request.setAttribute("submittbuton", "submittbuton");
			 }
			}catch(Exception e){
				e.printStackTrace();
			}
			
			IssueImpl impl=new IssueImpl();
			impl.getRequesterDetails(reqNo, form, request);
			impl.getConversationList(reqNo, form, request);
			impl.getApprovalStatus(reqNo, form, request, user.getEmployeeNo());
		return mapping.findForward("pickupSelectedIssue");
		}
	public ActionForward displayListUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm masterForm = (IssuesForm) form;
		String reqFiled=request.getParameter("reqFiled");
		masterForm.setReqFiled(reqFiled);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		String plantID=user.getPlantId();
		
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);	
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			masterForm.setDepartmentIdList(departmentIdList);
			masterForm.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		
		return mapping.findForward("displayITEnggs");
	}
	public ActionForward viewIssueDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		IssueImpl impl=new IssueImpl();
		int reqNo=Integer.parseInt(request.getParameter("ReqNo"));
		String empType="";
		String pendingtechn="";
		//check emp type
		String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo from IT_ISSUES as it,IT_All_ISSUES as all_r "
		+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
		ResultSet rs=ad.selectQuery(checkEmp);
		try{
		while(rs.next()){	
			 if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
				empType="Technician";
				request.setAttribute("showTechnReply", "showTechnReply");
			}
			 else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
				empType="Requester";
				request.setAttribute("showReqReply", "showReqReply");
			}else{
				empType="Other";
			}
			help.setEmpType(empType);
			

			if(rs.getString("AssignTo").contains(","))
					{
					String asgn[]=rs.getString("AssignTo").split(",");
					int asgnlength=asgn.length;
					 pendingtechn=asgn[asgnlength-1];
					
					}
					else
					{
						pendingtechn=rs.getString("AssignTo");
					}
					
			 
			
		}
		
		if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
		 {
			 request.setAttribute("submittbuton", "submittbuton");
		 }
		}catch(Exception e){
			e.printStackTrace();
		}
	    if(empType.equals("Other"))
	    	request.setAttribute("showBackButton", "showBackButton");
		
		impl.getRequesterDetails(reqNo,form,request);
		impl.getApprovalStatus(reqNo, form, request,user.getEmployeeNo());
		impl.getConversationList(reqNo, form, request);
		return mapping.findForward("pickupSelectedIssue");
	}
	public ActionForward pickupSelectedIssue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {/*
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String selectedReq[]=help.getSelectedRequestNo();
		IssueImpl impl=new IssueImpl();
		int reqNo=Integer.parseInt(selectedReq[0]);
		String empType="";
		String pendingtechn="";
		//check emp type
		String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo from IT_ISSUES as it,IT_All_ISSUES as all_r "
		+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
		ResultSet rs=ad.selectQuery(checkEmp);
		try{
		while(rs.next()){	
			if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
				empType="Technician";
				request.setAttribute("showTechnReply", "showTechnReply");
			}else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
				empType="Requester";
				request.setAttribute("showReqReply", "showReqReply");
			}else{
				empType="Other";
				request.setAttribute("showBackButton", "showBackButton");
			}
			help.setEmpType(empType);
			
			
			if(rs.getString("AssignTo").contains(","))
			{
			String asgn[]=rs.getString("AssignTo").split(",");
			int asgnlength=asgn.length;
			 pendingtechn=asgn[asgnlength-1];
			
			}
			else
			{
				pendingtechn=rs.getString("AssignTo");
			}
		
			 
			
		}
		
		
		if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
		 {
			 request.setAttribute("submittbuton", "submittbuton");
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		impl.getRequesterDetails(reqNo,form,request);
		impl.getApprovalStatus(reqNo, form, request,user.getEmployeeNo());
		impl.getConversationList(reqNo, form, request);
		return mapping.findForward("pickupSelectedIssue");
	*/
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String selectedReq[]=help.getSelectedRequestNo();
		IssueImpl impl=new IssueImpl();
		int reqNo=Integer.parseInt(selectedReq[0]);
		String empType="";
		String pendingtechn="";
		//check emp type
		String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo,it.Req_Status from IT_ISSUES as it,IT_All_ISSUES as all_r "
		+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
		ResultSet rs=ad.selectQuery(checkEmp);
		try{
		while(rs.next()){	
			if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
				empType="Technician";
				if(rs.getString("Req_Status").equalsIgnoreCase("ON_Behalf")){
					request.setAttribute("showReqReply1", "showReqReply1");
				}else{
				request.setAttribute("showTechnReply", "showTechnReply");
				}
			}else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
				empType="Requester";
				if(rs.getString("Req_Status").equalsIgnoreCase("ON_Behalf")){
					request.setAttribute("showReqReply1", "showReqReply1");
				}else{
				request.setAttribute("showReqReply", "showReqReply");
				}
			}else{
				empType="Other";
				request.setAttribute("showBackButton", "showBackButton");
			}
			help.setEmpType(empType);
			
			
			if(rs.getString("AssignTo").contains(","))
			{
			String asgn[]=rs.getString("AssignTo").split(",");
			int asgnlength=asgn.length;
			 pendingtechn=asgn[asgnlength-1];
			
			}
			else
			{
				pendingtechn=rs.getString("AssignTo");
			}
		
			 
			
		}
		
		
		if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
		 {
			 request.setAttribute("submittbuton", "submittbuton");
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		impl.getRequesterDetails(reqNo,form,request);
		impl.getApprovalStatus(reqNo, form, request,user.getEmployeeNo());
		impl.getConversationList(reqNo, form, request);
		return mapping.findForward("pickupSelectedIssue");
	
		}
	
	public ActionForward pickuponbehalfSelectedIssue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {/*
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String selectedReq[]=help.getSelectedRequestNo();
		IssueImpl impl=new IssueImpl();
		int reqNo=Integer.parseInt(selectedReq[0]);
		String empType="";
		String pendingtechn="";
		//check emp type
		String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo from IT_ISSUES as it,IT_All_ISSUES as all_r "
		+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
		ResultSet rs=ad.selectQuery(checkEmp);
		try{
		while(rs.next()){	
			if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
				empType="Technician";
				request.setAttribute("showTechnReply", "showTechnReply");
			}else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
				empType="Requester";
				request.setAttribute("showReqReply", "showReqReply");
			}else{
				empType="Other";
				request.setAttribute("showBackButton", "showBackButton");
			}
			help.setEmpType(empType);
			
			
			if(rs.getString("AssignTo").contains(","))
			{
			String asgn[]=rs.getString("AssignTo").split(",");
			int asgnlength=asgn.length;
			 pendingtechn=asgn[asgnlength-1];
			
			}
			else
			{
				pendingtechn=rs.getString("AssignTo");
			}
		
			 
			
		}
		
		
		if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
		 {
			 request.setAttribute("submittbuton", "submittbuton");
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		impl.getRequesterDetails(reqNo,form,request);
		impl.getApprovalStatus(reqNo, form, request,user.getEmployeeNo());
		impl.getConversationList(reqNo, form, request);
		return mapping.findForward("pickupSelectedIssue");
	*/
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		//String selectedReq[]=help.getSelectedRequestNo();
		IssueImpl impl=new IssueImpl();
		int reqNo=Integer.parseInt(request.getParameter("requstNo"));
		String empType="";
		String pendingtechn="";
		//check emp type
		String checkEmp="select it.Emp_No,all_r.Technician_Id,it.AssignTo,it.Req_Status from IT_ISSUES as it,IT_All_ISSUES as all_r "
		+ "where Req_No='"+reqNo+"' and it.Req_No=all_r.Req_Id";
		ResultSet rs=ad.selectQuery(checkEmp);
		try{
		while(rs.next()){	
			if(user.getEmployeeNo().equals(rs.getString("Technician_Id"))){
				empType="Technician";
				if(rs.getString("Req_Status").equalsIgnoreCase("ON_Behalf")){
					request.setAttribute("showReqReply1", "showReqReply1");
				}else{
				request.setAttribute("showTechnReply", "showTechnReply");
				}
			}else if(user.getEmployeeNo().equals(rs.getString("Emp_No"))){
				empType="Requester";
				if(rs.getString("Req_Status").equalsIgnoreCase("ON_Behalf")){
					request.setAttribute("showReqReply1", "showReqReply1");
				}else{
				request.setAttribute("showReqReply", "showReqReply");
				}
			}else{
				empType="Other";
				request.setAttribute("showBackButton", "showBackButton");
			}
			help.setEmpType(empType);
			
			
			if(rs.getString("AssignTo").contains(","))
			{
			String asgn[]=rs.getString("AssignTo").split(",");
			int asgnlength=asgn.length;
			 pendingtechn=asgn[asgnlength-1];
			
			}
			else
			{
				pendingtechn=rs.getString("AssignTo");
			}
		
			 
			
		}
		
		
		if(pendingtechn.equalsIgnoreCase(user.getEmployeeNo()))
		 {
			 request.setAttribute("submittbuton", "submittbuton");
		 }
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		impl.getRequesterDetails(reqNo,form,request);
		impl.getApprovalStatus(reqNo, form, request,user.getEmployeeNo());
		impl.getConversationList(reqNo, form, request);
		return mapping.findForward("pickupSelectedIssue");
	
		}
	public ActionForward submitReq(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {/*
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesForm help = (IssuesForm) form;
		String category=help.getCategory();
		String subCategory=request.getParameter("subCategory");
		String technician=request.getParameter("technician");
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
	    String dateNow = ft.format(dNow);
	    Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd");
	    String currendate = ft1.format(dNow1);
		synchronized (this) {
	int requestNo=help.getRequestNo();
	int newReqNo=0;
	int status=0;
	//check requestno
	String checkReqNo="select count(*) from IT_ISSUES where Req_No='"+requestNo+"'";
	try{
	ResultSet rsCheck=ad.selectQuery(checkReqNo);
	while(rsCheck.next()){
		status=rsCheck.getInt(1);
	}
	
	if(status>0){
		String maxReqNo="Select max(Req_No) from IT_ISSUES";
		ResultSet rsMax=ad.selectQuery(maxReqNo);
		while(rsMax.next()){
			requestNo=1+rsMax.getInt(1);
			newReqNo=requestNo;
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	String saveIssue="INSERT INTO IT_ISSUES(Req_No,Req_date,Emp_No,Email_ID,Ext_No,IP_Phone,HostName,IPAddress,"
	+ "Req_type,Req_Status,Req_Priority,ITEng_Status,Subject,Reason,Last_approver,Pending_approver,approved_date,Priority,Sub_Category,mode,Location,read_Status,AssignTo,SLA_Status,raisedBy,Escalated,Escalated_date) "
	+ "values('"+requestNo+"','"+dateNow+"','"+help.getEmpno()+"','"+help.getEmpEmailID()+"','"+help.getExtno()+"','"+help.getIpPhoneno()+"',"
	+ "'','"+help.getIPNumber()+"','"+category+"','New','"+help.getIssuePriority()+"','New','"+help.getSubject()+"','"+help.getReason()+"','','"+technician+"','','1','"+subCategory+"','"+help.getMode()+"','"+help.getLocNo()+"','2','"+technician+"','','"+user.getEmployeeNo()+"','0','')";
	 int i=ad.SqlExecuteUpdate(saveIssue);
	 ad.connClose();
	 if(i>0){
		 //send to technician
	 String sendReq="INSERT INTO IT_All_ISSUES(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Technician_id,"
	 + "Requester_Id,Comments,Sub_Category,Sequence,Documents,readStatus) values('"+requestNo+"','"+category+"','"+help.getEmpno()+"','"+dateNow+"',"
	 + "'New','"+technician+"','"+help.getEmpno()+"','','"+subCategory+"',1,'','0')";
	 i=ad.SqlExecuteUpdate(sendReq); 
	 ad.connClose();
		 help.setMessage("Ticket No. "+requestNo+" Generated Successfully");
		 ITMail mail=new ITMail();
		 mail.sendMailToApprover(request, requestNo, "Issues", technician,help.getComments());
		 if(status>0){
			//update user uploaded files
			 String updateFiles="update Issues_document set request_no='"+newReqNo+"' where request_no='"+requestNo+"' and user_id='"+help.getEmpno()+"'";
			 ad.SqlExecuteUpdate(updateFiles);
			 
			}
		 
	 }else{
		 help.setMessage("Error..While Sending Request to Technician");
	 }
		}
		newIncidentform(mapping, form, request, response);
		return mapping.findForward("newincidentform");
	*/

		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesForm help = (IssuesForm) form;
		String category=help.getCategory();
		String subCategory=request.getParameter("subCategory");
		String technician=request.getParameter("technician");
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
	    String dateNow = ft.format(dNow);
	    Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd");
	    String currendate = ft1.format(dNow1);
		synchronized (this) {
	int requestNo=help.getRequestNo();
	int newReqNo=0;
	int status=0;
	//check requestno
	String checkReqNo="select count(*) from IT_ISSUES where Req_No='"+requestNo+"'";
	try{
	ResultSet rsCheck=ad.selectQuery(checkReqNo);
	while(rsCheck.next()){
		status=rsCheck.getInt(1);
	}
	
	if(status>0){
		String maxReqNo="Select max(Req_No) from IT_ISSUES";
		ResultSet rsMax=ad.selectQuery(maxReqNo);
		while(rsMax.next()){
			requestNo=1+rsMax.getInt(1);
			newReqNo=requestNo;
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	if(!help.getEmpno().equalsIgnoreCase(user.getEmployeeNo())){
	String saveIssue="INSERT INTO IT_ISSUES(Req_No,Req_date,Emp_No,Email_ID,Ext_No,IP_Phone,HostName,IPAddress,"
	+ "Req_type,Req_Status,Req_Priority,ITEng_Status,Subject,Reason,Last_approver,Pending_approver,approved_date,Priority,Sub_Category,mode,Location,read_Status,AssignTo,SLA_Status,raisedBy,Escalated,Escalated_date,Approved_Status,behalf_comments) "
	+ "values('"+requestNo+"','"+dateNow+"','"+help.getEmpno()+"','"+help.getEmpEmailID()+"','"+help.getExtno()+"','"+help.getIpPhoneno()+"',"
	+ "'','"+help.getIPNumber()+"','"+category+"','ON_Behalf','"+help.getIssuePriority()+"','ON_Behalf','"+help.getSubject()+"','"+help.getReason()+"','','"+technician+"','','1','"+subCategory+"','"+help.getMode()+"','"+help.getLocNo()+"','2','"+technician+"','','"+user.getEmployeeNo()+"','0','','0','')";
	 int i=ad.SqlExecuteUpdate(saveIssue);
	 ad.connClose();
	 if(i>0){
		 //send to technician
	 String sendReq="INSERT INTO IT_All_ISSUES(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Technician_id,"
	 + "Requester_Id,Comments,Sub_Category,Sequence,Documents,readStatus) values('"+requestNo+"','"+category+"','"+help.getEmpno()+"','"+dateNow+"',"
	 + "'ON_Behalf','"+technician+"','"+help.getEmpno()+"','','"+subCategory+"',1,'','0')";
	 i=ad.SqlExecuteUpdate(sendReq); 
	 ad.connClose();
		 help.setMessage("Ticket No. "+requestNo+" Generated Successfully");
		 ITMail mail=new ITMail();
		 mail.sendMailToApprover(request, requestNo, "Issues", help.getEmpno(),"");
		
		
	 }
	
	}else{
		/*String checkReqNo1="select count(*) from IT_ISSUES where Req_No='"+requestNo+"'";
		try{
		ResultSet rsCheck=ad.selectQuery(checkReqNo1);
		while(rsCheck.next()){
			status=rsCheck.getInt(1);
		}
		
		if(status>0){
			String maxReqNo="Select max(Req_No) from IT_ISSUES";
			ResultSet rsMax=ad.selectQuery(maxReqNo);
			while(rsMax.next()){
				requestNo=1+rsMax.getInt(1);
				newReqNo=requestNo;
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		
			 String saveIssue1="INSERT INTO IT_ISSUES(Req_No,Req_date,Emp_No,Email_ID,Ext_No,IP_Phone,HostName,IPAddress,"
						+ "Req_type,Req_Status,Req_Priority,ITEng_Status,Subject,Reason,Last_approver,Pending_approver,approved_date,Priority,Sub_Category,mode,Location,read_Status,AssignTo,SLA_Status,raisedBy,Escalated,Escalated_date,Approved_Status,behalf_comments) "
						+ "values('"+requestNo+"','"+dateNow+"','"+help.getEmpno()+"','"+help.getEmpEmailID()+"','"+help.getExtno()+"','"+help.getIpPhoneno()+"',"
						+ "'','"+help.getIPNumber()+"','"+category+"','New','"+help.getIssuePriority()+"','New','"+help.getSubject()+"','"+help.getReason()+"','','"+technician+"','','1','"+subCategory+"','"+help.getMode()+"','"+help.getLocNo()+"','2','"+technician+"','','"+user.getEmployeeNo()+"','0','','1','')";
						 int i1=ad.SqlExecuteUpdate(saveIssue1);
						 ad.connClose();
						 if(i1>0){
							 //send to technician
						 String sendReq1="INSERT INTO IT_All_ISSUES(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Technician_id,"
						 + "Requester_Id,Comments,Sub_Category,Sequence,Documents,readStatus) values('"+requestNo+"','"+category+"','"+help.getEmpno()+"','"+dateNow+"',"
						 + "'New','"+technician+"','"+help.getEmpno()+"','','"+subCategory+"',1,'','0')";
						 i1=ad.SqlExecuteUpdate(sendReq1); 
						 ad.connClose();
							 help.setMessage("Ticket No. "+requestNo+" Generated Successfully");
							 ITMail mail=new ITMail();
							 mail.sendMailToApprover(request, requestNo, "Issues", technician,"");
							 
							 if(status>0){
									//update user uploaded files
									 String updateFiles="update Issues_document set request_no='"+newReqNo+"' where request_no='"+requestNo+"' and user_id='"+help.getEmpno()+"'";
									 ad.SqlExecuteUpdate(updateFiles);
									 
									}
							 else{
								 /*help.setMessage("Error..While Sending Request to Technician");*/
								 help.setMessage("Ticket No. "+requestNo+" Generated Successfully");
							 }
		 }
	}
	
		}
		newIncidentform(mapping, form, request, response);	
		return mapping.findForward("newincidentform");
		
	
	}
	
	public ActionForward appsubmitReq(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesForm help = (IssuesForm) form;
		//String category=help.getCategory();
		String comments=request.getParameter("com");
	//	String technician=request.getParameter("technician");
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
	    String dateNow = ft.format(dNow);
	    Date dNow1 = new Date( );
	   String techinician="";
		 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd");
	    String currendate = ft1.format(dNow1);
		synchronized (this) {
	int requestNo=help.getRequestNo();
	int newReqNo=0;

			 String saveIssue1="update IT_ISSUES set Approved_Status=1,behalf_comments='"+comments+"',Req_Status='New',ITEng_Status='New' where Req_No='"+requestNo+"' ";
			 ad.SqlExecuteUpdate(saveIssue1);
			
			 String saveIssue12="update IT_All_ISSUES Req_Status='New' where Req_Id='"+requestNo+"' ";
			 ad.SqlExecuteUpdate(saveIssue12);
						 
						 //send to technician
						 String sendReq1="select * from IT_All_ISSUES where Req_Id='"+requestNo+"' ";
						
							ResultSet rs1=ad.selectQuery(sendReq1);
							try{
							while(rs1.next()){	
								techinician	=rs1.getString("Technician_Id");
							}
							}catch(Exception e){
								e.printStackTrace();
							}
							 help.setMessage("Ticket No. "+requestNo+" Sent to Technician sucessfully");
							 ITMail mail=new ITMail();
							 mail.sendMailToApprover(request, requestNo, "Issues", techinician,"");
				
	}
		
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward appsubmitReq1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesForm help = (IssuesForm) form;
		//String category=help.getCategory();
		String comments=request.getParameter("com");
	//	String technician=request.getParameter("technician");
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
	    String dateNow = ft.format(dNow);
	    Date dNow1 = new Date( );
	   String techinician="";
		 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd");
	    String currendate = ft1.format(dNow1);
		synchronized (this) {
	int requestNo=help.getRequestNo();
	int newReqNo=0;

			 String saveIssue1="update IT_ISSUES set Approved_Status=1,behalf_comments='"+comments+"',Req_Status='Rejected',ITEng_Status='Rejected',Pending_approver='' where Req_No='"+requestNo+"' ";
			 ad.SqlExecuteUpdate(saveIssue1);
			
			 String saveIssue12="update IT_All_ISSUES Req_Status='Rejected',Technician_Id='' where Req_Id='"+requestNo+"' ";
			 ad.SqlExecuteUpdate(saveIssue12);
						 
						 //send to technician
						 String sendReq1="select * from IT_All_ISSUES where Req_Id='"+requestNo+"' ";
						
							ResultSet rs1=ad.selectQuery(sendReq1);
							try{
							while(rs1.next()){	
								techinician	=rs1.getString("Technician_Id");
							}
							}catch(Exception e){
								e.printStackTrace();
							}
							 help.setMessage("Ticket No. "+requestNo+" Rejected");
							 ITMail mail=new ITMail();
							 mail.sendMailToApprover(request, requestNo, "Issues", techinician,"");
				
	}
		
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward getTechn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String category=request.getParameter("category");
		String subCategory=request.getParameter("subCategory");
		String locNo=request.getParameter("locNo");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		List techList=new LinkedList();
		try{
		String getTecnicansList="select app.Approver_ID,emp.EMP_FULLNAME from IT_Approvers app,emp_official_info as "
		+ "emp where Category='"+category+"' and Req_Type='"+subCategory+"' and LOCATION_ID='"+locNo+"' and emp.PERNR=app.Approver_ID "
		+ "order by Priority";
		ResultSet rs=ad.selectQuery(getTecnicansList);
		while(rs.next()){
		IssuesForm issue=new IssuesForm();
		issue.setTechnicianID(rs.getString("Approver_ID"));
		issue.setTechnicianName(rs.getString("EMP_FULLNAME"));
		techList.add(issue);
		}
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		if(techList.size()>0)
		   request.setAttribute("techList", techList);
		else
			request.setAttribute("noRecords", "noRecords");
		try {
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			help.setLocationIdList(locationList);
			help.setLocationLabelList(locationLabelList);	
			ad.connClose();
			
			ArrayList deparmentList=new ArrayList();
			ArrayList departmentIdList=new ArrayList();
			
			ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				departmentIdList.add(rs12.getString("DPTID"));
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			rs12.close();
			ad.connClose();
			help.setDepartmentIdList(departmentIdList);
			help.setDepartmentList(deparmentList);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		
		return mapping.findForward("displayITEnggs");
	}
	public ActionForward getTechnicians(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String category=request.getParameter("category");
		String subCategory=request.getParameter("subCategory");
		String locNo=request.getParameter("locNo");
		List techList=new LinkedList();
		try{
		String getTecnicansList="select app.Approver_ID,emp.EMP_FULLNAME from IT_Approvers app,emp_official_info as "
		+ "emp where Category='"+category+"' and Req_Type='"+subCategory+"' and LOCATION_ID='"+locNo+"' and emp.PERNR=app.Approver_ID "
		+ "order by Priority";
		ResultSet rs=ad.selectQuery(getTecnicansList);
		while(rs.next()){
		IssuesForm issue=new IssuesForm();
		issue.setTechnicianID(rs.getString("Approver_ID"));
		issue.setTechnicianName(rs.getString("EMP_FULLNAME"));
		techList.add(issue);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("techList", techList);
		return mapping.findForward("techniciansAjax");
	}
	
	public ActionForward getTechnicians_parllel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String category=request.getParameter("category");
		String subCategory=request.getParameter("subCategory");
		String locNo=request.getParameter("locNo");
		List techList=new LinkedList();
		try{
			String getTecnicansList="select app.Approver_ID,emp.EMP_FULLNAME from Material_Approvers"
					+ " app,emp_official_info as "
					+ "emp where Material_Type='"+category+"' and "
					+ "Material_Sub_Category='"+subCategory+"' and  emp.PERNR=app.Approver_ID and "
					+ "LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"'  ) and role ='Creator'"
					+ " union select app.Parllel_Approver_1,emp.EMP_FULLNAME from Material_Approvers"
					+ " app,emp_official_info as "
					+ "emp where Material_Type='"+category+"' and "
					+ "Material_Sub_Category='"+subCategory+"' and emp.PERNR=app.Parllel_Approver_1 and "
					+ "LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"'  ) and role ='Creator'"
					+ " Union select app.Parllel_Approver_2,emp.EMP_FULLNAME from Material_Approvers"
					+ " app,emp_official_info as "
					+ "emp where Material_Type='"+category+"' and emp.PERNR=app.Parllel_Approver_2 and "
					+ "Material_Sub_Category='"+subCategory+"' and "
					+ "LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"'  ) and role ='Creator' ";
					

		ResultSet rs=ad.selectQuery(getTecnicansList);
		while(rs.next()){
		IssuesForm issue=new IssuesForm();
		issue.setTechnicianID(rs.getString("Approver_ID"));
		issue.setTechnicianName(rs.getString("EMP_FULLNAME"));
		techList.add(issue);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("techList", techList);
		return mapping.findForward("techniciansAjax");
	}
	public ActionForward getsubcategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String linkName = request.getParameter("linkName");		
		try {
			
			ArrayList subCategoryList = new ArrayList();
			if(linkName.equalsIgnoreCase("Hardware"))
			{
				subCategoryList.add("Desktop");
				subCategoryList.add("Monitor");
				subCategoryList.add("Keyboard");
				subCategoryList.add("Mouse");
				subCategoryList.add("RAM");
				subCategoryList.add("Hardisk");
				subCategoryList.add("Laptop");
				subCategoryList.add("Scanner");
				subCategoryList.add("Data Card");
				subCategoryList.add("Wyse");
				subCategoryList.add("New Installation");
							
			}
			
			if(linkName.equalsIgnoreCase("Internet"))
			{
				subCategoryList.add("Browser");
				subCategoryList.add("Web Site Link");
			}			
			
			if(linkName.equalsIgnoreCase("Network"))
			{
				subCategoryList.add("LAN");
				subCategoryList.add("VOIP PHONE");
				subCategoryList.add("WI-FI");
				subCategoryList.add("AD-Lockout");
			}
			
			if(linkName.equalsIgnoreCase("Operating System"))
			{
				subCategoryList.add("WINDOWS XP");
				subCategoryList.add("WINDOWS 7");
				subCategoryList.add("WINDOWS 8");
			}
			if(linkName.equalsIgnoreCase("Printers"))
			{
				subCategoryList.add("Connectivity");
				subCategoryList.add("Breakdown");
				subCategoryList.add("Tonner");
				subCategoryList.add("Cartridge");
			}
		
			if(linkName.equalsIgnoreCase("Software"))
			{
				subCategoryList.add("EMICRO");
				subCategoryList.add("MLLITAMS");
				subCategoryList.add("MLLPMS");
				subCategoryList.add("MLLQMS");
				subCategoryList.add("MLLDLS");
				subCategoryList.add("NAVADISHA");
				subCategoryList.add("MTS");
				subCategoryList.add("DISHA");
				subCategoryList.add("LOTUS MAIL");
				subCategoryList.add("MS OFFICE");
				subCategoryList.add("Adobe");
				subCategoryList.add("Legacy Application ");
				subCategoryList.add("Backup");
				subCategoryList.add("Citrix");
				subCategoryList.add("Installation");
				subCategoryList.add("Chromoleone");
				subCategoryList.add("Other");
			}
			
			
			if(linkName.equalsIgnoreCase("Telephone"))
			{
				subCategoryList.add("Land Line");
				subCategoryList.add("Black Berry");
				subCategoryList.add("Android");
				subCategoryList.add("Windows");
				subCategoryList.add("Iphone");
			}
			
			if(linkName.equalsIgnoreCase("Others"))
			{
				subCategoryList.add("Configuration");
				
			}
			
			if(linkName.equalsIgnoreCase("SAP Users"))
			{
				subCategoryList.add("Account assignment issues");
			subCategoryList.add("Advance Request");
			subCategoryList.add("Attachment Deletion");
			subCategoryList.add("Attendance");
			subCategoryList.add("Authorization Issues");
			subCategoryList.add("Bill of Material");
			subCategoryList.add("Bonding / Ex-Bonding Issues");
			subCategoryList.add("Catalogs Issues");
			subCategoryList.add("Change in Existing SAP User ID");
			subCategoryList.add("COA Issues");
			subCategoryList.add("Cost Center");
			subCategoryList.add("Costing Issue");
			subCategoryList.add("Customer Return entry");
			subCategoryList.add("Dispensing entry");
			subCategoryList.add("DMS Issues");
			subCategoryList.add("Document Print");
			subCategoryList.add("Document Creation");
			subCategoryList.add("Excise Duty Issues");
			subCategoryList.add("Excise Invoice");
			subCategoryList.add("FG Code");
			subCategoryList.add("FG Release");
			subCategoryList.add("Form Issues");
			subCategoryList.add("G/L Account");
			subCategoryList.add("Goods Issues ");
			subCategoryList.add("Goods Receipt Issues");
			subCategoryList.add("Issues in DC Creation / Posting");
			subCategoryList.add("Issues in Invoice Creation / Posting");
			subCategoryList.add("Leave balance");
			subCategoryList.add("Master Recipe");
			subCategoryList.add("Material Code");
			subCategoryList.add("Material release");
			subCategoryList.add("Material Transfer Issues");
			subCategoryList.add("MIGO Issues");
			subCategoryList.add("MIRO Issues");
			subCategoryList.add("MRP Issues");
			subCategoryList.add("MRP run");
			subCategoryList.add("New BIN Code creation");
			subCategoryList.add("New SAP User ID Creation");
			subCategoryList.add("Notification Issues");
			subCategoryList.add("Password Re-set");
			subCategoryList.add("User ID Unlock");
			subCategoryList.add("Payroll Issues");
			subCategoryList.add("PD Change approval");
			subCategoryList.add("PD Issues");
			subCategoryList.add("Physical Inventory Issues");
			subCategoryList.add("Pick List Print");
			subCategoryList.add("Planning Group / Specification ");
			subCategoryList.add("Post change notification");
			subCategoryList.add("Post-shipment");
			subCategoryList.add("Pre-shipment");
			subCategoryList.add("Pricing");
			subCategoryList.add("Printing Issues");
			subCategoryList.add("Process Order");
			subCategoryList.add("Process order confirmation");
			subCategoryList.add("Production Version");
			subCategoryList.add("Purchase order issues");
			subCategoryList.add("Purchase requisation issues");
			subCategoryList.add("Purchase Order Release");
			subCategoryList.add("QC Release");
			subCategoryList.add("Report");
			subCategoryList.add("Resource Code");
			subCategoryList.add("Result Recording");
			subCategoryList.add("Return Delivery");
			subCategoryList.add("Sales order Issues");
			subCategoryList.add("Sales Tax Issues");
			subCategoryList.add("Service Code");
			subCategoryList.add("Service entry issues");
			subCategoryList.add("Shipment Plan");
			subCategoryList.add("STO Issues");
			subCategoryList.add("Stock Transfer Issues");
			subCategoryList.add("Subcontracting Issues");
			subCategoryList.add("Tally Transfer");
			subCategoryList.add("Tax Code Issue");
			subCategoryList.add("TDS Issue");
			subCategoryList.add("Transfer Order");
			subCategoryList.add("Transfer Requirement ");
			subCategoryList.add("Usage Decision");
			subCategoryList.add("Vendor code");
			subCategoryList.add("WM View Extension");
			subCategoryList.add("Workorder issues");
			subCategoryList.add("Customer code");
			subCategoryList.add("Query on Issues");
			subCategoryList.add("New Requirement");
			subCategoryList.add("Changes in Material Code");
}
			
			if(linkName.equalsIgnoreCase("SAP Core Team"))
			{
				subCategoryList.add("New SAP User ID");
				subCategoryList.add("Change SAP User ID");
				subCategoryList.add("Costing Issue");
				subCategoryList.add("Document Creation");
				subCategoryList.add("Delete SAP User ID");
				subCategoryList.add("Password Re-set");
				subCategoryList.add("User ID Unlock");
				subCategoryList.add("Tcode Authorization");
				subCategoryList.add("Authorization Issues");
				subCategoryList.add("SPRO");
				subCategoryList.add("New ABAP requirement");
				subCategoryList.add("Change in ABAP requirement");
				subCategoryList.add("ABAP Issues");
				subCategoryList.add("Testing & Document Prepration");
				subCategoryList.add("Release of Transport request");
				subCategoryList.add("Query on SAP Issues");
				subCategoryList.add("Training User");
				subCategoryList.add("Training team members");
				subCategoryList.add("Meeting / Discussion with HOD");
				subCategoryList.add("Meeting / Discussion Business HOD");
				subCategoryList.add("Meeting / Discussion Business Users");
				subCategoryList.add("SOP Preparation");
				subCategoryList.add("SOP Reviewing");
				subCategoryList.add("Plant Visit");

			}
			if(linkName.equalsIgnoreCase("JAVA Team"))
			{
				subCategoryList.add("eMicro");
				subCategoryList.add("MLLQMS");
				subCategoryList.add("MLLDLS");
				subCategoryList.add("MLLITAMS");
				subCategoryList.add("MLLHRMS");
				subCategoryList.add("MLLPMS");
				subCategoryList.add("MTS");
				
			}
			help.setSubcatList(subCategoryList);
			help.setUsage(help.getUsage());
			help.setEmployeeno("");
			request.setAttribute("subCategoryList", subCategoryList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//displayMainLinks(mapping, form, request, response);
		return mapping.findForward("subcategoryajax");
	
	}
	
	public ActionForward userDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String ipaddress="";
		String username="";
		ipaddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}
		
		if(help.getEmployeeno()==null)
		{
			help.setEmployeeno(user.getEmployeeNo());
		}
		if(help.getEmployeeno().equals(""))
		{
			help.setEmployeeno(user.getEmployeeNo());
		}

		  
		if(help.getUsage()!=null)
		{
		if(help.getUsage().equalsIgnoreCase("Others")){
			   
			   help.setRequestStatus("ON_Behalf");
			   
			   String s12="select emp.EMP_FULLNAME,dep.DPTSTXT,des.DSGSTXT from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des  where  dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and emp.pernr='"+user.getEmployeeNo()+"'";
			   ResultSet rs12= ad.selectQuery(s12);
			   //IssuesForm help1 = new IssuesForm();
				try {
					while(rs12.next())
					{	
						//IssuesForm help1 = new IssuesForm();
						help.setOnbename(rs12.getString("EMP_FULLNAME"));
						help.setOnbedepartment(rs12.getString("DPTSTXT"));
						help.setOnbedesignation(rs12.getString("DSGSTXT"));
						
						//subCategoryList1.add(help);
					//	if(subCategoryList1.size()>0){
					request.setAttribute("Recordsfound", "subCategoryList1");
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}            
	        }
		}
	
		
		
		try{
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE,loc.LOCID " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+help.getEmployeeno()+"' and " +
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
	
	public ActionForward sapUserDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String ipaddress="";
		String username="";
		ipaddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}
		if(help.getEmployeeno().equals(""))
		{
			help.setEmployeeno(user.getEmployeeNo());
		}
		try{
			String data="select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE,loc.LOCID " +
					"from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"+help.getEmployeeno()+"' and " +
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
		
		return mapping.findForward("sapnewincident");
	}
	
	public ActionForward newIncidentform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		IssuesForm help = (IssuesForm) form;
		String forwardtype="";
		String localhostname="";
		String ipaddress="";
		String username="";
		
	
		help.setCategory("");
		help.setSubject("");
		help.setReason("");
		NewsandMediaDao ad=new NewsandMediaDao();
		try {
			int reqNo=0;
		String maxReqNo="Select max(Req_No) from IT_ISSUES";
		ResultSet rsMax=ad.selectQuery(maxReqNo);
		while(rsMax.next()){
			reqNo=1+rsMax.getInt(1);
		}
         help.setRequestNo(reqNo);	
         if(help.getUsage().equalsIgnoreCase("Others")){

             help.setRequestStatus("ON_Behalf");
                       
         }
         else{
          help.setRequestStatus("New");
         }
        /* help.setRequestStatus("New");*/
		userDetails(mapping, form, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("newincidentform");
	}
	public ActionForward displayAllIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		IssuesForm help = (IssuesForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String localhostname="";
		String ipaddress="";
		String username="";
		try {
			localhostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			ipaddress = (InetAddress.getLocalHost()).getHostAddress();
			username=(InetAddress.getLocalHost()).getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		ipaddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}
		String type=help.getRequestType();
		help.setRequestType(type);
		
		String empno="";
		int k=0;
		
	
		empno=help.getEmployeeno();
		if(empno==null)
			empno=user.getEmployeeNo();
		else{
			if((empno.equalsIgnoreCase(""))){
				empno=user.getEmployeeNo();
			}else{
			 empno=help.getEmployeeno();
			}
		}
		
		//chk valid employe no.
		
		String emp="Select Count(*) from emp_official_info where PERNR='"+empno+"'";
		ResultSet rsss= ad.selectQuery(emp);
		try {
			while(rsss.next())
			{
				 k=rsss.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(k==0)
		{
			help.setMessage("Please Enter Valid Employee Number");
			return mapping.findForward("displayform");
		}
			help.setEmployeeno(empno);
			try {
				ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				help.setLocationIdList(locationList);
				help.setLocationLabelList(locationLabelList);	
				
				ArrayList deparmentList=new ArrayList();
				ArrayList departmentIdList=new ArrayList();
				
				ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
				while(rs12.next()){
					departmentIdList.add(rs12.getString("DPTID"));
					deparmentList.add(rs12.getString("DPTSTXT"));
				}
				rs12.close();
				ad.connClose();
				help.setDepartmentIdList(departmentIdList);
				help.setDepartmentList(deparmentList);
				
				ad.connClose();
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			IssueImpl impl=new IssueImpl();
			List IssuesList=new LinkedList();
			
	
		    
		    //start date
		     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");
		        Calendar cal = Calendar.getInstance();
		        cal.add(Calendar.DATE, -7); 
		        String backdate="";
		    
				if(help.getFromDate()==null)
				{
		      	help.setFromDate("");	
				}
				if(help.getFromDate().equalsIgnoreCase(""))
				{
					backdate="";
				}
				if(!help.getFromDate().equalsIgnoreCase(""))
				{
					 String a[]=help.getFromDate().split("/");
					 backdate=a[2]+"-"+a[1]+"-"+a[0]+" "+"00:00:00.000";
				}
		        
		        help.setFromDate(backdate);
		        
		        //to datwe
		        
				Date dNow = new Date( );
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd 23:59:00.000");
				String dateNow ="";
				if(help.getToDate()==null)
				{
		      	help.setToDate("");	
				}
				if(help.getToDate().equalsIgnoreCase(""))
				{
					dateNow = "";	
				}
				if(!help.getToDate().equalsIgnoreCase(""))
				{
					 String a[]=help.getToDate().split("/");
					 dateNow=a[2]+"-"+a[1]+"-"+a[0]+" "+"23:59:00.000";
				}
				
			     
			    help.setToDate(dateNow);
			    
			    if(help.getMaincategory()==null)
			
			    {
			    	help.setMaincategory("");
			    }
			    if(help.getMainrequestStatus()==null)
			    {
			    	
			    help.setMainrequestStatus("");
			    }
			    if(help.getDepartment()==null)
			    {
			    	help.setDepartment("");
			    }
			    if(help.getLocationId()==null)
			    {
			    	help.setLocationId(user.getPlantId());
			    }
			    if(help.getLocationId().equalsIgnoreCase(""))
			    {
			    	help.setLocationId(user.getPlantId());
			    }
			    
			 
			 String view=request.getParameter("search");
			 if(view==null)
			 {
				 view="";
			 }
			 
			 if(view.equalsIgnoreCase(""))
			 {
				 help.setLocationId("All");
			 }
			 String checkstatus=help.getMainrequestStatus();
			   if(checkstatus.equalsIgnoreCase("ON_Behalf")){
				   IssuesList=impl.getAllRequest1(user.getEmployeeNo(),form,request,help.getLocationId(),view);
			   }
			   else{
				   IssuesList=impl.getAllRequest(user.getEmployeeNo(),form,request,help.getLocationId(),view);
			   }
			    
		/*	IssuesList=impl.getAllRequest(user.getEmployeeNo(),form,request,help.getLocationId(),view);*/
			
			if(IssuesList.size()>0)
				request.setAttribute("IssuesList", IssuesList);
			else
				request.setAttribute("noRecords", "noRecords");
			
			List technList=new LinkedList();
			impl.getTechnicanList(form, request);
			help.setEmployeeno("");
		return mapping.findForward("displayIssues");
	}
	
	public ActionForward displayAllSapIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		IssuesForm help = (IssuesForm) form;
		
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String localhostname="";
		String ipaddress="";
		String username="";
		try {
			localhostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			ipaddress = (InetAddress.getLocalHost()).getHostAddress();
			username=(InetAddress.getLocalHost()).getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		ipaddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}
		String type=help.getRequestType();
		help.setRequestType(type);
		
		String empno="";
		int k=0;
		
	
		empno=help.getEmployeeno();
		if(empno==null)
			empno=user.getEmployeeNo();
		else{
			if((empno.equalsIgnoreCase(""))){
				empno=user.getEmployeeNo();
			}else{
			 empno=help.getEmployeeno();
			}
		}
		
		//chk valid employe no.
		
		String emp="Select Count(*) from emp_official_info where PERNR='"+empno+"'";
		ResultSet rsss= ad.selectQuery(emp);
		try {
			while(rsss.next())
			{
				 k=rsss.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(k==0)
		{
			help.setMessage("Please Enter Valid Employee Number");
			return mapping.findForward("displayform");
		}
			help.setEmployeeno(empno);
			try {
				ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				help.setLocationIdList(locationList);
				help.setLocationLabelList(locationLabelList);	
				ad.connClose();
				
				
				ArrayList deparmentList=new ArrayList();
				ArrayList departmentIdList=new ArrayList();
				
				ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
				while(rs12.next()){
					departmentIdList.add(rs12.getString("DPTID"));
					deparmentList.add(rs12.getString("DPTSTXT"));
				}
				rs12.close();
				ad.connClose();
				help.setDepartmentIdList(departmentIdList);
				help.setDepartmentList(deparmentList);
				
				
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			IssueImpl impl=new IssueImpl();
			List IssuesList=new LinkedList();
			IssuesList=impl.getAllSapRequest(user.getEmployeeNo(),form,request);
			if(IssuesList.size()>0)
				request.setAttribute("IssuesList", IssuesList);
			else
				request.setAttribute("noRecords", "noRecords");
			
			List technList=new LinkedList();
			impl.getTechnicanList(form, request);
			help.setEmployeeno("");
		return mapping.findForward("displaySapIssues");
	}
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		
		FormFile documentFile=help.getDocumentFile();
		String documentName=documentFile.getFileName();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 String filePath="";
		 
		 String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
			int filesize=documentFile.getFileSize();
			
			if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")||ext.equalsIgnoreCase("txt")) && (filesize<1048576))
			{
		try{
		 byte[] size=documentFile.getFileData();
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	 	String fileName = documentFile.getFileName();
	     String extension=documentName.substring(dot,length);
 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
 	 Properties props = new Properties();
 	 props.load(in);
	 in.close();
 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
 	 filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
 	 
 	 
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
			String filePath1 = "E:/EMicro Files/IT/Help Desk/Issues/UploadFiles";
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
		try{
			int reqNo=help.getRequestNo();
			String sql9="select count(*) from Issues_document  where  file_name='"+documentFile.getFileName()+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				help.setMessage("File name is exist.Please choose another file");
			}
			else
			{
				String sql="insert into Issues_document(request_no,user_id,file_name)" +
				"values('"+reqNo+"','"+help.getEmployeeno()+"','"+documentFile.getFileName()+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					help.setMessage("Documents Uploaded Successfully");
					}
			}
		String sql1="select * from Issues_document where request_no='"+reqNo+"' and user_id='"+help.getEmployeeno()+"' ";
		ResultSet rs=ad.selectQuery(sql1);
		IssuesForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs.next()) {
				leaveForm1=new IssuesForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getString("id"));
				leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
		
		}catch(Exception e){
			e.printStackTrace();
		}
			}else{
				help.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
				String sql1="select * from Issues_document where request_no='"+help.getRequestNo()+"' and user_id='"+help.getEmployeeno()+"' ";
				ResultSet rs=ad.selectQuery(sql1);
				IssuesForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					try {
						while(rs.next()) {
							leaveForm1=new IssuesForm();
							leaveForm1.setFileName(rs.getString("file_name"));
							leaveForm1.setId(rs.getString("id"));
							leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
							a1.add(leaveForm1);
						}
						request.setAttribute("documentDetails", a1);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					request.setAttribute("documentDetails", a1);
						}
			userDetails(mapping, form, request, response);
			try{
			String category=help.getCategory();
			help.setCategory(category);
			String subCategory=request.getParameter("subCategory");
			String technician=request.getParameter("technician");
			help.setSelectedSubCategory(subCategory);
			help.setSelectedTechnician(technician);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			request.setAttribute("setSubCategAndTechns", "setSubCategoryAndTecnhicians");
			
		return mapping.findForward("newincidentform");
		}
	//arun
	public ActionForward sapUploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		
		FormFile documentFile=help.getDocumentFile();
		String documentName=documentFile.getFileName();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 String filePath="";
		 String filePath2="";
		 String cate="";
		 int reqNo=help.getRequestNo();
		 String reqname=request.getParameter("reqname");
		 if(reqname!=null)
		 {
		 if(reqname.equalsIgnoreCase("SAP User Id Management"))
		 {
			 
			 cate="'PSAP','SSAP'";
			 
		 }
		 if(reqname.equalsIgnoreCase("SAP Authorization Management"))
		 {
			 
			 cate="'AUTH'";
			 
		 }
		 if(reqname.equalsIgnoreCase("SAP Roles Management"))
		 {
			 
			 cate="'ROLES'";
			 
		 }
		 if(reqname.equalsIgnoreCase("Document Cancellation"))
		 {
			 
			 cate="'DC'";
			 
		 }
		 }
		 if(cate.equalsIgnoreCase("'PSAP','SSAP'"))
			{
				request.setAttribute("userid", "userid");
				help.setRequestName("SAP User Id Management");
			}
			if(cate.equalsIgnoreCase("'AUTH'"))
			{
				request.setAttribute("userid", "userid");
				help.setRequestName("SAP Authorization Management");
			}
			if(cate.equalsIgnoreCase("'ROLES'"))
			{
				request.setAttribute("userid", "userid");
				help.setRequestName("SAP Roles Management");
			}
			if(cate.equalsIgnoreCase("'DC'"))
			{
			/*	request.setAttribute("userid", "userid");*/
				help.setRequestName("Document Cancellation");
			}
		 String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
			int filesize=documentFile.getFileSize();
			
			if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")||ext.equalsIgnoreCase("txt")) && (filesize<1048576))
			{
		try{
		 byte[] size=documentFile.getFileData();
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	 	String fileName = reqNo+"_"+documentFile.getFileName();
	     String extension=documentName.substring(dot,length);
 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
 	 Properties props = new Properties();
 	 props.load(in);
	 in.close();
 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/SapIssues/UploadFiles/"+cate+"");
 	 filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/SapIssues/UploadFiles/"+cate+"";
 	filePath2="/EMicro Files/IT/Help Desk/SapIssues/UploadFiles/"+cate+"";
 	 
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
			String filePath1 = "E:/EMicro Files/IT/Help Desk/SapIssues/UploadFiles/"+cate+"";
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
			String fileName = reqNo+"_"+documentFile.getFileName();
		try{
			
			String sql9="select count(*) from Issues_document  where  file_name='"+fileName+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				help.setMessage("File name is exist.Please choose another file");
			}
			else
			{
				String sql="insert into Issues_document(request_no,user_id,file_name)" +
				"values('"+reqNo+"','"+help.getEmployeeno()+"','"+fileName+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					help.setMessage("Documents Uploaded Successfully");
					}
			}
		String sql1="select * from Issues_document where request_no='"+reqNo+"' and user_id='"+help.getEmployeeno()+"' ";
		ResultSet rs=ad.selectQuery(sql1);
		IssuesForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs.next()) {
				leaveForm1=new IssuesForm();
				String start = rs.getString("file_name").substring(rs.getString("file_name").indexOf("_")+1, rs.getString("file_name").length()-1);
				leaveForm1.setFileName(start);
				//leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getString("id"));
				leaveForm1.setFileFullPath(filePath2+"/"+rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
		
		}catch(Exception e){
			e.printStackTrace();
		}
			}else{
				help.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
				String sql1="select * from Issues_document where request_no='"+help.getRequestNo()+"' and user_id='"+help.getEmployeeno()+"' ";
				ResultSet rs=ad.selectQuery(sql1);
				IssuesForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					try {
						while(rs.next()) {
							leaveForm1=new IssuesForm();
							String start = rs.getString("file_name").substring(rs.getString("file_name").indexOf("_")+1, rs.getString("file_name").length()-1);
							leaveForm1.setFileName(start);
							//leaveForm1.setFileName(rs.getString("file_name"));
							leaveForm1.setId(rs.getString("id"));
							leaveForm1.setFileFullPath(filePath2+"/"+rs.getString("file_name"));
							a1.add(leaveForm1);
						}
						request.setAttribute("documentDetails", a1);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					request.setAttribute("documentDetails", a1);
						}
			sapUserDetails(mapping, form, request, response);
			try{
			String category=help.getCategory();
			help.setCategory(category);
			String subCategory=request.getParameter("subCategory");
			String technician=request.getParameter("technician");
			help.setSelectedSubCategory(subCategory);
			help.setSelectedTechnician(technician);
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
		
			request.setAttribute("setSubCategAndTechns", "setSubCategoryAndTecnhicians");
			
			
			///MAIL ID OF USER
			String mail="";
			String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
			ResultSet rss=ad.selectQuery(a);
			try {
				if(rss.next())
				{
				mail=rss.getString("EMAIL_ID")	;
				if(!mail.equalsIgnoreCase("null"))
					help.setReqEmail(mail);
			
				}
				
				if(mail.equalsIgnoreCase("")||mail.equalsIgnoreCase("null"))
				{
					request.setAttribute("MAILA","MAILA");
				}
				else
				{
					request.setAttribute("MAILP","MAILP");	
				}
				
			} catch (SQLException e) {
						e.printStackTrace();
			}
	
			displayApprovers(mapping, form, request, response);
			
			 //Category Setting
			
			ArrayList category=new ArrayList();
			ArrayList categoryShortName=new ArrayList();
			String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP' AND c_cat_sh_name IN ("+cate+")";
			ResultSet rs2 = ad.selectQuery(cat);
			
			try {
				while (rs2.next())

				{
					category.add(rs2.getString("c_cat_name"));
					categoryShortName.add(rs2.getString("c_cat_sh_name"));
					
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			help.setCategorylist(category);
			
			help.setCategortShortlist(categoryShortName);
			
			help.setRequestName(reqname);
			ArrayList roleList = new ArrayList();
			ArrayList roleLabelList = new ArrayList();
			String role = "select sap.role,sap.id,sap.department from Role_SAP as sap,location as loc where loc.LOCID = '"+user.getPlantId()+"' AND sap.plant=loc.location_code";
			ResultSet rs21 = ad.selectQuery(role);
			
			try {
				while (rs21.next())

				{
					roleList.add(rs21.getString("id"));
					roleLabelList.add(rs21.getString("Department")+"-"+rs21.getString("Role"));
					
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			help.setRolelist(roleList);
			help.setRoleLabelList(roleLabelList);
		return mapping.findForward("sapuseridmanagement");
		}
	//---
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
		
		int reqNo=help.getRequestNo();
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
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						String fileName="";
						String getFilename="select * from Issues_document where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from Issues_document  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					help.setMessage("Document Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	File fileToCreate1 = new File("E:/EMicro Files/IT/Help Desk/Issues/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from Issues_document where user_id='"+help.getEmployeeno()+"' and request_no='"+reqNo+"'";
			ResultSet rs=ad.selectQuery(sql1);
			try{
				IssuesForm help1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					help1=new IssuesForm();
					help1.setFileName(rs.getString("file_name"));
					help1.setId(rs.getString("id"));
					a1.add(help1);
				}
				request.setAttribute("documentDetails", a1);
			}catch(Exception e){
				e.printStackTrace();
			}
			userDetails(mapping, form, request, response);	
			try{
				String category=help.getCategory();
				help.setCategory(category);
				String subCategory=request.getParameter("subCategory");
				String technician=request.getParameter("technician");
				help.setSelectedSubCategory(subCategory);
				help.setSelectedTechnician(technician);
				
				}catch(Exception e){
					e.printStackTrace();
				}
				request.setAttribute("setSubCategAndTechns", "setSubCategoryAndTecnhicians");
		return mapping.findForward("newincidentform");
	}
	//arun
	public ActionForward sapDeleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IssuesForm help = (IssuesForm) form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			 String cate="";
			 String reqname=request.getParameter("reqname");
			 if(reqname!=null)
			 {
			 if(reqname.equalsIgnoreCase("SAP User Id Management"))
			 {
				 
				 cate="'PSAP','SSAP'";
				 
			 }
			 if(reqname.equalsIgnoreCase("SAP Authorization Management"))
			 {
				 
				 cate="'AUTH'";
				 
			 }
			 if(reqname.equalsIgnoreCase("SAP Roles Management"))
			 {
				 
				 cate="'ROLES'";
				 
			 }
			 if(reqname.equalsIgnoreCase("Document Cancellation"))
			 {
				 
				 cate="'DC'";
				 
			 }
			 }
			 if(cate.equalsIgnoreCase("'PSAP','SSAP'"))
				{
					request.setAttribute("userid", "userid");
					help.setRequestName("SAP User Id Management");
				}
				if(cate.equalsIgnoreCase("'AUTH'"))
				{
					request.setAttribute("userid", "userid");
					help.setRequestName("SAP Authorization Management");
				}
				if(cate.equalsIgnoreCase("'ROLES'"))
				{
					request.setAttribute("userid", "userid");
					help.setRequestName("SAP Roles Management");
				}
				if(cate.equalsIgnoreCase("'DC'"))
				{
				/*	request.setAttribute("userid", "userid");*/
					help.setRequestName("Document Cancellation");
				}
			 
		int reqNo=help.getRequestNo();
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
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						String fileName="";
						String getFilename="select * from Issues_document where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from Issues_document  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					help.setMessage("Document Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/SapIssues/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/SapIssues/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	File fileToCreate1 = new File("E:/EMicro Files/IT/Help Desk/SapIssues/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from Issues_document where user_id='"+help.getEmployeeno()+"' and request_no='"+reqNo+"'";
			ResultSet rs=ad.selectQuery(sql1);
			try{
				IssuesForm help1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					help1=new IssuesForm();
					String start = rs.getString("file_name").substring(rs.getString("file_name").indexOf("_")+1, rs.getString("file_name").length()-1);
					help1.setFileName(start);
					//help1.setFileName(rs.getString("file_name"));
					help1.setId(rs.getString("id"));
					a1.add(help1);
				}
				request.setAttribute("documentDetails", a1);
			}catch(Exception e){
				e.printStackTrace();
			}
			sapUserDetails(mapping, form, request, response);	
			try{
				String category=help.getCategory();
				help.setCategory(category);
				String subCategory=request.getParameter("subCategory");
				String technician=request.getParameter("technician");
				help.setSelectedSubCategory(subCategory);
				help.setSelectedTechnician(technician);
				
				}catch(Exception e){
					e.printStackTrace();
				}
				request.setAttribute("setSubCategAndTechns", "setSubCategoryAndTecnhicians");
				
				///MAIL ID OF USER
				String mail="";
				String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
				ResultSet rss=ad.selectQuery(a);
				try {
					if(rss.next())
					{
					mail=rss.getString("EMAIL_ID")	;
					if(!mail.equalsIgnoreCase("null"))
						help.setReqEmail(mail);
				
					}
					
					if(mail.equalsIgnoreCase("")||mail.equalsIgnoreCase("null"))
					{
						request.setAttribute("MAILA","MAILA");
					}
					else
					{
						request.setAttribute("MAILP","MAILP");	
					}
					
				} catch (SQLException e) {
							e.printStackTrace();
				}
				
				 //Category Setting
				
				ArrayList category=new ArrayList();
				ArrayList categoryShortName=new ArrayList();
				String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and c_cat_sh_name IN ("+cate+")";
				ResultSet rs2 = ad.selectQuery(cat);
				
				try {
					while (rs2.next())

					{
						category.add(rs2.getString("c_cat_name"));
						categoryShortName.add(rs2.getString("c_cat_sh_name"));
						
					}
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				
				help.setCategorylist(category);
				
				help.setCategortShortlist(categoryShortName);
				help.setRequestName(reqname);
				ArrayList roleList = new ArrayList();
				ArrayList roleLabelList = new ArrayList();
				String role = "select sap.role,sap.id,sap.department from Role_SAP as sap,location as loc where loc.LOCID = '"+user.getPlantId()+"' AND sap.plant=loc.location_code";
				ResultSet rs21 = ad.selectQuery(role);
				
				try {
					while (rs21.next())

					{
						roleList.add(rs21.getString("id"));
						roleLabelList.add(rs21.getString("Role")+"-"+rs21.getString("Department"));
						
					}
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				help.setRolelist(roleList);
				help.setRoleLabelList(roleLabelList);
		return mapping.findForward("sapuseridmanagement");
	}
	//------
	//arun
	public ActionForward sapDocumentCancellation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		IssuesForm help = (IssuesForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		String localhostname="";
		String ipaddress="";
		String username="";
		try {
			localhostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			ipaddress = (InetAddress.getLocalHost()).getHostAddress();
			username=(InetAddress.getLocalHost()).getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		ipaddress = request.getHeader("X-FORWARDED-FOR");   // proxy
		if(ipaddress==null)
		{
			ipaddress = request.getRemoteAddr();
		}
		String type=help.getRequestType();
		help.setRequestType(type);
		
		String empno="";
		int k=0;
		
	
		empno=help.getEmployeeno();
		if(empno==null)
			empno=user.getEmployeeNo();
		else{
			if((empno.equalsIgnoreCase(""))){
				empno=user.getEmployeeNo();
			}else{
			 empno=help.getEmployeeno();
			}
		}
		
		//chk valid employe no.
		
		String emp="Select Count(*) from emp_official_info where PERNR='"+empno+"'";
		ResultSet rsss= ad.selectQuery(emp);
		try {
			while(rsss.next())
			{
				 k=rsss.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(k==0)
		{
			help.setMessage("Please Enter Valid Employee Number");
			return mapping.findForward("displayform");
		}
			help.setEmployeeno(empno);
			try {
				ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location order by LOCID");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				help.setLocationIdList(locationList);
				help.setLocationLabelList(locationLabelList);	
				ad.connClose();
				
				ArrayList deparmentList=new ArrayList();
				ArrayList departmentIdList=new ArrayList();
				
				ResultSet rs12 =ad.selectQuery("select DPTID,DPTSTXT from department order by DPTSTXT");
				while(rs12.next()){
					departmentIdList.add(rs12.getString("DPTID"));
					deparmentList.add(rs12.getString("DPTSTXT"));
				}
				rs12.close();
				ad.connClose();
				help.setDepartmentIdList(departmentIdList);
				help.setDepartmentList(deparmentList);
			} catch (SQLException e) {
			  e.printStackTrace();
			}
			IssueImpl impl=new IssueImpl();
			List IssuesList=new LinkedList();
			IssuesList=impl.getAllRequest(user.getEmployeeNo(),form,request,user.getPlantId(),"");
			if(IssuesList.size()>0)
				request.setAttribute("IssuesList", IssuesList);
			else
				request.setAttribute("noRecords", "noRecords");
			
			List technList=new LinkedList();
			impl.getTechnicanList(form, request);
		return mapping.findForward("sapDocumentCancellation");
	}
	public ActionForward sapnewIncidentform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		IssuesForm help = (IssuesForm) form;
		String forwardtype="";
		String localhostname="";
		String ipaddress="";
		String username="";
		
	
		help.setCategory("");
		help.setSubject("");
		help.setReason("");
		NewsandMediaDao ad=new NewsandMediaDao();
		try {
			int reqNo=0;
		String maxReqNo="Select max(Req_No) from IT_SAP_ISSUES";
		ResultSet rsMax=ad.selectQuery(maxReqNo);
		while(rsMax.next()){
			reqNo=1+rsMax.getInt(1);
		}
         help.setRequestNo(reqNo);		
         help.setRequestStatus("New");
		userDetails(mapping, form, request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(help.getEmployeeno().equals(""))
		{
			help.setEmployeeno(user.getEmployeeNo());
		}
		else
		{
			help.setEmployeeno(help.getEmployeeno());
		}
		
		///MAIL ID OF USER
		String mail="";
		String a="Select EMAIL_ID from emp_official_info where pernr='"+help.getEmployeeno()+"'";
		ResultSet rss=ad.selectQuery(a);
		try {
			if(rss.next())
			{
			mail=rss.getString("EMAIL_ID")	;
			if(!mail.equalsIgnoreCase("null"))
				help.setReqEmail(mail);
		
			}
			
			if(mail.equalsIgnoreCase("")||mail.equalsIgnoreCase("null"))
			{
				request.setAttribute("MAILA","MAILA");
			}
			else
			{
				request.setAttribute("MAILP","MAILP");	
			}
			
		} catch (SQLException e) {
					e.printStackTrace();
		}
       //Category Setting
		
		ArrayList category=new ArrayList();
		ArrayList categoryShortName=new ArrayList();
		String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and (c_cat_sh_name='DC' or c_cat_sh_name='PSAP' or c_cat_sh_name='SSAP' or c_cat_sh_name='AUTH')";
		ResultSet rs2 = ad.selectQuery(cat);
		
		try {
			while (rs2.next())

			{
				category.add(rs2.getString("c_cat_name"));
				categoryShortName.add(rs2.getString("c_cat_sh_name"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		help.setCategorylist(category);
		
		help.setCategortShortlist(categoryShortName);
		
		return mapping.findForward("sapnewincident");
	}
	
	public ActionForward getsapsubcategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session=request.getSession();
		IssuesForm help = (IssuesForm) form;
		UserInfo user=(UserInfo)session.getAttribute("user");
		String linkName = request.getParameter("linkName");		
		try {
			
			ArrayList subCategoryList = new ArrayList();
			
			String cat = "select distinct  c_sub_cat_name from CAT_SUBCAT_MASTER where c_cat_name = '"+linkName+"' and c_prefix ='SAP'";
			ResultSet rs2 = ad.selectQuery(cat);
			
			try {
				while (rs2.next())

				{
					subCategoryList.add(rs2.getString("c_sub_cat_name"));
					
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			
			help.setSubcatList(subCategoryList);
			help.setUsage(help.getUsage());
			help.setEmployeeno("");
			request.setAttribute("subCategoryList", subCategoryList);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//displayMainLinks(mapping, form, request, response);
		return mapping.findForward("subcategoryajax");
	
	}
	
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		IssuesForm masterForm=(IssuesForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList apprList=new LinkedList();
		IssuesForm help = (IssuesForm) form;
		String category=request.getParameter("category");
		String subCategory=request.getParameter("subCategory");
		String locNo=request.getParameter("locNo");
		String technician=request.getParameter("technician"); 
		String employee=request.getParameter("empno"); 
		
		
		String getReqestNumber="select MAX(Priority) as priority"
     		    	+" from Material_Approvers app,"
  				+"emp_official_info as emp"
				+" where Material_Type='"+category+"' and " 
    			+"Material_Sub_Category='"+subCategory+"' and "  
				+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"')";
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
		
		int i =0;
   
		
		int hodreq =0;
		String Sqlstring ="";
		
		String chkreq=" select c_hod_req from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and c_cat_name = '"+category+"' and c_sub_cat_name ='"+subCategory+"'";
		 ResultSet chk= ad.selectQuery(chkreq);
		 try {
			while(chk.next())
			 {
				 hodreq=chk.getInt(1);
			 }
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		if(hodreq == 1)
		{
			
		
	 Sqlstring ="select 0 as priority ," 
			         +"PERNR as employeeCode ," 
			         +"EMP_FULLNAME as employeeName ,"
			         +"DEPARTMENT.DPTLTXT as department,"
			         +"DESIGNATION.DSGLTXT as designation"
			         +" from emp_official_info,  DEPARTMENT,DESIGNATION ,ESS_Approvers"
			        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
			        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
			        +"emp_official_info.PERNR =  ESS_Approvers.ApproverId and "
			        +"ESS_Approvers.employeeNumber = '"+employee+"'"
			        +" union " 
                    +"select app.priority as priority,"
         		    +"PERNR as employeeCode ," 
         			+"EMP_FULLNAME as employeeName ,"
         		    +"DEPARTMENT.DPTLTXT as department,"
         			+"DESIGNATION.DSGLTXT as designation"
 					+" from Material_Approvers app,"
      				+"emp_official_info as emp ,DEPARTMENT,DESIGNATION"
  					+" where Material_Type='"+category+"' and " 
	    			+"Material_Sub_Category='"+subCategory+"' and "  
	    			+"emp.PERNR=app.Approver_Id and " 
					+"app.LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"') and role ='USER' and "
					+"emp.DPTID  = DEPARTMENT.DPTID and "
        			+"emp.DSGID  = DESIGNATION.DSGID" 
					+" Union "
					+"select '"+maxPriority+"' as priority ," 
			         +"PERNR as employeeCode ," 
			         +"EMP_FULLNAME as employeeName ,"
			         +"DEPARTMENT.DPTLTXT as department,"
			         +"DESIGNATION.DSGLTXT as designation"
			         +" from emp_official_info,  DEPARTMENT,DESIGNATION "
			        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
			        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
			        +"emp_official_info.PERNR = '"+technician+"'"
        			+" Order by 1";
		}
		else
		{
			 Sqlstring = "select app.priority as priority,"
        		    +"PERNR as employeeCode ," 
        			+"EMP_FULLNAME as employeeName ,"
        		    +"DEPARTMENT.DPTLTXT as department,"
        			+"DESIGNATION.DSGLTXT as designation"
					+" from Material_Approvers app,"
     				+"emp_official_info as emp ,DEPARTMENT,DESIGNATION"
 					+" where Material_Type='"+category+"' and " 
	    			+"Material_Sub_Category='"+subCategory+"' and "  
	    			+"emp.PERNR=app.Approver_Id and " 
					+"app.LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"') and role ='USER' and "
					+"emp.DPTID  = DEPARTMENT.DPTID and "
       			+"emp.DSGID  = DESIGNATION.DSGID" 
					+" Union "
					+"select '"+maxPriority+"' as priority ," 
			         +"PERNR as employeeCode ," 
			         +"EMP_FULLNAME as employeeName ,"
			         +"DEPARTMENT.DPTLTXT as department,"
			         +"DESIGNATION.DSGLTXT as designation"
			         +" from emp_official_info,  DEPARTMENT,DESIGNATION "
			        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
			        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
			        +"emp_official_info.PERNR = '"+technician+"'"
       			+" Order by 1";
			
		}
		
                  
        ResultSet rs=ad.selectQuery(Sqlstring);
		
		try{
			while(rs.next()){
				IssuesForm form1=new IssuesForm();
				i = i+1;
				form1.setPriority(i);
				//form1.setPriority(rs.getInt("priority"));
				form1.setEmployeeCode(rs.getString("employeeCode"));
				form1.setEmployeename(rs.getString("employeeName"));
				form1.setDepartment(rs.getString("department"));
				form1.setDesignation(rs.getString("designation"));
				apprList.add(form1);	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
	
		return mapping.findForward("displayApprovers");	
}	

	// sap submit req
	public ActionForward sapsubmitReq(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		//String maild=request.getParameter("EMAIL");

		//insert emial id
		/*if(!maild.equalsIgnoreCase("null")||!maild.equalsIgnoreCase(""))
		{
		String mail="Update emp_official_info set EMAIL_ID='"+maild+"' where pernr='"+user.getEmployeeNo()+"'";
		ad.SqlExecuteUpdate(mail);
		}*/
		
		EssDao ad=EssDao.dBConnection();
		IssuesForm help = (IssuesForm) form;
		ITMail email = new ITMail();
//		String category=help.getCategory();
		String subCategory=request.getParameter("subCategory");
		String technician=request.getParameter("technician");
		String category=request.getParameter("category");
	
		String locNo=request.getParameter("locNo");
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
	    String dateNow = ft.format(dNow);
	    String type=help.getRequestType();
	    Date dNow1 = new Date( );
		 SimpleDateFormat ft1 = new SimpleDateFormat ("dd/MM/yyyy");
	    String currendate = ft1.format(dNow1);
	    String empno="";
		empno=help.getEmployeeno();
		if((empno.equalsIgnoreCase("") )|| empno==null)
		{
		 empno=user.getEmployeeNo();
		}
		
		synchronized (this) {
	    int requestNo=help.getRequestNo();
	    int newReqNo=0;
	    int status=0;
	    int hodpresent=0;
	    String HODPresentId="";
		String HODPresentname="";
		int hodpresentinIt=0;
	    
	//check requestno
	String checkReqNo="select count(*) from IT_SAP_ISSUES where Req_No='"+requestNo+"'";
	try{
	ResultSet rsCheck=ad.selectQuery(checkReqNo);
	while(rsCheck.next()){
		status=rsCheck.getInt(1);
	}
	
	if(status>0){
		String maxReqNo="Select max(Req_No) from IT_SAP_ISSUES";
		ResultSet rsMax=ad.selectQuery(maxReqNo);
		while(rsMax.next()){
			requestNo=1+rsMax.getInt(1);
			newReqNo=requestNo;
		}
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	
	int hodreq =0;
	
	String chkreq=" select c_hod_req from CAT_SUBCAT_MASTER where c_prefix = 'SAP' and c_cat_sh_name = '"+category+"' and c_sub_cat_name ='"+subCategory+"'";
	 ResultSet chk= ad.selectQuery(chkreq);
	 try {
		while(chk.next())
		 {
			 hodreq=chk.getInt(1);
		 }
	} catch (SQLException e1) {
		
		e1.printStackTrace();
	}
	
	 String pendingApprovers="";
	if(hodreq ==1)
	 {
				
			String chkhod="select count(*) from ESS_Approvers where employeeNumber='"+empno+"'  ";
			 ResultSet rs1= ad.selectQuery(chkhod);
			 try {
				while(rs1.next())
				 {
					 hodpresent=rs1.getInt(1);
				 }
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
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
			 
			 String getReqestNumber="select MAX(Priority) as priority"
				    	+" from Material_Approvers app,"
					+"emp_official_info as emp"
					+" where Material_Type='"+category+"' and " 
					+"Material_Sub_Category='"+subCategory+"' and "  
					+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"')";
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
			maxPriority+=1;
			
		
			 
		if(hodpresent == 1)
			 {   
				       String getApproverID ="select 1 as priority ,"
				         +"PERNR as employeeNo ,"   
				         +"EMP_FULLNAME as employeeName "
				         +" from emp_official_info,  DEPARTMENT,DESIGNATION ,ESS_Approvers"
				        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
				        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
				        +"emp_official_info.PERNR =  ESS_Approvers.ApproverId and "
				        +"ESS_Approvers.employeeNumber = '"+empno+"'"
				        +" union " 
			            +"select app.priority+1 as priority,"
			            +"PERNR as employeeNo ,"
			 			+"EMP_FULLNAME as employeeName "
							+" from Material_Approvers app,"
							+"emp_official_info as emp ,DEPARTMENT,DESIGNATION"
							+" where Material_Type='"+category+"' and " 
						+"Material_Sub_Category='"+subCategory+"' and "  
						+"emp.PERNR=app.Approver_Id and " 
						+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"') and role ='USER' and "
						+"emp.DPTID  = DEPARTMENT.DPTID and "
						+"emp.DSGID  = DESIGNATION.DSGID" 
						+" Union "
						+"select '"+maxPriority+"' as priority ,"
						+"PERNR as employeeNo ," 
				         +"EMP_FULLNAME as employeeName "
				         +" from emp_official_info,  DEPARTMENT,DESIGNATION "
				        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
				        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
				        +"emp_official_info.PERNR = '"+technician+"'"
						+" Order by 1";
				 
			 
				    ResultSet approverRS=ad.selectQuery(getApproverID);
					try {
						while(approverRS.next()){
								//int priority = approverRS.getInt("priority");
								
								if(pendingApprovers.equalsIgnoreCase("")){
								pendingApprovers = approverRS.getString("employeeName");
								}
								else{
									pendingApprovers = pendingApprovers + " ," +approverRS.getString("employeeName");
								}
								
						}
						}
					catch (SQLException e) {
			                e.printStackTrace();
			            }
			 
			 
			 
			 String saveIssue="INSERT INTO IT_SAP_ISSUES(Req_No,Req_date,Emp_No,Email_ID,Ext_No,IP_Phone,HostName,IPAddress,"
			+ "Req_type,Req_Status,Req_Priority,ITEng_Status,Subject,Reason,Last_approver,Pending_approver,approved_date,Priority,Sub_Category,mode,Location,read_Status,AssignTo,SLA_Status,raisedBy) "
			+ "values('"+requestNo+"','"+dateNow+"','"+help.getEmpno()+"','"+help.getEmpEmailID()+"','"+help.getExtno()+"','"+help.getIpPhoneno()+"',"
			+ "'','"+help.getIPNumber()+"','"+category+"','Pending','"+help.getIssuePriority()+"','Pending','"+help.getSubject()+"','"+help.getReason()+"','','"+pendingApprovers+"','','1','"+subCategory+"','"+help.getMode()+"','"+help.getLocNo()+"','2','"+technician+"','','"+user.getEmployeeNo()+"')";
			 int i=ad.SqlExecuteUpdate(saveIssue);
			 
			 if(i>0){
				 //send to Hod
			 String sendReq="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
							"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
							"'"+currendate+"','Pending','','"+HODPresentId+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
			 
			 int k = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", HODPresentId);
							
			 int j=ad.SqlExecuteUpdate(sendReq);
			     ad.connClose();
			     if(i>0){
				 help.setMessage("Request '"+requestNo+"'  has been Created and Submitted for Process");
			     }
			     else 
			     {
			     help.setMessage("Request '"+requestNo+"'  has been Created and Submitted for Process");	 
			     }
			     
				// ITMail mail=new ITMail();
				// mail.sendMailToApprover(request, requestNo, "Issues", technician);
				 
					
				}
			 }	
		else
			{
			help.setMessage("Request not sent HOD Not assigned Contact Admin ");
			}
			}
else
{
	
	String getReqestNumber="select MAX(Priority) as priority"
	    	+" from Material_Approvers app,"
		+"emp_official_info as emp"
		+" where Material_Type='"+category+"' and " 
		+"Material_Sub_Category='"+subCategory+"' and "  
		+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"')";
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
		maxPriority+=1;

	String getApproverID =
           "select app.priority+1 as priority,"
           +"PERNR as employeeNo ,"
			+"EMP_FULLNAME as employeeName "
				+" from Material_Approvers app,"
				+"emp_official_info as emp ,DEPARTMENT,DESIGNATION"
				+" where Material_Type='"+category+"' and " 
			+"Material_Sub_Category='"+subCategory+"' and "  
			+"emp.PERNR=app.Approver_Id and " 
			+"LOCATION=(select LOCATION_CODE FROM Location WHERE LOCID ='"+locNo+"') and role ='USER' and "
			+"emp.DPTID  = DEPARTMENT.DPTID and "
			+"emp.DSGID  = DESIGNATION.DSGID" 
			+" Union "
			+"select '"+maxPriority+"' as priority ,"
			+"PERNR as employeeNo ," 
	         +"EMP_FULLNAME as employeeName "
	         +" from emp_official_info,  DEPARTMENT,DESIGNATION "
	        +" where emp_official_info.DPTID  = DEPARTMENT.DPTID and "
	        +"emp_official_info.DSGID  = DESIGNATION.DSGID and "
	        +"emp_official_info.PERNR = '"+technician+"'"
			+" Order by 1";
	 

	    ResultSet approverRS=ad.selectQuery(getApproverID);
		try {
			while(approverRS.next()){
					//int priority = approverRS.getInt("priority");
					
					if(pendingApprovers.equalsIgnoreCase("")){
					pendingApprovers = approverRS.getString("employeeName");
					}
					else{
						pendingApprovers = pendingApprovers + " ," +approverRS.getString("employeeName");
					}
					
			}
			}
		catch (SQLException e) {
               e.printStackTrace();
           }



String saveIssue="INSERT INTO IT_SAP_ISSUES(Req_No,Req_date,Emp_No,Email_ID,Ext_No,IP_Phone,HostName,IPAddress,"
+ "Req_type,Req_Status,Req_Priority,ITEng_Status,Subject,Reason,Last_approver,Pending_approver,approved_date,Priority,Sub_Category,mode,Location,read_Status,AssignTo,SLA_Status,raisedBy) "
+ "values('"+requestNo+"','"+dateNow+"','"+help.getEmpno()+"','"+help.getEmpEmailID()+"','"+help.getExtno()+"','"+help.getIpPhoneno()+"',"
+ "'','"+help.getIPNumber()+"','"+category+"','Pending','"+help.getIssuePriority()+"','Pending','"+help.getSubject()+"','"+help.getReason()+"','','"+pendingApprovers+"','','1','"+subCategory+"','"+help.getMode()+"','"+help.getLocNo()+"','2','"+technician+"','','"+user.getEmployeeNo()+"')";
int i=ad.SqlExecuteUpdate(saveIssue);


if(i>0){
	 help.setMessage("Request '"+requestNo+"'  has been Created and Submitted for Process");
    }
    else 
    {
    help.setMessage("Request '"+requestNo+"'  has been Created and Submitted for Process");	 
    }	
	
	
	
	
	///new
    int presentPriorityCreator = 0;
	String Approver_Id_New ="";
	String role_New ="";
	String Parllel_Approver_1_New ="";
	String Parllel_Approver_2_New ="";
	
	String getpriotiy3 ="select Priority , Approver_Id, Parllel_Approver_1,Parllel_Approver_2 , Role from Material_Approvers"
            		   +" where Location = (select LOCATION_CODE FROM Location WHERE LOCID ='"+user.getPlantId()+"')"
            		   +" and Material_sub_category = '"+subCategory+"'"
            		   +" and Priority = '1' "
            		   +" and Material_Type= '"+category+"'" ;
	
	
	ResultSet rs2=ad.selectQuery(getpriotiy3);
	
	try{
       	
		if (rs2.next()){
			presentPriorityCreator = rs2.getInt("Priority");
			Approver_Id_New = rs2.getString("Approver_Id");
			role_New = rs2.getString("role");
			Parllel_Approver_1_New = rs2.getString("Parllel_Approver_1");
			Parllel_Approver_2_New = rs2.getString("Parllel_Approver_2");
			
		}
		}catch (SQLException e) {
		
		e.printStackTrace(); }
   
	  
	   if (!role_New.equalsIgnoreCase("Creator")){
		   
		 
		   
		   
	   if(!Approver_Id_New.equalsIgnoreCase("")) {
	   
	   String sendReq16="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
				"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
				"'"+currendate+"','Pending','','"+Approver_Id_New+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
	   int ii16=ad.SqlExecuteUpdate(sendReq16);
	   
	   
	   int j = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", Approver_Id_New);
	   }
	   
	   if(!Parllel_Approver_1_New.equalsIgnoreCase("")) {
	   String sendReq15="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
				"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
				"'"+currendate+"','Pending','','"+Parllel_Approver_1_New+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
	       int ii15=ad.SqlExecuteUpdate(sendReq15);
	       
	       int j = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", Parllel_Approver_1_New);
	   }
	   
	 
	   
	   if(!Parllel_Approver_2_New.equalsIgnoreCase("")) {
    	   String sendReq14="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
					"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
					"'"+currendate+"','Pending','','"+Parllel_Approver_2_New+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
   	   int ii10=ad.SqlExecuteUpdate(sendReq14);
   	   
   	   int j = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", Parllel_Approver_2_New);
       
   	
    	   }
	   
	   }
	   
	   //next level is creator den sending mail to oly technician 
	   if (role_New.equalsIgnoreCase("Creator")){
		   
		   if(!technician.equalsIgnoreCase("")) {
	    	   
	    	   String sendReq16="insert into All_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver," +
						"Pending_Approver,Approved_Persons,Requester_Id,type) values('"+requestNo+"','IT Sap Request','"+empno+"'," +
						"'"+currendate+"','Pending','','"+technician+"','','"+user.getEmployeeNo()+"','IT Sap Request')";
	    	   int ii16=ad.SqlExecuteUpdate(sendReq16);
	    	   
	    	   
	    	   
	    	   int j = email.sendMailToSapApprover(request, requestNo, "IT Sap Request", technician);

	           
		   
	   }
	   }  
	
	   
	   
	}			
		
	
	
}
		
	    sapnewIncidentform(mapping, form, request, response);
		return mapping.findForward("sapnewincident");
 }



}
