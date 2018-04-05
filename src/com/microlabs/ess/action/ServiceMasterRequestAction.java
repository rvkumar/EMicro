package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
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
import com.microlabs.ess.dao.MaterialmasterDAO;
import com.microlabs.ess.form.ServiceMasterRequestForm;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class ServiceMasterRequestAction extends DispatchAction {
	
	EssDao ad=EssDao.dBConnection();
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		ServiceMasterRequestForm masterForm=(ServiceMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList apprList=new LinkedList();
		MaterialmasterDAO dao=new MaterialmasterDAO();
		String location=request.getParameter("locationId");
		apprList=dao.serviceApprsList(location);
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
		return mapping.findForward("apprList");
	}
	public ActionForward lastCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ServiceMasterRequestForm v=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		LinkedList materialList=new LinkedList();
		
		String servdesc=v.getServiceDescription1();
		String serviceCategory=v.getServiceCatagory();
		try{

	 	int  totalCodeSearch=v.getTotalCodeSearch();
	     int  codeStartRecord=v.getCodeStartRecord();
	     int  codeEndRecord=v.getCodeEndRecord();
	     
	     codeStartRecord=totalCodeSearch-9;
	     codeEndRecord=totalCodeSearch;
	     
	     v.setTotalCodeSearch(totalCodeSearch);
			v.setCodeStartRecord(codeStartRecord);
			v.setCodeEndRecord(totalCodeSearch);
		 
			String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no) AS RowNum, request_no,service_description,detailed_desc,justification,SAP_CODE_NO,SAP_CREATION_DATE,request_date " +
					"from SERVICE_CODES where  service_description like '%"+servdesc+"%'  ";
			if(!serviceCategory.equalsIgnoreCase(""))
			{
				data=data + " and service_catagory ='"+serviceCategory+"'";
			}
			data=data +" ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";
				ResultSet rs=ad.selectQuery(data);
					while(rs.next()) 
					{
						ServiceMasterRequestForm sform1=new ServiceMasterRequestForm();
						
						
						if(rs.getString("request_no")==null)
						{
							   sform1.setRequestno("");
						}
						else{
							   sform1.setRequestno(rs.getString("request_no"));
						}
						
			
				        
				        sform1.setServiceDescription(rs.getString("service_description"));
				        sform1.setDetailedServiceDescription(rs.getString("detailed_desc"));
						sform1.setJustification(rs.getString("justification"));
		                sform1.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String cretedOn=rs.getString("SAP_CREATION_DATE");
						   if(cretedOn!=null)
						   {
							   String a[]=cretedOn.split(" ");
							   cretedOn=a[0];
							   String b[]=cretedOn.split("-");
							   cretedOn=b[2]+"/"+b[1]+"/"+b[0];
							   sform1.setSapCreationDate(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
						   }
						   	sform1.setRequestDate(rs.getString("request_date"));
						
					
						 materialList.add(sform1);
					
					}
					rs.close();
					ad.connClose();
							request.setAttribute("materialList", materialList);
							
							 request.setAttribute("disableNextButton", "disableNextButton");
								request.setAttribute("previousButton", "previousButton");
								if(materialList.size()<10)
								{
									
									request.setAttribute("previousButton", "");
									request.setAttribute("disablePreviousButton", "disablePreviousButton");
								}
								request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("SermasterSearch");
	}
	
	public ActionForward previousCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ServiceMasterRequestForm v=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		LinkedList materialList=new LinkedList();
		
		String servdesc=v.getServiceDescription1();
		String serviceCategory=v.getServiceCatagory();
		
		try{

	 	int  totalCodeSearch=v.getTotalCodeSearch();
	     int  codeEndRecord=v.getCodeStartRecord()-1;
	     int  codeStartRecord=v.getCodeStartRecord()-10;
	     if(codeStartRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				codeEndRecord=10;
			}
			
			
			  v.setTotalCodeSearch(totalCodeSearch);
			  v.setCodeStartRecord(1);
			  v.setCodeEndRecord(10);
			  String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no) AS RowNum, request_no,service_description,detailed_desc,justification,SAP_CODE_NO,SAP_CREATION_DATE,request_date " +
						"from SERVICE_CODES where  service_description like '%"+servdesc+"%'  ";
				if(!serviceCategory.equalsIgnoreCase(""))
				{
					data=data + " and service_catagory ='"+serviceCategory+"'";
				}
				data=data +" ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";
					ResultSet rs=ad.selectQuery(data);
						while(rs.next()) 
						{
							ServiceMasterRequestForm sform1=new ServiceMasterRequestForm();
							
							
							if(rs.getString("request_no")==null)
							{
								   sform1.setRequestno("");
							}
							else{
								   sform1.setRequestno(rs.getString("request_no"));
							}
							
				
					        
					        sform1.setServiceDescription(rs.getString("service_description"));
					        sform1.setDetailedServiceDescription(rs.getString("detailed_desc"));
							sform1.setJustification(rs.getString("justification"));
			                sform1.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String cretedOn=rs.getString("SAP_CREATION_DATE");
							   if(cretedOn!=null)
							   {
								   String a[]=cretedOn.split(" ");
								   cretedOn=a[0];
								   String b[]=cretedOn.split("-");
								   cretedOn=b[2]+"/"+b[1]+"/"+b[0];
								   sform1.setSapCreationDate(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
							   }
							   	sform1.setRequestDate(rs.getString("request_date"));
							
						
							 materialList.add(sform1);
						
						}
						rs.close();
						ad.connClose();
							request.setAttribute("materialList", materialList);
							
							v.setTotalCodeSearch(totalCodeSearch);
							v.setCodeStartRecord(codeStartRecord);
							v.setCodeEndRecord(codeEndRecord);
									request.setAttribute("nextButton", "nextButton");
									if(codeStartRecord!=1)
									request.setAttribute("previousButton", "previousButton");
									request.setAttribute("displayRecordNo", "displayRecordNo");
									if(materialList.size()<10)
									{
										v.setCodeStartRecord(1);
										request.setAttribute("previousButton", "");
										request.setAttribute("disablePreviousButton", "disablePreviousButton");
									}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("SermasterSearch");
	}
	public ActionForward nextCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ServiceMasterRequestForm v=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		LinkedList materialList=new LinkedList();
		
		String servdesc=v.getServiceDescription1();
		String serviceCategory=v.getServiceCatagory();
		try {
		
		
		 	int  totalCodeSearch=v.getTotalCodeSearch();
		     int  codeStartRecord=v.getCodeStartRecord();
		     int  codeEndRecord=v.getCodeEndRecord();
		     if(totalCodeSearch>codeEndRecord)
				{
					if(totalCodeSearch==codeEndRecord)
					{
						codeStartRecord=codeStartRecord;
						codeEndRecord=totalCodeSearch;
					}
					if(totalCodeSearch>codeEndRecord)
					{
						codeStartRecord=codeEndRecord+1;
						codeEndRecord=codeEndRecord+10;
					}
					
					String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no) AS RowNum, request_no,service_description,detailed_desc,justification,SAP_CODE_NO,SAP_CREATION_DATE,request_date " +
							"from SERVICE_CODES where  service_description like '%"+servdesc+"%'  ";
					if(!serviceCategory.equalsIgnoreCase(""))
					{
						data=data + " and service_catagory ='"+serviceCategory+"'";
					}
					data=data +" ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";
						ResultSet rs=ad.selectQuery(data);
							while(rs.next()) 
							{
								ServiceMasterRequestForm sform1=new ServiceMasterRequestForm();
								
								
								if(rs.getString("request_no")==null)
								{
									   sform1.setRequestno("");
								}
								else{
									   sform1.setRequestno(rs.getString("request_no"));
								}
								
					
						        
						        sform1.setServiceDescription(rs.getString("service_description"));
						        sform1.setDetailedServiceDescription(rs.getString("detailed_desc"));
								sform1.setJustification(rs.getString("justification"));
				                sform1.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								String cretedOn=rs.getString("SAP_CREATION_DATE");
								   if(cretedOn!=null)
								   {
									   String a[]=cretedOn.split(" ");
									   cretedOn=a[0];
									   String b[]=cretedOn.split("-");
									   cretedOn=b[2]+"/"+b[1]+"/"+b[0];
									   sform1.setSapCreationDate(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
								   }
								   	sform1.setRequestDate(rs.getString("request_date"));
								
							
								 materialList.add(sform1);
							
							}
							rs.close();
							ad.connClose();
									
								request.setAttribute("materialList", materialList);
					
								if(materialList.size()!=0)
								{
								 v.setTotalCodeSearch(totalCodeSearch);
								 v.setCodeStartRecord(codeStartRecord);
								 v.setCodeEndRecord(codeEndRecord);
									request.setAttribute("nextButton", "nextButton");
									request.setAttribute("previousButton", "previousButton");
								}
								else
								{
									int start=codeStartRecord;
									int end=codeStartRecord;
									
									v.setTotalCodeSearch(totalCodeSearch);
									v.setCodeStartRecord(codeStartRecord);
									v.setCodeEndRecord(codeEndRecord);
									
								}
							 if(materialList.size()<10)
							 {
								 v.setTotalCodeSearch(totalCodeSearch);
								 v.setCodeStartRecord(codeStartRecord);
								 v.setCodeEndRecord(codeStartRecord+materialList.size()-1);
									request.setAttribute("nextButton", "");
									request.setAttribute("disableNextButton", "disableNextButton");
									request.setAttribute("previousButton", "previousButton"); 
								 
							 }
							 
							 if(codeEndRecord==totalCodeSearch)
							 {
								 request.setAttribute("nextButton", "");
									request.setAttribute("disableNextButton", "disableNextButton");
							 }
							 request.setAttribute("displayRecordNo", "displayRecordNo");
					
				}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		  
		return mapping.findForward("SermasterSearch");
	}
	public ActionForward showServiceMasterSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	if(user==null){
		request.setAttribute("message","Session Expried! Try to Login again!");
		return mapping.findForward("displayiFrameSession");
	}
	int userID=user.getId();
	LinkedList materialList=new LinkedList();
	String servdesc=sform.getServiceDescription1();
	String serviceCategory=sform.getServiceCatagory();
	int  totalCodeSearch=0;
	  int  codeStartRecord=0;
	  int  codeEndRecord=0;
	  try{
		  LinkedList serviceGroupID=new LinkedList();
			LinkedList serviceGroupValues=new LinkedList();
			String getServiceGroup="select * from SERVICE_GROUP";
			ResultSet rsServiceGroup=ad.selectQuery(getServiceGroup);
			while(rsServiceGroup.next())
			{
				serviceGroupID.add(rsServiceGroup.getString("group_id"));
				serviceGroupValues.add(rsServiceGroup.getString("group_id")+" - "+rsServiceGroup.getString("STXT"));
			}
			sform.setServiceGroupID(serviceGroupID);
			sform.setServiceGroupValues(serviceGroupValues);
			}catch (Exception e) {
				e.printStackTrace();
			}
	  try {
		  
		 
	  String getCount="select count(*) from SERVICE_CODES where  service_description like '%"+servdesc+"%'  ";
		
		if(!serviceCategory.equalsIgnoreCase(""))
		{
			getCount=getCount + " and service_catagory ='"+serviceCategory+"'";
		}
			
			ResultSet rsCount=ad.selectQuery(getCount);
			while(rsCount.next()){
				totalCodeSearch=rsCount.getInt(1);
			}
			if(totalCodeSearch>=10)
			  {
				sform.setTotalCodeSearch(totalCodeSearch);
				  codeStartRecord=1;
				  codeEndRecord=10;
				  sform.setCodeStartRecord(1);
				  sform.setCodeEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  codeStartRecord=1;
				  codeEndRecord=totalCodeSearch;
				  sform.setTotalCodeSearch(totalCodeSearch);
				  sform.setCodeStartRecord(1);
				  sform.setCodeEndRecord(totalCodeSearch); 
			  }
	String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY request_no) AS RowNum, request_no,service_description,detailed_desc,justification,SAP_CODE_NO,SAP_CREATION_DATE,request_date " +
			"from SERVICE_CODES where  service_description like '%"+servdesc+"%'  ";
	if(!serviceCategory.equalsIgnoreCase(""))
	{
		data=data + " and service_catagory ='"+serviceCategory+"'";
	}
	data=data +" ) as  sub Where  sub.RowNum between 1 and 10";
		ResultSet rs=ad.selectQuery(data);
			while(rs.next()) 
			{
				ServiceMasterRequestForm sform1=new ServiceMasterRequestForm();
				
				
				if(rs.getString("request_no")==null)
				{
					   sform1.setRequestno("");
				}
				else{
					   sform1.setRequestno(rs.getString("request_no"));
				}
				
	
		        
		        sform1.setServiceDescription(rs.getString("service_description"));
		        sform1.setDetailedServiceDescription(rs.getString("detailed_desc"));
				sform1.setJustification(rs.getString("justification"));
                sform1.setSapCodeNo(rs.getString("SAP_CODE_NO"));
				String cretedOn=rs.getString("SAP_CREATION_DATE");
				   if(cretedOn!=null)
				   {
					   String a[]=cretedOn.split(" ");
					   cretedOn=a[0];
					   String b[]=cretedOn.split("-");
					   cretedOn=b[2]+"/"+b[1]+"/"+b[0];
					   sform1.setSapCreationDate(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
				   }
				   	sform1.setRequestDate(rs.getString("request_date"));
				
			
				 materialList.add(sform1);
			
			}
			rs.close();
			ad.connClose();
			if(materialList.size()==0){
				request.setAttribute("noRecords", "noRecords");
				sform.setMessage1("No Records Found");
				sform.setMessage2("");
			}
			if(materialList.size()>0)
			{
				request.setAttribute("materialList", materialList);
				
			}
			if(materialList.size()==0)
			{
				request.setAttribute("noMaterialList", "noMaterialList");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return mapping.findForward("SermasterSearch");	
		}
	
	public ActionForward getServiceMasterRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		sform.setServiceGroup1("");
		sform.setServiceDescription1("");

		LinkedList serviceGroupID=new LinkedList();
		LinkedList serviceGroupValues=new LinkedList();
		try{
		String getServiceGroup="select * from SERVICE_GROUP";
		ResultSet rsServiceGroup=ad.selectQuery(getServiceGroup);
		while(rsServiceGroup.next())
		{
			serviceGroupID.add(rsServiceGroup.getString("group_id"));
			serviceGroupValues.add(rsServiceGroup.getString("group_id")+" - "+rsServiceGroup.getString("STXT"));
		}
		sform.setServiceGroupID(serviceGroupID);
		sform.setServiceGroupValues(serviceGroupValues);
	
		
		}catch (Exception e) {
			e.printStackTrace();
		}
								
		
		return mapping.findForward("SermasterSearch");	
	}
		
	
	
	public ActionForward ViewServiceMasterrecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String requstNo=request.getParameter("ReqestNo");
		


        LinkedList SerDetails=new LinkedList();
        String location="";
 		
    	ApprovalsForm sform6=new ApprovalsForm();
 		

 		
 		String query="select ser.request_date,loc.LOCNAME,loc.LOCATION_CODE,ser.service_description,ser.detailed_desc,u.LTXT," +
 				"pur.PURCHASE_GROUP_DESC,ser.service_catagory,se.STXT,ser.machine_name,ser.app_value,ser.justification," +
 				"val.VALUATION_DESC,ser.attachment,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE," +
		 	"SAP_CREATED_BY,REQUESTED_BY from SERVICE_MASTER as ser,Location as loc,UNIT_MESUREMENT as u," +
 				"PURCHASE_GROUP as pur, SERVICE_GROUP as se,VALUATION_CLASS as val where REQUEST_NO='"+requstNo+"' and	" +
 				"loc.LOCID=ser.plant_code and u.UNIT_OF_MEAS_ID=ser.uom and pur.PURCHASE_GROUP_ID=ser.purchase_group " +
 				"and se.group_id=ser.service_group and val.VALUATION_ID=ser.valuation_class";
	
 		System.out.println(query);
		
			ResultSet rs=ad.selectQuery(query);
			try {
				if(rs.next()){
				
					sform6.setRequestNo(requstNo);
					sform6.setRequestNumber(requstNo);
					sform6.setPlantCode(rs.getString("LOCNAME"));
					location=rs.getString("LOCATION_CODE");
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
				
				
				String query_file="select file_name from service_documents where request_no='"+requstNo+"'";
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
					"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and " +
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
	
  								
			
		return mapping.findForward("SermasterView");
	}
	
	
	
	
	public ActionForward submittAllServiceMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
	 
		   
			String reqNo[]=sform.getGetReqno();
			
			
			for(int j=0;j<reqNo.length;j++)
			{
				try
				{
					int i=0;
					String requestNo=reqNo[j];
					String typeDetails=sform.getTypeDetails();
					int checkApprover=0;
					String plantcode="";
					
					
                 String accountGroup2="select plant_code from SERVICE_MASTER where REQUEST_NO='"+requestNo+"'";	
					  
					  ResultSet accountGroupId2=ad.selectQuery(accountGroup2);
				  
				   while(accountGroupId2.next()){
					   plantcode=accountGroupId2.getString("plant_code");
		    	 		
		    	 	}
				
					
					  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='Service Master' and " +
				  		" Location=(select LOCATION_CODE from Location where LOCID='"+plantcode+"')";
					  ResultSet rsCheck=ad.selectQuery(checkApprovers);
					  while(rsCheck.next())
					  {
						  checkApprover=1;
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
				  		String matDetails="select loc.LOCATION_CODE from SERVICE_MASTER as s,Location as loc  where s.REQUEST_NO='"+requestNo+"' " +
				  				"and s.plant_code=loc.LOCID";
						ResultSet rsDetails=ad.selectQuery(matDetails);
						if(rsDetails.next())
						{
							loctID=rsDetails.getString("LOCATION_CODE");
							matType="Service Master";
						}
						 checkApprover=0;
						 	String pendingApprs="";
						 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app," +
						 			"emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='Service Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
				  			 getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app," +
				  			 		"emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='Service Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
							saveRecReq = saveRecReq + "'"+requestNo+"','Service Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','"+matType+"')";
							int ij=ad.SqlExecuteUpdate(saveRecReq);
							if(!(parllelAppr1.equalsIgnoreCase(""))){
								
							
							 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
							 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Service Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','"+matType+"')";
							 ad.SqlExecuteUpdate(sendRecParllelAppr1);
							}
							if(!(parllelAppr2.equalsIgnoreCase(""))){
								
								 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
								 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Service Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','"+matType+"')";
								 ad.SqlExecuteUpdate(sendRecParllelAppr2);
							}
							
							if(ij>0){
								sform.setMessage("Request No "+requestNo+". Submitted for approval successully.");
								sform.setMessage2("");
								String updateStatus="update SERVICE_MASTER set app_satus='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where request_no='"+requestNo+"'";
								ad.SqlExecuteUpdate(updateStatus);
								//send mail to approvers
								EMailer email = new EMailer();
								String approvermail="";
							 email.sendMailToApprover(request, approvermail,requestNo, "Service Master");
								
							}else{
								sform.setMessage2("Error while submiting approval...");
								sform.setMessage("");
							}
				  		}else{
				  			
				  			sform.setMessage2("No Approvers are assigned.Please Contact to Admin");
				  			sform.setMessage("");
				  		}
							
							
						
						
						
						
						
						}
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			 String module="ESS"; 
				request.setAttribute("MenuIcon", module);
		return mapping.findForward("displayList");
	}
	
	public ActionForward saveAndSubmitMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userId=user.getId();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		int r_no=sform.getR_no();
		int c=0;
		int f=0;
		
		try{
			int i=0;
			String typeDetails=sform.getTypeDetails();
			int checkApprover=0;
			  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='Service Master' and " +
		  		" Location=(select LOCATION_CODE from Location where LOCID='"+sform.getPlantCode()+"')";
			  ResultSet rsCheck=ad.selectQuery(checkApprovers);
			  while(rsCheck.next())
			  {
				  checkApprover=1;
			  }
			if(typeDetails.equalsIgnoreCase("Save"))
			{
			int old_RequestNo=sform.getR_no();
			int count=0;
			
			String getRequestNoCount="select count(*) from SERVICE_MASTER where REQUEST_NO='"+sform.getR_no()+"'";
			ResultSet rsGetRequestNoCount=ad.selectQuery(getRequestNoCount);
			while(rsGetRequestNoCount.next())
			{
				count=rsGetRequestNoCount.getInt(1);
			}
			//coded added for approvals 
			String approver="";
			String lApprover="";
			String approvermail="";
			String pendingApprovers="";
			
			String getApproverID="select * from Approvers_Details where Type='Service Master Request'";
			
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
			
			
			if(count>0)
			{
				//Request  available
				String getReqestNumber="select max(REQUEST_NO)  from SERVICE_MASTER";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				sform.setR_no(maxReqno);
				
				String currentdate=EMicroUtils.getCurrentSysDate();
				  String a[]=currentdate.split("/");
				  
				  currentdate=a[2]+"-"+a[1]+"-"+a[0];
				  
				  
			String query="INSERT INTO SERVICE_MASTER(PLANT_CODE,request_date,STORAGE_LOCATION,SERVICE_DESCRIPTION,DETAILED_DESC," +
					"UOM,PURCHASE_GROUP,SERVICE_CATAGORY,SERVICE_GROUP,MACHINE_NAME,APP_VALUE,WHERE_USED,PURPOSE,JUSTIFICATION,Attachment," +
					"VALUATION_CLASS,APP_SATUS,REQUEST_NO,CREATED_DATE,CREATED_BY,SAC_Code)VALUES('"+sform.getPlantCode()+"','"+sform.getRequestDate()+"','"+sform.getStorageLocation()+"','"+sform.getServiceDescription()+"'," +
							"'"+sform.getDetailedServiceDescription()+"','"+sform.getUom()+"','"+sform.getPurchaseGroup()+"','"+sform.getServiceCatagory()+"',"+
							"'"+sform.getServiceGroup()+"','"+sform.getE_m_name()+"','"+sform.getApp_amount()+"','"+sform.getWhereUsed()+"','"+sform.getPurpose()+"','"+sform.getJustification()+"'," +
							"'"+sform.getSfile()+"',"+
									"'"+sform.getValuationClass()+"','Created','"+sform.getR_no()+"','"+currentdate+"','"+user.getEmployeeNo()+"','"+sform.getSacCode()+"')";
			
						
			i=ad.SqlExecuteUpdate(query);
			int jj=0;
			jj=ad.SqlExecuteUpdate("update SERVICE_MASTER set last_approver='No',pending_approver='"+pendingApprovers+"' where REQUEST_NO='"+sform.getR_no()+"'");
			
			if(i>0)
			{
				sform.setMessage("Alert Code creation request saved with New request number='"+sform.getR_no()+"' ");
				sform.setMessage2("");
				sform.setTypeDetails("Update");
				if(checkApprover==0)
				{
					sform.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				return displayServiceMaster(mapping, form, request, response);
				}
			}else{
				sform.setMessage2("Error...When code saving creation request.Please check");
				sform.setTypeDetails("Save");
				
			}
			
			int j=0;
			j=ad.SqlExecuteUpdate("update service_documents set request_no='"+sform.getR_no()+"' where request_no='"+old_RequestNo+"' and  userId='"+user.getEmployeeNo()+"'");
			
			}else{
				
				String currentdate=EMicroUtils.getCurrentSysDate();
				  String a[]=currentdate.split("/");
				  
				  currentdate=a[2]+"-"+a[1]+"-"+a[0];
				  
				  
			String query="INSERT INTO SERVICE_MASTER(PLANT_CODE,request_date,STORAGE_LOCATION,SERVICE_DESCRIPTION,DETAILED_DESC," +
			"UOM,PURCHASE_GROUP,SERVICE_CATAGORY,SERVICE_GROUP,MACHINE_NAME,APP_VALUE,WHERE_USED,PURPOSE,JUSTIFICATION,Attachment," +
			"VALUATION_CLASS,APP_SATUS,REQUEST_NO,CREATED_DATE,CREATED_BY,SAC_Code)VALUES('"+sform.getPlantCode()+"','"+sform.getRequestDate()+"','"+sform.getStorageLocation()+"','"+sform.getServiceDescription()+"'," +
			"'"+sform.getDetailedServiceDescription()+"','"+sform.getUom()+"','"+sform.getPurchaseGroup()+"','"+sform.getServiceCatagory()+"',"+
			"'"+sform.getServiceGroup()+"','"+sform.getE_m_name()+"','"+sform.getApp_amount()+"','"+sform.getWhereUsed()+"','"+sform.getPurpose()+"','"+sform.getJustification()+"'," +
			"'"+sform.getSfile()+"','"+sform.getValuationClass()+"','Created','"+sform.getR_no()+"','"+currentdate+"','"+user.getEmployeeNo()+"','"+sform.getSacCode()+"')";
			i=ad.SqlExecuteUpdate(query);
			int jj=0;
			jj=ad.SqlExecuteUpdate("update SERVICE_MASTER set last_approver='No',pending_approver='"+pendingApprovers+"' where REQUEST_NO='"+sform.getR_no()+"'");
			
			if(i>0)
			{
				sform.setMessage("Code creation request saved with  request number='"+sform.getR_no()+"' ");
				sform.setMessage2("");
				sform.setTypeDetails("Update");
				if(checkApprover==0)
				{
					sform.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				return displayServiceMaster(mapping, form, request, response);
				}
			}else{
				sform.setMessage2("Error...When code saving creation request.Please check");
				sform.setTypeDetails("Save");
			}
			
			}
			
			}else{
				String recordStatus="";
				String getRecordStatus="select APP_SATUS from SERVICE_MASTER where REQUEST_NO='"+sform.getR_no()+"' ";
				ResultSet rsRecord=ad.selectQuery(getRecordStatus);
				while(rsRecord.next())
				{
					recordStatus=rsRecord.getString("APP_SATUS");
				}
				if(recordStatus.equalsIgnoreCase("Rejected"))
				{
					
					String deleteRecords="delete from All_Request where Req_Id='"+sform.getR_no()+"' and Req_Type='Service Master'";
					int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
					System.out.println("deleteStatus="+deleteStatus);
					
					String updateFlag="update SERVICE_MASTER set rejected_flag='yes' where REQUEST_NO='"+sform.getR_no()+"'";
					ad.SqlExecuteUpdate(updateFlag);
					
				}
				//update
				String query="UPDATE SERVICE_MASTER SET PLANT_CODE='"+sform.getPlantCode()+"',request_date='"+sform.getRequestDate()+"',STORAGE_LOCATION='"+sform.getStorageLocation()+"'" +
				",SERVICE_DESCRIPTION='"+sform.getServiceDescription()+"',DETAILED_DESC='"+sform.getDetailedServiceDescription()+"'," +
		"UOM='"+sform.getUom()+"',PURCHASE_GROUP='"+sform.getPurchaseGroup()+"',SERVICE_CATAGORY='"+sform.getServiceCatagory()+"'" +
				",SERVICE_GROUP='"+sform.getServiceGroup()+"',MACHINE_NAME='"+sform.getE_m_name()+"',APP_VALUE='"+sform.getApp_amount()+"'" +
						",WHERE_USED='"+sform.getWhereUsed()+"',PURPOSE='"+sform.getPurpose()+"',JUSTIFICATION='"+sform.getJustification()+"'," +
						
					"Attachment='"+sform.getSfile()+"',VALUATION_CLASS='"+sform.getValuationClass()+"',APP_SATUS='Submited',SAC_Code='"+sform.getSacCode()+"'WHERE REQUEST_NO="+r_no+"";
	  i=ad.SqlExecuteUpdate(query);
		if(i>0)
		{
			sform.setMessage(" Code creation request updated with  request number='"+sform.getR_no()+"' ");
			sform.setMessage2("");
			sform.setTypeDetails("Update");
			if(checkApprover==0)
			{
				sform.setAppStatusMessage("No approvers are assigned for submiting this request. ");
			return displayServiceMaster(mapping, form, request, response);
			}
		}else{
			sform.setMessage2("Error...When code updated creation request.Please check");
			sform.setTypeDetails("Update");
		}
	   System.out.println(query);
	   
	  
	
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
		  		String matDetails="select loc.LOCATION_CODE from SERVICE_MASTER as s,Location as loc  where s.REQUEST_NO='"+sform.getR_no()+"' " +
		  				"and s.plant_code=loc.LOCID";
				ResultSet rsDetails=ad.selectQuery(matDetails);
				if(rsDetails.next())
				{
					loctID=rsDetails.getString("LOCATION_CODE");
					matType="Service Master";
				}
				 checkApprover=0;
				 	String pendingApprs="";
				 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app," +
				 			"emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='Service Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		  			 getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app," +
		  			 		"emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='Service Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		  			String currentdate=EMicroUtils.getCurrentSysDate();
					  String a[]=currentdate.split("/");
					  currentdate=a[2]+"-"+a[1]+"-"+a[0];
		  		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					saveRecReq = saveRecReq + "'"+sform.getR_no()+"','Service Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','"+matType+"')";
					int ij=ad.SqlExecuteUpdate(saveRecReq);
					
					String deleteHistory="delete SERVICE_MASTER_HISTORY where request_no='"+sform.getR_no()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
		  	  		ad.SqlExecuteUpdate(deleteHistory);
					String saveinHistory="INSERT INTO SERVICE_MASTER_HISTORY(PLANT_CODE,request_date,STORAGE_LOCATION,SERVICE_DESCRIPTION,DETAILED_DESC," +
							"UOM,PURCHASE_GROUP,SERVICE_CATAGORY,SERVICE_GROUP,MACHINE_NAME,APP_VALUE,WHERE_USED,PURPOSE,JUSTIFICATION,Attachment," +
							"VALUATION_CLASS,APP_SATUS,REQUEST_NO,CREATED_DATE,CREATED_BY,Role,SAC_Code)VALUES('"+sform.getPlantCode()+"','"+sform.getRequestDate()+"','"+sform.getStorageLocation()+"','"+sform.getServiceDescription()+"'," +
							"'"+sform.getDetailedServiceDescription()+"','"+sform.getUom()+"','"+sform.getPurchaseGroup()+"','"+sform.getServiceCatagory()+"',"+
							"'"+sform.getServiceGroup()+"','"+sform.getE_m_name()+"','"+sform.getApp_amount()+"','"+sform.getWhereUsed()+"','"+sform.getPurpose()+"','"+sform.getJustification()+"'," +
							"'"+sform.getSfile()+"','"+sform.getValuationClass()+"','Created','"+sform.getR_no()+"','"+currentdate+"','"+user.getEmployeeNo()+"','user','"+sform.getSacCode()+"')";
					ad.SqlExecuteUpdate(saveinHistory);	
					
					if(!(parllelAppr1.equalsIgnoreCase(""))){
						
					
					 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+sform.getR_no()+"','Service Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','"+matType+"')";
					 ad.SqlExecuteUpdate(sendRecParllelAppr1);
					}
					if(!(parllelAppr2.equalsIgnoreCase(""))){
						
						 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
						 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+sform.getR_no()+"','Service Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','"+matType+"')";
						 ad.SqlExecuteUpdate(sendRecParllelAppr2);
					}
					
					if(ij>0){
						sform.setMessage("Request No "+sform.getR_no()+". Submitted for approval successully.");
						sform.setMessage2("");
						String updateStatus="update SERVICE_MASTER set app_satus='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where request_no='"+sform.getR_no()+"'";
						ad.SqlExecuteUpdate(updateStatus);
						//send mail to approvers
						EMailer email = new EMailer();
						String approvermail="";
					 email.sendMailToApprover(request, approvermail,Integer.toString(sform.getR_no()), "Service Master");
						request.setAttribute("message", "message");
						return displayServiceMaster(mapping, form, request, response);
						
					}else{
						sform.setMessage2("Error while submiting approval...");
						sform.setMessage("");
					}
		  		}else{
		  			
		  			sform.setMessage2("No Approvers are assigned.Please Contact to Admin");
		  			sform.setMessage("");
		  		}
					
					
				
				
				
				
				}
			
			 String query2="select file_name from service_documents where request_no="+r_no+" and userId='"+user.getEmployeeNo()+"'";
				ArrayList documentDetails=new ArrayList();
				ResultSet rs2=ad.selectQuery(query2);

				while(rs2.next()){
					ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
					sf.setSfile(rs2.getString("file_name"));
					sf.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/"+rs2.getString("file_name")+"");
					documentDetails.add(sf);
				}
				
				request.setAttribute("documentDetails", documentDetails);
				setMasterData(mapping, form, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("display");
}
	
	public ActionForward copyRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int requstNo=Integer.parseInt(request.getParameter("requstNo"));
		System.out.println("rno---------------------"+requstNo);
		ServiceMasterRequestForm sform6=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String approveType="Pending";
		
		LinkedList uid=new LinkedList();
		LinkedList uname=new LinkedList();
		String query2="select * from UNIT_MESUREMENT";
		ResultSet r1=ad.selectQuery(query2);
		try{
		while(r1.next()){
			ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
			//uid.add(r1.getString("unit_of_meas_id"));
			sf.setU_id(r1.getString("UNIT_OF_MEAS_ID"));
			sf.setU_label((r1.getString("UNIT_OF_MEAS_ID")+"-"+r1.getString("LTXT")));
			uid.add(sf);
		}
		//sform.setUomId(uid);
		sform6.setUomLabel(uid);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		LinkedList p_label=new LinkedList();
		LinkedList p_id=new LinkedList();
		String query3="select * from PURCHASE_GROUP";
		ResultSet r2=ad.selectQuery(query3);
		try{
			while(r2.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setP_id((r2.getString("PURCHASE_GROUP_ID")));
			     sf.setP_all((r2.getString("PURCHASE_GROUP_ID")+"-"+r2.getString("PURCHASE_GROUP_DESC")));
				//p_label.add(sf);
			     p_id.add(sf);
			}
			
			sform6.setPlabel(p_id);
			//sform.setPid(p_id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		LinkedList vid=new LinkedList();
		LinkedList vlabel=new LinkedList();
		String query4="select * from valuation_class";
		ResultSet r3=ad.selectQuery(query4);
		try{
			while(r3.next()){
				vid.add(r3.getString("VALUATION_ID"));
				vlabel.add(r3.getString("VALUATION_DESC"));
			}
			sform6.setVid(vid);
			sform6.setVlabel(vlabel);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		LinkedList pl_id=new LinkedList();
		LinkedList pl_all=new LinkedList();
		String availableLoc=user.getAvailableLocations();
		if(availableLoc.equalsIgnoreCase(""))
			availableLoc="0";
		String query5="select * from location where LOCID in("+availableLoc+")";
		ResultSet r4=ad.selectQuery(query5);
		try{
			while(r4.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				pl_id.add(r4.getString("LOCATION_CODE"));
				pl_all.add(r4.getString("LOCATION_CODE")+"-"+r4.getString("LOCNAME"));
				pl_id.add(sf);
			}
			if(pl_id.size()==0){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				pl_id.add("");
				pl_all.add("");
				pl_id.add(sf);
			}
			sform6.setPcode(pl_id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		LinkedList sg_label=new LinkedList();
		String query6="select * from MATERIAL_GROUP";
		ResultSet r5=ad.selectQuery(query6);
		try{
			while(r5.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setService_id(r5.getString("material_group_id"));
				sf.setService_label(r5.getString("material_group_id")+"-"+r5.getString("STXT"));
				sg_label.add(sf);
			}
			sform6.setSgroupLabel(sg_label);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		String query="select * from SERVICE_MASTER where REQUEST_NO="+requstNo+"";
		ResultSet rs=ad.selectQuery(query);
		try{
		while(rs.next())
		{
			String getReqestNumber="select max(request_no)  from SERVICE_MASTER";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			sform6.setR_no(maxReqno);
			sform6.setRequestDate(EMicroUtils.getCurrentSysDate());
			sform6.setPlantCode(rs.getString("plant_code"));
			sform6.setStorageLocation(rs.getString("storage_location"));
			sform6.setServiceDescription(rs.getString("service_description"));
			sform6.setDetailedServiceDescription(rs.getString("detailed_desc"));
			sform6.setUom(rs.getString("uom"));
			sform6.setPurchaseGroup(rs.getString("purchase_group"));
			sform6.setServiceCatagory(rs.getString("service_catagory"));
			sform6.setServiceGroup(rs.getString("service_group"));
			sform6.setE_m_name(rs.getString("machine_name"));
			sform6.setApp_amount(rs.getString("app_value"));
			sform6.setWhereUsed(rs.getString("where_used"));
			sform6.setPurpose(rs.getString("purpose"));
			sform6.setJustification(rs.getString("justification"));
			sform6.setValuationClass(rs.getString("valuation_class"));
			//sform6.setMessage("Edit Data");
			
		}
		sform6.setTypeDetails("Update");
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		String serviceCategory=sform6.getServiceCatagory();
		String getValuationClass="select * from VALUATION_CLASS where MAT_TYPE='"+serviceCategory+"'";
		ResultSet rsValuation=ad.selectQuery(getValuationClass);
		while(rsValuation.next()){
		
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
		sform6.setValuationClassID(valuationClassID);
		sform6.setValuationClassName(valuationClassName);
		}
	 
catch(Exception e){
		e.printStackTrace();
	}
	
	setMasterData(mapping, form, request, response);
	
	
String forwardType="display";
	
	if(user.getId()==1)//Approver
	{
		forwardType="approverMaster";
		sform6.setApproveType(approveType);
	
	}
	if(user.getId()==2)//SAP Creatore
	{
		forwardType="serviceMasterSAP";
		sform6.setApproveType(approveType);
	
	}
	if(user.getId()!=1 && user.getId()!=2)
	{
		forwardType="display";
	}
	sform6.setTypeDetails("Save");
	return mapping.findForward(forwardType);
	
}
	
	public ActionForward getValuationClass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		ServiceMasterRequestForm masterForm=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			LinkedList valuationClassID=new LinkedList();
			LinkedList valuationClassName=new LinkedList();
			String serviceCategory=masterForm.getServiceCatagory();
			String getValuationClass="select * from VALUATION_CLASS ";
			ResultSet rsValuation=ad.selectQuery(getValuationClass);
			while(rsValuation.next()){
			
					valuationClassID.add(rsValuation.getString("VALUATION_ID"));
					valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
				}
				masterForm.setValuationClassID(valuationClassID);
				masterForm.setValuationClassName(valuationClassName);
			
				
				String query2="select file_name from service_documents where request_no="+masterForm.getR_no()+" and userId='"+user.getEmployeeNo()+"'";
				ArrayList documentDetails=new ArrayList();
				ResultSet rs2=ad.selectQuery(query2);

				while(rs2.next()){
					ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
					sf.setSfile(rs2.getString("file_name"));
					sf.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/"+rs2.getString("file_name")+"");
					documentDetails.add(sf);
				}
				if(documentDetails.size()>0){
				request.setAttribute("documentDetails", documentDetails);
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
		setMasterData(mapping, form, request, response);
		return mapping.findForward("display");	
	}
	
	public ActionForward setMasterData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ServiceMasterRequestForm masterForm=(ServiceMasterRequestForm)form;
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
		
		LinkedList serviceGroupID=new LinkedList();
		LinkedList serviceGroupValues=new LinkedList();
		
		String getServiceGroup="select * from SERVICE_GROUP";
		ResultSet rsServiceGroup=ad.selectQuery(getServiceGroup);
		while(rsServiceGroup.next())
		{
			serviceGroupID.add(rsServiceGroup.getString("group_id"));
			serviceGroupValues.add(rsServiceGroup.getString("group_id")+" - "+rsServiceGroup.getString("STXT"));
		}
		masterForm.setServiceGroupID(serviceGroupID);
		masterForm.setServiceGroupValues(serviceGroupValues);
		
		
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public ActionForward sendMailToApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceMasterRequestForm serviceForm=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			int i=0;
			
			String requestNo=request.getParameter("ReqNo");
			String matType="Service Master";
			String loctID=request.getParameter("LoctID");
				Date dNow = new Date( );
				SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
				String dateNow = ft.format(dNow);
			 	String pApprover="";
			 	String parllelAppr1="";
			 	String parllelAppr2="";
	    		String matGroup="";
	    		
			 	
			 	int checkApprover=0;
			 	String pendingApprs="";
			 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='Service Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
	    		if(!(pendingApprs.equalsIgnoreCase(""))){
	    			int size=pendingApprs.length();
	    			pendingApprs=pendingApprs.substring(0, (size-2));
	    		}
	    		if(!(pApprover.equalsIgnoreCase(""))){
	    		
	    		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				saveRecReq = saveRecReq + "'"+requestNo+"','Service Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','"+matType+"')";
				int ij=ad.SqlExecuteUpdate(saveRecReq);
				if(!(parllelAppr1.equalsIgnoreCase(""))){
					
					String a[]=parllelAppr1.split(",");
					parllelAppr1=a[0];
				 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Service Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','"+matType+"')";
				 ad.SqlExecuteUpdate(sendRecParllelAppr1);
				}
				if(!(parllelAppr2.equalsIgnoreCase(""))){
					String a[]=parllelAppr2.split(",");
					parllelAppr2=a[0];
					 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Service Master','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','"+matType+"')";
					 ad.SqlExecuteUpdate(sendRecParllelAppr2);
				}
				
				if(ij>0){
					serviceForm.setMessage("Request No"+requestNo+". Submitted for approval successully.");
					serviceForm.setMessage2("");
					String updateStatus="update SERVICE_MASTER set app_satus='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where request_no='"+requestNo+"'";
					ad.SqlExecuteUpdate(updateStatus);
					//send mail to approvers
					EMailer email = new EMailer();
					String approvermail="";
				 email.sendMailToApprover(request, approvermail,requestNo, "Service Master");
				}else{
					serviceForm.setMessage2("Error while submiting approval...");
				}
	    		}else{
	    			
	    			serviceForm.setMessage2("No Approvers are assigned.Please Contact to Admin");
	    		}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		serviceForm.setApproveStatus("Created");
		searchRecord(mapping, form, request, response);
		return mapping.findForward("displayList");
	}
	
	public ActionForward saveSAPCODEData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceMasterRequestForm sform6=(ServiceMasterRequestForm)form;
		int requstNo=sform6.getR_no();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String approveType="Pending";
		
		LinkedList uid=new LinkedList();
		LinkedList uname=new LinkedList();
		String query2="select * from UNIT_MESUREMENT";
		ResultSet r1=ad.selectQuery(query2);
		try{
		while(r1.next()){
			ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
			//uid.add(r1.getString("unit_of_meas_id"));
			sf.setU_id(r1.getString("UNIT_OF_MEAS_ID"));
			sf.setU_label((r1.getString("UNIT_OF_MEAS_ID")+"-"+r1.getString("LTXT")));
			uid.add(sf);
		}
		//sform.setUomId(uid);
		sform6.setUomLabel(uid);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		LinkedList p_label=new LinkedList();
		LinkedList p_id=new LinkedList();
		String query3="select * from PURCHASE_GROUP";
		ResultSet r2=ad.selectQuery(query3);
		try{
			while(r2.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setP_id((r2.getString("PURCHASE_GROUP_ID")));
			     sf.setP_all((r2.getString("PURCHASE_GROUP_ID")+"-"+r2.getString("PURCHASE_GROUP_DESC")));
				//p_label.add(sf);
			     p_id.add(sf);
			}
			
			sform6.setPlabel(p_id);
			//sform.setPid(p_id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		LinkedList vid=new LinkedList();
		LinkedList vlabel=new LinkedList();
		String query4="select * from valuation_class";
		ResultSet r3=ad.selectQuery(query4);
		try{
			while(r3.next()){
				vid.add(r3.getString("VALUATION_ID"));
				vlabel.add(r3.getString("VALUATION_DESC"));
			}
			sform6.setVid(vid);
			sform6.setVlabel(vlabel);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		LinkedList pl_id=new LinkedList();
		LinkedList pl_all=new LinkedList();
		String availableLoc=user.getAvailableLocations();
		if(availableLoc.equalsIgnoreCase(""))
			availableLoc="0";
		String query5="select * from location where LOCID in("+availableLoc+")";
		ResultSet r4=ad.selectQuery(query5);
		try{
			while(r4.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				pl_id.add(r4.getString("LOCATION_CODE"));
				pl_all.add(r4.getString("LOCATION_CODE")+"-"+r4.getString("LOCNAME"));
				pl_id.add(sf);
			}
			if(pl_id.size()==0){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				pl_id.add("");
				pl_all.add("");
				pl_id.add(sf);
			}
			sform6.setPcode(pl_id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		LinkedList sg_label=new LinkedList();
		String query6="select * from MATERIAL_GROUP";
		ResultSet r5=ad.selectQuery(query6);
		try{
			while(r5.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setService_id(r5.getString("material_group_id"));
				sf.setService_label(r5.getString("material_group_id")+"-"+r5.getString("STXT"));
				sg_label.add(sf);
			}
			sform6.setSgroupLabel(sg_label);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String query_file="select file_name from service_documents where request_no='"+requstNo+"'";
		ArrayList documentDetails=new ArrayList();
		ResultSet rs_file=ad.selectQuery(query_file);
		try{
		while(rs_file.next()){
			ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
			sf.setSfile(rs_file.getString("file_name"));
			documentDetails.add(sf);
		}
		
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("documentDetails", documentDetails);
		String query="select * from SERVICE_MASTER where REQUEST_NO="+requstNo+"";
		System.out.println(query);
		try{
		ResultSet rs=ad.selectQuery(query);
		if(rs.next()){
			sform6.setR_no(requstNo);
			sform6.setPlantCode(rs.getString("plant_code"));
			sform6.setStorageLocation(rs.getString("storage_location"));
			sform6.setServiceDescription(rs.getString("service_description"));
			sform6.setDetailedServiceDescription(rs.getString("detailed_desc"));
			sform6.setUom(rs.getString("uom"));
			sform6.setPurchaseGroup(rs.getString("purchase_group"));
			sform6.setServiceCatagory(rs.getString("service_catagory"));
			sform6.setServiceGroup(rs.getString("service_group"));
			sform6.setE_m_name(rs.getString("machine_name"));
			sform6.setApp_amount(rs.getString("app_value"));
			sform6.setWhereUsed(rs.getString("where_used"));
			sform6.setPurpose(rs.getString("purpose"));
			sform6.setJustification(rs.getString("justification"));
			sform6.setValuationClass(rs.getString("valuation_class"));
			//sform6.setMessage("Edit Data");
			approveType=rs.getString("app_satus");
		}
		
		int i=0;

		String sapCreationDate=sform6.getSapCreationDate();
		  String b[]=sapCreationDate.split("/");
		  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
		String updateMaterial="update SERVICE_MASTER set SAP_CODE_NO='"+sform6.getSapCodeNo()+"',SAP_CODE_EXISTS='"+sform6.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+sform6.getSapCreatedBy()+"' where REQUEST_NO='"+sform6.getR_no()+"'";
		i=ad.SqlExecuteUpdate(updateMaterial);
		if(i>0)
		{
			sform6.setMessage("Code creation request updated with request number='"+sform6.getR_no()+"'");
			sform6.setMessage2("");
		}else{
			sform6.setMessage2("Error...When updating code creation reequest.Please Check");
			sform6.setMessage("");
		} 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		setMasterData(mapping, form, request, response);
		
String forwardType="display";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
			sform6.setApproveType(approveType);
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="serviceMasterSAP";
			sform6.setApproveType(approveType);
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="display";
		}
		return mapping.findForward("serviceMasterSAP");
	}
	
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceMasterRequestForm sform6=(ServiceMasterRequestForm)form;
		int requstNo=sform6.getR_no();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String approveType="Pending";
		
		LinkedList uid=new LinkedList();
		LinkedList uname=new LinkedList();
		
		String query2="select * from UNIT_MESUREMENT";
		ResultSet r1=ad.selectQuery(query2);
		try{
		while(r1.next()){
			ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
			//uid.add(r1.getString("unit_of_meas_id"));
			sf.setU_id(r1.getString("UNIT_OF_MEAS_ID"));
			sf.setU_label((r1.getString("UNIT_OF_MEAS_ID")+"-"+r1.getString("LTXT")));
			uid.add(sf);
		}
		//sform.setUomId(uid);
		sform6.setUomLabel(uid);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		LinkedList p_label=new LinkedList();
		LinkedList p_id=new LinkedList();
		String query3="select * from PURCHASE_GROUP";
		ResultSet r2=ad.selectQuery(query3);
		try{
			while(r2.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setP_id((r2.getString("PURCHASE_GROUP_ID")));
			     sf.setP_all((r2.getString("PURCHASE_GROUP_ID")+"-"+r2.getString("PURCHASE_GROUP_DESC")));
				//p_label.add(sf);
			     p_id.add(sf);
			}
			
			sform6.setPlabel(p_id);
			//sform.setPid(p_id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		LinkedList vid=new LinkedList();
		LinkedList vlabel=new LinkedList();
		String query4="select * from valuation_class";
		ResultSet r3=ad.selectQuery(query4);
		try{
			while(r3.next()){
				vid.add(r3.getString("VALUATION_ID"));
				vlabel.add(r3.getString("VALUATION_DESC"));
			}
			sform6.setVid(vid);
			sform6.setVlabel(vlabel);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		LinkedList pl_id=new LinkedList();
		LinkedList pl_all=new LinkedList();
		String availableLoc=user.getAvailableLocations();
		if(availableLoc.equalsIgnoreCase(""))
			availableLoc="0";
		String query5="select * from location where LOCID in("+availableLoc+")";
		ResultSet r4=ad.selectQuery(query5);
		try{
			while(r4.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				pl_id.add(r4.getString("LOCATION_CODE"));
				pl_all.add(r4.getString("LOCATION_CODE")+"-"+r4.getString("LOCNAME"));
				pl_id.add(sf);
			}
			if(pl_id.size()==0){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				pl_id.add("");
				pl_all.add("");
				pl_id.add(sf);
			}
			sform6.setPcode(pl_id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		LinkedList sg_label=new LinkedList();
		String query6="select * from MATERIAL_GROUP";
		ResultSet r5=ad.selectQuery(query6);
		try{
			while(r5.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setService_id(r5.getString("material_group_id"));
				sf.setService_label(r5.getString("material_group_id")+"-"+r5.getString("STXT"));
				sg_label.add(sf);
			}
			sform6.setSgroupLabel(sg_label);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String query_file="select file_name from service_documents where request_no='"+requstNo+"'";
		ArrayList documentDetails=new ArrayList();
		ResultSet rs_file=ad.selectQuery(query_file);
		try{
		while(rs_file.next()){
			ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
			sf.setSfile(rs_file.getString("file_name"));
			documentDetails.add(sf);
		}
		
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("documentDetails", documentDetails);
		String query="select * from SERVICE_MASTER where REQUEST_NO="+requstNo+"";
		System.out.println(query);
		try{
		ResultSet rs=ad.selectQuery(query);
		if(rs.next()){
			sform6.setR_no(requstNo);
			sform6.setPlantCode(rs.getString("plant_code"));
			sform6.setStorageLocation(rs.getString("storage_location"));
			sform6.setServiceDescription(rs.getString("service_description"));
			sform6.setDetailedServiceDescription(rs.getString("detailed_desc"));
			sform6.setUom(rs.getString("uom"));
			sform6.setPurchaseGroup(rs.getString("purchase_group"));
			sform6.setServiceCatagory(rs.getString("service_catagory"));
			sform6.setServiceGroup(rs.getString("service_group"));
			sform6.setE_m_name(rs.getString("machine_name"));
			sform6.setApp_amount(rs.getString("app_value"));
			sform6.setWhereUsed(rs.getString("where_used"));
			sform6.setPurpose(rs.getString("purpose"));
			sform6.setJustification(rs.getString("justification"));
			sform6.setValuationClass(rs.getString("valuation_class"));
			//sform6.setMessage("Edit Data");
			approveType=rs.getString("app_satus");
		}
		
		int i=0;
		String currentdate=EMicroUtils.getCurrentSysDate();
		  String a[]=currentdate.split("/");
		  for(int j=0;j<a.length;j++)
		  {
			  System.out.println("a="+a[j]);
		  }
		  currentdate=a[2]+"-"+a[1]+"-"+a[0];
		
		String updateMaterial="update SERVICE_MASTER set app_satus='"+sform6.getApproveType()+"',approve_date='"+currentdate+"',last_approver='"+user.getFullName()+"',pending_approver='No'  where request_no='"+sform6.getR_no()+"'";
		i=ad.SqlExecuteUpdate(updateMaterial);
		if(i>0)
		{
			approveType=sform6.getApproveType();
			sform6.setMessage("Code creation request updated with request number='"+sform6.getR_no()+"'");
			sform6.setMessage2("");
		}else{
			sform6.setMessage2("Error...When updating code creation reequest.Please Check");
			sform6.setMessage("");
		} 
		
		
		
		
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		setMasterData(mapping, form, request, response);
		
		String forwardType="display";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
			sform6.setApproveType(approveType);
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="serviceMasterSAP";
			sform6.setApproveType(approveType);
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="display";
		}
		return mapping.findForward("approverMaster");
		
	}
	
	public ActionForward displayServiceMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  userId=user.getId();
		//sform.setR_no(0);
		Integer r_no=0;
		int i=0;
		
		String query="select max(request_no) from service_master";
		ResultSet rs=ad.selectQuery(query);
		try{
		while(rs.next()){
			i=rs.getInt(1);
			i++;
			sform.setR_no(i);
			r_no=i;
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		LinkedList serviceGroupID=new LinkedList();
		LinkedList serviceGroupValues=new LinkedList();
		try{
		String getServiceGroup="select * from SERVICE_GROUP";
		ResultSet rsServiceGroup=ad.selectQuery(getServiceGroup);
		while(rsServiceGroup.next())
		{
			serviceGroupID.add(rsServiceGroup.getString("group_id"));
			serviceGroupValues.add(rsServiceGroup.getString("group_id")+" - "+rsServiceGroup.getString("STXT"));
		}
		sform.setServiceGroupID(serviceGroupID);
		sform.setServiceGroupValues(serviceGroupValues);
	
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		String getValuationClass="select * from VALUATION_CLASS ";
		ResultSet rsValuation=ad.selectQuery(getValuationClass);
		while(rsValuation.next()){
		
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
		sform.setValuationClassID(valuationClassID);
		sform.setValuationClassName(valuationClassName);
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		setMasterData(mapping, form, request, response);
		sform.setRequestDate(EMicroUtils.getCurrentSysDate());
	
		sform.setPlantCode(user.getPlantId());
		sform.setStorageLocation("");
		sform.setServiceDescription("");
		sform.setDetailedServiceDescription("");
		sform.setUom("");
		sform.setPurchaseGroup("");
		sform.setServiceCatagory("");
		sform.setServiceGroup("");
		sform.setE_m_name("");
		sform.setApp_amount("");
		sform.setWhereUsed("");
		sform.setPurpose("");
		sform.setJustification("");
		sform.setValuationClass("");
		sform.setTypeDetails("Save");
		
		return mapping.findForward("display");
	}
	public ActionForward saveServiceMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		int userId=user.getId();
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		int r_no=sform.getR_no();
		int c=0;
		int f=0;
		
		try{
			int i=0;
			String typeDetails=sform.getTypeDetails();
			if(typeDetails.equalsIgnoreCase("Save")){
			int old_RequestNo=sform.getR_no();
			int count=0;
			
			String getRequestNoCount="select count(*) from SERVICE_MASTER where REQUEST_NO='"+sform.getR_no()+"'";
			ResultSet rsGetRequestNoCount=ad.selectQuery(getRequestNoCount);
			while(rsGetRequestNoCount.next())
			{
				count=rsGetRequestNoCount.getInt(1);
			}
			//coded added for approvals 
			String approver="";
			String lApprover="";
			String approvermail="";
			String pendingApprovers="";
			
			String getApproverID="select * from Approvers_Details where Type='Service Master Request'";
			
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
			if(count>0)
			{
				//Request  available
				String getReqestNumber="select max(REQUEST_NO)  from SERVICE_MASTER";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				sform.setR_no(maxReqno);
				
				String currentdate=EMicroUtils.getCurrentSysDate();
				  String a[]=currentdate.split("/");
				  
				  currentdate=a[2]+"-"+a[1]+"-"+a[0];
				  
				  
			String query="INSERT INTO SERVICE_MASTER(PLANT_CODE,request_date,STORAGE_LOCATION,SERVICE_DESCRIPTION,DETAILED_DESC," +
					"UOM,PURCHASE_GROUP,SERVICE_CATAGORY,SERVICE_GROUP,MACHINE_NAME,APP_VALUE,WHERE_USED,PURPOSE,JUSTIFICATION,Attachment," +
					"VALUATION_CLASS,APP_SATUS,REQUEST_NO,CREATED_DATE,CREATED_BY)VALUES('"+sform.getPlantCode()+"','"+sform.getRequestDate()+"','"+sform.getStorageLocation()+"','"+sform.getServiceDescription()+"'," +
							"'"+sform.getDetailedServiceDescription()+"','"+sform.getUom()+"','"+sform.getPurchaseGroup()+"','"+sform.getServiceCatagory()+"',"+
							"'"+sform.getServiceGroup()+"','"+sform.getE_m_name()+"','"+sform.getApp_amount()+"','"+sform.getWhereUsed()+"','"+sform.getPurpose()+"','"+sform.getJustification()+"'," +
							"'"+sform.getSfile()+"',"+
									"'"+sform.getValuationClass()+"','Created','"+sform.getR_no()+"','"+currentdate+"','"+user.getEmployeeNo()+"')";
			
						
			i=ad.SqlExecuteUpdate(query);
			int jj=0;
			jj=ad.SqlExecuteUpdate("update SERVICE_MASTER set last_approver='No',pending_approver='"+pendingApprovers+"' where REQUEST_NO='"+sform.getR_no()+"'");
			
			if(i>0)
			{
				sform.setMessage("Code creation request saved with request number='"+sform.getR_no()+"' ");
				sform.setMessage2("");
				
				String deleteHistory="delete SERVICE_MASTER_HISTORY where request_no='"+sform.getR_no()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  	  		ad.SqlExecuteUpdate(deleteHistory);
				String saveinHistory="INSERT INTO SERVICE_MASTER_HISTORY(PLANT_CODE,request_date,STORAGE_LOCATION,SERVICE_DESCRIPTION,DETAILED_DESC," +
						"UOM,PURCHASE_GROUP,SERVICE_CATAGORY,SERVICE_GROUP,MACHINE_NAME,APP_VALUE,WHERE_USED,PURPOSE,JUSTIFICATION,Attachment," +
						"VALUATION_CLASS,APP_SATUS,REQUEST_NO,CREATED_DATE,CREATED_BY,Role,SAC_Code)VALUES('"+sform.getPlantCode()+"','"+sform.getRequestDate()+"','"+sform.getStorageLocation()+"','"+sform.getServiceDescription()+"'," +
						"'"+sform.getDetailedServiceDescription()+"','"+sform.getUom()+"','"+sform.getPurchaseGroup()+"','"+sform.getServiceCatagory()+"',"+
						"'"+sform.getServiceGroup()+"','"+sform.getE_m_name()+"','"+sform.getApp_amount()+"','"+sform.getWhereUsed()+"','"+sform.getPurpose()+"','"+sform.getJustification()+"'," +
						"'"+sform.getSfile()+"','"+sform.getValuationClass()+"','Created','"+sform.getR_no()+"','"+currentdate+"','"+user.getEmployeeNo()+"','user','"+sform.getSacCode()+"')";
				ad.SqlExecuteUpdate(saveinHistory);
				
				sform.setTypeDetails("Update");
				return displayServiceMaster(mapping, form, request, response);
			}else{
				sform.setMessage2("Error...When code saving creation request.Please check");
				sform.setMessage("");
				sform.setTypeDetails("Save");
				
			}
			
			int j=0;
			j=ad.SqlExecuteUpdate("update service_documents set request_no='"+sform.getR_no()+"' where request_no='"+old_RequestNo+"' and  userId='"+user.getEmployeeNo()+"'");
			
			}else{
				
				String currentdate=EMicroUtils.getCurrentSysDate();
				  String a[]=currentdate.split("/");
				  
				  currentdate=a[2]+"-"+a[1]+"-"+a[0];
				  
				  
			String query="INSERT INTO SERVICE_MASTER(PLANT_CODE,request_date,STORAGE_LOCATION,SERVICE_DESCRIPTION,DETAILED_DESC," +
					"UOM,PURCHASE_GROUP,SERVICE_CATAGORY,SERVICE_GROUP,MACHINE_NAME,APP_VALUE,WHERE_USED,PURPOSE,JUSTIFICATION,Attachment," +
					"VALUATION_CLASS,APP_SATUS,REQUEST_NO,CREATED_DATE,CREATED_BY,SAC_Code)VALUES('"+sform.getPlantCode()+"','"+sform.getRequestDate()+"','"+sform.getStorageLocation()+"','"+sform.getServiceDescription()+"'," +
							"'"+sform.getDetailedServiceDescription()+"','"+sform.getUom()+"','"+sform.getPurchaseGroup()+"','"+sform.getServiceCatagory()+"',"+
							"'"+sform.getServiceGroup()+"','"+sform.getE_m_name()+"','"+sform.getApp_amount()+"','"+sform.getWhereUsed()+"','"+sform.getPurpose()+"','"+sform.getJustification()+"'," +
							"'"+sform.getSfile()+"',"+
									"'"+sform.getValuationClass()+"','Created','"+sform.getR_no()+"','"+currentdate+"','"+user.getEmployeeNo()+"','"+sform.getSacCode()+"')";
			
						
			i=ad.SqlExecuteUpdate(query);
			int jj=0;
			jj=ad.SqlExecuteUpdate("update SERVICE_MASTER set last_approver='No',pending_approver='"+pendingApprovers+"' where REQUEST_NO='"+sform.getR_no()+"'");
			
			if(i>0)
			{
				sform.setMessage("Code creation request saved with  request number='"+sform.getR_no()+"' ");
				sform.setMessage2("");
				sform.setTypeDetails("Update");
				
				String deleteHistory="delete SERVICE_MASTER_HISTORY where request_no='"+sform.getR_no()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  	  		ad.SqlExecuteUpdate(deleteHistory);
				String saveinHistory="INSERT INTO SERVICE_MASTER_HISTORY(PLANT_CODE,request_date,STORAGE_LOCATION,SERVICE_DESCRIPTION,DETAILED_DESC," +
						"UOM,PURCHASE_GROUP,SERVICE_CATAGORY,SERVICE_GROUP,MACHINE_NAME,APP_VALUE,WHERE_USED,PURPOSE,JUSTIFICATION,Attachment," +
						"VALUATION_CLASS,APP_SATUS,REQUEST_NO,CREATED_DATE,CREATED_BY,Role,SAC_Code)VALUES('"+sform.getPlantCode()+"','"+sform.getRequestDate()+"','"+sform.getStorageLocation()+"','"+sform.getServiceDescription()+"'," +
						"'"+sform.getDetailedServiceDescription()+"','"+sform.getUom()+"','"+sform.getPurchaseGroup()+"','"+sform.getServiceCatagory()+"',"+
						"'"+sform.getServiceGroup()+"','"+sform.getE_m_name()+"','"+sform.getApp_amount()+"','"+sform.getWhereUsed()+"','"+sform.getPurpose()+"','"+sform.getJustification()+"'," +
						"'"+sform.getSfile()+"','"+sform.getValuationClass()+"','Created','"+sform.getR_no()+"','"+currentdate+"','"+user.getEmployeeNo()+"','user','"+sform.getSacCode()+"')";
				ad.SqlExecuteUpdate(saveinHistory);
				
				return displayServiceMaster(mapping, form, request, response);
			}else{
				sform.setMessage2("Error...When code saving creation request.Please check");
				sform.setMessage("");
				sform.setTypeDetails("Save");
			}
			
			}
			
			}else{
				String recordStatus="";
				String getRecordStatus="select APP_SATUS from SERVICE_MASTER where REQUEST_NO='"+sform.getR_no()+"' ";
				ResultSet rsRecord=ad.selectQuery(getRecordStatus);
				while(rsRecord.next())
				{
					recordStatus=rsRecord.getString("APP_SATUS");
				}
				if(recordStatus.equalsIgnoreCase("Rejected"))
				{
					
					String deleteRecords="delete from All_Request where Req_Id='"+sform.getR_no()+"' and Req_Type='Service Master'";
					int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
					System.out.println("deleteStatus="+deleteStatus);
					String updateFlag="update SERVICE_MASTER set rejected_flag='yes' where REQUEST_NO='"+sform.getR_no()+"'";
					ad.SqlExecuteUpdate(updateFlag);
				}
				//update
				String query="UPDATE SERVICE_MASTER SET PLANT_CODE='"+sform.getPlantCode()+"',request_date='"+sform.getRequestDate()+"',STORAGE_LOCATION='"+sform.getStorageLocation()+"'" +
				",SERVICE_DESCRIPTION='"+sform.getServiceDescription()+"',DETAILED_DESC='"+sform.getDetailedServiceDescription()+"'," +
		"UOM='"+sform.getUom()+"',PURCHASE_GROUP='"+sform.getPurchaseGroup()+"',SERVICE_CATAGORY='"+sform.getServiceCatagory()+"'" +
				",SERVICE_GROUP='"+sform.getServiceGroup()+"',MACHINE_NAME='"+sform.getE_m_name()+"',APP_VALUE='"+sform.getApp_amount()+"'" +
						",WHERE_USED='"+sform.getWhereUsed()+"',PURPOSE='"+sform.getPurpose()+"',JUSTIFICATION='"+sform.getJustification()+"'," +
						
					"Attachment='"+sform.getSfile()+"',VALUATION_CLASS='"+sform.getValuationClass()+"',APP_SATUS='Created',SAC_Code='"+sform.getSacCode()+"' WHERE REQUEST_NO="+r_no+"";
	  i=ad.SqlExecuteUpdate(query);
		if(i>0)
		{
			sform.setMessage(" Code creation request updated with  request number='"+sform.getR_no()+"' ");
			sform.setMessage2("");
			sform.setTypeDetails("Update");
			String currentdate=EMicroUtils.getCurrentSysDate();
			  String a[]=currentdate.split("/");
			  currentdate=a[2]+"-"+a[1]+"-"+a[0];
			String deleteHistory="delete SERVICE_MASTER_HISTORY where request_no='"+sform.getR_no()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
  	  		ad.SqlExecuteUpdate(deleteHistory);
			String saveinHistory="INSERT INTO SERVICE_MASTER_HISTORY(PLANT_CODE,request_date,STORAGE_LOCATION,SERVICE_DESCRIPTION,DETAILED_DESC," +
					"UOM,PURCHASE_GROUP,SERVICE_CATAGORY,SERVICE_GROUP,MACHINE_NAME,APP_VALUE,WHERE_USED,PURPOSE,JUSTIFICATION,Attachment," +
					"VALUATION_CLASS,APP_SATUS,REQUEST_NO,CREATED_DATE,CREATED_BY,Role,SAC_Code)VALUES('"+sform.getPlantCode()+"','"+sform.getRequestDate()+"','"+sform.getStorageLocation()+"','"+sform.getServiceDescription()+"'," +
					"'"+sform.getDetailedServiceDescription()+"','"+sform.getUom()+"','"+sform.getPurchaseGroup()+"','"+sform.getServiceCatagory()+"',"+
					"'"+sform.getServiceGroup()+"','"+sform.getE_m_name()+"','"+sform.getApp_amount()+"','"+sform.getWhereUsed()+"','"+sform.getPurpose()+"','"+sform.getJustification()+"'," +
					"'"+sform.getSfile()+"','"+sform.getValuationClass()+"','Created','"+sform.getR_no()+"','"+currentdate+"','"+user.getEmployeeNo()+"','user','"+sform.getSacCode()+"')";
			ad.SqlExecuteUpdate(saveinHistory);
			
			return displayServiceMaster(mapping, form, request, response);
		}else{
			sform.setMessage2("Error...When code updated creation request.Please check");
			sform.setMessage("");
			sform.setTypeDetails("Update");
		}
	   
	  
	
			}
			 String query2="select file_name from service_documents where request_no="+r_no+" and userId='"+user.getEmployeeNo()+"'";
				ArrayList documentDetails=new ArrayList();
				ResultSet rs2=ad.selectQuery(query2);

				while(rs2.next()){
					ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
					sf.setSfile(rs2.getString("file_name"));
					sf.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/"+rs2.getString("file_name")+"");
					documentDetails.add(sf);
				}
				
				request.setAttribute("documentDetails", documentDetails);
				setMasterData(mapping, form, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("display");
}
	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
			
			String fileName=null;
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int r_no=sform.getR_no();
			String filePath="";
			try{
				FormFile myFile = sform.getServiceAttachment();
			    String contentType = myFile.getContentType();
				 fileName = myFile.getFileName();
				 
				 String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
					int filesize=myFile.getFileSize();
				
				if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
				{
				 
				byte[] fileData = myFile.getFileData();
				
				String sql9="select count(*) from service_documents where  FILE_NAME='"+fileName+"' ";
				ResultSet rs15 = ad.selectQuery(sql9);
				int fileCount=0;
				while (rs15.next())
				{
					fileCount=Integer.parseInt(rs15.getString(1));
				}
				if(fileCount>0)
				{
					
					sform.setMessage2("");
					sform.setMessage("File aleardy uploaded please change  file name");
					
				}else{
					//file is not available	custForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/"+rs5.getString("file_name")+"");
				filePath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles");
				
				 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			 	 filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles";
				
				
				
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
						sform.setMessage2("");
						sform.setMessage("File Uploaded Successfully.");
					}
				}
				//sform.setMessage("Fiel uploaded");
				
				//upload files in another path
				
				try{
					String filePath1 = "E:/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles";
					
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
				
				
		
		  
			String query="INSERT INTO service_documents(REQUEST_NO,FILE_NAME,userId)VALUES('"+r_no+"','"+fileName+"','"+user.getEmployeeNo()+"')";
			int i=0;
			
			i=ad.SqlExecuteUpdate(query);
				}
				
				}else{
					sform.setMessage("");
					sform.setMessage2("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
					request.setAttribute("serfile", "serfile")	;	
				}
				
		
			String query2="select file_name from service_documents where request_no="+r_no+" and userId='"+user.getEmployeeNo()+"'";
			ArrayList documentDetails=new ArrayList();
			ResultSet rs2=ad.selectQuery(query2);

			while(rs2.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setSfile(rs2.getString("file_name"));
				sf.setFilepath(filePath+"/"+rs2.getString("file_name")+"");
				documentDetails.add(sf);
			}
			request.setAttribute("documentDetails", documentDetails);
			String typeDetails=sform.getTypeDetails();
			sform.setTypeDetails(typeDetails);
			setMasterData(mapping, form, request, response);
			
			getValuationClass(mapping, form, request, response);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return mapping.findForward("display");
			}
	public ActionForward deleteFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {	
		
		ServiceMasterRequestForm sform2=(ServiceMasterRequestForm)form;
		
		String[] documentCheck=sform2.getDocumentCheck();
		int documentLength=0;
		documentLength=documentCheck.length;
		String documentId="";
		int document=0;
		//String documentId="";
		String sql="";
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int  userId=user.getId();
		
try{
			
			if(documentLength>0)
			{
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			 	String filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles";
				
				for(int i=0;i<documentLength;i++)
				{
					document++;
					documentId=documentCheck[i];
        			
        			
        			System.out.println("requestId Is ********************"+documentId);
        			 
        			 
        			sql="delete from service_documents where file_name='"+documentId+"' and request_no='"+sform2.getR_no()+"' and userId='"+user.getEmployeeNo()+"' ";
        			 
        			System.out.println("Getting a sql is *************"+sql);
					int c=ad.SqlExecuteUpdate(sql);
					 
					if(c>0){
						 sform2.setMessage("Service Document Details Deleted sucessfully");
						 
						 File fileToCreate = new File(filePath, documentId);
 					 	boolean test=fileToCreate.delete();
 					 	System.out.println(test);
 					 	
 					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles", documentId);
 					 	boolean test1=fileToCreate1.delete();
						 
						 sform2.setMessage2("");
						 
					}else{
						 sform2.setMessage2("Error..While Deleting Documents");
						 sform2.setMessage("");
					}
					 
				}
			}
}
catch(Exception e){
	e.printStackTrace();
}
String query2="select file_name from service_documents where request_no='"+sform2.getR_no()+"' and userId='"+user.getEmployeeNo()+"'";
ArrayList documentDetails=new ArrayList();
ResultSet rs2=ad.selectQuery(query2);
try{
while(rs2.next()){
	ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
	sf.setSfile(rs2.getString("file_name"));
	sf.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/serviceMaster/UploadedFiles/"+rs2.getString("file_name")+"");
	documentDetails.add(sf);
}

request.setAttribute("documentDetails", documentDetails);
}
catch(Exception e){
	e.printStackTrace();
}
String typeDetails=sform2.getTypeDetails();
sform2.setTypeDetails(typeDetails);	
setMasterData(mapping, form, request, response);
getValuationClass(mapping, form, request, response);
		return mapping.findForward("display");
		
	}
	public ActionForward displayList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ResultSet rs;
		ArrayList serviceDetails=new ArrayList();
		ArrayList all=new ArrayList();
		ServiceMasterRequestForm sform3=(ServiceMasterRequestForm)form;
		sform3.setMessage("");
		sform3.setPlantCode("");
		sform3.setStorageLocation("");
		sform3.setServiceDescription("");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String query="select s.request_no,loc.LOCATION_CODE,s.plant_code,s.storage_location,s.service_description,s.app_satus from service_master as s,Location as loc where s.app_satus='In Process' and s.CREATED_BY='"+user.getEmployeeNo()+"' and loc.LOCID=s.plant_code order by s.request_no desc";
		try{
			rs=ad.selectQuery(query);
			while(rs.next()){
				ServiceMasterRequestForm sform4=new ServiceMasterRequestForm();
				sform4.setR_no(rs.getInt("request_no"));
				sform4.setPlantCode(rs.getString("LOCATION_CODE"));
				sform4.setLocationID(rs.getString("plant_code")); 
				sform4.setStorageLocation(rs.getString("storage_location"));
				sform4.setServiceDescription(rs.getString("service_description"));
				sform4.setApproveStatus(rs.getString("app_satus"));
				all.add(sform4);
			}
			
			if(all.size()==0){
				request.setAttribute("noRecords", "noRecords");
				sform3.setMessage1("No Records Found");
			}
			sform3.setApproveStatus("Pending");
		}
	
		catch(Exception e){
			e.printStackTrace();
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
			sform3.setLocationIdList(locationList);
			sform3.setLocationLabelList(locationLabelList);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		session.setAttribute("all", all);
		
		ArrayList serviceMasterList=new ArrayList();
		
		Iterator it=all.iterator();
		
		int i=0;
		while(i<10){
			if(it.hasNext()){
			serviceMasterList.add(it.next());
			i++;
			}
			else
				break;
		}
		sform3.setStartRecord(1);
		sform3.setEndRecord(i);
		sform3.setNext(i);
		if(i>9){
		request.setAttribute("nextButton", "yes");
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("displayRecordNo","ok");
		}
		else{
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("disableNextButton","yes");
		}
		
		request.setAttribute("serviceMasterList", serviceMasterList);
		
		//request.setAttribute("serviceMasterList", serviceDetails);
		
		sform3.setApproveStatus("In Process");
		return mapping.findForward("displayList");
		
	}
	
	public ActionForward deleteRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ServiceMasterRequestForm sform3=(ServiceMasterRequestForm)form;
		
		
		try{
			int reqNo[]=sform3.getRequiredRequestNumber();
			
			for(int j=0;j<reqNo.length;j++)
			{
			  
			   String deleteRecord="update  service_master  set app_satus='deleted' where REQUEST_NO='"+reqNo[j]+"'";
			   int i=0;
			   i=ad.SqlExecuteUpdate(deleteRecord);
			   if(i>=1)
			   {
				   sform3.setMessage("Service Master Record Successfully Deleted");
				   sform3.setMessage2("");
			   }else{
				   sform3.setMessage2("Error.... When Service Master Record.");
				   sform3.setMessage("");
			   }
			}
		}
			catch(Exception e){
				e.printStackTrace();
			}
			ResultSet rs;
			ArrayList all=new ArrayList();
			ArrayList serviceDetails=new ArrayList();
			ServiceMasterRequestForm sform4=(ServiceMasterRequestForm)form;
			String query="select * from service_master where app_satus='Pending'";
			try{
				rs=ad.selectQuery(query);
				while(rs.next()){
					ServiceMasterRequestForm sform5=new ServiceMasterRequestForm();
					sform5.setR_no(rs.getInt("id"));
					sform5.setPlantCode(rs.getString("plant_code"));
					sform5.setStorageLocation(rs.getString("storage_location"));
					sform5.setServiceDescription(rs.getString("service_description"));
					sform5.setApproveStatus(rs.getString("app_satus"));
					sform5.setMessage("");
					all.add(sform5);
					
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
			HttpSession session=request.getSession();
			session.setAttribute("all", all);
			
			ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=all.iterator();
			
			int i=0;
			while(i<10){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			sform3.setStartRecord(1);
			sform3.setEndRecord(i++);
			sform3.setNext(i);
			if(i==10){
			request.setAttribute("nextButton", "yes");
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("displayRecordNo","ok");
			}
			else{
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("disableNextButton","yes");
			}
			
			request.setAttribute("serviceMasterList", serviceMasterList);
			//request.setAttribute("serviceMasterList", serviceDetails);
		
		
		
		
		return mapping.findForward("displayList");
		
	}
	public ActionForward editRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int requstNo=Integer.parseInt(request.getParameter("requstNo"));
		System.out.println("rno---------------------"+requstNo);
		ServiceMasterRequestForm sform6=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String approveType="Pending";
		
		LinkedList uid=new LinkedList();
		LinkedList uname=new LinkedList();
		String query2="select * from UNIT_MESUREMENT";
		ResultSet r1=ad.selectQuery(query2);
		try{
		while(r1.next()){
			ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
			//uid.add(r1.getString("unit_of_meas_id"));
			sf.setU_id(r1.getString("UNIT_OF_MEAS_ID"));
			sf.setU_label((r1.getString("UNIT_OF_MEAS_ID")+"-"+r1.getString("LTXT")));
			uid.add(sf);
		}
		//sform.setUomId(uid);
		sform6.setUomLabel(uid);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		LinkedList p_label=new LinkedList();
		LinkedList p_id=new LinkedList();
		String query3="select * from PURCHASE_GROUP";
		ResultSet r2=ad.selectQuery(query3);
		try{
			while(r2.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setP_id((r2.getString("PURCHASE_GROUP_ID")));
			     sf.setP_all((r2.getString("PURCHASE_GROUP_ID")+"-"+r2.getString("PURCHASE_GROUP_DESC")));
				//p_label.add(sf);
			     p_id.add(sf);
			}
			
			sform6.setPlabel(p_id);
			//sform.setPid(p_id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		LinkedList vid=new LinkedList();
		LinkedList vlabel=new LinkedList();
		String query4="select * from valuation_class";
		ResultSet r3=ad.selectQuery(query4);
		try{
			while(r3.next()){
				vid.add(r3.getString("VALUATION_ID"));
				vlabel.add(r3.getString("VALUATION_DESC"));
			}
			sform6.setVid(vid);
			sform6.setVlabel(vlabel);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		LinkedList sg_label=new LinkedList();
		String query6="select * from MATERIAL_GROUP";
		ResultSet r5=ad.selectQuery(query6);
		try{
			while(r5.next()){
				ServiceMasterRequestForm sf=new ServiceMasterRequestForm();
				sf.setService_id(r5.getString("material_group_id"));
				sf.setService_label(r5.getString("material_group_id")+"-"+r5.getString("STXT"));
				sg_label.add(sf);
			}
			sform6.setSgroupLabel(sg_label);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		String query_file="select file_name from service_documents where request_no='"+requstNo+"'";
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
		String query="select * from SERVICE_MASTER where REQUEST_NO="+requstNo+"";
		System.out.println(query);
		try{
		ResultSet rs=ad.selectQuery(query);
		if(rs.next()){
			sform6.setR_no(requstNo);
			sform6.setPlantCode(rs.getString("plant_code"));
			sform6.setStorageLocation(rs.getString("storage_location"));
			sform6.setServiceDescription(rs.getString("service_description"));
			sform6.setDetailedServiceDescription(rs.getString("detailed_desc"));
			sform6.setUom(rs.getString("uom"));
			sform6.setPurchaseGroup(rs.getString("purchase_group"));
			sform6.setServiceCatagory(rs.getString("service_catagory"));
			sform6.setServiceGroup(rs.getString("service_group"));
			sform6.setE_m_name(rs.getString("machine_name"));
			sform6.setApp_amount(rs.getString("app_value"));
			sform6.setWhereUsed(rs.getString("where_used"));
			sform6.setPurpose(rs.getString("purpose"));
			sform6.setJustification(rs.getString("justification"));
			sform6.setValuationClass(rs.getString("valuation_class"));
			//sform6.setMessage("Edit Data");
			
		}
		sform6.setTypeDetails("Update");
		
		LinkedList valuationClassID=new LinkedList();
		LinkedList valuationClassName=new LinkedList();
		String serviceCategory=sform6.getServiceCatagory();
		String getValuationClass="select * from VALUATION_CLASS where MAT_TYPE='"+serviceCategory+"'";
		ResultSet rsValuation=ad.selectQuery(getValuationClass);
		while(rsValuation.next()){
		
				valuationClassID.add(rsValuation.getString("VALUATION_ID"));
				valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
			}
		sform6.setValuationClassID(valuationClassID);
		sform6.setValuationClassName(valuationClassName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		setMasterData(mapping, form, request, response);
		
		String forwardType="display";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
			sform6.setApproveType(approveType);
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="serviceMasterSAP";
			sform6.setApproveType(approveType);
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="display";
		}
		return mapping.findForward(forwardType);
		
	}
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int r_no;
		String plant_code;
		String approveStatus;
		
		 HttpSession session=request.getSession();
		 UserInfo user=(UserInfo)session.getAttribute("user");
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		
		r_no=sform.getR_no();
		plant_code=sform.getPlantCode();
		approveStatus =sform.getApproveStatus();
		
		int userid=user.getId();
		String query=null;
		
	
		
		
		  query="select s.request_no,loc.LOCATION_CODE,s.plant_code,s.storage_location,s.service_description,s.app_satus from SERVICE_MASTER as s,Location as loc " +
		  		"where s.created_by='"+user.getEmployeeNo()+"' and s.plant_code=loc.LOCID ";
		
		if(!plant_code.equalsIgnoreCase(""))
			query=query+" and  s.plant_code='"+plant_code+"'";
		if(!approveStatus.equalsIgnoreCase(""))
			query=query+" and s.app_satus='"+approveStatus+"'";
		
		query=query+"order by s.request_no desc";
		ResultSet rs;
		ArrayList all=new ArrayList();
		System.out.println(query);
		//ServiceMasterRequestForm sform4=(ServiceMasterRequestForm)form;
		//String query="select * from service_master";
		try{
			rs=ad.selectQuery(query);
			while(rs.next()){
				ServiceMasterRequestForm sform5=new ServiceMasterRequestForm();
				sform5.setR_no(rs.getInt("REQUEST_NO"));
				sform5.setPlantCode(rs.getString("LOCATION_CODE"));
				sform5.setLocationID(rs.getString("plant_code")); 
				sform5.setStorageLocation(rs.getString("storage_location"));
				sform5.setServiceDescription(rs.getString("service_description"));
				sform5.setApproveStatus(rs.getString("app_satus"));
				sform5.setMessage("");
				all.add(sform5);
				
			}
			
			if(all.size()==0){
				request.setAttribute("noRecords", "noRecords");
				sform.setMessage1("No Records Found");
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//HttpSession session=request.getSession();
		session.setAttribute("all", all);
		
		ArrayList serviceMasterList=new ArrayList();
		
		Iterator it=all.iterator();
		
		int i=0;
		while(i<10){
			if(it.hasNext()){
			serviceMasterList.add(it.next());
			i++;
			}
			else
				break;
		}
		sform.setStartRecord(1);
		
	int total=i;
	
		
		sform.setEndRecord(i);
		
		System.out.println(sform.getEndRecord());
		sform.setNext(i);
		if(it.hasNext()){
		request.setAttribute("nextButton", "yes");
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("displayRecordNo","ok");
		}
		else{
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("disableNextButton","yes");
		}
		
		request.setAttribute("serviceMasterList", serviceMasterList);
		
		
	
		return mapping.findForward("displayList");
	}
	//----------------------------------------------------Next Ten Records--------------------------------------------------------//
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		int nextval=sform.getNext();
		sform.setStartRecord(nextval);
		sform.setPrev(nextval);
		int end=nextval+10;
		//sform.setEndRecord(end);
		int start=1;
		
		 HttpSession session=request.getSession();
		 UserInfo user=(UserInfo)session.getAttribute("user");
		String plant_code;
		String approveStatus;
		plant_code=sform.getPlantCode();
		approveStatus =sform.getApproveStatus();
		String	 query="select s.request_no,loc.LOCATION_CODE,s.plant_code,s.storage_location,s.service_description,s.app_satus from SERVICE_MASTER as s,Location as loc " +
		  		"where s.created_by='"+user.getEmployeeNo()+"' and s.plant_code=loc.LOCID ";
		
		if(!plant_code.equalsIgnoreCase(""))
			query=query+" and  s.plant_code='"+plant_code+"'";
		if(!approveStatus.equalsIgnoreCase(""))
			query=query+" and s.app_satus='"+approveStatus+"'";
		
		query=query+"order by s.request_no desc";
		ResultSet rs;
		ArrayList alldata=new ArrayList();
		System.out.println(query);
		//ServiceMasterRequestForm sform4=(ServiceMasterRequestForm)form;
		//String query="select * from service_master";
		try{
			rs=ad.selectQuery(query);
			while(rs.next()){
				ServiceMasterRequestForm sform5=new ServiceMasterRequestForm();
				sform5.setR_no(rs.getInt("REQUEST_NO"));
				sform5.setPlantCode(rs.getString("LOCATION_CODE"));
				sform5.setLocationID(rs.getString("plant_code")); 
				sform5.setStorageLocation(rs.getString("storage_location"));
				sform5.setServiceDescription(rs.getString("service_description"));
				sform5.setApproveStatus(rs.getString("app_satus"));
				sform5.setMessage("");
				alldata.add(sform5);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
		ArrayList serviceMasterList=new ArrayList();
		Iterator it=alldata.iterator();
		try{
		while(start<end){
			if(it.hasNext()&&start==nextval){
				serviceMasterList.add(it.next());
				nextval++;
				
			}
			else
			it.next();
			start++;
		}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		sform.setEndRecord(nextval);
		if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
		request.setAttribute("previousButton", "ok");
		}
		else{
			request.setAttribute("disableNextButton","yes");
		request.setAttribute("previousButton", "ok");
		}
		request.setAttribute("displayRecordNo","ok");	
		
		sform.setNext(nextval);
		
	
		int startRecord=sform.getStartRecord();
		sform.setStartRecord(startRecord+1);
		
		
		request.setAttribute("serviceMasterList", serviceMasterList);
		
		return mapping.findForward("displayList");
	}
	//-----------------------------------------------------------------------------------------------------------------------------//
	//------------------------------------------Previous Ten Records-----------------------------------------------------------------//
	public ActionForward prevRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		
		int prev=sform.getPrev();
		int start=prev-10;
		//start--;
		//prev--;
		if(start<0)
			start=1;
		sform.setStartRecord(start);
		sform.setEndRecord(prev);
		int i=0;
		
		
		 UserInfo user=(UserInfo)session.getAttribute("user");
		String plant_code;
		String approveStatus;
		plant_code=sform.getPlantCode();
		approveStatus =sform.getApproveStatus();
		String	 query="select s.request_no,loc.LOCATION_CODE,s.plant_code,s.storage_location,s.service_description,s.app_satus from SERVICE_MASTER as s,Location as loc " +
		  		"where s.created_by='"+user.getEmployeeNo()+"' and s.plant_code=loc.LOCID ";
		
		if(!plant_code.equalsIgnoreCase(""))
			query=query+" and  s.plant_code='"+plant_code+"'";
		if(!approveStatus.equalsIgnoreCase(""))
			query=query+" and s.app_satus='"+approveStatus+"'";
		
		query=query+"order by s.request_no desc";
		ResultSet rs;
		ArrayList alldata=new ArrayList();
		System.out.println(query);
		//ServiceMasterRequestForm sform4=(ServiceMasterRequestForm)form;
		//String query="select * from service_master";
		try{
			rs=ad.selectQuery(query);
			while(rs.next()){
				ServiceMasterRequestForm sform5=new ServiceMasterRequestForm();
				sform5.setR_no(rs.getInt("REQUEST_NO"));
				sform5.setPlantCode(rs.getString("LOCATION_CODE"));
				sform5.setLocationID(rs.getString("plant_code")); 
				sform5.setStorageLocation(rs.getString("storage_location"));
				sform5.setServiceDescription(rs.getString("service_description"));
				sform5.setApproveStatus(rs.getString("app_satus"));
				sform5.setMessage("");
				alldata.add(sform5);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList serviceMasterList=new ArrayList();
		
		
		Iterator it=alldata.iterator();
		while(i<prev){
			if(it.hasNext()&&i==start){
				serviceMasterList.add(it.next());
				start++;
			}
			else
			it.next();
			i++;
		}
		if((prev-10)<=0){
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("nextButton", "ok");
	          }
	    
		else{
			request.setAttribute("previousButton", "ok");
		request.setAttribute("nextButton", "ok");
		}
		sform.setPrev(prev-10);
		sform.setNext(prev);
		
		System.out.println(sform.getStartRecord());
		System.out.println(sform.getEndRecord());
		
		int startRecord=sform.getStartRecord();
		int endRecord=sform.getEndRecord();
	    sform.setEndRecord(endRecord); 
		if(startRecord==0)
		{
			sform.setStartRecord(1);
		}
		
		
		request.setAttribute("displayRecordNo","ok");
		request.setAttribute("serviceMasterList", serviceMasterList);
		
		return mapping.findForward("displayList");
	}
	//----------------------------------------------------------------------------------------------------//
	//---------------------------------Last record-------------------------------------------------------//
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		
		
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();

		
		
		int prev=sform.getPrev();
		int start=0;
		
		prev--;
		
		int i=0;
		 UserInfo user=(UserInfo)session.getAttribute("user");
			String plant_code;
			String approveStatus;
			plant_code=sform.getPlantCode();
			approveStatus =sform.getApproveStatus();
			String	 query="select s.request_no,loc.LOCATION_CODE,s.plant_code,s.storage_location,s.service_description,s.app_satus from SERVICE_MASTER as s,Location as loc " +
			  		"where s.created_by='"+user.getEmployeeNo()+"' and s.plant_code=loc.LOCID ";
			
			if(!plant_code.equalsIgnoreCase(""))
				query=query+" and  s.plant_code='"+plant_code+"'";
			if(!approveStatus.equalsIgnoreCase(""))
				query=query+" and s.app_satus='"+approveStatus+"'";
			
			query=query+"order by s.request_no desc";
			ResultSet rs;
			ArrayList alldata=new ArrayList();
			System.out.println(query);
			//ServiceMasterRequestForm sform4=(ServiceMasterRequestForm)form;
			//String query="select * from service_master";
			try{
				rs=ad.selectQuery(query);
				while(rs.next()){
					ServiceMasterRequestForm sform5=new ServiceMasterRequestForm();
					sform5.setR_no(rs.getInt("REQUEST_NO"));
					sform5.setPlantCode(rs.getString("LOCATION_CODE"));
					sform5.setLocationID(rs.getString("plant_code")); 
					sform5.setStorageLocation(rs.getString("storage_location"));
					sform5.setServiceDescription(rs.getString("service_description"));
					sform5.setApproveStatus(rs.getString("app_satus"));
					sform5.setMessage("");
					alldata.add(sform5);
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		ArrayList serviceMasterList=new ArrayList();
		
		
		ListIterator it=alldata.listIterator();
		int l=0;
		int j=0;
		while(it.hasNext())
		{
			l++;
			it.next();
		}
		j=l;
		while(j>0)
		{
			it.previous();
			j--;
		}
		i=l-10;
		sform.setStartRecord(i);
		sform.setPrev(i);
		while(i<l){
			if(it.hasNext()&&i==start){
				serviceMasterList.add(it.next());
				
				i++;
			}
			else if(it.hasNext())
			it.next();
			else
				break;
			start++;
		}
		
			request.setAttribute("previousButton", "ok");
			request.setAttribute("disableNextButton","yes");
		//request.setAttribute("nextButton", "ok");
		
		
		sform.setEndRecord(l);
	
		//sform.setNext(prev);
		request.setAttribute("displayRecordNo","ok");
		request.setAttribute("serviceMasterList", serviceMasterList);
		
		
		
		
		
		
		return mapping.findForward("displayList");
		
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	//-----------------------------First ten Records---------------------------------------------------//
	
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceMasterRequestForm sform=(ServiceMasterRequestForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String plant_code;
		String approveStatus;
		plant_code=sform.getPlantCode();
		approveStatus =sform.getApproveStatus();
		String	 query="select s.request_no,loc.LOCATION_CODE,s.plant_code,s.storage_location,s.service_description,s.app_satus from SERVICE_MASTER as s,Location as loc " +
		  		"where s.created_by='"+user.getEmployeeNo()+"' and s.plant_code=loc.LOCID ";
		
		if(!plant_code.equalsIgnoreCase(""))
			query=query+" and  s.plant_code='"+plant_code+"'";
		if(!approveStatus.equalsIgnoreCase(""))
			query=query+" and s.app_satus='"+approveStatus+"'";
		
		query=query+"order by s.request_no desc";
		ResultSet rs;
		ArrayList alldata=new ArrayList();
		System.out.println(query);
		//ServiceMasterRequestForm sform4=(ServiceMasterRequestForm)form;
		//String query="select * from service_master";
		try{
			rs=ad.selectQuery(query);
			while(rs.next()){
				ServiceMasterRequestForm sform5=new ServiceMasterRequestForm();
				sform5.setR_no(rs.getInt("REQUEST_NO"));
				sform5.setPlantCode(rs.getString("LOCATION_CODE"));
				sform5.setLocationID(rs.getString("plant_code")); 
				sform5.setStorageLocation(rs.getString("storage_location"));
				sform5.setServiceDescription(rs.getString("service_description"));
				sform5.setApproveStatus(rs.getString("app_satus"));
				sform5.setMessage("");
				alldata.add(sform5);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		LinkedList pl_id=new LinkedList();
		LinkedList pl_all=new LinkedList();
		
		
		ArrayList serviceMasterList=new ArrayList();
		
		Iterator it=alldata.iterator();
		
		int i=0;
		while(i<10){
			if(it.hasNext()){
			serviceMasterList.add(it.next());
			i++;
			}
			else
				break;
		}
		sform.setStartRecord(1);
		sform.setEndRecord(i++);
		sform.setNext(i);
		if(i>9){
		request.setAttribute("nextButton", "yes");
		request.setAttribute("disablePreviousButton", "ok");
		}
		else{
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("disableNextButton","yes");
		}
		request.setAttribute("displayRecordNo","ok");
		request.setAttribute("serviceMasterList", serviceMasterList);
		
		//request.setAttribute("serviceMasterList", serviceDetails);
		
		return mapping.findForward("displayList");
		
		//return mapping.findForward("displayList");
		
	}
	//-------------------------------------------------------------------------------------------------------------------------------------//
	
	/*For Displaying request details in approvals page*/
	public ServiceMasterRequestForm setFormElements(ResultSet smRS){
		NewsandMediaDao ad=new NewsandMediaDao();
		ServiceMasterRequestForm smForm= new ServiceMasterRequestForm();
		try{
			smForm.setServiceDescription(smRS.getString("service_description"));
			smForm.setDetailedServiceDescription(smRS.getString("detailed_desc"));
			smForm.setUom(smRS.getString("uom"));
			smForm.setPurchaseGroup(smRS.getString("purchase_group"));
			smForm.setServiceCatagory(smRS.getString("service_catagory"));
			smForm.setServiceGroup(smRS.getString("service_group"));
			smForm.setE_m_name(smRS.getString("machine_name"));
			smForm.setApp_amount(smRS.getString("app_value"));
			smForm.setWhereUsed(smRS.getString("where_used"));
			smForm.setPurpose(smRS.getString("purpose"));
			smForm.setJustification(smRS.getString("justification"));
			smForm.setValuationClass(smRS.getString("valuation_class"));
			
		}
		catch (SQLException sqle) { System.out.println("SQLException @ set Vendor Form Values"); sqle.printStackTrace();}
		return smForm;
	}
	
}

