package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.form.CalenderForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.toDoTask.dao.ToDoTaskDao;
import com.microlabs.utilities.UserInfo;



public class CalenderAction extends DispatchAction{
	public ActionForward massUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{CalenderForm calForm=(CalenderForm)form;
	EssDao ad=new EssDao();
	int frequency=Integer.parseInt(calForm.getFrequency());
	int  day1=Integer.parseInt(calForm.getDay());
	int month1=Integer.parseInt(calForm.getMonth());
	String holidyType=calForm.getHolidayType();
	 String location=calForm.getLocationId();
	String reqTable=getTableName(location);
	String a[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
	
	Calendar cal1 = Calendar.getInstance();
	int year =cal1.get(Calendar.YEAR);
	if(frequency!=0){//Not Every
		int month = month1;
		int i=0;
		if(month1!=12)
		{
			Calendar cal = new GregorianCalendar(year, month, 1);
		do {
		   int day = cal.get(Calendar.DAY_OF_WEEK);
		   if(frequency==cal.get(Calendar.WEEK_OF_MONTH)){
			   if (day == day1 ){
				   String update="update  "+reqTable+" set "+a[month1]+"='"+holidyType+"' where DAY="+cal.get(Calendar.DAY_OF_MONTH)+"";
					 i=ad.SqlExecuteUpdate(update);
					if(i>0)
						calForm.setMessage("Details Updated successfully");
					else
						calForm.setMessage2("Error..Please Check.");
			   }
		   }
		   cal.add(Calendar.DAY_OF_YEAR, 1);
		} 
        while (cal.get(Calendar.MONTH) == month);
		}
		else
		{
			Calendar cal = new GregorianCalendar(year, 0, 1);
			 for(int k=0;k<a.length;k++)
			   {
				 month=cal.get(Calendar.MONTH);
      do {
		   int day = cal.get(Calendar.DAY_OF_WEEK);
		   if(frequency==cal.get(Calendar.WEEK_OF_MONTH)){
			   if (day == day1 ){
				   String update="update  "+reqTable+" set "+a[k]+"='"+holidyType+"' where DAY="+cal.get(Calendar.DAY_OF_MONTH)+"";
				 i=ad.SqlExecuteUpdate(update);
					if(i>0)
						calForm.setMessage("Details Updated successfully");
					else
						calForm.setMessage2("Error..Please Check.");
			   }
		   }
		   cal.add(Calendar.DAY_OF_YEAR, 1);
		} 
      while (cal.get(Calendar.MONTH) == month);
     month=month+1;
		}
		}
		 
	}
	else{
		int month = month1;
		if(month1!=12)
		{
		 month = month1;
		Calendar cal = new GregorianCalendar(year, month, 1);
		int i=0;
		do {
			   int day = cal.get(Calendar.DAY_OF_WEEK);
			   if (day == day1 ) {
				   String update="update  "+reqTable+" set "+a[month1]+"='"+holidyType+"' where DAY="+cal.get(Calendar.DAY_OF_MONTH)+"";
						 i=ad.SqlExecuteUpdate(update);
						if(i>0)
							calForm.setMessage("Details Updated successfully");
						else
							calForm.setMessage2("Error..Please Check.");
			   }
			   cal.add(Calendar.DAY_OF_YEAR, 1);
			}  while (cal.get(Calendar.MONTH) == month);
			if(i==0)
				calForm.setMessage2("Error..Please Check.");
	}
		else
		{
			Calendar cal = new GregorianCalendar(year, 0, 1);
			 for(int k=0;k<a.length;k++)
			   {
				 month=cal.get(Calendar.MONTH);
	               

     do {
		   int day = cal.get(Calendar.DAY_OF_WEEK);
			   if (day == day1 ){
				   String update="update  "+reqTable+" set "+a[k]+"='"+holidyType+"' where DAY="+cal.get(Calendar.DAY_OF_MONTH)+"";
				int i=ad.SqlExecuteUpdate(update);
				 if(i>0)
						calForm.setMessage("Details Updated successfully");
					else
						calForm.setMessage2("Error..Please Check.");
			   }
		   cal.add(Calendar.DAY_OF_YEAR, 1);
		} 
     while (cal.get(Calendar.MONTH) == month);
    month=month+1;
		}
		}
	}
	displayCalender1(mapping, calForm, request, response);
	return mapping.findForward("displayCalender");}
	
	
	
	
	public ActionForward Modifyupdatedetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		EssDao ad1=new EssDao();
		HttpSession session = request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		CalenderForm calenderForm=(CalenderForm)form;
	    String holidaytype= calenderForm.getHolidayType();
	    String plantcode=request.getParameter("plant");
		String currentdate=request.getParameter("currentDate");
		String a[]=currentdate.split("/");
		String day=a[0];
		String mon1=a[1];
		String year=a[2];
		int mon2=Integer.parseInt(mon1);
		  String[] monthName = {"","JAN", "FEB",
				  "MAR", "APR", "MAY", "JUN", "JUL",
				  "AUG", "SEP", "OCT", "NOV",
				  "DEC"
				  };
		
	  String mon = monthName[mon2];
		
	  String location=plantcode;
	    String reqTable=getTableName(location.substring(0, 4));
	    	
		String cal="update "+reqTable+" set "+mon+"='"+holidaytype+"' where DAY='"+a[0]+"'and CYEAR='"+a[2]+"' ";
		int i=0;
		i=ad1.SqlExecuteUpdate(cal);
		if(i>0)
		{
			calenderForm.setMessage("Data updated successfully ");
		}else{
			calenderForm.setMessage("Error...Please check");
		}
		
							
		
	
	return mapping.findForward("displayCalenderlist");
	}
	
	
	
	
	
	
	
	
	public ActionForward UpdateCmpdetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		EssDao ad1=new EssDao();
		HttpSession session = request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		CalenderForm calenderForm=(CalenderForm)form;
		String plantcode=request.getParameter("plant");
		String currentdate=request.getParameter("currentDate");
		 String a[]=currentdate.split("/");
		String day=a[0];
		String mon1=a[1];
		String year=a[2];
		int mon2=Integer.parseInt(mon1);
		  String[] monthName = {"","JAN", "FEB",
				  "MAR", "APR", "MAY", "JUN", "JUL",
				  "AUG", "SEP", "OCT", "NOV",
				  "DEC"
				  };
		
