package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.microlabs.ess.form.IncomeTaxForm;
import com.microlabs.ess.form.LTAForm;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.PreviousIncomeForm;
import com.microlabs.hr.form.IncomeTaxReqForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class LTAAction extends DispatchAction{
	EssDao ad=EssDao.dBConnection();
	
	public ActionForward deleteLTADetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm lta=(LTAForm)form; 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int requestNo=lta.getRequestNo();
		lta.setEmployeeNo(user.getEmployeeNo());
		lta.setEmployeeName(user.getFullName());
		try{
			String checkedValues=request.getParameter("cValues");
			checkedValues = checkedValues.substring(0, checkedValues.length());
			String[] documentCheck=checkedValues.split(",");
			int j=0;
			for(int i=0;i<documentCheck.length;i++)
			{
			String updateRentDt="delete from  LTA_Details where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"' and Id='"+documentCheck[i]+"' ";
		   j=ad.SqlExecuteUpdate(updateRentDt);
			}
		    if(j>0){
		    	
		    	lta.setMessage("LTA details are deleted successfully");
		    	
		    	
		    }else{
		 
		    	lta.setMessage("Error...Data not deleted.");
		    }
		    List<LTAForm> listOfLta=new LinkedList<LTAForm>();
	         String getLTASavedDetails="select * from LTA_Details where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
	         ResultSet rs=ad.selectQuery(getLTASavedDetails);
	 	    while(rs.next()){
	 	    	LTAForm form2=new LTAForm();
	 	    	form2.setId(rs.getInt("ID"));
	 	    	form2.setRequestNo(rs.getInt("requestNo"));
	 	    	form2.setTravelStartDate(EMicroUtils.display(rs.getDate("Travel_Start_Date")));
	 	    	form2.setTravelEndDate(EMicroUtils.display(rs.getDate("Travel_End_Date")));
	 	    	form2.setLeaveType(rs.getString("Leave_Type"));
	 	    	form2.setLtaAmtApplFor(rs.getString("LTA_amount_approved_for"));
	 	    	form2.setLtaAmtAprvdFor(rs.getString("LTA_amount_approved"));
	 	    	listOfLta.add(form2);
	 	    }
	 	    
	 	    if(listOfLta.size()>0){
	 	     request.setAttribute("listOfLta", listOfLta);
	 	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("newLTA");
		}
	
	
	
	public ActionForward CancelReq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm lta=(LTAForm)form; 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int requestNo=lta.getRequestNo();
		lta.setEmployeeNo(user.getEmployeeNo());
		lta.setEmployeeName(user.getFullName());
		try{
			String deleteReq="delete from LTA_Income where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
			int i=ad.SqlExecuteUpdate(deleteReq);
			if(i>0){
				
				lta.setMessage("Request has been deleted successfully.");
		    	newLta(mapping, form, request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("newLTA");
	}
	public ActionForward submitForApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm lta=(LTAForm)form; 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		lta.setEmployeeNo(user.getEmployeeNo());
		lta.setEmployeeName(user.getFullName());
		int requestNo=lta.getRequestNo();
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		 String dateNow = ft.format(dNow);
			
		 String approverID="";
		synchronized (this) {
			try {
			
		
				
				 int newReqestnumber=0;
				 int reqestNo=lta.getRequestNo();
				 boolean checkReqno=false;
				 String checkReqNo="select count(*) from LTA_Income where requestNo='"+reqestNo+"'";
		        ResultSet rsCheck=ad.selectQuery(checkReqNo);
		        try{
		        while(rsCheck.next()){
		       	 int num=rsCheck.getInt(1);
		       	 if(num>0){
		       		 checkReqno=true;
		       	 }
		        }
		        
		    ////check 1 file atleast uploaded or not
		        
		        String fi="select count(*) from LTA_Income_documents where request_no='"+reqestNo+"'";
		        ResultSet rsfi=ad.selectQuery(fi);
		     
		        while(rsfi.next()){
		       	 int num=rsfi.getInt(1);
		       	 if(num>0){
		       		 
		       	 }
		       	 else
		       	 {
		       		lta.setMessage("Upload atleast 1 pdf document");
		       		newLta(mapping, form, request, response);
		       		return mapping.findForward("newLTA");
		       	 }
		        }
		        
		        String getNewNo="select max(requestNo) from LTA_Income";
		       	 ResultSet rsNewNo=ad.selectQuery(getNewNo);
		       	 while(rsNewNo.next()){
		       		 newReqestnumber=rsNewNo.getInt(1);
		       		 newReqestnumber=newReqestnumber+1;
		       		lta.setRequestNo(newReqestnumber);
		       	 }
		        if(checkReqno==true){
		       	 
		       	 //update reqno in uploaded doc
		       	 
		       	 String updateDOC="update LTA_Income_documents set request_no='"+newReqestnumber+"' where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqestNo+"'";
		       	 ad.SqlExecuteUpdate(updateDOC);
		        }
		        }catch (Exception e) {
					e.printStackTrace();
				}
				
				
		/*String getApprover="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where "
		+ "PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='1' and	Req_Type='LTA'";	
		ResultSet rsApproverID=ad.selectQuery(getApprover);*/
		        String getApprover="select Approver_ID from IncomeTax_Approvers where Priority='1' and	Req_Type='LTA'";	
        		ResultSet rsApproverID=ad.selectQuery(getApprover);
			while(rsApproverID.next()){
				approverID=rsApproverID.getString("Approver_ID");
			}		
		String pendingApprovers="";
		/* String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
		 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) "
		 + "AND  Req_Type='LTA'  and mat.Approver_Id=emp.PERNR  order by Priority";
		 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);*/
		 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where Req_Type='LTA'  and mat.Approver_Id=emp.PERNR  order by Priority";
		 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
			while(rsPendignApp.next())
			{  
				pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
			}
			if(!(pendingApprovers.equalsIgnoreCase(""))){
				pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
			}
		
			
		//add details to LTA_Income
		String saveLTAIncome="insert into LTA_Income(requestNo,employeeNo,year,Approval_status,submit_date,Last_approver,Pending_approver) values('"+newReqestnumber+"',"
		+ "'"+user.getEmployeeNo()+"','"+lta.getFiscalYear()+"','In Process',getdate(),'No','"+pendingApprovers+"')";
		int i=ad.SqlExecuteUpdate(saveLTAIncome);
			

		if(i>0){
	    	//Add request to Approvals
			
			
			
			
			/*String trst[]=request.getParameterValues("tstd");
			String tren[]=request.getParameterValues("tend");*/
			String ltype[]=request.getParameterValues("ltype");
			String lst[]=request.getParameterValues("std");
			String stdu[]=request.getParameterValues("stddu");
			String len[]=request.getParameterValues("end");
			String endu[]=request.getParameterValues("enddu");
			String no[]=request.getParameterValues("tota");
			String amount[]=request.getParameterValues("amount");
	
			
			if(lst!=null)
	    	{
			for(int j=0;j<lst.length;j++)
			{   
				
				/*String trastartDt=trst[j];
				   String a[]=trastartDt.split("/");
				   trastartDt=a[2]+"-"+a[1]+"-"+a[0];
				   
				   String traendDate=tren[j];
				   String b[]=traendDate.split("/");
				   traendDate=b[2]+"-"+b[1]+"-"+b[0];
				   */
				   
				   String leavstDate=lst[j];
				   String c[]=leavstDate.split("/");
				   leavstDate=c[2]+"-"+c[1]+"-"+c[0];
				   
				   
				   String leavenDate=len[j];
				   String d[]=leavenDate.split("/");
				   leavenDate=d[2]+"-"+d[1]+"-"+d[0];
				
				String saveLTA="insert into LTA_Details(requestNo,employeeNo,year,Leave_Details,Leave_Type,start_Date,"
				    + "start_Dur,end_Date,end_Dur,noOf_days,LTA_amount_approved_for) "
				    + "values('"+newReqestnumber+"','"+user.getEmployeeNo()+"','"+lta.getFiscalYear()+"','','"+ltype[j]+"','"+leavstDate+"',"
				    		+ " '"+stdu[j]+"','"+leavenDate+"','"+endu[j]+"','"+no[j]+"','"+amount[j]+"')";	
				    int k=ad.SqlExecuteUpdate(saveLTA);}
	    	}
			}
	    	
	    	String sendtoAppr="insert into IncomeTax_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,"
			+"Approved_Persons,Requester_Id,Comments,type) values('"+newReqestnumber+"','Income Tax','"+user.getEmployeeNo()+"',"
			+ "getdate(),'Pending','','"+approverID+"','','"+user.getEmployeeNo()+"','','LTA')";
			int i1=ad.SqlExecuteUpdate(sendtoAppr);
			if(i1>0){
			
				lta.setMessage("Request has been submitted for approval");
			}
	
		
			String filepath1="";
		 	String getFile = "select * from  LTA_Income_documents where request_no='"+newReqestnumber+"' and user_id='"+user.getEmployeeNo()+"'";
	     	 ResultSet rs22  =ad.selectQuery(getFile);
	     	 try {
				while(rs22.next())
				 {
					String filename2 = rs22.getString("file_name");
					int reqnumber = rs22.getInt("request_no");
					String filename1 =reqnumber+"_"+filename2;
					String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/LTA/UploadFiles");
					 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 	 Properties props = new Properties();
				 	try {
						props.load(in);
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				// 	FormFile documentFile=Paths.get(filename1);
				 
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles");
				 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles/"+reqnumber;
				 	 filepath1=uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles/"+filename2;
					
				 	String content = new String(Files.readAllBytes(Paths.get(filepath1)));
				 	byte[] contentInBytes1 = content.getBytes();
					byte[] contentInBytes = Files.readAllBytes(Paths.get(filepath1));
					File destinationDir1 = new File(filePath);
					destinationDir1.getName();
					
			/*	destinationDir1.renameTo(new File(uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles/"+reqestNo+"/"+filename1));*/
			/*	File destinationDir1 = new File(filePath);*/
				
			
				
				
					if(!destinationDir1.exists())
					{
						destinationDir1.mkdirs();
					}
					if (!filename1.equals("")) {
						File fileToCreate1 = new File(filePath, filename1);
						if (!fileToCreate1.exists()) {
							FileOutputStream fileOutStream1 = new FileOutputStream(
									fileToCreate1);
							fileOutStream1.write(contentInBytes);
							Files.write(Paths.get(filepath1), content.getBytes(), StandardOpenOption.CREATE);
						
							/*fileOutStream1.write(contentInBytes);*/
							fileOutStream1.flush();
							fileOutStream1.close();
						}
					}
		
		
		
				 
				 } }catch (Exception e) {
			e.printStackTrace();
		}
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		newLta(mapping, form, request, response);
		return mapping.findForward("newLTA");
	}
	
	
	
	public ActionForward submitMedicalForApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm lta=(LTAForm)form; 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		lta.setEmployeeNo(user.getEmployeeNo());
		lta.setEmployeeName(user.getFullName());
		int requestNo=lta.getRequestNo();
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		 String dateNow = ft.format(dNow);
			
		 String approverID="";
		synchronized (this) {
			try {
			
		
				
				 int newReqestnumber=0;
				 int reqestNo=lta.getRequestNo();
				 boolean checkReqno=false;
				 String checkReqNo="select count(*) from Medical_Income where requestNo='"+reqestNo+"'";
		        ResultSet rsCheck=ad.selectQuery(checkReqNo);
		        try{
		        while(rsCheck.next()){
		       	 int num=rsCheck.getInt(1);
		       	 if(num>0){
		       		 checkReqno=true;
		       	 }
		        }
		        
   /*////check 1 file atleast uploaded or not
		        
		        String fi="select count(*) from Medical_Bill_documents where request_no='"+reqestNo+"'";
		        ResultSet rsfi=ad.selectQuery(fi);
		     
		        while(rsfi.next()){
		       	 int num=rsfi.getInt(1);
		       	 if(num>0){
		       		 
		       	 }
		       	 else
		       	 {
		       		lta.setMessage("Upload atleast 1 pdf document");
		       	newMedical(mapping, form, request, response);
		       		return mapping.findForward("newMedical");
		       	 }
		        }*/
		   	 String getNewNo="select max(requestNo) from Medical_Income";
	       	 ResultSet rsNewNo=ad.selectQuery(getNewNo);
	       	 while(rsNewNo.next()){
	       		 newReqestnumber=rsNewNo.getInt(1);
	       		 newReqestnumber=newReqestnumber+1;
	       		lta.setRequestNo(newReqestnumber);
	       	 }
		        if(checkReqno==true){
		       
		       	 //update reqno in uploaded doc
		       	 
		       	 String updateDOC="update Medical_Bill_documents set request_no='"+newReqestnumber+"' where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqestNo+"'";
		       	 ad.SqlExecuteUpdate(updateDOC);
		        }
		        }catch (Exception e) {
					e.printStackTrace();
				}
				
				
		        String getApprover="select Approver_ID from IncomeTax_Approvers where Priority='1' and	Req_Type='Medical'";	
        		ResultSet rsApproverID=ad.selectQuery(getApprover);
			while(rsApproverID.next()){
				approverID=rsApproverID.getString("Approver_ID");
			}		
		String pendingApprovers="";
		 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where Req_Type='Medical'  and mat.Approver_Id=emp.PERNR  order by Priority";
		 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
			while(rsPendignApp.next())
			{  
				pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
			}
			if(!(pendingApprovers.equalsIgnoreCase(""))){
				pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
			}
		
			
		//add details to LTA_Income
		String saveLTAIncome="insert into Medical_Income(requestNo,employeeNo,year,Approval_status,submit_date,Last_approver,Pending_approver) values('"+newReqestnumber+"',"
		+ "'"+user.getEmployeeNo()+"','"+lta.getFiscalYear()+"','In Process',getdate(),'No','"+pendingApprovers+"')";
		int i=ad.SqlExecuteUpdate(saveLTAIncome);
			

		if(i>0){
	    	//Add request to Approvals
			
			
			
			
			String bill[]=request.getParameterValues("billno");
			String desc[]=request.getParameterValues("desc");
			String date[]=request.getParameterValues("startDate");
			String amount[]=request.getParameterValues("totalAmount");
			String rem[]=request.getParameterValues("remarks");
		
			
			if(bill!=null)
	    	{
			for(int j=0;j<bill.length;j++)
			{   
				
				String startDt=date[j];
				   String a[]=startDt.split("/");
				   startDt=a[2]+"-"+a[1]+"-"+a[0];
				   
				  
				   
				   
				
				String saveLTA="insert into Medical_Bill_Details(requestNo,employeeNo,year,bill_no,bill_desc,bill_date,bill_amount,remarks,tot_amount) "
				    + "values('"+newReqestnumber+"','"+user.getEmployeeNo()+"','"+lta.getFiscalYear()+"','"+bill[j]+"','"+desc[j]+"','"+startDt+"','"+amount[j]+"', '"+rem[j]+"','"+lta.getTotalIncome()+"')";	
				    int k=ad.SqlExecuteUpdate(saveLTA);}
	    	}
			}
	    	
	    	String sendtoAppr="insert into IncomeTax_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,"
			+"Approved_Persons,Requester_Id,Comments,type) values('"+newReqestnumber+"','Income Tax','"+user.getEmployeeNo()+"',"
			+ "getdate(),'Pending','','"+approverID+"','','"+user.getEmployeeNo()+"','','Medical')";
			int i1=ad.SqlExecuteUpdate(sendtoAppr);
			if(i1>0){
			
				lta.setMessage("Request has been submitted for approval");
			}
	
		
			String filepath1="";
		 	String getFile = "select * from  Medical_Bill_documents where request_no='"+newReqestnumber+"' and user_id='"+user.getEmployeeNo()+"'";
	     	 ResultSet rs22  =ad.selectQuery(getFile);
	     	 try {
				while(rs22.next())
				 {
					String filename2 = rs22.getString("file_name");
					int reqnumber = rs22.getInt("request_no");
					String filename1 =reqnumber+"_"+filename2;
					String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Medical/UploadFiles");
					 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 	 Properties props = new Properties();
				 	try {
						props.load(in);
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				// 	FormFile documentFile=Paths.get(filename1);
				 
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles");
				 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles/"+reqnumber;
				 	 filepath1=uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles/"+filename2;
					
				 	String content = new String(Files.readAllBytes(Paths.get(filepath1)));
				 	byte[] contentInBytes1 = content.getBytes();
					byte[] contentInBytes = Files.readAllBytes(Paths.get(filepath1));
					File destinationDir1 = new File(filePath);
					destinationDir1.getName();
					
			/*	destinationDir1.renameTo(new File(uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles/"+reqestNo+"/"+filename1));*/
			/*	File destinationDir1 = new File(filePath);*/
				
			
				
				
					if(!destinationDir1.exists())
					{
						destinationDir1.mkdirs();
					}
					if (!filename1.equals("")) {
						File fileToCreate1 = new File(filePath, filename1);
						if (!fileToCreate1.exists()) {
							FileOutputStream fileOutStream1 = new FileOutputStream(
									fileToCreate1);
							fileOutStream1.write(contentInBytes);
							Files.write(Paths.get(filepath1), content.getBytes(), StandardOpenOption.CREATE);
						
							/*fileOutStream1.write(contentInBytes);*/
							fileOutStream1.flush();
							fileOutStream1.close();
						}
					}
		
		
		
				 
				 } }catch (Exception e) {
			e.printStackTrace();
		}
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		newMedical(mapping, form, request, response);
		return mapping.findForward("newMedical");
	}
	
	
	public ActionForward addDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm lta=(LTAForm)form; 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
		String travelStDt=lta.getTravelStartDate();
		String c[]=travelStDt.split("/");
		travelStDt=c[2]+"-"+c[1]+"-"+c[0];
		String travelEndDt=lta.getTravelEndDate();
		String d[]=travelEndDt.split("/");
		travelEndDt=d[2]+"-"+d[1]+"-"+d[0];
		String leaveType=lta.getLeaveType();
		String leaveStartDt=lta.getStartDate();
		String a[]=leaveStartDt.split("/");
		leaveStartDt=a[2]+"-"+a[1]+"-"+a[0];
		String leaveEndDt=lta.getEndDate();
		String b[]=leaveEndDt.split("/");
		leaveEndDt=b[2]+"-"+b[1]+"-"+b[0];
	    double noOfDays=lta.getTotalLeaveDays();
	    int requestNo=lta.getRequestNo();
	    lta.setRequestNo(requestNo);
	    //check leave details
	    boolean leaveStatus=false;
	    
	    String[] monthName = {"","JAN", "FEB",
				  "MAR", "APR", "MAY", "JUN", "JUL",
				  "AUG", "SEP", "OCT", "NOV",
				  "DEC"
				  };
			String startMonth=c[1];
			String EndMonth=d[1];	
			int staMont=Integer.parseInt(startMonth);
			int endMnth=Integer.parseInt(EndMonth);
		  startMonth = monthName[staMont];
		  System.out.println("startMonth name: " + startMonth);
		  EndMonth = monthName[endMnth];
		  System.out.println("EndMonth name: " + EndMonth);
		  
			String userLocation="";
			String userLoc="select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"'";
			ResultSet rsloc=ad.selectQuery(userLoc);
			while(rsloc.next()){
				userLocation=rsloc.getString("LOCATION_CODE");
			}
			String tableName=getTableName(userLocation);
	    
		  String checkLeve="select count(*) from leave_details where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and start_date='"+leaveStartDt+"' "
	   + "and end_date='"+leaveEndDt+"' and Approvel_Status='Approved' and no_of_days='"+noOfDays+"'  ";
	    ResultSet rsCheckLeave=ad.selectQuery(checkLeve);
	    while(rsCheckLeave.next()){
	    	if(rsCheckLeave.getInt(1)==1){
	    		leaveStatus=true;
	    	}
	    }
	    if(leaveStatus==true){
	    //check travel start date and leave start date duration
	    	boolean checkLeavestDays=false;
	   //traval start month and leave start month same 	
	   if(c[1].equals(a[1])){//d b
		   int count=0;
		   int preWkDay=0;
		   
		   String getCount=" select DAY from "+tableName+" where DAY < "+a[0]+" and  "+startMonth+"='W' and "+startMonth+"!='' order by DAY desc";
		   
		  ResultSet rsCount=ad.selectQuery(getCount);
		  if(rsCount.next()){
			  preWkDay=rsCount.getInt("DAY");
			  }
		  if(preWkDay<Integer.parseInt(c[0]) && Integer.parseInt(c[0])<Integer.parseInt(a[0]))
			  checkLeavestDays=true;
		  
		  if(preWkDay==Integer.parseInt(c[0]))
				  checkLeavestDays=true;
		  /*if(Integer.parseInt(c[0])<=preWkDay){
			  checkLeavestDays=true;
		  }*/
		 
		  if(c[0].equals(a[0]))
			  checkLeavestDays=true;
	   }else{
	   int count=0; 
	   String getCount="select count("+startMonth+") from "+tableName+" where (day between "+c[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !=''";
	   ResultSet rsCount=ad.selectQuery(getCount);
		  while(rsCount.next()){
			  count=rsCount.getInt(1);
		  }
	   String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+b[0]+") and "+EndMonth+" ='W' and "+EndMonth+" !=''";  
	   rsCount=ad.selectQuery(getCount);
		  while(rsCount.next()){
			  count=count+rsCount.getInt(1);
		  }
		  if(count==2||count==1){
			  checkLeavestDays=true;
		  }
	   }
	    boolean checkLeaveEndDays=false;
	    if(d[1].equals(b[1])){//d b
			   int count=0;
			  int nextWkDay=0;
			   
			   String getCount=" select DAY from "+tableName+" where DAY > "+b[0]+" and  "+startMonth+"='W' and "+startMonth+"!='' order by DAY  ";
				  ResultSet rsCount=ad.selectQuery(getCount);
				  int c1=0;
				  if(rsCount.next()){
					  nextWkDay=rsCount.getInt(1);
				  }
				  
				  if(nextWkDay<Integer.parseInt(d[0]) && Integer.parseInt(d[0])<Integer.parseInt(b[0]))
					  checkLeaveEndDays=true;
				  
				  if(nextWkDay==Integer.parseInt(d[0]))
					  checkLeaveEndDays=true;
				  
				  if(b[0].equals(d[0]))
					  checkLeaveEndDays=true;
	    }else{
	 	   int count=0; 
		   /*String getCount="select count("+startMonth+") from "+tableName+" where (day between "+b[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !=''";
		   ResultSet rsCount=ad.selectQuery(getCount);
			  while(rsCount.next()){
				  count=rsCount.getInt(1);
			  }*/
		   String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+d[0]+") and "+EndMonth+" ='W' and "+EndMonth+" !=''";  
		   ResultSet  rsCount=ad.selectQuery(getCountQuery1);
			  while(rsCount.next()){
				  count=count+rsCount.getInt(1);
			  }
			  if(count==1){
				  checkLeaveEndDays=true;
			  }
		   }
	    
	    	System.out.println("checkLeavestDays="+checkLeavestDays);
	    	System.out.println("checkLeaveEndDays="+checkLeaveEndDays);
	    
	    if(checkLeavestDays==true && checkLeaveEndDays==true){
	    	// add leave details in LTA table 
	    	String dateNow=getCurrentDateWithFormat();
	    String saveLTA="insert into LTA_Details(requestNo,employeeNo,year,Travel_Start_Date,Travel_End_Date,Leave_Details,Leave_Type,start_Date,"
	    + "start_Dur,end_Date,end_Dur,noOf_days,LTA_amount_approved_for,LTA_amount_approved) "
	    + "values('"+requestNo+"','"+user.getEmployeeNo()+"','"+lta.getFiscalYear()+"','"+travelStDt+"','"+travelEndDt+"','"+lta.getLeaveDetails()+"','"+lta.getLeaveType()+"','"+leaveStartDt+"','"+lta.getStartDurationType()+"',"
	    		+ " '"+leaveEndDt+"','"+lta.getEndDurationType()+"','"+noOfDays+"','"+lta.getLtaAmtApplFor()+"','"+lta.getLtaAmtAprvdFor()+"')";	
	    int i=ad.SqlExecuteUpdate(saveLTA);
	    if(i>0){
	    	
	    	lta.setMessage("LTA details are saved successfully.");
	    	setLeaveDetails(mapping, form, request, response);
	    	resetValues(mapping, form, request, response);
	    }
	    }else{
	
	    	lta.setMessage("Please Check Travel Start Date Or End Date.");
	    	setLeaveDetails(mapping, form, request, response);
	    	
	    }
	    }else{
	    	//There is no leave record

	    	lta.setMessage("No Leave Details For The Selection Dates.Please Check");
	    	setLeaveDetails(mapping, form, request, response);
	    	resetValues(mapping, form, request, response);
	    }
	    List<LTAForm> listOfLta=new LinkedList<LTAForm>();
        String getLTASavedDetails="select * from LTA_Details where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
        ResultSet rs=ad.selectQuery(getLTASavedDetails);
	    while(rs.next()){
	    	LTAForm form2=new LTAForm();
	    	form2.setId(rs.getInt("ID"));
	    	form2.setRequestNo(rs.getInt("requestNo"));
	    	form2.setTravelStartDate(EMicroUtils.display(rs.getDate("Travel_Start_Date")));
	    	form2.setTravelEndDate(EMicroUtils.display(rs.getDate("Travel_End_Date")));
	    	form2.setLeaveType(rs.getString("Leave_Type"));
	    	form2.setLtaAmtApplFor(rs.getString("LTA_amount_approved_for"));
	    	form2.setLtaAmtAprvdFor(rs.getString("LTA_amount_approved"));
	    	listOfLta.add(form2);
	    }
	     request.setAttribute("listOfLta", listOfLta);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("newLTA");
	}
	
	
	public ActionForward showMedicalUploadFields(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm incomeTax=(LTAForm)form;
		
		System.out.println("showMedicalUploadFields");
		return mapping.findForward("showMedicalUploadFields");
	}
	
	public ActionForward showUploadFields(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm incomeTax=(LTAForm)form;
		
		System.out.println("showUploadFields");
		return mapping.findForward("showUploadFields");
	}
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm incomeTax=(LTAForm)form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
	
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=incomeTax.getRequestNo();
		incomeTax.setRequestNo(reqNo);
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
			System.out.println("rejectLength Is ********************"+documentLength);
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						String fileName="";
						String getFilename="select * from LTA_Income_documents where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from LTA_Income_documents  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					incomeTax.setMessage("Document Details Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/Income Tax/LTA/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from LTA_Income_documents where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqNo+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			
			try{
				IncomeTaxForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs.getString("request_no")));
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setInvRemarks(rs.getString("description"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);
			}catch(Exception e){
				e.printStackTrace();
			}
		return mapping.findForward("showUploadFields");
	}
	public ActionForward deleteMedicalDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm incomeTax=(LTAForm)form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
	
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=incomeTax.getRequestNo();
		incomeTax.setRequestNo(reqNo);
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
			System.out.println("rejectLength Is ********************"+documentLength);
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
						String fileName="";
						String getFilename="select * from Medical_Bill_documents where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from Medical_Bill_documents  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					incomeTax.setMessage("Document Details Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/Income Tax/Medical/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from Medical_Bill_documents where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqNo+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			
			try{
				IncomeTaxForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs.getString("request_no")));
					leaveForm1.setInvRemarks(rs.getString("description"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);
			}catch(Exception e){
				e.printStackTrace();
			}
		return mapping.findForward("showMedicalUploadFields");
	}
	
	
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm incomeTax=(LTAForm)form;
		
		FormFile documentFile=incomeTax.getDocumentFile();
		String documentName=documentFile.getFileName();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		 String filePath="";
		
			
	 	
		 try {
			byte[] size=documentFile.getFileData();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	 	String fileName1 = documentFile.getFileName();
	 	String fileName  =user.getEmployeeNo()+"LTA"+documentFile.getFileName();
		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();

		if (ext.equalsIgnoreCase("pdf")) {
			
			if((filesize>1048576))
			{
				incomeTax.setMessage("PDF file size should be less than 1MB");
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
			}
			else
			{
			
			int requestNo=0;
			String getReqNo="select max(requestNo) from LTA_Income";
			ResultSet rsReqNo=ad.selectQuery(getReqNo);
			try {
				while(rsReqNo.next()){
					requestNo=rsReqNo.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			requestNo=requestNo+1;
			incomeTax.setRequestNo(requestNo);
	     //jsp/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles
 	     //String filepath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/ESS/On Duty/UploadFiles/"+documentFile.getFileName());
 	  filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/LTA/UploadFiles");
 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
 	 Properties props = new Properties();
 	try {
		props.load(in);
		in.close();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles");
 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles";
		//masterForm.setUploadFilePath(filePath);
		File destinationDir = new File(filePath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
		}
		if (!fileName.equals("")) {
			File fileToCreate = new File(filePath, fileName);
			if (!fileToCreate.exists()) {
				FileOutputStream fileOutStream;
				try {
					fileOutStream = new FileOutputStream(
							fileToCreate);
				
				fileOutStream.write(documentFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//upload files in another path
		
		try{
			String filePath1 = "E:/EMicro Files/ESS/Income Tax/LTA/UploadFiles";
			
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
 	     
 	     
 	     
		String fildesc=request.getParameter("filedesc");
		
			
			
			String sql9="select count(*) from LTA_Income_documents  where  file_name='"+fileName+"' and request_no='"+requestNo+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			try {
				while (rs15.next())
				{
					fileCount=Integer.parseInt(rs15.getString(1));
				}
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(fileCount>0)
			{
				incomeTax.setMessage("Document already uploaded..please choose another file");
			}
			else
			{
				String sql="insert into LTA_Income_documents(request_no,user_id,file_name,description)" +
				"values('"+incomeTax.getRequestNo()+"','"+user.getEmployeeNo()+"','"+fileName+"','"+fildesc+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					//incomeTax.setMessage("Documents Uploaded Successfully");
					}
			}
		}	
		
		}
			else{
				incomeTax.setMessage("Upload only pdf documents");
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
				
			}
		String sql1="select * from LTA_Income_documents where request_no='"+incomeTax.getRequestNo()+"' and user_id='"+user.getEmployeeNo()+"' ";
		ResultSet rs=ad.selectQuery(sql1);
		IncomeTaxForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			String fileids="";
			try {
				while(rs.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs.getString("request_no")));
					fileids=fileids+rs.getString("id")+",";
					leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
					leaveForm1.setInvRemarks(rs.getString("description"));
					a1.add(leaveForm1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("documentDetails", a1);
			
			
			/*if(fileids!=null)
			{
			String h="insert into Upload_file_ids(ids,type)values('"+fileids.substring(0, fileids.length()-1)+"','Apply Investment')";
			int k=ad.SqlExecuteUpdatePrmaryKeys(h);
			}*/
			//incomeTax.setFileFullPath(fileids.substring(0, fileids.length()-1));
		 }
		 return mapping.findForward("showUploadFields");
		}
	
	public ActionForward uploadMedicalDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm incomeTax=(LTAForm)form;
		
		FormFile documentFile=incomeTax.getDocumentFile();
		String documentName=documentFile.getFileName();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		 String filePath="";
		
			
	 	
		 try {
			byte[] size=documentFile.getFileData();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	 	String fileName1 = documentFile.getFileName();
	 	String fileName  =user.getEmployeeNo()+"MDB"+documentFile.getFileName();
		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();

		if (ext.equalsIgnoreCase("pdf")) {
			
			if((filesize>1048576))
			{
				incomeTax.setMessage("PDF file size should be less than 1MB");
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
			}
			else
			{
			
			
			int requestNo=0;
			String getReqNo="select max(requestNo) from Medical_Income";
			ResultSet rsReqNo=ad.selectQuery(getReqNo);
			try {
				while(rsReqNo.next()){
					requestNo=rsReqNo.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			requestNo=requestNo+1;
			incomeTax.setRequestNo(requestNo);
	     //jsp/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles
 	     //String filepath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/ESS/On Duty/UploadFiles/"+documentFile.getFileName());
 	  filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Medical/UploadFiles");
 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
 	 Properties props = new Properties();
 	try {
		props.load(in);
		in.close();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles");
 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles";
		//masterForm.setUploadFilePath(filePath);
		File destinationDir = new File(filePath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
		}
		if (!fileName.equals("")) {
			File fileToCreate = new File(filePath, fileName);
			if (!fileToCreate.exists()) {
				FileOutputStream fileOutStream;
				try {
					fileOutStream = new FileOutputStream(
							fileToCreate);
				
				fileOutStream.write(documentFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//upload files in another path
		
		try{
			String filePath1 = "E:/EMicro Files/ESS/Income Tax/Medical/UploadFiles";
			
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
 	     
 	     
 	     
		String fildesc=request.getParameter("filedesc");
		
			
			
			String sql9="select count(*) from Medical_Bill_documents  where  file_name='"+fileName+"' and request_no='"+requestNo+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			try {
				while (rs15.next())
				{
					fileCount=Integer.parseInt(rs15.getString(1));
				}
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(fileCount>0)
			{
				incomeTax.setMessage("Document already uploaded..please choose another file");
			}
			else
			{
				String sql="insert into Medical_Bill_documents(request_no,user_id,file_name,description)" +
				"values('"+incomeTax.getRequestNo()+"','"+user.getEmployeeNo()+"','"+fileName+"','"+fildesc+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					//incomeTax.setMessage("Documents Uploaded Successfully");
					}
			}
		
		}
		
		}
			else{
				incomeTax.setMessage("Upload only pdf documents");
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
				
			}
		String sql1="select * from Medical_Bill_documents where request_no='"+incomeTax.getRequestNo()+"' and user_id='"+user.getEmployeeNo()+"' ";
		ResultSet rs=ad.selectQuery(sql1);
		IncomeTaxForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			String fileids="";
			try {
				while(rs.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getString("id"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs.getString("request_no")));
					fileids=fileids+rs.getString("id")+",";
					leaveForm1.setFileFullPath(filePath+"/"+rs.getString("request_no")+"/"+rs.getString("file_name"));
					leaveForm1.setInvRemarks(rs.getString("description"));
					a1.add(leaveForm1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("documentDetails", a1);
			
			
			/*if(fileids!=null)
			{
			String h="insert into Upload_file_ids(ids,type)values('"+fileids.substring(0, fileids.length()-1)+"','Apply Investment')";
			int k=ad.SqlExecuteUpdatePrmaryKeys(h);
			}*/
			//incomeTax.setFileFullPath(fileids.substring(0, fileids.length()-1));
		 }
		 return mapping.findForward("showMedicalUploadFields");
		}
	
	
	public String getCurrentDateWithFormat(){
		Calendar currentDate = Calendar.getInstance();
		 int day = currentDate.get(Calendar.DATE);
	     int month = currentDate.get(Calendar.MONTH) + 1;
	     int year = currentDate.get(Calendar.YEAR);
	     String dateNow=year+"-"+month+"-"+day;
	     return dateNow;
	}
	public Date getDate(String reqDate){
		Date date=null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    		 date = formatter.parse(reqDate);
    	
     
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
		return date;
	}
	
	
	public ActionForward displayPlLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LTAForm leaveForm=(LTAForm)form; 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		leaveForm.setEmployeeNo(user.getEmployeeNo());
		leaveForm.setEmployeeName(user.getFullName());
		leaveForm.setFiscalYear("");
		leaveForm.setFiscalYearDesc("");
		int fiscal = Integer.parseInt(request.getParameter("Fiscal"));
		

        ///getapproved PL leaves
        Calendar cal=Calendar.getInstance();
	     int year=cal.get(Calendar.YEAR);
        ArrayList li=new ArrayList();
        String g=" select CONVERT(varchar(10),start_date,103) as start_date1,CONVERT(varchar(10),end_date,103) end_date1,* from leave_details,lv_type_m where lv_type_m.lv_typeid=leave_details.leave_type and  user_id='"+user.getEmployeeNo()+"' and (start_date between '"+(fiscal-1)+"-01-04' and '"+fiscal+"-03-31') and leave_type=3 and Approvel_Status='Approved' and no_of_days>='3.0'";
        ResultSet fp=ad.selectQuery(g);
        try {
			while(fp.next())
			{
				LTAForm l=new LTAForm();
				l.setStartDate(fp.getString("start_date1"));
				l.setEndDate(fp.getString("end_date1"));
				String std="";
				String end="";
				if(fp.getString("start_duration").equalsIgnoreCase("FD"))
				{
					std="Full Duration";
				}
				if(fp.getString("start_duration").equalsIgnoreCase("FH"))
				{
					std="First Half";
				}
				if(fp.getString("start_duration").equalsIgnoreCase("SH"))
				{
					std="Second Half";
				}
				if(fp.getString("end_duration").equalsIgnoreCase("FD"))
				{
					end="Full Duration";
				}
				if(fp.getString("end_duration").equalsIgnoreCase("FH"))
				{
					end="First Half";
				}
				if(fp.getString("end_duration").equalsIgnoreCase("SH"))
				{
					end="Second Half";
				}
				
				l.setStartDurationType(std);
				
				l.setEndDurationType(end);
				l.setNoOfDays(fp.getString("no_of_days"));
				l.setLeaveType(fp.getString("lv_type"));
				
				li.add(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        request.setAttribute("li", li);
leaveForm.setTotalLeaveDays(0);



		
		return mapping.findForward("newLTA");
		
		
	}
	public ActionForward newLta(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm leaveForm=(LTAForm)form; 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		leaveForm.setEmployeeNo(user.getEmployeeNo());
		leaveForm.setEmployeeName(user.getFullName());
		leaveForm.setFiscalYear("");
		leaveForm.setFiscalYearDesc("");
		String fi = request.getParameter("Fiscal");
		int fiscal=0;
		int fiscal2=0;
		if(fi==null)
		{
			 fiscal=0;
		}
		else
		{
		 fiscal = Integer.parseInt(request.getParameter("Fiscal"));
		  fiscal2  =fiscal+1;
		 leaveForm.setFiscalYear(fi);
		String fisy =  "April-"+fiscal+" to March "+fiscal2;
		leaveForm.setFiscalYearDesc(fisy);
		}
		try{
	            int requestNo=0;
				String getReqNo="select max(requestNo) from LTA_Income";
				ResultSet rsReqNo=ad.selectQuery(getReqNo);
				while(rsReqNo.next()){
					requestNo=rsReqNo.getInt(1);
					requestNo=requestNo+1;
				}
				leaveForm.setRequestNo(requestNo);
				
				
				
			
	/*		String getleavadvdays="select * from Leave_Structure ";
			ResultSet rsleavadvdays=ad.selectQuery(getleavadvdays);
			while(rsleavadvdays.next())
			{ 
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("CL")){
				leaveForm.setCasleavadv(rsleavadvdays.getFloat("LADVDAY"));
				leaveForm.setClMaxDays(rsleavadvdays.getFloat("LMAXALW"));
				leaveForm.setClmindur(rsleavadvdays.getInt("LMINDUR"));
				
				}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("SL")){
					leaveForm.setSlMaxDays(rsleavadvdays.getFloat("LMAXALW"));
					leaveForm.setSlmindur(rsleavadvdays.getInt("LMINDUR"));
					}
				
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("EL")){
					leaveForm.setPreleavadv(rsleavadvdays.getFloat("LADVDAY"));
					leaveForm.setPreleavmin(rsleavadvdays.getFloat("LMINALW"));
					leaveForm.setPlmindur(rsleavadvdays.getInt("LMINDUR"));
					}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("LP")){
					leaveForm.setLossmindur(rsleavadvdays.getInt("LMINDUR"));
				}
			}
			double sicklvcloseBal=0;
			double casuallvcloseBal=0;
			String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' ";
			ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
			while(rsSickBal.next()){
			if(rsSickBal.getInt("lv_typeid")==2){
				 sicklvcloseBal=rsSickBal.getDouble("lv_clbal");
			}
			if(rsSickBal.getInt("lv_typeid")==1){
				 casuallvcloseBal=rsSickBal.getDouble("lv_clbal");
			}
			}
			leaveForm.setSicklvcloseBal(sicklvcloseBal);
			leaveForm.setCasuallvcloseBal(casuallvcloseBal);
			
			LinkedList leaveTypeID=new LinkedList();
			LinkedList leaveType=new LinkedList();
		
		String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"' and t.lv_calyear='2014' and t.lv_typeid=m.lv_typeid";
		ResultSet rsBalance=ad.selectQuery(getleaveBalence);
		ArrayList balList=new ArrayList();
		while(rsBalance.next())
		{
		   leaveForm.setYear(rsBalance.getInt("lv_calyear"));
			LeaveForm form2=new LeaveForm();
			form2.setLeaveType(rsBalance.getString("lv_type"));
			form2.setOpeningBalence(rsBalance.getFloat("lv_opbal"));
			form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
			form2.setNoOfDays(rsBalance.getString("lv_availed"));
			form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
		form2.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
			
			balList.add(form2);
		}
		request.setAttribute("LeaveBalenceList", balList);
		String getGender="select SEX from emp_official_info where PERNR='"+user.getUserName()+"'";
		ResultSet rsGender=ad.selectQuery(getGender);
		String gender="";
		while(rsGender.next()){
			gender=rsGender.getString("SEX");
		}
		if(gender.equalsIgnoreCase("M"))
		{
			String getLeaveType="select * from lv_type_m where lv_typeid!=5";
			ResultSet rs=ad.selectQuery(getLeaveType);
			while(rs.next())
			{
				leaveTypeID.add(rs.getString("lv_typeid"));
				leaveType.add(rs.getString("lv_type"));
			}
			leaveForm.setLeaveTypeID(leaveTypeID);
			leaveForm.setLeaveTypeName(leaveType);
		}else{
			String getLeaveType="select * from lv_type_m ";
			ResultSet rs=ad.selectQuery(getLeaveType);
			while(rs.next())
			{
				leaveTypeID.add(rs.getString("lv_typeid"));
				leaveType.add(rs.getString("lv_type"));
			}
			leaveForm.setLeaveTypeID(leaveTypeID);
			leaveForm.setLeaveTypeName(leaveType);
		}
		*/
		}catch (Exception e) {
			e.printStackTrace();
		}
		leaveForm.setNoOfDays("");
		
		
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select year from fiscal_year order by id desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("year"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				leaveForm.setYearList(yearList);
		
				String re[]=empDet(user.getEmployeeNo());
				leaveForm.setEmployeeNo(user.getEmployeeNo());
				leaveForm.setEmployeeName(re[0]);
				leaveForm.setLocation(re[1]);
                leaveForm.setDepartment(re[2]);
                leaveForm.setDesignation(re[3]); 
                leaveForm.setDoj(re[4]);
                leaveForm.setStaffCategory(re[8]);
                
               if(fiscal!=0)
               {
               ///getapproved PL leaves
                Calendar cal=Calendar.getInstance();
			     int year=cal.get(Calendar.YEAR);
                ArrayList li=new ArrayList();
                String g=" select CONVERT(varchar(10),start_date,103) as start_date1,CONVERT(varchar(10),end_date,103) end_date1,* from leave_details,lv_type_m where lv_type_m.lv_typeid=leave_details.leave_type and  user_id='"+user.getEmployeeNo()+"' and (start_date between '"+(fiscal)+"-01-04' and '"+fiscal2+"-03-31') and leave_type=3 and Approvel_Status='Approved' and no_of_days>='3.0'";
                ResultSet fp=ad.selectQuery(g);
                try {
					while(fp.next())
					{
						LTAForm l=new LTAForm();
						l.setStartDate(fp.getString("start_date1"));
						l.setEndDate(fp.getString("end_date1"));
						String std="";
						String end="";
						if(fp.getString("start_duration").equalsIgnoreCase("FD"))
						{
							std="Full Duration";
						}
						if(fp.getString("start_duration").equalsIgnoreCase("FH"))
						{
							std="First Half";
						}
						if(fp.getString("start_duration").equalsIgnoreCase("SH"))
						{
							std="Second Half";
						}
						if(fp.getString("end_duration").equalsIgnoreCase("FD"))
						{
							end="Full Duration";
						}
						if(fp.getString("end_duration").equalsIgnoreCase("FH"))
						{
							end="First Half";
						}
						if(fp.getString("end_duration").equalsIgnoreCase("SH"))
						{
							end="Second Half";
						}
						
						l.setStartDurationType(std);
						
						l.setEndDurationType(end);
						l.setNoOfDays(fp.getString("no_of_days"));
						l.setLeaveType(fp.getString("lv_type"));
						
						li.add(l);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
                
                request.setAttribute("li", li);
               }
		leaveForm.setTotalLeaveDays(0);
		
		
		//set approval status
		LinkedList listApprers=new LinkedList();
	String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
	+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' "
			+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='LTA'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
	ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
	try {
		while(rsStatus.next()){
			IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
			reqForm.setPriority(rsStatus.getInt("Priority"));
			String apprvers=rsStatus.getString("Approver_Id");
			reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
			reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
			reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
			String apprDate="";
			String approveStatus="In Process";

			listApprers.add(reqForm);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	request.setAttribute("approverDetails", listApprers);
		
		return mapping.findForward("newLTA");
	}
	
	
	public ActionForward newMedical(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm leaveForm=(LTAForm)form; 
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		leaveForm.setEmployeeNo(user.getEmployeeNo());
		leaveForm.setEmployeeName(user.getFullName());
		leaveForm.setFiscalYear("");
		leaveForm.setFiscalYearDesc("");
try{
	            int requestNo=0;
				String getReqNo="select max(requestNo) from Medical_Bill_Details";
				ResultSet rsReqNo=ad.selectQuery(getReqNo);
				while(rsReqNo.next()){
					requestNo=rsReqNo.getInt(1);
					requestNo=requestNo+1;
				}
				leaveForm.setRequestNo(requestNo);
				
				
				
			
	
		
		
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select year from fiscal_year order by id desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("year"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				leaveForm.setYearList(yearList);
		
				String re[]=empDet(user.getEmployeeNo());
				leaveForm.setEmployeeNo(user.getEmployeeNo());
				leaveForm.setEmployeeName(re[0]);
				leaveForm.setLocation(re[1]);
                leaveForm.setDepartment(re[2]);
                leaveForm.setDesignation(re[3]); 
                leaveForm.setDoj(re[4]);
                leaveForm.setStaffCategory(re[8]);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}  
                
//set approval status
LinkedList listApprers=new LinkedList();
String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' "
	+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='Medical'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
try {
while(rsStatus.next()){
	IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
	reqForm.setPriority(rsStatus.getInt("Priority"));
	String apprvers=rsStatus.getString("Approver_Id");
	reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
	reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
	reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
	String apprDate="";
	String approveStatus="In Process";

	listApprers.add(reqForm);
}
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
request.setAttribute("approverDetails", listApprers);
             
		return mapping.findForward("newMedical");
	}
	
	
	private String[] empDet( String empno) {
		//Created by Pradeep .Date:15-11-2016
		
 		EssDao ad = new EssDao();
		String emp=" select emp.eMP_FULLNAME,emp.lOCID,dep.DPTSTXT,desg.DSGSTXT,convert(nvarchar(10),emp.doj,103) as doj,datediff(year,dob,getdate()) as age,emp.Email_id, case when emp.sex='M' then 'Male' else 'Female' end as gender,catltext from Emp_official_info emp "
				+ " , Department dep ,Designation desg,category c  where emp.dPTID = dep.DPTID and emp.pERNR='"+empno+"'  and emp.staffcat=c.staffcat "
						+ " and desg.DSGID= emp.dSGID  ";
		ResultSet ae=ad.selectQuery(emp);

		String[] a = new String[10];
	
		try {
			while(ae.next())
			{
			
			

			    a[0]=ae.getString("eMP_FULLNAME");
			    a[1]=ae.getString("lOCID");
			    a[2]=ae.getString("DPTSTXT");
			    a[3]=ae.getString("DSGSTXT");
			    a[4]=ae.getString("doj");
			    a[5]=ae.getString("age");
			    a[6]=ae.getString("email_id");
			    a[7]=ae.getString("gender");
			    a[8]=ae.getString("catltext");
      


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return a;
		}
	
	public ActionForward setLeaveDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm leaveForm=(LTAForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		leaveForm.setEmployeeNo(user.getEmployeeNo());
		leaveForm.setEmployeeName(user.getFullName());
try{
	           
			
			String getleavadvdays="select * from Leave_Structure ";
			ResultSet rsleavadvdays=ad.selectQuery(getleavadvdays);
			while(rsleavadvdays.next())
			{ 
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("CL")){
				leaveForm.setCasleavadv(rsleavadvdays.getFloat("LADVDAY"));
				leaveForm.setClMaxDays(rsleavadvdays.getFloat("LMAXALW"));
				leaveForm.setClmindur(rsleavadvdays.getInt("LMINDUR"));
				
				}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("SL")){
					leaveForm.setSlMaxDays(rsleavadvdays.getFloat("LMAXALW"));
					leaveForm.setSlmindur(rsleavadvdays.getInt("LMINDUR"));
					}
				
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("EL")){
					leaveForm.setPreleavadv(rsleavadvdays.getFloat("LADVDAY"));
					leaveForm.setPreleavmin(rsleavadvdays.getFloat("LMINALW"));
					leaveForm.setPlmindur(rsleavadvdays.getInt("LMINDUR"));
					}
				if(rsleavadvdays.getString("LEAVTYP").equalsIgnoreCase("LP")){
					leaveForm.setLossmindur(rsleavadvdays.getInt("LMINDUR"));
				}
			}
			double sicklvcloseBal=0;
			double casuallvcloseBal=0;
			String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' ";
			ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
			while(rsSickBal.next()){
			if(rsSickBal.getInt("lv_typeid")==2){
				 sicklvcloseBal=rsSickBal.getDouble("lv_clbal");
			}
			if(rsSickBal.getInt("lv_typeid")==1){
				 casuallvcloseBal=rsSickBal.getDouble("lv_clbal");
			}
			}
			leaveForm.setSicklvcloseBal(sicklvcloseBal);
			leaveForm.setCasuallvcloseBal(casuallvcloseBal);
			LinkedList leaveTypeID=new LinkedList();
			LinkedList leaveType=new LinkedList();
		
		String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+user.getEmployeeNo()+"'  and t.lv_typeid=m.lv_typeid";
		ResultSet rsBalance=ad.selectQuery(getleaveBalence);
		ArrayList balList=new ArrayList();
		while(rsBalance.next())
		{
		   leaveForm.setYear(rsBalance.getInt("lv_calyear"));
			LeaveForm form2=new LeaveForm();
			form2.setLeaveType(rsBalance.getString("lv_type"));
			form2.setOpeningBalence(rsBalance.getFloat("lv_opbal"));
			form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
			form2.setNoOfDays(rsBalance.getString("lv_availed"));
			form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
		form2.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
		/*	String lvqry = "select no_of_days from leave_details where Approvel_Status='Pending'";
			ResultSet lvqryRS=ad.selectQuery(lvqry);
			while(lvqryRS.next()){
				String nD = lvqryRS.getString("no_of_days");
				if(!nD.equalsIgnoreCase("null")){
				noDays = noDays+lvqryRS.getFloat("no_of_days");
				}
			}*/
		/*	form2.setNoOfDays(""+noDays);
			if(rsBalance.getFloat("lv_clbal") != 0.0){
				String getLeaveType="select * from lv_type_m where lv_typeid="+rsBalance.getString("lv_typeid");
				ResultSet rs=ad.selectQuery(getLeaveType);
				while(rs.next())
				{
					leaveTypeID.add(rs.getString("lv_typeid"));
					leaveType.add(rs.getString("lv_type"));
				}
				leaveForm.setLeaveTypeID(leaveTypeID);
				leaveForm.setLeaveTypeName(leaveType);
			}*/
			
			
			
			balList.add(form2);
		}
		request.setAttribute("LeaveBalenceList", balList);
		String getGender="select SEX from emp_official_info where PERNR='"+user.getUserName()+"'";
		ResultSet rsGender=ad.selectQuery(getGender);
		String gender="";
		while(rsGender.next()){
			gender=rsGender.getString("SEX");
		}
		if(gender.equalsIgnoreCase("M"))
		{
			String getLeaveType="select * from lv_type_m where lv_typeid!=5";
			ResultSet rs=ad.selectQuery(getLeaveType);
			while(rs.next())
			{
				leaveTypeID.add(rs.getString("lv_typeid"));
				leaveType.add(rs.getString("lv_type"));
			}
			leaveForm.setLeaveTypeID(leaveTypeID);
			leaveForm.setLeaveTypeName(leaveType);
		}else{
			String getLeaveType="select * from lv_type_m ";
			ResultSet rs=ad.selectQuery(getLeaveType);
			while(rs.next())
			{
				leaveTypeID.add(rs.getString("lv_typeid"));
				leaveType.add(rs.getString("lv_type"));
			}
			leaveForm.setLeaveTypeID(leaveTypeID);
			leaveForm.setLeaveTypeName(leaveType);
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("newLTA");
	}
	
	public ActionForward resetValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm leaveForm=(LTAForm)form;
		leaveForm.setNoOfDays("");
		leaveForm.setTotalLeaveDays(0);
	    leaveForm.setFiscalYearDesc("");
		leaveForm.setTravelStartDate("");
		leaveForm.setTravelEndDate("");
		leaveForm.setLeaveDetails("");
		leaveForm.setLeaveType("");
		leaveForm.setStartDate("");
		leaveForm.setStartDurationType("");
		leaveForm.setEndDate("");
		leaveForm.setEndDurationType("");
		leaveForm.setLtaAmtApplFor("");
		leaveForm.setLtaAmtAprvdFor("");
		
		
		
		
		return mapping.findForward("newLTA");
	}
	
	public ActionForward calculateDays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		LTAForm leaveForm=(LTAForm)form;
		HttpSession session=request.getSession();
		
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String userLocation="";
			String userLoc="select LOCATION_CODE from Location where LOCID='"+user.getPlantId()+"'";
			ResultSet rsloc=ad.selectQuery(userLoc);
			while(rsloc.next()){
				userLocation=rsloc.getString("LOCATION_CODE");
			}
			String tableName=getTableName(userLocation);
			
		String getLeaveStruct="select * from Leave_Structure ";
		ResultSet rsLS=ad.selectQuery(getLeaveStruct);
		float casulLeaveBal=0;
		float casulLeavMaxBal=0;
		float previlageLeaveBal=0;
		float previlageLeaveMaxBal=0;
		float sickLeaveBal=0;
		float sickLeavMaxBal=0;
		float casulLeaveAdv=0;
		
		double sicklvcloseBal=0;
		double casuallvcloseBal=0;
		String clawothltyp="";
		String slawothltyp="";
		String plawothltyp="";
		String lossOfPayawothltyp="";
		int lmindur=0;
		String startDate=(String)request.getParameter("StartDate");  
		String reqStartDate=startDate;
		String startDateDuration=(String)request.getParameter("StartDur");
		String endDate=(String)request.getParameter("EndDate");
		String reqEndDate=endDate;
		String endDurationType=(String)request.getParameter("EndDur");
		String leaveType=(String)request.getParameter("LeaveType");
		float previlageAdv=0;
		while(rsLS.next()){
			if(rsLS.getString("LEAVTYP").equalsIgnoreCase("CL")){
				casulLeaveBal=rsLS.getFloat("LMINALW");
				casulLeavMaxBal=rsLS.getFloat("LMAXALW");
				casulLeaveAdv=rsLS.getFloat("LADVDAY");
				clawothltyp=rsLS.getString("AWOTHLTYP");
				if(leaveType.equals("1"))
				lmindur=rsLS.getInt("LMINDUR");
			}
			if(rsLS.getString("LEAVTYP").equalsIgnoreCase("SL")){
				sickLeaveBal=rsLS.getFloat("LMINALW");
				sickLeavMaxBal=rsLS.getFloat("LMAXALW");
				slawothltyp=rsLS.getString("AWOTHLTYP");
				if(leaveType.equals("2"))
				lmindur=rsLS.getInt("LMINDUR");
			}
			if(rsLS.getString("LEAVTYP").equalsIgnoreCase("EL")){
				previlageLeaveBal=rsLS.getFloat("LMINALW");
				leaveForm.setPreleavmin(previlageLeaveBal);
				previlageLeaveMaxBal=rsLS.getFloat("LMAXALW");
				previlageAdv=rsLS.getFloat("LADVDAY");
				plawothltyp=rsLS.getString("AWOTHLTYP");
				if(leaveType.equals("3"))
				lmindur=rsLS.getInt("LMINDUR");
			}
			if(rsLS.getString("LEAVTYP").equalsIgnoreCase("LP")){
				lossOfPayawothltyp=rsLS.getString("AWOTHLTYP");
				if(leaveType.equals("4"))
				lmindur=rsLS.getInt("LMINDUR");
			}
		}
		System.out.println("calculateDays");
		
		
		double noOfDays=0;
		float closingBal=0;
		float awaitingBal=0;
		String getLeaveBalence="select * from lv_type_d where lv_typeid='"+leaveType+"' and lv_empcode='"+user.getEmployeeNo()+"'";
		ResultSet rsLeaveBal=ad.selectQuery(getLeaveBalence);
		while(rsLeaveBal.next())
		{
			closingBal=rsLeaveBal.getFloat("lv_clbal");
			awaitingBal=rsLeaveBal.getFloat("lv_awtBal");
		}
		
		closingBal=closingBal-awaitingBal;
		
		 String[] monthName = {"","JAN", "FEB",
				  "MAR", "APR", "MAY", "JUN", "JUL",
				  "AUG", "SEP", "OCT", "NOV",
				  "DEC"
				  };
		 String a[]=startDate.split("/");
		 startDate=a[1];
			
		 String a1[]=endDate.split("/");
		 endDate=a1[1];
		
			String startMonth=a[1];
			String EndMonth=a1[1];	
			int staMont=Integer.parseInt(startMonth);
			int endMnth=Integer.parseInt(EndMonth);
				  Calendar cal = Calendar.getInstance();
				  startMonth = monthName[staMont];
				  System.out.println("startMonth name: " + startMonth);
				  EndMonth = monthName[endMnth];
				  System.out.println("EndMonth name: " + EndMonth);
				  
					 String startDate1=(String)request.getParameter("StartDate");  
					 String b[]=startDate1.split("/");
					 String str_date=b[2]+b[1]+b[0];
					 
					 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
					 Date date = new Date();
					 String currentdate=dateFormat.format(date);
					 String c1[]=currentdate.split("/");
					 currentdate=c1[0]+c1[1]+c1[2];
					 
					 int cnt=0;
                     boolean abc=false;
  //casual leave condition                   
 //if(leaveType.equalsIgnoreCase("1"))
 if(false)                    
{
	String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+currentdate+"','"+str_date+"') as a";
	ResultSet rs=ad.selectQuery(db1);
	while(rs.next())
	{
		cnt=rs.getInt(1);
	}	
	if(cnt<=casulLeaveAdv)
	{
		//check sick leave balence 
		
		String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' and lv_typeid=2";
		ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
		while(rsSickBal.next()){
		double closeBal=rsSickBal.getDouble("lv_clbal");	
		if(closeBal==0.0){
			abc=false;
		}else{
		  noOfDays=-3.0;  
		abc=true;
		}
		}
		
	}
	
}
if(leaveType.equalsIgnoreCase("2"))
{
	String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+currentdate+"','"+str_date+"') as a";
	ResultSet rs=ad.selectQuery(db1);
	while(rs.next())
	{
		cnt=rs.getInt(1);
	}	
/*	if(cnt<=4)
	{
		noOfDays=-4.0;
			abc=true;
	}
*/

}	
if(leaveType.equalsIgnoreCase("3"))
{
	String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+currentdate+"','"+str_date+"') as a";
	ResultSet rs=ad.selectQuery(db1);
	while(rs.next())
	{
		cnt=rs.getInt(1);
	}	
	if(cnt<previlageAdv)
	{	
		
		String getSickLeaveBal="select * from lv_type_d where lv_empcode='"+user.getEmployeeNo()+"' ";
		ResultSet rsSickBal=ad.selectQuery(getSickLeaveBal);
		while(rsSickBal.next()){
			
		if(rsSickBal.getInt("lv_typeid")==2){
			 sicklvcloseBal=rsSickBal.getDouble("lv_clbal");
		}
		if(rsSickBal.getInt("lv_typeid")==1){
			 casuallvcloseBal=rsSickBal.getDouble("lv_clbal");
		}
		}
		if(sicklvcloseBal==0.0 && casuallvcloseBal==0.0){
			abc=false;
		}else{
			//noOfDays=-3.0;  
			//abc=true;
		}
	
		
	}

	
	}	
if(leaveType.equalsIgnoreCase("4"))
{
	String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+currentdate+"','"+str_date+"') as a";
	ResultSet rs=ad.selectQuery(db1);
	while(rs.next())
	{
		cnt=rs.getInt(1);
	}	
	/*if(cnt<=4)
	{
		noOfDays=-4.0;
		abc=true;
	}*/
	
}
	
	
				  
				  
				  
	  if(abc==false){
	
  if(leaveType.equalsIgnoreCase("4"))//Loss Of Pay
  {



		String checkDayType="";
		if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
		{
			if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
			{
				
				String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"'";
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
				}
				if((checkDayType.equalsIgnoreCase("W"))&& (startMonth.equalsIgnoreCase(EndMonth)) &&(a[0].equalsIgnoreCase(a1[0])))
					noOfDays=noOfDays+0.5;
			}
			
		
		}
	
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{
		    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				int noofdays=daysBetween(cal1.getTime(),cal2.getTime());
				 
					 if(staMont==endMnth)
					 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" ='W' and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !='' ";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+" ='W' and "+startMonth+" !='' ";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						
						
					 } 
					 
				 
			}
			 else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") )){
				
					 if(staMont==endMnth)
					 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='WO' and "+startMonth+"!='WS' and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
					
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+" ='W' and "+EndMonth+" !=''";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						
					 } 
				 
			 }else{

				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" ='W'  and "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W'  and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+"='W'  and "+EndMonth+" !='' ";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" ='W'  and "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W'  and "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" ='W'  and "+EndMonth+" !=''";
								ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
						
					 } 
					 
				 }
			 
		 
				 
			 }
			//check holiday or not if statrt date and end date both same 
			if(reqStartDate.equalsIgnoreCase(reqEndDate)){
					String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"'";
					ResultSet rsCheckDay=ad.selectQuery(checkDay);
					while(rsCheckDay.next())
					{
						checkDayType=rsCheckDay.getString(startMonth);
						if(!(checkDayType.equalsIgnoreCase("W")))
						noOfDays=-6.0;
					}
					
				}
  }
				  
  if(closingBal>0)
	{

	if(leaveType.equalsIgnoreCase("3"))//Previlage Leave or Earned leave
	{


		String checkDayType="";
		if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
		{
			if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
			{
				
				String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"'";
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
				}
				if(checkDayType.equalsIgnoreCase("W")||checkDayType.equalsIgnoreCase("WS")||checkDayType.equalsIgnoreCase("WO"))
					noOfDays=noOfDays+0.5;
			}
			else{
				if(checkDayType.equalsIgnoreCase("W"))
					noOfDays=noOfDays+1;
			}
		
		}
		else{
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{
		    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				int noofdays=daysBetween(cal1.getTime(),cal2.getTime());
				 
					 if(staMont==endMnth)
					 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''";
							ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						
					
					 } 
					 
				 
			}
			 else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") )){
				
					 if(staMont==endMnth)
					 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+startMonth+" !='' ";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
					
					 } 
				 
			 }else{

				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='WO' and "+startMonth+"!='WS' and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W' and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+"='W' and "+EndMonth+" !=''";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")   and "+EndMonth+" !=''";
							ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
					 } 
					 
				 }
			 
		 
				 
			 }
			
		}
		//check holiday or not if statrt date and end date both same 
		if(reqStartDate.equalsIgnoreCase(reqEndDate)){
				String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"'";
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
					if(!(checkDayType.equalsIgnoreCase("W")))
					noOfDays=-6.0;
				}
				
			}
		if(sicklvcloseBal==0.0 && casuallvcloseBal==0.0){
			noOfDays=noOfDays;
		}else{
		if(noOfDays<previlageLeaveBal||noOfDays>previlageLeaveMaxBal)
			noOfDays=0;
		}
			
	
	}
	if(leaveType.equalsIgnoreCase("2"))//SICK LEAVE
	{

		String checkDayType="";
		if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
		{
			if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
			{
				
				String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"'";
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
				}
				if(checkDayType.equalsIgnoreCase("W")||checkDayType.equalsIgnoreCase("WS")||checkDayType.equalsIgnoreCase("WO"))
					noOfDays=noOfDays+0.5;
			}
			else{
				if(checkDayType.equalsIgnoreCase("W"))
					noOfDays=noOfDays+1;
			}
		}
		else{
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{
		    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				int noofdays=daysBetween(cal1.getTime(),cal2.getTime());
				 
					 if(staMont==endMnth)
					 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" !='' ";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and   "+EndMonth+" !=''";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 
			}
			 else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") )){
				
					 if(staMont==endMnth)
					 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='WO' and "+startMonth+"!='WS' and  "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and  "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and  "+EndMonth+" !='' ";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
				 
			 }else{

				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='WO' and "+startMonth+"!='WS' and  "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W' and  "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+"='W' and  "+EndMonth+" !='' ";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='WO' and "+startMonth+"!='WS' and  "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and "+startMonth+" ='W' and  "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and "+EndMonth+"='W' and  "+EndMonth+" !=''";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
						if(closingBal<noOfDays||noOfDays<0)
							noOfDays=0;
					 } 
					 
				 }
			 
		 
				 
			 }
			//check holiday or not if statrt date and end date both same 
			if(reqStartDate.equalsIgnoreCase(reqEndDate)){
				String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"'";
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
					if(!(checkDayType.equalsIgnoreCase("W")))
					noOfDays=-6.0;
				}
				
			}
			
			
		}
		
		
		
	
			
	}
	if(leaveType.equalsIgnoreCase("1"))//CASUL LEAVE
	{
		String checkDayType="";
		String endDayType="";
		String checkEndDayType="select day,"+startMonth+" from "+tableName+" where day='"+a1[0]+"'";
		ResultSet rsEndDayType=ad.selectQuery(checkEndDayType);
		while(rsEndDayType.next())
		{
			endDayType=rsEndDayType.getString(startMonth);
		}
	
		
		if(startDateDuration.equalsIgnoreCase("FH")||endDurationType.equalsIgnoreCase("SH"))
		{
			if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH"))||(startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
			{
				
				String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"'";
				ResultSet rsCheckDay=ad.selectQuery(checkDay);
				while(rsCheckDay.next())
				{
					checkDayType=rsCheckDay.getString(startMonth);
				}
				if(checkDayType.equalsIgnoreCase("W"))
					noOfDays=noOfDays+0.5;
			
				
				
			}
			else{
				if(checkDayType.equalsIgnoreCase("W"))
					noOfDays=noOfDays+1;
			}
		
		}
		else{
			if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
			{
		    
				Calendar cal1 = new GregorianCalendar();
				 Calendar cal2 = new GregorianCalendar();
				 
				 String startYear=a[2];
				 startYear=startYear.replaceAll(" ", "");
				 String endYear=a1[2];
				 endYear=endYear.replaceAll(" ", "");
				 cal1.set(Integer.parseInt(startYear),staMont, Integer.parseInt(a[0])); 
				 cal2.set(Integer.parseInt(endYear),endMnth, Integer.parseInt(a1[0])); 
				int noofdays=daysBetween(cal1.getTime(),cal2.getTime());
				 if(closingBal>noOfDays)
				 {
					 if(staMont==endMnth)
					 {
							 String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and "+startMonth+" !='' ";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and   "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and  "+startMonth+" !=''";
							ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 }
				 else{
					 noOfDays=0;
					 leaveForm.setMessage("Casual Leave should not exceeds more than closing balence");
				 }
			}
			 else if(startDateDuration.equalsIgnoreCase("FD")&&(endDurationType.equalsIgnoreCase("FH") ||(endDurationType.equalsIgnoreCase("SH")))){
				
					 if(staMont==endMnth)
					 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and  "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
					
						
						if(endDayType.equalsIgnoreCase("W")){
							if((endDurationType.equalsIgnoreCase("FH") ||(endDurationType.equalsIgnoreCase("SH")))){
							noOfDays=0.5+noOfDays-1;
							}
							
						}
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and  "+startMonth+" !='' ";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and  "+EndMonth+"  !='' ";
							ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=0.5+noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
				 
			 }else{

				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FH") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+")  and  "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=1;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31) and   "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+") and   "+EndMonth+" !=''";
							ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays-2;
						if(closingBal<noOfDays)
							noOfDays=0;
					 } 
					 
				 }
				 if(startDateDuration.equalsIgnoreCase("SH")&&(endDurationType.equalsIgnoreCase("FD") )){
					 
					 if(staMont==endMnth)
					 {
						 noOfDays=0.5;
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and "+a1[0]+") and  "+startMonth+" !=''";
							ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1);
						}
						noOfDays=noOfDays-1;
						if(closingBal<noOfDays)
							noOfDays=0;
					 }
					 if(staMont!=endMnth)
					 {
						 
						 noOfDays=0.5;
						 if(Integer.parseInt(a[0])!=31)
						 {
						String getCountQuery="select count("+startMonth+") from "+tableName+" where (day between "+a[0]+" and 31)  and "+startMonth+" !=''";
						ResultSet rsCount=ad.selectQuery(getCountQuery);
						while(rsCount.next())
						{
							noOfDays=noOfDays+rsCount.getInt(1)-1;
						}
						 }
						String getCountQuery1="select count("+EndMonth+") from "+tableName+" where (day between 1 and "+a1[0]+")  and "+EndMonth+" !=''";
						ResultSet rsCount1=ad.selectQuery(getCountQuery1);
						while(rsCount1.next())
						{
							noOfDays=noOfDays+rsCount1.getInt(1);
						}
						noOfDays=noOfDays;
						
						if(closingBal<noOfDays||noOfDays<0)
							noOfDays=0;
					 } 
					 
				 }
			 
		 
				 
			 }
			
		}
		//check holiday or not if statrt date and end date both same 
		if(reqStartDate.equalsIgnoreCase(reqEndDate)){
			String checkDay="select day,"+startMonth+" from "+tableName+" where day='"+a[0]+"'";
			ResultSet rsCheckDay=ad.selectQuery(checkDay);
			while(rsCheckDay.next())
			{
				checkDayType=rsCheckDay.getString(startMonth);
				if(!(checkDayType.equalsIgnoreCase("W")))
				noOfDays=-6.0;
			}
			
		}
	
		
		if(noOfDays==0.5)
			noOfDays=0.5;
		
	
	}
	}
    
    //check onduty 
  String c[]=reqStartDate.split("/");
  reqStartDate=c[2]+"-"+c[1]+"-"+c[0];
  String d[]=reqEndDate.split("/");
  reqEndDate=d[2]+"-"+d[1]+"-"+d[0];
  
  String lvStDate= (String)request.getParameter("StartDate"); 
  String lvEndDate=(String)request.getParameter("EndDate");
  String  e1[]=lvStDate.split("/");
  lvStDate=e1[2]+"-"+e1[1]+"-"+e1[0];
  String f[]=lvEndDate.split("/"); 
  lvEndDate=f[2]+"-"+f[1]+"-"+f[0];
  
	  try{
		  
				  
	
	  	String getAppliedDates="Select * from OnDuty_details where (OnDuty_status=0 or OnDuty_status=1) and  user_id='"+user.getEmployeeNo()+"' " +
	  			"and (('"+reqStartDate+"' between start_date and end_date) or ('"+reqEndDate+"' between start_date and end_date))";
		ResultSet rsAppDate=ad.selectQuery(getAppliedDates);
		while(rsAppDate.next())
		{
			String startDt=rsAppDate.getString("start_date");
			
			String endDt=rsAppDate.getString("end_date");
			
			
			
			String fromTime=rsAppDate.getString("startTime");
			String fromTimeCycle=fromTime.substring(5, 7);

			String toTime=rsAppDate.getString("endTime");
			String toTimeCycle=toTime.substring(5, 7);
			
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
			
			System.out.println("from Mint="+fromMint);
			System.out.println("To Mint="+toMint);
			
			if(startDt.equalsIgnoreCase(lvEndDate))
			{
				
				if(endDurationType.equalsIgnoreCase("FD")){
					noOfDays=-1.0;
				}
				if(endDurationType.equalsIgnoreCase("FH")){
					if(fromMint<780)
					{
						  noOfDays=-1.0;  
					}
				}
				
				if(endDurationType.equalsIgnoreCase("SH")){
					if(fromMint>780)
					{
						  noOfDays=-1.0;  
					}
				}
			}
			if(endDt.equalsIgnoreCase(lvStDate))
			{
				if(startDateDuration.equalsIgnoreCase("FD"))
						{
					  noOfDays=-1.0;  

						}
				if(startDateDuration.equalsIgnoreCase("FH"))
				{
					if(fromMint<780)
					{
						  noOfDays=-1.0;  
					}

				}
				if(startDateDuration.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
						  noOfDays=-1.0;  
					}

				}
				
			}
			
			if(startDt.equalsIgnoreCase(lvStDate))
			{
				if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-1.0;  
					}
				}
				if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
					noOfDays=-1.0;
					}
				}
				if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
				{
					noOfDays=-1.0;
				}
				
			}
			
			if(endDt.equalsIgnoreCase(lvEndDate))
			{
				if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-1.0;  
					}
				}
				if(endDurationType.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<=780)
					{
					noOfDays=-1.0;
					}
				}
				if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
					noOfDays=-1.0;
					}
				}
				if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
				{
					noOfDays=-1.0;
				}
				
			}
			
			if(!(endDt.equalsIgnoreCase(lvEndDate)) && !(startDt.equalsIgnoreCase(lvStDate))){
				noOfDays=-1.0;
			}
			
		}
		
	  }catch (Exception e) {
		e.printStackTrace();
	  }
      
	  //check Permission
	  
	  
	  
	  String checkPermission="Select * from Permission_details where (Approver_Status=0 or  Approver_Status=1) and  user_id='"+user.getEmployeeNo()+"'" +
	  		" and ((date  between '"+reqStartDate+"' and '"+reqEndDate+"'))";
	  ResultSet rscheckPermi=ad.selectQuery(checkPermission);
	  while(rscheckPermi.next())
	  {

			String startDt=rscheckPermi.getString("date");
			
			
			
			
			String fromTime=rscheckPermi.getString("startTime");
			String fromTimeCycle=fromTime.substring(5, 7);

			String toTime=rscheckPermi.getString("endTime");
			String toTimeCycle=toTime.substring(5, 7);
			
			fromTime=rscheckPermi.getString("startTime");
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
			toTime=rscheckPermi.getString("endTime");
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
			
			System.out.println("from Mint="+fromMint);
			System.out.println("To Mint="+toMint);
			
			if(startDt.equalsIgnoreCase(lvStDate))
			{
				if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint>780)
					{
						  noOfDays=-2.0;  
					}  
				}
				if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
						  noOfDays=-2.0;  
					}
				}
				if(startDateDuration.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint>780)
					{
						
						  noOfDays=-2.0;  
					}
				}
				if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
				{
					  noOfDays=-2.0;  
				}
				
			}
			if(startDt.equalsIgnoreCase(lvEndDate))
			{
				if(startDateDuration.equalsIgnoreCase("FH") && endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-2.0;  
					}
				}
				if(endDurationType.equalsIgnoreCase("SH") && endDurationType.equalsIgnoreCase("SH"))
				{
					if(toMint>780)
					{
						  noOfDays=-2.0;  
					}
				}
				if(startDateDuration.equalsIgnoreCase("FD") && endDurationType.equalsIgnoreCase("FD"))
				{
					  noOfDays=-2.0;  
				}
				
				if(endDurationType.equalsIgnoreCase("FH"))
				{
					if(toMint<780)
					{
						  noOfDays=-2.0;  
					}
				}
				
			}
	  }
	 
	  
		  
	  }
	  
	  //check minimum duration between two leaves
	  // reqStartDate,reqStartDate,lmindur
	  
	  String nextlvStDt="";
	     String prevlvEndDt="";
	  String getPreviousLv="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY l.id) AS RowNum, l.start_date,l.end_date from leave_details as l 	 " +
	  "where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and '"+reqStartDate+"'>=l.end_date) as  sub Where  sub.RowNum between 1 and 1 order by RowNum desc";
	  ResultSet rsPreviouslv=ad.selectQuery(getPreviousLv);
	  while(rsPreviouslv.next()){
		  prevlvEndDt=rsPreviouslv.getString("end_date");
	      String aa[]=prevlvEndDt.split(" ");
	      prevlvEndDt=aa[0];
	      int betweenDays=0;
	  	 String getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+prevlvEndDt+"','"+reqStartDate+"')";
	  	 ResultSet rsNoOfDays=ad.selectQuery(getNoOfDays);
	  	 while(rsNoOfDays.next()){
	  		 betweenDays=rsNoOfDays.getInt(1);
	  	 }
	  	if(lmindur>betweenDays){
			 //noOfDays=-7.0;
		 }
	  }
	  
 	
 	String getNextLv="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY l.id) AS RowNum,l.start_date from leave_details as l " +
 	"where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' and '"+reqStartDate+"'<=l.start_date ) as  sub Where  sub.RowNum between 1 and 1 order by RowNum desc";
	  ResultSet rsNextLv=ad.selectQuery(getNextLv);
	  while(rsNextLv.next()){
		  nextlvStDt=rsNextLv.getString("start_date");
	      String aa[]=nextlvStDt.split(" ");
	      nextlvStDt=aa[0];
	      int betweenDays=0;
	    String  getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+reqStartDate+"','"+nextlvStDt+"')";
		ResultSet  rsNoOfDays=ad.selectQuery(getNoOfDays);
		 while(rsNoOfDays.next()){
			 betweenDays=rsNoOfDays.getInt(1);
		 }
		if(lmindur>betweenDays){
			// noOfDays=-7.0;
		 }
	  }
	  
	 
 	
	  /*String getLastTwoLeaves="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY l.id) AS RowNum,l.start_date,l.end_date from leave_details as l " +
	  "where user_id='"+user.getEmployeeNo()+"' and leave_type='"+leaveType+"' ) as  sub Where  sub.RowNum between 1 and 2 order by RowNum desc";
     ResultSet rsLast2lv=ad.selectQuery(getLastTwoLeaves);
     int totalLeaves=0;
     String prevlvStDt="";
     String prevlvEndDt="";
     while(rsLast2lv.next()){
    	 totalLeaves=totalLeaves+1;
    	 if(totalLeaves==1){
    	 prevlvEndDt=rsLast2lv.getString("end_date");
    	 String aa[]=prevlvEndDt.split(" ");
    	 prevlvEndDt=aa[0];
    	 }
    	 if(totalLeaves==2){
    		 prevlvStDt=rsLast2lv.getString("start_date");
    		 String aa[]=prevlvStDt.split(" ");
    		 prevlvStDt=aa[0];
    	 } 
     } 
     if(totalLeaves==1){
    	 System.out.println(reqStartDate);
    	 int betweenDays=0;
    	 String getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+prevlvEndDt+"','"+reqStartDate+"')";
    	 ResultSet rsNoOfDays=ad.selectQuery(getNoOfDays);
    	 while(rsNoOfDays.next()){
    		 betweenDays=rsNoOfDays.getInt(1);
    	 }
    	 System.out.println(betweenDays);
    	 if(lmindur>betweenDays){
    		 noOfDays=-7.0;
    	 }
     }
     if(totalLeaves==2){
    	 int betweenDays=0;
    	 String getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+reqStartDate+"','"+prevlvEndDt+"')";
    	 ResultSet rsNoOfDays=ad.selectQuery(getNoOfDays);
    	 while(rsNoOfDays.next()){
    		 betweenDays=rsNoOfDays.getInt(1);
    	 }
    	 System.out.println(betweenDays);
    	 if(betweenDays>0)
    	 if(lmindur>betweenDays){
    		 noOfDays=-7.0;
    	 }
    	  getNoOfDays="SELECT COUNT(*) FROM dbo.ExplodeDates('"+reqStartDate+"','"+prevlvStDt+"')";
    	  rsNoOfDays=ad.selectQuery(getNoOfDays);
    	 while(rsNoOfDays.next()){
    		 betweenDays=rsNoOfDays.getInt(1);
    	 }
    	 if(betweenDays>0)
    	 if(lmindur>betweenDays){
    		 noOfDays=-7.0;
    	 }
     }*/
	  
	  
		noOfDays=noOfDays;
		leaveForm.setTotalLeaveDays(noOfDays);
		ArrayList totalDays=new ArrayList();
		LeaveForm leaveDays=new LeaveForm();
		leaveDays.setTotalLeaveDays(noOfDays);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("calculateDays");
	}
	public String getTableName(String location)
	{
		//Created by Pradeep .Date:15-11-2016
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
	
		return tableName;
	}
	 public int daysBetween(Date d1, Date d2){
		 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
		 }
}
