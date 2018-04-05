package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
import com.microlabs.ess.form.CustomerMasterForm;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class CustomerMasterAction extends DispatchAction{
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		CustomerMasterForm masterForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList apprList=new LinkedList();
		MaterialmasterDAO dao=new MaterialmasterDAO();
		String customerType=request.getParameter("customerType");
		
		apprList=dao.customerApprsList(customerType);
		
		if(apprList.size()>0)
			request.setAttribute("apprList", apprList);
		if(apprList.size()==0)
			request.setAttribute("noapprList", "noapprList");
		return mapping.findForward("apprList");
	}
	public ActionForward lastCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CustomerMasterForm v=(CustomerMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList materialList=new LinkedList();
		String name=v.getCustomerName1();
		String city=v.getCity1();
		String country=v.getCountryId1();
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		LinkedList countryList=new LinkedList();
		LinkedList countryLabelList=new LinkedList();
		try {
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
			}
			v.setCounID(countryList);
			v.setCountryName(countryLabelList);

	 	int  totalCodeSearch=v.getTotalCodeSearch();
	     int  codeStartRecord=v.getCodeStartRecord();
	     int  codeEndRecord=v.getCodeEndRecord();
	     
	     codeStartRecord=totalCodeSearch-9;
	     codeEndRecord=totalCodeSearch;
	     
	     v.setTotalCodeSearch(totalCodeSearch);
			v.setCodeStartRecord(codeStartRecord);
			v.setCodeEndRecord(totalCodeSearch);
		 
			 String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.REQUEST_NO) AS RowNum, cus.REQUEST_NO,cus.ACCOUNT_GROUP_ID,cus.SAP_CODE_NO,cus.NAME,cus.CITY,cus.SAP_CREATION_DATE," +
						"cus.REQUEST_DATE from CUSTOMER_CODES as cus where " +
						"    cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";	
						

						
						if(!country.equalsIgnoreCase(""))
						{
							data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.REQUEST_NO) AS RowNum, cus.REQUEST_NO,cus.ACCOUNT_GROUP_ID,cus.SAP_CODE_NO,cus.NAME,cus.CITY,cus.SAP_CREATION_DATE," +
								"cus.REQUEST_DATE from CUSTOMER_CODES as cus,Country as cou where  " +
								"   cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' and cus.COUNTRY_ID='"+country+"' and cus.COUNTRY_ID=cou.LAND1 ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+" ";
						}

						
						
						ResultSet rs=ad.selectQuery(data);
						
						
							while(rs.next()) 

							{
								CustomerMasterForm form2=new CustomerMasterForm();
								
								if(rs.getString("REQUEST_NO")==null)
								{
									form2.setRequestNo("");
								}
								else{
									form2.setRequestNo(rs.getString("REQUEST_NO"));
								}
								
								if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IN"))
								{
									form2.setAccGroupId("Domestic");
								}
								if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IM"))
								{
									form2.setAccGroupId("Export Customer");
								}
								if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("FS"))
								{
									form2.setAccGroupId("Field Staff");
								}
								if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("7"))
								{
									form2.setAccGroupId("Plants");
								}
								
								
								
								form2.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								form2.setCustomerName(rs.getString("NAME"));
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
		return mapping.findForward("CustomerMasterSearch");
	}
	public ActionForward previousCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CustomerMasterForm v=(CustomerMasterForm)form;

		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList materialList=new LinkedList();
		String name=v.getCustomerName1();
		String city=v.getCity1();
		String country=v.getCountryId1();
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		LinkedList countryList=new LinkedList();
		LinkedList countryLabelList=new LinkedList();
		try {
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
			}
		
		v.setCounID(countryList);
		v.setCountryName(countryLabelList);

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
			  String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.REQUEST_NO) AS RowNum, cus.REQUEST_NO,cus.ACCOUNT_GROUP_ID,cus.SAP_CODE_NO,cus.NAME,cus.CITY,cus.SAP_CREATION_DATE," +
						"cus.REQUEST_DATE from CUSTOMER_CODES as cus where " +
						"    cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";	
						

						
						if(!country.equalsIgnoreCase(""))
						{
							data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.REQUEST_NO) AS RowNum, cus.REQUEST_NO,cus.ACCOUNT_GROUP_ID,cus.SAP_CODE_NO,cus.NAME,cus.CITY,cus.SAP_CREATION_DATE," +
								"cus.REQUEST_DATE from CUSTOMER_CODES as cus,Country as cou where  " +
								"   cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' and cus.COUNTRY_ID='"+country+"' and cus.COUNTRY_ID=cou.LAND1 ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+" ";
						}

						
						
						ResultSet rs=ad.selectQuery(data);
						
						
							while(rs.next()) 

							{
								CustomerMasterForm form2=new CustomerMasterForm();
								
								if(rs.getString("REQUEST_NO")==null)
								{
									form2.setRequestNo("");
								}
								else{
									form2.setRequestNo(rs.getString("REQUEST_NO"));
								}
								
								if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IN"))
								{
									form2.setAccGroupId("Domestic");
								}
								if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IM"))
								{
									form2.setAccGroupId("Export Customer");
								}
								if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("FS"))
								{
									form2.setAccGroupId("Field Staff");
								}
								if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("7"))
								{
									form2.setAccGroupId("Plants");
								}
								
								
								
								form2.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								form2.setCustomerName(rs.getString("NAME"));
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
							request.setAttribute("materialList", materialList);
							rs.close();
							ad.connClose();		
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
	
		
		return mapping.findForward("CustomerMasterSearch");
	}
	public ActionForward nextCodeSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CustomerMasterForm vendorMasterRequestForm=(CustomerMasterForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList materialList=new LinkedList();
		String name=vendorMasterRequestForm.getCustomerName1();
		String city=vendorMasterRequestForm.getCity1();
		String country=vendorMasterRequestForm.getCountryId1();
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		LinkedList countryList=new LinkedList();
		LinkedList countryLabelList=new LinkedList();
		try {
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
			}
		
		vendorMasterRequestForm.setCounID(countryList);
		vendorMasterRequestForm.setCountryName(countryLabelList);
		
		 	int  totalCodeSearch=vendorMasterRequestForm.getTotalCodeSearch();
		     int  codeStartRecord=vendorMasterRequestForm.getCodeStartRecord();
		     int  codeEndRecord=vendorMasterRequestForm.getCodeEndRecord();
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
					String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.REQUEST_NO) AS RowNum, cus.REQUEST_NO,cus.ACCOUNT_GROUP_ID,cus.SAP_CODE_NO,cus.NAME,cus.CITY,cus.SAP_CREATION_DATE," +
							"cus.REQUEST_DATE from CUSTOMER_CODES as cus where " +
							"    cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+"";	
							

							
							if(!country.equalsIgnoreCase(""))
							{
								data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.REQUEST_NO) AS RowNum, cus.REQUEST_NO,cus.ACCOUNT_GROUP_ID,cus.SAP_CODE_NO,cus.NAME,cus.CITY,cus.SAP_CREATION_DATE," +
									"cus.REQUEST_DATE from CUSTOMER_CODES as cus,Country as cou where  " +
									"   cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' and cus.COUNTRY_ID='"+country+"' and cus.COUNTRY_ID=cou.LAND1 ) as  sub Where  sub.RowNum between "+codeStartRecord+" and "+codeEndRecord+" ";
							}

							
							
							ResultSet rs=ad.selectQuery(data);
							
							
								while(rs.next()) 

								{
									CustomerMasterForm form2=new CustomerMasterForm();
									
									if(rs.getString("REQUEST_NO")==null)
									{
										form2.setRequestNo("");
									}
									else{
										form2.setRequestNo(rs.getString("REQUEST_NO"));
									}
									
									if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IN"))
									{
										form2.setAccGroupId("Domestic");
									}
									if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IM"))
									{
										form2.setAccGroupId("Export Customer");
									}
									if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("FS"))
									{
										form2.setAccGroupId("Field Staff");
									}
									if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("7"))
									{
										form2.setAccGroupId("Plants");
									}
									
									
									
									form2.setSapCodeNo(rs.getString("SAP_CODE_NO"));
									form2.setCustomerName(rs.getString("NAME"));
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
								request.setAttribute("materialList", materialList);
								rs.close();
								ad.connClose();
					
					
								if(materialList.size()!=0)
								{
									vendorMasterRequestForm.setTotalCodeSearch(totalCodeSearch);
									vendorMasterRequestForm.setCodeStartRecord(codeStartRecord);
									vendorMasterRequestForm.setCodeEndRecord(codeEndRecord);
									request.setAttribute("nextButton", "nextButton");
									request.setAttribute("previousButton", "previousButton");
								}
								else
								{
									int start=codeStartRecord;
									int end=codeStartRecord;
									
									vendorMasterRequestForm.setTotalCodeSearch(totalCodeSearch);
									vendorMasterRequestForm.setCodeStartRecord(codeStartRecord);
									vendorMasterRequestForm.setCodeEndRecord(codeEndRecord);
									
								}
							 if(materialList.size()<10)
							 {
								 vendorMasterRequestForm.setTotalCodeSearch(totalCodeSearch);
								 vendorMasterRequestForm.setCodeStartRecord(codeStartRecord);
								 vendorMasterRequestForm.setCodeEndRecord(codeStartRecord+materialList.size()-1);
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
		  
		return mapping.findForward("CustomerMasterSearch");
	}
	public ActionForward showCustomerSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		CustomerMasterForm vendorMasterRequestForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	
	int userID=user.getId();
	LinkedList materialList=new LinkedList();
	String name=vendorMasterRequestForm.getCustomerName1();
	String city=vendorMasterRequestForm.getCity1();
	String country=vendorMasterRequestForm.getCountryId1();
	
	ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
	LinkedList countryList=new LinkedList();
	LinkedList countryLabelList=new LinkedList();
	try {
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
		}
	} catch (SQLException e1) {
	
		e1.printStackTrace();
	}
	vendorMasterRequestForm.setCounID(countryList);
	vendorMasterRequestForm.setCountryName(countryLabelList);
	
	int  totalCodeSearch=0;
	  int  codeStartRecord=0;
	  int  codeEndRecord=0;
	  
	  
	String getCount="Select count(*) from CUSTOMER_CODES as cus where " +
	"    cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' ";
	
	if(!country.equalsIgnoreCase(""))
	{
		getCount="Select count(*) from CUSTOMER_CODES as cus,Country as cou where  " +
			"   cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' and cus.COUNTRY_ID='"+country+"' and cus.COUNTRY_ID=cou.LAND1  ";
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
		
	String data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.REQUEST_NO) AS RowNum, cus.REQUEST_NO,cus.ACCOUNT_GROUP_ID,cus.SAP_CODE_NO,cus.NAME,cus.CITY,cus.SAP_CREATION_DATE," +
	"cus.REQUEST_DATE from CUSTOMER_CODES as cus where " +
	"    cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' ) as  sub Where  sub.RowNum between 1 and 10";	
	

	
	if(!country.equalsIgnoreCase(""))
	{
		data="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY cus.REQUEST_NO) AS RowNum, cus.REQUEST_NO,cus.ACCOUNT_GROUP_ID,cus.SAP_CODE_NO,cus.NAME,cus.CITY,cus.SAP_CREATION_DATE," +
			"cus.REQUEST_DATE from CUSTOMER_CODES as cus,Country as cou where  " +
			"   cus.NAME like '%"+name+"%' and  cus.CITY like '%"+city+"%' and cus.COUNTRY_ID='"+country+"' and cus.COUNTRY_ID=cou.LAND1 ) as  sub Where  sub.RowNum between 1 and 10 ";
	}

	
	
	ResultSet rs=ad.selectQuery(data);
	
	
		while(rs.next()) 

		{
			CustomerMasterForm form2=new CustomerMasterForm();
			
			if(rs.getString("REQUEST_NO")==null)
			{
				form2.setRequestNo("");
			}
			else{
				form2.setRequestNo(rs.getString("REQUEST_NO"));
			}
			
			if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IN"))
			{
				form2.setAccGroupId("Domestic");
			}
			if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("IM"))
			{
				form2.setAccGroupId("Export Customer");
			}
			if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("FS"))
			{
				form2.setAccGroupId("Field Staff");
			}
			if(rs.getString("ACCOUNT_GROUP_ID").equalsIgnoreCase("7"))
			{
				form2.setAccGroupId("Plants");
			}
			
			
			
			form2.setSapCodeNo(rs.getString("SAP_CODE_NO"));
			form2.setCustomerName(rs.getString("NAME"));
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
	
	
	return mapping.findForward("CustomerMasterSearch");
	}
	
	
	
	public ActionForward getCustomerRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		EssDao ad=EssDao.dBConnection();
		 CustomerMasterForm custForm=(CustomerMasterForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		custForm.setCustomerName1("");
		custForm.setCity1("");
		custForm.setCountryId1("");
	
		try{
			ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
			LinkedList countryList=new LinkedList();
			LinkedList countryLabelList=new LinkedList();
			
			try {
				while(rs9.next()) {
					countryList.add(rs9.getString("LAND1"));
					countryLabelList.add(rs9.getString("LANDX")+" - "+rs9.getString("LAND1"));
				}
			} catch (SQLException e1) {
			
				e1.printStackTrace();
			}
			custForm.setCounID(countryList);
			custForm.setCountryName(countryLabelList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("CustomerMasterSearch");

	}
	
	public ActionForward ViewCustomerrecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requstNo1=request.getParameter("ReqestNo");
		

		
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
		
		

		
     
		
		
		
		
		return mapping.findForward("CustomerMasterView");
	}
	
	public ActionForward SubmitAllCustomer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		EssDao ad=EssDao.dBConnection();
		CustomerMasterForm custForm=(CustomerMasterForm)form;
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
	 
			String reqNo[]=custForm.getGetReqno();
			
			for(int j=0;j<reqNo.length;j++)
			{
				try{
					int i=0;
					
					String approveRequestNo=reqNo[j];
					String matType="";
					String loctID="";
					
					 Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
				String dateNow = ft.format(dNow);
					String approvermail="";
					String pendingApprovers="";
					String accountGroupId="";
					String Customer_Type="";
					   String accountGroup="select Customer_Type from CUSTOMER_MASTER_M  " +
					   		"where REQUEST_NO='"+approveRequestNo+"'";	
					   ResultSet accountGroupId1=ad.selectQuery(accountGroup);
					   while(accountGroupId1.next()){
						   Customer_Type=accountGroupId1.getString("Customer_Type");
			    	 		
			    	 	}
					   
					   
						int checkApprover=0;
						
						
			    	 	
			    		if(Customer_Type.equalsIgnoreCase("Exports"))
					 	{
			    			Customer_Type="Export";
					 	}
			    		else{
			    			
			    			
			    		}
			    		String getApprovers="select emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='"+Customer_Type+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
			    		ResultSet rsAppr=ad.selectQuery(getApprovers);
			    		while(rsAppr.next())
			    		{
			    			checkApprover=1;
			    		}
			    		if(checkApprover==1)
						{
							try{


								dNow = new Date( );
								 ft = new SimpleDateFormat ("dd/MM/yyyy");
								dateNow = ft.format(dNow);
								 	String pApprover="";
								 	String parllelAppr1="";
								 	String parllelAppr2="";
									String pendingApprs="";
						    	
						    		
						    		
								 	
								 checkApprover=0;
								
								 	
								 	
								 	getApprovers="select app.Approver_Id,app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='"+Customer_Type+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
						    			 getApprovers="select app.Approver_Id,app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
									saveRecReq = saveRecReq + "'"+approveRequestNo+"','Customer Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','Customer Master ')";
									int ij=ad.SqlExecuteUpdate(saveRecReq);
									if(!(parllelAppr1.equalsIgnoreCase(""))){
										
										
									 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
									 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+approveRequestNo+"','Customer Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','Customer Master ')";
									 ad.SqlExecuteUpdate(sendRecParllelAppr1);
									}
									if(!(parllelAppr2.equalsIgnoreCase(""))){
										
										 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
										 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+approveRequestNo+"','Customer Master','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','Customer Master')";
										 ad.SqlExecuteUpdate(sendRecParllelAppr2);
									}
									
									if(ij>0){
										custForm.setMessage2("Request No "+approveRequestNo+". Submitted for approval successully.");
										custForm.setMessage("");
										String updateStatus="update CUSTOMER_MASTER_M set Approve_Status='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+approveRequestNo+"'";
										ad.SqlExecuteUpdate(updateStatus);
										
										EMailer email = new EMailer();
										email.sendMailToApprover(request, approvermail,approveRequestNo, "Customer Master");
										
									}else{
										custForm.setMessage("Error while submiting approval...");
										custForm.setMessage2("");
									}
						    		}else{
						    			
						    			custForm.setMessage("No approvers are assigned for submiting this request.");
						    			custForm.setMessage2("");
						    		}
									
									
									
								
									
									
									
								
								}catch (Exception e) {
									e.printStackTrace();
								}			
																
									
								
					
						}
			    		
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			custForm.setApproveType("Created");
			searchRecord(mapping, custForm, request, response);
		return mapping.findForward("customerList");
	}

	
	
	public ActionForward saveAndSubmitMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CustomerMasterForm custForm=(CustomerMasterForm)form;
		int approveRequestNo=0;
		EssDao ad=EssDao.dBConnection();
		try{
		HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
		String dateNow = ft.format(dNow);
			
			
			  String reqDate=custForm.getRequestDate();
			 
			  String a[]=reqDate.split("/");
			  for(int i=0;i<a.length;i++)
			  {
				  System.out.println("a="+a[i]);
			  }
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			  System.out.println("Now the date is :=>  " + dateNow);
			  String fileList="";
			  String getCountryDetails="select * from Country";
				LinkedList countryID=new LinkedList();
				LinkedList countryName=new LinkedList();
				ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
				while(rsCountryDetails.next()){
					countryID.add(rsCountryDetails.getString("LAND1"));
					countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
				}
				custForm.setCounID(countryID);
				custForm.setCountryName(countryName);
			int fileCount=0;
			String getFileCount="select count(*) from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rsFileCount=ad.selectQuery(getFileCount);
			while(rsFileCount.next())
			{
				fileCount=rsFileCount.getInt(1);
			}
			if(fileCount>0){
			String getUploadedFiles="select * from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rs=ad.selectQuery(getUploadedFiles);
			while(rs.next())
			{
				fileList+="jsp/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/"+rs.getString("file_name")+",";
			}
			
			String viewType="";
			String sales=null;
			String accounts=null;
			
			sales=custForm.getSales();
			accounts=custForm.getAccounts();
			
			if(sales!=null&& accounts!=null){
				
				viewType="3";
			}else{
			
			if(sales!=null)
			{
				
					viewType="S";
				
			}
				if(accounts!=null){
					
						viewType="A";
					
				}
				
				
			}
				System.out.println("View Type="+viewType);
			String customerType="";
			String domestic=null;
			String exports=null;
			domestic=custForm.getDomestic();
			exports=custForm.getExports();
		      if(domestic!=null)
		      {
					
						customerType="Domestic";
					
		      }
				if(exports!=null)
					
				{
					
						customerType="Exports";
					
				}
			String typeDetails=custForm.getTypeDetails();
			
			
			int i=0;
			
			String Customer_Type="";
    		
    		
		 		Customer_Type=customerType;
		 		
		 	
		 	if(Customer_Type.equalsIgnoreCase("Exports"))
		 	{
		 		Customer_Type="Export";
		 	}
		 	
		 	int checkApprover=0;
		 	String pendingApprs="";
		 	
		 	
		 	String getApprovers="select emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='"+Customer_Type+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
    		ResultSet rsAppr=ad.selectQuery(getApprovers);
    		while(rsAppr.next())
    		{
    			checkApprover=1;
    			 ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm ");
				String dateNow2 = ft.format(dNow);
				
				String deleteHistory="delete CUSTOMER_MASTER_HISTORY where REQUEST_NO='"+custForm.getRequestNumber()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
	  	  		ad.SqlExecuteUpdate(deleteHistory);
				
    			String saveInHistory="INSERT INTO CUSTOMER_MASTER_HISTORY(REQUEST_NO,REQUEST_DATE,ACCOUNT_GROUP_ID,VIEW_TYPE,Customer_Type,NAME,ADDRESS1,ADDRESS2," +
						"ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,Customer_Group,Price_Group,Price_List," +
						"Tax_Type,CURRENCY_ID,IS_REGD_EXCISE_Customer,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No," +
						"Excise_Range,Excise_Division,DLNO1,DLNO2,PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,CREATED_DATE,CREATED_BY,Attachments,Approve_Status,cutomer_code,role,GSTIN_Number) values('"+custForm.getRequestNumber()+"','"+dateNow2+"','"+custForm.getAccGroupId()+"','"+viewType+"','"+customerType+"'," +
						"'"+custForm.getCustomerName()+"','"+custForm.getAddress1()+"','"+custForm.getAddress2()+"'," +
						"'"+custForm.getAddress3()+"','"+custForm.getAddress4()+"','"+custForm.getCity()+"','"+custForm.getPincode()+"','"+custForm.getCountryId()+"'," +
						"'"+custForm.getState()+"','"+custForm.getLandlineNo()+"','"+custForm.getMobileNo()+"','"+custForm.getFaxNo()+"','"+custForm.getEmailId()+"'," +
						"'"+custForm.getCustomerGroup()+"','"+custForm.getPriceGroup()+"','"+custForm.getPriceList()+"','"+custForm.getTaxType()+"','"+custForm.getCurrencyId()+"','"+custForm.getTdsStatus()+"'," +
						"'"+custForm.getTdsCode()+"','"+custForm.getListNumber()+"','"+custForm.getTinNumber()+"','"+custForm.getCstNumber()+"','"+custForm.getPanNumber()+"'," +
						"'"+custForm.getServiceTaxNo()+"','"+custForm.getIsRegdExciseVender()+"','"+custForm.getEccNo()+"','"+custForm.getExciseRegNo()+"','"+custForm.getExciseRange()+"'," +
						"'"+custForm.getExciseDivision()+"','"+custForm.getDlno1()+"','"+custForm.getDlno2()+"','"+custForm.getPaymentTermID()+"','"+custForm.getAccountClerkId()+"','"+dateNow+"'," +
						"'"+user.getEmployeeNo()+"','"+fileList+"','Created','"+custForm.getCutomerCode()+"','user','"+custForm.getGstinNo()+"')";
				ad.SqlExecuteUpdate(saveInHistory);
				
    		}
			
			
			
			
			if(typeDetails.equalsIgnoreCase("Save"))
				
			{
				
				String customerCout="select count(*) from CUSTOMER_MASTER_M where REQUEST_NO='"+custForm.getRequestNumber()+"'";
				int count=0;
				
				ResultSet rsCount=ad.selectQuery(customerCout);
				while(rsCount.next()){
					count=rsCount.getInt(1);
				}
				String approver="";
				/*String lApprover="";
				String approvermail="";
				String pendingApprovers="";
				
				String getApproverID="select * from Approvers_Details where Type like '%Customer Master%'";
				
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
			int old_RequestNo=custForm.getRequestNumber();
			String getReqestNumber="select max(REQUEST_NO)  from CUSTOMER_MASTER_M";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			custForm.setRequestNumber(maxReqno);	
			
	String saveQuery="INSERT INTO CUSTOMER_MASTER_M(REQUEST_NO,REQUEST_DATE,ACCOUNT_GROUP_ID,VIEW_TYPE,Customer_Type,NAME,ADDRESS1,ADDRESS2," +
			"ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,Customer_Group,Price_Group,Price_List," +
			"Tax_Type,CURRENCY_ID,IS_REGD_EXCISE_Customer,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No," +
			"Excise_Range,Excise_Division,DLNO1,DLNO2,PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,CREATED_DATE,CREATED_BY,Attachments,Approve_Status,cutomer_code,GSTIN_Number) values('"+custForm.getRequestNumber()+"','"+reqDate+"','"+custForm.getAccGroupId()+"','"+viewType+"','"+customerType+"'," +
			"'"+custForm.getCustomerName()+"','"+custForm.getAddress1()+"','"+custForm.getAddress2()+"'," +
			"'"+custForm.getAddress3()+"','"+custForm.getAddress4()+"','"+custForm.getCity()+"','"+custForm.getPincode()+"','"+custForm.getCountryId()+"'," +
			"'"+custForm.getState()+"','"+custForm.getLandlineNo()+"','"+custForm.getMobileNo()+"','"+custForm.getFaxNo()+"','"+custForm.getEmailId()+"'," +
			"'"+custForm.getCustomerGroup()+"','"+custForm.getPriceGroup()+"','"+custForm.getPriceList()+"','"+custForm.getTaxType()+"','"+custForm.getCurrencyId()+"','"+custForm.getTdsStatus()+"'," +
			"'"+custForm.getTdsCode()+"','"+custForm.getListNumber()+"','"+custForm.getTinNumber()+"','"+custForm.getCstNumber()+"','"+custForm.getPanNumber()+"'," +
			"'"+custForm.getServiceTaxNo()+"','"+custForm.getIsRegdExciseVender()+"','"+custForm.getEccNo()+"','"+custForm.getExciseRegNo()+"','"+custForm.getExciseRange()+"'," +
			"'"+custForm.getExciseDivision()+"','"+custForm.getDlno1()+"','"+custForm.getDlno2()+"','"+custForm.getPaymentTermID()+"','"+custForm.getAccountClerkId()+"','"+dateNow+"'," +
			"'"+user.getEmployeeNo()+"','"+fileList+"','Created','"+custForm.getCutomerCode()+"','"+custForm.getGstinNo()+"')";
	System.out.println("saveQuery="+saveQuery);
	display(mapping, form, request, response);
			 i=ad.SqlExecuteUpdatePrmaryKeys(saveQuery);
			 custForm.setMessage2("Customer Code creation request saved with New request number='"+custForm.getRequestNumber()+"'");
			 custForm.setMessage("");
			 custForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
			 approveRequestNo=custForm.getRequestNumber();
			 int j=0;
				j=ad.SqlExecuteUpdate("update UploadFiles_Masters set request_no='"+custForm.getRequestNumber()+"' where request_no='"+old_RequestNo+"' and  userId='"+user.getEmployeeNo()+"'");
				
			 
		}else{
			String saveQuery="INSERT INTO CUSTOMER_MASTER_M(REQUEST_NO,REQUEST_DATE,ACCOUNT_GROUP_ID,VIEW_TYPE,Customer_Type,NAME,ADDRESS1,ADDRESS2," +
			"ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,Customer_Group,Price_Group,Price_List," +
			"Tax_Type,CURRENCY_ID,IS_REGD_EXCISE_Customer,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No," +
			"Excise_Range,Excise_Division,DLNO1,DLNO2,PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,CREATED_DATE,CREATED_BY,Attachments,Approve_Status,cutomer_code,GSTIN_Number) values('"+custForm.getRequestNumber()+"','"+reqDate+"','"+custForm.getAccGroupId()+"','"+viewType+"','"+customerType+"'," +
			"'"+custForm.getCustomerName()+"','"+custForm.getAddress1()+"','"+custForm.getAddress2()+"'," +
			"'"+custForm.getAddress3()+"','"+custForm.getAddress4()+"','"+custForm.getCity()+"','"+custForm.getPincode()+"','"+custForm.getCountryId()+"'," +
			"'"+custForm.getState()+"','"+custForm.getLandlineNo()+"','"+custForm.getMobileNo()+"','"+custForm.getFaxNo()+"','"+custForm.getEmailId()+"'," +
			"'"+custForm.getCustomerGroup()+"','"+custForm.getPriceGroup()+"','"+custForm.getPriceList()+"','"+custForm.getTaxType()+"','"+custForm.getCurrencyId()+"','"+custForm.getTdsStatus()+"'," +
			"'"+custForm.getTdsCode()+"','"+custForm.getListNumber()+"','"+custForm.getTinNumber()+"','"+custForm.getCstNumber()+"','"+custForm.getPanNumber()+"'," +
			"'"+custForm.getServiceTaxNo()+"','"+custForm.getIsRegdExciseVender()+"','"+custForm.getEccNo()+"','"+custForm.getExciseRegNo()+"','"+custForm.getExciseRange()+"'," +
			"'"+custForm.getExciseDivision()+"','"+custForm.getDlno1()+"','"+custForm.getDlno2()+"','"+custForm.getPaymentTermID()+"','"+custForm.getAccountClerkId()+"','"+dateNow+"'," +
			"'"+user.getEmployeeNo()+"','"+fileList+"','Created','"+custForm.getCutomerCode()+"','"+custForm.getGstinNo()+"')";
	System.out.println("saveQuery="+saveQuery);
	//display(mapping, form, request, response);
			 i=ad.SqlExecuteUpdatePrmaryKeys(saveQuery);
			 
				 custForm.setMessage2("Customer Code creation request submited with request number='"+custForm.getRequestNumber()+"'");
				 custForm.setMessage("");
				 custForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				 approveRequestNo=custForm.getRequestNumber();
			
		}
			if(i>0)
			{
				String Req_Id = ""+custForm.getRequestNumber();
				//EMailer email = new EMailer();
				//i = email.sendMailToApprover(request, approvermail,Req_Id,"Customer Master");
				String getReqestNumber="select max(REQUEST_NO)  from CUSTOMER_MASTER_M";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				custForm.setRequestNumber(maxReqno);
				custForm.setRequestDate(EMicroUtils.getCurrentSysDate());
				custForm.setTypeDetails("Save");
				custForm.setAccGroupId(""); 
				custForm.setViewType("");  
				custForm.setCustmerType("");
				custForm.setCustomerName("");
				custForm.setAddress1("");
				custForm.setAddress2("");
				custForm.setAddress3("");
				custForm.setAddress4("");
				custForm.setCity("");
				custForm.setPincode("");
				custForm.setCountryId("");
				custForm.setState("");
				custForm.setLandlineNo("");
				custForm.setMobileNo("");
				custForm.setFaxNo("");
				custForm.setEmailId("");
				custForm.setCustomerGroup("");
				custForm.setPriceList("");
				custForm.setTaxType("");
				custForm.setCurrencyId("");
				custForm.setTdsStatus("");
				custForm.setTdsCode("");
				custForm.setListNumber("");
				custForm.setTinNumber("");
				custForm.setCstNumber("");
				custForm.setPanNumber("");
				custForm.setServiceTaxNo("");
				custForm.setIsRegdExciseVender("");
				custForm.setEccNo("");
				custForm.setExciseRegNo("");
				custForm.setExciseRange("");
				custForm.setExciseDivision("");
				custForm.setDlno1("");
				custForm.setDlno2("");
				custForm.setPaymentTermID("");
				custForm.setAccountClerkId("");
				custForm.setPriceGroup("");
				custForm.setSales("");
				custForm.setAccounts("");
				custForm.setDomestic("");
				custForm.setExports("");
				custForm.setCutomerCode("");
			}
					else
					{
						custForm.setMessage("Error...While Saving Customer Master Data.Please Check...");
						custForm.setMessage2("");
					}
			}
			else{
				System.out.println("update Customer Record");
				
				String recordStatus="";
				String getRecordStatus="select Approve_Status from CUSTOMER_MASTER_M where REQUEST_NO='"+custForm.getRequestNumber()+"' ";
				ResultSet rsRecord=ad.selectQuery(getRecordStatus);
				while(rsRecord.next())
				{
					recordStatus=rsRecord.getString("Approve_Status");
				}
				if(recordStatus.equalsIgnoreCase("Rejected"))
				{
					
					String deleteRecords="delete from All_Request where Req_Id='"+custForm.getRequestNumber()+"' and Req_Type='Customer Master'";
					int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
					System.out.println("deleteStatus="+deleteStatus);
					
					String updateFlag="update CUSTOMER_MASTER_M set rejected_flag='yes' where REQUEST_NO='"+custForm.getRequestNumber()+"'";
					ad.SqlExecuteUpdate(updateFlag);
				}
				String approvedStatus="Pending";
				
			 user=(UserInfo)session.getAttribute("user");
			 
				String updateCustomerData="update CUSTOMER_MASTER_M set REQUEST_DATE='"+reqDate+"',ACCOUNT_GROUP_ID='"+custForm.getAccGroupId()+"',VIEW_TYPE='"+viewType+"',Customer_Type='"+customerType+"',NAME='"+custForm.getCustomerName()+"',ADDRESS1='"+custForm.getAddress1()+"',ADDRESS2='"+custForm.getAddress2()+"'," +
				"ADDRESS3='"+custForm.getAddress3()+"',ADDRESS4='"+custForm.getAddress4()+"',CITY='"+custForm.getCity()+"',PINCODE='"+custForm.getPincode()+"',COUNTRY_ID='"+custForm.getCountryId()+"',STATE='"+custForm.getState()+"',LANDLINE_NO='"+custForm.getLandlineNo()+"',MOBILE_NO='"+custForm.getMobileNo()+"',FAX_NO='"+custForm.getFaxNo()+"',EMAIL_ID='"+custForm.getEmailId()+"',Customer_Group='"+custForm.getCustomerGroup()+"',Price_Group='"+custForm.getPriceGroup()+"',Price_List='"+custForm.getPriceList()+"'," +
				"Tax_Type='"+custForm.getTaxType()+"',CURRENCY_ID='"+custForm.getCurrencyId()+"',IS_REGD_EXCISE_Customer='"+custForm.getTdsStatus()+"',TDS_CODE='"+custForm.getTdsCode()+"',LST_NO='"+custForm.getListNumber()+"',TIN_NO='"+custForm.getTinNumber()+"',CST_NO='"+custForm.getCstNumber()+"',PAN_No='"+custForm.getPanNumber()+"',Service_Tax_Registration_No='"+custForm.getServiceTaxNo()+"',IS_REGD_EXCISE_VENDOR='"+custForm.getIsRegdExciseVender()+"',ECC_No='"+custForm.getEccNo()+"',Excise_Reg_No='"+custForm.getExciseRegNo()+"'," +
				"Excise_Range='"+custForm.getExciseRange()+"',Excise_Division='"+custForm.getExciseDivision()+"',DLNO1='"+custForm.getDlno1()+"',DLNO2='"+custForm.getDlno2()+"',PAYMENT_TERM_ID='"+custForm.getPaymentTermID()+"',ACCOUNT_CLERK_ID='"+custForm.getAccountClerkId()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Attachments='"+fileList+"',Approve_Status='Created',cutomer_code='"+custForm.getCutomerCode()+"',GSTIN_Number='"+custForm.getGstinNo()+"' where  REQUEST_NO='"+custForm.getRequestNumber()+"'";
			i=ad.SqlExecuteUpdate(updateCustomerData);
			if(i>0)
			{
				custForm.setMessage2("Customer Code creation request updated with request number='"+custForm.getRequestNumber()+"'");
				custForm.setMessage("");
				custForm.setAppStatusMessage("No approvers are assigned for submiting this request. ");
				approveRequestNo=custForm.getRequestNumber();
				
				String Req_Id = ""+custForm.getRequestNumber();
				//EMailer email = new EMailer();
				//i = email.sendMailToApprover(request, approvermail,Req_Id,"Customer Master");
				String getReqestNumber="select max(REQUEST_NO)  from CUSTOMER_MASTER_M";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				custForm.setRequestNumber(maxReqno);
				custForm.setRequestDate(EMicroUtils.getCurrentSysDate());
				custForm.setTypeDetails("Save");
				custForm.setAccGroupId(""); 
				custForm.setViewType("");  
				custForm.setCustmerType("");
				custForm.setCustomerName("");
				custForm.setAddress1("");
				custForm.setAddress2("");
				custForm.setAddress3("");
				custForm.setAddress4("");
				custForm.setCity("");
				custForm.setPincode("");
				custForm.setCountryId("");
				custForm.setState("");
				custForm.setLandlineNo("");
				custForm.setMobileNo("");
				custForm.setFaxNo("");
				custForm.setEmailId("");
				custForm.setCustomerGroup("");
				custForm.setPriceList("");
				custForm.setTaxType("");
				custForm.setCurrencyId("");
				custForm.setTdsStatus("");
				custForm.setTdsCode("");
				custForm.setListNumber("");
				custForm.setTinNumber("");
				custForm.setCstNumber("");
				custForm.setPanNumber("");
				custForm.setServiceTaxNo("");
				custForm.setIsRegdExciseVender("");
				custForm.setEccNo("");
				custForm.setExciseRegNo("");
				custForm.setExciseRange("");
				custForm.setExciseDivision("");
				custForm.setDlno1("");
				custForm.setDlno2("");
				custForm.setPaymentTermID("");
				custForm.setAccountClerkId("");
				custForm.setPriceGroup("");
				custForm.setSales("");
				custForm.setAccounts("");
				custForm.setDomestic("");
				custForm.setExports("");
				custForm.setCutomerCode("");
			
			}
			else{
				
				custForm.setMessage("Error When Updating Customer Master");
				custForm.setMessage2("");
			}
			}
			
			
			if(checkApprover==1)
			{
				try{


				dNow = new Date( );
				 ft = new SimpleDateFormat ("dd/MM/yyyy");
				dateNow = ft.format(dNow);
				 	String pApprover="";
				 	String parllelAppr1="";
				 	String parllelAppr2="";
				 checkApprover=0;
				
				 	
				 	
				 	getApprovers="select app.Approver_Id,app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='"+Customer_Type+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		    			 getApprovers="select app.Approver_Id,app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
					saveRecReq = saveRecReq + "'"+approveRequestNo+"','Customer Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','Customer Master ')";
					int ij=ad.SqlExecuteUpdate(saveRecReq);
				
					
					if(!(parllelAppr1.equalsIgnoreCase(""))){
						
						
					 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+approveRequestNo+"','Customer Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','Customer Master ')";
					 ad.SqlExecuteUpdate(sendRecParllelAppr1);
					}
					if(!(parllelAppr2.equalsIgnoreCase(""))){
						
						 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
						 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+approveRequestNo+"','Customer Master','"+user.getUserName()+"','"+dateNow1+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','Customer Master')";
						 ad.SqlExecuteUpdate(sendRecParllelAppr2);
					}
					
					if(ij>0){
						custForm.setMessage2("Request No "+approveRequestNo+". Submitted for approval successully.");
						custForm.setMessage("");
						String updateStatus="update CUSTOMER_MASTER_M set Approve_Status='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+approveRequestNo+"'";
						ad.SqlExecuteUpdate(updateStatus);
						custForm.setAppStatusMessage("");
						
						EMailer email = new EMailer();
						String approvermail="";
						String reqNo=Integer.toString(approveRequestNo);
						email.sendMailToApprover(request, approvermail,reqNo, "Customer Master");
					}else{
						custForm.setMessage("Error while submiting approval...");
						custForm.setMessage2("");
					}
		    		}else{
		    			
		    			custForm.setMessage("No Approvers are assigned.Please Contact to Admin");
		    			custForm.setMessage2("");
		    		}
					
					
					
				
					
					
					
				
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			
			
			
			
			
			
			}else{
				custForm.setMessage("Error..Please Upload File");
				custForm.setMessage2("");
			}
				
				
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	custForm.setAccountGroupList(accountGroupList);
	custForm.setAccountGroupLabelList(accountGroupLabelList);	
			
				LinkedList stateID=new LinkedList();
				LinkedList stateName=new LinkedList();
				String getStateDetails="select * from State where LAND1='"+custForm.getCountryId()+"'";
				ResultSet rsState=ad.selectQuery(getStateDetails);
				
				while(rsState.next())
				{
					stateID.add(rsState.getString("BLAND"));
					stateName.add(rsState.getString("BEZEI"));
				}
				custForm.setStateId(stateID);
				custForm.setStates(stateName);
				request.setAttribute("diplayStates", "diplayStates");
				
				ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
				"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
				ArrayList paymentTermList=new ArrayList();
				ArrayList paymentTermLabelList=new ArrayList();
				
				while(rs7.next()) {
					paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
					paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
				}
				custForm.setPaymentTermList(paymentTermList);
				custForm.setPaymentTermLabelList(paymentTermLabelList);
				
				LinkedList currencyID=new LinkedList();
				LinkedList cureencyNames=new LinkedList();
				String geCurrencyDetails="select * from Currency";
				ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
				while(rsCurrencyDetails.next())
				{
					currencyID.add(rsCurrencyDetails.getString("WAERS"));
					cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
				}
				custForm.setCurrenIds(currencyID);
				custForm.setCurrencys(cureencyNames);	
				
			LinkedList tdsID=new LinkedList();
			LinkedList tdsValue=new LinkedList();

			String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
			ResultSet rsTDs=ad.selectQuery(getTDs);
			while(rsTDs.next())
			{
				tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
				tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
			}

			custForm.setTdsIds(tdsID);
			custForm.setTdsCodes(tdsValue);

			String countryId=custForm.getCountryId();
			

			String tdsState=custForm.getIsRegdExciseVender();

				if(tdsState.equalsIgnoreCase("True"))
				{
					
					 request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
				}
				else{
					 request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
					 
				}
				
				
			
			
			
			
			
			
			
			setMasterData(mapping, form, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//displayCustomerList(mapping, form, request, response);
			return mapping.findForward("display");
	
	}
	
	public ActionForward copyCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=EssDao.dBConnection();
		try{
			
		
		
		ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
		"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
		ArrayList paymentTermList=new ArrayList();
		ArrayList paymentTermLabelList=new ArrayList();
		
		while(rs7.next()) {
			paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
			paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
		}
		custForm.setPaymentTermList(paymentTermList);
		custForm.setPaymentTermLabelList(paymentTermLabelList);
		
		ArrayList accountGroupList=new ArrayList();
		ArrayList accountGroupLabelList=new ArrayList();
	 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
ArrayList subLinkIdList = new ArrayList();
ArrayList subLinkValueList = new ArrayList();
while(rs1.next()) {
	accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
	accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
}
custForm.setAccountGroupList(accountGroupList);
custForm.setAccountGroupLabelList(accountGroupLabelList);
			
			 
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			custForm.setCounID(countryID);
			custForm.setCountryName(countryName);
			
			
			LinkedList currencyID=new LinkedList();
			LinkedList cureencyNames=new LinkedList();
			String geCurrencyDetails="select * from Currency";
			ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
			while(rsCurrencyDetails.next())
			{
				currencyID.add(rsCurrencyDetails.getString("WAERS"));
				cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
			}
			custForm.setCurrenIds(currencyID);
			custForm.setCurrencys(cureencyNames);	
			
		LinkedList tdsID=new LinkedList();
		LinkedList tdsValue=new LinkedList();
		
		String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
		ResultSet rsTDs=ad.selectQuery(getTDs);
		while(rsTDs.next())
		{
			tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
			tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
		}
		
		custForm.setTdsIds(tdsID);
		custForm.setTdsCodes(tdsValue);
		setMasterData(mapping, form, request, response);
		
		EMicroUtils ut=new EMicroUtils();
		int requstNo=Integer.parseInt(request.getParameter("RequestNo"));
		String editRecord="select * from CUSTOMER_MASTER_M where REQUEST_NO='"+requstNo+"'";
		ResultSet rsEditRecord=ad.selectQuery(editRecord);
		while(rsEditRecord.next())
		{
			
			String getReqestNumber="select max(REQUEST_NO)  from CUSTOMER_MASTER_M";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			custForm.setRequestNumber(maxReqno);
			custForm.setRequestDate(EMicroUtils.getCurrentSysDate());
			custForm.setAccGroupId(rsEditRecord.getString("ACCOUNT_GROUP_ID"));
			custForm.setSales("");
			custForm.setAccounts("");
			String viewType=rsEditRecord.getString("VIEW_TYPE");
			if(viewType.equalsIgnoreCase("S"))
			{
				custForm.setSales("On");
			}
			if(viewType.equalsIgnoreCase("A"))
			{
				custForm.setAccounts("On");
			}
			if(viewType.equalsIgnoreCase("3"))
			{
				custForm.setSales("On");
				custForm.setAccounts("On");
			}
			
			custForm.setDomestic("");
			custForm.setExports("");
			
			String customertype=rsEditRecord.getString("Customer_Type");
			if(customertype.equalsIgnoreCase("Domestic"))
			{
				custForm.setDomestic("On");
			}
			if(customertype.equalsIgnoreCase("Exports"))
			{
				custForm.setExports("On");
			}
			custForm.setCustomerName(rsEditRecord.getString("NAME"));
			custForm.setAddress1(rsEditRecord.getString("ADDRESS1"));
			custForm.setAddress2(rsEditRecord.getString("ADDRESS2"));
			custForm.setAddress3(rsEditRecord.getString("ADDRESS3"));
			custForm.setAddress4(rsEditRecord.getString("ADDRESS4"));
			custForm.setCity(rsEditRecord.getString("CITY"));
			custForm.setPincode(rsEditRecord.getString("PINCODE"));
			
			custForm.setCountryId(rsEditRecord.getString("COUNTRY_ID"));
			
			
			
		
			custForm.setState(rsEditRecord.getString("STATE"));
			custForm.setLandlineNo(rsEditRecord.getString("LANDLINE_NO"));
			custForm.setMobileNo(rsEditRecord.getString("MOBILE_NO"));
			custForm.setFaxNo(rsEditRecord.getString("FAX_NO"));
			custForm.setEmailId(rsEditRecord.getString("EMAIL_ID"));
			custForm.setCustomerGroup(rsEditRecord.getString("Customer_Group"));
			custForm.setPriceGroup(rsEditRecord.getString("Price_Group"));
			custForm.setPriceList(rsEditRecord.getString("Price_List"));
			custForm.setTaxType(rsEditRecord.getString("Tax_Type"));
			custForm.setCurrencyId(rsEditRecord.getString("CURRENCY_ID"));
			String tdsStatus=rsEditRecord.getString("IS_REGD_EXCISE_Customer");
			if(tdsStatus.equalsIgnoreCase("1"))
			{
				custForm.setTdsStatus("True");
				request.setAttribute("setTdsState", "setTdsState");
			}
			if(tdsStatus.equalsIgnoreCase("0"))
				custForm.setTdsStatus("False");
				
			custForm.setTdsCode(rsEditRecord.getString("TDS_CODE"));
			custForm.setListNumber(rsEditRecord.getString("LST_NO"));
			custForm.setTinNumber(rsEditRecord.getString("TIN_NO"));
			custForm.setCstNumber(rsEditRecord.getString("CST_NO"));
			custForm.setPanNumber(rsEditRecord.getString("PAN_No"));
			custForm.setServiceTaxNo(rsEditRecord.getString("Service_Tax_Registration_No"));
			String isRegdExciseVender=rsEditRecord.getString("IS_REGD_EXCISE_VENDOR");
			if(isRegdExciseVender.equalsIgnoreCase("1"))
			{
				custForm.setIsRegdExciseVender("True");
				request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
			}
			if(isRegdExciseVender.equalsIgnoreCase("0"))
			{
				custForm.setIsRegdExciseVender("False");
				request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
			}
			custForm.setEccNo(rsEditRecord.getString("ECC_No"));
			custForm.setExciseRegNo(rsEditRecord.getString("Excise_Reg_No"));
			custForm.setExciseRange(rsEditRecord.getString("Excise_Range"));
			custForm.setExciseDivision(rsEditRecord.getString("Excise_Division"));
			custForm.setDlno1(rsEditRecord.getString("DLNO1"));
			custForm.setDlno2(rsEditRecord.getString("DLNO2"));
			custForm.setPaymentTermID(rsEditRecord.getString("PAYMENT_TERM_ID"));
			custForm.setAccountClerkId(rsEditRecord.getString("ACCOUNT_CLERK_ID"));
			custForm.setCutomerCode(rsEditRecord.getString("cutomer_code"));
			
			if(user.getId()==1)
				{	
				custForm.setApproveType(rsEditRecord.getString("Approve_Status"));
					request.setAttribute("approved", "approved");
				}
			if(user.getId()==2)//SAP Creatore
			{
				custForm.setApproveType(rsEditRecord.getString("Approve_Status"));
				request.setAttribute("approved", "approved");
			}
			}
	
		custForm.setTypeDetails("Update");
		
		LinkedList stateID=new LinkedList();
		LinkedList stateName=new LinkedList();
		
		String getStateDetails="select * from State where LAND1='"+custForm.getCountryId()+"'";
		ResultSet rsState=ad.selectQuery(getStateDetails);
		while(rsState.next())
		{
			stateID.add(rsState.getString("BLAND"));
			stateName.add(rsState.getString("BEZEI"));
		}
			
		custForm.setStateId(stateID);
		custForm.setStates(stateName);
		request.setAttribute("diplayStates", "diplayStates");
		}catch (Exception e) {
			e.printStackTrace();
		}
		String forwardType="display";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="customerMasterSAP";
			request.setAttribute("approved", "approved");
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="display";
		}
		custForm.setTypeDetails("Save");
		return mapping.findForward(forwardType);
	}
	
	
	
	public ActionForward sendMailToApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
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
    		String Customer_Type="";
    		
    		String getMatGroup="select Customer_Type from CUSTOMER_MASTER_M where REQUEST_NO='"+requestNo+"' ";
		 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
		 	while(rsMatGrup.next()){
		 		Customer_Type=rsMatGrup.getString("Customer_Type");
		 		
		 	}
		 	if(Customer_Type.equalsIgnoreCase("Exports"))
		 	{
		 		Customer_Type="Export";
		 	}
		 	
		 	int checkApprover=0;
		 	String pendingApprs="";
		 	
		 	
		 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='"+Customer_Type+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
    			 getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='' and app.Material_Type='Customer Master' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
			saveRecReq = saveRecReq + "'"+requestNo+"','Customer Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','Customer Master ')";
			int ij=ad.SqlExecuteUpdate(saveRecReq);
			if(!(parllelAppr1.equalsIgnoreCase(""))){
				
				String a[]=parllelAppr1.split(",");
				parllelAppr1=a[0];
			 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
			 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Customer Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','Customer Master ')";
			 ad.SqlExecuteUpdate(sendRecParllelAppr1);
			}
			if(!(parllelAppr2.equalsIgnoreCase(""))){
				String a[]=parllelAppr2.split(",");
				parllelAppr2=a[0];
				 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Customer Master','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','Customer Master')";
				 ad.SqlExecuteUpdate(sendRecParllelAppr2);
			}
			
			if(ij>0){
				custForm.setMessage2("Request No"+requestNo+". Submitted for approval successully.");
				custForm.setMessage("");
				String updateStatus="update CUSTOMER_MASTER_M set Approve_Status='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+requestNo+"'";
				ad.SqlExecuteUpdate(updateStatus);
				
				EMailer email = new EMailer();
				String approvermail="";
				String reqNo=Integer.toString(requestNo);
				email.sendMailToApprover(request, approvermail,reqNo, "Customer Master");
			}else{
				custForm.setMessage("Error while submiting approval...");
				custForm.setMessage2("");
			}
    		}else{
    			
    			custForm.setMessage("No approvers are assigned for submiting this request.");
    			custForm.setMessage2("");
    		}
			
			
			
		
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		custForm.setApproveType("Created");
		searchRecord(mapping, custForm, request, response);
		return mapping.findForward("customerList");
	}
	
	
	public ActionForward getRequiredSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			String requiredSearch=custForm.getSearchRequired();
			requiredSearch=requiredSearch.trim();
			LinkedList listOFCustomers=new LinkedList();
			  String getCustomersList="Select c.REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status,emp.emp_name,emp.dept_name,loc.LOCATION_CODE from  CUSTOMER_MASTER_M as c,emp_master as emp,Location as loc where (c.Approve_Status like '%"+requiredSearch+"%'" +
						" or c.REQUEST_NO like '%"+requiredSearch+"%' or  c.NAME like '%"+requiredSearch+"%' or c.CITY like '%"+requiredSearch+"%' or " +
						"c.Approve_Status like '%"+requiredSearch+"%' or  emp.emp_name like '%"+requiredSearch+"%' or loc.LOCATION_CODE like '%"+requiredSearch+"%' ) " +
								"and c.CREATED_BY='"+user.getEmployeeNo()+"' and c.CREATED_BY=emp.emp_id and emp.plant_id=loc.LOCID";
				
				
			  ResultSet rsCustomerList=ad.selectQuery(getCustomersList);
				while(rsCustomerList.next())
				{
					CustomerMasterForm custForm1=new CustomerMasterForm();
					custForm1.setRequestNumber(rsCustomerList.getInt("REQUEST_NO"));
					String requestDate=rsCustomerList.getString("REQUEST_DATE");
					String req[]=requestDate.split(" "); 
					requestDate=req[0];
					String a[]=requestDate.split("-");
					requestDate=a[2]+"/"+a[1]+"/"+a[0];
					custForm1.setRequestDate(requestDate);
					custForm1.setCustomerName(rsCustomerList.getString("NAME"));
					custForm1.setCity(rsCustomerList.getString("CITY"));
					custForm1.setRequestedBy(rsCustomerList.getString("emp_name"));
					custForm1.setDepartment(rsCustomerList.getString("dept_name"));
					custForm1.setLocationId(rsCustomerList.getString("LOCATION_CODE"));
					custForm1.setApproveType(rsCustomerList.getString("Approve_Status"));
					listOFCustomers.add(custForm1);
				}
			
				request.setAttribute("customerList", listOFCustomers);
				
				if(listOFCustomers.size()==0){
					request.setAttribute("noRecords", "noRecords");
					custForm.setMessage("No Records Found");
					custForm.setMessage2("");
				}
				
				
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("customerList");
	}
	
	
	
	public ActionForward setMasterData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		try{
			
			LinkedList cusGroupID = new LinkedList();
			LinkedList cusGroupList = new LinkedList();
			ResultSet rs1 = ad.selectQuery("select C_GROUP_ID,C_GROUP_NAME from Customer_Group ");
			while(rs1.next()) {
				cusGroupID.add(rs1.getString("C_GROUP_ID"));
				cusGroupList.add(rs1.getString("C_GROUP_NAME"));
			}
			custForm.setCusGroupID(cusGroupID);
			custForm.setCusGroupList(cusGroupList);	
			
			LinkedList priceGroupID = new LinkedList();
			LinkedList piceGroupList = new LinkedList();
			ResultSet rs2 = ad.selectQuery("select * from PRICE_GROUP ");
			while(rs2.next()) {
				priceGroupID.add(rs2.getString("P_GROUP_ID"));
				piceGroupList.add(rs2.getString("P_GROUP_NAME"));
			}
			custForm.setPriceGroupID(priceGroupID);
			custForm.setPiceGroupList(piceGroupList);	
			
			ResultSet rs8 = ad.selectQuery("select ACC_CLERK_ID," +
			"ACC_CLERK_DESC from ACC_CLERK_M where ACTIVE='True'");
			ArrayList accountClerkList=new ArrayList();
			ArrayList accountClerkLabelList=new ArrayList();
			
			while(rs8.next()) {
				accountClerkList.add(rs8.getString("ACC_CLERK_ID"));
				accountClerkLabelList.add(rs8.getString("ACC_CLERK_DESC"));
			}
			custForm.setAccountClerkList(accountClerkList);
			custForm.setAccountClerkLabelList(accountClerkLabelList);
			
			
			LinkedList priceListID = new LinkedList();
			LinkedList piceListValue = new LinkedList();
			ResultSet rs3 = ad.selectQuery("select * from PRICE_LIST ");
			while(rs3.next()) {
				priceListID.add(rs3.getString("P_List_ID"));
				piceListValue.add(rs3.getString("P_LIST_NAME"));
			}
			custForm.setPriceListID(priceListID);
			custForm.setPiceListValue(piceListValue);
			
			LinkedList taxTypeID = new LinkedList();
			LinkedList taxTypeValue = new LinkedList();
			ResultSet rs4 = ad.selectQuery("select * from TAX_CLASS ");
			while(rs4.next()) {
				taxTypeID.add(rs4.getString("T_CLASS_ID"));
				taxTypeValue.add(rs4.getString("T_CLASS_NAME"));
			}
			custForm.setTaxTypeID(taxTypeID);
			custForm.setTaxTypeValue(taxTypeValue);	
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("customerList");
	}	
	
	
	public ActionForward saveSAPCrationData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
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
	custForm.setAccountGroupList(accountGroupList);
	custForm.setAccountGroupLabelList(accountGroupLabelList);
			
			 
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			custForm.setCounID(countryID);
			custForm.setCountryName(countryName);
			
			LinkedList stateID=new LinkedList();
			LinkedList stateName=new LinkedList();
			String getStateDetails="select * from State ";
			ResultSet rsState=ad.selectQuery(getStateDetails);
			while(rsState.next())
			{
				stateID.add(rsState.getString("STATE_ID"));
				stateName.add(rsState.getString("STATE_NAME"));
			}
				
			
			LinkedList currencyID=new LinkedList();
			LinkedList cureencyNames=new LinkedList();
			String geCurrencyDetails="select * from Currency";
			ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
			while(rsCurrencyDetails.next())
			{
				currencyID.add(rsCurrencyDetails.getString("WAERS"));
				cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
			}
			custForm.setCurrenIds(currencyID);
			custForm.setCurrencys(cureencyNames);	
			
		LinkedList tdsID=new LinkedList();
		LinkedList tdsValue=new LinkedList();
		
		String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
		ResultSet rsTDs=ad.selectQuery(getTDs);
		while(rsTDs.next())
		{
			tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
			tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
		}
		
		custForm.setTdsIds(tdsID);
		custForm.setTdsCodes(tdsValue);
		EMicroUtils ut=new EMicroUtils();
		int requstNo=custForm.getRequestNumber();
		String editRecord="select * from CUSTOMER_MASTER_M where REQUEST_NO='"+requstNo+"'";
		ResultSet rsEditRecord=ad.selectQuery(editRecord);
		while(rsEditRecord.next())
		{
			
			custForm.setRequestNumber(rsEditRecord.getInt("REQUEST_NO"));
			String reqDate=rsEditRecord.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			custForm.setRequestDate(reqDate);
			custForm.setAccGroupId(rsEditRecord.getString("ACCOUNT_GROUP_ID"));
			custForm.setSales("");
			custForm.setAccounts("");
			String viewType=rsEditRecord.getString("VIEW_TYPE");
			if(viewType.equalsIgnoreCase("S"))
			{
				custForm.setSales("On");
			}
			if(viewType.equalsIgnoreCase("A"))
			{
				custForm.setAccounts("On");
			}
			custForm.setDomestic("");
			custForm.setExports("");
			
			String customertype=rsEditRecord.getString("Customer_Type");
			if(customertype.equalsIgnoreCase("Domestic"))
			{
				custForm.setDomestic("On");
			}
			if(customertype.equalsIgnoreCase("Exports"))
			{
				custForm.setExports("On");
			}
			custForm.setCustomerName(rsEditRecord.getString("NAME"));
			custForm.setAddress1(rsEditRecord.getString("ADDRESS1"));
			custForm.setAddress2(rsEditRecord.getString("ADDRESS2"));
			custForm.setAddress3(rsEditRecord.getString("ADDRESS3"));
			custForm.setAddress4(rsEditRecord.getString("ADDRESS4"));
			custForm.setCity(rsEditRecord.getString("CITY"));
			custForm.setPincode(rsEditRecord.getString("PINCODE"));
			
			custForm.setCountryId(rsEditRecord.getString("COUNTRY_ID"));
			
			
			
			custForm.setStateId(stateID);
			custForm.setStates(stateName);
			custForm.setState(rsEditRecord.getString("STATE"));
			custForm.setLandlineNo(rsEditRecord.getString("LANDLINE_NO"));
			custForm.setMobileNo(rsEditRecord.getString("MOBILE_NO"));
			custForm.setFaxNo(rsEditRecord.getString("FAX_NO"));
			custForm.setEmailId(rsEditRecord.getString("EMAIL_ID"));
			custForm.setCustomerGroup(rsEditRecord.getString("Customer_Group"));
			custForm.setPriceGroup(rsEditRecord.getString("Price_Group"));
			custForm.setPriceList(rsEditRecord.getString("Price_List"));
			custForm.setTaxType(rsEditRecord.getString("Tax_Type"));
			custForm.setCurrencyId(rsEditRecord.getString("CURRENCY_ID"));
			String tdsStatus=rsEditRecord.getString("IS_REGD_EXCISE_Customer");
			if(tdsStatus.equalsIgnoreCase("1"))
			{
				custForm.setTdsStatus("True");
				request.setAttribute("setTdsState", "setTdsState");
			}
			if(tdsStatus.equalsIgnoreCase("0"))
				custForm.setTdsStatus("False");
				
			custForm.setTdsCode(rsEditRecord.getString("TDS_CODE"));
			custForm.setListNumber(rsEditRecord.getString("LST_NO"));
			custForm.setTinNumber(rsEditRecord.getString("TIN_NO"));
			custForm.setCstNumber(rsEditRecord.getString("CST_NO"));
			custForm.setPanNumber(rsEditRecord.getString("PAN_No"));
			custForm.setServiceTaxNo(rsEditRecord.getString("Service_Tax_Registration_No"));
			String isRegdExciseVender=rsEditRecord.getString("IS_REGD_EXCISE_VENDOR");
			if(isRegdExciseVender.equalsIgnoreCase("1"))
			{
				custForm.setIsRegdExciseVender("True");
				request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
			}
			if(isRegdExciseVender.equalsIgnoreCase("0"))
			{
				custForm.setIsRegdExciseVender("False");
				request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
			}
			custForm.setEccNo(rsEditRecord.getString("ECC_No"));
			custForm.setExciseRegNo(rsEditRecord.getString("Excise_Reg_No"));
			custForm.setExciseRange(rsEditRecord.getString("Excise_Range"));
			custForm.setExciseDivision(rsEditRecord.getString("Excise_Division"));
			custForm.setDlno1(rsEditRecord.getString("DLNO1"));
			custForm.setDlno2(rsEditRecord.getString("DLNO2"));
			custForm.setPaymentTermID(rsEditRecord.getString("PAYMENT_TERM_ID"));
			custForm.setAccountClerkId(rsEditRecord.getString("ACCOUNT_CLERK_ID"));
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
				fileList.add(custForm2);
				}
			request.setAttribute("listName", fileList);
			}
			
			if(user.getId()==1)
				{	
			//	custForm.setApproveType(rsEditRecord.getString("Approve_Status"));
					request.setAttribute("approved", "approved");
				}
			}
		
		int i=0;
		int userId=user.getId();
		if(userId==2)
			
		{
			 String sapCreationDate=custForm.getSapCreationDate();
			  String b[]=sapCreationDate.split("/");
			  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
			String updateMaterial="update CUSTOMER_MASTER_M set SAP_CODE_NO='"+custForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+custForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+custForm.getSapCreatedBy()+"' where REQUEST_NO='"+custForm.getRequestNumber()+"'";
			i=ad.SqlExecuteUpdate(updateMaterial);
			if(i>0)
			{
				custForm.setMessage2("Code creation request updated with request number='"+custForm.getRequestNumber()+"'");
				custForm.setMessage("");
				custForm.setTypeDetails("Update");
			}else{
				custForm.setMessage("Error...When updating code creation reequest.Please Check");
				custForm.setMessage2("");
				custForm.setTypeDetails("Update");
			} 
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		custForm.setTypeDetails("Update");
		request.setAttribute("diplayStates", "diplayStates");
		
		displayCustomerList(mapping, form, request, response);
		return mapping.findForward("customerList");
	
		
		
	}
	
	
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
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
			custForm.setCounID(countryID);
			custForm.setCountryName(countryName);
			
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	custForm.setAccountGroupList(accountGroupList);
	custForm.setAccountGroupLabelList(accountGroupLabelList);
			LinkedList stateID=new LinkedList();
			LinkedList stateName=new LinkedList();
			String getStateDetails="select * from State ";
			ResultSet rsState=ad.selectQuery(getStateDetails);
			while(rsState.next())
			{
				stateID.add(rsState.getString("BLAND"));
				stateName.add(rsState.getString("BEZEI"));
			}
				
			
			LinkedList currencyID=new LinkedList();
			LinkedList cureencyNames=new LinkedList();
			String geCurrencyDetails="select * from Currency";
			ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
			while(rsCurrencyDetails.next())
			{
				currencyID.add(rsCurrencyDetails.getString("WAERS"));
				cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
			}
			custForm.setCurrenIds(currencyID);
			custForm.setCurrencys(cureencyNames);	
			
		LinkedList tdsID=new LinkedList();
		LinkedList tdsValue=new LinkedList();
		
		String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
		ResultSet rsTDs=ad.selectQuery(getTDs);
		while(rsTDs.next())
		{
			tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
			tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
		}
		
		custForm.setTdsIds(tdsID);
		custForm.setTdsCodes(tdsValue);
		EMicroUtils ut=new EMicroUtils();
		int requstNo=custForm.getRequestNumber();
		String editRecord="select * from CUSTOMER_MASTER_M where REQUEST_NO='"+requstNo+"'";
		ResultSet rsEditRecord=ad.selectQuery(editRecord);
		while(rsEditRecord.next())
		{
			
			custForm.setRequestNumber(rsEditRecord.getInt("REQUEST_NO"));
			String reqDate=rsEditRecord.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			custForm.setRequestDate(reqDate);
			custForm.setAccGroupId(rsEditRecord.getString("ACCOUNT_GROUP_ID"));
			custForm.setSales("");
			custForm.setAccounts("");
			String viewType=rsEditRecord.getString("VIEW_TYPE");
			if(viewType.equalsIgnoreCase("S"))
			{
				custForm.setSales("On");
			}
			if(viewType.equalsIgnoreCase("A"))
			{
				custForm.setAccounts("On");
			}
			custForm.setDomestic("");
			custForm.setExports("");
			
			String customertype=rsEditRecord.getString("Customer_Type");
			if(customertype.equalsIgnoreCase("Domestic"))
			{
				custForm.setDomestic("On");
			}
			if(customertype.equalsIgnoreCase("Exports"))
			{
				custForm.setExports("On");
			}
			custForm.setCustomerName(rsEditRecord.getString("NAME"));
			custForm.setAddress1(rsEditRecord.getString("ADDRESS1"));
			custForm.setAddress2(rsEditRecord.getString("ADDRESS2"));
			custForm.setAddress3(rsEditRecord.getString("ADDRESS3"));
			custForm.setAddress4(rsEditRecord.getString("ADDRESS4"));
			custForm.setCity(rsEditRecord.getString("CITY"));
			custForm.setPincode(rsEditRecord.getString("PINCODE"));
			
			custForm.setCountryId(rsEditRecord.getString("COUNTRY_ID"));
			
			
			
			custForm.setStateId(stateID);
			custForm.setStates(stateName);
			custForm.setState(rsEditRecord.getString("STATE"));
			custForm.setLandlineNo(rsEditRecord.getString("LANDLINE_NO"));
			custForm.setMobileNo(rsEditRecord.getString("MOBILE_NO"));
			custForm.setFaxNo(rsEditRecord.getString("FAX_NO"));
			custForm.setEmailId(rsEditRecord.getString("EMAIL_ID"));
			custForm.setCustomerGroup(rsEditRecord.getString("Customer_Group"));
			custForm.setPriceGroup(rsEditRecord.getString("Price_Group"));
			custForm.setPriceList(rsEditRecord.getString("Price_List"));
			custForm.setTaxType(rsEditRecord.getString("Tax_Type"));
			custForm.setCurrencyId(rsEditRecord.getString("CURRENCY_ID"));
			String tdsStatus=rsEditRecord.getString("IS_REGD_EXCISE_Customer");
			if(tdsStatus.equalsIgnoreCase("1"))
			{
				custForm.setTdsStatus("True");
				request.setAttribute("setTdsState", "setTdsState");
			}
			if(tdsStatus.equalsIgnoreCase("0"))
				custForm.setTdsStatus("False");
				
			custForm.setTdsCode(rsEditRecord.getString("TDS_CODE"));
			custForm.setListNumber(rsEditRecord.getString("LST_NO"));
			custForm.setTinNumber(rsEditRecord.getString("TIN_NO"));
			custForm.setCstNumber(rsEditRecord.getString("CST_NO"));
			custForm.setPanNumber(rsEditRecord.getString("PAN_No"));
			custForm.setServiceTaxNo(rsEditRecord.getString("Service_Tax_Registration_No"));
			String isRegdExciseVender=rsEditRecord.getString("IS_REGD_EXCISE_VENDOR");
			if(isRegdExciseVender.equalsIgnoreCase("1"))
			{
				custForm.setIsRegdExciseVender("True");
				request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
			}
			if(isRegdExciseVender.equalsIgnoreCase("0"))
			{
				custForm.setIsRegdExciseVender("False");
				request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
			}
			custForm.setEccNo(rsEditRecord.getString("ECC_No"));
			custForm.setExciseRegNo(rsEditRecord.getString("Excise_Reg_No"));
			custForm.setExciseRange(rsEditRecord.getString("Excise_Range"));
			custForm.setExciseDivision(rsEditRecord.getString("Excise_Division"));
			custForm.setDlno1(rsEditRecord.getString("DLNO1"));
			custForm.setDlno2(rsEditRecord.getString("DLNO2"));
			custForm.setPaymentTermID(rsEditRecord.getString("PAYMENT_TERM_ID"));
			custForm.setAccountClerkId(rsEditRecord.getString("ACCOUNT_CLERK_ID"));
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
				fileList.add(custForm2);
				}
			request.setAttribute("listName", fileList);
			}
			
			if(user.getId()==1)
				{	
			//	custForm.setApproveType(rsEditRecord.getString("Approve_Status"));
					request.setAttribute("approved", "approved");
				}
			}
		
		int i=0;
		int userId=user.getId();
		if(userId==1)
			
		{
			String currentdate=EMicroUtils.getCurrentSysDate();
			  String a[]=currentdate.split("/");
			  for(int j=0;j<a.length;j++)
			  {
				  System.out.println("a="+a[j]);
			  }
			  currentdate=a[2]+"-"+a[1]+"-"+a[0];
			String updateMaterial="update CUSTOMER_MASTER_M set Approve_Status='"+custForm.getApproveType()+"',approve_date='"+currentdate+"',last_approver='"+user.getFullName()+"',pending_approver='No'  where REQUEST_NO='"+custForm.getRequestNumber()+"'";
			i=ad.SqlExecuteUpdate(updateMaterial);
			if(i>0)
			{
				custForm.setMessage2("Code creation request updated with request number='"+custForm.getRequestNumber()+"'");
				custForm.setMessage("");
				custForm.setTypeDetails("Update");
			}else{
				custForm.setMessage("Error...When updating code creation reequest.Please Check");
				custForm.setMessage2("");
				custForm.setTypeDetails("Update");
			} 
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		custForm.setTypeDetails("Update");
		request.setAttribute("diplayStates", "diplayStates");
		
		displayCustomerList(mapping, form, request, response);
		return mapping.findForward("customerList");
	}
	
	public ActionForward firstCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList customerList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		try{
			int totalRecords=custForm.getTotalRecords();//21
			int startRecord=custForm.getStartRecord();//11
			int endRecord=custForm.getEndRecord();	
			
			
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  custForm.setTotalRecords(totalRecords);
				  custForm.setStartRecord(startRecord);
				  custForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  custForm.setTotalRecords(totalRecords);
					  custForm.setStartRecord(startRecord);
					  custForm.setEndRecord(totalRecords);  
				  }

			  String status=custForm.getApproveType();
			 if(status.equalsIgnoreCase(""))
				{
					  
				 String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY c.ID) AS RowNum,c.REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status,emp.EMP_FULLNAME," +
		 	"dept.DPTSTXT,loc.LOCATION_CODE  from  CUSTOMER_MASTER_M as c,emp_official_info as emp,Location as loc ,DEPARTMENT as dept   where Approve_Status='In Process' " +
		 	"and CREATED_BY='"+user.getEmployeeNo()+"' and c.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCID and emp.DPTID=dept.DPTID) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			
				 
			ResultSet rs=ad.selectQuery(getRecord);

			while(rs.next())
			{
				CustomerMasterForm custForm1=new CustomerMasterForm();
				custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
				String requestDate=rs.getString("REQUEST_DATE");
				String req[]=requestDate.split(" "); 
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				custForm1.setRequestDate(requestDate);
				custForm1.setCustomerName(rs.getString("NAME"));
				custForm1.setCity(rs.getString("CITY"));
				custForm1.setRequestedBy(rs.getString("EMP_FULLNAME"));
				custForm1.setDepartment(rs.getString("DPTSTXT"));
				custForm1.setLocationId(rs.getString("LOCATION_CODE"));
				custForm1.setApproveType(rs.getString("Approve_Status"));
				customerList.add(custForm1);
			}
			request.setAttribute("customerList", customerList);
				
				}else{
							
					String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
					"from CUSTOMER_MASTER_M where Approve_Status='"+status+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			ResultSet rs=ad.selectQuery(getRecord);

			while(rs.next())
			{
				CustomerMasterForm custForm1=new CustomerMasterForm();
				custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
				String requestDate=rs.getString("REQUEST_DATE");
				String req[]=requestDate.split(" "); 
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				custForm1.setRequestDate(requestDate);
				custForm1.setCustomerName(rs.getString("NAME"));
				custForm1.setCity(rs.getString("CITY"));
				custForm1.setApproveType(rs.getString("Approve_Status"));
				customerList.add(custForm1);
			}
			request.setAttribute("customerList", customerList);
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
	return mapping.findForward("customerList");
	}
	public ActionForward lastCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=Integer.parseInt(user.getEmployeeNo());
		LinkedList customerList=new LinkedList();
		try{
			int totalRecords=custForm.getTotalRecords();//21
			int startRecord=custForm.getStartRecord();//11
			int endRecord=custForm.getEndRecord();	
			
			
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 custForm.setTotalRecords(totalRecords);
			 custForm.setStartRecord(startRecord);
			 custForm.setEndRecord(totalRecords);
			  String status=custForm.getApproveType();
			 if(status.equalsIgnoreCase(""))
				{
					  
				 String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY c.ID) AS RowNum,c.REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status,emp.EMP_FULLNAME," +
		 	"dept.DPTSTXT,loc.LOCATION_CODE  from  CUSTOMER_MASTER_M as c,emp_official_info as emp,Location as loc ,DEPARTMENT as dept   where Approve_Status='In Process' " +
		 	"and CREATED_BY='"+user.getEmployeeNo()+"' and c.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCID and emp.DPTID=dept.DPTID) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				 
				 
			ResultSet rs=ad.selectQuery(getRecord);

			while(rs.next())
			{
				CustomerMasterForm custForm1=new CustomerMasterForm();
				custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
				String requestDate=rs.getString("REQUEST_DATE");
				String req[]=requestDate.split(" "); 
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				custForm1.setRequestDate(requestDate);
				custForm1.setCustomerName(rs.getString("NAME"));
				custForm1.setCity(rs.getString("CITY"));
				custForm1.setRequestedBy(rs.getString("EMP_FULLNAME"));
				custForm1.setDepartment(rs.getString("DPTSTXT"));
				custForm1.setLocationId(rs.getString("LOCATION_CODE"));
				custForm1.setApproveType(rs.getString("Approve_Status"));
				customerList.add(custForm1);
			}
			request.setAttribute("customerList", customerList);
				
				}else{
							
					String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
					"from CUSTOMER_MASTER_M where Approve_Status='"+status+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
					
					 if(userID==1 ||userID==2)
					 {
						 getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
							"from CUSTOMER_MASTER_M where Approve_Status='"+status+"' ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
							
					 }

					ResultSet rs=ad.selectQuery(getRecord);
					
					while(rs.next())
					{
						CustomerMasterForm custForm1=new CustomerMasterForm();
						custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
						String requestDate=rs.getString("REQUEST_DATE");
						String req[]=requestDate.split(" "); 
						requestDate=req[0];
						String a[]=requestDate.split("-");
						requestDate=a[2]+"/"+a[1]+"/"+a[0];
						custForm1.setRequestDate(requestDate);
						custForm1.setCustomerName(rs.getString("NAME"));
						custForm1.setCity(rs.getString("CITY"));
						custForm1.setApproveType(rs.getString("Approve_Status"));
						customerList.add(custForm1);
					}
					request.setAttribute("customerList", customerList);
				}
			 request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton");
				if(customerList.size()<10)
				{
					
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				request.setAttribute("displayRecordNo", "displayRecordNo");
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("customerList");
	}
	
	
	public ActionForward previousCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		LinkedList customerList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
	try{
		int totalRecords=custForm.getTotalRecords();//21
		int endRecord=custForm.getStartRecord()-1;//20
		int startRecord=custForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		
		  custForm.setTotalRecords(totalRecords);
		  custForm.setStartRecord(1);
		  custForm.setEndRecord(10);
		  String status=custForm.getApproveType();
	if(status.equalsIgnoreCase(""))
	{
		  
		String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY c.ID) AS RowNum,c.REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status,emp.EMP_FULLNAME," +
		 	"dept.DPTSTXT,loc.LOCATION_CODE  from  CUSTOMER_MASTER_M as c,emp_official_info as emp,Location as loc ,DEPARTMENT as dept   where Approve_Status='In Process' " +
		 	"and CREATED_BY='"+user.getEmployeeNo()+"' and c.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCID and emp.DPTID=dept.DPTID) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";

ResultSet rs=ad.selectQuery(getRecord);

while(rs.next())
{
	CustomerMasterForm custForm1=new CustomerMasterForm();
	custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
	String requestDate=rs.getString("REQUEST_DATE");
	String req[]=requestDate.split(" "); 
	requestDate=req[0];
	String a[]=requestDate.split("-");
	requestDate=a[2]+"/"+a[1]+"/"+a[0];
	custForm1.setRequestDate(requestDate);
	custForm1.setCustomerName(rs.getString("NAME"));
	custForm1.setCity(rs.getString("CITY"));
	custForm1.setRequestedBy(rs.getString("EMP_FULLNAME"));
	custForm1.setDepartment(rs.getString("DPTSTXT"));
	custForm1.setLocationId(rs.getString("LOCATION_CODE"));
	custForm1.setApproveType(rs.getString("Approve_Status"));
	customerList.add(custForm1);
}
request.setAttribute("customerList", customerList);

	
	}else{
				
		String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
		"from CUSTOMER_MASTER_M where Approve_Status='"+status+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
		if(userID==1||userID==2)
		{
			getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
			"from CUSTOMER_MASTER_M where Approve_Status='Pending'  ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";

		}
		ResultSet rs=ad.selectQuery(getRecord);
		
		while(rs.next())
		{
			CustomerMasterForm custForm1=new CustomerMasterForm();
			custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" "); 
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			custForm1.setRequestDate(requestDate);
			custForm1.setCustomerName(rs.getString("NAME"));
			custForm1.setCity(rs.getString("CITY"));
			custForm1.setApproveType(rs.getString("Approve_Status"));
			customerList.add(custForm1);
		}
		request.setAttribute("customerList", customerList);
	}
	custForm.setTotalRecords(totalRecords);
	custForm.setStartRecord(startRecord);
	custForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(customerList.size()<10)
			{
				custForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("customerList");
	}
	
	
	public ActionForward nextCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		LinkedList customerList=new LinkedList();
	try{
		int totalRecords=custForm.getTotalRecords();//21
		int startRecord=custForm.getStartRecord();//11
		int endRecord=custForm.getEndRecord();
	
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
		String status=custForm.getApproveType();
		if(status.equalsIgnoreCase(""))
		{
			String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY c.ID) AS RowNum,c.REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status,emp.EMP_FULLNAME," +
		 	"dept.DPTSTXT,loc.LOCATION_CODE  from  CUSTOMER_MASTER_M as c,emp_official_info as emp,Location as loc ,DEPARTMENT as dept   where Approve_Status='In Process' " +
		 	"and CREATED_BY='"+user.getEmployeeNo()+"' and c.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCID and emp.DPTID=dept.DPTID) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
	
	
	
			ResultSet rs=ad.selectQuery(getRecord);
	
	while(rs.next())
	{
		CustomerMasterForm custForm1=new CustomerMasterForm();
		custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
		String requestDate=rs.getString("REQUEST_DATE");
		String req[]=requestDate.split(" "); 
		requestDate=req[0];
		String a[]=requestDate.split("-");
		requestDate=a[2]+"/"+a[1]+"/"+a[0];
		custForm1.setRequestDate(requestDate);
		custForm1.setCustomerName(rs.getString("NAME"));
		custForm1.setCity(rs.getString("CITY"));
		custForm1.setRequestedBy(rs.getString("EMP_FULLNAME"));
		custForm1.setDepartment(rs.getString("DPTSTXT"));
		custForm1.setLocationId(rs.getString("LOCATION_CODE"));
		custForm1.setApproveType(rs.getString("Approve_Status"));
		customerList.add(custForm1);
	}
	request.setAttribute("customerList", customerList);
		}
		else{
			String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_DATE,REQUEST_NO,NAME,CITY,Approve_Status " +
			"from CUSTOMER_MASTER_M where  CREATED_BY='"+user.getEmployeeNo()+"' and Approve_Status='"+status+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
	ResultSet rs=ad.selectQuery(getRecord);
	
	while(rs.next())
	{
		CustomerMasterForm custForm1=new CustomerMasterForm();
		custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
		String requestDate=rs.getString("REQUEST_DATE");
		String req[]=requestDate.split(" "); 
		requestDate=req[0];
		String a[]=requestDate.split("-");
		requestDate=a[2]+"/"+a[1]+"/"+a[0];
		custForm1.setRequestDate(requestDate);
		custForm1.setCustomerName(rs.getString("NAME"));
		custForm1.setCity(rs.getString("CITY"));
		custForm1.setApproveType(rs.getString("Approve_Status"));
		customerList.add(custForm1);
	}
	request.setAttribute("customerList", customerList);
			
			
		}
		
		}
		System.out.println("list length="+customerList.size());
		
		 if(customerList.size()!=0)
			{
			 custForm.setTotalRecords(totalRecords);
			 custForm.setStartRecord(startRecord);
			 custForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				custForm.setTotalRecords(totalRecords);
				custForm.setStartRecord(start);
				custForm.setEndRecord(end);
				
			}
		 if(customerList.size()<10)
		 {
			 custForm.setTotalRecords(totalRecords);
			 custForm.setStartRecord(startRecord);
			 custForm.setEndRecord(startRecord+customerList.size()-1);
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
		
		return mapping.findForward("customerList");
	}
	
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		  int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		try{
			int recordsTotal=0;
		String status=custForm.getApproveType();
		
		String getVendorCount="Select count(*) from CUSTOMER_MASTER_M where CREATED_BY='"+user.getEmployeeNo()+"' and  Approve_Status='"+status+"'";
		
		ResultSet rsVendorCount=ad.selectQuery(getVendorCount);
		while(rsVendorCount.next())
		{
			recordsTotal=rsVendorCount.getInt(1);
		}
		System.out.println("Toatal Records="+recordsTotal);	    	 
		 if(recordsTotal>10)
		 {
			 custForm.setTotalRecords(recordsTotal);
		 startRecord=1;
		 endRecord=10;
		 custForm.setStartRecord(1);
		 custForm.setEndRecord(10);
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		 request.setAttribute("displayRecordNo", "displayRecordNo");
		 request.setAttribute("nextButton", "nextButton");
		 }else
		 {
			  startRecord=1;
			  endRecord=totalRecords;
			  custForm.setTotalRecords(totalRecords);
			  custForm.setStartRecord(1);
			  custForm.setEndRecord(totalRecords); 
		 }
		 
		 String getRecord="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY c.ID) AS  RowNum,c.REQUEST_DATE,c.REQUEST_NO,c.NAME,c.CITY,c.Approve_Status,emp.EMP_FULLNAME," +
		 	"dept.DPTSTXT,loc.LOCATION_CODE  from  CUSTOMER_MASTER_M as c,emp_official_info as emp,Location as loc ,DEPARTMENT as dept   where Approve_Status='"+status+"' " +
		 	"and CREATED_BY='"+user.getEmployeeNo()+"' and c.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCATION_CODE and emp.DPTID=dept.DPTID ) as  sub Where  sub.RowNum between 1 and 10";
		ResultSet rs=ad.selectQuery(getRecord);
		LinkedList customerList=new LinkedList();
		while(rs.next())
		{
			CustomerMasterForm custForm1=new CustomerMasterForm();
			custForm1.setRequestNumber(rs.getInt("REQUEST_NO"));
			String requestDate=rs.getString("REQUEST_DATE");
			String req[]=requestDate.split(" "); 
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			custForm1.setRequestDate(requestDate);
			custForm1.setCustomerName(rs.getString("NAME"));
			custForm1.setCity(rs.getString("CITY"));
			custForm1.setRequestedBy(rs.getString("EMP_FULLNAME")); 
			custForm1.setDepartment(rs.getString("DPTSTXT"));
			custForm1.setLocationId(rs.getString("LOCATION_CODE"));
			custForm1.setApproveType(rs.getString("Approve_Status"));
			customerList.add(custForm1);
		}
		request.setAttribute("customerList", customerList);
		if(customerList.size()==0){
			request.setAttribute("noRecords", "noRecords");
			custForm.setMessage1("No Records Found");
			custForm.setMessage2("");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		String module="ESS"; 
		request.setAttribute("MenuIcon", module);
		return mapping.findForward("customerList");
	}
	
	
	
	public ActionForward updateCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String fileList="";
		String getUploadedFiles="select * from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+user.getEmployeeNo()+"'";
		try{
		ResultSet rs=ad.selectQuery(getUploadedFiles);
		while(rs.next())
		{
			fileList+="Master"+"/Customer Master"+"/UploadFiles"+"/"+rs.getString("file_name")+",";
		}
		
		String viewType="";
		String sales=null;
		String accounts=null;
		
		sales=custForm.getSales();
		accounts=custForm.getAccounts();
		
		if(sales!=null)
		{
				viewType="S";
		}
			if(accounts!=null){
					viewType="A";
			}
			System.out.println("View Type="+viewType);
		String customerType="";
		String domestic=null;
		String exports=null;
		domestic=custForm.getDomestic();
		exports=custForm.getExports();
	      if(domestic!=null)
	      {
					customerType="Domestic";
				
	      }
			if(exports!=null)
			{
					customerType="Exports";
			}
			Calendar currentDate = Calendar.getInstance();
			  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
			  String dateNow = formatter.format(currentDate.getTime());
			  String reqDate=custForm.getRequestDate();
	
			
			
		String updateCustomerData="upate CUSTOMER_MASTER_M set REQUEST_DATE='"+reqDate+"',ACCOUNT_GROUP_ID='"+custForm.getAccGroupId()+"',VIEW_TYPE='"+viewType+"',Customer_Type='"+customerType+"',NAME='"+custForm.getCustomerName()+"',ADDRESS1='"+custForm.getAddress1()+"',ADDRESS2='"+custForm.getAddress2()+"'," +
			"ADDRESS3='"+custForm.getAddress3()+"',ADDRESS4='"+custForm.getAddress4()+"',CITY='"+custForm.getCity()+"',PINCODE='"+custForm.getPincode()+"',COUNTRY_ID='"+custForm.getCountryId()+"',STATE='"+custForm.getState()+"',LANDLINE_NO='"+custForm.getLandlineNo()+"',MOBILE_NO='"+custForm.getMobileNo()+"',FAX_NO='"+custForm.getFaxNo()+"',EMAIL_ID='"+custForm.getEmailId()+"',Customer_Group='"+custForm.getCustomerGroup()+"',Price_Group='"+custForm.getPriceGroup()+"',Price_List='"+custForm.getPriceList()+"'," +
			"Tax_Type='"+custForm.getTaxType()+"',CURRENCY_ID='"+custForm.getCurrencyId()+"',IS_REGD_EXCISE_Customer='"+custForm.getTdsStatus()+"',TDS_CODE='"+custForm.getTdsCode()+"',LST_NO='"+custForm.getListNumber()+"',TIN_NO='"+custForm.getTinNumber()+"',CST_NO='"+custForm.getCstNumber()+"',PAN_No='"+custForm.getPanNumber()+"',Service_Tax_Registration_No='"+custForm.getServiceTaxNo()+"',IS_REGD_EXCISE_VENDOR='"+custForm.getIsRegdExciseVender()+"',ECC_No='"+custForm.getEccNo()+"',Excise_Reg_No='"+custForm.getExciseRegNo()+"'," +
			"Excise_Range='"+custForm.getExciseRange()+"',Excise_Division='"+custForm.getExciseDivision()+"',DLNO1='"+custForm.getDlno1()+"',DLNO2='"+custForm.getDlno2()+"',PAYMENT_TERM_ID='"+custForm.getPaymentTermID()+"',ACCOUNT_CLERK_ID='"+custForm.getAccountClerkId()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Attachments='"+fileList+"' where  REQUEST_NO='"+custForm.getRequestNumber()+"'";
		
		int i=0;
		i=ad.SqlExecuteUpdate(updateCustomerData);
		if(i>0)
		{
			custForm.setMessage2("Customer Master Data Updated");
			custForm.setMessage("");
		}
		else{
			
			custForm.setMessage("Error When Updating Customer Master");
			custForm.setMessage2("");
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("updateCustomerForm" );
	}
	
	public ActionForward editCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			
		
		
		ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
		"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
		ArrayList paymentTermList=new ArrayList();
		ArrayList paymentTermLabelList=new ArrayList();
		
		while(rs7.next()) {
			paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
			paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
		}
		custForm.setPaymentTermList(paymentTermList);
		custForm.setPaymentTermLabelList(paymentTermLabelList);
		
		ArrayList accountGroupList=new ArrayList();
		ArrayList accountGroupLabelList=new ArrayList();
	 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
ArrayList subLinkIdList = new ArrayList();
ArrayList subLinkValueList = new ArrayList();
while(rs1.next()) {
	accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
	accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
}
custForm.setAccountGroupList(accountGroupList);
custForm.setAccountGroupLabelList(accountGroupLabelList);
			
			 
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			custForm.setCounID(countryID);
			custForm.setCountryName(countryName);
			
			
			LinkedList currencyID=new LinkedList();
			LinkedList cureencyNames=new LinkedList();
			String geCurrencyDetails="select * from Currency";
			ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
			while(rsCurrencyDetails.next())
			{
				currencyID.add(rsCurrencyDetails.getString("WAERS"));
				cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
			}
			custForm.setCurrenIds(currencyID);
			custForm.setCurrencys(cureencyNames);	
			
		LinkedList tdsID=new LinkedList();
		LinkedList tdsValue=new LinkedList();
		
		String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
		ResultSet rsTDs=ad.selectQuery(getTDs);
		while(rsTDs.next())
		{
			tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
			tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
		}
		
		custForm.setTdsIds(tdsID);
		custForm.setTdsCodes(tdsValue);
		setMasterData(mapping, form, request, response);
		
		EMicroUtils ut=new EMicroUtils();
		int requstNo=Integer.parseInt(request.getParameter("requstNo"));
		String editRecord="select * from CUSTOMER_MASTER_M where REQUEST_NO='"+requstNo+"'";
		ResultSet rsEditRecord=ad.selectQuery(editRecord);
		while(rsEditRecord.next())
		{
			
			custForm.setRequestNumber(rsEditRecord.getInt("REQUEST_NO"));
			String reqDate=rsEditRecord.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			custForm.setRequestDate(reqDate);
			custForm.setAccGroupId(rsEditRecord.getString("ACCOUNT_GROUP_ID"));
			custForm.setSales("");
			custForm.setAccounts("");
			String viewType=rsEditRecord.getString("VIEW_TYPE");
			if(viewType.equalsIgnoreCase("S"))
			{
				custForm.setSales("On");
			}
			if(viewType.equalsIgnoreCase("A"))
			{
				custForm.setAccounts("On");
			}
			if(viewType.equalsIgnoreCase("3"))
			{
				custForm.setSales("On");
				custForm.setAccounts("On");
			}
			
			custForm.setDomestic("");
			custForm.setExports("");
			
			String customertype=rsEditRecord.getString("Customer_Type");
			if(customertype.equalsIgnoreCase("Domestic"))
			{
				custForm.setDomestic("On");
			}
			if(customertype.equalsIgnoreCase("Exports"))
			{
				custForm.setExports("On");
			}
			custForm.setCustomerName(rsEditRecord.getString("NAME"));
			custForm.setAddress1(rsEditRecord.getString("ADDRESS1"));
			custForm.setAddress2(rsEditRecord.getString("ADDRESS2"));
			custForm.setAddress3(rsEditRecord.getString("ADDRESS3"));
			custForm.setAddress4(rsEditRecord.getString("ADDRESS4"));
			custForm.setCity(rsEditRecord.getString("CITY"));
			custForm.setPincode(rsEditRecord.getString("PINCODE"));
			
			custForm.setCountryId(rsEditRecord.getString("COUNTRY_ID"));
			
			
			
		
			custForm.setState(rsEditRecord.getString("STATE"));
			custForm.setLandlineNo(rsEditRecord.getString("LANDLINE_NO"));
			custForm.setMobileNo(rsEditRecord.getString("MOBILE_NO"));
			custForm.setFaxNo(rsEditRecord.getString("FAX_NO"));
			custForm.setEmailId(rsEditRecord.getString("EMAIL_ID"));
			custForm.setCustomerGroup(rsEditRecord.getString("Customer_Group"));
			custForm.setPriceGroup(rsEditRecord.getString("Price_Group"));
			custForm.setPriceList(rsEditRecord.getString("Price_List"));
			custForm.setTaxType(rsEditRecord.getString("Tax_Type"));
			custForm.setCurrencyId(rsEditRecord.getString("CURRENCY_ID"));
			String tdsStatus=rsEditRecord.getString("IS_REGD_EXCISE_Customer");
			if(tdsStatus.equalsIgnoreCase("1"))
			{
				custForm.setTdsStatus("True");
				request.setAttribute("setTdsState", "setTdsState");
			}
			if(tdsStatus.equalsIgnoreCase("0"))
				custForm.setTdsStatus("False");
				
			custForm.setTdsCode(rsEditRecord.getString("TDS_CODE"));
			custForm.setListNumber(rsEditRecord.getString("LST_NO"));
			custForm.setTinNumber(rsEditRecord.getString("TIN_NO"));
			custForm.setCstNumber(rsEditRecord.getString("CST_NO"));
			custForm.setPanNumber(rsEditRecord.getString("PAN_No"));
			custForm.setServiceTaxNo(rsEditRecord.getString("Service_Tax_Registration_No"));
			String isRegdExciseVender=rsEditRecord.getString("IS_REGD_EXCISE_VENDOR");
			if(isRegdExciseVender.equalsIgnoreCase("1"))
			{
				custForm.setIsRegdExciseVender("True");
				request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
			}
			if(isRegdExciseVender.equalsIgnoreCase("0"))
			{
				custForm.setIsRegdExciseVender("False");
				request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
			}
			custForm.setEccNo(rsEditRecord.getString("ECC_No"));
			custForm.setExciseRegNo(rsEditRecord.getString("Excise_Reg_No"));
			custForm.setExciseRange(rsEditRecord.getString("Excise_Range"));
			custForm.setExciseDivision(rsEditRecord.getString("Excise_Division"));
			custForm.setDlno1(rsEditRecord.getString("DLNO1"));
			custForm.setDlno2(rsEditRecord.getString("DLNO2"));
			custForm.setPaymentTermID(rsEditRecord.getString("PAYMENT_TERM_ID"));
			custForm.setAccountClerkId(rsEditRecord.getString("ACCOUNT_CLERK_ID"));
			custForm.setCutomerCode(rsEditRecord.getString("cutomer_code"));
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
				custForm2.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/"+uploadedFiles+"");
				fileList.add(custForm2);
				}
			request.setAttribute("listName", fileList);
			}
			
			if(user.getId()==1)
				{	
				custForm.setApproveType(rsEditRecord.getString("Approve_Status"));
					request.setAttribute("approved", "approved");
				}
			if(user.getId()==2)//SAP Creatore
			{
				custForm.setApproveType(rsEditRecord.getString("Approve_Status"));
				request.setAttribute("approved", "approved");
			}
			}
	
		custForm.setTypeDetails("Update");
		
		LinkedList stateID=new LinkedList();
		LinkedList stateName=new LinkedList();
		
		String getStateDetails="select * from State where LAND1='"+custForm.getCountryId()+"'";
		ResultSet rsState=ad.selectQuery(getStateDetails);
		while(rsState.next())
		{
			stateID.add(rsState.getString("BLAND"));
			stateName.add(rsState.getString("BEZEI"));
		}
			
		custForm.setStateId(stateID);
		custForm.setStates(stateName);
		request.setAttribute("diplayStates", "diplayStates");
		}catch (Exception e) {
			e.printStackTrace();
		}
		String forwardType="display";
		
		if(user.getId()==1)//Approver
		{
			forwardType="approverMaster";
		
		}
		if(user.getId()==2)//SAP Creatore
		{
			forwardType="customerMasterSAP";
			request.setAttribute("approved", "approved");
		}
		if(user.getId()!=1 && user.getId()!=2)
		{
			forwardType="display";
		}

		return mapping.findForward(forwardType);
	}
	
	public ActionForward deleteCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		try{
		String reqNo[]=custForm.getRequiredRequestNumber();
		
		for(int j=0;j<reqNo.length;j++)
		{
		   String deleteRecord="update   CUSTOMER_MASTER_M  set Approve_Status='deleted' where REQUEST_NO='"+reqNo[j]+"'";
		   int i=0;
		   i=ad.SqlExecuteUpdate(deleteRecord);
		   if(i>=1)
		   {
			   custForm.setMessage2("Customer Record Successfully Deleted");
				custForm.setMessage("");
		   }else{
			   custForm.setMessage("Error.... When Deleting Customer Record.");
				custForm.setMessage2("");
		   }
		}
		}catch (Exception e) {
			custForm.setMessage("Error.... When Deleting Customer Record.Please Select Atleast One Record");
			custForm.setMessage2("");
			displayCustomerList(mapping, form, request, response);
		}
	   
		return mapping.findForward("customerList");
	}
	public ActionForward displayCustomerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		ArrayList listOFCustomers=new ArrayList();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		try{
			int  totalRecords=0;
			  int  startRecord=0;
			  int  endRecord=0;
		
			  String getTotalRecords="select count(*) from CUSTOMER_MASTER_M where CREATED_BY='"+user.getEmployeeNo()+"' and Approve_Status='In Process'";
			  
			  // String getTotalRecords="select count(*) from material_code_request as m where  m.Approve_Type='Pending' and m.LOCATION_ID='"+user.getPlantId()+"'";
				  
				  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
				  while(rsTotalRecods.next())
				  {
					  totalRecords=rsTotalRecods.getInt(1);
				  }
				  
				  if(totalRecords>=10)
				  {
					  custForm.setTotalRecords(totalRecords);
				  startRecord=1;
				  endRecord=10;
				  custForm.setStartRecord(1);
				  custForm.setEndRecord(10);
				  request.setAttribute("displayRecordNo", "displayRecordNo");
				  request.setAttribute("nextButton", "nextButton");
				  }else
				  {
					  startRecord=1;
					  endRecord=totalRecords;
					  custForm.setTotalRecords(totalRecords);
					  custForm.setStartRecord(1);
					  custForm.setEndRecord(totalRecords); 
				  }
				  String getCustomersList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY c.ID) AS RowNum,c.REQUEST_DATE,c.REQUEST_NO,c.NAME,c.CITY,c.Approve_Status,emp.EMP_FULLNAME," +
		 	"dept.DPTSTXT,loc.LOCATION_CODE  from  CUSTOMER_MASTER_M as c,emp_official_info as emp,Location as loc ,DEPARTMENT as dept   where Approve_Status='In Process' " +
		 	"and CREATED_BY='"+user.getEmployeeNo()+"' and c.CREATED_BY=emp.PERNR and emp.LOCID=loc.LOCATION_CODE and emp.DPTID=dept.DPTID) as  sub Where  sub.RowNum between 1 and 10";
		ResultSet rsCustomerList=ad.selectQuery(getCustomersList);
		while(rsCustomerList.next())
		{
			CustomerMasterForm custForm1=new CustomerMasterForm();
			custForm1.setRequestNumber(rsCustomerList.getInt("REQUEST_NO"));
			String requestDate=rsCustomerList.getString("REQUEST_DATE");
			String req[]=requestDate.split(" "); 
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			custForm1.setRequestDate(requestDate);
			custForm1.setCustomerName(rsCustomerList.getString("NAME"));
			custForm1.setCity(rsCustomerList.getString("CITY"));
			custForm1.setRequestedBy(rsCustomerList.getString("EMP_FULLNAME"));
			custForm1.setDepartment(rsCustomerList.getString("DPTSTXT"));
			custForm1.setLocationId(rsCustomerList.getString("LOCATION_CODE"));
			custForm1.setApproveType(rsCustomerList.getString("Approve_Status"));
			listOFCustomers.add(custForm1);
		}
		
		
		if(listOFCustomers.size()==0){
			request.setAttribute("noRecords", "noRecords");
			custForm.setMessage1("No Records Found");
			custForm.setMessage2("");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("customerList", listOFCustomers);
		custForm.setApproveType("In Process");
		String module=request.getParameter("id"); 
		request.setAttribute("MenuIcon", module);
		request.setAttribute("disablePreviousButton","disablePreviousButton");
	
		return mapping.findForward("customerList");
	}
	
	
	public ActionForward getRegisterVendor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		try{
		String countryID=custForm.getCountryId();
		 if(countryID.equalsIgnoreCase(""))
		 {
			 
		 }else{
		getStates(mapping, form, request, response);
		 }
		 
		 String tdsState=custForm.getIsRegdExciseVender();
		 if(tdsState.equalsIgnoreCase("True"))
		 {
			
			 request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
		 }
		 else{
			 request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
			 
		 }
		 getTDS(mapping, form, request, response);
		
			LinkedList stateID=new LinkedList();
			LinkedList stateName=new LinkedList();
			String getStateDetails="select * from State";
			ResultSet rsState=ad.selectQuery(getStateDetails);
			while(rsState.next())
			{
				stateID.add(rsState.getString("BLAND"));
				stateName.add(rsState.getString("BEZEI"));
			}
			
			
			custForm.setStateId(stateID);
			custForm.setStates(stateName);
		 
		
			
			 
			String getCountryDetails="select * from Country";
			LinkedList countryIDs=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryIDs.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			custForm.setCounID(countryIDs);
			custForm.setCountryName(countryName);
			
			ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
			"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
			ArrayList paymentTermList=new ArrayList();
			ArrayList paymentTermLabelList=new ArrayList();
			
			while(rs7.next()) {
				paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
				paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
			}
			custForm.setPaymentTermList(paymentTermList);
			custForm.setPaymentTermLabelList(paymentTermLabelList);
			
			LinkedList currencyID=new LinkedList();
			LinkedList cureencyNames=new LinkedList();
			String geCurrencyDetails="select * from Currency";
			ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
			while(rsCurrencyDetails.next())
			{
				currencyID.add(rsCurrencyDetails.getString("WAERS"));
				cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
			}
			custForm.setCurrenIds(currencyID);
			custForm.setCurrencys(cureencyNames);	
			
		LinkedList tdsID=new LinkedList();
		LinkedList tdsValue=new LinkedList();
		
		String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
		ResultSet rsTDs=ad.selectQuery(getTDs);
		while(rsTDs.next())
		{
			tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
			tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
		}
		
		custForm.setTdsIds(tdsID);
		custForm.setTdsCodes(tdsValue);
		String typeDetails=custForm.getTypeDetails();
		custForm.setTypeDetails(typeDetails);
		
		ArrayList accountGroupList=new ArrayList();
		ArrayList accountGroupLabelList=new ArrayList();
	 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
ArrayList subLinkIdList = new ArrayList();
ArrayList subLinkValueList = new ArrayList();
while(rs1.next()) {
	accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
	accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
}
custForm.setAccountGroupList(accountGroupList);
custForm.setAccountGroupLabelList(accountGroupLabelList);

try{
ArrayList list = new ArrayList();
HttpSession session=request.getSession();
UserInfo user=(UserInfo)session.getAttribute("user");
ResultSet rs5 = ad.selectQuery("select *  from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+user.getEmployeeNo()+"'");
		while (rs5.next()) {
			CustomerMasterForm custForm1 = new CustomerMasterForm();
			String s=rs5.getString("file_name");
			custForm1.setFileList(rs5.getString("file_name"));
			custForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/"+rs5.getString("file_name")+"");
			list.add(custForm1);
		}
		if(list.size()>0){
		request.setAttribute("listName", list);
		}
}catch (Exception e) {
e.printStackTrace();
}

		}catch (Exception e) {
			e.printStackTrace();
		}
	return mapping.findForward("display");
	}
	public ActionForward getTDS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		 String countryID=custForm.getCountryId();
		 if(countryID.equalsIgnoreCase(""))
		 {
			 
		 }else{
		getStates(mapping, form, request, response);
		 }
		
		 String tdsStatus=custForm.getTdsStatus();
		 
		 if(tdsStatus.equalsIgnoreCase("True"))
		request.setAttribute("setTdsState", "setTdsState");
		 else{
			 
		 }
		 
		 String isRegdVendor=custForm.getIsRegdExciseVender();
		 
			 if(isRegdVendor.equalsIgnoreCase("True"))
			 {
				
				 request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
			 }
			 else{
				 request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
				 
			 }
			 
			 try{
				ArrayList list = new ArrayList();
				HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				ResultSet rs5 = ad.selectQuery("select *  from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+user.getEmployeeNo()+"'");
						while (rs5.next()) {
							CustomerMasterForm custForm1 = new CustomerMasterForm();
							String s=rs5.getString("file_name");
							custForm1.setFileList(rs5.getString("file_name"));
							custForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/"+rs5.getString("file_name")+"");
							list.add(custForm1);
						}
						if(list.size()>0){
						request.setAttribute("listName", list);
						}
			 }catch (Exception e) {
				e.printStackTrace();
			}
			 String typeDetails=custForm.getTypeDetails();
				custForm.setTypeDetails(typeDetails);
		display(mapping, form, request, response);
		displayCMS(mapping, form, request, response);
	return mapping.findForward("display");	
	}
	public ActionForward getStates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		try{
		
		String countryId=custForm.getCountryId();
		LinkedList stateID=new LinkedList();
		LinkedList stateName=new LinkedList();
		String getStateDetails="select * from State where LAND1='"+countryId+"'";
		ResultSet rsState=ad.selectQuery(getStateDetails);
		while(rsState.next())
		{
			stateID.add(rsState.getString("BLAND"));
			stateName.add(rsState.getString("BEZEI"));
		}
		
		
		
		custForm.setStateId(stateID);
		custForm.setStates(stateName);
		
		ArrayList accountGroupList=new ArrayList();
		ArrayList accountGroupLabelList=new ArrayList();
	 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
ArrayList subLinkIdList = new ArrayList();
ArrayList subLinkValueList = new ArrayList();
while(rs1.next()) {
	accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
	accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
}
custForm.setAccountGroupList(accountGroupList);
custForm.setAccountGroupLabelList(accountGroupLabelList);
		
		ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
		"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
		ArrayList paymentTermList=new ArrayList();
		ArrayList paymentTermLabelList=new ArrayList();
		
		while(rs7.next()) {
			paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
			paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
		}
		custForm.setPaymentTermList(paymentTermList);
		custForm.setPaymentTermLabelList(paymentTermLabelList);
		
		
		
		
		 
		String getCountryDetails="select * from Country";
		LinkedList countryID=new LinkedList();
		LinkedList countryName=new LinkedList();
		ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
		while(rsCountryDetails.next()){
			countryID.add(rsCountryDetails.getString("LAND1"));
			countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
		}
		custForm.setCounID(countryID);
		custForm.setCountryName(countryName);
		
			
		
		LinkedList currencyID=new LinkedList();
		LinkedList cureencyNames=new LinkedList();
		String geCurrencyDetails="select * from Currency";
		ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
		while(rsCurrencyDetails.next())
		{
			currencyID.add(rsCurrencyDetails.getString("WAERS"));
			cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
		}
		custForm.setCurrenIds(currencyID);
		custForm.setCurrencys(cureencyNames);	
		
	LinkedList tdsID=new LinkedList();
	LinkedList tdsValue=new LinkedList();
	
	String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
	ResultSet rsTDs=ad.selectQuery(getTDs);
	while(rsTDs.next())
	{
		tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
		tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
	}
	
	custForm.setTdsIds(tdsID);
	custForm.setTdsCodes(tdsValue);
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	ArrayList list = new ArrayList();
	ResultSet rs5 = ad.selectQuery("select *  from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+user.getEmployeeNo()+"'");
			while (rs5.next()) {
				CustomerMasterForm custForm1 = new CustomerMasterForm();
				String s=rs5.getString("file_name");
				custForm1.setFileList(rs5.getString("file_name"));
				custForm1.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/"+rs5.getString("file_name")+"");
				list.add(custForm1);
			}
			if(list.size()>0){
			request.setAttribute("listName", list);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		setMasterData(mapping, form, request, response);
		String typeDetails=custForm.getTypeDetails();
		custForm.setTypeDetails(typeDetails);
		request.setAttribute("diplayStates", "diplayStates");
		//display(mapping, form, request, response);
		//displayCMS(mapping, form, request, response);
	return mapping.findForward("display");	
	}
	public ActionForward saveEmployeeMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		try{
		HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
		String dateNow = ft.format(dNow);
			
			
			  String reqDate=custForm.getRequestDate();
			 
			  String a[]=reqDate.split("/");
			  for(int i=0;i<a.length;i++)
			  {
				  System.out.println("a="+a[i]);
			  }
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			  System.out.println("Now the date is :=>  " + dateNow);
			  String fileList="";
			  String getCountryDetails="select * from Country";
				LinkedList countryID=new LinkedList();
				LinkedList countryName=new LinkedList();
				ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
				while(rsCountryDetails.next()){
					countryID.add(rsCountryDetails.getString("LAND1"));
					countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
				}
				custForm.setCounID(countryID);
				custForm.setCountryName(countryName);
			int fileCount=0;
			String getFileCount="select count(*) from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rsFileCount=ad.selectQuery(getFileCount);
			while(rsFileCount.next())
			{
				fileCount=rsFileCount.getInt(1);
			}
			if(fileCount>0){
			String getUploadedFiles="select * from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+user.getEmployeeNo()+"'";
			ResultSet rs=ad.selectQuery(getUploadedFiles);
			while(rs.next())
			{
				fileList+="jsp/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles/"+rs.getString("file_name")+",";
			}
			
			String viewType="";
			String sales=null;
			String accounts=null;
			
			sales=custForm.getSales();
			accounts=custForm.getAccounts();
			
			if(sales!=null&& accounts!=null){
				
				viewType="3";
			}else{
			
			if(sales!=null)
			{
				
					viewType="S";
				
			}
				if(accounts!=null){
					
						viewType="A";
					
				}
				
				
			}
				System.out.println("View Type="+viewType);
			String customerType="";
			String domestic=null;
			String exports=null;
			domestic=custForm.getDomestic();
			exports=custForm.getExports();
		      if(domestic!=null)
		      {
					
						customerType="Domestic";
					
		      }
				if(exports!=null)
					
				{
					
						customerType="Exports";
					
				}
			String typeDetails=custForm.getTypeDetails();
			
			
			int i=0;
			if(typeDetails.equalsIgnoreCase("Save"))
				
			{
				
				String customerCout="select count(*) from CUSTOMER_MASTER_M where REQUEST_NO='"+custForm.getRequestNumber()+"'";
				int count=0;
				
				ResultSet rsCount=ad.selectQuery(customerCout);
				while(rsCount.next()){
					count=rsCount.getInt(1);
				}
				String approver="";
				/*String lApprover="";
				String approvermail="";
				String pendingApprovers="";
				
				String getApproverID="select * from Approvers_Details where Type like '%Customer Master%'";
				
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
			int old_RequestNo=custForm.getRequestNumber();
			String getReqestNumber="select max(REQUEST_NO)  from CUSTOMER_MASTER_M";
			int maxReqno=0;
			ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
			while(rsReqestNumber.next())
			{
				maxReqno=rsReqestNumber.getInt(1);
			}
			maxReqno+=1;
			custForm.setRequestNumber(maxReqno);	
			
	String saveQuery="INSERT INTO CUSTOMER_MASTER_M(REQUEST_NO,REQUEST_DATE,ACCOUNT_GROUP_ID,VIEW_TYPE,Customer_Type,NAME,ADDRESS1,ADDRESS2," +
			"ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,Customer_Group,Price_Group,Price_List," +
			"Tax_Type,CURRENCY_ID,IS_REGD_EXCISE_Customer,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No," +
			"Excise_Range,Excise_Division,DLNO1,DLNO2,PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,CREATED_DATE,CREATED_BY,Attachments,Approve_Status,cutomer_code,GSTIN_Number) values('"+custForm.getRequestNumber()+"','"+reqDate+"','"+custForm.getAccGroupId()+"','"+viewType+"','"+customerType+"'," +
			"'"+custForm.getCustomerName()+"','"+custForm.getAddress1()+"','"+custForm.getAddress2()+"'," +
			"'"+custForm.getAddress3()+"','"+custForm.getAddress4()+"','"+custForm.getCity()+"','"+custForm.getPincode()+"','"+custForm.getCountryId()+"'," +
			"'"+custForm.getState()+"','"+custForm.getLandlineNo()+"','"+custForm.getMobileNo()+"','"+custForm.getFaxNo()+"','"+custForm.getEmailId()+"'," +
			"'"+custForm.getCustomerGroup()+"','"+custForm.getPriceGroup()+"','"+custForm.getPriceList()+"','"+custForm.getTaxType()+"','"+custForm.getCurrencyId()+"','"+custForm.getTdsStatus()+"'," +
			"'"+custForm.getTdsCode()+"','"+custForm.getListNumber()+"','"+custForm.getTinNumber()+"','"+custForm.getCstNumber()+"','"+custForm.getPanNumber()+"'," +
			"'"+custForm.getServiceTaxNo()+"','"+custForm.getIsRegdExciseVender()+"','"+custForm.getEccNo()+"','"+custForm.getExciseRegNo()+"','"+custForm.getExciseRange()+"'," +
			"'"+custForm.getExciseDivision()+"','"+custForm.getDlno1()+"','"+custForm.getDlno2()+"','"+custForm.getPaymentTermID()+"','"+custForm.getAccountClerkId()+"','"+dateNow+"'," +
			"'"+user.getEmployeeNo()+"','"+fileList+"','Created','"+custForm.getCutomerCode()+"','"+custForm.getGstinNo()+"')";
	System.out.println("saveQuery="+saveQuery);
	display(mapping, form, request, response);
			 i=ad.SqlExecuteUpdatePrmaryKeys(saveQuery);
			 custForm.setMessage2("Customer Code creation request saved with request number='"+custForm.getRequestNumber()+"'");
				custForm.setMessage("");
				
			 int j=0;
				j=ad.SqlExecuteUpdate("update UploadFiles_Masters set request_no='"+custForm.getRequestNumber()+"' where request_no='"+old_RequestNo+"' and  userId='"+user.getEmployeeNo()+"'");
				
				String deleteHistory="delete CUSTOMER_MASTER_HISTORY where REQUEST_NO='"+custForm.getRequestNumber()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
				ad.SqlExecuteUpdate(deleteHistory);
				
				 ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm ");
					String dateNow2 = ft.format(dNow);
					
				String saveInHistory="INSERT INTO CUSTOMER_MASTER_HISTORY(REQUEST_NO,REQUEST_DATE,ACCOUNT_GROUP_ID,VIEW_TYPE,Customer_Type,NAME,ADDRESS1,ADDRESS2," +
				"ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,Customer_Group,Price_Group,Price_List," +
				"Tax_Type,CURRENCY_ID,IS_REGD_EXCISE_Customer,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No," +
				"Excise_Range,Excise_Division,DLNO1,DLNO2,PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,CREATED_DATE,CREATED_BY,Attachments,Approve_Status,cutomer_code,role,GSTIN_Number) values('"+custForm.getRequestNumber()+"','"+dateNow2+"','"+custForm.getAccGroupId()+"','"+viewType+"','"+customerType+"'," +
				"'"+custForm.getCustomerName()+"','"+custForm.getAddress1()+"','"+custForm.getAddress2()+"'," +
				"'"+custForm.getAddress3()+"','"+custForm.getAddress4()+"','"+custForm.getCity()+"','"+custForm.getPincode()+"','"+custForm.getCountryId()+"'," +
				"'"+custForm.getState()+"','"+custForm.getLandlineNo()+"','"+custForm.getMobileNo()+"','"+custForm.getFaxNo()+"','"+custForm.getEmailId()+"'," +
				"'"+custForm.getCustomerGroup()+"','"+custForm.getPriceGroup()+"','"+custForm.getPriceList()+"','"+custForm.getTaxType()+"','"+custForm.getCurrencyId()+"','"+custForm.getTdsStatus()+"'," +
				"'"+custForm.getTdsCode()+"','"+custForm.getListNumber()+"','"+custForm.getTinNumber()+"','"+custForm.getCstNumber()+"','"+custForm.getPanNumber()+"'," +
				"'"+custForm.getServiceTaxNo()+"','"+custForm.getIsRegdExciseVender()+"','"+custForm.getEccNo()+"','"+custForm.getExciseRegNo()+"','"+custForm.getExciseRange()+"'," +
				"'"+custForm.getExciseDivision()+"','"+custForm.getDlno1()+"','"+custForm.getDlno2()+"','"+custForm.getPaymentTermID()+"','"+custForm.getAccountClerkId()+"','"+dateNow+"'," +
				"'"+user.getEmployeeNo()+"','"+fileList+"','Created','"+custForm.getCutomerCode()+"','user','"+custForm.getGstinNo()+"')";
				ad.SqlExecuteUpdate(saveInHistory);
			 
		}else{
			String saveQuery="INSERT INTO CUSTOMER_MASTER_M(REQUEST_NO,REQUEST_DATE,ACCOUNT_GROUP_ID,VIEW_TYPE,Customer_Type,NAME,ADDRESS1,ADDRESS2," +
			"ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,Customer_Group,Price_Group,Price_List," +
			"Tax_Type,CURRENCY_ID,IS_REGD_EXCISE_Customer,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No," +
			"Excise_Range,Excise_Division,DLNO1,DLNO2,PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,CREATED_DATE,CREATED_BY,Attachments,Approve_Status,cutomer_code,GSTIN_Number) values('"+custForm.getRequestNumber()+"','"+reqDate+"','"+custForm.getAccGroupId()+"','"+viewType+"','"+customerType+"'," +
			"'"+custForm.getCustomerName()+"','"+custForm.getAddress1()+"','"+custForm.getAddress2()+"'," +
			"'"+custForm.getAddress3()+"','"+custForm.getAddress4()+"','"+custForm.getCity()+"','"+custForm.getPincode()+"','"+custForm.getCountryId()+"'," +
			"'"+custForm.getState()+"','"+custForm.getLandlineNo()+"','"+custForm.getMobileNo()+"','"+custForm.getFaxNo()+"','"+custForm.getEmailId()+"'," +
			"'"+custForm.getCustomerGroup()+"','"+custForm.getPriceGroup()+"','"+custForm.getPriceList()+"','"+custForm.getTaxType()+"','"+custForm.getCurrencyId()+"','"+custForm.getTdsStatus()+"'," +
			"'"+custForm.getTdsCode()+"','"+custForm.getListNumber()+"','"+custForm.getTinNumber()+"','"+custForm.getCstNumber()+"','"+custForm.getPanNumber()+"'," +
			"'"+custForm.getServiceTaxNo()+"','"+custForm.getIsRegdExciseVender()+"','"+custForm.getEccNo()+"','"+custForm.getExciseRegNo()+"','"+custForm.getExciseRange()+"'," +
			"'"+custForm.getExciseDivision()+"','"+custForm.getDlno1()+"','"+custForm.getDlno2()+"','"+custForm.getPaymentTermID()+"','"+custForm.getAccountClerkId()+"','"+dateNow+"'," +
			"'"+user.getEmployeeNo()+"','"+fileList+"','Created','"+custForm.getCutomerCode()+"','"+custForm.getGstinNo()+"')";
	System.out.println("saveQuery="+saveQuery);
	//display(mapping, form, request, response);
			 i=ad.SqlExecuteUpdatePrmaryKeys(saveQuery);
			 
				 custForm.setMessage2("Customer Code creation request saved with request number='"+custForm.getRequestNumber()+"'");
					custForm.setMessage("");
					 ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm ");
						String dateNow2 = ft.format(dNow);
						String deleteHistory="delete CUSTOMER_MASTER_HISTORY where REQUEST_NO='"+custForm.getRequestNumber()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
						ad.SqlExecuteUpdate(deleteHistory);	
					String saveInHistory="INSERT INTO CUSTOMER_MASTER_HISTORY(REQUEST_NO,REQUEST_DATE,ACCOUNT_GROUP_ID,VIEW_TYPE,Customer_Type,NAME,ADDRESS1,ADDRESS2," +
					"ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,Customer_Group,Price_Group,Price_List," +
					"Tax_Type,CURRENCY_ID,IS_REGD_EXCISE_Customer,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No," +
					"Excise_Range,Excise_Division,DLNO1,DLNO2,PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,CREATED_DATE,CREATED_BY,Attachments,Approve_Status,cutomer_code,role,GSTIN_Number) values('"+custForm.getRequestNumber()+"','"+dateNow2+"','"+custForm.getAccGroupId()+"','"+viewType+"','"+customerType+"'," +
					"'"+custForm.getCustomerName()+"','"+custForm.getAddress1()+"','"+custForm.getAddress2()+"'," +
					"'"+custForm.getAddress3()+"','"+custForm.getAddress4()+"','"+custForm.getCity()+"','"+custForm.getPincode()+"','"+custForm.getCountryId()+"'," +
					"'"+custForm.getState()+"','"+custForm.getLandlineNo()+"','"+custForm.getMobileNo()+"','"+custForm.getFaxNo()+"','"+custForm.getEmailId()+"'," +
					"'"+custForm.getCustomerGroup()+"','"+custForm.getPriceGroup()+"','"+custForm.getPriceList()+"','"+custForm.getTaxType()+"','"+custForm.getCurrencyId()+"','"+custForm.getTdsStatus()+"'," +
					"'"+custForm.getTdsCode()+"','"+custForm.getListNumber()+"','"+custForm.getTinNumber()+"','"+custForm.getCstNumber()+"','"+custForm.getPanNumber()+"'," +
					"'"+custForm.getServiceTaxNo()+"','"+custForm.getIsRegdExciseVender()+"','"+custForm.getEccNo()+"','"+custForm.getExciseRegNo()+"','"+custForm.getExciseRange()+"'," +
					"'"+custForm.getExciseDivision()+"','"+custForm.getDlno1()+"','"+custForm.getDlno2()+"','"+custForm.getPaymentTermID()+"','"+custForm.getAccountClerkId()+"','"+dateNow+"'," +
					"'"+user.getEmployeeNo()+"','"+fileList+"','Created','"+custForm.getCutomerCode()+"','user','"+custForm.getGstinNo()+"')";
					ad.SqlExecuteUpdate(saveInHistory);
		}
			if(i>0)
			{
				String Req_Id = ""+custForm.getRequestNumber();
				//EMailer email = new EMailer();
				//i = email.sendMailToApprover(request, approvermail,Req_Id,"Customer Master");
				String getReqestNumber="select max(REQUEST_NO)  from CUSTOMER_MASTER_M";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				custForm.setRequestNumber(maxReqno);
				custForm.setRequestDate(EMicroUtils.getCurrentSysDate());
				custForm.setTypeDetails("Save");
				custForm.setAccGroupId(""); 
				custForm.setViewType("");  
				custForm.setCustmerType("");
				custForm.setCustomerName("");
				custForm.setAddress1("");
				custForm.setAddress2("");
				custForm.setAddress3("");
				custForm.setAddress4("");
				custForm.setCity("");
				custForm.setPincode("");
				custForm.setCountryId("");
				custForm.setState("");
				custForm.setLandlineNo("");
				custForm.setMobileNo("");
				custForm.setFaxNo("");
				custForm.setEmailId("");
				custForm.setCustomerGroup("");
				custForm.setPriceList("");
				custForm.setTaxType("");
				custForm.setCurrencyId("");
				custForm.setTdsStatus("");
				custForm.setTdsCode("");
				custForm.setListNumber("");
				custForm.setTinNumber("");
				custForm.setCstNumber("");
				custForm.setPanNumber("");
				custForm.setServiceTaxNo("");
				custForm.setIsRegdExciseVender("");
				custForm.setEccNo("");
				custForm.setExciseRegNo("");
				custForm.setExciseRange("");
				custForm.setExciseDivision("");
				custForm.setDlno1("");
				custForm.setDlno2("");
				custForm.setPaymentTermID("");
				custForm.setAccountClerkId("");
				custForm.setPriceGroup("");
				custForm.setSales("");
				custForm.setAccounts("");
				custForm.setDomestic("");
				custForm.setExports("");
				custForm.setCutomerCode("");
			}
					else
					{
						custForm.setMessage("Error...While Saving Customer Master Data.Please Check...");
						custForm.setMessage2("");
					}
			}
			else{
				System.out.println("update Customer Record");
				String recordStatus="";
				String getRecordStatus="select Approve_Status from CUSTOMER_MASTER_M where REQUEST_NO='"+custForm.getRequestNumber()+"' ";
				ResultSet rsRecord=ad.selectQuery(getRecordStatus);
				while(rsRecord.next())
				{
					recordStatus=rsRecord.getString("Approve_Status");
				}
				if(recordStatus.equalsIgnoreCase("Rejected"))
				{
					
					String deleteRecords="delete from All_Request where Req_Id='"+custForm.getRequestNumber()+"' and Req_Type='Customer Master'";
					int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
					System.out.println("deleteStatus="+deleteStatus);
					
					String updateFlag="update CUSTOMER_MASTER_M set rejected_flag='yes' where REQUEST_NO='"+custForm.getRequestNumber()+"'";
					ad.SqlExecuteUpdate(updateFlag);
				}
				String approvedStatus="Pending";
				
			 user=(UserInfo)session.getAttribute("user");
				if(user.getId()==1)
				{
					approvedStatus=custForm.getApproveType();
				}
				String updateCustomerData="update CUSTOMER_MASTER_M set REQUEST_DATE='"+reqDate+"',ACCOUNT_GROUP_ID='"+custForm.getAccGroupId()+"',VIEW_TYPE='"+viewType+"',Customer_Type='"+customerType+"',NAME='"+custForm.getCustomerName()+"',ADDRESS1='"+custForm.getAddress1()+"',ADDRESS2='"+custForm.getAddress2()+"'," +
				"ADDRESS3='"+custForm.getAddress3()+"',ADDRESS4='"+custForm.getAddress4()+"',CITY='"+custForm.getCity()+"',PINCODE='"+custForm.getPincode()+"',COUNTRY_ID='"+custForm.getCountryId()+"',STATE='"+custForm.getState()+"',LANDLINE_NO='"+custForm.getLandlineNo()+"',MOBILE_NO='"+custForm.getMobileNo()+"',FAX_NO='"+custForm.getFaxNo()+"',EMAIL_ID='"+custForm.getEmailId()+"',Customer_Group='"+custForm.getCustomerGroup()+"',Price_Group='"+custForm.getPriceGroup()+"',Price_List='"+custForm.getPriceList()+"'," +
				"Tax_Type='"+custForm.getTaxType()+"',CURRENCY_ID='"+custForm.getCurrencyId()+"',IS_REGD_EXCISE_Customer='"+custForm.getTdsStatus()+"',TDS_CODE='"+custForm.getTdsCode()+"',LST_NO='"+custForm.getListNumber()+"',TIN_NO='"+custForm.getTinNumber()+"',CST_NO='"+custForm.getCstNumber()+"',PAN_No='"+custForm.getPanNumber()+"',Service_Tax_Registration_No='"+custForm.getServiceTaxNo()+"',IS_REGD_EXCISE_VENDOR='"+custForm.getIsRegdExciseVender()+"',ECC_No='"+custForm.getEccNo()+"',Excise_Reg_No='"+custForm.getExciseRegNo()+"'," +
				"Excise_Range='"+custForm.getExciseRange()+"',Excise_Division='"+custForm.getExciseDivision()+"',DLNO1='"+custForm.getDlno1()+"',DLNO2='"+custForm.getDlno2()+"',PAYMENT_TERM_ID='"+custForm.getPaymentTermID()+"',ACCOUNT_CLERK_ID='"+custForm.getAccountClerkId()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',Attachments='"+fileList+"',Approve_Status='Created',cutomer_code='"+custForm.getCutomerCode()+"',GSTIN_Number='"+custForm.getGstinNo()+"' where  REQUEST_NO='"+custForm.getRequestNumber()+"'";
			i=ad.SqlExecuteUpdate(updateCustomerData);
			if(i>0)
			{
				custForm.setMessage2("Customer Code creation request updated with request number='"+custForm.getRequestNumber()+"'");
				custForm.setMessage("");
				
				 ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm ");
					String dateNow2 = ft.format(dNow);
				
				String deleteHistory="delete CUSTOMER_MASTER_HISTORY where REQUEST_NO='"+custForm.getRequestNumber()+"' and CREATED_BY='"+user.getEmployeeNo()+"'";
				ad.SqlExecuteUpdate(deleteHistory);	
				String saveInHistory="INSERT INTO CUSTOMER_MASTER_HISTORY(REQUEST_NO,REQUEST_DATE,ACCOUNT_GROUP_ID,VIEW_TYPE,Customer_Type,NAME,ADDRESS1,ADDRESS2," +
				"ADDRESS3,ADDRESS4,CITY,PINCODE,COUNTRY_ID,STATE,LANDLINE_NO,MOBILE_NO,FAX_NO,EMAIL_ID,Customer_Group,Price_Group,Price_List," +
				"Tax_Type,CURRENCY_ID,IS_REGD_EXCISE_Customer,TDS_CODE,LST_NO,TIN_NO,CST_NO,PAN_No,Service_Tax_Registration_No,IS_REGD_EXCISE_VENDOR,ECC_No,Excise_Reg_No," +
				"Excise_Range,Excise_Division,DLNO1,DLNO2,PAYMENT_TERM_ID,ACCOUNT_CLERK_ID,CREATED_DATE,CREATED_BY,Attachments,Approve_Status,cutomer_code,role,GSTIN_Number) values('"+custForm.getRequestNumber()+"','"+dateNow2+"','"+custForm.getAccGroupId()+"','"+viewType+"','"+customerType+"'," +
				"'"+custForm.getCustomerName()+"','"+custForm.getAddress1()+"','"+custForm.getAddress2()+"'," +
				"'"+custForm.getAddress3()+"','"+custForm.getAddress4()+"','"+custForm.getCity()+"','"+custForm.getPincode()+"','"+custForm.getCountryId()+"'," +
				"'"+custForm.getState()+"','"+custForm.getLandlineNo()+"','"+custForm.getMobileNo()+"','"+custForm.getFaxNo()+"','"+custForm.getEmailId()+"'," +
				"'"+custForm.getCustomerGroup()+"','"+custForm.getPriceGroup()+"','"+custForm.getPriceList()+"','"+custForm.getTaxType()+"','"+custForm.getCurrencyId()+"','"+custForm.getTdsStatus()+"'," +
				"'"+custForm.getTdsCode()+"','"+custForm.getListNumber()+"','"+custForm.getTinNumber()+"','"+custForm.getCstNumber()+"','"+custForm.getPanNumber()+"'," +
				"'"+custForm.getServiceTaxNo()+"','"+custForm.getIsRegdExciseVender()+"','"+custForm.getEccNo()+"','"+custForm.getExciseRegNo()+"','"+custForm.getExciseRange()+"'," +
				"'"+custForm.getExciseDivision()+"','"+custForm.getDlno1()+"','"+custForm.getDlno2()+"','"+custForm.getPaymentTermID()+"','"+custForm.getAccountClerkId()+"','"+dateNow+"'," +
				"'"+user.getEmployeeNo()+"','"+fileList+"','Created','"+custForm.getCutomerCode()+"','user','"+custForm.getGstinNo()+"')";
				ad.SqlExecuteUpdate(saveInHistory);
				
				String Req_Id = ""+custForm.getRequestNumber();
				//EMailer email = new EMailer();
				//i = email.sendMailToApprover(request, approvermail,Req_Id,"Customer Master");
				String getReqestNumber="select max(REQUEST_NO)  from CUSTOMER_MASTER_M";
				int maxReqno=0;
				ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
				while(rsReqestNumber.next())
				{
					maxReqno=rsReqestNumber.getInt(1);
				}
				maxReqno+=1;
				custForm.setRequestNumber(maxReqno);
				custForm.setRequestDate(EMicroUtils.getCurrentSysDate());
				custForm.setTypeDetails("Save");
				custForm.setAccGroupId(""); 
				custForm.setViewType("");  
				custForm.setCustmerType("");
				custForm.setCustomerName("");
				custForm.setAddress1("");
				custForm.setAddress2("");
				custForm.setAddress3("");
				custForm.setAddress4("");
				custForm.setCity("");
				custForm.setPincode("");
				custForm.setCountryId("");
				custForm.setState("");
				custForm.setLandlineNo("");
				custForm.setMobileNo("");
				custForm.setFaxNo("");
				custForm.setEmailId("");
				custForm.setCustomerGroup("");
				custForm.setPriceList("");
				custForm.setTaxType("");
				custForm.setCurrencyId("");
				custForm.setTdsStatus("");
				custForm.setTdsCode("");
				custForm.setListNumber("");
				custForm.setTinNumber("");
				custForm.setCstNumber("");
				custForm.setPanNumber("");
				custForm.setServiceTaxNo("");
				custForm.setIsRegdExciseVender("");
				custForm.setEccNo("");
				custForm.setExciseRegNo("");
				custForm.setExciseRange("");
				custForm.setExciseDivision("");
				custForm.setDlno1("");
				custForm.setDlno2("");
				custForm.setPaymentTermID("");
				custForm.setAccountClerkId("");
				custForm.setPriceGroup("");
				custForm.setSales("");
				custForm.setAccounts("");
				custForm.setDomestic("");
				custForm.setExports("");
				custForm.setCutomerCode("");
			
			}
			else{
				
				custForm.setMessage("Error When Updating Customer Master");
				custForm.setMessage2("");
			}
			}
			}else{
				custForm.setMessage("Error..Please Upload File");
				custForm.setMessage2("");
			}
				
				
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	custForm.setAccountGroupList(accountGroupList);
	custForm.setAccountGroupLabelList(accountGroupLabelList);	
			
				LinkedList stateID=new LinkedList();
				LinkedList stateName=new LinkedList();
				String getStateDetails="select * from State where LAND1='"+custForm.getCountryId()+"'";
				ResultSet rsState=ad.selectQuery(getStateDetails);
				
				while(rsState.next())
				{
					stateID.add(rsState.getString("BLAND"));
					stateName.add(rsState.getString("BEZEI"));
				}
				custForm.setStateId(stateID);
				custForm.setStates(stateName);
				request.setAttribute("diplayStates", "diplayStates");
				
				ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
				"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
				ArrayList paymentTermList=new ArrayList();
				ArrayList paymentTermLabelList=new ArrayList();
				
				while(rs7.next()) {
					paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
					paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
				}
				custForm.setPaymentTermList(paymentTermList);
				custForm.setPaymentTermLabelList(paymentTermLabelList);
				
				LinkedList currencyID=new LinkedList();
				LinkedList cureencyNames=new LinkedList();
				String geCurrencyDetails="select * from Currency";
				ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
				while(rsCurrencyDetails.next())
				{
					currencyID.add(rsCurrencyDetails.getString("WAERS"));
					cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
				}
				custForm.setCurrenIds(currencyID);
				custForm.setCurrencys(cureencyNames);	
				
			LinkedList tdsID=new LinkedList();
			LinkedList tdsValue=new LinkedList();

			String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
			ResultSet rsTDs=ad.selectQuery(getTDs);
			while(rsTDs.next())
			{
				tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
				tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
			}

			custForm.setTdsIds(tdsID);
			custForm.setTdsCodes(tdsValue);

			String countryId=custForm.getCountryId();
			

			String tdsState=custForm.getIsRegdExciseVender();

				if(tdsState.equalsIgnoreCase("True"))
				{
					
					 request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
				}
				else{
					 request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
					 
				}
			
			setMasterData(mapping, form, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//displayCustomerList(mapping, form, request, response);
			return mapping.findForward("display");
	}
	public ActionForward deleteFileListModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
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
		 	 String filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles";
		 
		for(int i=0;i<v.length;i++)
		{
		String deleteQuery="delete from UploadFiles_Masters where file_name='"+v[i]+"' and request_no='"+custForm.getRequestNumber()+"'  and userId='"+userId+"'";
		int j=ad.SqlExecuteUpdate(deleteQuery);
			if(j>0)
			{
				File fileToCreate = new File(filePath, v[i]);
			 	boolean test=fileToCreate.delete();
			 	
			 	//delete file in another path
			 	
			 	File fileToCreate1 = new File("E:/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles", v[i]);
			 	boolean test1=fileToCreate1.delete();
			}
		}
		
		ArrayList list = new ArrayList();
		ResultSet rs5 = ad.selectQuery("select *  from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"'  and userId='"+userId+"'");
				while (rs5.next()) {
					CustomerMasterForm custForm1 = new CustomerMasterForm();
					String s=rs5.getString("file_name");
					custForm1.setFileList(rs5.getString("file_name"));
					list.add(custForm1);
				}
				request.setAttribute("listName", list);
		
				int reqNO=custForm.getRequestNumber();
				custForm.setRequestNumber(reqNO);
		
				
				 
				String getCountryDetails="select * from Country";
				LinkedList countryID=new LinkedList();
				LinkedList countryName=new LinkedList();
				ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
				while(rsCountryDetails.next()){
					countryID.add(rsCountryDetails.getString("LAND1"));
					countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
				}
				custForm.setCounID(countryID);
				custForm.setCountryName(countryName);
				
				ArrayList accountGroupList=new ArrayList();
				ArrayList accountGroupLabelList=new ArrayList();
			 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		while(rs1.next()) {
			accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
			accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
		}
		custForm.setAccountGroupList(accountGroupList);
		custForm.setAccountGroupLabelList(accountGroupLabelList);
				
				LinkedList currencyID=new LinkedList();
				LinkedList cureencyNames=new LinkedList();
				String geCurrencyDetails="select * from Currency";
				ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
				while(rsCurrencyDetails.next())
				{
					currencyID.add(rsCurrencyDetails.getString("WAERS"));
					cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
				}
				custForm.setCurrenIds(currencyID);
				custForm.setCurrencys(cureencyNames);	
				
			LinkedList tdsID=new LinkedList();
			LinkedList tdsValue=new LinkedList();
			
			ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
					"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
					ArrayList paymentTermList=new ArrayList();
					ArrayList paymentTermLabelList=new ArrayList();
					
					while(rs7.next()) {
						paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
						paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
					}
					custForm.setPaymentTermList(paymentTermList);
					custForm.setPaymentTermLabelList(paymentTermLabelList);

			String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
			ResultSet rsTDs=ad.selectQuery(getTDs);
			while(rsTDs.next())
			{
				tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
				tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
			}

			custForm.setTdsIds(tdsID);
			custForm.setTdsCodes(tdsValue);

			String countryId=custForm.getCountryId();
			LinkedList stateID=new LinkedList();
			LinkedList stateName=new LinkedList();
			String getStateDetails="select * from State where LAND1='"+countryId+"'";
			ResultSet rsState=ad.selectQuery(getStateDetails);
			while(rsState.next())
			{
				stateID.add(rsState.getString("BLAND"));
				stateName.add(rsState.getString("BEZEI"));
			}


			custForm.setStateId(stateID);
			custForm.setStates(stateName);
			
			String tdsState=custForm.getIsRegdExciseVender();
			
				if(tdsState.equalsIgnoreCase("True"))
				{
					
					 request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
				}
				else{
					 request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
					 
				}

				setMasterData(mapping, form, request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		displayCMS(mapping, form, request, response);
		request.setAttribute("diplayStates", "diplayStates");
		
			return mapping.findForward("display");
	}
	public ActionForward uploadFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
			try{
				FormFile myFile = custForm.getFileNames();
			    String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				int fileNo=0;
				EssDao adlinks=new EssDao();	
				 String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
					int filesize=myFile.getFileSize();
					String filePath = "";
					HttpSession session=request.getSession();
					UserInfo user=(UserInfo)session.getAttribute("user");
					String userId=user.getEmployeeNo();
					
				if((ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls")) && (filesize<1048576))
				{
				
					String sql9="select count(*) from UploadFiles_Masters where  file_name='"+fileName+"'";
				ResultSet rs15 = adlinks.selectQuery(sql9);
				int fileCount=0;
				
				while (rs15.next())
				{
					fileCount=Integer.parseInt(rs15.getString(1));
				}
				if(fileCount>0)
				{
					custForm.setMessage("File aleardy uploaded.Please change file name");
					custForm.setMessage2("");
				}
				else
				{
					byte[] fileData = myFile.getFileData();
					
					
					
					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
					 Properties props = new Properties();
					 	props.load(in);
						in.close();
					 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
					 	 filePath=uploadFilePath+"/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles";
					
					File destinationDir = new File(filePath);
					if(!destinationDir.exists())
					{
						destinationDir.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate = new File(filePath, fileName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
							fileOutStream.write(myFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					request.setAttribute("fileName", fileName);
					
					//upload files in another path
					
					try{
						String filePath1 = "E:/EMicro Files/ESS/sapMasterRequest/Customer master files/UploadFiles";
						
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
				String insertsql = "insert into UploadFiles_Masters(file_path,file_name,request_no,userId) values('"+filePath+"','"+fileName+"','"+custForm.getRequestNumber()+"','"+userId+"')";
				int a = adlinks.SqlExecuteUpdate(insertsql);
				if (a > 0)
				{
					custForm.setMessage2("Documents Uploaded Successfully");
					custForm.setMessage("");
				}else {
					custForm.setMessage("Error While Uploading Files ... Please check Entered Values");
					custForm.setMessage2("");
				}
				}
				
				
				}else{
					custForm.setMessage2("");
					custForm.setMessage("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");
					request.setAttribute("customerfile", "customerfile")	;	
				}
				
				ArrayList list = new ArrayList();
				ResultSet rs5 = adlinks.selectQuery("select *  from UploadFiles_Masters where request_no='"+custForm.getRequestNumber()+"' and userId='"+userId+"'");
						while (rs5.next()) {
							CustomerMasterForm custForm1 = new CustomerMasterForm();
							String s=rs5.getString("file_name");
							custForm1.setFileList(rs5.getString("file_name"));
							custForm1.setFilepath(filePath+"/"+rs5.getString("file_name")+"");
							list.add(custForm1);
						}
						request.setAttribute("listName", list);

						
				
				
			// set values
			int reqNO=custForm.getRequestNumber();
			custForm.setRequestNumber(reqNO);

			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
		ArrayList subLinkIdList = new ArrayList();
		ArrayList subLinkValueList = new ArrayList();
		while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
		}
		custForm.setAccountGroupList(accountGroupList);
		custForm.setAccountGroupLabelList(accountGroupLabelList);
			 
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			custForm.setCounID(countryID);
			custForm.setCountryName(countryName);
			
			ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
			"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
			ArrayList paymentTermList=new ArrayList();
			ArrayList paymentTermLabelList=new ArrayList();
			
			while(rs7.next()) {
				paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
				paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
			}
			custForm.setPaymentTermList(paymentTermList);
			custForm.setPaymentTermLabelList(paymentTermLabelList);
			
			LinkedList currencyID=new LinkedList();
			LinkedList cureencyNames=new LinkedList();
			String geCurrencyDetails="select * from Currency";
			ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
			while(rsCurrencyDetails.next())
			{
				currencyID.add(rsCurrencyDetails.getString("WAERS"));
				cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
			}
			custForm.setCurrenIds(currencyID);
			custForm.setCurrencys(cureencyNames);	
			
		LinkedList tdsID=new LinkedList();
		LinkedList tdsValue=new LinkedList();

		String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
		ResultSet rsTDs=ad.selectQuery(getTDs);
		while(rsTDs.next())
		{
			tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
			tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
		}

		custForm.setTdsIds(tdsID);
		custForm.setTdsCodes(tdsValue);

		String countryId=custForm.getCountryId();
		LinkedList stateID=new LinkedList();
		LinkedList stateName=new LinkedList();
		String getStateDetails="select * from State where LAND1='"+countryId+"'";
		ResultSet rsState=ad.selectQuery(getStateDetails);
		while(rsState.next())
		{
			stateID.add(rsState.getString("BLAND"));
			stateName.add(rsState.getString("BEZEI"));
		}


		custForm.setStateId(stateID);
		custForm.setStates(stateName);

		String tdsState=custForm.getIsRegdExciseVender();

			if(tdsState.equalsIgnoreCase("True"))
			{
				
				 request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
			}
			else{
				 request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
				 
			}


		}catch (Exception e) {
			e.printStackTrace();
		}
		setMasterData(mapping, form, request, response);
			//display(mapping, form, request, response);
			displayCMS(mapping, form, request, response);
			//getStates(mapping, form, request, response);
			request.setAttribute("diplayStates", "diplayStates");
				return mapping.findForward("display");
				}
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			CustomerMasterForm custForm=(CustomerMasterForm)form;
			EssDao ad=EssDao.dBConnection();
			String id=request.getParameter("id"); 		
			HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		String sql="select * from links where id in("+user.getIncludeSubLinks()+")";
		
		System.out.println("SQL is *********************************"+sql);
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			while(rs.next()){
				 
				 String sql1="select * from links where module='"+id+"' " +
				 		"and id ='"+rs.getString("id")+"'";
				 	
				 ResultSet rs1=ad.selectQuery(sql1);
				 if(rs1.next()){
				 hm.put(rs1.getString("link_path")+"?method="+rs1.getString("method")+"&sId="+rs1.getString("link_name")+"&id="+rs1.getString("module"), (rs1.getString("link_name")+','+rs1.getString("icon_name")));
				 }
			}
			session.setAttribute("SUBLINKS", hm);
			
		
			
			 
			String getCountryDetails="select * from Country";
			LinkedList countryID=new LinkedList();
			LinkedList countryName=new LinkedList();
			ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
			while(rsCountryDetails.next()){
				countryID.add(rsCountryDetails.getString("LAND1"));
				countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
			}
			custForm.setCounID(countryID);
			custForm.setCountryName(countryName);
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	custForm.setAccountGroupList(accountGroupList);
	custForm.setAccountGroupLabelList(accountGroupLabelList);
			
				
			ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_ID," +
			"PAYMENT_TERM_NAME from PAYMENT_TERM_M where ACTIVE='True' order by PAYMENT_TERM_NAME");
			ArrayList paymentTermList=new ArrayList();
			ArrayList paymentTermLabelList=new ArrayList();
			
			while(rs7.next()) {
				paymentTermList.add(rs7.getString("PAYMENT_TERM_ID"));
				paymentTermLabelList.add(rs7.getString("PAYMENT_TERM_ID")+"-"+rs7.getString("PAYMENT_TERM_NAME"));
			}
			custForm.setPaymentTermList(paymentTermList);
			custForm.setPaymentTermLabelList(paymentTermLabelList);
			
			LinkedList currencyID=new LinkedList();
			LinkedList cureencyNames=new LinkedList();
			String geCurrencyDetails="select * from Currency";
			ResultSet rsCurrencyDetails=ad.selectQuery(geCurrencyDetails);
			while(rsCurrencyDetails.next())
			{
				currencyID.add(rsCurrencyDetails.getString("WAERS"));
				cureencyNames.add(rsCurrencyDetails.getString("WAERS")+" - "+rsCurrencyDetails.getString("ISOCD"));
			}
			custForm.setCurrenIds(currencyID);
			custForm.setCurrencys(cureencyNames);	
			
		LinkedList tdsID=new LinkedList();
		LinkedList tdsValue=new LinkedList();
		
		String getTDs="select * from TDS_SECTION_M where ACTIVE='True'";
		ResultSet rsTDs=ad.selectQuery(getTDs);
		while(rsTDs.next())
		{
			tdsID.add(rsTDs.getString("TDS_SECTION_ID"));
			tdsValue.add(rsTDs.getString("TDS_SECTION_DESC"));
		}
		
		custForm.setTdsIds(tdsID);
		custForm.setTdsCodes(tdsValue);
		
		//set master table values
		
		setMasterData(mapping, form, request, response);
		
		String getReqestNumber="select max(REQUEST_NO)  from CUSTOMER_MASTER_M";
		int maxReqno=0;
		ResultSet rsReqestNumber=ad.selectQuery(getReqestNumber);
		while(rsReqestNumber.next())
		{
			maxReqno=rsReqestNumber.getInt(1);
		}
		maxReqno+=1;
		custForm.setRequestNumber(maxReqno);
		
		
		}catch(Exception se){
			se.printStackTrace();
		}
		
		custForm.setRequestDate(EMicroUtils.getCurrentSysDate());
		displayCMS(mapping, form, request, response);
		
		
		String isRegdVendor=custForm.getIsRegdExciseVender();
		if(isRegdVendor!=null)
		{
		 if(isRegdVendor.equalsIgnoreCase("True"))
		 {
			
			 request.setAttribute("setRegdExciseVender", "setRegdExciseVender");
		 }
		 else{
			 request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
			 
		 }
		}else{
			 request.setAttribute("setRegdExciseVenderNotMandatory", "setRegdExciseVenderNotMandatory");
		}
		custForm.setTypeDetails("Save");
		return mapping.findForward("display");
	}
	
	
	public ActionForward displayCMS(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterForm custForm=(CustomerMasterForm)form;
		EssDao ad=EssDao.dBConnection();
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
				 String sql1="select * from links where module='ESS' ";
				 ResultSet rs1=ad.selectQuery(sql1);
				 while(rs1.next()){
				 hm.put(rs1.getString("link_path")+"?method="+rs1.getString("method")+"&sId="+rs1.getString("link_name")+"&id="+rs1.getString("module"), (rs1.getString("link_name")+','+rs1.getString("icon_name")));
				 }
			session.setAttribute("SUBLINKS", hm);
			 
		}catch(SQLException se){
			se.printStackTrace();
		}
		ArrayList list=new ArrayList();
		return mapping.findForward("display");
	}
	
	public CustomerMasterForm setFormElements(ResultSet cmRS){
		CustomerMasterForm cmForm= new CustomerMasterForm();
		EssDao ad=EssDao.dBConnection();
		try{
			cmForm.setAccGroupId(cmRS.getString("ACCOUNT_GROUP_ID"));
			String viewType=cmRS.getString("VIEW_TYPE");
			if(viewType.equalsIgnoreCase("S"))
			{
				cmForm.setViewType("Sales");
			}
			else
			{
				cmForm.setViewType("Accounts");
			}
			cmForm.setCustomerName(cmRS.getString("NAME"));
			cmForm.setAddress1(cmRS.getString("ADDRESS1"));
			cmForm.setAddress2(cmRS.getString("ADDRESS2"));
			cmForm.setAddress3(cmRS.getString("ADDRESS3"));
			cmForm.setAddress4(cmRS.getString("ADDRESS4"));
			cmForm.setCity(cmRS.getString("CITY"));
			cmForm.setPincode(cmRS.getString("PINCODE"));
			String country = cmRS.getString("COUNTRY_ID");
			ResultSet rs9 = ad.selectQuery("select LANDX from Country where LAND1='"+country+"'");
			while(rs9.next()) {
				country = rs9.getString("LANDX");
			}
			cmForm.setCountryId(country);
			String state = cmRS.getString("STATE");
			ResultSet rs4 = ad.selectQuery("select BEZEI from State where BLAND='"+state+"'");
			
			while(rs4.next()) {
				state  = rs4.getString("BEZEI");
			}
			cmForm.setState(state);
			cmForm.setLandlineNo(cmRS.getString("LANDLINE_NO"));
			cmForm.setMobileNo(cmRS.getString("MOBILE_NO"));
			cmForm.setFaxNo(cmRS.getString("FAX_NO"));
			cmForm.setEmailId(cmRS.getString("EMAIL_ID"));
			String currency = cmRS.getString("CURRENCY_ID");
			ResultSet rs5 = ad.selectQuery("select ISOCD from Currency where WAERS='"+currency+"'");
			while(rs5.next()) {
				currency = rs5.getString("ISOCD");
			}
			cmForm.setCurrencyId(currency);
			//Sales...........
			cmForm.setCustomerGroup(cmRS.getString("Customer_Group"));
			cmForm.setPriceGroup(cmRS.getString("Price_Group"));
			cmForm.setPriceList(cmRS.getString("Price_List"));
			cmForm.setTaxType(cmRS.getString("Tax_Type"));
			//EXCISE/TAX
			String isRegistVendor=cmRS.getString("IS_REGD_EXCISE_VENDOR");
		 	if(isRegistVendor.equalsIgnoreCase("1"))
			{
		 		cmForm.setIsRegdExciseVender("True");
			}
			if(isRegistVendor.equalsIgnoreCase("0"))
			{
				cmForm.setIsRegdExciseVender("False");
			}
			cmForm.setTdsCode(cmRS.getString("TDS_CODE"));
			cmForm.setListNumber(cmRS.getString("LST_NO"));
			cmForm.setTinNumber(cmRS.getString("TIN_NO"));
			cmForm.setCstNumber(cmRS.getString("CST_NO"));
			cmForm.setPanNumber(cmRS.getString("PAN_No"));
			cmForm.setServiceTaxNo(cmRS.getString("Service_Tax_Registration_No"));
			String tdsStatus=cmRS.getString("IS_REGD_EXCISE_Customer");
			if(tdsStatus.equalsIgnoreCase("1"))
			{
				cmForm.setTdsStatus("True");
			}
			else if(tdsStatus.equalsIgnoreCase("0")){
				cmForm.setTdsStatus("False");
			}
			cmForm.setEccNo(cmRS.getString("ECC_No"));
			cmForm.setExciseRegNo(cmRS.getString("Excise_Reg_No"));
			cmForm.setExciseRange(cmRS.getString("Excise_Range"));
			cmForm.setExciseDivision(cmRS.getString("Excise_Division"));
			String paytrm = cmRS.getString("PAYMENT_TERM_ID");
			ResultSet rs7 = ad.selectQuery("select PAYMENT_TERM_NAME from PAYMENT_TERM_M where PAYMENT_TERM_ID='"+paytrm+"'");
			
			while(rs7.next()) {
				paytrm = rs7.getString("PAYMENT_TERM_NAME");
			}
			String accclr = cmRS.getString("ACCOUNT_CLERK_ID");
			ResultSet rs8 = ad.selectQuery("select ACC_CLERK_DESC from ACC_CLERK_M where ACC_CLERK_ID='"+accclr+"'");
			while(rs8.next()) {
				accclr = rs8.getString("ACC_CLERK_DESC");
			}
			cmForm.setPaymentTermID(paytrm);
			cmForm.setAccountClerkId(accclr);
			cmForm.setDlno1(cmRS.getString("DLNO1"));
			cmForm.setDlno2(cmRS.getString("DLNO2"));
			String customertype=cmRS.getString("Customer_Type");
			cmForm.setCustmerType(customertype);
			
			ArrayList accountGroupList=new ArrayList();
			ArrayList accountGroupLabelList=new ArrayList();
		 	ResultSet rs1 = ad.selectQuery("select ACCOUNT_GROUP_ID,ACCOUNT_GROUP_NAME from ACCOUNT_GROUP_M where active='True'");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	while(rs1.next()) {
		accountGroupList.add(rs1.getString("ACCOUNT_GROUP_ID"));
		accountGroupLabelList.add(rs1.getString("ACCOUNT_GROUP_NAME"));
	}
	cmForm.setAccountGroupList(accountGroupList);
	cmForm.setAccountGroupLabelList(accountGroupLabelList);
		}
		catch (SQLException sqle) { System.out.println("SQLException @ set Vendor Form Values"); sqle.printStackTrace();}
		return cmForm;
	}
	
}
