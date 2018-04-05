package com.microlabs.hr.action;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.ClaimTRLPForm;
import com.microlabs.ess.form.ExternalIncomeForm;
import com.microlabs.ess.form.IncomeTaxForm;
import com.microlabs.ess.form.LTAForm;
import com.microlabs.ess.form.PreviousIncomeForm;
import com.microlabs.hr.form.IncomeTaxReqForm;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class IncomeTaxReqAction extends DispatchAction {
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		IncomeTaxReqForm approvalsForm=(IncomeTaxReqForm)form;
		HttpSession session=request.getSession();
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String scount = Integer.toString(approvalsForm.getStartRecord());
		String reqType=approvalsForm.getReqRequstType();
		String type=approvalsForm.getSelectedFilter();

		int totalRecords=approvalsForm.getTotalRecords();//21
		int startRecord=approvalsForm.getStartRecord();//11
		int endRecord=approvalsForm.getEndRecord();	
		
		
		 startRecord=totalRecords-9;
		 endRecord=totalRecords;
		 approvalsForm.setTotalRecords(totalRecords);
		 approvalsForm.setStartRecord(startRecord);
		 approvalsForm.setEndRecord(totalRecords);
		 String status=approvalsForm.getReqRequstType();
		 
		 try{
			  
			 if(reqType.equalsIgnoreCase("Medical"))
			 {
					List medList=new LinkedList();
					String getInvList="";
					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						LTAForm trlpForm=new LTAForm();
						trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
						trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
						trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
						trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
						trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
						trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
						trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
						trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
						medList.add(trlpForm);
					}
					request.setAttribute("medlist", medList);
				if(medList.size()==0){
					request.setAttribute("no it records", "no it records");
					approvalsForm.setMessage3("No Records Found..");
				}
				request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton");
				if(medList.size()<10)
				{
					
					request.setAttribute("previousButton", "previousButton");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				request.setAttribute("displayRecordNo", "displayRecordNo");
				}catch(Exception e){
					e.printStackTrace();
				}
			 }
				 if(reqType.equalsIgnoreCase("Apply Investment"))
				 {
					 List invList=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
						 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
						 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							 	
						}
						if(type.equalsIgnoreCase("Rejected")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
						 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
						}
						if(type.equalsIgnoreCase("All")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
					 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							IncomeTaxForm investment=new IncomeTaxForm();
							investment.setRequestNumber(rsInvList.getInt("Req_Id"));
							investment.setEmployeeNo(rsInvList.getString("PERNR"));
							investment.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							investment.setDepartment(rsInvList.getString("DPTSTXT"));
							investment.setFiscalYear(rsInvList.getString("fiscal_year"));
							investment.setTotalAmount(rsInvList.getString("total_amount"));
							investment.setSubmitDate(rsInvList.getString("Creation_Date1"));
							investment.setApprovalStatus(rsInvList.getString("Req_Status"));
							investment.setLastApprover(rsInvList.getString("Last_approver"));
							investment.setPendingApprover(rsInvList.getString("Pending_approver"));
							invList.add(investment);
						}
					request.setAttribute("investmentList", invList);
					if(invList.size()==0){
						request.setAttribute("no it records", "no it records");
						approvalsForm.setMessage3("No Records Found..");
					}
					request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton");
					if(invList.size()<10)
					{
						
						request.setAttribute("previousButton", "previousButton");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
					request.setAttribute("displayRecordNo", "displayRecordNo");
					}catch(Exception e){
						e.printStackTrace();
					}
				 }
				 if(reqType.equals("Claim TRLP / HRA"))
				 {
					 List tRLPList=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
						 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
							 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
									 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
							
						 			 	
						}
						if(type.equalsIgnoreCase("Rejected")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
									 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							
											
						}
						if(type.equalsIgnoreCase("All")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
									 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
								}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							ClaimTRLPForm trlpForm=new ClaimTRLPForm();
							trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
							trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
							trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
							trlpForm.setFiscalYear(rsInvList.getString("Year"));
							trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
							trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							tRLPList.add(trlpForm);
						}
						request.setAttribute("tRLPList", tRLPList);
						if(tRLPList.size()==0){
							request.setAttribute("no it records", "no it records");
							approvalsForm.setMessage3("No Records Found..");
						}
						request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if(tRLPList.size()<10)
						{
							
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");
						}catch(Exception e){
							e.printStackTrace();
						}
					 }
				 if(reqType.equals("Previous Income"))
				 {

						List prevIncomeist=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
						 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
							 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
							
						 			 	
						}
						if(type.equalsIgnoreCase("Rejected")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							
											
						}
						if(type.equalsIgnoreCase("All")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
								}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							PreviousIncomeForm trlpForm=new PreviousIncomeForm();
							trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
							trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
							trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
							trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							prevIncomeist.add(trlpForm);
						}
						request.setAttribute("prevIncomeist", prevIncomeist);
						if(prevIncomeist.size()==0){
							request.setAttribute("no it records", "no it records");
							approvalsForm.setMessage3("No Records Found..");
						}
						request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if(prevIncomeist.size()<10)
						{
							
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");
						}catch(Exception e){
							e.printStackTrace();
						}
					 
				 }
				 if(reqType.equalsIgnoreCase("External Income/Deduction"))
				 {
					 List externalIncomeist=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
						 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
							 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
							
						 			 	
						}
						if(type.equalsIgnoreCase("Rejected")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							
											
						}
						if(type.equalsIgnoreCase("All")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
								}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							ExternalIncomeForm trlpForm=new ExternalIncomeForm();
							trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
							trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
							trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
							trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							externalIncomeist.add(trlpForm);
						}
						request.setAttribute("externalIncomeist", externalIncomeist);
						if(externalIncomeist.size()==0){
							request.setAttribute("no it records", "no it records");
							approvalsForm.setMessage3("No Records Found..");
						}
						request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if(externalIncomeist.size()<10)
						{
							
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");
						}catch(Exception e){
							e.printStackTrace();
						}
					 
				 }
				 if(reqType.equalsIgnoreCase("LTA"))
				 {
					 List ltaList=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
						 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
							 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
							
						 			 	
						}
						if(type.equalsIgnoreCase("Rejected")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							
											
						}
						if(type.equalsIgnoreCase("All")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
								}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							LTAForm trlpForm=new LTAForm();
							trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
							trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
							trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
							trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							ltaList.add(trlpForm);
						}
						request.setAttribute("ltaList", ltaList);
						if(ltaList.size()==0){
							request.setAttribute("no it records", "no it records");
							approvalsForm.setMessage3("No Records Found..");
						}
						request.setAttribute("disableNextButton", "disableNextButton");
						request.setAttribute("previousButton", "previousButton");
						if(ltaList.size()<10)
						{
							
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
						request.setAttribute("displayRecordNo", "displayRecordNo");
						}catch(Exception e){
							e.printStackTrace();
						}
					 
				 }
			 
			  
		  }catch(Exception e){
				e.printStackTrace();
			}
		
		return mapping.findForward("display");
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		IncomeTaxReqForm approvalsForm=(IncomeTaxReqForm)form;
		HttpSession session=request.getSession();
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String scount = Integer.toString(approvalsForm.getStartRecord());
		String reqType=approvalsForm.getReqRequstType();
		String type=approvalsForm.getSelectedFilter();
		int totalRecords=approvalsForm.getTotalRecords();//21
		int endRecord=approvalsForm.getStartRecord()-1;//20
		int startRecord=approvalsForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(1);
		approvalsForm.setEndRecord(10);
		  String status=approvalsForm.getReqRequstType();
		  
		  try{
			  
			  if(reqType.equalsIgnoreCase("Medical"))
				 {
					List medList=new LinkedList();
					String getInvList="";
					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						LTAForm trlpForm=new LTAForm();
						trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
						trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
						trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
						trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
						trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
						trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
						trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
						trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
						medList.add(trlpForm);
					}
					request.setAttribute("medlist", medList);
					if(medList.size()==0){
						request.setAttribute("no it records", "no it records");
						approvalsForm.setMessage3("No Records Found..");
					}
					 request.setAttribute("displayRecordNo", "displayRecordNo");
					 
					 
					 approvalsForm.setTotalRecords(totalRecords);
					  approvalsForm.setStartRecord(startRecord);
					  approvalsForm.setEndRecord(endRecord);
						request.setAttribute("nextButton", "nextButton");
						if(startRecord!=1)
						request.setAttribute("previousButton", "previousButton");
						request.setAttribute("displayRecordNo", "displayRecordNo");
						if(medList.size()<10)
						{
							approvalsForm.setStartRecord(1);
							request.setAttribute("previousButton", "");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				 }
			  

				 if(reqType.equalsIgnoreCase("Apply Investment"))
				 {
					 List invList=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
						 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
						 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							 	
						}
						if(type.equalsIgnoreCase("Rejected")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
						 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
						}
						if(type.equalsIgnoreCase("All")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
					 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							IncomeTaxForm investment=new IncomeTaxForm();
							investment.setRequestNumber(rsInvList.getInt("Req_Id"));
							investment.setEmployeeNo(rsInvList.getString("PERNR"));
							investment.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							investment.setDepartment(rsInvList.getString("DPTSTXT"));
							investment.setFiscalYear(rsInvList.getString("fiscal_year"));
							investment.setTotalAmount(rsInvList.getString("total_amount"));
							investment.setSubmitDate(rsInvList.getString("Creation_Date1"));
							investment.setApprovalStatus(rsInvList.getString("Req_Status"));
							investment.setLastApprover(rsInvList.getString("Last_approver"));
							investment.setPendingApprover(rsInvList.getString("Pending_approver"));
							invList.add(investment);
						}
					request.setAttribute("investmentList", invList);
					if(invList.size()==0){
						request.setAttribute("no it records", "no it records");
						approvalsForm.setMessage3("No Records Found..");
					}
					 request.setAttribute("displayRecordNo", "displayRecordNo");
					 
					 
					 approvalsForm.setTotalRecords(totalRecords);
					  approvalsForm.setStartRecord(startRecord);
					  approvalsForm.setEndRecord(endRecord);
						request.setAttribute("nextButton", "nextButton");
						if(startRecord!=1)
						request.setAttribute("previousButton", "previousButton");
						request.setAttribute("displayRecordNo", "displayRecordNo");
						if(invList.size()<10)
						{
							approvalsForm.setStartRecord(1);
							request.setAttribute("previousButton", "");
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				 }
				 if(reqType.equals("Claim TRLP / HRA"))
				 {
					 List tRLPList=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
						 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
							 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
									 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
							
						 			 	
						}
						if(type.equalsIgnoreCase("Rejected")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
									 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							
											
						}
						if(type.equalsIgnoreCase("All")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
									 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
								}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							ClaimTRLPForm trlpForm=new ClaimTRLPForm();
							trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
							trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
							trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
							trlpForm.setFiscalYear(rsInvList.getString("Year"));
							trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
							trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							tRLPList.add(trlpForm);
						}
						request.setAttribute("tRLPList", tRLPList);
						if(tRLPList.size()==0){
							request.setAttribute("no it records", "no it records");
							approvalsForm.setMessage3("No Records Found..");
						}
						 request.setAttribute("displayRecordNo", "displayRecordNo");
						 
						 
						 approvalsForm.setTotalRecords(totalRecords);
						  approvalsForm.setStartRecord(startRecord);
						  approvalsForm.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							if(startRecord!=1)
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("displayRecordNo", "displayRecordNo");
							if(tRLPList.size()<10)
							{
								approvalsForm.setStartRecord(1);
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					 }
				 if(reqType.equals("Previous Income"))
				 {

						List prevIncomeist=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
						 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
							 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
							
						 			 	
						}
						if(type.equalsIgnoreCase("Rejected")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							
											
						}
						if(type.equalsIgnoreCase("All")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
								}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							PreviousIncomeForm trlpForm=new PreviousIncomeForm();
							trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
							trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
							trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
							trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							prevIncomeist.add(trlpForm);
						}
						request.setAttribute("prevIncomeist", prevIncomeist);
						if(prevIncomeist.size()==0){
							request.setAttribute("no it records", "no it records");
							approvalsForm.setMessage3("No Records Found..");
						}
						 request.setAttribute("displayRecordNo", "displayRecordNo");
						 
						 
						 approvalsForm.setTotalRecords(totalRecords);
						  approvalsForm.setStartRecord(startRecord);
						  approvalsForm.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							if(startRecord!=1)
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("displayRecordNo", "displayRecordNo");
							if(prevIncomeist.size()<10)
							{
								approvalsForm.setStartRecord(1);
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					 
				 }
				 if(reqType.equalsIgnoreCase("External Income/Deduction"))
				 {
					 List externalIncomeist=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
						 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
							 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
							
						 			 	
						}
						if(type.equalsIgnoreCase("Rejected")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							
											
						}
						if(type.equalsIgnoreCase("All")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
								}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							ExternalIncomeForm trlpForm=new ExternalIncomeForm();
							trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
							trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
							trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
							trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							externalIncomeist.add(trlpForm);
						}
						request.setAttribute("externalIncomeist", externalIncomeist);
						if(externalIncomeist.size()==0){
							request.setAttribute("no it records", "no it records");
							approvalsForm.setMessage3("No Records Found..");
						}
						 request.setAttribute("displayRecordNo", "displayRecordNo");
						 
						 
						 approvalsForm.setTotalRecords(totalRecords);
						  approvalsForm.setStartRecord(startRecord);
						  approvalsForm.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							if(startRecord!=1)
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("displayRecordNo", "displayRecordNo");
							if(externalIncomeist.size()<10)
							{
								approvalsForm.setStartRecord(1);
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					 
				 }
				 if(reqType.equalsIgnoreCase("LTA"))
				 {
					 List ltaList=new LinkedList();
						String getInvList="";
						if(type.equalsIgnoreCase("Pending")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
						 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
						 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
						 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
						}
						if(type.equalsIgnoreCase("Approved")){
							 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
									
							
						 			 	
						}
						if(type.equalsIgnoreCase("Rejected")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							
											
						}
						if(type.equalsIgnoreCase("All")){
							getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
									 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
									 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
									 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
								}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							LTAForm trlpForm=new LTAForm();
							trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
							trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
							trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
							trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
							trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							ltaList.add(trlpForm);
						}
						request.setAttribute("ltaList", ltaList);
						if(ltaList.size()==0){
							request.setAttribute("no it records", "no it records");
							approvalsForm.setMessage3("No Records Found..");
						}
						 request.setAttribute("displayRecordNo", "displayRecordNo");
						 
						 
						 approvalsForm.setTotalRecords(totalRecords);
						  approvalsForm.setStartRecord(startRecord);
						  approvalsForm.setEndRecord(endRecord);
							request.setAttribute("nextButton", "nextButton");
							if(startRecord!=1)
							request.setAttribute("previousButton", "previousButton");
							request.setAttribute("displayRecordNo", "displayRecordNo");
							if(ltaList.size()<10)
							{
								approvalsForm.setStartRecord(1);
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					 
				 }
			 
			  
		  }
		  catch(Exception e){
				e.printStackTrace();
			}
		 
		
		return mapping.findForward("display");
		
	}
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IncomeTaxReqForm approvalsForm=(IncomeTaxReqForm)form;
		HttpSession session=request.getSession();
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String scount = Integer.toString(approvalsForm.getStartRecord());
		String reqType=approvalsForm.getReqRequstType();
		String type=approvalsForm.getSelectedFilter();
		int totalRecords=approvalsForm.getTotalRecords();//21
		int startRecord=approvalsForm.getStartRecord();//11
		int endRecord=approvalsForm.getEndRecord();	
		
		
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
		
String filterBy=approvalsForm.getSelectedFilter();
		
approvalsForm.setTotalRecords(totalRecords);
approvalsForm.setStartRecord(startRecord);
approvalsForm.setEndRecord(endRecord);
approvalsForm.setSelectedFilter(filterBy);
		


if(reqType.equalsIgnoreCase("Medical"))
{
	List medList=new LinkedList();
	String getInvList="";
	if(type.equalsIgnoreCase("Pending")){
	 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
	 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
	 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
	 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
	}
	if(type.equalsIgnoreCase("Approved")){
		 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
		
	 			 	
	}
	if(type.equalsIgnoreCase("Rejected")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
		
						
	}
	if(type.equalsIgnoreCase("All")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
		
			}
	ResultSet rsInvList=ad.selectQuery(getInvList);
	try{
	while(rsInvList.next()){
		LTAForm trlpForm=new LTAForm();
		trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
		trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
		trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
		trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
		trlpForm.setFiscalYear(rsInvList.getString("year"));
		trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
		trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
		trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
		trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
		medList.add(trlpForm);
	}
	request.setAttribute("medlist", medList);
	if(medList.size()!=0)
	{
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(startRecord);
		approvalsForm.setEndRecord(endRecord);
		request.setAttribute("nextButton", "nextButton");
		request.setAttribute("previousButton", "previousButton");
	}
	else
	{
		int start=startRecord;
		int end=startRecord;
		
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(start);
		approvalsForm.setEndRecord(end);
		
	}
 if(medList.size()<10)
 {
	 approvalsForm.setTotalRecords(totalRecords);
	 approvalsForm.setStartRecord(startRecord);
	 approvalsForm.setEndRecord(startRecord+medList.size()-1);
		request.setAttribute("nextButton", "");
		request.setAttribute("disableNextButton", "disableNextButton");
		request.setAttribute("previousButton", "previousButton"); 
	 
 }
	}catch(Exception e){
		e.printStackTrace();
	}
}

