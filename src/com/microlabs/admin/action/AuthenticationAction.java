package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.AuthenticationForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;

public class AuthenticationAction extends DispatchAction{
	
	public ActionForward deleteAuthenticationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AuthenticationForm authForm=(AuthenticationForm)form;
		UserDao ad=new UserDao();
		String requestType=authForm.getRequestType();
		int j=0;
		j=ad.SqlExecuteUpdate("delete from Approvers_Details where Type='"+requestType+"'");
		if(j>0)
		{
			authForm.setMessage("Authentication Level Successfully Deleted");
		}else{
			authForm.setMessage("Error...When Authentication Updating.Please Check");
		} 
		return mapping.findForward("display");
	}
	
	
	public ActionForward modifyAuthenticationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AuthenticationForm authForm=(AuthenticationForm)form;
		UserDao ad=new UserDao();
		String requestType=authForm.getRequestType();
		String[] approverIDS=authForm.getApproverID();
		String[] designation=authForm.getDesignation();
		String[] emailID=authForm.getEmailID();
		
		try{
	int j=0;
	j=ad.SqlExecuteUpdate("delete from Approvers_Details where Type='"+requestType+"'");
	for(int i=1;i<approverIDS.length;i++)
	{
		if(!(approverIDS[i].equalsIgnoreCase("Employee ID")))
		{
			
			String empDesignation="";
			String empName="";
			String getEmpDesignation="select * from users where id='"+approverIDS[i]+"'";
			ResultSet rsEmpDesign=ad.selectQuery(getEmpDesignation);
			while(rsEmpDesign.next())
			{
				empDesignation=rsEmpDesign.getString("designation_id");
				empName=rsEmpDesign.getString("fullname");
			}
			
			String saveApprover="insert into Approvers_Details(Approver_id,Approver_designation,Employee_Name,Priority,Type,plant,emailID) " +
					"values('"+approverIDS[i]+"','"+designation[i-1]+"','"+empName+"','"+i+"','"+requestType+"','1','"+emailID[i-1]+"')";
			int k=0;
			k=ad.SqlExecuteUpdate(saveApprover);
			if(k>0)
			{
				authForm.setMessage("Authentication Level Successfully Updated");
			}else{
				authForm.setMessage("Error...When Authentication Updating.Please Check");
			} 
		}
	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("display");
	}
	
	public ActionForward viewDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AuthenticationForm authForm=(AuthenticationForm)form;
		UserDao ad=new UserDao();
		ArrayList approverList=new ArrayList();
		String type = request.getParameter("Type");
		if(type == null){
			type = request.getAttribute("type").toString();
		}
		ArrayList infoList=new ArrayList();
		int approverCountForRequest = 0;
		String requestType = request.getParameter("reqType");
		try{
			if(requestType == null || requestType == ""){
				requestType=authForm.getRequestType();
			}
			authForm.setRequestType(requestType);
			//setRequestType(requestType, authForm);
			if(type.equalsIgnoreCase("Add"))
			{
				setRequestType("", authForm);
				authForm.setMessage("No Approvers are assigned.");
				request.setAttribute("levelPart", "show");
				request.setAttribute("appcount", approverCountForRequest);
				request.setAttribute("addNewRequest", infoList);
				request.setAttribute("requestList", infoList);
				request.setAttribute("editApproverList",infoList);
				return mapping.findForward("viewDetails");
			}
			else if(type.contains("Add New")){
				setRequestType("", authForm);
				request.setAttribute("addNewRequest", "show");
				request.setAttribute("appcount", approverCountForRequest);
				request.setAttribute("levelPart", "");
				request.setAttribute("requestList", infoList);
				request.setAttribute("editApproverList",infoList);
				return mapping.findForward("viewDetails");
			}
			String getRequestTypeDetails="select * from Approvers_Details where Type='"+requestType+"' order by Priority";
			ResultSet rsReqDetails=ad.selectQuery(getRequestTypeDetails);
			while(rsReqDetails.next())
			{
				AuthenticationForm authForm1=new AuthenticationForm();
				authForm1.setRequestType(requestType);
				authForm1.setApproverPriority(Integer.parseInt(rsReqDetails.getString("Priority")));
				authForm1.setApprID(rsReqDetails.getString("Approver_id"));
				authForm1.setEmpDesignation(rsReqDetails.getString("Approver_designation"));
				authForm1.setEmpEmailID(rsReqDetails.getString("emailID"));
				approverCountForRequest++;
				approverList.add(authForm1);
			}
		
			if(approverList.size()==0)
			{
				authForm.setMessage("No Approvers are assigned.");
				request.setAttribute("levelPart", "show");
			}
		
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		if(type.equalsIgnoreCase("View")){
			//setRequestType(requestType, authForm);
			request.setAttribute("viewApproverList", approverList);
			request.setAttribute("editApproverList",infoList);
		}
		else if(type.equalsIgnoreCase("Edit")){
			request.setAttribute("editApproverList", approverList);
			request.setAttribute("viewApproverList", infoList);
		}
		setRequestType(requestType, authForm);
		request.setAttribute("addNewRequest", infoList);
		request.setAttribute("appcount", approverCountForRequest);
		request.setAttribute("infoList", infoList);
		return mapping.findForward("viewDetails");
	}
	
	public ActionForward getDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		AuthenticationForm authForm=(AuthenticationForm)form;
		UserDao ad=new UserDao();
		ArrayList approverList=new ArrayList();
		try{
		String requestType=authForm.getRequestType();
		String empid = request.getParameter("empid");
		String empDesg = "";
		String nameandEmail = "";
		String getEmpDesignation="select * from emp_master where emp_id='"+empid+"'";
		ResultSet rsReqDetails=ad.selectQuery(getEmpDesignation);
		while(rsReqDetails.next())
		{
			//insertion process
			empid = rsReqDetails.getString("emp_id");
			empDesg = rsReqDetails.getString("emp_designation");
			String empName = rsReqDetails.getString("emp_name");
			String empEmail = rsReqDetails.getString("emp_email");
			nameandEmail = empName+"<"+empEmail+">";
			AuthenticationForm authForm1=new AuthenticationForm();
			authForm1.setApprID(empid);
			authForm1.setEmpDesignation(empDesg);
			authForm1.setEmpEmailID(nameandEmail);
			approverList.add(authForm1);
		}
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		request.setAttribute("infoList", approverList);
		request.setAttribute("searchList", "");
		request.setAttribute("levelPart", "");
		request.setAttribute("requestList", "");
		return mapping.findForward("viewApproverInfo");
	}
	
	public ActionForward displaynewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			AuthenticationForm authenticationForm=(AuthenticationForm)form;
			LinkedList requestTypeList = new LinkedList();
			MailInboxAction mAction=new MailInboxAction();
			requestTypeList = setRequestType("", authenticationForm);
			
			int approverCountForRequest = 0;
			UserDao ad=new UserDao();
			LinkedList approversList = new LinkedList();
			int noOfRequest = requestTypeList.size();
			mAction.basicNavigation(request);
			//int requestCount=0;
			int  startCount=0;
			int  endCount=0;
			int rowCount=1;
			try{
				String scount = request.getParameter("sCount");
				if(scount == null){
					startCount = 0;
					endCount=0;
				}
				if(scount != null){
					startCount=Integer.parseInt(scount);
					endCount=Integer.parseInt(request.getParameter("eCount"));
				}
				/*String searchQueryCount="select count(*) from Request_Details";
				ResultSet countrs=ad.selectQuery(searchQueryCount);
				while(countrs.next()) {
						requestCount=Integer.parseInt(countrs.getString(1));
				}*/
				mAction.basicNavigation(request);
				LinkedList naviCount = getLimitAndNavigation(mapping,form,request,noOfRequest,startCount,endCount);
				startCount = Integer.parseInt(naviCount.get(0).toString());
				
				endCount = Integer.parseInt(naviCount.get(1).toString());
				for(int i = 0; i < noOfRequest; i++)
				{
					if(rowCount >= startCount){
						String getRequestTypeDetails="select count(*) from Approvers_Details where Type='"+requestTypeList.get(i).toString()+"'";
						ResultSet rsReqDetails=ad.selectQuery(getRequestTypeDetails);
						while(rsReqDetails.next())
						{
							AuthenticationForm authForm1=new AuthenticationForm();
							authForm1.setApprID(""+rowCount);
							authForm1.setRequestType(requestTypeList.get(i).toString());
							int count = rsReqDetails.getInt(1);
							if(count == 0){
								authForm1.setAddOrView("Add");
							}
							else{
								authForm1.setAddOrView("Edit");
							}
							approverCountForRequest++;
							approversList.add(authForm1);
						}
					}
					if(rowCount == endCount)
					{
						break;
					}
					rowCount++;
				}
			}
			catch (SQLException e) {
				System.out.println("exception @ display new form");
				e.printStackTrace();
			}
			request.setAttribute("appcount", approverCountForRequest);
			request.setAttribute("levelPart", "");
			request.setAttribute("requestList", approversList);
			return mapping.findForward("display");
		}
	
	public ActionForward saveAuthenticationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		AuthenticationForm authForm=(AuthenticationForm)form;
		UserDao ad=new UserDao();
		String requestType=authForm.getRequestType();
		String[] approverIDS=authForm.getApproverID();
		String type=request.getParameter("type");
		String forwardName="";
		try{
		for(int i=0;i<approverIDS.length;i++)
		{
			String empDesignation="";
			String empName="";
			String empEmail ="";
			String locName = "";
			String getEmpDesignation="select * from emp_master where emp_id='"+approverIDS[i]+"'";
			ResultSet rsEmpDesign=ad.selectQuery(getEmpDesignation);
			while(rsEmpDesign.next())
			{
				empDesignation=rsEmpDesign.getString("emp_designation");
				empName=rsEmpDesign.getString("emp_name");
				empEmail = rsEmpDesign.getString("emp_email");
			}
			int empPlant = 1;
			String getLocationName="select plant_id from emp_master where emp_id='"+approverIDS[i]+"'";
			ResultSet rsReqestNumber=ad.selectQuery(getLocationName);
			while(rsReqestNumber.next())
			{
				locName = rsReqestNumber.getString(1);
			} 
			String getMaxPriority="select max(Priority) from Approvers_Details where Type='"+requestType+"'";
			int maxPriority=0;
			ResultSet rsPriorityNumber=ad.selectQuery(getMaxPriority);
			while(rsPriorityNumber.next())
			{
				maxPriority = rsPriorityNumber.getInt(1);
				maxPriority = maxPriority+1;
			}
			int j=0;
			if(type.equalsIgnoreCase("save")){
				String InsertOrUpsql="select count(*) from Approvers_Details where Approver_id='"+approverIDS[i]+"' and Type='"+requestType+"'";
				 int iou = 0;
				 try{
					ResultSet rs=ad.selectQuery(InsertOrUpsql);
					while (rs.next()) {
						iou = rs.getInt(1);
						if(iou > 0){
							String deleteRow = "delete from Approvers_Details where Approver_id='"+approverIDS[i]+"' and Type='"+requestType+"'";
							int dl = ad.SqlExecuteUpdate(deleteRow);
						}
					}
				 }
				 catch(SQLException se){
					se.printStackTrace();
				 }
				 String saveApprover="insert into Approvers_Details(Approver_id,Approver_designation,Employee_Name,Priority,Type,plant,emailID) values('"+approverIDS[i]+"','"+empDesignation+"','"+empName+"','"+maxPriority+"','"+requestType+"','"+locName+"','"+empEmail+"')";
				 j=ad.SqlExecuteUpdate(saveApprover);
				 if(j>0){
					 updatePriorityOrder(requestType, approverIDS[i]);
				 }
			}
			else if(type.equalsIgnoreCase("edit")){
				String saveApprover="update Approvers_Details set Approver_id = '"+approverIDS[i]+"', Approver_designation='"+empDesignation+"',Employee_Name='"+empName+"',plant='"+locName+"',emailID='"+empEmail+"' where Priority='"+(i+1)+"' and Type='"+requestType+"'";
				j=ad.SqlExecuteUpdate(saveApprover);
			}
			if(j>0)
			{
				authForm.setMessage("Authentication Level Successfully Saved For "+requestType);
				displaynewForm(mapping, form, request, response);
				//viewDetails(mapping, form, request, response);
				forwardName="display";
				
			}else{
				authForm.setMessage("Error...When Authentication Saving.Please Check");
				forwardName="display";
			} 
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward(forwardName);
	}
	
	public ActionForward emplyeeIdDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		AuthenticationForm authForm=(AuthenticationForm)form;
		UserDao ad=new UserDao();
		ArrayList approverList=new ArrayList();
		try{
		String requestType=authForm.getRequestType();
		String empid = request.getParameter("empid");
		String empDesg = "";
		String nameandEmail = "";
		String getEmpDesignation="select * from emp_master where emp_id like '%"+empid+"%'";
		ResultSet rsReqDetails=ad.selectQuery(getEmpDesignation);
		while(rsReqDetails.next())
		{
			AuthenticationForm authForm1=new AuthenticationForm();
			authForm1.setApprID(rsReqDetails.getString("emp_id"));
			approverList.add(authForm1);
		}
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		request.setAttribute("searchList", approverList);
		request.setAttribute("levelPart", "");
		request.setAttribute("requestList", "");
		return mapping.findForward("display");
	}
	public ActionForward deleteDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		AuthenticationForm authForm=(AuthenticationForm)form;
		UserDao ad=new UserDao();
		String forwardName="display";
		ArrayList approverList=new ArrayList();
		try{
			String requestType=request.getParameter("reqType");
			String empid = request.getParameter("empid");
			if(empid.equalsIgnoreCase("")){
				String getEmpDesignation="delete from Request_Details where Request_Name='"+requestType+"'";
				int deleted = ad.SqlExecuteUpdate(getEmpDesignation);
				if(deleted>0)
				{
					authForm.setMessage(requestType+" Successfully Deleted");
					getEmpDesignation="delete from Approvers_Details where Type='"+requestType+"'";
					deleted = ad.SqlExecuteUpdate(getEmpDesignation);
					displaynewForm(mapping, form, request, response);
					forwardName="display";
				}else{
					authForm.setMessage("Error...When Approver Deleting.Please Check");
					forwardName="display";
				}
			}
			else{
				String getEmpDesignation="delete from Approvers_Details where Approver_id='"+empid+"' and Type='"+requestType+"'";
				int deleted = ad.SqlExecuteUpdate(getEmpDesignation);
				if(deleted>0)
				{
					authForm.setMessage("Approver for "+requestType+" Successfully Deleted");
					updatePriorityOrder(requestType, empid);
					int countForType=0;
					String count="select count(*) from Approvers_Details where Type='"+requestType+"'";
					ResultSet rsCount = ad.selectQuery(count);
					while(rsCount.next()){
						countForType=rsCount.getInt(1);
					}
					if(countForType > 0){
						request.setAttribute("type", "Edit");
					}
					else{
						request.setAttribute("type", "Add");
					}
					
					viewDetails(mapping, form, request, response);
					forwardName="viewDetails";
				}else{
					authForm.setMessage("Error...When Approver Deleting.Please Check");
					forwardName="display";
				} 
			}
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		return mapping.findForward(forwardName);
	}
	public void updatePriorityOrder(String reqType, String empid){
		UserDao ad=new UserDao();
		try{
		String getMaxPriority="select * from Approvers_Details where Type='"+reqType+"' order by Priority";
		int maxPriority=0;
		ResultSet rsPriorityNumber=ad.selectQuery(getMaxPriority);
		while(rsPriorityNumber.next())
		{
			int rowCount = rsPriorityNumber.getRow();
			String saveApprover="update Approvers_Details set Priority='"+rowCount+"' where Type='"+reqType+"' and Approver_id='"+rsPriorityNumber.getString("Approver_id")+"'";
			int j=ad.SqlExecuteUpdate(saveApprover);
		}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace(); 
		}
	}
	
	public ActionForward addNewRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		AuthenticationForm authForm=(AuthenticationForm)form;
		UserDao ad=new UserDao();
		ArrayList approverList=new ArrayList();
		try{
		String requestType=authForm.getRequestType();
		if(requestType == null){
			requestType = request.getParameter("reqType");
		}
		String reqName = request.getParameter("reqName");
		String getEmpDesignation="insert into Request_Details(Request_Name, Request_Value) values('"+reqName+"','"+reqName+"')";
		int inserted = ad.SqlExecuteUpdate(getEmpDesignation);
		if(inserted>0)
		{
			authForm.setMessage("New Request Successfully Inserted");
			authForm.setRequestType(reqName);
			displaynewForm(mapping, form, request, response);
			//request.setAttribute("type", "view");
			//viewDetails(mapping, form, request, response);
		}else{
			authForm.setMessage("Error...When Approver Inserting.Please Check");
		} 
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		request.setAttribute("addNewRequest", "");
		request.setAttribute("searchList", approverList);
		request.setAttribute("infoList", approverList);
		request.setAttribute("editApproverList", approverList);
		return mapping.findForward("display");
	}
	
	private LinkedList setRequestType(String requestType, AuthenticationForm authForm){
		LinkedList defaultRequestNames=new LinkedList();
		UserDao ad=new UserDao();
		try{
			String getRequestTypeDetails="select * from Request_Details order by Request_Name";
			ResultSet rsReqDetails=ad.selectQuery(getRequestTypeDetails);
			while(rsReqDetails.next())
			{
				defaultRequestNames.add(rsReqDetails.getString("Request_Name"));
			}
			LinkedList requestNames=new LinkedList();
			for(int i = 0; i < defaultRequestNames.size(); i++){
				String selectedRequest = defaultRequestNames.get(i).toString();
				// for before selection (first visit authentication)
				if(requestType.equalsIgnoreCase("")){
					requestType = "-Select-";
					requestNames.addFirst(requestType);
				}
				//for selection view 
				if(requestType.equalsIgnoreCase(selectedRequest)){
					requestNames.addFirst(selectedRequest);
				}
				else{
					requestNames.add(selectedRequest);
				}
			}
			authForm.setRequestNames(requestNames);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace(); 
		}
		return defaultRequestNames;
	}
	
	public ActionForward searchGivenUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		AuthenticationForm authForm=(AuthenticationForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		String sTxt = request.getParameter("searchText");
		authForm.setSearchText(sTxt);
		MailInboxAction mAction = new MailInboxAction();
		LinkedList searchList=new LinkedList();
		try{
			String searchQuery="select * from emp_master where emp_name like '%"+sTxt+"%' or emp_email like '%"+sTxt+"%' or emp_id like'%"+sTxt+"%'";
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
	
	private LinkedList getLimitAndNavigation(ActionMapping mapping, ActionForm form, HttpServletRequest request, int totalCount, int startCount, int endCount){
		AuthenticationForm authForm=(AuthenticationForm)form;
		MailInboxAction mAction = new MailInboxAction();
		LinkedList naviCount = new LinkedList();
		if(startCount == 0)
		{
			if(totalCount == 0)
			{			
				authForm.setMessage("No Records in Selected Filter/Request!");
				request.setAttribute("noRecords","noRecords");
			}
			else if(totalCount<=mAction.MAIL_PER_PAGE)
			{
				startCount=1;
				endCount=totalCount;
				//getRequestList(mapping, form, request, requestType,startAppCount,endAppCount);
				//setAllCount(AppForm,totalAppCount,startAppCount,endAppCount);
			}
			else{
				startCount=1;
				endCount=mAction.MAIL_PER_PAGE;
				//getRequestList(mapping, form, request, requestType,startAppCount,endAppCount);
				//setAllCount(AppForm,totalAppCount,startAppCount,endAppCount);
				mAction.enableNextNavigation(request);
			}
		}
		else{
			String naviType = request.getParameter("fnpl");
			if(naviType.equalsIgnoreCase("next")){
				startCount=(startCount+mAction.MAIL_PER_PAGE);
				endCount=(endCount+mAction.MAIL_PER_PAGE);
				mAction.enablePreviousNavigation(request);
				if(totalCount <= endCount)
				{
					mAction.disableNextNavigation(request);
					endCount = totalCount;
				}
				else
				{
					mAction.enableNextNavigation(request);
				}
				//setAllCount(AppForm,totalAppCount,startAppCount,endAppCount);
			}
			else if(naviType.equalsIgnoreCase("priv"))
			{
				startCount=(startCount-mAction.MAIL_PER_PAGE);
				int ecount=0;
				if(endCount == totalCount)
				{
					int ec = endCount/mAction.MAIL_PER_PAGE;
					ecount = ec;
					endCount=(ecount*mAction.MAIL_PER_PAGE);
				}
				else
					endCount=(endCount-mAction.MAIL_PER_PAGE);
				if(endCount == mAction.MAIL_PER_PAGE)
				{
					mAction.disablePreviousNavigation(request);
					mAction.enableNextNavigation(request);
				}
				else{
					mAction.enablePreviousNavigation(request);
					mAction.enableNextNavigation(request);
				}
				//setAllCount(Form,totalCount,startCount,endCount);
			}
			else if(naviType.equalsIgnoreCase("alast")){
				int ecount=0;
				endCount = totalCount;
				int typeStart = endCount%mAction.MAIL_PER_PAGE;
				if(typeStart == 0){
					int ec = endCount-mAction.MAIL_PER_PAGE;
					ecount = ec;
					startCount=ecount+1;
				}
				else{
					int ec = endCount/mAction.MAIL_PER_PAGE;
					ecount = ec;
					startCount=(ecount*mAction.MAIL_PER_PAGE)+1;
				}
				mAction.enablePreviousNavigation(request);
				mAction.disableNextNavigation(request);

			}
			else if(naviType.equalsIgnoreCase("vfirst")){
				startCount=1;
				endCount = mAction.MAIL_PER_PAGE;
				mAction.disablePreviousNavigation(request);
				mAction.enableNextNavigation(request);
			}
			else{
				if(startCount == 1 && endCount == totalCount){
					mAction.disableNextNavigation(request);
					mAction.disablePreviousNavigation(request);
				}
				else if(startCount == 1 && endCount < totalCount){
					mAction.enableNextNavigation(request);
					mAction.disablePreviousNavigation(request);
				}
				else if(startCount != 1 && endCount == totalCount){
					mAction.enablePreviousNavigation(request);
					mAction.disableNextNavigation(request);
				}
				else if(startCount != 1 && endCount <= totalCount){
					mAction.enablePreviousNavigation(request);
					mAction.enableNextNavigation(request);
				}
			}
		}
		authForm.setTotalRequestCount(totalCount);
		authForm.setStartRequestCount(startCount);
		authForm.setEndRequestCount(endCount);
		naviCount.add(0,startCount);
		naviCount.add(1,endCount);
		return naviCount;
	}
}
