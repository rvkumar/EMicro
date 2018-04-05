package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.form.HolidayForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.utilities.UserInfo;

public class HolidayAction extends DispatchAction{
	EssDao ad=new EssDao();
	
	public ActionForward searchEmpHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		String requiredLocation=holidayForm.getRequiredLocation();
		int year=holidayForm.getYear();
		String requiredString=holidayForm.getRequiredString();
		requiredString=requiredString.trim();
		
		try
		{
			ArrayList list = null;
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			
			String getLocations="select * from Location";
			ResultSet rsGetLocation=ad.selectQuery(getLocations);
			while(rsGetLocation.next()){
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("Location_Code")+"-"+rsGetLocation.getString("LOCNAME"));
			}
			
			holidayForm.setLocationId(locationID);
			holidayForm.setLocationName(locationName);
			
			int userlocationID=Integer.parseInt(user.getPlantId());
			String getHolidays="select h.Year,loc.LOCNAME,loc.location_code,h.Holiday_Name,h.Holiday_Date,h.Day_Name from holidays as h,Location as loc where" +
					" h.Location=loc.LOCID  ";
			if(!requiredLocation.equalsIgnoreCase(""))
			{
				getHolidays=getHolidays+" and LOCID='"+requiredLocation+"'";
			}
			if(!requiredString.equalsIgnoreCase(""))
			{
				getHolidays=getHolidays+" and (Holiday_Date like '%"+requiredString+"%' or Day_Name like '%"+requiredString+"%' or LOCNAME like '%"+requiredString+"%' " +
						" or h.Holiday_Name like '%"+requiredString+"%')";
			}
			if(year>0)
			{
				getHolidays=getHolidays+" and  h.Year='"+year+"' order by Date";
			}
			ResultSet rs = ad.selectQuery(getHolidays);
			
			
			list = new ArrayList();
			HolidayForm holidayForms = null;
			while (rs.next()) {
				holidayForms = new HolidayForm();
              
               
				holidayForms.setLocation(rs.getString("location_code")+"-"+rs.getString("LOCNAME"));
				
				
				holidayForms.setHolidayName(rs.getString("Holiday_Name"));
				holidayForms.setDayName(rs.getString("Day_Name"));
				holidayForms.setHolidayDate(rs.getString("Holiday_Date"));
				
				list.add(holidayForms);
			}
			
			holidayForm.setYear(year);
			
			request.setAttribute("empSearchDetails", list);
			if(list.size()==0){
				request.setAttribute("no records", "no records");
			
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("empSearchHolidays");
	
		
	}
	
	
	public ActionForward searchHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayHolidays()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		String requiredLocation=holidayForm.getRequiredLocation();
		int year=holidayForm.getYear();
		String requiredString=holidayForm.getRequiredString();
		requiredString=requiredString.trim();
		
		try
		{
			ArrayList list = null;
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			
			String getLocations="select * from Location";
			ResultSet rsGetLocation=ad.selectQuery(getLocations);
			while(rsGetLocation.next()){
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("Location_Code")+"-"+rsGetLocation.getString("LOCNAME"));
			}
			
			holidayForm.setLocationId(locationID);
			holidayForm.setLocationName(locationName);
			
			int userlocationID=Integer.parseInt(user.getPlantId());
			String getHolidays="select h.Year,loc.LOCNAME,loc.location_code,h.Holiday_Name,h.Holiday_Date,h.Day_Name from holidays as h,Location as loc where" +
					" h.Location=loc.LOCID  ";
			if(!requiredLocation.equalsIgnoreCase(""))
			{
				getHolidays=getHolidays+" and LOCID='"+requiredLocation+"'";
			}
			if(!requiredString.equalsIgnoreCase(""))
			{
				getHolidays=getHolidays+" and (Holiday_Date like '%"+requiredString+"%' or Day_Name like '%"+requiredString+"%' or LOCNAME like '%"+requiredString+"%' " +
						" or h.Holiday_Name like '%"+requiredString+"%')";
			}
			if(year>0)
			{
				getHolidays=getHolidays+" and  h.Year='"+year+"' order by Date";
			}
			ResultSet rs = ad.selectQuery(getHolidays);
			
			
			list = new ArrayList();
			HolidayForm holidayForms = null;
			while (rs.next()) {
				holidayForms = new HolidayForm();
              
               
				holidayForms.setLocation(rs.getString("location_code")+"-"+rs.getString("LOCNAME"));
				
				
				holidayForms.setHolidayName(rs.getString("Holiday_Name"));
				holidayForms.setDayName(rs.getString("Day_Name"));
				holidayForms.setHolidayDate(rs.getString("Holiday_Date"));
				
				list.add(holidayForms);
			}
			
			holidayForm.setYear(year);
			
			if(list.size()>0)
			{
				request.setAttribute("listDetails", list);	
			}
			if(list.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayHolidays");
	}
	
