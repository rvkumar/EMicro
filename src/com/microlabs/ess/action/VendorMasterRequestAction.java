package com.microlabs.ess.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.springframework.util.StringUtils;

import com.microlabs.admin.form.LinksForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.MaterialmasterDAO;
import com.microlabs.ess.form.VendorMasterRequestForm;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.newsandmedia.form.NewsAndMediaForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;


public class VendorMasterRequestAction extends DispatchAction{
	
	
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		VendorMasterRequestForm masterForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList apprList=new LinkedList();
		MaterialmasterDAO dao=new MaterialmasterDAO();
		String accountGroupId=request.getParameter("accountGroupId");
		String vendorType="";
		
		String getMatGroup="select acc.ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M as acc where acc.ACCOUNT_GROUP_ID='"+accountGroupId+"'";
	 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
	 	try{
	 	while(rsMatGrup.next()){
	 		vendorType=rsMatGrup.getString("ACCOUNT_GROUP_NAME");
	 	}
	 	}catch(Exception e){
	 		e.printStackTrace();
	 	}
	 	if(vendorType.equalsIgnoreCase("Import")){
	 		
	 	}else{
	 		vendorType="Local";
	 	}
		apprList=dao.vendorApprsList(vendorType);
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
		return mapping.findForward("apprList");
	}
	public ActionForward lastCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		VendorMasterRequestForm v=(VendorMasterRequestForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList materialList=new LinkedList();
		String name=v.getName1();
		String city=v.getCity1();
		String country=v.getCountry1();
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		try{
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
			}
		v.setCountryList(countryList);
		v.setCountryLabelList(countryLabelList);
		

	 	int  totalCodeSearch=v.getTotalCodeSearch();
	     int  codeStartRecord=v.getCodeStartRecord();
	     int  codeEndRecord=v.getCodeEndRecord();
	     
	     codeStartRecord=totalCodeSearch-9;
	     codeEndRecord=totalCodeSearch;
	     
	     v.setTotalCodeSearch(totalCodeSearch);
			v.setCodeStartRecord(codeStartRecord);
			v.setCodeEndRecord(totalCodeSearch);
		 
			  String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY vend.REQUEST_NO) AS RowNum, vend.REQUEST_NO,acc.ACCOUNT_GROUP_NAME,vend.SAP_CODE_NO,vend.NAME,vend.CITY,vend.SAP_CREATION_DATE,vend.REQUEST_DATE from "
						+ "VENDOR_CODES as vend,ACCOUNT_GROUP_M as acc where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID and  vend.NAME like '%"+name+"%'  and  "
						+ "vend.CITY like '%"+city+"%' ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";		
						if(!country.equalsIgnoreCase(""))
						{
							data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY vend.REQUEST_NO) AS RowNum, vend.REQUEST_NO,acc.ACCOUNT_GROUP_NAME,vend.SAP_CODE_NO,vend.NAME,vend.CITY,vend.SAP_CREATION_DATE,vend.REQUEST_DATE,cou.LANDX from VENDOR_CODES as vend," +
							"ACCOUNT_GROUP_M as acc,Country as cou where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID  " +
							"and  vend.NAME like '%"+name+"%' and  vend.CITY like '%"+city+"%'  and vend.COUNTRY_ID='"+country+"' and vend.COUNTRY_ID=cou.LAND1) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";
						}
						ResultSet rs=ad.selectQuery(data);
						
							while(rs.next()) 
							{
								VendorMasterRequestForm form2=new VendorMasterRequestForm();
								
								if(rs.getString("REQUEST_NO")==null)
								{
									form2.setRequestNumber("");
								}
								else{
									form2.setRequestNumber(rs.getString("REQUEST_NO"));
								}
								form2.setAccountGroupId(rs.getString("ACCOUNT_GROUP_NAME"));
								form2.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								form2.setName(rs.getString("NAME"));
								form2.setCity(rs.getString("CITY"));
								String cretedOn=rs.getString("SAP_CREATION_DATE");
								   if(cretedOn!=null)
								   {
									   String a[]=cretedOn.split(" ");
									   cretedOn=a[0];
									   String b[]=cretedOn.split("-");
									   cretedOn=b[2]+"/"+b[1]+"/"+b[0];
									   form2.setSapCreationDate(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
								   }
								   String requestedOn=rs.getString("REQUEST_DATE");
								   if(requestedOn!=null)
								   {
									   String a[]=requestedOn.split(" ");
									   requestedOn=a[0];
									   String b[]=requestedOn.split("-");
									   requestedOn=b[2]+"/"+b[1]+"/"+b[0];
									   form2.setRequestDate(EMicroUtils.display1(rs.getDate("REQUEST_DATE")));
								   }
							
								 materialList.add(form2);
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
		return mapping.findForward("vendorMasterSearch");
	}
	
	public ActionForward previousCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		VendorMasterRequestForm v=(VendorMasterRequestForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList materialList=new LinkedList();
		String name=v.getName1();
		String city=v.getCity1();
		String country=v.getCountry1();
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		try{
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
			}
		v.setCountryList(countryList);
		v.setCountryLabelList(countryLabelList);
		

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
			  String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY vend.REQUEST_NO) AS RowNum, vend.REQUEST_NO,acc.ACCOUNT_GROUP_NAME,vend.SAP_CODE_NO,vend.NAME,vend.CITY,vend.SAP_CREATION_DATE,vend.REQUEST_DATE from "
						+ "VENDOR_CODES as vend,ACCOUNT_GROUP_M as acc where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID and  vend.NAME like '%"+name+"%'  and  "
						+ "vend.CITY like '%"+city+"%' ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";		
						if(!country.equalsIgnoreCase(""))
						{
							data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY vend.REQUEST_NO) AS RowNum, vend.REQUEST_NO,acc.ACCOUNT_GROUP_NAME,vend.SAP_CODE_NO,vend.NAME,vend.CITY,vend.SAP_CREATION_DATE,vend.REQUEST_DATE,cou.LANDX from VENDOR_CODES as vend," +
							"ACCOUNT_GROUP_M as acc,Country as cou where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID  " +
							"and  vend.NAME like '%"+name+"%' and  vend.CITY like '%"+city+"%'  and vend.COUNTRY_ID='"+country+"' and vend.COUNTRY_ID=cou.LAND1) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";
						}
						ResultSet rs=ad.selectQuery(data);
						
							while(rs.next()) 
							{
								VendorMasterRequestForm form2=new VendorMasterRequestForm();
								
								if(rs.getString("REQUEST_NO")==null)
								{
									form2.setRequestNumber("");
								}
								else{
									form2.setRequestNumber(rs.getString("REQUEST_NO"));
								}
								form2.setAccountGroupId(rs.getString("ACCOUNT_GROUP_NAME"));
								form2.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								form2.setName(rs.getString("NAME"));
								form2.setCity(rs.getString("CITY"));
								String cretedOn=rs.getString("SAP_CREATION_DATE");
								   if(cretedOn!=null)
								   {
									   String a[]=cretedOn.split(" ");
									   cretedOn=a[0];
									   String b[]=cretedOn.split("-");
									   cretedOn=b[2]+"/"+b[1]+"/"+b[0];
									   form2.setSapCreationDate(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
								   }
								   String requestedOn=rs.getString("REQUEST_DATE");
								   if(requestedOn!=null)
								   {
									   String a[]=requestedOn.split(" ");
									   requestedOn=a[0];
									   String b[]=requestedOn.split("-");
									   requestedOn=b[2]+"/"+b[1]+"/"+b[0];
									   form2.setRequestDate(EMicroUtils.display1(rs.getDate("REQUEST_DATE")));
								   }
							
								 materialList.add(form2);
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
		return mapping.findForward("vendorMasterSearch");
	}
	public ActionForward nextCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		VendorMasterRequestForm v=(VendorMasterRequestForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList materialList=new LinkedList();
		String name=v.getName1();
		String city=v.getCity1();
		String country=v.getCountry1();
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		try {
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
			}
		v.setCountryList(countryList);
		v.setCountryLabelList(countryLabelList);
		
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
					
					String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY vend.REQUEST_NO) AS RowNum, vend.REQUEST_NO,acc.ACCOUNT_GROUP_NAME,vend.SAP_CODE_NO,vend.NAME,vend.CITY,vend.SAP_CREATION_DATE,vend.REQUEST_DATE from "
							+ "VENDOR_CODES as vend,ACCOUNT_GROUP_M as acc where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID and  vend.NAME like '%"+name+"%'  and  "
							+ "vend.CITY like '%"+city+"%' ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";		
							if(!country.equalsIgnoreCase(""))
							{
								data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY vend.REQUEST_NO) AS RowNum, vend.REQUEST_NO,acc.ACCOUNT_GROUP_NAME,vend.SAP_CODE_NO,vend.NAME,vend.CITY,vend.SAP_CREATION_DATE,vend.REQUEST_DATE,cou.LANDX from VENDOR_CODES as vend," +
								"ACCOUNT_GROUP_M as acc,Country as cou where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID  " +
								"and  vend.NAME like '%"+name+"%' and  vend.CITY like '%"+city+"%'  and vend.COUNTRY_ID='"+country+"' and vend.COUNTRY_ID=cou.LAND1) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";
							}
							ResultSet rs=ad.selectQuery(data);
							
								while(rs.next()) 
								{
									VendorMasterRequestForm form2=new VendorMasterRequestForm();
									
									if(rs.getString("REQUEST_NO")==null)
									{
										form2.setRequestNumber("");
									}
									else{
										form2.setRequestNumber(rs.getString("REQUEST_NO"));
									}
									form2.setAccountGroupId(rs.getString("ACCOUNT_GROUP_NAME"));
									form2.setSapCodeNo(rs.getString("SAP_CODE_NO"));
									form2.setName(rs.getString("NAME"));
									form2.setCity(rs.getString("CITY"));
									String cretedOn=rs.getString("SAP_CREATION_DATE");
									   if(cretedOn!=null)
									   {
										   String a[]=cretedOn.split(" ");
										   cretedOn=a[0];
										   String b[]=cretedOn.split("-");
										   cretedOn=b[2]+"/"+b[1]+"/"+b[0];
										   form2.setSapCreationDate(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
									   }
									   String requestedOn=rs.getString("REQUEST_DATE");
									   if(requestedOn!=null)
									   {
										   String a[]=requestedOn.split(" ");
										   requestedOn=a[0];
										   String b[]=requestedOn.split("-");
										   requestedOn=b[2]+"/"+b[1]+"/"+b[0];
										   form2.setRequestDate(EMicroUtils.display1(rs.getDate("REQUEST_DATE")));
									   }
								
									 materialList.add(form2);
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
		  
		return mapping.findForward("vendorMasterSearch");
	}
	public ActionForward showVendorSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=new EssDao();
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	int userID=user.getId();
	LinkedList materialList=new LinkedList();
	String name=vendorMasterRequestForm.getName1();
	String city=vendorMasterRequestForm.getCity1();
	String country=vendorMasterRequestForm.getCountry1();
	
	ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
	ArrayList countryList=new ArrayList();
	ArrayList countryLabelList=new ArrayList();
	
	try {
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
		}
	} catch (SQLException e1) {
	
		e1.printStackTrace();
	}
	vendorMasterRequestForm.setCountryList(countryList);
	vendorMasterRequestForm.setCountryLabelList(countryLabelList);
    
	  int  totalCodeSearch=0;
	  int  codeStartRecord=0;
	  int  codeEndRecord=0;
	  
	  
	String getCount="Select count(*) from VENDOR_CODES as vend,ACCOUNT_GROUP_M as acc where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID and  vend.NAME like '%"+name+"%'  and  "
	+ "vend.CITY like '%"+city+"%'";
	
	if(!country.equalsIgnoreCase(""))
	{
		getCount="Select count(*) from VENDOR_CODES as vend,ACCOUNT_GROUP_M as acc,Country as cou where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID  " +
		"and  vend.NAME like '%"+name+"%' and  vend.CITY like '%"+city+"%'  and vend.COUNTRY_ID='"+country+"' and vend.COUNTRY_ID=cou.LAND1";
	}
	ResultSet rsCount=ad.selectQuery(getCount);
	try {
	while(rsCount.next()){
		totalCodeSearch=rsCount.getInt(1);
	}
	if(totalCodeSearch>=10)
	  {
		  vendorMasterRequestForm.setTotalCodeSearch(totalCodeSearch);
		  codeStartRecord=1;
		  codeEndRecord=10;
		  vendorMasterRequestForm.setCodeStartRecord(1);
		  vendorMasterRequestForm.setCodeEndRecord(10);
	  request.setAttribute("displayRecordNo", "displayRecordNo");
	  request.setAttribute("nextButton", "nextButton");
	  }else
	  {
		  codeStartRecord=1;
		  codeEndRecord=totalCodeSearch;
		  vendorMasterRequestForm.setTotalCodeSearch(totalCodeSearch);
		  vendorMasterRequestForm.setCodeStartRecord(1);
		  vendorMasterRequestForm.setCodeEndRecord(totalCodeSearch); 
	  }
	
	String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY vend.REQUEST_NO) AS RowNum, vend.REQUEST_NO,acc.ACCOUNT_GROUP_NAME,vend.SAP_CODE_NO,vend.NAME,vend.CITY,vend.SAP_CREATION_DATE,vend.REQUEST_DATE from "
	+ "VENDOR_CODES as vend,ACCOUNT_GROUP_M as acc where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID and  vend.NAME like '%"+name+"%'  and  "
	+ "vend.CITY like '%"+city+"%' ) as  sub Where  sub.RowNum between 1 and 10";		
	if(!country.equalsIgnoreCase(""))
	{
		data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY vend.REQUEST_NO) AS RowNum, vend.REQUEST_NO,acc.ACCOUNT_GROUP_NAME,vend.SAP_CODE_NO,vend.NAME,vend.CITY,vend.SAP_CREATION_DATE,vend.REQUEST_DATE,cou.LANDX from VENDOR_CODES as vend," +
		"ACCOUNT_GROUP_M as acc,Country as cou where acc.ACCOUNT_GROUP_ID=vend.ACCOUNT_GROUP_ID  " +
		"and  vend.NAME like '%"+name+"%' and  vend.CITY like '%"+city+"%'  and vend.COUNTRY_ID='"+country+"' and vend.COUNTRY_ID=cou.LAND1) as  sub Where  sub.RowNum between 1 and 10";
	}
	ResultSet rs=ad.selectQuery(data);
	
		while(rs.next()) 
		{
			VendorMasterRequestForm form2=new VendorMasterRequestForm();
			
			if(rs.getString("REQUEST_NO")==null)
			{
				form2.setRequestNumber("");
			}
			else{
				form2.setRequestNumber(rs.getString("REQUEST_NO"));
			}
			form2.setAccountGroupId(rs.getString("ACCOUNT_GROUP_NAME"));
			form2.setSapCodeNo(rs.getString("SAP_CODE_NO"));
			form2.setName(rs.getString("NAME"));
			form2.setCity(rs.getString("CITY"));
			String cretedOn=rs.getString("SAP_CREATION_DATE");
			   if(cretedOn!=null)
			   {
				   String a[]=cretedOn.split(" ");
				   cretedOn=a[0];
				   String b[]=cretedOn.split("-");
				   cretedOn=b[2]+"/"+b[1]+"/"+b[0];
				   form2.setSapCreationDate(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
			   }
			   String requestedOn=rs.getString("REQUEST_DATE");
			   if(requestedOn!=null)
			   {
				   String a[]=requestedOn.split(" ");
				   requestedOn=a[0];
				   String b[]=requestedOn.split("-");
				   requestedOn=b[2]+"/"+b[1]+"/"+b[0];
				   form2.setRequestDate(EMicroUtils.display1(rs.getDate("REQUEST_DATE")));
			   }
		
			 materialList.add(form2);
		}
		rs.close();
		ad.connClose();
		if(materialList.size()==0){
			request.setAttribute("noRecords", "noRecords");
			vendorMasterRequestForm.setMessage1("No Records Found");
			vendorMasterRequestForm.setMessage2("");
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
	
	
	return mapping.findForward("vendorMasterSearch");
	}
	
	public ActionForward getVendorRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		 VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		 EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		vendorMasterRequestForm.setName1("");
	   vendorMasterRequestForm.setCity1("");
	   vendorMasterRequestForm.setCountry1("");
		try{
			ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
			ArrayList countryList=new ArrayList();
			ArrayList countryLabelList=new ArrayList();
			
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
			}
			vendorMasterRequestForm.setCountryList(countryList);
			vendorMasterRequestForm.setCountryLabelList(countryLabelList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("vendorMasterSearch");

	}
	
	public ActionForward ViewVendorrecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String reqId=request.getParameter("ReqestNo");
		
		EssDao ad=EssDao.dBConnection();

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
					
					
					
					
						
		
			
		return mapping.findForward("vendorMasterView");
	}
	
	
	public ActionForward SubmitAllvendor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
			 VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
			 EssDao ad=EssDao.dBConnection();
				HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
		 
				String reqNo[]=vendorMasterRequestForm.getGetReqno();
				
				
				for(int j=0;j<reqNo.length;j++)
				{
					try{
						int i=0;
					
						String requestNo=reqNo[j];
						String matType="";
						String loctID="";
						
						 Date dNow = new Date( );
						 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
						String approvermail="";
						String pendingApprovers="";
						String accountGroupId="";
						   String accountGroup="select acc.ACCOUNT_GROUP_ID from vendor_master_m as v,ACCOUNT_GROUP_M as acc " +
						   		"where REQUEST_NO='"+requestNo+"' and v.ACCOUNT_GROUP_ID=acc.ACCOUNT_GROUP_ID";	
						   ResultSet accountGroupId1=ad.selectQuery(accountGroup);
						   while(accountGroupId1.next()){
							   accountGroupId=accountGroupId1.getString("ACCOUNT_GROUP_ID");
				    	 		
				    	 	}
						 
						
						int checkApprover=0;
						String vendorType="";
						String getMatGroup="select acc.ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M as acc where acc.ACCOUNT_GROUP_ID='"+accountGroupId+"'";
			    	 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
			    	 	while(rsMatGrup.next()){
			    	 		vendorType=rsMatGrup.getString("ACCOUNT_GROUP_NAME");
			    	 		
			    	 	}
					 	if(vendorType.equalsIgnoreCase("Import")){
					 		
					 	}else{
					 		vendorType="Local";
					 	}
						String getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='"+vendorType+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
			    		ResultSet rsAppr=ad.selectQuery(getApprovers);
			    		while(rsAppr.next())
			    		{
			    			checkApprover=1;
			    		}
			    		 if(checkApprover==1)
			 			{
			    			 

			 				try{


			 					
			 				 	String pApprover="";
			 				 	String parllelAppr1="";
			 				 	String parllelAppr2="";
			 		    		
			 		    		
			 				 	
			 				 	checkApprover=0;
			 				 	String pendingApprs="";
			 				 	
			 				 	
			 				 	getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='"+vendorType+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
			 		    		 rsAppr=ad.selectQuery(getApprovers);
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
			 		    			 getApprovers="select app.Approver_Idemp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
			 					saveRecReq = saveRecReq + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','Vendor Master')";
			 					int ij=ad.SqlExecuteUpdate(saveRecReq);
			 					if(!(parllelAppr1.equalsIgnoreCase(""))){
			 						
			 						String a[]=parllelAppr1.split(",");
			 						parllelAppr1=a[0];
			 					 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
			 					 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','Vendor Master')";
			 					 ad.SqlExecuteUpdate(sendRecParllelAppr1);
			 					}
			 					if(!(parllelAppr2.equalsIgnoreCase(""))){
			 						String a[]=parllelAppr2.split(",");
			 						parllelAppr2=a[0];
			 						 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
			 						 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','Vendor Master')";
			 						 ad.SqlExecuteUpdate(sendRecParllelAppr2);
			 					}
			 					
			 					if(ij>0){
			 						 vendorMasterRequestForm.setMessage("");
			 						vendorMasterRequestForm.setMessage2("Request No"+requestNo+". Submitted for approval successully.");
			 						String updateStatus="update vendor_master_m set Approve_Status='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+requestNo+"'";
			 						ad.SqlExecuteUpdate(updateStatus);
			 						
			 						EMailer email = new EMailer();
								 email.sendMailToApprover(request, approvermail,requestNo, "Vendor Master");
			 					}else{
			 						vendorMasterRequestForm.setMessage("Error while submiting approval...");
			 						 vendorMasterRequestForm.setMessage2("");
			 					}
			 		    		}else{
			 		    			
			 		    			vendorMasterRequestForm.setMessage("No Approvers are assigned.Please Contact to Admin");
			 		    			 vendorMasterRequestForm.setMessage2("");
			 		    		}
			 					
			 					
			 					
			 				
			 					
			 					
			 					
			 					
			 				}catch (Exception e) {
			 					e.printStackTrace();
			 				}
			 			
						
			 			}
						
						
						
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					
					
				}
					 
				
				
				
				
				
		
		 String module="ESS"; 
			request.setAttribute("MenuIcon", module);
			vendorMasterRequestForm.setApproveType("Created");
			searchRecord(mapping, vendorMasterRequestForm, request, response);
	return mapping.findForward("vendorMasterList");
		
	}

	
	public ActionForward saveAndSubmitMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		 EssDao ad=EssDao.dBConnection();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
		 try{
		
		 int requestNo=vendorMasterRequestForm.getRequestNo();
		 Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
	String dateNow = ft.format(dNow);
	 String accountGroupId=vendorMasterRequestForm.getAccountGroupId();
	  int totalcount=0;
	  if(!accountGroupId.equalsIgnoreCase("4"))
	  {
    String getVendorFileCount="select count(*) from temp_vendor_documents where request_no='"+requestNo+"' and userId='"+user.getEmployeeNo()+"'";
	 ResultSet rsVendorFile=ad.selectQuery(getVendorFileCount);

	 while(rsVendorFile.next())
	 {
		 totalcount=rsVendorFile.getInt(1);
	 }
	  }
	  else
	  {
		 totalcount=1;
	  }
		 if(totalcount>0)
		 {
		 FormFile vendorAttachmentFile=vendorMasterRequestForm.getVendorAttachments();
		 String attchmentName=vendorAttachmentFile.getFileName();
	 	
	 	 
	      accountGroupId=vendorMasterRequestForm.getAccountGroupId();
		 String requestDate=vendorMasterRequestForm.getRequestDate();
		 String b1[]=requestDate.split("/");
		 requestDate=b1[2]+"-"+b1[1]+"-"+b1[0];
		 String purchaseView=null;
		 String accountView=null;
		 purchaseView=request.getParameter("purchase");
		 
		 accountView=request.getParameter("accountView");
		 
		 
		
		 
		 if(purchaseView.equalsIgnoreCase("true") && accountView.equalsIgnoreCase("true")){
		 	 purchaseView="3";
		 }else{
			 if(purchaseView.equalsIgnoreCase("true")){
			 	 purchaseView="1";
			 }
			 if(accountView.equalsIgnoreCase("true")){
			 	 purchaseView="2";
			 }
		 }
		 
		 
		 String name=vendorMasterRequestForm.getName();
		 String address1=vendorMasterRequestForm.getAddress1();
		 String address2=vendorMasterRequestForm.getAddress2();
		 String address3=vendorMasterRequestForm.getAddress3();
		 String address4=vendorMasterRequestForm.getAddress4();
		 String city=vendorMasterRequestForm.getCity();
		 String pinCode=vendorMasterRequestForm.getPinCode();
		 String country=vendorMasterRequestForm.getCountry();
		 String state=vendorMasterRequestForm.getState();
		 String landLineNo=vendorMasterRequestForm.getLandLineNo();
		 String mobileNo=vendorMasterRequestForm.getMobileNo();
		 String faxNo=vendorMasterRequestForm.getFaxNo();
		 String emailId=vendorMasterRequestForm.getEmailId();
		 String currencyId=vendorMasterRequestForm.getCurrencyId();
		 String reConcillationActId=vendorMasterRequestForm.getReConcillationActId();
		 String elgTds=vendorMasterRequestForm.getElgTds();
		 String tdsCode=vendorMasterRequestForm.getTdsCode();
		 String lstNo=vendorMasterRequestForm.getLstNo();
		 String tinNo=vendorMasterRequestForm.getTinNo();
		 String cstNo=vendorMasterRequestForm.getCstNo();
		 String panNo=vendorMasterRequestForm.getPanNo();
		 String serviceTaxRegNo=vendorMasterRequestForm.getServiceTaxRegNo();
		 String regExciseVendor=vendorMasterRequestForm.getRegExciseVendor();
		 String eccNo=vendorMasterRequestForm.getEccNo();
		 String exciseRegNo=vendorMasterRequestForm.getExciseRegNo();
		 String exciseRange=vendorMasterRequestForm.getExciseRange();
		 String exciseDivision=vendorMasterRequestForm.getExciseDivision();
		 String paymentTermId=vendorMasterRequestForm.getPaymentTermId();
		 String accountClerkId=vendorMasterRequestForm.getAccountClerkId();
		 String isApprovedVendor=vendorMasterRequestForm.getIsApprovedVendor();
		 
		 String typeDetails=vendorMasterRequestForm.getTypeDetails();
		 
		
			int checkApprover=0;
			String vendorType="";
			String getMatGroup="select acc.ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M as acc where acc.ACCOUNT_GROUP_ID='"+accountGroupId+"'";
    	 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
    	 	while(rsMatGrup.next()){
    	 		vendorType=rsMatGrup.getString("ACCOUNT_GROUP_NAME");
    	 		
    	 	}
		 	if(vendorType.equalsIgnoreCase("Import")){
		 		
		 	}else{
		 		vendorType="Local";
		 	}
			String getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='"+vendorType+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
    		ResultSet rsAppr=ad.selectQuery(getApprovers);
    		while(rsAppr.next())
    		{
    			checkApprover=1;
    		}
		 
		 if(typeDetails.equalsIgnoreCase("Save"))
		 {
		 
			 int count=0;
				
			 String getCount="select count(*) from vendor_master_m where REQUEST_NO='"+requestNo+"'";
			 ResultSet rsCount=ad.selectQuery(getCount);
			 while(rsCount.next())
			 {
				count=rsCount.getInt(1);
			 }
			 int insert=0;
			 String approver="";
				String lApprover="";
				String approvermail="";
				String pendingApprovers="";
				
				/*String getApproverID="select * from Approvers_Details where Type like '%Vendor Master%'";
				
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
							pendingApprovers = pendingApprovers + "," +approverRS.getString("Employee_Name");
						}
						
				}*/
			 if(count>0){
				 //Request no is available
					 String getReqestNumber="select max(REQUEST_NO)  from vendor_master_m";
						int maxReqno=0;
						ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
						while(rsReqestNumber.next())
						{
							maxReqno=rsReqestNumber.getInt(1);
						}
						maxReqno+=1;
						
						vendorMasterRequestForm.setRequestNo(maxReqno);
						
									
				 
			 String sql="insert into vendor_master_m(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID" +
			 		",VIEW_TYPE,TITLE,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE"+
			 		",LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID"+
			 		",IS_ELIGIBLE_FOR_TDS,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No," +
			 		"IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division," +
			 		"PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status," +
			 		"REQUESTED_BY,GSTIN_Number)"+
			 		"values('"+vendorMasterRequestForm.getRequestNo()+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
			 		"'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"'," +
			 		"'"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
			 		"'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"'," +
			 		"'"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
			 		"'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"'," +
			 		"'"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','"+vendorMasterRequestForm.getGstinNo()+"')";
					 	
			 insert= ad.SqlExecuteUpdate(sql);
			 
			 String updateTempVendorDoc="update temp_vendor_documents set request_no='"+vendorMasterRequestForm.getRequestNo()+"' where request_no='"+requestNo+"'";
			 ad.SqlExecuteUpdate(updateTempVendorDoc);
			 
			 
			 
			 if(insert>0){
				 vendorMasterRequestForm.setMessage("");
				     vendorMasterRequestForm.setMessage2("Vendor Code creation request saved with request number='"+vendorMasterRequestForm.getRequestNo()+"'");
				     vendorMasterRequestForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				     
				     String deleteHistory="delete vendor_master_history where REQUEST_NO='"+requestNo+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
				     ad.SqlExecuteUpdate(deleteHistory);			

				     String saveInHistory="insert into vendor_master_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID,VIEW_TYPE,TITLE,NAME,ADDRESS1,"
				     + "ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID,IS_ELIGIBLE_FOR_TDS,"
				     + "TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division,PAYMENT_TERM_ID,"
				     + "ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,REQUESTED_BY,role,GSTIN_Number)"+
				     "values('"+requestNo+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
				     "'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"','"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
				     "'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"','"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
				     "'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"','"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"',"
				     + "'"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','user','"+vendorMasterRequestForm.getGstinNo()+"')";
				     ad.SqlExecuteUpdate(saveInHistory);
				     
				     display(mapping, form, request, response);
				     
				 }else{
					 vendorMasterRequestForm.setTypeDetails("Update");
				     
					  ArrayList listOfFiles=new ArrayList();
							String getUploadedFiles="select * from temp_vendor_documents where request_no='"+vendorMasterRequestForm.getRequestNo()+"' and  userId ='"+user.getEmployeeNo()+"' ";
							ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
							while(rsUploadFile.next())
							{
								VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
								vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
								vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
								vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
								listOfFiles.add(vendorMasterRequestForm1);
							}
							request.setAttribute("documentDetails", listOfFiles);
				 }
			 
			 }else{
				 
				 String  sql="insert into vendor_master_m(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID" +
			 		",VIEW_TYPE,TITLE,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE"+
			 		",LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID"+
			 		",IS_ELIGIBLE_FOR_TDS,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No," +
			 		"IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division," +
			 		"PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,last_approver,pending_approver,REQUESTED_BY,GSTIN_Number)"+
			 		"values('"+requestNo+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
			 		"'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"'," +
			 		"'"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
			 		"'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"'," +
			 		"'"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
			 		"'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"'," +
			 		"'"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','No','"+pendingApprovers+"','"+user.getEmployeeNo()+"','"+vendorMasterRequestForm.getGstinNo()+"')";
					 	
				 insert= ad.SqlExecuteUpdate(sql);
			 
			 
			
			 
			 if(insert>0){
				 vendorMasterRequestForm.setMessage("");
				     vendorMasterRequestForm.setMessage2("Vendor Code creation request saved with request number='"+requestNo+"'"); 
				     vendorMasterRequestForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
			
				     String deleteHistory="delete vendor_master_history where REQUEST_NO='"+requestNo+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
				     ad.SqlExecuteUpdate(deleteHistory);			

				     String saveInHistory="insert into vendor_master_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID,VIEW_TYPE,TITLE,NAME,ADDRESS1,"
				     + "ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID,IS_ELIGIBLE_FOR_TDS,"
				     + "TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division,PAYMENT_TERM_ID,"
				     + "ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,REQUESTED_BY,role,GSTIN_Number)"+
				     "values('"+requestNo+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
				     "'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"','"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
				     "'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"','"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
				     "'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"','"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"',"
				     + "'"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','user','"+vendorMasterRequestForm.getGstinNo()+"')";
				     ad.SqlExecuteUpdate(saveInHistory);
				     
				     display(mapping, form, request, response);
				 
			 }else{
				 
				 vendorMasterRequestForm.setTypeDetails("Update");
			     
				  ArrayList listOfFiles=new ArrayList();
						String getUploadedFiles="select * from temp_vendor_documents where request_no='"+vendorMasterRequestForm.getRequestNo()+"' and  userId ='"+user.getEmployeeNo()+"' ";
						ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
						while(rsUploadFile.next())
						{
							VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
							vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
							vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
							vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
							listOfFiles.add(vendorMasterRequestForm1);
						}
						request.setAttribute("documentDetails", listOfFiles);
			 }
			 
			 }
			/* //Send Mail To First Approver 
			 if(insert > 0){
				 String Req_Id = ""+vendorMasterRequestForm.getRequestNo();
				 EMailer email = new EMailer();
				 int i = email.sendMailToApprover(request, approvermail,Req_Id,"Vendor Master");
				 if(i > 0){
					 vendorMasterRequestForm.setMessage("Requested Submitted Successfully");
					}
			 }*/
			 
		 
		 }else{
			System.out.println("update vendor Master");
		String approvedStatus="Pending";
		
		String recordStatus="";
		String getRecordStatus="select Approve_Status from vendor_master_m where REQUEST_NO='"+requestNo+"' ";
		ResultSet rsRecord=ad.selectQuery(getRecordStatus);
		while(rsRecord.next())
		{
			recordStatus=rsRecord.getString("Approve_Status");
		}
		if(recordStatus.equalsIgnoreCase("Rejected"))
		{
			
			String deleteRecords="delete from All_Request where Req_Id='"+requestNo+"' and Req_Type='Vendor Master'";
			int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
			System.out.println("deleteStatus="+deleteStatus);
			
			String updateFlag="update vendor_master_m set rejected_flag='yes' where REQUEST_NO='"+requestNo+"'";
			ad.SqlExecuteUpdate(updateFlag);
			
		}
		
			
			
			String updateVendorMaster="update vendor_master_m  set REQUEST_DATE='"+requestDate+"',LOCATION_ID='"+vendorMasterRequestForm.getLocationId()+"',ACCOUNT_GROUP_ID='"+accountGroupId+"'" +
		 		",VIEW_TYPE='"+purchaseView+"',TITLE='"+vendorMasterRequestForm.getTitle()+"',NAME='"+name+"',ADDRESS1='"+address1+"',ADDRESS2='"+address2+"',ADDRESS3='"+address3+"',ADDRESS4='"+address4+"',CITY='"+city+"',PINCODE='"+pinCode+"',COUNTRY_ID='"+country+"',STATE='"+state+"'"+
		 		",LANDLINE_NO='"+landLineNo+"',MOBILE_NO='"+mobileNo+"',FAX_NO='"+faxNo+"',EMAIL_ID='"+emailId+"',CURRENCY_ID='"+currencyId+"',RECONCILATION_ACT_ID='"+reConcillationActId+"'"+
		 		",IS_ELIGIBLE_FOR_TDS='"+elgTds+"',TDS_CODE='"+tdsCode+"',LST_NO='"+lstNo+"',TIN_NO='"+tinNo+"',CST_NO='"+cstNo+"',PAN_No='"+panNo+"',Service_Tax_Registration_No='"+serviceTaxRegNo+"'," +
		 		"IS_REGD_EXCISE_VENDOR='"+regExciseVendor+"',ECC_No='"+eccNo+"',Excise_Reg_No='"+exciseRegNo+"',Excise_Range='"+exciseRange+"',Excise_Division='"+exciseDivision+"'," +
		 		"PAYMENT_TERM_ID='"+paymentTermId+"',ACCOUNT_CLERK_ID='"+accountClerkId+"',IS_APPROVED_VENDOR='"+isApprovedVendor+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',COMMISSIONERATE='"+vendorMasterRequestForm.getCommissionerate()+"',Type_Of_Vendor='"+vendorMasterRequestForm.getTypeOfVendor()+"',Approve_Status='Created',GSTIN_Number='"+vendorMasterRequestForm.getGstinNo()+"'  where REQUEST_NO='"+requestNo+"'";
			
			int status=0;
			status=ad.SqlExecuteUpdate(updateVendorMaster);
			if(status>0){
			     vendorMasterRequestForm.setMessage2("Vendor Code creation request updated Successfully");
			     vendorMasterRequestForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
			     vendorMasterRequestForm.setMessage("");
			     String deleteHistory="delete vendor_master_history where REQUEST_NO='"+requestNo+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
			     ad.SqlExecuteUpdate(deleteHistory);			

			     String saveInHistory="insert into vendor_master_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID,VIEW_TYPE,TITLE,NAME,ADDRESS1,"
			     + "ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID,IS_ELIGIBLE_FOR_TDS,"
			     + "TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division,PAYMENT_TERM_ID,"
			     + "ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,REQUESTED_BY,role,GSTIN_Number)"+
			     "values('"+requestNo+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
			     "'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"','"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
			     "'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"','"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
			     "'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"','"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"',"
			     + "'"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','user','"+vendorMasterRequestForm.getGstinNo()+"')";
			     ad.SqlExecuteUpdate(saveInHistory);
			   /*  ArrayList listOfFiles=new ArrayList();
				 String getUploadedFiles="select * from temp_vendor_documents where request_no='"+requestNo+"' and  userId ='"+user.getEmployeeNo()+"'";
					ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
					while(rsUploadFile.next())
					{
						VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
						vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
						vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
						vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
						listOfFiles.add(vendorMasterRequestForm1);
					}
					request.setAttribute("documentDetails", listOfFiles);*/
			     
			     display(mapping, form, request, response);
			    
			     
			}else{
				vendorMasterRequestForm.setMessage("Error..When Updating Vendor Details.");
				 vendorMasterRequestForm.setMessage2("");
				   ArrayList listOfFiles=new ArrayList();
					 String getUploadedFiles="select * from temp_vendor_documents where request_no='"+requestNo+"' and  userId ='"+user.getEmployeeNo()+"'";
						ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
						while(rsUploadFile.next())
						{
							VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
							vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
							vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
							vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
							listOfFiles.add(vendorMasterRequestForm1);
						}
						request.setAttribute("documentDetails", listOfFiles);
						
						 
			}
			
		 }
		 
		 if(checkApprover==1)
			{
				try{


					
				 	String pApprover="";
				 	String parllelAppr1="";
				 	String parllelAppr2="";
		    		
		    		
				 	
				 	checkApprover=0;
				 	String pendingApprs="";
				 	
				 	
				 	getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='"+vendorType+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
		    		 rsAppr=ad.selectQuery(getApprovers);
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
		    			 getApprovers="select emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		    			
		    			 Date dNow1 = new Date( );
						 SimpleDateFormat ft1 = new SimpleDateFormat ("dd/MM/yyyy");
					String dateNow1 = ft1.format(dNow1);
		    		
		    		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					saveRecReq = saveRecReq + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','Vendor Master')";
					int ij=ad.SqlExecuteUpdate(saveRecReq);
					
		String deleteHistory="delete vendor_master_history where REQUEST_NO='"+requestNo+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
		ad.SqlExecuteUpdate(deleteHistory);			
	
		 String saveInHistory="insert into vendor_master_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID,VIEW_TYPE,TITLE,NAME,ADDRESS1,"
		 + "ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID,IS_ELIGIBLE_FOR_TDS,"
		 + "TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division,PAYMENT_TERM_ID,"
		 + "ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,REQUESTED_BY,role,GSTIN_Number)"+
 		"values('"+requestNo+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
 		"'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"','"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
 		"'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"','"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
 		"'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"','"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"',"
 		+ "'"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','user','"+vendorMasterRequestForm.getGstinNo()+"')";
		 	ad.SqlExecuteUpdate(saveInHistory);
					
					if(!(parllelAppr1.equalsIgnoreCase(""))){
						
						String a[]=parllelAppr1.split(",");
						parllelAppr1=a[0];
					 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','Vendor Master')";
					 ad.SqlExecuteUpdate(sendRecParllelAppr1);
					}
					if(!(parllelAppr2.equalsIgnoreCase(""))){
						String a[]=parllelAppr2.split(",");
						parllelAppr2=a[0];
						 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
						 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','Vendor Master')";
						 ad.SqlExecuteUpdate(sendRecParllelAppr2);
					}
					
					if(ij>0){
						 vendorMasterRequestForm.setMessage("");
						vendorMasterRequestForm.setMessage2("Request No"+requestNo+". Submitted for approval successully.");
						String updateStatus="update vendor_master_m set Approve_Status='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+requestNo+"'";
						ad.SqlExecuteUpdate(updateStatus);
						vendorMasterRequestForm.setAppStatusMessage("");
						EMailer email = new EMailer();
						String approvermail="";
						String reqNo=Integer.toString(requestNo);
						int		i = email.sendMailToApprover(request, approvermail,reqNo, "Vendor Master");
						
					}else{
						vendorMasterRequestForm.setMessage("Error while submiting approval...");
						 vendorMasterRequestForm.setMessage2("");
					}
		    		}else{
		    			
		    			vendorMasterRequestForm.setMessage("No Approvers are assigned.Please Contact to Admin");
		    			 vendorMasterRequestForm.setMessage2("");
		    		}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		 
		 
		 
		 
		 
		 
	}
		 
		 else{
			 vendorMasterRequestForm.setMessage("Error..Please Upload File");
			 vendorMasterRequestForm.setMessage2("");
			 String typeDetails=vendorMasterRequestForm.getTypeDetails();
				vendorMasterRequestForm.setTypeDetails(typeDetails);
			 ArrayList accountGroupList=new ArrayList();
				ArrayList accountGroupLabelList=new ArrayList();
			 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		while(rs.next()) {
			accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
			accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
		}
		vendorMasterRequestForm.setAccountGroupList(accountGroupList);
		vendorMasterRequestForm.setAccountGroupLabelList(accountGroupLabelList);
			 	 
			 	 
		ResultSet rs9 = ad.selectQuery("select * from Country");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LAND1")+" - "+rs9.getString("LANDX"));
		}
		vendorMasterRequestForm.setCountryList(countryList);
		vendorMasterRequestForm.setCountryLabelList(countryLabelList);
		ResultSet rsState = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+vendorMasterRequestForm.getCountry()+"'");
		ArrayList stateList=new ArrayList();
		ArrayList stateLabelList=new ArrayList();
		
		while(rsState.next()) {
			stateList.add(rsState.getString("BLAND"));
			stateLabelList.add(rsState.getString("BEZEI"));
		}
		vendorMasterRequestForm.setStateList(stateList);
		vendorMasterRequestForm.setStateLabelList(stateLabelList);
		
		
		request.setAttribute("States", "States");
		
		ResultSet rs5 = ad.selectQuery("select WAERS,ISOCD from Currency order by ISOCD");
		ArrayList currencyList=new ArrayList();
		ArrayList currencyLabelList=new ArrayList();
	
		while(rs5.next()) {
			currencyList.add(rs5.getString("WAERS"));
			currencyLabelList.add(rs5.getString("WAERS")+"-"+rs5.getString("ISOCD"));
		}
		vendorMasterRequestForm.setCurrencyList(currencyList);
		vendorMasterRequestForm.setCurrencyLabelList(currencyLabelList);
		
		
		ResultSet rs6 = ad.selectQuery("select RECONCILIATION_ACCOUNT_ID," +
				"RECONCILIATION_ACCOUNT_NAME from RECONCILIATION_ACCOUNT_M where ACTIVE='True' order by RECONCILIATION_ACCOUNT_NAME");
		ArrayList reConcillationList=new ArrayList();
		ArrayList reConcillationLabelList=new ArrayList();
		
		
		while(rs6.next()) {
			reConcillationList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID"));
			reConcillationLabelList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID")+"-"+rs6.getString("RECONCILIATION_ACCOUNT_NAME"));
		}
		vendorMasterRequestForm.setReConcillationList(reConcillationList);
		vendorMasterRequestForm.setReConcillationLabelList(reConcillationLabelList);
		
		
		ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
		"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True'");
		ArrayList paymentTermList=new ArrayList();
		ArrayList paymentTermLabelList=new ArrayList();
		
		while(rs7.next()) {
			paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
			paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_NAME"));
		}
		vendorMasterRequestForm.setPaymentTermList(paymentTermList);
		vendorMasterRequestForm.setPaymentTermLabelList(paymentTermLabelList);
		
		
		ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
		"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
		ArrayList accountClerkList=new ArrayList();
		ArrayList accountClerkLabelList=new ArrayList();
		
		while(rs8.next()) {
			accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
			accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
		}
		vendorMasterRequestForm.setAccountClerkList(accountClerkList);
		vendorMasterRequestForm.setAccountClerkLabelList(accountClerkLabelList);
		
		
		ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		while(rs11.next()) {
			locationList.add(rs11.getString("LOCID"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		vendorMasterRequestForm.setLocationIdList(locationList);
		vendorMasterRequestForm.setLocationLabelList(locationLabelList);
		
		
		
		ResultSet rs4 = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
		ArrayList tdsIdList=new ArrayList();
		ArrayList tdsLabelList=new ArrayList();
		
		while(rs4.next()) {
			tdsIdList.add(rs4.getString("TDS_SECTION_ID"));
			tdsLabelList.add(rs4.getString("TDS_SECTION_DESC"));
		}
		vendorMasterRequestForm.setTdsIdList(tdsIdList);
		vendorMasterRequestForm.setTdsLabelList(tdsLabelList);
			 
		 }
		 }catch (Exception e) {
			e.printStackTrace();
		}
		// displayVendorMasterList(mapping, form, request, response);
		 String module="ESS"; 
			request.setAttribute("MenuIcon", module);
	return mapping.findForward("display");
		
		
	}
	
	public ActionForward copyVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		try{
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	vendorMasterRequestForm.setAccountGroupList(accountGroupList);
	vendorMasterRequestForm.setAccountGroupLabelList(accountGroupLabelList);
		 	 
		 	 
	ResultSet rs9 = ad.selectQuery("select * from Country");
	ArrayList countryList=new ArrayList();
	ArrayList countryLabelList=new ArrayList();
	
	while(rs9.next()) {
		countryList.add(rs9.getString("LAND1"));
		countryLabelList.add(rs9.getString("LAND1")+" - "+rs9.getString("LANDX"));
	}
	vendorMasterRequestForm.setCountryList(countryList);
	vendorMasterRequestForm.setCountryLabelList(countryLabelList);
	
	
	ResultSet rs5 = ad.selectQuery("select WAERS,ISOCD from Currency");
	ArrayList currencyList=new ArrayList();
	ArrayList currencyLabelList=new ArrayList();

	while(rs5.next()) {
		currencyList.add(rs5.getString("WAERS"));
		currencyLabelList.add(rs5.getString("ISOCD"));
	}
	vendorMasterRequestForm.setCurrencyList(currencyList);
	vendorMasterRequestForm.setCurrencyLabelList(currencyLabelList);
	
	
	ResultSet rs6 = ad.selectQuery("select RECONCILIATION_ACCOUNT_ID," +
			"RECONCILIATION_ACCOUNT_NAME from RECONCILIATION_ACCOUNT_M where ACTIVE='True' order by RECONCILIATION_ACCOUNT_ID");
	ArrayList reConcillationList=new ArrayList();
	ArrayList reConcillationLabelList=new ArrayList();
	
	
	while(rs6.next()) {
		reConcillationList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID"));
		reConcillationLabelList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID")+"-"+rs6.getString("RECONCILIATION_ACCOUNT_NAME"));
	}
	vendorMasterRequestForm.setReConcillationList(reConcillationList);
	vendorMasterRequestForm.setReConcillationLabelList(reConcillationLabelList);
	
	
	ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
	"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
	ArrayList paymentTermList=new ArrayList();
	ArrayList paymentTermLabelList=new ArrayList();
	
	while(rs7.next()) {
		paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
		paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
	}
	vendorMasterRequestForm.setPaymentTermList(paymentTermList);
	vendorMasterRequestForm.setPaymentTermLabelList(paymentTermLabelList);
	
	
	ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
	"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
	ArrayList accountClerkList=new ArrayList();
	ArrayList accountClerkLabelList=new ArrayList();
	
	while(rs8.next()) {
		accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
		accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
	}
	vendorMasterRequestForm.setAccountClerkList(accountClerkList);
	vendorMasterRequestForm.setAccountClerkLabelList(accountClerkLabelList);
	
	
	ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME from location");
	ArrayList locationList=new ArrayList();
	ArrayList locationLabelList=new ArrayList();
	
	while(rs11.next()) {
		locationList.add(rs11.getString("LOCID"));
		locationLabelList.add(rs11.getString("LOCID")+"-"+rs11.getString("LOCNAME"));
	}
	vendorMasterRequestForm.setLocationIdList(locationList);
	vendorMasterRequestForm.setLocationLabelList(locationLabelList);
	
	
	
	ResultSet rsTds = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
	ArrayList tdsIdList=new ArrayList();
	ArrayList tdsLabelList=new ArrayList();
	
	while(rsTds.next()) {
		tdsIdList.add(rsTds.getString("TDS_SECTION_ID"));
		tdsLabelList.add(rsTds.getString("TDS_SECTION_DESC"));
	}
	vendorMasterRequestForm.setTdsIdList(tdsIdList);
	vendorMasterRequestForm.setTdsLabelList(tdsLabelList);
	
	ArrayList venodreCode=new ArrayList();
	ArrayList vendorType=new ArrayList();
	ResultSet rsVendorType = ad.selectQuery("select V_CODE,V_LTXT from VENDOR_TYPE where active='True' ");
	while(rsVendorType.next()) {
		venodreCode.add(rsVendorType.getString("V_CODE"));
		vendorType.add(rsVendorType.getString("V_LTXT"));
	}
	
	vendorMasterRequestForm.setRequestDate(EMicroUtils.getCurrentSysDate());
	
			String country="";
		int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
		String getVendorDetails="select * from vendor_master_m where REQUEST_NO='"+requstNo+"'";
		ResultSet rs=ad.selectQuery(getVendorDetails);
		while(rs.next())
		{
			
			String getReqestNumber="select max(REQUEST_NO)  from vendor_master_m";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			vendorMasterRequestForm.setRequestNo(maxReqno);
			vendorMasterRequestForm.setRequestDate(EMicroUtils.getCurrentSysDate());
			vendorMasterRequestForm.setLocationId(rs.getString("LOCATION_ID"));
		    String accountGroupID=rs.getString("ACCOUNT_GROUP_ID");
		    accountGroupID=accountGroupID.replace(" ","");
			vendorMasterRequestForm.setAccountGroupId(accountGroupID);
		     
			
			 
			 String viewType=rs.getString("VIEW_TYPE");
			 if(viewType.equalsIgnoreCase("1"))
			 {
			vendorMasterRequestForm.setPurchaseView("On");

			 vendorMasterRequestForm.setAccountView("Off");
			 }
			 if(viewType.equalsIgnoreCase("2")){
			 vendorMasterRequestForm.setAccountView("On");
			 vendorMasterRequestForm.setPurchaseView("Off");
			 }
			 if(viewType.equalsIgnoreCase("3")){
				 vendorMasterRequestForm.setPurchaseView("On");
				 vendorMasterRequestForm.setAccountView("On");
				 }
			 vendorMasterRequestForm.setTitle(rs.getString("TITLE"));
			 vendorMasterRequestForm.setName(rs.getString("NAME"));
			 vendorMasterRequestForm.setAddress1(rs.getString("ADDRESS1"));
			vendorMasterRequestForm.setAddress2(rs.getString("ADDRESS2"));
			 vendorMasterRequestForm.setAddress3(rs.getString("ADDRESS3"));
			 vendorMasterRequestForm.setAddress4(rs.getString("ADDRESS4"));
			vendorMasterRequestForm.setCity(rs.getString("CITY"));
			 vendorMasterRequestForm.setPinCode(rs.getString("PINCODE"));
			 vendorMasterRequestForm.setCountry(rs.getString("COUNTRY_ID"));
			 country=rs.getString("COUNTRY_ID");
			 vendorMasterRequestForm.setState(rs.getString("STATE"));
			 request.setAttribute("States", "States");
			vendorMasterRequestForm.setLandLineNo(rs.getString("LANDLINE_NO"));
			 vendorMasterRequestForm.setMobileNo(rs.getString("MOBILE_NO"));
			 vendorMasterRequestForm.setFaxNo(rs.getString("FAX_NO"));
			 vendorMasterRequestForm.setEmailId(rs.getString("EMAIL_ID"));
			 vendorMasterRequestForm.setCurrencyId(rs.getString("CURRENCY_ID"));
			 vendorMasterRequestForm.setReConcillationActId(rs.getString("RECONCILATION_ACT_ID"));
			String elgTds=rs.getString("IS_ELIGIBLE_FOR_TDS");
			if(elgTds.equalsIgnoreCase("1"))
			{
				vendorMasterRequestForm.setElgTds("True");
			}
			if(elgTds.equalsIgnoreCase("0"))
			{
				vendorMasterRequestForm.setElgTds("False");
			}
			 vendorMasterRequestForm.setTdsCode(rs.getString("TDS_CODE"));
			 vendorMasterRequestForm.setLstNo(rs.getString("LST_NO"));
			 vendorMasterRequestForm.setTinNo(rs.getString("TIN_NO"));
			vendorMasterRequestForm.setCstNo(rs.getString("CST_NO"));
			 vendorMasterRequestForm.setPanNo(rs.getString("PAN_No"));
			 vendorMasterRequestForm.setServiceTaxRegNo(rs.getString("Service_Tax_Registration_No"));
			 String isRegistVendor=rs.getString("IS_REGD_EXCISE_VENDOR");
			 	if(isRegistVendor.equalsIgnoreCase("1"))
				{
				 vendorMasterRequestForm.setRegExciseVendor("True");
				 request.setAttribute("RegExciseVendor", "RegExciseVendor");
				}
				if(isRegistVendor.equalsIgnoreCase("0"))
				{
					
					
					 vendorMasterRequestForm.setRegExciseVendor("False");
				}
			 
			 vendorMasterRequestForm.setEccNo(rs.getString("ECC_No"));
			 vendorMasterRequestForm.setExciseRegNo(rs.getString("Excise_Reg_No"));
			 vendorMasterRequestForm.setExciseRange(rs.getString("Excise_Range"));
			 vendorMasterRequestForm.setCommissionerate(rs.getString("COMMISSIONERATE"));
			 vendorMasterRequestForm.setExciseDivision(rs.getString("Excise_Division"));
			 vendorMasterRequestForm.setPaymentTermId(rs.getString("PAYMENT_TERM_ID"));
			 vendorMasterRequestForm.setAccountClerkId(rs.getString("ACCOUNT_CLERK_ID"));
			 vendorMasterRequestForm.setIsApprovedVendor(rs.getString("IS_APPROVED_VENDOR"));
			 String isApproveVendor=rs.getString("IS_APPROVED_VENDOR");
			if(isApproveVendor.equalsIgnoreCase("1"))
			{
				vendorMasterRequestForm.setIsApprovedVendor("True");
			}	if(isApproveVendor.equalsIgnoreCase("0")){
				vendorMasterRequestForm.setIsApprovedVendor("False");
			}
			 vendorMasterRequestForm.setApproveType(rs.getString("Approve_Status"));
			 vendorMasterRequestForm.setTypeOfVendor(rs.getString("Type_Of_Vendor"));
		}
		
		ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+vendorMasterRequestForm.getCountry()+"'");
		ArrayList stateList=new ArrayList();
		ArrayList stateLabelList=new ArrayList();
		
		while(rs4.next()) {
			stateList.add(rs4.getString("BLAND"));
			stateLabelList.add(rs4.getString("BEZEI"));
		}
		vendorMasterRequestForm.setStateList(stateList);
		vendorMasterRequestForm.setStateLabelList(stateLabelList);
		request.setAttribute("States", "States");
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user.getId()==1)
		{
			request.setAttribute("approved", "approved");
		}
		vendorMasterRequestForm.setTypeDetails("Update");
		
		String forwardType="display";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="vendorMasterSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="display";
		}
		vendorMasterRequestForm.setTypeDetails("Save");
		return mapping.findForward(forwardType);
	}
	
	public ActionForward getRequiredSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String requiredSearch=vendorMasterRequestForm.getSearchRequired();
			requiredSearch=requiredSearch.trim();
			LinkedList vendorList=new LinkedList();
		String getVendorMasterList="Select v.REQUEST_DATE,v.REQUEST_NO,v.NAME,v.CITY,v.Approve_Status,emp.emp_name,emp.dept_name,loc.LOCATION_CODE " +
				"from  vendor_master_m as v,emp_master as emp,Location as loc where (v.Approve_Status like '%"+requiredSearch+"%' or v.REQUEST_NO like '%"+requiredSearch+"%' " +
				"or  v.NAME like '%"+requiredSearch+"%' or v.CITY like '%"+requiredSearch+"%' or v.Approve_Status like '%"+requiredSearch+"%' or  emp.emp_name like '%"+requiredSearch+"%' " +
				"or loc.LOCATION_CODE like '%"+requiredSearch+"%' )  and v.CREATED_BY='"+user.getEmployeeNo()+"' and v.CREATED_BY=emp.emp_id and emp.plant_id=loc.LOCID";
		
		ResultSet rs=ad.selectQuery(getVendorMasterList);
		while(rs.next()){
			VendorMasterRequestForm venderform=new VendorMasterRequestForm();
			
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" "); 
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[1]+"/"+a[2]+"/"+a[0];
			venderform.setRequestDate(requestDate);
			venderform.setRequestNo(rs.getInt("REQUEST_NO"));
			venderform.setName(rs.getString("NAME"));
			venderform.setCity(rs.getString("CITY"));
			venderform.setRequestedBy(rs.getString("emp_name"));
			venderform.setDepartment(rs.getString("dept_name"));
			venderform.setLocationId(rs.getString("LOCATION_CODE"));
			venderform.setApproveType(rs.getString("Approve_Status"));
			
			vendorList.add(venderform);
		}
		request.setAttribute("vendorMasterList", vendorList);
		
		if(vendorList.size()==0){
			request.setAttribute("noRecords", "noRecords");
			vendorMasterRequestForm.setMessage1("No Records Found");
			 vendorMasterRequestForm.setMessage2("");
			 vendorMasterRequestForm.setMessage("");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("vendorMasterList");
	}
	
	
	public ActionForward sendMailToApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		int requestNo=Integer.parseInt(request.getParameter("ReqNo"));
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{

			Date dNow = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			String dateNow = ft.format(dNow);
		 	String pApprover="";
		 	String parllelAppr1="";
		 	String parllelAppr2="";
    		String vendorType="";
    		
    		String getMatGroup="select acc.ACCOUNT_GROUP_NAME from vendor_master_m as v,ACCOUNT_GROUP_M as acc where v.REQUEST_NO='"+requestNo+"' and  v.ACCOUNT_GROUP_ID=acc.ACCOUNT_GROUP_ID ";
    	 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
    	 	while(rsMatGrup.next()){
    	 		vendorType=rsMatGrup.getString("ACCOUNT_GROUP_NAME");
    	 		
    	 	}
		 	if(vendorType.equalsIgnoreCase("Import")){
		 		
		 	}else{
		 		vendorType="Local";
		 	}
		 	
		 	int checkApprover=0;
		 	String pendingApprs="";
		 	
		 	
		 	String getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='"+vendorType+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
    			 getApprovers="select emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Vendor Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
			saveRecReq = saveRecReq + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','Vendor Master')";
			int ij=ad.SqlExecuteUpdate(saveRecReq);
			if(!(parllelAppr1.equalsIgnoreCase(""))){
				
				String a[]=parllelAppr1.split(",");
				parllelAppr1=a[0];
			 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
			 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','Vendor Master')";
			 ad.SqlExecuteUpdate(sendRecParllelAppr1);
			}
			if(!(parllelAppr2.equalsIgnoreCase(""))){
				String a[]=parllelAppr2.split(",");
				parllelAppr2=a[0];
				 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Vendor Master','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','Vendor Master')";
				 ad.SqlExecuteUpdate(sendRecParllelAppr2);
			}
			
			if(ij>0){
				vendorMasterRequestForm.setMessage2("Request No"+requestNo+". Submitted for approval successully.");
				 vendorMasterRequestForm.setMessage("");
				String updateStatus="update vendor_master_m set Approve_Status='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+requestNo+"'";
				ad.SqlExecuteUpdate(updateStatus);
				
				EMailer email = new EMailer();
				String approvermail="";
				String reqNo=Integer.toString(vendorMasterRequestForm.getRequestNo());
				int		i = email.sendMailToApprover(request, approvermail,reqNo, "Vendor Master");
				
			}else{
				vendorMasterRequestForm.setMessage("Error while submiting approval...");
				 vendorMasterRequestForm.setMessage2("");
			}
    		}else{
    			
    			vendorMasterRequestForm.setMessage("No approvers are assigned for submiting this request.");
    			 vendorMasterRequestForm.setMessage2("");
    		}
			
			
			
		
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		vendorMasterRequestForm.setApproveType("Created");
		searchRecord(mapping, vendorMasterRequestForm, request, response);
		return mapping.findForward("vendorMasterList");
	}

	
	public ActionForward saveSAPCODEData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		 
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	vendorMasterRequestForm.setAccountGroupList(accountGroupList);
	vendorMasterRequestForm.setAccountGroupLabelList(accountGroupLabelList);
		 	 
		 	 
	ResultSet rs9 = ad.selectQuery("select LAND1,LANDX from Country");
	ArrayList countryList=new ArrayList();
	ArrayList countryLabelList=new ArrayList();
	
	while(rs9.next()) {
		countryList.add(rs9.getString("LAND1"));
		countryLabelList.add(rs9.getString("LAND1")+" - "+rs9.getString("LANDX"));
	}
	vendorMasterRequestForm.setCountryList(countryList);
	vendorMasterRequestForm.setCountryLabelList(countryLabelList);
	ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+vendorMasterRequestForm.getCountry()+"'");
	ArrayList stateList=new ArrayList();
	ArrayList stateLabelList=new ArrayList();
	
	while(rs4.next()) {
		stateList.add(rs4.getString("BLAND"));
		stateLabelList.add(rs4.getString("BEZEI"));
	}
	vendorMasterRequestForm.setStateList(stateList);
	vendorMasterRequestForm.setStateLabelList(stateLabelList);
	request.setAttribute("States", "States");
	
	ResultSet rs5 = ad.selectQuery("select WAERS,ISOCE from Currency");
	ArrayList currencyList=new ArrayList();
	ArrayList currencyLabelList=new ArrayList();

	while(rs5.next()) {
		currencyList.add(rs5.getString("WAERS"));
		currencyLabelList.add(rs5.getString("ISOCD"));
	}
	vendorMasterRequestForm.setCurrencyList(currencyList);
	vendorMasterRequestForm.setCurrencyLabelList(currencyLabelList);
	
	
	ResultSet rs6 = ad.selectQuery("select RECONCILIATION_ACCOUNT_ID," +
			"RECONCILIATION_ACCOUNT_NAME from RECONCILIATION_ACCOUNT_M where ACTIVE='True' order by RECONCILIATION_ACCOUNT_ID");
	ArrayList reConcillationList=new ArrayList();
	ArrayList reConcillationLabelList=new ArrayList();
	
	
	while(rs6.next()) {
		reConcillationList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID"));
		reConcillationLabelList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID")+"-"+rs6.getString("RECONCILIATION_ACCOUNT_NAME"));
	}
	vendorMasterRequestForm.setReConcillationList(reConcillationList);
	vendorMasterRequestForm.setReConcillationLabelList(reConcillationLabelList);
	
	
	ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
	"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
	ArrayList paymentTermList=new ArrayList();
	ArrayList paymentTermLabelList=new ArrayList();
	
	while(rs7.next()) {
		paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
		paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
	}
	vendorMasterRequestForm.setPaymentTermList(paymentTermList);
	vendorMasterRequestForm.setPaymentTermLabelList(paymentTermLabelList);
	
	
	ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
	"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
	ArrayList accountClerkList=new ArrayList();
	ArrayList accountClerkLabelList=new ArrayList();
	
	while(rs8.next()) {
		accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
		accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
	}
	vendorMasterRequestForm.setAccountClerkList(accountClerkList);
	vendorMasterRequestForm.setAccountClerkLabelList(accountClerkLabelList);
	
	
	ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME from location");
	ArrayList locationList=new ArrayList();
	ArrayList locationLabelList=new ArrayList();
	
	while(rs11.next()) {
		locationList.add(rs11.getString("LOCID"));
		locationLabelList.add(rs11.getString("LOCID")+"-"+rs11.getString("LOCNAME"));
	}
	vendorMasterRequestForm.setLocationIdList(locationList);
	vendorMasterRequestForm.setLocationLabelList(locationLabelList);
	
	
	
	ResultSet rsTds = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
	ArrayList tdsIdList=new ArrayList();
	ArrayList tdsLabelList=new ArrayList();
	
	while(rsTds.next()) {
		tdsIdList.add(rsTds.getString("TDS_SECTION_ID"));
		tdsLabelList.add(rsTds.getString("TDS_SECTION_DESC"));
	}
	vendorMasterRequestForm.setTdsIdList(tdsIdList);
	vendorMasterRequestForm.setTdsLabelList(tdsLabelList);
	
	
	vendorMasterRequestForm.setRequestDate(EMicroUtils.getCurrentSysDate());
	
			
		int requstNo=vendorMasterRequestForm.getRequestNo();
		String getVendorDetails="select * from vendor_master_m where REQUEST_NO='"+requstNo+"'";
		ResultSet rs=ad.selectQuery(getVendorDetails);
		while(rs.next())
		{
			
			vendorMasterRequestForm.setRequestNo(rs.getInt("REQUEST_NO"));
			vendorMasterRequestForm.setLocationId(rs.getString("LOCATION_ID"));
		    String accountGroupID=rs.getString("ACCOUNT_GROUP_ID");
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
			vendorMasterRequestForm.setPurchaseView("On");

			 vendorMasterRequestForm.setAccountView("Off");
			 }
			 if(viewType.equalsIgnoreCase("2")){
			 vendorMasterRequestForm.setAccountView("On");
			 vendorMasterRequestForm.setPurchaseView("Off");
			 }
			 if(viewType.equalsIgnoreCase("3")){
				 vendorMasterRequestForm.setPurchaseView("On");
				 vendorMasterRequestForm.setAccountView("On");
				 }
			 vendorMasterRequestForm.setTitle(rs.getString("TITLE"));
			 vendorMasterRequestForm.setName(rs.getString("NAME"));
			 vendorMasterRequestForm.setAddress1(rs.getString("ADDRESS1"));
			vendorMasterRequestForm.setAddress2(rs.getString("ADDRESS2"));
			 vendorMasterRequestForm.setAddress3(rs.getString("ADDRESS3"));
			 vendorMasterRequestForm.setAddress4(rs.getString("ADDRESS4"));
			vendorMasterRequestForm.setCity(rs.getString("CITY"));
			 vendorMasterRequestForm.setPinCode(rs.getString("PINCODE"));
			 vendorMasterRequestForm.setCountry(rs.getString("COUNTRY_ID"));
			 vendorMasterRequestForm.setState(rs.getString("STATE"));
			 request.setAttribute("States", "States");
			vendorMasterRequestForm.setLandLineNo(rs.getString("LANDLINE_NO"));
			 vendorMasterRequestForm.setMobileNo(rs.getString("MOBILE_NO"));
			 vendorMasterRequestForm.setFaxNo(rs.getString("FAX_NO"));
			 vendorMasterRequestForm.setEmailId(rs.getString("EMAIL_ID"));
			 vendorMasterRequestForm.setCurrencyId(rs.getString("CURRENCY_ID"));
			 vendorMasterRequestForm.setReConcillationActId(rs.getString("RECONCILATION_ACT_ID"));
			String elgTds=rs.getString("IS_ELIGIBLE_FOR_TDS");
			if(elgTds.equalsIgnoreCase("1"))
			{
				vendorMasterRequestForm.setElgTds("True");
			}
			if(elgTds.equalsIgnoreCase("0"))
			{
				vendorMasterRequestForm.setElgTds("False");
			}
			 vendorMasterRequestForm.setTdsCode(rs.getString("TDS_CODE"));
			 vendorMasterRequestForm.setLstNo(rs.getString("LST_NO"));
			 vendorMasterRequestForm.setTinNo(rs.getString("TIN_NO"));
			vendorMasterRequestForm.setCstNo(rs.getString("CST_NO"));
			 vendorMasterRequestForm.setPanNo(rs.getString("PAN_No"));
			 vendorMasterRequestForm.setServiceTaxRegNo(rs.getString("Service_Tax_Registration_No"));
			 String isRegistVendor=rs.getString("IS_REGD_EXCISE_VENDOR");
			 	if(isRegistVendor.equalsIgnoreCase("1"))
				{
				 vendorMasterRequestForm.setRegExciseVendor("True");
				 request.setAttribute("RegExciseVendor", "RegExciseVendor");
				}
				if(isRegistVendor.equalsIgnoreCase("0"))
				{
					
					
					 vendorMasterRequestForm.setRegExciseVendor("False");
				}
			 
			 vendorMasterRequestForm.setEccNo(rs.getString("ECC_No"));
			 vendorMasterRequestForm.setExciseRegNo(rs.getString("Excise_Reg_No"));
			 vendorMasterRequestForm.setExciseRange(rs.getString("Excise_Range"));
			 vendorMasterRequestForm.setCommissionerate(rs.getString("COMMISSIONERATE"));
			 vendorMasterRequestForm.setExciseDivision(rs.getString("Excise_Division"));
			 vendorMasterRequestForm.setPaymentTermId(rs.getString("PAYMENT_TERM_ID"));
			 vendorMasterRequestForm.setAccountClerkId(rs.getString("ACCOUNT_CLERK_ID"));
			 vendorMasterRequestForm.setIsApprovedVendor(rs.getString("IS_APPROVED_VENDOR"));
			 //vendorMasterRequestForm.setApproveType(rs.getString("Approve_Status"));
		}
		
		
		
		ArrayList listOfFiles=new ArrayList();
		String getUploadedFiles="select * from vendor_documents where request_no='"+requstNo+"'";
		ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
		while(rsUploadFile.next())
		{
			VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
			vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
			vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
			listOfFiles.add(vendorMasterRequestForm1);
		}
		request.setAttribute("documentDetails", listOfFiles);
		
		
		
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		
		int  userId=Integer.parseInt(user.getEmployeeNo());
		int i=0;
		if(userId==2)
		
		{
			String sapCreationDate=vendorMasterRequestForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
			String updateMaterial="update vendor_master_m set SAP_CODE_NO='"+vendorMasterRequestForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+vendorMasterRequestForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+vendorMasterRequestForm.getSapCreatedBy()+"' where REQUEST_NO='"+vendorMasterRequestForm.getRequestNo()+"'";
			i=ad.SqlExecuteUpdate(updateMaterial);
			if(i>0)
			{
				vendorMasterRequestForm.setMessage2("Code creation request updated with request number='"+vendorMasterRequestForm.getRequestNo()+"'");
				vendorMasterRequestForm.setTypeDetails("Update");
				 vendorMasterRequestForm.setMessage("");
			}else{
				vendorMasterRequestForm.setMessage("Error...When updating code creation reequest.Please Check");
				vendorMasterRequestForm.setTypeDetails("Update");
				 vendorMasterRequestForm.setMessage2("");
			} 
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(user.getId()==1)
		{
			request.setAttribute("approved", "approved");
		}
		vendorMasterRequestForm.setTypeDetails("Update");
		
		String forwardType="display";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="vendorMasterSAP";
		
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="display";
		}
		
		return mapping.findForward(forwardType);
	
	}
	
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	vendorMasterRequestForm.setAccountGroupList(accountGroupList);
	vendorMasterRequestForm.setAccountGroupLabelList(accountGroupLabelList);
		 	 
		 	 
	ResultSet rs9 = ad.selectQuery("select LAND1,LANDX from Country");
	ArrayList countryList=new ArrayList();
	ArrayList countryLabelList=new ArrayList();
	
	while(rs9.next()) {
		countryList.add(rs9.getString("LAND1"));
		countryLabelList.add(rs9.getString("LAND1")+" - "+rs9.getString("LANDX"));
	}
	vendorMasterRequestForm.setCountryList(countryList);
	vendorMasterRequestForm.setCountryLabelList(countryLabelList);
	ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+vendorMasterRequestForm.getCountry()+"'");
	ArrayList stateList=new ArrayList();
	ArrayList stateLabelList=new ArrayList();
	
	while(rs4.next()) {
		stateList.add(rs4.getString("BLAND"));
		stateLabelList.add(rs4.getString("BEZEI"));
	}
	vendorMasterRequestForm.setStateList(stateList);
	vendorMasterRequestForm.setStateLabelList(stateLabelList);
	request.setAttribute("States", "States");
	
	ResultSet rs5 = ad.selectQuery("select WAERS,ISOCD from Currency");
	ArrayList currencyList=new ArrayList();
	ArrayList currencyLabelList=new ArrayList();

	while(rs5.next()) {
		currencyList.add(rs5.getString("WAERS"));
		currencyLabelList.add(rs5.getString("WAERS")+"-"+rs5.getString("ISOCD"));
	}
	vendorMasterRequestForm.setCurrencyList(currencyList);
	vendorMasterRequestForm.setCurrencyLabelList(currencyLabelList);
	
	
	ResultSet rs6 = ad.selectQuery("select RECONCILIATION_ACCOUNT_ID," +
			"RECONCILIATION_ACCOUNT_NAME from RECONCILIATION_ACCOUNT_M where ACTIVE='True' order by RECONCILIATION_ACCOUNT_ID");
	ArrayList reConcillationList=new ArrayList();
	ArrayList reConcillationLabelList=new ArrayList();
	
	
	while(rs6.next()) {
		reConcillationList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID"));
		reConcillationLabelList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID")+"-"+rs6.getString("RECONCILIATION_ACCOUNT_NAME"));
	}
	vendorMasterRequestForm.setReConcillationList(reConcillationList);
	vendorMasterRequestForm.setReConcillationLabelList(reConcillationLabelList);
	
	
	ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
	"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
	ArrayList paymentTermList=new ArrayList();
	ArrayList paymentTermLabelList=new ArrayList();
	
	while(rs7.next()) {
		paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
		paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
	}
	vendorMasterRequestForm.setPaymentTermList(paymentTermList);
	vendorMasterRequestForm.setPaymentTermLabelList(paymentTermLabelList);
	
	
	ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
	"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
	ArrayList accountClerkList=new ArrayList();
	ArrayList accountClerkLabelList=new ArrayList();
	
	while(rs8.next()) {
		accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
		accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
	}
	vendorMasterRequestForm.setAccountClerkList(accountClerkList);
	vendorMasterRequestForm.setAccountClerkLabelList(accountClerkLabelList);
	
	
	ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME from location");
	ArrayList locationList=new ArrayList();
	ArrayList locationLabelList=new ArrayList();
	
	while(rs11.next()) {
		locationList.add(rs11.getString("LOCID"));
		locationLabelList.add(rs11.getString("LOCID")+"-"+rs11.getString("LOCNAME"));
	}
	vendorMasterRequestForm.setLocationIdList(locationList);
	vendorMasterRequestForm.setLocationLabelList(locationLabelList);
	
	
	
	ResultSet rsTds = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
	ArrayList tdsIdList=new ArrayList();
	ArrayList tdsLabelList=new ArrayList();
	
	while(rsTds.next()) {
		tdsIdList.add(rsTds.getString("TDS_SECTION_ID"));
		tdsLabelList.add(rsTds.getString("TDS_SECTION_DESC"));
	}
	vendorMasterRequestForm.setTdsIdList(tdsIdList);
	vendorMasterRequestForm.setTdsLabelList(tdsLabelList);
	
	ArrayList venodreCode=new ArrayList();
	ArrayList vendorType=new ArrayList();
	ResultSet rsVendorType = ad.selectQuery("select V_CODE,V_LTXT from VENDOR_TYPE where active='True' ");
	while(rsVendorType.next()) {
		venodreCode.add(rsVendorType.getString("V_CODE"));
		vendorType.add(rsVendorType.getString("V_LTXT"));
	}
	
	
	vendorMasterRequestForm.setRequestDate(EMicroUtils.getCurrentSysDate());
	
			
		int requstNo=vendorMasterRequestForm.getRequestNo();
		String getVendorDetails="select * from vendor_master_m where REQUEST_NO='"+requstNo+"'";
		ResultSet rs=ad.selectQuery(getVendorDetails);
		while(rs.next())
		{
			
			vendorMasterRequestForm.setRequestNo(rs.getInt("REQUEST_NO"));
			vendorMasterRequestForm.setLocationId(rs.getString("LOCATION_ID"));
		    String accountGroupID=rs.getString("ACCOUNT_GROUP_ID");
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
			vendorMasterRequestForm.setPurchaseView("On");

			 vendorMasterRequestForm.setAccountView("Off");
			 }
			 if(viewType.equalsIgnoreCase("2")){
			 vendorMasterRequestForm.setAccountView("On");
			 vendorMasterRequestForm.setPurchaseView("Off");
			 }
			 if(viewType.equalsIgnoreCase("3")){
				 vendorMasterRequestForm.setPurchaseView("On");
				 vendorMasterRequestForm.setAccountView("On");
				 }
			 vendorMasterRequestForm.setTitle(rs.getString("TITLE"));
			 vendorMasterRequestForm.setName(rs.getString("NAME"));
			 vendorMasterRequestForm.setAddress1(rs.getString("ADDRESS1"));
			vendorMasterRequestForm.setAddress2(rs.getString("ADDRESS2"));
			 vendorMasterRequestForm.setAddress3(rs.getString("ADDRESS3"));
			 vendorMasterRequestForm.setAddress4(rs.getString("ADDRESS4"));
			vendorMasterRequestForm.setCity(rs.getString("CITY"));
			 vendorMasterRequestForm.setPinCode(rs.getString("PINCODE"));
			 vendorMasterRequestForm.setCountry(rs.getString("COUNTRY_ID"));
			 vendorMasterRequestForm.setState(rs.getString("STATE"));
			 request.setAttribute("States", "States");
			vendorMasterRequestForm.setLandLineNo(rs.getString("LANDLINE_NO"));
			 vendorMasterRequestForm.setMobileNo(rs.getString("MOBILE_NO"));
			 vendorMasterRequestForm.setFaxNo(rs.getString("FAX_NO"));
			 vendorMasterRequestForm.setEmailId(rs.getString("EMAIL_ID"));
			 vendorMasterRequestForm.setCurrencyId(rs.getString("CURRENCY_ID"));
			 vendorMasterRequestForm.setReConcillationActId(rs.getString("RECONCILATION_ACT_ID"));
			String elgTds=rs.getString("IS_ELIGIBLE_FOR_TDS");
			if(elgTds.equalsIgnoreCase("1"))
			{
				vendorMasterRequestForm.setElgTds("True");
			}
			if(elgTds.equalsIgnoreCase("0"))
			{
				vendorMasterRequestForm.setElgTds("False");
			}
			 vendorMasterRequestForm.setTdsCode(rs.getString("TDS_CODE"));
			 vendorMasterRequestForm.setLstNo(rs.getString("LST_NO"));
			 vendorMasterRequestForm.setTinNo(rs.getString("TIN_NO"));
			vendorMasterRequestForm.setCstNo(rs.getString("CST_NO"));
			 vendorMasterRequestForm.setPanNo(rs.getString("PAN_No"));
			 vendorMasterRequestForm.setServiceTaxRegNo(rs.getString("Service_Tax_Registration_No"));
			 String isRegistVendor=rs.getString("IS_REGD_EXCISE_VENDOR");
			 	if(isRegistVendor.equalsIgnoreCase("1"))
				{
				 vendorMasterRequestForm.setRegExciseVendor("True");
				 request.setAttribute("RegExciseVendor", "RegExciseVendor");
				}
				if(isRegistVendor.equalsIgnoreCase("0"))
				{
					
					
					 vendorMasterRequestForm.setRegExciseVendor("False");
				}
			 
			 vendorMasterRequestForm.setEccNo(rs.getString("ECC_No"));
			 vendorMasterRequestForm.setExciseRegNo(rs.getString("Excise_Reg_No"));
			 vendorMasterRequestForm.setExciseRange(rs.getString("Excise_Range"));
			 vendorMasterRequestForm.setCommissionerate(rs.getString("COMMISSIONERATE"));
			 vendorMasterRequestForm.setExciseDivision(rs.getString("Excise_Division"));
			 vendorMasterRequestForm.setPaymentTermId(rs.getString("PAYMENT_TERM_ID"));
			 vendorMasterRequestForm.setAccountClerkId(rs.getString("ACCOUNT_CLERK_ID"));
			 vendorMasterRequestForm.setIsApprovedVendor(rs.getString("IS_APPROVED_VENDOR"));
			 //vendorMasterRequestForm.setApproveType(rs.getString("Approve_Status"));
		}
		
		
		
		ArrayList listOfFiles=new ArrayList();
		String getUploadedFiles="select * from vendor_documents where request_no='"+requstNo+"'";
		ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
		while(rsUploadFile.next())
		{
			VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
			vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
			vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
			listOfFiles.add(vendorMasterRequestForm1);
		}
		request.setAttribute("documentDetails", listOfFiles);
		
		
		vendorMasterRequestForm.setMessage("");
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		
		int  userId=Integer.parseInt(user.getEmployeeNo());
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
			String updateMaterial="update vendor_master_m set Approve_Status='"+vendorMasterRequestForm.getApproveType()+"',approve_date='"+currentdate+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+vendorMasterRequestForm.getRequestNo()+"'";
			i=ad.SqlExecuteUpdate(updateMaterial);
			if(i>0)
			{
				vendorMasterRequestForm.setMessage2("vendor Code creation request updated with request number='"+vendorMasterRequestForm.getRequestNo()+"'");
				 vendorMasterRequestForm.setMessage("");
			}else{
				vendorMasterRequestForm.setMessage("Error...When updating vendor code  reequest.Please Check");
				 vendorMasterRequestForm.setMessage2("");
				
			} 
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(user.getId()==1)
		{
			request.setAttribute("approved", "approved");
		}
		vendorMasterRequestForm.setTypeDetails("Update");
		
		String forwardType="display";
		
	
		
		return mapping.findForward(forwardType);
	}
	public ActionForward firstVedorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		VendorMasterRequestForm venderMastForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList vendorList=new LinkedList();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int userID=Integer.parseInt(user.getEmployeeNo());
		try{
			int totalRecords=venderMastForm.getTotalRecords();//21
			int startRecord=venderMastForm.getStartRecord();//11
			int endRecord=venderMastForm.getEndRecord();	
			
			
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  venderMastForm.setTotalRecords(totalRecords);
				  venderMastForm.setStartRecord(startRecord);
				  venderMastForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  venderMastForm.setTotalRecords(totalRecords);
					  venderMastForm.setStartRecord(startRecord);
					  venderMastForm.setEndRecord(totalRecords);  
				  }

			  String status=venderMastForm.getApproveType();
			 if(status.equalsIgnoreCase(""))
				{
					  
				 String getVendorMasterList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.ID) AS  RowNum,v.REQUEST_DATE,v.REQUEST_NO,v.NAME,v.CITY," +
		"v.Approve_Status,emp.EMP_FULLNAME,dept.DPTSTXT,loc.LOCATION_CODE from  vendor_master_m as v,emp_official_info as  emp,Location as loc," +
		"DEPARTMENT as dept where v.Approve_Status='In Process' and v.CREATED_BY='"+user.getEmployeeNo()+"' and  v.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCID " +
		"and emp.DPTID=dept.DPTID) as sub Where  sub.RowNum  between '"+startRecord+"' and '"+endRecord+"' ";
							  
						  
						  ResultSet rs=ad.selectQuery(getVendorMasterList);
					
					while(rs.next()){
						VendorMasterRequestForm venderform=new VendorMasterRequestForm();
						venderform.setLocationId(rs.getString("LOCNAME"));
						String requestDate=rs.getString("REQUEST_DATE");
						String req[]=requestDate.split(" "); 
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
						venderform.setRequestDate(requestDate);
						venderform.setRequestNo(rs.getInt("REQUEST_NO"));
						venderform.setName(rs.getString("NAME"));
						venderform.setCity(rs.getString("CITY"));
						venderform.setRequestedBy(rs.getString("EMP_FULLNAME"));
						venderform.setDepartment(rs.getString("DPTSTXT"));
						venderform.setLocationId(rs.getString("LOCATION_CODE"));
						venderform.setApproveType(rs.getString("Approve_Status"));
						
						vendorList.add(venderform);
					}
					request.setAttribute("vendorMasterList", vendorList);
				
				}else{
							
						String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
						"from vendor_master_m where Approve_Status='"+status+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
						 if(userID==1 || userID==2)
						  {
							 getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
								"from vendor_master_m where Approve_Status='"+status+"' ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
						  }
				ResultSet rs=ad.selectQuery(getRecord);
				while(rs.next())
				{
					VendorMasterRequestForm venderform=new VendorMasterRequestForm();
					
				
				
					String requestDate=rs.getString("REQUEST_DATE");
					String req[]=requestDate.split(" ");
					requestDate=req[0];
					String a[]=requestDate.split("-");
					requestDate=a[2]+"/"+a[1]+"/"+a[0];
					venderform.setRequestDate(requestDate);
					venderform.setRequestNo(rs.getInt("REQUEST_NO"));
					venderform.setName(rs.getString("NAME"));
					venderform.setCity(rs.getString("CITY"));
					venderform.setApproveType(rs.getString("Approve_Status"));
					vendorList.add(venderform);
				}
				request.setAttribute("vendorMasterList", vendorList);
				}
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
				}
			
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				
				request.setAttribute("displayRecordNo", "displayRecordNo");
				request.setAttribute("materialCodeList","materialCodeList");
					
		
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("vendorMasterList");
	}
	
	
	public ActionForward lastVedorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		VendorMasterRequestForm venderMastForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList vendorList=new LinkedList();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int userID=Integer.parseInt(user.getEmployeeNo());
			
		try{
			int totalRecords=venderMastForm.getTotalRecords();//21
			int startRecord=venderMastForm.getStartRecord();//11
			int endRecord=venderMastForm.getEndRecord();	
			
			
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 venderMastForm.setTotalRecords(totalRecords);
			 venderMastForm.setStartRecord(startRecord);
			 venderMastForm.setEndRecord(totalRecords);
			  String status=venderMastForm.getApproveType();
			 if(status.equalsIgnoreCase(""))
				{
					  
				 String getVendorMasterList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.ID) AS  RowNum,v.REQUEST_DATE,v.REQUEST_NO,v.NAME,v.CITY," +
		"v.Approve_Status,emp.EMP_FULLNAME,dept.DPTSTXT,loc.LOCATION_CODE from  vendor_master_m as v,emp_official_info as  emp,Location as loc," +
		"DEPARTMENT as dept where v.Approve_Status='In Process' and v.CREATED_BY='"+user.getEmployeeNo()+"' and  v.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCID " +
		"and emp.DPTID=dept.DPTID) as sub Where  sub.RowNum  between '"+startRecord+"' and '"+endRecord+"' ";
						  
						  ResultSet rs=ad.selectQuery(getVendorMasterList);
					
					while(rs.next()){
						VendorMasterRequestForm venderform=new VendorMasterRequestForm();
						venderform.setLocationId(rs.getString("LOCNAME"));
						String requestDate=rs.getString("REQUEST_DATE");
						String req[]=requestDate.split(" "); 
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
						venderform.setRequestDate(requestDate);
						venderform.setRequestNo(rs.getInt("REQUEST_NO"));
						venderform.setName(rs.getString("NAME"));
						venderform.setCity(rs.getString("CITY"));
						venderform.setRequestedBy(rs.getString("EMP_FULLNAME"));
						venderform.setDepartment(rs.getString("DPTSTXT"));
						venderform.setLocationId(rs.getString("LOCATION_CODE"));
						venderform.setApproveType(rs.getString("Approve_Status"));
						
						vendorList.add(venderform);
					}
					request.setAttribute("vendorMasterList", vendorList);
				
				}else{
							
						String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
						"from vendor_master_m where Approve_Status='"+status+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
						if(userID==1|| userID==2){
							getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
							"from vendor_master_m where Approve_Status='"+status+"' ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
							
						}
				ResultSet rs=ad.selectQuery(getRecord);
				while(rs.next())
				{
					VendorMasterRequestForm venderform=new VendorMasterRequestForm();
					
				
				
					String requestDate=rs.getString("REQUEST_DATE");
					String req[]=requestDate.split(" ");
					requestDate=req[0];
					String a[]=requestDate.split("-");
					requestDate=a[2]+"/"+a[1]+"/"+a[0];
					venderform.setRequestDate(requestDate);
					venderform.setRequestNo(rs.getInt("REQUEST_NO"));
					venderform.setName(rs.getString("NAME"));
					venderform.setCity(rs.getString("CITY"));
					venderform.setRequestedBy(rs.getString("emp_name"));
					venderform.setDepartment(rs.getString("dept_name"));
					venderform.setLocationId(rs.getString("LOCATION_CODE"));
					venderform.setApproveType(rs.getString("Approve_Status"));
					vendorList.add(venderform);
				}
				request.setAttribute("vendorMasterList", vendorList);
				}
			 request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton");
				if(vendorList.size()<10)
				{
					
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				request.setAttribute("displayRecordNo", "displayRecordNo");
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("vendorMasterList");
	}
	
	
	public ActionForward previousVedorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		VendorMasterRequestForm venderMastForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList vendorList=new LinkedList();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int userID=Integer.parseInt(user.getEmployeeNo());
	try{
		System.out.println("Start Record="+venderMastForm.getStartRecord());
		System.out.println("End record="+venderMastForm.getEndRecord());
		System.out.println("Total Record="+venderMastForm.getTotalRecords());	
		
		int totalRecords=venderMastForm.getTotalRecords();//21
		int endRecord=venderMastForm.getStartRecord()-1;//20
		int startRecord=venderMastForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		 String getTotalRecords="select count(*) from material_code_request where CREATED_BY='"+user.getEmployeeNo()+"'";
		  if(userID==1 ||userID==2)
		  {
			  getTotalRecords="select count(*) from material_code_request ";
		  }
			  
		  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
		  while(rsTotalRecods.next())
		  {
			  totalRecords=rsTotalRecods.getInt(1);
		  }
		  venderMastForm.setTotalRecords(totalRecords);
		  venderMastForm.setStartRecord(1);
		  venderMastForm.setEndRecord(10);
		  String status=venderMastForm.getApproveType();
	if(status.equalsIgnoreCase(""))
	{
		  
		String getVendorMasterList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.ID) AS  RowNum,v.REQUEST_DATE,v.REQUEST_NO,v.NAME,v.CITY," +
		"v.Approve_Status,emp.EMP_FULLNAME,dept.DPTSTXT,loc.LOCATION_CODE from  vendor_master_m as v,emp_official_info as  emp,Location as loc," +
		"DEPARTMENT as dept where v.Approve_Status='In Process' and v.CREATED_BY='"+user.getEmployeeNo()+"' and  v.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCID " +
		"and emp.DPTID=dept.DPTID) as sub Where  sub.RowNum  between '"+startRecord+"' and '"+endRecord+"' ";
			  ResultSet rs=ad.selectQuery(getVendorMasterList);
		
		while(rs.next()){
			VendorMasterRequestForm venderform=new VendorMasterRequestForm();
			venderform.setLocationId(rs.getString("LOCNAME"));
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" "); 
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			venderform.setRequestDate(requestDate);
			venderform.setRequestNo(rs.getInt("REQUEST_NO"));
			venderform.setName(rs.getString("NAME"));
			venderform.setCity(rs.getString("CITY"));
			venderform.setRequestedBy(rs.getString("EMP_FULLNAME"));
			venderform.setDepartment(rs.getString("DPTSTXT"));
			venderform.setLocationId(rs.getString("LOCATION_CODE"));
			venderform.setApproveType(rs.getString("Approve_Status"));
	
			
			vendorList.add(venderform);
		}
		request.setAttribute("vendorMasterList", vendorList);
	
	}else{
				
			String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
			"from vendor_master_m where Approve_Status='"+status+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			  if(userID==1 ||userID==2)
			  {
				  getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
					"from vendor_master_m where Approve_Status='"+status+"' ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
						 
			  }
	
	ResultSet rs=ad.selectQuery(getRecord);
	while(rs.next())
	{
		VendorMasterRequestForm venderform=new VendorMasterRequestForm();
		
	
	
		String requestDate=rs.getString("REQUEST_DATE");
		String req[]=requestDate.split(" ");
		requestDate=req[0];
		String a[]=requestDate.split("-");
		requestDate=a[2]+"/"+a[1]+"/"+a[0];
		venderform.setRequestDate(requestDate);
		venderform.setRequestNo(rs.getInt("REQUEST_NO"));
		venderform.setName(rs.getString("NAME"));
		venderform.setCity(rs.getString("CITY"));
		venderform.setApproveType(rs.getString("Approve_Status"));
		vendorList.add(venderform);
	}
	request.setAttribute("vendorMasterList", vendorList);
	}
		  venderMastForm.setTotalRecords(totalRecords);
		  venderMastForm.setStartRecord(startRecord);
		  venderMastForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(vendorList.size()<10)
			{
				venderMastForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("vendorMasterList");
	}
	
	
	public ActionForward nextVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		VendorMasterRequestForm venderMastForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList vendorList=new LinkedList();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int userID=Integer.parseInt(user.getEmployeeNo());
	try{
		int totalRecords=venderMastForm.getTotalRecords();//21
		int startRecord=venderMastForm.getStartRecord();//11
		int endRecord=venderMastForm.getEndRecord();
	
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
		String status=venderMastForm.getApproveType();
		if(status.equalsIgnoreCase(""))
		{
			
			String getVendorMasterList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.ID) AS  RowNum,v.REQUEST_DATE,v.REQUEST_NO,v.NAME,v.CITY," +
		"v.Approve_Status,emp.EMP_FULLNAME,dept.DPTSTXT,loc.LOCATION_CODE from  vendor_master_m as v,emp_official_info as  emp,Location as loc," +
		"DEPARTMENT as dept where v.Approve_Status='In Process' and v.CREATED_BY='"+user.getEmployeeNo()+"' and  v.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCID " +
		"and emp.DPTID=dept.DPTID) as sub Where  sub.RowNum  between '"+startRecord+"' and '"+endRecord+"' ";
			ResultSet rs=ad.selectQuery(getVendorMasterList);
			
			while(rs.next()){
				VendorMasterRequestForm venderform=new VendorMasterRequestForm();
				venderform.setLocationId(rs.getString("LOCNAME"));
				String requestDate=rs.getString("REQUEST_DATE");
				String req[]=requestDate.split(" "); 
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				venderform.setRequestDate(requestDate);
				venderform.setRequestNo(rs.getInt("REQUEST_NO"));
				venderform.setName(rs.getString("NAME"));
				venderform.setCity(rs.getString("CITY"));
				venderform.setRequestedBy(rs.getString("EMP_FULLNAME"));
				venderform.setDepartment(rs.getString("DPTSTXT"));
				venderform.setLocationId(rs.getString("LOCATION_CODE"));
				venderform.setApproveType(rs.getString("Approve_Status"));

				
				vendorList.add(venderform);
			}
			request.setAttribute("vendorMasterList", vendorList);
		}
		else{
			String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
			"from vendor_master_m where Approve_Status='"+status+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
		
	
	ResultSet rs=ad.selectQuery(getRecord);
	while(rs.next())
	{
		VendorMasterRequestForm venderform=new VendorMasterRequestForm();
		


		String requestDate=rs.getString("REQUEST_DATE");
		String req[]=requestDate.split(" ");
		requestDate=req[0];
		String a[]=requestDate.split("-");
		requestDate=a[2]+"/"+a[1]+"/"+a[0];
		venderform.setRequestDate(requestDate);
		venderform.setRequestNo(rs.getInt("REQUEST_NO"));
		venderform.setName(rs.getString("NAME"));
		venderform.setCity(rs.getString("CITY"));
		venderform.setApproveType(rs.getString("Approve_Status"));
		vendorList.add(venderform);
	}
	request.setAttribute("vendorMasterList", vendorList);
			
			
		}
		
		}
		System.out.println("list length="+vendorList.size());
		
		 if(vendorList.size()!=0)
			{
			 venderMastForm.setTotalRecords(totalRecords);
			 venderMastForm.setStartRecord(startRecord);
			 venderMastForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				venderMastForm.setTotalRecords(totalRecords);
				venderMastForm.setStartRecord(start);
				venderMastForm.setEndRecord(end);
				
			}
		 if(vendorList.size()<10)
		 {
			 venderMastForm.setTotalRecords(totalRecords);
			 venderMastForm.setStartRecord(startRecord);
			 venderMastForm.setEndRecord(startRecord+vendorList.size()-1);
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
		
	}catch (Exception e) {
	e.printStackTrace();
	}
		
		return mapping.findForward("vendorMasterList");
	}
	
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VendorMasterRequestForm vendorMasterForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int userID=Integer.parseInt(user.getEmployeeNo());
		  int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		try{
			int recordsTotal=0;
		String status=vendorMasterForm.getApproveType();
		
		String getVendorCount="Select count(*) from vendor_master_m where CREATED_BY='"+user.getEmployeeNo()+"' and Approve_Status='"+status+"'";
		
		ResultSet rsVendorCount=ad.selectQuery(getVendorCount);
		while(rsVendorCount.next())
		{
			recordsTotal=rsVendorCount.getInt(1);
		}
		System.out.println("Toatal Records="+recordsTotal);	    	 
		 if(recordsTotal>10)
		 {
			 vendorMasterForm.setTotalRecords(recordsTotal);
		 startRecord=1;
		 endRecord=10;
		 vendorMasterForm.setStartRecord(1);
		 vendorMasterForm.setEndRecord(10);
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		 request.setAttribute("displayRecordNo", "displayRecordNo");
		 request.setAttribute("nextButton", "nextButton");
		 }else
		 {
			  startRecord=1;
			  endRecord=totalRecords;
			  vendorMasterForm.setTotalRecords(totalRecords);
			  vendorMasterForm.setStartRecord(1);
			  vendorMasterForm.setEndRecord(totalRecords); 
		 }
		String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.ID) AS  RowNum,v.REQUEST_DATE,v.REQUEST_NO,v.NAME,v.CITY," +
		"v.Approve_Status,emp.EMP_FULLNAME,dept.DPTSTXT,loc.LOCATION_CODE from  vendor_master_m as v,emp_official_info as  emp,Location as loc," +
		"DEPARTMENT as dept where v.Approve_Status='"+status+"' and v.CREATED_BY='"+user.getEmployeeNo()+"' and  v.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCATION_CODE " +
		"and emp.DPTID=dept.DPTID) as sub Where  sub.RowNum  between 1 and 10";
		
		
		ResultSet rs=ad.selectQuery(getRecord);
		LinkedList vendorList=new LinkedList();
		while(rs.next())
		{
			VendorMasterRequestForm venderform=new VendorMasterRequestForm();
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			venderform.setRequestDate(requestDate);
			venderform.setRequestNo(rs.getInt("REQUEST_NO"));
			venderform.setName(rs.getString("NAME"));
			venderform.setCity(rs.getString("CITY"));
			venderform.setRequestedBy(rs.getString("EMP_FULLNAME"));
			venderform.setDepartment(rs.getString("DPTSTXT"));
			venderform.setLocationId(rs.getString("LOCATION_CODE"));
			venderform.setApproveType(rs.getString("Approve_Status"));
			vendorList.add(venderform);
		}
		request.setAttribute("vendorMasterList", vendorList);
		
		if(vendorList.size()==0){
			request.setAttribute("noRecords", "noRecords");
			vendorMasterForm.setMessage1("No Records Found");
			vendorMasterForm.setMessage2("");
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
			
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("vendorMasterList");
		
		
	}
	
	public ActionForward editVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		try{
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	vendorMasterRequestForm.setAccountGroupList(accountGroupList);
	vendorMasterRequestForm.setAccountGroupLabelList(accountGroupLabelList);
		 	 
		 	 
	ResultSet rs9 = ad.selectQuery("select * from Country");
	ArrayList countryList=new ArrayList();
	ArrayList countryLabelList=new ArrayList();
	
	while(rs9.next()) {
		countryList.add(rs9.getString("LAND1"));
		countryLabelList.add(rs9.getString("LAND1")+" - "+rs9.getString("LANDX"));
	}
	vendorMasterRequestForm.setCountryList(countryList);
	vendorMasterRequestForm.setCountryLabelList(countryLabelList);
	
	
	ResultSet rs5 = ad.selectQuery("select WAERS,ISOCD from Currency");
	ArrayList currencyList=new ArrayList();
	ArrayList currencyLabelList=new ArrayList();

	while(rs5.next()) {
		currencyList.add(rs5.getString("WAERS"));
		currencyLabelList.add(rs5.getString("ISOCD"));
	}
	vendorMasterRequestForm.setCurrencyList(currencyList);
	vendorMasterRequestForm.setCurrencyLabelList(currencyLabelList);
	
	
	ResultSet rs6 = ad.selectQuery("select RECONCILIATION_ACCOUNT_ID," +
			"RECONCILIATION_ACCOUNT_NAME from RECONCILIATION_ACCOUNT_M where ACTIVE='True' order by RECONCILIATION_ACCOUNT_ID");
	ArrayList reConcillationList=new ArrayList();
	ArrayList reConcillationLabelList=new ArrayList();
	
	
	while(rs6.next()) {
		reConcillationList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID"));
		reConcillationLabelList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID")+"-"+rs6.getString("RECONCILIATION_ACCOUNT_NAME"));
	}
	vendorMasterRequestForm.setReConcillationList(reConcillationList);
	vendorMasterRequestForm.setReConcillationLabelList(reConcillationLabelList);
	
	
	ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
	"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
	ArrayList paymentTermList=new ArrayList();
	ArrayList paymentTermLabelList=new ArrayList();
	
	while(rs7.next()) {
		paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
		paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
	}
	vendorMasterRequestForm.setPaymentTermList(paymentTermList);
	vendorMasterRequestForm.setPaymentTermLabelList(paymentTermLabelList);
	
	
	ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
	"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
	ArrayList accountClerkList=new ArrayList();
	ArrayList accountClerkLabelList=new ArrayList();
	
	while(rs8.next()) {
		accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
		accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
	}
	vendorMasterRequestForm.setAccountClerkList(accountClerkList);
	vendorMasterRequestForm.setAccountClerkLabelList(accountClerkLabelList);
	
	
	ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME from location");
	ArrayList locationList=new ArrayList();
	ArrayList locationLabelList=new ArrayList();
	
	while(rs11.next()) {
		locationList.add(rs11.getString("LOCID"));
		locationLabelList.add(rs11.getString("LOCID")+"-"+rs11.getString("LOCNAME"));
	}
	vendorMasterRequestForm.setLocationIdList(locationList);
	vendorMasterRequestForm.setLocationLabelList(locationLabelList);
	
	
	
	ResultSet rsTds = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
	ArrayList tdsIdList=new ArrayList();
	ArrayList tdsLabelList=new ArrayList();
	
	while(rsTds.next()) {
		tdsIdList.add(rsTds.getString("TDS_SECTION_ID"));
		tdsLabelList.add(rsTds.getString("TDS_SECTION_DESC"));
	}
	vendorMasterRequestForm.setTdsIdList(tdsIdList);
	vendorMasterRequestForm.setTdsLabelList(tdsLabelList);
	
	ArrayList venodreCode=new ArrayList();
	ArrayList vendorType=new ArrayList();
	ResultSet rsVendorType = ad.selectQuery("select V_CODE,V_LTXT from VENDOR_TYPE where active='True' ");
	while(rsVendorType.next()) {
		venodreCode.add(rsVendorType.getString("V_CODE"));
		vendorType.add(rsVendorType.getString("V_LTXT"));
	}
	
	vendorMasterRequestForm.setRequestDate(EMicroUtils.getCurrentSysDate());
	
			String country="";
		int requstNo=Integer.parseInt(request.getParameter("requstNo"));
		String getVendorDetails="select * from vendor_master_m where REQUEST_NO='"+requstNo+"'";
		ResultSet rs=ad.selectQuery(getVendorDetails);
		while(rs.next())
		{
			
			vendorMasterRequestForm.setRequestNo(rs.getInt("REQUEST_NO"));
			vendorMasterRequestForm.setLocationId(rs.getString("LOCATION_ID"));
		    String accountGroupID=rs.getString("ACCOUNT_GROUP_ID");
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
			vendorMasterRequestForm.setPurchaseView("On");

			 vendorMasterRequestForm.setAccountView("Off");
			 }
			 if(viewType.equalsIgnoreCase("2")){
			 vendorMasterRequestForm.setAccountView("On");
			 vendorMasterRequestForm.setPurchaseView("Off");
			 }
			 if(viewType.equalsIgnoreCase("3")){
				 vendorMasterRequestForm.setPurchaseView("On");
				 vendorMasterRequestForm.setAccountView("On");
				 }
			 vendorMasterRequestForm.setTitle(rs.getString("TITLE"));
			 vendorMasterRequestForm.setName(rs.getString("NAME"));
			 vendorMasterRequestForm.setAddress1(rs.getString("ADDRESS1"));
			vendorMasterRequestForm.setAddress2(rs.getString("ADDRESS2"));
			 vendorMasterRequestForm.setAddress3(rs.getString("ADDRESS3"));
			 vendorMasterRequestForm.setAddress4(rs.getString("ADDRESS4"));
			vendorMasterRequestForm.setCity(rs.getString("CITY"));
			 vendorMasterRequestForm.setPinCode(rs.getString("PINCODE"));
			 vendorMasterRequestForm.setCountry(rs.getString("COUNTRY_ID"));
			 country=rs.getString("COUNTRY_ID");
			 vendorMasterRequestForm.setState(rs.getString("STATE"));
			 request.setAttribute("States", "States");
			vendorMasterRequestForm.setLandLineNo(rs.getString("LANDLINE_NO"));
			 vendorMasterRequestForm.setMobileNo(rs.getString("MOBILE_NO"));
			 vendorMasterRequestForm.setFaxNo(rs.getString("FAX_NO"));
			 vendorMasterRequestForm.setEmailId(rs.getString("EMAIL_ID"));
			 vendorMasterRequestForm.setCurrencyId(rs.getString("CURRENCY_ID"));
			 vendorMasterRequestForm.setReConcillationActId(rs.getString("RECONCILATION_ACT_ID"));
			String elgTds=rs.getString("IS_ELIGIBLE_FOR_TDS");
			if(elgTds.equalsIgnoreCase("1"))
			{
				vendorMasterRequestForm.setElgTds("True");
			}
			if(elgTds.equalsIgnoreCase("0"))
			{
				vendorMasterRequestForm.setElgTds("False");
			}
			 vendorMasterRequestForm.setTdsCode(rs.getString("TDS_CODE"));
			 vendorMasterRequestForm.setLstNo(rs.getString("LST_NO"));
			 vendorMasterRequestForm.setTinNo(rs.getString("TIN_NO"));
			vendorMasterRequestForm.setCstNo(rs.getString("CST_NO"));
			 vendorMasterRequestForm.setPanNo(rs.getString("PAN_No"));
			 vendorMasterRequestForm.setServiceTaxRegNo(rs.getString("Service_Tax_Registration_No"));
			 String isRegistVendor=rs.getString("IS_REGD_EXCISE_VENDOR");
			 	if(isRegistVendor.equalsIgnoreCase("1"))
				{
				 vendorMasterRequestForm.setRegExciseVendor("True");
				 request.setAttribute("RegExciseVendor", "RegExciseVendor");
				}
				if(isRegistVendor.equalsIgnoreCase("0"))
				{
					
					
					 vendorMasterRequestForm.setRegExciseVendor("False");
				}
			 
			 vendorMasterRequestForm.setEccNo(rs.getString("ECC_No"));
			 vendorMasterRequestForm.setExciseRegNo(rs.getString("Excise_Reg_No"));
			 vendorMasterRequestForm.setExciseRange(rs.getString("Excise_Range"));
			 vendorMasterRequestForm.setCommissionerate(rs.getString("COMMISSIONERATE"));
			 vendorMasterRequestForm.setExciseDivision(rs.getString("Excise_Division"));
			 vendorMasterRequestForm.setPaymentTermId(rs.getString("PAYMENT_TERM_ID"));
			 vendorMasterRequestForm.setAccountClerkId(rs.getString("ACCOUNT_CLERK_ID"));
			 vendorMasterRequestForm.setIsApprovedVendor(rs.getString("IS_APPROVED_VENDOR"));
			 String isApproveVendor=rs.getString("IS_APPROVED_VENDOR");
			if(isApproveVendor.equalsIgnoreCase("1"))
			{
				vendorMasterRequestForm.setIsApprovedVendor("True");
			}	if(isApproveVendor.equalsIgnoreCase("0")){
				vendorMasterRequestForm.setIsApprovedVendor("False");
			}
			 vendorMasterRequestForm.setApproveType(rs.getString("Approve_Status"));
			 vendorMasterRequestForm.setTypeOfVendor(rs.getString("Type_Of_Vendor"));
		}
		
		ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+vendorMasterRequestForm.getCountry()+"'");
		ArrayList stateList=new ArrayList();
		ArrayList stateLabelList=new ArrayList();
		
		while(rs4.next()) {
			stateList.add(rs4.getString("BLAND"));
			stateLabelList.add(rs4.getString("BEZEI"));
		}
		vendorMasterRequestForm.setStateList(stateList);
		vendorMasterRequestForm.setStateLabelList(stateLabelList);
		request.setAttribute("States", "States");
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ArrayList listOfFiles=new ArrayList();
		String getUploadedFiles="select * from temp_vendor_documents where request_no='"+requstNo+"' and userId='"+user.getEmployeeNo()+"' ";
		ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
		while(rsUploadFile.next())
		{
			VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
			vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
			vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
			vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
			listOfFiles.add(vendorMasterRequestForm1);
		}
		request.setAttribute("documentDetails", listOfFiles);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		vendorMasterRequestForm.setMessage("");
		
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user.getId()==1)
		{
			request.setAttribute("approved", "approved");
		}
		vendorMasterRequestForm.setTypeDetails("Update");
		
		String forwardType="display";
		
		
		vendorMasterRequestForm.setMessage("");
		vendorMasterRequestForm.setAppStatusMessage("");
		return mapping.findForward(forwardType);
	}
	
	
	public ActionForward deleteVendorMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		   VendorMasterRequestForm vendorMasterForm=(VendorMasterRequestForm)form;
		   EssDao ad=EssDao.dBConnection();
		  
		   HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
		   try{
				String reqNo[]=vendorMasterForm.getRequiredRequestNumber();
				
				for(int j=0;j<reqNo.length;j++)
				{
				   String deleteRecord="update  vendor_master_m set Approve_Status='deleted' where REQUEST_NO='"+reqNo[j]+"' and  CREATED_BY='"+user.getEmployeeNo()+"'";
				   int i=0;
				   i=ad.SqlExecuteUpdate(deleteRecord);
				   String deleteUploadedFiles="delete from  temp_vendor_documents where request_no='"+reqNo[j]+"' and userId='"+user.getEmployeeNo()+"'" ;
			ad.SqlExecuteUpdate(deleteUploadedFiles);
				   if(i>=1)
				   {
					   vendorMasterForm.setMessage2("Vendor Master Record Successfully Deleted");
					   vendorMasterForm.setMessage("");
				   }else{
					   vendorMasterForm.setMessage("Error.... When Vendor Master Record.");
					   vendorMasterForm.setMessage2("");
				   }
				}
				
				}catch (Exception e) {
					vendorMasterForm.setMessage("Error.... When Deleting Vendor Master.Please Select Atleast One Record");
					 vendorMasterForm.setMessage("");
					displayVendorMasterList(mapping, form, request, response);
				}
		   
		   
		   return mapping.findForward("vendorMasterList");
	}
	
	public ActionForward displayVendorMasterList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		vendorMasterRequestForm.setMessage("");
		vendorMasterRequestForm.setMessage2("");
		try{
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCID")+"-"+rs11.getString("LOCNAME"));
			}
			vendorMasterRequestForm.setLocationIdList(locationList);
			vendorMasterRequestForm.setLocationLabelList(locationLabelList);
			
			  HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				int userID=Integer.parseInt(user.getEmployeeNo());
			int  totalRecords=0;
			  int  startRecord=0;
			  int  endRecord=0;
			  
			  String getTotalRecords="select count(*) from vendor_master_m where Approve_Status='In Process'  and CREATED_BY='"+user.getEmployeeNo()+"'";
			  
			 // String getTotalRecords="select count(*) from material_code_request as m where  m.Approve_Type='Pending' and m.LOCATION_ID='"+user.getPlantId()+"'";
			  
			  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
			  while(rsTotalRecods.next())
			  {
				  totalRecords=rsTotalRecods.getInt(1);
			  }
			  
			  if(totalRecords>=10)
			  {
				  vendorMasterRequestForm.setTotalRecords(totalRecords);
			  startRecord=1;
			  endRecord=10;
			  vendorMasterRequestForm.setStartRecord(1);
			  vendorMasterRequestForm.setEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords;
				  vendorMasterRequestForm.setTotalRecords(totalRecords);
				  vendorMasterRequestForm.setStartRecord(1);
				  vendorMasterRequestForm.setEndRecord(totalRecords); 
			  }
			
			
			LinkedList vendorList=new LinkedList();
		String getVendorMasterList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY v.ID) AS  RowNum,v.REQUEST_DATE,v.REQUEST_NO,v.NAME,v.CITY," +
		"v.Approve_Status,emp.EMP_FULLNAME,dept.DPTSTXT,loc.LOCATION_CODE from  vendor_master_m as v,emp_official_info as  emp,Location as loc," +
		"DEPARTMENT as dept where v.Approve_Status='In Process' and v.CREATED_BY='"+user.getEmployeeNo()+"' and  v.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCATION_CODE " +
		"and emp.DPTID=dept.DPTID) as sub Where  sub.RowNum  between 1 and 10";
 		
		ResultSet rs=ad.selectQuery(getVendorMasterList);
		while(rs.next()){
			VendorMasterRequestForm venderform=new VendorMasterRequestForm();
			
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" "); 
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[1]+"/"+a[2]+"/"+a[0];
			venderform.setRequestDate(requestDate);
			venderform.setRequestNo(rs.getInt("REQUEST_NO"));
			venderform.setName(rs.getString("NAME"));
			venderform.setCity(rs.getString("CITY"));
			venderform.setRequestedBy(rs.getString("EMP_FULLNAME"));
			venderform.setDepartment(rs.getString("DPTSTXT"));
			venderform.setLocationId(rs.getString("LOCATION_CODE"));
			venderform.setApproveType(rs.getString("Approve_Status"));
			
			vendorList.add(venderform);
		}
		request.setAttribute("vendorMasterList", vendorList);
		
		if(vendorList.size()==0){
			request.setAttribute("noRecords", "noRecords");
			vendorMasterRequestForm.setMessage1("No Records Found");
			vendorMasterRequestForm.setMessage2("");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		vendorMasterRequestForm.setRequestNumber("");
	vendorMasterRequestForm.setLocationSearch("");
		vendorMasterRequestForm.setRequestsearchDate("");
		vendorMasterRequestForm.setApproveType("In Process");
		vendorMasterRequestForm.setMessage("");
		String module=request.getParameter("id"); 
		request.setAttribute("MenuIcon", module);
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		return mapping.findForward("vendorMasterList");
		
	}	
	public ActionForward displayCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		
		String linkName=request.getParameter("sId"); 
		
		String module=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{
			
			//LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			 	
			String sql3="select id from links where module='"+module+"'";
			
			ResultSet rs2=ad.selectQuery(sql3);
			
			String inLinks="";
			while (rs2.next()) {
				inLinks+=rs2.getString("id")+",";
			}
			
			Object obj=new Object();
			String sql11="select * from links where link_name='"+linkName+"' and module='"+module+"'";
			
			ResultSet rs11=ad.selectQuery(sql11);
			
			try{
				while(rs11.next()) {
					 
					vendorMasterRequestForm.setContentDescription(rs11.getString("content_description"));
					vendorMasterRequestForm.setFileFullPath(rs11.getString("file_name"));
					vendorMasterRequestForm.setVideoFullPath(rs11.getString("video_name"));
				}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			inLinks=inLinks.substring(0, inLinks.lastIndexOf(","));
			String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
			    list.add(s);  
			}  
			
			System.out.println("Getting a list is ***********************"+list);
			
			
			String userLinks=user.getIncludeSubLinks()+","+user.getIncludeSubSubLinks();
			
			String[] a1=userLinks.split(",");
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
			    list1.add(s);  
			}  
		    
			System.out.println("Getting a list1 is ***********************"+list1);
			
			
			Collection listOne = list;
	        Collection listTwo = list1;
	        
	        listTwo.retainAll(listOne);
	        System.out.println("Getting a listTwo is ***********************"+listTwo);
			
			//list.removeAll(list1);
			
			String inkLinks="";
			
			
			/*String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
					" from links  " +
					" where module='ESS'" +
					" and id in(13,14,18,19,59,60,61,62,63,64,65,66,67,68,69,70,71,86) order by priority";
		*/	
			
			 String commaDelimitedString = StringUtils.collectionToCommaDelimitedString(listTwo);
			 commaDelimitedString=commaDelimitedString+"93,86,96,98,99,100";
			String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname," +
					"icon_name " +
			" from links  " +
			" where module='ESS'" +
			" and id in(13,14,18,19,59,60,61,62,63,64,65,66,67,68,69,70,71,86,87,88,89,93,96,99,100,104) order by priority";
			
			
			ResultSet rs1=ad.selectQuery(sql1);
				
				
			//select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as 
			// url,module,link_name,sub_linkname  from links   where module ='News And Media' and id 
			//in(38,39,40,45,46) order by id	
			
			//LinkedHashMap hm=new LinkedHashMap();	
				
			
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<LinksForm> allLinkList=new LinkedList<LinksForm>();
		 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= new LinkedHashMap<LinksForm,LinkedList<LinksForm>>();
		 	
			while(rs1.next()){
				
				//System.out.println("rs1.getString(sub_linkname) **************"+rs1.getString("sub_linkname"));
				mainMenuSet.add(rs1.getString("sub_linkname"));
				LinksForm l=new LinksForm();
					l.setLinkPath(rs1.getString("url"));
					l.setPriority(""+rs1.getString("priority"));
					l.setSubLinkName(rs1.getString("sub_linkname"));
					l.setLinkName(rs1.getString("link_name"));
					l.setIconName(rs1.getString("icon_name"));
				allLinkList.add(l);
			}	
		 		
				
			 	for(String main:mainMenuSet){
			 		LinkedList<LinksForm> links=new LinkedList<LinksForm>();
			 		LinksForm temp=null;
			 		System.out.println(" -- "+main+"");
			 		for(LinksForm aa:allLinkList){
			 				for(LinksForm sl:allLinkList)
			 				{
			 				System.out.println("("+sl.getLinkName()+" -- "+sl.getSubLinkName()+") -- "+main+"");
			 						
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 		for(LinksForm g:links) System.out.println(g.getLinkName());
	 				
	 				System.out.println(main+"---temp lnk nmae--"+temp.getLinkName());
	 				finalLnkdList.put(temp, links);
			 	}
			 	
			 	vendorMasterRequestForm.setLinkName(linkName);
			 	
			 	
			 	System.out.println("finalLnkdList.size--"+finalLnkdList.size()+"\n\n\n\n\n");
		 	
		 	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
		 	for (Map.Entry<LinksForm,LinkedList<LinksForm>> entry : finalLnkdList.entrySet()) {
		 	    System.out.println("Key = " + entry.getKey().getLinkName() +"" +
		 	    		"Link Path is"+ entry.getKey().getLinkPath()+"Get Image "+ entry.getKey().getIconName()+ ", Value = " + entry.getValue().size()+"");
		 	    for (LinksForm lin : entry.getValue()) {

					System.out.println("--->"+lin.getLinkName());
				}
		 	}
		 	
		 	
		 	String sql2="select * from cms_sublinks where main_linkname='"+module+"' " +
		 			"and sub_linkname='"+linkName+"' and archived_status='0'";
		 	
		 	
		 	ResultSet rs3=ad.selectQuery(sql2);
		 	
		 	NewsAndMediaForm newsMediaForm1=null;
		 	
		 	ArrayList a2=new ArrayList();
		 	while (rs3.next()) {
		 		newsMediaForm1=new NewsAndMediaForm();
		 		
		 		
		 		System.out.println("Getting Icon Name is ****************"+rs3.getString("icon_name"));
		 		newsMediaForm1.setLinkId(rs3.getString("id"));
		 		newsMediaForm1.setLinkTitle(rs3.getString("link_name"));
		 		newsMediaForm1.setImageName(rs3.getString("icon_name"));
		 		a2.add(newsMediaForm1);
			}
		 	
		 	
		 	session.setAttribute("SUBLINKS", finalLnkdList);
		 	request.setAttribute("subLinkDetails", a2);
				
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("MenuIcon", module);
		
		return mapping.findForward("displayCMS");
	}
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
EssDao ad=EssDao.dBConnection();
		
		String linkName=request.getParameter("sId"); 	
		String module=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		try{
			
			//LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			 	
		 	
		 	
		 	
		 	ArrayList a2=new ArrayList();
		 
		 	
		 	
		 	request.setAttribute("subLinkDetails", a2);
				
			 	// session.setAttribute("SUBLINKS", finalLnkdList);
				
			 	 
			 	ArrayList accountGroupList=new ArrayList();
				ArrayList accountGroupLabelList=new ArrayList();
			 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		while(rs.next()) {
			accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
			accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
		}
		vendorMasterRequestForm.setAccountGroupList(accountGroupList);
		vendorMasterRequestForm.setAccountGroupLabelList(accountGroupLabelList);
			 	 
			 	 
		ResultSet rs9 = ad.selectQuery("select * from Country order by LAND1 ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LAND1")+" - "+rs9.getString("LANDX"));
		}
		vendorMasterRequestForm.setCountryList(countryList);
		vendorMasterRequestForm.setCountryLabelList(countryLabelList);
		
		
		ResultSet rs5 = ad.selectQuery("select WAERS,ISOCD from Currency order by ISOCD");
		ArrayList currencyList=new ArrayList();
		ArrayList currencyLabelList=new ArrayList();
	
		while(rs5.next()) {
			currencyList.add(rs5.getString("WAERS"));
			currencyLabelList.add(rs5.getString("WAERS")+"-"+rs5.getString("ISOCD"));
		}
		vendorMasterRequestForm.setCurrencyList(currencyList);
		vendorMasterRequestForm.setCurrencyLabelList(currencyLabelList);
		
		
		ResultSet rs6 = ad.selectQuery("select RECONCILIATION_ACCOUNT_ID," +
				"RECONCILIATION_ACCOUNT_NAME from RECONCILIATION_ACCOUNT_M where ACTIVE='True' order by RECONCILIATION_ACCOUNT_ID");
		ArrayList reConcillationList=new ArrayList();
		ArrayList reConcillationLabelList=new ArrayList();
		
		
		while(rs6.next()) {
			reConcillationList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID"));
			reConcillationLabelList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID")+"-"+rs6.getString("RECONCILIATION_ACCOUNT_NAME"));
		}
		vendorMasterRequestForm.setReConcillationList(reConcillationList);
		vendorMasterRequestForm.setReConcillationLabelList(reConcillationLabelList);
		
		
		ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
		"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
		ArrayList paymentTermList=new ArrayList();
		ArrayList paymentTermLabelList=new ArrayList();
		
		while(rs7.next()) {
			paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
			paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
		}
		vendorMasterRequestForm.setPaymentTermList(paymentTermList);
		vendorMasterRequestForm.setPaymentTermLabelList(paymentTermLabelList);
		
		
		ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
		"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
		ArrayList accountClerkList=new ArrayList();
		ArrayList accountClerkLabelList=new ArrayList();
		
		while(rs8.next()) {
			accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
			accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
		}
		vendorMasterRequestForm.setAccountClerkList(accountClerkList);
		vendorMasterRequestForm.setAccountClerkLabelList(accountClerkLabelList);
		
		
		ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		while(rs11.next()) {
			locationList.add(rs11.getString("LOCID"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		vendorMasterRequestForm.setLocationIdList(locationList);
		vendorMasterRequestForm.setLocationLabelList(locationLabelList);
		
		
		
		ResultSet rs4 = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
		ArrayList tdsIdList=new ArrayList();
		ArrayList tdsLabelList=new ArrayList();
		
		while(rs4.next()) {
			tdsIdList.add(rs4.getString("TDS_SECTION_ID"));
			tdsLabelList.add(rs4.getString("TDS_SECTION_DESC"));
		}
		vendorMasterRequestForm.setTdsIdList(tdsIdList);
		vendorMasterRequestForm.setTdsLabelList(tdsLabelList);
		
		ArrayList venodreCode=new ArrayList();
		ArrayList vendorType=new ArrayList();
		ResultSet rsVendorType = ad.selectQuery("select V_CODE,V_LTXT from VENDOR_TYPE where active='True' ");
		while(rsVendorType.next()) {
			venodreCode.add(rsVendorType.getString("V_CODE"));
			vendorType.add(rsVendorType.getString("V_LTXT"));
		}
		//vendorMasterRequestForm.setVenodreCode(venodreCode);
		//vendorMasterRequestForm.setVendorType(vendorType);
		
		vendorMasterRequestForm.setRequestDate(EMicroUtils.getCurrentSysDate());
		
		
		
		

			
			//LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
		/*
			String sql3="select id from links where module='"+module+"'";
			
			ResultSet rs2=ad.selectQuery(sql3);
			
			String inLinks="";
			while (rs2.next()) {
				inLinks+=rs2.getString("id")+",";
			}
			System.out.println("inlinks="+inLinks);
			
			String sql11="select * from links where link_name='"+linkName+"' and module='"+module+"'";
			
			ResultSet rs12=ad.selectQuery(sql11);
			
			try{
				while(rs12.next()) {
					 
					vendorMasterRequestForm.setContentDescription(rs12.getString("content_description"));
					vendorMasterRequestForm.setFileFullPath(rs12.getString("file_name"));
					vendorMasterRequestForm.setVideoFullPath(rs12.getString("video_name"));
				}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
		inLinks=inLinks.substring(0, inLinks.lastIndexOf(","));
			System.out.println("inlinks="+inLinks);
			
			String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
			    list.add(s);  
			}  
			
			System.out.println("Getting a list is ***********************"+list);*/
			
			
			/*String userLinks=user.getIncludeSubLinks()+","+user.getIncludeSubSubLinks();
			
			String[] a1=userLinks.split(",");
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
			    list1.add(s);  
			}  
		    
			System.out.println("Getting a list1 is ***********************"+list1);
			
			
		//	Collection listOne = list;
	        Collection listTwo = list1;
	        
	       // listTwo.retainAll(listOne);
	        System.out.println("Getting a listTwo is ***********************"+listTwo);
			
			//list.removeAll(list1);
			
			String inkLinks="";*/
			
			
			/*String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
					" from links  " +
					" where module='ESS'" +
					" and id in(13,14,18,19,59,60,61,62,63,64,65,66,67,68,69,70,71,86) order by priority";
		*/	
			
			/*String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname," +
					"icon_name " +
			" from links  " +
			" where module='ESS'" +
			" and id in(13,14,18,19,59,60,61,62,63,64,65,66,67,68,69,70,71,86,87,88,89) order by priority";
			
			
			ResultSet rs1=ad.selectQuery(sql1);
				
				
			//select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as 
			// url,module,link_name,sub_linkname  from links   where module ='News And Media' and id 
			//in(38,39,40,45,46) order by id	
			
			//LinkedHashMap hm=new LinkedHashMap();	
				
			
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<LinksForm> allLinkList=new LinkedList<LinksForm>();
		 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= new LinkedHashMap<LinksForm,LinkedList<LinksForm>>();
		 	
			while(rs1.next()){
				
				System.out.println("rs1.getString(sub_linkname) **************"+rs1.getString("sub_linkname"));
				mainMenuSet.add(rs1.getString("sub_linkname"));
				LinksForm l=new LinksForm();
					l.setLinkPath(rs1.getString("url"));
					l.setPriority(""+rs1.getString("priority"));
					l.setSubLinkName(rs1.getString("sub_linkname"));
					l.setLinkName(rs1.getString("link_name"));
					l.setIconName(rs1.getString("icon_name"));
				allLinkList.add(l);
			}	
		 		
				
			 	for(String main:mainMenuSet){
			 		LinkedList<LinksForm> links=new LinkedList<LinksForm>();
			 		LinksForm temp=null;
			 		System.out.println(" -- "+main+"");
			 		for(LinksForm aa:allLinkList){
			 				for(LinksForm sl:allLinkList)
			 				{
			 				System.out.println("("+sl.getLinkName()+" -- "+sl.getSubLinkName()+") -- "+main+"");
			 						
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 		for(LinksForm g:links) System.out.println(g.getLinkName());
	 				
	 				System.out.println(main+"---temp lnk nmae--"+temp.getLinkName());
	 				finalLnkdList.put(temp, links);
			 	}
			 	
			 	vendorMasterRequestForm.setLinkName(linkName);
			 	
			 	
			 	System.out.println("finalLnkdList.size--"+finalLnkdList.size()+"\n\n\n\n\n");
		 	
		 	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
		 	for (Map.Entry<LinksForm,LinkedList<LinksForm>> entry : finalLnkdList.entrySet()) {
		 	    System.out.println("Key = " + entry.getKey().getLinkName() +"" +
		 	    		"Link Path is"+ entry.getKey().getLinkPath()+"Get Image "+ entry.getKey().getIconName()+ ", Value = " + entry.getValue().size()+"");
		 	    for (LinksForm lin : entry.getValue()) {
		 	    
					System.out.println("--->"+lin.getLinkName());
				}
		 	}*/
		 	
	
		//set Fields Are Empty 	
		 	vendorMasterRequestForm.setTypeDetails("Save");
		 	String getReqestNumber="select max(REQUEST_NO)  from vendor_master_m";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			
			vendorMasterRequestForm.setRequestNo(maxReqno);
			vendorMasterRequestForm.setPurchaseView("");
		

		 vendorMasterRequestForm.setAccountView("");
		
		vendorMasterRequestForm.setAccountGroupId("");
		vendorMasterRequestForm.setCountry("");
		vendorMasterRequestForm.setState("");
		vendorMasterRequestForm.setTypeOfVendor("");
			 
			vendorMasterRequestForm.setTitle("");
		
		 vendorMasterRequestForm.setName("");
		 vendorMasterRequestForm.setAddress1("");
		vendorMasterRequestForm.setAddress2("");
		 vendorMasterRequestForm.setAddress3("");
		 vendorMasterRequestForm.setAddress4("");
		vendorMasterRequestForm.setCity("");
		 vendorMasterRequestForm.setPinCode("");
		
	
		vendorMasterRequestForm.setLandLineNo("");
		 vendorMasterRequestForm.setMobileNo("");
		 vendorMasterRequestForm.setFaxNo("");
		 vendorMasterRequestForm.setEmailId("");
		 vendorMasterRequestForm.setCurrencyId("");
		 vendorMasterRequestForm.setReConcillationActId("");
			vendorMasterRequestForm.setElgTds("");
		 vendorMasterRequestForm.setTdsCode("");
		 vendorMasterRequestForm.setLstNo("");
		 vendorMasterRequestForm.setTinNo("");
		vendorMasterRequestForm.setCstNo("");
		 vendorMasterRequestForm.setPanNo("");
		 vendorMasterRequestForm.setServiceTaxRegNo("");
			 vendorMasterRequestForm.setRegExciseVendor("");
			
				 vendorMasterRequestForm.setRegExciseVendor("");
		 vendorMasterRequestForm.setEccNo("");
		 vendorMasterRequestForm.setExciseRegNo("");
		 vendorMasterRequestForm.setExciseRange("");
		 vendorMasterRequestForm.setCommissionerate("");
		 vendorMasterRequestForm.setExciseDivision("");
		 vendorMasterRequestForm.setPaymentTermId("");
		 vendorMasterRequestForm.setAccountClerkId("");
		 vendorMasterRequestForm.setIsApprovedVendor("");
		 	//session.setAttribute("SUBLINKS", finalLnkdList);
		 	request.setAttribute("subLinkDetails", a2);
		
		}catch(SQLException se){
			se.printStackTrace();
		}
	
		ArrayList list =new ArrayList();
		
		request.setAttribute("MenuIcon", module);
		
		return mapping.findForward("display");
		
		
	}
	
	
	public ActionForward displayCMS1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		
		String linkName="SAP Masters Request"; 
		
		String module="ESS"; 		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{
			
			
/*			String sql11="select * from links where link_name='"+linkName+"' and module='"+module+"' " +
					"and sub_linkname=link_name";
			
			ResultSet rs11=ad.selectQuery(sql11);
			
				while(rs11.next()) {
					vendorMasterRequestForm.setContentDescription(rs11.getString("content_description"));
					vendorMasterRequestForm.setFileFullPath(rs11.getString("file_name"));
					vendorMasterRequestForm.setVideoFullPath(rs11.getString("video_name"));
				}*/
			
			
			
			String contentDescription="";
			String uplodedfiles="";
			String uplodedvideos="";
			String getCMSContent="select * from archieves where link_name='"+linkName+"' and module='"+module+"' and sub_link is null and status='null'";
			ResultSet rsCMSContent=ad.selectQuery(getCMSContent);
			
			
				while(rsCMSContent.next()) {
					 
					vendorMasterRequestForm.setContentDescription(rsCMSContent.getString("content_description"));
					vendorMasterRequestForm.setFileFullPath(rsCMSContent.getString("file_name"));
					vendorMasterRequestForm.setVideoFullPath(rsCMSContent.getString("video_name"));
					 contentDescription=rsCMSContent.getString("content_description");
						uplodedfiles=rsCMSContent.getString("file_name");
						uplodedvideos=rsCMSContent.getString("video_name");
				}
				if(contentDescription.equalsIgnoreCase(""))
				{
					vendorMasterRequestForm.setContentDescription("No Content is available");
					
				}
				
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					vendorMasterRequestForm.setFileFullPath("");
				}
				if(uplodedvideos.equalsIgnoreCase(""))
				{
					vendorMasterRequestForm.setVideoFullPath("");
				}	
			
			
			vendorMasterRequestForm.setLinkName(linkName);
		 	
		 	session.setAttribute("SUBLINKS", session.getAttribute("SUBLINKS"));
				
		}catch(Exception se){
			se.printStackTrace();
		}
		
		
		request.setAttribute("MenuIcon", module);
		
		return mapping.findForward("displayCMS1");
	}
	
	public ActionForward displayState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		
		String linkName=request.getParameter("sId"); 	
		String module=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{
		
		if(vendorMasterRequestForm.getCountry()!=null){
			
		if(!vendorMasterRequestForm.getCountry().equalsIgnoreCase("")){
		ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+vendorMasterRequestForm.getCountry()+"'");
		ArrayList stateList=new ArrayList();
		ArrayList stateLabelList=new ArrayList();
		
		while(rs4.next()) {
			stateList.add(rs4.getString("BLAND"));
			stateLabelList.add(rs4.getString("BEZEI"));
		}
		vendorMasterRequestForm.setStateList(stateList);
		vendorMasterRequestForm.setStateLabelList(stateLabelList);
		
		
		request.setAttribute("States", "States");
		
		}
		}
		
		
		
		if(vendorMasterRequestForm.getTdsCode()!=null){
			
			
			if(!vendorMasterRequestForm.getTdsCode().equalsIgnoreCase("YES")){
			ResultSet rs4 = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
			ArrayList tdsIdList=new ArrayList();
			ArrayList tdsLabelList=new ArrayList();
			
			while(rs4.next()) {
				tdsIdList.add(rs4.getString("TDS_SECTION_ID"));
				tdsLabelList.add(rs4.getString("TDS_SECTION_DESC"));
			}
			vendorMasterRequestForm.setTdsIdList(tdsIdList);
			vendorMasterRequestForm.setTdsLabelList(tdsLabelList);
			
			
			request.setAttribute("TdsCode", "TdsCode");
			}
			}
