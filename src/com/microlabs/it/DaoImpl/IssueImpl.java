package com.microlabs.it.DaoImpl;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.it.form.IssuesForm;
import com.microlabs.utilities.UserInfo;

public class IssueImpl {
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
	public void getTechnicanList(ActionForm form,HttpServletRequest request){
		IssuesForm help = (IssuesForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List technIDs=new LinkedList();
		List technNames=new LinkedList();
		String getTechnicians="select it.Approver_ID,emp.EMP_FULLNAME from IT_Approvers as it,emp_official_info as emp  "
		+ "where Category!='IT SERVICES' and it.LOCATION_ID='"+user.getPlantId()+"' and emp.PERNR=it.Approver_ID group by it.Approver_ID,emp.EMP_FULLNAME ";
		ResultSet rs=ad.selectQuery(getTechnicians);
		try{
		while(rs.next()){
		technIDs.add(rs.getInt("Approver_ID"));
		technNames.add(rs.getString("EMP_FULLNAME"));
		}
		help.setTechIDs(technIDs);
		help.setTechNames(technNames);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public List getSearchIssuesByLocation(ActionForm form1,HttpServletRequest request){
		IssuesForm help = (IssuesForm) form1;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List allReq=new LinkedList();
		String chooseType=help.getChooseType();
		String location=help.getLocationId();
		String reqNo=help.getChooseKeyword();
		int  totalRecords=0;
	    int  startRecord=0;
	    int  endRecord=0;
		if(chooseType.equals("All Requests") || chooseType.equals("Open Requests")||chooseType.equals("Completed")){
		    try{
		    String getTotalRecords="select COUNT(*) from IT_ISSUES as it "
		    + "where it.Location='"+location+"' ";
		   if(chooseType.equals("Open Requests")||chooseType.equals("Completed")){
		if(chooseType.equals("Open Requests"))  
		      getTotalRecords=getTotalRecords+" and (it.Req_Status!='New' and it.Req_Status!='Completed') ";
		if(chooseType.equals("Completed"))  
			getTotalRecords=getTotalRecords+" and it.Req_Status='Completed' ";
		   }
		    ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
			  while(rsTotalRecods.next())
			  {
				  totalRecords=rsTotalRecods.getInt(1);
			  }
			  if(totalRecords>=10)
			  {
				  help.setTotalRecords(totalRecords);
			  startRecord=1;
			  endRecord=10;
			  help.setStartRecord(1);
			  help.setEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords;
				  help.setTotalRecords(totalRecords);
				  help.setStartRecord(1);
				  help.setEndRecord(totalRecords); 
			  }
			String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
			+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp "
			+ "where it.Req_No=all_r.Req_Id and  it.Location='"+location+"'  ";
			 if(chooseType.equals("Open Requests")||chooseType.equals("Completed")){
					if(chooseType.equals("Open Requests"))  
						getAllReq=getAllReq+" and (it.Req_Status!='New' and it.Req_Status!='Completed') ";
					if(chooseType.equals("Completed"))  
						getAllReq=getAllReq+" and it.Req_Status='Completed'";
			 }
			 getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
			+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject) as  sub Where  sub.RowNum between 1 and 10";
			ResultSet rs=ad.selectQuery(getAllReq);
			while(rs.next()){
				IssuesForm form=new IssuesForm();
				form.setRequestNo(rs.getInt("Req_Id"));
				form.setLocation(rs.getString("LOCID"));
				form.setEmployeename(rs.getString("EMP_FULLNAME"));
				form.setCategory(rs.getString("Req_Type"));
				form.setSubcategory(rs.getString("Sub_Category"));
				form.setSubject(rs.getString("Subject"));
				String ReqDate=rs.getString("Req_date");
				String a[]=ReqDate.split(" "); 
				ReqDate=a[0];
				String b[]=ReqDate.split("-");
				ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
				form.setReqDate(ReqDate);
				form.setRequestStatus(rs.getString("Req_Status"));
				form.setReqPriority(rs.getString("Req_Priority"));
				form.setReadStatus(rs.getInt("read_Status"));
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
				form.setAssignTo(techNames);
				allReq.add(form);
			}
			ad.connClose();
			if(allReq.size()==0){
				request.setAttribute("noRecords", "noRecords");
				help.setMessage("No Records Found");
				
			}
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			}catch(Exception e){
				e.printStackTrace();
			}
			return allReq;
		}
		return allReq;
	}
	public List getSearchDetails(ActionForm form1,HttpServletRequest request){
		IssuesForm help = (IssuesForm) form1;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		List allReq=new LinkedList();
		String chooseType=help.getChooseType();
		String location=help.getLocationId();
		String reqNo=help.getChooseKeyword();
		int  totalRecords=0;
	    int  startRecord=0;
	    int  endRecord=0;
		if(chooseType.equals("My All Requests")||chooseType.equals("My Open Requests")||chooseType.equals("My Completed Requests")){
			try{
			 String getTotalRecords="select count(*) from IT_ISSUES as it,emp_official_info as emp,Location as loc where it.Emp_No='"+user.getEmployeeNo()+"'  "
			+ "and emp.PERNR=it.Emp_No and loc.LOCATION_CODE=emp.LOCID  ";
			 
			if(chooseType.equals("My Open Requests"))
				getTotalRecords=getTotalRecords+" and  it.Req_Status!='Completed' ";
			if(chooseType.equals("My Completed Requests"))
				getTotalRecords=getTotalRecords+"and  it.Req_Status='Completed' ";
			ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
             while(rsTotalRecods.next())
			  {
				  totalRecords=rsTotalRecods.getInt(1);
			  }
			  if(totalRecords>=10)
			  {
				  help.setTotalRecords(totalRecords);
			  startRecord=1;
			  endRecord=10;
			  help.setStartRecord(1);
			  help.setEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords;
				  help.setTotalRecords(totalRecords);
				  help.setStartRecord(1);
				  help.setEndRecord(totalRecords); 
			  }
			String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,"
			+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp,Location as loc where it.Emp_No='"+user.getEmployeeNo()+"'  "
			+ "and emp.PERNR=it.Emp_No and loc.LOCATION_CODE=emp.LOCID ";
			/* if(!(location.equals("")))
				 getAllReq=getAllReq+" and  loc.LOCID='"+location+"'";
			 if(!(reqNo.equals("")))
				 getAllReq=getAllReq+" and  it.Req_No='"+reqNo+"' ";*/
			if(chooseType.equals("My Open Requests"))
				getAllReq=getAllReq+"and  it.Req_Status!='Completed' ";
			if(chooseType.equals("My Completed Requests"))
				getAllReq=getAllReq+"and  it.Req_Status='Completed' ";
			getAllReq=getAllReq+" ) as  sub Where  sub.RowNum between 1 and 10";
			ResultSet rs=ad.selectQuery(getAllReq); 
			while(rs.next()){
				IssuesForm form=new IssuesForm();
				form.setRequestNo(rs.getInt("Req_No"));
				form.setLocation(rs.getString("LOCID"));
				form.setEmployeename(rs.getString("EMP_FULLNAME"));
				form.setCategory(rs.getString("Req_Type"));
				form.setSubcategory(rs.getString("Sub_Category"));
				form.setSubject(rs.getString("Subject"));
				String ReqDate=rs.getString("Req_date");
				String a[]=ReqDate.split(" "); 
				ReqDate=a[0];
				String b[]=ReqDate.split("-");
				ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
				form.setReqDate(ReqDate);
				form.setRequestStatus(rs.getString("Req_Status"));
				form.setReqPriority(rs.getString("Req_Priority"));
				form.setReadStatus(rs.getInt("read_Status"));
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
				form.setAssignTo(techNames);
				form.setSlaStatus(rs.getString("SLA_Status"));
				allReq.add(form);
			}
			ad.connClose();
			if(allReq.size()==0){
				request.setAttribute("noRecords", "noRecords");
				help.setMessage("No Records Found");
			
			}
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(chooseType.equals("All Requests") || chooseType.equals("Open Requests")||chooseType.equals("Completed")){
		    try{
		    String getTotalRecords="select COUNT(*) from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp "
		    + "where it.Req_No=all_r.Req_Id and (all_r.Technician_Id='"+user.getEmployeeNo()+"' ) and emp.PERNR=all_r.Requester_Id";
		   if(chooseType.equals("Open Requests")||chooseType.equals("Completed")){
		if(chooseType.equals("Open Requests"))  
		      getTotalRecords=getTotalRecords+" and  it.Req_Status!='Completed' ";
		if(chooseType.equals("Completed"))  
			getTotalRecords=getTotalRecords+" and it.Req_Status='Completed' ";
		   }
		    ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
			  while(rsTotalRecods.next())
			  {
				  totalRecords=rsTotalRecods.getInt(1);
			  }
			  if(totalRecords>=10)
			  {
				  help.setTotalRecords(totalRecords);
			  startRecord=1;
			  endRecord=10;
			  help.setStartRecord(1);
			  help.setEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords;
				  help.setTotalRecords(totalRecords);
				  help.setStartRecord(1);
				  help.setEndRecord(totalRecords); 
			  }
			String getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
			+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,it.SLA_Status from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp "
			+ "where it.Req_No=all_r.Req_Id and all_r.Technician_Id='"+user.getEmployeeNo()+"'";
			 if(chooseType.equals("Open Requests")||chooseType.equals("Completed")){
					if(chooseType.equals("Open Requests"))  
						getAllReq=getAllReq+" and it.Req_Status!='Completed'";
					if(chooseType.equals("Completed"))  
						getAllReq=getAllReq+" and it.Req_Status='Completed'";
			 }
			 getAllReq=getAllReq +" and emp.PERNR=all_r.Requester_Id) as  sub Where  sub.RowNum between 1 and 10";
			ResultSet rs=ad.selectQuery(getAllReq);
			while(rs.next()){
				IssuesForm form=new IssuesForm();
				form.setRequestNo(rs.getInt("Req_Id"));
				form.setLocation(rs.getString("LOCID"));
				form.setEmployeename(rs.getString("EMP_FULLNAME"));
				form.setCategory(rs.getString("Req_Type"));
				form.setSubcategory(rs.getString("Sub_Category"));
				form.setSubject(rs.getString("Subject"));
				String ReqDate=rs.getString("Req_date");
				String a[]=ReqDate.split(" "); 
				ReqDate=a[0];
				String b[]=ReqDate.split("-");
				ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
				form.setReqDate(ReqDate);
				form.setRequestStatus(rs.getString("Req_Status"));
				form.setReqPriority(rs.getString("Req_Priority"));
				form.setReadStatus(rs.getInt("readStatus"));
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
				form.setAssignTo(techNames);
				form.setSlaStatus(rs.getString("SLA_Status"));
				allReq.add(form);
			}
			ad.connClose();
			if(allReq.size()==0){
				request.setAttribute("noRecords", "noRecords");
				help.setMessage("No Records Found");
		
			}
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			}catch(Exception e){
				e.printStackTrace();
			}
			return allReq;
		}
		if(chooseType.equals("Open Requests")||chooseType.equals("Completed Requests")){
			
		}
		return allReq;
	}
	public List getConversationList(int reqNo,ActionForm form,HttpServletRequest request){
		List convList=new LinkedList();
		InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		try{
			Properties props = new Properties();
	 	 props.load(in);
		 in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	 	String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
		String getDetails="select emp.EMP_FULLNAME,it.Remarks,CONVERT(varchar(10),it.Transaction_Date,103)+' '+ CONVERT(varchar(5),it.Transaction_Date,108) as Transaction_Date1,it.Sequence,it.Status from IT_Issue_Trans as it,emp_official_info"
		+ " as emp where it.Req_No='"+reqNo+"' and it.Emp_No=emp.PERNR ORDER BY it.Transaction_Date";
		ResultSet rs=ad.selectQuery(getDetails);
	
		while(rs.next()){
			IssuesForm convDet=new IssuesForm();
			convDet.setEmployeename(rs.getString("EMP_FULLNAME"));
			convDet.setComments(rs.getString("Remarks"));
			String approveDate=rs.getString("Transaction_Date1");
			/*String a[]=approveDate.split(" ");
			approveDate=a[0];
			String b[]=approveDate.split("-");
			approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
			convDet.setReqDate(approveDate);
			convDet.setRequestStatus(rs.getString("Status"));
			int transNo=rs.getInt("Sequence");
			String getFiles="select * from Issues_solution_document where request_no='"+reqNo+"' and sequence_no='"+transNo+"'";
			ResultSet rsFiles=ad.selectQuery(getFiles);
			String fileName="";
			while(rsFiles.next()) {
				fileName=fileName+rsFiles.getString("file_name")+",";
			}
			System.out.println("fileName="+fileName);
			convDet.setFileName(fileName);
			convList.add(convDet);
		}
		if(convList.size()>0){
			request.setAttribute("convList", convList);
			
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return convList;
	}
	
	public List getApprovalStatus(int reqNo,ActionForm form,HttpServletRequest request,String emp){
		IssuesForm help = (IssuesForm) form;
		List approvalDetails=new LinkedList();
		String getAppDet="select all_r.Technician_Id,dep.DPTSTXT,emp.EMP_FULLNAME,all_r.Req_Status,CONVERT(varchar(10),all_r.approved_date,103)+' '+ CONVERT(varchar(5),all_r.approved_date,108) as approved_date1,all_r.approved_date,all_r.Comments"
				+ " from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep where it.Req_No='"+reqNo+"' "
				+ "and dep.DPTID=emp.DPTID and it.Req_No=all_r.Req_Id and emp.PERNR=all_r.Technician_Id group by all_r.Technician_Id,dep.DPTSTXT,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.Comments order by isnull(all_r.approved_date,getdate())";
		ResultSet rs=ad.selectQuery(getAppDet);
		try{
		while(rs.next()){
			IssuesForm appDet=new IssuesForm();
			appDet.setTechnicianID(rs.getString("Technician_Id"));
			appDet.setEmployeename(rs.getString("EMP_FULLNAME"));
			appDet.setDepartment(rs.getString("DPTSTXT"));
			String approveDate=rs.getString("approved_date1");
			if(approveDate!=null){
			/*String a[]=approveDate.split(" "); 
			approveDate=a[0];
			String b[]=approveDate.split("-");
			approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
			}
			appDet.setApprovedDate(approveDate);
			appDet.setRequestStatus(rs.getString("Req_Status"));
			appDet.setComments(rs.getString("Comments"));
		
		
			
			approvalDetails.add(appDet);
			
		}
		request.setAttribute("approverDetails", approvalDetails);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return approvalDetails;
	}
	
	public List getRequesterDetails(int reqNo,ActionForm form,HttpServletRequest request){/*
		IssuesForm help = (IssuesForm) form;
		List requseterDetails=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String getDetails="select CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Req_date1,emp.PERNR,emp.EMP_FULLNAME,emp.LOCID,emp.EMAIL_ID,it.Ext_No,it.Req_No,it.IP_Phone,"
		+ "it.IPAddress,it.Req_type,it.Req_Status,it.Req_Priority,it.Subject,it.Reason,it.Sub_Category,it.mode,dep.DPTSTXT,"
		+ "desg.DSGSTXT from IT_ISSUES as it,emp_official_info as emp,DEPARTMENT dep,DESIGNATION desg where it.Req_No='"+reqNo+"' and "
		+ "emp.PERNR=it.Emp_No and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID";
		ResultSet rs=ad.selectQuery(getDetails);
		try{
			String requesterNo="";
		while(rs.next()){
			IssuesForm form1=new IssuesForm();
			form1.setEmployeeno(rs.getString("PERNR"));
			requesterNo=rs.getString("PERNR");
			form1.setEmployeename(rs.getString("EMP_FULLNAME"));
			form1.setLocation(rs.getString("LOCID"));
			form1.setEmpEmailID(rs.getString("EMAIL_ID"));
			form1.setExtno(rs.getString("Ext_No"));
			form1.setRequestNo(rs.getInt("Req_No"));
			help.setRequestNo(rs.getInt("Req_No"));
			form1.setIPNumber(rs.getString("IPAddress"));
			form1.setIpPhoneno(rs.getString("IP_Phone"));
			form1.setCategory(rs.getString("Req_type"));
			form1.setSubcategory(rs.getString("Sub_Category"));
			help.setCategory(rs.getString("Req_type"));
			help.setSubcategory(rs.getString("Sub_Category"));
			form1.setIssuePriority(rs.getString("Req_Priority"));
			help.setRequestStatus(rs.getString("Req_Status"));
			form1.setSubject(rs.getString("Subject"));
			form1.setReason(rs.getString("Reason"));
			form1.setMode(rs.getString("mode"));
			form1.setReqDate(rs.getString("Req_date1"));
			form1.setRequesterdepartment(rs.getString("DPTSTXT"));
			form1.setRequesterdesignation(rs.getString("DSGSTXT"));
			requseterDetails.add(form1);
		}
		ad.closeResultset();
		 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 Properties props = new Properties();
	 	 props.load(in);
		 in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	 	String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
		String sql1="select * from Issues_document where request_no='"+reqNo+"' and user_id='"+requesterNo+"'";
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
			
			request.setAttribute("requesterDetails", a1);
		System.out.println("Emp Type="+help.getEmpType());
		
		if(help.getEmpType().equals("Technician") && help.getRequestStatus().equals("New")){
			String updateStatus="update IT_ISSUES set Req_Status='In Process' where Req_No='"+reqNo+"' and Req_Status='New' ";
			ad.SqlExecuteUpdate(updateStatus);
			 updateStatus="update IT_All_ISSUES set Req_Status='In Process',readStatus=1 where Req_Id='"+reqNo+"' and Req_Status='New' and "
			 		+ "Technician_Id='"+user.getEmployeeNo()+"'";
			ad.SqlExecuteUpdate(updateStatus);
			help.setRequestStatus("In Process");
		}if(help.getEmpType().equals("Technician")){
			String updateStatus="update IT_All_ISSUES set readStatus=1 where Req_Id='"+reqNo+"' ";
			ad.SqlExecuteUpdate(updateStatus);
		}
		if(help.getEmpType().equals("Requester")){
			String updateStatus="update IT_ISSUES set read_Status=1 where Req_No='"+reqNo+"' ";
			ad.SqlExecuteUpdate(updateStatus);
		}
		
		request.setAttribute("requseterDetails", requseterDetails);
		}catch(Exception e){
			e.printStackTrace();
		}
		return requseterDetails;
	*/

		IssuesForm help = (IssuesForm) form;
		List requseterDetails=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String getDetails="select CONVERT(varchar(10),Req_date,103)+' '+ CONVERT(varchar(5),Req_date,108) as Req_date1,emp.PERNR,emp.EMP_FULLNAME,emp.LOCID,emp.EMAIL_ID,it.Ext_No,it.Req_No,it.IP_Phone,"
		+ "it.IPAddress,it.Req_type,it.Req_Status,it.raisedBy,it.behalf_comments,it.Req_Priority,it.Subject,it.Reason,it.Sub_Category,it.mode,dep.DPTSTXT,"
		+ "desg.DSGSTXT from IT_ISSUES as it,emp_official_info as emp,DEPARTMENT dep,DESIGNATION desg where it.Req_No='"+reqNo+"' and "
		+ "emp.PERNR=it.Emp_No and emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID";
		ResultSet rs=ad.selectQuery(getDetails);
		try{
			String requesterNo="";
		while(rs.next()){
			IssuesForm form1=new IssuesForm();
			form1.setEmployeeno(rs.getString("PERNR"));
			requesterNo=rs.getString("PERNR");
			form1.setEmployeename(rs.getString("EMP_FULLNAME"));
			form1.setLocation(rs.getString("LOCID"));
			form1.setEmpEmailID(rs.getString("EMAIL_ID"));
			form1.setExtno(rs.getString("Ext_No"));
			form1.setRequestNo(rs.getInt("Req_No"));
			help.setRequestNo(rs.getInt("Req_No"));
			form1.setIPNumber(rs.getString("IPAddress"));
			form1.setIpPhoneno(rs.getString("IP_Phone"));
			form1.setCategory(rs.getString("Req_type"));
			form1.setSubcategory(rs.getString("Sub_Category"));
			help.setCategory(rs.getString("Req_type"));
			help.setSubcategory(rs.getString("Sub_Category"));
			form1.setIssuePriority(rs.getString("Req_Priority"));
			help.setRequestStatus(rs.getString("Req_Status"));
			form1.setSubject(rs.getString("Subject"));
			form1.setReason(rs.getString("Reason"));
			form1.setOncomments(rs.getString("behalf_comments"));
			form1.setMode(rs.getString("mode"));
			if(!rs.getString("raisedBy").equalsIgnoreCase(rs.getString("PERNR"))){
				String onemployee=rs.getString("raisedBy");
				String ongetDetails1="select emp.EMP_FULLNAME,dep.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as desg  where emp.DPTID=dep.DPTID and emp.DSGID=desg.DSGID and pernr='"+onemployee+"' ";
				ResultSet onrs=ad.selectQuery(ongetDetails1);
				try{
				while(onrs.next()){
					form1.setOnbename(onrs.getString("EMP_FULLNAME"));
					form1.setOnbedepartment(onrs.getString("DPTSTXT"));
					form1.setOnbedesignation(onrs.getString("DSGSTXT"));
					request.setAttribute("showReqReply12", "subCategoryList1");
				}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			
			form1.setReqDate(rs.getString("Req_date1"));
			form1.setRequesterdepartment(rs.getString("DPTSTXT"));
			form1.setRequesterdesignation(rs.getString("DSGSTXT"));
			requseterDetails.add(form1);
		}
		ad.closeResultset();
		 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 Properties props = new Properties();
	 	 props.load(in);
		 in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles");
	 	String filePath=uploadFilePath+"/EMicro Files/IT/Help Desk/Issues/UploadFiles";
		String sql1="select * from Issues_document where request_no='"+reqNo+"' and user_id='"+requesterNo+"'";
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
			
			request.setAttribute("requesterDetails", a1);
		System.out.println("Emp Type="+help.getEmpType());
		
		if(help.getEmpType().equals("Technician") && help.getRequestStatus().equals("New")){
			String updateStatus="update IT_ISSUES set Req_Status='In Process' where Req_No='"+reqNo+"' and Req_Status='New' ";
			ad.SqlExecuteUpdate(updateStatus);
			 updateStatus="update IT_All_ISSUES set Req_Status='In Process',readStatus=1 where Req_Id='"+reqNo+"' and Req_Status='New' and "
			 		+ "Technician_Id='"+user.getEmployeeNo()+"'";
			ad.SqlExecuteUpdate(updateStatus);
			help.setRequestStatus("In Process");
		}if(help.getEmpType().equals("Technician")){
			String updateStatus="update IT_All_ISSUES set readStatus=1 where Req_Id='"+reqNo+"' ";
			ad.SqlExecuteUpdate(updateStatus);
		}
		if(help.getEmpType().equals("Requester")){
			String updateStatus="update IT_ISSUES set read_Status=1 where Req_No='"+reqNo+"' ";
			ad.SqlExecuteUpdate(updateStatus);
		}
		
		request.setAttribute("requseterDetails", requseterDetails);
		}catch(Exception e){
			e.printStackTrace();
		}
		return requseterDetails;
		
	
	}
	
	public List getAllRequest(String empNo,ActionForm form1,HttpServletRequest request,String loc,String view){
		IssuesForm helpForm = (IssuesForm) form1;
		
		int  totalRecords=0;
	    int  startRecord=0;
	    int  endRecord=0;
	    List allReq=new LinkedList();
	   
	    try{
	    String empType="";	
	    //check login User Type	
	    String checkEmpType="select COUNT(*) from IT_Approvers where Approver_ID='"+empNo+"' and Category!='IT SERVICES'";
	    ResultSet rsEmpType=ad.selectQuery(checkEmpType);
	    while(rsEmpType.next()){
	    	if(rsEmpType.getInt(1)>0){
	    		helpForm.setChooseType("All Requests");
	    		empType="Technician";
	    	}else{
	    		helpForm.setChooseType("My All Requests");
	    		empType="Requester";
	    	}
	    	helpForm.setEmpType(empType);
	    }
	    
	    String fromDate=helpForm.getFromDate();
	    String toDate=helpForm.getToDate();
	    if(!helpForm.getFromDate().equalsIgnoreCase(""))
	    {
	    String fromspac[]=fromDate.split(" ");
	    String from[]=fromspac[0].split("-");
	    String tospac[]=toDate.split(" ");
	    String to[]=tospac[0].split("-");
	    		
	helpForm.setFromDate(from[2]+"/"+from[1]+"/"+from[0]);
	helpForm.setToDate(to[2]+"/"+to[1]+"/"+to[0]);
	    }
	 
	    String getTotalRecords="";
	    
		String pendingtechn="";
	    
	
	    
	    if(empType.equals("Technician")){
	     getTotalRecords="select COUNT(distinct(it.Req_No)) from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep "
	    + "where it.Req_No=all_r.Req_Id and (all_r.Technician_Id='"+empNo+"' )  and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+empNo+"' and emp.PERNR=all_r.Requester_Id and emp.DPTID=dep.DPTID and it.Req_Status !='ON_Behalf'";
	     if(helpForm.getLocationId()==null)
	     {
	    	 helpForm.setLocationId(loc);
	     }
	    if(helpForm.getLocationId().equalsIgnoreCase(""))
	    {
	    	helpForm.setLocationId(loc);
	    }
	    if(!helpForm.getLocationId().equalsIgnoreCase("All"))
	    {
	    	 getTotalRecords=getTotalRecords+" and Location ='"+helpForm.getLocationId()+"' ";
	    }
		
	    if(!helpForm.getFromDate().equalsIgnoreCase(""))
	    {
	    	 getTotalRecords=getTotalRecords+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
	    }
	    
	    if(!helpForm.getMaincategory().equalsIgnoreCase(""))
	    {
	    	 getTotalRecords=getTotalRecords+" and it.Req_type ='"+helpForm.getMaincategory()+"' ";
	    }
	   
	    if(!helpForm.getMainrequestStatus().equalsIgnoreCase(""))
	    {
	    	 getTotalRecords=getTotalRecords+" and it.Req_Status ='"+helpForm.getMainrequestStatus()+"' ";
	    }
	    
	  
	    
	    if(!helpForm.getDepartment().equalsIgnoreCase(""))
	    {
	    	 getTotalRecords=getTotalRecords+" and dep.DPTID ='"+helpForm.getDepartment()+"' ";
	    }
	     
		 }else{
		   getTotalRecords="select COUNT(distinct(it.Req_No)) from IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep where  ( it.Emp_No='"+empNo+"') and emp.PERNR=it.Emp_No and emp.DPTID=dep.DPTID and it.Req_Status !='ON_Behalf'";
		   
		     if(helpForm.getLocationId()==null)
		     {
		    	 helpForm.setLocationId(loc);
		     }
		    if(helpForm.getLocationId().equalsIgnoreCase(""))
		    {
		    	helpForm.setLocationId(loc);
		    }
		    if(!helpForm.getLocationId().equalsIgnoreCase("All"))
		    {
		    	 getTotalRecords=getTotalRecords+" and Location ='"+helpForm.getLocationId()+"' ";
		    }
			
		    if(!helpForm.getFromDate().equalsIgnoreCase(""))
		    {
		    	 getTotalRecords=getTotalRecords+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
		    }
		    
		    
		    if(!helpForm.getMaincategory().equalsIgnoreCase(""))
		    {
		    	 getTotalRecords=getTotalRecords+" and it.Req_type ='"+helpForm.getMaincategory()+"' ";
		    }
		   
		    if(!view.equalsIgnoreCase(""))
		    {
		    if(!helpForm.getMainrequestStatus().equalsIgnoreCase(""))
		    {
		    	 getTotalRecords=getTotalRecords+" and it.Req_Status ='"+helpForm.getMainrequestStatus()+"' ";
		    }
		    }
		    if(view.equalsIgnoreCase(""))
		    {
		    	getTotalRecords=getTotalRecords+" and it.Req_Status !='Completed' ";
		    }
		    
		    if(!helpForm.getDepartment().equalsIgnoreCase(""))
		    {
		    	 getTotalRecords=getTotalRecords+" and dep.DPTID ='"+helpForm.getDepartment()+"' ";
		    }
		    
			    }
	    ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
		  while(rsTotalRecods.next())
		  {
			  totalRecords=rsTotalRecods.getInt(1);
		  }
	   
		  if(totalRecords>=10)
		  {
			  helpForm.setTotalRecords(totalRecords);
		  startRecord=1;
		  endRecord=10;
		  helpForm.setStartRecord(1);
		  helpForm.setEndRecord(10);
		 if(!view.equalsIgnoreCase(""))
		  request.setAttribute("displayRecordNo", "displayRecordNo");
		  
		  request.setAttribute("nextButton", "nextButton");
		  }else
		  {
			  startRecord=1;
			  endRecord=totalRecords;
			  helpForm.setTotalRecords(totalRecords);
			  helpForm.setStartRecord(1);
			  helpForm.setEndRecord(totalRecords); 
		  }
		  String getAllReq="";
		  if(empType.equals("Technician")){
		 getAllReq="Select * from (Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
		+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,it.SLA_Status,all_r.approved_date from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep "
		+ "where it.Req_No=all_r.Req_Id and (all_r.Technician_Id='"+empNo+"' )  and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+empNo+"' and emp.PERNR=all_r.Requester_Id and emp.DPTID=dep.DPTID and it.Req_Status !='ON_Behalf'";
	     if(helpForm.getLocationId()==null)
	     {
	    	 helpForm.setLocationId(loc);
	     }
	    if(helpForm.getLocationId().equalsIgnoreCase(""))
	    {
	    	helpForm.setLocationId(loc);
	    }
	    if(!helpForm.getLocationId().equalsIgnoreCase("All"))
	    {
	    	getAllReq=getAllReq+" and Location ='"+helpForm.getLocationId()+"' ";
	    }
		
	    if(!helpForm.getFromDate().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
	    }
	    if(!helpForm.getMaincategory().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_type ='"+helpForm.getMaincategory()+"' ";
	    }
	    if(!view.equalsIgnoreCase(""))
	    {
	    if(!helpForm.getMainrequestStatus().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_Status ='"+helpForm.getMainrequestStatus()+"' ";
	    }
	    }
	    if(view.equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_Status !='Completed' ";
	    }
	    
	    if(!helpForm.getDepartment().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and dep.DPTID ='"+helpForm.getDepartment()+"' ";
	    }
	    
	    if(!view.equalsIgnoreCase(""))
	    getAllReq=getAllReq+" group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,it.SLA_Status,all_r.approved_date) as  sub Where  sub.RowNum between 1 and 10)t order by t.approved_date desc ";
	    else
	    	 getAllReq=getAllReq+" group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,it.SLA_Status,all_r.approved_date) as  sub Where  sub.RowNum between 1 and 50)t order by t.approved_date desc ";
	    	
		  }else{
	  getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,it.Req_Type,it.Sub_Category,"
				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp ,DEPARTMENT as dep "
				+ "where  (it.Emp_No='"+empNo+"') and emp.PERNR=it.Emp_No and emp.DPTID=dep.DPTID and it.Req_Status !='ON_Behalf'";
	  
	     if(helpForm.getLocationId()==null)
	     {
	    	 helpForm.setLocationId(loc);
	     }
	    if(helpForm.getLocationId().equalsIgnoreCase(""))
	    {
	    	helpForm.setLocationId(loc);
	    }
	    if(!helpForm.getLocationId().equalsIgnoreCase("All"))
	    {
	    	getAllReq=getAllReq+" and Location ='"+helpForm.getLocationId()+"' ";
	    }
		
	    if(!helpForm.getFromDate().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
	    }
		 
	    
	    if(!helpForm.getMaincategory().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_type ='"+helpForm.getMaincategory()+"' ";
	    }
	   
	    if(!helpForm.getMainrequestStatus().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_Status ='"+helpForm.getMainrequestStatus()+"' ";
	    }
	    
	    
	    if(!helpForm.getDepartment().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and dep.DPTID ='"+helpForm.getDepartment()+"' ";
	    }
	    
	    getAllReq=getAllReq+" group by it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status) as  sub Where  sub.RowNum between 1 and 10 ";
		  }
		ResultSet rs=ad.selectQuery(getAllReq);
		String reqnos="";
		while(rs.next()){
		
			
			
		IssuesForm form=new IssuesForm();
		if(empType.equals("Technician"))
			form.setRequestNo(rs.getInt("Req_Id"));
		else
			form.setRequestNo(rs.getInt("Req_No"));
		form.setLocation(rs.getString("LOCID"));
		form.setEmployeename(rs.getString("EMP_FULLNAME"));
		form.setCategory(rs.getString("Req_Type"));
		form.setSubcategory(rs.getString("Sub_Category"));
		form.setSubject(rs.getString("Subject"));
		String ReqDate=rs.getString("Req_date1");
		/*String a[]=ReqDate.split(" "); 
		ReqDate=a[0];
		String b[]=ReqDate.split("-");
		ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
		form.setReqDate(ReqDate);
		form.setRequestStatus(rs.getString("Req_Status"));
		form.setReqPriority(rs.getString("Req_Priority"));
		if(empType.equals("Technician"))
			form.setReadStatus(rs.getInt("readStatus"));
		else
			form.setReadStatus(rs.getInt("read_Status"));
		
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
		
		if(assignTo.contains(","))
		{
		String asgn[]=assignTo.split(",");
		int asgnlength=asgn.length;
		 pendingtechn=asgn[asgnlength-1];
		
		}
		else
		{
			pendingtechn=assignTo;
		}
			
		
		if(!(techNames.equalsIgnoreCase(""))){
			techNames=techNames.substring(0, (techNames.length()-1));	
		}
		
		form.setAssignTo(techNames);

		
		form.setSlaStatus(rs.getString("SLA_Status"));
		
		
		if(empType.equals("Technician"))
		{
		if(!(reqnos.contains(Integer.toString(rs.getInt("Req_Id")))))
		{
			allReq.add(form);
		}
		else
		{
			helpForm.setTotalRecords(helpForm.getTotalRecords()-1);
		
		}
		
		reqnos=	reqnos+","+rs.getInt("Req_Id");
		}
		else
		{
			if(!(reqnos.contains(Integer.toString(rs.getInt("Req_No")))))
			{
				allReq.add(form);
			}
			else
			{
				helpForm.setTotalRecords(helpForm.getTotalRecords()-1);
			
			}
		}
		
	
			
		
		
		
		}
		ad.connClose();
		if(allReq.size()==0){
			request.setAttribute("noRecords", "noRecords");
			helpForm.setMessage("No Records Found");
			
		}
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		 /*getAllReq="select it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,emp.EMP_FULLNAME,"
 		+ "emp.LOCID,it.Req_Priority from IT_ISSUES as it,emp_official_info as emp where it.Emp_No='"+empNo+"'  "
		+ "and emp.PERNR=it.Emp_No ";
			 rs=ad.selectQuery(getAllReq); 
			while(rs.next()){
			IssuesForm form=new IssuesForm();
			form.setRequestNo(rs.getInt("Req_No"));
			form.setLocation(rs.getString("LOCID"));
			form.setEmployeename(rs.getString("EMP_FULLNAME"));
			form.setCategory(rs.getString("Req_Type"));
			form.setSubcategory(rs.getString("Sub_Category"));
			form.setReqDate(EMicroUtils.display(rs.getDate("Req_date")));
			form.setRequestStatus(rs.getString("Req_Status"));
			form.setReqPriority(rs.getString("Req_Priority"));
			allReq.add(form);
			}*/
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return allReq;
	}
	
	public List getAllRequest1(String empNo,ActionForm form1,HttpServletRequest request,String loc,String view){
		IssuesForm helpForm = (IssuesForm) form1;
		
		int  totalRecords=0;
	    int  startRecord=0;
	    int  endRecord=0;
	    List allReq=new LinkedList();
	    
	    
	   
	    try{
	    String empType="";	
	    //check login User Type	
	    String checkEmpType="select COUNT(*) from IT_Approvers where Approver_ID='"+empNo+"' and Category!='IT SERVICES'";
	    ResultSet rsEmpType=ad.selectQuery(checkEmpType);
	    while(rsEmpType.next()){
	    	if(rsEmpType.getInt(1)>0){
	    		helpForm.setChooseType("All Requests");
	    		empType="Technician";
	    	}else{
	    		helpForm.setChooseType("My All Requests");
	    		empType="Requester";
	    	}
	    	helpForm.setEmpType(empType);
	    }
	    
	    String fromDate=helpForm.getFromDate();
	    String toDate=helpForm.getToDate();
	    String fromspac[]=fromDate.split(" ");
	    String from[]=fromspac[0].split("-");
	    String tospac[]=toDate.split(" ");
	    String to[]=tospac[0].split("-");
	    		
	helpForm.setFromDate(from[2]+"/"+from[1]+"/"+from[0]);
	helpForm.setToDate(to[2]+"/"+to[1]+"/"+to[0]);
	 
	    String getTotalRecords="";
	    
		String pendingtechn="";
		
		
		String Appstatus="select * from IT_ISSUES where Emp_No='"+empNo+"' and  Req_Status ='ON_Behalf'";
		ResultSet rsu=ad.selectQuery(Appstatus);
		String restatus="";
		while(rsu.next()){
			
			restatus=rsu.getString("Approved_Status");
		
		}
		if(restatus.equalsIgnoreCase("0")){
			empType="Requester";
			
		}
		
	    
	    /*if(empType.equals("Technician")){
	     getTotalRecords="select COUNT(distinct(it.Req_No)) from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep "
	    + "where it.Req_No=all_r.Req_Id and (all_r.Technician_Id='"+empNo+"' )  and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+empNo+"' and emp.PERNR=all_r.Requester_Id and emp.DPTID=dep.DPTID";
	     if(helpForm.getLocationId()==null)
	     {
	    	 helpForm.setLocationId(loc);
	     }
	    if(helpForm.getLocationId().equalsIgnoreCase(""))
	    {
	    	helpForm.setLocationId(loc);
	    }
	    if(!helpForm.getLocationId().equalsIgnoreCase("All"))
	    {
	    	 getTotalRecords=getTotalRecords+" and Location ='"+helpForm.getLocationId()+"' ";
	    }
		
	    if(!helpForm.getFromDate().equalsIgnoreCase(""))
	    {
	    	 getTotalRecords=getTotalRecords+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
	    }
	    
	    if(!helpForm.getMaincategory().equalsIgnoreCase(""))
	    {
	    	 getTotalRecords=getTotalRecords+" and it.Req_type ='"+helpForm.getMaincategory()+"' ";
	    }
	   
	    if(!helpForm.getMainrequestStatus().equalsIgnoreCase(""))
	    {
	    	 getTotalRecords=getTotalRecords+" and it.Req_Status ='"+helpForm.getMainrequestStatus()+"' ";
	    }
	    
	  
	    
	    if(!helpForm.getDepartment().equalsIgnoreCase(""))
	    {
	    	 getTotalRecords=getTotalRecords+" and dep.DPTID ='"+helpForm.getDepartment()+"' ";
	    }
	     
		 }else{
		*/   getTotalRecords="select COUNT(distinct(it.Req_No)) from IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep where  ( it.Emp_No='"+empNo+"') and emp.PERNR=it.Emp_No and emp.DPTID=dep.DPTID";
		   
		     if(helpForm.getLocationId()==null)
		     {
		    	 helpForm.setLocationId(loc);
		     }
		    if(helpForm.getLocationId().equalsIgnoreCase(""))
		    {
		    	helpForm.setLocationId(loc);
		    }
		    if(!helpForm.getLocationId().equalsIgnoreCase("All"))
		    {
		    	 getTotalRecords=getTotalRecords+" and Location ='"+helpForm.getLocationId()+"' ";
		    }
			
		    if(!helpForm.getFromDate().equalsIgnoreCase(""))
		    {
		    	 getTotalRecords=getTotalRecords+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
		    }
		    
		    
		    if(!helpForm.getMaincategory().equalsIgnoreCase(""))
		    {
		    	 getTotalRecords=getTotalRecords+" and it.Req_type ='"+helpForm.getMaincategory()+"' ";
		    }
		   
		    if(!view.equalsIgnoreCase(""))
		    {
		    if(!helpForm.getMainrequestStatus().equalsIgnoreCase(""))
		    {
		    	 getTotalRecords=getTotalRecords+" and it.Req_Status ='"+helpForm.getMainrequestStatus()+"' ";
		    }
		    }
		    if(view.equalsIgnoreCase(""))
		    {
		    	getTotalRecords=getTotalRecords+" and it.Req_Status !='Completed' ";
		    }
		    
		    if(!helpForm.getDepartment().equalsIgnoreCase(""))
		    {
		    	 getTotalRecords=getTotalRecords+" and dep.DPTID ='"+helpForm.getDepartment()+"' ";
		    }
		    
			    
	    ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
		  while(rsTotalRecods.next())
		  {
			  totalRecords=rsTotalRecods.getInt(1);
		  }
	   
		  if(totalRecords>=10)
		  {
			  helpForm.setTotalRecords(totalRecords);
		  startRecord=1;
		  endRecord=10;
		  helpForm.setStartRecord(1);
		  helpForm.setEndRecord(10);
		 if(!view.equalsIgnoreCase(""))
		  request.setAttribute("displayRecordNo", "displayRecordNo");
		  
		  request.setAttribute("nextButton", "nextButton");
		  }else
		  {
			  startRecord=1;
			  endRecord=totalRecords;
			  helpForm.setTotalRecords(totalRecords);
			  helpForm.setStartRecord(1);
			  helpForm.setEndRecord(totalRecords); 
		  }
		  String getAllReq="";
		 /* if(empType.equals("Technician")){
		 getAllReq="Select * from (Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_r.Req_Id desc) AS RowNum, all_r.Req_Id,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,"
		+ "all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,it.SLA_Status,all_r.approved_date from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp,DEPARTMENT as dep "
		+ "where  it.Req_No=all_r.Req_Id and (all_r.Technician_Id='"+empNo+"' )  and CASE WHEN charindex(',', AssignTo)> 0 THEN reverse(left(reverse(AssignTo),charindex(',', reverse(AssignTo)) -1))  ELSE AssignTo   END='"+empNo+"' and emp.PERNR=all_r.Requester_Id and emp.DPTID=dep.DPTID";
	     if(helpForm.getLocationId()==null)
	     {
	    	 helpForm.setLocationId(loc);
	     }
	    if(helpForm.getLocationId().equalsIgnoreCase(""))
	    {
	    	helpForm.setLocationId(loc);
	    }
	    if(!helpForm.getLocationId().equalsIgnoreCase("All"))
	    {
	    	getAllReq=getAllReq+" and Location ='"+helpForm.getLocationId()+"' ";
	    }
		
	    if(!helpForm.getFromDate().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
	    }
	    if(!helpForm.getMaincategory().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_type ='"+helpForm.getMaincategory()+"' ";
	    }
	    if(!view.equalsIgnoreCase(""))
	    {
	    if(!helpForm.getMainrequestStatus().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_Status ='"+helpForm.getMainrequestStatus()+"' ";
	    }
	    }
	    if(view.equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_Status !='Completed' ";
	    }
	    
	    if(!helpForm.getDepartment().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and dep.DPTID ='"+helpForm.getDepartment()+"' ";
	    }
	    
	    if(!view.equalsIgnoreCase(""))
	    getAllReq=getAllReq+" group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,it.SLA_Status,all_r.approved_date) as  sub Where  sub.RowNum between 1 and 10)t order by t.approved_date desc ";
	    else
	    	 getAllReq=getAllReq+" group by all_r.Req_Id,it.Req_date,it.Req_Status,all_r.Req_Type,all_r.Sub_Category,all_r.Requester_Id,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,all_r.readStatus,it.AssignTo,it.Subject,it.SLA_Status,all_r.approved_date) as  sub Where  sub.RowNum between 1 and 50)t order by t.approved_date desc ";
	    	
		  }else{
	 */ getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,CONVERT(varchar(10),it.Req_date,103)+' '+ CONVERT(varchar(5),it.Req_date,108) as Req_date1,it.Req_Status,it.Req_Type,it.Sub_Category,"
				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_ISSUES as it,emp_official_info as emp ,DEPARTMENT as dep "
				+ "where  (it.Emp_No='"+empNo+"') and emp.PERNR=it.Emp_No and emp.DPTID=dep.DPTID";
	  
	     if(helpForm.getLocationId()==null)
	     {
	    	 helpForm.setLocationId(loc);
	     }
	    if(helpForm.getLocationId().equalsIgnoreCase(""))
	    {
	    	helpForm.setLocationId(loc);
	    }
	    if(!helpForm.getLocationId().equalsIgnoreCase("All"))
	    {
	    	getAllReq=getAllReq+" and Location ='"+helpForm.getLocationId()+"' ";
	    }
		
	    if(!helpForm.getFromDate().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and (it.Req_date between '"+fromDate+"' and '"+ toDate+"') ";
	    }
		 
	    
	    if(!helpForm.getMaincategory().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_type ='"+helpForm.getMaincategory()+"' ";
	    }
	   
	    if(!helpForm.getMainrequestStatus().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and it.Req_Status ='"+helpForm.getMainrequestStatus()+"' ";
	    }
	    
	    
	    if(!helpForm.getDepartment().equalsIgnoreCase(""))
	    {
	    	getAllReq=getAllReq+" and dep.DPTID ='"+helpForm.getDepartment()+"' ";
	    }
	    
	    getAllReq=getAllReq+" group by it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status) as  sub Where  sub.RowNum between 1 and 10 ";
		  
		ResultSet rs=ad.selectQuery(getAllReq);
		String reqnos="";
		while(rs.next()){
			
		IssuesForm form=new IssuesForm();
		if(empType.equals("Technician"))
			form.setRequestNo(rs.getInt("Req_Id"));
		else
		form.setRequestNo(rs.getInt("Req_No"));
		form.setLocation(rs.getString("LOCID"));
		form.setEmployeename(rs.getString("EMP_FULLNAME"));
		form.setCategory(rs.getString("Req_Type"));
		form.setSubcategory(rs.getString("Sub_Category"));
		form.setSubject(rs.getString("Subject"));
		String ReqDate=rs.getString("Req_date1");
		/*String a[]=ReqDate.split(" "); 
		ReqDate=a[0];
		String b[]=ReqDate.split("-");
		ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];*/
		form.setReqDate(ReqDate);
		form.setRequestStatus(rs.getString("Req_Status"));
		form.setReqPriority(rs.getString("Req_Priority"));
		if(empType.equals("Technician"))
			form.setReadStatus(rs.getInt("readStatus"));
		else
			form.setReadStatus(rs.getInt("read_Status"));
		
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
		
		if(assignTo.contains(","))
		{
		String asgn[]=assignTo.split(",");
		int asgnlength=asgn.length;
		 pendingtechn=asgn[asgnlength-1];
		
		}
		else
		{
			pendingtechn=assignTo;
		}
			
		
		if(!(techNames.equalsIgnoreCase(""))){
			techNames=techNames.substring(0, (techNames.length()-1));	
		}
		
		form.setAssignTo(techNames);
		form.setSlaStatus(rs.getString("SLA_Status"));
		
		if(empType.equals("Technician"))
		{
		if(!(reqnos.contains(Integer.toString(rs.getInt("Req_Id")))))
		{
			allReq.add(form);
		}
		else
		{
			helpForm.setTotalRecords(helpForm.getTotalRecords()-1);
		
		}
		
		reqnos=	reqnos+","+rs.getInt("Req_Id");
		}
		else
		{
			if(!(reqnos.contains(Integer.toString(rs.getInt("Req_No")))))
			{
				allReq.add(form);
			}
			else
			{
				helpForm.setTotalRecords(helpForm.getTotalRecords()-1);
			
			}
		
		}
		}
		ad.connClose();
		if(allReq.size()==0){
			request.setAttribute("noRecords", "noRecords");
			helpForm.setMessage("No Records Found");
			
		}
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		 /*getAllReq="select it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,emp.EMP_FULLNAME,"
 		+ "emp.LOCID,it.Req_Priority from IT_ISSUES as it,emp_official_info as emp where it.Emp_No='"+empNo+"'  "
		+ "and emp.PERNR=it.Emp_No ";
			 rs=ad.selectQuery(getAllReq); 
			while(rs.next()){
			IssuesForm form=new IssuesForm();
			form.setRequestNo(rs.getInt("Req_No"));
			form.setLocation(rs.getString("LOCID"));
			form.setEmployeename(rs.getString("EMP_FULLNAME"));
			form.setCategory(rs.getString("Req_Type"));
			form.setSubcategory(rs.getString("Sub_Category"));
			form.setReqDate(EMicroUtils.display(rs.getDate("Req_date")));
			form.setRequestStatus(rs.getString("Req_Status"));
			form.setReqPriority(rs.getString("Req_Priority"));
			allReq.add(form);
			}*/
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return allReq;
	}
	
	public List getAllSapRequest(String empNo,ActionForm form1,HttpServletRequest request){
		IssuesForm helpForm = (IssuesForm) form1;
		
		int  totalRecords=0;
	    int  startRecord=0;
	    int  endRecord=0;
	    List allReq=new LinkedList();
	   
	    try{
	    String empType="";	
	    //check login User Type	
	    /*String checkEmpType="select COUNT(*) from IT_Approvers where Approver_ID='"+empNo+"' and Category!='IT SERVICES'";
	    ResultSet rsEmpType=ad.selectQuery(checkEmpType);*/
	    /*while(rsEmpType.next()){
	    	if(rsEmpType.getInt(1)>0){
	    		helpForm.setChooseType("All Requests");
	    		empType="Technician";
	    	}else{
	    		helpForm.setChooseType("My All Requests");
	    		empType="Requester";
	    	}
	    	helpForm.setEmpType(empType);
	    }*/
	    empType="Requester";
	    String getTotalRecords="";
	    
	    if(empType.equals("Technician")){
	     getTotalRecords="select COUNT(*) from IT_All_ISSUES as all_r,IT_ISSUES as it,emp_official_info as emp "
	    + "where it.Req_No=all_r.Req_Id and (all_r.Technician_Id='"+empNo+"' ) and emp.PERNR=all_r.Requester_Id";
		 }else{
		   getTotalRecords="select COUNT(*) from IT_ISSUES as it,emp_official_info as emp where  ( it.Emp_No='"+empNo+"') and emp.PERNR=it.Emp_No";
			    }
	    ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
		  while(rsTotalRecods.next())
		  {
			  totalRecords=rsTotalRecods.getInt(1);
		  }
	   
		  if(totalRecords>=10)
		  {
			  helpForm.setTotalRecords(totalRecords);
		  startRecord=1;
		  endRecord=10;
		  helpForm.setStartRecord(1);
		  helpForm.setEndRecord(10);
		  request.setAttribute("displayRecordNo", "displayRecordNo");
		  request.setAttribute("nextButton", "nextButton");
		  }else
		  {
			  startRecord=1;
			  endRecord=totalRecords;
			  helpForm.setTotalRecords(totalRecords);
			  helpForm.setStartRecord(1);
			  helpForm.setEndRecord(totalRecords); 
		  }
		  String getAllReq="";
		  
	  getAllReq="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY it.Req_No desc) AS RowNum, it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,"
				+ "emp.EMP_FULLNAME,emp.LOCID,it.Req_Priority,it.read_Status,it.AssignTo,it.Subject,it.SLA_Status from IT_SAP_ISSUES as it,emp_official_info as emp "
				+ "where  (it.Emp_No='"+empNo+"') and emp.PERNR=it.Emp_No) as  sub Where  sub.RowNum between 1 and 10";
		  
		ResultSet rs=ad.selectQuery(getAllReq);
		while(rs.next()){
		IssuesForm form=new IssuesForm();
		if(empType.equals("Technician"))
			form.setRequestNo(rs.getInt("Req_Id"));
		else
			form.setRequestNo(rs.getInt("Req_No"));
		form.setLocation(rs.getString("LOCID"));
		form.setEmployeename(rs.getString("EMP_FULLNAME"));
		form.setCategory(rs.getString("Req_Type"));
		form.setSubcategory(rs.getString("Sub_Category"));
		form.setSubject(rs.getString("Subject"));
		String ReqDate=rs.getString("Req_date");
		String a[]=ReqDate.split(" "); 
		ReqDate=a[0];
		String b[]=ReqDate.split("-");
		ReqDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
		form.setReqDate(ReqDate);
		form.setRequestStatus(rs.getString("Req_Status"));
		form.setReqPriority(rs.getString("Req_Priority"));
		if(empType.equals("Technician"))
			form.setReadStatus(rs.getInt("readStatus"));
		else
			form.setReadStatus(rs.getInt("read_Status"));
		
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
		form.setAssignTo(techNames);
		
		form.setSlaStatus(rs.getString("SLA_Status"));
		allReq.add(form);
		}
		ad.connClose();
		if(allReq.size()==0){
			request.setAttribute("noRecords", "noRecords");
			helpForm.setMessage("No Records Found");
	
		}
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		 /*getAllReq="select it.Req_No,it.Req_date,it.Req_Status,it.Req_Type,it.Sub_Category,it.Emp_No,emp.EMP_FULLNAME,"
 		+ "emp.LOCID,it.Req_Priority from IT_ISSUES as it,emp_official_info as emp where it.Emp_No='"+empNo+"'  "
		+ "and emp.PERNR=it.Emp_No ";
			 rs=ad.selectQuery(getAllReq); 
			while(rs.next()){
			IssuesForm form=new IssuesForm();
			form.setRequestNo(rs.getInt("Req_No"));
			form.setLocation(rs.getString("LOCID"));
			form.setEmployeename(rs.getString("EMP_FULLNAME"));
			form.setCategory(rs.getString("Req_Type"));
			form.setSubcategory(rs.getString("Sub_Category"));
			form.setReqDate(EMicroUtils.display(rs.getDate("Req_date")));
			form.setRequestStatus(rs.getString("Req_Status"));
			form.setReqPriority(rs.getString("Req_Priority"));
			allReq.add(form);
			}*/
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return allReq;
	}

}
