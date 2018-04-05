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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.microlabs.ess.form.ClaimTRLPForm;
import com.microlabs.ess.form.IncomeTaxForm;
import com.microlabs.hr.form.IncomeTaxReqForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class ClaimTRLPAction extends DispatchAction{
	EssDao ad=new EssDao();
	
	public ActionForward showUploadFields(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm incomeTax=(ClaimTRLPForm)form;
		
		System.out.println("showUploadFields");
		return mapping.findForward("showUploadFields");
	}
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm incomeTax=(ClaimTRLPForm)form;
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
						String getFilename="select * from Claim_TRLP_documents where id="+documentId+" and request_no='"+reqNo+"'";
						ResultSet rsFileName=ad.selectQuery(getFilename);
						while(rsFileName.next())
						{
							fileName=rsFileName.getString("file_name");
						}
						
	        				sql="delete from Claim_TRLP_documents  where id="+documentId+" and request_no='"+reqNo+"'";
	        			
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					incomeTax.setMessage("Document Details Deleted sucessfully");
	        					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	        					 Properties props = new Properties();
	        					 	props.load(in);
	        						in.close();
	        					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	        					 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles");
	        					 	 String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles";
	        					 	 
	        					 	File fileToCreate = new File(filePath, fileName);
	        					 	boolean test=fileToCreate.delete();
	        					 	System.out.println(test);
	        					 	
	        					 	//delete file in another path
	        					 	
	        					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles",fileName);
	        					 	boolean test1=fileToCreate1.delete();
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
			}catch(Exception se){
			 se.printStackTrace();
		    }
			String sql1="select * from Claim_TRLP_documents where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqNo+"'";
			
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
		ClaimTRLPForm incomeTax=(ClaimTRLPForm)form;
		
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
	 	String fileName  =user.getEmployeeNo()+"HRA"+documentFile.getFileName();
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
			String getReqNo="select max(requestNo) from Claim_TRLP";
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
 	  filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles");
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
 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles");
 	/* filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles/"+requestNo+"";*/
 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles";
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
			/*String filePath1 = "E:/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles/"+requestNo+"";*/
			String filePath1 = "E:/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles";
			
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
		
	
			
			String sql9="select count(*) from Claim_TRLP_documents  where  file_name='"+fileName+"' and request_no='"+requestNo+"'";
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
				String sql="insert into Claim_TRLP_documents(request_no,user_id,file_name,description)" +
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
		String sql1="select * from Claim_TRLP_documents where request_no='"+incomeTax.getRequestNo()+"' and user_id='"+user.getEmployeeNo()+"' ";
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
	
	public ActionForward deleteRent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm  clform=(ClaimTRLPForm)form;
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int requestNo=clform.getRequestNo();
		clform.setRequestNo(requestNo);
		int reqID=clform.getRequiredID();
		clform.setRequiredID(reqID);
		clform.setEmployeeNo(user.getEmployeeNo());
		clform.setEmployeeName(user.getFullName());
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try{
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			clform.setLocationIdList(locationList);
			clform.setLocationLabelList(locationLabelList);
			String checkedValues=request.getParameter("cValues");
			checkedValues = checkedValues.substring(0, checkedValues.length());
			String[] documentCheck=checkedValues.split(",");
			int j=0;
			for(int i=0;i<documentCheck.length;i++)
			{
			String updateRentDt="delete from  claim_address_details where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"' and Id='"+documentCheck[i]+"' ";
		   j=ad.SqlExecuteUpdate(updateRentDt);
			}
		    if(j>0){
	
		    	clform.setMessage("Rent details are deleted successfully");
		    }else{
		     
		    	clform.setMessage("Error...Data not deleted.");
		    	
		    }
		
		}catch (Exception e) {
			e.printStackTrace();
		}
