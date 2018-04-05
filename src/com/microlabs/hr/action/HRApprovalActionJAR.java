package com.microlabs.hr.action;


import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.microlabs.db.AttConnectionFactory;
import com.microlabs.ess.dao.AttDao;
import com.microlabs.ess.form.JoiningFormalityForm;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.ess.form.OnDutyForm;
import com.microlabs.hr.dao.SAPAttendenceDAO;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.microlabs.hr.form.HRApprovalForm;
import com.microlabs.hr.form.SAPAttendenceForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.utilities.UserInfo;



public class HRApprovalActionJAR  extends DispatchAction{

	AttDao ad=new AttDao();
	private ResultSet rs=null;
	private Statement st=null;
	private Connection conn=null;
	private static final String DESTINATION = "SAP_DESTINATION";
	private String query1;
	private String query2;
	private String query3;
	private String query4;
	private String query5;
	private String query6;
	private String query7;
	
 
	
	public String Empname(String a)
	{
		
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
		
		String emp = "select EMP_FULLNAME from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("emp_fullname"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
		return b;
		
	}
	
	

	
	
	public String insertTEMPEMP(String query) throws Exception {
		String datecurrent="";
		synchronized (this) {
			
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal1 = Calendar.getInstance();							
		 datecurrent=dateFormat.format(cal1.getTime());
	  
	////insert empNos
		String insert="insert into Temp_Emp(Pernr,Date_Time,Swipe_count) select pernr,'"+datecurrent+"',Swipe_count from ("+query+ ")t";
		int bqb=ad.SqlExecuteUpdate(insert);	
		
		
	}
		
		return datecurrent;
	}
	

	
	public int getActualEmpNo(int pernr) throws Exception {
		
		int emp=0;
		
		
		String per="Select pernr from emp_official_info where (PERNR='"+pernr+"' or Swipe_No='"+pernr+"')";
		ResultSet a=ad.selectQuery(per);
			if(a.next())	
			{
			emp=a.getInt("pernr");
				}
		
			try {
				a.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return emp;
	}
	
	
	
	public int getPunchNo(int pernr) throws Exception {
		
		int emp=0;
		
		
		String per="Select case when Swipe_No=0 then PERNR else isnULL(Swipe_No,pernr) end as pernr from emp_official_info where PERNR='"+pernr+"'";
		ResultSet a=ad.selectQuery(per);
			if(a.next())	
			{
			emp=a.getInt("pernr");
				}
		
			try {
				a.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return emp;
	}
	

	
	
	public String prevmonthdays(String selectedyear,String selectedmon)
	{
		String b="";
		// Create a calendar object and set year and month
		Calendar mycal = Calendar.getInstance();

		mycal.set(Integer.parseInt(selectedyear), Integer.parseInt(selectedmon)-1, 1);
		
		// Get the number of days in that month
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for(int j=21;j<=daysInMonth;j++)
		{
			b=b+"day"+j+"+";
			
		}
		
		b=b.substring(0, b.length()-1);
		return b;
	}
	
	public String currentmonthdays(String selectedyear,String selectedmon,int days)
	{
		String b="";
		// Create a calendar object and set year and month
		Calendar mycal = Calendar.getInstance();

		mycal.set(Integer.parseInt(selectedyear), Integer.parseInt(selectedmon)-1, 1);
		
		// Get the number of days in that month
		int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(days!=0)
		{
			daysInMonth=days;
		}
		else
		{
			daysInMonth=daysInMonth;
		}
		
		for(int j=1;j<=daysInMonth;j++)
		{
			b=b+"day"+j+"+";
			
		}
		
		b=b.substring(0, b.length()-1);
		return b;
	}
	
	public String EmpLoc(String a)
	{
		
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
			
		String emp = "select LOCID from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("LOCID"); 
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return b;
		
	}
	

	

	
	public String checknightshift(String shft ) {
	
		
		String night="";
		
		String g="Select * from EMP_SHIFT_MASTER where shift_code='"+shft+"'";
		ResultSet n=ad.selectQuery(g);
		try {
			if(n.next())
			{
				night=n.getString("Night_shift");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			n.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return night;
	}

	

	
	public static void main(String[] args) {
		
		HRApprovalActionJAR hrad=new HRApprovalActionJAR();
		
		String tran="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,User_Groupid,LocationId,FromDate,ToDate,EmpNo,ProcessedDate,AllEmps,rep_grp,category,workid,paygrp) "
				+ "values('"+args[0].replace("nulldata", "")+"','"+args[1].replace("nulldata", "")+"','"+args[2].replace("nulldata", "")+"','"+args[3].replace("nulldata", "")+"',"
						+ "'"+args[4].replace("nulldata", "")+"','"+args[5].replace("nulldata", "")+"','"+args[6].replace("nulldata", "")+"',getdate()"
								+ ",'"+args[7].replace("nulldata", "")+"','"+args[8].replace("nulldata", "")+"','"+args[9].replace("nulldata", "")+"',"
										+ "'"+args[10].replace("nulldata", "")+"','"+args[11].replace("nulldata", "")+"')";
		int j3=hrad.ad.SqlExecuteUpdate(tran);
		
		
		String user_empno=args[0].replace("nulldata", "");
		String user_empname=args[1].replace("nulldata", "");
		String user_grpid=args[2].replace("nulldata", "");
		String locid=args[3].replace("nulldata", "");
		String fromdate=args[4].replace("nulldata", "");
		String todate=args[5].replace("nulldata", "");
		String reqempno=args[6].replace("nulldata", "");
		String ACtualemps=args[7].replace("nulldata", "");
		String repgrp=args[8].replace("nulldata", "");
		String catgory=args[9].replace("nulldata", "");
		String workid=args[10].replace("nulldata", "");
		String pay_grp=args[11].replace("nulldata", "");
		String workID[]=null;
		if(!workid.equalsIgnoreCase(""))
		{
			if(workid.contains(","))
		 workID=workid.split(",");
			else
			{
				workID[0]=workid;
			}
		}
			
			
		
			String cmpcode=locid;
			String tempcmpcode="";
			if(cmpcode.equalsIgnoreCase("ML90"))
			{
				tempcmpcode="ML18";
			}
			if(cmpcode.equalsIgnoreCase("ML25"))
			{
				tempcmpcode="ML11";
			}
			if(cmpcode.equalsIgnoreCase("ML27"))
			{
				tempcmpcode="ML15";
			}
			
			if(cmpcode.equalsIgnoreCase("ML92")||cmpcode.equalsIgnoreCase("ML54")||cmpcode.equalsIgnoreCase("ML26"))
			{
				tempcmpcode="ML22";
			}
			
			if(cmpcode.equalsIgnoreCase("ML02")||cmpcode.equalsIgnoreCase("ML03"))
			{
				tempcmpcode="ML01";
			}
			
			
		    
		    if(cmpcode.equalsIgnoreCase("ML00"))
			{
		    	if(workID!=null)
		    	{
		    	for (int i = 0; i < workID.length; i++) {
		    		  if(workID[i].equalsIgnoreCase("2") || workID[i].equalsIgnoreCase("789"))
		    		    {
		    		    	tempcmpcode="ML18";
		    		    }
				}
		    	}
		    	if(repgrp!=null)
		    	{
		    	
		    		  if(repgrp.equalsIgnoreCase("12"))
		    		    {
		    		    	tempcmpcode="ML18";
		    		    }
				
		    	}
		    
			}
		    
		    String reqemp=reqempno;
		    
		    String tabledate="";
		    String sqlfromdate="";	    
		    String sqltodat="";
		    String filedate="";
		    int filedateday=0;
		    String filedatemonth="";
		    String filedateyear="";
		    
		    
		    int curnoofdays=0;
		    String curmonth="";
		    String curyear="";
		    
		    int prenoofdays=0;
		    String prevmont="";
		    String prevyear="";
		    
		    String from[]=fromdate.split("/");
		    sqlfromdate=from[2]+"-"+from[1]+"-"+from[0];
		    
		    String to[]=todate.split("/");
		    sqltodat=to[2]+"-"+to[1]+"-"+to[0];
		    
		    LinkedList miss=new LinkedList();
		    LinkedList in=new LinkedList();
	        LinkedList out=new LinkedList();
	        LinkedList datelist=new LinkedList();
	        LinkedList caldatelist=new LinkedList();
	        LinkedList leave=new LinkedList();
	        LinkedList onduty=new LinkedList();
	        LinkedList compff=new LinkedList();
	        LinkedList allleave=new LinkedList();
	        LinkedList allonduty=new LinkedList();
	        LinkedList allcompff=new LinkedList();
	        String emptyshiftregister= "";
	        String emptyshiftmaster= "";
	 		
	 		 String prevdate="";
	 		 HRApprovalForm date=new  HRApprovalForm();
	 		 
				
	 		 
	 		String daysreq="select day(DATEADD(day,-2,'"+sqlfromdate+"')) as lastbutone,day(DATEADD(day,-1,'"+sqlfromdate+"')) as last";
			  ResultSet rss=hrad.ad.selectQuery(daysreq);
			try {
				if(rss.next())
					
				{
					date=new  HRApprovalForm();
					date.setId("Day"+rss.getString("lastbutone"));		
					date.setDay(rss.getString("lastbutone"));
					datelist.add(date);
					
					date=new  HRApprovalForm();
					date.setId("Day"+rss.getString("last"));	
					date.setDay(rss.getString("last"));
					datelist.add(date);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			

			///reset to deafuklt 00:00:00 and AAAA before proces
	  		
	  		DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal11 = Calendar.getInstance();							
			String datecurren11t=dateFormat1.format(cal11.getTime());
		  
			
		////insert empNos
			String insert4="EXEC EMP_Split '"+(ACtualemps.replace(",", "$")).replace("'", "")+"','"+datecurren11t+"' ";
			int bqb4=hrad.ad.SqlExecuteUpdate(insert4);	
	
			
			
			
			
		

			String aqq =" Insert into Attendance_Process_log(emp_id,emp_name,process_Name,process_Location,process_Status,emp_count,lock_id,start_date,end_date,start_time) values "
					+ " ('"+user_empno+"','"+user_empname+"','Attendance','"+locid+"', 'Pending',(select count(*) from temp_emp where Date_Time='"+datecurren11t+"'),'"+datecurren11t+"',"
							+ "'"+fromdate+"','"+todate+"' ,getdate())" ;
			int kff11=hrad.ad.SqlExecuteUpdate(aqq);
			
			 String q2t="EXEC ResetEmpAttd '"+sqlfromdate+"','"+sqltodat+"','"+datecurren11t+"'";
				int kff1=hrad.ad.SqlExecuteUpdate(q2t);
				
		
				
				
				
				
	//delete empNos
			    
			    String delete3="delete Temp_Emp where Date_Time='"+datecurren11t+"'";						    
			   int lldd1=hrad.ad.SqlExecuteUpdate(delete3);
	 		
		    String countdays="SELECT * FROM dbo.ExplodeDates('"+sqlfromdate+"','"+sqltodat+"') as thedate";
		    ResultSet rscount=hrad.ad.selectQuery(countdays);
		    try {
				while(rscount.next())
				{
	               
	             
					  date=new  HRApprovalForm();
					  HRApprovalForm cal=new HRApprovalForm();
						
						String rtr="select day(DATEADD(day,0,'"+rscount.getString("thedate")+"')) as day,month(DATEADD(day,0,'"+rscount.getString("thedate")+"')) as mon,year(DATEADD(day,0,'"+rscount.getString("thedate")+"')) as year,"
								+ "day(DATEADD(day,-1,'"+rscount.getString("thedate")+"')) as prevday,month(DATEADD(day,0,'"+rscount.getString("thedate")+"')) as prevmon,year(DATEADD(day,0,'"+rscount.getString("thedate")+"')) as prevyear";
						  ResultSet rrr=hrad.ad.selectQuery(rtr);
						if(rrr.next())
							
						{
							date.setId("Day"+rrr.getString("day"));	
							date.setDay(rrr.getString("day"));						
			         	    datelist.add(date);
			         	    
			         	    cal.setId("Day"+rrr.getString("day"));	
			         	    cal.setDay(rrr.getString("day"));
			         	    cal.setMonth(rrr.getString("mon"));
			         	    cal.setYear(rrr.getString("year"));
			         	
			         	    caldatelist.add(cal);
			         	    
			         		   
						}
						rrr.close();

					
					String b[]=rscount.getString("thedate").split("-");
					tabledate=b[2]+b[1]+b[0].substring(2, b[0].length())+"-T";
					filedate= rscount.getString("thedate");
					filedateday=Integer.parseInt(b[2]);
					filedatemonth=b[1];
					filedateyear=b[0];
						//start date, to date
					String st[]=sqlfromdate.split("-");
					
					curmonth=to[1];
					curyear=to[2];
					
					 prevmont=Integer.toString((Integer.parseInt(from[1])));
					 prevyear="";
					if(prevmont.equalsIgnoreCase("0"))
					{
					 prevmont="12";
					 prevyear=Integer.toString((Integer.parseInt(from[2])));	
					}
					else
						prevyear=curyear;
					
			

					// Create a calendar object and set year and month
					Calendar mycal = Calendar.getInstance();

					mycal.set(Integer.parseInt(curyear), Integer.parseInt(curmonth)-1, 1);
					
					// Get the number of days in that month
					curnoofdays = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
					
					
					mycal.set(Integer.parseInt(prevyear), Integer.parseInt(prevmont)-1, 1);
					
					prenoofdays = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
					
					
	             
	         	   
				String filepath="";
			
			    String empno="";
				File f = new File(filepath);
				if(true) {
				
				
			
		          
		  		
				    
				    //Flat file processing
				    String empoff="";
			
				    String empshift="";
				    String pernrs="";
				    int parseempno=0;
				    
				    if(tempcmpcode.equalsIgnoreCase(""))
				    {
				    empoff="select * from(select left(id,2) as day,right(left(id,4),2) as mont,right(left(id,6),2)+':'+right(left(id,8),2) as time,SUBSTRING ( id ,9 ,len(id))  as tpernr,ATTENDANCE_MASTER.Id as fullpunch,ATTENDANCE_MASTER.Location as punchloc,ATTENDANCE_MASTER.date as punchdate from "
				    		+ "ATTENDANCE_MASTER where Date='"+filedate+"' and Location='"+cmpcode+"' )t,emp_official_info,Paygroup_Master,DEPARTMENT,DESIGNATION,Category"
				    		+ " where (emp_official_info.PERNR=t.tpernr or emp_official_info.swipe_no=t.tpernr)  and Paygroup_Master.Paygroup=emp_official_info.PAY_GROUP "
				    		+ "and DEPARTMENT.DPTID=emp_official_info.DPTID and DESIGNATION.DSGID=emp_official_info.DSGID and Category.STAFFCAT=emp_official_info.STAFFCAT";
				    }
				    else
				    {
				    	 empoff="select * from(select left(id,2) as day,right(left(id,4),2) as mont,right(left(id,6),2)+':'+right(left(id,8),2) as time,SUBSTRING ( id ,9 ,len(id))  as tpernr,ATTENDANCE_MASTER.Id as fullpunch,ATTENDANCE_MASTER.Location as punchloc,ATTENDANCE_MASTER.date as punchdate from "
						    		+ "ATTENDANCE_MASTER where Date='"+filedate+"' and Location='"+tempcmpcode+"' )t,emp_official_info,Paygroup_Master,DEPARTMENT,DESIGNATION,Category"
						    		+ " where (emp_official_info.PERNR=t.tpernr or emp_official_info.swipe_no=t.tpernr)  and Paygroup_Master.Paygroup=emp_official_info.PAY_GROUP "
						    		+ "and DEPARTMENT.DPTID=emp_official_info.DPTID and DESIGNATION.DSGID=emp_official_info.DSGID and Category.STAFFCAT=emp_official_info.STAFFCAT";
						  	
				    }
			
					
						empoff=empoff+" and emp_official_info.pernr in ("+ACtualemps+") and ACTIVE=1 ";
					
				    
					
					empoff=empoff+" order by t.time";
				    ResultSet rssempoffr=hrad.ad.selectQuery(empoff);
				    int j=1;
				   
				   
				    aqq ="  update  Attendance_Process_log set fetch_st=getdate(),progress=15"
							+ " where lock_id ='"+datecurren11t+"' and (fetch_st is  null or fetch_st='')";
						kff11=hrad.ad.SqlExecuteUpdate(aqq); 
				    while(rssempoffr.next())
				    {
				 
				    	j++;
				    	
				    	 HRApprovalForm intime=new  HRApprovalForm();
				
				    
			             
				    	 //add full punch
				    	 intime.setFullpunch(rssempoffr.getString("fullpunch"));
				    	 intime.setPunchloc(rssempoffr.getString("punchloc"));
				    	 intime.setPunchdate(rssempoffr.getString("punchdate"));
				    	 
			             parseempno=Integer.parseInt(rssempoffr.getString("pernr"));
				     empno=Integer.toString(parseempno);
				
				     
				    
				     
				     if(rssempoffr.getString("Shift_Code").equalsIgnoreCase(""))
				     {
				    	 emptyshiftmaster=emptyshiftmaster+","+empno;
				     }
				     else
				     {
				     empshift=hrad.getempshift(filedateday, empno, filedatemonth, filedateyear);
				     }
				     
				     String currentshifttime[]=new String[0];
				     String prevshifttime[]=new String[0];			    
				     String empshiftmaster[]=new String[0];
				     
				     
				     if(!empshift.equalsIgnoreCase(""))
				     {
				    	 empshiftmaster=empshift.split(",");
				      currentshifttime=hrad.getempshifttime(empshiftmaster[1]);
				      prevshifttime=hrad.getempshifttime(empshiftmaster[0]);
				     }
				     else
				     {
				    	 emptyshiftregister=emptyshiftregister+","+empno;
				     }
				     String curshftstart="";
				     String curshftend="";
				     String curfrsthalfend="";
				     String cursechalfstart="";
				     String curnightshift="";
				     String curpunchstart="";
				     String curpunchend="";
				     String curpunchvalid="";
				     
				     String prevshftstart="";
				     String prevshftend="";
				     String prevfrsthalfend="";
				     String prevsechalfstart="";
				     String prevnightshift="";
				     String prevpunchstart="";
				     String prevpunchend="";
				     String prevpunchvalid="";
				     
				     if(currentshifttime.length!=0)
				     {
				      curshftstart=currentshifttime[0].substring(0, 5);
				      curshftend=currentshifttime[1].substring(0, 5);
				      curfrsthalfend=currentshifttime[2].substring(0, 5);
				      cursechalfstart=currentshifttime[3].substring(0, 5);
				      curnightshift=currentshifttime[4];
				      curpunchstart=currentshifttime[5].substring(0, 5);
				      curpunchend=currentshifttime[6].substring(0, 5);
				      curpunchvalid=currentshifttime[7].substring(0, 5);
				     }
				     if(prevshifttime.length!=0)
				     {
				    	 prevshftstart=prevshifttime[0].substring(0, 5);
				    	 prevshftend=prevshifttime[1].substring(0, 5);
				    	 prevfrsthalfend=prevshifttime[2].substring(0, 5);
				    	 prevsechalfstart=prevshifttime[3].substring(0, 5);
				    	 prevnightshift=prevshifttime[4];
				    	 prevpunchstart=prevshifttime[5].substring(0, 5);
				    	 prevpunchend=prevshifttime[6].substring(0, 5);
				    	 prevpunchvalid=prevshifttime[7].substring(0, 5);
				     }

				     
				     
				     String punchtime="";
		
				     		     
				      punchtime=rssempoffr.getString("time");
			         
					         
					   
			         
	           if(curnightshift.equalsIgnoreCase("0")&&prevnightshift.equalsIgnoreCase("0"))
	            {
	        	   String actualcurshftstart="";
	        	   String actualcurfrsthalfend="";
	        	   String actualcursechalfstart="";
	        	   actualcurshftstart=curshftstart;
	        	   actualcurfrsthalfend=curfrsthalfend;
	        	   actualcursechalfstart=cursechalfstart;
	        	   if(curshftstart.substring(0, 2).equalsIgnoreCase("00"))
	        	   {
	        		  
	        		   curshftstart=Integer.toString(Integer.parseInt(curshftstart.substring(0, 2))+24)+curshftstart.substring(2, 5);
	        		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	        		   cursechalfstart=Integer.toString(Integer.parseInt(cursechalfstart.substring(0, 2))+24)+cursechalfstart.substring(2, 5);
	        	   }
	        	   
			        if(punchtime.compareTo(prevpunchvalid)>0 && punchtime.compareTo(curshftstart)<=0 || punchtime.compareTo(curfrsthalfend)>=0 && punchtime.compareTo(cursechalfstart)<=0)
			        {
			            intime.setStatus("I");
			            
			            if(!curfrsthalfend.equalsIgnoreCase("00:00") && !cursechalfstart.equalsIgnoreCase("00:00") )
			            	
			            {
			            	if(punchtime.compareTo(curfrsthalfend)>=0 )
					        {
					        	intime.setMessage("S");
					        }
			            }
			            
			            
			            
			            if(pernrs.contains(empno+"I"))
		                {
			            	if(punchtime.compareTo(curfrsthalfend)>=0 )
					        {
			            	  intime.setStatus("O");
					        }
		                }
			            
			            curshftstart=actualcurshftstart;
			            curfrsthalfend=actualcurfrsthalfend;
			            cursechalfstart=actualcursechalfstart;
			        }
			        
			        
	            
			        if(punchtime.compareTo(curshftstart)>0  && punchtime.compareTo(curfrsthalfend)<0)
			        
			        {
			        	intime.setStatus("I");
			        	intime.setMessage("S");
			        	if(pernrs.contains(empno+"I"))
			                {
				            	
				            	  intime.setStatus("O");
						        
			               }
			        }
			       
			       
			        
			        if(punchtime.compareTo(cursechalfstart)>0)
			        {
			        	if(punchtime.compareTo(cursechalfstart)>0 && punchtime.compareTo(curshftend)<0)
			        	{
			        		if(pernrs.contains(empno+"I"))
			                {
			        		 intime.setStatus("O");
					         intime.setMessage("S");
			                }
			        		else
			        		{
			        			intime.setStatus("I");
						         intime.setMessage("S");
			        		}
			        	}
			        	else
			        	{
			        		 intime.setStatus("O");
					         intime.setMessage("");
			        	}
			        }
			       	
	            
			         intime.setId("Day"+filedateday);
			         intime.setTime(punchtime);
			         intime.setEmployeeNo(empno);
			         intime.setFrommonth(filedatemonth);
			         intime.setCalyear(filedateyear);
			         
			         
	                //punch valid till
			         if(prevpunchvalid.compareTo(curshftstart)<=0)
				     {
			         
			         if(punchtime.compareTo("00:00")>=0 && punchtime.compareTo(prevpunchvalid)<=0)
			         {

				        	int prevday=0;
				    		int prevmonth=0;
				    		int preyear=0;
				        	int tempfiledateday=0;
				        	
				        	String actualpunch="";
				 
				            intime.setStatus("O");
				            actualpunch=punchtime;
				            if(curshftend.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				            	curshftend=Integer.toString(Integer.parseInt(curshftend.substring(0, 2))+24)+curshftend.substring(2, 5);
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				            if(punchtime.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				         
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				            
				          
				            intime.setMessage("");
				            
				           
				            punchtime=actualpunch;
				            tempfiledateday=filedateday-1;
				            if(tempfiledateday==0)
				    		{
				            		
				            	
				            	mycal = Calendar.getInstance();
				    			if(filedateday==Integer.parseInt(filedatemonth))
				    			{
				    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=12;
				    			preyear=Integer.parseInt(filedateyear)-1;
				    	
				    			}
				    			else
				    			{
				    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=Integer.parseInt(filedatemonth)-1;
				    			preyear=Integer.parseInt(filedateyear);
				    		
				    			}
				    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
				    			tempfiledateday=daysInMonth;
				    		
				            	
				    		}
				    		else
				    		{
				    			tempfiledateday=tempfiledateday;	
				    			prevmonth=Integer.parseInt(filedatemonth);
				    			preyear=Integer.parseInt(filedateyear);
				    		} 
				            intime.setId("Day"+tempfiledateday);	          
					         intime.setTime(punchtime);
					         intime.setFrommonth(Integer.toString(prevmonth));
					         intime.setCalyear(Integer.toString(preyear));
					         intime.setEmployeeNo(empno);
				          
				        
			         }
				     }
			         
			         
			         //////TS or SS1combined with FS
				        
				        if(prevshftend.equalsIgnoreCase(curshftstart))
				        {/*


				        	int prevday=0;
				    		int prevmonth=0;
				    		int preyear=0;
				        	int tempfiledateday=0;
				 
				        	String actualpunch="";
							 
				            intime.setStatus("O");
				            actualpunch=punchtime;
				            if(prevshftend.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				            	prevshftend=Integer.toString(Integer.parseInt(curshftend.substring(0, 2))+24)+curshftend.substring(2, 5);
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				           
				            intime.setMessage("");
				            
				           
				            punchtime=actualpunch;
				           
				            tempfiledateday=filedateday-1;
				            if(tempfiledateday==0)
				    		{
				            		
				            	
				            	mycal = Calendar.getInstance();
				    			if(filedateday==Integer.parseInt(filedatemonth))
				    			{
				    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=12;
				    			preyear=Integer.parseInt(filedateyear)-1;
				    	
				    			}
				    			else
				    			{
				    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=Integer.parseInt(filedatemonth)-1;
				    			preyear=Integer.parseInt(filedateyear);
				    		
				    			}
				    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
				    			tempfiledateday=daysInMonth;
				    		
				            	
				    		}
				    		else
				    		{
				    			tempfiledateday=tempfiledateday;	
				    			prevmonth=Integer.parseInt(filedatemonth);
				    			preyear=Integer.parseInt(filedateyear);
				    		} 
				            intime.setId("Day"+tempfiledateday);	          
					         intime.setTime(punchtime);
					         intime.setFrommonth(Integer.toString(prevmonth));
					         intime.setCalyear(Integer.toString(preyear));
					         intime.setEmployeeNo(empno);
				          
				        
			         
				        */}
			         
			         
	            }
	           
	           
	           
	           if(curnightshift.equalsIgnoreCase("1")&&prevnightshift.equalsIgnoreCase("0"))
	           {
	        	   String actualcurshftstart="";
	        	   String actualcurfrsthalfend="";
	        	   String actualcursechalfstart="";
	        	   actualcurshftstart=curshftstart;
	        	   actualcurfrsthalfend=curfrsthalfend;
	        	   actualcursechalfstart=cursechalfstart;
	        	   if(curshftstart.substring(0, 2).equalsIgnoreCase("00"))
	        	   {
	        		  
	        		   curshftstart=Integer.toString(Integer.parseInt(curshftstart.substring(0, 2))+24)+curshftstart.substring(2, 5);
	        		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	        		   cursechalfstart=Integer.toString(Integer.parseInt(cursechalfstart.substring(0, 2))+24)+cursechalfstart.substring(2, 5);
	        	   }
			        if(punchtime.compareTo(prevpunchvalid)>0 && punchtime.compareTo(curshftstart)<=0 || punchtime.compareTo(curfrsthalfend)>=0 && punchtime.compareTo(cursechalfstart)<=0)
			        {
			            intime.setStatus("I");
			            
	                  if(!curfrsthalfend.equalsIgnoreCase("00:00") && !cursechalfstart.equalsIgnoreCase("00:00") )
			            	
			            {
	                	  if(curshftstart.compareTo(curfrsthalfend)>0)
	                	   {
	                		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	                	   }
	                	  
	                	  
			            if(punchtime.compareTo(curfrsthalfend)>=0 )
				        {
				        	intime.setMessage("S");
				        }
			            
			            }
			            if(pernrs.contains(empno+"I"))
		                {
			            	if(punchtime.compareTo(curfrsthalfend)>=0 )
					        {
			            	  intime.setStatus("O");
					        }
		                }
			            
			            curshftstart=actualcurshftstart;
			            curfrsthalfend=actualcurfrsthalfend;
			            cursechalfstart=actualcursechalfstart;
			        }
			        
			        if(curshftstart.compareTo(curfrsthalfend)>0)
	         	   {
	         		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	         	   }
	           
			        if(punchtime.compareTo(curshftstart)>0  && punchtime.compareTo(curfrsthalfend)<0)
				        
			        {
			        	intime.setStatus("I");
			        	intime.setMessage("S");
			        	if(pernrs.contains(empno+"I"))
		                {
			            	
			            	  intime.setStatus("O");
					        
		               }
			        }
			        curfrsthalfend=actualcurfrsthalfend;
			        
			              
			         intime.setId("Day"+filedateday);
			         intime.setTime(punchtime);
			         intime.setEmployeeNo(empno);
			         intime.setFrommonth(filedatemonth);
			         intime.setCalyear(filedateyear);
			         
			         
	//punch valid till
			         if(prevpunchvalid.compareTo(curshftstart)<=0)
				     {
			         
			         if(punchtime.compareTo("00:00")>=0 && punchtime.compareTo(prevpunchvalid)<=0)
			         {

				        	int prevday=0;
				    		int prevmonth=0;
				    		int preyear=0;
				        	int tempfiledateday=0;
				 
				        	String actualpunch="";
							 
				            intime.setStatus("O");
				            actualpunch=punchtime;
				            if(curshftend.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				            	curshftend=Integer.toString(Integer.parseInt(curshftend.substring(0, 2))+24)+curshftend.substring(2, 5);
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				            if(punchtime.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				         
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				          
				            intime.setMessage("");
				            
				           
				            punchtime=actualpunch;
				           
				            tempfiledateday=filedateday-1;
				            if(tempfiledateday==0)
				    		{
				            		
				            	
				            	mycal = Calendar.getInstance();
				    			if(filedateday==Integer.parseInt(filedatemonth))
				    			{
				    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=12;
				    			preyear=Integer.parseInt(filedateyear)-1;
				    	
				    			}
				    			else
				    			{
				    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=Integer.parseInt(filedatemonth)-1;
				    			preyear=Integer.parseInt(filedateyear);
				    		
				    			}
				    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
				    			tempfiledateday=daysInMonth;
				    		
				            	
				    		}
				    		else
				    		{
				    			tempfiledateday=tempfiledateday;	
				    			prevmonth=Integer.parseInt(filedatemonth);
				    			preyear=Integer.parseInt(filedateyear);
				    		} 
				            intime.setId("Day"+tempfiledateday);	          
					         intime.setTime(punchtime);
					         intime.setFrommonth(Integer.toString(prevmonth));
					         intime.setCalyear(Integer.toString(preyear));
					         intime.setEmployeeNo(empno);
				          
				        
			         }
				     }
			         
			         
			         //////TS or SS1combined with FS
				        
				        if(prevshftend.equalsIgnoreCase(curshftstart))
				        {/*


				        	int prevday=0;
				    		int prevmonth=0;
				    		int preyear=0;
				        	int tempfiledateday=0;
				 
				        	String actualpunch="";
							 
				            intime.setStatus("O");
				            actualpunch=punchtime;
				            if(prevshftend.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				            	prevshftend=Integer.toString(Integer.parseInt(curshftend.substring(0, 2))+24)+curshftend.substring(2, 5);
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				          
				            intime.setMessage("");
				            
				           
				            punchtime=actualpunch;
				           
				            tempfiledateday=filedateday-1;
				            if(tempfiledateday==0)
				    		{
				            		
				            	
				            	mycal = Calendar.getInstance();
				    			if(filedateday==Integer.parseInt(filedatemonth))
				    			{
				    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=12;
				    			preyear=Integer.parseInt(filedateyear)-1;
				    	
				    			}
				    			else
				    			{
				    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=Integer.parseInt(filedatemonth)-1;
				    			preyear=Integer.parseInt(filedateyear);
				    		
				    			}
				    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
				    			tempfiledateday=daysInMonth;
				    		
				            	
				    		}
				    		else
				    		{
				    			tempfiledateday=tempfiledateday;	
				    			prevmonth=Integer.parseInt(filedatemonth);
				    			preyear=Integer.parseInt(filedateyear);
				    		} 
				            intime.setId("Day"+tempfiledateday);	          
					         intime.setTime(punchtime);
					         intime.setFrommonth(Integer.toString(prevmonth));
					         intime.setCalyear(Integer.toString(preyear));
					         intime.setEmployeeNo(empno);
				          
				        
			         
				        */}
			      }
	           if(curnightshift.equalsIgnoreCase("0")&&prevnightshift.equalsIgnoreCase("1"))
	           {
		     
	        	   String actualcurshftstart="";
	        	   String actualcurfrsthalfend="";
	        	   String actualcursechalfstart="";
	        	   actualcurshftstart=curshftstart;
	        	   actualcurfrsthalfend=curfrsthalfend;
	        	   actualcursechalfstart=cursechalfstart;
	        	   if(curshftstart.substring(0, 2).equalsIgnoreCase("00"))
	        	   {
	        		  
	        		   curshftstart=Integer.toString(Integer.parseInt(curshftstart.substring(0, 2))+24)+curshftstart.substring(2, 5);
	        		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	        		   cursechalfstart=Integer.toString(Integer.parseInt(cursechalfstart.substring(0, 2))+24)+cursechalfstart.substring(2, 5);
	        	   }
	        	   if(prevpunchvalid.compareTo(curshftstart)<=0)
	    		     {
	        	   
		        if(punchtime.compareTo(prevpunchvalid)>0 && punchtime.compareTo(curshftstart)<=0 || punchtime.compareTo(curfrsthalfend)>=0 && punchtime.compareTo(cursechalfstart)<=0)
		        {
		            intime.setStatus("I");
		            
		            if(!curfrsthalfend.equalsIgnoreCase("00:00") && !cursechalfstart.equalsIgnoreCase("00:00") )
		            	
		            {
		            if(punchtime.compareTo(curfrsthalfend)>=0 )
			        {
			        	intime.setMessage("S");
			        }
		            }
		            if(pernrs.contains(empno+"I"))
	                {
		            	if(punchtime.compareTo(curfrsthalfend)>=0 )
				        {
		            	  intime.setStatus("O");
				        }
	                }
		            
		            intime.setId("Day"+filedateday);
			         intime.setTime(punchtime);
			         intime.setEmployeeNo(empno);
			         intime.setFrommonth(filedatemonth);
			         intime.setCalyear(filedateyear);
		            
			         curshftstart=actualcurshftstart;
			            curfrsthalfend=actualcurfrsthalfend;
			            cursechalfstart=actualcursechalfstart;
		        }
	    		     }
	        	   
	        	   if( punchtime.compareTo(curshftstart)<=0 || punchtime.compareTo(curfrsthalfend)>=0 && punchtime.compareTo(cursechalfstart)<=0)
	   	        {
	   	            intime.setStatus("I");
	   	            
	   	            if(!curfrsthalfend.equalsIgnoreCase("00:00") && !cursechalfstart.equalsIgnoreCase("00:00") )
	   	            	
	   	            {
	   	            if(punchtime.compareTo(curfrsthalfend)>=0 )
	   		        {
	   		        	intime.setMessage("S");
	   		        }
	   	            }
	   	            if(pernrs.contains(empno+"I"))
	                   {
	   	            	if(punchtime.compareTo(curfrsthalfend)>=0 )
	   			        {
	   	            	  intime.setStatus("O");
	   			        }
	                   }
	   	            
	   	            intime.setId("Day"+filedateday);
	   		         intime.setTime(punchtime);
	   		         intime.setEmployeeNo(empno);
	   		         intime.setFrommonth(filedatemonth);
	   		         intime.setCalyear(filedateyear);
	   	            
	   		         curshftstart=actualcurshftstart;
	   		            curfrsthalfend=actualcurfrsthalfend;
	   		            cursechalfstart=actualcursechalfstart;
	   	        }
	        	   
		        if(punchtime.compareTo(curshftstart)>0  && punchtime.compareTo(curfrsthalfend)<0)
		        {
		        	 intime.setStatus("I");
		        	 intime.setMessage("S");
		        	 if(pernrs.contains(empno+"I"))
		                {
			            	
			            	  intime.setStatus("O");
					        
		               }
		        	 
		        	 intime.setId("Day"+filedateday);
			         intime.setTime(punchtime);
			         intime.setEmployeeNo(empno);
			         intime.setFrommonth(filedatemonth);
			         intime.setCalyear(filedateyear);
		        }
		       
		        
		        if(punchtime.compareTo(cursechalfstart)>0)
		        {
		        	if(punchtime.compareTo(cursechalfstart)>0 && punchtime.compareTo(curshftend)<0)
		        	{
		        		if(pernrs.contains(empno+"I"))
		                {
		        			   intime.setStatus("O");
		        		         intime.setMessage("S");
		        		         intime.setTime(punchtime);
		        		         intime.setId("Day"+filedateday);
		        		         intime.setFrommonth(filedatemonth);
		        		         intime.setCalyear(filedateyear);
		        		         intime.setEmployeeNo(empno);
		                }
		        		else
		        		{
		        			   intime.setStatus("I");
		        		         intime.setMessage("S");
		        		         intime.setTime(punchtime);
		        		         intime.setId("Day"+filedateday);
		        		         intime.setFrommonth(filedatemonth);
		        		         intime.setCalyear(filedateyear);
		        		         intime.setEmployeeNo(empno);
		        		}
		        	}
		        	else
		        	{
		        		  intime.setStatus("O");
		     	         intime.setMessage("");
		     	         intime.setTime(punchtime);
		     	         intime.setId("Day"+filedateday);
		     	         intime.setFrommonth(filedatemonth);
		     	         intime.setCalyear(filedateyear);
		     	         intime.setEmployeeNo(empno);
		        	}
		        }
		        
		        curshftstart=actualcurshftstart;
	            curfrsthalfend=actualcurfrsthalfend;
	            cursechalfstart=actualcursechalfstart;
		    	
		        if(punchtime.compareTo(prevshftend)>=0 && punchtime.compareTo(prevpunchend)<=0 )
		        {
		        
		        	
		        	int prevday=0;
		    		int prevmonth=0;
		    		int preyear=0;
		        	int tempfiledateday=0;
		        	 date=new  HRApprovalForm();
		            intime.setStatus("O");
		            intime.setMessage("");
		            
		            tempfiledateday=filedateday-1;
		            if(tempfiledateday==0)
		    		{
		            		
		            	
		            	mycal = Calendar.getInstance();
		    			if(filedateday==Integer.parseInt(filedatemonth))
		    			{
		    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=12;
		    			preyear=Integer.parseInt(filedateyear)-1;
		    	
		    			}
		    			else
		    			{
		    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=Integer.parseInt(filedatemonth)-1;
		    			preyear=Integer.parseInt(filedateyear);
		    		
		    			}
		    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    			tempfiledateday=daysInMonth;
		    		
		            	
		    		}
		    		else
		    		{
		    			tempfiledateday=tempfiledateday;	
		    			prevmonth=Integer.parseInt(filedatemonth);
		    			preyear=Integer.parseInt(filedateyear);
		    		} 
		            intime.setId("Day"+tempfiledateday);	          
			         intime.setTime(punchtime);
			         intime.setFrommonth(Integer.toString(prevmonth));
			         intime.setCalyear(Integer.toString(preyear));
			         intime.setEmployeeNo(empno);
		          
		        }
		        else
		        {
	        
		         intime.setId("Day"+filedateday);
		         intime.setTime(punchtime);
		         intime.setFrommonth(filedatemonth);
		         intime.setCalyear(filedateyear);
		         intime.setEmployeeNo(empno);
		        }  
			        
		        
		      //only if punch in time exceeds 24;00
		        if(prevshftstart.substring(0, 2).equalsIgnoreCase("00"))
	        	   {
		        if(punchtime.compareTo(prevshftstart)>0  && punchtime.compareTo(prevfrsthalfend)<0)
			        
		        {
		        	intime.setStatus("I");
		        	 intime.setMessage("S");
		        	
		        	int prevday=0;
		    		int prevmonth=0;
		    		int preyear=0;
		        	int tempfiledateday=0;
		        	 date=new  HRApprovalForm();
		          
		 
		            tempfiledateday=filedateday-1;
		            if(tempfiledateday==0)
		    		{
	            		
	            	
	            	 mycal = Calendar.getInstance();
	    			if(filedateday==Integer.parseInt(filedatemonth))
	    			{
	    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
	    			prevmonth=12;
	    			preyear=Integer.parseInt(filedateyear)-1;
	    	
	    			}
	    			else
	    			{
	    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
	    			prevmonth=Integer.parseInt(filedatemonth)-1;
	    			preyear=Integer.parseInt(filedateyear);
	    	
	    			}
	    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    			tempfiledateday=daysInMonth;
	    		
	            	
	    		}
		    		else
		    		{
		    			tempfiledateday=tempfiledateday;	
		    			prevmonth=Integer.parseInt(filedatemonth);
		    			preyear=Integer.parseInt(filedateyear);
		    		}
		            
		            
		            intime.setId("Day"+tempfiledateday);	          
			         intime.setTime(punchtime);
			         intime.setFrommonth(Integer.toString(prevmonth));
			         intime.setCalyear(Integer.toString(preyear));
			         intime.setEmployeeNo(empno);
		        }
	        	   }
		        
		      //punchin time 24.00
				   if(curshftstart.substring(0, 2).equalsIgnoreCase("00"))
			        	   {
				        if( punchtime.compareTo(curshftstart)<=0 && punchtime.compareTo("00:00")>=0)
				        {
				        	intime.setStatus("I");
				        	 
				        	
				        	int prevday=0;
				    		int prevmonth=0;
				    		int preyear=0;
				        	int tempfiledateday=0;
				        	 date=new  HRApprovalForm();
				          
				 
				            tempfiledateday=filedateday-1;
				            if(tempfiledateday==0)
				    		{
			            		
			            	
			            	 mycal = Calendar.getInstance();
			    			if(filedateday==Integer.parseInt(filedatemonth))
			    			{
			    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
			    			prevmonth=12;
			    			preyear=Integer.parseInt(filedateyear)-1;
			    	
			    			}
			    			else
			    			{
			    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
			    			prevmonth=Integer.parseInt(filedatemonth)-1;
			    			preyear=Integer.parseInt(filedateyear);
			    	
			    			}
			    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
			    			tempfiledateday=daysInMonth;
			    		
			            	
			    		}
				    		else
				    		{
				    			tempfiledateday=tempfiledateday;	
				    			prevmonth=Integer.parseInt(filedatemonth);
				    			preyear=Integer.parseInt(filedateyear);
				    		}
				            
				            
				            intime.setId("Day"+tempfiledateday);	          
					         intime.setTime(punchtime);
					         intime.setFrommonth(Integer.toString(prevmonth));
					         intime.setCalyear(Integer.toString(preyear));
					         intime.setEmployeeNo(empno);
				        }
				        }
			      
		      ///punch valid
				   
				   
		        
		        if(!pernrs.contains(empno+"O"))
	            {
		        	 if(prevpunchvalid.compareTo(curshftstart)<=0)
				     {
		        	
		         if(punchtime.compareTo("00:00")>=0 && punchtime.compareTo(prevpunchvalid)<=0)
		         {

			        	int prevday=0;
			    		int prevmonth=0;
			    		int preyear=0;
			        	int tempfiledateday=0;
			 
			        	String actualpunch="";
						 
			            intime.setStatus("O");
			            actualpunch=punchtime;
			            if(curshftend.substring(0, 2).equalsIgnoreCase("00"))
			        	   {
			            	curshftend=Integer.toString(Integer.parseInt(curshftend.substring(0, 2))+24)+curshftend.substring(2, 5);
			        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
			        		  
			        	   }
			            
			            if(punchtime.substring(0, 2).equalsIgnoreCase("00"))
			        	   {
			         
			        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
			        		  
			        	   }
			            if(punchtime.compareTo(curshftend)<0)
				        {
				        	
				        	 intime.setMessage("S");
				        
				        }
			            else
			            {
			            intime.setMessage("");
			            }
			           
			            punchtime=actualpunch;
			           
			            tempfiledateday=filedateday-1;
			            if(tempfiledateday==0)
			    		{
			            		
			            	
			            	mycal = Calendar.getInstance();
			    			if(filedateday==Integer.parseInt(filedatemonth))
			    			{
			    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
			    			prevmonth=12;
			    			preyear=Integer.parseInt(filedateyear)-1;
			    	
			    			}
			    			else
			    			{
			    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
			    			prevmonth=Integer.parseInt(filedatemonth)-1;
			    			preyear=Integer.parseInt(filedateyear);
			    		
			    			}
			    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
			    			tempfiledateday=daysInMonth;
			    		
			            	
			    		}
			    		else
			    		{
			    			tempfiledateday=tempfiledateday;	
			    			prevmonth=Integer.parseInt(filedatemonth);
			    			preyear=Integer.parseInt(filedateyear);
			    		} 
			            intime.setId("Day"+tempfiledateday);	          
				         intime.setTime(punchtime);
				         intime.setFrommonth(Integer.toString(prevmonth));
				         intime.setCalyear(Integer.toString(preyear));
				         intime.setEmployeeNo(empno);
			          
			        
		         }
				     }
	            }
			       
		        
		        
		        //////TS or SS1combined with FS
		        
		        if(prevshftend.equalsIgnoreCase(curshftstart))
		        {


		        	int prevday=0;
		    		int prevmonth=0;
		    		int preyear=0;
		        	int tempfiledateday=0;
		 
		        	String actualpunch="";
					 
		            intime.setStatus("O");
		            actualpunch=punchtime;
		            if(prevshftend.substring(0, 2).equalsIgnoreCase("00"))
		        	   {
		            	prevshftend=Integer.toString(Integer.parseInt(curshftend.substring(0, 2))+24)+curshftend.substring(2, 5);
		        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
		        		  
		        	   }
		         
		            intime.setMessage("");
		            
		           
		            punchtime=actualpunch;
		           
		            tempfiledateday=filedateday-1;
		            if(tempfiledateday==0)
		    		{
		            		
		            	
		            	mycal = Calendar.getInstance();
		    			if(filedateday==Integer.parseInt(filedatemonth))
		    			{
		    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=12;
		    			preyear=Integer.parseInt(filedateyear)-1;
		    	
		    			}
		    			else
		    			{
		    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=Integer.parseInt(filedatemonth)-1;
		    			preyear=Integer.parseInt(filedateyear);
		    		
		    			}
		    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    			tempfiledateday=daysInMonth;
		    		
		            	
		    		}
		    		else
		    		{
		    			tempfiledateday=tempfiledateday;	
		    			prevmonth=Integer.parseInt(filedatemonth);
		    			preyear=Integer.parseInt(filedateyear);
		    		} 
		            intime.setId("Day"+tempfiledateday);	          
			         intime.setTime(punchtime);
			         intime.setFrommonth(Integer.toString(prevmonth));
			         intime.setCalyear(Integer.toString(preyear));
			         intime.setEmployeeNo(empno);
		          
		        
	         
		        }
		        
		        
	            }
			         
	           
	           if(curnightshift.equalsIgnoreCase("1")&&prevnightshift.equalsIgnoreCase("1"))
	           {
	        	   
	        	   String actualcurshftstart="";
	        	   String actualcurfrsthalfend="";
	        	   String actualcursechalfstart="";
	        	   String actualpunch="";
	        	   actualcurshftstart=curshftstart;
	        	   actualcurfrsthalfend=curfrsthalfend;
	        	   actualcursechalfstart=cursechalfstart;
	        	   actualpunch=punchtime;
	        	   if(curshftstart.substring(0, 2).equalsIgnoreCase("00"))
	        	   {
	        		  
	        		   curshftstart=Integer.toString(Integer.parseInt(curshftstart.substring(0, 2))+24)+curshftstart.substring(2, 5);
	        		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	        		   cursechalfstart=Integer.toString(Integer.parseInt(cursechalfstart.substring(0, 2))+24)+cursechalfstart.substring(2, 5);
	        		   punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
	        	   }
	        	   if(curshftstart.substring(0, 2).equalsIgnoreCase("24"))
	        	   {
			        if(punchtime.compareTo(prevpunchvalid)>0 && punchtime.compareTo(curshftstart)<=0 || punchtime.compareTo(curfrsthalfend)>=0 && punchtime.compareTo(cursechalfstart)<=0)
			        {
			            intime.setStatus("I");
			            
	                    if(!curfrsthalfend.equalsIgnoreCase("00:00") && !cursechalfstart.equalsIgnoreCase("00:00") )
			            	
			            {
	                    	
	                    	if(curshftstart.compareTo(curfrsthalfend)>0)
	                 	   {
	                 		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	                 	   }
	                    	
			            if(punchtime.compareTo(curfrsthalfend)>=0 )
				        {
				        	intime.setMessage("S");
				        }
			            }
			            if(pernrs.contains(empno+"I"))
		                {
			            	if(punchtime.compareTo(curfrsthalfend)>=0 )
					        {
			            	  intime.setStatus("O");
					        }
		                }
			            
			            intime.setId("Day"+filedateday);
				         intime.setTime(actualpunch);
				         intime.setFrommonth(filedatemonth);
				         intime.setCalyear(filedateyear);
				         intime.setEmployeeNo(empno);
				         
				         curshftstart=actualcurshftstart;
				            curfrsthalfend=actualcurfrsthalfend;
				            cursechalfstart=actualcursechalfstart;
				       
			        }
			        if(actualpunch.compareTo(prevpunchvalid)>0 && actualpunch.compareTo(curshftstart)<=0 || actualpunch.compareTo(curfrsthalfend)>=0 && actualpunch.compareTo(cursechalfstart)<=0)
			        {
			        	  intime.setStatus("I");
				            
		                    if(!curfrsthalfend.equalsIgnoreCase("00:00") && !cursechalfstart.equalsIgnoreCase("00:00") )
				            	
				            {
				            if(actualpunch.compareTo(curfrsthalfend)>=0 )
					        {
					        	intime.setMessage("S");
					        }
				            }
				            if(pernrs.contains(empno+"I"))
			                {
				            	if(actualpunch.compareTo(curfrsthalfend)>=0 )
						        {
				            	  intime.setStatus("O");
						        }
			                }
				            
				            intime.setId("Day"+filedateday);
					         intime.setTime(actualpunch);
					         intime.setFrommonth(filedatemonth);
					         intime.setCalyear(filedateyear);
					         intime.setEmployeeNo(empno);
					         
					         curshftstart=actualcurshftstart;
					            curfrsthalfend=actualcurfrsthalfend;
					            cursechalfstart=actualcursechalfstart;
			        }
			        
	        	   }
	        	   else
	        	   {
	        		   
	        		   if(prevpunchvalid.compareTo(curshftstart)<=0)
	      		     {
	        		   if(punchtime.compareTo(prevpunchvalid)>0 && punchtime.compareTo(curshftstart)<=0 || punchtime.compareTo(curfrsthalfend)>=0 && punchtime.compareTo(cursechalfstart)<=0)
	   		        {
	   		            intime.setStatus("I");
	   		            
	                       if(!curfrsthalfend.equalsIgnoreCase("00:00") && !cursechalfstart.equalsIgnoreCase("00:00") )
	   		            	
	   		            {
	                    	   
	                    	   if(curshftstart.compareTo(curfrsthalfend)>0)
	                     	   {
	                     		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	                     	   }
	                    	   
	   		            if(punchtime.compareTo(curfrsthalfend)>=0 )
	   			        {
	   			        	intime.setMessage("S");
	   			        }
	   		            }
	   		            if(pernrs.contains(empno+"I"))
	   	                {
	   		            	if(punchtime.compareTo(curfrsthalfend)>=0 )
	   				        {
	   		            	  intime.setStatus("O");
	   				        }
	   	                }
	   		            
	   		            intime.setId("Day"+filedateday);
	   			         intime.setTime(actualpunch);
	   			         intime.setFrommonth(filedatemonth);
	   			         intime.setCalyear(filedateyear);
	   			         intime.setEmployeeNo(empno);
	   			         
	   			         curshftstart=actualcurshftstart;
	   			            curfrsthalfend=actualcurfrsthalfend;
	   			            cursechalfstart=actualcursechalfstart;
	   			       
	   		        }
	        		  
	        	   }
	        		   actualcurfrsthalfend=curfrsthalfend;
	            	   if(curshftstart.compareTo(curfrsthalfend)>0)
	            	   {
	            		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	            	   }
	        		   
	        		   if( punchtime.compareTo(curshftstart)<=0 || punchtime.compareTo(curfrsthalfend)>=0 && punchtime.compareTo(cursechalfstart)<=0)
	      		        {
	      		            intime.setStatus("I");
	      		            
	                          if(!curfrsthalfend.equalsIgnoreCase("00:00") && !cursechalfstart.equalsIgnoreCase("00:00") )
	      		            	
	      		            {
	      		            if(punchtime.compareTo(curfrsthalfend)>=0 )
	      			        {
	      			        	intime.setMessage("S");
	      			        }
	      		            }
	      		            if(pernrs.contains(empno+"I"))
	      	                {
	      		            	if(punchtime.compareTo(curfrsthalfend)>=0 )
	      				        {
	      		            	  intime.setStatus("O");
	      				        }
	      	                }
	      		            
	      		            intime.setId("Day"+filedateday);
	      			         intime.setTime(actualpunch);
	      			         intime.setFrommonth(filedatemonth);
	      			         intime.setCalyear(filedateyear);
	      			         intime.setEmployeeNo(empno);
	      			         
	      			         curshftstart=actualcurshftstart;
	      			            curfrsthalfend=actualcurfrsthalfend;
	      			            cursechalfstart=actualcursechalfstart;
	      			       
	      		        }  
	        		   
	        		   curshftstart=actualcurshftstart;
				            curfrsthalfend=actualcurfrsthalfend;
				            cursechalfstart=actualcursechalfstart;
	        	   }
	        	   actualcurfrsthalfend=curfrsthalfend;
	        	   if(curshftstart.compareTo(curfrsthalfend)>0)
	        	   {
	        		   curfrsthalfend=Integer.toString(Integer.parseInt(curfrsthalfend.substring(0, 2))+24)+curfrsthalfend.substring(2, 5);
	        	   }
			        
			        if(actualpunch.compareTo(curshftstart)>0  && actualpunch.compareTo(curfrsthalfend)<0)
			        {
			        	 intime.setStatus("I");
			        	 intime.setMessage("S");
			        	 if(pernrs.contains(empno+"I"))
			                {
				            	
				            	  intime.setStatus("O");
						        
			               }
			        	 intime.setId("Day"+filedateday);
				         intime.setTime(actualpunch);
				         intime.setEmployeeNo(empno);
				         intime.setFrommonth(filedatemonth);
				         intime.setCalyear(filedateyear);
			        }
			        
			        curshftstart=actualcurshftstart;
			            curfrsthalfend=actualcurfrsthalfend;
			            cursechalfstart=actualcursechalfstart;
			        
			     
			        
			        
			        if(actualpunch.compareTo(prevshftend)>=0 && actualpunch.compareTo(prevpunchend)<=0 )
			        {
			        	int prevday=0;
			    		int prevmonth=0;
			    		int preyear=0;
			        	int tempfiledateday=0;
			        	 date=new  HRApprovalForm();
			            intime.setStatus("O");
			            intime.setMessage("");
			 
			            tempfiledateday=filedateday-1;
			            if(tempfiledateday==0)
			    		{
		            		
		            	
		            	 mycal = Calendar.getInstance();
		    			if(filedateday==Integer.parseInt(filedatemonth))
		    			{
		    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=12;
		    			preyear=Integer.parseInt(filedateyear)-1;
		    	
		    			}
		    			else
		    			{
		    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=Integer.parseInt(filedatemonth)-1;
		    			preyear=Integer.parseInt(filedateyear);
		    	
		    			}
		    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    			tempfiledateday=daysInMonth;
		    		
		            	
		    		}
			    		else
			    		{
			    			tempfiledateday=tempfiledateday;	
			    			prevmonth=Integer.parseInt(filedatemonth);
			    			preyear=Integer.parseInt(filedateyear);
			    		} 
			            intime.setId("Day"+tempfiledateday);	          
				         intime.setTime(actualpunch);
				         intime.setFrommonth(Integer.toString(prevmonth));
				         intime.setCalyear(Integer.toString(preyear));
				         intime.setEmployeeNo(empno);
			         
			        }
			        else
			        {
		        
			            intime.setId("Day"+filedateday);
				         intime.setTime(actualpunch);
				         intime.setFrommonth(filedatemonth);
				         intime.setCalyear(filedateyear);
				         intime.setEmployeeNo(empno);
			        } 
			        
			        //only if punch in time exceeds 24;00
			        if(prevshftstart.substring(0, 2).equalsIgnoreCase("00"))
		        	   {
			        if(punchtime.compareTo(prevshftstart)>0  && punchtime.compareTo(prevfrsthalfend)<0)
				        
			        {
			        	intime.setStatus("I");
			        	 intime.setMessage("S");
			        	
			        	int prevday=0;
			    		int prevmonth=0;
			    		int preyear=0;
			        	int tempfiledateday=0;
			        	 date=new  HRApprovalForm();
			          
			 
			            tempfiledateday=filedateday-1;
			            if(tempfiledateday==0)
			    		{
		            		
		            	
		            	 mycal = Calendar.getInstance();
		    			if(filedateday==Integer.parseInt(filedatemonth))
		    			{
		    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=12;
		    			preyear=Integer.parseInt(filedateyear)-1;
		    	
		    			}
		    			else
		    			{
		    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=Integer.parseInt(filedatemonth)-1;
		    			preyear=Integer.parseInt(filedateyear);
		    	
		    			}
		    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    			tempfiledateday=daysInMonth;
		    		
		            	
		    		}
			    		else
			    		{
			    			tempfiledateday=tempfiledateday;	
			    			prevmonth=Integer.parseInt(filedatemonth);
			    			preyear=Integer.parseInt(filedateyear);
			    		}
			            
			            
			            intime.setId("Day"+tempfiledateday);	          
				         intime.setTime(actualpunch);
				         intime.setFrommonth(Integer.toString(prevmonth));
				         intime.setCalyear(Integer.toString(preyear));
				         intime.setEmployeeNo(empno);
			        }
		        	   }
			      //punchin time 24.00
			   if(curshftstart.substring(0, 2).equalsIgnoreCase("00"))
		        	   {
			        if( actualpunch.compareTo(curshftstart)<=0 && actualpunch.compareTo("00:00")>=0)
			        {
			        	intime.setStatus("I");
			        	 
			        	
			        	int prevday=0;
			    		int prevmonth=0;
			    		int preyear=0;
			        	int tempfiledateday=0;
			        	 date=new  HRApprovalForm();
			          
			 
			            tempfiledateday=filedateday-1;
			            if(tempfiledateday==0)
			    		{
		            		
		            	
		            	 mycal = Calendar.getInstance();
		    			if(filedateday==Integer.parseInt(filedatemonth))
		    			{
		    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=12;
		    			preyear=Integer.parseInt(filedateyear)-1;
		    	
		    			}
		    			else
		    			{
		    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
		    			prevmonth=Integer.parseInt(filedatemonth)-1;
		    			preyear=Integer.parseInt(filedateyear);
		    	
		    			}
		    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    			tempfiledateday=daysInMonth;
		    		
		            	
		    		}
			    		else
			    		{
			    			tempfiledateday=tempfiledateday;	
			    			prevmonth=Integer.parseInt(filedatemonth);
			    			preyear=Integer.parseInt(filedateyear);
			    		}
			            
			            
			            intime.setId("Day"+tempfiledateday);	          
				         intime.setTime(actualpunch);
				         intime.setFrommonth(Integer.toString(prevmonth));
				         intime.setCalyear(Integer.toString(preyear));
				         intime.setEmployeeNo(empno);
			        }
			        }
			        
			        
			        
			        ///punch valid
			        
			     if(prevpunchvalid.compareTo(curshftstart)<=0)
			     {
			         if(punchtime.compareTo("00:00")>=0 && punchtime.compareTo(prevpunchvalid)<=0)
			         {

				        	int prevday=0;
				    		int prevmonth=0;
				    		int preyear=0;
				        	int tempfiledateday=0;
				        	
				        	 if(!pernrs.contains(empno+"O"))
				                {
					            	
					            
				 
				            intime.setStatus("O");
				            
				            actualpunch=punchtime;
				            if(curshftend.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				            	curshftend=Integer.toString(Integer.parseInt(curshftend.substring(0, 2))+24)+curshftend.substring(2, 5);
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				            
				            if(punchtime.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				         
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				            if(punchtime.compareTo(curshftend)<0)
					        {
					        	
					        	 intime.setMessage("S");
					        
					        }
				            else
				            {
				            intime.setMessage("");
				            }
				           
				            punchtime=actualpunch;
				            tempfiledateday=filedateday-1;
				            if(tempfiledateday==0)
				    		{
				            		
				            	
				            	mycal = Calendar.getInstance();
				    			if(filedateday==Integer.parseInt(filedatemonth))
				    			{
				    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=12;
				    			preyear=Integer.parseInt(filedateyear)-1;
				    	
				    			}
				    			else
				    			{
				    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=Integer.parseInt(filedatemonth)-1;
				    			preyear=Integer.parseInt(filedateyear);
				    		
				    			}
				    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
				    			tempfiledateday=daysInMonth;
				    		
				            	
				    		}
				    		else
				    		{
				    			tempfiledateday=tempfiledateday;	
				    			prevmonth=Integer.parseInt(filedatemonth);
				    			preyear=Integer.parseInt(filedateyear);
				    		} 
			         
				        
				            intime.setId("Day"+tempfiledateday);	          
					         intime.setTime(actualpunch);
					         intime.setFrommonth(Integer.toString(prevmonth));
					         intime.setCalyear(Integer.toString(preyear));
					         intime.setEmployeeNo(empno);
			         }  
				        
			         }
	           }
			         pernrs=pernrs+","+empno+intime.getStatus();
			         
			         
			         
			         //////TS or SS1combined with FS
				        
				        if(prevshftend.equalsIgnoreCase(curshftstart))
				        {


				        	int prevday=0;
				    		int prevmonth=0;
				    		int preyear=0;
				        	int tempfiledateday=0;
				 
				        	 actualpunch="";
							 
				            intime.setStatus("O");
				            actualpunch=punchtime;
				            if(prevshftend.substring(0, 2).equalsIgnoreCase("00"))
				        	   {
				            	prevshftend=Integer.toString(Integer.parseInt(curshftend.substring(0, 2))+24)+curshftend.substring(2, 5);
				        		  punchtime=Integer.toString(Integer.parseInt(punchtime.substring(0, 2))+24)+punchtime.substring(2, 5);
				        		  
				        	   }
				           
				            intime.setMessage("");
				            
				           
				            punchtime=actualpunch;
				           
				            tempfiledateday=filedateday-1;
				            if(tempfiledateday==0)
				    		{
				            		
				            	
				            	mycal = Calendar.getInstance();
				    			if(filedateday==Integer.parseInt(filedatemonth))
				    			{
				    			mycal.set(Integer.parseInt(filedateyear)-1, Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=12;
				    			preyear=Integer.parseInt(filedateyear)-1;
				    	
				    			}
				    			else
				    			{
				    			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
				    			prevmonth=Integer.parseInt(filedatemonth)-1;
				    			preyear=Integer.parseInt(filedateyear);
				    		
				    			}
				    			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
				    			tempfiledateday=daysInMonth;
				    		
				            	
				    		}
				    		else
				    		{
				    			tempfiledateday=tempfiledateday;	
				    			prevmonth=Integer.parseInt(filedatemonth);
				    			preyear=Integer.parseInt(filedateyear);
				    		} 
				            intime.setId("Day"+tempfiledateday);	          
					         intime.setTime(punchtime);
					         intime.setFrommonth(Integer.toString(prevmonth));
					         intime.setCalyear(Integer.toString(preyear));
					         intime.setEmployeeNo(empno);
				          
				        
			         
				        }
			
	           }
		
			
	           if(intime.getStatus()==null)
	           {
	        	   intime.setStatus("");
	        	   
	           }
	           if(intime.getStatus().equalsIgnoreCase("") )
	           {
	        	   
	        	   miss.add(intime);
	           }
	           else
	           {
	        	   
	        	   
				    in.add(intime);
	           }
				
				    pernrs=pernrs+","+empno+intime.getStatus();
				    
				   
				  
				    }
				  rssempoffr.close();
				}
			      
		          /*if(!emptyshiftmaster.equalsIgnoreCase(""))
		          {
		         LinkedList abc= getofficaldetails(emptyshiftmaster.substring(1, emptyshiftmaster.length()),request);
		          }*/
		          
		          
				 //insert into transaction
				    
				  	String quer3="insert into ATTENDANCE_FILE_Upload_Tran values('"+filepath+"','"+cmpcode+"','"+user_empno+"',getdate())";
				    int a3=hrad.ad.SqlExecuteUpdate(quer3);
				   
				}
				
				 aqq ="  update  Attendance_Process_log set fetch_et=getdate(),leave_st=getdate() "
							+ " where lock_id ='"+datecurren11t+"' and (fetch_et is  null or fetch_et='')";
						kff11=hrad.ad.SqlExecuteUpdate(aqq); 
			
				///leaves onduty
				
				String lV_t="SELECT * FROM dbo.ExplodeDates('"+sqlfromdate+"','"+sqltodat+"') as thedate";
			    ResultSet rslV_t=hrad.ad.selectQuery(lV_t);
			    try {
					while(rslV_t.next())
					{
						
						String b[]=rslV_t.getString("thedate").split("-");
						
						String newfiledate= rslV_t.getString("thedate");
						String newfiledateday=b[2];
						int ondutyfiledateday=Integer.parseInt(b[2]);
						String newfiledatemonth=b[1];
						String newfiledateyear=b[0];
						 //leave 
				          leave=hrad.getempleave(newfiledate,ACtualemps, newfiledatemonth, newfiledateday,cmpcode);
				          allleave.addAll(leave);
				  	 
				         //onduty
				          onduty=hrad.getemponduty(newfiledate, ACtualemps, ondutyfiledateday,newfiledatemonth, newfiledateyear,cmpcode);
				          allonduty.addAll(onduty);
						
					}
			    }
		     catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			    
			    aqq ="  update  Attendance_Process_log set leave_et=getdate(),punch_st=getdate(),progress=30 "
						+ " where lock_id ='"+datecurren11t+"' and (leave_et is  null or leave_et='')";
					kff11=hrad.ad.SqlExecuteUpdate(aqq);
			    
					
					
					
			    //update punch records
					hrad. updateAttendanceDetails(in,datelist);
				  
				  	  
				   aqq ="  update  Attendance_Process_log set punch_et=getdate(),manual_st=getdate(),progress=50 "
							+ " where lock_id ='"+datecurren11t+"' and (punch_et is  null or punch_et='')";
						kff11=hrad.ad.SqlExecuteUpdate(aqq);
				  
				  if(miss.size()>0)
					  hrad.updateMissingAttendanceDetails(miss);
				    
				
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar cal1 = Calendar.getInstance();							
					String datecurrent=dateFormat.format(cal1.getTime());
					
					
					String trane1="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
							+ "values('"+user_empno+"','"+user_empname+"','"+locid+"','"+sqlfromdate+"','"+sqltodat+"','"+ACtualemps+"','1')";
					int je31=hrad.ad.SqlExecuteUpdate(trane1);
				////insert empNos
					String insert="EXEC EMP_Split '"+(ACtualemps.replace(",", "$")).replace("'", "")+"','"+datecurrent+"' ";
					int bqb=hrad.ad.SqlExecuteUpdate(insert);	
			
				  
				
					
					String rane1="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
							+ "values('"+user_empno+"','"+user_empname+"','"+locid+"','"+sqlfromdate+"','"+sqltodat+"','"+ACtualemps+"','2')";
					int ju31=hrad.ad.SqlExecuteUpdate(rane1);
					
					
					String monName="";
			          
			        
					
					  
					  String man="SELECT * FROM dbo.ExplodeDates('"+sqlfromdate+"','"+sqltodat+"') as thedate";
					    ResultSet rsman=hrad.ad.selectQuery(man);
					    try {
							while(rsman.next())
							{
								
								String b[]=rsman.getString("thedate").split("-");
								
								String newfiledate= rsman.getString("thedate");
								int newfiledateday=Integer.parseInt(b[2]);
								String newfiledatemonth=b[1];
								String newfiledateyear=b[0];
									  ///late count update 
								//Check emp_inout status latecount flag it shoul be less than 3
								
								String manua="insert into  EMP_MANUAL_SWIPE(Pernr,Start_date,In_Out,Start,Lost_Entry_Reason_Type,Remarks,late_flag,Status_Flag)"
								+ "select EMP_IN_OUT.pernr,'"+newfiledate+"','I',EMP_IN_OUT.Day"+newfiledateday+",'Late','System Generated','1','1' from EMP_IN_OUT,EMP_IN_OUT_Status,EMP_Shift_Register,"
								+ "EMP_SHIFT_MASTER,emp_official_info where EMP_IN_OUT.Day"+newfiledateday+">EMP_SHIFT_MASTER.Shift_start_time and EMP_IN_OUT.day"+newfiledateday+"<=EMP_SHIFT_MASTER.Come_Late and  "
								+ "EMP_IN_OUT.Month="+newfiledatemonth+" and In_Out='I' and EMP_IN_OUT.year='"+newfiledateyear+"'and EMP_IN_OUT_Status.Pernr=EMP_IN_OUT.Pernr and "
								+ "EMP_IN_OUT_Status.Month=EMP_IN_OUT.Month and EMP_IN_OUT_Status.Year=EMP_IN_OUT.Year and Late_count<(select Late_count from Location where LOCATION_CODE='"+cmpcode+"') and "
								+ "EMP_Shift_Register.Pernr=EMP_IN_OUT.Pernr and EMP_Shift_Register.Month=EMP_IN_OUT.Month and EMP_Shift_Register.Year=EMP_IN_OUT.Year   "
								+ "and EMP_Shift_Register.Day"+newfiledateday+"=EMP_SHIFT_MASTER.Shift_code and emp_official_info.PERNR=EMP_IN_OUT_Status.Pernr and LOCID='"+cmpcode+"' and  left(EMP_IN_OUT_Status.day"+newfiledateday+",2)='AA'";						
								int ab=hrad.ad.SqlExecuteUpdate(manua);
								
								
											
								
								String late="update EMP_IN_OUT_Status set day"+newfiledateday+"='PP'+RIGHT(Day"+newfiledateday+",2),Late_count=Late_count+1 where left(day"+newfiledateday+",2)='AA' and Pernr in ("
										+ "select EMP_IN_OUT.pernr from EMP_IN_OUT,EMP_IN_OUT_Status,EMP_Shift_Register,"
										+ "EMP_SHIFT_MASTER,emp_official_info where EMP_IN_OUT.Day"+newfiledateday+">EMP_SHIFT_MASTER.Shift_start_time and EMP_IN_OUT.day"+newfiledateday+"<=EMP_SHIFT_MASTER.Come_Late and  "
										+ "EMP_IN_OUT.Month="+newfiledatemonth+" and In_Out='I' and EMP_IN_OUT.year='"+newfiledateyear+"'and EMP_IN_OUT_Status.Pernr=EMP_IN_OUT.Pernr and "
										+ "EMP_IN_OUT_Status.Month=EMP_IN_OUT.Month and EMP_IN_OUT_Status.Year=EMP_IN_OUT.Year and Late_count<(select Late_count from Location where LOCATION_CODE='"+cmpcode+"') and "
										+ "EMP_Shift_Register.Pernr=EMP_IN_OUT.Pernr and EMP_Shift_Register.Month=EMP_IN_OUT.Month and EMP_Shift_Register.Year=EMP_IN_OUT.Year   "
										+ "and EMP_Shift_Register.Day"+newfiledateday+"=EMP_SHIFT_MASTER.Shift_code and emp_official_info.PERNR=EMP_IN_OUT_Status.Pernr and LOCID='"+cmpcode+"' and left(EMP_IN_OUT_Status.day"+newfiledateday+",2)='AA')and Month="+newfiledatemonth+" and Year='"+newfiledateyear+"'";						
								int ab1=hrad.ad.SqlExecuteUpdate(late);	
								
								
								  String requesry="select distinct EMP_MANUAL_SWIPE.Start,EMP_IN_OUT.Pernr,EMP_IN_OUT.Day"+newfiledateday+" as d from"
											+ " EMP_MANUAL_SWIPE,EMP_IN_OUT,EMP_SHIFT_LATE_RULES,EMP_Shift_Register where Start_date =  '"+newfiledate+"' "
											+ "and EMP_IN_OUT.In_Out=EMP_MANUAL_SWIPE.In_Out and EMP_MANUAL_SWIPE.Pernr=EMP_IN_OUT.Pernr and EMP_MANUAL_SWIPE.In_Out='I' "
											+ "and EMP_IN_OUT.Month=EMP_Shift_Register.Month and EMP_MANUAL_SWIPE.Lost_Entry_Reason_Type!='early' and EMP_IN_OUT.day"+newfiledateday+"!=start and "
											+ "EMP_IN_OUT.day"+newfiledateday+">Come_Late_by  and EMP_IN_OUT.day"+newfiledateday+">'00:00:00'  and EMP_Shift_Register.day"+newfiledateday+"=EMP_SHIFT_LATE_RULES.Rule_Code and"
											+ " EMP_Shift_Register.Pernr=EMP_IN_OUT.Pernr and  EMP_IN_OUT.Month="+newfiledatemonth+" and EMP_IN_OUT.Year='"+newfiledateyear+"'";
											
											ResultSet rsa=hrad.ad.selectQuery(requesry);
											while(rsa.next())
											{
											String up1="update EMP_IN_OUT set Day"+newfiledateday+"='"+rsa.getString("Start")+"' where Month="+newfiledatemonth+" and Year='"+newfiledateyear+"' and Pernr='"+rsa.getString("pernr")+"' and In_Out='I'";
											int ab5=hrad.ad.SqlExecuteUpdate(up1);
											
											String up2="update EMP_IN_OUT set Day"+newfiledateday+"='"+rsa.getString("d")+"' where Month="+newfiledatemonth+" and Year='"+newfiledateyear+"' and Pernr='"+rsa.getString("pernr")+"' and In_Out='O' and Day"+newfiledateday+"='00:00:00' ";
											int ab16=hrad.ad.SqlExecuteUpdate(up2);
													
											}
												
											rsa.close();
								
	                          //override all record which are in manual entry
								
								String overide="update EMP_IN_OUT set day"+newfiledateday+"=Start from EMP_MANUAL_SWIPE where EMP_MANUAL_SWIPE.In_Out=EMP_IN_OUT.In_Out and  EMP_IN_OUT.Month="+newfiledatemonth+" and EMP_IN_OUT.Year='"+newfiledateyear+"' and EMP_MANUAL_SWIPE.pernr=EMP_IN_OUT.Pernr and EMP_IN_OUT.Pernr in ("+ACtualemps+") and Start_date='"+rsman.getString("thedate")+"' and Status_Flag='1'";
								int ov=hrad.ad.SqlExecuteUpdate(overide);
								
								
								//PP i nempp_status manual swipe overide(I)
								String overidestatus="update EMP_IN_OUT_Status set day"+newfiledateday+"= 'PP'+RIGHT(day"+newfiledateday+",2) from EMP_MANUAL_SWIPE where   EMP_IN_OUT_Status.Month="+newfiledatemonth+" and EMP_IN_OUT_Status.Year='"+newfiledateyear+"' and EMP_MANUAL_SWIPE.pernr=EMP_IN_OUT_Status.Pernr and EMP_IN_OUT_Status.Pernr in ("+ACtualemps+") and Start_date='"+rsman.getString("thedate")+"' and Status_Flag='1' and Lost_Entry_Reason_Type in ('Forgot Swipe','Missing','Late','Special Approval') and EMP_MANUAL_SWIPE.In_Out='I'";
								int ove1=hrad.ad.SqlExecuteUpdate(overidestatus);
								
								//PP i nempp_status manual swipe overide (O)
								String wer="update EMP_IN_OUT_Status set day"+newfiledateday+"=LEFT(day"+newfiledateday+",2)+'PP'  from EMP_MANUAL_SWIPE where   EMP_IN_OUT_Status.Month="+newfiledatemonth+" and EMP_IN_OUT_Status.Year='"+newfiledateyear+"' and EMP_MANUAL_SWIPE.pernr=EMP_IN_OUT_Status.Pernr and EMP_IN_OUT_Status.Pernr in ("+ACtualemps+") and Start_date='"+rsman.getString("thedate")+"' and Status_Flag='1' and Lost_Entry_Reason_Type in ('Forgot Swipe','Missing','Late','Special Approval') and EMP_MANUAL_SWIPE.In_Out='O'";
								int ovefg1=hrad.ad.SqlExecuteUpdate(wer);
								
								//PM i nempp_status manual swipe overide
								String overidestatu1s="update EMP_IN_OUT_Status set day"+newfiledateday+"= 'PM'+RIGHT(day"+newfiledateday+",2)  from EMP_MANUAL_SWIPE where  EMP_IN_OUT_Status.Month="+newfiledatemonth+" and EMP_IN_OUT_Status.Year='"+newfiledateyear+"' and EMP_MANUAL_SWIPE.pernr=EMP_IN_OUT_Status.Pernr and EMP_IN_OUT_Status.Pernr in ("+ACtualemps+") and Start_date='"+rsman.getString("thedate")+"' and Status_Flag='1' and Lost_Entry_Reason_Type in ('Early') and EMP_MANUAL_SWIPE.In_Out='I'";
								int ove11=hrad.ad.SqlExecuteUpdate(overidestatu1s);
								
								//PM i nempp_status manual swipe overide
								String wersfd="update EMP_IN_OUT_Status set day"+newfiledateday+"= LEFT(day"+newfiledateday+",2)+'PM'  from EMP_MANUAL_SWIPE where  EMP_IN_OUT_Status.Month="+newfiledatemonth+" and EMP_IN_OUT_Status.Year='"+newfiledateyear+"' and EMP_MANUAL_SWIPE.pernr=EMP_IN_OUT_Status.Pernr and EMP_IN_OUT_Status.Pernr in ("+ACtualemps+") and Start_date='"+rsman.getString("thedate")+"' and Status_Flag='1' and Lost_Entry_Reason_Type in ('Early') and EMP_MANUAL_SWIPE.In_Out='O'";
								int fgfg=hrad.ad.SqlExecuteUpdate(wersfd);
								
								//AA i nempp_status manual swipe overide
								String overidestatu3s="update EMP_IN_OUT_Status set day"+newfiledateday+"='AA'+RIGHT(day"+newfiledateday+",2)  from EMP_MANUAL_SWIPE where   EMP_IN_OUT_Status.Month="+newfiledatemonth+" and EMP_IN_OUT_Status.Year='"+newfiledateyear+"' and EMP_MANUAL_SWIPE.pernr=EMP_IN_OUT_Status.Pernr and EMP_IN_OUT_Status.Pernr in ("+ACtualemps+") and Start_date='"+rsman.getString("thedate")+"' and Status_Flag='1' and Lost_Entry_Reason_Type in ('Absent') and EMP_MANUAL_SWIPE.In_Out='I'";
								int ove12=hrad.ad.SqlExecuteUpdate(overidestatu3s);
								
								
								//AA i nempp_status manual swipe overide
								String tyrt="update EMP_IN_OUT_Status set day"+newfiledateday+"= LEFT(day"+newfiledateday+",2)+'AA'  from EMP_MANUAL_SWIPE where   EMP_IN_OUT_Status.Month="+newfiledatemonth+" and EMP_IN_OUT_Status.Year='"+newfiledateyear+"' and EMP_MANUAL_SWIPE.pernr=EMP_IN_OUT_Status.Pernr and EMP_IN_OUT_Status.Pernr in ("+ACtualemps+") and Start_date='"+rsman.getString("thedate")+"' and Status_Flag='1' and Lost_Entry_Reason_Type in ('Absent') and EMP_MANUAL_SWIPE.In_Out='O'";
								int gds=hrad.ad.SqlExecuteUpdate(tyrt);
								
								
								/////restore sum of manual entry count ot empstatus
								
								
								 String q2tf="EXEC Restorelatecount '"+sqlfromdate+"','"+sqltodat+"','"+ACtualemps.replace("'", "")+"' ";
									int kfhf1=hrad.ad.SqlExecuteUpdate(q2tf);
									
									
									
									
									
									///compoff
							          compff=hrad.getcompoff(rsman.getString("thedate"), ACtualemps, filedateday,filedatemonth, filedateyear,cmpcode);
							          allcompff.addAll(compff);
									  ///manual entry update for 1st swip in OUT status
								
									
								
								
							
								
								  
							}
					  
							
							
					    } catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					  
				
					    rsman.close();
					
					    
					    aqq ="  update  Attendance_Process_log set manual_et=getdate(),week_st=getdate(),progress=60 "
								+ " where lock_id ='"+datecurren11t+"' and (manual_et is  null or manual_et='')";
							kff11=hrad.ad.SqlExecuteUpdate(aqq);
					    
					    
	 ///update working calendar
			        	
			        	
			        	Iterator daylist=caldatelist.iterator();
			        	 while(daylist.hasNext()) {
			        		 
			        	 HRApprovalForm abc=(HRApprovalForm)daylist.next();
			        		   
			        		   
			        		if (abc.getMonth().equals("1"))
			   	        		monName = "Jan";
			   	        	if (abc.getMonth().equals("2"))
			   	        		monName = "Feb";
			   	        	if (abc.getMonth().equals("3"))
			   	        		monName = "Mar";
			   	        	if (abc.getMonth().equals("4"))
			   	        		monName = "Apr";
			   	        	if (abc.getMonth().equals("5"))
			   	        		monName = "May";
			   	        	if (abc.getMonth().equals("6"))
			   	        		monName = "Jun";
			   	        	if (abc.getMonth().equals("7"))
			   	        		monName = "Jul";
			   	        	if (abc.getMonth().equals("8"))
			   	        		monName = "Aug";
			   	        	if (abc.getMonth().equals("9"))
			   	        		monName = "Sep";
			   	        	if (abc.getMonth().equals("10"))
			   	        		monName = "Oct";
			   	        	if (abc.getMonth().equals("11"))
			   	        		monName = "Nov";
			   	        	if (abc.getMonth().equals("12"))
			   	        		monName = "Dec";
			   			
			        		 
			        		 //emp cal list
			        		   String WOempcal="";
			        		   String SSempcal="";
			        		   String PHempcal="";
			        		   
			        		   //WO
			        		   String checkWOempcal="select distinct pernr from  emp_cal where DAY="+abc.getDay()+" and "+monName+"='WO' and CYEAR='"+abc.getYear()+"' and pernr in ("+ACtualemps+" )";
			        		   ResultSet WOrsempcal = hrad.ad.selectQuery(checkWOempcal);
			        		   while(WOrsempcal.next())
			        		   {
			        			   WOempcal=WOempcal+","+WOrsempcal.getString("pernr");
			        		   }
			        		   WOrsempcal.close();
			        	 if(!WOempcal.equalsIgnoreCase(""))
			        		 WOempcal=WOempcal.substring(1, WOempcal.length());
			        	 
			        	 
			        	 //SS
			        	   String checkSSempcal="select distinct pernr from  emp_cal where DAY="+abc.getDay()+" and "+monName+"='SS' and CYEAR='"+abc.getYear()+"' and pernr in ("+ACtualemps+" )";
		        		   ResultSet SSrsempcal = hrad.ad.selectQuery(checkSSempcal);
		        		   while(SSrsempcal.next())
		        		   {
		        			   SSempcal=SSempcal+","+SSrsempcal.getString("pernr");
		        		   }
		        		   SSrsempcal.close();
		        	 if(!SSempcal.equalsIgnoreCase(""))
		        		 SSempcal=SSempcal.substring(1, SSempcal.length());
		        	 
		        	 
		        	 //PH
		        	   String checkPHempcal="select distinct pernr from  emp_cal where DAY="+abc.getDay()+" and "+monName+"='PH' and CYEAR='"+abc.getYear()+"' and pernr in ("+ACtualemps+" )";
	        		   ResultSet PHrsempcal = hrad.ad.selectQuery(checkPHempcal);
	        		   while(PHrsempcal.next())
	        		   {
	        			   PHempcal=PHempcal+","+PHrsempcal.getString("pernr");
	        		   }
	        		   PHrsempcal.close();
	        	 if(!PHempcal.equalsIgnoreCase(""))
	        		 PHempcal=PHempcal.substring(1, PHempcal.length());	 
			        	 
			        	 //emp cal list
		      		   String allempcal="";
		      		   String allcheckempcal="select distinct pernr from  emp_cal where  CYEAR='"+abc.getYear()+"' and pernr in ("+ACtualemps+" )";
		      		   ResultSet allrsempcal = hrad.ad.selectQuery(allcheckempcal);
		      		   while(allrsempcal.next())
		      		   {
		      			 allempcal=allempcal+","+allrsempcal.getString("pernr");
		      		   }
		      		   allrsempcal.close();
		      	 if(!allempcal.equalsIgnoreCase(""))
		      		allempcal=allempcal.substring(1, allempcal.length());
			        	
			        	String check="select "+monName+" from "+hrad.getTableName(cmpcode)+" where DAY="+abc.getDay()+" and  CYEAR='"+abc.getYear()+"'";
			        	ResultSet rs = hrad.ad.selectQuery(check);
			        	if(rs.next())
			        	{
			        		if(!rs.getString(1).equalsIgnoreCase("W"))
			        		{
			    	        	if(rs.getString(1).equalsIgnoreCase("PH")||rs.getString(1).equalsIgnoreCase("SH")||rs.getString(1).equalsIgnoreCase("SS"))
			    	        	{
			    	        		
			    	        		String prevvalue="";
			    	        		String predate="";
			    	        		String preMont="";
			    	        		String preYEAR="";
			    	        		
			    	        		String nextvalue="";
			    	        		String nextdate="";
			    	        		String nextMont="";
			    	        		String nextYEAR="";
			    	        		
			    	        		
			    	        		int startcount=1;
			    	        		do{
			    	        		 String[] reqmonth = {"","JAN", "FEB",
			    	       				  "MAR", "APR", "MAY", "JUN", "JUL",
			    	       				  "AUG", "SEP", "OCT", "NOV",
			    	       				  "DEC"
			    	       				  };
			    	        		String previday="";
									String previmonth="";
									String previyear="";
									
									
									
			    	        		
			    	        		String prwrk="Select day(DATEADD(DD, DATEDIFF(DY, 0, '"+abc.getYear()+"-"+abc.getMonth()+"-"+abc.getDay()+"'), -"+startcount+")) as prevday , month(DATEADD(DD, DATEDIFF(DY, 0, '"+abc.getYear()+"-"+abc.getMonth()+"-"+abc.getDay()+"'), -"+startcount+")) as prevmon,year(DATEADD(DD, DATEDIFF(DY, 0, '"+abc.getYear()+"-"+abc.getMonth()+"-"+abc.getDay()+"'), -"+startcount+")) as prevyear";
			    	        		  ResultSet rsprwrk=hrad.ad.selectQuery(prwrk);
			    					  while(rsprwrk.next())	    						 
			    						{
			    						  previday=rsprwrk.getString("prevday");
			    						  previmonth=rsprwrk.getString("prevmon");
			    						  previyear=rsprwrk.getString("prevyear");
			    						  
			    						  preMont=previmonth;
			    						  preYEAR=previyear;
			    						}
			    					  
			    					  rsprwrk.close();
			    	        		//get prev and next working day
			    	        	
			    	        		String nextwrk="select "+reqmonth[Integer.parseInt(previmonth)]+" as m,DAY from "+hrad.getTableName(cmpcode)+" where DAY="+previday+" and  CYEAR='"+previyear+"'";
			    	        		  ResultSet rstable=hrad.ad.selectQuery(nextwrk);
			    					  while(rstable.next())	    						 
			    						{
			    						  prevvalue=rstable.getString("m");
			    						  predate=rstable.getString("DAY");
			    						}
	                                   rstable.close();
			    					  startcount=startcount+1;
			    	        		
			    	        	}while(!prevvalue.equalsIgnoreCase("W"));
			    	        		
			    	        		int endcount=1;
			    	        		do{
				    	        		 String[] reqmonth = {"","JAN", "FEB",
				    	       				  "MAR", "APR", "MAY", "JUN", "JUL",
				    	       				  "AUG", "SEP", "OCT", "NOV",
				    	       				  "DEC"
				    	       				  };
				    	        
										
										String nextday="";
										String nextdaymonth="";
										String nextyear="";
										
									
				    	        		
										String prwrk="Select day(DATEADD(DD, DATEDIFF(DY, 0, '"+abc.getYear()+"-"+abc.getMonth()+"-"+abc.getDay()+"'), "+endcount+")) as nextday , month(DATEADD(DD, DATEDIFF(DY, 0, '"+abc.getYear()+"-"+abc.getMonth()+"-"+abc.getDay()+"'),"+endcount+" )) as nextmon,year(DATEADD(DD, DATEDIFF(DY, 0, '"+abc.getYear()+"-"+abc.getMonth()+"-"+abc.getDay()+"'), "+endcount+")) as nextyear";
				    	        		  ResultSet rsprwrk=hrad.ad.selectQuery(prwrk);
				    					  while(rsprwrk.next())	    						 
				    						{
				    						  nextday=rsprwrk.getString("nextday");
				    						  nextdaymonth=rsprwrk.getString("nextmon");
				    						  nextyear=rsprwrk.getString("nextyear");
				    						  
				    						  nextMont=nextdaymonth;
				    						  nextYEAR=nextyear;
				    						}
				    					  rsprwrk.close();
				    					  
				    	        		//get prev and next working day
				    	        	
				    	        		String nextwrk="select "+reqmonth[Integer.parseInt(nextdaymonth)]+" as m,DAY from "+hrad.getTableName(cmpcode)+" where DAY="+nextday+" and  CYEAR='"+nextyear+"'";
				    	        		  ResultSet rstable=hrad.ad.selectQuery(nextwrk);
				    					  while(rstable.next())	    						 
				    						{
				    						  nextvalue=rstable.getString("m");
				    						  nextdate=rstable.getString("DAY");
				    						}
				    					  rstable.close();
				    					  
				    					  endcount=endcount+1;
				    	        		
				    	        	}while(!nextvalue.equalsIgnoreCase("W"));
							 
		  	        	
			    	        	
			    	        		
			    	        			    	        		
			    	        		String cal="update EMP_IN_OUT_Status set day"+abc.getDay()+"='"+rs.getString(1)+""+rs.getString(1)+"' where Month='"+abc.getMonth()+"' and Year='"+abc.getYear()+"' and Pernr in"
			    	        				+ " (select  pernr from EMP_IN_OUT_Status where month= '"+preMont+"' and Year= '"+preYEAR+"' and RIGHT(day"+predate+",2) not in ('AA','CL','SL','NL') and pernr in  "
			    	        			+ "(select pernr from Temp_Emp where Date_Time='"+datecurrent+"') union select  pernr from EMP_IN_OUT_Status where month= '"+nextMont+"' and Year= '"+nextYEAR+"'  and"
			    	        			+ " left(Day"+nextdate+",2) not in ('AA','CL','SL','NL') and pernr in ( select pernr from Temp_Emp where Date_Time='"+datecurrent+"'))";
				        	          int a=hrad.ad.SqlExecuteUpdate(cal)	;  
				        	          
				        	          
				        	        
			    	        	}
			    	        	else
			    	        	{
			    	        		if(allempcal.equalsIgnoreCase(""))
			    	        		{
			    	        		 String cal="update EMP_IN_OUT_Status set day"+abc.getDay()+"='"+rs.getString(1)+""+rs.getString(1)+"' where Month='"+abc.getMonth()+"' and Year='"+abc.getYear()+"' and Pernr in ("+ACtualemps+" )";
				        	          int a=hrad.ad.SqlExecuteUpdate(cal)	;   
			    	        		}
			    	        		else
			    	        		{
			    	        			String cal="update EMP_IN_OUT_Status set day"+abc.getDay()+"='"+rs.getString(1)+""+rs.getString(1)+"' where Month='"+abc.getMonth()+"' and Year='"+abc.getYear()+"' and pernr not in ("+allempcal+") and Pernr in ("+ACtualemps+" )";
					        	          int a=hrad.ad.SqlExecuteUpdate(cal)	;  
			    	        		}
			    	        	}
			        		}
			        	}
			        	
			        	rs.close();
			        	if(!WOempcal.equalsIgnoreCase(""))
			        	{
			        		String cal="update EMP_IN_OUT_Status set day"+abc.getDay()+"='WOWO' where Month='"+abc.getMonth()+"' and Year='"+abc.getYear()+"' and Pernr in ("+WOempcal+" )";
			        	           int a=hrad.ad.SqlExecuteUpdate(cal)	; 
			        			
			        	}
			        	if(!SSempcal.equalsIgnoreCase(""))
			        	{
			        		String cal="update EMP_IN_OUT_Status set day"+abc.getDay()+"='SSSS' where Month='"+abc.getMonth()+"' and Year='"+abc.getYear()+"' and Pernr in ("+SSempcal+" )";
			        	           int a=hrad.ad.SqlExecuteUpdate(cal)	; 
			        			
			        	}
			        	if(!PHempcal.equalsIgnoreCase(""))
			        	{
			        		String cal="update EMP_IN_OUT_Status set day"+abc.getDay()+"='PHPH' where Month='"+abc.getMonth()+"' and Year='"+abc.getYear()+"' and Pernr in ("+PHempcal+" )";
			        	           int a=hrad.ad.SqlExecuteUpdate(cal)	; 
			        			
			        	}
			        	
			        	 }
			        	 
			        
			        	 //weekoff reduction
			        	 
			        	 
			        	 String man1="SELECT * FROM dbo.ExplodeDates('"+sqlfromdate+"','"+sqltodat+"') as thedate";
						    ResultSet rsman1=hrad.ad.selectQuery(man1);
						    try {
								while(rsman1.next())
								{
									String b[]=rsman1.getString("thedate").split("-");
									
									String newfiledate= rsman1.getString("thedate");
									int newfiledateday=Integer.parseInt(b[2]);
									String newfiledatemonth=b[1];
									String newfiledateyear=b[0];
									
									String lastbutoneday="";
									String lastbutonemonth="";
									String lastbutoneyear="";
									
									String previday="";
									String previmonth="";
									String previyear="";
									
									String currday="";
									String currmonth="";
									String curryear="";
									
									String nextday="";
									String nextdaymonth="";
									String nextyear="";
									
									String days="Select day(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), -2)) as lastbutoneday , month(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), -2)) as lastbutonemonth,year(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), -2)) as lastbutoneyear,"
											+ " day(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), -1)) as prevday , month(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), -1)) as prevmonth,year(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), -1)) as prevyear,"
											+ "day(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), 0)) as curday , month(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), 0)) as curmonth,year(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), 0)) as curyear, "
											+ "day(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), 1)) as nextday , month(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), 1)) as nextmonth,year(DATEADD(DD, DATEDIFF(DY, 0, '"+newfiledate+"'), 1)) as nextyear";
									ResultSet rs=hrad.ad.selectQuery(days);
									if(rs.next())
									{
										lastbutoneday=rs.getString("lastbutoneday");
										lastbutonemonth=rs.getString("lastbutonemonth");
										lastbutoneyear=rs.getString("lastbutoneyear");
										
										previday=rs.getString("prevday");
										previmonth=rs.getString("prevmonth");
										previyear=rs.getString("prevyear");
										
										currday=rs.getString("curday");
										currmonth=rs.getString("curmonth");
										curryear=rs.getString("curyear");
										
										 nextday=rs.getString("nextday");
										 nextdaymonth=rs.getString("nextmonth");
										 nextyear=rs.getString("nextyear");
										
										
									}
									
									
									rs.close();
									if(previmonth.equalsIgnoreCase(currmonth))
									{
										String same="update EMP_IN_OUT_Status set Day"+newfiledateday+"=t.Output from (select * from EMP_IN_OUT_Status,WeekOff_Matrix where  "
					+ "(right(Day"+previday+",2) like WeekOff_Matrix.Value1 and Day"+newfiledateday+" = WeekOff_Matrix.Value2 and left(Day"+nextday+",2) like WeekOff_Matrix.Value3)and Month='"+currmonth+"' and year='"+curryear+"'"
					+ " and Pernr in (select pernr from Temp_Emp where Date_Time='"+datecurrent+"'))t where  EMP_IN_OUT_Status.Pernr=t.Pernr and EMP_IN_OUT_Status.Pernr in (select pernr from Temp_Emp where Date_Time='"+datecurrent+"') and EMP_IN_OUT_Status.Month='"+currmonth+"' and EMP_IN_OUT_Status.year='"+curryear+"'";
										int aa=hrad.ad.SqlExecuteUpdate(same);
										
										
										//sec sat
										
										
										String secsat="update EMP_IN_OUT_Status set Day"+previday+"=t.Output,Day"+newfiledateday+"=t.Output from "
								+ "(select * from EMP_IN_OUT_Status,WeekOff_Matrix where(right(Day"+lastbutoneday+",2) like WeekOff_Matrix.Value1 and Day"+previday+" = WeekOff_Matrix.Value4 and Day"+newfiledateday+" = WeekOff_Matrix.Value2 and left(Day"+nextday+",2)"
								+ " like WeekOff_Matrix.Value3)and Month='"+currmonth+"' and year='"+curryear+"' and Pernr in ("+ACtualemps+"))t where EMP_IN_OUT_Status.Pernr=t.Pernr and  EMP_IN_OUT_Status.Pernr in (select pernr from Temp_Emp where Date_Time='"+datecurrent+"') and EMP_IN_OUT_Status.Month='"+currmonth+"' and EMP_IN_OUT_Status.year='"+curryear+"'";					
										int cc=hrad.ad.SqlExecuteUpdate(secsat);
									}
									
									
									if(previmonth.compareTo(currmonth)<0)
									{
										String prev="update EMP_IN_OUT_Status set Day"+newfiledateday+"=WeekOff_Matrix.Output from(select pernr,day"+previday+" from EMP_IN_OUT_Status where "
												+ " Month='"+previmonth+"' and year='"+previyear+"'  and EMP_IN_OUT_Status.Pernr in (select pernr from Temp_Emp where Date_Time='"+datecurrent+"') )t, "
												+ "(select Pernr,day"+newfiledateday+",day"+nextday+" from EMP_IN_OUT_Status where    Month='"+currmonth+"' and year='"+curryear+"' and EMP_IN_OUT_Status.Pernr in"
												+ " (select pernr from Temp_Emp where Date_Time='"+datecurrent+"'))l,WeekOff_Matrix where t.pernr=l.Pernr and  EMP_IN_OUT_Status.Pernr=l.Pernr and"
												+ " EMP_IN_OUT_Status.Pernr in (select pernr from Temp_Emp where Date_Time='"+datecurrent+"') and EMP_IN_OUT_Status.Month='"+currmonth+"' and EMP_IN_OUT_Status.year='"+curryear+"' "
												+ "and WeekOff_Matrix.Value1=right(t.Day"+previday+",2) and WeekOff_Matrix.Value2=l.Day"+newfiledateday+" and WeekOff_Matrix.Value3=left(l.Day"+nextday+",2)";	
										
										int bb=hrad.ad.SqlExecuteUpdate(prev);	
									}
									
									if(currmonth.compareTo(nextdaymonth)<0)
									{
										
										String next="update EMP_IN_OUT_Status set Day"+newfiledateday+"=WeekOff_Matrix.Output from (select pernr,day"+previday+",day"+newfiledateday+" from EMP_IN_OUT_Status where  Month='"+currmonth+"'"
								+ " and year='"+curryear+"'  and EMP_IN_OUT_Status.Pernr in (select pernr from Temp_Emp where Date_Time='"+datecurrent+"') )t, (select Pernr,day"+nextday+" from EMP_IN_OUT_Status where    "
								+ "Month='"+nextdaymonth+"' and year='"+nextyear+"' and EMP_IN_OUT_Status.Pernr in (select pernr from Temp_Emp where Date_Time='"+datecurrent+"'))l,WeekOff_Matrix where t.pernr=l.Pernr and  "
								+ "EMP_IN_OUT_Status.Pernr=l.Pernr and EMP_IN_OUT_Status.Pernr in (select pernr from Temp_Emp where Date_Time='"+datecurrent+"') and EMP_IN_OUT_Status.Month='"+currmonth+"' and"
							+ " EMP_IN_OUT_Status.year='"+curryear+"' and WeekOff_Matrix.Value1=right(t.Day"+previday+",2) and WeekOff_Matrix.Value2=t.Day"+newfiledateday+" and WeekOff_Matrix.Value3=left(l.Day"+nextday+",2)";
										
										int ll=hrad.ad.SqlExecuteUpdate(next);
									}
								}
						    } catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						    rsman1.close();
						    
						    
						    aqq ="  update  Attendance_Process_log set week_et=getdate(),rep_st=getdate(),progress=80 "
									+ " where lock_id ='"+datecurren11t+"' and (week_et is  null or week_et='')";
								kff11=hrad.ad.SqlExecuteUpdate(aqq);
						
			  ///last 2 months reports wise table generation
			  
			  String m="Select day(dateadd(mm,DateDiff(mm, -1, '"+sqltodat+"'),0) -1) as days1 , month('"+sqltodat+"') as month1 ,year('"+sqltodat+"') as year1 ,"
	+ "day(dateadd(mm,DateDiff(mm, -1, DATEADD(MONTH, -1, '"+sqltodat+"') ),0) -1) as days2 , month(DATEADD(MONTH, -1, '"+sqltodat+"') ) as month2 ,year(DATEADD(MONTH, -1, '"+sqltodat+"') ) as year2 "
	+ ", day(dateadd(mm,DateDiff(mm, -1, DATEADD(MONTH, -2, '"+sqltodat+"') ),0) -1) as days3 , month(DATEADD(MONTH, -2, '"+sqltodat+"') ) as month3 ,year(DATEADD(MONTH, -2, '"+sqltodat+"') ) as year3 ";
			  
			 ResultSet rsas=hrad.ad.selectQuery(m);
			 if(rsas.next())
			 {
				 if(reqemp.equalsIgnoreCase(""))
				 {
				 String q2="EXEC daily_report_status '"+rsas.getString("days1")+"','"+rsas.getString("month1")+"','"+rsas.getString("year1")+"','"+cmpcode+"','"+datecurrent+"' ";
					int k1=hrad.ad.SqlExecuteUpdate(q2);
					
					 String q12=" EXEC daily_report_status '"+rsas.getString("days2")+"','"+rsas.getString("month2")+"','"+rsas.getString("year2")+"','"+cmpcode+"','"+datecurrent+"'";
						int k12=hrad.ad.SqlExecuteUpdate(q12);	
						
						
						String q21=" EXEC daily_report_status '"+rsas.getString("days3")+"','"+rsas.getString("month3")+"','"+rsas.getString("year3")+"','"+cmpcode+"','"+datecurrent+"'";
						int k31=hrad.ad.SqlExecuteUpdate(q21);
						
	//First half Late
						
						String late="update Report_Daily_wise set late = convert(nvarchar(10),floor(datediff(minute,Come_Late_by,In_time)/60))+':'+convert(nvarchar(10),"
					+ "datediff(minute,Come_Late_by,In_time)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code   "
					+ "   and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and In_time>Come_Late_by and In_time < First_Half_End_Time and Location='"+cmpcode+"' "
					+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"')";			 
						int klate=hrad.ad.SqlExecuteUpdate(late);
						
						//Second half Late
						
						String slate="update Report_Daily_wise set late = convert(nvarchar(10),floor(datediff(minute,SH_Start_Time,In_time)/60))+':'+convert(nvarchar(10),	"
					+ "datediff(minute,SH_Start_Time,In_time)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code"
					+ "  and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and  In_time > SH_Start_Time and SH_Start_Time>'00:00' and Location='"+cmpcode+"' and "
					+ "month(Report_Daily_wise.Date) between Month(DATEADD(MONTH,-4,'"+sqltodat+"')) and Month(DATEADD(MONTH,0,'"+sqltodat+"'))";		 
									int sqlate=hrad.ad.SqlExecuteUpdate(slate);
									
						///go early by 		
									
					String early="update Report_Daily_wise set early= convert(nvarchar(10),floor(datediff(minute,Out_time,Go_Early_by)/60))+':'+convert(nvarchar(10),"
			+ "datediff(minute,Out_time,Go_Early_by)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code  "
			+ "   and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and Out_time < Go_Early_by and Out_time > SH_Start_Time   and Location='"+cmpcode+"'"
			+ " and Report_Daily_wise.Date between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"')";				
					int earsly=hrad.ad.SqlExecuteUpdate(early);			
					
					
					//Total hrs
						
					
					String tot="update Report_Daily_wise set total=case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),floor(datediff(minute,In_time,Out_time)/60))+':'+convert(nvarchar(10),datediff(minute,In_time,Out_time)%60) else convert(nvarchar(10),floor(datediff(minute,cast(In_time as DATETIME),dateadd(day,1,cast(Out_time as DATETIME)))/60))+':'+convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),dateadd(day,1,cast(Out_time as DATETIME)))%60) end "
							+ "from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
							+ "  and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and Location='"+cmpcode+"' and Report_Daily_wise.Date"
							+ " between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"') and Total is null and In_time>'00:00:00' and Out_time>'00:00:00'";
							int rr=hrad.ad.SqlExecuteUpdate(tot);		
							
							
				///OT HRS FULL TIME
							
							String otful="update Report_Daily_wise set OT=convert(nvarchar(10),floor((case when t.min<(case  when datediff(minute,In_time,Out_time)>0 then"
	+ " convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),"
	+ " dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)  then t.min  else (case  when datediff(minute,In_time,Out_time)>0 then  "
	+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
	+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)end)/60))+':'+ convert(nvarchar(10),(case when t.min<(case  when "
	+ "datediff(minute,In_time,Out_time)>0 then convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else "
	+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)  then t.min   "
	+ " else (case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else "
	+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)end)%60) from "
	+ "EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER,(select sum(NoHRS)*60 as min,Pernr,FromDate from OVER_TIME_DETAILS where FromDate between DATEADD(MONTH,-4,'"+sqltodat+"')"
	+ " and DATEADD(MONTH,1,'"+sqltodat+"') and ApprvrStatus='Approved' group by Pernr,FromDate)t	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
	+ "and t.Pernr=Report_Daily_wise.Pernr  and t.FromDate=Report_Daily_wise.Date and	0<case  when datediff(minute,In_time,Out_time)>0 then  "
	+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
	+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and Location='"+cmpcode+"' "
	+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"') and Report_Daily_wise.Status not like '%AA%' "
	+ "and In_time>'00:00:00' and Out_time>'00:00:00'";			
							int rr6=hrad.ad.SqlExecuteUpdate(otful);	
							
							
							//OT hrs half time
							
							String othalf="update Report_Daily_wise set OT=convert(nvarchar(10),floor((case when t.min<(case  when datediff(minute,In_time,Out_time)>0 then"
	+ " convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),"
	+ " dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)  then t.min  else (case  when datediff(minute,In_time,Out_time)>0 then  "
	+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
	+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)end)/60))+':'+ convert(nvarchar(10),(case when t.min<(case  when "
	+ "datediff(minute,In_time,Out_time)>0 then convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else "
	+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)  then t.min   "
	+ " else (case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else "
	+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)end)%60) from "
	+ "EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER,(select sum(NoHRS)*60 as min,Pernr,FromDate from OVER_TIME_DETAILS where FromDate between DATEADD(MONTH,-4,'"+sqltodat+"')"
	+ " and DATEADD(MONTH,1,'"+sqltodat+"') and ApprvrStatus='Approved' group by Pernr,FromDate)t	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
	+ "and t.Pernr=Report_Daily_wise.Pernr  and t.FromDate=Report_Daily_wise.Date and	0<case  when datediff(minute,In_time,Out_time)>0 then  "
	+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
	+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and Location='"+cmpcode+"' "
	+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"') and Report_Daily_wise.Status  like '%AA%'"
			+ "  and Report_Daily_wise.Status not like '%AAAA%'  and ( In_time>'00:00:00' or Out_time>'00:00:00')";
							int rr7=hrad.ad.SqlExecuteUpdate(othalf);	
							
							
							
				 }
				 else
				 {
					 String q2="EXEC daily_report_status_Att '"+rsas.getString("days1")+"','"+rsas.getString("month1")+"','"+rsas.getString("year1")+"','"+reqemp+"'";
					int k1=hrad.ad.SqlExecuteUpdate(q2);
						
					String q21=" EXEC daily_report_status_Att '"+rsas.getString("days2")+"','"+rsas.getString("month2")+"','"+rsas.getString("year2")+"','"+reqemp+"'";
					int k11=hrad.ad.SqlExecuteUpdate(q21);
							
					String q121="EXEC daily_report_status_Att '"+rsas.getString("days3")+"','"+rsas.getString("month3")+"','"+rsas.getString("year3")+"','"+reqemp+"'";
					int k21=hrad.ad.SqlExecuteUpdate(q121);
					
					
	//First half Late
					
					String late="update Report_Daily_wise set late = convert(nvarchar(10),floor(datediff(minute,Come_Late_by,In_time)/60))+':'+convert(nvarchar(10),"
				+ "datediff(minute,Come_Late_by,In_time)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code   "
				+ "   and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and In_time>Come_Late_by and In_time < First_Half_End_Time and pernr='"+reqemp+"' "
				+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"')";			 
					int klate=hrad.ad.SqlExecuteUpdate(late);
					
					//Second half Late
					
					String slate="update Report_Daily_wise set late = convert(nvarchar(10),floor(datediff(minute,SH_Start_Time,In_time)/60))+':'+convert(nvarchar(10),	"
				+ "datediff(minute,SH_Start_Time,In_time)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code"
				+ "  and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and  In_time > SH_Start_Time and SH_Start_Time>'00:00' and pernr='"+reqemp+"' and "
				+ "month(Report_Daily_wise.Date) between Month(DATEADD(MONTH,-4,'"+sqltodat+"')) and Month(DATEADD(MONTH,0,'"+sqltodat+"'))";		 
								int sqlate=hrad.ad.SqlExecuteUpdate(slate);
								
					///go early by 		
								
				String early="update Report_Daily_wise set early= convert(nvarchar(10),floor(datediff(minute,Out_time,Go_Early_by)/60))+':'+convert(nvarchar(10),"
		+ "datediff(minute,Out_time,Go_Early_by)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code  "
		+ "   and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and Out_time < Go_Early_by and Out_time > SH_Start_Time   and pernr='"+reqemp+"'"
		+ " and Report_Daily_wise.Date between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"')";				
				int earsly=hrad.ad.SqlExecuteUpdate(early);			
				
				
				//Total hrs
					
				
				String tot="update Report_Daily_wise set total=case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),floor(datediff(minute,In_time,Out_time)/60))+':'+convert(nvarchar(10),datediff(minute,In_time,Out_time)%60) else convert(nvarchar(10),floor(datediff(minute,cast(In_time as DATETIME),dateadd(day,1,cast(Out_time as DATETIME)))/60))+':'+convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),dateadd(day,1,cast(Out_time as DATETIME)))%60) end "
						+ " from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
						+ "  and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and pernr='"+reqemp+"' and Report_Daily_wise.Date"
						+ " between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"') and Total is null and In_time>'00:00:00' and Out_time>'00:00:00'";
						int rr=hrad.ad.SqlExecuteUpdate(tot);
						
						
						///OT HRS FULL TIME
						
						String otful="update Report_Daily_wise set OT=convert(nvarchar(10),floor((case when t.min<(case  when datediff(minute,In_time,Out_time)>0 then"
	+ " convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),"
	+ " dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)  then t.min  else (case  when datediff(minute,In_time,Out_time)>0 then  "
	+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
	+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)end)/60))+':'+ convert(nvarchar(10),(case when t.min<(case  when "
	+ "datediff(minute,In_time,Out_time)>0 then convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else "
	+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)  then t.min   "
	+ " else (case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else "
	+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)end)%60) from "
	+ "EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER,(select sum(NoHRS)*60 as min,Pernr,FromDate from OVER_TIME_DETAILS where FromDate between DATEADD(MONTH,-4,'"+sqltodat+"')"
	+ " and DATEADD(MONTH,1,'"+sqltodat+"') and ApprvrStatus='Approved' group by Pernr,FromDate)t	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
	+ "and t.Pernr=Report_Daily_wise.Pernr  and t.FromDate=Report_Daily_wise.Date and	0<case  when datediff(minute,In_time,Out_time)>0 then  "
	+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
	+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and Report_Daily_wise.pernr='"+reqemp+"' "
	+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"') and Report_Daily_wise.Status not like '%AA%' "
	+ "and In_time>'00:00:00' and Out_time>'00:00:00'";			
						int rr6=hrad.ad.SqlExecuteUpdate(otful);	
						
						
						//OT hrs half time
						
						String othalf="update Report_Daily_wise set OT=convert(nvarchar(10),floor((case when t.min<(case  when datediff(minute,In_time,Out_time)>0 then"
	+ " convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),"
	+ " dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)  then t.min  else (case  when datediff(minute,In_time,Out_time)>0 then  "
	+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
	+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)end)/60))+':'+ convert(nvarchar(10),(case when t.min<(case  when "
	+ "datediff(minute,In_time,Out_time)>0 then convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else "
	+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)  then t.min   "
	+ " else (case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else "
	+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)end)%60) from "
	+ "EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER,(select sum(NoHRS)*60 as min,Pernr,FromDate from OVER_TIME_DETAILS where FromDate between DATEADD(MONTH,-4,'"+sqltodat+"')"
	+ " and DATEADD(MONTH,1,'"+sqltodat+"') and ApprvrStatus='Approved' group by Pernr,FromDate)t	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
	+ "and t.Pernr=Report_Daily_wise.Pernr  and t.FromDate=Report_Daily_wise.Date and	0<case  when datediff(minute,In_time,Out_time)>0 then  "
	+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
	+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and Report_Daily_wise.pernr='"+reqemp+"' "
	+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,'"+sqltodat+"') and DATEADD(MONTH,1,'"+sqltodat+"') and Report_Daily_wise.Status  like '%AA%'"
		+ "  and Report_Daily_wise.Status not like '%AAAA%'  and ( In_time>'00:00:00' or Out_time>'00:00:00')";
						int rr7=hrad.ad.SqlExecuteUpdate(othalf);	
				 }
					
			 }
			 rsas.close();
			 
			
			
			 
			  //delete empNos
			    
			    String delete="delete Temp_Emp where Date_Time='"+datecurrent+"'";						    
			   int lldd=hrad.ad.SqlExecuteUpdate(delete);
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
		    
		  
		    
		    aqq ="  update  Attendance_Process_log set rep_et=getdate(),end_time=getdate(),process_status='Completed',progress=100 "
					+ " where lock_id ='"+datecurren11t+"'  and (rep_et is  null or rep_et='')";
				kff11=hrad.ad.SqlExecuteUpdate(aqq);
		
		
		
			
	
		
		
	}
	public LinkedList getofficaldetails(String empnos,HttpServletRequest request) {
		LinkedList off=new LinkedList();
	
		String b="";
		String emp = "select dpt.DPTSTXT as DPTSTXT,EMP_FULLNAME, LOCID,DSGSTXT,emp.pernr,emp.doj from emp_official_info as emp,DEPARTMENT as dpt ,DESIGNATION  where emp.dsgid  = DESIGNATION.dsgid and dpt.DPTID=emp.DPTID and pernr in ("+empnos+")";
		ResultSet rs = ad.selectQuery(emp);
		try {
			while(rs.next())
			{
				EmpOfficalInformationForm a=new EmpOfficalInformationForm();
				a.setEmployeeNumber(rs.getString("PERNR"));
				a.setEmployeeName(rs.getString("EMP_FULLNAME"));
				a.setDepartment(rs.getString("DPTSTXT"));
				a.setDesignation(rs.getString("DSGSTXT"));
				a.setDoj(rs.getString("doj"));
				
				off.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("emptyshiftnos", off);
		
		
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return off;
	
	}
	
	public ActionForward progressbar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HRApprovalForm approvalsForm = (HRApprovalForm) form;
			
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
		
		/*	if(progress==15)
				approvalsForm.setMessage2("Fetching Records");	
			if(progress==30)
				approvalsForm.setMessage2("Leave & Onduty Records Updated");			
			if(progress==50)
				approvalsForm.setMessage2("Punch Records Updated");
			if(progress==60)
				approvalsForm.setMessage2("Manual Records Updated");
			if(progress==80)
				approvalsForm.setMessage2("Week Offs  Updated");
			if(progress==90)
				approvalsForm.setMessage2("Attendance Reports Updated");
			if(progress==95)
				progress=0;*/

			String a ="select top 1 *,CONVERT(varchar(5),fetch_st,108) as fetch_st1,CONVERT(varchar(5),fetch_et,108) as fetch_et1,"
					+ "CONVERT(varchar(5),leave_st,108) as leave_st1,CONVERT(varchar(5),leave_et,108) as leave_et1"
					+ ",CONVERT(varchar(5),punch_st,108) as punch_st1,CONVERT(varchar(5),punch_et,108) as punch_et1"
					+ ",CONVERT(varchar(5),manual_st,108) as manual_st1,CONVERT(varchar(5),rep_et,108) as rep_et1  from Attendance_Process_log where emp_id='"+user.getEmployeeNo()+"' and process_Status='Pending' order by Start_Time desc ";
			
			ResultSet rs = ad.selectQuery(a);
			try {
				if(rs.next())
				{
					approvalsForm.setProgress(rs.getInt("progress"));
					approvalsForm.setFromDate(rs.getString("Start_date"));
					approvalsForm.setToDate(rs.getString("End_date"));
					approvalsForm.setStart_time(rs.getString("start_time"));
					approvalsForm.setEnd_time(rs.getString("end_time"));
					approvalsForm.setLock_id(rs.getString("lock_id"));
					approvalsForm.setEmp_count(rs.getString("emp_count"));
					approvalsForm.setFetch_st(rs.getString("fetch_st1"));
					approvalsForm.setFetch_et(rs.getString("fetch_et1"));
					approvalsForm.setLeave_st(rs.getString("leave_st1"));
					approvalsForm.setLeave_et(rs.getString("leave_et1"));
					approvalsForm.setPunch_st(rs.getString("punch_st1"));
					approvalsForm.setPunch_et(rs.getString("punch_et1"));
					approvalsForm.setManual_st(rs.getString("manual_st1"));
					approvalsForm.setManual_et(rs.getString("manual_et"));
					approvalsForm.setWeek_st(rs.getString("week_st"));
					approvalsForm.setWeek_et(rs.getString("week_et"));
					approvalsForm.setRep_st(rs.getString("rep_st"));
					approvalsForm.setRep_et(rs.getString("rep_et1"));

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		

			return mapping.findForward("progressbar");}
	
	
	
	
	
	public int[] updateMissingAttendanceDetails(LinkedList in){

		
		
		try
		{
			
			
		 PreparedStatement st1=null;
		
	
			if(conn==null||conn.isClosed())
			{
			   conn=AttConnectionFactory.getConnection();
			 }
	
		 
		   
	
			st1=conn.prepareStatement("insert into ATTENDANCE_MASTER_tran(Id,Date,Location) values (?,?,?)");
	
			 Iterator  indata=in.iterator();
		
			
			
			while(indata.hasNext()) {
				   HRApprovalForm abc=(HRApprovalForm)indata.next();
		
			st1.setString(1, abc.getFullpunch());
			st1.setString(2, abc.getPunchdate());
			st1.setString(3, abc.getPunchloc());
			st1.addBatch();
				
			}
			
			if(!in.isEmpty())
			{
				st1.executeBatch();
			}
			
		   
		   
		   
		   
	
		
				/*conn.close();*/

		 
		 
		}
		
		
			 catch (SQLException e) {
					e.printStackTrace();// TODO Change to log
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));			
					Date d=new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(d);
					String error="insert into ERROR_DETAILS values('Attendance','"+dateNow+"','"+errors.toString()+"')";
					int i= ad.SqlExecuteUpdate(error);
					
				}
		
	
		
		
		return null; 
		
	
		
		
	}
	
	public int[] updateAttendanceDetails(LinkedList in,LinkedList datelist){
		
		
		try
		{
			
		 final int BATCH_SIZE = 300;
		 PreparedStatement st1=null;
		 PreparedStatement st2=null;
		 PreparedStatement st3=null;
		 PreparedStatement st4=null;
		 PreparedStatement st5=null;
		 PreparedStatement st6=null;
		 String value="";
		  
		   Iterator  datedata=datelist.iterator();
	
		   
		
		   
		   while(datedata.hasNext()) {
			   HRApprovalForm abc=(HRApprovalForm)datedata.next();
			
			   
			   if(conn==null||conn.isClosed())
				 {
				   conn=AttConnectionFactory.getConnection();
				 }
			   
			   String tran1="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
						+ "values('"+abc.getId()+"','','','','','','DATES')";
				int j31=ad.SqlExecuteUpdate(tran1);
			   
			   	value=abc.getId();
			  
			 st1=conn.prepareStatement("update EMP_IN_OUT set "+value+"=? where pernr=? and month=? and year=? and In_Out=?");
	
			 st2=conn.prepareStatement("update EMP_IN_OUT_Status set "+value+"='PP'+RIGHT("+value+",2) where pernr=? and month=? and year=? ");
			 
			 st3=conn.prepareStatement("update EMP_IN_OUT_Status set "+value+"= LEFT("+value+",2)+'PP' where pernr=? and month=? and year=? ");
			 
	
			 Iterator  indata=in.iterator();
		
			 int batch1=0;
			 int batch2=0;
			 int batch3=0;
			
			while(indata.hasNext()) {
				
				HRApprovalForm a=(HRApprovalForm)indata.next();
				String tran91="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
						+ "values('"+a.getEmployeeNo()+"','"+a.getTime()+"','"+a.getFrommonth()+"','"+value+"','"+a.getStatus()+"','','MEGAFIANL')";
				int jj31=ad.SqlExecuteUpdate(tran91);
			
			
				
					
					
				
				 if(conn==null||conn.isClosed())
				 {
					 conn=AttConnectionFactory.getConnection();
				 }

				
			
			
				st1.setString(1,a.getTime());
				st1.setString(2,a.getEmployeeNo());
				st1.setString(3,a.getFrommonth());
				st1.setString(4,a.getCalyear());
				st1.setString(5,a.getStatus());
				if(a.getId()==null)
				{
					a.setId("");
				}
				if(a.getId().equalsIgnoreCase(value))
				{
					
					
					
		
					
					batch1=batch1+1;
					st1.addBatch();
				}
				if(a.getStatus()==null)
				{
					a.setStatus("");
				}
				
				if(a.getMessage()==null)
				{
					a.setMessage("");
				}
				
				if(a.getStatus().equalsIgnoreCase("I"))
				{
				if(!a.getMessage().equalsIgnoreCase("S"))
				{
				st2.setString(1,a.getEmployeeNo());
				st2.setString(2,a.getFrommonth());
				st2.setString(3,a.getCalyear());
				if(a.getId().equalsIgnoreCase(value))
				{
						batch2=batch2+1;
					st2.addBatch();
				}
				}
				
				}
				if(a.getStatus().equalsIgnoreCase("O"))
				{
					if(!a.getMessage().equalsIgnoreCase("S"))
					{
					st3.setString(1,a.getEmployeeNo());
					st3.setString(2,a.getFrommonth());
					st3.setString(3,a.getCalyear());
					if(a.getId().equalsIgnoreCase(value))
					{
						batch3=batch3+1;
						st3.addBatch();
					}
					}
					
				}
				
				
				if (batch1 % BATCH_SIZE == BATCH_SIZE - 1)
				{
					conn=AttConnectionFactory.getConnection();
					st1.executeBatch();
					String yu="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
							+ "values('"+a.getEmployeeNo()+"','"+a.getTime()+"','"+a.getFrommonth()+"','"+value+"','"+a.getStatus()+"','','BATCH - 1  UPDATED')";
					int jo=ad.SqlExecuteUpdate(yu);
				
					st1.clearBatch();
				}
				if (batch2 % BATCH_SIZE == BATCH_SIZE - 1)
				{
					conn=AttConnectionFactory.getConnection();
					st2.executeBatch();
					String io="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
							+ "values('"+a.getEmployeeNo()+"','"+a.getTime()+"','"+a.getFrommonth()+"','"+value+"','"+a.getStatus()+"','','BATCH - 2  UPDATED')";
					int op=ad.SqlExecuteUpdate(io);

					st2.clearBatch();
				}
				
				if (batch3 % BATCH_SIZE == BATCH_SIZE - 1)
				{
					conn=AttConnectionFactory.getConnection();
					st3.executeBatch();
					String jj="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
							+ "values('"+a.getEmployeeNo()+"','"+a.getTime()+"','"+a.getFrommonth()+"','"+value+"','"+a.getStatus()+"','','BATCH - 3  UPDATED')";
					int lo=ad.SqlExecuteUpdate(jj);
					
					st3.clearBatch();
				}
				
				
				
					
			}
			
			
			
	
		
		
			
			    conn=AttConnectionFactory.getConnection();
				st1.executeBatch();
				String yu="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
						+ "values('','','','"+value+"','','','BATCH - 1  UPDATED')";
				int jo=ad.SqlExecuteUpdate(yu);			
	
				conn=AttConnectionFactory.getConnection();
				st2.executeBatch();
				String kk="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
						+ "values('','','','"+value+"','','','BATCH - 2  UPDATED')";
				int hh=ad.SqlExecuteUpdate(kk);	
		
				conn=AttConnectionFactory.getConnection();
				st3.executeBatch();
				String ll="insert into ATTENDANCE_PROCESS_TEMPLATE(User_Empno,User_EmpFullname,LocationId,FromDate,ToDate,allemps,paygrp) "
						+ "values('','','','"+value+"','','','BATCH - 3  UPDATED')";
				int jouu=ad.SqlExecuteUpdate(ll);	
	
		
			
		   }
		   
		   
		   
	
		
				/*conn.close();*/

		 
		 
		}
		
		
			 catch (SQLException e) {
					e.printStackTrace();// TODO Change to log
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));			
					Date d=new Date();
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(d);
					String error="insert into ERROR_DETAILS values('Attendance','"+dateNow+"','"+errors.toString()+"')";
					int i= ad.SqlExecuteUpdate(error);
					
				}
		
	
		
		
		return null; 
		
	}
		public String getempshift(int filedateday,String empno,String filedatemonth,String filedateyear)
	{
			
			conn=AttConnectionFactory.getConnection();
			String shift="";
		String currentshift="";
		String prevshift="";
		int prevday=0;
		int prevmonth=0;
		int prevyear=0;
		
		if(filedateday==1)
		{
			
			Calendar mycal = Calendar.getInstance();
			if(filedateday==Integer.parseInt(filedatemonth))
			{
			mycal.set(Integer.parseInt(filedateyear)-1, 12, 1);	
			prevmonth=12;
			prevyear=Integer.parseInt(filedateyear)-1;
			}
			else
			{
			mycal.set(Integer.parseInt(filedateyear), Integer.parseInt(filedatemonth)-2, 1);	
			prevmonth=Integer.parseInt(filedatemonth)-1;
			prevyear=Integer.parseInt(filedateyear);
			}
			
			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
			prevday=daysInMonth;
			
		}
		else
		{
			prevday=filedateday-1;	
			prevmonth=Integer.parseInt(filedatemonth);
			prevyear=Integer.parseInt(filedateyear);
		}
		
		  String shiftregister="Select (select Day"+filedateday+" from EMP_Shift_Register where Pernr='"+empno+"' and Month='"+filedatemonth+"' and Year='"+filedateyear+"'),(select Day"+prevday+" from EMP_Shift_Register where Pernr='"+empno+"' and Month='"+prevmonth+"' and Year='"+prevyear+"')";
		    ResultSet rsshiftregister=ad.selectQuery(shiftregister);
		    try {
				if(rsshiftregister.next())
				{
					
					currentshift=rsshiftregister.getString(1);
					prevshift=rsshiftregister.getString(2);
					shift=prevshift+","+currentshift;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			rsshiftregister.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return shift;
	}
		
		public LinkedList getempleave(String filedate,String empno,String filedatemonth,String filedateyear,String location)
		{
				
				conn=AttConnectionFactory.getConnection();
			String startdate="";
			String enddate="";
			String startdur="";
			String endur="";
			String leavtype="";
			LinkedList leave=new LinkedList();
			String shiftregister="";
			if(!empno.equalsIgnoreCase(""))
			   shiftregister="select year(start_date) as year,lv_shrt,day(start_date) as stday,day(end_date) as enday ,Month(start_date) as stMon,Month(end_date) as enMon  ,start_duration,end_duration, * from leave_details , emp_official_info,  lv_type_m   where ('"+filedate+"' between start_date and end_date or convert(date,approved_date)='"+filedate+"')  and   emp_official_info.PERNR=leave_details.user_id and lv_type_m.lv_typeid =leave_details.leave_type and locid='"+location+"'  and Approvel_Status='Approved' and user_id in ("+empno+")";
			else
				  shiftregister="select year(start_date) as year,lv_shrt,day(start_date) as stday,day(end_date) as enday ,Month(start_date) as stMon,Month(end_date) as enMon  ,start_duration,end_duration, * from leave_details , emp_official_info,  lv_type_m   where ('"+filedate+"' between start_date and end_date or convert(date,approved_date)='"+filedate+"')  and   emp_official_info.PERNR=leave_details.user_id and lv_type_m.lv_typeid =leave_details.leave_type and locid='"+location+"'  and Approvel_Status='Approved'";	
			  
			  
			  ResultSet rsshiftregister=ad.selectQuery(shiftregister);
			    try {
					while(rsshiftregister.next())
					{
						LeaveForm le=new LeaveForm();
						String days="";
						le.setEmployeeNumber(rsshiftregister.getString("user_id"));
						 String countdays="SELECT day(thedate)  as day FROM dbo.ExplodeDates('"+rsshiftregister.getString("start_date")+"','"+rsshiftregister.getString("end_date")+"') as thedate";
						    ResultSet rscount=ad.selectQuery(countdays);
						    while(rscount.next())
						    {
						    	days=days+","+"Day"+rscount.getString("day");
						    }
						    rscount.close();
						    days=days.substring(1, days.length());
						le.setId(days);
						le.setStartDate(rsshiftregister.getString("stday"));
						le.setEndDate(rsshiftregister.getString("enday"));
						le.setStartDurationType(rsshiftregister.getString("start_duration"));
						le.setEndDurationType(rsshiftregister.getString("end_duration"));
						le.setLeaveType(rsshiftregister.getString("lv_shrt"));
						filedateyear=rsshiftregister.getString("year");
						
						if(rsshiftregister.getString("start_duration").equalsIgnoreCase("FD") && rsshiftregister.getString("end_duration").equalsIgnoreCase("FD"))
						{
							le.setStatus(rsshiftregister.getString("lv_shrt")+rsshiftregister.getString("lv_shrt"));
							if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
							{
							String a="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
							}
							else
							{
								String a="update EMP_IN_OUT_Status set "+FullDayPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a);
				                
				                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
				                {
				                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
					                int end=ad.SqlExecuteUpdate(a1a);
				                }
				                
				            	String a1="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
				                int end=ad.SqlExecuteUpdate(a1);
							}
						}
						if(rsshiftregister.getString("start_duration").equalsIgnoreCase("FH") && rsshiftregister.getString("end_duration").equalsIgnoreCase("FH"))
						{
							le.setFrststatus(rsshiftregister.getString("lv_shrt"));
							if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
							{
							String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
							}
							else
							{
								String a1="update EMP_IN_OUT_Status set "+FrstHalfPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a1);
				                
				                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
				                {
				                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
					                int end=ad.SqlExecuteUpdate(a1a);
				                }
								
								String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
				                int frst1=ad.SqlExecuteUpdate(a);
							}
						}
						if(rsshiftregister.getString("start_duration").equalsIgnoreCase("SH") && rsshiftregister.getString("end_duration").equalsIgnoreCase("SH"))
						{
							le.setSecstatus(rsshiftregister.getString("lv_shrt"));
							if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
							{
							String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
							}
							else
							{
								String a1="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalfPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a1);
				                
				                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
				                {
				                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
					                int end=ad.SqlExecuteUpdate(a1a);
				                }
								
								String a="update EMP_IN_OUT_Status set "+SecondHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
				                int frst1=ad.SqlExecuteUpdate(a);
							}
						}
						if(rsshiftregister.getString("start_duration").equalsIgnoreCase("FD") && rsshiftregister.getString("end_duration").equalsIgnoreCase("FH"))
						{
							le.setStatus(rsshiftregister.getString("lv_shrt")+rsshiftregister.getString("lv_shrt"));
							le.setFrststatus(rsshiftregister.getString("lv_shrt"));
							if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
							{
							String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
							}
							else
							{
								String a1="update EMP_IN_OUT_Status set "+FrstHalfPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a1);
				                
				                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
				                {
				                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
					                int end=ad.SqlExecuteUpdate(a1a);
				                }
								
								String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
				                int frst1=ad.SqlExecuteUpdate(a);
							}
						}
						if(rsshiftregister.getString("start_duration").equalsIgnoreCase("SH") && rsshiftregister.getString("end_duration").equalsIgnoreCase("FD"))
						{
							le.setStatus(rsshiftregister.getString("lv_shrt")+rsshiftregister.getString("lv_shrt"));
							le.setSecstatus(rsshiftregister.getString("lv_shrt"));
							if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
							{
							String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
							}
							else
							{
								String a1="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalfPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a1);
				                
				                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
				                {
				                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
					                int end=ad.SqlExecuteUpdate(a1a);
				                }
								
								String a="update EMP_IN_OUT_Status set "+SecondHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
				                int frst1=ad.SqlExecuteUpdate(a);
							}
						}
						if(rsshiftregister.getString("start_duration").equalsIgnoreCase("SH") && rsshiftregister.getString("end_duration").equalsIgnoreCase("FH"))
						{

							le.setStatus(rsshiftregister.getString("lv_shrt")+rsshiftregister.getString("lv_shrt"));
							le.setSecstatus(rsshiftregister.getString("lv_shrt"));
							le.setFrststatus(rsshiftregister.getString("lv_shrt"));
							if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
							{
							String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalf_firstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
							}
							else
							{
								String a1="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalfPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a1);
								
				                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
				                {
				                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
					                int end=ad.SqlExecuteUpdate(a1a);
				                }
				                
								String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
				                int frst1=ad.SqlExecuteUpdate(a);
							}
						
							
						}
					 le.setMonth(filedatemonth);
					 le.setYear(Integer.parseInt(filedateyear));
						
						leave.add(le);
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			    try {
					rsshiftregister.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			return leave;
		}
		
		public String getrailwaytime(String time)
		{
			String reqtime="";
			
		  
		      //Format of the date defined in the input String
		      DateFormat df = new SimpleDateFormat("hh:mm aa");
		      //Desired format: 24 hour format: Change the pattern as per the need
		      DateFormat outputformat = new SimpleDateFormat("HH:mm");
		      Date date = null;
		  
		      try{
		         //Converting the input String to Date
		    	 date= df.parse(time);
		         //Changing the format of date and storing it in String
		    	 reqtime = outputformat.format(date);
		         //Displaying the date

		      }catch(ParseException pe){
		         pe.printStackTrace();
		       }
		   
			
			return reqtime;
		}
		
		
		public String timediff(String time1,String time2)
		{
		

				String hours="";

				//HH converts hour in 24 hours format (0-23), day calculation
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");

				Date d1 = null;
				Date d2 = null;

				try {
					d1 = format.parse(time1);
					d2 = format.parse(time2);

					//in milliseconds
					long diff = d2.getTime() - d1.getTime();

					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffDays = diff / (24 * 60 * 60 * 1000);

				long value=(diffHours*60)+diffMinutes;
					
					hours=Long.toString(value);
				} catch (Exception e) {
					e.printStackTrace();
				}

		
				
				return hours;
				
			}
		
		public String[] getshifttimings(int day,String empcode,String month,String year)
		{

			String shftstrtime="";
			String shftendtime="";
			String firsthalfend="";
			String sechalfstart="";
			
			
			
			
			  String timings="";
			  String shiftregister="select * from EMP_SHIFT_MASTER,EMP_SHIFT_LATE_RULES where Shift_code=(select day"+day+" from EMP_Shift_Register where Pernr='"+empcode+"' and Month='"+month+"' and Year='"+year+"') and EMP_SHIFT_MASTER.Shift_code=EMP_SHIFT_LATE_RULES.Rule_Code";
			    ResultSet rsshiftregister=ad.selectQuery(shiftregister);
			    try {
					if(rsshiftregister.next())
					{
						
						shftstrtime=rsshiftregister.getString("Shift_start_time");
						shftendtime=rsshiftregister.getString("Shift_End_Time");
						firsthalfend=rsshiftregister.getString("First_Half_End_Time");
						sechalfstart=rsshiftregister.getString("SH_Start_Time");
						
						timings=shftstrtime+","+shftendtime+","+firsthalfend+","+sechalfstart;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    

			    
			    String a[]=timings.split(",");
			
				try {
					rsshiftregister.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					
				}
			return a;
		}
		
		public LinkedList getcompoff(String filedate,String empno,int filedateday,String filedatemonth,String filedateyear,String location)
		{

			
			conn=AttConnectionFactory.getConnection();
		String startdate="";
		String enddate="";
		String startdur="";
		String endur="";
		String leavtype="";
		LinkedList compff=new LinkedList();
		String empshift="";
	
		
		String com="";
		String shiftregister="";
		if(!empno.equalsIgnoreCase(""))		
		 com="Select left(DATENAME (month,FromDate),3)  as monthname,day(FromDate) as day,month(FromDate) as mon,year(FromDate) as year,* from COMP_OT,emp_official_info  where ('"+filedate+"' between FromDate and todate or convert(date,ApprvdDate)='"+filedate+"') and COMP_OT.Pernr=emp_official_info.PERNR and SAP_Approved=0 and locid='"+location+"' and  comp_ot.ApprvrStatus='Approved' and emp_official_info.pernr in ("+empno+")";
		else
		 com="Select left(DATENAME (month,FromDate),3)  as monthname,day(FromDate) as day,month(FromDate) as mon,year(FromDate) as year,* from COMP_OT,emp_official_info  where ('"+filedate+"' between FromDate and todate or convert(date,ApprvdDate)='"+filedate+"') and COMP_OT.Pernr=emp_official_info.PERNR and SAP_Approved=0 and locid='"+location+"' and  comp_ot.ApprvrStatus='Approved'";
			
		
		
		
		ResultSet a1 = ad.selectQuery(com);
		String shifttime[]=new String[0];
		try {
			while (a1.next()) {
				boolean allow=false;
				LeaveForm l = new LeaveForm();
				l.setEmployeeNumber(Integer.toString(a1.getInt("Pernr")));
				l.setStartDate(a1.getString("FromDate"));
				l.setEndDate(a1.getString("ToDate"));
				l.setNofhrs(a1.getString("NoHRS"));
				l.setShift(a1.getString("Applicabale"));
				l.setLocationId(a1.getString("LOCID"));
				l.setRequestNumber(a1.getInt("SAP_ReqNo"));
				shifttime=getshifttimings(Integer.parseInt(a1.getString("day")), a1.getString("Pernr"), a1.getString("mon"),  a1.getString("year"));
				   String shftstart="";
				     String shftend="";
				     
				     if(shifttime.length!=0)
				     {
			          shftstart=shifttime[0].substring(0, 5);
				      shftend=shifttime[1].substring(0, 5);
				 
				     }
				     String punchtime="";
				     String intime="";
				     String outime="";
               String flat="select left(max(a),5) as intime,left(max(b),5) as outime from (select case when In_Out='I' then day"+a1.getString("day")+"  end  as a,case when In_Out='O' then day"+a1.getString("day")+"  end as b from EMP_IN_OUT where Pernr='"+a1.getInt("Pernr")+"' and Month='"+a1.getString("mon")+"' and Year='"+a1.getString("year")+"' )t";
               ResultSet rssempoffr=ad.selectQuery(flat);
               while(rssempoffr.next())
			    {
            	  	   intime=rssempoffr.getString("intime");
            	      outime=rssempoffr.getString("outime");
                }
               rssempoffr.close();
               String value="";
               String cou="";
               String table="";
               //check emp cal
               
               String empca="Select count(*) as cou from EMP_CAL where Pernr='"+a1.getInt("Pernr")+"' and CYEAR='"+a1.getInt("year")+"'";
               ResultSet ac=ad.selectQuery(empca);
               if(ac.next())
               {
            	  cou=ac.getString(1); 
               }
               ac.close();
               
               if(cou.equalsIgnoreCase("0"))
               {
                table="select "+a1.getString("monthname")+" as m from "+getTableName(a1.getString("LOCID"))+" where DAY='"+a1.getString("day")+"' and CYEAR='"+a1.getInt("year")+"' ";
               }
               else
               {
            	 table="select "+a1.getString("monthname")+" as m from EMP_CAL where DAY='"+a1.getString("day")+"' and  Pernr='"+a1.getInt("Pernr")+"' and CYEAR='"+a1.getInt("year")+"' "; 
               }
               
				  ResultSet rstable=ad.selectQuery(table);
				  while(rstable.next())
					  
					{
					  
					  value=rstable.getString("m");
					}
				  rstable.close();
               
               String nohrs="";
               
               if(value.equalsIgnoreCase("W"))
               {
               if(a1.getString("Applicabale").equals("BS"))
        	   {
            	   if(!(intime.equalsIgnoreCase("00:00")))
    			   {
        		    nohrs=timediff(intime, shftstart);
        		    nohrs= Integer.toString(Math.abs(Integer.parseInt(nohrs)));
    
    			   }
            	   else
            	   {
            		   nohrs="0"; 
            	   }
        	   }
               
               if(a1.getString("Applicabale").equals("AS"))
        	   {
            	   if(!outime.equalsIgnoreCase("00:00"))
    			   {
        		    nohrs=timediff(shftend, outime);
        		    nohrs= Integer.toString(Math.abs(Integer.parseInt(nohrs)));
        		  
    			   }
            	 //Arun 21/05/2016 Empty "" error came so kept this validation line 1
            	   else
            	   {
            		   nohrs="0"; 
            		   
            	   }  
        	   }
               }
               else
               {
            	   if(!(intime.equalsIgnoreCase("00:00") || outime.equalsIgnoreCase("00:00")))
            			   {
            	   nohrs=timediff(intime, outime);
            	   nohrs= Integer.toString(Math.abs(Integer.parseInt(nohrs)));
        	
            			   }
            	   else
            	   {
            		   nohrs="0";
            	   }
               }
               //Arun 21/05/2016 Empty "" error came so kept this validation line 1
            
               
               if(!a1.getString("NoHRS").equalsIgnoreCase(""))
               {
            	   
               
               if(a1.getString("NoHRS").equalsIgnoreCase("4"))
               {
                if(Integer.parseInt(nohrs)>=240)
                {
                	allow=true;
                	l.setNoOfDays("0.5");
                }
               }
               if(a1.getString("NoHRS").equalsIgnoreCase("8"))
               {
            	   if(Integer.parseInt(nohrs)>=480)
                   {
                   	allow=true;
                   	l.setNoOfDays("1");
                   }
               }
               }   
               if(allow==true)
               {
            	  
            	   SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
					String strt=l.getStartDate();
					String end=l.getEndDate();
					String compaviled="";
					String thirtyFromdat="";
					try {
					Calendar cal = Calendar.getInstance();
					cal.setTime(ft.parse(strt));
					cal.add(Calendar.DATE, 31);
					thirtyFromdat = ft.format(cal.getTime());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String insetrt="insert into COMP_OT_SAP(Calendar_Year,Employee_Number,Start_Date,End_Date,Number_of_Days,Actual_Time,From_Time,End_Time,Comp_Off_Availed_Date,Comp_Off_Balance,LapsBydate) "
							+ "values('"+a1.getString("year")+"','"+a1.getString("Pernr")+"','"+strt+"','"+end+"','"+l.getNoOfDays()+"','"+nohrs+"','"+intime+"','"+outime+"',"
									+ "'"+compaviled+"','"+l.getNoOfDays()+"','"+thirtyFromdat+"')";
			    	 int j=ad.SqlExecuteUpdatePrmaryKeys(insetrt);
			    	 
			       	float comOpBal=0;
		        	float comAvaiBal=0;
		        	float comCloBal=0;
		  
		        	if(j>0)
		        	{
		        		
		        		//update main table 
		        		String d="Update COMP_OT set SAP_Approved=1 where Req_no='"+a1.getString("Req_no")+"' and SAP_ReqNo='"+a1.getInt("SAP_ReqNo")+"'";
		        		int jd=ad.SqlExecuteUpdatePrmaryKeys(d);
		        		
		        	/*///Get balance from comptable
		        		String bal="select year(Start_Date) as year,sum(Number_of_Days) as openbal,sum(Comp_Off_Availed_Days) as avbal,sum(Comp_Off_Balance) as clobal from COMP_OT_SAP where Employee_Number='"+a1.getString("pernr")+"'  and Comp_Off_Balance>0  and  DATEDIFF(DAY,Start_Date,'"+startDate+"') <=30 group by Start_Date";
		       	    ResultSet ba=ad.selectQuery(bal);
		       	 try {
					while (ba.next ()) 
						{
						    comOpBal=comOpBal+ba.getFloat("openbal");
							comAvaiBal=comAvaiBal+ba.getFloat("avbal");
							comCloBal=comCloBal+ba.getFloat("clobal");
						}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        	
		           	 //Check comp balance availabe
		        	

		           	 int balpresent=0;
		           	 String coun="select count(*) from lv_type_d where lv_empcode='"+a1.getString("Pernr")+"' and lv_typeid='6' and lv_calyear='"+a1.getString("year")+"'";
		           	 ResultSet as=ad.selectQuery(coun);
		           	 try {
						while (as.next ()) 
						{
							
							balpresent=as.getInt(1);
							
						}
					} catch (SQLException e) {				
						e.printStackTrace();
					}
		        	
		        	 if(balpresent==0)
		           	 {
		       	 //insert into lv_type
		       	 String insertinto="insert into lv_type_d(lv_typeid,lv_calyear,lv_empcode,lv_opbal,lv_availed,lv_clbal,lv_awtBal) values(6,'"+a1.getString("year")+"','"+a1.getString("Pernr")+"','"+comOpBal+"','"+comAvaiBal+"','"+comCloBal+"',0)";
		       	  j=ad.SqlExecuteUpdatePrmaryKeys(insertinto);
		           	 }
		           	 
		           	 else
		           	 {
		          String update="update lv_type_d set lv_opbal="+comOpBal+",lv_clbal="+comCloBal+" where lv_empcode='"+a1.getString("Pernr")+"' and lv_calyear='"+a1.getString("year")+"' and lv_typeid='6'";
		           j=ad.SqlExecuteUpdatePrmaryKeys(update);
		           	 }
		        	}
			    	 */
		        	} 
               }
               
		
			
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			a1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return compff;
	
			
		}
		
		public String FrstHalf(int startday,int endday,String frst,String second,String full,int stmont,int enmonth)
		{
			
			String value="'"+frst+"'+RIGHT(Day"+endday+",2)";
			
			if(stmont==enmonth)
			{
			for(int i=startday;i<=endday-1;i++)
			{
				
				
				value=value+",Day"+i+"='"+full+"'";
			}
			}
			else
			{
				for(int i=1;i<=endday-1;i++)
				{
					
					
					value=value+",Day"+i+"='"+full+"'";
				}
				
				
			}
			
			
			
			return value;
		}
		
		
		public String FrstHalfPrev(int startday,int endday,String frst,String second,String full)
		{
			
			String value="";
			
			for(int i=startday;i<=31;i++)
			{
				
				
				value=value+",Day"+i+"='"+full+"'";
			}
			value=value.substring(1, value.length());
			
			
			
			return value;
		}
		
		public String SecondHalf(int startday,int endday,String frst,String second,String full,int stmont,int enmonth)
		{
			
			String value="LEFT(Day"+startday+",2)+"+"'"+second+"'";
			
			if(stmont==enmonth)
			{
			for(int i=startday+1;i<=endday;i++)
			{
				
				
				value=value+",Day"+i+"='"+full+"'";
			}
			}
			else
			{
				value="";
				for(int i=1;i<=endday;i++)
				{
					
					
					value=value+",Day"+i+"='"+full+"'";
				}
				value=value.substring(1, value.length());
			}
			
			
			
			return value;
		}
		public String SecondHalf_firstHalf(int startday,int endday,String frst,String second,String full)
		{
			
			String value="LEFT(Day"+startday+",2)+"+"'"+second+"'";
			
			if(startday<=endday)
			{
			for(int i=startday+1;i<=endday;i++)
			{
				
				if(i<endday)
				value=value+",Day"+i+"='"+full+"'";
				else
			    value=value+",Day"+i+"='"+second+"'+RIGHT(Day"+endday+",2)";	
				
			}
			}
			else
			{
				for(int i=1;i<=endday;i++)
				{
					
					
					value=value+",Day"+i+"='"+full+"'";
				}
				value=value.substring(1, value.length());
			}
			
			
			
			return value;
		}
		
		public String SecondHalfPrev(int startday,int endday,String frst,String second,String full)
		{
			
			String value="LEFT(Day"+startday+",2)+"+"'"+second+"'";
			
			for(int i=startday+1;i<=31;i++)
			{
				
				
				value=value+",Day"+i+"='"+full+"'";
			}
			
			
			
			return value;
		}
		
		public String FullDay(int startday,int endday,String frst,String second,String full,int stmont,int enmonth)
		{
			
			String value="";
			
			if(stmont==enmonth)
			{
			
			for(int i=startday;i<=endday;i++)
			{
				
				
				value=value+",Day"+i+"='"+full+"'";
			}
			 value=value.substring(1, value.length());
			
			}
			else
			{
				for(int i=1;i<=endday;i++)
				{
					
					
					value=value+",Day"+i+"='"+full+"'";
				}
				 value=value.substring(1, value.length());
			}
			
			return value;
		}
		
		
		public String FullDay_Inbetween_month(int month,String year,String full)
		{
			
			String value="";
			
			Calendar mycal = Calendar.getInstance();

			mycal.set(Integer.parseInt(year), month-1, 1);
			
			// Get the number of days in that month
			int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			
				for(int i=1;i<=daysInMonth;i++)
				{
					
					
					value=value+",Day"+i+"='"+full+"'";
				}
				 value=value.substring(1, value.length());
			
			
			return value;
		}
		
		public String FullDayPrev(int startday,int endday,String frst,String second,String full)
		{
			
			String value="";
			
			for(int i=startday;i<=31;i++)
			{
				
				
				value=value+",Day"+i+"='"+full+"'";
			}
			 value=value.substring(1, value.length());
			
			
			return value;
		}
		
		public LinkedList getemponduty(String filedate,String empno,int filedateday,String filedatemonth,String filedateyear,String location)
		{
				
				conn=AttConnectionFactory.getConnection();
			String startdate="";
			String enddate="";
			String startdur="";
			String endur="";
			String leavtype="";
			LinkedList onduty=new LinkedList();
			String empshift="";
			String shiftregister="";
			if(!empno.equalsIgnoreCase(""))
			   shiftregister="select year(start_date) as year,day(start_date) as stday,day(end_date) as enday  ,Month(start_date) as stMon,Month(end_date) as enMon  ,left(startTime,5)+' '+right(startTime,2) as startTime1,left(endTime,5)+' '+right(endTime,2) as endTime1, * from OnDuty_details , emp_official_info   where ('"+filedate+"' between start_date and end_date or convert(date,approved_date)='"+filedate+"')  and   emp_official_info.PERNR=OnDuty_details.user_id  and locid='"+location+"' and Approver_Status='Approved' and user_id in("+empno+")";
			else
				 shiftregister="select year(start_date) as year,day(start_date) as stday,day(end_date) as enday  ,Month(start_date) as stMon,Month(end_date) as enMon  ,left(startTime,5)+' '+right(startTime,2) as startTime1,left(endTime,5)+' '+right(endTime,2) as endTime1, * from OnDuty_details , emp_official_info   where ('"+filedate+"' between start_date and end_date or convert(date,approved_date)='"+filedate+"')  and   emp_official_info.PERNR=OnDuty_details.user_id  and locid='"+location+"' and Approver_Status='Approved'";
			
			
			    ResultSet rsshiftregister=ad.selectQuery(shiftregister);
			    try {
					while(rsshiftregister.next())
					{
						String days="";
						String stringdays="";
						OnDutyForm le=new OnDutyForm();
						 empno=rsshiftregister.getString("user_id");
						 filedateyear=rsshiftregister.getString("year");
						    
					     empshift=getempshift(filedateday, empno, filedatemonth, filedateyear);
					     String shifttime[]=new String[0];
					    
					     String empshiftmaster[]=new String[0];
					     
					     
					     if(!empshift.equalsIgnoreCase(""))
					     {
					    	 empshiftmaster=empshift.split(",");
					      shifttime=getempshifttime(empshiftmaster[1]);
					   
					     }
					     String shftstart="";
					     String shftend="";
					     String frsthalfend="";
					     String sechalfstart="";
					     
					     
					     if(shifttime.length!=0)
					     {
				          shftstart=shifttime[0].substring(0, 5);
					      shftend=shifttime[1].substring(0, 5);
					      frsthalfend=shifttime[2].substring(0, 5);
					      sechalfstart=shifttime[3].substring(0, 5);
					     }
						
						
						le.setEmployeeNumber(rsshiftregister.getString("user_id"));
						le.setStartDate(rsshiftregister.getString("start_date"));
						le.setEndDate(rsshiftregister.getString("end_date"));
						le.setStartTime(getrailwaytime(rsshiftregister.getString("startTime1")));
						le.setEndTime(getrailwaytime(rsshiftregister.getString("endTime1")));
						
					   
					   
					   
						/* String countdays="SELECT day(thedate)  as day FROM dbo.ExplodeDates('"+rsshiftregister.getString("start_date")+"','"+rsshiftregister.getString("end_date")+"') as thedate";
						    ResultSet rscount=ad.selectQuery(countdays);
						    while(rscount.next())
						    {
						    	days=days+","+"Day"+rscount.getString("day");
						    	frs
						    }*/
						
						le.setId(days);
						
						if(le.getStartDate().compareTo(le.getEndDate())==0)
						{
						if(le.getStartTime().compareTo(shftstart)<=0 && le.getEndTime().compareTo(shftend)>=0)
						{
							le.setStatus("ODOD");
							String a="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
						}
						else if(le.getStartTime().compareTo(shftstart)<=0 && le.getEndTime().compareTo(frsthalfend)>=0 && le.getEndTime().compareTo(sechalfstart)<=0)
						{
						
							le.setFrststatus("OD");
							String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
						}
						else if(le.getEndTime().compareTo(shftend)>=0 && le.getStartTime().compareTo(frsthalfend)>=0 && le.getStartTime().compareTo(sechalfstart)<=0)
						{
							le.setSecstatus("OD");
							String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
						}
						
						else if(le.getStartTime().compareTo(shftstart)>=0 && (le.getEndTime().compareTo(shftend)<=0 || le.getEndTime().compareTo(shftend)>=0))
						{
							le.setStatus("ODOD");
							String a="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
						}
						else if(le.getStartTime().compareTo(shftstart)<=0 && le.getEndTime().compareTo(frsthalfend)<=0)
						{
						
							le.setFrststatus("OD");
							String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
						}
						else if(le.getStartTime().compareTo(shftstart)<=0 && le.getEndTime().compareTo(frsthalfend)>0)
						{
						
							le.setStatus("ODOD");
							String a="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
			                int frst=ad.SqlExecuteUpdate(a);
						}
						
						if(le.getStatus()==null)
						{
							le.setStatus("");
						}
						if(le.getFrststatus()==null)
						{
							le.setFrststatus("");
						}
						if(le.getSecstatus()==null)
						{
							le.setSecstatus("");
						}
					
				
						}
							
						else
						{
							if(le.getStartTime().compareTo(shftstart)<=0 && le.getEndTime().compareTo(sechalfstart)>=0 )
							{
								le.setStatus("ODOD");
								
								if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
								{
								String a="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a);
								}
								else
								{
									String a="update EMP_IN_OUT_Status set "+FullDayPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
					                int frst=ad.SqlExecuteUpdate(a);
					                
					                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
					                {
					                	String a1="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
						                int end=ad.SqlExecuteUpdate(a1);
					                }
					                
					                
					            	String a1="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
					                int end=ad.SqlExecuteUpdate(a1);
								}
							
							}
							else if(le.getStartTime().compareTo(frsthalfend)>=0 && (le.getEndTime().compareTo(shftend)<=0 || le.getEndTime().compareTo(shftend)>=0))
							{
							 	le.setStatus("ODOD");
								le.setSecstatus("OD");
								
								if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
								{
								String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a);
								}
								else

								{
									String a1="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("stday")+"="+SecondHalfPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
					                int frst=ad.SqlExecuteUpdate(a1);
					                
					                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
					                {
					                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
						                int end=ad.SqlExecuteUpdate(a1a);
					                }
									
									String a="update EMP_IN_OUT_Status set "+SecondHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
					                int frst1=ad.SqlExecuteUpdate(a);
								}
				                	
							}
							
							else if(le.getEndTime().compareTo(sechalfstart)<=0 && le.getStartTime().compareTo(shftstart)<=0)
							{
								  
				              
				            	le.setStatus("ODOD");
								le.setFrststatus("OD");
								
								if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
								{
								String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a);
								}
								else
								{
									String a1="update EMP_IN_OUT_Status set "+FrstHalfPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
					                int frst=ad.SqlExecuteUpdate(a1);
					                
					                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
					                {
					                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
						                int end=ad.SqlExecuteUpdate(a1a);
					                }
									
									String a="update EMP_IN_OUT_Status set day"+rsshiftregister.getString("enday")+"="+FrstHalf(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
					                int frst1=ad.SqlExecuteUpdate(a);
								}
				               
							}
							
							else if(le.getStartTime().compareTo(shftstart)>=0 &&  (le.getEndTime().compareTo(shftend)<=0 || le.getEndTime().compareTo(shftend)>=0) )
							{
								le.setStatus("ODOD");
								
								if(rsshiftregister.getString("stMon").equalsIgnoreCase(rsshiftregister.getString("enMon")))
								{
								String a="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
				                int frst=ad.SqlExecuteUpdate(a);
								}
								else
								{
									String a="update EMP_IN_OUT_Status set "+FullDayPrev(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("stMon")+" and year="+filedateyear+" ";
					                int frst=ad.SqlExecuteUpdate(a);
					                
					                for(int j=Integer.parseInt(rsshiftregister.getString("stMon"))+1;j<Integer.parseInt(rsshiftregister.getString("enMon"));j++)
					                {
					                	String a1a="update EMP_IN_OUT_Status set "+FullDay_Inbetween_month(j,filedateyear,le.getStatus())+" where pernr="+rsshiftregister.getString("user_id")+" and month="+j+" and year="+filedateyear+" ";
						                int end=ad.SqlExecuteUpdate(a1a);
					                }
					                
					            	String a1="update EMP_IN_OUT_Status set "+FullDay(Integer.parseInt(rsshiftregister.getString("stday")), Integer.parseInt(rsshiftregister.getString("enday")),le.getFrststatus(),le.getSecstatus(),le.getStatus(),Integer.parseInt(rsshiftregister.getString("stMon")),Integer.parseInt(rsshiftregister.getString("enMon")))+" where pernr="+rsshiftregister.getString("user_id")+" and month="+rsshiftregister.getString("enMon")+" and year="+filedateyear+" ";
					                int end=ad.SqlExecuteUpdate(a1);
								}
							
							}
							
							
							if(le.getStatus()==null)
							{
								le.setStatus("");
							}
							if(le.getFrststatus()==null)
							{
								le.setFrststatus("");
							}
							if(le.getSecstatus()==null)
							{
								le.setSecstatus("");
							}
							
			
	                  
								}
							
						 le.setStartDurationType(frsthalfend);
						 le.setEndDurationType(sechalfstart);
						 le.setMonth(filedatemonth);
						 le.setYear(filedateyear);
						 
						 //update
						 
					
						onduty.add(le);
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			    
			    try {
					rsshiftregister.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return onduty;
		}
	
	public String[] getempshifttime(String shiftcode)
	{

		String shftstrtime="";
		String shftendtime="";
		String firsthalfend="";
		String sechalfstart="";
		String punchstrtime="";
		String punchendtime="";
		String punchvalidtime="";
		
		int nightshift=0;
		
		conn=AttConnectionFactory.getConnection();
		
		 String timings="";
		  String shiftregister="select Punch_Valid_till,Punch_Start_Time,Punch_End_Time,Shift_code,Shift_start_time,Shift_End_Time,First_Half_End_Time,SH_Start_Time,Night_Shift from EMP_SHIFT_MASTER where Shift_code = '"+shiftcode+"'";
		    ResultSet rsshiftregister=ad.selectQuery(shiftregister);
		    try {
				if(rsshiftregister.next())
				{
					
					shftstrtime=rsshiftregister.getString("Shift_start_time");
					shftendtime=rsshiftregister.getString("Shift_End_Time");
					firsthalfend=rsshiftregister.getString("First_Half_End_Time");
					sechalfstart=rsshiftregister.getString("SH_Start_Time");
					nightshift=Integer.parseInt(rsshiftregister.getString("Night_Shift"));
					punchstrtime=rsshiftregister.getString("Punch_Start_Time");
					punchendtime=rsshiftregister.getString("Punch_End_Time");
					punchvalidtime=rsshiftregister.getString("Punch_Valid_till");
					timings=shftstrtime+","+shftendtime+","+firsthalfend+","+sechalfstart+","+nightshift+","+punchstrtime+","+punchendtime+","+punchvalidtime;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    

		    
		    String a[]=timings.split(",");
		
			try {
				rsshiftregister.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				
			}
		return a;
	}
	
	
	
	
	public String[] getpunchtimings(String date,String empcode)
	{


		 String punchrange="";
		  String shiftregister="select * from(select left(id,2) as day,right(left(id,4),2) as mont,right(left(id,6),2)+':'+right(left(id,8),2) as time,SUBSTRING ( id ,9 ,len(id))  as pernr from ATTENDANCE_MASTER where Date='"+date+"' )t where  t.pernr='"+empcode+"' order by t.time";
		    ResultSet rsshiftregister=ad.selectQuery(shiftregister);
		    try {
				while(rsshiftregister.next())
				{
					
					punchrange=punchrange+","+rsshiftregister.getString("time");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String a[]=new String[0];
		    if(!punchrange.equalsIgnoreCase(""))
		    {
		    punchrange=punchrange.substring(1, punchrange.length());
		     a=punchrange.split(",");
		    }
		    
		    try {
				rsshiftregister.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return a;
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
		if(location.equalsIgnoreCase("ML27"))
			tableName="ML27CAL";
		if(location.equalsIgnoreCase("ML92"))
			tableName="ML92CAL";
	
		return tableName;
	}
	
	
	

	
	
	
	public ActionForward updateShiftAssign(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		HRApprovalForm approvalsForm = (HRApprovalForm) form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		
		String pernr[] = request.getParameterValues("employeeno");
		String shiflist[]= request.getParameterValues("shiflist");
		String rule_Code[]= request.getParameterValues("rule_Code");
		String swipe_Count[]= request.getParameterValues("swipe_Count");
		
		for(int i=0;i<pernr.length;i++)
		{
			String update ="update emp_official_info set Shift_Code = '"+shiflist[i]+"' , Rule_Code= '"+rule_Code[i]+"' , Swipe_Count='"+swipe_Count[i]+"' "
					+ " where pernr ='"+pernr[i]+"' ";
			int l=ad.SqlExecuteUpdate(update);
			
			Date dNow = new Date( );
			 SimpleDateFormat ft = new SimpleDateFormat ("dd");
			 SimpleDateFormat ft1 = new SimpleDateFormat ("MM");
		int day = Integer.parseInt(ft.format(dNow));
		int month = Integer.parseInt(ft1.format(dNow));
		
		for(int j=day; j<=31; j++ )
		{
			String update2 = "update EMP_Shift_Register set day"+j+" =  '"+shiflist[i]+"' where month ='"+month+"' and pernr= '"+pernr[i]+"' ";
			int k=ad.SqlExecuteUpdate(update2);
		}
		

		String update3 = "update EMP_Shift_Register set   day1 =  '"+shiflist[i]+"',"
														+" day2 =  '"+shiflist[i]+"',"
														+" day3 =  '"+shiflist[i]+"',"
														+" day4 =  '"+shiflist[i]+"',"
														+" day5 =  '"+shiflist[i]+"',"
														+" day6 =  '"+shiflist[i]+"',"
														+" day7 =  '"+shiflist[i]+"',"
														+" day8 =  '"+shiflist[i]+"',"
														+" day9 =  '"+shiflist[i]+"',"
														+" day10 =  '"+shiflist[i]+"',"
														+" day11 =  '"+shiflist[i]+"',"
														+" day12 =  '"+shiflist[i]+"',"
														+" day13 =  '"+shiflist[i]+"',"
														+" day14 =  '"+shiflist[i]+"',"
														+" day15 =  '"+shiflist[i]+"',"
														+" day16 =  '"+shiflist[i]+"',"
														+" day17 =  '"+shiflist[i]+"',"
														+" day18 =  '"+shiflist[i]+"',"
														+" day19 =  '"+shiflist[i]+"',"
														+" day20 =  '"+shiflist[i]+"',"
														+" day21 =  '"+shiflist[i]+"',"
														+" day22 =  '"+shiflist[i]+"',"
														+" day23 =  '"+shiflist[i]+"',"
														+" day24 =  '"+shiflist[i]+"',"
														+" day25 =  '"+shiflist[i]+"',"
														+" day26 =  '"+shiflist[i]+"',"
														+" day27 =  '"+shiflist[i]+"',"
														+" day28 =  '"+shiflist[i]+"',"
														+" day29 =  '"+shiflist[i]+"',"
														+" day30 =  '"+shiflist[i]+"',"
														+" day31 =  '"+shiflist[i]+"'"
														+" where month >'"+month+"' and pernr= '"+pernr[i]+"' ";
		int s=ad.SqlExecuteUpdate(update3);
		
		
			
			approvalsForm.setMessage("Shift Assigned");
		}
		
		

		String cmpcode="";
		int id=user.getGroupId();
		String cmp[]=new String[0]; 
		String locations="";
		
		String aa="select * from user_group where id='"+id+"'";
		ResultSet cc=ad.selectQuery(aa);
		try {
			if(cc.next())
			{
				cmpcode=cc.getString("Attendance_Loc");
				
				if(cmpcode.contains(","))
				{
				 cmp=cmpcode.split(",");
				
				for(int b=0;b<cmp.length;b++)
				{
					locations=locations+"'"+cmp[b]+"',";
				}
				
				locations=locations.substring(0, locations.length()-1);
				}
				else
				{
					locations="'"+cmpcode+"'";
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location where location_code in ("+locations+")"); 
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
						
					}
					rs11.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				approvalsForm.setLocationIdList(locationList);
				approvalsForm.setLocationLabelList(locationLabelList);
		
	
				ArrayList payGroupList=new ArrayList();
				ArrayList payGroupLabelList=new ArrayList();
				ResultSet rs12 = ad.selectQuery("select Paygroup," +
						"Long_Desc from Paygroup_Master"); 
						try {
							while(rs12.next()) {
								payGroupList.add(rs12.getString("Paygroup"));
								payGroupLabelList.add(rs12.getString("Long_Desc"));
							}
							rs12.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				approvalsForm.setPayGroupList(payGroupList);
				approvalsForm.setPayGroupLabelList(payGroupLabelList);
				
				ArrayList categoryList=new ArrayList();
				ArrayList categoryLabelList=new ArrayList();
				ResultSet rs13 = ad.selectQuery("select STAFFCAT," +
						"CATLTEXT from category"); 
						try {
							while(rs13.next()) {
								categoryList.add(rs13.getString("STAFFCAT"));
								categoryLabelList.add(rs13.getString("CATLTEXT"));
							}
							rs13.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				approvalsForm.setCategoryList(categoryList);
				approvalsForm.setCategoryLabelList(categoryLabelList);
				
				
				ArrayList workList=new ArrayList();
				ArrayList workLabelList=new ArrayList();
				ResultSet rs14 = ad.selectQuery("select loc_id," +
						"loc_name from working_location order by loc_name "); 
						try {
							while(rs14.next()) {
								workList.add(rs14.getString("loc_id"));
								workLabelList.add(rs14.getString("loc_name"));
							}
							rs14.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				approvalsForm.setWorkList(workList);
				approvalsForm.setWorkLabelList(workLabelList);
				
				
				ArrayList deptList=new ArrayList();
				ArrayList deptLabelList=new ArrayList();
				ResultSet rs16 = ad.selectQuery("select dptid," +
						"DPTLTXT from DEPARTMENT order by DPTLTXT "); 
						try {
							while(rs16.next()) {
								deptList.add(rs16.getString("dptid"));
								deptLabelList.add(rs16.getString("DPTLTXT"));
							}
							rs16.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						approvalsForm.setDeptList(deptList);
						approvalsForm.setDeptLabelList(deptLabelList);
				
				ArrayList shiftList=new ArrayList();
				ArrayList shiftLabelList=new ArrayList();
				ResultSet rs15 = ad.selectQuery("select shift_code," +
						"Shift_Name from EMP_SHIFT_MASTER"); 
						try {
							while(rs15.next()) {
								shiftList.add(rs15.getString("shift_code"));
								shiftLabelList.add(rs15.getString("Shift_Name"));
							}
							rs15.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				approvalsForm.setShiftList(shiftList);
				approvalsForm.setShiftLabelList(shiftLabelList);
				
				
				String ACtualdept="";
			    if(approvalsForm.getDeptArray()!=null)
			    {	
			    String[] dept = approvalsForm.getDeptArray();
			    
			    StringBuffer reqdept = new StringBuffer();
				for (int i = 0; i < dept.length; i++) {
					reqdept.append("'"+dept[i]+"'" + ",");
				}
				 ACtualdept= reqdept.substring(0, reqdept.length() - 1).toString();
			    }
				
				   String ACtualloc="";
				    if(approvalsForm.getLocArray()!=null)
				    {
				    
					String[] loc = approvalsForm.getLocArray();
				    StringBuffer reqloc = new StringBuffer();
					for (int i = 0; i < loc.length; i++) {
						reqloc.append("'"+loc[i]+"'" + ",");
					}
					ACtualloc = reqloc.substring(0, reqloc.length() - 1).toString();
				    
				    }
				    
				    String ACtualpay="";
				    if(approvalsForm.getPayArray()!=null)
				    {
				    
					String[] pay = approvalsForm.getPayArray();
				    StringBuffer reqpay = new StringBuffer();
					for (int i = 0; i < pay.length; i++) {
						reqpay.append("'"+pay[i]+"'" + ",");
					}
					ACtualpay = reqpay.substring(0, reqpay.length() - 1).toString();
				    
				    }
				    
				    //catArray
				    String ACtualcat="";
				    if(approvalsForm.getCatArray()!=null)
				    {
				    
					String[] cat = approvalsForm.getCatArray();
				    StringBuffer reqcat = new StringBuffer();
					for (int i = 0; i < cat.length; i++) {
						reqcat.append("'"+cat[i]+"'" + ",");
					}
					ACtualcat = reqcat.substring(0, reqcat.length() - 1).toString();
				    
				    }
				
				
				ArrayList list = new ArrayList();
				String query1="select * from emp_official_info ,location ,DEPARTMENT ,DESIGNATION where Location.LOCATION_CODE=emp_official_info.LOCID and "
						+ " DESIGNATION.DSGID =emp_official_info.DSGID and DEPARTMENT.DPTID =emp_official_info.DPTID";
				String sqlfromdate="";
				String sqltodate="";
				
				
				
				
				if(!approvalsForm.getLocationId().equalsIgnoreCase(""))
				{
					query1=query1+" and emp_official_info.LOCID = '"+approvalsForm.getLocationId()+"' ";
				
				}
				
				
				
				if(!ACtualloc.equalsIgnoreCase(""))
				{
					query1=query1+" and emp_official_info.Work_ID in("+ACtualloc+") ";
				
				}
					
				if(!ACtualpay.equalsIgnoreCase(""))
				{
					query1=query1+" and emp_official_info.PAY_GROUP in("+ACtualpay+")  ";
				
				}

				if(!ACtualcat.equalsIgnoreCase(""))
				{
					query1=query1+" and emp_official_info.STAFFCAT in ("+ACtualcat+") ";
				
				}
				
				
				if(!approvalsForm.getFromDate().equalsIgnoreCase(""))
				{
					String from[]=approvalsForm.getFromDate().split("/");
				    sqlfromdate=from[2]+"-"+from[1]+"-"+from[0]; 
				    
				    String to[]=approvalsForm.getToDate().split("/");
				    sqltodate=to[2]+"-"+to[1]+"-"+to[0];
				    
				    query1=query1+" and doj between '"+sqlfromdate+"' and '"+sqltodate+"' ";
					
				}
				
				
				if(!approvalsForm.getFrompernr().equalsIgnoreCase(""))
				{
					query1=query1+" and emp_official_info.pernr between '"+approvalsForm.getFrompernr()+"' and '"+approvalsForm.getTopernr()+"' ";
				
				}
				
				//Status shif assignment
				if(approvalsForm.getStatus().equalsIgnoreCase("Pending For Shift Assignment"))
				{
				query1=query1+" and (Shift_Code is null or Shift_Code='') ";
					
				}
				
				
				
				
				query1=query1+" and ACTIVE =1 ";
				
				ResultSet rs = ad.selectQuery(query1);
				try {
					while(rs.next())
					{
						
						HRApprovalForm help = new HRApprovalForm();
						help.setEmployeeno(rs.getString("pernr"));
						help.setEmployeeName(rs.getString("EMP_FULLNAME"));
						help.setPlant(rs.getString("LOCNAME"));
						help.setShift(rs.getString("Shift_Code"));
						help.setRule_Code(rs.getString("rule_Code"));
						help.setSwipe_Count(rs.getString("swipe_Count"));
						help.setDesignation(rs.getString("DSGSTXT"));
						help.setDepartment(rs.getString("DPTSTXT"));
						help.setDoj(rs.getString("doj"));
						help.setPaygrp(rs.getString("PAY_GROUP"));
						list.add(help);

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("list", list);
				try {
					rs13.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					cc.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					rs14.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					rs12.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					rs16.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					rs11.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					rs15.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return mapping.findForward("shiftassign");
	}
	
	public ActionForward empmanualtimeentrySearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
			HRApprovalForm approvalsForm = (HRApprovalForm) form;
			int i = 0;
		    ArrayList llist = new ArrayList();
		    ArrayList punchlist = new ArrayList();
		    String sqlfromdate="";
		    
		    String from[]=approvalsForm.getFromDate().split("/");
		    sqlfromdate=from[2]+"-"+from[1]+"-"+from[0]; 
		    
		    
//punch rtimings
		    
		    

			//Attendance

			SAPAttendenceDAO dao = new SAPAttendenceDAO();
			
			
			String cmpcode="";
			int id=user.getGroupId();
			String cmp[]=new String[0]; 
			String locations="";
			
			String aa="select * from user_group where id='"+id+"'";
			ResultSet cc=ad.selectQuery(aa);
			try {
				if(cc.next())
				{
					cmpcode=cc.getString("Attendance_Loc");
					
					if(cmpcode.contains(","))
					{
					 cmp=cmpcode.split(",");
					
					for(int b=0;b<cmp.length;b++)
					{
						locations=locations+"'"+cmp[b]+"',";
					}
					
					locations=locations.substring(0, locations.length()-1);
					}
					else
					{
						locations="'"+cmpcode+"'";
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


				String s9 = " select emp_official_info.pernr,date,convert(varchar(11),date,106) as date1,left(DATENAME(WEEKDAY,date),3) as day,left(In_time,5) as intime,left(out_time,5) as outtime,"
						+ "left(status,2) as instatus,right(status,2) as outstatus,shift from Report_Daily_wise,emp_official_info where emp_official_info.PERNR=Report_Daily_wise.Pernr and emp_official_info.LOCID=Report_Daily_wise.Location"
						+ " and  emp_official_info.Pernr='"+approvalsForm.getEmployeeno()+"' and date='"+sqlfromdate+"' and emp_official_info.LOCID in ("+locations+")  ";
				ResultSet rs9 = ad.selectQuery(s9);
				try {
					while(rs9.next())
					{
						
						SAPAttendenceForm help = new SAPAttendenceForm();
						help.setEmployeeNo(rs9.getString("pernr"));
						help.setDate(rs9.getString("date1"));
						help.setDay(rs9.getString("day"));
						help.setiNTIME(rs9.getString("intime"));
						help.setoUTTIME(rs9.getString("outtime"));
						help.setiNSTATUS(rs9.getString("instatus"));
						help.setoUTSTATUS(rs9.getString("outstatus"));
						help.setShift(rs9.getString("shift"));
						punchlist.add(help);
						
						
			    	}
				    	

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.setAttribute("punchlist", punchlist);
		    
		    
			//Employee Late Count List
			String query1="select CONVERT(varchar(10),Start_date,103) as Startdate ,EMP_MANUAL_SWIPE. *,emp_official_info.EMP_FULLNAME,CONVERT(varchar(10),EMP_MANUAL_SWIPE.Created_date,103)+' '+ CONVERT(varchar(5),EMP_MANUAL_SWIPE.Created_date,108) as Created_date1"
					+ " from EMP_MANUAL_SWIPE,emp_official_info where EMP_MANUAL_SWIPE.Pernr=emp_official_info.pernr and EMP_MANUAL_SWIPE.pernr= '"+approvalsForm.getEmployeeno()+"' and month(start_date) = month('"+sqlfromdate+"') "
							+ "and month(start_date) = month('"+sqlfromdate+"') and  emp_official_info.LOCID in ("+locations+") order by date desc ";
			ResultSet rsk = ad.selectQuery(query1);
			try {
				while(rsk.next())
				{  i++;
					HRApprovalForm help1 = new HRApprovalForm();
					help1.setEmployeeno(rsk.getString("pernr"));
					help1.setEmployeeName(rsk.getString("EMP_FULLNAME"));
					help1.setStartDate(rsk.getString("Startdate"));
					if(rsk.getString("in_out").equalsIgnoreCase("I"))
						help1.setSwipe_Type("IN");	
					if(rsk.getString("in_out").equalsIgnoreCase("O"))
					help1.setSwipe_Type("OUT");
					help1.setReason_Type(rsk.getString("lost_entry_reason_Type"));
					if(rsk.getString("start")!=null)
						help1.setTime(rsk.getString("start").substring(0, 5));
					help1.setRemarks(rsk.getString("remarks"));
					if(rsk.getString("prev_time")!=null)
						help1.setPrev_time(rsk.getString("prev_time").substring(0, 5));
					help1.setDate(rsk.getString("Created_date1"));
					llist.add(help1);

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String emp = "select count(distinct Start_date) from EMP_MANUAL_SWIPE ,emp_official_info  where late_flag = 1 and emp_official_info.PERNR= '"+approvalsForm.getEmployeeno()+"' and year(start_date) = year('"+sqlfromdate+"') "
							+ " and month(start_date) = month('"+sqlfromdate+"') and EMP_MANUAL_SWIPE.Pernr=emp_official_info.PERNR  ";
			ResultSet rsw = ad.selectQuery(emp);
			try {
				if(rsw.next())
				{
					i = rsw.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			approvalsForm.setCount(Integer.toString(i));
			request.setAttribute("llist", llist);
			
			
		
		    ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				ResultSet rs11 = ad.selectQuery("select LOCID," +
						"LOCNAME,location_code from location where location_code in ("+locations+") "); 
						try {
							while(rs11.next()) {
								locationList.add(rs11.getString("location_code"));
								locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
								
							}
							rs11.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						approvalsForm.setLocationIdList(locationList);
						approvalsForm.setLocationLabelList(locationLabelList);
						
						
						
						
								ArrayList workList=new ArrayList();
								ArrayList workLabelList=new ArrayList();
								ResultSet rs14 = ad.selectQuery("select loc_id," +
										"loc_name from working_location order by loc_name"); 
										try {
											while(rs14.next()) {
												workList.add(rs14.getString("loc_id"));
												workLabelList.add(rs14.getString("loc_name"));
											}
											rs14.close();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										approvalsForm.setWorkList(workList);
										approvalsForm.setWorkLabelList(workLabelList);
										
										
										
										//repgrp
										ArrayList repgrptList=new ArrayList();
										ArrayList repgrpLabelList=new ArrayList();
										ResultSet reprs15 = ad.selectQuery("select Grp_ID," +
												"Grp_LTXT from REPORTING_GROUP   order by Grp_LTXT "); 
												try {
													while(reprs15.next()) {
														repgrptList.add(reprs15.getString("Grp_ID"));
														repgrpLabelList.add(reprs15.getString("Grp_LTXT"));
													}
													reprs15.close();
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
												approvalsForm.setRepgrpList(repgrptList);
												approvalsForm.setRepgrpLabelList(repgrpLabelList);
												
												try {
													rs11.close();
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											 
											    
											    try {
													rs11.close();
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   
											    try {
													rs14.close();
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   
											    try {
													cc.close();
												} catch (SQLException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											   
			
				return mapping.findForward("manual_entry");
				} 
	
	
	
	public ActionForward searchForManualApprovers(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {HttpSession session = request.getSession();
			UserInfo user = (UserInfo) session.getAttribute("user");

			String sTxt = request.getParameter("searchText");
			String reqFieldName = request.getParameter("reqFieldName");
			HRApprovalForm f = (HRApprovalForm) form;

			f.setSearchText(sTxt);
			MailInboxAction mAction = new MailInboxAction();
			LinkedList searchList = new LinkedList();
			
			String cmpcode1="";
			int id=user.getGroupId();
			String cmp[]=new String[0]; 
			String locations="";
			
			
			String aa1="select * from user_group where id='"+id+"'";
			ResultSet cc1=ad.selectQuery(aa1);
			try {
				if(cc1.next())
				{
					cmpcode1=cc1.getString("Attendance_Loc");
					
					if(cmpcode1.contains(","))
					{
					 cmp=cmpcode1.split(",");
					
					for(int b=0;b<cmp.length;b++)
					{
						locations=locations+"'"+cmp[b]+"',";
					}
					
					locations=locations.substring(0, locations.length()-1);
					}
					else
					{
						locations="'"+cmpcode1+"'";
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			try {
				
				String searchQuery = "select dpt.DPTSTXT,EMP_FULLNAME,PERNR,DPTSTXT ,"
						+ "LOCID,Category.CATLTEXT, Paygroup_Master.short_desc"
						+ " from Category, emp_official_info as emp,DEPARTMENT as dpt ,Paygroup_Master where dpt.DPTID=emp.DPTID and (EMP_FULLNAME like '%"
						+ sTxt
						+ "%' or EMAIL_ID like '%"
						+ sTxt
						+ "%' or PERNR like'%" + sTxt + "%') and Paygroup_Master.paygroup=emp.pay_group "
						+ " and Category.staffcat=emp.staffcat and emp.locid in("+locations+") ";
				ResultSet rs = ad.selectQuery(searchQuery);
				int i = 1;
				while (rs.next()) {
					JoiningFormalityForm emp = new JoiningFormalityForm();
					emp.setEmpId(rs.getString("EMP_FULLNAME") + "-"
							+ rs.getString("PERNR") + "-" + rs.getString("DPTSTXT")+ "-" + rs.getString("LOCID")+ "-" + rs.getString("CATLTEXT")+ "-" + rs.getString("short_desc"));   
					
					emp.setEmpname(rs.getString("EMP_FULLNAME"));   
					emp.setEmployeeNo(rs.getString("PERNR"));
					emp.setDept(rs.getString("DPTSTXT"));
					emp.setLocation(rs.getString("LOCID"));
					emp.setCategory(rs.getString("CATLTEXT"));
					emp.setPaygrp( rs.getString("short_desc"));
					emp.setId(reqFieldName);
					searchList.add(emp);
					if (i == 100) {
						break;
					}
					i++;
				}
				rs.close();
			
			
			if(searchList.size()==0)
			{
			String a[]=null;		
			String actualtext="";
	        if(sTxt.contains(" "))
	        {
	        	a=sTxt.split(" ");
	        
			 for(int h=0;h<a.length;h++)
	        {
	             if(!a[h].equalsIgnoreCase(""))
				 actualtext=actualtext+"EMP_FULLNAME like '%"+a[h]+"%' or EMAIL_ID like '%"+a[h]+"%' or PERNR like'%"+a[h]+"%' or ";
	        }
			 
			 
			 actualtext=actualtext.substring(0, actualtext.length()-3);
			
			 
	        }
	        
	        else
	        {
	        	actualtext="EMP_FULLNAME like '%"+sTxt+"%' or EMAIL_ID like '%"+sTxt+"%' or PERNR like'%"+sTxt+"%'";
	        }
	        
	       
	      
	    	String searchQuery1 = "select dpt.DPTSTXT,EMP_FULLNAME,PERNR,DPTSTXT ,"
					+ "LOCID,Category.CATLTEXT, Paygroup_Master.short_desc"
					+ " from Category, emp_official_info as emp,DEPARTMENT as dpt ,Paygroup_Master where dpt.DPTID=emp.DPTID and ("+actualtext+") and Paygroup_Master.paygroup=emp.pay_group "
					+ " and Category.staffcat=emp.staffcat and emp.locid in("+locations+") ";
			ResultSet rs1 = ad.selectQuery(searchQuery1);
			int i1 = 1;
			while (rs1.next()) {
				JoiningFormalityForm emp = new JoiningFormalityForm();
				emp.setEmpId(rs1.getString("EMP_FULLNAME") + "-"
						+ rs1.getString("PERNR") + "-" + rs1.getString("DPTSTXT")+ "-" + rs1.getString("LOCID")+ "-" + rs1.getString("CATLTEXT")+ "-" + rs1.getString("short_desc"));   
				
				emp.setEmpname(rs1.getString("EMP_FULLNAME"));   
				emp.setEmployeeNo(rs1.getString("PERNR"));
				emp.setDept(rs1.getString("DPTSTXT"));
				emp.setLocation(rs1.getString("LOCID"));
				emp.setCategory(rs1.getString("CATLTEXT"));
				emp.setPaygrp( rs1.getString("short_desc"));
				emp.setId(reqFieldName);
				searchList.add(emp);
				if (i1 == 100) {
					break;
				}
				i1++;
			}rs1.close();
			
			}
			
			}
			

			catch (SQLException sqle) {

				sqle.printStackTrace();
			}

			request.setAttribute("emp", searchList);
			try {
				cc1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mapping.findForward("searchemployee");
			}
	
	public ActionForward processEmpmanualtimeentry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");

			HRApprovalForm help = (HRApprovalForm) form;
			String cmpcode="";
			int id=user.getGroupId();
			
			/*String aa="select * from user_group where id='"+id+"'";
			ResultSet cc=ad.selectQuery(aa);
			try {
				if(cc.next())
				{
					cmpcode=cc.getString("Attendance_Loc");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String cmp[]=new String[0]; 
			if(cmpcode.contains(","))
			{
			 cmp=cmpcode.split(",");
			}*/
			
			
			
			cmpcode=help.getLocationId();
			
			
			String ACtualloc="";
		    if(help.getLocArray()!=null)
		    {
		    
			String[] loc = help.getLocArray();
		    StringBuffer reqloc = new StringBuffer();
			for (int i = 0; i < loc.length; i++) {
				reqloc.append(""+loc[i]+" " + ",");
			}
			ACtualloc = reqloc.substring(0, reqloc.length() - 1).toString();
		    
		    }
		
			  String m="Select day(dateadd(mm,DateDiff(mm, -1, getdate()),0) -1) as days1 , month(getdate()) as month1 ,year(getdate()) as year1 ,"
					  + "day(dateadd(mm,DateDiff(mm, -1, DATEADD(MONTH, -1, getdate()) ),0) -1) as days2 , month(DATEADD(MONTH, -1, getdate()) ) as month2 ,year(DATEADD(MONTH, -1, getdate()) ) as year2 "
					  + ", day(dateadd(mm,DateDiff(mm, -1, DATEADD(MONTH, -2, getdate()) ),0) -1) as days3 , month(DATEADD(MONTH, -2, getdate()) ) as month3 ,year(DATEADD(MONTH, -2, getdate()) ) as year3 ";
					  		  
					  		 ResultSet rsas=ad.selectQuery(m);
					  		 try {
								if(rsas.next())
								 {
								
									String q2="EXEC daily_report_status_Att_manual '"+rsas.getString("days1")+"','"+rsas.getString("month1")+"','"+rsas.getString("year1")+"','"+cmpcode+"','"+ACtualloc+"' ";
									int k1=ad.SqlExecuteUpdate(q2);
											
											 String q12=" EXEC daily_report_status_Att_manual '"+rsas.getString("days2")+"','"+rsas.getString("month2")+"','"+rsas.getString("year2")+"','"+cmpcode+"' ,'"+ACtualloc+"' ";
									int k12=ad.SqlExecuteUpdate(q12);	
									
									
								/*	String q21=" EXEC daily_report_status_Att_manual '"+rsas.getString("days3")+"','"+rsas.getString("month3")+"','"+rsas.getString("year3")+"','"+cmpcode+"'";
												int k31=ad.SqlExecuteUpdate(q21);*/
												
												
												
//First half Late
												
												String late="update Report_Daily_wise set late = convert(nvarchar(10),floor(datediff(minute,Come_Late_by,In_time)/60))+':'+convert(nvarchar(10),"
											+ "datediff(minute,Come_Late_by,In_time)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code   "
											+ "   and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and In_time>Come_Late_by and In_time < First_Half_End_Time and Location='"+cmpcode+"' "
											+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,getdate()) and DATEADD(MONTH,1,getdate())";			 
												int klate=ad.SqlExecuteUpdate(late);
												
												//Second half Late
												
												/*String slate="update Report_Daily_wise set late = convert(nvarchar(10),floor(datediff(minute,SH_Start_Time,In_time)/60))+':'+convert(nvarchar(10),	"
											+ "datediff(minute,SH_Start_Time,In_time)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code"
											+ "  and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and  In_time > SH_Start_Time and SH_Start_Time>'00:00' and Location='"+cmpcode+"' and "
											+ "month(Report_Daily_wise.Date) between Month(DATEADD(MONTH,-4,getdate())) and Month(DATEADD(MONTH,0,getdate()))";		 
															int sqlate=ad.SqlExecuteUpdate(slate);*/
															
												///go early by 		
															
											String early="update Report_Daily_wise set early= convert(nvarchar(10),floor(datediff(minute,Out_time,Go_Early_by)/60))+':'+convert(nvarchar(10),"
									+ "datediff(minute,Out_time,Go_Early_by)%60) from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code  "
									+ "   and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code  and Out_time < Go_Early_by and Out_time > SH_Start_Time   and Location='"+cmpcode+"'"
									+ " and Report_Daily_wise.Date between DATEADD(MONTH,-4,getdate()) and DATEADD(MONTH,1,getdate())";				
											int earsly=ad.SqlExecuteUpdate(early);			
											
											
										
													
													
													//Total hrs
													
													
													String tot="update Report_Daily_wise set total=case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),floor(datediff(minute,In_time,Out_time)/60))+':'+convert(nvarchar(10),datediff(minute,In_time,Out_time)%60) else convert(nvarchar(10),floor(datediff(minute,cast(In_time as DATETIME),dateadd(day,1,cast(Out_time as DATETIME)))/60))+':'+convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),dateadd(day,1,cast(Out_time as DATETIME)))%60) end "
															+ "from EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
															+ "  and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and Location='"+cmpcode+"' and Report_Daily_wise.Date"
															+ " between DATEADD(MONTH,-4,getdate()) and DATEADD(MONTH,1,getdate()) and Total is null and In_time>'00:00:00' and Out_time>'00:00:00'";
															int rr=ad.SqlExecuteUpdate(tot);		
															
															
												///OT HRS FULL TIME
															
															String otful="update Report_Daily_wise set OT=convert(nvarchar(10),floor((case when t.min<(case  when datediff(minute,In_time,Out_time)>0 then"
									+ " convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),"
									+ " dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)  then t.min  else (case  when datediff(minute,In_time,Out_time)>0 then  "
									+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
									+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)end)/60))+':'+ convert(nvarchar(10),(case when t.min<(case  when "
									+ "datediff(minute,In_time,Out_time)>0 then convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else "
									+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)  then t.min   "
									+ " else (case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else "
									+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end)end)%60) from "
									+ "EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER,(select sum(NoHRS)*60 as min,Pernr,FromDate from OVER_TIME_DETAILS where FromDate between DATEADD(MONTH,-4,getdate())"
									+ " and DATEADD(MONTH,1,getdate()) and ApprvrStatus='Approved' group by Pernr,FromDate)t	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
									+ "and t.Pernr=Report_Daily_wise.Pernr  and t.FromDate=Report_Daily_wise.Date and	0<case  when datediff(minute,In_time,Out_time)>0 then  "
									+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_FULL else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
									+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_FULL end and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and Location='"+cmpcode+"' "
									+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,getdate()) and DATEADD(MONTH,1,getdate()) and Report_Daily_wise.Status not like '%AA%' "
									+ "and In_time>'00:00:00' and Out_time>'00:00:00'";			
															int rr6=ad.SqlExecuteUpdate(otful);	
															
															
															//OT hrs half time
															
															String othalf="update Report_Daily_wise set OT=convert(nvarchar(10),floor((case when t.min<(case  when datediff(minute,In_time,Out_time)>0 then"
									+ " convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME),"
									+ " dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)  then t.min  else (case  when datediff(minute,In_time,Out_time)>0 then  "
									+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
									+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)end)/60))+':'+ convert(nvarchar(10),(case when t.min<(case  when "
									+ "datediff(minute,In_time,Out_time)>0 then convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else "
									+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)  then t.min   "
									+ " else (case  when datediff(minute,In_time,Out_time)>0 then  convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else "
									+ "convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end)end)%60) from "
									+ "EMP_SHIFT_LATE_RULES ,EMP_SHIFT_MASTER,(select sum(NoHRS)*60 as min,Pernr,FromDate from OVER_TIME_DETAILS where FromDate between DATEADD(MONTH,-4,getdate())"
									+ " and DATEADD(MONTH,1,getdate()) and ApprvrStatus='Approved' group by Pernr,FromDate)t	where  EMP_SHIFT_MASTER.Shift_code = EMP_SHIFT_LATE_RULES.Rule_Code "
									+ "and t.Pernr=Report_Daily_wise.Pernr  and t.FromDate=Report_Daily_wise.Date and	0<case  when datediff(minute,In_time,Out_time)>0 then  "
									+ "convert(nvarchar(10),datediff(minute,In_time,Out_time))-EMP_SHIFT_MASTER.OT_half else convert(nvarchar(10),datediff(minute,cast(In_time as DATETIME), "
									+ "dateadd(day,1,cast(Out_time as DATETIME)))) -EMP_SHIFT_MASTER.OT_half end and  Report_Daily_wise.Shift=EMP_SHIFT_LATE_RULES.Rule_Code 	and Location='"+cmpcode+"' "
									+ "and Report_Daily_wise.Date between DATEADD(MONTH,-4,getdate()) and DATEADD(MONTH,1,getdate()) and Report_Daily_wise.Status  like '%AA%'"
											+ "  and Report_Daily_wise.Status not like '%AAAA%'  and ( In_time>'00:00:00' or Out_time>'00:00:00')";
															int rr7=ad.SqlExecuteUpdate(othalf);	
															
												
												
								
								
								
							 }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					  		 
					  	/*   try {
								conn.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
						    
						    try {
								finalize();
							} catch (Throwable e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
					  		 
						    
					
							String cmp[]=new String[0]; 
							String locations="";
							
							String aa="select * from user_group where id='"+id+"'";
							ResultSet cc=ad.selectQuery(aa);
							try {
								if(cc.next())
								{
									cmpcode=cc.getString("Attendance_Loc");
									
									if(cmpcode.contains(","))
									{
									 cmp=cmpcode.split(",");
									
									for(int b=0;b<cmp.length;b++)
									{
										locations=locations+"'"+cmp[b]+"',";
									}
									
									locations=locations.substring(0, locations.length()-1);
									}
									else
									{
										locations="'"+cmpcode+"'";
									}
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						    ArrayList locationList=new ArrayList();
								ArrayList locationLabelList=new ArrayList();
								ResultSet rs11 = ad.selectQuery("select LOCID," +
										"LOCNAME,location_code from location where location_code in ("+locations+") "); 
										try {
											while(rs11.next()) {
												locationList.add(rs11.getString("location_code"));
												locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
												
											}
											rs11.close();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										help.setLocationIdList(locationList);
										help.setLocationLabelList(locationLabelList);
										
										
										
										
												ArrayList workList=new ArrayList();
												ArrayList workLabelList=new ArrayList();
												ResultSet rs14 = ad.selectQuery("select loc_id," +
														"loc_name from working_location order by loc_name"); 
														try {
															while(rs14.next()) {
																workList.add(rs14.getString("loc_id"));
																workLabelList.add(rs14.getString("loc_name"));
															}
															rs14.close();
														} catch (SQLException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														
														help.setWorkList(workList);
														help.setWorkLabelList(workLabelList);
						    
					  		
					  		help.setMessage("DATA Processed Successfully");
					  		try {
								rs14.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					  		try {
								rs11.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					  		try {
								rsas.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					  		try {
								cc.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    return mapping.findForward("manual_entry");
	    }

	
	
	
	public String presentdays(int startday,int endday,int startmon,int endmon)
	{
	/*	day19 like '%PP%' or day20 like '%PP%' or day21 like '%PP%'*/
		String value="";
	
		
		for(int j=startday;j<=endday;j++)
		{
			value=value+"day"+j+" like '%PP%' or ";
		}
		
		value=value.substring(0, value.length()-3);
		
		
		return value;
	}
	

	
	
	
	
	
	
	

}