if(reqType.equalsIgnoreCase("Apply Investment"))
{
	List invList=new LinkedList();
	String getInvList="";
	if(type.equalsIgnoreCase("Pending")){
	 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
	 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
	 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
	 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
	}
	if(type.equalsIgnoreCase("Approved")){
	 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
	 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
	 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
	 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
		 	
	}
	if(type.equalsIgnoreCase("Rejected")){
	 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
	 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
	 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
	 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
	}
	if(type.equalsIgnoreCase("All")){
 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	}
	ResultSet rsInvList=ad.selectQuery(getInvList);
	try{
	while(rsInvList.next()){
		IncomeTaxForm investment=new IncomeTaxForm();
		investment.setRequestNumber(rsInvList.getInt("Req_Id"));
		investment.setEmployeeNo(rsInvList.getString("PERNR"));
		investment.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
		investment.setDepartment(rsInvList.getString("DPTSTXT"));
		investment.setFiscalYear(rsInvList.getString("fiscal_year"));
		investment.setTotalAmount(rsInvList.getString("total_amount"));
		investment.setSubmitDate(rsInvList.getString("Creation_Date1"));
		investment.setApprovalStatus(rsInvList.getString("Req_Status"));
		investment.setLastApprover(rsInvList.getString("Last_approver"));
		investment.setPendingApprover(rsInvList.getString("Pending_approver"));
		invList.add(investment);
	}
	request.setAttribute("investmentList", invList);
	if(invList.size()!=0)
	{
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(startRecord);
		approvalsForm.setEndRecord(endRecord);
		request.setAttribute("nextButton", "nextButton");
		request.setAttribute("previousButton", "previousButton");
	}
	else
	{
		int start=startRecord;
		int end=startRecord;
		
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(start);
		approvalsForm.setEndRecord(end);
		
	}
 if(invList.size()<10)
 {
	 approvalsForm.setTotalRecords(totalRecords);
	 approvalsForm.setStartRecord(startRecord);
	 approvalsForm.setEndRecord(startRecord+invList.size()-1);
		request.setAttribute("nextButton", "");
		request.setAttribute("disableNextButton", "disableNextButton");
		request.setAttribute("previousButton", "previousButton"); 
	 
 }
	}catch(Exception e){
		e.printStackTrace();
	}
}
if(reqType.equals("Claim TRLP / HRA"))
{
	List tRLPList=new LinkedList();
	String getInvList="";
	if(type.equalsIgnoreCase("Pending")){
	 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
	 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
	 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
	 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
	}
	if(type.equalsIgnoreCase("Approved")){
		 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
				 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
		
	 			 	
	}
	if(type.equalsIgnoreCase("Rejected")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
				 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
		
						
	}
	if(type.equalsIgnoreCase("All")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
				 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
		
			}
	ResultSet rsInvList=ad.selectQuery(getInvList);
	try{
	while(rsInvList.next()){
		ClaimTRLPForm trlpForm=new ClaimTRLPForm();
		trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
		trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
		trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
		trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
		trlpForm.setFiscalYear(rsInvList.getString("Year"));
		trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
		trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
		trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
		trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
		tRLPList.add(trlpForm);
	}
		request.setAttribute("tRLPList", tRLPList);
		if(tRLPList.size()!=0)
		{
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(startRecord);
			approvalsForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		}
		else
		{
			int start=startRecord;
			int end=startRecord;
			
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(start);
			approvalsForm.setEndRecord(end);
			
		}
	 if(tRLPList.size()<10)
	 {
		 approvalsForm.setTotalRecords(totalRecords);
		 approvalsForm.setStartRecord(startRecord);
		 approvalsForm.setEndRecord(startRecord+tRLPList.size()-1);
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
			request.setAttribute("previousButton", "previousButton"); 
		 
	 }
		}catch(Exception e){
			e.printStackTrace();
		}
	 }
