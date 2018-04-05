package com.microlabs.ess.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.MaterialHistoryForm;

public class MaterialHistoryAction extends DispatchAction{
	
	
	public ActionForward showVendorHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		MaterialHistoryForm mat=(MaterialHistoryForm)form;
		MaterialHistoryForm m=new MaterialHistoryForm();
		EssDao ad=new EssDao();
		String accountGroupID="";
		String viewType="";
		String elgTds="";
		String isRegistVendor="";
		 String isApproveVendor="";
		 String typeofVendor="";
		String reqId=request.getParameter("ReqestNo");
		String getVendorDetails="select  v.REQUEST_NO,v.REQUEST_DATE,v.CREATED_BY,acc.ACCOUNT_GROUP_NAME,acccl.ACC_CLERK_DESC,v.VIEW_TYPE,v.Type_Of_Vendor," +
				"v.TITLE,v.NAME,v.ADDRESS1,v.ADDRESS2,v.ADDRESS3,v.ADDRESS4,v.CITY,v.PINCODE,v.LANDLINE_NO," +
				"v.MOBILE_NO,v.FAX_NO,v.EMAIL_ID,cur.ISOCD,rec.RECONCILIATION_ACCOUNT_NAME,  v.IS_ELIGIBLE_FOR_TDS,v.LST_NO,v.TIN_NO" +
				",v.CST_NO, v.IS_APPROVED_VENDOR,v.PAN_No,v.Service_Tax_Registration_No,v.IS_REGD_EXCISE_VENDOR," +
				"v.ECC_No,v.Excise_Reg_No,v.Excise_Range,v.Excise_Division,v.COMMISSIONERATE,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
				"SAP_CREATED_BY,REQUESTED_BY  from vendor_master_history as v ,ACCOUNT_GROUP_M as acc,ACC_CLERK_M as acccl ,Currency as cur," +
				"RECONCILIATION_ACCOUNT_M as rec  where REQUEST_NO='"+reqId+"' and role='user' and acc.ACCOUNT_GROUP_ID=v.ACCOUNT_GROUP_ID and  " +
				" cur.WAERS=v.CURRENCY_ID  and rec.RECONCILIATION_ACCOUNT_ID=v.RECONCILATION_ACT_ID " +
				" and  acccl.ACC_CLERK_ID=v.ACCOUNT_CLERK_ID";
		ResultSet rs=ad.selectQuery(getVendorDetails);
		try{
		if(rs.next())
		{
			String empNo=rs.getString("CREATED_BY");
 		 	String empname="";
 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
 			ResultSet rs1=ad.selectQuery(empNameDetails);
 			while(rs1.next()){
 				empname=rs1.getString("EMP_FULLNAME");
 			}
 			m.setRequestedBy(empname);
			m.setRequestNumber(rs.getString("REQUEST_NO"));
		    accountGroupID=rs.getString("ACCOUNT_GROUP_NAME");
		    accountGroupID=accountGroupID.replace(" ","");
			m.setAccountGroupId(accountGroupID);
			accountGroupID=rs.getString("ACCOUNT_GROUP_NAME");
			 String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 m.setRequestDate(reqDate);
			 viewType=rs.getString("VIEW_TYPE");
			 if(viewType.equalsIgnoreCase("1"))
			 {
			m.setPurchaseView("Purchase View");
			 }
			 if(viewType.equalsIgnoreCase("2"))
			 {
			m.setAccountView("Account View");
			 }
			 if(viewType.equalsIgnoreCase("3"))
			 {
					m.setPurchaseView("Purchase View");
			m.setAccountView("Account View");
			 }

			 m.setTitle(rs.getString("TITLE"));
			 m.setName(rs.getString("NAME"));
			 m.setAddress1(rs.getString("ADDRESS1"));
			m.setAddress2(rs.getString("ADDRESS2"));
			 m.setAddress3(rs.getString("ADDRESS3"));
			 m.setAddress4(rs.getString("ADDRESS4"));
			m.setCity(rs.getString("CITY"));
			 m.setPinCode(rs.getString("PINCODE"));
			m.setLandLineNo(rs.getString("LANDLINE_NO"));
			 m.setMobileNo(rs.getString("MOBILE_NO"));
			 m.setFaxNo(rs.getString("FAX_NO"));
			 m.setEmailId(rs.getString("EMAIL_ID"));
			 m.setCurrencyId(rs.getString("ISOCD"));
			 m.setReConcillationActId(rs.getString("RECONCILIATION_ACCOUNT_NAME"));
			elgTds=rs.getString("IS_ELIGIBLE_FOR_TDS");
			if(elgTds.equalsIgnoreCase("1"))
				m.setElgTds("YES");
			if(elgTds.equalsIgnoreCase("0"))
				m.setElgTds("No");
		
			 m.setLstNo(rs.getString("LST_NO"));
			 m.setTinNo(rs.getString("TIN_NO"));
			 m.setCstNo(rs.getString("CST_NO"));
			 m.setPanNo(rs.getString("PAN_No"));
			 m.setServiceTaxRegNo(rs.getString("Service_Tax_Registration_No"));
			 isRegistVendor=rs.getString("IS_REGD_EXCISE_VENDOR");
			 	if(isRegistVendor.equalsIgnoreCase("1"))
				{
				 m.setRegExciseVendor("Yes");
				 request.setAttribute("RegExciseVendor", "RegExciseVendor");
				}
				if(isRegistVendor.equalsIgnoreCase("0"))
				{
				m.setRegExciseVendor("No");
				}
			 m.setEccNo(rs.getString("ECC_No"));
			 m.setExciseRegNo(rs.getString("Excise_Reg_No"));
			 m.setExciseRange(rs.getString("Excise_Range"));
			 m.setCommissionerate(rs.getString("COMMISSIONERATE"));
			 m.setExciseDivision(rs.getString("Excise_Division"));
			 m.setAccountClerkId(rs.getString("ACC_CLERK_DESC"));
			 m.setIsApprovedVendor(rs.getString("IS_APPROVED_VENDOR"));
			 isApproveVendor=rs.getString("IS_APPROVED_VENDOR");
			if(isApproveVendor.equalsIgnoreCase("1"))
				m.setIsApprovedVendor("Yes");
			if(isApproveVendor.equalsIgnoreCase("0"))
				m.setIsApprovedVendor("No");
			 typeofVendor=rs.getString("Type_Of_Vendor");
			 if(typeofVendor.equalsIgnoreCase("I"))
				m.setTypeOfVendor("IMPORTER");
			 if(typeofVendor.equalsIgnoreCase("MD"))
				m.setTypeOfVendor("DEPOT MANUFACTURER");
			 if(typeofVendor.equalsIgnoreCase("M"))
				m.setTypeOfVendor("MANUFACTURER");
			 if(typeofVendor.equalsIgnoreCase("D"))
				m.setTypeOfVendor("DEALER");
			 if(typeofVendor.equalsIgnoreCase("FD"))
				m.setTypeOfVendor("FIRST STAGE DEALER OF INDIGENOUS");
			 if(typeofVendor.equalsIgnoreCase("SD"))
				m.setTypeOfVendor("SECOND STAGE DEALER OF INDIGENOUS");
				}
		//payment term
		String getVendorDetails4="select pay.PAYMENT_TERM_NAME from vendor_master_history as v,PAYMENT_TERM_M as pay	" +
				"where REQUEST_NO='"+reqId+"' and role='user' and pay.PAYMENT_TERM_ID=v.PAYMENT_TERM_ID";
				
		ResultSet rs4=ad.selectQuery(getVendorDetails4);
		if(rs4.next())
		{
			m.setPaymentTermId(rs4.getString("PAYMENT_TERM_NAME"));
		}
		
		//tds
		String getVendorDetails1="select tds.TDS_SECTION_DESC from vendor_master_history as v," +
				"TDS_SECTION_M as tds where REQUEST_NO='"+reqId+"' and role='user' and tds.TDS_SECTION_ID=v.TDS_CODE";
				
		ResultSet rs1=ad.selectQuery(getVendorDetails1);
		if(rs1.next())
		{
			 m.setTdsCode(rs1.getString("TDS_SECTION_DESC"));
		}
		
		//country
		String getVendorDetails2=" select cou.LANDX from vendor_master_history as v,Country as cou " +
				" where REQUEST_NO='"+reqId+"' and role='user' and cou.LAND1=v.COUNTRY_ID ";
		ResultSet rs2=ad.selectQuery(getVendorDetails2);
		if(rs2.next())
		{
			m.setCountry(rs2.getString("LANDX"));
		}
		//state
		String getVendorDetails3=" select sta.BEZEI from vendor_master_history as v,State as sta " +
				"where REQUEST_NO='"+reqId+"' and role='user' and sta.BLAND=v.STATE";
		
		ResultSet rs3=ad.selectQuery(getVendorDetails3);
		if(rs3.next())
		{
		m.setState(rs3.getString("BEZEI"));
		}
		
		
		m.setAccountView_C("");
		m.setAccountGroupId_C("");
		m.setPurchaseView_C("");
		m.setTitle_C("");
		m.setName_C("");
		m.setPinCode_C("");
		m.setCountry_C("");
		m.setLandLineNo_C("");
		m.setReConcillationActId_C("");
		m.setElgTds_C("");
		m.setLstNo_C("");
		m.setTinNo_C("");
		m.setCstNo_C("");
		m.setPanNo_C("");
		m.setServiceTaxRegNo_C("");
		m.setRegExciseVendor_C("");
		m.setPaymentTermId_C("");
		m.setIsApprovedVendor_C("");
		m.setContentDescription_C("");
		m.setFileFullPath_C("");
		m.setVideoFullPath_C("");
		m.setCommissionerate_C("");
		m.setTypeOfVendor_C("");
		m.setAccGroupId_C("");
		m.setViewType_C("");
		m.setCustomerGroup_C("");
		m.setPriceGroup_C("");
		m.setPriceList_C("");
		m.setTaxType_C("");
		m.setListNumber_C("");
		m.setTinNumber_C("");
		m.setCstNumber_C("");
		m.setPanNumber_C("");
		m.setServiceTaxNo_C("");
		m.setIsRegdExciseVender_C("");
		m.setDlno1_C("");
		m.setDlno2_C("");
		m.setCreatedDate_C("");
		m.setCreatedBy_C("");
		m.setCustmerType_C("");
		m.setTdsStatus_C("");
		m.setSales_C("");
		m.setAccounts_C("");
		m.setDomestic_C("");
		m.setExports_C("");
		m.setCustomerType_C("");
		m.setCutomerCode_C("");
		m.setSearchRequired_C("");
		m.setCustomerName_C("");
		m.setAddress1_C("");
		m.setAddress2_C("");
		m.setAddress3_C("");
		m.setAddress4_C("");
		m.setCity_C("");
		m.setMobileNo_C("");
		m.setFaxNo_C("");
		m.setEmailId_C("");
		m.setEccNo_C("");
		m.setExciseRegNo_C("");
		m.setLinkName_C("");
		m.setExciseRange_C("");
		m.setExciseDivision_C("");
		m.setCurrencyId_C("");
		m.setAccountClerkId_C("");
		m.setTdsCode_C("");
		m.setCountryId_C("");
		m.setState_C("");

		String getCreator="select  v.REQUEST_NO,v.REQUEST_DATE,v.CREATED_BY,acc.ACCOUNT_GROUP_NAME,acccl.ACC_CLERK_DESC,v.VIEW_TYPE,v.Type_Of_Vendor," +
				"v.TITLE,v.NAME,v.ADDRESS1,v.ADDRESS2,v.ADDRESS3,v.ADDRESS4,v.CITY,v.PINCODE,v.LANDLINE_NO," +
				"v.MOBILE_NO,v.FAX_NO,v.EMAIL_ID,cur.ISOCD,rec.RECONCILIATION_ACCOUNT_NAME,  v.IS_ELIGIBLE_FOR_TDS,v.LST_NO,v.TIN_NO" +
				",v.CST_NO, v.IS_APPROVED_VENDOR,v.PAN_No,v.Service_Tax_Registration_No,v.IS_REGD_EXCISE_VENDOR," +
				"v.ECC_No,v.Excise_Reg_No,v.Excise_Range,v.Excise_Division,v.COMMISSIONERATE,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
				"SAP_CREATED_BY,REQUESTED_BY  from vendor_master_history as v ,ACCOUNT_GROUP_M as acc,ACC_CLERK_M as acccl ,Currency as cur," +
				"RECONCILIATION_ACCOUNT_M as rec  where REQUEST_NO='"+reqId+"' and role='Creator' and acc.ACCOUNT_GROUP_ID=v.ACCOUNT_GROUP_ID and  " +
				" cur.WAERS=v.CURRENCY_ID  and rec.RECONCILIATION_ACCOUNT_ID=v.RECONCILATION_ACT_ID " +
				" and  acccl.ACC_CLERK_ID=v.ACCOUNT_CLERK_ID";
		ResultSet rsC=ad.selectQuery(getCreator);
		if(rsC.next())
		{
			String empNo=rs.getString("CREATED_BY");
 		 	String empname="";
 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
 			 rs1=ad.selectQuery(empNameDetails);
 			while(rs1.next()){
 				empname=rs1.getString("EMP_FULLNAME");
 			}
 			m.setModifiedBy(empname);
			if(!accountGroupID.equals(rsC.getString("ACCOUNT_GROUP_NAME"))){
			    accountGroupID=rsC.getString("ACCOUNT_GROUP_NAME");
			    accountGroupID=accountGroupID.replace(" ","");
				m.setAccountGroupId_C(accountGroupID);
			}
			 String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 m.setRequestDate(reqDate);
			if(!viewType.equals(rsC.getString("VIEW_TYPE"))){	 
				 viewType=rsC.getString("VIEW_TYPE");
				 if(viewType.equalsIgnoreCase("1"))
				m.setPurchaseView_C("Purchase View");
				 if(viewType.equalsIgnoreCase("2"))
				m.setAccountView_C("Account View");
				 if(viewType.equalsIgnoreCase("3"))
				 {
						m.setPurchaseView("Purchase View");
				m.setAccountView_C("Account View");
				 }
			}
		 if(!m.getTitle().equals(rsC.getString("TITLE")))
			 m.setTitle_C(rsC.getString("TITLE"));
		 if(!m.getName().equals(rsC.getString("NAME")))
			 m.setName_C(rsC.getString("NAME"));
			if(!m.getAddress1().equals(rsC.getString("ADDRESS1")))
				m.setAddress1_C(rsC.getString("ADDRESS1"));
			if(!m.getAddress2().equals(rsC.getString("ADDRESS2")))
				m.setAddress2_C(rsC.getString("ADDRESS2"));
			if(!m.getAddress3().equals(rsC.getString("ADDRESS3")))
				m.setAddress3_C(rsC.getString("ADDRESS3"));
			if(!m.getAddress4().equals(rsC.getString("ADDRESS4")))
				m.setAddress4_C(rsC.getString("ADDRESS4"));
			if(!m.getCity().equals(rsC.getString("CITY")))
				m.setCity_C(rsC.getString("CITY"));
			if(!m.getPinCode().equals(rsC.getString("PINCODE")))
				m.setPincode_C(rsC.getString("PINCODE"));
			if(!m.getLandLineNo().equals(rsC.getString("LANDLINE_NO")))
				m.setLandLineNo_C(rsC.getString("LANDLINE_NO"));
			if(!m.getMobileNo().equals(rsC.getString("MOBILE_NO")))
			 m.setMobileNo_C(rsC.getString("MOBILE_NO"));
			if(!m.getFaxNo().equals(rsC.getString("FAX_NO")))
			 m.setFaxNo_C(rsC.getString("FAX_NO"));
			if(!m.getEmailId().equals(rsC.getString("EMAIL_ID")))
			 m.setEmailId_C(rsC.getString("EMAIL_ID"));
			if(!m.getCurrencyId().equals(rsC.getString("ISOCD")))
			 m.setCurrencyId_C(rsC.getString("ISOCD"));
			if(!m.getReConcillationActId().equals(rsC.getString("RECONCILIATION_ACCOUNT_NAME")))
			 m.setReConcillationActId_C(rsC.getString("RECONCILIATION_ACCOUNT_NAME"));
			if(!elgTds.equals(rsC.getString("IS_ELIGIBLE_FOR_TDS"))){
				elgTds=rsC.getString("IS_ELIGIBLE_FOR_TDS");
				if(elgTds.equalsIgnoreCase("1"))
					m.setElgTds_C("YES");
				if(elgTds.equalsIgnoreCase("0"))
					m.setElgTds_C("No");
			}
			if(!m.getLstNo().equals(rsC.getString("LST_NO")))
			 m.setLstNo_C(rsC.getString("LST_NO"));
			if(!m.getTinNo().equals(rsC.getString("TIN_NO")))
			 m.setTinNo_C(rsC.getString("TIN_NO"));
			if(!m.getCstNo().equals(rsC.getString("CST_NO")))
			 m.setCstNo_C(rsC.getString("CST_NO"));
			if(!m.getPanNo().equals(rsC.getString("PAN_No")))
			 m.setPanNo_C(rsC.getString("PAN_No"));
			if(!m.getServiceTaxRegNo().equals(rsC.getString("Service_Tax_Registration_No")))
			 m.setServiceTaxRegNo_C(rsC.getString("Service_Tax_Registration_No"));
			if(!isRegistVendor.equals(rsC.getString("IS_REGD_EXCISE_VENDOR"))){
			 isRegistVendor=rsC.getString("IS_REGD_EXCISE_VENDOR");
			 	if(isRegistVendor.equalsIgnoreCase("1"))
				{
				 m.setRegExciseVendor_C("Yes");
				 request.setAttribute("RegExciseVendor", "RegExciseVendor");
				}
				if(isRegistVendor.equalsIgnoreCase("0"))
				{
				m.setRegExciseVendor_C("No");
				}
			}
			if(!m.getEccNo().equals(rsC.getString("ECC_No")))
			 m.setEccNo_C(rsC.getString("ECC_No"));
			if(!m.getExciseRegNo().equals(rsC.getString("Excise_Reg_No")))
			 m.setExciseRegNo_C(rsC.getString("Excise_Reg_No"));
			if(!m.getExciseRange().equals(rsC.getString("Excise_Range")))
			 m.setExciseRange_C(rsC.getString("Excise_Range"));
			if(!m.getCommissionerate().equals(rsC.getString("COMMISSIONERATE")))
			 m.setCommissionerate_C(rsC.getString("COMMISSIONERATE"));
			if(!m.getExciseDivision().equals(rsC.getString("Excise_Division")))
			 m.setExciseDivision_C(rsC.getString("Excise_Division"));
			if(!m.getAccountClerkId().equals(rsC.getString("ACC_CLERK_DESC")))
			 m.setAccountClerkId_C(rsC.getString("ACC_CLERK_DESC"));
			if(!m.getIsApprovedVendor().equals(rsC.getString("IS_APPROVED_VENDOR")))
			 m.setIsApprovedVendor_C(rsC.getString("IS_APPROVED_VENDOR"));
			if(!isApproveVendor.equals(rsC.getString("IS_APPROVED_VENDOR"))){
				 isApproveVendor=rsC.getString("IS_APPROVED_VENDOR");
				if(isApproveVendor.equalsIgnoreCase("1"))
					m.setIsApprovedVendor_C("Yes");
				if(isApproveVendor.equalsIgnoreCase("0"))
					m.setIsApprovedVendor_C("No");
			}
			if(!typeofVendor.equals(rsC.getString("Type_Of_Vendor"))){
			 typeofVendor=rsC.getString("Type_Of_Vendor");
			 if(typeofVendor.equalsIgnoreCase("I"))
				m.setTypeOfVendor_C("IMPORTER");
			 if(typeofVendor.equalsIgnoreCase("MD"))
				m.setTypeOfVendor_C("DEPOT MANUFACTURER");
			 if(typeofVendor.equalsIgnoreCase("M"))
				m.setTypeOfVendor_C("MANUFACTURER");
			 if(typeofVendor.equalsIgnoreCase("D"))
				m.setTypeOfVendor_C("DEALER");
			 if(typeofVendor.equalsIgnoreCase("FD"))
				m.setTypeOfVendor_C("FIRST STAGE DEALER OF INDIGENOUS");
			 if(typeofVendor.equalsIgnoreCase("SD"))
				m.setTypeOfVendor_C("SECOND STAGE DEALER OF INDIGENOUS");
			}
			m.setRequestedBy_C(rsC.getString("REQUESTED_BY"));
		}
		
		
		//payment term
				 getVendorDetails4="select pay.PAYMENT_TERM_NAME from vendor_master_history as v,PAYMENT_TERM_M as pay	" +
						"where REQUEST_NO='"+reqId+"' and role='Creator' and pay.PAYMENT_TERM_ID=v.PAYMENT_TERM_ID";
				 rsC=ad.selectQuery(getVendorDetails4);
				if(rsC.next())
				{
					if(!m.getPaymentTermId().equals(rsC.getString("PAYMENT_TERM_NAME")))
						m.setPaymentTermId_C(rsC.getString("PAYMENT_TERM_NAME"));
				}
				//tds
				 getVendorDetails1="select tds.TDS_SECTION_DESC from vendor_master_history as v," +
						"TDS_SECTION_M as tds where REQUEST_NO='"+reqId+"' and role='Creator' and tds.TDS_SECTION_ID=v.TDS_CODE";
						
				 rsC=ad.selectQuery(getVendorDetails1);
				if(rsC.next())
				{
					if(!m.getTdsCode().equals(rsC.getString("TDS_SECTION_DESC")))
						m.setTdsCode_C(rsC.getString("TDS_SECTION_DESC"));
				}
				//country
				 getVendorDetails2=" select cou.LANDX from vendor_master_history as v,Country as cou " +
						" where REQUEST_NO='"+reqId+"' and role='Creator' and cou.LAND1=v.COUNTRY_ID ";
				 rsC=ad.selectQuery(getVendorDetails2);
				if(rsC.next())
				{
					if(!m.getCountry().equals(rsC.getString("LANDX")))
						m.setCountry_C(rsC.getString("LANDX"));
				}
				//state
				 getVendorDetails3=" select sta.BEZEI from vendor_master_history as v,State as sta " +
						"where REQUEST_NO='"+reqId+"' and role='Creator' and sta.BLAND=v.STATE";
				
				 rsC=ad.selectQuery(getVendorDetails3);
				if(rsC.next())
				{
					if(!m.getState().equals(rsC.getString("BEZEI")))
						m.setState_C(rsC.getString("BEZEI"));
				}

		
		}catch(Exception e){
			e.printStackTrace();
		}
		LinkedList list=new LinkedList();
		list.add(m);
		request.setAttribute("vendordetails", list);
		return mapping.findForward("vendorMaster");
		
	}
	public ActionForward showCustomerHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		MaterialHistoryForm mat=(MaterialHistoryForm)form;
		MaterialHistoryForm m=new MaterialHistoryForm();
		String reqId=request.getParameter("ReqestNo");
		EssDao ad=new EssDao();
		String account_groupId="";
		String viewType="";
		String tdsStatus="";
		String isRegdExciseVender="";
		String editRecord="select c.REQUEST_NO,c.REQUEST_DATE,c.CREATED_BY,c.VIEW_TYPE,c.cutomer_code,c.Customer_Type,c.NAME,c.ACCOUNT_GROUP_ID, " +
	 	"c.ADDRESS1,c.ADDRESS2,c.ADDRESS3,c.ADDRESS4,c.IS_REGD_EXCISE_Customer,c.CITY,c.PINCODE,c.Attachments,c.LANDLINE_NO," +
	 	"c.MOBILE_NO,c.FAX_NO,c.EMAIL_ID,c.IS_REGD_EXCISE_VENDOR,c.LST_NO,c.TIN_NO,c.CST_NO,c.PAN_No,c.Service_Tax_Registration_No," +
	 	"c.ECC_No,c.Excise_Reg_No,c.Excise_Range,c.Excise_Division,c.DLNO1,c.DLNO2, SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
	 	"SAP_CREATED_BY,REQUESTED_BY from CUSTOMER_MASTER_HISTORY as c  where REQUEST_NO='"+reqId+"' and role='user'";
		try{
			ResultSet rsEditRecord=ad.selectQuery(editRecord);
			if(rsEditRecord.next())
			{
				String empNo=rsEditRecord.getString("CREATED_BY");
	 		 	String empname="";
	 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
	 			ResultSet rs1=ad.selectQuery(empNameDetails);
	 			while(rs1.next()){
	 				empname=rs1.getString("EMP_FULLNAME");
	 			}
	 		 	m.setRequestedBy(empname);
				m.setRequestNumber(rsEditRecord.getString("REQUEST_NO"));
				String reqDate=rsEditRecord.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				m.setRequestDate(reqDate);
				
				account_groupId=rsEditRecord.getString("ACCOUNT_GROUP_ID");
				if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IN"))
					m.setAccGroupId("Domestic");
				if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IM"))
					m.setAccGroupId("Export Customer");
				if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("FS"))
					m.setAccGroupId("Field Staff");
				if(rsEditRecord.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("7"))
					m.setAccGroupId("Plants");
				m.setSales("");
				m.setAccounts("");
				 viewType=rsEditRecord.getString("VIEW_TYPE");
				if(viewType.equalsIgnoreCase("S"))
					m.setSales("Sales");
				if(viewType.equalsIgnoreCase("A"))
					m.setAccounts("Accounts");
				if(viewType.equalsIgnoreCase("3"))
				{
					m.setSales("Sales");
					m.setAccounts("Accounts");
				}
				m.setDomestic("");
				m.setExports("");
				m.setCustomerType(rsEditRecord.getString("Customer_Type"));
				m.setCustomerName(rsEditRecord.getString("NAME"));
				m.setAddress1(rsEditRecord.getString("ADDRESS1"));
				m.setAddress2(rsEditRecord.getString("ADDRESS2"));
				m.setAddress3(rsEditRecord.getString("ADDRESS3"));
				m.setAddress4(rsEditRecord.getString("ADDRESS4"));
				m.setCity(rsEditRecord.getString("CITY"));
				m.setPincode(rsEditRecord.getString("PINCODE"));
				m.setLandlineNo(rsEditRecord.getString("LANDLINE_NO"));
				m.setMobileNo(rsEditRecord.getString("MOBILE_NO"));
				m.setFaxNo(rsEditRecord.getString("FAX_NO"));
				m.setEmailId(rsEditRecord.getString("EMAIL_ID"));
				
				tdsStatus=rsEditRecord.getString("IS_REGD_EXCISE_Customer");
				if(tdsStatus.equalsIgnoreCase("1"))
				{
					m.setTdsStatus("YES");
					request.setAttribute("setTdsState", "setTdsState");
				}
				if(tdsStatus.equalsIgnoreCase("0"))
					m.setTdsStatus("No");
				m.setListNumber(rsEditRecord.getString("LST_NO"));
				m.setTinNumber(rsEditRecord.getString("TIN_NO"));
				m.setCstNumber(rsEditRecord.getString("CST_NO"));
				m.setPanNumber(rsEditRecord.getString("PAN_No"));
				m.setServiceTaxNo(rsEditRecord.getString("Service_Tax_Registration_No"));
				isRegdExciseVender=rsEditRecord.getString("IS_REGD_EXCISE_VENDOR");
				if(isRegdExciseVender.equalsIgnoreCase("1"))
				{
					m.setIsRegdExciseVender("Yes");
					request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
				}
				if(isRegdExciseVender.equalsIgnoreCase("0"))
				{
					m.setIsRegdExciseVender("No");
					request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
				}
				m.setEccNo(rsEditRecord.getString("ECC_No"));
				m.setExciseRegNo(rsEditRecord.getString("Excise_Reg_No"));
				m.setExciseRange(rsEditRecord.getString("Excise_Range"));
				m.setExciseDivision(rsEditRecord.getString("Excise_Division"));
				m.setDlno1(rsEditRecord.getString("DLNO1"));
				m.setDlno2(rsEditRecord.getString("DLNO2"));
				m.setCutomerCode(rsEditRecord.getString("cutomer_code"));
					}
				
					//currency
			String editRecord2="select cur.ISOCD from CUSTOMER_MASTER_HISTORY as c,Currency as cur where REQUEST_NO='"+reqId+"' and role='user'  and cur.WAERS=c.CURRENCY_ID";
			ResultSet rsEditRecord2=ad.selectQuery(editRecord2);
			if(rsEditRecord2.next())
			{
				m.setCurrencyId(rsEditRecord2.getString("ISOCD"));
			}
			//payment term
			
			String editRecord3="select pay.PAYMENT_TERM_NAME from CUSTOMER_MASTER_HISTORY as c,PAYMENT_TERM_M as pay " +
					"where REQUEST_NO='"+reqId+"' and role='user' and pay.PAYMENT_TERM_ID=c.PAYMENT_TERM_ID";
	 		ResultSet rsEditRecord3=ad.selectQuery(editRecord3);
			if(rsEditRecord3.next())
			{
				m.setPaymentTermID(rsEditRecord3.getString("PAYMENT_TERM_NAME"));
			}
							
							//acc clerk
							
							String editRecord4="select acccl.ACC_CLERK_DESC from CUSTOMER_MASTER_HISTORY as c,ACC_CLERK_M as acccl " +
									"where REQUEST_NO='"+reqId+"' and role='user' and acccl.ACC_CLERK_ID=c.ACCOUNT_CLERK_ID";
							ResultSet rsEditRecord4=ad.selectQuery(editRecord4);
							if(rsEditRecord4.next())
							{
								m.setAccountClerkId(rsEditRecord4.getString("ACC_CLERK_DESC"));
							}
								
								//tds code
								
						String editRecord5="select tds.TDS_SECTION_DESC from CUSTOMER_MASTER_HISTORY as c,TDS_SECTION_M as tds" +
								" where REQUEST_NO='"+reqId+"' and role='user' and tds.TDS_SECTION_ID=c.TDS_CODE";
						ResultSet rsEditRecord5=ad.selectQuery(editRecord5);
						if(rsEditRecord5.next())
						{
							m.setTdsCode(rsEditRecord5.getString("TDS_SECTION_DESC"));
							
						}
									
									//country
						String editRecord6="select cou.LANDX from CUSTOMER_MASTER_HISTORY as c ,Country as cou " +
								"where REQUEST_NO='"+reqId+"' and role='user' and cou.LAND1=c.COUNTRY_ID ";
						ResultSet rsEditRecord6=ad.selectQuery(editRecord6);
						if(rsEditRecord6.next())
						{
							m.setCountryId(rsEditRecord6.getString("LANDX"));
							
						}
								
						//state
						String editRecord7=" select sta.BEZEI  from CUSTOMER_MASTER_HISTORY as c,State as sta " +
								"where REQUEST_NO='"+reqId+"' and role='user' and sta.BLAND=c.STATE";
					ResultSet rsEditRecord7=ad.selectQuery(editRecord7);
					if(rsEditRecord7.next())
					{
						m.setState(rsEditRecord7.getString("BEZEI"));
						
					}
							//customer grp
							String editRecord8="select cust.C_GROUP_NAME from CUSTOMER_MASTER_HISTORY as c,Customer_Group as cust " +
									"where REQUEST_NO='"+reqId+"' and role='user' and cust.C_GROUP_ID=c.Customer_Group";
						ResultSet rsEditRecord8=ad.selectQuery(editRecord8);
						if(rsEditRecord8.next())
						{
							m.setCustomerGroup(rsEditRecord8.getString("C_GROUP_NAME"));
							
						}
						
						//price grp
						String editRecord9="select pri.P_GROUP_NAME from CUSTOMER_MASTER_HISTORY as c,PRICE_GROUP as pri " +
								"where REQUEST_NO='"+reqId+"' and role='user' and pri.P_GROUP_ID=c.Price_Group";
			 		
			 						 		
					ResultSet rsEditRecord9=ad.selectQuery(editRecord9);
					if(rsEditRecord9.next())
					{
						m.setPriceGroup(rsEditRecord9.getString("P_GROUP_NAME"));
						
					}
					//price list
					String editRecord10="select prili.P_LIST_NAME from CUSTOMER_MASTER_HISTORY as c,PRICE_LIST as  prili  " +
							"where REQUEST_NO='"+reqId+"' and role='user' and  prili.P_List_ID=c.Price_List";
		 		ResultSet rsEditRecord10=ad.selectQuery(editRecord10);
				if(rsEditRecord10.next())
				{
					m.setPriceList(rsEditRecord10.getString("P_LIST_NAME"));
					
				}
				
				//tax type
				String editRecord11="select tax.T_CLASS_NAME from CUSTOMER_MASTER_HISTORY as c,TAX_CLASS as tax" +
						" where REQUEST_NO='"+reqId+"' and role='user' and  tax.T_CLASS_ID=c.Tax_Type";
	 		ResultSet rsEditRecord11=ad.selectQuery(editRecord11);
			if(rsEditRecord11.next())
			{
				m.setTaxType(rsEditRecord11.getString("T_CLASS_NAME"));
				
			}
			
		//
			m.setAccGroupId_C("");
			m.setViewType_C("");
			m.setPincode_C("");
			m.setLandlineNo_C("");
			m.setCustomerGroup_C("");
			m.setPriceGroup_C("");
			m.setPriceList_C("");
			m.setTaxType_C("");
			m.setListNumber_C("");
			m.setTinNumber_C("");
			m.setCstNumber_C("");
			m.setPanNumber_C("");
			m.setServiceTaxNo_C("");
			m.setIsRegdExciseVender_C("");
			m.setDlno1_C("");
			m.setDlno2_C("");
			m.setPaymentTermID_C("");
			m.setCreatedDate_C("");
			m.setCreatedBy_C("");
			m.setCustmerType_C("");
			m.setTdsStatus_C("");
			m.setSales_C("");
			m.setAccounts_C("");
			m.setDomestic_C("");
			m.setExports_C("");
			m.setCustomerType_C("");
			m.setCutomerCode_C("");
			m.setSearchRequired_C("");
			m.setCustomerName_C("");
			m.setAddress1_C("");
			m.setAddress2_C("");
			m.setAddress3_C("");
			m.setAddress4_C("");
			m.setCity_C("");
			m.setMobileNo_C("");
			m.setFaxNo_C("");
			m.setEmailId_C("");
			m.setEccNo_C("");
			m.setExciseRegNo_C("");
			m.setLinkName_C("");
			m.setExciseRange_C("");
			m.setExciseDivision_C("");
			m.setCurrencyId_C("");
			m.setAccountClerkId_C("");
			m.setTdsCode_C("");
			m.setCountryId_C("");
			m.setState_C("");
			
			String editCreator="select c.REQUEST_NO,c.REQUEST_DATE,c.CREATED_BY,c.VIEW_TYPE,c.cutomer_code,c.Customer_Type,c.NAME,c.ACCOUNT_GROUP_ID, " +
		 	"c.ADDRESS1,c.ADDRESS2,c.ADDRESS3,c.ADDRESS4,c.IS_REGD_EXCISE_Customer,c.CITY,c.PINCODE,c.Attachments,c.LANDLINE_NO," +
		 	"c.MOBILE_NO,c.FAX_NO,c.EMAIL_ID,c.IS_REGD_EXCISE_VENDOR,c.LST_NO,c.TIN_NO,c.CST_NO,c.PAN_No,c.Service_Tax_Registration_No," +
		 	"c.ECC_No,c.Excise_Reg_No,c.Excise_Range,c.Excise_Division,c.DLNO1,c.DLNO2, SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
		 	"SAP_CREATED_BY,REQUESTED_BY from CUSTOMER_MASTER_HISTORY as c  where REQUEST_NO='"+reqId+"' and role='Creator' ";
			ResultSet rsC=ad.selectQuery(editCreator);
			if(rsC.next())
			{
				String empNo=rsC.getString("CREATED_BY");
	 		 	String empname="";
	 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
	 			ResultSet rs1=ad.selectQuery(empNameDetails);
	 			while(rs1.next()){
	 				empname=rs1.getString("EMP_FULLNAME");
	 			}
	 		 	m.setModifiedBy(empname);
				String reqDate=rsEditRecord.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				m.setModifiedDate(reqDate);
				if(!account_groupId.equals(rsC.getString("ACCOUNT_GROUP_ID"))){
				account_groupId=rsC.getString("ACCOUNT_GROUP_ID");
				if(rsC.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IN"))
					m.setAccGroupId_C("Domestic");
				if(rsC.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IM"))
					m.setAccGroupId_C("Export Customer");
				if(rsC.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("FS"))
					m.setAccGroupId_C("Field Staff");
				if(rsC.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("7"))
					m.setAccGroupId_C("Plants");
				
			}
		m.setSales_C("");
		m.setAccounts_C("");
			if(!viewType.equals(rsC.getString("VIEW_TYPE"))){
				 viewType=rsC.getString("VIEW_TYPE");
					if(viewType.equalsIgnoreCase("S"))
						m.setSales_C("Sales");
					if(viewType.equalsIgnoreCase("A"))
						m.setAccounts_C("Accounts");
					if(viewType.equalsIgnoreCase("3"))
					{
						m.setSales_C("Sales");
						m.setAccounts_C("Accounts");
					}
			}
		m.setDomestic_C("");
		m.setExports_C("");
		if(!m.getCustomerType().equals(rsC.getString("Customer_Type")))
			m.setCustomerType_C(rsC.getString("Customer_Type"));
		if(!m.getCustomerName().equals(rsC.getString("NAME")))
		m.setCustomerName_C(rsC.getString("NAME"));
		if(!m.getAddress1().equals(rsC.getString("ADDRESS1")))
			m.setAddress1_C(rsC.getString("ADDRESS1"));
		if(!m.getAddress2().equals(rsC.getString("ADDRESS2")))
			m.setAddress2_C(rsC.getString("ADDRESS2"));
		if(!m.getAddress3().equals(rsC.getString("ADDRESS3")))
			m.setAddress3_C(rsC.getString("ADDRESS3"));
		if(!m.getAddress4().equals(rsC.getString("ADDRESS4")))
			m.setAddress4_C(rsC.getString("ADDRESS4"));
		if(!m.getCity().equals(rsC.getString("CITY")))
			m.setCity_C(rsC.getString("CITY"));
		if(!m.getPincode().equals(rsC.getString("PINCODE")))
			m.setPincode_C(rsC.getString("PINCODE"));
		if(!m.getCity().equals(rsC.getString("LANDLINE_NO")))
			m.setCity_C(rsC.getString("LANDLINE_NO"));
		if(!m.getCity().equals(rsC.getString("MOBILE_NO")))
			m.setCity_C(rsC.getString("MOBILE_NO"));
		if(!m.getFaxNo().equals(rsC.getString("FAX_NO")))
			m.setFaxNo_C(rsC.getString("FAX_NO"));
		if(!m.getEmailId().equals(rsC.getString("EMAIL_ID")))
			m.setEmailId_C(rsC.getString("EMAIL_ID"));
		if(!tdsStatus.equals(rsC.getString("IS_REGD_EXCISE_Customer"))){
			tdsStatus=rsC.getString("IS_REGD_EXCISE_Customer");
			if(tdsStatus.equalsIgnoreCase("1"))
			{
				m.setTdsStatus_C("YES");
				request.setAttribute("setTdsState", "setTdsState");
			}
			if(tdsStatus.equalsIgnoreCase("0"))
				m.setTdsStatus_C("No");
		}
		
		if(!m.getListNumber().equals(rsC.getString("LST_NO")))
			m.setListNumber_C(rsC.getString("LST_NO"));
		if(!m.getTinNumber().equals(rsC.getString("TIN_NO")))
			m.setTinNumber_C(rsC.getString("TIN_NO"));
		if(!m.getCstNumber().equals(rsC.getString("CST_NO")))
			m.setCstNumber_C(rsC.getString("CST_NO"));
		if(!m.getPanNumber().equals(rsC.getString("PAN_No")))
			m.setPanNumber_C(rsC.getString("PAN_No"));
		if(!m.getServiceTaxNo().equals(rsC.getString("Service_Tax_Registration_No")))
			m.setServiceTaxNo_C(rsC.getString("Service_Tax_Registration_No"));
		
		if(!isRegdExciseVender.equals(rsC.getString("IS_REGD_EXCISE_VENDOR"))){
		isRegdExciseVender=rsC.getString("IS_REGD_EXCISE_VENDOR");
			if(isRegdExciseVender.equalsIgnoreCase("1"))
			{
				m.setIsRegdExciseVender_C("Yes");
				request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
			}
			if(isRegdExciseVender.equalsIgnoreCase("0"))
			{
				m.setIsRegdExciseVender_C("No");
				request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
			}
		}
		if(!m.getEccNo().equals(rsC.getString("ECC_No")))
			m.setEccNo_C(rsC.getString("ECC_No"));
		if(!m.getExciseRegNo().equals(rsC.getString("Excise_Reg_No")))
			m.setExciseRegNo_C(rsC.getString("Excise_Reg_No"));
		if(!m.getExciseRange().equals(rsC.getString("Excise_Range")))
			m.setExciseRange_C(rsC.getString("Excise_Range"));
		if(!m.getExciseDivision().equals(rsC.getString("Excise_Division")))
			m.setExciseDivision_C(rsC.getString("Excise_Division"));
		if(!m.getDlno1().equals(rsC.getString("DLNO1")))
			m.setDlno1_C(rsC.getString("DLNO1"));
		if(!m.getDlno2().equals(rsC.getString("DLNO2")))
			m.setDlno2_C(rsC.getString("DLNO2"));
		if(!m.getCutomerCode().equals(rsC.getString("cutomer_code")))
			m.setCutomerCode_C(rsC.getString("cutomer_code"));
		}
			
			//currency
			 editRecord2="select cur.ISOCD from CUSTOMER_MASTER_HISTORY as c,Currency as cur where REQUEST_NO='"+reqId+"' and role='Creator'  and cur.WAERS=c.CURRENCY_ID";
			 rsC=ad.selectQuery(editRecord2);
			if(rsC.next())
			{
		    if(!m.getCurrencyId().equals(rsC.getString("ISOCD")))
		    	m.setCurrencyId_C(rsC.getString("ISOCD"));
			}
			//payment term
			 editRecord3="select pay.PAYMENT_TERM_NAME from CUSTOMER_MASTER_HISTORY as c,PAYMENT_TERM_M as pay " +
			"where REQUEST_NO='"+reqId+"' and role='Creator' and pay.PAYMENT_TERM_ID=c.PAYMENT_TERM_ID";
			 rsC=ad.selectQuery(editRecord3);
			if(rsC.next())
			{
				if(!m.getPaymentTermID().equals(rsC.getString("PAYMENT_TERM_NAME")))
					m.setPaymentTermID_C(rsC.getString("PAYMENT_TERM_NAME"));
			}

			//acc clerk

			 editRecord4="select acccl.ACC_CLERK_DESC from CUSTOMER_MASTER_HISTORY as c,ACC_CLERK_M as acccl " +
			"where REQUEST_NO='"+reqId+"' and role='Creator' and acccl.ACC_CLERK_ID=c.ACCOUNT_CLERK_ID";
			 rsC=ad.selectQuery(editRecord4);
			if(rsC.next())
			{
				if(!m.getAccountClerkId().equals(rsC.getString("ACC_CLERK_DESC")))
					m.setAccountClerkId_C(rsC.getString("ACC_CLERK_DESC"));
			}

			//tds code

			 editRecord5="select tds.TDS_SECTION_DESC from CUSTOMER_MASTER_HISTORY as c,TDS_SECTION_M as tds" +
			" where REQUEST_NO='"+reqId+"' and role='Creator' and tds.TDS_SECTION_ID=c.TDS_CODE";
			 rsC=ad.selectQuery(editRecord5);
			if(rsC.next())
			{
				if(!m.getTdsCode().equals(rsC.getString("TDS_SECTION_DESC")))
					m.setTdsCode_C(rsC.getString("TDS_SECTION_DESC"));

			}

			//country
			 editRecord6="select cou.LANDX from CUSTOMER_MASTER_HISTORY as c ,Country as cou " +
			"where REQUEST_NO='"+reqId+"' and role='Creator' and cou.LAND1=c.COUNTRY_ID ";
			 rsC=ad.selectQuery(editRecord6);
			if(rsC.next())
			{
				if(!m.getCountryId().equals(rsC.getString("LANDX")))
					m.setCountryId_C(rsC.getString("LANDX"));

			}

			//state
			 editRecord7=" select sta.BEZEI  from CUSTOMER_MASTER_HISTORY as c,State as sta " +
			"where REQUEST_NO='"+reqId+"' and role='Creator' and sta.BLAND=c.STATE";
			 rsC=ad.selectQuery(editRecord7);
			if(rsC.next())
			{
				if(!m.getState().equals(rsC.getString("BEZEI")))
					m.setState_C(rsC.getString("BEZEI"));

			}
			//customer grp
			 editRecord8="select cust.C_GROUP_NAME from CUSTOMER_MASTER_HISTORY as c,Customer_Group as cust " +
			"where REQUEST_NO='"+reqId+"' and role='Creator' and cust.C_GROUP_ID=c.Customer_Group";
			 rsC=ad.selectQuery(editRecord8);
			if(rsC.next())
			{
				if(!m.getCustomerGroup().equals(rsC.getString("C_GROUP_NAME")))
					m.setCustomerGroup_C(rsC.getString("C_GROUP_NAME"));

			}

			//price grp
			 editRecord9="select pri.P_GROUP_NAME from CUSTOMER_MASTER_HISTORY as c,PRICE_GROUP as pri " +
			"where REQUEST_NO='"+reqId+"' and role='Creator' and pri.P_GROUP_ID=c.Price_Group";


			 rsC=ad.selectQuery(editRecord9);
			if(rsC.next())
			{
				if(!m.getPriceGroup().equals(rsC.getString("P_GROUP_NAME")))
					m.setPriceGroup_C(rsC.getString("P_GROUP_NAME"));

			}
			//price list
			 editRecord10="select prili.P_LIST_NAME from CUSTOMER_MASTER_HISTORY as c,PRICE_LIST as  prili  " +
			"where REQUEST_NO='"+reqId+"' and role='Creator' and  prili.P_List_ID=c.Price_List";
			 rsC=ad.selectQuery(editRecord10);
			if(rsC.next())
			{
				if(!m.getPriceList().equals(rsC.getString("P_LIST_NAME")))
					m.setPriceList_C(rsC.getString("P_LIST_NAME"));

			}

			//tax type
			 editRecord11="select tax.T_CLASS_NAME from CUSTOMER_MASTER_HISTORY as c,TAX_CLASS as tax" +
			" where REQUEST_NO='"+reqId+"' and role='Creator' and  tax.T_CLASS_ID=c.Tax_Type";
			 rsC=ad.selectQuery(editRecord11);
			if(rsC.next())
			{
				if(!m.getTaxType().equals(rsC.getString("T_CLASS_NAME")))
					m.setTaxType_C(rsC.getString("T_CLASS_NAME"));

			}
			LinkedList list=new LinkedList();
			list.add(m);
			request.setAttribute("customerdetails", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("customerMaster");
	}
	public ActionForward showServiceHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		MaterialHistoryForm mat=(MaterialHistoryForm)form;
		MaterialHistoryForm m=new MaterialHistoryForm();
		String reqId=request.getParameter("ReqestNo");
		EssDao ad=new EssDao();
		String sercat="";
		String query="select ser.CREATED_BY,ser.request_date,loc.LOCNAME,ser.service_description,ser.detailed_desc,u.LTXT," +
		"pur.PURCHASE_GROUP_DESC,ser.service_catagory,se.STXT,ser.machine_name,ser.app_value,ser.justification," +
		"val.VALUATION_DESC,ser.attachment from SERVICE_MASTER_HISTORY as ser,Location as loc,UNIT_MESUREMENT as u," +
		"PURCHASE_GROUP as pur, SERVICE_GROUP as se,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' and role='user' and	" +
		"loc.LOCID=ser.plant_code and u.UNIT_OF_MEAS_ID=ser.uom and pur.PURCHASE_GROUP_ID=ser.purchase_group " +
		"and se.group_id=ser.service_group and val.VALUATION_ID=ser.valuation_class";
		ResultSet rs=ad.selectQuery(query);
		try{
		if(rs.next()){
			m.setRequestNumber(reqId);
			String empNo=rs.getString("CREATED_BY");
 		 	String empname="";
 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
 			ResultSet rs1=ad.selectQuery(empNameDetails);
 			while(rs1.next()){
 				empname=rs1.getString("EMP_FULLNAME");
 			}
 		 	m.setRequestedBy(empname);
			m.setLocationId(rs.getString("LOCNAME"));
			m.setPlantCode(rs.getString("LOCNAME"));
			m.setRequestDate(rs.getString("request_date"));
			m.setServiceDescription(rs.getString("service_description"));
			m.setDetailedServiceDescription(rs.getString("detailed_desc"));
			m.setUom(rs.getString("LTXT"));
			m.setPurchaseGroup(rs.getString("PURCHASE_GROUP_DESC"));
			 sercat=rs.getString("service_catagory");
			if(sercat.equalsIgnoreCase("ZITA"))
				m.setServiceCatagory("ZITA- AMC-IT");
			if(sercat.equalsIgnoreCase("ZAMC"))
				m.setServiceCatagory("ZAMC- Annual maintainence");
			if(sercat.equalsIgnoreCase("ZCLB"))
				m.setServiceCatagory("ZCLB- Calibration");
			if(sercat.equalsIgnoreCase("ZCIV"))
				m.setServiceCatagory("ZCIV- Civil works");
			if(sercat.equalsIgnoreCase("ZMNT"))
				m.setServiceCatagory("ZMNT- Maintainence");
			if(sercat.equalsIgnoreCase("ZITM"))
				m.setServiceCatagory("ZITM-MAINAINENCE-IT");
			if(sercat.equalsIgnoreCase("ZMKT"))
				m.setServiceCatagory("ZMKT- Marketing");
			if(sercat.equalsIgnoreCase("ZTST"))
				m.setServiceCatagory("ZTST- Testing&Analysis");
			if(sercat.equalsIgnoreCase("ZTRC"))
				m.setServiceCatagory("ZTRC- Training&Recruitment");
			m.setServiceGroup(rs.getString("STXT"));
			m.setE_m_name(rs.getString("machine_name"));
			m.setApp_amount(rs.getString("app_value"));
			m.setJustification(rs.getString("justification"));
			m.setValuationClass(rs.getString("VALUATION_DESC"));
		}
		m.setServiceDescription_C("");
		m.setDetailedServiceDescription_C("");
		m.setUom_C("");
		m.setPurchaseGroup_C("");
		m.setServiceCatagory_C("");
		m.setServiceGroup_C("");
		m.setE_m_name_C("");
		m.setApp_amount_C("");
		m.setWhereUsed_C("");
		m.setPurpose_C("");
		m.setJustification_C("");
		
		String creatorDetails="select ser.CREATED_BY,ser.request_date,loc.LOCNAME,ser.service_description,ser.detailed_desc,u.LTXT," +
				"pur.PURCHASE_GROUP_DESC,ser.service_catagory,se.STXT,ser.machine_name,ser.app_value,ser.justification," +
				"val.VALUATION_DESC,ser.attachment from SERVICE_MASTER_HISTORY as ser,Location as loc,UNIT_MESUREMENT as u," +
				"PURCHASE_GROUP as pur, SERVICE_GROUP as se,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' and role='Creator' and	" +
				"loc.LOCID=ser.plant_code and u.UNIT_OF_MEAS_ID=ser.uom and pur.PURCHASE_GROUP_ID=ser.purchase_group " +
				"and se.group_id=ser.service_group and val.VALUATION_ID=ser.valuation_class";
		ResultSet rsC=ad.selectQuery(creatorDetails);
		if(rsC.next()){
			String empNo=rs.getString("CREATED_BY");
 		 	String empname="";
 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
 			ResultSet rs1=ad.selectQuery(empNameDetails);
 			while(rs1.next()){
 				empname=rs1.getString("EMP_FULLNAME");
 			}
 		 	m.setModifiedBy(empname);
			if(!m.getServiceDescription().equals(rsC.getString("service_description")))
				m.setServiceDescription_C(rsC.getString("service_description"));
			if(!m.getDetailedServiceDescription().equals(rsC.getString("detailed_desc")))
				m.setDetailedServiceDescription_C(rsC.getString("detailed_desc"));
			if(!m.getUom().equals(rsC.getString("LTXT")))
				m.setUom_C(rsC.getString("LTXT"));
			if(!m.getPurchaseGroup().equals(rs.getString("PURCHASE_GROUP_DESC")))
				m.setPurchaseGroup_C(rsC.getString("PURCHASE_GROUP_DESC"));
			 if(!sercat.equals(rsC.getString("service_catagory"))){
				 sercat=rsC.getString("service_catagory");
				 if(sercat.equalsIgnoreCase("ZITA"))
						m.setServiceCatagory_C("ZITA- AMC-IT");
					if(sercat.equalsIgnoreCase("ZAMC"))
						m.setServiceCatagory_C("ZAMC- Annual maintainence");
					if(sercat.equalsIgnoreCase("ZCLB"))
						m.setServiceCatagory_C("ZCLB- Calibration");
					if(sercat.equalsIgnoreCase("ZCIV"))
						m.setServiceCatagory_C("ZCIV- Civil works");
					if(sercat.equalsIgnoreCase("ZMNT"))
						m.setServiceCatagory_C("ZMNT- Maintainence");
					if(sercat.equalsIgnoreCase("ZITM"))
						m.setServiceCatagory_C("ZITM-MAINAINENCE-IT");
					if(sercat.equalsIgnoreCase("ZMKT"))
						m.setServiceCatagory_C("ZMKT- Marketing");
					if(sercat.equalsIgnoreCase("ZTST"))
						m.setServiceCatagory_C("ZTST- Testing&Analysis");
					if(sercat.equalsIgnoreCase("ZTRC"))
						m.setServiceCatagory_C("ZTRC- Training&Recruitment");
			 }
				if(!m.getServiceGroup().equals(rsC.getString("STXT"))) 
					m.setServiceGroup_C(rsC.getString("STXT"));
				if(!m.getE_m_name().equals(rsC.getString("machine_name")))
					m.setE_m_name_C(rsC.getString("machine_name"));
				if(!m.getApp_amount().equals(rsC.getString("app_value")))
					m.setApp_amount_C(rsC.getString("app_value"));
				if(!m.getJustification().equals(rsC.getString("justification")))
					m.setJustification_C(rsC.getString("justification"));
				if(!m.getValuationClass().equals(rsC.getString("VALUATION_DESC")))
					m.setValuationClass_C(rsC.getString("VALUATION_DESC"));
		}
		LinkedList list=new LinkedList();
		list.add(m);
		request.setAttribute("servicedetails", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("serviceMaster");
	}
	public ActionForward showHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		MaterialHistoryForm mat=(MaterialHistoryForm)form;
		
		MaterialHistoryForm m=new MaterialHistoryForm();
		String reqId=request.getParameter("ReqestNo");
		String matType=request.getParameter("matType");
		m.setMatType(matType);
		EssDao ad=new EssDao();
		
		if(matType.equalsIgnoreCase("FG")||matType.equalsIgnoreCase("HAWA"))
		{
			
			String domorexp="";
			String salorsam="";
			String matpri="";
			
			String getFinishedProduct="select mat.REQUEST_NO,mat.CREATED_BY,mat.CREATED_DATE,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,mat.MANUFACTURED_AT," +
		 	  "mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,mat.DOMESTIC_OR_EXPORTS,cou.LANDX,mat.CUSTOMER_NAME," +
		 	  "mat.SALEABLE_OR_SAMPLE,mat.SALES_PACK_ID,pac.P_TYPE_DESC,sal.S_UOM_DESC,div.DIV_DESC,the.THER_SEG_DESC,bran.BRAND_DESC, " +
		 	  "st.STRENGTH_DESC,gen.GEN_NAME_DESC,mat.GROSS_WEIGHT,mat.NET_WEIGHT,mat.DIMENSION," +
		 	  "mat.Material_Pricing,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ," +
		 	  "SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request_history as mat,Location as loc,MATERIAL_GROUP as matGroup ," +
		 	  "UNIT_MESUREMENT AS uom,Country as cou,PACK_TYPE as pac,SALES_UOM as sal,DIVISION as div,THERAPEUTIC_SEGMENT as the,BRAND as bran," +
		 	  "STRENGTH as st,GENERIC_NAME as gen " +
		 	  "where REQUEST_NO='"+reqId+"' and Role='user' and loc.LOCID=mat.LOCATION_ID  and " +
		 	  "cou.LAND1=mat.COUNTRY_ID and pac.P_TYPE_CODE=mat.PACK_TYPE_ID and sal.S_UOM_CODE=mat.SALES_UNIT_OF_MEAS_ID and " +
		 	  "uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID " +
		 	  "and the.THER_SEG_CODE=mat.THERAPEUTIC_SEGMENT_ID and bran.BRAND_CODE=mat.BRAND_ID and st.STRENGTH_CODE=mat.STRENGTH_ID" +
		 	  " and gen.GEN_NAME_CODE=mat.GENERIC_NAME ";
					ResultSet rs=ad.selectQuery(getFinishedProduct);
					try {
						if(rs.next())
						{

							m.setRequestNumber(reqId);
							String empNo=rs.getString("CREATED_BY");
				 		 	String empname="";
				 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
				 			ResultSet rs12=ad.selectQuery(empNameDetails);
				 			while(rs12.next()){
				 				empname=rs12.getString("EMP_FULLNAME");
				 			}
				 		 	m.setCreatedBy(empname);
							m.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
							matType=rs.getString("Type");
							m.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
							m.setLocationId(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							m.setRequestDate(reqDate);
							m.setLocationId(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
							
							
							String manufacturedAt=rs.getString("MANUFACTURED_AT");				
							
							m.setManufacturedAt(manufacturedAt);
							
							m.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							m.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							m.setMaterialGroupId(rs.getString("STXT"));
							
							m.setCountryId(rs.getString("LANDX"));
							m.setCustomerName(rs.getString("CUSTOMER_NAME"));
							m.setShelfLife(rs.getString("SHELF_LIFE"));
							m.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
							m.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
							m.setBatchCode(rs.getString("BATCH_CODE"));
							 salorsam=rs.getString("SALEABLE_OR_SAMPLE");
							if(salorsam.equalsIgnoreCase("1")){
								m.setSaleableOrSample("Saleable");
								}
								if(salorsam.equalsIgnoreCase("2")){
									m.setSaleableOrSample("Sample");
									}
							 domorexp=rs.getString("DOMESTIC_OR_EXPORTS");
							if(domorexp.equalsIgnoreCase("D")){
							m.setDomesticOrExports("DOMESTIC");
							}
							if(domorexp.equalsIgnoreCase("E")){
								m.setDomesticOrExports("EXPORTS");
								}
							if(domorexp.equalsIgnoreCase("V")){
								m.setDomesticOrExports("Validation");
								}
							
							String salesPackId=rs.getString("SALES_PACK_ID");
							m.setSalesPackId(rs.getString("SALES_PACK_ID"));
							m.setPackTypeId(rs.getString("P_TYPE_DESC"));
							m.setSalesUnitOfMeaseurement(rs.getString("S_UOM_DESC"));
							m.setDivisionId(rs.getString("DIV_DESC"));
							m.setTherapeuticSegmentID(rs.getString("THER_SEG_DESC"));
							m.setBrandID(rs.getString("BRAND_DESC"));
							m.setSrengthId(rs.getString("STRENGTH_DESC"));
							m.setGenericName(rs.getString("GEN_NAME_DESC"));
														
							 matpri=rs.getString("Material_Pricing");
							if(matpri.equalsIgnoreCase("1")){
								m.setMaterialPricing("Normal");
								}
							if(matpri.equalsIgnoreCase("2")){
								m.setMaterialPricing("Spare parts");
								}
							if(matpri.equalsIgnoreCase("11")){
								m.setMaterialPricing("Scheduled(Controled)");
								}
							if(matpri.equalsIgnoreCase("12")){
								m.setMaterialPricing("Un-Scheduled(De-Con)");
								}
							if(matpri.equalsIgnoreCase("13")){
								m.setMaterialPricing("PS / Promo (Micro)");
								}
							if(matpri.equalsIgnoreCase("14")){
								m.setMaterialPricing("No MRP ED Extra(Mic)");
								}
							if(matpri.equalsIgnoreCase("15")){
								m.setMaterialPricing("No MRP ED Incl (Mic)");
								}
							if(matpri.equalsIgnoreCase("16")){
								m.setMaterialPricing("Scrap IT Extra (Mic)");
								}
							if(matpri.equalsIgnoreCase("17")){
								m.setMaterialPricing("MRP(Con)-ED Exe(Mic)");
								}
							if(matpri.equalsIgnoreCase("18")){
								m.setMaterialPricing("MRP(DeC)-ED Exe(Mic)");
								}
							if(matpri.equalsIgnoreCase("19")){
								m.setMaterialPricing("No MRP-ED Exe(Mic)");
								}
							if(matpri.equalsIgnoreCase("20")){
								m.setMaterialPricing("Food Prod(MRP)-Micro)");
								}
							if(matpri.equalsIgnoreCase("21")){
								m.setMaterialPricing("Vet Prod (MRP)-Micr");
								}
							if(matpri.equalsIgnoreCase("22")){
								m.setMaterialPricing("Generic Price Grp-Mi");
								}
							if(matpri.equalsIgnoreCase("23")){
								m.setMaterialPricing("MRP(Vet)-ED Exe(Mic)");
								}
							if(matpri.equalsIgnoreCase("24")){
								m.setMaterialPricing("P-to-P ED Extra(Mic)");
								}
							if(matpri.equalsIgnoreCase("25")){
								m.setMaterialPricing("Cosm Prod(MRP)-Micro");
								}
							if(matpri.equalsIgnoreCase("26")){
								m.setMaterialPricing("Scrap - ED Exe(Mic))");
								}
							if(matpri.equalsIgnoreCase("27")){
								m.setMaterialPricing("Import Items PG");
								}
						
							m.setGrossWeight(rs.getString("GROSS_WEIGHT"));
							m.setNetWeight(rs.getString("NET_WEIGHT"));
							
							m.setDimension(rs.getString("DIMENSION"));
							m.setUnitOfMeasId(rs.getString("LTXT"));
							
				m.setStorageLocationId("");
				m.setWeightUOM("");
				m.setValuationClass("");
				//storage location	
				String getStorageLoc="select s.STORAGE_LOCATION_ID,s.STORAGE_LOCATION_NAME from material_code_request_history m,STORAGE_LOCATION s where "
						+ "REQUEST_NO='"+reqId+"' and Role='user' and m.STORAGE_LOCATION_ID=s.STORAGE_LOCATION_ID";
				ResultSet rsStLoc=ad.selectQuery(getStorageLoc);
				if(rsStLoc.next()){
					m.setStorageLocationId(rsStLoc.getString("STORAGE_LOCATION_ID")+"-"+rsStLoc.getString("STORAGE_LOCATION_NAME"));
				}
				//weight wom
				String getweight="select w.W_UOM_CODE,w.W_UOM_DESC from material_code_request_history m,WEIGHT_UOM w where REQUEST_NO='"+reqId+"' and Role='user' "
						+ "and m.WEIGHT_UOM=w.W_UOM_CODE";
				ResultSet rsWt=ad.selectQuery(getweight);
				if(rsWt.next()){
					m.setWeightUOM(rsWt.getString("W_UOM_CODE")+"-"+rsWt.getString("W_UOM_DESC"));
				}
				String getValuation="select v.VALUATION_DESC from material_code_request_history m,VALUATION_CLASS v where REQUEST_NO='"+reqId+"' and Role='user' "
						+ "and m.VALUATION_CLASS=v.VALUATION_ID";
				ResultSet rsValu=ad.selectQuery(getValuation);
				if(rsValu.next()){
					m.setValuationClass(rsValu.getString("VALUATION_DESC"));
				}			
				//purchase grp
				String getFinishedProduct1="select pur.PURCHASE_GROUP_DESC from material_code_request_history as mat,PURCHASE_GROUP as pur" +
						" where REQUEST_NO='"+reqId+"' and Role='user' and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID";
					ResultSet rs1=ad.selectQuery(getFinishedProduct1);
					if(rs1.next())
					{
						m.setPuchaseGroupId(rs1.getString("PURCHASE_GROUP_DESC"));
					}
					//tax type
					String editRecord11="select tax.T_CLASS_NAME from material_code_request_history as m,TAX_CLASS as tax" +
							" where REQUEST_NO='"+reqId+"'and Role='user' and  tax.T_CLASS_ID=m.Tax_Classification";
				ResultSet rsEditRecord11=ad.selectQuery(editRecord11);
				if(rsEditRecord11.next())
				{
					m.setTaxClassification(rsEditRecord11.getString("T_CLASS_NAME"));
					
				}
							
									}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					
					
					m.setDmfGradeId_C("");
					m.setMaterialGrade_C("");
					m.setCosGradeNo_C("");
					m.setAdditionalTest_C("");
					m.setCountryId_C("");
					m.setCustomerName_C("");
					m.setToBeUsedInProducts_C("");
					m.setIsVendorSpecificMaterial_C("");
					m.setMfgrName_C("");
					m.setSiteOfManufacture_C("");
					m.setTempCondition_C("");
					m.setStorageCondition_C("");
					m.setShelfLife_C("");
					m.setDutyElement_C("");
					m.setRetestDays_C("");
					m.setPharmacopName_C("");
					m.setPharmacopGrade_C("");
					m.setGenericName_C("");
					m.setSynonym_C("");
					m.setPharmacopSpecification_C("");
					m.setIsDMFMaterial_C("");
					m.setShelfLifeType_C("");
					m.setRetestType_C("");
					m.setReqMaterialGroup_C("");
			        m.setPackageMaterialGroup_C("");
					m.setTypeOfMaterial_C("");	
					m.setArtworkNo_C("");	
					m.setIsArtworkRevision_C("");
					m.setPackSize_C("");
					m.setTargetWeight_C("");
					m.setStandardBatchSize_C("");
					m.setBatchCode_C("");
					m.setWeightUOM_C("");
					
					String getFinishedProductCREA="select mat.REQUEST_NO,mat.CREATED_BY,mat.CREATED_DATE,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,mat.MANUFACTURED_AT," +
		 	  "mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,mat.DOMESTIC_OR_EXPORTS,cou.LANDX,mat.CUSTOMER_NAME," +
		 	  "mat.SALEABLE_OR_SAMPLE,mat.SALES_PACK_ID,pac.P_TYPE_DESC,sal.S_UOM_DESC,div.DIV_DESC,the.THER_SEG_DESC,bran.BRAND_DESC, " +
		 	  "st.STRENGTH_DESC,gen.GEN_NAME_DESC,mat.GROSS_WEIGHT,mat.NET_WEIGHT,mat.DIMENSION," +
		 	  "mat.Material_Pricing,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ," +
		 	  "SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request_history as mat,Location as loc,MATERIAL_GROUP as matGroup ," +
		 	  "UNIT_MESUREMENT AS uom,Country as cou,PACK_TYPE as pac,SALES_UOM as sal,DIVISION as div,THERAPEUTIC_SEGMENT as the,BRAND as bran," +
		 	  "STRENGTH as st,GENERIC_NAME as gen " +
		 	  "where REQUEST_NO='"+reqId+"' and Role='Creator' and loc.LOCID=mat.LOCATION_ID  and " +
		 	  "cou.LAND1=mat.COUNTRY_ID and pac.P_TYPE_CODE=mat.PACK_TYPE_ID and sal.S_UOM_CODE=mat.SALES_UNIT_OF_MEAS_ID and " +
		 	  "uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID " +
		 	  "and the.THER_SEG_CODE=mat.THERAPEUTIC_SEGMENT_ID and bran.BRAND_CODE=mat.BRAND_ID and st.STRENGTH_CODE=mat.STRENGTH_ID" +
		 	  " and gen.GEN_NAME_CODE=mat.GENERIC_NAME ";
							ResultSet rsC=ad.selectQuery(getFinishedProductCREA);
							try {
								if(rsC.next())								
								{
									String reqDate=rsC.getString("CREATED_DATE");
									String empNo=rs.getString("CREATED_BY");
						 		 	String empname="";
						 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
						 			ResultSet rs12=ad.selectQuery(empNameDetails);
						 			while(rs12.next()){
						 				empname=rs12.getString("EMP_FULLNAME");
						 			}
						 		 	m.setModifiedBy(empname);
									String b[]=reqDate.split("-");
									reqDate=b[2]+"/"+b[1]+"/"+b[0];
									m.setModifiedDate(reqDate);
				
									
									if(!m.getMaterialShortName().equals(rsC.getString("MATERIAL_SHORT_NAME")))
										m.setMaterialShortName_C(rsC.getString("MATERIAL_SHORT_NAME"));
									if(!m.getMaterialLongName().equals(rsC.getString("MATERIAL_LONG_NAME")))
										m.setMaterialLongName_C(rsC.getString("MATERIAL_LONG_NAME"));
									if(!m.getMaterialGroupId().equals(rsC.getString("STXT")))
										m.setMaterialGroupId_C(rsC.getString("STXT"));
									if(!m.getUnitOfMeasId().equals(rsC.getString("LTXT")))
										m.setUnitOfMeasId_C(rsC.getString("LTXT"));	
									if(!m.getManufacturedAt().equals(rsC.getString("MANUFACTURED_AT")))
										m.setManufacturedAt_C(rsC.getString("MANUFACTURED_AT"));
									if(!domorexp.equals(rsC.getString("DOMESTIC_OR_EXPORTS")))
									{
										domorexp=rsC.getString("DOMESTIC_OR_EXPORTS");
										if(domorexp.equalsIgnoreCase("D")){
										m.setDomesticOrExports_C("DOMESTIC");
										}
										if(domorexp.equalsIgnoreCase("E")){
											m.setDomesticOrExports_C("EXPORTS");
											}
										if(domorexp.equalsIgnoreCase("V")){
											m.setDomesticOrExports_C("Validation");
											}
									}
									if(!m.getCountryId().equals(rsC.getString("LANDX")))
										m.setCountryId_C(rsC.getString("LANDX"));
									if(!m.getCustomerName().equals(rsC.getString("CUSTOMER_NAME")))
										m.setCustomerName_C(rsC.getString("CUSTOMER_NAME"));
									if(!salorsam.equals(rsC.getString("SALEABLE_OR_SAMPLE")))
									{
										salorsam=rsC.getString("SALEABLE_OR_SAMPLE");
										if(salorsam.equalsIgnoreCase("1")){
											m.setSaleableOrSample_C("Saleable");
											}
											if(salorsam.equalsIgnoreCase("2")){
												m.setSaleableOrSample_C("Sample");
												}
									}
									if(!m.getSalesPackId().equals(rsC.getString("SALES_PACK_ID")))
										m.setSalesPackId_C(rsC.getString("SALES_PACK_ID"));
									if(!m.getPackTypeId().equals(rsC.getString("P_TYPE_DESC")))
									    m.setPackTypeId_C(rsC.getString("P_TYPE_DESC"));
									if(!m.getSalesUnitOfMeaseurement().equals(rsC.getString("S_UOM_DESC")))
										m.setSalesUnitOfMeaseurement_C(rsC.getString("S_UOM_DESC"));
									if(!m.getDivisionId().equals(rsC.getString("DIV_DESC")))
										m.setDivisionId_C(rsC.getString("DIV_DESC"));
									if(!m.getTherapeuticSegmentID().equals(rsC.getString("THER_SEG_DESC")))
										m.setTherapeuticSegmentID_C(rs.getString("THER_SEG_DESC"));
									if(!m.getBrandID().equals(rsC.getString("BRAND_DESC")))
										m.setBrandID_C(rsC.getString("BRAND_DESC"));
									if(!m.getSrengthId().equals(rsC.getString("STRENGTH_DESC")))
										m.setSrengthId_C(rsC.getString("STRENGTH_DESC"));
									if(!m.getGenericName().equals(rsC.getString("GEN_NAME_DESC")))
										m.setGenericName_C(rsC.getString("GEN_NAME_DESC"));
								   if(!m.getGrossWeight().equals(rsC.getString("GROSS_WEIGHT")))
									   m.setGrossWeight_C(rsC.getString("GROSS_WEIGHT"));
								   if(!m.getNetWeight().equals(rsC.getString("NET_WEIGHT")) )
									   m.setNetWeight_C(rsC.getString("NET_WEIGHT"));
								   
								   if(!m.getDimension().equals(rsC.getString("DIMENSION")))
									   m.setDimension_C(rsC.getString("DIMENSION"));
									
								 //tax type
									String editRecord11="select tax.T_CLASS_NAME from material_code_request_history as m,TAX_CLASS as tax" +
											" where REQUEST_NO='"+reqId+"'and Role='Creator' and  tax.T_CLASS_ID=m.Tax_Classification";
								
								
								ResultSet rsEditRecord11=ad.selectQuery(editRecord11);
								if(rsEditRecord11.next())
								{
									if(!m.getTaxClassification().equals(rsEditRecord11.getString("T_CLASS_NAME")))
									m.setTaxClassification_C(rsEditRecord11.getString("T_CLASS_NAME"));
									
								}
								
								if(!matpri.equals(rsC.getString("Material_Pricing")))
								{
									 matpri=rsC.getString("Material_Pricing");
										if(matpri.equalsIgnoreCase("1")){
											m.setMaterialPricing_C("Normal");
											}
										if(matpri.equalsIgnoreCase("2")){
											m.setMaterialPricing_C("Spare parts");
											}
										if(matpri.equalsIgnoreCase("11")){
											m.setMaterialPricing_C("Scheduled(Controled)");
											}
										if(matpri.equalsIgnoreCase("12")){
											m.setMaterialPricing_C("Un-Scheduled(De-Con)");
											}
										if(matpri.equalsIgnoreCase("13")){
											m.setMaterialPricing_C("PS / Promo (Micro)");
											}
										if(matpri.equalsIgnoreCase("14")){
											m.setMaterialPricing_C("No MRP ED Extra(Mic)");
											}
										if(matpri.equalsIgnoreCase("15")){
											m.setMaterialPricing_C("No MRP ED Incl (Mic)");
											}
										if(matpri.equalsIgnoreCase("16")){
											m.setMaterialPricing_C("Scrap IT Extra (Mic)");
											}
										if(matpri.equalsIgnoreCase("17")){
											m.setMaterialPricing_C("MRP(Con)-ED Exe(Mic)");
											}
										if(matpri.equalsIgnoreCase("18")){
											m.setMaterialPricing_C("MRP(DeC)-ED Exe(Mic)");
											}
										if(matpri.equalsIgnoreCase("19")){
											m.setMaterialPricing_C("No MRP-ED Exe(Mic)");
											}
										if(matpri.equalsIgnoreCase("20")){
											m.setMaterialPricing_C("Food Prod(MRP)-Micro)");
											}
										if(matpri.equalsIgnoreCase("21")){
											m.setMaterialPricing_C("Vet Prod (MRP)-Micr");
											}
										if(matpri.equalsIgnoreCase("22")){
											m.setMaterialPricing_C("Generic Price Grp-Mi");
											}
										if(matpri.equalsIgnoreCase("23")){
											m.setMaterialPricing_C("MRP(Vet)-ED Exe(Mic)");
											}
										if(matpri.equalsIgnoreCase("24")){
											m.setMaterialPricing_C("P-to-P ED Extra(Mic)");
											}
										if(matpri.equalsIgnoreCase("25")){
											m.setMaterialPricing_C("Cosm Prod(MRP)-Micro");
											}
										if(matpri.equalsIgnoreCase("26")){
											m.setMaterialPricing_C("Scrap - ED Exe(Mic))");
											}
										if(matpri.equalsIgnoreCase("27")){
											m.setMaterialPricing_C("Import Items PG");
											}
								}
								
								if(!m.getShelfLife().equals(rsC.getString("SHELF_LIFE")))
									m.setShelfLife_C(rsC.getString("SHELF_LIFE"));
								if(!m.getShelfLifeType().equals(rsC.getString("SHELF_LIFE_TYPE")))
									m.setShelfLifeType_C(rsC.getString("SHELF_LIFE_TYPE"));
								if(!m.getStandardBatchSize().equals(rsC.getString("STANDARD_BATCH_SIZE")))
									   m.setStandardBatchSize_C(rsC.getString("STANDARD_BATCH_SIZE"));
								if(!m.getBatchCode().equals(rsC.getString("BATCH_CODE")))
								 	  m.setBatchCode_C(rsC.getString("BATCH_CODE"));
								
								
								//storage location	
								String getStorageLoc="select s.STORAGE_LOCATION_ID,s.STORAGE_LOCATION_NAME from material_code_request_history m,STORAGE_LOCATION s where "
										+ "REQUEST_NO='"+reqId+"' and Role='Creator' and m.STORAGE_LOCATION_ID=s.STORAGE_LOCATION_ID";
								ResultSet rsStLoc=ad.selectQuery(getStorageLoc);
								if(rsStLoc.next()){

									if(!m.getStorageLocationId().equals(rsStLoc.getString("STORAGE_LOCATION_ID")+"-"+rsStLoc.getString("STORAGE_LOCATION_NAME")))
										m.setStorageLocationId_C(rsStLoc.getString("STORAGE_LOCATION_ID")+"-"+rsStLoc.getString("STORAGE_LOCATION_NAME"));
								}
								//weight wom
								String getweight="select w.W_UOM_CODE,w.W_UOM_DESC from material_code_request_history m,WEIGHT_UOM w where REQUEST_NO='"+reqId+"' and Role='Creator' "
										+ "and m.WEIGHT_UOM=w.W_UOM_CODE";
								 rsC=ad.selectQuery(getweight);
								if(rsC.next()){
									if(!m.getWeightUOM().equals(rsC.getString("W_UOM_DESC")))
										   m.setWeightUOM_C(rsC.getString("W_UOM_DESC"));
								}
								String getValuation="select v.VALUATION_DESC from material_code_request_history m,VALUATION_CLASS v where REQUEST_NO='"+reqId+"' and Role='Creator' "
										+ "and m.VALUATION_CLASS=v.VALUATION_ID";
								rsC=ad.selectQuery(getValuation);
								if(rsC.next()){
									if(!m.getValuationClass().equals(rsC.getString("VALUATION_DESC")))
										m.setValuationClass_C(rsC.getString("VALUATION_DESC"));
								}
								
								
								//purchase grp
								String getFinishedProduct1="select pur.PURCHASE_GROUP_DESC from material_code_request_history as mat,PURCHASE_GROUP as pur" +
										" where REQUEST_NO='"+reqId+"' and Role='Creator' and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID";
									ResultSet rs1=ad.selectQuery(getFinishedProduct1);
									if(rs1.next())
									{
										if(!m.getPuchaseGroupId().equals(rs1.getString("PURCHASE_GROUP_DESC")))
										   m.setPuchaseGroupId_C(rs1.getString("PURCHASE_GROUP_DESC"));
									}
									
								}
							}
							catch (Exception e) {
								e.printStackTrace();
							}
							
							LinkedList fertlist=new LinkedList();
							fertlist.add(m);
							request.setAttribute("FERT", fertlist);

							return mapping.findForward("FERT");		
							
		}
		if(matType.equalsIgnoreCase("BULK"))
		{
			
			String getSemiFinished="select mat.REQUEST_NO,mat.Type,mat.CREATED_DATE,mat.CREATED_BY,loc.LOCATION_CODE,loc.LOCATION_CODE,stLoc.MAT_TYPE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
	 				"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pac.PACK_SIZE_DESC,mat.CUSTOMER_NAME,mat.SHELF_LIFE," +
	 				"mat.SHELF_LIFE_TYPE,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ,mat.TARGET_WEIGHT," +
	 				"wei.W_UOM_DESC,val.VALUATION_DESC,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as " +
	 				"stLoc,MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PACK_SIZE as pac,WEIGHT_UOM as wei,VALUATION_CLASS" +
	 				" as val where REQUEST_NO='"+reqId+"' and Role='user' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID " +
	 				"and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID	" +
	 				"and pac.PACK_SIZE_CODE=mat.PACK_SIZE and wei.W_UOM_CODE=mat.WEIGHT_UOM " +
	 				"and val.VALUATION_ID=mat.VALUATION_CLASS";
					ResultSet rs=ad.selectQuery(getSemiFinished);
					try {
						if(rs.next())
						{
							m.setRequestNumber(reqId);
							String empNo=rs.getString("CREATED_BY");
				 		 	String empname="";
				 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
				 			ResultSet rs12=ad.selectQuery(empNameDetails);
				 			while(rs12.next()){
				 				empname=rs12.getString("EMP_FULLNAME");
				 			}
				 		 	m.setRequestedBy(empname);
							m.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							m.setRequestDate(reqDate);
							m.setLocationId(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
							m.setStorageLocationId(rs.getString("MAT_TYPE")+"-"+rs.getString("STORAGE_LOCATION_NAME"));
							m.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							m.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							m.setMaterialGroupId(rs.getString("STXT"));
							
							m.setUnitOfMeasId(rs.getString("LTXT"));
							m.setPackSize(rs.getString("PACK_SIZE_DESC"));
							m.setCustomerName(rs.getString("CUSTOMER_NAME"));
							m.setShelfLife(rs.getString("SHELF_LIFE"));
							m.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
							m.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
							m.setBatchCode(rs.getString("BATCH_CODE"));
							m.setTargetWeight(rs.getString("TARGET_WEIGHT"));
							
							
							m.setWeightUOM(rs.getString("W_UOM_DESC"));
							m.setRetestDays(rs.getString("RETEST_DAYS"));
							m.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
							m.setValuationClass(rs.getString("VALUATION_DESC"));
							//country
							String editRecord1="select cou.LANDX from material_code_request_history as m ,Country as cou " +
									"where REQUEST_NO='"+reqId+"' and Role='user' and cou.LAND1=m.COUNTRY_ID ";
				 		
				 		
						ResultSet rs1=ad.selectQuery(editRecord1);
						if(rs1.next())
						{
							m.setCountryId(rs1.getString("LANDX"));
							
						}

    }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m.setDmfGradeId_C("");
					m.setMaterialGrade_C("");
					m.setCosGradeNo_C("");
					m.setAdditionalTest_C("");
					m.setCountryId_C("");
					m.setCustomerName_C("");
					m.setToBeUsedInProducts_C("");
					m.setIsVendorSpecificMaterial_C("");
					m.setMfgrName_C("");
					m.setSiteOfManufacture_C("");
					m.setTempCondition_C("");
					m.setStorageCondition_C("");
					m.setShelfLife_C("");
					m.setDutyElement_C("");
					m.setRetestDays_C("");
					m.setPharmacopName_C("");
					m.setPharmacopGrade_C("");
					m.setGenericName_C("");
					m.setSynonym_C("");
					m.setPharmacopSpecification_C("");
					m.setIsDMFMaterial_C("");
					m.setShelfLifeType_C("");
					m.setRetestType_C("");
					m.setReqMaterialGroup_C("");
			        m.setPackageMaterialGroup_C("");
					m.setTypeOfMaterial_C("");	
					m.setArtworkNo_C("");	
					m.setIsArtworkRevision_C("");
					m.setPackSize_C("");
					m.setTargetWeight_C("");
					m.setStandardBatchSize_C("");
					m.setBatchCode_C("");
					m.setWeightUOM_C("");
					
					
					String getSemiFinishedcrea="select mat.REQUEST_NO,mat.Type,mat.CREATED_DATE,mat.CREATED_BY,loc.LOCATION_CODE,loc.LOCATION_CODE,stLoc.MAT_TYPE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
			 				"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pac.PACK_SIZE_DESC,mat.CUSTOMER_NAME,mat.SHELF_LIFE," +
			 				"mat.SHELF_LIFE_TYPE,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ,mat.TARGET_WEIGHT," +
			 				"wei.W_UOM_DESC,val.VALUATION_DESC,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as " +
			 				"stLoc,MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PACK_SIZE as pac,WEIGHT_UOM as wei,VALUATION_CLASS" +
			 				" as val where REQUEST_NO='"+reqId+"' and Role='Creator' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID " +
			 				"and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID	" +
			 				"and pac.PACK_SIZE_CODE=mat.PACK_SIZE and wei.W_UOM_CODE=mat.WEIGHT_UOM " +
			 				"and val.VALUATION_ID=mat.VALUATION_CLASS";
							ResultSet rsC=ad.selectQuery(getSemiFinishedcrea);
							try {
								if(rsC.next())
								{
									String reqDate=rsC.getString("CREATED_DATE");
									String empNo=rs.getString("CREATED_BY");
						 		 	String empname="";
						 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
						 			ResultSet rs12=ad.selectQuery(empNameDetails);
						 			while(rs12.next()){
						 				empname=rs12.getString("EMP_FULLNAME");
						 			}
						 		 	m.setModifiedBy(empname);
									String b[]=reqDate.split("-");
									reqDate=b[2]+"/"+b[1]+"/"+b[0];
									m.setModifiedDate(reqDate);
				
									if(!m.getStorageLocationId().equals(rsC.getString("MAT_TYPE")+"-"+rsC.getString("STORAGE_LOCATION_NAME")))
										m.setStorageLocationId_C(rsC.getString("MAT_TYPE")+"-"+rsC.getString("STORAGE_LOCATION_NAME"));
									if(!m.getMaterialShortName().equals(rsC.getString("MATERIAL_SHORT_NAME")))
										m.setMaterialShortName_C(rsC.getString("MATERIAL_SHORT_NAME"));
									if(!m.getMaterialLongName().equals(rsC.getString("MATERIAL_LONG_NAME")))
										m.setMaterialLongName_C(rsC.getString("MATERIAL_LONG_NAME"));
									if(!m.getMaterialGroupId().equals(rsC.getString("STXT")))
										m.setMaterialGroupId_C(rsC.getString("STXT"));
									if(!m.getUnitOfMeasId().equals(rsC.getString("LTXT")))
										m.setUnitOfMeasId_C(rsC.getString("LTXT"));	
									if(!m.getPackSize().equals(rsC.getString("PACK_SIZE_DESC")))
										m.setPackSize_C(rsC.getString("PACK_SIZE_DESC"));
									//country
									String editRecord1="select cou.LANDX from material_code_request_history as m ,Country as cou " +
									"where REQUEST_NO='"+reqId+"' and Role='Creator' and cou.LAND1=m.COUNTRY_ID ";						 		
						 		
									ResultSet rs1=ad.selectQuery(editRecord1);
									if(rs1.next())
									{
										if(!m.getCountryId().equals(rs1.getString("LANDX")))
										m.setCountryId_C(rs1.getString("LANDX"));
										
									}
									
							    	
									if(!m.getCustomerName().equals(rsC.getString("CUSTOMER_NAME")))
										m.setCustomerName_C(rsC.getString("CUSTOMER_NAME"));									
									if(!m.getShelfLife().equals(rsC.getString("SHELF_LIFE")))
										m.setShelfLife_C(rsC.getString("SHELF_LIFE"));
									if(!m.getShelfLifeType().equals(rsC.getString("SHELF_LIFE_TYPE")))
										m.setShelfLifeType_C(rsC.getString("SHELF_LIFE_TYPE"));
									if(!m.getRetestDays().equals(rsC.getString("RETEST_DAYS")))
										m.setRetestDays_C(rsC.getString("RETEST_DAYS"));
									if(!m.getRetestType().equals(rsC.getString("RETEST_DAYS_TYPE")))
										m.setRetestType_C(rsC.getString("RETEST_DAYS_TYPE"));
								   if(!m.getStandardBatchSize().equals(rsC.getString("STANDARD_BATCH_SIZE")))
									   m.setStandardBatchSize_C(rsC.getString("STANDARD_BATCH_SIZE"));
								   if(!m.getBatchCode().equals(rsC.getString("BATCH_CODE")))
								 	  m.setBatchCode_C(rsC.getString("BATCH_CODE"));
								   if(!m.getTargetWeight().equals(rsC.getString("TARGET_WEIGHT")))
										m.setTargetWeight_C(rsC.getString("TARGET_WEIGHT"));
								   if(!m.getWeightUOM().equals(rsC.getString("W_UOM_DESC")))
									  m.setWeightUOM_C(rsC.getString("W_UOM_DESC"));
								   if(!m.getValuationClass().equals(rsC.getString("VALUATION_DESC")))
											m.setValuationClass_C(rsC.getString("VALUATION_DESC"));
										
									
									
									
									
								
								
								}
								
								
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
							
		LinkedList halblist=new LinkedList();
		halblist.add(m);
		request.setAttribute("HALB", halblist);

		return mapping.findForward("HALB");					
							
		}

		if(matType.equalsIgnoreCase("PM"))
		{
			
			String dutyElement="";
			String typeOfMaterial="";
			String isARTWORKRevisionStatus="";
			String isDMFMaterial="";
			String isVendorStatus="";
			
			String getMaterial="select mat.REQUEST_NO,mat.Type,mat.MATERIAL_GROUP_ID,mat.CREATED_DATE,mat.CREATED_BY,loc.LOCATION_CODE,loc.LOCATION_CODE,stLoc.MAT_TYPE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
			"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.DUTY_ELEMENT,pacgrp.PACKING_MATERIAL_GROUP_NAME," +
			"mat.Type_Of_Material,mat.ARTWORK_NO,mat.IS_ARTWORK_REVISION,mat.EXISTING_SAP_ITEM_CODE,mat.IS_DMF_MATERIAL,dmf.DMF_GRADE_DESC," +
			"mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST,mat.IS_VENDOR_SPECIFIC_MATERIAL,mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX," +
			"mat.CUSTOMER_NAME,mat.TO_BE_USED_IN_PRODUCTS,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC," +
			"mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc," +
			"MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,PACKAGE_MATERIAL_GROUP as pacgrp,DMF_GRADE as dmf," +
			"Country as cou,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' and Role='user' " +
			"and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID" +
			" and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID  and pacgrp.PACKING_MATERIAL_GROUP_ID=mat.PACKING_MATERIAL_GROUP " +
			" and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID  " +
			" and val.VALUATION_ID=mat.VALUATION_CLASS";
			
			ResultSet rs=ad.selectQuery(getMaterial);
			try {
				if(rs.next())
				{
					
					m.setRequestNumber(reqId);	
					String empNo=rs.getString("CREATED_BY");
		 		 	String empname="";
		 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
		 			ResultSet rs12=ad.selectQuery(empNameDetails);
		 			while(rs12.next()){
		 				empname=rs12.getString("EMP_FULLNAME");
		 			}
		 		 	m.setRequestedBy(empname);
					m.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					m.setRequestDate(reqDate);
					m.setLocationId(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
					m.setStorageLocationId(rs.getString("MAT_TYPE")+"-"+rs.getString("STORAGE_LOCATION_NAME"));
				
					m.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					m.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					m.setMaterialGroupId(rs.getString("STXT"));
					 isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
					if(isDMFMaterial.equalsIgnoreCase("1"))
					{
						m.setIsDMFMaterial("YES");
						request.setAttribute("dmfMandatory", "dmfMandatory");
						m.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
						m.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					}
					if(isDMFMaterial.equalsIgnoreCase("0")){
						m.setIsDMFMaterial("NO");
						request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
						m.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
						m.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					}
					m.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
					
					m.setCountryId(rs.getString("LANDX"));
					m.setCustomerName(rs.getString("CUSTOMER_NAME"));
					m.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
					 isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
					if(isVendorStatus.equalsIgnoreCase("1"))
					{
						m.setIsVendorSpecificMaterial("YES");
						request.setAttribute("vedorMandatory", "vedorMandatory");
						m.setMfgrName(rs.getString("MFGR_NAME"));
						m.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
					}
					if(isVendorStatus.equalsIgnoreCase("0"))
					{
						m.setIsVendorSpecificMaterial("NO");
						request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
					}
						m.setMfgrName(rs.getString("MFGR_NAME"));
					m.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
					
					m.setRetestDays(rs.getString("RETEST_DAYS"));
					m.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
					 dutyElement=rs.getString("DUTY_ELEMENT");
					if(dutyElement.equalsIgnoreCase("0"))
					{
						m.setDutyElement("0-Indigenous Material with or without Cenvat");
					}
					if(dutyElement.equalsIgnoreCase("1"))
						m.setDutyElement("1-Duty Exempted Packing Materials for Finished product");
					m.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP_NAME"));
					m.setTypeOfMaterial(rs.getString("Type_Of_Material"));
					 typeOfMaterial=rs.getString("Type_Of_Material");
					
					if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
					{
						request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
						m.setArtworkNo(rs.getString("ARTWORK_NO"));
						 isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
						if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
						{
						m.setIsArtworkRevision("YES");
						}
						if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
					    m.setIsArtworkRevision("NO");
						
					}
				
					
					m.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
					m.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
					m.setUnitOfMeasId(rs.getString("LTXT"));
					m.setValuationClass(rs.getString("VALUATION_DESC"));
					m.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
					
					//temp cond
					
					String getMaterial1="select tem.TEMP_CON_DESC from material_code_request_history as mat,TEMP_CONDITION as tem " +
							" where REQUEST_NO='"+reqId+"' and Role='user' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
					
					ResultSet rs1=ad.selectQuery(getMaterial1);
					while(rs1.next())
					{
						m.setTempCondition(rs1.getString("TEMP_CON_DESC"));
					}
					
					//STORAGE_CONDITION
					
					String getMaterial2="select stcon.LTXT from material_code_request_history as mat,STORAGE_CONDITION as stcon" +
							" where REQUEST_NO='"+reqId+"' and Role='user' and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
					
					ResultSet rs2=ad.selectQuery(getMaterial2);
					while(rs2.next())
					{
						m.setStorageCondition(rs2.getString("LTXT"));
					}
				
						}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			m.setDmfGradeId_C("");
			m.setMaterialGrade_C("");
			m.setCosGradeNo_C("");
			m.setAdditionalTest_C("");
			m.setCountryId_C("");
			m.setCustomerName_C("");
			m.setToBeUsedInProducts_C("");
			m.setIsVendorSpecificMaterial_C("");
			m.setMfgrName_C("");
			m.setSiteOfManufacture_C("");
			m.setTempCondition_C("");
			m.setStorageCondition_C("");
			m.setShelfLife_C("");
			m.setDutyElement_C("");
			m.setRetestDays_C("");
			m.setPharmacopName_C("");
			m.setPharmacopGrade_C("");
			m.setGenericName_C("");
			m.setSynonym_C("");
			m.setPharmacopSpecification_C("");
			m.setIsDMFMaterial_C("");
			m.setShelfLifeType_C("");
			m.setRetestType_C("");
			m.setReqMaterialGroup_C("");
	        m.setPackageMaterialGroup_C("");
			m.setTypeOfMaterial_C("");	
			m.setArtworkNo_C("");	
			m.setIsArtworkRevision_C("");
			
			String getMaterialcrea="select mat.REQUEST_NO,mat.Type,mat.MATERIAL_GROUP_ID,mat.CREATED_DATE,mat.CREATED_BY,loc.LOCATION_CODE,loc.LOCATION_CODE,stLoc.MAT_TYPE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
					"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.DUTY_ELEMENT,pacgrp.PACKING_MATERIAL_GROUP_NAME," +
					"mat.Type_Of_Material,mat.ARTWORK_NO,mat.IS_ARTWORK_REVISION,mat.EXISTING_SAP_ITEM_CODE,mat.IS_DMF_MATERIAL,dmf.DMF_GRADE_DESC," +
					"mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST,mat.IS_VENDOR_SPECIFIC_MATERIAL,mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX," +
					"mat.CUSTOMER_NAME,mat.TO_BE_USED_IN_PRODUCTS,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC," +
					"mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc," +
					"MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,PACKAGE_MATERIAL_GROUP as pacgrp,DMF_GRADE as dmf," +
					"Country as cou,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' and Role='Creator' " +
					"and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID" +
					" and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID  and pacgrp.PACKING_MATERIAL_GROUP_ID=mat.PACKING_MATERIAL_GROUP " +
					" and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID  " +
					" and val.VALUATION_ID=mat.VALUATION_CLASS";
					
					ResultSet rsC=ad.selectQuery(getMaterialcrea);
					try {
						if(rsC.next())
						{
							String reqDate=rsC.getString("CREATED_DATE");
							String empNo=rs.getString("CREATED_BY");
				 		 	String empname="";
				 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
				 			ResultSet rs12=ad.selectQuery(empNameDetails);
				 			while(rs12.next()){
				 				empname=rs12.getString("EMP_FULLNAME");
				 			}
				 		 	m.setModifiedBy(empname);
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							m.setModifiedDate(reqDate);
		
							if(!m.getStorageLocationId().equals(rsC.getString("MAT_TYPE")+"-"+rsC.getString("STORAGE_LOCATION_NAME")))
								m.setStorageLocationId_C(rsC.getString("MAT_TYPE")+"-"+rsC.getString("STORAGE_LOCATION_NAME"));
							if(!m.getMaterialShortName().equals(rsC.getString("MATERIAL_SHORT_NAME")))
								m.setMaterialShortName_C(rsC.getString("MATERIAL_SHORT_NAME"));
							if(!m.getMaterialLongName().equals(rsC.getString("MATERIAL_LONG_NAME")))
								m.setMaterialLongName_C(rsC.getString("MATERIAL_LONG_NAME"));
							if(!m.getMaterialGroupId().equals(rsC.getString("STXT")))
								m.setMaterialGroupId_C(rsC.getString("STXT"));
							if(!m.getUnitOfMeasId().equals(rsC.getString("LTXT")))
								m.setUnitOfMeasId_C(rsC.getString("LTXT"));			
							if(!m.getPuchaseGroupId().equals(rsC.getString("PURCHASE_GROUP_DESC")))
								m.setPuchaseGroupId_C(rsC.getString("PURCHASE_GROUP_DESC"));
							 if(!dutyElement.equals(rsC.getString("DUTY_ELEMENT")))
								{							
								 dutyElement=rsC.getString("DUTY_ELEMENT");
								if(dutyElement.equalsIgnoreCase("0"))
								{
									m.setDutyElement_C("0-Duty Exempted Raw Materials for Finished product");
								}
								if(dutyElement.equalsIgnoreCase("2")){
									m.setDutyElement_C("2-Indigenous Material with or without Cenvat");
								}
								}
							 if(!m.getPackageMaterialGroup().equals(rsC.getString("PACKING_MATERIAL_GROUP_NAME")))
								 m.setPackageMaterialGroup_C(rsC.getString("PACKING_MATERIAL_GROUP_NAME"));
							 if(!m.getTypeOfMaterial().equals(rsC.getString("Type_Of_Material")))
								 m.setTypeOfMaterial_C(rsC.getString("Type_Of_Material"));
			
							 if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
								{
								 if(!m.getArtworkNo().equals(rsC.getString("ARTWORK_NO")))
									 m.setArtworkNo_C(rsC.getString("ARTWORK_NO"));
								 if(!isARTWORKRevisionStatus.equals(rsC.getString("IS_ARTWORK_REVISION")))
								 {
									 isARTWORKRevisionStatus=rsC.getString("IS_ARTWORK_REVISION");
								 if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
									{
									m.setIsArtworkRevision_C("YES");
									}
									if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
								    m.setIsArtworkRevision_C("NO");
								}
								}
							 if(!m.getExistingSAPItemCode().equals(rsC.getString("EXISTING_SAP_ITEM_CODE")))
								 m.setExistingSAPItemCode_C(rsC.getString("EXISTING_SAP_ITEM_CODE"));							
					     	if(!m.getApproximateValue().equals(rsC.getString("APPROXIMATE_VALUE")))
									m.setApproximateValue_C(rsC.getString("APPROXIMATE_VALUE"));
					     	
					    	if(!isDMFMaterial.equals(rsC.getString("IS_DMF_MATERIAL")))
							{
					    		isDMFMaterial=rsC.getString("IS_DMF_MATERIAL");
								
							if(isDMFMaterial.equalsIgnoreCase("1"))
							{
								m.setIsDMFMaterial_C("YES");						
								m.setDmfGradeId_C(rsC.getString("DMF_GRADE_DESC"));							
								m.setCosGradeNo_C(rsC.getString("COS_GRADE_AND_NO"));
								
							}
							if(isDMFMaterial.equalsIgnoreCase("0"))
							{
								m.setIsDMFMaterial_C("NO");
								m.setDmfGradeId_C(rsC.getString("DMF_GRADE_DESC"));							
								m.setCosGradeNo_C(rsC.getString("COS_GRADE_AND_NO"));
								
							}							
						}
					    	if(!m.getAdditionalTest().equals(rsC.getString("ADDITIONAL_TEST")))
								m.setAdditionalTest_C(rsC.getString("ADDITIONAL_TEST"));
					    	
					    	if(!isVendorStatus.equals(rsC.getString("IS_VENDOR_SPECIFIC_MATERIAL")))
							{	
									isVendorStatus=rsC.getString("IS_VENDOR_SPECIFIC_MATERIAL");
							if(isVendorStatus.equalsIgnoreCase("1"))
							{
								m.setIsVendorSpecificMaterial_C("YES");
								m.setMfgrName_C(rsC.getString("MFGR_NAME"));
								m.setSiteOfManufacture_C(rsC.getString("SITE_OF_MANUFACTURE"));
							}
							if(isVendorStatus.equalsIgnoreCase("0"))
							{
								m.setIsVendorSpecificMaterial_C("NO");
							}
						
							}
					    	if(!m.getCountryId().equals(rsC.getString("LANDX")))
								m.setCountryId_C(rsC.getString("LANDX"));
							if(!m.getCustomerName().equals(rsC.getString("CUSTOMER_NAME")))
								m.setCustomerName_C(rsC.getString("CUSTOMER_NAME"));
							if(!m.getToBeUsedInProducts().equals(rsC.getString("TO_BE_USED_IN_PRODUCTS")))
								m.setToBeUsedInProducts_C(rsC.getString("TO_BE_USED_IN_PRODUCTS"));
							
							if(!m.getRetestDays().equals(rsC.getString("RETEST_DAYS")))
								m.setRetestDays_C(rsC.getString("RETEST_DAYS"));
							if(!m.getRetestType().equals(rsC.getString("RETEST_DAYS_TYPE")))
								m.setRetestType_C(rsC.getString("RETEST_DAYS_TYPE"));
							if(!m.getApproximateValue().equals(rsC.getString("APPROXIMATE_VALUE")))
								m.setApproximateValue_C(rsC.getString("APPROXIMATE_VALUE"));
							if(!m.getValuationClass().equals(rsC.getString("VALUATION_DESC")))
								m.setValuationClass_C(rsC.getString("VALUATION_DESC"));
							
							String getMaterial1="select tem.TEMP_CON_DESC from material_code_request_history as mat,TEMP_CONDITION as tem " +
							   " where REQUEST_NO='"+reqId+"' and Role='Creator' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
								
								ResultSet rs1=ad.selectQuery(getMaterial1);
								while(rs1.next())
								{
									if(!m.getTempCondition().equals(rs1.getString("TEMP_CON_DESC")))
									m.setTempCondition_C(rs1.getString("TEMP_CON_DESC"));
								}
								
								//STORAGE_CONDITION
								
								String getMaterial2="select stcon.LTXT from material_code_request_history as mat,STORAGE_CONDITION as stcon" +
										" where REQUEST_NO='"+reqId+"'  and Role='Creator' and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
								
								ResultSet rs2=ad.selectQuery(getMaterial2);
								while(rs2.next())
								{
									if(!m.getStorageCondition().equals(rs2.getString("LTXT")))
									m.setStorageCondition_C(rs2.getString("LTXT"));
								}
						
						}
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					LinkedList verplist=new LinkedList();
					verplist.add(m);
					request.setAttribute("VERP", verplist);
			
					return mapping.findForward("VERP");
		
			
	}
		if(matType.equalsIgnoreCase("RM"))
		{
			String dutyElement="";
			String isDMfMaterial="";
			String isVendorStatus="";
			
			
			String getMaterial="select  mat.REQUEST_NO,mat.REQUEST_DATE,mat.CREATED_DATE,mat.CREATED_BY,loc.LOCNAME,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,stLoc.STORAGE_LOCATION_NAME,stLoc.MAT_TYPE," +
				" mat.MATERIAL_SHORT_NAME,mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, mat.PHARMACOP_NAME," +
					"mat.PHARMACOP_GRADE,mat.GENERIC_NAME,mat.SYNONYM,mat.PHARMACOP_SPECIFICATION,mat.DUTY_ELEMENT, mat.IS_DMF_MATERIAL," +
					"dmf.DMF_GRADE_DESC,mat.MATERIAL_GRADE,mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST, mat.IS_VENDOR_SPECIFIC_MATERIAL," +
					"mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX,mat.CUSTOMER_NAME, mat.TO_BE_USED_IN_PRODUCTS,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE," +
					"mat.RETEST_DAYS ,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC,mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS, " +
					"SAP_CREATION_DATE,SAP_CREATED_BY,rejected_flag from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP " +
					"as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,DMF_GRADE as dmf,Country as cou, VALUATION_CLASS as val " +
					" where REQUEST_NO='"+reqId+"' and Role='user' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and " +
					"matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID " +
					"and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID and val.VALUATION_ID=mat.VALUATION_CLASS";	
					ResultSet rs=ad.selectQuery(getMaterial);
					try {
						if(rs.next())
						{
						
						m.setRequestNumber(reqId);
						String empNo=rs.getString("CREATED_BY");
			 		 	String empname="";
			 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
			 			ResultSet rs12=ad.selectQuery(empNameDetails);
			 			while(rs12.next()){
			 				empname=rs12.getString("EMP_FULLNAME");
			 			}
			 		 	m.setRequestedBy(empname);
						m.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						m.setRequestDate(reqDate);
						m.setLocationId(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
						m.setStorageLocationId(rs.getString("MAT_TYPE")+"-"+rs.getString("STORAGE_LOCATION_NAME"));
						
						m.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
						m.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						m.setMaterialGroupId(rs.getString("STXT"));
						m.setPharmacopName(rs.getString("PHARMACOP_NAME"));
						String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
						m.setPharmacopGrade(pharmacopGrade);
						
						m.setGenericName(rs.getString("GENERIC_NAME"));
						m.setSynonym(rs.getString("SYNONYM"));
						m.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
						 isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
						if(isDMfMaterial.equalsIgnoreCase("1"))
						{
							m.setIsDMFMaterial("YES");						
							m.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
							m.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
							m.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
							
						}
						m.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
						if(isDMfMaterial.equalsIgnoreCase("0"))
						{
							m.setIsDMFMaterial("NO");
							m.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
							m.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
							m.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
							
							
						}
						
						m.setCountryId(rs.getString("LANDX"));
						m.setCustomerName(rs.getString("CUSTOMER_NAME"));
						m.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
						
						 isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
						if(isVendorStatus.equalsIgnoreCase("1"))
						{
							m.setIsVendorSpecificMaterial("YES");
							m.setMfgrName(rs.getString("MFGR_NAME"));
							m.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
						}
						if(isVendorStatus.equalsIgnoreCase("0"))
						{
							m.setIsVendorSpecificMaterial("NO");
						}
						
						
						m.setShelfLife(rs.getString("SHELF_LIFE"));
						 dutyElement=rs.getString("DUTY_ELEMENT");
						if(dutyElement.equalsIgnoreCase("0"))
						{
							m.setDutyElement("0-Duty Exempted Raw Materials for Finished product");
						}
						if(dutyElement.equalsIgnoreCase("2")){
							m.setDutyElement("2-Indigenous Material with or without Cenvat");
						}
						
						m.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
						m.setRetestDays(rs.getString("RETEST_DAYS"));
						m.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
						m.setValuationClass(rs.getString("VALUATION_DESC"));
						m.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						m.setUnitOfMeasId(rs.getString("LTXT"));
						m.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						
						String getMaterial1="select tem.TEMP_CON_DESC from material_code_request_history as mat,TEMP_CONDITION as tem " +
								   " where REQUEST_NO='"+reqId+"' and Role='user' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
									
									ResultSet rs1=ad.selectQuery(getMaterial1);
									while(rs1.next())
									{
										m.setTempCondition(rs1.getString("TEMP_CON_DESC"));
									}
									
									//STORAGE_CONDITION
									
									String getMaterial2="select stcon.LTXT from material_code_request_history as mat,STORAGE_CONDITION as stcon" +
											" where REQUEST_NO='"+reqId+"'  and Role='user' and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
									
									ResultSet rs2=ad.selectQuery(getMaterial2);
									while(rs2.next())
									{
										m.setStorageCondition(rs2.getString("LTXT"));
									}


							

						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					m.setDmfGradeId_C("");
					m.setMaterialGrade_C("");
					m.setCosGradeNo_C("");
					m.setAdditionalTest_C("");
					m.setCountryId_C("");
					m.setCustomerName_C("");
					m.setToBeUsedInProducts_C("");
					m.setIsVendorSpecificMaterial_C("");
					m.setMfgrName_C("");
					m.setSiteOfManufacture_C("");
					m.setTempCondition_C("");
					m.setStorageCondition_C("");
					m.setShelfLife_C("");
					m.setDutyElement_C("");
					m.setRetestDays_C("");
					m.setPharmacopName_C("");
					m.setPharmacopGrade_C("");
					m.setGenericName_C("");
					m.setSynonym_C("");
					m.setPharmacopSpecification_C("");
					m.setIsDMFMaterial_C("");
					m.setShelfLifeType_C("");
					m.setRetestType_C("");
					m.setReqMaterialGroup_C("");

					
					String getMaterialcrea="select  mat.REQUEST_NO,mat.REQUEST_DATE,mat.CREATED_DATE,mat.CREATED_BY,loc.LOCNAME,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,stLoc.STORAGE_LOCATION_NAME,stLoc.MAT_TYPE," +
							" mat.MATERIAL_SHORT_NAME,mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, mat.PHARMACOP_NAME," +
								"mat.PHARMACOP_GRADE,mat.GENERIC_NAME,mat.SYNONYM,mat.PHARMACOP_SPECIFICATION,mat.DUTY_ELEMENT, mat.IS_DMF_MATERIAL," +
								"dmf.DMF_GRADE_DESC,mat.MATERIAL_GRADE,mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST, mat.IS_VENDOR_SPECIFIC_MATERIAL," +
								"mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX,mat.CUSTOMER_NAME, mat.TO_BE_USED_IN_PRODUCTS,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE," +
								"mat.RETEST_DAYS ,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC,mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS, " +
								"SAP_CREATION_DATE,SAP_CREATED_BY,rejected_flag from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP " +
								"as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,DMF_GRADE as dmf,Country as cou, VALUATION_CLASS as val " +
								" where REQUEST_NO='"+reqId+"'  and Role='Creator'  and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and " +
								"matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID " +
								"and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID and val.VALUATION_ID=mat.VALUATION_CLASS";	
					ResultSet rsC=ad.selectQuery(getMaterialcrea);
					try {
						if(rsC.next())
						{
							String reqDate=rsC.getString("CREATED_DATE");
							
							String empNo=rs.getString("CREATED_BY");
				 		 	String empname="";
				 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
				 			ResultSet rs12=ad.selectQuery(empNameDetails);
				 			while(rs12.next()){
				 				empname=rs12.getString("EMP_FULLNAME");
				 			}
				 		 	m.setModifiedBy(empname);
				 		 	
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							m.setModifiedDate(reqDate);
		
							if(!m.getStorageLocationId().equals(rsC.getString("MAT_TYPE")+"-"+rsC.getString("STORAGE_LOCATION_NAME")))
								m.setStorageLocationId_C(rsC.getString("MAT_TYPE")+"-"+rsC.getString("STORAGE_LOCATION_NAME"));
							if(!m.getMaterialShortName().equals(rsC.getString("MATERIAL_SHORT_NAME")))
								m.setMaterialShortName_C(rsC.getString("MATERIAL_SHORT_NAME"));
							if(!m.getMaterialLongName().equals(rsC.getString("MATERIAL_LONG_NAME")))
								m.setMaterialLongName_C(rsC.getString("MATERIAL_LONG_NAME"));
							if(!m.getMaterialGroupId().equals(rsC.getString("STXT")))
								m.setMaterialGroupId_C(rsC.getString("STXT"));
							if(!m.getUnitOfMeasId().equals(rsC.getString("LTXT")))
								m.setUnitOfMeasId_C(rsC.getString("LTXT"));							
							if(!m.getPuchaseGroupId().equals(rsC.getString("PURCHASE_GROUP_DESC")))
								m.setPuchaseGroupId_C(rsC.getString("PURCHASE_GROUP_DESC"));
							if(!m.getPharmacopName().equals(rsC.getString("PHARMACOP_NAME")))
								m.setPharmacopName_C(rsC.getString("PHARMACOP_NAME"));
							if(!m.getPharmacopGrade().equals(rsC.getString("PHARMACOP_GRADE")))
								m.setPharmacopGrade_C(rsC.getString("PHARMACOP_GRADE"));
							if(!m.getGenericName().equals(rsC.getString("GENERIC_NAME")));
							m.setGenericName_C(rsC.getString("GENERIC_NAME"));
							if(!m.getSynonym().equals(rsC.getString("SYNONYM")))
								m.setSynonym_C(rsC.getString("SYNONYM"));
							if(!m.getPharmacopSpecification().equals(rsC.getString("PHARMACOP_SPECIFICATION")))
								m.setPharmacopSpecification_C(rsC.getString("PHARMACOP_SPECIFICATION"));				
					    	 if(!dutyElement.equals(rsC.getString("DUTY_ELEMENT")))
							{							
							 dutyElement=rsC.getString("DUTY_ELEMENT");
							if(dutyElement.equalsIgnoreCase("0"))
							{
								m.setDutyElement_C("0-Duty Exempted Raw Materials for Finished product");
							}
							if(dutyElement.equalsIgnoreCase("2")){
								m.setDutyElement_C("2-Indigenous Material with or without Cenvat");
							}
							}
							
							if(!isDMfMaterial.equals(rsC.getString("IS_DMF_MATERIAL")))
							{
								isDMfMaterial=rsC.getString("IS_DMF_MATERIAL");
								
							if(isDMfMaterial.equalsIgnoreCase("1"))
							{
								m.setIsDMFMaterial_C("YES");						
								m.setDmfGradeId_C(rsC.getString("DMF_GRADE_DESC"));
								m.setMaterialGrade_C(rsC.getString("MATERIAL_GRADE"));
								m.setCosGradeNo_C(rsC.getString("COS_GRADE_AND_NO"));
								
							}
							if(isDMfMaterial.equalsIgnoreCase("0"))
							{
								m.setIsDMFMaterial_C("NO");
								m.setDmfGradeId_C(rsC.getString("DMF_GRADE_DESC"));
								m.setMaterialGrade_C(rsC.getString("MATERIAL_GRADE"));
								m.setCosGradeNo_C(rsC.getString("COS_GRADE_AND_NO"));
								
							}							
						}
							if(!m.getAdditionalTest().equals(rsC.getString("ADDITIONAL_TEST")))
							m.setAdditionalTest_C(rsC.getString("ADDITIONAL_TEST"));
							
							if(!isVendorStatus.equals(rsC.getString("IS_VENDOR_SPECIFIC_MATERIAL")))
							{	
									isVendorStatus=rsC.getString("IS_VENDOR_SPECIFIC_MATERIAL");
							if(isVendorStatus.equalsIgnoreCase("1"))
							{
								m.setIsVendorSpecificMaterial_C("YES");
								m.setMfgrName_C(rsC.getString("MFGR_NAME"));
								m.setSiteOfManufacture_C(rsC.getString("SITE_OF_MANUFACTURE"));
							}
							if(isVendorStatus.equalsIgnoreCase("0"))
							{
								m.setIsVendorSpecificMaterial_C("NO");
							}
						
							}
							
							if(!m.getCountryId().equals(rsC.getString("LANDX")))
								m.setCountryId_C(rsC.getString("LANDX"));
							if(!m.getCustomerName().equals(rsC.getString("CUSTOMER_NAME")))
								m.setCustomerName_C(rsC.getString("CUSTOMER_NAME"));
							if(!m.getToBeUsedInProducts().equals(rsC.getString("TO_BE_USED_IN_PRODUCTS")))
								m.setToBeUsedInProducts_C(rsC.getString("TO_BE_USED_IN_PRODUCTS"));
							if(!m.getShelfLife().equals(rsC.getString("SHELF_LIFE")))
								m.setShelfLife_C(rsC.getString("SHELF_LIFE"));
							if(!m.getShelfLifeType().equals(rsC.getString("SHELF_LIFE_TYPE")))
								m.setShelfLifeType_C(rsC.getString("SHELF_LIFE_TYPE"));
							if(!m.getRetestDays().equals(rsC.getString("RETEST_DAYS")))
								m.setRetestDays_C(rsC.getString("RETEST_DAYS"));
							if(!m.getRetestType().equals(rsC.getString("RETEST_DAYS_TYPE")))
								m.setRetestType_C(rsC.getString("RETEST_DAYS_TYPE"));
							if(!m.getApproximateValue().equals(rsC.getString("APPROXIMATE_VALUE")))
								m.setApproximateValue_C(rsC.getString("APPROXIMATE_VALUE"));
							if(!m.getValuationClass().equals(rsC.getString("VALUATION_DESC")))
								m.setValuationClass_C(rsC.getString("VALUATION_DESC"));
							
							String getMaterial1="select tem.TEMP_CON_DESC from material_code_request_history as mat,TEMP_CONDITION as tem " +
									   " where REQUEST_NO='"+reqId+"' and Role='Creator' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
										
										ResultSet rs1=ad.selectQuery(getMaterial1);
										while(rs1.next())
										{
											if(!m.getTempCondition().equals(rs1.getString("TEMP_CON_DESC")))
											m.setTempCondition_C(rs1.getString("TEMP_CON_DESC"));
										}
										
										//STORAGE_CONDITION
										
										String getMaterial2="select stcon.LTXT from material_code_request_history as mat,STORAGE_CONDITION as stcon" +
												" where REQUEST_NO='"+reqId+"'  and Role='Creator' and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
										
										ResultSet rs2=ad.selectQuery(getMaterial2);
										while(rs2.next())
										{
											if(!m.getStorageCondition().equals(rs2.getString("LTXT")))
											m.setStorageCondition_C(rs2.getString("LTXT"));
										}
			
						 		
						}
					}
					

						catch (Exception e) {
							e.printStackTrace();
						}
					LinkedList rohlist=new LinkedList();
					rohlist.add(m);
					request.setAttribute("ROH", rohlist);
			
					return mapping.findForward("ROH");
				}
		
		
		if(matType.equalsIgnoreCase("PPC")){
			String isAsset="";
			String purposeId="";
			String isSAS="";
			String storageLoction="";
			String getZPPCDetails="select mat.REQUEST_NO,mat.CREATED_BY,mat.REQUEST_DATE,loc.LOCNAME,loc.LOCATION_CODE,stLoc.MAT_TYPE,stLoc.STORAGE_LOCATION_NAME," +
   			 		"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, div.DIV_DESC," +
   			 		"mat.is_asset,mat.PURPOSE_ID,mat.IS_SAS_FORM_AVAILABLE,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION," +
   			 		"mat.DETAILED_SPECIFICATION from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as " +
   			 		"matGroup , UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur,DIVISION as div,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' and role='user' and " +
   			 		"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
   			 		"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID  " +
   			 		"and val.VALUATION_ID=mat.VALUATION_CLASS and Type='ZPPC'";
			ResultSet rs=ad.selectQuery(getZPPCDetails);
			try{
				if(rs.next())
				{	
		 		 	m.setRequestNumber(reqId);
		 		 	String empNo=rs.getString("CREATED_BY");
		 		 	String empname="";
		 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
		 			ResultSet rs1=ad.selectQuery(empNameDetails);
		 			while(rs1.next()){
		 				empname=rs1.getString("EMP_FULLNAME");
		 			}
		 		 	m.setRequestedBy(empname);
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					m.setRequestDate(reqDate);
					storageLoction=rs.getString("LOCATION_CODE");
					m.setLocationId(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
					m.setStorageLocationId(rs.getString("MAT_TYPE")+"-"+rs.getString("STORAGE_LOCATION_NAME"));
					m.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					m.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					m.setMaterialGroupId(rs.getString("STXT"));
					m.setUnitOfMeasId(rs.getString("LTXT"));
					m.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
					m.setDivisionId(rs.getString("DIV_DESC"));
					System.out.println(m.getDivisionId());
					 isAsset=rs.getString("is_asset");
					if(isAsset.equalsIgnoreCase("1"))
					{
						m.setIsAsset("YES");
					}
					if(isAsset.equalsIgnoreCase("0")){
						m.setIsAsset("NO");
					}
					m.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
					m.setValuationClass(rs.getString("VALUATION_DESC"));
					m.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
					m.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
					 purposeId=rs.getString("PURPOSE_ID");
				    if(purposeId.equalsIgnoreCase("1"))
				    {
				    	m.setPurposeID("Gift & Compliments");
				    	
				    }
				    if(purposeId.equalsIgnoreCase("2"))
				    {
				    	m.setPurposeID("Propaganda & Promotional (KUDLU DEPOT)");
				    	
				    }
				    if(purposeId.equalsIgnoreCase("3"))
				    {
				    	m.setPurposeID("Product Launch Exp");
				    	
				    }
				    if(purposeId.equalsIgnoreCase("4"))
				    {
				    	m.setPurposeID("Sales Promotional (SAS)");
				    	
				    }
				    if(purposeId.equalsIgnoreCase("5"))
				    {
				    	m.setPurposeID("Visual Ads,Literature");
				    	
				    }
					
				    if(purposeId.equalsIgnoreCase("6"))
				    {
				    	m.setPurposeID("Conference,National & Regional");
				    	
				    }
				    if(purposeId.equalsIgnoreCase("7"))
				    {
				    	m.setPurposeID("Incentive to Field Staff");
				    	
				    }
				    if(purposeId.equalsIgnoreCase("8"))
				    {
				    	m.setPurposeID("Incentive to Stockist/Chemist");
				    	
				    }
				    if(purposeId.equalsIgnoreCase("9"))
				    {
				    	m.setPurposeID("Travelling Lodging & Boarding Exp");
				    	
				    }
					 isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
					if(isSAS.equalsIgnoreCase("1"))
					{
						m.setIsSASFormAvailable("YES");
					}
					if(isSAS.equalsIgnoreCase("0")){
						m.setIsSASFormAvailable("NO");
					}
		 		
		     }
				m.setMaterialGroupId_C("");
				m.setStorageLocationId_C("");
				m.setMaterialShortName_C("");
				m.setMaterialLongName_C("");
				m.setPuchaseGroupId_C("");
				m.setUnitOfMeasId_C("");
				m.setDivisionId_C("");
				m.setApproximateValue_C("");
				m.setValuationClass_C("");
				m.setDetailedJustification_C("");
				m.setDetailedSpecification_C("");
				m.setPurposeID_C("");
				m.setIsSASFormAvailable_C("");
				
				String getCreatorDetails="select mat.REQUEST_NO,mat.CREATED_BY,mat.CREATED_DATE,loc.LOCNAME,loc.LOCATION_CODE,stLoc.MAT_TYPE,stLoc.STORAGE_LOCATION_NAME," +
		 		"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, div.DIV_DESC," +
		 		"mat.is_asset,mat.PURPOSE_ID,mat.IS_SAS_FORM_AVAILABLE,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION," +
		 		"mat.DETAILED_SPECIFICATION from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as " +
		 		"matGroup , UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur,DIVISION as div,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' and role='Creator' and " +
		 		"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
		 		"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID  " +
		 		"and val.VALUATION_ID=mat.VALUATION_CLASS and Type='ZPPC'";
				ResultSet rsC=ad.selectQuery(getCreatorDetails);
				if(rsC.next())
				{
					String empNo=rs.getString("CREATED_BY");
		 		 	String empname="";
		 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
		 			ResultSet rs1=ad.selectQuery(empNameDetails);
		 			while(rs1.next()){
		 				empname=rs1.getString("EMP_FULLNAME");
		 			}
					m.setModifiedBy(empname);
					String reqDate=rsC.getString("CREATED_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					m.setRequestDate(reqDate);
					if(storageLoction.equals(rsC.getString("MAT_TYPE")))
						m.setStorageLocationId_C(rsC.getString("STORAGE_LOCATION_NAME")); 
					if(!m.getMaterialShortName().equals(rsC.getString("MATERIAL_SHORT_NAME")))
						m.setMaterialShortName_C(rsC.getString("MATERIAL_SHORT_NAME"));
					if(!m.getMaterialLongName().equals(rsC.getString("MATERIAL_LONG_NAME")))
						m.setMaterialLongName_C(rsC.getString("MATERIAL_LONG_NAME"));
					if(!m.getMaterialGroupId().equals(rsC.getString("STXT")))
						m.setMaterialGroupId_C(rsC.getString("STXT"));
					if(!m.getPuchaseGroupId().equals(rsC.getString("PURCHASE_GROUP_DESC")))
						m.setPuchaseGroupId_C(rsC.getString("PURCHASE_GROUP_DESC"));
					if(!m.getUnitOfMeasId().equals(rsC.getString("LTXT")))
						m.setUnitOfMeasId_C(rsC.getString("LTXT"));
					if(!m.getDivisionId().equals(rsC.getString("DIV_DESC")))
						m.setDivisionId_C(rsC.getString("DIV_DESC"));
					if(!isAsset.equals(rsC.getString("is_asset"))){
						 isAsset=rsC.getString("is_asset");
						if(isAsset.equalsIgnoreCase("1"))
							m.setIsAsset("YES");
						if(isAsset.equalsIgnoreCase("0"))
							m.setIsAsset("NO");
					}
					if(!m.getApproximateValue().equals(rsC.getString("APPROXIMATE_VALUE")))
						m.setApproximateValue_C(rsC.getString("APPROXIMATE_VALUE"));
					if(	!m.getValuationClass().equals(rsC.getString("VALUATION_DESC")))
						m.setValuationClass_C(rsC.getString("VALUATION_DESC"));
					if(!m.getDetailedJustification().equals(rsC.getString("DETAILED_JUSTIFICATION")))
				    	m.setDetailedJustification_C(rsC.getString("DETAILED_JUSTIFICATION"));
				    if(!m.getDetailedSpecification().equals(rsC.getString("DETAILED_SPECIFICATION")))
				    	m.setDetailedSpecification_C(rsC.getString("DETAILED_SPECIFICATION"));
				    if(!purposeId.equals(rsC.getString("PURPOSE_ID"))){
					    purposeId=rsC.getString("PURPOSE_ID");
					    if(purposeId.equalsIgnoreCase("1"))
					    	m.setPurposeID_C("Gift & Compliments");
					    if(purposeId.equalsIgnoreCase("2"))
					    	m.setPurposeID_C("Propaganda & Promotional (KUDLU DEPOT)");
					    if(purposeId.equalsIgnoreCase("3"))
					    	m.setPurposeID_C("Product Launch Exp");
					    if(purposeId.equalsIgnoreCase("4"))
					    	m.setPurposeID_C("Sales Promotional (SAS)");
					    if(purposeId.equalsIgnoreCase("5"))
					    	m.setPurposeID("Visual Ads,Literature");
					    if(purposeId.equalsIgnoreCase("6"))
					    	m.setPurposeID_C("Conference,National & Regional");
					    if(purposeId.equalsIgnoreCase("7"))
					    	m.setPurposeID_C("Incentive to Field Staff");
					    if(purposeId.equalsIgnoreCase("8"))
					    	m.setPurposeID_C("Incentive to Stockist/Chemist");
					    if(purposeId.equalsIgnoreCase("9"))
					    	m.setPurposeID_C("Travelling Lodging & Boarding Exp");
				    }
				    if(!isSAS.equals(rsC.getString("IS_SAS_FORM_AVAILABLE"))){
					    isSAS=rsC.getString("IS_SAS_FORM_AVAILABLE");
						if(isSAS.equalsIgnoreCase("1"))
							m.setIsSASFormAvailable("YES");
						if(isSAS.equalsIgnoreCase("0"))
							m.setIsSASFormAvailable("NO");
				    }
		}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			LinkedList list=new LinkedList();
			list.add(m);
			request.setAttribute("zppcdetails", list);
			return mapping.findForward("zppc");
		}
		if(matType.equalsIgnoreCase("OSE")){
			String isEquipment="";
			String isSpare="";
			String isNewEquipment="";
			String isNewfurniturt="";
			String isNewfacility="";
			String issparey="";
			String getZPSR="select mat.REQUEST_NO,mat.CREATED_BY,mat.REQUEST_DATE,mat.MATERIAL_GROUP_ID,loc.LOCNAME,loc.LOCATION_CODE,stLoc.MAT_TYPE,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
			 		" mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.IS_EQUIPMENT,mat.EQUIPMENT_NAME,mat.EQUIPMENT_MAKE," +
			 		"mat.Component_MAKE,mat.IS_SPARE,mat.OEM_PartNo,mat.IS_NEW_Equipment,mat.IS_NEW_Furniture,mat.IS_NEW_Facility,	mat.IS_Spare_required," +
			 		"mat.PO_NUMBER,mat.PR_NUMBER,dep.DPTSTXT,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,mat.moc,mat.rating,mat.range,mat.DIMENSION,mat.PACK_SIZE,mat.equip_Intended" +
			 		" from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , UNIT_MESUREMENT AS uom " +
			 		",PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+reqId+"' and	loc.LOCID=mat.LOCATION_ID and " +
			 		"stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID " +
			 		"and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='ZPSR' and val.VALUATION_ID=mat.VALUATION_CLASS and dep.DPTID=mat.UTILIZING_DEPT";
			 		ResultSet rs=ad.selectQuery(getZPSR);
			 		try{
					if(rs.next())
					{
						m.setRequestNumber(reqId);
						String empname="";
						String empNo=rs.getString("CREATED_BY");
			 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
			 			ResultSet rs1=ad.selectQuery(empNameDetails);
			 			while(rs1.next()){
			 				empname=rs1.getString("EMP_FULLNAME");
			 			}
			 		 	m.setRequestedBy(empname);
						m.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
						m.setLocationId(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						m.setRequestDate(reqDate);
					//	m.setLocationId(rs.getString("MAT_TYPE")+"-"+rs.getString("STORAGE_LOCATION_NAME"));
						m.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						m.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
						m.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						m.setMaterialGroupId(rs.getString("STXT"));
						m.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						m.setUnitOfMeasId(rs.getString("LTXT"));
						m.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						m.setValuationClass(rs.getString("VALUATION_DESC"));
						 isEquipment=rs.getString("IS_EQUIPMENT");
						if(isEquipment.equalsIgnoreCase("1"))
						{
							m.setIsEquipment("Yes");
						}
						if(isEquipment.equalsIgnoreCase("0")){
							m.setIsEquipment("No");
						}
						m.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
						m.setEquipmentMake(rs.getString("EQUIPMENT_MAKE"));
						
						 isSpare=rs.getString("IS_SPARE");
						if(isSpare.equalsIgnoreCase("1"))
						{
							m.setIsSpare("Yes");
						}
						if(isSpare.equalsIgnoreCase("0")){
							m.setIsSpare("No");
						}
						m.setComponentMake(rs.getString("Component_MAKE"));
						m.setOemPartNo(rs.getString("OEM_PartNo"));
						
						m.setMoc(rs.getString("moc")); 
						m.setRating(rs.getString("rating"));
						m.setRange(rs.getString("range"));
						isNewEquipment=rs.getString("IS_NEW_Equipment");
						if(isNewEquipment.equalsIgnoreCase("1"))
						{
							m.setIsNewEquipment("Yes");
						}
						if(isNewEquipment.equalsIgnoreCase("0"))
						{
							m.setIsNewEquipment("No");
						}
						if(isNewEquipment.equalsIgnoreCase("2"))
						{
							m.setIsNewEquipment("N/A");
						}
						isNewfurniturt=rs.getString("IS_NEW_Furniture");
						if(isNewfurniturt.equalsIgnoreCase("1"))
						{
							m.setIsItNewFurniture("Yes");
						}
						if(isNewfurniturt.equalsIgnoreCase("0"))
						{
							m.setIsItNewFurniture("No");
						}
						if(isNewfurniturt.equalsIgnoreCase("2"))
						{
							m.setIsItNewFurniture("N/A");
						}
						isNewfacility=rs.getString("IS_NEW_Facility");
						if(isNewfacility.equalsIgnoreCase("1"))
						{
							m.setIsItFacility("Yes");
						}
						if(isNewfacility.equalsIgnoreCase("0"))
						{
							m.setIsItFacility("No");
						}
						if(isNewfacility.equalsIgnoreCase("2"))
						{
							m.setIsItFacility("N/A");
						}
						issparey=rs.getString("IS_Spare_required");
						if(issparey.equalsIgnoreCase("1"))
						{
							m.setIsSpareNewEquipment("Yes");
						}
						if(issparey.equalsIgnoreCase("0"))
						{
							m.setIsSpareNewEquipment("No");
							
						}					
						if(issparey.equalsIgnoreCase("2"))
						{
							m.setIsSpareNewEquipment("N/A");
						}
						m.setPrNumber(rs.getString("PR_NUMBER"));
						m.setPoNumber(rs.getString("PO_NUMBER"));
						m.setUtilizingDept(rs.getString("DPTSTXT"));
						m.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						m.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						m.setDimensions(rs.getString("DIMENSION"));
						m.setPackSize(rs.getString("PACK_SIZE"));
						m.setEquipIntendedFor(rs.getString("equip_Intended"));
					}
					
					m.setMaterialGroupId_C("");
					m.setStorageLocationId_C("");
					m.setMaterialShortName_C("");
					m.setMaterialLongName_C("");
					m.setPuchaseGroupId_C("");
					m.setUnitOfMeasId_C("");
					m.setApproximateValue_C("");
					m.setValuationClass_C("");
					m.setEquipmentName_C("");
					m.setEquipmentMake_C("");
					m.setIsEquipment_C("");
					m.setIsNew_C("");
					m.setComponentMake_C("");
					m.setOemPartNo_C("");
					m.setIsSpare_C("");
					m.setMoc_C("");
					m.setRating_C("");
					m.setRange_C("");
					m.setIsNewEquipment_C("");
					m.setIsItNewFurniture_C("");
					m.setIsItFacility_C("");
					m.setIsSpareNewEquipment_C("");
					m.setPrNumber_C("");
					m.setPoNumber_C("");
					m.setUtilizingDept_C("");
					m.setDetailedJustification_C("");
					m.setDetailedSpecification_C("");
					m.setDimensions_C("");
					m.setPackSize_C("");
					m.setEquipIntendedFor_C("");
					
					
					
		
		String getCreatorDetails="select mat.REQUEST_NO,mat.CREATED_DATE,mat.CREATED_BY,mat.MATERIAL_GROUP_ID,loc.LOCNAME,loc.LOCATION_CODE,stLoc.MAT_TYPE,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
 		" mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.IS_EQUIPMENT,mat.EQUIPMENT_NAME,mat.EQUIPMENT_MAKE," +
 		"mat.Component_MAKE,mat.IS_SPARE,mat.OEM_PartNo,mat.IS_NEW_Equipment,mat.IS_NEW_Furniture,mat.IS_NEW_Facility,	mat.IS_Spare_required," +
 		"mat.PO_NUMBER,mat.PR_NUMBER,dep.DPTSTXT,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,mat.moc,mat.rating,mat.range,mat.DIMENSION,mat.PACK_SIZE,mat.equip_Intended" +
 		" from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , UNIT_MESUREMENT AS uom " +
 		",PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+reqId+"' and role='Creator' and	loc.LOCID=mat.LOCATION_ID and " +
 		"stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID " +
 		"and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='ZPSR' and val.VALUATION_ID=mat.VALUATION_CLASS and dep.DPTID=mat.UTILIZING_DEPT";
		ResultSet rsC=ad.selectQuery(getCreatorDetails);			
		while(rsC.next()){
			String empNo=rs.getString("CREATED_BY");
 		 	String empname="";
 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
 			ResultSet rs1=ad.selectQuery(empNameDetails);
 			while(rs1.next()){
 				empname=rs1.getString("EMP_FULLNAME");
 			}
			m.setModifiedBy(empname);
			String reqDate=rsC.getString("CREATED_DATE");
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			m.setModifiedDate(reqDate);
			if(!m.getStorageLocationId().equals(rsC.getString("STORAGE_LOCATION_NAME")))
				m.setStorageLocationId_C(rsC.getString("STORAGE_LOCATION_NAME")); 
			if(!m.getMaterialShortName().equals(rsC.getString("MATERIAL_SHORT_NAME")))
				m.setMaterialShortName_C(rsC.getString("MATERIAL_SHORT_NAME"));
			if(!m.getMaterialLongName().equals(rsC.getString("MATERIAL_LONG_NAME")))
				m.setMaterialLongName_C(rsC.getString("MATERIAL_LONG_NAME"));
			if(!m.getMaterialGroupId().equals(rsC.getString("STXT")))
				m.setMaterialGroupId_C(rsC.getString("STXT"));
			if(!m.getPuchaseGroupId().equals(rsC.getString("PURCHASE_GROUP_DESC")))
				m.setPuchaseGroupId_C(rsC.getString("PURCHASE_GROUP_DESC"));
			if(!m.getUnitOfMeasId().equals(rsC.getString("LTXT")))
				m.setUnitOfMeasId_C(rsC.getString("LTXT"));
			if(!m.getApproximateValue().equals(rsC.getString("APPROXIMATE_VALUE")))
				m.setApproximateValue_C(rsC.getString("APPROXIMATE_VALUE"));
			if(	!m.getValuationClass().equals(rsC.getString("VALUATION_DESC")))
				m.setValuationClass_C(rsC.getString("VALUATION_DESC"));
			if(!isEquipment.equals(rsC.getString("IS_EQUIPMENT"))){
				isEquipment=rsC.getString("IS_EQUIPMENT");
				if(isEquipment.equalsIgnoreCase("1"))
				{
					m.setIsEquipment("Yes");
				}
				if(isEquipment.equalsIgnoreCase("0")){
					m.setIsEquipment("No");
				}
			}
			
			if(!m.getEquipmentName_C().equals(rsC.getString("EQUIPMENT_NAME")))
				m.setEquipmentName_C(rsC.getString("EQUIPMENT_NAME"));
			if(!m.getEquipmentMake_C().equals(rsC.getString("EQUIPMENT_MAKE")))
				m.setEquipmentMake_C(rsC.getString("EQUIPMENT_MAKE"));
			if(!isSpare.equals(rsC.getString("IS_SPARE"))){
			 isSpare=rs.getString("IS_SPARE");
				if(isSpare.equalsIgnoreCase("1"))
				{
					m.setIsSpare_C("Yes");
				}
				if(isSpare.equalsIgnoreCase("0")){
					m.setIsSpare_C("No");
				}
			}
			if(!m.getComponentMake().equals(rsC.getString("Component_MAKE")));
			 	m.setComponentMake_C(rsC.getString("Component_MAKE"));
            if(!m.getOemPartNo().equals(rs.getString("OEM_PartNo")))
                m.setOemPartNo_C(rsC.getString("OEM_PartNo"));
			if(!m.getMoc().equals(rsC.getString("moc")))
				m.setMoc_C(rsC.getString("moc"));
			if(!m.getRating().equals(rsC.getString("rating")))
				m.setRating_C(rsC.getString("rating"));
			if(!m.getRange().equals(rsC.getString("range")))
				m.setRange_C(rsC.getString("range"));
		    if(!isNewEquipment.equals(rsC.getString("IS_NEW_Equipment"))){
				isNewEquipment=rsC.getString("IS_NEW_Equipment");
				if(isNewEquipment.equalsIgnoreCase("1"))
				{
					m.setIsNewEquipment_C("Yes");
				}
				if(isNewEquipment.equalsIgnoreCase("0"))
				{
					m.setIsNewEquipment_C("No");
				}
				if(isNewEquipment.equalsIgnoreCase("2"))
				{
					m.setIsNewEquipment_C("N/A");
				}
		    }
		    if(!isNewfurniturt.equals(rsC.getString("IS_NEW_Furniture"))){
			    isNewfurniturt=rsC.getString("IS_NEW_Furniture");
				if(isNewfurniturt.equalsIgnoreCase("1"))
				{
					m.setIsItNewFurniture_C("Yes");
				}
				if(isNewfurniturt.equalsIgnoreCase("0"))
				{
					m.setIsItNewFurniture_C("No");
				}
				if(isNewfurniturt.equalsIgnoreCase("2"))
				{
					m.setIsItNewFurniture_C("N/A");
				}
		    }
		    if(!isNewfacility.equals(rsC.getString("IS_NEW_Facility"))){
			    isNewfacility=rsC.getString("IS_NEW_Facility");
				if(isNewfacility.equalsIgnoreCase("1"))
				{
					m.setIsItFacility_C("Yes");
				}
				if(isNewfacility.equalsIgnoreCase("0"))
				{
					m.setIsItFacility_C("No");
				}
				if(isNewfacility.equalsIgnoreCase("2"))
				{
					m.setIsItFacility_C("N/A");
				}
		    }
		    if(!issparey.equals(rsC.getString("IS_Spare_required"))){
			    issparey=rsC.getString("IS_Spare_required");
				if(issparey.equalsIgnoreCase("1"))
				{
					m.setIsSpareNewEquipment_C("Yes");
				}
				if(issparey.equalsIgnoreCase("0"))
				{
					m.setIsSpareNewEquipment_C("No");
					
				}					
				if(issparey.equalsIgnoreCase("2"))
				{
					m.setIsSpareNewEquipment_C("N/A");
				}
		    }
		    if(!m.getPrNumber().equals(rsC.getString("PR_NUMBER")))
		    	m.setPrNumber_C(rsC.getString("PR_NUMBER"));
		    if(!m.getPoNumber().equals(rsC.getString("PO_NUMBER")))
		    	m.setPoNumber_C(rsC.getString("PO_NUMBER"));
		    if(!m.getUtilizingDept().equals(rsC.getString("DPTSTXT")))
		    	m.setUtilizingDept_C(rsC.getString("DPTSTXT"));
		    if(!m.getDetailedJustification().equals(rsC.getString("DETAILED_JUSTIFICATION")))
		    	m.setDetailedJustification_C(rsC.getString("DETAILED_JUSTIFICATION"));
		    if(!m.getDetailedSpecification().equals(rsC.getString("DETAILED_SPECIFICATION")))
		    	m.setDetailedSpecification_C(rsC.getString("DETAILED_SPECIFICATION"));
		    if(!m.getDimensions().equals(rsC.getString("DIMENSION")))
		    	m.setDimensions_C(rsC.getString("DIMENSION"));
		    if(!m.getPackSize().equals(rsC.getString("PACK_SIZE")))
		    	m.setPackSize(rsC.getString("PACK_SIZE"));
		    if(!m.getEquipIntendedFor().equals(rsC.getString("equip_Intended")))
		    		m.setEquipIntendedFor_C(rsC.getString("equip_Intended"));
		}
	 		}catch(Exception e){
	 			e.printStackTrace();
	 		}
	 		LinkedList list=new LinkedList();
			list.add(m);
			request.setAttribute("zpsrdetails", list);
			return mapping.findForward("zpsr");
		}
		if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("LC")
				||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
	     {
			String strogetLocId="";
			String isAsset="";
		try{
			
		String getUserDetails="select mat.REQUEST_NO,mat.CREATED_BY,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,loc.LOCATION_CODE,stLoc.STORAGE_LOCATION_NAME,stLoc.MAT_TYPE," +
				"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.is_asset,dep.DPTSTXT" +
				",mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,mat.rejected_flag from " +
				"material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , " +
				"UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+reqId+"' and mat.role='user' and	" +
				"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
				"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='"+matType+"' and val.VALUATION_ID=mat.VALUATION_CLASS" +
				" and dep.DPTID=mat.UTILIZING_DEPT";
		
		ResultSet rs=ad.selectQuery(getUserDetails);
		if(rs.next())
		{
			m.setRequestNumber(reqId);
			String empNo=rs.getString("CREATED_BY");
 		 	String empname="";
 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
 			ResultSet rs1=ad.selectQuery(empNameDetails);
 			while(rs1.next()){
 				empname=rs1.getString("EMP_FULLNAME");
 			}
			m.setRequestedBy(empname);
			m.setLocationId(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));
			String reqDate=rs.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			m.setRequestDate(reqDate);
			m.setStorageLocationId(rs.getString("MAT_TYPE")+"-"+rs.getString("STORAGE_LOCATION_NAME"));
			strogetLocId=rs.getString("MAT_TYPE");
			m.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
			m.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
			m.setMaterialGroupId(rs.getString("STXT"));
			m.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
			m.setUnitOfMeasId(rs.getString("LTXT"));
			m.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			m.setValuationClass(rs.getString("VALUATION_DESC"));
			m.setUtilizingDept(rs.getString("DPTSTXT"));
			 isAsset=rs.getString("is_asset");
			if(isAsset.equalsIgnoreCase("1"))
			{
				m.setIsAsset("YES");
			}
			if(isAsset.equalsIgnoreCase("0"))
				m.setIsAsset("NO");
			m.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
			m.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		m.setStorageLocationId_C("");
		m.setMaterialShortName_C("");
		m.setMaterialLongName_C("");
		m.setMaterialGroupId_C("");
		m.setPuchaseGroupId_C("");
		m.setDetailedJustification_C("");
		m.setDetailedSpecification_C("");
		m.setUnitOfMeasId_C("");
		m.setApproximateValue_C("");
		m.setValuationClass_C("");
		m.setIsAsset("");
		
		
	
		
		String getCreatorDetails="select mat.REQUEST_NO,mat.CREATED_DATE,mat.CREATED_BY,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,loc.LOCATION_CODE,"
		+ "stLoc.STORAGE_LOCATION_NAME,stLoc.MAT_TYPE,mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,"
		+ "mat.is_asset,dep.DPTSTXT,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,mat.rejected_flag "
		+ "from material_code_request_history as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom ,"
		+ "PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+reqId+"' and mat.Role='Creator'  and	stLoc.MAT_TYPE='ZLAB' "
		+ "AND loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and "
		+ "uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='ZLAB' and val.VALUATION_ID=mat.VALUATION_CLASS "
		+ "and dep.DPTID=mat.UTILIZING_DEPT";
		ResultSet rsC=ad.selectQuery(getCreatorDetails);
		try {
			if(rsC.next()){
				
				String empNo=rsC.getString("CREATED_BY");
	 		 	String empname="";
	 		 	String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
	 			ResultSet rs1=ad.selectQuery(empNameDetails);
	 			while(rs1.next()){
	 				empname=rs1.getString("EMP_FULLNAME");
	 			}
				m.setModifiedBy(empname);
				String reqDate=rsC.getString("CREATED_DATE");
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				m.setModifiedDate(reqDate);
				if(!m.getStorageLocationId().equals(rsC.getString("MAT_TYPE")))
					m.setStorageLocationId_C(rsC.getString("MAT_TYPE")+"-"+rsC.getString("STORAGE_LOCATION_NAME"));
				if(!m.getMaterialShortName().equals(rsC.getString("MATERIAL_SHORT_NAME")))
					m.setMaterialShortName_C(rsC.getString("MATERIAL_SHORT_NAME"));
				if(!m.getMaterialLongName().equals(rsC.getString("MATERIAL_LONG_NAME")))
					m.setMaterialLongName_C(rsC.getString("MATERIAL_LONG_NAME"));
				if(!m.getMaterialGroupId().equals(rsC.getString("STXT")))
					m.setMaterialGroupId_C(rsC.getString("STXT"));
				if(!m.getPuchaseGroupId().equals(rsC.getString("PURCHASE_GROUP_DESC")))
					m.setPuchaseGroupId_C(rsC.getString("PURCHASE_GROUP_DESC"));
				if(!m.getUnitOfMeasId().equals(rsC.getString("LTXT")))
					m.setUnitOfMeasId_C(rsC.getString("LTXT"));
				if(!m.getApproximateValue().equals(rsC.getString("APPROXIMATE_VALUE")))
					m.setApproximateValue_C(rsC.getString("APPROXIMATE_VALUE"));
				if(!m.getValuationClass().equals(rsC.getString("VALUATION_DESC")))
					m.setValuationClass_C(rsC.getString("VALUATION_DESC"));
				if(!isAsset.equals(rsC.getString("is_asset"))){
					isAsset=rsC.getString("is_asset");
						if(isAsset.equalsIgnoreCase("1"))
						{
							m.setIsAsset("YES");
						}
						if(isAsset.equalsIgnoreCase("0"))
							m.setIsAsset("NO");
				}
				if(!m.getDetailedJustification().equals(rsC.getString("DETAILED_JUSTIFICATION")))
					m.setDetailedJustification_C(rsC.getString("DETAILED_JUSTIFICATION"));
				if(!m.getDetailedSpecification().equals(rsC.getString("DETAILED_SPECIFICATION")))
					m.setDetailedSpecification_C(rsC.getString("DETAILED_SPECIFICATION"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LinkedList list=new LinkedList();
		list.add(m);
		request.setAttribute("generalMaterial", list);
		return mapping.findForward("generalMaterial");
	   }
		return mapping.findForward("generalMaterial");
	}
	
	public String empDetails(String empNo){
		String empname="";
		EssDao ad=new EssDao();
		String empNameDetails="select PERNR,EMP_FULLNAME from emp_official_info where PERNR='"+empNo+"' ";
		ResultSet rs1=ad.selectQuery(empNameDetails);
		try{
				while(rs1.next()){
					empname=rs1.getString("PERNR")+"-"+rs1.getString("EMP_FULLNAME");
				}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return empname;
	}

}
