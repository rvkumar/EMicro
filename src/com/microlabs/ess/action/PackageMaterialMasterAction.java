package com.microlabs.ess.action;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.MaterialmasterDAO;
import com.microlabs.ess.form.CustomerMasterForm;
import com.microlabs.ess.form.PackageMaterialMasterForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class PackageMaterialMasterAction extends DispatchAction{
	
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		PackageMaterialMasterForm masterForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		LinkedList apprList=new LinkedList();
		MaterialmasterDAO dao=new MaterialmasterDAO();
		String location=request.getParameter("locationId");
		String matGroup=request.getParameter("materialGroupId");
		String materialTypeId="2";
		String groupName="";
		String getGroupName="select STXT from MATERIAL_GROUP where MATERIAL_GROUP_ID ='"+matGroup+"'";
		ResultSet rs=ad.selectQuery(getGroupName);
		try{
		while(rs.next()){
			groupName=rs.getString("STXT");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		apprList=dao.approversList(location, "2", matGroup);
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
		return mapping.findForward("apprList");
	}
	public ActionForward saveAndSubmitMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		
		String maild=request.getParameter("EMAIL");
		maild=maild.replace("*", "&");

		//insert emial id
		if(!maild.equalsIgnoreCase("null")||!maild.equalsIgnoreCase(""))
		{
		String mail="Update emp_official_info set EMAIL_ID='"+maild+"' where pernr='"+user.getEmployeeNo()+"'";
		ad.SqlExecuteUpdate(mail);
		}
		
		int userId=user.getId();
		  String userTextId=Integer.toString(userId);
		Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		  String dateNow = formatter.format(currentDate.getTime());
		  String reqDate=materialForm.getRequestDate();
			 
		  String a[]=reqDate.split("/");
		  
		 
		  reqDate=a[2]+"-"+a[1]+"-"+a[0];
		  
		  
		  String fileList="";
		  try{
			  
			  int fileCount=0;
			  
			String getFileCount="select * from  PacakageMaterial_Doc where  request_no='"+materialForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rsFileCount=ad.selectQuery(getFileCount);
			while(rsFileCount.next())
			{
				fileCount=1;
			}
	
			
			if(fileCount>0){
				String getUploadedFiles="select * from PacakageMaterial_Doc where request_no='"+materialForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"'";
				ResultSet rs=ad.selectQuery(getUploadedFiles);
				while(rs.next())
				{
					fileList+="jsp/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/"+"/"+rs.getString("file_name")+",";
				}
		  int count=0;
		  String typeDetails=materialForm.getTypeDetails();
		  
		  
		  int checkApprover=0;
		  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='PM' and " +
	  		" Location=(select LOCATION_CODE from Location where LOCID='"+materialForm.getLocationId()+"') and Material_Group='"+materialForm.getMaterialGroupId()+"'";
		  ResultSet rsCheck=ad.selectQuery(checkApprovers);
		  while(rsCheck.next())
		  {
			  checkApprover=1;
		  }
		  if(checkApprover==0)
		  {
			  checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='PM' and " +
				  		" Location=(select LOCATION_CODE from Location where LOCID='"+materialForm.getLocationId()+"') and Material_Group=''";
					   rsCheck=ad.selectQuery(checkApprovers);
				  while(rsCheck.next())
				  {
					  checkApprover=1;
				  }
		  }
		
		 if(typeDetails.equalsIgnoreCase("Save"))
		 {
			 
			 String getcount="select count(*) from material_code_request where REQUEST_NO='"+materialForm.getRequestNo()+"'";
				ResultSet rs1=ad.selectQuery(getcount);
				while(rs1.next())
				{
					count=rs1.getInt(1);
				}
				String insertQuery="";
				String approve="Pending";
				//added for approval task
				
				
				int i=0;
			if(count>0){	
				//RequestNo is available in database
				
				int old_RequestNo=materialForm.getRequestNo();
				String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				materialForm.setRequestNo(maxReqno);
			 
			 String url="packageMaterial.do?method=editPackageMaterialRecord";
			 String reportURL="packageMaterial.do?method=getReport";
insertQuery="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
			",MATERIAL_GROUP_ID,IS_DMF_MATERIAL,DMF_GRADE_ID,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME,TO_BE_USED_IN_PRODUCTS" +
			",IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,RETEST_DAYS,RETEST_DAYS_TYPE,DUTY_ELEMENT,PACKING_MATERIAL_GROUP,Type_Of_Material,IS_ARTWORK_REVISION ,EXISTING_SAP_ITEM_CODE" +
			" ,APPROXIMATE_VALUE,REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,ARTWORK_NO,UNIT_OF_MEAS_ID,VALUATION_CLASS,PURCHASE_GROUP_ID,Approve_Type,URL,REPORT_URL,last_approver,pending_approver,Storage,HSN_Code)" +
			" values('"+materialForm.getRequestNo()+"','"+reqDate+"','"+materialForm.getLocationId()+"','"+materialForm.getStorageLocationId()+"','2','"+materialForm.getMaterialShortName()+"'" +
			",'"+materialForm.getMaterialLongName()+"','"+materialForm.getMaterialGroupId()+"','"+materialForm.getIsDMFMaterial()+"','"+materialForm.getDmfGradeId()+"','"+materialForm.getCosGradeNo()+"'" +
			",'"+materialForm.getAdditionalTest()+"','"+materialForm.getCountryId()+"','"+materialForm.getCustomerName()+"','"+materialForm.getToBeUsedInProducts()+"','"+materialForm.getIsVendorSpecificMaterial()+"','"+materialForm.getMfgrName()+"','"+materialForm.getSiteOfManufacture()+"','"+materialForm.getTempCondition()+"','"+materialForm.getStorageCondition()+"'" +
			",'"+materialForm.getRetestDays()+"','"+materialForm.getRetestType()+"','"+materialForm.getDutyElement()+"','"+materialForm.getPackageMaterialGroup()+"','"+materialForm.getTypeOfMaterial()+"','"+materialForm.getIsArtworkRevision()+"','"+materialForm.getExistingSAPItemCode()+"','"+materialForm.getApproximateValue()+"'" +
			",'"+materialForm.getRequestedBy()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+fileList+"','PM','"+materialForm.getArtworkNo()+"','"+materialForm.getUnitOfMeasId()+"','"+materialForm.getValuationClass()+"','"+materialForm.getPuchaseGroupId()+"','Created','"+url+"','"+reportURL+"','No','','"+materialForm.getStorage()+"','"+materialForm.getHsnCode()+"') ";	
			i=ad.SqlExecuteUpdate(insertQuery);
				int j=0;
			j=ad.SqlExecuteUpdate("update PacakageMaterial_Doc set request_no='"+materialForm.getRequestNo()+"' where request_no='"+old_RequestNo+"' and  userId='"+userId+"'");
			
			if(i>0)
			{
				materialForm.setMessage2("Alert Code creation request created with New request number='"+materialForm.getRequestNo()+"'.");
				materialForm.setTypeDetails("Update");
				if(checkApprover==0)
				{
					materialForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				return displayNewPackageMaterial(mapping, form, request, response);
				}
				
				
			}else{
				materialForm.setMessage("Error...When Saving  Material.Please Check....");
				materialForm.setTypeDetails("Save");
			}
			}
			else{
				//Request no is not available in database
				 String url="packageMaterial.do?method=editPackageMaterialRecord";
				 String reportURL="packageMaterial.do?method=getReport";
	insertQuery="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
				",MATERIAL_GROUP_ID,IS_DMF_MATERIAL,DMF_GRADE_ID,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME,TO_BE_USED_IN_PRODUCTS" +
				",IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,RETEST_DAYS,RETEST_DAYS_TYPE,DUTY_ELEMENT,PACKING_MATERIAL_GROUP,Type_Of_Material,IS_ARTWORK_REVISION ,EXISTING_SAP_ITEM_CODE" +
				" ,APPROXIMATE_VALUE,REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,ARTWORK_NO,UNIT_OF_MEAS_ID,VALUATION_CLASS,PURCHASE_GROUP_ID,Approve_Type,URL,REPORT_URL,last_approver,pending_approver,Storage,HSN_Code)" +
				" values('"+materialForm.getRequestNo()+"','"+reqDate+"','"+materialForm.getLocationId()+"','"+materialForm.getStorageLocationId()+"','2','"+materialForm.getMaterialShortName()+"'" +
				",'"+materialForm.getMaterialLongName()+"','"+materialForm.getMaterialGroupId()+"','"+materialForm.getIsDMFMaterial()+"','"+materialForm.getDmfGradeId()+"','"+materialForm.getCosGradeNo()+"'" +
				",'"+materialForm.getAdditionalTest()+"','"+materialForm.getCountryId()+"','"+materialForm.getCustomerName()+"','"+materialForm.getToBeUsedInProducts()+"','"+materialForm.getIsVendorSpecificMaterial()+"','"+materialForm.getMfgrName()+"','"+materialForm.getSiteOfManufacture()+"','"+materialForm.getTempCondition()+"','"+materialForm.getStorageCondition()+"'" +
				",'"+materialForm.getRetestDays()+"','"+materialForm.getRetestType()+"','"+materialForm.getDutyElement()+"','"+materialForm.getPackageMaterialGroup()+"','"+materialForm.getTypeOfMaterial()+"','"+materialForm.getIsArtworkRevision()+"','"+materialForm.getExistingSAPItemCode()+"','"+materialForm.getApproximateValue()+"'" +
				",'"+materialForm.getRequestedBy()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+fileList+"','PM','"+materialForm.getArtworkNo()+"','"+materialForm.getUnitOfMeasId()+"','"+materialForm.getValuationClass()+"','"+materialForm.getPuchaseGroupId()+"','Created','"+url+"','"+reportURL+"','No','','"+materialForm.getStorage()+"','"+materialForm.getHsnCode()+"') ";	
				i=ad.SqlExecuteUpdate(insertQuery);
				if(i>0)
				{
					materialForm.setMessage2("Code creation request created with request number='"+materialForm.getRequestNo()+"'.");
					materialForm.setTypeDetails("Update");
					if(checkApprover==0)
					{
						materialForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");	
					return displayNewPackageMaterial(mapping, form, request, response);
					}
					
				}else{
					materialForm.setMessage("Error...When Saving  Material.Please Check....");
					materialForm.setTypeDetails("Save");
				}
				
			}
			/*String Req_Id = ""+materialForm.getRequestNo();
			EMailer email = new EMailer();
			i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");*/
		 }
		 else{
				String approvedStatus="Pending";
				
				 user=(UserInfo)session.getAttribute("user");
					if(userId==1)
					{
						approvedStatus=materialForm.getApproveType();
					}
					int i=0;
					
					String recordStatus="";
					String getRecordStatus="select Approve_Type from material_code_request where REQUEST_NO='"+materialForm.getRequestNo()+"' ";
					ResultSet rsRecord=ad.selectQuery(getRecordStatus);
					while(rsRecord.next())
					{
						recordStatus=rsRecord.getString("Approve_Type");
					}
					if(recordStatus.equalsIgnoreCase("Rejected"))
					{
						
						String deleteRecords="delete from All_Request where Req_Id='"+materialForm.getRequestNo()+"' and Req_Type='Material Master'";
						int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
						
						String updateFlag="update material_code_request set rejected_flag='y' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
						ad.SqlExecuteUpdate(updateFlag);
					}		
					
			
			 String updateQuery="update material_code_request set LOCATION_ID='"+materialForm.getLocationId()+"',STORAGE_LOCATION_ID='"+materialForm.getStorageLocationId()+"',MATERIAL_TYPE_ID='2',MATERIAL_SHORT_NAME='"+materialForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+materialForm.getMaterialLongName()+"'" +
			",MATERIAL_GROUP_ID='"+materialForm.getMaterialGroupId()+"',IS_DMF_MATERIAL='"+materialForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+materialForm.getDmfGradeId()+"',COS_GRADE_AND_NO='"+materialForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+materialForm.getAdditionalTest()+"',COUNTRY_ID='"+materialForm.getCountryId()+"',CUSTOMER_NAME='"+materialForm.getCustomerName()+"',TO_BE_USED_IN_PRODUCTS='"+materialForm.getToBeUsedInProducts()+"'" +
			",IS_VENDOR_SPECIFIC_MATERIAL='"+materialForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+materialForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+materialForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+materialForm.getTempCondition()+"',STORAGE_CONDITION='"+materialForm.getStorageCondition()+"',RETEST_DAYS='"+materialForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+materialForm.getRetestType()+"',DUTY_ELEMENT='"+materialForm.getDutyElement()+"',PACKING_MATERIAL_GROUP='"+materialForm.getPackageMaterialGroup()+"',Type_Of_Material='"+materialForm.getTypeOfMaterial()+"',IS_ARTWORK_REVISION='"+materialForm.getIsArtworkRevision()+"' ,EXISTING_SAP_ITEM_CODE='"+materialForm.getExistingSAPItemCode()+"'" +
			" ,APPROXIMATE_VALUE='"+materialForm.getApproximateValue()+"',REQUESTED_BY='"+materialForm.getRequestedBy()+"',Attachements='"+fileList+"',ARTWORK_NO='"+materialForm.getArtworkNo()+"',UNIT_OF_MEAS_ID='"+materialForm.getUnitOfMeasId()+"',VALUATION_CLASS='"+materialForm.getValuationClass()+"'," +
			"PURCHASE_GROUP_ID='"+materialForm.getPuchaseGroupId()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',Storage ='"+materialForm.getStorage()+"',HSN_Code='"+materialForm.getHsnCode()+"' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
			 
				i=ad.SqlExecuteUpdate(updateQuery);
					
					/*if(userId==1)
					{
						String updateMaterial="update material_code_request set Approve_Type='"+approvedStatus+"' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
						i=ad.SqlExecuteUpdate(updateMaterial);
					}
				if(userId==2)
				{
				 String sapCreationDate=materialForm.getSapCreationDate();
				  String b[]=sapCreationDate.split("/");
					sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
					String updateMaterial="update material_code_request set SAP_CODE_NO='"+materialForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+materialForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+materialForm.getSapCreatedBy()+"' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
					i=ad.SqlExecuteUpdate(updateMaterial);
					request.setAttribute("approved", "approved");
					request.setAttribute("sapApprover", "sapApprover");
				}
*/				if(i>0)
				{
					materialForm.setMessage2("Code creation request updated with request number='"+materialForm.getRequestNo()+"'.");
					materialForm.setTypeDetails("Update");
					if(checkApprover==0)
					{
						materialForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
					return displayNewPackageMaterial(mapping, form, request, response);
					}
					
				}else{
					materialForm.setMessage("Error...When modifying package material.Please Check....");
					materialForm.setTypeDetails("Update");
				}
			 
		 }
		 
		 
		 
		 if(checkApprover==1)
			{

				//send request to  approvers
				
				Date dNow1 = new Date( );
				SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
				String dateNow1 = ft.format(dNow1);
			 	String pApprover="";
			 	String parllelAppr1="";
			 	String parllelAppr2="";
	  		String matGroup="";
	  		String loctID="";
	  		String matType="";
			 	String getMatGroup="select mast.Type,mast.MATERIAL_GROUP_ID,loc.LOCATION_CODE from material_code_request  as mast,Location as loc where mast.REQUEST_NO='"+materialForm.getRequestNo()+"' and loc.LOCID=mast.LOCATION_ID ";
			 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
			 	while(rsMatGrup.next()){
			 		matGroup=rsMatGrup.getString("MATERIAL_GROUP_ID");
			 		loctID=rsMatGrup.getString("LOCATION_CODE");
			 		matType=rsMatGrup.getString("Type");
			 	}
			 checkApprover=0;
			 	String pendingApprs="";
			 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='PM' and app.Material_Group='"+matGroup+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
	  		ResultSet rsAppr=ad.selectQuery(getApprovers);
	  		while(rsAppr.next())
	  		{
	  			
	  			checkApprover=1;
	  			if(rsAppr.getInt("Priority")==1){
	  			pApprover=rsAppr.getString("Approver_Id");
	  			parllelAppr1=rsAppr.getString("Parllel_Approver_1");
	  			parllelAppr2=rsAppr.getString("Parllel_Approver_2");
	  			
	  			}
	  			pendingApprs=pendingApprs+rsAppr.getString("EMP_FULLNAME")+" , ";
	  		}
	  		if(checkApprover==0)
	  		{
	  			 getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='PM' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
	  			 rsAppr=ad.selectQuery(getApprovers);
	      		while(rsAppr.next())
	      		{
	      			if(rsAppr.getInt("Priority")==1){
	          			pApprover=rsAppr.getString("Approver_Id");
	          			parllelAppr1=rsAppr.getString("Parllel_Approver_1");
	          			parllelAppr2=rsAppr.getString("Parllel_Approver_2");
	          			
	          			}
	      			
	      			pendingApprs=pendingApprs+rsAppr.getString("EMP_FULLNAME")+" , ";
	      		}
	  		}
	  		
	  		if(!(pendingApprs.equalsIgnoreCase(""))){
	  			int size=pendingApprs.length();
	  			pendingApprs=pendingApprs.substring(0, (size-2));
	  		}
	  		if(!(pApprover.equalsIgnoreCase(""))){
	  		
	  			String deleteHistory="delete material_code_request_history where REQUEST_NO='"+materialForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  	  		ad.SqlExecuteUpdate(deleteHistory);
	  			String insertHistryQuery="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
	  					",MATERIAL_GROUP_ID,IS_DMF_MATERIAL,DMF_GRADE_ID,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME,TO_BE_USED_IN_PRODUCTS" +
	  					",IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,RETEST_DAYS,RETEST_DAYS_TYPE,DUTY_ELEMENT,PACKING_MATERIAL_GROUP,Type_Of_Material,IS_ARTWORK_REVISION ,EXISTING_SAP_ITEM_CODE" +
	  					" ,APPROXIMATE_VALUE,REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,ARTWORK_NO,UNIT_OF_MEAS_ID,VALUATION_CLASS,PURCHASE_GROUP_ID,Approve_Type,URL,REPORT_URL,Role,HSN_Code)" +
	  					" values('"+materialForm.getRequestNo()+"','"+reqDate+"','"+materialForm.getLocationId()+"','"+materialForm.getStorageLocationId()+"','2','"+materialForm.getMaterialShortName()+"'" +
	  					",'"+materialForm.getMaterialLongName()+"','"+materialForm.getMaterialGroupId()+"','"+materialForm.getIsDMFMaterial()+"','"+materialForm.getDmfGradeId()+"','"+materialForm.getCosGradeNo()+"'" +
	  					",'"+materialForm.getAdditionalTest()+"','"+materialForm.getCountryId()+"','"+materialForm.getCustomerName()+"','"+materialForm.getToBeUsedInProducts()+"','"+materialForm.getIsVendorSpecificMaterial()+"','"+materialForm.getMfgrName()+"','"+materialForm.getSiteOfManufacture()+"','"+materialForm.getTempCondition()+"','"+materialForm.getStorageCondition()+"'" +
	  					",'"+materialForm.getRetestDays()+"','"+materialForm.getRetestType()+"','"+materialForm.getDutyElement()+"','"+materialForm.getPackageMaterialGroup()+"','"+materialForm.getTypeOfMaterial()+"','"+materialForm.getIsArtworkRevision()+"','"+materialForm.getExistingSAPItemCode()+"','"+materialForm.getApproximateValue()+"'" +
	  					",'"+user.getEmployeeNo()+"','"+dateNow+"','"+user.getEmployeeNo()+"','','PM','"+materialForm.getArtworkNo()+"','"+materialForm.getUnitOfMeasId()+"','"+materialForm.getValuationClass()+"','"+materialForm.getPuchaseGroupId()+"','Created','','','user','"+materialForm.getHsnCode()+"') ";	
	  		int j=0;
	  		j=ad.SqlExecuteUpdate(insertHistryQuery);
	  			
	  			
	  		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				saveRecReq = saveRecReq + "'"+materialForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','"+matType+"')";
				int ij=ad.SqlExecuteUpdate(saveRecReq);
				if(!(parllelAppr1.equalsIgnoreCase(""))){
					
				
				 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+materialForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','"+matType+"')";
				 ad.SqlExecuteUpdate(sendRecParllelAppr1);
				}
				if(!(parllelAppr2.equalsIgnoreCase(""))){
					
					 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+materialForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','"+matType+"')";
					 ad.SqlExecuteUpdate(sendRecParllelAppr2);
				}
				
				if(ij>0){
					materialForm.setMessage2("Request No "+materialForm.getRequestNo()+". Submitted for approval successully.");
					String updateStatus="update material_code_request set Approve_Type='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
					ad.SqlExecuteUpdate(updateStatus);
					request.setAttribute("sendMessage", "sendMessage");
					
					EMailer email = new EMailer();
					String approvermail="";
					String reqNo=Integer.toString(materialForm.getRequestNo());
					int		i = email.sendMailToApprover(request, approvermail,reqNo, "Material Code Request");
					return displayNewPackageMaterial(mapping, form, request, response);
					
				}else{
					materialForm.setMessage2("Error while submiting approval...");
				}
	  		}else{
	  			
	  			materialForm.setMessage2("No Approvers are assigned.Please Contact to Admin");
	  		}
				
				
			
			
			}
		 
		 
		 
		 ArrayList list = new ArrayList();
		  int count1=0;
		  String getCount="select count(*)  from material_code_request where request_no='"+materialForm.getRequestNo()+"'";
		  ResultSet rs6=ad.selectQuery(getCount);
		  while(rs6.next())
		  {
			  count1=rs6.getInt(1);
		  }
		  if(count1>=1)
		  {  
		  String getUplodedFiles="select *  from material_code_request where request_no='"+materialForm.getRequestNo()+"'";
			ResultSet rs5 = ad.selectQuery(getUplodedFiles);
				
			while (rs5.next()) {
				PackageMaterialMasterForm custForm1 = new PackageMaterialMasterForm();
						//custForm1.setUrl(url);
						
						custForm1.setFileList(rs5.getString("Attachements"));
						list.add(custForm1);
					}
					request.setAttribute("listName", list);
					
		  }	
		 
			 }
			  else{
				  materialForm.setMessage("Error...Please Upload File");
				  String typeDetails=materialForm.getTypeDetails();
				  materialForm.setTypeDetails(typeDetails);
			  }	 
		 
		 //SAP_CODE_NO='"+materialForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+materialForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+materialForm.getSapCreatedBy()+"',
	
			String status3=materialForm.getTypeOfMaterial();
			if(status3.equalsIgnoreCase("Printed Material")){
				request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
				String artWorkNo=materialForm.getArtworkNo();
				String isArtworkRevision=materialForm.getIsArtworkRevision();
				
				materialForm.setArtworkNo(artWorkNo);
				materialForm.setIsArtworkRevision(isArtworkRevision);
				
				
			}
			else
				request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
			
			
			String status1=materialForm.getIsDMFMaterial();
			if(status1.equalsIgnoreCase("True")){
				request.setAttribute("dmfMandatory", "dmfMandatory");
			String dmfGrade=materialForm.getDmfGradeId();
			String cosGrade=materialForm.getCosGradeNo();
			materialForm.setMfgrName(dmfGrade);
			materialForm.setSiteOfManufacture(cosGrade);
			}
			else
				request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
		 

		 
		 String status=materialForm.getIsVendorSpecificMaterial();
			if(status.equalsIgnoreCase("True")){
				request.setAttribute("vedorMandatory", "vedorMandatory");
				String mfgrName=materialForm.getMfgrName();
				String siteOfManufacture=materialForm.getSiteOfManufacture();
				materialForm.setMfgrName(mfgrName);
				materialForm.setSiteOfManufacture(siteOfManufacture);
			}
			else
				request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
		
			
			  LinkedList tempIDList=new LinkedList();
				LinkedList temValueList=new LinkedList();
				
				String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
				ResultSet rsTemp=ad.selectQuery(getTemp);
				while(rsTemp.next())
				{
					tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
					temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
				}
				materialForm.setTempIDList(tempIDList);
				materialForm.setTemValueList(temValueList);
				

				LinkedList storageIDList=new LinkedList();
				LinkedList storageLocList=new LinkedList();
				
				String getStorageLoc="select * from STORAGE_CONDITION";
				ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
				while(rsStorageLoc.next())
				{
					storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
					storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
				}
				materialForm.setStorageIDList(storageIDList);
				materialForm.setStorageLocList(storageLocList);
			
		 String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			//set Approved Field
			user=(UserInfo)session.getAttribute("user");
			if(userId==1)
			{	
				request.setAttribute("approved", "approved");
			}
		 
		  }catch (Exception e) {
			 
				e.printStackTrace();
			}
		  materialForm.setMaterialCodeLists1("2");
			materialForm.setMaterialTypeId("2");
		return mapping.findForward("newPackageMaterial");
	
	}
	
	public ActionForward copyNewPackageMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  userId=user.getId();
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			materialForm.setTempIDList(tempIDList);
			materialForm.setTemValueList(temValueList);
			

			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			materialForm.setStorageIDList(storageIDList);
			materialForm.setStorageLocList(storageLocList);
			
			
			int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
			
			String getMaterial="select * from material_code_request where REQUEST_NO='"+requstNo+"'";
			ResultSet rs=ad.selectQuery(getMaterial);
			while(rs.next())
			{
				String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				materialForm.setRequestNo(maxReqno);
				materialForm.setRequestDate(EMicroUtils.getCurrentSysDate());
				materialForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				materialForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				materialForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				materialForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				materialForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
				
				String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
				if(isVendorStatus.equalsIgnoreCase("1"))
				{
					materialForm.setIsVendorSpecificMaterial("1");
					request.setAttribute("vedorMandatory", "vedorMandatory");
					materialForm.setMfgrName(rs.getString("MFGR_NAME"));
					materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				}
				if(isVendorStatus.equalsIgnoreCase("0"))
				{
					materialForm.setIsVendorSpecificMaterial("0");
					request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
				}
					materialForm.setMfgrName(rs.getString("MFGR_NAME"));
				materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				materialForm.setTempCondition(rs.getString("TEMP_CONDITION"));
				materialForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
				materialForm.setRetestDays(rs.getString("RETEST_DAYS"));
				materialForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
				
				
				
				materialForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				materialForm.setRequestedBy(rs.getString("REQUESTED_BY"));
	
				if(userId==1)
					{	
					materialForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
				if(userId==2)
				{
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
				materialForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					materialForm.setSapCodeExists("1");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					materialForm.setSapCodeExists("0");
				String createDate=rs.getString("SAP_CREATION_DATE");
				String a1[]=createDate.split(" ");
				createDate=a1[0];
				String b1[]=createDate.split("-");
				createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				materialForm.setSapCreationDate(createDate);
				materialForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				
			
					}
					materialForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
					request.setAttribute("sapApprover", "sapApprover");
				}
			}
			
			///MAIL ID OF USER
			String mail="";
			String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
			ResultSet rss=ad.selectQuery(a);
			try {
				if(rss.next())
				{
				mail=rss.getString("EMAIL_ID")	;
				if(!mail.equalsIgnoreCase("null"))
					materialForm.setReqEmail(mail);
			
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
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		materialForm.setTypeDetails("Update");
		materialForm.setMaterialCodeLists("PACKING MATERIALS");
		String forwardType="newPackageMaterial";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="PackageMaterialSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newPackageMaterial";
		}
		materialForm.setTypeDetails("Save");
		materialForm.setMaterialTypeId("2");
		materialForm.setMaterialCodeLists1("2");
		return mapping.findForward(forwardType);
	}
	public ActionForward saveSAPData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  userId=user.getId();
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			
			
			
		
			
			String getMaterial="select * from material_code_request where REQUEST_NO='"+materialForm.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getMaterial);
			while(rs.next())
			{
				materialForm.setRequestNo(materialForm.getRequestNo());
				String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				materialForm.setRequestDate(reqDate);
				materialForm.setLocationId(rs.getString("LOCATION_ID"));
				materialForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				materialForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				materialForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				materialForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				materialForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				String isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
				if(isDMFMaterial.equalsIgnoreCase("1"))
				{
					materialForm.setIsDMFMaterial("True");
					request.setAttribute("dmfMandatory", "dmfMandatory");
					materialForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
					materialForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
				}
				if(isDMFMaterial.equalsIgnoreCase("0")){
					materialForm.setIsDMFMaterial("False");
					request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
				}
				materialForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
				
				materialForm.setCountryId(rs.getString("COUNTRY_ID"));
				materialForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				materialForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
				String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
				if(isVendorStatus.equalsIgnoreCase("1"))
				{
					materialForm.setIsVendorSpecificMaterial("True");
					request.setAttribute("vedorMandatory", "vedorMandatory");
					materialForm.setMfgrName(rs.getString("MFGR_NAME"));
					materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				}
				if(isVendorStatus.equalsIgnoreCase("0"))
				{
					materialForm.setIsVendorSpecificMaterial("False");
					request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
				}
					materialForm.setMfgrName(rs.getString("MFGR_NAME"));
				materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				materialForm.setTempCondition(rs.getString("TEMP_CONDITION"));
				materialForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
				materialForm.setRetestDays(rs.getString("RETEST_DAYS"));
				materialForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
				String dutyElement=rs.getString("DUTY_ELEMENT");
				if(dutyElement.equalsIgnoreCase("1"))
				{
					materialForm.setDutyElement("True");
				}
				if(dutyElement.equalsIgnoreCase("0"))
					materialForm.setDutyElement("False");
				materialForm.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP"));
				materialForm.setTypeOfMaterial(rs.getString("Type_Of_Material"));
				String typeOfMaterial=rs.getString("Type_Of_Material");
				
				if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
				{
					request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
					materialForm.setArtworkNo(rs.getString("ARTWORK_NO"));
					String isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
					if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
					{
						materialForm.setIsArtworkRevision("True");
					}
					if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
						materialForm.setIsArtworkRevision("False");
					
				}
				else
					request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
				
				materialForm.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
				materialForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				materialForm.setRequestedBy(rs.getString("REQUESTED_BY"));
				ArrayList fileList = new ArrayList();
				String uploadedFiles=rs.getString("Attachements");
				materialForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				materialForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				materialForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
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
				fileList.add(materialForm2);
				}
				request.setAttribute("listName", fileList);
				}
	
				
				if(userId==1)
					{	
					//materialForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
				if(userId==2)
				{
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
				materialForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					materialForm.setSapCodeExists("True");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					materialForm.setSapCodeExists("False");
				String createDate=rs.getString("SAP_CREATION_DATE");
				String a1[]=createDate.split(" ");
				createDate=a1[0];
				String b1[]=createDate.split("-");
				createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				materialForm.setSapCreationDate(createDate);
				materialForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				
			
					}
					materialForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
					request.setAttribute("sapApprover", "sapApprover");
				}
			}
			int i=0;
			if(userId==2)
			{
			 String sapCreationDate=materialForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
				sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
				String updateMaterial="update material_code_request set SAP_CODE_NO='"+materialForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+materialForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+materialForm.getSapCreatedBy()+"' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
				i=ad.SqlExecuteUpdate(updateMaterial);
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
			}
			if(i>0)
			{
				materialForm.setMessage2("Code creation request updated with request number='"+materialForm.getRequestNo()+"'.");
				materialForm.setTypeDetails("Update");
			}else{
				materialForm.setMessage("Error...When modifying package material.Please Check....");
				materialForm.setTypeDetails("Update");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		materialForm.setTypeDetails("Update");
		materialForm.setMaterialCodeLists1("2");
		
		return mapping.findForward("approverMaster");
	}
	
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		int  userId=user.getId();
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_DESC"));
			}
			materialForm.setTempIDList(tempIDList);
			materialForm.setTemValueList(temValueList);
			

			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			materialForm.setStorageIDList(storageIDList);
			materialForm.setStorageLocList(storageLocList);
		
			
			String getMaterial="select * from material_code_request where REQUEST_NO='"+materialForm.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getMaterial);
			while(rs.next())
			{
				materialForm.setRequestNo(materialForm.getRequestNo());
				String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				materialForm.setRequestDate(reqDate);
				materialForm.setLocationId(rs.getString("LOCATION_ID"));
				materialForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				materialForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				materialForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				materialForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				materialForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				String isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
				if(isDMFMaterial.equalsIgnoreCase("1"))
				{
					materialForm.setIsDMFMaterial("True");
					request.setAttribute("dmfMandatory", "dmfMandatory");
					materialForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
					materialForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
				}
				if(isDMFMaterial.equalsIgnoreCase("0")){
					materialForm.setIsDMFMaterial("False");
					request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
				}
				materialForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
				
				materialForm.setCountryId(rs.getString("COUNTRY_ID"));
				materialForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				materialForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
				String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
				if(isVendorStatus.equalsIgnoreCase("1"))
				{
					materialForm.setIsVendorSpecificMaterial("True");
					request.setAttribute("vedorMandatory", "vedorMandatory");
					materialForm.setMfgrName(rs.getString("MFGR_NAME"));
					materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				}
				if(isVendorStatus.equalsIgnoreCase("0"))
				{
					materialForm.setIsVendorSpecificMaterial("False");
					request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
				}
					materialForm.setMfgrName(rs.getString("MFGR_NAME"));
				materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				materialForm.setTempCondition(rs.getString("TEMP_CONDITION"));
				materialForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
				materialForm.setRetestDays(rs.getString("RETEST_DAYS"));
				materialForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
				String dutyElement=rs.getString("DUTY_ELEMENT");
				if(dutyElement.equalsIgnoreCase("1"))
				{
					materialForm.setDutyElement("True");
				}
				if(dutyElement.equalsIgnoreCase("0"))
					materialForm.setDutyElement("False");
				materialForm.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP"));
				materialForm.setTypeOfMaterial(rs.getString("Type_Of_Material"));
				String typeOfMaterial=rs.getString("Type_Of_Material");
				
				if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
				{
					request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
					materialForm.setArtworkNo(rs.getString("ARTWORK_NO"));
					String isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
					if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
					{
						materialForm.setIsArtworkRevision("True");
					}
					if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
						materialForm.setIsArtworkRevision("False");
					
				}
				else
					request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
				
				materialForm.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
				materialForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				materialForm.setRequestedBy(rs.getString("REQUESTED_BY"));
				ArrayList fileList = new ArrayList();
				String uploadedFiles=rs.getString("Attachements");
				materialForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				materialForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				materialForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
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
				fileList.add(materialForm2);
				}
				request.setAttribute("listName", fileList);
				}
	
				
				if(userId==1)
					{	
					//materialForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
				if(userId==2)
				{
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
				materialForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					materialForm.setSapCodeExists("True");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					materialForm.setSapCodeExists("False");
				String createDate=rs.getString("SAP_CREATION_DATE");
				String a1[]=createDate.split(" ");
				createDate=a1[0];
				String b1[]=createDate.split("-");
				createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				materialForm.setSapCreationDate(createDate);
				materialForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				
			
					}
					materialForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
					request.setAttribute("sapApprover", "sapApprover");
				}
			}
			int i=0;
			if(userId==1)
			{
				String currentdate=EMicroUtils.getCurrentSysDate();
				  String a[]=currentdate.split("/");
				  for(int j=0;j<a.length;j++)
				  {
					  System.out.println("a="+a[j]);
				  }
				  currentdate=a[2]+"-"+a[1]+"-"+a[0];
				String updateMaterial="update material_code_request set Approve_Type='"+materialForm.getApproveType()+"',approve_date='"+currentdate+"',last_approver='"+user.getFullName()+"',pending_approver='No'  where REQUEST_NO='"+materialForm.getRequestNo()+"'";
				i=ad.SqlExecuteUpdate(updateMaterial);
				if(i>0)
				{
					materialForm.setMessage2("Code creation request updated with request number='"+materialForm.getRequestNo()+"'.");
					materialForm.setTypeDetails("Update");
				}else{
					materialForm.setMessage("Error...When modifying package material.Please Check....");
					materialForm.setTypeDetails("Update");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		materialForm.setTypeDetails("Update");
		materialForm.setMaterialCodeLists1("2");
		
		return mapping.findForward("approverMaster");
	}
	
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
			EssDao ad=new EssDao();
			
			 String path="packing_Material.jrxml";
			    String f2="packing_mat1.jrxml";
		        String output="raw_m1.pdf";
			 	
			    HttpSession session=request.getSession();
			    
				String pat3=getServlet().getServletContext().getRealPath("/jasperReports/"+path);
				String f_path2=getServlet().getServletContext().getRealPath("/jasperReports/"+f2);
				String pat_out=getServlet().getServletContext().getRealPath("/jasperReports/"+output);
				System.out.println("path of the JRXML File is *********************"+path);
				System.out.println("pat3 of the JRXML File is *********************"+pat3);
				try {
				String path2="pack_m.jasper";
				//String path2="MarksCard.jasper";
				String path1=getServlet().getServletContext().getRealPath("/jasperReports/"+path2);
				System.out.println("Path of the JRXML File is *********************"+path1);
				File reportFile=new File(path1);
				Map parameters = new HashMap();
				
				InputStream input = new FileInputStream(new File(pat3));
				InputStream input2 = new FileInputStream(new File(f_path2));
				
				JasperDesign design = JRXmlLoader.load(input);
				JasperDesign design2 = JRXmlLoader.load(input2);
				Map param_map = new HashMap();
				Map param_map2 = new HashMap();
				int requestNo=Integer.parseInt(request.getParameter("requstNo"));
				
				
				
				param_map.put("REQUEST_NO", requestNo);
				param_map.put("PHARMACOP_GRADE", "Rajesh");
				
				
				JasperReport jReport = JasperCompileManager.compileReport(design);
				
				JasperReport jReport2 = JasperCompileManager.compileReport(design2);
				
				
				
				Connection connection=ConnectionFactory.getConnection();
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(jReport, param_map, connection);
				
				JasperPrint jasperPrint2 = JasperFillManager.fillReport(jReport2, param_map, connection);
				
				 List<JasperPrint> jlist=new ArrayList<JasperPrint>();
				 jlist.add(jasperPrint);
				 jlist.add(jasperPrint2);
				
				//Connection con=ConnectionFactory.getConnection();
				
				
				
				session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
				
				 if(jasperPrint!=null)
				  { 
					 //JRHtmlExporterParameter.BETWEEN_PAGES_HTML;
					 FileOutputStream os = new FileOutputStream(pat_out);
					 //JasperExportManager.exportReportToPdfFile(jasperPrint, pat_out);
					 JRPdfExporter exporter = new JRPdfExporter();
					 exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jlist);
					 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
					 //JasperExportManager.exportReportToHtml(jasperPrint,"yy.html");
					   //JasperExportManager.exportReportToPdfFile(jasperPrint,path4 + "/"+"db_stats.pdf"); 
					   // int t=pdfasbytes.length;
					   // System.out.println("Length===="+t);
					 exporter.exportReport();
					 
					 OutputStream servletOutputStream = null;
					 ByteArrayOutputStream htmlStream =new ByteArrayOutputStream();
					 byte[] report=htmlStream.toByteArray();
					 
					 
					 response.reset();
					 //response.setContentType("application/html");
						response.setContentType("application/pdf");
					// response.setHeader("Content-disposition","inline=" + "jasperReportName" + ".html");
					// servletOutputStream = response.getOutputStream();
					 //servletOutputStream.write(report);
					// response.setContentLength(report.length);
					 
					 		int length=0;
							StringBuffer buf=new StringBuffer();
							ServletOutputStream outstream=response.getOutputStream();
							response.setHeader("Content-disposition", "attachment; filename="+pat_out+"");
							//response.setContentLength(pdfasbytes.length);
							FileInputStream input1 = new FileInputStream(pat_out);
				                ServletOutputStream sos = response.getOutputStream();   
				                byte[] bbuf = new byte[4096];   

				              DataInputStream in = new DataInputStream(input1); 
				                
				               while ((in != null) && ((length = in.read(bbuf)) != -1))   
				               {   
				                    sos.write(bbuf,0,length);   
				               }   
				                
				                
				              in.close();   
				                sos.flush();   
						
							//response.setContentType("application/pdf");
							//outstream.write(pdfasbytes);
							outstream.close();
				  } 
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
			    
			
			
		return mapping.findForward("");	
		}
	
	
	public ActionForward serchRequiredData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		String requirdData=materialForm.getSearchData();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
		materialForm.setLocationIdList(locationList);
		materialForm.setLocationLabelList(locationLabelList);
		
		
		LinkedList rawRecordList=new LinkedList();
		String getRecord="";
		
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO like '"+requirdData+"'  and m.Type='Package Material'";
		
		ResultSet rs=ad.selectQuery(getRecord);
		while(rs.next())
		{
			PackageMaterialMasterForm rawRecord=new PackageMaterialMasterForm();
			
			rawRecord.setLocationId(rs.getString("Location_Name"));
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String b[]=requestDate.split("-");
			requestDate=b[2]+"/"+b[1]+"/"+b[0];
			rawRecord.setRequestDate(requestDate);
			rawRecord.setRequestNo(rs.getInt("REQUEST_NO"));
			
			
			rawRecordList.add(rawRecord);
		}
		request.setAttribute("listOfPackages", rawRecordList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("packageMaterialList");
		
		
	}
	
	public ActionForward getMaterialStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			materialForm.setTempIDList(tempIDList);
			materialForm.setTemValueList(temValueList);
			

			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			materialForm.setStorageIDList(storageIDList);
			materialForm.setStorageLocList(storageLocList);
			
			
			String status=materialForm.getTypeOfMaterial();
			if(status.equalsIgnoreCase("Printed Material")){
				request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
				
			}
			else
				request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
			
			
			
			
			
			String status2=materialForm.getIsDMFMaterial();
			if(status2!=null){
			if(status2.equalsIgnoreCase("True")){
				request.setAttribute("dmfMandatory", "dmfMandatory");
			String dmfGrade=materialForm.getDmfGradeId();
			String cosGrade=materialForm.getCosGradeNo();
			materialForm.setMfgrName(dmfGrade);
			materialForm.setSiteOfManufacture(cosGrade);
			}
			else
				request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
			}
			String status1=materialForm.getIsVendorSpecificMaterial();
			if(status1!=null)
			{
			if(status1.equalsIgnoreCase("True")){
				request.setAttribute("vedorMandatory", "vedorMandatory");
			String mfgrName=materialForm.getMfgrName();
			String siteOfManufacture=materialForm.getSiteOfManufacture();
			materialForm.setMfgrName(mfgrName);
			materialForm.setSiteOfManufacture(siteOfManufacture);
				
			}
			else
				request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		///MAIL ID OF USER
		String mail="";
		String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
		ResultSet rss=ad.selectQuery(a);
		try {
			if(rss.next())
			{
			mail=rss.getString("EMAIL_ID")	;
			if(!mail.equalsIgnoreCase("null"))
				materialForm.setReqEmail(mail);
		
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
		
		String typeDetails=materialForm.getTypeDetails();
		materialForm.setTypeDetails(typeDetails);
		materialForm.setMaterialCodeLists1("2");
		return mapping.findForward("newPackageMaterial");
	}
	
	
	public ActionForward getDMFStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%'  order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			materialForm.setTempIDList(tempIDList);
			materialForm.setTemValueList(temValueList);
			

			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			materialForm.setStorageIDList(storageIDList);
			materialForm.setStorageLocList(storageLocList);
			
			String status=request.getParameter("status");
			if(status.equalsIgnoreCase("1")){
				request.setAttribute("dmfMandatory", "dmfMandatory");
			String dmfGrade=materialForm.getDmfGradeId();
			String cosGrade=materialForm.getCosGradeNo();
			materialForm.setMfgrName(dmfGrade);
			materialForm.setSiteOfManufacture(cosGrade);
			}
			else
				request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
			
			String status1=materialForm.getIsVendorSpecificMaterial();
			if(status1!=null)
			{
			if(status1.equalsIgnoreCase("1")){
				request.setAttribute("vedorMandatory", "vedorMandatory");
			String mfgrName=materialForm.getMfgrName();
			String siteOfManufacture=materialForm.getSiteOfManufacture();
			materialForm.setMfgrName(mfgrName);
			materialForm.setSiteOfManufacture(siteOfManufacture);
				
			}
			else
				request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
			}
			
			String status3=materialForm.getTypeOfMaterial();
			if(status3!=null){
			if(status3.equalsIgnoreCase("Printed Material")){
				request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
				String artworkNo=materialForm.getArtworkNo();
				String isArtwork=materialForm.getIsArtworkRevision();
				
				materialForm.setArtworkNo(artworkNo);
				materialForm.setIsArtworkRevision(isArtwork);
				
			}
			else
				request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");	
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		String typeDetails=materialForm.getTypeDetails();
		materialForm.setTypeDetails(typeDetails);
		materialForm.setMaterialCodeLists1("2");
		
		///MAIL ID OF USER
				String mail="";
				String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
				ResultSet rss=ad.selectQuery(a);
				try {
					if(rss.next())
					{
					mail=rss.getString("EMAIL_ID")	;
					if(!mail.equalsIgnoreCase("null"))
						materialForm.setReqEmail(mail);
				
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
		
		
		
		return mapping.findForward("newPackageMaterial");
	}
	
	
	public ActionForward getVendorStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			materialForm.setTempIDList(tempIDList);
			materialForm.setTemValueList(temValueList);
			

			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			materialForm.setStorageIDList(storageIDList);
			materialForm.setStorageLocList(storageLocList);
			
			String status1=materialForm.getIsDMFMaterial();
			if(status1.equalsIgnoreCase("1")){
				request.setAttribute("dmfMandatory", "dmfMandatory");
			String dmfGrade=materialForm.getDmfGradeId();
			String cosGrade=materialForm.getCosGradeNo();
			materialForm.setDmfGradeId(dmfGrade);
			materialForm.setCosGradeNo(cosGrade);
			}
			else
				request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
			
			String status=request.getParameter("status");
			if(status.equalsIgnoreCase("1")){
				request.setAttribute("vedorMandatory", "vedorMandatory");
			/*String mfgrName=materialForm.getMfgrName();
			String siteOfManufacture=materialForm.getSiteOfManufacture();
			materialForm.setMfgrName(mfgrName);
			materialForm.setSiteOfManufacture(siteOfManufacture);*/
				
			}
			else
				request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
			String status3=materialForm.getTypeOfMaterial();
			if(status3!=null){
			if(status3.equalsIgnoreCase("Printed Material")){
				request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
				String artworkNo=materialForm.getArtworkNo();
				String isArtwork=materialForm.getIsArtworkRevision();
				
				materialForm.setArtworkNo(artworkNo);
				materialForm.setIsArtworkRevision(isArtwork);
				
			}
			else
				request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");	
			
			}	
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		String typeDetails=materialForm.getTypeDetails();
		materialForm.setTypeDetails(typeDetails);
		materialForm.setMaterialCodeLists1("2");
		
		///MAIL ID OF USER
		String mail="";
		String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
		ResultSet rss=ad.selectQuery(a);
		try {
			if(rss.next())
			{
			mail=rss.getString("EMAIL_ID")	;
			if(!mail.equalsIgnoreCase("null"))
				materialForm.setReqEmail(mail);
		
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
		
		
		return mapping.findForward("newPackageMaterial");
	}
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requestNumber=materialForm.getRequestNumber();
		if(requestNumber.equalsIgnoreCase("Request Number"))
		{
			requestNumber="";
		}
		String locationId=materialForm.getLocationSearch();
		String reqDate=materialForm.getRequestsearchDate();
		if(reqDate.equalsIgnoreCase("")){
		}else{
		String a[]=reqDate.split("/");
		reqDate=a[2]+"-"+a[1]+"-"+a[0];
		}
		
		try{
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
		materialForm.setLocationIdList(locationList);
		materialForm.setLocationLabelList(locationLabelList);
		
		
		LinkedList rawRecordList=new LinkedList();
		String getRecord="";
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Package Material'";
		
		}else{
		if(reqDate.equalsIgnoreCase("")&&locationId.equalsIgnoreCase("") ){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"'  and l.LOCID=m.LOCATION_ID and m.Type='Package Material'";
		}
		 if(reqDate.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Package Material'";
		}
		 if(locationId.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.Location_ID=l.LOCID and m.Type='Package Material'";
		}
		 if(requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&&!locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Package Material'";
		}
		 if(reqDate.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Package Material'";
		}
		if(locationId.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !reqDate.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.REQUEST_DATE='"+reqDate+"' and l.LOCID=m.LOCATION_ID and m.Type='Package Material'";
		}
		
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Package Material'";
		
		}
		}
		ResultSet rs=ad.selectQuery(getRecord);
		while(rs.next())
		{
			PackageMaterialMasterForm rawRecord=new PackageMaterialMasterForm();
			
			rawRecord.setLocationId(rs.getString("LOCNAME"));
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String b[]=requestDate.split("-");
			requestDate=b[2]+"/"+b[1]+"/"+b[0];
			rawRecord.setRequestDate(requestDate);
			rawRecord.setRequestNo(rs.getInt("REQUEST_NO"));
			
			
			rawRecordList.add(rawRecord);
		}
		request.setAttribute("listOfPackages", rawRecordList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("packageMaterialList");
		
		
	}
	
	public ActionForward editPackageMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  userId=user.getId();
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			materialForm.setTempIDList(tempIDList);
			materialForm.setTemValueList(temValueList);
			

			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			materialForm.setStorageIDList(storageIDList);
			materialForm.setStorageLocList(storageLocList);
			
			
			int requstNo=Integer.parseInt(request.getParameter("requstNo"));
			
			String getMaterial="select * from material_code_request where REQUEST_NO='"+requstNo+"'";
			ResultSet rs=ad.selectQuery(getMaterial);
			while(rs.next())
			{
				materialForm.setRequestNo(requstNo);
				String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				materialForm.setRequestDate(reqDate);
				materialForm.setLocationId(rs.getString("LOCATION_ID"));
				materialForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				materialForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				materialForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				materialForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				materialForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				String isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
				if(isDMFMaterial.equalsIgnoreCase("1"))
				{
					materialForm.setIsDMFMaterial("1");
					request.setAttribute("dmfMandatory", "dmfMandatory");
					materialForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
					materialForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
				}
				if(isDMFMaterial.equalsIgnoreCase("0")){
					materialForm.setIsDMFMaterial("0");
					request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
				}
				materialForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
				
				materialForm.setCountryId(rs.getString("COUNTRY_ID"));
				materialForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				materialForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
				String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
				if(isVendorStatus.equalsIgnoreCase("1"))
				{
					materialForm.setIsVendorSpecificMaterial("1");
					request.setAttribute("vedorMandatory", "vedorMandatory");
					materialForm.setMfgrName(rs.getString("MFGR_NAME"));
					materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				}
				if(isVendorStatus.equalsIgnoreCase("0"))
				{
					materialForm.setIsVendorSpecificMaterial("0");
					request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
				}
					materialForm.setMfgrName(rs.getString("MFGR_NAME"));
				materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				materialForm.setTempCondition(rs.getString("TEMP_CONDITION"));
				materialForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
				materialForm.setRetestDays(rs.getString("RETEST_DAYS"));
				materialForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
				String dutyElement=rs.getString("DUTY_ELEMENT");
				if(dutyElement.equalsIgnoreCase("0"))
				{
					materialForm.setDutyElement("0");
				}
				if(dutyElement.equalsIgnoreCase("1"))
					materialForm.setDutyElement("1");
				materialForm.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP"));
				materialForm.setTypeOfMaterial(rs.getString("Type_Of_Material"));
				String typeOfMaterial=rs.getString("Type_Of_Material");
				
				if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
				{
					request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
					materialForm.setArtworkNo(rs.getString("ARTWORK_NO"));
					String isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
					if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
					{
						materialForm.setIsArtworkRevision("1");
					}
					if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
						materialForm.setIsArtworkRevision("0");
					
				}
				else
					request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
				
				materialForm.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
				materialForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				materialForm.setRequestedBy(rs.getString("REQUESTED_BY"));
				ArrayList fileList = new ArrayList();
				String uploadedFiles=rs.getString("Attachements");
				materialForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				materialForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				materialForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
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
	
				
				if(userId==1)
					{	
					materialForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
				if(userId==2)
				{
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
				materialForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					materialForm.setSapCodeExists("True");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					materialForm.setSapCodeExists("False");
				String createDate=rs.getString("SAP_CREATION_DATE");
				String a1[]=createDate.split(" ");
				createDate=a1[0];
				String b1[]=createDate.split("-");
				createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				materialForm.setSapCreationDate(createDate);
				materialForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				
			
					}
					materialForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
					request.setAttribute("sapApprover", "sapApprover");
				}
			}
			
			///MAIL ID OF USER
			String mail="";
			String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
			ResultSet rss=ad.selectQuery(a);
			try {
				if(rss.next())
				{
				mail=rss.getString("EMAIL_ID")	;
				if(!mail.equalsIgnoreCase("null"))
					materialForm.setReqEmail(mail);
			
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

		}catch (Exception e) {
			e.printStackTrace();
		}
		materialForm.setTypeDetails("Update");
		materialForm.setMaterialCodeLists("PACKING MATERIALS");
		String forwardType="newPackageMaterial";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="PackageMaterialSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newPackageMaterial";
		}
		materialForm.setMaterialTypeId("2");
		materialForm.setMaterialCodeLists1("2");
		return mapping.findForward(forwardType);
	}
	
	public ActionForward deletePackageMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		PackageMaterialMasterForm masterForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		try{
		int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
		   String deleteRecord="delete from material_code_request where REQUEST_NO='"+requstNo+"'";
		   int i=0;
		   i=ad.SqlExecuteUpdate(deleteRecord);
		   if(i>=1)
		   {
			   masterForm.setMessage("Package Material Has Been Successfully Deleted");
			   
			  
			   
		   }else{
			   masterForm.setMessage("Error.... When Deleting Package Material.");
		   }
		}catch (Exception e) {
			e.printStackTrace();
		}
		   displayPackageMaterialList(mapping, form, request, response);
	return mapping.findForward("packageMaterialList");
	}
	public ActionForward savePackageMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String maild=request.getParameter("EMAIL");
		maild=maild.replace("*", "&");

		//insert emial id
		if(!maild.equalsIgnoreCase("null")||!maild.equalsIgnoreCase(""))
		{
		String mail="Update emp_official_info set EMAIL_ID='"+maild+"' where pernr='"+user.getEmployeeNo()+"'";
		ad.SqlExecuteUpdate(mail);
		}
		int userId=user.getId();
		  String userTextId=Integer.toString(userId);
		Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		  String dateNow = formatter.format(currentDate.getTime());
		  String reqDate=materialForm.getRequestDate();
			 
		  String a[]=reqDate.split("/");
		  
		 
		  reqDate=a[2]+"-"+a[1]+"-"+a[0];
		  
		  
		  String fileList="";
		  try{
			  
			  int fileCount=0;
			  
			String getFileCount="select * from  PacakageMaterial_Doc where  request_no='"+materialForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rsFileCount=ad.selectQuery(getFileCount);
			while(rsFileCount.next())
			{
				fileCount=1;
			}
	
			
			if(fileCount>0){
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			 	String filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles";
			 	
			 	
				
				
				String getUploadedFiles="select * from PacakageMaterial_Doc where request_no='"+materialForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"'";
				ResultSet rs=ad.selectQuery(getUploadedFiles);
				while(rs.next())
				{
					fileList+=filePath+"/"+rs.getString("file_name")+",";
				}
		  int count=0;
		  String typeDetails=materialForm.getTypeDetails();
		
		 if(typeDetails.equalsIgnoreCase("Save"))
		 {
			 
			 String getcount="select count(*) from material_code_request where REQUEST_NO='"+materialForm.getRequestNo()+"'";
				ResultSet rs1=ad.selectQuery(getcount);
				while(rs1.next())
				{
					count=rs1.getInt(1);
				}
				String insertQuery="";
				String approve="Pending";
				//added for approval task
				String approver="";
				String lApprover="";
				String approvermail="";
				String pendingApprovers="";
				
				String getApproverID="select * from Approvers_Details where Type='Material Code Request'";
				
				ResultSet approverRS=ad.selectQuery(getApproverID);
				while(approverRS.next()){
						int priority = approverRS.getInt("Priority");
						if(priority == 1){
							approvermail = approverRS.getString("emailID");
						}
						if(pendingApprovers.equalsIgnoreCase("")){
						pendingApprovers = approverRS.getString("Employee_Name");
						}
						else{
							pendingApprovers = pendingApprovers + " ," +approverRS.getString("Employee_Name");
						}
						
				}
				
				int i=0;
			if(count>0){	
				//RequestNo is available in database
				
				int old_RequestNo=materialForm.getRequestNo();
				String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				materialForm.setRequestNo(maxReqno);
			 
			 String url="packageMaterial.do?method=editPackageMaterialRecord";
			 String reportURL="packageMaterial.do?method=getReport";
insertQuery="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
			",MATERIAL_GROUP_ID,IS_DMF_MATERIAL,DMF_GRADE_ID,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME,TO_BE_USED_IN_PRODUCTS" +
			",IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,RETEST_DAYS,RETEST_DAYS_TYPE,DUTY_ELEMENT,PACKING_MATERIAL_GROUP,Type_Of_Material,IS_ARTWORK_REVISION ,EXISTING_SAP_ITEM_CODE" +
			" ,APPROXIMATE_VALUE,REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,ARTWORK_NO,UNIT_OF_MEAS_ID,VALUATION_CLASS,PURCHASE_GROUP_ID,Approve_Type,URL,REPORT_URL,last_approver,pending_approver,Storage,HSN_Code)" +
			" values('"+materialForm.getRequestNo()+"','"+reqDate+"','"+materialForm.getLocationId()+"','"+materialForm.getStorageLocationId()+"','2','"+materialForm.getMaterialShortName()+"'" +
			",'"+materialForm.getMaterialLongName()+"','"+materialForm.getMaterialGroupId()+"','"+materialForm.getIsDMFMaterial()+"','"+materialForm.getDmfGradeId()+"','"+materialForm.getCosGradeNo()+"'" +
			",'"+materialForm.getAdditionalTest()+"','"+materialForm.getCountryId()+"','"+materialForm.getCustomerName()+"','"+materialForm.getToBeUsedInProducts()+"','"+materialForm.getIsVendorSpecificMaterial()+"','"+materialForm.getMfgrName()+"','"+materialForm.getSiteOfManufacture()+"','"+materialForm.getTempCondition()+"','"+materialForm.getStorageCondition()+"'" +
			",'"+materialForm.getRetestDays()+"','"+materialForm.getRetestType()+"','"+materialForm.getDutyElement()+"','"+materialForm.getPackageMaterialGroup()+"','"+materialForm.getTypeOfMaterial()+"','"+materialForm.getIsArtworkRevision()+"','"+materialForm.getExistingSAPItemCode()+"','"+materialForm.getApproximateValue()+"'" +
			",'"+materialForm.getRequestedBy()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+fileList+"','PM','"+materialForm.getArtworkNo()+"','"+materialForm.getUnitOfMeasId()+"','"+materialForm.getValuationClass()+"','"+materialForm.getPuchaseGroupId()+"','Created','"+url+"','"+reportURL+"','No','"+pendingApprovers+"','"+materialForm.getStorage()+"','"+materialForm.getHsnCode()+"') ";	
			i=ad.SqlExecuteUpdate(insertQuery);
				int j=0;
			j=ad.SqlExecuteUpdate("update PacakageMaterial_Doc set request_no='"+materialForm.getRequestNo()+"' where request_no='"+old_RequestNo+"' and  userId='"+userId+"'");
			
			if(i>0)
			{
				materialForm.setMessage2("Code creation request Created with request number='"+materialForm.getRequestNo()+"'.");
				materialForm.setTypeDetails("Update");
				
				String deleteHistory="delete material_code_request_history where REQUEST_NO='"+materialForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  	  		ad.SqlExecuteUpdate(deleteHistory);
	  			String insertHistryQuery="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
	  					",MATERIAL_GROUP_ID,IS_DMF_MATERIAL,DMF_GRADE_ID,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME,TO_BE_USED_IN_PRODUCTS" +
	  					",IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,RETEST_DAYS,RETEST_DAYS_TYPE,DUTY_ELEMENT,PACKING_MATERIAL_GROUP,Type_Of_Material,IS_ARTWORK_REVISION ,EXISTING_SAP_ITEM_CODE" +
	  					" ,APPROXIMATE_VALUE,REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,ARTWORK_NO,UNIT_OF_MEAS_ID,VALUATION_CLASS,PURCHASE_GROUP_ID,Approve_Type,URL,REPORT_URL,Role,HSN_Code)" +
	  					" values('"+materialForm.getRequestNo()+"','"+reqDate+"','"+materialForm.getLocationId()+"','"+materialForm.getStorageLocationId()+"','2','"+materialForm.getMaterialShortName()+"'" +
	  					",'"+materialForm.getMaterialLongName()+"','"+materialForm.getMaterialGroupId()+"','"+materialForm.getIsDMFMaterial()+"','"+materialForm.getDmfGradeId()+"','"+materialForm.getCosGradeNo()+"'" +
	  					",'"+materialForm.getAdditionalTest()+"','"+materialForm.getCountryId()+"','"+materialForm.getCustomerName()+"','"+materialForm.getToBeUsedInProducts()+"','"+materialForm.getIsVendorSpecificMaterial()+"','"+materialForm.getMfgrName()+"','"+materialForm.getSiteOfManufacture()+"','"+materialForm.getTempCondition()+"','"+materialForm.getStorageCondition()+"'" +
	  					",'"+materialForm.getRetestDays()+"','"+materialForm.getRetestType()+"','"+materialForm.getDutyElement()+"','"+materialForm.getPackageMaterialGroup()+"','"+materialForm.getTypeOfMaterial()+"','"+materialForm.getIsArtworkRevision()+"','"+materialForm.getExistingSAPItemCode()+"','"+materialForm.getApproximateValue()+"'" +
	  					",'"+user.getEmployeeNo()+"','"+dateNow+"','"+user.getEmployeeNo()+"','','PM','"+materialForm.getArtworkNo()+"','"+materialForm.getUnitOfMeasId()+"','"+materialForm.getValuationClass()+"','"+materialForm.getPuchaseGroupId()+"','Created','','','user','"+materialForm.getHsnCode()+"') ";	
	  		ad.SqlExecuteUpdate(insertHistryQuery);
				
				return displayNewPackageMaterial(mapping, form, request, response);
				
			}else{
				materialForm.setMessage("Error...When Saving  Material.Please Check....");
				materialForm.setTypeDetails("Save");
			}
			
			
			
			
			}
			else{
				//Request no is not available in database
				 String url="packageMaterial.do?method=editPackageMaterialRecord";
				 String reportURL="packageMaterial.do?method=getReport";
				 insertQuery="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
				",MATERIAL_GROUP_ID,IS_DMF_MATERIAL,DMF_GRADE_ID,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME,TO_BE_USED_IN_PRODUCTS" +
				",IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,RETEST_DAYS,RETEST_DAYS_TYPE,DUTY_ELEMENT,PACKING_MATERIAL_GROUP,Type_Of_Material,IS_ARTWORK_REVISION ,EXISTING_SAP_ITEM_CODE" +
				" ,APPROXIMATE_VALUE,REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,ARTWORK_NO,UNIT_OF_MEAS_ID,VALUATION_CLASS,PURCHASE_GROUP_ID,Approve_Type,URL,REPORT_URL,last_approver,pending_approver,Storage,HSN_Code)" +
				" values('"+materialForm.getRequestNo()+"','"+reqDate+"','"+materialForm.getLocationId()+"','"+materialForm.getStorageLocationId()+"','2','"+materialForm.getMaterialShortName()+"'" +
				",'"+materialForm.getMaterialLongName()+"','"+materialForm.getMaterialGroupId()+"','"+materialForm.getIsDMFMaterial()+"','"+materialForm.getDmfGradeId()+"','"+materialForm.getCosGradeNo()+"'" +
				",'"+materialForm.getAdditionalTest()+"','"+materialForm.getCountryId()+"','"+materialForm.getCustomerName()+"','"+materialForm.getToBeUsedInProducts()+"','"+materialForm.getIsVendorSpecificMaterial()+"','"+materialForm.getMfgrName()+"','"+materialForm.getSiteOfManufacture()+"','"+materialForm.getTempCondition()+"','"+materialForm.getStorageCondition()+"'" +
				",'"+materialForm.getRetestDays()+"','"+materialForm.getRetestType()+"','"+materialForm.getDutyElement()+"','"+materialForm.getPackageMaterialGroup()+"','"+materialForm.getTypeOfMaterial()+"','"+materialForm.getIsArtworkRevision()+"','"+materialForm.getExistingSAPItemCode()+"','"+materialForm.getApproximateValue()+"'" +
				",'"+materialForm.getRequestedBy()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+fileList+"','PM','"+materialForm.getArtworkNo()+"','"+materialForm.getUnitOfMeasId()+"','"+materialForm.getValuationClass()+"','"+materialForm.getPuchaseGroupId()+"','Created','"+url+"','"+reportURL+"','No','"+pendingApprovers+"','"+materialForm.getStorage()+"','"+materialForm.getHsnCode()+"') ";	
				i=ad.SqlExecuteUpdate(insertQuery);
				if(i>0)
				{
					materialForm.setMessage2("Code creation request Created with request number='"+materialForm.getRequestNo()+"'.");
					materialForm.setTypeDetails("Update");
					
					String deleteHistory="delete material_code_request_history where REQUEST_NO='"+materialForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
		  	  		ad.SqlExecuteUpdate(deleteHistory);
		  			String insertHistryQuery="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
		  					",MATERIAL_GROUP_ID,IS_DMF_MATERIAL,DMF_GRADE_ID,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME,TO_BE_USED_IN_PRODUCTS" +
		  					",IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,RETEST_DAYS,RETEST_DAYS_TYPE,DUTY_ELEMENT,PACKING_MATERIAL_GROUP,Type_Of_Material,IS_ARTWORK_REVISION ,EXISTING_SAP_ITEM_CODE" +
		  					" ,APPROXIMATE_VALUE,REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,ARTWORK_NO,UNIT_OF_MEAS_ID,VALUATION_CLASS,PURCHASE_GROUP_ID,Approve_Type,URL,REPORT_URL,Role,HSN_Code)" +
		  					" values('"+materialForm.getRequestNo()+"','"+reqDate+"','"+materialForm.getLocationId()+"','"+materialForm.getStorageLocationId()+"','2','"+materialForm.getMaterialShortName()+"'" +
		  					",'"+materialForm.getMaterialLongName()+"','"+materialForm.getMaterialGroupId()+"','"+materialForm.getIsDMFMaterial()+"','"+materialForm.getDmfGradeId()+"','"+materialForm.getCosGradeNo()+"'" +
		  					",'"+materialForm.getAdditionalTest()+"','"+materialForm.getCountryId()+"','"+materialForm.getCustomerName()+"','"+materialForm.getToBeUsedInProducts()+"','"+materialForm.getIsVendorSpecificMaterial()+"','"+materialForm.getMfgrName()+"','"+materialForm.getSiteOfManufacture()+"','"+materialForm.getTempCondition()+"','"+materialForm.getStorageCondition()+"'" +
		  					",'"+materialForm.getRetestDays()+"','"+materialForm.getRetestType()+"','"+materialForm.getDutyElement()+"','"+materialForm.getPackageMaterialGroup()+"','"+materialForm.getTypeOfMaterial()+"','"+materialForm.getIsArtworkRevision()+"','"+materialForm.getExistingSAPItemCode()+"','"+materialForm.getApproximateValue()+"'" +
		  					",'"+user.getEmployeeNo()+"','"+dateNow+"','"+user.getEmployeeNo()+"','','PM','"+materialForm.getArtworkNo()+"','"+materialForm.getUnitOfMeasId()+"','"+materialForm.getValuationClass()+"','"+materialForm.getPuchaseGroupId()+"','Created','','','user','"+materialForm.getHsnCode()+"') ";	
		  		ad.SqlExecuteUpdate(insertHistryQuery);
		  		
					return displayNewPackageMaterial(mapping, form, request, response);
				}else{
					materialForm.setMessage("Error...When Saving  Material.Please Check....");
					materialForm.setTypeDetails("Save");
				}
				
			}
			/*String Req_Id = ""+materialForm.getRequestNo();
			EMailer email = new EMailer();
			i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");*/
		 }
		 else{
				String approvedStatus="Pending";
				
				 user=(UserInfo)session.getAttribute("user");
					if(userId==1)
					{
						approvedStatus=materialForm.getApproveType();
					}
					int i=0;
			
					String recordStatus="";
					String getRecordStatus="select Approve_Type from material_code_request where REQUEST_NO='"+materialForm.getRequestNo()+"' ";
					ResultSet rsRecord=ad.selectQuery(getRecordStatus);
					while(rsRecord.next())
					{
						recordStatus=rsRecord.getString("Approve_Type");
					}
					if(recordStatus.equalsIgnoreCase("Rejected"))
					{
						
						String deleteRecords="delete from All_Request where Req_Id='"+materialForm.getRequestNo()+"' and Req_Type='Material Master'";
						int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
						String updateFlag="update material_code_request set rejected_flag='y' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
						ad.SqlExecuteUpdate(updateFlag);
					}	
					
			
			 String updateQuery="update material_code_request set LOCATION_ID='"+materialForm.getLocationId()+"',STORAGE_LOCATION_ID='"+materialForm.getStorageLocationId()+"',MATERIAL_TYPE_ID='2',MATERIAL_SHORT_NAME='"+materialForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+materialForm.getMaterialLongName()+"'" +
			",MATERIAL_GROUP_ID='"+materialForm.getMaterialGroupId()+"',IS_DMF_MATERIAL='"+materialForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+materialForm.getDmfGradeId()+"',COS_GRADE_AND_NO='"+materialForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+materialForm.getAdditionalTest()+"',COUNTRY_ID='"+materialForm.getCountryId()+"',CUSTOMER_NAME='"+materialForm.getCustomerName()+"',TO_BE_USED_IN_PRODUCTS='"+materialForm.getToBeUsedInProducts()+"'" +
			",IS_VENDOR_SPECIFIC_MATERIAL='"+materialForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+materialForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+materialForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+materialForm.getTempCondition()+"',STORAGE_CONDITION='"+materialForm.getStorageCondition()+"',RETEST_DAYS='"+materialForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+materialForm.getRetestType()+"',DUTY_ELEMENT='"+materialForm.getDutyElement()+"',PACKING_MATERIAL_GROUP='"+materialForm.getPackageMaterialGroup()+"',Type_Of_Material='"+materialForm.getTypeOfMaterial()+"',IS_ARTWORK_REVISION='"+materialForm.getIsArtworkRevision()+"' ,EXISTING_SAP_ITEM_CODE='"+materialForm.getExistingSAPItemCode()+"'" +
			" ,APPROXIMATE_VALUE='"+materialForm.getApproximateValue()+"',REQUESTED_BY='"+materialForm.getRequestedBy()+"',Attachements='"+fileList+"',ARTWORK_NO='"+materialForm.getArtworkNo()+"',UNIT_OF_MEAS_ID='"+materialForm.getUnitOfMeasId()+"',VALUATION_CLASS='"+materialForm.getValuationClass()+"'," +
			"PURCHASE_GROUP_ID='"+materialForm.getPuchaseGroupId()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',Storage ='"+materialForm.getStorage()+"',HSN_Code='"+materialForm.getHsnCode()+"' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
			 
				i=ad.SqlExecuteUpdate(updateQuery);
				
				String deleteHistory="delete material_code_request_history where REQUEST_NO='"+materialForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  	  		ad.SqlExecuteUpdate(deleteHistory);
	  			String insertHistryQuery="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
	  					",MATERIAL_GROUP_ID,IS_DMF_MATERIAL,DMF_GRADE_ID,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME,TO_BE_USED_IN_PRODUCTS" +
	  					",IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,RETEST_DAYS,RETEST_DAYS_TYPE,DUTY_ELEMENT,PACKING_MATERIAL_GROUP,Type_Of_Material,IS_ARTWORK_REVISION ,EXISTING_SAP_ITEM_CODE" +
	  					" ,APPROXIMATE_VALUE,REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,ARTWORK_NO,UNIT_OF_MEAS_ID,VALUATION_CLASS,PURCHASE_GROUP_ID,Approve_Type,URL,REPORT_URL,Role,HSN_Code)" +
	  					" values('"+materialForm.getRequestNo()+"','"+reqDate+"','"+materialForm.getLocationId()+"','"+materialForm.getStorageLocationId()+"','2','"+materialForm.getMaterialShortName()+"'" +
	  					",'"+materialForm.getMaterialLongName()+"','"+materialForm.getMaterialGroupId()+"','"+materialForm.getIsDMFMaterial()+"','"+materialForm.getDmfGradeId()+"','"+materialForm.getCosGradeNo()+"'" +
	  					",'"+materialForm.getAdditionalTest()+"','"+materialForm.getCountryId()+"','"+materialForm.getCustomerName()+"','"+materialForm.getToBeUsedInProducts()+"','"+materialForm.getIsVendorSpecificMaterial()+"','"+materialForm.getMfgrName()+"','"+materialForm.getSiteOfManufacture()+"','"+materialForm.getTempCondition()+"','"+materialForm.getStorageCondition()+"'" +
	  					",'"+materialForm.getRetestDays()+"','"+materialForm.getRetestType()+"','"+materialForm.getDutyElement()+"','"+materialForm.getPackageMaterialGroup()+"','"+materialForm.getTypeOfMaterial()+"','"+materialForm.getIsArtworkRevision()+"','"+materialForm.getExistingSAPItemCode()+"','"+materialForm.getApproximateValue()+"'" +
	  					",'"+user.getEmployeeNo()+"','"+dateNow+"','"+user.getEmployeeNo()+"','','PM','"+materialForm.getArtworkNo()+"','"+materialForm.getUnitOfMeasId()+"','"+materialForm.getValuationClass()+"','"+materialForm.getPuchaseGroupId()+"','Created','','','user','"+materialForm.getHsnCode()+"') ";	
	  		ad.SqlExecuteUpdate(insertHistryQuery);
					
					/*if(userId==1)
					{
						String updateMaterial="update material_code_request set Approve_Type='"+approvedStatus+"' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
						i=ad.SqlExecuteUpdate(updateMaterial);
					}
				if(userId==2)
				{
				 String sapCreationDate=materialForm.getSapCreationDate();
				  String b[]=sapCreationDate.split("/");
					sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
					String updateMaterial="update material_code_request set SAP_CODE_NO='"+materialForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+materialForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+materialForm.getSapCreatedBy()+"' where REQUEST_NO='"+materialForm.getRequestNo()+"'";
					i=ad.SqlExecuteUpdate(updateMaterial);
					request.setAttribute("approved", "approved");
					request.setAttribute("sapApprover", "sapApprover");
				}
*/				if(i>0)
				{
					materialForm.setMessage2("Code creation request updated with request number='"+materialForm.getRequestNo()+"'.");
					materialForm.setTypeDetails("Update");
					return displayNewPackageMaterial(mapping, form, request, response);
				}else{
					materialForm.setMessage("Error...When modifying package material.Please Check....");
					materialForm.setTypeDetails("Update");
				}
			 
		 }
		 
		 ArrayList list = new ArrayList();
		  int count1=0;
		  String getCount="select count(*)  from material_code_request where request_no='"+materialForm.getRequestNo()+"'";
		  ResultSet rs6=ad.selectQuery(getCount);
		  while(rs6.next())
		  {
			  count1=rs6.getInt(1);
		  }
		  if(count1>=1)
		  {  
		  String getUplodedFiles="select *  from material_code_request where request_no='"+materialForm.getRequestNo()+"'";
			ResultSet rs5 = ad.selectQuery(getUplodedFiles);
				
			while (rs5.next()) {
				PackageMaterialMasterForm custForm1 = new PackageMaterialMasterForm();
						//custForm1.setUrl(url);
						
						custForm1.setFileList(rs5.getString("Attachements"));
						list.add(custForm1);
					}
					request.setAttribute("listName", list);
					
		  }	
		 
			 }
			  else{
				  materialForm.setMessage("Error...Please Upload File");
				  String typeDetails=materialForm.getTypeDetails();
				  materialForm.setTypeDetails(typeDetails);
			  }	 
		 
		 //SAP_CODE_NO='"+materialForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+materialForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+materialForm.getSapCreatedBy()+"',
	
			String status3=materialForm.getTypeOfMaterial();
			if(status3.equalsIgnoreCase("Printed Material")){
				request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
				String artWorkNo=materialForm.getArtworkNo();
				String isArtworkRevision=materialForm.getIsArtworkRevision();
				
				materialForm.setArtworkNo(artWorkNo);
				materialForm.setIsArtworkRevision(isArtworkRevision);
				
				
			}
			else
				request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
			
			
			String status1=materialForm.getIsDMFMaterial();
			if(status1.equalsIgnoreCase("True")){
				request.setAttribute("dmfMandatory", "dmfMandatory");
			String dmfGrade=materialForm.getDmfGradeId();
			String cosGrade=materialForm.getCosGradeNo();
			materialForm.setMfgrName(dmfGrade);
			materialForm.setSiteOfManufacture(cosGrade);
			}
			else
				request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
		 

		 
		 String status=materialForm.getIsVendorSpecificMaterial();
			if(status.equalsIgnoreCase("True")){
				request.setAttribute("vedorMandatory", "vedorMandatory");
				String mfgrName=materialForm.getMfgrName();
				String siteOfManufacture=materialForm.getSiteOfManufacture();
				materialForm.setMfgrName(mfgrName);
				materialForm.setSiteOfManufacture(siteOfManufacture);
			}
			else
				request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
		
			
			  LinkedList tempIDList=new LinkedList();
				LinkedList temValueList=new LinkedList();
				
				String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
				ResultSet rsTemp=ad.selectQuery(getTemp);
				while(rsTemp.next())
				{
					tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
					temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
				}
				materialForm.setTempIDList(tempIDList);
				materialForm.setTemValueList(temValueList);
				

				LinkedList storageIDList=new LinkedList();
				LinkedList storageLocList=new LinkedList();
				
				String getStorageLoc="select * from STORAGE_CONDITION";
				ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
				while(rsStorageLoc.next())
				{
					storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
					storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
				}
				materialForm.setStorageIDList(storageIDList);
				materialForm.setStorageLocList(storageLocList);
			
		 String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			//set Approved Field
			user=(UserInfo)session.getAttribute("user");
			if(userId==1)
			{	
				request.setAttribute("approved", "approved");
			}
		 
		  }catch (Exception e) {
			 
				e.printStackTrace();
			}
		  materialForm.setMaterialCodeLists1("2");
			materialForm.setMaterialTypeId("2");
		return mapping.findForward("newPackageMaterial");
	}
	
	public ActionForward deleteFileListModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		try{
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			String userId=user.getEmployeeNo();
		String checkedValues=request.getParameter("cValues");
		String UncheckedValues=request.getParameter("unValues");
	
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String v[] = checkedValues.split(",");
		
		 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 Properties props = new Properties();
	 	props.load(in);
		in.close();
	 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	String filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles";
	 	
	 	
	 	
		for(int i=0;i<v.length;i++)
		{
		String deleteQuery="delete from PacakageMaterial_Doc where file_name='"+v[i]+"' and request_no='"+materialForm.getRequestNo()+"' and userId='"+userId+"'";
		int j=ad.SqlExecuteUpdate(deleteQuery);
		
			if(j>0)
			{
			 	File fileToCreate = new File(filePath, v[i]);
			 	boolean test=fileToCreate.delete();
			 	System.out.println(test);
			 	
			 	//delete file in another path
			 	
			 	File fileToCreate1 = new File("E:/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles", v[i]);
			 	boolean test1=fileToCreate1.delete();
			}
	 	
		
		}
		ArrayList list = new ArrayList();
		ResultSet rs5 = ad.selectQuery("select *  from PacakageMaterial_Doc where request_no='"+materialForm.getRequestNo()+"' and userId='"+userId+"' ");
				while (rs5.next()) {
					CustomerMasterForm custForm1 = new CustomerMasterForm();
					String s=rs5.getString("file_name");
					custForm1.setFileList(rs5.getString("file_name"));
					custForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/"+rs5.getString("file_name")+"");
					list.add(custForm1);
				}
		request.setAttribute("listName", list);
		if(list.size()>0)
		{
			materialForm.setUploadFileStatus("yes");
		}
		else
			materialForm.setUploadFileStatus("no");
		
		String getCountryDetails="select * from Country";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
		}
		materialForm.setCounID(countryID);
		materialForm.setCountryName(countryName);
		String availableLoc=user.getAvailableLocations();
		if(availableLoc.equalsIgnoreCase(""))
			availableLoc="0";
		ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		while(rs11.next()) {
			locationList.add(rs11.getString("LOCID"));
			locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
		}
		if(locationList.size()==0){
			locationList.add("");
			locationLabelList.add("");
		}
		materialForm.setLocationIdList(locationList);
		materialForm.setLocationLabelList(locationLabelList);
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		materialForm.setStorageID(storageID);
		materialForm.setStorageIDName(storageName);
		
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("id"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
		}
		materialForm.setMaterTypeIDList(materTypeIDList);
		materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
		ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
		while(rsMaterialGroup.next())
		{
			materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
		}
		materialForm.setMaterGroupIDList(materGroupIDList);
		materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
		
		LinkedList unitOfMeasIdList=new LinkedList();
		LinkedList unitOfMeasIdValues=new LinkedList();
		
		String getunitMesurement="select * from UNIT_MESUREMENT";
		ResultSet rsUnit=ad.selectQuery(getunitMesurement);
		while(rsUnit.next())
		{
			unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
			unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
		}
		materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
		materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
		
		LinkedList puchaseGroupIdList=new LinkedList();
		LinkedList puchaseGroupIdValues=new LinkedList();
		
		String getPurchaseGroup="select * from PURCHASE_GROUP";
		ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
		while(rsPurchaseGroup.next())
		{
			puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
			puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
		}
		materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
		materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		
		LinkedList dmfGradeIDList=new LinkedList();
		LinkedList dmfGradeIDValueList=new LinkedList();
		
		String getDMFValues="select * from DMF_GRADE";
		ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
		while(rsDMFValues.next())
		{
			dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
			dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
		}
		materialForm.setDmfGradeIDList(dmfGradeIDList);
		materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
		
		LinkedList packageGroupID=new LinkedList();
		LinkedList packageGroupIDValue=new LinkedList();
		
		String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
		ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
		while(rsPackageGroup.next())
		{
			packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
			packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
		}
		materialForm.setPackageGroupID(packageGroupID);
		materialForm.setPackageGroupIDValue(packageGroupIDValue);
		
		String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
		ResultSet rsValuation=ad.selectQuery(getValuation);
		while(rsValuation.next())
		{
			valuationClassID.add(rsValuation.getString("VALUATION_ID"));
			valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
		}
		materialForm.setValuationClassID(valuationClassID);
		materialForm.setValuationClassName(valuationClassName);
		
		LinkedList tempIDList=new LinkedList();
		LinkedList temValueList=new LinkedList();
		
		String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
		ResultSet rsTemp=ad.selectQuery(getTemp);
		while(rsTemp.next())
		{
			tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
			temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
		}
		materialForm.setTempIDList(tempIDList);
		materialForm.setTemValueList(temValueList);
		

		LinkedList storageIDList=new LinkedList();
		LinkedList storageLocList=new LinkedList();
		
		String getStorageLoc="select * from STORAGE_CONDITION";
		ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
		while(rsStorageLoc.next())
		{
			storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
			storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
		}
		materialForm.setStorageIDList(storageIDList);
		materialForm.setStorageLocList(storageLocList);
		
		String status1=materialForm.getIsDMFMaterial();
		if(status1.equalsIgnoreCase("True")){
			request.setAttribute("dmfMandatory", "dmfMandatory");
		String dmfGrade=materialForm.getDmfGradeId();
		String cosGrade=materialForm.getCosGradeNo();
		materialForm.setMfgrName(dmfGrade);
		materialForm.setSiteOfManufacture(cosGrade);
		}
		else
			request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
		String status=materialForm.getIsVendorSpecificMaterial();
		if(status.equalsIgnoreCase("True")){
			request.setAttribute("vedorMandatory", "vedorMandatory");
			String mfgrName=materialForm.getMfgrName();
			String siteOfManufacture=materialForm.getSiteOfManufacture();
			materialForm.setMfgrName(mfgrName);
			materialForm.setSiteOfManufacture(siteOfManufacture);
		}
		else
			request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
		
		String status3=materialForm.getTypeOfMaterial();
		if(status3.equalsIgnoreCase("Printed Material")){
			request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
			String artWorkNo=materialForm.getArtworkNo();
			String isArtworkRevision=materialForm.getIsArtworkRevision();
			
			materialForm.setArtworkNo(artWorkNo);
			materialForm.setIsArtworkRevision(isArtworkRevision);
			
			
		}
		else
			request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
		
		///MAIL ID OF USER
		String mail="";
		String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
		ResultSet rss=ad.selectQuery(a);
		try {
			if(rss.next())
			{
			mail=rss.getString("EMAIL_ID")	;
			if(!mail.equalsIgnoreCase("null"))
			materialForm.setReqEmail(mail);
		
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
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		String typeDetails=materialForm.getTypeDetails();
		materialForm.setTypeDetails(typeDetails);
		materialForm.setMaterialCodeLists1("2");
		
		return mapping.findForward("newPackageMaterial");
	}
	
	
	public ActionForward uploadFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
			materialForm.setUploadFileStatus("yes");
			try{
					HttpSession session=request.getSession();
					UserInfo user=(UserInfo)session.getAttribute("user");
					
					String userId=user.getEmployeeNo();
					
					
					FormFile myFile = materialForm.getFileNames();
				    String contentType = myFile.getContentType();
					String fileName = myFile.getFileName();
					int filesize=myFile.getFileSize();
					byte[] fileData = myFile.getFileData();
					EssDao adlinks=new EssDao();	
					String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
					String filePath="";
					if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
					{
					
					 filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles");
					
					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 	 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	 filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles";
				 	 
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
							fileOutStream.write(myFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					request.setAttribute("fileName", fileName);
					
					
					//upload files in another path
					
					try{
						String filePath1 = "E:/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles";
						
						byte[] fileData1 = myFile.getFileData();
						
						
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
								fileOutStream1.write(myFile.getFileData());
								fileOutStream1.flush();
								fileOutStream1.close();
							}
						}
						
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					filePath = filePath.replace("\\", "\\");
					String sql9="select count(*) from PacakageMaterial_Doc where  file_name='"+fileName+"' ";
					ResultSet rs15 = adlinks.selectQuery(sql9);
					int fileCount=0;
					while (rs15.next())
					{
						fileCount=Integer.parseInt(rs15.getString(1));
					}
					if(fileCount>0)
					{
						materialForm.setMessage("File aleardy uploaded..please choose another file or change the file name");
					}
					else
					{
					String insertsql = "insert into PacakageMaterial_Doc(file_path,file_name,request_no,userId) values('"+filePath+"','"+fileName+"','"+materialForm.getRequestNo()+"','"+userId+"')";
					int a = adlinks.SqlExecuteUpdate(insertsql);
					if (a > 0)
					{
						materialForm.setMessage2("Documents uploaded successfully");
						
					}else {
						materialForm.setMessage("Error while uploading files ... please check entered values");
					}
					
					}
					
					}else{
						materialForm.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
						
							}
					ArrayList list = new ArrayList();
					ResultSet rs5 = adlinks.selectQuery("select *  from PacakageMaterial_Doc where request_no='"+materialForm.getRequestNo()+"' and userId='"+userId+"'");
							while (rs5.next()) {
								CustomerMasterForm custForm1 = new CustomerMasterForm();
								String s=rs5.getString("file_name");
								custForm1.setFileList(rs5.getString("file_name"));
								custForm1.setFilepath(filePath+"/"+rs5.getString("file_name")+"");
								list.add(custForm1);
							}
							request.setAttribute("listName", list);
							
					
							
							String getCountryDetails="select * from Country";
							LinkedList countryID=new LinkedList();
							LinkedList countryName=new LinkedList();
							ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
							while(rsCountryDetails.next()){
								countryID.add(rsCountryDetails.getString("LAND1"));
								countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
							}
							materialForm.setCounID(countryID);
							materialForm.setCountryName(countryName);
							String availableLoc=user.getAvailableLocations();
							if(availableLoc.equalsIgnoreCase(""))
								availableLoc="0";
							ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
							ArrayList locationList=new ArrayList();
							ArrayList locationLabelList=new ArrayList();
							while(rs11.next()) {
								locationList.add(rs11.getString("LOCID"));
								locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
							}
							if(locationList.size()==0){
								locationList.add("");
								locationLabelList.add("");
							}
							materialForm.setLocationIdList(locationList);
							materialForm.setLocationLabelList(locationLabelList);
							
							LinkedList storageID=new LinkedList();
							LinkedList storageName=new LinkedList();
							String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
							ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
							while(rsStrogeLocation.next()){
								storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
								storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
							}
							materialForm.setStorageID(storageID);
							materialForm.setStorageIDName(storageName);
							
							LinkedList materTypeIDList=new LinkedList();
							LinkedList materialTypeIdValueList=new LinkedList();
							String getMaterials="select * from MATERIAL_TYPE";
							ResultSet rsMaterial=ad.selectQuery(getMaterials);
							while(rsMaterial.next())
							{
								materTypeIDList.add(rsMaterial.getString("id"));
								materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
							}
							materialForm.setMaterTypeIDList(materTypeIDList);
							materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
							
							LinkedList materGroupIDList=new LinkedList();
							LinkedList materialGroupIdValueList=new LinkedList();
							
							String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%'  order by STXT ";
							ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
							while(rsMaterialGroup.next())
							{
								materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
								materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							}
							materialForm.setMaterGroupIDList(materGroupIDList);
							materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
							
							LinkedList unitOfMeasIdList=new LinkedList();
							LinkedList unitOfMeasIdValues=new LinkedList();
							
							String getunitMesurement="select * from UNIT_MESUREMENT";
							ResultSet rsUnit=ad.selectQuery(getunitMesurement);
							while(rsUnit.next())
							{
								unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
								unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
							}
							materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
							materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
							
							LinkedList puchaseGroupIdList=new LinkedList();
							LinkedList puchaseGroupIdValues=new LinkedList();
							
							String getPurchaseGroup="select * from PURCHASE_GROUP";
							ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
							while(rsPurchaseGroup.next())
							{
								puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
								puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
							}
							materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
							materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
							
							LinkedList valuationClassID=new LinkedList();
							LinkedList valuationClassName=new LinkedList();
							
							LinkedList dmfGradeIDList=new LinkedList();
							LinkedList dmfGradeIDValueList=new LinkedList();
							
							String getDMFValues="select * from DMF_GRADE";
							ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
							while(rsDMFValues.next())
							{
								dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
								dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
							}
							materialForm.setDmfGradeIDList(dmfGradeIDList);
							materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
							
							LinkedList packageGroupID=new LinkedList();
							LinkedList packageGroupIDValue=new LinkedList();
							
							String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
							ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
							while(rsPackageGroup.next())
							{
								packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
								packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
							}
							materialForm.setPackageGroupID(packageGroupID);
							materialForm.setPackageGroupIDValue(packageGroupIDValue);
							
							String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
							ResultSet rsValuation=ad.selectQuery(getValuation);
							while(rsValuation.next())
							{
								valuationClassID.add(rsValuation.getString("VALUATION_ID"));
								valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
							}
							materialForm.setValuationClassID(valuationClassID);
							materialForm.setValuationClassName(valuationClassName);
							
							LinkedList tempIDList=new LinkedList();
							LinkedList temValueList=new LinkedList();
							
							String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
							ResultSet rsTemp=ad.selectQuery(getTemp);
							while(rsTemp.next())
							{
								tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
								temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
							}
							materialForm.setTempIDList(tempIDList);
							materialForm.setTemValueList(temValueList);
							

							LinkedList storageIDList=new LinkedList();
							LinkedList storageLocList=new LinkedList();
							
							String getStorageLoc="select * from STORAGE_CONDITION";
							ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
							while(rsStorageLoc.next())
							{
								storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
								storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
							}
							materialForm.setStorageIDList(storageIDList);
							materialForm.setStorageLocList(storageLocList);
							
							
							
							String status1=materialForm.getIsDMFMaterial();
							if(status1.equalsIgnoreCase("True")){
								request.setAttribute("dmfMandatory", "dmfMandatory");
							String dmfGrade=materialForm.getDmfGradeId();
							String cosGrade=materialForm.getCosGradeNo();
							materialForm.setMfgrName(dmfGrade);
							materialForm.setSiteOfManufacture(cosGrade);
							}
							else
								request.setAttribute("dmfNotMandatory", "dmfNotMandatory");			
							
				String status=materialForm.getIsVendorSpecificMaterial();
				if(status.equalsIgnoreCase("True")){
					request.setAttribute("vedorMandatory", "vedorMandatory");
					String mfgrName=materialForm.getMfgrName();
					String siteOfManufacture=materialForm.getSiteOfManufacture();
					materialForm.setMfgrName(mfgrName);
					materialForm.setSiteOfManufacture(siteOfManufacture);	
					
				}
				else
					request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
				
				String status3=materialForm.getTypeOfMaterial();
				if(status3.equalsIgnoreCase("Printed Material")){
					request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
					String artWorkNo=materialForm.getArtworkNo();
					String isArtworkRevision=materialForm.getIsArtworkRevision();
					
					materialForm.setArtworkNo(artWorkNo);
					materialForm.setIsArtworkRevision(isArtworkRevision);
					
					
				}
				else
					request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");	
				
				///MAIL ID OF USER
				String mail="";
				String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
				ResultSet rss=ad.selectQuery(a);
				try {
					if(rss.next())
					{
					mail=rss.getString("EMAIL_ID")	;
					if(!mail.equalsIgnoreCase("null"))
					materialForm.setReqEmail(mail);
				
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
				
				}catch (Exception e) {
					e.printStackTrace();
				}
				String typeDetails=materialForm.getTypeDetails();
				materialForm.setTypeDetails(typeDetails);
				materialForm.setMaterialCodeLists1("2");
				return mapping.findForward("newPackageMaterial");
				}
	
	
	public ActionForward displayPackageMaterialList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);	
			
		LinkedList listOfMaterialCode=new LinkedList();
		String getList="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.Type='Package Material' and m.LOCATION_ID=l.LOCID";
		ResultSet rsList=ad.selectQuery(getList);
		while(rsList.next())
		{
			PackageMaterialMasterForm codeMasterForm=new PackageMaterialMasterForm();
			codeMasterForm.setRequestNo(rsList.getInt("REQUEST_NO"));
			String requestDate=rsList.getString("REQUEST_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			codeMasterForm.setRequestDate(requestDate);
			codeMasterForm.setLocationId(rsList.getString("Location_Name"));
			listOfMaterialCode.add(codeMasterForm);
			
		}
		
		request.setAttribute("listOfPackages", listOfMaterialCode);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		materialForm.setTypeDetails("Update");
		materialForm.setMaterialCodeLists("PACKING MATERIALS");
		return mapping.findForward("packageMaterialList");
	}
	
	public ActionForward displayNewPackageMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PackageMaterialMasterForm materialForm=(PackageMaterialMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
			
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		try{
			String userName=user.getFirstName()+" "+user.getMiddleName()+" "+user.getLastName();
			materialForm.setRequestedBy(userName);
			
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			materialForm.setCounID(countryID);
			materialForm.setCountryName(countryName);
			materialForm.setCountryId("IN");
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select * from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
			materialForm.setLocationIdList(locationList);
			materialForm.setLocationLabelList(locationLabelList);
			
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			materialForm.setStorageID(storageID);
			materialForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			materialForm.setMaterTypeIDList(materTypeIDList);
			materialForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			materialForm.setMaterGroupIDList(materGroupIDList);
			materialForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			materialForm.setUnitOfMeasIdList(unitOfMeasIdList);
			materialForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			materialForm.setPuchaseGroupIdList(puchaseGroupIdList);
			materialForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			materialForm.setDmfGradeIDList(dmfGradeIDList);
			materialForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			LinkedList packageGroupID=new LinkedList();
			LinkedList packageGroupIDValue=new LinkedList();
			
			
			String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
			ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
			while(rsPackageGroup.next())
			{
				packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
				packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
			}
			materialForm.setPackageGroupID(packageGroupID);
			materialForm.setPackageGroupIDValue(packageGroupIDValue);
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			materialForm.setValuationClassID(valuationClassID);
			materialForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			materialForm.setTempIDList(tempIDList);
			materialForm.setTemValueList(temValueList);
			

			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			materialForm.setStorageIDList(storageIDList);
			materialForm.setStorageLocList(storageLocList);
			
			String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			materialForm.setRequestNo(maxReqno);
			
			//Reset Form Values
			
			String locationId=request.getParameter("locationId");
			String materialGrup=request.getParameter("materialGrup");
			String shortName=request.getParameter("shortName");
			String longName=request.getParameter("longName");
			
			materialForm.setRequestDate(EMicroUtils.getCurrentSysDate());
			materialForm.setLocationId(locationId);
			materialForm.setStorageLocationId("");
			materialForm.setMaterialTypeId("2");
			materialForm.setMaterialCodeLists1("2");
			materialForm.setMaterialShortName(shortName);
			materialForm.setMaterialLongName(longName);
			materialForm.setMaterialGroupId(materialGrup);
			
			materialForm.setIsDMFMaterial("");
			materialForm.setDmfGradeId("");
			materialForm.setCosGradeNo("");
			materialForm.setAdditionalTest("");
			materialForm.setCustomerName("");
			materialForm.setToBeUsedInProducts("");
			materialForm.setIsVendorSpecificMaterial("");
			materialForm.setMfgrName("");
			materialForm.setSiteOfManufacture("");
			materialForm.setRetestDays("");
			materialForm.setDutyElement("");
			materialForm.setPackageMaterialGroup("");
			materialForm.setIsArtworkRevision("");
			materialForm.setExistingSAPItemCode("");
			materialForm.setApproximateValue("");
			materialForm.setSapCodeNo("");
			materialForm.setSapCodeExists("");
			materialForm.setSapCreationDate("");
			materialForm.setSapCreatedBy("");
			materialForm.setArtworkNo("");
			materialForm.setUnitOfMeasId("");
			materialForm.setValuationClass("");
			materialForm.setPuchaseGroupId("");
			materialForm.setUploadFileStatus("no");
			materialForm.setTempCondition("");
			materialForm.setStorageCondition("");
			materialForm.setTypeOfMaterial("");
			
			///MAIL ID OF USER
			String mail="";
			String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
			ResultSet rss=ad.selectQuery(a);
			try {
				if(rss.next())
				{
				mail=rss.getString("EMAIL_ID")	;
				if(!mail.equalsIgnoreCase("null"))
					materialForm.setReqEmail(mail);
			
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
			
			
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		
	
		materialForm.setTypeDetails("Save");
		request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
		request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
		request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
		return mapping.findForward("newPackageMaterial");
	}
	
	
	
}
