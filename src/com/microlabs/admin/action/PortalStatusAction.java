package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.form.PortalStatusForm;
import com.microlabs.ess.dao.EssDao;

public class PortalStatusAction extends DispatchAction {		
	

	
	
	public ActionForward getlist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		/*Timer timer = new Timer();
		TimerTask tt = new TimerTask(){
			public void run(){
				Calendar cal = Calendar.getInstance(); //this is the method you should use, not the Date(), because it is desperated.
 
				int hour = cal.get(Calendar.HOUR_OF_DAY);//get the hour number of the day, from 0 to 23
 
				if(hour == 17){
					System.out.println("doing the scheduled task");
				}
			}
		};
		timer.schedule(tt, 0, 1000*10);//	delay the task 1 second, and then run task every five seconds
*/		
		
		/*TimerTask tt = new TimerTask(){
			public void run(){
				
				
						System.out.println("doing the scheduled task");
					}			
		};
		
		Calendar date = Calendar.getInstance();
		 date.set(Calendar.HOUR, 9);
		 date.set(Calendar.MINUTE, 06);
		 date.set(Calendar.SECOND, 0);
		 date.set(Calendar.MILLISECOND, 0);
		 Timer timer = new Timer();		    
		 timer.schedule(tt,date.getTime(),1000 *5);*/
	
		
		
		
		PortalStatusForm por=(PortalStatusForm)form;
		EssDao ad=new EssDao();
		LinkedList locid=new LinkedList();
		LinkedList locnamelist=new LinkedList();
		String sql="Select * from Location";
		ResultSet rs=ad.selectQuery(sql);
		try {
			while(rs.next())
			{
				locid.add(rs.getString("LOCATION_CODE"));
				locnamelist.add(rs.getString("LOCATION_CODE")+"-"+rs.getString("LOCNAME"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		por.setLocidlist(locid);
		por.setLocnamelist(locnamelist);
		
		
		
		return mapping.findForward("displayAll");
	}
	
	public ActionForward exportdata(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		EssDao ad=new EssDao();
		PortalStatusForm por1=(PortalStatusForm)form;
		
		
		
		String link=request.getParameter("linkname");		
		
		
		String sql="";
		
		String loc=por1.getExplocid();
		String cat=por1.getExpcat();
		
		if(link.equalsIgnoreCase("Login"))
		{
			sql="select usr.username,emp.EMP_FULLNAME,desg.DSGSTXT,dept.DPTSTXT,usr.loginCount from users as usr,emp_official_info as emp,DEPARTMENT as dept,"
			+ "DESIGNATION as desg where usr.employeenumber=emp.PERNR and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and usr.loginCount!=0 and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
		sql=sql+"  order by username";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList loginlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("username"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("Yes");
				loginlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select usr.username,emp.EMP_FULLNAME,desg.DSGSTXT,dept.DPTSTXT,usr.loginCount from users as usr,emp_official_info as emp,DEPARTMENT as dept,"
				+ "DESIGNATION as desg where usr.employeenumber=emp.PERNR and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and usr.loginCount=0 and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by username";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("username"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("No");
					loginlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("loginlist", loginlist);
		}
		
		if(link.equalsIgnoreCase("Leave"))
		{
			sql="select lv.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from leave_details as lv,emp_official_info as emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where lv.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and "
			+ "dept.DPTID=emp.DPTID and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by lv.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList leavlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("Yes");
				leavlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
			+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in"
			+ "(select lv.user_id from leave_details as lv,emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where lv.user_id =emp.PERNR "
			+ " and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  ) and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("No");
					leavlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("leavlist", leavlist);
		
			
		}
		
		if(link.equalsIgnoreCase("ALL"))
		{
			
			sql="select PERNR,EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,(select employee_no from emp_personal_info where employee_no=main.PERNR ) as pers,"
					+ "(select distinct(employee_no) from emp_address where employee_no=main.PERNR ) as addr,(select distinct(employee_no) from emp_family_details"
					+ " where employee_no=main.PERNR ) as family,(select distinct(employee_no) from emp_education_details where employee_no=main.PERNR ) as education,"
					+ "(select distinct(employee_no) from emp_experience_details where employee_no=main.PERNR ) as exper,(select distinct(employee_no) "
					+ "from emp_language_details where employee_no=main.PERNR ) as lang from emp_official_info as main,DEPARTMENT as dept,DESIGNATION as desg where"
					+ " desg.DSGID=main.DSGID and dept.DPTID=main.DPTID and LOCID='"+loc+"' and main.ACTIVE=1";
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			sql=sql+"order by PERNR";
			ResultSet rs=ad.selectQuery(sql);
			LinkedList All=new LinkedList();
			try {
							
				while(rs.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs.getString("PERNR"));
					por.setName(rs.getString("EMP_FULLNAME"));
					por.setDept(rs.getString("DPTSTXT"));
					por.setDesg(rs.getString("DSGSTXT"));
				    String a=rs.getString("pers");
				    String b=rs.getString("addr");
				    String c=rs.getString("family");
				    String d=rs.getString("education");
				    String e=rs.getString("exper");
				    String f=rs.getString("lang");
					if(a==null)
					{
						por.setPerstatus("No");
					}
					else
					{
						por.setPerstatus("Yes");
					}
					if(b==null)
					{
						por.setAddstatus("No");
					}
					else
					{
						por.setAddstatus("Yes");
					}
					if(c==null)
					{
						por.setFamstatus("No");
					}
					else
					{
						por.setFamstatus("Yes");
					}
					if(d==null)
					{
						por.setEdustatus("No");
					}
					else
					{
						por.setEdustatus("Yes");
					}
					if(e==null)
					{
						por.setExpstatus("No");
					}
					else
					{
						por.setExpstatus("Yes");
					}
					if(f==null)
					{
						por.setLanstatus("No");
					}
					else
					{
						por.setLanstatus("Yes");
					}
					
					All.add(por);				
					
				}}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			request.setAttribute("alllist", All);
		}
		
		if(link.equalsIgnoreCase("Personal Information"))
		{
			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_personal_info as per,emp_official_info as emp,DEPARTMENT as dept,"
				+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1 ";
		
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList perlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				perlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where  "
					+ "  desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in(select per.user_id from emp_personal_info"
					+ " as per,emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where per.user_id =emp.PERNR  and"
					+ " desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					perlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("perlist", perlist);
		
			
		}
		
		if(link.equalsIgnoreCase("Address"))
		{

			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_address as per,emp_official_info as emp,DEPARTMENT as dept,"
				+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList addrlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				addrlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and "
					+ "pernr not in(select per.user_id from emp_address as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					addrlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("addrlist", addrlist);	
					
			
		}
		if(link.equalsIgnoreCase("Family"))
		{


			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_family_details as per,"
					+ "emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where per.user_id =emp.PERNR  "
					+ "and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList familist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				familist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr "
					+ "not in(select per.user_id from emp_family_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					familist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("familist", familist);						
					
			
		}
		
		if(link.equalsIgnoreCase("Education"))
		{


			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_education_details as per,"
					+ "emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where per.user_id =emp.PERNR  "
					+ "and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList edulist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				edulist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in"
					+ "(select per.user_id from emp_education_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					edulist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("edulist", edulist);						
					
					
		}
		
		if(link.equalsIgnoreCase("Experience"))
		{
			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_experience_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1";
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		
		ResultSet rs=ad.selectQuery(sql);
		LinkedList explist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				explist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in"
					+ "(select per.user_id from emp_experience_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					explist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("explist", explist);						
							
		}
		
		if(link.equalsIgnoreCase("Language"))
		{
			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_language_details as per,"
					+ "emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where per.user_id =emp.PERNR  "
					+ "and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
			
			
		ResultSet rs=ad.selectQuery(sql);
		LinkedList langlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				langlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in"
					+ "(select per.user_id from emp_language_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					langlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("langlist", langlist);						
							
		
			
		}
		
		 response.setHeader("Content-disposition:", "attachment; filename=portalStatus.xls;");
		return mapping.findForward("exportdata");
	
		
	}
	
	
	public ActionForward displaydata(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad=new EssDao();
		PortalStatusForm por1=(PortalStatusForm)form;
		
		LinkedList locid=new LinkedList();
		LinkedList locnamelist=new LinkedList();
		String sql1="Select * from Location";
		ResultSet rs11=ad.selectQuery(sql1);
		try {
			while(rs11.next())
			{
				locid.add(rs11.getString("LOCATION_CODE"));
				locnamelist.add(rs11.getString("LOCATION_CODE")+"-"+rs11.getString("LOCNAME"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		por1.setLocidlist(locid);
		por1.setLocnamelist(locnamelist);
		
		String link=request.getParameter("linkname");		
		System.out.println(link);
		
		String sql="";
		
		String loc=por1.getLocid();
		por1.setExplocid(loc);
		String cat=por1.getCat();
		por1.setExpcat(cat);
		
		if(link.equalsIgnoreCase("Login"))
		{
			sql="select usr.username,emp.EMP_FULLNAME,desg.DSGSTXT,dept.DPTSTXT,usr.loginCount from users as usr,emp_official_info as emp,DEPARTMENT as dept,"
			+ "DESIGNATION as desg where usr.employeenumber=emp.PERNR and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and usr.loginCount!=0 and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
		sql=sql+"  order by username";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList loginlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("username"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("Yes");
				loginlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select usr.username,emp.EMP_FULLNAME,desg.DSGSTXT,dept.DPTSTXT,usr.loginCount from users as usr,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where usr.employeenumber=emp.PERNR and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and usr.loginCount=0 and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by username";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("username"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("No");
					loginlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("loginlist", loginlist);
		}
		
		if(link.equalsIgnoreCase("Leave"))
		{
			sql="select lv.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from leave_details as lv,emp_official_info as emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where lv.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and "
			+ "dept.DPTID=emp.DPTID and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by lv.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList leavlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("Yes");
				leavlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
			+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in"
			+ "(select lv.user_id from leave_details as lv,emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where lv.user_id =emp.PERNR "
			+ " and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  ) and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("No");
					leavlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("leavlist", leavlist);
		
			
		}
		
		if(link.equalsIgnoreCase("ALL"))
		{
			
			sql="select PERNR,EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,(select employee_no from emp_personal_info where employee_no=main.PERNR ) as pers,"
					+ "(select distinct(employee_no) from emp_address where employee_no=main.PERNR ) as addr,(select distinct(employee_no) from emp_family_details"
					+ " where employee_no=main.PERNR ) as family,(select distinct(employee_no) from emp_education_details where employee_no=main.PERNR ) as education,"
					+ "(select distinct(employee_no) from emp_experience_details where employee_no=main.PERNR ) as exper,(select distinct(employee_no) "
					+ "from emp_language_details where employee_no=main.PERNR ) as lang from emp_official_info as main,DEPARTMENT as dept,DESIGNATION as desg where"
					+ " desg.DSGID=main.DSGID and dept.DPTID=main.DPTID and LOCID='"+loc+"' and main.ACTIVE=1";
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			sql=sql+"order by PERNR";
			ResultSet rs=ad.selectQuery(sql);
			LinkedList All=new LinkedList();
			try {
							
				while(rs.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs.getString("PERNR"));
					por.setName(rs.getString("EMP_FULLNAME"));
					por.setDept(rs.getString("DPTSTXT"));
					por.setDesg(rs.getString("DSGSTXT"));
				    String a=rs.getString("pers");
				    String b=rs.getString("addr");
				    String c=rs.getString("family");
				    String d=rs.getString("education");
				    String e=rs.getString("exper");
				    String f=rs.getString("lang");
					if(a==null)
					{
						por.setPerstatus("No");
					}
					else
					{
						por.setPerstatus("Yes");
					}
					if(b==null)
					{
						por.setAddstatus("No");
					}
					else
					{
						por.setAddstatus("Yes");
					}
					if(c==null)
					{
						por.setFamstatus("No");
					}
					else
					{
						por.setFamstatus("Yes");
					}
					if(d==null)
					{
						por.setEdustatus("No");
					}
					else
					{
						por.setEdustatus("Yes");
					}
					if(e==null)
					{
						por.setExpstatus("No");
					}
					else
					{
						por.setExpstatus("Yes");
					}
					if(f==null)
					{
						por.setLanstatus("No");
					}
					else
					{
						por.setLanstatus("Yes");
					}
					
					All.add(por);				
					
				}}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			request.setAttribute("alllist", All);
		}
		
		if(link.equalsIgnoreCase("Personal Information"))
		{
			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_personal_info as per,emp_official_info as emp,DEPARTMENT as dept,"
				+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"'  and emp.ACTIVE=1";
		
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList perlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				perlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where  "
					+ "  desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in(select per.user_id from emp_personal_info"
					+ " as per,emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where per.user_id =emp.PERNR  and"
					+ " desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					perlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("perlist", perlist);
		
			
		}
		
		if(link.equalsIgnoreCase("Address"))
		{

			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_address as per,emp_official_info as emp,DEPARTMENT as dept,"
				+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList addrlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				addrlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and "
					+ "pernr not in(select per.user_id from emp_address as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					addrlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("addrlist", addrlist);	
					
			
		}
		if(link.equalsIgnoreCase("Family"))
		{


			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_family_details as per,"
					+ "emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where per.user_id =emp.PERNR  "
					+ "and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList familist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				familist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr "
					+ "not in(select per.user_id from emp_family_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					familist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("familist", familist);						
					
			
		}
		
		if(link.equalsIgnoreCase("Education"))
		{


			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_education_details as per,"
					+ "emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where per.user_id =emp.PERNR  "
					+ "and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1";
		
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		ResultSet rs=ad.selectQuery(sql);
		LinkedList edulist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				edulist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in"
					+ "(select per.user_id from emp_education_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					edulist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("edulist", edulist);						
					
					
		}
		
		if(link.equalsIgnoreCase("Experience"))
		{
			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_experience_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1";
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
		
		ResultSet rs=ad.selectQuery(sql);
		LinkedList explist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				explist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in"
					+ "(select per.user_id from emp_experience_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					explist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("explist", explist);						
							
		}
		
		if(link.equalsIgnoreCase("Language"))
		{
			sql="select per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_language_details as per,"
					+ "emp_official_info as emp,DEPARTMENT as dept,DESIGNATION as desg where per.user_id =emp.PERNR  "
					+ "and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID  and LOCID='"+loc+"' and emp.ACTIVE=1 ";
		
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  group by per.user_id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT";
			
			
		ResultSet rs=ad.selectQuery(sql);
		LinkedList langlist=new LinkedList();
		try {
						
			while(rs.next())
			{
				PortalStatusForm por=new PortalStatusForm();
				por.setPernr(rs.getString("user_id"));
				por.setName(rs.getString("EMP_FULLNAME"));
				por.setDept(rs.getString("DPTSTXT"));
				por.setDesg(rs.getString("DSGSTXT"));
				por.setStatus("1");
				langlist.add(por);				
				
			}}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sql="select emp.PERNR,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT from emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where    desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID and pernr not in"
					+ "(select per.user_id from emp_language_details as per,emp_official_info as emp,DEPARTMENT as dept,"
					+ "DESIGNATION as desg where per.user_id =emp.PERNR  and desg.DSGID=emp.DSGID and dept.DPTID=emp.DPTID   )and LOCID='"+loc+"' and emp.ACTIVE=1";
				
			
			if(!cat.equalsIgnoreCase(""))
			{
				sql=sql+" and STAFFCAT='"+cat+"' ";
			}
			
			sql=sql+"  order by PERNR";
			ResultSet rs1=ad.selectQuery(sql);
			try {
				while(rs1.next())
				{
					PortalStatusForm por=new PortalStatusForm();
					por.setPernr(rs1.getString("PERNR"));
					por.setName(rs1.getString("EMP_FULLNAME"));
					por.setDept(rs1.getString("DPTSTXT"));
					por.setDesg(rs1.getString("DSGSTXT"));
					por.setStatus("0");
					langlist.add(por);				
					
				}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		request.setAttribute("langlist", langlist);						
							
		
			
		}
		
		
		return mapping.findForward("displayAll");
	}

}