if(vendorMasterRequestForm.getRegExciseVendor()!=null){
			
			
			if(vendorMasterRequestForm.getRegExciseVendor().equalsIgnoreCase("True")){
			
			
			
			request.setAttribute("RegExciseVendor", "RegExciseVendor");
			}
			}
		
		
		
		
		
		
		
		String country=vendorMasterRequestForm.getCountry();
		vendorMasterRequestForm.setCountry(country);
		
		ArrayList accountGroupList=new ArrayList();
		ArrayList accountGroupLabelList=new ArrayList();
	 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
ArrayList subLinkIdList = new ArrayList();
ArrayList subLinkValueList = new ArrayList();
while(rs.next()) {
	accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
	accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
}
vendorMasterRequestForm.setAccountGroupList(accountGroupList);
vendorMasterRequestForm.setAccountGroupLabelList(accountGroupLabelList);
	 	 
	 	 
ResultSet rs9 = ad.selectQuery("select * from Country");
ArrayList countryList=new ArrayList();
ArrayList countryLabelList=new ArrayList();

while(rs9.next()) {
	countryList.add(rs9.getString("LAND1"));
	countryLabelList.add(rs9.getString("LAND1")+" - "+rs9.getString("LANDX"));
}
vendorMasterRequestForm.setCountryList(countryList);
vendorMasterRequestForm.setCountryLabelList(countryLabelList);


