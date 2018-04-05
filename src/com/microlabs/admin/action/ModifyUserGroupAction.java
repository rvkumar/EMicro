package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.GroupDao;
import com.microlabs.admin.form.ModifyUserGroupForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;


public class ModifyUserGroupAction extends DispatchAction{
	
	
	public String replace(String str)
	{
		String replaced_value=str.replace("'", "");
		return replaced_value;
	}
	
	public ActionForward getRequiredSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("display()-----");
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ArrayList listOFGroup=new ArrayList();
		try{
			  int  totalRecords=0;
			  int  startRecord=0;
			  int  endRecord=0;
			  String requiredSearch=modifyUserGroupForm.getSearchRequired();
			  requiredSearch=requiredSearch.trim();
			  
						  String getgroupList="Select * from (SELECT ROW_NUMBER() OVER(ORDER BY grp.ID) AS RowNum,grp.id,grp.group_name,status from user_group as grp where grp.group_name like '%"+requiredSearch+"%') as row where row.RowNum between 1 and 10";
						  String getTotalRecords="Select count(*) from user_group WHERE user_group.group_name like '%"+requiredSearch+"%'";
				
				
						  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
						  while(rsTotalRecods.next())
						  {
							  totalRecords=rsTotalRecods.getInt(1);
						  }
						  
						  if(totalRecords>10)
						  {
							  modifyUserGroupForm.setTotalRecords(totalRecords);
						  startRecord=1;
						  endRecord=10;
						  modifyUserGroupForm.setStartRecord(1);
						  modifyUserGroupForm.setEndRecord(10);
						  request.setAttribute("displayRecordNo", "displayRecordNo");
						  request.setAttribute("nextButton", "nextButton");
						  }else
						  {
							  startRecord=1;
							  endRecord=totalRecords;
							  modifyUserGroupForm.setTotalRecords(totalRecords);
							  modifyUserGroupForm.setStartRecord(1);
							  modifyUserGroupForm.setEndRecord(totalRecords); 
						  }
						  
						  
						  
						  
			  ResultSet rsGroupList=ad.selectQuery(getgroupList);
				while(rsGroupList.next())
				{
					ModifyUserGroupForm grpForm1=new ModifyUserGroupForm();
					grpForm1.setGroupname(rsGroupList.getString("group_name"));
					grpForm1.setGroupid(rsGroupList.getString("id"));
					String status= rsGroupList.getString("status");
					if(status.equalsIgnoreCase("0"))
					{
						grpForm1.setStatus("Active");
					}
					else
					{
						grpForm1.setStatus("Inactive");
					}
					
					
					
					listOFGroup.add(grpForm1);
				}
			
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(listOFGroup.size()==0)
		{
			request.setAttribute("noRecords", "noRecords");
			modifyUserGroupForm.setMessage("No Records");
		}
		else
		{
			modifyUserGroupForm.setMessage("");
		}
		
		
		request.setAttribute("groupList", listOFGroup);
		
		
		
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		
		return mapping.findForward("display");
	}
	
	
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("display()-----");
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ArrayList listOFGroup=new ArrayList();
		try{
			  int  totalRecords=0;
			  int  startRecord=0;
			  int  endRecord=0;
						  String getgroupList="Select * from (SELECT ROW_NUMBER() OVER(ORDER BY grp.ID) AS RowNum,grp.id,grp.group_name,status from user_group as grp) as row where row.RowNum between 1 and 10";
						  String getTotalRecords="Select count(*) from user_group";
				
				
						  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
						  while(rsTotalRecods.next())
						  {
							  totalRecords=rsTotalRecods.getInt(1);
						  }
						  
						  if(totalRecords>=10)
						  {
							  modifyUserGroupForm.setTotalRecords(totalRecords);
						  startRecord=1;
						  endRecord=10;
						  modifyUserGroupForm.setStartRecord(1);
						  modifyUserGroupForm.setEndRecord(10);
						  request.setAttribute("displayRecordNo", "displayRecordNo");
						  request.setAttribute("nextButton", "nextButton");
						  }else
						  {
							  startRecord=1;
							  endRecord=totalRecords;
							  modifyUserGroupForm.setTotalRecords(totalRecords);
							  modifyUserGroupForm.setStartRecord(1);
							  modifyUserGroupForm.setEndRecord(totalRecords); 
						  }
						  
						  
						  
						  
			  ResultSet rsGroupList=ad.selectQuery(getgroupList);
				while(rsGroupList.next())
				{
					ModifyUserGroupForm grpForm1=new ModifyUserGroupForm();
					grpForm1.setGroupname(rsGroupList.getString("group_name"));
					grpForm1.setGroupid(rsGroupList.getString("id"));
					String status= rsGroupList.getString("status");
					if(status.equalsIgnoreCase("0"))
					{
						grpForm1.setStatus("Active");
					}
					else
					{
						grpForm1.setStatus("Inactive");
					}
					
					listOFGroup.add(grpForm1);
				}
			
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		modifyUserGroupForm.setSearchRequired("");
		modifyUserGroupForm.setMessage("");
		request.setAttribute("groupList", listOFGroup);
		
		
		
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		
		return mapping.findForward("display");
	}
	

	public ActionForward firstGroupRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		LinkedList customerList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();
		try{
			int totalRecords=modifyUserGroupForm.getTotalRecords();//21
			int startRecord=modifyUserGroupForm.getStartRecord();//11
			int endRecord=modifyUserGroupForm.getEndRecord();	
			
			
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  modifyUserGroupForm.setTotalRecords(totalRecords);
				  modifyUserGroupForm.setStartRecord(startRecord);
				  modifyUserGroupForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  modifyUserGroupForm.setTotalRecords(totalRecords);
					  modifyUserGroupForm.setStartRecord(startRecord);
					  modifyUserGroupForm.setEndRecord(totalRecords);  
				  }

