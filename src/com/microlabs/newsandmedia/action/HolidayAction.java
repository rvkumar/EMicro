package com.microlabs.newsandmedia.action;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.newsandmedia.dao.HolidayDao;
import com.microlabs.newsandmedia.form.HolidayForm;

public class HolidayAction extends DispatchAction{
	
	public ActionForward displayHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayHolidays()");
		HolidayForm holidayForm = (HolidayForm) form;
		HttpSession sesion=request.getSession();
		try
		{
			ArrayList list = null;
			
			HolidayDao adHoliday = new HolidayDao();
			
			ResultSet rs = adHoliday.selectQuery("select * from holidays");
			
			list = new ArrayList();
			HolidayForm holidayForms = null;
			while (rs.next()) {
				holidayForms = new HolidayForm();
				holidayForms.setHolidayName(rs.getString(2));
				holidayForms.setHolidayDate(rs.getString(3));
				holidayForms.setHolidayType(rs.getString(4));
				holidayForms.setCountry(rs.getString(5));
				holidayForms.setState(rs.getString(6));
				holidayForms.setCity(rs.getString(7));
				holidayForms.setPlanet(rs.getString(8));
				list.add(holidayForms);
			}
			request.setAttribute("listDetails", list);
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	String result=(String)sesion.getAttribute("result");
	System.out.println("result="+result);
	if(result==null||result==" ")
	sesion.setAttribute("result"," ");
	
	
	clear(mapping, form, request, response);
		return mapping.findForward("displayHolidays");
	}
	public ActionForward saveHolidays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm holidayForm = (HolidayForm) form;// TODO
		HttpSession sesion=request.getSession();
		
		try {
			String holidayName = holidayForm.getHolidayName();
			
			String holidayDate = holidayForm.getHolidayDate();
			String holidayType = holidayForm.getHolidayType();
			String country = holidayForm.getCountry();
			String state = holidayForm.getState();
			String city = holidayForm.getCity();
			String planet = holidayForm.getPlanet();
			HolidayDao adHoliday = new HolidayDao();
			String insertsql = "insert into holidays(Holiday_Name, Holiday_Date, Holiday_Type, Country, State, City, Planet) values('"
				+ holidayName
				+ "','"
				+ holidayDate
				+ "','"
				+ holidayType
				+ "','"
				+ country
				+ "','"
				+ state
				+ "','"
				+ city
				+ "','"
				+ planet+ "')";
		int a = adHoliday.SqlExecuteUpdate(insertsql);
		
		if (a > 0) {
	
			//holidayForm.setMessage("Holiday Details Submitted  Successfully");
			sesion.setAttribute("result","Holiday Details Submitted  Successfully");
		}
			else
			{
				sesion.setAttribute("result","Error While  Adding Holidays Details.. Please check Entered Values");
				
			}
	
	} catch (Exception e) {
		e.printStackTrace();
	}
	
		clear(mapping, form, request, response);
		displayHolidays(mapping, form, request, response);
		return mapping.findForward("displayHolidays");
	}
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HolidayForm holidayForm = (HolidayForm) form;
		String holidayDate = request.getParameter("sId");
		
		try
		{
		
			HolidayDao adHoliday = new HolidayDao();
			
			String sql = "select * from holidays where Holiday_Date='"+holidayDate+"'";
			System.out.println("sqllllllll"+sql);
			ResultSet rs=adHoliday.selectQuery(sql);
		
			while (rs.next()) {
			
				holidayForm.setHolidayName(rs.getString(2));
				holidayForm.setHolidayDate(rs.getString(3));
				holidayForm.setModifyDate(rs.getString(3));
				holidayForm.setHolidayType(rs.getString(4));
				holidayForm.setCountry(rs.getString(5));
				holidayForm.setState(rs.getString(6));
				holidayForm.setCity(rs.getString(7));
				holidayForm.setPlanet(rs.getString(8));
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
		HolidayDao adHoliday = new HolidayDao();

		String holidayName = holidayForm.getHolidayName();
		String holidayDate = holidayForm.getHolidayDate();
		String holidayType = holidayForm.getHolidayType();
		String country = holidayForm.getCountry();
		String state = holidayForm.getState();
		String city = holidayForm.getCity();
		String planet = holidayForm.getPlanet();
		
		String updateSql = "update holidays set Holiday_Name='" + holidayName
		+"',Holiday_Date='"+holidayDate+"',Holiday_Type='"+holidayType
		+"',Country='"+country+"',State='"+state+"',City='"+city+"',Planet='"+planet+"' " +
				"where Holiday_Date='"+Date+"' ";
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
		HolidayDao adHoliday = new HolidayDao();
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
		holidayForm.setHolidayName("");
		holidayForm.setHolidayDate("");
		holidayForm.setHolidayType("");
		holidayForm.setCountry("");
		holidayForm.setState("");
		holidayForm.setCity("");
		holidayForm.setPlanet("");
		
		return null;
	}
	

}
