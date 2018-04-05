package com.microlabs.hr.action;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.microlabs.admin.dao.UserDao;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.hr.form.HRManPowerMatrixForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.utilities.UserInfo;
public class HRManPowerMatrixAction extends DispatchAction{
				
	public ActionForward newManPowerMatrixForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			HRManPowerMatrixForm hrMPMForm=(HRManPowerMatrixForm)form;	
		    hrMPMForm.setMessage("");
			HRDao hd=new HRDao();
			HttpSession session=request.getSession();
			ArrayList idList=new ArrayList();
			
			UserInfo user=(UserInfo)session.getAttribute("user");
			int user_Id = user.getId();
			int plantId = Integer.parseInt(user.getPlantId());
			String getLocation="select * from Location where LOCID="+plantId;
			ResultSet locRS=hd.selectQuery(getLocation);
			while(locRS.next()){
				hrMPMForm.setLocation(locRS.getString("LOCNAME"));
			}
			request.setAttribute("manordepart", "depart");
		}catch (Exception e) {
			System.out.println("exception @ man power matrix");
			e.printStackTrace();
		}
		return mapping.findForward("newManPowerMatrixForm");
	}
	
	//display the manpower 
	
	public ActionForward displayManPower(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("dsplay ManPower");
		HRManPowerMatrixForm hrMPMForm=(HRManPowerMatrixForm) form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		String module = request.getParameter("id");
		System.out.println(" module - "+module);
		System.out.println(" user selection loc "+hrMPMForm.getLocation());
		System.out.println(" sub link of - "+request.getParameter("subLink"));
		System.out.println(" link name - "+request.getParameter("sId"));
		int userSelectedlocID=Integer.parseInt(user.getPlantId());
		if(hrMPMForm.getLocation() != null)
		{
			userSelectedlocID = Integer.parseInt(hrMPMForm.getLocation());
		}
		String locName = "";
		try
		{
			LinkedList list = new LinkedList();
			LinkedList locationID=new LinkedList();
			LinkedList locationName=new LinkedList();
			UserDao adMPM=new UserDao();
			String getLocations="select * from Location";
			ResultSet rsGetLocation=adMPM.selectQuery(getLocations);
			while(rsGetLocation.next()){
				if(userSelectedlocID == rsGetLocation.getInt("LOCID")){
					System.out.println("if "+rsGetLocation.getInt("LOCID"));
					locationID.addFirst(rsGetLocation.getInt("LOCID"));
					locName=rsGetLocation.getString("LOCNAME");
					locationName.addFirst(locName);
				}
				else{
					System.out.println("else "+rsGetLocation.getInt("LOCID"));
				locationID.add(rsGetLocation.getInt("LOCID"));
				locationName.add(rsGetLocation.getString("LOCNAME"));
				}
			}
			hrMPMForm.setLocationId(locationID);
			hrMPMForm.setLocationName(locationName);
			
			int userId = user.getId();
			String userDepart = "";
			if(hrMPMForm.getUserDepart() == null){
				ResultSet rs1 = adMPM.selectQuery("select dept_name from emp_master where emp_id="+user.getUserName()+"");
				while (rs1.next()) {
					userDepart = rs1.getString("dept_name");
				}
			}
			else
			{
				userDepart = hrMPMForm.getUserDepart();
			}
			
			LinkedList departNames=new LinkedList();
			String getDeparts="select dept_name from emp_master group by dept_name";
			ResultSet departRS=adMPM.selectQuery(getDeparts);
			while(departRS.next()){
				if(userDepart.equalsIgnoreCase(departRS.getString("dept_name"))){
					System.out.println("if "+departRS.getString("dept_name"));
					departNames.addFirst(userDepart);
				}
				else{
					System.out.println("else "+departRS.getString("dept_name"));
					departNames.add(departRS.getString("dept_name"));
				}
			}
			hrMPMForm.setDepartmentName(departNames);
			
			ResultSet rs = adMPM.selectQuery("select * from Man_Power_Matrix where Plant='"+locName+"' and Department='"+userDepart+"'");
			
			HRManPowerMatrixForm manpowerForm=null;
			while (rs.next()) {
                String locationName1="";
                manpowerForm=new HRManPowerMatrixForm();
                /*String getLocationHolidays="select * from Location where LOCID='"+rs.getString("Plant")+"'";
				ResultSet rs2=adMPM.selectQuery(getLocationHolidays);
				while(rs2.next())
				{
					locationName1=rs2.getString("LOCNAME");
				}*/
                locationName1 = locName;
				manpowerForm.setLocation(locationName1);
				manpowerForm.setRecuritmentID(rs.getString("Req_Id"));
				manpowerForm.setDepartment(rs.getString("Department"));
				manpowerForm.setJobTitle(rs.getString("Job_Title"));
				manpowerForm.setDepartmentInvestment(rs.getString("Money_Investment"));
				int reqMP = rs.getInt("Req_Man_Power");
				String status = rs.getString("Status");
				if(reqMP == 0 && status.equalsIgnoreCase("Approved")){
					manpowerForm.setReqEmp(""+reqMP);
					manpowerForm.setRecLink("hrManPower.do?method=addManPowerForm&sId=ManPower Matrix&id=HR&recId="+rs.getString("Req_Id"));
				}
				else if(reqMP > 0 && status.equalsIgnoreCase("Approved")){
					manpowerForm.setReqEmp(""+reqMP);
					manpowerForm.setRecLink("hrRecruitment.do?method=newHrRecrutmentRequestForm&sId=Recruitment Request&id=HR");
				}
				else if(reqMP > 0 && status.equalsIgnoreCase("Pending")){
					manpowerForm.setReqEmp("Waiting For Approve");
					manpowerForm.setRecLink("Waiting For Approve");
				}
				
				
				list.add(manpowerForm);
			}
			request.setAttribute("header", "ManPower List for Department");
			request.setAttribute("listDetails", list);
			
	} catch (Exception e) {
		e.printStackTrace();
	}
	String result=(String)sesion.getAttribute("result");
	System.out.println("result="+result);
	if(result==null||result==" ")
	sesion.setAttribute("result"," ");
	
	
	if(request.getParameter("id")!=null){
	
	request.setAttribute("MenuIcon", request.getParameter("id"));
	}
	clear(mapping, form, request, response);
		return mapping.findForward("displayMPMList");
	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		HRManPowerMatrixForm hrMPMForm=(HRManPowerMatrixForm) form;
		hrMPMForm.setLocation("");
		hrMPMForm.setDepartment("");
		hrMPMForm.setJobTitle("");
		hrMPMForm.setRecuritmentID("");
		hrMPMForm.setDepartmentInvestment("");
		hrMPMForm.setTotalEmp("");
		return null;
	}

	public ActionForward addManPowerForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			HRManPowerMatrixForm hrMPMForm=(HRManPowerMatrixForm)form;	
		    hrMPMForm.setMessage("");
			HRDao hd=new HRDao();
			HttpSession session=request.getSession();
			ArrayList idList=new ArrayList();
			
			String recId = request.getParameter("recId");
		
			String getMPM="select * from Man_Power_Matrix where Req_Id='"+recId+"'";
			ResultSet mpmRS=hd.selectQuery(getMPM);
			while(mpmRS.next()){
				hrMPMForm.setRecuritmentID(recId);
				hrMPMForm.setDepartment(mpmRS.getString("Department"));
				hrMPMForm.setLocation(mpmRS.getString("Plant"));
			}
			request.setAttribute("manordepart", "man");
		}catch (Exception e) {
			System.out.println("exception @ man power matrix");
			e.printStackTrace();
		}
		return mapping.findForward("newManPowerMatrixForm");
	}
	public ActionForward sendManPowerRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try{
			HRManPowerMatrixForm hrMPMForm=(HRManPowerMatrixForm)form;
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			String fullname=user.getFullName();
			HRDao hd=new HRDao();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//get current date time with Date()
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			System.out.println("Current Date="+dateFormat.format(cal.getTime()));
			String currentDate=dateFormat.format(cal.getTime());
			MailInboxForm mailForm = new MailInboxForm();
			MailInboxAction mailAction = new MailInboxAction();
			String approver="";
			String lApprover="";
			String pApprover="";
			String approvermail="";
			int approver2=0;
			int approver3=0;
			
			String getApproverID="select * from Approvers_Details where Type='Add Man Power' and Priority=1";
			
			ResultSet rs=hd.selectQuery(getApproverID);
			while(rs.next()){
				
					approver = rs.getString("Employee_Name");
					lApprover = "";
					pApprover = approver;
					approvermail = rs.getString("emailID");
					
			}
			String updateSql= "update Man_Power_Matrix set Status='Pending', Req_Man_Power='"+hrMPMForm.getReqEmp()+"' where Req_Id='"+hrMPMForm.getRecuritmentID()+"'";
			LinkedList paramList =  new LinkedList();
			int upd=hd.SqlExecuteUpdate(updateSql);
			String saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons) values (";
			saveRecReq = saveRecReq + "'"+hrMPMForm.getRecuritmentID()+"','Add Man Power','"+fullname+"','"+currentDate+"','Pending','','"+pApprover+"','No')";
			int i=hd.SqlExecuteUpdate(saveRecReq);
			if(i > 0){
				hrMPMForm.setMessage("Your Request Has Been Sent.");
			}
			mailForm.setToAddress(approvermail);
			mailForm.setccAddress("");
			mailForm.setbccAddress("");
			mailForm.setSubject("Add Man Power");
			String desc = "Requested By : "+fullname+"</br>";
			desc = desc + "Requested Date : "+currentDate+"</br>";
			desc = desc + "Dpartment : "+hrMPMForm.getDepartment()+"</br>";
			desc = desc + "Required Employee : "+hrMPMForm.getReqEmp()+"</br>";
			mailForm.setDescription(desc);
			mailAction.mailSendToRecipient(request, mailForm,"request");
		}catch (Exception e) {
			System.out.println("exception @ man power matrix");
			e.printStackTrace();
		}
		return mapping.findForward("sendManPowerRequest");
	}
}
