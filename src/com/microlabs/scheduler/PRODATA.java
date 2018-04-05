package com.microlabs.scheduler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.SAPLeaveDao;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class PRODATA {
	static EssDao ad = EssDao.dBConnection();
	private static Connection conn=null;
	
	public static void main(String[] args) {
		

			
			
		System.out.println("Started PRO_DATA_task");
		Date dNow1 = new Date( );
	 SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String dateNow1 = ft1.format(dNow1);
		
		String tran="insert into Schedule_Transaction values('PRODATA','"+dateNow1+"')";
		int j=ad.SqlExecuteUpdate(tran);
		
    SAPLeaveDao dao=new SAPLeaveDao();				
	JCoFunction function=null;
	JCoTable a=null;
	
	
	try {
		String sick="update lv_type_d set lv_clbal=d,lv_opbal=d,prodata=1,SAP_Approved_Date=getdate() from (select pernr,doj,case when day(DOJ)>15 "
				+ "then datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')*0.5 else datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')*0.5+0.5 end as d "
				+ "from emp_official_info where convert(date,created_date) = convert(date,getdate()) and emp_official_info.STAFFCAT!=8 )t where t.PERNR=lv_type_d.lv_empcode and lv_typeid=2 and prodata=0 and "
				+ "lv_calyear=year(DOJ) ";
	 int a2=ad.SqlExecuteUpdate(sick);
	 
	 //CL --- current year and doj year same
	 String casual="update lv_type_d set lv_clbal=d,lv_opbal=d,prodata=1,SAP_Approved_Date=getdate() from (select pernr,doj,case when day(DOJ)>15 "
	 		+ "then datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')*0.5 else datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')*0.5+0.5 end as d"
	 		+ " from emp_official_info where dateadd(month,6,doj) = convert(date,getdate()) and  year(doj)=year(dateadd(month,6,doj)) and emp_official_info.STAFFCAT!=8)t  where t.PERNR=lv_type_d.lv_empcode "
	 		+ "and lv_typeid=1 and prodata=0 and lv_calyear=year(DOJ) and lv_calyear=year(getdate())";
	 int a3=ad.SqlExecuteUpdate(casual);
	 
	 ///CL- current year and doj year diff
	 String casual1="update lv_type_d set lv_clbal=6,lv_opbal=6,prodata=1,SAP_Approved_Date=getdate() from emp_official_info where pernr=lv_type_d.lv_empcode and lv_typeid=1 "
	 		+ "and prodata=0 and lv_calyear=year(getdate()) and dateadd(month,6,doj) = convert(date,getdate()) and  emp_official_info.STAFFCAT!=8";
	 int a9=ad.SqlExecuteUpdate(casual1);
	 
	 
	 //PL
	 String priv="update lv_type_d set lv_clbal=d,lv_opbal=d,prodata=1,SAP_Approved_Date=getdate() from (select pernr,doj,case when day(DOJ)>15"
	 		+ " then round(datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')* 1.25,0) else round(datediff(MONTH,DOJ,convert(nvarchar(4),year(DOJ))+'-12-01')* 1.25 + 1.25,0) end as d"
	 		+ " from emp_official_info where dateadd(month,12,doj) = convert(date,getdate()) and emp_official_info.STAFFCAT!=8 )t  where t.PERNR=lv_type_d.lv_empcode and lv_typeid=3 and prodata=0 and"
	 		+ " lv_calyear=year(getdate())";
	 
	 
	 int a4=ad.SqlExecuteUpdate(priv);
	 
		
	
	}catch(Throwable e){
		System.out.println(e.toString());// TODO change to log
		e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));			
		Date d=new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateNow = ft.format(d);
		String error="insert into ERROR_DETAILS values('PRO_DATA','"+dateNow+"','"+errors.toString()+"')";
		int i= ad.SqlExecuteUpdate(error);
	} finally {
		//rs.close();
	}
	
	

System.out.println("Ended PRO_DATA Task");
	
	}
	
	
	


	}


