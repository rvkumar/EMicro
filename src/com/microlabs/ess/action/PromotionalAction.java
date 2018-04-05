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
import com.microlabs.ess.form.PromotionalForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class PromotionalAction extends DispatchAction{
	
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		PromotionalForm masterForm=(PromotionalForm)form;
		EssDao ad=EssDao.dBConnection();
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
		
		PromotionalForm masterForm=(PromotionalForm)form;
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
			 
			
			String typeDetail=masterForm.getTypeDetails();
			
			
			 int checkApprover=0;
			  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='PPC' and " +
		  		" Location=(select LOCATION_CODE from Location where LOCID='"+masterForm.getLocationId()+"')";
			  ResultSet rsCheck=ad.selectQuery(checkApprovers);
			  while(rsCheck.next())
			  {
				  checkApprover=1;
				  
			  }
			
			 if(typeDetail.equalsIgnoreCase("Save"))
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
					int maxReqno=0;
					String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
					ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
					while(rsReqestNumber.next())
					{
						maxReqno=rsReqestNumber.getInt(1);
					}
					maxReqno+=1;
					masterForm.setRequestNo(maxReqno);
					masterForm.setRequestDate(EMicroUtils.getCurrentSysDate());
					
				 
				 String url="promotional.do?method=editPromotional";
					String savePromotional="insert into material_code_request(BRAND_ID,REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME," +
							"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,DIVISION_ID,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,is_asset,APPROXIMATE_VALUE,VALUATION_CLASS,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID,IS_SAS_FORM_AVAILABLE," +
							"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,HSN_Code) values('"+masterForm.getBrandID()+"','"+masterForm.getRequestNo()+"','"+reqDate+"'," +
					"'"+masterForm.getLocationId()+"','12','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getDivisionId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getIsAsset()+"'," +
					"'"+masterForm.getApproximateValue()+"','"+masterForm.getValuationClass()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"','"+masterForm.getPurposeID()+"','"+masterForm.getIsSASFormAvailable()+"'," +
					"'"+dateNow+"','"+user.getEmployeeNo()+"','PPC','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getHsnCode()+"')";
					i=ad.SqlExecuteUpdate(savePromotional);
					 String approve="Pending";
								
					if(i>0)
					{
						masterForm.setMessage2("Alert Code creation request Submited with New request number='"+masterForm.getRequestNo()+"'.");
						masterForm.setTypeDetails("Update");
						if(checkApprover==0)
						{
							masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
						return displayNewPromotional(mapping, form, request, response);
						}
					}else{
						masterForm.setMessage("Error...When Saving Promotional.Please Check....");
						masterForm.setTypeDetails("Save");
					} 
				}else{
					String url="promotional.do?method=editPromotional";
					String savePromotional="insert into material_code_request(BRAND_ID,REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME," +
							"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,DIVISION_ID,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,is_asset,APPROXIMATE_VALUE,VALUATION_CLASS,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID,IS_SAS_FORM_AVAILABLE," +
							"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,HSN_Code) values('"+masterForm.getBrandID()+"','"+masterForm.getRequestNo()+"','"+reqDate+"'," +
					"'"+masterForm.getLocationId()+"','12','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getDivisionId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getIsAsset()+"'," +
					"'"+masterForm.getApproximateValue()+"','"+masterForm.getValuationClass()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"','"+masterForm.getPurposeID()+"','"+masterForm.getIsSASFormAvailable()+"'," +
					"'"+dateNow+"','"+user.getEmployeeNo()+"','PPC','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getHsnCode()+"')";
					i=ad.SqlExecuteUpdate(savePromotional);
					 String approve="Saved";
								
					if(i>0)
					{
						masterForm.setMessage2("Code creation request Submited with request number='"+masterForm.getRequestNo()+"'.");
						masterForm.setTypeDetails("Update");
						if(checkApprover==0)
						{
							masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
						return displayNewPromotional(mapping, form, request, response);
						}
						
					}else{
						masterForm.setMessage("Error...When Saving Promotional.Please Check....");
						masterForm.setTypeDetails("Save");
					} 
					
				}
				/*String Req_Id = ""+masterForm.getRequestNo();
				EMailer email = new EMailer();
				i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");
		*/	 }else{
				 String sss=masterForm.getIsSASFormAvailable();
				 System.out.println("update Finished Products");
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
					 String updateFinishedProduct="update material_code_request set BRAND_ID='"+masterForm.getBrandID()+"',LOCATION_ID='"+masterForm.getLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"'," +
							"MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',DIVISION_ID='"+masterForm.getDivisionId()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+masterForm.getPuchaseGroupId()+"',is_asset='"+masterForm.getIsAsset()+"',APPROXIMATE_VALUE='"+masterForm.getApproximateValue()+"'," +
							"VALUATION_CLASS='"+masterForm.getValuationClass()+"',DETAILED_JUSTIFICATION='"+masterForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+masterForm.getDetailedSpecification()+"',PURPOSE_ID='"+masterForm.getPurposeID()+"',IS_SAS_FORM_AVAILABLE='"+masterForm.getIsSASFormAvailable()+"'," +
							"MODIFIED_DATE='"+dateNow+"'," +
							"MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',HSN_Code='"+masterForm.getHsnCode()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";	 
			int i=0;
			i=ad.SqlExecuteUpdate(updateFinishedProduct);
			if(i>0)
			{
				masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'.");
				masterForm.setTypeDetails("Update");
				if(checkApprover==0)
				{
					masterForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				return displayNewPromotional(mapping, form, request, response);
				}
			}else{
				masterForm.setMessage("Error...When Updating Promotional.Please Check....");
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
					saveRecReq = saveRecReq + "'"+masterForm.getRequestNo()+"','Material Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','"+matType+"')";
					int ij=ad.SqlExecuteUpdate(saveRecReq);
					
					String deleteHistory="delete material_code_request_history where REQUEST_NO='"+masterForm.getRequestNo()+"'";
		  	  		ad.SqlExecuteUpdate(deleteHistory);
		  			
					String savePromotionalHistory="insert into material_code_request_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME," +
							"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,DIVISION_ID,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,is_asset,APPROXIMATE_VALUE,VALUATION_CLASS,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID,IS_SAS_FORM_AVAILABLE," +
							"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,Role,HSN_Code) values('"+masterForm.getRequestNo()+"','"+reqDate+"'," +
					"'"+masterForm.getLocationId()+"','12','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getDivisionId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getIsAsset()+"'," +
					"'"+masterForm.getApproximateValue()+"','"+masterForm.getValuationClass()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"','"+masterForm.getPurposeID()+"','"+masterForm.getIsSASFormAvailable()+"'," +
					"'"+dateNow+"','"+user.getEmployeeNo()+"','PPC','Created','','user','"+masterForm.getHsnCode()+"')";
					ad.SqlExecuteUpdate(savePromotionalHistory);
					
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
						return displayNewPromotional(mapping, form, request, response);
						
					}else{
						masterForm.setMessage2("Error while submiting approval...");
					}
		  		}else{
		  			
		  			masterForm.setMessage2("No Approvers are assigned.Please Contact to Admin");
		  		}
					
					
				
				
				}
			 
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
			 
			 
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LANDX"));
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
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PPC%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
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
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PPC'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
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
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PPC'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			//set Approved Field
			user=(UserInfo)session.getAttribute("user");
			if(user.getId()==1)
			{	
				request.setAttribute("approved", "approved");
			}
			masterForm.setMaterialCodeLists("12");
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("newPromotional");
	}
	
	public ActionForward copyNewPromotional(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PromotionalForm masterForm=(PromotionalForm)form;
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
				countryName.add(rsCountryDetails.getString("LANDX"));
			}
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
			
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
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PPC%' order by STXT";
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
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PPC'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
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
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PPC'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			
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
				masterForm.setLocationId(rs.getString("LOCATION_ID"));
				masterForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
				masterForm.setDivisionId(rs.getString("DIVISION_ID"));
				masterForm.setBrandID(rs.getString("BRAND_ID"));
				String isAsset=rs.getString("is_asset");
				if(isAsset.equalsIgnoreCase("1"))
				{
					masterForm.setIsAsset("1");
				}
				if(isAsset.equalsIgnoreCase("0"))
					masterForm.setIsAsset("0");
				
				
				masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
				masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
				String purposeId=rs.getString("PURPOSE_ID");
				purposeId=purposeId.replace(" ","");
				masterForm.setPurposeID(purposeId);
				String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
				
				if(isSAS.equalsIgnoreCase("1"))
				{
					masterForm.setIsSASFormAvailable("1");
				}
				if(isSAS.equalsIgnoreCase("0"))
					masterForm.setIsSASFormAvailable("0");
				
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
			masterForm.setMaterialCodeLists("12");
			
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
String forwardType="newPromotional";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="promotionalSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newPromotional";
		}
		masterForm.setTypeDetails("Save");
		return mapping.findForward(forwardType);
	}
	
	public ActionForward saveSAPCrationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PromotionalForm masterForm=(PromotionalForm)form;
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
				countryName.add(rsCountryDetails.getString("LANDX"));
			}
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
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
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PPC%' order by STXT";
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
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PPC'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
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
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PPC'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
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
				masterForm.setMaterialTypeId("12");
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
				masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
				masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
				String purposeId=rs.getString("PURPOSE_ID");
				purposeId=purposeId.replace(" ","");
				masterForm.setPurposeID(purposeId);
				String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
				
				if(isSAS.equalsIgnoreCase("1"))
				{
					masterForm.setIsSASFormAvailable("True");
				}
				if(isSAS.equalsIgnoreCase("0"))
					masterForm.setIsSASFormAvailable("False");
				
				
				
			}
			masterForm.setMaterialCodeLists("ZPPC");
			
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
		return mapping.findForward("newPromotional");
	
		
	}
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PromotionalForm masterForm=(PromotionalForm)form;
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
				countryName.add(rsCountryDetails.getString("LANDX"));
			}
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
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
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PPC%' order by STXT";
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
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PPC'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
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
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PPC'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			
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
				masterForm.setMaterialTypeId("12");
				masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
				masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				masterForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
				masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
				masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
				masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
				String purposeId=rs.getString("PURPOSE_ID");
				purposeId=purposeId.replace(" ","");
				masterForm.setPurposeID(purposeId);
				String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
				
				if(isSAS.equalsIgnoreCase("1"))
				{
					masterForm.setIsSASFormAvailable("True");
				}
				if(isSAS.equalsIgnoreCase("0"))
					masterForm.setIsSASFormAvailable("False");
				
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
			//	masterForm.setApproveType(rs.getString("Approve_Type"));
					request.setAttribute("approved", "approved");
				}
				
			}
			masterForm.setMaterialCodeLists("ZPPC");
			
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
		return mapping.findForward("newPromotional");
	}
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		PromotionalForm masterForm=(PromotionalForm)form;
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
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Promotional'";
		
		}else{
		if(reqDate.equalsIgnoreCase("")&&locationId.equalsIgnoreCase("") ){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"'  and l.LOCID=m.LOCATION_ID and m.Type='Promotional'";
		}
		 if(reqDate.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Promotional'";
		}
		 if(locationId.equalsIgnoreCase("")&&requestNumber.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.Location_ID=l.LOCID and m.Type='Promotional'";
		}
		 if(requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&&!locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Promotional'";
		}
		 if(reqDate.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !locationId.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Promotional'";
		}
		if(locationId.equalsIgnoreCase("") && !requestNumber.equalsIgnoreCase("") && !reqDate.equalsIgnoreCase("")){
			getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and m.REQUEST_DATE='"+reqDate+"' and l.LOCID=m.LOCATION_ID and m.Type='Promotional'";
		}
		
		if(!requestNumber.equalsIgnoreCase("")&& !reqDate.equalsIgnoreCase("")&& !locationId.equalsIgnoreCase(""))
		 {
		getRecord="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.REQUEST_NO='"+requestNumber+"' and  m.REQUEST_DATE='"+reqDate+"' and m.LOCATION_ID='"+locationId+"' and l.LOCID='"+locationId+"' and m.Type='Promotional'";
		
		}
		}
		
		ResultSet rs=ad.selectQuery(getRecord);
		while(rs.next())
		{
			PromotionalForm rawRecord=new PromotionalForm();
			
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
		request.setAttribute("listOfPromotional", rawRecordList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("promotionalList");
		
		
	}
	public ActionForward deleteMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PromotionalForm masterForm=(PromotionalForm)form;
		EssDao ad=new EssDao();
		int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
		   String deleteRecord="delete from material_code_request where REQUEST_NO='"+requstNo+"'";
		   int i=0;
		   i=ad.SqlExecuteUpdate(deleteRecord);
		   if(i>=1)
		   {
			   masterForm.setMessage("Promotional Has Been Successfully Deleted");
		   }else{
			   masterForm.setMessage("Error.... When Deleting Promotional.");
		   }
		
		   displayPromotionalList(mapping, form, request, response);
		
		return mapping.findForward("promotionalList");
	}
	public ActionForward editPromotional(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PromotionalForm masterForm=(PromotionalForm)form;
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
				countryName.add(rsCountryDetails.getString("LANDX"));
			}
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			int requstNo=Integer.parseInt(request.getParameter("requstNo"));
			
			
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
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PPC%' order by STXT";
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
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PPC'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
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
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PPC'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			
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
				masterForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
				masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
				masterForm.setDivisionId(rs.getString("DIVISION_ID"));
				masterForm.setBrandID(rs.getString("BRAND_ID"));
				String isAsset=rs.getString("is_asset");
				if(isAsset.equalsIgnoreCase("1"))
				{
					masterForm.setIsAsset("1");
				}
				if(isAsset.equalsIgnoreCase("0"))
					masterForm.setIsAsset("0");
				
				masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				masterForm.setValuationClass(rs.getString("VALUATION_CLASS"));
				masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
				masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
				
				String purposeId=rs.getString("PURPOSE_ID");
				purposeId=purposeId.replace(" ","");
				masterForm.setPurposeID(purposeId);
				String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
				
				if(isSAS.equalsIgnoreCase("1"))
				{
					masterForm.setIsSASFormAvailable("1");
				}
				if(isSAS.equalsIgnoreCase("0"))
					masterForm.setIsSASFormAvailable("0");
				
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
			masterForm.setMaterialCodeLists("12");
			
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
String forwardType="newPromotional";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="promotionalSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="newPromotional";
		}
		return mapping.findForward(forwardType);
	}
	
	public ActionForward savePromotional(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		PromotionalForm masterForm=(PromotionalForm)form;
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
			
			String typeDetail=masterForm.getTypeDetails();
			
			
			 if(typeDetail.equalsIgnoreCase("Save"))
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
					int maxReqno=0;
					String getReqestNumber="select max(REQUEST_NO)  from material_code_request";
					ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
					while(rsReqestNumber.next())
					{
						maxReqno=rsReqestNumber.getInt(1);
					}
					maxReqno+=1;
					masterForm.setRequestNo(maxReqno);
					masterForm.setRequestDate(EMicroUtils.getCurrentSysDate());
					
				 
				 String url="promotional.do?method=editPromotional";
					String savePromotional="insert into material_code_request(BRAND_ID,REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME," +
							"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,DIVISION_ID,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,is_asset,APPROXIMATE_VALUE,VALUATION_CLASS,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID,IS_SAS_FORM_AVAILABLE," +
							"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,HSN_Code) values('"+masterForm.getBrandID()+"','"+masterForm.getRequestNo()+"','"+reqDate+"'," +
					"'"+masterForm.getLocationId()+"','12','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getDivisionId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getIsAsset()+"'," +
					"'"+masterForm.getApproximateValue()+"','"+masterForm.getValuationClass()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"','"+masterForm.getPurposeID()+"','"+masterForm.getIsSASFormAvailable()+"'," +
					"'"+dateNow+"','"+user.getEmployeeNo()+"','PPC','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getHsnCode()+"')";
					i=ad.SqlExecuteUpdate(savePromotional);
					 String approve="Pending";
								
					if(i>0)
					{
						masterForm.setMessage2("Alert Code creation request created with New request number='"+masterForm.getRequestNo()+"'.");
						masterForm.setTypeDetails("Update");
						return displayNewPromotional(mapping, form, request, response);
					}else{
						masterForm.setMessage("Error...When Saving Promotional.Please Check....");
						masterForm.setTypeDetails("Save");
					} 
				}else{
					String url="promotional.do?method=editPromotional";
					String savePromotional="insert into material_code_request(BRAND_ID,REQUEST_NO,REQUEST_DATE,LOCATION_ID,MATERIAL_TYPE_ID,STORAGE_LOCATION_ID,MATERIAL_SHORT_NAME," +
							"MATERIAL_LONG_NAME,MATERIAL_GROUP_ID,DIVISION_ID,UNIT_OF_MEAS_ID,PURCHASE_GROUP_ID,is_asset,APPROXIMATE_VALUE,VALUATION_CLASS,DETAILED_JUSTIFICATION,DETAILED_SPECIFICATION,PURPOSE_ID,IS_SAS_FORM_AVAILABLE," +
							"CREATED_DATE,CREATED_BY,Type,Approve_Type,URL,last_approver,pending_approver,HSN_Code) values('"+masterForm.getBrandID()+"','"+masterForm.getRequestNo()+"','"+reqDate+"'," +
					"'"+masterForm.getLocationId()+"','12','"+masterForm.getStorageLocationId()+"','"+masterForm.getMaterialShortName()+"','"+masterForm.getMaterialLongName()+"','"+masterForm.getMaterialGroupId()+"','"+masterForm.getDivisionId()+"','"+masterForm.getUnitOfMeasId()+"','"+masterForm.getPuchaseGroupId()+"','"+masterForm.getIsAsset()+"'," +
					"'"+masterForm.getApproximateValue()+"','"+masterForm.getValuationClass()+"','"+masterForm.getDetailedJustification()+"','"+masterForm.getDetailedSpecification()+"','"+masterForm.getPurposeID()+"','"+masterForm.getIsSASFormAvailable()+"'," +
					"'"+dateNow+"','"+user.getEmployeeNo()+"','PPC','Created','"+url+"','No','"+pendingApprovers+"','"+masterForm.getHsnCode()+"')";
					i=ad.SqlExecuteUpdate(savePromotional);
					 String approve="Saved";
								
					if(i>0)
					{
						masterForm.setMessage2("Code creation request created with request number='"+masterForm.getRequestNo()+"'.");
						masterForm.setTypeDetails("Update");
						return displayNewPromotional(mapping, form, request, response);
						
					}else{
						masterForm.setMessage("Error...When Saving Promotional.Please Check....");
						masterForm.setTypeDetails("Save");
					} 
					
				}
				/*String Req_Id = ""+masterForm.getRequestNo();
				EMailer email = new EMailer();
				i = email.sendMailToApprover(request, approvermail,Req_Id,"Material Code Request");
		*/	 }else{
				 String sss=masterForm.getIsSASFormAvailable();
				 System.out.println("update Finished Products");
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
					 String updateFinishedProduct="update material_code_request set BRAND_ID='"+masterForm.getBrandID()+"',LOCATION_ID='"+masterForm.getLocationId()+"',MATERIAL_TYPE_ID='"+masterForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+masterForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+masterForm.getMaterialShortName()+"'," +
							"MATERIAL_LONG_NAME='"+masterForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+masterForm.getMaterialGroupId()+"',DIVISION_ID='"+masterForm.getDivisionId()+"',UNIT_OF_MEAS_ID='"+masterForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+masterForm.getPuchaseGroupId()+"',is_asset='"+masterForm.getIsAsset()+"',APPROXIMATE_VALUE='"+masterForm.getApproximateValue()+"'," +
							"VALUATION_CLASS='"+masterForm.getValuationClass()+"',DETAILED_JUSTIFICATION='"+masterForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+masterForm.getDetailedSpecification()+"',PURPOSE_ID='"+masterForm.getPurposeID()+"',IS_SAS_FORM_AVAILABLE='"+masterForm.getIsSASFormAvailable()+"'," +
							"MODIFIED_DATE='"+dateNow+"'," +
							"MODIFIED_BY='"+user.getEmployeeNo()+"',Approve_Type='Created',HSN_Code='"+masterForm.getHsnCode()+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";	 
			int i=0;
			i=ad.SqlExecuteUpdate(updateFinishedProduct);
			if(i>0)
			{
				masterForm.setMessage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'.");
				masterForm.setTypeDetails("Update");
				return displayNewPromotional(mapping, form, request, response);
			}else{
				masterForm.setMessage("Error...When Updating Promotional.Please Check....");
				masterForm.setTypeDetails("Update");
			}	 
				 
			 }
			 
			 
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LANDX"));
			}
			masterForm.setCounID(countryID);
			masterForm.setCountryName(countryName);
			
			ResultSet rs11 = ad.selectQuery("select * from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+"-"+rs11.getString("LOCNAME"));
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
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PPC%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
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
			
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PPC'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
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
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PPC'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
			}
			masterForm.setValuationClassID(valuationClassID);
			masterForm.setValuationClassName(valuationClassName);
			
			//set Approved Field
			user=(UserInfo)session.getAttribute("user");
			if(user.getId()==1)
			{	
				request.setAttribute("approved", "approved");
			}
			masterForm.setMaterialCodeLists("12");
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("newPromotional");
	}
	
	
	public ActionForward displayNewPromotional(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		PromotionalForm masterForm=(PromotionalForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		try{
			
			
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
			System.out.println("location="+user.getPlantId());
			masterForm.setLocationId(user.getPlantId());
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("id"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			String getMaterialGroup="select * from MATERIAL_GROUP where MATERIAL_TYPE like '%PPC%' order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			
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
			LinkedList unitOfMeasIdList=new LinkedList();
			LinkedList unitOfMeasIdValues=new LinkedList();
			
			String getunitMesurement="select * from UNIT_MESUREMENT";
			ResultSet rsUnit=ad.selectQuery(getunitMesurement);
			while(rsUnit.next())
			{
				unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
				unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
			}
			masterForm.setUnitOfMeasIdList(unitOfMeasIdList);
			masterForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PPC'";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
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
				puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
			}
			masterForm.setPuchaseGroupIdList(puchaseGroupIdList);
			masterForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
			
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String getValuation="select * from VALUATION_CLASS where MAT_TYPE='PPC'";
			ResultSet rsValuation=ad.selectQuery(getValuation);
			while(rsValuation.next())
			{
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
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
			masterForm.setRequestDate(EMicroUtils.getCurrentSysDate());
			//Reset Form Values
			
			String locationId=request.getParameter("locationId");
			String materialGrup=request.getParameter("materialGrup");
			String shortName=request.getParameter("shortName");
			String longName=request.getParameter("longName");
			masterForm.setLocationId(locationId);
			masterForm.setMaterialCodeLists("12");
			masterForm.setTypeDetails("Save");
			masterForm.setMaterialCodeLists1("");
			masterForm.setRequestDate("");
			masterForm.setMaterialTypeId("12");
			masterForm.setStorageLocationId("");
			masterForm.setMaterialShortName(shortName);
			masterForm.setMaterialLongName(longName);
			masterForm.setMaterialGroupId(materialGrup);
			masterForm.setUnitOfMeasId("");
			masterForm.setDivisionId("");
			masterForm.setPuchaseGroupId("");
			masterForm.setIsAsset("");
			masterForm.setApproximateValue("");
			masterForm.setValuationClass("");
			masterForm.setDetailedJustification("");
			masterForm.setDetailedSpecification("");
			masterForm.setPurposeID("");
			masterForm.setIsSASFormAvailable("");
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
			// TODO: handle exception
		}
		return mapping.findForward("newPromotional");
	}
	public ActionForward displayPromotionalList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		PromotionalForm masterForm=(PromotionalForm)form;
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
		String getList="select m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME from material_code_request as m,Location as l where m.Type='Promotional'  and m.LOCATION_ID=l.LOCID";
		ResultSet rsList=ad.selectQuery(getList);
		while(rsList.next())
		{
			PromotionalForm codeMasterForm=new PromotionalForm();
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
		
		request.setAttribute("listOfPromotional", listOfFinishedProducts);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return mapping.findForward("promotionalList");
	}
	
}
