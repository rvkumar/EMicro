package com.microlabs.hr.action;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.hr.form.HolidayForm;
import com.microlabs.utilities.UserInfo;

public class HolidayAction extends DispatchAction{
	
	public ActionForward searchHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayHolidays()");
		HolidayForm holidayForm = (HolidayForm) form;
		
		
		HttpSession sesion=request.getSession();
		try
		{
			ArrayList list = null;
			
			HRDao adHoliday=new HRDao();
			
			String getHolidays="select * from holidays where Location='"+holidayForm.getLocation()+"' and" +
					" SUBSTRING ( holiday_date ,7 , 10 ) = "+holidayForm.getHolidayYear()+"";
			System.out.println("getHolidays="+getHolidays);
			ResultSet rs = adHoliday.selectQuery(getHolidays);
			
			list = new ArrayList();
			HolidayForm holidayForms = null;
			while (rs.next()) {
				holidayForms = new HolidayForm();
                String locationName1="";
                String locationAddress1="";
				ResultSet rs2=adHoliday.selectQuery("select * from Location where LOCID='"+rs.getString("Location")+"'");
				while(rs2.next())
				{
					locationName1=rs2.getString("LOCNAME")+" "+rs2.getString("location_code");
					locationAddress1=rs2.getString("location_address");
				}
				holidayForm.setLocation(locationName1);
				holidayForm.setLocationAddress(locationAddress1);
				holidayForms.setHolidayName(rs.getString("Holiday_Name"));
				holidayForms.setDayName(rs.getString("Day_Name"));
				holidayForms.setHolidayDate(rs.getString("Holiday_Date"));
				holidayForms.setHolidayType(rs.getString("Holiday_Type"));
				
				list.add(holidayForms);
			}
			
			int a = list.size();
			
			if (a > 0) {
				
			}
			else
			{
				sesion.setAttribute("result","No Holidays...");
			}
			
			request.setAttribute("listDetails", list);
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("displayHolidays");
	}
	
	public ActionForward displayHolidays(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayHolidays()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		String locId = request.getParameter("id");
		System.out.println("ln -- >"+request.getParameter("id"));
		int userlocationID=Integer.parseInt(user.getPlantId());
		if(!locId.equalsIgnoreCase("HR")){
			userlocationID=Integer.parseInt(locId);
		}
		
		try
		{
			LinkedList list = new LinkedList();
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			UserDao adHoliday=new UserDao();
			String getLocations="select * from Location";
			ResultSet rsGetLocation=adHoliday.selectQuery(getLocations);
			while(rsGetLocation.next()){
				if(userlocationID == rsGetLocation.getInt("LOCID")){
					locationID.addFirst(rsGetLocation.getInt("LOCID"));
					locationName.addFirst(rsGetLocation.getString("LOCNAME"));
				}
				else{
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("LOCNAME"));
				}
			}
			
			holidayForm.setLocationId(locationID);
			holidayForm.setLocationName(locationName);
			
			
			ResultSet rs = adHoliday.selectQuery("select * from holidays where Location='"+userlocationID+"'");
			
			
			HolidayForm holidayForms = null;
			while (rs.next()) {
				holidayForms = new HolidayForm();
                String locationName1="";
                String getLocationHolidays="select * from Location where LOCID='"+rs.getString("Location")+"'";
				ResultSet rs2=adHoliday.selectQuery(getLocationHolidays);
				while(rs2.next())
				{
					locationName1=rs2.getString("LOCNAME");
				}
				holidayForms.setLocation(locationName1);
				
				
				holidayForms.setHolidayName(rs.getString("Holiday_Name"));
				holidayForms.setDayName(rs.getString("Day_Name"));
				holidayForms.setHolidayDate(rs.getString("Holiday_Date"));
				
				list.add(holidayForms);
			}
			request.setAttribute("header", "Holiday List");
			request.setAttribute("listDetails", list);
			
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
		return mapping.findForward("displayHRList");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm holidayForm = (HolidayForm) form;
		String holidayDate = request.getParameter("sId");
		holidayForm.setModifyDate(holidayDate);
		try
		{
		
			HRDao adHoliday=new HRDao();
			
			String sql = "select * from holidays where Holiday_Date='"+holidayDate+"'";
		
			ResultSet rs=adHoliday.selectQuery(sql);
		
			while (rs.next()) {
			    
				holidayForm.setLocation(rs.getString("Location"));
				holidayForm.setHolidayName(rs.getString("Holiday_Name"));
				holidayForm.setHolidayDate(rs.getString("Holiday_Date"));
				holidayForm.setHolidayType(rs.getString("Holiday_Type"));
				
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
		
		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("displayHolidays");
		
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("update()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		String Date=holidayForm.getModifyDate();
		try
		{
			HRDao adHoliday=new HRDao();

		String holidayName = holidayForm.getHolidayName();
		String holidayDate = holidayForm.getHolidayDate();
		String holidayType = holidayForm.getHolidayType();
		
		String b[]=holidayDate.split("/");
		
		String formateDate=b[1]+"/"+b[0]+"/"+b[2];
	     String getDay="select datename(dw,'"+formateDate+"')";
	     String day="";
	     ResultSet rsGetDay=adHoliday.selectQuery(getDay);
	     while(rsGetDay.next()){
	    	 day=rsGetDay.getString(1);
	     }
		
		
		
		String updateSql = "update holidays set Location='"+holidayForm.getLocation()+"', Holiday_Name='" + holidayName
		+"',Day_Name='"+day+"',Holiday_Date='"+holidayDate+"',Holiday_Type='"+holidayType
		+"' where Holiday_Date='"+Date+"' ";
		System.out.println("updateSql="+updateSql);
		int a = adHoliday.SqlExecuteUpdate(updateSql);
		
		if (a > 0) {
			
			//holidayForm.setMessage("Holiday Details Updated  Successfully");
			sesion.setAttribute("result","Holiday Details Updated  Successfully");
		}
			else
			{
				//holidayForm.setMessage("Error While  Updating Holidays Details.. Please check Entered Values");
				sesion.setAttribute("result","Error While  Updating Holidays Details.. Please check Entered Values");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		displayHolidays(mapping, form, request, response);
		return mapping.findForward("displayHolidays");
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("update()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		String Date=holidayForm.getModifyDate();
		try
		{
			HRDao adHoliday=new HRDao();
		String deletesql="delete from holidays where Holiday_Date='"+Date+"'";
		int a = adHoliday.SqlExecuteUpdate(deletesql);
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
		return mapping.findForward("displayHolidays");
	}
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HolidayForm holidayForm = (HolidayForm) form;
		holidayForm.setLocation("");
		holidayForm.setHolidayName("");
		holidayForm.setHolidayDate("");
		holidayForm.setHolidayType("");
		
		
		return null;
	}
	

}
