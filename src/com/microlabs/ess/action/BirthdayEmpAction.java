package com.microlabs.ess.action;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.BirthdayEmpForm;

public class BirthdayEmpAction extends DispatchAction{
	EssDao ad=new EssDao();
	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		BirthdayEmpForm birthdayEmpForm=(BirthdayEmpForm)form;
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		birthdayEmpForm.setCurrentDate(ft.format(dNow));
		  ft = new SimpleDateFormat ("MM/dd");
		String dateNow = ft.format(dNow);
		System.out.println("Current Date ="+dateNow);
		LinkedList empBirthDayList=new LinkedList();
		com.sun.security.auth.module.NTSystem NTSystem = new com.sun.security.auth.module.NTSystem();
		System.out.println(NTSystem.getName());
		String getEmpDetails="select e.PERNR,CONVERT(VARCHAR(5), DOB, 101)as  date,u.Emp_Photo,e.SEX,e.EMP_FULLNAME,dept.DPTSTXT,l.LOCATION_CODE,l.LOCNAME," +
		"e.TEL_EXTENS,u.Emp_Photo,e.EMAIL_ID,e.IP_PHONE from  users as  u,emp_official_info as e,DEPARTMENT as dept,Location as l where   '"+dateNow+"'=CONVERT(VARCHAR(5), e.DOB, 101)   and  " +
		"u.employeenumber=e.PERNR  and  u.employeenumber=e.PERNR and e.STAFFCAT!='2'  and  e.LOCID=l.LOCATION_CODE and  e.DPTID=dept.DPTID and e.ACTIVE='1' order by e.LOCID";
		ResultSet rs=ad.selectQuery(getEmpDetails);
		try{
		while(rs.next())
		{
			BirthdayEmpForm mainForm2=new BirthdayEmpForm();
			mainForm2.setLocation(rs.getString("LOCATION_CODE")+" - "+rs.getString("LOCNAME"));
			mainForm2.setEmplyeeName(rs.getString("EMP_FULLNAME"));
			mainForm2.setDept(rs.getString("DPTSTXT"));
			mainForm2.setExtention(rs.getString("TEL_EXTENS"));
			String empPhoto=rs.getString("Emp_Photo");
		
			if(empPhoto.equalsIgnoreCase("")){
				String sex=rs.getString("SEX");
				if(sex.equalsIgnoreCase("M"))
					mainForm2.setEmpPhoto("male.png");
				if(sex.equalsIgnoreCase("F"))
					mainForm2.setEmpPhoto("female.png");
			}else{
				mainForm2.setEmpPhoto(rs.getString("Emp_Photo")+"?time="+new Date().getTime());
			}
			mainForm2.setEmailID(rs.getString("EMAIL_ID"));
			
			String ipPhone=rs.getString("IP_PHONE");
			if(ipPhone!=null)
			mainForm2.setIpphone(ipPhone);
			else
				mainForm2.setIpphone("");
			empBirthDayList.add(mainForm2);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(empBirthDayList.size()>0)	
		{
		request.setAttribute("empBirthDayList", empBirthDayList);
		}else{
			request.setAttribute("noRecords", "noRecords");
		}
		
		return mapping.findForward("display");
	}
}
