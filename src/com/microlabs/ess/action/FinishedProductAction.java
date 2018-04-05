package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.MaterialmasterDAO;
import com.microlabs.ess.form.CustomerMasterForm;
import com.microlabs.ess.form.FinishedProductForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class FinishedProductAction extends DispatchAction{
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
		LinkedList apprList=new LinkedList();
		MaterialmasterDAO dao=new MaterialmasterDAO();
		String location=request.getParameter("locationId");
		String matGroup=request.getParameter("productId");
		String materialTypeId=request.getParameter("materialTypeId");
		
 		
 		if(matGroup.equals("D"))
 			matGroup="Domestic";
 		if(matGroup.equals("E"))
 			matGroup="Export";
 		if(matGroup.equals("V"))
 			matGroup="V";
 		
		/*String getMaterial="select MATERIAL_GROUP_ID from MATERIAL_TYPE where (id='"+materialTypeId+"' or MATERIAL_GROUP_ID='"+materialTypeId+"')";
		ResultSet rs=ad.selectQuery(getMaterial);
		try{
			while(rs.next()){
				materialTypeId=rs.getString("MATERIAL_GROUP_ID");
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
	
		apprList=dao.approversList(location, materialTypeId, matGroup);
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
		return mapping.findForward("apprList");
	}
	public ActionForward saveAndSubmitMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
		try{
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
			
			
			///Brand name text	 
			if(!masterForm.getBrandIDtxt().equalsIgnoreCase(""))
			{
				int maxtcount=0;
				String newbrandid="";
				
				String abc="Select * from  BRAND where BRAND_DESC='"+masterForm.getBrandIDtxt()+"'";
				ResultSet rsabc=ad.selectQuery(abc);
				if(rsabc.next())
				{
					masterForm.setBrandID(rsabc.getString("BRAND_CODE"));
				}
				else
				{
				String getbrandcount="select count(*) from BRAND";
				ResultSet rsbrand1=ad.selectQuery(getbrandcount);
				while(rsbrand1.next())
				{
					maxtcount=rsbrand1.getInt(1);
					maxtcount=maxtcount+1;
					newbrandid=Integer.toString(maxtcount);
				}
				
        String savebrand="insert into BRAND(BRAND_CODE,BRAND_DESC)values('"+newbrandid+"',UPPER('"+masterForm.getBrandIDtxt()+"'))" ;		
	    int	ij=ad.SqlExecuteUpdate(savebrand);
	    if(ij>0)
	    {
	    	masterForm.setBrandID(newbrandid);
	    }
				
			}
			}
			
			
			
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
		String dateNow = ft.format(dNow);
			  String reqDate=masterForm.getRequestDate();
			  String a[]=reqDate.split("/");
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			  /*String sapCreationDate=masterForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];*/
			String manufacturedAt="";
			manufacturedAt=masterForm.getManufacturedAt();
			
			String typeDetails=masterForm.getTypeDetails();
			String materialType=masterForm.getMaterialTypeId();
			 if(materialType.equalsIgnoreCase("4"))
			 {
				 materialType="FG";
			 }
			 else{
				 materialType="HAWA";
			 }
			
			 int checkApprover=0;
			 String productType=masterForm.getDomesticOrExports();
				
		 		
		 		if(productType.equals("D"))
		 			productType="Domestic";
		 		if(productType.equals("E"))
		 			productType="Export";
		 		if(productType.equals("V"))
		 			productType="V";
			  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='"+materialType+"' and " +
		  		" Location=(select LOCATION_CODE from Location where LOCID='"+masterForm.getLocationId()+"') and Material_Group='"+productType+"'";
			  ResultSet rsCheck=ad.selectQuery(checkApprovers);
			  while(rsCheck.next())
			  {
				  checkApprover=1;
			  }
			  if(checkApprover==0)
			  {
			  checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='"+materialType+"' and " +
				  		" Location=(select LOCATION_CODE from Location where LOCID='"+masterForm.getLocationId()+"') and Material_Group=''";
					   rsCheck=ad.selectQuery(checkApprovers);
					  while(rsCheck.next())
					  {
						  checkApprover=1;
					  }
			  }
			  
			if(typeDetails.equalsIgnoreCase("Save"))
			 {
				
				int count=0;
				String getcount="select count(*) from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				ResultSet rs1=ad.selectQuery(getcount);
				while(rs1.next())
				{
					count=rs1.getInt(1);
				}
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
				 if(count>0)
				 {
				//Request no is available
				int oldRequestNo=masterForm.getRequestNo();
				String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				masterForm.setRequestNo(maxReqno);
					 
				
					 
				 String url="finishedProduct.do?method=editFinishedProdut";
				 String approve="Pending";
				 String saveFinishedProduct="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,MANUFACTURED_AT,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
					",MATERIAL_GROUP_ID,VALUATION_CLASS,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,SALEABLE_OR_SAMPLE,DOMESTIC_OR_EXPORTS,SALES_PACK_ID,PACK_TYPE_ID,SALES_UNIT_OF_MEAS_ID,DIVISION_ID,THERAPEUTIC_SEGMENT_ID," +
					"BRAND_ID,STRENGTH_ID,GENERIC_NAME,PROD_INSP_MEMO,GROSS_WEIGHT,NET_WEIGHT,WEIGHT_UOM,DIMENSION,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,CREATED_DATE,CREATED_BY," +
					"Type,Approve_Type,URL,last_approver,pending_approver,Tax_Classification,Material_Pricing,Storage,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+manufacturedAt+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getValuationClass()+"','"+masterForm.getCountryId()+"'" +
					",'"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getSaleableOrSample()+"','"+masterForm.getDomesticOrExports()+"','"+masterForm.getSalesPackId()+"','"+masterForm.getPackTypeId()+"','"+masterForm.getSalesUnitOfMeaseurement()+"','"+masterForm.getDivisionId()+"','"+masterForm.getTherapeuticSegmentID()+"','"+masterForm.getBrandID()+"'" +
					",'"+masterForm.getSrengthId()+"','"+masterForm.getGenericName()+"','"+masterForm.getProdInspMemo()+"','"+masterForm.getGrossWeight()+"','"+masterForm.getNetWeight()+"','"+masterForm.getWeightUOM()+"','"+masterForm.getDimension()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getTaxClassification()+"','"+masterForm.getMaterialPricing()+"','"+masterForm.getStorage()+"','"+masterForm.getHsnCode()+"')";
					i=ad.SqlExecuteUpdate(saveFinishedProduct);
					if(i>0)
					{
						masterForm.setMessage2("Alert Code creation request saved with New request number='"+masterForm.getRequestNo()+"'.");
						masterForm.setTypeDetails("Update");
						
						if(checkApprover==0)
						{
							masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
						return displayNewFinishedProduct(mapping, form, request, response);
						}
					}else{
						masterForm.setMessage("Error...When saving finished products.Please check....");
						typeDetails=masterForm.getTypeDetails();
						masterForm.setTypeDetails(typeDetails);
						
					}
					
				 }else{
					 
					 String url="finishedProduct.do?method=editFinishedProdut";
					 String approve="Pending";
					 String saveFinishedProduct="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,MANUFACTURED_AT,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
						",MATERIAL_GROUP_ID,VALUATION_CLASS,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,SALEABLE_OR_SAMPLE,DOMESTIC_OR_EXPORTS,SALES_PACK_ID,PACK_TYPE_ID,SALES_UNIT_OF_MEAS_ID,DIVISION_ID,THERAPEUTIC_SEGMENT_ID," +
						"BRAND_ID,STRENGTH_ID,GENERIC_NAME,PROD_INSP_MEMO,GROSS_WEIGHT,NET_WEIGHT,WEIGHT_UOM,DIMENSION,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,CREATED_DATE,CREATED_BY," +
						"Type,Approve_Type,URL,last_approver,pending_approver,Tax_Classification,Material_Pricing,Storage,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+manufacturedAt+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getValuationClass()+"','"+masterForm.getCountryId()+"'" +
						",'"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getSaleableOrSample()+"','"+masterForm.getDomesticOrExports()+"','"+masterForm.getSalesPackId()+"','"+masterForm.getPackTypeId()+"','"+masterForm.getSalesUnitOfMeaseurement()+"','"+masterForm.getDivisionId()+"','"+masterForm.getTherapeuticSegmentID()+"','"+masterForm.getBrandID()+"'" +
						",'"+masterForm.getSrengthId()+"','"+masterForm.getGenericName()+"','"+masterForm.getProdInspMemo()+"','"+masterForm.getGrossWeight()+"','"+masterForm.getNetWeight()+"','"+masterForm.getWeightUOM()+"','"+masterForm.getDimension()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getTaxClassification()+"','"+masterForm.getMaterialPricing()+"','"+masterForm.getStorage()+"','"+masterForm.getHsnCode()+"')";
						i=ad.SqlExecuteUpdate(saveFinishedProduct);
							if(i>0)
						{
							masterForm.setMessage2("Code creation request saved with request number='"+masterForm.getRequestNo()+"'.");
							masterForm.setTypeDetails("Update");
							if(checkApprover==0)
							{
								masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
							return displayNewFinishedProduct(mapping, form, request, response);
							}
						}else{
							masterForm.setMessage("Error...When saving finished products.Please check....");
							typeDetails=masterForm.getTypeDetails();
							masterForm.setTypeDetails(typeDetails);
						}
					 
				 }
				/* String Req_Id = ""+masterForm.getRequestNo();
				 EMailer email = new EMailer();
				 i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");
			*/	 
			 }else{
				 System.out.println("update Finished Products");
				 String approvedStatus="Pending";
					
				
					/*if(user.getId()==1)
					{
						approvedStatus=masterForm.getApproveType();
					}
					if(user.getId()==2)
					{
						 String sapCreationDate=masterForm.getSapCreationDate();
						  String b[]=sapCreationDate.split("/");
						  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
						String updateMaterial="update material_code_request set SAP_CODE_NO='"+masterForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+masterForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+masterForm.getSapCreatedBy()+"',REQUESTED_BY='"+masterForm.getRequestedBy()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
						int j=ad.SqlExecuteUpdate(updateMaterial);
						request.setAttribute("approved", "approved");
						request.setAttribute("sapApprover", "sapApprover");
						
					}*/
				 
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
						int j=ad.SqlExecuteUpdate(updateFlag);
						System.out.println("j="+j);
					}
				 		String updateFinishedProduct="update material_code_request set LOCATION_ID='"+masterForm.getLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',MANUFACTURED_AT='"+manufacturedAt+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"'" +
						",MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',VALUATION_CLASS='"+masterForm.getValuationClass()+"',COUNTRY_ID='"+masterForm.getCountryId()+"',CUSTOMER_NAME='"+masterForm.getCustomerName()+"',SHELF_LIFE='"+masterForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+masterForm.getShelfType()+"',STANDARD_BATCH_SIZE='"+masterForm.getStandardBatchSize()+"',BATCH_CODE='"+masterForm.getBatchCode()+"',SALEABLE_OR_SAMPLE='"+masterForm.getSaleableOrSample()+"',DOMESTIC_OR_EXPORTS='"+masterForm.getDomesticOrExports()+"',SALES_PACK_ID='"+masterForm.getSalesPackId()+"',PACK_TYPE_ID='"+masterForm.getPackTypeId()+"',SALES_UNIT_OF_MEAS_ID='"+masterForm.getSalesUnitOfMeaseurement()+"',DIVISION_ID='"+masterForm.getDivisionId()+"',THERAPEUTIC_SEGMENT_ID='"+masterForm.getTherapeuticSegmentID()+"'," +
						"BRAND_ID='"+masterForm.getBrandID()+"',STRENGTH_ID='"+masterForm.getSrengthId()+"',GENERIC_NAME='"+masterForm.getGenericName()+"',PROD_INSP_MEMO='"+masterForm.getProdInspMemo()+"',GROSS_WEIGHT='"+masterForm.getGrossWeight()+"',NET_WEIGHT='"+masterForm.getNetWeight()+"',WEIGHT_UOM='"+masterForm.getWeightUOM()+"',DIMENSION='"+masterForm.getDimension()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+masterForm.getPuchaseGroupId()+"'," +
						" MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',Tax_Classification='"+masterForm.getTaxClassification()+"',Material_Pricing='"+masterForm.getMaterialPricing()+"',Storage ='"+masterForm.getStorage()+"',HSN_Code='"+masterForm.getHsnCode()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";	 
			int i=0;
			i=ad.SqlExecuteUpdate(updateFinishedProduct);
			if(i>0)
			{
				masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'.");
				masterForm.setTypeDetails("Update");
				if(checkApprover==0)
				{
					masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				return displayNewFinishedProduct(mapping, form, request, response);
				}
			}else{
				masterForm.setMessage("Error...When Updating Finished Products.Please Check....");
				masterForm.setTypeDetails("Update");
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
		  		String matGroup="";
		  		String loctID="";
		  		String matType="";
		  		String produtType="";
				 	String getMatGroup="select mast.Type,mast.MATERIAL_GROUP_ID,loc.LOCATION_CODE,mast.DOMESTIC_OR_EXPORTS from material_code_request  as mast,Location as loc where mast.REQUEST_NO='"+masterForm.getRequestNo()+"' and loc.LOCID=mast.LOCATION_ID ";
				 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
				 	while(rsMatGrup.next()){
				 		matGroup=rsMatGrup.getString("MATERIAL_GROUP_ID");
				 		loctID=rsMatGrup.getString("LOCATION_CODE");
				 		matType=rsMatGrup.getString("Type");
				 		produtType=rsMatGrup.getString("DOMESTIC_OR_EXPORTS");
				 		
				 		if(produtType.equals("D"))
				 			produtType="Domestic";
				 		if(produtType.equals("E"))
				 			produtType="Export";
				 		if(produtType.equals("V"))
				 			produtType="V";
				 	}
				 checkApprover=0;
				 	String pendingApprs="";
				 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='"+materialType+"' and app.Material_Group='"+produtType+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		  			 getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='"+materialType+"' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		  			
		  			 String saveFinishedProduct="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,MANUFACTURED_AT,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
			 					",MATERIAL_GROUP_ID,VALUATION_CLASS,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,SALEABLE_OR_SAMPLE,DOMESTIC_OR_EXPORTS,SALES_PACK_ID,PACK_TYPE_ID,SALES_UNIT_OF_MEAS_ID,DIVISION_ID,THERAPEUTIC_SEGMENT_ID," +
			 					"BRAND_ID,STRENGTH_ID,GENERIC_NAME,PROD_INSP_MEMO,GROSS_WEIGHT,NET_WEIGHT,WEIGHT_UOM,DIMENSION,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,CREATED_DATE,CREATED_BY," +
			 					"Type,Approve_Type,URL,Tax_Classification,Material_Pricing,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+manufacturedAt+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getValuationClass()+"','"+masterForm.getCountryId()+"'" +
			 					",'"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getSaleableOrSample()+"','"+masterForm.getDomesticOrExports()+"','"+masterForm.getSalesPackId()+"','"+masterForm.getPackTypeId()+"','"+masterForm.getSalesUnitOfMeaseurement()+"','"+masterForm.getDivisionId()+"','"+masterForm.getTherapeuticSegmentID()+"','"+masterForm.getBrandID()+"'" +
			 					",'"+masterForm.getSrengthId()+"','"+masterForm.getGenericName()+"','"+masterForm.getProdInspMemo()+"','"+masterForm.getGrossWeight()+"','"+masterForm.getNetWeight()+"','"+masterForm.getWeightUOM()+"','"+masterForm.getDimension()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','','"+masterForm.getTaxClassification()+"','"+masterForm.getMaterialPricing()+"','user','"+masterForm.getHsnCode()+"')";
			 				int j=0;
			  		j=ad.SqlExecuteUpdate(saveFinishedProduct);
		  		
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
						String updateStatus="update material_code_request set Approve_Type='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
						ad.SqlExecuteUpdate(updateStatus);
						request.setAttribute("sendMessage", "sendMessage");
						EMailer email = new EMailer();
						String approvermail="";
						String reqNo=Integer.toString(masterForm.getRequestNo());
						int		i = email.sendMailToApprover(request, approvermail,reqNo, "Material Code Request");
						return displayNewFinishedProduct(mapping, form, request, response);
						
					}else{
						masterForm.setMessage2("Error while submiting approval...");
					}
		  		}else{
		  			
		  			masterForm.setMessage2("No Approvers are assigned.Please Contact to Admin");
		  		}
					
					
				
				
				}
			
			
			
			 if(masterForm.getManufacturedAt().equalsIgnoreCase("Own"))
				{
					request.setAttribute("standardBathcMandatory", "standardBathcMandatory");
					request.setAttribute("manufactureNotMandatory", "manufactureNotMandatory");
				}else{
					masterForm.setManufacturedAt("Third Party");
					
					
					request.setAttribute("standardBathcNotMandatory", "standardBathcNotMandatory");
					request.setAttribute("manufactureMandatory", "manufactureMandatory");
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
				
				LinkedList weightUOMID=new LinkedList();
				LinkedList weightUOMName=new LinkedList();
				String getweightUOM="select * from WEIGHT_UOM";
				ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
				while(rsweightUOM.next())
				{
					weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
					weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
				}
				masterForm.setWeightUOMID(weightUOMID);
				masterForm.setWeightUOMName(weightUOMName);
				
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
				LinkedList materGroupIDList=new LinkedList();
				LinkedList materialGroupIdValueList=new LinkedList();
				String matType=masterForm.getMatType();
				masterForm.setMatType(matType);
				String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%"+matType+"%' order by STXT";
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
				
				
				LinkedList storageID=new LinkedList();
				LinkedList storageName=new LinkedList();
				String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
				ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
				while(rsStrogeLocation.next()){
					storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
					storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
				}
				masterForm.setStorageID(storageID);
				masterForm.setStorageIDName(storageName);
				setMasterData(mapping, form, request, response);
				//set Approved Field
				user=(UserInfo)session.getAttribute("user");
				if(user.getId()==1)
				{	
					request.setAttribute("approved", "approved");
				}
		}catch (Exception e) {
		e.printStackTrace();
		}
		String materialType=masterForm.getMaterialTypeId();
		masterForm.setMaterialCodeLists(materialType);
		return mapping.findForward("newFinishedProduct");
	}
	
	public ActionForward copyNewFinishedProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		FinishedProductForm masterForm=(FinishedProductForm)form;
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
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
			
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
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String matType="";
			
			String getMaterialType="select mt.MATERIAL_GROUP_ID from material_code_request m,MATERIAL_TYPE mt where REQUEST_NO='"+requstNo+"' "
					+ "and m.MATERIAL_TYPE_ID=mt.id";
			ResultSet rsMatType=ad.selectQuery(getMaterialType);
			while(rsMatType.next()){
				matType=rsMatType.getString("MATERIAL_GROUP_ID");
			}
			masterForm.setMatType(matType);
			String getMaterialGroup="select * from MATERIAL_GROUP where  MATERIAL_TYPE like '%"+matType+"%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
			LinkedList weightUOMID=new LinkedList();
			LinkedList weightUOMName=new LinkedList();
			String getweightUOM="select * from WEIGHT_UOM";
			ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
			while(rsweightUOM.next())
			{
				weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
				weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
			}
			masterForm.setWeightUOMID(weightUOMID);
			masterForm.setWeightUOMName(weightUOMName);
			
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
			
			
			
			
			
			
			String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+requstNo+"'";
			ResultSet rs=ad.selectQuery(getFinishedProduct);
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
				masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				
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
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				masterForm.setCountryId(rs.getString("COUNTRY_ID"));
				masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				masterForm.setHsnCode(rs.getString("HSN_Code"));
				masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
				masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
				masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
				masterForm.setBatchCode(rs.getString("BATCH_CODE"));
				masterForm.setSaleableOrSample(rs.getString("SALEABLE_OR_SAMPLE"));
				masterForm.setDomesticOrExports(rs.getString("DOMESTIC_OR_EXPORTS"));
				masterForm.setPackTypeId(rs.getString("PACK_TYPE_ID"));
				masterForm.setSalesUnitOfMeaseurement(rs.getString("SALES_UNIT_OF_MEAS_ID"));
				masterForm.setDivisionId(rs.getString("DIVISION_ID"));
				masterForm.setTherapeuticSegmentID(rs.getString("THERAPEUTIC_SEGMENT_ID"));
				masterForm.setBrandID(rs.getString("BRAND_ID"));
				masterForm.setSrengthId(rs.getString("STRENGTH_ID"));
				masterForm.setGenericName(rs.getString("GENERIC_NAME"));
				masterForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
				masterForm.setTaxClassification(rs.getString("Tax_Classification"));
				masterForm.setMaterialPricing(rs.getString("Material_Pricing"));
				masterForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
				masterForm.setNetWeight(rs.getString("NET_WEIGHT"));
				masterForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
				masterForm.setDimension(rs.getString("DIMENSION"));
				masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			
				if(user.getId()==2)
				{
					masterForm.setApproveType(rs.getString("Approve_Type"));
				String sapcodeNo=rs.getString("SAP_CODE_NO");
				if(sapcodeNo!=null){
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					masterForm.setSapCodeExists("True");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					masterForm.setSapCodeExists("False");
				String sapCreationDate=rs.getString("SAP_CREATION_DATE");
				String sapDate[]=sapCreationDate.split(" ");
				sapCreationDate=sapDate[0];
				String sapCode[]=sapCreationDate.split("-");
				sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
				masterForm.setSapCreationDate(sapCreationDate);
				masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				String requestedBY=rs.getString("REQUESTED_BY");
				masterForm.setRequestedBy(requestedBY);
				masterForm.setTaxClassification(rs.getString("Tax_Classification"));
				masterForm.setMaterialPricing(rs.getString("Material_Pricing"));
				}
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
				}
				if(user.getId()==1)
					{	
					masterForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
				
			}
			masterForm.setTypeDetails("Update");
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			setMasterData(mapping, form, request, response);
			
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
		masterForm.setMaterialCodeLists("FINISHED PRODUCTS");
		
		String forwardType="newFinishedProduct";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="finishedProductSAP";
			request.setAttribute("sapApprover", "sapApprover");
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newMasterForm";
		}
		System.out.println(masterForm.getMaterialTypeId());
		String materialType=masterForm.getMaterialTypeId();
		masterForm.setTypeDetails("Save");
		masterForm.setMaterialCodeLists(materialType);
		return mapping.findForward("newFinishedProduct");
	}
	
	public ActionForward setMasterData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
	try{
		
		LinkedList packTypeID=new LinkedList();
		LinkedList packTypeName=new LinkedList();
		
		String getPackType="select * from PACK_TYPE";
		ResultSet rsPackType=ad.selectQuery(getPackType);
		while(rsPackType.next())
		{
			packTypeID.add(rsPackType.getString("P_TYPE_CODE"));
			packTypeName.add(rsPackType.getString("P_TYPE_CODE")+" - "+rsPackType.getString("P_TYPE_DESC"));
		}
		masterForm.setPackTypeID(packTypeID);
		masterForm.setPackTypeName(packTypeName);
	
		LinkedList packSizeID=new LinkedList();
		LinkedList packSizeName=new LinkedList();
		String getPackSize="select * from PACK_SIZE ";
		ResultSet rsPackSize=ad.selectQuery(getPackSize);
		while(rsPackSize.next())
		{
			packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
			packSizeName.add(rsPackSize.getString("PACK_SIZE_CODE") );
		}
		masterForm.setPackSizeID(packSizeID);
		masterForm.setPackSizeName(packSizeName);
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		String getValuation="select * from VALUATION_CLASS where MAT_TYPE=(select  MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
		ResultSet rsValuation=ad.selectQuery(getValuation);
		while(rsValuation.next())
		{
			valuationClassID.add(rsValuation.getString("VALUATION_ID"));
			valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
		}
		masterForm.setValuationClassID(valuationClassID);
		masterForm.setValuationClassName(valuationClassName);
		
		LinkedList salesUOMID=new LinkedList();
		LinkedList salesUOMName=new LinkedList();
		String getSalesUOM="select * from SALES_UOM order by S_UOM_DESC";
		ResultSet rsSalesUOM=ad.selectQuery(getSalesUOM);
		while(rsSalesUOM.next())
		{
			salesUOMID.add(rsSalesUOM.getString("S_UOM_CODE"));
			packSizeName.add(rsSalesUOM.getString("S_UOM_CODE")+" - "+rsSalesUOM.getString("S_UOM_DESC"));
		}
		masterForm.setSalesUOMID(salesUOMID);
		masterForm.setSalesUOMName(salesUOMName);

		LinkedList divisonID=new LinkedList();
		LinkedList divisonName=new LinkedList();
		String getDivison="select * from DIVISION";
		ResultSet rsDivison=ad.selectQuery(getDivison);
		while(rsDivison.next())
		{
			divisonID.add(rsDivison.getString("DIV_CODE"));
			divisonName.add(rsDivison.getString("DIV_DESC"));
		}
		masterForm.setDivisonID(divisonID);
		masterForm.setDivisonName(divisonName);
		
		LinkedList therapeuticID=new LinkedList();
		LinkedList therapeuticName=new LinkedList();
		String getrstherapeutic="select * from THERAPEUTIC_SEGMENT ORDER BY THER_SEG_DESC";
		ResultSet rstherapeutic=ad.selectQuery(getrstherapeutic);
		while(rstherapeutic.next())
		{
			therapeuticID.add(rstherapeutic.getString("THER_SEG_CODE"));
			therapeuticName.add(rstherapeutic.getString("THER_SEG_DESC"));
		}
		masterForm.setTherapeuticID(therapeuticID);
		masterForm.setTherapeuticName(therapeuticName);
		
		LinkedList brandIDList=new LinkedList();
		LinkedList brandNameList=new LinkedList();
		String getBrand="select * from BRAND order by BRAND_DESC";
		ResultSet rsBrand=ad.selectQuery(getBrand);
		while(rsBrand.next())
		{
			brandIDList.add(rsBrand.getString("BRAND_CODE"));
			brandNameList.add(rsBrand.getString("BRAND_DESC"));
		}
		masterForm.setBrandIDList(brandIDList);
		masterForm.setBrandNameList(brandNameList);
		
		LinkedList strengthIDList=new LinkedList();
		LinkedList strengthNameList=new LinkedList();
		String getStrength="select * from STRENGTH ORDER BY STRENGTH_DESC";
		ResultSet rsStrength=ad.selectQuery(getStrength);
		while(rsStrength.next())
		{
			strengthIDList.add(rsStrength.getString("STRENGTH_CODE"));
			strengthNameList.add(rsStrength.getString("STRENGTH_DESC"));
		}
		masterForm.setStrengthIDList(strengthIDList);
		masterForm.setStrengthNameList(strengthNameList);
		
		LinkedList genericIDList=new LinkedList();
		LinkedList genericNameList=new LinkedList();
		String getGeneric="select * from GENERIC_NAME ORDER BY GEN_NAME_DESC";
		ResultSet rsGeneric=ad.selectQuery(getGeneric);
		while(rsGeneric.next())
		{
			genericIDList.add(rsGeneric.getString("GEN_NAME_CODE"));
			genericNameList.add(rsGeneric.getString("GEN_NAME_DESC"));
		}
		masterForm.setGenericIDList(genericIDList);
		masterForm.setGenericNameList(genericNameList);
		
		LinkedList weightUOMID=new LinkedList();
		LinkedList weightUOMName=new LinkedList();
		String getweightUOM="select * from WEIGHT_UOM";
		ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
		while(rsweightUOM.next())
		{
			weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
			weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
		}
		masterForm.setWeightUOMID(weightUOMID);
		masterForm.setWeightUOMName(weightUOMName);
		
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
		
		
		
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String materialTypeId=request.getParameter("materialType");

		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			
			materTypeIDList.add(rsMaterial.getString("id"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
		}
		masterForm.setMaterTypeIDList(materTypeIDList);
		masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
		return mapping.findForward("approverMaster");
	}
	public ActionForward saveSAPCrationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FinishedProductForm masterForm=(FinishedProductForm)form;
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
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String matType=masterForm.getMatType();
			masterForm.setMatType(matType);
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%"+matType+"%' order by STXT";
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
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			LinkedList weightUOMID=new LinkedList();
			LinkedList weightUOMName=new LinkedList();
			String getweightUOM="select * from WEIGHT_UOM";
			ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
			while(rsweightUOM.next())
			{
				weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
				weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
			}
			masterForm.setWeightUOMID(weightUOMID);
			masterForm.setWeightUOMName(weightUOMName);
			
			setMasterData(mapping, form, request, response);
			
			String sapCreationDate="";
			String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getFinishedProduct);
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
				masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				
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
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				masterForm.setCountryId(rs.getString("COUNTRY_ID"));
				masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
				masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
				masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
				masterForm.setBatchCode(rs.getString("BATCH_CODE"));
				masterForm.setSaleableOrSample(rs.getString("SALEABLE_OR_SAMPLE"));
				masterForm.setDomesticOrExports(rs.getString("DOMESTIC_OR_EXPORTS"));
				String salesPackId=rs.getString("SALES_PACK_ID");
				masterForm.setSalesPackId(rs.getString("SALES_PACK_ID"));
				masterForm.setPackTypeId(rs.getString("PACK_TYPE_ID"));
				masterForm.setSalesUnitOfMeaseurement(rs.getString("SALES_UNIT_OF_MEAS_ID"));
				masterForm.setDivisionId(rs.getString("DIVISION_ID"));
				masterForm.setTherapeuticSegmentID(rs.getString("THERAPEUTIC_SEGMENT_ID"));
				masterForm.setBrandID(rs.getString("BRAND_ID"));
				masterForm.setSrengthId(rs.getString("STRENGTH_ID"));
				masterForm.setGenericName(rs.getString("GENERIC_NAME"));
				masterForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
				masterForm.setTaxClassification(rs.getString("Tax_Classification"));
				masterForm.setMaterialPricing(rs.getString("Material_Pricing"));
				masterForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
				masterForm.setNetWeight(rs.getString("NET_WEIGHT"));
				masterForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
				masterForm.setDimension(rs.getString("DIMENSION"));
				masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			
				if(user.getId()==2)
				{
				String sapcodeNo=rs.getString("SAP_CODE_NO");
				if(sapcodeNo!=null){
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					masterForm.setSapCodeExists("True");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					masterForm.setSapCodeExists("False");
				sapCreationDate=rs.getString("SAP_CREATION_DATE");
				String sapDate[]=sapCreationDate.split(" ");
				sapCreationDate=sapDate[0];
				String sapCode[]=sapCreationDate.split("-");
				sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
				masterForm.setSapCreationDate(sapCreationDate);
				masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				//masterForm.setRequestedBy(rs.getString("REQUESTED_BY"));
				}
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
				}
				if(user.getId()==1)
					{	
					//masterForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
			}
			masterForm.setTypeDetails("Update");
			int k=0;
		//String sapCreationDate=rs.getString("SAP_CREATION_DATE");
		sapCreationDate=masterForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
			
			String updateMaterial="update material_code_request set SAP_CODE_NO='"+masterForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+masterForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+masterForm.getSapCreatedBy()+"',REQUESTED_BY='"+masterForm.getRequestedBy()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			
			 k=ad.SqlExecuteUpdate(updateMaterial);
			if(k>0)
			{
				masterForm.setMessage2("Code creation request saved with request number='"+masterForm.getRequestNo()+"'.");
				masterForm.setTypeDetails("Update");
			}else{
				masterForm.setMessage("Error...When saving finished products.Please check....");
				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterialCodeLists("FINISHED PRODUCTS");
		
		
		return mapping.findForward("approverMaster");
	}
	public ActionForward saveApproverData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		FinishedProductForm masterForm=(FinishedProductForm)form;
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
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String matType=masterForm.getMatType();
			masterForm.setMatType(matType);
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%"+matType+"%' order by STXT";
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
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			setMasterData(mapping, form, request, response);
			
			String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getFinishedProduct);
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
				masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				
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
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				masterForm.setCountryId(rs.getString("COUNTRY_ID"));
				masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
				masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
				masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
				masterForm.setBatchCode(rs.getString("BATCH_CODE"));
				masterForm.setSaleableOrSample(rs.getString("SALEABLE_OR_SAMPLE"));
				masterForm.setDomesticOrExports(rs.getString("DOMESTIC_OR_EXPORTS"));
				String salesPackId=rs.getString("SALES_PACK_ID");
				masterForm.setSalesPackId(rs.getString("SALES_PACK_ID"));
				masterForm.setPackTypeId(rs.getString("PACK_TYPE_ID"));
				masterForm.setSalesUnitOfMeaseurement(rs.getString("SALES_UNIT_OF_MEAS_ID"));
				masterForm.setDivisionId(rs.getString("DIVISION_ID"));
				masterForm.setTherapeuticSegmentID(rs.getString("THERAPEUTIC_SEGMENT_ID"));
				masterForm.setBrandID(rs.getString("BRAND_ID"));
				masterForm.setSrengthId(rs.getString("STRENGTH_ID"));
				masterForm.setGenericName(rs.getString("GENERIC_NAME"));
				masterForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
				masterForm.setTaxClassification(rs.getString("Tax_Classification"));
				masterForm.setMaterialPricing(rs.getString("Material_Pricing"));
				masterForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
				masterForm.setNetWeight(rs.getString("NET_WEIGHT"));
				masterForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
				masterForm.setDimension(rs.getString("DIMENSION"));
				masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			
				if(user.getId()==2)
				{
				String sapcodeNo=rs.getString("SAP_CODE_NO");
				if(sapcodeNo!=null){
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					masterForm.setSapCodeExists("True");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					masterForm.setSapCodeExists("False");
				String sapCreationDate=rs.getString("SAP_CREATION_DATE");
				String sapDate[]=sapCreationDate.split(" ");
				sapCreationDate=sapDate[0];
				String sapCode[]=sapCreationDate.split("-");
				sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
				masterForm.setSapCreationDate(sapCreationDate);
				masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				masterForm.setRequestedBy(rs.getString("REQUESTED_BY"));
				}
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
				}
				if(user.getId()==1)
					{	
					//masterForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
			}
			masterForm.setTypeDetails("Update");
			int k=0;
			String currentdate=EMicroUtils.getCurrentSysDate();
			  String a[]=currentdate.split("/");
			  for(int j=0;j<a.length;j++)
			  {
				  System.out.println("a="+a[j]);
			  }
			  currentdate=a[2]+"-"+a[1]+"-"+a[0];
			 k=ad.SqlExecuteUpdate("update material_code_request set Approve_Type='"+masterForm.getApproveType()+"',approve_date='"+currentdate+"',last_approver='"+user.getFullName()+"',pending_approver='No'  where REQUEST_NO='"+masterForm.getRequestNo()+"'");
			if(k>0)
			{
				masterForm.setMessage2("Code creation request saved with request number='"+masterForm.getRequestNo()+"'.");
				masterForm.setTypeDetails("Update");
			}else{
				masterForm.setMessage("Error...When saving finished products.Please check....");
				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterialCodeLists("FINISHED PRODUCTS");
		
		
		return mapping.findForward("approverMaster");
	}
	
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
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
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Finished Products'";
		
		}else{
		if(reqDate.equalsIgnoreCase("")&&locationId.equalsIgnoreCase("") ){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"'  and l.LOCID=m.LOCATION_ID and m.Type='Finished Products'";
		}
		 if(reqDate.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Finished Products'";
		}
		 if(locationId.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.Location_ID=l.LOCID and m.Type='Finished Products'";
		}
		 if(requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&&!locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Finished Products'";
		}
		 if(reqDate.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Finished Products'";
		}
		if(locationId.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !reqDate.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NOl.LOCNAME,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.REQUEST_DATE='"+reqDate+"' and l.LOCID=m.LOCATION_ID and m.Type='Finished Products'";
		}
		
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Finished Products'";
		
		}
		}
		ResultSet rs=ad.selectQuery(getRecord);
		while(rs.next())
		{
			FinishedProductForm rawRecord=new FinishedProductForm();
			
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
		request.setAttribute("listOfFinishedProducts", rawRecordList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("finishedProductList");
		
		
	}
	
	public ActionForward getMaterialTypeid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	
		String materialTypeId=request.getParameter("materialTypeID");
		
		if(materialTypeId.equalsIgnoreCase("Own"))
		{
			
			
			request.setAttribute("standardBathcMandatory", "standardBathcMandatory");
			request.setAttribute("manufactureNotMandatory", "manufactureNotMandatory");
		}else{
			masterForm.setManufacturedAt("Third Party");
			
			
			request.setAttribute("standardBathcNotMandatory", "standardBathcNotMandatory");
			request.setAttribute("manufactureMandatory", "manufactureMandatory");
		}
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		try{
		String getCountryDetails="select * from Country";
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
		
		LinkedList weightUOMID=new LinkedList();
		LinkedList weightUOMName=new LinkedList();
		String getweightUOM="select * from WEIGHT_UOM";
		ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
		while(rsweightUOM.next())
		{
			weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
			weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
		}
		masterForm.setWeightUOMID(weightUOMID);
		masterForm.setWeightUOMName(weightUOMName);
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String matType="";
		String getMaterialType="select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"'";
		ResultSet rsMatType=ad.selectQuery(getMaterialType);
		while(rsMatType.next()){
			matType=rsMatType.getString("MATERIAL_GROUP_ID");
		}
		masterForm.setMatType(matType);
		String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%"+matType+"%' order by STXT";
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
		
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		masterForm.setStorageID(storageID);
		masterForm.setStorageIDName(storageName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String typeDetails=masterForm.getTypeDetails();
		masterForm.setTypeDetails(typeDetails);
		setMasterData(mapping, form, request, response);
		String materialType=masterForm.getMaterialTypeId();
		masterForm.setMaterialCodeLists(materialType);
		
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
		return mapping.findForward("newFinishedProduct");
	}
	
	public ActionForward editFinishedProdut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		FinishedProductForm masterForm=(FinishedProductForm)form;
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
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			int requstNo=Integer.parseInt(request.getParameter("requstNo"));
			
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
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			
			
			LinkedList weightUOMID=new LinkedList();
			LinkedList weightUOMName=new LinkedList();
			String getweightUOM="select * from WEIGHT_UOM";
			ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
			while(rsweightUOM.next())
			{
				weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
				weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
			}
			masterForm.setWeightUOMID(weightUOMID);
			masterForm.setWeightUOMName(weightUOMName);
			
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
			
			
			
			
			
			
			String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+requstNo+"'";
			ResultSet rs=ad.selectQuery(getFinishedProduct);
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
				masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				
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
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				masterForm.setCountryId(rs.getString("COUNTRY_ID"));
				masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
				masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
				masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
				masterForm.setBatchCode(rs.getString("BATCH_CODE"));
				masterForm.setSaleableOrSample(rs.getString("SALEABLE_OR_SAMPLE"));
				masterForm.setDomesticOrExports(rs.getString("DOMESTIC_OR_EXPORTS"));
				String salesPackId=rs.getString("SALES_PACK_ID");
				masterForm.setSalesPackId(rs.getString("SALES_PACK_ID"));
				masterForm.setPackTypeId(rs.getString("PACK_TYPE_ID"));
				masterForm.setSalesUnitOfMeaseurement(rs.getString("SALES_UNIT_OF_MEAS_ID"));
				masterForm.setDivisionId(rs.getString("DIVISION_ID"));
				masterForm.setTherapeuticSegmentID(rs.getString("THERAPEUTIC_SEGMENT_ID"));
				masterForm.setBrandID(rs.getString("BRAND_ID"));
				masterForm.setSrengthId(rs.getString("STRENGTH_ID"));
				masterForm.setGenericName(rs.getString("GENERIC_NAME"));
				masterForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
				masterForm.setTaxClassification(rs.getString("Tax_Classification"));
				masterForm.setMaterialPricing(rs.getString("Material_Pricing"));
				masterForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
				masterForm.setNetWeight(rs.getString("NET_WEIGHT"));
				masterForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
				masterForm.setDimension(rs.getString("DIMENSION"));
				masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			
				if(user.getId()==2)
				{
					masterForm.setApproveType(rs.getString("Approve_Type"));
				String sapcodeNo=rs.getString("SAP_CODE_NO");
				if(sapcodeNo!=null){
					masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				
				String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
				if(sapCodeExist.equalsIgnoreCase("1"))
				{
					masterForm.setSapCodeExists("True");
				}
				if(sapCodeExist.equalsIgnoreCase("0"))
					masterForm.setSapCodeExists("False");
				String sapCreationDate=rs.getString("SAP_CREATION_DATE");
				String sapDate[]=sapCreationDate.split(" ");
				sapCreationDate=sapDate[0];
				String sapCode[]=sapCreationDate.split("-");
				sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
				masterForm.setSapCreationDate(sapCreationDate);
				masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				String requestedBY=rs.getString("REQUESTED_BY");
				masterForm.setRequestedBy(requestedBY);
				masterForm.setTaxClassification(rs.getString("Tax_Classification"));
				masterForm.setMaterialPricing(rs.getString("Material_Pricing"));
				}
				request.setAttribute("approved", "approved");
				request.setAttribute("sapApprover", "sapApprover");
				}
				if(user.getId()==1)
					{	
					masterForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
				
			}
			masterForm.setTypeDetails("Update");
	
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			
			String matType="";
			String getMaterialType="select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"'";
			ResultSet rsMatType=ad.selectQuery(getMaterialType);
			while(rsMatType.next()){
				matType=rsMatType.getString("MATERIAL_GROUP_ID");
			}
			masterForm.setMatType(matType);
			String getMaterialGroup="select * from MATERIAL_GROUP where  MATERIAL_TYPE like '%"+matType+"%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			setMasterData(mapping, form, request, response);
			
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
		masterForm.setMaterialCodeLists("FINISHED PRODUCTS");
		
		String forwardType="newFinishedProduct";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="finishedProductSAP";
			request.setAttribute("sapApprover", "sapApprover");
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newMasterForm";
		}
		System.out.println(masterForm.getMaterialTypeId());
		String materialType=masterForm.getMaterialTypeId();
		masterForm.setMaterialCodeLists(materialType);
		return mapping.findForward("newFinishedProduct");
	}
	
	public ActionForward deleteMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
		int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
		   String deleteRecord="delete from material_code_request where REQUEST_NO='"+requstNo+"'";
		   int i=0;
		   i=ad.SqlExecuteUpdate(deleteRecord);
		   if(i>=1)
		   {
			   masterForm.setMessage("Finished Product Has Been Successfully Deleted");
		   }else{
			   masterForm.setMessage("Error.... When Deleting Finished Product.");
		   }
		
		   displayFinishedProductList(mapping, form, request, response);
		
		return mapping.findForward("finishedProductList"); 
	}
	
	public ActionForward saveFinishedProducts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
		try{
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
			
			///Brand name text	 
			///Brand name text	 
			if(!masterForm.getBrandIDtxt().equalsIgnoreCase(""))
			{
				int maxtcount=0;
				String newbrandid="";
				
				String abc="Select * from  BRAND where BRAND_DESC='"+masterForm.getBrandIDtxt()+"'";
				ResultSet rsabc=ad.selectQuery(abc);
				if(rsabc.next())
				{
					masterForm.setBrandID(rsabc.getString("BRAND_CODE"));
				}
				else
				{
				String getbrandcount="select count(*) from BRAND";
				ResultSet rsbrand1=ad.selectQuery(getbrandcount);
				while(rsbrand1.next())
				{
					maxtcount=rsbrand1.getInt(1);
					maxtcount=maxtcount+1;
					newbrandid=Integer.toString(maxtcount);
				}
				
        String savebrand="insert into BRAND(BRAND_CODE,BRAND_DESC)values('"+newbrandid+"',UPPER('"+masterForm.getBrandIDtxt()+"'))" ;		
	    int	ij=ad.SqlExecuteUpdate(savebrand);
	    if(ij>0)
	    {
	    	masterForm.setBrandID(newbrandid);
	    }
				
			}
			}
				
			
			
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
		String dateNow = ft.format(dNow);
			  String reqDate=masterForm.getRequestDate();
			  String a[]=reqDate.split("/");
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			  /*String sapCreationDate=masterForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];*/
			String manufacturedAt="";
			manufacturedAt=masterForm.getManufacturedAt();
			
			String typeDetails=masterForm.getTypeDetails();
			String materialType=masterForm.getMaterialTypeId();
			 if(materialType.equalsIgnoreCase("4"))
			 {
				 materialType="FG";
			 }
			 else{
				 materialType="HAWA";
			 }
			
			
			if(typeDetails.equalsIgnoreCase("Save"))
			 {
				
				int count=0;
				String getcount="select count(*) from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				ResultSet rs1=ad.selectQuery(getcount);
				while(rs1.next())
				{
					count=rs1.getInt(1);
				}
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
				 if(count>0)
				 {
				//Request no is available
				int oldRequestNo=masterForm.getRequestNo();
				String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				masterForm.setRequestNo(maxReqno);
					 
					 
					 
				 String url="finishedProduct.do?method=editFinishedProdut";
				 String approve="Pending";
				 String saveFinishedProduct="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,MANUFACTURED_AT,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
					",MATERIAL_GROUP_ID,VALUATION_CLASS,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,SALEABLE_OR_SAMPLE,DOMESTIC_OR_EXPORTS,SALES_PACK_ID,PACK_TYPE_ID,SALES_UNIT_OF_MEAS_ID,DIVISION_ID,THERAPEUTIC_SEGMENT_ID," +
					"BRAND_ID,STRENGTH_ID,GENERIC_NAME,PROD_INSP_MEMO,GROSS_WEIGHT,NET_WEIGHT,WEIGHT_UOM,DIMENSION,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,CREATED_DATE,CREATED_BY," +
					"Type,Approve_Type,URL,last_approver,pending_approver,Tax_Classification,Material_Pricing,Storage,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+manufacturedAt+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getValuationClass()+"','"+masterForm.getCountryId()+"'" +
					",'"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getSaleableOrSample()+"','"+masterForm.getDomesticOrExports()+"','"+masterForm.getSalesPackId()+"','"+masterForm.getPackTypeId()+"','"+masterForm.getSalesUnitOfMeaseurement()+"','"+masterForm.getDivisionId()+"','"+masterForm.getTherapeuticSegmentID()+"','"+masterForm.getBrandID()+"'" +
					",'"+masterForm.getSrengthId()+"','"+masterForm.getGenericName()+"','"+masterForm.getProdInspMemo()+"','"+masterForm.getGrossWeight()+"','"+masterForm.getNetWeight()+"','"+masterForm.getWeightUOM()+"','"+masterForm.getDimension()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getTaxClassification()+"','"+masterForm.getMaterialPricing()+"','"+masterForm.getStorage()+"','"+masterForm.getHsnCode()+"')";
					i=ad.SqlExecuteUpdate(saveFinishedProduct);
					if(i>0)
					{
						masterForm.setMessage2("Code creation request created with request number='"+masterForm.getRequestNo()+"'.");
						masterForm.setTypeDetails("Update");
						
						String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
			  	  		ad.SqlExecuteUpdate(deleteHistory);
			  			
			  			  saveFinishedProduct="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,MANUFACTURED_AT,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
				 					",MATERIAL_GROUP_ID,VALUATION_CLASS,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,SALEABLE_OR_SAMPLE,DOMESTIC_OR_EXPORTS,SALES_PACK_ID,PACK_TYPE_ID,SALES_UNIT_OF_MEAS_ID,DIVISION_ID,THERAPEUTIC_SEGMENT_ID," +
				 					"BRAND_ID,STRENGTH_ID,GENERIC_NAME,PROD_INSP_MEMO,GROSS_WEIGHT,NET_WEIGHT,WEIGHT_UOM,DIMENSION,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,CREATED_DATE,CREATED_BY," +
				 					"Type,Approve_Type,URL,Tax_Classification,Material_Pricing,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+manufacturedAt+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getValuationClass()+"','"+masterForm.getCountryId()+"'" +
				 					",'"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getSaleableOrSample()+"','"+masterForm.getDomesticOrExports()+"','"+masterForm.getSalesPackId()+"','"+masterForm.getPackTypeId()+"','"+masterForm.getSalesUnitOfMeaseurement()+"','"+masterForm.getDivisionId()+"','"+masterForm.getTherapeuticSegmentID()+"','"+masterForm.getBrandID()+"'" +
				 					",'"+masterForm.getSrengthId()+"','"+masterForm.getGenericName()+"','"+masterForm.getProdInspMemo()+"','"+masterForm.getGrossWeight()+"','"+masterForm.getNetWeight()+"','"+masterForm.getWeightUOM()+"','"+masterForm.getDimension()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','','"+masterForm.getTaxClassification()+"','"+masterForm.getMaterialPricing()+"','user','"+masterForm.getHsnCode()+"')";
				 				int j=0;
				  		j=ad.SqlExecuteUpdate(saveFinishedProduct);
						
						return displayNewFinishedProduct(mapping, form, request, response);
					}else{
						masterForm.setMessage("Error...When saving finished products.Please check....");
						typeDetails=masterForm.getTypeDetails();
						masterForm.setTypeDetails(typeDetails);
						
					}
					
				 }else{
					 
					 String url="finishedProduct.do?method=editFinishedProdut";
					 String approve="Pending";
					 String saveFinishedProduct="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,MANUFACTURED_AT,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
						",MATERIAL_GROUP_ID,VALUATION_CLASS,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,SALEABLE_OR_SAMPLE,DOMESTIC_OR_EXPORTS,SALES_PACK_ID,PACK_TYPE_ID,SALES_UNIT_OF_MEAS_ID,DIVISION_ID,THERAPEUTIC_SEGMENT_ID," +
						"BRAND_ID,STRENGTH_ID,GENERIC_NAME,PROD_INSP_MEMO,GROSS_WEIGHT,NET_WEIGHT,WEIGHT_UOM,DIMENSION,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,CREATED_DATE,CREATED_BY," +
						"Type,Approve_Type,URL,last_approver,pending_approver,Tax_Classification,Material_Pricing,Storage,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+manufacturedAt+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getValuationClass()+"','"+masterForm.getCountryId()+"'" +
						",'"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getSaleableOrSample()+"','"+masterForm.getDomesticOrExports()+"','"+masterForm.getSalesPackId()+"','"+masterForm.getPackTypeId()+"','"+masterForm.getSalesUnitOfMeaseurement()+"','"+masterForm.getDivisionId()+"','"+masterForm.getTherapeuticSegmentID()+"','"+masterForm.getBrandID()+"'" +
						",'"+masterForm.getSrengthId()+"','"+masterForm.getGenericName()+"','"+masterForm.getProdInspMemo()+"','"+masterForm.getGrossWeight()+"','"+masterForm.getNetWeight()+"','"+masterForm.getWeightUOM()+"','"+masterForm.getDimension()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getTaxClassification()+"','"+masterForm.getMaterialPricing()+"','"+masterForm.getStorage()+"','"+masterForm.getHsnCode()+"')";
						i=ad.SqlExecuteUpdate(saveFinishedProduct);
							if(i>0)
						{
							masterForm.setMessage2("Code creation request created with request number='"+masterForm.getRequestNo()+"'.");
							masterForm.setTypeDetails("Update");
							
							String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
				  	  		ad.SqlExecuteUpdate(deleteHistory);
				  			
				  			  saveFinishedProduct="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,MANUFACTURED_AT,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
					 					",MATERIAL_GROUP_ID,VALUATION_CLASS,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,SALEABLE_OR_SAMPLE,DOMESTIC_OR_EXPORTS,SALES_PACK_ID,PACK_TYPE_ID,SALES_UNIT_OF_MEAS_ID,DIVISION_ID,THERAPEUTIC_SEGMENT_ID," +
					 					"BRAND_ID,STRENGTH_ID,GENERIC_NAME,PROD_INSP_MEMO,GROSS_WEIGHT,NET_WEIGHT,WEIGHT_UOM,DIMENSION,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,CREATED_DATE,CREATED_BY," +
					 					"Type,Approve_Type,URL,Tax_Classification,Material_Pricing,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+manufacturedAt+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getValuationClass()+"','"+masterForm.getCountryId()+"'" +
					 					",'"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getSaleableOrSample()+"','"+masterForm.getDomesticOrExports()+"','"+masterForm.getSalesPackId()+"','"+masterForm.getPackTypeId()+"','"+masterForm.getSalesUnitOfMeaseurement()+"','"+masterForm.getDivisionId()+"','"+masterForm.getTherapeuticSegmentID()+"','"+masterForm.getBrandID()+"'" +
					 					",'"+masterForm.getSrengthId()+"','"+masterForm.getGenericName()+"','"+masterForm.getProdInspMemo()+"','"+masterForm.getGrossWeight()+"','"+masterForm.getNetWeight()+"','"+masterForm.getWeightUOM()+"','"+masterForm.getDimension()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','','"+masterForm.getTaxClassification()+"','"+masterForm.getMaterialPricing()+"','user','"+masterForm.getHsnCode()+"')";
					 				int j=0;
					  		j=ad.SqlExecuteUpdate(saveFinishedProduct);
					  		
							return displayNewFinishedProduct(mapping, form, request, response);
						}else{
							masterForm.setMessage("Error...When saving finished products.Please check....");
							typeDetails=masterForm.getTypeDetails();
							masterForm.setTypeDetails(typeDetails);
						}
					 
				 }
				/* String Req_Id = ""+masterForm.getRequestNo();
				 EMailer email = new EMailer();
				 i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");
			*/	 
			 }else{
				 System.out.println("update Finished Products");
				 String approvedStatus="Pending";
					
				
					/*if(user.getId()==1)
					{
						approvedStatus=masterForm.getApproveType();
					}
					if(user.getId()==2)
					{
						 String sapCreationDate=masterForm.getSapCreationDate();
						  String b[]=sapCreationDate.split("/");
						  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
						String updateMaterial="update material_code_request set SAP_CODE_NO='"+masterForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+masterForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+masterForm.getSapCreatedBy()+"',REQUESTED_BY='"+masterForm.getRequestedBy()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
						int j=ad.SqlExecuteUpdate(updateMaterial);
						request.setAttribute("approved", "approved");
						request.setAttribute("sapApprover", "sapApprover");
						
					}*/
				 
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
				 		String updateFinishedProduct="update material_code_request set LOCATION_ID='"+masterForm.getLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',MANUFACTURED_AT='"+manufacturedAt+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"'" +
						",MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',VALUATION_CLASS='"+masterForm.getValuationClass()+"',COUNTRY_ID='"+masterForm.getCountryId()+"',CUSTOMER_NAME='"+masterForm.getCustomerName()+"',SHELF_LIFE='"+masterForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+masterForm.getShelfType()+"',STANDARD_BATCH_SIZE='"+masterForm.getStandardBatchSize()+"',BATCH_CODE='"+masterForm.getBatchCode()+"',SALEABLE_OR_SAMPLE='"+masterForm.getSaleableOrSample()+"',DOMESTIC_OR_EXPORTS='"+masterForm.getDomesticOrExports()+"',SALES_PACK_ID='"+masterForm.getSalesPackId()+"',PACK_TYPE_ID='"+masterForm.getPackTypeId()+"',SALES_UNIT_OF_MEAS_ID='"+masterForm.getSalesUnitOfMeaseurement()+"',DIVISION_ID='"+masterForm.getDivisionId()+"',THERAPEUTIC_SEGMENT_ID='"+masterForm.getTherapeuticSegmentID()+"'," +
						"BRAND_ID='"+masterForm.getBrandID()+"',STRENGTH_ID='"+masterForm.getSrengthId()+"',GENERIC_NAME='"+masterForm.getGenericName()+"',PROD_INSP_MEMO='"+masterForm.getProdInspMemo()+"',GROSS_WEIGHT='"+masterForm.getGrossWeight()+"',NET_WEIGHT='"+masterForm.getNetWeight()+"',WEIGHT_UOM='"+masterForm.getWeightUOM()+"',DIMENSION='"+masterForm.getDimension()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+masterForm.getPuchaseGroupId()+"'," +
						" MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',Tax_Classification='"+masterForm.getTaxClassification()+"',Material_Pricing='"+masterForm.getMaterialPricing()+"',Storage ='"+masterForm.getStorage()+"',HSN_Code='"+masterForm.getHsnCode()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";	 
			int i=0;
			i=ad.SqlExecuteUpdate(updateFinishedProduct);
			if(i>0)
			{
				masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'.");
				masterForm.setTypeDetails("Update");
				
				String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  	  		ad.SqlExecuteUpdate(deleteHistory);
	  			
	  			 String saveFinishedProduct="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,MANUFACTURED_AT,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME" +
		 					",MATERIAL_GROUP_ID,VALUATION_CLASS,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,SALEABLE_OR_SAMPLE,DOMESTIC_OR_EXPORTS,SALES_PACK_ID,PACK_TYPE_ID,SALES_UNIT_OF_MEAS_ID,DIVISION_ID,THERAPEUTIC_SEGMENT_ID," +
		 					"BRAND_ID,STRENGTH_ID,GENERIC_NAME,PROD_INSP_MEMO,GROSS_WEIGHT,NET_WEIGHT,WEIGHT_UOM,DIMENSION,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,CREATED_DATE,CREATED_BY," +
		 					"Type,Approve_Type,URL,Tax_Classification,Material_Pricing,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+manufacturedAt+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getValuationClass()+"','"+masterForm.getCountryId()+"'" +
		 					",'"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getSaleableOrSample()+"','"+masterForm.getDomesticOrExports()+"','"+masterForm.getSalesPackId()+"','"+masterForm.getPackTypeId()+"','"+masterForm.getSalesUnitOfMeaseurement()+"','"+masterForm.getDivisionId()+"','"+masterForm.getTherapeuticSegmentID()+"','"+masterForm.getBrandID()+"'" +
		 					",'"+masterForm.getSrengthId()+"','"+masterForm.getGenericName()+"','"+masterForm.getProdInspMemo()+"','"+masterForm.getGrossWeight()+"','"+masterForm.getNetWeight()+"','"+masterForm.getWeightUOM()+"','"+masterForm.getDimension()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','','"+masterForm.getTaxClassification()+"','"+masterForm.getMaterialPricing()+"','user','"+masterForm.getHsnCode()+"')";
		 				int j=0;
		  		j=ad.SqlExecuteUpdate(saveFinishedProduct);
				
				return displayNewFinishedProduct(mapping, form, request, response);
			}else{
				masterForm.setMessage("Error...When Updating Finished Products.Please Check....");
				masterForm.setTypeDetails("Update");
			}	
			 }
			 
			 if(masterForm.getManufacturedAt().equalsIgnoreCase("Own"))
				{
					request.setAttribute("standardBathcMandatory", "standardBathcMandatory");
					request.setAttribute("manufactureNotMandatory", "manufactureNotMandatory");
				}else{
					masterForm.setManufacturedAt("Third Party");
					
					
					request.setAttribute("standardBathcNotMandatory", "standardBathcNotMandatory");
					request.setAttribute("manufactureMandatory", "manufactureMandatory");
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
				
				LinkedList weightUOMID=new LinkedList();
				LinkedList weightUOMName=new LinkedList();
				String getweightUOM="select * from WEIGHT_UOM";
				ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
				while(rsweightUOM.next())
				{
					weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
					weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
				}
				masterForm.setWeightUOMID(weightUOMID);
				masterForm.setWeightUOMName(weightUOMName);
				
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
				LinkedList materGroupIDList=new LinkedList();
				LinkedList materialGroupIdValueList=new LinkedList();
				
				String matType=masterForm.getMatType();
				masterForm.setMatType(matType);
				String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%"+matType+"%' order by STXT";
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
				
				
				LinkedList storageID=new LinkedList();
				LinkedList storageName=new LinkedList();
				String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
				ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
				while(rsStrogeLocation.next()){
					storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
					storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
				}
				masterForm.setStorageID(storageID);
				masterForm.setStorageIDName(storageName);
				setMasterData(mapping, form, request, response);
				//set Approved Field
				user=(UserInfo)session.getAttribute("user");
				if(user.getId()==1)
				{	
					request.setAttribute("approved", "approved");
				}
		}catch (Exception e) {
		e.printStackTrace();
		}
		String materialType=masterForm.getMaterialTypeId();
		masterForm.setMaterialCodeLists(materialType);
		return mapping.findForward("newFinishedProduct");
	}
	
	
	public ActionForward deleteFileListModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
		String checkedValues=request.getParameter("cValues");
		String UncheckedValues=request.getParameter("unValues");
	
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String v[] = checkedValues.split(" ,");
		for(int i=0;i<v.length;i++)
		{
		String deleteQuery="delete from FinishedProduct_Doc where file_name='"+v[i]+"' and request_no='"+masterForm.getRequestNo()+"'";
		ad.SqlExecuteUpdate(deleteQuery);
		}
		ArrayList list = new ArrayList();
		ResultSet rs5 = ad.selectQuery("select *  from FinishedProduct_Doc where request_no='"+masterForm.getRequestNo()+"' ");
				while (rs5.next()) {
					CustomerMasterForm custForm1 = new CustomerMasterForm();
					String s=rs5.getString("file_name");
					custForm1.setFileList(rs5.getString("file_name"));
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
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String matType=masterForm.getMatType();
		masterForm.setMatType(matType);
		String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%"+matType+"%' order by STXT";
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
		
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		masterForm.setStorageID(storageID);
		masterForm.setStorageIDName(storageName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("newFinishedProduct");
	
		
		
		
	}
	
	
	public ActionForward uploadFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
	try{
			FormFile myFile = masterForm.getFileNames();
		    String contentType = myFile.getContentType();
			String fileName = myFile.getFileName();
			byte[] fileData = myFile.getFileData();
			EssDao adlinks=new EssDao();	
			String filePath = getServlet().getServletContext().getRealPath("jsp/ess/sapMasterRequest/Finished Products/UploadFiles");
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
			filePath = filePath.replace("\\", "\\");
			String sql9="select count(*) from FinishedProduct_Doc where  file_name='"+fileName+"' and request_no='"+masterForm.getRequestNo()+"'";
			ResultSet rs15 = adlinks.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				masterForm.setMessage("File aleardy uploaded..please choose another file");
			}
			else
			{
			String insertsql = "insert into FinishedProduct_Doc(file_path,file_name,request_no) values('"+filePath+"','"+fileName+"','"+masterForm.getRequestNo()+"')";
			int a = adlinks.SqlExecuteUpdate(insertsql);
			if (a > 0)
			{
				masterForm.setMessage("Documents Uploaded Successfully");
				
			}else {
				masterForm.setMessage("Error While Uploading Files ... Please check Entered Values");
			}
			
			}
			ArrayList list = new ArrayList();
			ResultSet rs5 = adlinks.selectQuery("select *  from FinishedProduct_Doc where request_no='"+masterForm.getRequestNo()+"'");
					while (rs5.next()) {
						CustomerMasterForm custForm1 = new CustomerMasterForm();
						String s=rs5.getString("file_name");
						custForm1.setFileList(rs5.getString("file_name"));
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
		masterForm.setCounID(countryID);
		masterForm.setCountryName(countryName);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
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
		
		LinkedList weightUOMID=new LinkedList();
		LinkedList weightUOMName=new LinkedList();
		String getweightUOM="select * from WEIGHT_UOM";
		ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
		while(rsweightUOM.next())
		{
			weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
			weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
		}
		masterForm.setWeightUOMID(weightUOMID);
		masterForm.setWeightUOMName(weightUOMName);
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String matType=masterForm.getMatType();
		masterForm.setMatType(matType);
		String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%"+matType+"%' order by STXT";
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
		
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+masterForm.getMaterialTypeId()+"')";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		masterForm.setStorageID(storageID);
		masterForm.setStorageIDName(storageName);
		setMasterData(mapping, form, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("newFinishedProduct");
	
		
	
	}
	
	public ActionForward displayFinishedProductList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
		try{
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
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
		LinkedList listOfFinishedProducts=new LinkedList();
		String getList="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.Type='Finished Products'  and m.LOCATION_ID=l.LOCID";
		ResultSet rsList=ad.selectQuery(getList);
		while(rsList.next())
		{
			FinishedProductForm codeMasterForm=new FinishedProductForm();
			codeMasterForm.setRequestNo(rsList.getInt("REQUEST_NO"));
			String requestDate=rsList.getString("REQUEST_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			codeMasterForm.setRequestDate(requestDate);
			codeMasterForm.setLocationId(rsList.getString("LOCNAME"));
			listOfFinishedProducts.add(codeMasterForm);
			
		}
		
		request.setAttribute("listOfFinishedProducts", listOfFinishedProducts);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("finishedProductList");
	}
	public ActionForward displayNewFinishedProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		FinishedProductForm masterForm=(FinishedProductForm)form;
		EssDao ad=new EssDao();
		String materialTypeId=request.getParameter("materialType");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		if(materialTypeId==null)
		{
			materialTypeId=masterForm.getMaterialTypeId();
		}
		
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
			
			masterForm.setCountryId("IN");
			
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
			
			
			
			LinkedList weightUOMID=new LinkedList();
			LinkedList weightUOMName=new LinkedList();
			String getweightUOM="select * from WEIGHT_UOM";
			ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
			while(rsweightUOM.next())
			{
				weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
				weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
			}
			masterForm.setWeightUOMID(weightUOMID);
			masterForm.setWeightUOMName(weightUOMName);
			
			///
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
		
			String materialname="";
			
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			String matType="";
			String getMaterialType="select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+materialTypeId+"'";
			ResultSet rsMatType=ad.selectQuery(getMaterialType);
			while(rsMatType.next()){
				matType=rsMatType.getString("MATERIAL_GROUP_ID");
			}
			masterForm.setMatType(matType);
			String getMaterialGroup="select * from MATERIAL_GROUP where  MATERIAL_TYPE like '%"+matType+"%' order by STXT";
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
			
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+materialTypeId+"')";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			masterForm.setMaterialTypeId(materialTypeId);
			setMasterData(mapping, form, request, response);
			
			String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			masterForm.setRequestNo(maxReqno);
			
			//Reset Form Values
			masterForm.setTypeDetails("Save");
			
			String locationId=request.getParameter("locationId");
			String materialGrup=request.getParameter("materialGrup");
			String shortName=request.getParameter("shortName");
			String longName=request.getParameter("longName");
			
			masterForm.setMaterialCodeLists(materialTypeId);
			masterForm.setMaterialCodeLists1("");
			masterForm.setLocationId(locationId);
			masterForm.setMaterialTypeId(materialTypeId);
			masterForm.setManufacturedAt("");
			masterForm.setStorageLocationId("");
			masterForm.setMaterialShortName(shortName);
			masterForm.setMaterialLongName(longName);
			masterForm.setMaterialGroupId(materialGrup);
			masterForm.setValuationClass("");
			masterForm.setGenericName("");
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+materialTypeId+"')";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			masterForm.setCountryId("");
			masterForm.setCustomerName("");
			masterForm.setShelfLife("");
			masterForm.setStandardBatchSize("");
			masterForm.setBatchCode("");
			masterForm.setSaleableOrSample("");
			masterForm.setDomesticOrExports("");
			masterForm.setSalesPackId("");
			masterForm.setPackTypeId("");
			masterForm.setDivisionId("");
			masterForm.setTherapeuticSegmentID("");
			masterForm.setBrandID("");
			masterForm.setSrengthId("");
			masterForm.setProdInspMemo("");
			masterForm.setGrossWeight("");
			masterForm.setNetWeight("");
			masterForm.setWeightUOM("");
			masterForm.setDimension("");
			masterForm.setUnitOfMeasId("");
			masterForm.setPuchaseGroupId("");
			masterForm.setSapCodeNo("");
			masterForm.setSapCodeExists("");
			masterForm.setSapCreationDate("");
			masterForm.setSapCreatedBy("");
			masterForm.setRequestedBy("");
			masterForm.setTaxClassification("");
			masterForm.setMaterialPricing("");
			
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
		masterForm.setRequestDate(EMicroUtils.getCurrentSysDate());
		request.setAttribute("manufactureNotMandatory", "manufactureNotMandatory");
		request.setAttribute("standardBathcNotMandatory", "standardBathcNotMandatory");
		
		return mapping.findForward("newFinishedProduct");
	}
}