	public ActionForward displayEmpHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayHolidays()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try
		{
			ArrayList list = null;
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			
			String getLocations="select * from Location";
			ResultSet rsGetLocation=ad.selectQuery(getLocations);
			while(rsGetLocation.next()){
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("location_code")+"-"+rsGetLocation.getString("LOCNAME"));
			}
			
			holidayForm.setLocationId(locationID);
			holidayForm.setLocationName(locationName);
			
			int userlocationID=Integer.parseInt(user.getPlantId());
			

			int year = Calendar.getInstance().get(Calendar.YEAR);
			
			
			String getHolidays="select h.Year,h.Holiday_Name,h.Day_Name,h.Holiday_Date,h.Day_Name,loc.LOCNAME,loc.location_code  from holidays as h,Location as loc " +
					"where h.Location='"+userlocationID+"' and h.Location=loc.LOCID  and h.Year='"+year+"' order by h.Date";
			
			ResultSet rs = ad.selectQuery(getHolidays);
			
			
			list = new ArrayList();
			HolidayForm holidayForms = null;
			while (rs.next()) {
				holidayForms = new HolidayForm();
               
				holidayForms.setLocation(rs.getString("location_code")+"-"+rs.getString("LOCNAME"));
				holidayForms.setHolidayName(rs.getString("Holiday_Name"));
				holidayForms.setDayName(rs.getString("Day_Name"));
				holidayForms.setHolidayDate(rs.getString("Holiday_Date"));
				
				
				list.add(holidayForms);
			}
			request.setAttribute("listDetails", list);
			
