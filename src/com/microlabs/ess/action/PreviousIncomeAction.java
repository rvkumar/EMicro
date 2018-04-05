package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
import com.microlabs.ess.form.PreviousIncomeForm;
import com.microlabs.hr.form.IncomeTaxReqForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;
public class PreviousIncomeAction extends DispatchAction{
   
	EssDao ad=EssDao.dBConnection();
	public ActionForward cancelRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PreviousIncomeForm  incomeForm=(PreviousIncomeForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		incomeForm.setEmployeeNo(user.getEmployeeNo());
		incomeForm.setEmployeeName(user.getFullName());
		int requestNo=incomeForm.getRequestNo();
		try{
			String deleteReq="delete from Previous_Income where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
			int i=ad.SqlExecuteUpdate(deleteReq);
			if(i>0){
			
				incomeForm.setMessage("Request has been deleted.");
		    	String getMaxReqNo="select MAX(requestNo) from Previous_Income ";
				ResultSet rsMaxReqno=ad.selectQuery(getMaxReqNo);
					while(rsMaxReqno.next()){
						int reqNo=rsMaxReqno.getInt(1);
						incomeForm.setRequestNo(reqNo+1);
					}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("newPreviousIncome");
	}
	
	
	public ActionForward showUploadFields(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PreviousIncomeForm incomeTax=(PreviousIncomeForm)form;
		
		System.out.println("showUploadFields");
		return mapping.findForward("showUploadFields");
	}
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		PreviousIncomeForm incomeTax=(PreviousIncomeForm)form;
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
						String getFilename="select * from Previous_Income_documents where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from Previous_Income_documents  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					incomeTax.setMessage("Document Details Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from Previous_Income_documents where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqNo+"'";			
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
	
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		PreviousIncomeForm incomeTax=(PreviousIncomeForm)form;
		
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
	 	String fileName  =user.getEmployeeNo()+"PID"+documentFile.getFileName();
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
			String getReqNo="select max(requestNo) from Previous_Income";
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
 	  filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles");
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
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles");
 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles";
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
			String filePath1 = "E:/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles";
			
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
		

			
			String sql9="select count(*) from Previous_Income_documents  where  file_name='"+fileName+"' and request_no='"+requestNo+"'";
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
				String sql="insert into Previous_Income_documents(request_no,user_id,file_name,description)" +
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
		String sql1="select * from Previous_Income_documents where request_no='"+incomeTax.getRequestNo()+"' and user_id='"+user.getEmployeeNo()+"' ";
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
			
			
			
		 }
		 return mapping.findForward("showUploadFields");
		}
	