ResultSet rs5 = ad.selectQuery("select WAERS,ISOCD from Currency order by ISOCD");
ArrayList currencyList=new ArrayList();
ArrayList currencyLabelList=new ArrayList();

while(rs5.next()) {
	currencyList.add(rs5.getString("WAERS"));
	currencyLabelList.add(rs5.getString("WAERS")+"-"+rs5.getString("ISOCD"));
}
vendorMasterRequestForm.setCurrencyList(currencyList);
vendorMasterRequestForm.setCurrencyLabelList(currencyLabelList);


ResultSet rs6 = ad.selectQuery("select RECONCILIATION_ACCOUNT_ID," +
		"RECONCILIATION_ACCOUNT_NAME from RECONCILIATION_ACCOUNT_M where ACTIVE='True' order by RECONCILIATION_ACCOUNT_NAME");
ArrayList reConcillationList=new ArrayList();
ArrayList reConcillationLabelList=new ArrayList();


while(rs6.next()) {
	reConcillationList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID"));
	reConcillationLabelList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID")+"-"+rs6.getString("RECONCILIATION_ACCOUNT_NAME"));
}
vendorMasterRequestForm.setReConcillationList(reConcillationList);
vendorMasterRequestForm.setReConcillationLabelList(reConcillationLabelList);


ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
ArrayList paymentTermList=new ArrayList();
ArrayList paymentTermLabelList=new ArrayList();

