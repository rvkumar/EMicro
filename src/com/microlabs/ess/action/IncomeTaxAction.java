package com.microlabs.ess.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.digester.rss.RSSDigester;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.IncomeTaxForm;
import com.microlabs.ess.form.OnDutyForm;
import com.microlabs.hr.form.IncomeTaxReqForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class IncomeTaxAction extends DispatchAction {
	
	EssDao ad=new EssDao();
	
	//Created by Pradeep .Date:15-11-2016
	
	public ActionForward submitDraftToApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//Created by Pradeep .Date:15-11-2016

		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");

		
		String re[]=empDet(user.getEmployeeNo());
		incomeTax.setEmployeeNo(user.getEmployeeNo());
		incomeTax.setEmployeeName(re[0]);
		incomeTax.setLocation(re[1]);
		incomeTax.setDepartment(re[2]);
		incomeTax.setDesignation(re[3]); 
		incomeTax.setDoj(re[4]);
		
		String c[]=request.getParameterValues("investmentcode");
		int rows=c.length;
		 String dateNow=getCurrentDateWithFormat();
		 String totalAmount=request.getParameter("totalAmount");
		String amount1[]=request.getParameterValues("amount");
		//save investmet
		
		 //check requstno exist or not
		 int newReqestnumber=0;
		 int reqestNo=incomeTax.getRequestNumber();
		
		 String deleteInv="delete  from investment where requestNo='"+reqestNo+"' and employeenumber='"+user.getEmployeeNo()+"'";
		int ii= ad.SqlExecuteUpdate(deleteInv);
		 String deleteInvDet="delete  from investment_Details where requsetNo='"+reqestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
		 int jj=ad.SqlExecuteUpdate(deleteInvDet);
		 
		 String approverID="";
			String pendingApprovers="";
	/*	String getApprover="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where "
		+ "PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='1' and	Req_Type='Apply Investment'";	
*/
			String getApprover="select Approver_ID from IncomeTax_Approvers where LOCATION_ID='16' and Priority='1' and	Req_Type='Apply Investment'";	

			ResultSet rsApproverID=ad.selectQuery(getApprover);
		try {
			while(rsApproverID.next()){
				approverID=rsApproverID.getString("Approver_ID");
			}
		
		
		
		/* String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
		 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) "
		 + "AND  Req_Type='Apply Investment'  and mat.Approver_Id=emp.PERNR  order by Priority";*/
			 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
					 + "mat.LOCATION_ID='16' "
					 + "AND  Req_Type='Apply Investment'  and mat.Approver_Id=emp.PERNR  order by Priority";
					 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
			//ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
			while(rsPendignApp.next())
			{  
				pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
			}
			if(!(pendingApprovers.equalsIgnoreCase(""))){
				pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
        	 
		String saveData="insert into investment(requestNo,employeenumber,fiscal_year,total_amount,Creation_Date,Record_Status,Approval_Status,Last_approver,Pending_approver) " +
	   "values('"+incomeTax.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+incomeTax.getFiscalYear()+"','"+totalAmount+"','"+dateNow+"','submit','In Process','No','"+pendingApprovers+"')";
		int j=ad.SqlExecuteUpdate(saveData);
		if(j>0){
			incomeTax.setMessage("Request has been submitted for approval");
			String investmentCode[]=request.getParameterValues("investmentcode");
			String investmentDesc[]=request.getParameterValues("InvestmentDesc");
			String  receiptNo[]=request.getParameterValues("receiptNo");
			String amount[]=request.getParameterValues("amount");
			String date1[]=request.getParameterValues("date");
			
			String status[]=request.getParameterValues("status");
			String remarks[]=request.getParameterValues("remarks");
			
			for(int i=0;i<rows;i++){
				if(!(investmentCode[i].equalsIgnoreCase("select"))){
				String date=date1[i];
				String a[]=date.split("/");
				date=a[2]+"-"+a[1]+"-"+a[0];
				
		String saveInvestmentDetails="insert into investment_Details(requsetNo,employeeNo,investment_Code,investment_Desc,receipt_no,dates,amount,status,remarks) " +
		" values('"+incomeTax.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+investmentCode[i]+"','"+investmentCode[i]+"','"+receiptNo[i]+"','"+date+"','"+amount[i]+"','"+status[i]+"','"+remarks[i]+"')";		
		int k=ad.SqlExecuteUpdate(saveInvestmentDetails);	
		System.out.println("k="+k);
				}
			}
	
			
		String sendtoAppr="insert into IncomeTax_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,"
		+"Approved_Persons,Requester_Id,Comments,type) values('"+incomeTax.getRequestNumber()+"','Income Tax','"+user.getEmployeeNo()+"',"
		+ "'"+dateNow+"','Pending','','"+approverID+"','','"+user.getEmployeeNo()+"','','Apply Investment')";
		int i=ad.SqlExecuteUpdate(sendtoAppr);
		if(i>0){
			  
				incomeTax.setMessage("Request has been submitted for approval");
		}
		
			
			int requestNo=0;
			try{
				String getReqNo="select max(requestNo) from investment";
				ResultSet rsReqNo=ad.selectQuery(getReqNo);
				while(rsReqNo.next()){
					requestNo=rsReqNo.getInt(1);
				}
				requestNo=requestNo+1;
				incomeTax.setRequestNumber(requestNo);
				
				LinkedList invCodelist=new LinkedList();
				LinkedList invlist=new LinkedList();
				
				String getInvDet="select * from Investment_Master order by INVTXT";
				ResultSet rsInv=ad.selectQuery(getInvDet);
				while(rsInv.next()){
					IncomeTaxForm form2=new IncomeTaxForm();
					form2.setInvCode(rsInv.getString("INVECO"));
					form2.setInvDes(rsInv.getString("INVTXT"));
					invCodelist.add(form2);
					invlist.add(rsInv.getString("INVTXT"));
	     			}
				request.setAttribute("invCodelist", invCodelist);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return mapping.findForward("display");
	
	}
	public ActionForward editDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int requestNo=0;
		try{
			LinkedList invCodelist=new LinkedList();
			LinkedList invlist=new LinkedList();
			String getInvDet="select * from Investment_Master order by INVTXT";
			ResultSet rsInv=ad.selectQuery(getInvDet);
			while(rsInv.next()){
				IncomeTaxForm form2=new IncomeTaxForm();
				form2.setInvCode(rsInv.getString("INVECO"));
				form2.setInvDes(rsInv.getString("INVTXT"));
				invCodelist.add(form2);
				invlist.add(rsInv.getString("INVTXT"));
     			}
			request.setAttribute("invCodelist", invCodelist);
			
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
					
					incomeTax.setYearList(yearList);
			
		try{
			 requestNo=Integer.parseInt(request.getParameter("RequestNo"));
		}catch (NumberFormatException e1) {
			requestNo=incomeTax.getRequestNumber();
		}
		incomeTax.setEmployeeNo(user.getEmployeeNo());
		incomeTax.setEmployeeName(user.getFullName());
		String getDetails="select * from investment where employeenumber='"+user.getEmployeeNo()+"' and requestNo='"+requestNo+"'";
		ResultSet rs=ad.selectQuery(getDetails);
		while(rs.next()){
			incomeTax.setRequestNumber(requestNo);
			incomeTax.setFiscalYear(rs.getString("fiscal_year"));
			incomeTax.setTotalAmount(rs.getString("total_amount"));
		}
		request.setAttribute("receiptNo1", 1);
		int totalRows=0;
		String getInvestDetails="select * from investment_Details where requsetNo='"+requestNo+"'";
		LinkedList list=new LinkedList();
		ResultSet rsDet=ad.selectQuery(getInvestDetails);
		while(rsDet.next())
		{
			IncomeTaxForm form2=new IncomeTaxForm();
			totalRows=totalRows+1;
			incomeTax.setTotalRecords(totalRows);
			form2.setInvCode(rsDet.getString("investment_Code"));
			form2.setInvDes(rsDet.getString("investment_Desc"));
			form2.setReciptNum(rsDet.getString("receipt_no"));
			form2.setAmt(rsDet.getString("amount"));
			form2.setInvDate(EMicroUtils.display(rsDet.getDate("dates")));
			form2.setInvStatus(rsDet.getString("status"));
			form2.setInvRemarks(rsDet.getString("remarks"));
			list.add(form2);
		}
		request.setAttribute("investmetDetails", list);
		 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 Properties props = new Properties();
	 	props.load(in);
		in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
		String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
		String sql1="select * from investment_documents where request_no='"+requestNo+"' and user_id='"+user.getEmployeeNo()+"' ";
		ResultSet rs1=ad.selectQuery(sql1);
		IncomeTaxForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs1.next()) {
				leaveForm1=new IncomeTaxForm();
				leaveForm1.setFileName(rs1.getString("file_name"));
				leaveForm1.setId(rs1.getString("id"));
				leaveForm1.setFileFullPath(filePath+"/"+rs1.getString("file_name"));
				a1.add(leaveForm1);
			}
			if(a1.size()>0)
			request.setAttribute("documentDetails", a1);
		
		request.setAttribute("levelNo", totalRows);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("modifyInvestment");
	}
	public ActionForward investmentList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		String getTotal="select count(*) from investment where employeenumber='"+user.getEmployeeNo()+"'";
		ResultSet rsTotal=ad.selectQuery(getTotal);
		try{
			while(rsTotal.next()){
				totalRecords=rsTotal.getInt(1);
			}
			 if(totalRecords>=10)
			  {
				 incomeTax.setTotalRecords(totalRecords);
			  startRecord=1;
			  endRecord=10;
			  incomeTax.setStartRecord(1);
			  incomeTax.setEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords;
				  incomeTax.setTotalRecords(totalRecords);
				  incomeTax.setStartRecord(1);
				  incomeTax.setEndRecord(totalRecords); 
			  }
		LinkedList listOfInvestmet=new LinkedList();	 
		String getList="select * from investment where employeenumber='"+user.getEmployeeNo()+"'";
		ResultSet rs=ad.selectQuery(getList);
		while(rs.next()){
			IncomeTaxForm taxForm=new IncomeTaxForm();
			taxForm.setRequestNumber(rs.getInt("requestNo"));
			taxForm.setFiscalYear(rs.getString("fiscal_year"));
			taxForm.setTotalAmount(rs.getString("total_amount"));
			taxForm.setSubmitDate(rs.getString("Creation_Date"));
			taxForm.setApprovalStatus(rs.getString("Approval_Status"));
			listOfInvestmet.add(taxForm);
		}
		if(listOfInvestmet.size()==0){
			request.setAttribute("noRecords", "noRecords");
			incomeTax.setMessage("No Records Found");
	
		}
		request.setAttribute("listOfInvestmet", listOfInvestmet);
		if(listOfInvestmet.size()==0)
			request.setAttribute("noRecords", "noRecords");
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("investmentList");
	}
	public ActionForward deleteDocInDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
	
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=incomeTax.getRequestNumber();
		incomeTax.setRequestNumber(reqNo);
		 int documentLength=0;
		 try 
		 {
			 LinkedList invCodelist=new LinkedList();
				LinkedList invlist=new LinkedList();
				String getInvDet="select * from Investment_Master order by INVTXT";
				ResultSet rsInv=ad.selectQuery(getInvDet);
				while(rsInv.next()){
					IncomeTaxForm form2=new IncomeTaxForm();
					form2.setInvCode(rsInv.getString("INVECO"));
					form2.setInvDes(rsInv.getString("INVTXT"));
					form2.setInvSection(rsInv.getString("Section"));
					form2.setInvLimit(rsInv.getString("Limit"));
					invCodelist.add(form2);
					invlist.add(rsInv.getString("INVTXT"));
	     			}
				request.setAttribute("invCodelist", invCodelist);
			 documentLength=documentCheck.length;
		 }catch(Exception e){
			e.printStackTrace();
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
						String getFilename="select * from investment_documents where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from investment_documents  where id="+documentId+" and request_no='"+reqNo+"'";
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					incomeTax.setMessage("Document Details Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
					
					//update Investment Details
					

					
					//update saved records
					
					String investmentCode[]=request.getParameterValues("investmentcode");
					String  receiptNo[]=request.getParameterValues("receiptNo");
					String amount[]=request.getParameterValues("amount");
					String date1[]=request.getParameterValues("date");
					
					String status[]=request.getParameterValues("status");
					String remarks[]=request.getParameterValues("remarks");
					int rows=investmentCode.length;
					
					
					String updateInvestment ="update investment set fiscal_year='"+incomeTax.getFiscalYear()+"',total_amount='"+incomeTax.getTotalAmount()+"' "
							+ "where requestNo='"+incomeTax.getRequestNumber()+"' and employeenumber='"+user.getEmployeeNo()+"'";
					ad.SqlExecuteUpdate(updateInvestment);
					String deleteInvDt="delete from investment_Details where requsetNo='"+incomeTax.getRequestNumber()+"' and employeeNo='"+user.getEmployeeNo()+"'";
					ad.SqlExecuteUpdate(deleteInvDt);
					for(int i=0;i<rows;i++){
						if(!(investmentCode[i].equalsIgnoreCase("select"))){
						String date=date1[i];
						String a1[]=date.split("/");
						date=a1[2]+"-"+a1[1]+"-"+a1[0];
						
				String saveInvestmentDetails="insert into investment_Details(requsetNo,employeeNo,investment_Code,investment_Desc,receipt_no,dates,amount,status,remarks) " +
				" values('"+incomeTax.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+investmentCode[i]+"','"+investmentCode[i]+"','"+receiptNo[i]+"','"+date+"','"+amount[i]+"','"+status[i]+"','"+remarks[i]+"')";		
				int k=ad.SqlExecuteUpdate(saveInvestmentDetails);	
						}
					}
					
					
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			editDetails(mapping, form, request, response);
			
			
			/*String sql1="select * from investment_documents where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqNo+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
			
			try{
				IncomeTaxForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs.getString("file_name"));
					leaveForm1.setId(rs.getString("id"));
					a1.add(leaveForm1);
				}
				request.setAttribute("documentDetails", a1);
				
			}catch(Exception e){
				e.printStackTrace();
			}*/
		return mapping.findForward("modifyInvestment");
	}
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
		HttpSession session=request.getSession();
	
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqNo=incomeTax.getRequestNumber();
		incomeTax.setRequestNumber(reqNo);
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
						String getFilename="select * from investment_documents where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from investment_documents  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					incomeTax.setMessage("Document Details Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/Income Tax/Apply Investment/UploadFile",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from investment_documents where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqNo+"'";
			
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
		return mapping.findForward("showUploadFields");
	}
	
	public ActionForward submitForApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		incomeTax.setEmployeeNo(user.getEmployeeNo());
		incomeTax.setEmployeeName(user.getFullName());
		String c[]=request.getParameterValues("investmentcode");
		int rows=c.length;
		int req_no=0;
		String param=request.getParameter("param");
		 String dateNow=getCurrentDateWithFormat();
		 String totalAmount=request.getParameter("totalAmount");
		 //String totaldeduction=request.getParameter("totaldeduction");
		String amount1[]=request.getParameterValues("amount");
		//save investmet
		
		
	
		 //check requstno exist or not
		 int newReqestnumber=0;
		 int reqestNo=incomeTax.getRequestNumber();
		 boolean checkReqno=false;
		 String checkReqNo="select count(*) from investment where requestNo='"+reqestNo+"'";
        ResultSet rsCheck=ad.selectQuery(checkReqNo);
        try{
        while(rsCheck.next()){
       	 int num=rsCheck.getInt(1);
       	 if(num>0){
       		 checkReqno=true;
       	 }
        }
        ////check 1 file atleast uploaded or not
        
        
        String fi="select count(*) from investment_documents where request_no='"+reqestNo+"'";
        ResultSet rsfi=ad.selectQuery(fi);
     
        while(rsfi.next()){
       	 int num=rsfi.getInt(1);
       	 if(num>0){
       		 
       	 }
       	 else
       	 {
       		incomeTax.setMessage("Upload atleast 1 pdf document");
       		display(mapping, form, request, response);
       		return mapping.findForward("display");
       	 }
        }
        
        String getNewNo="select max(requestNo) from investment";
      	 ResultSet rsNewNo=ad.selectQuery(getNewNo);
      	 while(rsNewNo.next()){
      		 newReqestnumber=rsNewNo.getInt(1);
      		 newReqestnumber=newReqestnumber+1;
      		 incomeTax.setRequestNumber(newReqestnumber);
      	 }
        if(checkReqno==true){
       	/* String getNewNo="select max(requestNo) from investment";
       	 ResultSet rsNewNo=ad.selectQuery(getNewNo);
       	 while(rsNewNo.next()){
       		 newReqestnumber=rsNewNo.getInt(1);
       		 newReqestnumber=newReqestnumber+1;
       		 incomeTax.setRequestNumber(newReqestnumber);
       	 }*/
       	 //update reqno in uploaded doc
      
       	 String updateDOC="update investment_documents set request_no='"+newReqestnumber+"' where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqestNo+"'";
       	 ad.SqlExecuteUpdate(updateDOC);
       	 
       	 
       	 
       	
        }
        }catch (Exception e) {
			e.printStackTrace();
		}
         String approvalStatu="";
     	if(param.equalsIgnoreCase("submit"))
     		approvalStatu="In Process";
			
			if(param.equalsIgnoreCase("draft"))
				approvalStatu="Drafts";
       
			String approverID="";
			String pendingApprovers="";
	/*	String getApprover="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where "
		+ "PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='1' and	Req_Type='Apply Investment'";	
		ResultSet rsApproverID=ad.selectQuery(getApprover);*/
			String getApprover="select Approver_ID from IncomeTax_Approvers where Priority='1' and	Req_Type='Apply Investment'";	
			ResultSet rsApproverID=ad.selectQuery(getApprover);
			
		try {
			while(rsApproverID.next()){
				approverID=rsApproverID.getString("Approver_ID");
			}
		
		
		
		 /*String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
		 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) "
		 + "AND  Req_Type='Apply Investment'  and mat.Approver_Id=emp.PERNR  order by Priority";*/
			 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where Req_Type='Apply Investment'  and mat.Approver_Id=emp.PERNR  order by Priority";
			 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
			while(rsPendignApp.next())
			{  
				pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
			}
			if(!(pendingApprovers.equalsIgnoreCase(""))){
				pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String saveData="insert into investment(requestNo,employeenumber,fiscal_year,total_amount,Creation_Date,Record_Status,Approval_Status,Last_approver,Pending_approver) " +
	   "values('"+newReqestnumber+"','"+user.getEmployeeNo()+"','"+incomeTax.getFiscalYear()+"','"+totalAmount+"',getdate(),'"+param+"','"+approvalStatu+"','No','"+pendingApprovers+"')";
		int j=ad.SqlExecuteUpdate(saveData);
		if(j>0){
			if(param.equalsIgnoreCase("submit"))
			incomeTax.setMessage("Request is submitted for approval");
			
			if(param.equalsIgnoreCase("draft"))
				incomeTax.setMessage("Request is saved in drafts");
			
			String investmentCode[]=request.getParameterValues("investmentcode");	
			String  receiptNo[]=request.getParameterValues("receiptNo");
			String amount[]=request.getParameterValues("amount");
			String date1[]=request.getParameterValues("date");
			
			
			String remarks[]=request.getParameterValues("remarks");
			
			for(int i=0;i<rows;i++){
				if(!(investmentCode[i].equalsIgnoreCase("select"))){
					
				String date=date1[i];
				String a[]=date.split("/");
				date=a[2]+"-"+a[1]+"-"+a[0];
				
				
				String inv[]=investmentCode[i].split(":");
				String actinvocde=inv[0];
				
		String saveInvestmentDetails="insert into investment_Details(requsetNo,employeeNo,investment_Code,investment_Desc,receipt_no,dates,amount,status,remarks) " +
		" values('"+newReqestnumber+"','"+user.getEmployeeNo()+"','"+actinvocde+"','','"+receiptNo[i]+"','"+date+"','"+amount[i]+"','Declared','"+remarks[i]+"')";		
		int k=ad.SqlExecuteUpdate(saveInvestmentDetails);	
		System.out.println("k="+k);
				}
			}
			
			
			//summary 
			String secfilter[]=request.getParameterValues("secfilter");	
			String  secreq[]=request.getParameterValues("secreq");
			String secreqyear[]=request.getParameterValues("secreqyear");
			String seclimit[]=request.getParameterValues("seclimit");
			String secallow[]=request.getParameterValues("secallow");
			
			for(int i=0;i<secfilter.length;i++){
				if(!(secreq[i].equalsIgnoreCase("0"))){
					
			
				
		String saveInvestmentDetails="insert into Investment_Summary(request_no,section,claim_request,claim_year,limit,allowed) " +
		" values('"+newReqestnumber+"','"+secfilter[i]+"','"+secreq[i]+"','"+secreqyear[i]+"','"+seclimit[i]+"','"+secallow[i]+"')";		
		int k=ad.SqlExecuteUpdate(saveInvestmentDetails);	
		System.out.println("k="+k);
				}
			}
			
			
		if(param.equals("submit")){
			//get first approver details from IncomeTax Approvers
		
		String sendtoAppr="insert into IncomeTax_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,"
		+"Approved_Persons,Requester_Id,Comments,type) values('"+newReqestnumber+"','Income Tax','"+user.getEmployeeNo()+"',"
		+ "'"+dateNow+"','Pending','','"+approverID+"','','"+user.getEmployeeNo()+"','','Apply Investment')";
		int i=ad.SqlExecuteUpdate(sendtoAppr);
		if(i>0){
		
				incomeTax.setMessage("Request has been submitted for approval");
				
		
				
				
		}
		}
		
		/*String getreq = "select max(request_no) from investment_documents";
		 ResultSet rsC=ad.selectQuery(getreq);
		 try {
			while(rsC.next())
			 {
				int req = rsC.getInt(1);
				 req_no=req+1;
			 }
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		 
		String filepath1="";
	 	String getFile = "select * from  investment_documents where request_no='"+newReqestnumber+"' and user_id='"+user.getEmployeeNo()+"'";
     	 ResultSet rs22  =ad.selectQuery(getFile);
     	 try {
			while(rs22.next())
			 {
				String filename2 = rs22.getString("file_name");
				int reqnumber = rs22.getInt("request_no");
				String filename1 =reqnumber+"_"+filename2;
				String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles");
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
			 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles");
			 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles/"+reqnumber;
			 	 filepath1=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles/"+filename2;
				
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
				
			
	 	     
			 	
			 }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		
			try{
			
				
				LinkedList invCodelist=new LinkedList();
				LinkedList invlist=new LinkedList();
				
				String getInvDet="select * from Investment_Master order by INVTXT";
				ResultSet rsInv=ad.selectQuery(getInvDet);
				while(rsInv.next()){
					IncomeTaxForm form2=new IncomeTaxForm();
					form2.setInvCode(rsInv.getString("INVECO"));
					form2.setInvDes(rsInv.getString("INVTXT"));
					invCodelist.add(form2);
					invlist.add(rsInv.getString("INVTXT"));
	     			}
				request.setAttribute("invCodelist", invCodelist);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		display(mapping, form, request, response);
		return mapping.findForward("display");
	}
	public String getCurrentDateWithFormat(){
		Calendar currentDate = Calendar.getInstance();
		 int day = currentDate.get(Calendar.DATE);
	     int month = currentDate.get(Calendar.MONTH) + 1;
	     int year = currentDate.get(Calendar.YEAR);
	     String dateNow=year+"-"+month+"-"+day;
	     return dateNow;
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
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm form1=(IncomeTaxForm)form;
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		form1.setEmployeeNo(user.getEmployeeNo());
		form1.setEmployeeName(user.getFullName());
		int requestNo=0;
		try{
			
			
			String getReqNo="select max(requestNo) from investment";
			ResultSet rsReqNo=ad.selectQuery(getReqNo);
			while(rsReqNo.next()){
				requestNo=rsReqNo.getInt(1);
			}
		
			requestNo=requestNo+1;
			form1.setRequestNumber(requestNo);
			
			
			
			LinkedList invCodelist=new LinkedList();
			LinkedList invlist=new LinkedList();
			
			String getInvDet="select * from Investment_Master order by INVTXT";
			ResultSet rsInv=ad.selectQuery(getInvDet);
			while(rsInv.next()){
				IncomeTaxForm form2=new IncomeTaxForm();
				form2.setInvCode(rsInv.getString("INVECO")+":"+rsInv.getString("INVTXT"));
				form2.setInvDes(rsInv.getString("INVTXT"));
				form2.setInvSection(rsInv.getString("Section"));
				form2.setInvLimit(rsInv.getString("Limit"));
				invCodelist.add(form2);
				invlist.add(rsInv.getString("INVTXT"));
     			}
			request.setAttribute("invCodelist", invCodelist);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ArrayList sectionlist=new ArrayList();
		
		String limit="select sum(convert(int,limit)) as limit,Section from investment_master group by Section";
		ResultSet l=ad.selectQuery(limit);
		try {
			while(l.next())
			{
				IncomeTaxForm form2=new IncomeTaxForm();
				form2.setInvSection(l.getString("Section").trim());
				form2.setInvLimit(l.getString("limit"));
				String prev="select Investment_Master.Section,sum(investment_Details.amount)  as availed from investment_Details,investment,Investment_Master "
						+ "where investment.requestNo=investment_Details.requsetNo and Approval_Status!='Rejected' and fiscal_year=2016 "
						+ "and Investment_Master.INVECO=investment_Details.investment_Code and investment_Details.employeeNo='"+user.getEmployeeNo()+"' and"
						+ " Investment_Master.Section='"+l.getString("Section")+"'  group by Investment_Master.Section";
						
						ResultSet l3=ad.selectQuery(prev);
						if(l3.next())
						{
							form2.setInvRemarks(l3.getString("availed"));
						}
						else
						{
							form2.setInvRemarks("0");
						}
							
							sectionlist.add(form2);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		request.setAttribute("sectionlist", sectionlist);
		
		
		ArrayList yearList=new ArrayList();
/*		ResultSet rs17 = ad.selectQuery("select year(getdate()) as yearlist    order by 1 desc");*/ 
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
				
				form1.setYearList(yearList);
				
				
				
				String re[]=empDet(user.getEmployeeNo());
				form1.setEmployeeNo(user.getEmployeeNo());
				form1.setEmployeeName(re[0]);
				form1.setLocation(re[1]);
				form1.setDepartment(re[2]);
				form1.setDesignation(re[3]); 
				form1.setDoj(re[4]);
				form1.setStaffCategory(re[8]);
		
				
				
				
				//set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='Apply Investment'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
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
				
		return mapping.findForward("display");
	}
	
	public ActionForward showUploadFields(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		
		System.out.println("showUploadFields");
		return mapping.findForward("showUploadFields");
	}
	
	public ActionForward modifyuploadDocInDrafts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		FormFile documentFile=incomeTax.getDocumentFile();
		String documentName=documentFile.getFileName();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 String filePath="";
		try{
			LinkedList invCodelist=new LinkedList();
			LinkedList invlist=new LinkedList();
			String getInvDet="select * from Investment_Master order by INVTXT";
			ResultSet rsInv=ad.selectQuery(getInvDet);
			while(rsInv.next()){
				IncomeTaxForm form2=new IncomeTaxForm();
				form2.setInvCode(rsInv.getString("INVECO"));
				form2.setInvDes(rsInv.getString("INVTXT"));
				form2.setInvSection(rsInv.getString("Section"));
				form2.setInvLimit(rsInv.getString("Limit"));
				invCodelist.add(form2);
				invlist.add(rsInv.getString("INVTXT"));
     			}
			request.setAttribute("invCodelist", invCodelist);
		}catch(Exception e){
			e.printStackTrace();
		}
		  String fileName   =  documentFile.getFileName();
		String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
		try{
			if(ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("txt")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg"))
			{
				 byte[] size=documentFile.getFileData();
				 int length=documentName.length();
			     int dot=documentName.lastIndexOf(".");
		 	  filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles");
		 	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
		 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles");
		 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
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
					String filePath1 = "E:/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
					
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
			int requestNo=incomeTax.getRequestNumber();
				incomeTax.setRequestNumber(requestNo);
			incomeTax.setRequestNumber(requestNo);
			String sql9="select count(*) from investment_documents  where  file_name='"+documentFile.getFileName()+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				incomeTax.setMessage("Document aleardy uploaded..please choose another file");
			}
			else
			{
				String sql="insert into investment_documents(request_no,user_id,file_name)" +
				"values('"+requestNo+"','"+user.getEmployeeNo()+"','"+documentFile.getFileName()+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					
					//update saved records
					
					String investmentCode[]=request.getParameterValues("investmentcode");
					String  receiptNo[]=request.getParameterValues("receiptNo");
					String amount[]=request.getParameterValues("amount");
					String date1[]=request.getParameterValues("date");
					
					String status[]=request.getParameterValues("status");
					String remarks[]=request.getParameterValues("remarks");
					int rows=investmentCode.length;
					
					
					String updateInvestment ="update investment set fiscal_year='"+incomeTax.getFiscalYear()+"',total_amount='"+incomeTax.getTotalAmount()+"' "
							+ "where requestNo='"+incomeTax.getRequestNumber()+"' and employeenumber='"+user.getEmployeeNo()+"'";
					ad.SqlExecuteUpdate(updateInvestment);
					String deleteInvDt="delete from investment_Details where requsetNo='"+incomeTax.getRequestNumber()+"' and employeeNo='"+user.getEmployeeNo()+"'";
					ad.SqlExecuteUpdate(deleteInvDt);
					for(int i=0;i<rows;i++){
						if(!(investmentCode[i].equalsIgnoreCase("select"))){
						String date=date1[i];
						String a1[]=date.split("/");
						date=a1[2]+"-"+a1[1]+"-"+a1[0];
						
				String saveInvestmentDetails="insert into investment_Details(requsetNo,employeeNo,investment_Code,investment_Desc,receipt_no,dates,amount,status,remarks) " +
				" values('"+incomeTax.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+investmentCode[i]+"','"+investmentCode[i]+"','"+receiptNo[i]+"','"+date+"','"+amount[i]+"','"+status[i]+"','"+remarks[i]+"')";		
				int k=ad.SqlExecuteUpdate(saveInvestmentDetails);	
						}
					}
					}
			}
			
			
			}
			else{
				incomeTax.setMessage("Please upload files with doc,docx,pdf or jpg extensions");
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
				
			}
		String sql1="select * from investment_documents where request_no='"+incomeTax.getRequestNumber()+"' and user_id='"+user.getEmployeeNo()+"' ";
		ResultSet rs=ad.selectQuery(sql1);
		IncomeTaxForm leaveForm1=null;
			ArrayList a1=new ArrayList();
			while(rs.next()) {
				leaveForm1=new IncomeTaxForm();
				leaveForm1.setFileName(rs.getString("file_name"));
				leaveForm1.setId(rs.getString("id"));
				leaveForm1.setFileFullPath(filePath+"/"+rs.getString("file_name"));
				a1.add(leaveForm1);
			}
			request.setAttribute("documentDetails", a1);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
		editDetails(mapping, form, request, response);
		return mapping.findForward("modifyInvestment");
		
	}
	
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		IncomeTaxForm incomeTax=(IncomeTaxForm)form;
		
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
	 	
	 	
	 	
	 	String fileName  =user.getEmployeeNo()+"INV"+documentFile.getFileName();
	 	
		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();

		if ( ext.equalsIgnoreCase("pdf")) {
			if((filesize>1048576))
			{
				incomeTax.setMessage("pdf file should be less than 1Mb");
				OnDutyAction lA = new OnDutyAction();
				LinkedList appList= lA.getApprovers(request, response);
				request.setAttribute("appList", appList);
			}
			else
			{
			int requestNo=0;
			String getReqNo="select max(requestNo) from investment";
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
			incomeTax.setRequestNumber(requestNo);
			
	     //jsp/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles
 	     //String filepath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/ESS/On Duty/UploadFiles/"+documentFile.getFileName());
 	  filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles");
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
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles");
 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
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
			String filePath1 = "E:/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
			
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
			
			String sql9="select count(*) from investment_documents  where  file_name='"+fileName+"' and request_no='"+requestNo+"'";
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
				String sql="insert into investment_documents(request_no,user_id,file_name,description)" +
				"values('"+incomeTax.getRequestNumber()+"','"+user.getEmployeeNo()+"','"+fileName+"','"+fildesc+"')";
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
		String sql1="select * from investment_documents where request_no='"+incomeTax.getRequestNumber()+"' and user_id='"+user.getEmployeeNo()+"' ";
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
		 return mapping.findForward("showUploadFields");
		}
	
}
