package com.microlabs.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EMicroUtils {
	
	
	public static String dateConvert(String dbDate)
	  {
	   Date fdt=null;
	   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	   try{
	    fdt=sdf.parse(dbDate);
	   }
	   catch(Exception e)
	   {
	    e.printStackTrace();
	   }
	   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   String dt=dateFormat.format(fdt);
	   
	   return dt;
	  }
	
	public static String dateConvertDisplay(String dbDate)
	  {
	   Date fdt=null;
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   try{
	    fdt=sdf.parse(dbDate);
	   }
	   catch(Exception e)
	   {
	    e.printStackTrace();
	   }
	   DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	   String dt=dateFormat.format(fdt);
	   
	   return dt;
	  }
	public static String reqDisplay(String dbDate)
	  {
	   Date fdt=null;
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	   try{
	    fdt=sdf.parse(dbDate);
	   }
	   catch(Exception e)
	   {
	    e.printStackTrace();
	   }
	   DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	   String dt=dateFormat.format(fdt);
	   
	   return dt;
	  }
	public static String display1(Date dbDate)
	{
	String dt="";
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	try
	{
	dt=dateFormat.format(dbDate);
	}
	catch (Exception e)
	{
	dt="";
	}
	return dt;
	}
	public static String getCurrentDate(){
	   	
		
		
	   DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm: a", Locale.US);
	   //get current date time with Date()
	   Date date = new Date();
	   
	   String s=dateFormat.format(date);
	   
	   return s;
	}
	
	
	public static String getCurrentSysDate(){
		    
		   
		   DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   //get current date time with Date()
		   Date date = new Date();
		   System.out.println(dateFormat.format(date));
		   
		   String s=dateFormat.format(date);
		   
		   return s;
		}
	
	public static String display(Date dbDate)
	{
		String dt="";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try{
			dt=dateFormat.format(dbDate);
		}catch (Exception e) {
			dt="";
		}
		return dt;
	}
	
	
}
