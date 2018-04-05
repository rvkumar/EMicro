package com.microlabs.newsandmedia.action;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.newsandmedia.form.CalenderForm;

public class CalenderAction extends DispatchAction {

	public ActionForward displaycalender(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		CalenderForm calenderForm=(CalenderForm)form;
		
		return mapping.findForward("display");
	}

	public ActionForward nextMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		CalenderForm calenderForm=(CalenderForm)form;
		int iYear = 0;
		int iMonth = 0;
		Calendar ca = new GregorianCalendar();

		int iTDay = ca.get(Calendar.DATE);
		int iTYear = ca.get(Calendar.YEAR);
		int iTMonth = ca.get(Calendar.MONTH);

		if (iYear == 0) {
			iYear = iTYear;
			iMonth = iTMonth;
		}

		GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);

		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);

		cal = new GregorianCalendar(iYear, iMonth, days);
		int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
		int ryear = iYear;
		int rmonth = iMonth;
		int iy=0;
		int im = 0;
		for ( iy = iTYear - 70; iy <= iTYear + 70; iy++) {
			if (iy == iYear) {
				iy = iy;
			} else {
				iy = iy;
			}
			
			for (im = 0; im <= 11; im++) {
				if (im == iMonth) {
					im = im;
				}
			}		

		}
		HttpSession session = request.getSession();
		System.out.println("nextMonth()");
		System.out.println("iy="+ryear);
		System.out.println("im="+rmonth);
		session.setAttribute("iYear", String.valueOf(ryear));
		session.setAttribute("iMonth", String.valueOf(rmonth));
		return mapping.findForward("display");
	}
	
	public ActionForward nextMonth1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	System.out.println("nextMonth1()");

		CalenderForm calenderForm=(CalenderForm)form;
	
		String iYears = request.getParameter("hYear");
		String iMonths = request.getParameter("hMonth");
		
		
		int iYear=Integer.parseInt(iYears);
		System.out.println("iYear="+iYear);
		int iMonth=Integer.parseInt(iMonths);
		iMonth=iMonth+1;
		
		if(iMonth==12)
		{
			iMonth=0;
			iYear=iYear+1;
		}
		
		System.out.println("iMonth="+iMonth);
		
		Calendar ca = new GregorianCalendar();

		int iTDay = ca.get(Calendar.DATE);
		int iTYear = ca.get(Calendar.YEAR);
		int iTMonth = ca.get(Calendar.MONTH);

		if (iYear == 0) {
			iYear = iTYear;
			iMonth = iTMonth;
		}

		GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);

		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);

		cal = new GregorianCalendar(iYear, iMonth, days);
		int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
		int ryear = iYear;
		int rmonth = iMonth;

		for (int iy = iTYear - 70; iy <= iTYear + 70; iy++) {
			if (iy == iYear) {
				iy = iy;
			} else {
				iy = iy;
			}
			int im = 0;
			for (im = 0; im <= 11; im++) {
				if (im == iMonth) {
					im = im;
				}
			}
		
			HttpSession session = request.getSession();
		

			session.setAttribute("iYear", String.valueOf(iYear));
			session.setAttribute("iMonth", String.valueOf(iMonth));

		}
		return mapping.findForward("display");
	}
	public ActionForward prviousMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	System.out.println("nextMonth1()");

		String iYears = request.getParameter("hYear");
		String iMonths = request.getParameter("hMonth");
		
		
		int iYear=Integer.parseInt(iYears);
		System.out.println("iYear="+iYear);
		int iMonth=Integer.parseInt(iMonths);
		iMonth=iMonth-1;
		
		if(iMonth==-1)
		{
			iMonth=11;
			iYear=iYear-1;
		}
		
		System.out.println("iMonth="+iMonth);
		
		Calendar ca = new GregorianCalendar();

		int iTDay = ca.get(Calendar.DATE);
		int iTYear = ca.get(Calendar.YEAR);
		int iTMonth = ca.get(Calendar.MONTH);

		if (iYear == 0) {
			iYear = iTYear;
			iMonth = iTMonth;
		}

		GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);

		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);

		cal = new GregorianCalendar(iYear, iMonth, days);
		int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
		int ryear = iYear;
		int rmonth = iMonth;

		for (int iy = iTYear - 70; iy <= iTYear + 70; iy++) {
			if (iy == iYear) {
				iy = iy;
			} else {
				iy = iy;
			}
			int im = 0;
			for (im = 0; im <= 11; im++) {
				if (im == iMonth) {
					im = im;
				}
			}
		
			HttpSession session = request.getSession();
		

			session.setAttribute("iYear", String.valueOf(iYear));
			session.setAttribute("iMonth", String.valueOf(iMonth));

		}
		return mapping.findForward("display");
	}
}