while(rs7.next()) {
	paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
	paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
}
vendorMasterRequestForm.setPaymentTermList(paymentTermList);
vendorMasterRequestForm.setPaymentTermLabelList(paymentTermLabelList);


ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
ArrayList accountClerkList=new ArrayList();
ArrayList accountClerkLabelList=new ArrayList();

while(rs8.next()) {
	accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
	accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
}
vendorMasterRequestForm.setAccountClerkList(accountClerkList);
vendorMasterRequestForm.setAccountClerkLabelList(accountClerkLabelList);


ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location");
ArrayList locationList=new ArrayList();
ArrayList locationLabelList=new ArrayList();

while(rs11.next()) {
	locationList.add(rs11.getString("LOCID"));
	locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
}
vendorMasterRequestForm.setLocationIdList(locationList);
vendorMasterRequestForm.setLocationLabelList(locationLabelList);



ResultSet rs4 = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
ArrayList tdsIdList=new ArrayList();
ArrayList tdsLabelList=new ArrayList();

while(rs4.next()) {
	tdsIdList.add(rs4.getString("TDS_SECTION_ID"));
	tdsLabelList.add(rs4.getString("TDS_SECTION_DESC"));
}
vendorMasterRequestForm.setTdsIdList(tdsIdList);
vendorMasterRequestForm.setTdsLabelList(tdsLabelList);