if(reqType.equals("Previous Income"))
{

	List prevIncomeist=new LinkedList();
	String getInvList="";
	if(type.equalsIgnoreCase("Pending")){
	 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
	 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
	 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
	 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
	}
	if(type.equalsIgnoreCase("Approved")){
		 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
		
	 			 	
	}
	if(type.equalsIgnoreCase("Rejected")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
		
						
	}
	if(type.equalsIgnoreCase("All")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
		
			}
	ResultSet rsInvList=ad.selectQuery(getInvList);
	try{
	while(rsInvList.next()){
		PreviousIncomeForm trlpForm=new PreviousIncomeForm();
		trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
		trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
		trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
		trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
		trlpForm.setFiscalYear(rsInvList.getString("year"));
		trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
		trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
		trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
		trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
		prevIncomeist.add(trlpForm);
	}
		request.setAttribute("prevIncomeist", prevIncomeist);
		if(prevIncomeist.size()!=0)
		{
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(startRecord);
			approvalsForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		}
		else
		{
			int start=startRecord;
			int end=startRecord;
			
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(start);
			approvalsForm.setEndRecord(end);
			
		}
	 if(prevIncomeist.size()<10)
	 {
		 approvalsForm.setTotalRecords(totalRecords);
		 approvalsForm.setStartRecord(startRecord);
		 approvalsForm.setEndRecord(startRecord+prevIncomeist.size()-1);
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
			request.setAttribute("previousButton", "previousButton"); 
		 
	 }
		}catch(Exception e){
			e.printStackTrace();
		}
	 
}
if(reqType.equalsIgnoreCase("External Income/Deduction"))
{
	List externalIncomeist=new LinkedList();
	String getInvList="";
	if(type.equalsIgnoreCase("Pending")){
	 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
	 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
	 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
	 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
	}
	if(type.equalsIgnoreCase("Approved")){
		 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
		
	 			 	
	}
	if(type.equalsIgnoreCase("Rejected")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
		
						
	}
	if(type.equalsIgnoreCase("All")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
		
			}
	ResultSet rsInvList=ad.selectQuery(getInvList);
	try{
	while(rsInvList.next()){
		ExternalIncomeForm trlpForm=new ExternalIncomeForm();
		trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
		trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
		trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
		trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
		trlpForm.setFiscalYear(rsInvList.getString("year"));
		trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
		trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
		trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
		trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
		externalIncomeist.add(trlpForm);
	}
		request.setAttribute("externalIncomeist", externalIncomeist);
		if(externalIncomeist.size()!=0)
		{
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(startRecord);
			approvalsForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		}
		else
		{
			int start=startRecord;
			int end=startRecord;
			
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(start);
			approvalsForm.setEndRecord(end);
			
		}
	 if(externalIncomeist.size()<10)
	 {
		 approvalsForm.setTotalRecords(totalRecords);
		 approvalsForm.setStartRecord(startRecord);
		 approvalsForm.setEndRecord(startRecord+externalIncomeist.size()-1);
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
			request.setAttribute("previousButton", "previousButton"); 
		 
	 }
		}catch(Exception e){
			e.printStackTrace();
		}
	 
}
if(reqType.equalsIgnoreCase("LTA"))
{
	List ltaList=new LinkedList();
	String getInvList="";
	if(type.equalsIgnoreCase("Pending")){
	 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
	 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
	 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
	 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
	}
	if(type.equalsIgnoreCase("Approved")){
		 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
		
	 			 	
	}
	if(type.equalsIgnoreCase("Rejected")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
	
		
						
	}
	if(type.equalsIgnoreCase("All")){
		getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
		
			}
	ResultSet rsInvList=ad.selectQuery(getInvList);
	try{
	while(rsInvList.next()){
		LTAForm trlpForm=new LTAForm();
		trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
		trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
		trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
		trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
		trlpForm.setFiscalYear(rsInvList.getString("year"));
		trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
		trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
		trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
		trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
		ltaList.add(trlpForm);
	}
		request.setAttribute("ltaList", ltaList);
		if(ltaList.size()!=0)
		{
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(startRecord);
			approvalsForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		}
		else
		{
			int start=startRecord;
			int end=startRecord;
			
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(start);
			approvalsForm.setEndRecord(end);
			
		}
	 if(ltaList.size()<10)
	 {
		 approvalsForm.setTotalRecords(totalRecords);
		 approvalsForm.setStartRecord(startRecord);
		 approvalsForm.setEndRecord(startRecord+ltaList.size()-1);
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
			request.setAttribute("previousButton", "previousButton"); 
		 
	 }
		}catch(Exception e){
			e.printStackTrace();
		}
	 
}

		}
		
		 if(endRecord==totalRecords)
		 {
			 request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
		 }
		 request.setAttribute("displayRecordNo", "displayRecordNo");
		
		return mapping.findForward("display");
	}
	
	public ActionForward curentRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		IncomeTaxReqForm approvalsForm=(IncomeTaxReqForm)form;
		HttpSession session=request.getSession();
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		try{
			int totalRecords=approvalsForm.getTotalRecords();//21
			int startRecord=approvalsForm.getStartRecord();//11
			int endRecord=approvalsForm.getEndRecord();	
			
			
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(startRecord);
			approvalsForm.setEndRecord(endRecord);
			String reqType=approvalsForm.getReqRequstType();
			String type=approvalsForm.getSelectedFilter();
				
			approvalsForm.setReqRequstType(reqType);
			approvalsForm.setSelectedFilter(type);
			
			if(reqType.equalsIgnoreCase("Medical"))
			 {
				List medList=new LinkedList();
				String getInvList="";
				if(type.equalsIgnoreCase("Pending")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
				}
				if(type.equalsIgnoreCase("Approved")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
					
				 			 	
				}
				if(type.equalsIgnoreCase("Rejected")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
					
									
				}
				if(type.equalsIgnoreCase("All")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						}
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					LTAForm trlpForm=new LTAForm();
					trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
					trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
					trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
					trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
					trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
					trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
					trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
					trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
					medList.add(trlpForm);
				}
				request.setAttribute("medlist", medList);
				if(medList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					if(medList.size()==0){
						request.setAttribute("no Investment records", "no Investment records");
						approvalsForm.setMessage3("No Records Found..");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			 }
			
			if(reqType.equalsIgnoreCase("Apply Investment"))
			 {
				List invList=new LinkedList();
				String getInvList="";
				if(type.equalsIgnoreCase("Pending")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
				 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
				}
				if(type.equalsIgnoreCase("Approved")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
				 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					 	
				}
				if(type.equalsIgnoreCase("Rejected")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
				 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
				}
				if(type.equalsIgnoreCase("All")){
			 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
			 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
			 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
			 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				}
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					IncomeTaxForm investment=new IncomeTaxForm();
					investment.setRequestNumber(rsInvList.getInt("Req_Id"));
					investment.setEmployeeNo(rsInvList.getString("PERNR"));
					investment.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
					investment.setDepartment(rsInvList.getString("DPTSTXT"));
					investment.setFiscalYear(rsInvList.getString("fiscal_year"));
					investment.setTotalAmount(rsInvList.getString("total_amount"));
					investment.setSubmitDate(rsInvList.getString("Creation_Date1"));
					investment.setApprovalStatus(rsInvList.getString("Req_Status"));
					investment.setLastApprover(rsInvList.getString("Last_approver"));
					investment.setPendingApprover(rsInvList.getString("Pending_approver"));
					invList.add(investment);
				}
				request.setAttribute("investmentList", invList);
				if(invList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					if(invList.size()==0){
						request.setAttribute("no Investment records", "no Investment records");
						approvalsForm.setMessage3("No Records Found..");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			 }
			 if(reqType.equals("Claim TRLP / HRA"))
			 {
				 List tRLPList=new LinkedList();
					String getInvList="";
					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
					 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
								 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
								 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
								 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						ClaimTRLPForm trlpForm=new ClaimTRLPForm();
						trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
						trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
						trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
						trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
						trlpForm.setFiscalYear(rsInvList.getString("Year"));
						trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
						trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
						trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
						trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
						tRLPList.add(trlpForm);
					}
					request.setAttribute("tRLPList", tRLPList);
					if(tRLPList.size()>0){
						if(type.equalsIgnoreCase("Pending")){
							request.setAttribute("displayButton", "displayButton");
							}
						}
						if(tRLPList.size()==0){
							request.setAttribute("no Investment records", "no Investment records");
							approvalsForm.setMessage3("No Records Found..");
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				 }
			 if(reqType.equals("Previous Income"))
			 {

					List prevIncomeist=new LinkedList();
					String getInvList="";
					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						PreviousIncomeForm trlpForm=new PreviousIncomeForm();
						trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
						trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
						trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
						trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
						trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
						trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
						trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
						trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
						prevIncomeist.add(trlpForm);
					}
					request.setAttribute("prevIncomeist", prevIncomeist);
					if(prevIncomeist.size()>0){
						if(type.equalsIgnoreCase("Pending")){
							request.setAttribute("displayButton", "displayButton");
							}
						}
						if(prevIncomeist.size()==0){
							request.setAttribute("no Investment records", "no Investment records");
							approvalsForm.setMessage3("No Records Found..");
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				 
			 }
			 if(reqType.equalsIgnoreCase("External Income/Deduction"))
			 {
				 List externalIncomeist=new LinkedList();
					String getInvList="";
					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						ExternalIncomeForm trlpForm=new ExternalIncomeForm();
						trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
						trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
						trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
						trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
						trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
						trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
						trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
						trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
						externalIncomeist.add(trlpForm);
					}
					request.setAttribute("externalIncomeist", externalIncomeist);
					if(externalIncomeist.size()>0){
						if(type.equalsIgnoreCase("Pending")){
							request.setAttribute("displayButton", "displayButton");
							}
						}
						if(externalIncomeist.size()==0){
							request.setAttribute("no Investment records", "no Investment records");
							approvalsForm.setMessage3("No Records Found..");
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				 
			 }
			 if(reqType.equalsIgnoreCase("LTA"))
			 {
				 List ltaList=new LinkedList();
					String getInvList="";
					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						LTAForm trlpForm=new LTAForm();
						trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
						trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
						trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
						trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
						trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
						trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
						trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
						trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
						ltaList.add(trlpForm);
					}
					request.setAttribute("ltaList", ltaList);
					if(ltaList.size()>0){
						if(type.equalsIgnoreCase("Pending")){
							request.setAttribute("displayButton", "displayButton");
							}
						}
						if(ltaList.size()==0){
							request.setAttribute("no Investment records", "no Investment records");
							approvalsForm.setMessage3("No Records Found..");
						}
					}catch(Exception e){
						e.printStackTrace();
					}
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("display");
	}
	public ActionForward statusChangeForRequest (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IncomeTaxReqForm statusForm=(IncomeTaxReqForm)form;
		HttpSession session=request.getSession();
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String reqId = request.getParameter("reqId");
		String reqType = statusForm.getReqRequstType();
		statusForm.setRequestType(reqType);
		String reqStatus = request.getParameter("status");
		String filterBy=statusForm.getSelectedFilter();
		statusForm.setSelectedFilter(filterBy);
	statusForm.setReqRequstType(reqType);
	int totalRecords=statusForm.getTotalRecords();//21
	int startRecord=statusForm.getStartRecord();//11
	int endRecord=statusForm.getEndRecord();	
	
	
	statusForm.setTotalRecords(totalRecords);
	statusForm.setStartRecord(startRecord);
	statusForm.setEndRecord(endRecord);
	String pendingApprovers="";
		String comments = statusForm.getComments();
		String recordStatus="";
		try{
			
			if(reqType.equalsIgnoreCase("Medical"))
			{
				try{
					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					String empID="";
					String submit_date="";
					String reqDetails="select * from Medical_Income where requestNo='"+reqId+"'";
					ResultSet rsDetails=ad.selectQuery(reqDetails);
					while(rsDetails.next()){
						empID=rsDetails.getString("employeeNo");
						submit_date=rsDetails.getString("submit_date");
					}
					int priorityCout=0;
					int totalApproverCount=0;
					String checkESSApproverList="select COUNT(*) from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
				+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Req_Type='"+reqType+"'";
					ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
					while(rsCheckInEss.next())
					{
						totalApproverCount=rsCheckInEss.getInt(1);
					}
					int approverPriority=0;
					String getApprPriority="select Priority from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,"
					+ "Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Approver_ID='"+user.getEmployeeNo()+"' and Req_Type='"+reqType+"'";
					ResultSet rsApprPriority=ad.selectQuery(getApprPriority);
					while(rsApprPriority.next())
					{
						approverPriority=rsApprPriority.getInt("Priority");
					}
				if(reqStatus.equals("Approve")){
					
					String medID[]=request.getParameterValues("ltaID");
					String medAmtApproved[]=request.getParameterValues("billamountapproved");
					
					for(int i=0;i<medID.length;i++){
						String updateLTA="update Medical_Bill_Details set tot_amount_approved='"+medAmtApproved[i]+"' where requestNo='"+reqId+"' and employeeNo='"+empID+"' and ID='"+medID[i]+"'";
						int k=ad.SqlExecuteUpdate(updateLTA);
						System.out.println(k);
					}
					
					if(totalApproverCount==approverPriority)
					{
						//last approver
						String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
					            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Medical'";
								 int upd=ad.SqlExecuteUpdate(updateSql);
						//update lastapprover and pending approver
						if(upd>0){	
							 statusForm.setMessage("Request has been approved.");
						String updateApprover="update Medical_Income set Last_approver='"+user.getFullName()+"',Pending_approver='No',approved_date='"+dateNow+"',"
						+ "Approval_Status='Approved' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
						int i1=ad.SqlExecuteUpdate(updateApprover);
						
						}
					}else{
						String nextApprover="";
						String getNextApprDetails="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
						+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='"+(approverPriority+1)+"' and "
						+ "Req_Type='Medical'";
			            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
			            while(rsNextApprDetails.next())
			            {
			            	nextApprover=rsNextApprDetails.getString("Approver_ID");
			            }
			            String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
			            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Medical'";
						 int upd=ad.SqlExecuteUpdate(updateSql);
						 if(upd>0)
						 {
							 statusForm.setMessage("Request has been approved.");
							String saveRecReq="insert into IncomeTax_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,type) values (";
							saveRecReq = saveRecReq + "'"+reqId+"','Income Tax','"+empID+"','"+submit_date+"','Pending','','"+nextApprover+"','No','"+empID+"','Medical')";
							 int i=ad.SqlExecuteUpdate(saveRecReq);
							
							 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
							 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) "
							 + "AND  Req_Type='Medical'  and mat.Approver_Id=emp.PERNR and Priority>'"+approverPriority+"' order by Priority";
							 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
								while(rsPendignApp.next())
								{  
									pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
								}
								if(!(pendingApprovers.equalsIgnoreCase(""))){
									pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
								}
							//update lastapprover and pending approver
							String updateApprover="update Medical_Income set Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"',approved_date='"+dateNow+"'"
							+ " where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
							int i1=ad.SqlExecuteUpdate(updateApprover);
							
						 }
					}
				}else{
					//reject
					

					
					String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
				            "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Medical'";
							 int upd=ad.SqlExecuteUpdate(updateSql);
					//update lastapprover and pending approver
					if(upd>0){	
						 statusForm.setMessage("Request has been rejected.");
					String updateApprover="update Medical_Income set Last_approver='"+user.getFullName()+"',Pending_approver='No',rejected_date='"+dateNow+"',"
					+ "Approval_Status='Rejected' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
					ad.SqlExecuteUpdate(updateApprover);
					
					}
				
				}
					}catch(Exception e){
					e.printStackTrace();
				}
				
				//set LTA

				String empID="";
				List LTAIncomeDet=new LinkedList();
				LTAForm ltaIncome=new LTAForm();
				
				String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from Medical_Income as i,emp_official_info as emp "
						+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
				ResultSet rs=ad.selectQuery(getDetails);
				while(rs.next()){
				
					empID=rs.getString("employeeNo");		
					String re[]=empDet(empID);
					ltaIncome.setEmployeeNo(empID);
					ltaIncome.setEmployeeName(re[0]);
					ltaIncome.setLocation(re[1]);
					ltaIncome.setDepartment(re[2]);
					ltaIncome.setDesignation(re[3]); 
					ltaIncome.setDoj(re[4]);
					ltaIncome.setStaffCategory(re[8]);
					int fiscalYear1=rs.getInt("year");
					String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
					ltaIncome.setFiscalYear(fiscalYear);
					recordStatus=rs.getString("Approval_Status");
				}
				LTAIncomeDet.add(ltaIncome);
				request.setAttribute("LTAIncomeDet", LTAIncomeDet);
				
				
				LinkedList listofLTAIncomeDet=new LinkedList();
				double totalIncome=0;
				double totalDeduction=0;
				List<LTAForm> listOfmed=new LinkedList<LTAForm>();
		        String getLTASavedDetails="select * from Medical_Bill_Details where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
		        ResultSet rsLTADet=ad.selectQuery(getLTASavedDetails);
			    while(rsLTADet.next()){
			    	LTAForm form2=new LTAForm();
			    	form2.setId(rsLTADet.getInt("ID"));
			    	form2.setRequestNo(rsLTADet.getInt("requestNo"));
			    	form2.setBillno(rsLTADet.getString("bill_no"));
			    	form2.setBilldesc(rsLTADet.getString("bill_desc"));
			    	form2.setBilldate(rsLTADet.getString("bill_date"));
			    	form2.setBillamount(rsLTADet.getString("bill_amount"));
			    	if(rsLTADet.getString("tot_amount_approved")==null)
			    	{
			    		form2.setBillamountapproved(rsLTADet.getString("bill_amount"));
			    	}
			    	else if(rsLTADet.getString("tot_amount_approved").equalsIgnoreCase(""))
			    	{
			    		form2.setBillamountapproved(rsLTADet.getString("bill_amount"));
			    	}
			    	
			    	else
			    	{
			    		form2.setBillamountapproved(rsLTADet.getString("tot_amount_approved"));
			    	}
			    	form2.setRemarks(rsLTADet.getString("remarks"));
			    	ltaIncome.setTotalIncome(rsLTADet.getDouble("tot_amount"));
			    	
			    	listOfmed.add(form2);
			    }
			     request.setAttribute("listOfmed", listOfmed);
			
				
				
				if(listofLTAIncomeDet.size()>0){
					request.setAttribute("listofLTAIncomeDet", listofLTAIncomeDet);
				}
				
				
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles";
				String sql1="select * from Medical_Bill_documents where request_no='"+reqId+"' and user_id='"+empID+"' ";
				ResultSet rs11=ad.selectQuery(sql1);
				IncomeTaxForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs11.next()) {
						leaveForm1=new IncomeTaxForm();
						leaveForm1.setFileName(rs11.getString("file_name"));
						leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
						leaveForm1.setId(rs11.getString("id"));
						leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
						leaveForm1.setInvRemarks(rs11.getString("description"));
						a1.add(leaveForm1);
					}
					if(a1.size()>0)
					request.setAttribute("documentDetails", a1);
				
				if(filterBy.equalsIgnoreCase("Pending")){
					request.setAttribute("approveButton", "approveButton");
					request.setAttribute("rejectButton", "rejectButton");
					}
				//set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
			+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
			while(rsStatus.next()){
				IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
				reqForm.setPriority(rsStatus.getInt("Priority"));
				String apprvers=rsStatus.getString("Approver_Id");
				reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
				reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
				reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
				String apprDate="";
				String approveStatus="In Process";
				 comments="";
				String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
				+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
				ResultSet rsAppInfo=ad.selectQuery(apprvDate);
				while(rsAppInfo.next()){
					approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase(""))
					{
						reqForm.setApprStatus("In Process");
					}
					if(approveStatus.equalsIgnoreCase("Approved"))
					{
						String approveDate=rsAppInfo.getString("approved_date");
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(approveDate);
					}if(approveStatus.equalsIgnoreCase("Rejected"))
					{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(rejectDate);
					}
					reqForm.setApprStatus(approveStatus);
					comments=rsAppInfo.getString("Comments");
					reqForm.setComments(comments);
					
				}
				listApprers.add(reqForm);
			}
			request.setAttribute("approverDetails", listApprers);
			
				return mapping.findForward("medicalIncome");
				
			}
			
			if(reqType.equalsIgnoreCase("Apply Investment"))
			{
				String empID="";
				try{
					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					
					String submit_date="";
					String reqDetails="select * from investment where requestNo='"+reqId+"'";
					ResultSet rsDetails=ad.selectQuery(reqDetails);
					while(rsDetails.next()){
						empID=rsDetails.getString("employeenumber");
						submit_date=rsDetails.getString("Creation_Date");
					}
					int priorityCout=0;
					int totalApproverCount=0;
					String checkESSApproverList="select COUNT(*) from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
							+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Req_Type='"+reqType+"'";
					ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
					while(rsCheckInEss.next())
					{
						totalApproverCount=rsCheckInEss.getInt(1);
					}
					int approverPriority=0;
					String getApprPriority="select Priority from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,"
					+ "Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Approver_ID='"+user.getEmployeeNo()+"' and Req_Type='Apply Investment'";
					ResultSet rsApprPriority=ad.selectQuery(getApprPriority);
					while(rsApprPriority.next())
					{
						approverPriority=rsApprPriority.getInt("Priority");
					}
				if(reqStatus.equals("Approve")){
					
					String secID[]=request.getParameterValues("ltaID");
					String amtApproved[]=request.getParameterValues("amtApproved");
					
					for(int i=0;i<secID.length;i++){
						String updateLTA="update Investment_Summary set approved='"+amtApproved[i]+"' where request_no='"+reqId+"'  and section='"+secID[i]+"'";
						int k=ad.SqlExecuteUpdate(updateLTA);
						System.out.println(k);
					}
					
					if(totalApproverCount==approverPriority)
					{
						//last approver
						String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
					            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Apply Investment'";
								 int upd=ad.SqlExecuteUpdate(updateSql);
						//update lastapprover and pending approver
						if(upd>0){	
							 statusForm.setMessage("Request has been approved.");
						String updateApprover="update investment set Last_approver='"+user.getFullName()+"',Pending_approver='No',approved_date='"+dateNow+"',"
						+ "comments='',Approval_Status='Approved' where requestNo='"+reqId+"' and employeenumber='"+empID+"'";
						int i1=ad.SqlExecuteUpdate(updateApprover);
						System.out.println("i1="+i1);
						
						}
					}else{
						String nextApprover="";
						String getNextApprDetails="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
						+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='"+(approverPriority+1)+"' and "
						+ "Req_Type='Apply Investment'";
			            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
			            while(rsNextApprDetails.next())
			            {
			            	nextApprover=rsNextApprDetails.getString("Approver_ID");
			            }
			            String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
			            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Apply Investment'";
						 int upd=ad.SqlExecuteUpdate(updateSql);
						 if(upd>0)
						 {
							 statusForm.setMessage("Request has been approved.");
							String saveRecReq="insert into IncomeTax_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,type) values (";
							saveRecReq = saveRecReq + "'"+reqId+"','Income Tax','"+empID+"','"+submit_date+"','Pending','','"+nextApprover+"','No','"+empID+"','Apply Investment')";
							 int i=ad.SqlExecuteUpdate(saveRecReq);
							
							 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
							 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) "
							 + "AND  Req_Type='Apply Investment'  and mat.Approver_Id=emp.PERNR and Priority>'"+approverPriority+"' order by Priority";
							 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
								while(rsPendignApp.next())
								{  
									pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
								}
								if(!(pendingApprovers.equalsIgnoreCase(""))){
									pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
								}
							//update lastapprover and pending approver
							String updateApprover="update investment set Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"',approved_date='"+dateNow+"',"
							+ "comments='' where requestNo='"+reqId+"' and employeenumber='"+empID+"'";
							int i1=ad.SqlExecuteUpdate(updateApprover);
							
						 }
					}
				}else{
					//reject
					

					
					String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
				            "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Apply Investment'";
							 int upd=ad.SqlExecuteUpdate(updateSql);
					//update lastapprover and pending approver
					if(upd>0){	
						 statusForm.setMessage("Request has been rejected.");
					String updateApprover="update investment set Last_approver='"+user.getFullName()+"',Pending_approver='No',rejected_date='"+dateNow+"',"
					+ "comments='',Approval_Status='Rejected' where requestNo='"+reqId+"' and employeenumber='"+empID+"'";
					ad.SqlExecuteUpdate(updateApprover);
					
					}
				
				}
					}catch(Exception e){
						e.printStackTrace();
					}
				
				//set Investment details
				List investDet=new LinkedList();
				IncomeTaxForm incomeTax=new IncomeTaxForm();
		
				String getDetails="select i.employeenumber,i.fiscal_year,i.total_amount,emp.EMP_FULLNAME,i.Approval_Status  from investment as i,emp_official_info as emp "
						+ "where  requestNo='"+reqId+"' and i.employeenumber=emp.PERNR";
				ResultSet rs=ad.selectQuery(getDetails);
				while(rs.next()){
					empID=rs.getString("employeenumber");
					String re[]=empDet(empID);
					incomeTax.setEmployeeNo(empID);
					incomeTax.setEmployeeName(re[0]);
					incomeTax.setLocation(re[1]);
					incomeTax.setDepartment(re[2]);
					incomeTax.setDesignation(re[3]); 
					incomeTax.setDoj(re[4]);
					incomeTax.setStaffCategory(re[8]);
					int fiscalYear1=rs.getInt("fiscal_year");
					String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
					incomeTax.setFiscalYear(fiscalYear);
					incomeTax.setTotalAmount(rs.getString("total_amount"));
				
					recordStatus=rs.getString("Approval_Status");
				}
				investDet.add(incomeTax);
				request.setAttribute("investment", investDet);
				String getInvestDetails="select inv.investment_Code,mas.INVTXT,Section,inv.receipt_no,inv.dates,inv.amount,inv.status,"
				+ "inv.remarks from investment_Details as inv,Investment_Master as mas where requsetNo='"+reqId+"' and inv.investment_Code=mas.INVECO";
				LinkedList list=new LinkedList();
				ResultSet rsDet=ad.selectQuery(getInvestDetails);
				while(rsDet.next())
				{
					IncomeTaxForm form2=new IncomeTaxForm();
					form2.setInvCode(rsDet.getString("investment_Code"));
					form2.setInvDes(rsDet.getString("INVTXT"));
					form2.setInvSection(rsDet.getString("Section"));
					form2.setReciptNum(rsDet.getString("receipt_no"));
					form2.setAmt(rsDet.getString("amount"));
					form2.setInvDate(EMicroUtils.display(rsDet.getDate("dates")));
					String status=rsDet.getString("status");
					if(status.equals("A"))
						status="Approved";
					if(status.equals("D"))
						status="Declared";
					form2.setInvStatus(status);
					form2.setInvRemarks(rsDet.getString("remarks"));
					list.add(form2);
				}
				request.setAttribute("investmetDetails", list);
				 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
				String sql1="select * from investment_documents where request_no='"+reqId+"'  ";
				ResultSet rs1=ad.selectQuery(sql1);
				IncomeTaxForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs1.next()) {
						leaveForm1=new IncomeTaxForm();
						leaveForm1.setFileName(rs1.getString("file_name"));
						leaveForm1.setRequestNumber(Integer.parseInt(rs1.getString("request_no")));
						leaveForm1.setId(rs1.getString("id"));
						leaveForm1.setFileFullPath(filePath+"/"+rs1.getString("file_name"));
						leaveForm1.setInvRemarks(rs1.getString("description"));
						a1.add(leaveForm1);
					}
					if(a1.size()>0)
					request.setAttribute("documentDetails", a1);
					
					//set approval status
					LinkedList listApprers=new LinkedList();
				String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
				+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
						+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
				ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
				while(rsStatus.next()){
					IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
					reqForm.setPriority(rsStatus.getInt("Priority"));
					String apprvers=rsStatus.getString("Approver_Id");
					reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
					reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
					reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
					String apprDate="";
					String approveStatus="In Process";
					 comments="";
					String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
					+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
					ResultSet rsAppInfo=ad.selectQuery(apprvDate);
					while(rsAppInfo.next()){
						approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase(""))
						{
							reqForm.setApprStatus("In Process");
						}
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
							String approveDate=rsAppInfo.getString("approved_date");
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							reqForm.setApprDate(approveDate);
						}if(approveStatus.equalsIgnoreCase("Rejected"))
						{
							String rejectDate=	rsAppInfo.getString("rejected_date");
							String a[]=rejectDate.split(" ");
							rejectDate=a[0];
							String b[]=rejectDate.split("-");
							rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							reqForm.setApprDate(rejectDate);
						}
						reqForm.setApprStatus(approveStatus);
						comments=rsAppInfo.getString("Comments");
						reqForm.setComments(comments);
						
					}
					listApprers.add(reqForm);
				}
				request.setAttribute("approverDetails", listApprers);
						
					return mapping.findForward("investment");
			
			}
			if(reqType.equalsIgnoreCase("Claim TRLP / HRA"))
			{
				try{
					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					String empID="";
					String submit_date="";
					String reqDetails="select * from Claim_TRLP where requestNo='"+reqId+"'";
					ResultSet rsDetails=ad.selectQuery(reqDetails);
					while(rsDetails.next()){
						empID=rsDetails.getString("employeeNo");
						submit_date=rsDetails.getString("submit_date");
					}
					int priorityCout=0;
					int totalApproverCount=0;
					String checkESSApproverList="select COUNT(*) from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
				+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Req_Type='"+reqType+"'";
					ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
					while(rsCheckInEss.next())
					{
						totalApproverCount=rsCheckInEss.getInt(1);
					}
					int approverPriority=0;
					String getApprPriority="select Priority from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,"
					+ "Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Approver_ID='"+user.getEmployeeNo()+"' and Req_Type='Claim TRLP / HRA'";
					ResultSet rsApprPriority=ad.selectQuery(getApprPriority);
					while(rsApprPriority.next())
					{
						approverPriority=rsApprPriority.getInt("Priority");
					}
				if(reqStatus.equals("Approve")){
					if(totalApproverCount==approverPriority)
					{
						//last approver
						String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
					            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Claim TRLP / HRA'";
								 int upd=ad.SqlExecuteUpdate(updateSql);
						//update lastapprover and pending approver
						if(upd>0){	
							 statusForm.setMessage("Request has been approved.");
						String updateApprover="update Claim_TRLP set Last_approver='"+user.getFullName()+"',Pending_approver='No',approved_date='"+dateNow+"',"
						+ "Approval_Status='Approved' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
						int i1=ad.SqlExecuteUpdate(updateApprover);
						}
					}else{
						String nextApprover="";
						String getNextApprDetails="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
						+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='"+(approverPriority+1)+"' and "
						+ "Req_Type='Claim TRLP / HRA'";
			            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
			            while(rsNextApprDetails.next())
			            {
			            	nextApprover=rsNextApprDetails.getString("Approver_ID");
			            }
			            String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
			            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Claim TRLP / HRA'";
						 int upd=ad.SqlExecuteUpdate(updateSql);
						 if(upd>0)
						 {
							 statusForm.setMessage("Request has been approved.");
							String saveRecReq="insert into IncomeTax_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,type) values (";
							saveRecReq = saveRecReq + "'"+reqId+"','Income Tax','"+empID+"','"+submit_date+"','Pending','','"+nextApprover+"','No','"+empID+"','Claim TRLP / HRA')";
							 int i=ad.SqlExecuteUpdate(saveRecReq);
							
							 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
							 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) "
							 + "AND  Req_Type='Claim TRLP / HRA'  and mat.Approver_Id=emp.PERNR and Priority>'"+approverPriority+"' order by Priority";
							 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
								while(rsPendignApp.next())
								{  
									pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
								}
								if(!(pendingApprovers.equalsIgnoreCase(""))){
									pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
								}
							//update lastapprover and pending approver
							String updateApprover="update Claim_TRLP set Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"',approved_date='"+dateNow+"'"
							+ " where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
							int i1=ad.SqlExecuteUpdate(updateApprover);
							
						 }
					}
				}else{
					//reject
					

					
					String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
				            "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Claim TRLP / HRA'";
							 int upd=ad.SqlExecuteUpdate(updateSql);
					//update lastapprover and pending approver
					if(upd>0){	
						 statusForm.setMessage("Request has been rejected.");
					String updateApprover="update Claim_TRLP set Last_approver='"+user.getFullName()+"',Pending_approver='No',rejected_date='"+dateNow+"',"
					+ "Approval_Status='Rejected' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
					ad.SqlExecuteUpdate(updateApprover);
					
					}
				
				}
					}catch(Exception e){
						e.printStackTrace();
					}
				
				//set claim TRLP/HRA details
				String empID="";
				List claimTRLPDet=new LinkedList();
				ClaimTRLPForm incomeTax=new ClaimTRLPForm();
				String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from Claim_TRLP as i,emp_official_info as emp "
						+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
				ResultSet rs=ad.selectQuery(getDetails);
				while(rs.next()){
					empID=rs.getString("employeeNo");				
					String re[]=empDet(empID);
					incomeTax.setEmployeeNo(empID);
					incomeTax.setEmployeeName(re[0]);
					incomeTax.setLocation(re[1]);
					incomeTax.setDepartment(re[2]);
					incomeTax.setDesignation(re[3]); 
					incomeTax.setDoj(re[4]);
					incomeTax.setStaffCategory(re[8]);
					int fiscalYear1=rs.getInt("Year");
					String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
					incomeTax.setFiscalYear(fiscalYear);
					recordStatus=rs.getString("Approval_Status");
				}
				claimTRLPDet.add(incomeTax);
				request.setAttribute("claimTRLPDet", claimTRLPDet);
				
				LinkedList listofAccomDet=new LinkedList();
				
				String getAccomdList="select *,case when metro ='false' then 'No' else 'Yes' end metrocity from claim_address_details where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
				ResultSet rs1=ad.selectQuery(getAccomdList);
			
				while(rs1.next()){
					ClaimTRLPForm  clform1=new ClaimTRLPForm();
				
					clform1.setFromDate(EMicroUtils.display(rs1.getDate("fromDate")));
					clform1.setToDate(EMicroUtils.display(rs1.getDate("toDate")));
					clform1.setAmount(rs1.getInt("payableAmount"));
					clform1.setAccommodationType(rs1.getString("accomdation_Type"));
					clform1.setPanNo(rs1.getString("panNo"));
					clform1.setLandName(rs1.getString("landName"));
					clform1.setCity(rs1.getString("city"));
					clform1.setAddress1(rs1.getString("address1"));
					clform1.setAddress2(rs1.getString("address2"));
					clform1.setAddress3(rs1.getString("address3"));
					clform1.setMetrocity(rs1.getString("metrocity"));
					listofAccomDet.add(clform1);
				}
				if(listofAccomDet.size()>0){
					request.setAttribute("listofAccomDet", listofAccomDet);
				}
				
				 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles";
				String sql1="select * from Claim_TRLP_documents where request_no='"+reqId+"' and user_id='"+empID+"' ";
				ResultSet rs11=ad.selectQuery(sql1);
				IncomeTaxForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs11.next()) {
						leaveForm1=new IncomeTaxForm();
						leaveForm1.setFileName(rs11.getString("file_name"));
						leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
						leaveForm1.setId(rs11.getString("id"));
						leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
						leaveForm1.setInvRemarks(rs11.getString("description"));
						a1.add(leaveForm1);
					}
					if(a1.size()>0)
					request.setAttribute("documentDetails", a1);
				
				if(reqStatus.equalsIgnoreCase("Pending")){
					request.setAttribute("approveButton", "approveButton");
					request.setAttribute("rejectButton", "rejectButton");
					}
				//set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
			while(rsStatus.next()){
				IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
				reqForm.setPriority(rsStatus.getInt("Priority"));
				String apprvers=rsStatus.getString("Approver_Id");
				reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
				reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
				reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
				String apprDate="";
				String approveStatus="In Process";
				 comments="";
				String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
				+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
				ResultSet rsAppInfo=ad.selectQuery(apprvDate);
				while(rsAppInfo.next()){
					approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase(""))
					{
						reqForm.setApprStatus("In Process");
					}
					if(approveStatus.equalsIgnoreCase("Approved"))
					{
						String approveDate=rsAppInfo.getString("approved_date");
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(approveDate);
					}if(approveStatus.equalsIgnoreCase("Rejected"))
					{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(rejectDate);
					}
					reqForm.setApprStatus(approveStatus);
					comments=rsAppInfo.getString("Comments");
					reqForm.setComments(comments);
					
				}
				listApprers.add(reqForm);
			}
			request.setAttribute("approverDetails", listApprers);
				return mapping.findForward("claimTRLP");
			}
			if(reqType.equalsIgnoreCase("Previous Income"))
			{
				try{
					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					String empID="";
					String submit_date="";
					String reqDetails="select * from Previous_Income where requestNo='"+reqId+"'";
					ResultSet rsDetails=ad.selectQuery(reqDetails);
					while(rsDetails.next()){
						empID=rsDetails.getString("employeeNo");
						submit_date=rsDetails.getString("submit_date");
					}
					int priorityCout=0;
					int totalApproverCount=0;
					String checkESSApproverList="select COUNT(*) from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
				+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Req_Type='"+reqType+"'";
					ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
					while(rsCheckInEss.next())
					{
						totalApproverCount=rsCheckInEss.getInt(1);
					}
					int approverPriority=0;
					String getApprPriority="select Priority from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,"
					+ "Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Approver_ID='"+user.getEmployeeNo()+"' and Req_Type='"+reqType+"'";
					ResultSet rsApprPriority=ad.selectQuery(getApprPriority);
					while(rsApprPriority.next())
					{
						approverPriority=rsApprPriority.getInt("Priority");
					}
				if(reqStatus.equals("Approve")){
					if(totalApproverCount==approverPriority)
					{
						//last approver
						String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
					            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Previous Income'";
								 int upd=ad.SqlExecuteUpdate(updateSql);
						//update lastapprover and pending approver
						if(upd>0){	
							 statusForm.setMessage("Request has been approved.");
						String updateApprover="update Previous_Income set Last_approver='"+user.getFullName()+"',Pending_approver='No',approved_date='"+dateNow+"',"
						+ "Approval_Status='Approved' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
						int i1=ad.SqlExecuteUpdate(updateApprover);
						
						}
					}else{
						String nextApprover="";
						String getNextApprDetails="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
						+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='"+(approverPriority+1)+"' and "
						+ "Req_Type='Previous Income'";
			            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
			            while(rsNextApprDetails.next())
			            {
			            	nextApprover=rsNextApprDetails.getString("Approver_ID");
			            }
			            String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
			            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Previous Income'";
						 int upd=ad.SqlExecuteUpdate(updateSql);
						 if(upd>0)
						 {
							 statusForm.setMessage("Request has been approved.");
							String saveRecReq="insert into IncomeTax_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,type) values (";
							saveRecReq = saveRecReq + "'"+reqId+"','Income Tax','"+empID+"','"+submit_date+"','Pending','','"+nextApprover+"','No','"+empID+"','Previous Income')";
							 int i=ad.SqlExecuteUpdate(saveRecReq);
							
							 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
							 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) "
							 + "AND  Req_Type='Previous Income'  and mat.Approver_Id=emp.PERNR and Priority>'"+approverPriority+"' order by Priority";
							 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
								while(rsPendignApp.next())
								{  
									pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
								}
								if(!(pendingApprovers.equalsIgnoreCase(""))){
									pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
								}
							//update lastapprover and pending approver
							String updateApprover="update Previous_Income set Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"',approved_date='"+dateNow+"'"
							+ " where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
							int i1=ad.SqlExecuteUpdate(updateApprover);
							
						 }
					}
				}else{
					//reject
					

					
					String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
				            "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Previous Income'";
							 int upd=ad.SqlExecuteUpdate(updateSql);
					//update lastapprover and pending approver
					if(upd>0){	
						 statusForm.setMessage("Request has been rejected.");
					String updateApprover="update Previous_Income set Last_approver='"+user.getFullName()+"',Pending_approver='No',rejected_date='"+dateNow+"',"
					+ "Approval_Status='Rejected' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
					ad.SqlExecuteUpdate(updateApprover);
					
					}
				
				}
					}catch(Exception e){
					e.printStackTrace();
				}
				
				//set Previous Income Values
				String empID="";
				List preivousIncomeDet=new LinkedList();
				PreviousIncomeForm previousIncome=new PreviousIncomeForm();
				String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from Previous_Income as i,emp_official_info as emp "
						+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
				ResultSet rs=ad.selectQuery(getDetails);
				while(rs.next()){
					empID=rs.getString("employeeNo");			
					String re[]=empDet(empID);
					previousIncome.setEmployeeNo(empID);
					previousIncome.setEmployeeName(re[0]);
					previousIncome.setLocation(re[1]);
					previousIncome.setDepartment(re[2]);
					previousIncome.setDesignation(re[3]); 
					previousIncome.setDoj(re[4]);
					previousIncome.setStaffCategory(re[8]);
					int fiscalYear1=rs.getInt("year");
					String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
					previousIncome.setFiscalYear(fiscalYear);
					recordStatus=rs.getString("Approval_Status");
				}
				preivousIncomeDet.add(previousIncome);
				request.setAttribute("previousIncomeDet", preivousIncomeDet);
				
				LinkedList listofPrevIncDet=new LinkedList();
				
				String getAccomdList="select ID,employer,saleryType,start_Date,end_Date,total_amount,remarks from "
						+ "Previous_Income_Details where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
				ResultSet rs1=ad.selectQuery(getAccomdList);
			
				while(rs1.next()){
					PreviousIncomeForm form2=new PreviousIncomeForm();
					form2.setId(rs1.getInt("ID"));
					form2.setEmployer(rs1.getString("employer"));
					/*form2.setSaleryID(rs1.getString("SAL_SNAME"));*/
					form2.setSaleryType(rs1.getString("saleryType"));
				/*	form2.setSaleryDescription(rs1.getString("SAL_LNAME"));*/
					form2.setStartDate(EMicroUtils.display(rs1.getDate("start_Date")));
					form2.setEndDate(EMicroUtils.display(rs1.getDate("end_Date")));
					form2.setTotalAmount(rs1.getString("total_amount"));
					form2.setRemarks(rs1.getString("remarks"));
					listofPrevIncDet.add(form2);
					
				}
				if(listofPrevIncDet.size()>0){
					request.setAttribute("listofPrevIncDet", listofPrevIncDet);
				}
				
				 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles";
				String sql1="select * from Previous_Income_documents where request_no='"+reqId+"' and user_id='"+empID+"' ";
				ResultSet rs11=ad.selectQuery(sql1);
				IncomeTaxForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs11.next()) {
						leaveForm1=new IncomeTaxForm();
						leaveForm1.setFileName(rs11.getString("file_name"));
						leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
						leaveForm1.setId(rs11.getString("id"));
						leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
						leaveForm1.setInvRemarks(rs11.getString("description"));
						a1.add(leaveForm1);
					}
					if(a1.size()>0)
					request.setAttribute("documentDetails", a1);
				
				
				//set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
			while(rsStatus.next()){
				IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
				reqForm.setPriority(rsStatus.getInt("Priority"));
				String apprvers=rsStatus.getString("Approver_Id");
				reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
				reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
				reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
				String apprDate="";
				String approveStatus="In Process";
				 comments="";
				String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
				+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
				ResultSet rsAppInfo=ad.selectQuery(apprvDate);
				while(rsAppInfo.next()){
					approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase(""))
					{
						reqForm.setApprStatus("In Process");
					}
					if(approveStatus.equalsIgnoreCase("Approved"))
					{
						String approveDate=rsAppInfo.getString("approved_date");
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(approveDate);
					}if(approveStatus.equalsIgnoreCase("Rejected"))
					{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(rejectDate);
					}
					reqForm.setApprStatus(approveStatus);
					comments=rsAppInfo.getString("Comments");
					reqForm.setComments(comments);
					
				}
				listApprers.add(reqForm);
			}
			request.setAttribute("approverDetails", listApprers);
				return mapping.findForward("previousIncome");
			}
			if(reqType.equalsIgnoreCase("External Income/Deduction"))
			{
				try{
					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					String empID="";
					String submit_date="";
					String reqDetails="select * from External_Income where requestNo='"+reqId+"'";
					ResultSet rsDetails=ad.selectQuery(reqDetails);
					while(rsDetails.next()){
						empID=rsDetails.getString("employeeNo");
						submit_date=rsDetails.getString("submit_date");
					}
					int priorityCout=0;
					int totalApproverCount=0;
					String checkESSApproverList="select COUNT(*) from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
				+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Req_Type='"+reqType+"'";
					ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
					while(rsCheckInEss.next())
					{
						totalApproverCount=rsCheckInEss.getInt(1);
					}
					int approverPriority=0;
					String getApprPriority="select Priority from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,"
					+ "Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Approver_ID='"+user.getEmployeeNo()+"' and Req_Type='"+reqType+"'";
					ResultSet rsApprPriority=ad.selectQuery(getApprPriority);
					while(rsApprPriority.next())
					{
						approverPriority=rsApprPriority.getInt("Priority");
					}
				if(reqStatus.equals("Approve")){
					if(totalApproverCount==approverPriority)
					{
						//last approver
						String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
					            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='External Income/Deduction'";
								 int upd=ad.SqlExecuteUpdate(updateSql);
						//update lastapprover and pending approver
						if(upd>0){	
							 statusForm.setMessage("Request has been approved.");
						String updateApprover="update External_Income set Last_approver='"+user.getFullName()+"',Pending_approver='No',approved_date='"+dateNow+"',"
						+ "Approval_Status='Approved' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
						int i1=ad.SqlExecuteUpdate(updateApprover);
						
						}
					}else{
						String nextApprover="";
						String getNextApprDetails="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
						+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='"+(approverPriority+1)+"' and "
						+ "Req_Type='External Income/Deduction'";
			            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
			            while(rsNextApprDetails.next())
			            {
			            	nextApprover=rsNextApprDetails.getString("Approver_ID");
			            }
			            String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
			            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='External Income/Deduction'";
						 int upd=ad.SqlExecuteUpdate(updateSql);
						 if(upd>0)
						 {
							 statusForm.setMessage("Request has been approved.");
							String saveRecReq="insert into IncomeTax_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,type) values (";
							saveRecReq = saveRecReq + "'"+reqId+"','Income Tax','"+empID+"','"+submit_date+"','Pending','','"+nextApprover+"','No','"+empID+"','External Income/Deduction')";
							 int i=ad.SqlExecuteUpdate(saveRecReq);
							
							 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
							 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) "
							 + "AND  Req_Type='External Income/Deduction'  and mat.Approver_Id=emp.PERNR and Priority>'"+approverPriority+"' order by Priority";
							 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
								while(rsPendignApp.next())
								{  
									pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
								}
								if(!(pendingApprovers.equalsIgnoreCase(""))){
									pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
								}
							//update lastapprover and pending approver
							String updateApprover="update External_Income set Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"',approved_date='"+dateNow+"'"
							+ " where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
							int i1=ad.SqlExecuteUpdate(updateApprover);
							
						 }
					}
				}else{
					//reject
					

					
					String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
				            "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='External Income/Deduction'";
							 int upd=ad.SqlExecuteUpdate(updateSql);
					//update lastapprover and pending approver
					if(upd>0){	
						 statusForm.setMessage("Request has been rejected.");
					String updateApprover="update External_Income set Last_approver='"+user.getFullName()+"',Pending_approver='No',rejected_date='"+dateNow+"',"
					+ "Approval_Status='Rejected' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
					ad.SqlExecuteUpdate(updateApprover);
					
					}
				
				}
					}catch(Exception e){
					e.printStackTrace();
				}
				
				//set External Income
				String empID="";
				List extIncomeDet=new LinkedList();
				ExternalIncomeForm previousIncome=new ExternalIncomeForm();
				String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from External_Income as i,emp_official_info as emp "
						+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
				ResultSet rs=ad.selectQuery(getDetails);
				while(rs.next()){
					empID=rs.getString("employeeNo");				
					String re[]=empDet(empID);
					previousIncome.setEmployeeNo(empID);
					previousIncome.setEmployeeName(re[0]);
					previousIncome.setLocation(re[1]);
					previousIncome.setDepartment(re[2]);
					previousIncome.setDesignation(re[3]); 
					previousIncome.setDoj(re[4]);
					previousIncome.setStaffCategory(re[8]);
					int fiscalYear1=rs.getInt("year");
					String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
					previousIncome.setFiscalYear(fiscalYear);
					recordStatus=rs.getString("Approval_Status");
				}
				
				LinkedList listofExtIncomeDet=new LinkedList();
				double totalIncome=0;
				double totalDeduction=0;
				String getAccomdList="select p.ID,p.salery_Desc,p.saleryType,p.start_Date,p.end_Date,p.total_amount,p.remarks,p.total_Income,p.total_Deduction from "
						+ "External_Income_Details as p where requestNo='"+reqId+"' and employeeNo='"+empID+"' ";
				ResultSet rs1=ad.selectQuery(getAccomdList);
				while(rs1.next()){
					ExternalIncomeForm form2=new ExternalIncomeForm();
					form2.setId(rs1.getInt("ID"));
					/*form2.setSaleryID(rs1.getString("SAL_SNAME"));*/
					form2.setSaleryDescription(rs1.getString("salery_Desc"));
					String salaryType=rs1.getString("saleryType");
					if(salaryType.equals("I"))
						salaryType="Income";
					if(salaryType.equals("D"))
						salaryType="Deduction";
					form2.setSaleryType(salaryType);
				
					form2.setStartDate(EMicroUtils.display(rs1.getDate("start_Date")));
					form2.setEndDate(EMicroUtils.display(rs1.getDate("end_Date")));
					form2.setTotalAmount(rs1.getString("total_amount"));
					form2.setRemarks(rs1.getString("remarks"));
					form2.setTotalIncome(rs1.getDouble("total_Income"));
					form2.setTotalDeduction(rs1.getDouble("total_Deduction"));
					totalIncome=rs1.getDouble("total_Income");
					totalDeduction=rs1.getDouble("total_Deduction");
					listofExtIncomeDet.add(form2);
					
				}
				previousIncome.setTotalIncome(totalIncome);
				previousIncome.setTotalDeduction(totalDeduction);
				extIncomeDet.add(previousIncome);
				request.setAttribute("extIncomeDet", extIncomeDet);
				if(listofExtIncomeDet.size()>0){
					request.setAttribute("listofExtIncomeDet", listofExtIncomeDet);
				}
				 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/External Income/UploadFiles";
				String sql1="select * from External_Income_documents where request_no='"+reqId+"' and user_id='"+empID+"' ";
				ResultSet rs11=ad.selectQuery(sql1);
				IncomeTaxForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs11.next()) {
						leaveForm1=new IncomeTaxForm();
						leaveForm1.setFileName(rs11.getString("file_name"));
						leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
						leaveForm1.setId(rs11.getString("id"));
						leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
						leaveForm1.setInvRemarks(rs11.getString("description"));
						a1.add(leaveForm1);
					}
					if(a1.size()>0)
					request.setAttribute("documentDetails", a1);
				
				//set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
			while(rsStatus.next()){
				IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
				reqForm.setPriority(rsStatus.getInt("Priority"));
				String apprvers=rsStatus.getString("Approver_Id");
				reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
				reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
				reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
				String apprDate="";
				String approveStatus="In Process";
				 comments="";
				String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
				+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
				ResultSet rsAppInfo=ad.selectQuery(apprvDate);
				while(rsAppInfo.next()){
					approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase(""))
					{
						reqForm.setApprStatus("In Process");
					}
					if(approveStatus.equalsIgnoreCase("Approved"))
					{
						String approveDate=rsAppInfo.getString("approved_date");
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(approveDate);
					}if(approveStatus.equalsIgnoreCase("Rejected"))
					{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(rejectDate);
					}
					reqForm.setApprStatus(approveStatus);
					comments=rsAppInfo.getString("Comments");
					reqForm.setComments(comments);
					
				}
				listApprers.add(reqForm);
			}
			request.setAttribute("approverDetails", listApprers);
				return mapping.findForward("externalIncome");
			}
			if(reqType.equalsIgnoreCase("LTA"))
			{
				try{
					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					String empID="";
					String submit_date="";
					String reqDetails="select * from LTA_Income where requestNo='"+reqId+"'";
					ResultSet rsDetails=ad.selectQuery(reqDetails);
					while(rsDetails.next()){
						empID=rsDetails.getString("employeeNo");
						submit_date=rsDetails.getString("submit_date");
					}
					int priorityCout=0;
					int totalApproverCount=0;
					String checkESSApproverList="select COUNT(*) from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
				+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Req_Type='"+reqType+"'";
					ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
					while(rsCheckInEss.next())
					{
						totalApproverCount=rsCheckInEss.getInt(1);
					}
					int approverPriority=0;
					String getApprPriority="select Priority from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,"
					+ "Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Approver_ID='"+user.getEmployeeNo()+"' and Req_Type='"+reqType+"'";
					ResultSet rsApprPriority=ad.selectQuery(getApprPriority);
					while(rsApprPriority.next())
					{
						approverPriority=rsApprPriority.getInt("Priority");
					}
				if(reqStatus.equals("Approve")){
					
					String ltaID[]=request.getParameterValues("ltaID");
					String ltaAmtApproved[]=request.getParameterValues("ltaAmtApproved");
					
					for(int i=0;i<ltaID.length;i++){
						String updateLTA="update LTA_Details set LTA_amount_approved='"+ltaAmtApproved[i]+"' where requestNo='"+reqId+"' and employeeNo='"+empID+"' and ID='"+ltaID[i]+"'";
						int k=ad.SqlExecuteUpdate(updateLTA);
						System.out.println(k);
					}
					
					if(totalApproverCount==approverPriority)
					{
						//last approver
						String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
					            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='LTA'";
								 int upd=ad.SqlExecuteUpdate(updateSql);
						//update lastapprover and pending approver
						if(upd>0){	
							 statusForm.setMessage("Request has been approved.");
						String updateApprover="update LTA_Income set Last_approver='"+user.getFullName()+"',Pending_approver='No',approved_date='"+dateNow+"',"
						+ "Approval_Status='Approved' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
						int i1=ad.SqlExecuteUpdate(updateApprover);
						
						}
					}else{
						String nextApprover="";
						String getNextApprDetails="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
						+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='"+(approverPriority+1)+"' and "
						+ "Req_Type='LTA'";
			            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
			            while(rsNextApprDetails.next())
			            {
			            	nextApprover=rsNextApprDetails.getString("Approver_ID");
			            }
			            String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
			            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='LTA'";
						 int upd=ad.SqlExecuteUpdate(updateSql);
						 if(upd>0)
						 {
							 statusForm.setMessage("Request has been approved.");
							String saveRecReq="insert into IncomeTax_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,type) values (";
							saveRecReq = saveRecReq + "'"+reqId+"','Income Tax','"+empID+"','"+submit_date+"','Pending','','"+nextApprover+"','No','"+empID+"','LTA')";
							 int i=ad.SqlExecuteUpdate(saveRecReq);
							
							 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
							 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) "
							 + "AND  Req_Type='LTA'  and mat.Approver_Id=emp.PERNR and Priority>'"+approverPriority+"' order by Priority";
							 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
								while(rsPendignApp.next())
								{  
									pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
								}
								if(!(pendingApprovers.equalsIgnoreCase(""))){
									pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
								}
							//update lastapprover and pending approver
							String updateApprover="update LTA_Income set Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"',approved_date='"+dateNow+"'"
							+ " where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
							int i1=ad.SqlExecuteUpdate(updateApprover);
							
						 }
					}
				}else{
					//reject
					

					
					String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments='"+comments+"'," +
				            "approved_date='',rejected_date='"+dateNow+"',Req_Status='Rejected' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='LTA'";
							 int upd=ad.SqlExecuteUpdate(updateSql);
					//update lastapprover and pending approver
					if(upd>0){	
						 statusForm.setMessage("Request has been rejected.");
					String updateApprover="update LTA_Income set Last_approver='"+user.getFullName()+"',Pending_approver='No',rejected_date='"+dateNow+"',"
					+ "Approval_Status='Rejected' where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
					ad.SqlExecuteUpdate(updateApprover);
					
					}
				
				}
					}catch(Exception e){
					e.printStackTrace();
				}
				
				//set LTA

				String empID="";
				List LTAIncomeDet=new LinkedList();
				LTAForm ltaIncome=new LTAForm();
				
				String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from LTA_Income as i,emp_official_info as emp "
						+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
				ResultSet rs=ad.selectQuery(getDetails);
				while(rs.next()){
				
					empID=rs.getString("employeeNo");		
					String re[]=empDet(empID);
					ltaIncome.setEmployeeNo(empID);
					ltaIncome.setEmployeeName(re[0]);
					ltaIncome.setLocation(re[1]);
					ltaIncome.setDepartment(re[2]);
					ltaIncome.setDesignation(re[3]); 
					ltaIncome.setDoj(re[4]);
					ltaIncome.setStaffCategory(re[8]);
					int fiscalYear1=rs.getInt("year");
					String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
					ltaIncome.setFiscalYear(fiscalYear);
					recordStatus=rs.getString("Approval_Status");
				}
				LTAIncomeDet.add(ltaIncome);
				request.setAttribute("LTAIncomeDet", LTAIncomeDet);
				
				
				LinkedList listofLTAIncomeDet=new LinkedList();
				double totalIncome=0;
				double totalDeduction=0;
				List<LTAForm> listOfLta=new LinkedList<LTAForm>();
		        String getLTASavedDetails="select * from LTA_Details where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
		        ResultSet rsLTADet=ad.selectQuery(getLTASavedDetails);
			    while(rsLTADet.next()){
			    	LTAForm form2=new LTAForm();
			    	form2.setId(rsLTADet.getInt("ID"));
			    	form2.setRequestNo(rsLTADet.getInt("requestNo"));
			    	String lv_Type=rsLTADet.getString("Leave_Type");
			    	if(lv_Type.equals("3"))
			    		lv_Type="Privilage";
			    	form2.setLeaveType(lv_Type);
			    	form2.setTravelStartDate(EMicroUtils.display(rsLTADet.getDate("Travel_Start_Date")));
			    	form2.setStartDate(EMicroUtils.display(rsLTADet.getDate("start_Date")));
			    	form2.setStartDurationType(rsLTADet.getString("start_Dur"));
			    	form2.setTravelEndDate(EMicroUtils.display(rsLTADet.getDate("end_Date")));
			    	form2.setEndDurationType(rsLTADet.getString("end_Dur"));
			    	form2.setNoOfDays(rsLTADet.getString("noOf_days"));
			    	form2.setEndDate(EMicroUtils.display(rsLTADet.getDate("Travel_End_Date")));
			    	form2.setLtaAmtApplFor(rsLTADet.getString("LTA_amount_approved_for"));
			    	form2.setLtaAmtAprvdFor(rsLTADet.getString("LTA_amount_approved"));
			    	listOfLta.add(form2);
			    }
			     request.setAttribute("listOfLta", listOfLta);
			
				
				
				if(listofLTAIncomeDet.size()>0){
					request.setAttribute("listofLTAIncomeDet", listofLTAIncomeDet);
				}
				
				
				 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles";
				String sql1="select * from LTA_Income_documents where request_no='"+reqId+"' and user_id='"+empID+"' ";
				ResultSet rs11=ad.selectQuery(sql1);
				IncomeTaxForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs11.next()) {
						leaveForm1=new IncomeTaxForm();
						leaveForm1.setFileName(rs11.getString("file_name"));
						leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
						leaveForm1.setId(rs11.getString("id"));
						leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
						leaveForm1.setInvRemarks(rs11.getString("description"));
						a1.add(leaveForm1);
					}
					if(a1.size()>0)
					request.setAttribute("documentDetails", a1);
				//set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
			while(rsStatus.next()){
				IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
				reqForm.setPriority(rsStatus.getInt("Priority"));
				String apprvers=rsStatus.getString("Approver_Id");
				reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
				reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
				reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
				String apprDate="";
				String approveStatus="In Process";
				 comments="";
				String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
				+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
				ResultSet rsAppInfo=ad.selectQuery(apprvDate);
				while(rsAppInfo.next()){
					approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase(""))
					{
						reqForm.setApprStatus("In Process");
					}
					if(approveStatus.equalsIgnoreCase("Approved"))
					{
						String approveDate=rsAppInfo.getString("approved_date");
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(approveDate);
					}if(approveStatus.equalsIgnoreCase("Rejected"))
					{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(rejectDate);
					}
					reqForm.setApprStatus(approveStatus);
					comments=rsAppInfo.getString("Comments");
					reqForm.setComments(comments);
					
				}
				listApprers.add(reqForm);
			}
			request.setAttribute("approverDetails", listApprers);
				return mapping.findForward("ltaIncome");
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("investment");	
	}
	
	
	private String[] empDet( String empno) {
		//Created by Pradeep .Date:15-11-2016
		
 		EssDao ad = new EssDao();
		String emp=" select emp.eMP_FULLNAME,emp.lOCID,dep.DPTSTXT,desg.DSGSTXT,convert(nvarchar(10),emp.doj,103) as doj,datediff(year,dob,getdate()) as age,emp.Email_id, case when emp.sex='M' then 'Male' else 'Female' end as gender,catltext from Emp_official_info emp "
				+ " , Department dep ,Designation desg,category c  where emp.dPTID = dep.DPTID and emp.pERNR='"+empno+"'  and emp.staffcat=c.staffcat "
						+ " and desg.DSGID= emp.dSGID  ";
		ResultSet ae=ad.selectQuery(emp);

		String[] a = new String[10];
	
		try {
			while(ae.next())
			{
			
			

			    a[0]=ae.getString("eMP_FULLNAME");
			    a[1]=ae.getString("lOCID");
			    a[2]=ae.getString("DPTSTXT");
			    a[3]=ae.getString("DSGSTXT");
			    a[4]=ae.getString("doj");
			    a[5]=ae.getString("age");
			    a[6]=ae.getString("email_id");
			    a[7]=ae.getString("gender");
			    a[8]=ae.getString("catltext");
      


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
		}
	public ActionForward getSelectedRequestToApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IncomeTaxReqForm pendAppForm=(IncomeTaxReqForm)form;
		
		String reqId = request.getParameter("reqId");
		String reqType = pendAppForm.getReqRequstType();
		pendAppForm.setRequestNo(reqId);
		pendAppForm.setRequestType(reqType);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String scount = Integer.toString(pendAppForm.getStartRecord());
		
		int totalRecords=pendAppForm.getTotalRecords();//21
		int startRecord=pendAppForm.getStartRecord();//11
		int endRecord=pendAppForm.getEndRecord();	
		String filterBy=pendAppForm.getSelectedFilter();
		
		pendAppForm.setTotalRecords(totalRecords);
		pendAppForm.setStartRecord(startRecord);
		pendAppForm.setEndRecord(endRecord);
	pendAppForm.setSelectedFilter(filterBy);
		if(reqId==null && reqType==null){
			reqId=pendAppForm.getRequestNo();
			reqType=pendAppForm.getRequestType();
		}
		int startCount = 0;
		int endCount = 0;
		if(scount == null){
			request.setAttribute("noRecords","noRecords");
		}
		if(scount != null){
			startCount=Integer.parseInt(scount);
			endCount=endRecord;
			pendAppForm.setStartRecord(startCount);
			pendAppForm.setEndRecord(endCount);
		}
		setAllCount(pendAppForm, pendAppForm.getTotalAppCount(), startCount, endCount);
		request.setAttribute("header", reqType+"For Approve");
		String tName="leave";
		setFilterType(reqType, pendAppForm);
		String uName = user.getUserName();
		String recordStatus="";
		EssDao ad=new EssDao();
		
		try{
			
			if(reqType.equalsIgnoreCase("Medical"))
			{
				String empID="";
				List LTAIncomeDet=new LinkedList();
				LTAForm ltaIncome=new LTAForm();
				int requestNo=Integer.parseInt(request.getParameter("RequesterNo"));
				String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from Medical_Income as i,emp_official_info as emp "
						+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
				ResultSet rs=ad.selectQuery(getDetails);
				while(rs.next()){
					ltaIncome.setRequestNo(requestNo);
					empID=rs.getString("employeeNo");		
					String re[]=empDet(empID);
					ltaIncome.setEmployeeNo(empID);
					ltaIncome.setEmployeeName(re[0]);
					ltaIncome.setLocation(re[1]);
					ltaIncome.setDepartment(re[2]);
					ltaIncome.setDesignation(re[3]); 
					ltaIncome.setDoj(re[4]);
					ltaIncome.setStaffCategory(re[8]);
					int fiscalYear1=rs.getInt("year");
					String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
					ltaIncome.setFiscalYear(fiscalYear);
					recordStatus=rs.getString("Approval_Status");
				}
				LTAIncomeDet.add(ltaIncome);
				request.setAttribute("LTAIncomeDet", LTAIncomeDet);
				
				
				LinkedList listofLTAIncomeDet=new LinkedList();
				double totalIncome=0;
				double totalDeduction=0;
				List<LTAForm> listOfmed=new LinkedList<LTAForm>();
		        String getLTASavedDetails="select * from Medical_Bill_Details where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
		        ResultSet rsLTADet=ad.selectQuery(getLTASavedDetails);
			    while(rsLTADet.next()){
			    	LTAForm form2=new LTAForm();
			    	form2.setId(rsLTADet.getInt("ID"));
			    	form2.setRequestNo(rsLTADet.getInt("requestNo"));
			    	form2.setBillno(rsLTADet.getString("bill_no"));
			    	form2.setBilldesc(rsLTADet.getString("bill_desc"));
			    	form2.setBilldate(rsLTADet.getString("bill_date"));
			    	form2.setBillamount(rsLTADet.getString("bill_amount"));
			    	if(rsLTADet.getString("tot_amount_approved")==null)
			    	{
			    		form2.setBillamountapproved(rsLTADet.getString("bill_amount"));
			    	}
			    	else if(rsLTADet.getString("tot_amount_approved").equalsIgnoreCase(""))
			    	{
			    		form2.setBillamountapproved(rsLTADet.getString("bill_amount"));
			    	}
			    	
			    	else
			    	{
			    		form2.setBillamountapproved(rsLTADet.getString("tot_amount_approved"));
			    	}
			    	
			    	form2.setRemarks(rsLTADet.getString("remarks"));
			    	ltaIncome.setTotalIncome(rsLTADet.getDouble("tot_amount"));
			    	
			    	listOfmed.add(form2);
			    }
			     request.setAttribute("listOfmed", listOfmed);
			
				
				
				if(listofLTAIncomeDet.size()>0){
					request.setAttribute("listofLTAIncomeDet", listofLTAIncomeDet);
				}
				
				
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Medical/UploadFiles";
				String sql1="select * from Medical_Bill_documents where request_no='"+requestNo+"' and user_id='"+empID+"' ";
				ResultSet rs11=ad.selectQuery(sql1);
				IncomeTaxForm leaveForm1=null;
					ArrayList a1=new ArrayList();
					while(rs11.next()) {
						leaveForm1=new IncomeTaxForm();
						leaveForm1.setFileName(rs11.getString("file_name"));
						leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
						leaveForm1.setId(rs11.getString("id"));
						leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
						leaveForm1.setInvRemarks(rs11.getString("description"));
						a1.add(leaveForm1);
					}
					if(a1.size()>0)
					request.setAttribute("documentDetails", a1);
				
				if(filterBy.equalsIgnoreCase("Pending")){
					request.setAttribute("approveButton", "approveButton");
					request.setAttribute("rejectButton", "rejectButton");
					}
				//set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
			+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
			while(rsStatus.next()){
				IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
				reqForm.setPriority(rsStatus.getInt("Priority"));
				String apprvers=rsStatus.getString("Approver_Id");
				reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
				reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
				reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
				String apprDate="";
				String approveStatus="In Process";
				String comments="";
				String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
				+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
				ResultSet rsAppInfo=ad.selectQuery(apprvDate);
				while(rsAppInfo.next()){
					approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase(""))
					{
						reqForm.setApprStatus("In Process");
					}
					if(approveStatus.equalsIgnoreCase("Approved"))
					{
						String approveDate=rsAppInfo.getString("approved_date");
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(approveDate);
					}if(approveStatus.equalsIgnoreCase("Rejected"))
					{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(rejectDate);
					}
					reqForm.setApprStatus(approveStatus);
					comments=rsAppInfo.getString("Comments");
					reqForm.setComments(comments);
					
				}
				listApprers.add(reqForm);
			}
			request.setAttribute("approverDetails", listApprers);
			
				return mapping.findForward("medicalIncome");
			}
			
			
		if(reqType.equalsIgnoreCase("Apply Investment"))
		{
			String empID="";
			List investDet=new LinkedList();
			IncomeTaxForm incomeTax=new IncomeTaxForm();
			int requestNo=Integer.parseInt(request.getParameter("RequesterNo"));
			String getDetails="select i.employeenumber,i.fiscal_year,i.total_amount,emp.EMP_FULLNAME,i.Approval_Status  from investment as i,emp_official_info as emp "
					+ "where  requestNo='"+reqId+"' and i.employeenumber=emp.PERNR";
			ResultSet rs=ad.selectQuery(getDetails);
			while(rs.next()){
				incomeTax.setRequestNumber(requestNo);
				empID=rs.getString("employeenumber");
				String re[]=empDet(empID);
				incomeTax.setEmployeeNo(empID);
				incomeTax.setEmployeeName(re[0]);
				incomeTax.setLocation(re[1]);
				incomeTax.setDepartment(re[2]);
				incomeTax.setDesignation(re[3]); 
				incomeTax.setDoj(re[4]);
				incomeTax.setStaffCategory(re[8]);
				int fiscalYear1=rs.getInt("fiscal_year");
				String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
				incomeTax.setFiscalYear(fiscalYear);
				incomeTax.setTotalAmount(rs.getString("total_amount"));
				
				recordStatus=rs.getString("Approval_Status");
			}
			investDet.add(incomeTax);
			request.setAttribute("investment", investDet);
			String getInvestDetails="select inv.investment_Code,mas.INVTXT,Section,inv.receipt_no,inv.dates,inv.amount,inv.status,"
			+ "inv.remarks from investment_Details as inv,Investment_Master as mas where requsetNo='"+reqId+"' and inv.investment_Code=mas.INVECO ";
			LinkedList list=new LinkedList();
			ResultSet rsDet=ad.selectQuery(getInvestDetails);
			while(rsDet.next())
			{
				IncomeTaxForm form2=new IncomeTaxForm();
				form2.setInvCode(rsDet.getString("investment_Code"));
				form2.setInvDes(rsDet.getString("INVTXT"));
				form2.setInvSection(rsDet.getString("Section"));
				form2.setReciptNum(rsDet.getString("receipt_no"));
				form2.setAmt(rsDet.getString("amount"));
				form2.setInvDate(EMicroUtils.display(rsDet.getDate("dates")));
				String status=rsDet.getString("status");
				if(status.equals("A"))
					status="Approved";
				if(status.equals("D"))
					status="Declared";
				form2.setInvStatus(status);
				form2.setInvRemarks(rsDet.getString("remarks"));
				list.add(form2);
			}
			request.setAttribute("investmetDetails", list);
			 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles";
			String sql1="select * from investment_documents where request_no='"+requestNo+"' and user_id='"+empID+"' ";
			ResultSet rs1=ad.selectQuery(sql1);
			IncomeTaxForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs1.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs1.getString("file_name"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs1.getString("request_no")));
					leaveForm1.setId(rs1.getString("id"));
					leaveForm1.setFileFullPath(filePath+"/"+rs1.getString("file_name"));
					leaveForm1.setInvRemarks(rs1.getString("description"));
					a1.add(leaveForm1);
				}
				if(a1.size()>0)
				request.setAttribute("documentDetails", a1);
				
				if(filterBy.equalsIgnoreCase("Pending")){
					request.setAttribute("approveButton", "approveButton");
					request.setAttribute("rejectButton", "rejectButton");
					}
			//set approval status
				LinkedList listApprers=new LinkedList();
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
			while(rsStatus.next()){
				IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
				reqForm.setPriority(rsStatus.getInt("Priority"));
				String apprvers=rsStatus.getString("Approver_Id");
				reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
				reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
				reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
				String apprDate="";
				String approveStatus="In Process";
				String comments="";
				String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
				+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
				ResultSet rsAppInfo=ad.selectQuery(apprvDate);
				while(rsAppInfo.next()){
					approveStatus=rsAppInfo.getString("Req_Status");
					if(approveStatus.equalsIgnoreCase(""))
					{
						reqForm.setApprStatus("In Process");
					}
					if(approveStatus.equalsIgnoreCase("Approved"))
					{
						String approveDate=rsAppInfo.getString("approved_date");
						String a[]=approveDate.split(" ");
						approveDate=a[0];
						String b[]=approveDate.split("-");
						approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(approveDate);
					}if(approveStatus.equalsIgnoreCase("Rejected"))
					{
						String rejectDate=	rsAppInfo.getString("rejected_date");
						String a[]=rejectDate.split(" ");
						rejectDate=a[0];
						String b[]=rejectDate.split("-");
						rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
						reqForm.setApprDate(rejectDate);
					}
					reqForm.setApprStatus(approveStatus);
					comments=rsAppInfo.getString("Comments");
					reqForm.setComments(comments);
					
				}
				listApprers.add(reqForm);
			}
			request.setAttribute("approverDetails", listApprers);
			
			///sumary
			
			ArrayList sectionlist=new ArrayList();
			
			String limit="select * from Investment_Summary where  request_no='"+requestNo+"'";
			ResultSet l=ad.selectQuery(limit);
			try {
				while(l.next())
				{
					IncomeTaxForm form2=new IncomeTaxForm();
					form2.setInvSection(l.getString("Section"));
					form2.setInvLimit(l.getString("limit"));
					form2.setInvCode(l.getString("claim_request"));
					form2.setInvRemarks(l.getString("claim_year"));
					form2.setInvStatus(l.getString("allowed"));
					if(l.getString("approved")==null)
					{
						form2.setAmtApproved(l.getString("allowed"));
					}
					
					else if(l.getString("approved").equalsIgnoreCase(""))
					{
						form2.setAmtApproved(l.getString("allowed"));
					}
					else
					{
					form2.setAmtApproved(l.getString("approved"));
					}		
								
								sectionlist.add(form2);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("sectionlist", sectionlist);
			
					
				return mapping.findForward("investment");
		}
		if(reqType.equalsIgnoreCase("Claim TRLP / HRA"))
		{
			String empID="";
			List claimTRLPDet=new LinkedList();
			ClaimTRLPForm incomeTax=new ClaimTRLPForm();
			int requestNo=Integer.parseInt(request.getParameter("RequesterNo"));
			String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from Claim_TRLP as i,emp_official_info as emp "
					+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
			ResultSet rs=ad.selectQuery(getDetails);
			while(rs.next()){
				incomeTax.setRequestNo(requestNo);
				empID=rs.getString("employeeNo");
				String re[]=empDet(empID);
				incomeTax.setEmployeeNo(empID);
				incomeTax.setEmployeeName(re[0]);
				incomeTax.setLocation(re[1]);
				incomeTax.setDepartment(re[2]);
				incomeTax.setDesignation(re[3]); 
				incomeTax.setDoj(re[4]);
				incomeTax.setStaffCategory(re[8]);
				int fiscalYear1=rs.getInt("Year");
				String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
				incomeTax.setFiscalYear(fiscalYear);
				recordStatus=rs.getString("Approval_Status");
			}
			claimTRLPDet.add(incomeTax);
			request.setAttribute("claimTRLPDet", claimTRLPDet);
			
			LinkedList listofAccomDet=new LinkedList();
			
			String getAccomdList="select *,case when metro ='false' then 'No' else 'Yes' end metrocity from claim_address_details where requestNo='"+requestNo+"' and employeeNo='"+empID+"'";
			ResultSet rs1=ad.selectQuery(getAccomdList);
		
			while(rs1.next()){
				ClaimTRLPForm  clform1=new ClaimTRLPForm();
			
				clform1.setFromDate(EMicroUtils.display(rs1.getDate("fromDate")));
				clform1.setToDate(EMicroUtils.display(rs1.getDate("toDate")));
				clform1.setAmount(rs1.getInt("payableAmount"));
				clform1.setAccommodationType(rs1.getString("accomdation_Type"));
				clform1.setPanNo(rs1.getString("panNo"));
				clform1.setLandName(rs1.getString("landName"));
				clform1.setCity(rs1.getString("city"));
				clform1.setAddress1(rs1.getString("address1"));
				clform1.setAddress2(rs1.getString("address2"));
				clform1.setAddress3(rs1.getString("address3"));
				clform1.setMetrocity(rs1.getString("metrocity"));
				listofAccomDet.add(clform1);
			}
			if(listofAccomDet.size()>0){
				request.setAttribute("listofAccomDet", listofAccomDet);
			}
			
			 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Claim TRLP/UploadFiles";
			String sql1="select * from Claim_TRLP_documents where request_no='"+requestNo+"' and user_id='"+empID+"' ";
			ResultSet rs11=ad.selectQuery(sql1);
			IncomeTaxForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs11.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs11.getString("file_name"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
					leaveForm1.setId(rs11.getString("id"));
					leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
					leaveForm1.setInvRemarks(rs11.getString("description"));
					a1.add(leaveForm1);
				}
				if(a1.size()>0)
				request.setAttribute("documentDetails", a1);
			
			if(filterBy.equalsIgnoreCase("Pending")){
				request.setAttribute("approveButton", "approveButton");
				request.setAttribute("rejectButton", "rejectButton");
				}
			//set approval status
			LinkedList listApprers=new LinkedList();
		String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
				+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
		ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
		while(rsStatus.next()){
			IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
			reqForm.setPriority(rsStatus.getInt("Priority"));
			String apprvers=rsStatus.getString("Approver_Id");
			reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
			reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
			reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
			String apprDate="";
			String approveStatus="In Process";
			String comments="";
			String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
			+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
			ResultSet rsAppInfo=ad.selectQuery(apprvDate);
			while(rsAppInfo.next()){
				approveStatus=rsAppInfo.getString("Req_Status");
				if(approveStatus.equalsIgnoreCase(""))
				{
					reqForm.setApprStatus("In Process");
				}
				if(approveStatus.equalsIgnoreCase("Approved"))
				{
					String approveDate=rsAppInfo.getString("approved_date");
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					reqForm.setApprDate(approveDate);
				}if(approveStatus.equalsIgnoreCase("Rejected"))
				{
					String rejectDate=	rsAppInfo.getString("rejected_date");
					String a[]=rejectDate.split(" ");
					rejectDate=a[0];
					String b[]=rejectDate.split("-");
					rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					reqForm.setApprDate(rejectDate);
				}
				reqForm.setApprStatus(approveStatus);
				comments=rsAppInfo.getString("Comments");
				reqForm.setComments(comments);
				
			}
			listApprers.add(reqForm);
		}
		request.setAttribute("approverDetails", listApprers);
		
			return mapping.findForward("claimTRLP");
		}
		if(reqType.equalsIgnoreCase("Previous Income"))
		{
			String empID="";
			List preivousIncomeDet=new LinkedList();
			PreviousIncomeForm previousIncome=new PreviousIncomeForm();
			int requestNo=Integer.parseInt(request.getParameter("RequesterNo"));
			String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from Previous_Income as i,emp_official_info as emp "
					+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
			ResultSet rs=ad.selectQuery(getDetails);
			while(rs.next()){
				previousIncome.setRequestNo(requestNo);
				empID=rs.getString("employeeNo");			
				String re[]=empDet(empID);
				previousIncome.setEmployeeNo(empID);
				previousIncome.setEmployeeName(re[0]);
				previousIncome.setLocation(re[1]);
				previousIncome.setDepartment(re[2]);
				previousIncome.setDesignation(re[3]); 
				previousIncome.setDoj(re[4]);
				previousIncome.setStaffCategory(re[8]);
				int fiscalYear1=rs.getInt("year");
				String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
				previousIncome.setFiscalYear(fiscalYear);
				recordStatus=rs.getString("Approval_Status");
			}
			preivousIncomeDet.add(previousIncome);
			request.setAttribute("previousIncomeDet", preivousIncomeDet);
			
			LinkedList listofPrevIncDet=new LinkedList();
			
			String getAccomdList="select ID,employer,saleryType,start_Date,end_Date,total_amount,remarks from "
					+ "Previous_Income_Details where requestNo='"+requestNo+"' and employeeNo='"+empID+"'";
			ResultSet rs1=ad.selectQuery(getAccomdList);
		
			while(rs1.next()){
				PreviousIncomeForm form2=new PreviousIncomeForm();
				form2.setId(rs1.getInt("ID"));
				form2.setEmployer(rs1.getString("employer"));
			/*	form2.setSaleryID(rs1.getString("SAL_SNAME"));*/
				form2.setSaleryType(rs1.getString("saleryType"));
				/*form2.setSaleryDescription(rs1.getString("SAL_LNAME"));*/
				form2.setStartDate(EMicroUtils.display(rs1.getDate("start_Date")));
				form2.setEndDate(EMicroUtils.display(rs1.getDate("end_Date")));
				form2.setTotalAmount(rs1.getString("total_amount"));
				form2.setRemarks(rs1.getString("remarks"));
				listofPrevIncDet.add(form2);
				
			}
			
			if(listofPrevIncDet.size()>0){
				request.setAttribute("listofPrevIncDet", listofPrevIncDet);
			}
			
			
			 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/Previous Income/UploadFiles";
			String sql1="select * from Previous_Income_documents where request_no='"+requestNo+"' and user_id='"+empID+"' ";
			ResultSet rs11=ad.selectQuery(sql1);
			IncomeTaxForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs11.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs11.getString("file_name"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
					leaveForm1.setId(rs11.getString("id"));
					leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
					leaveForm1.setInvRemarks(rs11.getString("description"));
					a1.add(leaveForm1);
				}
				if(a1.size()>0)
				request.setAttribute("documentDetails", a1);
			
			if(filterBy.equalsIgnoreCase("Pending")){
				request.setAttribute("approveButton", "approveButton");
				request.setAttribute("rejectButton", "rejectButton");
				}
			//set approval status
			LinkedList listApprers=new LinkedList();
		String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
				+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
		ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
		while(rsStatus.next()){
			IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
			reqForm.setPriority(rsStatus.getInt("Priority"));
			String apprvers=rsStatus.getString("Approver_Id");
			reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
			reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
			reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
			String apprDate="";
			String approveStatus="In Process";
			String comments="";
			String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
			+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
			ResultSet rsAppInfo=ad.selectQuery(apprvDate);
			while(rsAppInfo.next()){
				approveStatus=rsAppInfo.getString("Req_Status");
				if(approveStatus.equalsIgnoreCase(""))
				{
					reqForm.setApprStatus("In Process");
				}
				if(approveStatus.equalsIgnoreCase("Approved"))
				{
					String approveDate=rsAppInfo.getString("approved_date");
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					reqForm.setApprDate(approveDate);
				}if(approveStatus.equalsIgnoreCase("Rejected"))
				{
					String rejectDate=	rsAppInfo.getString("rejected_date");
					String a[]=rejectDate.split(" ");
					rejectDate=a[0];
					String b[]=rejectDate.split("-");
					rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					reqForm.setApprDate(rejectDate);
				}
				reqForm.setApprStatus(approveStatus);
				comments=rsAppInfo.getString("Comments");
				reqForm.setComments(comments);
				
			}
			listApprers.add(reqForm);
		}
		request.setAttribute("approverDetails", listApprers);
			return mapping.findForward("previousIncome");
		}
		if(reqType.equalsIgnoreCase("External Income/Deduction"))
		{
			String empID="";
			List extIncomeDet=new LinkedList();
			ExternalIncomeForm previousIncome=new ExternalIncomeForm();
			int requestNo=Integer.parseInt(request.getParameter("RequesterNo"));
			String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from External_Income as i,emp_official_info as emp "
					+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
			ResultSet rs=ad.selectQuery(getDetails);
			while(rs.next()){
				previousIncome.setRequestNo(requestNo);
				empID=rs.getString("employeeNo");
			
				String re[]=empDet(empID);
				previousIncome.setEmployeeNo(empID);
				previousIncome.setEmployeeName(re[0]);
				previousIncome.setLocation(re[1]);
				previousIncome.setDepartment(re[2]);
				previousIncome.setDesignation(re[3]); 
				previousIncome.setDoj(re[4]);
				previousIncome.setStaffCategory(re[8]);
				int fiscalYear1=rs.getInt("year");
				String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
				previousIncome.setFiscalYear(fiscalYear);
				recordStatus=rs.getString("Approval_Status");
			}
			
			LinkedList listofExtIncomeDet=new LinkedList();
			double totalIncome=0;
			double totalDeduction=0;
			String getAccomdList="select p.ID,p.salery_Desc,p.saleryType,p.start_Date,p.end_Date,p.total_amount,p.remarks,p.total_Income,p.total_Deduction from "
					+ "External_Income_Details as p where requestNo='"+requestNo+"' and employeeNo='"+empID+"' ";
			ResultSet rs1=ad.selectQuery(getAccomdList);
			while(rs1.next()){
				ExternalIncomeForm form2=new ExternalIncomeForm();
				form2.setId(rs1.getInt("ID"));
		/*		form2.setSaleryID(rs1.getString("SAL_SNAME"));*/
				form2.setSaleryDescription(rs1.getString("salery_Desc"));
				String salaryType=rs1.getString("saleryType");
				if(salaryType.equals("I"))
					salaryType="Income";
				if(salaryType.equals("D"))
					salaryType="Deduction";
				form2.setSaleryType(salaryType);
				
				form2.setStartDate(EMicroUtils.display(rs1.getDate("start_Date")));
				form2.setEndDate(EMicroUtils.display(rs1.getDate("end_Date")));
				form2.setTotalAmount(rs1.getString("total_amount"));
				form2.setRemarks(rs1.getString("remarks"));
				form2.setTotalIncome(rs1.getDouble("total_Income"));
				form2.setTotalDeduction(rs1.getDouble("total_Deduction"));
				totalIncome=rs1.getDouble("total_Income");
				totalDeduction=rs1.getDouble("total_Deduction");
				listofExtIncomeDet.add(form2);
				
			}
			previousIncome.setTotalIncome(totalIncome);
			previousIncome.setTotalDeduction(totalDeduction);
			extIncomeDet.add(previousIncome);
			request.setAttribute("extIncomeDet", extIncomeDet);
			if(listofExtIncomeDet.size()>0){
				request.setAttribute("listofExtIncomeDet", listofExtIncomeDet);
			}
			
			 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/External Income/UploadFiles";
			String sql1="select * from External_Income_documents where request_no='"+requestNo+"' and user_id='"+empID+"' ";
			ResultSet rs11=ad.selectQuery(sql1);
			IncomeTaxForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs11.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs11.getString("file_name"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
					leaveForm1.setId(rs11.getString("id"));
					leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
					leaveForm1.setInvRemarks(rs11.getString("description"));
					a1.add(leaveForm1);
				}
				if(a1.size()>0)
				request.setAttribute("documentDetails", a1);
			
			if(filterBy.equalsIgnoreCase("Pending")){
				request.setAttribute("approveButton", "approveButton");
				request.setAttribute("rejectButton", "rejectButton");
				}
			//set approval status
			LinkedList listApprers=new LinkedList();
		String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
				+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
		ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
		while(rsStatus.next()){
			IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
			reqForm.setPriority(rsStatus.getInt("Priority"));
			String apprvers=rsStatus.getString("Approver_Id");
			reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
			reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
			reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
			String apprDate="";
			String approveStatus="In Process";
			String comments="";
			String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
			+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
			ResultSet rsAppInfo=ad.selectQuery(apprvDate);
			while(rsAppInfo.next()){
				approveStatus=rsAppInfo.getString("Req_Status");
				if(approveStatus.equalsIgnoreCase(""))
				{
					reqForm.setApprStatus("In Process");
				}
				if(approveStatus.equalsIgnoreCase("Approved"))
				{
					String approveDate=rsAppInfo.getString("approved_date");
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					reqForm.setApprDate(approveDate);
				}if(approveStatus.equalsIgnoreCase("Rejected"))
				{
					String rejectDate=	rsAppInfo.getString("rejected_date");
					String a[]=rejectDate.split(" ");
					rejectDate=a[0];
					String b[]=rejectDate.split("-");
					rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					reqForm.setApprDate(rejectDate);
				}
				reqForm.setApprStatus(approveStatus);
				comments=rsAppInfo.getString("Comments");
				reqForm.setComments(comments);
				
			}
			listApprers.add(reqForm);
		}
		request.setAttribute("approverDetails", listApprers);
			return mapping.findForward("externalIncome");
		}
		if(reqType.equalsIgnoreCase("LTA"))
		{
			String empID="";
			List LTAIncomeDet=new LinkedList();
			LTAForm ltaIncome=new LTAForm();
			int requestNo=Integer.parseInt(request.getParameter("RequesterNo"));
			String getDetails="select i.employeeNo,i.Year,emp.EMP_FULLNAME,i.Approval_Status  from LTA_Income as i,emp_official_info as emp "
					+ "where  requestNo='"+reqId+"' and i.employeeNo=emp.PERNR";
			ResultSet rs=ad.selectQuery(getDetails);
			while(rs.next()){
				ltaIncome.setRequestNo(requestNo);
				empID=rs.getString("employeeNo");		
				String re[]=empDet(empID);
				ltaIncome.setEmployeeNo(empID);
				ltaIncome.setEmployeeName(re[0]);
				ltaIncome.setLocation(re[1]);
				ltaIncome.setDepartment(re[2]);
				ltaIncome.setDesignation(re[3]); 
				ltaIncome.setDoj(re[4]);
				ltaIncome.setStaffCategory(re[8]);
				int fiscalYear1=rs.getInt("year");
				String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
				ltaIncome.setFiscalYear(fiscalYear);
				recordStatus=rs.getString("Approval_Status");
			}
			LTAIncomeDet.add(ltaIncome);
			request.setAttribute("LTAIncomeDet", LTAIncomeDet);
			
			
			LinkedList listofLTAIncomeDet=new LinkedList();
			double totalIncome=0;
			double totalDeduction=0;
			List<LTAForm> listOfLta=new LinkedList<LTAForm>();
	        String getLTASavedDetails="select * from LTA_Details where requestNo='"+reqId+"' and employeeNo='"+empID+"'";
	        ResultSet rsLTADet=ad.selectQuery(getLTASavedDetails);
		    while(rsLTADet.next()){
		    	LTAForm form2=new LTAForm();
		    	form2.setId(rsLTADet.getInt("ID"));
		    	form2.setRequestNo(rsLTADet.getInt("requestNo"));
		    	String lv_Type=rsLTADet.getString("Leave_Type");
		    	if(lv_Type.equals("3"))
		    		lv_Type="Privilage";
		    	form2.setLeaveType(lv_Type);
		    	/*form2.setTravelStartDate(EMicroUtils.display(rsLTADet.getDate("Travel_Start_Date")));*/
		    	form2.setStartDate(EMicroUtils.display(rsLTADet.getDate("start_Date")));
		    	form2.setStartDurationType(rsLTADet.getString("start_Dur"));
		    /*	form2.setTravelEndDate(EMicroUtils.display(rsLTADet.getDate("end_Date")));*/
		    	form2.setEndDurationType(rsLTADet.getString("end_Dur"));
		    	form2.setNoOfDays(rsLTADet.getString("noOf_days"));
		    	form2.setEndDate(EMicroUtils.display(rsLTADet.getDate("end_Date")));
		    	form2.setLtaAmtApplFor(rsLTADet.getString("LTA_amount_approved_for"));
		    	form2.setLtaAmtAprvdFor(rsLTADet.getString("LTA_amount_approved"));
		    	listOfLta.add(form2);
		    }
		     request.setAttribute("listOfLta", listOfLta);
		
			
			
			if(listofLTAIncomeDet.size()>0){
				request.setAttribute("listofLTAIncomeDet", listofLTAIncomeDet);
			}
			
			
			InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			String filePath=uploadFilePath+"/EMicro Files/ESS/Income Tax/LTA/UploadFiles";
			String sql1="select * from LTA_Income_documents where request_no='"+requestNo+"' and user_id='"+empID+"' ";
			ResultSet rs11=ad.selectQuery(sql1);
			IncomeTaxForm leaveForm1=null;
				ArrayList a1=new ArrayList();
				while(rs11.next()) {
					leaveForm1=new IncomeTaxForm();
					leaveForm1.setFileName(rs11.getString("file_name"));
					leaveForm1.setRequestNumber(Integer.parseInt(rs11.getString("request_no")));
					leaveForm1.setId(rs11.getString("id"));
					leaveForm1.setFileFullPath(filePath+"/"+rs11.getString("file_name"));
					leaveForm1.setInvRemarks(rs11.getString("description"));
					a1.add(leaveForm1);
				}
				if(a1.size()>0)
				request.setAttribute("documentDetails", a1);
			
			if(filterBy.equalsIgnoreCase("Pending")){
				request.setAttribute("approveButton", "approveButton");
				request.setAttribute("rejectButton", "rejectButton");
				}
			//set approval status
			LinkedList listApprers=new LinkedList();
		String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
		+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
		ResultSet rsStatus=ad.selectQuery(getApprovalStatus);
		while(rsStatus.next()){
			IncomeTaxReqForm reqForm=new IncomeTaxReqForm();
			reqForm.setPriority(rsStatus.getInt("Priority"));
			String apprvers=rsStatus.getString("Approver_Id");
			reqForm.setApproverName(rsStatus.getString("EMP_FULLNAME"));
			reqForm.setApprDept(rsStatus.getString("DPTSTXT"));
			reqForm.setApprDesg(rsStatus.getString("DSGSTXT"));
			String apprDate="";
			String approveStatus="In Process";
			String comments="";
			String apprvDate="select * from IncomeTax_Request as i where (i.Pending_Approver='"+apprvers+"' or i.Approved_Persons ='"+apprvers+"') and "
			+ "i.type='"+reqType+"' and i.Req_Id='"+reqId+"'";
			ResultSet rsAppInfo=ad.selectQuery(apprvDate);
			while(rsAppInfo.next()){
				approveStatus=rsAppInfo.getString("Req_Status");
				if(approveStatus.equalsIgnoreCase(""))
				{
					reqForm.setApprStatus("In Process");
				}
				if(approveStatus.equalsIgnoreCase("Approved"))
				{
					String approveDate=rsAppInfo.getString("approved_date");
					String a[]=approveDate.split(" ");
					approveDate=a[0];
					String b[]=approveDate.split("-");
					approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					reqForm.setApprDate(approveDate);
				}if(approveStatus.equalsIgnoreCase("Rejected"))
				{
					String rejectDate=	rsAppInfo.getString("rejected_date");
					String a[]=rejectDate.split(" ");
					rejectDate=a[0];
					String b[]=rejectDate.split("-");
					rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
					reqForm.setApprDate(rejectDate);
				}
				reqForm.setApprStatus(approveStatus);
				comments=rsAppInfo.getString("Comments");
				reqForm.setComments(comments);
				
			}
			listApprers.add(reqForm);
		}
		request.setAttribute("approverDetails", listApprers);
		
			return mapping.findForward("ltaIncome");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward("investment");
	}
	private void setFilterType(String requestType, IncomeTaxReqForm statusForm){
		LinkedList defaultFilter=new LinkedList();
		defaultFilter.add(0,"Pending");
		defaultFilter.add(1,"Approved");
		defaultFilter.add(2,"Rejected");
		LinkedList filter=new LinkedList();
		System.out.println("size -> "+defaultFilter.size());
		for(int i = 0; i < defaultFilter.size(); i++){
			String selectedFilter = defaultFilter.get(i).toString();
			if(requestType.equalsIgnoreCase(selectedFilter)){
				System.out.println("if "+selectedFilter);
				filter.addFirst(selectedFilter);
			}
			else{
				System.out.println("else "+selectedFilter);
				filter.add(selectedFilter);
			}
		}
		statusForm.setFilterType(filter);
		
	}
	private void setAllCount(IncomeTaxReqForm mailForm,int totalCount, int sCount, int eCount){
		mailForm.setTotalAppCount(totalCount);
		mailForm.setStartAppCount(sCount);
		mailForm.setEndAppCount(eCount);
    }
	public ActionForward approveRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IncomeTaxReqForm approvalsForm=(IncomeTaxReqForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String selectedReq[]=approvalsForm.getSelectedRequestNo();
		System.out.println("total="+selectedReq.length);
		String reqType=approvalsForm.getReqRequstType();
		approvalsForm.setReqRequstType(reqType);
		String status=approvalsForm.getSelectedFilter();
		String pendingApprovers="";
		approvalsForm.setSelectedFilter(status);
		if(reqType.equalsIgnoreCase("Apply Investment"))
		{
			for(int i=0;i<selectedReq.length;i++)
			{
				try{
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
				String dateNow = ft.format(dNow);
				String reqId=selectedReq[i];
				
				String empID="";
				String submit_date="";
				String reqDetails="select * from investment where requestNo='"+reqId+"'";
				ResultSet rsDetails=ad.selectQuery(reqDetails);
				while(rsDetails.next()){
					empID=rsDetails.getString("employeenumber");
					submit_date=rsDetails.getString("Creation_Date");
				}
				int priorityCout=0;
				int totalApproverCount=0;
				String checkESSApproverList="select COUNT(*) from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as "
						+ "emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Req_Type='"+reqType+"'";
				ResultSet rsCheckInEss=ad.selectQuery(checkESSApproverList);
				while(rsCheckInEss.next())
				{
					totalApproverCount=rsCheckInEss.getInt(1);
				}
				int approverPriority=0;
				String getApprPriority="select Priority from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info as emp,"
				+ "Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Approver_ID='"+user.getEmployeeNo()+"' and Req_Type='Apply Investment'";
				ResultSet rsApprPriority=ad.selectQuery(getApprPriority);
				while(rsApprPriority.next())
				{
					approverPriority=rsApprPriority.getInt("Priority");
				}
				if(totalApproverCount==approverPriority)
				{
					//last approver
					String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments=''," +
				            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Apply Investment'";
							 int upd=ad.SqlExecuteUpdate(updateSql);
					//update lastapprover and pending approver
					if(upd>0){		 
					String updateApprover="update investment set Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"',approved_date='"+dateNow+"',"
					+ "comments='',Approval_Status='Approved' where requestNo='"+reqId+"' and employeenumber='"+empID+"'";
					int i1=ad.SqlExecuteUpdate(updateApprover);
					System.out.println("i1="+i1);
					
					}
				}else{
					String nextApprover="";
					String getNextApprDetails="select Approver_ID from IncomeTax_Approvers where LOCATION_ID=(select loc.LOCID from emp_official_info "
					+ "as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) and Priority='"+(approverPriority+1)+"' and "
					+ "Req_Type='Apply Investment'";
		            ResultSet rsNextApprDetails=ad.selectQuery(getNextApprDetails);
		            while(rsNextApprDetails.next())
		            {
		            	nextApprover=rsNextApprDetails.getString("Approver_ID");
		            }
		            String updateSql= "update IncomeTax_Request set Approved_Persons='"+user.getEmployeeNo()+"', Last_approver='"+user.getEmployeeNo()+"',Pending_approver='',Comments=''," +
		            "approved_date='"+dateNow+"',rejected_date='',Req_Status='Approved' where Req_Id='"+reqId+"' and Pending_approver='"+user.getEmployeeNo()+"' and type='Apply Investment'";
					 int upd=ad.SqlExecuteUpdate(updateSql);
					 if(upd>0)
					 {
						String saveRecReq="insert into IncomeTax_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,type) values (";
						saveRecReq = saveRecReq + "'"+reqId+"','Income Tax','"+empID+"','"+submit_date+"','Pending','','"+nextApprover+"','No','"+empID+"','Apply Investment')";
						  i=ad.SqlExecuteUpdate(saveRecReq);
						
						 String getNextApprovers="select emp.EMP_FULLNAME,Approver_Id from  IncomeTax_Approvers as mat,emp_official_info AS emp where "
						 + "mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' and loc.LOCATION_CODE=emp.LOCID) "
						 + "AND  Req_Type='Apply Investment'  and mat.Approver_Id=emp.PERNR and Priority>'"+approverPriority+"' order by Priority";
						 ResultSet rsPendignApp=ad.selectQuery(getNextApprovers);
							while(rsPendignApp.next())
							{  
								pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
							}
						//update lastapprover and pending approver
						String updateApprover="update investment set Last_approver='"+user.getFullName()+"',Pending_approver='"+pendingApprovers+"',approved_date='"+dateNow+"',"
						+ "comments='' where requestNo='"+reqId+"' and employeenumber='"+empID+"'";
						int i1=ad.SqlExecuteUpdate(updateApprover);
						
					 }
				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		pendingRecords(mapping, form, request, response);
		return mapping.findForward("display");
	}

	public ActionForward pendingRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IncomeTaxReqForm approvalsForm=(IncomeTaxReqForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		  int  startRecord=0;
		  int  endRecord=0;
		  String reqType=approvalsForm.getReqRequstType();
			String type=approvalsForm.getSelectedFilter();
			int totalRecords = getCountForTable(approvalsForm.getSelectedFilter(),user.getEmployeeNo(),approvalsForm.getReqRequstType());
		
	 if(totalRecords>10)
	 {
		 approvalsForm.setTotalRecords(totalRecords);
	 startRecord=1;
	 endRecord=10;
	 approvalsForm.setStartRecord(1);
	 approvalsForm.setEndRecord(10);
		request.setAttribute("disablePreviousButton", "disablePreviousButton");
	 request.setAttribute("displayRecordNo", "displayRecordNo");
	 request.setAttribute("nextButton", "nextButton");
	 }else
	 {
		  startRecord=1;
		  endRecord=totalRecords;
		  approvalsForm.setTotalRecords(totalRecords);
		  approvalsForm.setStartRecord(1);
		  approvalsForm.setEndRecord(totalRecords); 
	 }
	 if(totalRecords>0)
	 {
		 
		 if(reqType.equalsIgnoreCase("Medical"))
		 {
				List medList=new LinkedList();
				String getInvList="";
				
				if(approvalsForm.getChooseKeyword()==null)
				{
					approvalsForm.setChooseKeyword("");
				}
				if(!approvalsForm.getChooseKeyword().equalsIgnoreCase(""))
				{

					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"'  and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"'  and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"'  and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')  and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					
				}
				else
				{
				if(type.equalsIgnoreCase("Pending")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
				}
				if(type.equalsIgnoreCase("Approved")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
					
				 			 	
				}
				if(type.equalsIgnoreCase("Rejected")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
					
									
				}
				if(type.equalsIgnoreCase("All")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Medical_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						}
				}
				
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					LTAForm trlpForm=new LTAForm();
					trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
					trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
					trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
					trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
					trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
					trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
					trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
					trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
					medList.add(trlpForm);
				}
				request.setAttribute("medlist", medList);
				if(medList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					if(medList.size()==0){
						request.setAttribute("no Investment records", "no Investment records");
						approvalsForm.setMessage3("No Records Found..");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			 
		 }
		 
		 if(reqType.equalsIgnoreCase("Apply Investment"))
		 {
			List invList=new LinkedList();
			String getInvList="";
			if(approvalsForm.getChooseKeyword()==null)
			{
				approvalsForm.setChooseKeyword("");
			}
			if(!approvalsForm.getChooseKeyword().equalsIgnoreCase(""))
			{

				if(type.equalsIgnoreCase("Pending")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
				 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
				}
				if(type.equalsIgnoreCase("Approved")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
				 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					 	
				}
				if(type.equalsIgnoreCase("Rejected")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
				 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
				}
				if(type.equalsIgnoreCase("All")){
			 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
			 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
			 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
			 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				}
				
					
			}
			else
			{
			if(type.equalsIgnoreCase("Pending")){
			 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
			 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
			 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
			 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
			
			}
			if(type.equalsIgnoreCase("Approved")){
			 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
			 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
			 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
			 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				 	
			}
			if(type.equalsIgnoreCase("Rejected")){
			 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
			 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
			 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
			 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
			}
			if(type.equalsIgnoreCase("All")){
		 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
		 + "inv.fiscal_year,inv.total_amount,CONVERT(varchar(10),inv.Creation_Date,103)+' '+ CONVERT(varchar(5),inv.Creation_Date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,investment as inv "
		 + "where type='Apply Investment' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
		 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
			}
			
			}
			ResultSet rsInvList=ad.selectQuery(getInvList);
			try{
			while(rsInvList.next()){
				IncomeTaxForm investment=new IncomeTaxForm();
				investment.setRequestNumber(rsInvList.getInt("Req_Id"));
				investment.setEmployeeNo(rsInvList.getString("PERNR"));
				investment.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
				investment.setDepartment(rsInvList.getString("DPTSTXT"));
				investment.setFiscalYear(rsInvList.getString("fiscal_year"));
				investment.setTotalAmount(rsInvList.getString("total_amount"));
				investment.setSubmitDate(rsInvList.getString("Creation_Date1"));
				investment.setApprovalStatus(rsInvList.getString("Req_Status"));
				investment.setLastApprover(rsInvList.getString("Last_approver"));
				investment.setPendingApprover(rsInvList.getString("Pending_approver"));
				invList.add(investment);
			}
			request.setAttribute("investmentList", invList);
			if(invList.size()>0){
				if(type.equalsIgnoreCase("Pending")){
					request.setAttribute("displayButton", "displayButton");
					}
				}
				if(invList.size()==0){
					request.setAttribute("no Investment records", "no Investment records");
					approvalsForm.setMessage3("No Records Found..");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		 }
		 if(reqType.equals("Claim TRLP / HRA"))
		 {
				List tRLPList=new LinkedList();
				String getInvList="";
				if(approvalsForm.getChooseKeyword()==null)
				{
					approvalsForm.setChooseKeyword("");
				}
				if(!approvalsForm.getChooseKeyword().equalsIgnoreCase(""))
				{

					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
					 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
								 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
								 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
								 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					
				}
				else
				{
				if(type.equalsIgnoreCase("Pending")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
				 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
				}
				if(type.equalsIgnoreCase("Approved")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
							 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
					
				 			 	
				}
				if(type.equalsIgnoreCase("Rejected")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
							 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
					
									
				}
				if(type.equalsIgnoreCase("All")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Claim_TRLP as inv "
							 + "where type='Claim TRLP / HRA' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						}
				}
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					ClaimTRLPForm trlpForm=new ClaimTRLPForm();
					trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
					trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
					trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
					trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
					trlpForm.setFiscalYear(rsInvList.getString("Year"));
					trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
					trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
					trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
					trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
					tRLPList.add(trlpForm);
				}
				request.setAttribute("tRLPList", tRLPList);
				if(tRLPList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					if(tRLPList.size()==0){
						request.setAttribute("no Investment records", "no Investment records");
						approvalsForm.setMessage3("No Records Found..");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			 }
		 if(reqType.equals("Previous Income"))
		 {

				List prevIncomeist=new LinkedList();
				String getInvList="";
				
				if(approvalsForm.getChooseKeyword()==null)
				{
					approvalsForm.setChooseKeyword("");
				}
				if(!approvalsForm.getChooseKeyword().equalsIgnoreCase(""))
				{

					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					
					
				}
				else
				{
				if(type.equalsIgnoreCase("Pending")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
				}
				if(type.equalsIgnoreCase("Approved")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
					
				 			 	
				}
				if(type.equalsIgnoreCase("Rejected")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
					
									
				}
				if(type.equalsIgnoreCase("All")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,Previous_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						}
				
				}
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					PreviousIncomeForm trlpForm=new PreviousIncomeForm();
					trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
					trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
					trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
					trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
					trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
					trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
					trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
					trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
					prevIncomeist.add(trlpForm);
				}
				request.setAttribute("prevIncomeist", prevIncomeist);
				if(prevIncomeist.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					if(prevIncomeist.size()==0){
						request.setAttribute("no Investment records", "no Investment records");
						approvalsForm.setMessage3("No Records Found..");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			 
		 }
		 if(reqType.equalsIgnoreCase("External Income/Deduction"))
		 {
				List externalIncomeist=new LinkedList();
				String getInvList="";
				
				if(approvalsForm.getChooseKeyword()==null)
				{
					approvalsForm.setChooseKeyword("");
				}
				if(!approvalsForm.getChooseKeyword().equalsIgnoreCase(""))
				{
					

					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					
					
				}
				
				else
				{
				if(type.equalsIgnoreCase("Pending")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
				}
				if(type.equalsIgnoreCase("Approved")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
					
				 			 	
				}
				if(type.equalsIgnoreCase("Rejected")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
					
									
				}
				if(type.equalsIgnoreCase("All")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,External_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						}
				
				}
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					ExternalIncomeForm trlpForm=new ExternalIncomeForm();
					trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
					trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
					trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
					trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
					trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
					trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
					trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
					trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
					externalIncomeist.add(trlpForm);
				}
				request.setAttribute("externalIncomeist", externalIncomeist);
				if(externalIncomeist.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					if(externalIncomeist.size()==0){
						request.setAttribute("no Investment records", "no Investment records");
						approvalsForm.setMessage3("No Records Found..");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			 
		 }
		 if(reqType.equalsIgnoreCase("LTA"))
		 {
				List ltaList=new LinkedList();
				String getInvList="";
				
				if(approvalsForm.getChooseKeyword()==null)
				{
					approvalsForm.setChooseKeyword("");
				}
				if(!approvalsForm.getChooseKeyword().equalsIgnoreCase(""))
				{

					
					if(type.equalsIgnoreCase("Pending")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
					 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
					 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
					 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
					}
					if(type.equalsIgnoreCase("Approved")){
						 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
								
						
					 			 	
					}
					if(type.equalsIgnoreCase("Rejected")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"' and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						
										
					}
					if(type.equalsIgnoreCase("All")){
						getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
								 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
								 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
								 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"') and i.Requester_Id='"+approvalsForm.getChooseKeyword()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
						
							}
					
				}
				else
				{
					
				if(type.equalsIgnoreCase("Pending")){
				 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
				 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
				 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
				 + "i.Pending_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
				}
				if(type.equalsIgnoreCase("Approved")){
					 getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Last_Approver='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
							
					
				 			 	
				}
				if(type.equalsIgnoreCase("Rejected")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "i.Approved_Persons='"+user.getEmployeeNo()+"' and i.Req_Status='"+type+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
				
					
									
				}
				if(type.equalsIgnoreCase("All")){
					getInvList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.Req_Id desc) AS RowNum,i.Req_Id,e.PERNR,e.EMP_FULLNAME,dept.DPTSTXT,"
							 + "inv.Year,CONVERT(varchar(10),inv.submit_date,103)+' '+ CONVERT(varchar(5),inv.submit_date,108) as Creation_Date1,i.Req_Status,inv.Last_approver,inv.Pending_approver from IncomeTax_Request as i,emp_official_info as e,DEPARTMENT as dept,DESIGNATION as desg,LTA_Income as inv "
							 + "where type='"+reqType+"' and e.PERNR=i.Requester_Id and e.DPTID=dept.DPTID and e.DSGID=desg.DSGID and inv.requestNo=i.Req_Id and "
							 + "(i.Last_Approver ='"+user.getEmployeeNo()+"' or i.Pending_Approver = '"+user.getEmployeeNo()+"')) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord+" order by Req_Id desc";
					
						}
				}
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					LTAForm trlpForm=new LTAForm();
					trlpForm.setRequestNo(rsInvList.getInt("Req_Id"));
					trlpForm.setEmployeeNo(rsInvList.getString("PERNR"));
					trlpForm.setEmployeeName(rsInvList.getString("EMP_FULLNAME"));
					trlpForm.setDepartment(rsInvList.getString("DPTSTXT"));
					trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(rsInvList.getString("Creation_Date1"));
					trlpForm.setApprovalStatus(rsInvList.getString("Req_Status"));
					trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
					trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
					ltaList.add(trlpForm);
				}
				request.setAttribute("ltaList", ltaList);
				if(ltaList.size()>0){
					if(type.equalsIgnoreCase("Pending")){
						request.setAttribute("displayButton", "displayButton");
						}
					}
					if(ltaList.size()==0){
						request.setAttribute("no Investment records", "no Investment records");
						approvalsForm.setMessage3("No Records Found..");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			 
		 }
	 }else{
		 	request.setAttribute("No Records", "No Records");
			approvalsForm.setMessage3("No Records Found..");
	 }
			return mapping.findForward("display");
	}
	public int getCountForTable(String reqStatus, String fullName, String reqType){
    	int rowCount = 0;
    	EssDao ad=new EssDao();
    	String reqList="select count(*) from IncomeTax_Request where Pending_approver='"+fullName+"' and Req_Status='Pending' and type='"+reqType+"'";
    	if(reqStatus.equalsIgnoreCase("Approved")){
			reqList="select count(*) from IncomeTax_Request where Last_Approver='"+fullName+"' and Req_Status='Approved' and type='"+reqType+"'";
		}
    	else if(reqStatus.equalsIgnoreCase("Created")){
			reqList="select count(*) from IncomeTax_Request where Last_Approver='"+fullName+"' and Req_Status='Created' and type='"+reqType+"'";
		}
		else if(reqStatus.equalsIgnoreCase("Rejected")){
			reqList="select count(*) from IncomeTax_Request where Req_Status='"+reqStatus+"' and Last_Approver='"+fullName+"'  and type='"+reqType+"' ";
		}
		else if(reqStatus.equalsIgnoreCase("Cancelled")){
			reqList="select count(*) from IncomeTax_Request where Req_Status='"+reqStatus+"' and Last_Approver='"+fullName+"'  and type='"+reqType+"' ";
		}
		else if(reqStatus.equalsIgnoreCase("All")){
			reqList="select count(*) from IncomeTax_Request where (Last_Approver ='"+fullName+"' or Pending_Approver = '"+fullName+"')  and type='"+reqType+"'";
		}
    	try
    	{
    		ResultSet countrs=ad.selectQuery(reqList);
    		while(countrs.next()) {
    			rowCount=Integer.parseInt(countrs.getString(1));
    		}
    	}
    	catch(SQLException se){
    		System.out.println("Exception @ getting count");
    		se.printStackTrace();
    	}
    	return rowCount;
    }
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		IncomeTaxReqForm taxForm=(IncomeTaxReqForm)form;
		return mapping.findForward("display");
	}
	
	public ActionForward displayReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad=new EssDao();
		IncomeTaxReqForm taxForm=(IncomeTaxReqForm)form;
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select year from fiscal_year order by id desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("year"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				ResultSet rs11 = ad.selectQuery("select LOCID," +
						"LOCNAME,location_code from location "); 
						try {
							while(rs11.next()) {
								locationList.add(rs11.getString("location_code"));
								locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
								
							}
							rs11.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						taxForm.setLocationIdList(locationList);
						taxForm.setLocationLabelList(locationLabelList);
						
						
						
						//repgrp
						ArrayList repgrptList=new ArrayList();
						ArrayList repgrpLabelList=new ArrayList();
						ResultSet reprs15 = ad.selectQuery("select Grp_ID," +
								"Grp_LTXT from REPORTING_GROUP   order by Grp_LTXT "); 
								try {
									while(reprs15.next()) {
										repgrptList.add(reprs15.getString("Grp_ID"));
										repgrpLabelList.add(reprs15.getString("Grp_LTXT"));
									}
									reprs15.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								taxForm.setRepgrpList(repgrptList);
								taxForm.setRepgrpLabelList(repgrpLabelList);
				
				taxForm.setYearList(yearList);
		return mapping.findForward("displayReport");
	}
	
	public ActionForward displayStatusReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad=new EssDao();
		IncomeTaxReqForm taxForm=(IncomeTaxReqForm)form;
		System.out.println(taxForm.getFiscalYear());
		taxForm.setFiscalYear(taxForm.getFiscalYear());
		ArrayList yearList=new ArrayList();
		ResultSet rs17 = ad.selectQuery("select year(getdate())-1 as yearlist    order by 1 desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("yearlist"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Date dNow = new Date( );
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
				String exportdate=ft.format(dNow);

				response.setHeader("Content-Disposition", "inline; filename="+exportdate+"_IncomeTax_Status_report.xls");
				
				
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				ResultSet rs11 = ad.selectQuery("select LOCID," +
						"LOCNAME,location_code from location "); 
						try {
							while(rs11.next()) {
								locationList.add(rs11.getString("location_code"));
								locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
								
							}
							rs11.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						taxForm.setLocationIdList(locationList);
						taxForm.setLocationLabelList(locationLabelList);
					
						//repgrp
						ArrayList repgrptList=new ArrayList();
						ArrayList repgrpLabelList=new ArrayList();
						ResultSet reprs15 = ad.selectQuery("select Grp_ID," +
								"Grp_LTXT from REPORTING_GROUP   order by Grp_LTXT "); 
								try {
									while(reprs15.next()) {
										repgrptList.add(reprs15.getString("Grp_ID"));
										repgrpLabelList.add(reprs15.getString("Grp_LTXT"));
									}
									reprs15.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								taxForm.setRepgrpList(repgrptList);
								taxForm.setRepgrpLabelList(repgrpLabelList);
								
								
								/////////status report
								
								
								 //location
							    
							    String ACtualloc="";
							    if(taxForm.getLocArray()!=null)
							    {	
							    String[] loc = taxForm.getLocArray();
							    
							    StringBuffer reqreploc = new StringBuffer();
								for (int i = 0; i < loc.length; i++) {
									reqreploc.append("'"+loc[i]+"'" + ",");
								}
								ACtualloc= reqreploc.substring(0, reqreploc.length() - 1).toString();
							    }
								
								 //reporting
							    
							    String ACtualrepgrp="";
							    if(taxForm.getRepgrpArray()!=null)
							    {	
							    String[] repgrp = taxForm.getRepgrpArray();
							    
							    StringBuffer reqrepgrp = new StringBuffer();
								for (int i = 0; i < repgrp.length; i++) {
									reqrepgrp.append("'"+repgrp[i]+"'" + ",");
								}
								ACtualrepgrp= reqrepgrp.substring(0, reqrepgrp.length() - 1).toString();
							    }
							    String sp="";
							   
							    sp="select emp_official_info.pernr,emp_official_info.EMP_FULLNAME, DEPARTMENT.DPTSTXT,DESIGNATION.DSGSTXT,"
+ "case when count(investment.employeenumber)>0 then 'Yes' else 'No' end inv,case when count(Claim_TRLP.employeeNo)>0 then 'Yes' else 'No' end hra ,"
+ "case when count(Previous_Income.employeeNo)>0 then 'Yes' else 'No' end prev ,case when count(External_Income.employeeNo)>0 then 'Yes' else 'No' end ext ,"
+ "case when count(LTA_Income.employeeNo)>0 then 'Yes' else 'No' end lta ,case when count(Medical_Income.employeeNo)>0 then 'Yes' else 'No' end med"
+ " from DEPARTMENT,DESIGNATION ,emp_official_info left outer join investment on  investment.employeenumber=emp_official_info.PERNR and investment.fiscal_year="+taxForm.getFiscalYear()+" "
+ " left outer join Claim_TRLP on  Claim_TRLP.employeeNo=emp_official_info.PERNR  and Claim_TRLP.year="+taxForm.getFiscalYear()+" left outer join Previous_Income on  "
+ "Previous_Income.employeeNo=emp_official_info.PERNR  and Previous_Income.year="+taxForm.getFiscalYear()+"  left outer join External_Income on  "
+ "External_Income.employeeNo=emp_official_info.PERNR  and External_Income.year="+taxForm.getFiscalYear()+"  left outer join LTA_Income on  LTA_Income.employeeNo=emp_official_info.PERNR"
+ " and LTA_Income.year="+taxForm.getFiscalYear()+"  left outer join Medical_Income on  Medical_Income.employeeNo=emp_official_info.PERNR and Medical_Income.year="+taxForm.getFiscalYear()+"  where "
+ " DEPARTMENT.DPTID=emp_official_info.DPTID and DESIGNATION.DSGID=emp_official_info.DSGID  ";
							  
							    if(!ACtualloc.equalsIgnoreCase(""))
							    {
							    	sp=sp+" and emp_official_info.locid in ("+ACtualloc+")";
							    }
							    
							    if(!ACtualrepgrp.equalsIgnoreCase(""))
							    {
							    	sp=sp+" and emp_official_info.Reporting_Grp in ("+ACtualrepgrp+")";
							    }
							    
							    sp=sp+" and active=1 group by emp_official_info.pernr,emp_official_info.EMP_FULLNAME,DPTSTXT,DSGSTXT";
							    ArrayList hp=new ArrayList();
							    ResultSet rs=ad.selectQuery(sp);
							    try {
									while(rs.next())
									{
										IncomeTaxForm re=new IncomeTaxForm();
										re.setEmployeeNo(rs.getString("pernr"));
										re.setEmployeeName(rs.getString("EMP_FULLNAME"));
										re.setDepartment(rs.getString("DPTSTXT"));
										re.setDesignation(rs.getString("DSGSTXT"));
										re.setInvCode(rs.getString("inv"));
										re.setInvDes(rs.getString("hra"));
										re.setInvLimit(rs.getString("prev"));
										re.setInvRemarks(rs.getString("ext"));
										re.setInvSection(rs.getString("lta"));
										re.setInvStatus(rs.getString("med"));
										hp.add(re);
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							    request.setAttribute("status", hp);
							    
								
								
		return mapping.findForward("displayStatusReport");	
	}
	
}