LinkedList listofAccomDet=new LinkedList();
		
		String getAccomdList="select * from claim_address_details where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
		ResultSet rs=ad.selectQuery(getAccomdList);
		try{
		while(rs.next()){
			ClaimTRLPForm  clform1=new ClaimTRLPForm();
		
			clform1.setFromDate(EMicroUtils.display(rs.getDate("fromDate")));
			clform1.setToDate(EMicroUtils.display(rs.getDate("toDate")));
			clform1.setAmount(rs.getInt("payableAmount"));
			clform1.setAccommodationType(rs.getString("accomdation_Type"));
			clform1.setPanNo(rs.getString("panNo"));
			clform1.setLandName(rs.getString("landName"));
			clform1.setCity(rs.getString("city"));
			clform1.setId(rs.getInt("Id"));
			listofAccomDet.add(clform1);
		}
		if(listofAccomDet.size()>0){
			request.setAttribute("listofAccomDet", listofAccomDet);
		}
		} 
		catch (Exception e) {
		e.printStackTrace();
		}
		
		clform.setFromDate("");
		clform.setToDate("");
		clform.setAmount(0);
		clform.setAccommodationType("");
		clform.setPanNo("");
		clform.setLandName("");
		clform.setCity("");
		clform.setAddress1("");
		clform.setAddress2("");
		clform.setAddress3("");
		
		return mapping.findForward("newClaimTRLP");
	}
	
	public ActionForward modifyAccomDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm  clform=(ClaimTRLPForm)form;
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int requestNo=clform.getRequestNo();
		clform.setRequestNo(requestNo);
		int reqID=clform.getRequiredID();
		clform.setEmployeeNo(user.getEmployeeNo());
		clform.setEmployeeName(user.getFullName());
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try{
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			clform.setLocationIdList(locationList);
			clform.setLocationLabelList(locationLabelList);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
		String fromDate=clform.getFromDate();
		String a[]=fromDate.split("/");
		fromDate=a[2]+"-"+a[1]+"-"+a[0];
		String toDate=clform.getToDate();
		String b[]=toDate.split("/");
		toDate=b[2]+"-"+b[1]+"-"+b[0];
		
	String updateRentDt="update claim_address_details set fromDate='"+fromDate+"',toDate='"+toDate+"',payableAmount='"+clform.getAmount()+"'," +
		"panNo='"+clform.getPanNo()+"',landName='"+clform.getLandName()+"',city='"+clform.getCity()+"',address1='"+clform.getAddress1()+"',address2='"+clform.getAddress2()+"'," +
		"address3='"+clform.getAddress3()+"',accomdation_Type='"+clform.getAccommodationType()+"' where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"' and Id='"+reqID+"' ";
	    int i=ad.SqlExecuteUpdate(updateRentDt);
	    if(i>0){

	    	clform.setMessage("Rent details are updated successfully");
	    }else{
	    
	    	clform.setMessage("Error...Data not Updated.");
	    	
	    }
		
	    LinkedList listofAccomDet=new LinkedList();
		
		String getAccomdList="select * from claim_address_details where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
		ResultSet rs=ad.selectQuery(getAccomdList);
		try{
		while(rs.next()){
			ClaimTRLPForm  clform1=new ClaimTRLPForm();
		
			clform1.setFromDate(EMicroUtils.display(rs.getDate("fromDate")));
			clform1.setToDate(EMicroUtils.display(rs.getDate("toDate")));
			clform1.setAmount(rs.getInt("payableAmount"));
			clform1.setAccommodationType(rs.getString("accomdation_Type"));
			clform1.setPanNo(rs.getString("panNo"));
			clform1.setLandName(rs.getString("landName"));
			clform1.setCity(rs.getString("city"));
			clform1.setId(rs.getInt("Id"));
			listofAccomDet.add(clform1);
		}
		if(listofAccomDet.size()>0){
			request.setAttribute("listofAccomDet", listofAccomDet);
		}
		} 
		catch (Exception e) {
		e.printStackTrace();
		}
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
				
				clform.setYearList(yearList);
				
				String re[]=empDet(user.getEmployeeNo());
                clform.setEmployeeNo(user.getEmployeeNo());
                clform.setEmployeeName(re[0]);
                clform.setLocation(re[1]);
                clform.setDepartment(re[2]);
                clform.setDesignation(re[3]); 
                clform.setDoj(re[4]);
                clform.setStaffCategory(re[8]);
		
		clform.setFromDate("");
		clform.setToDate("");
		clform.setAmount(0);
		clform.setAccommodationType("");
		clform.setPanNo("");
		clform.setLandName("");
		clform.setCity("");
		clform.setAddress1("");
		clform.setAddress2("");
		clform.setAddress3("");
		return mapping.findForward("newClaimTRLP");
	}
	
	public ActionForward editAccomDetaisl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm  clform=(ClaimTRLPForm)form;
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int requestNo=clform.getRequestNo();
		clform.setRequestNo(requestNo);
		int reqID=Integer.parseInt(request.getParameter("reqID"));
		clform.setRequiredID(reqID);
		clform.setEmployeeNo(user.getEmployeeNo());
		clform.setEmployeeName(user.getFullName());
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try{
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			clform.setLocationIdList(locationList);
			clform.setLocationLabelList(locationLabelList);
		
			
			String year="select * from Claim_TRLP where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
			ResultSet rsq=ad.selectQuery(year);
			if(rsq.next())
			{
				clform.setFiscalYear(rsq.getString("year"));
			}
		
		String getRentDetails="select * from claim_address_details where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"' and Id='"+reqID+"' ";
		ResultSet rs=ad.selectQuery(getRentDetails);
		while(rs.next()){
			clform.setFromDate(EMicroUtils.display(rs.getDate("fromDate")));
			clform.setToDate(EMicroUtils.display(rs.getDate("toDate")));
			clform.setAmount(rs.getInt("payableAmount"));
			clform.setAccommodationType(rs.getString("accomdation_Type"));
			clform.setCity(rs.getString("city"));
			clform.setLandName(rs.getString("landName"));
			clform.setPanNo(rs.getString("panNo"));
			clform.setLandName(rs.getString("landName"));
			clform.setAddress1(rs.getString("address1"));
			clform.setAddress2(rs.getString("address2"));
			clform.setAddress3(rs.getString("address3"));
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
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
				
				clform.setYearList(yearList);
				
				String re[]=empDet(user.getEmployeeNo());
                clform.setEmployeeNo(user.getEmployeeNo());
                clform.setEmployeeName(re[0]);
                clform.setLocation(re[1]);
                clform.setDepartment(re[2]);
                clform.setDesignation(re[3]); 
                clform.setDoj(re[4]);
                clform.setStaffCategory(re[8]);
		
		request.setAttribute("modify", "modify");
		request.setAttribute("addDetails", "addDetails");
		return mapping.findForward("newClaimTRLP");
	}
	public ActionForward submitForApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm  clform=(ClaimTRLPForm)form;
			
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int requestNo=clform.getRequestNo();
			clform.setEmployeeNo(user.getEmployeeNo());
			clform.setEmployeeName(user.getFullName());
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
			 String dateNow = ft.format(dNow);
			synchronized (this) {
				try{
				boolean checkRequestNo=false;
				int maxReqNo=0;
				String approverID="";
			/*	String getApprover="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where "
				+ "PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='1' and	Req_Type='Claim TRLP / HRA'";	
*/
				String getApprover="select Approver_ID from IncomeTax_Approvers where Priority='1' and	Req_Type='Claim TRLP / HRA'";	

				ResultSet rsApproverID=ad.selectQuery(getApprover);
				try {
					while(rsApproverID.next()){
						approverID=rsApproverID.getString("Approver_ID");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 int newReqestnumber=0;
				 int reqestNo=clform.getRequestNo();
				 boolean checkReqno=false;
				 String checkReqNo="select count(*) from Claim_TRLP where requestNo='"+reqestNo+"'";
		        ResultSet rsCheck=ad.selectQuery(checkReqNo);
		        try{
		        while(rsCheck.next()){
		       	 int num=rsCheck.getInt(1);
		       	 if(num>0){
		       		 checkReqno=true;
		       	 }
		        }
		        
		        ////check 1 file atleast uploaded or not
		        
		        String fi="select count(*) from Claim_TRLP_documents where request_no='"+reqestNo+"'";
		        ResultSet rsfi=ad.selectQuery(fi);
		     
		        while(rsfi.next()){
		       	 int num=rsfi.getInt(1);
		       	 if(num>0){
		       		 
		       	 }
		       	 else
		       	 {
		       		clform.setMessage("Upload atleast 1 pdf document");
		       		newClaimTRLP(mapping, clform, request, response);
		       		return mapping.findForward("newClaimTRLP");
		       	 }
		        }
		        String getNewNo="select max(requestNo) from Claim_TRLP";
		       	 ResultSet rsNewNo=ad.selectQuery(getNewNo);
		       	 while(rsNewNo.next()){
		       		 newReqestnumber=rsNewNo.getInt(1);
		       		 newReqestnumber=newReqestnumber+1;
		       		clform.setRequestNo(newReqestnumber);
		       	 }
		        if(checkReqno==true){
		       	/* String getNewNo="select max(requestNo) from Claim_TRLP";
		       	 ResultSet rsNewNo=ad.selectQuery(getNewNo);
		       	 while(rsNewNo.next()){
		       		 newReqestnumber=rsNewNo.getInt(1);
		       		 newReqestnumber=newReqestnumber+1;
		       		clform.setRequestNo(newReqestnumber);
		       	 }*/
		       	 //update reqno in uploaded doc
		       	 
		       	 String updateDOC="update Claim_TRLP_documents set request_no='"+newReqestnumber+"' where user_id='"+user.getEmployeeNo()+"' and request_no='"+reqestNo+"'";
		       	 ad.SqlExecuteUpdate(updateDOC);
		        }
		        }catch (Exception e) {
					e.printStackTrace();
				}
				
			
				
				String pendingApprovers="";
				/* String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
				 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' and loc.LOCATION_CODE=emp.LOCID) "
				 + "AND  Req_Type='Claim TRLP / HRA'  and mat.Approver_Id=emp.PERNR  order by Priority";
*/
				String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where Req_Type='Claim TRLP / HRA'  and mat.Approver_Id=emp.PERNR  order by Priority";
				ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
					while(rsPendignApp.next())
					{  
						pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
					}
					if(!(pendingApprovers.equalsIgnoreCase(""))){
						pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
					}
				
					String saveData="insert into Claim_TRLP(requestNo,employeeNo,Year,location,Staff_category,Record_Status,Approval_status,submit_date,Last_approver,Pending_approver) " +
					"values('"+clform.getRequestNo()+"','"+user.getEmployeeNo()+"','"+clform.getFiscalYear()+"','"+clform.getLocation()+"','"+clform.getStaffCategory()+"','Submit','In Process',getdate(),'No','"+pendingApprovers+"')";
					int i=ad.SqlExecuteUpdate(saveData);
					if(i>0)
					{
					String fromDate[]=request.getParameterValues("fd");
					String toDate[]=request.getParameterValues("td");
					String amount[]=request.getParameterValues("am");
					String acctyp[]=request.getParameterValues("acc");
					String pan[]=request.getParameterValues("pn");
					String land[]=request.getParameterValues("la");
					String city[]=request.getParameterValues("ci");
					String add1[]=request.getParameterValues("a1");
					String add2[]=request.getParameterValues("a2");
					String add3[]=request.getParameterValues("a3");
					String met[]=request.getParameterValues("met");
					
					if(fromDate!=null)
			    	{
					for(int j=0;j<fromDate.length;j++)
					{
						
						String startDt=fromDate[j];
						   String a[]=startDt.split("/");
						   startDt=a[2]+"-"+a[1]+"-"+a[0];
						   
						   String endDate=toDate[j];
						   String b[]=endDate.split("/");
						   endDate=b[2]+"-"+b[1]+"-"+b[0];
						   int metro=0;
						   
						   if(met[j].equalsIgnoreCase("yes"))
						   {
							   metro=1;
						   }
							   else
							   {
								   metro=0;
							   }
							   
						
					String saveAccomodDet="insert into claim_address_details(requestNo,employeeNo,fromDate,toDate,payableAmount,panNo,landName,city,address1,address2,address3,accomdation_Type,metro) " +
							"values('"+newReqestnumber+"','"+user.getEmployeeNo()+"','"+startDt+"','"+endDate+"','"+amount[j]+"','"+pan[j]+"','"+land[j]+"','"+city[j]+"'," +
									"'"+add1[j]+"','"+add2[j]+"','"+add3[j]+"','"+acctyp[j]+"','"+metro+"')";
							int l=ad.SqlExecuteUpdate(saveAccomodDet);
					}
			    	}
					}
					String sendtoAppr="insert into IncomeTax_Request(Req_Id,Req_Type,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,"
					+"Approved_Persons,Requester_Id,Comments,type) values('"+newReqestnumber+"','Income Tax','"+user.getEmployeeNo()+"',"
					+ "'"+dateNow+"','Pending','','"+approverID+"','','"+user.getEmployeeNo()+"','','Claim TRLP / HRA')";
					int i1=ad.SqlExecuteUpdate(sendtoAppr);
					if(i1>0){
			
						clform.setMessage("Request has been submitted for approval");
					}
					
					String filepath1="";
				 	String getFile = "select * from  Claim_TRLP_documents where request_no='"+newReqestnumber+"' and user_id='"+user.getEmployeeNo()+"'";
			     	 ResultSet rs22  =ad.selectQuery(getFile);
			     	 try {
						while(rs22.next())
						 {
							String filename2 = rs22.getString("file_name");
							int reqnumber = rs22.getInt("request_no");
							String filename1 =reqnumber+"_"+filename2;
							String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles");
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
						 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles");
						 	 filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles/"+reqnumber;
						 	 filepath1=uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles/"+filename2;
							
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
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			newClaimTRLP(mapping, form, request, response);
			
			clform.setFromDate("");
			clform.setToDate("");
			clform.setAmount(0);
			clform.setAccommodationType("");
			clform.setPanNo("");
			clform.setLandName("");
			clform.setCity("");
			clform.setAddress1("");
			clform.setAddress2("");
			clform.setAddress3("");
			return mapping.findForward("newClaimTRLP");
			}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm  clform=(ClaimTRLPForm)form;
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int requestNo=clform.getRequestNo();
		clform.setEmployeeNo(user.getEmployeeNo());
		clform.setEmployeeName(user.getFullName());
		
		synchronized (this) {
			try{
			boolean checkRequestNo=false;
			int maxReqNo=0;
			String getMaxRequstNo="select max(requestNo) from Claim_TRLP";
			ResultSet rsRequestNo=ad.selectQuery(getMaxRequstNo);
			while(rsRequestNo.next())
			{
				maxReqNo=rsRequestNo.getInt(1);
				maxReqNo+=1;
			}
			if(requestNo==maxReqNo){
				String saveData="insert into Claim_TRLP(requestNo,employeeNo,Year,location,Staff_category,Record_Status) " +
				"values('"+requestNo+"','"+user.getEmployeeNo()+"','"+clform.getFiscalYear()+"','"+clform.getLocation()+"','"+clform.getStaffCategory()+"','Drafts')";
				int i=ad.SqlExecuteUpdate(saveData);
	
				clform.setMessage("Data Saved in Drafts Successfully.");
				
			}
			else{
			//update reqno in claim_address_details	
				
				String saveData="insert into Claim_TRLP(requestNo,employeeNo,Year,location,Staff_category,Record_Status) " +
				"values('"+maxReqNo+"','"+user.getEmployeeNo()+"','"+clform.getFiscalYear()+"','"+clform.getLocation()+"','"+clform.getStaffCategory()+"','Drafts')";
				int i=ad.SqlExecuteUpdate(saveData);
				if(i>0){
					String updateClamAddress="update claim_address_details set requestNo='"+maxReqNo+"' where requestNo='"+requestNo+"'";
					ad.SqlExecuteUpdate(updateClamAddress);
					
					clform.setMessage("Data Saved in Drafts Successfully.");
				}
			}
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		newClaimTRLP(mapping, form, request, response);
		
		clform.setFromDate("");
		clform.setToDate("");
		clform.setAmount(0);
		clform.setAccommodationType("");
		clform.setPanNo("");
		clform.setLandName("");
		clform.setCity("");
		clform.setAddress1("");
		clform.setAddress2("");
		clform.setAddress3("");
		return mapping.findForward("newClaimTRLP");
	}
	
	public ActionForward addAccommodDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm  clform=(ClaimTRLPForm)form;
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int requestNo=clform.getRequestNo();
		clform.setEmployeeNo(user.getEmployeeNo());
		clform.setEmployeeName(user.getFullName());
		String fromDate=clform.getFromDate();
		String a[]=fromDate.split("/");
		fromDate=a[2]+"-"+a[1]+"-"+a[0];
		String toDate=clform.getToDate();
		String b[]=toDate.split("/");
		toDate=b[2]+"-"+b[1]+"-"+b[0];
		
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		ResultSet rs11 = ad.selectQuery("select * from location");
		try{
		while(rs11.next()) {
			locationList.add(rs11.getString("LOCATION_CODE"));
			locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		clform.setLocationIdList(locationList);
		clform.setLocationLabelList(locationLabelList);
		String saveAccomodDet="insert into claim_address_details(requestNo,employeeNo,fromDate,toDate,payableAmount,panNo,landName,city,address1,address2,address3,accomdation_Type) " +
		"values('"+requestNo+"','"+user.getEmployeeNo()+"','"+fromDate+"','"+toDate+"','"+clform.getAmount()+"','"+clform.getPanNo()+"','"+clform.getLandName()+"','"+clform.getCity()+"'," +
				"'"+clform.getAddress1()+"','"+clform.getAddress2()+"','"+clform.getAddress3()+"','"+clform.getAccommodationType()+"')";
		int i=ad.SqlExecuteUpdate(saveAccomodDet);
		if(i>0){
			
			clform.setMessage("Rent Details Saved  Successfully.");
		}
		LinkedList listofAccomDet=new LinkedList();
		
		String getAccomdList="select * from claim_address_details where requestNo='"+requestNo+"' and employeeNo='"+user.getEmployeeNo()+"'";
		ResultSet rs=ad.selectQuery(getAccomdList);
		try{
		while(rs.next()){
			ClaimTRLPForm  clform1=new ClaimTRLPForm();
		
			clform1.setFromDate(EMicroUtils.display(rs.getDate("fromDate")));
			clform1.setToDate(EMicroUtils.display(rs.getDate("toDate")));
			clform1.setAmount(rs.getInt("payableAmount"));
			clform1.setAccommodationType(rs.getString("accomdation_Type"));
			clform1.setPanNo(rs.getString("panNo"));
			clform1.setLandName(rs.getString("landName"));
			clform1.setCity(rs.getString("city"));
			clform1.setId(rs.getInt("Id"));
			listofAccomDet.add(clform1);
		}
		if(listofAccomDet.size()>0){
			request.setAttribute("listofAccomDet", listofAccomDet);
		}
		} 
		catch (Exception e) {
		e.printStackTrace();
		}
		
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
				
				clform.setYearList(yearList);
				
				String re[]=empDet(user.getEmployeeNo());
                clform.setEmployeeNo(user.getEmployeeNo());
                clform.setEmployeeName(re[0]);
                clform.setLocation(re[1]);
                clform.setDepartment(re[2]);
                clform.setDesignation(re[3]); 
                clform.setDoj(re[4]);
                clform.setStaffCategory(re[8]);
		
		clform.setFromDate("");
		clform.setToDate("");
		
		clform.setAccommodationType("");
		clform.setPanNo("");
		clform.setLandName("");
		clform.setCity("");
		clform.setAddress1("");
		clform.setAddress2("");
		clform.setAddress3("");
		
		return mapping.findForward("newClaimTRLP");
	}
	
	public ActionForward newClaimTRLP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm  clform=(ClaimTRLPForm)form;
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		clform.setEmployeeNo(user.getEmployeeNo());
		clform.setEmployeeName(user.getFullName());
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try{
		while(rs11.next()) {
			locationList.add(rs11.getString("LOCATION_CODE"));
			locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
		}
		
		clform.setLocationIdList(locationList);
		clform.setLocationLabelList(locationLabelList);
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		int requestNo=0;
		String getMaxRequstNo="select max(requestNo) from Claim_TRLP";
		ResultSet rsRequestNo=ad.selectQuery(getMaxRequstNo);
		try {
			while(rsRequestNo.next())
			{
				requestNo=rsRequestNo.getInt(1);
				requestNo+=1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		clform.setRequestNo(requestNo);
	
		
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
				
				clform.setYearList(yearList);
				
				String re[]=empDet(user.getEmployeeNo());
                clform.setEmployeeNo(user.getEmployeeNo());
                clform.setEmployeeName(re[0]);
                clform.setLocation(re[1]);
                clform.setDepartment(re[2]);
                clform.setDesignation(re[3]); 
                clform.setDoj(re[4]);
                clform.setStaffCategory(re[8]);
                
                
              //set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+user.getEmployeeNo()+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='Claim TRLP / HRA'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
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
		
		return mapping.findForward("newClaimTRLP");
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
	
	public ActionForward displayDrafts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ClaimTRLPForm  clform=(ClaimTRLPForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String getDraftRecords="select * from Claim_TRLP where employeeNo='"+user.getEmployeeNo()+"' ";
		ResultSet rsDrafts=ad.selectQuery(getDraftRecords);
		LinkedList listOfClaimTRLP=new LinkedList();
		try{
		while(rsDrafts.next()){
			ClaimTRLPForm form2=new ClaimTRLPForm();
			form2.setRequestNo(rsDrafts.getInt("requestNo"));
			form2.setFiscalYear(rsDrafts.getString("Year"));
			form2.setEmployeeNo(rsDrafts.getString("employeeNo"));
			form2.setEmployeeName(user.getFullName());
			form2.setLocation(rsDrafts.getString("location"));
			form2.setStaffCategory(rsDrafts.getString("Staff_category"));
			form2.setRecordStatus(rsDrafts.getString("Record_Status"));
			listOfClaimTRLP.add(form2);
		}
		if(listOfClaimTRLP.size()>0){
			request.setAttribute("listOfClaimTRLP", listOfClaimTRLP);
		}else{
			request.setAttribute("noRecords", "noRecords");
		}
		}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("displayDraftList");
	}
}