	  String mon = monthName[mon2];
		
	  String location=plantcode;
	    String reqTable=getTableName(location);
		String cal1="Select  LOCATION_CODE,LOCNAME from Location where LOCATION_CODE='"+plantcode+"'";
		ResultSet rs=ad1.selectQuery(cal1);
	
			
			try {
				while(rs.next())
				{
					calenderForm.setLocationId(rs.getString("LOCATION_CODE")+" - "+rs.getString("LOCNAME"));
				 }
			} catch (SQLException e) {
					e.printStackTrace();
			}
			

			   
			   
			    String cal="select "+mon+" as abc from "+reqTable+" where DAY="+a[0]+" and CYEAR="+a[2]+"";
			    ResultSet rs1=ad1.selectQuery(cal);
				try {
					
					while(rs1.next())
					{
						calenderForm.setHolidayType(rs1.getString("abc"));
					
					}
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			
		
		calenderForm.setCurentDate(currentdate);
		
		return mapping.findForward("displayCalenderlist");

	}
	
	
	public ActionForward prviousMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	System.out.println("nextMonth1()");
	CalenderForm calenderForm=(CalenderForm)form;
		String iYears = request.getParameter("hYear");
		String iMonths = request.getParameter("hMonth");
		HttpSession session = request.getSession();
		EssDao ad1=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ResultSet rs11 = ad1.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calenderForm.setLocationIdList(locationList);
		calenderForm.setLocationLabelList(locationLabelList);
		
		
		 String location=calenderForm.getLocationId();
		    String reqTable=getTableName(location);
		
		
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
		
		

