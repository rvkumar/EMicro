package com.microlabs.main.db;

import java.sql.ResultSet;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.ConferenceForm;
import com.microlabs.ess.form.VCForm;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class ApprovalsList {

	public LinkedList getApprovalsList(String reqType,String type, HttpServletRequest request,ActionForm form){
		ApprovalsForm approvalsForm = (ApprovalsForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		LinkedList masterList=new LinkedList();
		 String getConfRecords="";
	if(reqType.equalsIgnoreCase("VC Booking"))
	 {
		if(type.equalsIgnoreCase("Pending")){
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
				"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
				+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, VCRoom_Details as conf,Location loc " +
				"where all_R.Pending_Approver='"+user.getEmployeeNo()+"' and all_R.Req_Status='Pending' and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
				"all_R.Req_Type='VC Booking') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc";			
			}
		 if(type.equalsIgnoreCase("Approved")){
			 
			 request.setAttribute("CompletedButton", "CompletedButton");
			 
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
			"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
			+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, VCRoom_Details as conf,Location loc " +
			"where all_R.Last_Approver='"+user.getEmployeeNo()+"' and all_R.Req_Status='Approved' and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
			"all_R.Req_Type='VC Booking') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc"; 
		 }
		 if(type.equalsIgnoreCase("Rejected")){
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
			"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
			+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, VCRoom_Details as conf,Location loc " +
			"where all_R.Last_Approver='"+user.getEmployeeNo()+"' and all_R.Req_Status='Rejected' and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
			"all_R.Req_Type='VC Booking') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc"; 
		 }
		 if(type.equalsIgnoreCase("Completed")){
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
			"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
			+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, VCRoom_Details as conf,Location loc " +
			"where all_R.Last_Approver='"+user.getEmployeeNo()+"' and all_R.Req_Status='Completed' and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
			"all_R.Req_Type='VC Booking') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc";
		 }
		 if(type.equalsIgnoreCase("All")){
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
				"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
				+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, VCRoom_Details as conf,Location loc " +
				"where (all_R.Pending_Approver='"+user.getEmployeeNo()+"' or all_R.Last_Approver='"+user.getEmployeeNo()+"')  and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
				"all_R.Req_Type='VC Booking') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc";			
			}
		 ResultSet rsConfList=ad.selectQuery(getConfRecords);
		 try{
		 while(rsConfList.next()){
			 VCForm c=new VCForm();
		     c.setReqNo(rsConfList.getString("Req_Id"));
		     c.setRequestername(rsConfList.getString("EMP_FULLNAME"));
		     c.setSubmitDate(EMicroUtils.reqDisplay(rsConfList.getString("Submit_Date")));
		     c.setLocationId(rsConfList.getString("LOCATION_CODE"));
		     c.setFloor(rsConfList.getString("Floor"));
		     c.setRoomName(rsConfList.getString("Conf_Room"));
		     c.setFromDate(EMicroUtils.display(rsConfList.getDate(("From_Date")))+" "+rsConfList.getString("From_Time"));
		     c.setToDate(EMicroUtils.display(rsConfList.getDate("To_Date"))+" "+rsConfList.getString("To_Time"));
		     c.setStatus(rsConfList.getString("Req_Status"));
		     c.setLastApprover(rsConfList.getString("Last_approver"));
		     c.setPendingApprover(rsConfList.getString("Pending_approver"));
		     masterList.add(c);
		 }
	 }catch(Exception e){
		 e.printStackTrace();
	 }
		
	}
	if(reqType.equalsIgnoreCase("Conference"))
	 {
		
		if(type.equalsIgnoreCase("Pending")){
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
				"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
				+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, ConferenceRoom_Details as conf,Location loc " +
				"where all_R.Pending_Approver='"+user.getEmployeeNo()+"' and all_R.Req_Status='Pending' and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
				"all_R.Req_Type='Conference') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc";			
			}
		 if(type.equalsIgnoreCase("Approved")){
			 
			 request.setAttribute("CompletedButton", "CompletedButton");
			 
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
			"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
			+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, ConferenceRoom_Details as conf,Location loc " +
			"where all_R.Last_Approver='"+user.getEmployeeNo()+"' and all_R.Req_Status='Approved' and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
			"all_R.Req_Type='Conference') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc"; 
		 }
		 if(type.equalsIgnoreCase("Rejected")){
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
			"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
			+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, ConferenceRoom_Details as conf,Location loc " +
			"where all_R.Last_Approver='"+user.getEmployeeNo()+"' and all_R.Req_Status='Rejected' and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
			"all_R.Req_Type='Conference') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc"; 
		 }
		 if(type.equalsIgnoreCase("Completed")){
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
			"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
			+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, ConferenceRoom_Details as conf,Location loc " +
			"where all_R.Last_Approver='"+user.getEmployeeNo()+"' and all_R.Req_Status='Completed' and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
			"all_R.Req_Type='Conference') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc";
		 }
		 if(type.equalsIgnoreCase("All")){
			 getConfRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  all_R.Req_Id desc) AS  RowNum,all_R.Req_Id,all_R.Requester_Name," +
				"emp.EMP_FULLNAME,all_R.Req_Status,conf.Submit_Date,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,"
				+"conf.To_Time,all_R.type,conf.Last_approver,conf.Pending_approver from  All_Request as all_R,emp_official_info as  emp, ConferenceRoom_Details as conf,Location loc " +
				"where (all_R.Pending_Approver='"+user.getEmployeeNo()+"' or all_R.Last_Approver='"+user.getEmployeeNo()+"')  and all_R.Req_Id=conf.Reqest_No and  conf.Loc_Id=loc.LOCID and  all_R.Requester_Name=emp.PERNR and " +
				"all_R.Req_Type='Conference') as  sub Where   sub.RowNum between "+approvalsForm.getStartRecord()+" and "+approvalsForm.getEndRecord()+" order by Req_Id desc";			
			}
		 ResultSet rsConfList=ad.selectQuery(getConfRecords);
		 try{
		 while(rsConfList.next()){
			 ConferenceForm c=new ConferenceForm();
		     c.setReqNo(rsConfList.getString("Req_Id"));
		     c.setRequestername(rsConfList.getString("EMP_FULLNAME"));
		     c.setSubmitDate(EMicroUtils.reqDisplay(rsConfList.getString("Submit_Date")));
		     c.setLocationId(rsConfList.getString("LOCATION_CODE"));
		     c.setFloor(rsConfList.getString("Floor"));
		     c.setRoomName(rsConfList.getString("Conf_Room"));
		     c.setFromDate(EMicroUtils.display(rsConfList.getDate(("From_Date")))+" "+rsConfList.getString("From_Time"));
		     c.setToDate(EMicroUtils.display(rsConfList.getDate("To_Date"))+" "+rsConfList.getString("To_Time"));
		     c.setStatus(rsConfList.getString("Req_Status"));
		     c.setLastApprover(rsConfList.getString("Last_approver"));
		     c.setPendingApprover(rsConfList.getString("Pending_approver"));
		     masterList.add(c);
		 }
		
	 
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	
	
		
}
	 return masterList;
}
}