			String searchRequired=modifyUserGroupForm.getSearchRequired();
			
			
			String getgroupList="Select * from (SELECT ROW_NUMBER() OVER(ORDER BY grp.ID) AS RowNum,grp.id,grp.group_name,status from user_group as grp where grp.group_name like '%"+searchRequired+"%') as row where row.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			 
			ResultSet rsGroupList=ad.selectQuery(getgroupList);
			while(rsGroupList.next())
			{
				ModifyUserGroupForm grpForm1=new ModifyUserGroupForm();
				grpForm1.setGroupname(rsGroupList.getString("group_name"));
				grpForm1.setGroupid(rsGroupList.getString("id"));
				String status= rsGroupList.getString("status");
				if(status.equalsIgnoreCase("0"))
				{
					grpForm1.setStatus("Active");
				}
				else
				{
					grpForm1.setStatus("Inactive");
				}
				
				listOFGroup.add(grpForm1);
			}
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
				}
			
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				request.setAttribute("groupList", listOFGroup);
				request.setAttribute("displayRecordNo", "displayRecordNo");
				//request.setAttribute("materialCodeList","materialCodeList");
					
		
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	modifyUserGroupForm.setMessage("");
	return mapping.findForward("display");
	}
	
	
	
	
	public ActionForward previousGroupRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		LinkedList customerList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();
	try{
		System.out.println("Start Record="+modifyUserGroupForm.getStartRecord());
		System.out.println("End record="+modifyUserGroupForm.getEndRecord());
		System.out.println("Total Record="+modifyUserGroupForm.getTotalRecords());	
		
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int endRecord=modifyUserGroupForm.getStartRecord()-1;//20
		int startRecord=modifyUserGroupForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(1);
		modifyUserGroupForm.setEndRecord(10);
		String searchRequired=modifyUserGroupForm.getSearchRequired();
		
		
		String getgroupList="Select * from (SELECT ROW_NUMBER() OVER(ORDER BY grp.ID) AS RowNum,grp.id,grp.group_name,status from user_group as grp where grp.group_name like '%"+searchRequired+"%') as row where row.RowNum between '"+startRecord+"' and '"+endRecord+"'";
		
		ResultSet rsGroupList=ad.selectQuery(getgroupList);
		while(rsGroupList.next())
		{
			ModifyUserGroupForm grpForm1=new ModifyUserGroupForm();
			grpForm1.setGroupname(rsGroupList.getString("group_name"));
			grpForm1.setGroupid(rsGroupList.getString("id"));
			String status= rsGroupList.getString("status");
			if(status.equalsIgnoreCase("0"))
			{
				grpForm1.setStatus("Active");
			}
			else
			{
				grpForm1.setStatus("Inactive");
			}
			
			listOFGroup.add(grpForm1);
		}
	
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(startRecord);
		modifyUserGroupForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(customerList.size()<10)
			{
				modifyUserGroupForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	modifyUserGroupForm.setMessage("");
	request.setAttribute("groupList", listOFGroup);
	return mapping.findForward("display");
	}
	
	
	
	public ActionForward nextGroupRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();
	try{
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
	
		if(totalRecords>=endRecord)
		{
			if(totalRecords==endRecord)
			{
				startRecord=startRecord;
				endRecord=totalRecords;
			}
			if(totalRecords>endRecord)
			{
			startRecord=endRecord+1;
			endRecord=endRecord+10;
			}
		
			String searchRequired=modifyUserGroupForm.getSearchRequired();
			
			
			String getgroupList="Select * from (SELECT ROW_NUMBER() OVER(ORDER BY grp.ID) AS RowNum,grp.id,grp.group_name,status from user_group as grp where grp.group_name like '%"+searchRequired+"%') as row where row.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			
			ResultSet rsGroupList=ad.selectQuery(getgroupList);
			while(rsGroupList.next())
			{
				ModifyUserGroupForm grpForm1=new ModifyUserGroupForm();
				grpForm1.setGroupname(rsGroupList.getString("group_name"));
				grpForm1.setGroupid(rsGroupList.getString("id"));
				String status= rsGroupList.getString("status");
				if(status.equalsIgnoreCase("0"))
				{
					grpForm1.setStatus("Active");
				}
				else
				{
					grpForm1.setStatus("Inactive");
				}
				
				listOFGroup.add(grpForm1);
			}
		
		}
		System.out.println("list length="+listOFGroup.size());
		
		 if(listOFGroup.size()!=0)
			{
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				modifyUserGroupForm.setTotalRecords(totalRecords);
				modifyUserGroupForm.setStartRecord(start);
				modifyUserGroupForm.setEndRecord(end);
				
			}
		 if(listOFGroup.size()<10)
		 {
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(startRecord+listOFGroup.size()-1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton"); 
			 
		 }
		 
		 if(endRecord==totalRecords)
		 {
			 request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
		 }
		 request.setAttribute("displayRecordNo", "displayRecordNo");
		
	}catch (Exception e) {
	e.printStackTrace();
	}
		request.setAttribute("groupList", listOFGroup);
		modifyUserGroupForm.setMessage("");
		return mapping.findForward("display");
	}
	
	
	
	
	public ActionForward lastGroupRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=Integer.parseInt(user.getEmployeeNo());
		ArrayList listOFGroup=new ArrayList();
		try{
			int totalRecords=modifyUserGroupForm.getTotalRecords();//21
			int startRecord=modifyUserGroupForm.getStartRecord();//11
			int endRecord=modifyUserGroupForm.getEndRecord();	
			
			
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(totalRecords);
			
			 String searchRequired=modifyUserGroupForm.getSearchRequired();
				
				
				String getgroupList="Select * from (SELECT ROW_NUMBER() OVER(ORDER BY grp.ID) AS RowNum,grp.id,grp.group_name,status from user_group as grp where grp.group_name like '%"+searchRequired+"%') as row where row.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				
				ResultSet rsGroupList=ad.selectQuery(getgroupList);
				while(rsGroupList.next())
				{
					ModifyUserGroupForm grpForm1=new ModifyUserGroupForm();
					grpForm1.setGroupname(rsGroupList.getString("group_name"));
					grpForm1.setGroupid(rsGroupList.getString("id"));
					String status= rsGroupList.getString("status");
					if(status.equalsIgnoreCase("0"))
					{
						grpForm1.setStatus("Active");
					}
					else
					{
						grpForm1.setStatus("Inactive");
					}
					
					listOFGroup.add(grpForm1);
				}
			 request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton");
				if(listOFGroup.size()<10)
				{
					
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				request.setAttribute("groupList", listOFGroup);
				request.setAttribute("displayRecordNo", "displayRecordNo");
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	modifyUserGroupForm.setMessage("");
	return mapping.findForward("display");
	}
	
	
	
	
	public ActionForward active_inactive_group(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String status1=request.getParameter("status");
		String active_inactive;
		
		try{
			  			if(status1.equalsIgnoreCase("Active"))
			  			{
						  active_inactive="Update user_group set status='1' where id='"+grpid+"'";
			  			}
			  			else
			  			{
			  				active_inactive="Update user_group set status='0' where id='"+grpid+"'";
			  			}
						  int active=ad.SqlExecuteUpdate(active_inactive);
						  if(active!=0)
							{
							  if(status1.equalsIgnoreCase("Active"))
							  {
								  modifyUserGroupForm.setMessage(grpname+" Successfully Deactivated");
							  }
							  else
							  {
								  modifyUserGroupForm.setMessage(grpname+" Successfully Activated");
							  }
							} 
							
							else
							{
								modifyUserGroupForm.setMessage("Sorry, Contact your Admin Team");
							
							}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(startRecord);
		modifyUserGroupForm.setEndRecord(endRecord);
		return curentRecord(mapping, form, request, response);
	}
	
	
	
	
	public ActionForward delete_group(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String status1=request.getParameter("status");
		String check,delete;
		
		try{
			  			
			check="Select group_id from users where group_id='"+grpid+"' and status='1'";
			
			ResultSet rsGroupList=ad.selectQuery(check);
			if(rsGroupList.next())
			{
				modifyUserGroupForm.setMessage("Sorry, Users are assigned to "+grpname+" group, So you can not delete");
			}
			
			else
			{
			
			delete="Delete user_group where id='"+grpid+"'";
			  			  
						  int active=ad.SqlExecuteUpdate(delete);
						  if(active!=0)
							{
							 
								  modifyUserGroupForm.setMessage(grpname+" Deleted Successfully");
							  
							}
							
							else
							{
								modifyUserGroupForm.setMessage("Sorry, Contact your Admin Team");
							
							}
			}			  
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(startRecord);
		modifyUserGroupForm.setEndRecord(endRecord);
		return curentRecord(mapping, form, request, response);
	}
	
	public ActionForward curentRecord_without_alert_message(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();
	try{
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
	
		String searchRequired=modifyUserGroupForm.getSearchRequired();
		
		if(searchRequired==null)
		{
			searchRequired="";
		}
		
		String getgroupList="Select * from (SELECT ROW_NUMBER() OVER(ORDER BY grp.ID) AS RowNum,grp.id,grp.group_name,status from user_group as grp where grp.group_name like '%"+searchRequired+"%') as row where row.RowNum between '"+startRecord+"' and '"+endRecord+"'";
		
			ResultSet rsGroupList=ad.selectQuery(getgroupList);
			while(rsGroupList.next())
			{
				ModifyUserGroupForm grpForm1=new ModifyUserGroupForm();
				grpForm1.setGroupname(rsGroupList.getString("group_name"));
				grpForm1.setGroupid(rsGroupList.getString("id"));
				String status= rsGroupList.getString("status");
				if(status.equalsIgnoreCase("0"))
				{
					grpForm1.setStatus("Active");
				}
				else
				{
					grpForm1.setStatus("Inactive");
				}
				
				listOFGroup.add(grpForm1);
			}
		
		System.out.println("list length="+listOFGroup.size());
		
		/* if(listOFGroup.size()!=0)
			{
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				//request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				modifyUserGroupForm.setTotalRecords(totalRecords);
				modifyUserGroupForm.setStartRecord(start);
				modifyUserGroupForm.setEndRecord(end);
				
			}
		 if(listOFGroup.size()<10)
		 {
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(startRecord+listOFGroup.size()-1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
				//request.setAttribute("previousButton", "previousButton"); 
			 
		 }*/
		 
		if(totalRecords>10)
		{
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
		
		
		 if(startRecord==1)
		 {
			 request.setAttribute("disablePreviousButton", "disablePreviousButton"); 
		 }
		 if(endRecord>10)
		 {
			 request.setAttribute("previousButton", "previousButton"); 
		 }
		 
		 if(endRecord==totalRecords)
		 {
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
		 }
		}
		 //request.setAttribute("displayRecordNo", "displayRecordNo");
		
	}catch (Exception e) {
	e.printStackTrace();
	}
		request.setAttribute("groupList", listOFGroup);
		modifyUserGroupForm.setMessage("");
		return mapping.findForward("display");
	}
	
	
	public ActionForward curentRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();
	try{
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
		String searchRequired=modifyUserGroupForm.getSearchRequired();

		
		if(searchRequired==null)
		{
			searchRequired="";
		}
			
			String getgroupList="Select * from (SELECT ROW_NUMBER() OVER(ORDER BY grp.ID) AS RowNum,grp.id,grp.group_name,status from user_group as grp where grp.group_name like '%"+searchRequired+"%') as row where row.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			 
			ResultSet rsGroupList=ad.selectQuery(getgroupList);
			while(rsGroupList.next())
			{
				ModifyUserGroupForm grpForm1=new ModifyUserGroupForm();
				grpForm1.setGroupname(rsGroupList.getString("group_name"));
				grpForm1.setGroupid(rsGroupList.getString("id"));
				String status= rsGroupList.getString("status");
				if(status.equalsIgnoreCase("0"))
				{
					grpForm1.setStatus("Active");
				}
				else
				{
					grpForm1.setStatus("Inactive");
				}
				
				listOFGroup.add(grpForm1);
			}
		
		System.out.println("list length="+listOFGroup.size());
		
		/* if(listOFGroup.size()!=0)
			{
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				//request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				modifyUserGroupForm.setTotalRecords(totalRecords);
				modifyUserGroupForm.setStartRecord(start);
				modifyUserGroupForm.setEndRecord(end);
				
			}
		 if(listOFGroup.size()<10)
		 {
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(startRecord+listOFGroup.size()-1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
				//request.setAttribute("previousButton", "previousButton"); 
			 
		 }*/
		 
		if(totalRecords>10)
		{
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
		
		
		 if(startRecord==1)
		 {
			 request.setAttribute("disablePreviousButton", "disablePreviousButton"); 
		 }
		 if(endRecord>10)
		 {
			 request.setAttribute("previousButton", "previousButton"); 
		 }
		 
		 if(endRecord==totalRecords)
		 {
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
		 }
		}
		 //request.setAttribute("displayRecordNo", "displayRecordNo");
		
	}catch (Exception e) {
	e.printStackTrace();
	}
		request.setAttribute("groupList", listOFGroup);
		//modifyUserGroupForm.setMessage("");
		return mapping.findForward("display");
	}
	
	
	
	public ActionForward edit_group(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		String grpcode=request.getParameter("grpcode");
		String grpname=request.getParameter("grpname");
		
		modifyUserGroupForm.setGroupname(grpname);
		modifyUserGroupForm.setGroupid(grpcode);
		modifyUserGroupForm.setMessage("");
		String sereq=modifyUserGroupForm.getSearchRequired();
		
		modifyUserGroupForm.setSearchRequired(sereq);
		return mapping.findForward("modifygroupname");
	}
	
	public ActionForward modifygrpname(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		String grpcode=modifyUserGroupForm.getGroupid();
		String grpname=modifyUserGroupForm.getGroupname();
		grpname=replace(grpname);
		grpname=grpname.trim();
		String check,update;
		try{
  			
			if(grpname.equalsIgnoreCase(""))
			{
				modifyUserGroupForm.setMessage("Please enter proper group name");
				
			}
			
			else
			{
			
			check="Select group_name from user_group where group_name='"+grpname+"'";
			
			ResultSet rsGroupList=ad.selectQuery(check);
			if(rsGroupList.next())
			{
				modifyUserGroupForm.setMessage("Group name "+grpname+" already present");
				return mapping.findForward("modifygroupname");
			}
			
			else
			{
			
			update="Update user_group set group_name='"+grpname+"' where id='"+grpcode+"'";
			  			  
						  int active=ad.SqlExecuteUpdate(update);
						  if(active!=0)
							{
							 
								  modifyUserGroupForm.setMessage(grpname+" Modified Successfully");
							  
							}
							
							else
							{
								modifyUserGroupForm.setMessage("Sorry, Contact your Admin Team");
							
							}
			}			  
			}
			modifyUserGroupForm.setGroupName("");
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(startRecord);
		modifyUserGroupForm.setEndRecord(endRecord);
		return curentRecord(mapping, form, request, response);
		
	}
	
	public ActionForward close(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(startRecord);
		modifyUserGroupForm.setEndRecord(endRecord);
		return curentRecord_without_alert_message(mapping, form, request, response);
	}
	
	public ActionForward close_assign_user(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		
		HttpSession session = request.getSession();
		String tot = (String)session.getAttribute("total");
		String str = (String)session.getAttribute("start");
		String end = (String)session.getAttribute("end");
		String searchRequired = (String)session.getAttribute("searchRequired");
		
		int totalRecords = Integer.parseInt(tot);
		int startRecord = Integer.parseInt(str);
		int endRecord = Integer.parseInt(end);
		
		
		
		
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(startRecord);
		modifyUserGroupForm.setEndRecord(endRecord);
		modifyUserGroupForm.setSearchRequired(searchRequired);
		return curentRecord_without_alert_message(mapping, form, request, response);
	}
	
	public ActionForward assign_users(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		ArrayList contactList=new ArrayList();
		String grpcode=request.getParameter("grpcode");
		String grpname=request.getParameter("grpname");
		
		String total=request.getParameter("total");
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String searchRequired=request.getParameter("searchRequired");
		
		HttpSession session=request.getSession();
		 
		session.setAttribute("total", total);
		session.setAttribute("start", start);
		session.setAttribute("end", end);
		session.setAttribute("searchRequired", searchRequired);
		
		String grp1="Select grp.group_name as grname from user_group as grp where grp.id="+grpcode+"";
		ResultSet rss=ad.selectQuery(grp1);
		try {
			if(rss.next())
			{
				modifyUserGroupForm.setGroupname(rss.getString("grname"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		modifyUserGroupForm.setGroupid(grpcode);
		
		
		try{
			String check;
			check="Select count(username) as cnt from users where group_id='"+grpcode+"'";
			
			ResultSet rsGroupList=ad.selectQuery(check);
			if(rsGroupList.next())
			{
			String count=rsGroupList.getString("cnt");
			if(count.equalsIgnoreCase("0"))
			{
				modifyUserGroupForm.setGroupcount("Not At Assign");
				
			}
			
			else
			{
				modifyUserGroupForm.setGroupcount(count);
			}
			
			
			LinkedList listOfMaterialCode=new LinkedList();
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			 
			 UserInfo user=(UserInfo)session.getAttribute("user");
				
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location"); 
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			modifyUserGroupForm.setLocationIdList(locationList);
			modifyUserGroupForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			modifyUserGroupForm.setDepartmentList(deparmentList);
			
			ArrayList groupList=new ArrayList();
			ArrayList groupLabelList=new ArrayList();
			
			ResultSet rs13 = ad.selectQuery("select id," +
					"group_name from user_group"); 
					while(rs13.next()) {
						groupList.add(rs13.getString("id"));
						groupLabelList.add(rs13.getString("group_name"));
					}
					modifyUserGroupForm.setGroupIdList(groupList);
					modifyUserGroupForm.setGroupLabelList(groupLabelList);
			
			
			
			
			
			String getCount="select count(*) from emp_official_info as u,Location as loc,department as dept,designation as desg,users as usr where  " +
			"u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";


int  totalRecords=0;

	ResultSet rsCount=ad.selectQuery(getCount);
	while(rsCount.next())
	{
		totalRecords=rsCount.getInt(1);	
	}
			
			int startRecord=1;//11
			int endRecord=10;	
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  modifyUserGroupForm.setTotalRecords(totalRecords);
				  modifyUserGroupForm.setStartRecord(startRecord);
				  modifyUserGroupForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  modifyUserGroupForm.setTotalRecords(totalRecords);
					  modifyUserGroupForm.setStartRecord(startRecord);
					  modifyUserGroupForm.setEndRecord(totalRecords);  
				  }

			String getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
			"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
			"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";
				
				
	getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
	modifyUserGroupForm.setSearchQuery(getContactList);
	ResultSet rs=ad.selectQuery(getContactList);
	ModifyUserGroupForm emp=null;
	while(rs.next())
	{
		emp=new ModifyUserGroupForm();
		emp.setEmpcode(rs.getString("PERNR"));
		emp.setReqempcode(rs.getString("PERNR"));
		emp.setFirstName(rs.getString("EMP_FULLNAME"));
		emp.setDesignation(rs.getString("DSGSTXT"));
		emp.setDepartment(rs.getString("DPTSTXT"));
		emp.setLocationId(rs.getString("location_code"));
		
		String grid=rs.getString("grid");
		
		String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
		ResultSet rs1=ad.selectQuery(grp);
		if(rs1.next())
		{
			emp.setEgroupname(rs1.getString("grname"));
			//emp.setReqempcode(rs.getString("PERNR"));
			
		}
		
		else
		{
			emp.setEgroupname("Not Assigned");
			
		}
	
		contactList.add(emp);
	}			
	request.setAttribute("contactlist", contactList);
			
		
				
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("displayRecordNo", "displayRecordNo");
				}
			
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				
				
					
				if(contactList.size()==0)
				{
					request.setAttribute("noRecords", "noRecords");
					modifyUserGroupForm.setMessage("No Records");
				}
			
			
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		
		
		
		return mapping.findForward("assignuser");
	}
	
	
	
	public ActionForward clearUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		ArrayList contactList=new ArrayList();
		String grpcode=modifyUserGroupForm.getGroupid();
		String grpname=modifyUserGroupForm.getGroupname();
		
		modifyUserGroupForm.setGroup("--Select--");
		modifyUserGroupForm.setLocationId("--Select--");
		modifyUserGroupForm.setDepartment("--Select--");
		modifyUserGroupForm.setFirstName("");
		modifyUserGroupForm.setGroupname(grpname);
		modifyUserGroupForm.setGroupid(grpcode);
		
		
		try{
			String check;
			check="Select count(username) as cnt from users where group_id='"+grpcode+"'";
			
			ResultSet rsGroupList=ad.selectQuery(check);
			if(rsGroupList.next())
			{
			String count=rsGroupList.getString("cnt");
			if(count.equalsIgnoreCase("0"))
			{
				modifyUserGroupForm.setGroupcount("Not At Assign");
				
			}
			
			else
			{
				modifyUserGroupForm.setGroupcount(count);
			}
			
			
			LinkedList listOfMaterialCode=new LinkedList();
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location"); 
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			modifyUserGroupForm.setLocationIdList(locationList);
			modifyUserGroupForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			modifyUserGroupForm.setDepartmentList(deparmentList);
			
			ArrayList groupList=new ArrayList();
			ArrayList groupLabelList=new ArrayList();
			
			ResultSet rs13 = ad.selectQuery("select id," +
					"group_name from user_group"); 
					while(rs13.next()) {
						groupList.add(rs13.getString("id"));
						groupLabelList.add(rs13.getString("group_name"));
					}
					modifyUserGroupForm.setGroupIdList(groupList);
					modifyUserGroupForm.setGroupLabelList(groupLabelList);
			
			
			
			
			
			String getCount="select count(*) from emp_official_info as u,Location as loc,department as dept,designation as desg,users as usr where  " +
			"u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";


int  totalRecords=0;

	ResultSet rsCount=ad.selectQuery(getCount);
	while(rsCount.next())
	{
		totalRecords=rsCount.getInt(1);	
	}
			
			int startRecord=1;//11
			int endRecord=10;	
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  modifyUserGroupForm.setTotalRecords(totalRecords);
				  modifyUserGroupForm.setStartRecord(startRecord);
				  modifyUserGroupForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  modifyUserGroupForm.setTotalRecords(totalRecords);
					  modifyUserGroupForm.setStartRecord(startRecord);
					  modifyUserGroupForm.setEndRecord(totalRecords);  
				  }

			String getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
			"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
			"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";
				
				
	getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
	modifyUserGroupForm.setSearchQuery(getContactList);
	ResultSet rs=ad.selectQuery(getContactList);
	ModifyUserGroupForm emp=null;
	while(rs.next())
	{
		emp=new ModifyUserGroupForm();
		emp.setEmpcode(rs.getString("PERNR"));
		emp.setReqempcode(rs.getString("PERNR"));
		emp.setFirstName(rs.getString("EMP_FULLNAME"));
		emp.setDesignation(rs.getString("DSGSTXT"));
		emp.setDepartment(rs.getString("DPTSTXT"));
		emp.setLocationId(rs.getString("location_code"));
		
		String grid=rs.getString("grid");
		
		String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
		ResultSet rs1=ad.selectQuery(grp);
		if(rs1.next())
		{
			emp.setEgroupname(rs1.getString("grname"));
			//emp.setReqempcode(rs.getString("PERNR"));
			
		}
		
		else
		{
			emp.setEgroupname("Not Assigned");
			
		}
	
		contactList.add(emp);
	}			
	request.setAttribute("contactlist", contactList);
			
		
				
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("displayRecordNo", "displayRecordNo");
				}
			
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				
				
					
				if(contactList.size()==0)
				{
					request.setAttribute("noRecords", "noRecords");
					modifyUserGroupForm.setMessage("No Records");
				}
			
			
			
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		
		
		modifyUserGroupForm.setMessage("");
		return mapping.findForward("assignuser");
	}
	
	public ActionForward search_assign_user(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		
		String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String groupcount=request.getParameter("groupcount");
		
		modifyUserGroupForm.setGroupid(grpid);
		String grp1="Select grp.group_name as grname from user_group as grp where grp.id="+grpid+"";
		ResultSet rss=ad.selectQuery(grp1);
		try {
			if(rss.next())
			{
				modifyUserGroupForm.setGroupname(rss.getString("grname"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		modifyUserGroupForm.setGroupcount(groupcount);
		
		
		ArrayList contactList=new ArrayList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		try{
			LinkedList listOfMaterialCode=new LinkedList();
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			modifyUserGroupForm.setLocationIdList(locationList);
			modifyUserGroupForm.setLocationLabelList(locationLabelList);
			
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			modifyUserGroupForm.setDepartmentList(deparmentList);
			
			ArrayList groupList=new ArrayList();
			ArrayList groupLabelList=new ArrayList();
			
			ResultSet rs13 = ad.selectQuery("select id," +
					"group_name from user_group"); 
					while(rs13.next()) {
						groupList.add(rs13.getString("id"));
						groupLabelList.add(rs13.getString("group_name"));
					}
					modifyUserGroupForm.setGroupIdList(groupList);
					modifyUserGroupForm.setGroupLabelList(groupLabelList);
		
			String getCount="";
			String getContactList="";
			String locationId=modifyUserGroupForm.getLocationId();
			String department=modifyUserGroupForm.getDepartment();
			String firstName=modifyUserGroupForm.getFirstName();
			String group=modifyUserGroupForm.getGroup();
			
			
			if(!group.equalsIgnoreCase(""))
			{
				getCount="select count(*) from emp_official_info as u,Location as loc,department as dept,designation as desg,users as usr,user_group as ugrp where  " +
						"u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and ugrp.id=usr.group_id";

			}
			
			else
			{

				getCount="select count(*) from emp_official_info as u,Location as loc,department as dept,designation as desg,users as usr where  " +
				"u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";

			}
			
			
			
			
if(!group.equalsIgnoreCase(""))
{
getCount=getCount+" and ugrp.id= '"+group+"'";
}		
			
if(!locationId.equalsIgnoreCase(""))
{
getCount=getCount+" and u.LOCID= '"+locationId+"'";
}
if(!department.equalsIgnoreCase(""))
{
	getCount=getCount+" and dept.DPTSTXT like'%"+department+"%'";
}
if(!firstName.equalsIgnoreCase(""))
{

getCount=getCount+" and  (u.EMP_FULLNAME like '%"+firstName+"%' or u.EMAIL_ID  like '%"+firstName+"%' or u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' or desg.DSGSTXT like '%"+firstName+"%' or u.PERNR like '%"+firstName+"%' )";

}

int  totalRecords=0;

	ResultSet rsCount=ad.selectQuery(getCount);
	while(rsCount.next())
	{
		totalRecords=rsCount.getInt(1);	
	}
			
			int startRecord=1;//11
			int endRecord=10;	
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  modifyUserGroupForm.setTotalRecords(totalRecords);
				  modifyUserGroupForm.setStartRecord(startRecord);
				  modifyUserGroupForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  modifyUserGroupForm.setTotalRecords(totalRecords);
					  modifyUserGroupForm.setStartRecord(startRecord);
					  modifyUserGroupForm.setEndRecord(totalRecords);  
				  }
			
			
			
			
			if(!group.equalsIgnoreCase(""))
			{
				getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
						"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr,user_group as ugrp where u.LOCID=loc.location_code " +
						"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and ugrp.id=usr.group_id";

			}
			
			else
			{

				getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
						"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
						"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";

			}

				
				if(!group.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and ugrp.id= '"+group+"'";
				}
			
				if(!locationId.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and u.LOCID= '"+locationId+"'";
				}
				if(!department.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
				}
				if(!firstName.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' or desg.DSGSTXT like '%"+firstName+"%' or u.PERNR like '%"+firstName+"%' ) ";
				}
				
	getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
	modifyUserGroupForm.setSearchQuery(getContactList);
	ResultSet rs=ad.selectQuery(getContactList);
	ModifyUserGroupForm emp=null;
	while(rs.next())
	{
		emp=new ModifyUserGroupForm();
		emp.setEmpcode(rs.getString("PERNR"));
		emp.setFirstName(rs.getString("EMP_FULLNAME"));
		emp.setDesignation(rs.getString("DSGSTXT"));
		emp.setDepartment(rs.getString("DPTSTXT"));
		emp.setLocationId(rs.getString("location_code"));
		
		String grid=rs.getString("grid");
		
		String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
		ResultSet rs1=ad.selectQuery(grp);
		if(rs1.next())
		{
			
			emp.setEgroupname(rs1.getString("grname"));
			//emp.setReqempcode(rs.getString("PERNR"));
			
		}
		
		else
		{
			emp.setEgroupname("Not Assigned");
			
		}
	
		
	
		contactList.add(emp);
	}			
	request.setAttribute("contactlist", contactList);
			
		
				
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("displayRecordNo", "displayRecordNo");
				}
			
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				
				
					
				if(contactList.size()==0)
				{
					request.setAttribute("noRecords", "noRecords");
					modifyUserGroupForm.setMessage("No Records");
				}
				else
				{
					modifyUserGroupForm.setMessage("");
				}
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("assignuser");
	}
	
	
	public ActionForward firstUserRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String groupcount=request.getParameter("groupcount");
		
		modifyUserGroupForm.setGroupid(grpid);
		modifyUserGroupForm.setGroupname(grpname);
		modifyUserGroupForm.setGroupcount(groupcount);
		
		GroupDao ad=new GroupDao();
		LinkedList customerList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();
		try{
			
			
			LinkedList listOfMaterialCode=new LinkedList();
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			 
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location"); 
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			modifyUserGroupForm.setLocationIdList(locationList);
			modifyUserGroupForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			modifyUserGroupForm.setDepartmentList(deparmentList);
			
			ArrayList groupList=new ArrayList();
			ArrayList groupLabelList=new ArrayList();
			
			ResultSet rs13 = ad.selectQuery("select id," +
					"group_name from user_group"); 
					while(rs13.next()) {
						groupList.add(rs13.getString("id"));
						groupLabelList.add(rs13.getString("group_name"));
					}
					modifyUserGroupForm.setGroupIdList(groupList);
					modifyUserGroupForm.setGroupLabelList(groupLabelList);
			
			int totalRecords=modifyUserGroupForm.getTotalRecords();//21
			int startRecord=modifyUserGroupForm.getStartRecord();//11
			int endRecord=modifyUserGroupForm.getEndRecord();	
			
			
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  modifyUserGroupForm.setTotalRecords(totalRecords);
				  modifyUserGroupForm.setStartRecord(startRecord);
				  modifyUserGroupForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  modifyUserGroupForm.setTotalRecords(totalRecords);
					  modifyUserGroupForm.setStartRecord(startRecord);
					  modifyUserGroupForm.setEndRecord(totalRecords);  
				  }

			String getCount="";
			String getContactList="";
			String locationId=modifyUserGroupForm.getLocationId();
			String department=modifyUserGroupForm.getDepartment();
			String firstName=modifyUserGroupForm.getFirstName();
			String group=modifyUserGroupForm.getGroup();


if(!group.equalsIgnoreCase(""))
			{
				getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
						"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr,user_group as ugrp where u.LOCID=loc.location_code " +
						"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and ugrp.id=usr.group_id";

			}
			
			else
			{

				getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
						"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
						"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";

			}

				
				if(!group.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and ugrp.id= '"+group+"'";
				}
			
				if(!locationId.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and u.LOCID= '"+locationId+"'";
				}
				if(!department.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
				}
				if(!firstName.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' or desg.DSGSTXT like '%"+firstName+"%' or u.PERNR like '%"+firstName+"%' ) ";
				}
				
	getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
	modifyUserGroupForm.setSearchQuery(getContactList);
	ResultSet rs=ad.selectQuery(getContactList);
	ModifyUserGroupForm emp=null;
	while(rs.next())
	{
		emp=new ModifyUserGroupForm();
		emp.setEmpcode(rs.getString("PERNR"));
		emp.setFirstName(rs.getString("EMP_FULLNAME"));
		emp.setDesignation(rs.getString("DSGSTXT"));
		emp.setDepartment(rs.getString("DPTSTXT"));
		emp.setLocationId(rs.getString("location_code"));
		
		String grid=rs.getString("grid");
		
		String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
		ResultSet rs1=ad.selectQuery(grp);
		if(rs1.next())
		{
			emp.setEgroupname(rs1.getString("grname"));
			//emp.setReqempcode(rs.getString("PERNR"));
			
		}
		
		else
		{
			emp.setEgroupname("Not Assigned");
			
		}
	
		
	
		listOFGroup.add(emp);
	}			
	//request.setAttribute("contactlist", listOFGroup);
	
			
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
				}
			 	
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				request.setAttribute("contactlist", listOFGroup);
				request.setAttribute("displayRecordNo", "displayRecordNo");
				//request.setAttribute("materialCodeList","materialCodeList");
					
				
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	modifyUserGroupForm.setMessage("");
	return mapping.findForward("assignuser");
	}
	
	
	
	
	public ActionForward previousUserRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String groupcount=request.getParameter("groupcount");
		
		modifyUserGroupForm.setGroupid(grpid);
		modifyUserGroupForm.setGroupname(grpname);
		modifyUserGroupForm.setGroupcount(groupcount);
		
		GroupDao ad=new GroupDao();
		LinkedList customerList=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();
	try{
		
		
		LinkedList listOfMaterialCode=new LinkedList();
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		 
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location"); 
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code"));
			locationLabelList.add(rs11.getString("location_code"));
		}
		modifyUserGroupForm.setLocationIdList(locationList);
		modifyUserGroupForm.setLocationLabelList(locationLabelList);
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
		while(rs12.next()){
			
			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		modifyUserGroupForm.setDepartmentList(deparmentList);
		
		
		ArrayList groupList=new ArrayList();
		ArrayList groupLabelList=new ArrayList();
		
		ResultSet rs13 = ad.selectQuery("select id," +
				"group_name from user_group"); 
				while(rs13.next()) {
					groupList.add(rs13.getString("id"));
					groupLabelList.add(rs13.getString("group_name"));
				}
				modifyUserGroupForm.setGroupIdList(groupList);
				modifyUserGroupForm.setGroupLabelList(groupLabelList);
				
		System.out.println("Start Record="+modifyUserGroupForm.getStartRecord());
		System.out.println("End record="+modifyUserGroupForm.getEndRecord());
		System.out.println("Total Record="+modifyUserGroupForm.getTotalRecords());	
		
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int endRecord=modifyUserGroupForm.getStartRecord()-1;//20
		int startRecord=modifyUserGroupForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(1);
		modifyUserGroupForm.setEndRecord(10);
		
		String getCount="";
		String getContactList="";
		String locationId=modifyUserGroupForm.getLocationId();
		String department=modifyUserGroupForm.getDepartment();
		String firstName=modifyUserGroupForm.getFirstName();
		String group=modifyUserGroupForm.getGroup();


if(!group.equalsIgnoreCase(""))
		{
			getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
					"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr,user_group as ugrp where u.LOCID=loc.location_code " +
					"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and ugrp.id=usr.group_id";

		}
		
		else
		{

			getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
					"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
					"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";

		}

			
			if(!group.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and ugrp.id= '"+group+"'";
			}
		
			if(!locationId.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and u.LOCID= '"+locationId+"'";
			}
			if(!department.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
			}
			if(!firstName.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' or desg.DSGSTXT like '%"+firstName+"%' or u.PERNR like '%"+firstName+"%' ) ";
			}
			
getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
modifyUserGroupForm.setSearchQuery(getContactList);
ResultSet rs=ad.selectQuery(getContactList);
ModifyUserGroupForm emp=null;
while(rs.next())
{
	emp=new ModifyUserGroupForm();
	emp.setEmpcode(rs.getString("PERNR"));
	emp.setFirstName(rs.getString("EMP_FULLNAME"));
	emp.setDesignation(rs.getString("DSGSTXT"));
	emp.setDepartment(rs.getString("DPTSTXT"));
	emp.setLocationId(rs.getString("location_code"));
	
	String grid=rs.getString("grid");
	
	String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
	ResultSet rs1=ad.selectQuery(grp);
	if(rs1.next())
	{
		emp.setEgroupname(rs1.getString("grname"));
		//emp.setReqempcode(rs.getString("PERNR"));
		
	}
	
	else
	{
		emp.setEgroupname("Not Assigned");
		
	}

	

	listOFGroup.add(emp);
}			
//request.setAttribute("contactlist", listOFGroup);
	
		modifyUserGroupForm.setTotalRecords(totalRecords);
		modifyUserGroupForm.setStartRecord(startRecord);
		modifyUserGroupForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(customerList.size()<10)
			{
				modifyUserGroupForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	modifyUserGroupForm.setMessage("");
	request.setAttribute("contactlist", listOFGroup);
	return mapping.findForward("assignuser");
	}
	
	
	
	public ActionForward nextUserRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String groupcount=request.getParameter("groupcount");
		
		modifyUserGroupForm.setGroupid(grpid);
		modifyUserGroupForm.setGroupname(grpname);
		modifyUserGroupForm.setGroupcount(groupcount);
		
		GroupDao ad=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		ArrayList listOFGroup=new ArrayList();
	try{
		
		LinkedList listOfMaterialCode=new LinkedList();
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		 
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location"); 
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code"));
			locationLabelList.add(rs11.getString("location_code"));
		}
		modifyUserGroupForm.setLocationIdList(locationList);
		modifyUserGroupForm.setLocationLabelList(locationLabelList);
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
		while(rs12.next()){
			
			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		modifyUserGroupForm.setDepartmentList(deparmentList);
		
		ArrayList groupList=new ArrayList();
		ArrayList groupLabelList=new ArrayList();
		
		ResultSet rs13 = ad.selectQuery("select id," +
				"group_name from user_group"); 
				while(rs13.next()) {
					groupList.add(rs13.getString("id"));
					groupLabelList.add(rs13.getString("group_name"));
				}
				modifyUserGroupForm.setGroupIdList(groupList);
				modifyUserGroupForm.setGroupLabelList(groupLabelList);
		
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
	
		if(totalRecords>=endRecord)
		{
			if(totalRecords==endRecord)
			{
				startRecord=startRecord;
				endRecord=totalRecords;
			}
			if(totalRecords>endRecord)
			{
			startRecord=endRecord+1;
			endRecord=endRecord+10;
			}
		
			String getCount="";
			String getContactList="";
			String locationId=modifyUserGroupForm.getLocationId();
			String department=modifyUserGroupForm.getDepartment();
			String firstName=modifyUserGroupForm.getFirstName();
			String group=modifyUserGroupForm.getGroup();


if(!group.equalsIgnoreCase(""))
			{
				getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
						"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr,user_group as ugrp where u.LOCID=loc.location_code " +
						"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and ugrp.id=usr.group_id";

			}
			
			else
			{

				getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
						"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
						"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";

			}

				
				if(!group.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and ugrp.id= '"+group+"'";
				}
			
				if(!locationId.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and u.LOCID= '"+locationId+"'";
				}
				if(!department.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
				}
				if(!firstName.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' or desg.DSGSTXT like '%"+firstName+"%' or u.PERNR like '%"+firstName+"%' ) ";
				}
				
	getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
	modifyUserGroupForm.setSearchQuery(getContactList);
	ResultSet rs=ad.selectQuery(getContactList);
	ModifyUserGroupForm emp=null;
	while(rs.next())
	{
		emp=new ModifyUserGroupForm();
		emp.setEmpcode(rs.getString("PERNR"));
		emp.setFirstName(rs.getString("EMP_FULLNAME"));
		emp.setDesignation(rs.getString("DSGSTXT"));
		emp.setDepartment(rs.getString("DPTSTXT"));
		emp.setLocationId(rs.getString("location_code"));
		
		String grid=rs.getString("grid");
		
		String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
		ResultSet rs1=ad.selectQuery(grp);
		if(rs1.next())
		{
			emp.setEgroupname(rs1.getString("grname"));
			//emp.setReqempcode(rs.getString("PERNR"));
			
		}
		
		else
		{
			emp.setEgroupname("Not Assigned");
			
		}
	
		
	
		listOFGroup.add(emp);
	}			
	//request.setAttribute("contactlist", listOFGroup);
		}
		System.out.println("list length="+listOFGroup.size());
		
		 if(listOFGroup.size()!=0)
			{
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				modifyUserGroupForm.setTotalRecords(totalRecords);
				modifyUserGroupForm.setStartRecord(start);
				modifyUserGroupForm.setEndRecord(end);
				
			}
		 if(listOFGroup.size()<10)
		 {
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(startRecord+listOFGroup.size()-1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton"); 
			 
		 }
		 
		 if(endRecord==totalRecords)
		 {
			 request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
		 }
		 request.setAttribute("displayRecordNo", "displayRecordNo");
		
	}catch (Exception e) {
	e.printStackTrace();
	}
		request.setAttribute("contactlist", listOFGroup);
		modifyUserGroupForm.setMessage("");
		return mapping.findForward("assignuser");
	}
	
	
	
	
	public ActionForward lastUserRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String groupcount=request.getParameter("groupcount");
		
		modifyUserGroupForm.setGroupid(grpid);
		modifyUserGroupForm.setGroupname(grpname);
		modifyUserGroupForm.setGroupcount(groupcount);
		
		GroupDao ad=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=Integer.parseInt(user.getEmployeeNo());
		ArrayList listOFGroup=new ArrayList();
		try{
			
			LinkedList listOfMaterialCode=new LinkedList();
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			 
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location"); 
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			modifyUserGroupForm.setLocationIdList(locationList);
			modifyUserGroupForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			modifyUserGroupForm.setDepartmentList(deparmentList);
			
			ArrayList groupList=new ArrayList();
			ArrayList groupLabelList=new ArrayList();
			
			ResultSet rs13 = ad.selectQuery("select id," +
					"group_name from user_group"); 
					while(rs13.next()) {
						groupList.add(rs13.getString("id"));
						groupLabelList.add(rs13.getString("group_name"));
					}
					modifyUserGroupForm.setGroupIdList(groupList);
					modifyUserGroupForm.setGroupLabelList(groupLabelList);
					
			int totalRecords=modifyUserGroupForm.getTotalRecords();//21
			int startRecord=modifyUserGroupForm.getStartRecord();//11
			int endRecord=modifyUserGroupForm.getEndRecord();	
			
			
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(totalRecords);
			
			 String getCount="";
				String getContactList="";
				String locationId=modifyUserGroupForm.getLocationId();
				String department=modifyUserGroupForm.getDepartment();
				String firstName=modifyUserGroupForm.getFirstName();
				String group=modifyUserGroupForm.getGroup();


	if(!group.equalsIgnoreCase(""))
				{
					getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
							"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr,user_group as ugrp where u.LOCID=loc.location_code " +
							"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and ugrp.id=usr.group_id";

				}
				
				else
				{

					getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
							"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
							"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";

				}

					
					if(!group.equalsIgnoreCase(""))
					{
						getContactList=getContactList+" and ugrp.id= '"+group+"'";
					}
				
					if(!locationId.equalsIgnoreCase(""))
					{
						getContactList=getContactList+" and u.LOCID= '"+locationId+"'";
					}
					if(!department.equalsIgnoreCase(""))
					{
						getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
					}
					if(!firstName.equalsIgnoreCase(""))
					{
						getContactList=getContactList+" and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' or desg.DSGSTXT like '%"+firstName+"%' or u.PERNR like '%"+firstName+"%' ) ";
					}
					
		getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
		modifyUserGroupForm.setSearchQuery(getContactList);
		ResultSet rs=ad.selectQuery(getContactList);
		ModifyUserGroupForm emp=null;
		while(rs.next())
		{
			emp=new ModifyUserGroupForm();
			emp.setEmpcode(rs.getString("PERNR"));
			emp.setFirstName(rs.getString("EMP_FULLNAME"));
			emp.setDesignation(rs.getString("DSGSTXT"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setLocationId(rs.getString("location_code"));
			
			String grid=rs.getString("grid");
			
			String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
			ResultSet rs1=ad.selectQuery(grp);
			if(rs1.next())
			{
				emp.setEgroupname(rs1.getString("grname"));
				//emp.setReqempcode(rs.getString("PERNR"));
				
			}
			
			else
			{
				emp.setEgroupname("Not Assigned");
				
			}
		
			
		
			listOFGroup.add(emp);
		}			
		//request.setAttribute("contactlist", listOFGroup);
			 
			 request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton");
				if(listOFGroup.size()<10)
				{
					
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				request.setAttribute("contactlist", listOFGroup);
				request.setAttribute("displayRecordNo", "displayRecordNo");
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	modifyUserGroupForm.setMessage("");
	return mapping.findForward("assignuser");
	}
	
	
	public ActionForward curentuserRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		/*String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String groupcount=request.getParameter("groupcount");*/

		String grpid=modifyUserGroupForm.getGroupid();
		String grpname=modifyUserGroupForm.getGroupname();
		String groupcount=modifyUserGroupForm.getGroupcount();
		
		
		modifyUserGroupForm.setGroupid(grpid);
		modifyUserGroupForm.setGroupname(grpname);
		modifyUserGroupForm.setGroupcount(groupcount);
		
		GroupDao ad=new GroupDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=Integer.parseInt(user.getEmployeeNo());
		ArrayList listOFGroup=new ArrayList();
		try{
			
			LinkedList listOfMaterialCode=new LinkedList();
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			 
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location"); 
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			modifyUserGroupForm.setLocationIdList(locationList);
			modifyUserGroupForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			modifyUserGroupForm.setDepartmentList(deparmentList);
	
			ArrayList groupList=new ArrayList();
			ArrayList groupLabelList=new ArrayList();
			
			ResultSet rs13 = ad.selectQuery("select id," +
					"group_name from user_group"); 
					while(rs13.next()) {
						groupList.add(rs13.getString("id"));
						groupLabelList.add(rs13.getString("group_name"));
					}
					modifyUserGroupForm.setGroupIdList(groupList);
					modifyUserGroupForm.setGroupLabelList(groupLabelList);
					
		int totalRecords=modifyUserGroupForm.getTotalRecords();//21
		int startRecord=modifyUserGroupForm.getStartRecord();//11
		int endRecord=modifyUserGroupForm.getEndRecord();
		
		if(totalRecords==0)
		{
			startRecord=1;
			endRecord=10;
		}
	
		
			
		String getCount="";
		String getContactList="";
		String locationId=modifyUserGroupForm.getLocationId();
		String department=modifyUserGroupForm.getDepartment();
		String firstName=modifyUserGroupForm.getFirstName();
		String group=modifyUserGroupForm.getGroup();


if(!group.equalsIgnoreCase(""))
		{
			getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
					"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr,user_group as ugrp where u.LOCID=loc.location_code " +
					"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and ugrp.id=usr.group_id";

		}
		
		else
		{

			getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
					"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
					"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber";

		}

			
			if(!group.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and ugrp.id= '"+group+"'";
			}
		
			if(!locationId.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and u.LOCID= '"+locationId+"'";
			}
			if(!department.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
			}
			if(!firstName.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' or desg.DSGSTXT like '%"+firstName+"%' or u.PERNR like '%"+firstName+"%' ) ";
			}
			
getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
modifyUserGroupForm.setSearchQuery(getContactList);
ResultSet rs=ad.selectQuery(getContactList);
ModifyUserGroupForm emp=null;
while(rs.next())
{
	emp=new ModifyUserGroupForm();
	emp.setEmpcode(rs.getString("PERNR"));
	emp.setFirstName(rs.getString("EMP_FULLNAME"));
	emp.setDesignation(rs.getString("DSGSTXT"));
	emp.setDepartment(rs.getString("DPTSTXT"));
	emp.setLocationId(rs.getString("location_code"));
	
	String grid=rs.getString("grid");
	
	String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
	ResultSet rs1=ad.selectQuery(grp);
	if(rs1.next())
	{
		emp.setEgroupname(rs1.getString("grname"));
		//emp.setReqempcode(rs.getString("PERNR"));
		
	}
	
	else
	{
		emp.setEgroupname("Not Assigned");
		
	}

	

	listOFGroup.add(emp);
}			
//request.setAttribute("contactlist", listOFGroup);
		
		System.out.println("list length="+listOFGroup.size());
		
		/* if(listOFGroup.size()!=0)
			{
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				//request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				modifyUserGroupForm.setTotalRecords(totalRecords);
				modifyUserGroupForm.setStartRecord(start);
				modifyUserGroupForm.setEndRecord(end);
				
			}
		 if(listOFGroup.size()<10)
		 {
			 modifyUserGroupForm.setTotalRecords(totalRecords);
			 modifyUserGroupForm.setStartRecord(startRecord);
			 modifyUserGroupForm.setEndRecord(startRecord+listOFGroup.size()-1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
				//request.setAttribute("previousButton", "previousButton"); 
		 
		 }*/
		if(totalRecords>10)
		{
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
		
		 if(startRecord==1)
		 {
			 request.setAttribute("disablePreviousButton", "disablePreviousButton"); 
		 }
		 if(endRecord>10)
		 {
			 request.setAttribute("previousButton", "previousButton"); 
		 }
		 
		 if(endRecord==totalRecords)
		 {
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
		 }
		 if(endRecord<totalRecords)
		 {
			 request.setAttribute("nextButton", "nextButton");
		 }
		}
		 //request.setAttribute("displayRecordNo", "displayRecordNo");
		
	}catch (Exception e) {
	e.printStackTrace();
	}
		request.setAttribute("contactlist", listOFGroup);
		//modifyUserGroupForm.setMessage("");
		return mapping.findForward("assignuser");
	}
	
	
	public ActionForward assign_user_to_group(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		String grid=modifyUserGroupForm.getGroupid();
		String grname=modifyUserGroupForm.getGroupname();
		String count=modifyUserGroupForm.getGroupcount();
		
		GroupDao ad=new GroupDao();
		String Checkvalupdate="",unCheckvalupdate="",uncheckselect="",verify="";
		
		String checkValues=request.getParameter("cValues");
		
		
		String uncheckValues=request.getParameter("unValues");
		
		
		
		
		System.out.println(checkValues);
		System.out.println(uncheckValues);
		
		int aaa=uncheckValues.length();
		
		
		
		
		int bbb=uncheckValues.length();
		
		try
		{
		if(uncheckValues!="")
		{
			uncheckValues = uncheckValues.substring(0, uncheckValues.length()-1);
			
			//Defalult group is 33
			unCheckvalupdate="Update users set group_id=33 where employeenumber in ("+uncheckValues+") and group_id='"+grid+"'";
			int uch=ad.SqlExecuteUpdate(unCheckvalupdate);
			
			
			
		}
		
		
		if(checkValues!="")
		{
			  checkValues = checkValues.substring(0, checkValues.length()-1);
			  Checkvalupdate="Update users set group_id='"+grid+"' where employeenumber in ("+checkValues+")";
			  
			  int ch=ad.SqlExecuteUpdate(Checkvalupdate);
			 
		}
		
		modifyUserGroupForm.setMessage("Users are successfully assigned to "+grname+" group");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		modifyUserGroupForm.setGroupname(grname);
		return curentuserRecord(mapping, form, request, response);
		//return mapping.findForward("assignuser");
	
	}
	
	
	/*public ActionForward search_assign_user(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		
		String grpid=request.getParameter("groupid");
		String grpname=request.getParameter("groupname");
		String groupcount=request.getParameter("groupcount");
		
		modifyUserGroupForm.setGroupid(grpid);
		modifyUserGroupForm.setGroupname(grpname);
		modifyUserGroupForm.setGroupcount(groupcount);
		
		GroupDao ad=new GroupDao();
		ArrayList contactList=new ArrayList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		try{
			LinkedList listOfMaterialCode=new LinkedList();
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			modifyUserGroupForm.setLocationIdList(locationList);
			modifyUserGroupForm.setLocationLabelList(locationLabelList);
			
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			modifyUserGroupForm.setDepartmentList(deparmentList);
			
		
			String locationId=modifyUserGroupForm.getLocationId();
			String department=modifyUserGroupForm.getDepartment();
			String firstName=modifyUserGroupForm.getFirstName();
			
			String getCount="select count(*) from emp_official_info as u,Location as loc,department as dept,designation as desg,users as usr,user_group as grp where  " +
			"u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and usr.group_id=grp.id";
if(!locationId.equalsIgnoreCase(""))
{
getCount=getCount+" and u.LOCID= '"+locationId+"'";
}
if(!department.equalsIgnoreCase(""))
{
getCount=getCount+" and dept.DPTSTXT like'%"+department+"%'";
}
if(!firstName.equalsIgnoreCase(""))
{

getCount=getCount+" and  (u.EMP_FULLNAME like '%"+firstName+"%' or u.EMAIL_ID  like '%"+firstName+"%' or u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' or desg.DSGSTXT like '%"+firstName+"%' or grp.group_name like '%"+firstName+"%' or grp.group_name=null )";

}

int  totalRecords=0;

	ResultSet rsCount=ad.selectQuery(getCount);
	while(rsCount.next())
	{
		totalRecords=rsCount.getInt(1);	
	}
			
			int startRecord=1;//11
			int endRecord=10;	
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  modifyUserGroupForm.setTotalRecords(totalRecords);
				  modifyUserGroupForm.setStartRecord(startRecord);
				  modifyUserGroupForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  modifyUserGroupForm.setTotalRecords(totalRecords);
					  modifyUserGroupForm.setStartRecord(startRecord);
					  modifyUserGroupForm.setEndRecord(totalRecords);  
				  }

			String getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
			"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,usr.group_id as grid from emp_official_info as u,user_group as grp,Location as loc,department as  dept,designation as desg,users as usr where u.LOCID=loc.location_code " +
			"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR=usr.employeenumber and usr.group_id=grp.id";
				if(!locationId.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and u.LOCID= '"+locationId+"'";
				}
				if(!department.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
				}
				if(!firstName.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%'  or desg.DSGSTXT like '%"+firstName+"%' or grp.group_name like '%"+firstName+"%' or grp.group_name=null ) ";
				}
				
	getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
	modifyUserGroupForm.setSearchQuery(getContactList);
	ResultSet rs=ad.selectQuery(getContactList);
	ModifyUserGroupForm emp=null;
	while(rs.next())
	{
		emp=new ModifyUserGroupForm();
		emp.setEmpcode(rs.getString("PERNR"));
		emp.setFirstName(rs.getString("EMP_FULLNAME"));
		emp.setDesignation(rs.getString("DSGSTXT"));
		emp.setDepartment(rs.getString("DPTSTXT"));
		emp.setLocationId(rs.getString("location_code"));
		
		String grid=rs.getString("grid");
		
		String grp="Select grp.group_name as grname from user_group as grp where grp.id="+grid+"";
		ResultSet rs1=ad.selectQuery(grp);
		if(rs1.next())
		{
			emp.setEgroupname(rs1.getString("grname"));
			//emp.setReqempcode(rs.getString("PERNR"));
			
		}
		
		else
		{
			emp.setEgroupname("Not Assigned");
			
		}
	
		
	
		contactList.add(emp);
	}			
	request.setAttribute("contactlist", contactList);
			
		
				
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("displayRecordNo", "displayRecordNo");
				}
			
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				
				
					
				if(contactList.size()==0)
				{
					request.setAttribute("noRecords", "noRecords");
					modifyUserGroupForm.setMessage("No Records");
				}
				else
				{
					modifyUserGroupForm.setMessage("");
				}
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("assignuser");
	}*/
	
	
	public void droupdown(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
	
		ModifyUserGroupForm modifyUserGroupForm=(ModifyUserGroupForm) form;
		GroupDao ad=new GroupDao();
		HttpSession session=request.getSession();
		
		LinkedList listOfMaterialCode=new LinkedList();
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		try{
			 
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location"); 
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code"));
			locationLabelList.add(rs11.getString("location_code"));
		}
		modifyUserGroupForm.setLocationIdList(locationList);
		modifyUserGroupForm.setLocationLabelList(locationLabelList);
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
		while(rs12.next()){
			
			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		modifyUserGroupForm.setDepartmentList(deparmentList);
		
		ArrayList groupList=new ArrayList();
		ArrayList groupLabelList=new ArrayList();
		
		ResultSet rs13 = ad.selectQuery("select id," +
				"group_name from user_group"); 
				while(rs13.next()) {
					groupList.add(rs13.getString("id"));
					groupLabelList.add(rs13.getString("group_name"));
				}
				modifyUserGroupForm.setGroupIdList(groupList);
				modifyUserGroupForm.setGroupLabelList(groupLabelList);

		 }
		 catch (Exception e) {
				e.printStackTrace();
			}
	}
}
