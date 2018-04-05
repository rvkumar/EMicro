package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.form.ESSApproverForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;
public class ESSApproverAction extends DispatchAction{
	NewsandMediaDao ad=new NewsandMediaDao();
	public ActionForward searchEmpRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ESSApproverForm masterForm=(ESSApproverForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		
		int id=user.getGroupId();
		String locations = "";
		String aa1="select * from user_group where id='"+id+"'";
		ResultSet cc1=ad.selectQuery(aa1);
		try {
			if(cc1.next())
			{
				String cmpcode1 = cc1.getString("Attendance_Loc");

				
				if(cmpcode1.contains(","))
				{
				 String[] cmp = cmpcode1.split(",");

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
		
		
		String searchKeyword=masterForm.getSearchKeyword();
		try{
			LinkedList 	empList=new LinkedList();
			String getEmpList="Select ess.employeeNumber,emp.EMP_FULLNAME,ess.essType," +
			"dept.DPTSTXT from ESS_Approvers as ess,emp_official_info as emp,DEPARTMENT as dept  where emp.PERNR=ess.employeeNumber and emp.DPTID=dept.DPTID and emp.locid in ("+locations+") " +
			" and (ess.employeeNumber like '%"+searchKeyword+"%' or emp.EMP_FULLNAME like '%"+searchKeyword+"%' or dept.DPTSTXT like '%"+searchKeyword+"%' or ess.essType like '%"+searchKeyword+"%') group by employeeNumber,essType,emp.EMP_FULLNAME,dept.DPTSTXT  ";
			ResultSet rsempList=ad.selectQuery(getEmpList);
			while(rsempList.next()){
				ESSApproverForm emp=new ESSApproverForm();
				emp.setEmployeeNo(rsempList.getString("employeeNumber"));
				emp.setEmpname(rsempList.getString("EMP_FULLNAME"));
				emp.setDepartment(rsempList.getString("DPTSTXT"));
				emp.setEssType(rsempList.getString("essType"));
				empList.add(emp);
			}	
			request.setAttribute("empList", empList);
		}catch (Exception e) {
		e.printStackTrace();
		}
		return mapping.findForward("employyList");
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ESSApproverForm masterForm=(ESSApproverForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		int id=user.getGroupId();
		String locations = "";
		String aa1="select * from user_group where id='"+id+"'";
		ResultSet cc1=ad.selectQuery(aa1);
		try {
			if(cc1.next())
			{
				String cmpcode1 = cc1.getString("Attendance_Loc");


				if(cmpcode1.contains(","))
				{
				 String[] cmp = cmpcode1.split(",");

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
		
		try{
			int totalRecords=masterForm.getTotalRecords();//21
			int startRecord=masterForm.getStartRecord();//11
			int endRecord=masterForm.getEndRecord();	
			
			
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 masterForm.setTotalRecords(totalRecords);
			 masterForm.setStartRecord(startRecord);
			 masterForm.setEndRecord(totalRecords);
			 LinkedList 	empList=new LinkedList();
				String getEmpList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ess.employeeNumber) AS RowNum,ess.employeeNumber,emp.EMP_FULLNAME,ess.essType," +
				"dept.DPTSTXT from ESS_Approvers as ess,emp_official_info as emp,DEPARTMENT as dept  where emp.PERNR=ess.employeeNumber and emp.DPTID=dept.DPTID and emp.locid in ("+locations+")" +
				" group by employeeNumber,essType,emp.EMP_FULLNAME,dept.DPTSTXT) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rsempList=ad.selectQuery(getEmpList);
				while(rsempList.next()){
					ESSApproverForm emp=new ESSApproverForm();
					emp.setEmployeeNo(rsempList.getString("employeeNumber"));
					emp.setEmpname(rsempList.getString("EMP_FULLNAME"));
					emp.setDepartment(rsempList.getString("DPTSTXT"));
					emp.setEssType(rsempList.getString("essType"));
					empList.add(emp);
				}	
				request.setAttribute("empList", empList);
				 request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton");
					if(totalRecords<10)
					{
						
						request.setAttribute("previousButton", "");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
					request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("employyList");
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		ESSApproverForm masterForm=(ESSApproverForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		int id=user.getGroupId();
		String locations = "";
		String aa1="select * from user_group where id='"+id+"'";
		ResultSet cc1=ad.selectQuery(aa1);
		try {
			if(cc1.next())
			{
				String cmpcode1 = cc1.getString("Attendance_Loc");


				if(cmpcode1.contains(","))
				{
				 String[] cmp = cmpcode1.split(",");

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
		
		try{
			int totalRecords=masterForm.getTotalRecords();//21
			int endRecord=masterForm.getStartRecord()-1;//20
			int startRecord=masterForm.getStartRecord()-10;//11
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(1);
			masterForm.setEndRecord(10);
			LinkedList 	empList=new LinkedList();
			String getEmpList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ess.employeeNumber) AS RowNum,ess.employeeNumber,emp.EMP_FULLNAME,ess.essType," +
			"dept.DPTSTXT from ESS_Approvers as ess,emp_official_info as emp,DEPARTMENT as dept  where emp.PERNR=ess.employeeNumber and emp.DPTID=dept.DPTID and emp.locid in ("+locations+")" +
			" group by employeeNumber,essType,emp.EMP_FULLNAME,dept.DPTSTXT) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			ResultSet rsempList=ad.selectQuery(getEmpList);
			while(rsempList.next()){
				ESSApproverForm emp=new ESSApproverForm();
				emp.setEmployeeNo(rsempList.getString("employeeNumber"));
				emp.setEmpname(rsempList.getString("EMP_FULLNAME"));
				emp.setDepartment(rsempList.getString("DPTSTXT"));
				emp.setEssType(rsempList.getString("essType"));
				empList.add(emp);
			}	
			request.setAttribute("empList", empList);
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(startRecord);
			masterForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					if(startRecord!=1)
					request.setAttribute("previousButton", "previousButton");
					request.setAttribute("displayRecordNo", "displayRecordNo");
					if(empList.size()<10)
					{
						masterForm.setStartRecord(1);
						request.setAttribute("previousButton", "");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("employyList");
	}
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		ESSApproverForm masterForm=(ESSApproverForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		int id=user.getGroupId();
		String locations = "";
		String aa1="select * from user_group where id='"+id+"'";
		ResultSet cc1=ad.selectQuery(aa1);
		try {
			if(cc1.next())
			{
				String cmpcode1 = cc1.getString("Attendance_Loc");


				if(cmpcode1.contains(","))
				{
				 String[] cmp = cmpcode1.split(",");

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
		
		try{
			int totalRecords=masterForm.getTotalRecords();//21
			int startRecord=masterForm.getStartRecord();//11
			int endRecord=masterForm.getEndRecord();	
			
			if(totalRecords>endRecord)
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
				LinkedList 	empList=new LinkedList();
				String getEmpList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ess.employeeNumber) AS RowNum,ess.employeeNumber,emp.EMP_FULLNAME,ess.essType," +
				"dept.DPTSTXT from ESS_Approvers as ess,emp_official_info as emp,DEPARTMENT as dept  where emp.PERNR=ess.employeeNumber and emp.DPTID=dept.DPTID and emp.locid in ("+locations+")" +
				" group by employeeNumber,essType,emp.EMP_FULLNAME,dept.DPTSTXT) as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rsempList=ad.selectQuery(getEmpList);
				while(rsempList.next()){
					ESSApproverForm emp=new ESSApproverForm();
					emp.setEmployeeNo(rsempList.getString("employeeNumber"));
					emp.setEmpname(rsempList.getString("EMP_FULLNAME"));
					emp.setDepartment(rsempList.getString("DPTSTXT"));
					emp.setEssType(rsempList.getString("essType"));
					empList.add(emp);
				}	
				request.setAttribute("empList", empList);
				if(empList.size()!=0)
				{
					masterForm.setTotalRecords(totalRecords);
					masterForm.setStartRecord(startRecord);
					masterForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					masterForm.setTotalRecords(totalRecords);
					masterForm.setStartRecord(start);
					masterForm.setEndRecord(end);
					
				}
			 if(empList.size()<10)
			 {
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(startRecord+empList.size()-1);
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
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("employyList");
	}

	public ActionForward modifyEmpApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ESSApproverForm masterForm=(ESSApproverForm)form;
		
		 String approver1=masterForm.getApprover1();
		 String approver2=masterForm.getApprover2();
		 String approver3=masterForm.getApprover3();
		 String parllApp11=masterForm.getParllelAppr11();
		  String parllApp12=masterForm.getParllelAppr12();
		  String parllApp21=masterForm.getParllelAppr21();
		  String parllApp22=masterForm.getParllelAppr22();
		  String parllApp31=masterForm.getParllelAppr31();
		  String parllApp32=masterForm.getParllelAppr32();
		  int i=0;
		  String sequentialType=masterForm.getSequentialType();
		  String parallelType=masterForm.getParallelType();
		  String reqType="";
		  if(sequentialType!=null && sequentialType.equalsIgnoreCase("On"))
		  {
			  reqType="sequential";
		  }
		  if(parallelType!=null && parallelType.equalsIgnoreCase("On"))
		  {
			  reqType="parallel";
		  }
		  
		  HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
		  
		  
		  String deletePreviousDetails1="Insert into  ess_approvers_log(essType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2,reqType,created_by)"
		  		+ "  select essType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2,reqType,'"+user.getEmployeeNo()+"' from ess_approvers "
		  		+ "  where employeeNumber='"+masterForm.getEmployeeNo()+"' and essType='"+masterForm.getEssType()+"'";
		  ad.SqlExecuteUpdate(deletePreviousDetails1);
		  
		  
		  String subst="select * from ESS_Approvers where employeeNumber='"+masterForm.getEmployeeNo()+"' order by priority";
		  ResultSet rs =ad.selectQuery(subst);
		  try {
			while(rs.next())
			  {
				
				if(rs.getString("priority").equalsIgnoreCase("1"))
				{	
				int ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getApprover1()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("ApproverId")+"' and req_status= 'Pending' " );
				
				 ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getParllelAppr11()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("Parallel_Approver1")+"' and req_status= 'Pending' ");
				
				 ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getParllelAppr12()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("Parallel_Approver2")+"' and req_status= 'Pending' ");
				}
				
				if(rs.getString("priority").equalsIgnoreCase("2"))
				{	
				int ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getApprover2()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("ApproverId")+"' and req_status= 'Pending' ");
				
				 ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getParllelAppr21()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("Parallel_Approver1")+"' and req_status= 'Pending' ");
				
				 ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getParllelAppr22()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("Parallel_Approver2")+"' and req_status= 'Pending' ");
				}
				
				
				if(rs.getString("priority").equalsIgnoreCase("3"))
				{	
				int ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getApprover3()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("ApproverId")+"' and req_status= 'Pending' ");
				
				 ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getParllelAppr31()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("Parallel_Approver1")+"' and req_status= 'Pending' ");
				
				 ik = ad.SqlExecuteUpdate("update all_request set Pending_Approver ='"+masterForm.getParllelAppr32()+"' "
						+ " where Requester_Name='"+masterForm.getEmployeeNo()+"' and Pending_Approver='"+rs.getString("Parallel_Approver2")+"' and req_status= 'Pending' ");
				}
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  
		  String deletePreviousDetails="delete from ESS_Approvers where employeeNumber='"+masterForm.getEmployeeNo()+"' and essType='"+masterForm.getEssType()+"'";
		  ad.SqlExecuteUpdate(deletePreviousDetails);
		  
		  if(!(approver1.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into ESS_Approvers(essType,reqType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2)" +
			  " values('"+masterForm.getEssType()+"','"+reqType+"','"+masterForm.getEmployeeNo()+"','"+masterForm.getPriority1()+"','"+masterForm.getApprover1()+"','"+masterForm.getParllelAppr11()+"','"+masterForm.getParllelAppr12()+"')";	
			  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }	  
		  if(!(approver2.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into ESS_Approvers(essType,reqType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2)" +
			  " values('"+masterForm.getEssType()+"','"+reqType+"','"+masterForm.getEmployeeNo()+"','"+masterForm.getPriority2()+"','"+masterForm.getApprover2()+"','"+masterForm.getParllelAppr21()+"','"+masterForm.getParllelAppr22()+"')";	
			  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }
		  if(!(approver3.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into ESS_Approvers(essType,reqType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2)" +
			  " values('"+masterForm.getEssType()+"','"+reqType+"','"+masterForm.getEmployeeNo()+"','"+masterForm.getPriority3()+"','"+masterForm.getApprover3()+"','"+masterForm.getParllelAppr31()+"','"+masterForm.getParllelAppr32()+"')";	
			  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }	
		  if(i>0)
			{
				masterForm.setMessage("Data Updated Successfully");
			}else{
				masterForm.setMessage("Error..Data Not Updated");
			}
		  
		  
		  
			request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("addApprover");
	}
	
	public ActionForward editEmpDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ESSApproverForm masterForm=(ESSApproverForm)form;
		
		String empNo=request.getParameter("EmpNo");
		String essType=request.getParameter("EssType");
		String getEmpApprovers="select * from ESS_Approvers where employeeNumber='"+empNo+"' and essType='"+essType+"'";
		ResultSet rs=ad.selectQuery(getEmpApprovers);
		try{
		while(rs.next()){
			String reqType=rs.getString("reqType");
			masterForm.setEssType(rs.getString("essType"));
			masterForm.setEmployeeNo(rs.getString("employeeNumber"));
			
			if(reqType.equalsIgnoreCase("sequential")){
				int priority=rs.getInt("Priority");
				if(priority==1)
					masterForm.setApprover1(rs.getString("ApproverId"));
				if(priority==2)
					masterForm.setApprover2(rs.getString("ApproverId"));
				if(priority==3)
					masterForm.setApprover3(rs.getString("ApproverId"));
				
				masterForm.setSequentialType("On");
			}
			if(reqType.equalsIgnoreCase("parallel")){
				masterForm.setApprover1(rs.getString("ApproverId"));
				masterForm.setParllelAppr11(rs.getString("Parallel_Approver1"));
				masterForm.setParllelAppr12(rs.getString("Parallel_Approver2"));
				masterForm.setParallelType("On");
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("addApprover");
	}
	
	
	
	public ActionForward addApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ESSApproverForm masterForm=(ESSApproverForm)form;
		
		 String approver1=masterForm.getApprover1();
		 String approver2=masterForm.getApprover2();
		 String approver3=masterForm.getApprover3();
		 String parllApp11=masterForm.getParllelAppr11();
		  String parllApp12=masterForm.getParllelAppr12();
		  String parllApp21=masterForm.getParllelAppr21();
		  String parllApp22=masterForm.getParllelAppr22();
		  String parllApp31=masterForm.getParllelAppr31();
		  String parllApp32=masterForm.getParllelAppr32();
		  int i=0;
		  String sequentialType=masterForm.getSequentialType();
		  String parallelType=masterForm.getParallelType();
		  String reqType="";
		  if(sequentialType!=null && sequentialType.equalsIgnoreCase("On"))
		  {
			  reqType="sequential";
		  }
		  if(parallelType!=null && parallelType.equalsIgnoreCase("On"))
		  {
			  reqType="parallel";
		  }
		  
		  String subst="select * from ESS_Approvers where employeeNumber='"+masterForm.getEmployeeNo()+"'";
		  ResultSet rs =ad.selectQuery(subst);
		  try {
			if(rs.next())
			  {
			
				employyList(mapping, masterForm, request, response);
				masterForm.setMessage("Approvers already available for the Employee ..Kindly Check");  
				return mapping.findForward("employyList");
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");

		  
	
		  
		 if(!(approver1.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into ESS_Approvers(essType,reqType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2)" +
			  " values('"+masterForm.getEssType()+"','"+reqType+"','"+masterForm.getEmployeeNo()+"','"+masterForm.getPriority1()+"','"+masterForm.getApprover1()+"','"+masterForm.getParllelAppr11()+"','"+masterForm.getParllelAppr12()+"')";	
			  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }	  
		  if(!(approver2.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into ESS_Approvers(essType,reqType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2)" +
			  " values('"+masterForm.getEssType()+"','"+reqType+"','"+masterForm.getEmployeeNo()+"','"+masterForm.getPriority2()+"','"+masterForm.getApprover2()+"','"+masterForm.getParllelAppr21()+"','"+masterForm.getParllelAppr22()+"')";	
			  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }
		  if(!(approver3.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into ESS_Approvers(essType,reqType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2)" +
			  " values('"+masterForm.getEssType()+"','"+reqType+"','"+masterForm.getEmployeeNo()+"','"+masterForm.getPriority3()+"','"+masterForm.getApprover3()+"','"+masterForm.getParllelAppr31()+"','"+masterForm.getParllelAppr32()+"')";	
			  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }	
		if(i>0)
		{
			  String deletePreviousDetails1="Insert into  ess_approvers_log(essType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2,reqType,created_by)"
				  		+ "  select essType,employeeNumber,Priority,ApproverId,Parallel_Approver1,Parallel_Approver2,reqType,'"+user.getEmployeeNo()+"' from ess_approvers "
				  		+ "  where employeeNumber='"+masterForm.getEmployeeNo()+"' and essType='"+masterForm.getEssType()+"'";
				  ad.SqlExecuteUpdate(deletePreviousDetails1);
			
			masterForm.setMessage("Data Saved Successfully");

            masterForm.setEssType("");
            masterForm.setEmployeeNo("");
            masterForm.setSequentialType("");
            masterForm.setParallelType("");
            masterForm.setApprover1("");
            masterForm.setApprover2("");
            masterForm.setApprover3("");
            masterForm.setParllelAppr11("");
            masterForm.setParllelAppr12("");
		}else{
			masterForm.setMessage("Error..Data Not Saved");
		}
	
		
		request.setAttribute("saveButton", "saveButton");
		return mapping.findForward("addApprover");
	}
	
	public ActionForward newApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("saveButton", "saveButton");
		return mapping.findForward("addApprover");
	}
	public ActionForward employyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ESSApproverForm masterForm=(ESSApproverForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		
		int id=user.getGroupId();
		String locations = "";
		String aa1="select * from user_group where id='"+id+"'";
		ResultSet cc1=ad.selectQuery(aa1);
		try {
			if(cc1.next())
			{
				String cmpcode1 = cc1.getString("Attendance_Loc");

				
				if(cmpcode1.contains(","))
				{
				 String[] cmp = cmpcode1.split(",");

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
		
		
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		  try{
		String getCount="select ess.employeeNumber,ess.essType from ESS_Approvers as ess group by employeeNumber,essType";
		ResultSet rsCount=ad.selectQuery(getCount);
		while(rsCount.next()){
			totalRecords=totalRecords+1;
		}
			if(totalRecords>=10)
			  {
				masterForm.setTotalRecords(totalRecords);
			  startRecord=1;
			  endRecord=10;
			  masterForm.setStartRecord(1);
			  masterForm.setEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords;
				  masterForm.setTotalRecords(totalRecords);
				  masterForm.setStartRecord(1);
				  masterForm.setEndRecord(totalRecords); 
			  }
		LinkedList 	empList=new LinkedList();
		String getEmpList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ess.employeeNumber) AS RowNum,ess.employeeNumber,emp.EMP_FULLNAME,ess.essType," +
		"dept.DPTSTXT from ESS_Approvers as ess,emp_official_info as emp,DEPARTMENT as dept  where emp.PERNR=ess.employeeNumber and emp.DPTID=dept.DPTID and emp.locid in ("+locations+")" +
		" group by employeeNumber,essType,emp.EMP_FULLNAME,dept.DPTSTXT) as  sub Where  sub.RowNum between 1 and 10   ";
		ResultSet rsempList=ad.selectQuery(getEmpList);
		while(rsempList.next()){
			ESSApproverForm emp=new ESSApproverForm();
			emp.setEmployeeNo(rsempList.getString("employeeNumber"));
			emp.setEmpname(rsempList.getString("EMP_FULLNAME"));
			emp.setDepartment(rsempList.getString("DPTSTXT"));
			emp.setEssType(rsempList.getString("essType"));
			
			empList.add(emp);
		}
		if(empList.size()==0){
			request.setAttribute("noRecords", "noRecords");
			masterForm.setMessage1("No Records Found");
			masterForm.setMessage2("");
		}
		request.setAttribute("empList", empList);
		request.setAttribute("disablePreviousButton","disablePreviousButton");
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		
		return mapping.findForward("employyList");
	}
	
	public ActionForward searchForApprovers(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	ESSApproverForm userRightsForm=(ESSApproverForm)form;
	
	String sTxt = request.getParameter("searchText");
	String reqFieldName=request.getParameter("reqFieldName");
	
	userRightsForm.setSearchText(sTxt);
	MailInboxAction mAction = new MailInboxAction();
	LinkedList searchList=new LinkedList();
	try{
		String searchQuery="select * from emp_official_info where EMP_FULLNAME like '%"+sTxt+"%' or EMAIL_ID like '%"+sTxt+"%' or PERNR like'%"+sTxt+"%'";
		ResultSet rs=ad.selectQuery(searchQuery);
	 int i=1;
		while(rs.next()) {
			
			ESSApproverForm emp=new ESSApproverForm();
			emp.setEmp(rs.getString("EMP_FULLNAME")+"-"+rs.getString("PERNR"));
			emp.setReqFieldName(reqFieldName);
		   searchList.add(emp);
		  if(i==4)
		  {
			  break;
		  }
		i++;
		}
		
	}
	
	catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
	
	request.setAttribute("SearchUserDetails", searchList);
	
	return mapping.findForward("searchApprovers");
	}




public ActionForward searchForApproversleave(ActionMapping mapping, ActionForm form, 
		HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		ESSApproverForm userRightsForm=(ESSApproverForm)form;
		
		String sTxt = request.getParameter("searchText");
		String reqFieldName=request.getParameter("reqFieldName");
		
		int id=user.getGroupId();
		String locations = "";
		String aa1="select * from user_group where id='"+id+"'";
		ResultSet cc1=ad.selectQuery(aa1);
		try {
			if(cc1.next())
			{
				String cmpcode1 = cc1.getString("Attendance_Loc");

				
				if(cmpcode1.contains(","))
				{
				 String[] cmp = cmpcode1.split(",");

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
		
		
		userRightsForm.setSearchText(sTxt);
		MailInboxAction mAction = new MailInboxAction();
		LinkedList searchList=new LinkedList();
		try{
			String searchQuery="select * from emp_official_info where "
					+ " emp_official_info.locid in ("+locations+")  and (EMP_FULLNAME like '%"+sTxt+"%' or EMAIL_ID like '%"+sTxt+"%' or PERNR like'%"+sTxt+"%') ";
			ResultSet rs=ad.selectQuery(searchQuery);
		 int i=1;
			while(rs.next()) {
				
				ESSApproverForm emp=new ESSApproverForm();
				emp.setEmp(rs.getString("EMP_FULLNAME")+"-"+rs.getString("PERNR"));
				emp.setReqFieldName(reqFieldName);
			   searchList.add(emp);
			  if(i==4)
			  {
				  break;
			  }
			i++;
			}
			
		}

			catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
			
			request.setAttribute("SearchUserDetails", searchList);
			
			return mapping.findForward("searchApprovers");
			}
			}