	public ActionForward submitForApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		
		PreviousIncomeForm  clform=(PreviousIncomeForm)form;
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int requestNo=clform.getRequestNo();
			clform.setEmployeeNo(user.getEmployeeNo());
			clform.setEmployeeName(user.getFullName());
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
			 String dateNow = ft.format(dNow);
			try{
				LinkedList salerylist=new LinkedList();
				String getInvDet="select * from SALARY_M order by SAL_SNAME";
				ResultSet rsInv=ad.selectQuery(getInvDet);
			
				while(rsInv.next()){
					PreviousIncomeForm form2=new PreviousIncomeForm();
					form2.setSalID(rsInv.getInt("SAL_ID"));
					form2.setSalSTXT(rsInv.getString("SAL_SNAME"));
					form2.setSalLTXT(rsInv.getString("SAL_LNAME"));
					salerylist.add(form2);
		 			}
				request.setAttribute("salerylist", salerylist);
			synchronized (this) {
			
					
					
					 int newReqestnumber=0;
					 int reqestNo=clform.getRequestNo();
					 boolean checkReqno=false;
					 String checkReqNo="select count(*) from Previous_Income where requestNo='"+reqestNo+"'";
			        ResultSet rsCheck=ad.selectQuery(checkReqNo);
			        try{
			        while(rsCheck.next()){
			       	 int num=rsCheck.getInt(1);
			       	 if(num>0){
			       		 checkReqno=true;
			       	 }
			        }
			        
			    ////check 1 file atleast uploaded or not
			        
			        String fi="select count(*) from Previous_Income_documents where request_no='"+reqestNo+"'";
			        ResultSet rsfi=ad.selectQuery(fi);
			     
			        while(rsfi.next()){
			       	 int num=rsfi.getInt(1);
			       	 if(num>0){
			       		 
			       	 }
			       	 else
			       	 {
			       		clform.setMessage("Upload atleast 1 pdf document");
			       newPreviousIncome(mapping, clform, request, response);
			       		return mapping.findForward("newPreviousIncome");
			       	 }
			        }
			        
			   	 String getNewNo="select max(requestNo) from Previous_Income";
		       	 ResultSet rsNewNo=ad.selectQuery(getNewNo);
		       	 while(rsNewNo.next()){
		       		 newReqestnumber=rsNewNo.getInt(1);
		       		 newReqestnumber=newReqestnumber+1;
		       		clform.setRequestNo(newReqestnumber);
		       	 }
			        if(checkReqno==true){
			       
			       	 //update reqno in uploaded doc
			       	 
			       	 String updateDOC="update Previous_Income_documents set request_no='"+newReqestnumber+"' where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqestNo+"'";
			       	 ad.SqlExecuteUpdate(updateDOC);
			        }
			        }catch (Exception e) {
						e.printStackTrace();
					}
					
			String approverID="";
		/*	String getApprover="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where "
			+ "PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='1' and	Req_Type='Previous Income'";	
			ResultSet rsApproverID=ad.selectQuery(getApprover);*/
			String getApprover="select Approver_ID from IncomeTax_Approvers where  Priority='1' and	Req_Type='Previous Income'";	
			ResultSet rsApproverID=ad.selectQuery(getApprover);
			try {
				while(rsApproverID.next()){
					approverID=rsApproverID.getString("Approver_ID");
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String pendingApprovers="";
			String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where Req_Type='Previous Income'  and mat.Approver_Id=emp.PERNR  order by Priority";
			 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
				while(rsPendignApp.next())
				{  
					pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
				}
				if(!(pendingApprovers.equalsIgnoreCase(""))){
					pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
				}
		
			
				
		
			String savePrevIncome="insert into Previous_Income(requestNo,employeeNo,year,Approval_status,submit_date,Last_approver,Pending_approver) values('"+newReqestnumber+"',"
			+ "'"+user.getEmployeeNo()+"','"+clform.getFiscalYear()+"','In Process',getdate(),'No','"+pendingApprovers+"')";
			int i =ad.SqlExecuteUpdate(savePrevIncome);
				
			
			if(i>0){
						    	clform.setMessage("Request has been submitted for Approval.");
		    	
		    	String employ[]=request.getParameterValues("emplo");
				/*String salId[]=request.getParameterValues("sal");*/
				String salType[]=request.getParameterValues("salty");
				String start[]=request.getParameterValues("st");
				String end[]=request.getParameterValues("en");
				String amount[]=request.getParameterValues("am");
				String rem[]=request.getParameterValues("rem");
		    	
		    	
				if(employ!=null)
		    	{
				for(int j=0;j<employ.length;j++)
				{
					 String startDt=start[j];
					   String a[]=startDt.split("/");
					   startDt=a[2]+"-"+a[1]+"-"+a[0];
					   
					   String endDate=end[j];
					   String b[]=endDate.split("/");
					   endDate=b[2]+"-"+b[1]+"-"+b[0];
					   
					  /* String sal[]=salId[j].split("-");
					   String actualsalid=sal[0];*/
					
				String saveAccomodDet="insert into Previous_Income_Details(requestNo,employeeNo,year,employer,saleryType,"
						+ "start_Date,end_Date,total_amount,remarks,Approval_status,submit_date) values('"+newReqestnumber+"','"+user.getEmployeeNo()+"','"+clform.getFiscalYear()+"','"+employ[j]+"','"+salType[j]+"'"
								+ ",'"+startDt+"','"+endDate+"','"+amount[j]+"','"+rem[j]+"','In Process',getdate())";
						int l=ad.SqlExecuteUpdate(saveAccomodDet);
				}
		    	}
				
		    	//Add request to Approvals
		    	
		    	String sendtoAppr="insert into IncomeTax_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,"
				+"Approved_Persons,Requester_Id,Comments,type) values('"+newReqestnumber+"','Income Tax','"+user.getEmployeeNo()+"',getdate(),'Pending','','"+approverID+"','','"+user.getEmployeeNo()+"','','Previous Income')";
				int i1=ad.SqlExecuteUpdate(sendtoAppr);
				if(i1>0){
				
					clform.setMessage("Request has been submitted for approval");
				}
			}
	
			
			String filepath1="";
		 	String getFile = "select * from  Previous_Income_documents where request_no='"+newReqestnumber+"' and user_id='"+user.getEmployeeNo()+"'";
	     	 ResultSet rs22  =ad.selectQuery(getFile);
	     	 try {
				while(rs22.next())
				 {
					String filename2 = rs22.getString("file_name");
					int reqnumber = rs22.getInt("request_no");
					String filename1 =reqnumber+"_"+filename2;
					String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles");
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
				 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles");
				 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles/"+reqnumber;
				 	 filepath1=uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles/"+filename2;
					
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
	     	 
			
			}
			
			//end of synchronized
			}catch (Exception e) {
				e.printStackTrace();
			}
			newPreviousIncome(mapping, clform, request, response);
			
			return mapping.findForward("newPreviousIncome");}
		public ActionForward deletePrevIncome(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			//Created by Pradeep .Date:15-11-2016
			PreviousIncomeForm  clform=(PreviousIncomeForm)form;
		
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			
			int reqID=clform.getRequestNo();
			clform.setRequestNo(reqID);
			clform.setEmployeeNo(user.getEmployeeNo());
			clform.setEmployeeName(user.getFullName());
			
			try{
				
				String checkedValues=request.getParameter("cValues");
				checkedValues = checkedValues.substring(0, checkedValues.length());
				String[] documentCheck=checkedValues.split(",");
				int j=0;
				for(int i=0;i<documentCheck.length;i++)
				{
				String updateRentDt="delete from  Previous_Income where requestNo='"+reqID+"' and employeeNo='"+user.getEmployeeNo()+"' and Id='"+documentCheck[i]+"' ";
			   j=ad.SqlExecuteUpdate(updateRentDt);
				}
			    if(j>0){
			    	
			    	clform.setMessage("Details are deleted successfully");
			    }else{
			      
			    	clform.setMessage("Error...Data not deleted.");
			    	
			    }
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			try{
				String getList="select * from Previous_Income where requestNo='"+reqID+"'";
				LinkedList incomeList= new LinkedList();
				ResultSet rsList=ad.selectQuery(getList);
				while(rsList.next()){
					PreviousIncomeForm form2=new PreviousIncomeForm();
					form2.setId(rsList.getInt("ID"));
					form2.setEmployer(rsList.getString("employer"));
					/*form2.setSaleryID(rsList.getString("saleryId"));*/
					form2.setSaleryType(rsList.getString("saleryType"));
					/*form2.setSaleryDescription(rsList.getString("salery_Desc"));*/
					form2.setStartDate(EMicroUtils.display(rsList.getDate("start_Date")));
					form2.setEndDate(EMicroUtils.display(rsList.getDate("end_Date")));
					form2.setTotalAmount(rsList.getString("total_amount"));
					form2.setRemarks(rsList.getString("remarks"));
					incomeList.add(form2);
				}
				request.setAttribute("previousIncomeList", incomeList);
				}catch (Exception e) {
					e.printStackTrace();
				}
			
			
			
			return mapping.findForward("newPreviousIncome");
		}
	public String getCurrentDateWithFormat(){
		Calendar currentDate = Calendar.getInstance();
		 int day = currentDate.get(Calendar.DATE);
	     int month = currentDate.get(Calendar.MONTH) + 1;
	     int year = currentDate.get(Calendar.YEAR);
	     String dateNow=year+"-"+month+"-"+day;
	     return dateNow;
	}
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		//Created by Pradeep .Date:15-11-2016
		PreviousIncomeForm incomeForm=(PreviousIncomeForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		incomeForm.setEmployeeNo(user.getEmployeeNo());
		incomeForm.setEmployeeName(user.getFullName());
		int reqNo=incomeForm.getRequestNo();
	
		String dateNow=getCurrentDateWithFormat();
			   try {
					Connection con = ConnectionFactory.getConnection();
				PreparedStatement pstmt=con.prepareStatement("insert into Previous_Income_Details(requestNo,employeeNo,year,employer,saleryType,"
						+ "start_Date,end_Date,total_amount,remarks,Approval_status,submit_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
						   pstmt.setInt(1, reqNo);
						   pstmt.setString(2, user.getEmployeeNo());
						   pstmt.setString(3, incomeForm.getFiscalYear());
						   pstmt.setString(4, incomeForm.getEmployer());
						  /* pstmt.setString(5, incomeForm.getSaleryID());*/
						   pstmt.setString(6, incomeForm.getSaleryType());
						/*   pstmt.setString(7, incomeForm.getSaleryDescription());*/
						   String startDt=incomeForm.getStartDate();
						   String a[]=startDt.split("/");
						   startDt=a[2]+"-"+a[1]+"-"+a[0];
						   pstmt.setString(8, startDt);
						   String endDate=incomeForm.getEndDate();
						   String b[]=endDate.split("/");
						   endDate=b[2]+"-"+b[1]+"-"+b[0];
						   pstmt.setString(9, endDate);
						   pstmt.setString(10, incomeForm.getTotalAmount());
						   pstmt.setString(11, incomeForm.getRemarks());
						   pstmt.setString(12, "In Process");
						   pstmt.setString(13, dateNow);
						   boolean check=pstmt.execute();
						   if(check==false){
							   pstmt.close();
							   incomeForm.setMessage("Details are saved successfully");
								incomeForm.setEmployer("");
								incomeForm.setSaleryID("");
								incomeForm.setSaleryType("");
								incomeForm.setSaleryDescription("");
								incomeForm.setStartDate("");
								incomeForm.setEndDate("");
								incomeForm.setTotalAmount("");
								incomeForm.setRemarks("");
								
						   }
			   }catch (Exception e) {
					e.printStackTrace();
				}
			try{
			String getList="select * from Previous_Income_Details where requestNo='"+reqNo+"'";
			LinkedList incomeList= new LinkedList();
			ResultSet rsList=ad.selectQuery(getList);
			while(rsList.next()){
				PreviousIncomeForm form2=new PreviousIncomeForm();
				form2.setId(rsList.getInt("ID"));
				form2.setEmployer(rsList.getString("employer"));
				/*form2.setSaleryID(rsList.getString("saleryId"));*/
				form2.setSaleryType(rsList.getString("saleryType"));
				/*form2.setSaleryDescription(rsList.getString("salery_Desc"));*/
				form2.setStartDate(EMicroUtils.display(rsList.getDate("start_Date")));
				form2.setEndDate(EMicroUtils.display(rsList.getDate("end_Date")));
				form2.setTotalAmount(rsList.getString("total_amount"));
				form2.setRemarks(rsList.getString("remarks"));
				incomeList.add(form2);
			}
			request.setAttribute("previousIncomeList", incomeList);
			}catch (Exception e) {
				e.printStackTrace();
			}
		return mapping.findForward("newPreviousIncome");
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
	
	public ActionForward newPreviousIncome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		//Created by Pradeep .Date:15-11-2016
		PreviousIncomeForm incomeForm=(PreviousIncomeForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		incomeForm.setEmployeeNo(user.getEmployeeNo());
		incomeForm.setEmployeeName(user.getFullName());
		
		String getMaxReqNo="select MAX(requestNo) from Previous_Income ";
		ResultSet rsMaxReqno=ad.selectQuery(getMaxReqNo);
		try{
			while(rsMaxReqno.next()){
				int reqNo=rsMaxReqno.getInt(1);
				incomeForm.setRequestNo(reqNo+1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
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
				
				incomeForm.setYearList(yearList);
				
				ArrayList salList = new ArrayList();
				ArrayList salLabelList = new ArrayList();
				
				
				String d="select * from SALARY_M order by SAL_SNAME";
				ResultSet a=ad.selectQuery(d);
				try {
					while(a.next())
					{
						salList.add(a.getString("SAL_ID")+"-"+a.getString("SAL_SNAME")+"-"+a.getString("SAL_TYPE"));
						salLabelList.add(a.getString("SAL_LNAME"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				incomeForm.setSalIdList(salList);
				incomeForm.setSalLabelList(salLabelList);
				
				String re[]=empDet(user.getEmployeeNo());
				incomeForm.setEmployeeNo(user.getEmployeeNo());
				incomeForm.setEmployeeName(re[0]);
				incomeForm.setLocation(re[1]);
                incomeForm.setDepartment(re[2]);
                incomeForm.setDesignation(re[3]); 
                incomeForm.setDoj(re[4]);
                incomeForm.setStaffCategory(re[8]);
                
              //set approval status
    			LinkedList listApprers=new LinkedList();
    		String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
    		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' "
    				+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='Previous Income'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
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
		
		return mapping.findForward("newPreviousIncome");
	}
	public ActionForward displayDrafts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		//Created by Pradeep .Date:15-11-2016
		PreviousIncomeForm incomeForm=(PreviousIncomeForm)form;
		
		return mapping.findForward("displayDraftList");
	}
}
