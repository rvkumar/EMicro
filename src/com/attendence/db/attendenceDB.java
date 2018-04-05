package com.attendence.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import com.attendence.form.AttendenceForm;

public class attendenceDB {

public int submitdb(String empcode,String month,HttpServletRequest request,AttendenceForm attendenceForm){
	
	Connection con = ConnectionFactory1.getConnection();
	
	int i=0;
	try {
		//Statement st=con.createStatement();
		//Statement st1=con.createStatement();
		Statement st2=con.createStatement();
		
		String sql2="Select a.empcode,a.name,a.designatn,b.[desc],CONVERT(varchar(11), a.joindate, 106) as doj from Empmst as a,deptdesc as b where a.dept=b.dept and a.empcode='"+empcode+"'";
		
		ResultSet rs2=st2.executeQuery(sql2);
		
		if (rs2.next()) {
			
			attendenceForm.setEmpcode1(rs2.getString("empcode"));
			attendenceForm.setName(rs2.getString("name"));
			attendenceForm.setDesignation(rs2.getString("designatn"));
			attendenceForm.setDepartment(rs2.getString("desc"));
			attendenceForm.setDoj(rs2.getString("doj"));
			attendenceForm.setDat1(attendenceForm.getDat());
			
			i=1;
		
		}
		
	
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return i;
}

public void displaydb(String empcode,HttpServletRequest request,AttendenceForm attendenceForm)
{
	Connection con = ConnectionFactory1.getConnection();
	int i=0;
	
	try {
		//Statement st=con.createStatement();
		Statement st1=con.createStatement();
		
		String tablename=attendenceForm.getDat()+"Trn"; //User Selected Month & Year with "Trn" Keyword
		
		ArrayList arrayl=new ArrayList();
		
		String sq1="Select convert(varchar(11),a.[date],106) as date,DATENAME(WEEKDAY,a.[date]) as day,a.arrtim,SUBSTRINg(a.presabs,1,2) as Morning,a.deptim,SUBSTRINg(a.presabs,3,2) as Evening from "+tablename+" as a where a.empcode='"+empcode+"' and SUBSTRING(convert(varchar(11),a.[date],106),4,3)+''+SUBSTRING(convert(varchar(11),a.[date],106),10,2)+'Trn'='"+tablename+"'";
		ResultSet rs1=st1.executeQuery(sq1);
		
		AttendenceForm attendenceForm1=null;
		String arrtim;
		String arrtim1[];
		String arrtim2; 
		String deptim;
		String deptim1[];
		String deptim2;
		if(rs1.next())
		{
			attendenceForm1=new AttendenceForm();
			attendenceForm1.setDay(rs1.getString("date"));
			attendenceForm1.setGetday(rs1.getString("day"));
			arrtim = rs1.getString("arrtim");
			arrtim1 = arrtim.split("\\.");
			arrtim2 = arrtim1[1];
			if (arrtim2.length() < 2)
			{
				arrtim=arrtim1[0]+"."+arrtim1[1]+"0";
			}
			attendenceForm1.setArr(arrtim);
			attendenceForm1.setArrdesc(rs1.getString("Morning"));
			deptim = rs1.getString("deptim");
			deptim1 = deptim.split("\\.");
			deptim2 = deptim1[1];
			if (deptim2.length() < 2)
			{
				deptim=deptim1[0]+"."+deptim1[1]+"0";
			}
			attendenceForm1.setDep(deptim);
			attendenceForm1.setDepdesc(rs1.getString("Evening"));
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
		    SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy");
		    Date date1=new Date();
		    Calendar now = Calendar.getInstance();   // This gets the current date and time.
		    now.get(Calendar.YEAR);
		    Date date = format1.parse((date1.getDate()+"/"+(now.get(Calendar.MONTH)+1)+"/"+(now.get(Calendar.YEAR))));
			if(format2.format(date).equalsIgnoreCase(attendenceForm1.getDay())){
				String depTime=attendenceForm1.getDep();
				if(depTime.equalsIgnoreCase("0.00")){
					attendenceForm1.setDep("");
					attendenceForm1.setDepdesc("");
				}
			}
			arrayl.add(attendenceForm1);
			
		while (rs1.next()) {
		
			attendenceForm1=new AttendenceForm();
			
			attendenceForm1.setDay(rs1.getString("date"));
			attendenceForm1.setGetday(rs1.getString("day"));
			arrtim = rs1.getString("arrtim");
			arrtim1 = arrtim.split("\\.");
			arrtim2 = arrtim1[1];
			if (arrtim2.length() < 2)
			{
				arrtim=arrtim1[0]+"."+arrtim1[1]+"0";
			}
			attendenceForm1.setArr(arrtim);
			attendenceForm1.setArrdesc(rs1.getString("Morning"));
			deptim = rs1.getString("deptim");
			deptim1 = deptim.split("\\.");
			deptim2 = deptim1[1];
			if (deptim2.length() < 2)
			{
				deptim=deptim1[0]+"."+deptim1[1]+"0";
				
			}
			attendenceForm1.setDep(deptim);
			attendenceForm1.setDepdesc(rs1.getString("Evening"));
			
			  now.get(Calendar.YEAR);
			     date = format1.parse((date1.getDate()+"/"+(now.get(Calendar.MONTH)+1)+"/"+(now.get(Calendar.YEAR))));
		    
		  
		   
			if(format2.format(date).equalsIgnoreCase(attendenceForm1.getDay())){
				String depTime=attendenceForm1.getDep();
				if(depTime.equalsIgnoreCase("0.00")){
					attendenceForm1.setDep("");
					attendenceForm1.setDepdesc("");
				}
			}
			arrayl.add(attendenceForm1);
			
			
		}
		attendenceForm.setMesage("");
		
		}
		
		else
		{
			
			attendenceForm.setMesage("Attendence Details not found");
		}
		request.setAttribute("arrayl", arrayl);
	}
	
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	
}





public int monthyear(HttpServletRequest request,AttendenceForm attendenceForm){
	
	Connection con = ConnectionFactory1.getConnection();
	ArrayList ar_id=new ArrayList();
	ArrayList ar_name=new ArrayList();
	
	int i=0;
	try {
		/*Statement st=con.createStatement();
		
		String sql="Select * from ATT_MONTH_YEAR order by ID DESC";
		
		ResultSet rs=st.executeQuery(sql);
		
		while (rs.next()) {
			
			ar_id.add(rs.getString("MONTH_SHORT_NAME"));
			ar_name.add(rs.getString("MONTH_LONG_NAME"));
		
		}*/
		
		Calendar ca = new GregorianCalendar();
		int iTDay = ca.get(Calendar.DATE);
		int iTYear = ca.get(Calendar.YEAR);
		int iTMonth = ca.get(Calendar.MONTH);
		GregorianCalendar cal = new GregorianCalendar(iTYear, iTMonth, iTDay);
	  int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
	 int month=cal.get(Calendar.MONTH);
	 int year=cal.get(Calendar.YEAR);
	 
	String month1=new SimpleDateFormat("MMMM").format(new Date(2008,iTMonth, 01));
	String tableName1=tableName((month1+iTYear),Integer.toString(iTYear));
	ar_id.add(tableName1);
	ar_name.add(month1+" "+iTYear);
	if(month1.equals("January"))
		iTYear=iTYear-1;
	String month2=new SimpleDateFormat("MMMM").format(new Date(2008,(iTMonth-1), 01));
	String tableName2=tableName((month2+iTYear),Integer.toString(iTYear));
	ar_id.add(tableName2);
	ar_name.add(month2+" "+iTYear);
	if(month2.equals("January"))
		iTYear=iTYear-1;
	String month3=new SimpleDateFormat("MMMM").format(new Date(2008,(iTMonth-2), 01));
	String tableName3=tableName((month3+iTYear),Integer.toString(iTYear));
	ar_id.add(tableName3);
	ar_name.add(month3+" "+iTYear);
		
		
		attendenceForm.setAr_id(ar_id);
		attendenceForm.setAr_name(ar_name);
		
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return i;
}
public  String tableName(String monYear,String year){
	  String mon=monYear.substring(0,3);
	  String yr=year.substring(year.length()-2,year.length());
	  return mon+yr;
}
}