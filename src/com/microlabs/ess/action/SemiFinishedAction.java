package com.microlabs.ess.action;

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

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.MaterialmasterDAO;
import com.microlabs.ess.form.SemiFinishedForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class SemiFinishedAction extends DispatchAction{
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
		EssDao ad=new EssDao();
		LinkedList apprList=new LinkedList();
		MaterialmasterDAO dao=new MaterialmasterDAO();
		String location=request.getParameter("locationId");
		String matGroup=request.getParameter("materialGroupId");
		String materialTypeId=request.getParameter("materialTypeId");
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
		apprList=dao.approversList(location, materialTypeId, matGroup);
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
		return mapping.findForward("apprList");
	}
	public ActionForward saveAndSubmitMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
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
			
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
		String dateNow = ft.format(dNow);
			  String reqDate=masterForm.getRequestDate();
			  String a[]=reqDate.split("/");
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
				String typDetails=masterForm.getTypeDetails();
				 int checkApprover=0;
				  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='BULK' and " +
			  		" Location=(select LOCATION_CODE from Location where LOCID='"+masterForm.getLocationId()+"') and Material_Group='"+masterForm.getMaterialGroupId()+"'";
				  ResultSet rsCheck=ad.selectQuery(checkApprovers);
				  while(rsCheck.next())
				  {
					  checkApprover=1;
				  }
				  if(checkApprover==0)
				  {
					  checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='BULK' and " +
						  		" Location=(select LOCATION_CODE from Location where LOCID='"+masterForm.getLocationId()+"') and Material_Group=''";
					rsCheck=ad.selectQuery(checkApprovers);
						  while(rsCheck.next())
						  {
							  checkApprover=1;
						  }
				  }
				
				
				 if(typDetails.equalsIgnoreCase("Save"))
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
						String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
						int maxReqno=0;
						ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
						while(rsReqestNumber.next())
						{
							maxReqno=rsReqestNumber.getInt(1);
						}
						maxReqno+=1;
						masterForm.setRequestNo(maxReqno);
						
					 String url="semifinished.do?method=editSemiFinished";
					 String saveSemiFinished="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
					 		"MATERIAL_GROUP_ID,UNIT_OF_MEAS_ID,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,TARGET_WEIGHT,PROD_INSP_MEMO,WEIGHT_UOM,RETEST_DAYS,RETEST_DAYS_TYPE," +
					 		"VALUATION_CLASS,CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,PACK_SIZE) " +
					 		"values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"'," +
					 		"'"+masterForm.getUnitOfMeasId()+"','"+masterForm.getCountryId()+"','"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getTargetWeight()+"','"+masterForm.getProdInspMemo()+"'," +
					 		"'"+masterForm.getWeightUOM()+"','"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+dateNow+"','"+user.getEmployeeNo()+"','BULK','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getPackSize()+"')";
						i=ad.SqlExecuteUpdate(saveSemiFinished);
						String approve="Pending";
						/*int k=0;
						 k=ad.SqlExecuteUpdate("update material_code_request set Approve_Type='"+approve+"',last_approver='No',pending_approver='"+pendingApprovers+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'");
						*/if(i>0)
						{
							masterForm.setMessage2("Alert Code creation request created with New request number='"+masterForm.getRequestNo()+"' ");
							masterForm.setTypeDetails("Update");
							if(checkApprover==0)
							{
								masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
							return displayNewSemiFinished(mapping, form, request, response);
							}
						}else{
							masterForm.setMessage("Error...When Saving Semi Finished.Please Check....");
							masterForm.setTypeDetails("Save");
						} 
						
					}else{
						 String url="semifinished.do?method=editSemiFinished";
						 String saveSemiFinished="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
						 		"MATERIAL_GROUP_ID,UNIT_OF_MEAS_ID,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,TARGET_WEIGHT,PROD_INSP_MEMO,WEIGHT_UOM,RETEST_DAYS,RETEST_DAYS_TYPE," +
						 		"VALUATION_CLASS,CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,PACK_SIZE) " +
						 		"values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"'," +
						 		"'"+masterForm.getUnitOfMeasId()+"','"+masterForm.getCountryId()+"','"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getTargetWeight()+"','"+masterForm.getProdInspMemo()+"'," +
						 		"'"+masterForm.getWeightUOM()+"','"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+dateNow+"','"+user.getEmployeeNo()+"','BULK','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getPackSize()+"')";
							i=ad.SqlExecuteUpdate(saveSemiFinished);
							String approve="Pending";
								if(i>0)
							{
								masterForm.setMessage2("Code creation request created with request number='"+masterForm.getRequestNo()+"'.");
								masterForm.setTypeDetails("Update");
								if(checkApprover==0)
								{
									masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
								return displayNewSemiFinished(mapping, form, request, response);
								}
							}else{
								masterForm.setMessage("Error...When Saving Semi Finished.Please Check....");
								masterForm.setTypeDetails("Save");
							} 	
						
						
					}
					/*String Req_Id = ""+masterForm.getRequestNo();
					EMailer email = new EMailer();
					i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");*/
				 }
				 else{
					 System.out.println("update Semi Finished ");
					 String approvedStatus="Pending";
						
					 user=(UserInfo)session.getAttribute("user");
					/* if(user.getId()==1)
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
					 String updateSemiFinished="update material_code_request set LOCATION_ID='"+masterForm.getLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"'," +
					 		"MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',COUNTRY_ID='"+masterForm.getCountryId()+"',CUSTOMER_NAME='"+masterForm.getCustomerName()+"',SHELF_LIFE='"+masterForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+masterForm.getShelfType()+"',STANDARD_BATCH_SIZE='"+masterForm.getStandardBatchSize()+"',BATCH_CODE='"+masterForm.getBatchCode()+"',TARGET_WEIGHT='"+masterForm.getTargetWeight()+"',PROD_INSP_MEMO='"+masterForm.getProdInspMemo()+"'," +
					 		"WEIGHT_UOM='"+masterForm.getWeightUOM()+"',RETEST_DAYS='"+masterForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+masterForm.getRetestType()+"',VALUATION_CLASS='"+masterForm.getValuationClass()+"',MODIFIED_DATE='"+dateNow+"'," +
					 		"MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',PACK_SIZE='"+masterForm.getPackSize()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
					 
				int i=0;
				i=ad.SqlExecuteUpdate(updateSemiFinished);
				if(i>0)
				{
					masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'.");
					masterForm.setTypeDetails("Update");
					if(checkApprover==0)
					{
						masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
					return displayNewSemiFinished(mapping, form, request, response);
					}
				}else{
					masterForm.setMessage("Error...When Updating Semi Finished.Please Check....");
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
					 	String getMatGroup="select mast.Type,mast.MATERIAL_GROUP_ID,loc.LOCATION_CODE from material_code_request  as mast,Location as loc where mast.REQUEST_NO='"+masterForm.getRequestNo()+"' and loc.LOCID=mast.LOCATION_ID ";
					 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
					 	while(rsMatGrup.next()){
					 		matGroup=rsMatGrup.getString("MATERIAL_GROUP_ID");
					 		loctID=rsMatGrup.getString("LOCATION_CODE");
					 		matType=rsMatGrup.getString("Type");
					 	}
					 checkApprover=0;
					 	String pendingApprs="";
					 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='BULK' and app.Material_Group='"+matGroup+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
			  			 getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='BULK' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
			  	  		String inserHistyrySemiFinished="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
					 		"MATERIAL_GROUP_ID,UNIT_OF_MEAS_ID,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,TARGET_WEIGHT,PROD_INSP_MEMO,WEIGHT_UOM,RETEST_DAYS,RETEST_DAYS_TYPE," +
					 		"VALUATION_CLASS,CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,PACK_SIZE,Role) " +
					 		"values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"'," +
					 		"'"+masterForm.getUnitOfMeasId()+"','"+masterForm.getCountryId()+"','"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getTargetWeight()+"','"+masterForm.getProdInspMemo()+"'," +
					 		"'"+masterForm.getWeightUOM()+"','"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+dateNow+"','"+user.getEmployeeNo()+"','BULK','Created','','"+masterForm.getPackSize()+"','user')";
				int j=0;
				 j=ad.SqlExecuteUpdate(inserHistyrySemiFinished);
				 
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
							return displayNewSemiFinished(mapping, form, request, response);
							
						}else{
							masterForm.setMessage2("Error while submiting approval...");
						}
			  		}else{
			  			
			  			masterForm.setMessage2("No Approvers are assigned.Please Contact to Admin");
			  		}
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
					
					LinkedList valuationClassID=new LinkedList();
					LinkedList valuationClassName=new LinkedList();
					String getValuation="select * from VALUATION_CLASS where MAT_TYPE='BULK'";
					ResultSet rsValuation=ad.selectQuery(getValuation);
					while(rsValuation.next())
					{
						valuationClassID.add(rsValuation.getString("VALUATION_ID"));
						valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
					}
					masterForm.setValuationClassID(valuationClassID);
					masterForm.setValuationClassName(valuationClassName);
					
					LinkedList packSizeID=new LinkedList();
					LinkedList packSizeName=new LinkedList();
					String getPackSize="select * from PACK_SIZE";
					ResultSet rsPackSize=ad.selectQuery(getPackSize);
					while(rsPackSize.next())
					{
						packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
						packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
					}
					masterForm.setPackSizeID(packSizeID);
					masterForm.setPackSizeName(packSizeName);
					
					LinkedList storageID=new LinkedList();
					LinkedList storageName=new LinkedList();
					String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='BULK'";
					ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
					while(rsStrogeLocation.next()){
						storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
						storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
					}
					masterForm.setStorageID(storageID);
					masterForm.setStorageIDName(storageName);
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
					
					String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%BULK%'  order by STXT order by STXT ";
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
					
					
					
					//set Approved Field
					user=(UserInfo)session.getAttribute("user");
					if(user.getId()==1)
					{	
						request.setAttribute("approved", "approved");
					}
		}catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterialCodeLists("3");
		return mapping.findForward("newSemiFinishedProduct");
	}
	
	public ActionForward copyNewSemiFinished(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
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
			
			LinkedList packSizeID=new LinkedList();
			LinkedList packSizeName=new LinkedList();
			String getPackSize="select * from PACK_SIZE";
			ResultSet rsPackSize=ad.selectQuery(getPackSize);
			while(rsPackSize.next())
			{
				packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
				packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
			}
			masterForm.setPackSizeID(packSizeID);
			masterForm.setPackSizeName(packSizeName);
		
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
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='BULK'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%BULK%' order by STXT";
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
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='BULK'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
			String getSemiFinished="select * from material_code_request where REQUEST_NO='"+requstNo+"'";
			ResultSet rs=ad.selectQuery(getSemiFinished);
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
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				
		 		masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
		 		masterForm.setPackSize(rs.getString("PACK_SIZE"));
		 		masterForm.setCountryId(rs.getString("COUNTRY_ID"));
		 		masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
		 		masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
		 		masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
		 		masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
		 		masterForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
		 		masterForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
		 		
		 		masterForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
		 		masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
		 		masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
		 		
				
				
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
					masterForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
			}			
			masterForm.setTypeDetails("Update");
			masterForm.setMaterialCodeLists("SEMI FINISHED");
			
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
String forwardType="newMasterForm";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="semifinishedSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newSemiFinishedProduct";
		}
		masterForm.setTypeDetails("Save");
		masterForm.setMaterialTypeId("3");
		masterForm.setMaterialCodeLists("3");
		return mapping.findForward(forwardType);
	}
	public ActionForward saveSAPCrationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
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
			
			LinkedList packSizeID=new LinkedList();
			LinkedList packSizeName=new LinkedList();
			String getPackSize="select * from PACK_SIZE";
			ResultSet rsPackSize=ad.selectQuery(getPackSize);
			while(rsPackSize.next())
			{
				packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
				packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
			}
			masterForm.setPackSizeID(packSizeID);
			masterForm.setPackSizeName(packSizeName);
			
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
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='BULK'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%BULK%' order by STXT";
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
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='BULK'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			int requstNo=masterForm.getRequestNo();
			String getSemiFinished="select * from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getSemiFinished);
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
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				
		 		masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
		 		masterForm.setCountryId(rs.getString("COUNTRY_ID"));
		 		masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
		 		masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
		 		masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
		 		masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
		 		masterForm.setBatchCode(rs.getString("BATCH_CODE"));
		 		masterForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
		 		masterForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
		 		
		 		masterForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
		 		masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
		 		masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
		 		
				
				
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
			masterForm.setMaterialCodeLists("SEMI FINISHED");
			int  userId=user.getId();
			int i=0;
			if(userId==2)
			
			{
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
		}catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("approverMaster");
	}
	
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
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
			
			LinkedList packSizeID=new LinkedList();
			LinkedList packSizeName=new LinkedList();
			String getPackSize="select * from PACK_SIZE";
			ResultSet rsPackSize=ad.selectQuery(getPackSize);
			while(rsPackSize.next())
			{
				packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
				packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
			}
			masterForm.setPackSizeID(packSizeID);
			masterForm.setPackSizeName(packSizeName);
			
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
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='BULK'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%BULK%' order by STXT";
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
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='BULK'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			int requstNo=masterForm.getRequestNo();
			String getSemiFinished="select * from material_code_request where REQUEST_NO='"+masterForm.getRequestNo()+"'";
			ResultSet rs=ad.selectQuery(getSemiFinished);
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
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				
		 		masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
		 		masterForm.setCountryId(rs.getString("COUNTRY_ID"));
		 		masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
		 		masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
		 		masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
		 		masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
		 		masterForm.setBatchCode(rs.getString("BATCH_CODE"));
		 		masterForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
		 		masterForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
		 		
		 		masterForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
		 		masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
		 		masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
		 		
				
				
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
			masterForm.setMaterialCodeLists("SEMI FINISHED");
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
		}catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("approverMaster");
	}
	
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
		EssDao ad=new EssDao();
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
		
		
		LinkedList rawRecordList=new LinkedList();
		String getRecord="";
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Semi Finished'";
		
		}else{
		if(reqDate.equalsIgnoreCase("")&&locationId.equalsIgnoreCase("") ){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"'  and l.LOCID=m.LOCATION_ID and m.Type='Semi Finished'";
		}
		 if(reqDate.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Semi Finished'";
		}
		 if(locationId.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.Location_ID=l.LOCID and m.Type='Semi Finished'";
		}
		 if(requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&&!locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Semi Finished'";
		}
		 if(reqDate.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Semi Finished'";
		}
		if(locationId.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !reqDate.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.REQUEST_DATE='"+reqDate+"' and l.LOCID=m.LOCATION_ID and m.Type='Semi Finished'";
		}
		
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Semi Finished'";
		
		}
		}
		ResultSet rs=ad.selectQuery(getRecord);
		while(rs.next())
		{
			SemiFinishedForm rawRecord=new SemiFinishedForm();
			
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
		request.setAttribute("listOfSemiFinished", rawRecordList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("semiFinishedProductList");
		
		
	}
	
	public ActionForward deleteSemiFinished(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
		EssDao ad=new EssDao();
		int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
		   String deleteRecord="delete from material_code_request where REQUEST_NO='"+requstNo+"'";
		   int i=0;
		   i=ad.SqlExecuteUpdate(deleteRecord);
		   if(i>=1)
		   {
			   masterForm.setMessage("Semi Finished Has Been Successfully Deleted");
		   }else{
			   masterForm.setMessage("Error.... When Deleting Semi Finished.");
		   }
		
		   displaySemiFinishedList(mapping, form, request, response);
		
		return mapping.findForward("semiFinishedProductList");
	}
	
	public ActionForward editSemiFinished(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
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
			
			LinkedList packSizeID=new LinkedList();
			LinkedList packSizeName=new LinkedList();
			String getPackSize="select * from PACK_SIZE";
			ResultSet rsPackSize=ad.selectQuery(getPackSize);
			while(rsPackSize.next())
			{
				packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
				packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
			}
			masterForm.setPackSizeID(packSizeID);
			masterForm.setPackSizeName(packSizeName);
		
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
			String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='BULK'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%BULK%' order by STXT";
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
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='BULK'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			int requstNo=Integer.parseInt(request.getParameter("requstNo"));
			String getSemiFinished="select * from material_code_request where REQUEST_NO='"+requstNo+"'";
			ResultSet rs=ad.selectQuery(getSemiFinished);
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
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				
		 		masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
		 		masterForm.setPackSize(rs.getString("PACK_SIZE"));
		 		masterForm.setCountryId(rs.getString("COUNTRY_ID"));
		 		masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
		 		masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
		 		masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
		 		masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
		 		masterForm.setBatchCode(rs.getString("BATCH_CODE"));
		 		masterForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
		 		masterForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
		 		
		 		masterForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
		 		masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
		 		masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
		 		
				
				
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
					masterForm.setApproveType(rs.getString("Approve_Type"));
						request.setAttribute("approved", "approved");
					}
			}			
			masterForm.setTypeDetails("Update");
			masterForm.setMaterialCodeLists("SEMI FINISHED");
			
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
String forwardType="newMasterForm";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="semifinishedSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newSemiFinishedProduct";
		}
		masterForm.setMaterialTypeId("3");
		masterForm.setMaterialCodeLists("3");
		return mapping.findForward(forwardType);
	}
	
	public ActionForward saveSemiFinished(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
		
		EssDao ad=new EssDao();
		try{
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
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
			
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
		String dateNow = ft.format(dNow);
			  String reqDate=masterForm.getRequestDate();
			  String a[]=reqDate.split("/");
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			/*  String sapCreationDate=masterForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];*/
			  
			  
				
				String typDetails=masterForm.getTypeDetails();
				 if(typDetails.equalsIgnoreCase("Save"))
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
						String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
						int maxReqno=0;
						ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
						while(rsReqestNumber.next())
						{
							maxReqno=rsReqestNumber.getInt(1);
						}
						maxReqno+=1;
						masterForm.setRequestNo(maxReqno);
						
					 String url="semifinished.do?method=editSemiFinished";
					 String saveSemiFinished="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
					 		"MATERIAL_GROUP_ID,UNIT_OF_MEAS_ID,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,TARGET_WEIGHT,PROD_INSP_MEMO,WEIGHT_UOM,RETEST_DAYS,RETEST_DAYS_TYPE," +
					 		"VALUATION_CLASS,CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,PACK_SIZE) " +
					 		"values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"'," +
					 		"'"+masterForm.getUnitOfMeasId()+"','"+masterForm.getCountryId()+"','"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getTargetWeight()+"','"+masterForm.getProdInspMemo()+"'," +
					 		"'"+masterForm.getWeightUOM()+"','"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+dateNow+"','"+user.getEmployeeNo()+"','BULK','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getPackSize()+"')";
						i=ad.SqlExecuteUpdate(saveSemiFinished);
						String approve="Pending";
						/*int k=0;
						 k=ad.SqlExecuteUpdate("update material_code_request set Approve_Type='"+approve+"',last_approver='No',pending_approver='"+pendingApprovers+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'");
						*/if(i>0)
						{
							masterForm.setMessage2("Code creation request created with request number='"+masterForm.getRequestNo()+"' ");
							masterForm.setTypeDetails("Update");
							
							String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
				  	  		ad.SqlExecuteUpdate(deleteHistory);
				  	  		String inserHistyrySemiFinished="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
						 		"MATERIAL_GROUP_ID,UNIT_OF_MEAS_ID,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,TARGET_WEIGHT,PROD_INSP_MEMO,WEIGHT_UOM,RETEST_DAYS,RETEST_DAYS_TYPE," +
						 		"VALUATION_CLASS,CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,PACK_SIZE,Role) " +
						 		"values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"'," +
						 		"'"+masterForm.getUnitOfMeasId()+"','"+masterForm.getCountryId()+"','"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getTargetWeight()+"','"+masterForm.getProdInspMemo()+"'," +
						 		"'"+masterForm.getWeightUOM()+"','"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+dateNow+"','"+user.getEmployeeNo()+"','BULK','Created','','"+masterForm.getPackSize()+"','user')";
						int j=0;
						 j=ad.SqlExecuteUpdate(inserHistyrySemiFinished);
							
							return displayNewSemiFinished(mapping, form, request, response);
						}else{
							masterForm.setMessage("Error...When Saving Semi Finished.Please Check....");
							masterForm.setTypeDetails("Save");
						} 
						
					}else{
						 String url="semifinished.do?method=editSemiFinished";
						 String saveSemiFinished="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
						 		"MATERIAL_GROUP_ID,UNIT_OF_MEAS_ID,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,TARGET_WEIGHT,PROD_INSP_MEMO,WEIGHT_UOM,RETEST_DAYS,RETEST_DAYS_TYPE," +
						 		"VALUATION_CLASS,CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,PACK_SIZE) " +
						 		"values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"'," +
						 		"'"+masterForm.getUnitOfMeasId()+"','"+masterForm.getCountryId()+"','"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getTargetWeight()+"','"+masterForm.getProdInspMemo()+"'," +
						 		"'"+masterForm.getWeightUOM()+"','"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+dateNow+"','"+user.getEmployeeNo()+"','BULK','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getPackSize()+"')";
							i=ad.SqlExecuteUpdate(saveSemiFinished);
							String approve="Pending";
								if(i>0)
							{
								masterForm.setMessage2("Code creation request created with request number='"+masterForm.getRequestNo()+"'.");
								masterForm.setTypeDetails("Update");
								
								String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
					  	  		ad.SqlExecuteUpdate(deleteHistory);
					  	  		String inserHistyrySemiFinished="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
							 		"MATERIAL_GROUP_ID,UNIT_OF_MEAS_ID,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,TARGET_WEIGHT,PROD_INSP_MEMO,WEIGHT_UOM,RETEST_DAYS,RETEST_DAYS_TYPE," +
							 		"VALUATION_CLASS,CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,PACK_SIZE,Role) " +
							 		"values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"'," +
							 		"'"+masterForm.getUnitOfMeasId()+"','"+masterForm.getCountryId()+"','"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getTargetWeight()+"','"+masterForm.getProdInspMemo()+"'," +
							 		"'"+masterForm.getWeightUOM()+"','"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+dateNow+"','"+user.getEmployeeNo()+"','BULK','Created','','"+masterForm.getPackSize()+"','user')";
							int j=0;
							 j=ad.SqlExecuteUpdate(inserHistyrySemiFinished);
								
								return displayNewSemiFinished(mapping, form, request, response);
							}else{
								masterForm.setMessage("Error...When Saving Semi Finished.Please Check....");
								masterForm.setTypeDetails("Save");
							} 	
						
						
					}
					/*String Req_Id = ""+masterForm.getRequestNo();
					EMailer email = new EMailer();
					i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");*/
				 }
				 else{
					 System.out.println("update Semi Finished ");
					 String approvedStatus="Pending";
						
					 user=(UserInfo)session.getAttribute("user");
					/* if(user.getId()==1)
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
					 String updateSemiFinished="update material_code_request set LOCATION_ID='"+masterForm.getLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"'," +
					 		"MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',COUNTRY_ID='"+masterForm.getCountryId()+"',CUSTOMER_NAME='"+masterForm.getCustomerName()+"',SHELF_LIFE='"+masterForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+masterForm.getShelfType()+"',STANDARD_BATCH_SIZE='"+masterForm.getStandardBatchSize()+"',BATCH_CODE='"+masterForm.getBatchCode()+"',TARGET_WEIGHT='"+masterForm.getTargetWeight()+"',PROD_INSP_MEMO='"+masterForm.getProdInspMemo()+"'," +
					 		"WEIGHT_UOM='"+masterForm.getWeightUOM()+"',RETEST_DAYS='"+masterForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+masterForm.getRetestType()+"',VALUATION_CLASS='"+masterForm.getValuationClass()+"',MODIFIED_DATE='"+dateNow+"'," +
					 		"MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',PACK_SIZE='"+masterForm.getPackSize()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
					 
				int i=0;
				i=ad.SqlExecuteUpdate(updateSemiFinished);
				if(i>0)
				{
					masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'.");
					masterForm.setTypeDetails("Update");
					
					String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
		  	  		ad.SqlExecuteUpdate(deleteHistory);
		  	  		String inserHistyrySemiFinished="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
				 		"MATERIAL_GROUP_ID,UNIT_OF_MEAS_ID,COUNTRY_ID,CUSTOMER_NAME,SHELF_LIFE,SHELF_LIFE_TYPE,STANDARD_BATCH_SIZE,BATCH_CODE,TARGET_WEIGHT,PROD_INSP_MEMO,WEIGHT_UOM,RETEST_DAYS,RETEST_DAYS_TYPE," +
				 		"VALUATION_CLASS,CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,PACK_SIZE,Role) " +
				 		"values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"'," +
				 		"'"+masterForm.getUnitOfMeasId()+"','"+masterForm.getCountryId()+"','"+masterForm.getCustomerName()+"','"+masterForm.getShelfLife()+"','"+masterForm.getShelfType()+"','"+masterForm.getStandardBatchSize()+"','"+masterForm.getBatchCode()+"','"+masterForm.getTargetWeight()+"','"+masterForm.getProdInspMemo()+"'," +
				 		"'"+masterForm.getWeightUOM()+"','"+masterForm.getRetestDays()+"','"+masterForm.getRetestType()+"','"+masterForm.getValuationClass()+"','"+dateNow+"','"+user.getEmployeeNo()+"','BULK','Created','','"+masterForm.getPackSize()+"','user')";
				int j=0;
				 j=ad.SqlExecuteUpdate(inserHistyrySemiFinished);
				 
					return displayNewSemiFinished(mapping, form, request, response);
				}else{
					masterForm.setMessage("Error...When Updating Semi Finished.Please Check....");
					masterForm.setTypeDetails("Update");
				}	 
					 
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
					
					LinkedList valuationClassID=new LinkedList();
					LinkedList valuationClassName=new LinkedList();
					String getValuation="select * from VALUATION_CLASS where MAT_TYPE='BULK'";
					ResultSet rsValuation=ad.selectQuery(getValuation);
					while(rsValuation.next())
					{
						valuationClassID.add(rsValuation.getString("VALUATION_ID"));
						valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
					}
					masterForm.setValuationClassID(valuationClassID);
					masterForm.setValuationClassName(valuationClassName);
					
					LinkedList packSizeID=new LinkedList();
					LinkedList packSizeName=new LinkedList();
					String getPackSize="select * from PACK_SIZE";
					ResultSet rsPackSize=ad.selectQuery(getPackSize);
					while(rsPackSize.next())
					{
						packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
						packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
					}
					masterForm.setPackSizeID(packSizeID);
					masterForm.setPackSizeName(packSizeName);
					
					LinkedList storageID=new LinkedList();
					LinkedList storageName=new LinkedList();
					String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='BULK'";
					ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
					while(rsStrogeLocation.next()){
						storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
						storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
					}
					masterForm.setStorageID(storageID);
					masterForm.setStorageIDName(storageName);
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
					
					String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%BULK%'  order by STXT";
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
					
					
					
					//set Approved Field
					user=(UserInfo)session.getAttribute("user");
					if(user.getId()==1)
					{	
						request.setAttribute("approved", "approved");
					}
		}catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterialCodeLists("3");
		return mapping.findForward("newSemiFinishedProduct");
	}
	
	public ActionForward displaySemiFinishedList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
		EssDao ad=new EssDao();
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
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
		LinkedList listOfFinishedProducts=new LinkedList();
		String getList="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.Type='Semi Finished'  and m.LOCATION_ID=l.LOCID";
		ResultSet rsList=ad.selectQuery(getList);
		while(rsList.next())
		{
			SemiFinishedForm codeMasterForm=new SemiFinishedForm();
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
		
		request.setAttribute("listOfSemiFinished", listOfFinishedProducts);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return mapping.findForward("semiFinishedProductList");
	}
	public ActionForward displayNewSemiFinished(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		SemiFinishedForm masterForm=(SemiFinishedForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 	
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
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
				
				LinkedList packSizeID=new LinkedList();
				LinkedList packSizeName=new LinkedList();
				String getPackSize="select * from PACK_SIZE";
				ResultSet rsPackSize=ad.selectQuery(getPackSize);
				while(rsPackSize.next())
				{
					packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
					packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
				}
				masterForm.setPackSizeID(packSizeID);
				masterForm.setPackSizeName(packSizeName);
				
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
				String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%BULK%'  order by STXT ";
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
				String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='BULK'";
				ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
				while(rsStrogeLocation.next()){
					storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
					storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
				}
				masterForm.setStorageID(storageID);
				masterForm.setStorageIDName(storageName);
				LinkedList valuationClassID=new LinkedList();
				LinkedList valuationClassName=new LinkedList();
				String getValuation="select * from VALUATION_CLASS where MAT_TYPE='BULK'";
				ResultSet rsValuation=ad.selectQuery(getValuation);
				while(rsValuation.next())
				{
					valuationClassID.add(rsValuation.getString("VALUATION_ID"));
					valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
				}
				masterForm.setValuationClassID(valuationClassID);
				masterForm.setValuationClassName(valuationClassName);
				
				
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
				String locationId=request.getParameter("locationId");
				String materialGrup=request.getParameter("materialGrup");
				String shortName=request.getParameter("shortName");
				String longName=request.getParameter("longName");
				
				masterForm.setMaterialCodeLists("3");
				masterForm.setMaterialCodeLists1("");
				masterForm.setTypeDetails("Save");
				masterForm.setRequestDate("");
			
				
				masterForm.setLocationId(locationId);
				masterForm.setMaterialTypeId("3");
				masterForm.setStorageLocationId("");
				masterForm.setMaterialShortName(shortName);
				masterForm.setMaterialLongName(longName);
				masterForm.setMaterialGroupId(materialGrup);
				masterForm.setPackSize("");
		 		masterForm.setUnitOfMeasId("");
		 		masterForm.setCountryId("");
		 		masterForm.setCustomerName("");
		 		masterForm.setShelfLife("");
		 		masterForm.setStandardBatchSize("");
		 		masterForm.setBatchCode("");
		 		masterForm.setTargetWeight("");
		 		masterForm.setProdInspMemo("");
		 		masterForm.setGrossWeight("");
		 		masterForm.setNetWeight("");
		 		masterForm.setWeightUOM("");
		 		masterForm.setRetestDays("");
		 		masterForm.setValuationClass("");
		 		masterForm.setSapCodeNo("");
				masterForm.setSapCodeExists("");
				masterForm.setSapCreationDate("");
		 		masterForm.setSapCreatedBy("");
		 		masterForm.setRequestedBy("");
			
			masterForm.setRequestDate(EMicroUtils.getCurrentSysDate());
			
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
		
		
		
		return mapping.findForward("newSemiFinishedProduct");
	}

}
