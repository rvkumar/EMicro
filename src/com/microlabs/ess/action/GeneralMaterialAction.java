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
import com.microlabs.ess.form.GeneralMaterialForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class GeneralMaterialAction extends DispatchAction{
	
	
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
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
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
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
	           SimpleDateFormat ft = 
	           new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");

	           System.out.println("Current Date: " + ft.format(dNow));
			  String dateNow = ft.format(dNow);
			  String reqDate=masterForm.getRequestDate();
			  String a[]=reqDate.split("/");
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			 /* String sapCreationDate=masterForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];*/
			String typeDetails=masterForm.getTypeDetails();
			 String materialType="";
				
			 if(masterForm.getMaterialTypeId().equalsIgnoreCase("10"))
			 {
				 materialType="LC";
			 }
			 if(masterForm.getMaterialTypeId().equalsIgnoreCase("7"))
			 {
				 materialType="ZCIV";
			 }
			 if(masterForm.getMaterialTypeId().equalsIgnoreCase("8"))
			 {
				 materialType="ZCON";
			 }
			 if(masterForm.getMaterialTypeId().equalsIgnoreCase("14"))
			 {
				 materialType="ZSCR";
			 }
			 if(masterForm.getMaterialTypeId().equalsIgnoreCase("9"))
			 {
				 materialType="ZITC";
			 }
			 if(masterForm.getMaterialTypeId().equalsIgnoreCase("11"))
			 {
				 materialType="ZPFL";
			 }
			
			int checkApprover=0;
			  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='"+materialType+"' and " +
		  		" Location=(select LOCATION_CODE from Location where LOCID='"+masterForm.getLocationId()+"') and Material_Group='"+masterForm.getMaterialGroupId()+"'";
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
					if(count>0){
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
						
				  String url="generalMaterial.do?method=editGeneralMaterial";
				  String saveGeneralMaterial="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
					"MATERIAL_GROUP_ID,PURCHASE_GROUP_ID,UNIT_OF_MEAS_ID,APPROXIMATE_VALUE,VALUATION_CLASS,is_asset,UTILIZING_DEPT,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID," +
					"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"'," +
					"'"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getApproximateValue()+"'," +
					"'"+masterForm.getValuationClass()+"','"+masterForm.getIsAsset()+"','"+masterForm.getUtilizingDept()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"'," +
					"'"+masterForm.getPurposeID()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getHsnCode()+"')";
		
					i=ad.SqlExecuteUpdate(saveGeneralMaterial);
					String approve="Pending";
						if(i>0)
					{
						masterForm.setMessage2("Alert Code creation request submited with New request number='"+masterForm.getRequestNo()+"'");
						masterForm.setTypeDetails("Update");
						
						if(checkApprover==0)
						{
							masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
						return displayNewGeneralMaterial(mapping, form, request, response);
						}
					}else{
						masterForm.setMessage("Error...When Saving General Material.Please Check....");
						masterForm.setTypeDetails("Save");
					} 
				}else{
						String url="generalMaterial.do?method=editGeneralMaterial";
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
							
						}
				*/		String saveGeneralMaterial="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
								"MATERIAL_GROUP_ID,PURCHASE_GROUP_ID,UNIT_OF_MEAS_ID,APPROXIMATE_VALUE,VALUATION_CLASS,is_asset,UTILIZING_DEPT,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID," +
								"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"'," +
								"'"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getApproximateValue()+"'," +
								"'"+masterForm.getValuationClass()+"','"+masterForm.getIsAsset()+"','"+masterForm.getUtilizingDept()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"'," +
								"'"+masterForm.getPurposeID()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getHsnCode()+"')";
						i=ad.SqlExecuteUpdate(saveGeneralMaterial);
						String approve="Saved";
						if(i>0)
						{
							
							
							masterForm.setMessage2(" Code creation request submited with New request number='"+masterForm.getRequestNo()+"'");
							masterForm.setTypeDetails("Update");
							if(checkApprover==0)
							{
								masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
							return displayNewGeneralMaterial(mapping, form, request, response);
							}
						}else{
							masterForm.setMessage("Error...When Saving General Material.Please Check....");
							masterForm.setTypeDetails("Save");
						} 
						
					}
				/*	String Req_Id = ""+masterForm.getRequestNo();
					EMailer email = new EMailer();
					i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");
		*/	 }else{
				 System.out.println("update General Material ");
				 String approvedStatus="Pending";
					
				 user=(UserInfo)session.getAttribute("user");
					/*if(user.getId()==1)
					{
						approvedStatus=masterForm.getApproveType();
					}
					if(user.getId()==2)
					{
						 String sapCreationDate=masterForm.getSapCreationDate();
						  String b[]=sapCreationDate.split("/");
						  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
						String updateMaterial="update material_code_request set SAP_CODE_NO='"+masterForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+masterForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+masterForm.getSapCreatedBy()+"',REQUESTED_BY='"+masterForm.getRequestedBy()+"'  where REQUEST_NO='"+masterForm.getRequestNo()+"'";
						int j=ad.SqlExecuteUpdate(updateMaterial);
						request.setAttribute("approved", "approved");
						request.setAttribute("sapApprover", "sapApprover");
						
					}
		*/	
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
				 String updateGeneralMaterial="update material_code_request set LOCATION_ID='"+masterForm.getLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"'," +
							"MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',PURCHASE_GROUP_ID='"+masterForm.getPuchaseGroupId()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',APPROXIMATE_VALUE='"+masterForm.getApproximateValue()+"',VALUATION_CLASS='"+masterForm.getValuationClass()+"',is_asset='"+masterForm.getIsAsset()+"',UTILIZING_DEPT='"+masterForm.getUtilizingDept()+"',DETAILED_JUSTIFICATION='"+masterForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+masterForm.getDetailedJustification()+"',PURPOSE_ID='"+masterForm.getPurposeID()+"'," +
							"REQUESTED_BY='"+masterForm.getRequestedBy()+"',MODIFIED_DATE='"+dateNow+"'," +
									"MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Submited' where REQUEST_NO='"+masterForm.getRequestNo()+"'";	 
			int i=0;
			i=ad.SqlExecuteUpdate(updateGeneralMaterial);
			if(i>0)
			{
				masterForm.setMessage2("Code creation request Updated with New request number='"+masterForm.getRequestNo()+"'.");
				masterForm.setTypeDetails("Update");
				if(checkApprover==0)
				{
					masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				return displayNewGeneralMaterial(mapping, form, request, response);
				}
			}else{
				masterForm.setMessage("Error...When Updating General Material.Please Check....");
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
				 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='"+matType+"' and app.Material_Group='"+matGroup+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		  			 getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='"+matType+"' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		  		
		  		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					saveRecReq = saveRecReq + "'"+masterForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+pApprover+"','No','"+user.getUserName()+"','','"+matType+"')";
					int ij=ad.SqlExecuteUpdate(saveRecReq);
					
					String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
		  	  		ad.SqlExecuteUpdate(deleteHistory);
		  			
					String saveInHistory="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
							"MATERIAL_GROUP_ID,PURCHASE_GROUP_ID,UNIT_OF_MEAS_ID,APPROXIMATE_VALUE,VALUATION_CLASS,is_asset,UTILIZING_DEPT,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID," +
							"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"'," +
							"'"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getApproximateValue()+"'," +
							"'"+masterForm.getValuationClass()+"','"+masterForm.getIsAsset()+"','"+masterForm.getUtilizingDept()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"'," +
							"'"+masterForm.getPurposeID()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','','user','"+masterForm.getHsnCode()+"')";
					ad.SqlExecuteUpdate(saveInHistory);
					
					if(!(parllelAppr1.equalsIgnoreCase(""))){
						
					
					 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+masterForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr1+"','No','"+user.getUserName()+"','','"+matType+"')";
					 ad.SqlExecuteUpdate(sendRecParllelAppr1);
					}
					if(!(parllelAppr2.equalsIgnoreCase(""))){
						
						 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
						 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+masterForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr2+"','No','"+user.getUserName()+"','','"+matType+"')";
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
						return displayNewGeneralMaterial(mapping, form, request, response);
						
					}else{
						masterForm.setMessage2("Error while submiting approval...");
					}
		  		}else{
		  			
		  			masterForm.setMessage2("No Approvers are assigned.Please Contact to Admin");
		  		}
					
					
				
				
				
				}
			 
			
				
				LinkedList deptID=new LinkedList();
				LinkedList deptName=new LinkedList();
				String getdepartment="select * from department order by DPTSTXT";
				ResultSet rsdepartment=ad.selectQuery(getdepartment);
				while(rsdepartment.next()){
					deptID.add(rsdepartment.getInt("DPTID"));
					deptName.add(rsdepartment.getString("DPTSTXT"));
				}
				
				masterForm.setDeptId(deptID);
				masterForm.setDeptName(deptName);
				
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
				String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
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
				
				String getunitMesurement="select * from UNIT_MESUREMENT order by UNIT_OF_MEAS_ID";
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
		
				//set Approved Field
				user=(UserInfo)session.getAttribute("user");
				if(user.getId()==1)
				{	
					request.setAttribute("approved", "approved");
				}
				String selectedMaterial=masterForm.getMaterialCodeLists();
				masterForm.setMaterialCodeLists(selectedMaterial);
				
				String materialTypeList=masterForm.getMaterialTypeId();
				masterForm.setMaterialCodeLists(materialTypeList);
		}catch (Exception e) {
		e.printStackTrace();
		}
		
		return mapping.findForward("newGeneralMaterial");
	
		
	}
	
	public ActionForward copyNewGeneralMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	try{
		
		int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
		String materialType="";
		String valuationType="";
		String getvaluationType="select MATERIAL_TYPE_ID,VALUATION_CLASS from material_code_request where REQUEST_NO='"+requstNo+"'";
		ResultSet rsValuationType=ad.selectQuery(getvaluationType);
		while(rsValuationType.next()){
			materialType=rsValuationType.getString("MATERIAL_TYPE_ID");
			valuationType=rsValuationType.getString("VALUATION_CLASS");
		}
		
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
		
		String matType="";
		
		String getMaterialType="select mt.MATERIAL_GROUP_ID from material_code_request m,MATERIAL_TYPE mt where REQUEST_NO='"+requstNo+"' "
				+ "and m.MATERIAL_TYPE_ID=mt.id";
		ResultSet rsMatType=ad.selectQuery(getMaterialType);
		while(rsMatType.next()){
			matType=rsMatType.getString("MATERIAL_GROUP_ID");
		}
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
		
		LinkedList deptID=new LinkedList();
		LinkedList deptName=new LinkedList();
		String getdepartment="select * from department order by DPTSTXT";
		ResultSet rsdepartment=ad.selectQuery(getdepartment);
		while(rsdepartment.next()){
			deptID.add(rsdepartment.getInt("DPTID"));
			deptName.add(rsdepartment.getString("DPTSTXT"));
		}
		
		masterForm.setDeptId(deptID);
		masterForm.setDeptName(deptName);
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
		String getValuation="select * from VALUATION_CLASS where MAT_TYPE=(select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+materialType+"')";
		ResultSet rsValuation=ad.selectQuery(getValuation);
		while(rsValuation.next())
		{
			valuationClassID.add(rsValuation.getString("VALUATION_ID"));
			valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
		}
		masterForm.setValuationClassID(valuationClassID);
		masterForm.setValuationClassName(valuationClassName);
		
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
			masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
			masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
			masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			String isAsset=rs.getString("is_asset");
			if(isAsset.equalsIgnoreCase("1"))
			{
				masterForm.setIsAsset("1");
			}
			if(isAsset.equalsIgnoreCase("0"))
				masterForm.setIsAsset("0");
			masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
			masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
	/*		masterForm.setMaterialUsedIn(rs.getString("MATERIAL_USED_IN"));
			String isEquipment=rs.getString("IS_EQUIPMENT");
			if(isEquipment.equalsIgnoreCase("1"))
			{
				masterForm.setIsEquipment("True");
			}
			if(isEquipment.equalsIgnoreCase("0"))
				masterForm.setIsEquipment("False");
			
			String isSpare=rs.getString("IS_SPARE");
			if(isSpare.equalsIgnoreCase("1"))
			{
				masterForm.setIsSpare("True");
			}
			if(isSpare.equalsIgnoreCase("0"))
				masterForm.setIsSpare("False");
			
			String isNew=rs.getString("IS_NEW");
			if(isNew.equalsIgnoreCase("1"))
			{
				masterForm.setIsNew("True");
			}
			if(isNew.equalsIgnoreCase("0"))
				masterForm.setIsNew("False");
			
			
			masterForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
			masterForm.setPrNumber(rs.getString("PR_NUMBER"));
			masterForm.setPoNumber(rs.getString("PO_NUMBER"));*/
			masterForm.setUtilizingDept(rs.getString("UTILIZING_DEPT"));
			masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
			masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
			masterForm.setPurposeID(rs.getString("PURPOSE_ID"));
			masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
			
			
	 		if(user.getId()==2)
			{
	 			String sapcodeNo=rs.getString("SAP_CODE_NO");
				if(sapcodeNo!=null){
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
		masterForm.setMaterialCodeLists(materialType);
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
				
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	String forwardType="newGeneralMaterial";
	
	if(user.getId()==1)//Approver
	{
		forwardType="approverMaster";
	
	}
	if(user.getId()==2)//SAP Creatore
	{
		forwardType="generalMaterialSAP";
	
	}
	if(user.getId()!=1 && user.getId()!=2)
	{
		forwardType="newGeneralMaterial";
	}
	masterForm.setTypeDetails("Save");
	return mapping.findForward(forwardType);
	}
	public ActionForward saveSAPCrationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	try{
		
		int requstNo=masterForm.getRequestNo();
		
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
		String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
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
		
		String getunitMesurement="select * from UNIT_MESUREMENT  order by UNIT_OF_MEAS_ID";
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
		LinkedList deptID=new LinkedList();
		LinkedList deptName=new LinkedList();
		String getdepartment="select * from department order by DPTSTXT";
		ResultSet rsdepartment=ad.selectQuery(getdepartment);
		while(rsdepartment.next()){
			deptID.add(rsdepartment.getInt("DPTID"));
			deptName.add(rsdepartment.getString("DPTSTXT"));
		}
		
		masterForm.setDeptId(deptID);
		masterForm.setDeptName(deptName);
		String sapCreationDate="";
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
			masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
			masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
			masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
			masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
			masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
			masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
			masterForm.setMaterialUsedIn(rs.getString("MATERIAL_USED_IN"));
			String isEquipment=rs.getString("IS_EQUIPMENT");
			if(isEquipment.equalsIgnoreCase("1"))
			{
				masterForm.setIsEquipment("True");
			}
			if(isEquipment.equalsIgnoreCase("0"))
				masterForm.setIsEquipment("False");
			
			String isSpare=rs.getString("IS_SPARE");
			if(isSpare.equalsIgnoreCase("1"))
			{
				masterForm.setIsSpare("True");
			}
			if(isSpare.equalsIgnoreCase("0"))
				masterForm.setIsSpare("False");
			
			String isNew=rs.getString("IS_NEW");
			if(isNew.equalsIgnoreCase("1"))
			{
				masterForm.setIsNew("True");
			}
			if(isNew.equalsIgnoreCase("0"))
				masterForm.setIsNew("False");
			
			
			masterForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
			masterForm.setPrNumber(rs.getString("PR_NUMBER"));
			masterForm.setPoNumber(rs.getString("PO_NUMBER"));
			masterForm.setUtilizingDept(rs.getString("UTILIZING_DEPT"));
			masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
			masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
			masterForm.setPurposeID(rs.getString("PURPOSE_ID"));
			//masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
			
			
	 		if(user.getId()==2)
			{
	 			String sapcodeNo=rs.getString("SAP_CODE_NO");
				if(sapcodeNo!=null){
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
		masterForm.setTypeDetails("Update");
	}catch (Exception e) {
		e.printStackTrace();
	}

	return mapping.findForward("generalMaterialSAP");
	}
	
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
	try{
		
		int requstNo=masterForm.getRequestNo();
		
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
		String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
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
		
		String getunitMesurement="select * from UNIT_MESUREMENT order by UNIT_OF_MEAS_ID";
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
		
		LinkedList deptID=new LinkedList();
		LinkedList deptName=new LinkedList();
		String getdepartment="select * from department order by DPTSTXT";
		ResultSet rsdepartment=ad.selectQuery(getdepartment);
		while(rsdepartment.next()){
			deptID.add(rsdepartment.getInt("DPTID"));
			deptName.add(rsdepartment.getString("DPTSTXT"));
		}
		
		masterForm.setDeptId(deptID);
		masterForm.setDeptName(deptName);
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
			masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
			masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
			masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
			masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
			masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
			masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
			masterForm.setMaterialUsedIn(rs.getString("MATERIAL_USED_IN"));
			String isEquipment=rs.getString("IS_EQUIPMENT");
			if(isEquipment.equalsIgnoreCase("1"))
			{
				masterForm.setIsEquipment("True");
			}
			if(isEquipment.equalsIgnoreCase("0"))
				masterForm.setIsEquipment("False");
			
			String isSpare=rs.getString("IS_SPARE");
			if(isSpare.equalsIgnoreCase("1"))
			{
				masterForm.setIsSpare("True");
			}
			if(isSpare.equalsIgnoreCase("0"))
				masterForm.setIsSpare("False");
			
			String isNew=rs.getString("IS_NEW");
			if(isNew.equalsIgnoreCase("1"))
			{
				masterForm.setIsNew("True");
			}
			if(isNew.equalsIgnoreCase("0"))
				masterForm.setIsNew("False");
			
			
			masterForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
			masterForm.setPrNumber(rs.getString("PR_NUMBER"));
			masterForm.setPoNumber(rs.getString("PO_NUMBER"));
			masterForm.setUtilizingDept(rs.getString("UTILIZING_DEPT"));
			masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
			masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
			masterForm.setPurposeID(rs.getString("PURPOSE_ID"));
			masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
			
			
	 		if(user.getId()==2)
			{
	 			String sapcodeNo=rs.getString("SAP_CODE_NO");
				if(sapcodeNo!=null){
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
		int k=0;
		String currentdate=EMicroUtils.getCurrentSysDate();
		  String a[]=currentdate.split("/");
		  for(int j=0;j<a.length;j++)
		  {
			  System.out.println("a="+a[j]);
		  }
		  currentdate=a[2]+"-"+a[1]+"-"+a[0];
		 k=ad.SqlExecuteUpdate("update material_code_request set Approve_Type='"+masterForm.getApproveType()+"',approve_date='"+currentdate+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+masterForm.getRequestNo()+"'");
		if(k>0)
		{
			masterForm.setMessage2("Code creation request saved with request number='"+masterForm.getRequestNo()+"'.");
			masterForm.setTypeDetails("Update");
		}else{
			masterForm.setMessage("Error...When saving finished products.Please check....");
		}
		masterForm.setTypeDetails("Update");
	}catch (Exception e) {
		e.printStackTrace();
	}

	return mapping.findForward("approverMaster");
	}
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
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
		
		
		LinkedList listOfGeneralMaterial=new LinkedList();
		String getRecord="";
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='General Material'";
		
		}else{
		if(reqDate.equalsIgnoreCase("")&&locationId.equalsIgnoreCase("") ){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"'  and l.LOCID=m.LOCATION_ID and m.Type='General Material'";
		}
		 if(reqDate.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='General Material'";
		}
		 if(locationId.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.Location_ID=l.LOCID and m.Type='General Material'";
		}
		 if(requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&&!locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='General Material'";
		}
		 if(reqDate.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='General Material'";
		}
		if(locationId.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !reqDate.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.REQUEST_DATE='"+reqDate+"' and l.LOCID=m.LOCATION_ID and m.Type='General Material'";
		}
		
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='General Material'";
		
		}
		}
		ResultSet rs=ad.selectQuery(getRecord);
		while(rs.next())
		{
			GeneralMaterialForm rawRecord=new GeneralMaterialForm();
			
			rawRecord.setLocationId(rs.getString("LOCNAME"));
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String b[]=requestDate.split("-");
			requestDate=b[2]+"/"+b[1]+"/"+b[0];
			rawRecord.setRequestDate(requestDate);
			rawRecord.setRequestNo(rs.getInt("REQUEST_NO"));
			
			
			listOfGeneralMaterial.add(rawRecord);
		}
		request.setAttribute("listOfGeneralMaterial", listOfGeneralMaterial);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("generalMaterialList");
		
		
	}
	public ActionForward editGeneralMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String reqMaterialType=request.getParameter("MAT_TYPE");
	try{
	
		int requstNo=Integer.parseInt(request.getParameter("requstNo"));
		String materialType="";
		String valuationType="";
		String getvaluationType="select MATERIAL_TYPE_ID,VALUATION_CLASS from material_code_request where REQUEST_NO='"+requstNo+"'";
		ResultSet rsValuationType=ad.selectQuery(getvaluationType);
		while(rsValuationType.next()){
			materialType=rsValuationType.getString("MATERIAL_TYPE_ID");
			valuationType=rsValuationType.getString("VALUATION_CLASS");
		}
		
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='"+reqMaterialType+"'";
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
		String getMaterialGroup="select * from MATERIAL_GROUP where  MATERIAL_TYPE like '%"+reqMaterialType+"%' order by STXT";
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
		
		LinkedList deptID=new LinkedList();
		LinkedList deptName=new LinkedList();
		String getdepartment="select * from department order by DPTSTXT";
		ResultSet rsdepartment=ad.selectQuery(getdepartment);
		while(rsdepartment.next()){
			deptID.add(rsdepartment.getInt("DPTID"));
			deptName.add(rsdepartment.getString("DPTSTXT"));
		}
		
		masterForm.setDeptId(deptID);
		masterForm.setDeptName(deptName);
		String getunitMesurement="select * from UNIT_MESUREMENT order by UNIT_OF_MEAS_ID";
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
		String getValuation="select * from VALUATION_CLASS where MAT_TYPE='"+reqMaterialType+"'";
		ResultSet rsValuation=ad.selectQuery(getValuation);
		while(rsValuation.next())
		{
			valuationClassID.add(rsValuation.getString("VALUATION_ID"));
			valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
		}
		masterForm.setValuationClassID(valuationClassID);
		masterForm.setValuationClassName(valuationClassName);
		
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
			masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
			masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
			masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
			masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
			masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
			masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
			masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
			masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
	/*		masterForm.setMaterialUsedIn(rs.getString("MATERIAL_USED_IN"));
			String isEquipment=rs.getString("IS_EQUIPMENT");
			if(isEquipment.equalsIgnoreCase("1"))
			{
				masterForm.setIsEquipment("True");
			}
			if(isEquipment.equalsIgnoreCase("0"))
				masterForm.setIsEquipment("False");
			
			String isSpare=rs.getString("IS_SPARE");
			if(isSpare.equalsIgnoreCase("1"))
			{
				masterForm.setIsSpare("True");
			}
			if(isSpare.equalsIgnoreCase("0"))
				masterForm.setIsSpare("False");
			
			String isNew=rs.getString("IS_NEW");
			if(isNew.equalsIgnoreCase("1"))
			{
				masterForm.setIsNew("True");
			}
			if(isNew.equalsIgnoreCase("0"))
				masterForm.setIsNew("False");
			
			
			masterForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
			masterForm.setPrNumber(rs.getString("PR_NUMBER"));
			masterForm.setPoNumber(rs.getString("PO_NUMBER"));*/
			masterForm.setUtilizingDept(rs.getString("UTILIZING_DEPT"));
			String isAsset=rs.getString("is_asset");
			if(isAsset==null)
				isAsset="";
			if(isAsset.equalsIgnoreCase("1"))
			{
				masterForm.setIsAsset("1");
			}
			if(isAsset.equalsIgnoreCase("0"))
				masterForm.setIsAsset("0");
			masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
			masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
			masterForm.setPurposeID(rs.getString("PURPOSE_ID"));
			masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
			
			
	 		if(user.getId()==2)
			{
	 			String sapcodeNo=rs.getString("SAP_CODE_NO");
				if(sapcodeNo!=null){
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
		
		masterForm.setMaterialCodeLists(materialType);
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
				
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	String forwardType="newGeneralMaterial";
	
	if(user.getId()==1)//Approver
	{
		forwardType="approverMaster";
	
	}
	if(user.getId()==2)//SAP Creatore
	{
		forwardType="generalMaterialSAP";
	
	}
	if(user.getId()!=1 && user.getId()!=2)
	{
		forwardType="newGeneralMaterial";
	}
	return mapping.findForward(forwardType);
	}
	
	public ActionForward deleteGeneralMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
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
		   displayGeneralMaterialList(mapping, form, request, response);
		
		return mapping.findForward("generalMaterialList");
	}
	public ActionForward saveGeneralMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
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
	           SimpleDateFormat ft = 
	           new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");

	           System.out.println("Current Date: " + ft.format(dNow));
			  String dateNow = ft.format(dNow);
			  String reqDate=masterForm.getRequestDate();
			  String a[]=reqDate.split("/");
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			 /* String sapCreationDate=masterForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];*/
			String typeDetails=masterForm.getTypeDetails();
			String materialType="";
			
			
			 if(typeDetails.equalsIgnoreCase("Save"))
			 {
				 
				 
				
				 if(masterForm.getMaterialTypeId().equalsIgnoreCase("10"))
				 {
					 materialType="LC";
				 }
				 if(masterForm.getMaterialTypeId().equalsIgnoreCase("7"))
				 {
					 materialType="ZCIV";
				 }
				 if(masterForm.getMaterialTypeId().equalsIgnoreCase("8"))
				 {
					 materialType="ZCON";
				 }
				 if(masterForm.getMaterialTypeId().equalsIgnoreCase("14"))
				 {
					 materialType="ZSCR";
				 }
				 if(masterForm.getMaterialTypeId().equalsIgnoreCase("9"))
				 {
					 materialType="ZITC";
				 }
				 if(masterForm.getMaterialTypeId().equalsIgnoreCase("11"))
				 {
					 materialType="ZPFL";
				 }
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
					if(count>0){
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
						
				  String url="generalMaterial.do?method=editGeneralMaterial";
				  String saveGeneralMaterial="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
					"MATERIAL_GROUP_ID,PURCHASE_GROUP_ID,UNIT_OF_MEAS_ID,APPROXIMATE_VALUE,VALUATION_CLASS,is_asset,UTILIZING_DEPT,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID," +
					"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"'," +
					"'"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getApproximateValue()+"'," +
					"'"+masterForm.getValuationClass()+"','"+masterForm.getIsAsset()+"','"+masterForm.getUtilizingDept()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"'," +
					"'"+masterForm.getPurposeID()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getHsnCode()+"')";
		
					i=ad.SqlExecuteUpdate(saveGeneralMaterial);
					
					
		  			
				
					
					String approve="Pending";
						if(i>0)
					{
						masterForm.setMessage2("Code creation request saved with request number='"+masterForm.getRequestNo()+"'");
						masterForm.setTypeDetails("Save");
						
						String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
			  	  		ad.SqlExecuteUpdate(deleteHistory);
						String saveInHistory="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
								"MATERIAL_GROUP_ID,PURCHASE_GROUP_ID,UNIT_OF_MEAS_ID,APPROXIMATE_VALUE,VALUATION_CLASS,is_asset,UTILIZING_DEPT,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID," +
								"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"'," +
								"'"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getApproximateValue()+"'," +
								"'"+masterForm.getValuationClass()+"','"+masterForm.getIsAsset()+"','"+masterForm.getUtilizingDept()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"'," +
								"'"+masterForm.getPurposeID()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','','user','"+masterForm.getHsnCode()+"')";
						ad.SqlExecuteUpdate(saveInHistory);
						
						return displayNewGeneralMaterial(mapping, form, request, response);
					}else{
						masterForm.setMessage("Error...When Saving General Material.Please Check....");
						masterForm.setTypeDetails("Save");
					} 
				}else{
						String url="generalMaterial.do?method=editGeneralMaterial";
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
							
						}
				*/		String saveGeneralMaterial="insert into material_code_request(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
								"MATERIAL_GROUP_ID,PURCHASE_GROUP_ID,UNIT_OF_MEAS_ID,APPROXIMATE_VALUE,VALUATION_CLASS,is_asset,UTILIZING_DEPT,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID," +
								"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"'," +
								"'"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getApproximateValue()+"'," +
								"'"+masterForm.getValuationClass()+"','"+masterForm.getIsAsset()+"','"+masterForm.getUtilizingDept()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"'," +
								"'"+masterForm.getPurposeID()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getHsnCode()+"')";
						i=ad.SqlExecuteUpdate(saveGeneralMaterial);
						String approve="Saved";
						if(i>0)
						{
							masterForm.setMessage2(" Code creation request created with New request number='"+masterForm.getRequestNo()+"'");
							masterForm.setTypeDetails("Save");
							
							String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
				  	  		ad.SqlExecuteUpdate(deleteHistory);
							String saveInHistory="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
									"MATERIAL_GROUP_ID,PURCHASE_GROUP_ID,UNIT_OF_MEAS_ID,APPROXIMATE_VALUE,VALUATION_CLASS,is_asset,UTILIZING_DEPT,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID," +
									"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"'," +
									"'"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getApproximateValue()+"'," +
									"'"+masterForm.getValuationClass()+"','"+masterForm.getIsAsset()+"','"+masterForm.getUtilizingDept()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"'," +
									"'"+masterForm.getPurposeID()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','','user','"+masterForm.getHsnCode()+"')";
							ad.SqlExecuteUpdate(saveInHistory);
							
							return displayNewGeneralMaterial(mapping, form, request, response);
						}else{
							masterForm.setMessage("Error...When Saving General Material.Please Check....");
							masterForm.setTypeDetails("Save");
						} 
						
					}
				/*	String Req_Id = ""+masterForm.getRequestNo();
					EMailer email = new EMailer();
					i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");
		*/	 }else{
				 System.out.println("update General Material ");
				 String approvedStatus="Pending";
					
				 user=(UserInfo)session.getAttribute("user");
					/*if(user.getId()==1)
					{
						approvedStatus=masterForm.getApproveType();
					}
					if(user.getId()==2)
					{
						 String sapCreationDate=masterForm.getSapCreationDate();
						  String b[]=sapCreationDate.split("/");
						  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
						String updateMaterial="update material_code_request set SAP_CODE_NO='"+masterForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+masterForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+masterForm.getSapCreatedBy()+"',REQUESTED_BY='"+masterForm.getRequestedBy()+"'  where REQUEST_NO='"+masterForm.getRequestNo()+"'";
						int j=ad.SqlExecuteUpdate(updateMaterial);
						request.setAttribute("approved", "approved");
						request.setAttribute("sapApprover", "sapApprover");
						
					}
		*/
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
				 String updateGeneralMaterial="update material_code_request set LOCATION_ID='"+masterForm.getLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"'," +
							"MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',PURCHASE_GROUP_ID='"+masterForm.getPuchaseGroupId()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',APPROXIMATE_VALUE='"+masterForm.getApproximateValue()+"',VALUATION_CLASS='"+masterForm.getValuationClass()+"',is_asset='"+masterForm.getIsAsset()+"',UTILIZING_DEPT='"+masterForm.getUtilizingDept()+"',DETAILED_JUSTIFICATION='"+masterForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+masterForm.getDetailedSpecification()+"',PURPOSE_ID='"+masterForm.getPurposeID()+"'," +
							"REQUESTED_BY='"+masterForm.getRequestedBy()+"',MODIFIED_DATE='"+dateNow+"'," +
									"MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',HSN_Code='"+masterForm.getHsnCode()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";	 
			int i=0;
			i=ad.SqlExecuteUpdate(updateGeneralMaterial);
			if(i>0)
			{
				masterForm.setMessage2("Code creation request Updated with request number='"+masterForm.getRequestNo()+"'.");
				masterForm.setTypeDetails("Update");
				
				String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  	  		ad.SqlExecuteUpdate(deleteHistory);
	  	  		
				String saveInHistory="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME," +
						"MATERIAL_GROUP_ID,PURCHASE_GROUP_ID,UNIT_OF_MEAS_ID,APPROXIMATE_VALUE,VALUATION_CLASS,is_asset,UTILIZING_DEPT,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID," +
						"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getLocationId()+"','"+masterForm.getMaterialTypeId()+"'," +
						"'"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getApproximateValue()+"'," +
						"'"+masterForm.getValuationClass()+"','"+masterForm.getIsAsset()+"','"+masterForm.getUtilizingDept()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"'," +
						"'"+masterForm.getPurposeID()+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+materialType+"','Created','','user','"+masterForm.getHsnCode()+"')";
				ad.SqlExecuteUpdate(saveInHistory);
			}else{
				masterForm.setMessage("Error...When Updating General Material.Please Check....");
				masterForm.setTypeDetails("Update");
			}	 
				 
			 }
			 
			
				
				LinkedList deptID=new LinkedList();
				LinkedList deptName=new LinkedList();
				String getdepartment="select * from department order by DPTSTXT";
				ResultSet rsdepartment=ad.selectQuery(getdepartment);
				while(rsdepartment.next()){
					deptID.add(rsdepartment.getInt("DPTID"));
					deptName.add(rsdepartment.getString("DPTSTXT"));
				}
				
				masterForm.setDeptId(deptID);
				masterForm.setDeptName(deptName);
				
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
				String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
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
				
				String getunitMesurement="select * from UNIT_MESUREMENT order by UNIT_OF_MEAS_ID";
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
		
				//set Approved Field
				user=(UserInfo)session.getAttribute("user");
				if(user.getId()==1)
				{	
					request.setAttribute("approved", "approved");
				}
				String selectedMaterial=masterForm.getMaterialCodeLists();
				masterForm.setMaterialCodeLists(selectedMaterial);
				
				String materialTypeList=masterForm.getMaterialTypeId();
				masterForm.setMaterialCodeLists(materialTypeList);
		}catch (Exception e) {
		e.printStackTrace();
		}
		
		return mapping.findForward("newGeneralMaterial");
	}
	
	public ActionForward displayNewGeneralMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		masterForm.setLocationId(user.getPlantId());
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
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String materialTypeId=request.getParameter("materialType");
			if(materialTypeId==null)
			{
				materialTypeId=masterForm.getMaterialTypeId();
			}
			String materialname="";
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
			String matType="";
			String getMaterialType="select MATERIAL_GROUP_ID from MATERIAL_TYPE where id='"+materialTypeId+"'";
			ResultSet rsMatType=ad.selectQuery(getMaterialType);
			while(rsMatType.next()){
				matType=rsMatType.getString("MATERIAL_GROUP_ID");
			}
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
			
			String getunitMesurement="select * from UNIT_MESUREMENT order by UNIT_OF_MEAS_ID";
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
			
			
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
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
			
			
			LinkedList deptID=new LinkedList();
			LinkedList deptName=new LinkedList();
			String getdepartment="select * from department order by DPTSTXT";
			ResultSet rsdepartment=ad.selectQuery(getdepartment);
			while(rsdepartment.next()){
				deptID.add(rsdepartment.getInt("DPTID"));
				deptName.add(rsdepartment.getString("DPTSTXT"));
			}
			
			masterForm.setDeptId(deptID);
			masterForm.setDeptName(deptName);
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
			masterForm.setLocationId(locationId);
			masterForm.setTypeDetails("Save");
			masterForm.setMaterialCodeLists(materialname);
			masterForm.setMaterialCodeLists1("");
			masterForm.setMaterialCodeLists(materialTypeId);
			
		
			masterForm.setMaterialTypeId(materialTypeId);
			masterForm.setStorageLocationId("");
			
			masterForm.setMaterialShortName(shortName);
			masterForm.setMaterialLongName(longName);
			masterForm.setMaterialGroupId(materialGrup);
			masterForm.setPuchaseGroupId("");
			masterForm.setUnitOfMeasId("");
			masterForm.setApproximateValue("");
			masterForm.setValuationClass("");
			masterForm.setMaterialUsedIn("");
			
				masterForm.setIsEquipment("");
			masterForm.setIsSpare("");
			
				masterForm.setIsNew("");
			masterForm.setEquipmentName("");
			masterForm.setPrNumber("");
			masterForm.setPoNumber("");
			masterForm.setUtilizingDept("");
			masterForm.setDetailedJustification("");
			masterForm.setDetailedSpecification("");
			masterForm.setPurposeID("");
			masterForm.setSapCodeNo("");
			masterForm.setIsAsset("");
			
				masterForm.setSapCodeExists("");
			
			
			masterForm.setSapCreationDate("");
			masterForm.setSapCreatedBy("");
			masterForm.setRequestedBy("");
			
			
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
		
		return mapping.findForward("newGeneralMaterial");
	}
	
	
	public ActionForward displayGeneralMaterialList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		GeneralMaterialForm masterForm=(GeneralMaterialForm)form;
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
		LinkedList listOfGeneralMaterial=new LinkedList();
		String getList="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.Type='General Material'  and m.LOCATION_ID=l.LOCID";
		ResultSet rsList=ad.selectQuery(getList);
		while(rsList.next())
		{
			GeneralMaterialForm codeMasterForm=new GeneralMaterialForm();
			codeMasterForm.setRequestNo(rsList.getInt("REQUEST_NO"));
			String requestDate=rsList.getString("REQUEST_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			codeMasterForm.setRequestDate(requestDate);
			codeMasterForm.setLocationId(rsList.getString("LOCNAME"));
			listOfGeneralMaterial.add(codeMasterForm);
		}
		request.setAttribute("listOfGeneralMaterial", listOfGeneralMaterial);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("generalMaterialList");
	}
}
