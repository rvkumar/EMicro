package com.microlabs.scheduler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import com.microlabs.db.AttConnectionFactory;
import com.microlabs.ess.dao.AttDao;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.it.form.IssuesForm;

public class HELPDESK_ESCALATION {
	static 	AttDao ad=new AttDao();
	private static Connection conn=null;
	private  static String password; 
	public static void main(String[] args) {
		
		

		HELPDESK_ESCALATION dao=new HELPDESK_ESCALATION();
	    
					
					System.out.println("Started ESCALATION task");
			
					
					
					String reqnos = "";
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
					String datenw = sdf.format(d);
					String tran="insert into Schedule_Transaction values('ESCALATION','"+datenw+"')";
					int j=ad.SqlExecuteUpdate(tran);
				
					
				
					String a="SELECT iss.*,loc.LOCATION_CODE   FROM IT_ISSUES iss,Location loc where  Req_Status not in('Completed','On Hold')  and iss.Location=loc.LOCID and iss.Escalated_Level!='1'  and Req_No !=''  order by Req_No desc";
					ResultSet es = ad.selectQuery(a);

					try {
					   int actaulminutes=0;
					   int i=0;
					   PreparedStatement pst=null;
						LinkedList esclateList = new LinkedList();
						String reqpriority="";
						while (es.next()) {
							reqpriority=es.getString("Req_Priority");
							if(es.getString("approved_date").contains("1900"))
							{



									if(es.getString("Req_date").substring(0, 11).equalsIgnoreCase(datenw.substring(0, 11)))
									{
										actaulminutes=getminutes(es.getString("Req_date"), datenw,es.getString("Location"),es.getString("LOCATION_CODE") ,2);
									}

									if(!es.getString("Req_date").substring(0, 11).equalsIgnoreCase(datenw.substring(0, 11)))
									{
										actaulminutes=	getminutes(es.getString("Req_date"), datenw,es.getString("Location"),es.getString("LOCATION_CODE") ,1);
									}

							}

							else
							{



									if(es.getString("approved_date").substring(0, 11).equalsIgnoreCase(datenw.substring(0, 11)))
									{
										actaulminutes=getminutes(es.getString("approved_date"), datenw,es.getString("Location"),es.getString("LOCATION_CODE") ,2);
									}

									if(!es.getString("approved_date").substring(0, 11).equalsIgnoreCase(datenw.substring(0, 11)))
									{
										actaulminutes=	getminutes(es.getString("approved_date"), datenw,es.getString("Location"),es.getString("LOCATION_CODE") ,1);
									}

							}
							
							
							//escalation master
						
							String esc="select top 1 id from Escalation_Master where TimeInHrs*60<"+actaulminutes+" and user_priority='"+reqpriority+"' order by TimeInHrs desc";
							ResultSet escl = ad.selectQuery(esc);	
							
								while (escl.next()) {
								   IssuesForm is=new IssuesForm();
								   is.setId(Integer.toString(escl.getInt(1)));
								   is.setRequestNo(es.getInt("Req_No"));
								   esclateList.add(is);
								}
							
						}
						if(esclateList.size()>0)
						{				
							dao.updateESCALATIONDetails(esclateList);		   
					
						}		
						
					} catch (SQLException e) {	
						e.printStackTrace();
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));			
						 d=new Date();
						SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						String dateNow = ft.format(d);
						String error="insert into ERROR_DETAILS values('ESCALATION','"+dateNow+"','"+errors.toString()+"')";
						int i= ad.SqlExecuteUpdate(error);
					}
					
					
					
					System.out.println("Ended ESCALATION Task");
		
		
	
	}
	public static int[] updateESCALATIONDetails(LinkedList esclateList)
	{
		try {
		 Iterator  updateitr=esclateList.iterator();
		 
		 if(conn==null||conn.isClosed())
		 {
			 conn=AttConnectionFactory.getConnection();
		 }
		 PreparedStatement	pst=conn.prepareStatement("update IT_ISSUES set Escalated='1',Escalated_date=?,Escalated_Level=? where Req_No =? ");
		while(updateitr.hasNext())
		{
			IssuesForm issues=(IssuesForm)updateitr.next();
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ");
			String datenw = sdf.format(d);
			
			pst.setString(1, datenw);			
			pst.setString(2, issues.getId());
			pst.setInt(3, issues.getRequestNo());
			pst.addBatch();
		
		
		
		}
		
		if(!esclateList.isEmpty())
		{
			pst.executeBatch();
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));			
			Date d=new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String dateNow = ft.format(d);
			String error="insert into ERROR_DETAILS values('ESCALATION','"+dateNow+"','"+errors.toString()+"')";
			int i= ad.SqlExecuteUpdate(error);
		}
		
		return null; 
	}
	
	public static  int getminutes(String fromdate,String todate,String locationID ,String locationCODE, int prefix)
	{
		int count=0;
		String query="";
		if(prefix==2)
		{
			//query="select  DATEDIFF( MINUTE,'"+fromdate+"','"+todate+"') minutes from CMP_WRKIN_HRS where Location='"+locationID+"' ";
			query="select  CASE WHEN DATEDIFF( MINUTE,'"+todate+"',left(GETDATE(),11)+' '+End_time)> 1 THEN DATEDIFF( MINUTE,'"+fromdate+"','"+todate+"') "
					+ " ELSE DATEDIFF( MINUTE,'"+fromdate+"','"+todate.substring(0,11)+"'+' '+End_time)"
					+ "END as minutes from CMP_WRKIN_HRS where Location='"+locationID+"' ";
		}
		
		if(prefix==1)
		{
			query="select SUM(minutes)  as mind from (select COUNT(*)*(select DATEDIFF( MINUTE,left(GETDATE(),11)+' '+Start_time,left(GETDATE(),11)+' '+End_time) "
		+ " from CMP_WRKIN_HRS where Location='"+locationID+"') as minutes from ESCALATION_CALENDAR e where DATE>'"+fromdate.substring(0, 11)+"' and DATE<'"+todate.substring(0, 11)+"' and "+locationCODE+"='W' "
		+ "union all select  DATEDIFF( MINUTE,left(GETDATE(),11)+' '+'"+fromdate.substring(11, fromdate.length())+"',left(GETDATE(),11)+' '+End_time) minutes from CMP_WRKIN_HRS where Location='"+locationID+"'"
		+ " union all select  DATEDIFF( MINUTE,left(GETDATE(),11)+' '+Start_time, left(GETDATE(),11)+' '+'"+todate.substring(11, fromdate.length())+"') minutes from CMP_WRKIN_HRS where Location='"+locationID+"' )d ";			
		}
		
		
		
		ResultSet rsCount = ad.selectQuery(query);
		try {
			while (rsCount.next()) {
				count=rsCount.getInt(1);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		
		return count;
	}

	
	
	


	}


