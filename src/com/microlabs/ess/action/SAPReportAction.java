package com.microlabs.ess.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.CustomerMasterForm;
import com.microlabs.ess.form.MaterialCodeRequestForm;
import com.microlabs.ess.form.PackageMaterialMasterForm;
import com.microlabs.ess.form.RawMaterialForm;
import com.microlabs.ess.form.SAPReportForm;
import com.microlabs.ess.form.ServiceMasterRequestForm;
import com.microlabs.ess.form.VendorMasterRequestForm;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class SAPReportAction extends DispatchAction{
	public ActionForward getmatDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad=new EssDao();
		
		String forwardType="";
		int j=0;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String reqId=request.getParameter("reqno");
		String requstNo1=reqId;
		String matType=request.getParameter("matType");
		
		
		
		
		if(matType.equalsIgnoreCase("Service Master"))
		{
			 LinkedList SerDetails=new LinkedList();
		        String location="";
		 		
		    	ApprovalsForm sform6=new ApprovalsForm();
		 		

		 		
		 		String query="select SER.SAC_Code,ser.request_date,loc.LOCNAME,loc.LOCATION_CODE,ser.service_description,ser.detailed_desc,u.LTXT," +
		 				"pur.PURCHASE_GROUP_DESC,ser.service_catagory,se.STXT,ser.machine_name,ser.app_value,ser.justification," +
		 				"val.VALUATION_DESC,ser.attachment,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
				 	"SAP_CREATED_BY,REQUESTED_BY from SERVICE_MASTER as ser,Location as loc,UNIT_MESUREMENT as u," +
		 				"PURCHASE_GROUP as pur, SERVICE_GROUP as se,VALUATION_CLASS as val where REQUEST_NO='"+requstNo1+"' and	" +
		 				"loc.LOCID=ser.plant_code and u.UNIT_OF_MEAS_ID=ser.uom and pur.PURCHASE_GROUP_ID=ser.purchase_group " +
		 				"and se.group_id=ser.service_group and val.VALUATION_ID=ser.valuation_class";
			
		 		System.out.println(query);
				
					ResultSet rs=ad.selectQuery(query);
					try {
						if(rs.next()){
						
							sform6.setRequestNo(requstNo1);
							sform6.setRequestNumber(requstNo1);
							sform6.setPlantCode(rs.getString("LOCNAME"));
							location=rs.getString("LOCATION_CODE");
							sform6.setSacCode(rs.getString("SAC_Code"));
						    sform6.setRequestDate(rs.getString("request_date"));
							sform6.setServiceDescription(rs.getString("service_description"));
							sform6.setDetailedServiceDescription(rs.getString("detailed_desc"));
							sform6.setUom(rs.getString("LTXT"));
							sform6.setPurchaseGroup(rs.getString("PURCHASE_GROUP_DESC"));
							String sercat=rs.getString("service_catagory");
							
							
							
							if(sercat.equalsIgnoreCase("ZITA"))
							{
								sform6.setServiceCatagory("ZITA- AMC-IT");
								
							}
							if(sercat.equalsIgnoreCase("ZAMC"))
							{
								sform6.setServiceCatagory("ZAMC- Annual maintainence");
								
							}
							if(sercat.equalsIgnoreCase("ZCLB"))
							{
								sform6.setServiceCatagory("ZCLB- Calibration");
								
							}
							if(sercat.equalsIgnoreCase("ZCIV"))
							{
								sform6.setServiceCatagory("ZCIV- Civil works");
								
							}
							if(sercat.equalsIgnoreCase("ZMNT"))
							{
								sform6.setServiceCatagory("ZMNT- Maintainence");
								
							}
							if(sercat.equalsIgnoreCase("ZITM"))
							{
								sform6.setServiceCatagory("ZITM-MAINAINENCE-IT");
								
							}
							if(sercat.equalsIgnoreCase("ZMKT"))
							{
								sform6.setServiceCatagory("ZMKT- Marketing");
								
							}
							if(sercat.equalsIgnoreCase("ZTST"))
							{
								sform6.setServiceCatagory("ZTST- Testing&Analysis");
								
							}
							if(sercat.equalsIgnoreCase("ZTRC"))
							{
								sform6.setServiceCatagory("ZTRC- Training&Recruitment");
								
							}

							
							
							sform6.setServiceGroup(rs.getString("STXT"));
							sform6.setE_m_name(rs.getString("machine_name"));
							sform6.setApp_amount(rs.getString("app_value"));
						
							sform6.setJustification(rs.getString("justification"));
							sform6.setValuationClass(rs.getString("VALUATION_DESC"));
						
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null){
								sform6.setSapCodeNo(sapCodeno);		
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							sform6.setSapCodeExists("Yes");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							sform6.setSapCodeExists("No");
						String sapCreationDate=rs.getString("SAP_CREATION_DATE");
						String sapDate[]=sapCreationDate.split(" ");
						sapCreationDate=sapDate[0];
						String sapCode[]=sapCreationDate.split("-");
						sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
						sform6.setSapCreationDate(sapCreationDate);
						sform6.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						sform6.setRequestedBy(rs.getString("REQUESTED_BY"));
							}
								
																	
						
						
						SerDetails.add(sform6);
						
						
						String query_file="select file_name from service_documents where request_no='"+requstNo1+"'";
						ArrayList documentDetails=new ArrayList();
						ResultSet rs_file=ad.selectQuery(query_file);
						try{
						while(rs_file.next()){
							ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
							sf.setSfile(rs_file.getString("file_name"));
							sf.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/"+rs_file.getString("file_name")+"");
							documentDetails.add(sf);
						}
						
						
						}
						catch(Exception e){
							e.printStackTrace();
						}
						request.setAttribute("documentDetails", documentDetails);
						request.setAttribute("ServiceMasterView", SerDetails);
						
						
						
						//set ApproverDetails 
						
														
						
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		     		
					int checkStatus=0;
					
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
							"where mast.Location='"+location+"' AND  mast.Material_Type='Service Master' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String approveStatus="";
							String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date," +
							"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo1+"' and " +
							"mast.Location='"+location+"' AND all_r.type='Service Master' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver " +
							"and (mast.Role='User' or mast.Role='Accounts' or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority," +
							"mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase(""))
								{
									apprvers.setApproveStatus("In Process");
								}
								
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							 
								
								
							}
							if(approveStatus.equalsIgnoreCase(""))
							{
								apprvers.setApproveStatus("In Process");
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							listApprers.add(apprvers);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					request.setAttribute("approverDetails", listApprers);
					forwardType="SermasterView";
		}
		
		
		if(matType.equalsIgnoreCase("Vendor Master"))
		{
			String accountGroupID="";
	           LinkedList venDetails=new LinkedList();		 

	           ApprovalsForm vendorMasterRequestForm=new ApprovalsForm();
	           
			
					
				String getVendorDetails="select  v.REQUEST_NO,v.REQUEST_DATE,acc.ACCOUNT_GROUP_NAME,acccl.ACC_CLERK_DESC,v.VIEW_TYPE,v.Type_Of_Vendor," +
			"v.TITLE,v.NAME,v.ADDRESS1,v.ADDRESS2,v.ADDRESS3,v.ADDRESS4,v.CITY,v.PINCODE,v.LANDLINE_NO," +
			"v.MOBILE_NO,v.FAX_NO,v.EMAIL_ID,cur.ISOCD,rec.RECONCILIATION_ACCOUNT_NAME,  v.IS_ELIGIBLE_FOR_TDS,v.LST_NO,v.TIN_NO" +
			",v.CST_NO, v.IS_APPROVED_VENDOR,v.PAN_No,v.Service_Tax_Registration_No,v.IS_REGD_EXCISE_VENDOR," +
			"v.ECC_No,v.Excise_Reg_No,v.Excise_Range,v.Excise_Division,v.COMMISSIONERATE,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
			"SAP_CREATED_BY,REQUESTED_BY  from vendor_master_m as v ,ACCOUNT_GROUP_M as acc,ACC_CLERK_M as acccl ,Currency as cur," +
			"RECONCILIATION_ACCOUNT_M as rec  where REQUEST_NO='"+reqId+"' and acc.ACCOUNT_GROUP_ID=v.ACCOUNT_GROUP_ID and  " +
			" cur.WAERS=v.CURRENCY_ID  and rec.RECONCILIATION_ACCOUNT_ID=v.RECONCILATION_ACT_ID " +
			" and  acccl.ACC_CLERK_ID=v.ACCOUNT_CLERK_ID";
					
					
					ResultSet rs=ad.selectQuery(getVendorDetails);
					try {
						if(rs.next())
						{
							vendorMasterRequestForm.setRequestNo(reqId);
							vendorMasterRequestForm.setRequestNumber(reqId);

						    accountGroupID=rs.getString("ACCOUNT_GROUP_NAME");
						    accountGroupID=accountGroupID.replace(" ","");
							vendorMasterRequestForm.setAccountGroupId(accountGroupID);
						     
							 String reqDate=rs.getString("REQUEST_DATE");
								String a[]=reqDate.split(" ");
								reqDate=a[0];
								String b[]=reqDate.split("-");
								reqDate=b[2]+"/"+b[1]+"/"+b[0];
								 vendorMasterRequestForm.setRequestDate(reqDate);
							 
							 String viewType=rs.getString("VIEW_TYPE");
							 if(viewType.equalsIgnoreCase("1"))
							 {
							vendorMasterRequestForm.setPurchaseView("Purchase View");
							 }
							 if(viewType.equalsIgnoreCase("2"))
							 {
							vendorMasterRequestForm.setAccountView("Account View");
							 }
							 if(viewType.equalsIgnoreCase("3"))
							 {
									vendorMasterRequestForm.setPurchaseView("Purchase View");
							vendorMasterRequestForm.setAccountView("Account View");
							 }

							 vendorMasterRequestForm.setTitle(rs.getString("TITLE"));
							 vendorMasterRequestForm.setName(rs.getString("NAME"));
							 vendorMasterRequestForm.setAddress1(rs.getString("ADDRESS1"));
							vendorMasterRequestForm.setAddress2(rs.getString("ADDRESS2"));
							 vendorMasterRequestForm.setAddress3(rs.getString("ADDRESS3"));
							 vendorMasterRequestForm.setAddress4(rs.getString("ADDRESS4"));
							vendorMasterRequestForm.setCity(rs.getString("CITY"));
							 vendorMasterRequestForm.setPinCode(rs.getString("PINCODE"));
						
							vendorMasterRequestForm.setLandLineNo(rs.getString("LANDLINE_NO"));
							 vendorMasterRequestForm.setMobileNo(rs.getString("MOBILE_NO"));
							 vendorMasterRequestForm.setFaxNo(rs.getString("FAX_NO"));
							 vendorMasterRequestForm.setEmailId(rs.getString("EMAIL_ID"));
							 vendorMasterRequestForm.setCurrencyId(rs.getString("ISOCD"));
							 vendorMasterRequestForm.setReConcillationActId(rs.getString("RECONCILIATION_ACCOUNT_NAME"));
							String elgTds=rs.getString("IS_ELIGIBLE_FOR_TDS");
							if(elgTds.equalsIgnoreCase("1"))
							{
								vendorMasterRequestForm.setElgTds("YES");
							}
							if(elgTds.equalsIgnoreCase("0"))
							{
								vendorMasterRequestForm.setElgTds("No");
							}
						
							 vendorMasterRequestForm.setLstNo(rs.getString("LST_NO"));
							 vendorMasterRequestForm.setTinNo(rs.getString("TIN_NO"));
							vendorMasterRequestForm.setCstNo(rs.getString("CST_NO"));
							 vendorMasterRequestForm.setPanNo(rs.getString("PAN_No"));
							 vendorMasterRequestForm.setServiceTaxRegNo(rs.getString("Service_Tax_Registration_No"));
							 String isRegistVendor=rs.getString("IS_REGD_EXCISE_VENDOR");
							 	if(isRegistVendor.equalsIgnoreCase("1"))
								{
								 vendorMasterRequestForm.setRegExciseVendor("Yes");
								 request.setAttribute("RegExciseVendor", "RegExciseVendor");
								}
								if(isRegistVendor.equalsIgnoreCase("0"))
								{
									
									
								vendorMasterRequestForm.setRegExciseVendor("No");
								}
							 
							 vendorMasterRequestForm.setEccNo(rs.getString("ECC_No"));
							 vendorMasterRequestForm.setExciseRegNo(rs.getString("Excise_Reg_No"));
							 vendorMasterRequestForm.setExciseRange(rs.getString("Excise_Range"));
							 vendorMasterRequestForm.setCommissionerate(rs.getString("COMMISSIONERATE"));
							 vendorMasterRequestForm.setExciseDivision(rs.getString("Excise_Division"));

							 vendorMasterRequestForm.setAccountClerkId(rs.getString("ACC_CLERK_DESC"));
							 vendorMasterRequestForm.setIsApprovedVendor(rs.getString("IS_APPROVED_VENDOR"));
							 String isApproveVendor=rs.getString("IS_APPROVED_VENDOR");
							if(isApproveVendor.equalsIgnoreCase("1"))
							{
								vendorMasterRequestForm.setIsApprovedVendor("Yes");
							}	if(isApproveVendor.equalsIgnoreCase("0")){
								vendorMasterRequestForm.setIsApprovedVendor("No");
							}
							 
						
						
							 String typeofVendor=rs.getString("Type_Of_Vendor");
							 if(typeofVendor.equalsIgnoreCase("I"))
								{												
								vendorMasterRequestForm.setTypeOfVendor("IMPORTER");
								}
							 if(typeofVendor.equalsIgnoreCase("MD"))
								{												
								vendorMasterRequestForm.setTypeOfVendor("DEPOT MANUFACTURER");
								}
							 if(typeofVendor.equalsIgnoreCase("M"))
								{												
								vendorMasterRequestForm.setTypeOfVendor("MANUFACTURER");
								}
							 if(typeofVendor.equalsIgnoreCase("D"))
								{												
								vendorMasterRequestForm.setTypeOfVendor("DEALER");
								}
							 if(typeofVendor.equalsIgnoreCase("FD"))
								{												
								vendorMasterRequestForm.setTypeOfVendor("FIRST STAGE DEALER OF INDIGENOUS");
								}
							 if(typeofVendor.equalsIgnoreCase("SD"))
								{												
								vendorMasterRequestForm.setTypeOfVendor("SECOND STAGE DEALER OF INDIGENOUS");
								}
							 String sapCodeno=rs.getString("SAP_CODE_NO");  
								if(sapCodeno!=null){ 
									vendorMasterRequestForm.setSapCodeNo(sapCodeno);		
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								vendorMasterRequestForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								vendorMasterRequestForm.setSapCodeExists("No");
							String sapCreationDate=rs.getString("SAP_CREATION_DATE");
							String sapDate[]=sapCreationDate.split(" ");
							sapCreationDate=sapDate[0];
							String sapCode[]=sapCreationDate.split("-");
							sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
							vendorMasterRequestForm.setSapCreationDate(sapCreationDate);
							vendorMasterRequestForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
							vendorMasterRequestForm.setRequestedBy(rs.getString("REQUESTED_BY"));
								}
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					ArrayList listOfFiles=new ArrayList();
					String getUploadedFiles="select * from temp_vendor_documents where request_no='"+reqId+"' and userId='"+user.getEmployeeNo()+"' ";
					ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
					try {
						while(rsUploadFile.next())
						{
							VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
							vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
							vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
							vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
							listOfFiles.add(vendorMasterRequestForm1);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("documentDetails", listOfFiles);
					
					//payment term
					String getVendorDetails4="select pay.PAYMENT_TERM_NAME from vendor_master_m as v,PAYMENT_TERM_M as pay	" +
							"where REQUEST_NO='"+reqId+"'and pay.PAYMENT_TERM_ID=v.PAYMENT_TERM_ID";
							
					ResultSet rs4=ad.selectQuery(getVendorDetails4);
					try {
						if(rs4.next())
						{
							 vendorMasterRequestForm.setPaymentTermId(rs4.getString("PAYMENT_TERM_NAME"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					//tds
					String getVendorDetails1="select tds.TDS_SECTION_DESC from vendor_master_m as v," +
							"TDS_SECTION_M as tds where REQUEST_NO='"+reqId+"' and tds.TDS_SECTION_ID=v.TDS_CODE";
							
					ResultSet rs1=ad.selectQuery(getVendorDetails1);
					try {
						if(rs1.next())
						{
							 vendorMasterRequestForm.setTdsCode(rs1.getString("TDS_SECTION_DESC"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//country
					String getVendorDetails2=" select cou.LANDX from vendor_master_m as v,Country as cou " +
							" where REQUEST_NO='"+reqId+"' and cou.LAND1=v.COUNTRY_ID ";
					
			ResultSet rs2=ad.selectQuery(getVendorDetails2);
			try {
				if(rs2.next())
				{
					 vendorMasterRequestForm.setCountry(rs2.getString("LANDX"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//state
			String getVendorDetails3=" select sta.BEZEI from vendor_master_m as v,State as sta " +
					"where REQUEST_NO='"+reqId+"' and sta.BLAND=v.STATE";
			
	ResultSet rs3=ad.selectQuery(getVendorDetails3);
	try {
		if(rs3.next())
		{
			 vendorMasterRequestForm.setState(rs3.getString("BEZEI"));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
					
					
					venDetails.add(vendorMasterRequestForm);
						request.setAttribute("vendorMasterView", venDetails);
						
						
						if(accountGroupID.equalsIgnoreCase("Micro-Staff") || accountGroupID.equalsIgnoreCase("Domestic") || accountGroupID.equalsIgnoreCase("Loan-Licence") || accountGroupID.equalsIgnoreCase("Plants"))
						{
							accountGroupID="Local";
						}
						
						int checkStatus=0;
						LinkedList listApprers=new LinkedList();
						String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
								"where mast.Location='' AND  mast.Material_Type='Vendor Master' AND Material_Group='"+accountGroupID+"' and mast.Approver_Id=emp.PERNR order by Priority";
						ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
						try {
							while(rsApprDetails.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(rsApprDetails.getString("Priority"));
								apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
								String empCode=rsApprDetails.getString("Approver_Id");
								String approveStatus="";
								String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date," +
								"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and " +
								"mast.Location='' AND all_r.type='Vendor Master' AND Material_Group='"+accountGroupID+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver " +
								"and (mast.Role='User' or mast.Role='Accounts' or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority," +
								"mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								while(rsAppInfo.next())
								{
									
									apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
									approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase(""))
									{
										apprvers.setApproveStatus("In Process");
									}
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
									{
									apprvers.setDate(rsAppInfo.getString("approved_date"));
									}else{
										apprvers.setDate(rsAppInfo.getString("rejected_date"));
									}
									String comments=rsAppInfo.getString("Comments");
									if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo.getString("Comments"));
									}
									
									
								}
								if(approveStatus.equalsIgnoreCase(""))
								{
									apprvers.setApproveStatus("In Process");
								}
								apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
								listApprers.add(apprvers);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						request.setAttribute("approverDetails", listApprers);	
						forwardType="vendorMasterView";
		}
		
		if(matType.equalsIgnoreCase("Customer Master"))
		{
			LinkedList CustDetails=new LinkedList();
	        String location="";
	 		
	    	ApprovalsForm custForm=new ApprovalsForm();
	 		
	 	
	 		
	 		String editRecord="select C.ACCOUNT_GROUP_ID,c.REQUEST_NO,c.REQUEST_DATE,c.VIEW_TYPE,c.cutomer_code,c.Customer_Type,c.NAME, " +
	 	"c.ADDRESS1,c.ADDRESS2,c.ADDRESS3,c.ADDRESS4,c.IS_REGD_EXCISE_Customer,c.CITY,c.PINCODE,c.Attachments,c.LANDLINE_NO," +
	 	"c.MOBILE_NO,c.FAX_NO,c.EMAIL_ID,c.IS_REGD_EXCISE_VENDOR,c.LST_NO,c.TIN_NO,c.CST_NO,c.PAN_No,c.Service_Tax_Registration_No," +
	 	"c.ECC_No,c.Excise_Reg_No,c.Excise_Range,c.Excise_Division,c.DLNO1,c.DLNO2, SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
	 	"SAP_CREATED_BY,REQUESTED_BY from CUSTOMER_MASTER_M as c  where REQUEST_NO='"+requstNo1+"'";
	 		
	 		
	 		
			ResultSet rsEditRecord=ad.selectQuery(editRecord);
			try {
				if(rsEditRecord.next())
				{
					
					custForm.setRequestNo(requstNo1);
					custForm.setRequestNumber(requstNo1);
					String reqDate=rsEditRecord.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					custForm.setRequestDate(reqDate);
					if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IN"))
					{
						custForm.setAccGroupId("Domestic");
					}
					if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IM"))
					{
						custForm.setAccGroupId("Export Customer");
					}
					if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("FS"))
					{
						custForm.setAccGroupId("Field Staff");
					}
					if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("7"))
					{
						custForm.setAccGroupId("Plants");
					}
					
					custForm.setSales("");
					custForm.setAccounts("");
					String viewType=rsEditRecord.getString("VIEW_TYPE");
					if(viewType.equalsIgnoreCase("S"))
					{
						custForm.setSales("Sales");
					}
					if(viewType.equalsIgnoreCase("A"))
					{
						custForm.setAccounts("Accounts");
					}
					if(viewType.equalsIgnoreCase("3"))
					{
						custForm.setSales("Sales");
						custForm.setAccounts("Accounts");
					}
				
					
					custForm.setDomestic("");
					custForm.setExports("");
					
				    custForm.setCustomerType(rsEditRecord.getString("Customer_Type"));
					custForm.setCustomerName(rsEditRecord.getString("NAME"));
					custForm.setAddress1(rsEditRecord.getString("ADDRESS1"));
					custForm.setAddress2(rsEditRecord.getString("ADDRESS2"));
					custForm.setAddress3(rsEditRecord.getString("ADDRESS3"));
					custForm.setAddress4(rsEditRecord.getString("ADDRESS4"));
					custForm.setCity(rsEditRecord.getString("CITY"));
					custForm.setPincode(rsEditRecord.getString("PINCODE"));
					
				
					custForm.setLandlineNo(rsEditRecord.getString("LANDLINE_NO"));
					custForm.setMobileNo(rsEditRecord.getString("MOBILE_NO"));
					custForm.setFaxNo(rsEditRecord.getString("FAX_NO"));
					custForm.setEmailId(rsEditRecord.getString("EMAIL_ID"));
					
					String tdsStatus=rsEditRecord.getString("IS_REGD_EXCISE_Customer");
					if(tdsStatus.equalsIgnoreCase("1"))
					{
						custForm.setTdsStatus("YES");
						request.setAttribute("setTdsState", "setTdsState");
					}
					if(tdsStatus.equalsIgnoreCase("0"))
						custForm.setTdsStatus("No");
						
				
					custForm.setListNumber(rsEditRecord.getString("LST_NO"));
					custForm.setTinNumber(rsEditRecord.getString("TIN_NO"));
					custForm.setCstNumber(rsEditRecord.getString("CST_NO"));
					custForm.setPanNumber(rsEditRecord.getString("PAN_No"));
					custForm.setServiceTaxNo(rsEditRecord.getString("Service_Tax_Registration_No"));
					String isRegdExciseVender=rsEditRecord.getString("IS_REGD_EXCISE_VENDOR");
					if(isRegdExciseVender.equalsIgnoreCase("1"))
					{
						custForm.setIsRegdExciseVender("Yes");
						request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
					}
					if(isRegdExciseVender.equalsIgnoreCase("0"))
					{
						custForm.setIsRegdExciseVender("No");
						request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
					}
					custForm.setEccNo(rsEditRecord.getString("ECC_No"));
					custForm.setExciseRegNo(rsEditRecord.getString("Excise_Reg_No"));
					custForm.setExciseRange(rsEditRecord.getString("Excise_Range"));
					custForm.setExciseDivision(rsEditRecord.getString("Excise_Division"));
					custForm.setDlno1(rsEditRecord.getString("DLNO1"));
					custForm.setDlno2(rsEditRecord.getString("DLNO2"));
				
				
					custForm.setCutomerCode(rsEditRecord.getString("cutomer_code"));
					
					 String sapCodeno=rsEditRecord.getString("SAP_CODE_NO");  
						if(sapCodeno!=null){ 
							custForm.setSapCodeNo(sapCodeno);		
					String sapCodeExist=rsEditRecord.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
					{
						custForm.setSapCodeExists("Yes");
					}
					if(sapCodeExist.equalsIgnoreCase("0"))
						custForm.setSapCodeExists("No");
					String sapCreationDate=rsEditRecord.getString("SAP_CREATION_DATE");
					String sapDate[]=sapCreationDate.split(" ");
					sapCreationDate=sapDate[0];
					String sapCode[]=sapCreationDate.split("-");
					sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
					custForm.setSapCreationDate(sapCreationDate);
					custForm.setSapCreatedBy(rsEditRecord.getString("SAP_CREATED_BY"));
					custForm.setRequestedBy(rsEditRecord.getString("REQUESTED_BY"));
						}
					
					
					
					ArrayList fileList = new ArrayList();
					String uploadedFiles=rsEditRecord.getString("Attachments");
					if(uploadedFiles.equalsIgnoreCase(""))
					{
						
					}else{
					String v[] = uploadedFiles.split(",");
					int l = v.length;
						for (int i = 0; i < l; i++) 
						{
							CustomerMasterForm custForm2=new CustomerMasterForm();
						int x=v[i].lastIndexOf("/");
						uploadedFiles=v[i].substring(x+1);		
						custForm2.setFileList(uploadedFiles);
						custForm2.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/");
						fileList.add(custForm2);
						}
					request.setAttribute("listName", fileList);
					}
					
					
					
						//currency
						String editRecord2="select cur.ISOCD from CUSTOMER_MASTER_M as c,Currency as cur where REQUEST_NO='"+requstNo1+"'" +
								" and cur.WAERS=c.CURRENCY_ID";
					 		
					 	
							ResultSet rsEditRecord2=ad.selectQuery(editRecord2);
							if(rsEditRecord2.next())
							{
								custForm.setCurrencyId(rsEditRecord2.getString("ISOCD"));
							}
							
							//payment term
							
							String editRecord3="select pay.PAYMENT_TERM_NAME from CUSTOMER_MASTER_M as c,PAYMENT_TERM_M as pay " +
									"where REQUEST_NO='"+requstNo1+"' and pay.PAYMENT_TERM_ID=c.PAYMENT_TERM_ID";
						 		
						 		
						 		ResultSet rsEditRecord3=ad.selectQuery(editRecord3);
								if(rsEditRecord3.next())
								{
									custForm.setPaymentTermID(rsEditRecord3.getString("PAYMENT_TERM_NAME"));
								}
								
								//acc clerk
								
								String editRecord4="select acccl.ACC_CLERK_DESC from CUSTOMER_MASTER_M as c,ACC_CLERK_M as acccl " +
										"where REQUEST_NO='"+requstNo1+"' and acccl.ACC_CLERK_ID=c.ACCOUNT_CLERK_ID";
							 		
							 		
							 		
									ResultSet rsEditRecord4=ad.selectQuery(editRecord4);
									if(rsEditRecord4.next())
									{
										custForm.setAccountClerkId(rsEditRecord4.getString("ACC_CLERK_DESC"));
									}
									
									//tds code
									
									String editRecord5="select tds.TDS_SECTION_DESC from CUSTOMER_MASTER_M as c,TDS_SECTION_M as tds" +
											" where REQUEST_NO='"+requstNo1+"' and tds.TDS_SECTION_ID=c.TDS_CODE";
								 		
								 		
								 		
										ResultSet rsEditRecord5=ad.selectQuery(editRecord5);
										if(rsEditRecord5.next())
										{
											custForm.setTdsCode(rsEditRecord5.getString("TDS_SECTION_DESC"));
											
										}
										
										//country
										String editRecord6="select cou.LANDX from CUSTOMER_MASTER_M as c ,Country as cou " +
												"where REQUEST_NO='"+requstNo1+"'and cou.LAND1=c.COUNTRY_ID ";
							 		
							 		
									ResultSet rsEditRecord6=ad.selectQuery(editRecord6);
									if(rsEditRecord6.next())
									{
										custForm.setCountryId(rsEditRecord6.getString("LANDX"));
										
									}
									
									//state
									String editRecord7=" select sta.BEZEI  from CUSTOMER_MASTER_M as c,State as sta " +
											"where REQUEST_NO='"+requstNo1+"' and sta.BLAND=c.STATE";
						 		
						 								 		
								ResultSet rsEditRecord7=ad.selectQuery(editRecord7);
								if(rsEditRecord7.next())
								{
									custForm.setState(rsEditRecord7.getString("BEZEI"));
									
								}
								
								//customer grp
								String editRecord8="select cust.C_GROUP_NAME from CUSTOMER_MASTER_M as c,Customer_Group as cust " +
										"where REQUEST_NO='"+requstNo1+"' and cust.C_GROUP_ID=c.Customer_Group";
					 		
					 							 		
							ResultSet rsEditRecord8=ad.selectQuery(editRecord8);
							if(rsEditRecord8.next())
							{
								custForm.setCustomerGroup(rsEditRecord8.getString("C_GROUP_NAME"));
								
							}
							
							//price grp
							String editRecord9="select pri.P_GROUP_NAME from CUSTOMER_MASTER_M as c,PRICE_GROUP as pri " +
									"where REQUEST_NO='"+requstNo1+"'and pri.P_GROUP_ID=c.Price_Group";
				 		
				 						 		
						ResultSet rsEditRecord9=ad.selectQuery(editRecord9);
						if(rsEditRecord9.next())
						{
							custForm.setPriceGroup(rsEditRecord9.getString("P_GROUP_NAME"));
							
						}
						//price list
						String editRecord10="select prili.P_LIST_NAME from CUSTOMER_MASTER_M as c,PRICE_LIST as  prili  " +
								"where REQUEST_NO='"+requstNo1+"'and  prili.P_List_ID=c.Price_List";
					
					
					ResultSet rsEditRecord10=ad.selectQuery(editRecord10);
					if(rsEditRecord10.next())
					{
						custForm.setPriceList(rsEditRecord10.getString("P_LIST_NAME"));
						
					}
					
					//tax type
					String editRecord11="select tax.T_CLASS_NAME from CUSTOMER_MASTER_M as c,TAX_CLASS as tax" +
							" where REQUEST_NO='"+requstNo1+"'and  tax.T_CLASS_ID=c.Tax_Type";
				
				
				ResultSet rsEditRecord11=ad.selectQuery(editRecord11);
				if(rsEditRecord11.next())
				{
					custForm.setTaxType(rsEditRecord11.getString("T_CLASS_NAME"));
					
				}												
																
												
					
					CustDetails.add(custForm);
					request.setAttribute("CustomerMasterView", CustDetails);
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String matGroup="";
			String Customer_Type="";
			
			String getMatGroup="select Customer_Type from CUSTOMER_MASTER_M where REQUEST_NO='"+requstNo1+"' ";
		 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
		 	try {
				while(rsMatGrup.next()){
					Customer_Type=rsMatGrup.getString("Customer_Type");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	if(Customer_Type.equalsIgnoreCase("Exports"))
		 	{
		 		Customer_Type="Export";
		 	}   
		 	
			
			int checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='' AND  mast.Material_Type='Customer Master' AND Material_Group='"+Customer_Type+"' and mast.Approver_Id=emp.PERNR order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String approveStatus="";
					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date," +
					"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo1+"' and " +
					"mast.Location='' AND all_r.type='Customer Master' AND Material_Group='"+Customer_Type+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver " +
					"and (mast.Role='User' or mast.Role='Accounts' or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority," +
					"mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase(""))
						{
							apprvers.setApproveStatus("In Process");
						}
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
						
						
					}
					if(approveStatus.equalsIgnoreCase(""))
					{
						apprvers.setApproveStatus("In Process");
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					listApprers.add(apprvers);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("approverDetails", listApprers);
			forwardType="CustomerMasterView";
		}
		
		
		if(matType.equalsIgnoreCase("RM"))
		{

			LinkedList rawDetails=new LinkedList();
			ApprovalsForm pendAppForm=new ApprovalsForm();
			String getMaterial="select  mat.Storage,mat.REQUEST_NO,mat.REQUEST_DATE,loc.LOCNAME,mat.MATERIAL_GROUP_ID,stLoc.STORAGE_LOCATION_NAME," +
			" mat.MATERIAL_SHORT_NAME,mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, mat.PHARMACOP_NAME," +
			"mat.PHARMACOP_GRADE,mat.GENERIC_NAME,mat.SYNONYM,mat.PHARMACOP_SPECIFICATION,mat.DUTY_ELEMENT, mat.IS_DMF_MATERIAL," +
			"dmf.DMF_GRADE_DESC,mat.MATERIAL_GRADE,mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST, mat.IS_VENDOR_SPECIFIC_MATERIAL," +
			"mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX,mat.CUSTOMER_NAME, mat.TO_BE_USED_IN_PRODUCTS,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE," +
			"mat.RETEST_DAYS ,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC,mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS, " +
			"SAP_CREATION_DATE,SAP_CREATED_BY,rejected_flag from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP " +
			"as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,DMF_GRADE as dmf,Country as cou, VALUATION_CLASS as val " +
			" where REQUEST_NO='"+reqId+"' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and " +
			"matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID " +
			"and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID and val.VALUATION_ID=mat.VALUATION_CLASS";	
			ResultSet rs=ad.selectQuery(getMaterial);
			try {
				while(rs.next())
				{
				
					pendAppForm.setRequestNo(reqId);
					pendAppForm.setRequestNumber(reqId);
					pendAppForm.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
				String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				pendAppForm.setRequestDate(reqDate);
				pendAppForm.setLocationId(rs.getString("LOCNAME"));
				pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
				pendAppForm.setStorage(rs.getString("Storage"));
				pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
				pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				pendAppForm.setMaterialGroupId(rs.getString("STXT"));
				pendAppForm.setPharmacopName(rs.getString("PHARMACOP_NAME"));
				String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
				pendAppForm.setPharmacopGrade(pharmacopGrade);
				
				pendAppForm.setGenericName(rs.getString("GENERIC_NAME"));
				pendAppForm.setSynonym(rs.getString("SYNONYM"));
				pendAppForm.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
				String isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
				if(isDMfMaterial.equalsIgnoreCase("1"))
				{
					pendAppForm.setIsDMFMaterial("Yes");
				
					pendAppForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
					pendAppForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
					pendAppForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					
				}
				pendAppForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
				if(isDMfMaterial.equalsIgnoreCase("0"))
				{
					pendAppForm.setIsDMFMaterial("NO");
					pendAppForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
					pendAppForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
					pendAppForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					
					
				}
				
				pendAppForm.setCountryId(rs.getString("LANDX"));
				pendAppForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				pendAppForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
				
				String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
				if(isVendorStatus.equalsIgnoreCase("1"))
				{
					pendAppForm.setIsVendorSpecificMaterial("YES");
					pendAppForm.setMfgrName(rs.getString("MFGR_NAME"));
					pendAppForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				}
				if(isVendorStatus.equalsIgnoreCase("0"))
				{
					pendAppForm.setIsVendorSpecificMaterial("NO");
				}
				
				
				pendAppForm.setShelfLife(rs.getString("SHELF_LIFE"));
				String dutyElement=rs.getString("DUTY_ELEMENT");
				if(dutyElement.equalsIgnoreCase("0"))
				{
					pendAppForm.setDutyElement("0-Duty Exempted Raw Materials for Finished product");
				}
				if(dutyElement.equalsIgnoreCase("2")){
					pendAppForm.setDutyElement("2-Indigenous Material with or without Cenvat");
				}
				pendAppForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
				pendAppForm.setRetestDays(rs.getString("RETEST_DAYS"));
				pendAppForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
				pendAppForm.setValuationClass(rs.getString("VALUATION_DESC"));
				pendAppForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				pendAppForm.setUnitOfMeasId(rs.getString("LTXT"));
				pendAppForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));


				
				ArrayList fileList = new ArrayList();
				String uploadedFiles=rs.getString("Attachements");
				if(uploadedFiles.equalsIgnoreCase(""))
				{
					
				}else{
				String v[] = uploadedFiles.split(",");
				int l = v.length;
				for (int i = 0; i < l; i++) 
				{
					RawMaterialForm materialForm2=new RawMaterialForm();
					//String url=v[i];
					//materialForm2.setUrl(url);
				System.out.println(v[i]);
					materialForm2.setUploadFilePath(v[i]);
				int x=v[i].lastIndexOf("/");
				uploadedFiles=v[i].substring(x+1);		
				materialForm2.setFileList(uploadedFiles);
				
				fileList.add(materialForm2);
				}
				request.setAttribute("listName", fileList);
				
				String sapCodeno=rs.getString("SAP_CODE_NO");
				if(sapCodeno!=null)
				{
					pendAppForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
					String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
					{
						pendAppForm.setSapCodeExists("Yes");
					}
					if(sapCodeExist.equalsIgnoreCase("0"))
						pendAppForm.setSapCodeExists("No");
					String createDate=rs.getString("SAP_CREATION_DATE");
					String a1[]=createDate.split(" ");
					createDate=a1[0];
					String b1[]=createDate.split("-");
					createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					pendAppForm.setSapCreationDate(createDate);
					pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				}
				
				
}
				String rejected_flag=rs.getString("rejected_flag");
				if(rejected_flag!=null)
				{
					if(rejected_flag.equalsIgnoreCase("y"))
						request.setAttribute("rejectedFLag", "rejectedFLag");
				}

}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			String getMaterial1="select tem.TEMP_CON_DESC from material_code_request as mat,TEMP_CONDITION as tem " +
			" where REQUEST_NO='"+reqId+"' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
	
	ResultSet rs1=ad.selectQuery(getMaterial1);
	try {
		while(rs1.next())
		{
			pendAppForm.setTempCondition(rs1.getString("TEMP_CON_DESC"));
		}
	} catch (SQLException e) {
				e.printStackTrace();
	}
	
	//STORAGE_CONDITION
	
	String getMaterial2="select stcon.LTXT from material_code_request as mat,STORAGE_CONDITION as stcon" +
			" where REQUEST_NO='"+reqId+"'  and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
	
	ResultSet rs2=ad.selectQuery(getMaterial2);
	try {
		while(rs2.next())
		{
			pendAppForm.setStorageCondition(rs2.getString("LTXT"));
		}
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	rawDetails.add(pendAppForm);
	request.setAttribute("rawdetails", rawDetails);
	

	
	
	String location="";
	String matGroup="";
	String matDetails="select loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+reqId+"' and mat.LOCATION_ID=loc.LOCID";
	ResultSet rsDetails=ad.selectQuery(matDetails);
	try {
		if(rsDetails.next())
		{
			location=rsDetails.getString("LOCATION_CODE");
			matGroup=rsDetails.getString("MATERIAL_GROUP_ID");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	int  checkStatus=0;
	LinkedList listApprers=new LinkedList();
	String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
	"where mast.Location='"+location+"' AND  mast.Material_Type='RM' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
	ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
	try {
		while(rsApprDetails.next())
		{
			checkStatus=1;
			ApprovalsForm apprvers=new ApprovalsForm();
			apprvers.setPriority(rsApprDetails.getString("Priority"));
			apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
			String empCode=rsApprDetails.getString("Approver_Id");
			String actualapprover="";
			boolean data=false;
			
			String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
			"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
			"mast.Material_Type='RM' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
			"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
			"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
			ResultSet rsAppInfo=ad.selectQuery(recordStatus);
			if(rsAppInfo.next())
			{
				actualapprover=rsAppInfo.getString("Actual_Approver");
				if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
				{
					apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
					data=true;
				}
				if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
				{
					apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
					data=true;
				}
				if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
				{
					apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
					data=true;
				}
				apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
				String approveStatus=rsAppInfo.getString("Req_Status");
				if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
				{
				apprvers.setDate(rsAppInfo.getString("approved_date"));
				}else{
					apprvers.setDate(rsAppInfo.getString("rejected_date"));
				}
				String comments=rsAppInfo.getString("Comments");
				if(comments==null || comments.equalsIgnoreCase(""))
				{
					apprvers.setComments("");
				}else{
					apprvers.setComments(rsAppInfo.getString("Comments"));
				}
			}
			String pernr="";

			if(data==true)
							{
								pernr=actualapprover;
							}
							else
							{
								pernr=rsApprDetails.getString("Approver_Id");
							}						
					
					
				String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
				ResultSet rsname=ad.selectQuery(name);
				if(rsname.next())
				{
					apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
				}
			listApprers.add(apprvers);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(checkStatus==0)
	{
		getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
		"where mast.Location='"+location+"' AND  mast.Material_Type='RM' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
		rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				String actualapprover="";
				boolean data=false;
				
				String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
				"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
				"mast.Material_Type='RM' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
				"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
				"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				while(rsAppInfo.next())
				{
					actualapprover=rsAppInfo.getString("Actual_Approver");
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
						data=true;
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
					{
						String approveDate=	rsAppInfo.getString("approved_date");
						
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						apprvers.setDate(approveDate);
					}else{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						apprvers.setDate(rejectDate);
					}
					String comments=rsAppInfo.getString("Comments");
					if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				String pernr="";

				if(data==true)
								{
									pernr=actualapprover;
								}
								else
								{
									pernr=rsApprDetails.getString("Approver_Id");
								}						
						
						
					String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
					ResultSet rsname=ad.selectQuery(name);
					if(rsname.next())
					{
						apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
					}
				listApprers.add(apprvers);
}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	request.setAttribute("approverDetails", listApprers);
	forwardType="ROHDetails";
		}
		if(matType.equalsIgnoreCase("PM"))
		{
			LinkedList pacDetails=new LinkedList();

			
		ApprovalsForm materialForm=new ApprovalsForm();
			

			String matGroup="";
			 String location="";
		
			 String getMaterial="select mat.Storage,mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
				"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.DUTY_ELEMENT,pacgrp.PACKING_MATERIAL_GROUP_NAME," +
				"mat.Type_Of_Material,mat.ARTWORK_NO,mat.IS_ARTWORK_REVISION,mat.EXISTING_SAP_ITEM_CODE,mat.IS_DMF_MATERIAL,dmf.DMF_GRADE_DESC," +
				"mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST,mat.IS_VENDOR_SPECIFIC_MATERIAL,mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX," +
				"mat.CUSTOMER_NAME,mat.TO_BE_USED_IN_PRODUCTS,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC," +
				"mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc," +
				"MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,PACKAGE_MATERIAL_GROUP as pacgrp,DMF_GRADE as dmf," +
				"Country as cou,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' " +
				"and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID" +
				" and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID  and pacgrp.PACKING_MATERIAL_GROUP_ID=mat.PACKING_MATERIAL_GROUP " +
				" and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID  " +
				" and val.VALUATION_ID=mat.VALUATION_CLASS";
				
				
				
				ResultSet rs=ad.selectQuery(getMaterial);
				
					try {
						if(rs.next())
{
							materialForm.setRequestNo(reqId);
							materialForm.setRequestNumber(reqId);
							matType=rs.getString("Type");
							matGroup=rs.getString("MATERIAL_GROUP_ID");
							location=rs.getString("LOCATION_CODE");
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							materialForm.setRequestDate(reqDate);
							materialForm.setLocationId(rs.getString("LOCNAME"));
							materialForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
							materialForm.setStorage(rs.getString("Storage"));
							materialForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							materialForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							materialForm.setMaterialGroupId(rs.getString("STXT"));
							String isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
							if(isDMFMaterial.equalsIgnoreCase("1"))
							{
								materialForm.setIsDMFMaterial("YES");
								request.setAttribute("dmfMandatory", "dmfMandatory");
								materialForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
								materialForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
							}
							if(isDMFMaterial.equalsIgnoreCase("0")){
								materialForm.setIsDMFMaterial("NO");
								request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
							}
							materialForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
							
							materialForm.setCountryId(rs.getString("LANDX"));
							materialForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
							materialForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
							String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
							if(isVendorStatus.equalsIgnoreCase("1"))
							{
								materialForm.setIsVendorSpecificMaterial("YES");
								request.setAttribute("vedorMandatory", "vedorMandatory");
								materialForm.setMfgrName(rs.getString("MFGR_NAME"));
								materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
							}
							if(isVendorStatus.equalsIgnoreCase("0"))
							{
								materialForm.setIsVendorSpecificMaterial("NO");
								request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
							}
								materialForm.setMfgrName(rs.getString("MFGR_NAME"));
							materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
							
							materialForm.setRetestDays(rs.getString("RETEST_DAYS"));
							materialForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
							String dutyElement=rs.getString("DUTY_ELEMENT");
							if(dutyElement.equalsIgnoreCase("1"))
							{
								materialForm.setDutyElement("0-Indigenous Material with or without Cenvat");
							}
							if(dutyElement.equalsIgnoreCase("0"))
								materialForm.setDutyElement("1-Duty Exempted Packing Materials for Finished product");
							materialForm.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP_NAME"));
							materialForm.setTypeOfMaterial(rs.getString("Type_Of_Material"));
							String typeOfMaterial=rs.getString("Type_Of_Material");
							
							if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
							{
								request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
								materialForm.setArtworkNo(rs.getString("ARTWORK_NO"));
								String isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
								if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
								{
									materialForm.setIsArtworkRevision("YES");
								}
								if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
									materialForm.setIsArtworkRevision("NO");
								
							}
							else
								request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
							
							materialForm.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
							materialForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						
							
							ArrayList fileList = new ArrayList();
							String uploadedFiles=rs.getString("Attachements");
							materialForm.setUnitOfMeasId(rs.getString("LTXT"));
							materialForm.setValuationClass(rs.getString("VALUATION_DESC"));
							materialForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
							if(uploadedFiles.equalsIgnoreCase(""))
							{
							}else{
							String v[] = uploadedFiles.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) 
							{
								PackageMaterialMasterForm materialForm2=new PackageMaterialMasterForm();
							int x=v[i].lastIndexOf("/");
							uploadedFiles=v[i].substring(x+1);		
							materialForm2.setFileList(uploadedFiles);
							materialForm2.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/"+uploadedFiles+"");
							fileList.add(materialForm2);
							}
							request.setAttribute("listName", fileList);
							}
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null)
							{
								materialForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
								if(sapCodeExist.equalsIgnoreCase("1"))
								{
									materialForm.setSapCodeExists("Yes");
								}
								if(sapCodeExist.equalsIgnoreCase("0"))
									materialForm.setSapCodeExists("No");
								String createDate=rs.getString("SAP_CREATION_DATE");
								String a1[]=createDate.split(" ");
								createDate=a1[0];
								String b1[]=createDate.split("-");
								createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
								materialForm.setSapCreationDate(createDate);
								materialForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
							}
							
}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//temp cond
					
					String getMaterial1="select tem.TEMP_CON_DESC from material_code_request as mat,TEMP_CONDITION as tem " +
							" where REQUEST_NO='"+reqId+"' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
					
					ResultSet rs1=ad.selectQuery(getMaterial1);
					try {
						if(rs1.next())
						{
							materialForm.setTempCondition(rs1.getString("TEMP_CON_DESC"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//STORAGE_CONDITION
					
					String getMaterial2="select stcon.LTXT from material_code_request as mat,STORAGE_CONDITION as stcon" +
							" where REQUEST_NO='"+reqId+"'  and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
					
					ResultSet rs2=ad.selectQuery(getMaterial2);
					try {
						if(rs2.next())
						{
							materialForm.setStorageCondition(rs2.getString("LTXT"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pacDetails.add(materialForm);
				
			
		
		request.setAttribute("pacdetails", pacDetails);
		
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
		"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				String actualapprover="";
				boolean data=false;
				
				String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
				"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
				"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
				"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
				"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					actualapprover=rsAppInfo.getString("Actual_Approver");
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
						data=true;
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
					{
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments=rsAppInfo.getString("Comments");
					if(comments==null || comments.equalsIgnoreCase(""))
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				String pernr="";

				if(data==true)
								{
									pernr=actualapprover;
								}
								else
								{
									pernr=rsApprDetails.getString("Approver_Id");
								}						
						
						
					String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
					ResultSet rsname=ad.selectQuery(name);
					if(rsname.next())
					{
						apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
					}
				listApprers.add(apprvers);
				request.setAttribute("approverDetails", listApprers);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(checkStatus==0)
		{

			getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
			"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
			rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						actualapprover=rsAppInfo.getString("Actual_Approver");
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(approveDate);
						}else{
							String rejectDate=	rsAppInfo.getString("rejected_date");
							
							String a[]=rejectDate.split(" ");
							rejectDate=a[0];
							String b[]=rejectDate.split("-");
							rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(rejectDate);
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					String pernr="";

					if(data==true)
									{
										pernr=actualapprover;
									}
									else
									{
										pernr=rsApprDetails.getString("Approver_Id");
									}						
							
							
						String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
						ResultSet rsname=ad.selectQuery(name);
						if(rsname.next())
						{
							apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
						}
					listApprers.add(apprvers);
					
					
}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		request.setAttribute("approverDetails", listApprers);
		forwardType="pacmaterial";
	}
		
		if(matType.equalsIgnoreCase("BULK"))
		{

	 		LinkedList semfDetails=new LinkedList();

	 		ApprovalsForm masterForm=new ApprovalsForm();
	 		
	 	
	           
	        String matGroup="";
	        String location="";
	 		String getSemiFinished="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
	 				"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pac.PACK_SIZE_DESC,mat.CUSTOMER_NAME,mat.SHELF_LIFE," +
	 				"mat.SHELF_LIFE_TYPE,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ,mat.TARGET_WEIGHT," +
	 				"wei.W_UOM_DESC,val.VALUATION_DESC,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as " +
	 				"stLoc,MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PACK_SIZE as pac,WEIGHT_UOM as wei,VALUATION_CLASS" +
	 				" as val where REQUEST_NO='"+reqId+"' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID " +
	 				"and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID	" +
	 				"and pac.PACK_SIZE_CODE=mat.PACK_SIZE and wei.W_UOM_CODE=mat.WEIGHT_UOM " +
	 				"and val.VALUATION_ID=mat.VALUATION_CLASS";
			ResultSet rs=ad.selectQuery(getSemiFinished);
			try {
				if(rs.next())
				{
					masterForm.setRequestNo(reqId);
					masterForm.setRequestNumber(reqId);
					matType=rs.getString("Type");
					matGroup=rs.getString("MATERIAL_GROUP_ID");
					location=rs.getString("LOCATION_CODE");
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					masterForm.setRequestDate(reqDate);
					
					masterForm.setLocationId(rs.getString("LOCNAME"));
					
					masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
					masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					masterForm.setMaterialGroupId(rs.getString("STXT"));
					
					masterForm.setUnitOfMeasId(rs.getString("LTXT"));
					masterForm.setPackSize(rs.getString("PACK_SIZE_DESC"));
				
					masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
					masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
					masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
					masterForm.setBatchCode(rs.getString("BATCH_CODE"));
					masterForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
					
					
					masterForm.setWeightUOM(rs.getString("W_UOM_DESC"));
					masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
					masterForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
					masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
					
					

					
					
					
					request.setAttribute("semidetails", semfDetails);
					
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							masterForm.setSapCodeExists("Yes");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							masterForm.setSapCodeExists("No");
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						masterForm.setSapCreationDate(createDate);
						masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
					
					
					
					
					
					
					forwardType="semimaterial";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//country
			String editRecord1="select cou.LANDX from material_code_request as m ,Country as cou " +
					"where REQUEST_NO='"+reqId+"'and cou.LAND1=m.COUNTRY_ID ";
 		
 		
		ResultSet rs1=ad.selectQuery(editRecord1);
		try {
			if(rs1.next())
			{
				masterForm.setCountryId(rs1.getString("LANDX"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		semfDetails.add(masterForm);
		request.setAttribute("semfdetails", semfDetails);
			
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
		"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				String actualapprover="";
				boolean data=false;
				
				String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
				"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
				"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
				"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
				"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					actualapprover=rsAppInfo.getString("Actual_Approver");
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
						data=true;
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
					{
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments=rsAppInfo.getString("Comments");
					if(comments==null || comments.equalsIgnoreCase(""))
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				String pernr="";

				if(data==true)
								{
									pernr=actualapprover;
								}
								else
								{
									pernr=rsApprDetails.getString("Approver_Id");
								}						
						
						
					String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
					ResultSet rsname=ad.selectQuery(name);
					if(rsname.next())
					{
						apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
					}
				listApprers.add(apprvers);
				request.setAttribute("approverDetails", listApprers);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(checkStatus==0)
		{

			getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
			"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
			rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						actualapprover=rsAppInfo.getString("Actual_Approver");
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(approveDate);
						}else{
							String rejectDate=	rsAppInfo.getString("rejected_date");
							
							String a[]=rejectDate.split(" ");
							rejectDate=a[0];
							String b[]=rejectDate.split("-");
							rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(rejectDate);
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					String pernr="";

					if(data==true)
									{
										pernr=actualapprover;
									}
									else
									{
										pernr=rsApprDetails.getString("Approver_Id");
									}						
							
							
						String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
						ResultSet rsname=ad.selectQuery(name);
						if(rsname.next())
						{
							apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
						}
					listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
			
			
	     
			
			
			
		}
		if(matType.equalsIgnoreCase("FG")||matType.equalsIgnoreCase("HAWA"))
		{
	 		LinkedList finDetails=new LinkedList();

	 	
	 		String matGroup="";
	 		 String location="";
	 		ApprovalsForm masterForm=new ApprovalsForm();
	 		
	 	
			
			
	 		String getFinishedProduct="select mat.Storage,mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,mat.MANUFACTURED_AT," +
	 			 	  "mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,mat.DOMESTIC_OR_EXPORTS,cou.LANDX,mat.CUSTOMER_NAME," +
	 			 	  "mat.SALEABLE_OR_SAMPLE,mat.SALES_PACK_ID,pac.P_TYPE_DESC,sal.S_UOM_DESC,div.DIV_DESC,the.THER_SEG_DESC,bran.BRAND_DESC, " +
	 			 	  "st.STRENGTH_DESC,gen.GEN_NAME_DESC,mat.GROSS_WEIGHT,mat.NET_WEIGHT,mat.DIMENSION," +
	 			 	  "mat.Material_Pricing,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ," +
	 			 	  "SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,MATERIAL_GROUP as matGroup ," +
	 			 	  "UNIT_MESUREMENT AS uom,Country as cou,PACK_TYPE as pac,SALES_UOM as sal,DIVISION as div,THERAPEUTIC_SEGMENT as the,BRAND as bran," +
	 			 	  "STRENGTH as st,GENERIC_NAME as gen " +
	 			 	  "where REQUEST_NO='"+reqId+"' and loc.LOCID=mat.LOCATION_ID  and " +
	 			 	  "cou.LAND1=mat.COUNTRY_ID and pac.P_TYPE_CODE=mat.PACK_TYPE_ID and sal.S_UOM_CODE=mat.SALES_UNIT_OF_MEAS_ID and " +
	 			 	  "uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID " +
	 			 	  "and the.THER_SEG_CODE=mat.THERAPEUTIC_SEGMENT_ID and bran.BRAND_CODE=mat.BRAND_ID and st.STRENGTH_CODE=mat.STRENGTH_ID" +
	 			 	  " and gen.GEN_NAME_CODE=mat.GENERIC_NAME";
	 		ResultSet rs=ad.selectQuery(getFinishedProduct);
			try {
				if(rs.next())
				{
					masterForm.setRequestNo(reqId);
					masterForm.setRequestNumber(reqId);
					matType=rs.getString("Type");
					masterForm.setLocationId(rs.getString("LOCNAME"));
					matGroup=rs.getString("MATERIAL_GROUP_ID");
					location=rs.getString("LOCATION_CODE");
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					masterForm.setRequestDate(reqDate);
					
					
					
					String manufacturedAt=rs.getString("MANUFACTURED_AT");
					
					if(manufacturedAt.equalsIgnoreCase("Third Party"))
					{
						request.setAttribute("standardBathcNotMandatory", "standardBathcNotMandatory");
						request.setAttribute("manufactureMandatory", "manufactureMandatory");
					}
					else{
						request.setAttribute("standardBathcMandatory", "standardBathcMandatory");
						request.setAttribute("manufactureNotMandatory", "manufactureNotMandatory");
					}
					
					masterForm.setManufacturedAt(manufacturedAt);
					
					masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					masterForm.setMaterialGroupId(rs.getString("STXT"));
					masterForm.setStorage(rs.getString("Storage"));
					masterForm.setCountryId(rs.getString("LANDX"));
					masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
					masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
					masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
					masterForm.setBatchCode(rs.getString("BATCH_CODE"));
					String salorsam=rs.getString("SALEABLE_OR_SAMPLE");
					if(salorsam.equalsIgnoreCase("1")){
						masterForm.setSaleableOrSample("Saleable");
						}
						if(salorsam.equalsIgnoreCase("2")){
							masterForm.setSaleableOrSample("Sample");
							}
					String domorexp=rs.getString("DOMESTIC_OR_EXPORTS");
					if(domorexp.equalsIgnoreCase("D")){
					masterForm.setDomesticOrExports("DOMESTIC");
					}
					if(domorexp.equalsIgnoreCase("E")){
						masterForm.setDomesticOrExports("EXPORTS");
						}
					if(domorexp.equalsIgnoreCase("V")){
						masterForm.setDomesticOrExports("Validation");
						}
					String salesPackId=rs.getString("SALES_PACK_ID");
					masterForm.setSalesPackId(rs.getString("SALES_PACK_ID"));
					masterForm.setPackTypeId(rs.getString("P_TYPE_DESC"));
					masterForm.setSalesUnitOfMeaseurement(rs.getString("S_UOM_DESC"));
					masterForm.setDivisionId(rs.getString("DIV_DESC"));
					masterForm.setTherapeuticSegmentID(rs.getString("THER_SEG_DESC"));
					masterForm.setBrandID(rs.getString("BRAND_DESC"));
					masterForm.setSrengthId(rs.getString("STRENGTH_DESC"));
					masterForm.setGenericName(rs.getString("GEN_NAME_DESC"));
					
					
					String matpri=rs.getString("Material_Pricing");
					if(matpri.equalsIgnoreCase("1")){
						masterForm.setMaterialPricing("Normal");
						}
					if(matpri.equalsIgnoreCase("2")){
						masterForm.setMaterialPricing("Spare parts");
						}
					if(matpri.equalsIgnoreCase("11")){
						masterForm.setMaterialPricing("Scheduled(Controled)");
						}
					if(matpri.equalsIgnoreCase("12")){
						masterForm.setMaterialPricing("Un-Scheduled(De-Con)");
						}
					if(matpri.equalsIgnoreCase("13")){
						masterForm.setMaterialPricing("PS / Promo (Micro)");
						}
					if(matpri.equalsIgnoreCase("14")){
						masterForm.setMaterialPricing("No MRP ED Extra(Mic)");
						}
					if(matpri.equalsIgnoreCase("15")){
						masterForm.setMaterialPricing("No MRP ED Incl (Mic)");
						}
					if(matpri.equalsIgnoreCase("16")){
						masterForm.setMaterialPricing("Scrap IT Extra (Mic)");
						}
					if(matpri.equalsIgnoreCase("17")){
						masterForm.setMaterialPricing("MRP(Con)-ED Exe(Mic)");
						}
					if(matpri.equalsIgnoreCase("18")){
						masterForm.setMaterialPricing("MRP(DeC)-ED Exe(Mic)");
						}
					if(matpri.equalsIgnoreCase("19")){
						masterForm.setMaterialPricing("No MRP-ED Exe(Mic)");
						}
					if(matpri.equalsIgnoreCase("20")){
						masterForm.setMaterialPricing("Food Prod(MRP)-Micro)");
						}
					if(matpri.equalsIgnoreCase("21")){
						masterForm.setMaterialPricing("Vet Prod (MRP)-Micr");
						}
					if(matpri.equalsIgnoreCase("22")){
						masterForm.setMaterialPricing("Generic Price Grp-Mi");
						}
					if(matpri.equalsIgnoreCase("23")){
						masterForm.setMaterialPricing("MRP(Vet)-ED Exe(Mic)");
						}
					if(matpri.equalsIgnoreCase("24")){
						masterForm.setMaterialPricing("P-to-P ED Extra(Mic)");
						}
					if(matpri.equalsIgnoreCase("25")){
						masterForm.setMaterialPricing("Cosm Prod(MRP)-Micro");
						}
					if(matpri.equalsIgnoreCase("26")){
						masterForm.setMaterialPricing("Scrap - ED Exe(Mic))");
						}
					if(matpri.equalsIgnoreCase("27")){
						masterForm.setMaterialPricing("Import Items PG");
						}
				
					masterForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
					masterForm.setNetWeight(rs.getString("NET_WEIGHT"));
					
					masterForm.setDimension(rs.getString("DIMENSION"));
					masterForm.setUnitOfMeasId(rs.getString("LTXT"));
				
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							masterForm.setSapCodeExists("Yes");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							masterForm.setSapCodeExists("No");
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						masterForm.setSapCreationDate(createDate);
						masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
					
								
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			masterForm.setStorageLocationId("");
			masterForm.setWeightUOM("");
			masterForm.setValuationClass("");
			try {
				
			//storage location	
			String getStorageLoc="select s.STORAGE_LOCATION_ID,s.STORAGE_LOCATION_NAME from material_code_request m,STORAGE_LOCATION s where "
					+ "REQUEST_NO='"+reqId+"' and m.STORAGE_LOCATION_ID=s.STORAGE_LOCATION_ID";
			ResultSet rsStLoc=ad.selectQuery(getStorageLoc);
			if(rsStLoc.next()){
				masterForm.setStorageLocationId(rsStLoc.getString("STORAGE_LOCATION_ID")+"-"+rsStLoc.getString("STORAGE_LOCATION_NAME"));
			}
			//weight wom
			String getweight="select w.W_UOM_CODE,w.W_UOM_DESC from material_code_request m,WEIGHT_UOM w where REQUEST_NO='"+reqId+"' "
					+ "and m.WEIGHT_UOM=w.W_UOM_CODE";
			ResultSet rsWt=ad.selectQuery(getweight);
			if(rsWt.next()){
				masterForm.setWeightUOM(rsWt.getString("W_UOM_CODE")+"-"+rsWt.getString("W_UOM_DESC"));
			}
			String getValuation="select v.VALUATION_DESC from material_code_request m,VALUATION_CLASS v where REQUEST_NO='"+reqId+"' "
					+ "and m.VALUATION_CLASS=v.VALUATION_ID";
			ResultSet rsValu=ad.selectQuery(getValuation);
			if(rsValu.next()){
				masterForm.setValuationClass(rsValu.getString("VALUATION_DESC"));
			}
			//purchasegrp
			String getFinishedProduct1="select pur.PURCHASE_GROUP_DESC from material_code_request as mat,PURCHASE_GROUP as pur" +
			" where REQUEST_NO='"+reqId+"' and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID";
			ResultSet rs1=ad.selectQuery(getFinishedProduct1);
			if(rs1.next())
			{
				masterForm.setPuchaseGroupId(rs1.getString("PURCHASE_GROUP_DESC"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	
		//tax type
		String editRecord11="select tax.T_CLASS_NAME from material_code_request as m,TAX_CLASS as tax" +
				" where REQUEST_NO='"+reqId+"'and  tax.T_CLASS_ID=m.Tax_Classification";
		
		
		ResultSet rsEditRecord11=ad.selectQuery(editRecord11);
	try {
		if(rsEditRecord11.next())
		{
			masterForm.setTaxClassification(rsEditRecord11.getString("T_CLASS_NAME"));
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     

	
	if(matType.equals("FG")||matType.equals("HAWA")){
		String matDetails="select loc.LOCATION_CODE,mat.Type,mat.CREATED_BY,mat.DOMESTIC_OR_EXPORTS from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+reqId+"' and mat.LOCATION_ID=loc.LOCID";
		ResultSet rsDetails=ad.selectQuery(matDetails);
		try{
			while(rsDetails.next())
		
		{
			
			matGroup=rsDetails.getString("DOMESTIC_OR_EXPORTS");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(matGroup.equals("D"))
			matGroup="Domestic";
		if(matGroup.equals("E"))
 			matGroup="Export";
 		if(matGroup.equals("V"))
 			matGroup="V";
	}
			
			finDetails.add(masterForm);
			request.setAttribute("findetails", finDetails);
			forwardType="finmaterial";
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
			"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						actualapprover=rsAppInfo.getString("Actual_Approver");
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments==null || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					String pernr="";

					if(data==true)
									{
										pernr=actualapprover;
									}
									else
									{
										pernr=rsApprDetails.getString("Approver_Id");
									}						
							
							
						String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
						ResultSet rsname=ad.selectQuery(name);
						if(rsname.next())
						{
							apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
						}
					listApprers.add(apprvers);
					request.setAttribute("approverDetails", listApprers);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(checkStatus==0)
			{

				getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
				rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;
						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
								String approveDate=	rsAppInfo.getString("approved_date");
								
								String a[]=approveDate.split(" ");
								approveDate=a[0];
								String b[]=approveDate.split("-");
								approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(approveDate);
							}else{
								String rejectDate=	rsAppInfo.getString("rejected_date");
								
								String a[]=rejectDate.split(" ");
								rejectDate=a[0];
								String b[]=rejectDate.split("-");
								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(rejectDate);
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
     
			forwardType="finmaterial";
     }
		
			if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("LC")
					||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
		{
		 		LinkedList genDetails=new LinkedList();

		 		ApprovalsForm masterForm=new ApprovalsForm();
		 		
		 	
		 		
		 		
		 		String matGroup="";
		 		 String location="";
				String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME," +
						"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.is_asset,dep.DPTSTXT" +
						",mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from " +
						"material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , " +
						"UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+reqId+"' and	" +
						"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
						"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='"+matType+"' and val.VALUATION_ID=mat.VALUATION_CLASS" +
						" and dep.DPTID=mat.UTILIZING_DEPT";
				
				ResultSet rs=ad.selectQuery(getFinishedProduct);
				try {
					if(rs.next())
					{
						masterForm.setRequestNo(reqId);
						masterForm.setRequestNumber(reqId);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");

						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
					
	     					masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
						masterForm.setUtilizingDept(rs.getString("DPTSTXT"));
						String isAsset=rs.getString("is_asset");
						if(isAsset.equalsIgnoreCase("1"))
						{
							masterForm.setIsAsset("YES");
						}
						if(isAsset.equalsIgnoreCase("0"))
							masterForm.setIsAsset("NO");
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
						
						genDetails.add(masterForm);
						request.setAttribute("gendetails", genDetails);
					

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;

						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments==null || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkStatus==0)
				{

					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;

							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									String approveDate=	rsAppInfo.getString("approved_date");
									
									String a[]=approveDate.split(" ");
									approveDate=a[0];
									String b[]=approveDate.split("-");
									approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				  
				forwardType="genmaterial";
				}
			
			if(matType.equalsIgnoreCase("OSE"))
		     {
		 		LinkedList planDetails=new LinkedList();
		 	
		 		String matGroup="";
		 		 String location="";
		 		
		 		ApprovalsForm masterForm=new ApprovalsForm();
		 		
		 		
		 		
		 		String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
		 		" mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.IS_EQUIPMENT,mat.EQUIPMENT_NAME,mat.EQUIPMENT_MAKE," +
		 		"mat.Component_MAKE,mat.IS_SPARE,mat.OEM_PartNo,mat.IS_NEW_Equipment,mat.IS_NEW_Furniture,mat.IS_NEW_Facility,	mat.IS_Spare_required,mat.moc,mat.rating,mat.range," +
		 		"mat.PO_NUMBER,mat.PR_NUMBER,dep.DPTSTXT,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY,DIMENSION,mat.PACK_SIZE,mat.equip_Intended" +
		 		" from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , UNIT_MESUREMENT AS uom " +
		 		",PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+reqId+"' and	loc.LOCID=mat.LOCATION_ID and " +
		 		"stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID " +
		 		"and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='OSE' and val.VALUATION_ID=mat.VALUATION_CLASS and dep.DPTID=mat.UTILIZING_DEPT";
				
		 		
		 		
		 		ResultSet rs=ad.selectQuery(getFinishedProduct);
				try {
					if(rs.next())
					{
						masterForm.setRequestNo(reqId);
						masterForm.setRequestNumber(reqId);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");
						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
					
						masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
					
						String isEquipment=rs.getString("IS_EQUIPMENT");
						if(isEquipment.equalsIgnoreCase("1"))
						{
							masterForm.setIsEquipment("Yes");
							
						}
						masterForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
						masterForm.setEquipmentMake(rs.getString("EQUIPMENT_MAKE"));
						if(isEquipment.equalsIgnoreCase("0")){
							masterForm.setIsEquipment("No");
						
						}
						String isSpare=rs.getString("IS_SPARE");
						if(isSpare.equalsIgnoreCase("1"))
						{
							masterForm.setIsSpare("Yes");
						
						}
						masterForm.setComponentMake(rs.getString("Component_MAKE"));
						masterForm.setOemPartNo(rs.getString("OEM_PartNo"));
						if(isSpare.equalsIgnoreCase("0")){
							masterForm.setIsSpare("No");
						
							
						}
						masterForm.setMoc(rs.getString("moc"));
						masterForm.setRating(rs.getString("rating"));
						masterForm.setRange(rs.getString("range"));
						String isNewEquipment=rs.getString("IS_NEW_Equipment");
						if(isNewEquipment.equalsIgnoreCase("1"))
						{
							masterForm.setIsNewEquipment("Yes");
							
						}
						if(isNewEquipment.equalsIgnoreCase("0"))
						{
							masterForm.setIsNewEquipment("No");
							
						}
						String isNewfurniturt=rs.getString("IS_NEW_Furniture");
						if(isNewfurniturt.equalsIgnoreCase("1"))
						{
							masterForm.setIsItNewFurniture("Yes");
							
						}
						if(isNewfurniturt.equalsIgnoreCase("0"))
						{
							masterForm.setIsItNewFurniture("No");
							
						}
						if(isNewfurniturt.equalsIgnoreCase("2"))
						{
							masterForm.setIsItNewFurniture("N/A");
							
						}
						String isNewfacility=rs.getString("IS_NEW_Facility");
						if(isNewfacility.equalsIgnoreCase("1"))
						{
							masterForm.setIsItFacility("Yes");
							
						}
						if(isNewfacility.equalsIgnoreCase("0"))
						{
							masterForm.setIsItFacility("No");
							
						}
						if(isNewfacility.equalsIgnoreCase("2"))
						{
							masterForm.setIsItFacility("N/A");
							
						}
						String issparey=rs.getString("IS_Spare_required");
						if(issparey.equalsIgnoreCase("1"))
						{
							masterForm.setIsSpareNewEquipment("Yes");
							
						}
						if(issparey.equalsIgnoreCase("0"))
						{
							masterForm.setIsSpareNewEquipment("No");
							
						}	
						if(issparey.equalsIgnoreCase("2"))
						{
							masterForm.setIsSpareNewEquipment("N/A");
							
						}	
					
						masterForm.setPrNumber(rs.getString("PR_NUMBER"));
						masterForm.setPoNumber(rs.getString("PO_NUMBER"));
						masterForm.setUtilizingDept(rs.getString("DPTSTXT"));
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						masterForm.setDimension(rs.getString("DIMENSION"));
						masterForm.setPackSize(rs.getString("PACK_SIZE"));
						masterForm.setEquipIntendedFor(rs.getString("equip_Intended"));
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
					
						planDetails.add(masterForm);
						request.setAttribute("plandetails", planDetails);
						forwardType="planmaterial";


					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;
						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments==null || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkStatus==0)
				{

					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;
							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									String approveDate=	rsAppInfo.getString("approved_date");
									
									String a[]=approveDate.split(" ");
									approveDate=a[0];
									String b[]=approveDate.split("-");
									approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				forwardType="planmaterial";
				}
			if(matType.equalsIgnoreCase("PPC"))
			{
		 		LinkedList proDetails=new LinkedList();

		 		
		 		ApprovalsForm masterForm=new ApprovalsForm();
		 		
		 
		 		String matGroup="";
		 		 String location="";
		 		
		 		
		 		String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME," +
		 		"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, div.DIV_DESC," +
		 		"mat.is_asset,mat.PURPOSE_ID,mat.IS_SAS_FORM_AVAILABLE,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION," +
		 		"mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as " +
		 		"matGroup , UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur,DIVISION as div,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' and " +
		 		"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
		 		"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID  " +
		 		"and val.VALUATION_ID=mat.VALUATION_CLASS and Type='PPC' and stLoc.MAT_TYPE='PPC'"; 
		 				
		 		ResultSet rs=ad.selectQuery(getFinishedProduct);
		 		
		 	
		 			try {
						if(rs.next())
{
							String  str=rs.getString("STORAGE_LOCATION_NAME");
							if(str.equalsIgnoreCase("Printed Material")||str.equalsIgnoreCase("General Material")||str.equalsIgnoreCase("Complement store"))
							{	
								masterForm.setRequestNo(reqId);
								masterForm.setRequestNumber(reqId);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");

						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
						
						masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setDivisionId(rs.getString("DIV_DESC"));
						String isAsset=rs.getString("is_asset");
						if(isAsset.equalsIgnoreCase("1"))
						{
							masterForm.setIsAsset("YES");
						}
						if(isAsset.equalsIgnoreCase("0")){
							masterForm.setIsAsset("NO");
						}
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						
						String purposeId=rs.getString("PURPOSE_ID");
						if(purposeId.equalsIgnoreCase("1"))
						{
							masterForm.setPurposeID("Gift & Compliments");
							
						}
						if(purposeId.equalsIgnoreCase("2"))
						{
							masterForm.setPurposeID("Propaganda & Promotional (KUDLU DEPOT)");
							
						}
						if(purposeId.equalsIgnoreCase("3"))
						{
							masterForm.setPurposeID("Product Launch Exp");
							
						}
						if(purposeId.equalsIgnoreCase("4"))
						{
							masterForm.setPurposeID("Sales Promotional (SAS)");
							
						}
						if(purposeId.equalsIgnoreCase("5"))
						{
							masterForm.setPurposeID("Visual Ads,Literature");
							
						}
						
						if(purposeId.equalsIgnoreCase("6"))
						{
							masterForm.setPurposeID("Conference,National & Regional");
							
						}
						if(purposeId.equalsIgnoreCase("7"))
						{
							masterForm.setPurposeID("Incentive to Field Staff");
							
						}
						if(purposeId.equalsIgnoreCase("8"))
						{
							masterForm.setPurposeID("Incentive to Stockist/Chemist");
							
						}
						if(purposeId.equalsIgnoreCase("9"))
						{
							masterForm.setPurposeID("Travelling Lodging & Boarding Exp");
							
						}
						
   
						String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
						
						if(isSAS.equalsIgnoreCase("1"))
						{
							masterForm.setIsSASFormAvailable("YES");
						}
						if(isSAS.equalsIgnoreCase("0")){
							masterForm.setIsSASFormAvailable("NO");
	
}
						
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
						proDetails.add(masterForm);
	
						request.setAttribute("prodetails", proDetails);
						forwardType="promaterial";
   
	
    }}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 			
		 			int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;
							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments==null || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(checkStatus==0)
					{

						getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
						rsApprDetails=ad.selectQuery(getApprDetails);
						try {
							while(rsApprDetails.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(rsApprDetails.getString("Priority"));
								apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
								String empCode=rsApprDetails.getString("Approver_Id");
								String actualapprover="";
								boolean data=false;
								
								String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
								"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
								"mast.Material_Type='"+matType+"' AND Material_Group=''  and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
								"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
								"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								while(rsAppInfo.next())
								{
									actualapprover=rsAppInfo.getString("Actual_Approver");
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
										data=true;
									}
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
										data=true;
									}
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
										data=true;
									}
									apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
									{
										String approveDate=	rsAppInfo.getString("approved_date");
										
										String a[]=approveDate.split(" ");
										approveDate=a[0];
										String b[]=approveDate.split("-");
										approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
										apprvers.setDate(approveDate);
									}else{
										String rejectDate=	rsAppInfo.getString("rejected_date");
										
										String a[]=rejectDate.split(" ");
										rejectDate=a[0];
										String b[]=rejectDate.split("-");
										rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
										apprvers.setDate(rejectDate);
									}
									String comments=rsAppInfo.getString("Comments");
									if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo.getString("Comments"));
									}
								}
								String pernr="";

								if(data==true)
												{
													pernr=actualapprover;
												}
												else
												{
													pernr=rsApprDetails.getString("Approver_Id");
												}						
										
										
									String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
									ResultSet rsname=ad.selectQuery(name);
									if(rsname.next())
									{
										apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
									}
								listApprers.add(apprvers);
								request.setAttribute("approverDetails", listApprers);
}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
		 			forwardType="promaterial";
		 		}
		
		
		return mapping.findForward(forwardType);
	}

	public ActionForward lastMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SAPReportForm masterForm=(SAPReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		try{
			  LinkedList materTypeIDList=new LinkedList();
				LinkedList materialTypeIdValueList=new LinkedList();
				String getMaterials="select * from MATERIAL_TYPE";
				ResultSet rsMaterial=ad.selectQuery(getMaterials);
				while(rsMaterial.next())
				{
					materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
					materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
				}
				masterForm.setMaterTypeIDList(materTypeIDList);
				masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
				ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				if(locationList.size()==0){
					locationList.add("");
					locationLabelList.add("");
				}
				masterForm.setLocationIdList(locationList);
				masterForm.setLocationLabelList(locationLabelList);
				//vendor type
				 ArrayList accountGroupList=new ArrayList();
					ArrayList accountGroupLabelList=new ArrayList();
				 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
			ArrayList subLinkIdList = new ArrayList();
			ArrayList subLinkValueList = new ArrayList();
			while(rs.next()) {
				accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
				accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
			}
			masterForm.setAccountGroupList(accountGroupList);
			masterForm.setAccountGroupLabelList(accountGroupLabelList);
				String reqnumber=masterForm.getRequestNumber();
				String From=masterForm.getFromDate();
				String To=masterForm.getToDate();
				
				String From1=masterForm.getFromDate1();
				String To1=masterForm.getToDate1();
				
				if(From!=null){
					if(!From.equalsIgnoreCase("")){
					  String b[]=From.split("/");
					  From=b[2]+"-"+b[1]+"-"+b[0];
					}
					}
					//String To=codeRequestForm.getToDate();
				
					if(To!=null){
					if(!To.equalsIgnoreCase("")){
					  String b1[]=To.split("/");
					  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
					}
					}
					
					if(From1!=null){
						if(!From1.equalsIgnoreCase("")){
						  String b[]=From1.split("/");
						  From1=b[2]+"-"+b[1]+"-"+b[0];
						}
						}
						//String To=codeRequestForm.getToDate();
					
						if(To1!=null){
						if(!To1.equalsIgnoreCase("")){
						  String b1[]=To1.split("/");
						  To1=b1[2]+"-"+b1[1]+"-"+b1[0];	
						}
						}	
				
		 String Location=masterForm.getLocationId();
	     String Materialcode=masterForm.getMaterialCodeLists();
	     String searchType=masterForm.getSearchType();
	     if(searchType.equals("Service Master")){
	    	 LinkedList listOfServiceCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
				int startRecord=masterForm.getStartRecord();//11
				int endRecord=masterForm.getEndRecord();	
				
				
				 startRecord=totalRecords-9;
				 endRecord=totalRecords;
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(totalRecords);
				 String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY s.request_no) AS RowNum, s.request_no,s.request_date,"
		 					+ "loc.LOCATION_CODE,emp.EMP_FULLNAME,s.service_description,s.last_approver,s.pending_approver,s.app_satus,s.SAP_CODE_NO,s.SAP_CREATION_DATE from SERVICE_MASTER s,Location loc,"
		 					+ "emp_official_info emp where s.CREATED_BY=emp.PERNR and s.plant_code=loc.LOCID   ";
		 							if(!Location.equalsIgnoreCase("")){	
		 								query=query+ " and  loc.LOCID='"+Location+"'";
		 							}
		 							if(!Materialcode.equalsIgnoreCase("")){	
		 								query=query+ " and s.service_catagory='"+Materialcode+"'";
		 							}
		 							if(!From.equalsIgnoreCase("")){	
		 							query=query+ " and (s.CREATED_DATE between '"+From+"' and '"+To+"') ";
		 							}
		 							
		 							/*if(!From1.equalsIgnoreCase("")){	
		 								query=query+ " and (s.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
		 							}*/
		 							
		 							if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
		 							{
		 								query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
		 							}
		 							
		 							if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
		 							{
		 								query=query+ " and  app_satus = 'Completed' ";
		 							}
		 							
		 							if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
		 							{
		 								query=query+ " and  app_satus = 'Rejected' ";
		 							}
		 							
		 							if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
		 							{
		 								query=query+ " and  app_satus not in ('Rejected','Completed','Saved') ";
		 							}
		 							
		 							/*if(!masterForm.getApprover().equalsIgnoreCase(""))
		 							{
		 								query=query+ " and  request_no in (select Req_Id "
		 										+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Service Master') ";
		 							}*/
		 							
		 							query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by request_no ";
		 							ResultSet rsList=ad.selectQuery(query);
		 							while(rsList.next()){
		 								ServiceMasterRequestForm c=new ServiceMasterRequestForm();
		 								c.setR_no(rsList.getInt("request_no"));
		 								c.setRequestDate(rsList.getString("request_date"));
		 								
		 								c.setLocationID(rsList.getString("LOCATION_CODE"));
		 								c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
		 								c.setServiceDescription(rsList.getString("service_description"));
		 								c.setApproveType(rsList.getString("app_satus"));
		 								String sapcodeno=rsList.getString("SAP_CODE_NO");
		 								if(sapcodeno!=null){
		 								 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
		 								 c.setSapCreationDate("");
		 								}
		 								else
		 									c.setSapCodeNo("");	
		 								
		 								String sapCodeNo=rsList.getString("SAP_CODE_NO");
		 								String requestNo=rsList.getString("request_no");
		 								String approveStatus=rsList.getString("app_satus");
		 								String approverList="";
		 								String codeCreationDate="";
		 								
		 								String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
		 								+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Service Master'  "
		 								+ " and all_r.Approved_Persons=emp.PERNR";
		 								ResultSet rsApp=ad.selectQuery(approverDet);
		 								try{
		 									while(rsApp.next()){
		 										approverList=approverList+rsApp.getString("EMP_FULLNAME");
		 										String status=rsApp.getString("Req_Status");
		 										if(status.equals("Approved")||status.equals("Completed"))
		 											approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
		 										else if(status.equals("Rejected"))
		 											approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
		 										
		 										if(status.equals("Completed"))
		 											codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
		 										else if(status.equals("Rejected"))
		 											codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
		 											
		 									}
		 									//if(!approverList.equals(""))
		 									//approverList=approverList.substring(0, (approverList.length()-1));
		 									c.setApproversList(approverList);
		 								}catch(Exception e){
		 									e.printStackTrace();
		 								}
		 								 
		 								if(sapCodeNo!=null){
		 									c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
		 									c.setCodeCreationDate(codeCreationDate);
		 								}
		 								else{
		 									c.setSapCodeNo("");
		 									c.setCodeCreationDate("");
		 								}
		 								String sapCrDt=rsList.getString("SAP_CREATION_DATE");
		 								if(sapCrDt!=null){
		 									if(!sapCrDt.equals("")){
		 										c.setSapCreationDate(codeCreationDate);
		 									}else
		 										c.setSapCreationDate("");
		 										
		 								}else{
		 									c.setSapCreationDate("");
		 								}
		 								String lastApprover=rsList.getString("last_approver");
		 								if(!lastApprover.equals("No"))
		 								 c.setLastApprover(rsList.getString("last_approver"));
		 								else
		 									c.setLastApprover("");	
		 								String pendingAppr=rsList.getString("pending_approver");
		 								if(!pendingAppr.equals("No"))
		 									c.setPendingApprover(rsList.getString("pending_approver"));
		 								else
		 									c.setPendingApprover("");
		 								
		 								
		 								listOfServiceCode.add(c);
		 							}
		 			
		 					
		 					request.setAttribute("listOfServiceCode", listOfServiceCode);
		 			
		 					
		 					request.setAttribute("listOfServiceCode", listOfServiceCode);
	 					request.setAttribute("disableNextButton", "disableNextButton");
	 					request.setAttribute("previousButton", "previousButton");
	 					if(listOfServiceCode.size()<10)
	 					{
	 						request.setAttribute("previousButton", "");
	 						request.setAttribute("disablePreviousButton", "disablePreviousButton");
	 					}
	 					request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
		}
	     if(searchType.equals("Vendor Master")){
	    	 LinkedList listOfVendorCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
				int startRecord=masterForm.getStartRecord();//11
				int endRecord=masterForm.getEndRecord();	
				
				
				 startRecord=totalRecords-9;
				 endRecord=totalRecords;
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(totalRecords);
				 String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.REQUEST_NO ) AS RowNum, v.REQUEST_NO,v.REQUEST_DATE,emp.LOCID,"
							+ "emp.EMP_FULLNAME,v.NAME,v.CITY,v.Approve_Status,v.last_approver,"
							+ "v.pending_approver,v.SAP_CODE_NO,v.SAP_CREATION_DATE from vendor_master_m v,emp_official_info emp,Location loc "
							+ "where v.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID   ";
							if(!Location.equalsIgnoreCase("")){	
								query=query+ " and  loc.LOCID='"+Location+"'";
							}
							if(!Materialcode.equalsIgnoreCase("")){	
								query=query+ " and v.ACCOUNT_GROUP_ID='"+Materialcode+"'";
							}
							if(!From.equalsIgnoreCase("")){	
							query=query+ " and v.REQUEST_DATE between '"+From+"' and '"+To+"'";
							}
							
							
							/*if(!From1.equalsIgnoreCase("")){	
								query=query+ " and (v.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
							}*/
							
							if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
							{
							query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
							}
							
							if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
							{
								query=query+ " and  Approve_Status = 'Completed' ";
							}
							
							if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
							{
								query=query+ " and  Approve_Status = 'Rejected' ";
							}
							
 							if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
							{
								query=query+ " and  Approve_Status in ('Rejected','Completed','Saved') ";
							}
							
							/*if(!masterForm.getApprover().equalsIgnoreCase(""))
							{
								query=query+ " and  request_no in (select Req_Id "
										+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Vendor Master') ";
							}*/
							
							query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
							ResultSet rsList=ad.selectQuery(query);
							while(rsList.next()){
								VendorMasterRequestForm c=new VendorMasterRequestForm();
								c.setRequestNo(rsList.getInt("REQUEST_NO"));
								c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
								c.setName(rsList.getString("NAME"));
								c.setCity(rsList.getString("CITY"));
								c.setLocationId(rsList.getString("LOCID"));
								c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
								c.setApproveType(rsList.getString("Approve_Status"));
								String sapcodeno=rsList.getString("SAP_CODE_NO");
								if(sapcodeno!=null){
								 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
								 c.setSapCreationDate("");
								}
								else
									c.setSapCodeNo("");	
								
								String sapCodeNo=rsList.getString("SAP_CODE_NO");
								String requestNo=rsList.getString("REQUEST_NO");
								String approveStatus=rsList.getString("Approve_Status");
								String approverList="";
								String codeCreationDate="";
								
								String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
								+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Vendor Master'  "
								+ " and all_r.Approved_Persons=emp.PERNR";
								ResultSet rsApp=ad.selectQuery(approverDet);
								try{
									while(rsApp.next()){
										approverList=approverList+rsApp.getString("EMP_FULLNAME");
										String status=rsApp.getString("Req_Status");
										if(status.equals("Approved")||status.equals("Completed"))
											approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
										else if(status.equals("Rejected"))
											approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
										
										if(status.equals("Completed"))
											codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
										else if(status.equals("Rejected"))
											codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
											
									}
									//if(!approverList.equals(""))
									//approverList=approverList.substring(0, (approverList.length()-1));
									c.setApproversList(approverList);
								}catch(Exception e){
									e.printStackTrace();
								}
								 
								if(sapCodeNo!=null){
									c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
									c.setCodeCreationDate(codeCreationDate);
								}
								else{
									c.setSapCodeNo("");
									c.setCodeCreationDate("");
								}
								String sapCrDt=rsList.getString("SAP_CREATION_DATE");
								if(sapCrDt!=null){
									if(!sapCrDt.equals("")){
										c.setSapCreationDate(codeCreationDate);
									}else
										c.setSapCreationDate("");
										
								}else{
									c.setSapCreationDate("");
								}
								String lastApprover=rsList.getString("last_approver");
								if(!lastApprover.equals("No"))
								 c.setLastApprover(rsList.getString("last_approver"));
								else
									c.setLastApprover("");	
								String pendingAppr=rsList.getString("pending_approver");
								if(!pendingAppr.equals("No"))
									c.setPendingApprover(rsList.getString("pending_approver"));
								else
									c.setPendingApprover("");
								
								
								listOfVendorCode.add(c);
							}
		 			
		 					
		 					request.setAttribute("listOfVendorCode", listOfVendorCode);
	 					request.setAttribute("disableNextButton", "disableNextButton");
	 					request.setAttribute("previousButton", "previousButton");
	 					if(listOfVendorCode.size()<10)
	 					{
	 						
	 						request.setAttribute("previousButton", "");
	 						request.setAttribute("disablePreviousButton", "disablePreviousButton");
	 					}
	 					request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
	 		
	 		
		}
	     if(searchType.equals("Customer Master")){
	    	 LinkedList listOfCustomerCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
				int startRecord=masterForm.getStartRecord();//11
				int endRecord=masterForm.getEndRecord();	
				
				
				 startRecord=totalRecords-9;
				 endRecord=totalRecords;
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(totalRecords);
				 String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.ID) AS RowNum, cus.REQUEST_NO,cus.REQUEST_DATE,cus.NAME,cus.CITY,emp.LOCID,"
		 					+ "emp.EMP_FULLNAME,cus.Approve_Status,cus.SAP_CODE_NO,cus.last_approver,cus.pending_approver,cus.SAP_CREATION_DATE from CUSTOMER_MASTER_M cus,emp_official_info emp,Location loc "
		 					+ "where cus.CREATED_BY=emp.PERNR and emp.LOCID =loc.LOCATION_CODE ";
		 					if(!Location.equalsIgnoreCase("")){	
		 						query=query+ " and loc.LOCID='"+Location+"' ";
		 					}
		 					if(!Materialcode.equalsIgnoreCase("")){	
		 						query=query+ " and cus.ACCOUNT_GROUP_ID='"+Materialcode+"'";
		 					}
		 					if(!From.equalsIgnoreCase("")){	
		 					query=query+ " and cus.REQUEST_DATE between '"+From+"' and '"+To+"'";
		 					}
		 					
		 					/*if(!From1.equalsIgnoreCase("")){	
		 						query=query+ " and (cus.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
		 					}*/
		 					
		 					if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
		 							{
		 								query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
		 							}
		 					
		 					if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
		 					{
		 						query=query+ " and  Approve_Status = 'Completed' ";
		 					}
		 					
		 					if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
		 					{
		 						query=query+ " and  Approve_Status = 'Rejected' ";
		 					}
		 					
 							if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
		 					{
		 						query=query+ " and  Approve_Status not in ('Rejected','Completed','Saved') ";
		 					}
		 						
		 					/*if(!masterForm.getApprover().equalsIgnoreCase(""))
		 					{
		 						query=query+ " and  request_no in (select Req_Id "
		 								+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Customer Master') ";
		 					}*/

		 					
		 					
		 					query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
		 					ResultSet rsList=ad.selectQuery(query);
		 					while(rsList.next()){
		 						CustomerMasterForm c=new CustomerMasterForm();
		 						c.setRequestNo(rsList.getString("REQUEST_NO"));
		 						c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
		 						c.setCustomerName(rsList.getString("NAME"));
		 						c.setCity(rsList.getString("CITY"));
		 						c.setLocationId(rsList.getString("LOCID"));
		 						c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
		 						c.setApproveType(rsList.getString("Approve_Status"));
		 						String sapcodeno=rsList.getString("SAP_CODE_NO");
		 						if(sapcodeno!=null){
		 						 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
		 						 c.setSapCreationDate("");
		 						}
		 						else
		 							c.setSapCodeNo("");	
		 						
		 						String sapCodeNo=rsList.getString("SAP_CODE_NO");
		 						String requestNo=rsList.getString("REQUEST_NO");
		 						String approveStatus=rsList.getString("Approve_Status");
		 						String approverList="";
		 						String codeCreationDate="";
		 						
		 						String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
		 						+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Customer Master'  "
		 						+ " and all_r.Approved_Persons=emp.PERNR";
		 						ResultSet rsApp=ad.selectQuery(approverDet);
		 						try{
		 							while(rsApp.next()){
		 								approverList=approverList+rsApp.getString("EMP_FULLNAME");
		 								String status=rsApp.getString("Req_Status");
		 								if(status.equals("Approved")||status.equals("Completed"))
		 									approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
		 								else if(status.equals("Rejected"))
		 									approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
		 								
		 								if(status.equals("Completed"))
		 									codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
		 								else if(status.equals("Rejected"))
		 									codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
		 									
		 							}
		 							//if(!approverList.equals(""))
		 							//approverList=approverList.substring(0, (approverList.length()-1));
		 							c.setApproversList(approverList);
		 						}catch(Exception e){
		 							e.printStackTrace();
		 						}
		 						 
		 						if(sapCodeNo!=null){
		 							c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
		 							c.setCodeCreationDate(codeCreationDate);
		 						}
		 						else{
		 							c.setSapCodeNo("");
		 							c.setCodeCreationDate("");
		 						}
		 						String sapCrDt=rsList.getString("SAP_CREATION_DATE");
		 						if(sapCrDt!=null){
		 							if(!sapCrDt.equals("")){
		 								c.setSapCreationDate(codeCreationDate);
		 							}else
		 								c.setSapCreationDate("");
		 								
		 						}else{
		 							c.setSapCreationDate("");
		 						}
		 						String lastApprover=rsList.getString("last_approver");
		 						if(lastApprover!=null)
		 						if(!lastApprover.equals("No"))
		 						 c.setLastApprover(rsList.getString("last_approver"));
		 						else
		 							c.setLastApprover("");	
		 						String pendingAppr=rsList.getString("pending_approver");
		 						if(pendingAppr!=null)
		 						if(!pendingAppr.equals("No"))
		 							c.setPendingApprover(rsList.getString("pending_approver"));
		 						else
		 							c.setPendingApprover("");
		 						listOfCustomerCode.add(c);
		 					}
		 					
		 					request.setAttribute("listOfCustomerCode", listOfCustomerCode);
	 			
	 					
	 					request.setAttribute("listOfCustomerCode", listOfCustomerCode);
	 		
	 					request.setAttribute("disableNextButton", "disableNextButton");
	 					request.setAttribute("previousButton", "previousButton");
	 					if(listOfCustomerCode.size()<10)
	 					{
	 						
	 						request.setAttribute("previousButton", "");
	 						request.setAttribute("disablePreviousButton", "disablePreviousButton");
	 					}
	 					request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
	 		
	 		
		}
	     if(searchType.equals("Material Master")){
	    	 LinkedList listOfMaterialCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
				int startRecord=masterForm.getStartRecord();//11
				int endRecord=masterForm.getEndRecord();	
				
				
				 startRecord=totalRecords-9;
				 endRecord=totalRecords;
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(totalRecords);
	 			
	 			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO ) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,emp.EMP_FULLNAME,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
	 					"m.URL,L.location_code,m.REPORT_URL,mType.M_DESC,m.last_approver,m.pending_approver,m.SAP_CODE_NO,m.SAP_CREATION_DATE from material_code_request "
	 					+ "as m,Location as l ,MATERIAL_TYPE as mType,emp_official_info emp  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and emp.PERNR=m.CREATED_BY  ";
	 					if(!Location.equalsIgnoreCase("")){	
	 						query=query+ " and m.LOCATION_ID='"+Location+"'";
	 					}
	 					if(!Materialcode.equalsIgnoreCase("")){	
	 					query=query+ " and m.Type='"+Materialcode+"'";
	 					}
	 					if(!From.equalsIgnoreCase("")){	
	 					query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
	 					}
	 					
	 					

						/*if(!From1.equalsIgnoreCase("")){	
					query=query+ " and (m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
					query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				

				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					query=query+ " and  Approve_Type = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					query=query+ " and  Approve_Type = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
				{
					query=query+ " and  Approve_Type  in ('Submited') ";
				}

				if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					query=query+ " and  Approve_Type  in ('In Process') ";
				}
				
				/*if(!masterForm.getApprover().equalsIgnoreCase(""))
				{
					query=query+ " and  request_no in (select Req_Id "
							+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Material Master') ";
				}*/
				
				
	 					query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
	 					ResultSet rsList=ad.selectQuery(query);
	 					while(rsList.next()){
	 						MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
	 						materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
	 						
	 						String requestDate=rsList.getString("REQUEST_DATE");
	 						String req[]=requestDate.split(" ");
	 						requestDate=req[0];
	 						String a[]=requestDate.split("-");
	 						requestDate=a[2]+"/"+a[1]+"/"+a[0];
	 						materialForm.setRequestDate(requestDate);
	 						materialForm.setRequesterName(rsList.getString("EMP_FULLNAME"));
	 						materialForm.setLocationId(rsList.getString("location_code"));
	 						materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
	 						materialForm.setApproveType(rsList.getString("Approve_Type"));
	 						materialForm.setmType(rsList.getString("Type"));
	 						
	 						materialForm.setReqMatType(rsList.getString("Type"));
	 						String sapCodeNo=rsList.getString("SAP_CODE_NO");
	 						String requestNo=rsList.getString("REQUEST_NO");
	 						String matType=rsList.getString("Type");
	 						String approveStatus=rsList.getString("Approve_Type");
	 						String approverList="";
	 						String codeCreationDate="";
	 						String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
	 						+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Material Master' and "
	 						+ "type='"+matType+"' and all_r.Approved_Persons=emp.PERNR";
	 						ResultSet rsApp=ad.selectQuery(approverDet);
	 						try{
	 							while(rsApp.next()){
	 								approverList=approverList+rsApp.getString("EMP_FULLNAME");
	 								String status=rsApp.getString("Req_Status");
	 								if(status.equals("Approved")||status.equals("Completed"))
	 									approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
	 								else if(status.equals("Rejected"))
	 									approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
	 								
	 								if(status.equals("Completed"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
	 								else if(status.equals("Rejected"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
	 									
	 							}
	 							//if(!approverList.equals(""))
	 							//approverList=approverList.substring(0, (approverList.length()-1));
	 							materialForm.setApproversList(approverList);
	 						}catch(Exception e){
	 							e.printStackTrace();
	 						}
	 						 
	 						if(sapCodeNo!=null){
	 							materialForm.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 							materialForm.setCodeCreationDate(codeCreationDate);
	 						}
	 						else{
	 							materialForm.setSapCodeNo("");
	 							materialForm.setCodeCreationDate("");
	 						}
	 						String sapCrDt=rsList.getString("SAP_CREATION_DATE");
	 						if(sapCrDt!=null){
	 							if(!sapCrDt.equals("")){
	 								materialForm.setSapCreationDate(codeCreationDate);
	 							}else
	 								materialForm.setSapCreationDate("");
	 								
	 						}else{
	 							materialForm.setSapCreationDate("");
	 						}
	 						String lastApprover=rsList.getString("last_approver");
	 						if(!lastApprover.equals("No"))
	 						 materialForm.setLastApprover(rsList.getString("last_approver"));
	 						else
	 							materialForm.setLastApprover("");	
	 						String pendingAppr=rsList.getString("pending_approver");
	 						if(!pendingAppr.equals("No"))
	 							materialForm.setPendingApprover(rsList.getString("pending_approver"));
	 						else
	 							materialForm.setPendingApprover("");
	 						//prevappr
	 						String lastappr="select CONVERT(varchar(10),all_r.approved_date,103)+' '+ CONVERT(varchar(5),all_r.approved_date,108) as approved_date1,Actual_Approver,approved_date,emp.EMP_FULLNAME from All_Request as all_r,emp_official_info emp where Req_Id='"+requestNo+"' and Req_Type='Material Master'  and emp.PERNR=all_r.Actual_Approver order by approved_date desc";
	 						ResultSet rslast=ad.selectQuery(lastappr);
	 						int ab=0;
	 						String actual="";
	 						int chk=0;
	 						while(rslast.next())
	 						{
	 							
	 							materialForm.setPrevApprover(rslast.getString("EMP_FULLNAME"));
	 							materialForm.setPrevApproverDate(rslast.getString("approved_date1"));
	 							ab=ab+1;
	 							if(!actual.equalsIgnoreCase(rslast.getString("Actual_Approver"))&&chk==1)
	 							{
	 								break;
	 							}
	 							
	 							
	 							actual=rslast.getString("Actual_Approver");
	 							chk=1;
	 							
	 						}
	 						listOfMaterialCode.add(materialForm);
	 						}
	 					
	 					request.setAttribute("listOfMaterials", listOfMaterialCode);
	 		
	 					request.setAttribute("disableNextButton", "disableNextButton");
	 					request.setAttribute("previousButton", "previousButton");
	 					if(listOfMaterialCode.size()<10)
	 					{
	 						
	 						request.setAttribute("previousButton", "");
	 						request.setAttribute("disablePreviousButton", "disablePreviousButton");
	 					}
	 					request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
	 		
	 		
		}
	     
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("display");
	}
	public ActionForward previousMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SAPReportForm masterForm=(SAPReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		try{
			  LinkedList materTypeIDList=new LinkedList();
				LinkedList materialTypeIdValueList=new LinkedList();
				String getMaterials="select * from MATERIAL_TYPE";
				ResultSet rsMaterial=ad.selectQuery(getMaterials);
				while(rsMaterial.next())
				{
					materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
					materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
				}
				masterForm.setMaterTypeIDList(materTypeIDList);
				masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
				ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				if(locationList.size()==0){
					locationList.add("");
					locationLabelList.add("");
				}
				masterForm.setLocationIdList(locationList);
				masterForm.setLocationLabelList(locationLabelList);
				//vendor type
				 ArrayList accountGroupList=new ArrayList();
					ArrayList accountGroupLabelList=new ArrayList();
				 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
			ArrayList subLinkIdList = new ArrayList();
			ArrayList subLinkValueList = new ArrayList();
			while(rs.next()) {
				accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
				accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
			}
			masterForm.setAccountGroupList(accountGroupList);
			masterForm.setAccountGroupLabelList(accountGroupLabelList);
			
			ArrayList techList=new ArrayList();
			ArrayList techLabelList=new ArrayList();
			ResultSet rs13 = ad.selectQuery(" select app,emp.EMP_FULLNAME from ( select Approver_Id as app from Material_Approvers where role ='Creator' and Approver_Id!='' group by Approver_Id union select Parllel_Approver_1 from Material_Approvers where role ='Creator' and Parllel_Approver_1!='' group by Parllel_Approver_1 union select Parllel_Approver_2 from Material_Approvers where role ='Creator' and Parllel_Approver_2!='' group by Parllel_Approver_2)t,emp_official_info as emp where emp.PERNR=App  and emp.ACTIVE =1 order  by emp.EMP_FULLNAME"); 
					try {
						while(rs13.next()) {
							techList.add(rs13.getString("App"));
							techLabelList.add(rs13.getString("EMP_FULLNAME"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					masterForm.setTechidList(techList);
					masterForm.setTechnameList(techLabelList);
				String reqnumber=masterForm.getRequestNumber();
				String From=masterForm.getFromDate();
				String To=masterForm.getToDate();
				
				String From1=masterForm.getFromDate1();
				String To1=masterForm.getToDate1();
				if(From!=null){
					if(!From.equalsIgnoreCase("")){
					  String b[]=From.split("/");
					  From=b[2]+"-"+b[1]+"-"+b[0];
					}
					}
					//String To=codeRequestForm.getToDate();
				
					if(To!=null){
					if(!To.equalsIgnoreCase("")){
					  String b1[]=To.split("/");
					  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
					}
					}
					
					if(From1!=null){
						if(!From1.equalsIgnoreCase("")){
						  String b[]=From1.split("/");
						  From1=b[2]+"-"+b[1]+"-"+b[0];
						}
						}
						//String To=codeRequestForm.getToDate();
					
						if(To1!=null){
						if(!To1.equalsIgnoreCase("")){
						  String b1[]=To1.split("/");
						  To1=b1[2]+"-"+b1[1]+"-"+b1[0];	
						}
						}
				
		 String Location=masterForm.getLocationId();
	     String Materialcode=masterForm.getMaterialCodeLists();
	     String searchType=masterForm.getSearchType();	
	     if(searchType.equals("Service Master")){
			  LinkedList listOfServiceCode=new LinkedList();
		    	 int totalRecords=masterForm.getTotalRecords();//21
		 		int endRecord=masterForm.getStartRecord()-1;//20
		 		int startRecord=masterForm.getStartRecord()-10;//11
		 		
		 		if(startRecord==1)
		 		{
		 			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		 			endRecord=10;
		 		}
		 		masterForm.setTotalRecords(totalRecords);
		 		masterForm.setStartRecord(1);
		 		masterForm.setEndRecord(10);
		 		String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY s.request_no) AS RowNum, s.request_no,s.request_date,"
	 					+ "loc.LOCATION_CODE,emp.EMP_FULLNAME,s.service_description,s.last_approver,s.pending_approver,s.app_satus,s.SAP_CODE_NO,s.SAP_CREATION_DATE from SERVICE_MASTER s,Location loc,"
	 					+ "emp_official_info emp where s.CREATED_BY=emp.PERNR and s.plant_code=loc.LOCID   ";
	 							if(!Location.equalsIgnoreCase("")){	
	 								query=query+ " and  loc.LOCID='"+Location+"'";
	 							}
	 							if(!Materialcode.equalsIgnoreCase("")){	
	 								query=query+ " and s.service_catagory='"+Materialcode+"'";
	 							}
	 						if(!From.equalsIgnoreCase("")){	
	 							query=query+ " and (s.CREATED_DATE between '"+From+"' and '"+To+"') ";
	 							}
	 							
	 							/*if(!From1.equalsIgnoreCase("")){	
	 								query=query+ " and (s.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
	 							}*/
	 							
	 							if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
	 							{
	 								query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
	 							}
	 							
	 							if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
	 							{
	 								query=query+ " and  app_satus = 'Completed' ";
	 							}
	 							
	 							if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
	 							{
	 								query=query+ " and  app_satus = 'Rejected' ";
	 							}
	 							
	 							if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
	 							{
	 								query=query+ " and  Approve_Type  in ('Submited') ";
	 							}
	 							if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
	 							{
	 								query=query+ " and  Approve_Type  in ('In Process') ";
	 							}
	 							
	 							/*if(!masterForm.getApprover().equalsIgnoreCase(""))
	 							{
	 								query=query+ " and  request_no in (select Req_Id "
	 										+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Service Master') ";
	 							}*/
	 							
	 							
	 							query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by request_no ";
	 							ResultSet rsList=ad.selectQuery(query);
	 							while(rsList.next()){
	 								ServiceMasterRequestForm c=new ServiceMasterRequestForm();
	 								c.setR_no(rsList.getInt("request_no"));
	 								c.setRequestDate(rsList.getString("request_date"));
	 								
	 								c.setLocationID(rsList.getString("LOCATION_CODE"));
	 								c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
	 								c.setServiceDescription(rsList.getString("service_description"));
	 								c.setApproveType(rsList.getString("app_satus"));
	 								String sapcodeno=rsList.getString("SAP_CODE_NO");
	 								if(sapcodeno!=null){
	 								 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 								 c.setSapCreationDate("");
	 								}
	 								else
	 									c.setSapCodeNo("");	
	 								
	 								String sapCodeNo=rsList.getString("SAP_CODE_NO");
	 								String requestNo=rsList.getString("request_no");
	 								String approveStatus=rsList.getString("app_satus");
	 								String approverList="";
	 								String codeCreationDate="";
	 								
	 								String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
	 								+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Service Master'  "
	 								+ " and all_r.Approved_Persons=emp.PERNR";
	 								ResultSet rsApp=ad.selectQuery(approverDet);
	 								try{
	 									while(rsApp.next()){
	 										approverList=approverList+rsApp.getString("EMP_FULLNAME");
	 										String status=rsApp.getString("Req_Status");
	 										if(status.equals("Approved")||status.equals("Completed"))
	 											approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
	 										else if(status.equals("Rejected"))
	 											approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
	 										
	 										if(status.equals("Completed"))
	 											codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
	 										else if(status.equals("Rejected"))
	 											codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
	 											
	 									}
	 									//if(!approverList.equals(""))
	 									//approverList=approverList.substring(0, (approverList.length()-1));
	 									c.setApproversList(approverList);
	 								}catch(Exception e){
	 									e.printStackTrace();
	 								}
	 								 
	 								if(sapCodeNo!=null){
	 									c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 									c.setCodeCreationDate(codeCreationDate);
	 								}
	 								else{
	 									c.setSapCodeNo("");
	 									c.setCodeCreationDate("");
	 								}
	 								String sapCrDt=rsList.getString("SAP_CREATION_DATE");
	 								if(sapCrDt!=null){
	 									if(!sapCrDt.equals("")){
	 										c.setSapCreationDate(codeCreationDate);
	 									}else
	 										c.setSapCreationDate("");
	 										
	 								}else{
	 									c.setSapCreationDate("");
	 								}
	 								String lastApprover=rsList.getString("last_approver");
	 								if(!lastApprover.equals("No"))
	 								 c.setLastApprover(rsList.getString("last_approver"));
	 								else
	 									c.setLastApprover("");	
	 								String pendingAppr=rsList.getString("pending_approver");
	 								if(!pendingAppr.equals("No"))
	 									c.setPendingApprover(rsList.getString("pending_approver"));
	 								else
	 									c.setPendingApprover("");
	 								
	 								
	 								listOfServiceCode.add(c);
	 							}
	 			
	 					
	 					request.setAttribute("listOfServiceCode", listOfServiceCode);
		 					masterForm.setTotalRecords(totalRecords);
		 					masterForm.setStartRecord(startRecord);
		 					masterForm.setEndRecord(endRecord);
		 							request.setAttribute("nextButton", "nextButton");
		 							if(startRecord!=1)
		 							request.setAttribute("previousButton", "previousButton");
		 							request.setAttribute("displayRecordNo", "displayRecordNo");
		 							if(listOfServiceCode.size()<10)
		 							{
		 								masterForm.setStartRecord(1);
		 								request.setAttribute("previousButton", "");
		 								request.setAttribute("disablePreviousButton", "disablePreviousButton");
		 							}
				 
				 if(endRecord==totalRecords)
				 {
					 request.setAttribute("nextButton", "");
						request.setAttribute("disableNextButton", "disableNextButton");
				 }
				 request.setAttribute("displayRecordNo", "displayRecordNo");
		 		
		 		
			}
		  if(searchType.equals("Vendor Master")){
			  LinkedList listOfVendorCode=new LinkedList();
		    	 int totalRecords=masterForm.getTotalRecords();//21
		 		int endRecord=masterForm.getStartRecord()-1;//20
		 		int startRecord=masterForm.getStartRecord()-10;//11
		 		
		 		if(startRecord==1)
		 		{
		 			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		 			endRecord=10;
		 		}
		 		masterForm.setTotalRecords(totalRecords);
		 		masterForm.setStartRecord(1);
		 		masterForm.setEndRecord(10);
		 		String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.REQUEST_NO ) AS RowNum, v.REQUEST_NO,v.REQUEST_DATE,emp.LOCID,"
						+ "emp.EMP_FULLNAME,v.NAME,v.CITY,v.Approve_Status,v.last_approver,"
						+ "v.pending_approver,v.SAP_CODE_NO,v.SAP_CREATION_DATE from vendor_master_m v,emp_official_info emp,Location loc "
						+ "where v.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID   ";
						if(!Location.equalsIgnoreCase("")){	
							query=query+ " and  loc.LOCID='"+Location+"'";
						}
						if(!Materialcode.equalsIgnoreCase("")){	
							query=query+ " and v.ACCOUNT_GROUP_ID='"+Materialcode+"'";
						}
						if(!From.equalsIgnoreCase("")){	
						query=query+ " and v.REQUEST_DATE between '"+From+"' and '"+To+"'";
						}
						
						/*if(!From1.equalsIgnoreCase("")){	
							query=query+ " and (v.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
						}*/
						
						if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
						{
						query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
						}
						
						if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
						{
							query=query+ " and  Approve_Status = 'Completed' ";
						}
						
						if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
						{
							query=query+ " and  Approve_Status = 'Rejected' ";
						}
						
						if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
						{
							query=query+ " and  Approve_Type  in ('Submited') ";
						}
						if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
						{
							query=query+ " and  Approve_Type  in ('In Process') ";
						}
						
						/*if(!masterForm.getApprover().equalsIgnoreCase(""))
						{
							query=query+ " and  request_no in (select Req_Id "
									+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Vendor Master') ";
						}*/
						query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
						ResultSet rsList=ad.selectQuery(query);
						while(rsList.next()){
							VendorMasterRequestForm c=new VendorMasterRequestForm();
							c.setRequestNo(rsList.getInt("REQUEST_NO"));
							c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
							c.setName(rsList.getString("NAME"));
							c.setCity(rsList.getString("CITY"));
							c.setLocationId(rsList.getString("LOCID"));
							c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
							c.setApproveType(rsList.getString("Approve_Status"));
							String sapcodeno=rsList.getString("SAP_CODE_NO");
							if(sapcodeno!=null){
							 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
							 c.setSapCreationDate("");
							}
							else
								c.setSapCodeNo("");	
							
							String sapCodeNo=rsList.getString("SAP_CODE_NO");
							String requestNo=rsList.getString("REQUEST_NO");
							String approveStatus=rsList.getString("Approve_Status");
							String approverList="";
							String codeCreationDate="";
							
							String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
							+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Vendor Master'  "
							+ " and all_r.Approved_Persons=emp.PERNR";
							ResultSet rsApp=ad.selectQuery(approverDet);
							try{
								while(rsApp.next()){
									approverList=approverList+rsApp.getString("EMP_FULLNAME");
									String status=rsApp.getString("Req_Status");
									if(status.equals("Approved")||status.equals("Completed"))
										approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
									else if(status.equals("Rejected"))
										approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
									
									if(status.equals("Completed"))
										codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
									else if(status.equals("Rejected"))
										codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
										
								}
								//if(!approverList.equals(""))
								//approverList=approverList.substring(0, (approverList.length()-1));
								c.setApproversList(approverList);
							}catch(Exception e){
								e.printStackTrace();
							}
							 
							if(sapCodeNo!=null){
								c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
								c.setCodeCreationDate(codeCreationDate);
							}
							else{
								c.setSapCodeNo("");
								c.setCodeCreationDate("");
							}
							String sapCrDt=rsList.getString("SAP_CREATION_DATE");
							if(sapCrDt!=null){
								if(!sapCrDt.equals("")){
									c.setSapCreationDate(codeCreationDate);
								}else
									c.setSapCreationDate("");
									
							}else{
								c.setSapCreationDate("");
							}
							String lastApprover=rsList.getString("last_approver");
							if(!lastApprover.equals("No"))
							 c.setLastApprover(rsList.getString("last_approver"));
							else
								c.setLastApprover("");	
							String pendingAppr=rsList.getString("pending_approver");
							if(!pendingAppr.equals("No"))
								c.setPendingApprover(rsList.getString("pending_approver"));
							else
								c.setPendingApprover("");
							
							
							listOfVendorCode.add(c);
						}
	 			
	 					
	 					request.setAttribute("listOfVendorCode", listOfVendorCode);
		 		
		 					masterForm.setTotalRecords(totalRecords);
		 					masterForm.setStartRecord(startRecord);
		 					masterForm.setEndRecord(endRecord);
		 							request.setAttribute("nextButton", "nextButton");
		 							if(startRecord!=1)
		 							request.setAttribute("previousButton", "previousButton");
		 							request.setAttribute("displayRecordNo", "displayRecordNo");
		 							if(listOfVendorCode.size()<10)
		 							{
		 								masterForm.setStartRecord(1);
		 								request.setAttribute("previousButton", "");
		 								request.setAttribute("disablePreviousButton", "disablePreviousButton");
		 							}
				 
				 if(endRecord==totalRecords)
				 {
					 request.setAttribute("nextButton", "");
						request.setAttribute("disableNextButton", "disableNextButton");
				 }
				 request.setAttribute("displayRecordNo", "displayRecordNo");
		 		
		 		
			}
		  if(searchType.equals("Customer Master")){
			  LinkedList listOfCustomerCode=new LinkedList();
		    	 int totalRecords=masterForm.getTotalRecords();//21
		 		int endRecord=masterForm.getStartRecord()-1;//20
		 		int startRecord=masterForm.getStartRecord()-10;//11
		 		
		 		if(startRecord==1)
		 		{
		 			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		 			endRecord=10;
		 		}
		 		
		 		
		 		masterForm.setTotalRecords(totalRecords);
		 		masterForm.setStartRecord(1);
		 		masterForm.setEndRecord(10);
		 		String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.ID) AS RowNum, cus.REQUEST_NO,cus.REQUEST_DATE,cus.NAME,cus.CITY,emp.LOCID,"
	 					+ "emp.EMP_FULLNAME,cus.Approve_Status,cus.SAP_CODE_NO,cus.last_approver,cus.pending_approver,cus.SAP_CREATION_DATE from CUSTOMER_MASTER_M cus,emp_official_info emp,Location loc "
	 					+ "where cus.CREATED_BY=emp.PERNR and emp.LOCID =loc.LOCATION_CODE ";
	 					if(!Location.equalsIgnoreCase("")){	
	 						query=query+ " and loc.LOCID='"+Location+"' ";
	 					}
	 					if(!Materialcode.equalsIgnoreCase("")){	
	 						query=query+ " and cus.ACCOUNT_GROUP_ID='"+Materialcode+"'";
	 					}
	 					if(!From.equalsIgnoreCase("")){	
	 					query=query+ " and cus.REQUEST_DATE between '"+From+"' and '"+To+"'";
	 					}
	 					
	 					/*if(!From1.equalsIgnoreCase("")){	
	 						query=query+ " and (cus.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
	 					}*/
	 					
	 					if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
	 							{
	 								query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
	 							}
	 					
	 					if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
	 					{
	 						query=query+ " and  Approve_Status = 'Completed' ";
	 					}
	 					
	 					if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
	 					{
	 						query=query+ " and  Approve_Status = 'Rejected' ";
	 					}
	 					
	 					if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
	 					{
	 						query=query+ " and  Approve_Type  in ('Submited') ";
	 					}
	 					if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
	 					{
	 						query=query+ " and  Approve_Type  in ('In Process') ";
	 					}
	 					
	 					/*if(!masterForm.getApprover().equalsIgnoreCase(""))
	 					{
	 						query=query+ " and  request_no in (select Req_Id "
	 								+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Customer Master') ";
	 					}*/
	 					
	 							
	 					query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
	 					ResultSet rsList=ad.selectQuery(query);
	 					while(rsList.next()){
	 						CustomerMasterForm c=new CustomerMasterForm();
	 						c.setRequestNo(rsList.getString("REQUEST_NO"));
	 						c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
	 						c.setCustomerName(rsList.getString("NAME"));
	 						c.setCity(rsList.getString("CITY"));
	 						c.setLocationId(rsList.getString("LOCID"));
	 						c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
	 						c.setApproveType(rsList.getString("Approve_Status"));
	 						String sapcodeno=rsList.getString("SAP_CODE_NO");
	 						if(sapcodeno!=null){
	 						 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 						 c.setSapCreationDate("");
	 						}
	 						else
	 							c.setSapCodeNo("");	
	 						
	 						String sapCodeNo=rsList.getString("SAP_CODE_NO");
	 						String requestNo=rsList.getString("REQUEST_NO");
	 						String approveStatus=rsList.getString("Approve_Status");
	 						String approverList="";
	 						String codeCreationDate="";
	 						
	 						String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
	 						+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Customer Master'  "
	 						+ " and all_r.Approved_Persons=emp.PERNR";
	 						ResultSet rsApp=ad.selectQuery(approverDet);
	 						try{
	 							while(rsApp.next()){
	 								approverList=approverList+rsApp.getString("EMP_FULLNAME");
	 								String status=rsApp.getString("Req_Status");
	 								if(status.equals("Approved")||status.equals("Completed"))
	 									approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
	 								else if(status.equals("Rejected"))
	 									approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
	 								
	 								if(status.equals("Completed"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
	 								else if(status.equals("Rejected"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
	 									
	 							}
	 							//if(!approverList.equals(""))
	 							//approverList=approverList.substring(0, (approverList.length()-1));
	 							c.setApproversList(approverList);
	 						}catch(Exception e){
	 							e.printStackTrace();
	 						}
	 						 
	 						if(sapCodeNo!=null){
	 							c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 							c.setCodeCreationDate(codeCreationDate);
	 						}
	 						else{
	 							c.setSapCodeNo("");
	 							c.setCodeCreationDate("");
	 						}
	 						String sapCrDt=rsList.getString("SAP_CREATION_DATE");
	 						if(sapCrDt!=null){
	 							if(!sapCrDt.equals("")){
	 								c.setSapCreationDate(codeCreationDate);
	 							}else
	 								c.setSapCreationDate("");
	 								
	 						}else{
	 							c.setSapCreationDate("");
	 						}
	 						String lastApprover=rsList.getString("last_approver");
	 						if(lastApprover!=null)
	 						if(!lastApprover.equals("No"))
	 						 c.setLastApprover(rsList.getString("last_approver"));
	 						else
	 							c.setLastApprover("");	
	 						String pendingAppr=rsList.getString("pending_approver");
	 						if(pendingAppr!=null)
	 						if(!pendingAppr.equals("No"))
	 							c.setPendingApprover(rsList.getString("pending_approver"));
	 						else
	 							c.setPendingApprover("");
	 						listOfCustomerCode.add(c);
	 					}
	 					
	 					request.setAttribute("listOfCustomerCode", listOfCustomerCode);
		 		
		 					masterForm.setTotalRecords(totalRecords);
		 					masterForm.setStartRecord(startRecord);
		 					masterForm.setEndRecord(endRecord);
		 							request.setAttribute("nextButton", "nextButton");
		 							if(startRecord!=1)
		 							request.setAttribute("previousButton", "previousButton");
		 							request.setAttribute("displayRecordNo", "displayRecordNo");
		 							if(listOfCustomerCode.size()<10)
		 							{
		 								masterForm.setStartRecord(1);
		 								request.setAttribute("previousButton", "");
		 								request.setAttribute("disablePreviousButton", "disablePreviousButton");
		 							}
				 
				 if(endRecord==totalRecords)
				 {
					 request.setAttribute("nextButton", "");
						request.setAttribute("disableNextButton", "disableNextButton");
				 }
				 request.setAttribute("displayRecordNo", "displayRecordNo");
		 		
		 		
			}
	     if(searchType.equals("Material Master")){
	    	 LinkedList listOfMaterialCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
	 		int endRecord=masterForm.getStartRecord()-1;//20
	 		int startRecord=masterForm.getStartRecord()-10;//11
	 		
	 		if(startRecord==1)
	 		{
	 			request.setAttribute("disablePreviousButton", "disablePreviousButton");
	 			endRecord=10;
	 		}
	 		
	 		
	 		masterForm.setTotalRecords(totalRecords);
	 		masterForm.setStartRecord(1);
	 		masterForm.setEndRecord(10);
	 			
	 			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO ) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,emp.EMP_FULLNAME,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
	 					"m.URL,L.location_code,m.REPORT_URL,mType.M_DESC,m.last_approver,m.pending_approver,m.SAP_CODE_NO,m.SAP_CREATION_DATE from material_code_request "
	 					+ "as m,Location as l ,MATERIAL_TYPE as mType,emp_official_info emp  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and emp.PERNR=m.CREATED_BY  ";
	 					if(!Location.equalsIgnoreCase("")){	
	 						query=query+ " and m.LOCATION_ID='"+Location+"'";
	 					}
	 					if(!Materialcode.equalsIgnoreCase("")){	
	 					query=query+ " and m.Type='"+Materialcode+"'";
	 					}
	 					if(!From.equalsIgnoreCase("")){	
	 					query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
	 					}
	 					

						/*if(!From1.equalsIgnoreCase("")){	
					query=query+ " and (m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}
				*/
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
					query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				

				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					query=query+ " and  Approve_Type = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					query=query+ " and  Approve_Type = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
				{
					query=query+ " and  Approve_Type  in ('Submited') ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					query=query+ " and  Approve_Type  in ('In Process') ";
				}
				
				/*if(!masterForm.getApprover().equalsIgnoreCase(""))
				{
					query=query+ " and  request_no in (select Req_Id "
							+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Material Master') ";
				}*/
				
	 					query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
	 					ResultSet rsList=ad.selectQuery(query);
	 					while(rsList.next()){
	 						MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
	 						materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
	 						
	 						String requestDate=rsList.getString("REQUEST_DATE");
	 						String req[]=requestDate.split(" ");
	 						requestDate=req[0];
	 						String a[]=requestDate.split("-");
	 						requestDate=a[2]+"/"+a[1]+"/"+a[0];
	 						materialForm.setRequestDate(requestDate);
	 						materialForm.setRequesterName(rsList.getString("EMP_FULLNAME"));
	 						materialForm.setLocationId(rsList.getString("location_code"));
	 						materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
	 						materialForm.setApproveType(rsList.getString("Approve_Type"));
	 						materialForm.setmType(rsList.getString("Type"));
	 						
	 						materialForm.setReqMatType(rsList.getString("Type"));
	 						String sapCodeNo=rsList.getString("SAP_CODE_NO");
	 						String requestNo=rsList.getString("REQUEST_NO");
	 						String matType=rsList.getString("Type");
	 						String approveStatus=rsList.getString("Approve_Type");
	 						String approverList="";
	 						String codeCreationDate="";
	 						String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
	 						+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Material Master' and "
	 						+ "type='"+matType+"' and all_r.Approved_Persons=emp.PERNR";
	 						ResultSet rsApp=ad.selectQuery(approverDet);
	 						try{
	 							while(rsApp.next()){
	 								approverList=approverList+rsApp.getString("EMP_FULLNAME");
	 								String status=rsApp.getString("Req_Status");
	 								if(status.equals("Approved")||status.equals("Completed"))
	 									approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
	 								else if(status.equals("Rejected"))
	 									approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
	 								
	 								if(status.equals("Completed"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
	 								else if(status.equals("Rejected"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
	 									
	 							}
	 							//if(!approverList.equals(""))
	 							//approverList=approverList.substring(0, (approverList.length()-1));
	 							materialForm.setApproversList(approverList);
	 						}catch(Exception e){
	 							e.printStackTrace();
	 						}
	 						 
	 						if(sapCodeNo!=null){
	 							materialForm.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 							materialForm.setCodeCreationDate(codeCreationDate);
	 						}
	 						else{
	 							materialForm.setSapCodeNo("");
	 							materialForm.setCodeCreationDate("");
	 						}
	 						String sapCrDt=rsList.getString("SAP_CREATION_DATE");
	 						if(sapCrDt!=null){
	 							if(!sapCrDt.equals("")){
	 								materialForm.setSapCreationDate(codeCreationDate);
	 							}else
	 								materialForm.setSapCreationDate("");
	 								
	 						}else{
	 							materialForm.setSapCreationDate("");
	 						}
	 						String lastApprover=rsList.getString("last_approver");
	 						if(!lastApprover.equals("No"))
	 						 materialForm.setLastApprover(rsList.getString("last_approver"));
	 						else
	 							materialForm.setLastApprover("");	
	 						String pendingAppr=rsList.getString("pending_approver");
	 						if(!pendingAppr.equals("No"))
	 							materialForm.setPendingApprover(rsList.getString("pending_approver"));
	 						else
	 							materialForm.setPendingApprover("");
	 						//prevappr
	 						String lastappr="select CONVERT(varchar(10),all_r.approved_date,103)+' '+ CONVERT(varchar(5),all_r.approved_date,108) as approved_date1,Actual_Approver,approved_date,emp.EMP_FULLNAME from All_Request as all_r,emp_official_info emp where Req_Id='"+requestNo+"' and Req_Type='Material Master'  and emp.PERNR=all_r.Actual_Approver order by approved_date desc";
	 						ResultSet rslast=ad.selectQuery(lastappr);
	 						int ab=0;
	 						String actual="";
	 						int chk=0;
	 						while(rslast.next())
	 						{
	 							
	 							materialForm.setPrevApprover(rslast.getString("EMP_FULLNAME"));
	 							materialForm.setPrevApproverDate(rslast.getString("approved_date1"));
	 							ab=ab+1;
	 							if(!actual.equalsIgnoreCase(rslast.getString("Actual_Approver"))&&chk==1)
	 							{
	 								break;
	 							}
	 							
	 							
	 							actual=rslast.getString("Actual_Approver");
	 							chk=1;
	 							
	 						}
	 						listOfMaterialCode.add(materialForm);
	 						}
	 					
	 					request.setAttribute("listOfMaterials", listOfMaterialCode);
	 		
	 					masterForm.setTotalRecords(totalRecords);
	 					masterForm.setStartRecord(startRecord);
	 					masterForm.setEndRecord(endRecord);
	 							request.setAttribute("nextButton", "nextButton");
	 							if(startRecord!=1)
	 							request.setAttribute("previousButton", "previousButton");
	 							request.setAttribute("displayRecordNo", "displayRecordNo");
	 							if(listOfMaterialCode.size()<10)
	 							{
	 								masterForm.setStartRecord(1);
	 								request.setAttribute("previousButton", "");
	 								request.setAttribute("disablePreviousButton", "disablePreviousButton");
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
		return mapping.findForward("display");
	}
	
	public ActionForward nextMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SAPReportForm masterForm=(SAPReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		try{
			  LinkedList materTypeIDList=new LinkedList();
				LinkedList materialTypeIdValueList=new LinkedList();
				String getMaterials="select * from MATERIAL_TYPE";
				ResultSet rsMaterial=ad.selectQuery(getMaterials);
				while(rsMaterial.next())
				{
					materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
					materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
				}
				masterForm.setMaterTypeIDList(materTypeIDList);
				masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
				ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				if(locationList.size()==0){
					locationList.add("");
					locationLabelList.add("");
				}
				masterForm.setLocationIdList(locationList);
				masterForm.setLocationLabelList(locationLabelList);
				//vendor type
				 ArrayList accountGroupList=new ArrayList();
					ArrayList accountGroupLabelList=new ArrayList();
				 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
			ArrayList subLinkIdList = new ArrayList();
			ArrayList subLinkValueList = new ArrayList();
			while(rs.next()) {
				accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
				accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
			}
			masterForm.setAccountGroupList(accountGroupList);
			masterForm.setAccountGroupLabelList(accountGroupLabelList);
			
			ArrayList techList=new ArrayList();
			ArrayList techLabelList=new ArrayList();
			ResultSet rs13 = ad.selectQuery(" select app,emp.EMP_FULLNAME from ( select Approver_Id as app from Material_Approvers where role ='Creator' and Approver_Id!='' group by Approver_Id union select Parllel_Approver_1 from Material_Approvers where role ='Creator' and Parllel_Approver_1!='' group by Parllel_Approver_1 union select Parllel_Approver_2 from Material_Approvers where role ='Creator' and Parllel_Approver_2!='' group by Parllel_Approver_2)t,emp_official_info as emp where emp.PERNR=App  and emp.ACTIVE =1 order  by emp.EMP_FULLNAME"); 
					try {
						while(rs13.next()) {
							techList.add(rs13.getString("App"));
							techLabelList.add(rs13.getString("EMP_FULLNAME"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					masterForm.setTechidList(techList);
					masterForm.setTechnameList(techLabelList);
				String reqnumber=masterForm.getRequestNumber();
				String From=masterForm.getFromDate();
				String To=masterForm.getToDate();
				
				String From1=masterForm.getFromDate1();
				String To1=masterForm.getToDate1();
				
				
				if(From!=null){
					if(!From.equalsIgnoreCase("")){
					  String b[]=From.split("/");
					  From=b[2]+"-"+b[1]+"-"+b[0];
					}
					}
					//String To=codeRequestForm.getToDate();
				
					if(To!=null){
					if(!To.equalsIgnoreCase("")){
					  String b1[]=To.split("/");
					  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
					}
					}
					
					
					if(From1!=null){
						if(!From1.equalsIgnoreCase("")){
						  String b[]=From1.split("/");
						  From1=b[2]+"-"+b[1]+"-"+b[0];
						}
						}
						//String To=codeRequestForm.getToDate();
					
						if(To1!=null){
						if(!To1.equalsIgnoreCase("")){
						  String b1[]=To1.split("/");
						  To1=b1[2]+"-"+b1[1]+"-"+b1[0];	
						}
						}
				
		 String Location=masterForm.getLocationId();
	     String Materialcode=masterForm.getMaterialCodeLists();
	     String searchType=masterForm.getSearchType();
	     if(searchType.equals("Service Master")){
	    	 LinkedList listOfServiceCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
	 		int startRecord=masterForm.getStartRecord();//11
	 		int endRecord=masterForm.getEndRecord();
	 	
	 		if(totalRecords>endRecord)
	 		{
	 			if(totalRecords==endRecord)
	 			{
	 				startRecord=startRecord;
	 				endRecord=totalRecords;
	 			}
	 			if(totalRecords>endRecord)
	 			{
	 			startRecord=endRecord+1;
	 			endRecord=endRecord+10;
	 			}
	 			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY s.request_no) AS RowNum, s.request_no,s.request_date,"
	 					+ "loc.LOCATION_CODE,emp.EMP_FULLNAME,s.service_description,s.last_approver,s.pending_approver,s.app_satus,s.SAP_CODE_NO,s.SAP_CREATION_DATE from SERVICE_MASTER s,Location loc,"
	 					+ "emp_official_info emp where s.CREATED_BY=emp.PERNR and s.plant_code=loc.LOCID   ";
	 							if(!Location.equalsIgnoreCase("")){	
	 								query=query+ " and  loc.LOCID='"+Location+"'";
	 							}
	 							if(!Materialcode.equalsIgnoreCase("")){	
	 								query=query+ " and s.service_catagory='"+Materialcode+"'";
	 							}
	 							if(!From.equalsIgnoreCase("")){	
	 							query=query+ " and (s.CREATED_DATE between '"+From+"' and '"+To+"') ";
	 							}
	 							
	 							/*if(!From1.equalsIgnoreCase("")){	
	 								query=query+ " and (s.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
	 							}*/
	 							
	 							if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
	 							{
	 								query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
	 							}
	 							
	 							if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
	 							{
	 								query=query+ " and  app_satus = 'Completed' ";
	 							}
	 							
	 							if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
	 							{
	 								query=query+ " and  app_satus = 'Rejected' ";
	 							}
	 							
	 							if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
	 							{
	 								query=query+ " and  Approve_Type  in ('Submited') ";
	 							}
	 							if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
	 							{
	 								query=query+ " and  Approve_Type  in ('In Process') ";
	 							}
	 							
	 							/*if(!masterForm.getApprover().equalsIgnoreCase(""))
	 							{
	 								query=query+ " and  request_no in (select Req_Id "
	 										+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Service Master') ";
	 							}*/
	 							
	 							
	 							query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by request_no ";
	 							ResultSet rsList=ad.selectQuery(query);
	 							while(rsList.next()){
	 								ServiceMasterRequestForm c=new ServiceMasterRequestForm();
	 								c.setR_no(rsList.getInt("request_no"));
	 								c.setRequestDate(rsList.getString("request_date"));
	 								
	 								c.setLocationID(rsList.getString("LOCATION_CODE"));
	 								c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
	 								c.setServiceDescription(rsList.getString("service_description"));
	 								c.setApproveType(rsList.getString("app_satus"));
	 								String sapcodeno=rsList.getString("SAP_CODE_NO");
	 								if(sapcodeno!=null){
	 								 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 								 c.setSapCreationDate("");
	 								}
	 								else
	 									c.setSapCodeNo("");	
	 								
	 								String sapCodeNo=rsList.getString("SAP_CODE_NO");
	 								String requestNo=rsList.getString("request_no");
	 								String approveStatus=rsList.getString("app_satus");
	 								String approverList="";
	 								String codeCreationDate="";
	 								
	 								String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
	 								+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Service Master'  "
	 								+ " and all_r.Approved_Persons=emp.PERNR";
	 								ResultSet rsApp=ad.selectQuery(approverDet);
	 								try{
	 									while(rsApp.next()){
	 										approverList=approverList+rsApp.getString("EMP_FULLNAME");
	 										String status=rsApp.getString("Req_Status");
	 										if(status.equals("Approved")||status.equals("Completed"))
	 											approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
	 										else if(status.equals("Rejected"))
	 											approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
	 										
	 										if(status.equals("Completed"))
	 											codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
	 										else if(status.equals("Rejected"))
	 											codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
	 											
	 									}
	 									//if(!approverList.equals(""))
	 									//approverList=approverList.substring(0, (approverList.length()-1));
	 									c.setApproversList(approverList);
	 								}catch(Exception e){
	 									e.printStackTrace();
	 								}
	 								 
	 								if(sapCodeNo!=null){
	 									c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 									c.setCodeCreationDate(codeCreationDate);
	 								}
	 								else{
	 									c.setSapCodeNo("");
	 									c.setCodeCreationDate("");
	 								}
	 								String sapCrDt=rsList.getString("SAP_CREATION_DATE");
	 								if(sapCrDt!=null){
	 									if(!sapCrDt.equals("")){
	 										c.setSapCreationDate(codeCreationDate);
	 									}else
	 										c.setSapCreationDate("");
	 										
	 								}else{
	 									c.setSapCreationDate("");
	 								}
	 								String lastApprover=rsList.getString("last_approver");
	 								if(!lastApprover.equals("No"))
	 								 c.setLastApprover(rsList.getString("last_approver"));
	 								else
	 									c.setLastApprover("");	
	 								String pendingAppr=rsList.getString("pending_approver");
	 								if(!pendingAppr.equals("No"))
	 									c.setPendingApprover(rsList.getString("pending_approver"));
	 								else
	 									c.setPendingApprover("");
	 								
	 								
	 								listOfServiceCode.add(c);
	 							}
	 			
	 					
	 					request.setAttribute("listOfServiceCode", listOfServiceCode);
	 		}
	 		 if(listOfServiceCode.size()!=0)
				{
	 			masterForm.setTotalRecords(totalRecords);
	 			masterForm.setStartRecord(startRecord);
	 			masterForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					masterForm.setTotalRecords(totalRecords);
					masterForm.setStartRecord(start);
					masterForm.setEndRecord(end);
					
				}
			 if(listOfServiceCode.size()<10)
			 {
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(startRecord+listOfServiceCode.size()-1);
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
	     if(searchType.equals("Vendor Master")){
	    	 LinkedList listOfVendorCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
	 		int startRecord=masterForm.getStartRecord();//11
	 		int endRecord=masterForm.getEndRecord();
	 	
	 		if(totalRecords>endRecord)
	 		{
	 			if(totalRecords==endRecord)
	 			{
	 				startRecord=startRecord;
	 				endRecord=totalRecords;
	 			}
	 			if(totalRecords>endRecord)
	 			{
	 			startRecord=endRecord+1;
	 			endRecord=endRecord+10;
	 			}
	 			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.REQUEST_NO ) AS RowNum, v.REQUEST_NO,v.REQUEST_DATE,emp.LOCID,"
						+ "emp.EMP_FULLNAME,v.NAME,v.CITY,v.Approve_Status,v.last_approver,"
						+ "v.pending_approver,v.SAP_CODE_NO,v.SAP_CREATION_DATE from vendor_master_m v,emp_official_info emp,Location loc "
						+ "where v.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID   ";
						if(!Location.equalsIgnoreCase("")){	
							query=query+ " and  loc.LOCID='"+Location+"'";
						}
						if(!Materialcode.equalsIgnoreCase("")){	
							query=query+ " and v.ACCOUNT_GROUP_ID='"+Materialcode+"'";
						}
						if(!Materialcode.equalsIgnoreCase("")){	
							query=query+ " and v.ACCOUNT_GROUP_ID='"+Materialcode+"'";
						}
						if(!From.equalsIgnoreCase("")){	
						query=query+ " and v.REQUEST_DATE between '"+From+"' and '"+To+"'";
						}
						
						/*if(!From1.equalsIgnoreCase("")){	
							query=query+ " and (v.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
						}*/
						
						if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
						{
						query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
						}
						
						if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
						{
							query=query+ " and  Approve_Status = 'Completed' ";
						}
						
						if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
						{
							query=query+ " and  Approve_Status = 'Rejected' ";
						}
						
						if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
						{
							query=query+ " and  Approve_Type  in ('Submited') ";
						}
						if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
						{
							query=query+ " and  Approve_Type  in ('In Process') ";
						}
						
						/*if(!masterForm.getApprover().equalsIgnoreCase(""))
						{
							query=query+ " and  request_no in (select Req_Id "
									+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Vendor Master') ";
						}
						*/
						query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
						ResultSet rsList=ad.selectQuery(query);
						while(rsList.next()){
							VendorMasterRequestForm c=new VendorMasterRequestForm();
							c.setRequestNo(rsList.getInt("REQUEST_NO"));
							c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
							c.setName(rsList.getString("NAME"));
							c.setCity(rsList.getString("CITY"));
							c.setLocationId(rsList.getString("LOCID"));
							c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
							c.setApproveType(rsList.getString("Approve_Status"));
							String sapcodeno=rsList.getString("SAP_CODE_NO");
							if(sapcodeno!=null){
							 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
							 c.setSapCreationDate("");
							}
							else
								c.setSapCodeNo("");	
							
							String sapCodeNo=rsList.getString("SAP_CODE_NO");
							String requestNo=rsList.getString("REQUEST_NO");
							String approveStatus=rsList.getString("Approve_Status");
							String approverList="";
							String codeCreationDate="";
							
							String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
							+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Vendor Master'  "
							+ " and all_r.Approved_Persons=emp.PERNR";
							ResultSet rsApp=ad.selectQuery(approverDet);
							try{
								while(rsApp.next()){
									approverList=approverList+rsApp.getString("EMP_FULLNAME");
									String status=rsApp.getString("Req_Status");
									if(status.equals("Approved")||status.equals("Completed"))
										approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
									else if(status.equals("Rejected"))
										approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
									
									if(status.equals("Completed"))
										codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
									else if(status.equals("Rejected"))
										codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
										
								}
								//if(!approverList.equals(""))
								//approverList=approverList.substring(0, (approverList.length()-1));
								c.setApproversList(approverList);
							}catch(Exception e){
								e.printStackTrace();
							}
							 
							if(sapCodeNo!=null){
								c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
								c.setCodeCreationDate(codeCreationDate);
							}
							else{
								c.setSapCodeNo("");
								c.setCodeCreationDate("");
							}
							String sapCrDt=rsList.getString("SAP_CREATION_DATE");
							if(sapCrDt!=null){
								if(!sapCrDt.equals("")){
									c.setSapCreationDate(codeCreationDate);
								}else
									c.setSapCreationDate("");
									
							}else{
								c.setSapCreationDate("");
							}
							String lastApprover=rsList.getString("last_approver");
							if(!lastApprover.equals("No"))
							 c.setLastApprover(rsList.getString("last_approver"));
							else
								c.setLastApprover("");	
							String pendingAppr=rsList.getString("pending_approver");
							if(!pendingAppr.equals("No"))
								c.setPendingApprover(rsList.getString("pending_approver"));
							else
								c.setPendingApprover("");
							
							
							listOfVendorCode.add(c);
						}
	 			
	 					
	 					request.setAttribute("listOfVendorCode", listOfVendorCode);
	 		}
	 		 if(listOfVendorCode.size()!=0)
				{
	 			masterForm.setTotalRecords(totalRecords);
	 			masterForm.setStartRecord(startRecord);
	 			masterForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					masterForm.setTotalRecords(totalRecords);
					masterForm.setStartRecord(start);
					masterForm.setEndRecord(end);
					
				}
			 if(listOfVendorCode.size()<10)
			 {
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(startRecord+listOfVendorCode.size()-1);
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
	     if(searchType.equals("Customer Master")){
	    	 LinkedList listOfCustomerCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
	 		int startRecord=masterForm.getStartRecord();//11
	 		int endRecord=masterForm.getEndRecord();
	 	
	 		if(totalRecords>endRecord)
	 		{
	 			if(totalRecords==endRecord)
	 			{
	 				startRecord=startRecord;
	 				endRecord=totalRecords;
	 			}
	 			if(totalRecords>endRecord)
	 			{
	 			startRecord=endRecord+1;
	 			endRecord=endRecord+10;
	 			}
	 			
	 			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.ID) AS RowNum, cus.REQUEST_NO,cus.REQUEST_DATE,cus.NAME,cus.CITY,emp.LOCID,"
	 					+ "emp.EMP_FULLNAME,cus.Approve_Status,cus.SAP_CODE_NO,cus.last_approver,cus.pending_approver,cus.SAP_CREATION_DATE from CUSTOMER_MASTER_M cus,emp_official_info emp,Location loc "
	 					+ "where cus.CREATED_BY=emp.PERNR and emp.LOCID =loc.LOCATION_CODE ";
	 					if(!Location.equalsIgnoreCase("")){	
	 						query=query+ " and loc.LOCID='"+Location+"' ";
	 					}
	 					if(!Materialcode.equalsIgnoreCase("")){	
	 						query=query+ " and cus.ACCOUNT_GROUP_ID='"+Materialcode+"'";
	 					}
	 					if(!From.equalsIgnoreCase("")){	
	 					query=query+ " and cus.REQUEST_DATE between '"+From+"' and '"+To+"'";
	 					}
	 					
	 					/*if(!From1.equalsIgnoreCase("")){	
	 						query=query+ " and (cus.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
	 					}*/
	 					
	 					if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
	 							{
	 								query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
	 							}
	 					
	 					if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
	 					{
	 						query=query+ " and  Approve_Status = 'Completed' ";
	 					}
	 					
	 					if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
	 					{
	 						query=query+ " and  Approve_Status = 'Rejected' ";
	 					}
	 					
	 					if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
	 					{
	 						query=query+ " and  Approve_Type  in ('Submited') ";
	 					}
	 					if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
	 					{
	 						query=query+ " and  Approve_Type  in ('In Process') ";
	 					}
	 					
	 					/*if(!masterForm.getApprover().equalsIgnoreCase(""))
	 					{
	 						query=query+ " and  request_no in (select Req_Id "
	 								+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Customer Master') ";
	 					}*/
	 							
	 					query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
	 					ResultSet rsList=ad.selectQuery(query);
	 					while(rsList.next()){
	 						CustomerMasterForm c=new CustomerMasterForm();
	 						c.setRequestNo(rsList.getString("REQUEST_NO"));
	 						c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
	 						c.setCustomerName(rsList.getString("NAME"));
	 						c.setCity(rsList.getString("CITY"));
	 						c.setLocationId(rsList.getString("LOCID"));
	 						c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
	 						c.setApproveType(rsList.getString("Approve_Status"));
	 						String sapcodeno=rsList.getString("SAP_CODE_NO");
	 						if(sapcodeno!=null){
	 						 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 						 c.setSapCreationDate("");
	 						}
	 						else
	 							c.setSapCodeNo("");	
	 						
	 						String sapCodeNo=rsList.getString("SAP_CODE_NO");
	 						String requestNo=rsList.getString("REQUEST_NO");
	 						String approveStatus=rsList.getString("Approve_Status");
	 						String approverList="";
	 						String codeCreationDate="";
	 						
	 						String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
	 						+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Customer Master'  "
	 						+ " and all_r.Approved_Persons=emp.PERNR";
	 						ResultSet rsApp=ad.selectQuery(approverDet);
	 						try{
	 							while(rsApp.next()){
	 								approverList=approverList+rsApp.getString("EMP_FULLNAME");
	 								String status=rsApp.getString("Req_Status");
	 								if(status.equals("Approved")||status.equals("Completed"))
	 									approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
	 								else if(status.equals("Rejected"))
	 									approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
	 								
	 								if(status.equals("Completed"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
	 								else if(status.equals("Rejected"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
	 									
	 							}
	 							//if(!approverList.equals(""))
	 							//approverList=approverList.substring(0, (approverList.length()-1));
	 							c.setApproversList(approverList);
	 						}catch(Exception e){
	 							e.printStackTrace();
	 						}
	 						 
	 						if(sapCodeNo!=null){
	 							c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 							c.setCodeCreationDate(codeCreationDate);
	 						}
	 						else{
	 							c.setSapCodeNo("");
	 							c.setCodeCreationDate("");
	 						}
	 						String sapCrDt=rsList.getString("SAP_CREATION_DATE");
	 						if(sapCrDt!=null){
	 							if(!sapCrDt.equals("")){
	 								c.setSapCreationDate(codeCreationDate);
	 							}else
	 								c.setSapCreationDate("");
	 								
	 						}else{
	 							c.setSapCreationDate("");
	 						}
	 						String lastApprover=rsList.getString("last_approver");
	 						if(lastApprover!=null)
	 						if(!lastApprover.equals("No"))
	 						 c.setLastApprover(rsList.getString("last_approver"));
	 						else
	 							c.setLastApprover("");	
	 						String pendingAppr=rsList.getString("pending_approver");
	 						if(pendingAppr!=null)
	 						if(!pendingAppr.equals("No"))
	 							c.setPendingApprover(rsList.getString("pending_approver"));
	 						else
	 							c.setPendingApprover("");
	 						listOfCustomerCode.add(c);
	 					}
	 					
	 					request.setAttribute("listOfCustomerCode", listOfCustomerCode);
	 		}
	 		 if(listOfCustomerCode.size()!=0)
				{
	 			masterForm.setTotalRecords(totalRecords);
	 			masterForm.setStartRecord(startRecord);
	 			masterForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					masterForm.setTotalRecords(totalRecords);
					masterForm.setStartRecord(start);
					masterForm.setEndRecord(end);
					
				}
			 if(listOfCustomerCode.size()<10)
			 {
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(startRecord+listOfCustomerCode.size()-1);
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
	     
	     if(searchType.equals("Material Master")){
	    	 LinkedList listOfMaterialCode=new LinkedList();
	    	 int totalRecords=masterForm.getTotalRecords();//21
	 		int startRecord=masterForm.getStartRecord();//11
	 		int endRecord=masterForm.getEndRecord();
	 	
	 		if(totalRecords>endRecord)
	 		{
	 			if(totalRecords==endRecord)
	 			{
	 				startRecord=startRecord;
	 				endRecord=totalRecords;
	 			}
	 			if(totalRecords>endRecord)
	 			{
	 			startRecord=endRecord+1;
	 			endRecord=endRecord+10;
	 			}
	 			
	 			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO ) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,emp.EMP_FULLNAME,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
	 					"m.URL,L.location_code,m.REPORT_URL,mType.M_DESC,m.last_approver,m.pending_approver,m.SAP_CODE_NO,m.SAP_CREATION_DATE from material_code_request "
	 					+ "as m,Location as l ,MATERIAL_TYPE as mType,emp_official_info emp  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and emp.PERNR=m.CREATED_BY  ";
	 					if(!Location.equalsIgnoreCase("")){	
	 						query=query+ " and m.LOCATION_ID='"+Location+"'";
	 					}
	 					if(!Materialcode.equalsIgnoreCase("")){	
	 					query=query+ " and m.Type='"+Materialcode+"'";
	 					}
	 					if(!From.equalsIgnoreCase("")){	
	 					query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
	 					}
	 					

						/*if(!From1.equalsIgnoreCase("")){	
					query=query+ " and (m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
					query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				

				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					query=query+ " and  Approve_Type = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					query=query+ " and  Approve_Type = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
				{
					query=query+ " and  Approve_Type  in ('Submited') ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					query=query+ " and  Approve_Type  in ('In Process') ";
				}
				
				/*if(!masterForm.getApprover().equalsIgnoreCase(""))
				{
					query=query+ " and  request_no in (select Req_Id "
							+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Material Master') ";
				}
				*/
	 					query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by REQUEST_NO ";
	 					ResultSet rsList=ad.selectQuery(query);
	 					while(rsList.next()){
	 						MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
	 						materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
	 						
	 						String requestDate=rsList.getString("REQUEST_DATE");
	 						String req[]=requestDate.split(" ");
	 						requestDate=req[0];
	 						String a[]=requestDate.split("-");
	 						requestDate=a[2]+"/"+a[1]+"/"+a[0];
	 						materialForm.setRequestDate(requestDate);
	 						materialForm.setRequesterName(rsList.getString("EMP_FULLNAME"));
	 						materialForm.setLocationId(rsList.getString("location_code"));
	 						materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
	 						materialForm.setApproveType(rsList.getString("Approve_Type"));
	 						materialForm.setmType(rsList.getString("Type"));
	 						
	 						materialForm.setReqMatType(rsList.getString("Type"));
	 						String sapCodeNo=rsList.getString("SAP_CODE_NO");
	 						String requestNo=rsList.getString("REQUEST_NO");
	 						String matType=rsList.getString("Type");
	 						String approveStatus=rsList.getString("Approve_Type");
	 						String approverList="";
	 						String codeCreationDate="";
	 						String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
	 						+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Material Master' and "
	 						+ "type='"+matType+"' and all_r.Approved_Persons=emp.PERNR";
	 						ResultSet rsApp=ad.selectQuery(approverDet);
	 						try{
	 							while(rsApp.next()){
	 								approverList=approverList+rsApp.getString("EMP_FULLNAME");
	 								String status=rsApp.getString("Req_Status");
	 								if(status.equals("Approved")||status.equals("Completed"))
	 									approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
	 								else if(status.equals("Rejected"))
	 									approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
	 								
	 								if(status.equals("Completed"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
	 								else if(status.equals("Rejected"))
	 									codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
	 									
	 							}
	 							//if(!approverList.equals(""))
	 							//approverList=approverList.substring(0, (approverList.length()-1));
	 							materialForm.setApproversList(approverList);
	 						}catch(Exception e){
	 							e.printStackTrace();
	 						}
	 						 
	 						if(sapCodeNo!=null){
	 							materialForm.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	 							materialForm.setCodeCreationDate(codeCreationDate);
	 						}
	 						else{
	 							materialForm.setSapCodeNo("");
	 							materialForm.setCodeCreationDate("");
	 						}
	 						String sapCrDt=rsList.getString("SAP_CREATION_DATE");
	 						if(sapCrDt!=null){
	 							if(!sapCrDt.equals("")){
	 								materialForm.setSapCreationDate(codeCreationDate);
	 							}else
	 								materialForm.setSapCreationDate("");
	 								
	 						}else{
	 							materialForm.setSapCreationDate("");
	 						}
	 						String lastApprover=rsList.getString("last_approver");
	 						if(!lastApprover.equals("No"))
	 						 materialForm.setLastApprover(rsList.getString("last_approver"));
	 						else
	 							materialForm.setLastApprover("");	
	 						String pendingAppr=rsList.getString("pending_approver");
	 						if(!pendingAppr.equals("No"))
	 							materialForm.setPendingApprover(rsList.getString("pending_approver"));
	 						else
	 							materialForm.setPendingApprover("");
	 						//prevappr
	 						String lastappr="select CONVERT(varchar(10),all_r.approved_date,103)+' '+ CONVERT(varchar(5),all_r.approved_date,108) as approved_date1,Actual_Approver,approved_date,emp.EMP_FULLNAME from All_Request as all_r,emp_official_info emp where Req_Id='"+requestNo+"' and Req_Type='Material Master'  and emp.PERNR=all_r.Actual_Approver order by approved_date desc";
	 						ResultSet rslast=ad.selectQuery(lastappr);
	 						int ab=0;
	 						String actual="";
	 						int chk=0;
	 						while(rslast.next())
	 						{
	 							
	 							materialForm.setPrevApprover(rslast.getString("EMP_FULLNAME"));
	 							materialForm.setPrevApproverDate(rslast.getString("approved_date1"));
	 							ab=ab+1;
	 							if(!actual.equalsIgnoreCase(rslast.getString("Actual_Approver"))&&chk==1)
	 							{
	 								break;
	 							}
	 							
	 							
	 							actual=rslast.getString("Actual_Approver");
	 							chk=1;
	 							
	 						}
	 						listOfMaterialCode.add(materialForm);
	 						}
	 					
	 					request.setAttribute("listOfMaterials", listOfMaterialCode);
	 		}
	 		 if(listOfMaterialCode.size()!=0)
				{
	 			masterForm.setTotalRecords(totalRecords);
	 			masterForm.setStartRecord(startRecord);
	 			masterForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					masterForm.setTotalRecords(totalRecords);
					masterForm.setStartRecord(start);
					masterForm.setEndRecord(end);
					
				}
			 if(listOfMaterialCode.size()<10)
			 {
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(startRecord+listOfMaterialCode.size()-1);
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
		return mapping.findForward("display");
	}
	
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SAPReportForm masterForm=(SAPReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		
		try{
		  LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			//vendor type
			 ArrayList accountGroupList=new ArrayList();
				ArrayList accountGroupLabelList=new ArrayList();
			 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		while(rs.next()) {
			accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
			accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
		}
		masterForm.setAccountGroupList(accountGroupList);
		masterForm.setAccountGroupLabelList(accountGroupLabelList);
			String reqnumber=masterForm.getRequestNumber();
			String From=masterForm.getFromDate();
			String To=masterForm.getToDate();
			
			String From1=masterForm.getFromDate1();
			String To1=masterForm.getToDate1();
			if(From!=null){
				if(!From.equalsIgnoreCase("")){
				  String b[]=From.split("/");
				  From=b[2]+"-"+b[1]+"-"+b[0];
				}
				}
				//String To=codeRequestForm.getToDate();
			
				if(To!=null){
				if(!To.equalsIgnoreCase("")){
				  String b1[]=To.split("/");
				  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
				}
				}
				
				if(From1!=null){
					if(!From1.equalsIgnoreCase("")){
					  String b[]=From1.split("/");
					  From1=b[2]+"-"+b[1]+"-"+b[0];
					}
					}
					//String To=codeRequestForm.getToDate();
				
					if(To1!=null){
					if(!To1.equalsIgnoreCase("")){
					  String b1[]=To1.split("/");
					  To1=b1[2]+"-"+b1[1]+"-"+b1[0];	
					}
					}
		int  totalRecords=0;
	    int  startRecord=0;
	    int  endRecord=0;	
	 String Location=masterForm.getLocationId();
     String Materialcode=masterForm.getMaterialCodeLists();
     String searchType=masterForm.getSearchType();
     
     if(searchType.equals("Service Master")){
    	 LinkedList listOfServiceCode=new LinkedList();
    		String  queryCount="select count(*) from SERVICE_MASTER s,Location loc,emp_official_info emp where s.CREATED_BY=emp.PERNR and s.plant_code=loc.LOCID";
			 
			if(!Location.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and loc.LOCID='"+Location+"'";
			}
			if(!Materialcode.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and s.service_catagory='"+Materialcode+"'";
			}
				if(!From.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and (s.CREATED_DATE between '"+From+"' and '"+To+"') ";
				}
				
				
				/*if(!From1.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and (s.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
					queryCount=queryCount+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					queryCount=queryCount+ " and  app_satus = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					queryCount=queryCount+ " and  app_satus = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					queryCount=queryCount+ " and  app_satus not in ('Rejected','Completed','Saved') ";
				}
				
				ResultSet rsQueryCount=ad.selectQuery(queryCount);
				int queryTotal=0;
				 while(rsQueryCount.next())
				 {
					 queryTotal=rsQueryCount.getInt(1);
				 }
				 if(queryTotal>10)
				 {
					 masterForm.setTotalRecords(queryTotal);
				 startRecord=1;
				 endRecord=10;
				 masterForm.setStartRecord(1);
				 masterForm.setEndRecord(10);
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  masterForm.setTotalRecords(totalRecords);
					  masterForm.setStartRecord(1);
					  masterForm.setEndRecord(totalRecords); 
				 }	 
		startRecord=1;
		endRecord=10;
		String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY s.request_no) AS RowNum, s.request_no,s.request_date,"
		+ "loc.LOCATION_CODE,emp.EMP_FULLNAME,s.service_description,s.last_approver,s.pending_approver,s.app_satus,s.service_group,s.valuation_class,s.service_catagory,s.SAP_CODE_NO,s.SAC_Code,s.SAP_CREATION_DATE from SERVICE_MASTER s,Location loc,"
		+ "emp_official_info emp where s.CREATED_BY=emp.PERNR and s.plant_code=loc.LOCID   ";
				if(!Location.equalsIgnoreCase("")){	
					query=query+ " and  loc.LOCID='"+Location+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
					query=query+ " and s.service_catagory='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
				query=query+ " and (s.CREATED_DATE between '"+From+"' and '"+To+"') ";
				}
				
				/*if(!From1.equalsIgnoreCase("")){	
					query=query+ " and (s.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
					query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					query=query+ " and  app_satus = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					query=query+ " and  app_satus = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					query=query+ " and  app_satus not in ('Rejected','Completed','Saved') ";
				}
				
				query=query+ ")as sub Where  sub.RowNum between 1 and "+queryTotal+" order by request_no ";
				ResultSet rsList=ad.selectQuery(query);
				while(rsList.next()){
					ServiceMasterRequestForm c=new ServiceMasterRequestForm();
					c.setR_no(rsList.getInt("request_no"));
					c.setRequestDate(rsList.getString("request_date"));
					
					c.setLocationID(rsList.getString("LOCATION_CODE"));
					c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
					c.setServiceDescription(rsList.getString("service_description"));
					c.setApproveType(rsList.getString("app_satus"));
					c.setSacCode(rsList.getString("SAC_Code"));
					c.setServiceCatagory(rsList.getString("service_catagory"));
					c.setServiceGroup(rsList.getString("service_group"));
					c.setValuationClass(rsList.getString("valuation_class"));
					String sapcodeno=rsList.getString("SAP_CODE_NO");
					if(sapcodeno!=null){
					 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
					 c.setSapCreationDate("");
					}
					else
						c.setSapCodeNo("");	
					
					String sapCodeNo=rsList.getString("SAP_CODE_NO");
					String requestNo=rsList.getString("request_no");
					String approveStatus=rsList.getString("app_satus");
					String approverList="";
					String codeCreationDate="";
					
					String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
					+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Service Master'  "
					+ " and all_r.Approved_Persons=emp.PERNR";
					ResultSet rsApp=ad.selectQuery(approverDet);
					try{
						while(rsApp.next()){
							approverList=approverList+rsApp.getString("EMP_FULLNAME");
							String status=rsApp.getString("Req_Status");
							if(status.equals("Approved")||status.equals("Completed"))
								approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
							else if(status.equals("Rejected"))
								approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
							
							if(status.equals("Completed"))
								codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
							else if(status.equals("Rejected"))
								codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
								
						}
						//if(!approverList.equals(""))
						//approverList=approverList.substring(0, (approverList.length()-1));
						c.setApproversList(approverList);
					}catch(Exception e){
						e.printStackTrace();
					}
					 
					if(sapCodeNo!=null){
						c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
						c.setCodeCreationDate(codeCreationDate);
					}
					else{
						c.setSapCodeNo("");
						c.setCodeCreationDate("");
					}
					String sapCrDt=rsList.getString("SAP_CREATION_DATE");
					if(sapCrDt!=null){
						if(!sapCrDt.equals("")){
							c.setSapCreationDate(codeCreationDate);
						}else
							c.setSapCreationDate("");
							
					}else{
						c.setSapCreationDate("");
					}
					String lastApprover=rsList.getString("last_approver");
					if(!lastApprover.equals("No"))
					 c.setLastApprover(rsList.getString("last_approver"));
					else
						c.setLastApprover("");	
					String pendingAppr=rsList.getString("pending_approver");
					if(!pendingAppr.equals("No"))
						c.setPendingApprover(rsList.getString("pending_approver"));
					else
						c.setPendingApprover("");
					
					
					listOfServiceCode.add(c);
				}
				
				if(!rsList.previous()){
					masterForm.setMessage("No Record Found..");
				request.setAttribute("noCustRecords", "noCustRecords");
				}
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			    request.setAttribute("listOfServiceCode", listOfServiceCode); 
     }
     if(searchType.equals("Vendor Master")){
    	 LinkedList listOfVendorCode=new LinkedList();
			String  queryCount="select COUNT(*) from vendor_master_m v,emp_official_info emp,Location loc where v.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID  ";
				 
			if(!Location.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and loc.LOCID='"+Location+"'";
			}
			if(!Materialcode.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and v.ACCOUNT_GROUP_ID='"+Materialcode+"'";
			}
				if(!From.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and v.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				
				/*if(!From1.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and (v.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
					queryCount=queryCount+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					queryCount=queryCount+ " and  Approve_Status = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					queryCount=queryCount+ " and  Approve_Status = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					queryCount=queryCount+ " and  Approve_Status in ('Rejected','Completed','Saved') ";
				}
				ResultSet rsQueryCount=ad.selectQuery(queryCount);
				int queryTotal=0;
				 while(rsQueryCount.next())
				 {
					 queryTotal=rsQueryCount.getInt(1);
				 }
				 if(queryTotal>10)
				 {
					 masterForm.setTotalRecords(queryTotal);
				 startRecord=1;
				 endRecord=10;
				 masterForm.setStartRecord(1);
				 masterForm.setEndRecord(10);
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  masterForm.setTotalRecords(totalRecords);
					  masterForm.setStartRecord(1);
					  masterForm.setEndRecord(totalRecords); 
				 }	 
		startRecord=1;
		endRecord=10;
		String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.REQUEST_NO ) AS RowNum, v.REQUEST_NO,v.REQUEST_DATE,emp.LOCID,"
				+ "emp.EMP_FULLNAME,v.NAME,v.CITY,v.Approve_Status,v.last_approver,"
				+ "v.pending_approver,v.SAP_CODE_NO,v.SAP_CREATION_DATE from vendor_master_m v,emp_official_info emp,Location loc "
				+ "where v.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID   ";
				if(!Location.equalsIgnoreCase("")){	
					query=query+ " and  loc.LOCID='"+Location+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
					query=query+ " and v.ACCOUNT_GROUP_ID='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
				query=query+ " and v.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				
				/*if(!From1.equalsIgnoreCase("")){	
					query=query+ " and (v.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
					query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					query=query+ " and  Approve_Status = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					query=query+ " and  Approve_Status = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					query=query+ " and  Approve_Status in ('Rejected','Completed','Saved') ";
				}
				
				query=query+ ")as sub Where  sub.RowNum between 1 and "+queryTotal+" order by REQUEST_NO ";
				ResultSet rsList=ad.selectQuery(query);
				while(rsList.next()){
					VendorMasterRequestForm c=new VendorMasterRequestForm();
					c.setRequestNo(rsList.getInt("REQUEST_NO"));
					c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
					c.setName(rsList.getString("NAME"));
					c.setCity(rsList.getString("CITY"));
					c.setLocationId(rsList.getString("LOCID"));
					c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
					c.setApproveType(rsList.getString("Approve_Status"));
					String sapcodeno=rsList.getString("SAP_CODE_NO");
					if(sapcodeno!=null){
					 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
					 c.setSapCreationDate("");
					}
					else
						c.setSapCodeNo("");	
					
					String sapCodeNo=rsList.getString("SAP_CODE_NO");
					String requestNo=rsList.getString("REQUEST_NO");
					String approveStatus=rsList.getString("Approve_Status");
					String approverList="";
					String codeCreationDate="";
					
					String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
					+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Vendor Master'  "
					+ " and all_r.Approved_Persons=emp.PERNR";
					ResultSet rsApp=ad.selectQuery(approverDet);
					try{
						while(rsApp.next()){
							approverList=approverList+rsApp.getString("EMP_FULLNAME");
							String status=rsApp.getString("Req_Status");
							if(status.equals("Approved")||status.equals("Completed"))
								approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
							else if(status.equals("Rejected"))
								approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
							
							if(status.equals("Completed"))
								codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
							else if(status.equals("Rejected"))
								codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
								
						}
						//if(!approverList.equals(""))
						//approverList=approverList.substring(0, (approverList.length()-1));
						c.setApproversList(approverList);
					}catch(Exception e){
						e.printStackTrace();
					}
					 
					if(sapCodeNo!=null){
						c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
						c.setCodeCreationDate(codeCreationDate);
					}
					else{
						c.setSapCodeNo("");
						c.setCodeCreationDate("");
					}
					String sapCrDt=rsList.getString("SAP_CREATION_DATE");
					if(sapCrDt!=null){
						if(!sapCrDt.equals("")){
							c.setSapCreationDate(codeCreationDate);
						}else
							c.setSapCreationDate("");
							
					}else{
						c.setSapCreationDate("");
					}
					String lastApprover=rsList.getString("last_approver");
					if(!lastApprover.equals("No"))
					 c.setLastApprover(rsList.getString("last_approver"));
					else
						c.setLastApprover("");	
					String pendingAppr=rsList.getString("pending_approver");
					if(!pendingAppr.equals("No"))
						c.setPendingApprover(rsList.getString("pending_approver"));
					else
						c.setPendingApprover("");
					
					
					listOfVendorCode.add(c);
				}
				
				if(!rsList.previous()){
					masterForm.setMessage("No Record Found..");
				request.setAttribute("noCustRecords", "noCustRecords");
				}
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			    request.setAttribute("listOfVendorCode", listOfVendorCode);
			
				
     }
     
		 if(searchType.equals("Material Master")){ 
			 LinkedList listOfMaterialCode=new LinkedList();
		String  queryCount="select count(*) from material_code_request as m,Location as l where  m.LOCATION_ID=l.LOCID  ";
			 
		if(!Location.equalsIgnoreCase("")){	
			queryCount=queryCount+ " and m.LOCATION_ID='"+Location+"'";
		}
			if(!Materialcode.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and m.Type='"+Materialcode+"'";
			}
			if(!From.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
			}
			
			/*if(!From1.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and (m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
			}*/
			
			if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
			{
				queryCount=queryCount+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
			}
			

			if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
			{
				queryCount=queryCount+ " and  Approve_Type = 'Completed' ";
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
			{
				queryCount=queryCount+ " and  Approve_Type = 'Rejected' ";
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
			{
				queryCount=queryCount+ " and  Approve_Type  in ('Submited') ";
			}
			if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
			{
				queryCount=queryCount+ " and  Approve_Type  in ('In Process') ";
			}
			ResultSet rsQueryCount=ad.selectQuery(queryCount);
			int queryTotal=0;
			 while(rsQueryCount.next())
			 {
				 queryTotal=rsQueryCount.getInt(1);
			 }
			 if(queryTotal>10)
			 {
				 masterForm.setTotalRecords(queryTotal);
			 startRecord=1;
			 endRecord=10;
			 masterForm.setStartRecord(1);
			 masterForm.setEndRecord(10);
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  masterForm.setTotalRecords(totalRecords);
				  masterForm.setStartRecord(1);
				  masterForm.setEndRecord(totalRecords); 
			 }	 
	startRecord=1;
	endRecord=10;
	//hashmap
	HashMap hy=new HashMap();
	String hash=" select pernr,EMP_FULLNAME from emp_official_info where pernr in (Select CREATED_BY From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO ) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,m.CREATED_BY,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.UNIT_OF_MEAS_ID,m.MATERIAL_GROUP_ID,m.Type,m.Approve_Type," +
"m.URL,L.location_code,m.REPORT_URL,mType.M_DESC,m.last_approver,m.pending_approver,m.SAP_CODE_NO,m.SAP_CREATION_DATE from material_code_request "
+ "as m,Location as l ,MATERIAL_TYPE as mType  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID   ";
if(!Location.equalsIgnoreCase("")){	
	hash=hash+ " and m.LOCATION_ID='"+Location+"'";
}
if(!Materialcode.equalsIgnoreCase("")){	
hash=hash+ " and m.Type='"+Materialcode+"'";
}
if(!From.equalsIgnoreCase("")){	
hash=hash+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
}

/*if(!From1.equalsIgnoreCase("")){	
	hash=hash+ " and (m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
}
*/
if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
{
	hash=hash+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
}


if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
{
	hash=hash+ " and  Approve_Type = 'Completed' ";
}

if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
{
	hash=hash+ " and  Approve_Type = 'Rejected' ";
}

if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
{
	hash=hash+ " and  Approve_Type  in ('Submited') ";
}
if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
{
	hash=hash+ " and  Approve_Type  in ('In Process') ";
}
hash=hash+ ")as sub Where  sub.RowNum between 1 and "+queryTotal+" group by CREATED_BY )order by 1";
ResultSet hs=ad.selectQuery(hash);
while(hs.next())
{
	hy.put(hs.getString("pernr"), hs.getString("EMP_FULLNAME"));
}
	
	
String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO ) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,m.CREATED_BY,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.UNIT_OF_MEAS_ID,m.MATERIAL_GROUP_ID,m.Type,m.Approve_Type," +
"m.URL,L.location_code,m.REPORT_URL,mType.M_DESC,m.last_approver,m.pending_approver,m.SAP_CODE_NO,convert(nvarchar(10),m.SAP_CREATION_DATE,103) as SAP_CREATION_DATE1 from material_code_request "
+ "as m,Location as l ,MATERIAL_TYPE as mType  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID   ";
if(!Location.equalsIgnoreCase("")){	
	query=query+ " and m.LOCATION_ID='"+Location+"'";
}
if(!Materialcode.equalsIgnoreCase("")){	
query=query+ " and m.Type='"+Materialcode+"'";
}
if(!From.equalsIgnoreCase("")){	
query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
}

/*if(!From1.equalsIgnoreCase("")){	
	query=query+ " and (m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
}
*/
if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
{
	query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
}


if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
{
	query=query+ " and  Approve_Type = 'Completed' ";
}

if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
{
	query=query+ " and  Approve_Type = 'Rejected' ";
}

if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
{
	query=query+ " and  Approve_Type  in ('Submited') ";
}
if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
{
	query=query+ " and  Approve_Type  in ('In Process') ";
}
query=query+ ")as sub Where  sub.RowNum between 1 and "+queryTotal+" order by REQUEST_NO ";
ResultSet rsList=ad.selectQuery(query);
while(rsList.next()){
	MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
	materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
	
	String requestDate=rsList.getString("REQUEST_DATE");
	String req[]=requestDate.split(" ");
	requestDate=req[0];
	String a[]=requestDate.split("-");
	requestDate=a[2]+"/"+a[1]+"/"+a[0];
	materialForm.setRequestDate(requestDate);
	materialForm.setRequesterName((String)hy.get(rsList.getString("CREATED_BY")));
	materialForm.setLocationId(rsList.getString("location_code"));
	materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
	materialForm.setApproveType(rsList.getString("Approve_Type"));
	materialForm.setmType(rsList.getString("Type"));
	materialForm.setMaterialDesc(rsList.getString("MATERIAL_LONG_NAME"));
	materialForm.setUom(rsList.getString("UNIT_OF_MEAS_ID"));


	String mtgrp="select * from MATERIAL_GROUP where MATERIAL_GROUP_ID='"+rsList.getString("MATERIAL_GROUP_ID")+"'";
	ResultSet ff = ad.selectQuery(mtgrp);
	if(ff.next())
	{
		materialForm.setMaterialGroupName(ff.getString("MATERIAL_GROUP_ID"));
	}
	materialForm.setReqMatType(rsList.getString("Type"));
	String sapCodeNo=rsList.getString("SAP_CODE_NO");
	String requestNo=rsList.getString("REQUEST_NO");
	String matType=rsList.getString("Type");
	String approveStatus=rsList.getString("Approve_Type");
	String approverList="";
	String codeCreationDate="";
	
/*	String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
	+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Material Master' and "
	+ "type='"+matType+"' and all_r.Approved_Persons=emp.PERNR";
	ResultSet rsApp=ad.selectQuery(approverDet);
	try{
		while(rsApp.next()){
			approverList=approverList+rsApp.getString("EMP_FULLNAME");
			String status=rsApp.getString("Req_Status");
			if(status.equals("Approved")||status.equals("Completed"))
				approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
			else if(status.equals("Rejected"))
				approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
			
			if(status.equals("Completed"))
				codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
			else if(status.equals("Rejected"))
				codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
				
		}
		//if(!approverList.equals(""))
		//approverList=approverList.substring(0, (approverList.length()-1));
		materialForm.setApproversList(approverList);
	}catch(Exception e){
		e.printStackTrace();
	}*/
	 
	if(sapCodeNo!=null){
		materialForm.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
		materialForm.setCodeCreationDate(rsList.getString("SAP_CREATION_DATE1"));
	}
	else{
		materialForm.setSapCodeNo("");
		materialForm.setCodeCreationDate("");
	}
	String sapCrDt=rsList.getString("SAP_CREATION_DATE1");
	if(sapCrDt!=null){
		if(!sapCrDt.equals("")){
			materialForm.setSapCreationDate(rsList.getString("SAP_CREATION_DATE1"));
		}else
			materialForm.setSapCreationDate("");
			
	}else{
		materialForm.setSapCreationDate("");
	}
	String lastApprover=rsList.getString("last_approver");
	if(!lastApprover.equals("No"))
	 materialForm.setLastApprover(rsList.getString("last_approver"));
	else
		materialForm.setLastApprover("");	
	String pendingAppr=rsList.getString("pending_approver");
	if(!pendingAppr.equals("No"))
		materialForm.setPendingApprover(rsList.getString("pending_approver"));
	else
		materialForm.setPendingApprover("");
	
	
	
	//prevappr
		String lastappr="select Actual_Approver,approved_date,emp.EMP_FULLNAME from All_Request as all_r,emp_official_info emp where Req_Id='"+requestNo+"' and Req_Type='Material Master'  and emp.PERNR=all_r.Actual_Approver order by approved_date desc";
		ResultSet rslast=ad.selectQuery(lastappr);
		int ab=0;
		String actual="";
		int chk=0;
		while(rslast.next())
		{
			
			materialForm.setPrevApprover(rslast.getString("EMP_FULLNAME"));
			materialForm.setPrevApproverDate(rslast.getString("approved_date"));
			ab=ab+1;
			if(!actual.equalsIgnoreCase(rslast.getString("Actual_Approver"))&&chk==1)
			{
				break;
			}
			
			
			actual=rslast.getString("Actual_Approver");
			chk=1;
			
		}
		listOfMaterialCode.add(materialForm);
		}

if(!rsList.previous()){
	masterForm.setMessage("No Record Found..");
request.setAttribute("noRecords", "noRecords");
}
request.setAttribute("disablePreviousButton", "disablePreviousButton");
request.setAttribute("listOfMaterials", listOfMaterialCode);

		 } //end of Material Master
	 if(searchType.equals("Customer Master")){
		 LinkedList listOfCustomerCode=new LinkedList();
			String  queryCount="select count(*) from CUSTOMER_MASTER_M cus,emp_official_info emp,Location loc where cus.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID ";
			if(!Location.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and loc.LOCID='"+Location+"'";
			}
			if(!Materialcode.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and cus.ACCOUNT_GROUP_ID='"+Materialcode+"'";
			}
				if(!From.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and cus.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				
				/*if(!From1.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and (cus.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
					queryCount=queryCount+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					queryCount=queryCount+ " and  Approve_Status = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					queryCount=queryCount+ " and  Approve_Status = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					queryCount=queryCount+ " and  Approve_Status not in ('Rejected','Completed','Saved') ";
				}
		 
				ResultSet rsQueryCount=ad.selectQuery(queryCount);
				int queryTotal=0;
				 while(rsQueryCount.next())
				 {
					 queryTotal=rsQueryCount.getInt(1);
				 }
				 if(queryTotal>10)
				 {
					 masterForm.setTotalRecords(queryTotal);
				 startRecord=1;
				 endRecord=10;
				 masterForm.setStartRecord(1);
				 masterForm.setEndRecord(10);
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  masterForm.setTotalRecords(totalRecords);
					  masterForm.setStartRecord(1);
					  masterForm.setEndRecord(totalRecords); 
				 }	 
		startRecord=1;
		endRecord=10;
	String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.ID) AS RowNum, cus.REQUEST_NO,cus.REQUEST_DATE,cus.NAME,cus.CITY,emp.LOCID,"
	+ "emp.EMP_FULLNAME,cus.Approve_Status,cus.SAP_CODE_NO,cus.last_approver,cus.pending_approver,cus.SAP_CREATION_DATE from CUSTOMER_MASTER_M cus,emp_official_info emp,Location loc "
	+ "where cus.CREATED_BY=emp.PERNR and emp.LOCID =loc.LOCATION_CODE ";
	if(!Location.equalsIgnoreCase("")){	
		query=query+ " and loc.LOCID='"+Location+"' ";
	}
	if(!Materialcode.equalsIgnoreCase("")){	
		query=query+ " and cus.ACCOUNT_GROUP_ID='"+Materialcode+"'";
	}
	if(!From.equalsIgnoreCase("")){	
	query=query+ " and cus.REQUEST_DATE between '"+From+"' and '"+To+"'";
	}
	/*if(!From1.equalsIgnoreCase("")){	
		query=query+ " and (cus.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
	}*/
	
	if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
	{
		query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
	}
	
	if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
	{
		query=query+ " and  Approve_Status = 'Completed' ";
	}
	
	if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
	{
		query=query+ " and  Approve_Status = 'Rejected' ";
	}
	
	if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
	{
		query=query+ " and  Approve_Status not in ('Rejected','Completed','Saved') ";
	}
	
	query=query+ ")as sub Where  sub.RowNum between 1 and "+queryTotal+" order by REQUEST_NO ";
	ResultSet rsList=ad.selectQuery(query);
	while(rsList.next()){
		CustomerMasterForm c=new CustomerMasterForm();
		c.setRequestNo(rsList.getString("REQUEST_NO"));
		c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
		c.setCustomerName(rsList.getString("NAME"));
		c.setCity(rsList.getString("CITY"));
		c.setLocationId(rsList.getString("LOCID"));
		c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
		c.setApproveType(rsList.getString("Approve_Status"));
		String sapcodeno=rsList.getString("SAP_CODE_NO");
		if(sapcodeno!=null){
		 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
		 c.setSapCreationDate("");
		}
		else
			c.setSapCodeNo("");	
		
		String sapCodeNo=rsList.getString("SAP_CODE_NO");
		String requestNo=rsList.getString("REQUEST_NO");
		String approveStatus=rsList.getString("Approve_Status");
		String approverList="";
		String codeCreationDate="";
		
		String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
		+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Customer Master'  "
		+ " and all_r.Approved_Persons=emp.PERNR";
		ResultSet rsApp=ad.selectQuery(approverDet);
		try{
			while(rsApp.next()){
				approverList=approverList+rsApp.getString("EMP_FULLNAME");
				String status=rsApp.getString("Req_Status");
				if(status.equals("Approved")||status.equals("Completed"))
					approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
				else if(status.equals("Rejected"))
					approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
				
				if(status.equals("Completed"))
					codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
				else if(status.equals("Rejected"))
					codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
					
			}
			//if(!approverList.equals(""))
			//approverList=approverList.substring(0, (approverList.length()-1));
			c.setApproversList(approverList);
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		if(sapCodeNo!=null){
			c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
			c.setCodeCreationDate(codeCreationDate);
		}
		else{
			c.setSapCodeNo("");
			c.setCodeCreationDate("");
		}
		String sapCrDt=rsList.getString("SAP_CREATION_DATE");
		if(sapCrDt!=null){
			if(!sapCrDt.equals("")){
				c.setSapCreationDate(codeCreationDate);
			}else
				c.setSapCreationDate("");
				
		}else{
			c.setSapCreationDate("");
		}
		String lastApprover=rsList.getString("last_approver");
		if(lastApprover!=null)
		if(!lastApprover.equals("No"))
		 c.setLastApprover(rsList.getString("last_approver"));
		else
			c.setLastApprover("");	
		String pendingAppr=rsList.getString("pending_approver");
		if(pendingAppr!=null)
		if(!pendingAppr.equals("No"))
			c.setPendingApprover(rsList.getString("pending_approver"));
		else
			c.setPendingApprover("");
		listOfCustomerCode.add(c);
	}
	
	if(!rsList.previous()){
		masterForm.setMessage("No Record Found..");
	request.setAttribute("noCustRecords", "noCustRecords");
	}
	request.setAttribute("disablePreviousButton", "disablePreviousButton");
    request.setAttribute("listOfCustomers", listOfCustomerCode);
	 }//end of Customer Master
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("exportToExcel");
}
	public ActionForward searchMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		SAPReportForm masterForm=(SAPReportForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		
		try{
		  LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			//vendor type
			 	ArrayList accountGroupList=new ArrayList();
				ArrayList accountGroupLabelList=new ArrayList();
			 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		while(rs.next()) {
			accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
			accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
		}
		masterForm.setAccountGroupList(accountGroupList);
		masterForm.setAccountGroupLabelList(accountGroupLabelList);
		
		

		ArrayList techList=new ArrayList();
		ArrayList techLabelList=new ArrayList();
		ResultSet rs13 = ad.selectQuery(" select app,emp.EMP_FULLNAME from ( select Approver_Id as app from Material_Approvers where role ='Creator' and Approver_Id!='' group by Approver_Id union select Parllel_Approver_1 from Material_Approvers where role ='Creator' and Parllel_Approver_1!='' group by Parllel_Approver_1 union select Parllel_Approver_2 from Material_Approvers where role ='Creator' and Parllel_Approver_2!='' group by Parllel_Approver_2)t,emp_official_info as emp where emp.PERNR=App  and emp.ACTIVE =1 order  by emp.EMP_FULLNAME"); 
				try {
					while(rs13.next()) {
						techList.add(rs13.getString("App"));
						techLabelList.add(rs13.getString("EMP_FULLNAME"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				masterForm.setTechidList(techList);
				masterForm.setTechnameList(techLabelList);
				
				
				
			String reqnumber=masterForm.getRequestNumber();
			String From=masterForm.getFromDate();
			String To=masterForm.getToDate();
			
			String From1=masterForm.getFromDate1();
			String To1=masterForm.getToDate1();
			
			
			if(From!=null){
				if(!From.equalsIgnoreCase("")){
				  String b[]=From.split("/");
				  From=b[2]+"-"+b[1]+"-"+b[0];
				}
				}
				//String To=codeRequestForm.getToDate();
			
				if(To!=null){
				if(!To.equalsIgnoreCase("")){
				  String b1[]=To.split("/");
				  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
				}
				}
			

				if(From1!=null){
					if(!From1.equalsIgnoreCase("")){
					  String b[]=From1.split("/");
					  From1=b[2]+"-"+b[1]+"-"+b[0];
					}
					}
					//String To=codeRequestForm.getToDate();
				
					if(To1!=null){
					if(!To1.equalsIgnoreCase("")){
					  String b1[]=To1.split("/");
					  To1=b1[2]+"-"+b1[1]+"-"+b1[0];	
					}
					}
				
				
		int  totalRecords=0;
	    int  startRecord=0;
	    int  endRecord=0;	
	 String Location=masterForm.getLocationId();
     String Materialcode=masterForm.getMaterialCodeLists();
     String searchType=masterForm.getSearchType();
     
     if(searchType.equals("Service Master")){
    	 LinkedList listOfServiceCode=new LinkedList();
    		String  queryCount="select count(*) from SERVICE_MASTER s,Location loc,emp_official_info emp "
    				+ " where s.CREATED_BY=emp.PERNR and s.plant_code=loc.LOCID ";
    		if(!Materialcode.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and s.service_catagory='"+Materialcode+"'";
			}
			if(!Location.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and loc.LOCID='"+Location+"'";
			}
				
			if(!From.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and (s.CREATED_DATE between '"+From+"' and '"+To+"') ";
			}
			
			/*if(!From1.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and (s.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
			}*/
			
			if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
			{
				queryCount=queryCount+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
			{
				queryCount=queryCount+ " and  app_satus = 'Completed' ";
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
			{
				queryCount=queryCount+ " and  app_satus = 'Rejected' ";
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
			{
				queryCount=queryCount+ " and  app_satus  in ('Submited') ";
			}
			if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
			{
				queryCount=queryCount+ " and  app_satus  in ('In Process') ";
			}
			
			/*if(!masterForm.getApprover().equalsIgnoreCase(""))
			{
				queryCount=queryCount+ " and  request_no in (select Req_Id "
						+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Service Master') ";
			}*/
			
			ResultSet rsQueryCount=ad.selectQuery(queryCount);
			int queryTotal=0;
				 while(rsQueryCount.next())
				 {
					 queryTotal=rsQueryCount.getInt(1);
				 }
				 if(queryTotal>10)
				 {
					 masterForm.setTotalRecords(queryTotal);
				 startRecord=1;
				 endRecord=10;
				 masterForm.setStartRecord(1);
				 masterForm.setEndRecord(10);
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  masterForm.setTotalRecords(totalRecords);
					  masterForm.setStartRecord(1);
					  masterForm.setEndRecord(totalRecords); 
				 }	 
		startRecord=1;
		endRecord=10;
		
		String query1="select case service_catagory when 'ZAMC'  then 'ZAMC- Annual maintainence '"
				+ "when 'ZCLB'  then 'ZCLB- Calibration '"
				+ "when 'ZCIV'  then 'ZCIV- Civil works '"
				+ "when 'ZMNT'  then 'ZMNT- Maintainence '"
				+ "when 'ZITM'  then 'ZITM-MAINAINENCE-IT '"
				+ "when 'ZMKT'  then 'ZMKT- Marketing '"
				+ "when 'ZTST'  then 'ZTST- Testing&Analysis'"
				+ " else 'ZTRC- Training&Recruitment' end as desca ,"
				+ " sum( case when app_satus='Completed' then 1 else 0  end )  as completed ,"
				+ "	sum( case when app_satus='In Process'  then 1 else 0  end )  as inprogress ,"
				+ "	sum( case when app_satus='Submited'  then 1 else 0  end )  as submitted ,"
				+ "	sum( case when app_satus='Rejected'  then 1 else 0  end )  as rejected ,"
				+ "	sum( case when app_satus='Completed' then 1 else 0  end )+sum( case when app_satus!='Created' and app_satus!='Completed' and app_satus!='Rejected' and app_satus!='Deleted'  then 1 else 0  end ) as total "
				+ " from SERVICE_MASTER  "
				+ " where  service_catagory is not null ";
				if(!Location.equalsIgnoreCase("")){	
					query1=query1+ " and SERVICE_MASTER.plant_code='"+Location+"'";
				}
				
				if(!From.equalsIgnoreCase("")){
					
					query1=query1+ " and SERVICE_MASTER.CREATED_DATE between '"+From+"' and '"+To+"'";
				}
				
				/*if(!From1.equalsIgnoreCase("")){	
					query1=query1+ " and (SERVICE_MASTER.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				
				
				/*if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
				query1=query1+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}*/
				query1=query1+" group by service_catagory";
		
		ResultSet rsQuerysumm=ad.selectQuery(query1);
		ArrayList sumlist = new ArrayList();
		while(rsQuerysumm.next())
		{
			SAPReportForm form1 = new SAPReportForm();
			form1.setSearchType(rsQuerysumm.getString("desca"));
			form1.setStartRecord(rsQuerysumm.getInt("completed"));
			form1.setEndRecord(rsQuerysumm.getInt("inprogress"));
			form1.setMidRecord(rsQuerysumm.getInt("submitted"));
			form1.setrejRecord(rsQuerysumm.getInt("rejected"));
			form1.setTotalRecords(rsQuerysumm.getInt("total"));
			sumlist.add(form1);
			
		}
		request.setAttribute("sumlist", sumlist);
		
		String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY s.request_no) AS RowNum, s.request_no,s.request_date,"
		+ "loc.LOCATION_CODE,emp.EMP_FULLNAME,s.service_description,s.last_approver,s.pending_approver,s.app_satus,s.SAP_CODE_NO,s.SAP_CREATION_DATE "
		+ " from SERVICE_MASTER s,Location loc,"
		+ "emp_official_info emp where s.CREATED_BY=emp.PERNR and s.plant_code=loc.LOCID   ";
		if(!Materialcode.equalsIgnoreCase("")){	
			query=query+ " and s.service_catagory='"+Materialcode+"'";
		}		
		if(!Location.equalsIgnoreCase("")){	
					query=query+ " and  loc.LOCID='"+Location+"'";
				}
				if(!From.equalsIgnoreCase("")){	
				query=query+ " and (s.CREATED_DATE between '"+From+"' and '"+To+"') ";
				}
				
				/*if(!From1.equalsIgnoreCase("")){	
					query=query+ " and (s.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
				 query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					query=query+ " and  app_satus = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					query=query+ " and  app_satus = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
					
				{
					query=query+ " and  app_satus  in ('Submited')";
				}
				if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
					
				{
					query=query+ " and  app_satus  in ('In Process') ";
				}
				
				/*if(!masterForm.getApprover().equalsIgnoreCase(""))
				{
					queryCount=queryCount+ " and  request_no in (select Req_Id "
							+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Service Master') ";
				}*/
				
				query=query+ ")as sub Where  sub.RowNum between 1 and 10 order by request_no ";
				ResultSet rsList=ad.selectQuery(query);
				while(rsList.next()){
					ServiceMasterRequestForm c=new ServiceMasterRequestForm();
					c.setR_no(rsList.getInt("request_no"));
					c.setRequestDate(rsList.getString("request_date"));
					
					c.setLocationID(rsList.getString("LOCATION_CODE"));
					c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
					c.setServiceDescription(rsList.getString("service_description"));
					c.setApproveType(rsList.getString("app_satus"));
					String sapcodeno=rsList.getString("SAP_CODE_NO");
					if(sapcodeno!=null){
					 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
					 c.setSapCreationDate("");
					}
					else
						c.setSapCodeNo("");	
					
					String sapCodeNo=rsList.getString("SAP_CODE_NO");
					String requestNo=rsList.getString("request_no");
					String approveStatus=rsList.getString("app_satus");
					String approverList="";
					String codeCreationDate="";
					
					String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
					+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Service Master'  "
					+ " and all_r.Approved_Persons=emp.PERNR";
					ResultSet rsApp=ad.selectQuery(approverDet);
					try{
						while(rsApp.next()){
							approverList=approverList+rsApp.getString("EMP_FULLNAME");
							String status=rsApp.getString("Req_Status");
							if(status.equals("Approved")||status.equals("Completed"))
								approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
							else if(status.equals("Rejected"))
								approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
							
							if(status.equals("Completed"))
								codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
							else if(status.equals("Rejected"))
								codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
								
						}
						//if(!approverList.equals(""))
						//approverList=approverList.substring(0, (approverList.length()-1));
						c.setApproversList(approverList);
					}catch(Exception e){
						e.printStackTrace();
					}
					 
					if(sapCodeNo!=null){
						c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
						c.setCodeCreationDate(codeCreationDate);
					}
					else{
						c.setSapCodeNo("");
						c.setCodeCreationDate("");
					}
					String sapCrDt=rsList.getString("SAP_CREATION_DATE");
					if(sapCrDt!=null){
						if(!sapCrDt.equals("")){
							c.setSapCreationDate(codeCreationDate);
						}else
							c.setSapCreationDate("");
							
					}else{
						c.setSapCreationDate("");
					}
					String lastApprover=rsList.getString("last_approver");
					if(!lastApprover.equals("No"))
					 c.setLastApprover(rsList.getString("last_approver"));
					else
						c.setLastApprover("");	
					String pendingAppr=rsList.getString("pending_approver");
					if(!pendingAppr.equals("No"))
						c.setPendingApprover(rsList.getString("pending_approver"));
					else
						c.setPendingApprover("");
					
					
					listOfServiceCode.add(c);
				}
				
				if(!rsList.previous()){
					masterForm.setMessage("No Record Found..");
				request.setAttribute("noCustRecords", "noCustRecords");
				}
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			    request.setAttribute("listOfServiceCode", listOfServiceCode); 
     }
     if(searchType.equals("Vendor Master")){
    	 LinkedList listOfVendorCode=new LinkedList();
			String  queryCount="select COUNT(*) from vendor_master_m v,emp_official_info emp,Location loc where v.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID  ";
				 
				if(!Location.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and loc.LOCID='"+Location+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and v.ACCOUNT_GROUP_ID='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and v.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				
				/*if(!From1.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and (v.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
				queryCount=queryCount+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					queryCount=queryCount+ " and  Approve_Status = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					queryCount=queryCount+ " and  Approve_Status = 'Rejected' ";
				}
				
				/*if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
				{
					queryCount=queryCount+ " and  Approve_Status in ('Rejected','Completed','Saved') ";
				}*/
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
				{
					queryCount=queryCount+ " and  Approve_Status in ('Submited') ";
				}
				if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					queryCount=queryCount+ " and  Approve_Status in ('In Process') ";
				}
				/*if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
					
				{
					queryCount=queryCount+ " and  request_no in (select Req_Id "
							+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Vendor Master') ";
				}*/
				
				
				
				
				
				
				
				
				ResultSet rsQueryCount=ad.selectQuery(queryCount);
				int queryTotal=0;
				 while(rsQueryCount.next())
				 {
					 queryTotal=rsQueryCount.getInt(1);
				 }
				 if(queryTotal>10)
				 {
					 masterForm.setTotalRecords(queryTotal);
				 startRecord=1;
				 endRecord=10;
				 masterForm.setStartRecord(1);
				 masterForm.setEndRecord(10);
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  masterForm.setTotalRecords(totalRecords);
					  masterForm.setStartRecord(1);
					  masterForm.setEndRecord(totalRecords); 
				 }	 
		startRecord=1;
		endRecord=10;
		
		String query1="select ACCOUNT_GROUP_M.ACCOUNT_GROUP_NAME as desca, sum( case when Approve_Status='Completed' then 1 else 0  end )  as completed ,	sum( case when Approve_Status='In Process' then 1 else 0  end )  as inprogress ,sum( case when Approve_Status='Submited' then 1 else 0  end )  as submitted,sum( case when Approve_Status='Rejected' then 1 else 0  end )  as rejected,	sum( case when Approve_Status='Completed' then 1 else 0  end )+sum( case when Approve_Status!='Created' and Approve_Status!='Completed' and Approve_Status!='Rejected' and Approve_Status!='Deleted'  then 1 else 0  end ) as total  "
				+ " from emp_official_info emp,Location loc ,vendor_master_m left outer join  ACCOUNT_GROUP_M on ACCOUNT_GROUP_M.ACCOUNT_GROUP_ID = vendor_master_m.ACCOUNT_GROUP_ID  "
				+ " where   vendor_master_m.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID   ";
				if(!Location.equalsIgnoreCase("")){	
					query1=query1+ " and loc.LOCID='"+Location+"'";
				}
				
				if(!From.equalsIgnoreCase("")){	
					query1=query1+ " and vendor_master_m.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				
				/*if(!From1.equalsIgnoreCase("")){	
					query1=query1+ " and (vendor_master_m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				
				/*if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
				query1=query1+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}*/
				query1=query1+" group by ACCOUNT_GROUP_M.ACCOUNT_GROUP_NAME ";
		
		ResultSet rsQuerysumm=ad.selectQuery(query1);
		ArrayList sumlist = new ArrayList();
		while(rsQuerysumm.next())
		{
			SAPReportForm form1 = new SAPReportForm();
			form1.setSearchType(rsQuerysumm.getString("desca"));
			form1.setStartRecord(rsQuerysumm.getInt("completed"));
			form1.setEndRecord(rsQuerysumm.getInt("inprogress"));
			form1.setMidRecord(rsQuerysumm.getInt("submitted"));
			form1.setrejRecord(rsQuerysumm.getInt("rejected"));
			form1.setTotalRecords(rsQuerysumm.getInt("total"));
			sumlist.add(form1);
			
		}
		request.setAttribute("sumlist", sumlist);
		
		String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.REQUEST_NO ) AS RowNum, v.REQUEST_NO,v.REQUEST_DATE,emp.LOCID,"
				+ "emp.EMP_FULLNAME,v.NAME,v.CITY,v.Approve_Status,v.last_approver,"
				+ "v.pending_approver,v.SAP_CODE_NO,v.SAP_CREATION_DATE from vendor_master_m v,emp_official_info emp,Location loc "
				+ "where v.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID   ";
				if(!Location.equalsIgnoreCase("")){	
					query=query+ " and  loc.LOCID='"+Location+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
					query=query+ " and v.ACCOUNT_GROUP_ID='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
				query=query+ " and v.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				/*if(!From1.equalsIgnoreCase("")){	
					query=query+ " and (v.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
				{
				query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					query=query+ " and  Approve_Status = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					query=query+ " and  Approve_Status = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
					
				{
					query=query+ " and  Approve_Status in ('Submited') ";
				}
				if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
					
				{
					query=query+ " and  Approve_Status in ('In Process') ";
				}
				
				/*if(!masterForm.getApprover().equalsIgnoreCase(""))
				{
					queryCount=queryCount+ " and  request_no in (select Req_Id "
							+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Vendor Master') ";
				}*/
				
				query=query+ ")as sub Where  sub.RowNum between 1 and 10 order by REQUEST_NO ";
				ResultSet rsList=ad.selectQuery(query);
				while(rsList.next()){
					VendorMasterRequestForm c=new VendorMasterRequestForm();
					c.setRequestNo(rsList.getInt("REQUEST_NO"));
					c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
					c.setName(rsList.getString("NAME"));
					c.setCity(rsList.getString("CITY"));
					c.setLocationId(rsList.getString("LOCID"));
					c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
					c.setApproveType(rsList.getString("Approve_Status"));
					String sapcodeno=rsList.getString("SAP_CODE_NO");
					if(sapcodeno!=null){
					 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
					 c.setSapCreationDate("");
					}
					else
						c.setSapCodeNo("");	
					
					String sapCodeNo=rsList.getString("SAP_CODE_NO");
					String requestNo=rsList.getString("REQUEST_NO");
					String approveStatus=rsList.getString("Approve_Status");
					String approverList="";
					String codeCreationDate="";
					
					String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
					+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Vendor Master'  "
					+ " and all_r.Approved_Persons=emp.PERNR";
					ResultSet rsApp=ad.selectQuery(approverDet);
					try{
						while(rsApp.next()){
							approverList=approverList+rsApp.getString("EMP_FULLNAME");
							String status=rsApp.getString("Req_Status");
							if(status.equals("Approved")||status.equals("Completed"))
								approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
							else if(status.equals("Rejected"))
								approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
							
							if(status.equals("Completed"))
								codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
							else if(status.equals("Rejected"))
								codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
								
						}
						//if(!approverList.equals(""))
						//approverList=approverList.substring(0, (approverList.length()-1));
						c.setApproversList(approverList);
					}catch(Exception e){
						e.printStackTrace();
					}
					 
					if(sapCodeNo!=null){
						c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
						c.setCodeCreationDate(codeCreationDate);
					}
					else{
						c.setSapCodeNo("");
						c.setCodeCreationDate("");
					}
					String sapCrDt=rsList.getString("SAP_CREATION_DATE");
					if(sapCrDt!=null){
						if(!sapCrDt.equals("")){
							c.setSapCreationDate(codeCreationDate);
						}else
							c.setSapCreationDate("");
							
					}else{
						c.setSapCreationDate("");
					}
					String lastApprover=rsList.getString("last_approver");
					if(!lastApprover.equals("No"))
					 c.setLastApprover(rsList.getString("last_approver"));
					else
						c.setLastApprover("");	
					String pendingAppr=rsList.getString("pending_approver");
					if(!pendingAppr.equals("No"))
						c.setPendingApprover(rsList.getString("pending_approver"));
					else
						c.setPendingApprover("");
					
					
					listOfVendorCode.add(c);
				}
				
				if(!rsList.previous()){
					masterForm.setMessage("No Record Found..");
				request.setAttribute("noCustRecords", "noCustRecords");
				}
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			    request.setAttribute("listOfVendorCode", listOfVendorCode);
			
				
     }
     
		 if(searchType.equals("Material Master"))
		 { 
			 LinkedList listOfMaterialCode=new LinkedList();
		String  queryCount="select count(*) from material_code_request as m,Location as l where  m.LOCATION_ID=l.LOCID  ";
			 
		if(!Location.equalsIgnoreCase("")){	
			queryCount=queryCount+ " and m.LOCATION_ID='"+Location+"'";
		}
		     
			if(!Materialcode.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and m.Type='"+Materialcode+"'";
			}
			if(!From.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
			}
			
			/*if(!From1.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and (m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
			}
			*/
			if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
			{
				queryCount=queryCount+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
			}
			

			if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
			{
				queryCount=queryCount+ " and  Approve_Type = 'Completed' ";
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
			{
				queryCount=queryCount+ " and  Approve_Type = 'Rejected' ";
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
			{
				queryCount=queryCount+ " and  Approve_Type in ('Submited') ";
			}
			
		/*	if(masterForm.getReq_Status().equalsIgnoreCase("Created"))
			{
				queryCount=queryCount+ " and  Approve_Type not in ('Rejected','Completed','Created','deleted') ";
			}*/
			
			
			if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
			{
				queryCount=queryCount+ " and  Approve_Type in ('In Process') ";
			}
			
		/*	if(!masterForm.getApprover().equalsIgnoreCase(""))
			{
				queryCount=queryCount+ " and  request_no in (select Req_Id "
						+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Material Master') ";
			}*/
			
			
			
			ResultSet rsQueryCount=ad.selectQuery(queryCount);
			int queryTotal=0;
			 while(rsQueryCount.next())
			 {
				 queryTotal=rsQueryCount.getInt(1);
			 }
			 if(queryTotal>10)
			 {
				 masterForm.setTotalRecords(queryTotal);
			 startRecord=1;
			 endRecord=10;
			 masterForm.setStartRecord(1);
			 masterForm.setEndRecord(10);
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  masterForm.setTotalRecords(totalRecords);
				  masterForm.setStartRecord(1);
				  masterForm.setEndRecord(totalRecords); 
			 }	 
	startRecord=1;
	endRecord=10;
	
	
	
String query1="select MATERIAL_TYPE.M_DESC as desca,"
		+ " sum( case when Approve_Type='Completed' then 1 else 0  end )  as completed ,"
		+ "	sum( case when Approve_Type!='Created' and  Approve_Type!='Submited' and Approve_Type!='Completed' and Approve_Type!='Rejected' and Approve_Type!='Deleted'  then 1 else 0  end )  as inprogress ,"
		+ "	sum( case when Approve_Type!='Created' and  Approve_Type!='In Process' and Approve_Type!='Completed' and Approve_Type!='Rejected' and Approve_Type!='Deleted'  then 1 else 0  end )  as submitted ,"
		+ "	sum( case when Approve_Type='Rejected' then 1 else 0  end )  as Rejected ,"
		+ "	sum( case when Approve_Type='Completed' then 1 else 0  end )+sum( case when Approve_Type!='Created' and Approve_Type!='Completed' and Approve_Type!='Rejected' and Approve_Type!='Deleted'  then 1 else 0  end ) as total "
		+ " from material_code_request left outer join  MATERIAL_TYPE on MATERIAL_TYPE.MATERIAL_GROUP_ID = material_code_request.Type "
		+ " where  MATERIAL_TYPE.M_DESC is not null  ";
		if(!Location.equalsIgnoreCase("")){	
			query1=query1+ " and material_code_request.LOCATION_ID='"+Location+"'";
		}
		
		if(!From.equalsIgnoreCase("")){	
			query1=query1+ " and material_code_request.REQUEST_DATE between '"+From+"' and '"+To+"'";
		}
		
		/*if(!From1.equalsIgnoreCase("")){	
			query1=query1+ " and (material_code_request.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
		}*/
		
		/*if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
			{
				query1=query1+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
			}*/
		query1=query1+" group by MATERIAL_TYPE.M_DESC";

ResultSet rsQuerysumm=ad.selectQuery(query1);
ArrayList sumlist = new ArrayList();
while(rsQuerysumm.next())
{
	SAPReportForm form1 = new SAPReportForm();
	form1.setSearchType(rsQuerysumm.getString("desca"));
	form1.setStartRecord(rsQuerysumm.getInt("completed"));
	form1.setEndRecord(rsQuerysumm.getInt("inprogress"));
	form1.setMidRecord(rsQuerysumm.getInt("submitted"));
	form1.setrejRecord(rsQuerysumm.getInt("Rejected"));
	form1.setTotalRecords(rsQuerysumm.getInt("total"));
	sumlist.add(form1);
	
}
request.setAttribute("sumlist", sumlist);
	
				String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO ) AS RowNum,m.REQUEST_NO,m.REQUEST_DATE,emp.EMP_FULLNAME,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.UNIT_OF_MEAS_ID,m.MATERIAL_GROUP_ID,uom.LTXT,m.Type,m.Approve_Type," +
				"m.URL,L.location_code,m.REPORT_URL,mType.M_DESC,m.last_approver,m.pending_approver,m.SAP_CODE_NO,m.SAP_CREATION_DATE from material_code_request "
				+ "as m,Location as l ,UNIT_MESUREMENT as uom,MATERIAL_TYPE as mType,emp_official_info emp  where m.UNIT_OF_MEAS_ID=uom.UNIT_OF_MEAS_ID AND  m.Type=mType.MATERIAL_GROUP_ID and  m.LOCATION_ID=l.LOCID and emp.PERNR=m.CREATED_BY  ";
				if(!Location.equalsIgnoreCase("")){	
					query=query+ " and m.LOCATION_ID='"+Location+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
				query=query+ " and m.Type='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
				query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				/*if(!From1.equalsIgnoreCase("")){	
					query=query+ " and (m.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
				}*/
				if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
							{
								query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
							}
				
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
				{
					query=query+ " and  Approve_Type = 'Completed' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
				{
					query=query+ " and  Approve_Type = 'Rejected' ";
				}
				
				if(masterForm.getReq_Status().equalsIgnoreCase("Submitted"))
				{
					query=query+ " and  Approve_Type  in ('Submited') ";
				}
				if(masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				{
					query=query+ " and  Approve_Type  in ('In Process') ";
				}
				
				/*if(!masterForm.getApprover().equalsIgnoreCase(""))
				{
					query=query+ " and  request_no in (select Req_Id "
							+ " from All_Request where ( Pending_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Material Master') ";
				}
*/
				
			/*if(!masterForm.getApprover().equalsIgnoreCase(""))
				{
					query=query+ " and  request_no in (select Req_Id "
							+ " from All_Request where ( Pending_Approver='"+masterForm.getApprover()+"' ) and Req_Type='Material Master') ";
				}*/

query=query+ ")as sub Where  sub.RowNum between 1 and 10 order by REQUEST_NO ";
ResultSet rsList=ad.selectQuery(query);
while(rsList.next()){
MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));

String requestDate=rsList.getString("REQUEST_DATE");
String req[]=requestDate.split(" ");
requestDate=req[0];
String a[]=requestDate.split("-");
requestDate=a[2]+"/"+a[1]+"/"+a[0];
materialForm.setRequestDate(requestDate);
materialForm.setRequesterName(rsList.getString("EMP_FULLNAME"));
materialForm.setLocationId(rsList.getString("location_code"));
materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
materialForm.setMaterialDesc(rsList.getString("MATERIAL_LONG_NAME"));
materialForm.setUom(rsList.getString("UNIT_OF_MEAS_ID"));


String mtgrp="select * from MATERIAL_GROUP where MATERIAL_GROUP_ID='"+rsList.getString("MATERIAL_GROUP_ID")+"'";
ResultSet ff = ad.selectQuery(mtgrp);
if(ff.next())
{
	materialForm.setMaterialGroupName(ff.getString("MATERIAL_GROUP_ID"));
}

materialForm.setApproveType(rsList.getString("Approve_Type"));
materialForm.setmType(rsList.getString("Type"));

materialForm.setReqMatType(rsList.getString("Type"));
String sapCodeNo=rsList.getString("SAP_CODE_NO");
String requestNo=rsList.getString("REQUEST_NO");
String matType=rsList.getString("Type");
String approveStatus=rsList.getString("Approve_Type");
String approverList="";
String codeCreationDate="";
String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Material Master' and "
+ "type='"+matType+"' and all_r.Approved_Persons=emp.PERNR";
ResultSet rsApp=ad.selectQuery(approverDet);
try{
	while(rsApp.next()){
		approverList=approverList+rsApp.getString("EMP_FULLNAME");
		String status=rsApp.getString("Req_Status");
		if(status.equals("Approved")||status.equals("Completed"))
			approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
		else if(status.equals("Rejected"))
			approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
		
		if(status.equals("Completed"))
			codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
		else if(status.equals("Rejected"))
			codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
			
	}
	//if(!approverList.equals(""))
	//approverList=approverList.substring(0, (approverList.length()-1));
	materialForm.setApproversList(approverList);
}catch(Exception e){
	e.printStackTrace();
}
 
if(sapCodeNo!=null){
	materialForm.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
	materialForm.setCodeCreationDate(codeCreationDate);
}
else{
	materialForm.setSapCodeNo("");
	materialForm.setCodeCreationDate("");
}
String sapCrDt=rsList.getString("SAP_CREATION_DATE");
if(sapCrDt!=null){
	if(!sapCrDt.equals("")){
		materialForm.setSapCreationDate(codeCreationDate);
	}else
		materialForm.setSapCreationDate("");
		
}else{
	materialForm.setSapCreationDate("");
}
String lastApprover=rsList.getString("last_approver");
if(!lastApprover.equals("No"))
 materialForm.setLastApprover(rsList.getString("last_approver"));
else
	materialForm.setLastApprover("");	
String pendingAppr=rsList.getString("pending_approver");
if(!pendingAppr.equals("No"))
	materialForm.setPendingApprover(rsList.getString("pending_approver"));
else
	materialForm.setPendingApprover("");
//prevappr
	String lastappr="select CONVERT(varchar(10),all_r.approved_date,103)+' '+ CONVERT(varchar(5),all_r.approved_date,108) as approved_date1,Actual_Approver,approved_date,emp.EMP_FULLNAME from All_Request as all_r,emp_official_info emp where Req_Id='"+requestNo+"' and Req_Type='Material Master'  and emp.PERNR=all_r.Actual_Approver order by approved_date desc";
	ResultSet rslast=ad.selectQuery(lastappr);
	int ab=0;
	String actual="";
	int chk=0;
	while(rslast.next())
	{
		
		materialForm.setPrevApprover(rslast.getString("EMP_FULLNAME"));
		materialForm.setPrevApproverDate(rslast.getString("approved_date1"));
		ab=ab+1;
		if(!actual.equalsIgnoreCase(rslast.getString("Actual_Approver"))&&chk==1)
		{
			break;
		}
		
		
		actual=rslast.getString("Actual_Approver");
		chk=1;
		
	}
	listOfMaterialCode.add(materialForm);
	}

if(!rsList.previous()){
	masterForm.setMessage("No Record Found..");
request.setAttribute("noRecords", "noRecords");
}
request.setAttribute("disablePreviousButton", "disablePreviousButton");
request.setAttribute("listOfMaterials", listOfMaterialCode);

		 } //end of Material Master
	 if(searchType.equals("Customer Master")){
		 
		 
		 String query1="select ACCOUNT_GROUP_M.ACCOUNT_GROUP_NAME as desca, sum( case when Approve_Status='Completed' then 1 else 0  end )  as completed ,	sum( case when Approve_Status='In Process' then 1 else 0  end )  as inprogress ,sum( case when Approve_Status='Rejected' then 1 else 0  end )  as rejected ,sum( case when Approve_Status='Submited' then 1 else 0  end )  as submitted ,	sum( case when Approve_Status='Completed' then 1 else 0  end )+sum( case when Approve_Status!='Created' and Approve_Status!='Completed' and Approve_Status!='Rejected' and Approve_Status!='Deleted'  then 1 else 0  end ) as total  "
					+ " from emp_official_info emp,Location loc ,CUSTOMER_MASTER_M left outer join  ACCOUNT_GROUP_M on ACCOUNT_GROUP_M.MATERIAL_TYPE_ID = CUSTOMER_MASTER_M.ACCOUNT_GROUP_ID  "
					+ " where   CUSTOMER_MASTER_M.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID   ";
					if(!Location.equalsIgnoreCase("")){	
						query1=query1+ " and loc.LOCID='"+Location+"'";
					}
					
					if(!From.equalsIgnoreCase("")){	
						query1=query1+ " and CUSTOMER_MASTER_M.REQUEST_DATE between '"+From+"' and '"+To+"'";
					}
					
					/*if(!From1.equalsIgnoreCase("")){	
						query1=query1+ " and (CUSTOMER_MASTER_M.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
					}*/
					/*if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
			{
				query1=query1+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
			}*/    
					
					query1=query1+" group by ACCOUNT_GROUP_M.ACCOUNT_GROUP_NAME ";
			
			ResultSet rsQuerysumm=ad.selectQuery(query1);
			ArrayList sumlist = new ArrayList();
			while(rsQuerysumm.next())
			{
				SAPReportForm form1 = new SAPReportForm();
				form1.setSearchType(rsQuerysumm.getString("desca"));
				form1.setStartRecord(rsQuerysumm.getInt("completed"));
				form1.setEndRecord(rsQuerysumm.getInt("inprogress"));
				form1.setMidRecord(rsQuerysumm.getInt("submitted"));
				form1.setrejRecord(rsQuerysumm.getInt("rejected"));
				form1.setTotalRecords(rsQuerysumm.getInt("total"));
				sumlist.add(form1);
				
			}
			request.setAttribute("sumlist", sumlist);
		 
		    LinkedList listOfCustomerCode=new LinkedList();
			String  queryCount="select count(*) from CUSTOMER_MASTER_M cus,emp_official_info emp,Location loc where cus.CREATED_BY=emp.PERNR and loc.LOCATION_CODE=emp.LOCID ";
			if(!Location.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and loc.LOCID='"+Location+"'";
			}
			if(!Materialcode.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and cus.ACCOUNT_GROUP_ID='"+Materialcode+"'";
			}
				
			if(!From.equalsIgnoreCase("")){	
					queryCount=queryCount+ " and cus.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
			/*if(!From1.equalsIgnoreCase("")){	
				queryCount=queryCount+ " and (cus.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
			}*/
			
			if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
			{
				queryCount=queryCount+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
			{
				queryCount=queryCount+ " and  Approve_Status = 'Completed' ";
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
			{
				queryCount=queryCount+ " and  Approve_Status = 'Rejected' ";
			}
			
			if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
				
			{
				queryCount=queryCount+ " and  Approve_Status not in ('Rejected','Completed','Saved') ";
			}
			
			/*if(!masterForm.getApprover().equalsIgnoreCase(""))
			{
				queryCount=queryCount+ " and  request_no in (select Req_Id "
						+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Customer Master') ";
			}*/

		 
				ResultSet rsQueryCount=ad.selectQuery(queryCount);
				int queryTotal=0;
				 while(rsQueryCount.next())
				 {
					 queryTotal=rsQueryCount.getInt(1);
				 }
				 if(queryTotal>10)
				 {
					 masterForm.setTotalRecords(queryTotal);
				 startRecord=1;
				 endRecord=10;
				 masterForm.setStartRecord(1);
				 masterForm.setEndRecord(10);
				 request.setAttribute("displayRecordNo", "displayRecordNo");
				 request.setAttribute("nextButton", "nextButton");
				 }else
				 {
					  startRecord=1;
					  endRecord=totalRecords;
					  masterForm.setTotalRecords(totalRecords);
					  masterForm.setStartRecord(1);
					  masterForm.setEndRecord(totalRecords); 
				 }	 
		startRecord=1;
		endRecord=10;
	String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.ID) AS RowNum, cus.REQUEST_NO,cus.REQUEST_DATE,cus.NAME,cus.CITY,emp.LOCID,"
	+ "emp.EMP_FULLNAME,cus.Approve_Status,cus.SAP_CODE_NO,cus.last_approver,cus.pending_approver,cus.SAP_CREATION_DATE from CUSTOMER_MASTER_M cus,emp_official_info emp,Location loc "
	+ "where cus.CREATED_BY=emp.PERNR and emp.LOCID =loc.LOCATION_CODE ";
	if(!Location.equalsIgnoreCase("")){	
		query=query+ " and loc.LOCID='"+Location+"' ";
	}
	if(!Materialcode.equalsIgnoreCase("")){	
		query=query+ " and cus.ACCOUNT_GROUP_ID='"+Materialcode+"'";
	}
	if(!From.equalsIgnoreCase("")){	
	query=query+ " and cus.REQUEST_DATE between '"+From+"' and '"+To+"'";
	}
	
	/*if(!From1.equalsIgnoreCase("")){	
		query=query+ " and (cus.SAP_CREATION_DATE between '"+From1+"' and '"+To1+"') ";
	}
	*/
	if(!masterForm.getRequestNumber().equalsIgnoreCase(""))
			{
				query=query+ " and request_no = '"+masterForm.getRequestNumber()+"' "; 
			}
	
	if(masterForm.getReq_Status().equalsIgnoreCase("Completed"))
	{
		query=query+ " and  Approve_Status = 'Completed' ";
	}
	
	if(masterForm.getReq_Status().equalsIgnoreCase("Rejected"))
	{
		query=query+ " and  Approve_Status = 'Rejected' ";
	}
	
	if(masterForm.getReq_Status().equalsIgnoreCase("Submitted")||masterForm.getReq_Status().equalsIgnoreCase("In Process"))
		
	{
		query=query+ " and  Approve_Status not in ('Rejected','Completed','Saved') ";
	}
	
	if(!masterForm.getApprover().equalsIgnoreCase(""))
	{
		queryCount=queryCount+ " and  request_no in (select Req_Id "
				+ " from All_Request where ( last_Approver='"+masterForm.getApprover()+"' or actual_approver='"+masterForm.getApprover()+"' ) and Req_Type='Customer Master') ";
	}
	
	query=query+ ")as sub Where  sub.RowNum between 1 and 10 order by REQUEST_NO ";
	ResultSet rsList=ad.selectQuery(query);
	while(rsList.next()){
		CustomerMasterForm c=new CustomerMasterForm();
		c.setRequestNo(rsList.getString("REQUEST_NO"));
		c.setRequestDate(EMicroUtils.display(rsList.getDate("REQUEST_DATE")));
		c.setCustomerName(rsList.getString("NAME"));
		c.setCity(rsList.getString("CITY"));
		c.setLocationId(rsList.getString("LOCID"));
		c.setRequestedBy(rsList.getString("EMP_FULLNAME"));
		c.setApproveType(rsList.getString("Approve_Status"));
		String sapcodeno=rsList.getString("SAP_CODE_NO");
		if(sapcodeno!=null){
		 c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
		 c.setSapCreationDate("");
		}
		else
			c.setSapCodeNo("");	
		
		String sapCodeNo=rsList.getString("SAP_CODE_NO");
		String requestNo=rsList.getString("REQUEST_NO");
		String approveStatus=rsList.getString("Approve_Status");
		String approverList="";
		String codeCreationDate="";
		
		String approverDet="select all_r.Approved_Persons,emp.EMP_FULLNAME,Req_Status,approved_date,rejected_date "
		+ "from All_Request all_r,emp_official_info emp  where Req_Id='"+requestNo+"'   and all_r.Approved_Persons!='No'  and Req_Type='Customer Master'  "
		+ " and all_r.Approved_Persons=emp.PERNR";
		ResultSet rsApp=ad.selectQuery(approverDet);
		try{
			while(rsApp.next()){
				approverList=approverList+rsApp.getString("EMP_FULLNAME");
				String status=rsApp.getString("Req_Status");
				if(status.equals("Approved")||status.equals("Completed"))
					approverList=approverList+"-"+rsApp.getString("approved_date")+" ";
				else if(status.equals("Rejected"))
					approverList=approverList+"-"+rsApp.getString("rejected_date")+" ";
				
				if(status.equals("Completed"))
					codeCreationDate=getDateFormate(rsApp.getDate("approved_date"));
				else if(status.equals("Rejected"))
					codeCreationDate=getDateFormate(rsApp.getDate("rejected_date")); 
					
			}
			//if(!approverList.equals(""))
			//approverList=approverList.substring(0, (approverList.length()-1));
			c.setApproversList(approverList);
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		if(sapCodeNo!=null){
			c.setSapCodeNo(rsList.getString("SAP_CODE_NO"));
			c.setCodeCreationDate(codeCreationDate);
		}
		else{
			c.setSapCodeNo("");
			c.setCodeCreationDate("");
		}
		String sapCrDt=rsList.getString("SAP_CREATION_DATE");
		if(sapCrDt!=null){
			if(!sapCrDt.equals("")){
				c.setSapCreationDate(codeCreationDate);
			}else
				c.setSapCreationDate("");
				
		}else{
			c.setSapCreationDate("");
		}
		String lastApprover=rsList.getString("last_approver");
		if(lastApprover!=null)
		if(!lastApprover.equals("No"))
		 c.setLastApprover(rsList.getString("last_approver"));
		else
			c.setLastApprover("");	
		String pendingAppr=rsList.getString("pending_approver");
		if(pendingAppr!=null)
		if(!pendingAppr.equals("No"))
			c.setPendingApprover(rsList.getString("pending_approver"));
		else
			c.setPendingApprover("");
		listOfCustomerCode.add(c);
	}
	
	if(!rsList.previous()){
		masterForm.setMessage("No Record Found..");
	request.setAttribute("noCustRecords", "noCustRecords");
	}
	request.setAttribute("disablePreviousButton", "disablePreviousButton");
    request.setAttribute("listOfCustomers", listOfCustomerCode);
	 }//end of Customer Master
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("display");
}
	public String getDateFormate(Date dNow){
	
	SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
	String dateNow = ft.format(dNow);
	return dateNow;
	}
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		SAPReportForm masterForm=(SAPReportForm)form;
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			EssDao ad=new EssDao();
		try{
			LinkedList listOfMaterialCode=new LinkedList();
			
			
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			//vendor type
			 ArrayList accountGroupList=new ArrayList();
				ArrayList accountGroupLabelList=new ArrayList();
			 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		while(rs.next()) {
			accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
			accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
		}
		masterForm.setAccountGroupList(accountGroupList);
		masterForm.setAccountGroupLabelList(accountGroupLabelList);
		
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
	}catch(Exception e){
		e.printStackTrace();
	
	}
		
				ArrayList techList=new ArrayList();
				ArrayList techLabelList=new ArrayList();
				ResultSet rs13 = ad.selectQuery(" select app,emp.EMP_FULLNAME from ( select Approver_Id as app from Material_Approvers where role ='Creator' and Approver_Id!='' group by Approver_Id union select Parllel_Approver_1 from Material_Approvers where role ='Creator' and Parllel_Approver_1!='' group by Parllel_Approver_1 union select Parllel_Approver_2 from Material_Approvers where role ='Creator' and Parllel_Approver_2!='' group by Parllel_Approver_2)t,emp_official_info as emp where emp.PERNR=App  and emp.ACTIVE =1 order  by emp.EMP_FULLNAME"); 
						try {
							while(rs13.next()) {
								techList.add(rs13.getString("App"));
								techLabelList.add(rs13.getString("EMP_FULLNAME"));
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						masterForm.setTechidList(techList);
						masterForm.setTechnameList(techLabelList);
		
		return mapping.findForward("display");
	}
}