			session.setAttribute("iYear", String.valueOf(iYear));
			session.setAttribute("iMonth", String.valueOf(iMonth));

		}
		int year=Calendar.getInstance().get(Calendar.YEAR); 
		   int month=rmonth;
		   
		   month=month+1;


		   String[] monthName = {"","JAN", "FEB",
					  "MAR", "APR", "MAY", "JUN", "JUL",
					  "AUG", "SEP", "OCT", "NOV",
					  "DEC"
					  };
			
		    String mon = monthName[month];
		   
		    HashMap holidaysMap=new HashMap();
		    String cal1="select DAY,"+mon+","+year+" as CYEAR from "+reqTable+" where "+mon+"!=''";
			ResultSet rs=ad1.selectQuery(cal1);
			try {
				
				while(rs.next())
				{
					String reqDate=rs.getString("Day")+"/"+month+"/"+rs.getString("CYEAR");
					holidaysMap.put(reqDate, rs.getString(mon));
				}
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		    request.setAttribute("holidaysList", holidaysMap);
		
		return mapping.findForward("displayCalender");
	}
	
	public ActionForward nextMonth1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("nextMonth1()");

		CalenderForm calenderForm=(CalenderForm)form;
		HttpSession session = request.getSession();
		EssDao ad1=new EssDao();
	
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ResultSet rs11 = ad1.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calenderForm.setLocationIdList(locationList);
		calenderForm.setLocationLabelList(locationLabelList);
		
		
		 String location=calenderForm.getLocationId();
		    String reqTable=getTableName(location);
		
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
			
				

				session.setAttribute("iYear", String.valueOf(iYear));
				session.setAttribute("iMonth", String.valueOf(iMonth));

			}
			
			
			
				int year=Calendar.getInstance().get(Calendar.YEAR); 
			   int month=rmonth;
			   
			   month=month+1;


			   String[] monthName = {"","JAN", "FEB",
						  "MAR", "APR", "MAY", "JUN", "JUL",
						  "AUG", "SEP", "OCT", "NOV",
						  "DEC"
						  };
				
			    String mon = monthName[month];
			   
			    HashMap holidaysMap=new HashMap();
			    String cal1="select DAY,"+mon+","+year+" as CYEAR from "+reqTable+" where "+mon+"!=''";
				ResultSet rs=ad1.selectQuery(cal1);
				try {
					
					while(rs.next())
					{
						String reqDate=rs.getString("Day")+"/"+month+"/"+rs.getString("CYEAR");
						holidaysMap.put(reqDate, rs.getString(mon));
					}
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			    request.setAttribute("holidaysList", holidaysMap);
			
			return mapping.findForward("displayCalender");
		}
	
	public ActionForward displayCalender1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {
		CalenderForm calenderForm=(CalenderForm)form;
		EssDao ad=new EssDao();
		HttpSession session = request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calenderForm.setLocationIdList(locationList);
		calenderForm.setLocationLabelList(locationLabelList);
	    calenderForm.setLocationId("ML00");
	    
	    

	   int year=Calendar.getInstance().get(Calendar.YEAR); 
	   int month=Calendar.getInstance().get(Calendar.MONTH); 
	   
	   month=month+1;


	   String[] monthName = {"","JAN", "FEB",
				  "MAR", "APR", "MAY", "JUN", "JUL",
				  "AUG", "SEP", "OCT", "NOV",
				  "DEC"
				  };
		
	    String mon = monthName[month];
	   
	    HashMap holidaysMap=new HashMap();
	    String cal="select DAY,"+mon+","+year+" as CYEAR from CmpCal2013 where "+mon+"!=''";
		ResultSet rs=ad.selectQuery(cal);
		try {
			
			while(rs.next())
			{
				String reqDate=rs.getString("Day")+"/"+month+"/"+rs.getString("CYEAR");
				holidaysMap.put(reqDate, rs.getString(mon));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	    request.setAttribute("holidaysList", holidaysMap);
	    	
		
		int iYear = 0;
		int iMonth = 0;
		session.setAttribute("iYear", String.valueOf(iYear));
		session.setAttribute("iMonth", String.valueOf(iMonth));
		int user_id=user.getId();
		int ch=0;
		HashMap<Integer,ArrayList> m1=new HashMap<Integer,ArrayList>();
		ToDoTaskDao ad1=new ToDoTaskDao();
		
		
		
		return mapping.findForward("displayCalender");
	}
	public ActionForward changeReqLoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {
		CalenderForm calenderForm=(CalenderForm)form;
		EssDao ad=new EssDao();
		HttpSession session = request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calenderForm.setLocationIdList(locationList);
		calenderForm.setLocationLabelList(locationLabelList);
	   
	    String location=calenderForm.getLocationId();
	    String reqTable=getTableName(location);
	    

	   int year=Calendar.getInstance().get(Calendar.YEAR); 
	   int month=Calendar.getInstance().get(Calendar.MONTH); 
	   
	   month=month+1;


	   String[] monthName = {"","JAN", "FEB",
				  "MAR", "APR", "MAY", "JUN", "JUL",
				  "AUG", "SEP", "OCT", "NOV",
				  "DEC"
				  };
		
	    String mon = monthName[month];
	   
	    HashMap holidaysMap=new HashMap();
	    String cal="select DAY,"+mon+","+year+" as CYEAR from "+reqTable+" where "+mon+"!=''";
		ResultSet rs=ad.selectQuery(cal);
		try {
			
			while(rs.next())
			{
				String reqDate=rs.getString("Day")+"/"+month+"/"+rs.getString("CYEAR");
				holidaysMap.put(reqDate, rs.getString(mon));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	    request.setAttribute("holidaysList", holidaysMap);
	    	
		
		int iYear = 0;
		int iMonth = 0;
		session.setAttribute("iYear", String.valueOf(iYear));
		session.setAttribute("iMonth", String.valueOf(iMonth));
		int user_id=user.getId();
		int ch=0;
		HashMap<Integer,ArrayList> m1=new HashMap<Integer,ArrayList>();
		ToDoTaskDao ad1=new ToDoTaskDao();
		
		
		
		return mapping.findForward("displayCalender");
	}
	
	
	public String getTableName(String location)
	{
		String tableName="";
		if(location.equalsIgnoreCase("CBWH"))
			tableName="CBWHCAL";
		if(location.equalsIgnoreCase("ML00"))
				tableName="CmpCal2013";
		if(location.equalsIgnoreCase("ML01"))
			tableName="ML01CAL";
		if(location.equalsIgnoreCase("ML02"))
			tableName="ML02CAL";
		if(location.equalsIgnoreCase("ML03"))
			tableName="ML03CAL";
		if(location.equalsIgnoreCase("ML04"))
			tableName="ML04CAL";
		if(location.equalsIgnoreCase("ML05"))
			tableName="ML05CAL";
		if(location.equalsIgnoreCase("ML06"))
			tableName="ML06CAL";
		if(location.equalsIgnoreCase("ML07"))
			tableName="ML07CAL";
		if(location.equalsIgnoreCase("ML08"))
			tableName="ML08CAL";
		if(location.equalsIgnoreCase("ML09"))
			tableName="ML09CAL";
		if(location.equalsIgnoreCase("ML10"))
			tableName="ML10CAL";
		if(location.equalsIgnoreCase("ML11"))
			tableName="ML11CAL";
		if(location.equalsIgnoreCase("ML12"))
			tableName="ML12CAL";
		if(location.equalsIgnoreCase("ML13"))
			tableName="ML13CAL";
		if(location.equalsIgnoreCase("ML14"))
			tableName="ML14CAL";
		if(location.equalsIgnoreCase("ML15"))
			tableName="ML15CAL";
		if(location.equalsIgnoreCase("ML16"))
			tableName="ML16CAL";
		if(location.equalsIgnoreCase("ML17"))
			tableName="ML17CAL";
		if(location.equalsIgnoreCase("ML18"))
       		tableName="ML18CAL";
		if(location.equalsIgnoreCase("ML19"))
			tableName="ML19CAL";
		if(location.equalsIgnoreCase("ML20"))
			tableName="ML20CAL";
		if(location.equalsIgnoreCase("ML21"))
			tableName="ML21CAL";
		if(location.equalsIgnoreCase("ML22"))
       		tableName="ML22CAL";
		if(location.equalsIgnoreCase("ML23"))
			tableName="ML23CAL";
		if(location.equalsIgnoreCase("ML24"))
			tableName="ML24CAL";
		if(location.equalsIgnoreCase("ML25"))
			tableName="ML25CAL";
		if(location.equalsIgnoreCase("ML26"))
       		tableName="ML26CAL";
		if(location.equalsIgnoreCase("ML51"))
			tableName="ML51CAL";
		if(location.equalsIgnoreCase("ML52"))
			tableName="ML52CAL";
		if(location.equalsIgnoreCase("ML53"))
			tableName="ML53CAL";
		if(location.equalsIgnoreCase("ML54"))
			tableName="ML54CAL";
		if(location.equalsIgnoreCase("ML55"))
			tableName="ML55CAL";
		if(location.equalsIgnoreCase("ML56"))
			tableName="ML56CAL";
		if(location.equalsIgnoreCase("ML57"))
			tableName="ML57CAL";
		if(location.equalsIgnoreCase("ML58"))
			tableName="ML58CAL";
		if(location.equalsIgnoreCase("ML59"))
			tableName="ML59CAL";
		if(location.equalsIgnoreCase("ML60"))
			tableName="ML60CAL";
		if(location.equalsIgnoreCase("ML61"))
			tableName="ML61CAL";
		if(location.equalsIgnoreCase("ML62"))
			tableName="ML62CAL";
		if(location.equalsIgnoreCase("ML63"))
			tableName="ML63CAL";
		if(location.equalsIgnoreCase("ML64"))
			tableName="ML64CAL";
		if(location.equalsIgnoreCase("ML65"))
			tableName="ML65CAL";
		if(location.equalsIgnoreCase("ML66"))
			tableName="ML66CAL";
		if(location.equalsIgnoreCase("ML67"))
			tableName="ML67CAL";
		if(location.equalsIgnoreCase("ML68"))
			tableName="ML68CAL";
		if(location.equalsIgnoreCase("ML90"))
			tableName="ML90CAL";
		if(location.equalsIgnoreCase("ML91"))
			tableName="ML91CAL";
	
		return tableName;
	}
	
}