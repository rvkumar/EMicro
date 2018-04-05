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
import com.microlabs.ess.form.RawMaterialForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;
public class RawMaterialAction extends DispatchAction{
	
	
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList apprList=new LinkedList();
		MaterialmasterDAO dao=new MaterialmasterDAO();
		String location=request.getParameter("locationId");
		String matGroup=request.getParameter("materialGroupId");
		String materialTypeId=request.getParameter("materialGroupId");
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
		apprList=dao.approversList(location, "1", matGroup);
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
		return mapping.findForward("apprList");
	}
	public ActionForward saveAndSubmitMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
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
		
		int  userId=user.getId();
		String userTextId=Integer.toString(userId);
		try{
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
		String dateNow = ft.format(dNow);
		  String reqDate=masterForm.getRequestDate();
		 	 
		  String a[]=reqDate.split("/");
		  for(int i=0;i<a.length;i++)
		  {
			  System.out.println("a="+a[i]);
		  }
		  reqDate=a[2]+"-"+a[1]+"-"+a[0];
		  String fileList="";
		  try{
			String getUploadedFiles="select * from UploadMaterial_CodeMaster_Doc where request_no='"+masterForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rs=ad.selectQuery(getUploadedFiles);
			while(rs.next())
			{
				fileList+="jsp/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles"+"/"+rs.getString("file_name")+",";
			}
		  }catch (Exception e) {
			e.printStackTrace();
		}
		 
		  if(!fileList.equalsIgnoreCase("")){
		  int count=0;
		  
		  String typeDetails=masterForm.getTypeDetails();
			
		  
		  String phramacopSpecification=masterForm.getPharmacopSpecification();
		  phramacopSpecification=phramacopSpecification+" ";
	String tobeUsedInProducts=masterForm.getToBeUsedInProducts();
	tobeUsedInProducts=tobeUsedInProducts+" ";
	String customername=masterForm.getCustomerName();
	customername=customername+" ";
	String genericName=masterForm.getGenericName();
	  genericName=genericName+" ";
	  masterForm.setGenericName(genericName);
	  String synonyam=masterForm.getSynonym();
	  synonyam=synonyam+" ";
	  masterForm.setSynonym(synonyam);
	  String matGroup=masterForm.getMaterialGroupId();
	int checkApprover=0;
	  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='RM' and " +
	  		" Location=(select LOCATION_CODE from Location where LOCID='"+masterForm.getLocationId()+"') and Material_Group='"+matGroup+"'";
	  ResultSet rsCheck=ad.selectQuery(checkApprovers);
	  while(rsCheck.next())
	  {
		  checkApprover=1;
	  }
	  if(checkApprover==0)
	  {
		  checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='RM' and " +
			" Location=(select LOCATION_CODE from Location where LOCID='"+masterForm.getLocationId()+"') and Material_Group=''";
			   rsCheck=ad.selectQuery(checkApprovers);
			  while(rsCheck.next())
			  {
				  checkApprover=1;
			  }
	  }
	  if(typeDetails.equalsIgnoreCase("Save"))
	  {
			String getcount="select count(*) from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getcount);
			while(rs.next())
			{
				count=rs.getInt(1);
			}
			String insertQuery="";
			String approve="Pending";
			
			//added for approval task
			
			int i=0;
			 
				
			//added for approval task
			if(count>0){	
				//RequestNo is available
				int old_RequestNo=masterForm.getRequestNo();
				String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				masterForm.setRequestNo(maxReqno);
			  String url="rawMaterial.do?method=editMaterialRecord";
			  String reportURL="rawMaterial.do?method=getReport";
			 
			  //SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY, '"+masterForm.getSapCodeNo()+"','"+masterForm.getSapCodeExists()+"','"+sapCreationDate+"','"+masterForm.getSapCreatedBy()+"'
			  insertQuery="INSERT INTO material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME," +
					"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,PHARMACOP_NAME,PHARMACOP_GRADE,GENERIC_NAME,SYNONYM,PHARMACOP_SPECIFICATION" +
					",IS_DMF_MATERIAL,DMF_GRADE_ID,MATERIAL_GRADE,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME" +
					",TO_BE_USED_IN_PRODUCTS,IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,SHELF_LIFE,SHELF_LIFE_TYPE,DUTY_ELEMENT" +
					",RETEST_DAYS,RETEST_DAYS_TYPE,VALUATION_CLASS,APPROXIMATE_VALUE,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID," +
					"REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,Approve_Type,URL,REPORT_URL,last_approver,pending_approver,Storage,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
							"'"+masterForm.getLocationId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getMaterialShortName()+"'," +
					"'"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPharmacopName()+"','"+masterForm.getPharmacopGrade()+"','"+masterForm.getGenericName()+"','"+masterForm.getSynonym()+"','"+phramacopSpecification+"'" +
					",'"+masterForm.getIsDMFMaterial()+"','"+masterForm.getDmfGradeId()+"','"+masterForm.getMaterialGrade()+"','"+masterForm.getCosGradeNo()+"','"+masterForm.getAdditionalTest()+"','"+masterForm.getCountryId()+"','"+customername+"'" +
					",'"+tobeUsedInProducts+"','"+masterForm.getIsVendorSpecificMaterial()+"','"+masterForm.getMfgrName()+"','"+masterForm.getSiteOfManufacture()+"','"+masterForm.getTempCondition()+"','"+masterForm.getStorageCondition()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfLifeType()+"','"+masterForm.getDutyElement()+"'" +
					",'"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+masterForm.getApproximateValue()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"'," +
					"'"+masterForm.getRequestedBy()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+fileList+"','RM','Created','"+url+"','"+reportURL+"','No','','"+masterForm.getStorage()+"','"+masterForm.getHsnCode()+"')";
			
			  
			  i=ad.SqlExecuteUpdate(insertQuery);
			
			  int j=0;
			  j=ad.SqlExecuteUpdate("update UploadMaterial_CodeMaster_Doc set request_no='"+masterForm.getRequestNo()+"' where request_no='"+old_RequestNo+"' and  userId='"+userId+"'");
			
			  if(i>0)
			  {
				masterForm.setMessage2("Alert Code creation request saved with New request number='"+masterForm.getRequestNo()+"' ");
				
				if(checkApprover==0)
				{
				masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");	
				return displayNewMaterialCodeMaster(mapping, form, request, response);
				}
			  }else{
				masterForm.setMessage("Error...When code saving creation request.Please check");
				checkApprover=0;
				masterForm.setTypeDetails("Save");
			  }
			}
			else{
				String url="rawMaterial.do?method=editMaterialRecord";
				String reportURL="rawMaterial.do?method=getReport";
				//SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY, '"+masterForm.getSapCodeNo()+"','"+masterForm.getSapCodeExists()+"','"+sapCreationDate+"','"+masterForm.getSapCreatedBy()+"'
				insertQuery="INSERT INTO material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME," +
					"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,PHARMACOP_NAME,PHARMACOP_GRADE,GENERIC_NAME,SYNONYM,PHARMACOP_SPECIFICATION" +
					",IS_DMF_MATERIAL,DMF_GRADE_ID,MATERIAL_GRADE,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME" +
					",TO_BE_USED_IN_PRODUCTS,IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,SHELF_LIFE,SHELF_LIFE_TYPE,DUTY_ELEMENT" +
					",RETEST_DAYS,RETEST_DAYS_TYPE,VALUATION_CLASS,APPROXIMATE_VALUE,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID," +
					"REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,Approve_Type,URL,REPORT_URL,last_approver,pending_approver,Storage,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
							"'"+masterForm.getLocationId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getMaterialShortName()+"'," +
					"'"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPharmacopName()+"','"+masterForm.getPharmacopGrade()+"','"+masterForm.getGenericName()+"','"+masterForm.getSynonym()+"','"+phramacopSpecification+"'" +
					",'"+masterForm.getIsDMFMaterial()+"','"+masterForm.getDmfGradeId()+"','"+masterForm.getMaterialGrade()+"','"+masterForm.getCosGradeNo()+"','"+masterForm.getAdditionalTest()+"','"+masterForm.getCountryId()+"','"+customername+"'" +
					",'"+tobeUsedInProducts+"','"+masterForm.getIsVendorSpecificMaterial()+"','"+masterForm.getMfgrName()+"','"+masterForm.getSiteOfManufacture()+"','"+masterForm.getTempCondition()+"','"+masterForm.getStorageCondition()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfLifeType()+"','"+masterForm.getDutyElement()+"'" +
					",'"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+masterForm.getApproximateValue()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"'," +
					"'"+masterForm.getRequestedBy()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+fileList+"','RM','Created','"+url+"','"+reportURL+"','No','','"+masterForm.getStorage()+"','"+masterForm.getHsnCode()+"')";
				i=0;
				i=ad.SqlExecuteUpdate(insertQuery);
				approve="Pending";
					if(i>0)
				{
					masterForm.setMessage2("Code creation request created with request number='"+masterForm.getRequestNo()+"'.");
					
					if(checkApprover==0)
					{
						masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");	
					return displayNewMaterialCodeMaster(mapping, form, request, response);
					}
				
				}else{
					masterForm.setMessage("Error...When code saving creation request.Please check");
					checkApprover=0;
					masterForm.setTypeDetails("Save");
				}
		
			}
			
		
	  }
	  else{

		  System.out.println("update Material Code");
		  String approvedStatus="Pending";
			
		  user=(UserInfo)session.getAttribute("user");
			if(userId==1)
			{
				approvedStatus=masterForm.getApproveType();
			}
			int i=0;
			String recordStatus="";
			String getRecordStatus="select Approve_Type from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"' ";
			ResultSet rsRecord=ad.selectQuery(getRecordStatus);
			while(rsRecord.next())
			{
				recordStatus=rsRecord.getString("Approve_Type");
			}
			if(recordStatus.equalsIgnoreCase("Rejected"))
			{
				
				
				String deleteRecords="delete from All_Request where Req_Id='"+masterForm.getRequestNo()+"' and Req_Type='Material Master'";
				int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);

				String updateFlag="update material_code_request set rejected_flag='y' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				ad.SqlExecuteUpdate(updateFlag);
			}
			String updatQuery="update material_code_request set  REQUEST_DATE='"+reqDate+"',LOCATION_ID='"+masterForm.getLocationId()+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"'," +
				"MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',PHARMACOP_NAME='"+masterForm.getPharmacopName()+"',PHARMACOP_GRADE='"+masterForm.getPharmacopGrade()+"',GENERIC_NAME='"+masterForm.getGenericName()+"',SYNONYM='"+masterForm.getSynonym()+"',PHARMACOP_SPECIFICATION='"+phramacopSpecification+"'" +
				",IS_DMF_MATERIAL='"+masterForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+masterForm.getDmfGradeId()+"',MATERIAL_GRADE='"+masterForm.getMaterialGrade()+"',COS_GRADE_AND_NO='"+masterForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+masterForm.getAdditionalTest()+"',COUNTRY_ID='"+masterForm.getCountryId()+"',CUSTOMER_NAME='"+customername+"'" +
				",TO_BE_USED_IN_PRODUCTS='"+tobeUsedInProducts+"',IS_VENDOR_SPECIFIC_MATERIAL='"+masterForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+masterForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+masterForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+masterForm.getTempCondition()+"',STORAGE_CONDITION='"+masterForm.getStorageCondition()+"',SHELF_LIFE='"+masterForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+masterForm.getShelfLifeType()+"',DUTY_ELEMENT='"+masterForm.getDutyElement()+"'" +
				",RETEST_DAYS='"+masterForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+masterForm.getRetestType()+"',VALUATION_CLASS='"+masterForm.getValuationClass()+"',APPROXIMATE_VALUE='"+masterForm.getApproximateValue()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+masterForm.getPuchaseGroupId()+"'" +
				",REQUESTED_BY='"+masterForm.getRequestedBy()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Attachements='"+fileList+"',Approve_Type='Created',Storage ='"+masterForm.getStorage()+"',HSN_Code='"+masterForm.getHsnCode()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
		  
			i=ad.SqlExecuteUpdate(updatQuery);
			if(checkApprover==0)
			{
				masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
			return displayNewMaterialCodeMaster(mapping, form, request, response);
			}
			
			String status=masterForm.getIsVendorSpecificMaterial();
			if(status.equalsIgnoreCase("True")){
				String mfgrName=masterForm.getMfgrName();
				String siteOfManufacture=masterForm.getSiteOfManufacture();
				masterForm.setMfgrName(mfgrName);
				masterForm.setSiteOfManufacture(siteOfManufacture);
			}	
			else
			{
			}
			
			String status1=masterForm.getIsDMFMaterial();
			if(status1.equalsIgnoreCase("True")){
				request.setAttribute("isDmfMaterialMandatory", "isDmfMaterialMandatory");
				String dmfGradeId=masterForm.getDmfGradeId();
				String dmfGrade=masterForm.getMaterialGrade();
				String cosGrade=masterForm.getCosGradeNo();
				masterForm.setDmfGradeId(dmfGradeId);
				masterForm.setMaterialGrade(dmfGrade);
				masterForm.setCosGradeNo(cosGrade);
			}
			else
				request.setAttribute("isDmfMaterialNotMandatory", "isDmfMaterialNotMandatory");
			
			
			
			
			
			if(i>0)
			{
				masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'");
				masterForm.setTypeDetails("Update");
			}else{
				masterForm.setMessage("Error...When updating code creation reequest.Please Check");
				masterForm.setTypeDetails("Update");
				checkApprover=0;
			} 
			
			
		  
	  	  
		  
		  
		  
	  }
	 
	  if(checkApprover==1)
		{
			//send request to  approvers
			
			Date dNow1 = new Date( );
			ft = new SimpleDateFormat ("dd/MM/yyyy");
			String dateNow1 = ft.format(dNow1);
		 	String pApprover="";
		 	String parllelAppr1="";
		 	String parllelAppr2="";
  		
  		String loctID="";
  		String matType="";
		 	String getMatGroup="select mast.Type,mast.MATERIAL_GROUP_ID,loc.LOCATION_CODE from material_code_request  as mast,Location as loc where mast.REQUEST_NO='"+masterForm.getRequestNo()+"' and loc.LOCID=mast.LOCATION_ID ";
		 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
		 	while(rsMatGrup.next()){
		 		matGroup=rsMatGrup.getString("MATERIAL_GROUP_ID");
		 		loctID=rsMatGrup.getString("LOCATION_CODE");
		 		matType=rsMatGrup.getString("Type");
		 	}
		 checkApprover=0;
		 	String pendingApprs="";
		 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='RM' and app.Material_Group='"+matGroup+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
  			 getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='RM' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
  			
  		String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
  		ad.SqlExecuteUpdate(deleteHistory);
  		String	insertHistryQuery="INSERT INTO material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME," +
					"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,PHARMACOP_NAME,PHARMACOP_GRADE,GENERIC_NAME,SYNONYM,PHARMACOP_SPECIFICATION" +
					",IS_DMF_MATERIAL,DMF_GRADE_ID,MATERIAL_GRADE,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME" +
					",TO_BE_USED_IN_PRODUCTS,IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,SHELF_LIFE,SHELF_LIFE_TYPE,DUTY_ELEMENT" +
					",RETEST_DAYS,RETEST_DAYS_TYPE,VALUATION_CLASS,APPROXIMATE_VALUE,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID," +
					"REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,Approve_Type,URL,REPORT_URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
							"'"+masterForm.getLocationId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getMaterialShortName()+"'," +
					"'"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPharmacopName()+"','"+masterForm.getPharmacopGrade()+"','"+masterForm.getGenericName()+"','"+masterForm.getSynonym()+"','"+phramacopSpecification+"'" +
					",'"+masterForm.getIsDMFMaterial()+"','"+masterForm.getDmfGradeId()+"','"+masterForm.getMaterialGrade()+"','"+masterForm.getCosGradeNo()+"','"+masterForm.getAdditionalTest()+"','"+masterForm.getCountryId()+"','"+customername+"'" +
					",'"+tobeUsedInProducts+"','"+masterForm.getIsVendorSpecificMaterial()+"','"+masterForm.getMfgrName()+"','"+masterForm.getSiteOfManufacture()+"','"+masterForm.getTempCondition()+"','"+masterForm.getStorageCondition()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfLifeType()+"','"+masterForm.getDutyElement()+"'" +
					",'"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+masterForm.getApproximateValue()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"'," +
					"'"+user.getEmployeeNo()+"','"+dateNow+"','"+user.getEmployeeNo()+"','','RM','Created','','','user','"+masterForm.getHsnCode()+"')";
				int j=0;
				j=ad.SqlExecuteUpdate(insertHistryQuery);
  		
  		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
			saveRecReq = saveRecReq + "'"+masterForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','"+matType+"')";
			int ij=ad.SqlExecuteUpdate(saveRecReq);
			if(!(parllelAppr1.equalsIgnoreCase(""))){
				
			
			 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
			 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+masterForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','"+matType+"')";
			 ad.SqlExecuteUpdate(sendRecParllelAppr1);
			}
			if(!(parllelAppr2.equalsIgnoreCase(""))){
				
				 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+masterForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','"+matType+"')";
				 ad.SqlExecuteUpdate(sendRecParllelAppr2);
			}
			
			if(ij>0){
				masterForm.setMessage2("Request No "+masterForm.getRequestNo()+". Submitted for approval successully.");
				EMailer email = new EMailer();
				String approvermail="";
				String reqNo=Integer.toString(masterForm.getRequestNo());
				int		i = email.sendMailToApprover(request, approvermail,reqNo, "Material Code Request");
				String updateStatus="update material_code_request set Approve_Type='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				ad.SqlExecuteUpdate(updateStatus);
				request.setAttribute("sendMessage", "sendMessage");
				return displayNewMaterialCodeMaster(mapping, form, request, response);
				
			}else{
				masterForm.setMessage2("Error while submiting approval...");
			}
  		}else{
  			
  			masterForm.setMessage2("No Approvers are assigned.Please Contact to Admin");
  		}
			
			
		
		}
 
	  
	  
  }
  else{
	  masterForm.setMessage("Error...Please Upload File");
	  String typeDetails=masterForm.getTypeDetails();
	  masterForm.setTypeDetails(typeDetails);
	  
	  
	  String status=masterForm.getIsVendorSpecificMaterial();
		if(status.equalsIgnoreCase("True")){
			request.setAttribute("vedorMandatory", "vedorMandatory");
			String mfgrName=masterForm.getMfgrName();
			String siteOfManufacture=masterForm.getSiteOfManufacture();
			masterForm.setMfgrName(mfgrName);
			masterForm.setSiteOfManufacture(siteOfManufacture);
		}	
		else
			request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
		
		String status1=masterForm.getIsDMFMaterial();
		if(status1.equalsIgnoreCase("True")){
			request.setAttribute("isDmfMaterialMandatory", "isDmfMaterialMandatory");
			String dmfGradeId=masterForm.getDmfGradeId();
			String dmfGrade=masterForm.getMaterialGrade();
			String cosGrade=masterForm.getCosGradeNo();
			masterForm.setDmfGradeId(dmfGradeId);
			masterForm.setMaterialGrade(dmfGrade);
			masterForm.setCosGradeNo(cosGrade);
		}
		else
			request.setAttribute("isDmfMaterialNotMandatory", "isDmfMaterialNotMandatory");
		
  }
	  ArrayList list = new ArrayList();
	
	  if(!fileList.equalsIgnoreCase(""))
	  {  
	  String getUplodedFiles="select *  from material_code_request where request_no='"+masterForm.getRequestNo()+"'";
		ResultSet rs5 = ad.selectQuery(getUplodedFiles);
			
		while (rs5.next()) {
					RawMaterialForm custForm1 = new RawMaterialForm();
					//custForm1.setUrl(url);
					
					custForm1.setFileList(rs5.getString("Attachements"));
					list.add(custForm1);
				}
				request.setAttribute("listName", list);
				
	  }	
	
	  String getCountryDetails="select * from Country";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
		}
		masterForm.setCounID(countryID);
		masterForm.setCountryName(countryName);
		
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
		
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		masterForm.setStorageID(storageID);
		masterForm.setStorageIDName(storageName);
		
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("id"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
		}
		masterForm.setMaterTypeIDList(materTypeIDList);
		masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%RM%' order by STXT";
		ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
		while(rsMaterialGroup.next())
		{
			materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
		}
		masterForm.setMaterGroupIDList(materGroupIDList);
		masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
		
		LinkedList unitOfMeasIdList=new LinkedList();
		LinkedList unitOfMeasIdValues=new LinkedList();
		
		String getunitMesurement="select * from UNIT_MESUREMENT";
		ResultSet rsUnit=ad.selectQuery(getunitMesurement);
		while(rsUnit.next())
		{
			unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
			unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
		}
		masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
		masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
		
		LinkedList puchaseGroupIdList=new LinkedList();
		LinkedList puchaseGroupIdValues=new LinkedList();
		
		String getPurchaseGroup="select * from PURCHASE_GROUP";
		ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
		while(rsPurchaseGroup.next())
		{
			puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
			puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
		}
		masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
		masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
		
		LinkedList dmfGradeIDList=new LinkedList();
		LinkedList dmfGradeIDValueList=new LinkedList();
		
		String getDMFValues="select * from DMF_GRADE";
		ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
		while(rsDMFValues.next())
		{
			dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
			dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
		}
		masterForm.setDmfGradeIDList(dmfGradeIDList);
		masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
		
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		
		String getValuation="select * from VALUATION_CLASS where MAT_TYPE='RM'";
		ResultSet rsValuation=ad.selectQuery(getValuation);
		while(rsValuation.next())
		{
			valuationClassID.add(rsValuation.getString("VALUATION_ID"));
			valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
		}
		masterForm.setValuationClassID(valuationClassID);
		masterForm.setValuationClassName(valuationClassName);
		
		LinkedList tempIDList=new LinkedList();
		LinkedList temValueList=new LinkedList();
		
		String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
		ResultSet rsTemp=ad.selectQuery(getTemp);
		while(rsTemp.next())
		{
			tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
			temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
		}
		masterForm.setTempIDList(tempIDList);
		masterForm.setTemValueList(temValueList);
		

		LinkedList storageIDList=new LinkedList();
		LinkedList storageLocList=new LinkedList();
		
		String getStorageLoc="select * from STORAGE_CONDITION";
		ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
		while(rsStorageLoc.next())
		{
			storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
			storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
		}
		masterForm.setStorageIDList(storageIDList);
		masterForm.setStorageLocList(storageLocList);

		//set Approved Field
		user=(UserInfo)session.getAttribute("user");
		if(userId==1)
		{	
			request.setAttribute("approved", "approved");
		}
		if(userId==2)
		{	
			request.setAttribute("approved", "approved");
			request.setAttribute("sapApprover", "sapApprover");
		}
		
		 
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		masterForm.setMaterialCodeLists("RAW MATERIALS");
		String forwardType="";
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="rawmaterialSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)//Creatore
		{
			forwardType="newMasterForm";
		}
		masterForm.setMaterialTypeId("1");
		masterForm.setMaterialCodeLists("1");
		return mapping.findForward(forwardType);
	}
	
	
	public ActionForward copyNewRawMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String materialType="";
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			
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
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%RM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			masterForm.setDmfGradeIDList(dmfGradeIDList);
			masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='RM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			masterForm.setTempIDList(tempIDList);
			masterForm.setTemValueList(temValueList);
			
			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			masterForm.setStorageIDList(storageIDList);
			masterForm.setStorageLocList(storageLocList);
		
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
			masterForm.setRequestNo(maxReqno);
			masterForm.setRequestDate(EMicroUtils.getCurrentSysDate());
			masterForm.setLocationId("");
			masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
			masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
			masterForm.setMaterialShortName(""); 
			masterForm.setMaterialLongName("");
			masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
			
		    
			
			
			
			
			
			
			
			masterForm.setTempCondition(rs.getString("TEMP_CONDITION"));
			masterForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
			masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
			
			masterForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
			masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
			masterForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
			masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
			masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
			masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			masterForm.setRequestedBy(rs.getString("REQUESTED_BY"));
			
		
			
			
			int  userId=user.getId();
			if(userId==1)
				{	
				masterForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
				}
			if(userId==2)
			{
				String sapCodeno=rs.getString("SAP_CODE_NO");
				if(sapCodeno!=null)
				{
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
					String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
					{
						masterForm.setSapCodeExists("True");
					}
					if(sapCodeExist.equalsIgnoreCase("0"))
						masterForm.setSapCodeExists("False");
					String createDate=rs.getString("SAP_CREATION_DATE");
					String a1[]=createDate.split(" ");
					createDate=a1[0];
					String b1[]=createDate.split("-");
					createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					masterForm.setSapCreationDate(createDate);
					masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				}
				masterForm.setApproveType(rs.getString("Approve_Type"));
				materialType=rs.getString("Type");
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
			}
		}
		
		masterForm.setTypeDetails("Update");
		
		///MAIL ID OF USER
				String mail="";
				String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
				ResultSet rss=ad.selectQuery(a);
				try {
					if(rss.next())
					{
					mail=rss.getString("EMAIL_ID")	;
					if(!mail.equalsIgnoreCase("null"))
					masterForm.setReqEmail(mail);
				
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterialCodeLists("RAW MATERIALS");
		String forwardType="newMasterForm";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="rawmaterialSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newMasterForm";
		}
		masterForm.setTypeDetails("Save");
		masterForm.setMaterialTypeId("1");
		masterForm.setMaterialCodeLists("1");
		return mapping.findForward(forwardType);
	}
	
	
	public ActionForward getApproversDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
		try{
			String getApprovers="";
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("");
	}
	
	public ActionForward saveSAPCrationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
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
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			
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
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%RM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			masterForm.setDmfGradeIDList(dmfGradeIDList);
			masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='RM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
		
		
		String getMaterial="select * from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
		ResultSet rs=ad.selectQuery(getMaterial);
		while(rs.next())
		{
			masterForm.setRequestNo(masterForm.getRequestNo());
			String reqDate=rs.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			masterForm.setRequestDate(reqDate);
			masterForm.setLocationId(rs.getString("LOCATION_ID"));
			masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
			masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
			masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
			masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
			masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
			masterForm.setPharmacopName(rs.getString("PHARMACOP_NAME"));
		String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
			masterForm.setPharmacopGrade(pharmacopGrade);
			
			masterForm.setGenericName(rs.getString("GENERIC_NAME"));
			masterForm.setSynonym(rs.getString("SYNONYM"));
			masterForm.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
			String isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
			if(isDMfMaterial.equalsIgnoreCase("1"))
			{
				masterForm.setIsDMFMaterial("True");
				masterForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
				masterForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
				masterForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
				
			}
			masterForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
			if(isDMfMaterial.equalsIgnoreCase("0"))
			{
				masterForm.setIsDMFMaterial("False");
				
				
			}
			
			masterForm.setCountryId(rs.getString("COUNTRY_ID"));
			masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
			masterForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
			
			String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
			if(isVendorStatus.equalsIgnoreCase("1"))
			{
				masterForm.setIsVendorSpecificMaterial("True");
				masterForm.setMfgrName(rs.getString("MFGR_NAME"));
				masterForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
			}
			if(isVendorStatus.equalsIgnoreCase("0"))
			{
				masterForm.setIsVendorSpecificMaterial("False");
			}
			
			masterForm.setTempCondition(rs.getString("TEMP_CONDITION"));
			masterForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
			masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
			String dutyElement=rs.getString("DUTY_ELEMENT");
			if(dutyElement.equalsIgnoreCase("1"))
			{
				masterForm.setDutyElement("True");
			}
			if(dutyElement.equalsIgnoreCase("0"))
				masterForm.setDutyElement("False");
			masterForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
			masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
			masterForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
			masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
			masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
			masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			masterForm.setRequestedBy(rs.getString("REQUESTED_BY"));
			
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
			
				materialForm2.setUploadFilePath(v[i]);
			int x=v[i].lastIndexOf("/");
			uploadedFiles=v[i].substring(x+1);		
			materialForm2.setFileList(uploadedFiles);
			fileList.add(materialForm2);
			}
			request.setAttribute("listName", fileList);
			}
			
			
			int  userId=user.getId();
			if(userId==1)
				{	
			//	masterForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
				}
			if(userId==2)
			{
				String sapCodeno=rs.getString("SAP_CODE_NO");
				if(sapCodeno!=null)
				{
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
					String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
					{
						masterForm.setSapCodeExists("True");
					}
					if(sapCodeExist.equalsIgnoreCase("0"))
						masterForm.setSapCodeExists("False");
					String createDate=rs.getString("SAP_CREATION_DATE");
					String a1[]=createDate.split(" ");
					createDate=a1[0];
					String b1[]=createDate.split("-");
					createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					masterForm.setSapCreationDate(createDate);
					masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				}
				masterForm.setApproveType(rs.getString("Approve_Type"));
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
			}
		}
		
		int  userId=user.getId();
		int i=0;
		
			 String sapCreationDate=masterForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
			String updateMaterial="update material_code_request set SAP_CODE_NO='"+masterForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+masterForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+masterForm.getSapCreatedBy()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			i=ad.SqlExecuteUpdate(updateMaterial);
			if(i>0)
			{
				masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'");
				masterForm.setTypeDetails("Update");
			}else{
				masterForm.setMessage("Error...When updating code creation reequest.Please Check");
				masterForm.setTypeDetails("Update");
			} 
		
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterialCodeLists("RAW MATERIALS");
		
	
		
		return mapping.findForward("approverMaster");
	}
	
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
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
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			
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
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%RM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			masterForm.setDmfGradeIDList(dmfGradeIDList);
			masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='RM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			masterForm.setTempIDList(tempIDList);
			masterForm.setTemValueList(temValueList);
			
			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			String getStorageLoc="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STORAGE_LOCATION_ID"));
				storageLocList.add(rsValuation.getString("STORAGE_LOCATION_ID")+" - "+rsValuation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageIDList(storageIDList);
			masterForm.setStorageLocList(storageLocList);
		
		String getMaterial="select * from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
		ResultSet rs=ad.selectQuery(getMaterial);
		while(rs.next())
		{
			masterForm.setRequestNo(masterForm.getRequestNo());
			String reqDate=rs.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			masterForm.setRequestDate(reqDate);
			masterForm.setLocationId(rs.getString("LOCATION_ID"));
			masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
			masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
			masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
			masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
			masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
			masterForm.setPharmacopName(rs.getString("PHARMACOP_NAME"));
		String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
			masterForm.setPharmacopGrade(pharmacopGrade);
			
			masterForm.setGenericName(rs.getString("GENERIC_NAME"));
			masterForm.setSynonym(rs.getString("SYNONYM"));
			masterForm.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
			String isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
			if(isDMfMaterial.equalsIgnoreCase("1"))
			{
				masterForm.setIsDMFMaterial("True");
				masterForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
				masterForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
				masterForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
				
			}
			masterForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
			if(isDMfMaterial.equalsIgnoreCase("0"))
			{
				masterForm.setIsDMFMaterial("False");
				
				
			}
			
			masterForm.setCountryId(rs.getString("COUNTRY_ID"));
			masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
			masterForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
			
			String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
			if(isVendorStatus.equalsIgnoreCase("1"))
			{
				masterForm.setIsVendorSpecificMaterial("True");
				masterForm.setMfgrName(rs.getString("MFGR_NAME"));
				masterForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
			}
			if(isVendorStatus.equalsIgnoreCase("0"))
			{
				masterForm.setIsVendorSpecificMaterial("False");
			}
			
			masterForm.setTempCondition(rs.getString("TEMP_CONDITION"));
			masterForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
			masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
			String dutyElement=rs.getString("DUTY_ELEMENT");
			if(dutyElement.equalsIgnoreCase("1"))
			{
				masterForm.setDutyElement("True");
			}
			if(dutyElement.equalsIgnoreCase("0"))
				masterForm.setDutyElement("False");
			masterForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
			masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
			masterForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
			masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
			masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
			masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			masterForm.setRequestedBy(rs.getString("REQUESTED_BY"));
			
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
			
				materialForm2.setUploadFilePath(v[i]);
			int x=v[i].lastIndexOf("/");
			uploadedFiles=v[i].substring(x+1);		
			materialForm2.setFileList(uploadedFiles);
			fileList.add(materialForm2);
			}
			request.setAttribute("listName", fileList);
			}
			
			
			int  userId=user.getId();
			if(userId==1)
				{	
			//	masterForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
				}
			if(userId==2)
			{
				String sapCodeno=rs.getString("SAP_CODE_NO");
				if(sapCodeno!=null)
				{
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
					String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
					{
						masterForm.setSapCodeExists("True");
					}
					if(sapCodeExist.equalsIgnoreCase("0"))
						masterForm.setSapCodeExists("False");
					String createDate=rs.getString("SAP_CREATION_DATE");
					String a1[]=createDate.split(" ");
					createDate=a1[0];
					String b1[]=createDate.split("-");
					createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					masterForm.setSapCreationDate(createDate);
					masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				}
				masterForm.setApproveType(rs.getString("Approve_Type"));
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
			}
		}
		
		int  userId=user.getId();
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
			
			String updateMaterial="update material_code_request set Approve_Type='"+masterForm.getApproveType()+"',approve_date='"+currentdate+"',last_approver='"+user.getFullName()+"',pending_approver='No'  where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			i=ad.SqlExecuteUpdate(updateMaterial);
			if(i>0)
			{
				masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'");
				masterForm.setTypeDetails("Update");
			}else{
				masterForm.setMessage("Error...When updating code creation reequest.Please Check");
				masterForm.setTypeDetails("Update");
			} 
		}
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterialCodeLists("RAW MATERIALS");
		
	
		
		return mapping.findForward("approverMaster");
	}
	
	 
	
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws SQLException {
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
			
			 String path="raw_m.jrxml";
			    //String f2="raw_m2.jrxml";
			    String f2="raw_m1.jrxml";
			    
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
				String pharmagrade="";
				String qc="select PHARMACOP_GRADE from material_code_request where REQUEST_NO ='"+requestNo+"'";
				ResultSet rs=ad.selectQuery(qc);
			while(rs.next()){
				String pharma =rs.getString("PHARMACOP_GRADE");
				if(pharma.equalsIgnoreCase("IH ")){
					
					masterForm.setPharmacopGrade("0");
					
				}
                 if(pharma.equalsIgnoreCase("IP ")){
					
					masterForm.setPharmacopGrade("1");
					
				}
                 if(pharma.equalsIgnoreCase("BP ")){
 					
 					masterForm.setPharmacopGrade("2");
 					
 				}
                 if(pharma.equalsIgnoreCase("USP ")){
 					
 					masterForm.setPharmacopGrade("3");
 					
 				}
                 if(pharma.equalsIgnoreCase("EP ")){
 					
 					masterForm.setPharmacopGrade("4");
 					
 				}
                 if(pharma.equalsIgnoreCase("NF ")){
 					
 					masterForm.setPharmacopGrade("5");
 					
 				}
                 if(pharma.equalsIgnoreCase("INT ")){
 					
 					masterForm.setPharmacopGrade("6");
 					
 				}
               
               pharmagrade=masterForm.getPharmacopGrade();

			
			
			}
		
			
			
				
				param_map.put("REQUEST_NO", requestNo);
				param_map.put("PHARMACOP_GRADE", "Rajesh");
				param_map.put("QC Spec", pharmagrade);
				
				
				
				
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
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requestNumber=masterForm.getRequestNumber();
		if(requestNumber.equalsIgnoreCase("Request Number"))
		{
			requestNumber="";
		}
		String locationId=masterForm.getLocationSearch();
		String reqDate=masterForm.getRequestsearchDate();
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
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
		
		LinkedList rawRecordList=new LinkedList();
		String getRecord="";
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Raw Material'";
		
		}else{
		if(reqDate.equalsIgnoreCase("")&&locationId.equalsIgnoreCase("") ){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"'  and l.LOCID=m.LOCATION_ID and m.Type='Raw Material'";
		}
		 if(reqDate.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Raw Material'";
		}
		 if(locationId.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.Location_ID=l.LOCID and m.Type='Raw Material'";
		}
		 if(requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&&!locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Raw Material'";
		}
		 if(reqDate.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Raw Material'";
		}
		if(locationId.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !reqDate.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.REQUEST_DATE='"+reqDate+"' and l.LOCID=m.LOCATION_ID and m.Type='Raw Material'";
		}
		
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Raw Material'";
		
		}
		}
		ResultSet rs=ad.selectQuery(getRecord);
		while(rs.next())
		{
			RawMaterialForm rawRecord=new RawMaterialForm();
			
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
		request.setAttribute("listOfMaterials", rawRecordList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("masterList");
		
		
	}
	
	public ActionForward editMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RawMaterialForm masterForm=(RawMaterialForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new  EssDao();
		String materialType="";
		try{
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			
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
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%RM%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			masterForm.setDmfGradeIDList(dmfGradeIDList);
			masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='RM'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			masterForm.setTempIDList(tempIDList);
			masterForm.setTemValueList(temValueList);
			
			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			masterForm.setStorageIDList(storageIDList);
			masterForm.setStorageLocList(storageLocList);
		
		int requstNo=Integer.parseInt(request.getParameter("requstNo"));
		
		
		
		
		String getMaterial="select * from material_code_request where REQUEST_NO='"+requstNo+"'";
		ResultSet rs=ad.selectQuery(getMaterial);
		while(rs.next())
		{
			masterForm.setRequestNo(requstNo);
			String reqDate=rs.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			masterForm.setRequestDate(reqDate);
			masterForm.setLocationId(rs.getString("LOCATION_ID"));
			masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
			masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
			masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
			masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
			masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
			masterForm.setPharmacopName(rs.getString("PHARMACOP_NAME"));
			String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
			masterForm.setPharmacopGrade(pharmacopGrade);
			
			masterForm.setGenericName(rs.getString("GENERIC_NAME"));
			masterForm.setSynonym(rs.getString("SYNONYM"));
			masterForm.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
			String isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
			System.out.println("isDMfMaterial="+isDMfMaterial.length());
			if(isDMfMaterial.equalsIgnoreCase("1"))
			{
				masterForm.setIsDMFMaterial("1");
				masterForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
				masterForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
				masterForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
				
			}
			masterForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
			if(isDMfMaterial.equalsIgnoreCase("0"))
			{
				masterForm.setIsDMFMaterial("0");
				masterForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
				masterForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
				masterForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
			}
			
			masterForm.setCountryId(rs.getString("COUNTRY_ID"));
			masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
			masterForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
			
			String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
			if(isVendorStatus.equalsIgnoreCase("1"))
			{
				masterForm.setIsVendorSpecificMaterial("1");
				masterForm.setMfgrName(rs.getString("MFGR_NAME"));
				masterForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
			}
			if(isVendorStatus.equalsIgnoreCase("0"))
			{
				masterForm.setIsVendorSpecificMaterial("0");
				masterForm.setMfgrName(rs.getString("MFGR_NAME"));
				masterForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
			}
			
			masterForm.setTempCondition(rs.getString("TEMP_CONDITION"));
			masterForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
			masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
			String dutyElement=rs.getString("DUTY_ELEMENT");
			if(dutyElement.equalsIgnoreCase("0"))
			{
				masterForm.setDutyElement("0");
			}
			if(dutyElement.equalsIgnoreCase("2"))
				masterForm.setDutyElement("2");
			if(dutyElement.equalsIgnoreCase("3"))
				masterForm.setDutyElement("3");
			masterForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
			masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
			masterForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
			masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
			masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
			masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			masterForm.setRequestedBy(rs.getString("REQUESTED_BY"));
			
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
			}
			
			
			int  userId=user.getId();
			if(userId==1)
				{	
				masterForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
				}
			if(userId==2)
			{
				String sapCodeno=rs.getString("SAP_CODE_NO");
				if(sapCodeno!=null)
				{
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
					String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
					{
						masterForm.setSapCodeExists("True");
					}
					if(sapCodeExist.equalsIgnoreCase("0"))
						masterForm.setSapCodeExists("False");
					String createDate=rs.getString("SAP_CREATION_DATE");
					String a1[]=createDate.split(" ");
					createDate=a1[0];
					String b1[]=createDate.split("-");
					createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					masterForm.setSapCreationDate(createDate);
					masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				}
				masterForm.setApproveType(rs.getString("Approve_Type"));
				materialType=rs.getString("Type");
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
			}
		}
		
		masterForm.setTypeDetails("Update");
		
		///MAIL ID OF USER
				String mail="";
				String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
				ResultSet rss=ad.selectQuery(a);
				try {
					if(rss.next())
					{
					mail=rss.getString("EMAIL_ID")	;
					if(!mail.equalsIgnoreCase("null"))
					masterForm.setReqEmail(mail);
				
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterialCodeLists("RAW MATERIALS");
		String forwardType="newMasterForm";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="rawmaterialSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newMasterForm";
		}
		masterForm.setMaterialTypeId("1");
		masterForm.setMaterialCodeLists("1");
		return mapping.findForward(forwardType);
	}
	public ActionForward deleteMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
		int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
		   String deleteRecord="delete from material_code_request where REQUEST_NO='"+requstNo+"'";
		   int i=0;
		   i=ad.SqlExecuteUpdate(deleteRecord);
		   if(i>=1)
		   {
			   masterForm.setMessage2("Raw Material Record Successfully Deleted");
		   }else{
			   masterForm.setMessage("Error.... When Deleting Raw Material.");
		   }
		
		   displayMaterialCodeList(mapping, form, request, response);
	return mapping.findForward("masterList");
	}
	public ActionForward displayMaterialCodeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		RawMaterialForm masterForm=(RawMaterialForm)form;
		HttpSession session=request.getSession();
		EssDao ad=new  EssDao();
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
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);	
			
		LinkedList listOfMaterialCode=new LinkedList();
		String getList="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Approve_Type from material_code_request as m,Location as l where  m.LOCATION_ID=l.LOCID";
		ResultSet rsList=ad.selectQuery(getList);
		while(rsList.next())
		{
			RawMaterialForm materialForm=new RawMaterialForm();
			 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
			 String requestDate=rsList.getString("REQUEST_DATE");
				String req[]=requestDate.split(" ");
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				materialForm.setRequestDate(requestDate);
			materialForm.setLocationId(rsList.getString("LOCNAME"));
			materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
			materialForm.setApproveType(rsList.getString("Approve_Type"));
			materialForm.setRequiredRequestNumber("");
		
			listOfMaterialCode.add(materialForm);
		}
		
		
		request.setAttribute("listOfMaterials", listOfMaterialCode);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("masterList");
	}
	public ActionForward saveMaterialCodeMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int  userId=user.getId();
		String userTextId=Integer.toString(userId);
		try{
			
			String maild=request.getParameter("EMAIL");
			maild=maild.replace("*", "&");

			//insert emial id
			if(!maild.equalsIgnoreCase("null")||!maild.equalsIgnoreCase(""))
			{
			String mail="Update emp_official_info set EMAIL_ID='"+maild+"' where pernr='"+user.getEmployeeNo()+"'";
			ad.SqlExecuteUpdate(mail);
			}
			
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
		String dateNow = ft.format(dNow);
		  String reqDate=masterForm.getRequestDate();
		 	 
		  String a[]=reqDate.split("/");
		  for(int i=0;i<a.length;i++)
		  {
			  System.out.println("a="+a[i]);
		  }
		  reqDate=a[2]+"-"+a[1]+"-"+a[0];
		  String fileList="";
		  try{
			  
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 String uploadFilePath=props.getProperty("file.uploadFilePath");
			 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles");
			 	 String filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles";
			 	 
			 
			 	 
			String getUploadedFiles="select * from UploadMaterial_CodeMaster_Doc where request_no='"+masterForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rs=ad.selectQuery(getUploadedFiles);
			while(rs.next())
			{
				fileList+=filePath+"/"+rs.getString("file_name")+",";
			}
		  }catch (Exception e) {
			e.printStackTrace();
		}
		 
		  if(!fileList.equalsIgnoreCase("")){
		  int count=0;
		  
		  String typeDetails=masterForm.getTypeDetails();
			
		  
		  String phramacopSpecification=masterForm.getPharmacopSpecification();
		  phramacopSpecification=phramacopSpecification+" ";
	String tobeUsedInProducts=masterForm.getToBeUsedInProducts();
	tobeUsedInProducts=tobeUsedInProducts+" ";
	String customername=masterForm.getCustomerName();
	customername=customername+" ";
	String genericName=masterForm.getGenericName();
	  genericName=genericName+" ";
	  masterForm.setGenericName(genericName);
	  String synonyam=masterForm.getSynonym();
	  synonyam=synonyam+" ";
	  masterForm.setSynonym(synonyam);
	  if(typeDetails.equalsIgnoreCase("Save"))
	  {
		  synchronized (this){
			String getcount="select count(*) from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getcount);
			while(rs.next())
			{
				count=rs.getInt(1);
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
			//added for approval task
			if(count>0){	
				//RequestNo is available
				int old_RequestNo=masterForm.getRequestNo();
				String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				masterForm.setRequestNo(maxReqno);
				
					
			  
			  String url="rawMaterial.do?method=editMaterialRecord";
			  String reportURL="rawMaterial.do?method=getReport";
			  //SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY, '"+masterForm.getSapCodeNo()+"','"+masterForm.getSapCodeExists()+"','"+sapCreationDate+"','"+masterForm.getSapCreatedBy()+"'
			  insertQuery="INSERT INTO material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME," +
					"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,PHARMACOP_NAME,PHARMACOP_GRADE,GENERIC_NAME,SYNONYM,PHARMACOP_SPECIFICATION" +
					",IS_DMF_MATERIAL,DMF_GRADE_ID,MATERIAL_GRADE,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME" +
					",TO_BE_USED_IN_PRODUCTS,IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,SHELF_LIFE,SHELF_LIFE_TYPE,DUTY_ELEMENT" +
					",RETEST_DAYS,RETEST_DAYS_TYPE,VALUATION_CLASS,APPROXIMATE_VALUE,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID," +
					"REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,Approve_Type,URL,REPORT_URL,last_approver,pending_approver,Storage,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
							"'"+masterForm.getLocationId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getMaterialShortName()+"'," +
					"'"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPharmacopName()+"','"+masterForm.getPharmacopGrade()+"','"+masterForm.getGenericName()+"','"+masterForm.getSynonym()+"','"+phramacopSpecification+"'" +
					",'"+masterForm.getIsDMFMaterial()+"','"+masterForm.getDmfGradeId()+"','"+masterForm.getMaterialGrade()+"','"+masterForm.getCosGradeNo()+"','"+masterForm.getAdditionalTest()+"','"+masterForm.getCountryId()+"','"+customername+"'" +
					",'"+tobeUsedInProducts+"','"+masterForm.getIsVendorSpecificMaterial()+"','"+masterForm.getMfgrName()+"','"+masterForm.getSiteOfManufacture()+"','"+masterForm.getTempCondition()+"','"+masterForm.getStorageCondition()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfLifeType()+"','"+masterForm.getDutyElement()+"'" +
					",'"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+masterForm.getApproximateValue()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"'," +
					"'"+masterForm.getRequestedBy()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+fileList+"','RM','Created','"+url+"','"+reportURL+"','No','"+pendingApprovers+"','"+masterForm.getStorage()+"','"+masterForm.getHsnCode()+"')";
			
			  
			  i=ad.SqlExecuteUpdate(insertQuery);
			
			  int j=0;
			  j=ad.SqlExecuteUpdate("update UploadMaterial_CodeMaster_Doc set request_no='"+masterForm.getRequestNo()+"' where request_no='"+old_RequestNo+"' and  userId='"+userId+"'");
			
			  if(i>0)
			  {
				masterForm.setMessage2("Code creation request Created with request number='"+masterForm.getRequestNo()+"' ");
				
				String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
		  		ad.SqlExecuteUpdate(deleteHistory);
		  		String	insertHistryQuery="INSERT INTO material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME," +
							"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,PHARMACOP_NAME,PHARMACOP_GRADE,GENERIC_NAME,SYNONYM,PHARMACOP_SPECIFICATION" +
							",IS_DMF_MATERIAL,DMF_GRADE_ID,MATERIAL_GRADE,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME" +
							",TO_BE_USED_IN_PRODUCTS,IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,SHELF_LIFE,SHELF_LIFE_TYPE,DUTY_ELEMENT" +
							",RETEST_DAYS,RETEST_DAYS_TYPE,VALUATION_CLASS,APPROXIMATE_VALUE,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID," +
							"REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,Approve_Type,URL,REPORT_URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
									"'"+masterForm.getLocationId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getMaterialShortName()+"'," +
							"'"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPharmacopName()+"','"+masterForm.getPharmacopGrade()+"','"+masterForm.getGenericName()+"','"+masterForm.getSynonym()+"','"+phramacopSpecification+"'" +
							",'"+masterForm.getIsDMFMaterial()+"','"+masterForm.getDmfGradeId()+"','"+masterForm.getMaterialGrade()+"','"+masterForm.getCosGradeNo()+"','"+masterForm.getAdditionalTest()+"','"+masterForm.getCountryId()+"','"+customername+"'" +
							",'"+tobeUsedInProducts+"','"+masterForm.getIsVendorSpecificMaterial()+"','"+masterForm.getMfgrName()+"','"+masterForm.getSiteOfManufacture()+"','"+masterForm.getTempCondition()+"','"+masterForm.getStorageCondition()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfLifeType()+"','"+masterForm.getDutyElement()+"'" +
							",'"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+masterForm.getApproximateValue()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"'," +
							"'"+user.getEmployeeNo()+"','"+dateNow+"','"+user.getEmployeeNo()+"','','RM','Created','','','user','"+masterForm.getHsnCode()+"')";
				ad.SqlExecuteUpdate(insertHistryQuery);
				
				
				return displayNewMaterialCodeMaster(mapping, form, request, response);
			  }else{
				masterForm.setMessage("Error...When code creation request.Please check");
				masterForm.setTypeDetails("Save");
			  }
			}
			else{
				String url="rawMaterial.do?method=editMaterialRecord";
				String reportURL="rawMaterial.do?method=getReport";
				//SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY, '"+masterForm.getSapCodeNo()+"','"+masterForm.getSapCodeExists()+"','"+sapCreationDate+"','"+masterForm.getSapCreatedBy()+"'
				insertQuery="INSERT INTO material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME," +
					"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,PHARMACOP_NAME,PHARMACOP_GRADE,GENERIC_NAME,SYNONYM,PHARMACOP_SPECIFICATION" +
					",IS_DMF_MATERIAL,DMF_GRADE_ID,MATERIAL_GRADE,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME" +
					",TO_BE_USED_IN_PRODUCTS,IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,SHELF_LIFE,SHELF_LIFE_TYPE,DUTY_ELEMENT" +
					",RETEST_DAYS,RETEST_DAYS_TYPE,VALUATION_CLASS,APPROXIMATE_VALUE,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID," +
					"REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,Approve_Type,URL,REPORT_URL,last_approver,pending_approver,Storage,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
							"'"+masterForm.getLocationId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getMaterialShortName()+"'," +
					"'"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPharmacopName()+"','"+masterForm.getPharmacopGrade()+"','"+masterForm.getGenericName()+"','"+masterForm.getSynonym()+"','"+phramacopSpecification+"'" +
					",'"+masterForm.getIsDMFMaterial()+"','"+masterForm.getDmfGradeId()+"','"+masterForm.getMaterialGrade()+"','"+masterForm.getCosGradeNo()+"','"+masterForm.getAdditionalTest()+"','"+masterForm.getCountryId()+"','"+customername+"'" +
					",'"+tobeUsedInProducts+"','"+masterForm.getIsVendorSpecificMaterial()+"','"+masterForm.getMfgrName()+"','"+masterForm.getSiteOfManufacture()+"','"+masterForm.getTempCondition()+"','"+masterForm.getStorageCondition()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfLifeType()+"','"+masterForm.getDutyElement()+"'" +
					",'"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+masterForm.getApproximateValue()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"'," +
					"'"+masterForm.getRequestedBy()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+fileList+"','RM','Created','"+url+"','"+reportURL+"','No','"+pendingApprovers+"','"+masterForm.getStorage()+"','"+masterForm.getHsnCode()+"')";
				i=0;
				i=ad.SqlExecuteUpdate(insertQuery);
				approve="Pending";
					if(i>0)
				{
					masterForm.setMessage2("Code creation request Created with request number='"+masterForm.getRequestNo()+"' ");
					
					String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
			  		ad.SqlExecuteUpdate(deleteHistory);
			  		String	insertHistryQuery="INSERT INTO material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME," +
								"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,PHARMACOP_NAME,PHARMACOP_GRADE,GENERIC_NAME,SYNONYM,PHARMACOP_SPECIFICATION" +
								",IS_DMF_MATERIAL,DMF_GRADE_ID,MATERIAL_GRADE,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME" +
								",TO_BE_USED_IN_PRODUCTS,IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,SHELF_LIFE,SHELF_LIFE_TYPE,DUTY_ELEMENT" +
								",RETEST_DAYS,RETEST_DAYS_TYPE,VALUATION_CLASS,APPROXIMATE_VALUE,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID," +
								"REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,Approve_Type,URL,REPORT_URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
										"'"+masterForm.getLocationId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getMaterialShortName()+"'," +
								"'"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPharmacopName()+"','"+masterForm.getPharmacopGrade()+"','"+masterForm.getGenericName()+"','"+masterForm.getSynonym()+"','"+phramacopSpecification+"'" +
								",'"+masterForm.getIsDMFMaterial()+"','"+masterForm.getDmfGradeId()+"','"+masterForm.getMaterialGrade()+"','"+masterForm.getCosGradeNo()+"','"+masterForm.getAdditionalTest()+"','"+masterForm.getCountryId()+"','"+customername+"'" +
								",'"+tobeUsedInProducts+"','"+masterForm.getIsVendorSpecificMaterial()+"','"+masterForm.getMfgrName()+"','"+masterForm.getSiteOfManufacture()+"','"+masterForm.getTempCondition()+"','"+masterForm.getStorageCondition()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfLifeType()+"','"+masterForm.getDutyElement()+"'" +
								",'"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+masterForm.getApproximateValue()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"'," +
								"'"+user.getEmployeeNo()+"','"+dateNow+"','"+user.getEmployeeNo()+"','','RM','Created','','','user','"+masterForm.getHsnCode()+"')";
					ad.SqlExecuteUpdate(insertHistryQuery);
					
					
					return displayNewMaterialCodeMaster(mapping, form, request, response);
				}else{
					masterForm.setMessage("Error...When code creation request.Please check");
					masterForm.setTypeDetails("Save");
				}
		
			}
			
			
			
			/*String Req_Id = ""+masterForm.getRequestNo();
			EMailer email = new EMailer();
			i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");
			*/
		
		  }
	  }
	  else{
		  System.out.println("update Material Code");
		  String approvedStatus="Pending";
			
		  user=(UserInfo)session.getAttribute("user");
			if(userId==1)
			{
				approvedStatus=masterForm.getApproveType();
			}
			int i=0;
			
			String recordStatus="";
			String getRecordStatus="select Approve_Type from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"' ";
			ResultSet rsRecord=ad.selectQuery(getRecordStatus);
			while(rsRecord.next())
			{
				recordStatus=rsRecord.getString("Approve_Type");
			}
			
			if(recordStatus.equalsIgnoreCase("Rejected"))
			{
				
				String deleteRecords="delete from All_Request where Req_Id='"+masterForm.getRequestNo()+"' and Req_Type='Material Master'";
				int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
				
				String updateFlag="update material_code_request set rejected_flag='y' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				ad.SqlExecuteUpdate(updateFlag);
			}
			
			String updatQuery="update material_code_request set  REQUEST_DATE='"+reqDate+"',LOCATION_ID='"+masterForm.getLocationId()+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"'," +
				"MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',PHARMACOP_NAME='"+masterForm.getPharmacopName()+"',PHARMACOP_GRADE='"+masterForm.getPharmacopGrade()+"',GENERIC_NAME='"+masterForm.getGenericName()+"',SYNONYM='"+masterForm.getSynonym()+"',PHARMACOP_SPECIFICATION='"+phramacopSpecification+"'" +
				",IS_DMF_MATERIAL='"+masterForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+masterForm.getDmfGradeId()+"',MATERIAL_GRADE='"+masterForm.getMaterialGrade()+"',COS_GRADE_AND_NO='"+masterForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+masterForm.getAdditionalTest()+"',COUNTRY_ID='"+masterForm.getCountryId()+"',CUSTOMER_NAME='"+customername+"'" +
				",TO_BE_USED_IN_PRODUCTS='"+tobeUsedInProducts+"',IS_VENDOR_SPECIFIC_MATERIAL='"+masterForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+masterForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+masterForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+masterForm.getTempCondition()+"',STORAGE_CONDITION='"+masterForm.getStorageCondition()+"',SHELF_LIFE='"+masterForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+masterForm.getShelfLifeType()+"',DUTY_ELEMENT='"+masterForm.getDutyElement()+"'" +
				",RETEST_DAYS='"+masterForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+masterForm.getRetestType()+"',VALUATION_CLASS='"+masterForm.getValuationClass()+"',APPROXIMATE_VALUE='"+masterForm.getApproximateValue()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+masterForm.getPuchaseGroupId()+"'" +
				",REQUESTED_BY='"+masterForm.getRequestedBy()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Attachements='"+fileList+"',Approve_Type='Created',Storage ='"+masterForm.getStorage()+"',HSN_Code='"+masterForm.getHsnCode()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
		  
			i=ad.SqlExecuteUpdate(updatQuery);
			
			String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  		ad.SqlExecuteUpdate(deleteHistory);
	  		String	insertHistryQuery="INSERT INTO material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,STORAGE_LOCATION_ID,MATERIAL_TYPE_ID,MATERIAL_SHORT_NAME," +
						"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,PHARMACOP_NAME,PHARMACOP_GRADE,GENERIC_NAME,SYNONYM,PHARMACOP_SPECIFICATION" +
						",IS_DMF_MATERIAL,DMF_GRADE_ID,MATERIAL_GRADE,COS_GRADE_AND_NO,ADDITIONAL_TEST,COUNTRY_ID,CUSTOMER_NAME" +
						",TO_BE_USED_IN_PRODUCTS,IS_VENDOR_SPECIFIC_MATERIAL,MFGR_NAME,SITE_OF_MANUFACTURE,TEMP_CONDITION,STORAGE_CONDITION,SHELF_LIFE,SHELF_LIFE_TYPE,DUTY_ELEMENT" +
						",RETEST_DAYS,RETEST_DAYS_TYPE,VALUATION_CLASS,APPROXIMATE_VALUE,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID," +
						"REQUESTED_BY,CREATED_DATE,CREATED_BY,Attachements,Type,Approve_Type,URL,REPORT_URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
								"'"+masterForm.getLocationId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getMaterialShortName()+"'," +
						"'"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPharmacopName()+"','"+masterForm.getPharmacopGrade()+"','"+masterForm.getGenericName()+"','"+masterForm.getSynonym()+"','"+phramacopSpecification+"'" +
						",'"+masterForm.getIsDMFMaterial()+"','"+masterForm.getDmfGradeId()+"','"+masterForm.getMaterialGrade()+"','"+masterForm.getCosGradeNo()+"','"+masterForm.getAdditionalTest()+"','"+masterForm.getCountryId()+"','"+customername+"'" +
						",'"+tobeUsedInProducts+"','"+masterForm.getIsVendorSpecificMaterial()+"','"+masterForm.getMfgrName()+"','"+masterForm.getSiteOfManufacture()+"','"+masterForm.getTempCondition()+"','"+masterForm.getStorageCondition()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfLifeType()+"','"+masterForm.getDutyElement()+"'" +
						",'"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+masterForm.getApproximateValue()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"'," +
						"'"+user.getEmployeeNo()+"','"+dateNow+"','"+user.getEmployeeNo()+"','','RM','Created','','','user','"+masterForm.getHsnCode()+"')";
			int i1=ad.SqlExecuteUpdate(insertHistryQuery);
			
			String status=masterForm.getIsVendorSpecificMaterial();
			if(status.equalsIgnoreCase("True")){
				String mfgrName=masterForm.getMfgrName();
				String siteOfManufacture=masterForm.getSiteOfManufacture();
				masterForm.setMfgrName(mfgrName);
				masterForm.setSiteOfManufacture(siteOfManufacture);
			}	
			else
			{
			}
			
			String status1=masterForm.getIsDMFMaterial();
			if(status1.equalsIgnoreCase("True")){
				request.setAttribute("isDmfMaterialMandatory", "isDmfMaterialMandatory");
				String dmfGradeId=masterForm.getDmfGradeId();
				String dmfGrade=masterForm.getMaterialGrade();
				String cosGrade=masterForm.getCosGradeNo();
				masterForm.setDmfGradeId(dmfGradeId);
				masterForm.setMaterialGrade(dmfGrade);
				masterForm.setCosGradeNo(cosGrade);
			}
			else
				request.setAttribute("isDmfMaterialNotMandatory", "isDmfMaterialNotMandatory");
			
			
			
		/*	if(userId==1)
			{
				String updateMaterial="update material_code_request set Approve_Type='"+approvedStatus+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				i=ad.SqlExecuteUpdate(updateMaterial);
			}
			
			if(userId==2)
			{
				 String sapCreationDate=masterForm.getSapCreationDate();
				  String b[]=sapCreationDate.split("/");
				  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
				String updateMaterial="update material_code_request set SAP_CODE_NO='"+masterForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+masterForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+masterForm.getSapCreatedBy()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				i=ad.SqlExecuteUpdate(updateMaterial);
			}*/
			
			if(i>0)
			{
				masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'");
				masterForm.setTypeDetails("Update");
				return displayNewMaterialCodeMaster(mapping, form, request, response);
			}else{
				masterForm.setMessage("Error...When updating code creation reequest.Please Check");
				masterForm.setTypeDetails("Update");
			} 
			
			
		  
	  }
	  
 
	  
	  
  }
  else{
	  masterForm.setMessage("Error...Please Upload File");
	  String typeDetails=masterForm.getTypeDetails();
	  masterForm.setTypeDetails(typeDetails);
	  
	  
	  String status=masterForm.getIsVendorSpecificMaterial();
		if(status.equalsIgnoreCase("True")){
			request.setAttribute("vedorMandatory", "vedorMandatory");
			String mfgrName=masterForm.getMfgrName();
			String siteOfManufacture=masterForm.getSiteOfManufacture();
			masterForm.setMfgrName(mfgrName);
			masterForm.setSiteOfManufacture(siteOfManufacture);
		}	
		else
			request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
		
		String status1=masterForm.getIsDMFMaterial();
		if(status1.equalsIgnoreCase("True")){
			request.setAttribute("isDmfMaterialMandatory", "isDmfMaterialMandatory");
			String dmfGradeId=masterForm.getDmfGradeId();
			String dmfGrade=masterForm.getMaterialGrade();
			String cosGrade=masterForm.getCosGradeNo();
			masterForm.setDmfGradeId(dmfGradeId);
			masterForm.setMaterialGrade(dmfGrade);
			masterForm.setCosGradeNo(cosGrade);
		}
		else
			request.setAttribute("isDmfMaterialNotMandatory", "isDmfMaterialNotMandatory");
		
  }
	  ArrayList list = new ArrayList();
	
	  if(!fileList.equalsIgnoreCase(""))
	  {  
	  String getUplodedFiles="select *  from material_code_request where request_no='"+masterForm.getRequestNo()+"'";
		ResultSet rs5 = ad.selectQuery(getUplodedFiles);
			
		while (rs5.next()) {
					RawMaterialForm custForm1 = new RawMaterialForm();
					//custForm1.setUrl(url);
					
					custForm1.setFileList(rs5.getString("Attachements"));
					list.add(custForm1);
				}
				request.setAttribute("listName", list);
				
	  }	
	
	  String getCountryDetails="select * from Country";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
		}
		masterForm.setCounID(countryID);
		masterForm.setCountryName(countryName);
		
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
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		masterForm.setStorageID(storageID);
		masterForm.setStorageIDName(storageName);
		
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("id"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
		}
		masterForm.setMaterTypeIDList(materTypeIDList);
		masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%RM%' order by STXT";
		ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
		while(rsMaterialGroup.next())
		{
			materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
		}
		masterForm.setMaterGroupIDList(materGroupIDList);
		masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
		
		LinkedList unitOfMeasIdList=new LinkedList();
		LinkedList unitOfMeasIdValues=new LinkedList();
		
		String getunitMesurement="select * from UNIT_MESUREMENT";
		ResultSet rsUnit=ad.selectQuery(getunitMesurement);
		while(rsUnit.next())
		{
			unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
			unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
		}
		masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
		masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
		
		LinkedList puchaseGroupIdList=new LinkedList();
		LinkedList puchaseGroupIdValues=new LinkedList();
		
		String getPurchaseGroup="select * from PURCHASE_GROUP";
		ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
		while(rsPurchaseGroup.next())
		{
			puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
			puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
		}
		masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
		masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
		
		LinkedList dmfGradeIDList=new LinkedList();
		LinkedList dmfGradeIDValueList=new LinkedList();
		
		String getDMFValues="select * from DMF_GRADE";
		ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
		while(rsDMFValues.next())
		{
			dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
			dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
		}
		masterForm.setDmfGradeIDList(dmfGradeIDList);
		masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
		
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		
		String getValuation="select * from VALUATION_CLASS where MAT_TYPE='RM'";
		ResultSet rsValuation=ad.selectQuery(getValuation);
		while(rsValuation.next())
		{
			valuationClassID.add(rsValuation.getString("VALUATION_ID"));
			valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
		}
		masterForm.setValuationClassID(valuationClassID);
		masterForm.setValuationClassName(valuationClassName);
		
		LinkedList tempIDList=new LinkedList();
		LinkedList temValueList=new LinkedList();
		
		String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
		ResultSet rsTemp=ad.selectQuery(getTemp);
		while(rsTemp.next())
		{
			tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
			temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
		}
		masterForm.setTempIDList(tempIDList);
		masterForm.setTemValueList(temValueList);
		

		LinkedList storageIDList=new LinkedList();
		LinkedList storageLocList=new LinkedList();
		
		String getStorageLoc="select * from STORAGE_CONDITION";
		ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
		while(rsStorageLoc.next())
		{
			storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
			storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
		}
		masterForm.setStorageIDList(storageIDList);
		masterForm.setStorageLocList(storageLocList);

		//set Approved Field
		user=(UserInfo)session.getAttribute("user");
		if(userId==1)
		{	
			request.setAttribute("approved", "approved");
		}
		if(userId==2)
		{	
			request.setAttribute("approved", "approved");
			request.setAttribute("sapApprover", "sapApprover");
		}
		
		 
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		masterForm.setMaterialCodeLists("RAW MATERIALS");
		String forwardType="";
		
		masterForm.setMaterialTypeId("1");
		masterForm.setMaterialCodeLists("1");
		return mapping.findForward("newMasterForm");
	}
	
	
	public ActionForward deleteFileListModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
		try{
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			String  userId=user.getEmployeeNo();
		String checkedValues=request.getParameter("cValues");
		String UncheckedValues=request.getParameter("unValues");
	
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String v[] = checkedValues.split(",");
		
		InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles");
	 	 String filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles";
		for(int i=0;i<v.length;i++)
		{
		String deleteQuery="delete from UploadMaterial_CodeMaster_Doc where file_name='"+v[i]+"' and request_no='"+masterForm.getRequestNo()+"' and userId='"+userId+"'";
		int j=ad.SqlExecuteUpdate(deleteQuery);
			if(j>0)
			{
				 	File fileToCreate = new File(filePath, v[i]);
				 	boolean test=fileToCreate.delete();
				 	System.out.println(test);
				 	
				 	//delete file in another path
				 	
				 	File fileToCreate1 = new File("E:/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles", v[i]);
				 	boolean test1=fileToCreate1.delete();
			}
		}
		ArrayList list = new ArrayList();
		ResultSet rs5 = ad.selectQuery("select *  from UploadMaterial_CodeMaster_Doc where request_no='"+masterForm.getRequestNo()+"' and userId='"+userId+"' ");
				
		while (rs5.next()) {
					RawMaterialForm custForm1 = new RawMaterialForm();
					String s=rs5.getString("file_name");
					custForm1.setFileList(rs5.getString("file_name"));
					String filePath1 = uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles/"+rs5.getString("file_name")+"";
					custForm1.setUploadFilePath(filePath1);
					list.add(custForm1);
				}
		request.setAttribute("listName", list);
		masterForm.setUploadFileStatus("Yes");
		int a=0;
		
		if(list.size()>0)
		{
				}else{
					masterForm.setUploadFileStatus("No");
				}
		
		String getCountryDetails="select * from Country";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
		}
		masterForm.setCounID(countryID);
		masterForm.setCountryName(countryName);
		
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
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		masterForm.setStorageID(storageID);
		masterForm.setStorageIDName(storageName);
		
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("id"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
		}
		masterForm.setMaterTypeIDList(materTypeIDList);
		masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%RM%' order by STXT";
		ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
		while(rsMaterialGroup.next())
		{
			materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
		}
		masterForm.setMaterGroupIDList(materGroupIDList);
		masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
		
		LinkedList unitOfMeasIdList=new LinkedList();
		LinkedList unitOfMeasIdValues=new LinkedList();
		
		String getunitMesurement="select * from UNIT_MESUREMENT";
		ResultSet rsUnit=ad.selectQuery(getunitMesurement);
		while(rsUnit.next())
		{
			unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
			unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
		}
		masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
		masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
		
		LinkedList puchaseGroupIdList=new LinkedList();
		LinkedList puchaseGroupIdValues=new LinkedList();
		
		String getPurchaseGroup="select * from PURCHASE_GROUP";
		ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
		while(rsPurchaseGroup.next())
		{
			puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
			puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
		}
		masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
		masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
		
		LinkedList dmfGradeIDList=new LinkedList();
		LinkedList dmfGradeIDValueList=new LinkedList();
		
		String getDMFValues="select * from DMF_GRADE";
		ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
		while(rsDMFValues.next())
		{
			dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
			dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
		}
		masterForm.setDmfGradeIDList(dmfGradeIDList);
		masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
		
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		
		String getValuation="select * from VALUATION_CLASS where MAT_TYPE='RM'";
		ResultSet rsValuation=ad.selectQuery(getValuation);
		while(rsValuation.next())
		{
			valuationClassID.add(rsValuation.getString("VALUATION_ID"));
			valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
		}
		masterForm.setValuationClassID(valuationClassID);
		masterForm.setValuationClassName(valuationClassName);
		
		LinkedList tempIDList=new LinkedList();
		LinkedList temValueList=new LinkedList();
		String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
		ResultSet rsTemp=ad.selectQuery(getTemp);
		while(rsTemp.next())
		{
			tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
			temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
		}
		masterForm.setTempIDList(tempIDList);
		masterForm.setTemValueList(temValueList);
		
		LinkedList storageIDList=new LinkedList();
		LinkedList storageLocList=new LinkedList();
		String getStorageLoc="select * from STORAGE_CONDITION";
		ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
		while(rsStorageLoc.next())
		{
			storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
			storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
		}
		masterForm.setStorageIDList(storageIDList);
		masterForm.setStorageLocList(storageLocList);
		
		///MAIL ID OF USER
				String mail="";
				String a1="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
				ResultSet rss=ad.selectQuery(a1);
				try {
					if(rss.next())
					{
					mail=rss.getString("EMAIL_ID")	;
					if(!mail.equalsIgnoreCase("null"))
					masterForm.setReqEmail(mail);
				
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
		String status=masterForm.getIsVendorSpecificMaterial();
		if(status.equalsIgnoreCase("True")){
			request.setAttribute("vedorMandatory", "vedorMandatory");
			String mfgrName=masterForm.getMfgrName();
			String siteOfManufacture=masterForm.getSiteOfManufacture();
			masterForm.setMfgrName(mfgrName);
			masterForm.setSiteOfManufacture(siteOfManufacture);
			
		}
		else
			request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
		
		String status1=masterForm.getIsDMFMaterial();
		
		if(status1.equalsIgnoreCase("True"))
		{
			request.setAttribute("isDmfMaterialMandatory", "isDmfMaterialMandatory");
		String dmfGradeId=masterForm.getDmfGradeId();
		String dmfGrade=masterForm.getMaterialGrade();
		String cosGrade=masterForm.getCosGradeNo();
		masterForm.setDmfGradeId(dmfGradeId);
		masterForm.setMaterialGrade(dmfGrade);
		masterForm.setCosGradeNo(cosGrade);
		}
		else
			request.setAttribute("isDmfMaterialNotMandatory", "isDmfMaterialNotMandatory");	
		
		String typeDetails=masterForm.getTypeDetails();
		masterForm.setTypeDetails(typeDetails);
		masterForm.setMaterialTypeId("1");
		masterForm.setMaterialCodeLists("1");
		return mapping.findForward("newMasterForm");
	}
	
	
	public ActionForward uploadFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
			try{
				HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				String  userId=user.getEmployeeNo();
				EssDao adlinks=new EssDao();	
					FormFile myFile = masterForm.getFileNames();
				    String contentType = myFile.getContentType();
					String fileName = myFile.getFileName();
					int filesize=myFile.getFileSize();
					System.out.println(filesize);
					String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
					String filePath="";
					
					if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
					{
					
					
					
					String sql9="select count(*) from UploadMaterial_CodeMaster_Doc where  file_name='"+fileName+"' ";
					ResultSet rs15 = adlinks.selectQuery(sql9);
					int fileCount=0;
					while (rs15.next())
					{
						fileCount=Integer.parseInt(rs15.getString(1));
					}
					 filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles");
					
					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 	 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	 filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles";
				 	 
					if(fileCount>0)
					{
						masterForm.setMessage("File name is aleardy exist.Please change the file name.");
					}
					else
					{
						
						byte[] fileData = myFile.getFileData();
					
						
						masterForm.setUploadFilePath(filePath);
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
							String filePath1 = "E:/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles";
							
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
					String insertsql = "insert into UploadMaterial_CodeMaster_Doc(file_path,file_name,request_no,userId) values('"+filePath+"','"+fileName+"','"+masterForm.getRequestNo()+"','"+userId+"')";
					int a = adlinks.SqlExecuteUpdate(insertsql);
					if (a > 0)
					{
						masterForm.setMessage2("Documents uploaded successfully");
						
					}else {
						masterForm.setMessage("Error while uploading files ... Please check entered Values");
					}
				
					}
					
					}else{
						masterForm.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
						
						
							}
					
					
					ArrayList list = new ArrayList();
					ResultSet rs5 = adlinks.selectQuery("select *  from UploadMaterial_CodeMaster_Doc where request_no='"+masterForm.getRequestNo()+"' and userId='"+userId+"'");
					
					while (rs5.next()) {
								
								RawMaterialForm custForm1 = new RawMaterialForm();
								//custForm1.setUrl(url);
								String s=rs5.getString("file_name");
								custForm1.setFileList(rs5.getString("file_name"));
								//String filePath1 = getServlet().getServletContext().getRealPath("jsp/ess/sapMasterRequest/Raw Materials Files/UploadFiles/"+rs5.getString("file_name")+"");
								filePath="/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles"+"/"+rs5.getString("file_name");
								
								custForm1.setUploadFilePath(filePath);
								list.add(custForm1);
							}
							request.setAttribute("listName", list);
							masterForm.setUploadFileStatus("Yes");
							
					
							
							String getCountryDetails="select * from Country";
							LinkedList countryID=new LinkedList();
							LinkedList countryName=new LinkedList();
							ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
							while(rsCountryDetails.next()){
								countryID.add(rsCountryDetails.getString("LAND1"));
								countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
							}
							masterForm.setCounID(countryID);
							masterForm.setCountryName(countryName);
							
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
							masterForm.setLocationIdList(locationList);
							masterForm.setLocationLabelList(locationLabelList);
							
							
							LinkedList storageID=new LinkedList();
							LinkedList storageName=new LinkedList();
							String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM'";
							ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
							while(rsStrogeLocation.next()){
								storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
								storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
							}
							masterForm.setStorageID(storageID);
							masterForm.setStorageIDName(storageName);
							
							LinkedList materTypeIDList=new LinkedList();
							LinkedList materialTypeIdValueList=new LinkedList();
							String getMaterials="select * from MATERIAL_TYPE";
							ResultSet rsMaterial=ad.selectQuery(getMaterials);
							while(rsMaterial.next())
							{
								materTypeIDList.add(rsMaterial.getString("id"));
								materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
							}
							masterForm.setMaterTypeIDList(materTypeIDList);
							masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
							
							LinkedList materGroupIDList=new LinkedList();
							LinkedList materialGroupIdValueList=new LinkedList();
							
							String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%RM%'  order by STXT";
							ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
							while(rsMaterialGroup.next())
							{
								materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
								materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							}
							masterForm.setMaterGroupIDList(materGroupIDList);
							masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
							
							LinkedList unitOfMeasIdList=new LinkedList();
							LinkedList unitOfMeasIdValues=new LinkedList();
							
							String getunitMesurement="select * from UNIT_MESUREMENT";
							ResultSet rsUnit=ad.selectQuery(getunitMesurement);
							while(rsUnit.next())
							{
								unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
								unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
							}
							masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
							masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
							
							LinkedList puchaseGroupIdList=new LinkedList();
							LinkedList puchaseGroupIdValues=new LinkedList();
							
							String getPurchaseGroup="select * from PURCHASE_GROUP";
							ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
							while(rsPurchaseGroup.next())
							{
								puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
								puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
							}
							masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
							masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
							
							LinkedList dmfGradeIDList=new LinkedList();
							LinkedList dmfGradeIDValueList=new LinkedList();
							
							String getDMFValues="select * from DMF_GRADE";
							ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
							while(rsDMFValues.next())
							{
								dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
								dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
							}
							masterForm.setDmfGradeIDList(dmfGradeIDList);
							masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
							
							
							LinkedList valuationClassID=new LinkedList();
							LinkedList valuationClassName=new LinkedList();
							
							String getValuation="select * from VALUATION_CLASS where MAT_TYPE='RM'";
							ResultSet rsValuation=ad.selectQuery(getValuation);
							while(rsValuation.next())
							{
								valuationClassID.add(rsValuation.getString("VALUATION_ID"));
								valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
							}
							masterForm.setValuationClassID(valuationClassID);
							masterForm.setValuationClassName(valuationClassName);
							LinkedList tempIDList=new LinkedList();
							LinkedList temValueList=new LinkedList();
							
							
							String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
							ResultSet rsTemp=ad.selectQuery(getTemp);
							while(rsTemp.next())
							{
								tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
								temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
							}
							masterForm.setTempIDList(tempIDList);
							masterForm.setTemValueList(temValueList);
							
							LinkedList storageIDList=new LinkedList();
							LinkedList storageLocList=new LinkedList();
							String getStorageLoc="select * from STORAGE_CONDITION";
							ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
							while(rsStorageLoc.next())
							{
								storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
								storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
							}
							masterForm.setStorageIDList(storageIDList);
							masterForm.setStorageLocList(storageLocList);
				
							///MAIL ID OF USER
							String mail="";
							String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
							ResultSet rss=ad.selectQuery(a);
							try {
								if(rss.next())
								{
								mail=rss.getString("EMAIL_ID")	;
								if(!mail.equalsIgnoreCase("null"))
								masterForm.setReqEmail(mail);
							
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
				String status=masterForm.getIsVendorSpecificMaterial();
				if(status.equalsIgnoreCase("True")){
					request.setAttribute("vedorMandatory", "vedorMandatory");
					String mfgrName=masterForm.getMfgrName();
					String siteOfManufacture=masterForm.getSiteOfManufacture();
					masterForm.setMfgrName(mfgrName);
					masterForm.setSiteOfManufacture(siteOfManufacture);
				}	
				else
					request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
				
				String status1=masterForm.getIsDMFMaterial();
				if(status1.equalsIgnoreCase("True")){
					request.setAttribute("isDmfMaterialMandatory", "isDmfMaterialMandatory");
					String dmfGradeId=masterForm.getDmfGradeId();
					String dmfGrade=masterForm.getMaterialGrade();
					String cosGrade=masterForm.getCosGradeNo();
					masterForm.setDmfGradeId(dmfGradeId);
					masterForm.setMaterialGrade(dmfGrade);
					masterForm.setCosGradeNo(cosGrade);
				}
				else
					request.setAttribute("isDmfMaterialNotMandatory", "isDmfMaterialNotMandatory");
				
					
				String typeDetails=masterForm.getTypeDetails();
				masterForm.setTypeDetails(typeDetails);
				masterForm.setMaterialTypeId("1");
				masterForm.setMaterialCodeLists("1");

				return mapping.findForward("newMasterForm");
			}
	

	public ActionForward displayNewMaterialCodeMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		RawMaterialForm masterForm=(RawMaterialForm)form;
		EssDao ad=new  EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		try{
			String getCountryDetails="select * from Country order by LANDX ";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			masterForm.setCountryId("IN");
			
			
			String userName=user.getFirstName()+" "+user.getMiddleName()+" "+user.getLastName();
			masterForm.setRequestedBy(userName);
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
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='RM' ";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE ";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP  where MATERIAL_TYPE like '%RM%'  order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			
			LinkedList puchaseGroupIdList=new LinkedList();
			LinkedList puchaseGroupIdValues=new LinkedList();
			
			String getPurchaseGroup="select * from PURCHASE_GROUP";
			ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
			while(rsPurchaseGroup.next())
			{
				puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList dmfGradeIDList=new LinkedList();
			LinkedList dmfGradeIDValueList=new LinkedList();
			
			String getDMFValues="select * from DMF_GRADE";
			ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
			while(rsDMFValues.next())
			{
				dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
				dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
			}
			masterForm.setDmfGradeIDList(dmfGradeIDList);
			masterForm.setDmfGradeIDValueList(dmfGradeIDValueList);
			
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='1')";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			LinkedList tempIDList=new LinkedList();
			LinkedList temValueList=new LinkedList();
			
			String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
			ResultSet rsTemp=ad.selectQuery(getTemp);
			while(rsTemp.next())
			{
				tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
				temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
			}
			masterForm.setTempIDList(tempIDList);
			masterForm.setTemValueList(temValueList);
			

			LinkedList storageIDList=new LinkedList();
			LinkedList storageLocList=new LinkedList();
			
			String getStorageLoc="select * from STORAGE_CONDITION";
			ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
			while(rsStorageLoc.next())
			{
				storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
				storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
			}
			masterForm.setStorageIDList(storageIDList);
			masterForm.setStorageLocList(storageLocList);
			
			
			
			
			String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			
			String locationId=request.getParameter("locationId");
			String materialGrup=request.getParameter("materialGrup");
			String shortName=request.getParameter("shortName");
			String longName=request.getParameter("longName");
			
			masterForm.setRequestNo(maxReqno);
			masterForm.setRequestDate(EMicroUtils.getCurrentSysDate());
			masterForm.setLocationId(locationId);
			//Reset Form Values
			masterForm.setMaterialCodeLists("1");
			masterForm.setMaterialCodeLists1("");
			masterForm.setStorageLocationId(locationId);
			masterForm.setMaterialTypeId("1");
			masterForm.setMaterialShortName(shortName); 
			masterForm.setMaterialLongName(longName);
			masterForm.setMaterialGroupId(materialGrup);
			masterForm.setPharmacopName("");
			masterForm.setPharmacopGrade("");
			masterForm.setGenericName("");
			masterForm.setSynonym("");
			masterForm.setPharmacopSpecification("");
			masterForm.setIsDMFMaterial("");
			masterForm.setDmfGradeId("");
			masterForm.setMaterialGrade("");
			masterForm.setCosGradeNo("");
			masterForm.setAdditionalTest("");
			
			masterForm.setCustomerName("");
			masterForm.setToBeUsedInProducts("");
			masterForm.setIsVendorSpecificMaterial("");
			masterForm.setMfgrName("");
			masterForm.setSiteOfManufacture("");
			masterForm.setShelfLife("");
			masterForm.setShelfLifeType("");
			masterForm.setDutyElement("");
			masterForm.setDutyElement("");
			masterForm.setTempCondition("");
			masterForm.setStorageCondition("");
			masterForm.setRetestDays("");
			masterForm.setRetestType("");
			masterForm.setValuationClass("");
			masterForm.setApproximateValue("");
			masterForm.setUnitOfMeasId("");
			masterForm.setPuchaseGroupId("");
			masterForm.setSapCodeNo("");
			masterForm.setSapCodeExists("");
			masterForm.setSapCreationDate("");
			masterForm.setSapCreatedBy("");
			masterForm.setUploadFileStatus("No");
			
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		masterForm.setTypeDetails("Save");
		request.setAttribute("isDmfMaterialNotMandatory", "isDmfMaterialNotMandatory");
		request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
		
		///MAIL ID OF USER
				String mail="";
				String a="Select EMAIL_ID from emp_official_info where pernr='"+user.getEmployeeNo()+"'";
				ResultSet rss=ad.selectQuery(a);
				try {
					if(rss.next())
					{
					mail=rss.getString("EMAIL_ID")	;
					if(!mail.equalsIgnoreCase("null"))
					masterForm.setReqEmail(mail);
				
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
		
		
		return mapping.findForward("newMasterForm");
	}
	
	
	/*Code for code creation after approvals*/
	public int updateCode(LinkedList codeDetails) {
		int i =0;
		EssDao ad=new  EssDao();
		String codeExists = "";
		String codeNo ="";
		String codeCD = "";
		String codeCB = "";
		String reqId= "";
		if(!codeDetails.isEmpty()){
			codeExists = codeDetails.get(0).toString();
			codeNo = codeDetails.get(1).toString();
			codeCD = codeDetails.get(2).toString();
			codeCB = codeDetails.get(3).toString();
			reqId = codeDetails.get(4).toString();
			String b[]=codeCD.split("/");
			codeCD=b[2]+"-"+b[1]+"-"+b[0];
			String updateMaterial="update material_code_request set SAP_CODE_NO='"+codeNo+"',SAP_CODE_EXISTS='"+codeExists+"',SAP_CREATION_DATE='"+codeCD+"',SAP_CREATED_BY='"+codeCB+"' where REQUEST_NO='"+reqId+"'";
			i=ad.SqlExecuteUpdate(updateMaterial);
		}
		return i;
	}
	
}