			holidayForm.setYear(year);
			holidayForm.setRequiredLocation("");
			holidayForm.setRequiredString("");
		
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	String result=(String)sesion.getAttribute("result");
	System.out.println("result="+result);
	if(result==null||result==" ")
	sesion.setAttribute("o"," ");
	
	
	if(request.getParameter("id")!=null){
	
	request.setAttribute("MenuIcon", request.getParameter("id"));
	}
	clear(mapping, form, request, response);
		return mapping.findForward("displayEmpHolidays");
	}
	
	public ActionForward modifyHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try
		{
			ArrayList list = null;
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			
			String getLocations="select * from Location";
			ResultSet rsGetLocation=ad.selectQuery(getLocations);
			while(rsGetLocation.next()){
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("location_code")+"-"+rsGetLocation.getString("LOCNAME"));
			}
			
			holidayForm.setLocationId(locationID);
			holidayForm.setLocationName(locationName);
		}catch (Exception e) {
		e.printStackTrace();
		}
		holidayForm.setYear(0);
		holidayForm.setMessage("");
		request.setAttribute("message", "message");
		return mapping.findForward("modifyHolidays");
	}
	
	public ActionForward displayHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayHolidays()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		try
		{
			ArrayList list = null;
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			
			String getLocations="select * from Location";
			ResultSet rsGetLocation=ad.selectQuery(getLocations);
			while(rsGetLocation.next()){
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("Location_Code")+"-"+rsGetLocation.getString("LOCNAME"));
			}
			
			holidayForm.setLocationId(locationID);
			holidayForm.setLocationName(locationName);
			  Calendar cal=Calendar.getInstance();
			     int year=cal.get(Calendar.YEAR);
			int userlocationID=Integer.parseInt(user.getPlantId());
			String getHolidays="select h.Year,h.Holiday_Name,h.Day_Name,h.Holiday_Date,h.Day_Name,loc.LOCNAME,loc.location_code  from holidays as h,Location as loc " +
					"where h.Location='"+userlocationID+"' and h.Location=loc.LOCID  and h.Year='"+year+"' order by h.Date ";
			
			ResultSet rs = ad.selectQuery(getHolidays);
			
			
			list = new ArrayList();
			HolidayForm holidayForms = null;
			while (rs.next()) {
				holidayForms = new HolidayForm();
              
               
				holidayForms.setLocation(rs.getString("location_code")+"-"+rs.getString("LOCNAME"));
				holidayForms.setHolidayName(rs.getString("Holiday_Name"));
				holidayForms.setDayName(rs.getString("Day_Name"));
				holidayForms.setHolidayDate(rs.getString("Holiday_Date"));
				
				list.add(holidayForms);
			}
			request.setAttribute("listDetails", list);
			
			holidayForm.setYear(year);
			holidayForm.setRequiredLocation("");
			holidayForm.setRequiredString("");
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	String result=(String)sesion.getAttribute("result");
	System.out.println("result="+result);
	if(result==null||result==" ")
	sesion.setAttribute("result"," ");
	
	
	if(request.getParameter("id")!=null){
	
	request.setAttribute("MenuIcon", request.getParameter("id"));
	}
	clear(mapping, form, request, response);
		return mapping.findForward("displayHolidays");
	}
	public ActionForward saveHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm holidayForm = (HolidayForm) form;// TODO
		HttpSession sesion=request.getSession();
		System.out.println("saveHolidays()");
		
		String holidayDate=holidayForm.getHolidayDate();
		try {
			String dDate = holidayForm.getHolidayDate();
			String c[]=dDate.split("/");
			dDate=c[2]+"-"+c[1]+"-"+c[0];
			
			String[] changeDate=holidayDate.split("/");
			String dd=changeDate[0];
			String mm=changeDate[1];
			dd=dd.startsWith("0") ? dd.substring(1) : dd;
			mm=mm.startsWith("0") ? mm.substring(1) : mm;
			holidayDate=dd+"/"+mm+"/"+changeDate[2];
			String b[]=holidayDate.split("/");
			
			
			String formateDate=b[1]+"/"+b[0]+"/"+b[2];
		     String getDay="select datename(dw,'"+formateDate+"')";
		     String day="";
		     ResultSet rsGetDay=ad.selectQuery(getDay);
		     while(rsGetDay.next()){
		    	 day=rsGetDay.getString(1);
		     }
		     Calendar cal=Calendar.getInstance();
		     int year=holidayForm.getYear();
			String insertHoliday="insert into holidays(Location,Holiday_Name,Day_Name,Holiday_Date,Date,Year) " +
					"values('"+holidayForm.getLocation()+"','"+holidayForm.getHolidayName()+"','"+day+"','"+holidayDate+"','"+dDate+"','"+year+"')";
	
		int a = ad.SqlExecuteUpdate(insertHoliday);
		
		if (a > 0) {
			

			holidayForm.setMessage("Holiday Details Submitted  Successfully");
		}
			else
			{
				holidayForm.setMessage("Error While  Adding Holidays Details.. Please check Entered Values");
				
			}
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	System.out.println("Getting Parameter"+request.getParameter("MenuIcon2"));
	
	request.setAttribute("MenuIcon", request.getParameter("MenuIcon2"));
	
		clear(mapping, form, request, response);
		displayHolidays(mapping, form, request, response);
		return mapping.findForward("displayHolidays");
	}
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm holidayForm = (HolidayForm) form;
		String holidayDate = request.getParameter("sId");
		holidayForm.setModifyDate(holidayDate);
		try
		{
		
			
			
			String sql = "select * from holidays where Holiday_Date='"+holidayDate+"'";
		
			ResultSet rs=ad.selectQuery(sql);
		
			while (rs.next()) {
			    
				holidayForm.setLocation(rs.getString("Location"));
				holidayForm.setHolidayName(rs.getString("Holiday_Name"));
				holidayForm.setHolidayDate(rs.getString("Holiday_Date"));
				holidayForm.setYear(rs.getInt("Year"));
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	holidayForm.setMessage("");
	request.setAttribute("message", "message");
		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("modifyHolidays");
		
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("update()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		String Date=holidayForm.getModifyDate();
		try
		{
			;

		String holidayName = holidayForm.getHolidayName();
		String holidayDate = holidayForm.getHolidayDate();
	
		
		
		String dDate = holidayForm.getHolidayDate();
		String c[]=dDate.split("/");
		dDate=c[2]+"-"+c[1]+"-"+c[0];
		
		  
		  String b[]=holidayDate.split("/");
		String formateDate=b[1]+"/"+b[0]+"/"+b[2];
	     String getDay="select datename(dw,'"+formateDate+"')";
	     String day="";
	     ResultSet rsGetDay=ad.selectQuery(getDay);
	     while(rsGetDay.next()){
	    	 day=rsGetDay.getString(1);
	     }
		
	     Calendar cal=Calendar.getInstance();
	     int year=holidayForm.getYear();
		
		String updateSql = "update holidays set Year='"+year+"',Location='"+holidayForm.getLocation()+"', Holiday_Name='" + holidayName
		+"',Day_Name='"+day+"',Holiday_Date='"+holidayDate+"',Date='"+dDate+"' where Holiday_Date='"+Date+"' ";
		System.out.println("updateSql="+updateSql);
		int a = ad.SqlExecuteUpdate(updateSql);
		
		if (a > 0) {
			holidayForm.setMessage("Holiday Details Submitted  Successfully");
			request.setAttribute("modifyButton", "modifyButton");
		}
			else
			{
				holidayForm.setMessage("Error While  Adding Holidays Details.. Please check Entered Values");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("modifyHolidays");
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("update()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		String Date=holidayForm.getModifyDate();
		try
		{
			
		String deletesql="delete from holidays where Holiday_Date='"+Date+"'";
		int a = ad.SqlExecuteUpdate(deletesql);
	if (a > 0) {
			
			
			sesion.setAttribute("result","Holiday Details Deleted  Successfully");
		}
			else
			{
				
				sesion.setAttribute("result","Error While Deleting Holidays Details..");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		displayHolidays(mapping, form, request, response);
		clear(mapping, form, request, response);
		return mapping.findForward("displayHolidays");
	}
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		holidayForm.setLocation(user.getPlantId());
		holidayForm.setHolidayName("");
		holidayForm.setHolidayDate("");
		
		
		return null;
	}
	

}
