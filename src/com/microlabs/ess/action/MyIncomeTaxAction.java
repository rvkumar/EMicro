package com.microlabs.ess.action;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.ClaimTRLPForm;
import com.microlabs.ess.form.ExternalIncomeForm;
import com.microlabs.ess.form.IncomeTaxForm;
import com.microlabs.ess.form.LTAForm;
import com.microlabs.ess.form.MyIncomeTaxForm;
import com.microlabs.ess.form.PreviousIncomeForm;
import com.microlabs.hr.form.IncomeTaxReqForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class MyIncomeTaxAction extends DispatchAction{
	
	public ActionForward displayPending(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		//Created by Pradeep .Date:15-11-2016
		return mapping.findForward("displayPending");
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		//Created by Pradeep .Date:15-11-2016
		   MyIncomeTaxForm approvalsForm=(MyIncomeTaxForm)form;
			
			String reqId = request.getParameter("reqId");
			String reqType = approvalsForm.getReqRequstType();
			approvalsForm.setRequestNo(reqId);
			approvalsForm.setRequestType(reqType);
			
			
			  approvalsForm.setReqRequstType(reqType);
				String type=approvalsForm.getSelectedFilter();
				approvalsForm.setSelectedFilter(type);
			HttpSession session=request.getSession();

			UserInfo user=(UserInfo)session.getAttribute("user");
			EssDao ad=new EssDao();
			
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
			String scount = Integer.toString(approvalsForm.getStartRecord());
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
					 List medlist=new LinkedList();
						String getInvList="";
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Medical_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Medical_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						
						
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							LTAForm trlpForm=new LTAForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
							trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
							trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
							medlist.add(trlpForm);
						}
						request.setAttribute("medlist", medlist);
					if(medlist.size()==0){
						request.setAttribute("no it records", "no it records");
						approvalsForm.setMessage3("No Records Found..");
					}
					request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton");
					if(medlist.size()<10)
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
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from investment as i where employeenumber='"+user.getEmployeeNo()+"' and Approval_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
					if(type.equalsIgnoreCase("All"))
					{
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from investment as i where employeenumber='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

					}
					
					
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						IncomeTaxForm investment=new IncomeTaxForm();
						investment.setRequestNumber(rsInvList.getInt("requestNo"));
						
						investment.setFiscalYear(rsInvList.getString("fiscal_year"));
						investment.setTotalAmount(rsInvList.getString("total_amount"));
						investment.setSubmitDate(EMicroUtils.display(rsInvList.getDate("Creation_Date")));
						investment.setApprovalStatus(rsInvList.getString("Approval_Status"));
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
						
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Claim_TRLP as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Claim_TRLP as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							ClaimTRLPForm trlpForm=new ClaimTRLPForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
		                    trlpForm.setFiscalYear(rsInvList.getString("Year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
						
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Previous_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Previous_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							PreviousIncomeForm trlpForm=new PreviousIncomeForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
		                    trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
						
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from External_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from External_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							ExternalIncomeForm trlpForm=new ExternalIncomeForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
							
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
						
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from LTA_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from LTA_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							LTAForm trlpForm=new LTAForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
		
		
		return mapping.findForward("displayPending");
		
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		//Created by Pradeep .Date:15-11-2016
		
      MyIncomeTaxForm approvalsForm=(MyIncomeTaxForm)form;
		
		String reqId = request.getParameter("reqId");
		String reqType = approvalsForm.getReqRequstType();
		approvalsForm.setRequestNo(reqId);
		approvalsForm.setRequestType(reqType);
		
		
		  approvalsForm.setReqRequstType(reqType);
			String type=approvalsForm.getSelectedFilter();
			approvalsForm.setSelectedFilter(type);
		HttpSession session=request.getSession();

		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String scount = Integer.toString(approvalsForm.getStartRecord());
		
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
					
					List medlist=new LinkedList();
					String getInvList="";
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Medical_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
					if(type.equalsIgnoreCase("All"))
					{
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Medical_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

					}
					
					
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						LTAForm trlpForm=new LTAForm();
						trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
						trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
						trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
						trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
						trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
						medlist.add(trlpForm);
					}
					request.setAttribute("medlist", medlist);
					if(medlist.size()==0){
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
						if(medlist.size()<10)
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
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from investment as i where employeenumber='"+user.getEmployeeNo()+"' and Approval_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
					if(type.equalsIgnoreCase("All"))
					{
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from investment as i where employeenumber='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

					}
					
					
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						IncomeTaxForm investment=new IncomeTaxForm();
						investment.setRequestNumber(rsInvList.getInt("requestNo"));
						
						investment.setFiscalYear(rsInvList.getString("fiscal_year"));
						investment.setTotalAmount(rsInvList.getString("total_amount"));
						investment.setSubmitDate(EMicroUtils.display(rsInvList.getDate("Creation_Date")));
						investment.setApprovalStatus(rsInvList.getString("Approval_Status"));
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
						
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Claim_TRLP as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Claim_TRLP as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							ClaimTRLPForm trlpForm=new ClaimTRLPForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
		                    trlpForm.setFiscalYear(rsInvList.getString("Year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
						
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Previous_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Previous_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							PreviousIncomeForm trlpForm=new PreviousIncomeForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
		                    trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
						
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from External_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from External_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							ExternalIncomeForm trlpForm=new ExternalIncomeForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
							
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
						
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from LTA_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
						if(type.equalsIgnoreCase("All"))
						{
							getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from LTA_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

						}
						ResultSet rsInvList=ad.selectQuery(getInvList);
						try{
						while(rsInvList.next()){
							LTAForm trlpForm=new LTAForm();
							trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
							trlpForm.setFiscalYear(rsInvList.getString("year"));
							trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
							trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
			 
			  
			  
		  }catch(Exception e){
				e.printStackTrace();
			}
		 
		  
		
		return mapping.findForward("displayPending");
		
	}
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//Created by Pradeep .Date:15-11-2016
		
     MyIncomeTaxForm approvalsForm=(MyIncomeTaxForm)form;
		
		String reqId = request.getParameter("reqId");
		String reqType = approvalsForm.getReqRequstType();
		approvalsForm.setRequestNo(reqId);
		approvalsForm.setRequestType(reqType);
		
		
		  approvalsForm.setReqRequstType(reqType);
			String type=approvalsForm.getSelectedFilter();
			approvalsForm.setSelectedFilter(type);
		HttpSession session=request.getSession();

		UserInfo user=(UserInfo)session.getAttribute("user");
		EssDao ad=new EssDao();
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String scount = Integer.toString(approvalsForm.getStartRecord());
		
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
	    String recordStatus="";
	    
	    try{
	    	if(reqType.equalsIgnoreCase("Medical"))
			 {
	    		List medlist=new LinkedList();
				String getInvList="";
				getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Medical_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
				if(type.equalsIgnoreCase("All"))
				{
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Medical_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

				}
				
				
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					LTAForm trlpForm=new LTAForm();
					trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
					trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
					trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
					trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
					trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
					medlist.add(trlpForm);
				}
				request.setAttribute("medlist", medlist);
				if(medlist.size()!=0)
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
			 if(medlist.size()<10)
			 {
				 approvalsForm.setTotalRecords(totalRecords);
				 approvalsForm.setStartRecord(startRecord);
				 approvalsForm.setEndRecord(startRecord+medlist.size()-1);
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
				getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from investment as i where employeenumber='"+user.getEmployeeNo()+"' and Approval_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
				if(type.equalsIgnoreCase("All"))
				{
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from investment as i where employeenumber='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

				}
				
				
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					IncomeTaxForm investment=new IncomeTaxForm();
					investment.setRequestNumber(rsInvList.getInt("requestNo"));
					
					investment.setFiscalYear(rsInvList.getString("fiscal_year"));
					investment.setTotalAmount(rsInvList.getString("total_amount"));
					investment.setSubmitDate(EMicroUtils.display(rsInvList.getDate("Creation_Date")));
					investment.setApprovalStatus(rsInvList.getString("Approval_Status"));
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
					
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Claim_TRLP as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
					if(type.equalsIgnoreCase("All"))
					{
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Claim_TRLP as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

					}
					
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						ClaimTRLPForm trlpForm=new ClaimTRLPForm();
						trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
	                    trlpForm.setFiscalYear(rsInvList.getString("Year"));
						trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
						trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
					
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Previous_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
					if(type.equalsIgnoreCase("All"))
					{
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Previous_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

					}
					
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						PreviousIncomeForm trlpForm=new PreviousIncomeForm();
						trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
	                    trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
						trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
					
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from External_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
					if(type.equalsIgnoreCase("All"))
					{
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from External_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

					}
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						ExternalIncomeForm trlpForm=new ExternalIncomeForm();
						trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
						
						trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
						trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
					
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from LTA_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
					if(type.equalsIgnoreCase("All"))
					{
						getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from LTA_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

					}
					ResultSet rsInvList=ad.selectQuery(getInvList);
					try{
					while(rsInvList.next()){
						LTAForm trlpForm=new LTAForm();
						trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
						trlpForm.setFiscalYear(rsInvList.getString("year"));
						trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
						trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
		 }catch(Exception e){
			e.printStackTrace();
		}
	    
		 if(endRecord==totalRecords)
		 {
			 request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
		 }
		 request.setAttribute("displayRecordNo", "displayRecordNo");
		
		

		}
		
		return mapping.findForward("displayPending");
		
	}
	
	private String[] empDet( String empno) {
		//Created by Pradeep .Date:15-11-2016
		
 		EssDao ad = new EssDao();
		String emp=" select emp.eMP_FULLNAME,emp.lOCID,dep.DPTSTXT,desg.DSGSTXT,convert(nvarchar(10),emp.doj,103) as doj,datediff(year,dob,getdate()) as age,emp.Email_id, case when emp.sex='M' then 'Male' else 'Female' end as gender from Emp_official_info emp "
				+ " , Department dep ,Designation desg  where emp.dPTID = dep.DPTID and emp.pERNR='"+empno+"' "
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
      


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
		}
	
	public ActionForward viewrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{//Created by Pradeep .Date:15-11-2016
		MyIncomeTaxForm pendAppForm=(MyIncomeTaxForm)form;
		
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
	
		request.setAttribute("header", reqType+"For Approve");
		String tName="leave";
	
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
					ltaIncome.setEmployeeNo(rs.getString("employeeNo"));
					ltaIncome.setEmployeeName(rs.getString("EMP_FULLNAME"));
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
			/*String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			*/
				
				String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
						+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID='16' AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
						
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
				int fiscalYear1=rs.getInt("fiscal_year");
				String fiscalYear="April -"+fiscalYear1+" to March -"+(fiscalYear1+1);
				incomeTax.setFiscalYear(fiscalYear);
				incomeTax.setTotalAmount(rs.getString("total_amount"));
			
				recordStatus=rs.getString("Approval_Status");
			}
			investDet.add(incomeTax);
			request.setAttribute("investment", investDet);
			String getInvestDetails="select inv.investment_Code,mas.INVTXT, Section,inv.receipt_no,inv.dates,inv.amount,inv.status,"
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
			/*String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
			+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
					+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
			*/
				String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
						+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID='16' AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
						
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
				incomeTax.setEmployeeNo(rs.getString("employeeNo"));
				incomeTax.setEmployeeName(rs.getString("EMP_FULLNAME"));
				int fiscalYear1=0;
				 fiscalYear1=rs.getInt("Year");
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
		/*String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
				+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
		*/
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
					+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID='16' AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
					
			
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
				previousIncome.setEmployeeNo(rs.getString("employeeNo"));
				previousIncome.setEmployeeName(rs.getString("EMP_FULLNAME"));
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
				/*form2.setSaleryID(rs1.getString("SAL_SNAME"));*/
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
		/*String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
				+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
		*/
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
					+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID='16' AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
					
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
				previousIncome.setEmployeeNo(rs.getString("employeeNo"));
				previousIncome.setEmployeeName(rs.getString("EMP_FULLNAME"));
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
		/*String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
				+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
		*/
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
					+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID='16' AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
					
			
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
				ltaIncome.setEmployeeNo(rs.getString("employeeNo"));
				ltaIncome.setEmployeeName(rs.getString("EMP_FULLNAME"));
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
		/*String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
		+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID=(select loc.LOCID from emp_official_info as emp,Location as loc where PERNR='"+empID+"' "
				+ "and loc.LOCATION_CODE=emp.LOCID) AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
		*/
			String getApprovalStatus="select Approver_Id,emp.EMP_FULLNAME,dept.DPTSTXT,desg.DSGSTXT,Priority from  IncomeTax_Approvers as mat,emp_official_info AS emp,"
					+ "DEPARTMENT as dept,DESIGNATION as desg where  mat.LOCATION_ID='16' AND  Req_Type='"+reqType+"'  and mat.Approver_Id=emp.PERNR and emp.DPTID=dept.DPTID and emp.DSGID=desg.DSGID  order by Priority";
					
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
	
	public int getCountForTable(String reqStatus, String fullName, String reqType){
		//Created by Pradeep .Date:15-11-2016
    	int rowCount = 0;
    	EssDao ad=new EssDao();
    	String reqList=""; 
    	
    	if(reqType.equalsIgnoreCase("Apply Investment")){
    	 reqList="select count(*) from investment where employeenumber='"+fullName+"' and Approval_Status='"+reqStatus+"' ";
    	 if( reqStatus.equalsIgnoreCase("All"))
    			 {
 			reqList="select count(*) from investment where employeenumber='"+fullName+"' ";

    			 }
    	 
    	}
    	if(reqType.equalsIgnoreCase("Claim TRLP / HRA")){
       	 reqList="select count(*) from Claim_TRLP where employeeNo='"+fullName+"' and Approval_status='"+reqStatus+"' ";
       	 
       	 if( reqStatus.equalsIgnoreCase("All"))
		 {
		reqList="select count(*) from Claim_TRLP where employeeNo='"+fullName+"' ";

		 }
       	}
    	if(reqType.equalsIgnoreCase("Previous Income")){
       	 reqList="select count(*) from Previous_Income where employeeNo='"+fullName+"' and Approval_status='"+reqStatus+"' ";
       	 
    	 if( reqStatus.equalsIgnoreCase("All"))
		 {
		reqList="select count(*) from Previous_Income where employeeNo='"+fullName+"' ";

		 }
       	}
    	
    	if(reqType.equalsIgnoreCase("External Income/Deduction")){
       	 reqList="select count(*) from External_Income where employeeNo='"+fullName+"' and Approval_status='"+reqStatus+"' ";
       	 
       	 if( reqStatus.equalsIgnoreCase("All"))
		 {
		reqList="select count(*) from External_Income where employeeNo='"+fullName+"' ";

		 }
       	}
    	if(reqType.equalsIgnoreCase("LTA")){
       	 reqList="select count(*) from LTA_Income where employeeNo='"+fullName+"' and Approval_status='"+reqStatus+"' ";
       	 
       	 if( reqStatus.equalsIgnoreCase("All"))
		 {
		reqList="select count(*) from LTA_Income where employeeNo='"+fullName+"' ";

		 }
       	}
    	
    	if(reqType.equalsIgnoreCase("Medical")){
          	 reqList="select count(*) from Medical_Income where employeeNo='"+fullName+"' and Approval_status='"+reqStatus+"' ";
          	 
          	 if( reqStatus.equalsIgnoreCase("All"))
   		 {
   		reqList="select count(*) from Medical_Income where employeeNo='"+fullName+"' ";

   		 }
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
	
	
	public ActionForward mypendingRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		//Created by Pradeep .Date:15-11-2016

		MyIncomeTaxForm approvalsForm=(MyIncomeTaxForm)form;
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
		  approvalsForm.setReqRequstType(reqType);
			String type=approvalsForm.getSelectedFilter();
			approvalsForm.setSelectedFilter(type);
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
			List medlist=new LinkedList();
			String getInvList="";
			getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Medical_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
			if(type.equalsIgnoreCase("All"))
			{
				getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Medical_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

			}
			
			
			ResultSet rsInvList=ad.selectQuery(getInvList);
			try{
			while(rsInvList.next()){
				LTAForm trlpForm=new LTAForm();
				trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
				trlpForm.setFiscalYear(rsInvList.getString("year"));
				trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
				trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
				trlpForm.setLastApprover(rsInvList.getString("Last_approver"));
				trlpForm.setPendingApprover(rsInvList.getString("Pending_approver"));
				medlist.add(trlpForm);
			}
			request.setAttribute("medlist", medlist);
			if(medlist.size()>0){
				if(type.equalsIgnoreCase("Pending")){
					request.setAttribute("displayButton", "displayButton");
					}
				}
				if(medlist.size()==0){
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
			getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from investment as i where employeenumber='"+user.getEmployeeNo()+"' and Approval_Status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
			if(type.equalsIgnoreCase("All"))
			{
				getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from investment as i where employeenumber='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

			}
			
			
			ResultSet rsInvList=ad.selectQuery(getInvList);
			try{
			while(rsInvList.next()){
				IncomeTaxForm investment=new IncomeTaxForm();
				investment.setRequestNumber(rsInvList.getInt("requestNo"));
				
				investment.setFiscalYear(rsInvList.getString("fiscal_year"));
				investment.setTotalAmount(rsInvList.getString("total_amount"));
				investment.setSubmitDate(EMicroUtils.display(rsInvList.getDate("Creation_Date")));
				investment.setApprovalStatus(rsInvList.getString("Approval_Status"));
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
				
				getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Claim_TRLP as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
				if(type.equalsIgnoreCase("All"))
				{
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Claim_TRLP as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

				}
				
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					ClaimTRLPForm trlpForm=new ClaimTRLPForm();
					trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
                    trlpForm.setFiscalYear(rsInvList.getString("Year"));
					trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
					trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
				
				getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Previous_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
				if(type.equalsIgnoreCase("All"))
				{
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from Previous_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

				}
				
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					PreviousIncomeForm trlpForm=new PreviousIncomeForm();
					trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
                    trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
					trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
				
				getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from External_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
				if(type.equalsIgnoreCase("All"))
				{
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from External_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

				}
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					ExternalIncomeForm trlpForm=new ExternalIncomeForm();
					trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
					
					trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
					trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
				
				getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from LTA_Income as i where employeeNo='"+user.getEmployeeNo()+"' and Approval_status='"+type+"' ) as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +" ";		
				if(type.equalsIgnoreCase("All"))
				{
					getInvList="select * From (SELECT ROW_NUMBER() OVER(ORDER BY  i.requestNo desc) AS RowNum,i.* from LTA_Income as i where employeeNo='"+user.getEmployeeNo()+"') as  sub Where  sub.RowNum between "+startRecord+" and "+endRecord +"  ";

				}
				ResultSet rsInvList=ad.selectQuery(getInvList);
				try{
				while(rsInvList.next()){
					LTAForm trlpForm=new LTAForm();
					trlpForm.setRequestNo(rsInvList.getInt("requestNo"));
					trlpForm.setFiscalYear(rsInvList.getString("year"));
					trlpForm.setSubmitDate(EMicroUtils.display(rsInvList.getDate("submit_date")));
					trlpForm.setApprovalStatus(rsInvList.getString("Approval_status"));
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
			return mapping.findForward("displayPending");
	
		
	}
}
