package com.microlabs.ess.action;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.MaterialCodeRequestForm;
import com.microlabs.ess.form.PackageMaterialMasterForm;
import com.microlabs.ess.form.RawMaterialForm;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class MaterialCodeRequestAction extends DispatchAction{
	
	public ActionForward lastSearcRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		masterData(mapping, form, request, response);
		LinkedList materialList=new LinkedList();
		try{
			int totalRecords=masterForm.getTotalSearchRecords();
			int startRecord=masterForm.getStartSearchRecord();
			int endRecord=masterForm.getEndSearchRecord();
			startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 masterForm.setTotalSearchRecords(totalRecords);
			 masterForm.setStartSearchRecord(startRecord);
			 masterForm.setEndSearchRecord(totalRecords);
			
			
			
		
				String location =masterForm.getLocationId();
				String materialType=masterForm.getMaterialType();
				String materialGroup=masterForm.getMaterialGrup();
				String shortName=masterForm.getShortName();
				String longName=masterForm.getLongName();
				String getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.LOCATION_ID) AS RowNum, loc.LOCATION_CODE,m.MATERIAL_GROUP,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.SAP_CODE_NO,m.UNIT_OF_MEAS_ID,m.CREATED_DATE,m.SAP_CREATION_DATE,m.APPROXIMATE_PRICE  from Material_Code_Search as m , " +
				"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and mType.id=m.MATERIAL_TYPE   ";
		
		if(!(materialType.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_TYPE='"+materialType+"'";
		}
		if(!(materialGroup.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_GROUP='"+materialGroup+"'";
		}
		if(!(shortName.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and  m.MATERIAL_SHORT_NAME like '%"+shortName+"%'";
		}
		if(!(longName.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_LONG_NAME like '%"+longName+"%' ";
		}
		getMaterialRecords=getMaterialRecords+") as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+"";
		if(!(materialType.equalsIgnoreCase("")) && !(materialGroup.equalsIgnoreCase("")) && !(shortName.equalsIgnoreCase("")) && !(longName.equalsIgnoreCase("")))
		{
			getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.LOCATION_ID) AS RowNum, loc.LOCATION_CODE,m.MATERIAL_GROUP,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.SAP_CODE_NO,m.UNIT_OF_MEAS_ID,m.CREATED_DATE,m.SAP_CREATION_DATE,m.APPROXIMATE_PRICE from  Material_Code_Search as m," +
			"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and  mType.id=m.MATERIAL_TYPE   and " +
			"m.MATERIAL_TYPE = '"+materialType+"' and (m.MATERIAL_GROUP = '"+materialGroup+"' and m.MATERIAL_SHORT_NAME like '%"+shortName+"' and m.MATERIAL_LONG_NAME like '%"+longName+"%')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+"";
		}

		ResultSet rs=ad.selectQuery(getMaterialRecords);
		while(rs.next())
		{
			MaterialCodeRequestForm apprForm=new MaterialCodeRequestForm();
			apprForm.setLocationId(rs.getString("LOCATION_CODE"));
			 String codeNo=rs.getString("SAP_CODE_NO");
			    if(codeNo!=null)
			    {
			    	apprForm.setCodeNo(codeNo);
			    }else{
			    	apprForm.setCodeNo("");
			    }
			apprForm.setShortName(rs.getString("MATERIAL_SHORT_NAME"));
			apprForm.setLongName(rs.getString("MATERIAL_LONG_NAME"));
			apprForm.setUom(rs.getString("UNIT_OF_MEAS_ID"));
			apprForm.setMaterialGrup(rs.getString("MATERIAL_GROUP"));
			apprForm.setApproximatePrice(rs.getString("APPROXIMATE_PRICE"));
			apprForm.setCreatedOn("");
			 apprForm.setRequestedOn("");
			 String cretedOn=rs.getString("SAP_CREATION_DATE");
			   if(cretedOn!=null)
			   {
				   if(cretedOn!="")
					   apprForm.setCreatedOn(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
			   }
			   String requestedOn=rs.getString("CREATED_DATE");
			   if(requestedOn!=null)
			   {
				   if(requestedOn!="")
					   apprForm.setRequestedOn(EMicroUtils.display1(rs.getDate("CREATED_DATE")));
			   }
		    materialList.add(apprForm);
		}
		request.setAttribute("materialList", materialList);
			
		request.setAttribute("disableNextButton", "disableNextButton");
		request.setAttribute("previousButton", "previousButton");
		if(materialList.size()<10)
		{
			
			request.setAttribute("previousButton", "");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		}
		request.setAttribute("displayRecordNo", "displayRecordNo");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("materialSearchList");
	}
	
	public ActionForward previousSearcRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		masterData(mapping, form, request, response);
		LinkedList materialList=new LinkedList();
		try{
			int totalRecords=masterForm.getTotalSearchRecords();
			int endRecord=masterForm.getStartSearchRecord()-1;//20
			int startRecord=masterForm.getStartSearchRecord()-10;//11
		
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}
			
			
			masterForm.setTotalSearchRecords(totalRecords);
			masterForm.setStartSearchRecord(1);
			masterForm.setEndSearchRecord(10);
				String location =masterForm.getLocationId();
				String materialType=masterForm.getMaterialType();
				String materialGroup=masterForm.getMaterialGrup();
				String shortName=masterForm.getShortName();
				String longName=masterForm.getLongName();
				String getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.LOCATION_ID) AS RowNum, loc.LOCATION_CODE,m.MATERIAL_GROUP,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.SAP_CODE_NO,m.UNIT_OF_MEAS_ID,m.CREATED_DATE,m.SAP_CREATION_DATE,m.APPROXIMATE_PRICE  from Material_Code_Search as m , " +
				"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and mType.id=m.MATERIAL_TYPE   ";
		
		if(!(materialType.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_TYPE='"+materialType+"'";
		}
		if(!(materialGroup.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_GROUP='"+materialGroup+"'";
		}
		if(!(shortName.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and  m.MATERIAL_SHORT_NAME like '%"+shortName+"%'";
		}
		if(!(longName.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_LONG_NAME like '%"+longName+"%' ";
		}
		getMaterialRecords=getMaterialRecords+") as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+"";
		if(!(materialType.equalsIgnoreCase("")) && !(materialGroup.equalsIgnoreCase("")) && !(shortName.equalsIgnoreCase("")) && !(longName.equalsIgnoreCase("")))
		{
			getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.LOCATION_ID) AS RowNum, loc.LOCATION_CODE,m.MATERIAL_GROUP,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.SAP_CODE_NO,m.UNIT_OF_MEAS_ID,m.CREATED_DATE,m.SAP_CREATION_DATE,m.APPROXIMATE_PRICE from  Material_Code_Search as m," +
			"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and  mType.id=m.MATERIAL_TYPE   and " +
			"m.MATERIAL_TYPE = '"+materialType+"' and (m.MATERIAL_GROUP = '"+materialGroup+"' and m.MATERIAL_SHORT_NAME like '%"+shortName+"' and m.MATERIAL_LONG_NAME like '%"+longName+"%')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+"";
		}

		ResultSet rs=ad.selectQuery(getMaterialRecords);
		while(rs.next())
		{
			MaterialCodeRequestForm apprForm=new MaterialCodeRequestForm();
			apprForm.setLocationId(rs.getString("LOCATION_CODE"));
			 String codeNo=rs.getString("SAP_CODE_NO");
			    if(codeNo!=null)
			    {
			    	apprForm.setCodeNo(codeNo);
			    }else{
			    	apprForm.setCodeNo("");
			    }
			apprForm.setShortName(rs.getString("MATERIAL_SHORT_NAME"));
			apprForm.setLongName(rs.getString("MATERIAL_LONG_NAME"));
			apprForm.setUom(rs.getString("UNIT_OF_MEAS_ID"));
			apprForm.setMaterialGrup(rs.getString("MATERIAL_GROUP"));
			apprForm.setApproximatePrice(rs.getString("APPROXIMATE_PRICE"));
			apprForm.setCreatedOn("");
			 apprForm.setRequestedOn("");
			 String cretedOn=rs.getString("SAP_CREATION_DATE");
			   if(cretedOn!=null)
			   {
				   if(cretedOn!="")
					   apprForm.setCreatedOn(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
			   }
			   String requestedOn=rs.getString("CREATED_DATE");
			   if(requestedOn!=null)
			   {
				   if(requestedOn!="")
					   apprForm.setRequestedOn(EMicroUtils.display1(rs.getDate("CREATED_DATE")));
			   }
		    materialList.add(apprForm);
		}
		request.setAttribute("materialList", materialList);
			
		masterForm.setTotalSearchRecords(totalRecords);
		masterForm.setStartSearchRecord(startRecord);
		masterForm.setEndSearchRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(materialList.size()<10)
				{
					masterForm.setStartRecord(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("materialSearchList");
	}
	public void masterData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try{
			MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
			EssDao ad=new EssDao();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			
			LinkedList listOfMaterialCode=new LinkedList();
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
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
			
			String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("STXT"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
	
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	}
	public ActionForward nextSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		
		EssDao ad=new EssDao();
		masterData(mapping, form, request, response);
		LinkedList materialList=new LinkedList();
		try{
			int totalRecords=masterForm.getTotalSearchRecords();
			int startRecord=masterForm.getStartSearchRecord();
			int endRecord=masterForm.getEndSearchRecord();
			if(totalRecords>endRecord)
			{
				if(totalRecords==endRecord)
				{
					endRecord=totalRecords;
				}
				if(totalRecords>endRecord)
				{
				startRecord=endRecord+1;
				endRecord=endRecord+10;
				}
				String location =masterForm.getLocationId();
				String materialType=masterForm.getMaterialType();
				String materialGroup=masterForm.getMaterialGrup();
				String shortName=masterForm.getShortName();
				String longName=masterForm.getLongName();
				String getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.LOCATION_ID) AS RowNum, loc.LOCATION_CODE,m.MATERIAL_GROUP,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.SAP_CODE_NO,m.UNIT_OF_MEAS_ID,m.CREATED_DATE,m.SAP_CREATION_DATE,m.APPROXIMATE_PRICE  from Material_Code_Search as m , " +
				"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and mType.id=m.MATERIAL_TYPE   ";
		
		if(!(materialType.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_TYPE='"+materialType+"'";
		}
		if(!(materialGroup.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_GROUP='"+materialGroup+"'";
		}
		if(!(shortName.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and  m.MATERIAL_SHORT_NAME like '%"+shortName+"%'";
		}
		if(!(longName.equalsIgnoreCase("")))
		{
			getMaterialRecords=getMaterialRecords+" and m.MATERIAL_LONG_NAME like '%"+longName+"%' ";
		}
		getMaterialRecords=getMaterialRecords+") as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+"";
		if(!(materialType.equalsIgnoreCase("")) && !(materialGroup.equalsIgnoreCase("")) && !(shortName.equalsIgnoreCase("")) && !(longName.equalsIgnoreCase("")))
		{
			getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.LOCATION_ID) AS RowNum, loc.LOCATION_CODE,m.MATERIAL_GROUP,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.SAP_CODE_NO,m.UNIT_OF_MEAS_ID,m.CREATED_DATE,m.SAP_CREATION_DATE,m.APPROXIMATE_PRICE from  Material_Code_Search as m," +
			"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and  mType.id=m.MATERIAL_TYPE   and " +
			"m.MATERIAL_TYPE = '"+materialType+"' and (m.MATERIAL_GROUP = '"+materialGroup+"' and m.MATERIAL_SHORT_NAME like '%"+shortName+"' and m.MATERIAL_LONG_NAME like '%"+longName+"%')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+"";
		}

		ResultSet rs=ad.selectQuery(getMaterialRecords);
		while(rs.next())
		{
			MaterialCodeRequestForm apprForm=new MaterialCodeRequestForm();
			apprForm.setLocationId(rs.getString("LOCATION_CODE"));
			 String codeNo=rs.getString("SAP_CODE_NO");
			    if(codeNo!=null)
			    {
			    	apprForm.setCodeNo(codeNo);
			    }else{
			    	apprForm.setCodeNo("");
			    }
			apprForm.setShortName(rs.getString("MATERIAL_SHORT_NAME"));
			apprForm.setLongName(rs.getString("MATERIAL_LONG_NAME"));
			apprForm.setUom(rs.getString("UNIT_OF_MEAS_ID"));
			apprForm.setMaterialGrup(rs.getString("MATERIAL_GROUP"));
			apprForm.setApproximatePrice(rs.getString("APPROXIMATE_PRICE"));
			apprForm.setCreatedOn("");
			 apprForm.setRequestedOn("");
			 String cretedOn=rs.getString("SAP_CREATION_DATE");
			   if(cretedOn!=null)
			   {
				   if(cretedOn!="")
					   apprForm.setCreatedOn(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
			   }
			   String requestedOn=rs.getString("CREATED_DATE");
			   if(requestedOn!=null)
			   {
				   if(requestedOn!="")
					   apprForm.setRequestedOn(EMicroUtils.display1(rs.getDate("CREATED_DATE")));
			   }
		    materialList.add(apprForm);
		}
		request.setAttribute("materialList", materialList);
			}
			if(materialList.size()!=0)
			{
				masterForm.setTotalSearchRecords(totalRecords);
				masterForm.setStartSearchRecord(startRecord);
				masterForm.setEndSearchRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				masterForm.setTotalSearchRecords(totalRecords);
				masterForm.setStartSearchRecord(start);
				masterForm.setEndSearchRecord(end);
				
			}
		 if(materialList.size()<10)
		 {
			 masterForm.setTotalSearchRecords(totalRecords);
			 masterForm.setStartSearchRecord(startRecord);
			 masterForm.setEndSearchRecord(startRecord+materialList.size()-1);
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
		return mapping.findForward("materialSearchList");
	}
	
	public ActionForward showMaterialSeaarchlList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{ 
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		try{
			LinkedList listOfMaterialCode=new LinkedList();
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
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
			
			String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("STXT"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
	
			String location =masterForm.getLocationId();
			String materialType=masterForm.getMaterialType();
			String materialGroup=masterForm.getMaterialGrup();
			String shortName=masterForm.getShortName();
			String longName=masterForm.getLongName();
			LinkedList materialList=new LinkedList();
			
			int  totalSearchRecords=0;
			int  startSearchRecord=0;
			int  endSearchRecord=0;
			  String getTotalRecords="select count(*)  from Material_Code_Search as m , " +
					"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and mType.id=m.MATERIAL_TYPE   ";
			
			if(!(materialType.equalsIgnoreCase("")))
			{
				getTotalRecords=getTotalRecords+" and m.MATERIAL_TYPE='"+materialType+"'";
			}
			if(!(materialGroup.equalsIgnoreCase("")))
			{
				getTotalRecords=getTotalRecords+" and m.MATERIAL_GROUP='"+materialGroup+"'";
			}
			if(!(shortName.equalsIgnoreCase("")))
			{
				getTotalRecords=getTotalRecords+" and  m.MATERIAL_SHORT_NAME like '%"+shortName+"%'";
			}
			if(!(longName.equalsIgnoreCase("")))
			{
				getTotalRecords=getTotalRecords+" and m.MATERIAL_LONG_NAME like '%"+longName+"%' ";
			}
			if(!(materialType.equalsIgnoreCase("")) && !(materialGroup.equalsIgnoreCase("")) && !(shortName.equalsIgnoreCase("")) && !(longName.equalsIgnoreCase("")))
			{
				getTotalRecords="select count(*) from  Material_Code_Search as m,MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and  mType.id=m.MATERIAL_TYPE   and " +
				"m.MATERIAL_TYPE = '"+materialType+"' and (m.MATERIAL_GROUP = '"+materialGroup+"' and m.MATERIAL_SHORT_NAME like '%"+shortName+"' and m.MATERIAL_LONG_NAME like '%"+longName+"%')";
			}
			ResultSet rsSearch=ad.selectQuery(getTotalRecords);
			while(rsSearch.next()){
				totalSearchRecords=rsSearch.getInt(1);
			}
			if(totalSearchRecords>=10)
			  {
				masterForm.setTotalSearchRecords(totalSearchRecords);
			  startSearchRecord=1;
			  endSearchRecord=10;
			  masterForm.setStartSearchRecord(1);
			  masterForm.setEndSearchRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startSearchRecord=1;
				  endSearchRecord=totalSearchRecords;
				  masterForm.setTotalSearchRecords(totalSearchRecords);
				  masterForm.setStartSearchRecord(1);
				  masterForm.setEndSearchRecord(totalSearchRecords); 
			  }
			
			String getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.LOCATION_ID) AS RowNum, loc.LOCATION_CODE,m.MATERIAL_GROUP,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.SAP_CODE_NO,m.UNIT_OF_MEAS_ID,m.CREATED_DATE,m.SAP_CREATION_DATE,m.APPROXIMATE_PRICE  from Material_Code_Search as m , " +
					"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and mType.id=m.MATERIAL_TYPE   ";
			
			if(!(materialType.equalsIgnoreCase("")))
			{
				getMaterialRecords=getMaterialRecords+" and m.MATERIAL_TYPE='"+materialType+"'";
			}
			if(!(materialGroup.equalsIgnoreCase("")))
			{
				getMaterialRecords=getMaterialRecords+" and m.MATERIAL_GROUP='"+materialGroup+"'";
			}
			if(!(shortName.equalsIgnoreCase("")))
			{
				getMaterialRecords=getMaterialRecords+" and  m.MATERIAL_SHORT_NAME like '%"+shortName+"%'";
			}
			if(!(longName.equalsIgnoreCase("")))
			{
				getMaterialRecords=getMaterialRecords+" and m.MATERIAL_LONG_NAME like '%"+longName+"%' ";
			}
			getMaterialRecords=getMaterialRecords+") as  sub Where  sub.RowNum between 1 and 10";
			if(!(materialType.equalsIgnoreCase("")) && !(materialGroup.equalsIgnoreCase("")) && !(shortName.equalsIgnoreCase("")) && !(longName.equalsIgnoreCase("")))
			{
				getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.LOCATION_ID) AS RowNum, loc.LOCATION_CODE,m.MATERIAL_GROUP,m.MATERIAL_SHORT_NAME,m.MATERIAL_LONG_NAME,m.SAP_CODE_NO,m.UNIT_OF_MEAS_ID,m.CREATED_DATE,m.SAP_CREATION_DATE,m.APPROXIMATE_PRICE from  Material_Code_Search as m," +
				"MATERIAL_TYPE as mType,Location as loc where loc.LOCATION_CODE=m.LOCATION_ID and  mType.id=m.MATERIAL_TYPE   and " +
				"m.MATERIAL_TYPE = '"+materialType+"' and (m.MATERIAL_GROUP = '"+materialGroup+"' and m.MATERIAL_SHORT_NAME like '%"+shortName+"' and m.MATERIAL_LONG_NAME like '%"+longName+"%')) as  sub Where  sub.RowNum between 1 and 10";
			}
			try{
			ResultSet rs=ad.selectQuery(getMaterialRecords);
			while(rs.next())
			{
				MaterialCodeRequestForm apprForm=new MaterialCodeRequestForm();
				apprForm.setLocationId(rs.getString("LOCATION_CODE"));
				 String codeNo=rs.getString("SAP_CODE_NO");
				    if(codeNo!=null)
				    {
				    	apprForm.setCodeNo(codeNo);
				    }else{
				    	apprForm.setCodeNo("");
				    }
				apprForm.setShortName(rs.getString("MATERIAL_SHORT_NAME"));
				apprForm.setLongName(rs.getString("MATERIAL_LONG_NAME"));
				apprForm.setUom(rs.getString("UNIT_OF_MEAS_ID"));
				apprForm.setMaterialGrup(rs.getString("MATERIAL_GROUP"));
				apprForm.setApproximatePrice(rs.getString("APPROXIMATE_PRICE"));
				apprForm.setCreatedOn("");
			 apprForm.setRequestedOn("");
			 String cretedOn=rs.getString("SAP_CREATION_DATE");
			   if(cretedOn!=null)
			   {
				   if(cretedOn!="")
					   apprForm.setCreatedOn(EMicroUtils.display1(rs.getDate("SAP_CREATION_DATE")));
			   }
			   String requestedOn=rs.getString("CREATED_DATE");
			   if(requestedOn!=null)
			   {
				   if(requestedOn!="")
					   apprForm.setRequestedOn(requestedOn);
			   }
			    materialList.add(apprForm);
			}
			
			
			if(materialList.size()>0)
			{
				request.setAttribute("materialList", materialList);
				
			}
			if(materialList.size()==0)
			{
				request.setAttribute("noMaterialList", "noMaterialList");
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("materialSearchList");
	}
	
	
	public ActionForward getMaterialRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{ 
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		try{
			LinkedList listOfMaterialCode=new LinkedList();
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
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
			
			String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("STXT"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
	     masterForm.setLocationId("");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("materialSearchList");
	}
	
	public ActionForward curentRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{ 
		MaterialCodeRequestForm masterForm1=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();

		int totalRecords=masterForm1.getTotalRecords();//21
		int startRecord=masterForm1.getStartRecord();//11
		int endRecord=masterForm1.getEndRecord();//20
		
		
		if(totalRecords==0)
			
		{
			startRecord=1;
			endRecord=10;
			
		}
		
		LinkedList listOfMaterialCode=new LinkedList();
		
		masterForm1.setTotalRecords(totalRecords);
		masterForm1.setStartRecord(startRecord);
		masterForm1.setEndRecord(endRecord);
		
		String From=masterForm1.getFromDate();
		if(From!=null){
		if(!From.equalsIgnoreCase("")){
		  String b[]=From.split("/");
		  From=b[2]+"-"+b[1]+"-"+b[0];
		}
		}
		//String To=codeRequestForm.getToDate();
		String To=masterForm1.getToDate();
		if(To!=null){
		if(!To.equalsIgnoreCase("")){
		  String b1[]=To.split("/");
		  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
		}
		}
		
		String appStatus=masterForm1.getApproveStatus();
		String Location=masterForm1.getLocationId();
	    String Materialcode=masterForm1.getMaterialCodeLists();
		
	     try{
	    	 String availableLoc=user.getAvailableLocations();
				if(availableLoc.equalsIgnoreCase(""))
					availableLoc="0";
				ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				if(locationList.size()==0){
					locationList.add("");
					locationLabelList.add("");
				}
	 	
	 		masterForm1.setLocationIdList(locationList);
	 		masterForm1.setLocationLabelList(locationLabelList);
	 		
	 		LinkedList materTypeIDList=new LinkedList();
	 		LinkedList materialTypeIdValueList=new LinkedList();
	 		String getMaterials="select * from MATERIAL_TYPE";
	 		ResultSet rsMaterial=ad.selectQuery(getMaterials);
	 		while(rsMaterial.next())
	 		{
	 			materTypeIDList.add(rsMaterial.getString("id"));
	 			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
	 		}
	 		masterForm1.setMaterTypeIDList(materTypeIDList);
	 		masterForm1.setMaterialTypeIdValueList(materialTypeIdValueList);
	 		
	     }catch (Exception e) {
		e.printStackTrace();
		}	
	
		 try{
			 String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO desc) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
			 "m.URL,L.location_code,m.REPORT_URL,mType.M_DESC from material_code_request as m,Location as l ,MATERIAL_TYPE as mType  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and m.CREATED_BY='"+user.getEmployeeNo()+"'  ";
	 		 
			 if(!Location.equals(""))
				 query=query+" and m.LOCATION_ID='"+Location+"'";
			 if(!appStatus.equalsIgnoreCase("")){	
				 query=query+ " and m.Approve_Type='"+appStatus+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
					query=query+ " and m.Type='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
					query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
		  
		  int count=0;
	ResultSet rsList=ad.selectQuery(query);
	while(rsList.next())
	{
		MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
		 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
		 String requestDate=rsList.getString("REQUEST_DATE");
			String req[]=requestDate.split(" ");
			requestDate=req[0];
			String a[]=requestDate.split("-");
			requestDate=a[2]+"/"+a[1]+"/"+a[0];
			materialForm.setRequestDate(requestDate);
		materialForm.setLocationId(rsList.getString("location_code"));
		materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
		materialForm.setApproveType(rsList.getString("Approve_Type"));
		materialForm.setRequiredRequestNumber("");
		materialForm.setUrl(rsList.getString("URL"));
		materialForm.setmType(rsList.getString("Type")+" - "+rsList.getString("M_DESC"));
		materialForm.setReqMatType(rsList.getString("Type"));
		materialForm.setReportUrl(rsList.getString("REPORT_URL"));
		listOfMaterialCode.add(materialForm);
		count++;
		if(count==10)
		{
			break;
		}
	}
	 request.setAttribute("listOfMaterials", listOfMaterialCode);
		request.setAttribute("materialCodeList","materialCodeList");
		 }catch (Exception e) {
			e.printStackTrace();
		}
		 if(totalRecords>10){
		 	   
		       
			 
			 if(startRecord==1)
			 {
				 request.setAttribute("disablePreviousButton", "disablePreviousButton"); 
			 }
			 if(endRecord>10)
			 {
				 request.setAttribute("previousButton", "previousButton"); 
			 }
			 
			 if(endRecord==totalRecords)
			 {
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
			 }
			 if(endRecord<totalRecords)
			 {
				 request.setAttribute("nextButton", "nextButton");
			 }
			 request.setAttribute("displayRecordNo", "displayRecordNo");
	      }
		 masterForm1.setTotalRecords(totalRecords);
		 masterForm1.setStartRecord(startRecord);
		 masterForm1.setEndRecord(endRecord);
			  
			  
		 
		 
			
		
		return mapping.findForward("materialcodeList");
	}
	
	
	
	
	public ActionForward ViewMaterialrecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	
	{
		MaterialCodeRequestForm masterForm1=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		String forwardType="";
		int j=0;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String reqId=request.getParameter("ReqestNo");
		String matType=request.getParameter("matType");
		String loctID=request.getParameter("LoctID");
		
		int totalRecords=masterForm1.getTotalRecords();//21
		int startRecord=masterForm1.getStartRecord();//11
		int endRecord=masterForm1.getEndRecord();//20
		
		
		
		masterForm1.setTotalRecords(totalRecords);
		masterForm1.setStartRecord(startRecord);
		masterForm1.setEndRecord(endRecord);
		
		String From=masterForm1.getFromDate();
		String To=masterForm1.getToDate();
		
		String appStatus=masterForm1.getApproveStatus();
		String Location=masterForm1.getLocationId();
	     String Materialcode=masterForm1.getMaterialCodeLists();
	     
	     masterForm1.setMaterialCodeLists(Materialcode);
			masterForm1.setApproveStatus(appStatus);
			masterForm1.setLocationId(Location);
			masterForm1.setFromDate(From);
			masterForm1.setToDate(To);
	     
	     
		
		if(matType.equalsIgnoreCase("RM"))
		{

			LinkedList rawDetails=new LinkedList();
			ApprovalsForm pendAppForm=new ApprovalsForm();
			String getMaterial="select mat.HSN_Code,mat.REQUEST_NO,mat.Storage,mat.REQUEST_DATE,loc.LOCNAME,mat.MATERIAL_GROUP_ID,stLoc.STORAGE_LOCATION_NAME," +
			" mat.MATERIAL_SHORT_NAME,mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, mat.PHARMACOP_NAME," +
			"mat.PHARMACOP_GRADE,mat.GENERIC_NAME,mat.SYNONYM,mat.PHARMACOP_SPECIFICATION,mat.DUTY_ELEMENT, mat.IS_DMF_MATERIAL," +
			"dmf.DMF_GRADE_DESC,mat.MATERIAL_GRADE,mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST, mat.IS_VENDOR_SPECIFIC_MATERIAL," +
			"mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX,mat.CUSTOMER_NAME, mat.TO_BE_USED_IN_PRODUCTS,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE," +
			"mat.RETEST_DAYS ,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC,mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS, " +
			"SAP_CREATION_DATE,SAP_CREATED_BY,rejected_flag from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP " +
			"as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,DMF_GRADE as dmf,Country as cou, VALUATION_CLASS as val " +
			" where REQUEST_NO='"+reqId+"' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and " +
			"matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID " +
			"and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID and val.VALUATION_ID=mat.VALUATION_CLASS";	
			ResultSet rs=ad.selectQuery(getMaterial);
			try {
				while(rs.next())
				{
				
					pendAppForm.setRequestNo(reqId);
					pendAppForm.setRequestNumber(reqId);
					pendAppForm.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
				String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				pendAppForm.setRequestDate(reqDate);
				pendAppForm.setLocationId(rs.getString("LOCNAME"));
				pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
				pendAppForm.setStorage(rs.getString("Storage"));
				pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
				pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
				pendAppForm.setMaterialGroupId(rs.getString("STXT"));
				pendAppForm.setPharmacopName(rs.getString("PHARMACOP_NAME"));
				String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
				pendAppForm.setPharmacopGrade(pharmacopGrade);
				
				pendAppForm.setGenericName(rs.getString("GENERIC_NAME"));
				pendAppForm.setSynonym(rs.getString("SYNONYM"));
				pendAppForm.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
				String isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
				if(isDMfMaterial.equalsIgnoreCase("1"))
				{
					pendAppForm.setIsDMFMaterial("Yes");
				
					pendAppForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
					pendAppForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
					pendAppForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					
				}
				pendAppForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
				if(isDMfMaterial.equalsIgnoreCase("0"))
				{
					pendAppForm.setIsDMFMaterial("NO");
					pendAppForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
					pendAppForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
					pendAppForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					
					
				}
				
				pendAppForm.setCountryId(rs.getString("LANDX"));
				pendAppForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
				pendAppForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
				
				String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
				if(isVendorStatus.equalsIgnoreCase("1"))
				{
					pendAppForm.setIsVendorSpecificMaterial("YES");
					pendAppForm.setMfgrName(rs.getString("MFGR_NAME"));
					pendAppForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
				}
				if(isVendorStatus.equalsIgnoreCase("0"))
				{
					pendAppForm.setIsVendorSpecificMaterial("NO");
				}
				
				
				pendAppForm.setShelfLife(rs.getString("SHELF_LIFE"));
				String dutyElement=rs.getString("DUTY_ELEMENT");
				if(dutyElement.equalsIgnoreCase("0"))
				{
					pendAppForm.setDutyElement("0-Duty Exempted Raw Materials for Finished product");
				}
				if(dutyElement.equalsIgnoreCase("2")){
					pendAppForm.setDutyElement("2-Indigenous Material with or without Cenvat");
				}
				if(dutyElement.equalsIgnoreCase("3")){
					pendAppForm.setDutyElement("N/A");
				}
				pendAppForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
				pendAppForm.setRetestDays(rs.getString("RETEST_DAYS"));
				pendAppForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
				pendAppForm.setValuationClass(rs.getString("VALUATION_DESC"));
				pendAppForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				pendAppForm.setUnitOfMeasId(rs.getString("LTXT"));
				pendAppForm.setHsnCode(rs.getString("HSN_Code"));
				pendAppForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));


				
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
				
				String sapCodeno=rs.getString("SAP_CODE_NO");
				if(sapCodeno!=null)
				{
					pendAppForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
					String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
					if(sapCodeExist.equalsIgnoreCase("1"))
					{
						pendAppForm.setSapCodeExists("Yes");
					}
					if(sapCodeExist.equalsIgnoreCase("0"))
						pendAppForm.setSapCodeExists("No");
					String createDate=rs.getString("SAP_CREATION_DATE");
					String a1[]=createDate.split(" ");
					createDate=a1[0];
					String b1[]=createDate.split("-");
					createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					pendAppForm.setSapCreationDate(createDate);
					pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
				}
				
				
}
				String rejected_flag=rs.getString("rejected_flag");
				if(rejected_flag!=null)
				{
					if(rejected_flag.equalsIgnoreCase("y"))
						request.setAttribute("rejectedFLag", "rejectedFLag");
				}

}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			String getMaterial1="select tem.TEMP_CON_DESC from material_code_request as mat,TEMP_CONDITION as tem " +
			" where REQUEST_NO='"+reqId+"' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
	
	ResultSet rs1=ad.selectQuery(getMaterial1);
	try {
		while(rs1.next())
		{
			pendAppForm.setTempCondition(rs1.getString("TEMP_CON_DESC"));
		}
	} catch (SQLException e) {
				e.printStackTrace();
	}
	
	//STORAGE_CONDITION
	
	String getMaterial2="select stcon.LTXT from material_code_request as mat,STORAGE_CONDITION as stcon" +
			" where REQUEST_NO='"+reqId+"'  and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
	
	ResultSet rs2=ad.selectQuery(getMaterial2);
	try {
		while(rs2.next())
		{
			pendAppForm.setStorageCondition(rs2.getString("LTXT"));
		}
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	rawDetails.add(pendAppForm);
	request.setAttribute("rawdetails", rawDetails);
	

	
	
	String location="";
	String matGroup="";
	String matDetails="select loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+reqId+"' and mat.LOCATION_ID=loc.LOCID";
	ResultSet rsDetails=ad.selectQuery(matDetails);
	try {
		if(rsDetails.next())
		{
			location=rsDetails.getString("LOCATION_CODE");
			matGroup=rsDetails.getString("MATERIAL_GROUP_ID");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	int  checkStatus=0;
	LinkedList listApprers=new LinkedList();
	String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
	"where mast.Location='"+location+"' AND  mast.Material_Type='RM' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
	ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
	try {
		while(rsApprDetails.next())
		{
			checkStatus=1;
			ApprovalsForm apprvers=new ApprovalsForm();
			apprvers.setPriority(rsApprDetails.getString("Priority"));
			apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
			String empCode=rsApprDetails.getString("Approver_Id");
			String actualapprover="";
			boolean data=false;
			
			String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
			"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
			"mast.Material_Type='RM' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
			"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
			"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
			ResultSet rsAppInfo=ad.selectQuery(recordStatus);
			if(rsAppInfo.next())
			{
				actualapprover=rsAppInfo.getString("Actual_Approver");
				if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
				{
					apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
					data=true;
				}
				if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
				{
					apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
					data=true;
				}
				if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
				{
					apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
					data=true;
				}
				apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
				String approveStatus=rsAppInfo.getString("Req_Status");
				if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
				{
				apprvers.setDate(rsAppInfo.getString("approved_date"));
				}else{
					apprvers.setDate(rsAppInfo.getString("rejected_date"));
				}
				String comments=rsAppInfo.getString("Comments");
				if(comments==null || comments.equalsIgnoreCase(""))
				{
					apprvers.setComments("");
				}else{
					apprvers.setComments(rsAppInfo.getString("Comments"));
				}
			}
			String pernr="";

			if(data==true)
							{
								pernr=actualapprover;
							}
							else
							{
								pernr=rsApprDetails.getString("Approver_Id");
							}						
					
					
				String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
				ResultSet rsname=ad.selectQuery(name);
				if(rsname.next())
				{
					apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
				}
			listApprers.add(apprvers);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(checkStatus==0)
	{
		getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
		"where mast.Location='"+location+"' AND  mast.Material_Type='RM' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
		rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				String actualapprover="";
				boolean data=false;
				
				String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
				"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
				"mast.Material_Type='RM' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
				"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
				"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				while(rsAppInfo.next())
				{
					actualapprover=rsAppInfo.getString("Actual_Approver");
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
						data=true;
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
					{
						String approveDate=	rsAppInfo.getString("approved_date");
						
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						apprvers.setDate(approveDate);
					}else{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						apprvers.setDate(rejectDate);
					}
					String comments=rsAppInfo.getString("Comments");
					if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				String pernr="";

				if(data==true)
								{
									pernr=actualapprover;
								}
								else
								{
									pernr=rsApprDetails.getString("Approver_Id");
								}						
						
						
					String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
					ResultSet rsname=ad.selectQuery(name);
					if(rsname.next())
					{
						apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
					}
				listApprers.add(apprvers);
}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	request.setAttribute("approverDetails", listApprers);
	forwardType="ROHDetails";
		}
		
		if(matType.equalsIgnoreCase("PM"))
		{
			LinkedList pacDetails=new LinkedList();

			
		ApprovalsForm materialForm=new ApprovalsForm();
			

			String matGroup="";
			 String location="";
		
			 String getMaterial="select mat.REQUEST_NO,mat.Storage,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
				"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.DUTY_ELEMENT,pacgrp.PACKING_MATERIAL_GROUP_NAME," +
				"mat.Type_Of_Material,mat.ARTWORK_NO,mat.IS_ARTWORK_REVISION,mat.EXISTING_SAP_ITEM_CODE,mat.IS_DMF_MATERIAL,dmf.DMF_GRADE_DESC," +
				"mat.COS_GRADE_AND_NO,mat.ADDITIONAL_TEST,mat.IS_VENDOR_SPECIFIC_MATERIAL,mat.MFGR_NAME,mat.SITE_OF_MANUFACTURE,cou.LANDX," +
				"mat.CUSTOMER_NAME,mat.TO_BE_USED_IN_PRODUCTS,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,val.VALUATION_DESC," +
				"mat.APPROXIMATE_VALUE,mat.Attachements,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc," +
				"MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PURCHASE_GROUP as pur,PACKAGE_MATERIAL_GROUP as pacgrp,DMF_GRADE as dmf," +
				"Country as cou,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' " +
				"and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID" +
				" and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID  and pacgrp.PACKING_MATERIAL_GROUP_ID=mat.PACKING_MATERIAL_GROUP " +
				" and dmf.DMF_GRADE_ID=mat.DMF_GRADE_ID and cou.LAND1=mat.COUNTRY_ID  " +
				" and val.VALUATION_ID=mat.VALUATION_CLASS";
				
				
				
				ResultSet rs=ad.selectQuery(getMaterial);
				
					try {
						if(rs.next())
{
							materialForm.setRequestNo(reqId);
							materialForm.setRequestNumber(reqId);
							matType=rs.getString("Type");
							matGroup=rs.getString("MATERIAL_GROUP_ID");
							location=rs.getString("LOCATION_CODE");
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							materialForm.setRequestDate(reqDate);
							materialForm.setLocationId(rs.getString("LOCNAME"));
							materialForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
							materialForm.setStorage(rs.getString("Storage"));
							materialForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							materialForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							materialForm.setMaterialGroupId(rs.getString("STXT"));
							String isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
							if(isDMFMaterial.equalsIgnoreCase("1"))
							{
								materialForm.setIsDMFMaterial("YES");
								request.setAttribute("dmfMandatory", "dmfMandatory");
								materialForm.setDmfGradeId(rs.getString("DMF_GRADE_DESC"));
								materialForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
							}
							if(isDMFMaterial.equalsIgnoreCase("0")){
								materialForm.setIsDMFMaterial("NO");
								request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
							}
							materialForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
							
							materialForm.setCountryId(rs.getString("LANDX"));
							materialForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
							materialForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
							String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
							if(isVendorStatus.equalsIgnoreCase("1"))
							{
								materialForm.setIsVendorSpecificMaterial("YES");
								request.setAttribute("vedorMandatory", "vedorMandatory");
								materialForm.setMfgrName(rs.getString("MFGR_NAME"));
								materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
							}
							if(isVendorStatus.equalsIgnoreCase("0"))
							{
								materialForm.setIsVendorSpecificMaterial("NO");
								request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
							}
								materialForm.setMfgrName(rs.getString("MFGR_NAME"));
							materialForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
							
							materialForm.setRetestDays(rs.getString("RETEST_DAYS"));
							materialForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
							String dutyElement=rs.getString("DUTY_ELEMENT");
							if(dutyElement.equalsIgnoreCase("1"))
							{
								materialForm.setDutyElement("0-Indigenous Material with or without Cenvat");
							}
							if(dutyElement.equalsIgnoreCase("0"))
								materialForm.setDutyElement("1-Duty Exempted Packing Materials for Finished product");
							materialForm.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP_NAME"));
							materialForm.setTypeOfMaterial(rs.getString("Type_Of_Material"));
							String typeOfMaterial=rs.getString("Type_Of_Material");
							
							if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
							{
								request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
								materialForm.setArtworkNo(rs.getString("ARTWORK_NO"));
								String isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
								if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
								{
									materialForm.setIsArtworkRevision("YES");
								}
								if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
									materialForm.setIsArtworkRevision("NO");
								
							}
							else
								request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
							
							materialForm.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
							materialForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						
							
							ArrayList fileList = new ArrayList();
							String uploadedFiles=rs.getString("Attachements");
							materialForm.setUnitOfMeasId(rs.getString("LTXT"));
							materialForm.setValuationClass(rs.getString("VALUATION_DESC"));
							materialForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
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
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null)
							{
								materialForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
								if(sapCodeExist.equalsIgnoreCase("1"))
								{
									materialForm.setSapCodeExists("Yes");
								}
								if(sapCodeExist.equalsIgnoreCase("0"))
									materialForm.setSapCodeExists("No");
								String createDate=rs.getString("SAP_CREATION_DATE");
								String a1[]=createDate.split(" ");
								createDate=a1[0];
								String b1[]=createDate.split("-");
								createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
								materialForm.setSapCreationDate(createDate);
								materialForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
							}
							
}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//temp cond
					
					String getMaterial1="select tem.TEMP_CON_DESC from material_code_request as mat,TEMP_CONDITION as tem " +
							" where REQUEST_NO='"+reqId+"' and tem.TEMP_CON_ID=mat.TEMP_CONDITION ";
					
					ResultSet rs1=ad.selectQuery(getMaterial1);
					try {
						if(rs1.next())
						{
							materialForm.setTempCondition(rs1.getString("TEMP_CON_DESC"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//STORAGE_CONDITION
					
					String getMaterial2="select stcon.LTXT from material_code_request as mat,STORAGE_CONDITION as stcon" +
							" where REQUEST_NO='"+reqId+"'  and stcon.STO_COND_CODE=mat.STORAGE_CONDITION";	
					
					ResultSet rs2=ad.selectQuery(getMaterial2);
					try {
						if(rs2.next())
						{
							materialForm.setStorageCondition(rs2.getString("LTXT"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pacDetails.add(materialForm);
				
			
		
		request.setAttribute("pacdetails", pacDetails);
		
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
		"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				String actualapprover="";
				boolean data=false;
				
				String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
				"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
				"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
				"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
				"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					actualapprover=rsAppInfo.getString("Actual_Approver");
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
						data=true;
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
					{
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments=rsAppInfo.getString("Comments");
					if(comments==null || comments.equalsIgnoreCase(""))
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				String pernr="";

				if(data==true)
								{
									pernr=actualapprover;
								}
								else
								{
									pernr=rsApprDetails.getString("Approver_Id");
								}						
						
						
					String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
					ResultSet rsname=ad.selectQuery(name);
					if(rsname.next())
					{
						apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
					}
				listApprers.add(apprvers);
				request.setAttribute("approverDetails", listApprers);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(checkStatus==0)
		{

			getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
			"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
			rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						actualapprover=rsAppInfo.getString("Actual_Approver");
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(approveDate);
						}else{
							String rejectDate=	rsAppInfo.getString("rejected_date");
							
							String a[]=rejectDate.split(" ");
							rejectDate=a[0];
							String b[]=rejectDate.split("-");
							rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(rejectDate);
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					String pernr="";

					if(data==true)
									{
										pernr=actualapprover;
									}
									else
									{
										pernr=rsApprDetails.getString("Approver_Id");
									}						
							
							
						String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
						ResultSet rsname=ad.selectQuery(name);
						if(rsname.next())
						{
							apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
						}
					listApprers.add(apprvers);
					
}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		request.setAttribute("approverDetails", listApprers);
		forwardType="pacmaterial";
	}
		
		if(matType.equalsIgnoreCase("BULK"))
		{

	 		LinkedList semfDetails=new LinkedList();

	 		ApprovalsForm masterForm=new ApprovalsForm();
	 		
	 	
	           
	        String matGroup="";
	        String location="";
	 		String getSemiFinished="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
	 				"mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pac.PACK_SIZE_DESC,mat.CUSTOMER_NAME,mat.SHELF_LIFE," +
	 				"mat.SHELF_LIFE_TYPE,mat.RETEST_DAYS,mat.RETEST_DAYS_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ,mat.TARGET_WEIGHT," +
	 				"wei.W_UOM_DESC,val.VALUATION_DESC,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as " +
	 				"stLoc,MATERIAL_GROUP as matGroup ,UNIT_MESUREMENT AS uom,PACK_SIZE as pac,WEIGHT_UOM as wei,VALUATION_CLASS" +
	 				" as val where REQUEST_NO='"+reqId+"' and loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID " +
	 				"and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID	" +
	 				"and pac.PACK_SIZE_CODE=mat.PACK_SIZE and wei.W_UOM_CODE=mat.WEIGHT_UOM " +
	 				"and val.VALUATION_ID=mat.VALUATION_CLASS";
			ResultSet rs=ad.selectQuery(getSemiFinished);
			try {
				if(rs.next())
				{
					masterForm.setRequestNo(reqId);
					masterForm.setRequestNumber(reqId);
					matType=rs.getString("Type");
					matGroup=rs.getString("MATERIAL_GROUP_ID");
					location=rs.getString("LOCATION_CODE");
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					masterForm.setRequestDate(reqDate);
					
					masterForm.setLocationId(rs.getString("LOCNAME"));
					
					masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
					masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					masterForm.setMaterialGroupId(rs.getString("STXT"));
					
					masterForm.setUnitOfMeasId(rs.getString("LTXT"));
					masterForm.setPackSize(rs.getString("PACK_SIZE_DESC"));
				
					masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
					masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
					masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
					masterForm.setBatchCode(rs.getString("BATCH_CODE"));
					masterForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
					
					
					masterForm.setWeightUOM(rs.getString("W_UOM_DESC"));
					masterForm.setRetestDays(rs.getString("RETEST_DAYS"));
					masterForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
					masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
					
					

					
					
					
					request.setAttribute("semidetails", semfDetails);
					
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							masterForm.setSapCodeExists("Yes");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							masterForm.setSapCodeExists("No");
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						masterForm.setSapCreationDate(createDate);
						masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
					
					
					
					
					
					
					forwardType="semimaterial";

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//country
			String editRecord1="select cou.LANDX from material_code_request as m ,Country as cou " +
					"where REQUEST_NO='"+reqId+"'and cou.LAND1=m.COUNTRY_ID ";
 		
 		
		ResultSet rs1=ad.selectQuery(editRecord1);
		try {
			if(rs1.next())
			{
				masterForm.setCountryId(rs1.getString("LANDX"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		semfDetails.add(masterForm);
		request.setAttribute("semfdetails", semfDetails);
			
		int  checkStatus=0;
		LinkedList listApprers=new LinkedList();
		String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
		"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
		ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
		try {
			while(rsApprDetails.next())
			{
				checkStatus=1;
				ApprovalsForm apprvers=new ApprovalsForm();
				apprvers.setPriority(rsApprDetails.getString("Priority"));
				apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
				String empCode=rsApprDetails.getString("Approver_Id");
				String actualapprover="";
				boolean data=false;
				
				String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
				"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
				"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
				"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
				"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
				ResultSet rsAppInfo=ad.selectQuery(recordStatus);
				if(rsAppInfo.next())
				{
					actualapprover=rsAppInfo.getString("Actual_Approver");
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
						data=true;
					}
					if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
					{
						apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
						data=true;
					}
					apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
					String approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
					{
					apprvers.setDate(rsAppInfo.getString("approved_date"));
					}else{
						apprvers.setDate(rsAppInfo.getString("rejected_date"));
					}
					String comments=rsAppInfo.getString("Comments");
					if(comments==null || comments.equalsIgnoreCase(""))
					{
						apprvers.setComments("");
					}else{
						apprvers.setComments(rsAppInfo.getString("Comments"));
					}
				}
				String pernr="";

				if(data==true)
								{
									pernr=actualapprover;
								}
								else
								{
									pernr=rsApprDetails.getString("Approver_Id");
								}						
						
						
					String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
					ResultSet rsname=ad.selectQuery(name);
					if(rsname.next())
					{
						apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
					}
				listApprers.add(apprvers);
				request.setAttribute("approverDetails", listApprers);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(checkStatus==0)
		{

			getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
			"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
			rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						actualapprover=rsAppInfo.getString("Actual_Approver");
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(approveDate);
						}else{
							String rejectDate=	rsAppInfo.getString("rejected_date");
							
							String a[]=rejectDate.split(" ");
							rejectDate=a[0];
							String b[]=rejectDate.split("-");
							rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							apprvers.setDate(rejectDate);
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					String pernr="";

					if(data==true)
									{
										pernr=actualapprover;
									}
									else
									{
										pernr=rsApprDetails.getString("Approver_Id");
									}						
							
							
						String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
						ResultSet rsname=ad.selectQuery(name);
						if(rsname.next())
						{
							apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
						}
					listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
			
			
	     
			
			
			
		}
		if(matType.equalsIgnoreCase("FG")||matType.equalsIgnoreCase("HAWA"))
		{
	 		LinkedList finDetails=new LinkedList();

	 	
	 		String matGroup="";
	 		 String location="";
	 		ApprovalsForm masterForm=new ApprovalsForm();
	 		
	 	
			
			
	 		String getFinishedProduct="select mat.REQUEST_NO,mat.Storage,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,mat.MANUFACTURED_AT," +
		 	  "mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,mat.DOMESTIC_OR_EXPORTS,cou.LANDX,mat.CUSTOMER_NAME," +
		 	  "mat.SALEABLE_OR_SAMPLE,mat.SALES_PACK_ID,pac.P_TYPE_DESC,sal.S_UOM_DESC,div.DIV_DESC,the.THER_SEG_DESC,bran.BRAND_DESC, " +
		 	  "st.STRENGTH_DESC,gen.GEN_NAME_DESC,mat.GROSS_WEIGHT,mat.NET_WEIGHT,mat.DIMENSION," +
		 	  "mat.Material_Pricing,mat.SHELF_LIFE,mat.SHELF_LIFE_TYPE,mat.STANDARD_BATCH_SIZE,mat.BATCH_CODE ," +
		 	  "SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,MATERIAL_GROUP as matGroup ," +
		 	  "UNIT_MESUREMENT AS uom,Country as cou,PACK_TYPE as pac,SALES_UOM as sal,DIVISION as div,THERAPEUTIC_SEGMENT as the,BRAND as bran," +
		 	  "STRENGTH as st,GENERIC_NAME as gen " +
		 	  "where REQUEST_NO='"+reqId+"' and loc.LOCID=mat.LOCATION_ID  and " +
		 	  "cou.LAND1=mat.COUNTRY_ID and pac.P_TYPE_CODE=mat.PACK_TYPE_ID and sal.S_UOM_CODE=mat.SALES_UNIT_OF_MEAS_ID and " +
		 	  "uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID " +
		 	  "and the.THER_SEG_CODE=mat.THERAPEUTIC_SEGMENT_ID and bran.BRAND_CODE=mat.BRAND_ID and st.STRENGTH_CODE=mat.STRENGTH_ID" +
		 	  " and gen.GEN_NAME_CODE=mat.GENERIC_NAME";
	 		ResultSet rs=ad.selectQuery(getFinishedProduct);
			try {
				if(rs.next())
				{
					masterForm.setRequestNo(reqId);
					masterForm.setRequestNumber(reqId);
					matType=rs.getString("Type");
					masterForm.setLocationId(rs.getString("LOCNAME"));
					matGroup=rs.getString("MATERIAL_GROUP_ID");
					location=rs.getString("LOCATION_CODE");
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					masterForm.setRequestDate(reqDate);
					masterForm.setStorage(rs.getString("Storage"));
					
					
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
					
					masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					masterForm.setMaterialGroupId(rs.getString("STXT"));
					
					masterForm.setCountryId(rs.getString("LANDX"));
					masterForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					masterForm.setShelfLife(rs.getString("SHELF_LIFE"));
					masterForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
					masterForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
					masterForm.setBatchCode(rs.getString("BATCH_CODE"));
					String salorsam=rs.getString("SALEABLE_OR_SAMPLE");
					if(salorsam.equalsIgnoreCase("1")){
						masterForm.setSaleableOrSample("Saleable");
						}
						if(salorsam.equalsIgnoreCase("2")){
							masterForm.setSaleableOrSample("Sample");
							}
						if(salorsam.equalsIgnoreCase("3")){
							masterForm.setSaleableOrSample("Converted Sample");
							}
					String domorexp=rs.getString("DOMESTIC_OR_EXPORTS");
					if(domorexp.equalsIgnoreCase("D")){
					masterForm.setDomesticOrExports("DOMESTIC");
					}
					if(domorexp.equalsIgnoreCase("E")){
						masterForm.setDomesticOrExports("EXPORTS");
						}
					if(domorexp.equalsIgnoreCase("V")){
						masterForm.setDomesticOrExports("Validation");
						}
					String salesPackId=rs.getString("SALES_PACK_ID");
					masterForm.setSalesPackId(rs.getString("SALES_PACK_ID"));
					masterForm.setPackTypeId(rs.getString("P_TYPE_DESC"));
					masterForm.setSalesUnitOfMeaseurement(rs.getString("S_UOM_DESC"));
					masterForm.setDivisionId(rs.getString("DIV_DESC"));
					masterForm.setTherapeuticSegmentID(rs.getString("THER_SEG_DESC"));
					masterForm.setBrandID(rs.getString("BRAND_DESC"));
					masterForm.setSrengthId(rs.getString("STRENGTH_DESC"));
					masterForm.setGenericName(rs.getString("GEN_NAME_DESC"));
					
					
					String matpri=rs.getString("Material_Pricing");
					if(matpri.equalsIgnoreCase("1")){
						masterForm.setMaterialPricing("Normal");
						}
					if(matpri.equalsIgnoreCase("2")){
						masterForm.setMaterialPricing("Spare parts");
						}
					if(matpri.equalsIgnoreCase("11")){
						masterForm.setMaterialPricing("Scheduled(Controled)");
						}
					if(matpri.equalsIgnoreCase("12")){
						masterForm.setMaterialPricing("Un-Scheduled(De-Con)");
						}
					if(matpri.equalsIgnoreCase("13")){
						masterForm.setMaterialPricing("PS / Promo (Micro)");
						}
					if(matpri.equalsIgnoreCase("14")){
						masterForm.setMaterialPricing("No MRP ED Extra(Mic)");
						}
					if(matpri.equalsIgnoreCase("15")){
						masterForm.setMaterialPricing("No MRP ED Incl (Mic)");
						}
					if(matpri.equalsIgnoreCase("16")){
						masterForm.setMaterialPricing("Scrap IT Extra (Mic)");
						}
					if(matpri.equalsIgnoreCase("17")){
						masterForm.setMaterialPricing("MRP(Con)-ED Exe(Mic)");
						}
					if(matpri.equalsIgnoreCase("18")){
						masterForm.setMaterialPricing("MRP(DeC)-ED Exe(Mic)");
						}
					if(matpri.equalsIgnoreCase("19")){
						masterForm.setMaterialPricing("No MRP-ED Exe(Mic)");
						}
					if(matpri.equalsIgnoreCase("20")){
						masterForm.setMaterialPricing("Food Prod(MRP)-Micro)");
						}
					if(matpri.equalsIgnoreCase("21")){
						masterForm.setMaterialPricing("Vet Prod (MRP)-Micr");
						}
					if(matpri.equalsIgnoreCase("22")){
						masterForm.setMaterialPricing("Generic Price Grp-Mi");
						}
					if(matpri.equalsIgnoreCase("23")){
						masterForm.setMaterialPricing("MRP(Vet)-ED Exe(Mic)");
						}
					if(matpri.equalsIgnoreCase("24")){
						masterForm.setMaterialPricing("P-to-P ED Extra(Mic)");
						}
					if(matpri.equalsIgnoreCase("25")){
						masterForm.setMaterialPricing("Cosm Prod(MRP)-Micro");
						}
					if(matpri.equalsIgnoreCase("26")){
						masterForm.setMaterialPricing("Scrap - ED Exe(Mic))");
						}
					if(matpri.equalsIgnoreCase("27")){
						masterForm.setMaterialPricing("Import Items PG");
						}
				
					masterForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
					masterForm.setNetWeight(rs.getString("NET_WEIGHT"));
					
					masterForm.setDimension(rs.getString("DIMENSION"));
					masterForm.setUnitOfMeasId(rs.getString("LTXT"));
				
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							masterForm.setSapCodeExists("Yes");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							masterForm.setSapCodeExists("No");
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						masterForm.setSapCreationDate(createDate);
						masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
					
								
				}
				
				// masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME")); masterForm.setWeightUOM(rs.getString("W_UOM_DESC")); masterForm.setValuationClass(rs.getString("VALUATION_DESC"));  masterForm.setWeightUOM(rs.getString("W_UOM_DESC"));
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			masterForm.setStorageLocationId("");
			masterForm.setWeightUOM("");
			masterForm.setValuationClass("");
	
		try {
				
			//storage location	
			String getStorageLoc="select s.STORAGE_LOCATION_ID,s.STORAGE_LOCATION_NAME from material_code_request m,STORAGE_LOCATION s where "
					+ "REQUEST_NO='"+reqId+"' and m.STORAGE_LOCATION_ID=s.STORAGE_LOCATION_ID";
			ResultSet rsStLoc=ad.selectQuery(getStorageLoc);
			if(rsStLoc.next()){
				masterForm.setStorageLocationId(rsStLoc.getString("STORAGE_LOCATION_ID")+"-"+rsStLoc.getString("STORAGE_LOCATION_NAME"));
			}
			//weight wom
			String getweight="select w.W_UOM_CODE,w.W_UOM_DESC from material_code_request m,WEIGHT_UOM w where REQUEST_NO='"+reqId+"' "
					+ "and m.WEIGHT_UOM=w.W_UOM_CODE";
			ResultSet rsWt=ad.selectQuery(getweight);
			if(rsWt.next()){
				masterForm.setWeightUOM(rsWt.getString("W_UOM_CODE")+"-"+rsWt.getString("W_UOM_DESC"));
			}
			String getValuation="select v.VALUATION_DESC from material_code_request m,VALUATION_CLASS v where REQUEST_NO='"+reqId+"' "
					+ "and m.VALUATION_CLASS=v.VALUATION_ID";
			ResultSet rsValu=ad.selectQuery(getValuation);
			if(rsValu.next()){
				masterForm.setValuationClass(rsValu.getString("VALUATION_DESC"));
			}
			//purchasegrp
			String getFinishedProduct1="select pur.PURCHASE_GROUP_DESC from material_code_request as mat,PURCHASE_GROUP as pur" +
			" where REQUEST_NO='"+reqId+"' and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID";
			ResultSet rs1=ad.selectQuery(getFinishedProduct1);
			if(rs1.next())
			{
				masterForm.setPuchaseGroupId(rs1.getString("PURCHASE_GROUP_DESC"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	
		//tax type
		String editRecord11="select tax.T_CLASS_NAME from material_code_request as m,TAX_CLASS as tax" +
				" where REQUEST_NO='"+reqId+"'and  tax.T_CLASS_ID=m.Tax_Classification";
		
		
		ResultSet rsEditRecord11=ad.selectQuery(editRecord11);
	try {
		if(rsEditRecord11.next())
		{
			masterForm.setTaxClassification(rsEditRecord11.getString("T_CLASS_NAME"));
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     

	if(matType.equals("FG")||matType.equals("HAWA")){
		String matDetails="select loc.LOCATION_CODE,mat.Type,mat.CREATED_BY,mat.DOMESTIC_OR_EXPORTS from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+reqId+"' and mat.LOCATION_ID=loc.LOCID";
		ResultSet rsDetails=ad.selectQuery(matDetails);
		try{
			while(rsDetails.next())
		
		{
			
			matGroup=rsDetails.getString("DOMESTIC_OR_EXPORTS");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(matGroup.equals("D"))
			matGroup="Domestic";
		if(matGroup.equals("E"))
 			matGroup="Export";
 		if(matGroup.equals("V"))
 			matGroup="V";
	}
			
			finDetails.add(masterForm);
			request.setAttribute("findetails", finDetails);
			forwardType="finmaterial";
			int  checkStatus=0;
			LinkedList listApprers=new LinkedList();
			String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
			"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
			ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
			try {
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String actualapprover="";
					boolean data=false;
					
					String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					if(rsAppInfo.next())
					{
						actualapprover=rsAppInfo.getString("Actual_Approver");
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
							data=true;
						}
						if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
						{
							apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
							data=true;
						}
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						String approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments==null || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					String pernr="";

					if(data==true)
									{
										pernr=actualapprover;
									}
									else
									{
										pernr=rsApprDetails.getString("Approver_Id");
									}						
							
							
						String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
						ResultSet rsname=ad.selectQuery(name);
						if(rsname.next())
						{
							apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
						}
					listApprers.add(apprvers);
					request.setAttribute("approverDetails", listApprers);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(checkStatus==0)
			{

				getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
				rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;
						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
								String approveDate=	rsAppInfo.getString("approved_date");
								
								String a[]=approveDate.split(" ");
								approveDate=a[0];
								String b[]=approveDate.split("-");
								approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(approveDate);
							}else{
								String rejectDate=	rsAppInfo.getString("rejected_date");
								
								String a[]=rejectDate.split(" ");
								rejectDate=a[0];
								String b[]=rejectDate.split("-");
								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(rejectDate);
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
     
			forwardType="finmaterial";
     }
		
			if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("LC")
					||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
		{
		 		LinkedList genDetails=new LinkedList();

		 		ApprovalsForm masterForm=new ApprovalsForm();
		 		
		 		String matGroup="";
		 		 String location="";
				String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME," +
						"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.is_asset,dep.DPTSTXT" +
						",mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from " +
						"material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , " +
						"UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+reqId+"' and	" +
						"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
						"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='"+matType+"' and val.VALUATION_ID=mat.VALUATION_CLASS" +
						" and dep.DPTID=mat.UTILIZING_DEPT";
				
				ResultSet rs=ad.selectQuery(getFinishedProduct);
				try {
					if(rs.next())
					{
						masterForm.setRequestNo(reqId);
						masterForm.setRequestNumber(reqId);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");

						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
					
						masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
						masterForm.setUtilizingDept(rs.getString("DPTSTXT"));
						String isAsset=rs.getString("is_asset");
						if(isAsset.equalsIgnoreCase("1"))
						{
							masterForm.setIsAsset("YES");
						}
						if(isAsset.equalsIgnoreCase("0"))
							masterForm.setIsAsset("NO");
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
						
						genDetails.add(masterForm);
						request.setAttribute("gendetails", genDetails);
					

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;

						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments==null || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkStatus==0)
				{

					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;

							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									String approveDate=	rsAppInfo.getString("approved_date");
									
									String a[]=approveDate.split(" ");
									approveDate=a[0];
									String b[]=approveDate.split("-");
									approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				  
				forwardType="genmaterial";
				}
			
			if(matType.equalsIgnoreCase("OSE"))
		     {
		 		LinkedList planDetails=new LinkedList();
		 	
		 		String matGroup="";
		 		 String location="";
		 		
		 		ApprovalsForm masterForm=new ApprovalsForm();
		 		
		 		
		 		
		 		String getFinishedProduct="select mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME,mat.MATERIAL_SHORT_NAME," +
		 		" mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC,mat.IS_EQUIPMENT,mat.EQUIPMENT_NAME,mat.EQUIPMENT_MAKE," +
		 		"mat.Component_MAKE,mat.IS_SPARE,mat.OEM_PartNo,mat.IS_NEW_Equipment,mat.IS_NEW_Furniture,mat.IS_NEW_Facility,	mat.IS_Spare_required,mat.moc,mat.rating,mat.range," +
		 		"mat.PO_NUMBER,mat.PR_NUMBER,dep.DPTSTXT,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION,	mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY,DIMENSION,mat.PACK_SIZE,mat.equip_Intended" +
		 		" from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as matGroup , UNIT_MESUREMENT AS uom " +
		 		",PURCHASE_GROUP as pur ,VALUATION_CLASS as val ,DEPARTMENT as dep where REQUEST_NO='"+reqId+"' and	loc.LOCID=mat.LOCATION_ID and " +
		 		"stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID " +
		 		"and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and TYPE='OSE' and val.VALUATION_ID=mat.VALUATION_CLASS and dep.DPTID=mat.UTILIZING_DEPT";
				
		 		
		 		
		 		ResultSet rs=ad.selectQuery(getFinishedProduct);
				try {
					if(rs.next())
					{
						masterForm.setRequestNo(reqId);
						masterForm.setRequestNumber(reqId);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");
						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
					
						masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
					
						String isEquipment=rs.getString("IS_EQUIPMENT");
						if(isEquipment.equalsIgnoreCase("1"))
						{
							masterForm.setIsEquipment("Yes");
							
						}
						masterForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
						masterForm.setEquipmentMake(rs.getString("EQUIPMENT_MAKE"));
						if(isEquipment.equalsIgnoreCase("0")){
							masterForm.setIsEquipment("No");
						
						}
						String isSpare=rs.getString("IS_SPARE");
						if(isSpare.equalsIgnoreCase("1"))
						{
							masterForm.setIsSpare("Yes");
						
						}
						masterForm.setComponentMake(rs.getString("Component_MAKE"));
						masterForm.setOemPartNo(rs.getString("OEM_PartNo"));
						if(isSpare.equalsIgnoreCase("0")){
							masterForm.setIsSpare("No");
						
							
						}
						masterForm.setMoc(rs.getString("moc"));
						masterForm.setRating(rs.getString("rating"));
						masterForm.setRange(rs.getString("range"));
						String isNewEquipment=rs.getString("IS_NEW_Equipment");
						if(isNewEquipment.equalsIgnoreCase("1"))
						{
							masterForm.setIsNewEquipment("Yes");
							
						}
						if(isNewEquipment.equalsIgnoreCase("0"))
						{
							masterForm.setIsNewEquipment("No");
							
						}
						String isNewfurniturt=rs.getString("IS_NEW_Furniture");
						if(isNewfurniturt.equalsIgnoreCase("1"))
						{
							masterForm.setIsItNewFurniture("Yes");
							
						}
						if(isNewfurniturt.equalsIgnoreCase("0"))
						{
							masterForm.setIsItNewFurniture("No");
							
						}
						if(isNewfurniturt.equalsIgnoreCase("2"))
						{
							masterForm.setIsItNewFurniture("N/A");
							
						}
						String isNewfacility=rs.getString("IS_NEW_Facility");
						if(isNewfacility.equalsIgnoreCase("1"))
						{
							masterForm.setIsItFacility("Yes");
							
						}
						if(isNewfacility.equalsIgnoreCase("0"))
						{
							masterForm.setIsItFacility("No");
							
						}
						if(isNewfacility.equalsIgnoreCase("2"))
						{
							masterForm.setIsItFacility("N/A");
							
						}
						String issparey=rs.getString("IS_Spare_required");
						if(issparey.equalsIgnoreCase("1"))
						{
							masterForm.setIsSpareNewEquipment("Yes");
							
						}
						if(issparey.equalsIgnoreCase("0"))
						{
							masterForm.setIsSpareNewEquipment("No");
							
						}	
						if(issparey.equalsIgnoreCase("2"))
						{
							masterForm.setIsSpareNewEquipment("N/A");
							
						}	
					
						masterForm.setPrNumber(rs.getString("PR_NUMBER"));
						masterForm.setPoNumber(rs.getString("PO_NUMBER"));
						masterForm.setUtilizingDept(rs.getString("DPTSTXT"));
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						masterForm.setDimension(rs.getString("DIMENSION"));
						masterForm.setPackSize(rs.getString("PACK_SIZE"));
						masterForm.setEquipIntendedFor(rs.getString("equip_Intended"));
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
					
						planDetails.add(masterForm);
						request.setAttribute("plandetails", planDetails);
						forwardType="planmaterial";


					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int  checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				try {
					while(rsApprDetails.next())
					{
						
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String actualapprover="";
						boolean data=false;
						
						String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						if(rsAppInfo.next())
						{
							actualapprover=rsAppInfo.getString("Actual_Approver");
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
								data=true;
							}
							if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
							{
								apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
								data=true;
							}
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							String approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments==null || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						String pernr="";

						if(data==true)
										{
											pernr=actualapprover;
										}
										else
										{
											pernr=rsApprDetails.getString("Approver_Id");
										}						
								
								
							String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
							ResultSet rsname=ad.selectQuery(name);
							if(rsname.next())
							{
								apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
							}
						listApprers.add(apprvers);
						request.setAttribute("approverDetails", listApprers);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkStatus==0)
				{

					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;
							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
									String approveDate=	rsAppInfo.getString("approved_date");
									
									String a[]=approveDate.split(" ");
									approveDate=a[0];
									String b[]=approveDate.split("-");
									approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				forwardType="planmaterial";
				}
			if(matType.equalsIgnoreCase("PPC"))
			{
		 		LinkedList proDetails=new LinkedList();

		 		
		 		ApprovalsForm masterForm=new ApprovalsForm();
		 		
		 
		 		String matGroup="";
		 		 String location="";
		 		
		 		
		 		String getFinishedProduct="select mat.BRAND_ID,mat.REQUEST_NO,mat.Type,loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.REQUEST_DATE,loc.LOCNAME,stLoc.STORAGE_LOCATION_NAME," +
		 		"mat.MATERIAL_SHORT_NAME, mat.MATERIAL_LONG_NAME,matGroup.STXT,uom.LTXT,pur.PURCHASE_GROUP_DESC, div.DIV_DESC," +
		 		"mat.is_asset,mat.PURPOSE_ID,mat.IS_SAS_FORM_AVAILABLE,mat.APPROXIMATE_VALUE, val.VALUATION_DESC,mat.DETAILED_JUSTIFICATION," +
		 		"mat.DETAILED_SPECIFICATION,SAP_CODE_NO,SAP_CODE_EXISTS,SAP_CREATION_DATE,SAP_CREATED_BY from material_code_request as mat,Location as loc,STORAGE_LOCATION as stLoc,MATERIAL_GROUP as " +
		 		"matGroup , UNIT_MESUREMENT AS uom ,PURCHASE_GROUP as pur,DIVISION as div,VALUATION_CLASS as val where REQUEST_NO='"+reqId+"' and " +
		 		"loc.LOCID=mat.LOCATION_ID and stLoc.STORAGE_LOCATION_ID=mat.STORAGE_LOCATION_ID and matGroup.MATERIAL_GROUP_ID=mat.MATERIAL_GROUP_ID " +
		 		"and uom.UNIT_OF_MEAS_ID=mat.UNIT_OF_MEAS_ID and pur.PURCHASE_GROUP_ID=mat.PURCHASE_GROUP_ID and div.DIV_CODE=mat.DIVISION_ID  " +
		 		"and val.VALUATION_ID=mat.VALUATION_CLASS and Type='PPC'"; 
		 				
		 		ResultSet rs=ad.selectQuery(getFinishedProduct);
		 		
		 	
		 			try {
						if(rs.next())
{
							String  str=rs.getString("STORAGE_LOCATION_NAME");
							if(str.equalsIgnoreCase("Printed Material")||str.equalsIgnoreCase("General Material")||str.equalsIgnoreCase("Complement store"))
							{	
								masterForm.setRequestNo(reqId);
								masterForm.setRequestNumber(reqId);
						matType=rs.getString("Type");
						matGroup=rs.getString("MATERIAL_GROUP_ID");
						location=rs.getString("LOCATION_CODE");

						String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						masterForm.setRequestDate(reqDate);
						masterForm.setLocationId(rs.getString("LOCNAME"));
						
						masterForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_NAME"));
						masterForm.setBrandID(rs.getString("BRAND_ID"));
						masterForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
						masterForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						masterForm.setMaterialGroupId(rs.getString("STXT"));
						masterForm.setUnitOfMeasId(rs.getString("LTXT"));
						masterForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_DESC"));
						masterForm.setDivisionId(rs.getString("DIV_DESC"));
						String isAsset=rs.getString("is_asset");
						if(isAsset.equalsIgnoreCase("1"))
						{
							masterForm.setIsAsset("YES");
						}
						if(isAsset.equalsIgnoreCase("0")){
							masterForm.setIsAsset("NO");
						}
						masterForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						masterForm.setValuationClass(rs.getString("VALUATION_DESC"));
						masterForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
						masterForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
						
						String purposeId=rs.getString("PURPOSE_ID");
						if(purposeId.equalsIgnoreCase("1"))
						{
							masterForm.setPurposeID("Gift & Compliments");
							
						}
						if(purposeId.equalsIgnoreCase("2"))
						{
							masterForm.setPurposeID("Propaganda & Promotional (KUDLU DEPOT)");
							
						}
						if(purposeId.equalsIgnoreCase("3"))
						{
							masterForm.setPurposeID("Product Launch Exp");
							
						}
						if(purposeId.equalsIgnoreCase("4"))
						{
							masterForm.setPurposeID("Sales Promotional (SAS)");
							
						}
						if(purposeId.equalsIgnoreCase("5"))
						{
							masterForm.setPurposeID("Visual Ads,Literature");
							
						}
						
						if(purposeId.equalsIgnoreCase("6"))
						{
							masterForm.setPurposeID("Conference,National & Regional");
							
						}
						if(purposeId.equalsIgnoreCase("7"))
						{
							masterForm.setPurposeID("Incentive to Field Staff");
							
						}
						if(purposeId.equalsIgnoreCase("8"))
						{
							masterForm.setPurposeID("Incentive to Stockist/Chemist");
							
						}
						if(purposeId.equalsIgnoreCase("9"))
						{
							masterForm.setPurposeID("Travelling Lodging & Boarding Exp");
							
						}
						
   
						String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
						
						if(isSAS.equalsIgnoreCase("1"))
						{
							masterForm.setIsSASFormAvailable("YES");
						}
						if(isSAS.equalsIgnoreCase("0")){
							masterForm.setIsSASFormAvailable("NO");
	
}
						
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							masterForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								masterForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								masterForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							masterForm.setSapCreationDate(createDate);
							masterForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
						proDetails.add(masterForm);
	
						request.setAttribute("prodetails", proDetails);
						forwardType="promaterial";
   
	
    }}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 			int  checkStatus=0;
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					try {
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String actualapprover="";
							boolean data=false;
							
							String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							if(rsAppInfo.next())
							{
								actualapprover=rsAppInfo.getString("Actual_Approver");
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
									data=true;
								}
								if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
								{
									apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
									data=true;
								}
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								String approveStatus=rsAppInfo.getString("Req_Status");
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
								{
								apprvers.setDate(rsAppInfo.getString("approved_date"));
								}else{
									apprvers.setDate(rsAppInfo.getString("rejected_date"));
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments==null || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							String pernr="";

							if(data==true)
											{
												pernr=actualapprover;
											}
											else
											{
												pernr=rsApprDetails.getString("Approver_Id");
											}						
									
									
								String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
								ResultSet rsname=ad.selectQuery(name);
								if(rsname.next())
								{
									apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
								}
							listApprers.add(apprvers);
							request.setAttribute("approverDetails", listApprers);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(checkStatus==0)
					{

						getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
						rsApprDetails=ad.selectQuery(getApprDetails);
						try {
							while(rsApprDetails.next())
							{
								checkStatus=1;
								ApprovalsForm apprvers=new ApprovalsForm();
								apprvers.setPriority(rsApprDetails.getString("Priority"));
								apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
								String empCode=rsApprDetails.getString("Approver_Id");
								String actualapprover="";
								boolean data=false;
								
								String recordStatus="select mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
								"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
								"mast.Material_Type='"+matType+"' AND Material_Group=''  and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
								"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,mast.Parllel_Approver_1,mast.Parllel_Approver_2,all_r.Actual_Approver,emp.EMP_FULLNAME,all_r.Req_Status," +
								"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
								ResultSet rsAppInfo=ad.selectQuery(recordStatus);
								while(rsAppInfo.next())
								{
									actualapprover=rsAppInfo.getString("Actual_Approver");
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Approver_Id")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Approver_Id"));
										data=true;
									}
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_1")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_1"));
										data=true;
									}
									if(actualapprover.equalsIgnoreCase(rsAppInfo.getString("Parllel_Approver_2")))
									{
										apprvers.setEmployeeCode(rsAppInfo.getString("Parllel_Approver_2"));
										data=true;
									}
									apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
									String approveStatus=rsAppInfo.getString("Req_Status");
									if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Completed"))
									{
										String approveDate=	rsAppInfo.getString("approved_date");
										
										String a[]=approveDate.split(" ");
										approveDate=a[0];
										String b[]=approveDate.split("-");
										approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
										apprvers.setDate(approveDate);
									}else{
										String rejectDate=	rsAppInfo.getString("rejected_date");
										
										String a[]=rejectDate.split(" ");
										rejectDate=a[0];
										String b[]=rejectDate.split("-");
										rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
										apprvers.setDate(rejectDate);
									}
									String comments=rsAppInfo.getString("Comments");
									if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
									{
										apprvers.setComments("");
									}else{
										apprvers.setComments(rsAppInfo.getString("Comments"));
									}
								}
								String pernr="";

								if(data==true)
												{
													pernr=actualapprover;
												}
												else
												{
													pernr=rsApprDetails.getString("Approver_Id");
												}						
										
										
									String name="select EMP_FULLNAME from emp_official_info where PERNR='"+pernr+"'";
									ResultSet rsname=ad.selectQuery(name);
									if(rsname.next())
									{
										apprvers.setEmployeeName(rsname.getString("EMP_FULLNAME"));
									}
								listApprers.add(apprvers);
								request.setAttribute("approverDetails", listApprers);
}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
		 			forwardType="promaterial";
		 		}
		
		
		
	
		return mapping.findForward(forwardType);
		
	}

	public ActionForward submitAllMaterilaCodeRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	
	{

		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		String ch[]= masterForm.getGetReqno();
		
	
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		try{
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("id"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setMaterTypeIDList(materTypeIDList);
		masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		for(int j=0;j<ch.length;j++)
		{
			try{
				int i=0;
				HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				String requestNo=ch[j];
				String matType="";
				String loctID="";
				
				
				String approvermail="";
				String pendingApprovers="";
				
				String getMaterialDetails="select type,loc.LOCATION_CODE from material_code_request as mat,Location as loc where REQUEST_NO='"+requestNo+"' and loc.LOCID=mat.LOCATION_ID";
				ResultSet rsMatDetails=ad.selectQuery(getMaterialDetails);
				while(rsMatDetails.next())
				{
					matType=rsMatDetails.getString("type");
					loctID=rsMatDetails.getString("LOCATION_CODE");
				}
				
					Date dNow = new Date( );
					SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
					String dateNow = ft.format(dNow);
				 	String pApprover="";
				 	String parllelAppr1="";
				 	String parllelAppr2="";
		    		String matGroup="";
		    		String produtType="";
				 	String getMatGroup="select MATERIAL_GROUP_ID,DOMESTIC_OR_EXPORTS from material_code_request where REQUEST_NO='"+requestNo+"' and Type='"+matType+"'";
				 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
				 	while(rsMatGrup.next()){
				 		matGroup=rsMatGrup.getString("MATERIAL_GROUP_ID");
				 		if(matType.equals("FG")||matType.equals("HAWA"))
				 			matGroup=rsMatGrup.getString("DOMESTIC_OR_EXPORTS");
				 	}
				 	if(matType.equals("FG")||matType.equals("HAWA")){
				 		if(matGroup.equals("D"))
				 			matGroup="Domestic";
				 		if(matGroup.equals("E"))
				 			matGroup="Export";
				 		if(matGroup.equals("V"))
				 			matGroup="V";
				 	}
				 	int checkApprover=0;
				 	String pendingApprs="";
				 	String getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='"+matType+"' and app.Material_Group='"+matGroup+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
		    			 getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='"+matType+"' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
					saveRecReq = saveRecReq + "'"+requestNo+"','Material Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getEmployeeNo()+"','','"+matType+"')";
					int ij=ad.SqlExecuteUpdate(saveRecReq);
					if(!(parllelAppr1.equalsIgnoreCase(""))){
						
						String a[]=parllelAppr1.split(",");
						parllelAppr1=a[0];
					 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Material Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getEmployeeNo()+"','','"+matType+"')";
					 ad.SqlExecuteUpdate(sendRecParllelAppr1);
					}
					if(!(parllelAppr2.equalsIgnoreCase(""))){
						String a[]=parllelAppr2.split(",");
						parllelAppr2=a[0];
						 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
						 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Material Master','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getEmployeeNo()+"','','"+matType+"')";
						 ad.SqlExecuteUpdate(sendRecParllelAppr2);
					}
					
					if(ij>0){
						masterForm.setSendMessage("Request  Submitted for approval successfully.");
						String updateStatus="update material_code_request set Approve_Type='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+requestNo+"'";
						ad.SqlExecuteUpdate(updateStatus);
						
					//send mail to priority 1 approvers
						EMailer email = new EMailer();
					i = email.sendMailToApprover(request, approvermail,requestNo, "Material Code Request");
					 email.sendMailToApprover(request, approvermail,requestNo, "Material Code Request");
						request.setAttribute("sendMessage", "sendMessage");
					}else{
						masterForm.setMessage2("Error while submiting approval...");
					}
		    		}else{
		    			masterForm.setMessage2("No Approvers are assigned.Please Contact to Admin");
		    		}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			
		}
		displayInProcessMaterialList(mapping, form, request, response);
		return mapping.findForward("materialcodeList");
	}
	public ActionForward sendMailToApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
	try{
		int i=0;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String requestNo=request.getParameter("ReqestNo");
		String matType=request.getParameter("matType");
		String loctID=request.getParameter("LoctID");
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
		
		String approvermail="";
		String pendingApprovers="";
		
		String getMaterialDetails="select type from material_code_request where REQUEST_NO='"+requestNo+"'";
		ResultSet rsMatDetails=ad.selectQuery(getMaterialDetails);
		while(rsMatDetails.next())
		{
			matType=rsMatDetails.getString("type");
			
		}
		
		/*EMailer email = new EMailer();
			i = email.sendMailToApprover(request, approvermail,requestNo,"Material Code Request");
			if(i>0){
				
				masterForm.setMessage("Request No"+requestNo+". Submitted for approval successully.");
				String updateStatus="update material_code_request set Approve_Type='Pending' where REQUEST_NO='"+requestNo+"'";
				ad.SqlExecuteUpdate(updateStatus);
				
			}else{
				masterForm.setMessage("Error while submiting approval...");
			}*/
			Date dNow = new Date( );
			SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
			String dateNow = ft.format(dNow);
		 	String pApprover="";
		 	String parllelAppr1="";
		 	String parllelAppr2="";
    		String matGroup="";
    		
		 	String getMatGroup="select MATERIAL_GROUP_ID,DOMESTIC_OR_EXPORTS from material_code_request where REQUEST_NO='"+requestNo+"' and Type='"+matType+"'";
		 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
		 	while(rsMatGrup.next()){
		 		matGroup=rsMatGrup.getString("MATERIAL_GROUP_ID");
		 		if(matType.equals("FG")||matType.equals("HAWA"))
		 			matGroup=rsMatGrup.getString("DOMESTIC_OR_EXPORTS");
		 	}
			if(matType.equals("FG")||matType.equals("HAWA")){
		 		if(matGroup.equals("D"))
		 			matGroup="Domestic";
		 		if(matGroup.equals("E"))
		 			matGroup="Export";
		 		if(matGroup.equals("V"))
		 			matGroup="V";
		 	}
		 	int checkApprover=0;
		 	String pendingApprs="";
		 	String getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='"+matType+"' and app.Material_Group='"+matGroup+"' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
    			 getApprovers="select emp.EMP_FULLNAME,app.Approver_Id,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='"+matType+"' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
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
			saveRecReq = saveRecReq + "'"+requestNo+"','Material Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getId()+"','','"+matType+"')";
			int ij=ad.SqlExecuteUpdate(saveRecReq);
			if(!(parllelAppr1.equalsIgnoreCase(""))){
				
				String a[]=parllelAppr1.split(",");
				parllelAppr1=a[0];
			 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
			 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Material Master','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getId()+"','','"+matType+"')";
			 ad.SqlExecuteUpdate(sendRecParllelAppr1);
			}
			if(!(parllelAppr2.equalsIgnoreCase(""))){
				String a[]=parllelAppr2.split(",");
				parllelAppr2=a[0];
				 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Material Master','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getId()+"','','"+matType+"')";
				 ad.SqlExecuteUpdate(sendRecParllelAppr2);
			}
			if(ij>0){
				masterForm.setSendMessage("Request No "+requestNo+". Submitted for approval successully.");
				String updateStatus="update material_code_request set Approve_Type='Submited',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+requestNo+"'";
				ad.SqlExecuteUpdate(updateStatus);
				request.setAttribute("sendMessage", "sendMessage");
				
				EMailer email = new EMailer();
				 email.sendMailToApprover(request, approvermail,requestNo, "Material Code Request");
				
			}else{
				masterForm.setMessage2("Error while submiting approval...");
			}
    		}else{
    			
    			masterForm.setMessage2("No approvers are assigned for submiting this request.");
    		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	curentRecord(mapping, masterForm, request, response);
	return mapping.findForward("materialcodeList");
	}
	
	public ActionForward firstMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		int totalRecords=masterForm.getTotalRecords();//21
		int startRecord=masterForm.getStartRecord();//11
		int endRecord=masterForm.getEndRecord();//20
		  LinkedList listOfMaterialCode=new LinkedList();
		ArrayList locationLabelList=new ArrayList();
		 HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
			String userPlantID=user.getPlantId();
			int userID=user.getId();
		try{
			
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
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
		
			
			
		  java.util.List set1 = new java.util.ArrayList();
		  ResultSet rs3=ad.selectQuery("select * from Material_ColumnHeader  where status='1' order by Order_List,status ");
		  while(rs3.next()) {
			      java.util.Map email = new java.util.HashMap();
			      
				  email.put("property", rs3.getString("column_property"));
				  email.put("title",  rs3.getString("column_name"));
				  set1.add(email);
		}
		  
		  ResultSet rs1=ad.selectQuery("select * from Material_ColumnHeader    order by status,Order_List");
			LinkedList a2=new LinkedList();
			  while (rs1.next()) {
				  java.util.Map email = new java.util.HashMap();
				  email.put("property", rs1.getString("column_property"));
				  email.put("title",  rs1.getString("column_name"));
				  a2.add(email);
			}
			request.setAttribute("collist", a2);
		  request.setAttribute("collist1", set1);
		  
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		try{
	
		  
		  if(totalRecords>10){
		  startRecord=1;
		  endRecord=10;
		  masterForm.setTotalRecords(totalRecords);
		  masterForm.setStartRecord(startRecord);
		  masterForm.setEndRecord(10);
		  }
		  else{
			  startRecord=1;
			  masterForm.setTotalRecords(totalRecords);
			  masterForm.setStartRecord(startRecord);
			  masterForm.setEndRecord(totalRecords);  
		  }
		  String From=masterForm.getFromDate();
			if(From!=null){
			if(!From.equalsIgnoreCase("")){
			  String b[]=From.split("/");
			  From=b[2]+"-"+b[1]+"-"+b[0];
			}
			}
			//String To=codeRequestForm.getToDate();
			String To=masterForm.getToDate();
			if(To!=null){
			if(!To.equalsIgnoreCase("")){
			  String b1[]=To.split("/");
			  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
			}
			}
			String appStatus=masterForm.getApproveStatus();
		     String Location=masterForm.getLocationId();
		     String Materialcode=masterForm.getMaterialCodeLists();
		 
		  
		  
			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO desc) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
			 "m.URL,L.location_code,m.REPORT_URL,mType.M_DESC from material_code_request as m,Location as l ,MATERIAL_TYPE as mType  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and m.CREATED_BY='"+user.getEmployeeNo()+"'  ";
			 		 
			 
			 if(!appStatus.equalsIgnoreCase("")){	
				 query=query+ " and m.Approve_Type='"+appStatus+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
					query=query+ " and m.Type='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
					query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
			int count=0;
			ResultSet rsList=ad.selectQuery(query);
			while(rsList.next())
			{
				MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
				 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
				 String requestDate=rsList.getString("REQUEST_DATE");
					String req[]=requestDate.split(" ");
					requestDate=req[0];
					String a[]=requestDate.split("-");
					requestDate=a[2]+"/"+a[1]+"/"+a[0];
					materialForm.setRequestDate(requestDate);
				materialForm.setLocationId(rsList.getString("location_code"));
				materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
				materialForm.setApproveType(rsList.getString("Approve_Type"));
				materialForm.setRequiredRequestNumber("");
				materialForm.setUrl(rsList.getString("URL"));
				materialForm.setmType(rsList.getString("Type")+" - "+rsList.getString("M_DESC"));
				materialForm.setReqMatType(rsList.getString("Type"));
				materialForm.setReportUrl(rsList.getString("REPORT_URL"));
				listOfMaterialCode.add(materialForm);
				count++;
				if(count==10)
				{
					break;
				}
			}
			
			request.setAttribute("listOfMaterials", listOfMaterialCode);
		
		
		}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			if(totalRecords>10)
			{
				request.setAttribute("nextButton", "nextButton");
			}
		
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("materialCodeList","materialCodeList");
				
			return mapping.findForward("materialcodeList");
	
	
	}
	
	
	
	public ActionForward lastMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		  LinkedList listOfMaterialCode=new LinkedList();
		ArrayList locationLabelList=new ArrayList();
		 HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
			String userPlantID=user.getPlantId();
			int userID=user.getId();
		try{
			
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
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
			
			
		  java.util.List set1 = new java.util.ArrayList();
		  ResultSet rs3=ad.selectQuery("select * from Material_ColumnHeader  where status='1' order by Order_List,status ");
		  while(rs3.next()) {
			      java.util.Map email = new java.util.HashMap();
			      
				  email.put("property", rs3.getString("column_property"));
				  email.put("title",  rs3.getString("column_name"));
				  set1.add(email);
		}
		  
		  ResultSet rs1=ad.selectQuery("select * from Material_ColumnHeader     order by status,Order_List");
			LinkedList a2=new LinkedList();
			  while (rs1.next()) {
				  java.util.Map email = new java.util.HashMap();
				  email.put("property", rs1.getString("column_property"));
				  email.put("title",  rs1.getString("column_name"));
				  a2.add(email);
			}
			request.setAttribute("collist", a2);
		  request.setAttribute("collist1", set1);
		  
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		try{
			String queryCount="";
		
		
		
		  //String getTotalRecords="select * from material_code_request as m,Location as l where  m.LOCATION_ID='"+userPlantID+"'";
			int totalRecords=masterForm.getTotalRecords();//21
			int startRecord=masterForm.getStartRecord();//11
			int endRecord=totalRecords;//20
		  
		
		  startRecord=totalRecords-9;
		  masterForm.setTotalRecords(totalRecords);
		  masterForm.setStartRecord(startRecord);
		  masterForm.setEndRecord(totalRecords);

		  
		//	String getList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type,m.URL,l.location_code " +
		//			"From material_code_request as m,Location as l where m.LOCATION_ID=l.LOCID and m.Approve_Type='Pending' and m.LOCATION_ID='"+user.getPlantId()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+totalRecords+"'  ";
			
		  String From=masterForm.getFromDate();
			if(From!=null){
			if(!From.equalsIgnoreCase("")){
			  String b[]=From.split("/");
			  From=b[2]+"-"+b[1]+"-"+b[0];
			}
			}
			//String To=codeRequestForm.getToDate();
			String To=masterForm.getToDate();
			if(To!=null){
			if(!To.equalsIgnoreCase("")){
			  String b1[]=To.split("/");
			  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
			}
			}
			String appStatus=masterForm.getApproveStatus();
		     String Location=masterForm.getLocationId();
		     String Materialcode=masterForm.getMaterialCodeLists();
		 
		  
		  
			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO desc) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
			 "m.URL,L.location_code,m.REPORT_URL,mType.M_DESC from material_code_request as m,Location as l ,MATERIAL_TYPE as mType  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and m.CREATED_BY='"+user.getEmployeeNo()+"'  ";
			 		 
			 if(!Location.equals(""))
				 query=query+" and m.LOCATION_ID='"+Location+"'";
			 if(!appStatus.equalsIgnoreCase("")){	
				 query=query+ " and m.Approve_Type='"+appStatus+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
					query=query+ " and m.Type='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
					query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
	
			
			
			int count=0;
			ResultSet rsList=ad.selectQuery(query);
			while(rsList.next())
			{
				MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
				 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
				 String requestDate=rsList.getString("REQUEST_DATE");
					String req[]=requestDate.split(" ");
					requestDate=req[0];
					String a[]=requestDate.split("-");
					requestDate=a[2]+"/"+a[1]+"/"+a[0];
					materialForm.setRequestDate(requestDate);
				materialForm.setLocationId(rsList.getString("location_code"));
				materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
				materialForm.setApproveType(rsList.getString("Approve_Type"));
				materialForm.setRequiredRequestNumber("");
				materialForm.setUrl(rsList.getString("URL"));
				materialForm.setReportUrl(rsList.getString("REPORT_URL"));
				materialForm.setmType(rsList.getString("Type")+" - "+rsList.getString("M_DESC"));
				materialForm.setReqMatType(rsList.getString("Type"));
				listOfMaterialCode.add(materialForm);
				count++;
				if(count==10)
				{
					break;
				}
			}
			
			request.setAttribute("listOfMaterials", listOfMaterialCode);
		
		 }
		
			catch (Exception e) {
				e.printStackTrace();
			}
		
			request.setAttribute("disableNextButton", "disableNextButton");
			//request.setAttribute("disablePreviousButton", "disablePreviousButton");
			
			
			
			request.setAttribute("previousButton", "previousButton");
			if(listOfMaterialCode.size()<10)
			{
				
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("materialCodeList","materialCodeList");
				
			return mapping.findForward("materialcodeList");
	}
	
	
	
	public ActionForward previousMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		String materialCodeLists=masterForm.getMaterialCodeLists();
		 masterForm.setMaterialCodeLists(materialCodeLists);
		 String locationId=masterForm.getLocationId();
		 masterForm.setLocationId(locationId);
		 String approveStatus=masterForm.getApproveStatus();
		 masterForm.setApproveStatus(approveStatus);
		
		LinkedList listOfMaterialCode=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String userPlantID=user.getPlantId();
		int userID=user.getId();
		try{
		
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
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
			
			
		  java.util.List set1 = new java.util.ArrayList();
		  ResultSet rs3=ad.selectQuery("select * from Material_ColumnHeader   where status='1' order by Order_List,status ");
		  while(rs3.next()) {
			      java.util.Map email = new java.util.HashMap();
			      
				  email.put("property", rs3.getString("column_property"));
				  email.put("title",  rs3.getString("column_name"));
				  set1.add(email);
		}
		  ResultSet rs1=ad.selectQuery("select * from Material_ColumnHeader     order by status,Order_List");
			LinkedList a2=new LinkedList();
			  while (rs1.next()) {
				  java.util.Map email = new java.util.HashMap();
				  email.put("property", rs1.getString("column_property"));
				  email.put("title",  rs1.getString("column_name"));
				  a2.add(email);
			}
			request.setAttribute("collist", a2);
		  request.setAttribute("collist1", set1);
		  
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println("Start Record="+masterForm.getStartRecord());
		System.out.println("End record="+masterForm.getEndRecord());
		System.out.println("Total Record="+masterForm.getTotalRecords());
		
		
		int totalRecords=masterForm.getTotalRecords();//21
		int endRecord=masterForm.getStartRecord()-1;//20
		int startRecord=masterForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		try{
			
		  
		  //String getTotalRecords="select * from material_code_request as m,Location as l where  m.LOCATION_ID='"+userPlantID+"'";
		  String getTotalRecords="select count(*) from material_code_request where LOCATION_ID='"+user.getPlantId()+"' and m.CREATED_BY='"+user.getEmployeeNo()+"'";

		
		  masterForm.setTotalRecords(totalRecords);
		  masterForm.setStartRecord(1);
		  masterForm.setEndRecord(10);

		  
		  String From=masterForm.getFromDate();
			if(From!=null){
			if(!From.equalsIgnoreCase("")){
			  String b[]=From.split("/");
			  From=b[2]+"-"+b[1]+"-"+b[0];
			}
			}
			//String To=codeRequestForm.getToDate();
			String To=masterForm.getToDate();
			if(To!=null){
			if(!To.equalsIgnoreCase("")){
			  String b1[]=To.split("/");
			  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
			}
			}
			String appStatus=masterForm.getApproveStatus();
		     String Location=masterForm.getLocationId();
		     String Materialcode=masterForm.getMaterialCodeLists();
		 
		  
		  
			String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO desc) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
			 "m.URL,L.location_code,m.REPORT_URL,mType.M_DESC from material_code_request as m,Location as l ,MATERIAL_TYPE as mType  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and m.CREATED_BY='"+user.getEmployeeNo()+"'  ";
			 		 
			 if(!Location.equals(""))
				 query=query+" and m.LOCATION_ID='"+Location+"'";
			 if(!appStatus.equalsIgnoreCase("")){	
				 query=query+ " and m.Approve_Type='"+appStatus+"'";
				}
				if(!Materialcode.equalsIgnoreCase("")){	
					query=query+ " and m.Type='"+Materialcode+"'";
				}
				if(!From.equalsIgnoreCase("")){	
					query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
				}
				query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
		//String getList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type,m.URL,l.location_code " +
		//	"From material_code_request as m,Location as l where m.LOCATION_ID=l.LOCID and m.Approve_Type='Pending' ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";

			
			
			ResultSet rsList=ad.selectQuery(query);
			while(rsList.next())
			{
				MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
				 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
				 String requestDate=rsList.getString("REQUEST_DATE");
					String req[]=requestDate.split(" ");
					requestDate=req[0];
					String a[]=requestDate.split("-");
					requestDate=a[2]+"/"+a[1]+"/"+a[0];
					materialForm.setRequestDate(requestDate);
				materialForm.setLocationId(rsList.getString("location_code"));
				materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
				materialForm.setApproveType(rsList.getString("Approve_Type"));
				materialForm.setRequiredRequestNumber("");
				materialForm.setUrl(rsList.getString("URL"));
				materialForm.setmType(rsList.getString("Type")+" - "+rsList.getString("M_DESC"));
				materialForm.setReqMatType(rsList.getString("Type"));
				materialForm.setReportUrl(rsList.getString("REPORT_URL"));
				listOfMaterialCode.add(materialForm);
				
			}
			
			request.setAttribute("listOfMaterials", listOfMaterialCode);
		
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			endRecord=endRecord;
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(startRecord);
			masterForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(listOfMaterialCode.size()<10)
			{
				masterForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
			
		 
	
		
			request.setAttribute("materialCodeList","materialCodeList");
			
		return mapping.findForward("materialcodeList");
		
	}
	
	public ActionForward nextMaterialRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			String userPlantID=user.getPlantId();
			int userID=user.getId();
		try{
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
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
		
		 java.util.List set1 = new java.util.ArrayList();
		  ResultSet rs3=ad.selectQuery("select * from Material_ColumnHeader  where status='1' order by Order_List,status ");
		  while(rs3.next()) {
			      java.util.Map email = new java.util.HashMap();
			      
				  email.put("property", rs3.getString("column_property"));
				  email.put("title",  rs3.getString("column_name"));
				  set1.add(email);
		}
		  ResultSet rs1=ad.selectQuery("select * from Material_ColumnHeader     order by status,Order_List");
			LinkedList a2=new LinkedList();
			  while (rs1.next()) {
				  java.util.Map email = new java.util.HashMap();
				  email.put("property", rs1.getString("column_property"));
				  email.put("title",  rs1.getString("column_name"));
				  a2.add(email);
			}
			request.setAttribute("collist", a2);
		  request.setAttribute("collist1", set1);
		
		
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Start Record="+masterForm.getStartRecord());
		System.out.println("End record="+masterForm.getEndRecord());
		System.out.println("Total Record="+masterForm.getTotalRecords());
		LinkedList listOfMaterialCode=new LinkedList();
		
		int totalRecords=masterForm.getTotalRecords();//21
		int startRecord=masterForm.getStartRecord();//11
		int endRecord=masterForm.getEndRecord();//20
		
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
			
			try{
				
				String From=masterForm.getFromDate();
				if(From!=null){
				if(!From.equalsIgnoreCase("")){
				  String b[]=From.split("/");
				  From=b[2]+"-"+b[1]+"-"+b[0];
				}
				}
				//String To=codeRequestForm.getToDate();
				String To=masterForm.getToDate();
				if(To!=null){
				if(!To.equalsIgnoreCase("")){
				  String b1[]=To.split("/");
				  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
				}
				}
				String appStatus=masterForm.getApproveStatus();
			     String Location=masterForm.getLocationId();
			     String Materialcode=masterForm.getMaterialCodeLists();
			 
			  
			  
				String query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO desc) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
				 "m.URL,L.location_code,m.REPORT_URL,mType.M_DESC from material_code_request as m,Location as l ,MATERIAL_TYPE as mType  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and m.CREATED_BY='"+user.getEmployeeNo()+"'  ";
		 		 
				 if(!Location.equals(""))
					 query=query+" and m.LOCATION_ID='"+Location+"'";
				 if(!appStatus.equalsIgnoreCase("")){	
					 query=query+ " and m.Approve_Type='"+appStatus+"'";
					}
					if(!Materialcode.equalsIgnoreCase("")){	
						query=query+ " and m.Type='"+Materialcode+"'";
					}
					if(!From.equalsIgnoreCase("")){	
						query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
					}
					query=query+ ")as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
			  
			  int count=0;
		ResultSet rsList=ad.selectQuery(query);
		while(rsList.next())
		{
			MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
			 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
			 String requestDate=rsList.getString("REQUEST_DATE");
				String req[]=requestDate.split(" ");
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				materialForm.setRequestDate(requestDate);
			materialForm.setLocationId(rsList.getString("location_code"));
			materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
			materialForm.setApproveType(rsList.getString("Approve_Type"));
			materialForm.setRequiredRequestNumber("");
			materialForm.setUrl(rsList.getString("URL"));
			materialForm.setmType(rsList.getString("Type")+" - "+rsList.getString("M_DESC"));
			materialForm.setReqMatType(rsList.getString("Type"));
			materialForm.setReportUrl(rsList.getString("REPORT_URL"));
			listOfMaterialCode.add(materialForm);
			count++;
			if(count==10)
			{
				break;
			}
		}
		
		System.out.println("list length="+listOfMaterialCode.size());
		
		
		if(listOfMaterialCode.size()!=0)
		{
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(startRecord);
			masterForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		}
		else
		{
			int start=startRecord;
			int end=startRecord;
			
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(start);
			masterForm.setEndRecord(end);
			
		}
	 if(listOfMaterialCode.size()<10)
	 {
		 masterForm.setTotalRecords(totalRecords);
		 masterForm.setStartRecord(startRecord);
		 masterForm.setEndRecord(startRecord+listOfMaterialCode.size()-1);
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
			request.setAttribute("previousButton", "previousButton"); 
		 
	 }
	 
	 if(endRecord==totalRecords)
	 {
		 request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
	 }
	 request.setAttribute("listOfMaterials", listOfMaterialCode);
		if(listOfMaterialCode.size()!=0)
		{
			
		}
	  
		
		request.setAttribute("previousButton", "previousButton");
		
		
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		 
		request.setAttribute("displayRecordNo", "displayRecordNo");
		
	
		request.setAttribute("materialCodeList","materialCodeList");
		return mapping.findForward("materialcodeList");
		
	}
	
	public ActionForward newMaterialForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		
		
		
		
		return mapping.findForward("newmaterialcode");
	}
	
	
	public ActionForward saveColums(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
	try{
	    
	
		
		
		HttpSession session=request.getSession();
		
		
		LinkedList a1=(LinkedList) session.getAttribute("Art");
		
		int noOfRecords=0;
	 	String[] t= request.getParameterValues("email2");
	 	java.util.List set1 = new java.util.ArrayList();
	 	if(t==null){
	 		
	 		String getSelectedColumns="Select * from Material_ColumnHeader where status='1'";
	 		ResultSet rsSelectedColumns=ad.selectQuery(getSelectedColumns);
	 		while(rsSelectedColumns.next())
	 		{
	 			java.util.Map email = new java.util.HashMap();
	 			
	 			email.put("property", rsSelectedColumns.getString("column_property"));
				email.put("title",  rsSelectedColumns.getString("Column_Name"));
				set1.add(email);
	 		}
	 	}else{
		 	String updatePropertyTable="update Material_ColumnHeader set status='2'";
			ad.SqlExecuteUpdate(updatePropertyTable);
		 set1 = new java.util.ArrayList();
		 	for(int i=0;i<t.length;i++){
		 		
		 		java.util.Map email = new java.util.HashMap();
		 		
		 		System.out.println("Getting a Property is *********"+t[i].split(",")[0]);
		 		String updateProperty="update Material_ColumnHeader set status='1' where column_property='"+t[i].split(",")[0]+"' ";
		 		ad.selectQuery(updateProperty);
		 		System.out.println("Getting a Title is *********"+ t[i].split(",")[1]);
	
		 		email.put("property", t[i].split(",")[0]);
				email.put("title",  t[i].split(",")[1]);
				set1.add(email);
		 		
		 	}
		 	
		 /*	String getSelectedColumns="Select * from Material_ColumnHeader where status='1'";
	 		ResultSet rsSelectedColumns=ad.selectQuery(getSelectedColumns);
	 		while(rsSelectedColumns.next())
	 		{
	 			java.util.Map email = new java.util.HashMap();
	 			
	 			email.put("property", rsSelectedColumns.getString("column_property"));
				email.put("title",  rsSelectedColumns.getString("Column_Name"));
				set1.add(email);
	 		}*/
		 	
		 	
	 	}
			ResultSet rs1=ad.selectQuery("select * from Material_ColumnHeader     order by status,Order_List");
			LinkedList a2=new LinkedList();
			  while (rs1.next()) {
				  java.util.Map email = new java.util.HashMap();
				  email.put("property", rs1.getString("column_property"));
				  email.put("title",  rs1.getString("column_name"));
				  a2.add(email);
			}
			request.setAttribute("collist", a2);
			request.setAttribute("collist1", set1);
			LinkedList listOfMaterialCode = new LinkedList();
			Iterator itr= a1.iterator();
			while(itr.hasNext()) {
				MaterialCodeRequestForm masterForm1=(MaterialCodeRequestForm)itr.next();
				
				System.out.println("Getting a Leave is "+masterForm1.getRequestDate());
				listOfMaterialCode.add(masterForm1);
			}
			
			System.out.println(listOfMaterialCode);
			 request.setAttribute("listOfMaterials", listOfMaterialCode);
			// displayMaterialList(mapping, form, request, response);
	}catch (Exception e) {
		e.printStackTrace();
	}
	request.setAttribute("nextButton", "nextButton");
	request.setAttribute("displayRecordNo", "displayRecordNo");
	request.setAttribute("previousButton", "previousButton");
	return mapping.findForward("materialcodeList");
	}
	
	public ActionForward displayInProcessMaterialList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
	try{
		LinkedList listOfMaterialCode=new LinkedList();
		
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			String userPlantID=user.getPlantId();
			int userID=user.getId();
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
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
			materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
		}
		masterForm.setMaterTypeIDList(materTypeIDList);
		masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		masterForm.setRequestNumber("");
		masterForm.setMaterialCodeLists("");
		masterForm.setApproveStatus("In Process");
		masterForm.setLocationId(userPlantID);
		
		String updatePropertyTable="update Material_ColumnHeader set status='1'";
		ad.SqlExecuteUpdate(updatePropertyTable);
	  java.util.List set1 = new java.util.ArrayList();
	  ResultSet rs3=ad.selectQuery("select * from Material_ColumnHeader  order by Order_List,status ");
	  while(rs3.next()) {
		      java.util.Map email = new java.util.HashMap();
		      
			  email.put("property", rs3.getString("column_property"));
			  email.put("title",  rs3.getString("column_name"));
			  set1.add(email);
	}
	  request.setAttribute("collist", set1);
	  request.setAttribute("collist1", set1);
	  
	  
	  int  totalRecords=0;
	  int  startRecord=0;
	  int  endRecord=0;
	  
	  String getTotalRecords="select count(*) from material_code_request  where  LOCATION_ID='"+userPlantID+"' and Approve_Type='Saved'  and CREATED_BY='"+user.getEmployeeNo()+"'";
	 // String getTotalRecords="select count(*) from material_code_request as m where  m.Approve_Type='Pending' and m.LOCATION_ID='"+user.getPlantId()+"'";
	  if(userID==1 ||userID==2 )
	  {
		getTotalRecords="select count(*) from material_code_request  where  LOCATION_ID='"+userPlantID+"' ";
	  }
	  
	  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
	  while(rsTotalRecods.next())
	  {
		  totalRecords=rsTotalRecods.getInt(1);
	  }
	  
	  if(totalRecords>10)
	  {
	  masterForm.setTotalRecords(totalRecords);
	  startRecord=1;
	  endRecord=10;
	  masterForm.setStartRecord(1);
	  masterForm.setEndRecord(10);
	  request.setAttribute("displayRecordNo", "displayRecordNo");
	  request.setAttribute("nextButton", "nextButton");
	  }else
	  {
		  startRecord=1;
		  endRecord=totalRecords;
		  masterForm.setTotalRecords(totalRecords);
		  masterForm.setStartRecord(1);
		  masterForm.setEndRecord(totalRecords); 
	  }
	  
		String getList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.ID) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type,m.URL,l.location_code,m.REPORT_URL,mType.M_DESC " +
				"From material_code_request as m,Location as l,MATERIAL_TYPE as mType where m.Type=mType.MATERIAL_GROUP_ID and m.LOCATION_ID=l.LOCID and m.Approve_Type='Saved' and m.LOCATION_ID='"+user.getPlantId()+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '1' and '10'  ";
		
		  
		//String getList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type,m.URL,l.location_code " +
		//"From material_code_request as m,Location as l where m.LOCATION_ID=l.LOCID and m.Approve_Type='Pending' ) as sub Where  sub.RowNum between 1 and 10  ";

		
		int count=0;
		ResultSet rsList=ad.selectQuery(getList);
		while(rsList.next())
		{
			MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
			 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
			 String requestDate=rsList.getString("REQUEST_DATE");
				String req[]=requestDate.split(" ");
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				materialForm.setRequestDate(requestDate);
			materialForm.setLocationId(rsList.getString("location_code"));
			materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
			materialForm.setApproveType(rsList.getString("Approve_Type"));
			materialForm.setRequiredRequestNumber("");
			materialForm.setUrl(rsList.getString("URL"));
			materialForm.setmType(rsList.getString("Type")+" - "+rsList.getString("M_DESC"));
			materialForm.setReportUrl(rsList.getString("REPORT_URL"));
			listOfMaterialCode.add(materialForm);
			
			count++;
			if(count==10)
			{
				break;
			}
		}
		if(!rsList.previous()){
			masterForm.setMessage("No Record Found..");
	    request.setAttribute("noRecords", "noRecords");
	    }
		request.setAttribute("listOfMaterials", listOfMaterialCode);
	
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		
			request.setAttribute("materialCodeList","materialCodeList");
			
		return mapping.findForward("materialcodeList");
	}
	
	
	public ActionForward displayMaterialList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		MaterialCodeRequestForm masterForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
	try{
		LinkedList listOfMaterialCode=new LinkedList();
		String availableLoc=user.getAvailableLocations();
		if(availableLoc.equalsIgnoreCase(""))
			availableLoc="0";
		ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		while(rs11.next()) {
			locationList.add(rs11.getString("LOCID"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		if(locationList.size()==0){
			locationList.add("");
			locationLabelList.add("");
		}
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		String userPlantID=user.getPlantId();
		int userID=user.getId();
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
		}
		masterForm.setMaterTypeIDList(materTypeIDList);
		masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		masterForm.setRequestNumber("");
		masterForm.setMaterialCodeLists("");
		masterForm.setApproveStatus("In Process");
		masterForm.setLocationId(userPlantID);
		
		String updatePropertyTable="update Material_ColumnHeader set status='1'";
		ad.SqlExecuteUpdate(updatePropertyTable);
	  java.util.List set1 = new java.util.ArrayList();
	  ResultSet rs3=ad.selectQuery("select * from Material_ColumnHeader  order by Order_List,status ");
	  while(rs3.next()) {
		      java.util.Map email = new java.util.HashMap();
		      
			  email.put("property", rs3.getString("column_property"));
			  email.put("title",  rs3.getString("column_name"));
			  set1.add(email);
	}
	  request.setAttribute("collist", set1);
	  request.setAttribute("collist1", set1);
	  
	  
	  int  totalRecords=0;
	  int  startRecord=0;
	  int  endRecord=0;
	  
	  String getTotalRecords="select count(*) from material_code_request  where  LOCATION_ID='"+userPlantID+"' and Approve_Type='In Process'  and CREATED_BY='"+user.getEmployeeNo()+"'";
	 // String getTotalRecords="select count(*) from material_code_request as m where  m.Approve_Type='Pending' and m.LOCATION_ID='"+user.getPlantId()+"'";
	  if(userID==1 ||userID==2 )
	  {
		getTotalRecords="select count(*) from material_code_request  where  LOCATION_ID='"+userPlantID+"' ";
	  }
	  
	  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
	  while(rsTotalRecods.next())
	  {
		  totalRecords=rsTotalRecods.getInt(1);
	  }
	  
	  if(totalRecords>10)
	  {
	  masterForm.setTotalRecords(totalRecords);
	  startRecord=1;
	  endRecord=10;
	  masterForm.setStartRecord(1);
	  masterForm.setEndRecord(10);
	  request.setAttribute("displayRecordNo", "displayRecordNo");
	  request.setAttribute("nextButton", "nextButton");
	  }else
	  {
		  startRecord=1;
		  endRecord=totalRecords;
		  masterForm.setTotalRecords(totalRecords);
		  masterForm.setStartRecord(1);
		  masterForm.setEndRecord(totalRecords); 
	  }
	  
		String getList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.REQUEST_NO desc) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type,m.URL,l.location_code,m.REPORT_URL,mType.M_DESC " +
				"From material_code_request as m,Location as l,MATERIAL_TYPE as mType where m.Type=mType.MATERIAL_GROUP_ID and m.LOCATION_ID=l.LOCID and m.Approve_Type='In Process' and m.LOCATION_ID='"+user.getPlantId()+"' and CREATED_BY='"+user.getEmployeeNo()+"') as sub Where  sub.RowNum between '1' and '10'  ";
		
		  
		//String getList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type,m.URL,l.location_code " +
		//"From material_code_request as m,Location as l where m.LOCATION_ID=l.LOCID and m.Approve_Type='Pending' ) as sub Where  sub.RowNum between 1 and 10  ";

		
		int count=0;
		ResultSet rsList=ad.selectQuery(getList);
		while(rsList.next())
		{
			MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
			 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
			 String requestDate=rsList.getString("REQUEST_DATE");
				String req[]=requestDate.split(" ");
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				materialForm.setRequestDate(requestDate);
			materialForm.setLocationId(rsList.getString("location_code"));
			materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
			materialForm.setApproveType(rsList.getString("Approve_Type"));
			materialForm.setRequiredRequestNumber("");
			materialForm.setUrl(rsList.getString("URL"));
			materialForm.setmType(rsList.getString("Type")+" - "+rsList.getString("M_DESC"));
			
			materialForm.setReqMatType(rsList.getString("Type"));
			materialForm.setReportUrl(rsList.getString("REPORT_URL"));
			listOfMaterialCode.add(materialForm);
			
			count++;
			if(count==10)
			{
				break;
			}
		}
		if(!rsList.previous()){
			masterForm.setMessage("No Record Found..");
	    request.setAttribute("noRecords", "noRecords");
	    }
		request.setAttribute("listOfMaterials", listOfMaterialCode);
	
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		
			request.setAttribute("materialCodeList","materialCodeList");
			
		return mapping.findForward("materialcodeList");
	}
	
	
	
	public ActionForward displayNewMaterialCodeSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm codeRequestForm=(MaterialCodeRequestForm)form;
		String type=request.getParameter("type");
		
		codeRequestForm.setMaterialCodeLists(type);
		request.setAttribute("newMaterialForm","newMaterialForm");
		return mapping.findForward("materialcodeList");
	}
	
	
	public ActionForward displayNewMaterialCodeRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm codeRequestForm=(MaterialCodeRequestForm)form;
		EssDao ad=new EssDao();
		try{
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("id"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
		}
		codeRequestForm.setMaterTypeIDList(materTypeIDList);
		codeRequestForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("newMaterialCodeRequest");
	}
	public ActionForward deleteMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm codeRequestForm=(MaterialCodeRequestForm)form;
		String ch[]= codeRequestForm.getChRequestNumber();
		EssDao ad=new EssDao();
		try{
		for(int j=0;j<ch.length;j++)
		{
		   //String deleteRecord="delete from material_code_request where REQUEST_NO='"+ch[j]+"'";
		   String changeStatus="update material_code_request set Approve_Type='deleted' where REQUEST_NO='"+ch[j]+"'";
		   int i=0;
		   i=ad.SqlExecuteUpdate(changeStatus);
		   
		   
		   if(i>=1)
		   {
			   codeRequestForm.setMessage("Material Record Successfully Deleted");
		   }else{
			   codeRequestForm.setMessage("Error.... When Deleting Material Record.");
		   }
		}
		}catch (Exception e) {
			codeRequestForm.setMessage("Error.... When Deleting Material Record.Please Select Atleast One Record");
			
		}
	   
		//return mapping.findForward("customerList");
		displayMaterialList(mapping, form, request, response);
		return mapping.findForward("materialcodeList");
	}
	public ActionForward searchMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeRequestForm codeRequestForm=(MaterialCodeRequestForm)form;
		//String ch[]= codeRequestForm.getChRequestNumber();
		EssDao ad=new EssDao();
		String query="";
	     String queryCount="";
		 LinkedList listOfMaterialCode=new LinkedList();
		try{
		//String updatePropertyTable="update Material_ColumnHeader set status='2'";
		//ad.SqlExecuteUpdate(updatePropertyTable);
	  java.util.List set1 = new java.util.ArrayList();
	  ResultSet rs3=ad.selectQuery("select * from Material_ColumnHeader where status='1' order by Order_List,status ");
	  while(rs3.next()) {
		      java.util.Map email = new java.util.HashMap();
		      
			  email.put("property", rs3.getString("column_property"));
			  email.put("title",  rs3.getString("column_name"));
			  set1.add(email);
	}
	  ResultSet rs1=ad.selectQuery("select * from Material_ColumnHeader     order by status,Order_List");
		LinkedList a2=new LinkedList();
		  while (rs1.next()) {
			  java.util.Map email = new java.util.HashMap();
			  email.put("property", rs1.getString("column_property"));
			  email.put("title",  rs1.getString("column_name"));
			  a2.add(email);
		}
		request.setAttribute("collist", a2);
	  request.setAttribute("collist1", set1);
	  
	  LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
		}
		codeRequestForm.setMaterTypeIDList(materTypeIDList);
		codeRequestForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		String reqnumber=codeRequestForm.getRequestNumber();
		String From=codeRequestForm.getFromDate();
		String To=codeRequestForm.getToDate();
		
		String appStatus=codeRequestForm.getApproveStatus();
		String Location=codeRequestForm.getLocationId();
	     String Materialcode=codeRequestForm.getMaterialCodeLists();
		if(From!=null){
		if(!From.equalsIgnoreCase("")){
		  String b[]=From.split("/");
		  From=b[2]+"-"+b[1]+"-"+b[0];
		}
		}
		//String To=codeRequestForm.getToDate();
	
		if(To!=null){
		if(!To.equalsIgnoreCase("")){
		  String b1[]=To.split("/");
		  To=b1[2]+"-"+b1[1]+"-"+b1[0];	
		}
		}
	     
	     
	     
	     int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
	     
	     /*if(Materialcode.equalsIgnoreCase("PROMOTIONAL")||Materialcode.equalsIgnoreCase("PRINTED")||Materialcode.equalsIgnoreCase("COMPLIMENTS")||Materialcode.equalsIgnoreCase("PPC"))
	     {
	    	 Materialcode="PPC";
	     }
	     if(Materialcode.equalsIgnoreCase("OSE")||Materialcode.equalsIgnoreCase("LC")||Materialcode.equalsIgnoreCase("ZCIV")||Materialcode.equalsIgnoreCase("ZCON"))
	     {
	    	 Materialcode=Materialcode;
	     }*/
	     
	     
	     HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			int userID=user.getId();
	     
  queryCount="select count(*) from material_code_request as m,Location as l where  m.LOCATION_ID=l.LOCID  and m.CREATED_BY='"+user.getEmployeeNo()+"'"; 
	
  	if(!Location.equals(""))
	  queryCount=queryCount+" and m.LOCATION_ID='"+Location+"'";
	if(!appStatus.equalsIgnoreCase("")){	
		queryCount=queryCount+ " and m.Approve_Type='"+appStatus+"'";
	}
	if(!Materialcode.equalsIgnoreCase("")){	
		queryCount=queryCount+ " and m.Type='"+Materialcode+"'";
	}
	if(!From.equalsIgnoreCase("")){	
		queryCount=queryCount+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
	}
	  
 System.out.println("query Count="+queryCount);	 
 int queryTotal=0;
 ResultSet rsQueryCount=ad.selectQuery(queryCount);
 while(rsQueryCount.next())
 {
	 queryTotal=rsQueryCount.getInt(1);
 }
 if(queryTotal>10)
 {
	 codeRequestForm.setTotalRecords(queryTotal);
 startRecord=1;
 endRecord=10;
 codeRequestForm.setStartRecord(1);
 codeRequestForm.setEndRecord(10);
 request.setAttribute("displayRecordNo", "displayRecordNo");
 request.setAttribute("nextButton", "nextButton");
 }else
 {
	  startRecord=1;
	  endRecord=totalRecords;
	  codeRequestForm.setTotalRecords(totalRecords);
	  codeRequestForm.setStartRecord(1);
	  codeRequestForm.setEndRecord(totalRecords); 
 }
 

	    	 
	    ////////////////////////////////////////////////////////////	
 startRecord=1;
 endRecord=10;
 
 
 
 query="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY m.ID) AS RowNum, m.REQUEST_NO,m.REQUEST_DATE,l.LOCNAME,m.MATERIAL_SHORT_NAME,m.Type,m.Approve_Type," +
 "m.URL,L.location_code,m.REPORT_URL,mType.M_DESC from material_code_request as m,Location as l ,MATERIAL_TYPE as mType  where m.Type=mType.MATERIAL_GROUP_ID  and  m.LOCATION_ID=l.LOCID and m.CREATED_BY='"+user.getEmployeeNo()+"'  ";
 		 
 if(!Location.equals(""))
	 query=query+" and m.LOCATION_ID='"+Location+"'";
 if(!appStatus.equalsIgnoreCase("")){	
	 query=query+ " and m.Approve_Type='"+appStatus+"'";
	}
	if(!Materialcode.equalsIgnoreCase("")){	
		query=query+ " and m.Type='"+Materialcode+"'";
	}
	if(!From.equalsIgnoreCase("")){	
		query=query+ " and m.REQUEST_DATE between '"+From+"' and '"+To+"'";
	}
	query=query+ ")as sub Where  sub.RowNum between '"+(queryTotal-9)+"' and '"+(queryTotal)+"' order by RowNum desc";
 
 	



	    ResultSet rsList=ad.selectQuery(query);
	    while(rsList.next()){
	    	MaterialCodeRequestForm materialForm=new MaterialCodeRequestForm();
			 materialForm.setRequestNumber(rsList.getString("REQUEST_NO"));
			 String requestDate=rsList.getString("REQUEST_DATE");
				String req[]=requestDate.split(" ");
				requestDate=req[0];
				String a[]=requestDate.split("-");
				requestDate=a[2]+"/"+a[1]+"/"+a[0];
				materialForm.setRequestDate(requestDate);
			materialForm.setLocationId(rsList.getString("location_code"));
			materialForm.setMaterialShortName(rsList.getString("MATERIAL_SHORT_NAME"));
			materialForm.setApproveType(rsList.getString("Approve_Type"));
			materialForm.setRequiredRequestNumber("");
			materialForm.setUrl(rsList.getString("URL"));
			materialForm.setReportUrl(rsList.getString("REPORT_URL"));
			materialForm.setmType(rsList.getString("Type")+" - "+rsList.getString("M_DESC"));
			materialForm.setReqMatType(rsList.getString("Type"));
			listOfMaterialCode.add(materialForm);
	    }
	    if(!rsList.previous()){
 	    	codeRequestForm.setMessage("No Record Found..");
	    request.setAttribute("noRecords", "noRecords");
	    }
	    
		}
	    catch(Exception e){
	    	e.printStackTrace();	    
	    }
	    try{
	    	HttpSession session=request.getSession();
	    	UserInfo user=(UserInfo)session.getAttribute("user");
	    	String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			if(locationList.size()==0){
				locationList.add("");
				locationLabelList.add("");
			}
	    
		codeRequestForm.setLocationIdList(locationList);
		codeRequestForm.setLocationLabelList(locationLabelList);
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    	System.out.println(e);
	    }
		request.setAttribute("disablePreviousButton", "disablePreviousButton");
	    request.setAttribute("listOfMaterials", listOfMaterialCode);
		return mapping.findForward("materialcodeList");
	}
	
	
}