String typeDetails=vendorMasterRequestForm.getTypeDetails();
vendorMasterRequestForm.setTypeDetails(typeDetails);


ArrayList venodreCode=new ArrayList();
ArrayList vendorType=new ArrayList();
ResultSet rsVendorType = ad.selectQuery("select V_CODE,V_LTXT from VENDOR_TYPE where active='True' ");
while(rsVendorType.next()) {
	venodreCode.add(rsVendorType.getString("V_CODE"));
	vendorType.add(rsVendorType.getString("V_LTXT"));
}

		}catch(SQLException se){
			se.printStackTrace();
		}
		
		String sql1="select * from temp_vendor_documents where request_no='"+vendorMasterRequestForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"'";
		ResultSet rs=ad.selectQuery(sql1);
		try{
			VendorMasterRequestForm vendorMasterRequestForm1=null;
			ArrayList a1=new ArrayList();
			while(rs.next()) {
				vendorMasterRequestForm1=new VendorMasterRequestForm();
				vendorMasterRequestForm1.setFileName(rs.getString("file_name"));
				vendorMasterRequestForm1.setFileId(rs.getString("file_name"));
				vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rs.getString("file_name")+"");
				a1.add(vendorMasterRequestForm1);
			}
			if(a1.size()>0){
			request.setAttribute("documentDetails", a1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//display(mapping, form, request, response);
		return mapping.findForward("display");
	}
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
			EssDao ad=EssDao.dBConnection();
			FormFile vendorAttachmentFile=vendorMasterRequestForm.getVendorAttachments();
			String attchmentName=vendorAttachmentFile.getFileName();
			int requestNo=vendorMasterRequestForm.getRequestNo();
			
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			
			 String ext = attchmentName.substring(attchmentName.lastIndexOf('.') + 1);
				int filesize=vendorAttachmentFile.getFileSize();
			
			if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
			{
			
			try{
			
			 byte[] size=vendorAttachmentFile.getFileData();
			 String extension="";
			 if(!attchmentName.equalsIgnoreCase("")){
			 int length=attchmentName.length();
		     	 
		     int dot=attchmentName.lastIndexOf(".");
		     extension=attchmentName.substring(dot, length);
		     
		     
		        
		     String documentPath="jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/";
		        
		     String fileName=documentPath+attchmentName;
		     System.out.println("Getting File name is ");
			 
		     String checkFileCount="select count(*) from temp_vendor_documents where file_name='"+attchmentName+"'";
		     int count=0;
		     ResultSet rsCheckFile=ad.selectQuery(checkFileCount);
		     while(rsCheckFile.next())
		     {
		    	 count=rsCheckFile.getInt(1);
		     }
		     if(count==0)
		     {
		    	 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	 String filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp";
				 	 
				 	File destinationDir = new File(filePath);
					if(!destinationDir.exists())
					{
						destinationDir.mkdirs();
					}
			 	
	 	     String filepath = uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+vendorAttachmentFile.getFileName();
			 System.out.println("File Path is **************"+filepath);
			 	
			 	
			 File imageFile=new File(filepath);
			 FileOutputStream outputStream=new FileOutputStream(imageFile);
			 outputStream.write(size);
			 outputStream.flush();
			 outputStream.close();
			 request.setAttribute("submitDetails", "submitDetails");
			 
			//upload files in another path
				
				try{
					String filePath1 = "E:/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp";
					
					byte[] fileData1 = vendorAttachmentFile.getFileData();
					
					
					File destinationDir1 = new File(filePath1);
					if(!destinationDir1.exists())
					{
						destinationDir1.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate1 = new File(filePath1, attchmentName);
						if (!fileToCreate1.exists()) {
							FileOutputStream fileOutStream1 = new FileOutputStream(
									fileToCreate1);
							fileOutStream1.write(vendorAttachmentFile.getFileData());
							fileOutStream1.flush();
							fileOutStream1.close();
						}
					}
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			 
			 ArrayList venodreCode=new ArrayList();
				ArrayList vendorType=new ArrayList();
				ResultSet rsVendorType = ad.selectQuery("select V_CODE,V_LTXT from VENDOR_TYPE where active='True' ");
				while(rsVendorType.next()) {
					venodreCode.add(rsVendorType.getString("V_CODE"));
					vendorType.add(rsVendorType.getString("V_LTXT"));
				}
			 
		
			 
				
			
			
			
		
			String sql="insert into temp_vendor_documents(request_no,file_name,userId)" +
					"values('"+requestNo+"','"+vendorAttachmentFile.getFileName()+"','"+user.getEmployeeNo()+"')";
			
			int a=ad.SqlExecuteUpdate(sql);

			if(a>0){
				vendorMasterRequestForm.setMessage2("Documents Uploaded Successfully");
				vendorMasterRequestForm.setMessage("");
			}
			 }else{
				 vendorMasterRequestForm.setMessage("Error..File is already exist.Please change file name.");
					vendorMasterRequestForm.setMessage2("");
			 }
			}else{
				vendorMasterRequestForm.setMessage("Error..Please Upload Atleast One File.");
				vendorMasterRequestForm.setMessage2("");
			}
			}catch(FileNotFoundException fe){
				fe.printStackTrace();
			}
			
			catch(IOException ie){
				ie.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			}else{
				 vendorMasterRequestForm.setMessage2("");
				 vendorMasterRequestForm.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
				request.setAttribute("vendorfile", "vendorfile")	;	
			}
			
			String sql1="select * from temp_vendor_documents where request_no='"+requestNo+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rs=ad.selectQuery(sql1);
			try{
				VendorMasterRequestForm vendorMasterRequestForm1=null;
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					vendorMasterRequestForm1=new VendorMasterRequestForm();
					vendorMasterRequestForm1.setFileName(rs.getString("file_name"));
					vendorMasterRequestForm1.setFileId(rs.getString("file_name"));
					vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rs.getString("file_name")+"");
					a1.add(vendorMasterRequestForm1);
				}
				request.setAttribute("documentDetails", a1);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String typeDetails=vendorMasterRequestForm.getTypeDetails();
			vendorMasterRequestForm.setTypeDetails(typeDetails);
			return mapping.findForward("display");
			
			}
	
	
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		 EssDao ad=EssDao.dBConnection();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
		 try{
		
		 int requestNo=vendorMasterRequestForm.getRequestNo();
		 Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
	String dateNow = ft.format(dNow);
	 String accountGroupId=vendorMasterRequestForm.getAccountGroupId();
	  int totalcount=0;
	  if(!accountGroupId.equalsIgnoreCase("4"))
	  {
    String getVendorFileCount="select count(*) from temp_vendor_documents where request_no='"+requestNo+"' and userId='"+user.getEmployeeNo()+"'";
	 ResultSet rsVendorFile=ad.selectQuery(getVendorFileCount);

	 while(rsVendorFile.next())
	 {
		 totalcount=rsVendorFile.getInt(1);
	 }
	  }
	  else
	  {
		 totalcount=1;
	  }
		 if(totalcount>0)
		 {
		 FormFile vendorAttachmentFile=vendorMasterRequestForm.getVendorAttachments();
		 String attchmentName=vendorAttachmentFile.getFileName();
	 	
	 	 
	      accountGroupId=vendorMasterRequestForm.getAccountGroupId();
		 String requestDate=vendorMasterRequestForm.getRequestDate();
		 String b1[]=requestDate.split("/");
		 requestDate=b1[2]+"-"+b1[1]+"-"+b1[0];
		 String purchaseView=null;
		 String accountView=null;
		 purchaseView=request.getParameter("purchase");
		 
		 accountView=request.getParameter("accountView");
		 
		 
		
		 
		 if(purchaseView.equalsIgnoreCase("true") && accountView.equalsIgnoreCase("true")){
		 	 purchaseView="3";
		 }else{
			 if(purchaseView.equalsIgnoreCase("true")){
			 	 purchaseView="1";
			 }
			 if(accountView.equalsIgnoreCase("true")){
			 	 purchaseView="2";
			 }
		 }
		 
		 
		 String name=vendorMasterRequestForm.getName();
		 String address1=vendorMasterRequestForm.getAddress1();
		 String address2=vendorMasterRequestForm.getAddress2();
		 String address3=vendorMasterRequestForm.getAddress3();
		 String address4=vendorMasterRequestForm.getAddress4();
		 String city=vendorMasterRequestForm.getCity();
		 String pinCode=vendorMasterRequestForm.getPinCode();
		 String country=vendorMasterRequestForm.getCountry();
		 String state=vendorMasterRequestForm.getState();
		 String landLineNo=vendorMasterRequestForm.getLandLineNo();
		 String mobileNo=vendorMasterRequestForm.getMobileNo();
		 String faxNo=vendorMasterRequestForm.getFaxNo();
		 String emailId=vendorMasterRequestForm.getEmailId();
		 String currencyId=vendorMasterRequestForm.getCurrencyId();
		 String reConcillationActId=vendorMasterRequestForm.getReConcillationActId();
		 String elgTds=vendorMasterRequestForm.getElgTds();
		 String tdsCode=vendorMasterRequestForm.getTdsCode();
		 String lstNo=vendorMasterRequestForm.getLstNo();
		 String tinNo=vendorMasterRequestForm.getTinNo();
		 String cstNo=vendorMasterRequestForm.getCstNo();
		 String panNo=vendorMasterRequestForm.getPanNo();
		 String serviceTaxRegNo=vendorMasterRequestForm.getServiceTaxRegNo();
		 String regExciseVendor=vendorMasterRequestForm.getRegExciseVendor();
		 String eccNo=vendorMasterRequestForm.getEccNo();
		 String exciseRegNo=vendorMasterRequestForm.getExciseRegNo();
		 String exciseRange=vendorMasterRequestForm.getExciseRange();
		 String exciseDivision=vendorMasterRequestForm.getExciseDivision();
		 String paymentTermId=vendorMasterRequestForm.getPaymentTermId();
		 String accountClerkId=vendorMasterRequestForm.getAccountClerkId();
		 String isApprovedVendor=vendorMasterRequestForm.getIsApprovedVendor();
		 
		 String typeDetails=vendorMasterRequestForm.getTypeDetails();
		 
		
		 
		 if(typeDetails.equalsIgnoreCase("Save"))
		 {
		 
			 int count=0;
				
			 String getCount="select count(*) from vendor_master_m where REQUEST_NO='"+requestNo+"'";
			 ResultSet rsCount=ad.selectQuery(getCount);
			 while(rsCount.next())
			 {
				count=rsCount.getInt(1);
			 }
			 int insert=0;
			 String approver="";
				String lApprover="";
				String approvermail="";
				String pendingApprovers="";
				
				/*String getApproverID="select * from Approvers_Details where Type like '%Vendor Master%'";
				
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
							pendingApprovers = pendingApprovers + "," +approverRS.getString("Employee_Name");
						}
						
				}*/
			 if(count>0){
				 //Request no is available
					 String getReqestNumber="select max(REQUEST_NO)  from vendor_master_m";
						int maxReqno=0;
						ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
						while(rsReqestNumber.next())
						{
							maxReqno=rsReqestNumber.getInt(1);
						}
						maxReqno+=1;
						
						vendorMasterRequestForm.setRequestNo(maxReqno);
				 
			 String sql="insert into vendor_master_m(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID" +
			 		",VIEW_TYPE,TITLE,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE"+
			 		",LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID"+
			 		",IS_ELIGIBLE_FOR_TDS,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No," +
			 		"IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division," +
			 		"PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status," +
			 		"REQUESTED_BY,GSTIN_Number)"+
			 		"values('"+vendorMasterRequestForm.getRequestNo()+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
			 		"'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"'," +
			 		"'"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
			 		"'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"'," +
			 		"'"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
			 		"'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"'," +
			 		"'"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','"+vendorMasterRequestForm.getGstinNo()+"')";
					 	
			 insert= ad.SqlExecuteUpdate(sql);
			 
			 String updateTempVendorDoc="update temp_vendor_documents set request_no='"+vendorMasterRequestForm.getRequestNo()+"' where request_no='"+requestNo+"'";
			 ad.SqlExecuteUpdate(updateTempVendorDoc);
			 
			 
			 
			 if(insert>0){
				     vendorMasterRequestForm.setMessage2(" Vendor Code creation request saved with request number='"+vendorMasterRequestForm.getRequestNo()+"'");
				 	vendorMasterRequestForm.setMessage("");
				 	
					String deleteHistory="delete vendor_master_history where REQUEST_NO='"+vendorMasterRequestForm.getRequestNo()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
					ad.SqlExecuteUpdate(deleteHistory);			
				
					 String saveInHistory="insert into vendor_master_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID,VIEW_TYPE,TITLE,NAME,ADDRESS1,"
					 + "ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID,IS_ELIGIBLE_FOR_TDS,"
					 + "TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division,PAYMENT_TERM_ID,"
					 + "ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,REQUESTED_BY,role,GSTIN_Number)"+
			 		"values('"+vendorMasterRequestForm.getRequestNo()+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
			 		"'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"','"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
			 		"'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"','"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
			 		"'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"','"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"',"
			 		+ "'"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','user','"+vendorMasterRequestForm.getGstinNo()+"')";
					 	ad.SqlExecuteUpdate(saveInHistory);
				     display(mapping, form, request, response);
				     
				 }else{
					 vendorMasterRequestForm.setTypeDetails("Update");
				     
					  ArrayList listOfFiles=new ArrayList();
							String getUploadedFiles="select * from temp_vendor_documents where request_no='"+vendorMasterRequestForm.getRequestNo()+"' and  userId ='"+user.getEmployeeNo()+"' ";
							ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
							while(rsUploadFile.next())
							{
								VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
								vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
								vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
								vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
								listOfFiles.add(vendorMasterRequestForm1);
							}
							request.setAttribute("documentDetails", listOfFiles);
				 }
			 
			 }else{
				 
				 String  sql="insert into vendor_master_m(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID" +
			 		",VIEW_TYPE,TITLE,NAME,ADDRESS1,ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE"+
			 		",LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID"+
			 		",IS_ELIGIBLE_FOR_TDS,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No," +
			 		"IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division," +
			 		"PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,last_approver,pending_approver,REQUESTED_BY,GSTIN_Number)"+
			 		"values('"+requestNo+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
			 		"'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"'," +
			 		"'"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
			 		"'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"'," +
			 		"'"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
			 		"'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"'," +
			 		"'"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"','"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','No','"+pendingApprovers+"','"+user.getEmployeeNo()+"','"+vendorMasterRequestForm.getGstinNo()+"')";
					 	
				 insert= ad.SqlExecuteUpdate(sql);
			 
			 
			
			 
			 if(insert>0){
				     vendorMasterRequestForm.setMessage2("Vendor Code creation request created with request number='"+requestNo+"'"); 
				 	vendorMasterRequestForm.setMessage("");
					String deleteHistory="delete vendor_master_history where REQUEST_NO='"+requestNo+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
					ad.SqlExecuteUpdate(deleteHistory);			
				
					 String saveInHistory="insert into vendor_master_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID,VIEW_TYPE,TITLE,NAME,ADDRESS1,"
					 + "ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID,IS_ELIGIBLE_FOR_TDS,"
					 + "TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division,PAYMENT_TERM_ID,"
					 + "ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,REQUESTED_BY,role,GSTIN_Number)"+
			 		"values('"+requestNo+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
			 		"'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"','"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
			 		"'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"','"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
			 		"'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"','"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"',"
			 		+ "'"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','user','"+vendorMasterRequestForm.getGstinNo()+"')";
					 	ad.SqlExecuteUpdate(saveInHistory);
				     display(mapping, form, request, response);
				 
			 }else{
				 
				 vendorMasterRequestForm.setTypeDetails("Update");
			     
				  ArrayList listOfFiles=new ArrayList();
						String getUploadedFiles="select * from temp_vendor_documents where request_no='"+vendorMasterRequestForm.getRequestNo()+"' and  userId ='"+user.getEmployeeNo()+"' ";
						ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
						while(rsUploadFile.next())
						{
							VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
							vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
							vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
							vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
							listOfFiles.add(vendorMasterRequestForm1);
						}
						request.setAttribute("documentDetails", listOfFiles);
			 }
			 
			 }
			/* //Send Mail To First Approver 
			 if(insert > 0){
				 String Req_Id = ""+vendorMasterRequestForm.getRequestNo();
				 EMailer email = new EMailer();
				 int i = email.sendMailToApprover(request, approvermail,Req_Id,"Vendor Master");
				 if(i > 0){
					 vendorMasterRequestForm.setMessage("Requested Submitted Successfully");
					}
			 }*/
			 
		 
		 }else{
			System.out.println("update vendor Master");
			
			String recordStatus="";
			String getRecordStatus="select Approve_Status from vendor_master_m where REQUEST_NO='"+requestNo+"' ";
			ResultSet rsRecord=ad.selectQuery(getRecordStatus);
			while(rsRecord.next())
			{
				recordStatus=rsRecord.getString("Approve_Status");
			}
			if(recordStatus.equalsIgnoreCase("Rejected"))
			{
				
				String deleteRecords="delete from All_Request where Req_Id='"+requestNo+"' and Req_Type='Vendor Master'";
				int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
				System.out.println("deleteStatus="+deleteStatus);
				
				String updateFlag="update vendor_master_m set rejected_flag='yes' where REQUEST_NO='"+requestNo+"'";
				ad.SqlExecuteUpdate(updateFlag);
			}
		String approvedStatus="Pending";
		
			if(user.getId()==1)
			{
				approvedStatus=vendorMasterRequestForm.getApproveType();
			}
			
			String updateVendorMaster="update vendor_master_m  set REQUEST_DATE='"+requestDate+"',LOCATION_ID='"+vendorMasterRequestForm.getLocationId()+"',ACCOUNT_GROUP_ID='"+accountGroupId+"'" +
		 		",VIEW_TYPE='"+purchaseView+"',TITLE='"+vendorMasterRequestForm.getTitle()+"',NAME='"+name+"',ADDRESS1='"+address1+"',ADDRESS2='"+address2+"',ADDRESS3='"+address3+"',ADDRESS4='"+address4+"',CITY='"+city+"',PINCODE='"+pinCode+"',COUNTRY_ID='"+country+"',STATE='"+state+"'"+
		 		",LANDLINE_NO='"+landLineNo+"',MOBILE_NO='"+mobileNo+"',FAX_NO='"+faxNo+"',EMAIL_ID='"+emailId+"',CURRENCY_ID='"+currencyId+"',RECONCILATION_ACT_ID='"+reConcillationActId+"'"+
		 		",IS_ELIGIBLE_FOR_TDS='"+elgTds+"',TDS_CODE='"+tdsCode+"',LST_NO='"+lstNo+"',TIN_NO='"+tinNo+"',CST_NO='"+cstNo+"',PAN_No='"+panNo+"',Service_Tax_Registration_No='"+serviceTaxRegNo+"'," +
		 		"IS_REGD_EXCISE_VENDOR='"+regExciseVendor+"',ECC_No='"+eccNo+"',Excise_Reg_No='"+exciseRegNo+"',Excise_Range='"+exciseRange+"',Excise_Division='"+exciseDivision+"'," +
		 		"PAYMENT_TERM_ID='"+paymentTermId+"',ACCOUNT_CLERK_ID='"+accountClerkId+"',IS_APPROVED_VENDOR='"+isApprovedVendor+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',COMMISSIONERATE='"+vendorMasterRequestForm.getCommissionerate()+"',Type_Of_Vendor='"+vendorMasterRequestForm.getTypeOfVendor()+"',Approve_Status='Created',GSTIN_Number='"+vendorMasterRequestForm.getGstinNo()+"'  where REQUEST_NO='"+requestNo+"'";
			
			int status=0;
			status=ad.SqlExecuteUpdate(updateVendorMaster);
			if(status>0){
			     vendorMasterRequestForm.setMessage2("Vendor Details Updated Successfully");
			 	vendorMasterRequestForm.setMessage("");
			 	
			 	String deleteHistory="delete vendor_master_history where REQUEST_NO='"+requestNo+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
				ad.SqlExecuteUpdate(deleteHistory);			
			
				 String saveInHistory="insert into vendor_master_history(REQUEST_NO,REQUEST_DATE,LOCATION_ID,ACCOUNT_GROUP_ID,VIEW_TYPE,TITLE,NAME,ADDRESS1,"
				 + "ADDRESS2,ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,CURRENCY_ID,RECONCILATION_ACT_ID,IS_ELIGIBLE_FOR_TDS,"
				 + "TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No,Excise_Range,Excise_Division,PAYMENT_TERM_ID,"
				 + "ACCOUNT_CLERK_ID,IS_APPROVED_VENDOR,CREATED_DATE,CREATED_BY,COMMISSIONERATE,Type_Of_Vendor,Approve_Status,REQUESTED_BY,role,GSTIN_Number)"+
		 		"values('"+requestNo+"','"+requestDate+"','"+vendorMasterRequestForm.getLocationId()+"','"+accountGroupId+"','"+purchaseView+"','"+vendorMasterRequestForm.getTitle()+"','"+name+"'," +
		 		"'"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+city+"','"+pinCode+"','"+country+"','"+state+"','"+landLineNo+"','"+mobileNo+"','"+faxNo+"','"+emailId+"'," +
		 		"'"+currencyId+"','"+reConcillationActId+"','"+elgTds+"','"+tdsCode+"','"+lstNo+"','"+tinNo+"','"+cstNo+"','"+panNo+"','"+serviceTaxRegNo+"','"+regExciseVendor+"'," +
		 		"'"+eccNo+"','"+exciseRegNo+"','"+exciseRange+"','"+exciseDivision+"','"+paymentTermId+"','"+accountClerkId+"','"+isApprovedVendor+"','"+dateNow+"','"+user.getEmployeeNo()+"',"
		 		+ "'"+vendorMasterRequestForm.getCommissionerate()+"','"+vendorMasterRequestForm.getTypeOfVendor()+"','Created','"+user.getEmployeeNo()+"','user','"+vendorMasterRequestForm.getGstinNo()+"')";
				 	ad.SqlExecuteUpdate(saveInHistory);
			     
			   /*  ArrayList listOfFiles=new ArrayList();
				 String getUploadedFiles="select * from temp_vendor_documents where request_no='"+requestNo+"' and  userId ='"+user.getEmployeeNo()+"'";
					ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
					while(rsUploadFile.next())
					{
						VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
						vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
						vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
						vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
						listOfFiles.add(vendorMasterRequestForm1);
					}
					request.setAttribute("documentDetails", listOfFiles);*/
			     
			     display(mapping, form, request, response);
			    
			     
			}else{
				vendorMasterRequestForm.setMessage("Error..When Updating Vendor Details.");
				vendorMasterRequestForm.setMessage2("");
				
				   ArrayList listOfFiles=new ArrayList();
					 String getUploadedFiles="select * from temp_vendor_documents where request_no='"+requestNo+"' and  userId ='"+user.getEmployeeNo()+"'";
						ResultSet rsUploadFile=ad.selectQuery(getUploadedFiles);
						while(rsUploadFile.next())
						{
							VendorMasterRequestForm	vendorMasterRequestForm1=new VendorMasterRequestForm();
							vendorMasterRequestForm1.setFileName(rsUploadFile.getString("file_name"));
							vendorMasterRequestForm1.setFileId(rsUploadFile.getString("id"));
							vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rsUploadFile.getString("file_name"));
							listOfFiles.add(vendorMasterRequestForm1);
						}
						request.setAttribute("documentDetails", listOfFiles);
						
						 
			}
			
		 }
	}
		 
		 else{
			 vendorMasterRequestForm.setMessage("Error..Please Upload File");
				vendorMasterRequestForm.setMessage2("");
			 String typeDetails=vendorMasterRequestForm.getTypeDetails();
				vendorMasterRequestForm.setTypeDetails(typeDetails);
			 ArrayList accountGroupList=new ArrayList();
				ArrayList accountGroupLabelList=new ArrayList();
			 	ResultSet rs = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		while(rs.next()) {
			accountGroupList.add(rs.getString("ACCOUNT_GROUP_ID"));
			accountGroupLabelList.add(rs.getString("ACCOUNT_GROUP_NAME"));
		}
		vendorMasterRequestForm.setAccountGroupList(accountGroupList);
		vendorMasterRequestForm.setAccountGroupLabelList(accountGroupLabelList);
			 	 
			 	 
		ResultSet rs9 = ad.selectQuery("select * from Country");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LAND1")+" - "+rs9.getString("LANDX"));
		}
		vendorMasterRequestForm.setCountryList(countryList);
		vendorMasterRequestForm.setCountryLabelList(countryLabelList);
		ResultSet rsState = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+vendorMasterRequestForm.getCountry()+"'");
		ArrayList stateList=new ArrayList();
		ArrayList stateLabelList=new ArrayList();
		
		while(rsState.next()) {
			stateList.add(rsState.getString("BLAND"));
			stateLabelList.add(rsState.getString("BEZEI"));
		}
		vendorMasterRequestForm.setStateList(stateList);
		vendorMasterRequestForm.setStateLabelList(stateLabelList);
		
		
		request.setAttribute("States", "States");
		
		ResultSet rs5 = ad.selectQuery("select WAERS,ISOCD from Currency order by ISOCD");
		ArrayList currencyList=new ArrayList();
		ArrayList currencyLabelList=new ArrayList();
	
		while(rs5.next()) {
			currencyList.add(rs5.getString("WAERS"));
			currencyLabelList.add(rs5.getString("WAERS")+"-"+rs5.getString("ISOCD"));
		}
		vendorMasterRequestForm.setCurrencyList(currencyList);
		vendorMasterRequestForm.setCurrencyLabelList(currencyLabelList);
		
		
		ResultSet rs6 = ad.selectQuery("select RECONCILIATION_ACCOUNT_ID," +
				"RECONCILIATION_ACCOUNT_NAME from RECONCILIATION_ACCOUNT_M where ACTIVE='True' order by RECONCILIATION_ACCOUNT_NAME");
		ArrayList reConcillationList=new ArrayList();
		ArrayList reConcillationLabelList=new ArrayList();
		
		
		while(rs6.next()) {
			reConcillationList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID"));
			reConcillationLabelList.add(rs6.getString("RECONCILIATION_ACCOUNT_ID")+"-"+rs6.getString("RECONCILIATION_ACCOUNT_NAME"));
		}
		vendorMasterRequestForm.setReConcillationList(reConcillationList);
		vendorMasterRequestForm.setReConcillationLabelList(reConcillationLabelList);
		
		
		ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
		"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True'");
		ArrayList paymentTermList=new ArrayList();
		ArrayList paymentTermLabelList=new ArrayList();
		
		while(rs7.next()) {
			paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
			paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_NAME"));
		}
		vendorMasterRequestForm.setPaymentTermList(paymentTermList);
		vendorMasterRequestForm.setPaymentTermLabelList(paymentTermLabelList);
		
		
		ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
		"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
		ArrayList accountClerkList=new ArrayList();
		ArrayList accountClerkLabelList=new ArrayList();
		
		while(rs8.next()) {
			accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
			accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
		}
		vendorMasterRequestForm.setAccountClerkList(accountClerkList);
		vendorMasterRequestForm.setAccountClerkLabelList(accountClerkLabelList);
		
		
		ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		while(rs11.next()) {
			locationList.add(rs11.getString("LOCID"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		vendorMasterRequestForm.setLocationIdList(locationList);
		vendorMasterRequestForm.setLocationLabelList(locationLabelList);
		
		
		
		ResultSet rs4 = ad.selectQuery("select TDS_SECTION_ID,TDS_SECTION_DESC from TDS_SECTION_M where ACTIVE='True' ");
		ArrayList tdsIdList=new ArrayList();
		ArrayList tdsLabelList=new ArrayList();
		
		while(rs4.next()) {
			tdsIdList.add(rs4.getString("TDS_SECTION_ID"));
			tdsLabelList.add(rs4.getString("TDS_SECTION_DESC"));
		}
		vendorMasterRequestForm.setTdsIdList(tdsIdList);
		vendorMasterRequestForm.setTdsLabelList(tdsLabelList);
			 
		 }
		 }catch (Exception e) {
			e.printStackTrace();
		}
		// displayVendorMasterList(mapping, form, request, response);
		 String module="ESS"; 
			request.setAttribute("MenuIcon", module);
	return mapping.findForward("display");
		
		
	}
	
	
	public ActionForward fileDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		VendorMasterRequestForm vendorMasterRequestForm=(VendorMasterRequestForm)form;
		EssDao ad=EssDao.dBConnection();
		
		String param=request.getParameter("param");
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		
		String[] documentCheck=vendorMasterRequestForm.getDocumentCheck();
		
		int documentLength=0;
		
		
		try 
	 	{
			documentLength=documentCheck.length;
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		int document=0;
		String documentId="";
		String sql="";
		System.out.println("rejectLength Is ********************"+documentLength);
		String[] departmentId1=null;
		try{
			
			if(documentLength>0)
			{
				
				 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	 String filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp";
				
				for(int i=0;i<documentLength;i++)
				{
					document++;
					documentId=documentCheck[i];
        			
        			
        			System.out.println("requestId Is ********************"+documentId);
        			 
        			 
        			sql="delete from temp_vendor_documents where file_name='"+documentId+"' and request_no='"+vendorMasterRequestForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"' ";
        			 
        			System.out.println("Getting a sql is *************"+sql);
					int c=ad.SqlExecuteUpdate(sql);
					 
					if(c>0){
						 vendorMasterRequestForm.setMessage2("Vendor Document Details Deleted sucessfully");
							vendorMasterRequestForm.setMessage("");
						 	File fileToCreate = new File(filePath, documentId);
    					 	boolean test=fileToCreate.delete();
    					 	
    					 	//delete file in another path
    					 	
    					 	File fileToCreate1 = new File("E:/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp", documentId);
    					 	boolean test1=fileToCreate1.delete();
					}else{
						 vendorMasterRequestForm.setMessage("Error While Deleting Vendor Documents");
							vendorMasterRequestForm.setMessage2("");
					}
					 
				}
			}
			
			
			String sql1="select * from temp_vendor_documents where request_no='"+vendorMasterRequestForm.getRequestNo()+"' and userId='"+user.getEmployeeNo()+"'";
			
			ResultSet rs=ad.selectQuery(sql1);
				VendorMasterRequestForm vendorMasterRequestForm1=null;
				
				ArrayList a1=new ArrayList();
				while(rs.next()) {
					vendorMasterRequestForm1=new VendorMasterRequestForm();
					vendorMasterRequestForm1.setFileName(rs.getString("file_name"));
					vendorMasterRequestForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/vendorDocuments/temp/"+rs.getString("file_name")+"");
					vendorMasterRequestForm1.setFileId(rs.getString("id"));
					a1.add(vendorMasterRequestForm1);
				}
				
				request.setAttribute("documentDetails", a1);
		}catch(Exception se){
			se.printStackTrace();
		}
	
		String typeDetails=vendorMasterRequestForm.getTypeDetails();
		vendorMasterRequestForm.setTypeDetails(typeDetails);
		
		return mapping.findForward("display");
	}
	
	
	public VendorMasterRequestForm setFormElements(ResultSet vmRS){
		VendorMasterRequestForm vmForm= new VendorMasterRequestForm();
		EssDao ad=EssDao.dBConnection();
		try{
		vmForm.setAccountGroupId(vmRS.getString("ACCOUNT_GROUP_ID"));
		String viewType=vmRS.getString("VIEW_TYPE");
		if(viewType.equalsIgnoreCase("1"))
		{
			vmForm.setPurchaseView("Purchase View");
		}
		else
		{
			vmForm.setPurchaseView("Acount View");
		}
		vmForm.setTitle(vmRS.getString("TITLE"));
		vmForm.setName(vmRS.getString("NAME"));
		vmForm.setAddress1(vmRS.getString("ADDRESS1"));
		vmForm.setAddress2(vmRS.getString("ADDRESS2"));
		vmForm.setAddress3(vmRS.getString("ADDRESS3"));
		vmForm.setAddress4(vmRS.getString("ADDRESS4"));
		vmForm.setCity(vmRS.getString("CITY"));
		vmForm.setPinCode(vmRS.getString("PINCODE"));
		String country = vmRS.getString("COUNTRY_ID");
		ResultSet rs9 = ad.selectQuery("select LANDX from Country where LAND1='"+country+"'");
		while(rs9.next()) {
			country = rs9.getString("LANDX");
		}
		vmForm.setCountry(country);
		String state = vmRS.getString("STATE");
		ResultSet rs4 = ad.selectQuery("select BEZEI from State where BLAND='"+state+"'");
		
		while(rs4.next()) {
			state  = rs4.getString("BEZEI");
		}
		vmForm.setState(state);
		vmForm.setLandLineNo(vmRS.getString("LANDLINE_NO"));
		vmForm.setMobileNo(vmRS.getString("MOBILE_NO"));
		vmForm.setFaxNo(vmRS.getString("FAX_NO"));
		vmForm.setEmailId(vmRS.getString("EMAIL_ID"));
		String currency = vmRS.getString("CURRENCY_ID");
		ResultSet rs5 = ad.selectQuery("select ISOCD from Currency where WAERS='"+currency+"'");
		while(rs5.next()) {
			currency = rs5.getString("ISOCD");
		}
		vmForm.setCurrencyId(currency);
		vmForm.setReConcillationActId(vmRS.getString("RECONCILATION_ACT_ID"));
		String elgTds=vmRS.getString("IS_ELIGIBLE_FOR_TDS");
		if(elgTds.equalsIgnoreCase("1"))
		{
			vmForm.setElgTds("True");
		}
		if(elgTds.equalsIgnoreCase("0"))
		{
			vmForm.setElgTds("False");
		}
		vmForm.setTdsCode(vmRS.getString("TDS_CODE"));
		vmForm.setLstNo(vmRS.getString("LST_NO"));
		vmForm.setTinNo(vmRS.getString("TIN_NO"));
		vmForm.setCstNo(vmRS.getString("CST_NO"));
		String paytrm = vmRS.getString("PAYMENT_TERM_ID");
		ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_NAME from PAYMENT_TERM_M where PAYMENT_TERM_ID='"+paytrm+"'");
		
		while(rs7.next()) {
			paytrm = rs7.getString("PAYMENT_TERM_NAME");
		}
		String accclr = vmRS.getString("ACCOUNT_CLERK_ID");
		ResultSet rs8 = ad.selectQuery("select ACC_CLERK_DESC from ACC_CLERK_M where ACC_CLERK_ID='"+accclr+"'");
		while(rs8.next()) {
			accclr = rs8.getString("ACC_CLERK_DESC");
		}
		vmForm.setPaymentTermId(paytrm);
		vmForm.setAccountClerkId(accclr);
		vmForm.setIsApprovedVendor(vmRS.getString("IS_APPROVED_VENDOR"));
		vmForm.setPanNo(vmRS.getString("PAN_No"));
		vmForm.setServiceTaxRegNo(vmRS.getString("Service_Tax_Registration_No"));
		String isRegistVendor=vmRS.getString("IS_REGD_EXCISE_VENDOR");
	 	if(isRegistVendor.equalsIgnoreCase("1"))
		{
	 		vmForm.setRegExciseVendor("True");
		}
		if(isRegistVendor.equalsIgnoreCase("0"))
		{
			vmForm.setRegExciseVendor("False");
		}
		vmForm.setEccNo(vmRS.getString("ECC_No"));
		vmForm.setExciseRegNo(vmRS.getString("Excise_Reg_No"));
		vmForm.setExciseRange(vmRS.getString("Excise_Range"));
		vmForm.setExciseDivision(vmRS.getString("Excise_Division"));
		vmForm.setCommissionerate(vmRS.getString("COMMISSIONERATE"));
		vmForm.setTypeOfVendor(vmRS.getString("Type_Of_Vendor"));
		}
		catch (SQLException sqle) { 
			System.out.println("SQLException @ set Vendor Form Values"); 
			sqle.printStackTrace();}
		return vmForm;
	}
	
	
}
