package com.microlabs.hr.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.hr.form.UpdateLeaveBalForm;
import com.microlabs.utilities.UserInfo;

public class UpdateLeaveBalAction extends DispatchAction {
	EssDao ad=EssDao.dBConnection();
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	
	{
		UpdateLeaveBalForm leaveForm=(UpdateLeaveBalForm)form;
		
		return mapping.findForward("display");
	}
	
	
	public ActionForward updatedol(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		UpdateLeaveBalForm leaveForm=(UpdateLeaveBalForm)form;
		String actdol="";
		
		if(!leaveForm.getDol().equalsIgnoreCase(""))
		{
			String from[]=leaveForm.getDol().split("/");
			actdol=from[2]+"-"+from[1]+"-"+from[0]; 
		 
		}
		
		
		String a="update emp_official_info set ACTIVE=0,DOL='"+actdol+"' where PERNR='"+leaveForm.getEmpNumber()+"'";
		int j=ad.SqlExecuteUpdate(a);
		
		String a1="update users set activated='Off' where employeenumber='"+leaveForm.getEmpNumber()+"'";
		int j1=ad.SqlExecuteUpdate(a1);
		
		displayLeavebal(mapping, leaveForm, request, response);
		return mapping.findForward("display");
		
	}
	
	public ActionForward displayLeavebal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	
	{
		UpdateLeaveBalForm leaveForm=(UpdateLeaveBalForm)form;
		String a=leaveForm.getEmpNumber();		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		boolean present=false;
		String emp="select emp.EMP_FULLNAME,dep.DPTSTXT,des.DSGSTXT,emp.DOJ,emp.DOL from emp_official_info emp,DEPARTMENT dep,DESIGNATION des where emp.PERNR='"+a+"' and emp.DPTID=dep.DPTID and emp.DSGID=des.DSGID ";
		ResultSet em=ad.selectQuery(emp);
		try {
			while(em.next())
			{
				leaveForm.setEmpName(em.getString("EMP_FULLNAME"));
				leaveForm.setEmpdep(em.getString("DPTSTXT"));
				leaveForm.setEmpdesg(em.getString("DSGSTXT"));
				leaveForm.setDoj(em.getString("doj"));
				if(em.getString("dol")==null)
				{
					leaveForm.setDol("");
				}
				else
				{
				if(em.getString("dol").equalsIgnoreCase("1900-01-01"))
			    leaveForm.setDol("");	
				else					
				leaveForm.setDol(em.getString("dol"));
				}
				present=true;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	if(present==false)
	{
		leaveForm.setMessage("Invalid Employee Number");
	 request.setAttribute("invalid", "invalid");
	 return mapping.findForward("display");
	}
		
	try{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		leaveForm.setYear(year);
			String getleaveBalence="select t.lv_calyear,t.lv_opbal,t.lv_awtBal,t.lv_typeid,t.lv_availed,t.lv_clbal,m.lv_type from lv_type_d as t,lv_type_m as m where t.lv_empcode='"+a+"' and t.lv_calyear='"+year+"' and t.lv_typeid=m.lv_typeid and m.lv_typeid in(1,2,3)";
			ResultSet rsBalance=ad.selectQuery(getleaveBalence);
			ArrayList balList=new ArrayList();
			while(rsBalance.next())
			{
			   leaveForm.setYear(rsBalance.getInt("lv_calyear"));
				LeaveForm form2=new LeaveForm();
				form2.setLeaveType(rsBalance.getString("lv_type"));
				form2.setOpeningBalence(rsBalance.getFloat("lv_opbal"));
				form2.setAvalableBalence(rsBalance.getFloat("lv_availed"));
				form2.setNoOfDays(rsBalance.getString("lv_availed"));
				form2.setClosingBalence(rsBalance.getFloat("lv_clbal"));
			    form2.setAwaitingBalence(rsBalance.getFloat("lv_awtBal"));
				balList.add(form2);
			}
			request.setAttribute("LeaveBalenceList", balList);
			
			if(balList.size()==0){
				request.setAttribute("No LeaveBalence", "No LeaveBalence");
				leaveForm.setMessage2("No Leave balence");
			}
			
	}catch (Exception e) {
		e.printStackTrace();
	}
		
		
		return mapping.findForward("display");
	}
	
	
	public ActionForward calculatedays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	
	{
		
		UpdateLeaveBalForm leaveForm=(UpdateLeaveBalForm)form;
		String startDate=request.getParameter("StartDate");  	
		String b[]=startDate.split("/");
		 String str_date=b[2]+b[1]+b[0];
		String startDateDuration=request.getParameter("StartDur");
		String endDate=request.getParameter("EndDate");	
		String c[]=endDate.split("/");
		 String end_Date=c[2]+c[1]+c[0];
		String endDurationType=request.getParameter("EndDur");
	      double cnt=0;
		String db1="SELECT COUNT(*) FROM dbo.ExplodeDates('"+str_date+"','"+end_Date+"') as a";
		ResultSet rs=ad.selectQuery(db1);
		try {
			while(rs.next())
			{
				cnt=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FD")))
		{
			cnt=cnt;
		}
		if((startDateDuration.equalsIgnoreCase("FD")&&endDurationType.equalsIgnoreCase("FH")))
		{
			cnt=cnt-0.5;
		}
		if((startDateDuration.equalsIgnoreCase("FH")&&endDurationType.equalsIgnoreCase("FH")))
		{
			cnt=cnt-0.5;
		}
		if((startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("SH")))
		{
			cnt=cnt-0.5;
		}
		if((startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("FD")))
		{
			cnt=cnt-0.5;
		}
		if((startDateDuration.equalsIgnoreCase("SH")&&endDurationType.equalsIgnoreCase("FH")))
		{
			cnt=cnt-1.0;
		}
		
		
		leaveForm.setTotalLeaveDays(cnt);
		
		
		
		return mapping.findForward("calculateDays");
	}
	
	public ActionForward saveleave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		UpdateLeaveBalForm leaveForm=(UpdateLeaveBalForm)form;
	
		int year = Calendar.getInstance().get(Calendar.YEAR);
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
	    String submitDate = ft.format(dNow);
		
		
	
		int cnt=0;
		String count="Select count(*) from lv_type_d where lv_empcode='"+leaveForm.getEmpNumber()+"' and lv_typeid='6' and lv_calyear='"+year+"'";
		ResultSet rs=ad.selectQuery(count);
		try {
			while(rs.next())
			{
				cnt=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String bal[]=request.getParameterValues("closingBalence");

			//cl
			String update="update lv_type_d set  lv_clbal='"+bal[0]+"',Prodata=1 where lv_empcode='"+leaveForm.getEmpNumber()+"'  and lv_calyear='"+year+"' and lv_typeid='1'";
			int l=ad.SqlExecuteUpdate(update);
		   //sl
			String update1="update lv_type_d set  lv_clbal='"+bal[1]+"',Prodata=1 where lv_empcode='"+leaveForm.getEmpNumber()+"'  and lv_calyear='"+year+"' and lv_typeid='2'";
			int l1=ad.SqlExecuteUpdate(update1);
			//el
			String update2="update lv_type_d set  lv_clbal='"+bal[2]+"',Prodata=1 where lv_empcode='"+leaveForm.getEmpNumber()+"'  and lv_calyear='"+year+"' and lv_typeid='3'";
			int l2=ad.SqlExecuteUpdate(update2);

			leaveForm.setMessage2("Updated Successfully");
				
			
	displayLeavebal(mapping, leaveForm, request, response);
	
		return mapping.findForward("display");
	}
	
	

}
