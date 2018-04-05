package com.microlabs.ess.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.DelegationForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;


public class DelegationAction extends DispatchAction{
	
	
	
	public ActionForward displayDelegation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String userName=user.getUserName();
		request.setAttribute("newDelegation", "show");
		DelegationForm dForm=(DelegationForm)form;
		EssDao ed = new EssDao();
		int count = 0;
		LinkedList delegateList = new LinkedList();
		try{
			DelegationForm newDForm = new DelegationForm();
			String searchQuery="select * from Delegation_Details where delegate_from ='"+userName+"'";
			ResultSet delRS = ed.selectQuery(searchQuery);
			while(delRS.next()) {
				String deldates= delRS.getString("delegate_dates");
				String ddates[] = deldates.split(",");
				newDForm.setFromDate(ddates[0]);
				newDForm.setToDate(ddates[(ddates.length-1)]);
				newDForm.setEmployeeNumber(delRS.getString("delegate_to"));
				delegateList.add(newDForm);
				count++;
			}
			if(count ==0){
				dForm.setMessage("No Delegation Records");
			}
		
		}
		catch (SQLException sqle) { System.out.println("SQLException @ getting delegation details"); sqle.printStackTrace();}
		request.setAttribute("DelegationList", delegateList);
		return mapping.findForward("displayDelegate");	
	}
	
	public ActionForward searchGivenUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		DelegationForm dForm=(DelegationForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		String sTxt = request.getParameter("searchText");
		dForm.setSearchText(sTxt);
		MailInboxAction mAction = new MailInboxAction();
		LinkedList searchList=new LinkedList();
		try{
			String searchQuery="select * from emp_master where emp_name like '%"+sTxt+"%' or emp_email like '%"+sTxt+"%' or emp_id like'%"+sTxt+"%' or emp_designation='%"+sTxt+"%'";
			ResultSet srchRS=ad.selectQuery(searchQuery);
			int count = 0;
			String empmail= "";
			Date today = new Date();
			while(srchRS.next()) {
				MailInboxForm mForm = new MailInboxForm();
				empmail = srchRS.getString("emp_id");
				mForm.setSearchAddress(empmail);
				searchList.add(mForm);
				count++;
				if(count == mAction.MAIL_PER_PAGE)
				{
					break;
				}
			}
		}
		catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
		//displayComposeMail(mapping, form, request, response);
		request.setAttribute("SearchUserDetails", searchList);
		return mapping.findForward("searchUser");
	}
	
	public ActionForward saveDelegation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String uName = user.getUserName();
		int userID=user.getId();
		DelegationForm dForm=(DelegationForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		String delegateToEmp = dForm.getEmployeeNumber();
		System.out.println(delegateToEmp);
		MailInboxAction mAction = new MailInboxAction();
		LinkedList delegateList=new LinkedList();
		try{
			DelegationForm newDForm = new DelegationForm();
			String dates = "";
			String sDate = EMicroUtils.dateConvert(dForm.getFromDate());
			String eDate = EMicroUtils.dateConvert(dForm.getToDate());
			int sdd = Integer.parseInt(getDD(sDate));
			int edd = Integer.parseInt(getDD(eDate));
			int noOfDays = edd-sdd;
			System.out.println(noOfDays);
			dates = sDate;
			for(int i=1;i<=noOfDays;i++)
			{
				//String sDate = dForm.getFromDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(sDate));
				c.add(Calendar.DATE, 1);  // number of days to add
				sDate = sdf.format(c.getTime());
				System.out.println("next day "+sDate);
				dates = dates+","+sDate;
				if(sDate.equalsIgnoreCase(eDate)){
					break;
				}
			}
			System.out.println(dates);
			String  searchQuery="select emp_name from emp_master where emp_id='"+delegateToEmp+"'";
			ResultSet delName = ad.selectQuery(searchQuery);
			while(delName.next()){
				newDForm.setEmployeeNumber(delName.getString(1));
			}
			searchQuery="insert into Delegation_Details (delegate_from, delegate_to, delegate_dates ) values('"+uName+"','"+delegateToEmp+"','"+dates+"')";
			int upd=ad.SqlExecuteUpdate(searchQuery);
			if(upd > 0) {
				newDForm.setFromDate(dForm.getFromDate());
				newDForm.setToDate(dForm.getToDate());
				delegateList.add(newDForm);
			}
			else{
				dForm.setMessage("No Delegation Records");
			}
			
		}
		catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
		catch(ParseException pe){
			System.out.println("Exception @ getting count");
			pe.printStackTrace();
			}
		//displayComposeMail(mapping, form, request, response);
		request.setAttribute("DelegationList", delegateList);
		return mapping.findForward("displayDelegate");
	}
	
	private String getDD(String dates){
		String dd[] = dates.split("-");
		int ddlength = dd.length;
		dates = dd[ddlength-1];
		//dates = dates.substring((dates.lastIndexOf("-")+1), dates.length()); 
		System.out.println(dates);
		return dates;
	}
}